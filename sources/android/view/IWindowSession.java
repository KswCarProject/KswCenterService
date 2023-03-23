package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.MergedConfiguration;
import android.view.DisplayCutout;
import android.view.IWindowId;
import android.view.WindowManager;
import java.util.List;

public interface IWindowSession extends IInterface {
    int addToDisplay(IWindow iWindow, int i, WindowManager.LayoutParams layoutParams, int i2, int i3, Rect rect, Rect rect2, Rect rect3, Rect rect4, DisplayCutout.ParcelableWrapper parcelableWrapper, InputChannel inputChannel, InsetsState insetsState) throws RemoteException;

    int addToDisplayWithoutInputChannel(IWindow iWindow, int i, WindowManager.LayoutParams layoutParams, int i2, int i3, Rect rect, Rect rect2, InsetsState insetsState) throws RemoteException;

    void cancelDragAndDrop(IBinder iBinder, boolean z) throws RemoteException;

    void dragRecipientEntered(IWindow iWindow) throws RemoteException;

    void dragRecipientExited(IWindow iWindow) throws RemoteException;

    @UnsupportedAppUsage
    void finishDrawing(IWindow iWindow) throws RemoteException;

    void finishMovingTask(IWindow iWindow) throws RemoteException;

    void getDisplayFrame(IWindow iWindow, Rect rect) throws RemoteException;

    @UnsupportedAppUsage
    boolean getInTouchMode() throws RemoteException;

    IWindowId getWindowId(IBinder iBinder) throws RemoteException;

    void insetsModified(IWindow iWindow, InsetsState insetsState) throws RemoteException;

    void onRectangleOnScreenRequested(IBinder iBinder, Rect rect) throws RemoteException;

    boolean outOfMemory(IWindow iWindow) throws RemoteException;

    @UnsupportedAppUsage
    IBinder performDrag(IWindow iWindow, int i, SurfaceControl surfaceControl, int i2, float f, float f2, float f3, float f4, ClipData clipData) throws RemoteException;

    @UnsupportedAppUsage
    boolean performHapticFeedback(int i, boolean z) throws RemoteException;

    void pokeDrawLock(IBinder iBinder) throws RemoteException;

    void prepareToReplaceWindows(IBinder iBinder, boolean z) throws RemoteException;

    int relayout(IWindow iWindow, int i, WindowManager.LayoutParams layoutParams, int i2, int i3, int i4, int i5, long j, Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, Rect rect6, Rect rect7, DisplayCutout.ParcelableWrapper parcelableWrapper, MergedConfiguration mergedConfiguration, SurfaceControl surfaceControl, InsetsState insetsState) throws RemoteException;

    @UnsupportedAppUsage
    void remove(IWindow iWindow) throws RemoteException;

    void reparentDisplayContent(IWindow iWindow, SurfaceControl surfaceControl, int i) throws RemoteException;

    void reportDropResult(IWindow iWindow, boolean z) throws RemoteException;

    void reportSystemGestureExclusionChanged(IWindow iWindow, List<Rect> list) throws RemoteException;

    Bundle sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle, boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setInTouchMode(boolean z) throws RemoteException;

    void setInsets(IWindow iWindow, int i, Rect rect, Rect rect2, Region region) throws RemoteException;

    @UnsupportedAppUsage
    void setTransparentRegion(IWindow iWindow, Region region) throws RemoteException;

    void setWallpaperDisplayOffset(IBinder iBinder, int i, int i2) throws RemoteException;

    void setWallpaperPosition(IBinder iBinder, float f, float f2, float f3, float f4) throws RemoteException;

    boolean startMovingTask(IWindow iWindow, float f, float f2) throws RemoteException;

    void updateDisplayContentLocation(IWindow iWindow, int i, int i2, int i3) throws RemoteException;

    void updatePointerIcon(IWindow iWindow) throws RemoteException;

    void updateTapExcludeRegion(IWindow iWindow, int i, Region region) throws RemoteException;

