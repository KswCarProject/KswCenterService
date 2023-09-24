package android.service.voice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.p007os.Bundle;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.service.voice.IVoiceInteractionSessionService;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.p016os.HandlerCaller;
import com.android.internal.p016os.SomeArgs;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class VoiceInteractionSessionService extends Service {
    static final int MSG_NEW_SESSION = 1;
    HandlerCaller mHandlerCaller;
    VoiceInteractionSession mSession;
    IVoiceInteractionManagerService mSystemService;
    IVoiceInteractionSessionService mInterface = new IVoiceInteractionSessionService.Stub() { // from class: android.service.voice.VoiceInteractionSessionService.1
        @Override // android.service.voice.IVoiceInteractionSessionService
        public void newSession(IBinder token, Bundle args, int startFlags) {
            VoiceInteractionSessionService.this.mHandlerCaller.sendMessage(VoiceInteractionSessionService.this.mHandlerCaller.obtainMessageIOO(1, startFlags, token, args));
        }
    };
    final HandlerCaller.Callback mHandlerCallerCallback = new HandlerCaller.Callback() { // from class: android.service.voice.VoiceInteractionSessionService.2
        @Override // com.android.internal.p016os.HandlerCaller.Callback
        public void executeMessage(Message msg) {
            SomeArgs args = (SomeArgs) msg.obj;
            if (msg.what == 1) {
                VoiceInteractionSessionService.this.doNewSession((IBinder) args.arg1, (Bundle) args.arg2, args.argi1);
            }
        }
    };

    public abstract VoiceInteractionSession onNewSession(Bundle bundle);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mSystemService = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService(Context.VOICE_INTERACTION_MANAGER_SERVICE));
        this.mHandlerCaller = new HandlerCaller(this, Looper.myLooper(), this.mHandlerCallerCallback, true);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mInterface.asBinder();
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mSession != null) {
            this.mSession.onConfigurationChanged(newConfig);
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        if (this.mSession != null) {
            this.mSession.onLowMemory();
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (this.mSession != null) {
            this.mSession.onTrimMemory(level);
        }
    }

    @Override // android.app.Service
    protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        if (this.mSession == null) {
            writer.println("(no active session)");
            return;
        }
        writer.println("VoiceInteractionSession:");
        this.mSession.dump("  ", fd, writer, args);
    }

    void doNewSession(IBinder token, Bundle args, int startFlags) {
        if (this.mSession != null) {
            this.mSession.doDestroy();
            this.mSession = null;
        }
        this.mSession = onNewSession(args);
        try {
            this.mSystemService.deliverNewSession(token, this.mSession.mSession, this.mSession.mInteractor);
            this.mSession.doCreate(this.mSystemService, token);
        } catch (RemoteException e) {
        }
    }
}
