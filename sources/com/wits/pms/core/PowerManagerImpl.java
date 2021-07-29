package com.wits.pms.core;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
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

public class PowerManagerImpl extends IPowerManagerAppService.Stub {
    private static final int MSG_CMD = 1;
    private static final int MSG_UPDATE_INFO = 2;
    private static final int OBSCHANGE = 2;
    private static final int SENDCOMMAND = 1;
    private static final String TAG = "IPowerManagerAppService";
    private static final int UPDATESTATUS = 3;
    /* access modifiers changed from: private */
    public final RemoteCallbackList<ICmdListener> cmdListeners = new RemoteCallbackList<>();
    private BtPhoneStatus mBtPhoneStatus = new BtPhoneStatus(false, false, "", 0, 0, true);
    private HashMap<String, Object> mFieldMap = new HashMap<>();
    private final Object mFiledLock = new Object();
    /* access modifiers changed from: private */
    public Handler mHandler;
    private McuStatus mMcuStatus = new McuStatus(1, "-1");
    private MusicStatus mMusicStatus = new MusicStatus("", "", false, 0);
    /* access modifiers changed from: private */
    public HashMap<String, List<IContentObserver>> mObserverMap = new HashMap<>();
    private SystemStatus mSystemStatus = new SystemStatus();
    /* access modifiers changed from: private */
    public final Object obsLock = new Object();
    private final Object sendCmdLock = new Object();
    /* access modifiers changed from: private */
    public final Object updateInfoLock = new Object();

    public PowerManagerImpl(Context context) {
        saveSystem(this.mSystemStatus);
        saveBt(this.mBtPhoneStatus);
        saveMusic(this.mMusicStatus);
        saveMcu(this.mMcuStatus);
        setUpHandler(context);
    }

    private void setUpHandler(Context context) {
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                String jsonMsg = (String) msg.obj;
                switch (msg.what) {
                    case 1:
                        boolean unused = PowerManagerImpl.this.goSendCommand(jsonMsg);
                        return;
                    case 2:
                        synchronized (PowerManagerImpl.this.updateInfoLock) {
                            int length = PowerManagerImpl.this.cmdListeners.beginBroadcast();
                            for (int i = 0; i < length; i++) {
                                ICmdListener listener = (ICmdListener) PowerManagerImpl.this.cmdListeners.getBroadcastItem(i);
                                if (listener != null) {
                                    PowerManagerImpl.this.mHandler.post(new Runnable(jsonMsg) {
                                        private final /* synthetic */ String f$1;

                                        {
                                            this.f$1 = r2;
                                        }

                                        public final void run() {
                                            PowerManagerImpl.AnonymousClass1.lambda$handleMessage$0(ICmdListener.this, this.f$1);
                                        }
                                    });
                                }
                            }
                            Log.i(PowerManagerImpl.TAG, "sendStatus Msg: " + jsonMsg);
                            PowerManagerImpl.this.cmdListeners.finishBroadcast();
                        }
                        return;
                    default:
                        return;
                }
            }

