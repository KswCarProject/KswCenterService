package android.media;

import android.media.AudioManager;
import android.media.IAudioService;
import android.media.IRecordingConfigDispatcher;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.annotations.GuardedBy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class AudioRecordingMonitorImpl implements AudioRecordingMonitor {
    private static final int MSG_RECORDING_CONFIG_CHANGE = 1;
    private static final String TAG = "android.media.AudioRecordingMonitor";
    private static IAudioService sService;
    private final AudioRecordingMonitorClient mClient;
    /* access modifiers changed from: private */
    @GuardedBy({"mRecordCallbackLock"})
    public LinkedList<AudioRecordingCallbackInfo> mRecordCallbackList = new LinkedList<>();
    /* access modifiers changed from: private */
    public final Object mRecordCallbackLock = new Object();
    @GuardedBy({"mRecordCallbackLock"})
    private final IRecordingConfigDispatcher mRecordingCallback = new IRecordingConfigDispatcher.Stub() {
        public void dispatchRecordingConfigChange(List<AudioRecordingConfiguration> configs) {
            AudioRecordingConfiguration config = AudioRecordingMonitorImpl.this.getMyConfig(configs);
            if (config != null) {
                synchronized (AudioRecordingMonitorImpl.this.mRecordCallbackLock) {
                    if (AudioRecordingMonitorImpl.this.mRecordingCallbackHandler != null) {
                        AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.sendMessage(AudioRecordingMonitorImpl.this.mRecordingCallbackHandler.obtainMessage(1, config));
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    @GuardedBy({"mRecordCallbackLock"})
    public volatile Handler mRecordingCallbackHandler;
    @GuardedBy({"mRecordCallbackLock"})
    private HandlerThread mRecordingCallbackHandlerThread;

    AudioRecordingMonitorImpl(AudioRecordingMonitorClient client) {
        this.mClient = client;
    }

    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback cb) {
        if (cb == null) {
            throw new IllegalArgumentException("Illegal null AudioRecordingCallback");
        } else if (executor != null) {
            synchronized (this.mRecordCallbackLock) {
                Iterator it = this.mRecordCallbackList.iterator();
                while (it.hasNext()) {
                    if (((AudioRecordingCallbackInfo) it.next()).mCb == cb) {
                        throw new IllegalArgumentException("AudioRecordingCallback already registered");
                    }
                }
                beginRecordingCallbackHandling();
                this.mRecordCallbackList.add(new AudioRecordingCallbackInfo(executor, cb));
            }
        } else {
            throw new IllegalArgumentException("Illegal null Executor");
        }
    }

    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback cb) {
        if (cb != null) {
            synchronized (this.mRecordCallbackLock) {
                Iterator it = this.mRecordCallbackList.iterator();
                while (it.hasNext()) {
                    AudioRecordingCallbackInfo arci = (AudioRecordingCallbackInfo) it.next();
                    if (arci.mCb == cb) {
                        this.mRecordCallbackList.remove(arci);
                        if (this.mRecordCallbackList.size() == 0) {
                            endRecordingCallbackHandling();
                        }
                    }
                }
                throw new IllegalArgumentException("AudioRecordingCallback was not registered");
            }
            return;
        }
        throw new IllegalArgumentException("Illegal null AudioRecordingCallback argument");
    }

    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        try {
            return getMyConfig(getService().getActiveRecordingConfigurations());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class AudioRecordingCallbackInfo {
        final AudioManager.AudioRecordingCallback mCb;
        final Executor mExecutor;

        AudioRecordingCallbackInfo(Executor e, AudioManager.AudioRecordingCallback cb) {
            this.mExecutor = e;
            this.mCb = cb;
        }
    }

    @GuardedBy({"mRecordCallbackLock"})
    private void beginRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread == null) {
            this.mRecordingCallbackHandlerThread = new HandlerThread("android.media.AudioRecordingMonitor.RecordingCallback");
            this.mRecordingCallbackHandlerThread.start();
            Looper looper = this.mRecordingCallbackHandlerThread.getLooper();
            if (looper != null) {
                this.mRecordingCallbackHandler = new Handler(looper) {
                    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0050, code lost:
                        r3 = android.os.Binder.clearCallingIdentity();
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
                        r1 = r2.iterator();
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:19:0x005c, code lost:
                        if (r1.hasNext() == false) goto L_0x006f;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005e, code lost:
                        r5 = (android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo) r1.next();
                        r5.mExecutor.execute(new android.media.$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr_fiLO_SMYka7I(r5, r0));
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0075, code lost:
                        r1 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0076, code lost:
                        android.os.Binder.restoreCallingIdentity(r3);
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0079, code lost:
                        throw r1;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
                        return;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void handleMessage(android.os.Message r9) {
                        /*
                            r8 = this;
                            int r0 = r9.what
                            r1 = 1
                            if (r0 == r1) goto L_0x001e
                            java.lang.String r0 = "android.media.AudioRecordingMonitor"
                            java.lang.StringBuilder r1 = new java.lang.StringBuilder
                            r1.<init>()
                            java.lang.String r2 = "Unknown event "
                            r1.append(r2)
                            int r2 = r9.what
                            r1.append(r2)
                            java.lang.String r1 = r1.toString()
                            android.util.Log.e(r0, r1)
                            goto L_0x0074
                        L_0x001e:
                            java.lang.Object r0 = r9.obj
                            if (r0 != 0) goto L_0x0023
                            return
                        L_0x0023:
                            java.util.ArrayList r0 = new java.util.ArrayList
                            r0.<init>()
                            java.lang.Object r1 = r9.obj
                            android.media.AudioRecordingConfiguration r1 = (android.media.AudioRecordingConfiguration) r1
                            r0.add(r1)
                            android.media.AudioRecordingMonitorImpl r1 = android.media.AudioRecordingMonitorImpl.this
                            java.lang.Object r1 = r1.mRecordCallbackLock
                            monitor-enter(r1)
                            android.media.AudioRecordingMonitorImpl r2 = android.media.AudioRecordingMonitorImpl.this     // Catch:{ all -> 0x007a }
                            java.util.LinkedList r2 = r2.mRecordCallbackList     // Catch:{ all -> 0x007a }
                            int r2 = r2.size()     // Catch:{ all -> 0x007a }
                            if (r2 != 0) goto L_0x0044
                            monitor-exit(r1)     // Catch:{ all -> 0x007a }
                            return
                        L_0x0044:
                            java.util.LinkedList r2 = new java.util.LinkedList     // Catch:{ all -> 0x007a }
                            android.media.AudioRecordingMonitorImpl r3 = android.media.AudioRecordingMonitorImpl.this     // Catch:{ all -> 0x007a }
                            java.util.LinkedList r3 = r3.mRecordCallbackList     // Catch:{ all -> 0x007a }
                            r2.<init>(r3)     // Catch:{ all -> 0x007a }
                            monitor-exit(r1)     // Catch:{ all -> 0x007a }
                            long r3 = android.os.Binder.clearCallingIdentity()
                            java.util.Iterator r1 = r2.iterator()     // Catch:{ all -> 0x0075 }
                        L_0x0058:
                            boolean r5 = r1.hasNext()     // Catch:{ all -> 0x0075 }
                            if (r5 == 0) goto L_0x006f
                            java.lang.Object r5 = r1.next()     // Catch:{ all -> 0x0075 }
                            android.media.AudioRecordingMonitorImpl$AudioRecordingCallbackInfo r5 = (android.media.AudioRecordingMonitorImpl.AudioRecordingCallbackInfo) r5     // Catch:{ all -> 0x0075 }
                            java.util.concurrent.Executor r6 = r5.mExecutor     // Catch:{ all -> 0x0075 }
                            android.media.-$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr-_fiLO_SMYka7I r7 = new android.media.-$$Lambda$AudioRecordingMonitorImpl$2$cn04v8rie0OYr-_fiLO_SMYka7I     // Catch:{ all -> 0x0075 }
                            r7.<init>(r0)     // Catch:{ all -> 0x0075 }
                            r6.execute(r7)     // Catch:{ all -> 0x0075 }
                            goto L_0x0058
                        L_0x006f:
                            android.os.Binder.restoreCallingIdentity(r3)
                        L_0x0074:
                            return
                        L_0x0075:
                            r1 = move-exception
                            android.os.Binder.restoreCallingIdentity(r3)
                            throw r1
                        L_0x007a:
                            r2 = move-exception
                            monitor-exit(r1)     // Catch:{ all -> 0x007a }
                            throw r2
                        */
                        throw new UnsupportedOperationException("Method not decompiled: android.media.AudioRecordingMonitorImpl.AnonymousClass2.handleMessage(android.os.Message):void");
                    }
                };
                try {
                    getService().registerRecordingCallback(this.mRecordingCallback);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @GuardedBy({"mRecordCallbackLock"})
    private void endRecordingCallbackHandling() {
        if (this.mRecordingCallbackHandlerThread != null) {
            try {
                getService().unregisterRecordingCallback(this.mRecordingCallback);
                this.mRecordingCallbackHandlerThread.quit();
                this.mRecordingCallbackHandlerThread = null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public AudioRecordingConfiguration getMyConfig(List<AudioRecordingConfiguration> configs) {
        int portId = this.mClient.getPortId();
        for (AudioRecordingConfiguration config : configs) {
            if (config.getClientPortId() == portId) {
                return config;
            }
        }
        return null;
    }

    private static IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        return sService;
    }
}
