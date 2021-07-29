package android.service.voice;

import android.R;
import android.app.Dialog;
import android.app.DirectAction;
import android.app.Instrumentation;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.SoftInputWindow;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.SettingsStringUtil;
import android.service.voice.IVoiceInteractionSession;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.android.internal.annotations.Immutable;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.IVoiceInteractorCallback;
import com.android.internal.app.IVoiceInteractorRequest;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.ibm.icu.text.PluralRules;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class VoiceInteractionSession implements KeyEvent.Callback, ComponentCallbacks2 {
    static final boolean DEBUG = false;
    static final int MSG_CANCEL = 7;
    static final int MSG_CLOSE_SYSTEM_DIALOGS = 102;
    static final int MSG_DESTROY = 103;
    static final int MSG_HANDLE_ASSIST = 104;
    static final int MSG_HANDLE_SCREENSHOT = 105;
    static final int MSG_HIDE = 107;
    static final int MSG_ON_LOCKSCREEN_SHOWN = 108;
    static final int MSG_SHOW = 106;
    static final int MSG_START_ABORT_VOICE = 4;
    static final int MSG_START_COMMAND = 5;
    static final int MSG_START_COMPLETE_VOICE = 3;
    static final int MSG_START_CONFIRMATION = 1;
    static final int MSG_START_PICK_OPTION = 2;
    static final int MSG_SUPPORTS_COMMANDS = 6;
    static final int MSG_TASK_FINISHED = 101;
    static final int MSG_TASK_STARTED = 100;
    public static final int SHOW_SOURCE_ACTIVITY = 16;
    public static final int SHOW_SOURCE_APPLICATION = 8;
    public static final int SHOW_SOURCE_ASSIST_GESTURE = 4;
    public static final int SHOW_SOURCE_AUTOMOTIVE_SYSTEM_UI = 128;
    public static final int SHOW_SOURCE_NOTIFICATION = 64;
    public static final int SHOW_SOURCE_PUSH_TO_TALK = 32;
    public static final int SHOW_WITH_ASSIST = 1;
    public static final int SHOW_WITH_SCREENSHOT = 2;
    static final String TAG = "VoiceInteractionSession";
    final ArrayMap<IBinder, Request> mActiveRequests;
    final MyCallbacks mCallbacks;
    FrameLayout mContentFrame;
    final Context mContext;
    final KeyEvent.DispatcherState mDispatcherState;
    final HandlerCaller mHandlerCaller;
    boolean mInShowWindow;
    LayoutInflater mInflater;
    boolean mInitialized;
    final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer;
    final IVoiceInteractor mInteractor;
    ICancellationSignal mKillCallback;
    final Map<SafeResultListener, Consumer<Bundle>> mRemoteCallbacks;
    View mRootView;
    final IVoiceInteractionSession mSession;
    IVoiceInteractionManagerService mSystemService;
    int mTheme;
    TypedArray mThemeAttrs;
    final Insets mTmpInsets;
    IBinder mToken;
    boolean mUiEnabled;
    final WeakReference<VoiceInteractionSession> mWeakRef;
    SoftInputWindow mWindow;
    boolean mWindowAdded;
    boolean mWindowVisible;
    boolean mWindowWasVisible;

    public static final class Insets {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public final Rect contentInsets = new Rect();
        public int touchableInsets;
        public final Region touchableRegion = new Region();
    }

    public static class Request {
        final IVoiceInteractorCallback mCallback;
        final String mCallingPackage;
        final int mCallingUid;
        final Bundle mExtras;
        final IVoiceInteractorRequest mInterface = new IVoiceInteractorRequest.Stub() {
            public void cancel() throws RemoteException {
                VoiceInteractionSession session = (VoiceInteractionSession) Request.this.mSession.get();
                if (session != null) {
                    session.mHandlerCaller.sendMessage(session.mHandlerCaller.obtainMessageO(7, Request.this));
                }
            }
        };
        final WeakReference<VoiceInteractionSession> mSession;

        Request(String packageName, int uid, IVoiceInteractorCallback callback, VoiceInteractionSession session, Bundle extras) {
            this.mCallingPackage = packageName;
            this.mCallingUid = uid;
            this.mCallback = callback;
            this.mSession = session.mWeakRef;
            this.mExtras = extras;
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public boolean isActive() {
            VoiceInteractionSession session = (VoiceInteractionSession) this.mSession.get();
            if (session == null) {
                return false;
            }
            return session.isRequestActive(this.mInterface.asBinder());
        }

        /* access modifiers changed from: package-private */
        public void finishRequest() {
            VoiceInteractionSession session = (VoiceInteractionSession) this.mSession.get();
            if (session != null) {
                Request req = session.removeRequest(this.mInterface.asBinder());
                if (req == null) {
                    throw new IllegalStateException("Request not active: " + this);
                } else if (req != this) {
                    throw new IllegalStateException("Current active request " + req + " not same as calling request " + this);
                }
            } else {
                throw new IllegalStateException("VoiceInteractionSession has been destroyed");
            }
        }

        public void cancel() {
            try {
                finishRequest();
                this.mCallback.deliverCancel(this.mInterface);
            } catch (RemoteException e) {
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            DebugUtils.buildShortClassTag(this, sb);
            sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            sb.append(this.mInterface.asBinder());
            sb.append(" pkg=");
            sb.append(this.mCallingPackage);
            sb.append(" uid=");
            UserHandle.formatUid(sb, this.mCallingUid);
            sb.append('}');
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            writer.print(prefix);
            writer.print("mInterface=");
            writer.println(this.mInterface.asBinder());
            writer.print(prefix);
            writer.print("mCallingPackage=");
            writer.print(this.mCallingPackage);
            writer.print(" mCallingUid=");
            UserHandle.formatUid(writer, this.mCallingUid);
            writer.println();
            writer.print(prefix);
            writer.print("mCallback=");
            writer.println(this.mCallback.asBinder());
            if (this.mExtras != null) {
                writer.print(prefix);
                writer.print("mExtras=");
                writer.println(this.mExtras);
            }
        }
    }

    public static final class ConfirmationRequest extends Request {
        final VoiceInteractor.Prompt mPrompt;

        ConfirmationRequest(String packageName, int uid, IVoiceInteractorCallback callback, VoiceInteractionSession session, VoiceInteractor.Prompt prompt, Bundle extras) {
            super(packageName, uid, callback, session, extras);
            this.mPrompt = prompt;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @Deprecated
        public CharSequence getPrompt() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public void sendConfirmationResult(boolean confirmed, Bundle result) {
            try {
                finishRequest();
                this.mCallback.deliverConfirmationResult(this.mInterface, confirmed, result);
            } catch (RemoteException e) {
            }
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            super.dump(prefix, fd, writer, args);
            writer.print(prefix);
            writer.print("mPrompt=");
            writer.println(this.mPrompt);
        }
    }

    public static final class PickOptionRequest extends Request {
        final VoiceInteractor.PickOptionRequest.Option[] mOptions;
        final VoiceInteractor.Prompt mPrompt;

        PickOptionRequest(String packageName, int uid, IVoiceInteractorCallback callback, VoiceInteractionSession session, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] options, Bundle extras) {
            super(packageName, uid, callback, session, extras);
            this.mPrompt = prompt;
            this.mOptions = options;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @Deprecated
        public CharSequence getPrompt() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public VoiceInteractor.PickOptionRequest.Option[] getOptions() {
            return this.mOptions;
        }

        /* access modifiers changed from: package-private */
        public void sendPickOptionResult(boolean finished, VoiceInteractor.PickOptionRequest.Option[] selections, Bundle result) {
            if (finished) {
                try {
                    finishRequest();
                } catch (RemoteException e) {
                    return;
                }
            }
            this.mCallback.deliverPickOptionResult(this.mInterface, finished, selections, result);
        }

        public void sendIntermediatePickOptionResult(VoiceInteractor.PickOptionRequest.Option[] selections, Bundle result) {
            sendPickOptionResult(false, selections, result);
        }

        public void sendPickOptionResult(VoiceInteractor.PickOptionRequest.Option[] selections, Bundle result) {
            sendPickOptionResult(true, selections, result);
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            super.dump(prefix, fd, writer, args);
            writer.print(prefix);
            writer.print("mPrompt=");
            writer.println(this.mPrompt);
            if (this.mOptions != null) {
                writer.print(prefix);
                writer.println("Options:");
                for (int i = 0; i < this.mOptions.length; i++) {
                    VoiceInteractor.PickOptionRequest.Option op = this.mOptions[i];
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.println(SettingsStringUtil.DELIMITER);
                    writer.print(prefix);
                    writer.print("    mLabel=");
                    writer.println(op.getLabel());
                    writer.print(prefix);
                    writer.print("    mIndex=");
                    writer.println(op.getIndex());
                    if (op.countSynonyms() > 0) {
                        writer.print(prefix);
                        writer.println("    Synonyms:");
                        for (int j = 0; j < op.countSynonyms(); j++) {
                            writer.print(prefix);
                            writer.print("      #");
                            writer.print(j);
                            writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                            writer.println(op.getSynonymAt(j));
                        }
                    }
                    if (op.getExtras() != null) {
                        writer.print(prefix);
                        writer.print("    mExtras=");
                        writer.println(op.getExtras());
                    }
                }
            }
        }
    }

    public static final class CompleteVoiceRequest extends Request {
        final VoiceInteractor.Prompt mPrompt;

        CompleteVoiceRequest(String packageName, int uid, IVoiceInteractorCallback callback, VoiceInteractionSession session, VoiceInteractor.Prompt prompt, Bundle extras) {
            super(packageName, uid, callback, session, extras);
            this.mPrompt = prompt;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @Deprecated
        public CharSequence getMessage() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public void sendCompleteResult(Bundle result) {
            try {
                finishRequest();
                this.mCallback.deliverCompleteVoiceResult(this.mInterface, result);
            } catch (RemoteException e) {
            }
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            super.dump(prefix, fd, writer, args);
            writer.print(prefix);
            writer.print("mPrompt=");
            writer.println(this.mPrompt);
        }
    }

    public static final class AbortVoiceRequest extends Request {
        final VoiceInteractor.Prompt mPrompt;

        AbortVoiceRequest(String packageName, int uid, IVoiceInteractorCallback callback, VoiceInteractionSession session, VoiceInteractor.Prompt prompt, Bundle extras) {
            super(packageName, uid, callback, session, extras);
            this.mPrompt = prompt;
        }

        public VoiceInteractor.Prompt getVoicePrompt() {
            return this.mPrompt;
        }

        @Deprecated
        public CharSequence getMessage() {
            if (this.mPrompt != null) {
                return this.mPrompt.getVoicePromptAt(0);
            }
            return null;
        }

        public void sendAbortResult(Bundle result) {
            try {
                finishRequest();
                this.mCallback.deliverAbortVoiceResult(this.mInterface, result);
            } catch (RemoteException e) {
            }
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            super.dump(prefix, fd, writer, args);
            writer.print(prefix);
            writer.print("mPrompt=");
            writer.println(this.mPrompt);
        }
    }

    public static final class CommandRequest extends Request {
        final String mCommand;

        CommandRequest(String packageName, int uid, IVoiceInteractorCallback callback, VoiceInteractionSession session, String command, Bundle extras) {
            super(packageName, uid, callback, session, extras);
            this.mCommand = command;
        }

        public String getCommand() {
            return this.mCommand;
        }

        /* access modifiers changed from: package-private */
        public void sendCommandResult(boolean finished, Bundle result) {
            if (finished) {
                try {
                    finishRequest();
                } catch (RemoteException e) {
                    return;
                }
            }
            this.mCallback.deliverCommandResult(this.mInterface, finished, result);
        }

        public void sendIntermediateResult(Bundle result) {
            sendCommandResult(false, result);
        }

        public void sendResult(Bundle result) {
            sendCommandResult(true, result);
        }

        /* access modifiers changed from: package-private */
        public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
            super.dump(prefix, fd, writer, args);
            writer.print(prefix);
            writer.print("mCommand=");
            writer.println(this.mCommand);
        }
    }

    class MyCallbacks implements HandlerCaller.Callback, SoftInputWindow.Callback {
        MyCallbacks() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: com.android.internal.os.SomeArgs} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: com.android.internal.os.SomeArgs} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void executeMessage(android.os.Message r11) {
            /*
                r10 = this;
                r0 = 0
                int r1 = r11.what
                switch(r1) {
                    case 1: goto L_0x00cd;
                    case 2: goto L_0x00c3;
                    case 3: goto L_0x00b9;
                    case 4: goto L_0x00af;
                    case 5: goto L_0x00a5;
                    case 6: goto L_0x008f;
                    case 7: goto L_0x0085;
                    default: goto L_0x0006;
                }
            L_0x0006:
                switch(r1) {
                    case 100: goto L_0x0079;
                    case 101: goto L_0x006d;
                    case 102: goto L_0x0067;
                    case 103: goto L_0x0060;
                    case 104: goto L_0x003a;
                    case 105: goto L_0x002f;
                    case 106: goto L_0x0019;
                    case 107: goto L_0x0012;
                    case 108: goto L_0x000b;
                    default: goto L_0x0009;
                }
            L_0x0009:
                goto L_0x00d7
            L_0x000b:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                r1.onLockscreenShown()
                goto L_0x00d7
            L_0x0012:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                r1.doHide()
                goto L_0x00d7
            L_0x0019:
                java.lang.Object r1 = r11.obj
                r0 = r1
                com.android.internal.os.SomeArgs r0 = (com.android.internal.os.SomeArgs) r0
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r0.arg1
                android.os.Bundle r2 = (android.os.Bundle) r2
                int r3 = r11.arg1
                java.lang.Object r4 = r0.arg2
                com.android.internal.app.IVoiceInteractionSessionShowCallback r4 = (com.android.internal.app.IVoiceInteractionSessionShowCallback) r4
                r1.doShow(r2, r3, r4)
                goto L_0x00d7
            L_0x002f:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.graphics.Bitmap r2 = (android.graphics.Bitmap) r2
                r1.onHandleScreenshot(r2)
                goto L_0x00d7
            L_0x003a:
                java.lang.Object r1 = r11.obj
                r0 = r1
                com.android.internal.os.SomeArgs r0 = (com.android.internal.os.SomeArgs) r0
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                int r2 = r0.argi1
                java.lang.Object r3 = r0.arg5
                android.os.IBinder r3 = (android.os.IBinder) r3
                java.lang.Object r4 = r0.arg1
                android.os.Bundle r4 = (android.os.Bundle) r4
                java.lang.Object r5 = r0.arg2
                android.app.assist.AssistStructure r5 = (android.app.assist.AssistStructure) r5
                java.lang.Object r6 = r0.arg3
                java.lang.Throwable r6 = (java.lang.Throwable) r6
                java.lang.Object r7 = r0.arg4
                android.app.assist.AssistContent r7 = (android.app.assist.AssistContent) r7
                int r8 = r0.argi5
                int r9 = r0.argi6
                r1.doOnHandleAssist(r2, r3, r4, r5, r6, r7, r8, r9)
                goto L_0x00d7
            L_0x0060:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                r1.doDestroy()
                goto L_0x00d7
            L_0x0067:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                r1.onCloseSystemDialogs()
                goto L_0x00d7
            L_0x006d:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.content.Intent r2 = (android.content.Intent) r2
                int r3 = r11.arg1
                r1.onTaskFinished(r2, r3)
                goto L_0x00d7
            L_0x0079:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.content.Intent r2 = (android.content.Intent) r2
                int r3 = r11.arg1
                r1.onTaskStarted(r2, r3)
                goto L_0x00d7
            L_0x0085:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.service.voice.VoiceInteractionSession$Request r2 = (android.service.voice.VoiceInteractionSession.Request) r2
                r1.onCancelRequest(r2)
                goto L_0x00d7
            L_0x008f:
                java.lang.Object r1 = r11.obj
                r0 = r1
                com.android.internal.os.SomeArgs r0 = (com.android.internal.os.SomeArgs) r0
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r0.arg1
                java.lang.String[] r2 = (java.lang.String[]) r2
                boolean[] r1 = r1.onGetSupportedCommands(r2)
                r0.arg1 = r1
                r0.complete()
                r0 = 0
                goto L_0x00d7
            L_0x00a5:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.service.voice.VoiceInteractionSession$CommandRequest r2 = (android.service.voice.VoiceInteractionSession.CommandRequest) r2
                r1.onRequestCommand(r2)
                goto L_0x00d7
            L_0x00af:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.service.voice.VoiceInteractionSession$AbortVoiceRequest r2 = (android.service.voice.VoiceInteractionSession.AbortVoiceRequest) r2
                r1.onRequestAbortVoice(r2)
                goto L_0x00d7
            L_0x00b9:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.service.voice.VoiceInteractionSession$CompleteVoiceRequest r2 = (android.service.voice.VoiceInteractionSession.CompleteVoiceRequest) r2
                r1.onRequestCompleteVoice(r2)
                goto L_0x00d7
            L_0x00c3:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.service.voice.VoiceInteractionSession$PickOptionRequest r2 = (android.service.voice.VoiceInteractionSession.PickOptionRequest) r2
                r1.onRequestPickOption(r2)
                goto L_0x00d7
            L_0x00cd:
                android.service.voice.VoiceInteractionSession r1 = android.service.voice.VoiceInteractionSession.this
                java.lang.Object r2 = r11.obj
                android.service.voice.VoiceInteractionSession$ConfirmationRequest r2 = (android.service.voice.VoiceInteractionSession.ConfirmationRequest) r2
                r1.onRequestConfirmation(r2)
            L_0x00d7:
                if (r0 == 0) goto L_0x00dc
                r0.recycle()
            L_0x00dc:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.voice.VoiceInteractionSession.MyCallbacks.executeMessage(android.os.Message):void");
        }

        public void onBackPressed() {
            VoiceInteractionSession.this.onBackPressed();
        }
    }

    public VoiceInteractionSession(Context context) {
        this(context, new Handler());
    }

    public VoiceInteractionSession(Context context, Handler handler) {
        this.mDispatcherState = new KeyEvent.DispatcherState();
        this.mTheme = 0;
        this.mUiEnabled = true;
        this.mActiveRequests = new ArrayMap<>();
        this.mTmpInsets = new Insets();
        this.mWeakRef = new WeakReference<>(this);
        this.mRemoteCallbacks = new ArrayMap();
        this.mInteractor = new IVoiceInteractor.Stub() {
            public IVoiceInteractorRequest startConfirmation(String callingPackage, IVoiceInteractorCallback callback, VoiceInteractor.Prompt prompt, Bundle extras) {
                ConfirmationRequest request = new ConfirmationRequest(callingPackage, Binder.getCallingUid(), callback, VoiceInteractionSession.this, prompt, extras);
                VoiceInteractionSession.this.addRequest(request);
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(1, request));
                return request.mInterface;
            }

            public IVoiceInteractorRequest startPickOption(String callingPackage, IVoiceInteractorCallback callback, VoiceInteractor.Prompt prompt, VoiceInteractor.PickOptionRequest.Option[] options, Bundle extras) {
                PickOptionRequest request = new PickOptionRequest(callingPackage, Binder.getCallingUid(), callback, VoiceInteractionSession.this, prompt, options, extras);
                VoiceInteractionSession.this.addRequest(request);
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(2, request));
                return request.mInterface;
            }

            public IVoiceInteractorRequest startCompleteVoice(String callingPackage, IVoiceInteractorCallback callback, VoiceInteractor.Prompt message, Bundle extras) {
                CompleteVoiceRequest request = new CompleteVoiceRequest(callingPackage, Binder.getCallingUid(), callback, VoiceInteractionSession.this, message, extras);
                VoiceInteractionSession.this.addRequest(request);
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(3, request));
                return request.mInterface;
            }

            public IVoiceInteractorRequest startAbortVoice(String callingPackage, IVoiceInteractorCallback callback, VoiceInteractor.Prompt message, Bundle extras) {
                AbortVoiceRequest request = new AbortVoiceRequest(callingPackage, Binder.getCallingUid(), callback, VoiceInteractionSession.this, message, extras);
                VoiceInteractionSession.this.addRequest(request);
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(4, request));
                return request.mInterface;
            }

            public IVoiceInteractorRequest startCommand(String callingPackage, IVoiceInteractorCallback callback, String command, Bundle extras) {
                CommandRequest request = new CommandRequest(callingPackage, Binder.getCallingUid(), callback, VoiceInteractionSession.this, command, extras);
                VoiceInteractionSession.this.addRequest(request);
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(5, request));
                return request.mInterface;
            }

            public boolean[] supportsCommands(String callingPackage, String[] commands) {
                SomeArgs args = VoiceInteractionSession.this.mHandlerCaller.sendMessageAndWait(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIOO(6, 0, commands, (Object) null));
                if (args == null) {
                    return new boolean[commands.length];
                }
                boolean[] res = (boolean[]) args.arg1;
                args.recycle();
                return res;
            }

            public void notifyDirectActionsChanged(int taskId, IBinder assistToken) {
                VoiceInteractionSession.this.mHandlerCaller.getHandler().sendMessage(PooledLambda.obtainMessage($$Lambda$lR4OeV3qsxUCrL7Xl2vrhTvEo.INSTANCE, VoiceInteractionSession.this, new ActivityId(taskId, assistToken)));
            }

            public void setKillCallback(ICancellationSignal callback) {
                VoiceInteractionSession.this.mKillCallback = callback;
            }
        };
        this.mSession = new IVoiceInteractionSession.Stub() {
            public void show(Bundle sessionArgs, int flags, IVoiceInteractionSessionShowCallback showCallback) {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIOO(106, flags, sessionArgs, showCallback));
            }

            public void hide() {
                VoiceInteractionSession.this.mHandlerCaller.removeMessages(106);
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(107));
            }

            public void handleAssist(int taskId, IBinder assistToken, Bundle data, AssistStructure structure, AssistContent content, int index, int count) {
                final AssistStructure assistStructure = structure;
                final int i = taskId;
                final Bundle bundle = data;
                final AssistContent assistContent = content;
                final IBinder iBinder = assistToken;
                final int i2 = index;
                final int i3 = count;
                new Thread("AssistStructure retriever") {
                    public void run() {
                        Throwable failure = null;
                        if (assistStructure != null) {
                            try {
                                assistStructure.ensureData();
                            } catch (Throwable e) {
                                Log.w(VoiceInteractionSession.TAG, "Failure retrieving AssistStructure", e);
                                failure = e;
                            }
                        }
                        SomeArgs args = SomeArgs.obtain();
                        args.argi1 = i;
                        args.arg1 = bundle;
                        args.arg2 = failure == null ? assistStructure : null;
                        args.arg3 = failure;
                        args.arg4 = assistContent;
                        args.arg5 = iBinder;
                        args.argi5 = i2;
                        args.argi6 = i3;
                        VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(104, args));
                    }
                }.start();
            }

            public void handleScreenshot(Bitmap screenshot) {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageO(105, screenshot));
            }

            public void taskStarted(Intent intent, int taskId) {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(100, taskId, intent));
            }

            public void taskFinished(Intent intent, int taskId) {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessageIO(101, taskId, intent));
            }

            public void closeSystemDialogs() {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(102));
            }

            public void onLockscreenShown() {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(108));
            }

            public void destroy() {
                VoiceInteractionSession.this.mHandlerCaller.sendMessage(VoiceInteractionSession.this.mHandlerCaller.obtainMessage(103));
            }
        };
        this.mCallbacks = new MyCallbacks();
        this.mInsetsComputer = new ViewTreeObserver.OnComputeInternalInsetsListener() {
            public void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo info) {
                VoiceInteractionSession.this.onComputeInsets(VoiceInteractionSession.this.mTmpInsets);
                info.contentInsets.set(VoiceInteractionSession.this.mTmpInsets.contentInsets);
                info.visibleInsets.set(VoiceInteractionSession.this.mTmpInsets.contentInsets);
                info.touchableRegion.set(VoiceInteractionSession.this.mTmpInsets.touchableRegion);
                info.setTouchableInsets(VoiceInteractionSession.this.mTmpInsets.touchableInsets);
            }
        };
        this.mContext = context;
        this.mHandlerCaller = new HandlerCaller(context, handler.getLooper(), this.mCallbacks, true);
    }

    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: package-private */
    public void addRequest(Request req) {
        synchronized (this) {
            this.mActiveRequests.put(req.mInterface.asBinder(), req);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isRequestActive(IBinder reqInterface) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.mActiveRequests.containsKey(reqInterface);
        }
        return containsKey;
    }

    /* access modifiers changed from: package-private */
    public Request removeRequest(IBinder reqInterface) {
        Request remove;
        synchronized (this) {
            remove = this.mActiveRequests.remove(reqInterface);
        }
        return remove;
    }

    /* access modifiers changed from: package-private */
    public void doCreate(IVoiceInteractionManagerService service, IBinder token) {
        this.mSystemService = service;
        this.mToken = token;
        onCreate();
    }

    /* access modifiers changed from: package-private */
    public void doShow(Bundle args, int flags, final IVoiceInteractionSessionShowCallback showCallback) {
        if (this.mInShowWindow) {
            Log.w(TAG, "Re-entrance in to showWindow");
            return;
        }
        try {
            this.mInShowWindow = true;
            onPrepareShow(args, flags);
            if (!this.mWindowVisible) {
                ensureWindowAdded();
            }
            onShow(args, flags);
            if (!this.mWindowVisible) {
                this.mWindowVisible = true;
                if (this.mUiEnabled) {
                    this.mWindow.show();
                }
            }
            if (showCallback != null) {
                if (this.mUiEnabled) {
                    this.mRootView.invalidate();
                    this.mRootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        public boolean onPreDraw() {
                            VoiceInteractionSession.this.mRootView.getViewTreeObserver().removeOnPreDrawListener(this);
                            try {
                                showCallback.onShown();
                                return true;
                            } catch (RemoteException e) {
                                Log.w(VoiceInteractionSession.TAG, "Error calling onShown", e);
                                return true;
                            }
                        }
                    });
                } else {
                    showCallback.onShown();
                }
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Error calling onShown", e);
        } catch (Throwable th) {
            this.mWindowWasVisible = true;
            this.mInShowWindow = false;
            throw th;
        }
        this.mWindowWasVisible = true;
        this.mInShowWindow = false;
    }

    /* access modifiers changed from: package-private */
    public void doHide() {
        if (this.mWindowVisible) {
            ensureWindowHidden();
            this.mWindowVisible = false;
            onHide();
        }
    }

    /* access modifiers changed from: package-private */
    public void doDestroy() {
        onDestroy();
        if (this.mKillCallback != null) {
            try {
                this.mKillCallback.cancel();
            } catch (RemoteException e) {
            }
            this.mKillCallback = null;
        }
        if (this.mInitialized) {
            this.mRootView.getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mInsetsComputer);
            if (this.mWindowAdded) {
                this.mWindow.dismiss();
                this.mWindowAdded = false;
            }
            this.mInitialized = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureWindowCreated() {
        if (!this.mInitialized) {
            if (this.mUiEnabled) {
                this.mInitialized = true;
                this.mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                this.mWindow = new SoftInputWindow(this.mContext, TAG, this.mTheme, this.mCallbacks, this, this.mDispatcherState, 2031, 80, true);
                this.mWindow.getWindow().addFlags(16843008);
                this.mThemeAttrs = this.mContext.obtainStyledAttributes(R.styleable.VoiceInteractionSession);
                this.mRootView = this.mInflater.inflate((int) com.android.internal.R.layout.voice_interaction_session, (ViewGroup) null);
                this.mRootView.setSystemUiVisibility(1792);
                this.mWindow.setContentView(this.mRootView);
                this.mRootView.getViewTreeObserver().addOnComputeInternalInsetsListener(this.mInsetsComputer);
                this.mContentFrame = (FrameLayout) this.mRootView.findViewById(16908290);
                this.mWindow.getWindow().setLayout(-1, -1);
                this.mWindow.setToken(this.mToken);
                return;
            }
            throw new IllegalStateException("setUiEnabled is false");
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureWindowAdded() {
        if (this.mUiEnabled && !this.mWindowAdded) {
            this.mWindowAdded = true;
            ensureWindowCreated();
            View v = onCreateContentView();
            if (v != null) {
                setContentView(v);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureWindowHidden() {
        if (this.mWindow != null) {
            this.mWindow.hide();
        }
    }

    public void setDisabledShowContext(int flags) {
        try {
            this.mSystemService.setDisabledShowContext(flags);
        } catch (RemoteException e) {
        }
    }

    public int getDisabledShowContext() {
        try {
            return this.mSystemService.getDisabledShowContext();
        } catch (RemoteException e) {
            return 0;
        }
    }

    public int getUserDisabledShowContext() {
        try {
            return this.mSystemService.getUserDisabledShowContext();
        } catch (RemoteException e) {
            return 0;
        }
    }

    public void show(Bundle args, int flags) {
        if (this.mToken != null) {
            try {
                this.mSystemService.showSessionFromSession(this.mToken, args, flags);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public void hide() {
        if (this.mToken != null) {
            try {
                this.mSystemService.hideSessionFromSession(this.mToken);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public void setUiEnabled(boolean enabled) {
        if (this.mUiEnabled != enabled) {
            this.mUiEnabled = enabled;
            if (!this.mWindowVisible) {
                return;
            }
            if (enabled) {
                ensureWindowAdded();
                this.mWindow.show();
                return;
            }
            ensureWindowHidden();
        }
    }

    public void setTheme(int theme) {
        if (this.mWindow == null) {
            this.mTheme = theme;
            return;
        }
        throw new IllegalStateException("Must be called before onCreate()");
    }

    public void startVoiceActivity(Intent intent) {
        if (this.mToken != null) {
            try {
                intent.migrateExtraStreamToClipData();
                intent.prepareToLeaveProcess(this.mContext);
                Instrumentation.checkStartActivityResult(this.mSystemService.startVoiceActivity(this.mToken, intent, intent.resolveType(this.mContext.getContentResolver())), intent);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public void startAssistantActivity(Intent intent) {
        if (this.mToken != null) {
            try {
                intent.migrateExtraStreamToClipData();
                intent.prepareToLeaveProcess(this.mContext);
                Instrumentation.checkStartActivityResult(this.mSystemService.startAssistantActivity(this.mToken, intent, intent.resolveType(this.mContext.getContentResolver())), intent);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public final void requestDirectActions(ActivityId activityId, CancellationSignal cancellationSignal, Executor resultExecutor, Consumer<List<DirectAction>> callback) {
        RemoteCallback cancellationCallback;
        Preconditions.checkNotNull(activityId);
        Preconditions.checkNotNull(resultExecutor);
        Preconditions.checkNotNull(callback);
        if (this.mToken != null) {
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (cancellationSignal != null) {
                cancellationCallback = new RemoteCallback((RemoteCallback.OnResultListener) new RemoteCallback.OnResultListener() {
                    public final void onResult(Bundle bundle) {
                        VoiceInteractionSession.lambda$requestDirectActions$0(CancellationSignal.this, bundle);
                    }
                });
            } else {
                cancellationCallback = null;
            }
            try {
                this.mSystemService.requestDirectActions(this.mToken, activityId.getTaskId(), activityId.getAssistToken(), cancellationCallback, new RemoteCallback((RemoteCallback.OnResultListener) createSafeResultListener(new Consumer(resultExecutor, callback) {
                    private final /* synthetic */ Executor f$0;
                    private final /* synthetic */ Consumer f$1;

                    {
                        this.f$0 = r1;
                        this.f$1 = r2;
                    }

                    public final void accept(Object obj) {
                        VoiceInteractionSession.lambda$requestDirectActions$2(this.f$0, this.f$1, (Bundle) obj);
                    }
                })));
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    static /* synthetic */ void lambda$requestDirectActions$0(CancellationSignal cancellationSignal, Bundle b) {
        IBinder cancellation;
        if (b != null && (cancellation = b.getBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL)) != null) {
            cancellationSignal.setRemote(ICancellationSignal.Stub.asInterface(cancellation));
        }
    }

    static /* synthetic */ void lambda$requestDirectActions$2(Executor resultExecutor, Consumer callback, Bundle result) {
        List<DirectAction> list;
        if (result == null) {
            list = Collections.emptyList();
        } else {
            ParceledListSlice<DirectAction> pls = (ParceledListSlice) result.getParcelable(DirectAction.KEY_ACTIONS_LIST);
            if (pls != null) {
                List<DirectAction> receivedList = pls.getList();
                list = receivedList != null ? receivedList : Collections.emptyList();
            } else {
                list = Collections.emptyList();
            }
        }
        resultExecutor.execute(new Runnable(callback, list) {
            private final /* synthetic */ Consumer f$0;
            private final /* synthetic */ List f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void run() {
                this.f$0.accept(this.f$1);
            }
        });
    }

    public void onDirectActionsInvalidated(ActivityId activityId) {
    }

    public final void performDirectAction(DirectAction action, Bundle extras, CancellationSignal cancellationSignal, Executor resultExecutor, Consumer<Bundle> resultListener) {
        RemoteCallback cancellationCallback;
        if (this.mToken != null) {
            Preconditions.checkNotNull(resultExecutor);
            Preconditions.checkNotNull(resultListener);
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            if (cancellationSignal != null) {
                cancellationCallback = new RemoteCallback((RemoteCallback.OnResultListener) createSafeResultListener(new Consumer() {
                    public final void accept(Object obj) {
                        VoiceInteractionSession.lambda$performDirectAction$3(CancellationSignal.this, (Bundle) obj);
                    }
                }));
            } else {
                cancellationCallback = null;
            }
            try {
                this.mSystemService.performDirectAction(this.mToken, action.getId(), extras, action.getTaskId(), action.getActivityId(), cancellationCallback, new RemoteCallback((RemoteCallback.OnResultListener) createSafeResultListener(new Consumer(resultExecutor, resultListener) {
                    private final /* synthetic */ Executor f$0;
                    private final /* synthetic */ Consumer f$1;

                    {
                        this.f$0 = r1;
                        this.f$1 = r2;
                    }

                    public final void accept(Object obj) {
                        VoiceInteractionSession.lambda$performDirectAction$6(this.f$0, this.f$1, (Bundle) obj);
                    }
                })));
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    static /* synthetic */ void lambda$performDirectAction$3(CancellationSignal cancellationSignal, Bundle b) {
        IBinder cancellation;
        if (b != null && (cancellation = b.getBinder(VoiceInteractor.KEY_CANCELLATION_SIGNAL)) != null) {
            cancellationSignal.setRemote(ICancellationSignal.Stub.asInterface(cancellation));
        }
    }

    static /* synthetic */ void lambda$performDirectAction$6(Executor resultExecutor, Consumer resultListener, Bundle b) {
        if (b != null) {
            resultExecutor.execute(new Runnable(resultListener, b) {
                private final /* synthetic */ Consumer f$0;
                private final /* synthetic */ Bundle f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    this.f$0.accept(this.f$1);
                }
            });
        } else {
            resultExecutor.execute(new Runnable(resultListener) {
                private final /* synthetic */ Consumer f$0;

                {
                    this.f$0 = r1;
                }

                public final void run() {
                    this.f$0.accept(Bundle.EMPTY);
                }
            });
        }
    }

    public void setKeepAwake(boolean keepAwake) {
        if (this.mToken != null) {
            try {
                this.mSystemService.setKeepAwake(this.mToken, keepAwake);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public void closeSystemDialogs() {
        if (this.mToken != null) {
            try {
                this.mSystemService.closeSystemDialogs(this.mToken);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public LayoutInflater getLayoutInflater() {
        ensureWindowCreated();
        return this.mInflater;
    }

    public Dialog getWindow() {
        ensureWindowCreated();
        return this.mWindow;
    }

    public void finish() {
        if (this.mToken != null) {
            try {
                this.mSystemService.finish(this.mToken);
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalStateException("Can't call before onCreate()");
        }
    }

    public void onCreate() {
        doOnCreate();
    }

    private void doOnCreate() {
        int i;
        if (this.mTheme != 0) {
            i = this.mTheme;
        } else {
            i = com.android.internal.R.style.Theme_DeviceDefault_VoiceInteractionSession;
        }
        this.mTheme = i;
    }

    public void onPrepareShow(Bundle args, int showFlags) {
    }

    public void onShow(Bundle args, int showFlags) {
    }

    public void onHide() {
    }

    public void onDestroy() {
    }

    public View onCreateContentView() {
        return null;
    }

    public void setContentView(View view) {
        ensureWindowCreated();
        this.mContentFrame.removeAllViews();
        this.mContentFrame.addView(view, (ViewGroup.LayoutParams) new FrameLayout.LayoutParams(-1, -1));
        this.mContentFrame.requestApplyInsets();
    }

    /* access modifiers changed from: package-private */
    public void doOnHandleAssist(int taskId, IBinder assistToken, Bundle data, AssistStructure structure, Throwable failure, AssistContent content, int index, int count) {
        Throwable th = failure;
        if (th != null) {
            onAssistStructureFailure(th);
        }
        int i = taskId;
        IBinder iBinder = assistToken;
        onHandleAssist(new AssistState(new ActivityId(taskId, assistToken), data, structure, content, index, count));
    }

    public void onAssistStructureFailure(Throwable failure) {
    }

    @Deprecated
    public void onHandleAssist(Bundle data, AssistStructure structure, AssistContent content) {
    }

    public void onHandleAssist(AssistState state) {
        if (state.getIndex() == 0) {
            onHandleAssist(state.getAssistData(), state.getAssistStructure(), state.getAssistContent());
            return;
        }
        onHandleAssistSecondary(state.getAssistData(), state.getAssistStructure(), state.getAssistContent(), state.getIndex(), state.getCount());
    }

    @Deprecated
    public void onHandleAssistSecondary(Bundle data, AssistStructure structure, AssistContent content, int index, int count) {
    }

    public void onHandleScreenshot(Bitmap screenshot) {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }

    public void onBackPressed() {
        hide();
    }

    public void onCloseSystemDialogs() {
        hide();
    }

    public void onLockscreenShown() {
        hide();
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onLowMemory() {
    }

    public void onTrimMemory(int level) {
    }

    public void onComputeInsets(Insets outInsets) {
        outInsets.contentInsets.left = 0;
        outInsets.contentInsets.bottom = 0;
        outInsets.contentInsets.right = 0;
        View decor = getWindow().getWindow().getDecorView();
        outInsets.contentInsets.top = decor.getHeight();
        outInsets.touchableInsets = 0;
        outInsets.touchableRegion.setEmpty();
    }

    public void onTaskStarted(Intent intent, int taskId) {
    }

    public void onTaskFinished(Intent intent, int taskId) {
        hide();
    }

    public boolean[] onGetSupportedCommands(String[] commands) {
        return new boolean[commands.length];
    }

    public void onRequestConfirmation(ConfirmationRequest request) {
    }

    public void onRequestPickOption(PickOptionRequest request) {
    }

    public void onRequestCompleteVoice(CompleteVoiceRequest request) {
    }

    public void onRequestAbortVoice(AbortVoiceRequest request) {
    }

    public void onRequestCommand(CommandRequest request) {
    }

    public void onCancelRequest(Request request) {
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.print(prefix);
        writer.print("mToken=");
        writer.println(this.mToken);
        writer.print(prefix);
        writer.print("mTheme=#");
        writer.println(Integer.toHexString(this.mTheme));
        writer.print(prefix);
        writer.print("mUiEnabled=");
        writer.println(this.mUiEnabled);
        writer.print(" mInitialized=");
        writer.println(this.mInitialized);
        writer.print(prefix);
        writer.print("mWindowAdded=");
        writer.print(this.mWindowAdded);
        writer.print(" mWindowVisible=");
        writer.println(this.mWindowVisible);
        writer.print(prefix);
        writer.print("mWindowWasVisible=");
        writer.print(this.mWindowWasVisible);
        writer.print(" mInShowWindow=");
        writer.println(this.mInShowWindow);
        if (this.mActiveRequests.size() > 0) {
            writer.print(prefix);
            writer.println("Active requests:");
            String innerPrefix = prefix + "    ";
            for (int i = 0; i < this.mActiveRequests.size(); i++) {
                Request req = this.mActiveRequests.valueAt(i);
                writer.print(prefix);
                writer.print("  #");
                writer.print(i);
                writer.print(PluralRules.KEYWORD_RULE_SEPARATOR);
                writer.println(req);
                req.dump(innerPrefix, fd, writer, args);
            }
        }
    }

    private SafeResultListener createSafeResultListener(Consumer<Bundle> consumer) {
        SafeResultListener listener;
        synchronized (this) {
            listener = new SafeResultListener(consumer, this);
            this.mRemoteCallbacks.put(listener, consumer);
        }
        return listener;
    }

    /* access modifiers changed from: private */
    public Consumer<Bundle> removeSafeResultListener(SafeResultListener listener) {
        Consumer<Bundle> remove;
        synchronized (this) {
            remove = this.mRemoteCallbacks.remove(listener);
        }
        return remove;
    }

    @Immutable
    public static final class AssistState {
        private final ActivityId mActivityId;
        private final AssistContent mContent;
        private final int mCount;
        private final Bundle mData;
        private final int mIndex;
        private final AssistStructure mStructure;

        AssistState(ActivityId activityId, Bundle data, AssistStructure structure, AssistContent content, int index, int count) {
            this.mActivityId = activityId;
            this.mIndex = index;
            this.mCount = count;
            this.mData = data;
            this.mStructure = structure;
            this.mContent = content;
        }

        public boolean isFocused() {
            return this.mIndex == 0;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public int getCount() {
            return this.mCount;
        }

        public ActivityId getActivityId() {
            return this.mActivityId;
        }

        public Bundle getAssistData() {
            return this.mData;
        }

        public AssistStructure getAssistStructure() {
            return this.mStructure;
        }

        public AssistContent getAssistContent() {
            return this.mContent;
        }
    }

    public static class ActivityId {
        private final IBinder mAssistToken;
        private final int mTaskId;

        ActivityId(int taskId, IBinder assistToken) {
            this.mTaskId = taskId;
            this.mAssistToken = assistToken;
        }

        /* access modifiers changed from: package-private */
        public int getTaskId() {
            return this.mTaskId;
        }

        /* access modifiers changed from: package-private */
        public IBinder getAssistToken() {
            return this.mAssistToken;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ActivityId that = (ActivityId) o;
            if (this.mTaskId != that.mTaskId) {
                return false;
            }
            if (this.mAssistToken != null) {
                return this.mAssistToken.equals(that.mAssistToken);
            }
            if (that.mAssistToken == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.mTaskId * 31) + (this.mAssistToken != null ? this.mAssistToken.hashCode() : 0);
        }
    }

    private static class SafeResultListener implements RemoteCallback.OnResultListener {
        private final WeakReference<VoiceInteractionSession> mWeakSession;

        SafeResultListener(Consumer<Bundle> consumer, VoiceInteractionSession session) {
            this.mWeakSession = new WeakReference<>(session);
        }

        public void onResult(Bundle result) {
            Consumer<Bundle> consumer;
            VoiceInteractionSession session = (VoiceInteractionSession) this.mWeakSession.get();
            if (session != null && (consumer = session.removeSafeResultListener(this)) != null) {
                consumer.accept(result);
            }
        }
    }
}
