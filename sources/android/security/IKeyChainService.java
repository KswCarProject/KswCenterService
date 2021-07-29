package android.security;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.StringParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import java.util.List;

public interface IKeyChainService extends IInterface {
    int attestKey(String str, byte[] bArr, int[] iArr, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException;

    boolean containsCaAlias(String str) throws RemoteException;

    boolean deleteCaCertificate(String str) throws RemoteException;

    int generateKeyPair(String str, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec) throws RemoteException;

    List<String> getCaCertificateChainAliases(String str, boolean z) throws RemoteException;

    byte[] getCaCertificates(String str) throws RemoteException;

    byte[] getCertificate(String str) throws RemoteException;

    byte[] getEncodedCaCertificate(String str, boolean z) throws RemoteException;

    StringParceledListSlice getSystemCaAliases() throws RemoteException;

    StringParceledListSlice getUserCaAliases() throws RemoteException;

    boolean hasGrant(int i, String str) throws RemoteException;

    String installCaCertificate(byte[] bArr) throws RemoteException;

    boolean installKeyPair(byte[] bArr, byte[] bArr2, byte[] bArr3, String str) throws RemoteException;

    boolean isUserSelectable(String str) throws RemoteException;

    boolean removeKeyPair(String str) throws RemoteException;

    @UnsupportedAppUsage
    String requestPrivateKey(String str) throws RemoteException;

    boolean reset() throws RemoteException;

    void setGrant(int i, String str, boolean z) throws RemoteException;

    boolean setKeyPairCertificate(String str, byte[] bArr, byte[] bArr2) throws RemoteException;

    void setUserSelectable(String str, boolean z) throws RemoteException;

    public static class Default implements IKeyChainService {
        public String requestPrivateKey(String alias) throws RemoteException {
            return null;
        }

        public byte[] getCertificate(String alias) throws RemoteException {
            return null;
        }

        public byte[] getCaCertificates(String alias) throws RemoteException {
            return null;
        }

        public boolean isUserSelectable(String alias) throws RemoteException {
            return false;
        }

        public void setUserSelectable(String alias, boolean isUserSelectable) throws RemoteException {
        }

        public int generateKeyPair(String algorithm, ParcelableKeyGenParameterSpec spec) throws RemoteException {
            return 0;
        }

        public int attestKey(String alias, byte[] challenge, int[] idAttestationFlags, KeymasterCertificateChain chain) throws RemoteException {
            return 0;
        }

        public boolean setKeyPairCertificate(String alias, byte[] userCert, byte[] certChain) throws RemoteException {
            return false;
        }

        public String installCaCertificate(byte[] caCertificate) throws RemoteException {
            return null;
        }

        public boolean installKeyPair(byte[] privateKey, byte[] userCert, byte[] certChain, String alias) throws RemoteException {
            return false;
        }

        public boolean removeKeyPair(String alias) throws RemoteException {
            return false;
        }

        public boolean deleteCaCertificate(String alias) throws RemoteException {
            return false;
        }

        public boolean reset() throws RemoteException {
            return false;
        }

        public StringParceledListSlice getUserCaAliases() throws RemoteException {
            return null;
        }

        public StringParceledListSlice getSystemCaAliases() throws RemoteException {
            return null;
        }

        public boolean containsCaAlias(String alias) throws RemoteException {
            return false;
        }

        public byte[] getEncodedCaCertificate(String alias, boolean includeDeletedSystem) throws RemoteException {
            return null;
        }

        public List<String> getCaCertificateChainAliases(String rootAlias, boolean includeDeletedSystem) throws RemoteException {
            return null;
        }

        public void setGrant(int uid, String alias, boolean value) throws RemoteException {
        }

        public boolean hasGrant(int uid, String alias) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IKeyChainService {
        private static final String DESCRIPTOR = "android.security.IKeyChainService";
        static final int TRANSACTION_attestKey = 7;
        static final int TRANSACTION_containsCaAlias = 16;
        static final int TRANSACTION_deleteCaCertificate = 12;
        static final int TRANSACTION_generateKeyPair = 6;
        static final int TRANSACTION_getCaCertificateChainAliases = 18;
        static final int TRANSACTION_getCaCertificates = 3;
        static final int TRANSACTION_getCertificate = 2;
        static final int TRANSACTION_getEncodedCaCertificate = 17;
        static final int TRANSACTION_getSystemCaAliases = 15;
        static final int TRANSACTION_getUserCaAliases = 14;
        static final int TRANSACTION_hasGrant = 20;
        static final int TRANSACTION_installCaCertificate = 9;
        static final int TRANSACTION_installKeyPair = 10;
        static final int TRANSACTION_isUserSelectable = 4;
        static final int TRANSACTION_removeKeyPair = 11;
        static final int TRANSACTION_requestPrivateKey = 1;
        static final int TRANSACTION_reset = 13;
        static final int TRANSACTION_setGrant = 19;
        static final int TRANSACTION_setKeyPairCertificate = 8;
        static final int TRANSACTION_setUserSelectable = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeyChainService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IKeyChainService)) {
                return new Proxy(obj);
            }
            return (IKeyChainService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "requestPrivateKey";
                case 2:
                    return "getCertificate";
                case 3:
                    return "getCaCertificates";
                case 4:
                    return "isUserSelectable";
                case 5:
                    return "setUserSelectable";
                case 6:
                    return "generateKeyPair";
                case 7:
                    return "attestKey";
                case 8:
                    return "setKeyPairCertificate";
                case 9:
                    return "installCaCertificate";
                case 10:
                    return "installKeyPair";
                case 11:
                    return "removeKeyPair";
                case 12:
                    return "deleteCaCertificate";
                case 13:
                    return "reset";
                case 14:
                    return "getUserCaAliases";
                case 15:
                    return "getSystemCaAliases";
                case 16:
                    return "containsCaAlias";
                case 17:
                    return "getEncodedCaCertificate";
                case 18:
                    return "getCaCertificateChainAliases";
                case 19:
                    return "setGrant";
                case 20:
                    return "hasGrant";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParcelableKeyGenParameterSpec _arg1;
            if (code != 1598968902) {
                boolean _arg2 = false;
                switch (code) {
                    case 1:
                        data.enforceInterface(DESCRIPTOR);
                        String _result = requestPrivateKey(data.readString());
                        reply.writeNoException();
                        reply.writeString(_result);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        byte[] _result2 = getCertificate(data.readString());
                        reply.writeNoException();
                        reply.writeByteArray(_result2);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        byte[] _result3 = getCaCertificates(data.readString());
                        reply.writeNoException();
                        reply.writeByteArray(_result3);
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result4 = isUserSelectable(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result4);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg0 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        setUserSelectable(_arg0, _arg2);
                        reply.writeNoException();
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        if (data.readInt() != 0) {
                            _arg1 = ParcelableKeyGenParameterSpec.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        int _result5 = generateKeyPair(_arg02, _arg1);
                        reply.writeNoException();
                        reply.writeInt(_result5);
                        return true;
                    case 7:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg03 = data.readString();
                        byte[] _arg12 = data.createByteArray();
                        int[] _arg22 = data.createIntArray();
                        KeymasterCertificateChain _arg3 = new KeymasterCertificateChain();
                        int _result6 = attestKey(_arg03, _arg12, _arg22, _arg3);
                        reply.writeNoException();
                        reply.writeInt(_result6);
                        reply.writeInt(1);
                        _arg3.writeToParcel(reply, 1);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result7 = setKeyPairCertificate(data.readString(), data.createByteArray(), data.createByteArray());
                        reply.writeNoException();
                        reply.writeInt(_result7);
                        return true;
                    case 9:
                        data.enforceInterface(DESCRIPTOR);
                        String _result8 = installCaCertificate(data.createByteArray());
                        reply.writeNoException();
                        reply.writeString(_result8);
                        return true;
                    case 10:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result9 = installKeyPair(data.createByteArray(), data.createByteArray(), data.createByteArray(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result9);
                        return true;
                    case 11:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result10 = removeKeyPair(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result10);
                        return true;
                    case 12:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result11 = deleteCaCertificate(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result11);
                        return true;
                    case 13:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result12 = reset();
                        reply.writeNoException();
                        reply.writeInt(_result12);
                        return true;
                    case 14:
                        data.enforceInterface(DESCRIPTOR);
                        StringParceledListSlice _result13 = getUserCaAliases();
                        reply.writeNoException();
                        if (_result13 != null) {
                            reply.writeInt(1);
                            _result13.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 15:
                        data.enforceInterface(DESCRIPTOR);
                        StringParceledListSlice _result14 = getSystemCaAliases();
                        reply.writeNoException();
                        if (_result14 != null) {
                            reply.writeInt(1);
                            _result14.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 16:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result15 = containsCaAlias(data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result15);
                        return true;
                    case 17:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg04 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        byte[] _result16 = getEncodedCaCertificate(_arg04, _arg2);
                        reply.writeNoException();
                        reply.writeByteArray(_result16);
                        return true;
                    case 18:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg05 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        List<String> _result17 = getCaCertificateChainAliases(_arg05, _arg2);
                        reply.writeNoException();
                        reply.writeStringList(_result17);
                        return true;
                    case 19:
                        data.enforceInterface(DESCRIPTOR);
                        int _arg06 = data.readInt();
                        String _arg13 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = true;
                        }
                        setGrant(_arg06, _arg13, _arg2);
                        reply.writeNoException();
                        return true;
                    case 20:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result18 = hasGrant(data.readInt(), data.readString());
                        reply.writeNoException();
                        reply.writeInt(_result18);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements IKeyChainService {
            public static IKeyChainService sDefaultImpl;
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

            public String requestPrivateKey(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestPrivateKey(alias);
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

            public byte[] getCertificate(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCertificate(alias);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] getCaCertificates(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCaCertificates(alias);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isUserSelectable(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isUserSelectable(alias);
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

            public void setUserSelectable(String alias, boolean isUserSelectable) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(isUserSelectable);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUserSelectable(alias, isUserSelectable);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int generateKeyPair(String algorithm, ParcelableKeyGenParameterSpec spec) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(algorithm);
                    if (spec != null) {
                        _data.writeInt(1);
                        spec.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().generateKeyPair(algorithm, spec);
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

            public int attestKey(String alias, byte[] challenge, int[] idAttestationFlags, KeymasterCertificateChain chain) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(challenge);
                    _data.writeIntArray(idAttestationFlags);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().attestKey(alias, challenge, idAttestationFlags, chain);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    if (_reply.readInt() != 0) {
                        chain.readFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setKeyPairCertificate(String alias, byte[] userCert, byte[] certChain) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeByteArray(userCert);
                    _data.writeByteArray(certChain);
                    boolean z = false;
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setKeyPairCertificate(alias, userCert, certChain);
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

            public String installCaCertificate(byte[] caCertificate) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(caCertificate);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().installCaCertificate(caCertificate);
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

            public boolean installKeyPair(byte[] privateKey, byte[] userCert, byte[] certChain, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(privateKey);
                    _data.writeByteArray(userCert);
                    _data.writeByteArray(certChain);
                    _data.writeString(alias);
                    boolean z = false;
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().installKeyPair(privateKey, userCert, certChain, alias);
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

            public boolean removeKeyPair(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeKeyPair(alias);
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

            public boolean deleteCaCertificate(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    boolean z = false;
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteCaCertificate(alias);
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

            public boolean reset() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().reset();
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

            public StringParceledListSlice getUserCaAliases() throws RemoteException {
                StringParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserCaAliases();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StringParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StringParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public StringParceledListSlice getSystemCaAliases() throws RemoteException {
                StringParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getSystemCaAliases();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = StringParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    StringParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean containsCaAlias(String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    boolean z = false;
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().containsCaAlias(alias);
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

            public byte[] getEncodedCaCertificate(String alias, boolean includeDeletedSystem) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(alias);
                    _data.writeInt(includeDeletedSystem);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getEncodedCaCertificate(alias, includeDeletedSystem);
                    }
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getCaCertificateChainAliases(String rootAlias, boolean includeDeletedSystem) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rootAlias);
                    _data.writeInt(includeDeletedSystem);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCaCertificateChainAliases(rootAlias, includeDeletedSystem);
                    }
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setGrant(int uid, String alias, boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(alias);
                    _data.writeInt(value);
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setGrant(uid, alias, value);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasGrant(int uid, String alias) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uid);
                    _data.writeString(alias);
                    boolean z = false;
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasGrant(uid, alias);
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

        public static boolean setDefaultImpl(IKeyChainService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IKeyChainService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
