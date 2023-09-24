package android.speech;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.PermissionChecker;
import android.p007os.Binder;
import android.p007os.Bundle;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.speech.IRecognitionService;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.Objects;

/* loaded from: classes3.dex */
public abstract class RecognitionService extends Service {
    private static final boolean DBG = false;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_RESET = 4;
    private static final int MSG_START_LISTENING = 1;
    private static final int MSG_STOP_LISTENING = 2;
    public static final String SERVICE_INTERFACE = "android.speech.RecognitionService";
    public static final String SERVICE_META_DATA = "android.speech";
    private static final String TAG = "RecognitionService";
    private RecognitionServiceBinder mBinder = new RecognitionServiceBinder(this);
    private Callback mCurrentCallback = null;
    private final Handler mHandler = new Handler() { // from class: android.speech.RecognitionService.1
        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    StartListeningArgs args = (StartListeningArgs) msg.obj;
                    RecognitionService.this.dispatchStartListening(args.mIntent, args.mListener, args.mCallingUid);
                    return;
                case 2:
                    RecognitionService.this.dispatchStopListening((IRecognitionListener) msg.obj);
                    return;
                case 3:
                    RecognitionService.this.dispatchCancel((IRecognitionListener) msg.obj);
                    return;
                case 4:
                    RecognitionService.this.dispatchClearCallback();
                    return;
                default:
                    return;
            }
        }
    };

    protected abstract void onCancel(Callback callback);

    protected abstract void onStartListening(Intent intent, Callback callback);

    protected abstract void onStopListening(Callback callback);

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchStartListening(Intent intent, final IRecognitionListener listener, int callingUid) {
        if (this.mCurrentCallback == null) {
            try {
                listener.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: android.speech.RecognitionService.2
                    @Override // android.p007os.IBinder.DeathRecipient
                    public void binderDied() {
                        RecognitionService.this.mHandler.sendMessage(RecognitionService.this.mHandler.obtainMessage(3, listener));
                    }
                }, 0);
                this.mCurrentCallback = new Callback(listener, callingUid);
                onStartListening(intent, this.mCurrentCallback);
                return;
            } catch (RemoteException e) {
                Log.m70e(TAG, "dead listener on startListening");
                return;
            }
        }
        try {
            listener.onError(8);
        } catch (RemoteException e2) {
            Log.m72d(TAG, "onError call from startListening failed");
        }
        Log.m68i(TAG, "concurrent startListening received - ignoring this call");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchStopListening(IRecognitionListener listener) {
        try {
            if (this.mCurrentCallback == null) {
                listener.onError(5);
                Log.m64w(TAG, "stopListening called with no preceding startListening - ignoring");
            } else if (this.mCurrentCallback.mListener.asBinder() != listener.asBinder()) {
                listener.onError(8);
                Log.m64w(TAG, "stopListening called by other caller than startListening - ignoring");
            } else {
                onStopListening(this.mCurrentCallback);
            }
        } catch (RemoteException e) {
            Log.m72d(TAG, "onError call from stopListening failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCancel(IRecognitionListener listener) {
        if (this.mCurrentCallback == null) {
            return;
        }
        if (this.mCurrentCallback.mListener.asBinder() != listener.asBinder()) {
            Log.m64w(TAG, "cancel called by client who did not call startListening - ignoring");
            return;
        }
        onCancel(this.mCurrentCallback);
        this.mCurrentCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchClearCallback() {
        this.mCurrentCallback = null;
    }

    /* loaded from: classes3.dex */
    private class StartListeningArgs {
        public final int mCallingUid;
        public final Intent mIntent;
        public final IRecognitionListener mListener;

        public StartListeningArgs(Intent intent, IRecognitionListener listener, int callingUid) {
            this.mIntent = intent;
            this.mListener = listener;
            this.mCallingUid = callingUid;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPermissions(IRecognitionListener listener) {
        if (PermissionChecker.checkCallingOrSelfPermission(this, Manifest.C0000permission.RECORD_AUDIO) == 0) {
            return true;
        }
        try {
            Log.m70e(TAG, "call for recognition service without RECORD_AUDIO permissions");
            listener.onError(9);
            return false;
        } catch (RemoteException re) {
            Log.m69e(TAG, "sending ERROR_INSUFFICIENT_PERMISSIONS message failed", re);
            return false;
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mCurrentCallback = null;
        this.mBinder.clearReference();
        super.onDestroy();
    }

    /* loaded from: classes3.dex */
    public class Callback {
        private final int mCallingUid;
        private final IRecognitionListener mListener;

        private Callback(IRecognitionListener listener, int callingUid) {
            this.mListener = listener;
            this.mCallingUid = callingUid;
        }

        public void beginningOfSpeech() throws RemoteException {
            this.mListener.onBeginningOfSpeech();
        }

        public void bufferReceived(byte[] buffer) throws RemoteException {
            this.mListener.onBufferReceived(buffer);
        }

        public void endOfSpeech() throws RemoteException {
            this.mListener.onEndOfSpeech();
        }

        public void error(int error) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4).sendToTarget();
            this.mListener.onError(error);
        }

        public void partialResults(Bundle partialResults) throws RemoteException {
            this.mListener.onPartialResults(partialResults);
        }

        public void readyForSpeech(Bundle params) throws RemoteException {
            this.mListener.onReadyForSpeech(params);
        }

        public void results(Bundle results) throws RemoteException {
            Message.obtain(RecognitionService.this.mHandler, 4).sendToTarget();
            this.mListener.onResults(results);
        }

        public void rmsChanged(float rmsdB) throws RemoteException {
            this.mListener.onRmsChanged(rmsdB);
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }
    }

    /* loaded from: classes3.dex */
    private static final class RecognitionServiceBinder extends IRecognitionService.Stub {
        private final WeakReference<RecognitionService> mServiceRef;

        public RecognitionServiceBinder(RecognitionService service) {
            this.mServiceRef = new WeakReference<>(service);
        }

        @Override // android.speech.IRecognitionService
        public void startListening(Intent recognizerIntent, IRecognitionListener listener) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null && service.checkPermissions(listener)) {
                Handler handler = service.mHandler;
                Handler handler2 = service.mHandler;
                Objects.requireNonNull(service);
                handler.sendMessage(Message.obtain(handler2, 1, new StartListeningArgs(recognizerIntent, listener, Binder.getCallingUid())));
            }
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(IRecognitionListener listener) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null && service.checkPermissions(listener)) {
                service.mHandler.sendMessage(Message.obtain(service.mHandler, 2, listener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void cancel(IRecognitionListener listener) {
            RecognitionService service = this.mServiceRef.get();
            if (service != null && service.checkPermissions(listener)) {
                service.mHandler.sendMessage(Message.obtain(service.mHandler, 3, listener));
            }
        }

        public void clearReference() {
            this.mServiceRef.clear();
        }
    }
}
