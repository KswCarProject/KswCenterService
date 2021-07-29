package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Intent;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.voice.IVoiceInteractionService;
import android.service.voice.IVoiceInteractionSession;
import java.util.List;

public interface IVoiceInteractionManagerService extends IInterface {
    boolean activeServiceSupportsAssist() throws RemoteException;

    boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException;

    void closeSystemDialogs(IBinder iBinder) throws RemoteException;

    int deleteKeyphraseSoundModel(int i, String str) throws RemoteException;

    boolean deliverNewSession(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) throws RemoteException;

    void finish(IBinder iBinder) throws RemoteException;

    ComponentName getActiveServiceComponentName() throws RemoteException;

    void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback iVoiceActionCheckCallback) throws RemoteException;

    int getDisabledShowContext() throws RemoteException;

    SoundTrigger.ModuleProperties getDspModuleProperties(IVoiceInteractionService iVoiceInteractionService) throws RemoteException;

    @UnsupportedAppUsage
    SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str) throws RemoteException;

    int getUserDisabledShowContext() throws RemoteException;

    void hideCurrentSession() throws RemoteException;

    boolean hideSessionFromSession(IBinder iBinder) throws RemoteException;

    boolean isEnrolledForKeyphrase(IVoiceInteractionService iVoiceInteractionService, int i, String str) throws RemoteException;

    boolean isSessionRunning() throws RemoteException;

    void launchVoiceAssistFromKeyguard() throws RemoteException;

    void onLockscreenShown() throws RemoteException;

    void performDirectAction(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) throws RemoteException;

    void requestDirectActions(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void setDisabledShowContext(int i) throws RemoteException;

    void setKeepAwake(IBinder iBinder, boolean z) throws RemoteException;

    void setUiHints(IVoiceInteractionService iVoiceInteractionService, Bundle bundle) throws RemoteException;

    void showSession(IVoiceInteractionService iVoiceInteractionService, Bundle bundle, int i) throws RemoteException;

    boolean showSessionForActiveService(Bundle bundle, int i, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) throws RemoteException;

    boolean showSessionFromSession(IBinder iBinder, Bundle bundle, int i) throws RemoteException;

    int startAssistantActivity(IBinder iBinder, Intent intent, String str) throws RemoteException;

    int startRecognition(IVoiceInteractionService iVoiceInteractionService, int i, String str, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException;

    int startVoiceActivity(IBinder iBinder, Intent intent, String str) throws RemoteException;

    int stopRecognition(IVoiceInteractionService iVoiceInteractionService, int i, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException;

    int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException;

    public static class Default implements IVoiceInteractionManagerService {
        public void showSession(IVoiceInteractionService service, Bundle sessionArgs, int flags) throws RemoteException {
        }

        public boolean deliverNewSession(IBinder token, IVoiceInteractionSession session, IVoiceInteractor interactor) throws RemoteException {
            return false;
        }

        public boolean showSessionFromSession(IBinder token, Bundle sessionArgs, int flags) throws RemoteException {
            return false;
        }

        public boolean hideSessionFromSession(IBinder token) throws RemoteException {
            return false;
        }

        public int startVoiceActivity(IBinder token, Intent intent, String resolvedType) throws RemoteException {
            return 0;
        }

        public int startAssistantActivity(IBinder token, Intent intent, String resolvedType) throws RemoteException {
            return 0;
        }

        public void setKeepAwake(IBinder token, boolean keepAwake) throws RemoteException {
        }

        public void closeSystemDialogs(IBinder token) throws RemoteException {
        }

        public void finish(IBinder token) throws RemoteException {
        }

        public void setDisabledShowContext(int flags) throws RemoteException {
        }

        public int getDisabledShowContext() throws RemoteException {
            return 0;
        }

        public int getUserDisabledShowContext() throws RemoteException {
            return 0;
        }

        public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int keyphraseId, String bcp47Locale) throws RemoteException {
            return null;
        }

        public int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel model) throws RemoteException {
            return 0;
        }

        public int deleteKeyphraseSoundModel(int keyphraseId, String bcp47Locale) throws RemoteException {
            return 0;
        }

        public SoundTrigger.ModuleProperties getDspModuleProperties(IVoiceInteractionService service) throws RemoteException {
            return null;
        }

        public boolean isEnrolledForKeyphrase(IVoiceInteractionService service, int keyphraseId, String bcp47Locale) throws RemoteException {
            return false;
        }

        public int startRecognition(IVoiceInteractionService service, int keyphraseId, String bcp47Locale, IRecognitionStatusCallback callback, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
            return 0;
        }

        public int stopRecognition(IVoiceInteractionService service, int keyphraseId, IRecognitionStatusCallback callback) throws RemoteException {
            return 0;
        }

        public ComponentName getActiveServiceComponentName() throws RemoteException {
            return null;
        }

        public boolean showSessionForActiveService(Bundle args, int sourceFlags, IVoiceInteractionSessionShowCallback showCallback, IBinder activityToken) throws RemoteException {
            return false;
        }

        public void hideCurrentSession() throws RemoteException {
        }

        public void launchVoiceAssistFromKeyguard() throws RemoteException {
        }

        public boolean isSessionRunning() throws RemoteException {
            return false;
        }

        public boolean activeServiceSupportsAssist() throws RemoteException {
            return false;
        }

        public boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException {
            return false;
        }

        public void onLockscreenShown() throws RemoteException {
        }

        public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener listener) throws RemoteException {
        }

        public void getActiveServiceSupportedActions(List<String> list, IVoiceActionCheckCallback callback) throws RemoteException {
        }

        public void setUiHints(IVoiceInteractionService service, Bundle hints) throws RemoteException {
        }

        public void requestDirectActions(IBinder token, int taskId, IBinder assistToken, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
        }

        public void performDirectAction(IBinder token, String actionId, Bundle arguments, int taskId, IBinder assistToken, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IVoiceInteractionManagerService {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionManagerService";
        static final int TRANSACTION_activeServiceSupportsAssist = 25;
        static final int TRANSACTION_activeServiceSupportsLaunchFromKeyguard = 26;
        static final int TRANSACTION_closeSystemDialogs = 8;
        static final int TRANSACTION_deleteKeyphraseSoundModel = 15;
        static final int TRANSACTION_deliverNewSession = 2;
        static final int TRANSACTION_finish = 9;
        static final int TRANSACTION_getActiveServiceComponentName = 20;
        static final int TRANSACTION_getActiveServiceSupportedActions = 29;
        static final int TRANSACTION_getDisabledShowContext = 11;
        static final int TRANSACTION_getDspModuleProperties = 16;
        static final int TRANSACTION_getKeyphraseSoundModel = 13;
        static final int TRANSACTION_getUserDisabledShowContext = 12;
        static final int TRANSACTION_hideCurrentSession = 22;
        static final int TRANSACTION_hideSessionFromSession = 4;
        static final int TRANSACTION_isEnrolledForKeyphrase = 17;
        static final int TRANSACTION_isSessionRunning = 24;
        static final int TRANSACTION_launchVoiceAssistFromKeyguard = 23;
        static final int TRANSACTION_onLockscreenShown = 27;
        static final int TRANSACTION_performDirectAction = 32;
        static final int TRANSACTION_registerVoiceInteractionSessionListener = 28;
        static final int TRANSACTION_requestDirectActions = 31;
        static final int TRANSACTION_setDisabledShowContext = 10;
        static final int TRANSACTION_setKeepAwake = 7;
        static final int TRANSACTION_setUiHints = 30;
        static final int TRANSACTION_showSession = 1;
        static final int TRANSACTION_showSessionForActiveService = 21;
        static final int TRANSACTION_showSessionFromSession = 3;
        static final int TRANSACTION_startAssistantActivity = 6;
        static final int TRANSACTION_startRecognition = 18;
        static final int TRANSACTION_startVoiceActivity = 5;
        static final int TRANSACTION_stopRecognition = 19;
        static final int TRANSACTION_updateKeyphraseSoundModel = 14;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IVoiceInteractionManagerService)) {
                return new Proxy(obj);
            }
            return (IVoiceInteractionManagerService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "showSession";
                case 2:
                    return "deliverNewSession";
                case 3:
                    return "showSessionFromSession";
                case 4:
                    return "hideSessionFromSession";
                case 5:
                    return "startVoiceActivity";
                case 6:
                    return "startAssistantActivity";
                case 7:
                    return "setKeepAwake";
                case 8:
                    return "closeSystemDialogs";
                case 9:
                    return "finish";
                case 10:
                    return "setDisabledShowContext";
                case 11:
                    return "getDisabledShowContext";
                case 12:
                    return "getUserDisabledShowContext";
                case 13:
                    return "getKeyphraseSoundModel";
                case 14:
                    return "updateKeyphraseSoundModel";
                case 15:
                    return "deleteKeyphraseSoundModel";
                case 16:
                    return "getDspModuleProperties";
                case 17:
                    return "isEnrolledForKeyphrase";
                case 18:
                    return "startRecognition";
                case 19:
                    return "stopRecognition";
                case 20:
                    return "getActiveServiceComponentName";
                case 21:
                    return "showSessionForActiveService";
                case 22:
                    return "hideCurrentSession";
                case 23:
                    return "launchVoiceAssistFromKeyguard";
                case 24:
                    return "isSessionRunning";
                case 25:
                    return "activeServiceSupportsAssist";
                case 26:
                    return "activeServiceSupportsLaunchFromKeyguard";
                case 27:
                    return "onLockscreenShown";
                case 28:
                    return "registerVoiceInteractionSessionListener";
                case 29:
                    return "getActiveServiceSupportedActions";
                case 30:
                    return "setUiHints";
                case 31:
                    return "requestDirectActions";
                case 32:
                    return "performDirectAction";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: android.os.Bundle} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v12, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v16, types: [android.content.Intent] */
        /* JADX WARNING: type inference failed for: r1v22 */
        /* JADX WARNING: type inference failed for: r1v33 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r19, android.os.Parcel r20, android.os.Parcel r21, int r22) throws android.os.RemoteException {
            /*
                r18 = this;
                r8 = r18
                r9 = r19
                r10 = r20
                r11 = r21
                java.lang.String r12 = "com.android.internal.app.IVoiceInteractionManagerService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r13 = 1
                if (r9 == r0) goto L_0x039e
                r0 = 0
                r1 = 0
                switch(r9) {
                    case 1: goto L_0x0378;
                    case 2: goto L_0x0356;
                    case 3: goto L_0x0330;
                    case 4: goto L_0x031e;
                    case 5: goto L_0x02f8;
                    case 6: goto L_0x02d2;
                    case 7: goto L_0x02bc;
                    case 8: goto L_0x02ae;
                    case 9: goto L_0x02a0;
                    case 10: goto L_0x0292;
                    case 11: goto L_0x0284;
                    case 12: goto L_0x0276;
                    case 13: goto L_0x0257;
                    case 14: goto L_0x0237;
                    case 15: goto L_0x0221;
                    case 16: goto L_0x0202;
                    case 17: goto L_0x01e4;
                    case 18: goto L_0x01a7;
                    case 19: goto L_0x0185;
                    case 20: goto L_0x016e;
                    case 21: goto L_0x013e;
                    case 22: goto L_0x0134;
                    case 23: goto L_0x012a;
                    case 24: goto L_0x011c;
                    case 25: goto L_0x010e;
                    case 26: goto L_0x0100;
                    case 27: goto L_0x00f6;
                    case 28: goto L_0x00e4;
                    case 29: goto L_0x00ce;
                    case 30: goto L_0x00ac;
                    case 31: goto L_0x006f;
                    case 32: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r19, r20, r21, r22)
                return r0
            L_0x001a:
                r10.enforceInterface(r12)
                android.os.IBinder r14 = r20.readStrongBinder()
                java.lang.String r15 = r20.readString()
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0035
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.Bundle r0 = (android.os.Bundle) r0
                r3 = r0
                goto L_0x0036
            L_0x0035:
                r3 = r1
            L_0x0036:
                int r16 = r20.readInt()
                android.os.IBinder r17 = r20.readStrongBinder()
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x004e
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r6 = r0
                goto L_0x004f
            L_0x004e:
                r6 = r1
            L_0x004f:
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x005f
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r7 = r0
                goto L_0x0060
            L_0x005f:
                r7 = r1
            L_0x0060:
                r0 = r18
                r1 = r14
                r2 = r15
                r4 = r16
                r5 = r17
                r0.performDirectAction(r1, r2, r3, r4, r5, r6, r7)
                r21.writeNoException()
                return r13
            L_0x006f:
                r10.enforceInterface(r12)
                android.os.IBinder r6 = r20.readStrongBinder()
                int r7 = r20.readInt()
                android.os.IBinder r14 = r20.readStrongBinder()
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x008e
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r4 = r0
                goto L_0x008f
            L_0x008e:
                r4 = r1
            L_0x008f:
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x009f
                android.os.Parcelable$Creator<android.os.RemoteCallback> r0 = android.os.RemoteCallback.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.os.RemoteCallback r0 = (android.os.RemoteCallback) r0
                r5 = r0
                goto L_0x00a0
            L_0x009f:
                r5 = r1
            L_0x00a0:
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r14
                r0.requestDirectActions(r1, r2, r3, r4, r5)
                r21.writeNoException()
                return r13
            L_0x00ac:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionService r0 = android.service.voice.IVoiceInteractionService.Stub.asInterface(r0)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x00c6
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x00c7
            L_0x00c6:
            L_0x00c7:
                r8.setUiHints(r0, r1)
                r21.writeNoException()
                return r13
            L_0x00ce:
                r10.enforceInterface(r12)
                java.util.ArrayList r0 = r20.createStringArrayList()
                android.os.IBinder r1 = r20.readStrongBinder()
                com.android.internal.app.IVoiceActionCheckCallback r1 = com.android.internal.app.IVoiceActionCheckCallback.Stub.asInterface(r1)
                r8.getActiveServiceSupportedActions(r0, r1)
                r21.writeNoException()
                return r13
            L_0x00e4:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                com.android.internal.app.IVoiceInteractionSessionListener r0 = com.android.internal.app.IVoiceInteractionSessionListener.Stub.asInterface(r0)
                r8.registerVoiceInteractionSessionListener(r0)
                r21.writeNoException()
                return r13
            L_0x00f6:
                r10.enforceInterface(r12)
                r18.onLockscreenShown()
                r21.writeNoException()
                return r13
            L_0x0100:
                r10.enforceInterface(r12)
                boolean r0 = r18.activeServiceSupportsLaunchFromKeyguard()
                r21.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x010e:
                r10.enforceInterface(r12)
                boolean r0 = r18.activeServiceSupportsAssist()
                r21.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x011c:
                r10.enforceInterface(r12)
                boolean r0 = r18.isSessionRunning()
                r21.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x012a:
                r10.enforceInterface(r12)
                r18.launchVoiceAssistFromKeyguard()
                r21.writeNoException()
                return r13
            L_0x0134:
                r10.enforceInterface(r12)
                r18.hideCurrentSession()
                r21.writeNoException()
                return r13
            L_0x013e:
                r10.enforceInterface(r12)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x0151
                android.os.Parcelable$Creator<android.os.Bundle> r0 = android.os.Bundle.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0152
            L_0x0151:
            L_0x0152:
                r0 = r1
                int r1 = r20.readInt()
                android.os.IBinder r2 = r20.readStrongBinder()
                com.android.internal.app.IVoiceInteractionSessionShowCallback r2 = com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub.asInterface(r2)
                android.os.IBinder r3 = r20.readStrongBinder()
                boolean r4 = r8.showSessionForActiveService(r0, r1, r2, r3)
                r21.writeNoException()
                r11.writeInt(r4)
                return r13
            L_0x016e:
                r10.enforceInterface(r12)
                android.content.ComponentName r1 = r18.getActiveServiceComponentName()
                r21.writeNoException()
                if (r1 == 0) goto L_0x0181
                r11.writeInt(r13)
                r1.writeToParcel((android.os.Parcel) r11, (int) r13)
                goto L_0x0184
            L_0x0181:
                r11.writeInt(r0)
            L_0x0184:
                return r13
            L_0x0185:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionService r0 = android.service.voice.IVoiceInteractionService.Stub.asInterface(r0)
                int r1 = r20.readInt()
                android.os.IBinder r2 = r20.readStrongBinder()
                android.hardware.soundtrigger.IRecognitionStatusCallback r2 = android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.asInterface(r2)
                int r3 = r8.stopRecognition(r0, r1, r2)
                r21.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x01a7:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionService r6 = android.service.voice.IVoiceInteractionService.Stub.asInterface(r0)
                int r7 = r20.readInt()
                java.lang.String r14 = r20.readString()
                android.os.IBinder r0 = r20.readStrongBinder()
                android.hardware.soundtrigger.IRecognitionStatusCallback r15 = android.hardware.soundtrigger.IRecognitionStatusCallback.Stub.asInterface(r0)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x01d2
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$RecognitionConfig> r0 = android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                android.hardware.soundtrigger.SoundTrigger$RecognitionConfig r0 = (android.hardware.soundtrigger.SoundTrigger.RecognitionConfig) r0
                r5 = r0
                goto L_0x01d3
            L_0x01d2:
                r5 = r1
            L_0x01d3:
                r0 = r18
                r1 = r6
                r2 = r7
                r3 = r14
                r4 = r15
                int r0 = r0.startRecognition(r1, r2, r3, r4, r5)
                r21.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x01e4:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionService r0 = android.service.voice.IVoiceInteractionService.Stub.asInterface(r0)
                int r1 = r20.readInt()
                java.lang.String r2 = r20.readString()
                boolean r3 = r8.isEnrolledForKeyphrase(r0, r1, r2)
                r21.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0202:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionService r1 = android.service.voice.IVoiceInteractionService.Stub.asInterface(r1)
                android.hardware.soundtrigger.SoundTrigger$ModuleProperties r2 = r8.getDspModuleProperties(r1)
                r21.writeNoException()
                if (r2 == 0) goto L_0x021d
                r11.writeInt(r13)
                r2.writeToParcel(r11, r13)
                goto L_0x0220
            L_0x021d:
                r11.writeInt(r0)
            L_0x0220:
                return r13
            L_0x0221:
                r10.enforceInterface(r12)
                int r0 = r20.readInt()
                java.lang.String r1 = r20.readString()
                int r2 = r8.deleteKeyphraseSoundModel(r0, r1)
                r21.writeNoException()
                r11.writeInt(r2)
                return r13
            L_0x0237:
                r10.enforceInterface(r12)
                int r0 = r20.readInt()
                if (r0 == 0) goto L_0x024a
                android.os.Parcelable$Creator<android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel> r0 = android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r10)
                r1 = r0
                android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel r1 = (android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel) r1
                goto L_0x024b
            L_0x024a:
            L_0x024b:
                r0 = r1
                int r1 = r8.updateKeyphraseSoundModel(r0)
                r21.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0257:
                r10.enforceInterface(r12)
                int r1 = r20.readInt()
                java.lang.String r2 = r20.readString()
                android.hardware.soundtrigger.SoundTrigger$KeyphraseSoundModel r3 = r8.getKeyphraseSoundModel(r1, r2)
                r21.writeNoException()
                if (r3 == 0) goto L_0x0272
                r11.writeInt(r13)
                r3.writeToParcel(r11, r13)
                goto L_0x0275
            L_0x0272:
                r11.writeInt(r0)
            L_0x0275:
                return r13
            L_0x0276:
                r10.enforceInterface(r12)
                int r0 = r18.getUserDisabledShowContext()
                r21.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0284:
                r10.enforceInterface(r12)
                int r0 = r18.getDisabledShowContext()
                r21.writeNoException()
                r11.writeInt(r0)
                return r13
            L_0x0292:
                r10.enforceInterface(r12)
                int r0 = r20.readInt()
                r8.setDisabledShowContext(r0)
                r21.writeNoException()
                return r13
            L_0x02a0:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                r8.finish(r0)
                r21.writeNoException()
                return r13
            L_0x02ae:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                r8.closeSystemDialogs(r0)
                r21.writeNoException()
                return r13
            L_0x02bc:
                r10.enforceInterface(r12)
                android.os.IBinder r1 = r20.readStrongBinder()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x02cb
                r0 = r13
            L_0x02cb:
                r8.setKeepAwake(r1, r0)
                r21.writeNoException()
                return r13
            L_0x02d2:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x02e8
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x02e9
            L_0x02e8:
            L_0x02e9:
                java.lang.String r2 = r20.readString()
                int r3 = r8.startAssistantActivity(r0, r1, r2)
                r21.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x02f8:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x030e
                android.os.Parcelable$Creator<android.content.Intent> r1 = android.content.Intent.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.content.Intent r1 = (android.content.Intent) r1
                goto L_0x030f
            L_0x030e:
            L_0x030f:
                java.lang.String r2 = r20.readString()
                int r3 = r8.startVoiceActivity(r0, r1, r2)
                r21.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x031e:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                boolean r1 = r8.hideSessionFromSession(r0)
                r21.writeNoException()
                r11.writeInt(r1)
                return r13
            L_0x0330:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0346
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0347
            L_0x0346:
            L_0x0347:
                int r2 = r20.readInt()
                boolean r3 = r8.showSessionFromSession(r0, r1, r2)
                r21.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0356:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.os.IBinder r1 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionSession r1 = android.service.voice.IVoiceInteractionSession.Stub.asInterface(r1)
                android.os.IBinder r2 = r20.readStrongBinder()
                com.android.internal.app.IVoiceInteractor r2 = com.android.internal.app.IVoiceInteractor.Stub.asInterface(r2)
                boolean r3 = r8.deliverNewSession(r0, r1, r2)
                r21.writeNoException()
                r11.writeInt(r3)
                return r13
            L_0x0378:
                r10.enforceInterface(r12)
                android.os.IBinder r0 = r20.readStrongBinder()
                android.service.voice.IVoiceInteractionService r0 = android.service.voice.IVoiceInteractionService.Stub.asInterface(r0)
                int r2 = r20.readInt()
                if (r2 == 0) goto L_0x0392
                android.os.Parcelable$Creator<android.os.Bundle> r1 = android.os.Bundle.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r10)
                android.os.Bundle r1 = (android.os.Bundle) r1
                goto L_0x0393
            L_0x0392:
            L_0x0393:
                int r2 = r20.readInt()
                r8.showSession(r0, r1, r2)
                r21.writeNoException()
                return r13
            L_0x039e:
                r11.writeString(r12)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.IVoiceInteractionManagerService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IVoiceInteractionManagerService {
            public static IVoiceInteractionManagerService sDefaultImpl;
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

            public void showSession(IVoiceInteractionService service, Bundle sessionArgs, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(service != null ? service.asBinder() : null);
                    if (sessionArgs != null) {
                        _data.writeInt(1);
                        sessionArgs.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().showSession(service, sessionArgs, flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean deliverNewSession(IBinder token, IVoiceInteractionSession session, IVoiceInteractor interactor) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(session != null ? session.asBinder() : null);
                    if (interactor != null) {
                        iBinder = interactor.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    boolean z = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deliverNewSession(token, session, interactor);
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

            public boolean showSessionFromSession(IBinder token, Bundle sessionArgs, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean _result = true;
                    if (sessionArgs != null) {
                        _data.writeInt(1);
                        sessionArgs.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(flags);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().showSessionFromSession(token, sessionArgs, flags);
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

            public boolean hideSessionFromSession(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    boolean z = false;
                    if (!this.mRemote.transact(4, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hideSessionFromSession(token);
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

            public int startVoiceActivity(IBinder token, Intent intent, String resolvedType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startVoiceActivity(token, intent, resolvedType);
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

            public int startAssistantActivity(IBinder token, Intent intent, String resolvedType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(resolvedType);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startAssistantActivity(token, intent, resolvedType);
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

            public void setKeepAwake(IBinder token, boolean keepAwake) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(keepAwake);
                    if (this.mRemote.transact(7, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setKeepAwake(token, keepAwake);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void closeSystemDialogs(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(8, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().closeSystemDialogs(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void finish(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().finish(token);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDisabledShowContext(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setDisabledShowContext(flags);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDisabledShowContext() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDisabledShowContext();
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

            public int getUserDisabledShowContext() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUserDisabledShowContext();
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

            public SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int keyphraseId, String bcp47Locale) throws RemoteException {
                SoundTrigger.KeyphraseSoundModel _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyphraseId);
                    _data.writeString(bcp47Locale);
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getKeyphraseSoundModel(keyphraseId, bcp47Locale);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SoundTrigger.KeyphraseSoundModel.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    SoundTrigger.KeyphraseSoundModel _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel model) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (model != null) {
                        _data.writeInt(1);
                        model.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().updateKeyphraseSoundModel(model);
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

            public int deleteKeyphraseSoundModel(int keyphraseId, String bcp47Locale) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(keyphraseId);
                    _data.writeString(bcp47Locale);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().deleteKeyphraseSoundModel(keyphraseId, bcp47Locale);
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

            public SoundTrigger.ModuleProperties getDspModuleProperties(IVoiceInteractionService service) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    SoundTrigger.ModuleProperties _result = null;
                    _data.writeStrongBinder(service != null ? service.asBinder() : null);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getDspModuleProperties(service);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = SoundTrigger.ModuleProperties.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isEnrolledForKeyphrase(IVoiceInteractionService service, int keyphraseId, String bcp47Locale) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(service != null ? service.asBinder() : null);
                    _data.writeInt(keyphraseId);
                    _data.writeString(bcp47Locale);
                    boolean z = false;
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isEnrolledForKeyphrase(service, keyphraseId, bcp47Locale);
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

            public int startRecognition(IVoiceInteractionService service, int keyphraseId, String bcp47Locale, IRecognitionStatusCallback callback, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(service != null ? service.asBinder() : null);
                    _data.writeInt(keyphraseId);
                    _data.writeString(bcp47Locale);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    if (recognitionConfig != null) {
                        _data.writeInt(1);
                        recognitionConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startRecognition(service, keyphraseId, bcp47Locale, callback, recognitionConfig);
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

            public int stopRecognition(IVoiceInteractionService service, int keyphraseId, IRecognitionStatusCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = null;
                    _data.writeStrongBinder(service != null ? service.asBinder() : null);
                    _data.writeInt(keyphraseId);
                    if (callback != null) {
                        iBinder = callback.asBinder();
                    }
                    _data.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(19, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().stopRecognition(service, keyphraseId, callback);
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

            public ComponentName getActiveServiceComponentName() throws RemoteException {
                ComponentName _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveServiceComponentName();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ComponentName.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ComponentName _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean showSessionForActiveService(Bundle args, int sourceFlags, IVoiceInteractionSessionShowCallback showCallback, IBinder activityToken) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (args != null) {
                        _data.writeInt(1);
                        args.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(sourceFlags);
                    _data.writeStrongBinder(showCallback != null ? showCallback.asBinder() : null);
                    _data.writeStrongBinder(activityToken);
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().showSessionForActiveService(args, sourceFlags, showCallback, activityToken);
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

            public void hideCurrentSession() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(22, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().hideCurrentSession();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void launchVoiceAssistFromKeyguard() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(23, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().launchVoiceAssistFromKeyguard();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSessionRunning() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSessionRunning();
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

            public boolean activeServiceSupportsAssist() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(25, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().activeServiceSupportsAssist();
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

            public boolean activeServiceSupportsLaunchFromKeyguard() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(26, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().activeServiceSupportsLaunchFromKeyguard();
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

            public void onLockscreenShown() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().onLockscreenShown();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    if (this.mRemote.transact(28, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerVoiceInteractionSessionListener(listener);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void getActiveServiceSupportedActions(List<String> voiceActions, IVoiceActionCheckCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(voiceActions);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    if (this.mRemote.transact(29, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().getActiveServiceSupportedActions(voiceActions, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setUiHints(IVoiceInteractionService service, Bundle hints) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(service != null ? service.asBinder() : null);
                    if (hints != null) {
                        _data.writeInt(1);
                        hints.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(30, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setUiHints(service, hints);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void requestDirectActions(IBinder token, int taskId, IBinder assistToken, RemoteCallback cancellationCallback, RemoteCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeInt(taskId);
                    _data.writeStrongBinder(assistToken);
                    if (cancellationCallback != null) {
                        _data.writeInt(1);
                        cancellationCallback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (callback != null) {
                        _data.writeInt(1);
                        callback.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().requestDirectActions(token, taskId, assistToken, cancellationCallback, callback);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void performDirectAction(IBinder token, String actionId, Bundle arguments, int taskId, IBinder assistToken, RemoteCallback cancellationCallback, RemoteCallback resultCallback) throws RemoteException {
                Bundle bundle = arguments;
                RemoteCallback remoteCallback = cancellationCallback;
                RemoteCallback remoteCallback2 = resultCallback;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        _data.writeStrongBinder(token);
                        try {
                            _data.writeString(actionId);
                            if (bundle != null) {
                                _data.writeInt(1);
                                bundle.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            _data.writeInt(taskId);
                            _data.writeStrongBinder(assistToken);
                            if (remoteCallback != null) {
                                _data.writeInt(1);
                                remoteCallback.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (remoteCallback2 != null) {
                                _data.writeInt(1);
                                remoteCallback2.writeToParcel(_data, 0);
                            } else {
                                _data.writeInt(0);
                            }
                            if (this.mRemote.transact(32, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                _reply.recycle();
                                _data.recycle();
                                return;
                            }
                            Stub.getDefaultImpl().performDirectAction(token, actionId, arguments, taskId, assistToken, cancellationCallback, resultCallback);
                            _reply.recycle();
                            _data.recycle();
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        String str = actionId;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    IBinder iBinder = token;
                    String str2 = actionId;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }
        }

        public static boolean setDefaultImpl(IVoiceInteractionManagerService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IVoiceInteractionManagerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
