package com.android.internal.telephony;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.IFinancialSmsCallback;
import java.util.List;

public interface ISms extends IInterface {
    int checkSmsShortCodeDestination(int i, String str, String str2, String str3) throws RemoteException;

    boolean copyMessageToIccEfForSubscriber(int i, String str, int i2, byte[] bArr, byte[] bArr2) throws RemoteException;

    String createAppSpecificSmsToken(int i, String str, PendingIntent pendingIntent) throws RemoteException;

    String createAppSpecificSmsTokenWithPackageInfo(int i, String str, String str2, PendingIntent pendingIntent) throws RemoteException;

    boolean disableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException;

    boolean disableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException;

    boolean enableCellBroadcastForSubscriber(int i, int i2, int i3) throws RemoteException;

    boolean enableCellBroadcastRangeForSubscriber(int i, int i2, int i3, int i4) throws RemoteException;

    List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int i, String str) throws RemoteException;

    String getImsSmsFormatForSubscriber(int i) throws RemoteException;

    int getPreferredSmsSubscription() throws RemoteException;

    int getPremiumSmsPermission(String str) throws RemoteException;

    int getPremiumSmsPermissionForSubscriber(int i, String str) throws RemoteException;

    int getSmsCapacityOnIccForSubscriber(int i) throws RemoteException;

    void getSmsMessagesForFinancialApp(int i, String str, Bundle bundle, IFinancialSmsCallback iFinancialSmsCallback) throws RemoteException;

    void injectSmsPduForSubscriber(int i, byte[] bArr, String str, PendingIntent pendingIntent) throws RemoteException;

    boolean isImsSmsSupportedForSubscriber(int i) throws RemoteException;

    boolean isSMSPromptEnabled() throws RemoteException;

    boolean isSmsSimPickActivityNeeded(int i) throws RemoteException;

    void sendDataForSubscriber(int i, String str, String str2, String str3, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void sendDataForSubscriberWithSelfPermissions(int i, String str, String str2, String str3, int i2, byte[] bArr, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void sendMultipartTextForSubscriber(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z) throws RemoteException;

    void sendMultipartTextForSubscriberWithOptions(int i, String str, String str2, String str3, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean z, int i2, boolean z2, int i3) throws RemoteException;

    void sendStoredMultipartText(int i, String str, Uri uri, String str2, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException;

    void sendStoredText(int i, String str, Uri uri, String str2, PendingIntent pendingIntent, PendingIntent pendingIntent2) throws RemoteException;

    void sendTextForSubscriber(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) throws RemoteException;

    void sendTextForSubscriberWithOptions(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z, int i2, boolean z2, int i3) throws RemoteException;

    void sendTextForSubscriberWithSelfPermissions(int i, String str, String str2, String str3, String str4, PendingIntent pendingIntent, PendingIntent pendingIntent2, boolean z) throws RemoteException;

    void setPremiumSmsPermission(String str, int i) throws RemoteException;

    void setPremiumSmsPermissionForSubscriber(int i, String str, int i2) throws RemoteException;

    boolean updateMessageOnIccEfForSubscriber(int i, String str, int i2, int i3, byte[] bArr) throws RemoteException;

    public static class Default implements ISms {
        public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int subId, String callingPkg) throws RemoteException {
            return null;
        }

        public boolean updateMessageOnIccEfForSubscriber(int subId, String callingPkg, int messageIndex, int newStatus, byte[] pdu) throws RemoteException {
            return false;
        }

        public boolean copyMessageToIccEfForSubscriber(int subId, String callingPkg, int status, byte[] pdu, byte[] smsc) throws RemoteException {
            return false;
        }

        public void sendDataForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, int destPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
        }

        public void sendDataForSubscriberWithSelfPermissions(int subId, String callingPkg, String destAddr, String scAddr, int destPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
        }

        public void sendTextForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp) throws RemoteException {
        }

        public void sendTextForSubscriberWithSelfPermissions(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage) throws RemoteException {
        }

        public void sendTextForSubscriberWithOptions(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
        }

        public void injectSmsPduForSubscriber(int subId, byte[] pdu, String format, PendingIntent receivedIntent) throws RemoteException {
        }

        public void sendMultipartTextForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean persistMessageForNonDefaultSmsApp) throws RemoteException {
        }

        public void sendMultipartTextForSubscriberWithOptions(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> list, List<PendingIntent> list2, List<PendingIntent> list3, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
        }

        public boolean enableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
            return false;
        }

        public boolean disableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
            return false;
        }

        public boolean enableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
            return false;
        }

        public boolean disableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
            return false;
        }

        public int getPremiumSmsPermission(String packageName) throws RemoteException {
            return 0;
        }

        public int getPremiumSmsPermissionForSubscriber(int subId, String packageName) throws RemoteException {
            return 0;
        }

        public void setPremiumSmsPermission(String packageName, int permission) throws RemoteException {
        }

        public void setPremiumSmsPermissionForSubscriber(int subId, String packageName, int permission) throws RemoteException {
        }

        public boolean isImsSmsSupportedForSubscriber(int subId) throws RemoteException {
            return false;
        }

        public boolean isSmsSimPickActivityNeeded(int subId) throws RemoteException {
            return false;
        }

        public int getPreferredSmsSubscription() throws RemoteException {
            return 0;
        }

        public String getImsSmsFormatForSubscriber(int subId) throws RemoteException {
            return null;
        }

        public boolean isSMSPromptEnabled() throws RemoteException {
            return false;
        }

        public void sendStoredText(int subId, String callingPkg, Uri messageUri, String scAddress, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
        }

        public void sendStoredMultipartText(int subId, String callingPkg, Uri messageUri, String scAddress, List<PendingIntent> list, List<PendingIntent> list2) throws RemoteException {
        }

        public String createAppSpecificSmsToken(int subId, String callingPkg, PendingIntent intent) throws RemoteException {
            return null;
        }

        public String createAppSpecificSmsTokenWithPackageInfo(int subId, String callingPkg, String prefixes, PendingIntent intent) throws RemoteException {
            return null;
        }

        public void getSmsMessagesForFinancialApp(int subId, String callingPkg, Bundle params, IFinancialSmsCallback callback) throws RemoteException {
        }

        public int getSmsCapacityOnIccForSubscriber(int subId) throws RemoteException {
            return 0;
        }

        public int checkSmsShortCodeDestination(int subId, String callingApk, String destAddress, String countryIso) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISms {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ISms";
        static final int TRANSACTION_checkSmsShortCodeDestination = 31;
        static final int TRANSACTION_copyMessageToIccEfForSubscriber = 3;
        static final int TRANSACTION_createAppSpecificSmsToken = 27;
        static final int TRANSACTION_createAppSpecificSmsTokenWithPackageInfo = 28;
        static final int TRANSACTION_disableCellBroadcastForSubscriber = 13;
        static final int TRANSACTION_disableCellBroadcastRangeForSubscriber = 15;
        static final int TRANSACTION_enableCellBroadcastForSubscriber = 12;
        static final int TRANSACTION_enableCellBroadcastRangeForSubscriber = 14;
        static final int TRANSACTION_getAllMessagesFromIccEfForSubscriber = 1;
        static final int TRANSACTION_getImsSmsFormatForSubscriber = 23;
        static final int TRANSACTION_getPreferredSmsSubscription = 22;
        static final int TRANSACTION_getPremiumSmsPermission = 16;
        static final int TRANSACTION_getPremiumSmsPermissionForSubscriber = 17;
        static final int TRANSACTION_getSmsCapacityOnIccForSubscriber = 30;
        static final int TRANSACTION_getSmsMessagesForFinancialApp = 29;
        static final int TRANSACTION_injectSmsPduForSubscriber = 9;
        static final int TRANSACTION_isImsSmsSupportedForSubscriber = 20;
        static final int TRANSACTION_isSMSPromptEnabled = 24;
        static final int TRANSACTION_isSmsSimPickActivityNeeded = 21;
        static final int TRANSACTION_sendDataForSubscriber = 4;
        static final int TRANSACTION_sendDataForSubscriberWithSelfPermissions = 5;
        static final int TRANSACTION_sendMultipartTextForSubscriber = 10;
        static final int TRANSACTION_sendMultipartTextForSubscriberWithOptions = 11;
        static final int TRANSACTION_sendStoredMultipartText = 26;
        static final int TRANSACTION_sendStoredText = 25;
        static final int TRANSACTION_sendTextForSubscriber = 6;
        static final int TRANSACTION_sendTextForSubscriberWithOptions = 8;
        static final int TRANSACTION_sendTextForSubscriberWithSelfPermissions = 7;
        static final int TRANSACTION_setPremiumSmsPermission = 18;
        static final int TRANSACTION_setPremiumSmsPermissionForSubscriber = 19;
        static final int TRANSACTION_updateMessageOnIccEfForSubscriber = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISms asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISms)) {
                return new Proxy(obj);
            }
            return (ISms) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getAllMessagesFromIccEfForSubscriber";
                case 2:
                    return "updateMessageOnIccEfForSubscriber";
                case 3:
                    return "copyMessageToIccEfForSubscriber";
                case 4:
                    return "sendDataForSubscriber";
                case 5:
                    return "sendDataForSubscriberWithSelfPermissions";
                case 6:
                    return "sendTextForSubscriber";
                case 7:
                    return "sendTextForSubscriberWithSelfPermissions";
                case 8:
                    return "sendTextForSubscriberWithOptions";
                case 9:
                    return "injectSmsPduForSubscriber";
                case 10:
                    return "sendMultipartTextForSubscriber";
                case 11:
                    return "sendMultipartTextForSubscriberWithOptions";
                case 12:
                    return "enableCellBroadcastForSubscriber";
                case 13:
                    return "disableCellBroadcastForSubscriber";
                case 14:
                    return "enableCellBroadcastRangeForSubscriber";
                case 15:
                    return "disableCellBroadcastRangeForSubscriber";
                case 16:
                    return "getPremiumSmsPermission";
                case 17:
                    return "getPremiumSmsPermissionForSubscriber";
                case 18:
                    return "setPremiumSmsPermission";
                case 19:
                    return "setPremiumSmsPermissionForSubscriber";
                case 20:
                    return "isImsSmsSupportedForSubscriber";
                case 21:
                    return "isSmsSimPickActivityNeeded";
                case 22:
                    return "getPreferredSmsSubscription";
                case 23:
                    return "getImsSmsFormatForSubscriber";
                case 24:
                    return "isSMSPromptEnabled";
                case 25:
                    return "sendStoredText";
                case 26:
                    return "sendStoredMultipartText";
                case 27:
                    return "createAppSpecificSmsToken";
                case 28:
                    return "createAppSpecificSmsTokenWithPackageInfo";
                case 29:
                    return "getSmsMessagesForFinancialApp";
                case 30:
                    return "getSmsCapacityOnIccForSubscriber";
                case 31:
                    return "checkSmsShortCodeDestination";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v51, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v55, resolved type: android.app.PendingIntent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v59, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v6 */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: type inference failed for: r1v18 */
        /* JADX WARNING: type inference failed for: r1v65 */
        /* JADX WARNING: type inference failed for: r1v66 */
        /* JADX WARNING: type inference failed for: r1v67 */
        /* JADX WARNING: type inference failed for: r1v68 */
        /* JADX WARNING: type inference failed for: r1v69 */
        /* JADX WARNING: type inference failed for: r1v70 */
        /* JADX WARNING: type inference failed for: r1v71 */
        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        /* JADX WARNING: Multi-variable type inference failed */
        public boolean onTransact(int r27, android.os.Parcel r28, android.os.Parcel r29, int r30) throws android.os.RemoteException {
            /*
                r26 = this;
                r12 = r26
                r13 = r27
                r14 = r28
                r15 = r29
                java.lang.String r11 = "com.android.internal.telephony.ISms"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r16 = 1
                if (r13 == r0) goto L_0x0544
                r0 = 0
                r1 = 0
                switch(r13) {
                    case 1: goto L_0x052d;
                    case 2: goto L_0x0503;
                    case 3: goto L_0x04d9;
                    case 4: goto L_0x0489;
                    case 5: goto L_0x0439;
                    case 6: goto L_0x03e5;
                    case 7: goto L_0x0391;
                    case 8: goto L_0x0324;
                    case 9: goto L_0x02fd;
                    case 10: goto L_0x02bb;
                    case 11: goto L_0x0260;
                    case 12: goto L_0x0246;
                    case 13: goto L_0x022c;
                    case 14: goto L_0x020e;
                    case 15: goto L_0x01f0;
                    case 16: goto L_0x01de;
                    case 17: goto L_0x01c8;
                    case 18: goto L_0x01b6;
                    case 19: goto L_0x01a0;
                    case 20: goto L_0x018e;
                    case 21: goto L_0x017c;
                    case 22: goto L_0x016e;
                    case 23: goto L_0x015c;
                    case 24: goto L_0x014e;
                    case 25: goto L_0x0100;
                    case 26: goto L_0x00c5;
                    case 27: goto L_0x009f;
                    case 28: goto L_0x0075;
                    case 29: goto L_0x004b;
                    case 30: goto L_0x0039;
                    case 31: goto L_0x001b;
                    default: goto L_0x0016;
                }
            L_0x0016:
                boolean r0 = super.onTransact(r27, r28, r29, r30)
                return r0
            L_0x001b:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.lang.String r2 = r28.readString()
                java.lang.String r3 = r28.readString()
                int r4 = r12.checkSmsShortCodeDestination(r0, r1, r2, r3)
                r29.writeNoException()
                r15.writeInt(r4)
                return r16
            L_0x0039:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                int r1 = r12.getSmsCapacityOnIccForSubscriber(r0)
                r29.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x004b:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x0065
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0066
            L_0x0065:
            L_0x0066:
                android.os.IBinder r3 = r28.readStrongBinder()
                android.telephony.IFinancialSmsCallback r3 = android.telephony.IFinancialSmsCallback.Stub.asInterface(r3)
                r12.getSmsMessagesForFinancialApp(r0, r2, r1, r3)
                r29.writeNoException()
                return r16
            L_0x0075:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                java.lang.String r3 = r28.readString()
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x0093
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0094
            L_0x0093:
            L_0x0094:
                java.lang.String r4 = r12.createAppSpecificSmsTokenWithPackageInfo(r0, r2, r3, r1)
                r29.writeNoException()
                r15.writeString(r4)
                return r16
            L_0x009f:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r2 = r28.readString()
                int r3 = r28.readInt()
                if (r3 == 0) goto L_0x00b9
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x00ba
            L_0x00b9:
            L_0x00ba:
                java.lang.String r3 = r12.createAppSpecificSmsToken(r0, r2, r1)
                r29.writeNoException()
                r15.writeString(r3)
                return r16
            L_0x00c5:
                r14.enforceInterface(r11)
                int r7 = r28.readInt()
                java.lang.String r8 = r28.readString()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x00e0
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.net.Uri r0 = (android.net.Uri) r0
                r3 = r0
                goto L_0x00e1
            L_0x00e0:
                r3 = r1
            L_0x00e1:
                java.lang.String r9 = r28.readString()
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.util.ArrayList r10 = r14.createTypedArrayList(r0)
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.util.ArrayList r17 = r14.createTypedArrayList(r0)
                r0 = r26
                r1 = r7
                r2 = r8
                r4 = r9
                r5 = r10
                r6 = r17
                r0.sendStoredMultipartText(r1, r2, r3, r4, r5, r6)
                r29.writeNoException()
                return r16
            L_0x0100:
                r14.enforceInterface(r11)
                int r7 = r28.readInt()
                java.lang.String r8 = r28.readString()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x011b
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.net.Uri r0 = (android.net.Uri) r0
                r3 = r0
                goto L_0x011c
            L_0x011b:
                r3 = r1
            L_0x011c:
                java.lang.String r9 = r28.readString()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0130
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r5 = r0
                goto L_0x0131
            L_0x0130:
                r5 = r1
            L_0x0131:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0141
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r6 = r0
                goto L_0x0142
            L_0x0141:
                r6 = r1
            L_0x0142:
                r0 = r26
                r1 = r7
                r2 = r8
                r4 = r9
                r0.sendStoredText(r1, r2, r3, r4, r5, r6)
                r29.writeNoException()
                return r16
            L_0x014e:
                r14.enforceInterface(r11)
                boolean r0 = r26.isSMSPromptEnabled()
                r29.writeNoException()
                r15.writeInt(r0)
                return r16
            L_0x015c:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r1 = r12.getImsSmsFormatForSubscriber(r0)
                r29.writeNoException()
                r15.writeString(r1)
                return r16
            L_0x016e:
                r14.enforceInterface(r11)
                int r0 = r26.getPreferredSmsSubscription()
                r29.writeNoException()
                r15.writeInt(r0)
                return r16
            L_0x017c:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                boolean r1 = r12.isSmsSimPickActivityNeeded(r0)
                r29.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x018e:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                boolean r1 = r12.isImsSmsSupportedForSubscriber(r0)
                r29.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x01a0:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r28.readInt()
                r12.setPremiumSmsPermissionForSubscriber(r0, r1, r2)
                r29.writeNoException()
                return r16
            L_0x01b6:
                r14.enforceInterface(r11)
                java.lang.String r0 = r28.readString()
                int r1 = r28.readInt()
                r12.setPremiumSmsPermission(r0, r1)
                r29.writeNoException()
                return r16
            L_0x01c8:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                int r2 = r12.getPremiumSmsPermissionForSubscriber(r0, r1)
                r29.writeNoException()
                r15.writeInt(r2)
                return r16
            L_0x01de:
                r14.enforceInterface(r11)
                java.lang.String r0 = r28.readString()
                int r1 = r12.getPremiumSmsPermission(r0)
                r29.writeNoException()
                r15.writeInt(r1)
                return r16
            L_0x01f0:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                boolean r4 = r12.disableCellBroadcastRangeForSubscriber(r0, r1, r2, r3)
                r29.writeNoException()
                r15.writeInt(r4)
                return r16
            L_0x020e:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                int r3 = r28.readInt()
                boolean r4 = r12.enableCellBroadcastRangeForSubscriber(r0, r1, r2, r3)
                r29.writeNoException()
                r15.writeInt(r4)
                return r16
            L_0x022c:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                boolean r3 = r12.disableCellBroadcastForSubscriber(r0, r1, r2)
                r29.writeNoException()
                r15.writeInt(r3)
                return r16
            L_0x0246:
                r14.enforceInterface(r11)
                int r0 = r28.readInt()
                int r1 = r28.readInt()
                int r2 = r28.readInt()
                boolean r3 = r12.enableCellBroadcastForSubscriber(r0, r1, r2)
                r29.writeNoException()
                r15.writeInt(r3)
                return r16
            L_0x0260:
                r14.enforceInterface(r11)
                int r17 = r28.readInt()
                java.lang.String r18 = r28.readString()
                java.lang.String r19 = r28.readString()
                java.lang.String r20 = r28.readString()
                java.util.ArrayList r21 = r28.createStringArrayList()
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.util.ArrayList r22 = r14.createTypedArrayList(r1)
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.util.ArrayList r23 = r14.createTypedArrayList(r1)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x028c
                r8 = r16
                goto L_0x028d
            L_0x028c:
                r8 = r0
            L_0x028d:
                int r24 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x029a
                r10 = r16
                goto L_0x029b
            L_0x029a:
                r10 = r0
            L_0x029b:
                int r25 = r28.readInt()
                r0 = r26
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r20
                r5 = r21
                r6 = r22
                r7 = r23
                r9 = r24
                r13 = r11
                r11 = r25
                r0.sendMultipartTextForSubscriberWithOptions(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                return r16
            L_0x02bb:
                r13 = r11
                r14.enforceInterface(r13)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                java.lang.String r11 = r28.readString()
                java.lang.String r17 = r28.readString()
                java.util.ArrayList r18 = r28.createStringArrayList()
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.util.ArrayList r19 = r14.createTypedArrayList(r1)
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.util.ArrayList r20 = r14.createTypedArrayList(r1)
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x02e8
                r8 = r16
                goto L_0x02e9
            L_0x02e8:
                r8 = r0
            L_0x02e9:
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r17
                r5 = r18
                r6 = r19
                r7 = r20
                r0.sendMultipartTextForSubscriber(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                return r16
            L_0x02fd:
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r28.readInt()
                byte[] r2 = r28.createByteArray()
                java.lang.String r3 = r28.readString()
                int r4 = r28.readInt()
                if (r4 == 0) goto L_0x031c
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x031d
            L_0x031c:
            L_0x031d:
                r12.injectSmsPduForSubscriber(r0, r2, r3, r1)
                r29.writeNoException()
                return r16
            L_0x0324:
                r13 = r11
                r14.enforceInterface(r13)
                int r17 = r28.readInt()
                java.lang.String r18 = r28.readString()
                java.lang.String r19 = r28.readString()
                java.lang.String r20 = r28.readString()
                java.lang.String r21 = r28.readString()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x034c
                android.os.Parcelable$Creator<android.app.PendingIntent> r2 = android.app.PendingIntent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.app.PendingIntent r2 = (android.app.PendingIntent) r2
                r6 = r2
                goto L_0x034d
            L_0x034c:
                r6 = r1
            L_0x034d:
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x035d
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x035b:
                r7 = r1
                goto L_0x035e
            L_0x035d:
                goto L_0x035b
            L_0x035e:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0367
                r8 = r16
                goto L_0x0368
            L_0x0367:
                r8 = r0
            L_0x0368:
                int r22 = r28.readInt()
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0375
                r10 = r16
                goto L_0x0376
            L_0x0375:
                r10 = r0
            L_0x0376:
                int r23 = r28.readInt()
                r0 = r26
                r1 = r17
                r2 = r18
                r3 = r19
                r4 = r20
                r5 = r21
                r9 = r22
                r11 = r23
                r0.sendTextForSubscriberWithOptions(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
                r29.writeNoException()
                return r16
            L_0x0391:
                r13 = r11
                r14.enforceInterface(r13)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                java.lang.String r11 = r28.readString()
                java.lang.String r17 = r28.readString()
                java.lang.String r18 = r28.readString()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x03b9
                android.os.Parcelable$Creator<android.app.PendingIntent> r2 = android.app.PendingIntent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.app.PendingIntent r2 = (android.app.PendingIntent) r2
                r6 = r2
                goto L_0x03ba
            L_0x03b9:
                r6 = r1
            L_0x03ba:
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x03ca
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x03c8:
                r7 = r1
                goto L_0x03cb
            L_0x03ca:
                goto L_0x03c8
            L_0x03cb:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x03d4
                r8 = r16
                goto L_0x03d5
            L_0x03d4:
                r8 = r0
            L_0x03d5:
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r17
                r5 = r18
                r0.sendTextForSubscriberWithSelfPermissions(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                return r16
            L_0x03e5:
                r13 = r11
                r14.enforceInterface(r13)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                java.lang.String r11 = r28.readString()
                java.lang.String r17 = r28.readString()
                java.lang.String r18 = r28.readString()
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x040d
                android.os.Parcelable$Creator<android.app.PendingIntent> r2 = android.app.PendingIntent.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r14)
                android.app.PendingIntent r2 = (android.app.PendingIntent) r2
                r6 = r2
                goto L_0x040e
            L_0x040d:
                r6 = r1
            L_0x040e:
                int r2 = r28.readInt()
                if (r2 == 0) goto L_0x041e
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r14)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x041c:
                r7 = r1
                goto L_0x041f
            L_0x041e:
                goto L_0x041c
            L_0x041f:
                int r1 = r28.readInt()
                if (r1 == 0) goto L_0x0428
                r8 = r16
                goto L_0x0429
            L_0x0428:
                r8 = r0
            L_0x0429:
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r17
                r5 = r18
                r0.sendTextForSubscriber(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                return r16
            L_0x0439:
                r13 = r11
                r14.enforceInterface(r13)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                java.lang.String r11 = r28.readString()
                java.lang.String r17 = r28.readString()
                int r18 = r28.readInt()
                byte[] r19 = r28.createByteArray()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0465
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r7 = r0
                goto L_0x0466
            L_0x0465:
                r7 = r1
            L_0x0466:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x0476
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r8 = r0
                goto L_0x0477
            L_0x0476:
                r8 = r1
            L_0x0477:
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r17
                r5 = r18
                r6 = r19
                r0.sendDataForSubscriberWithSelfPermissions(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                return r16
            L_0x0489:
                r13 = r11
                r14.enforceInterface(r13)
                int r9 = r28.readInt()
                java.lang.String r10 = r28.readString()
                java.lang.String r11 = r28.readString()
                java.lang.String r17 = r28.readString()
                int r18 = r28.readInt()
                byte[] r19 = r28.createByteArray()
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x04b5
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r7 = r0
                goto L_0x04b6
            L_0x04b5:
                r7 = r1
            L_0x04b6:
                int r0 = r28.readInt()
                if (r0 == 0) goto L_0x04c6
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r14)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
                r8 = r0
                goto L_0x04c7
            L_0x04c6:
                r8 = r1
            L_0x04c7:
                r0 = r26
                r1 = r9
                r2 = r10
                r3 = r11
                r4 = r17
                r5 = r18
                r6 = r19
                r0.sendDataForSubscriber(r1, r2, r3, r4, r5, r6, r7, r8)
                r29.writeNoException()
                return r16
            L_0x04d9:
                r13 = r11
                r14.enforceInterface(r13)
                int r6 = r28.readInt()
                java.lang.String r7 = r28.readString()
                int r8 = r28.readInt()
                byte[] r9 = r28.createByteArray()
                byte[] r10 = r28.createByteArray()
                r0 = r26
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                boolean r0 = r0.copyMessageToIccEfForSubscriber(r1, r2, r3, r4, r5)
                r29.writeNoException()
                r15.writeInt(r0)
                return r16
            L_0x0503:
                r13 = r11
                r14.enforceInterface(r13)
                int r6 = r28.readInt()
                java.lang.String r7 = r28.readString()
                int r8 = r28.readInt()
                int r9 = r28.readInt()
                byte[] r10 = r28.createByteArray()
                r0 = r26
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r10
                boolean r0 = r0.updateMessageOnIccEfForSubscriber(r1, r2, r3, r4, r5)
                r29.writeNoException()
                r15.writeInt(r0)
                return r16
            L_0x052d:
                r13 = r11
                r14.enforceInterface(r13)
                int r0 = r28.readInt()
                java.lang.String r1 = r28.readString()
                java.util.List r2 = r12.getAllMessagesFromIccEfForSubscriber(r0, r1)
                r29.writeNoException()
                r15.writeTypedList(r2)
                return r16
            L_0x0544:
                r13 = r11
                r15.writeString(r13)
                return r16
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.ISms.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISms {
            public static ISms sDefaultImpl;
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

            public List<SmsRawData> getAllMessagesFromIccEfForSubscriber(int subId, String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllMessagesFromIccEfForSubscriber(subId, callingPkg);
                    }
                    _reply.readException();
                    List<SmsRawData> _result = _reply.createTypedArrayList(SmsRawData.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean updateMessageOnIccEfForSubscriber(int subId, String callingPkg, int messageIndex, int newStatus, byte[] pdu) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            try {
                                _data.writeInt(messageIndex);
                                try {
                                    _data.writeInt(newStatus);
                                } catch (Throwable th) {
                                    th = th;
                                    byte[] bArr = pdu;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                int i = newStatus;
                                byte[] bArr2 = pdu;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i2 = messageIndex;
                            int i3 = newStatus;
                            byte[] bArr22 = pdu;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeByteArray(pdu);
                            try {
                                boolean z = false;
                                if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        z = true;
                                    }
                                    boolean _result = z;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean updateMessageOnIccEfForSubscriber = Stub.getDefaultImpl().updateMessageOnIccEfForSubscriber(subId, callingPkg, messageIndex, newStatus, pdu);
                                _reply.recycle();
                                _data.recycle();
                                return updateMessageOnIccEfForSubscriber;
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
                        String str = callingPkg;
                        int i22 = messageIndex;
                        int i32 = newStatus;
                        byte[] bArr222 = pdu;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i4 = subId;
                    String str2 = callingPkg;
                    int i222 = messageIndex;
                    int i322 = newStatus;
                    byte[] bArr2222 = pdu;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean copyMessageToIccEfForSubscriber(int subId, String callingPkg, int status, byte[] pdu, byte[] smsc) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            try {
                                _data.writeInt(status);
                                try {
                                    _data.writeByteArray(pdu);
                                } catch (Throwable th) {
                                    th = th;
                                    byte[] bArr = smsc;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                byte[] bArr2 = pdu;
                                byte[] bArr3 = smsc;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i = status;
                            byte[] bArr22 = pdu;
                            byte[] bArr32 = smsc;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeByteArray(smsc);
                            try {
                                boolean z = false;
                                if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        z = true;
                                    }
                                    boolean _result = z;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result;
                                }
                                boolean copyMessageToIccEfForSubscriber = Stub.getDefaultImpl().copyMessageToIccEfForSubscriber(subId, callingPkg, status, pdu, smsc);
                                _reply.recycle();
                                _data.recycle();
                                return copyMessageToIccEfForSubscriber;
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
                        String str = callingPkg;
                        int i2 = status;
                        byte[] bArr222 = pdu;
                        byte[] bArr322 = smsc;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i3 = subId;
                    String str2 = callingPkg;
                    int i22 = status;
                    byte[] bArr2222 = pdu;
                    byte[] bArr3222 = smsc;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendDataForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, int destPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            _data.writeString(destAddr);
                            _data.writeString(scAddr);
                            _data.writeInt(destPort);
                            _data.writeByteArray(data);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (pendingIntent2 != null) {
                                _data.writeInt(1);
                                pendingIntent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendDataForSubscriber(subId, callingPkg, destAddr, scAddr, destPort, data, sentIntent, deliveryIntent);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = callingPkg;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = subId;
                    String str2 = callingPkg;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendDataForSubscriberWithSelfPermissions(int subId, String callingPkg, String destAddr, String scAddr, int destPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            _data.writeString(destAddr);
                            _data.writeString(scAddr);
                            _data.writeInt(destPort);
                            _data.writeByteArray(data);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (pendingIntent2 != null) {
                                _data.writeInt(1);
                                pendingIntent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendDataForSubscriberWithSelfPermissions(subId, callingPkg, destAddr, scAddr, destPort, data, sentIntent, deliveryIntent);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = callingPkg;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = subId;
                    String str2 = callingPkg;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendTextForSubscriber(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp) throws RemoteException {
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            _data.writeString(destAddr);
                            _data.writeString(scAddr);
                            _data.writeString(text);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (pendingIntent2 != null) {
                                _data.writeInt(1);
                                pendingIntent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(persistMessageForNonDefaultSmsApp ? 1 : 0);
                            if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendTextForSubscriber(subId, callingPkg, destAddr, scAddr, text, sentIntent, deliveryIntent, persistMessageForNonDefaultSmsApp);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = callingPkg;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = subId;
                    String str2 = callingPkg;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendTextForSubscriberWithSelfPermissions(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage) throws RemoteException {
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            _data.writeString(destAddr);
                            _data.writeString(scAddr);
                            _data.writeString(text);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (pendingIntent2 != null) {
                                _data.writeInt(1);
                                pendingIntent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(persistMessage ? 1 : 0);
                            if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendTextForSubscriberWithSelfPermissions(subId, callingPkg, destAddr, scAddr, text, sentIntent, deliveryIntent, persistMessage);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = callingPkg;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = subId;
                    String str2 = callingPkg;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendTextForSubscriberWithOptions(int subId, String callingPkg, String destAddr, String scAddr, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
                Parcel _reply;
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply2 = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(destAddr);
                    _data.writeString(scAddr);
                    _data.writeString(text);
                    if (pendingIntent != null) {
                        try {
                            _data.writeInt(1);
                            pendingIntent.writeToParcel(_data, 0);
                        } catch (Throwable th) {
                            th = th;
                            _reply = _reply2;
                        }
                    } else {
                        _data.writeInt(0);
                    }
                    if (pendingIntent2 != null) {
                        _data.writeInt(1);
                        pendingIntent2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(persistMessageForNonDefaultSmsApp ? 1 : 0);
                    _data.writeInt(priority);
                    _data.writeInt(expectMore ? 1 : 0);
                    _data.writeInt(validityPeriod);
                    if (this.mRemote.transact(8, _data, _reply2, 0) || Stub.getDefaultImpl() == null) {
                        _reply = _reply2;
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply = _reply2;
                    try {
                        Stub.getDefaultImpl().sendTextForSubscriberWithOptions(subId, callingPkg, destAddr, scAddr, text, sentIntent, deliveryIntent, persistMessageForNonDefaultSmsApp, priority, expectMore, validityPeriod);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    _reply = _reply2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void injectSmsPduForSubscriber(int subId, byte[] pdu, String format, PendingIntent receivedIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeByteArray(pdu);
                    _data.writeString(format);
                    if (receivedIntent != null) {
                        _data.writeInt(1);
                        receivedIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().injectSmsPduForSubscriber(subId, pdu, format, receivedIntent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendMultipartTextForSubscriber(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessageForNonDefaultSmsApp) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                        } catch (Throwable th) {
                            th = th;
                            String str = destinationAddress;
                            String str2 = scAddress;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeString(destinationAddress);
                            try {
                                _data.writeString(scAddress);
                                _data.writeStringList(parts);
                                _data.writeTypedList(sentIntents);
                                _data.writeTypedList(deliveryIntents);
                                _data.writeInt(persistMessageForNonDefaultSmsApp ? 1 : 0);
                                if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().sendMultipartTextForSubscriber(subId, callingPkg, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessageForNonDefaultSmsApp);
                                _reply.recycle();
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str22 = scAddress;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str3 = callingPkg;
                        String str4 = destinationAddress;
                        String str222 = scAddress;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i = subId;
                    String str32 = callingPkg;
                    String str42 = destinationAddress;
                    String str2222 = scAddress;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendMultipartTextForSubscriberWithOptions(int subId, String callingPkg, String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessageForNonDefaultSmsApp, int priority, boolean expectMore, int validityPeriod) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        _data.writeString(callingPkg);
                        _data.writeString(destinationAddress);
                        _data.writeString(scAddress);
                        _data.writeStringList(parts);
                        _data.writeTypedList(sentIntents);
                        _data.writeTypedList(deliveryIntents);
                        _data.writeInt(persistMessageForNonDefaultSmsApp ? 1 : 0);
                        _data.writeInt(priority);
                        _data.writeInt(expectMore ? 1 : 0);
                        _data.writeInt(validityPeriod);
                        if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().sendMultipartTextForSubscriberWithOptions(subId, callingPkg, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessageForNonDefaultSmsApp, priority, expectMore, validityPeriod);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    int i = subId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean enableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(messageIdentifier);
                    _data.writeInt(ranType);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableCellBroadcastForSubscriber(subId, messageIdentifier, ranType);
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

            public boolean disableCellBroadcastForSubscriber(int subId, int messageIdentifier, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(messageIdentifier);
                    _data.writeInt(ranType);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableCellBroadcastForSubscriber(subId, messageIdentifier, ranType);
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

            public boolean enableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(startMessageId);
                    _data.writeInt(endMessageId);
                    _data.writeInt(ranType);
                    boolean z = false;
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableCellBroadcastRangeForSubscriber(subId, startMessageId, endMessageId, ranType);
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

            public boolean disableCellBroadcastRangeForSubscriber(int subId, int startMessageId, int endMessageId, int ranType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(startMessageId);
                    _data.writeInt(endMessageId);
                    _data.writeInt(ranType);
                    boolean z = false;
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableCellBroadcastRangeForSubscriber(subId, startMessageId, endMessageId, ranType);
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

            public int getPremiumSmsPermission(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPremiumSmsPermission(packageName);
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

            public int getPremiumSmsPermissionForSubscriber(int subId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPremiumSmsPermissionForSubscriber(subId, packageName);
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

            public void setPremiumSmsPermission(String packageName, int permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(permission);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPremiumSmsPermission(packageName, permission);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPremiumSmsPermissionForSubscriber(int subId, String packageName, int permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(packageName);
                    _data.writeInt(permission);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPremiumSmsPermissionForSubscriber(subId, packageName, permission);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isImsSmsSupportedForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isImsSmsSupportedForSubscriber(subId);
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

            public boolean isSmsSimPickActivityNeeded(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    boolean z = false;
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSmsSimPickActivityNeeded(subId);
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

            public int getPreferredSmsSubscription() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreferredSmsSubscription();
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

            public String getImsSmsFormatForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImsSmsFormatForSubscriber(subId);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSMSPromptEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSMSPromptEnabled();
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

            public void sendStoredText(int subId, String callingPkg, Uri messageUri, String scAddress, PendingIntent sentIntent, PendingIntent deliveryIntent) throws RemoteException {
                Uri uri = messageUri;
                PendingIntent pendingIntent = sentIntent;
                PendingIntent pendingIntent2 = deliveryIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                    } catch (Throwable th) {
                        th = th;
                        String str = callingPkg;
                        String str2 = scAddress;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(callingPkg);
                        if (uri != null) {
                            _data.writeInt(1);
                            uri.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeString(scAddress);
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (pendingIntent2 != null) {
                                _data.writeInt(1);
                                pendingIntent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendStoredText(subId, callingPkg, messageUri, scAddress, sentIntent, deliveryIntent);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str22 = scAddress;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i = subId;
                    String str3 = callingPkg;
                    String str222 = scAddress;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void sendStoredMultipartText(int subId, String callingPkg, Uri messageUri, String scAddress, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents) throws RemoteException {
                Uri uri = messageUri;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeString(callingPkg);
                            if (uri != null) {
                                _data.writeInt(1);
                                uri.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th) {
                            th = th;
                            String str = scAddress;
                            List<PendingIntent> list = sentIntents;
                            List<PendingIntent> list2 = deliveryIntents;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str2 = callingPkg;
                        String str3 = scAddress;
                        List<PendingIntent> list3 = sentIntents;
                        List<PendingIntent> list22 = deliveryIntents;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(scAddress);
                        try {
                            _data.writeTypedList(sentIntents);
                        } catch (Throwable th3) {
                            th = th3;
                            List<PendingIntent> list222 = deliveryIntents;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeTypedList(deliveryIntents);
                            if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendStoredMultipartText(subId, callingPkg, messageUri, scAddress, sentIntents, deliveryIntents);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th4) {
                            th = th4;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        List<PendingIntent> list32 = sentIntents;
                        List<PendingIntent> list2222 = deliveryIntents;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i = subId;
                    String str22 = callingPkg;
                    String str32 = scAddress;
                    List<PendingIntent> list322 = sentIntents;
                    List<PendingIntent> list22222 = deliveryIntents;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public String createAppSpecificSmsToken(int subId, String callingPkg, PendingIntent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createAppSpecificSmsToken(subId, callingPkg, intent);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String createAppSpecificSmsTokenWithPackageInfo(int subId, String callingPkg, String prefixes, PendingIntent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    _data.writeString(prefixes);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createAppSpecificSmsTokenWithPackageInfo(subId, callingPkg, prefixes, intent);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getSmsMessagesForFinancialApp(int subId, String callingPkg, Bundle params, IFinancialSmsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getSmsMessagesForFinancialApp(subId, callingPkg, params, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSmsCapacityOnIccForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSmsCapacityOnIccForSubscriber(subId);
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

            public int checkSmsShortCodeDestination(int subId, String callingApk, String destAddress, String countryIso) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingApk);
                    _data.writeString(destAddress);
                    _data.writeString(countryIso);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().checkSmsShortCodeDestination(subId, callingApk, destAddress, countryIso);
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

        public static boolean setDefaultImpl(ISms impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISms getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
