package android.os;

import android.annotation.UnsupportedAppUsage;
import android.content.IntentSender;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.os.UserManager;
import java.util.List;

public interface IUserManager extends IInterface {
    boolean canAddMoreManagedProfiles(int i, boolean z) throws RemoteException;

    boolean canHaveRestrictedProfile(int i) throws RemoteException;

    void clearSeedAccountData() throws RemoteException;

    UserInfo createProfileForUser(String str, int i, int i2, String[] strArr) throws RemoteException;

    UserInfo createProfileForUserEvenWhenDisallowed(String str, int i, int i2, String[] strArr) throws RemoteException;

    UserInfo createRestrictedProfile(String str, int i) throws RemoteException;

    UserInfo createUser(String str, int i) throws RemoteException;

    void evictCredentialEncryptionKey(int i) throws RemoteException;

    Bundle getApplicationRestrictions(String str) throws RemoteException;

    Bundle getApplicationRestrictionsForUser(String str, int i) throws RemoteException;

    int getCredentialOwnerProfile(int i) throws RemoteException;

    Bundle getDefaultGuestRestrictions() throws RemoteException;

    int getManagedProfileBadge(int i) throws RemoteException;

    UserInfo getPrimaryUser() throws RemoteException;

    int[] getProfileIds(int i, boolean z) throws RemoteException;

    UserInfo getProfileParent(int i) throws RemoteException;

    int getProfileParentId(int i) throws RemoteException;

    List<UserInfo> getProfiles(int i, boolean z) throws RemoteException;

    String getSeedAccountName() throws RemoteException;

    PersistableBundle getSeedAccountOptions() throws RemoteException;

    String getSeedAccountType() throws RemoteException;

    String getUserAccount(int i) throws RemoteException;

    long getUserCreationTime(int i) throws RemoteException;

    int getUserHandle(int i) throws RemoteException;

    ParcelFileDescriptor getUserIcon(int i) throws RemoteException;

    @UnsupportedAppUsage
    UserInfo getUserInfo(int i) throws RemoteException;

    String getUserName() throws RemoteException;

    int getUserRestrictionSource(String str, int i) throws RemoteException;

    List<UserManager.EnforcingUser> getUserRestrictionSources(String str, int i) throws RemoteException;

    Bundle getUserRestrictions(int i) throws RemoteException;

    int getUserSerialNumber(int i) throws RemoteException;

    long getUserStartRealtime() throws RemoteException;

    long getUserUnlockRealtime() throws RemoteException;

    List<UserInfo> getUsers(boolean z) throws RemoteException;

    boolean hasBaseUserRestriction(String str, int i) throws RemoteException;

    boolean hasRestrictedProfiles() throws RemoteException;

    boolean hasUserRestriction(String str, int i) throws RemoteException;

    boolean hasUserRestrictionOnAnyUser(String str) throws RemoteException;

    boolean isDemoUser(int i) throws RemoteException;

    boolean isManagedProfile(int i) throws RemoteException;

    boolean isQuietModeEnabled(int i) throws RemoteException;

    boolean isRestricted() throws RemoteException;

    boolean isSameProfileGroup(int i, int i2) throws RemoteException;

    boolean isUserNameSet(int i) throws RemoteException;

    boolean isUserRunning(int i) throws RemoteException;

    boolean isUserUnlocked(int i) throws RemoteException;

    boolean isUserUnlockingOrUnlocked(int i) throws RemoteException;

    boolean markGuestForDeletion(int i) throws RemoteException;

    boolean removeUser(int i) throws RemoteException;

    boolean removeUserEvenWhenDisallowed(int i) throws RemoteException;

    boolean requestQuietModeEnabled(String str, boolean z, int i, IntentSender intentSender) throws RemoteException;

    void setApplicationRestrictions(String str, Bundle bundle, int i) throws RemoteException;

    void setDefaultGuestRestrictions(Bundle bundle) throws RemoteException;

    void setSeedAccountData(int i, String str, String str2, PersistableBundle persistableBundle, boolean z) throws RemoteException;

    void setUserAccount(int i, String str) throws RemoteException;

    void setUserAdmin(int i) throws RemoteException;

    void setUserEnabled(int i) throws RemoteException;

    void setUserIcon(int i, Bitmap bitmap) throws RemoteException;

    void setUserName(int i, String str) throws RemoteException;

    void setUserRestriction(String str, boolean z, int i) throws RemoteException;

    boolean someUserHasSeedAccount(String str, String str2) throws RemoteException;

    public static class Default implements IUserManager {
        public int getCredentialOwnerProfile(int userHandle) throws RemoteException {
            return 0;
        }

        public int getProfileParentId(int userHandle) throws RemoteException {
            return 0;
        }

        public UserInfo createUser(String name, int flags) throws RemoteException {
            return null;
        }

        public UserInfo createProfileForUser(String name, int flags, int userHandle, String[] disallowedPackages) throws RemoteException {
            return null;
        }

        public UserInfo createRestrictedProfile(String name, int parentUserHandle) throws RemoteException {
            return null;
        }

        public void setUserEnabled(int userHandle) throws RemoteException {
        }

        public void setUserAdmin(int userId) throws RemoteException {
        }

