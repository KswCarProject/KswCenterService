package com.wits.pms;

import android.media.AudioManager;
import android.p007os.Bundle;
import android.p007os.RemoteException;
import android.support.annotation.Nullable;
import android.support.p014v7.app.AppCompatActivity;
import com.wits.pms.IContentObserver;

/* loaded from: classes2.dex */
public class ClockActivity extends AppCompatActivity {
    private IContentObserver.Stub systemModeObserver;

    @Override // android.support.p014v7.app.AppCompatActivity, android.support.p011v4.app.FragmentActivity, android.support.p011v4.app.SupportActivity, android.app.Activity
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C3580R.layout.activity_clock);
        final AudioManager audioManager = (AudioManager) getSystemService("audio");
        audioManager.requestAudioFocus(null, 3, 1);
        this.systemModeObserver = new IContentObserver.Stub() { // from class: com.wits.pms.ClockActivity.1
            @Override // com.wits.pms.IContentObserver
            public void onChange() throws RemoteException {
                if (com.wits.pms.statuscontrol.PowerManagerApp.getStatusInt("systemMode") == 1) {
                    audioManager.abandonAudioFocus(null);
                    ClockActivity.this.finish();
                }
            }
        };
        com.wits.pms.statuscontrol.PowerManagerApp.registerIContentObserver("systemMode", this.systemModeObserver);
    }

    @Override // android.support.p014v7.app.AppCompatActivity, android.support.p011v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        com.wits.pms.statuscontrol.PowerManagerApp.unRegisterIContentObserver(this.systemModeObserver);
    }
}
