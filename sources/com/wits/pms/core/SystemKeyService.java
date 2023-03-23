package com.wits.pms.core;

import android.content.Context;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
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
        int lastMode = Settings.System.getInt(this.mContext.getContentResolver(), "mode", 0);
        int mPageIndex = Settings.System.getInt(this.mContext.getContentResolver(), "mPageIndex", 0);
        Log.d("SystemKeyService", "keyControl  lastMode = " + lastMode + "   mPageIndex = " + mPageIndex);
        if (mPageIndex == 1 && (event.getKeyCode() == 4 || event.getKeyCode() == 3)) {
            return true;
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), "wits_key", 0) == 1) {
            return true;
        }
        return false;
    }
}