        public void evictCredentialEncryptionKey(int userHandle) throws RemoteException {
        }

        public boolean removeUser(int userHandle) throws RemoteException {
            return false;
        }

        public boolean removeUserEvenWhenDisallowed(int userHandle) throws RemoteException {
            return false;
        }

        public void setUserName(int userHandle, String name) throws RemoteException {
        }

        public void setUserIcon(int userHandle, Bitmap icon) throws RemoteException {
        }

        public ParcelFileDescriptor getUserIcon(int userHandle) throws RemoteException {
            return null;
        }

        public UserInfo getPrimaryUser() throws RemoteException {
            return null;
        }

        public List<UserInfo> getUsers(boolean excludeDying) throws RemoteException {
            return null;
        }

        public List<UserInfo> getProfiles(int userHandle, boolean enabledOnly) throws RemoteException {
            return null;
        }

        public int[] getProfileIds(int userId, boolean enabledOnly) throws RemoteException {
            return null;
        }

        public boolean canAddMoreManagedProfiles(int userHandle, boolean allowedToRemoveOne) throws RemoteException {
            return false;
        }

        public UserInfo getProfileParent(int userHandle) throws RemoteException {
            return null;
        }

        public boolean isSameProfileGroup(int userHandle, int otherUserHandle) throws RemoteException {
            return false;
        }

        public UserInfo getUserInfo(int userHandle) throws RemoteException {
            return null;
        }

        public String getUserAccount(int userHandle) throws RemoteException {
            return null;
        }

        public void setUserAccount(int userHandle, String accountName) throws RemoteException {
        }

        public long getUserCreationTime(int userHandle) throws RemoteException {
            return 0;
        }

        public boolean isRestricted() throws RemoteException {
            return false;
        }

        public boolean canHaveRestrictedProfile(int userHandle) throws RemoteException {
            return false;
        }

        public int getUserSerialNumber(int userHandle) throws RemoteException {
            return 0;
        }

        public int getUserHandle(int userSerialNumber) throws RemoteException {
            return 0;
        }

        public int getUserRestrictionSource(String restrictionKey, int userHandle) throws RemoteException {
            return 0;
        }

        public List<UserManager.EnforcingUser> getUserRestrictionSources(String restrictionKey, int userHandle) throws RemoteException {
            return null;
        }

        public Bundle getUserRestrictions(int userHandle) throws RemoteException {
            return null;
        }

        public boolean hasBaseUserRestriction(String restrictionKey, int userHandle) throws RemoteException {
            return false;
        }

        public boolean hasUserRestriction(String restrictionKey, int userHandle) throws RemoteException {
            return false;
        }

        public boolean hasUserRestrictionOnAnyUser(String restrictionKey) throws RemoteException {
            return false;
        }

        public void setUserRestriction(String key, boolean value, int userHandle) throws RemoteException {
        }

        public void setApplicationRestrictions(String packageName, Bundle restrictions, int userHandle) throws RemoteException {
        }

        public Bundle getApplicationRestrictions(String packageName) throws RemoteException {
            return null;
        }

        public Bundle getApplicationRestrictionsForUser(String packageName, int userHandle) throws RemoteException {
            return null;
        }

        public void setDefaultGuestRestrictions(Bundle restrictions) throws RemoteException {
        }

        public Bundle getDefaultGuestRestrictions() throws RemoteException {
            return null;
        }

        public boolean markGuestForDeletion(int userHandle) throws RemoteException {
            return false;
        }

        public boolean isQuietModeEnabled(int userHandle) throws RemoteException {
            return false;
        }

        public void setSeedAccountData(int userHandle, String accountName, String accountType, PersistableBundle accountOptions, boolean persist) throws RemoteException {
        }

        public String getSeedAccountName() throws RemoteException {
            return null;
        }

        public String getSeedAccountType() throws RemoteException {
            return null;
        }

        public PersistableBundle getSeedAccountOptions() throws RemoteException {
            return null;
        }

        public void clearSeedAccountData() throws RemoteException {
        }

        public boolean someUserHasSeedAccount(String accountName, String accountType) throws RemoteException {
            return false;
        }

        public boolean isManagedProfile(int userId) throws RemoteException {
            return false;
        }

        public boolean isDemoUser(int userId) throws RemoteException {
            return false;
        }

        public UserInfo createProfileForUserEvenWhenDisallowed(String name, int flags, int userHandle, String[] disallowedPackages) throws RemoteException {
            return null;
        }

        public boolean isUserUnlockingOrUnlocked(int userId) throws RemoteException {
            return false;
        }

        public int getManagedProfileBadge(int userId) throws RemoteException {
            return 0;
        }

        public boolean isUserUnlocked(int userId) throws RemoteException {
            return false;
        }

        public boolean isUserRunning(int userId) throws RemoteException {
            return false;
        }

        public boolean isUserNameSet(int userHandle) throws RemoteException {
            return false;
        }

        public boolean hasRestrictedProfiles() throws RemoteException {
            return false;
        }

        public boolean requestQuietModeEnabled(String callingPackage, boolean enableQuietMode, int userHandle, IntentSender target) throws RemoteException {
            return false;
        }

