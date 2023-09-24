package com.wits.pms.utils;

import android.content.Context;
import android.media.AudioManager;

/* loaded from: classes2.dex */
public class SystemSoundControl {
    public static int lastMusicIndex = -1;
    public static int lastRingIndex = -1;
    public static int lastAlarmIndex = -1;

    public static void mute(Context context, int streamType, boolean mute) {
        if (mute) {
            getAudioManager(context).setStreamVolume(streamType, 0, 0);
            return;
        }
        int index = 0;
        switch (streamType) {
            case 2:
                index = lastRingIndex;
                break;
            case 3:
                index = lastMusicIndex;
                break;
            case 4:
                index = lastAlarmIndex;
                break;
        }
        if (index != -1) {
            getAudioManager(context).setStreamVolume(streamType, index, 0);
        }
    }

    private static AudioManager getAudioManager(Context context) {
        return (AudioManager) context.getSystemService("audio");
    }

    public static void allMute(Context context, boolean mute) {
        if (mute) {
            lastMusicIndex = getAudioManager(context).getStreamVolume(3);
            lastRingIndex = getAudioManager(context).getStreamVolume(2);
            lastAlarmIndex = getAudioManager(context).getStreamVolume(4);
        }
        mute(context, 3, mute);
    }
}
