package android.accessibilityservice;

import android.content.pm.ParceledListSlice;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import java.util.List;

public interface IAccessibilityServiceConnection extends IInterface {
    void disableSelf() throws RemoteException;

    String[] findAccessibilityNodeInfoByAccessibilityId(int i, long j, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, long j2, Bundle bundle) throws RemoteException;

    String[] findAccessibilityNodeInfosByText(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    String[] findAccessibilityNodeInfosByViewId(int i, long j, String str, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    String[] findFocus(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    String[] focusSearch(int i, long j, int i2, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    float getMagnificationCenterX(int i) throws RemoteException;

    float getMagnificationCenterY(int i) throws RemoteException;

    Region getMagnificationRegion(int i) throws RemoteException;

    float getMagnificationScale(int i) throws RemoteException;

    AccessibilityServiceInfo getServiceInfo() throws RemoteException;

    int getSoftKeyboardShowMode() throws RemoteException;

    AccessibilityWindowInfo getWindow(int i) throws RemoteException;

    List<AccessibilityWindowInfo> getWindows() throws RemoteException;

    boolean isAccessibilityButtonAvailable() throws RemoteException;

    boolean isFingerprintGestureDetectionAvailable() throws RemoteException;

    boolean performAccessibilityAction(int i, long j, int i2, Bundle bundle, int i3, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, long j2) throws RemoteException;

    boolean performGlobalAction(int i) throws RemoteException;

    boolean resetMagnification(int i, boolean z) throws RemoteException;

    void sendGesture(int i, ParceledListSlice parceledListSlice) throws RemoteException;

    void setMagnificationCallbackEnabled(int i, boolean z) throws RemoteException;

    boolean setMagnificationScaleAndCenter(int i, float f, float f2, float f3, boolean z) throws RemoteException;

    void setOnKeyEventResult(boolean z, int i) throws RemoteException;

    void setServiceInfo(AccessibilityServiceInfo accessibilityServiceInfo) throws RemoteException;

    void setSoftKeyboardCallbackEnabled(boolean z) throws RemoteException;

    boolean setSoftKeyboardShowMode(int i) throws RemoteException;

    public static class Default implements IAccessibilityServiceConnection {
        public void setServiceInfo(AccessibilityServiceInfo info) throws RemoteException {
        }

        public String[] findAccessibilityNodeInfoByAccessibilityId(int accessibilityWindowId, long accessibilityNodeId, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, long threadId, Bundle arguments) throws RemoteException {
            return null;
        }

        public String[] findAccessibilityNodeInfosByText(int accessibilityWindowId, long accessibilityNodeId, String text, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
            return null;
        }

        public String[] findAccessibilityNodeInfosByViewId(int accessibilityWindowId, long accessibilityNodeId, String viewId, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
            return null;
        }

        public String[] findFocus(int accessibilityWindowId, long accessibilityNodeId, int focusType, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
            return null;
        }

        public String[] focusSearch(int accessibilityWindowId, long accessibilityNodeId, int direction, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
            return null;
        }

        public boolean performAccessibilityAction(int accessibilityWindowId, long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
            return false;
        }

        public AccessibilityWindowInfo getWindow(int windowId) throws RemoteException {
            return null;
        }

        public List<AccessibilityWindowInfo> getWindows() throws RemoteException {
            return null;
        }

        public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
            return null;
        }

        public boolean performGlobalAction(int action) throws RemoteException {
            return false;
        }

        public void disableSelf() throws RemoteException {
        }

        public void setOnKeyEventResult(boolean handled, int sequence) throws RemoteException {
        }

        public float getMagnificationScale(int displayId) throws RemoteException {
            return 0.0f;
        }

        public float getMagnificationCenterX(int displayId) throws RemoteException {
            return 0.0f;
        }

        public float getMagnificationCenterY(int displayId) throws RemoteException {
            return 0.0f;
        }

        public Region getMagnificationRegion(int displayId) throws RemoteException {
            return null;
        }

        public boolean resetMagnification(int displayId, boolean animate) throws RemoteException {
            return false;
        }

        public boolean setMagnificationScaleAndCenter(int displayId, float scale, float centerX, float centerY, boolean animate) throws RemoteException {
            return false;
        }

        public void setMagnificationCallbackEnabled(int displayId, boolean enabled) throws RemoteException {
        }

        public boolean setSoftKeyboardShowMode(int showMode) throws RemoteException {
            return false;
        }

        public int getSoftKeyboardShowMode() throws RemoteException {
            return 0;
        }

        public void setSoftKeyboardCallbackEnabled(boolean enabled) throws RemoteException {
        }

        public boolean isAccessibilityButtonAvailable() throws RemoteException {
            return false;
        }

        public void sendGesture(int sequence, ParceledListSlice gestureSteps) throws RemoteException {
        }

        public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccessibilityServiceConnection {
        private static final String DESCRIPTOR = "android.accessibilityservice.IAccessibilityServiceConnection";
        static final int TRANSACTION_disableSelf = 12;
        static final int TRANSACTION_findAccessibilityNodeInfoByAccessibilityId = 2;
        static final int TRANSACTION_findAccessibilityNodeInfosByText = 3;
        static final int TRANSACTION_findAccessibilityNodeInfosByViewId = 4;
        static final int TRANSACTION_findFocus = 5;
        static final int TRANSACTION_focusSearch = 6;
        static final int TRANSACTION_getMagnificationCenterX = 15;
        static final int TRANSACTION_getMagnificationCenterY = 16;
        static final int TRANSACTION_getMagnificationRegion = 17;
        static final int TRANSACTION_getMagnificationScale = 14;
        static final int TRANSACTION_getServiceInfo = 10;
        static final int TRANSACTION_getSoftKeyboardShowMode = 22;
        static final int TRANSACTION_getWindow = 8;
        static final int TRANSACTION_getWindows = 9;
        static final int TRANSACTION_isAccessibilityButtonAvailable = 24;
        static final int TRANSACTION_isFingerprintGestureDetectionAvailable = 26;
        static final int TRANSACTION_performAccessibilityAction = 7;
        static final int TRANSACTION_performGlobalAction = 11;
        static final int TRANSACTION_resetMagnification = 18;
        static final int TRANSACTION_sendGesture = 25;
        static final int TRANSACTION_setMagnificationCallbackEnabled = 20;
        static final int TRANSACTION_setMagnificationScaleAndCenter = 19;
        static final int TRANSACTION_setOnKeyEventResult = 13;
        static final int TRANSACTION_setServiceInfo = 1;
        static final int TRANSACTION_setSoftKeyboardCallbackEnabled = 23;
        static final int TRANSACTION_setSoftKeyboardShowMode = 21;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccessibilityServiceConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccessibilityServiceConnection)) {
                return new Proxy(obj);
            }
            return (IAccessibilityServiceConnection) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setServiceInfo";
                case 2:
                    return "findAccessibilityNodeInfoByAccessibilityId";
                case 3:
                    return "findAccessibilityNodeInfosByText";
                case 4:
                    return "findAccessibilityNodeInfosByViewId";
                case 5:
                    return "findFocus";
                case 6:
                    return "focusSearch";
                case 7:
                    return "performAccessibilityAction";
                case 8:
                    return "getWindow";
                case 9:
                    return "getWindows";
                case 10:
                    return "getServiceInfo";
                case 11:
                    return "performGlobalAction";
                case 12:
                    return "disableSelf";
                case 13:
                    return "setOnKeyEventResult";
                case 14:
                    return "getMagnificationScale";
                case 15:
                    return "getMagnificationCenterX";
                case 16:
                    return "getMagnificationCenterY";
                case 17:
                    return "getMagnificationRegion";
                case 18:
                    return "resetMagnification";
                case 19:
                    return "setMagnificationScaleAndCenter";
                case 20:
                    return "setMagnificationCallbackEnabled";
                case 21:
                    return "setSoftKeyboardShowMode";
                case 22:
                    return "getSoftKeyboardShowMode";
                case 23:
                    return "setSoftKeyboardCallbackEnabled";
                case 24:
                    return "isAccessibilityButtonAvailable";
                case 25:
                    return "sendGesture";
                case 26:
                    return "isFingerprintGestureDetectionAvailable";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.accessibilityservice.AccessibilityServiceInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v51, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v6 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: type inference failed for: r0v57 */
        /* JADX WARNING: type inference failed for: r0v58 */
        /* JADX WARNING: type inference failed for: r0v59 */
        /* JADX WARNING: type inference failed for: r0v60 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r25, android.os.Parcel r26, android.os.Parcel r27, int r28) throws android.os.RemoteException {
            /*
                r24 = this;
                r10 = r24
                r11 = r25
                r12 = r26
                r13 = r27
                java.lang.String r14 = "android.accessibilityservice.IAccessibilityServiceConnection"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r15 = 1
                if (r11 == r0) goto L_0x032d
                r0 = 0
                r1 = 0
                switch(r11) {
                    case 1: goto L_0x0313;
                    case 2: goto L_0x02ca;
                    case 3: goto L_0x0293;
                    case 4: goto L_0x025c;
                    case 5: goto L_0x0225;
                    case 6: goto L_0x01ee;
                    case 7: goto L_0x01a5;
                    case 8: goto L_0x018a;
                    case 9: goto L_0x017c;
                    case 10: goto L_0x0165;
                    case 11: goto L_0x0153;
                    case 12: goto L_0x0149;
                    case 13: goto L_0x0135;
                    case 14: goto L_0x0123;
                    case 15: goto L_0x0111;
                    case 16: goto L_0x00ff;
                    case 17: goto L_0x00e4;
                    case 18: goto L_0x00ca;
                    case 19: goto L_0x009d;
                    case 20: goto L_0x0087;
                    case 21: goto L_0x0075;
                    case 22: goto L_0x0067;
                    case 23: goto L_0x0054;
                    case 24: goto L_0x0046;
                    case 25: goto L_0x0028;
                    case 26: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r25, r26, r27, r28)
                return r0
            L_0x001a:
                r12.enforceInterface(r14)
                boolean r0 = r24.isFingerprintGestureDetectionAvailable()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0028:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x003e
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r0 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.content.pm.ParceledListSlice r0 = (android.content.pm.ParceledListSlice) r0
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                r10.sendGesture(r1, r0)
                r27.writeNoException()
                return r15
            L_0x0046:
                r12.enforceInterface(r14)
                boolean r0 = r24.isAccessibilityButtonAvailable()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0054:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x005f
                r1 = r15
            L_0x005f:
                r0 = r1
                r10.setSoftKeyboardCallbackEnabled(r0)
                r27.writeNoException()
                return r15
            L_0x0067:
                r12.enforceInterface(r14)
                int r0 = r24.getSoftKeyboardShowMode()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0075:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.setSoftKeyboardShowMode(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0087:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x0096
                r1 = r15
            L_0x0096:
                r10.setMagnificationCallbackEnabled(r0, r1)
                r27.writeNoException()
                return r15
            L_0x009d:
                r12.enforceInterface(r14)
                int r6 = r26.readInt()
                float r7 = r26.readFloat()
                float r8 = r26.readFloat()
                float r9 = r26.readFloat()
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x00b8
                r5 = r15
                goto L_0x00b9
            L_0x00b8:
                r5 = r1
            L_0x00b9:
                r0 = r24
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                boolean r0 = r0.setMagnificationScaleAndCenter(r1, r2, r3, r4, r5)
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x00ca:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x00d9
                r1 = r15
            L_0x00d9:
                boolean r2 = r10.resetMagnification(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x00e4:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                android.graphics.Region r2 = r10.getMagnificationRegion(r0)
                r27.writeNoException()
                if (r2 == 0) goto L_0x00fb
                r13.writeInt(r15)
                r2.writeToParcel(r13, r15)
                goto L_0x00fe
            L_0x00fb:
                r13.writeInt(r1)
            L_0x00fe:
                return r15
            L_0x00ff:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                float r1 = r10.getMagnificationCenterY(r0)
                r27.writeNoException()
                r13.writeFloat(r1)
                return r15
            L_0x0111:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                float r1 = r10.getMagnificationCenterX(r0)
                r27.writeNoException()
                r13.writeFloat(r1)
                return r15
            L_0x0123:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                float r1 = r10.getMagnificationScale(r0)
                r27.writeNoException()
                r13.writeFloat(r1)
                return r15
            L_0x0135:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x0140
                r1 = r15
            L_0x0140:
                r0 = r1
                int r1 = r26.readInt()
                r10.setOnKeyEventResult(r0, r1)
                return r15
            L_0x0149:
                r12.enforceInterface(r14)
                r24.disableSelf()
                r27.writeNoException()
                return r15
            L_0x0153:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.performGlobalAction(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0165:
                r12.enforceInterface(r14)
                android.accessibilityservice.AccessibilityServiceInfo r0 = r24.getServiceInfo()
                r27.writeNoException()
                if (r0 == 0) goto L_0x0178
                r13.writeInt(r15)
                r0.writeToParcel(r13, r15)
                goto L_0x017b
            L_0x0178:
                r13.writeInt(r1)
            L_0x017b:
                return r15
            L_0x017c:
                r12.enforceInterface(r14)
                java.util.List r0 = r24.getWindows()
                r27.writeNoException()
                r13.writeTypedList(r0)
                return r15
            L_0x018a:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                android.view.accessibility.AccessibilityWindowInfo r2 = r10.getWindow(r0)
                r27.writeNoException()
                if (r2 == 0) goto L_0x01a1
                r13.writeInt(r15)
                r2.writeToParcel(r13, r15)
                goto L_0x01a4
            L_0x01a1:
                r13.writeInt(r1)
            L_0x01a4:
                return r15
            L_0x01a5:
                r12.enforceInterface(r14)
                int r16 = r26.readInt()
                long r17 = r26.readLong()
                int r19 = r26.readInt()
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x01c4
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x01c2:
                r5 = r0
                goto L_0x01c5
            L_0x01c4:
                goto L_0x01c2
            L_0x01c5:
                int r20 = r26.readInt()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r21 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r0)
                long r22 = r26.readLong()
                r0 = r24
                r1 = r16
                r2 = r17
                r4 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                boolean r0 = r0.performAccessibilityAction(r1, r2, r4, r5, r6, r7, r8)
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x01ee:
                r12.enforceInterface(r14)
                int r9 = r26.readInt()
                long r16 = r26.readLong()
                int r18 = r26.readInt()
                int r19 = r26.readInt()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r20 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r0)
                long r21 = r26.readLong()
                r0 = r24
                r1 = r9
                r2 = r16
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                java.lang.String[] r0 = r0.focusSearch(r1, r2, r4, r5, r6, r7)
                r27.writeNoException()
                r13.writeStringArray(r0)
                return r15
            L_0x0225:
                r12.enforceInterface(r14)
                int r9 = r26.readInt()
                long r16 = r26.readLong()
                int r18 = r26.readInt()
                int r19 = r26.readInt()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r20 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r0)
                long r21 = r26.readLong()
                r0 = r24
                r1 = r9
                r2 = r16
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                java.lang.String[] r0 = r0.findFocus(r1, r2, r4, r5, r6, r7)
                r27.writeNoException()
                r13.writeStringArray(r0)
                return r15
            L_0x025c:
                r12.enforceInterface(r14)
                int r9 = r26.readInt()
                long r16 = r26.readLong()
                java.lang.String r18 = r26.readString()
                int r19 = r26.readInt()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r20 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r0)
                long r21 = r26.readLong()
                r0 = r24
                r1 = r9
                r2 = r16
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                java.lang.String[] r0 = r0.findAccessibilityNodeInfosByViewId(r1, r2, r4, r5, r6, r7)
                r27.writeNoException()
                r13.writeStringArray(r0)
                return r15
            L_0x0293:
                r12.enforceInterface(r14)
                int r9 = r26.readInt()
                long r16 = r26.readLong()
                java.lang.String r18 = r26.readString()
                int r19 = r26.readInt()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r20 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r0)
                long r21 = r26.readLong()
                r0 = r24
                r1 = r9
                r2 = r16
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                java.lang.String[] r0 = r0.findAccessibilityNodeInfosByText(r1, r2, r4, r5, r6, r7)
                r27.writeNoException()
                r13.writeStringArray(r0)
                return r15
            L_0x02ca:
                r12.enforceInterface(r14)
                int r16 = r26.readInt()
                long r17 = r26.readLong()
                int r19 = r26.readInt()
                android.os.IBinder r1 = r26.readStrongBinder()
                android.view.accessibility.IAccessibilityInteractionConnectionCallback r20 = android.view.accessibility.IAccessibilityInteractionConnectionCallback.Stub.asInterface(r1)
                int r21 = r26.readInt()
                long r22 = r26.readLong()
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x02f9
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x02f7:
                r9 = r0
                goto L_0x02fa
            L_0x02f9:
                goto L_0x02f7
            L_0x02fa:
                r0 = r24
                r1 = r16
                r2 = r17
                r4 = r19
                r5 = r20
                r6 = r21
                r7 = r22
                java.lang.String[] r0 = r0.findAccessibilityNodeInfoByAccessibilityId(r1, r2, r4, r5, r6, r7, r9)
                r27.writeNoException()
                r13.writeStringArray(r0)
                return r15
            L_0x0313:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x0325
                android.os.Parcelable$Creator<android.accessibilityservice.AccessibilityServiceInfo> r0 = android.accessibilityservice.AccessibilityServiceInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.accessibilityservice.AccessibilityServiceInfo r0 = (android.accessibilityservice.AccessibilityServiceInfo) r0
                goto L_0x0326
            L_0x0325:
            L_0x0326:
                r10.setServiceInfo(r0)
                r27.writeNoException()
                return r15
            L_0x032d:
                r13.writeString(r14)
                return r15
            */
            throw new UnsupportedOperationException("Method not decompiled: android.accessibilityservice.IAccessibilityServiceConnection.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAccessibilityServiceConnection {
            public static IAccessibilityServiceConnection sDefaultImpl;
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

            public void setServiceInfo(AccessibilityServiceInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        _data.writeInt(1);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setServiceInfo(info);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] findAccessibilityNodeInfoByAccessibilityId(int accessibilityWindowId, long accessibilityNodeId, int interactionId, IAccessibilityInteractionConnectionCallback callback, int flags, long threadId, Bundle arguments) throws RemoteException {
                Bundle bundle = arguments;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(accessibilityWindowId);
                        _data.writeLong(accessibilityNodeId);
                    } catch (Throwable th) {
                        th = th;
                        int i = interactionId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(interactionId);
                        _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                        _data.writeInt(flags);
                        _data.writeLong(threadId);
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            String[] _result = _reply.createStringArray();
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        String[] findAccessibilityNodeInfoByAccessibilityId = Stub.getDefaultImpl().findAccessibilityNodeInfoByAccessibilityId(accessibilityWindowId, accessibilityNodeId, interactionId, callback, flags, threadId, arguments);
                        _reply.recycle();
                        _data.recycle();
                        return findAccessibilityNodeInfoByAccessibilityId;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i2 = accessibilityWindowId;
                    int i3 = interactionId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String[] findAccessibilityNodeInfosByText(int accessibilityWindowId, long accessibilityNodeId, String text, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(accessibilityWindowId);
                        try {
                            _data.writeLong(accessibilityNodeId);
                        } catch (Throwable th) {
                            th = th;
                            String str = text;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(text);
                            _data.writeInt(interactionId);
                            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                            _data.writeLong(threadId);
                            if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String[] _result = _reply.createStringArray();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            String[] findAccessibilityNodeInfosByText = Stub.getDefaultImpl().findAccessibilityNodeInfosByText(accessibilityWindowId, accessibilityNodeId, text, interactionId, callback, threadId);
                            _reply.recycle();
                            _data.recycle();
                            return findAccessibilityNodeInfosByText;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = accessibilityNodeId;
                        String str2 = text;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i = accessibilityWindowId;
                    long j2 = accessibilityNodeId;
                    String str22 = text;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String[] findAccessibilityNodeInfosByViewId(int accessibilityWindowId, long accessibilityNodeId, String viewId, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(accessibilityWindowId);
                        try {
                            _data.writeLong(accessibilityNodeId);
                        } catch (Throwable th) {
                            th = th;
                            String str = viewId;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(viewId);
                            _data.writeInt(interactionId);
                            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                            _data.writeLong(threadId);
                            if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String[] _result = _reply.createStringArray();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            String[] findAccessibilityNodeInfosByViewId = Stub.getDefaultImpl().findAccessibilityNodeInfosByViewId(accessibilityWindowId, accessibilityNodeId, viewId, interactionId, callback, threadId);
                            _reply.recycle();
                            _data.recycle();
                            return findAccessibilityNodeInfosByViewId;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = accessibilityNodeId;
                        String str2 = viewId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i = accessibilityWindowId;
                    long j2 = accessibilityNodeId;
                    String str22 = viewId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String[] findFocus(int accessibilityWindowId, long accessibilityNodeId, int focusType, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(accessibilityWindowId);
                        try {
                            _data.writeLong(accessibilityNodeId);
                        } catch (Throwable th) {
                            th = th;
                            int i = focusType;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(focusType);
                            _data.writeInt(interactionId);
                            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                            _data.writeLong(threadId);
                            if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String[] _result = _reply.createStringArray();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            String[] findFocus = Stub.getDefaultImpl().findFocus(accessibilityWindowId, accessibilityNodeId, focusType, interactionId, callback, threadId);
                            _reply.recycle();
                            _data.recycle();
                            return findFocus;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = accessibilityNodeId;
                        int i2 = focusType;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i3 = accessibilityWindowId;
                    long j2 = accessibilityNodeId;
                    int i22 = focusType;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String[] focusSearch(int accessibilityWindowId, long accessibilityNodeId, int direction, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(accessibilityWindowId);
                        try {
                            _data.writeLong(accessibilityNodeId);
                        } catch (Throwable th) {
                            th = th;
                            int i = direction;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(direction);
                            _data.writeInt(interactionId);
                            _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                            _data.writeLong(threadId);
                            if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String[] _result = _reply.createStringArray();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            String[] focusSearch = Stub.getDefaultImpl().focusSearch(accessibilityWindowId, accessibilityNodeId, direction, interactionId, callback, threadId);
                            _reply.recycle();
                            _data.recycle();
                            return focusSearch;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        long j = accessibilityNodeId;
                        int i2 = direction;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i3 = accessibilityWindowId;
                    long j2 = accessibilityNodeId;
                    int i22 = direction;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean performAccessibilityAction(int accessibilityWindowId, long accessibilityNodeId, int action, Bundle arguments, int interactionId, IAccessibilityInteractionConnectionCallback callback, long threadId) throws RemoteException {
                Bundle bundle = arguments;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(accessibilityWindowId);
                        _data.writeLong(accessibilityNodeId);
                    } catch (Throwable th) {
                        th = th;
                        int i = action;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(action);
                        boolean _result = true;
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(interactionId);
                        _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                        _data.writeLong(threadId);
                        if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() == 0) {
                                _result = false;
                            }
                            _reply.recycle();
                            _data.recycle();
                            return _result;
                        }
                        boolean performAccessibilityAction = Stub.getDefaultImpl().performAccessibilityAction(accessibilityWindowId, accessibilityNodeId, action, arguments, interactionId, callback, threadId);
                        _reply.recycle();
                        _data.recycle();
                        return performAccessibilityAction;
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i2 = accessibilityWindowId;
                    int i3 = action;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public AccessibilityWindowInfo getWindow(int windowId) throws RemoteException {
                AccessibilityWindowInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(windowId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindow(windowId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AccessibilityWindowInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AccessibilityWindowInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AccessibilityWindowInfo> getWindows() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getWindows();
                    }
                    _reply.readException();
                    List<AccessibilityWindowInfo> _result = _reply.createTypedArrayList(AccessibilityWindowInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AccessibilityServiceInfo getServiceInfo() throws RemoteException {
                AccessibilityServiceInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getServiceInfo();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AccessibilityServiceInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AccessibilityServiceInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean performGlobalAction(int action) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(action);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().performGlobalAction(action);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableSelf() throws RemoteException {
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
                    Stub.getDefaultImpl().disableSelf();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setOnKeyEventResult(boolean handled, int sequence) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(handled);
                    _data.writeInt(sequence);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setOnKeyEventResult(handled, sequence);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public float getMagnificationScale(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMagnificationScale(displayId);
                    }
                    _reply.readException();
                    float _result = _reply.readFloat();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getMagnificationCenterX(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMagnificationCenterX(displayId);
                    }
                    _reply.readException();
                    float _result = _reply.readFloat();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public float getMagnificationCenterY(int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMagnificationCenterY(displayId);
                    }
                    _reply.readException();
                    float _result = _reply.readFloat();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Region getMagnificationRegion(int displayId) throws RemoteException {
                Region _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMagnificationRegion(displayId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Region.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Region _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean resetMagnification(int displayId, boolean animate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(animate);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().resetMagnification(displayId, animate);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setMagnificationScaleAndCenter(int displayId, float scale, float centerX, float centerY, boolean animate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(displayId);
                        try {
                            _data.writeFloat(scale);
                            try {
                                _data.writeFloat(centerX);
                                try {
                                    _data.writeFloat(centerY);
                                } catch (Throwable th) {
                                    th = th;
                                    boolean z = animate;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                float f = centerY;
                                boolean z2 = animate;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            float f2 = centerX;
                            float f3 = centerY;
                            boolean z22 = animate;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(animate ? 1 : 0);
                            try {
                                boolean z3 = false;
                                if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        z3 = true;
                                    }
                                    boolean _result = z3;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean magnificationScaleAndCenter = Stub.getDefaultImpl().setMagnificationScaleAndCenter(displayId, scale, centerX, centerY, animate);
                                _reply.recycle();
                                _data.recycle();
                                return magnificationScaleAndCenter;
                            } catch (Throwable th4) {
                                th = th4;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        float f4 = scale;
                        float f22 = centerX;
                        float f32 = centerY;
                        boolean z222 = animate;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i = displayId;
                    float f42 = scale;
                    float f222 = centerX;
                    float f322 = centerY;
                    boolean z2222 = animate;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void setMagnificationCallbackEnabled(int displayId, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(displayId);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMagnificationCallbackEnabled(displayId, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setSoftKeyboardShowMode(int showMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showMode);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setSoftKeyboardShowMode(showMode);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSoftKeyboardShowMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoftKeyboardShowMode();
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

            public void setSoftKeyboardCallbackEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSoftKeyboardCallbackEnabled(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isAccessibilityButtonAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAccessibilityButtonAvailable();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendGesture(int sequence, ParceledListSlice gestureSteps) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sequence);
                    if (gestureSteps != null) {
                        _data.writeInt(1);
                        gestureSteps.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendGesture(sequence, gestureSteps);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isFingerprintGestureDetectionAvailable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isFingerprintGestureDetectionAvailable();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccessibilityServiceConnection impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccessibilityServiceConnection getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
