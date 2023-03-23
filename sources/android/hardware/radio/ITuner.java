package android.hardware.radio;

import android.graphics.Bitmap;
import android.hardware.radio.ProgramList;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;
import java.util.Map;

public interface ITuner extends IInterface {
    void cancel() throws RemoteException;

    void cancelAnnouncement() throws RemoteException;

    void close() throws RemoteException;

    RadioManager.BandConfig getConfiguration() throws RemoteException;

    Bitmap getImage(int i) throws RemoteException;

    Map getParameters(List<String> list) throws RemoteException;

    boolean isClosed() throws RemoteException;

    boolean isConfigFlagSet(int i) throws RemoteException;

    boolean isConfigFlagSupported(int i) throws RemoteException;

    boolean isMuted() throws RemoteException;

    void scan(boolean z, boolean z2) throws RemoteException;

    void setConfigFlag(int i, boolean z) throws RemoteException;

    void setConfiguration(RadioManager.BandConfig bandConfig) throws RemoteException;

    void setMuted(boolean z) throws RemoteException;

    Map setParameters(Map map) throws RemoteException;

    boolean startBackgroundScan() throws RemoteException;

    void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException;

    void step(boolean z, boolean z2) throws RemoteException;

    void stopProgramListUpdates() throws RemoteException;

    void tune(ProgramSelector programSelector) throws RemoteException;

    public static class Default implements ITuner {
        public void close() throws RemoteException {
        }

        public boolean isClosed() throws RemoteException {
            return false;
        }

        public void setConfiguration(RadioManager.BandConfig config) throws RemoteException {
        }

        public RadioManager.BandConfig getConfiguration() throws RemoteException {
            return null;
        }

        public void setMuted(boolean mute) throws RemoteException {
        }

        public boolean isMuted() throws RemoteException {
            return false;
        }

        public void step(boolean directionDown, boolean skipSubChannel) throws RemoteException {
        }

        public void scan(boolean directionDown, boolean skipSubChannel) throws RemoteException {
        }

        public void tune(ProgramSelector selector) throws RemoteException {
        }

        public void cancel() throws RemoteException {
        }

        public void cancelAnnouncement() throws RemoteException {
        }

        public Bitmap getImage(int id) throws RemoteException {
            return null;
        }

        public boolean startBackgroundScan() throws RemoteException {
            return false;
        }

        public void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException {
        }

        public void stopProgramListUpdates() throws RemoteException {
        }

        public boolean isConfigFlagSupported(int flag) throws RemoteException {
            return false;
        }

        public boolean isConfigFlagSet(int flag) throws RemoteException {
            return false;
        }

        public void setConfigFlag(int flag, boolean value) throws RemoteException {
        }

        public Map setParameters(Map parameters) throws RemoteException {
            return null;
        }

