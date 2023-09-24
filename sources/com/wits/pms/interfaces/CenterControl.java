package com.wits.pms.interfaces;

import android.p007os.RemoteException;

/* loaded from: classes2.dex */
public interface CenterControl {
    void acceptPhone();

    void btMusicContinue();

    void btMusicNext();

    void btMusicOpen();

    void btMusicPlayPause();

    void btMusicPrev();

    void btMusicRelease();

    void btMusicStop();

    void btPhoneCall(String str, String str2);

    void call();

    void clearMemory();

    void closeApp(String str);

    void closeBluetooth();

    void displayScreen(boolean z);

    void enterZlink();

    void exitZlink();

    void fmClose();

    void fmNext();

    void fmOpen();

    void fmPrevious();

    void handUpPhone();

    void handupEcarPhone();

    void mediaNext();

    void mediaPause();

    void mediaPlay();

    void mediaPlayPause();

    void mediaPrevious();

    void mute(boolean z);

    void openApp(String str);

    void openApp(String str, String str2);

    void openBluetooth(boolean z);

    void powerOff();

    void sourceSwitch() throws RemoteException;

    void stopMedia();

    void stopZlinkMusic();

    void switchSoundToCar();

    void switchSoundToPhone();

    void unCall();

    void volumeDown();

    void volumeMax();

    void volumeMin();

    void volumeUp();

    void wifiOperation(boolean z);

    void zlinkHandleCall();
}
