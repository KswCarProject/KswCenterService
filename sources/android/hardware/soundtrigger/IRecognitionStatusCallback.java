package android.hardware.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRecognitionStatusCallback extends IInterface {
    void onError(int i) throws RemoteException;

    void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException;

    void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent) throws RemoteException;

    void onRecognitionPaused() throws RemoteException;

    void onRecognitionResumed() throws RemoteException;

    public static class Default implements IRecognitionStatusCallback {
        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent recognitionEvent) throws RemoteException {
        }

        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {
        }

        public void onError(int status) throws RemoteException {
        }

        public void onRecognitionPaused() throws RemoteException {
        }

        public void onRecognitionResumed() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRecognitionStatusCallback {
        private static final String DESCRIPTOR = "android.hardware.soundtrigger.IRecognitionStatusCallback";
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onGenericSoundTriggerDetected = 2;
        static final int TRANSACTION_onKeyphraseDetected = 1;
        static final int TRANSACTION_onRecognitionPaused = 4;
        static final int TRANSACTION_onRecognitionResumed = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRecognitionStatusCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRecognitionStatusCallback)) {
                return new Proxy(obj);
            }
            return (IRecognitionStatusCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onKeyphraseDetected";
                case 2:
                    return "onGenericSoundTriggerDetected";
                case 3:
                    return "onError";
                case 4:
                    return "onRecognitionPaused";
                case 5:
                    return "onRecognitionResumed";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.hardware.soundtrigger.SoundTrigger$KeyphraseRecognitionEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.hardware.soundtrigger.SoundTrigger$GenericRecognitionEvent} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v12 */
        /* JADX WARNING: type inference failed for: r1v13 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "android.hardware.soundtrigger.IRecognitionStatusCallback"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r5 == r1) goto L_0x0058
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x0041;
                    case 2: goto L_0x002a;
                    case 3: goto L_0x001f;
                    case 4: goto L_0x0018;
                    case 5: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r5, r6, r7, r8)
                return r1
            L_0x0011:
                r6.enforceInterface(r0)
                r4.onRecognitionResumed()
                return r2
            L_0x0018:
                r6.enforceInterface(r0)
                r4.onRecognitionPaused()
                return r2
            L_0x001f:
                r6.enforceInterface(r0)
                int r1 = r6.readInt()
                r4.onError(r1)
                return r2
            L_0x002a:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x003c
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$GenericRecognitionEvent> r1 = android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.hardware.soundtrigger.SoundTrigger$GenericRecognitionEvent r1 = (android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent) r1
                goto L_0x003d
            L_0x003c:
            L_0x003d:
                r4.onGenericSoundTriggerDetected(r1)
                return r2
            L_0x0041:
                r6.enforceInterface(r0)
                int r3 = r6.readInt()
                if (r3 == 0) goto L_0x0053
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$KeyphraseRecognitionEvent> r1 = android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r6)
                android.hardware.soundtrigger.SoundTrigger$KeyphraseRecognitionEvent r1 = (android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent) r1
                goto L_0x0054
            L_0x0053:
            L_0x0054:
                r4.onKeyphraseDetected(r1)
                return r2
            L_0x0058:
                r7.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IRecognitionStatusCallback {
            public static IRecognitionStatusCallback sDefaultImpl;
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

            public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent recognitionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (recognitionEvent != null) {
                        _data.writeInt(1);
                        recognitionEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onKeyphraseDetected(recognitionEvent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (recognitionEvent != null) {
                        _data.writeInt(1);
                        recognitionEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGenericSoundTriggerDetected(recognitionEvent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onError(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onError(status);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRecognitionPaused() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRecognitionPaused();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onRecognitionResumed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onRecognitionResumed();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRecognitionStatusCallback impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IRecognitionStatusCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
