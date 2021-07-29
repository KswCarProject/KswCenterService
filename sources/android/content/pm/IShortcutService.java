package android.content.pm;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.List;

public interface IShortcutService extends IInterface {
    boolean addDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    void applyRestore(byte[] bArr, int i) throws RemoteException;

    Intent createShortcutResultIntent(String str, ShortcutInfo shortcutInfo, int i) throws RemoteException;

    void disableShortcuts(String str, List list, CharSequence charSequence, int i, int i2) throws RemoteException;

    void enableShortcuts(String str, List list, int i) throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    ParceledListSlice getDynamicShortcuts(String str, int i) throws RemoteException;

    int getIconMaxDimensions(String str, int i) throws RemoteException;

    ParceledListSlice getManifestShortcuts(String str, int i) throws RemoteException;

    int getMaxShortcutCountPerActivity(String str, int i) throws RemoteException;

    ParceledListSlice getPinnedShortcuts(String str, int i) throws RemoteException;

    long getRateLimitResetTime(String str, int i) throws RemoteException;

    int getRemainingCallCount(String str, int i) throws RemoteException;

    ParceledListSlice getShareTargets(String str, IntentFilter intentFilter, int i) throws RemoteException;

    boolean hasShareTargets(String str, String str2, int i) throws RemoteException;

    boolean isRequestPinItemSupported(int i, int i2) throws RemoteException;

    void onApplicationActive(String str, int i) throws RemoteException;

    void removeAllDynamicShortcuts(String str, int i) throws RemoteException;

    void removeDynamicShortcuts(String str, List list, int i) throws RemoteException;

    void reportShortcutUsed(String str, String str2, int i) throws RemoteException;

    boolean requestPinShortcut(String str, ShortcutInfo shortcutInfo, IntentSender intentSender, int i) throws RemoteException;

    void resetThrottling() throws RemoteException;

    boolean setDynamicShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    boolean updateShortcuts(String str, ParceledListSlice parceledListSlice, int i) throws RemoteException;

    public static class Default implements IShortcutService {
        public boolean setDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
            return false;
        }

        public ParceledListSlice getDynamicShortcuts(String packageName, int userId) throws RemoteException {
            return null;
        }

        public ParceledListSlice getManifestShortcuts(String packageName, int userId) throws RemoteException {
            return null;
        }

