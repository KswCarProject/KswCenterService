package android.media;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.media.IAudioFocusDispatcher;
import android.media.IAudioRoutesObserver;
import android.media.IAudioServerStateDispatcher;
import android.media.IPlaybackConfigDispatcher;
import android.media.IRecordingConfigDispatcher;
import android.media.IRingtonePlayer;
import android.media.IVolumeController;
import android.media.PlayerBase;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.IMediaProjection;
import android.net.Uri;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface IAudioService extends IInterface {
    int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) throws RemoteException;

    int addMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void adjustStreamVolume(int i, int i2, int i3, String str) throws RemoteException;

    void adjustSuggestedStreamVolume(int i, int i2, int i3, String str, String str2) throws RemoteException;

    void avrcpSupportsAbsoluteVolume(String str, boolean z) throws RemoteException;

    void disableRingtoneSync(int i) throws RemoteException;

    void disableSafeMediaVolume(String str) throws RemoteException;

    int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void forceRemoteSubmixFullVolume(boolean z, IBinder iBinder) throws RemoteException;

    void forceVolumeControlStream(int i, IBinder iBinder) throws RemoteException;

    List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException;

    List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException;

    List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException;

    List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException;

    int getCurrentAudioFocus() throws RemoteException;

    int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) throws RemoteException;

    int getLastAudibleStreamVolume(int i) throws RemoteException;

    int getMaxVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException;

    int getMinVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException;

    int getMode() throws RemoteException;

    int getRingerModeExternal() throws RemoteException;

    int getRingerModeInternal() throws RemoteException;

    IRingtonePlayer getRingtonePlayer() throws RemoteException;

    @UnsupportedAppUsage
    int getStreamMaxVolume(int i) throws RemoteException;

    int getStreamMinVolume(int i) throws RemoteException;

    @UnsupportedAppUsage
    int getStreamVolume(int i) throws RemoteException;

    int getUiSoundsStreamType() throws RemoteException;

    int getVibrateSetting(int i) throws RemoteException;

    int getVolumeIndexForAttributes(AudioAttributes audioAttributes) throws RemoteException;

    void handleBluetoothA2dpActiveDeviceChange(BluetoothDevice bluetoothDevice, int i, int i2, boolean z, int i3) throws RemoteException;

    void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice bluetoothDevice) throws RemoteException;

    boolean hasHapticChannels(Uri uri) throws RemoteException;

    boolean hasRegisteredDynamicPolicy() throws RemoteException;

    boolean isAudioServerRunning() throws RemoteException;

    boolean isBluetoothA2dpOn() throws RemoteException;

    boolean isBluetoothScoOn() throws RemoteException;

    boolean isCameraSoundForced() throws RemoteException;

    boolean isHdmiSystemAudioSupported() throws RemoteException;

    boolean isMasterMute() throws RemoteException;

    boolean isSpeakerphoneOn() throws RemoteException;

    boolean isStreamAffectedByMute(int i) throws RemoteException;

    boolean isStreamAffectedByRingerMode(int i) throws RemoteException;

    boolean isStreamMute(int i) throws RemoteException;

    boolean isValidRingerMode(int i) throws RemoteException;

    boolean loadSoundEffects() throws RemoteException;

    void notifyVolumeControllerVisible(IVolumeController iVolumeController, boolean z) throws RemoteException;

    void playSoundEffect(int i) throws RemoteException;

    void playSoundEffectVolume(int i, float f) throws RemoteException;

    void playerAttributes(int i, AudioAttributes audioAttributes) throws RemoteException;

    void playerEvent(int i, int i2) throws RemoteException;

    void playerHasOpPlayAudio(int i, boolean z) throws RemoteException;

    void recorderEvent(int i, int i2) throws RemoteException;

    String registerAudioPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback, boolean z, boolean z2, boolean z3, boolean z4, IMediaProjection iMediaProjection) throws RemoteException;

    void registerAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException;

    void registerPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException;

    void registerRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException;

    void releasePlayer(int i) throws RemoteException;

    void releaseRecorder(int i) throws RemoteException;

    void reloadAudioSettings() throws RemoteException;

    int removeMixForPolicy(AudioPolicyConfig audioPolicyConfig, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    int removeUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i) throws RemoteException;

    int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, IAudioPolicyCallback iAudioPolicyCallback, int i3) throws RemoteException;

    void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice bluetoothDevice, int i, int i2, boolean z, int i3) throws RemoteException;

    void setBluetoothA2dpOn(boolean z) throws RemoteException;

    void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice bluetoothDevice, int i, boolean z, int i2) throws RemoteException;

    void setBluetoothScoOn(boolean z) throws RemoteException;

    int setFocusPropertiesForPolicy(int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i, IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    int setHdmiSystemAudioSupported(boolean z) throws RemoteException;

    void setMasterMute(boolean z, int i, String str, int i2) throws RemoteException;

    void setMicrophoneMute(boolean z, String str, int i) throws RemoteException;

    void setMode(int i, IBinder iBinder, String str) throws RemoteException;

    void setRingerModeExternal(int i, String str) throws RemoteException;

    void setRingerModeInternal(int i, String str) throws RemoteException;

    void setRingtonePlayer(IRingtonePlayer iRingtonePlayer) throws RemoteException;

    void setSpeakerphoneOn(boolean z) throws RemoteException;

    @UnsupportedAppUsage
    void setStreamVolume(int i, int i2, int i3, String str) throws RemoteException;

    int setUidDeviceAffinity(IAudioPolicyCallback iAudioPolicyCallback, int i, int[] iArr, String[] strArr) throws RemoteException;

    void setVibrateSetting(int i, int i2) throws RemoteException;

    void setVolumeController(IVolumeController iVolumeController) throws RemoteException;

    void setVolumeIndexForAttributes(AudioAttributes audioAttributes, int i, int i2, String str) throws RemoteException;

    void setVolumePolicy(VolumePolicy volumePolicy) throws RemoteException;

    void setWiredDeviceConnectionState(int i, int i2, String str, String str2, String str3) throws RemoteException;

    boolean shouldVibrate(int i) throws RemoteException;

    void startBluetoothSco(IBinder iBinder, int i) throws RemoteException;

    void startBluetoothScoVirtualCall(IBinder iBinder) throws RemoteException;

    @UnsupportedAppUsage
    AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver iAudioRoutesObserver) throws RemoteException;

    void stopBluetoothSco(IBinder iBinder) throws RemoteException;

    int trackPlayer(PlayerBase.PlayerIdCard playerIdCard) throws RemoteException;

    int trackRecorder(IBinder iBinder) throws RemoteException;

    void unloadSoundEffects() throws RemoteException;

    void unregisterAudioFocusClient(String str) throws RemoteException;

    void unregisterAudioPolicy(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void unregisterAudioPolicyAsync(IAudioPolicyCallback iAudioPolicyCallback) throws RemoteException;

    void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher iAudioServerStateDispatcher) throws RemoteException;

    void unregisterPlaybackCallback(IPlaybackConfigDispatcher iPlaybackConfigDispatcher) throws RemoteException;

    void unregisterRecordingCallback(IRecordingConfigDispatcher iRecordingConfigDispatcher) throws RemoteException;

    /* loaded from: classes3.dex */
    public static class Default implements IAudioService {
        @Override // android.media.IAudioService
        public int trackPlayer(PlayerBase.PlayerIdCard pic) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playerAttributes(int piid, AudioAttributes attr) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playerEvent(int piid, int event) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void releasePlayer(int piid) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int trackRecorder(IBinder recorder) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void recorderEvent(int riid, int event) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void releaseRecorder(int riid) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags, String callingPackage, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setStreamVolume(int streamType, int index, int flags, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isStreamMute(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isMasterMute() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMasterMute(boolean mute, int flags, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getStreamVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMinVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getStreamMaxVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void setVolumeIndexForAttributes(AudioAttributes aa, int index, int flags, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getMaxVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getMinVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void setMicrophoneMute(boolean on, String callingPackage, int userId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getRingerModeExternal() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int getRingerModeInternal() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isValidRingerMode(int ringerMode) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getVibrateSetting(int vibrateType) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean shouldVibrate(int vibrateType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getMode() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playSoundEffect(int effectType) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean loadSoundEffects() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void unloadSoundEffects() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void reloadAudioSettings() throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void avrcpSupportsAbsoluteVolume(String address, boolean support) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setSpeakerphoneOn(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isSpeakerphoneOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothScoOn(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothScoOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setBluetoothA2dpOn(boolean on) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isBluetoothA2dpOn() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int requestAudioFocus(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, int flags, IAudioPolicyCallback pcb, int sdk) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int abandonAudioFocus(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioFocusClient(String clientId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getCurrentAudioFocus() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void stopBluetoothSco(IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public IRingtonePlayer getRingtonePlayer() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public int getUiSoundsStreamType() throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setWiredDeviceConnectionState(int type, int state, String address, String name, String caller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice device) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void handleBluetoothA2dpActiveDeviceChange(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public boolean isCameraSoundForced() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void setVolumeController(IVolumeController controller) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int setHdmiSystemAudioSupported(boolean on) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean isHdmiSystemAudioSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public String registerAudioPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb, boolean hasFocusListener, boolean isFocusPolicy, boolean isTestFocusPolicy, boolean isVolumeController, IMediaProjection projection) throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int addMixForPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeMixForPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void setVolumePolicy(VolumePolicy policy) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean hasRegisteredDynamicPolicy() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
            return null;
        }

        @Override // android.media.IAudioService
        public void disableRingtoneSync(int userId) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice device, int state, boolean suppressNoisyIntent, int musicDevice) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void setFocusRequestResultFromExtPolicy(AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
        }

        @Override // android.media.IAudioService
        public boolean isAudioServerRunning() throws RemoteException {
            return false;
        }

        @Override // android.media.IAudioService
        public int setUidDeviceAffinity(IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
            return 0;
        }

        @Override // android.media.IAudioService
        public boolean hasHapticChannels(Uri uri) throws RemoteException {
            return false;
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Stub extends Binder implements IAudioService {
        private static final String DESCRIPTOR = "android.media.IAudioService";
        static final int TRANSACTION_abandonAudioFocus = 49;
        static final int TRANSACTION_addMixForPolicy = 74;
        static final int TRANSACTION_adjustStreamVolume = 9;
        static final int TRANSACTION_adjustSuggestedStreamVolume = 8;
        static final int TRANSACTION_avrcpSupportsAbsoluteVolume = 41;
        static final int TRANSACTION_disableRingtoneSync = 85;
        static final int TRANSACTION_disableSafeMediaVolume = 68;
        static final int TRANSACTION_dispatchFocusChange = 87;
        static final int TRANSACTION_forceRemoteSubmixFullVolume = 12;
        static final int TRANSACTION_forceVolumeControlStream = 55;
        static final int TRANSACTION_getActivePlaybackConfigurations = 84;
        static final int TRANSACTION_getActiveRecordingConfigurations = 81;
        static final int TRANSACTION_getAudioProductStrategies = 24;
        static final int TRANSACTION_getAudioVolumeGroups = 18;
        static final int TRANSACTION_getCurrentAudioFocus = 51;
        static final int TRANSACTION_getFocusRampTimeMs = 86;
        static final int TRANSACTION_getLastAudibleStreamVolume = 23;
        static final int TRANSACTION_getMaxVolumeIndexForAttributes = 21;
        static final int TRANSACTION_getMinVolumeIndexForAttributes = 22;
        static final int TRANSACTION_getMode = 35;
        static final int TRANSACTION_getRingerModeExternal = 28;
        static final int TRANSACTION_getRingerModeInternal = 29;
        static final int TRANSACTION_getRingtonePlayer = 57;
        static final int TRANSACTION_getStreamMaxVolume = 17;
        static final int TRANSACTION_getStreamMinVolume = 16;
        static final int TRANSACTION_getStreamVolume = 15;
        static final int TRANSACTION_getUiSoundsStreamType = 58;
        static final int TRANSACTION_getVibrateSetting = 32;
        static final int TRANSACTION_getVolumeIndexForAttributes = 20;
        static final int TRANSACTION_handleBluetoothA2dpActiveDeviceChange = 61;
        static final int TRANSACTION_handleBluetoothA2dpDeviceConfigChange = 60;
        static final int TRANSACTION_hasHapticChannels = 97;
        static final int TRANSACTION_hasRegisteredDynamicPolicy = 78;
        static final int TRANSACTION_isAudioServerRunning = 94;
        static final int TRANSACTION_isBluetoothA2dpOn = 47;
        static final int TRANSACTION_isBluetoothScoOn = 45;
        static final int TRANSACTION_isCameraSoundForced = 63;
        static final int TRANSACTION_isHdmiSystemAudioSupported = 70;
        static final int TRANSACTION_isMasterMute = 13;
        static final int TRANSACTION_isSpeakerphoneOn = 43;
        static final int TRANSACTION_isStreamAffectedByMute = 67;
        static final int TRANSACTION_isStreamAffectedByRingerMode = 66;
        static final int TRANSACTION_isStreamMute = 11;
        static final int TRANSACTION_isValidRingerMode = 30;
        static final int TRANSACTION_loadSoundEffects = 38;
        static final int TRANSACTION_notifyVolumeControllerVisible = 65;
        static final int TRANSACTION_playSoundEffect = 36;
        static final int TRANSACTION_playSoundEffectVolume = 37;
        static final int TRANSACTION_playerAttributes = 2;
        static final int TRANSACTION_playerEvent = 3;
        static final int TRANSACTION_playerHasOpPlayAudio = 88;
        static final int TRANSACTION_recorderEvent = 6;
        static final int TRANSACTION_registerAudioPolicy = 71;
        static final int TRANSACTION_registerAudioServerStateDispatcher = 92;
        static final int TRANSACTION_registerPlaybackCallback = 82;
        static final int TRANSACTION_registerRecordingCallback = 79;
        static final int TRANSACTION_releasePlayer = 4;
        static final int TRANSACTION_releaseRecorder = 7;
        static final int TRANSACTION_reloadAudioSettings = 40;
        static final int TRANSACTION_removeMixForPolicy = 75;
        static final int TRANSACTION_removeUidDeviceAffinity = 96;
        static final int TRANSACTION_requestAudioFocus = 48;

        /* renamed from: TRANSACTION_setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent */
        static final int f117x36cf6f62 = 90;
        static final int TRANSACTION_setBluetoothA2dpOn = 46;
        static final int TRANSACTION_setBluetoothHearingAidDeviceConnectionState = 89;
        static final int TRANSACTION_setBluetoothScoOn = 44;
        static final int TRANSACTION_setFocusPropertiesForPolicy = 76;
        static final int TRANSACTION_setFocusRequestResultFromExtPolicy = 91;
        static final int TRANSACTION_setHdmiSystemAudioSupported = 69;
        static final int TRANSACTION_setMasterMute = 14;
        static final int TRANSACTION_setMicrophoneMute = 25;
        static final int TRANSACTION_setMode = 34;
        static final int TRANSACTION_setRingerModeExternal = 26;
        static final int TRANSACTION_setRingerModeInternal = 27;
        static final int TRANSACTION_setRingtonePlayer = 56;
        static final int TRANSACTION_setSpeakerphoneOn = 42;
        static final int TRANSACTION_setStreamVolume = 10;
        static final int TRANSACTION_setUidDeviceAffinity = 95;
        static final int TRANSACTION_setVibrateSetting = 31;
        static final int TRANSACTION_setVolumeController = 64;
        static final int TRANSACTION_setVolumeIndexForAttributes = 19;
        static final int TRANSACTION_setVolumePolicy = 77;
        static final int TRANSACTION_setWiredDeviceConnectionState = 59;
        static final int TRANSACTION_shouldVibrate = 33;
        static final int TRANSACTION_startBluetoothSco = 52;
        static final int TRANSACTION_startBluetoothScoVirtualCall = 53;
        static final int TRANSACTION_startWatchingRoutes = 62;
        static final int TRANSACTION_stopBluetoothSco = 54;
        static final int TRANSACTION_trackPlayer = 1;
        static final int TRANSACTION_trackRecorder = 5;
        static final int TRANSACTION_unloadSoundEffects = 39;
        static final int TRANSACTION_unregisterAudioFocusClient = 50;
        static final int TRANSACTION_unregisterAudioPolicy = 73;
        static final int TRANSACTION_unregisterAudioPolicyAsync = 72;
        static final int TRANSACTION_unregisterAudioServerStateDispatcher = 93;
        static final int TRANSACTION_unregisterPlaybackCallback = 83;
        static final int TRANSACTION_unregisterRecordingCallback = 80;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAudioService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAudioService)) {
                return (IAudioService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "trackPlayer";
                case 2:
                    return "playerAttributes";
                case 3:
                    return "playerEvent";
                case 4:
                    return "releasePlayer";
                case 5:
                    return "trackRecorder";
                case 6:
                    return "recorderEvent";
                case 7:
                    return "releaseRecorder";
                case 8:
                    return "adjustSuggestedStreamVolume";
                case 9:
                    return "adjustStreamVolume";
                case 10:
                    return "setStreamVolume";
                case 11:
                    return "isStreamMute";
                case 12:
                    return "forceRemoteSubmixFullVolume";
                case 13:
                    return "isMasterMute";
                case 14:
                    return "setMasterMute";
                case 15:
                    return "getStreamVolume";
                case 16:
                    return "getStreamMinVolume";
                case 17:
                    return "getStreamMaxVolume";
                case 18:
                    return "getAudioVolumeGroups";
                case 19:
                    return "setVolumeIndexForAttributes";
                case 20:
                    return "getVolumeIndexForAttributes";
                case 21:
                    return "getMaxVolumeIndexForAttributes";
                case 22:
                    return "getMinVolumeIndexForAttributes";
                case 23:
                    return "getLastAudibleStreamVolume";
                case 24:
                    return "getAudioProductStrategies";
                case 25:
                    return "setMicrophoneMute";
                case 26:
                    return "setRingerModeExternal";
                case 27:
                    return "setRingerModeInternal";
                case 28:
                    return "getRingerModeExternal";
                case 29:
                    return "getRingerModeInternal";
                case 30:
                    return "isValidRingerMode";
                case 31:
                    return "setVibrateSetting";
                case 32:
                    return "getVibrateSetting";
                case 33:
                    return "shouldVibrate";
                case 34:
                    return "setMode";
                case 35:
                    return "getMode";
                case 36:
                    return "playSoundEffect";
                case 37:
                    return "playSoundEffectVolume";
                case 38:
                    return "loadSoundEffects";
                case 39:
                    return "unloadSoundEffects";
                case 40:
                    return "reloadAudioSettings";
                case 41:
                    return "avrcpSupportsAbsoluteVolume";
                case 42:
                    return "setSpeakerphoneOn";
                case 43:
                    return "isSpeakerphoneOn";
                case 44:
                    return "setBluetoothScoOn";
                case 45:
                    return "isBluetoothScoOn";
                case 46:
                    return "setBluetoothA2dpOn";
                case 47:
                    return "isBluetoothA2dpOn";
                case 48:
                    return "requestAudioFocus";
                case 49:
                    return "abandonAudioFocus";
                case 50:
                    return "unregisterAudioFocusClient";
                case 51:
                    return "getCurrentAudioFocus";
                case 52:
                    return "startBluetoothSco";
                case 53:
                    return "startBluetoothScoVirtualCall";
                case 54:
                    return "stopBluetoothSco";
                case 55:
                    return "forceVolumeControlStream";
                case 56:
                    return "setRingtonePlayer";
                case 57:
                    return "getRingtonePlayer";
                case 58:
                    return "getUiSoundsStreamType";
                case 59:
                    return "setWiredDeviceConnectionState";
                case 60:
                    return "handleBluetoothA2dpDeviceConfigChange";
                case 61:
                    return "handleBluetoothA2dpActiveDeviceChange";
                case 62:
                    return "startWatchingRoutes";
                case 63:
                    return "isCameraSoundForced";
                case 64:
                    return "setVolumeController";
                case 65:
                    return "notifyVolumeControllerVisible";
                case 66:
                    return "isStreamAffectedByRingerMode";
                case 67:
                    return "isStreamAffectedByMute";
                case 68:
                    return "disableSafeMediaVolume";
                case 69:
                    return "setHdmiSystemAudioSupported";
                case 70:
                    return "isHdmiSystemAudioSupported";
                case 71:
                    return "registerAudioPolicy";
                case 72:
                    return "unregisterAudioPolicyAsync";
                case 73:
                    return "unregisterAudioPolicy";
                case 74:
                    return "addMixForPolicy";
                case 75:
                    return "removeMixForPolicy";
                case 76:
                    return "setFocusPropertiesForPolicy";
                case 77:
                    return "setVolumePolicy";
                case 78:
                    return "hasRegisteredDynamicPolicy";
                case 79:
                    return "registerRecordingCallback";
                case 80:
                    return "unregisterRecordingCallback";
                case 81:
                    return "getActiveRecordingConfigurations";
                case 82:
                    return "registerPlaybackCallback";
                case 83:
                    return "unregisterPlaybackCallback";
                case 84:
                    return "getActivePlaybackConfigurations";
                case 85:
                    return "disableRingtoneSync";
                case 86:
                    return "getFocusRampTimeMs";
                case 87:
                    return "dispatchFocusChange";
                case 88:
                    return "playerHasOpPlayAudio";
                case 89:
                    return "setBluetoothHearingAidDeviceConnectionState";
                case 90:
                    return "setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent";
                case 91:
                    return "setFocusRequestResultFromExtPolicy";
                case 92:
                    return "registerAudioServerStateDispatcher";
                case 93:
                    return "unregisterAudioServerStateDispatcher";
                case 94:
                    return "isAudioServerRunning";
                case 95:
                    return "setUidDeviceAffinity";
                case 96:
                    return "removeUidDeviceAffinity";
                case 97:
                    return "hasHapticChannels";
                default:
                    return null;
            }
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _arg2;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    PlayerBase.PlayerIdCard _arg0 = data.readInt() != 0 ? PlayerBase.PlayerIdCard.CREATOR.createFromParcel(data) : null;
                    int _result = trackPlayer(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg02 = data.readInt();
                    AudioAttributes _arg1 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    playerAttributes(_arg02, _arg1);
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    playerEvent(_arg03, _arg12);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg04 = data.readInt();
                    releasePlayer(_arg04);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg05 = data.readStrongBinder();
                    int _result2 = trackRecorder(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg06 = data.readInt();
                    int _arg13 = data.readInt();
                    recorderEvent(_arg06, _arg13);
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg07 = data.readInt();
                    releaseRecorder(_arg07);
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg08 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg22 = data.readInt();
                    String _arg3 = data.readString();
                    String _arg4 = data.readString();
                    adjustSuggestedStreamVolume(_arg08, _arg14, _arg22, _arg3, _arg4);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg09 = data.readInt();
                    int _arg15 = data.readInt();
                    int _arg23 = data.readInt();
                    String _arg32 = data.readString();
                    adjustStreamVolume(_arg09, _arg15, _arg23, _arg32);
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg010 = data.readInt();
                    int _arg16 = data.readInt();
                    int _arg24 = data.readInt();
                    String _arg33 = data.readString();
                    setStreamVolume(_arg010, _arg16, _arg24, _arg33);
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg011 = data.readInt();
                    boolean isStreamMute = isStreamMute(_arg011);
                    reply.writeNoException();
                    reply.writeInt(isStreamMute ? 1 : 0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    IBinder _arg17 = data.readStrongBinder();
                    forceRemoteSubmixFullVolume(_arg2, _arg17);
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isMasterMute = isMasterMute();
                    reply.writeNoException();
                    reply.writeInt(isMasterMute ? 1 : 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    int _arg18 = data.readInt();
                    String _arg25 = data.readString();
                    int _arg34 = data.readInt();
                    setMasterMute(_arg2, _arg18, _arg25, _arg34);
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg012 = data.readInt();
                    int _result3 = getStreamVolume(_arg012);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg013 = data.readInt();
                    int _result4 = getStreamMinVolume(_arg013);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg014 = data.readInt();
                    int _result5 = getStreamMaxVolume(_arg014);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    List<AudioVolumeGroup> _result6 = getAudioVolumeGroups();
                    reply.writeNoException();
                    reply.writeTypedList(_result6);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    AudioAttributes _arg015 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    AudioAttributes _arg016 = _arg015;
                    int _arg19 = data.readInt();
                    int _arg26 = data.readInt();
                    String _arg35 = data.readString();
                    setVolumeIndexForAttributes(_arg016, _arg19, _arg26, _arg35);
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    AudioAttributes _arg017 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    int _result7 = getVolumeIndexForAttributes(_arg017);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    AudioAttributes _arg018 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    int _result8 = getMaxVolumeIndexForAttributes(_arg018);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    AudioAttributes _arg019 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    int _result9 = getMinVolumeIndexForAttributes(_arg019);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg020 = data.readInt();
                    int _result10 = getLastAudibleStreamVolume(_arg020);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    List<AudioProductStrategy> _result11 = getAudioProductStrategies();
                    reply.writeNoException();
                    reply.writeTypedList(_result11);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    String _arg110 = data.readString();
                    setMicrophoneMute(_arg2, _arg110, data.readInt());
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg021 = data.readInt();
                    String _arg111 = data.readString();
                    setRingerModeExternal(_arg021, _arg111);
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg022 = data.readInt();
                    String _arg112 = data.readString();
                    setRingerModeInternal(_arg022, _arg112);
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    int _result12 = getRingerModeExternal();
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    int _result13 = getRingerModeInternal();
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg023 = data.readInt();
                    boolean isValidRingerMode = isValidRingerMode(_arg023);
                    reply.writeNoException();
                    reply.writeInt(isValidRingerMode ? 1 : 0);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg024 = data.readInt();
                    int _arg113 = data.readInt();
                    setVibrateSetting(_arg024, _arg113);
                    reply.writeNoException();
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg025 = data.readInt();
                    int _result14 = getVibrateSetting(_arg025);
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg026 = data.readInt();
                    boolean shouldVibrate = shouldVibrate(_arg026);
                    reply.writeNoException();
                    reply.writeInt(shouldVibrate ? 1 : 0);
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg027 = data.readInt();
                    IBinder _arg114 = data.readStrongBinder();
                    setMode(_arg027, _arg114, data.readString());
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    int _result15 = getMode();
                    reply.writeNoException();
                    reply.writeInt(_result15);
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg028 = data.readInt();
                    playSoundEffect(_arg028);
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg029 = data.readInt();
                    float _arg115 = data.readFloat();
                    playSoundEffectVolume(_arg029, _arg115);
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    boolean loadSoundEffects = loadSoundEffects();
                    reply.writeNoException();
                    reply.writeInt(loadSoundEffects ? 1 : 0);
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    unloadSoundEffects();
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    reloadAudioSettings();
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg030 = data.readString();
                    _arg2 = data.readInt() != 0;
                    avrcpSupportsAbsoluteVolume(_arg030, _arg2);
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    setSpeakerphoneOn(_arg2);
                    reply.writeNoException();
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isSpeakerphoneOn = isSpeakerphoneOn();
                    reply.writeNoException();
                    reply.writeInt(isSpeakerphoneOn ? 1 : 0);
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    setBluetoothScoOn(_arg2);
                    reply.writeNoException();
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isBluetoothScoOn = isBluetoothScoOn();
                    reply.writeNoException();
                    reply.writeInt(isBluetoothScoOn ? 1 : 0);
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    setBluetoothA2dpOn(_arg2);
                    reply.writeNoException();
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isBluetoothA2dpOn = isBluetoothA2dpOn();
                    reply.writeNoException();
                    reply.writeInt(isBluetoothA2dpOn ? 1 : 0);
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    AudioAttributes _arg031 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    int _arg116 = data.readInt();
                    IBinder _arg27 = data.readStrongBinder();
                    IAudioFocusDispatcher _arg36 = IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
                    String _arg42 = data.readString();
                    String _arg5 = data.readString();
                    int _arg6 = data.readInt();
                    IAudioPolicyCallback _arg7 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg8 = data.readInt();
                    int _result16 = requestAudioFocus(_arg031, _arg116, _arg27, _arg36, _arg42, _arg5, _arg6, _arg7, _arg8);
                    reply.writeNoException();
                    reply.writeInt(_result16);
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioFocusDispatcher _arg032 = IAudioFocusDispatcher.Stub.asInterface(data.readStrongBinder());
                    String _arg117 = data.readString();
                    AudioAttributes _arg28 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    String _arg37 = data.readString();
                    int _result17 = abandonAudioFocus(_arg032, _arg117, _arg28, _arg37);
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg033 = data.readString();
                    unregisterAudioFocusClient(_arg033);
                    reply.writeNoException();
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    int _result18 = getCurrentAudioFocus();
                    reply.writeNoException();
                    reply.writeInt(_result18);
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg034 = data.readStrongBinder();
                    int _arg118 = data.readInt();
                    startBluetoothSco(_arg034, _arg118);
                    reply.writeNoException();
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg035 = data.readStrongBinder();
                    startBluetoothScoVirtualCall(_arg035);
                    reply.writeNoException();
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    IBinder _arg036 = data.readStrongBinder();
                    stopBluetoothSco(_arg036);
                    reply.writeNoException();
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg037 = data.readInt();
                    IBinder _arg119 = data.readStrongBinder();
                    forceVolumeControlStream(_arg037, _arg119);
                    reply.writeNoException();
                    return true;
                case 56:
                    data.enforceInterface(DESCRIPTOR);
                    IRingtonePlayer _arg038 = IRingtonePlayer.Stub.asInterface(data.readStrongBinder());
                    setRingtonePlayer(_arg038);
                    reply.writeNoException();
                    return true;
                case 57:
                    data.enforceInterface(DESCRIPTOR);
                    IRingtonePlayer _result19 = getRingtonePlayer();
                    reply.writeNoException();
                    reply.writeStrongBinder(_result19 != null ? _result19.asBinder() : null);
                    return true;
                case 58:
                    data.enforceInterface(DESCRIPTOR);
                    int _result20 = getUiSoundsStreamType();
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 59:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg039 = data.readInt();
                    int _arg120 = data.readInt();
                    String _arg29 = data.readString();
                    String _arg38 = data.readString();
                    String _arg43 = data.readString();
                    setWiredDeviceConnectionState(_arg039, _arg120, _arg29, _arg38, _arg43);
                    reply.writeNoException();
                    return true;
                case 60:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg040 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    handleBluetoothA2dpDeviceConfigChange(_arg040);
                    reply.writeNoException();
                    return true;
                case 61:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg041 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _arg121 = data.readInt();
                    int _arg210 = data.readInt();
                    boolean _arg39 = data.readInt() != 0;
                    int _arg44 = data.readInt();
                    handleBluetoothA2dpActiveDeviceChange(_arg041, _arg121, _arg210, _arg39, _arg44);
                    reply.writeNoException();
                    return true;
                case 62:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioRoutesObserver _arg042 = IAudioRoutesObserver.Stub.asInterface(data.readStrongBinder());
                    AudioRoutesInfo _result21 = startWatchingRoutes(_arg042);
                    reply.writeNoException();
                    if (_result21 != null) {
                        reply.writeInt(1);
                        _result21.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 63:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isCameraSoundForced = isCameraSoundForced();
                    reply.writeNoException();
                    reply.writeInt(isCameraSoundForced ? 1 : 0);
                    return true;
                case 64:
                    data.enforceInterface(DESCRIPTOR);
                    IVolumeController _arg043 = IVolumeController.Stub.asInterface(data.readStrongBinder());
                    setVolumeController(_arg043);
                    reply.writeNoException();
                    return true;
                case 65:
                    data.enforceInterface(DESCRIPTOR);
                    IVolumeController _arg044 = IVolumeController.Stub.asInterface(data.readStrongBinder());
                    _arg2 = data.readInt() != 0;
                    notifyVolumeControllerVisible(_arg044, _arg2);
                    reply.writeNoException();
                    return true;
                case 66:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg045 = data.readInt();
                    boolean isStreamAffectedByRingerMode = isStreamAffectedByRingerMode(_arg045);
                    reply.writeNoException();
                    reply.writeInt(isStreamAffectedByRingerMode ? 1 : 0);
                    return true;
                case 67:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg046 = data.readInt();
                    boolean isStreamAffectedByMute = isStreamAffectedByMute(_arg046);
                    reply.writeNoException();
                    reply.writeInt(isStreamAffectedByMute ? 1 : 0);
                    return true;
                case 68:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg047 = data.readString();
                    disableSafeMediaVolume(_arg047);
                    reply.writeNoException();
                    return true;
                case 69:
                    data.enforceInterface(DESCRIPTOR);
                    _arg2 = data.readInt() != 0;
                    int _result22 = setHdmiSystemAudioSupported(_arg2);
                    reply.writeNoException();
                    reply.writeInt(_result22);
                    return true;
                case 70:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isHdmiSystemAudioSupported = isHdmiSystemAudioSupported();
                    reply.writeNoException();
                    reply.writeInt(isHdmiSystemAudioSupported ? 1 : 0);
                    return true;
                case 71:
                    data.enforceInterface(DESCRIPTOR);
                    AudioPolicyConfig _arg048 = data.readInt() != 0 ? AudioPolicyConfig.CREATOR.createFromParcel(data) : null;
                    IAudioPolicyCallback _arg122 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    boolean _arg211 = data.readInt() != 0;
                    boolean _arg310 = data.readInt() != 0;
                    boolean _arg45 = data.readInt() != 0;
                    boolean _arg52 = data.readInt() != 0;
                    IMediaProjection _arg62 = IMediaProjection.Stub.asInterface(data.readStrongBinder());
                    String _result23 = registerAudioPolicy(_arg048, _arg122, _arg211, _arg310, _arg45, _arg52, _arg62);
                    reply.writeNoException();
                    reply.writeString(_result23);
                    return true;
                case 72:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioPolicyCallback _arg049 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    unregisterAudioPolicyAsync(_arg049);
                    return true;
                case 73:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioPolicyCallback _arg050 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    unregisterAudioPolicy(_arg050);
                    reply.writeNoException();
                    return true;
                case 74:
                    data.enforceInterface(DESCRIPTOR);
                    AudioPolicyConfig _arg051 = data.readInt() != 0 ? AudioPolicyConfig.CREATOR.createFromParcel(data) : null;
                    AudioPolicyConfig _arg052 = _arg051;
                    IAudioPolicyCallback _arg123 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _result24 = addMixForPolicy(_arg052, _arg123);
                    reply.writeNoException();
                    reply.writeInt(_result24);
                    return true;
                case 75:
                    data.enforceInterface(DESCRIPTOR);
                    AudioPolicyConfig _arg053 = data.readInt() != 0 ? AudioPolicyConfig.CREATOR.createFromParcel(data) : null;
                    AudioPolicyConfig _arg054 = _arg053;
                    IAudioPolicyCallback _arg124 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _result25 = removeMixForPolicy(_arg054, _arg124);
                    reply.writeNoException();
                    reply.writeInt(_result25);
                    return true;
                case 76:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg055 = data.readInt();
                    IAudioPolicyCallback _arg125 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _result26 = setFocusPropertiesForPolicy(_arg055, _arg125);
                    reply.writeNoException();
                    reply.writeInt(_result26);
                    return true;
                case 77:
                    data.enforceInterface(DESCRIPTOR);
                    VolumePolicy _arg056 = data.readInt() != 0 ? VolumePolicy.CREATOR.createFromParcel(data) : null;
                    setVolumePolicy(_arg056);
                    reply.writeNoException();
                    return true;
                case 78:
                    data.enforceInterface(DESCRIPTOR);
                    boolean hasRegisteredDynamicPolicy = hasRegisteredDynamicPolicy();
                    reply.writeNoException();
                    reply.writeInt(hasRegisteredDynamicPolicy ? 1 : 0);
                    return true;
                case 79:
                    data.enforceInterface(DESCRIPTOR);
                    IRecordingConfigDispatcher _arg057 = IRecordingConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    registerRecordingCallback(_arg057);
                    reply.writeNoException();
                    return true;
                case 80:
                    data.enforceInterface(DESCRIPTOR);
                    IRecordingConfigDispatcher _arg058 = IRecordingConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    unregisterRecordingCallback(_arg058);
                    return true;
                case 81:
                    data.enforceInterface(DESCRIPTOR);
                    List<AudioRecordingConfiguration> _result27 = getActiveRecordingConfigurations();
                    reply.writeNoException();
                    reply.writeTypedList(_result27);
                    return true;
                case 82:
                    data.enforceInterface(DESCRIPTOR);
                    IPlaybackConfigDispatcher _arg059 = IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    registerPlaybackCallback(_arg059);
                    reply.writeNoException();
                    return true;
                case 83:
                    data.enforceInterface(DESCRIPTOR);
                    IPlaybackConfigDispatcher _arg060 = IPlaybackConfigDispatcher.Stub.asInterface(data.readStrongBinder());
                    unregisterPlaybackCallback(_arg060);
                    return true;
                case 84:
                    data.enforceInterface(DESCRIPTOR);
                    List<AudioPlaybackConfiguration> _result28 = getActivePlaybackConfigurations();
                    reply.writeNoException();
                    reply.writeTypedList(_result28);
                    return true;
                case 85:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg061 = data.readInt();
                    disableRingtoneSync(_arg061);
                    reply.writeNoException();
                    return true;
                case 86:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg062 = data.readInt();
                    AudioAttributes _arg126 = data.readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel(data) : null;
                    int _result29 = getFocusRampTimeMs(_arg062, _arg126);
                    reply.writeNoException();
                    reply.writeInt(_result29);
                    return true;
                case 87:
                    data.enforceInterface(DESCRIPTOR);
                    AudioFocusInfo _arg063 = data.readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel(data) : null;
                    AudioFocusInfo _arg064 = _arg063;
                    int _arg127 = data.readInt();
                    int _result30 = dispatchFocusChange(_arg064, _arg127, IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeInt(_result30);
                    return true;
                case 88:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg065 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    playerHasOpPlayAudio(_arg065, _arg2);
                    return true;
                case 89:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg066 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _arg128 = data.readInt();
                    _arg2 = data.readInt() != 0;
                    int _arg311 = data.readInt();
                    setBluetoothHearingAidDeviceConnectionState(_arg066, _arg128, _arg2, _arg311);
                    reply.writeNoException();
                    return true;
                case 90:
                    data.enforceInterface(DESCRIPTOR);
                    BluetoothDevice _arg067 = data.readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel(data) : null;
                    int _arg129 = data.readInt();
                    int _arg212 = data.readInt();
                    boolean _arg312 = data.readInt() != 0;
                    int _arg46 = data.readInt();
                    setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(_arg067, _arg129, _arg212, _arg312, _arg46);
                    reply.writeNoException();
                    return true;
                case 91:
                    data.enforceInterface(DESCRIPTOR);
                    AudioFocusInfo _arg068 = data.readInt() != 0 ? AudioFocusInfo.CREATOR.createFromParcel(data) : null;
                    AudioFocusInfo _arg069 = _arg068;
                    int _arg130 = data.readInt();
                    setFocusRequestResultFromExtPolicy(_arg069, _arg130, IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder()));
                    return true;
                case 92:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioServerStateDispatcher _arg070 = IAudioServerStateDispatcher.Stub.asInterface(data.readStrongBinder());
                    registerAudioServerStateDispatcher(_arg070);
                    reply.writeNoException();
                    return true;
                case 93:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioServerStateDispatcher _arg071 = IAudioServerStateDispatcher.Stub.asInterface(data.readStrongBinder());
                    unregisterAudioServerStateDispatcher(_arg071);
                    return true;
                case 94:
                    data.enforceInterface(DESCRIPTOR);
                    boolean isAudioServerRunning = isAudioServerRunning();
                    reply.writeNoException();
                    reply.writeInt(isAudioServerRunning ? 1 : 0);
                    return true;
                case 95:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioPolicyCallback _arg072 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg131 = data.readInt();
                    int[] _arg213 = data.createIntArray();
                    String[] _arg313 = data.createStringArray();
                    int _result31 = setUidDeviceAffinity(_arg072, _arg131, _arg213, _arg313);
                    reply.writeNoException();
                    reply.writeInt(_result31);
                    return true;
                case 96:
                    data.enforceInterface(DESCRIPTOR);
                    IAudioPolicyCallback _arg073 = IAudioPolicyCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg132 = data.readInt();
                    int _result32 = removeUidDeviceAffinity(_arg073, _arg132);
                    reply.writeNoException();
                    reply.writeInt(_result32);
                    return true;
                case 97:
                    data.enforceInterface(DESCRIPTOR);
                    Uri _arg074 = data.readInt() != 0 ? Uri.CREATOR.createFromParcel(data) : null;
                    boolean hasHapticChannels = hasHapticChannels(_arg074);
                    reply.writeNoException();
                    reply.writeInt(hasHapticChannels ? 1 : 0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes3.dex */
        private static class Proxy implements IAudioService {
            public static IAudioService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.IAudioService
            public int trackPlayer(PlayerBase.PlayerIdCard pic) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pic != null) {
                        _data.writeInt(1);
                        pic.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(1, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().trackPlayer(pic);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerAttributes(int piid, AudioAttributes attr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    if (attr != null) {
                        _data.writeInt(1);
                        attr.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playerAttributes(piid, attr);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerEvent(int piid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeInt(event);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playerEvent(piid, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releasePlayer(int piid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releasePlayer(piid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int trackRecorder(IBinder recorder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(recorder);
                    boolean _status = this.mRemote.transact(5, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().trackRecorder(recorder);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void recorderEvent(int riid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(riid);
                    _data.writeInt(event);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().recorderEvent(riid, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void releaseRecorder(int riid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(riid);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseRecorder(riid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags, String callingPackage, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    _data.writeInt(suggestedStreamType);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeString(caller);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adjustSuggestedStreamVolume(direction, suggestedStreamType, flags, callingPackage, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(9, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().adjustStreamVolume(streamType, direction, flags, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setStreamVolume(int streamType, int index, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(10, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStreamVolume(streamType, index, flags, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamMute(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(11, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStreamMute(streamType);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(startForcing ? 1 : 0);
                    _data.writeStrongBinder(cb);
                    boolean _status = this.mRemote.transact(12, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceRemoteSubmixFullVolume(startForcing, cb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isMasterMute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(13, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMasterMute();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMasterMute(boolean mute, int flags, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mute ? 1 : 0);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(14, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMasterMute(mute, flags, callingPackage, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(15, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStreamVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMinVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(16, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStreamMinVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getStreamMaxVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(17, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStreamMaxVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(18, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioVolumeGroups();
                    }
                    _reply.readException();
                    List<AudioVolumeGroup> _result = _reply.createTypedArrayList(AudioVolumeGroup.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeIndexForAttributes(AudioAttributes aa, int index, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(19, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeIndexForAttributes(aa, index, flags, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(20, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolumeIndexForAttributes(aa);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMaxVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(21, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaxVolumeIndexForAttributes(aa);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMinVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(22, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMinVolumeIndexForAttributes(aa);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(23, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastAudibleStreamVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(24, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioProductStrategies();
                    }
                    _reply.readException();
                    List<AudioProductStrategy> _result = _reply.createTypedArrayList(AudioProductStrategy.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMicrophoneMute(boolean on, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(25, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMicrophoneMute(on, callingPackage, userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    _data.writeString(caller);
                    boolean _status = this.mRemote.transact(26, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingerModeExternal(ringerMode, caller);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    _data.writeString(caller);
                    boolean _status = this.mRemote.transact(27, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingerModeInternal(ringerMode, caller);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeExternal() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(28, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRingerModeExternal();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getRingerModeInternal() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(29, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRingerModeInternal();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isValidRingerMode(int ringerMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    boolean _status = this.mRemote.transact(30, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isValidRingerMode(ringerMode);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    _data.writeInt(vibrateSetting);
                    boolean _status = this.mRemote.transact(31, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVibrateSetting(vibrateType, vibrateSetting);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getVibrateSetting(int vibrateType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    boolean _status = this.mRemote.transact(32, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVibrateSetting(vibrateType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean shouldVibrate(int vibrateType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    boolean _status = this.mRemote.transact(33, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldVibrate(vibrateType);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeStrongBinder(cb);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(34, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMode(mode, cb, callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(35, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMode();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffect(int effectType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectType);
                    boolean _status = this.mRemote.transact(36, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playSoundEffect(effectType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectType);
                    _data.writeFloat(volume);
                    boolean _status = this.mRemote.transact(37, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playSoundEffectVolume(effectType, volume);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean loadSoundEffects() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(38, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().loadSoundEffects();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unloadSoundEffects() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(39, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unloadSoundEffects();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void reloadAudioSettings() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(40, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reloadAudioSettings();
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void avrcpSupportsAbsoluteVolume(String address, boolean support) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(support ? 1 : 0);
                    boolean _status = this.mRemote.transact(41, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().avrcpSupportsAbsoluteVolume(address, support);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setSpeakerphoneOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    boolean _status = this.mRemote.transact(42, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSpeakerphoneOn(on);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isSpeakerphoneOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(43, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSpeakerphoneOn();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothScoOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    boolean _status = this.mRemote.transact(44, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothScoOn(on);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothScoOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(45, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBluetoothScoOn();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothA2dpOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    boolean _status = this.mRemote.transact(46, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothA2dpOn(on);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isBluetoothA2dpOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(47, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBluetoothA2dpOn();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int requestAudioFocus(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, int flags, IAudioPolicyCallback pcb, int sdk) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(durationHint);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeStrongBinder(cb);
                    _data.writeStrongBinder(fd != null ? fd.asBinder() : null);
                    _data.writeString(clientId);
                    _data.writeString(callingPackageName);
                    _data.writeInt(flags);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    _data.writeInt(sdk);
                    boolean _status = this.mRemote.transact(48, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        int requestAudioFocus = Stub.getDefaultImpl().requestAudioFocus(aa, durationHint, cb, fd, clientId, callingPackageName, flags, pcb, sdk);
                        _reply.recycle();
                        _data.recycle();
                        return requestAudioFocus;
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.media.IAudioService
            public int abandonAudioFocus(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(fd != null ? fd.asBinder() : null);
                    _data.writeString(clientId);
                    if (aa != null) {
                        _data.writeInt(1);
                        aa.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(callingPackageName);
                    boolean _status = this.mRemote.transact(49, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().abandonAudioFocus(fd, clientId, aa, callingPackageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioFocusClient(String clientId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(clientId);
                    boolean _status = this.mRemote.transact(50, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAudioFocusClient(clientId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getCurrentAudioFocus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(51, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAudioFocus();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    _data.writeInt(targetSdkVersion);
                    boolean _status = this.mRemote.transact(52, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startBluetoothSco(cb, targetSdkVersion);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    boolean _status = this.mRemote.transact(53, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startBluetoothScoVirtualCall(cb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void stopBluetoothSco(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    boolean _status = this.mRemote.transact(54, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopBluetoothSco(cb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeStrongBinder(cb);
                    boolean _status = this.mRemote.transact(55, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceVolumeControlStream(streamType, cb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(player != null ? player.asBinder() : null);
                    boolean _status = this.mRemote.transact(56, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingtonePlayer(player);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public IRingtonePlayer getRingtonePlayer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(57, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRingtonePlayer();
                    }
                    _reply.readException();
                    IRingtonePlayer _result = IRingtonePlayer.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getUiSoundsStreamType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(58, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUiSoundsStreamType();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setWiredDeviceConnectionState(int type, int state, String address, String name, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(state);
                    _data.writeString(address);
                    _data.writeString(name);
                    _data.writeString(caller);
                    boolean _status = this.mRemote.transact(59, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setWiredDeviceConnectionState(type, state, address, name, caller);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice device) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(60, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleBluetoothA2dpDeviceConfigChange(device);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void handleBluetoothA2dpActiveDeviceChange(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(state);
                    _data.writeInt(profile);
                    _data.writeInt(suppressNoisyIntent ? 1 : 0);
                    _data.writeInt(a2dpVolume);
                    boolean _status = this.mRemote.transact(61, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleBluetoothA2dpActiveDeviceChange(device, state, profile, suppressNoisyIntent, a2dpVolume);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    AudioRoutesInfo _result = null;
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    boolean _status = this.mRemote.transact(62, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startWatchingRoutes(observer);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AudioRoutesInfo.CREATOR.createFromParcel(_reply);
                    }
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isCameraSoundForced() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(63, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCameraSoundForced();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumeController(IVolumeController controller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(controller != null ? controller.asBinder() : null);
                    boolean _status = this.mRemote.transact(64, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumeController(controller);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(controller != null ? controller.asBinder() : null);
                    _data.writeInt(visible ? 1 : 0);
                    boolean _status = this.mRemote.transact(65, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyVolumeControllerVisible(controller, visible);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(66, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStreamAffectedByRingerMode(streamType);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean _status = this.mRemote.transact(67, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStreamAffectedByMute(streamType);
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    boolean _status = this.mRemote.transact(68, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableSafeMediaVolume(callingPackage);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setHdmiSystemAudioSupported(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on ? 1 : 0);
                    boolean _status = this.mRemote.transact(69, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setHdmiSystemAudioSupported(on);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isHdmiSystemAudioSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(70, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHdmiSystemAudioSupported();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public String registerAudioPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb, boolean hasFocusListener, boolean isFocusPolicy, boolean isTestFocusPolicy, boolean isVolumeController, IMediaProjection projection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyConfig != null) {
                        _data.writeInt(1);
                        policyConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(hasFocusListener ? 1 : 0);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(isFocusPolicy ? 1 : 0);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(isTestFocusPolicy ? 1 : 0);
                    _data.writeInt(isVolumeController ? 1 : 0);
                    _data.writeStrongBinder(projection != null ? projection.asBinder() : null);
                    boolean _status = this.mRemote.transact(71, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        String registerAudioPolicy = Stub.getDefaultImpl().registerAudioPolicy(policyConfig, pcb, hasFocusListener, isFocusPolicy, isTestFocusPolicy, isVolumeController, projection);
                        _reply.recycle();
                        _data.recycle();
                        return registerAudioPolicy;
                    }
                    _reply.readException();
                    String _result = _reply.readString();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(72, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAudioPolicyAsync(pcb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(73, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAudioPolicy(pcb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int addMixForPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyConfig != null) {
                        _data.writeInt(1);
                        policyConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(74, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addMixForPolicy(policyConfig, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeMixForPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policyConfig != null) {
                        _data.writeInt(1);
                        policyConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(75, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeMixForPolicy(policyConfig, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(duckingBehavior);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(76, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setFocusPropertiesForPolicy(duckingBehavior, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setVolumePolicy(VolumePolicy policy) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (policy != null) {
                        _data.writeInt(1);
                        policy.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(77, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolumePolicy(policy);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasRegisteredDynamicPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(78, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasRegisteredDynamicPolicy();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rcdb != null ? rcdb.asBinder() : null);
                    boolean _status = this.mRemote.transact(79, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRecordingCallback(rcdb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rcdb != null ? rcdb.asBinder() : null);
                    boolean _status = this.mRemote.transact(80, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterRecordingCallback(rcdb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(81, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveRecordingConfigurations();
                    }
                    _reply.readException();
                    List<AudioRecordingConfiguration> _result = _reply.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcdb != null ? pcdb.asBinder() : null);
                    boolean _status = this.mRemote.transact(82, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerPlaybackCallback(pcdb);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcdb != null ? pcdb.asBinder() : null);
                    boolean _status = this.mRemote.transact(83, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterPlaybackCallback(pcdb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(84, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivePlaybackConfigurations();
                    }
                    _reply.readException();
                    List<AudioPlaybackConfiguration> _result = _reply.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void disableRingtoneSync(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    boolean _status = this.mRemote.transact(85, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableRingtoneSync(userId);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(focusGain);
                    if (attr != null) {
                        _data.writeInt(1);
                        attr.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(86, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFocusRampTimeMs(focusGain, attr);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (afi != null) {
                        _data.writeInt(1);
                        afi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(focusChange);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(87, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dispatchFocusChange(afi, focusChange, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeInt(hasOpPlayAudio ? 1 : 0);
                    boolean _status = this.mRemote.transact(88, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playerHasOpPlayAudio(piid, hasOpPlayAudio);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice device, int state, boolean suppressNoisyIntent, int musicDevice) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(state);
                    _data.writeInt(suppressNoisyIntent ? 1 : 0);
                    _data.writeInt(musicDevice);
                    boolean _status = this.mRemote.transact(89, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothHearingAidDeviceConnectionState(device, state, suppressNoisyIntent, musicDevice);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (device != null) {
                        _data.writeInt(1);
                        device.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(state);
                    _data.writeInt(profile);
                    _data.writeInt(suppressNoisyIntent ? 1 : 0);
                    _data.writeInt(a2dpVolume);
                    boolean _status = this.mRemote.transact(90, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(device, state, profile, suppressNoisyIntent, a2dpVolume);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void setFocusRequestResultFromExtPolicy(AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (afi != null) {
                        _data.writeInt(1);
                        afi.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(requestResult);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    boolean _status = this.mRemote.transact(91, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setFocusRequestResultFromExtPolicy(afi, requestResult, pcb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(asd != null ? asd.asBinder() : null);
                    boolean _status = this.mRemote.transact(92, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerAudioServerStateDispatcher(asd);
                    } else {
                        _reply.readException();
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(asd != null ? asd.asBinder() : null);
                    boolean _status = this.mRemote.transact(93, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterAudioServerStateDispatcher(asd);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean isAudioServerRunning() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _status = this.mRemote.transact(94, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAudioServerRunning();
                    }
                    _reply.readException();
                    boolean _status2 = _reply.readInt() != 0;
                    return _status2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int setUidDeviceAffinity(IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    _data.writeInt(uid);
                    _data.writeIntArray(deviceTypes);
                    _data.writeStringArray(deviceAddresses);
                    boolean _status = this.mRemote.transact(95, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUidDeviceAffinity(pcb, uid, deviceTypes, deviceAddresses);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    _data.writeInt(uid);
                    boolean _status = this.mRemote.transact(96, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeUidDeviceAffinity(pcb, uid);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IAudioService
            public boolean hasHapticChannels(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(97, _data, _reply, 0);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasHapticChannels(uri);
                    }
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAudioService impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAudioService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
