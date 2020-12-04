package android.accounts;

import android.content.IntentSender;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.Map;

public interface IAccountManager extends IInterface {
    boolean accountAuthenticated(Account account) throws RemoteException;

    void addAccount(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException;

    void addAccountAsUser(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle, int i) throws RemoteException;

    boolean addAccountExplicitly(Account account, String str, Bundle bundle) throws RemoteException;

    boolean addAccountExplicitlyWithVisibility(Account account, String str, Bundle bundle, Map map) throws RemoteException;

    void addSharedAccountsFromParentUser(int i, int i2, String str) throws RemoteException;

    void clearPassword(Account account) throws RemoteException;

    void confirmCredentialsAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, Bundle bundle, boolean z, int i) throws RemoteException;

    void copyAccountToUser(IAccountManagerResponse iAccountManagerResponse, Account account, int i, int i2) throws RemoteException;

    IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String str, UserHandle userHandle) throws RemoteException;

    void editProperties(IAccountManagerResponse iAccountManagerResponse, String str, boolean z) throws RemoteException;

    void finishSessionAsUser(IAccountManagerResponse iAccountManagerResponse, Bundle bundle, boolean z, Bundle bundle2, int i) throws RemoteException;

    void getAccountByTypeAndFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException;

    int getAccountVisibility(Account account, String str) throws RemoteException;

    Account[] getAccounts(String str, String str2) throws RemoteException;

    Map getAccountsAndVisibilityForPackage(String str, String str2) throws RemoteException;

    Account[] getAccountsAsUser(String str, int i, String str2) throws RemoteException;

    void getAccountsByFeatures(IAccountManagerResponse iAccountManagerResponse, String str, String[] strArr, String str2) throws RemoteException;

    Account[] getAccountsByTypeForPackage(String str, String str2, String str3) throws RemoteException;

    Account[] getAccountsForPackage(String str, int i, String str2) throws RemoteException;

    void getAuthToken(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, boolean z2, Bundle bundle) throws RemoteException;

    void getAuthTokenLabel(IAccountManagerResponse iAccountManagerResponse, String str, String str2) throws RemoteException;

    AuthenticatorDescription[] getAuthenticatorTypes(int i) throws RemoteException;

    Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException;

    String getPassword(Account account) throws RemoteException;

    String getPreviousName(Account account) throws RemoteException;

    Account[] getSharedAccountsAsUser(int i) throws RemoteException;

    String getUserData(Account account, String str) throws RemoteException;

    boolean hasAccountAccess(Account account, String str, UserHandle userHandle) throws RemoteException;

    void hasFeatures(IAccountManagerResponse iAccountManagerResponse, Account account, String[] strArr, String str) throws RemoteException;

    void invalidateAuthToken(String str, String str2) throws RemoteException;

    void isCredentialsUpdateSuggested(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException;

    void onAccountAccessed(String str) throws RemoteException;

    String peekAuthToken(Account account, String str) throws RemoteException;

    void registerAccountListener(String[] strArr, String str) throws RemoteException;

    void removeAccount(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z) throws RemoteException;

    void removeAccountAsUser(IAccountManagerResponse iAccountManagerResponse, Account account, boolean z, int i) throws RemoteException;

    boolean removeAccountExplicitly(Account account) throws RemoteException;

    boolean removeSharedAccountAsUser(Account account, int i) throws RemoteException;

    void renameAccount(IAccountManagerResponse iAccountManagerResponse, Account account, String str) throws RemoteException;

    boolean renameSharedAccountAsUser(Account account, String str, int i) throws RemoteException;

    boolean setAccountVisibility(Account account, String str, int i) throws RemoteException;

    void setAuthToken(Account account, String str, String str2) throws RemoteException;

    void setPassword(Account account, String str) throws RemoteException;

    void setUserData(Account account, String str, String str2) throws RemoteException;

    boolean someUserHasAccount(Account account) throws RemoteException;

    void startAddAccountSession(IAccountManagerResponse iAccountManagerResponse, String str, String str2, String[] strArr, boolean z, Bundle bundle) throws RemoteException;

    void startUpdateCredentialsSession(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException;

    void unregisterAccountListener(String[] strArr, String str) throws RemoteException;

    void updateAppPermission(Account account, String str, int i, boolean z) throws RemoteException;

    void updateCredentials(IAccountManagerResponse iAccountManagerResponse, Account account, String str, boolean z, Bundle bundle) throws RemoteException;

    public static class Default implements IAccountManager {
        public String getPassword(Account account) throws RemoteException {
            return null;
        }

        public String getUserData(Account account, String key) throws RemoteException {
            return null;
        }

        public AuthenticatorDescription[] getAuthenticatorTypes(int userId) throws RemoteException {
            return null;
        }

        public Account[] getAccounts(String accountType, String opPackageName) throws RemoteException {
            return null;
        }

        public Account[] getAccountsForPackage(String packageName, int uid, String opPackageName) throws RemoteException {
            return null;
        }

        public Account[] getAccountsByTypeForPackage(String type, String packageName, String opPackageName) throws RemoteException {
            return null;
        }

        public Account[] getAccountsAsUser(String accountType, int userId, String opPackageName) throws RemoteException {
            return null;
        }

        public void hasFeatures(IAccountManagerResponse response, Account account, String[] features, String opPackageName) throws RemoteException {
        }

        public void getAccountByTypeAndFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
        }

        public void getAccountsByFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
        }

        public boolean addAccountExplicitly(Account account, String password, Bundle extras) throws RemoteException {
            return false;
        }

        public void removeAccount(IAccountManagerResponse response, Account account, boolean expectActivityLaunch) throws RemoteException {
        }

        public void removeAccountAsUser(IAccountManagerResponse response, Account account, boolean expectActivityLaunch, int userId) throws RemoteException {
        }

        public boolean removeAccountExplicitly(Account account) throws RemoteException {
            return false;
        }

        public void copyAccountToUser(IAccountManagerResponse response, Account account, int userFrom, int userTo) throws RemoteException {
        }

        public void invalidateAuthToken(String accountType, String authToken) throws RemoteException {
        }

        public String peekAuthToken(Account account, String authTokenType) throws RemoteException {
            return null;
        }

        public void setAuthToken(Account account, String authTokenType, String authToken) throws RemoteException {
        }

        public void setPassword(Account account, String password) throws RemoteException {
        }

        public void clearPassword(Account account) throws RemoteException {
        }

        public void setUserData(Account account, String key, String value) throws RemoteException {
        }

        public void updateAppPermission(Account account, String authTokenType, int uid, boolean value) throws RemoteException {
        }

        public void getAuthToken(IAccountManagerResponse response, Account account, String authTokenType, boolean notifyOnAuthFailure, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        public void addAccount(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        public void addAccountAsUser(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options, int userId) throws RemoteException {
        }

        public void updateCredentials(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        public void editProperties(IAccountManagerResponse response, String accountType, boolean expectActivityLaunch) throws RemoteException {
        }

        public void confirmCredentialsAsUser(IAccountManagerResponse response, Account account, Bundle options, boolean expectActivityLaunch, int userId) throws RemoteException {
        }

        public boolean accountAuthenticated(Account account) throws RemoteException {
            return false;
        }

        public void getAuthTokenLabel(IAccountManagerResponse response, String accountType, String authTokenType) throws RemoteException {
        }

        public Account[] getSharedAccountsAsUser(int userId) throws RemoteException {
            return null;
        }

        public boolean removeSharedAccountAsUser(Account account, int userId) throws RemoteException {
            return false;
        }

        public void addSharedAccountsFromParentUser(int parentUserId, int userId, String opPackageName) throws RemoteException {
        }

        public void renameAccount(IAccountManagerResponse response, Account accountToRename, String newName) throws RemoteException {
        }

        public String getPreviousName(Account account) throws RemoteException {
            return null;
        }

        public boolean renameSharedAccountAsUser(Account accountToRename, String newName, int userId) throws RemoteException {
            return false;
        }

        public void startAddAccountSession(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        public void startUpdateCredentialsSession(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        public void finishSessionAsUser(IAccountManagerResponse response, Bundle sessionBundle, boolean expectActivityLaunch, Bundle appInfo, int userId) throws RemoteException {
        }

        public boolean someUserHasAccount(Account account) throws RemoteException {
            return false;
        }

        public void isCredentialsUpdateSuggested(IAccountManagerResponse response, Account account, String statusToken) throws RemoteException {
        }

        public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
            return null;
        }

        public boolean addAccountExplicitlyWithVisibility(Account account, String password, Bundle extras, Map visibility) throws RemoteException {
            return false;
        }

        public boolean setAccountVisibility(Account a, String packageName, int newVisibility) throws RemoteException {
            return false;
        }

        public int getAccountVisibility(Account a, String packageName) throws RemoteException {
            return 0;
        }

        public Map getAccountsAndVisibilityForPackage(String packageName, String accountType) throws RemoteException {
            return null;
        }

        public void registerAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
        }

        public void unregisterAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
        }

        public boolean hasAccountAccess(Account account, String packageName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String packageName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        public void onAccountAccessed(String token) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccountManager {
        private static final String DESCRIPTOR = "android.accounts.IAccountManager";
        static final int TRANSACTION_accountAuthenticated = 29;
        static final int TRANSACTION_addAccount = 24;
        static final int TRANSACTION_addAccountAsUser = 25;
        static final int TRANSACTION_addAccountExplicitly = 11;
        static final int TRANSACTION_addAccountExplicitlyWithVisibility = 43;
        static final int TRANSACTION_addSharedAccountsFromParentUser = 33;
        static final int TRANSACTION_clearPassword = 20;
        static final int TRANSACTION_confirmCredentialsAsUser = 28;
        static final int TRANSACTION_copyAccountToUser = 15;
        static final int TRANSACTION_createRequestAccountAccessIntentSenderAsUser = 50;
        static final int TRANSACTION_editProperties = 27;
        static final int TRANSACTION_finishSessionAsUser = 39;
        static final int TRANSACTION_getAccountByTypeAndFeatures = 9;
        static final int TRANSACTION_getAccountVisibility = 45;
        static final int TRANSACTION_getAccounts = 4;
        static final int TRANSACTION_getAccountsAndVisibilityForPackage = 46;
        static final int TRANSACTION_getAccountsAsUser = 7;
        static final int TRANSACTION_getAccountsByFeatures = 10;
        static final int TRANSACTION_getAccountsByTypeForPackage = 6;
        static final int TRANSACTION_getAccountsForPackage = 5;
        static final int TRANSACTION_getAuthToken = 23;
        static final int TRANSACTION_getAuthTokenLabel = 30;
        static final int TRANSACTION_getAuthenticatorTypes = 3;
        static final int TRANSACTION_getPackagesAndVisibilityForAccount = 42;
        static final int TRANSACTION_getPassword = 1;
        static final int TRANSACTION_getPreviousName = 35;
        static final int TRANSACTION_getSharedAccountsAsUser = 31;
        static final int TRANSACTION_getUserData = 2;
        static final int TRANSACTION_hasAccountAccess = 49;
        static final int TRANSACTION_hasFeatures = 8;
        static final int TRANSACTION_invalidateAuthToken = 16;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 41;
        static final int TRANSACTION_onAccountAccessed = 51;
        static final int TRANSACTION_peekAuthToken = 17;
        static final int TRANSACTION_registerAccountListener = 47;
        static final int TRANSACTION_removeAccount = 12;
        static final int TRANSACTION_removeAccountAsUser = 13;
        static final int TRANSACTION_removeAccountExplicitly = 14;
        static final int TRANSACTION_removeSharedAccountAsUser = 32;
        static final int TRANSACTION_renameAccount = 34;
        static final int TRANSACTION_renameSharedAccountAsUser = 36;
        static final int TRANSACTION_setAccountVisibility = 44;
        static final int TRANSACTION_setAuthToken = 18;
        static final int TRANSACTION_setPassword = 19;
        static final int TRANSACTION_setUserData = 21;
        static final int TRANSACTION_someUserHasAccount = 40;
        static final int TRANSACTION_startAddAccountSession = 37;
        static final int TRANSACTION_startUpdateCredentialsSession = 38;
        static final int TRANSACTION_unregisterAccountListener = 48;
        static final int TRANSACTION_updateAppPermission = 22;
        static final int TRANSACTION_updateCredentials = 26;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccountManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccountManager)) {
                return new Proxy(obj);
            }
            return (IAccountManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getPassword";
                case 2:
                    return "getUserData";
                case 3:
                    return "getAuthenticatorTypes";
                case 4:
                    return "getAccounts";
                case 5:
                    return "getAccountsForPackage";
                case 6:
                    return "getAccountsByTypeForPackage";
                case 7:
                    return "getAccountsAsUser";
                case 8:
                    return "hasFeatures";
                case 9:
                    return "getAccountByTypeAndFeatures";
                case 10:
                    return "getAccountsByFeatures";
                case 11:
                    return "addAccountExplicitly";
                case 12:
                    return "removeAccount";
                case 13:
                    return "removeAccountAsUser";
                case 14:
                    return "removeAccountExplicitly";
                case 15:
                    return "copyAccountToUser";
                case 16:
                    return "invalidateAuthToken";
                case 17:
                    return "peekAuthToken";
                case 18:
                    return "setAuthToken";
                case 19:
                    return "setPassword";
                case 20:
                    return "clearPassword";
                case 21:
                    return "setUserData";
                case 22:
                    return "updateAppPermission";
                case 23:
                    return "getAuthToken";
                case 24:
                    return "addAccount";
                case 25:
                    return "addAccountAsUser";
                case 26:
                    return "updateCredentials";
                case 27:
                    return "editProperties";
                case 28:
                    return "confirmCredentialsAsUser";
                case 29:
                    return "accountAuthenticated";
                case 30:
                    return "getAuthTokenLabel";
                case 31:
                    return "getSharedAccountsAsUser";
                case 32:
                    return "removeSharedAccountAsUser";
                case 33:
                    return "addSharedAccountsFromParentUser";
                case 34:
                    return "renameAccount";
                case 35:
                    return "getPreviousName";
                case 36:
                    return "renameSharedAccountAsUser";
                case 37:
                    return "startAddAccountSession";
                case 38:
                    return "startUpdateCredentialsSession";
                case 39:
                    return "finishSessionAsUser";
                case 40:
                    return "someUserHasAccount";
                case 41:
                    return "isCredentialsUpdateSuggested";
                case 42:
                    return "getPackagesAndVisibilityForAccount";
                case 43:
                    return "addAccountExplicitlyWithVisibility";
                case 44:
                    return "setAccountVisibility";
                case 45:
                    return "getAccountVisibility";
                case 46:
                    return "getAccountsAndVisibilityForPackage";
                case 47:
                    return "registerAccountListener";
                case 48:
                    return "unregisterAccountListener";
                case 49:
                    return "hasAccountAccess";
                case 50:
                    return "createRequestAccountAccessIntentSenderAsUser";
                case 51:
                    return "onAccountAccessed";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v20, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v24, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v36, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v60, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v87, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v106, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v114, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v129, resolved type: android.os.UserHandle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v133, resolved type: android.os.UserHandle} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v5 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v56 */
        /* JADX WARNING: type inference failed for: r1v70 */
        /* JADX WARNING: type inference failed for: r1v76 */
        /* JADX WARNING: type inference failed for: r1v82 */
        /* JADX WARNING: type inference failed for: r1v91 */
        /* JADX WARNING: type inference failed for: r1v95 */
        /* JADX WARNING: type inference failed for: r1v102 */
        /* JADX WARNING: type inference failed for: r1v110 */
        /* JADX WARNING: type inference failed for: r1v118 */
        /* JADX WARNING: type inference failed for: r1v122 */
        /* JADX WARNING: type inference failed for: r1v137 */
        /* JADX WARNING: type inference failed for: r1v138 */
        /* JADX WARNING: type inference failed for: r1v139 */
        /* JADX WARNING: type inference failed for: r1v140 */
        /* JADX WARNING: type inference failed for: r1v141 */
        /* JADX WARNING: type inference failed for: r1v142 */
        /* JADX WARNING: type inference failed for: r1v143 */
        /* JADX WARNING: type inference failed for: r1v144 */
        /* JADX WARNING: type inference failed for: r1v145 */
        /* JADX WARNING: type inference failed for: r1v146 */
        /* JADX WARNING: type inference failed for: r1v147 */
        /* JADX WARNING: type inference failed for: r1v148 */
        /* JADX WARNING: type inference failed for: r1v149 */
        /* JADX WARNING: type inference failed for: r1v150 */
        /* JADX WARNING: type inference failed for: r1v151 */
        /* JADX WARNING: type inference failed for: r1v152 */
        /* JADX WARNING: type inference failed for: r1v153 */
        /* JADX WARNING: type inference failed for: r1v154 */
        /* JADX WARNING: type inference failed for: r1v155 */
        /* JADX WARNING: type inference failed for: r1v156 */
        /* JADX WARNING: type inference failed for: r1v157 */
        /* JADX WARNING: type inference failed for: r1v158 */
        /* JADX WARNING: type inference failed for: r1v159 */
        /* JADX WARNING: type inference failed for: r1v160 */
        /* JADX WARNING: type inference failed for: r1v161 */
        /* JADX WARNING: type inference failed for: r1v162 */
        /* JADX WARNING: type inference failed for: r1v163 */
        /* JADX WARNING: type inference failed for: r1v164 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r8 = r19
                r9 = r20
                r10 = r21
                r11 = r22
                java.lang.String r12 = "android.accounts.IAccountManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x07ca
                r0 = 0
                r1 = 0
                switch(r9) {
                    case 1: goto L_0x07aa;
                    case 2: goto L_0x0786;
                    case 3: goto L_0x0774;
                    case 4: goto L_0x075e;
                    case 5: goto L_0x0744;
                    case 6: goto L_0x072a;
                    case 7: goto L_0x0710;
                    case 8: goto L_0x06e6;
                    case 9: goto L_0x06c8;
                    case 10: goto L_0x06aa;
                    case 11: goto L_0x0678;
                    case 12: goto L_0x064e;
                    case 13: goto L_0x0620;
                    case 14: goto L_0x0600;
                    case 15: goto L_0x05d6;
                    case 16: goto L_0x05c4;
                    case 17: goto L_0x05a0;
                    case 18: goto L_0x057c;
                    case 19: goto L_0x055c;
                    case 20: goto L_0x0540;
                    case 21: goto L_0x051c;
                    case 22: goto L_0x04f2;
                    case 23: goto L_0x04a5;
                    case 24: goto L_0x0466;
                    case 25: goto L_0x0420;
                    case 26: goto L_0x03dc;
                    case 27: goto L_0x03be;
                    case 28: goto L_0x037a;
                    case 29: goto L_0x035a;
                    case 30: goto L_0x0340;
                    case 31: goto L_0x032e;
                    case 32: goto L_0x030a;
                    case 33: goto L_0x02f4;
                    case 34: goto L_0x02ce;
                    case 35: goto L_0x02ae;
                    case 36: goto L_0x0286;
                    case 37: goto L_0x0247;
                    case 38: goto L_0x0203;
                    case 39: goto L_0x01bf;
                    case 40: goto L_0x019f;
                    case 41: goto L_0x0179;
                    case 42: goto L_0x0159;
                    case 43: goto L_0x011b;
                    case 44: goto L_0x00f3;
                    case 45: goto L_0x00cf;
                    case 46: goto L_0x00b9;
                    case 47: goto L_0x00a7;
                    case 48: goto L_0x0095;
                    case 49: goto L_0x0063;
                    case 50: goto L_0x0028;
                    case 51: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                r8.onAccountAccessed(r0)
                r22.writeNoException()
                return r13
            L_0x0028:
                r10.enforceInterface(r12)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x003a
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x003b
            L_0x003a:
                r2 = r1
            L_0x003b:
                java.lang.String r3 = r21.readString()
                int r4 = r21.readInt()
                if (r4 == 0) goto L_0x004e
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.UserHandle r1 = (android.os.UserHandle) r1
                goto L_0x004f
            L_0x004e:
            L_0x004f:
                android.content.IntentSender r4 = r8.createRequestAccountAccessIntentSenderAsUser(r2, r3, r1)
                r22.writeNoException()
                if (r4 == 0) goto L_0x005f
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x0062
            L_0x005f:
                r11.writeInt(r0)
            L_0x0062:
                return r13
            L_0x0063:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0075
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0076
            L_0x0075:
                r0 = r1
            L_0x0076:
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0089
                android.os.Parcelable$Creator<android.os.UserHandle> r1 = android.os.UserHandle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.UserHandle r1 = (android.os.UserHandle) r1
                goto L_0x008a
            L_0x0089:
            L_0x008a:
                boolean r3 = r8.hasAccountAccess(r0, r2, r1)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0095:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r21.createStringArray()
                java.lang.String r1 = r21.readString()
                r8.unregisterAccountListener(r0, r1)
                r22.writeNoException()
                return r13
            L_0x00a7:
                r10.enforceInterface(r12)
                java.lang.String[] r0 = r21.createStringArray()
                java.lang.String r1 = r21.readString()
                r8.registerAccountListener(r0, r1)
                r22.writeNoException()
                return r13
            L_0x00b9:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                java.util.Map r2 = r8.getAccountsAndVisibilityForPackage(r0, r1)
                r22.writeNoException()
                r11.writeMap(r2)
                return r13
            L_0x00cf:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x00e2
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x00e3
            L_0x00e2:
            L_0x00e3:
                r0 = r1
                java.lang.String r1 = r21.readString()
                int r2 = r8.getAccountVisibility(r0, r1)
                r22.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x00f3:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0106
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0107
            L_0x0106:
            L_0x0107:
                r0 = r1
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                boolean r3 = r8.setAccountVisibility(r0, r1, r2)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x011b:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x012d
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x012e
            L_0x012d:
                r0 = r1
            L_0x012e:
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0141
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0142
            L_0x0141:
            L_0x0142:
                java.lang.Class r3 = r19.getClass()
                java.lang.ClassLoader r3 = r3.getClassLoader()
                java.util.HashMap r4 = r10.readHashMap(r3)
                boolean r5 = r8.addAccountExplicitlyWithVisibility(r0, r2, r1, r4)
                r22.writeNoException()
                r11.writeInt(r5)
                return r13
            L_0x0159:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x016c
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x016d
            L_0x016c:
            L_0x016d:
                r0 = r1
                java.util.Map r1 = r8.getPackagesAndVisibilityForAccount(r0)
                r22.writeNoException()
                r11.writeMap(r1)
                return r13
            L_0x0179:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0193
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0194
            L_0x0193:
            L_0x0194:
                java.lang.String r2 = r21.readString()
                r8.isCredentialsUpdateSuggested(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x019f:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x01b2
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x01b3
            L_0x01b2:
            L_0x01b3:
                r0 = r1
                boolean r1 = r8.someUserHasAccount(r0)
                r22.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x01bf:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r6 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x01d9
                android.os.Parcelable$Creator<android.os.Bundle> r2 = android.os.Bundle.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.os.Bundle r2 = (android.os.Bundle) r2
                goto L_0x01da
            L_0x01d9:
                r2 = r1
            L_0x01da:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x01e2
                r3 = r13
                goto L_0x01e3
            L_0x01e2:
                r3 = r0
            L_0x01e3:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x01f3
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r4 = r0
                goto L_0x01f4
            L_0x01f3:
                r4 = r1
            L_0x01f4:
                int r7 = r21.readInt()
                r0 = r19
                r1 = r6
                r5 = r7
                r0.finishSessionAsUser(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r13
            L_0x0203:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r6 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x021d
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x021e
            L_0x021d:
                r2 = r1
            L_0x021e:
                java.lang.String r7 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x022a
                r4 = r13
                goto L_0x022b
            L_0x022a:
                r4 = r0
            L_0x022b:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x023b
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r5 = r0
                goto L_0x023c
            L_0x023b:
                r5 = r1
            L_0x023c:
                r0 = r19
                r1 = r6
                r3 = r7
                r0.startUpdateCredentialsSession(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r13
            L_0x0247:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r7 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                java.lang.String r14 = r21.readString()
                java.lang.String r15 = r21.readString()
                java.lang.String[] r16 = r21.createStringArray()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0266
                r5 = r13
                goto L_0x0267
            L_0x0266:
                r5 = r0
            L_0x0267:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0277
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r6 = r0
                goto L_0x0278
            L_0x0277:
                r6 = r1
            L_0x0278:
                r0 = r19
                r1 = r7
                r2 = r14
                r3 = r15
                r4 = r16
                r0.startAddAccountSession(r1, r2, r3, r4, r5, r6)
                r22.writeNoException()
                return r13
            L_0x0286:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0299
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x029a
            L_0x0299:
            L_0x029a:
                r0 = r1
                java.lang.String r1 = r21.readString()
                int r2 = r21.readInt()
                boolean r3 = r8.renameSharedAccountAsUser(r0, r1, r2)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x02ae:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x02c1
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x02c2
            L_0x02c1:
            L_0x02c2:
                r0 = r1
                java.lang.String r1 = r8.getPreviousName(r0)
                r22.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x02ce:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x02e8
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x02e9
            L_0x02e8:
            L_0x02e9:
                java.lang.String r2 = r21.readString()
                r8.renameAccount(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x02f4:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                r8.addSharedAccountsFromParentUser(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x030a:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x031d
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x031e
            L_0x031d:
            L_0x031e:
                r0 = r1
                int r1 = r21.readInt()
                boolean r2 = r8.removeSharedAccountAsUser(r0, r1)
                r22.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x032e:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                android.accounts.Account[] r1 = r8.getSharedAccountsAsUser(r0)
                r22.writeNoException()
                r11.writeTypedArray(r1, r13)
                return r13
            L_0x0340:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r8.getAuthTokenLabel(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x035a:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x036d
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x036e
            L_0x036d:
            L_0x036e:
                r0 = r1
                boolean r1 = r8.accountAuthenticated(r0)
                r22.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x037a:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r6 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0394
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x0395
            L_0x0394:
                r2 = r1
            L_0x0395:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x03a5
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x03a3:
                r3 = r1
                goto L_0x03a6
            L_0x03a5:
                goto L_0x03a3
            L_0x03a6:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x03ae
                r4 = r13
                goto L_0x03af
            L_0x03ae:
                r4 = r0
            L_0x03af:
                int r7 = r21.readInt()
                r0 = r19
                r1 = r6
                r5 = r7
                r0.confirmCredentialsAsUser(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r13
            L_0x03be:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r1 = android.accounts.IAccountManagerResponse.Stub.asInterface(r1)
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x03d5
                r0 = r13
            L_0x03d5:
                r8.editProperties(r1, r2, r0)
                r22.writeNoException()
                return r13
            L_0x03dc:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r6 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x03f6
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x03f7
            L_0x03f6:
                r2 = r1
            L_0x03f7:
                java.lang.String r7 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0403
                r4 = r13
                goto L_0x0404
            L_0x0403:
                r4 = r0
            L_0x0404:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0414
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r5 = r0
                goto L_0x0415
            L_0x0414:
                r5 = r1
            L_0x0415:
                r0 = r19
                r1 = r6
                r3 = r7
                r0.updateCredentials(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r13
            L_0x0420:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r14 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                java.lang.String r15 = r21.readString()
                java.lang.String r16 = r21.readString()
                java.lang.String[] r17 = r21.createStringArray()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x043f
                r5 = r13
                goto L_0x0440
            L_0x043f:
                r5 = r0
            L_0x0440:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0450
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r6 = r0
                goto L_0x0451
            L_0x0450:
                r6 = r1
            L_0x0451:
                int r18 = r21.readInt()
                r0 = r19
                r1 = r14
                r2 = r15
                r3 = r16
                r4 = r17
                r7 = r18
                r0.addAccountAsUser(r1, r2, r3, r4, r5, r6, r7)
                r22.writeNoException()
                return r13
            L_0x0466:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r7 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                java.lang.String r14 = r21.readString()
                java.lang.String r15 = r21.readString()
                java.lang.String[] r16 = r21.createStringArray()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0485
                r5 = r13
                goto L_0x0486
            L_0x0485:
                r5 = r0
            L_0x0486:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0496
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r6 = r0
                goto L_0x0497
            L_0x0496:
                r6 = r1
            L_0x0497:
                r0 = r19
                r1 = r7
                r2 = r14
                r3 = r15
                r4 = r16
                r0.addAccount(r1, r2, r3, r4, r5, r6)
                r22.writeNoException()
                return r13
            L_0x04a5:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r7 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x04bf
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x04c0
            L_0x04bf:
                r2 = r1
            L_0x04c0:
                java.lang.String r14 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x04cc
                r4 = r13
                goto L_0x04cd
            L_0x04cc:
                r4 = r0
            L_0x04cd:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x04d5
                r5 = r13
                goto L_0x04d6
            L_0x04d5:
                r5 = r0
            L_0x04d6:
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x04e6
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r6 = r0
                goto L_0x04e7
            L_0x04e6:
                r6 = r1
            L_0x04e7:
                r0 = r19
                r1 = r7
                r3 = r14
                r0.getAuthToken(r1, r2, r3, r4, r5, r6)
                r22.writeNoException()
                return r13
            L_0x04f2:
                r10.enforceInterface(r12)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0504
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0505
            L_0x0504:
            L_0x0505:
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                int r4 = r21.readInt()
                if (r4 == 0) goto L_0x0515
                r0 = r13
            L_0x0515:
                r8.updateAppPermission(r1, r2, r3, r0)
                r22.writeNoException()
                return r13
            L_0x051c:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x052f
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0530
            L_0x052f:
            L_0x0530:
                r0 = r1
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r8.setUserData(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x0540:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0553
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0554
            L_0x0553:
            L_0x0554:
                r0 = r1
                r8.clearPassword(r0)
                r22.writeNoException()
                return r13
            L_0x055c:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x056f
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0570
            L_0x056f:
            L_0x0570:
                r0 = r1
                java.lang.String r1 = r21.readString()
                r8.setPassword(r0, r1)
                r22.writeNoException()
                return r13
            L_0x057c:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x058f
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0590
            L_0x058f:
            L_0x0590:
                r0 = r1
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                r8.setAuthToken(r0, r1, r2)
                r22.writeNoException()
                return r13
            L_0x05a0:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x05b3
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x05b4
            L_0x05b3:
            L_0x05b4:
                r0 = r1
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r8.peekAuthToken(r0, r1)
                r22.writeNoException()
                r11.writeString(r2)
                return r13
            L_0x05c4:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                r8.invalidateAuthToken(r0, r1)
                r22.writeNoException()
                return r13
            L_0x05d6:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x05f0
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x05f1
            L_0x05f0:
            L_0x05f1:
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                r8.copyAccountToUser(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x0600:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0613
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0614
            L_0x0613:
            L_0x0614:
                r0 = r1
                boolean r1 = r8.removeAccountExplicitly(r0)
                r22.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0620:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r2 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x063a
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x063b
            L_0x063a:
            L_0x063b:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0643
                r0 = r13
            L_0x0643:
                int r3 = r21.readInt()
                r8.removeAccountAsUser(r2, r1, r0, r3)
                r22.writeNoException()
                return r13
            L_0x064e:
                r10.enforceInterface(r12)
                android.os.IBinder r2 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r2 = android.accounts.IAccountManagerResponse.Stub.asInterface(r2)
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0668
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0669
            L_0x0668:
            L_0x0669:
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0671
                r0 = r13
            L_0x0671:
                r8.removeAccount(r2, r1, r0)
                r22.writeNoException()
                return r13
            L_0x0678:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x068a
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x068b
            L_0x068a:
                r0 = r1
            L_0x068b:
                java.lang.String r2 = r21.readString()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x069e
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x069f
            L_0x069e:
            L_0x069f:
                boolean r3 = r8.addAccountExplicitly(r0, r2, r1)
                r22.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x06aa:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                java.lang.String r1 = r21.readString()
                java.lang.String[] r2 = r21.createStringArray()
                java.lang.String r3 = r21.readString()
                r8.getAccountsByFeatures(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x06c8:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                java.lang.String r1 = r21.readString()
                java.lang.String[] r2 = r21.createStringArray()
                java.lang.String r3 = r21.readString()
                r8.getAccountByTypeAndFeatures(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x06e6:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.accounts.IAccountManagerResponse r0 = android.accounts.IAccountManagerResponse.Stub.asInterface(r0)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0700
                android.os.Parcelable$Creator<android.accounts.Account> r1 = android.accounts.Account.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x0701
            L_0x0700:
            L_0x0701:
                java.lang.String[] r2 = r21.createStringArray()
                java.lang.String r3 = r21.readString()
                r8.hasFeatures(r0, r1, r2, r3)
                r22.writeNoException()
                return r13
            L_0x0710:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.accounts.Account[] r3 = r8.getAccountsAsUser(r0, r1, r2)
                r22.writeNoException()
                r11.writeTypedArray(r3, r13)
                return r13
            L_0x072a:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r21.readString()
                android.accounts.Account[] r3 = r8.getAccountsByTypeForPackage(r0, r1, r2)
                r22.writeNoException()
                r11.writeTypedArray(r3, r13)
                return r13
            L_0x0744:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                int r1 = r21.readInt()
                java.lang.String r2 = r21.readString()
                android.accounts.Account[] r3 = r8.getAccountsForPackage(r0, r1, r2)
                r22.writeNoException()
                r11.writeTypedArray(r3, r13)
                return r13
            L_0x075e:
                r10.enforceInterface(r12)
                java.lang.String r0 = r21.readString()
                java.lang.String r1 = r21.readString()
                android.accounts.Account[] r2 = r8.getAccounts(r0, r1)
                r22.writeNoException()
                r11.writeTypedArray(r2, r13)
                return r13
            L_0x0774:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                android.accounts.AuthenticatorDescription[] r1 = r8.getAuthenticatorTypes(r0)
                r22.writeNoException()
                r11.writeTypedArray(r1, r13)
                return r13
            L_0x0786:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0799
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x079a
            L_0x0799:
            L_0x079a:
                r0 = r1
                java.lang.String r1 = r21.readString()
                java.lang.String r2 = r8.getUserData(r0, r1)
                r22.writeNoException()
                r11.writeString(r2)
                return r13
            L_0x07aa:
                r10.enforceInterface(r12)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x07bd
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.accounts.Account r1 = (android.accounts.Account) r1
                goto L_0x07be
            L_0x07bd:
            L_0x07be:
                r0 = r1
                java.lang.String r1 = r8.getPassword(r0)
                r22.writeNoException()
                r11.writeString(r1)
                return r13
            L_0x07ca:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.accounts.IAccountManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAccountManager {
            public static IAccountManager sDefaultImpl;
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

            public String getPassword(Account account) throws RemoteException {
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
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPassword(account);
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

            public String getUserData(Account account, String key) throws RemoteException {
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
                    _data.writeString(key);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserData(account, key);
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

            public AuthenticatorDescription[] getAuthenticatorTypes(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAuthenticatorTypes(userId);
                    }
                    _reply.readException();
                    AuthenticatorDescription[] _result = (AuthenticatorDescription[]) _reply.createTypedArray(AuthenticatorDescription.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Account[] getAccounts(String accountType, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountType);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccounts(accountType, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Account[] getAccountsForPackage(String packageName, int uid, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsForPackage(packageName, uid, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Account[] getAccountsByTypeForPackage(String type, String packageName, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(packageName);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsByTypeForPackage(type, packageName, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Account[] getAccountsAsUser(String accountType, int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountType);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsAsUser(accountType, userId, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void hasFeatures(IAccountManagerResponse response, Account account, String[] features, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStringArray(features);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().hasFeatures(response, account, features, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getAccountByTypeAndFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeStringArray(features);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getAccountByTypeAndFeatures(response, accountType, features, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getAccountsByFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeStringArray(features);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getAccountsByFeatures(response, accountType, features, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addAccountExplicitly(Account account, String password, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(password);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAccountExplicitly(account, password, extras);
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

            public void removeAccount(IAccountManagerResponse response, Account account, boolean expectActivityLaunch) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(expectActivityLaunch);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeAccount(response, account, expectActivityLaunch);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeAccountAsUser(IAccountManagerResponse response, Account account, boolean expectActivityLaunch, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(expectActivityLaunch);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeAccountAsUser(response, account, expectActivityLaunch, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeAccountExplicitly(Account account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeAccountExplicitly(account);
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

            public void copyAccountToUser(IAccountManagerResponse response, Account account, int userFrom, int userTo) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userFrom);
                    _data.writeInt(userTo);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().copyAccountToUser(response, account, userFrom, userTo);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void invalidateAuthToken(String accountType, String authToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountType);
                    _data.writeString(authToken);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().invalidateAuthToken(accountType, authToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String peekAuthToken(Account account, String authTokenType) throws RemoteException {
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
                    _data.writeString(authTokenType);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().peekAuthToken(account, authTokenType);
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

            public void setAuthToken(Account account, String authTokenType, String authToken) throws RemoteException {
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
                    _data.writeString(authTokenType);
                    _data.writeString(authToken);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAuthToken(account, authTokenType, authToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPassword(Account account, String password) throws RemoteException {
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
                    _data.writeString(password);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setPassword(account, password);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearPassword(Account account) throws RemoteException {
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
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().clearPassword(account);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserData(Account account, String key, String value) throws RemoteException {
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
                    _data.writeString(key);
                    _data.writeString(value);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserData(account, key, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateAppPermission(Account account, String authTokenType, int uid, boolean value) throws RemoteException {
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
                    _data.writeString(authTokenType);
                    _data.writeInt(uid);
                    _data.writeInt(value);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateAppPermission(account, authTokenType, uid, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getAuthToken(IAccountManagerResponse response, Account account, String authTokenType, boolean notifyOnAuthFailure, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Account account2 = account;
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account2 != null) {
                        _data.writeInt(1);
                        account2.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeString(authTokenType);
                    } catch (Throwable th) {
                        th = th;
                        boolean z = notifyOnAuthFailure;
                        boolean z2 = expectActivityLaunch;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(notifyOnAuthFailure ? 1 : 0);
                        try {
                            _data.writeInt(expectActivityLaunch ? 1 : 0);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().getAuthToken(response, account, authTokenType, notifyOnAuthFailure, expectActivityLaunch, options);
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
                        boolean z22 = expectActivityLaunch;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = authTokenType;
                    boolean z3 = notifyOnAuthFailure;
                    boolean z222 = expectActivityLaunch;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void addAccount(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    try {
                        _data.writeString(accountType);
                    } catch (Throwable th) {
                        th = th;
                        String str = authTokenType;
                        String[] strArr = requiredFeatures;
                        boolean z = expectActivityLaunch;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(authTokenType);
                        try {
                            _data.writeStringArray(requiredFeatures);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z2 = expectActivityLaunch;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(expectActivityLaunch ? 1 : 0);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().addAccount(response, accountType, authTokenType, requiredFeatures, expectActivityLaunch, options);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String[] strArr2 = requiredFeatures;
                        boolean z22 = expectActivityLaunch;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str2 = accountType;
                    String str3 = authTokenType;
                    String[] strArr22 = requiredFeatures;
                    boolean z222 = expectActivityLaunch;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void addAccountAsUser(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options, int userId) throws RemoteException {
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    try {
                        _data.writeString(accountType);
                    } catch (Throwable th) {
                        th = th;
                        String str = authTokenType;
                        String[] strArr = requiredFeatures;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(authTokenType);
                        try {
                            _data.writeStringArray(requiredFeatures);
                            _data.writeInt(expectActivityLaunch ? 1 : 0);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(userId);
                            if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().addAccountAsUser(response, accountType, authTokenType, requiredFeatures, expectActivityLaunch, options, userId);
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
                        String[] strArr2 = requiredFeatures;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str2 = accountType;
                    String str3 = authTokenType;
                    String[] strArr22 = requiredFeatures;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void updateCredentials(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authTokenType);
                    _data.writeInt(expectActivityLaunch);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateCredentials(response, account, authTokenType, expectActivityLaunch, options);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void editProperties(IAccountManagerResponse response, String accountType, boolean expectActivityLaunch) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeInt(expectActivityLaunch);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().editProperties(response, accountType, expectActivityLaunch);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void confirmCredentialsAsUser(IAccountManagerResponse response, Account account, Bundle options, boolean expectActivityLaunch, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(expectActivityLaunch);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().confirmCredentialsAsUser(response, account, options, expectActivityLaunch, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean accountAuthenticated(Account account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().accountAuthenticated(account);
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

            public void getAuthTokenLabel(IAccountManagerResponse response, String accountType, String authTokenType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeString(authTokenType);
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getAuthTokenLabel(response, accountType, authTokenType);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Account[] getSharedAccountsAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSharedAccountsAsUser(userId);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeSharedAccountAsUser(Account account, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeSharedAccountAsUser(account, userId);
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

            public void addSharedAccountsFromParentUser(int parentUserId, int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(parentUserId);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(33, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addSharedAccountsFromParentUser(parentUserId, userId, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void renameAccount(IAccountManagerResponse response, Account accountToRename, String newName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (accountToRename != null) {
                        _data.writeInt(1);
                        accountToRename.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(newName);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().renameAccount(response, accountToRename, newName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getPreviousName(Account account) throws RemoteException {
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
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreviousName(account);
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

            public boolean renameSharedAccountAsUser(Account accountToRename, String newName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (accountToRename != null) {
                        _data.writeInt(1);
                        accountToRename.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(newName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(36, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().renameSharedAccountAsUser(accountToRename, newName, userId);
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

            public void startAddAccountSession(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    try {
                        _data.writeString(accountType);
                    } catch (Throwable th) {
                        th = th;
                        String str = authTokenType;
                        String[] strArr = requiredFeatures;
                        boolean z = expectActivityLaunch;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(authTokenType);
                        try {
                            _data.writeStringArray(requiredFeatures);
                        } catch (Throwable th2) {
                            th = th2;
                            boolean z2 = expectActivityLaunch;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(expectActivityLaunch ? 1 : 0);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(37, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().startAddAccountSession(response, accountType, authTokenType, requiredFeatures, expectActivityLaunch, options);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String[] strArr2 = requiredFeatures;
                        boolean z22 = expectActivityLaunch;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str2 = accountType;
                    String str3 = authTokenType;
                    String[] strArr22 = requiredFeatures;
                    boolean z222 = expectActivityLaunch;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void startUpdateCredentialsSession(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(authTokenType);
                    _data.writeInt(expectActivityLaunch);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(38, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startUpdateCredentialsSession(response, account, authTokenType, expectActivityLaunch, options);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finishSessionAsUser(IAccountManagerResponse response, Bundle sessionBundle, boolean expectActivityLaunch, Bundle appInfo, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (sessionBundle != null) {
                        _data.writeInt(1);
                        sessionBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(expectActivityLaunch);
                    if (appInfo != null) {
                        _data.writeInt(1);
                        appInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finishSessionAsUser(response, sessionBundle, expectActivityLaunch, appInfo, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean someUserHasAccount(Account account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().someUserHasAccount(account);
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

            public void isCredentialsUpdateSuggested(IAccountManagerResponse response, Account account, String statusToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(statusToken);
                    if (this.mRemote.transact(41, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().isCredentialsUpdateSuggested(response, account, statusToken);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
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
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesAndVisibilityForAccount(account);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addAccountExplicitlyWithVisibility(Account account, String password, Bundle extras, Map visibility) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(password);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeMap(visibility);
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAccountExplicitlyWithVisibility(account, password, extras, visibility);
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

            public boolean setAccountVisibility(Account a, String packageName, int newVisibility) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (a != null) {
                        _data.writeInt(1);
                        a.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(newVisibility);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAccountVisibility(a, packageName, newVisibility);
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

            public int getAccountVisibility(Account a, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (a != null) {
                        _data.writeInt(1);
                        a.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountVisibility(a, packageName);
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

            public Map getAccountsAndVisibilityForPackage(String packageName, String accountType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(accountType);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsAndVisibilityForPackage(packageName, accountType);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(accountTypes);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(47, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerAccountListener(accountTypes, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(accountTypes);
                    _data.writeString(opPackageName);
                    if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterAccountListener(accountTypes, opPackageName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasAccountAccess(Account account, String packageName, UserHandle userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    if (userHandle != null) {
                        _data.writeInt(1);
                        userHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasAccountAccess(account, packageName, userHandle);
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

            public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String packageName, UserHandle userHandle) throws RemoteException {
                IntentSender _result;
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
                    _data.writeString(packageName);
                    if (userHandle != null) {
                        _data.writeInt(1);
                        userHandle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createRequestAccountAccessIntentSenderAsUser(account, packageName, userHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IntentSender.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IntentSender _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onAccountAccessed(String token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(token);
                    if (this.mRemote.transact(51, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onAccountAccessed(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccountManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccountManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
