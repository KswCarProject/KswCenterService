package com.android.internal.app;

import android.app.Activity;
import android.app.IUiModeManager;
import android.content.Context;
import android.p007os.Bundle;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.util.Log;

/* loaded from: classes4.dex */
public class DisableCarModeActivity extends Activity {
    private static final String TAG = "DisableCarModeActivity";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            IUiModeManager uiModeManager = IUiModeManager.Stub.asInterface(ServiceManager.getService(Context.UI_MODE_SERVICE));
            uiModeManager.disableCarMode(1);
        } catch (RemoteException e) {
            Log.m69e(TAG, "Failed to disable car mode", e);
        }
        finish();
    }
}
