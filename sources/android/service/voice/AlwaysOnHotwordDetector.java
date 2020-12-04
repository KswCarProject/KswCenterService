package android.service.voice;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.KeyphraseEnrollmentInfo;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.app.IVoiceInteractionManagerService;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class AlwaysOnHotwordDetector {
    static final boolean DBG = false;
    public static final int MANAGE_ACTION_ENROLL = 0;
    public static final int MANAGE_ACTION_RE_ENROLL = 1;
    public static final int MANAGE_ACTION_UN_ENROLL = 2;
    private static final int MSG_AVAILABILITY_CHANGED = 1;
    private static final int MSG_DETECTION_ERROR = 3;
    private static final int MSG_DETECTION_PAUSE = 4;
    private static final int MSG_DETECTION_RESUME = 5;
    private static final int MSG_HOTWORD_DETECTED = 2;
    public static final int RECOGNITION_FLAG_ALLOW_MULTIPLE_TRIGGERS = 2;
    public static final int RECOGNITION_FLAG_CAPTURE_TRIGGER_AUDIO = 1;
    public static final int RECOGNITION_FLAG_NONE = 0;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int STATE_HARDWARE_UNAVAILABLE = -2;
    private static final int STATE_INVALID = -3;
    public static final int STATE_KEYPHRASE_ENROLLED = 2;
    public static final int STATE_KEYPHRASE_UNENROLLED = 1;
    public static final int STATE_KEYPHRASE_UNSUPPORTED = -1;
    private static final int STATE_NOT_READY = 0;
    private static final int STATUS_ERROR = Integer.MIN_VALUE;
    private static final int STATUS_OK = 0;
    static final String TAG = "AlwaysOnHotwordDetector";
    /* access modifiers changed from: private */
    public int mAvailability = 0;
    /* access modifiers changed from: private */
    public final Callback mExternalCallback;
    private final Handler mHandler;
    private final SoundTriggerListener mInternalCallback;
    private final KeyphraseEnrollmentInfo mKeyphraseEnrollmentInfo;
    /* access modifiers changed from: private */
    public final KeyphraseMetadata mKeyphraseMetadata;
    /* access modifiers changed from: private */
    public final Locale mLocale;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public final IVoiceInteractionManagerService mModelManagementService;
    private final String mText;
    /* access modifiers changed from: private */
    public final IVoiceInteractionService mVoiceInteractionService;

    public static abstract class Callback {
        public abstract void onAvailabilityChanged(int i);

        public abstract void onDetected(EventPayload eventPayload);

        public abstract void onError();

        public abstract void onRecognitionPaused();

        public abstract void onRecognitionResumed();
    }

    @Retention(RetentionPolicy.SOURCE)
    private @interface ManageActions {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RecognitionModes {
    }

    public static class EventPayload {
        private final AudioFormat mAudioFormat;
        private final boolean mCaptureAvailable;
        private final int mCaptureSession;
        private final byte[] mData;
        private final boolean mTriggerAvailable;

        private EventPayload(boolean triggerAvailable, boolean captureAvailable, AudioFormat audioFormat, int captureSession, byte[] data) {
            this.mTriggerAvailable = triggerAvailable;
            this.mCaptureAvailable = captureAvailable;
            this.mCaptureSession = captureSession;
            this.mAudioFormat = audioFormat;
            this.mData = data;
        }

        public AudioFormat getCaptureAudioFormat() {
            return this.mAudioFormat;
        }

        public byte[] getTriggerAudio() {
            if (this.mTriggerAvailable) {
                return this.mData;
            }
            return null;
        }

        @UnsupportedAppUsage
        public Integer getCaptureSession() {
            if (this.mCaptureAvailable) {
                return Integer.valueOf(this.mCaptureSession);
            }
            return null;
        }
    }

    public AlwaysOnHotwordDetector(String text, Locale locale, Callback callback, KeyphraseEnrollmentInfo keyphraseEnrollmentInfo, IVoiceInteractionService voiceInteractionService, IVoiceInteractionManagerService modelManagementService) {
        this.mText = text;
        this.mLocale = locale;
        this.mKeyphraseEnrollmentInfo = keyphraseEnrollmentInfo;
        this.mKeyphraseMetadata = this.mKeyphraseEnrollmentInfo.getKeyphraseMetadata(text, locale);
        this.mExternalCallback = callback;
        this.mHandler = new MyHandler();
        this.mInternalCallback = new SoundTriggerListener(this.mHandler);
        this.mVoiceInteractionService = voiceInteractionService;
        this.mModelManagementService = modelManagementService;
        new RefreshAvailabiltyTask().execute((Params[]) new Void[0]);
    }

    public int getSupportedRecognitionModes() {
        int supportedRecognitionModesLocked;
        synchronized (this.mLock) {
            supportedRecognitionModesLocked = getSupportedRecognitionModesLocked();
        }
        return supportedRecognitionModesLocked;
    }

    private int getSupportedRecognitionModesLocked() {
        if (this.mAvailability == -3) {
            throw new IllegalStateException("getSupportedRecognitionModes called on an invalid detector");
        } else if (this.mAvailability == 2 || this.mAvailability == 1) {
            return this.mKeyphraseMetadata.recognitionModeFlags;
        } else {
            throw new UnsupportedOperationException("Getting supported recognition modes for the keyphrase is not supported");
        }
    }

    public boolean startRecognition(int recognitionFlags) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mAvailability == -3) {
                throw new IllegalStateException("startRecognition called on an invalid detector");
            } else if (this.mAvailability == 2) {
                z = startRecognitionLocked(recognitionFlags) == 0;
            } else {
                throw new UnsupportedOperationException("Recognition for the given keyphrase is not supported");
            }
        }
        return z;
    }

    public boolean stopRecognition() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mAvailability == -3) {
                throw new IllegalStateException("stopRecognition called on an invalid detector");
            } else if (this.mAvailability == 2) {
                z = stopRecognitionLocked() == 0;
            } else {
                throw new UnsupportedOperationException("Recognition for the given keyphrase is not supported");
            }
        }
        return z;
    }

    public Intent createEnrollIntent() {
        Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(0);
        }
        return manageIntentLocked;
    }

    public Intent createUnEnrollIntent() {
        Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(2);
        }
        return manageIntentLocked;
    }

    public Intent createReEnrollIntent() {
        Intent manageIntentLocked;
        synchronized (this.mLock) {
            manageIntentLocked = getManageIntentLocked(1);
        }
        return manageIntentLocked;
    }

    private Intent getManageIntentLocked(int action) {
        if (this.mAvailability == -3) {
            throw new IllegalStateException("getManageIntent called on an invalid detector");
        } else if (this.mAvailability == 2 || this.mAvailability == 1) {
            return this.mKeyphraseEnrollmentInfo.getManageKeyphraseIntent(action, this.mText, this.mLocale);
        } else {
            throw new UnsupportedOperationException("Managing the given keyphrase is not supported");
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidate() {
        synchronized (this.mLock) {
            this.mAvailability = -3;
            notifyStateChangedLocked();
        }
    }

    /* access modifiers changed from: package-private */
    public void onSoundModelsChanged() {
        synchronized (this.mLock) {
            if (!(this.mAvailability == -3 || this.mAvailability == -2)) {
                if (this.mAvailability != -1) {
                    stopRecognitionLocked();
                    new RefreshAvailabiltyTask().execute((Params[]) new Void[0]);
                    return;
                }
            }
            Slog.w(TAG, "Received onSoundModelsChanged for an unsupported keyphrase/config");
        }
    }

    private int startRecognitionLocked(int recognitionFlags) {
        boolean allowMultipleTriggers = true;
        SoundTrigger.KeyphraseRecognitionExtra[] recognitionExtra = {new SoundTrigger.KeyphraseRecognitionExtra(this.mKeyphraseMetadata.id, this.mKeyphraseMetadata.recognitionModeFlags, 0, new SoundTrigger.ConfidenceLevel[0])};
        boolean captureTriggerAudio = (recognitionFlags & 1) != 0;
        if ((recognitionFlags & 2) == 0) {
            allowMultipleTriggers = false;
        }
        int code = Integer.MIN_VALUE;
        try {
            code = this.mModelManagementService.startRecognition(this.mVoiceInteractionService, this.mKeyphraseMetadata.id, this.mLocale.toLanguageTag(), this.mInternalCallback, new SoundTrigger.RecognitionConfig(captureTriggerAudio, allowMultipleTriggers, recognitionExtra, (byte[]) null));
        } catch (RemoteException e) {
            Slog.w(TAG, "RemoteException in startRecognition!", e);
        }
        if (code != 0) {
            Slog.w(TAG, "startRecognition() failed with error code " + code);
        }
        return code;
    }

    private int stopRecognitionLocked() {
        int code = Integer.MIN_VALUE;
        try {
            code = this.mModelManagementService.stopRecognition(this.mVoiceInteractionService, this.mKeyphraseMetadata.id, this.mInternalCallback);
        } catch (RemoteException e) {
            Slog.w(TAG, "RemoteException in stopRecognition!", e);
        }
        if (code != 0) {
            Slog.w(TAG, "stopRecognition() failed with error code " + code);
        }
        return code;
    }

    /* access modifiers changed from: private */
    public void notifyStateChangedLocked() {
        Message message = Message.obtain(this.mHandler, 1);
        message.arg1 = this.mAvailability;
        message.sendToTarget();
    }

    static final class SoundTriggerListener extends IRecognitionStatusCallback.Stub {
        private final Handler mHandler;

        public SoundTriggerListener(Handler handler) {
            this.mHandler = handler;
        }

        public void onKeyphraseDetected(SoundTrigger.KeyphraseRecognitionEvent event) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onDetected");
            Message.obtain(this.mHandler, 2, new EventPayload(event.triggerInData, event.captureAvailable, event.captureFormat, event.captureSession, event.data)).sendToTarget();
        }

        public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent event) {
            Slog.w(AlwaysOnHotwordDetector.TAG, "Generic sound trigger event detected at AOHD: " + event);
        }

        public void onError(int status) {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onError: " + status);
            this.mHandler.sendEmptyMessage(3);
        }

        public void onRecognitionPaused() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRecognitionPaused");
            this.mHandler.sendEmptyMessage(4);
        }

        public void onRecognitionResumed() {
            Slog.i(AlwaysOnHotwordDetector.TAG, "onRecognitionResumed");
            this.mHandler.sendEmptyMessage(5);
        }
    }

    class MyHandler extends Handler {
        MyHandler() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0032, code lost:
            switch(r5.what) {
                case 1: goto L_0x0065;
                case 2: goto L_0x0057;
                case 3: goto L_0x004d;
                case 4: goto L_0x0043;
                case 5: goto L_0x0039;
                default: goto L_0x0035;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0035, code lost:
            super.handleMessage(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0039, code lost:
            android.service.voice.AlwaysOnHotwordDetector.access$300(r4.this$0).onRecognitionResumed();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0043, code lost:
            android.service.voice.AlwaysOnHotwordDetector.access$300(r4.this$0).onRecognitionPaused();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004d, code lost:
            android.service.voice.AlwaysOnHotwordDetector.access$300(r4.this$0).onError();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0057, code lost:
            android.service.voice.AlwaysOnHotwordDetector.access$300(r4.this$0).onDetected((android.service.voice.AlwaysOnHotwordDetector.EventPayload) r5.obj);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0065, code lost:
            android.service.voice.AlwaysOnHotwordDetector.access$300(r4.this$0).onAvailabilityChanged(r5.arg1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r5) {
            /*
                r4 = this;
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                android.service.voice.AlwaysOnHotwordDetector r1 = android.service.voice.AlwaysOnHotwordDetector.this     // Catch:{ all -> 0x0072 }
                int r1 = r1.mAvailability     // Catch:{ all -> 0x0072 }
                r2 = -3
                if (r1 != r2) goto L_0x002f
                java.lang.String r1 = "AlwaysOnHotwordDetector"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0072 }
                r2.<init>()     // Catch:{ all -> 0x0072 }
                java.lang.String r3 = "Received message: "
                r2.append(r3)     // Catch:{ all -> 0x0072 }
                int r3 = r5.what     // Catch:{ all -> 0x0072 }
                r2.append(r3)     // Catch:{ all -> 0x0072 }
                java.lang.String r3 = " for an invalid detector"
                r2.append(r3)     // Catch:{ all -> 0x0072 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0072 }
                android.util.Slog.w((java.lang.String) r1, (java.lang.String) r2)     // Catch:{ all -> 0x0072 }
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                return
            L_0x002f:
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                int r0 = r5.what
                switch(r0) {
                    case 1: goto L_0x0065;
                    case 2: goto L_0x0057;
                    case 3: goto L_0x004d;
                    case 4: goto L_0x0043;
                    case 5: goto L_0x0039;
                    default: goto L_0x0035;
                }
            L_0x0035:
                super.handleMessage(r5)
                goto L_0x0071
            L_0x0039:
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                android.service.voice.AlwaysOnHotwordDetector$Callback r0 = r0.mExternalCallback
                r0.onRecognitionResumed()
                goto L_0x0071
            L_0x0043:
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                android.service.voice.AlwaysOnHotwordDetector$Callback r0 = r0.mExternalCallback
                r0.onRecognitionPaused()
                goto L_0x0071
            L_0x004d:
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                android.service.voice.AlwaysOnHotwordDetector$Callback r0 = r0.mExternalCallback
                r0.onError()
                goto L_0x0071
            L_0x0057:
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                android.service.voice.AlwaysOnHotwordDetector$Callback r0 = r0.mExternalCallback
                java.lang.Object r1 = r5.obj
                android.service.voice.AlwaysOnHotwordDetector$EventPayload r1 = (android.service.voice.AlwaysOnHotwordDetector.EventPayload) r1
                r0.onDetected(r1)
                goto L_0x0071
            L_0x0065:
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                android.service.voice.AlwaysOnHotwordDetector$Callback r0 = r0.mExternalCallback
                int r1 = r5.arg1
                r0.onAvailabilityChanged(r1)
            L_0x0071:
                return
            L_0x0072:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0072 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.voice.AlwaysOnHotwordDetector.MyHandler.handleMessage(android.os.Message):void");
        }
    }

    class RefreshAvailabiltyTask extends AsyncTask<Void, Void, Void> {
        RefreshAvailabiltyTask() {
        }

        public Void doInBackground(Void... params) {
            int availability = internalGetInitialAvailability();
            if (availability == 0 || availability == 1 || availability == 2) {
                if (!internalGetIsEnrolled(AlwaysOnHotwordDetector.this.mKeyphraseMetadata.id, AlwaysOnHotwordDetector.this.mLocale)) {
                    availability = 1;
                } else {
                    availability = 2;
                }
            }
            synchronized (AlwaysOnHotwordDetector.this.mLock) {
                int unused = AlwaysOnHotwordDetector.this.mAvailability = availability;
                AlwaysOnHotwordDetector.this.notifyStateChangedLocked();
            }
            return null;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
            r0 = android.service.voice.AlwaysOnHotwordDetector.access$800(r4.this$0).getDspModuleProperties(android.service.voice.AlwaysOnHotwordDetector.access$700(r4.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0026, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
            android.util.Slog.w(android.service.voice.AlwaysOnHotwordDetector.TAG, "RemoteException in getDspProperties!", r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
            r0 = null;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int internalGetInitialAvailability() {
            /*
                r4 = this;
                android.service.voice.AlwaysOnHotwordDetector r0 = android.service.voice.AlwaysOnHotwordDetector.this
                java.lang.Object r0 = r0.mLock
                monitor-enter(r0)
                android.service.voice.AlwaysOnHotwordDetector r1 = android.service.voice.AlwaysOnHotwordDetector.this     // Catch:{ all -> 0x003e }
                int r1 = r1.mAvailability     // Catch:{ all -> 0x003e }
                r2 = -3
                if (r1 != r2) goto L_0x0012
                monitor-exit(r0)     // Catch:{ all -> 0x003e }
                return r2
            L_0x0012:
                monitor-exit(r0)     // Catch:{ all -> 0x003e }
                r0 = 0
                android.service.voice.AlwaysOnHotwordDetector r1 = android.service.voice.AlwaysOnHotwordDetector.this     // Catch:{ RemoteException -> 0x0026 }
                com.android.internal.app.IVoiceInteractionManagerService r1 = r1.mModelManagementService     // Catch:{ RemoteException -> 0x0026 }
                android.service.voice.AlwaysOnHotwordDetector r2 = android.service.voice.AlwaysOnHotwordDetector.this     // Catch:{ RemoteException -> 0x0026 }
                android.service.voice.IVoiceInteractionService r2 = r2.mVoiceInteractionService     // Catch:{ RemoteException -> 0x0026 }
                android.hardware.soundtrigger.SoundTrigger$ModuleProperties r1 = r1.getDspModuleProperties(r2)     // Catch:{ RemoteException -> 0x0026 }
                r0 = r1
                goto L_0x002e
            L_0x0026:
                r1 = move-exception
                java.lang.String r2 = "AlwaysOnHotwordDetector"
                java.lang.String r3 = "RemoteException in getDspProperties!"
                android.util.Slog.w(r2, r3, r1)
            L_0x002e:
                if (r0 != 0) goto L_0x0032
                r1 = -2
                return r1
            L_0x0032:
                android.service.voice.AlwaysOnHotwordDetector r1 = android.service.voice.AlwaysOnHotwordDetector.this
                android.hardware.soundtrigger.KeyphraseMetadata r1 = r1.mKeyphraseMetadata
                if (r1 != 0) goto L_0x003c
                r1 = -1
                return r1
            L_0x003c:
                r1 = 0
                return r1
            L_0x003e:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x003e }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.service.voice.AlwaysOnHotwordDetector.RefreshAvailabiltyTask.internalGetInitialAvailability():int");
        }

        private boolean internalGetIsEnrolled(int keyphraseId, Locale locale) {
            try {
                return AlwaysOnHotwordDetector.this.mModelManagementService.isEnrolledForKeyphrase(AlwaysOnHotwordDetector.this.mVoiceInteractionService, keyphraseId, locale.toLanguageTag());
            } catch (RemoteException e) {
                Slog.w(AlwaysOnHotwordDetector.TAG, "RemoteException in listRegisteredKeyphraseSoundModels!", e);
                return false;
            }
        }
    }

    public void dump(String prefix, PrintWriter pw) {
        synchronized (this.mLock) {
            pw.print(prefix);
            pw.print("Text=");
            pw.println(this.mText);
            pw.print(prefix);
            pw.print("Locale=");
            pw.println(this.mLocale);
            pw.print(prefix);
            pw.print("Availability=");
            pw.println(this.mAvailability);
            pw.print(prefix);
            pw.print("KeyphraseMetadata=");
            pw.println(this.mKeyphraseMetadata);
            pw.print(prefix);
            pw.print("EnrollmentInfo=");
            pw.println(this.mKeyphraseEnrollmentInfo);
        }
    }
}
