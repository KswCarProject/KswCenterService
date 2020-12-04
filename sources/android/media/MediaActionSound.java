package android.media;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

public class MediaActionSound {
    public static final int FOCUS_COMPLETE = 1;
    private static final int NUM_MEDIA_SOUND_STREAMS = 1;
    public static final int SHUTTER_CLICK = 0;
    private static final String[] SOUND_DIRS = {"/product/media/audio/ui/", "/system/media/audio/ui/"};
    private static final String[] SOUND_FILES = {"camera_click.ogg", "camera_focus.ogg", "VideoRecord.ogg", "VideoStop.ogg"};
    public static final int START_VIDEO_RECORDING = 2;
    private static final int STATE_LOADED = 3;
    private static final int STATE_LOADING = 1;
    private static final int STATE_LOADING_PLAY_REQUESTED = 2;
    private static final int STATE_NOT_LOADED = 0;
    public static final int STOP_VIDEO_RECORDING = 3;
    private static final String TAG = "MediaActionSound";
    private SoundPool.OnLoadCompleteListener mLoadCompleteListener = new SoundPool.OnLoadCompleteListener() {
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0074, code lost:
            if (r0 == 0) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0076, code lost:
            r13.play(r0, 1.0f, 1.0f, 0, 0, 1.0f);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onLoadComplete(android.media.SoundPool r13, int r14, int r15) {
            /*
                r12 = this;
                android.media.MediaActionSound r0 = android.media.MediaActionSound.this
                android.media.MediaActionSound$SoundState[] r0 = r0.mSounds
                int r1 = r0.length
                r2 = 0
                r3 = r2
            L_0x0009:
                if (r3 >= r1) goto L_0x0086
                r4 = r0[r3]
                int r5 = r4.id
                if (r5 == r14) goto L_0x0015
                int r3 = r3 + 1
                goto L_0x0009
            L_0x0015:
                r0 = 0
                monitor-enter(r4)
                if (r15 == 0) goto L_0x0041
                r4.state = r2     // Catch:{ all -> 0x003f }
                r4.id = r2     // Catch:{ all -> 0x003f }
                java.lang.String r1 = "MediaActionSound"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x003f }
                r2.<init>()     // Catch:{ all -> 0x003f }
                java.lang.String r3 = "OnLoadCompleteListener() error: "
                r2.append(r3)     // Catch:{ all -> 0x003f }
                r2.append(r15)     // Catch:{ all -> 0x003f }
                java.lang.String r3 = " loading sound: "
                r2.append(r3)     // Catch:{ all -> 0x003f }
                int r3 = r4.name     // Catch:{ all -> 0x003f }
                r2.append(r3)     // Catch:{ all -> 0x003f }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x003f }
                android.util.Log.e(r1, r2)     // Catch:{ all -> 0x003f }
                monitor-exit(r4)     // Catch:{ all -> 0x003f }
                return
            L_0x003f:
                r1 = move-exception
                goto L_0x0084
            L_0x0041:
                int r1 = r4.state     // Catch:{ all -> 0x003f }
                r2 = 3
                switch(r1) {
                    case 1: goto L_0x0050;
                    case 2: goto L_0x004a;
                    default: goto L_0x0047;
                }     // Catch:{ all -> 0x003f }
            L_0x0047:
                java.lang.String r1 = "MediaActionSound"
                goto L_0x0053
            L_0x004a:
                int r1 = r4.id     // Catch:{ all -> 0x003f }
                r0 = r1
                r4.state = r2     // Catch:{ all -> 0x003f }
                goto L_0x0073
            L_0x0050:
                r4.state = r2     // Catch:{ all -> 0x003f }
                goto L_0x0073
            L_0x0053:
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x003f }
                r2.<init>()     // Catch:{ all -> 0x003f }
                java.lang.String r3 = "OnLoadCompleteListener() called in wrong state: "
                r2.append(r3)     // Catch:{ all -> 0x003f }
                int r3 = r4.state     // Catch:{ all -> 0x003f }
                r2.append(r3)     // Catch:{ all -> 0x003f }
                java.lang.String r3 = " for sound: "
                r2.append(r3)     // Catch:{ all -> 0x003f }
                int r3 = r4.name     // Catch:{ all -> 0x003f }
                r2.append(r3)     // Catch:{ all -> 0x003f }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x003f }
                android.util.Log.e(r1, r2)     // Catch:{ all -> 0x003f }
            L_0x0073:
                monitor-exit(r4)     // Catch:{ all -> 0x003f }
                if (r0 == 0) goto L_0x0086
                r7 = 1065353216(0x3f800000, float:1.0)
                r8 = 1065353216(0x3f800000, float:1.0)
                r9 = 0
                r10 = 0
                r11 = 1065353216(0x3f800000, float:1.0)
                r5 = r13
                r6 = r0
                r5.play(r6, r7, r8, r9, r10, r11)
                goto L_0x0086
            L_0x0084:
                monitor-exit(r4)     // Catch:{ all -> 0x003f }
                throw r1
            L_0x0086:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaActionSound.AnonymousClass1.onLoadComplete(android.media.SoundPool, int, int):void");
        }
    };
    private SoundPool mSoundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setFlags(1).setContentType(4).build()).build();
    /* access modifiers changed from: private */
    public SoundState[] mSounds;

    private class SoundState {
        public int id = 0;
        public final int name;
        public int state = 0;

        public SoundState(int name2) {
            this.name = name2;
        }
    }

    public MediaActionSound() {
        this.mSoundPool.setOnLoadCompleteListener(this.mLoadCompleteListener);
        this.mSounds = new SoundState[SOUND_FILES.length];
        for (int i = 0; i < this.mSounds.length; i++) {
            this.mSounds[i] = new SoundState(i);
        }
    }

    private int loadSound(SoundState sound) {
        String soundFileName = SOUND_FILES[sound.name];
        for (String soundDir : SOUND_DIRS) {
            int id = this.mSoundPool.load(soundDir + soundFileName, 1);
            if (id > 0) {
                sound.state = 1;
                sound.id = id;
                return id;
            }
        }
        return 0;
    }

    public void load(int soundName) {
        if (soundName < 0 || soundName >= SOUND_FILES.length) {
            throw new RuntimeException("Unknown sound requested: " + soundName);
        }
        SoundState sound = this.mSounds[soundName];
        synchronized (sound) {
            if (sound.state != 0) {
                Log.e(TAG, "load() called in wrong state: " + sound + " for sound: " + soundName);
            } else if (loadSound(sound) <= 0) {
                Log.e(TAG, "load() error loading sound: " + soundName);
            }
        }
    }

    public void play(int soundName) {
        if (soundName < 0 || soundName >= SOUND_FILES.length) {
            throw new RuntimeException("Unknown sound requested: " + soundName);
        }
        SoundState sound = this.mSounds[soundName];
        synchronized (sound) {
            int i = sound.state;
            if (i != 3) {
                switch (i) {
                    case 0:
                        loadSound(sound);
                        if (loadSound(sound) <= 0) {
                            Log.e(TAG, "play() error loading sound: " + soundName);
                            break;
                        }
                    case 1:
                        sound.state = 2;
                        break;
                    default:
                        Log.e(TAG, "play() called in wrong state: " + sound.state + " for sound: " + soundName);
                        break;
                }
            } else {
                this.mSoundPool.play(sound.id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        }
    }

    public void release() {
        if (this.mSoundPool != null) {
            for (SoundState sound : this.mSounds) {
                synchronized (sound) {
                    sound.state = 0;
                    sound.id = 0;
                }
            }
            this.mSoundPool.release();
            this.mSoundPool = null;
        }
    }
}