            static /* synthetic */ void lambda$handleMessage$0(ICmdListener listener, String jsonMsg) {
                try {
                    listener.updateStatusInfo(jsonMsg);
                } catch (Exception e) {
                    Log.e(PowerManagerImpl.TAG, "PMAS handleCommand error!!!");
                }
            }
        };
    }

    /* access modifiers changed from: private */
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
                        Log.e(TAG, "PMAS handleCommand error!!!", e);
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("sendCommand:");
            sb.append(result ? "success" : "failed");
            sb.append("\ncommand: ");
            sb.append(json);
            Log.i(TAG, sb.toString());
            this.cmdListeners.finishBroadcast();
        }
        return result;
    }

    public synchronized boolean sendCommand(String jsonMsg) throws RemoteException {
        if (WitsCommand.getWitsCommandFormJson(jsonMsg).isNeedResult()) {
            return goSendCommand(jsonMsg);
        }
        this.mHandler.obtainMessage(1, jsonMsg).sendToTarget();
        return true;
    }

    public synchronized boolean sendStatus(String json) throws RemoteException {
        updateStatusInfo(json);
        return true;
    }

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
        boolean z = false;
        boolean hasChange = false;
        try {
            if (handlerStatus(WitsStatus.getWitsStatusFormJson(jsonMsg)) != 0) {
                z = true;
            }
            hasChange = z;
        } catch (Exception e) {
            Log.e(TAG, "sendStatus:failed\nmsg: " + jsonMsg, e);
        }
        if (hasChange) {
            this.mHandler.obtainMessage(2, jsonMsg).sendToTarget();
        }
    }

    public synchronized void registerObserver(String key, IContentObserver observer) throws RemoteException {
        synchronized (this.obsLock) {
            Log.i(TAG, "registerObserver key:" + key + " - observer:" + observer);
            List<IContentObserver> list = this.mObserverMap.get(key);
            List<IContentObserver> contentObservers = list;
            if (list == null) {
                contentObservers = new ArrayList<>();
            }
            contentObservers.add(observer);
            this.mObserverMap.put(key, contentObservers);
        }
    }

    public void unregisterObserver(IContentObserver observer) throws RemoteException {
        for (List<IContentObserver> list : this.mObserverMap.values()) {
            Iterator<IContentObserver> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    IContentObserver contentObserver = it.next();
                    if (observer.equals(contentObserver)) {
                        list.remove(contentObserver);
                        return;
                    }
                }
            }
        }
    }

    public int getSettingsInt(String key) throws RemoteException {
        try {
            return KswSettings.getSettings().getSettingsInt(key);
        } catch (Settings.SettingNotFoundException e) {
            Log.i(TAG, "getSettingsInt settings not found");
            return 0;
        }
    }

    public String getSettingsString(String key) throws RemoteException {
        return KswSettings.getSettings().getSettingsString(key);
    }

    public void setSettingsInt(String key, int value) throws RemoteException {
        KswSettings.getSettings().setInt(key, value);
    }

    public void setSettingsString(String key, String value) throws RemoteException {
        KswSettings.getSettings().setString(key, value);
    }

    public boolean getStatusBoolean(String key) throws RemoteException {
        try {
            return ((Boolean) this.mFieldMap.get(key)).booleanValue();
        } catch (Exception e) {
            Log.e(TAG, "cannot cast key :" + key, e);
            return false;
        }
    }

    public int getStatusInt(String key) throws RemoteException {
        try {
            return ((Integer) this.mFieldMap.get(key)).intValue();
        } catch (Exception e) {
            throw new RemoteException("cannot cast");
        }
    }

    public String getStatusString(String key) throws RemoteException {
        try {
            return (String) this.mFieldMap.get(key);
        } catch (Exception e) {
            throw new RemoteException("cannot cast");
        }
    }

    private void save(WitsStatus witsStatus) {
        int i = witsStatus.type;
        if (i == 1) {
            saveSystem(SystemStatus.getStatusFormJson(witsStatus.getJsonArg()));
        } else if (i == 3) {
            saveBt(BtPhoneStatus.getStatusForJson(witsStatus.getJsonArg()));
        } else if (i == 5) {
            saveMcu(McuStatus.getStatusFromJson(witsStatus.getJsonArg()));
        } else if (i == 21) {
            saveMusic(MusicStatus.getStatusFromJson(witsStatus.getJsonArg()));
        }
    }

    private void saveMcu(McuStatus mcuStatus) {
        this.mFieldMap.put("benzData", mcuStatus.benzData.getJson());
        this.mFieldMap.put("acData", mcuStatus.acData.getJson());
        this.mFieldMap.put("carData", mcuStatus.carData.getJson());
        this.mFieldMap.put("carGear", Integer.valueOf(mcuStatus.carData.carGear));
        this.mFieldMap.put("signalLeft", Integer.valueOf(mcuStatus.carData.signalLeft));
        this.mFieldMap.put("signalDouble", Integer.valueOf(mcuStatus.carData.signalDouble));
        this.mFieldMap.put("signalRight", Integer.valueOf(mcuStatus.carData.signalRight));
        this.mFieldMap.put("carDoor", Integer.valueOf(mcuStatus.carData.carDoor));
        this.mFieldMap.put("mcuVerison", mcuStatus.mcuVerison);
        this.mFieldMap.put("systemMode", Integer.valueOf(mcuStatus.systemMode));
    }

    private void saveSystem(SystemStatus systemStatus) {
        this.mFieldMap.put("topApp", systemStatus.topApp);
        this.mFieldMap.put("screenSwitch", Integer.valueOf(systemStatus.screenSwitch));
        this.mFieldMap.put("lastMode", Integer.valueOf(systemStatus.lastMode));
        this.mFieldMap.put("ill", Integer.valueOf(systemStatus.ill));
        this.mFieldMap.put("acc", Integer.valueOf(systemStatus.acc));
        this.mFieldMap.put("epb", Integer.valueOf(systemStatus.epb));
        this.mFieldMap.put("ccd", Integer.valueOf(systemStatus.ccd));
        this.mFieldMap.put("dormant", Boolean.valueOf(systemStatus.dormant));
        this.mFieldMap.put("rlight", Integer.valueOf(systemStatus.rlight));
    }

    private void saveBt(BtPhoneStatus btPhoneStatus) {
        this.mFieldMap.put("btSwitch", Boolean.valueOf(btPhoneStatus.btSwitch));
        this.mFieldMap.put("callStatus", Integer.valueOf(btPhoneStatus.callStatus));
        this.mFieldMap.put("voiceStatus", Integer.valueOf(btPhoneStatus.voiceStatus));
        this.mFieldMap.put("devAddr", btPhoneStatus.devAddr);
        this.mFieldMap.put("isConnected", Boolean.valueOf(btPhoneStatus.isConnected));
        this.mFieldMap.put("isPlayingMusic", Boolean.valueOf(btPhoneStatus.isPlayingMusic));
        this.mFieldMap.put("connectedAddr", btPhoneStatus.connectedAddr);
    }

    private void saveMusic(MusicStatus musicStatus) {
        this.mFieldMap.put("mode", musicStatus.mode);
        this.mFieldMap.put("path", musicStatus.path);
        this.mFieldMap.put("play", Boolean.valueOf(musicStatus.play));
        this.mFieldMap.put(BrowserContract.Bookmarks.POSITION, Integer.valueOf(musicStatus.position));
    }

    private void saveVideo(VideoStatus musicStatus) {
    }

    private int handlerStatus(WitsStatus witsStatus) {
        List<String> compares = new ArrayList<>();
        int i = witsStatus.type;
        if (i == 1) {
            SystemStatus systemStatus = SystemStatus.getStatusFormJson(witsStatus.getJsonArg());
            if (this.mSystemStatus != null) {
                compares.addAll(this.mSystemStatus.compare(systemStatus));
                if (compares.size() > 0) {
                    Log.i(TAG, "mSystemStatus has change status - " + compares.size());
                }
            }
            this.mSystemStatus = systemStatus;
        } else if (i == 3) {
            BtPhoneStatus btPhoneStatus = BtPhoneStatus.getStatusForJson(witsStatus.getJsonArg());
            if (this.mBtPhoneStatus != null) {
                compares.addAll(this.mBtPhoneStatus.compare(btPhoneStatus));
                if (compares.size() > 0) {
                    Log.i(TAG, "mBtPhoneStatus has change status - " + compares.size());
                }
            }
            this.mBtPhoneStatus = btPhoneStatus;
        } else if (i == 5) {
            McuStatus mcuStatus = McuStatus.getStatusFromJson(witsStatus.getJsonArg());
            if (this.mMcuStatus != null) {
                compares.addAll(this.mMcuStatus.compare(mcuStatus));
                if (compares.size() > 0) {
                    Log.i(TAG, "mMcuStatus has change status - " + compares.size());
                }
            }
            this.mMcuStatus = mcuStatus;
        } else if (i == 21) {
            MusicStatus musicStatus = MusicStatus.getStatusFromJson(witsStatus.getJsonArg());
            if (this.mMusicStatus != null) {
                compares.addAll(this.mMusicStatus.compare(musicStatus));
                if (compares.size() > 0) {
                    Log.i(TAG, "mMusicStatus has change status - " + compares.size());
                }
            }
            this.mMusicStatus = musicStatus;
        }
        save(witsStatus);
        this.mHandler.post(new Runnable(compares) {
            private final /* synthetic */ List f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                PowerManagerImpl.lambda$handlerStatus$0(PowerManagerImpl.this, this.f$1);
            }
        });
        return compares.size();
    }

    public static /* synthetic */ void lambda$handlerStatus$0(PowerManagerImpl powerManagerImpl, List compares) {
        synchronized (powerManagerImpl.obsLock) {
            Iterator it = compares.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (powerManagerImpl.mObserverMap.get(key) != null) {
                    List<IContentObserver> deadObs = new ArrayList<>();
                    for (IContentObserver observer : powerManagerImpl.mObserverMap.get(key)) {
                        Log.i(TAG, "IContentObserver - Key:" + key + " - " + observer);
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
                            Log.e(TAG, "error onChange key:" + key);
                        } catch (Exception e2) {
                        }
                    }
                }
            }
        }
    }

    public void onShellCommand(FileDescriptor in, FileDescriptor out, FileDescriptor err, String[] args, ShellCallback callback, ResultReceiver resultReceiver) throws RemoteException {
        new PowerManagerAppCommand(this).exec(this, in, out, err, args, callback, resultReceiver);
    }

    public void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
    }

    public void updateMcuJsonStatus(final String key, String value) {
        this.mFieldMap.put(key, value);
        new Thread() {
            public void run() {
                synchronized (PowerManagerImpl.this.obsLock) {
                    if (PowerManagerImpl.this.mObserverMap.get(key) != null) {
                        List<IContentObserver> deadObs = new ArrayList<>();
                        for (IContentObserver observer : (List) PowerManagerImpl.this.mObserverMap.get(key)) {
                            Log.i(PowerManagerImpl.TAG, "IContentObserver - Key:" + key + " - " + observer);
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
                                Log.e(PowerManagerImpl.TAG, "error onChange key:" + key);
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

    private void broadcastField(String key) {
        synchronized (this.obsLock) {
            if (this.mObserverMap.get(key) != null) {
                for (IContentObserver innerObs : this.mObserverMap.get(key)) {
                    if (innerObs != null) {
                        onChange(innerObs);
                        Log.i(TAG, "IContentObserver - Key:" + key + " - " + innerObs);
                    }
                }
            }
        }
    }

    private void onChange(final IContentObserver observer) {
        new Thread() {
            public void run() {
                try {
                    observer.onChange();
                } catch (Exception e) {
                    if (e instanceof DeadObjectException) {
                        Log.i(PowerManagerImpl.TAG, "DeadObjectException - obs:" + observer);
                    }
                }
            }
        }.start();
    }

    private void saveField(String key, Object value) {
        if (value != null && !value.equals(getField(key))) {
            synchronized (this.mFiledLock) {
                Log.i(TAG, "addStatus:  key:" + key + "- value:" + value + " - pid:" + getCallingPid());
                this.mFieldMap.put(key, value);
                broadcastField(key);
            }
        }
    }

    private void saveField(String key, Object value, boolean broadcast) {
        if (value != null && !value.equals(getField(key))) {
            synchronized (this.mFiledLock) {
                this.mFieldMap.put(key, value);
                if (broadcast) {
                    broadcastField(key);
                }
            }
        }
    }

    public void addIntStatus(String key, int value) throws RemoteException {
        saveField(key, Integer.valueOf(value));
    }

    public void addBooleanStatus(String key, boolean value) throws RemoteException {
        saveField(key, Boolean.valueOf(value));
    }

    public void addStringStatus(String key, String value) throws RemoteException {
        saveField(key, value);
    }

    private void showLog(String msg, boolean open) {
    }

    public void addStatusFromObject(Object o) {
        for (Field field : o.getClass().getFields()) {
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

    public String getJsonConfig(String pkgName) throws RemoteException {
        return null;
    }

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
