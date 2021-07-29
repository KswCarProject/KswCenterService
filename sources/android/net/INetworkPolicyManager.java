package android.net;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.SubscriptionPlan;

public interface INetworkPolicyManager extends IInterface {
    void addUidPolicy(int i, int i2) throws RemoteException;

    void factoryReset(String str) throws RemoteException;

    NetworkPolicy[] getNetworkPolicies(String str) throws RemoteException;

    @UnsupportedAppUsage
    NetworkQuotaInfo getNetworkQuotaInfo(NetworkState networkState) throws RemoteException;

    @UnsupportedAppUsage
    boolean getRestrictBackground() throws RemoteException;

    int getRestrictBackgroundByCaller() throws RemoteException;

    SubscriptionPlan[] getSubscriptionPlans(int i, String str) throws RemoteException;

    String getSubscriptionPlansOwner(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getUidPolicy(int i) throws RemoteException;

    int[] getUidsWithPolicy(int i) throws RemoteException;

    boolean isUidNetworkingBlocked(int i, boolean z) throws RemoteException;

    void onTetheringChanged(String str, boolean z) throws RemoteException;

    void registerListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException;

    void removeUidPolicy(int i, int i2) throws RemoteException;

    void setDeviceIdleMode(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setNetworkPolicies(NetworkPolicy[] networkPolicyArr) throws RemoteException;

    @UnsupportedAppUsage
    void setRestrictBackground(boolean z) throws RemoteException;

    void setSubscriptionOverride(int i, int i2, int i3, long j, String str) throws RemoteException;

    void setSubscriptionPlans(int i, SubscriptionPlan[] subscriptionPlanArr, String str) throws RemoteException;

    @UnsupportedAppUsage
    void setUidPolicy(int i, int i2) throws RemoteException;

    void setWifiMeteredOverride(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    void snoozeLimit(NetworkTemplate networkTemplate) throws RemoteException;

    void unregisterListener(INetworkPolicyListener iNetworkPolicyListener) throws RemoteException;

    public static class Default implements INetworkPolicyManager {
        public void setUidPolicy(int uid, int policy) throws RemoteException {
        }

        public void addUidPolicy(int uid, int policy) throws RemoteException {
        }

        public void removeUidPolicy(int uid, int policy) throws RemoteException {
        }

        public int getUidPolicy(int uid) throws RemoteException {
            return 0;
        }

        public int[] getUidsWithPolicy(int policy) throws RemoteException {
            return null;
        }

        public void registerListener(INetworkPolicyListener listener) throws RemoteException {
        }

        public void unregisterListener(INetworkPolicyListener listener) throws RemoteException {
        }

        public void setNetworkPolicies(NetworkPolicy[] policies) throws RemoteException {
        }

        public NetworkPolicy[] getNetworkPolicies(String callingPackage) throws RemoteException {
            return null;
        }

        public void snoozeLimit(NetworkTemplate template) throws RemoteException {
        }

        public void setRestrictBackground(boolean restrictBackground) throws RemoteException {
        }

        public boolean getRestrictBackground() throws RemoteException {
            return false;
        }

        public void onTetheringChanged(String iface, boolean tethering) throws RemoteException {
        }

        public int getRestrictBackgroundByCaller() throws RemoteException {
            return 0;
        }

        public void setDeviceIdleMode(boolean enabled) throws RemoteException {
        }

        public void setWifiMeteredOverride(String networkId, int meteredOverride) throws RemoteException {
        }

        public NetworkQuotaInfo getNetworkQuotaInfo(NetworkState state) throws RemoteException {
            return null;
        }

        public SubscriptionPlan[] getSubscriptionPlans(int subId, String callingPackage) throws RemoteException {
            return null;
        }

        public void setSubscriptionPlans(int subId, SubscriptionPlan[] plans, String callingPackage) throws RemoteException {
        }

        public String getSubscriptionPlansOwner(int subId) throws RemoteException {
            return null;
        }

        public void setSubscriptionOverride(int subId, int overrideMask, int overrideValue, long timeoutMillis, String callingPackage) throws RemoteException {
        }

        public void factoryReset(String subscriber) throws RemoteException {
        }

        public boolean isUidNetworkingBlocked(int uid, boolean meteredNetwork) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INetworkPolicyManager {
        private static final String DESCRIPTOR = "android.net.INetworkPolicyManager";
        static final int TRANSACTION_addUidPolicy = 2;
        static final int TRANSACTION_factoryReset = 22;
        static final int TRANSACTION_getNetworkPolicies = 9;
        static final int TRANSACTION_getNetworkQuotaInfo = 17;
        static final int TRANSACTION_getRestrictBackground = 12;
        static final int TRANSACTION_getRestrictBackgroundByCaller = 14;
        static final int TRANSACTION_getSubscriptionPlans = 18;
        static final int TRANSACTION_getSubscriptionPlansOwner = 20;
        static final int TRANSACTION_getUidPolicy = 4;
        static final int TRANSACTION_getUidsWithPolicy = 5;
        static final int TRANSACTION_isUidNetworkingBlocked = 23;
        static final int TRANSACTION_onTetheringChanged = 13;
        static final int TRANSACTION_registerListener = 6;
        static final int TRANSACTION_removeUidPolicy = 3;
        static final int TRANSACTION_setDeviceIdleMode = 15;
        static final int TRANSACTION_setNetworkPolicies = 8;
        static final int TRANSACTION_setRestrictBackground = 11;
        static final int TRANSACTION_setSubscriptionOverride = 21;
        static final int TRANSACTION_setSubscriptionPlans = 19;
        static final int TRANSACTION_setUidPolicy = 1;
        static final int TRANSACTION_setWifiMeteredOverride = 16;
        static final int TRANSACTION_snoozeLimit = 10;
        static final int TRANSACTION_unregisterListener = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INetworkPolicyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INetworkPolicyManager)) {
                return new Proxy(obj);
            }
            return (INetworkPolicyManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setUidPolicy";
                case 2:
                    return "addUidPolicy";
                case 3:
                    return "removeUidPolicy";
                case 4:
                    return "getUidPolicy";
                case 5:
                    return "getUidsWithPolicy";
                case 6:
                    return "registerListener";
                case 7:
                    return "unregisterListener";
                case 8:
                    return "setNetworkPolicies";
                case 9:
                    return "getNetworkPolicies";
                case 10:
                    return "snoozeLimit";
                case 11:
                    return "setRestrictBackground";
                case 12:
                    return "getRestrictBackground";
                case 13:
                    return "onTetheringChanged";
                case 14:
                    return "getRestrictBackgroundByCaller";
                case 15:
                    return "setDeviceIdleMode";
                case 16:
                    return "setWifiMeteredOverride";
                case 17:
                    return "getNetworkQuotaInfo";
                case 18:
                    return "getSubscriptionPlans";
                case 19:
                    return "setSubscriptionPlans";
                case 20:
                    return "getSubscriptionPlansOwner";
                case 21:
                    return "setSubscriptionOverride";
                case 22:
                    return "factoryReset";
                case 23:
                    return "isUidNetworkingBlocked";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: android.net.NetworkTemplate} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: android.net.NetworkState} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r0v39 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r7 = r19
                r8 = r20
                r9 = r21
                r10 = r22
                java.lang.String r11 = "android.net.INetworkPolicyManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x01f8
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x01e6;
                    case 2: goto L_0x01d4;
                    case 3: goto L_0x01c2;
                    case 4: goto L_0x01b0;
                    case 5: goto L_0x019e;
                    case 6: goto L_0x018c;
                    case 7: goto L_0x017a;
                    case 8: goto L_0x0168;
                    case 9: goto L_0x0156;
                    case 10: goto L_0x013c;
                    case 11: goto L_0x0129;
                    case 12: goto L_0x011b;
                    case 13: goto L_0x0105;
                    case 14: goto L_0x00f7;
                    case 15: goto L_0x00e4;
                    case 16: goto L_0x00d2;
                    case 17: goto L_0x00ab;
                    case 18: goto L_0x0095;
                    case 19: goto L_0x007b;
                    case 20: goto L_0x0069;
                    case 21: goto L_0x0042;
                    case 22: goto L_0x0034;
                    case 23: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0029
                r1 = r12
            L_0x0029:
                boolean r2 = r7.isUidNetworkingBlocked(r0, r1)
                r22.writeNoException()
                r10.writeInt(r2)
                return r12
            L_0x0034:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                r7.factoryReset(r0)
                r22.writeNoException()
                return r12
            L_0x0042:
                r9.enforceInterface(r11)
                int r13 = r21.readInt()
                int r14 = r21.readInt()
                int r15 = r21.readInt()
                long r16 = r21.readLong()
                java.lang.String r18 = r21.readString()
                r0 = r19
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r6 = r18
                r0.setSubscriptionOverride(r1, r2, r3, r4, r6)
                r22.writeNoException()
                return r12
            L_0x0069:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r7.getSubscriptionPlansOwner(r0)
                r22.writeNoException()
                r10.writeString(r1)
                return r12
            L_0x007b:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                android.os.Parcelable$Creator<android.telephony.SubscriptionPlan> r1 = android.telephony.SubscriptionPlan.CREATOR
                java.lang.Object[] r1 = r9.createTypedArray(r1)
                android.telephony.SubscriptionPlan[] r1 = (android.telephony.SubscriptionPlan[]) r1
                java.lang.String r2 = r21.readString()
                r7.setSubscriptionPlans(r0, r1, r2)
                r22.writeNoException()
                return r12
            L_0x0095:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.String r1 = r21.readString()
                android.telephony.SubscriptionPlan[] r2 = r7.getSubscriptionPlans(r0, r1)
                r22.writeNoException()
                r10.writeTypedArray(r2, r12)
                return r12
            L_0x00ab:
                r9.enforceInterface(r11)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x00bd
                android.os.Parcelable$Creator<android.net.NetworkState> r0 = android.net.NetworkState.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.NetworkState r0 = (android.net.NetworkState) r0
                goto L_0x00be
            L_0x00bd:
            L_0x00be:
                android.net.NetworkQuotaInfo r2 = r7.getNetworkQuotaInfo(r0)
                r22.writeNoException()
                if (r2 == 0) goto L_0x00ce
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x00d1
            L_0x00ce:
                r10.writeInt(r1)
            L_0x00d1:
                return r12
            L_0x00d2:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                int r1 = r21.readInt()
                r7.setWifiMeteredOverride(r0, r1)
                r22.writeNoException()
                return r12
            L_0x00e4:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x00ef
                r1 = r12
            L_0x00ef:
                r0 = r1
                r7.setDeviceIdleMode(r0)
                r22.writeNoException()
                return r12
            L_0x00f7:
                r9.enforceInterface(r11)
                int r0 = r19.getRestrictBackgroundByCaller()
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0105:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0114
                r1 = r12
            L_0x0114:
                r7.onTetheringChanged(r0, r1)
                r22.writeNoException()
                return r12
            L_0x011b:
                r9.enforceInterface(r11)
                boolean r0 = r19.getRestrictBackground()
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0129:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                if (r0 == 0) goto L_0x0134
                r1 = r12
            L_0x0134:
                r0 = r1
                r7.setRestrictBackground(r0)
                r22.writeNoException()
                return r12
            L_0x013c:
                r9.enforceInterface(r11)
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x014e
                android.os.Parcelable$Creator<android.net.NetworkTemplate> r0 = android.net.NetworkTemplate.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.net.NetworkTemplate r0 = (android.net.NetworkTemplate) r0
                goto L_0x014f
            L_0x014e:
            L_0x014f:
                r7.snoozeLimit(r0)
                r22.writeNoException()
                return r12
            L_0x0156:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                android.net.NetworkPolicy[] r1 = r7.getNetworkPolicies(r0)
                r22.writeNoException()
                r10.writeTypedArray(r1, r12)
                return r12
            L_0x0168:
                r9.enforceInterface(r11)
                android.os.Parcelable$Creator<android.net.NetworkPolicy> r0 = android.net.NetworkPolicy.CREATOR
                java.lang.Object[] r0 = r9.createTypedArray(r0)
                android.net.NetworkPolicy[] r0 = (android.net.NetworkPolicy[]) r0
                r7.setNetworkPolicies(r0)
                r22.writeNoException()
                return r12
            L_0x017a:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.net.INetworkPolicyListener r0 = android.net.INetworkPolicyListener.Stub.asInterface(r0)
                r7.unregisterListener(r0)
                r22.writeNoException()
                return r12
            L_0x018c:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r21.readStrongBinder()
                android.net.INetworkPolicyListener r0 = android.net.INetworkPolicyListener.Stub.asInterface(r0)
                r7.registerListener(r0)
                r22.writeNoException()
                return r12
            L_0x019e:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int[] r1 = r7.getUidsWithPolicy(r0)
                r22.writeNoException()
                r10.writeIntArray(r1)
                return r12
            L_0x01b0:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r7.getUidPolicy(r0)
                r22.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x01c2:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                r7.removeUidPolicy(r0, r1)
                r22.writeNoException()
                return r12
            L_0x01d4:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                r7.addUidPolicy(r0, r1)
                r22.writeNoException()
                return r12
            L_0x01e6:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                r7.setUidPolicy(r0, r1)
                r22.writeNoException()
                return r12
            L_0x01f8:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.INetworkPolicyManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INetworkPolicyManager {
            public static INetworkPolicyManager sDefaultImpl;
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

            public void setUidPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUidPolicy(uid, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addUidPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addUidPolicy(uid, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeUidPolicy(int uid, int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(policy);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeUidPolicy(uid, policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUidPolicy(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidPolicy(uid);
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

            public int[] getUidsWithPolicy(int policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(policy);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUidsWithPolicy(policy);
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

            public void registerListener(INetworkPolicyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterListener(INetworkPolicyListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNetworkPolicies(NetworkPolicy[] policies) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(policies, 0);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setNetworkPolicies(policies);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkPolicy[] getNetworkPolicies(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkPolicies(callingPackage);
                    }
                    _reply.readException();
                    NetworkPolicy[] _result = (NetworkPolicy[]) _reply.createTypedArray(NetworkPolicy.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void snoozeLimit(NetworkTemplate template) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (template != null) {
                        _data.writeInt(1);
                        template.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().snoozeLimit(template);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRestrictBackground(boolean restrictBackground) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(restrictBackground);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRestrictBackground(restrictBackground);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getRestrictBackground() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRestrictBackground();
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

            public void onTetheringChanged(String iface, boolean tethering) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(iface);
                    _data.writeInt(tethering);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onTetheringChanged(iface, tethering);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRestrictBackgroundByCaller() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRestrictBackgroundByCaller();
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

            public void setDeviceIdleMode(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDeviceIdleMode(enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setWifiMeteredOverride(String networkId, int meteredOverride) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(networkId);
                    _data.writeInt(meteredOverride);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWifiMeteredOverride(networkId, meteredOverride);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public NetworkQuotaInfo getNetworkQuotaInfo(NetworkState state) throws RemoteException {
                NetworkQuotaInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (state != null) {
                        _data.writeInt(1);
                        state.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNetworkQuotaInfo(state);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = NetworkQuotaInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    NetworkQuotaInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public SubscriptionPlan[] getSubscriptionPlans(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubscriptionPlans(subId, callingPackage);
                    }
                    _reply.readException();
                    SubscriptionPlan[] _result = (SubscriptionPlan[]) _reply.createTypedArray(SubscriptionPlan.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setSubscriptionPlans(int subId, SubscriptionPlan[] plans, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeTypedArray(plans, 0);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSubscriptionPlans(subId, plans, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getSubscriptionPlansOwner(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSubscriptionPlansOwner(subId);
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

            public void setSubscriptionOverride(int subId, int overrideMask, int overrideValue, long timeoutMillis, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                        try {
                            _data.writeInt(overrideMask);
                        } catch (Throwable th) {
                            th = th;
                            int i = overrideValue;
                            long j = timeoutMillis;
                            String str = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(overrideValue);
                            try {
                                _data.writeLong(timeoutMillis);
                            } catch (Throwable th2) {
                                th = th2;
                                String str2 = callingPackage;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                            try {
                                _data.writeString(callingPackage);
                                if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().setSubscriptionOverride(subId, overrideMask, overrideValue, timeoutMillis, callingPackage);
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
                            long j2 = timeoutMillis;
                            String str22 = callingPackage;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        int i2 = overrideMask;
                        int i3 = overrideValue;
                        long j22 = timeoutMillis;
                        String str222 = callingPackage;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    int i4 = subId;
                    int i22 = overrideMask;
                    int i32 = overrideValue;
                    long j222 = timeoutMillis;
                    String str2222 = callingPackage;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void factoryReset(String subscriber) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(subscriber);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().factoryReset(subscriber);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUidNetworkingBlocked(int uid, boolean meteredNetwork) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeInt(meteredNetwork);
                    boolean z = false;
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUidNetworkingBlocked(uid, meteredNetwork);
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

        public static boolean setDefaultImpl(INetworkPolicyManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INetworkPolicyManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
