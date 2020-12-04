package android.view.autofill;

import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.UserData;
import com.android.internal.os.IResultReceiver;
import java.util.List;

public interface IAutoFillManager extends IInterface {
    void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int i, IResultReceiver iResultReceiver) throws RemoteException;

    void cancelSession(int i, int i2) throws RemoteException;

    void disableOwnedAutofillServices(int i) throws RemoteException;

    void finishSession(int i, int i2) throws RemoteException;

    void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException;

    void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) throws RemoteException;

    void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) throws RemoteException;

    void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException;

    void getUserData(IResultReceiver iResultReceiver) throws RemoteException;

    void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException;

    void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException;

    void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver) throws RemoteException;

    void isServiceSupported(int i, IResultReceiver iResultReceiver) throws RemoteException;

    void onPendingSaveUi(int i, IBinder iBinder) throws RemoteException;

    void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) throws RemoteException;

    void restoreSession(int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) throws RemoteException;

    void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver) throws RemoteException;

    void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) throws RemoteException;

    void setAutofillFailure(int i, List<AutofillId> list, int i2) throws RemoteException;

    void setHasCallback(int i, int i2, boolean z) throws RemoteException;

    void setUserData(UserData userData) throws RemoteException;

    void startSession(IBinder iBinder, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, boolean z, int i2, ComponentName componentName, boolean z2, IResultReceiver iResultReceiver) throws RemoteException;

    void updateSession(int i, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i2, int i3, int i4) throws RemoteException;

    public static class Default implements IAutoFillManager {
        public void addClient(IAutoFillManagerClient client, ComponentName componentName, int userId, IResultReceiver result) throws RemoteException {
        }

        public void removeClient(IAutoFillManagerClient client, int userId) throws RemoteException {
        }

        public void startSession(IBinder activityToken, IBinder appCallback, AutofillId autoFillId, Rect bounds, AutofillValue value, int userId, boolean hasCallback, int flags, ComponentName componentName, boolean compatMode, IResultReceiver result) throws RemoteException {
        }

        public void getFillEventHistory(IResultReceiver result) throws RemoteException {
        }

        public void restoreSession(int sessionId, IBinder activityToken, IBinder appCallback, IResultReceiver result) throws RemoteException {
        }

        public void updateSession(int sessionId, AutofillId id, Rect bounds, AutofillValue value, int action, int flags, int userId) throws RemoteException {
        }

        public void setAutofillFailure(int sessionId, List<AutofillId> list, int userId) throws RemoteException {
        }

        public void finishSession(int sessionId, int userId) throws RemoteException {
        }

        public void cancelSession(int sessionId, int userId) throws RemoteException {
        }

        public void setAuthenticationResult(Bundle data, int sessionId, int authenticationId, int userId) throws RemoteException {
        }

        public void setHasCallback(int sessionId, int userId, boolean hasIt) throws RemoteException {
        }

        public void disableOwnedAutofillServices(int userId) throws RemoteException {
        }

        public void isServiceSupported(int userId, IResultReceiver result) throws RemoteException {
        }

        public void isServiceEnabled(int userId, String packageName, IResultReceiver result) throws RemoteException {
        }

        public void onPendingSaveUi(int operation, IBinder token) throws RemoteException {
        }

        public void getUserData(IResultReceiver result) throws RemoteException {
        }

        public void getUserDataId(IResultReceiver result) throws RemoteException {
        }

        public void setUserData(UserData userData) throws RemoteException {
        }

        public void isFieldClassificationEnabled(IResultReceiver result) throws RemoteException {
        }

        public void getAutofillServiceComponentName(IResultReceiver result) throws RemoteException {
        }

        public void getAvailableFieldClassificationAlgorithms(IResultReceiver result) throws RemoteException {
        }

        public void getDefaultFieldClassificationAlgorithm(IResultReceiver result) throws RemoteException {
        }

        public void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver result) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAutoFillManager {
        private static final String DESCRIPTOR = "android.view.autofill.IAutoFillManager";
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_cancelSession = 9;
        static final int TRANSACTION_disableOwnedAutofillServices = 12;
        static final int TRANSACTION_finishSession = 8;
        static final int TRANSACTION_getAutofillServiceComponentName = 20;
        static final int TRANSACTION_getAvailableFieldClassificationAlgorithms = 21;
        static final int TRANSACTION_getDefaultFieldClassificationAlgorithm = 22;
        static final int TRANSACTION_getFillEventHistory = 4;
        static final int TRANSACTION_getUserData = 16;
        static final int TRANSACTION_getUserDataId = 17;
        static final int TRANSACTION_isFieldClassificationEnabled = 19;
        static final int TRANSACTION_isServiceEnabled = 14;
        static final int TRANSACTION_isServiceSupported = 13;
        static final int TRANSACTION_onPendingSaveUi = 15;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_restoreSession = 5;
        static final int TRANSACTION_setAugmentedAutofillWhitelist = 23;
        static final int TRANSACTION_setAuthenticationResult = 10;
        static final int TRANSACTION_setAutofillFailure = 7;
        static final int TRANSACTION_setHasCallback = 11;
        static final int TRANSACTION_setUserData = 18;
        static final int TRANSACTION_startSession = 3;
        static final int TRANSACTION_updateSession = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAutoFillManager)) {
                return new Proxy(obj);
            }
            return (IAutoFillManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addClient";
                case 2:
                    return "removeClient";
                case 3:
                    return "startSession";
                case 4:
                    return "getFillEventHistory";
                case 5:
                    return "restoreSession";
                case 6:
                    return "updateSession";
                case 7:
                    return "setAutofillFailure";
                case 8:
                    return "finishSession";
                case 9:
                    return "cancelSession";
                case 10:
                    return "setAuthenticationResult";
                case 11:
                    return "setHasCallback";
                case 12:
                    return "disableOwnedAutofillServices";
                case 13:
                    return "isServiceSupported";
                case 14:
                    return "isServiceEnabled";
                case 15:
                    return "onPendingSaveUi";
                case 16:
                    return "getUserData";
                case 17:
                    return "getUserDataId";
                case 18:
                    return "setUserData";
                case 19:
                    return "isFieldClassificationEnabled";
                case 20:
                    return "getAutofillServiceComponentName";
                case 21:
                    return "getAvailableFieldClassificationAlgorithms";
                case 22:
                    return "getDefaultFieldClassificationAlgorithm";
                case 23:
                    return "setAugmentedAutofillWhitelist";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v29, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v43, resolved type: android.service.autofill.UserData} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v32, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.content.ComponentName} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: android.content.ComponentName} */
        /* JADX WARNING: type inference failed for: r1v21, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v29, types: [android.service.autofill.UserData] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r23, android.os.Parcel r24, android.os.Parcel r25, int r26) throws android.os.RemoteException {
            /*
                r22 = this;
                r12 = r22
                r13 = r23
                r14 = r24
                java.lang.String r15 = "android.view.autofill.IAutoFillManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r16 = 1
                if (r13 == r0) goto L_0x0294
                r0 = 0
                r1 = 0
                switch(r13) {
                    case 1: goto L_0x0269;
                    case 2: goto L_0x0256;
                    case 3: goto L_0x01d3;
                    case 4: goto L_0x01c4;
                    case 5: goto L_0x01a9;
                    case 6: goto L_0x0159;
                    case 7: goto L_0x0144;
                    case 8: goto L_0x0135;
                    case 9: goto L_0x0126;
                    case 10: goto L_0x0101;
                    case 11: goto L_0x00e9;
                    case 12: goto L_0x00de;
                    case 13: goto L_0x00cb;
                    case 14: goto L_0x00b4;
                    case 15: goto L_0x00a5;
                    case 16: goto L_0x0096;
                    case 17: goto L_0x0087;
                    case 18: goto L_0x006e;
                    case 19: goto L_0x005f;
                    case 20: goto L_0x0050;
                    case 21: goto L_0x0041;
                    case 22: goto L_0x0032;
                    case 23: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r23, r24, r25, r26)
                return r0
            L_0x0019:
                r14.enforceInterface(r15)
                java.util.ArrayList r0 = r24.createStringArrayList()
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.util.ArrayList r1 = r14.createTypedArrayList(r1)
                android.os.IBinder r2 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r2 = com.android.internal.os.IResultReceiver.Stub.asInterface(r2)
                r12.setAugmentedAutofillWhitelist(r0, r1, r2)
                return r16
            L_0x0032:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.getDefaultFieldClassificationAlgorithm(r0)
                return r16
            L_0x0041:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.getAvailableFieldClassificationAlgorithms(r0)
                return r16
            L_0x0050:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.getAutofillServiceComponentName(r0)
                return r16
            L_0x005f:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.isFieldClassificationEnabled(r0)
                return r16
            L_0x006e:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0081
                android.os.Parcelable$Creator<android.service.autofill.UserData> r0 = android.service.autofill.UserData.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r1 = r0
                android.service.autofill.UserData r1 = (android.service.autofill.UserData) r1
                goto L_0x0082
            L_0x0081:
            L_0x0082:
                r0 = r1
                r12.setUserData(r0)
                return r16
            L_0x0087:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.getUserDataId(r0)
                return r16
            L_0x0096:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.getUserData(r0)
                return r16
            L_0x00a5:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                r12.onPendingSaveUi(r0, r1)
                return r16
            L_0x00b4:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                java.lang.String r1 = r24.readString()
                android.os.IBinder r2 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r2 = com.android.internal.os.IResultReceiver.Stub.asInterface(r2)
                r12.isServiceEnabled(r0, r1, r2)
                return r16
            L_0x00cb:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r1 = com.android.internal.os.IResultReceiver.Stub.asInterface(r1)
                r12.isServiceSupported(r0, r1)
                return r16
            L_0x00de:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                r12.disableOwnedAutofillServices(r0)
                return r16
            L_0x00e9:
                r14.enforceInterface(r15)
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x00fd
                r0 = r16
            L_0x00fd:
                r12.setHasCallback(r1, r2, r0)
                return r16
            L_0x0101:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0114
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                r1 = r0
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0115
            L_0x0114:
            L_0x0115:
                r0 = r1
                int r1 = r24.readInt()
                int r2 = r24.readInt()
                int r3 = r24.readInt()
                r12.setAuthenticationResult(r0, r1, r2, r3)
                return r16
            L_0x0126:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                r12.cancelSession(r0, r1)
                return r16
            L_0x0135:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                int r1 = r24.readInt()
                r12.finishSession(r0, r1)
                return r16
            L_0x0144:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.util.ArrayList r1 = r14.createTypedArrayList(r1)
                int r2 = r24.readInt()
                r12.setAutofillFailure(r0, r1, r2)
                return r16
            L_0x0159:
                r14.enforceInterface(r15)
                int r8 = r24.readInt()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0170
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r0 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.view.autofill.AutofillId r0 = (android.view.autofill.AutofillId) r0
                r2 = r0
                goto L_0x0171
            L_0x0170:
                r2 = r1
            L_0x0171:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0181
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r3 = r0
                goto L_0x0182
            L_0x0181:
                r3 = r1
            L_0x0182:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0192
                android.os.Parcelable$Creator<android.view.autofill.AutofillValue> r0 = android.view.autofill.AutofillValue.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.view.autofill.AutofillValue r0 = (android.view.autofill.AutofillValue) r0
                r4 = r0
                goto L_0x0193
            L_0x0192:
                r4 = r1
            L_0x0193:
                int r9 = r24.readInt()
                int r10 = r24.readInt()
                int r11 = r24.readInt()
                r0 = r22
                r1 = r8
                r5 = r9
                r6 = r10
                r7 = r11
                r0.updateSession(r1, r2, r3, r4, r5, r6, r7)
                return r16
            L_0x01a9:
                r14.enforceInterface(r15)
                int r0 = r24.readInt()
                android.os.IBinder r1 = r24.readStrongBinder()
                android.os.IBinder r2 = r24.readStrongBinder()
                android.os.IBinder r3 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r3 = com.android.internal.os.IResultReceiver.Stub.asInterface(r3)
                r12.restoreSession(r0, r1, r2, r3)
                return r16
            L_0x01c4:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r12.getFillEventHistory(r0)
                return r16
            L_0x01d3:
                r14.enforceInterface(r15)
                android.os.IBinder r17 = r24.readStrongBinder()
                android.os.IBinder r18 = r24.readStrongBinder()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x01ee
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r2 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.view.autofill.AutofillId r2 = (android.view.autofill.AutofillId) r2
                r3 = r2
                goto L_0x01ef
            L_0x01ee:
                r3 = r1
            L_0x01ef:
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x01ff
                android.os.Parcelable$Creator<android.graphics.Rect> r2 = android.graphics.Rect.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.graphics.Rect r2 = (android.graphics.Rect) r2
                r4 = r2
                goto L_0x0200
            L_0x01ff:
                r4 = r1
            L_0x0200:
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0210
                android.os.Parcelable$Creator<android.view.autofill.AutofillValue> r2 = android.view.autofill.AutofillValue.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.view.autofill.AutofillValue r2 = (android.view.autofill.AutofillValue) r2
                r5 = r2
                goto L_0x0211
            L_0x0210:
                r5 = r1
            L_0x0211:
                int r19 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x021e
                r7 = r16
                goto L_0x021f
            L_0x021e:
                r7 = r0
            L_0x021f:
                int r20 = r24.readInt()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0233
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
            L_0x0231:
                r9 = r1
                goto L_0x0234
            L_0x0233:
                goto L_0x0231
            L_0x0234:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x023d
                r10 = r16
                goto L_0x023e
            L_0x023d:
                r10 = r0
            L_0x023e:
                android.os.IBinder r0 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r21 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r0 = r22
                r1 = r17
                r2 = r18
                r6 = r19
                r8 = r20
                r11 = r21
                r0.startSession(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                return r16
            L_0x0256:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                android.view.autofill.IAutoFillManagerClient r0 = android.view.autofill.IAutoFillManagerClient.Stub.asInterface(r0)
                int r1 = r24.readInt()
                r12.removeClient(r0, r1)
                return r16
            L_0x0269:
                r14.enforceInterface(r15)
                android.os.IBinder r0 = r24.readStrongBinder()
                android.view.autofill.IAutoFillManagerClient r0 = android.view.autofill.IAutoFillManagerClient.Stub.asInterface(r0)
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0283
                android.os.Parcelable$Creator<android.content.ComponentName> r1 = android.content.ComponentName.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0284
            L_0x0283:
            L_0x0284:
                int r2 = r24.readInt()
                android.os.IBinder r3 = r24.readStrongBinder()
                com.android.internal.os.IResultReceiver r3 = com.android.internal.os.IResultReceiver.Stub.asInterface(r3)
                r12.addClient(r0, r1, r2, r3)
                return r16
            L_0x0294:
                r0 = r25
                r0.writeString(r15)
                return r16
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.IAutoFillManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAutoFillManager {
            public static IAutoFillManager sDefaultImpl;
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

            public void addClient(IAutoFillManagerClient client, ComponentName componentName, int userId, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    if (componentName != null) {
                        _data.writeInt(1);
                        componentName.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addClient(client, componentName, userId, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void removeClient(IAutoFillManagerClient client, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(client != null ? client.asBinder() : null);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().removeClient(client, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startSession(IBinder activityToken, IBinder appCallback, AutofillId autoFillId, Rect bounds, AutofillValue value, int userId, boolean hasCallback, int flags, ComponentName componentName, boolean compatMode, IResultReceiver result) throws RemoteException {
                Parcel _data;
                AutofillId autofillId = autoFillId;
                Rect rect = bounds;
                AutofillValue autofillValue = value;
                ComponentName componentName2 = componentName;
                Parcel _data2 = Parcel.obtain();
                try {
                    _data2.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data2.writeStrongBinder(activityToken);
                    _data2.writeStrongBinder(appCallback);
                    if (autofillId != null) {
                        try {
                            _data2.writeInt(1);
                            autofillId.writeToParcel(_data2, 0);
                        } catch (Throwable th) {
                            th = th;
                            _data = _data2;
                        }
                    } else {
                        _data2.writeInt(0);
                    }
                    if (rect != null) {
                        _data2.writeInt(1);
                        rect.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    if (autofillValue != null) {
                        _data2.writeInt(1);
                        autofillValue.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(userId);
                    _data2.writeInt(hasCallback ? 1 : 0);
                    _data2.writeInt(flags);
                    if (componentName2 != null) {
                        _data2.writeInt(1);
                        componentName2.writeToParcel(_data2, 0);
                    } else {
                        _data2.writeInt(0);
                    }
                    _data2.writeInt(compatMode ? 1 : 0);
                    _data2.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(3, _data2, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data2.recycle();
                        return;
                    }
                    _data = _data2;
                    try {
                        Stub.getDefaultImpl().startSession(activityToken, appCallback, autoFillId, bounds, value, userId, hasCallback, flags, componentName, compatMode, result);
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

            public void getFillEventHistory(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getFillEventHistory(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void restoreSession(int sessionId, IBinder activityToken, IBinder appCallback, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(appCallback);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().restoreSession(sessionId, activityToken, appCallback, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateSession(int sessionId, AutofillId id, Rect bounds, AutofillValue value, int action, int flags, int userId) throws RemoteException {
                AutofillId autofillId = id;
                Rect rect = bounds;
                AutofillValue autofillValue = value;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(sessionId);
                        if (autofillId != null) {
                            _data.writeInt(1);
                            autofillId.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (autofillValue != null) {
                            _data.writeInt(1);
                            autofillValue.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(action);
                        } catch (Throwable th) {
                            th = th;
                            int i = flags;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i2 = action;
                        int i3 = flags;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(flags);
                        _data.writeInt(userId);
                        if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().updateSession(sessionId, id, bounds, value, action, flags, userId);
                        _data.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i4 = sessionId;
                    int i22 = action;
                    int i32 = flags;
                    _data.recycle();
                    throw th;
                }
            }

            public void setAutofillFailure(int sessionId, List<AutofillId> ids, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedList(ids);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAutofillFailure(sessionId, ids, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void finishSession(int sessionId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().finishSession(sessionId, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void cancelSession(int sessionId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().cancelSession(sessionId, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAuthenticationResult(Bundle data, int sessionId, int authenticationId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (data != null) {
                        _data.writeInt(1);
                        data.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sessionId);
                    _data.writeInt(authenticationId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAuthenticationResult(data, sessionId, authenticationId, userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setHasCallback(int sessionId, int userId, boolean hasIt) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(userId);
                    _data.writeInt(hasIt);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setHasCallback(sessionId, userId, hasIt);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void disableOwnedAutofillServices(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().disableOwnedAutofillServices(userId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void isServiceSupported(int userId, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isServiceSupported(userId, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void isServiceEnabled(int userId, String packageName, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isServiceEnabled(userId, packageName, result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onPendingSaveUi(int operation, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(operation);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(15, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onPendingSaveUi(operation, token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getUserData(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(16, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getUserData(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getUserDataId(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getUserDataId(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setUserData(UserData userData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (userData != null) {
                        _data.writeInt(1);
                        userData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setUserData(userData);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void isFieldClassificationEnabled(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(19, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isFieldClassificationEnabled(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAutofillServiceComponentName(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(20, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAutofillServiceComponentName(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAvailableFieldClassificationAlgorithms(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(21, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAvailableFieldClassificationAlgorithms(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getDefaultFieldClassificationAlgorithm(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(22, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getDefaultFieldClassificationAlgorithm(result);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setAugmentedAutofillWhitelist(List<String> packages, List<ComponentName> activities, IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packages);
                    _data.writeTypedList(activities);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(23, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setAugmentedAutofillWhitelist(packages, activities, result);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAutoFillManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAutoFillManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
