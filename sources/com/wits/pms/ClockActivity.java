package com.wits.pms;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.wits.pms.IContentObserver;
import com.wits.pms.statuscontrol.PowerManagerApp;

public class ClockActivity extends AppCompatActivity {
    private IContentObserver.Stub systemModeObserver;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_clock);
        final AudioManager audioManager = (AudioManager) getSystemService("audio");
        audioManager.requestAudioFocus((AudioManager.OnAudioFocusChangeListener) null, 3, 1);
        this.systemModeObserver = new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                if (PowerManagerApp.getStatusInt("systemMode") == 1) {
                    audioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
                    ClockActivity.this.finish();
                }
            }
        };
        PowerManagerApp.registerIContentObserver("systemMode", this.systemModeObserver);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        PowerManagerApp.unRegisterIContentObserver(this.systemModeObserver);
    }
}