        public String getUserName() throws RemoteException {
            return null;
        }

        public long getUserStartRealtime() throws RemoteException {
            return 0;
        }

        public long getUserUnlockRealtime() throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IUserManager {
        private static final String DESCRIPTOR = "android.os.IUserManager";
        static final int TRANSACTION_canAddMoreManagedProfiles = 18;
        static final int TRANSACTION_canHaveRestrictedProfile = 26;
        static final int TRANSACTION_clearSeedAccountData = 47;
        static final int TRANSACTION_createProfileForUser = 4;
        static final int TRANSACTION_createProfileForUserEvenWhenDisallowed = 51;
        static final int TRANSACTION_createRestrictedProfile = 5;
        static final int TRANSACTION_createUser = 3;
        static final int TRANSACTION_evictCredentialEncryptionKey = 8;
        static final int TRANSACTION_getApplicationRestrictions = 37;
        static final int TRANSACTION_getApplicationRestrictionsForUser = 38;
        static final int TRANSACTION_getCredentialOwnerProfile = 1;
        static final int TRANSACTION_getDefaultGuestRestrictions = 40;
        static final int TRANSACTION_getManagedProfileBadge = 53;
        static final int TRANSACTION_getPrimaryUser = 14;
        static final int TRANSACTION_getProfileIds = 17;
        static final int TRANSACTION_getProfileParent = 19;
        static final int TRANSACTION_getProfileParentId = 2;
        static final int TRANSACTION_getProfiles = 16;
        static final int TRANSACTION_getSeedAccountName = 44;
        static final int TRANSACTION_getSeedAccountOptions = 46;
        static final int TRANSACTION_getSeedAccountType = 45;
        static final int TRANSACTION_getUserAccount = 22;
        static final int TRANSACTION_getUserCreationTime = 24;
        static final int TRANSACTION_getUserHandle = 28;
        static final int TRANSACTION_getUserIcon = 13;
        static final int TRANSACTION_getUserInfo = 21;
        static final int TRANSACTION_getUserName = 59;
        static final int TRANSACTION_getUserRestrictionSource = 29;
        static final int TRANSACTION_getUserRestrictionSources = 30;
        static final int TRANSACTION_getUserRestrictions = 31;
        static final int TRANSACTION_getUserSerialNumber = 27;
        static final int TRANSACTION_getUserStartRealtime = 60;
        static final int TRANSACTION_getUserUnlockRealtime = 61;
        static final int TRANSACTION_getUsers = 15;
        static final int TRANSACTION_hasBaseUserRestriction = 32;
        static final int TRANSACTION_hasRestrictedProfiles = 57;
        static final int TRANSACTION_hasUserRestriction = 33;
        static final int TRANSACTION_hasUserRestrictionOnAnyUser = 34;
        static final int TRANSACTION_isDemoUser = 50;
        static final int TRANSACTION_isManagedProfile = 49;
        static final int TRANSACTION_isQuietModeEnabled = 42;
        static final int TRANSACTION_isRestricted = 25;
        static final int TRANSACTION_isSameProfileGroup = 20;
        static final int TRANSACTION_isUserNameSet = 56;
        static final int TRANSACTION_isUserRunning = 55;
        static final int TRANSACTION_isUserUnlocked = 54;
        static final int TRANSACTION_isUserUnlockingOrUnlocked = 52;
        static final int TRANSACTION_markGuestForDeletion = 41;
        static final int TRANSACTION_removeUser = 9;
        static final int TRANSACTION_removeUserEvenWhenDisallowed = 10;
        static final int TRANSACTION_requestQuietModeEnabled = 58;
        static final int TRANSACTION_setApplicationRestrictions = 36;
        static final int TRANSACTION_setDefaultGuestRestrictions = 39;
        static final int TRANSACTION_setSeedAccountData = 43;
        static final int TRANSACTION_setUserAccount = 23;
        static final int TRANSACTION_setUserAdmin = 7;
        static final int TRANSACTION_setUserEnabled = 6;
        static final int TRANSACTION_setUserIcon = 12;
        static final int TRANSACTION_setUserName = 11;
        static final int TRANSACTION_setUserRestriction = 35;
        static final int TRANSACTION_someUserHasSeedAccount = 48;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUserManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IUserManager)) {
                return new Proxy(obj);
            }
            return (IUserManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getCredentialOwnerProfile";
                case 2:
                    return "getProfileParentId";
                case 3:
                    return "createUser";
                case 4:
                    return "createProfileForUser";
                case 5:
                    return "createRestrictedProfile";
                case 6:
                    return "setUserEnabled";
                case 7:
                    return "setUserAdmin";
                case 8:
                    return "evictCredentialEncryptionKey";
                case 9:
                    return "removeUser";
                case 10:
                    return "removeUserEvenWhenDisallowed";
                case 11:
                    return "setUserName";
                case 12:
                    return "setUserIcon";
                case 13:
                    return "getUserIcon";
                case 14:
                    return "getPrimaryUser";
                case 15:
                    return "getUsers";
                case 16:
                    return "getProfiles";
                case 17:
                    return "getProfileIds";
                case 18:
                    return "canAddMoreManagedProfiles";
                case 19:
                    return "getProfileParent";
                case 20:
                    return "isSameProfileGroup";
                case 21:
                    return "getUserInfo";
                case 22:
                    return "getUserAccount";
                case 23:
                    return "setUserAccount";
                case 24:
                    return "getUserCreationTime";
                case 25:
                    return "isRestricted";
                case 26:
                    return "canHaveRestrictedProfile";
                case 27:
                    return "getUserSerialNumber";
                case 28:
                    return "getUserHandle";
                case 29:
                    return "getUserRestrictionSource";
                case 30:
                    return "getUserRestrictionSources";
                case 31:
                    return "getUserRestrictions";
                case 32:
                    return "hasBaseUserRestriction";
                case 33:
                    return "hasUserRestriction";
                case 34:
                    return "hasUserRestrictionOnAnyUser";
                case 35:
                    return "setUserRestriction";
                case 36:
                    return "setApplicationRestrictions";
                case 37:
                    return "getApplicationRestrictions";
                case 38:
                    return "getApplicationRestrictionsForUser";
                case 39:
                    return "setDefaultGuestRestrictions";
                case 40:
                    return "getDefaultGuestRestrictions";
                case 41:
                    return "markGuestForDeletion";
                case 42:
                    return "isQuietModeEnabled";
                case 43:
                    return "setSeedAccountData";
                case 44:
                    return "getSeedAccountName";
                case 45:
                    return "getSeedAccountType";
                case 46:
                    return "getSeedAccountOptions";
                case 47:
                    return "clearSeedAccountData";
                case 48:
                    return "someUserHasSeedAccount";
                case 49:
                    return "isManagedProfile";
                case 50:
                    return "isDemoUser";
                case 51:
                    return "createProfileForUserEvenWhenDisallowed";
                case 52:
                    return "isUserUnlockingOrUnlocked";
                case 53:
                    return "getManagedProfileBadge";
                case 54:
                    return "isUserUnlocked";
                case 55:
                    return "isUserRunning";
                case 56:
                    return "isUserNameSet";
                case 57:
                    return "hasRestrictedProfiles";
                case 58:
                    return "requestQuietModeEnabled";
                case 59:
                    return "getUserName";
                case 60:
                    return "getUserStartRealtime";
                case 61:
                    return "getUserUnlockRealtime";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v13, resolved type: android.graphics.Bitmap} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v41, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v47, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v73, resolved type: android.content.IntentSender} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v54 */
        /* JADX WARNING: type inference failed for: r0v81 */
        /* JADX WARNING: type inference failed for: r0v82 */
        /* JADX WARNING: type inference failed for: r0v83 */
        /* JADX WARNING: type inference failed for: r0v84 */
        /* JADX WARNING: type inference failed for: r0v85 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r6 = r15
                r7 = r16
                r8 = r17
                r9 = r18
                java.lang.String r10 = "android.os.IUserManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x055f
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x054d;
                    case 2: goto L_0x053b;
                    case 3: goto L_0x051c;
                    case 4: goto L_0x04f5;
                    case 5: goto L_0x04d6;
                    case 6: goto L_0x04c8;
                    case 7: goto L_0x04ba;
                    case 8: goto L_0x04ac;
                    case 9: goto L_0x049a;
                    case 10: goto L_0x0488;
                    case 11: goto L_0x0476;
                    case 12: goto L_0x0458;
                    case 13: goto L_0x043d;
                    case 14: goto L_0x0426;
                    case 15: goto L_0x040f;
                    case 16: goto L_0x03f5;
                    case 17: goto L_0x03db;
                    case 18: goto L_0x03c1;
                    case 19: goto L_0x03a6;
                    case 20: goto L_0x0390;
                    case 21: goto L_0x0375;
                    case 22: goto L_0x0363;
                    case 23: goto L_0x0351;
                    case 24: goto L_0x033f;
                    case 25: goto L_0x0331;
                    case 26: goto L_0x031f;
                    case 27: goto L_0x030d;
                    case 28: goto L_0x02fb;
                    case 29: goto L_0x02e5;
                    case 30: goto L_0x02cf;
                    case 31: goto L_0x02b4;
                    case 32: goto L_0x029e;
                    case 33: goto L_0x0288;
                    case 34: goto L_0x0276;
                    case 35: goto L_0x025c;
                    case 36: goto L_0x023a;
                    case 37: goto L_0x021f;
                    case 38: goto L_0x0200;
                    case 39: goto L_0x01e6;
                    case 40: goto L_0x01cf;
                    case 41: goto L_0x01bd;
                    case 42: goto L_0x01ab;
                    case 43: goto L_0x0177;
                    case 44: goto L_0x0169;
                    case 45: goto L_0x015b;
                    case 46: goto L_0x0144;
                    case 47: goto L_0x013a;
                    case 48: goto L_0x0124;
                    case 49: goto L_0x0112;
                    case 50: goto L_0x0100;
                    case 51: goto L_0x00d9;
                    case 52: goto L_0x00c7;
                    case 53: goto L_0x00b5;
                    case 54: goto L_0x00a3;
                    case 55: goto L_0x0091;
                    case 56: goto L_0x007f;
                    case 57: goto L_0x0071;
                    case 58: goto L_0x0043;
                    case 59: goto L_0x0035;
                    case 60: goto L_0x0027;
                    case 61: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0019:
                r8.enforceInterface(r10)
                long r0 = r15.getUserUnlockRealtime()
                r18.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x0027:
                r8.enforceInterface(r10)
                long r0 = r15.getUserStartRealtime()
                r18.writeNoException()
                r9.writeLong(r0)
                return r11
            L_0x0035:
                r8.enforceInterface(r10)
                java.lang.String r0 = r15.getUserName()
                r18.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x0043:
                r8.enforceInterface(r10)
                java.lang.String r2 = r17.readString()
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x0052
                r1 = r11
            L_0x0052:
                int r3 = r17.readInt()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x0065
                android.os.Parcelable$Creator<android.content.IntentSender> r0 = android.content.IntentSender.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.content.IntentSender r0 = (android.content.IntentSender) r0
                goto L_0x0066
            L_0x0065:
            L_0x0066:
                boolean r4 = r15.requestQuietModeEnabled(r2, r1, r3, r0)
                r18.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x0071:
                r8.enforceInterface(r10)
                boolean r0 = r15.hasRestrictedProfiles()
                r18.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x007f:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isUserNameSet(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0091:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isUserRunning(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00a3:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isUserUnlocked(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00b5:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r1 = r15.getManagedProfileBadge(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00c7:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isUserUnlockingOrUnlocked(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x00d9:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r2 = r17.readInt()
                int r3 = r17.readInt()
                java.lang.String[] r4 = r17.createStringArray()
                android.content.pm.UserInfo r5 = r15.createProfileForUserEvenWhenDisallowed(r0, r2, r3, r4)
                r18.writeNoException()
                if (r5 == 0) goto L_0x00fc
                r9.writeInt(r11)
                r5.writeToParcel(r9, r11)
                goto L_0x00ff
            L_0x00fc:
                r9.writeInt(r1)
            L_0x00ff:
                return r11
            L_0x0100:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isDemoUser(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0112:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isManagedProfile(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0124:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                java.lang.String r1 = r17.readString()
                boolean r2 = r15.someUserHasSeedAccount(r0, r1)
                r18.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x013a:
                r8.enforceInterface(r10)
                r15.clearSeedAccountData()
                r18.writeNoException()
                return r11
            L_0x0144:
                r8.enforceInterface(r10)
                android.os.PersistableBundle r0 = r15.getSeedAccountOptions()
                r18.writeNoException()
                if (r0 == 0) goto L_0x0157
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x015a
            L_0x0157:
                r9.writeInt(r1)
            L_0x015a:
                return r11
            L_0x015b:
                r8.enforceInterface(r10)
                java.lang.String r0 = r15.getSeedAccountType()
                r18.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x0169:
                r8.enforceInterface(r10)
                java.lang.String r0 = r15.getSeedAccountName()
                r18.writeNoException()
                r9.writeString(r0)
                return r11
            L_0x0177:
                r8.enforceInterface(r10)
                int r12 = r17.readInt()
                java.lang.String r13 = r17.readString()
                java.lang.String r14 = r17.readString()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0196
                android.os.Parcelable$Creator<android.os.PersistableBundle> r0 = android.os.PersistableBundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.PersistableBundle r0 = (android.os.PersistableBundle) r0
            L_0x0194:
                r4 = r0
                goto L_0x0197
            L_0x0196:
                goto L_0x0194
            L_0x0197:
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x019f
                r5 = r11
                goto L_0x01a0
            L_0x019f:
                r5 = r1
            L_0x01a0:
                r0 = r15
                r1 = r12
                r2 = r13
                r3 = r14
                r0.setSeedAccountData(r1, r2, r3, r4, r5)
                r18.writeNoException()
                return r11
            L_0x01ab:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.isQuietModeEnabled(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x01bd:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.markGuestForDeletion(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x01cf:
                r8.enforceInterface(r10)
                android.os.Bundle r0 = r15.getDefaultGuestRestrictions()
                r18.writeNoException()
                if (r0 == 0) goto L_0x01e2
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x01e5
            L_0x01e2:
                r9.writeInt(r1)
            L_0x01e5:
                return r11
            L_0x01e6:
                r8.enforceInterface(r10)
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x01f8
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x01f9
            L_0x01f8:
            L_0x01f9:
                r15.setDefaultGuestRestrictions(r0)
                r18.writeNoException()
                return r11
            L_0x0200:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r2 = r17.readInt()
                android.os.Bundle r3 = r15.getApplicationRestrictionsForUser(r0, r2)
                r18.writeNoException()
                if (r3 == 0) goto L_0x021b
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x021e
            L_0x021b:
                r9.writeInt(r1)
            L_0x021e:
                return r11
            L_0x021f:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                android.os.Bundle r2 = r15.getApplicationRestrictions(r0)
                r18.writeNoException()
                if (r2 == 0) goto L_0x0236
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x0239
            L_0x0236:
                r9.writeInt(r1)
            L_0x0239:
                return r11
            L_0x023a:
                r8.enforceInterface(r10)
                java.lang.String r1 = r17.readString()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0250
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.os.Bundle r0 = (android.os.Bundle) r0
                goto L_0x0251
            L_0x0250:
            L_0x0251:
                int r2 = r17.readInt()
                r15.setApplicationRestrictions(r1, r0, r2)
                r18.writeNoException()
                return r11
            L_0x025c:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x026b
                r1 = r11
            L_0x026b:
                int r2 = r17.readInt()
                r15.setUserRestriction(r0, r1, r2)
                r18.writeNoException()
                return r11
            L_0x0276:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                boolean r1 = r15.hasUserRestrictionOnAnyUser(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0288:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r1 = r17.readInt()
                boolean r2 = r15.hasUserRestriction(r0, r1)
                r18.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x029e:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r1 = r17.readInt()
                boolean r2 = r15.hasBaseUserRestriction(r0, r1)
                r18.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x02b4:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                android.os.Bundle r2 = r15.getUserRestrictions(r0)
                r18.writeNoException()
                if (r2 == 0) goto L_0x02cb
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x02ce
            L_0x02cb:
                r9.writeInt(r1)
            L_0x02ce:
                return r11
            L_0x02cf:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r1 = r17.readInt()
                java.util.List r2 = r15.getUserRestrictionSources(r0, r1)
                r18.writeNoException()
                r9.writeTypedList(r2)
                return r11
            L_0x02e5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r1 = r17.readInt()
                int r2 = r15.getUserRestrictionSource(r0, r1)
                r18.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x02fb:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r1 = r15.getUserHandle(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x030d:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r1 = r15.getUserSerialNumber(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x031f:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.canHaveRestrictedProfile(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x0331:
                r8.enforceInterface(r10)
                boolean r0 = r15.isRestricted()
                r18.writeNoException()
                r9.writeInt(r0)
                return r11
            L_0x033f:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                long r1 = r15.getUserCreationTime(r0)
                r18.writeNoException()
                r9.writeLong(r1)
                return r11
            L_0x0351:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                java.lang.String r1 = r17.readString()
                r15.setUserAccount(r0, r1)
                r18.writeNoException()
                return r11
            L_0x0363:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                java.lang.String r1 = r15.getUserAccount(r0)
                r18.writeNoException()
                r9.writeString(r1)
                return r11
            L_0x0375:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                android.content.pm.UserInfo r2 = r15.getUserInfo(r0)
                r18.writeNoException()
                if (r2 == 0) goto L_0x038c
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x038f
            L_0x038c:
                r9.writeInt(r1)
            L_0x038f:
                return r11
            L_0x0390:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r1 = r17.readInt()
                boolean r2 = r15.isSameProfileGroup(r0, r1)
                r18.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x03a6:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                android.content.pm.UserInfo r2 = r15.getProfileParent(r0)
                r18.writeNoException()
                if (r2 == 0) goto L_0x03bd
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x03c0
            L_0x03bd:
                r9.writeInt(r1)
            L_0x03c0:
                return r11
            L_0x03c1:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x03d0
                r1 = r11
            L_0x03d0:
                boolean r2 = r15.canAddMoreManagedProfiles(r0, r1)
                r18.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x03db:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x03ea
                r1 = r11
            L_0x03ea:
                int[] r2 = r15.getProfileIds(r0, r1)
                r18.writeNoException()
                r9.writeIntArray(r2)
                return r11
            L_0x03f5:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0404
                r1 = r11
            L_0x0404:
                java.util.List r2 = r15.getProfiles(r0, r1)
                r18.writeNoException()
                r9.writeTypedList(r2)
                return r11
            L_0x040f:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x041a
                r1 = r11
            L_0x041a:
                r0 = r1
                java.util.List r1 = r15.getUsers(r0)
                r18.writeNoException()
                r9.writeTypedList(r1)
                return r11
            L_0x0426:
                r8.enforceInterface(r10)
                android.content.pm.UserInfo r0 = r15.getPrimaryUser()
                r18.writeNoException()
                if (r0 == 0) goto L_0x0439
                r9.writeInt(r11)
                r0.writeToParcel(r9, r11)
                goto L_0x043c
            L_0x0439:
                r9.writeInt(r1)
            L_0x043c:
                return r11
            L_0x043d:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                android.os.ParcelFileDescriptor r2 = r15.getUserIcon(r0)
                r18.writeNoException()
                if (r2 == 0) goto L_0x0454
                r9.writeInt(r11)
                r2.writeToParcel(r9, r11)
                goto L_0x0457
            L_0x0454:
                r9.writeInt(r1)
            L_0x0457:
                return r11
            L_0x0458:
                r8.enforceInterface(r10)
                int r1 = r17.readInt()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x046e
                android.os.Parcelable$Creator<android.graphics.Bitmap> r0 = android.graphics.Bitmap.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                android.graphics.Bitmap r0 = (android.graphics.Bitmap) r0
                goto L_0x046f
            L_0x046e:
            L_0x046f:
                r15.setUserIcon(r1, r0)
                r18.writeNoException()
                return r11
            L_0x0476:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                java.lang.String r1 = r17.readString()
                r15.setUserName(r0, r1)
                r18.writeNoException()
                return r11
            L_0x0488:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.removeUserEvenWhenDisallowed(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x049a:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                boolean r1 = r15.removeUser(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x04ac:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                r15.evictCredentialEncryptionKey(r0)
                r18.writeNoException()
                return r11
            L_0x04ba:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                r15.setUserAdmin(r0)
                r18.writeNoException()
                return r11
            L_0x04c8:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                r15.setUserEnabled(r0)
                r18.writeNoException()
                return r11
            L_0x04d6:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r2 = r17.readInt()
                android.content.pm.UserInfo r3 = r15.createRestrictedProfile(r0, r2)
                r18.writeNoException()
                if (r3 == 0) goto L_0x04f1
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x04f4
            L_0x04f1:
                r9.writeInt(r1)
            L_0x04f4:
                return r11
            L_0x04f5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r2 = r17.readInt()
                int r3 = r17.readInt()
                java.lang.String[] r4 = r17.createStringArray()
                android.content.pm.UserInfo r5 = r15.createProfileForUser(r0, r2, r3, r4)
                r18.writeNoException()
                if (r5 == 0) goto L_0x0518
                r9.writeInt(r11)
                r5.writeToParcel(r9, r11)
                goto L_0x051b
            L_0x0518:
                r9.writeInt(r1)
            L_0x051b:
                return r11
            L_0x051c:
                r8.enforceInterface(r10)
                java.lang.String r0 = r17.readString()
                int r2 = r17.readInt()
                android.content.pm.UserInfo r3 = r15.createUser(r0, r2)
                r18.writeNoException()
                if (r3 == 0) goto L_0x0537
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x053a
            L_0x0537:
                r9.writeInt(r1)
            L_0x053a:
                return r11
            L_0x053b:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r1 = r15.getProfileParentId(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x054d:
                r8.enforceInterface(r10)
                int r0 = r17.readInt()
                int r1 = r15.getCredentialOwnerProfile(r0)
                r18.writeNoException()
                r9.writeInt(r1)
                return r11
            L_0x055f:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.os.IUserManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IUserManager {
            public static IUserManager sDefaultImpl;
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

            public int getCredentialOwnerProfile(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCredentialOwnerProfile(userHandle);
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

            public int getProfileParentId(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileParentId(userHandle);
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

            public UserInfo createUser(String name, int flags) throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createUser(name, flags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UserInfo createProfileForUser(String name, int flags, int userHandle, String[] disallowedPackages) throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(flags);
                    _data.writeInt(userHandle);
                    _data.writeStringArray(disallowedPackages);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createProfileForUser(name, flags, userHandle, disallowedPackages);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UserInfo createRestrictedProfile(String name, int parentUserHandle) throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(parentUserHandle);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createRestrictedProfile(name, parentUserHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserEnabled(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserEnabled(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserAdmin(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserAdmin(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void evictCredentialEncryptionKey(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().evictCredentialEncryptionKey(userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean removeUser(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeUser(userHandle);
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

            public boolean removeUserEvenWhenDisallowed(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeUserEvenWhenDisallowed(userHandle);
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

            public void setUserName(int userHandle, String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeString(name);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserName(userHandle, name);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUserIcon(int userHandle, Bitmap icon) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (icon != null) {
                        _data.writeInt(1);
                        icon.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserIcon(userHandle, icon);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParcelFileDescriptor getUserIcon(int userHandle) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserIcon(userHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParcelFileDescriptor.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParcelFileDescriptor _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public UserInfo getPrimaryUser() throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPrimaryUser();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<UserInfo> getUsers(boolean excludeDying) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(excludeDying);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUsers(excludeDying);
                    }
                    _reply.readException();
                    List<UserInfo> _result = _reply.createTypedArrayList(UserInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<UserInfo> getProfiles(int userHandle, boolean enabledOnly) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(enabledOnly);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfiles(userHandle, enabledOnly);
                    }
                    _reply.readException();
                    List<UserInfo> _result = _reply.createTypedArrayList(UserInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getProfileIds(int userId, boolean enabledOnly) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(enabledOnly);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileIds(userId, enabledOnly);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean canAddMoreManagedProfiles(int userHandle, boolean allowedToRemoveOne) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(allowedToRemoveOne);
                    boolean z = false;
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canAddMoreManagedProfiles(userHandle, allowedToRemoveOne);
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

            public UserInfo getProfileParent(int userHandle) throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getProfileParent(userHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSameProfileGroup(int userHandle, int otherUserHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeInt(otherUserHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSameProfileGroup(userHandle, otherUserHandle);
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

            public UserInfo getUserInfo(int userHandle) throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserInfo(userHandle);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getUserAccount(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserAccount(userHandle);
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

            public void setUserAccount(int userHandle, String accountName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeString(accountName);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserAccount(userHandle, accountName);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getUserCreationTime(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserCreationTime(userHandle);
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRestricted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRestricted();
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

            public boolean canHaveRestrictedProfile(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().canHaveRestrictedProfile(userHandle);
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

            public int getUserSerialNumber(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserSerialNumber(userHandle);
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

            public int getUserHandle(int userSerialNumber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userSerialNumber);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserHandle(userSerialNumber);
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

            public int getUserRestrictionSource(String restrictionKey, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restrictionKey);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserRestrictionSource(restrictionKey, userHandle);
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

            public List<UserManager.EnforcingUser> getUserRestrictionSources(String restrictionKey, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restrictionKey);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserRestrictionSources(restrictionKey, userHandle);
                    }
                    _reply.readException();
                    List<UserManager.EnforcingUser> _result = _reply.createTypedArrayList(UserManager.EnforcingUser.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getUserRestrictions(int userHandle) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(31, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserRestrictions(userHandle);
                    }
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
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasBaseUserRestriction(String restrictionKey, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restrictionKey);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasBaseUserRestriction(restrictionKey, userHandle);
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

            public boolean hasUserRestriction(String restrictionKey, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restrictionKey);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasUserRestriction(restrictionKey, userHandle);
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

            public boolean hasUserRestrictionOnAnyUser(String restrictionKey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(restrictionKey);
                    boolean z = false;
                    if (!this.mRemote.transact(34, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasUserRestrictionOnAnyUser(restrictionKey);
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

            public void setUserRestriction(String key, boolean value, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(key);
                    _data.writeInt(value);
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(35, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserRestriction(key, value, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setApplicationRestrictions(String packageName, Bundle restrictions, int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (restrictions != null) {
                        _data.writeInt(1);
                        restrictions.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userHandle);
                    if (this.mRemote.transact(36, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setApplicationRestrictions(packageName, restrictions, userHandle);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getApplicationRestrictions(String packageName) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(37, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationRestrictions(packageName);
                    }
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
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getApplicationRestrictionsForUser(String packageName, int userHandle) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userHandle);
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getApplicationRestrictionsForUser(packageName, userHandle);
                    }
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
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDefaultGuestRestrictions(Bundle restrictions) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (restrictions != null) {
                        _data.writeInt(1);
                        restrictions.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(39, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDefaultGuestRestrictions(restrictions);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getDefaultGuestRestrictions() throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(40, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDefaultGuestRestrictions();
                    }
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
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean markGuestForDeletion(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(41, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().markGuestForDeletion(userHandle);
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

            public boolean isQuietModeEnabled(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(42, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isQuietModeEnabled(userHandle);
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

            public void setSeedAccountData(int userHandle, String accountName, String accountType, PersistableBundle accountOptions, boolean persist) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    _data.writeString(accountName);
                    _data.writeString(accountType);
                    if (accountOptions != null) {
                        _data.writeInt(1);
                        accountOptions.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(persist);
                    if (this.mRemote.transact(43, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSeedAccountData(userHandle, accountName, accountType, accountOptions, persist);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getSeedAccountName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(44, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSeedAccountName();
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

            public String getSeedAccountType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSeedAccountType();
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

            public PersistableBundle getSeedAccountOptions() throws RemoteException {
                PersistableBundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(46, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSeedAccountOptions();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = PersistableBundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    PersistableBundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void clearSeedAccountData() throws RemoteException {
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
                    Stub.getDefaultImpl().clearSeedAccountData();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean someUserHasSeedAccount(String accountName, String accountType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(accountName);
                    _data.writeString(accountType);
                    boolean z = false;
                    if (!this.mRemote.transact(48, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().someUserHasSeedAccount(accountName, accountType);
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

            public boolean isManagedProfile(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isManagedProfile(userId);
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

            public boolean isDemoUser(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(50, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isDemoUser(userId);
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

            public UserInfo createProfileForUserEvenWhenDisallowed(String name, int flags, int userHandle, String[] disallowedPackages) throws RemoteException {
                UserInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeInt(flags);
                    _data.writeInt(userHandle);
                    _data.writeStringArray(disallowedPackages);
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createProfileForUserEvenWhenDisallowed(name, flags, userHandle, disallowedPackages);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = UserInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    UserInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUserUnlockingOrUnlocked(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(52, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserUnlockingOrUnlocked(userId);
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

            public int getManagedProfileBadge(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(53, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getManagedProfileBadge(userId);
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

            public boolean isUserUnlocked(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(54, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserUnlocked(userId);
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

            public boolean isUserRunning(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(55, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserRunning(userId);
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

            public boolean isUserNameSet(int userHandle) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userHandle);
                    boolean z = false;
                    if (!this.mRemote.transact(56, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserNameSet(userHandle);
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

            public boolean hasRestrictedProfiles() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasRestrictedProfiles();
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

            public boolean requestQuietModeEnabled(String callingPackage, boolean enableQuietMode, int userHandle, IntentSender target) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(enableQuietMode);
                    _data.writeInt(userHandle);
                    boolean _result = true;
                    if (target != null) {
                        _data.writeInt(1);
                        target.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestQuietModeEnabled(callingPackage, enableQuietMode, userHandle, target);
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

            public String getUserName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(59, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserName();
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

            public long getUserStartRealtime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(60, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserStartRealtime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public long getUserUnlockRealtime() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(61, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserUnlockRealtime();
                    }
                    _reply.readException();
                    long _result = _reply.readLong();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IUserManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IUserManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
