package android.net.wifi.aware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface IWifiAwareManager extends IInterface {
    void connect(IBinder iBinder, String str, IWifiAwareEventCallback iWifiAwareEventCallback, ConfigRequest configRequest, boolean z) throws RemoteException;

    void disconnect(int i, IBinder iBinder) throws RemoteException;

    Characteristics getCharacteristics() throws RemoteException;

    boolean isUsageEnabled() throws RemoteException;

    void publish(String str, int i, PublishConfig publishConfig, IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) throws RemoteException;

    void requestMacAddresses(int i, List list, IWifiAwareMacAddressProvider iWifiAwareMacAddressProvider) throws RemoteException;

    void sendMessage(int i, int i2, int i3, byte[] bArr, int i4, int i5) throws RemoteException;

    void subscribe(String str, int i, SubscribeConfig subscribeConfig, IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) throws RemoteException;

    void terminateSession(int i, int i2) throws RemoteException;

    void updatePublish(int i, int i2, PublishConfig publishConfig) throws RemoteException;

    void updateSubscribe(int i, int i2, SubscribeConfig subscribeConfig) throws RemoteException;

    public static class Default implements IWifiAwareManager {
        public boolean isUsageEnabled() throws RemoteException {
            return false;
        }

        public Characteristics getCharacteristics() throws RemoteException {
            return null;
        }

        public void connect(IBinder binder, String callingPackage, IWifiAwareEventCallback callback, ConfigRequest configRequest, boolean notifyOnIdentityChanged) throws RemoteException {
        }

        public void disconnect(int clientId, IBinder binder) throws RemoteException {
        }

        public void publish(String callingPackage, int clientId, PublishConfig publishConfig, IWifiAwareDiscoverySessionCallback callback) throws RemoteException {
        }

        public void subscribe(String callingPackage, int clientId, SubscribeConfig subscribeConfig, IWifiAwareDiscoverySessionCallback callback) throws RemoteException {
        }

        public void updatePublish(int clientId, int discoverySessionId, PublishConfig publishConfig) throws RemoteException {
        }

        public void updateSubscribe(int clientId, int discoverySessionId, SubscribeConfig subscribeConfig) throws RemoteException {
        }

        public void sendMessage(int clientId, int discoverySessionId, int peerId, byte[] message, int messageId, int retryCount) throws RemoteException {
        }

        public void terminateSession(int clientId, int discoverySessionId) throws RemoteException {
        }

        public void requestMacAddresses(int uid, List peerIds, IWifiAwareMacAddressProvider callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWifiAwareManager {
        private static final String DESCRIPTOR = "android.net.wifi.aware.IWifiAwareManager";
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_getCharacteristics = 2;
        static final int TRANSACTION_isUsageEnabled = 1;
        static final int TRANSACTION_publish = 5;
        static final int TRANSACTION_requestMacAddresses = 11;
        static final int TRANSACTION_sendMessage = 9;
        static final int TRANSACTION_subscribe = 6;
        static final int TRANSACTION_terminateSession = 10;
        static final int TRANSACTION_updatePublish = 7;
        static final int TRANSACTION_updateSubscribe = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWifiAwareManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IWifiAwareManager)) {
                return new Proxy(obj);
            }
            return (IWifiAwareManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "isUsageEnabled";
                case 2:
                    return "getCharacteristics";
                case 3:
                    return "connect";
                case 4:
                    return "disconnect";
                case 5:
                    return "publish";
                case 6:
                    return "subscribe";
                case 7:
                    return "updatePublish";
                case 8:
                    return "updateSubscribe";
                case 9:
                    return "sendMessage";
                case 10:
                    return "terminateSession";
                case 11:
                    return "requestMacAddresses";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.net.wifi.aware.PublishConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.net.wifi.aware.SubscribeConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.net.wifi.aware.PublishConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v21, resolved type: android.net.wifi.aware.SubscribeConfig} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v2 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r20, android.os.Parcel r21, android.os.Parcel r22, int r23) throws android.os.RemoteException {
            /*
                r19 = this;
                r7 = r19
                r8 = r20
                r9 = r21
                r10 = r22
                java.lang.String r11 = "android.net.wifi.aware.IWifiAwareManager"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x0183
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0175;
                    case 2: goto L_0x015e;
                    case 3: goto L_0x0125;
                    case 4: goto L_0x0113;
                    case 5: goto L_0x00e9;
                    case 6: goto L_0x00bf;
                    case 7: goto L_0x009d;
                    case 8: goto L_0x007b;
                    case 9: goto L_0x004e;
                    case 10: goto L_0x003c;
                    case 11: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r20, r21, r22, r23)
                return r0
            L_0x001a:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                java.lang.Class r1 = r19.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.ArrayList r2 = r9.readArrayList(r1)
                android.os.IBinder r3 = r21.readStrongBinder()
                android.net.wifi.aware.IWifiAwareMacAddressProvider r3 = android.net.wifi.aware.IWifiAwareMacAddressProvider.Stub.asInterface(r3)
                r7.requestMacAddresses(r0, r2, r3)
                r22.writeNoException()
                return r12
            L_0x003c:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r1 = r21.readInt()
                r7.terminateSession(r0, r1)
                r22.writeNoException()
                return r12
            L_0x004e:
                r9.enforceInterface(r11)
                int r13 = r21.readInt()
                int r14 = r21.readInt()
                int r15 = r21.readInt()
                byte[] r16 = r21.createByteArray()
                int r17 = r21.readInt()
                int r18 = r21.readInt()
                r0 = r19
                r1 = r13
                r2 = r14
                r3 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                r0.sendMessage(r1, r2, r3, r4, r5, r6)
                r22.writeNoException()
                return r12
            L_0x007b:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0095
                android.os.Parcelable$Creator<android.net.wifi.aware.SubscribeConfig> r1 = android.net.wifi.aware.SubscribeConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.wifi.aware.SubscribeConfig r1 = (android.net.wifi.aware.SubscribeConfig) r1
                goto L_0x0096
            L_0x0095:
            L_0x0096:
                r7.updateSubscribe(r0, r2, r1)
                r22.writeNoException()
                return r12
            L_0x009d:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x00b7
                android.os.Parcelable$Creator<android.net.wifi.aware.PublishConfig> r1 = android.net.wifi.aware.PublishConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.wifi.aware.PublishConfig r1 = (android.net.wifi.aware.PublishConfig) r1
                goto L_0x00b8
            L_0x00b7:
            L_0x00b8:
                r7.updatePublish(r0, r2, r1)
                r22.writeNoException()
                return r12
            L_0x00bf:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x00d9
                android.os.Parcelable$Creator<android.net.wifi.aware.SubscribeConfig> r1 = android.net.wifi.aware.SubscribeConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.wifi.aware.SubscribeConfig r1 = (android.net.wifi.aware.SubscribeConfig) r1
                goto L_0x00da
            L_0x00d9:
            L_0x00da:
                android.os.IBinder r3 = r21.readStrongBinder()
                android.net.wifi.aware.IWifiAwareDiscoverySessionCallback r3 = android.net.wifi.aware.IWifiAwareDiscoverySessionCallback.Stub.asInterface(r3)
                r7.subscribe(r0, r2, r1, r3)
                r22.writeNoException()
                return r12
            L_0x00e9:
                r9.enforceInterface(r11)
                java.lang.String r0 = r21.readString()
                int r2 = r21.readInt()
                int r3 = r21.readInt()
                if (r3 == 0) goto L_0x0103
                android.os.Parcelable$Creator<android.net.wifi.aware.PublishConfig> r1 = android.net.wifi.aware.PublishConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.wifi.aware.PublishConfig r1 = (android.net.wifi.aware.PublishConfig) r1
                goto L_0x0104
            L_0x0103:
            L_0x0104:
                android.os.IBinder r3 = r21.readStrongBinder()
                android.net.wifi.aware.IWifiAwareDiscoverySessionCallback r3 = android.net.wifi.aware.IWifiAwareDiscoverySessionCallback.Stub.asInterface(r3)
                r7.publish(r0, r2, r1, r3)
                r22.writeNoException()
                return r12
            L_0x0113:
                r9.enforceInterface(r11)
                int r0 = r21.readInt()
                android.os.IBinder r1 = r21.readStrongBinder()
                r7.disconnect(r0, r1)
                r22.writeNoException()
                return r12
            L_0x0125:
                r9.enforceInterface(r11)
                android.os.IBinder r6 = r21.readStrongBinder()
                java.lang.String r13 = r21.readString()
                android.os.IBinder r2 = r21.readStrongBinder()
                android.net.wifi.aware.IWifiAwareEventCallback r14 = android.net.wifi.aware.IWifiAwareEventCallback.Stub.asInterface(r2)
                int r2 = r21.readInt()
                if (r2 == 0) goto L_0x0148
                android.os.Parcelable$Creator<android.net.wifi.aware.ConfigRequest> r1 = android.net.wifi.aware.ConfigRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.wifi.aware.ConfigRequest r1 = (android.net.wifi.aware.ConfigRequest) r1
            L_0x0146:
                r4 = r1
                goto L_0x0149
            L_0x0148:
                goto L_0x0146
            L_0x0149:
                int r1 = r21.readInt()
                if (r1 == 0) goto L_0x0151
                r5 = r12
                goto L_0x0152
            L_0x0151:
                r5 = r0
            L_0x0152:
                r0 = r19
                r1 = r6
                r2 = r13
                r3 = r14
                r0.connect(r1, r2, r3, r4, r5)
                r22.writeNoException()
                return r12
            L_0x015e:
                r9.enforceInterface(r11)
                android.net.wifi.aware.Characteristics r1 = r19.getCharacteristics()
                r22.writeNoException()
                if (r1 == 0) goto L_0x0171
                r10.writeInt(r12)
                r1.writeToParcel(r10, r12)
                goto L_0x0174
            L_0x0171:
                r10.writeInt(r0)
            L_0x0174:
                return r12
            L_0x0175:
                r9.enforceInterface(r11)
                boolean r0 = r19.isUsageEnabled()
                r22.writeNoException()
                r10.writeInt(r0)
                return r12
            L_0x0183:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.net.wifi.aware.IWifiAwareManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IWifiAwareManager {
            public static IWifiAwareManager sDefaultImpl;
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

            public boolean isUsageEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUsageEnabled();
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

            public Characteristics getCharacteristics() throws RemoteException {
                Characteristics _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCharacteristics();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Characteristics.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Characteristics _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void connect(IBinder binder, String callingPackage, IWifiAwareEventCallback callback, ConfigRequest configRequest, boolean notifyOnIdentityChanged) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(binder);
                    _data.writeString(callingPackage);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (configRequest != null) {
                        _data.writeInt(1);
                        configRequest.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(notifyOnIdentityChanged);
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().connect(binder, callingPackage, callback, configRequest, notifyOnIdentityChanged);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disconnect(int clientId, IBinder binder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    _data.writeStrongBinder(binder);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disconnect(clientId, binder);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void publish(String callingPackage, int clientId, PublishConfig publishConfig, IWifiAwareDiscoverySessionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(clientId);
                    if (publishConfig != null) {
                        _data.writeInt(1);
                        publishConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().publish(callingPackage, clientId, publishConfig, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void subscribe(String callingPackage, int clientId, SubscribeConfig subscribeConfig, IWifiAwareDiscoverySessionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(clientId);
                    if (subscribeConfig != null) {
                        _data.writeInt(1);
                        subscribeConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().subscribe(callingPackage, clientId, subscribeConfig, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updatePublish(int clientId, int discoverySessionId, PublishConfig publishConfig) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    _data.writeInt(discoverySessionId);
                    if (publishConfig != null) {
                        _data.writeInt(1);
                        publishConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updatePublish(clientId, discoverySessionId, publishConfig);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateSubscribe(int clientId, int discoverySessionId, SubscribeConfig subscribeConfig) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    _data.writeInt(discoverySessionId);
                    if (subscribeConfig != null) {
                        _data.writeInt(1);
                        subscribeConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateSubscribe(clientId, discoverySessionId, subscribeConfig);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendMessage(int clientId, int discoverySessionId, int peerId, byte[] message, int messageId, int retryCount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(clientId);
                        try {
                            _data.writeInt(discoverySessionId);
                            try {
                                _data.writeInt(peerId);
                                try {
                                    _data.writeByteArray(message);
                                } catch (Throwable th) {
                                    th = th;
                                    int i = messageId;
                                    int i2 = retryCount;
                                    _reply.recycle();
                                    _data.recycle();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                byte[] bArr = message;
                                int i3 = messageId;
                                int i22 = retryCount;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            int i4 = peerId;
                            byte[] bArr2 = message;
                            int i32 = messageId;
                            int i222 = retryCount;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(messageId);
                            try {
                                _data.writeInt(retryCount);
                                if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    _reply.recycle();
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().sendMessage(clientId, discoverySessionId, peerId, message, messageId, retryCount);
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
                            int i2222 = retryCount;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        int i5 = discoverySessionId;
                        int i42 = peerId;
                        byte[] bArr22 = message;
                        int i322 = messageId;
                        int i22222 = retryCount;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i6 = clientId;
                    int i52 = discoverySessionId;
                    int i422 = peerId;
                    byte[] bArr222 = message;
                    int i3222 = messageId;
                    int i222222 = retryCount;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void terminateSession(int clientId, int discoverySessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(clientId);
                    _data.writeInt(discoverySessionId);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().terminateSession(clientId, discoverySessionId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestMacAddresses(int uid, List peerIds, IWifiAwareMacAddressProvider callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeList(peerIds);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestMacAddresses(uid, peerIds, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IWifiAwareManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IWifiAwareManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
