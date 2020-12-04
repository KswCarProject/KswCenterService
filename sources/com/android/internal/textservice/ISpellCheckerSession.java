package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.textservice.TextInfo;

public interface ISpellCheckerSession extends IInterface {
    void onCancel() throws RemoteException;

    void onClose() throws RemoteException;

    void onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) throws RemoteException;

    void onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) throws RemoteException;

    public static class Default implements ISpellCheckerSession {
        public void onGetSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit, boolean multipleWords) throws RemoteException {
        }

        public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) throws RemoteException {
        }

        public void onCancel() throws RemoteException {
        }

        public void onClose() throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISpellCheckerSession {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerSession";
        static final int TRANSACTION_onCancel = 3;
        static final int TRANSACTION_onClose = 4;
        static final int TRANSACTION_onGetSentenceSuggestionsMultiple = 2;
        static final int TRANSACTION_onGetSuggestionsMultiple = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ISpellCheckerSession)) {
                return new Proxy(obj);
            }
            return (ISpellCheckerSession) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onGetSuggestionsMultiple";
                case 2:
                    return "onGetSentenceSuggestionsMultiple";
                case 3:
                    return "onCancel";
                case 4:
                    return "onClose";
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
                        onGetSuggestionsMultiple((TextInfo[]) data.createTypedArray(TextInfo.CREATOR), data.readInt(), data.readInt() != 0);
                        return true;
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        onGetSentenceSuggestionsMultiple((TextInfo[]) data.createTypedArray(TextInfo.CREATOR), data.readInt());
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        onCancel();
                        return true;
                    case 4:
                        data.enforceInterface(DESCRIPTOR);
                        onClose();
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            } else {
                reply.writeString(DESCRIPTOR);
                return true;
            }
        }

        private static class Proxy implements ISpellCheckerSession {
            public static ISpellCheckerSession sDefaultImpl;
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

            public void onGetSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit, boolean multipleWords) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(textInfos, 0);
                    _data.writeInt(suggestionsLimit);
                    _data.writeInt(multipleWords);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGetSuggestionsMultiple(textInfos, suggestionsLimit, multipleWords);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(textInfos, 0);
                    _data.writeInt(suggestionsLimit);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onGetSentenceSuggestionsMultiple(textInfos, suggestionsLimit);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCancel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCancel();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onClose() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onClose();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISpellCheckerSession impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ISpellCheckerSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
