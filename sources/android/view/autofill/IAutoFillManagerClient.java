package android.view.autofill;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import com.android.internal.os.IResultReceiver;
import java.util.List;

public interface IAutoFillManagerClient extends IInterface {
    void authenticate(int i, int i2, IntentSender intentSender, Intent intent) throws RemoteException;

    void autofill(int i, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException;

    void dispatchUnhandledKey(int i, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException;

    void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException;

    void notifyNoFillUi(int i, AutofillId autofillId, int i2) throws RemoteException;

    void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException;

    void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException;

    void setSaveUiState(int i, boolean z) throws RemoteException;

    void setSessionFinished(int i, List<AutofillId> list) throws RemoteException;

    void setState(int i) throws RemoteException;

    void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId) throws RemoteException;

    void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException;

    public static class Default implements IAutoFillManagerClient {
        public void setState(int flags) throws RemoteException {
        }

        public void autofill(int sessionId, List<AutofillId> list, List<AutofillValue> list2) throws RemoteException {
        }

        public void authenticate(int sessionId, int authenticationId, IntentSender intent, Intent fillInIntent) throws RemoteException {
        }

        public void setTrackedViews(int sessionId, AutofillId[] savableIds, boolean saveOnAllViewsInvisible, boolean saveOnFinish, AutofillId[] fillableIds, AutofillId saveTriggerId) throws RemoteException {
        }

        public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
        }

        public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
        }

        public void notifyNoFillUi(int sessionId, AutofillId id, int sessionFinishedState) throws RemoteException {
        }

        public void dispatchUnhandledKey(int sessionId, AutofillId id, KeyEvent keyEvent) throws RemoteException {
        }

