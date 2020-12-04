package com.android.internal.telephony;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IMms extends IInterface {
    Uri addMultimediaMessageDraft(String str, Uri uri) throws RemoteException;

    Uri addTextMessageDraft(String str, String str2, String str3) throws RemoteException;

    boolean archiveStoredConversation(String str, long j, boolean z) throws RemoteException;

    boolean deleteStoredConversation(String str, long j) throws RemoteException;

    boolean deleteStoredMessage(String str, Uri uri) throws RemoteException;

    void downloadMessage(int i, String str, String str2, Uri uri, Bundle bundle, PendingIntent pendingIntent) throws RemoteException;

    boolean getAutoPersisting() throws RemoteException;

    Bundle getCarrierConfigValues(int i) throws RemoteException;

    Uri importMultimediaMessage(String str, Uri uri, String str2, long j, boolean z, boolean z2) throws RemoteException;

    Uri importTextMessage(String str, String str2, int i, String str3, long j, boolean z, boolean z2) throws RemoteException;

    void sendMessage(int i, String str, Uri uri, String str2, Bundle bundle, PendingIntent pendingIntent) throws RemoteException;

    void sendStoredMessage(int i, String str, Uri uri, Bundle bundle, PendingIntent pendingIntent) throws RemoteException;

    void setAutoPersisting(String str, boolean z) throws RemoteException;

    boolean updateStoredMessageStatus(String str, Uri uri, ContentValues contentValues) throws RemoteException;

    public static class Default implements IMms {
        public void sendMessage(int subId, String callingPkg, Uri contentUri, String locationUrl, Bundle configOverrides, PendingIntent sentIntent) throws RemoteException {
        }

        public void downloadMessage(int subId, String callingPkg, String locationUrl, Uri contentUri, Bundle configOverrides, PendingIntent downloadedIntent) throws RemoteException {
        }

        public Bundle getCarrierConfigValues(int subId) throws RemoteException {
            return null;
        }

        public Uri importTextMessage(String callingPkg, String address, int type, String text, long timestampMillis, boolean seen, boolean read) throws RemoteException {
            return null;
        }

        public Uri importMultimediaMessage(String callingPkg, Uri contentUri, String messageId, long timestampSecs, boolean seen, boolean read) throws RemoteException {
            return null;
        }

        public boolean deleteStoredMessage(String callingPkg, Uri messageUri) throws RemoteException {
            return false;
        }

        public boolean deleteStoredConversation(String callingPkg, long conversationId) throws RemoteException {
            return false;
        }

        public boolean updateStoredMessageStatus(String callingPkg, Uri messageUri, ContentValues statusValues) throws RemoteException {
            return false;
        }

        public boolean archiveStoredConversation(String callingPkg, long conversationId, boolean archived) throws RemoteException {
            return false;
        }

        public Uri addTextMessageDraft(String callingPkg, String address, String text) throws RemoteException {
            return null;
        }

        public Uri addMultimediaMessageDraft(String callingPkg, Uri contentUri) throws RemoteException {
            return null;
        }

        public void sendStoredMessage(int subId, String callingPkg, Uri messageUri, Bundle configOverrides, PendingIntent sentIntent) throws RemoteException {
        }

        public void setAutoPersisting(String callingPkg, boolean enabled) throws RemoteException {
        }

        public boolean getAutoPersisting() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMms {
        private static final String DESCRIPTOR = "com.android.internal.telephony.IMms";
        static final int TRANSACTION_addMultimediaMessageDraft = 11;
        static final int TRANSACTION_addTextMessageDraft = 10;
        static final int TRANSACTION_archiveStoredConversation = 9;
        static final int TRANSACTION_deleteStoredConversation = 7;
        static final int TRANSACTION_deleteStoredMessage = 6;
        static final int TRANSACTION_downloadMessage = 2;
        static final int TRANSACTION_getAutoPersisting = 14;
        static final int TRANSACTION_getCarrierConfigValues = 3;
        static final int TRANSACTION_importMultimediaMessage = 5;
        static final int TRANSACTION_importTextMessage = 4;
        static final int TRANSACTION_sendMessage = 1;
        static final int TRANSACTION_sendStoredMessage = 12;
        static final int TRANSACTION_setAutoPersisting = 13;
        static final int TRANSACTION_updateStoredMessageStatus = 8;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMms asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IMms)) {
                return new Proxy(obj);
            }
            return (IMms) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "sendMessage";
                case 2:
                    return "downloadMessage";
                case 3:
                    return "getCarrierConfigValues";
                case 4:
                    return "importTextMessage";
                case 5:
                    return "importMultimediaMessage";
                case 6:
                    return "deleteStoredMessage";
                case 7:
                    return "deleteStoredConversation";
                case 8:
                    return "updateStoredMessageStatus";
                case 9:
                    return "archiveStoredConversation";
                case 10:
                    return "addTextMessageDraft";
                case 11:
                    return "addMultimediaMessageDraft";
                case 12:
                    return "sendStoredMessage";
                case 13:
                    return "setAutoPersisting";
                case 14:
                    return "getAutoPersisting";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v30, resolved type: android.content.ContentValues} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2 */
        /* JADX WARNING: type inference failed for: r0v7 */
        /* JADX WARNING: type inference failed for: r0v17 */
        /* JADX WARNING: type inference failed for: r0v25, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v36, types: [android.net.Uri] */
        /* JADX WARNING: type inference failed for: r0v40 */
        /* JADX WARNING: type inference failed for: r0v48 */
        /* JADX WARNING: type inference failed for: r0v49 */
        /* JADX WARNING: type inference failed for: r0v50 */
        /* JADX WARNING: type inference failed for: r0v51 */
        /* JADX WARNING: type inference failed for: r0v52 */
        /* JADX WARNING: type inference failed for: r0v53 */
        /* JADX WARNING: type inference failed for: r0v54 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r23, android.os.Parcel r24, android.os.Parcel r25, int r26) throws android.os.RemoteException {
            /*
                r22 = this;
                r9 = r22
                r10 = r23
                r11 = r24
                r12 = r25
                java.lang.String r13 = "com.android.internal.telephony.IMms"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r14 = 1
                if (r10 == r0) goto L_0x02ac
                r15 = 0
                r0 = 0
                switch(r10) {
                    case 1: goto L_0x025e;
                    case 2: goto L_0x0210;
                    case 3: goto L_0x01f5;
                    case 4: goto L_0x01ac;
                    case 5: goto L_0x015f;
                    case 6: goto L_0x013d;
                    case 7: goto L_0x0127;
                    case 8: goto L_0x00f5;
                    case 9: goto L_0x00d6;
                    case 10: goto L_0x00b3;
                    case 11: goto L_0x0088;
                    case 12: goto L_0x003f;
                    case 13: goto L_0x0028;
                    case 14: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r23, r24, r25, r26)
                return r0
            L_0x001a:
                r11.enforceInterface(r13)
                boolean r0 = r22.getAutoPersisting()
                r25.writeNoException()
                r12.writeInt(r0)
                return r14
            L_0x0028:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0037
                r15 = r14
            L_0x0037:
                r1 = r15
                r9.setAutoPersisting(r0, r1)
                r25.writeNoException()
                return r14
            L_0x003f:
                r11.enforceInterface(r13)
                int r6 = r24.readInt()
                java.lang.String r7 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x005a
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.net.Uri r1 = (android.net.Uri) r1
                r3 = r1
                goto L_0x005b
            L_0x005a:
                r3 = r0
            L_0x005b:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x006b
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r4 = r1
                goto L_0x006c
            L_0x006b:
                r4 = r0
            L_0x006c:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x007c
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x007a:
                r5 = r0
                goto L_0x007d
            L_0x007c:
                goto L_0x007a
            L_0x007d:
                r0 = r22
                r1 = r6
                r2 = r7
                r0.sendStoredMessage(r1, r2, r3, r4, r5)
                r25.writeNoException()
                return r14
            L_0x0088:
                r11.enforceInterface(r13)
                java.lang.String r1 = r24.readString()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x009e
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x009f
            L_0x009e:
            L_0x009f:
                android.net.Uri r2 = r9.addMultimediaMessageDraft(r1, r0)
                r25.writeNoException()
                if (r2 == 0) goto L_0x00af
                r12.writeInt(r14)
                r2.writeToParcel(r12, r14)
                goto L_0x00b2
            L_0x00af:
                r12.writeInt(r15)
            L_0x00b2:
                return r14
            L_0x00b3:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                java.lang.String r1 = r24.readString()
                java.lang.String r2 = r24.readString()
                android.net.Uri r3 = r9.addTextMessageDraft(r0, r1, r2)
                r25.writeNoException()
                if (r3 == 0) goto L_0x00d2
                r12.writeInt(r14)
                r3.writeToParcel(r12, r14)
                goto L_0x00d5
            L_0x00d2:
                r12.writeInt(r15)
            L_0x00d5:
                return r14
            L_0x00d6:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                long r1 = r24.readLong()
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x00e9
                r15 = r14
            L_0x00e9:
                r3 = r15
                boolean r4 = r9.archiveStoredConversation(r0, r1, r3)
                r25.writeNoException()
                r12.writeInt(r4)
                return r14
            L_0x00f5:
                r11.enforceInterface(r13)
                java.lang.String r1 = r24.readString()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x010b
                android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r11)
                android.net.Uri r2 = (android.net.Uri) r2
                goto L_0x010c
            L_0x010b:
                r2 = r0
            L_0x010c:
                int r3 = r24.readInt()
                if (r3 == 0) goto L_0x011b
                android.os.Parcelable$Creator<android.content.ContentValues> r0 = android.content.ContentValues.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.content.ContentValues r0 = (android.content.ContentValues) r0
                goto L_0x011c
            L_0x011b:
            L_0x011c:
                boolean r3 = r9.updateStoredMessageStatus(r1, r2, r0)
                r25.writeNoException()
                r12.writeInt(r3)
                return r14
            L_0x0127:
                r11.enforceInterface(r13)
                java.lang.String r0 = r24.readString()
                long r1 = r24.readLong()
                boolean r3 = r9.deleteStoredConversation(r0, r1)
                r25.writeNoException()
                r12.writeInt(r3)
                return r14
            L_0x013d:
                r11.enforceInterface(r13)
                java.lang.String r1 = r24.readString()
                int r2 = r24.readInt()
                if (r2 == 0) goto L_0x0153
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.net.Uri r0 = (android.net.Uri) r0
                goto L_0x0154
            L_0x0153:
            L_0x0154:
                boolean r2 = r9.deleteStoredMessage(r1, r0)
                r25.writeNoException()
                r12.writeInt(r2)
                return r14
            L_0x015f:
                r11.enforceInterface(r13)
                java.lang.String r8 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0176
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.net.Uri r0 = (android.net.Uri) r0
            L_0x0174:
                r2 = r0
                goto L_0x0177
            L_0x0176:
                goto L_0x0174
            L_0x0177:
                java.lang.String r16 = r24.readString()
                long r17 = r24.readLong()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0187
                r6 = r14
                goto L_0x0188
            L_0x0187:
                r6 = r15
            L_0x0188:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x0190
                r7 = r14
                goto L_0x0191
            L_0x0190:
                r7 = r15
            L_0x0191:
                r0 = r22
                r1 = r8
                r3 = r16
                r4 = r17
                android.net.Uri r0 = r0.importMultimediaMessage(r1, r2, r3, r4, r6, r7)
                r25.writeNoException()
                if (r0 == 0) goto L_0x01a8
                r12.writeInt(r14)
                r0.writeToParcel(r12, r14)
                goto L_0x01ab
            L_0x01a8:
                r12.writeInt(r15)
            L_0x01ab:
                return r14
            L_0x01ac:
                r11.enforceInterface(r13)
                java.lang.String r16 = r24.readString()
                java.lang.String r17 = r24.readString()
                int r18 = r24.readInt()
                java.lang.String r19 = r24.readString()
                long r20 = r24.readLong()
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x01cb
                r7 = r14
                goto L_0x01cc
            L_0x01cb:
                r7 = r15
            L_0x01cc:
                int r0 = r24.readInt()
                if (r0 == 0) goto L_0x01d4
                r8 = r14
                goto L_0x01d5
            L_0x01d4:
                r8 = r15
            L_0x01d5:
                r0 = r22
                r1 = r16
                r2 = r17
                r3 = r18
                r4 = r19
                r5 = r20
                android.net.Uri r0 = r0.importTextMessage(r1, r2, r3, r4, r5, r7, r8)
                r25.writeNoException()
                if (r0 == 0) goto L_0x01f1
                r12.writeInt(r14)
                r0.writeToParcel(r12, r14)
                goto L_0x01f4
            L_0x01f1:
                r12.writeInt(r15)
            L_0x01f4:
                return r14
            L_0x01f5:
                r11.enforceInterface(r13)
                int r0 = r24.readInt()
                android.os.Bundle r1 = r9.getCarrierConfigValues(r0)
                r25.writeNoException()
                if (r1 == 0) goto L_0x020c
                r12.writeInt(r14)
                r1.writeToParcel(r12, r14)
                goto L_0x020f
            L_0x020c:
                r12.writeInt(r15)
            L_0x020f:
                return r14
            L_0x0210:
                r11.enforceInterface(r13)
                int r7 = r24.readInt()
                java.lang.String r8 = r24.readString()
                java.lang.String r15 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x022f
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.net.Uri r1 = (android.net.Uri) r1
                r4 = r1
                goto L_0x0230
            L_0x022f:
                r4 = r0
            L_0x0230:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0240
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x0241
            L_0x0240:
                r5 = r0
            L_0x0241:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0251
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x024f:
                r6 = r0
                goto L_0x0252
            L_0x0251:
                goto L_0x024f
            L_0x0252:
                r0 = r22
                r1 = r7
                r2 = r8
                r3 = r15
                r0.downloadMessage(r1, r2, r3, r4, r5, r6)
                r25.writeNoException()
                return r14
            L_0x025e:
                r11.enforceInterface(r13)
                int r7 = r24.readInt()
                java.lang.String r8 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x0279
                android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.net.Uri r1 = (android.net.Uri) r1
                r3 = r1
                goto L_0x027a
            L_0x0279:
                r3 = r0
            L_0x027a:
                java.lang.String r15 = r24.readString()
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x028e
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r11)
                android.os.Bundle r1 = (android.os.Bundle) r1
                r5 = r1
                goto L_0x028f
            L_0x028e:
                r5 = r0
            L_0x028f:
                int r1 = r24.readInt()
                if (r1 == 0) goto L_0x029f
                android.os.Parcelable$Creator<android.app.PendingIntent> r0 = android.app.PendingIntent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r11)
                android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            L_0x029d:
                r6 = r0
                goto L_0x02a0
            L_0x029f:
                goto L_0x029d
            L_0x02a0:
                r0 = r22
                r1 = r7
                r2 = r8
                r4 = r15
                r0.sendMessage(r1, r2, r3, r4, r5, r6)
                r25.writeNoException()
                return r14
            L_0x02ac:
                r12.writeString(r13)
                return r14
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.IMms.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IMms {
            public static IMms sDefaultImpl;
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

            public void sendMessage(int subId, String callingPkg, Uri contentUri, String locationUrl, Bundle configOverrides, PendingIntent sentIntent) throws RemoteException {
                Uri uri = contentUri;
                Bundle bundle = configOverrides;
                PendingIntent pendingIntent = sentIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                    } catch (Throwable th) {
                        th = th;
                        String str = callingPkg;
                        String str2 = locationUrl;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(callingPkg);
                        if (uri != null) {
                            _data.writeInt(1);
                            uri.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeString(locationUrl);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (pendingIntent != null) {
                                _data.writeInt(1);
                                pendingIntent.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().sendMessage(subId, callingPkg, contentUri, locationUrl, configOverrides, sentIntent);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        String str22 = locationUrl;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i = subId;
                    String str3 = callingPkg;
                    String str222 = locationUrl;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void downloadMessage(int subId, String callingPkg, String locationUrl, Uri contentUri, Bundle configOverrides, PendingIntent downloadedIntent) throws RemoteException {
                Uri uri = contentUri;
                Bundle bundle = configOverrides;
                PendingIntent pendingIntent = downloadedIntent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeInt(subId);
                    } catch (Throwable th) {
                        th = th;
                        String str = callingPkg;
                        String str2 = locationUrl;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(callingPkg);
                    } catch (Throwable th2) {
                        th = th2;
                        String str22 = locationUrl;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(locationUrl);
                        if (uri != null) {
                            _data.writeInt(1);
                            uri.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (bundle != null) {
                            _data.writeInt(1);
                            bundle.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (pendingIntent != null) {
                            _data.writeInt(1);
                            pendingIntent.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            _reply.recycle();
                            _data.recycle();
                            return;
                        }
                        Stub.getDefaultImpl().downloadMessage(subId, callingPkg, locationUrl, contentUri, configOverrides, downloadedIntent);
                        _reply.recycle();
                        _data.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    int i = subId;
                    String str3 = callingPkg;
                    String str222 = locationUrl;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public Bundle getCarrierConfigValues(int subId) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCarrierConfigValues(subId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Bundle _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri importTextMessage(String callingPkg, String address, int type, String text, long timestampMillis, boolean seen, boolean read) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPkg);
                        try {
                            _data.writeString(address);
                        } catch (Throwable th) {
                            th = th;
                            int i = type;
                            String str = text;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeInt(type);
                            try {
                                _data.writeString(text);
                                _data.writeLong(timestampMillis);
                                _data.writeInt(seen ? 1 : 0);
                                _data.writeInt(read ? 1 : 0);
                                if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                    _reply.readException();
                                    if (_reply.readInt() != 0) {
                                        _result = Uri.CREATOR.createFromParcel(_reply);
                                    } else {
                                        _result = null;
                                    }
                                    Uri _result2 = _result;
                                    _reply.recycle();
                                    _data.recycle();
                                    return _result2;
                                }
                                Uri importTextMessage = Stub.getDefaultImpl().importTextMessage(callingPkg, address, type, text, timestampMillis, seen, read);
                                _reply.recycle();
                                _data.recycle();
                                return importTextMessage;
                            } catch (Throwable th2) {
                                th = th2;
                                _reply.recycle();
                                _data.recycle();
                                throw th;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            String str2 = text;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        String str3 = address;
                        int i2 = type;
                        String str22 = text;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str4 = callingPkg;
                    String str32 = address;
                    int i22 = type;
                    String str222 = text;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public Uri importMultimediaMessage(String callingPkg, Uri contentUri, String messageId, long timestampSecs, boolean seen, boolean read) throws RemoteException {
                Uri _result;
                Uri uri = contentUri;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPkg);
                        if (uri != null) {
                            _data.writeInt(1);
                            uri.writeToParcel(_data, 0);
                        } else {
                            _data.writeInt(0);
                        }
                        try {
                            _data.writeString(messageId);
                        } catch (Throwable th) {
                            th = th;
                            long j = timestampSecs;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = messageId;
                        long j2 = timestampSecs;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(timestampSecs);
                        _data.writeInt(seen ? 1 : 0);
                        _data.writeInt(read ? 1 : 0);
                        if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                            _reply.readException();
                            if (_reply.readInt() != 0) {
                                _result = Uri.CREATOR.createFromParcel(_reply);
                            } else {
                                _result = null;
                            }
                            Uri _result2 = _result;
                            _reply.recycle();
                            _data.recycle();
                            return _result2;
                        }
                        Uri importMultimediaMessage = Stub.getDefaultImpl().importMultimediaMessage(callingPkg, contentUri, messageId, timestampSecs, seen, read);
                        _reply.recycle();
                        _data.recycle();
                        return importMultimediaMessage;
                    } catch (Throwable th3) {
                        th = th3;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str2 = callingPkg;
                    String str3 = messageId;
                    long j22 = timestampSecs;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean deleteStoredMessage(String callingPkg, Uri messageUri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    boolean _result = true;
                    if (messageUri != null) {
                        _data.writeInt(1);
                        messageUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteStoredMessage(callingPkg, messageUri);
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

            public boolean deleteStoredConversation(String callingPkg, long conversationId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeLong(conversationId);
                    boolean z = false;
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteStoredConversation(callingPkg, conversationId);
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

            public boolean updateStoredMessageStatus(String callingPkg, Uri messageUri, ContentValues statusValues) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    boolean _result = true;
                    if (messageUri != null) {
                        _data.writeInt(1);
                        messageUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (statusValues != null) {
                        _data.writeInt(1);
                        statusValues.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateStoredMessageStatus(callingPkg, messageUri, statusValues);
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

            public boolean archiveStoredConversation(String callingPkg, long conversationId, boolean archived) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeLong(conversationId);
                    _data.writeInt(archived);
                    boolean z = false;
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().archiveStoredConversation(callingPkg, conversationId, archived);
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

            public Uri addTextMessageDraft(String callingPkg, String address, String text) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeString(address);
                    _data.writeString(text);
                    if (!this.mRemote.transact(10, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addTextMessageDraft(callingPkg, address, text);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Uri addMultimediaMessageDraft(String callingPkg, Uri contentUri) throws RemoteException {
                Uri _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    if (contentUri != null) {
                        _data.writeInt(1);
                        contentUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addMultimediaMessageDraft(callingPkg, contentUri);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = Uri.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    Uri _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendStoredMessage(int subId, String callingPkg, Uri messageUri, Bundle configOverrides, PendingIntent sentIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPkg);
                    if (messageUri != null) {
                        _data.writeInt(1);
                        messageUri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (configOverrides != null) {
                        _data.writeInt(1);
                        configOverrides.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (sentIntent != null) {
                        _data.writeInt(1);
                        sentIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().sendStoredMessage(subId, callingPkg, messageUri, configOverrides, sentIntent);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setAutoPersisting(String callingPkg, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    _data.writeInt(enabled);
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setAutoPersisting(callingPkg, enabled);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getAutoPersisting() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAutoPersisting();
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
        }

        public static boolean setDefaultImpl(IMms impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IMms getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
