package android.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.DisplayCutout;
import com.android.internal.os.IResultReceiver;

public interface IWindow extends IInterface {
    void closeSystemDialogs(String str) throws RemoteException;

    void dispatchAppVisibility(boolean z) throws RemoteException;

    void dispatchDragEvent(DragEvent dragEvent) throws RemoteException;

    void dispatchGetNewSurface() throws RemoteException;

    void dispatchPointerCaptureChanged(boolean z) throws RemoteException;

    void dispatchSystemUiVisibilityChanged(int i, int i2, int i3, int i4) throws RemoteException;

    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException;

    void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, boolean z) throws RemoteException;

    void dispatchWindowShown() throws RemoteException;

    void executeCommand(String str, String str2, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void insetsChanged(InsetsState insetsState) throws RemoteException;

    void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) throws RemoteException;

    void moved(int i, int i2) throws RemoteException;

    void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int i) throws RemoteException;

    void resized(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, Rect rect6, boolean z, MergedConfiguration mergedConfiguration, Rect rect7, boolean z2, boolean z3, int i, DisplayCutout.ParcelableWrapper parcelableWrapper) throws RemoteException;

    void updatePointerIcon(float f, float f2) throws RemoteException;

    void windowFocusChanged(boolean z, boolean z2) throws RemoteException;

    public static class Default implements IWindow {
        public void executeCommand(String command, String parameters, ParcelFileDescriptor descriptor) throws RemoteException {
        }

        public void resized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration newMergedConfiguration, Rect backDropFrame, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) throws RemoteException {
        }

        public void insetsChanged(InsetsState insetsState) throws RemoteException {
        }

        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) throws RemoteException {
        }

        public void moved(int newX, int newY) throws RemoteException {
        }

        public void dispatchAppVisibility(boolean visible) throws RemoteException {
        }

        public void dispatchGetNewSurface() throws RemoteException {
        }

        public void windowFocusChanged(boolean hasFocus, boolean inTouchMode) throws RemoteException {
        }

        public void closeSystemDialogs(String reason) throws RemoteException {
        }

        public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, boolean sync) throws RemoteException {
        }

        public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) throws RemoteException {
        }

        public void dispatchDragEvent(DragEvent event) throws RemoteException {
        }

        public void updatePointerIcon(float x, float y) throws RemoteException {
        }

        public void dispatchSystemUiVisibilityChanged(int seq, int globalVisibility, int localValue, int localChanges) throws RemoteException {
        }

        public void dispatchWindowShown() throws RemoteException {
        }

        public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) throws RemoteException {
        }

        public void dispatchPointerCaptureChanged(boolean hasCapture) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWindow {
        private static final String DESCRIPTOR = "android.view.IWindow";
        static final int TRANSACTION_closeSystemDialogs = 9;
        static final int TRANSACTION_dispatchAppVisibility = 6;
        static final int TRANSACTION_dispatchDragEvent = 12;
        static final int TRANSACTION_dispatchGetNewSurface = 7;
        static final int TRANSACTION_dispatchPointerCaptureChanged = 17;
        static final int TRANSACTION_dispatchSystemUiVisibilityChanged = 14;
        static final int TRANSACTION_dispatchWallpaperCommand = 11;
        static final int TRANSACTION_dispatchWallpaperOffsets = 10;
        static final int TRANSACTION_dispatchWindowShown = 15;
        static final int TRANSACTION_executeCommand = 1;
        static final int TRANSACTION_insetsChanged = 3;
        static final int TRANSACTION_insetsControlChanged = 4;
        static final int TRANSACTION_moved = 5;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 16;
        static final int TRANSACTION_resized = 2;
        static final int TRANSACTION_updatePointerIcon = 13;
        static final int TRANSACTION_windowFocusChanged = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindow asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWindow)) {
                return new Proxy(obj);
            }
            return (IWindow) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "executeCommand";
                case 2:
                    return "resized";
                case 3:
                    return "insetsChanged";
                case 4:
                    return "insetsControlChanged";
                case 5:
                    return "moved";
                case 6:
                    return "dispatchAppVisibility";
                case 7:
                    return "dispatchGetNewSurface";
                case 8:
                    return "windowFocusChanged";
                case 9:
                    return "closeSystemDialogs";
                case 10:
                    return "dispatchWallpaperOffsets";
                case 11:
                    return "dispatchWallpaperCommand";
                case 12:
                    return "dispatchDragEvent";
                case 13:
                    return "updatePointerIcon";
                case 14:
                    return "dispatchSystemUiVisibilityChanged";
                case 15:
                    return "dispatchWindowShown";
                case 16:
                    return "requestAppKeyboardShortcuts";
                case 17:
                    return "dispatchPointerCaptureChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v4, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.view.InsetsState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v7, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: android.view.InsetsState} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v10, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: android.view.DragEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v13, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v14, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v15, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v16, resolved type: android.os.ParcelFileDescriptor} */
        /* JADX WARNING: type inference failed for: r17v6, types: [android.view.InsetsState] */
        /* JADX WARNING: type inference failed for: r17v9, types: [android.view.InsetsState] */
        /* JADX WARNING: type inference failed for: r17v12, types: [android.view.DragEvent] */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r14 = r19
                r15 = r20
                r12 = r21
                java.lang.String r13 = "android.view.IWindow"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r16 = 1
                if (r15 == r0) goto L_0x025e
                r0 = 0
                r17 = 0
                switch(r15) {
                    case 1: goto L_0x0237;
                    case 2: goto L_0x016e;
                    case 3: goto L_0x0153;
                    case 4: goto L_0x0130;
                    case 5: goto L_0x0121;
                    case 6: goto L_0x0111;
                    case 7: goto L_0x010a;
                    case 8: goto L_0x00f0;
                    case 9: goto L_0x00e5;
                    case 10: goto L_0x00be;
                    case 11: goto L_0x0085;
                    case 12: goto L_0x006a;
                    case 13: goto L_0x005b;
                    case 14: goto L_0x0044;
                    case 15: goto L_0x003d;
                    case 16: goto L_0x002a;
                    case 17: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r12.enforceInterface(r13)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0026
                r0 = r16
            L_0x0026:
                r14.dispatchPointerCaptureChanged(r0)
                return r16
            L_0x002a:
                r12.enforceInterface(r13)
                android.os.IBinder r0 = r21.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                int r1 = r21.readInt()
                r14.requestAppKeyboardShortcuts(r0, r1)
                return r16
            L_0x003d:
                r12.enforceInterface(r13)
                r19.dispatchWindowShown()
                return r16
            L_0x0044:
                r12.enforceInterface(r13)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                r14.dispatchSystemUiVisibilityChanged(r0, r1, r2, r3)
                return r16
            L_0x005b:
                r12.enforceInterface(r13)
                float r0 = r21.readFloat()
                float r1 = r21.readFloat()
                r14.updatePointerIcon(r0, r1)
                return r16
            L_0x006a:
                r12.enforceInterface(r13)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x007e
                android.os.Parcelable$Creator<android.view.DragEvent> r0 = android.view.DragEvent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r17 = r0
                android.view.DragEvent r17 = (android.view.DragEvent) r17
                goto L_0x007f
            L_0x007e:
            L_0x007f:
                r0 = r17
                r14.dispatchDragEvent(r0)
                return r16
            L_0x0085:
                r12.enforceInterface(r13)
                java.lang.String r7 = r21.readString()
                int r8 = r21.readInt()
                int r9 = r21.readInt()
                int r10 = r21.readInt()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00a8
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x00aa
            L_0x00a8:
                r5 = r17
            L_0x00aa:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00b3
                r6 = r16
                goto L_0x00b4
            L_0x00b3:
                r6 = r0
            L_0x00b4:
                r0 = r19
                r1 = r7
                r2 = r8
                r3 = r9
                r4 = r10
                r0.dispatchWallpaperCommand(r1, r2, r3, r4, r5, r6)
                return r16
            L_0x00be:
                r12.enforceInterface(r13)
                float r6 = r21.readFloat()
                float r7 = r21.readFloat()
                float r8 = r21.readFloat()
                float r9 = r21.readFloat()
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00da
                r5 = r16
                goto L_0x00db
            L_0x00da:
                r5 = r0
            L_0x00db:
                r0 = r19
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r0.dispatchWallpaperOffsets(r1, r2, r3, r4, r5)
                return r16
            L_0x00e5:
                r12.enforceInterface(r13)
                java.lang.String r0 = r21.readString()
                r14.closeSystemDialogs(r0)
                return r16
            L_0x00f0:
                r12.enforceInterface(r13)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x00fc
                r1 = r16
                goto L_0x00fd
            L_0x00fc:
                r1 = r0
            L_0x00fd:
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0106
                r0 = r16
            L_0x0106:
                r14.windowFocusChanged(r1, r0)
                return r16
            L_0x010a:
                r12.enforceInterface(r13)
                r19.dispatchGetNewSurface()
                return r16
            L_0x0111:
                r12.enforceInterface(r13)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x011d
                r0 = r16
            L_0x011d:
                r14.dispatchAppVisibility(r0)
                return r16
            L_0x0121:
                r12.enforceInterface(r13)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                r14.moved(r0, r1)
                return r16
            L_0x0130:
                r12.enforceInterface(r13)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0144
                android.os.Parcelable$Creator<android.view.InsetsState> r0 = android.view.InsetsState.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r17 = r0
                android.view.InsetsState r17 = (android.view.InsetsState) r17
                goto L_0x0145
            L_0x0144:
            L_0x0145:
                r0 = r17
                android.os.Parcelable$Creator<android.view.InsetsSourceControl> r1 = android.view.InsetsSourceControl.CREATOR
                java.lang.Object[] r1 = r12.createTypedArray(r1)
                android.view.InsetsSourceControl[] r1 = (android.view.InsetsSourceControl[]) r1
                r14.insetsControlChanged(r0, r1)
                return r16
            L_0x0153:
                r12.enforceInterface(r13)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0167
                android.os.Parcelable$Creator<android.view.InsetsState> r0 = android.view.InsetsState.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r17 = r0
                android.view.InsetsState r17 = (android.view.InsetsState) r17
                goto L_0x0168
            L_0x0167:
            L_0x0168:
                r0 = r17
                r14.insetsChanged(r0)
                return r16
            L_0x016e:
                r12.enforceInterface(r13)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0180
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                goto L_0x0182
            L_0x0180:
                r1 = r17
            L_0x0182:
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0191
                android.os.Parcelable$Creator<android.graphics.Rect> r2 = android.graphics.Rect.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r12)
                android.graphics.Rect r2 = (android.graphics.Rect) r2
                goto L_0x0193
            L_0x0191:
                r2 = r17
            L_0x0193:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x01a2
                android.os.Parcelable$Creator<android.graphics.Rect> r3 = android.graphics.Rect.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r12)
                android.graphics.Rect r3 = (android.graphics.Rect) r3
                goto L_0x01a4
            L_0x01a2:
                r3 = r17
            L_0x01a4:
                int r4 = r21.readInt()
                if (r4 == 0) goto L_0x01b3
                android.os.Parcelable$Creator<android.graphics.Rect> r4 = android.graphics.Rect.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r12)
                android.graphics.Rect r4 = (android.graphics.Rect) r4
                goto L_0x01b5
            L_0x01b3:
                r4 = r17
            L_0x01b5:
                int r5 = r21.readInt()
                if (r5 == 0) goto L_0x01c4
                android.os.Parcelable$Creator<android.graphics.Rect> r5 = android.graphics.Rect.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r12)
                android.graphics.Rect r5 = (android.graphics.Rect) r5
                goto L_0x01c6
            L_0x01c4:
                r5 = r17
            L_0x01c6:
                int r6 = r21.readInt()
                if (r6 == 0) goto L_0x01d5
                android.os.Parcelable$Creator<android.graphics.Rect> r6 = android.graphics.Rect.CREATOR
                java.lang.Object r6 = r6.createFromParcel(r12)
                android.graphics.Rect r6 = (android.graphics.Rect) r6
                goto L_0x01d7
            L_0x01d5:
                r6 = r17
            L_0x01d7:
                int r7 = r21.readInt()
                if (r7 == 0) goto L_0x01e0
                r7 = r16
                goto L_0x01e1
            L_0x01e0:
                r7 = r0
            L_0x01e1:
                int r8 = r21.readInt()
                if (r8 == 0) goto L_0x01f0
                android.os.Parcelable$Creator<android.util.MergedConfiguration> r8 = android.util.MergedConfiguration.CREATOR
                java.lang.Object r8 = r8.createFromParcel(r12)
                android.util.MergedConfiguration r8 = (android.util.MergedConfiguration) r8
                goto L_0x01f2
            L_0x01f0:
                r8 = r17
            L_0x01f2:
                int r9 = r21.readInt()
                if (r9 == 0) goto L_0x0201
                android.os.Parcelable$Creator<android.graphics.Rect> r9 = android.graphics.Rect.CREATOR
                java.lang.Object r9 = r9.createFromParcel(r12)
                android.graphics.Rect r9 = (android.graphics.Rect) r9
                goto L_0x0203
            L_0x0201:
                r9 = r17
            L_0x0203:
                int r10 = r21.readInt()
                if (r10 == 0) goto L_0x020c
                r10 = r16
                goto L_0x020d
            L_0x020c:
                r10 = r0
            L_0x020d:
                int r11 = r21.readInt()
                if (r11 == 0) goto L_0x0216
                r11 = r16
                goto L_0x0217
            L_0x0216:
                r11 = r0
            L_0x0217:
                int r18 = r21.readInt()
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x022a
                android.os.Parcelable$Creator<android.view.DisplayCutout$ParcelableWrapper> r0 = android.view.DisplayCutout.ParcelableWrapper.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.view.DisplayCutout$ParcelableWrapper r0 = (android.view.DisplayCutout.ParcelableWrapper) r0
                goto L_0x022c
            L_0x022a:
                r0 = r17
            L_0x022c:
                r15 = r13
                r13 = r0
                r0 = r19
                r14 = r12
                r12 = r18
                r0.resized(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
                return r16
            L_0x0237:
                r14 = r12
                r15 = r13
                r14.enforceInterface(r15)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0255
                android.os.Parcelable$Creator<android.os.ParcelFileDescriptor> r2 = android.os.ParcelFileDescriptor.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                r17 = r2
                android.os.ParcelFileDescriptor r17 = (android.os.ParcelFileDescriptor) r17
                goto L_0x0256
            L_0x0255:
            L_0x0256:
                r2 = r17
                r3 = r19
                r3.executeCommand(r0, r1, r2)
                return r16
            L_0x025e:
                r15 = r13
                r3 = r14
                r0 = r22
                r0.writeString(r15)
                return r16
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.IWindow.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWindow {
            public static IWindow sDefaultImpl;
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

            public void executeCommand(String command, String parameters, ParcelFileDescriptor descriptor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(command);
                    _data.writeString(parameters);
                    if (descriptor != null) {
                        _data.writeInt(1);
                        descriptor.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().executeCommand(command, parameters, descriptor);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void resized(Rect frame, Rect overscanInsets, Rect contentInsets, Rect visibleInsets, Rect stableInsets, Rect outsets, boolean reportDraw, MergedConfiguration newMergedConfiguration, Rect backDropFrame, boolean forceLayout, boolean alwaysConsumeSystemBars, int displayId, DisplayCutout.ParcelableWrapper displayCutout) throws RemoteException {
                Parcel _data;
                Rect rect = frame;
                Rect rect2 = overscanInsets;
                Rect rect3 = contentInsets;
                Rect rect4 = visibleInsets;
                Rect rect5 = stableInsets;
                Rect rect6 = outsets;
                MergedConfiguration mergedConfiguration = newMergedConfiguration;
                Rect rect7 = backDropFrame;
                DisplayCutout.ParcelableWrapper parcelableWrapper = displayCutout;
                Parcel _data2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        try {
                            _data2.writeInt(1);
                            rect.writeToParcel(_data2, 0);
                        } catch (Throwable th) {
                            th = th;
                            _data = _data2;
                        }
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect2 != null) {
                        _data2.writeInt(1);
                        rect2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect3 != null) {
                        _data2.writeInt(1);
                        rect3.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect4 != null) {
                        _data2.writeInt(1);
                        rect4.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect5 != null) {
                        _data2.writeInt(1);
                        rect5.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect6 != null) {
                        _data2.writeInt(1);
                        rect6.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(reportDraw ? 1 : 0);
                    if (mergedConfiguration != null) {
                        _data2.writeInt(1);
                        mergedConfiguration.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect7 != null) {
                        _data2.writeInt(1);
                        rect7.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(forceLayout ? 1 : 0);
                    _data2.writeInt(alwaysConsumeSystemBars ? 1 : 0);
                    _data2.writeInt(displayId);
                    if (parcelableWrapper != null) {
                        _data2.writeInt(1);
                        parcelableWrapper.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data2, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data2.recycle();
                        return;
                    }
                    _data = _data2;
                    try {
                        Stub.getDefaultImpl().resized(frame, overscanInsets, contentInsets, visibleInsets, stableInsets, outsets, reportDraw, newMergedConfiguration, backDropFrame, forceLayout, alwaysConsumeSystemBars, displayId, displayCutout);
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

            public void insetsChanged(InsetsState insetsState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (insetsState != null) {
                        _data.writeInt(1);
                        insetsState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().insetsChanged(insetsState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] activeControls) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (insetsState != null) {
                        _data.writeInt(1);
                        insetsState.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeTypedArray(activeControls, 0);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().insetsControlChanged(insetsState, activeControls);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void moved(int newX, int newY) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newX);
                    _data.writeInt(newY);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().moved(newX, newY);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchAppVisibility(boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchAppVisibility(visible);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchGetNewSurface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchGetNewSurface();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void windowFocusChanged(boolean hasFocus, boolean inTouchMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hasFocus);
                    _data.writeInt(inTouchMode);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().windowFocusChanged(hasFocus, inTouchMode);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void closeSystemDialogs(String reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(reason);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().closeSystemDialogs(reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchWallpaperOffsets(float x, float y, float xStep, float yStep, boolean sync) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(x);
                    _data.writeFloat(y);
                    _data.writeFloat(xStep);
                    _data.writeFloat(yStep);
                    _data.writeInt(sync);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchWallpaperOffsets(x, y, xStep, yStep, sync);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchWallpaperCommand(String action, int x, int y, int z, Bundle extras, boolean sync) throws RemoteException {
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(action);
                    } catch (Throwable th) {
                        th = th;
                        int i = x;
                        int i2 = y;
                        int i3 = z;
                        boolean z2 = sync;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(x);
                        try {
                            _data.writeInt(y);
                            try {
                                _data.writeInt(z);
                                if (bundle != null) {
                                    _data.writeInt(1);
                                    bundle.writeToParcel(_data, 0);
                                } else {
                                    _data.writeInt(0);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                boolean z22 = sync;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i32 = z;
                            boolean z222 = sync;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i22 = y;
                        int i322 = z;
                        boolean z2222 = sync;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(sync ? 1 : 0);
                        try {
                            if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().dispatchWallpaperCommand(action, x, y, z, extras, sync);
                            _data.recycle();
                        } catch (Throwable th5) {
                            th = th5;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    String str = action;
                    int i4 = x;
                    int i222 = y;
                    int i3222 = z;
                    boolean z22222 = sync;
                    _data.recycle();
                    throw th;
                }
            }

            public void dispatchDragEvent(DragEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchDragEvent(event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updatePointerIcon(float x, float y) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(x);
                    _data.writeFloat(y);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updatePointerIcon(x, y);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchSystemUiVisibilityChanged(int seq, int globalVisibility, int localValue, int localChanges) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(seq);
                    _data.writeInt(globalVisibility);
                    _data.writeInt(localValue);
                    _data.writeInt(localChanges);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchSystemUiVisibilityChanged(seq, globalVisibility, localValue, localChanges);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchWindowShown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchWindowShown();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void requestAppKeyboardShortcuts(IResultReceiver receiver, int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    _data.writeInt(deviceId);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestAppKeyboardShortcuts(receiver, deviceId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchPointerCaptureChanged(boolean hasCapture) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(hasCapture);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchPointerCaptureChanged(hasCapture);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWindow impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWindow getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