        public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
        }

        public void setSaveUiState(int sessionId, boolean shown) throws RemoteException {
        }

        public void setSessionFinished(int newState, List<AutofillId> list) throws RemoteException {
        }

        public void getAugmentedAutofillClient(IResultReceiver result) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAutoFillManagerClient {
        private static final String DESCRIPTOR = "android.view.autofill.IAutoFillManagerClient";
        static final int TRANSACTION_authenticate = 3;
        static final int TRANSACTION_autofill = 2;
        static final int TRANSACTION_dispatchUnhandledKey = 8;
        static final int TRANSACTION_getAugmentedAutofillClient = 12;
        static final int TRANSACTION_notifyNoFillUi = 7;
        static final int TRANSACTION_requestHideFillUi = 6;
        static final int TRANSACTION_requestShowFillUi = 5;
        static final int TRANSACTION_setSaveUiState = 10;
        static final int TRANSACTION_setSessionFinished = 11;
        static final int TRANSACTION_setState = 1;
        static final int TRANSACTION_setTrackedViews = 4;
        static final int TRANSACTION_startIntentSender = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManagerClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAutoFillManagerClient)) {
                return new Proxy(obj);
            }
            return (IAutoFillManagerClient) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setState";
                case 2:
                    return "autofill";
                case 3:
                    return "authenticate";
                case 4:
                    return "setTrackedViews";
                case 5:
                    return "requestShowFillUi";
                case 6:
                    return "requestHideFillUi";
                case 7:
                    return "notifyNoFillUi";
                case 8:
                    return "dispatchUnhandledKey";
                case 9:
                    return "startIntentSender";
                case 10:
                    return "setSaveUiState";
                case 11:
                    return "setSessionFinished";
                case 12:
                    return "getAugmentedAutofillClient";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.view.autofill.AutofillId} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: android.view.KeyEvent} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v3, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v21, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v28 */
        /* JADX WARNING: type inference failed for: r1v29 */
        /* JADX WARNING: type inference failed for: r1v30 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v32 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
            /*
                r16 = this;
                r7 = r16
                r8 = r17
                r9 = r18
                java.lang.String r10 = "android.view.autofill.IAutoFillManagerClient"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r11 = 1
                if (r8 == r0) goto L_0x01b0
                r0 = 0
                r1 = 0
                switch(r8) {
                    case 1: goto L_0x01a5;
                    case 2: goto L_0x018e;
                    case 3: goto L_0x015f;
                    case 4: goto L_0x011a;
                    case 5: goto L_0x00d7;
                    case 6: goto L_0x00bc;
                    case 7: goto L_0x009d;
                    case 8: goto L_0x0072;
                    case 9: goto L_0x004b;
                    case 10: goto L_0x0038;
                    case 11: goto L_0x0027;
                    case 12: goto L_0x0018;
                    default: goto L_0x0013;
                }
            L_0x0013:
                boolean r0 = super.onTransact(r17, r18, r19, r20)
                return r0
            L_0x0018:
                r9.enforceInterface(r10)
                android.os.IBinder r0 = r18.readStrongBinder()
                com.android.internal.os.IResultReceiver r0 = com.android.internal.os.IResultReceiver.Stub.asInterface(r0)
                r7.getAugmentedAutofillClient(r0)
                return r11
            L_0x0027:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.util.ArrayList r1 = r9.createTypedArrayList(r1)
                r7.setSessionFinished(r0, r1)
                return r11
            L_0x0038:
                r9.enforceInterface(r10)
                int r1 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x0047
                r0 = r11
            L_0x0047:
                r7.setSaveUiState(r1, r0)
                return r11
            L_0x004b:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x005d
                android.os.Parcelable$Creator<android.content.IntentSender> r0 = android.content.IntentSender.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.content.IntentSender r0 = (android.content.IntentSender) r0
                goto L_0x005e
            L_0x005d:
                r0 = r1
            L_0x005e:
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x006d
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x006e
            L_0x006d:
            L_0x006e:
                r7.startIntentSender(r0, r1)
                return r11
            L_0x0072:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x0088
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r2 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r9)
                android.view.autofill.AutofillId r2 = (android.view.autofill.AutofillId) r2
                goto L_0x0089
            L_0x0088:
                r2 = r1
            L_0x0089:
                int r3 = r18.readInt()
                if (r3 == 0) goto L_0x0098
                android.os.Parcelable$Creator<android.view.KeyEvent> r1 = android.view.KeyEvent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.view.KeyEvent r1 = (android.view.KeyEvent) r1
                goto L_0x0099
            L_0x0098:
            L_0x0099:
                r7.dispatchUnhandledKey(r0, r2, r1)
                return r11
            L_0x009d:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x00b3
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.view.autofill.AutofillId r1 = (android.view.autofill.AutofillId) r1
                goto L_0x00b4
            L_0x00b3:
            L_0x00b4:
                int r2 = r18.readInt()
                r7.notifyNoFillUi(r0, r1, r2)
                return r11
            L_0x00bc:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x00d2
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.view.autofill.AutofillId r1 = (android.view.autofill.AutofillId) r1
                goto L_0x00d3
            L_0x00d2:
            L_0x00d3:
                r7.requestHideFillUi(r0, r1)
                return r11
            L_0x00d7:
                r9.enforceInterface(r10)
                int r12 = r18.readInt()
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x00ee
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r0 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.autofill.AutofillId r0 = (android.view.autofill.AutofillId) r0
                r2 = r0
                goto L_0x00ef
            L_0x00ee:
                r2 = r1
            L_0x00ef:
                int r13 = r18.readInt()
                int r14 = r18.readInt()
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x0107
                android.os.Parcelable$Creator<android.graphics.Rect> r0 = android.graphics.Rect.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.graphics.Rect r0 = (android.graphics.Rect) r0
                r5 = r0
                goto L_0x0108
            L_0x0107:
                r5 = r1
            L_0x0108:
                android.os.IBinder r0 = r18.readStrongBinder()
                android.view.autofill.IAutofillWindowPresenter r15 = android.view.autofill.IAutofillWindowPresenter.Stub.asInterface(r0)
                r0 = r16
                r1 = r12
                r3 = r13
                r4 = r14
                r6 = r15
                r0.requestShowFillUi(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x011a:
                r9.enforceInterface(r10)
                int r12 = r18.readInt()
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r2 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object[] r2 = r9.createTypedArray(r2)
                r13 = r2
                android.view.autofill.AutofillId[] r13 = (android.view.autofill.AutofillId[]) r13
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x0132
                r3 = r11
                goto L_0x0133
            L_0x0132:
                r3 = r0
            L_0x0133:
                int r2 = r18.readInt()
                if (r2 == 0) goto L_0x013b
                r4 = r11
                goto L_0x013c
            L_0x013b:
                r4 = r0
            L_0x013c:
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r0 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object[] r0 = r9.createTypedArray(r0)
                r14 = r0
                android.view.autofill.AutofillId[] r14 = (android.view.autofill.AutofillId[]) r14
                int r0 = r18.readInt()
                if (r0 == 0) goto L_0x0155
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r0 = android.view.autofill.AutofillId.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r9)
                android.view.autofill.AutofillId r0 = (android.view.autofill.AutofillId) r0
                r6 = r0
                goto L_0x0156
            L_0x0155:
                r6 = r1
            L_0x0156:
                r0 = r16
                r1 = r12
                r2 = r13
                r5 = r14
                r0.setTrackedViews(r1, r2, r3, r4, r5, r6)
                return r11
            L_0x015f:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                int r2 = r18.readInt()
                int r3 = r18.readInt()
                if (r3 == 0) goto L_0x0179
                android.os.Parcelable$Creator<android.content.IntentSender> r3 = android.content.IntentSender.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.content.IntentSender r3 = (android.content.IntentSender) r3
                goto L_0x017a
            L_0x0179:
                r3 = r1
            L_0x017a:
                int r4 = r18.readInt()
                if (r4 == 0) goto L_0x0189
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x018a
            L_0x0189:
            L_0x018a:
                r7.authenticate(r0, r2, r3, r1)
                return r11
            L_0x018e:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                android.os.Parcelable$Creator<android.view.autofill.AutofillId> r1 = android.view.autofill.AutofillId.CREATOR
                java.util.ArrayList r1 = r9.createTypedArrayList(r1)
                android.os.Parcelable$Creator<android.view.autofill.AutofillValue> r2 = android.view.autofill.AutofillValue.CREATOR
                java.util.ArrayList r2 = r9.createTypedArrayList(r2)
                r7.autofill(r0, r1, r2)
                return r11
            L_0x01a5:
                r9.enforceInterface(r10)
                int r0 = r18.readInt()
                r7.setState(r0)
                return r11
            L_0x01b0:
                r0 = r19
                r0.writeString(r10)
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: android.view.autofill.IAutoFillManagerClient.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAutoFillManagerClient {
            public static IAutoFillManagerClient sDefaultImpl;
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

            public void setState(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(1, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setState(flags);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedList(ids);
                    _data.writeTypedList(values);
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().autofill(sessionId, ids, values);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void authenticate(int sessionId, int authenticationId, IntentSender intent, Intent fillInIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(authenticationId);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (fillInIntent != null) {
                        _data.writeInt(1);
                        fillInIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().authenticate(sessionId, authenticationId, intent, fillInIntent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setTrackedViews(int sessionId, AutofillId[] savableIds, boolean saveOnAllViewsInvisible, boolean saveOnFinish, AutofillId[] fillableIds, AutofillId saveTriggerId) throws RemoteException {
                AutofillId autofillId = saveTriggerId;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(sessionId);
                    } catch (Throwable th) {
                        th = th;
                        AutofillId[] autofillIdArr = savableIds;
                        boolean z = saveOnAllViewsInvisible;
                        boolean z2 = saveOnFinish;
                        AutofillId[] autofillIdArr2 = fillableIds;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedArray(savableIds, 0);
                        try {
                            _data.writeInt(saveOnAllViewsInvisible ? 1 : 0);
                            try {
                                _data.writeInt(saveOnFinish ? 1 : 0);
                            } catch (Throwable th2) {
                                th = th2;
                                AutofillId[] autofillIdArr22 = fillableIds;
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            boolean z22 = saveOnFinish;
                            AutofillId[] autofillIdArr222 = fillableIds;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        boolean z3 = saveOnAllViewsInvisible;
                        boolean z222 = saveOnFinish;
                        AutofillId[] autofillIdArr2222 = fillableIds;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeTypedArray(fillableIds, 0);
                        if (autofillId != null) {
                            _data.writeInt(1);
                            autofillId.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().setTrackedViews(sessionId, savableIds, saveOnAllViewsInvisible, saveOnFinish, fillableIds, saveTriggerId);
                            _data.recycle();
                        } catch (Throwable th5) {
                            th = th5;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    int i = sessionId;
                    AutofillId[] autofillIdArr3 = savableIds;
                    boolean z32 = saveOnAllViewsInvisible;
                    boolean z2222 = saveOnFinish;
                    AutofillId[] autofillIdArr22222 = fillableIds;
                    _data.recycle();
                    throw th;
                }
            }

            public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
                AutofillId autofillId = id;
                Rect rect = anchorBounds;
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(sessionId);
                        if (autofillId != null) {
                            _data.writeInt(1);
                            autofillId.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeInt(width);
                        } catch (Throwable th) {
                            th = th;
                            int i = height;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        int i2 = width;
                        int i3 = height;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(height);
                        if (rect != null) {
                            _data.writeInt(1);
                            rect.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        _data.writeStrongBinder(presenter != null ? presenter.asBinder() : null);
                        try {
                            if (this.mRemote.transact(5, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().requestShowFillUi(sessionId, id, width, height, anchorBounds, presenter);
                            _data.recycle();
                        } catch (Throwable th3) {
                            th = th3;
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    int i4 = sessionId;
                    int i22 = width;
                    int i32 = height;
                    _data.recycle();
                    throw th;
                }
            }

            public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (id != null) {
                        _data.writeInt(1);
                        id.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().requestHideFillUi(sessionId, id);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void notifyNoFillUi(int sessionId, AutofillId id, int sessionFinishedState) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (id != null) {
                        _data.writeInt(1);
                        id.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sessionFinishedState);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().notifyNoFillUi(sessionId, id, sessionFinishedState);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void dispatchUnhandledKey(int sessionId, AutofillId id, KeyEvent keyEvent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    if (id != null) {
                        _data.writeInt(1);
                        id.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (keyEvent != null) {
                        _data.writeInt(1);
                        keyEvent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().dispatchUnhandledKey(sessionId, id, keyEvent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentSender != null) {
                        _data.writeInt(1);
                        intentSender.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(9, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().startIntentSender(intentSender, intent);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSaveUiState(int sessionId, boolean shown) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(shown);
                    if (this.mRemote.transact(10, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSaveUiState(sessionId, shown);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSessionFinished(int newState, List<AutofillId> autofillableIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newState);
                    _data.writeTypedList(autofillableIds);
                    if (this.mRemote.transact(11, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setSessionFinished(newState, autofillableIds);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void getAugmentedAutofillClient(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(result != null ? result.asBinder() : null);
                    if (this.mRemote.transact(12, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().getAugmentedAutofillClient(result);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAutoFillManagerClient impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAutoFillManagerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
