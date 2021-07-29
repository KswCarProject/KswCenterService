package com.android.internal.appwidget;

import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

public interface IAppWidgetService extends IInterface {
    int allocateAppWidgetId(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle) throws RemoteException;

    @UnsupportedAppUsage
    boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, int i2) throws RemoteException;

    IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException;

    void deleteAllHosts() throws RemoteException;

    void deleteAppWidgetId(String str, int i) throws RemoteException;

    void deleteHost(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    int[] getAppWidgetIds(ComponentName componentName) throws RemoteException;

    int[] getAppWidgetIdsForHost(String str, int i) throws RemoteException;

    AppWidgetProviderInfo getAppWidgetInfo(String str, int i) throws RemoteException;

    Bundle getAppWidgetOptions(String str, int i) throws RemoteException;

    @UnsupportedAppUsage
    RemoteViews getAppWidgetViews(String str, int i) throws RemoteException;

    ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str) throws RemoteException;

    boolean hasBindAppWidgetPermission(String str, int i) throws RemoteException;

    boolean isBoundWidgetPackage(String str, int i) throws RemoteException;

    boolean isRequestPinAppWidgetSupported() throws RemoteException;

    void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) throws RemoteException;

    void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException;

    boolean requestPinAppWidget(String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) throws RemoteException;

    void setBindAppWidgetPermission(String str, int i, boolean z) throws RemoteException;

    ParceledListSlice startListening(IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr) throws RemoteException;

    void stopListening(String str, int i) throws RemoteException;

    void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetOptions(String str, int i, Bundle bundle) throws RemoteException;

    void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetProviderInfo(ComponentName componentName, String str) throws RemoteException;

    public static class Default implements IAppWidgetService {
        public ParceledListSlice startListening(IAppWidgetHost host, String callingPackage, int hostId, int[] appWidgetIds) throws RemoteException {
            return null;
        }

        public void stopListening(String callingPackage, int hostId) throws RemoteException {
        }

        public int allocateAppWidgetId(String callingPackage, int hostId) throws RemoteException {
            return 0;
        }

        public void deleteAppWidgetId(String callingPackage, int appWidgetId) throws RemoteException {
        }

        public void deleteHost(String packageName, int hostId) throws RemoteException {
        }

        public void deleteAllHosts() throws RemoteException {
        }

        public RemoteViews getAppWidgetViews(String callingPackage, int appWidgetId) throws RemoteException {
            return null;
        }

        public int[] getAppWidgetIdsForHost(String callingPackage, int hostId) throws RemoteException {
            return null;
        }

        public IntentSender createAppWidgetConfigIntentSender(String callingPackage, int appWidgetId, int intentFlags) throws RemoteException {
            return null;
        }

        public void updateAppWidgetIds(String callingPackage, int[] appWidgetIds, RemoteViews views) throws RemoteException {
        }

        public void updateAppWidgetOptions(String callingPackage, int appWidgetId, Bundle extras) throws RemoteException {
        }

        public Bundle getAppWidgetOptions(String callingPackage, int appWidgetId) throws RemoteException {
            return null;
        }

        public void partiallyUpdateAppWidgetIds(String callingPackage, int[] appWidgetIds, RemoteViews views) throws RemoteException {
        }

        public void updateAppWidgetProvider(ComponentName provider, RemoteViews views) throws RemoteException {
        }

        public void updateAppWidgetProviderInfo(ComponentName provider, String metadataKey) throws RemoteException {
        }

        public void notifyAppWidgetViewDataChanged(String packageName, int[] appWidgetIds, int viewId) throws RemoteException {
        }

        public ParceledListSlice getInstalledProvidersForProfile(int categoryFilter, int profileId, String packageName) throws RemoteException {
            return null;
        }

        public AppWidgetProviderInfo getAppWidgetInfo(String callingPackage, int appWidgetId) throws RemoteException {
            return null;
        }

        public boolean hasBindAppWidgetPermission(String packageName, int userId) throws RemoteException {
            return false;
        }

        public void setBindAppWidgetPermission(String packageName, int userId, boolean permission) throws RemoteException {
        }

        public boolean bindAppWidgetId(String callingPackage, int appWidgetId, int providerProfileId, ComponentName providerComponent, Bundle options) throws RemoteException {
            return false;
        }

        public boolean bindRemoteViewsService(String callingPackage, int appWidgetId, Intent intent, IApplicationThread caller, IBinder token, IServiceConnection connection, int flags) throws RemoteException {
            return false;
        }

        public int[] getAppWidgetIds(ComponentName providerComponent) throws RemoteException {
            return null;
        }

        public boolean isBoundWidgetPackage(String packageName, int userId) throws RemoteException {
            return false;
        }

        public boolean requestPinAppWidget(String packageName, ComponentName providerComponent, Bundle extras, IntentSender resultIntent) throws RemoteException {
            return false;
        }

        public boolean isRequestPinAppWidgetSupported() throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppWidgetService {
        private static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetService";
        static final int TRANSACTION_allocateAppWidgetId = 3;
        static final int TRANSACTION_bindAppWidgetId = 21;
        static final int TRANSACTION_bindRemoteViewsService = 22;
        static final int TRANSACTION_createAppWidgetConfigIntentSender = 9;
        static final int TRANSACTION_deleteAllHosts = 6;
        static final int TRANSACTION_deleteAppWidgetId = 4;
        static final int TRANSACTION_deleteHost = 5;
        static final int TRANSACTION_getAppWidgetIds = 23;
        static final int TRANSACTION_getAppWidgetIdsForHost = 8;
        static final int TRANSACTION_getAppWidgetInfo = 18;
        static final int TRANSACTION_getAppWidgetOptions = 12;
        static final int TRANSACTION_getAppWidgetViews = 7;
        static final int TRANSACTION_getInstalledProvidersForProfile = 17;
        static final int TRANSACTION_hasBindAppWidgetPermission = 19;
        static final int TRANSACTION_isBoundWidgetPackage = 24;
        static final int TRANSACTION_isRequestPinAppWidgetSupported = 26;
        static final int TRANSACTION_notifyAppWidgetViewDataChanged = 16;
        static final int TRANSACTION_partiallyUpdateAppWidgetIds = 13;
        static final int TRANSACTION_requestPinAppWidget = 25;
        static final int TRANSACTION_setBindAppWidgetPermission = 20;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_updateAppWidgetIds = 10;
        static final int TRANSACTION_updateAppWidgetOptions = 11;
        static final int TRANSACTION_updateAppWidgetProvider = 14;
        static final int TRANSACTION_updateAppWidgetProviderInfo = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IAppWidgetService)) {
                return new Proxy(obj);
            }
            return (IAppWidgetService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "startListening";
                case 2:
                    return "stopListening";
                case 3:
                    return "allocateAppWidgetId";
                case 4:
                    return "deleteAppWidgetId";
                case 5:
                    return "deleteHost";
                case 6:
                    return "deleteAllHosts";
                case 7:
                    return "getAppWidgetViews";
                case 8:
                    return "getAppWidgetIdsForHost";
                case 9:
                    return "createAppWidgetConfigIntentSender";
                case 10:
                    return "updateAppWidgetIds";
                case 11:
                    return "updateAppWidgetOptions";
                case 12:
                    return "getAppWidgetOptions";
                case 13:
                    return "partiallyUpdateAppWidgetIds";
                case 14:
                    return "updateAppWidgetProvider";
                case 15:
                    return "updateAppWidgetProviderInfo";
                case 16:
                    return "notifyAppWidgetViewDataChanged";
                case 17:
                    return "getInstalledProvidersForProfile";
                case 18:
                    return "getAppWidgetInfo";
                case 19:
                    return "hasBindAppWidgetPermission";
                case 20:
                    return "setBindAppWidgetPermission";
                case 21:
                    return "bindAppWidgetId";
                case 22:
                    return "bindRemoteViewsService";
                case 23:
                    return "getAppWidgetIds";
                case 24:
                    return "isBoundWidgetPackage";
                case 25:
                    return "requestPinAppWidget";
                case 26:
                    return "isRequestPinAppWidgetSupported";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v43, resolved type: android.content.IntentSender} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v10, types: [android.widget.RemoteViews] */
        /* JADX WARNING: type inference failed for: r1v19, types: [android.widget.RemoteViews] */
        /* JADX WARNING: type inference failed for: r1v23, types: [android.widget.RemoteViews] */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v38 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r21, android.os.Parcel r22, android.os.Parcel r23, int r24) throws android.os.RemoteException {
            /*
                r20 = this;
                r8 = r20
                r9 = r21
                r10 = r22
                r11 = r23
                java.lang.String r12 = "com.android.internal.appwidget.IAppWidgetService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x035c
                r0 = 0
                r1 = 0
                switch(r9) {
                    case 1: goto L_0x0331;
                    case 2: goto L_0x031f;
                    case 3: goto L_0x0309;
                    case 4: goto L_0x02f7;
                    case 5: goto L_0x02e5;
                    case 6: goto L_0x02db;
                    case 7: goto L_0x02bc;
                    case 8: goto L_0x02a6;
                    case 9: goto L_0x0283;
                    case 10: goto L_0x0261;
                    case 11: goto L_0x023f;
                    case 12: goto L_0x0220;
                    case 13: goto L_0x01fe;
                    case 14: goto L_0x01d4;
                    case 15: goto L_0x01b4;
                    case 16: goto L_0x019e;
                    case 17: goto L_0x017b;
                    case 18: goto L_0x015c;
                    case 19: goto L_0x0146;
                    case 20: goto L_0x012c;
                    case 21: goto L_0x00eb;
                    case 22: goto L_0x00a0;
                    case 23: goto L_0x0080;
                    case 24: goto L_0x006a;
                    case 25: goto L_0x0028;
                    case 26: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r21, r22, r23, r24)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                boolean r0 = r20.isRequestPinAppWidgetSupported()
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0028:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x003e
                android.os.Parcelable$Creator<android.content.ComponentName> r2 = android.content.ComponentName.CREATOR
                java.lang.Object r2 = r2.createFromParcel(r10)
                android.content.ComponentName r2 = (android.content.ComponentName) r2
                goto L_0x003f
            L_0x003e:
                r2 = r1
            L_0x003f:
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x004e
                android.os.Parcelable$Creator<android.os.Bundle> r3 = android.os.Bundle.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r10)
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x004f
            L_0x004e:
                r3 = r1
            L_0x004f:
                int r4 = r22.readInt()
                if (r4 == 0) goto L_0x005e
                android.os.Parcelable$Creator<android.content.IntentSender> r1 = android.content.IntentSender.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.IntentSender r1 = (android.content.IntentSender) r1
                goto L_0x005f
            L_0x005e:
            L_0x005f:
                boolean r4 = r8.requestPinAppWidget(r0, r2, r3, r1)
                r23.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x006a:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.isBoundWidgetPackage(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0080:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x0093
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x0094
            L_0x0093:
            L_0x0094:
                r0 = r1
                int[] r1 = r8.getAppWidgetIds(r0)
                r23.writeNoException()
                r11.writeIntArray(r1)
                return r13
            L_0x00a0:
                r10.enforceInterface(r12)
                java.lang.String r14 = r22.readString()
                int r15 = r22.readInt()
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x00bb
                android.os.Parcelable$Creator<android.content.Intent> r0 = android.content.Intent.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.Intent r0 = (android.content.Intent) r0
                r3 = r0
                goto L_0x00bc
            L_0x00bb:
                r3 = r1
            L_0x00bc:
                android.os.IBinder r0 = r22.readStrongBinder()
                android.app.IApplicationThread r16 = android.app.IApplicationThread.Stub.asInterface(r0)
                android.os.IBinder r17 = r22.readStrongBinder()
                android.os.IBinder r0 = r22.readStrongBinder()
                android.app.IServiceConnection r18 = android.app.IServiceConnection.Stub.asInterface(r0)
                int r19 = r22.readInt()
                r0 = r20
                r1 = r14
                r2 = r15
                r4 = r16
                r5 = r17
                r6 = r18
                r7 = r19
                boolean r0 = r0.bindRemoteViewsService(r1, r2, r3, r4, r5, r6, r7)
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x00eb:
                r10.enforceInterface(r12)
                java.lang.String r6 = r22.readString()
                int r7 = r22.readInt()
                int r14 = r22.readInt()
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x010a
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                r4 = r0
                goto L_0x010b
            L_0x010a:
                r4 = r1
            L_0x010b:
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x011b
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r5 = r0
                goto L_0x011c
            L_0x011b:
                r5 = r1
            L_0x011c:
                r0 = r20
                r1 = r6
                r2 = r7
                r3 = r14
                boolean r0 = r0.bindAppWidgetId(r1, r2, r3, r4, r5)
                r23.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x012c:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x013f
                r0 = r13
            L_0x013f:
                r8.setBindAppWidgetPermission(r1, r2, r0)
                r23.writeNoException()
                return r13
            L_0x0146:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                boolean r2 = r8.hasBindAppWidgetPermission(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x015c:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                android.appwidget.AppWidgetProviderInfo r3 = r8.getAppWidgetInfo(r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x0177
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x017a
            L_0x0177:
                r11.writeInt(r0)
            L_0x017a:
                return r13
            L_0x017b:
                r10.enforceInterface(r12)
                int r1 = r22.readInt()
                int r2 = r22.readInt()
                java.lang.String r3 = r22.readString()
                android.content.pm.ParceledListSlice r4 = r8.getInstalledProvidersForProfile(r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x019a
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x019d
            L_0x019a:
                r11.writeInt(r0)
            L_0x019d:
                return r13
            L_0x019e:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int[] r1 = r22.createIntArray()
                int r2 = r22.readInt()
                r8.notifyAppWidgetViewDataChanged(r0, r1, r2)
                r23.writeNoException()
                return r13
            L_0x01b4:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x01c7
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.content.ComponentName r1 = (android.content.ComponentName) r1
                goto L_0x01c8
            L_0x01c7:
            L_0x01c8:
                r0 = r1
                java.lang.String r1 = r22.readString()
                r8.updateAppWidgetProviderInfo(r0, r1)
                r23.writeNoException()
                return r13
            L_0x01d4:
                r10.enforceInterface(r12)
                int r0 = r22.readInt()
                if (r0 == 0) goto L_0x01e6
                android.os.Parcelable$Creator<android.content.ComponentName> r0 = android.content.ComponentName.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.content.ComponentName r0 = (android.content.ComponentName) r0
                goto L_0x01e7
            L_0x01e6:
                r0 = r1
            L_0x01e7:
                int r2 = r22.readInt()
                if (r2 == 0) goto L_0x01f6
                android.os.Parcelable$Creator<android.widget.RemoteViews> r1 = android.widget.RemoteViews.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.widget.RemoteViews r1 = (android.widget.RemoteViews) r1
                goto L_0x01f7
            L_0x01f6:
            L_0x01f7:
                r8.updateAppWidgetProvider(r0, r1)
                r23.writeNoException()
                return r13
            L_0x01fe:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int[] r2 = r22.createIntArray()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0218
                android.os.Parcelable$Creator<android.widget.RemoteViews> r1 = android.widget.RemoteViews.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.widget.RemoteViews r1 = (android.widget.RemoteViews) r1
                goto L_0x0219
            L_0x0218:
            L_0x0219:
                r8.partiallyUpdateAppWidgetIds(r0, r2, r1)
                r23.writeNoException()
                return r13
            L_0x0220:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                android.os.Bundle r3 = r8.getAppWidgetOptions(r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x023b
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x023e
            L_0x023b:
                r11.writeInt(r0)
            L_0x023e:
                return r13
            L_0x023f:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x0259
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x025a
            L_0x0259:
            L_0x025a:
                r8.updateAppWidgetOptions(r0, r2, r1)
                r23.writeNoException()
                return r13
            L_0x0261:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int[] r2 = r22.createIntArray()
                int r3 = r22.readInt()
                if (r3 == 0) goto L_0x027b
                android.os.Parcelable$Creator<android.widget.RemoteViews> r1 = android.widget.RemoteViews.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.widget.RemoteViews r1 = (android.widget.RemoteViews) r1
                goto L_0x027c
            L_0x027b:
            L_0x027c:
                r8.updateAppWidgetIds(r0, r2, r1)
                r23.writeNoException()
                return r13
            L_0x0283:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                int r3 = r22.readInt()
                android.content.IntentSender r4 = r8.createAppWidgetConfigIntentSender(r1, r2, r3)
                r23.writeNoException()
                if (r4 == 0) goto L_0x02a2
                r11.writeInt(r13)
                r4.writeToParcel(r11, r13)
                goto L_0x02a5
            L_0x02a2:
                r11.writeInt(r0)
            L_0x02a5:
                return r13
            L_0x02a6:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int[] r2 = r8.getAppWidgetIdsForHost(r0, r1)
                r23.writeNoException()
                r11.writeIntArray(r2)
                return r13
            L_0x02bc:
                r10.enforceInterface(r12)
                java.lang.String r1 = r22.readString()
                int r2 = r22.readInt()
                android.widget.RemoteViews r3 = r8.getAppWidgetViews(r1, r2)
                r23.writeNoException()
                if (r3 == 0) goto L_0x02d7
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x02da
            L_0x02d7:
                r11.writeInt(r0)
            L_0x02da:
                return r13
            L_0x02db:
                r10.enforceInterface(r12)
                r20.deleteAllHosts()
                r23.writeNoException()
                return r13
            L_0x02e5:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.deleteHost(r0, r1)
                r23.writeNoException()
                return r13
            L_0x02f7:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.deleteAppWidgetId(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0309:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                int r2 = r8.allocateAppWidgetId(r0, r1)
                r23.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x031f:
                r10.enforceInterface(r12)
                java.lang.String r0 = r22.readString()
                int r1 = r22.readInt()
                r8.stopListening(r0, r1)
                r23.writeNoException()
                return r13
            L_0x0331:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r22.readStrongBinder()
                com.android.internal.appwidget.IAppWidgetHost r1 = com.android.internal.appwidget.IAppWidgetHost.Stub.asInterface(r1)
                java.lang.String r2 = r22.readString()
                int r3 = r22.readInt()
                int[] r4 = r22.createIntArray()
                android.content.pm.ParceledListSlice r5 = r8.startListening(r1, r2, r3, r4)
                r23.writeNoException()
                if (r5 == 0) goto L_0x0358
                r11.writeInt(r13)
                r5.writeToParcel(r11, r13)
                goto L_0x035b
            L_0x0358:
                r11.writeInt(r0)
            L_0x035b:
                return r13
            L_0x035c:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.appwidget.IAppWidgetService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAppWidgetService {
            public static IAppWidgetService sDefaultImpl;
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

            public ParceledListSlice startListening(IAppWidgetHost host, String callingPackage, int hostId, int[] appWidgetIds) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    ParceledListSlice _result = null;
                    _data.writeStrongBinder(host != null ? host.asBinder() : null);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    _data.writeIntArray(appWidgetIds);
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startListening(host, callingPackage, hostId, appWidgetIds);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopListening(String callingPackage, int hostId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    if (this.mRemote.transact(2, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopListening(callingPackage, hostId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int allocateAppWidgetId(String callingPackage, int hostId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().allocateAppWidgetId(callingPackage, hostId);
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

            public void deleteAppWidgetId(String callingPackage, int appWidgetId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteAppWidgetId(callingPackage, appWidgetId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteHost(String packageName, int hostId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(hostId);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteHost(packageName, hostId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void deleteAllHosts() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(6, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().deleteAllHosts();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public RemoteViews getAppWidgetViews(String callingPackage, int appWidgetId) throws RemoteException {
                RemoteViews _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppWidgetViews(callingPackage, appWidgetId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = RemoteViews.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    RemoteViews _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] getAppWidgetIdsForHost(String callingPackage, int hostId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppWidgetIdsForHost(callingPackage, hostId);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IntentSender createAppWidgetConfigIntentSender(String callingPackage, int appWidgetId, int intentFlags) throws RemoteException {
                IntentSender _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(intentFlags);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().createAppWidgetConfigIntentSender(callingPackage, appWidgetId, intentFlags);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = IntentSender.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    IntentSender _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateAppWidgetIds(String callingPackage, int[] appWidgetIds, RemoteViews views) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeIntArray(appWidgetIds);
                    if (views != null) {
                        _data.writeInt(1);
                        views.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateAppWidgetIds(callingPackage, appWidgetIds, views);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateAppWidgetOptions(String callingPackage, int appWidgetId, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(11, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateAppWidgetOptions(callingPackage, appWidgetId, extras);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getAppWidgetOptions(String callingPackage, int appWidgetId) throws RemoteException {
                Bundle _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppWidgetOptions(callingPackage, appWidgetId);
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

            public void partiallyUpdateAppWidgetIds(String callingPackage, int[] appWidgetIds, RemoteViews views) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeIntArray(appWidgetIds);
                    if (views != null) {
                        _data.writeInt(1);
                        views.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(13, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().partiallyUpdateAppWidgetIds(callingPackage, appWidgetIds, views);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateAppWidgetProvider(ComponentName provider, RemoteViews views) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provider != null) {
                        _data.writeInt(1);
                        provider.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (views != null) {
                        _data.writeInt(1);
                        views.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateAppWidgetProvider(provider, views);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateAppWidgetProviderInfo(ComponentName provider, String metadataKey) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (provider != null) {
                        _data.writeInt(1);
                        provider.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(metadataKey);
                    if (this.mRemote.transact(15, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().updateAppWidgetProviderInfo(provider, metadataKey);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyAppWidgetViewDataChanged(String packageName, int[] appWidgetIds, int viewId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeIntArray(appWidgetIds);
                    _data.writeInt(viewId);
                    if (this.mRemote.transact(16, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyAppWidgetViewDataChanged(packageName, appWidgetIds, viewId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getInstalledProvidersForProfile(int categoryFilter, int profileId, String packageName) throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(categoryFilter);
                    _data.writeInt(profileId);
                    _data.writeString(packageName);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getInstalledProvidersForProfile(categoryFilter, profileId, packageName);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AppWidgetProviderInfo getAppWidgetInfo(String callingPackage, int appWidgetId) throws RemoteException {
                AppWidgetProviderInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppWidgetInfo(callingPackage, appWidgetId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AppWidgetProviderInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    AppWidgetProviderInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasBindAppWidgetPermission(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasBindAppWidgetPermission(packageName, userId);
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

            public void setBindAppWidgetPermission(String packageName, int userId, boolean permission) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(permission);
                    if (this.mRemote.transact(20, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBindAppWidgetPermission(packageName, userId, permission);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean bindAppWidgetId(String callingPackage, int appWidgetId, int providerProfileId, ComponentName providerComponent, Bundle options) throws RemoteException {
                boolean _result;
                ComponentName componentName = providerComponent;
                Bundle bundle = options;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPackage);
                    } catch (Throwable th) {
                        th = th;
                        int i = appWidgetId;
                        int i2 = providerProfileId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(appWidgetId);
                        try {
                            _data.writeInt(providerProfileId);
                            _result = true;
                            if (componentName != null) {
                                _data.writeInt(1);
                                componentName.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            if (this.mRemote.transact(21, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean bindAppWidgetId = Stub.getDefaultImpl().bindAppWidgetId(callingPackage, appWidgetId, providerProfileId, providerComponent, options);
                            _reply.recycle();
                            _data.recycle();
                            return bindAppWidgetId;
                        } catch (Throwable th3) {
                            th = th3;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        int i22 = providerProfileId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    String str = callingPackage;
                    int i3 = appWidgetId;
                    int i222 = providerProfileId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public boolean bindRemoteViewsService(String callingPackage, int appWidgetId, Intent intent, IApplicationThread caller, IBinder token, IServiceConnection connection, int flags) throws RemoteException {
                boolean _result;
                IBinder iBinder;
                Intent intent2 = intent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeString(callingPackage);
                        try {
                            _data.writeInt(appWidgetId);
                            _result = true;
                            if (intent2 != null) {
                                _data.writeInt(1);
                                intent2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            iBinder = null;
                            _data.writeStrongBinder(caller != null ? caller.asBinder() : null);
                        } catch (Throwable th) {
                            th = th;
                            IBinder iBinder2 = token;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                        try {
                            _data.writeStrongBinder(token);
                            if (connection != null) {
                                iBinder = connection.asBinder();
                            }
                            _data.writeStrongBinder(iBinder);
                            _data.writeInt(flags);
                            if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                if (_reply.readInt() == 0) {
                                    _result = false;
                                }
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            boolean bindRemoteViewsService = Stub.getDefaultImpl().bindRemoteViewsService(callingPackage, appWidgetId, intent, caller, token, connection, flags);
                            _reply.recycle();
                            _data.recycle();
                            return bindRemoteViewsService;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        int i = appWidgetId;
                        IBinder iBinder22 = token;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    String str = callingPackage;
                    int i2 = appWidgetId;
                    IBinder iBinder222 = token;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public int[] getAppWidgetIds(ComponentName providerComponent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (providerComponent != null) {
                        _data.writeInt(1);
                        providerComponent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAppWidgetIds(providerComponent);
                    }
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBoundWidgetPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBoundWidgetPackage(packageName, userId);
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

            public boolean requestPinAppWidget(String packageName, ComponentName providerComponent, Bundle extras, IntentSender resultIntent) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    boolean _result = true;
                    if (providerComponent != null) {
                        _data.writeInt(1);
                        providerComponent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (extras != null) {
                        _data.writeInt(1);
                        extras.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (resultIntent != null) {
                        _data.writeInt(1);
                        resultIntent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().requestPinAppWidget(packageName, providerComponent, extras, resultIntent);
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

            public boolean isRequestPinAppWidgetSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isRequestPinAppWidgetSupported();
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

        public static boolean setDefaultImpl(IAppWidgetService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAppWidgetService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
