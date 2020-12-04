package android.app.contentsuggestions;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.os.IResultReceiver;

public interface IContentSuggestionsManager extends IInterface {
    void classifyContentSelections(int i, ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException;

    void isEnabled(int i, IResultReceiver iResultReceiver) throws RemoteException;

    void notifyInteraction(int i, String str, Bundle bundle) throws RemoteException;

    void provideContextImage(int i, int i2, Bundle bundle) throws RemoteException;

    void suggestContentSelections(int i, SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException;

    public static class Default implements IContentSuggestionsManager {
        public void provideContextImage(int userId, int taskId, Bundle imageContextRequestExtras) throws RemoteException {
        }

        public void suggestContentSelections(int userId, SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
        }

        public void classifyContentSelections(int userId, ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
        }

        public void notifyInteraction(int userId, String requestId, Bundle interaction) throws RemoteException {
        }

        public void isEnabled(int userId, IResultReceiver receiver) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContentSuggestionsManager {
        private static final String DESCRIPTOR = "android.app.contentsuggestions.IContentSuggestionsManager";
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_isEnabled = 5;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentSuggestionsManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IContentSuggestionsManager)) {
                return new Proxy(obj);
            }
            return (IContentSuggestionsManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "provideContextImage";
                case 2:
                    return "suggestContentSelections";
                case 3:
                    return "classifyContentSelections";
                case 4:
                    return "notifyInteraction";
                case 5:
                    return "isEnabled";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.app.contentsuggestions.SelectionsRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.app.contentsuggestions.ClassificationsRequest} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r7, android.os.Parcel r8, android.os.Parcel r9, int r10) throws android.os.RemoteException {
            /*
                r6 = this;
                java.lang.String r0 = "android.app.contentsuggestions.IContentSuggestionsManager"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r7 == r1) goto L_0x00a8
                r1 = 0
                switch(r7) {
                    case 1: goto L_0x0089;
                    case 2: goto L_0x0066;
                    case 3: goto L_0x0043;
                    case 4: goto L_0x0024;
                    case 5: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r7, r8, r9, r10)
                return r1
            L_0x0011:
                r8.enforceInterface(r0)
                int r1 = r8.readInt()
                android.os.IBinder r3 = r8.readStrongBinder()
                com.android.internal.os.IResultReceiver r3 = com.android.internal.os.IResultReceiver.Stub.asInterface(r3)
                r6.isEnabled(r1, r3)
                return r2
            L_0x0024:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                java.lang.String r4 = r8.readString()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                r6.notifyInteraction(r3, r4, r1)
                return r2
            L_0x0043:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x0059
                android.os.Parcelable$Creator<android.app.contentsuggestions.ClassificationsRequest> r1 = android.app.contentsuggestions.ClassificationsRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.contentsuggestions.ClassificationsRequest r1 = (android.app.contentsuggestions.ClassificationsRequest) r1
                goto L_0x005a
            L_0x0059:
            L_0x005a:
                android.os.IBinder r4 = r8.readStrongBinder()
                android.app.contentsuggestions.IClassificationsCallback r4 = android.app.contentsuggestions.IClassificationsCallback.Stub.asInterface(r4)
                r6.classifyContentSelections(r3, r1, r4)
                return r2
            L_0x0066:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                if (r4 == 0) goto L_0x007c
                android.os.Parcelable$Creator<android.app.contentsuggestions.SelectionsRequest> r1 = android.app.contentsuggestions.SelectionsRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.app.contentsuggestions.SelectionsRequest r1 = (android.app.contentsuggestions.SelectionsRequest) r1
                goto L_0x007d
            L_0x007c:
            L_0x007d:
                android.os.IBinder r4 = r8.readStrongBinder()
                android.app.contentsuggestions.ISelectionsCallback r4 = android.app.contentsuggestions.ISelectionsCallback.Stub.asInterface(r4)
                r6.suggestContentSelections(r3, r1, r4)
                return r2
            L_0x0089:
                r8.enforceInterface(r0)
                int r3 = r8.readInt()
                int r4 = r8.readInt()
                int r5 = r8.readInt()
                if (r5 == 0) goto L_0x00a3
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r8)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00a4
            L_0x00a3:
            L_0x00a4:
                r6.provideContextImage(r3, r4, r1)
                return r2
            L_0x00a8:
                r9.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.contentsuggestions.IContentSuggestionsManager.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IContentSuggestionsManager {
            public static IContentSuggestionsManager sDefaultImpl;
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

            public void provideContextImage(int userId, int taskId, Bundle imageContextRequestExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeInt(taskId);
                    if (imageContextRequestExtras != null) {
                        _data.writeInt(1);
                        imageContextRequestExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().provideContextImage(userId, taskId, imageContextRequestExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void suggestContentSelections(int userId, SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().suggestContentSelections(userId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void classifyContentSelections(int userId, ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().classifyContentSelections(userId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyInteraction(int userId, String requestId, Bundle interaction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(requestId);
                    if (interaction != null) {
                        _data.writeInt(1);
                        interaction.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyInteraction(userId, requestId, interaction);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void isEnabled(int userId, IResultReceiver receiver) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStrongBinder(receiver != null ? receiver.asBinder() : null);
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().isEnabled(userId, receiver);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentSuggestionsManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IContentSuggestionsManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
