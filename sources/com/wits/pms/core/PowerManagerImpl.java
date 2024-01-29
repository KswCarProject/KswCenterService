package com.wits.pms.core;

import android.content.Context;
import android.p007os.DeadObjectException;
import android.p007os.Handler;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteCallbackList;
import android.p007os.RemoteException;
import android.p007os.ResultReceiver;
import android.p007os.ShellCallback;
import android.provider.BrowserContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.wits.pms.ICmdListener;
import com.wits.pms.IContentObserver;
import com.wits.pms.IPowerManagerAppService;
import com.wits.pms.core.PowerManagerImpl;
import com.wits.pms.custom.KswSettings;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.McuStatus;
import com.wits.pms.statuscontrol.MusicStatus;
import com.wits.pms.statuscontrol.SystemStatus;
import com.wits.pms.statuscontrol.VideoStatus;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.statuscontrol.WitsStatus;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class PowerManagerImpl extends IPowerManagerAppService.Stub {
    private static final int MSG_BROADCAST_FIELD = 3;
    private static final int MSG_CMD = 1;
    private static final int MSG_UPDATE_INFO = 2;
    private static final int OBSCHANGE = 2;
    private static final int SENDCOMMAND = 1;
    private static final String TAG = "IPowerManagerAppService";
    private static final int UPDATESTATUS = 3;
    private Handler mHandler;
    private VideoStatus mVideoStatus;
    private final RemoteCallbackList<ICmdListener> cmdListeners = new RemoteCallbackList<>();
    private final Object sendCmdLock = new Object();
    private final Object updateInfoLock = new Object();
    private HashMap<String, List<IContentObserver>> mObserverMap = new HashMap<>();
    private final Object obsLock = new Object();
    private HashMap<String, Object> mFieldMap = new HashMap<>();
    private final Object mFiledLock = new Object();
    private SystemStatus mSystemStatus = new SystemStatus();
    private BtPhoneStatus mBtPhoneStatus = new BtPhoneStatus(false, false, "", 0, 0, true);
    private MusicStatus mMusicStatus = new MusicStatus("", "", false, 0);
    private McuStatus mMcuStatus = new McuStatus(1, "-1");

    public PowerManagerImpl(Context context) {
        saveSystem(this.mSystemStatus);
        saveBt(this.mBtPhoneStatus);
        saveMusic(this.mMusicStatus);
        saveMcu(this.mMcuStatus);
        setUpHandler(context);
    }

    /* renamed from: com.wits.pms.core.PowerManagerImpl$1 */
    /* loaded from: classes2.dex */
    class HandlerC36001 extends Handler {
        HandlerC36001(Looper x0) {
            super(x0);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            final String jsonMsg = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    PowerManagerImpl.this.goSendCommand(jsonMsg);
                    return;
                case 2:
                    synchronized (PowerManagerImpl.this.updateInfoLock) {
                        int length = PowerManagerImpl.this.cmdListeners.beginBroadcast();
                        for (int i = 0; i < length; i++) {
                            final ICmdListener listener = (ICmdListener) PowerManagerImpl.this.cmdListeners.getBroadcastItem(i);
                            if (listener != null) {
                                PowerManagerImpl.this.mHandler.post(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$PowerManagerImpl$1$KEtKBLBYATR10qE_6I2rRcqYZHA
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        PowerManagerImpl.HandlerC36001.lambda$handleMessage$0(ICmdListener.this, jsonMsg);
                                    }
                                });
                            }
                        }
                        Log.m68i(PowerManagerImpl.TAG, "sendStatus Msg: " + jsonMsg);
                        PowerManagerImpl.this.cmdListeners.finishBroadcast();
                    }
                    return;
                case 3:
                    PowerManagerImpl.this.broadcastField((String) msg.obj);
                    return;
                default:
                    return;
            }
        }

        static /* synthetic */ void lambda$handleMessage$0(ICmdListener listener, String jsonMsg) {
            try {
                listener.updateStatusInfo(jsonMsg);
            } catch (Exception e) {
                Log.m70e(PowerManagerImpl.TAG, "PMAS handleCommand error!!!");
            }
        }
    }

    private void setUpHandler(Context context) {
        this.mHandler = new HandlerC36001(context.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean goSendCommand(String json) {
        boolean result = false;
        synchronized (this.sendCmdLock) {
            int length = this.cmdListeners.beginBroadcast();
            for (int i = 0; i < length; i++) {
                ICmdListener listener = this.cmdListeners.getBroadcastItem(i);
                if (listener != null) {
                    try {
                        result |= listener.handleCommand(json);
                    } catch (Exception e) {
                        Log.m69e(TAG, "PMAS handleCommand error!!!", e);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("sendCommand:");
            sb.append(result ? "success" : "failed");
            sb.append("\ncommand: ");
            sb.append(json);
            Log.m68i(TAG, sb.toString());
            this.cmdListeners.finishBroadcast();
        }
        return result;
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public synchronized boolean sendCommand(String jsonMsg) throws RemoteException {
        if (WitsCommand.getWitsCommandFormJson(jsonMsg).isNeedResult()) {
            return goSendCommand(jsonMsg);
        }
        this.mHandler.obtainMessage(1, jsonMsg).sendToTarget();
        return true;
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public synchronized boolean sendStatus(String json) throws RemoteException {
        updateStatusInfo(json);
        return true;
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void registerCmdListener(ICmdListener listener) throws RemoteException {
        synchronized (this.cmdListeners) {
            if (listener != null) {
                try {
                    this.cmdListeners.register(listener);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void unregisterCmdListener(ICmdListener listener) throws RemoteException {
        synchronized (this.cmdListeners) {
            if (listener != null) {
                try {
                    this.cmdListeners.unregister(listener);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private void updateStatusInfo(String jsonMsg) {
        boolean hasChange = false;
        try {
            hasChange = handlerStatus(WitsStatus.getWitsStatusFormJson(jsonMsg)) != 0;
        } catch (Exception e) {
            Log.m69e(TAG, "sendStatus:failed\nmsg: " + jsonMsg, e);
        }
        if (hasChange) {
            this.mHandler.obtainMessage(2, jsonMsg).sendToTarget();
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public synchronized void registerObserver(String key, IContentObserver observer) throws RemoteException {
        synchronized (this.obsLock) {
            Log.m68i(TAG, "registerObserver key:" + key + " - observer:" + observer);
            List<IContentObserver> list = this.mObserverMap.get(key);
            List<IContentObserver> contentObservers = list;
            if (list == null) {
                contentObservers = new ArrayList<>();
            }
            contentObservers.add(observer);
            this.mObserverMap.put(key, contentObservers);
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void unregisterObserver(IContentObserver observer) throws RemoteException {
        synchronized (this.obsLock) {
            for (List<IContentObserver> list : this.mObserverMap.values()) {
                for (IContentObserver contentObserver : list) {
                    if (observer.equals(contentObserver)) {
                        list.remove(contentObserver);
                        return;
                    }
                }
            }
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public int getSettingsInt(String key) throws RemoteException {
        try {
            return KswSettings.getSettings().getSettingsInt(key);
        } catch (Settings.SettingNotFoundException e) {
            Log.m68i(TAG, "getSettingsInt settings not found");
            return 0;
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public String getSettingsString(String key) throws RemoteException {
        return KswSettings.getSettings().getSettingsString(key);
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void setSettingsInt(String key, int value) throws RemoteException {
        KswSettings.getSettings().setInt(key, value);
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void setSettingsString(String key, String value) throws RemoteException {
        KswSettings.getSettings().setString(key, value);
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public boolean getStatusBoolean(String key) throws RemoteException {
        try {
            return ((Boolean) getField(key)).booleanValue();
        } catch (Exception e) {
            Log.m69e(TAG, "cannot cast key :" + key, e);
            return false;
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public int getStatusInt(String key) throws RemoteException {
        try {
            return ((Integer) getField(key)).intValue();
        } catch (Exception e) {
            throw new RemoteException("cannot cast");
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public String getStatusString(String key) throws RemoteException {
        try {
            return (String) getField(key);
        } catch (Exception e) {
            throw new RemoteException("cannot cast");
        }
    }

    private void save(WitsStatus witsStatus) {
        int i = witsStatus.type;
        if (i == 1) {
            SystemStatus systemStatus = SystemStatus.getStatusFormJson(witsStatus.getJsonArg());
            saveSystem(systemStatus);
        } else if (i == 3) {
            BtPhoneStatus btPhoneStatus = BtPhoneStatus.getStatusForJson(witsStatus.getJsonArg());
            saveBt(btPhoneStatus);
        } else if (i == 5) {
            McuStatus mcuStatus = McuStatus.getStatusFromJson(witsStatus.getJsonArg());
            saveMcu(mcuStatus);
        } else {
            switch (i) {
                case 21:
                    MusicStatus musicStatus = MusicStatus.getStatusFromJson(witsStatus.getJsonArg());
                    saveMusic(musicStatus);
                    return;
                case 22:
                    VideoStatus videoStatus = VideoStatus.getStatusFromJson(witsStatus.getJsonArg());
                    saveVideo(videoStatus);
                    return;
                default:
                    return;
            }
        }
    }

    private void saveMcu(McuStatus mcuStatus) {
        saveField("benzData", mcuStatus.benzData.getJson(), false);
        saveField("acData", mcuStatus.acData.getJson(), false);
        saveField("carData", mcuStatus.carData.getJson(), false);
        saveField("carGear", Integer.valueOf(mcuStatus.carData.carGear), false);
        saveField("signalLeft", Integer.valueOf(mcuStatus.carData.signalLeft), false);
        saveField("signalDouble", Integer.valueOf(mcuStatus.carData.signalDouble), false);
        saveField("signalRight", Integer.valueOf(mcuStatus.carData.signalRight), false);
        saveField("carDoor", Integer.valueOf(mcuStatus.carData.carDoor), false);
        saveField("carWheelAngle", Integer.valueOf(mcuStatus.carData.carWheelAngle), false);
        saveField("frontRadarDataL", Integer.valueOf(mcuStatus.carData.frontRadarDataL), false);
        saveField("frontRadarDataLM", Integer.valueOf(mcuStatus.carData.frontRadarDataLM), false);
        saveField("frontRadarDataRM", Integer.valueOf(mcuStatus.carData.frontRadarDataRM), false);
        saveField("frontRadarDataR", Integer.valueOf(mcuStatus.carData.frontRadarDataR), false);
        saveField("backRadarDataL", Integer.valueOf(mcuStatus.carData.backRadarDataL), false);
        saveField("backRadarDataLM", Integer.valueOf(mcuStatus.carData.backRadarDataLM), false);
        saveField("backRadarDataRM", Integer.valueOf(mcuStatus.carData.backRadarDataRM), false);
        saveField("backRadarDataR", Integer.valueOf(mcuStatus.carData.backRadarDataR), false);
        saveField("mcuVerison", mcuStatus.mcuVerison, false);
        saveField("systemMode", Integer.valueOf(mcuStatus.systemMode), false);
    }

    private void saveSystem(SystemStatus systemStatus) {
        saveField("topApp", systemStatus.topApp, false);
        saveField("screenSwitch", Integer.valueOf(systemStatus.screenSwitch), false);
        saveField("lastMode", Integer.valueOf(systemStatus.lastMode), false);
        saveField("ill", Integer.valueOf(systemStatus.ill), false);
        saveField("acc", Integer.valueOf(systemStatus.acc), false);
        saveField("epb", Integer.valueOf(systemStatus.epb), false);
        saveField("ccd", Integer.valueOf(systemStatus.ccd), false);
        saveField("dormant", Boolean.valueOf(systemStatus.dormant), false);
        saveField("rlight", Integer.valueOf(systemStatus.rlight), false);
    }

    private void saveBt(BtPhoneStatus btPhoneStatus) {
        saveField("btSwitch", Boolean.valueOf(btPhoneStatus.btSwitch), false);
        saveField("callStatus", Integer.valueOf(btPhoneStatus.callStatus), false);
        saveField("voiceStatus", Integer.valueOf(btPhoneStatus.voiceStatus), false);
        saveField("devAddr", btPhoneStatus.devAddr, false);
        saveField("isConnected", Boolean.valueOf(btPhoneStatus.isConnected), false);
        saveField("isPlayingMusic", Boolean.valueOf(btPhoneStatus.isPlayingMusic), false);
        saveField("connectedAddr", btPhoneStatus.connectedAddr, false);
    }

    private void saveMusic(MusicStatus musicStatus) {
        saveField("mode", musicStatus.mode, false);
        saveField("path", musicStatus.path, false);
        saveField("play", Boolean.valueOf(musicStatus.play), false);
        saveField(BrowserContract.Bookmarks.POSITION, Integer.valueOf(musicStatus.position), false);
    }

    private void saveVideo(VideoStatus videoStatus) {
        saveField("videoMode", videoStatus.getMode(), false);
        saveField("videoPath", videoStatus.getPath(), false);
        saveField("videoPlay", Boolean.valueOf(videoStatus.isPlay()), false);
        saveField("videoPosition", Integer.valueOf(videoStatus.getPosition()), false);
        saveField("videoMask", Boolean.valueOf(videoStatus.isMask()), false);
    }

    private int handlerStatus(WitsStatus witsStatus) {
        final List<String> compares = new ArrayList<>();
        int i = witsStatus.type;
        if (i == 1) {
            SystemStatus systemStatus = SystemStatus.getStatusFormJson(witsStatus.getJsonArg());
            if (this.mSystemStatus != null) {
                compares.addAll(this.mSystemStatus.compare(systemStatus));
                if (compares.size() > 0) {
                    Log.m68i(TAG, "mSystemStatus has change status - " + compares.size());
                }
            }
            this.mSystemStatus = systemStatus;
        } else if (i == 3) {
            BtPhoneStatus btPhoneStatus = BtPhoneStatus.getStatusForJson(witsStatus.getJsonArg());
            if (this.mBtPhoneStatus != null) {
                compares.addAll(this.mBtPhoneStatus.compare(btPhoneStatus));
                if (compares.size() > 0) {
                    Log.m68i(TAG, "mBtPhoneStatus has change status - " + compares.size());
                }
            }
            this.mBtPhoneStatus = btPhoneStatus;
        } else if (i == 5) {
            McuStatus mcuStatus = McuStatus.getStatusFromJson(witsStatus.getJsonArg());
            if (this.mMcuStatus != null) {
                compares.addAll(this.mMcuStatus.compare(mcuStatus));
                if (compares.size() > 0) {
                    Log.m68i(TAG, "mMcuStatus has change status - " + compares.size());
                }
            }
            this.mMcuStatus = mcuStatus;
        } else {
            switch (i) {
                case 21:
                    MusicStatus musicStatus = MusicStatus.getStatusFromJson(witsStatus.getJsonArg());
                    if (this.mMusicStatus != null) {
                        compares.addAll(this.mMusicStatus.compare(musicStatus));
                        if (compares.size() > 0) {
                            Log.m68i(TAG, "mMusicStatus has change status - " + compares.size());
                        }
                    }
                    this.mMusicStatus = musicStatus;
                    break;
                case 22:
                    VideoStatus videoStatus = VideoStatus.getStatusFromJson(witsStatus.getJsonArg());
                    if (this.mVideoStatus != null) {
                        compares.addAll(this.mVideoStatus.compare(videoStatus));
                        if (compares.size() > 0) {
                            Log.m68i(TAG, "mVideoStatus has change status - " + compares.size());
                        }
                    } else {
                        List<String> keys = new ArrayList<>();
                        keys.add("path");
                        keys.add("mode");
                        keys.add("play");
                        keys.add(BrowserContract.Bookmarks.POSITION);
                        keys.add("mask");
                        compares.addAll(keys);
                    }
                    this.mVideoStatus = videoStatus;
                    break;
            }
        }
        save(witsStatus);
        this.mHandler.post(new Runnable() { // from class: com.wits.pms.core.-$$Lambda$PowerManagerImpl$UR5vrQgBwn-HAp-HthweZ0GF2Ns
            @Override // java.lang.Runnable
            public final void run() {
                PowerManagerImpl.lambda$handlerStatus$0(PowerManagerImpl.this, compares);
            }
        });
        return compares.size();
    }

    public static /* synthetic */ void lambda$handlerStatus$0(PowerManagerImpl powerManagerImpl, List compares) {
        Iterator it = compares.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            synchronized (powerManagerImpl.obsLock) {
                if (powerManagerImpl.mObserverMap.get(key) != null) {
                    List<IContentObserver> deadObs = new ArrayList<>();
                    for (IContentObserver observer : powerManagerImpl.mObserverMap.get(key)) {
                        Log.m68i(TAG, "IContentObserver - Key:" + key + " - " + observer);
                        if (observer != null) {
                            try {
                                observer.onChange();
                            } catch (Exception e) {
                                if (e instanceof DeadObjectException) {
                                    deadObs.add(observer);
                                }
                            }
                        }
                    }
                    for (IContentObserver observer2 : deadObs) {
                        try {
                            powerManagerImpl.mObserverMap.get(key).remove(observer2);
                            Log.m70e(TAG, "error onChange key:" + key);
                        } catch (Exception e2) {
                        }
                    }
                }
            }
        }
    }

    @Override // android.p007os.Binder
    public void onShellCommand(FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, ResultReceiver resultReceiver) throws RemoteException {
        new PowerManagerAppCommand(this).exec(this, in, out, err, args, callback, resultReceiver);
    }

    @Override // android.p007os.Binder
    public void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.wits.pms.core.PowerManagerImpl$2] */
    public void updateMcuJsonStatus(final String key, String value) {
        saveField(key, value, false);
        new Thread() { // from class: com.wits.pms.core.PowerManagerImpl.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (PowerManagerImpl.this.obsLock) {
                    if (PowerManagerImpl.this.mObserverMap.get(key) != null) {
                        List<IContentObserver> deadObs = new ArrayList<>();
                        for (IContentObserver observer : (List) PowerManagerImpl.this.mObserverMap.get(key)) {
                            Log.m68i(PowerManagerImpl.TAG, "IContentObserver - Key:" + key + " - " + observer);
                            if (observer != null) {
                                try {
                                    observer.onChange();
                                } catch (Exception e) {
                                    if (e instanceof DeadObjectException) {
                                        deadObs.add(observer);
                                    }
                                }
                            }
                        }
                        for (IContentObserver observer2 : deadObs) {
                            try {
                                ((List) PowerManagerImpl.this.mObserverMap.get(key)).remove(observer2);
                                Log.m70e(PowerManagerImpl.TAG, "error onChange key:" + key);
                            } catch (Exception e2) {
                            }
                        }
                    }
                }
            }
        }.start();
    }

    private Object getField(String key) {
        Object obj;
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        synchronized (this.mFiledLock) {
            obj = this.mFieldMap.get(key);
        }
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastField(String key) {
        synchronized (this.obsLock) {
            if (this.mObserverMap.get(key) != null) {
                for (IContentObserver innerObs : this.mObserverMap.get(key)) {
                    if (innerObs != null) {
                        onChange(innerObs);
                        Log.m68i(TAG, "IContentObserver - Key:" + key + " - " + innerObs);
                    }
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.core.PowerManagerImpl$3] */
    private void onChange(final IContentObserver observer) {
        new Thread() { // from class: com.wits.pms.core.PowerManagerImpl.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    observer.onChange();
                } catch (Exception e) {
                    if (e instanceof DeadObjectException) {
                        Log.m68i(PowerManagerImpl.TAG, "DeadObjectException - obs:" + observer);
                    }
                }
            }
        }.start();
    }

    private void saveField(String key, Object value) {
        if (value == null || value.equals(getField(key))) {
            return;
        }
        synchronized (this.mFiledLock) {
            Log.m68i(TAG, "addStatus:  key:" + key + "- value:" + value + " - pid:" + getCallingPid());
            this.mFieldMap.put(key, value);
            this.mHandler.obtainMessage(3, key).sendToTarget();
        }
    }

    private void saveField(String key, Object value, boolean broadcast) {
        if (value == null || value.equals(getField(key))) {
            return;
        }
        synchronized (this.mFiledLock) {
            this.mFieldMap.put(key, value);
            if (broadcast) {
                this.mHandler.obtainMessage(3, key).sendToTarget();
            }
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void addIntStatus(String key, int value) throws RemoteException {
        saveField(key, Integer.valueOf(value));
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void addBooleanStatus(String key, boolean value) throws RemoteException {
        saveField(key, Boolean.valueOf(value));
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void addStringStatus(String key, String value) throws RemoteException {
        saveField(key, value);
    }

    private void showLog(String msg, boolean open) {
    }

    public void addStatusFromObject(Object o) {
        Field[] fields = o.getClass().getFields();
        for (Field field : fields) {
            try {
                Object obj = field.get(o);
                if (obj instanceof Boolean) {
                    saveField(field.getName(), Boolean.valueOf(((Boolean) obj).booleanValue()), false);
                } else if (obj instanceof Integer) {
                    saveField(field.getName(), Integer.valueOf(((Integer) obj).intValue()), false);
                } else if (obj instanceof String) {
                    saveField(field.getName(), (String) obj, false);
                } else if (obj instanceof List) {
                    saveField(field.getName(), obj.toString(), false);
                }
            } catch (Exception e) {
            }
        }
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public String getJsonConfig(String pkgName) throws RemoteException {
        return null;
    }

    @Override // com.wits.pms.IPowerManagerAppService
    public void saveJsonConfig(String pkgName, String json) throws RemoteException {
    }

    public SystemStatus getmSystemStatus() {
        return this.mSystemStatus;
    }

    public BtPhoneStatus getmBtPhoneStatus() {
        return this.mBtPhoneStatus;
    }

    public McuStatus getmMcuStatus() {
        return this.mMcuStatus;
    }

    public MusicStatus getmMusicStatus() {
        return this.mMusicStatus;
    }
}
