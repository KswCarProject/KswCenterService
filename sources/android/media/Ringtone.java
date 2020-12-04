package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;

public class Ringtone {
    private static final boolean LOGD = true;
    private static final String[] MEDIA_COLUMNS = {"_id", "title"};
    private static final String MEDIA_SELECTION = "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
    private static final String TAG = "Ringtone";
    /* access modifiers changed from: private */
    public static final ArrayList<Ringtone> sActiveRingtones = new ArrayList<>();
    private final boolean mAllowRemote;
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setUsage(6).setContentType(4).build();
    private final AudioManager mAudioManager;
    private final MyOnCompletionListener mCompletionListener = new MyOnCompletionListener();
    private final Context mContext;
    private boolean mIsLooping = false;
    @UnsupportedAppUsage
    private MediaPlayer mLocalPlayer;
    private final Object mPlaybackSettingsLock = new Object();
    private final IRingtonePlayer mRemotePlayer;
    private final Binder mRemoteToken;
    private String mTitle;
    @UnsupportedAppUsage
    private Uri mUri;
    private float mVolume = 1.0f;
    private VolumeShaper mVolumeShaper;
    private VolumeShaper.Configuration mVolumeShaperConfig;

    @UnsupportedAppUsage
    public Ringtone(Context context, boolean allowRemote) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mAllowRemote = allowRemote;
        Binder binder = null;
        this.mRemotePlayer = allowRemote ? this.mAudioManager.getRingtonePlayer() : null;
        this.mRemoteToken = allowRemote ? new Binder() : binder;
    }

    @Deprecated
    public void setStreamType(int streamType) {
        PlayerBase.deprecateStreamTypeForPlayback(streamType, TAG, "setStreamType()");
        setAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(streamType).build());
    }

    @Deprecated
    public int getStreamType() {
        return AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
    }

    public void setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
        if (attributes != null) {
            this.mAudioAttributes = attributes;
            setUri(this.mUri, this.mVolumeShaperConfig);
            return;
        }
        throw new IllegalArgumentException("Invalid null AudioAttributes for Ringtone");
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public void setLooping(boolean looping) {
        synchronized (this.mPlaybackSettingsLock) {
            this.mIsLooping = looping;
            applyPlaybackProperties_sync();
        }
    }

    public boolean isLooping() {
        boolean z;
        synchronized (this.mPlaybackSettingsLock) {
            z = this.mIsLooping;
        }
        return z;
    }

    public void setVolume(float volume) {
        synchronized (this.mPlaybackSettingsLock) {
            if (volume < 0.0f) {
                volume = 0.0f;
            }
            if (volume > 1.0f) {
                volume = 1.0f;
            }
            this.mVolume = volume;
            applyPlaybackProperties_sync();
        }
    }

    public float getVolume() {
        float f;
        synchronized (this.mPlaybackSettingsLock) {
            f = this.mVolume;
        }
        return f;
    }

    private void applyPlaybackProperties_sync() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.setVolume(this.mVolume);
            this.mLocalPlayer.setLooping(this.mIsLooping);
        } else if (!this.mAllowRemote || this.mRemotePlayer == null) {
            Log.w(TAG, "Neither local nor remote player available when applying playback properties");
        } else {
            try {
                this.mRemotePlayer.setPlaybackProperties(this.mRemoteToken, this.mVolume, this.mIsLooping);
            } catch (RemoteException e) {
                Log.w(TAG, "Problem setting playback properties: ", e);
            }
        }
    }

    public String getTitle(Context context) {
        if (this.mTitle != null) {
            return this.mTitle;
        }
        String title = getTitle(context, this.mUri, true, this.mAllowRemote);
        this.mTitle = title;
        return title;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006a, code lost:
        if (r10 != null) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006c, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0094, code lost:
        if (r10 == null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0097, code lost:
        if (r7 != null) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0099, code lost:
        r7 = r12.getLastPathSegment();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getTitle(android.content.Context r11, android.net.Uri r12, boolean r13, boolean r14) {
        /*
            android.content.ContentResolver r6 = r11.getContentResolver()
            r7 = 0
            if (r12 == 0) goto L_0x009e
            java.lang.String r0 = r12.getAuthority()
            java.lang.String r8 = android.content.ContentProvider.getAuthorityWithoutUserId(r0)
            java.lang.String r0 = "settings"
            boolean r0 = r0.equals(r8)
            r9 = 1
            if (r0 == 0) goto L_0x0036
            if (r13 == 0) goto L_0x009d
            int r0 = android.media.RingtoneManager.getDefaultType(r12)
            android.net.Uri r0 = android.media.RingtoneManager.getActualDefaultRingtoneUri(r11, r0)
            r1 = 0
            java.lang.String r2 = getTitle(r11, r0, r1, r14)
            r3 = 17040940(0x104062c, float:2.4249E-38)
            java.lang.Object[] r4 = new java.lang.Object[r9]
            r4[r1] = r2
            java.lang.String r7 = r11.getString(r3, r4)
            goto L_0x009d
        L_0x0036:
            r0 = 0
            r10 = r0
            java.lang.String r1 = "media"
            boolean r1 = r1.equals(r8)     // Catch:{ SecurityException -> 0x0073 }
            if (r1 == 0) goto L_0x006a
            if (r14 == 0) goto L_0x0045
        L_0x0043:
            r3 = r0
            goto L_0x0049
        L_0x0045:
            java.lang.String r0 = "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')"
            goto L_0x0043
        L_0x0049:
            java.lang.String[] r2 = MEDIA_COLUMNS     // Catch:{ SecurityException -> 0x0073 }
            r4 = 0
            r5 = 0
            r0 = r6
            r1 = r12
            android.database.Cursor r0 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ SecurityException -> 0x0073 }
            r10 = r0
            if (r10 == 0) goto L_0x006a
            int r0 = r10.getCount()     // Catch:{ SecurityException -> 0x0073 }
            if (r0 != r9) goto L_0x006a
            r10.moveToFirst()     // Catch:{ SecurityException -> 0x0073 }
            java.lang.String r0 = r10.getString(r9)     // Catch:{ SecurityException -> 0x0073 }
            if (r10 == 0) goto L_0x0068
            r10.close()
        L_0x0068:
            r1 = 0
            return r0
        L_0x006a:
            if (r10 == 0) goto L_0x006f
        L_0x006c:
            r10.close()
        L_0x006f:
            r0 = 0
            goto L_0x0097
        L_0x0071:
            r0 = move-exception
            goto L_0x008c
        L_0x0073:
            r0 = move-exception
            r1 = 0
            if (r14 == 0) goto L_0x0084
            java.lang.String r2 = "audio"
            java.lang.Object r2 = r11.getSystemService((java.lang.String) r2)     // Catch:{ all -> 0x0071 }
            android.media.AudioManager r2 = (android.media.AudioManager) r2     // Catch:{ all -> 0x0071 }
            android.media.IRingtonePlayer r3 = r2.getRingtonePlayer()     // Catch:{ all -> 0x0071 }
            r1 = r3
        L_0x0084:
            if (r1 == 0) goto L_0x0094
            java.lang.String r2 = r1.getTitle(r12)     // Catch:{ RemoteException -> 0x0093 }
            r7 = r2
            goto L_0x0094
        L_0x008c:
            if (r10 == 0) goto L_0x0091
            r10.close()
        L_0x0091:
            r1 = 0
            throw r0
        L_0x0093:
            r2 = move-exception
        L_0x0094:
            if (r10 == 0) goto L_0x006f
            goto L_0x006c
        L_0x0097:
            if (r7 != 0) goto L_0x009d
            java.lang.String r7 = r12.getLastPathSegment()
        L_0x009d:
            goto L_0x00a5
        L_0x009e:
            r0 = 17040944(0x1040630, float:2.424901E-38)
            java.lang.String r7 = r11.getString(r0)
        L_0x00a5:
            if (r7 != 0) goto L_0x00b2
            r0 = 17040945(0x1040631, float:2.4249013E-38)
            java.lang.String r7 = r11.getString(r0)
            if (r7 != 0) goto L_0x00b2
            java.lang.String r7 = ""
        L_0x00b2:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.Ringtone.getTitle(android.content.Context, android.net.Uri, boolean, boolean):java.lang.String");
    }

    @UnsupportedAppUsage
    public void setUri(Uri uri) {
        setUri(uri, (VolumeShaper.Configuration) null);
    }

    public void setUri(Uri uri, VolumeShaper.Configuration volumeShaperConfig) {
        this.mVolumeShaperConfig = volumeShaperConfig;
        destroyLocalPlayer();
        this.mUri = uri;
        if (this.mUri != null) {
            this.mLocalPlayer = new MediaPlayer();
            try {
                this.mLocalPlayer.setDataSource(this.mContext, this.mUri);
                this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
                synchronized (this.mPlaybackSettingsLock) {
                    applyPlaybackProperties_sync();
                }
                if (this.mVolumeShaperConfig != null) {
                    this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
                }
                this.mLocalPlayer.prepare();
            } catch (IOException | SecurityException e) {
                destroyLocalPlayer();
                if (!this.mAllowRemote) {
                    Log.w(TAG, "Remote playback not allowed: " + e);
                }
            }
            if (this.mLocalPlayer != null) {
                Log.d(TAG, "Successfully created local player");
            } else {
                Log.d(TAG, "Problem opening; delegating to remote player");
            }
        }
    }

    @UnsupportedAppUsage
    public Uri getUri() {
        return this.mUri;
    }

    public void play() {
        boolean looping;
        float volume;
        if (this.mLocalPlayer != null) {
            if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) != 0) {
                startLocalPlayer();
            }
        } else if (this.mAllowRemote && this.mRemotePlayer != null && this.mUri != null) {
            Uri canonicalUri = this.mUri.getCanonicalUri();
            synchronized (this.mPlaybackSettingsLock) {
                looping = this.mIsLooping;
                volume = this.mVolume;
            }
            try {
                this.mRemotePlayer.playWithVolumeShaping(this.mRemoteToken, canonicalUri, this.mAudioAttributes, volume, looping, this.mVolumeShaperConfig);
            } catch (RemoteException e) {
                if (!playFallbackRingtone()) {
                    Log.w(TAG, "Problem playing ringtone: " + e);
                }
            }
        } else if (!playFallbackRingtone()) {
            Log.w(TAG, "Neither local nor remote playback available");
        }
    }

    public void stop() {
        if (this.mLocalPlayer != null) {
            destroyLocalPlayer();
        } else if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.stop(this.mRemoteToken);
            } catch (RemoteException e) {
                Log.w(TAG, "Problem stopping ringtone: " + e);
            }
        }
    }

    private void destroyLocalPlayer() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener) null);
            this.mLocalPlayer.reset();
            this.mLocalPlayer.release();
            this.mLocalPlayer = null;
            this.mVolumeShaper = null;
            synchronized (sActiveRingtones) {
                sActiveRingtones.remove(this);
            }
        }
    }

    private void startLocalPlayer() {
        if (this.mLocalPlayer != null) {
            synchronized (sActiveRingtones) {
                sActiveRingtones.add(this);
            }
            this.mLocalPlayer.setOnCompletionListener(this.mCompletionListener);
            this.mLocalPlayer.start();
            if (this.mVolumeShaper != null) {
                this.mVolumeShaper.apply(VolumeShaper.Operation.PLAY);
            }
        }
    }

    public boolean isPlaying() {
        if (this.mLocalPlayer != null) {
            return this.mLocalPlayer.isPlaying();
        }
        if (!this.mAllowRemote || this.mRemotePlayer == null) {
            Log.w(TAG, "Neither local nor remote playback available");
            return false;
        }
        try {
            return this.mRemotePlayer.isPlaying(this.mRemoteToken);
        } catch (RemoteException e) {
            Log.w(TAG, "Problem checking ringtone: " + e);
            return false;
        }
    }

    private boolean playFallbackRingtone() {
        if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) == 0) {
            return false;
        }
        int ringtoneType = RingtoneManager.getDefaultType(this.mUri);
        if (ringtoneType == -1 || RingtoneManager.getActualDefaultRingtoneUri(this.mContext, ringtoneType) != null) {
            try {
                AssetFileDescriptor afd = this.mContext.getResources().openRawResourceFd(R.raw.fallbackring);
                if (afd != null) {
                    this.mLocalPlayer = new MediaPlayer();
                    if (afd.getDeclaredLength() < 0) {
                        this.mLocalPlayer.setDataSource(afd.getFileDescriptor());
                    } else {
                        this.mLocalPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    }
                    this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
                    synchronized (this.mPlaybackSettingsLock) {
                        applyPlaybackProperties_sync();
                    }
                    if (this.mVolumeShaperConfig != null) {
                        this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
                    }
                    this.mLocalPlayer.prepare();
                    startLocalPlayer();
                    afd.close();
                    return true;
                }
                Log.e(TAG, "Could not load fallback ringtone");
                return false;
            } catch (IOException e) {
                destroyLocalPlayer();
                Log.e(TAG, "Failed to open fallback ringtone");
                return false;
            } catch (Resources.NotFoundException e2) {
                Log.e(TAG, "Fallback ringtone does not exist");
                return false;
            }
        } else {
            Log.w(TAG, "not playing fallback for " + this.mUri);
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.release();
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        MyOnCompletionListener() {
        }

        public void onCompletion(MediaPlayer mp) {
            synchronized (Ringtone.sActiveRingtones) {
                Ringtone.sActiveRingtones.remove(Ringtone.this);
            }
            mp.setOnCompletionListener((MediaPlayer.OnCompletionListener) null);
        }
    }
}
