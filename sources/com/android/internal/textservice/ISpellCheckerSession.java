package com.android.internal.textservice;

import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.textservice.TextInfo;

/* loaded from: classes4.dex */
public interface ISpellCheckerSession extends IInterface {
    void onCancel() throws RemoteException;

    void onClose() throws RemoteException;

    void onGetSentenceSuggestionsMultiple(TextInfo[] textInfoArr, int i) throws RemoteException;

    void onGetSuggestionsMultiple(TextInfo[] textInfoArr, int i, boolean z) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements ISpellCheckerSession {
        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit, boolean multipleWords) throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onCancel() throws RemoteException {
        }

        @Override // com.android.internal.textservice.ISpellCheckerSession
        public void onClose() throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
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
            if (iin != null && (iin instanceof ISpellCheckerSession)) {
                return (ISpellCheckerSession) iin;
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

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    TextInfo[] _arg0 = (TextInfo[]) data.createTypedArray(TextInfo.CREATOR);
                    int _arg1 = data.readInt();
                    boolean _arg2 = data.readInt() != 0;
                    onGetSuggestionsMultiple(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    TextInfo[] _arg02 = (TextInfo[]) data.createTypedArray(TextInfo.CREATOR);
                    int _arg12 = data.readInt();
                    onGetSentenceSuggestionsMultiple(_arg02, _arg12);
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
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements ISpellCheckerSession {
            public static ISpellCheckerSession sDefaultImpl;
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

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onGetSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit, boolean multipleWords) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(textInfos, 0);
                    _data.writeInt(suggestionsLimit);
                    _data.writeInt(multipleWords ? 1 : 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSuggestionsMultiple(textInfos, suggestionsLimit, multipleWords);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(textInfos, 0);
                    _data.writeInt(suggestionsLimit);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGetSentenceSuggestionsMultiple(textInfos, suggestionsLimit);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onCancel() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCancel();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.textservice.ISpellCheckerSession
            public void onClose() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onClose();
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ISpellCheckerSession impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static ISpellCheckerSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
