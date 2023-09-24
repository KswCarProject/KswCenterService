package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.net.Uri;
import android.p007os.Binder;
import android.p007os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import com.android.internal.C3132R;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class Ringtone {
    private static final boolean LOGD = true;
    private static final String MEDIA_SELECTION = "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
    private static final String TAG = "Ringtone";
    private final boolean mAllowRemote;
    private final AudioManager mAudioManager;
    private final Context mContext;
    @UnsupportedAppUsage
    private MediaPlayer mLocalPlayer;
    private final IRingtonePlayer mRemotePlayer;
    private final Binder mRemoteToken;
    private String mTitle;
    @UnsupportedAppUsage
    private Uri mUri;
    private VolumeShaper mVolumeShaper;
    private VolumeShaper.Configuration mVolumeShaperConfig;
    private static final String[] MEDIA_COLUMNS = {"_id", "title"};
    private static final ArrayList<Ringtone> sActiveRingtones = new ArrayList<>();
    private final MyOnCompletionListener mCompletionListener = new MyOnCompletionListener();
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setUsage(6).setContentType(4).build();
    private boolean mIsLooping = false;
    private float mVolume = 1.0f;
    private final Object mPlaybackSettingsLock = new Object();

    @UnsupportedAppUsage
    public Ringtone(Context context, boolean allowRemote) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mAllowRemote = allowRemote;
        this.mRemotePlayer = allowRemote ? this.mAudioManager.getRingtonePlayer() : null;
        this.mRemoteToken = allowRemote ? new Binder() : null;
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
        if (attributes == null) {
            throw new IllegalArgumentException("Invalid null AudioAttributes for Ringtone");
        }
        this.mAudioAttributes = attributes;
        setUri(this.mUri, this.mVolumeShaperConfig);
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
        } else if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.setPlaybackProperties(this.mRemoteToken, this.mVolume, this.mIsLooping);
            } catch (RemoteException e) {
                Log.m63w(TAG, "Problem setting playback properties: ", e);
            }
        } else {
            Log.m64w(TAG, "Neither local nor remote player available when applying playback properties");
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x006a, code lost:
        if (r10 != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006c, code lost:
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0094, code lost:
        if (r10 == null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0097, code lost:
        if (r7 != null) goto L7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0099, code lost:
        r7 = r12.getLastPathSegment();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getTitle(Context context, Uri uri, boolean followSettingsUri, boolean allowRemote) {
        ContentResolver res = context.getContentResolver();
        String title = null;
        if (uri != null) {
            String authority = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
            if (!"settings".equals(authority)) {
                Cursor cursor = null;
                try {
                    try {
                        if (MediaStore.AUTHORITY.equals(authority)) {
                            String mediaSelection = allowRemote ? null : MEDIA_SELECTION;
                            cursor = res.query(uri, MEDIA_COLUMNS, mediaSelection, null, null);
                            if (cursor != null && cursor.getCount() == 1) {
                                cursor.moveToFirst();
                                return cursor.getString(1);
                            }
                        }
                    } catch (SecurityException e) {
                        IRingtonePlayer mRemotePlayer = null;
                        if (allowRemote) {
                            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
                            mRemotePlayer = audioManager.getRingtonePlayer();
                        }
                        if (mRemotePlayer != null) {
                            try {
                                title = mRemotePlayer.getTitle(uri);
                            } catch (RemoteException e2) {
                            }
                        }
                    }
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            } else if (followSettingsUri) {
                Uri actualUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(uri));
                String actualTitle = getTitle(context, actualUri, false, allowRemote);
                title = context.getString(C3132R.string.ringtone_default_with_actual, actualTitle);
            }
        } else {
            title = context.getString(C3132R.string.ringtone_silent);
        }
        if (title == null) {
            String title2 = context.getString(C3132R.string.ringtone_unknown);
            return title2 == null ? "" : title2;
        }
        return title;
    }

    @UnsupportedAppUsage
    public void setUri(Uri uri) {
        setUri(uri, null);
    }

    public void setUri(Uri uri, VolumeShaper.Configuration volumeShaperConfig) {
        this.mVolumeShaperConfig = volumeShaperConfig;
        destroyLocalPlayer();
        this.mUri = uri;
        if (this.mUri == null) {
            return;
        }
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
                Log.m64w(TAG, "Remote playback not allowed: " + e);
            }
        }
        if (this.mLocalPlayer != null) {
            Log.m72d(TAG, "Successfully created local player");
        } else {
            Log.m72d(TAG, "Problem opening; delegating to remote player");
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
                    Log.m64w(TAG, "Problem playing ringtone: " + e);
                }
            }
        } else if (!playFallbackRingtone()) {
            Log.m64w(TAG, "Neither local nor remote playback available");
        }
    }

    public void stop() {
        if (this.mLocalPlayer != null) {
            destroyLocalPlayer();
        } else if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.stop(this.mRemoteToken);
            } catch (RemoteException e) {
                Log.m64w(TAG, "Problem stopping ringtone: " + e);
            }
        }
    }

    private void destroyLocalPlayer() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.setOnCompletionListener(null);
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
        if (this.mLocalPlayer == null) {
            return;
        }
        synchronized (sActiveRingtones) {
            sActiveRingtones.add(this);
        }
        this.mLocalPlayer.setOnCompletionListener(this.mCompletionListener);
        this.mLocalPlayer.start();
        if (this.mVolumeShaper != null) {
            this.mVolumeShaper.apply(VolumeShaper.Operation.PLAY);
        }
    }

    public boolean isPlaying() {
        if (this.mLocalPlayer != null) {
            return this.mLocalPlayer.isPlaying();
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                return this.mRemotePlayer.isPlaying(this.mRemoteToken);
            } catch (RemoteException e) {
                Log.m64w(TAG, "Problem checking ringtone: " + e);
                return false;
            }
        }
        Log.m64w(TAG, "Neither local nor remote playback available");
        return false;
    }

    private boolean playFallbackRingtone() {
        if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) != 0) {
            int ringtoneType = RingtoneManager.getDefaultType(this.mUri);
            if (ringtoneType == -1 || RingtoneManager.getActualDefaultRingtoneUri(this.mContext, ringtoneType) != null) {
                try {
                    AssetFileDescriptor afd = this.mContext.getResources().openRawResourceFd(C3132R.raw.fallbackring);
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
                    Log.m70e(TAG, "Could not load fallback ringtone");
                    return false;
                } catch (Resources.NotFoundException e) {
                    Log.m70e(TAG, "Fallback ringtone does not exist");
                    return false;
                } catch (IOException e2) {
                    destroyLocalPlayer();
                    Log.m70e(TAG, "Failed to open fallback ringtone");
                    return false;
                }
            }
            Log.m64w(TAG, "not playing fallback for " + this.mUri);
            return false;
        }
        return false;
    }

    void setTitle(String title) {
        this.mTitle = title;
    }

    protected void finalize() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.release();
        }
    }

    /* loaded from: classes3.dex */
    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        MyOnCompletionListener() {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mp) {
            synchronized (Ringtone.sActiveRingtones) {
                Ringtone.sActiveRingtones.remove(Ringtone.this);
            }
            mp.setOnCompletionListener(null);
        }
    }
}
