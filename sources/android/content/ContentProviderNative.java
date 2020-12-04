package android.content;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;

public abstract class ContentProviderNative extends Binder implements IContentProvider {
    public abstract String getProviderName();

    public ContentProviderNative() {
        attachInterface(this, IContentProvider.descriptor);
    }

    @UnsupportedAppUsage
    public static IContentProvider asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IContentProvider in = (IContentProvider) obj.queryLocalInterface(IContentProvider.descriptor);
        if (in != null) {
            return in;
        }
        return new ContentProviderProxy(obj);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:70:0x02a2, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x02a3, code lost:
        if (r4 != null) goto L_0x02a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x02a5, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x02a8, code lost:
        if (r1 != null) goto L_0x02aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x02aa, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x02ad, code lost:
        throw r0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:9:0x001f, B:62:0x027e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
        /*
            r16 = this;
            r7 = r16
            r8 = r17
            r9 = r18
            r10 = r19
            r0 = 10
            r11 = 1
            if (r8 == r0) goto L_0x02b7
            r0 = 0
            switch(r8) {
                case 1: goto L_0x0232;
                case 2: goto L_0x021a;
                case 3: goto L_0x01f6;
                case 4: goto L_0x01d2;
                default: goto L_0x0011;
            }
        L_0x0011:
            switch(r8) {
                case 13: goto L_0x01ae;
                case 14: goto L_0x0175;
                case 15: goto L_0x0143;
                default: goto L_0x0014;
            }
        L_0x0014:
            switch(r8) {
                case 20: goto L_0x0111;
                case 21: goto L_0x00eb;
                case 22: goto L_0x00cf;
                case 23: goto L_0x0096;
                case 24: goto L_0x0082;
                case 25: goto L_0x0066;
                case 26: goto L_0x004a;
                case 27: goto L_0x001d;
                default: goto L_0x0017;
            }
        L_0x0017:
            boolean r0 = super.onTransact(r17, r18, r19, r20)
            return r0
        L_0x001d:
            java.lang.String r1 = "android.content.IContentProvider"
            r9.enforceInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r1 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r2 = r2.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r2 = (android.net.Uri) r2     // Catch:{ Exception -> 0x02b5 }
            android.os.Bundle r3 = r18.readBundle()     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r4 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            android.os.ICancellationSignal r4 = android.os.ICancellationSignal.Stub.asInterface(r4)     // Catch:{ Exception -> 0x02b5 }
            boolean r5 = r7.refresh(r1, r2, r3, r4)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            if (r5 == 0) goto L_0x0045
            goto L_0x0046
        L_0x0045:
            r0 = -1
        L_0x0046:
            r10.writeInt(r0)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x004a:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r0 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r1 = (android.net.Uri) r1     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r2 = r7.uncanonicalize(r0, r1)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri.writeToParcel(r10, r2)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x0066:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r0 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r1 = (android.net.Uri) r1     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r2 = r7.canonicalize(r0, r1)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri.writeToParcel(r10, r2)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x0082:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            android.os.ICancellationSignal r0 = r16.createCancellationSignal()     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r1 = r0.asBinder()     // Catch:{ Exception -> 0x02b5 }
            r10.writeStrongBinder(r1)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x0096:
            java.lang.String r1 = "android.content.IContentProvider"
            r9.enforceInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            r3 = r1
            android.net.Uri r3 = (android.net.Uri) r3     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r4 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Bundle r5 = r18.readBundle()     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r1 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            android.os.ICancellationSignal r6 = android.os.ICancellationSignal.Stub.asInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            r1 = r16
            android.content.res.AssetFileDescriptor r1 = r1.openTypedAssetFile(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            if (r1 == 0) goto L_0x00cb
            r10.writeInt(r11)     // Catch:{ Exception -> 0x02b5 }
            r1.writeToParcel(r10, r11)     // Catch:{ Exception -> 0x02b5 }
            goto L_0x00ce
        L_0x00cb:
            r10.writeInt(r0)     // Catch:{ Exception -> 0x02b5 }
        L_0x00ce:
            return r11
        L_0x00cf:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r0 = r0.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r0 = (android.net.Uri) r0     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r1 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String[] r2 = r7.getStreamTypes(r0, r1)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeStringArray(r2)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x00eb:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r4 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r5 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Bundle r6 = r18.readBundle()     // Catch:{ Exception -> 0x02b5 }
            r1 = r16
            android.os.Bundle r0 = r1.call(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeBundle(r0)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x0111:
            java.lang.String r1 = "android.content.IContentProvider"
            r9.enforceInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r1 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            int r3 = r18.readInt()     // Catch:{ Exception -> 0x02b5 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x02b5 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x02b5 }
            r5 = r0
        L_0x0128:
            if (r5 >= r3) goto L_0x0138
            android.os.Parcelable$Creator<android.content.ContentProviderOperation> r6 = android.content.ContentProviderOperation.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r6 = r6.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.content.ContentProviderOperation r6 = (android.content.ContentProviderOperation) r6     // Catch:{ Exception -> 0x02b5 }
            r4.add(r5, r6)     // Catch:{ Exception -> 0x02b5 }
            int r5 = r5 + 1
            goto L_0x0128
        L_0x0138:
            android.content.ContentProviderResult[] r5 = r7.applyBatch(r1, r2, r4)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeTypedArray(r5, r0)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x0143:
            java.lang.String r1 = "android.content.IContentProvider"
            r9.enforceInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r1 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r2 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r2 = r2.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r2 = (android.net.Uri) r2     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r3 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r4 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            android.os.ICancellationSignal r4 = android.os.ICancellationSignal.Stub.asInterface(r4)     // Catch:{ Exception -> 0x02b5 }
            android.content.res.AssetFileDescriptor r5 = r7.openAssetFile(r1, r2, r3, r4)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            if (r5 == 0) goto L_0x0171
            r10.writeInt(r11)     // Catch:{ Exception -> 0x02b5 }
            r5.writeToParcel(r10, r11)     // Catch:{ Exception -> 0x02b5 }
            goto L_0x0174
        L_0x0171:
            r10.writeInt(r0)     // Catch:{ Exception -> 0x02b5 }
        L_0x0174:
            return r11
        L_0x0175:
            java.lang.String r1 = "android.content.IContentProvider"
            r9.enforceInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            r3 = r1
            android.net.Uri r3 = (android.net.Uri) r3     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r4 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r1 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            android.os.ICancellationSignal r5 = android.os.ICancellationSignal.Stub.asInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r6 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            r1 = r16
            android.os.ParcelFileDescriptor r1 = r1.openFile(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            if (r1 == 0) goto L_0x01aa
            r10.writeInt(r11)     // Catch:{ Exception -> 0x02b5 }
            r1.writeToParcel(r10, r11)     // Catch:{ Exception -> 0x02b5 }
            goto L_0x01ad
        L_0x01aa:
            r10.writeInt(r0)     // Catch:{ Exception -> 0x02b5 }
        L_0x01ad:
            return r11
        L_0x01ae:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r0 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r1 = (android.net.Uri) r1     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.content.ContentValues> r2 = android.content.ContentValues.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object[] r2 = r9.createTypedArray(r2)     // Catch:{ Exception -> 0x02b5 }
            android.content.ContentValues[] r2 = (android.content.ContentValues[]) r2     // Catch:{ Exception -> 0x02b5 }
            int r3 = r7.bulkInsert(r0, r1, r2)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeInt(r3)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x01d2:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r0 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r1 = (android.net.Uri) r1     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String[] r3 = r18.readStringArray()     // Catch:{ Exception -> 0x02b5 }
            int r4 = r7.delete(r0, r1, r2, r3)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeInt(r4)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x01f6:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r0 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r1 = (android.net.Uri) r1     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.content.ContentValues> r2 = android.content.ContentValues.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r2 = r2.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.content.ContentValues r2 = (android.content.ContentValues) r2     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r3 = r7.insert(r0, r1, r2)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri.writeToParcel(r10, r3)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x021a:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r0 = r0.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            android.net.Uri r0 = (android.net.Uri) r0     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r1 = r7.getType(r0)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeString(r1)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x0232:
            java.lang.String r1 = "android.content.IContentProvider"
            r9.enforceInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r1 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r1 = r1.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            r3 = r1
            android.net.Uri r3 = (android.net.Uri) r3     // Catch:{ Exception -> 0x02b5 }
            int r1 = r18.readInt()     // Catch:{ Exception -> 0x02b5 }
            r12 = r1
            r1 = 0
            if (r12 <= 0) goto L_0x025b
            java.lang.String[] r4 = new java.lang.String[r12]     // Catch:{ Exception -> 0x02b5 }
            r1 = r4
            r4 = r0
        L_0x0250:
            if (r4 >= r12) goto L_0x025b
            java.lang.String r5 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            r1[r4] = r5     // Catch:{ Exception -> 0x02b5 }
            int r4 = r4 + 1
            goto L_0x0250
        L_0x025b:
            r13 = r1
            android.os.Bundle r5 = r18.readBundle()     // Catch:{ Exception -> 0x02b5 }
            android.os.IBinder r1 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            android.database.IContentObserver r1 = android.database.IContentObserver.Stub.asInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            r14 = r1
            android.os.IBinder r1 = r18.readStrongBinder()     // Catch:{ Exception -> 0x02b5 }
            android.os.ICancellationSignal r6 = android.os.ICancellationSignal.Stub.asInterface(r1)     // Catch:{ Exception -> 0x02b5 }
            r1 = r16
            r4 = r13
            android.database.Cursor r1 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x02b5 }
            if (r1 == 0) goto L_0x02ae
            r0 = 0
            r4 = r0
            android.database.CursorToBulkCursorAdaptor r0 = new android.database.CursorToBulkCursorAdaptor     // Catch:{ all -> 0x02a2 }
            java.lang.String r15 = r16.getProviderName()     // Catch:{ all -> 0x02a2 }
            r0.<init>(r1, r14, r15)     // Catch:{ all -> 0x02a2 }
            r4 = r0
            r1 = 0
            android.database.BulkCursorDescriptor r0 = r4.getBulkCursorDescriptor()     // Catch:{ all -> 0x02a2 }
            r4 = 0
            r19.writeNoException()     // Catch:{ all -> 0x02a2 }
            r10.writeInt(r11)     // Catch:{ all -> 0x02a2 }
            r0.writeToParcel(r10, r11)     // Catch:{ all -> 0x02a2 }
            if (r4 == 0) goto L_0x029c
            r4.close()     // Catch:{ Exception -> 0x02b5 }
        L_0x029c:
            if (r1 == 0) goto L_0x02a1
            r1.close()     // Catch:{ Exception -> 0x02b5 }
        L_0x02a1:
            goto L_0x02b4
        L_0x02a2:
            r0 = move-exception
            if (r4 == 0) goto L_0x02a8
            r4.close()     // Catch:{ Exception -> 0x02b5 }
        L_0x02a8:
            if (r1 == 0) goto L_0x02ad
            r1.close()     // Catch:{ Exception -> 0x02b5 }
        L_0x02ad:
            throw r0     // Catch:{ Exception -> 0x02b5 }
        L_0x02ae:
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeInt(r0)     // Catch:{ Exception -> 0x02b5 }
        L_0x02b4:
            return r11
        L_0x02b5:
            r0 = move-exception
            goto L_0x02e7
        L_0x02b7:
            java.lang.String r0 = "android.content.IContentProvider"
            r9.enforceInterface(r0)     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r2 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r0 = r0.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            r3 = r0
            android.net.Uri r3 = (android.net.Uri) r3     // Catch:{ Exception -> 0x02b5 }
            android.os.Parcelable$Creator<android.content.ContentValues> r0 = android.content.ContentValues.CREATOR     // Catch:{ Exception -> 0x02b5 }
            java.lang.Object r0 = r0.createFromParcel(r9)     // Catch:{ Exception -> 0x02b5 }
            r4 = r0
            android.content.ContentValues r4 = (android.content.ContentValues) r4     // Catch:{ Exception -> 0x02b5 }
            java.lang.String r5 = r18.readString()     // Catch:{ Exception -> 0x02b5 }
            java.lang.String[] r6 = r18.readStringArray()     // Catch:{ Exception -> 0x02b5 }
            r1 = r16
            int r0 = r1.update(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x02b5 }
            r19.writeNoException()     // Catch:{ Exception -> 0x02b5 }
            r10.writeInt(r0)     // Catch:{ Exception -> 0x02b5 }
            return r11
        L_0x02e7:
            android.database.DatabaseUtils.writeExceptionToParcel(r10, r0)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.ContentProviderNative.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }

    public IBinder asBinder() {
        return this;
    }
}
