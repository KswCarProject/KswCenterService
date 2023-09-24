package android.media.p006tv;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.Surface;

/* renamed from: android.media.tv.ITvInputHardware */
/* loaded from: classes3.dex */
public interface ITvInputHardware extends IInterface {
    void overrideAudioSink(int i, String str, int i2, int i3, int i4) throws RemoteException;

    void setStreamVolume(float f) throws RemoteException;

    boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) throws RemoteException;

    /* renamed from: android.media.tv.ITvInputHardware$Default */
    /* loaded from: classes3.dex */
    public static class Default implements ITvInputHardware {
        @Override // android.media.p006tv.ITvInputHardware
        public boolean setSurface(Surface surface, TvStreamConfig config) throws RemoteException {
            return false;
        }

        @Override // android.media.p006tv.ITvInputHardware
        public void setStreamVolume(float volume) throws RemoteException {
        }

        @Override // android.media.p006tv.ITvInputHardware
        public void overrideAudioSink(int audioType, String audioAddress, int samplingRate, int channelMask, int format) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* renamed from: android.media.tv.ITvInputHardware$Stub */
    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements ITvInputHardware {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputHardware";
        static final int TRANSACTION_overrideAudioSink = 3;
        static final int TRANSACTION_setStreamVolume = 2;
        static final int TRANSACTION_setSurface = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputHardware asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITvInputHardware)) {
                return (ITvInputHardware) iin;
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
                    return "setSurface";
                case 2:
                    return "setStreamVolume";
                case 3:
                    return "overrideAudioSink";
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
            Surface _arg0;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = Surface.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    TvStreamConfig _arg1 = data.readInt() != 0 ? TvStreamConfig.CREATOR.createFromParcel(data) : null;
                    boolean surface = setSurface(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(surface ? 1 : 0);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    float _arg02 = data.readFloat();
                    setStreamVolume(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    int _arg3 = data.readInt();
                    int _arg4 = data.readInt();
                    overrideAudioSink(_arg03, _arg12, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* renamed from: android.media.tv.ITvInputHardware$Stub$Proxy */
        /* loaded from: classes3.dex */
        private static class Proxy implements ITvInputHardware {
            public static ITvInputHardware sDefaultImpl;
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

            @Override // android.media.p006tv.ITvInputHardware
            public boolean setSurface(Surface surface, TvStreamConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (surface != null) {
                        _data.writeInt(1);
                        surface.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setSurface(surface, config);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputHardware
            public void setStreamVolume(float volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeFloat(volume);
                    boolean _status = this.mRemote.transact(2, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStreamVolume(volume);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.p006tv.ITvInputHardware
            public void overrideAudioSink(int audioType, String audioAddress, int samplingRate, int channelMask, int format) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioType);
                    _data.writeString(audioAddress);
                    _data.writeInt(samplingRate);
                    _data.writeInt(channelMask);
                    _data.writeInt(format);
                    boolean _status = this.mRemote.transact(3, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overrideAudioSink(audioType, audioAddress, samplingRate, channelMask, format);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITvInputHardware impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ITvInputHardware getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}