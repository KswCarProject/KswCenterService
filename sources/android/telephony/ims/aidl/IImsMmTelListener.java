package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsReasonInfo;
import com.android.ims.internal.IImsCallSession;

public interface IImsMmTelListener extends IInterface {
    void onIncomingCall(IImsCallSession iImsCallSession, Bundle bundle) throws RemoteException;

    void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void onVoiceMessageCountUpdate(int i) throws RemoteException;

    public static class Default implements IImsMmTelListener {
        public void onIncomingCall(IImsCallSession c, Bundle extras) throws RemoteException {
        }

        public void onRejectedCall(ImsCallProfile callProfile, ImsReasonInfo reason) throws RemoteException {
        }

        public void onVoiceMessageCountUpdate(int count) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsMmTelListener {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelListener";
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onRejectedCall = 2;
        static final int TRANSACTION_onVoiceMessageCountUpdate = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMmTelListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IImsMmTelListener)) {
                return new Proxy(obj);
            }
            return (IImsMmTelListener) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onIncomingCall";
                case 2:
                    return "onRejectedCall";
                case 3:
                    return "onVoiceMessageCountUpdate";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.telephony.ims.ImsReasonInfo} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: type inference failed for: r1v13 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.telephony.ims.aidl.IImsMmTelListener"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0062
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x0043;
                    case 2: goto L_0x001c;
                    case 3: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                r5.onVoiceMessageCountUpdate(r1)
                return r2
            L_0x001c:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x002e
                android.os.Parcelable$Creator<android.telephony.ims.ImsCallProfile> r3 = android.telephony.ims.ImsCallProfile.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.telephony.ims.ImsCallProfile r3 = (android.telephony.ims.ImsCallProfile) r3
                goto L_0x002f
            L_0x002e:
                r3 = r1
            L_0x002f:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.telephony.ims.ImsReasonInfo> r1 = android.telephony.ims.ImsReasonInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.telephony.ims.ImsReasonInfo r1 = (android.telephony.ims.ImsReasonInfo) r1
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                r5.onRejectedCall(r3, r1)
                return r2
            L_0x0043:
                r7.enforceInterface(r0)
                android.os.IBinder r3 = r7.readStrongBinder()
                com.android.ims.internal.IImsCallSession r3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(r3)
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x005d
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x005e
            L_0x005d:
            L_0x005e:
                r5.onIncomingCall(r3, r1)
                return r2
            L_0x0062:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.aidl.IImsMmTelListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IImsMmTelListener {
            public static IImsMmTelListener sDefaultImpl;
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

            public void onIncomingCall(IImsCallSession c, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(c != null ? c.asBinder() : null);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onIncomingCall(c, extras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRejectedCall(ImsCallProfile callProfile, ImsReasonInfo reason) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callProfile != null) {
                        _data.writeInt(1);
                        callProfile.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (reason != null) {
                        _data.writeInt(1);
                        reason.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRejectedCall(callProfile, reason);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onVoiceMessageCountUpdate(int count) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(count);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onVoiceMessageCountUpdate(count);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IImsMmTelListener impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IImsMmTelListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
