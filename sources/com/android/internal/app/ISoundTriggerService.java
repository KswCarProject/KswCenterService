package com.android.internal.app;

import android.content.ComponentName;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.RemoteException;

public interface ISoundTriggerService extends IInterface {
    void deleteSoundModel(ParcelUuid parcelUuid) throws RemoteException;

    int getModelState(ParcelUuid parcelUuid) throws RemoteException;

    SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelUuid) throws RemoteException;

    boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException;

    int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException;

    int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException;

    int startRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException;

    int startRecognitionForService(ParcelUuid parcelUuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException;

    int stopRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException;

    int stopRecognitionForService(ParcelUuid parcelUuid) throws RemoteException;

    int unloadSoundModel(ParcelUuid parcelUuid) throws RemoteException;

    void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException;

    public static class Default implements ISoundTriggerService {
        public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid soundModelId) throws RemoteException {
            return null;
        }

        public void updateSoundModel(SoundTrigger.GenericSoundModel soundModel) throws RemoteException {
        }

        public void deleteSoundModel(ParcelUuid soundModelId) throws RemoteException {
        }

        public int startRecognition(ParcelUuid soundModelId, IRecognitionStatusCallback callback, SoundTrigger.RecognitionConfig config) throws RemoteException {
            return 0;
        }

        public int stopRecognition(ParcelUuid soundModelId, IRecognitionStatusCallback callback) throws RemoteException {
            return 0;
        }

        public int loadGenericSoundModel(SoundTrigger.GenericSoundModel soundModel) throws RemoteException {
            return 0;
        }

        public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel soundModel) throws RemoteException {
            return 0;
        }

        public int startRecognitionForService(ParcelUuid soundModelId, Bundle params, ComponentName callbackIntent, SoundTrigger.RecognitionConfig config) throws RemoteException {
            return 0;
        }

        public int stopRecognitionForService(ParcelUuid soundModelId) throws RemoteException {
            return 0;
        }

        public int unloadSoundModel(ParcelUuid soundModelId) throws RemoteException {
            return 0;
        }

        public boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException {
            return false;
        }

        public int getModelState(ParcelUuid soundModelId) throws RemoteException {
            return 0;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISoundTriggerService {
        private static final String DESCRIPTOR = "com.android.internal.app.ISoundTriggerService";
        static final int TRANSACTION_deleteSoundModel = 3;
        static final int TRANSACTION_getModelState = 12;
        static final int TRANSACTION_getSoundModel = 1;
        static final int TRANSACTION_isRecognitionActive = 11;
        static final int TRANSACTION_loadGenericSoundModel = 6;
        static final int TRANSACTION_loadKeyphraseSoundModel = 7;
        static final int TRANSACTION_startRecognition = 4;
        static final int TRANSACTION_startRecognitionForService = 8;
        static final int TRANSACTION_stopRecognition = 5;
        static final int TRANSACTION_stopRecognitionForService = 9;
        static final int TRANSACTION_unloadSoundModel = 10;
        static final int TRANSACTION_updateSoundModel = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISoundTriggerService)) {
                return new Proxy(obj);
            }
            return (ISoundTriggerService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getSoundModel";
                case 2:
                    return "updateSoundModel";
                case 3:
                    return "deleteSoundModel";
                case 4:
                    return "startRecognition";
                case 5:
                    return "stopRecognition";
                case 6:
                    return "loadGenericSoundModel";
                case 7:
                    return "loadKeyphraseSoundModel";
                case 8:
                    return "startRecognitionForService";
                case 9:
                    return "stopRecognitionForService";
                case 10:
                    return "unloadSoundModel";
                case 11:
                    return "isRecognitionActive";
                case 12:
                    return "getModelState";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.hardware.soundtrigger.SoundTrigger$GenericSoundModel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.hardware.soundtrigger.SoundTrigger$RecognitionConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.hardware.soundtrigger.SoundTrigger$GenericSoundModel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.hardware.soundtrigger.SoundTrigger$RecognitionConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v38, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v42, resolved type: android.os.ParcelUuid} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v46, resolved type: android.os.ParcelUuid} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v54 */
        /* JADX WARNING: type inference failed for: r1v55 */
        /* JADX WARNING: type inference failed for: r1v56 */
        /* JADX WARNING: type inference failed for: r1v57 */
        /* JADX WARNING: type inference failed for: r1v58 */
        /* JADX WARNING: type inference failed for: r1v59 */
        /* JADX WARNING: type inference failed for: r1v60 */
        /* JADX WARNING: type inference failed for: r1v61 */
        /* JADX WARNING: type inference failed for: r1v62 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "com.android.internal.app.ISoundTriggerService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x01cb
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x01a3;
                    case 2: goto L_0x0189;
                    case 3: goto L_0x016f;
                    case 4: goto L_0x0139;
                    case 5: goto L_0x0113;
                    case 6: goto L_0x00f5;
                    case 7: goto L_0x00d7;
                    case 8: goto L_0x0089;
                    case 9: goto L_0x006b;
                    case 10: goto L_0x004d;
                    case 11: goto L_0x002f;
                    case 12: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0024
            L_0x0023:
            L_0x0024:
                int r3 = r7.getModelState(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x002f:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0041
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0042
            L_0x0041:
            L_0x0042:
                boolean r3 = r7.isRecognitionActive(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x004d:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x005f
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0060
            L_0x005f:
            L_0x0060:
                int r3 = r7.unloadSoundModel(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x006b:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x007d
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x007e
            L_0x007d:
            L_0x007e:
                int r3 = r7.stopRecognitionForService(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0089:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x009b
                android.os.Parcelable$Creator<android.os.ParcelUuid> r3 = android.os.ParcelUuid.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.ParcelUuid r3 = (android.os.ParcelUuid) r3
                goto L_0x009c
            L_0x009b:
                r3 = r1
            L_0x009c:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00ab
                android.os.Parcelable$Creator<android.os.Bundle> r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.os.Bundle r4 = (android.os.Bundle) r4
                goto L_0x00ac
            L_0x00ab:
                r4 = r1
            L_0x00ac:
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x00bb
                android.os.Parcelable$Creator<android.content.ComponentName> r5 = android.content.ComponentName.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r9)
                android.content.ComponentName r5 = (android.content.ComponentName) r5
                goto L_0x00bc
            L_0x00bb:
                r5 = r1
            L_0x00bc:
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x00cb
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$RecognitionConfig> r1 = android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.hardware.soundtrigger.SoundTrigger$RecognitionConfig r1 = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) r1
                goto L_0x00cc
            L_0x00cb:
            L_0x00cc:
                int r6 = r7.startRecognitionForService(r3, r4, r5, r1)
                r10.writeNoException()
                r10.writeInt(r6)
                return r2
            L_0x00d7:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x00e9
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel> r1 = android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel r1 = (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) r1
                goto L_0x00ea
            L_0x00e9:
            L_0x00ea:
                int r3 = r7.loadKeyphraseSoundModel(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x00f5:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0107
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$GenericSoundModel> r1 = android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.hardware.soundtrigger.SoundTrigger$GenericSoundModel r1 = (android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) r1
                goto L_0x0108
            L_0x0107:
            L_0x0108:
                int r3 = r7.loadGenericSoundModel(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x0113:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0125
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0126
            L_0x0125:
            L_0x0126:
                android.os.IBinder r3 = r9.readStrongBinder()
                android.hardware.soundtrigger.IRecognitionStatusCallback r3 = android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.asInterface(r3)
                int r4 = r7.stopRecognition(r1, r3)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x0139:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x014b
                android.os.Parcelable$Creator<android.os.ParcelUuid> r3 = android.os.ParcelUuid.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.os.ParcelUuid r3 = (android.os.ParcelUuid) r3
                goto L_0x014c
            L_0x014b:
                r3 = r1
            L_0x014c:
                android.os.IBinder r4 = r9.readStrongBinder()
                android.hardware.soundtrigger.IRecognitionStatusCallback r4 = android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.asInterface(r4)
                int r5 = r9.readInt()
                if (r5 == 0) goto L_0x0163
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$RecognitionConfig> r1 = android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.hardware.soundtrigger.SoundTrigger$RecognitionConfig r1 = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) r1
                goto L_0x0164
            L_0x0163:
            L_0x0164:
                int r5 = r7.startRecognition(r3, r4, r1)
                r10.writeNoException()
                r10.writeInt(r5)
                return r2
            L_0x016f:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x0181
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x0182
            L_0x0181:
            L_0x0182:
                r7.deleteSoundModel(r1)
                r10.writeNoException()
                return r2
            L_0x0189:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x019b
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$GenericSoundModel> r1 = android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.hardware.soundtrigger.SoundTrigger$GenericSoundModel r1 = (android.hardware.soundtrigger.SoundTrigger.GenericSoundModel) r1
                goto L_0x019c
            L_0x019b:
            L_0x019c:
                r7.updateSoundModel(r1)
                r10.writeNoException()
                return r2
            L_0x01a3:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x01b5
                android.os.Parcelable$Creator<android.os.ParcelUuid> r1 = android.os.ParcelUuid.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.ParcelUuid r1 = (android.os.ParcelUuid) r1
                goto L_0x01b6
            L_0x01b5:
            L_0x01b6:
                android.hardware.soundtrigger.SoundTrigger$GenericSoundModel r3 = r7.getSoundModel(r1)
                r10.writeNoException()
                if (r3 == 0) goto L_0x01c6
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x01ca
            L_0x01c6:
                r4 = 0
                r10.writeInt(r4)
            L_0x01ca:
                return r2
            L_0x01cb:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.ISoundTriggerService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ISoundTriggerService {
            public static ISoundTriggerService sDefaultImpl;
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

            public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid soundModelId) throws RemoteException {
                SoundTrigger.GenericSoundModel _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSoundModel(soundModelId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SoundTrigger.GenericSoundModel.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SoundTrigger.GenericSoundModel _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateSoundModel(SoundTrigger.GenericSoundModel soundModel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModel != null) {
                        _data.writeInt(1);
                        soundModel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateSoundModel(soundModel);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteSoundModel(ParcelUuid soundModelId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteSoundModel(soundModelId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int startRecognition(ParcelUuid soundModelId, IRecognitionStatusCallback callback, SoundTrigger.RecognitionConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startRecognition(soundModelId, callback, config);
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

            public int stopRecognition(ParcelUuid soundModelId, IRecognitionStatusCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopRecognition(soundModelId, callback);
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

            public int loadGenericSoundModel(SoundTrigger.GenericSoundModel soundModel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModel != null) {
                        _data.writeInt(1);
                        soundModel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().loadGenericSoundModel(soundModel);
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

            public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel soundModel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModel != null) {
                        _data.writeInt(1);
                        soundModel.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().loadKeyphraseSoundModel(soundModel);
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

            public int startRecognitionForService(ParcelUuid soundModelId, Bundle params, ComponentName callbackIntent, SoundTrigger.RecognitionConfig config) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (params != null) {
                        _data.writeInt(1);
                        params.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (callbackIntent != null) {
                        _data.writeInt(1);
                        callbackIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (config != null) {
                        _data.writeInt(1);
                        config.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startRecognitionForService(soundModelId, params, callbackIntent, config);
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

            public int stopRecognitionForService(ParcelUuid soundModelId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopRecognitionForService(soundModelId);
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

            public int unloadSoundModel(ParcelUuid soundModelId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().unloadSoundModel(soundModelId);
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

            public boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (parcelUuid != null) {
                        _data.writeInt(1);
                        parcelUuid.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRecognitionActive(parcelUuid);
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

            public int getModelState(ParcelUuid soundModelId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (soundModelId != null) {
                        _data.writeInt(1);
                        soundModelId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getModelState(soundModelId);
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
        }

        public static boolean setDefaultImpl(ISoundTriggerService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISoundTriggerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