    @UnsupportedAppUsage
    void wallpaperCommandComplete(IBinder iBinder, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void wallpaperOffsetsComplete(IBinder iBinder) throws RemoteException;

    public static class Default implements IWindowSession {
        public int addToDisplay(IWindow window, int seq, WindowManager.LayoutParams attrs, int viewVisibility, int layerStackId, Rect outFrame, Rect outContentInsets, Rect outStableInsets, Rect outOutsets, DisplayCutout.ParcelableWrapper displayCutout, InputChannel outInputChannel, InsetsState insetsState) throws RemoteException {
            return 0;
        }

        public int addToDisplayWithoutInputChannel(IWindow window, int seq, WindowManager.LayoutParams attrs, int viewVisibility, int layerStackId, Rect outContentInsets, Rect outStableInsets, InsetsState insetsState) throws RemoteException {
            return 0;
        }

        public void remove(IWindow window) throws RemoteException {
        }

        public int relayout(IWindow window, int seq, WindowManager.LayoutParams attrs, int requestedWidth, int requestedHeight, int viewVisibility, int flags, long frameNumber, Rect outFrame, Rect outOverscanInsets, Rect outContentInsets, Rect outVisibleInsets, Rect outStableInsets, Rect outOutsets, Rect outBackdropFrame, DisplayCutout.ParcelableWrapper displayCutout, MergedConfiguration outMergedConfiguration, SurfaceControl outSurfaceControl, InsetsState insetsState) throws RemoteException {
            return 0;
        }

        public void prepareToReplaceWindows(IBinder appToken, boolean childrenOnly) throws RemoteException {
        }

        public boolean outOfMemory(IWindow window) throws RemoteException {
            return false;
        }

        public void setTransparentRegion(IWindow window, Region region) throws RemoteException {
        }

        public void setInsets(IWindow window, int touchableInsets, Rect contentInsets, Rect visibleInsets, Region touchableRegion) throws RemoteException {
        }

        public void getDisplayFrame(IWindow window, Rect outDisplayFrame) throws RemoteException {
        }

        public void finishDrawing(IWindow window) throws RemoteException {
        }

        public void setInTouchMode(boolean showFocus) throws RemoteException {
        }

        public boolean getInTouchMode() throws RemoteException {
            return false;
        }

        public boolean performHapticFeedback(int effectId, boolean always) throws RemoteException {
            return false;
        }

        public IBinder performDrag(IWindow window, int flags, SurfaceControl surface, int touchSource, float touchX, float touchY, float thumbCenterX, float thumbCenterY, ClipData data) throws RemoteException {
            return null;
        }

        public void reportDropResult(IWindow window, boolean consumed) throws RemoteException {
        }

        public void cancelDragAndDrop(IBinder dragToken, boolean skipAnimation) throws RemoteException {
        }

        public void dragRecipientEntered(IWindow window) throws RemoteException {
        }

        public void dragRecipientExited(IWindow window) throws RemoteException {
        }

        public void setWallpaperPosition(IBinder windowToken, float x, float y, float xstep, float ystep) throws RemoteException {
        }

        public void wallpaperOffsetsComplete(IBinder window) throws RemoteException {
        }

        public void setWallpaperDisplayOffset(IBinder windowToken, int x, int y) throws RemoteException {
        }

        public Bundle sendWallpaperCommand(IBinder window, String action, int x, int y, int z, Bundle extras, boolean sync) throws RemoteException {
            return null;
        }

        public void wallpaperCommandComplete(IBinder window, Bundle result) throws RemoteException {
        }

        public void onRectangleOnScreenRequested(IBinder token, Rect rectangle) throws RemoteException {
        }

        public IWindowId getWindowId(IBinder window) throws RemoteException {
            return null;
        }

        public void pokeDrawLock(IBinder window) throws RemoteException {
        }

        public boolean startMovingTask(IWindow window, float startX, float startY) throws RemoteException {
            return false;
        }

        public void finishMovingTask(IWindow window) throws RemoteException {
        }

        public void updatePointerIcon(IWindow window) throws RemoteException {
        }

        public void reparentDisplayContent(IWindow window, SurfaceControl sc, int displayId) throws RemoteException {
        }

        public void updateDisplayContentLocation(IWindow window, int x, int y, int displayId) throws RemoteException {
        }

        public void updateTapExcludeRegion(IWindow window, int regionId, Region region) throws RemoteException {
        }

        public void insetsModified(IWindow window, InsetsState state) throws RemoteException {
        }

        public void reportSystemGestureExclusionChanged(IWindow window, List<Rect> list) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWindowSession {
        private static final String DESCRIPTOR = "android.view.IWindowSession";
        static final int TRANSACTION_addToDisplay = 1;
        static final int TRANSACTION_addToDisplayWithoutInputChannel = 2;
        static final int TRANSACTION_cancelDragAndDrop = 16;
        static final int TRANSACTION_dragRecipientEntered = 17;
        static final int TRANSACTION_dragRecipientExited = 18;
        static final int TRANSACTION_finishDrawing = 10;
        static final int TRANSACTION_finishMovingTask = 28;
        static final int TRANSACTION_getDisplayFrame = 9;
        static final int TRANSACTION_getInTouchMode = 12;
        static final int TRANSACTION_getWindowId = 25;
        static final int TRANSACTION_insetsModified = 33;
        static final int TRANSACTION_onRectangleOnScreenRequested = 24;
        static final int TRANSACTION_outOfMemory = 6;
        static final int TRANSACTION_performDrag = 14;
        static final int TRANSACTION_performHapticFeedback = 13;
        static final int TRANSACTION_pokeDrawLock = 26;
        static final int TRANSACTION_prepareToReplaceWindows = 5;
        static final int TRANSACTION_relayout = 4;
        static final int TRANSACTION_remove = 3;
        static final int TRANSACTION_reparentDisplayContent = 30;
        static final int TRANSACTION_reportDropResult = 15;
        static final int TRANSACTION_reportSystemGestureExclusionChanged = 34;
        static final int TRANSACTION_sendWallpaperCommand = 22;
        static final int TRANSACTION_setInTouchMode = 11;
        static final int TRANSACTION_setInsets = 8;
        static final int TRANSACTION_setTransparentRegion = 7;
        static final int TRANSACTION_setWallpaperDisplayOffset = 21;
        static final int TRANSACTION_setWallpaperPosition = 19;
        static final int TRANSACTION_startMovingTask = 27;
        static final int TRANSACTION_updateDisplayContentLocation = 31;
        static final int TRANSACTION_updatePointerIcon = 29;
        static final int TRANSACTION_updateTapExcludeRegion = 32;
        static final int TRANSACTION_wallpaperCommandComplete = 23;
        static final int TRANSACTION_wallpaperOffsetsComplete = 20;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindowSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWindowSession)) {
                return new Proxy(obj);
            }
            return (IWindowSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addToDisplay";
                case 2:
                    return "addToDisplayWithoutInputChannel";
                case 3:
                    return "remove";
                case 4:
                    return "relayout";
                case 5:
                    return "prepareToReplaceWindows";
                case 6:
                    return "outOfMemory";
                case 7:
                    return "setTransparentRegion";
                case 8:
                    return "setInsets";
                case 9:
                    return "getDisplayFrame";
                case 10:
                    return "finishDrawing";
                case 11:
                    return "setInTouchMode";
                case 12:
                    return "getInTouchMode";
                case 13:
                    return "performHapticFeedback";
                case 14:
                    return "performDrag";
                case 15:
                    return "reportDropResult";
                case 16:
                    return "cancelDragAndDrop";
                case 17:
                    return "dragRecipientEntered";
                case 18:
                    return "dragRecipientExited";
                case 19:
                    return "setWallpaperPosition";
                case 20:
                    return "wallpaperOffsetsComplete";
                case 21:
                    return "setWallpaperDisplayOffset";
                case 22:
                    return "sendWallpaperCommand";
                case 23:
                    return "wallpaperCommandComplete";
                case 24:
                    return "onRectangleOnScreenRequested";
                case 25:
                    return "getWindowId";
                case 26:
                    return "pokeDrawLock";
                case 27:
                    return "startMovingTask";
                case 28:
                    return "finishMovingTask";
                case 29:
                    return "updatePointerIcon";
                case 30:
                    return "reparentDisplayContent";
                case 31:
                    return "updateDisplayContentLocation";
                case 32:
                    return "updateTapExcludeRegion";
                case 33:
                    return "insetsModified";
                case 34:
                    return "reportSystemGestureExclusionChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v38, resolved type: android.graphics.Region} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v78, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v82, resolved type: android.graphics.Rect} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v95, resolved type: android.view.SurfaceControl} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v101, resolved type: android.graphics.Region} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v105, resolved type: android.view.InsetsState} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v3 */
        /* JADX WARNING: type inference failed for: r0v16 */
        /* JADX WARNING: type inference failed for: r0v27 */
        /* JADX WARNING: type inference failed for: r0v42 */
        /* JADX WARNING: type inference failed for: r0v55 */
        /* JADX WARNING: type inference failed for: r0v71 */
        /* JADX WARNING: type inference failed for: r0v86, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v112 */
        /* JADX WARNING: type inference failed for: r0v113 */
        /* JADX WARNING: type inference failed for: r0v114 */
        /* JADX WARNING: type inference failed for: r0v115 */
        /* JADX WARNING: type inference failed for: r0v116 */
        /* JADX WARNING: type inference failed for: r0v117 */
        /* JADX WARNING: type inference failed for: r0v118 */
        /* JADX WARNING: type inference failed for: r0v119 */
        /* JADX WARNING: type inference failed for: r0v120 */
        /* JADX WARNING: type inference failed for: r0v121 */
        /* JADX WARNING: type inference failed for: r0v122 */
        /* JADX WARNING: type inference failed for: r0v123 */
        /* JADX WARNING: type inference failed for: r0v124 */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r54, android.os.Parcel r55, android.os.Parcel r56, int r57) throws android.os.RemoteException {
            /*
                r53 = this;
                r15 = r53
                r14 = r54
                r13 = r55
                r12 = r56
                java.lang.String r11 = "android.view.IWindowSession"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r14 == r0) goto L_0x0625
                r8 = 0
                r0 = 0
                switch(r14) {
                    case 1: goto L_0x055d;
                    case 2: goto L_0x04e4;
                    case 3: goto L_0x04c8;
                    case 4: goto L_0x03b8;
                    case 5: goto L_0x03a1;
                    case 6: goto L_0x038b;
                    case 7: goto L_0x0369;
                    case 8: goto L_0x031c;
                    case 9: goto L_0x02fe;
                    case 10: goto L_0x02ec;
                    case 11: goto L_0x02d9;
                    case 12: goto L_0x02cb;
                    case 13: goto L_0x02b0;
                    case 14: goto L_0x0250;
                    case 15: goto L_0x0235;
                    case 16: goto L_0x021e;
                    case 17: goto L_0x020c;
                    case 18: goto L_0x01fa;
                    case 19: goto L_0x01d4;
                    case 20: goto L_0x01c6;
                    case 21: goto L_0x01b0;
                    case 22: goto L_0x0160;
                    case 23: goto L_0x0142;
                    case 24: goto L_0x0124;
                    case 25: goto L_0x010b;
                    case 26: goto L_0x00fd;
                    case 27: goto L_0x00df;
                    case 28: goto L_0x00cd;
                    case 29: goto L_0x00bb;
                    case 30: goto L_0x0095;
                    case 31: goto L_0x0077;
                    case 32: goto L_0x0051;
                    case 33: goto L_0x002f;
                    case 34: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r54, r55, r56, r57)
                return r0
            L_0x001a:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.util.ArrayList r1 = r13.createTypedArrayList(r1)
                r15.reportSystemGestureExclusionChanged(r0, r1)
                return r10
            L_0x002f:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r1 = android.view.IWindow.Stub.asInterface(r1)
                int r2 = r55.readInt()
                if (r2 == 0) goto L_0x0049
                android.os.Parcelable$Creator<android.view.InsetsState> r0 = android.view.InsetsState.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.InsetsState r0 = (android.view.InsetsState) r0
                goto L_0x004a
            L_0x0049:
            L_0x004a:
                r15.insetsModified(r1, r0)
                r56.writeNoException()
                return r10
            L_0x0051:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r1 = android.view.IWindow.Stub.asInterface(r1)
                int r2 = r55.readInt()
                int r3 = r55.readInt()
                if (r3 == 0) goto L_0x006f
                android.os.Parcelable$Creator<android.graphics.Region> r0 = android.graphics.Region.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Region r0 = (android.graphics.Region) r0
                goto L_0x0070
            L_0x006f:
            L_0x0070:
                r15.updateTapExcludeRegion(r1, r2, r0)
                r56.writeNoException()
                return r10
            L_0x0077:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                int r1 = r55.readInt()
                int r2 = r55.readInt()
                int r3 = r55.readInt()
                r15.updateDisplayContentLocation(r0, r1, r2, r3)
                r56.writeNoException()
                return r10
            L_0x0095:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r1 = android.view.IWindow.Stub.asInterface(r1)
                int r2 = r55.readInt()
                if (r2 == 0) goto L_0x00af
                android.os.Parcelable$Creator<android.view.SurfaceControl> r0 = android.view.SurfaceControl.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.SurfaceControl r0 = (android.view.SurfaceControl) r0
                goto L_0x00b0
            L_0x00af:
            L_0x00b0:
                int r2 = r55.readInt()
                r15.reparentDisplayContent(r1, r0, r2)
                r56.writeNoException()
                return r10
            L_0x00bb:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                r15.updatePointerIcon(r0)
                r56.writeNoException()
                return r10
            L_0x00cd:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                r15.finishMovingTask(r0)
                r56.writeNoException()
                return r10
            L_0x00df:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                float r1 = r55.readFloat()
                float r2 = r55.readFloat()
                boolean r3 = r15.startMovingTask(r0, r1, r2)
                r56.writeNoException()
                r12.writeInt(r3)
                return r10
            L_0x00fd:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                r15.pokeDrawLock(r0)
                r56.writeNoException()
                return r10
            L_0x010b:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindowId r2 = r15.getWindowId(r1)
                r56.writeNoException()
                if (r2 == 0) goto L_0x0120
                android.os.IBinder r0 = r2.asBinder()
            L_0x0120:
                r12.writeStrongBinder(r0)
                return r10
            L_0x0124:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                int r2 = r55.readInt()
                if (r2 == 0) goto L_0x013a
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                goto L_0x013b
            L_0x013a:
            L_0x013b:
                r15.onRectangleOnScreenRequested(r1, r0)
                r56.writeNoException()
                return r10
            L_0x0142:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                int r2 = r55.readInt()
                if (r2 == 0) goto L_0x0158
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0159
            L_0x0158:
            L_0x0159:
                r15.wallpaperCommandComplete(r1, r0)
                r56.writeNoException()
                return r10
            L_0x0160:
                r13.enforceInterface(r11)
                android.os.IBinder r9 = r55.readStrongBinder()
                java.lang.String r16 = r55.readString()
                int r17 = r55.readInt()
                int r18 = r55.readInt()
                int r19 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x0187
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0185:
                r6 = r0
                goto L_0x0188
            L_0x0187:
                goto L_0x0185
            L_0x0188:
                int r0 = r55.readInt()
                if (r0 == 0) goto L_0x0190
                r7 = r10
                goto L_0x0191
            L_0x0190:
                r7 = r8
            L_0x0191:
                r0 = r53
                r1 = r9
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                android.os.Bundle r0 = r0.sendWallpaperCommand(r1, r2, r3, r4, r5, r6, r7)
                r56.writeNoException()
                if (r0 == 0) goto L_0x01ac
                r12.writeInt(r10)
                r0.writeToParcel(r12, r10)
                goto L_0x01af
            L_0x01ac:
                r12.writeInt(r8)
            L_0x01af:
                return r10
            L_0x01b0:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                int r1 = r55.readInt()
                int r2 = r55.readInt()
                r15.setWallpaperDisplayOffset(r0, r1, r2)
                r56.writeNoException()
                return r10
            L_0x01c6:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                r15.wallpaperOffsetsComplete(r0)
                r56.writeNoException()
                return r10
            L_0x01d4:
                r13.enforceInterface(r11)
                android.os.IBinder r6 = r55.readStrongBinder()
                float r7 = r55.readFloat()
                float r8 = r55.readFloat()
                float r9 = r55.readFloat()
                float r16 = r55.readFloat()
                r0 = r53
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                r0.setWallpaperPosition(r1, r2, r3, r4, r5)
                r56.writeNoException()
                return r10
            L_0x01fa:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                r15.dragRecipientExited(r0)
                r56.writeNoException()
                return r10
            L_0x020c:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                r15.dragRecipientEntered(r0)
                r56.writeNoException()
                return r10
            L_0x021e:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x022d
                r8 = r10
            L_0x022d:
                r1 = r8
                r15.cancelDragAndDrop(r0, r1)
                r56.writeNoException()
                return r10
            L_0x0235:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x0248
                r8 = r10
            L_0x0248:
                r1 = r8
                r15.reportDropResult(r0, r1)
                r56.writeNoException()
                return r10
            L_0x0250:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r16 = android.view.IWindow.Stub.asInterface(r1)
                int r17 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x026f
                android.os.Parcelable$Creator<android.view.SurfaceControl> r1 = android.view.SurfaceControl.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.view.SurfaceControl r1 = (android.view.SurfaceControl) r1
                r3 = r1
                goto L_0x0270
            L_0x026f:
                r3 = r0
            L_0x0270:
                int r18 = r55.readInt()
                float r19 = r55.readFloat()
                float r20 = r55.readFloat()
                float r21 = r55.readFloat()
                float r22 = r55.readFloat()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x0294
                android.os.Parcelable$Creator<android.content.ClipData> r0 = android.content.ClipData.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.content.ClipData r0 = (android.content.ClipData) r0
            L_0x0292:
                r9 = r0
                goto L_0x0295
            L_0x0294:
                goto L_0x0292
            L_0x0295:
                r0 = r53
                r1 = r16
                r2 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                android.os.IBinder r0 = r0.performDrag(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                r56.writeNoException()
                r12.writeStrongBinder(r0)
                return r10
            L_0x02b0:
                r13.enforceInterface(r11)
                int r0 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x02bf
                r8 = r10
            L_0x02bf:
                r1 = r8
                boolean r2 = r15.performHapticFeedback(r0, r1)
                r56.writeNoException()
                r12.writeInt(r2)
                return r10
            L_0x02cb:
                r13.enforceInterface(r11)
                boolean r0 = r53.getInTouchMode()
                r56.writeNoException()
                r12.writeInt(r0)
                return r10
            L_0x02d9:
                r13.enforceInterface(r11)
                int r0 = r55.readInt()
                if (r0 == 0) goto L_0x02e4
                r8 = r10
            L_0x02e4:
                r0 = r8
                r15.setInTouchMode(r0)
                r56.writeNoException()
                return r10
            L_0x02ec:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                r15.finishDrawing(r0)
                r56.writeNoException()
                return r10
            L_0x02fe:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                android.graphics.Rect r1 = new android.graphics.Rect
                r1.<init>()
                r15.getDisplayFrame(r0, r1)
                r56.writeNoException()
                r12.writeInt(r10)
                r1.writeToParcel(r12, r10)
                return r10
            L_0x031c:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r6 = android.view.IWindow.Stub.asInterface(r1)
                int r7 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x033b
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r3 = r1
                goto L_0x033c
            L_0x033b:
                r3 = r0
            L_0x033c:
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x034c
                android.os.Parcelable$Creator<android.graphics.Rect> r1 = android.graphics.Rect.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r13)
                android.graphics.Rect r1 = (android.graphics.Rect) r1
                r4 = r1
                goto L_0x034d
            L_0x034c:
                r4 = r0
            L_0x034d:
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x035d
                android.os.Parcelable$Creator<android.graphics.Region> r0 = android.graphics.Region.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Region r0 = (android.graphics.Region) r0
            L_0x035b:
                r5 = r0
                goto L_0x035e
            L_0x035d:
                goto L_0x035b
            L_0x035e:
                r0 = r53
                r1 = r6
                r2 = r7
                r0.setInsets(r1, r2, r3, r4, r5)
                r56.writeNoException()
                return r10
            L_0x0369:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r1 = android.view.IWindow.Stub.asInterface(r1)
                int r2 = r55.readInt()
                if (r2 == 0) goto L_0x0383
                android.os.Parcelable$Creator<android.graphics.Region> r0 = android.graphics.Region.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.graphics.Region r0 = (android.graphics.Region) r0
                goto L_0x0384
            L_0x0383:
            L_0x0384:
                r15.setTransparentRegion(r1, r0)
                r56.writeNoException()
                return r10
            L_0x038b:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                boolean r1 = r15.outOfMemory(r0)
                r56.writeNoException()
                r12.writeInt(r1)
                return r10
            L_0x03a1:
                r13.enforceInterface(r11)
                android.os.IBinder r0 = r55.readStrongBinder()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x03b0
                r8 = r10
            L_0x03b0:
                r1 = r8
                r15.prepareToReplaceWindows(r0, r1)
                r56.writeNoException()
                return r10
            L_0x03b8:
                r13.enforceInterface(r11)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r21 = android.view.IWindow.Stub.asInterface(r1)
                int r22 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x03d7
                android.os.Parcelable$Creator<android.view.WindowManager$LayoutParams> r0 = android.view.WindowManager.LayoutParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r13)
                android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            L_0x03d5:
                r3 = r0
                goto L_0x03d8
            L_0x03d7:
                goto L_0x03d5
            L_0x03d8:
                int r23 = r55.readInt()
                r4 = r23
                int r24 = r55.readInt()
                r5 = r24
                int r25 = r55.readInt()
                r6 = r25
                int r26 = r55.readInt()
                r7 = r26
                long r27 = r55.readLong()
                r8 = r27
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r2 = r0
                r1 = r10
                r10 = r2
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r29 = r11
                r11 = r0
                android.graphics.Rect r16 = new android.graphics.Rect
                r16.<init>()
                r30 = r16
                r12 = r30
                android.graphics.Rect r16 = new android.graphics.Rect
                r16.<init>()
                r31 = r16
                r13 = r31
                android.graphics.Rect r16 = new android.graphics.Rect
                r16.<init>()
                r32 = r16
                r14 = r32
                android.graphics.Rect r16 = new android.graphics.Rect
                r16.<init>()
                r33 = r16
                r15 = r33
                android.graphics.Rect r16 = new android.graphics.Rect
                r16.<init>()
                r34 = r16
                android.view.DisplayCutout$ParcelableWrapper r17 = new android.view.DisplayCutout$ParcelableWrapper
                r17.<init>()
                r35 = r17
                android.util.MergedConfiguration r18 = new android.util.MergedConfiguration
                r18.<init>()
                r36 = r18
                android.view.SurfaceControl r19 = new android.view.SurfaceControl
                r19.<init>()
                r37 = r19
                android.view.InsetsState r20 = new android.view.InsetsState
                r20.<init>()
                r38 = r20
                r39 = r0
                r0 = r53
                r1 = r21
                r40 = r2
                r2 = r22
                int r0 = r0.relayout(r1, r2, r3, r4, r5, r6, r7, r8, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
                r56.writeNoException()
                r13 = r56
                r13.writeInt(r0)
                r14 = 1
                r13.writeInt(r14)
                r1 = r40
                r1.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r2 = r39
                r2.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r4 = r30
                r4.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r5 = r31
                r5.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r6 = r32
                r6.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r7 = r33
                r7.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r8 = r34
                r8.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r9 = r35
                r9.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r10 = r36
                r10.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r11 = r37
                r11.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r12 = r38
                r12.writeToParcel(r13, r14)
                return r14
            L_0x04c8:
                r14 = r10
                r29 = r11
                r13 = r12
                r12 = r29
                r15 = r55
                r15.enforceInterface(r12)
                android.os.IBinder r0 = r55.readStrongBinder()
                android.view.IWindow r0 = android.view.IWindow.Stub.asInterface(r0)
                r11 = r53
                r11.remove(r0)
                r56.writeNoException()
                return r14
            L_0x04e4:
                r14 = r10
                r52 = r12
                r12 = r11
                r11 = r15
                r15 = r13
                r13 = r52
                r15.enforceInterface(r12)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r9 = android.view.IWindow.Stub.asInterface(r1)
                int r10 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x050b
                android.os.Parcelable$Creator<android.view.WindowManager$LayoutParams> r0 = android.view.WindowManager.LayoutParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            L_0x0509:
                r3 = r0
                goto L_0x050c
            L_0x050b:
                goto L_0x0509
            L_0x050c:
                int r16 = r55.readInt()
                int r17 = r55.readInt()
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r8 = r0
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r7 = r0
                android.view.InsetsState r0 = new android.view.InsetsState
                r0.<init>()
                r6 = r0
                r0 = r53
                r1 = r9
                r2 = r10
                r4 = r16
                r5 = r17
                r41 = r6
                r6 = r8
                r42 = r7
                r43 = r8
                r8 = r41
                int r0 = r0.addToDisplayWithoutInputChannel(r1, r2, r3, r4, r5, r6, r7, r8)
                r56.writeNoException()
                r13.writeInt(r0)
                r13.writeInt(r14)
                r1 = r43
                r1.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r2 = r42
                r2.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r4 = r41
                r4.writeToParcel(r13, r14)
                return r14
            L_0x055d:
                r14 = r10
                r52 = r12
                r12 = r11
                r11 = r15
                r15 = r13
                r13 = r52
                r15.enforceInterface(r12)
                android.os.IBinder r1 = r55.readStrongBinder()
                android.view.IWindow r16 = android.view.IWindow.Stub.asInterface(r1)
                int r17 = r55.readInt()
                int r1 = r55.readInt()
                if (r1 == 0) goto L_0x0584
                android.os.Parcelable$Creator<android.view.WindowManager$LayoutParams> r0 = android.view.WindowManager.LayoutParams.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r15)
                android.view.WindowManager$LayoutParams r0 = (android.view.WindowManager.LayoutParams) r0
            L_0x0582:
                r3 = r0
                goto L_0x0585
            L_0x0584:
                goto L_0x0582
            L_0x0585:
                int r18 = r55.readInt()
                int r19 = r55.readInt()
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r10 = r0
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r9 = r0
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r8 = r0
                android.graphics.Rect r0 = new android.graphics.Rect
                r0.<init>()
                r7 = r0
                android.view.DisplayCutout$ParcelableWrapper r0 = new android.view.DisplayCutout$ParcelableWrapper
                r0.<init>()
                r6 = r0
                android.view.InputChannel r0 = new android.view.InputChannel
                r0.<init>()
                r5 = r0
                android.view.InsetsState r0 = new android.view.InsetsState
                r0.<init>()
                r4 = r0
                r0 = r53
                r1 = r16
                r2 = r17
                r44 = r4
                r4 = r18
                r45 = r5
                r5 = r19
                r46 = r6
                r6 = r10
                r47 = r7
                r7 = r9
                r48 = r8
                r49 = r9
                r9 = r47
                r50 = r10
                r10 = r46
                r11 = r45
                r51 = r12
                r12 = r44
                int r0 = r0.addToDisplay(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
                r56.writeNoException()
                r13.writeInt(r0)
                r13.writeInt(r14)
                r1 = r50
                r1.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r2 = r49
                r2.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r4 = r48
                r4.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r5 = r47
                r5.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r6 = r46
                r6.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r7 = r45
                r7.writeToParcel(r13, r14)
                r13.writeInt(r14)
                r8 = r44
                r8.writeToParcel(r13, r14)
                return r14
            L_0x0625:
                r14 = r10
                r51 = r11
                r15 = r13
                r13 = r12
                r0 = r51
                r13.writeString(r0)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.IWindowSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWindowSession {
            public static IWindowSession sDefaultImpl;
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

            public int addToDisplay(IWindow window, int seq, WindowManager.LayoutParams attrs, int viewVisibility, int layerStackId, Rect outFrame, Rect outContentInsets, Rect outStableInsets, Rect outOutsets, DisplayCutout.ParcelableWrapper displayCutout, InputChannel outInputChannel, InsetsState insetsState) throws RemoteException {
                Parcel _reply;
                IBinder iBinder;
                WindowManager.LayoutParams layoutParams = attrs;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (window != null) {
                        try {
                            iBinder = window.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            Rect rect = outFrame;
                            Rect rect2 = outContentInsets;
                            Rect rect3 = outStableInsets;
                            Rect rect4 = outOutsets;
                            DisplayCutout.ParcelableWrapper parcelableWrapper = displayCutout;
                            InputChannel inputChannel = outInputChannel;
                            InsetsState insetsState2 = insetsState;
                            _reply = _reply2;
                        }
                    } else {
                        iBinder = null;
                    }
                    _data.writeStrongBinder(iBinder);
                    _data.writeInt(seq);
                    if (layoutParams != null) {
                        _data.writeInt(1);
                        layoutParams.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(viewVisibility);
                    _data.writeInt(layerStackId);
                    if (!this.mRemote.transact(1, _data, _reply2, 0)) {
                        try {
                            if (Stub.getDefaultImpl() != null) {
                                Parcel _reply3 = _reply2;
                                try {
                                    int addToDisplay = Stub.getDefaultImpl().addToDisplay(window, seq, attrs, viewVisibility, layerStackId, outFrame, outContentInsets, outStableInsets, outOutsets, displayCutout, outInputChannel, insetsState);
                                    _reply3.recycle();
                                    _data.recycle();
                                    return addToDisplay;
                                } catch (Throwable th2) {
                                    th = th2;
                                    Rect rect5 = outFrame;
                                    Rect rect6 = outContentInsets;
                                    Rect rect7 = outStableInsets;
                                    Rect rect8 = outOutsets;
                                    DisplayCutout.ParcelableWrapper parcelableWrapper2 = displayCutout;
                                    InputChannel inputChannel2 = outInputChannel;
                                    InsetsState insetsState3 = insetsState;
                                    _reply = _reply3;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            Rect rect9 = outFrame;
                            Rect rect10 = outContentInsets;
                            Rect rect11 = outStableInsets;
                            Rect rect12 = outOutsets;
                            DisplayCutout.ParcelableWrapper parcelableWrapper3 = displayCutout;
                            InputChannel inputChannel3 = outInputChannel;
                            InsetsState insetsState4 = insetsState;
                            _reply = _reply2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    }
                    Parcel _reply4 = _reply2;
                    try {
                        _reply4.readException();
                        int _result = _reply4.readInt();
                        if (_reply4.readInt() != 0) {
                            _reply = _reply4;
                            try {
                                outFrame.readFromParcel(_reply);
                            } catch (Throwable th4) {
                                th = th4;
                                Rect rect13 = outContentInsets;
                                Rect rect14 = outStableInsets;
                                Rect rect15 = outOutsets;
                                DisplayCutout.ParcelableWrapper parcelableWrapper4 = displayCutout;
                                InputChannel inputChannel4 = outInputChannel;
                                InsetsState insetsState5 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect16 = outFrame;
                            _reply = _reply4;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outContentInsets.readFromParcel(_reply);
                            } catch (Throwable th5) {
                                th = th5;
                                Rect rect142 = outStableInsets;
                                Rect rect152 = outOutsets;
                                DisplayCutout.ParcelableWrapper parcelableWrapper42 = displayCutout;
                                InputChannel inputChannel42 = outInputChannel;
                                InsetsState insetsState52 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect17 = outContentInsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outStableInsets.readFromParcel(_reply);
                            } catch (Throwable th6) {
                                th = th6;
                                Rect rect1522 = outOutsets;
                                DisplayCutout.ParcelableWrapper parcelableWrapper422 = displayCutout;
                                InputChannel inputChannel422 = outInputChannel;
                                InsetsState insetsState522 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect18 = outStableInsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outOutsets.readFromParcel(_reply);
                            } catch (Throwable th7) {
                                th = th7;
                                DisplayCutout.ParcelableWrapper parcelableWrapper4222 = displayCutout;
                                InputChannel inputChannel4222 = outInputChannel;
                                InsetsState insetsState5222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect19 = outOutsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                displayCutout.readFromParcel(_reply);
                            } catch (Throwable th8) {
                                th = th8;
                                InputChannel inputChannel42222 = outInputChannel;
                                InsetsState insetsState52222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            DisplayCutout.ParcelableWrapper parcelableWrapper5 = displayCutout;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outInputChannel.readFromParcel(_reply);
                            } catch (Throwable th9) {
                                th = th9;
                                InsetsState insetsState522222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            InputChannel inputChannel5 = outInputChannel;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                insetsState.readFromParcel(_reply);
                            } catch (Throwable th10) {
                                th = th10;
                            }
                        } else {
                            InsetsState insetsState6 = insetsState;
                        }
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th11) {
                        th = th11;
                        Rect rect20 = outFrame;
                        Rect rect21 = outContentInsets;
                        Rect rect22 = outStableInsets;
                        Rect rect23 = outOutsets;
                        DisplayCutout.ParcelableWrapper parcelableWrapper6 = displayCutout;
                        InputChannel inputChannel6 = outInputChannel;
                        InsetsState insetsState7 = insetsState;
                        _reply = _reply4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    Rect rect24 = outFrame;
                    Rect rect25 = outContentInsets;
                    Rect rect26 = outStableInsets;
                    Rect rect27 = outOutsets;
                    DisplayCutout.ParcelableWrapper parcelableWrapper7 = displayCutout;
                    InputChannel inputChannel7 = outInputChannel;
                    InsetsState insetsState8 = insetsState;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int addToDisplayWithoutInputChannel(IWindow window, int seq, WindowManager.LayoutParams attrs, int viewVisibility, int layerStackId, Rect outContentInsets, Rect outStableInsets, InsetsState insetsState) throws RemoteException {
                WindowManager.LayoutParams layoutParams = attrs;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    try {
                        _data.writeInt(seq);
                        if (layoutParams != null) {
                            _data.writeInt(1);
                            layoutParams.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        int i = viewVisibility;
                        Rect rect = outContentInsets;
                        Rect rect2 = outStableInsets;
                        InsetsState insetsState2 = insetsState;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(viewVisibility);
                        _data.writeInt(layerStackId);
                        if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            int _result = _reply.readInt();
                            if (_reply.readInt() != 0) {
                                try {
                                    outContentInsets.readFromParcel(_reply);
                                } catch (Throwable th2) {
                                    th = th2;
                                    Rect rect22 = outStableInsets;
                                    InsetsState insetsState22 = insetsState;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } else {
                                Rect rect3 = outContentInsets;
                            }
                            if (_reply.readInt() != 0) {
                                try {
                                    outStableInsets.readFromParcel(_reply);
                                } catch (Throwable th3) {
                                    th = th3;
                                    InsetsState insetsState222 = insetsState;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } else {
                                Rect rect4 = outStableInsets;
                            }
                            if (_reply.readInt() != 0) {
                                try {
                                    insetsState.readFromParcel(_reply);
                                } catch (Throwable th4) {
                                    th = th4;
                                }
                            } else {
                                InsetsState insetsState3 = insetsState;
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        int addToDisplayWithoutInputChannel = Stub.getDefaultImpl().addToDisplayWithoutInputChannel(window, seq, attrs, viewVisibility, layerStackId, outContentInsets, outStableInsets, insetsState);
                        _reply.recycle();
                        _data.recycle();
                        return addToDisplayWithoutInputChannel;
                    } catch (Throwable th5) {
                        th = th5;
                        Rect rect5 = outContentInsets;
                        Rect rect222 = outStableInsets;
                        InsetsState insetsState2222 = insetsState;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i2 = seq;
                    int i3 = viewVisibility;
                    Rect rect52 = outContentInsets;
                    Rect rect2222 = outStableInsets;
                    InsetsState insetsState22222 = insetsState;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void remove(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().remove(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int relayout(IWindow window, int seq, WindowManager.LayoutParams attrs, int requestedWidth, int requestedHeight, int viewVisibility, int flags, long frameNumber, Rect outFrame, Rect outOverscanInsets, Rect outContentInsets, Rect outVisibleInsets, Rect outStableInsets, Rect outOutsets, Rect outBackdropFrame, DisplayCutout.ParcelableWrapper displayCutout, MergedConfiguration outMergedConfiguration, SurfaceControl outSurfaceControl, InsetsState insetsState) throws RemoteException {
                Parcel _data;
                Parcel _reply;
                IBinder iBinder;
                WindowManager.LayoutParams layoutParams = attrs;
                Parcel _data2 = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (window != null) {
                        try {
                            iBinder = window.asBinder();
                        } catch (Throwable th) {
                            th = th;
                            Rect rect = outFrame;
                            Rect rect2 = outOverscanInsets;
                            Rect rect3 = outContentInsets;
                            Rect rect4 = outVisibleInsets;
                            Rect rect5 = outStableInsets;
                            Rect rect6 = outOutsets;
                            Rect rect7 = outBackdropFrame;
                            DisplayCutout.ParcelableWrapper parcelableWrapper = displayCutout;
                            MergedConfiguration mergedConfiguration = outMergedConfiguration;
                            SurfaceControl surfaceControl = outSurfaceControl;
                            _reply = _reply2;
                            _data = _data2;
                        }
                    } else {
                        iBinder = null;
                    }
                    _data2.writeStrongBinder(iBinder);
                    _data2.writeInt(seq);
                    if (layoutParams != null) {
                        _data2.writeInt(1);
                        layoutParams.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(requestedWidth);
                    _data2.writeInt(requestedHeight);
                    _data2.writeInt(viewVisibility);
                    _data2.writeInt(flags);
                    _data2.writeLong(frameNumber);
                    if (!this.mRemote.transact(4, _data2, _reply2, 0)) {
                        try {
                            if (Stub.getDefaultImpl() != null) {
                                Parcel _reply3 = _reply2;
                                _data = _data2;
                                try {
                                    int relayout = Stub.getDefaultImpl().relayout(window, seq, attrs, requestedWidth, requestedHeight, viewVisibility, flags, frameNumber, outFrame, outOverscanInsets, outContentInsets, outVisibleInsets, outStableInsets, outOutsets, outBackdropFrame, displayCutout, outMergedConfiguration, outSurfaceControl, insetsState);
                                    _reply3.recycle();
                                    _data.recycle();
                                    return relayout;
                                } catch (Throwable th2) {
                                    th = th2;
                                    Rect rect8 = outFrame;
                                    Rect rect9 = outOverscanInsets;
                                    Rect rect10 = outContentInsets;
                                    Rect rect11 = outVisibleInsets;
                                    Rect rect12 = outStableInsets;
                                    Rect rect13 = outOutsets;
                                    Rect rect14 = outBackdropFrame;
                                    DisplayCutout.ParcelableWrapper parcelableWrapper2 = displayCutout;
                                    MergedConfiguration mergedConfiguration2 = outMergedConfiguration;
                                    SurfaceControl surfaceControl2 = outSurfaceControl;
                                    InsetsState insetsState2 = insetsState;
                                    _reply = _reply3;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data = _data2;
                            Rect rect15 = outFrame;
                            Rect rect16 = outOverscanInsets;
                            Rect rect17 = outContentInsets;
                            Rect rect18 = outVisibleInsets;
                            Rect rect19 = outStableInsets;
                            Rect rect20 = outOutsets;
                            Rect rect21 = outBackdropFrame;
                            DisplayCutout.ParcelableWrapper parcelableWrapper3 = displayCutout;
                            MergedConfiguration mergedConfiguration3 = outMergedConfiguration;
                            SurfaceControl surfaceControl3 = outSurfaceControl;
                            _reply = _reply2;
                            InsetsState insetsState3 = insetsState;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    }
                    Parcel _reply4 = _reply2;
                    _data = _data2;
                    try {
                        _reply4.readException();
                        int _result = _reply4.readInt();
                        if (_reply4.readInt() != 0) {
                            _reply = _reply4;
                            try {
                                outFrame.readFromParcel(_reply);
                            } catch (Throwable th4) {
                                th = th4;
                                Rect rect22 = outOverscanInsets;
                                Rect rect23 = outContentInsets;
                                Rect rect24 = outVisibleInsets;
                                Rect rect25 = outStableInsets;
                                Rect rect26 = outOutsets;
                                Rect rect27 = outBackdropFrame;
                                DisplayCutout.ParcelableWrapper parcelableWrapper4 = displayCutout;
                                MergedConfiguration mergedConfiguration4 = outMergedConfiguration;
                                SurfaceControl surfaceControl4 = outSurfaceControl;
                                InsetsState insetsState4 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect28 = outFrame;
                            _reply = _reply4;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outOverscanInsets.readFromParcel(_reply);
                            } catch (Throwable th5) {
                                th = th5;
                                Rect rect232 = outContentInsets;
                                Rect rect242 = outVisibleInsets;
                                Rect rect252 = outStableInsets;
                                Rect rect262 = outOutsets;
                                Rect rect272 = outBackdropFrame;
                                DisplayCutout.ParcelableWrapper parcelableWrapper42 = displayCutout;
                                MergedConfiguration mergedConfiguration42 = outMergedConfiguration;
                                SurfaceControl surfaceControl42 = outSurfaceControl;
                                InsetsState insetsState42 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect29 = outOverscanInsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outContentInsets.readFromParcel(_reply);
                            } catch (Throwable th6) {
                                th = th6;
                                Rect rect2422 = outVisibleInsets;
                                Rect rect2522 = outStableInsets;
                                Rect rect2622 = outOutsets;
                                Rect rect2722 = outBackdropFrame;
                                DisplayCutout.ParcelableWrapper parcelableWrapper422 = displayCutout;
                                MergedConfiguration mergedConfiguration422 = outMergedConfiguration;
                                SurfaceControl surfaceControl422 = outSurfaceControl;
                                InsetsState insetsState422 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect30 = outContentInsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outVisibleInsets.readFromParcel(_reply);
                            } catch (Throwable th7) {
                                th = th7;
                                Rect rect25222 = outStableInsets;
                                Rect rect26222 = outOutsets;
                                Rect rect27222 = outBackdropFrame;
                                DisplayCutout.ParcelableWrapper parcelableWrapper4222 = displayCutout;
                                MergedConfiguration mergedConfiguration4222 = outMergedConfiguration;
                                SurfaceControl surfaceControl4222 = outSurfaceControl;
                                InsetsState insetsState4222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect31 = outVisibleInsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outStableInsets.readFromParcel(_reply);
                            } catch (Throwable th8) {
                                th = th8;
                                Rect rect262222 = outOutsets;
                                Rect rect272222 = outBackdropFrame;
                                DisplayCutout.ParcelableWrapper parcelableWrapper42222 = displayCutout;
                                MergedConfiguration mergedConfiguration42222 = outMergedConfiguration;
                                SurfaceControl surfaceControl42222 = outSurfaceControl;
                                InsetsState insetsState42222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect32 = outStableInsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outOutsets.readFromParcel(_reply);
                            } catch (Throwable th9) {
                                th = th9;
                                Rect rect2722222 = outBackdropFrame;
                                DisplayCutout.ParcelableWrapper parcelableWrapper422222 = displayCutout;
                                MergedConfiguration mergedConfiguration422222 = outMergedConfiguration;
                                SurfaceControl surfaceControl422222 = outSurfaceControl;
                                InsetsState insetsState422222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect33 = outOutsets;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outBackdropFrame.readFromParcel(_reply);
                            } catch (Throwable th10) {
                                th = th10;
                                DisplayCutout.ParcelableWrapper parcelableWrapper4222222 = displayCutout;
                                MergedConfiguration mergedConfiguration4222222 = outMergedConfiguration;
                                SurfaceControl surfaceControl4222222 = outSurfaceControl;
                                InsetsState insetsState4222222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            Rect rect34 = outBackdropFrame;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                displayCutout.readFromParcel(_reply);
                            } catch (Throwable th11) {
                                th = th11;
                                MergedConfiguration mergedConfiguration42222222 = outMergedConfiguration;
                                SurfaceControl surfaceControl42222222 = outSurfaceControl;
                                InsetsState insetsState42222222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            DisplayCutout.ParcelableWrapper parcelableWrapper5 = displayCutout;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outMergedConfiguration.readFromParcel(_reply);
                            } catch (Throwable th12) {
                                th = th12;
                                SurfaceControl surfaceControl422222222 = outSurfaceControl;
                                InsetsState insetsState422222222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            MergedConfiguration mergedConfiguration5 = outMergedConfiguration;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                outSurfaceControl.readFromParcel(_reply);
                            } catch (Throwable th13) {
                                th = th13;
                                InsetsState insetsState4222222222 = insetsState;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } else {
                            SurfaceControl surfaceControl5 = outSurfaceControl;
                        }
                        if (_reply.readInt() != 0) {
                            try {
                                insetsState.readFromParcel(_reply);
                            } catch (Throwable th14) {
                                th = th14;
                            }
                        } else {
                            InsetsState insetsState5 = insetsState;
                        }
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th15) {
                        th = th15;
                        Rect rect35 = outFrame;
                        Rect rect36 = outOverscanInsets;
                        Rect rect37 = outContentInsets;
                        Rect rect38 = outVisibleInsets;
                        Rect rect39 = outStableInsets;
                        Rect rect40 = outOutsets;
                        Rect rect41 = outBackdropFrame;
                        DisplayCutout.ParcelableWrapper parcelableWrapper6 = displayCutout;
                        MergedConfiguration mergedConfiguration6 = outMergedConfiguration;
                        SurfaceControl surfaceControl6 = outSurfaceControl;
                        InsetsState insetsState6 = insetsState;
                        _reply = _reply4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th16) {
                    th = th16;
                    Rect rect42 = outFrame;
                    Rect rect43 = outOverscanInsets;
                    Rect rect44 = outContentInsets;
                    Rect rect45 = outVisibleInsets;
                    Rect rect46 = outStableInsets;
                    Rect rect47 = outOutsets;
                    Rect rect48 = outBackdropFrame;
                    DisplayCutout.ParcelableWrapper parcelableWrapper7 = displayCutout;
                    MergedConfiguration mergedConfiguration7 = outMergedConfiguration;
                    SurfaceControl surfaceControl7 = outSurfaceControl;
                    _reply = _reply2;
                    _data = _data2;
                    InsetsState insetsState7 = insetsState;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void prepareToReplaceWindows(IBinder appToken, boolean childrenOnly) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(appToken);
                    _data.writeInt(childrenOnly);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().prepareToReplaceWindows(appToken, childrenOnly);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean outOfMemory(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().outOfMemory(window);
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

            public void setTransparentRegion(IWindow window, Region region) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTransparentRegion(window, region);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInsets(IWindow window, int touchableInsets, Rect contentInsets, Rect visibleInsets, Region touchableRegion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeInt(touchableInsets);
                    if (contentInsets != null) {
                        _data.writeInt(1);
                        contentInsets.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (visibleInsets != null) {
                        _data.writeInt(1);
                        visibleInsets.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (touchableRegion != null) {
                        _data.writeInt(1);
                        touchableRegion.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInsets(window, touchableInsets, contentInsets, visibleInsets, touchableRegion);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getDisplayFrame(IWindow window, Rect outDisplayFrame) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        if (_reply.readInt() != 0) {
                            outDisplayFrame.readFromParcel(_reply);
                        }
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getDisplayFrame(window, outDisplayFrame);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishDrawing(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishDrawing(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setInTouchMode(boolean showFocus) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showFocus);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setInTouchMode(showFocus);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getInTouchMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInTouchMode();
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

            public boolean performHapticFeedback(int effectId, boolean always) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectId);
                    _data.writeInt(always);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().performHapticFeedback(effectId, always);
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

            public IBinder performDrag(IWindow window, int flags, SurfaceControl surface, int touchSource, float touchX, float touchY, float thumbCenterX, float thumbCenterY, ClipData data) throws RemoteException {
                SurfaceControl surfaceControl = surface;
                ClipData clipData = data;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeInt(flags);
                    if (surfaceControl != null) {
                        _data.writeInt(1);
                        surfaceControl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(touchSource);
                    _data.writeFloat(touchX);
                    _data.writeFloat(touchY);
                    _data.writeFloat(thumbCenterX);
                    _data.writeFloat(thumbCenterY);
                    if (clipData != null) {
                        _data.writeInt(1);
                        clipData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().performDrag(window, flags, surface, touchSource, touchX, touchY, thumbCenterX, thumbCenterY, data);
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportDropResult(IWindow window, boolean consumed) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeInt(consumed);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportDropResult(window, consumed);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelDragAndDrop(IBinder dragToken, boolean skipAnimation) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(dragToken);
                    _data.writeInt(skipAnimation);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelDragAndDrop(dragToken, skipAnimation);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dragRecipientEntered(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dragRecipientEntered(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void dragRecipientExited(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dragRecipientExited(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setWallpaperPosition(IBinder windowToken, float x, float y, float xstep, float ystep) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeFloat(x);
                    _data.writeFloat(y);
                    _data.writeFloat(xstep);
                    _data.writeFloat(ystep);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWallpaperPosition(windowToken, x, y, xstep, ystep);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void wallpaperOffsetsComplete(IBinder window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().wallpaperOffsetsComplete(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setWallpaperDisplayOffset(IBinder windowToken, int x, int y) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWallpaperDisplayOffset(windowToken, x, y);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle sendWallpaperCommand(IBinder window, String action, int x, int y, int z, Bundle extras, boolean sync) throws RemoteException {
                Bundle _result;
                Bundle bundle = extras;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(window);
                        try {
                            _data.writeString(action);
                        } catch (Throwable th) {
                            th = th;
                            int i = x;
                            int i2 = y;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(x);
                        } catch (Throwable th2) {
                            th = th2;
                            int i22 = y;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str = action;
                        int i3 = x;
                        int i222 = y;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(y);
                        _data.writeInt(z);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(sync ? 1 : 0);
                        if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                _result = Bundle.CREATOR.createFromParcel(_reply);
                            } else {
                                _result = null;
                            }
                            Bundle _result2 = _result;
                            _reply.recycle();
                            _data.recycle();
                            return _result2;
                        }
                        Bundle sendWallpaperCommand = Stub.getDefaultImpl().sendWallpaperCommand(window, action, x, y, z, extras, sync);
                        _reply.recycle();
                        _data.recycle();
                        return sendWallpaperCommand;
                    } catch (Throwable th4) {
                        th = th4;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    IBinder iBinder = window;
                    String str2 = action;
                    int i32 = x;
                    int i2222 = y;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void wallpaperCommandComplete(IBinder window, Bundle result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window);
                    if (result != null) {
                        _data.writeInt(1);
                        result.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().wallpaperCommandComplete(window, result);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onRectangleOnScreenRequested(IBinder token, Rect rectangle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (rectangle != null) {
                        _data.writeInt(1);
                        rectangle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onRectangleOnScreenRequested(token, rectangle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IWindowId getWindowId(IBinder window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window);
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindowId(window);
                    }
                    _reply.readException();
                    IWindowId _result = IWindowId.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void pokeDrawLock(IBinder window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pokeDrawLock(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startMovingTask(IWindow window, float startX, float startY) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeFloat(startX);
                    _data.writeFloat(startY);
                    boolean z = false;
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startMovingTask(window, startX, startY);
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

            public void finishMovingTask(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishMovingTask(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updatePointerIcon(IWindow window) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updatePointerIcon(window);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reparentDisplayContent(IWindow window, SurfaceControl sc, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (sc != null) {
                        _data.writeInt(1);
                        sc.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reparentDisplayContent(window, sc, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateDisplayContentLocation(IWindow window, int x, int y, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeInt(x);
                    _data.writeInt(y);
                    _data.writeInt(displayId);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateDisplayContentLocation(window, x, y, displayId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateTapExcludeRegion(IWindow window, int regionId, Region region) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeInt(regionId);
                    if (region != null) {
                        _data.writeInt(1);
                        region.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateTapExcludeRegion(window, regionId, region);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void insetsModified(IWindow window, InsetsState state) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().insetsModified(window, state);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void reportSystemGestureExclusionChanged(IWindow window, List<Rect> exclusionRects) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(window != null ? window.asBinder() : null);
                    _data.writeTypedList(exclusionRects);
                    if (this.mRemote.transact(34, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().reportSystemGestureExclusionChanged(window, exclusionRects);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWindowSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWindowSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
