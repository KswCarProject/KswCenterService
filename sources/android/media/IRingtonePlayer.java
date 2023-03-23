package android.media;

import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.UserHandle;

public interface IRingtonePlayer extends IInterface {
    String getTitle(Uri uri) throws RemoteException;

    boolean isPlaying(IBinder iBinder) throws RemoteException;

    ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException;

    void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z) throws RemoteException;

    void playAsync(Uri uri, UserHandle userHandle, boolean z, AudioAttributes audioAttributes) throws RemoteException;

    void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean z, VolumeShaper.Configuration configuration) throws RemoteException;

    void setPlaybackProperties(IBinder iBinder, float f, boolean z) throws RemoteException;

    void stop(IBinder iBinder) throws RemoteException;

    void stopAsync() throws RemoteException;

    public static class Default implements IRingtonePlayer {
        public void play(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping) throws RemoteException {
        }

        public void playWithVolumeShaping(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping, VolumeShaper.Configuration volumeShaperConfig) throws RemoteException {
        }

        public void stop(IBinder token) throws RemoteException {
        }

        public boolean isPlaying(IBinder token) throws RemoteException {
            return false;
        }

        public void setPlaybackProperties(IBinder token, float volume, boolean looping) throws RemoteException {
        }

        public void playAsync(Uri uri, UserHandle user, boolean looping, AudioAttributes aa) throws RemoteException {
        }

        public void stopAsync() throws RemoteException {
        }

        public String getTitle(Uri uri) throws RemoteException {
            return null;
        }

        public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRingtonePlayer {
        private static final String DESCRIPTOR = "android.media.IRingtonePlayer";
        static final int TRANSACTION_getTitle = 8;
        static final int TRANSACTION_isPlaying = 4;
        static final int TRANSACTION_openRingtone = 9;
        static final int TRANSACTION_play = 1;
        static final int TRANSACTION_playAsync = 6;
        static final int TRANSACTION_playWithVolumeShaping = 2;
        static final int TRANSACTION_setPlaybackProperties = 5;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_stopAsync = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRingtonePlayer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRingtonePlayer)) {
                return new Proxy(obj);
            }
            return (IRingtonePlayer) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "play";
                case 2:
                    return "playWithVolumeShaping";
                case 3:
                    return "stop";
                case 4:
                    return "isPlaying";
                case 5:
                    return "setPlaybackProperties";
                case 6:
                    return "playAsync";
                case 7:
                    return "stopAsync";
                case 8:
                    return "getTitle";
                case 9:
                    return "openRingtone";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.media.AudioAttributes} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v14 */
        /* JADX WARNING: type inference failed for: r1v18, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: type inference failed for: r1v24 */
        /* JADX WARNING: type inference failed for: r1v25 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r16, android.os.Parcel r17, android.os.Parcel r18, int r19) throws android.os.RemoteException {
            /*
                r15 = this;
                r7 = r15
                r8 = r16
                r9 = r17
                r10 = r18
                java.lang.String r11 = "android.media.IRingtonePlayer"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r12 = 1
                if (r8 == r0) goto L_0x0162
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x0126;
                    case 2: goto L_0x00da;
                    case 3: goto L_0x00cf;
                    case 4: goto L_0x00bd;
                    case 5: goto L_0x00a6;
                    case 6: goto L_0x0067;
                    case 7: goto L_0x0060;
                    case 8: goto L_0x0040;
                    case 9: goto L_0x0019;
                    default: goto L_0x0014;
                }
            L_0x0014:
                boolean r0 = super.onTransact(r16, r17, r18, r19)
                return r0
            L_0x0019:
                r9.enforceInterface(r11)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x002b
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x002c
            L_0x002b:
            L_0x002c:
                android.os.ParcelFileDescriptor r2 = r15.openRingtone(r1)
                r18.writeNoException()
                if (r2 == 0) goto L_0x003c
                r10.writeInt(r12)
                r2.writeToParcel(r10, r12)
                goto L_0x003f
            L_0x003c:
                r10.writeInt(r0)
            L_0x003f:
                return r12
            L_0x0040:
                r9.enforceInterface(r11)
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x0053
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                r1 = r0
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x0054
            L_0x0053:
            L_0x0054:
                r0 = r1
                java.lang.String r1 = r15.getTitle(r0)
                r18.writeNoException()
                r10.writeString(r1)
                return r12
            L_0x0060:
                r9.enforceInterface(r11)
                r15.stopAsync()
                return r12
            L_0x0067:
                r9.enforceInterface(r11)
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x0079
                android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.net.Uri r2 = (android.net.Uri) r2
                goto L_0x007a
            L_0x0079:
                r2 = r1
            L_0x007a:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x0089
                android.os.Parcelable$Creator<android.os.UserHandle> r3 = android.os.UserHandle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                goto L_0x008a
            L_0x0089:
                r3 = r1
            L_0x008a:
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x0092
                r0 = r12
            L_0x0092:
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x00a1
                android.os.Parcelable$Creator<android.media.AudioAttributes> r1 = android.media.AudioAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x00a2
            L_0x00a1:
            L_0x00a2:
                r15.playAsync(r2, r3, r0, r1)
                return r12
            L_0x00a6:
                r9.enforceInterface(r11)
                android.os.IBinder r1 = r17.readStrongBinder()
                float r2 = r17.readFloat()
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x00b9
                r0 = r12
            L_0x00b9:
                r15.setPlaybackProperties(r1, r2, r0)
                return r12
            L_0x00bd:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r17.readStrongBinder()
                boolean r1 = r15.isPlaying(r0)
                r18.writeNoException()
                r10.writeInt(r1)
                return r12
            L_0x00cf:
                r9.enforceInterface(r11)
                android.os.IBinder r0 = r17.readStrongBinder()
                r15.stop(r0)
                return r12
            L_0x00da:
                r9.enforceInterface(r11)
                android.os.IBinder r13 = r17.readStrongBinder()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x00f0
                android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.net.Uri r2 = (android.net.Uri) r2
                goto L_0x00f1
            L_0x00f0:
                r2 = r1
            L_0x00f1:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x0100
                android.os.Parcelable$Creator<android.media.AudioAttributes> r3 = android.media.AudioAttributes.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.media.AudioAttributes r3 = (android.media.AudioAttributes) r3
                goto L_0x0101
            L_0x0100:
                r3 = r1
            L_0x0101:
                float r14 = r17.readFloat()
                int r4 = r17.readInt()
                if (r4 == 0) goto L_0x010d
                r5 = r12
                goto L_0x010e
            L_0x010d:
                r5 = r0
            L_0x010e:
                int r0 = r17.readInt()
                if (r0 == 0) goto L_0x011e
                android.os.Parcelable$Creator<android.media.VolumeShaper$Configuration> r0 = android.media.VolumeShaper.Configuration.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.media.VolumeShaper$Configuration r0 = (android.media.VolumeShaper.Configuration) r0
                r6 = r0
                goto L_0x011f
            L_0x011e:
                r6 = r1
            L_0x011f:
                r0 = r15
                r1 = r13
                r4 = r14
                r0.playWithVolumeShaping(r1, r2, r3, r4, r5, r6)
                return r12
            L_0x0126:
                r9.enforceInterface(r11)
                android.os.IBinder r6 = r17.readStrongBinder()
                int r2 = r17.readInt()
                if (r2 == 0) goto L_0x013c
                android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.net.Uri r2 = (android.net.Uri) r2
                goto L_0x013d
            L_0x013c:
                r2 = r1
            L_0x013d:
                int r3 = r17.readInt()
                if (r3 == 0) goto L_0x014d
                android.os.Parcelable$Creator<android.media.AudioAttributes> r1 = android.media.AudioAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
            L_0x014b:
                r3 = r1
                goto L_0x014e
            L_0x014d:
                goto L_0x014b
            L_0x014e:
                float r13 = r17.readFloat()
                int r1 = r17.readInt()
                if (r1 == 0) goto L_0x015a
                r5 = r12
                goto L_0x015b
            L_0x015a:
                r5 = r0
            L_0x015b:
                r0 = r15
                r1 = r6
                r4 = r13
                r0.play(r1, r2, r3, r4, r5)
                return r12
            L_0x0162:
                r10.writeString(r11)
                return r12
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.IRingtonePlayer.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IRingtonePlayer {
            public static IRingtonePlayer sDefaultImpl;
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

            public void play(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeFloat(volume);
                    _data.writeInt(looping);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().play(token, uri, aa, volume, looping);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void playWithVolumeShaping(IBinder token, Uri uri, AudioAttributes aa, float volume, boolean looping, VolumeShaper.Configuration volumeShaperConfig) throws RemoteException {
                Uri uri2 = uri;
                AudioAttributes audioAttributes = aa;
                VolumeShaper.Configuration configuration = volumeShaperConfig;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(token);
                        if (uri2 != null) {
                            _data.writeInt(1);
                            uri2.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (audioAttributes != null) {
                            _data.writeInt(1);
                            audioAttributes.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        float f = volume;
                        boolean z = looping;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeFloat(volume);
                        try {
                            _data.writeInt(looping ? 1 : 0);
                            if (configuration != null) {
                                _data.writeInt(1);
                                configuration.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            try {
                                if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                    _data.recycle();
                                    return;
                                }
                                Stub.getDefaultImpl().playWithVolumeShaping(token, uri, aa, volume, looping, volumeShaperConfig);
                                _data.recycle();
                            } catch (Throwable th2) {
                                th = th2;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        boolean z2 = looping;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    IBinder iBinder = token;
                    float f2 = volume;
                    boolean z22 = looping;
                    _data.recycle();
                    throw th;
                }
            }

            public void stop(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stop(token);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean isPlaying(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isPlaying(token);
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

            public void setPlaybackProperties(IBinder token, float volume, boolean looping) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeFloat(volume);
                    _data.writeInt(looping);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setPlaybackProperties(token, volume, looping);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void playAsync(Uri uri, UserHandle user, boolean looping, AudioAttributes aa) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (user != null) {
                        _data.writeInt(1);
                        user.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(looping);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playAsync(uri, user, looping, aa);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void stopAsync() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().stopAsync();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public String getTitle(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getTitle(uri);
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

            public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
                ParcelFileDescriptor _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().openRingtone(uri);
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
        }

        public static boolean setDefaultImpl(IRingtonePlayer impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IRingtonePlayer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