        public Map getParameters(List<String> list) throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITuner {
        private static final String DESCRIPTOR = "android.hardware.radio.ITuner";
        static final int TRANSACTION_cancel = 10;
        static final int TRANSACTION_cancelAnnouncement = 11;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_getConfiguration = 4;
        static final int TRANSACTION_getImage = 12;
        static final int TRANSACTION_getParameters = 20;
        static final int TRANSACTION_isClosed = 2;
        static final int TRANSACTION_isConfigFlagSet = 17;
        static final int TRANSACTION_isConfigFlagSupported = 16;
        static final int TRANSACTION_isMuted = 6;
        static final int TRANSACTION_scan = 8;
        static final int TRANSACTION_setConfigFlag = 18;
        static final int TRANSACTION_setConfiguration = 3;
        static final int TRANSACTION_setMuted = 5;
        static final int TRANSACTION_setParameters = 19;
        static final int TRANSACTION_startBackgroundScan = 13;
        static final int TRANSACTION_startProgramListUpdates = 14;
        static final int TRANSACTION_step = 7;
        static final int TRANSACTION_stopProgramListUpdates = 15;
        static final int TRANSACTION_tune = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITuner asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITuner)) {
                return new Proxy(obj);
            }
            return (ITuner) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "close";
                case 2:
                    return "isClosed";
                case 3:
                    return "setConfiguration";
                case 4:
                    return "getConfiguration";
                case 5:
                    return "setMuted";
                case 6:
                    return "isMuted";
                case 7:
                    return "step";
                case 8:
                    return "scan";
                case 9:
                    return "tune";
                case 10:
                    return "cancel";
                case 11:
                    return "cancelAnnouncement";
                case 12:
                    return "getImage";
                case 13:
                    return "startBackgroundScan";
                case 14:
                    return "startProgramListUpdates";
                case 15:
                    return "stopProgramListUpdates";
                case 16:
                    return "isConfigFlagSupported";
                case 17:
                    return "isConfigFlagSet";
                case 18:
                    return "setConfigFlag";
                case 19:
                    return "setParameters";
                case 20:
                    return "getParameters";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: android.hardware.radio.ProgramSelector} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v25, resolved type: android.hardware.radio.ProgramList$Filter} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v3, types: [android.hardware.radio.RadioManager$BandConfig] */
        /* JADX WARNING: type inference failed for: r1v36 */
        /* JADX WARNING: type inference failed for: r1v37 */
        /* JADX WARNING: type inference failed for: r1v38 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.hardware.radio.ITuner"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0193
                r1 = 0
                r3 = 0
                switch(r6) {
                    case 1: goto L_0x0189;
                    case 2: goto L_0x017b;
                    case 3: goto L_0x0161;
                    case 4: goto L_0x014a;
                    case 5: goto L_0x0137;
                    case 6: goto L_0x0129;
                    case 7: goto L_0x010e;
                    case 8: goto L_0x00f3;
                    case 9: goto L_0x00d9;
                    case 10: goto L_0x00cf;
                    case 11: goto L_0x00c5;
                    case 12: goto L_0x00aa;
                    case 13: goto L_0x009c;
                    case 14: goto L_0x0082;
                    case 15: goto L_0x0078;
                    case 16: goto L_0x0066;
                    case 17: goto L_0x0054;
                    case 18: goto L_0x003e;
                    case 19: goto L_0x0024;
                    case 20: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0012:
                r7.enforceInterface(r0)
                java.util.ArrayList r1 = r7.createStringArrayList()
                java.util.Map r3 = r5.getParameters(r1)
                r8.writeNoException()
                r8.writeMap(r3)
                return r2
            L_0x0024:
                r7.enforceInterface(r0)
                java.lang.Class r1 = r5.getClass()
                java.lang.ClassLoader r1 = r1.getClassLoader()
                java.util.HashMap r3 = r7.readHashMap(r1)
                java.util.Map r4 = r5.setParameters(r3)
                r8.writeNoException()
                r8.writeMap(r4)
                return r2
            L_0x003e:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x004d
                r3 = r2
            L_0x004d:
                r5.setConfigFlag(r1, r3)
                r8.writeNoException()
                return r2
            L_0x0054:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                boolean r3 = r5.isConfigFlagSet(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0066:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                boolean r3 = r5.isConfigFlagSupported(r1)
                r8.writeNoException()
                r8.writeInt(r3)
                return r2
            L_0x0078:
                r7.enforceInterface(r0)
                r5.stopProgramListUpdates()
                r8.writeNoException()
                return r2
            L_0x0082:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0094
                android.os.Parcelable$Creator<android.hardware.radio.ProgramList$Filter> r1 = android.hardware.radio.ProgramList.Filter.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.hardware.radio.ProgramList$Filter r1 = (android.hardware.radio.ProgramList.Filter) r1
                goto L_0x0095
            L_0x0094:
            L_0x0095:
                r5.startProgramListUpdates(r1)
                r8.writeNoException()
                return r2
            L_0x009c:
                r7.enforceInterface(r0)
                boolean r1 = r5.startBackgroundScan()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x00aa:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                android.graphics.Bitmap r4 = r5.getImage(r1)
                r8.writeNoException()
                if (r4 == 0) goto L_0x00c1
                r8.writeInt(r2)
                r4.writeToParcel(r8, r2)
                goto L_0x00c4
            L_0x00c1:
                r8.writeInt(r3)
            L_0x00c4:
                return r2
            L_0x00c5:
                r7.enforceInterface(r0)
                r5.cancelAnnouncement()
                r8.writeNoException()
                return r2
            L_0x00cf:
                r7.enforceInterface(r0)
                r5.cancel()
                r8.writeNoException()
                return r2
            L_0x00d9:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00eb
                android.os.Parcelable$Creator<android.hardware.radio.ProgramSelector> r1 = android.hardware.radio.ProgramSelector.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.hardware.radio.ProgramSelector r1 = (android.hardware.radio.ProgramSelector) r1
                goto L_0x00ec
            L_0x00eb:
            L_0x00ec:
                r5.tune(r1)
                r8.writeNoException()
                return r2
            L_0x00f3:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x00fe
                r1 = r2
                goto L_0x00ff
            L_0x00fe:
                r1 = r3
            L_0x00ff:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0107
                r3 = r2
            L_0x0107:
                r5.scan(r1, r3)
                r8.writeNoException()
                return r2
            L_0x010e:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0119
                r1 = r2
                goto L_0x011a
            L_0x0119:
                r1 = r3
            L_0x011a:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0122
                r3 = r2
            L_0x0122:
                r5.step(r1, r3)
                r8.writeNoException()
                return r2
            L_0x0129:
                r7.enforceInterface(r0)
                boolean r1 = r5.isMuted()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0137:
                r7.enforceInterface(r0)
                int r1 = r7.readInt()
                if (r1 == 0) goto L_0x0142
                r3 = r2
            L_0x0142:
                r1 = r3
                r5.setMuted(r1)
                r8.writeNoException()
                return r2
            L_0x014a:
                r7.enforceInterface(r0)
                android.hardware.radio.RadioManager$BandConfig r1 = r5.getConfiguration()
                r8.writeNoException()
                if (r1 == 0) goto L_0x015d
                r8.writeInt(r2)
                r1.writeToParcel(r8, r2)
                goto L_0x0160
            L_0x015d:
                r8.writeInt(r3)
            L_0x0160:
                return r2
            L_0x0161:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0173
                android.os.Parcelable$Creator<android.hardware.radio.RadioManager$BandConfig> r1 = android.hardware.radio.RadioManager.BandConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.hardware.radio.RadioManager$BandConfig r1 = (android.hardware.radio.RadioManager.BandConfig) r1
                goto L_0x0174
            L_0x0173:
            L_0x0174:
                r5.setConfiguration(r1)
                r8.writeNoException()
                return r2
            L_0x017b:
                r7.enforceInterface(r0)
                boolean r1 = r5.isClosed()
                r8.writeNoException()
                r8.writeInt(r1)
                return r2
            L_0x0189:
                r7.enforceInterface(r0)
                r5.close()
                r8.writeNoException()
                return r2
            L_0x0193:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.hardware.radio.ITuner.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITuner {
            public static ITuner sDefaultImpl;
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

            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().close();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isClosed() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isClosed();
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

            public void setConfiguration(RadioManager.BandConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setConfiguration(config);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RadioManager.BandConfig getConfiguration() throws RemoteException {
                RadioManager.BandConfig _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getConfiguration();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RadioManager.BandConfig.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RadioManager.BandConfig _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMuted(boolean mute) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mute);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMuted(mute);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMuted() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMuted();
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

            public void step(boolean directionDown, boolean skipSubChannel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(directionDown);
                    _data.writeInt(skipSubChannel);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().step(directionDown, skipSubChannel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void scan(boolean directionDown, boolean skipSubChannel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(directionDown);
                    _data.writeInt(skipSubChannel);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().scan(directionDown, skipSubChannel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void tune(ProgramSelector selector) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (selector != null) {
                        _data.writeInt(1);
                        selector.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().tune(selector);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancel();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelAnnouncement() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelAnnouncement();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bitmap getImage(int id) throws RemoteException {
                Bitmap _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getImage(id);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bitmap.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bitmap _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean startBackgroundScan() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startBackgroundScan();
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

            public void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (filter != null) {
                        _data.writeInt(1);
                        filter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startProgramListUpdates(filter);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopProgramListUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopProgramListUpdates();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isConfigFlagSupported(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConfigFlagSupported(flag);
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

            public boolean isConfigFlagSet(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isConfigFlagSet(flag);
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

            public void setConfigFlag(int flag, boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flag);
                    _data.writeInt(value);
                    if (this.mRemote.transact(18, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setConfigFlag(flag, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map setParameters(Map parameters) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeMap(parameters);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setParameters(parameters);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Map getParameters(List<String> keys) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(keys);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getParameters(keys);
                    }
                    _reply.readException();
                    Map _result = _reply.readHashMap(getClass().getClassLoader());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITuner impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITuner getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
