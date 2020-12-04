package android.view.accessibility;

import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.MagnificationSpec;

public interface IAccessibilityInteractionConnection extends IInterface {
    void clearAccessibilityFocus() throws RemoteException;

    void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, Bundle bundle) throws RemoteException;

    void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec) throws RemoteException;

    void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec) throws RemoteException;

    void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec) throws RemoteException;

    void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec) throws RemoteException;

    void notifyOutsideTouch() throws RemoteException;

    void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws RemoteException;

    public static class Default implements IAccessibilityInteractionConnection {
        public void findAccessibilityNodeInfoByAccessibilityId(long accessibilityNodeId, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, Bundle arguments) throws RemoteException {
        }

        public void findAccessibilityNodeInfosByViewId(long accessibilityNodeId, String viewId, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
        }

        public void findAccessibilityNodeInfosByText(long accessibilityNodeId, String text, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
        }

        public void findFocus(long accessibilityNodeId, int focusType, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
        }

        public void focusSearch(long accessibilityNodeId, int direction, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
        }

        public void performAccessibilityAction(long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid) throws RemoteException {
        }

        public void clearAccessibilityFocus() throws RemoteException {
        }

        public void notifyOutsideTouch() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccessibilityInteractionConnection {
        private static final String DESCRIPTOR = "android.view.accessibility.IAccessibilityInteractionConnection";
        static final int TRANSACTION_clearAccessibilityFocus = 7;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 1;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 2;
        static final int TRANSACTION_findFocus = 4;
        static final int TRANSACTION_focusSearch = 5;
        static final int TRANSACTION_notifyOutsideTouch = 8;
        static final int TRANSACTION_performAccessibilityAction = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityInteractionConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccessibilityInteractionConnection)) {
                return new Proxy(obj);
            }
            return (IAccessibilityInteractionConnection) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "findAccessibilityNodeInfoByAccessibilityId";
                case 2:
                    return "findAccessibilityNodeInfosByViewId";
                case 3:
                    return "findAccessibilityNodeInfosByText";
                case 4:
                    return "findFocus";
                case 5:
                    return "focusSearch";
                case 6:
                    return "performAccessibilityAction";
                case 7:
                    return "clearAccessibilityFocus";
                case 8:
                    return "notifyOutsideTouch";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v0, resolved type: android.view.MagnificationSpec} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v1, resolved type: android.view.MagnificationSpec} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v15, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r25v1, resolved type: android.view.MagnificationSpec} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v19, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r35v1, resolved type: android.view.MagnificationSpec} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v23, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v30, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v31, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v33, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v34, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r3v10, types: [android.view.MagnificationSpec] */
        /* JADX WARNING: type inference failed for: r3v14, types: [android.view.MagnificationSpec] */
        /* JADX WARNING: type inference failed for: r3v18, types: [android.view.MagnificationSpec] */
        /* JADX WARNING: type inference failed for: r3v22, types: [android.view.MagnificationSpec] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r39, android.os.Parcel r40, android.os.Parcel r41, int r42) throws android.os.RemoteException {
            /*
                r38 = this;
                r0 = r39
                r1 = r40
                java.lang.String r2 = "android.view.accessibility.IAccessibilityInteractionConnection"
                r3 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r4 = 1
                if (r0 == r3) goto L_0x0241
                r3 = 0
                switch(r0) {
                    case 1: goto L_0x01d8;
                    case 2: goto L_0x017d;
                    case 3: goto L_0x0121;
                    case 4: goto L_0x00c6;
                    case 5: goto L_0x006a;
                    case 6: goto L_0x0023;
                    case 7: goto L_0x001c;
                    case 8: goto L_0x0015;
                    default: goto L_0x0010;
                }
            L_0x0010:
                boolean r3 = super.onTransact(r39, r40, r41, r42)
                return r3
            L_0x0015:
                r1.enforceInterface(r2)
                r38.notifyOutsideTouch()
                return r4
            L_0x001c:
                r1.enforceInterface(r2)
                r38.clearAccessibilityFocus()
                return r4
            L_0x0023:
                r1.enforceInterface(r2)
                long r16 = r40.readLong()
                int r18 = r40.readInt()
                int r5 = r40.readInt()
                if (r5 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                android.os.Bundle r3 = (android.os.Bundle) r3
            L_0x003c:
                r9 = r3
                goto L_0x003f
            L_0x003e:
                goto L_0x003c
            L_0x003f:
                int r3 = r40.readInt()
                android.os.IBinder r5 = r40.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r19 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r5)
                int r20 = r40.readInt()
                int r21 = r40.readInt()
                long r22 = r40.readLong()
                r5 = r38
                r6 = r16
                r8 = r18
                r10 = r3
                r11 = r19
                r12 = r20
                r13 = r21
                r14 = r22
                r5.performAccessibilityAction(r6, r8, r9, r10, r11, r12, r13, r14)
                return r4
            L_0x006a:
                r1.enforceInterface(r2)
                long r5 = r40.readLong()
                int r7 = r40.readInt()
                int r8 = r40.readInt()
                if (r8 == 0) goto L_0x0086
                android.os.Parcelable$Creator<android.graphics.Region> r8 = android.graphics.Region.CREATOR
                java.lang.Object r8 = r8.createFromParcel(r1)
                android.graphics.Region r8 = (android.graphics.Region) r8
                r28 = r8
                goto L_0x0088
            L_0x0086:
                r28 = r3
            L_0x0088:
                int r8 = r40.readInt()
                android.os.IBinder r9 = r40.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r9 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r9)
                int r10 = r40.readInt()
                int r11 = r40.readInt()
                long r12 = r40.readLong()
                int r14 = r40.readInt()
                if (r14 == 0) goto L_0x00b1
                android.os.Parcelable$Creator<android.view.MagnificationSpec> r3 = android.view.MagnificationSpec.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                android.view.MagnificationSpec r3 = (android.view.MagnificationSpec) r3
            L_0x00ae:
                r35 = r3
                goto L_0x00b2
            L_0x00b1:
                goto L_0x00ae
            L_0x00b2:
                r24 = r38
                r25 = r5
                r27 = r7
                r29 = r8
                r30 = r9
                r31 = r10
                r32 = r11
                r33 = r12
                r24.focusSearch(r25, r27, r28, r29, r30, r31, r32, r33, r35)
                return r4
            L_0x00c6:
                r1.enforceInterface(r2)
                long r5 = r40.readLong()
                int r7 = r40.readInt()
                int r8 = r40.readInt()
                if (r8 == 0) goto L_0x00e2
                android.os.Parcelable$Creator<android.graphics.Region> r8 = android.graphics.Region.CREATOR
                java.lang.Object r8 = r8.createFromParcel(r1)
                android.graphics.Region r8 = (android.graphics.Region) r8
                r18 = r8
                goto L_0x00e4
            L_0x00e2:
                r18 = r3
            L_0x00e4:
                int r8 = r40.readInt()
                android.os.IBinder r9 = r40.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r9 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r9)
                int r10 = r40.readInt()
                int r11 = r40.readInt()
                long r12 = r40.readLong()
                int r14 = r40.readInt()
                if (r14 == 0) goto L_0x010d
                android.os.Parcelable$Creator<android.view.MagnificationSpec> r3 = android.view.MagnificationSpec.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                android.view.MagnificationSpec r3 = (android.view.MagnificationSpec) r3
            L_0x010a:
                r25 = r3
                goto L_0x010e
            L_0x010d:
                goto L_0x010a
            L_0x010e:
                r14 = r38
                r15 = r5
                r17 = r7
                r19 = r8
                r20 = r9
                r21 = r10
                r22 = r11
                r23 = r12
                r14.findFocus(r15, r17, r18, r19, r20, r21, r22, r23, r25)
                return r4
            L_0x0121:
                r1.enforceInterface(r2)
                long r5 = r40.readLong()
                java.lang.String r7 = r40.readString()
                int r8 = r40.readInt()
                if (r8 == 0) goto L_0x013d
                android.os.Parcelable$Creator<android.graphics.Region> r8 = android.graphics.Region.CREATOR
                java.lang.Object r8 = r8.createFromParcel(r1)
                android.graphics.Region r8 = (android.graphics.Region) r8
                r30 = r8
                goto L_0x013f
            L_0x013d:
                r30 = r3
            L_0x013f:
                int r8 = r40.readInt()
                android.os.IBinder r9 = r40.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r9 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r9)
                int r10 = r40.readInt()
                int r11 = r40.readInt()
                long r12 = r40.readLong()
                int r14 = r40.readInt()
                if (r14 == 0) goto L_0x0168
                android.os.Parcelable$Creator<android.view.MagnificationSpec> r3 = android.view.MagnificationSpec.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                android.view.MagnificationSpec r3 = (android.view.MagnificationSpec) r3
            L_0x0165:
                r37 = r3
                goto L_0x0169
            L_0x0168:
                goto L_0x0165
            L_0x0169:
                r26 = r38
                r27 = r5
                r29 = r7
                r31 = r8
                r32 = r9
                r33 = r10
                r34 = r11
                r35 = r12
                r26.findAccessibilityNodeInfosByText(r27, r29, r30, r31, r32, r33, r34, r35, r37)
                return r4
            L_0x017d:
                r1.enforceInterface(r2)
                long r5 = r40.readLong()
                java.lang.String r7 = r40.readString()
                int r8 = r40.readInt()
                if (r8 == 0) goto L_0x0199
                android.os.Parcelable$Creator<android.graphics.Region> r8 = android.graphics.Region.CREATOR
                java.lang.Object r8 = r8.createFromParcel(r1)
                android.graphics.Region r8 = (android.graphics.Region) r8
                r18 = r8
                goto L_0x019b
            L_0x0199:
                r18 = r3
            L_0x019b:
                int r8 = r40.readInt()
                android.os.IBinder r9 = r40.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r9 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r9)
                int r10 = r40.readInt()
                int r11 = r40.readInt()
                long r12 = r40.readLong()
                int r14 = r40.readInt()
                if (r14 == 0) goto L_0x01c4
                android.os.Parcelable$Creator<android.view.MagnificationSpec> r3 = android.view.MagnificationSpec.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                android.view.MagnificationSpec r3 = (android.view.MagnificationSpec) r3
            L_0x01c1:
                r25 = r3
                goto L_0x01c5
            L_0x01c4:
                goto L_0x01c1
            L_0x01c5:
                r14 = r38
                r15 = r5
                r17 = r7
                r19 = r8
                r20 = r9
                r21 = r10
                r22 = r11
                r23 = r12
                r14.findAccessibilityNodeInfosByViewId(r15, r17, r18, r19, r20, r21, r22, r23, r25)
                return r4
            L_0x01d8:
                r1.enforceInterface(r2)
                long r5 = r40.readLong()
                int r7 = r40.readInt()
                if (r7 == 0) goto L_0x01f0
                android.os.Parcelable$Creator<android.graphics.Region> r7 = android.graphics.Region.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r1)
                android.graphics.Region r7 = (android.graphics.Region) r7
                r29 = r7
                goto L_0x01f2
            L_0x01f0:
                r29 = r3
            L_0x01f2:
                int r7 = r40.readInt()
                android.os.IBinder r8 = r40.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r8 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r8)
                int r9 = r40.readInt()
                int r10 = r40.readInt()
                long r11 = r40.readLong()
                int r13 = r40.readInt()
                if (r13 == 0) goto L_0x021b
                android.os.Parcelable$Creator<android.view.MagnificationSpec> r13 = android.view.MagnificationSpec.CREATOR
                java.lang.Object r13 = r13.createFromParcel(r1)
                android.view.MagnificationSpec r13 = (android.view.MagnificationSpec) r13
                r36 = r13
                goto L_0x021d
            L_0x021b:
                r36 = r3
            L_0x021d:
                int r13 = r40.readInt()
                if (r13 == 0) goto L_0x022e
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r1)
                android.os.Bundle r3 = (android.os.Bundle) r3
            L_0x022b:
                r37 = r3
                goto L_0x022f
            L_0x022e:
                goto L_0x022b
            L_0x022f:
                r26 = r38
                r27 = r5
                r30 = r7
                r31 = r8
                r32 = r9
                r33 = r10
                r34 = r11
                r26.findAccessibilityNodeInfoByAccessibilityId(r27, r29, r30, r31, r32, r33, r34, r36, r37)
                return r4
            L_0x0241:
                r3 = r41
                r3.writeString(r2)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.accessibility.IAccessibilityInteractionConnection.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAccessibilityInteractionConnection {
            public static IAccessibilityInteractionConnection sDefaultImpl;
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

            public void findAccessibilityNodeInfoByAccessibilityId(long accessibilityNodeId, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec, Bundle arguments) throws RemoteException {
                Parcel _data;
                Region region = bounds;
                MagnificationSpec magnificationSpec = spec;
                Bundle bundle = arguments;
                Parcel _data2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data2.writeLong(accessibilityNodeId);
                    if (region != null) {
                        try {
                            _data2.writeInt(1);
                            region.writeToParcel(_data2, 0);
                        } catch (Throwable th) {
                            th = th;
                            _data = _data2;
                        }
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(interactionId);
                    _data2.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data2.writeInt(flags);
                    _data2.writeInt(interrogatingPid);
                    _data2.writeLong(interrogatingTid);
                    if (magnificationSpec != null) {
                        _data2.writeInt(1);
                        magnificationSpec.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (bundle != null) {
                        _data2.writeInt(1);
                        bundle.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data2, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data2.recycle();
                        return;
                    }
                    _data = _data2;
                    try {
                        Stub.getDefaultImpl().findAccessibilityNodeInfoByAccessibilityId(accessibilityNodeId, bounds, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec, arguments);
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _data = _data2;
                    _data.recycle();
                    throw th;
                }
            }

            public void findAccessibilityNodeInfosByViewId(long accessibilityNodeId, String viewId, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
                Region region = bounds;
                MagnificationSpec magnificationSpec = spec;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(accessibilityNodeId);
                    _data.writeString(viewId);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(interactionId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(flags);
                    _data.writeInt(interrogatingPid);
                    _data.writeLong(interrogatingTid);
                    if (magnificationSpec != null) {
                        _data.writeInt(1);
                        magnificationSpec.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().findAccessibilityNodeInfosByViewId(accessibilityNodeId, viewId, bounds, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void findAccessibilityNodeInfosByText(long accessibilityNodeId, String text, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
                Region region = bounds;
                MagnificationSpec magnificationSpec = spec;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(accessibilityNodeId);
                    _data.writeString(text);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(interactionId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(flags);
                    _data.writeInt(interrogatingPid);
                    _data.writeLong(interrogatingTid);
                    if (magnificationSpec != null) {
                        _data.writeInt(1);
                        magnificationSpec.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().findAccessibilityNodeInfosByText(accessibilityNodeId, text, bounds, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void findFocus(long accessibilityNodeId, int focusType, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
                Region region = bounds;
                MagnificationSpec magnificationSpec = spec;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(accessibilityNodeId);
                    _data.writeInt(focusType);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(interactionId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(flags);
                    _data.writeInt(interrogatingPid);
                    _data.writeLong(interrogatingTid);
                    if (magnificationSpec != null) {
                        _data.writeInt(1);
                        magnificationSpec.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().findFocus(accessibilityNodeId, focusType, bounds, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void focusSearch(long accessibilityNodeId, int direction, Region bounds, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid, MagnificationSpec spec) throws RemoteException {
                Region region = bounds;
                MagnificationSpec magnificationSpec = spec;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(accessibilityNodeId);
                    _data.writeInt(direction);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(interactionId);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(flags);
                    _data.writeInt(interrogatingPid);
                    _data.writeLong(interrogatingTid);
                    if (magnificationSpec != null) {
                        _data.writeInt(1);
                        magnificationSpec.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().focusSearch(accessibilityNodeId, direction, bounds, interactionId, callback, flags, interrogatingPid, interrogatingTid, spec);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void performAccessibilityAction(long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, int interrogatingPid, long interrogatingTid) throws RemoteException {
                Bundle bundle = arguments;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeLong(accessibilityNodeId);
                        _data.writeInt(action);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(interactionId);
                        _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                        _data.writeInt(flags);
                        _data.writeInt(interrogatingPid);
                        _data.writeLong(interrogatingTid);
                        if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().performAccessibilityAction(accessibilityNodeId, action, arguments, interactionId, callback, flags, interrogatingPid, interrogatingTid);
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    long j = accessibilityNodeId;
                    _data.recycle();
                    throw th;
                }
            }

            public void clearAccessibilityFocus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().clearAccessibilityFocus();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyOutsideTouch() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyOutsideTouch();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccessibilityInteractionConnection impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccessibilityInteractionConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
