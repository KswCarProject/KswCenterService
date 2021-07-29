package android.nfc;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.INfcAdapterExtras;
import android.nfc.INfcCardEmulation;
import android.nfc.INfcDta;
import android.nfc.INfcFCardEmulation;
import android.nfc.INfcTag;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INfcAdapter extends IInterface {
    void addNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler, int[] iArr) throws RemoteException;

    boolean deviceSupportsNfcSecure() throws RemoteException;

    boolean disable(boolean z) throws RemoteException;

    boolean disableNdefPush() throws RemoteException;

    void dispatch(Tag tag) throws RemoteException;

    boolean enable() throws RemoteException;

    boolean enableNdefPush() throws RemoteException;

    INfcAdapterExtras getNfcAdapterExtrasInterface(String str) throws RemoteException;

    IBinder getNfcAdapterVendorInterface(String str) throws RemoteException;

    INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException;

    INfcDta getNfcDtaInterface(String str) throws RemoteException;

    INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException;

    INfcTag getNfcTagInterface() throws RemoteException;

    int getState() throws RemoteException;

    boolean ignore(int i, int i2, ITagRemovedCallback iTagRemovedCallback) throws RemoteException;

    void invokeBeam() throws RemoteException;

    void invokeBeamInternal(BeamShareData beamShareData) throws RemoteException;

    boolean isNdefPushEnabled() throws RemoteException;

    boolean isNfcSecureEnabled() throws RemoteException;

    void pausePolling(int i) throws RemoteException;

    void removeNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler) throws RemoteException;

    void resumePolling() throws RemoteException;

    void setAppCallback(IAppCallback iAppCallback) throws RemoteException;

    void setForegroundDispatch(PendingIntent pendingIntent, IntentFilter[] intentFilterArr, TechListParcel techListParcel) throws RemoteException;

    boolean setNfcSecure(boolean z) throws RemoteException;

    void setP2pModes(int i, int i2) throws RemoteException;

    void setReaderMode(IBinder iBinder, IAppCallback iAppCallback, int i, Bundle bundle) throws RemoteException;

    void verifyNfcPermission() throws RemoteException;

    public static class Default implements INfcAdapter {
        public INfcTag getNfcTagInterface() throws RemoteException {
            return null;
        }

        public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException {
            return null;
        }

        public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException {
            return null;
        }

        public INfcAdapterExtras getNfcAdapterExtrasInterface(String pkg) throws RemoteException {
            return null;
        }

        public INfcDta getNfcDtaInterface(String pkg) throws RemoteException {
            return null;
        }

        public IBinder getNfcAdapterVendorInterface(String vendor) throws RemoteException {
            return null;
        }

        public int getState() throws RemoteException {
            return 0;
        }

        public boolean disable(boolean saveState) throws RemoteException {
            return false;
        }

        public boolean enable() throws RemoteException {
            return false;
        }

        public boolean enableNdefPush() throws RemoteException {
            return false;
        }

        public boolean disableNdefPush() throws RemoteException {
            return false;
        }

        public boolean isNdefPushEnabled() throws RemoteException {
            return false;
        }

        public void pausePolling(int timeoutInMs) throws RemoteException {
        }

        public void resumePolling() throws RemoteException {
        }

        public void setForegroundDispatch(PendingIntent intent, IntentFilter[] filters, TechListParcel techLists) throws RemoteException {
        }

        public void setAppCallback(IAppCallback callback) throws RemoteException {
        }

        public void invokeBeam() throws RemoteException {
        }

        public void invokeBeamInternal(BeamShareData shareData) throws RemoteException {
        }

        public boolean ignore(int nativeHandle, int debounceMs, ITagRemovedCallback callback) throws RemoteException {
            return false;
        }

        public void dispatch(Tag tag) throws RemoteException {
        }

        public void setReaderMode(IBinder b, IAppCallback callback, int flags, Bundle extras) throws RemoteException {
        }

        public void setP2pModes(int initatorModes, int targetModes) throws RemoteException {
        }

        public void addNfcUnlockHandler(INfcUnlockHandler unlockHandler, int[] techList) throws RemoteException {
        }

        public void removeNfcUnlockHandler(INfcUnlockHandler unlockHandler) throws RemoteException {
        }

        public void verifyNfcPermission() throws RemoteException {
        }

        public boolean isNfcSecureEnabled() throws RemoteException {
            return false;
        }

        public boolean deviceSupportsNfcSecure() throws RemoteException {
            return false;
        }

        public boolean setNfcSecure(boolean enable) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INfcAdapter {
        private static final String DESCRIPTOR = "android.nfc.INfcAdapter";
        static final int TRANSACTION_addNfcUnlockHandler = 23;
        static final int TRANSACTION_deviceSupportsNfcSecure = 27;
        static final int TRANSACTION_disable = 8;
        static final int TRANSACTION_disableNdefPush = 11;
        static final int TRANSACTION_dispatch = 20;
        static final int TRANSACTION_enable = 9;
        static final int TRANSACTION_enableNdefPush = 10;
        static final int TRANSACTION_getNfcAdapterExtrasInterface = 4;
        static final int TRANSACTION_getNfcAdapterVendorInterface = 6;
        static final int TRANSACTION_getNfcCardEmulationInterface = 2;
        static final int TRANSACTION_getNfcDtaInterface = 5;
        static final int TRANSACTION_getNfcFCardEmulationInterface = 3;
        static final int TRANSACTION_getNfcTagInterface = 1;
        static final int TRANSACTION_getState = 7;
        static final int TRANSACTION_ignore = 19;
        static final int TRANSACTION_invokeBeam = 17;
        static final int TRANSACTION_invokeBeamInternal = 18;
        static final int TRANSACTION_isNdefPushEnabled = 12;
        static final int TRANSACTION_isNfcSecureEnabled = 26;
        static final int TRANSACTION_pausePolling = 13;
        static final int TRANSACTION_removeNfcUnlockHandler = 24;
        static final int TRANSACTION_resumePolling = 14;
        static final int TRANSACTION_setAppCallback = 16;
        static final int TRANSACTION_setForegroundDispatch = 15;
        static final int TRANSACTION_setNfcSecure = 28;
        static final int TRANSACTION_setP2pModes = 22;
        static final int TRANSACTION_setReaderMode = 21;
        static final int TRANSACTION_verifyNfcPermission = 25;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static INfcAdapter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof INfcAdapter)) {
                return new Proxy(obj);
            }
            return (INfcAdapter) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getNfcTagInterface";
                case 2:
                    return "getNfcCardEmulationInterface";
                case 3:
                    return "getNfcFCardEmulationInterface";
                case 4:
                    return "getNfcAdapterExtrasInterface";
                case 5:
                    return "getNfcDtaInterface";
                case 6:
                    return "getNfcAdapterVendorInterface";
                case 7:
                    return "getState";
                case 8:
                    return "disable";
                case 9:
                    return "enable";
                case 10:
                    return "enableNdefPush";
                case 11:
                    return "disableNdefPush";
                case 12:
                    return "isNdefPushEnabled";
                case 13:
                    return "pausePolling";
                case 14:
                    return "resumePolling";
                case 15:
                    return "setForegroundDispatch";
                case 16:
                    return "setAppCallback";
                case 17:
                    return "invokeBeam";
                case 18:
                    return "invokeBeamInternal";
                case 19:
                    return "ignore";
                case 20:
                    return "dispatch";
                case 21:
                    return "setReaderMode";
                case 22:
                    return "setP2pModes";
                case 23:
                    return "addNfcUnlockHandler";
                case 24:
                    return "removeNfcUnlockHandler";
                case 25:
                    return "verifyNfcPermission";
                case 26:
                    return "isNfcSecureEnabled";
                case 27:
                    return "deviceSupportsNfcSecure";
                case 28:
                    return "setNfcSecure";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: android.nfc.TechListParcel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r3v0 */
        /* JADX WARNING: type inference failed for: r3v1, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v3, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v5, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v7, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v9, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r3v18 */
        /* JADX WARNING: type inference failed for: r3v22 */
        /* JADX WARNING: type inference failed for: r3v33 */
        /* JADX WARNING: type inference failed for: r3v34 */
        /* JADX WARNING: type inference failed for: r3v35 */
        /* JADX WARNING: type inference failed for: r3v36 */
        /* JADX WARNING: type inference failed for: r3v37 */
        /* JADX WARNING: type inference failed for: r3v38 */
        /* JADX WARNING: type inference failed for: r3v39 */
        /* JADX WARNING: type inference failed for: r3v40 */
        /* JADX WARNING: type inference failed for: r3v41 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.nfc.INfcAdapter"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x0247
                r1 = 0
                r3 = 0
                switch(r8) {
                    case 1: goto L_0x0232;
                    case 2: goto L_0x021d;
                    case 3: goto L_0x0208;
                    case 4: goto L_0x01ef;
                    case 5: goto L_0x01d6;
                    case 6: goto L_0x01c4;
                    case 7: goto L_0x01b6;
                    case 8: goto L_0x01a0;
                    case 9: goto L_0x0192;
                    case 10: goto L_0x0184;
                    case 11: goto L_0x0176;
                    case 12: goto L_0x0168;
                    case 13: goto L_0x015a;
                    case 14: goto L_0x0150;
                    case 15: goto L_0x011e;
                    case 16: goto L_0x010c;
                    case 17: goto L_0x0105;
                    case 18: goto L_0x00ec;
                    case 19: goto L_0x00ce;
                    case 20: goto L_0x00b2;
                    case 21: goto L_0x0088;
                    case 22: goto L_0x0076;
                    case 23: goto L_0x0060;
                    case 24: goto L_0x004e;
                    case 25: goto L_0x0044;
                    case 26: goto L_0x0036;
                    case 27: goto L_0x0028;
                    case 28: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0012:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x001d
                r1 = r2
            L_0x001d:
                boolean r3 = r7.setNfcSecure(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0028:
                r9.enforceInterface(r0)
                boolean r1 = r7.deviceSupportsNfcSecure()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0036:
                r9.enforceInterface(r0)
                boolean r1 = r7.isNfcSecureEnabled()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0044:
                r9.enforceInterface(r0)
                r7.verifyNfcPermission()
                r10.writeNoException()
                return r2
            L_0x004e:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.nfc.INfcUnlockHandler r1 = android.nfc.INfcUnlockHandler.Stub.asInterface(r1)
                r7.removeNfcUnlockHandler(r1)
                r10.writeNoException()
                return r2
            L_0x0060:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.nfc.INfcUnlockHandler r1 = android.nfc.INfcUnlockHandler.Stub.asInterface(r1)
                int[] r3 = r9.createIntArray()
                r7.addNfcUnlockHandler(r1, r3)
                r10.writeNoException()
                return r2
            L_0x0076:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                r7.setP2pModes(r1, r3)
                r10.writeNoException()
                return r2
            L_0x0088:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.os.IBinder r4 = r9.readStrongBinder()
                android.nfc.IAppCallback r4 = android.nfc.IAppCallback.Stub.asInterface(r4)
                int r5 = r9.readInt()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x00aa
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x00ab
            L_0x00aa:
            L_0x00ab:
                r7.setReaderMode(r1, r4, r5, r3)
                r10.writeNoException()
                return r2
            L_0x00b2:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x00c5
                android.os.Parcelable$Creator<android.nfc.Tag> r1 = android.nfc.Tag.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r3 = r1
                android.nfc.Tag r3 = (android.nfc.Tag) r3
                goto L_0x00c6
            L_0x00c5:
            L_0x00c6:
                r1 = r3
                r7.dispatch(r1)
                r10.writeNoException()
                return r2
            L_0x00ce:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                int r3 = r9.readInt()
                android.os.IBinder r4 = r9.readStrongBinder()
                android.nfc.ITagRemovedCallback r4 = android.nfc.ITagRemovedCallback.Stub.asInterface(r4)
                boolean r5 = r7.ignore(r1, r3, r4)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x00ec:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x00ff
                android.os.Parcelable$Creator<android.nfc.BeamShareData> r1 = android.nfc.BeamShareData.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r3 = r1
                android.nfc.BeamShareData r3 = (android.nfc.BeamShareData) r3
                goto L_0x0100
            L_0x00ff:
            L_0x0100:
                r1 = r3
                r7.invokeBeamInternal(r1)
                return r2
            L_0x0105:
                r9.enforceInterface(r0)
                r7.invokeBeam()
                return r2
            L_0x010c:
                r9.enforceInterface(r0)
                android.os.IBinder r1 = r9.readStrongBinder()
                android.nfc.IAppCallback r1 = android.nfc.IAppCallback.Stub.asInterface(r1)
                r7.setAppCallback(r1)
                r10.writeNoException()
                return r2
            L_0x011e:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0130
                android.os.Parcelable$Creator<android.app.PendingIntent> r1 = android.app.PendingIntent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
                goto L_0x0131
            L_0x0130:
                r1 = r3
            L_0x0131:
                android.os.Parcelable$Creator<android.content.IntentFilter> r4 = android.content.IntentFilter.CREATOR
                java.lang.Object[] r4 = r9.createTypedArray(r4)
                android.content.IntentFilter[] r4 = (android.content.IntentFilter[]) r4
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0148
                android.os.Parcelable$Creator<android.nfc.TechListParcel> r3 = android.nfc.TechListParcel.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.nfc.TechListParcel r3 = (android.nfc.TechListParcel) r3
                goto L_0x0149
            L_0x0148:
            L_0x0149:
                r7.setForegroundDispatch(r1, r4, r3)
                r10.writeNoException()
                return r2
            L_0x0150:
                r9.enforceInterface(r0)
                r7.resumePolling()
                r10.writeNoException()
                return r2
            L_0x015a:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.pausePolling(r1)
                r10.writeNoException()
                return r2
            L_0x0168:
                r9.enforceInterface(r0)
                boolean r1 = r7.isNdefPushEnabled()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0176:
                r9.enforceInterface(r0)
                boolean r1 = r7.disableNdefPush()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0184:
                r9.enforceInterface(r0)
                boolean r1 = r7.enableNdefPush()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x0192:
                r9.enforceInterface(r0)
                boolean r1 = r7.enable()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x01a0:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x01ab
                r1 = r2
            L_0x01ab:
                boolean r3 = r7.disable(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x01b6:
                r9.enforceInterface(r0)
                int r1 = r7.getState()
                r10.writeNoException()
                r10.writeInt(r1)
                return r2
            L_0x01c4:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                android.os.IBinder r3 = r7.getNfcAdapterVendorInterface(r1)
                r10.writeNoException()
                r10.writeStrongBinder(r3)
                return r2
            L_0x01d6:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                android.nfc.INfcDta r4 = r7.getNfcDtaInterface(r1)
                r10.writeNoException()
                if (r4 == 0) goto L_0x01eb
                android.os.IBinder r3 = r4.asBinder()
            L_0x01eb:
                r10.writeStrongBinder(r3)
                return r2
            L_0x01ef:
                r9.enforceInterface(r0)
                java.lang.String r1 = r9.readString()
                android.nfc.INfcAdapterExtras r4 = r7.getNfcAdapterExtrasInterface(r1)
                r10.writeNoException()
                if (r4 == 0) goto L_0x0204
                android.os.IBinder r3 = r4.asBinder()
            L_0x0204:
                r10.writeStrongBinder(r3)
                return r2
            L_0x0208:
                r9.enforceInterface(r0)
                android.nfc.INfcFCardEmulation r1 = r7.getNfcFCardEmulationInterface()
                r10.writeNoException()
                if (r1 == 0) goto L_0x0219
                android.os.IBinder r3 = r1.asBinder()
            L_0x0219:
                r10.writeStrongBinder(r3)
                return r2
            L_0x021d:
                r9.enforceInterface(r0)
                android.nfc.INfcCardEmulation r1 = r7.getNfcCardEmulationInterface()
                r10.writeNoException()
                if (r1 == 0) goto L_0x022e
                android.os.IBinder r3 = r1.asBinder()
            L_0x022e:
                r10.writeStrongBinder(r3)
                return r2
            L_0x0232:
                r9.enforceInterface(r0)
                android.nfc.INfcTag r1 = r7.getNfcTagInterface()
                r10.writeNoException()
                if (r1 == 0) goto L_0x0243
                android.os.IBinder r3 = r1.asBinder()
            L_0x0243:
                r10.writeStrongBinder(r3)
                return r2
            L_0x0247:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.nfc.INfcAdapter.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements INfcAdapter {
            public static INfcAdapter sDefaultImpl;
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

            public INfcTag getNfcTagInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfcTagInterface();
                    }
                    _reply.readException();
                    INfcTag _result = INfcTag.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfcCardEmulationInterface();
                    }
                    _reply.readException();
                    INfcCardEmulation _result = INfcCardEmulation.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfcFCardEmulationInterface();
                    }
                    _reply.readException();
                    INfcFCardEmulation _result = INfcFCardEmulation.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INfcAdapterExtras getNfcAdapterExtrasInterface(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfcAdapterExtrasInterface(pkg);
                    }
                    _reply.readException();
                    INfcAdapterExtras _result = INfcAdapterExtras.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public INfcDta getNfcDtaInterface(String pkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkg);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfcDtaInterface(pkg);
                    }
                    _reply.readException();
                    INfcDta _result = INfcDta.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IBinder getNfcAdapterVendorInterface(String vendor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(vendor);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getNfcAdapterVendorInterface(vendor);
                    }
                    _reply.readException();
                    IBinder _result = _reply.readStrongBinder();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getState();
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

            public boolean disable(boolean saveState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(saveState);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disable(saveState);
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

            public boolean enable() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enable();
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

            public boolean enableNdefPush() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enableNdefPush();
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

            public boolean disableNdefPush() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().disableNdefPush();
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

            public boolean isNdefPushEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNdefPushEnabled();
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

            public void pausePolling(int timeoutInMs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(timeoutInMs);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().pausePolling(timeoutInMs);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void resumePolling() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().resumePolling();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setForegroundDispatch(PendingIntent intent, IntentFilter[] filters, TechListParcel techLists) throws RemoteException {
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
                    _data.writeTypedArray(filters, 0);
                    if (techLists != null) {
                        _data.writeInt(1);
                        techLists.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setForegroundDispatch(intent, filters, techLists);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAppCallback(IAppCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAppCallback(callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void invokeBeam() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(17, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().invokeBeam();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void invokeBeamInternal(BeamShareData shareData) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (shareData != null) {
                        _data.writeInt(1);
                        shareData.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(18, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().invokeBeamInternal(shareData);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean ignore(int nativeHandle, int debounceMs, ITagRemovedCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(nativeHandle);
                    _data.writeInt(debounceMs);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().ignore(nativeHandle, debounceMs, callback);
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

            public void dispatch(Tag tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tag != null) {
                        _data.writeInt(1);
                        tag.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().dispatch(tag);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setReaderMode(IBinder b, IAppCallback callback, int flags, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(b);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeInt(flags);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setReaderMode(b, callback, flags, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setP2pModes(int initatorModes, int targetModes) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(initatorModes);
                    _data.writeInt(targetModes);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setP2pModes(initatorModes, targetModes);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void addNfcUnlockHandler(INfcUnlockHandler unlockHandler, int[] techList) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(unlockHandler != null ? unlockHandler.asBinder() : null);
                    _data.writeIntArray(techList);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().addNfcUnlockHandler(unlockHandler, techList);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void removeNfcUnlockHandler(INfcUnlockHandler unlockHandler) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(unlockHandler != null ? unlockHandler.asBinder() : null);
                    if (this.mRemote.transact(24, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().removeNfcUnlockHandler(unlockHandler);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void verifyNfcPermission() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().verifyNfcPermission();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isNfcSecureEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isNfcSecureEnabled();
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

            public boolean deviceSupportsNfcSecure() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(27, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deviceSupportsNfcSecure();
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

            public boolean setNfcSecure(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(enable);
                    boolean z = false;
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setNfcSecure(enable);
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

        public static boolean setDefaultImpl(INfcAdapter impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static INfcAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
