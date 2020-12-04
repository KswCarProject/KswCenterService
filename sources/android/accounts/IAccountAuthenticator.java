package android.accounts;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccountAuthenticator extends IInterface {
    @UnsupportedAppUsage
    void addAccount(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException;

    void addAccountFromCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void confirmCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void editProperties(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    void finishSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, Bundle bundle) throws RemoteException;

    void getAccountCredentialsForCloning(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException;

    @UnsupportedAppUsage
    void getAccountRemovalAllowed(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account) throws RemoteException;

    @UnsupportedAppUsage
    void getAuthToken(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void getAuthTokenLabel(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str) throws RemoteException;

    @UnsupportedAppUsage
    void hasFeatures(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String[] strArr) throws RemoteException;

    void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str) throws RemoteException;

    void startAddAccountSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws RemoteException;

    void startUpdateCredentialsSession(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    void updateCredentials(IAccountAuthenticatorResponse iAccountAuthenticatorResponse, Account account, String str, Bundle bundle) throws RemoteException;

    public static class Default implements IAccountAuthenticator {
        public void addAccount(IAccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws RemoteException {
        }

        public void confirmCredentials(IAccountAuthenticatorResponse response, Account account, Bundle options) throws RemoteException {
        }

        public void getAuthToken(IAccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws RemoteException {
        }

        public void getAuthTokenLabel(IAccountAuthenticatorResponse response, String authTokenType) throws RemoteException {
        }

        public void updateCredentials(IAccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws RemoteException {
        }

        public void editProperties(IAccountAuthenticatorResponse response, String accountType) throws RemoteException {
        }

        public void hasFeatures(IAccountAuthenticatorResponse response, Account account, String[] features) throws RemoteException {
        }

        public void getAccountRemovalAllowed(IAccountAuthenticatorResponse response, Account account) throws RemoteException {
        }

        public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse response, Account account) throws RemoteException {
        }

        public void addAccountFromCredentials(IAccountAuthenticatorResponse response, Account account, Bundle accountCredentials) throws RemoteException {
        }

        public void startAddAccountSession(IAccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws RemoteException {
        }

        public void startUpdateCredentialsSession(IAccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws RemoteException {
        }

        public void finishSession(IAccountAuthenticatorResponse response, String accountType, Bundle sessionBundle) throws RemoteException {
        }

        public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse response, Account account, String statusToken) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAccountAuthenticator {
        private static final String DESCRIPTOR = "android.accounts.IAccountAuthenticator";
        static final int TRANSACTION_addAccount = 1;
        static final int TRANSACTION_addAccountFromCredentials = 10;
        static final int TRANSACTION_confirmCredentials = 2;
        static final int TRANSACTION_editProperties = 6;
        static final int TRANSACTION_finishSession = 13;
        static final int TRANSACTION_getAccountCredentialsForCloning = 9;
        static final int TRANSACTION_getAccountRemovalAllowed = 8;
        static final int TRANSACTION_getAuthToken = 3;
        static final int TRANSACTION_getAuthTokenLabel = 4;
        static final int TRANSACTION_hasFeatures = 7;
        static final int TRANSACTION_isCredentialsUpdateSuggested = 14;
        static final int TRANSACTION_startAddAccountSession = 11;
        static final int TRANSACTION_startUpdateCredentialsSession = 12;
        static final int TRANSACTION_updateCredentials = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAccountAuthenticator asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAccountAuthenticator)) {
                return new Proxy(obj);
            }
            return (IAccountAuthenticator) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "addAccount";
                case 2:
                    return "confirmCredentials";
                case 3:
                    return "getAuthToken";
                case 4:
                    return "getAuthTokenLabel";
                case 5:
                    return "updateCredentials";
                case 6:
                    return "editProperties";
                case 7:
                    return "hasFeatures";
                case 8:
                    return "getAccountRemovalAllowed";
                case 9:
                    return "getAccountCredentialsForCloning";
                case 10:
                    return "addAccountFromCredentials";
                case 11:
                    return "startAddAccountSession";
                case 12:
                    return "startUpdateCredentialsSession";
                case 13:
                    return "finishSession";
                case 14:
                    return "isCredentialsUpdateSuggested";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v12, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: android.accounts.Account} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v36, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v45, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v49, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v53, resolved type: android.accounts.Account} */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v3 */
        /* JADX WARNING: type inference failed for: r0v40 */
        /* JADX WARNING: type inference failed for: r0v58 */
        /* JADX WARNING: type inference failed for: r0v59 */
        /* JADX WARNING: type inference failed for: r0v60 */
        /* JADX WARNING: type inference failed for: r0v61 */
        /* JADX WARNING: type inference failed for: r0v62 */
        /* JADX WARNING: type inference failed for: r0v63 */
        /* JADX WARNING: type inference failed for: r0v64 */
        /* JADX WARNING: type inference failed for: r0v65 */
        /* JADX WARNING: type inference failed for: r0v66 */
        /* JADX WARNING: type inference failed for: r0v67 */
        /* JADX WARNING: type inference failed for: r0v68 */
        /* JADX WARNING: type inference failed for: r0v69 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                java.lang.String r9 = "android.accounts.IAccountAuthenticator"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r10 = 1
                if (r7 == r0) goto L_0x023c
                r0 = 0
                switch(r7) {
                    case 1: goto L_0x020b;
                    case 2: goto L_0x01dc;
                    case 3: goto L_0x01a9;
                    case 4: goto L_0x0196;
                    case 5: goto L_0x0163;
                    case 6: goto L_0x0150;
                    case 7: goto L_0x012d;
                    case 8: goto L_0x010e;
                    case 9: goto L_0x00ef;
                    case 10: goto L_0x00c0;
                    case 11: goto L_0x008f;
                    case 12: goto L_0x005c;
                    case 13: goto L_0x0039;
                    case 14: goto L_0x0016;
                    default: goto L_0x0011;
                }
            L_0x0011:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0016:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0030
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0031
            L_0x0030:
            L_0x0031:
                java.lang.String r2 = r17.readString()
                r15.isCredentialsUpdateSuggested(r1, r0, r2)
                return r10
            L_0x0039:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                java.lang.String r2 = r17.readString()
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x0057
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0058
            L_0x0057:
            L_0x0058:
                r15.finishSession(r1, r2, r0)
                return r10
            L_0x005c:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0076
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x0077
            L_0x0076:
                r2 = r0
            L_0x0077:
                java.lang.String r3 = r17.readString()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x008a
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x008b
            L_0x008a:
            L_0x008b:
                r15.startUpdateCredentialsSession(r1, r2, r3, r0)
                return r10
            L_0x008f:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r11 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                java.lang.String r12 = r17.readString()
                java.lang.String r13 = r17.readString()
                java.lang.String[] r14 = r17.createStringArray()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x00b6
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x00b4:
                r5 = r0
                goto L_0x00b7
            L_0x00b6:
                goto L_0x00b4
            L_0x00b7:
                r0 = r15
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r0.startAddAccountSession(r1, r2, r3, r4, r5)
                return r10
            L_0x00c0:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00da
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x00db
            L_0x00da:
                r2 = r0
            L_0x00db:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x00ea
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x00eb
            L_0x00ea:
            L_0x00eb:
                r15.addAccountFromCredentials(r1, r2, r0)
                return r10
            L_0x00ef:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0109
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x010a
            L_0x0109:
            L_0x010a:
                r15.getAccountCredentialsForCloning(r1, r0)
                return r10
            L_0x010e:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0128
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0129
            L_0x0128:
            L_0x0129:
                r15.getAccountRemovalAllowed(r1, r0)
                return r10
            L_0x012d:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0147
                android.os.Parcelable$Creator<android.accounts.Account> r0 = android.accounts.Account.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.accounts.Account r0 = (android.accounts.Account) r0
                goto L_0x0148
            L_0x0147:
            L_0x0148:
                java.lang.String[] r2 = r17.createStringArray()
                r15.hasFeatures(r1, r0, r2)
                return r10
            L_0x0150:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r0 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r0)
                java.lang.String r1 = r17.readString()
                r15.editProperties(r0, r1)
                return r10
            L_0x0163:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x017d
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x017e
            L_0x017d:
                r2 = r0
            L_0x017e:
                java.lang.String r3 = r17.readString()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x0191
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0192
            L_0x0191:
            L_0x0192:
                r15.updateCredentials(r1, r2, r3, r0)
                return r10
            L_0x0196:
                r8.enforceInterface(r9)
                android.os.IBinder r0 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r0 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r0)
                java.lang.String r1 = r17.readString()
                r15.getAuthTokenLabel(r0, r1)
                return r10
            L_0x01a9:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x01c3
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x01c4
            L_0x01c3:
                r2 = r0
            L_0x01c4:
                java.lang.String r3 = r17.readString()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x01d7
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x01d8
            L_0x01d7:
            L_0x01d8:
                r15.getAuthToken(r1, r2, r3, r0)
                return r10
            L_0x01dc:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r1 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x01f6
                android.os.Parcelable$Creator<android.accounts.Account> r2 = android.accounts.Account.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.accounts.Account r2 = (android.accounts.Account) r2
                goto L_0x01f7
            L_0x01f6:
                r2 = r0
            L_0x01f7:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x0206
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0207
            L_0x0206:
            L_0x0207:
                r15.confirmCredentials(r1, r2, r0)
                return r10
            L_0x020b:
                r8.enforceInterface(r9)
                android.os.IBinder r1 = r17.readStrongBinder()
                android.accounts.IAccountAuthenticatorResponse r11 = android.accounts.IAccountAuthenticatorResponse.Stub.asInterface(r1)
                java.lang.String r12 = r17.readString()
                java.lang.String r13 = r17.readString()
                java.lang.String[] r14 = r17.createStringArray()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x0232
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0230:
                r5 = r0
                goto L_0x0233
            L_0x0232:
                goto L_0x0230
            L_0x0233:
                r0 = r15
                r1 = r11
                r2 = r12
                r3 = r13
                r4 = r14
                r0.addAccount(r1, r2, r3, r4, r5)
                return r10
            L_0x023c:
                r0 = r18
                r0.writeString(r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: android.accounts.IAccountAuthenticator.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAccountAuthenticator {
            public static IAccountAuthenticator sDefaultImpl;
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

            public void addAccount(IAccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeString(authTokenType);
                    _data.writeStringArray(requiredFeatures);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addAccount(response, accountType, authTokenType, requiredFeatures, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void confirmCredentials(IAccountAuthenticatorResponse response, Account account, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().confirmCredentials(response, account, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAuthToken(IAccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAuthToken(response, account, authTokenType, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAuthTokenLabel(IAccountAuthenticatorResponse response, String authTokenType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(authTokenType);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAuthTokenLabel(response, authTokenType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void updateCredentials(IAccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().updateCredentials(response, account, authTokenType, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void editProperties(IAccountAuthenticatorResponse response, String accountType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().editProperties(response, accountType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void hasFeatures(IAccountAuthenticatorResponse response, Account account, String[] features) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().hasFeatures(response, account, features);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAccountRemovalAllowed(IAccountAuthenticatorResponse response, Account account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAccountRemovalAllowed(response, account);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAccountCredentialsForCloning(IAccountAuthenticatorResponse response, Account account) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAccountCredentialsForCloning(response, account);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void addAccountFromCredentials(IAccountAuthenticatorResponse response, Account account, Bundle accountCredentials) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    if (account != null) {
                        _data.writeInt(1);
                        account.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (accountCredentials != null) {
                        _data.writeInt(1);
                        accountCredentials.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().addAccountFromCredentials(response, account, accountCredentials);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startAddAccountSession(IAccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    _data.writeString(authTokenType);
                    _data.writeStringArray(requiredFeatures);
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startAddAccountSession(response, accountType, authTokenType, requiredFeatures, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startUpdateCredentialsSession(IAccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (options != null) {
                        _data.writeInt(1);
                        options.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startUpdateCredentialsSession(response, account, authTokenType, options);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void finishSession(IAccountAuthenticatorResponse response, String accountType, Bundle sessionBundle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(response != null ? response.asBinder() : null);
                    _data.writeString(accountType);
                    if (sessionBundle != null) {
                        _data.writeInt(1);
                        sessionBundle.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().finishSession(response, accountType, sessionBundle);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void isCredentialsUpdateSuggested(IAccountAuthenticatorResponse response, Account account, String statusToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
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
                    if (this.mRemote.transact(14, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isCredentialsUpdateSuggested(response, account, statusToken);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAccountAuthenticator impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAccountAuthenticator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
