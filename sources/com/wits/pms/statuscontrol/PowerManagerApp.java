package com.wits.pms.statuscontrol;

import android.content.Context;
import android.database.ContentObserver;
import android.p007os.Handler;
import android.p007os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wits.pms.ICmdListener;
import com.wits.pms.IContentObserver;
import com.wits.pms.IPowerManagerAppService;
import com.wits.pms.mirror.ServiceManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class PowerManagerApp {
    private static ICmdListener cmdListener;
    private static Context context;
    private static final HashMap<String, IContentObserver> maps = new HashMap<>();

    public static void init(Context context2) {
        context = context2;
        context2.getContentResolver().registerContentObserver(Settings.System.getUriFor("bootTimes"), true, new ContentObserver(new Handler(context2.getMainLooper())) { // from class: com.wits.pms.statuscontrol.PowerManagerApp.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                if (PowerManagerApp.cmdListener != null) {
                    PowerManagerApp.registerICmdListener(PowerManagerApp.cmdListener);
                }
                for (String key : PowerManagerApp.maps.keySet()) {
                    PowerManagerApp.registerIContentObserver(key, (IContentObserver) PowerManagerApp.maps.get(key));
                }
            }
        });
    }

    public static IPowerManagerAppService getManager() {
        return IPowerManagerAppService.Stub.asInterface(ServiceManager.getService("wits_pms"));
    }

    public static void registerICmdListener(ICmdListener listener) {
        try {
            cmdListener = listener;
            if (getManager() == null) {
                return;
            }
            getManager().registerCmdListener(listener);
        } catch (RemoteException e) {
        }
    }

    public static void registerIContentObserver(String key, IContentObserver contentObserver) {
        Log.m68i("IPowerManagerService registerIContentObserver", contentObserver.getClass().getName());
        try {
            if (getManager() == null) {
                return;
            }
            getManager().registerObserver(key, contentObserver);
        } catch (RemoteException e) {
        }
    }

    public static void unRegisterIContentObserver(IContentObserver contentObserver) {
        try {
            Log.m68i("IPowerManagerService unRegisterIContentObserver", contentObserver.getClass().getName());
            if (getManager() == null) {
                return;
            }
            getManager().unregisterObserver(contentObserver);
        } catch (RemoteException e) {
        }
    }

    public static boolean sendCommand(String jsonMsg) {
        try {
            return getManager().sendCommand(jsonMsg);
        } catch (RemoteException e) {
            Log.m67i(getManager().getClass().getName(), "error sendCommand", e);
            return false;
        }
    }

    public static void sendStatus(WitsStatus witsStatus) {
        if (getManager() != null) {
            try {
                getManager().sendStatus(new Gson().toJson(witsStatus));
            } catch (RemoteException e) {
            }
        }
    }

    public static List<String> getDataListFromJsonKey(String key) {
        String json = Settings.System.getString(context.getContentResolver(), key);
        return (List) new Gson().fromJson(json, new TypeToken<ArrayList<String>>() { // from class: com.wits.pms.statuscontrol.PowerManagerApp.2
        }.getType());
    }

    public static void setBooleanStatus(String key, boolean value) throws RemoteException {
        getManager().addBooleanStatus(key, value);
    }

    public static void setStatusString(String key, String value) {
        try {
            getManager().addStringStatus(key, value);
        } catch (RemoteException e) {
        }
    }

    public static void setStatusInt(String key, int value) throws RemoteException {
        getManager().addIntStatus(key, value);
    }

    public static boolean getStatusBoolean(String key) throws RemoteException {
        return getManager().getStatusBoolean(key);
    }

    public static String getStatusString(String key) throws RemoteException {
        return getManager().getStatusString(key);
    }

    public static int getStatusInt(String key) throws RemoteException {
        return getManager().getStatusInt(key);
    }

    public static int getSettingsInt(String key) throws RemoteException {
        return getManager().getSettingsInt(key);
    }

    public static String getSettingsString(String key) throws RemoteException {
        return getManager().getSettingsString(key);
    }

    public static void setSettingsInt(String key, int value) throws RemoteException {
        getManager().setSettingsInt(key, value);
    }

    public static void setSettingsString(String key, String value) throws RemoteException {
        getManager().setSettingsString(key, value);
    }

    public static boolean getStatusBoolean(String key, boolean defaultValue) {
        try {
            return getManager().getStatusBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
