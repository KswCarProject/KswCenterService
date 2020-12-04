package android.service.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.graphics.GraphicBuffer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IContentSuggestionsService extends IInterface {
    void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) throws RemoteException;

    void notifyInteraction(String str, Bundle bundle) throws RemoteException;

    void provideContextImage(int i, GraphicBuffer graphicBuffer, int i2, Bundle bundle) throws RemoteException;

    void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) throws RemoteException;

    public static class Default implements IContentSuggestionsService {
        public void provideContextImage(int taskId, GraphicBuffer contextImage, int colorSpaceId, Bundle imageContextRequestExtras) throws RemoteException {
        }

        public void suggestContentSelections(SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
        }

        public void classifyContentSelections(ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
        }

        public void notifyInteraction(String requestId, Bundle interaction) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContentSuggestionsService {
        private static final String DESCRIPTOR = "android.service.contentsuggestions.IContentSuggestionsService";
        static final int TRANSACTION_classifyContentSelections = 3;
        static final int TRANSACTION_notifyInteraction = 4;
        static final int TRANSACTION_provideContextImage = 1;
        static final int TRANSACTION_suggestContentSelections = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContentSuggestionsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IContentSuggestionsService)) {
                return new Proxy(obj);
            }
            return (IContentSuggestionsService) iin;
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
        /* JADX WARNING: type inference failed for: r1v19 */
        /* JADX WARNING: type inference failed for: r1v20 */
        /* JADX WARNING: type inference failed for: r1v21 */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.service.contentsuggestions.IContentSuggestionsService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x0099
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x006a;
                    case 2: goto L_0x004b;
                    case 3: goto L_0x002c;
                    case 4: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0011:
                r9.enforceInterface(r0)
                java.lang.String r3 = r9.readString()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0027
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0028
            L_0x0027:
            L_0x0028:
                r7.notifyInteraction(r3, r1)
                return r2
            L_0x002c:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.app.contentsuggestions.ClassificationsRequest> r1 = android.app.contentsuggestions.ClassificationsRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.contentsuggestions.ClassificationsRequest r1 = (android.app.contentsuggestions.ClassificationsRequest) r1
                goto L_0x003f
            L_0x003e:
            L_0x003f:
                android.os.IBinder r3 = r9.readStrongBinder()
                android.app.contentsuggestions.IClassificationsCallback r3 = android.app.contentsuggestions.IClassificationsCallback.Stub.asInterface(r3)
                r7.classifyContentSelections(r1, r3)
                return r2
            L_0x004b:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                if (r3 == 0) goto L_0x005d
                android.os.Parcelable$Creator<android.app.contentsuggestions.SelectionsRequest> r1 = android.app.contentsuggestions.SelectionsRequest.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.contentsuggestions.SelectionsRequest r1 = (android.app.contentsuggestions.SelectionsRequest) r1
                goto L_0x005e
            L_0x005d:
            L_0x005e:
                android.os.IBinder r3 = r9.readStrongBinder()
                android.app.contentsuggestions.ISelectionsCallback r3 = android.app.contentsuggestions.ISelectionsCallback.Stub.asInterface(r3)
                r7.suggestContentSelections(r1, r3)
                return r2
            L_0x006a:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x0080
                android.os.Parcelable$Creator<android.graphics.GraphicBuffer> r4 = android.graphics.GraphicBuffer.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r9)
                android.graphics.GraphicBuffer r4 = (android.graphics.GraphicBuffer) r4
                goto L_0x0081
            L_0x0080:
                r4 = r1
            L_0x0081:
                int r5 = r9.readInt()
                int r6 = r9.readInt()
                if (r6 == 0) goto L_0x0094
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0095
            L_0x0094:
            L_0x0095:
                r7.provideContextImage(r3, r4, r5, r1)
                return r2
            L_0x0099:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.contentsuggestions.IContentSuggestionsService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IContentSuggestionsService {
            public static IContentSuggestionsService sDefaultImpl;
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

            public void provideContextImage(int taskId, GraphicBuffer contextImage, int colorSpaceId, Bundle imageContextRequestExtras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(taskId);
                    if (contextImage != null) {
                        _data.writeInt(1);
                        contextImage.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(colorSpaceId);
                    if (imageContextRequestExtras != null) {
                        _data.writeInt(1);
                        imageContextRequestExtras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().provideContextImage(taskId, contextImage, colorSpaceId, imageContextRequestExtras);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void suggestContentSelections(SelectionsRequest request, ISelectionsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().suggestContentSelections(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void classifyContentSelections(ClassificationsRequest request, IClassificationsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().classifyContentSelections(request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyInteraction(String requestId, Bundle interaction) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
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
                        Stub.getDefaultImpl().notifyInteraction(requestId, interaction);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IContentSuggestionsService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IContentSuggestionsService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
