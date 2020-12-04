package android.accessibilityservice;

import android.graphics.Region;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public interface IAccessibilityServiceClient extends IInterface {
    void clearAccessibilityCache() throws RemoteException;

    void init(IAccessibilityServiceConnection iAccessibilityServiceConnection, int i, IBinder iBinder) throws RemoteException;

    void onAccessibilityButtonAvailabilityChanged(boolean z) throws RemoteException;

    void onAccessibilityButtonClicked() throws RemoteException;

    void onAccessibilityEvent(AccessibilityEvent accessibilityEvent, boolean z) throws RemoteException;

    void onFingerprintCapturingGesturesChanged(boolean z) throws RemoteException;

    void onFingerprintGesture(int i) throws RemoteException;

    void onGesture(int i) throws RemoteException;

    void onInterrupt() throws RemoteException;

    void onKeyEvent(KeyEvent keyEvent, int i) throws RemoteException;

    void onMagnificationChanged(int i, Region region, float f, float f2, float f3) throws RemoteException;

    void onPerformGestureResult(int i, boolean z) throws RemoteException;

    void onSoftKeyboardShowModeChanged(int i) throws RemoteException;

    public static class Default implements IAccessibilityServiceClient {
        public void init(IAccessibilityServiceConnection connection, int connectionId, IBinder windowToken) throws RemoteException {
        }

        public void onAccessibilityEvent(AccessibilityEvent event, boolean serviceWantsEvent) throws RemoteException {
        }

        public void onInterrupt() throws RemoteException {
        }

        public void onGesture(int gesture) throws RemoteException {
        }

        public void clearAccessibilityCache() throws RemoteException {
        }

        public void onKeyEvent(KeyEvent event, int sequence) throws RemoteException {
        }

        public void onMagnificationChanged(int displayId, Region region, float scale, float centerX, float centerY) throws RemoteException {
        }

        public void onSoftKeyboardShowModeChanged(int showMode) throws RemoteException {
        }

        public void onPerformGestureResult(int sequence, boolean completedSuccessfully) throws RemoteException {
        }

        public void onFingerprintCapturingGesturesChanged(boolean capturing) throws RemoteException {
        }

        public void onFingerprintGesture(int gesture) throws RemoteException {
        }

        public void onAccessibilityButtonClicked() throws RemoteException {
        }

        public void onAccessibilityButtonAvailabilityChanged(boolean available) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccessibilityServiceClient {
        private static final String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceClient";
        static final int TRANSACTION_clearAccessibilityCache = 5;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_onAccessibilityButtonAvailabilityChanged = 13;
        static final int TRANSACTION_onAccessibilityButtonClicked = 12;
        static final int TRANSACTION_onAccessibilityEvent = 2;
        static final int TRANSACTION_onFingerprintCapturingGesturesChanged = 10;
        static final int TRANSACTION_onFingerprintGesture = 11;
        static final int TRANSACTION_onGesture = 4;
        static final int TRANSACTION_onInterrupt = 3;
        static final int TRANSACTION_onKeyEvent = 6;
        static final int TRANSACTION_onMagnificationChanged = 7;
        static final int TRANSACTION_onPerformGestureResult = 9;
        static final int TRANSACTION_onSoftKeyboardShowModeChanged = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityServiceClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccessibilityServiceClient)) {
                return new Proxy(obj);
            }
            return (IAccessibilityServiceClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "init";
                case 2:
                    return "onAccessibilityEvent";
                case 3:
                    return "onInterrupt";
                case 4:
                    return "onGesture";
                case 5:
                    return "clearAccessibilityCache";
                case 6:
                    return "onKeyEvent";
                case 7:
                    return "onMagnificationChanged";
                case 8:
                    return "onSoftKeyboardShowModeChanged";
                case 9:
                    return "onPerformGestureResult";
                case 10:
                    return "onFingerprintCapturingGesturesChanged";
                case 11:
                    return "onFingerprintGesture";
                case 12:
                    return "onAccessibilityButtonClicked";
                case 13:
                    return "onAccessibilityButtonAvailabilityChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.view.accessibility.AccessibilityEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: android.view.KeyEvent} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v14 */
        /* JADX WARNING: type inference failed for: r0v27 */
        /* JADX WARNING: type inference failed for: r0v28 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.accessibilityservice.IAccessibilityServiceClient"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x00fe
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x00e7;
                    case 2: goto L_0x00c8;
                    case 3: goto L_0x00c1;
                    case 4: goto L_0x00b6;
                    case 5: goto L_0x00af;
                    case 6: goto L_0x0094;
                    case 7: goto L_0x0067;
                    case 8: goto L_0x005c;
                    case 9: goto L_0x0049;
                    case 10: goto L_0x0039;
                    case 11: goto L_0x002e;
                    case 12: goto L_0x0027;
                    case 13: goto L_0x0017;
                    default: goto L_0x0012;
                }
            L_0x0012:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0017:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0022
                r1 = r10
            L_0x0022:
                r0 = r1
                r15.onAccessibilityButtonAvailabilityChanged(r0)
                return r10
            L_0x0027:
                r8.enforceInterface(r9)
                r15.onAccessibilityButtonClicked()
                return r10
            L_0x002e:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                r15.onFingerprintGesture(r0)
                return r10
            L_0x0039:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0044
                r1 = r10
            L_0x0044:
                r0 = r1
                r15.onFingerprintCapturingGesturesChanged(r0)
                return r10
            L_0x0049:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0058
                r1 = r10
            L_0x0058:
                r15.onPerformGestureResult(r0, r1)
                return r10
            L_0x005c:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                r15.onSoftKeyboardShowModeChanged(r0)
                return r10
            L_0x0067:
                r8.enforceInterface(r9)
                int r11 = r17.readInt()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x007e
                android.os.Parcelable$Creator<android.graphics.Region> r0 = android.graphics.Region.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.graphics.Region r0 = (android.graphics.Region) r0
            L_0x007c:
                r2 = r0
                goto L_0x007f
            L_0x007e:
                goto L_0x007c
            L_0x007f:
                float r12 = r17.readFloat()
                float r13 = r17.readFloat()
                float r14 = r17.readFloat()
                r0 = r15
                r1 = r11
                r3 = r12
                r4 = r13
                r5 = r14
                r0.onMagnificationChanged(r1, r2, r3, r4, r5)
                return r10
            L_0x0094:
                r8.enforceInterface(r9)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00a6
                android.os.Parcelable$Creator<android.view.KeyEvent> r0 = android.view.KeyEvent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.KeyEvent r0 = (android.view.KeyEvent) r0
                goto L_0x00a7
            L_0x00a6:
            L_0x00a7:
                int r1 = r17.readInt()
                r15.onKeyEvent(r0, r1)
                return r10
            L_0x00af:
                r8.enforceInterface(r9)
                r15.clearAccessibilityCache()
                return r10
            L_0x00b6:
                r8.enforceInterface(r9)
                int r0 = r17.readInt()
                r15.onGesture(r0)
                return r10
            L_0x00c1:
                r8.enforceInterface(r9)
                r15.onInterrupt()
                return r10
            L_0x00c8:
                r8.enforceInterface(r9)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00da
                android.os.Parcelable$Creator<android.view.accessibility.AccessibilityEvent> r0 = android.view.accessibility.AccessibilityEvent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.view.accessibility.AccessibilityEvent r0 = (android.view.accessibility.AccessibilityEvent) r0
                goto L_0x00db
            L_0x00da:
            L_0x00db:
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00e3
                r1 = r10
            L_0x00e3:
                r15.onAccessibilityEvent(r0, r1)
                return r10
            L_0x00e7:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                android.accessibilityservice.IAccessibilityServiceConnection r0 = android.accessibilityservice.IAccessibilityServiceConnection.Stub.asInterface(r0)
                int r1 = r17.readInt()
                android.os.IBinder r2 = r17.readStrongBinder()
                r15.init(r0, r1, r2)
                return r10
            L_0x00fe:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.accessibilityservice.IAccessibilityServiceClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAccessibilityServiceClient {
            public static IAccessibilityServiceClient sDefaultImpl;
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

            public void init(IAccessibilityServiceConnection connection, int connectionId, IBinder windowToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(connection != null ? connection.asBinder() : null);
                    _data.writeInt(connectionId);
                    _data.writeStrongBinder(windowToken);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().init(connection, connectionId, windowToken);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAccessibilityEvent(AccessibilityEvent event, boolean serviceWantsEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(serviceWantsEvent);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAccessibilityEvent(event, serviceWantsEvent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onInterrupt() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onInterrupt();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onGesture(int gesture) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(gesture);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGesture(gesture);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void clearAccessibilityCache() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clearAccessibilityCache();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onKeyEvent(KeyEvent event, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onKeyEvent(event, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onMagnificationChanged(int displayId, Region region, float scale, float centerX, float centerY) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeFloat(scale);
                    _data.writeFloat(centerX);
                    _data.writeFloat(centerY);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMagnificationChanged(displayId, region, scale, centerX, centerY);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSoftKeyboardShowModeChanged(int showMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showMode);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSoftKeyboardShowModeChanged(showMode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPerformGestureResult(int sequence, boolean completedSuccessfully) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    _data.writeInt(completedSuccessfully);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPerformGestureResult(sequence, completedSuccessfully);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onFingerprintCapturingGesturesChanged(boolean capturing) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(capturing);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onFingerprintCapturingGesturesChanged(capturing);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onFingerprintGesture(int gesture) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(gesture);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onFingerprintGesture(gesture);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAccessibilityButtonClicked() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAccessibilityButtonClicked();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onAccessibilityButtonAvailabilityChanged(boolean available) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(available);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onAccessibilityButtonAvailabilityChanged(available);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccessibilityServiceClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccessibilityServiceClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
