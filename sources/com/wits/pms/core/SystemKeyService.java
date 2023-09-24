package com.wits.pms.core;

import android.content.Context;
import android.content.Intent;
import android.p007os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.wits.pms.ISystemKeyService;

/* loaded from: classes2.dex */
public class SystemKeyService extends ISystemKeyService.Stub {
    private final Context mContext;

    public SystemKeyService(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.core.SystemKeyService$1] */
    @Override // com.wits.pms.ISystemKeyService
    public void onKeyEvent(final KeyEvent event) throws RemoteException {
        new Thread() { // from class: com.wits.pms.core.SystemKeyService.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SystemStatusControl.getDefault().updateKeyEvent(event);
            }
        }.start();
    }

    @Override // com.wits.pms.ISystemKeyService
    public boolean keyControl(KeyEvent event) throws RemoteException {
        if (event.getKeyCode() == 5 && event.getAction() == 0) {
            return CenterControlImpl.getImpl().handleCall();
        }
        int lastMode = Settings.System.getInt(this.mContext.getContentResolver(), "mode", 0);
        int mPageIndex = Settings.System.getInt(this.mContext.getContentResolver(), "mPageIndex", 0);
        Log.m72d("SystemKeyService", "keyControl  lastMode = " + lastMode + "   mPageIndex = " + mPageIndex);
        if (mPageIndex == 1) {
            if (event.getKeyCode() == 4 || event.getKeyCode() == 3) {
                return true;
            }
            String uiName = Settings.System.getString(this.mContext.getContentResolver(), "UiName");
            if (TextUtils.equals(uiName, "UI_GS_ID8") && (event.getKeyCode() == 19 || event.getKeyCode() == 20 || event.getKeyCode() == 21 || event.getKeyCode() == 22 || event.getKeyCode() == 66)) {
                Log.m72d("SystemKeyService", "KEYCODE_DPAD_UP KEYCODE_DPAD_DOWN");
                Intent intent = new Intent("com.example.KeyEvent.control");
                int keyCode = event.getKeyCode();
                if (keyCode != 66) {
                    switch (keyCode) {
                        case 19:
                            intent.putExtra("message", "KEYCODE_DPAD_UP");
                            break;
                        case 20:
                            intent.putExtra("message", "KEYCODE_DPAD_DOWN");
                            break;
                        case 21:
                            intent.putExtra("message", "KEYCODE_DPAD_LEFT");
                            break;
                        case 22:
                            intent.putExtra("message", "KEYCODE_DPAD_RIGHT");
                            break;
                    }
                } else {
                    intent.putExtra("message", "KEYCODE_ENTER");
                }
                this.mContext.sendBroadcast(intent);
                return true;
            }
        }
        return Settings.System.getInt(this.mContext.getContentResolver(), "wits_key", 0) == 1;
    }
}
