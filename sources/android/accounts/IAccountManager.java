package android.accounts;

import android.accounts.IAccountManagerResponse;
import android.content.IntentSender;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.p007os.UserHandle;
import java.util.Map;

/* loaded from: classes.dex */
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

    /* loaded from: classes.dex */
    public static class Default implements IAccountManager {
        @Override // android.accounts.IAccountManager
        public String getPassword(Account account) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public String getUserData(Account account, String key) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public AuthenticatorDescription[] getAuthenticatorTypes(int userId) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccounts(String accountType, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccountsForPackage(String packageName, int uid, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccountsByTypeForPackage(String type, String packageName, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public Account[] getAccountsAsUser(String accountType, int userId, String opPackageName) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void hasFeatures(IAccountManagerResponse response, Account account, String[] features, String opPackageName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAccountByTypeAndFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAccountsByFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean addAccountExplicitly(Account account, String password, Bundle extras) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void removeAccount(IAccountManagerResponse response, Account account, boolean expectActivityLaunch) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void removeAccountAsUser(IAccountManagerResponse response, Account account, boolean expectActivityLaunch, int userId) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean removeAccountExplicitly(Account account) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void copyAccountToUser(IAccountManagerResponse response, Account account, int userFrom, int userTo) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void invalidateAuthToken(String accountType, String authToken) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public String peekAuthToken(Account account, String authTokenType) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void setAuthToken(Account account, String authTokenType, String authToken) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void setPassword(Account account, String password) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void clearPassword(Account account) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void setUserData(Account account, String key, String value) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void updateAppPermission(Account account, String authTokenType, int uid, boolean value) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void getAuthToken(IAccountManagerResponse response, Account account, String authTokenType, boolean notifyOnAuthFailure, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void addAccount(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void addAccountAsUser(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options, int userId) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void updateCredentials(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void editProperties(IAccountManagerResponse response, String accountType, boolean expectActivityLaunch) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void confirmCredentialsAsUser(IAccountManagerResponse response, Account account, Bundle options, boolean expectActivityLaunch, int userId) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean accountAuthenticated(Account account) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void getAuthTokenLabel(IAccountManagerResponse response, String accountType, String authTokenType) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public Account[] getSharedAccountsAsUser(int userId) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public boolean removeSharedAccountAsUser(Account account, int userId) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void addSharedAccountsFromParentUser(int parentUserId, int userId, String opPackageName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void renameAccount(IAccountManagerResponse response, Account accountToRename, String newName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public String getPreviousName(Account account) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public boolean renameSharedAccountAsUser(Account accountToRename, String newName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void startAddAccountSession(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void startUpdateCredentialsSession(IAccountManagerResponse response, Account account, String authTokenType, boolean expectActivityLaunch, Bundle options) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void finishSessionAsUser(IAccountManagerResponse response, Bundle sessionBundle, boolean expectActivityLaunch, Bundle appInfo, int userId) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean someUserHasAccount(Account account) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public void isCredentialsUpdateSuggested(IAccountManagerResponse response, Account account, String statusToken) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public Map getPackagesAndVisibilityForAccount(Account account) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public boolean addAccountExplicitlyWithVisibility(Account account, String password, Bundle extras, Map visibility) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public boolean setAccountVisibility(Account a, String packageName, int newVisibility) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public int getAccountVisibility(Account a, String packageName) throws RemoteException {
            return 0;
        }

        @Override // android.accounts.IAccountManager
        public Map getAccountsAndVisibilityForPackage(String packageName, String accountType) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void registerAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public void unregisterAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
        }

        @Override // android.accounts.IAccountManager
        public boolean hasAccountAccess(Account account, String packageName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.accounts.IAccountManager
        public IntentSender createRequestAccountAccessIntentSenderAsUser(Account account, String packageName, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.accounts.IAccountManager
        public void onAccountAccessed(String token) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes.dex */
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
            if (iin != null && (iin instanceof IAccountManager)) {
                return (IAccountManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Account _arg0;
            boolean _arg2;
            Account _arg1;
            Account _arg12;
            Account _arg13;
            Account _arg14;
            Bundle _arg15;
            Account _arg02;
            Account _arg03;
            Account _arg04;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg05 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    String _result = getPassword(_arg05);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg06 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg07 = _arg06;
                    String _arg16 = data.readString();
                    String _result2 = getUserData(_arg07, _arg16);
                    reply.writeNoException();
                    reply.writeString(_result2);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg08 = data.readInt();
                    AuthenticatorDescription[] _result3 = getAuthenticatorTypes(_arg08);
                    reply.writeNoException();
                    reply.writeTypedArray(_result3, 1);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg09 = data.readString();
                    String _arg17 = data.readString();
                    Account[] _result4 = getAccounts(_arg09, _arg17);
                    reply.writeNoException();
                    reply.writeTypedArray(_result4, 1);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg010 = data.readString();
                    int _arg18 = data.readInt();
                    Account[] _result5 = getAccountsForPackage(_arg010, _arg18, data.readString());
                    reply.writeNoException();
                    reply.writeTypedArray(_result5, 1);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg011 = data.readString();
                    String _arg19 = data.readString();
                    Account[] _result6 = getAccountsByTypeForPackage(_arg011, _arg19, data.readString());
                    reply.writeNoException();
                    reply.writeTypedArray(_result6, 1);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg012 = data.readString();
                    int _arg110 = data.readInt();
                    Account[] _result7 = getAccountsAsUser(_arg012, _arg110, data.readString());
                    reply.writeNoException();
                    reply.writeTypedArray(_result7, 1);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg013 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    Account _arg111 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    String[] _arg22 = data.createStringArray();
                    String _arg3 = data.readString();
                    hasFeatures(_arg013, _arg111, _arg22, _arg3);
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg014 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg112 = data.readString();
                    String[] _arg23 = data.createStringArray();
                    String _arg32 = data.readString();
                    getAccountByTypeAndFeatures(_arg014, _arg112, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg015 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg113 = data.readString();
                    String[] _arg24 = data.createStringArray();
                    String _arg33 = data.readString();
                    getAccountsByFeatures(_arg015, _arg113, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    String _arg114 = data.readString();
                    boolean addAccountExplicitly = addAccountExplicitly(_arg0, _arg114, data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    reply.writeInt(addAccountExplicitly ? 1 : 0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg016 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    Account _arg115 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    _arg2 = data.readInt() != 0;
                    removeAccount(_arg016, _arg115, _arg2);
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg017 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    Account _arg116 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    _arg2 = data.readInt() != 0;
                    int _arg34 = data.readInt();
                    removeAccountAsUser(_arg017, _arg116, _arg2, _arg34);
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg018 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    boolean removeAccountExplicitly = removeAccountExplicitly(_arg018);
                    reply.writeNoException();
                    reply.writeInt(removeAccountExplicitly ? 1 : 0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg019 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    Account _arg117 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    int _arg25 = data.readInt();
                    int _arg35 = data.readInt();
                    copyAccountToUser(_arg019, _arg117, _arg25, _arg35);
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg020 = data.readString();
                    String _arg118 = data.readString();
                    invalidateAuthToken(_arg020, _arg118);
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg021 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg022 = _arg021;
                    String _arg119 = data.readString();
                    String _result8 = peekAuthToken(_arg022, _arg119);
                    reply.writeNoException();
                    reply.writeString(_result8);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg023 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg024 = _arg023;
                    String _arg120 = data.readString();
                    setAuthToken(_arg024, _arg120, data.readString());
                    reply.writeNoException();
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg025 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg026 = _arg025;
                    String _arg121 = data.readString();
                    setPassword(_arg026, _arg121);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg027 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    clearPassword(_arg027);
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg028 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg029 = _arg028;
                    String _arg122 = data.readString();
                    setUserData(_arg029, _arg122, data.readString());
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg030 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    String _arg123 = data.readString();
                    int _arg26 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    updateAppPermission(_arg030, _arg123, _arg26, _arg2);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg031 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg1 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    String _arg27 = data.readString();
                    boolean _arg36 = data.readInt() != 0;
                    boolean _arg4 = data.readInt() != 0;
                    Bundle _arg5 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    getAuthToken(_arg031, _arg1, _arg27, _arg36, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg032 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg124 = data.readString();
                    String _arg28 = data.readString();
                    String[] _arg37 = data.createStringArray();
                    boolean _arg42 = data.readInt() != 0;
                    Bundle _arg52 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    addAccount(_arg032, _arg124, _arg28, _arg37, _arg42, _arg52);
                    reply.writeNoException();
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg033 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg125 = data.readString();
                    String _arg29 = data.readString();
                    String[] _arg38 = data.createStringArray();
                    boolean _arg43 = data.readInt() != 0;
                    Bundle _arg53 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _arg6 = data.readInt();
                    addAccountAsUser(_arg033, _arg125, _arg29, _arg38, _arg43, _arg53, _arg6);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg034 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg12 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg12 = null;
                    }
                    String _arg210 = data.readString();
                    boolean _arg39 = data.readInt() != 0;
                    Bundle _arg44 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    updateCredentials(_arg034, _arg12, _arg210, _arg39, _arg44);
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg035 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg126 = data.readString();
                    _arg2 = data.readInt() != 0;
                    editProperties(_arg035, _arg126, _arg2);
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg036 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg13 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    Bundle _arg211 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    boolean _arg310 = data.readInt() != 0;
                    int _arg45 = data.readInt();
                    confirmCredentialsAsUser(_arg036, _arg13, _arg211, _arg310, _arg45);
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg037 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    boolean accountAuthenticated = accountAuthenticated(_arg037);
                    reply.writeNoException();
                    reply.writeInt(accountAuthenticated ? 1 : 0);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg038 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg127 = data.readString();
                    getAuthTokenLabel(_arg038, _arg127, data.readString());
                    reply.writeNoException();
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg039 = data.readInt();
                    Account[] _result9 = getSharedAccountsAsUser(_arg039);
                    reply.writeNoException();
                    reply.writeTypedArray(_result9, 1);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg040 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg041 = _arg040;
                    int _arg128 = data.readInt();
                    boolean removeSharedAccountAsUser = removeSharedAccountAsUser(_arg041, _arg128);
                    reply.writeNoException();
                    reply.writeInt(removeSharedAccountAsUser ? 1 : 0);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg042 = data.readInt();
                    int _arg129 = data.readInt();
                    addSharedAccountsFromParentUser(_arg042, _arg129, data.readString());
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg043 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    Account _arg130 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    renameAccount(_arg043, _arg130, data.readString());
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg044 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    String _result10 = getPreviousName(_arg044);
                    reply.writeNoException();
                    reply.writeString(_result10);
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg045 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg046 = _arg045;
                    String _arg131 = data.readString();
                    boolean renameSharedAccountAsUser = renameSharedAccountAsUser(_arg046, _arg131, data.readInt());
                    reply.writeNoException();
                    reply.writeInt(renameSharedAccountAsUser ? 1 : 0);
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg047 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    String _arg132 = data.readString();
                    String _arg212 = data.readString();
                    String[] _arg311 = data.createStringArray();
                    boolean _arg46 = data.readInt() != 0;
                    Bundle _arg54 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    startAddAccountSession(_arg047, _arg132, _arg212, _arg311, _arg46, _arg54);
                    reply.writeNoException();
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg048 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg14 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg14 = null;
                    }
                    String _arg213 = data.readString();
                    boolean _arg312 = data.readInt() != 0;
                    Bundle _arg47 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    startUpdateCredentialsSession(_arg048, _arg14, _arg213, _arg312, _arg47);
                    reply.writeNoException();
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg049 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        _arg15 = Bundle.CREATOR.createFromParcel(data);
                    } else {
                        _arg15 = null;
                    }
                    boolean _arg214 = data.readInt() != 0;
                    Bundle _arg313 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    int _arg48 = data.readInt();
                    finishSessionAsUser(_arg049, _arg15, _arg214, _arg313, _arg48);
                    reply.writeNoException();
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg050 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    boolean someUserHasAccount = someUserHasAccount(_arg050);
                    reply.writeNoException();
                    reply.writeInt(someUserHasAccount ? 1 : 0);
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    IAccountManagerResponse _arg051 = IAccountManagerResponse.Stub.asInterface(data.readStrongBinder());
                    Account _arg133 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    isCredentialsUpdateSuggested(_arg051, _arg133, data.readString());
                    reply.writeNoException();
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg052 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Map _result11 = getPackagesAndVisibilityForAccount(_arg052);
                    reply.writeNoException();
                    reply.writeMap(_result11);
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    String _arg134 = data.readString();
                    Bundle _arg215 = data.readInt() != 0 ? Bundle.CREATOR.createFromParcel(data) : null;
                    ClassLoader cl = getClass().getClassLoader();
                    Map _arg314 = data.readHashMap(cl);
                    boolean addAccountExplicitlyWithVisibility = addAccountExplicitlyWithVisibility(_arg02, _arg134, _arg215, _arg314);
                    reply.writeNoException();
                    reply.writeInt(addAccountExplicitlyWithVisibility ? 1 : 0);
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg053 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg054 = _arg053;
                    String _arg135 = data.readString();
                    boolean accountVisibility = setAccountVisibility(_arg054, _arg135, data.readInt());
                    reply.writeNoException();
                    reply.writeInt(accountVisibility ? 1 : 0);
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    Account _arg055 = data.readInt() != 0 ? Account.CREATOR.createFromParcel(data) : null;
                    Account _arg056 = _arg055;
                    String _arg136 = data.readString();
                    int _result12 = getAccountVisibility(_arg056, _arg136);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg057 = data.readString();
                    String _arg137 = data.readString();
                    Map _result13 = getAccountsAndVisibilityForPackage(_arg057, _arg137);
                    reply.writeNoException();
                    reply.writeMap(_result13);
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _arg058 = data.createStringArray();
                    String _arg138 = data.readString();
                    registerAccountListener(_arg058, _arg138);
                    reply.writeNoException();
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    String[] _arg059 = data.createStringArray();
                    String _arg139 = data.readString();
                    unregisterAccountListener(_arg059, _arg139);
                    reply.writeNoException();
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg03 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg03 = null;
                    }
                    String _arg140 = data.readString();
                    UserHandle _arg216 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    boolean hasAccountAccess = hasAccountAccess(_arg03, _arg140, _arg216);
                    reply.writeNoException();
                    reply.writeInt(hasAccountAccess ? 1 : 0);
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = Account.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    String _arg141 = data.readString();
                    UserHandle _arg217 = data.readInt() != 0 ? UserHandle.CREATOR.createFromParcel(data) : null;
                    IntentSender _result14 = createRequestAccountAccessIntentSenderAsUser(_arg04, _arg141, _arg217);
                    reply.writeNoException();
                    if (_result14 != null) {
                        reply.writeInt(1);
                        _result14.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg060 = data.readString();
                    onAccountAccessed(_arg060);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes.dex */
        private static class Proxy implements IAccountManager {
            public static IAccountManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPassword(account);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserData(account, key);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public AuthenticatorDescription[] getAuthenticatorTypes(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAuthenticatorTypes(userId);
                    }
                    _reply.readException();
                    AuthenticatorDescription[] _result = (AuthenticatorDescription[]) _reply.createTypedArray(AuthenticatorDescription.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccounts(String accountType, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountType);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(4, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccounts(accountType, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsForPackage(String packageName, int uid, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(uid);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsForPackage(packageName, uid, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsByTypeForPackage(String type, String packageName, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(packageName);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(6, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsByTypeForPackage(type, packageName, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getAccountsAsUser(String accountType, int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountType);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(7, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsAsUser(accountType, userId, opPackageName);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(8, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hasFeatures(response, account, features, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountByTypeAndFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeStringArray(features);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAccountByTypeAndFeatures(response, accountType, features, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAccountsByFeatures(IAccountManagerResponse response, String accountType, String[] features, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeStringArray(features);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAccountsByFeatures(response, accountType, features, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitly(Account account, String password, Bundle extras) throws RemoteException {
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
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAccountExplicitly(account, password, extras);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAccount(response, account, expectActivityLaunch);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAccountAsUser(response, account, expectActivityLaunch, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean removeAccountExplicitly(Account account) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeAccountExplicitly(account);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().copyAccountToUser(response, account, userFrom, userTo);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void invalidateAuthToken(String accountType, String authToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountType);
                    _data.writeString(authToken);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().invalidateAuthToken(accountType, authToken);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().peekAuthToken(account, authTokenType);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAuthToken(account, authTokenType, authToken);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPassword(account, password);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPassword(account);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserData(account, key, value);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(value ? 1 : 0);
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateAppPermission(account, authTokenType, uid, value);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthToken(IAccountManagerResponse response, Account account, String authTokenType, boolean notifyOnAuthFailure, boolean expectActivityLaunch, Bundle options) throws RemoteException {
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
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(authTokenType);
                    try {
                        _data.writeInt(notifyOnAuthFailure ? 1 : 0);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(expectActivityLaunch ? 1 : 0);
                        if (options != null) {
                            _data.writeInt(1);
                            options.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().getAuthToken(response, account, authTokenType, notifyOnAuthFailure, expectActivityLaunch, options);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
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
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccount(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(accountType);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(authTokenType);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStringArray(requiredFeatures);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addAccount(response, accountType, authTokenType, requiredFeatures, expectActivityLaunch, options);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.accounts.IAccountManager
            public void addAccountAsUser(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    try {
                        _data.writeString(accountType);
                    } catch (Throwable th) {
                        th = th;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(authTokenType);
                    } catch (Throwable th2) {
                        th = th2;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeStringArray(requiredFeatures);
                        _data.writeInt(expectActivityLaunch ? 1 : 0);
                        if (options != null) {
                            _data.writeInt(1);
                            options.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeInt(userId);
                        boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().addAccountAsUser(response, accountType, authTokenType, requiredFeatures, expectActivityLaunch, options, userId);
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        _reply.readException();
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
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateCredentials(response, account, authTokenType, expectActivityLaunch, options);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void editProperties(IAccountManagerResponse response, String accountType, boolean expectActivityLaunch) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().editProperties(response, accountType, expectActivityLaunch);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().confirmCredentialsAsUser(response, account, options, expectActivityLaunch, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean accountAuthenticated(Account account) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().accountAuthenticated(account);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void getAuthTokenLabel(IAccountManagerResponse response, String accountType, String authTokenType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeString(authTokenType);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getAuthTokenLabel(response, accountType, authTokenType);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Account[] getSharedAccountsAsUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSharedAccountsAsUser(userId);
                    }
                    _reply.readException();
                    Account[] _result = (Account[]) _reply.createTypedArray(Account.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean removeSharedAccountAsUser(Account account, int userId) throws RemoteException {
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
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeSharedAccountAsUser(account, userId);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void addSharedAccountsFromParentUser(int parentUserId, int userId, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(parentUserId);
                    _data.writeInt(userId);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSharedAccountsFromParentUser(parentUserId, userId, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().renameAccount(response, accountToRename, newName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPreviousName(account);
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean renameSharedAccountAsUser(Account accountToRename, String newName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (accountToRename != null) {
                        _data.writeInt(1);
                        accountToRename.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(newName);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(36, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().renameSharedAccountAsUser(accountToRename, newName, userId);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void startAddAccountSession(IAccountManagerResponse response, String accountType, String authTokenType, String[] requiredFeatures, boolean expectActivityLaunch, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeString(accountType);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(authTokenType);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStringArray(requiredFeatures);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(37, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startAddAccountSession(response, accountType, authTokenType, requiredFeatures, expectActivityLaunch, options);
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    _reply.readException();
                    _reply.recycle();
                    _data.recycle();
                } catch (Throwable th5) {
                    th = th5;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startUpdateCredentialsSession(response, account, authTokenType, expectActivityLaunch, options);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    _data.writeInt(expectActivityLaunch ? 1 : 0);
                    if (appInfo != null) {
                        _data.writeInt(1);
                        appInfo.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(39, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishSessionAsUser(response, sessionBundle, expectActivityLaunch, appInfo, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean someUserHasAccount(Account account) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(40, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().someUserHasAccount(account);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(41, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().isCredentialsUpdateSuggested(response, account, statusToken);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPackagesAndVisibilityForAccount(account);
                    }
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean addAccountExplicitlyWithVisibility(Account account, String password, Bundle extras, Map visibility) throws RemoteException {
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
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeMap(visibility);
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addAccountExplicitlyWithVisibility(account, password, extras, visibility);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean setAccountVisibility(Account a, String packageName, int newVisibility) throws RemoteException {
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
                    _data.writeInt(newVisibility);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setAccountVisibility(a, packageName, newVisibility);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountVisibility(a, packageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public Map getAccountsAndVisibilityForPackage(String packageName, String accountType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(accountType);
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAccountsAndVisibilityForPackage(packageName, accountType);
                    }
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void registerAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(accountTypes);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAccountListener(accountTypes, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void unregisterAccountListener(String[] accountTypes, String opPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(accountTypes);
                    _data.writeString(opPackageName);
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAccountListener(accountTypes, opPackageName);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public boolean hasAccountAccess(Account account, String packageName, UserHandle userHandle) throws RemoteException {
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
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasAccountAccess(account, packageName, userHandle);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
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
                    boolean _status = this.mRemote.transact(50, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createRequestAccountAccessIntentSenderAsUser(account, packageName, userHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IntentSender.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.accounts.IAccountManager
            public void onAccountAccessed(String token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(token);
                    boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAccountAccessed(token);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccountManager impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAccountManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