        public boolean addDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
            return false;
        }

        public void removeDynamicShortcuts(String packageName, List shortcutIds, int userId) throws RemoteException {
        }

        public void removeAllDynamicShortcuts(String packageName, int userId) throws RemoteException {
        }

        public ParceledListSlice getPinnedShortcuts(String packageName, int userId) throws RemoteException {
            return null;
        }

        public boolean updateShortcuts(String packageName, ParceledListSlice shortcuts, int userId) throws RemoteException {
            return false;
        }

        public boolean requestPinShortcut(String packageName, ShortcutInfo shortcut, IntentSender resultIntent, int userId) throws RemoteException {
            return false;
        }

        public Intent createShortcutResultIntent(String packageName, ShortcutInfo shortcut, int userId) throws RemoteException {
            return null;
        }

        public void disableShortcuts(String packageName, List shortcutIds, CharSequence disabledMessage, int disabledMessageResId, int userId) throws RemoteException {
        }

        public void enableShortcuts(String packageName, List shortcutIds, int userId) throws RemoteException {
        }

        public int getMaxShortcutCountPerActivity(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public int getRemainingCallCount(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public long getRateLimitResetTime(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public int getIconMaxDimensions(String packageName, int userId) throws RemoteException {
            return 0;
        }

        public void reportShortcutUsed(String packageName, String shortcutId, int userId) throws RemoteException {
        }

        public void resetThrottling() throws RemoteException {
        }

        public void onApplicationActive(String packageName, int userId) throws RemoteException {
        }

        public byte[] getBackupPayload(int user) throws RemoteException {
            return null;
        }

        public void applyRestore(byte[] payload, int user) throws RemoteException {
        }

        public boolean isRequestPinItemSupported(int user, int requestType) throws RemoteException {
            return false;
        }

        public ParceledListSlice getShareTargets(String packageName, IntentFilter filter, int userId) throws RemoteException {
            return null;
        }

        public boolean hasShareTargets(String packageName, String packageToCheck, int userId) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IShortcutService {
        private static final String DESCRIPTOR = "android.content.pm.IShortcutService";
        static final int TRANSACTION_addDynamicShortcuts = 4;
        static final int TRANSACTION_applyRestore = 21;
        static final int TRANSACTION_createShortcutResultIntent = 10;
        static final int TRANSACTION_disableShortcuts = 11;
        static final int TRANSACTION_enableShortcuts = 12;
        static final int TRANSACTION_getBackupPayload = 20;
        static final int TRANSACTION_getDynamicShortcuts = 2;
        static final int TRANSACTION_getIconMaxDimensions = 16;
        static final int TRANSACTION_getManifestShortcuts = 3;
        static final int TRANSACTION_getMaxShortcutCountPerActivity = 13;
        static final int TRANSACTION_getPinnedShortcuts = 7;
        static final int TRANSACTION_getRateLimitResetTime = 15;
        static final int TRANSACTION_getRemainingCallCount = 14;
        static final int TRANSACTION_getShareTargets = 23;
        static final int TRANSACTION_hasShareTargets = 24;
        static final int TRANSACTION_isRequestPinItemSupported = 22;
        static final int TRANSACTION_onApplicationActive = 19;
        static final int TRANSACTION_removeAllDynamicShortcuts = 6;
        static final int TRANSACTION_removeDynamicShortcuts = 5;
        static final int TRANSACTION_reportShortcutUsed = 17;
        static final int TRANSACTION_requestPinShortcut = 9;
        static final int TRANSACTION_resetThrottling = 18;
        static final int TRANSACTION_setDynamicShortcuts = 1;
        static final int TRANSACTION_updateShortcuts = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IShortcutService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IShortcutService)) {
                return new Proxy(obj);
            }
            return (IShortcutService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setDynamicShortcuts";
                case 2:
                    return "getDynamicShortcuts";
                case 3:
                    return "getManifestShortcuts";
                case 4:
                    return "addDynamicShortcuts";
                case 5:
                    return "removeDynamicShortcuts";
                case 6:
                    return "removeAllDynamicShortcuts";
                case 7:
                    return "getPinnedShortcuts";
                case 8:
                    return "updateShortcuts";
                case 9:
                    return "requestPinShortcut";
                case 10:
                    return "createShortcutResultIntent";
                case 11:
                    return "disableShortcuts";
                case 12:
                    return "enableShortcuts";
                case 13:
                    return "getMaxShortcutCountPerActivity";
                case 14:
                    return "getRemainingCallCount";
                case 15:
                    return "getRateLimitResetTime";
                case 16:
                    return "getIconMaxDimensions";
                case 17:
                    return "reportShortcutUsed";
                case 18:
                    return "resetThrottling";
                case 19:
                    return "onApplicationActive";
                case 20:
                    return "getBackupPayload";
                case 21:
                    return "applyRestore";
                case 22:
                    return "isRequestPinItemSupported";
                case 23:
                    return "getShareTargets";
                case 24:
                    return "hasShareTargets";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v15, resolved type: android.content.pm.ParceledListSlice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.content.IntentSender} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v23, resolved type: android.content.pm.ShortcutInfo} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v39, types: [android.content.IntentFilter] */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r18, android.os.Parcel r19, android.os.Parcel r20, int r21) throws android.os.RemoteException {
            /*
                r17 = this;
                r6 = r17
                r7 = r18
                r8 = r19
                r9 = r20
                java.lang.String r10 = "android.content.pm.IShortcutService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r7 == r0) goto L_0x02e3
                r0 = 0
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x02bd;
                    case 2: goto L_0x029e;
                    case 3: goto L_0x027f;
                    case 4: goto L_0x0259;
                    case 5: goto L_0x023b;
                    case 6: goto L_0x0229;
                    case 7: goto L_0x020a;
                    case 8: goto L_0x01e4;
                    case 9: goto L_0x01ae;
                    case 10: goto L_0x017f;
                    case 11: goto L_0x0145;
                    case 12: goto L_0x0127;
                    case 13: goto L_0x0111;
                    case 14: goto L_0x00fb;
                    case 15: goto L_0x00e5;
                    case 16: goto L_0x00cf;
                    case 17: goto L_0x00b9;
                    case 18: goto L_0x00af;
                    case 19: goto L_0x009d;
                    case 20: goto L_0x008b;
                    case 21: goto L_0x0079;
                    case 22: goto L_0x0063;
                    case 23: goto L_0x0034;
                    case 24: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r18, r19, r20, r21)
                return r0
            L_0x001a:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                boolean r3 = r6.hasShareTargets(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x0034:
                r8.enforceInterface(r10)
                java.lang.String r2 = r19.readString()
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x004a
                android.os.Parcelable$Creator<android.content.IntentFilter> r1 = android.content.IntentFilter.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.IntentFilter r1 = (android.content.IntentFilter) r1
                goto L_0x004b
            L_0x004a:
            L_0x004b:
                int r3 = r19.readInt()
                android.content.pm.ParceledListSlice r4 = r6.getShareTargets(r2, r1, r3)
                r20.writeNoException()
                if (r4 == 0) goto L_0x005f
                r9.writeInt(r11)
                r4.writeToParcel(r9, r11)
                goto L_0x0062
            L_0x005f:
                r9.writeInt(r0)
            L_0x0062:
                return r11
            L_0x0063:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                int r1 = r19.readInt()
                boolean r2 = r6.isRequestPinItemSupported(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0079:
                r8.enforceInterface(r10)
                byte[] r0 = r19.createByteArray()
                int r1 = r19.readInt()
                r6.applyRestore(r0, r1)
                r20.writeNoException()
                return r11
            L_0x008b:
                r8.enforceInterface(r10)
                int r0 = r19.readInt()
                byte[] r1 = r6.getBackupPayload(r0)
                r20.writeNoException()
                r9.writeByteArray(r1)
                return r11
            L_0x009d:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                r6.onApplicationActive(r0, r1)
                r20.writeNoException()
                return r11
            L_0x00af:
                r8.enforceInterface(r10)
                r17.resetThrottling()
                r20.writeNoException()
                return r11
            L_0x00b9:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                r6.reportShortcutUsed(r0, r1, r2)
                r20.writeNoException()
                return r11
            L_0x00cf:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                int r2 = r6.getIconMaxDimensions(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x00e5:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                long r2 = r6.getRateLimitResetTime(r0, r1)
                r20.writeNoException()
                r9.writeLong(r2)
                return r11
            L_0x00fb:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                int r2 = r6.getRemainingCallCount(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0111:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                int r2 = r6.getMaxShortcutCountPerActivity(r0, r1)
                r20.writeNoException()
                r9.writeInt(r2)
                return r11
            L_0x0127:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.Class r1 = r17.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.ArrayList r2 = r8.readArrayList(r1)
                int r3 = r19.readInt()
                r6.enableShortcuts(r0, r2, r3)
                r20.writeNoException()
                return r11
            L_0x0145:
                r8.enforceInterface(r10)
                java.lang.String r12 = r19.readString()
                java.lang.Class r0 = r17.getClass()
                java.lang.ClassLoader r13 = r0.getClassLoader()
                java.util.ArrayList r14 = r8.readArrayList(r13)
                int r0 = r19.readInt()
                if (r0 == 0) goto L_0x0168
                android.os.Parcelable$Creator<java.lang.CharSequence> r0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r0 = r0.createFromParcel(r8)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                r3 = r0
                goto L_0x0169
            L_0x0168:
                r3 = r1
            L_0x0169:
                int r15 = r19.readInt()
                int r16 = r19.readInt()
                r0 = r17
                r1 = r12
                r2 = r14
                r4 = r15
                r5 = r16
                r0.disableShortcuts(r1, r2, r3, r4, r5)
                r20.writeNoException()
                return r11
            L_0x017f:
                r8.enforceInterface(r10)
                java.lang.String r2 = r19.readString()
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x0195
                android.os.Parcelable$Creator<android.content.pm.ShortcutInfo> r1 = android.content.pm.ShortcutInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ShortcutInfo r1 = (android.content.pm.ShortcutInfo) r1
                goto L_0x0196
            L_0x0195:
            L_0x0196:
                int r3 = r19.readInt()
                android.content.Intent r4 = r6.createShortcutResultIntent(r2, r1, r3)
                r20.writeNoException()
                if (r4 == 0) goto L_0x01aa
                r9.writeInt(r11)
                r4.writeToParcel(r9, r11)
                goto L_0x01ad
            L_0x01aa:
                r9.writeInt(r0)
            L_0x01ad:
                return r11
            L_0x01ae:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x01c4
                android.os.Parcelable$Creator<android.content.pm.ShortcutInfo> r2 = android.content.pm.ShortcutInfo.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r8)
                android.content.pm.ShortcutInfo r2 = (android.content.pm.ShortcutInfo) r2
                goto L_0x01c5
            L_0x01c4:
                r2 = r1
            L_0x01c5:
                int r3 = r19.readInt()
                if (r3 == 0) goto L_0x01d4
                android.os.Parcelable$Creator<android.content.IntentSender> r1 = android.content.IntentSender.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.IntentSender r1 = (android.content.IntentSender) r1
                goto L_0x01d5
            L_0x01d4:
            L_0x01d5:
                int r3 = r19.readInt()
                boolean r4 = r6.requestPinShortcut(r0, r2, r1, r3)
                r20.writeNoException()
                r9.writeInt(r4)
                return r11
            L_0x01e4:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x01fa
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x01fb
            L_0x01fa:
            L_0x01fb:
                int r2 = r19.readInt()
                boolean r3 = r6.updateShortcuts(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x020a:
                r8.enforceInterface(r10)
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                android.content.pm.ParceledListSlice r3 = r6.getPinnedShortcuts(r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x0225
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x0228
            L_0x0225:
                r9.writeInt(r0)
            L_0x0228:
                return r11
            L_0x0229:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r1 = r19.readInt()
                r6.removeAllDynamicShortcuts(r0, r1)
                r20.writeNoException()
                return r11
            L_0x023b:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                java.lang.Class r1 = r17.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.ArrayList r2 = r8.readArrayList(r1)
                int r3 = r19.readInt()
                r6.removeDynamicShortcuts(r0, r2, r3)
                r20.writeNoException()
                return r11
            L_0x0259:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x026f
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x0270
            L_0x026f:
            L_0x0270:
                int r2 = r19.readInt()
                boolean r3 = r6.addDynamicShortcuts(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x027f:
                r8.enforceInterface(r10)
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                android.content.pm.ParceledListSlice r3 = r6.getManifestShortcuts(r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x029a
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x029d
            L_0x029a:
                r9.writeInt(r0)
            L_0x029d:
                return r11
            L_0x029e:
                r8.enforceInterface(r10)
                java.lang.String r1 = r19.readString()
                int r2 = r19.readInt()
                android.content.pm.ParceledListSlice r3 = r6.getDynamicShortcuts(r1, r2)
                r20.writeNoException()
                if (r3 == 0) goto L_0x02b9
                r9.writeInt(r11)
                r3.writeToParcel(r9, r11)
                goto L_0x02bc
            L_0x02b9:
                r9.writeInt(r0)
            L_0x02bc:
                return r11
            L_0x02bd:
                r8.enforceInterface(r10)
                java.lang.String r0 = r19.readString()
                int r2 = r19.readInt()
                if (r2 == 0) goto L_0x02d3
                android.os.Parcelable$ClassLoaderCreator<android.content.pm.ParceledListSlice> r1 = android.content.pm.ParceledListSlice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.content.pm.ParceledListSlice r1 = (android.content.pm.ParceledListSlice) r1
                goto L_0x02d4
            L_0x02d3:
            L_0x02d4:
                int r2 = r19.readInt()
                boolean r3 = r6.setDynamicShortcuts(r0, r1, r2)
                r20.writeNoException()
                r9.writeInt(r3)
                return r11
            L_0x02e3:
                r9.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.content.pm.IShortcutService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IShortcutService {
            public static IShortcutService sDefaultImpl;
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

            public boolean setDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (shortcutInfoList != null) {
                        _data.writeInt(1);
                        shortcutInfoList.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setDynamicShortcuts(packageName, shortcutInfoList, userId);
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

            public ParceledListSlice getDynamicShortcuts(String packageName, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDynamicShortcuts(packageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getManifestShortcuts(String packageName, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getManifestShortcuts(packageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean addDynamicShortcuts(String packageName, ParceledListSlice shortcutInfoList, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (shortcutInfoList != null) {
                        _data.writeInt(1);
                        shortcutInfoList.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addDynamicShortcuts(packageName, shortcutInfoList, userId);
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

            public void removeDynamicShortcuts(String packageName, List shortcutIds, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeList(shortcutIds);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeDynamicShortcuts(packageName, shortcutIds, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeAllDynamicShortcuts(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeAllDynamicShortcuts(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getPinnedShortcuts(String packageName, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPinnedShortcuts(packageName, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean updateShortcuts(String packageName, ParceledListSlice shortcuts, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (shortcuts != null) {
                        _data.writeInt(1);
                        shortcuts.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateShortcuts(packageName, shortcuts, userId);
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

            public boolean requestPinShortcut(String packageName, ShortcutInfo shortcut, IntentSender resultIntent, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (shortcut != null) {
                        _data.writeInt(1);
                        shortcut.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (resultIntent != null) {
                        _data.writeInt(1);
                        resultIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestPinShortcut(packageName, shortcut, resultIntent, userId);
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

            public Intent createShortcutResultIntent(String packageName, ShortcutInfo shortcut, int userId) throws RemoteException {
                Intent _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (shortcut != null) {
                        _data.writeInt(1);
                        shortcut.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createShortcutResultIntent(packageName, shortcut, userId);
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

            public void disableShortcuts(String packageName, List shortcutIds, CharSequence disabledMessage, int disabledMessageResId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeList(shortcutIds);
                    if (disabledMessage != null) {
                        _data.writeInt(1);
                        TextUtils.writeToParcel(disabledMessage, _data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(disabledMessageResId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableShortcuts(packageName, shortcutIds, disabledMessage, disabledMessageResId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableShortcuts(String packageName, List shortcutIds, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeList(shortcutIds);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().enableShortcuts(packageName, shortcutIds, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMaxShortcutCountPerActivity(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaxShortcutCountPerActivity(packageName, userId);
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

            public int getRemainingCallCount(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRemainingCallCount(packageName, userId);
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

            public long getRateLimitResetTime(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRateLimitResetTime(packageName, userId);
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

            public int getIconMaxDimensions(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getIconMaxDimensions(packageName, userId);
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

            public void reportShortcutUsed(String packageName, String shortcutId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(shortcutId);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(17, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().reportShortcutUsed(packageName, shortcutId, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resetThrottling() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resetThrottling();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onApplicationActive(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onApplicationActive(packageName, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getBackupPayload(int user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getBackupPayload(user);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void applyRestore(byte[] payload, int user) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(payload);
                    _data.writeInt(user);
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().applyRestore(payload, user);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRequestPinItemSupported(int user, int requestType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(user);
                    _data.writeInt(requestType);
                    boolean z = false;
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRequestPinItemSupported(user, requestType);
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

            public ParceledListSlice getShareTargets(String packageName, IntentFilter filter, int userId) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(userId);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getShareTargets(packageName, filter, userId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasShareTargets(String packageName, String packageToCheck, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(packageToCheck);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasShareTargets(packageName, packageToCheck, userId);
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

        public static boolean setDefaultImpl(IShortcutService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IShortcutService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
