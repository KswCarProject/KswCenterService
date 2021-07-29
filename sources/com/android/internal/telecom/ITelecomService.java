package com.android.internal.telecom;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomAnalytics;
import java.util.List;

public interface ITelecomService extends IInterface {
    void acceptHandover(Uri uri, int i, PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    void acceptRingingCall(String str) throws RemoteException;

    void acceptRingingCallWithVideoState(String str, int i) throws RemoteException;

    void addNewIncomingCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException;

    void addNewUnknownCall(PhoneAccountHandle phoneAccountHandle, Bundle bundle) throws RemoteException;

    void addOrRemoveTestCallCompanionApp(String str, boolean z) throws RemoteException;

    void cancelMissedCallsNotification(String str) throws RemoteException;

    void clearAccounts(String str) throws RemoteException;

    Intent createManageBlockedNumbersIntent() throws RemoteException;

    TelecomAnalytics dumpCallAnalytics() throws RemoteException;

    boolean enablePhoneAccount(PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    boolean endCall(String str) throws RemoteException;

    Uri getAdnUriForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    List<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException;

    List<PhoneAccount> getAllPhoneAccounts() throws RemoteException;

    int getAllPhoneAccountsCount() throws RemoteException;

    List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z, String str) throws RemoteException;

    @UnsupportedAppUsage
    int getCallState() throws RemoteException;

    int getCurrentTtyMode(String str) throws RemoteException;

    String getDefaultDialerPackage() throws RemoteException;

    PhoneAccountHandle getDefaultOutgoingPhoneAccount(String str, String str2) throws RemoteException;

    ComponentName getDefaultPhoneApp() throws RemoteException;

    String getLine1Number(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    PhoneAccount getPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    List<PhoneAccountHandle> getPhoneAccountsForPackage(String str) throws RemoteException;

    List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String str, String str2) throws RemoteException;

    List<PhoneAccountHandle> getSelfManagedPhoneAccounts(String str) throws RemoteException;

    PhoneAccountHandle getSimCallManager(int i) throws RemoteException;

    PhoneAccountHandle getSimCallManagerForUser(int i) throws RemoteException;

    String getSystemDialerPackage() throws RemoteException;

    PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String str) throws RemoteException;

