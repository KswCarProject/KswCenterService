package com.wits.pms.core;

import android.content.Context;
import android.os.RemoteException;
import android.provider.Settings;
import android.view.KeyEvent;
import com.wits.pms.ISystemKeyService;

public class SystemKeyService extends ISystemKeyService.Stub {
    private final Context mContext;

    public SystemKeyService(Context context) {
        this.mContext = context;
    }

    public void onKeyEvent(final KeyEvent event) throws RemoteException {
        new Thread() {
            public void run() {
                SystemStatusControl.getDefault().updateKeyEvent(event);
            }
        }.start();
    }

    public boolean keyControl(KeyEvent event) throws RemoteException {
        if (event.getKeyCode() == 5 && event.getAction() == 0) {
            return CenterControlImpl.getImpl().handleCall();
        }
        return Settings.System.getInt(this.mContext.getContentResolver(), "wits_key", 0) == 1;
    }
}
