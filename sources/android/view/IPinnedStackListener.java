package android.view;

import android.content.pm.ParceledListSlice;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPinnedStackListener extends IInterface {
    void onActionsChanged(ParceledListSlice parceledListSlice) throws RemoteException;

    void onImeVisibilityChanged(boolean z, int i) throws RemoteException;

    void onListenerRegistered(IPinnedStackController iPinnedStackController) throws RemoteException;

    void onMinimizedStateChanged(boolean z) throws RemoteException;

    void onMovementBoundsChanged(Rect rect, Rect rect2, Rect rect3, boolean z, boolean z2, int i) throws RemoteException;

    void onShelfVisibilityChanged(boolean z, int i) throws RemoteException;

    public static class Default implements IPinnedStackListener {
        public void onListenerRegistered(IPinnedStackController controller) throws RemoteException {
        }

        public void onMovementBoundsChanged(Rect insetBounds, Rect normalBounds, Rect animatingBounds, boolean fromImeAdjustment, boolean fromShelfAdjustment, int displayRotation) throws RemoteException {
        }

        public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) throws RemoteException {
        }

        public void onShelfVisibilityChanged(boolean shelfVisible, int shelfHeight) throws RemoteException {
        }

        public void onMinimizedStateChanged(boolean isMinimized) throws RemoteException {
        }

        public void onActionsChanged(ParceledListSlice actions) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPinnedStackListener {
        private static final String DESCRIPTOR = "android.view.IPinnedStackListener";
        static final int TRANSACTION_onActionsChanged = 6;
        static final int TRANSACTION_onImeVisibilityChanged = 3;
        static final int TRANSACTION_onListenerRegistered = 1;
        static final int TRANSACTION_onMinimizedStateChanged = 5;
        static final int TRANSACTION_onMovementBoundsChanged = 2;
        static final int TRANSACTION_onShelfVisibilityChanged = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPinnedStackListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPinnedStackListener)) {
                return new Proxy(obj);
            }
            return (IPinnedStackListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onListenerRegistered";
                case 2:
                    return "onMovementBoundsChanged";
                case 3:
                    return "onImeVisibilityChanged";
                case 4:
                    return "onShelfVisibilityChanged";
                case 5:
                    return "onMinimizedStateChanged";
                case 6:
                    return "onActionsChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX WARNING: type inference failed for: r0v11, types: [android.graphics.Rect] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r14, android.os.Parcel r15, android.os.Parcel r16, int r17) throws android.os.RemoteException {
            /*
                r13 = this;
                r7 = r13
                r8 = r14
                r9 = r15
                java.lang.String r10 = "android.view.IPinnedStackListener"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x00c2
                r0 = 0
                r5 = 0
                switch(r8) {
                    case 1: goto L_0x00b3;
                    case 2: goto L_0x0064;
                    case 3: goto L_0x0050;
                    case 4: goto L_0x003c;
                    case 5: goto L_0x002c;
                    case 6: goto L_0x0015;
                    default: goto L_0x0010;
                }
            L_0x0010:
                boolean r0 = super.onTransact(r14, r15, r16, r17)
                return r0
            L_0x0015:
                r15.enforceInterface(r10)
                int r1 = r15.readInt()
                if (r1 == 0) goto L_0x0027
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r0 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.content.pm.ParceledListSlice r0 = (android.content.pm.ParceledListSlice) r0
                goto L_0x0028
            L_0x0027:
            L_0x0028:
                r13.onActionsChanged(r0)
                return r11
            L_0x002c:
                r15.enforceInterface(r10)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0037
                r5 = r11
            L_0x0037:
                r0 = r5
                r13.onMinimizedStateChanged(r0)
                return r11
            L_0x003c:
                r15.enforceInterface(r10)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x0047
                r5 = r11
            L_0x0047:
                r0 = r5
                int r1 = r15.readInt()
                r13.onShelfVisibilityChanged(r0, r1)
                return r11
            L_0x0050:
                r15.enforceInterface(r10)
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x005b
                r5 = r11
            L_0x005b:
                r0 = r5
                int r1 = r15.readInt()
                r13.onImeVisibilityChanged(r0, r1)
                return r11
            L_0x0064:
                r15.enforceInterface(r10)
                int r1 = r15.readInt()
                if (r1 == 0) goto L_0x0076
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r15)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x0077
            L_0x0076:
                r1 = r0
            L_0x0077:
                int r2 = r15.readInt()
                if (r2 == 0) goto L_0x0086
                android.os.Parcelable$Creator<android.graphics.Rect> r2 = android.graphics.Rect.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r15)
                android.graphics.Rect r2 = (android.graphics.Rect) r2
                goto L_0x0087
            L_0x0086:
                r2 = r0
            L_0x0087:
                int r3 = r15.readInt()
                if (r3 == 0) goto L_0x0097
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
            L_0x0095:
                r3 = r0
                goto L_0x0098
            L_0x0097:
                goto L_0x0095
            L_0x0098:
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x00a0
                r4 = r11
                goto L_0x00a1
            L_0x00a0:
                r4 = r5
            L_0x00a1:
                int r0 = r15.readInt()
                if (r0 == 0) goto L_0x00a9
                r5 = r11
            L_0x00a9:
                int r12 = r15.readInt()
                r0 = r13
                r6 = r12
                r0.onMovementBoundsChanged(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x00b3:
                r15.enforceInterface(r10)
                android.os.IBinder r0 = r15.readStrongBinder()
                android.view.IPinnedStackController r0 = android.view.IPinnedStackController.Stub.asInterface(r0)
                r13.onListenerRegistered(r0)
                return r11
            L_0x00c2:
                r0 = r16
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.IPinnedStackListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IPinnedStackListener {
            public static IPinnedStackListener sDefaultImpl;
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

            public void onListenerRegistered(IPinnedStackController controller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(controller != null ? controller.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onListenerRegistered(controller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onMovementBoundsChanged(Rect insetBounds, Rect normalBounds, Rect animatingBounds, boolean fromImeAdjustment, boolean fromShelfAdjustment, int displayRotation) throws RemoteException {
                Rect rect = insetBounds;
                Rect rect2 = normalBounds;
                Rect rect3 = animatingBounds;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        _data.writeInt(1);
                        rect.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (rect2 != null) {
                        _data.writeInt(1);
                        rect2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (rect3 != null) {
                        _data.writeInt(1);
                        rect3.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(fromImeAdjustment ? 1 : 0);
                        try {
                            _data.writeInt(fromShelfAdjustment ? 1 : 0);
                        } catch (Throwable th) {
                            th = th;
                            int i = displayRotation;
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(displayRotation);
                            try {
                                if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().onMovementBoundsChanged(insetBounds, normalBounds, animatingBounds, fromImeAdjustment, fromShelfAdjustment, displayRotation);
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        boolean z = fromShelfAdjustment;
                        int i2 = displayRotation;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    boolean z2 = fromImeAdjustment;
                    boolean z3 = fromShelfAdjustment;
                    int i22 = displayRotation;
                    _data.recycle();
                    throw th;
                }
            }

            public void onImeVisibilityChanged(boolean imeVisible, int imeHeight) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(imeVisible);
                    _data.writeInt(imeHeight);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onImeVisibilityChanged(imeVisible, imeHeight);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onShelfVisibilityChanged(boolean shelfVisible, int shelfHeight) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(shelfVisible);
                    _data.writeInt(shelfHeight);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onShelfVisibilityChanged(shelfVisible, shelfHeight);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onMinimizedStateChanged(boolean isMinimized) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isMinimized);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onMinimizedStateChanged(isMinimized);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onActionsChanged(ParceledListSlice actions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (actions != null) {
                        _data.writeInt(1);
                        actions.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onActionsChanged(actions);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IPinnedStackListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IPinnedStackListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
