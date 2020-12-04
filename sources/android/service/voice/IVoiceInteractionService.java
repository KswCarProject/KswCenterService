package android.service.voice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.app.IVoiceActionCheckCallback;
import java.util.List;

public interface IVoiceInteractionService extends IInterface {
    void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException;

    void launchVoiceAssistFromKeyguard() throws RemoteException;

    void ready() throws RemoteException;

    void shutdown() throws RemoteException;

    void soundModelsChanged() throws RemoteException;

    public static class Default implements IVoiceInteractionService {
        public void ready() throws RemoteException {
        }

        public void soundModelsChanged() throws RemoteException {
        }

        public void shutdown() throws RemoteException {
        }

        public void launchVoiceAssistFromKeyguard() throws RemoteException {
        }

        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVoiceInteractionService {
        private static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionService";
        static final int TRANSACTION_getActiveServiceSupportedActions = 5;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 4;
        static final int TRANSACTION_ready = 1;
        static final int TRANSACTION_shutdown = 3;
        static final int TRANSACTION_soundModelsChanged = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IVoiceInteractionService)) {
                return new Proxy(obj);
            }
            return (IVoiceInteractionService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "ready";
                case 2:
                    return "soundModelsChanged";
                case 3:
                    return "shutdown";
                case 4:
                    return "launchVoiceAssistFromKeyguard";
                case 5:
                    return "getActiveServiceSupportedActions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        ready();
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        soundModelsChanged();
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        shutdown();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        launchVoiceAssistFromKeyguard();
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        getActiveServiceSupportedActions(data.createStringArrayList(), IVoiceActionCheckCallback.Stub.asInterface(data.readStrongBinder()));
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IVoiceInteractionService {
            public static IVoiceInteractionService sDefaultImpl;
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

            public void ready() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().ready();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void soundModelsChanged() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().soundModelsChanged();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void shutdown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().shutdown();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void launchVoiceAssistFromKeyguard() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().launchVoiceAssistFromKeyguard();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getActiveServiceSupportedActions(List<String> voiceActions, IVoiceActionCheckCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(voiceActions);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getActiveServiceSupportedActions(voiceActions, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IVoiceInteractionService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IVoiceInteractionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