    String getVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str) throws RemoteException;

    void handleCallIntent(Intent intent) throws RemoteException;

    boolean handlePinMmi(String str, String str2) throws RemoteException;

    boolean handlePinMmiForPhoneAccount(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    boolean isInCall(String str) throws RemoteException;

    boolean isInEmergencyCall() throws RemoteException;

    boolean isInManagedCall(String str) throws RemoteException;

    boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    boolean isRinging(String str) throws RemoteException;

    boolean isTtySupported(String str) throws RemoteException;

    boolean isVoiceMailNumber(PhoneAccountHandle phoneAccountHandle, String str, String str2) throws RemoteException;

    void placeCall(Uri uri, Bundle bundle, String str) throws RemoteException;

    void registerPhoneAccount(PhoneAccount phoneAccount) throws RemoteException;

    boolean setDefaultDialer(String str) throws RemoteException;

    void setTestAutoModeApp(String str) throws RemoteException;

    void setTestDefaultCallRedirectionApp(String str) throws RemoteException;

    void setTestDefaultCallScreeningApp(String str) throws RemoteException;

    void setTestDefaultDialer(String str) throws RemoteException;

    void setTestPhoneAcctSuggestionComponent(String str) throws RemoteException;

    void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    void showInCallScreen(boolean z, String str) throws RemoteException;

    void silenceRinger(String str) throws RemoteException;

    void unregisterPhoneAccount(PhoneAccountHandle phoneAccountHandle) throws RemoteException;

    void waitOnHandlers() throws RemoteException;

    public static class Default implements ITelecomService {
        public void showInCallScreen(boolean showDialpad, String callingPackage) throws RemoteException {
        }

        public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String uriScheme, String callingPackage) throws RemoteException {
            return null;
        }

        public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String callingPackage) throws RemoteException {
            return null;
        }

        public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle account) throws RemoteException {
        }

        public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean includeDisabledAccounts, String callingPackage) throws RemoteException {
            return null;
        }

        public List<PhoneAccountHandle> getSelfManagedPhoneAccounts(String callingPackage) throws RemoteException {
            return null;
        }

        public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String uriScheme, String callingPackage) throws RemoteException {
            return null;
        }

        public List<PhoneAccountHandle> getPhoneAccountsForPackage(String packageName) throws RemoteException {
            return null;
        }

        public PhoneAccount getPhoneAccount(PhoneAccountHandle account) throws RemoteException {
            return null;
        }

        public int getAllPhoneAccountsCount() throws RemoteException {
            return 0;
        }

        public List<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
            return null;
        }

        public List<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
            return null;
        }

        public PhoneAccountHandle getSimCallManager(int subId) throws RemoteException {
            return null;
        }

        public PhoneAccountHandle getSimCallManagerForUser(int userId) throws RemoteException {
            return null;
        }

        public void registerPhoneAccount(PhoneAccount metadata) throws RemoteException {
        }

        public void unregisterPhoneAccount(PhoneAccountHandle account) throws RemoteException {
        }

        public void clearAccounts(String packageName) throws RemoteException {
        }

        public boolean isVoiceMailNumber(PhoneAccountHandle accountHandle, String number, String callingPackage) throws RemoteException {
            return false;
        }

        public String getVoiceMailNumber(PhoneAccountHandle accountHandle, String callingPackage) throws RemoteException {
            return null;
        }

        public String getLine1Number(PhoneAccountHandle accountHandle, String callingPackage) throws RemoteException {
            return null;
        }

        public ComponentName getDefaultPhoneApp() throws RemoteException {
            return null;
        }

        public String getDefaultDialerPackage() throws RemoteException {
            return null;
        }

        public String getSystemDialerPackage() throws RemoteException {
            return null;
        }

        public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
            return null;
        }

        public void silenceRinger(String callingPackage) throws RemoteException {
        }

        public boolean isInCall(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isInManagedCall(String callingPackage) throws RemoteException {
            return false;
        }

        public boolean isRinging(String callingPackage) throws RemoteException {
            return false;
        }

        public int getCallState() throws RemoteException {
            return 0;
        }

        public boolean endCall(String callingPackage) throws RemoteException {
            return false;
        }

        public void acceptRingingCall(String callingPackage) throws RemoteException {
        }

        public void acceptRingingCallWithVideoState(String callingPackage, int videoState) throws RemoteException {
        }

        public void cancelMissedCallsNotification(String callingPackage) throws RemoteException {
        }

        public boolean handlePinMmi(String dialString, String callingPackage) throws RemoteException {
            return false;
        }

        public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle accountHandle, String dialString, String callingPackage) throws RemoteException {
            return false;
        }

        public Uri getAdnUriForPhoneAccount(PhoneAccountHandle accountHandle, String callingPackage) throws RemoteException {
            return null;
        }

        public boolean isTtySupported(String callingPackage) throws RemoteException {
            return false;
        }

        public int getCurrentTtyMode(String callingPackage) throws RemoteException {
            return 0;
        }

        public void addNewIncomingCall(PhoneAccountHandle phoneAccount, Bundle extras) throws RemoteException {
        }

        public void addNewUnknownCall(PhoneAccountHandle phoneAccount, Bundle extras) throws RemoteException {
        }

        public void placeCall(Uri handle, Bundle extras, String callingPackage) throws RemoteException {
        }

        public boolean enablePhoneAccount(PhoneAccountHandle accountHandle, boolean isEnabled) throws RemoteException {
            return false;
        }

        public boolean setDefaultDialer(String packageName) throws RemoteException {
            return false;
        }

        public Intent createManageBlockedNumbersIntent() throws RemoteException {
            return null;
        }

        public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return false;
        }

        public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
            return false;
        }

        public void waitOnHandlers() throws RemoteException {
        }

        public void acceptHandover(Uri srcAddr, int videoState, PhoneAccountHandle destAcct) throws RemoteException {
        }

        public boolean isInEmergencyCall() throws RemoteException {
            return false;
        }

        public void handleCallIntent(Intent intent) throws RemoteException {
        }

        public void setTestDefaultCallRedirectionApp(String packageName) throws RemoteException {
        }

        public void setTestPhoneAcctSuggestionComponent(String flattenedComponentName) throws RemoteException {
        }

        public void setTestDefaultCallScreeningApp(String packageName) throws RemoteException {
        }

        public void addOrRemoveTestCallCompanionApp(String packageName, boolean isAdded) throws RemoteException {
        }

        public void setTestAutoModeApp(String packageName) throws RemoteException {
        }

        public void setTestDefaultDialer(String packageName) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITelecomService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ITelecomService";
        static final int TRANSACTION_acceptHandover = 48;
        static final int TRANSACTION_acceptRingingCall = 31;
        static final int TRANSACTION_acceptRingingCallWithVideoState = 32;
        static final int TRANSACTION_addNewIncomingCall = 39;
        static final int TRANSACTION_addNewUnknownCall = 40;
        static final int TRANSACTION_addOrRemoveTestCallCompanionApp = 54;
        static final int TRANSACTION_cancelMissedCallsNotification = 33;
        static final int TRANSACTION_clearAccounts = 17;
        static final int TRANSACTION_createManageBlockedNumbersIntent = 44;
        static final int TRANSACTION_dumpCallAnalytics = 24;
        static final int TRANSACTION_enablePhoneAccount = 42;
        static final int TRANSACTION_endCall = 30;
        static final int TRANSACTION_getAdnUriForPhoneAccount = 36;
        static final int TRANSACTION_getAllPhoneAccountHandles = 12;
        static final int TRANSACTION_getAllPhoneAccounts = 11;
        static final int TRANSACTION_getAllPhoneAccountsCount = 10;
        static final int TRANSACTION_getCallCapablePhoneAccounts = 5;
        static final int TRANSACTION_getCallState = 29;
        static final int TRANSACTION_getCurrentTtyMode = 38;
        static final int TRANSACTION_getDefaultDialerPackage = 22;
        static final int TRANSACTION_getDefaultOutgoingPhoneAccount = 2;
        static final int TRANSACTION_getDefaultPhoneApp = 21;
        static final int TRANSACTION_getLine1Number = 20;
        static final int TRANSACTION_getPhoneAccount = 9;
        static final int TRANSACTION_getPhoneAccountsForPackage = 8;
        static final int TRANSACTION_getPhoneAccountsSupportingScheme = 7;
        static final int TRANSACTION_getSelfManagedPhoneAccounts = 6;
        static final int TRANSACTION_getSimCallManager = 13;
        static final int TRANSACTION_getSimCallManagerForUser = 14;
        static final int TRANSACTION_getSystemDialerPackage = 23;
        static final int TRANSACTION_getUserSelectedOutgoingPhoneAccount = 3;
        static final int TRANSACTION_getVoiceMailNumber = 19;
        static final int TRANSACTION_handleCallIntent = 50;
        static final int TRANSACTION_handlePinMmi = 34;
        static final int TRANSACTION_handlePinMmiForPhoneAccount = 35;
        static final int TRANSACTION_isInCall = 26;
        static final int TRANSACTION_isInEmergencyCall = 49;
        static final int TRANSACTION_isInManagedCall = 27;
        static final int TRANSACTION_isIncomingCallPermitted = 45;
        static final int TRANSACTION_isOutgoingCallPermitted = 46;
        static final int TRANSACTION_isRinging = 28;
        static final int TRANSACTION_isTtySupported = 37;
        static final int TRANSACTION_isVoiceMailNumber = 18;
        static final int TRANSACTION_placeCall = 41;
        static final int TRANSACTION_registerPhoneAccount = 15;
        static final int TRANSACTION_setDefaultDialer = 43;
        static final int TRANSACTION_setTestAutoModeApp = 55;
        static final int TRANSACTION_setTestDefaultCallRedirectionApp = 51;
        static final int TRANSACTION_setTestDefaultCallScreeningApp = 53;
        static final int TRANSACTION_setTestDefaultDialer = 56;
        static final int TRANSACTION_setTestPhoneAcctSuggestionComponent = 52;
        static final int TRANSACTION_setUserSelectedOutgoingPhoneAccount = 4;
        static final int TRANSACTION_showInCallScreen = 1;
        static final int TRANSACTION_silenceRinger = 25;
        static final int TRANSACTION_unregisterPhoneAccount = 16;
        static final int TRANSACTION_waitOnHandlers = 47;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelecomService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITelecomService)) {
                return new Proxy(obj);
            }
            return (ITelecomService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "showInCallScreen";
                case 2:
                    return "getDefaultOutgoingPhoneAccount";
                case 3:
                    return "getUserSelectedOutgoingPhoneAccount";
                case 4:
                    return "setUserSelectedOutgoingPhoneAccount";
                case 5:
                    return "getCallCapablePhoneAccounts";
                case 6:
                    return "getSelfManagedPhoneAccounts";
                case 7:
                    return "getPhoneAccountsSupportingScheme";
                case 8:
                    return "getPhoneAccountsForPackage";
                case 9:
                    return "getPhoneAccount";
                case 10:
                    return "getAllPhoneAccountsCount";
                case 11:
                    return "getAllPhoneAccounts";
                case 12:
                    return "getAllPhoneAccountHandles";
                case 13:
                    return "getSimCallManager";
                case 14:
                    return "getSimCallManagerForUser";
                case 15:
                    return "registerPhoneAccount";
                case 16:
                    return "unregisterPhoneAccount";
                case 17:
                    return "clearAccounts";
                case 18:
                    return "isVoiceMailNumber";
                case 19:
                    return "getVoiceMailNumber";
                case 20:
                    return "getLine1Number";
                case 21:
                    return "getDefaultPhoneApp";
                case 22:
                    return "getDefaultDialerPackage";
                case 23:
                    return "getSystemDialerPackage";
                case 24:
                    return "dumpCallAnalytics";
                case 25:
                    return "silenceRinger";
                case 26:
                    return "isInCall";
                case 27:
                    return "isInManagedCall";
                case 28:
                    return "isRinging";
                case 29:
                    return "getCallState";
                case 30:
                    return "endCall";
                case 31:
                    return "acceptRingingCall";
                case 32:
                    return "acceptRingingCallWithVideoState";
                case 33:
                    return "cancelMissedCallsNotification";
                case 34:
                    return "handlePinMmi";
                case 35:
                    return "handlePinMmiForPhoneAccount";
                case 36:
                    return "getAdnUriForPhoneAccount";
                case 37:
                    return "isTtySupported";
                case 38:
                    return "getCurrentTtyMode";
                case 39:
                    return "addNewIncomingCall";
                case 40:
                    return "addNewUnknownCall";
                case 41:
                    return "placeCall";
                case 42:
                    return "enablePhoneAccount";
                case 43:
                    return "setDefaultDialer";
                case 44:
                    return "createManageBlockedNumbersIntent";
                case 45:
                    return "isIncomingCallPermitted";
                case 46:
                    return "isOutgoingCallPermitted";
                case 47:
                    return "waitOnHandlers";
                case 48:
                    return "acceptHandover";
                case 49:
                    return "isInEmergencyCall";
                case 50:
                    return "handleCallIntent";
                case 51:
                    return "setTestDefaultCallRedirectionApp";
                case 52:
                    return "setTestPhoneAcctSuggestionComponent";
                case 53:
                    return "setTestDefaultCallScreeningApp";
                case 54:
                    return "addOrRemoveTestCallCompanionApp";
                case 55:
                    return "setTestAutoModeApp";
                case 56:
                    return "setTestDefaultDialer";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v13, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v49, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v55, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v59, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v63, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v67, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v81, resolved type: android.telecom.PhoneAccountHandle} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v5 */
        /* JADX WARNING: type inference failed for: r3v19 */
        /* JADX WARNING: type inference failed for: r3v22 */
        /* JADX WARNING: type inference failed for: r3v25 */
        /* JADX WARNING: type inference failed for: r3v29 */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v45 */
        /* JADX WARNING: type inference failed for: r3v73 */
        /* JADX WARNING: type inference failed for: r3v77 */
        /* JADX WARNING: type inference failed for: r3v85 */
        /* JADX WARNING: type inference failed for: r3v89 */
        /* JADX WARNING: type inference failed for: r3v90 */
        /* JADX WARNING: type inference failed for: r3v91 */
        /* JADX WARNING: type inference failed for: r3v92 */
        /* JADX WARNING: type inference failed for: r3v93 */
        /* JADX WARNING: type inference failed for: r3v94 */
        /* JADX WARNING: type inference failed for: r3v95 */
        /* JADX WARNING: type inference failed for: r3v96 */
        /* JADX WARNING: type inference failed for: r3v97 */
        /* JADX WARNING: type inference failed for: r3v98 */
        /* JADX WARNING: type inference failed for: r3v99 */
        /* JADX WARNING: type inference failed for: r3v100 */
        /* JADX WARNING: type inference failed for: r3v101 */
        /* JADX WARNING: type inference failed for: r3v102 */
        /* JADX WARNING: type inference failed for: r3v103 */
        /* JADX WARNING: type inference failed for: r3v104 */
        /* JADX WARNING: type inference failed for: r3v105 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "com.android.internal.telecom.ITelecomService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x0547
                r1 = 0
                r3 = 0
                switch(r7) {
                    case 1: goto L_0x0531;
                    case 2: goto L_0x0512;
                    case 3: goto L_0x04f7;
                    case 4: goto L_0x04db;
                    case 5: goto L_0x04c1;
                    case 6: goto L_0x04af;
                    case 7: goto L_0x0499;
                    case 8: goto L_0x0487;
                    case 9: goto L_0x0460;
                    case 10: goto L_0x0452;
                    case 11: goto L_0x0444;
                    case 12: goto L_0x0436;
                    case 13: goto L_0x041b;
                    case 14: goto L_0x0400;
                    case 15: goto L_0x03e4;
                    case 16: goto L_0x03c8;
                    case 17: goto L_0x03ba;
                    case 18: goto L_0x0392;
                    case 19: goto L_0x036e;
                    case 20: goto L_0x034a;
                    case 21: goto L_0x0333;
                    case 22: goto L_0x0325;
                    case 23: goto L_0x0317;
                    case 24: goto L_0x0300;
                    case 25: goto L_0x02f2;
                    case 26: goto L_0x02e0;
                    case 27: goto L_0x02ce;
                    case 28: goto L_0x02bc;
                    case 29: goto L_0x02ae;
                    case 30: goto L_0x029c;
                    case 31: goto L_0x028e;
                    case 32: goto L_0x027c;
                    case 33: goto L_0x026e;
                    case 34: goto L_0x0258;
                    case 35: goto L_0x0230;
                    case 36: goto L_0x0205;
                    case 37: goto L_0x01f3;
                    case 38: goto L_0x01e1;
                    case 39: goto L_0x01b7;
                    case 40: goto L_0x018d;
                    case 41: goto L_0x015f;
                    case 42: goto L_0x0139;
                    case 43: goto L_0x0127;
                    case 44: goto L_0x0110;
                    case 45: goto L_0x00f0;
                    case 46: goto L_0x00d0;
                    case 47: goto L_0x00c6;
                    case 48: goto L_0x0098;
                    case 49: goto L_0x008a;
                    case 50: goto L_0x006e;
                    case 51: goto L_0x0060;
                    case 52: goto L_0x0052;
                    case 53: goto L_0x0044;
                    case 54: goto L_0x002e;
                    case 55: goto L_0x0020;
                    case 56: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0012:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setTestDefaultDialer(r1)
                r9.writeNoException()
                return r2
            L_0x0020:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setTestAutoModeApp(r1)
                r9.writeNoException()
                return r2
            L_0x002e:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x003d
                r1 = r2
            L_0x003d:
                r6.addOrRemoveTestCallCompanionApp(r3, r1)
                r9.writeNoException()
                return r2
            L_0x0044:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setTestDefaultCallScreeningApp(r1)
                r9.writeNoException()
                return r2
            L_0x0052:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setTestPhoneAcctSuggestionComponent(r1)
                r9.writeNoException()
                return r2
            L_0x0060:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.setTestDefaultCallRedirectionApp(r1)
                r9.writeNoException()
                return r2
            L_0x006e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0081
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.content.Intent r3 = (android.content.Intent) r3
                goto L_0x0082
            L_0x0081:
            L_0x0082:
                r1 = r3
                r6.handleCallIntent(r1)
                r9.writeNoException()
                return r2
            L_0x008a:
                r8.enforceInterface(r0)
                boolean r1 = r6.isInEmergencyCall()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x0098:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00aa
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x00ab
            L_0x00aa:
                r1 = r3
            L_0x00ab:
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00be
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r3 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x00bf
            L_0x00be:
            L_0x00bf:
                r6.acceptHandover(r1, r4, r3)
                r9.writeNoException()
                return r2
            L_0x00c6:
                r8.enforceInterface(r0)
                r6.waitOnHandlers()
                r9.writeNoException()
                return r2
            L_0x00d0:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x00e3
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x00e4
            L_0x00e3:
            L_0x00e4:
                r1 = r3
                boolean r3 = r6.isOutgoingCallPermitted(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x00f0:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0103
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x0104
            L_0x0103:
            L_0x0104:
                r1 = r3
                boolean r3 = r6.isIncomingCallPermitted(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0110:
                r8.enforceInterface(r0)
                android.content.Intent r3 = r6.createManageBlockedNumbersIntent()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0123
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x0126
            L_0x0123:
                r9.writeInt(r1)
            L_0x0126:
                return r2
            L_0x0127:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.setDefaultDialer(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0139:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x014b
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r3 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x014c
            L_0x014b:
            L_0x014c:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0154
                r1 = r2
            L_0x0154:
                boolean r4 = r6.enablePhoneAccount(r3, r1)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x015f:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0171
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x0172
            L_0x0171:
                r1 = r3
            L_0x0172:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0181
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x0182
            L_0x0181:
            L_0x0182:
                java.lang.String r4 = r8.readString()
                r6.placeCall(r1, r3, r4)
                r9.writeNoException()
                return r2
            L_0x018d:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x019f
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telecom.PhoneAccountHandle r1 = (android.telecom.PhoneAccountHandle) r1
                goto L_0x01a0
            L_0x019f:
                r1 = r3
            L_0x01a0:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01af
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x01b0
            L_0x01af:
            L_0x01b0:
                r6.addNewUnknownCall(r1, r3)
                r9.writeNoException()
                return r2
            L_0x01b7:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x01c9
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.telecom.PhoneAccountHandle r1 = (android.telecom.PhoneAccountHandle) r1
                goto L_0x01ca
            L_0x01c9:
                r1 = r3
            L_0x01ca:
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x01d9
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x01da
            L_0x01d9:
            L_0x01da:
                r6.addNewIncomingCall(r1, r3)
                r9.writeNoException()
                return r2
            L_0x01e1:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r6.getCurrentTtyMode(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x01f3:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.isTtySupported(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x0205:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0217
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r3 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x0218
            L_0x0217:
            L_0x0218:
                java.lang.String r4 = r8.readString()
                android.net.Uri r5 = r6.getAdnUriForPhoneAccount(r3, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x022c
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x022f
            L_0x022c:
                r9.writeInt(r1)
            L_0x022f:
                return r2
            L_0x0230:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0243
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x0244
            L_0x0243:
            L_0x0244:
                r1 = r3
                java.lang.String r3 = r8.readString()
                java.lang.String r4 = r8.readString()
                boolean r5 = r6.handlePinMmiForPhoneAccount(r1, r3, r4)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x0258:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                boolean r4 = r6.handlePinMmi(r1, r3)
                r9.writeNoException()
                r9.writeInt(r4)
                return r2
            L_0x026e:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.cancelMissedCallsNotification(r1)
                r9.writeNoException()
                return r2
            L_0x027c:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                int r3 = r8.readInt()
                r6.acceptRingingCallWithVideoState(r1, r3)
                r9.writeNoException()
                return r2
            L_0x028e:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.acceptRingingCall(r1)
                r9.writeNoException()
                return r2
            L_0x029c:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.endCall(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x02ae:
                r8.enforceInterface(r0)
                int r1 = r6.getCallState()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x02bc:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.isRinging(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x02ce:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.isInManagedCall(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x02e0:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                boolean r3 = r6.isInCall(r1)
                r9.writeNoException()
                r9.writeInt(r3)
                return r2
            L_0x02f2:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.silenceRinger(r1)
                r9.writeNoException()
                return r2
            L_0x0300:
                r8.enforceInterface(r0)
                android.telecom.TelecomAnalytics r3 = r6.dumpCallAnalytics()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0313
                r9.writeInt(r2)
                r3.writeToParcel(r9, r2)
                goto L_0x0316
            L_0x0313:
                r9.writeInt(r1)
            L_0x0316:
                return r2
            L_0x0317:
                r8.enforceInterface(r0)
                java.lang.String r1 = r6.getSystemDialerPackage()
                r9.writeNoException()
                r9.writeString(r1)
                return r2
            L_0x0325:
                r8.enforceInterface(r0)
                java.lang.String r1 = r6.getDefaultDialerPackage()
                r9.writeNoException()
                r9.writeString(r1)
                return r2
            L_0x0333:
                r8.enforceInterface(r0)
                android.content.ComponentName r3 = r6.getDefaultPhoneApp()
                r9.writeNoException()
                if (r3 == 0) goto L_0x0346
                r9.writeInt(r2)
                r3.writeToParcel((android.os.Parcel) r9, (int) r2)
                goto L_0x0349
            L_0x0346:
                r9.writeInt(r1)
            L_0x0349:
                return r2
            L_0x034a:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x035d
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x035e
            L_0x035d:
            L_0x035e:
                r1 = r3
                java.lang.String r3 = r8.readString()
                java.lang.String r4 = r6.getLine1Number(r1, r3)
                r9.writeNoException()
                r9.writeString(r4)
                return r2
            L_0x036e:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x0381
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x0382
            L_0x0381:
            L_0x0382:
                r1 = r3
                java.lang.String r3 = r8.readString()
                java.lang.String r4 = r6.getVoiceMailNumber(r1, r3)
                r9.writeNoException()
                r9.writeString(r4)
                return r2
            L_0x0392:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x03a5
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x03a6
            L_0x03a5:
            L_0x03a6:
                r1 = r3
                java.lang.String r3 = r8.readString()
                java.lang.String r4 = r8.readString()
                boolean r5 = r6.isVoiceMailNumber(r1, r3, r4)
                r9.writeNoException()
                r9.writeInt(r5)
                return r2
            L_0x03ba:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                r6.clearAccounts(r1)
                r9.writeNoException()
                return r2
            L_0x03c8:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x03db
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x03dc
            L_0x03db:
            L_0x03dc:
                r1 = r3
                r6.unregisterPhoneAccount(r1)
                r9.writeNoException()
                return r2
            L_0x03e4:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x03f7
                android.os.Parcelable$Creator<android.telecom.PhoneAccount> r1 = android.telecom.PhoneAccount.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccount r3 = (android.telecom.PhoneAccount) r3
                goto L_0x03f8
            L_0x03f7:
            L_0x03f8:
                r1 = r3
                r6.registerPhoneAccount(r1)
                r9.writeNoException()
                return r2
            L_0x0400:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                android.telecom.PhoneAccountHandle r4 = r6.getSimCallManagerForUser(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0417
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x041a
            L_0x0417:
                r9.writeInt(r1)
            L_0x041a:
                return r2
            L_0x041b:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                android.telecom.PhoneAccountHandle r4 = r6.getSimCallManager(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0432
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0435
            L_0x0432:
                r9.writeInt(r1)
            L_0x0435:
                return r2
            L_0x0436:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getAllPhoneAccountHandles()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x0444:
                r8.enforceInterface(r0)
                java.util.List r1 = r6.getAllPhoneAccounts()
                r9.writeNoException()
                r9.writeTypedList(r1)
                return r2
            L_0x0452:
                r8.enforceInterface(r0)
                int r1 = r6.getAllPhoneAccountsCount()
                r9.writeNoException()
                r9.writeInt(r1)
                return r2
            L_0x0460:
                r8.enforceInterface(r0)
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0472
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r3 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r8)
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x0473
            L_0x0472:
            L_0x0473:
                android.telecom.PhoneAccount r4 = r6.getPhoneAccount(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x0483
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0486
            L_0x0483:
                r9.writeInt(r1)
            L_0x0486:
                return r2
            L_0x0487:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.util.List r3 = r6.getPhoneAccountsForPackage(r1)
                r9.writeNoException()
                r9.writeTypedList(r3)
                return r2
            L_0x0499:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.lang.String r3 = r8.readString()
                java.util.List r4 = r6.getPhoneAccountsSupportingScheme(r1, r3)
                r9.writeNoException()
                r9.writeTypedList(r4)
                return r2
            L_0x04af:
                r8.enforceInterface(r0)
                java.lang.String r1 = r8.readString()
                java.util.List r3 = r6.getSelfManagedPhoneAccounts(r1)
                r9.writeNoException()
                r9.writeTypedList(r3)
                return r2
            L_0x04c1:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x04cc
                r1 = r2
            L_0x04cc:
                java.lang.String r3 = r8.readString()
                java.util.List r4 = r6.getCallCapablePhoneAccounts(r1, r3)
                r9.writeNoException()
                r9.writeTypedList(r4)
                return r2
            L_0x04db:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                if (r1 == 0) goto L_0x04ee
                android.os.Parcelable$Creator<android.telecom.PhoneAccountHandle> r1 = android.telecom.PhoneAccountHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                r3 = r1
                android.telecom.PhoneAccountHandle r3 = (android.telecom.PhoneAccountHandle) r3
                goto L_0x04ef
            L_0x04ee:
            L_0x04ef:
                r1 = r3
                r6.setUserSelectedOutgoingPhoneAccount(r1)
                r9.writeNoException()
                return r2
            L_0x04f7:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                android.telecom.PhoneAccountHandle r4 = r6.getUserSelectedOutgoingPhoneAccount(r3)
                r9.writeNoException()
                if (r4 == 0) goto L_0x050e
                r9.writeInt(r2)
                r4.writeToParcel(r9, r2)
                goto L_0x0511
            L_0x050e:
                r9.writeInt(r1)
            L_0x0511:
                return r2
            L_0x0512:
                r8.enforceInterface(r0)
                java.lang.String r3 = r8.readString()
                java.lang.String r4 = r8.readString()
                android.telecom.PhoneAccountHandle r5 = r6.getDefaultOutgoingPhoneAccount(r3, r4)
                r9.writeNoException()
                if (r5 == 0) goto L_0x052d
                r9.writeInt(r2)
                r5.writeToParcel(r9, r2)
                goto L_0x0530
            L_0x052d:
                r9.writeInt(r1)
            L_0x0530:
                return r2
            L_0x0531:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                if (r3 == 0) goto L_0x053c
                r1 = r2
            L_0x053c:
                java.lang.String r3 = r8.readString()
                r6.showInCallScreen(r1, r3)
                r9.writeNoException()
                return r2
            L_0x0547:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telecom.ITelecomService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITelecomService {
            public static ITelecomService sDefaultImpl;
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

            public void showInCallScreen(boolean showDialpad, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(showDialpad);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showInCallScreen(showDialpad, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PhoneAccountHandle getDefaultOutgoingPhoneAccount(String uriScheme, String callingPackage) throws RemoteException {
                PhoneAccountHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriScheme);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultOutgoingPhoneAccount(uriScheme, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PhoneAccountHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PhoneAccountHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PhoneAccountHandle getUserSelectedOutgoingPhoneAccount(String callingPackage) throws RemoteException {
                PhoneAccountHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserSelectedOutgoingPhoneAccount(callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PhoneAccountHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PhoneAccountHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserSelectedOutgoingPhoneAccount(PhoneAccountHandle account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserSelectedOutgoingPhoneAccount(account);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PhoneAccountHandle> getCallCapablePhoneAccounts(boolean includeDisabledAccounts, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(includeDisabledAccounts);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallCapablePhoneAccounts(includeDisabledAccounts, callingPackage);
                    }
                    _reply.readException();
                    List<PhoneAccountHandle> _result = _reply.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PhoneAccountHandle> getSelfManagedPhoneAccounts(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSelfManagedPhoneAccounts(callingPackage);
                    }
                    _reply.readException();
                    List<PhoneAccountHandle> _result = _reply.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PhoneAccountHandle> getPhoneAccountsSupportingScheme(String uriScheme, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(uriScheme);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPhoneAccountsSupportingScheme(uriScheme, callingPackage);
                    }
                    _reply.readException();
                    List<PhoneAccountHandle> _result = _reply.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PhoneAccountHandle> getPhoneAccountsForPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPhoneAccountsForPackage(packageName);
                    }
                    _reply.readException();
                    List<PhoneAccountHandle> _result = _reply.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PhoneAccount getPhoneAccount(PhoneAccountHandle account) throws RemoteException {
                PhoneAccount _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPhoneAccount(account);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PhoneAccount.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PhoneAccount _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getAllPhoneAccountsCount() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPhoneAccountsCount();
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

            public List<PhoneAccount> getAllPhoneAccounts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPhoneAccounts();
                    }
                    _reply.readException();
                    List<PhoneAccount> _result = _reply.createTypedArrayList(PhoneAccount.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<PhoneAccountHandle> getAllPhoneAccountHandles() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPhoneAccountHandles();
                    }
                    _reply.readException();
                    List<PhoneAccountHandle> _result = _reply.createTypedArrayList(PhoneAccountHandle.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PhoneAccountHandle getSimCallManager(int subId) throws RemoteException {
                PhoneAccountHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSimCallManager(subId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PhoneAccountHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PhoneAccountHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public PhoneAccountHandle getSimCallManagerForUser(int userId) throws RemoteException {
                PhoneAccountHandle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSimCallManagerForUser(userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PhoneAccountHandle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PhoneAccountHandle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerPhoneAccount(PhoneAccount metadata) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (metadata != null) {
                        _data.writeInt(1);
                        metadata.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerPhoneAccount(metadata);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterPhoneAccount(PhoneAccountHandle account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterPhoneAccount(account);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearAccounts(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearAccounts(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVoiceMailNumber(PhoneAccountHandle accountHandle, String number, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(number);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isVoiceMailNumber(accountHandle, number, callingPackage);
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

            public String getVoiceMailNumber(PhoneAccountHandle accountHandle, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVoiceMailNumber(accountHandle, callingPackage);
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

            public String getLine1Number(PhoneAccountHandle accountHandle, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLine1Number(accountHandle, callingPackage);
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

            public ComponentName getDefaultPhoneApp() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultPhoneApp();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDefaultDialerPackage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultDialerPackage();
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

            public String getSystemDialerPackage() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemDialerPackage();
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

            public TelecomAnalytics dumpCallAnalytics() throws RemoteException {
                TelecomAnalytics _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dumpCallAnalytics();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = TelecomAnalytics.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    TelecomAnalytics _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void silenceRinger(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().silenceRinger(callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInCall(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInCall(callingPackage);
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

            public boolean isInManagedCall(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInManagedCall(callingPackage);
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

            public boolean isRinging(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRinging(callingPackage);
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

            public int getCallState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCallState();
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

            public boolean endCall(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().endCall(callingPackage);
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

            public void acceptRingingCall(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().acceptRingingCall(callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void acceptRingingCallWithVideoState(String callingPackage, int videoState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(videoState);
                    if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().acceptRingingCallWithVideoState(callingPackage, videoState);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelMissedCallsNotification(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelMissedCallsNotification(callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean handlePinMmi(String dialString, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dialString);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().handlePinMmi(dialString, callingPackage);
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

            public boolean handlePinMmiForPhoneAccount(PhoneAccountHandle accountHandle, String dialString, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(dialString);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().handlePinMmiForPhoneAccount(accountHandle, dialString, callingPackage);
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

            public Uri getAdnUriForPhoneAccount(PhoneAccountHandle accountHandle, String callingPackage) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAdnUriForPhoneAccount(accountHandle, callingPackage);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTtySupported(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean z = false;
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isTtySupported(callingPackage);
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

            public int getCurrentTtyMode(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentTtyMode(callingPackage);
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

            public void addNewIncomingCall(PhoneAccountHandle phoneAccount, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccount != null) {
                        _data.writeInt(1);
                        phoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addNewIncomingCall(phoneAccount, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addNewUnknownCall(PhoneAccountHandle phoneAccount, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccount != null) {
                        _data.writeInt(1);
                        phoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(40, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addNewUnknownCall(phoneAccount, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void placeCall(Uri handle, Bundle extras, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (handle != null) {
                        _data.writeInt(1);
                        handle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().placeCall(handle, extras, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean enablePhoneAccount(PhoneAccountHandle accountHandle, boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (accountHandle != null) {
                        _data.writeInt(1);
                        accountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(isEnabled);
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enablePhoneAccount(accountHandle, isEnabled);
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

            public boolean setDefaultDialer(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean z = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDefaultDialer(packageName);
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

            public Intent createManageBlockedNumbersIntent() throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createManageBlockedNumbersIntent();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Intent.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Intent _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIncomingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (phoneAccountHandle != null) {
                        _data.writeInt(1);
                        phoneAccountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isIncomingCallPermitted(phoneAccountHandle);
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

            public boolean isOutgoingCallPermitted(PhoneAccountHandle phoneAccountHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (phoneAccountHandle != null) {
                        _data.writeInt(1);
                        phoneAccountHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isOutgoingCallPermitted(phoneAccountHandle);
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

            public void waitOnHandlers() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().waitOnHandlers();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void acceptHandover(Uri srcAddr, int videoState, PhoneAccountHandle destAcct) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (srcAddr != null) {
                        _data.writeInt(1);
                        srcAddr.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(videoState);
                    if (destAcct != null) {
                        _data.writeInt(1);
                        destAcct.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().acceptHandover(srcAddr, videoState, destAcct);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isInEmergencyCall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isInEmergencyCall();
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

            public void handleCallIntent(Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleCallIntent(intent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestDefaultCallRedirectionApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestDefaultCallRedirectionApp(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestPhoneAcctSuggestionComponent(String flattenedComponentName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(flattenedComponentName);
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestPhoneAcctSuggestionComponent(flattenedComponentName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestDefaultCallScreeningApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestDefaultCallScreeningApp(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addOrRemoveTestCallCompanionApp(String packageName, boolean isAdded) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(isAdded);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addOrRemoveTestCallCompanionApp(packageName, isAdded);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestAutoModeApp(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestAutoModeApp(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setTestDefaultDialer(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setTestDefaultDialer(packageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITelecomService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITelecomService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
