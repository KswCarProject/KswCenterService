package android.service.textclassifier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;

public interface ITextClassifierService extends IInterface {
    void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) throws RemoteException;

    void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) throws RemoteException;

    void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) throws RemoteException;

    void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) throws RemoteException;

    public static class Default implements ITextClassifierService {
        public void onSuggestSelection(TextClassificationSessionId sessionId, TextSelection.Request request, ITextClassifierCallback callback) throws RemoteException {
        }

        public void onClassifyText(TextClassificationSessionId sessionId, TextClassification.Request request, ITextClassifierCallback callback) throws RemoteException {
        }

        public void onGenerateLinks(TextClassificationSessionId sessionId, TextLinks.Request request, ITextClassifierCallback callback) throws RemoteException {
        }

        public void onSelectionEvent(TextClassificationSessionId sessionId, SelectionEvent event) throws RemoteException {
        }

        public void onTextClassifierEvent(TextClassificationSessionId sessionId, TextClassifierEvent event) throws RemoteException {
        }

        public void onCreateTextClassificationSession(TextClassificationContext context, TextClassificationSessionId sessionId) throws RemoteException {
        }

        public void onDestroyTextClassificationSession(TextClassificationSessionId sessionId) throws RemoteException {
        }

        public void onDetectLanguage(TextClassificationSessionId sessionId, TextLanguage.Request request, ITextClassifierCallback callback) throws RemoteException {
        }

        public void onSuggestConversationActions(TextClassificationSessionId sessionId, ConversationActions.Request request, ITextClassifierCallback callback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITextClassifierService {
        private static final String DESCRIPTOR = "android.service.textclassifier.ITextClassifierService";
        static final int TRANSACTION_onClassifyText = 2;
        static final int TRANSACTION_onCreateTextClassificationSession = 6;
        static final int TRANSACTION_onDestroyTextClassificationSession = 7;
        static final int TRANSACTION_onDetectLanguage = 8;
        static final int TRANSACTION_onGenerateLinks = 3;
        static final int TRANSACTION_onSelectionEvent = 4;
        static final int TRANSACTION_onSuggestConversationActions = 9;
        static final int TRANSACTION_onSuggestSelection = 1;
        static final int TRANSACTION_onTextClassifierEvent = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITextClassifierService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITextClassifierService)) {
                return new Proxy(obj);
            }
            return (ITextClassifierService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onSuggestSelection";
                case 2:
                    return "onClassifyText";
                case 3:
                    return "onGenerateLinks";
                case 4:
                    return "onSelectionEvent";
                case 5:
                    return "onTextClassifierEvent";
                case 6:
                    return "onCreateTextClassificationSession";
                case 7:
                    return "onDestroyTextClassificationSession";
                case 8:
                    return "onDetectLanguage";
                case 9:
                    return "onSuggestConversationActions";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: android.view.textclassifier.TextSelection$Request} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: android.view.textclassifier.TextClassification$Request} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: android.view.textclassifier.TextLinks$Request} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.view.textclassifier.SelectionEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v22, resolved type: android.view.textclassifier.TextClassificationSessionId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: android.view.textclassifier.TextClassificationSessionId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: android.view.textclassifier.TextLanguage$Request} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: android.view.textclassifier.ConversationActions$Request} */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v18, types: [android.view.textclassifier.TextClassifierEvent] */
        /* JADX WARNING: type inference failed for: r1v39 */
        /* JADX WARNING: type inference failed for: r1v40 */
        /* JADX WARNING: type inference failed for: r1v41 */
        /* JADX WARNING: type inference failed for: r1v42 */
        /* JADX WARNING: type inference failed for: r1v43 */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r6, android.os.Parcel r7, android.os.Parcel r8, int r9) throws android.os.RemoteException {
            /*
                r5 = this;
                java.lang.String r0 = "android.service.textclassifier.ITextClassifierService"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r6 == r1) goto L_0x0188
                r1 = 0
                switch(r6) {
                    case 1: goto L_0x0159;
                    case 2: goto L_0x012a;
                    case 3: goto L_0x00fb;
                    case 4: goto L_0x00d4;
                    case 5: goto L_0x00ad;
                    case 6: goto L_0x0086;
                    case 7: goto L_0x006f;
                    case 8: goto L_0x0040;
                    case 9: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r1 = super.onTransact(r6, r7, r8, r9)
                return r1
            L_0x0011:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0023
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x0024
            L_0x0023:
                r3 = r1
            L_0x0024:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0033
                android.os.Parcelable$Creator<android.view.textclassifier.ConversationActions$Request> r1 = android.view.textclassifier.ConversationActions.Request.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.ConversationActions$Request r1 = (android.view.textclassifier.ConversationActions.Request) r1
                goto L_0x0034
            L_0x0033:
            L_0x0034:
                android.os.IBinder r4 = r7.readStrongBinder()
                android.service.textclassifier.ITextClassifierCallback r4 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(r4)
                r5.onSuggestConversationActions(r3, r1, r4)
                return r2
            L_0x0040:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0052
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x0053
            L_0x0052:
                r3 = r1
            L_0x0053:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x0062
                android.os.Parcelable$Creator<android.view.textclassifier.TextLanguage$Request> r1 = android.view.textclassifier.TextLanguage.Request.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextLanguage$Request r1 = (android.view.textclassifier.TextLanguage.Request) r1
                goto L_0x0063
            L_0x0062:
            L_0x0063:
                android.os.IBinder r4 = r7.readStrongBinder()
                android.service.textclassifier.ITextClassifierCallback r4 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(r4)
                r5.onDetectLanguage(r3, r1, r4)
                return r2
            L_0x006f:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0081
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r1 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r1 = (android.view.textclassifier.TextClassificationSessionId) r1
                goto L_0x0082
            L_0x0081:
            L_0x0082:
                r5.onDestroyTextClassificationSession(r1)
                return r2
            L_0x0086:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x0098
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationContext> r3 = android.view.textclassifier.TextClassificationContext.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationContext r3 = (android.view.textclassifier.TextClassificationContext) r3
                goto L_0x0099
            L_0x0098:
                r3 = r1
            L_0x0099:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00a8
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r1 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r1 = (android.view.textclassifier.TextClassificationSessionId) r1
                goto L_0x00a9
            L_0x00a8:
            L_0x00a9:
                r5.onCreateTextClassificationSession(r3, r1)
                return r2
            L_0x00ad:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x00c0
            L_0x00bf:
                r3 = r1
            L_0x00c0:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00cf
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassifierEvent> r1 = android.view.textclassifier.TextClassifierEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextClassifierEvent r1 = (android.view.textclassifier.TextClassifierEvent) r1
                goto L_0x00d0
            L_0x00cf:
            L_0x00d0:
                r5.onTextClassifierEvent(r3, r1)
                return r2
            L_0x00d4:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x00e6
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x00e7
            L_0x00e6:
                r3 = r1
            L_0x00e7:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x00f6
                android.os.Parcelable$Creator<android.view.textclassifier.SelectionEvent> r1 = android.view.textclassifier.SelectionEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.SelectionEvent r1 = (android.view.textclassifier.SelectionEvent) r1
                goto L_0x00f7
            L_0x00f6:
            L_0x00f7:
                r5.onSelectionEvent(r3, r1)
                return r2
            L_0x00fb:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x010d
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x010e
            L_0x010d:
                r3 = r1
            L_0x010e:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x011d
                android.os.Parcelable$Creator<android.view.textclassifier.TextLinks$Request> r1 = android.view.textclassifier.TextLinks.Request.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextLinks$Request r1 = (android.view.textclassifier.TextLinks.Request) r1
                goto L_0x011e
            L_0x011d:
            L_0x011e:
                android.os.IBinder r4 = r7.readStrongBinder()
                android.service.textclassifier.ITextClassifierCallback r4 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(r4)
                r5.onGenerateLinks(r3, r1, r4)
                return r2
            L_0x012a:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x013c
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x013d
            L_0x013c:
                r3 = r1
            L_0x013d:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x014c
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassification$Request> r1 = android.view.textclassifier.TextClassification.Request.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextClassification$Request r1 = (android.view.textclassifier.TextClassification.Request) r1
                goto L_0x014d
            L_0x014c:
            L_0x014d:
                android.os.IBinder r4 = r7.readStrongBinder()
                android.service.textclassifier.ITextClassifierCallback r4 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(r4)
                r5.onClassifyText(r3, r1, r4)
                return r2
            L_0x0159:
                r7.enforceInterface(r0)
                int r3 = r7.readInt()
                if (r3 == 0) goto L_0x016b
                android.os.Parcelable$Creator<android.view.textclassifier.TextClassificationSessionId> r3 = android.view.textclassifier.TextClassificationSessionId.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r7)
                android.view.textclassifier.TextClassificationSessionId r3 = (android.view.textclassifier.TextClassificationSessionId) r3
                goto L_0x016c
            L_0x016b:
                r3 = r1
            L_0x016c:
                int r4 = r7.readInt()
                if (r4 == 0) goto L_0x017b
                android.os.Parcelable$Creator<android.view.textclassifier.TextSelection$Request> r1 = android.view.textclassifier.TextSelection.Request.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r7)
                android.view.textclassifier.TextSelection$Request r1 = (android.view.textclassifier.TextSelection.Request) r1
                goto L_0x017c
            L_0x017b:
            L_0x017c:
                android.os.IBinder r4 = r7.readStrongBinder()
                android.service.textclassifier.ITextClassifierCallback r4 = android.service.textclassifier.ITextClassifierCallback.Stub.asInterface(r4)
                r5.onSuggestSelection(r3, r1, r4)
                return r2
            L_0x0188:
                r8.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.textclassifier.ITextClassifierService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements ITextClassifierService {
            public static ITextClassifierService sDefaultImpl;
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

            public void onSuggestSelection(TextClassificationSessionId sessionId, TextSelection.Request request, ITextClassifierCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSuggestSelection(sessionId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onClassifyText(TextClassificationSessionId sessionId, TextClassification.Request request, ITextClassifierCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
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
                        Stub.getDefaultImpl().onClassifyText(sessionId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onGenerateLinks(TextClassificationSessionId sessionId, TextLinks.Request request, ITextClassifierCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
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
                        Stub.getDefaultImpl().onGenerateLinks(sessionId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSelectionEvent(TextClassificationSessionId sessionId, SelectionEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSelectionEvent(sessionId, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onTextClassifierEvent(TextClassificationSessionId sessionId, TextClassifierEvent event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (event != null) {
                        _data.writeInt(1);
                        event.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onTextClassifierEvent(sessionId, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onCreateTextClassificationSession(TextClassificationContext context, TextClassificationSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (context != null) {
                        _data.writeInt(1);
                        context.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onCreateTextClassificationSession(context, sessionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDestroyTextClassificationSession(TextClassificationSessionId sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDestroyTextClassificationSession(sessionId);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onDetectLanguage(TextClassificationSessionId sessionId, TextLanguage.Request request, ITextClassifierCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onDetectLanguage(sessionId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void onSuggestConversationActions(TextClassificationSessionId sessionId, ConversationActions.Request request, ITextClassifierCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sessionId != null) {
                        _data.writeInt(1);
                        sessionId.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (request != null) {
                        _data.writeInt(1);
                        request.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().onSuggestConversationActions(sessionId, request, callback);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(ITextClassifierService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static ITextClassifierService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
