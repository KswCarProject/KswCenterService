package android.hardware.display;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ParceledListSlice;
import android.graphics.Point;
import android.media.projection.IMediaProjection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.DisplayInfo;
import android.view.Surface;

public interface IDisplayManager extends IInterface {
    void connectWifiDisplay(String str) throws RemoteException;

    int createVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, IMediaProjection iMediaProjection, String str, String str2, int i, int i2, int i3, Surface surface, int i4, String str3) throws RemoteException;

    void disconnectWifiDisplay() throws RemoteException;

    void forgetWifiDisplay(String str) throws RemoteException;

    ParceledListSlice getAmbientBrightnessStats() throws RemoteException;

    BrightnessConfiguration getBrightnessConfigurationForUser(int i) throws RemoteException;

    ParceledListSlice getBrightnessEvents(String str) throws RemoteException;

    BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException;

    int[] getDisplayIds() throws RemoteException;

    @UnsupportedAppUsage
    DisplayInfo getDisplayInfo(int i) throws RemoteException;

    Curve getMinimumBrightnessCurve() throws RemoteException;

    int getPreferredWideGamutColorSpaceId() throws RemoteException;

    Point getStableDisplaySize() throws RemoteException;

    WifiDisplayStatus getWifiDisplayStatus() throws RemoteException;

    boolean isUidPresentOnDisplay(int i, int i2) throws RemoteException;

    void pauseWifiDisplay() throws RemoteException;

    void registerCallback(IDisplayManagerCallback iDisplayManagerCallback) throws RemoteException;

    void releaseVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback) throws RemoteException;

    void renameWifiDisplay(String str, String str2) throws RemoteException;

    void requestColorMode(int i, int i2) throws RemoteException;

    void resizeVirtualDisplay(IVirtualDisplayCallback iVirtualDisplayCallback, int i, int i2, int i3) throws RemoteException;

    void resumeWifiDisplay() throws RemoteException;

    void setBrightnessConfigurationForUser(BrightnessConfiguration brightnessConfiguration, int i, String str) throws RemoteException;

    void setTemporaryAutoBrightnessAdjustment(float f) throws RemoteException;

    void setTemporaryBrightness(int i) throws RemoteException;

    void setVirtualDisplayState(IVirtualDisplayCallback iVirtualDisplayCallback, boolean z) throws RemoteException;

    void setVirtualDisplaySurface(IVirtualDisplayCallback iVirtualDisplayCallback, Surface surface) throws RemoteException;

    void startWifiDisplayScan() throws RemoteException;

    void stopWifiDisplayScan() throws RemoteException;

    public static class Default implements IDisplayManager {
        public DisplayInfo getDisplayInfo(int displayId) throws RemoteException {
            return null;
        }

        public int[] getDisplayIds() throws RemoteException {
            return null;
        }

        public boolean isUidPresentOnDisplay(int uid, int displayId) throws RemoteException {
            return false;
        }

        public void registerCallback(IDisplayManagerCallback callback) throws RemoteException {
        }

        public void startWifiDisplayScan() throws RemoteException {
        }

        public void stopWifiDisplayScan() throws RemoteException {
        }

        public void connectWifiDisplay(String address) throws RemoteException {
        }

        public void disconnectWifiDisplay() throws RemoteException {
        }

        public void renameWifiDisplay(String address, String alias) throws RemoteException {
        }

        public void forgetWifiDisplay(String address) throws RemoteException {
        }

        public void pauseWifiDisplay() throws RemoteException {
        }

        public void resumeWifiDisplay() throws RemoteException {
        }

        public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
            return null;
        }

        public void requestColorMode(int displayId, int colorMode) throws RemoteException {
        }

        public int createVirtualDisplay(IVirtualDisplayCallback callback, IMediaProjection projectionToken, String packageName, String name, int width, int height, int densityDpi, Surface surface, int flags, String uniqueId) throws RemoteException {
            return 0;
        }

        public void resizeVirtualDisplay(IVirtualDisplayCallback token, int width, int height, int densityDpi) throws RemoteException {
        }

        public void setVirtualDisplaySurface(IVirtualDisplayCallback token, Surface surface) throws RemoteException {
        }

        public void releaseVirtualDisplay(IVirtualDisplayCallback token) throws RemoteException {
        }

        public void setVirtualDisplayState(IVirtualDisplayCallback token, boolean isOn) throws RemoteException {
        }

        public Point getStableDisplaySize() throws RemoteException {
            return null;
        }

        public ParceledListSlice getBrightnessEvents(String callingPackage) throws RemoteException {
            return null;
        }

        public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
            return null;
        }

        public void setBrightnessConfigurationForUser(BrightnessConfiguration c, int userId, String packageName) throws RemoteException {
        }

        public BrightnessConfiguration getBrightnessConfigurationForUser(int userId) throws RemoteException {
            return null;
        }

        public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
            return null;
        }

        public void setTemporaryBrightness(int brightness) throws RemoteException {
        }

        public void setTemporaryAutoBrightnessAdjustment(float adjustment) throws RemoteException {
        }

        public Curve getMinimumBrightnessCurve() throws RemoteException {
            return null;
        }

        public int getPreferredWideGamutColorSpaceId() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDisplayManager {
        private static final String DESCRIPTOR = "android.hardware.display.IDisplayManager";
        static final int TRANSACTION_connectWifiDisplay = 7;
        static final int TRANSACTION_createVirtualDisplay = 15;
        static final int TRANSACTION_disconnectWifiDisplay = 8;
        static final int TRANSACTION_forgetWifiDisplay = 10;
        static final int TRANSACTION_getAmbientBrightnessStats = 22;
        static final int TRANSACTION_getBrightnessConfigurationForUser = 24;
        static final int TRANSACTION_getBrightnessEvents = 21;
        static final int TRANSACTION_getDefaultBrightnessConfiguration = 25;
        static final int TRANSACTION_getDisplayIds = 2;
        static final int TRANSACTION_getDisplayInfo = 1;
        static final int TRANSACTION_getMinimumBrightnessCurve = 28;
        static final int TRANSACTION_getPreferredWideGamutColorSpaceId = 29;
        static final int TRANSACTION_getStableDisplaySize = 20;
        static final int TRANSACTION_getWifiDisplayStatus = 13;
        static final int TRANSACTION_isUidPresentOnDisplay = 3;
        static final int TRANSACTION_pauseWifiDisplay = 11;
        static final int TRANSACTION_registerCallback = 4;
        static final int TRANSACTION_releaseVirtualDisplay = 18;
        static final int TRANSACTION_renameWifiDisplay = 9;
        static final int TRANSACTION_requestColorMode = 14;
        static final int TRANSACTION_resizeVirtualDisplay = 16;
        static final int TRANSACTION_resumeWifiDisplay = 12;
        static final int TRANSACTION_setBrightnessConfigurationForUser = 23;
        static final int TRANSACTION_setTemporaryAutoBrightnessAdjustment = 27;
        static final int TRANSACTION_setTemporaryBrightness = 26;
        static final int TRANSACTION_setVirtualDisplayState = 19;
        static final int TRANSACTION_setVirtualDisplaySurface = 17;
        static final int TRANSACTION_startWifiDisplayScan = 5;
        static final int TRANSACTION_stopWifiDisplayScan = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDisplayManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IDisplayManager)) {
                return new Proxy(obj);
            }
            return (IDisplayManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getDisplayInfo";
                case 2:
                    return "getDisplayIds";
                case 3:
                    return "isUidPresentOnDisplay";
                case 4:
                    return "registerCallback";
                case 5:
                    return "startWifiDisplayScan";
                case 6:
                    return "stopWifiDisplayScan";
                case 7:
                    return "connectWifiDisplay";
                case 8:
                    return "disconnectWifiDisplay";
                case 9:
                    return "renameWifiDisplay";
                case 10:
                    return "forgetWifiDisplay";
                case 11:
                    return "pauseWifiDisplay";
                case 12:
                    return "resumeWifiDisplay";
                case 13:
                    return "getWifiDisplayStatus";
                case 14:
                    return "requestColorMode";
                case 15:
                    return "createVirtualDisplay";
                case 16:
                    return "resizeVirtualDisplay";
                case 17:
                    return "setVirtualDisplaySurface";
                case 18:
                    return "releaseVirtualDisplay";
                case 19:
                    return "setVirtualDisplayState";
                case 20:
                    return "getStableDisplaySize";
                case 21:
                    return "getBrightnessEvents";
                case 22:
                    return "getAmbientBrightnessStats";
                case 23:
                    return "setBrightnessConfigurationForUser";
                case 24:
                    return "getBrightnessConfigurationForUser";
                case 25:
                    return "getDefaultBrightnessConfiguration";
                case 26:
                    return "setTemporaryBrightness";
                case 27:
                    return "setTemporaryAutoBrightnessAdjustment";
                case 28:
                    return "getMinimumBrightnessCurve";
                case 29:
                    return "getPreferredWideGamutColorSpaceId";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: android.hardware.display.BrightnessConfiguration} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v12 */
        /* JADX WARNING: type inference failed for: r0v20, types: [android.view.Surface] */
        /* JADX WARNING: type inference failed for: r0v42 */
        /* JADX WARNING: type inference failed for: r0v43 */
        /* JADX WARNING: type inference failed for: r0v44 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r26, android.os.Parcel r27, android.os.Parcel r28, int r29) throws android.os.RemoteException {
            /*
                r25 = this;
                r11 = r25
                r12 = r26
                r13 = r27
                r14 = r28
                java.lang.String r15 = "android.hardware.display.IDisplayManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r12 == r0) goto L_0x02ac
                r0 = 0
                r1 = 0
                switch(r12) {
                    case 1: goto L_0x0290;
                    case 2: goto L_0x0281;
                    case 3: goto L_0x026a;
                    case 4: goto L_0x0257;
                    case 5: goto L_0x024c;
                    case 6: goto L_0x0241;
                    case 7: goto L_0x0232;
                    case 8: goto L_0x0227;
                    case 9: goto L_0x0214;
                    case 10: goto L_0x0205;
                    case 11: goto L_0x01fa;
                    case 12: goto L_0x01ef;
                    case 13: goto L_0x01d7;
                    case 14: goto L_0x01c4;
                    case 15: goto L_0x0164;
                    case 16: goto L_0x0146;
                    case 17: goto L_0x0124;
                    case 18: goto L_0x0112;
                    case 19: goto L_0x00f8;
                    case 20: goto L_0x00e1;
                    case 21: goto L_0x00c6;
                    case 22: goto L_0x00af;
                    case 23: goto L_0x008d;
                    case 24: goto L_0x0072;
                    case 25: goto L_0x005b;
                    case 26: goto L_0x004d;
                    case 27: goto L_0x003f;
                    case 28: goto L_0x0028;
                    case 29: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r26, r27, r28, r29)
                return r0
            L_0x001a:
                r13.enforceInterface(r15)
                int r0 = r25.getPreferredWideGamutColorSpaceId()
                r28.writeNoException()
                r14.writeInt(r0)
                return r10
            L_0x0028:
                r13.enforceInterface(r15)
                android.hardware.display.Curve r0 = r25.getMinimumBrightnessCurve()
                r28.writeNoException()
                if (r0 == 0) goto L_0x003b
                r14.writeInt(r10)
                r0.writeToParcel(r14, r10)
                goto L_0x003e
            L_0x003b:
                r14.writeInt(r1)
            L_0x003e:
                return r10
            L_0x003f:
                r13.enforceInterface(r15)
                float r0 = r27.readFloat()
                r11.setTemporaryAutoBrightnessAdjustment(r0)
                r28.writeNoException()
                return r10
            L_0x004d:
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                r11.setTemporaryBrightness(r0)
                r28.writeNoException()
                return r10
            L_0x005b:
                r13.enforceInterface(r15)
                android.hardware.display.BrightnessConfiguration r0 = r25.getDefaultBrightnessConfiguration()
                r28.writeNoException()
                if (r0 == 0) goto L_0x006e
                r14.writeInt(r10)
                r0.writeToParcel(r14, r10)
                goto L_0x0071
            L_0x006e:
                r14.writeInt(r1)
            L_0x0071:
                return r10
            L_0x0072:
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                android.hardware.display.BrightnessConfiguration r2 = r11.getBrightnessConfigurationForUser(r0)
                r28.writeNoException()
                if (r2 == 0) goto L_0x0089
                r14.writeInt(r10)
                r2.writeToParcel(r14, r10)
                goto L_0x008c
            L_0x0089:
                r14.writeInt(r1)
            L_0x008c:
                return r10
            L_0x008d:
                r13.enforceInterface(r15)
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x009f
                android.os.Parcelable$Creator<android.hardware.display.BrightnessConfiguration> r0 = android.hardware.display.BrightnessConfiguration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.hardware.display.BrightnessConfiguration r0 = (android.hardware.display.BrightnessConfiguration) r0
                goto L_0x00a0
            L_0x009f:
            L_0x00a0:
                int r1 = r27.readInt()
                java.lang.String r2 = r27.readString()
                r11.setBrightnessConfigurationForUser(r0, r1, r2)
                r28.writeNoException()
                return r10
            L_0x00af:
                r13.enforceInterface(r15)
                android.content.pm.ParceledListSlice r0 = r25.getAmbientBrightnessStats()
                r28.writeNoException()
                if (r0 == 0) goto L_0x00c2
                r14.writeInt(r10)
                r0.writeToParcel(r14, r10)
                goto L_0x00c5
            L_0x00c2:
                r14.writeInt(r1)
            L_0x00c5:
                return r10
            L_0x00c6:
                r13.enforceInterface(r15)
                java.lang.String r0 = r27.readString()
                android.content.pm.ParceledListSlice r2 = r11.getBrightnessEvents(r0)
                r28.writeNoException()
                if (r2 == 0) goto L_0x00dd
                r14.writeInt(r10)
                r2.writeToParcel(r14, r10)
                goto L_0x00e0
            L_0x00dd:
                r14.writeInt(r1)
            L_0x00e0:
                return r10
            L_0x00e1:
                r13.enforceInterface(r15)
                android.graphics.Point r0 = r25.getStableDisplaySize()
                r28.writeNoException()
                if (r0 == 0) goto L_0x00f4
                r14.writeInt(r10)
                r0.writeToParcel(r14, r10)
                goto L_0x00f7
            L_0x00f4:
                r14.writeInt(r1)
            L_0x00f7:
                return r10
            L_0x00f8:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                android.hardware.display.IVirtualDisplayCallback r0 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(r0)
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x010b
                r1 = r10
            L_0x010b:
                r11.setVirtualDisplayState(r0, r1)
                r28.writeNoException()
                return r10
            L_0x0112:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                android.hardware.display.IVirtualDisplayCallback r0 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(r0)
                r11.releaseVirtualDisplay(r0)
                r28.writeNoException()
                return r10
            L_0x0124:
                r13.enforceInterface(r15)
                android.os.IBinder r1 = r27.readStrongBinder()
                android.hardware.display.IVirtualDisplayCallback r1 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(r1)
                int r2 = r27.readInt()
                if (r2 == 0) goto L_0x013e
                android.os.Parcelable$Creator<android.view.Surface> r0 = android.view.Surface.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.Surface r0 = (android.view.Surface) r0
                goto L_0x013f
            L_0x013e:
            L_0x013f:
                r11.setVirtualDisplaySurface(r1, r0)
                r28.writeNoException()
                return r10
            L_0x0146:
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                android.hardware.display.IVirtualDisplayCallback r0 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(r0)
                int r1 = r27.readInt()
                int r2 = r27.readInt()
                int r3 = r27.readInt()
                r11.resizeVirtualDisplay(r0, r1, r2, r3)
                r28.writeNoException()
                return r10
            L_0x0164:
                r13.enforceInterface(r15)
                android.os.IBinder r1 = r27.readStrongBinder()
                android.hardware.display.IVirtualDisplayCallback r16 = android.hardware.display.IVirtualDisplayCallback.Stub.asInterface(r1)
                android.os.IBinder r1 = r27.readStrongBinder()
                android.media.projection.IMediaProjection r17 = android.media.projection.IMediaProjection.Stub.asInterface(r1)
                java.lang.String r18 = r27.readString()
                java.lang.String r19 = r27.readString()
                int r20 = r27.readInt()
                int r21 = r27.readInt()
                int r22 = r27.readInt()
                int r1 = r27.readInt()
                if (r1 == 0) goto L_0x019b
                android.os.Parcelable$Creator<android.view.Surface> r0 = android.view.Surface.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.Surface r0 = (android.view.Surface) r0
            L_0x0199:
                r8 = r0
                goto L_0x019c
            L_0x019b:
                goto L_0x0199
            L_0x019c:
                int r23 = r27.readInt()
                java.lang.String r24 = r27.readString()
                r0 = r25
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r5 = r20
                r6 = r21
                r7 = r22
                r9 = r23
                r12 = r10
                r10 = r24
                int r0 = r0.createVirtualDisplay(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
                r28.writeNoException()
                r14.writeInt(r0)
                return r12
            L_0x01c4:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                int r1 = r27.readInt()
                r11.requestColorMode(r0, r1)
                r28.writeNoException()
                return r12
            L_0x01d7:
                r12 = r10
                r13.enforceInterface(r15)
                android.hardware.display.WifiDisplayStatus r0 = r25.getWifiDisplayStatus()
                r28.writeNoException()
                if (r0 == 0) goto L_0x01eb
                r14.writeInt(r12)
                r0.writeToParcel(r14, r12)
                goto L_0x01ee
            L_0x01eb:
                r14.writeInt(r1)
            L_0x01ee:
                return r12
            L_0x01ef:
                r12 = r10
                r13.enforceInterface(r15)
                r25.resumeWifiDisplay()
                r28.writeNoException()
                return r12
            L_0x01fa:
                r12 = r10
                r13.enforceInterface(r15)
                r25.pauseWifiDisplay()
                r28.writeNoException()
                return r12
            L_0x0205:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r27.readString()
                r11.forgetWifiDisplay(r0)
                r28.writeNoException()
                return r12
            L_0x0214:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r27.readString()
                java.lang.String r1 = r27.readString()
                r11.renameWifiDisplay(r0, r1)
                r28.writeNoException()
                return r12
            L_0x0227:
                r12 = r10
                r13.enforceInterface(r15)
                r25.disconnectWifiDisplay()
                r28.writeNoException()
                return r12
            L_0x0232:
                r12 = r10
                r13.enforceInterface(r15)
                java.lang.String r0 = r27.readString()
                r11.connectWifiDisplay(r0)
                r28.writeNoException()
                return r12
            L_0x0241:
                r12 = r10
                r13.enforceInterface(r15)
                r25.stopWifiDisplayScan()
                r28.writeNoException()
                return r12
            L_0x024c:
                r12 = r10
                r13.enforceInterface(r15)
                r25.startWifiDisplayScan()
                r28.writeNoException()
                return r12
            L_0x0257:
                r12 = r10
                r13.enforceInterface(r15)
                android.os.IBinder r0 = r27.readStrongBinder()
                android.hardware.display.IDisplayManagerCallback r0 = android.hardware.display.IDisplayManagerCallback.Stub.asInterface(r0)
                r11.registerCallback(r0)
                r28.writeNoException()
                return r12
            L_0x026a:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                int r1 = r27.readInt()
                boolean r2 = r11.isUidPresentOnDisplay(r0, r1)
                r28.writeNoException()
                r14.writeInt(r2)
                return r12
            L_0x0281:
                r12 = r10
                r13.enforceInterface(r15)
                int[] r0 = r25.getDisplayIds()
                r28.writeNoException()
                r14.writeIntArray(r0)
                return r12
            L_0x0290:
                r12 = r10
                r13.enforceInterface(r15)
                int r0 = r27.readInt()
                android.view.DisplayInfo r2 = r11.getDisplayInfo(r0)
                r28.writeNoException()
                if (r2 == 0) goto L_0x02a8
                r14.writeInt(r12)
                r2.writeToParcel(r14, r12)
                goto L_0x02ab
            L_0x02a8:
                r14.writeInt(r1)
            L_0x02ab:
                return r12
            L_0x02ac:
                r12 = r10
                r14.writeString(r15)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.display.IDisplayManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IDisplayManager {
            public static IDisplayManager sDefaultImpl;
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

            public DisplayInfo getDisplayInfo(int displayId) throws RemoteException {
                DisplayInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisplayInfo(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = DisplayInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    DisplayInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getDisplayIds() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisplayIds();
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUidPresentOnDisplay(int uid, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(displayId);
                    boolean z = false;
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUidPresentOnDisplay(uid, displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _status = z;
                    _reply.recycle();
                    _data.recycle();
                    return _status;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerCallback(IDisplayManagerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startWifiDisplayScan() throws RemoteException {
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
                    Stub.getDefaultImpl().startWifiDisplayScan();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopWifiDisplayScan() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopWifiDisplayScan();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void connectWifiDisplay(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().connectWifiDisplay(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disconnectWifiDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disconnectWifiDisplay();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void renameWifiDisplay(String address, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeString(alias);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().renameWifiDisplay(address, alias);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forgetWifiDisplay(String address) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forgetWifiDisplay(address);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pauseWifiDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pauseWifiDisplay();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resumeWifiDisplay() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resumeWifiDisplay();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public WifiDisplayStatus getWifiDisplayStatus() throws RemoteException {
                WifiDisplayStatus _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWifiDisplayStatus();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = WifiDisplayStatus.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    WifiDisplayStatus _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestColorMode(int displayId, int colorMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(colorMode);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestColorMode(displayId, colorMode);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int createVirtualDisplay(IVirtualDisplayCallback callback, IMediaProjection projectionToken, String packageName, String name, int width, int height, int densityDpi, Surface surface, int flags, String uniqueId) throws RemoteException {
                Surface surface2 = surface;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (projectionToken != null) {
                        iBinder = projectionToken.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeString(packageName);
                    _data.writeString(name);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(densityDpi);
                    if (surface2 != null) {
                        _data.writeInt(1);
                        surface2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    _data.writeString(uniqueId);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createVirtualDisplay(callback, projectionToken, packageName, name, width, height, densityDpi, surface, flags, uniqueId);
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

            public void resizeVirtualDisplay(IVirtualDisplayCallback token, int width, int height, int densityDpi) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeInt(densityDpi);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resizeVirtualDisplay(token, width, height, densityDpi);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVirtualDisplaySurface(IVirtualDisplayCallback token, Surface surface) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVirtualDisplaySurface(token, surface);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void releaseVirtualDisplay(IVirtualDisplayCallback token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().releaseVirtualDisplay(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVirtualDisplayState(IVirtualDisplayCallback token, boolean isOn) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token != null ? token.asBinder() : null);
                    _data.writeInt(isOn);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVirtualDisplayState(token, isOn);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Point getStableDisplaySize() throws RemoteException {
                Point _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStableDisplaySize();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Point.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Point _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getBrightnessEvents(String callingPackage) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBrightnessEvents(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAmbientBrightnessStats() throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAmbientBrightnessStats();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBrightnessConfigurationForUser(BrightnessConfiguration c, int userId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (c != null) {
                        _data.writeInt(1);
                        c.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBrightnessConfigurationForUser(c, userId, packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public BrightnessConfiguration getBrightnessConfigurationForUser(int userId) throws RemoteException {
                BrightnessConfiguration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBrightnessConfigurationForUser(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BrightnessConfiguration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BrightnessConfiguration _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public BrightnessConfiguration getDefaultBrightnessConfiguration() throws RemoteException {
                BrightnessConfiguration _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultBrightnessConfiguration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = BrightnessConfiguration.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    BrightnessConfiguration _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTemporaryBrightness(int brightness) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(brightness);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTemporaryBrightness(brightness);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTemporaryAutoBrightnessAdjustment(float adjustment) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(adjustment);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTemporaryAutoBrightnessAdjustment(adjustment);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Curve getMinimumBrightnessCurve() throws RemoteException {
                Curve _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMinimumBrightnessCurve();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Curve.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Curve _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPreferredWideGamutColorSpaceId() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreferredWideGamutColorSpaceId();
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
        }

        public static boolean setDefaultImpl(IDisplayManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IDisplayManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
