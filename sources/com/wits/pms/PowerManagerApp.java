package com.wits.pms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.wits.pms.IPowerManagerAppService;
import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class PowerManagerApp {
    private static ICmdListener cmdListener;
    private static Context context;
    private static final HashMap<String, IContentObserver> maps = new HashMap<>();

    public static void init(Context context2) {
        context = context2;
        context2.getContentResolver().registerContentObserver(Settings.System.getUriFor("bootTimes"), true, new ContentObserver(new Handler(context2.getMainLooper())) { // from class: com.wits.pms.PowerManagerApp.1
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
        return IPowerManagerAppService.Stub.asInterface(getService("wits_pms"));
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
        Log.m68i("IPowerManagerService", contentObserver.getClass().getName());
        try {
            maps.put(key, contentObserver);
            if (getManager() == null) {
                return;
            }
            getManager().registerObserver(key, contentObserver);
        } catch (RemoteException e) {
        }
    }

    public static void unRegisterIContentObserver(IContentObserver contentObserver) {
        try {
            for (String key : maps.keySet()) {
                if (maps.get(key) == contentObserver) {
                    maps.remove(key, contentObserver);
                }
            }
            if (getManager() == null) {
                return;
            }
            getManager().unregisterObserver(contentObserver);
        } catch (RemoteException e) {
        }
    }

    @SuppressLint({"PrivateApi"})
    public static IBinder getService(String serviceName) {
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getMethod("getService", String.class);
            return (IBinder) getService.invoke(serviceManager, serviceName);
        } catch (Exception e) {
            String name = PowerManagerApp.class.getName();
            Log.m69e(name, "error service init - " + serviceName, e);
            return null;
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

    public static String getStatusString(String key) {
        try {
            return getManager().getStatusString(key);
        } catch (RemoteException e) {
            return null;
        }
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
}
