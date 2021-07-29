package android.media;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.media.IRingtonePlayer;
import android.media.PlayerBase;
import android.media.audiopolicy.AudioPolicyConfig;
import android.media.audiopolicy.AudioProductStrategy;
import android.media.audiopolicy.AudioVolumeGroup;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.media.projection.IMediaProjection;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

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

    public static class Default implements IAudioService {
        public int trackPlayer(PlayerBase.PlayerIdCard pic) throws RemoteException {
            return 0;
        }

        public void playerAttributes(int piid, AudioAttributes attr) throws RemoteException {
        }

        public void playerEvent(int piid, int event) throws RemoteException {
        }

        public void releasePlayer(int piid) throws RemoteException {
        }

        public int trackRecorder(IBinder recorder) throws RemoteException {
            return 0;
        }

        public void recorderEvent(int riid, int event) throws RemoteException {
        }

        public void releaseRecorder(int riid) throws RemoteException {
        }

        public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags, String callingPackage, String caller) throws RemoteException {
        }

        public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage) throws RemoteException {
        }

        public void setStreamVolume(int streamType, int index, int flags, String callingPackage) throws RemoteException {
        }

        public boolean isStreamMute(int streamType) throws RemoteException {
            return false;
        }

        public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb) throws RemoteException {
        }

        public boolean isMasterMute() throws RemoteException {
            return false;
        }

        public void setMasterMute(boolean mute, int flags, String callingPackage, int userId) throws RemoteException {
        }

        public int getStreamVolume(int streamType) throws RemoteException {
            return 0;
        }

        public int getStreamMinVolume(int streamType) throws RemoteException {
            return 0;
        }

        public int getStreamMaxVolume(int streamType) throws RemoteException {
            return 0;
        }

        public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
            return null;
        }

        public void setVolumeIndexForAttributes(AudioAttributes aa, int index, int flags, String callingPackage) throws RemoteException {
        }

        public int getVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
            return 0;
        }

        public int getMaxVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
            return 0;
        }

        public int getMinVolumeIndexForAttributes(AudioAttributes aa) throws RemoteException {
            return 0;
        }

        public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
            return 0;
        }

        public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
            return null;
        }

        public void setMicrophoneMute(boolean on, String callingPackage, int userId) throws RemoteException {
        }

        public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {
        }

        public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {
        }

        public int getRingerModeExternal() throws RemoteException {
            return 0;
        }

        public int getRingerModeInternal() throws RemoteException {
            return 0;
        }

        public boolean isValidRingerMode(int ringerMode) throws RemoteException {
            return false;
        }

        public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {
        }

        public int getVibrateSetting(int vibrateType) throws RemoteException {
            return 0;
        }

        public boolean shouldVibrate(int vibrateType) throws RemoteException {
            return false;
        }

        public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {
        }

        public int getMode() throws RemoteException {
            return 0;
        }

        public void playSoundEffect(int effectType) throws RemoteException {
        }

        public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {
        }

        public boolean loadSoundEffects() throws RemoteException {
            return false;
        }

        public void unloadSoundEffects() throws RemoteException {
        }

        public void reloadAudioSettings() throws RemoteException {
        }

        public void avrcpSupportsAbsoluteVolume(String address, boolean support) throws RemoteException {
        }

        public void setSpeakerphoneOn(boolean on) throws RemoteException {
        }

        public boolean isSpeakerphoneOn() throws RemoteException {
            return false;
        }

        public void setBluetoothScoOn(boolean on) throws RemoteException {
        }

        public boolean isBluetoothScoOn() throws RemoteException {
            return false;
        }

        public void setBluetoothA2dpOn(boolean on) throws RemoteException {
        }

        public boolean isBluetoothA2dpOn() throws RemoteException {
            return false;
        }

        public int requestAudioFocus(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, int flags, IAudioPolicyCallback pcb, int sdk) throws RemoteException {
            return 0;
        }

        public int abandonAudioFocus(IAudioFocusDispatcher fd, String clientId, AudioAttributes aa, String callingPackageName) throws RemoteException {
            return 0;
        }

        public void unregisterAudioFocusClient(String clientId) throws RemoteException {
        }

        public int getCurrentAudioFocus() throws RemoteException {
            return 0;
        }

        public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {
        }

        public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {
        }

        public void stopBluetoothSco(IBinder cb) throws RemoteException {
        }

        public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {
        }

        public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {
        }

        public IRingtonePlayer getRingtonePlayer() throws RemoteException {
            return null;
        }

        public int getUiSoundsStreamType() throws RemoteException {
            return 0;
        }

        public void setWiredDeviceConnectionState(int type, int state, String address, String name, String caller) throws RemoteException {
        }

        public void handleBluetoothA2dpDeviceConfigChange(BluetoothDevice device) throws RemoteException {
        }

        public void handleBluetoothA2dpActiveDeviceChange(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) throws RemoteException {
        }

        public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer) throws RemoteException {
            return null;
        }

        public boolean isCameraSoundForced() throws RemoteException {
            return false;
        }

        public void setVolumeController(IVolumeController controller) throws RemoteException {
        }

        public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) throws RemoteException {
        }

        public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
            return false;
        }

        public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
            return false;
        }

        public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
        }

        public int setHdmiSystemAudioSupported(boolean on) throws RemoteException {
            return 0;
        }

        public boolean isHdmiSystemAudioSupported() throws RemoteException {
            return false;
        }

        public String registerAudioPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb, boolean hasFocusListener, boolean isFocusPolicy, boolean isTestFocusPolicy, boolean isVolumeController, IMediaProjection projection) throws RemoteException {
            return null;
        }

        public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {
        }

        public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {
        }

        public int addMixForPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        public int removeMixForPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        public void setVolumePolicy(VolumePolicy policy) throws RemoteException {
        }

        public boolean hasRegisteredDynamicPolicy() throws RemoteException {
            return false;
        }

        public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
        }

        public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
        }

        public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
            return null;
        }

        public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
        }

        public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
        }

        public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
            return null;
        }

        public void disableRingtoneSync(int userId) throws RemoteException {
        }

        public int getFocusRampTimeMs(int focusGain, AudioAttributes attr) throws RemoteException {
            return 0;
        }

        public int dispatchFocusChange(AudioFocusInfo afi, int focusChange, IAudioPolicyCallback pcb) throws RemoteException {
            return 0;
        }

        public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
        }

        public void setBluetoothHearingAidDeviceConnectionState(BluetoothDevice device, int state, boolean suppressNoisyIntent, int musicDevice) throws RemoteException {
        }

        public void setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(BluetoothDevice device, int state, int profile, boolean suppressNoisyIntent, int a2dpVolume) throws RemoteException {
        }

        public void setFocusRequestResultFromExtPolicy(AudioFocusInfo afi, int requestResult, IAudioPolicyCallback pcb) throws RemoteException {
        }

        public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
        }

        public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
        }

        public boolean isAudioServerRunning() throws RemoteException {
            return false;
        }

        public int setUidDeviceAffinity(IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
            return 0;
        }

        public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
            return 0;
        }

        public boolean hasHapticChannels(Uri uri) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

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
        static final int TRANSACTION_setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent = 90;
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
            if (iin == null || !(iin instanceof IAudioService)) {
                return new Proxy(obj);
            }
            return (IAudioService) iin;
        }

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

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: android.media.AudioAttributes} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v54, resolved type: android.media.AudioAttributes} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v56, resolved type: android.media.AudioAttributes} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v68, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v80, resolved type: android.media.audiopolicy.AudioPolicyConfig} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v99, resolved type: android.media.AudioAttributes} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v108, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v112, resolved type: android.bluetooth.BluetoothDevice} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1 */
        /* JADX WARNING: type inference failed for: r1v23 */
        /* JADX WARNING: type inference failed for: r1v27 */
        /* JADX WARNING: type inference failed for: r1v31 */
        /* JADX WARNING: type inference failed for: r1v35 */
        /* JADX WARNING: type inference failed for: r1v55 */
        /* JADX WARNING: type inference failed for: r1v62, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v65 */
        /* JADX WARNING: type inference failed for: r1v84 */
        /* JADX WARNING: type inference failed for: r1v89 */
        /* JADX WARNING: type inference failed for: r1v96 */
        /* JADX WARNING: type inference failed for: r1v103 */
        /* JADX WARNING: type inference failed for: r1v116 */
        /* JADX WARNING: type inference failed for: r1v122 */
        /* JADX WARNING: type inference failed for: r1v126 */
        /* JADX WARNING: type inference failed for: r1v127 */
        /* JADX WARNING: type inference failed for: r1v128 */
        /* JADX WARNING: type inference failed for: r1v129 */
        /* JADX WARNING: type inference failed for: r1v130 */
        /* JADX WARNING: type inference failed for: r1v131 */
        /* JADX WARNING: type inference failed for: r1v132 */
        /* JADX WARNING: type inference failed for: r1v133 */
        /* JADX WARNING: type inference failed for: r1v134 */
        /* JADX WARNING: type inference failed for: r1v135 */
        /* JADX WARNING: type inference failed for: r1v136 */
        /* JADX WARNING: type inference failed for: r1v137 */
        /* JADX WARNING: type inference failed for: r1v138 */
        /* JADX WARNING: type inference failed for: r1v139 */
        /* JADX WARNING: type inference failed for: r1v140 */
        /* JADX WARNING: type inference failed for: r1v141 */
        /* JADX WARNING: type inference failed for: r1v142 */
        /* JADX WARNING: type inference failed for: r1v143 */
        /* JADX WARNING: type inference failed for: r1v144 */
        /* JADX WARNING: type inference failed for: r1v145 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r25, android.os.Parcel r26, android.os.Parcel r27, int r28) throws android.os.RemoteException {
            /*
                r24 = this;
                r10 = r24
                r11 = r25
                r12 = r26
                r13 = r27
                java.lang.String r14 = "android.media.IAudioService"
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r15 = 1
                if (r11 == r0) goto L_0x08c3
                r0 = 0
                r1 = 0
                switch(r11) {
                    case 1: goto L_0x08a3;
                    case 2: goto L_0x0888;
                    case 3: goto L_0x0879;
                    case 4: goto L_0x086e;
                    case 5: goto L_0x085c;
                    case 6: goto L_0x084d;
                    case 7: goto L_0x0842;
                    case 8: goto L_0x081f;
                    case 9: goto L_0x0805;
                    case 10: goto L_0x07eb;
                    case 11: goto L_0x07d9;
                    case 12: goto L_0x07c3;
                    case 13: goto L_0x07b5;
                    case 14: goto L_0x0797;
                    case 15: goto L_0x0785;
                    case 16: goto L_0x0773;
                    case 17: goto L_0x0761;
                    case 18: goto L_0x0753;
                    case 19: goto L_0x072b;
                    case 20: goto L_0x070b;
                    case 21: goto L_0x06eb;
                    case 22: goto L_0x06cb;
                    case 23: goto L_0x06b9;
                    case 24: goto L_0x06ab;
                    case 25: goto L_0x0691;
                    case 26: goto L_0x067f;
                    case 27: goto L_0x066d;
                    case 28: goto L_0x065f;
                    case 29: goto L_0x0651;
                    case 30: goto L_0x063f;
                    case 31: goto L_0x062d;
                    case 32: goto L_0x061b;
                    case 33: goto L_0x0609;
                    case 34: goto L_0x05f3;
                    case 35: goto L_0x05e5;
                    case 36: goto L_0x05da;
                    case 37: goto L_0x05cb;
                    case 38: goto L_0x05bd;
                    case 39: goto L_0x05b6;
                    case 40: goto L_0x05af;
                    case 41: goto L_0x059c;
                    case 42: goto L_0x058a;
                    case 43: goto L_0x057c;
                    case 44: goto L_0x056a;
                    case 45: goto L_0x055c;
                    case 46: goto L_0x054a;
                    case 47: goto L_0x053c;
                    case 48: goto L_0x04e4;
                    case 49: goto L_0x04b6;
                    case 50: goto L_0x04a8;
                    case 51: goto L_0x049a;
                    case 52: goto L_0x0488;
                    case 53: goto L_0x047a;
                    case 54: goto L_0x046c;
                    case 55: goto L_0x045a;
                    case 56: goto L_0x0448;
                    case 57: goto L_0x0433;
                    case 58: goto L_0x0425;
                    case 59: goto L_0x03ff;
                    case 60: goto L_0x03e3;
                    case 61: goto L_0x03af;
                    case 62: goto L_0x0390;
                    case 63: goto L_0x0382;
                    case 64: goto L_0x0370;
                    case 65: goto L_0x0356;
                    case 66: goto L_0x0344;
                    case 67: goto L_0x0332;
                    case 68: goto L_0x0324;
                    case 69: goto L_0x030e;
                    case 70: goto L_0x0300;
                    case 71: goto L_0x02aa;
                    case 72: goto L_0x029b;
                    case 73: goto L_0x0289;
                    case 74: goto L_0x0261;
                    case 75: goto L_0x0239;
                    case 76: goto L_0x021f;
                    case 77: goto L_0x0203;
                    case 78: goto L_0x01f5;
                    case 79: goto L_0x01e3;
                    case 80: goto L_0x01d4;
                    case 81: goto L_0x01c6;
                    case 82: goto L_0x01b4;
                    case 83: goto L_0x01a5;
                    case 84: goto L_0x0197;
                    case 85: goto L_0x0189;
                    case 86: goto L_0x0167;
                    case 87: goto L_0x013b;
                    case 88: goto L_0x0128;
                    case 89: goto L_0x00fe;
                    case 90: goto L_0x00ca;
                    case 91: goto L_0x00a5;
                    case 92: goto L_0x0093;
                    case 93: goto L_0x0084;
                    case 94: goto L_0x0076;
                    case 95: goto L_0x0054;
                    case 96: goto L_0x003a;
                    case 97: goto L_0x001a;
                    default: goto L_0x0015;
                }
            L_0x0015:
                boolean r0 = super.onTransact(r25, r26, r27, r28)
                return r0
            L_0x001a:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x002d
                android.os.Parcelable$Creator<android.net.Uri> r0 = android.net.Uri.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.net.Uri r1 = (android.net.Uri) r1
                goto L_0x002e
            L_0x002d:
            L_0x002e:
                r0 = r1
                boolean r1 = r10.hasHapticChannels(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x003a:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r0 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r0)
                int r1 = r26.readInt()
                int r2 = r10.removeUidDeviceAffinity(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x0054:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r0 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r0)
                int r1 = r26.readInt()
                int[] r2 = r26.createIntArray()
                java.lang.String[] r3 = r26.createStringArray()
                int r4 = r10.setUidDeviceAffinity(r0, r1, r2, r3)
                r27.writeNoException()
                r13.writeInt(r4)
                return r15
            L_0x0076:
                r12.enforceInterface(r14)
                boolean r0 = r24.isAudioServerRunning()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0084:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IAudioServerStateDispatcher r0 = android.media.IAudioServerStateDispatcher.Stub.asInterface(r0)
                r10.unregisterAudioServerStateDispatcher(r0)
                return r15
            L_0x0093:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IAudioServerStateDispatcher r0 = android.media.IAudioServerStateDispatcher.Stub.asInterface(r0)
                r10.registerAudioServerStateDispatcher(r0)
                r27.writeNoException()
                return r15
            L_0x00a5:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x00b8
                android.os.Parcelable$Creator<android.media.AudioFocusInfo> r0 = android.media.AudioFocusInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.AudioFocusInfo r1 = (android.media.AudioFocusInfo) r1
                goto L_0x00b9
            L_0x00b8:
            L_0x00b9:
                r0 = r1
                int r1 = r26.readInt()
                android.os.IBinder r2 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r2 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r2)
                r10.setFocusRequestResultFromExtPolicy(r0, r1, r2)
                return r15
            L_0x00ca:
                r12.enforceInterface(r14)
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x00dc
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x00dd
            L_0x00dc:
            L_0x00dd:
                int r6 = r26.readInt()
                int r7 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x00ed
                r4 = r15
                goto L_0x00ee
            L_0x00ed:
                r4 = r0
            L_0x00ee:
                int r8 = r26.readInt()
                r0 = r24
                r2 = r6
                r3 = r7
                r5 = r8
                r0.setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(r1, r2, r3, r4, r5)
                r27.writeNoException()
                return r15
            L_0x00fe:
                r12.enforceInterface(r14)
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x0110
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x0111
            L_0x0110:
            L_0x0111:
                int r2 = r26.readInt()
                int r3 = r26.readInt()
                if (r3 == 0) goto L_0x011d
                r0 = r15
            L_0x011d:
                int r3 = r26.readInt()
                r10.setBluetoothHearingAidDeviceConnectionState(r1, r2, r0, r3)
                r27.writeNoException()
                return r15
            L_0x0128:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x0137
                r0 = r15
            L_0x0137:
                r10.playerHasOpPlayAudio(r1, r0)
                return r15
            L_0x013b:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x014e
                android.os.Parcelable$Creator<android.media.AudioFocusInfo> r0 = android.media.AudioFocusInfo.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.AudioFocusInfo r1 = (android.media.AudioFocusInfo) r1
                goto L_0x014f
            L_0x014e:
            L_0x014f:
                r0 = r1
                int r1 = r26.readInt()
                android.os.IBinder r2 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r2 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r2)
                int r3 = r10.dispatchFocusChange(r0, r1, r2)
                r27.writeNoException()
                r13.writeInt(r3)
                return r15
            L_0x0167:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x017d
                android.os.Parcelable$Creator<android.media.AudioAttributes> r1 = android.media.AudioAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x017e
            L_0x017d:
            L_0x017e:
                int r2 = r10.getFocusRampTimeMs(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x0189:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                r10.disableRingtoneSync(r0)
                r27.writeNoException()
                return r15
            L_0x0197:
                r12.enforceInterface(r14)
                java.util.List r0 = r24.getActivePlaybackConfigurations()
                r27.writeNoException()
                r13.writeTypedList(r0)
                return r15
            L_0x01a5:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IPlaybackConfigDispatcher r0 = android.media.IPlaybackConfigDispatcher.Stub.asInterface(r0)
                r10.unregisterPlaybackCallback(r0)
                return r15
            L_0x01b4:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IPlaybackConfigDispatcher r0 = android.media.IPlaybackConfigDispatcher.Stub.asInterface(r0)
                r10.registerPlaybackCallback(r0)
                r27.writeNoException()
                return r15
            L_0x01c6:
                r12.enforceInterface(r14)
                java.util.List r0 = r24.getActiveRecordingConfigurations()
                r27.writeNoException()
                r13.writeTypedList(r0)
                return r15
            L_0x01d4:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IRecordingConfigDispatcher r0 = android.media.IRecordingConfigDispatcher.Stub.asInterface(r0)
                r10.unregisterRecordingCallback(r0)
                return r15
            L_0x01e3:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IRecordingConfigDispatcher r0 = android.media.IRecordingConfigDispatcher.Stub.asInterface(r0)
                r10.registerRecordingCallback(r0)
                r27.writeNoException()
                return r15
            L_0x01f5:
                r12.enforceInterface(r14)
                boolean r0 = r24.hasRegisteredDynamicPolicy()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0203:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x0216
                android.os.Parcelable$Creator<android.media.VolumePolicy> r0 = android.media.VolumePolicy.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.VolumePolicy r1 = (android.media.VolumePolicy) r1
                goto L_0x0217
            L_0x0216:
            L_0x0217:
                r0 = r1
                r10.setVolumePolicy(r0)
                r27.writeNoException()
                return r15
            L_0x021f:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                android.os.IBinder r1 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r1 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r1)
                int r2 = r10.setFocusPropertiesForPolicy(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x0239:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x024c
                android.os.Parcelable$Creator<android.media.audiopolicy.AudioPolicyConfig> r0 = android.media.audiopolicy.AudioPolicyConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.audiopolicy.AudioPolicyConfig r1 = (android.media.audiopolicy.AudioPolicyConfig) r1
                goto L_0x024d
            L_0x024c:
            L_0x024d:
                r0 = r1
                android.os.IBinder r1 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r1 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r1)
                int r2 = r10.removeMixForPolicy(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x0261:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x0274
                android.os.Parcelable$Creator<android.media.audiopolicy.AudioPolicyConfig> r0 = android.media.audiopolicy.AudioPolicyConfig.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.audiopolicy.AudioPolicyConfig r1 = (android.media.audiopolicy.AudioPolicyConfig) r1
                goto L_0x0275
            L_0x0274:
            L_0x0275:
                r0 = r1
                android.os.IBinder r1 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r1 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r1)
                int r2 = r10.addMixForPolicy(r0, r1)
                r27.writeNoException()
                r13.writeInt(r2)
                return r15
            L_0x0289:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r0 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r0)
                r10.unregisterAudioPolicy(r0)
                r27.writeNoException()
                return r15
            L_0x029b:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r0 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r0)
                r10.unregisterAudioPolicyAsync(r0)
                return r15
            L_0x02aa:
                r12.enforceInterface(r14)
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x02bc
                android.os.Parcelable$Creator<android.media.audiopolicy.AudioPolicyConfig> r1 = android.media.audiopolicy.AudioPolicyConfig.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.media.audiopolicy.AudioPolicyConfig r1 = (android.media.audiopolicy.AudioPolicyConfig) r1
                goto L_0x02bd
            L_0x02bc:
            L_0x02bd:
                android.os.IBinder r2 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r8 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r2)
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x02cd
                r3 = r15
                goto L_0x02ce
            L_0x02cd:
                r3 = r0
            L_0x02ce:
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x02d6
                r4 = r15
                goto L_0x02d7
            L_0x02d6:
                r4 = r0
            L_0x02d7:
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x02df
                r5 = r15
                goto L_0x02e0
            L_0x02df:
                r5 = r0
            L_0x02e0:
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x02e8
                r6 = r15
                goto L_0x02e9
            L_0x02e8:
                r6 = r0
            L_0x02e9:
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.projection.IMediaProjection r9 = android.media.projection.IMediaProjection.Stub.asInterface(r0)
                r0 = r24
                r2 = r8
                r7 = r9
                java.lang.String r0 = r0.registerAudioPolicy(r1, r2, r3, r4, r5, r6, r7)
                r27.writeNoException()
                r13.writeString(r0)
                return r15
            L_0x0300:
                r12.enforceInterface(r14)
                boolean r0 = r24.isHdmiSystemAudioSupported()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x030e:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x0319
                r0 = r15
            L_0x0319:
                int r1 = r10.setHdmiSystemAudioSupported(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0324:
                r12.enforceInterface(r14)
                java.lang.String r0 = r26.readString()
                r10.disableSafeMediaVolume(r0)
                r27.writeNoException()
                return r15
            L_0x0332:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.isStreamAffectedByMute(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0344:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.isStreamAffectedByRingerMode(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0356:
                r12.enforceInterface(r14)
                android.os.IBinder r1 = r26.readStrongBinder()
                android.media.IVolumeController r1 = android.media.IVolumeController.Stub.asInterface(r1)
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x0369
                r0 = r15
            L_0x0369:
                r10.notifyVolumeControllerVisible(r1, r0)
                r27.writeNoException()
                return r15
            L_0x0370:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IVolumeController r0 = android.media.IVolumeController.Stub.asInterface(r0)
                r10.setVolumeController(r0)
                r27.writeNoException()
                return r15
            L_0x0382:
                r12.enforceInterface(r14)
                boolean r0 = r24.isCameraSoundForced()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0390:
                r12.enforceInterface(r14)
                android.os.IBinder r1 = r26.readStrongBinder()
                android.media.IAudioRoutesObserver r1 = android.media.IAudioRoutesObserver.Stub.asInterface(r1)
                android.media.AudioRoutesInfo r2 = r10.startWatchingRoutes(r1)
                r27.writeNoException()
                if (r2 == 0) goto L_0x03ab
                r13.writeInt(r15)
                r2.writeToParcel(r13, r15)
                goto L_0x03ae
            L_0x03ab:
                r13.writeInt(r0)
            L_0x03ae:
                return r15
            L_0x03af:
                r12.enforceInterface(r14)
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x03c1
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r1 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x03c2
            L_0x03c1:
            L_0x03c2:
                int r6 = r26.readInt()
                int r7 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x03d2
                r4 = r15
                goto L_0x03d3
            L_0x03d2:
                r4 = r0
            L_0x03d3:
                int r8 = r26.readInt()
                r0 = r24
                r2 = r6
                r3 = r7
                r5 = r8
                r0.handleBluetoothA2dpActiveDeviceChange(r1, r2, r3, r4, r5)
                r27.writeNoException()
                return r15
            L_0x03e3:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x03f6
                android.os.Parcelable$Creator<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.bluetooth.BluetoothDevice r1 = (android.bluetooth.BluetoothDevice) r1
                goto L_0x03f7
            L_0x03f6:
            L_0x03f7:
                r0 = r1
                r10.handleBluetoothA2dpDeviceConfigChange(r0)
                r27.writeNoException()
                return r15
            L_0x03ff:
                r12.enforceInterface(r14)
                int r6 = r26.readInt()
                int r7 = r26.readInt()
                java.lang.String r8 = r26.readString()
                java.lang.String r9 = r26.readString()
                java.lang.String r16 = r26.readString()
                r0 = r24
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                r0.setWiredDeviceConnectionState(r1, r2, r3, r4, r5)
                r27.writeNoException()
                return r15
            L_0x0425:
                r12.enforceInterface(r14)
                int r0 = r24.getUiSoundsStreamType()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x0433:
                r12.enforceInterface(r14)
                android.media.IRingtonePlayer r0 = r24.getRingtonePlayer()
                r27.writeNoException()
                if (r0 == 0) goto L_0x0444
                android.os.IBinder r1 = r0.asBinder()
            L_0x0444:
                r13.writeStrongBinder(r1)
                return r15
            L_0x0448:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IRingtonePlayer r0 = android.media.IRingtonePlayer.Stub.asInterface(r0)
                r10.setRingtonePlayer(r0)
                r27.writeNoException()
                return r15
            L_0x045a:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                android.os.IBinder r1 = r26.readStrongBinder()
                r10.forceVolumeControlStream(r0, r1)
                r27.writeNoException()
                return r15
            L_0x046c:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                r10.stopBluetoothSco(r0)
                r27.writeNoException()
                return r15
            L_0x047a:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                r10.startBluetoothScoVirtualCall(r0)
                r27.writeNoException()
                return r15
            L_0x0488:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                int r1 = r26.readInt()
                r10.startBluetoothSco(r0, r1)
                r27.writeNoException()
                return r15
            L_0x049a:
                r12.enforceInterface(r14)
                int r0 = r24.getCurrentAudioFocus()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x04a8:
                r12.enforceInterface(r14)
                java.lang.String r0 = r26.readString()
                r10.unregisterAudioFocusClient(r0)
                r27.writeNoException()
                return r15
            L_0x04b6:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IAudioFocusDispatcher r0 = android.media.IAudioFocusDispatcher.Stub.asInterface(r0)
                java.lang.String r2 = r26.readString()
                int r3 = r26.readInt()
                if (r3 == 0) goto L_0x04d4
                android.os.Parcelable$Creator<android.media.AudioAttributes> r1 = android.media.AudioAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x04d5
            L_0x04d4:
            L_0x04d5:
                java.lang.String r3 = r26.readString()
                int r4 = r10.abandonAudioFocus(r0, r2, r1, r3)
                r27.writeNoException()
                r13.writeInt(r4)
                return r15
            L_0x04e4:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x04f7
                android.os.Parcelable$Creator<android.media.AudioAttributes> r0 = android.media.AudioAttributes.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                android.media.AudioAttributes r0 = (android.media.AudioAttributes) r0
                r1 = r0
            L_0x04f7:
                int r16 = r26.readInt()
                android.os.IBinder r17 = r26.readStrongBinder()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.IAudioFocusDispatcher r18 = android.media.IAudioFocusDispatcher.Stub.asInterface(r0)
                java.lang.String r19 = r26.readString()
                java.lang.String r20 = r26.readString()
                int r21 = r26.readInt()
                android.os.IBinder r0 = r26.readStrongBinder()
                android.media.audiopolicy.IAudioPolicyCallback r22 = android.media.audiopolicy.IAudioPolicyCallback.Stub.asInterface(r0)
                int r23 = r26.readInt()
                r0 = r24
                r2 = r16
                r3 = r17
                r4 = r18
                r5 = r19
                r6 = r20
                r7 = r21
                r8 = r22
                r9 = r23
                int r0 = r0.requestAudioFocus(r1, r2, r3, r4, r5, r6, r7, r8, r9)
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x053c:
                r12.enforceInterface(r14)
                boolean r0 = r24.isBluetoothA2dpOn()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x054a:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x0555
                r0 = r15
            L_0x0555:
                r10.setBluetoothA2dpOn(r0)
                r27.writeNoException()
                return r15
            L_0x055c:
                r12.enforceInterface(r14)
                boolean r0 = r24.isBluetoothScoOn()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x056a:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x0575
                r0 = r15
            L_0x0575:
                r10.setBluetoothScoOn(r0)
                r27.writeNoException()
                return r15
            L_0x057c:
                r12.enforceInterface(r14)
                boolean r0 = r24.isSpeakerphoneOn()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x058a:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x0595
                r0 = r15
            L_0x0595:
                r10.setSpeakerphoneOn(r0)
                r27.writeNoException()
                return r15
            L_0x059c:
                r12.enforceInterface(r14)
                java.lang.String r1 = r26.readString()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x05ab
                r0 = r15
            L_0x05ab:
                r10.avrcpSupportsAbsoluteVolume(r1, r0)
                return r15
            L_0x05af:
                r12.enforceInterface(r14)
                r24.reloadAudioSettings()
                return r15
            L_0x05b6:
                r12.enforceInterface(r14)
                r24.unloadSoundEffects()
                return r15
            L_0x05bd:
                r12.enforceInterface(r14)
                boolean r0 = r24.loadSoundEffects()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x05cb:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                float r1 = r26.readFloat()
                r10.playSoundEffectVolume(r0, r1)
                return r15
            L_0x05da:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                r10.playSoundEffect(r0)
                return r15
            L_0x05e5:
                r12.enforceInterface(r14)
                int r0 = r24.getMode()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x05f3:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                android.os.IBinder r1 = r26.readStrongBinder()
                java.lang.String r2 = r26.readString()
                r10.setMode(r0, r1, r2)
                r27.writeNoException()
                return r15
            L_0x0609:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.shouldVibrate(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x061b:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r10.getVibrateSetting(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x062d:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                r10.setVibrateSetting(r0, r1)
                r27.writeNoException()
                return r15
            L_0x063f:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.isValidRingerMode(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0651:
                r12.enforceInterface(r14)
                int r0 = r24.getRingerModeInternal()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x065f:
                r12.enforceInterface(r14)
                int r0 = r24.getRingerModeExternal()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x066d:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                r10.setRingerModeInternal(r0, r1)
                r27.writeNoException()
                return r15
            L_0x067f:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                java.lang.String r1 = r26.readString()
                r10.setRingerModeExternal(r0, r1)
                r27.writeNoException()
                return r15
            L_0x0691:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x069c
                r0 = r15
            L_0x069c:
                java.lang.String r1 = r26.readString()
                int r2 = r26.readInt()
                r10.setMicrophoneMute(r0, r1, r2)
                r27.writeNoException()
                return r15
            L_0x06ab:
                r12.enforceInterface(r14)
                java.util.List r0 = r24.getAudioProductStrategies()
                r27.writeNoException()
                r13.writeTypedList(r0)
                return r15
            L_0x06b9:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r10.getLastAudibleStreamVolume(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x06cb:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x06de
                android.os.Parcelable$Creator<android.media.AudioAttributes> r0 = android.media.AudioAttributes.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x06df
            L_0x06de:
            L_0x06df:
                r0 = r1
                int r1 = r10.getMinVolumeIndexForAttributes(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x06eb:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x06fe
                android.os.Parcelable$Creator<android.media.AudioAttributes> r0 = android.media.AudioAttributes.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x06ff
            L_0x06fe:
            L_0x06ff:
                r0 = r1
                int r1 = r10.getMaxVolumeIndexForAttributes(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x070b:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x071e
                android.os.Parcelable$Creator<android.media.AudioAttributes> r0 = android.media.AudioAttributes.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x071f
            L_0x071e:
            L_0x071f:
                r0 = r1
                int r1 = r10.getVolumeIndexForAttributes(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x072b:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x073e
                android.os.Parcelable$Creator<android.media.AudioAttributes> r0 = android.media.AudioAttributes.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x073f
            L_0x073e:
            L_0x073f:
                r0 = r1
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                java.lang.String r3 = r26.readString()
                r10.setVolumeIndexForAttributes(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x0753:
                r12.enforceInterface(r14)
                java.util.List r0 = r24.getAudioVolumeGroups()
                r27.writeNoException()
                r13.writeTypedList(r0)
                return r15
            L_0x0761:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r10.getStreamMaxVolume(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0773:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r10.getStreamMinVolume(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0785:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r10.getStreamVolume(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x0797:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x07a2
                r0 = r15
            L_0x07a2:
                int r1 = r26.readInt()
                java.lang.String r2 = r26.readString()
                int r3 = r26.readInt()
                r10.setMasterMute(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x07b5:
                r12.enforceInterface(r14)
                boolean r0 = r24.isMasterMute()
                r27.writeNoException()
                r13.writeInt(r0)
                return r15
            L_0x07c3:
                r12.enforceInterface(r14)
                int r1 = r26.readInt()
                if (r1 == 0) goto L_0x07ce
                r0 = r15
            L_0x07ce:
                android.os.IBinder r1 = r26.readStrongBinder()
                r10.forceRemoteSubmixFullVolume(r0, r1)
                r27.writeNoException()
                return r15
            L_0x07d9:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                boolean r1 = r10.isStreamMute(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x07eb:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                java.lang.String r3 = r26.readString()
                r10.setStreamVolume(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x0805:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                int r2 = r26.readInt()
                java.lang.String r3 = r26.readString()
                r10.adjustStreamVolume(r0, r1, r2, r3)
                r27.writeNoException()
                return r15
            L_0x081f:
                r12.enforceInterface(r14)
                int r6 = r26.readInt()
                int r7 = r26.readInt()
                int r8 = r26.readInt()
                java.lang.String r9 = r26.readString()
                java.lang.String r16 = r26.readString()
                r0 = r24
                r1 = r6
                r2 = r7
                r3 = r8
                r4 = r9
                r5 = r16
                r0.adjustSuggestedStreamVolume(r1, r2, r3, r4, r5)
                return r15
            L_0x0842:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                r10.releaseRecorder(r0)
                return r15
            L_0x084d:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                r10.recorderEvent(r0, r1)
                return r15
            L_0x085c:
                r12.enforceInterface(r14)
                android.os.IBinder r0 = r26.readStrongBinder()
                int r1 = r10.trackRecorder(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x086e:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                r10.releasePlayer(r0)
                return r15
            L_0x0879:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r1 = r26.readInt()
                r10.playerEvent(r0, r1)
                return r15
            L_0x0888:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                int r2 = r26.readInt()
                if (r2 == 0) goto L_0x089e
                android.os.Parcelable$Creator<android.media.AudioAttributes> r1 = android.media.AudioAttributes.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r12)
                android.media.AudioAttributes r1 = (android.media.AudioAttributes) r1
                goto L_0x089f
            L_0x089e:
            L_0x089f:
                r10.playerAttributes(r0, r1)
                return r15
            L_0x08a3:
                r12.enforceInterface(r14)
                int r0 = r26.readInt()
                if (r0 == 0) goto L_0x08b6
                android.os.Parcelable$Creator<android.media.PlayerBase$PlayerIdCard> r0 = android.media.PlayerBase.PlayerIdCard.CREATOR
                java.lang.Object r0 = r0.createFromParcel(r12)
                r1 = r0
                android.media.PlayerBase$PlayerIdCard r1 = (android.media.PlayerBase.PlayerIdCard) r1
                goto L_0x08b7
            L_0x08b6:
            L_0x08b7:
                r0 = r1
                int r1 = r10.trackPlayer(r0)
                r27.writeNoException()
                r13.writeInt(r1)
                return r15
            L_0x08c3:
                r13.writeString(r14)
                return r15
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.IAudioService.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IAudioService {
            public static IAudioService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

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
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().trackPlayer(pic);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(2, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playerAttributes(piid, attr);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void playerEvent(int piid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeInt(event);
                    if (this.mRemote.transact(3, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playerEvent(piid, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void releasePlayer(int piid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    if (this.mRemote.transact(4, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().releasePlayer(piid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public int trackRecorder(IBinder recorder) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(recorder);
                    if (!this.mRemote.transact(5, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().trackRecorder(recorder);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void recorderEvent(int riid, int event) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(riid);
                    _data.writeInt(event);
                    if (this.mRemote.transact(6, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().recorderEvent(riid, event);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void releaseRecorder(int riid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(riid);
                    if (this.mRemote.transact(7, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().releaseRecorder(riid);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags, String callingPackage, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(direction);
                    _data.writeInt(suggestedStreamType);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeString(caller);
                    if (this.mRemote.transact(8, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().adjustSuggestedStreamVolume(direction, suggestedStreamType, flags, callingPackage, caller);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void adjustStreamVolume(int streamType, int direction, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(direction);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(9, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().adjustStreamVolume(streamType, direction, flags, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setStreamVolume(int streamType, int index, int flags, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(index);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(10, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setStreamVolume(streamType, index, flags, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isStreamMute(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean z = false;
                    if (!this.mRemote.transact(11, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStreamMute(streamType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceRemoteSubmixFullVolume(boolean startForcing, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(startForcing);
                    _data.writeStrongBinder(cb);
                    if (this.mRemote.transact(12, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceRemoteSubmixFullVolume(startForcing, cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isMasterMute() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(13, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isMasterMute();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMasterMute(boolean mute, int flags, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mute);
                    _data.writeInt(flags);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(14, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMasterMute(mute, flags, callingPackage, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getStreamVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    if (!this.mRemote.transact(15, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStreamVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getStreamMinVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    if (!this.mRemote.transact(16, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStreamMinVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getStreamMaxVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    if (!this.mRemote.transact(17, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStreamMaxVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AudioVolumeGroup> getAudioVolumeGroups() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioVolumeGroups();
                    }
                    _reply.readException();
                    List<AudioVolumeGroup> _result = _reply.createTypedArrayList(AudioVolumeGroup.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(19, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolumeIndexForAttributes(aa, index, flags, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(20, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVolumeIndexForAttributes(aa);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(21, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMaxVolumeIndexForAttributes(aa);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(22, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMinVolumeIndexForAttributes(aa);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLastAudibleStreamVolume(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    if (!this.mRemote.transact(23, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getLastAudibleStreamVolume(streamType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<AudioProductStrategy> getAudioProductStrategies() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAudioProductStrategies();
                    }
                    _reply.readException();
                    List<AudioProductStrategy> _result = _reply.createTypedArrayList(AudioProductStrategy.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMicrophoneMute(boolean on, String callingPackage, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    _data.writeString(callingPackage);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(25, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMicrophoneMute(on, callingPackage, userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRingerModeExternal(int ringerMode, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    _data.writeString(caller);
                    if (this.mRemote.transact(26, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRingerModeExternal(ringerMode, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRingerModeInternal(int ringerMode, String caller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    _data.writeString(caller);
                    if (this.mRemote.transact(27, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRingerModeInternal(ringerMode, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRingerModeExternal() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRingerModeExternal();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRingerModeInternal() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRingerModeInternal();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isValidRingerMode(int ringerMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(ringerMode);
                    boolean z = false;
                    if (!this.mRemote.transact(30, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isValidRingerMode(ringerMode);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVibrateSetting(int vibrateType, int vibrateSetting) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    _data.writeInt(vibrateSetting);
                    if (this.mRemote.transact(31, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVibrateSetting(vibrateType, vibrateSetting);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVibrateSetting(int vibrateType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    if (!this.mRemote.transact(32, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getVibrateSetting(vibrateType);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean shouldVibrate(int vibrateType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(vibrateType);
                    boolean z = false;
                    if (!this.mRemote.transact(33, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().shouldVibrate(vibrateType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setMode(int mode, IBinder cb, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeStrongBinder(cb);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(34, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setMode(mode, cb, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(35, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getMode();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playSoundEffect(int effectType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectType);
                    if (this.mRemote.transact(36, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playSoundEffect(effectType);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void playSoundEffectVolume(int effectType, float volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(effectType);
                    _data.writeFloat(volume);
                    if (this.mRemote.transact(37, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playSoundEffectVolume(effectType, volume);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean loadSoundEffects() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(38, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().loadSoundEffects();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unloadSoundEffects() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(39, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unloadSoundEffects();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void reloadAudioSettings() throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(40, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().reloadAudioSettings();
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void avrcpSupportsAbsoluteVolume(String address, boolean support) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(address);
                    _data.writeInt(support);
                    if (this.mRemote.transact(41, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().avrcpSupportsAbsoluteVolume(address, support);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void setSpeakerphoneOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    if (this.mRemote.transact(42, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setSpeakerphoneOn(on);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSpeakerphoneOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(43, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isSpeakerphoneOn();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBluetoothScoOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    if (this.mRemote.transact(44, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBluetoothScoOn(on);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBluetoothScoOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(45, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBluetoothScoOn();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setBluetoothA2dpOn(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    if (this.mRemote.transact(46, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBluetoothA2dpOn(on);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isBluetoothA2dpOn() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(47, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isBluetoothA2dpOn();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int requestAudioFocus(AudioAttributes aa, int durationHint, IBinder cb, IAudioFocusDispatcher fd, String clientId, String callingPackageName, int flags, IAudioPolicyCallback pcb, int sdk) throws RemoteException {
                AudioAttributes audioAttributes = aa;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioAttributes != null) {
                        _data.writeInt(1);
                        audioAttributes.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    try {
                        _data.writeInt(durationHint);
                        try {
                            _data.writeStrongBinder(cb);
                            IBinder iBinder = null;
                            _data.writeStrongBinder(fd != null ? fd.asBinder() : null);
                            _data.writeString(clientId);
                            _data.writeString(callingPackageName);
                            _data.writeInt(flags);
                            if (pcb != null) {
                                iBinder = pcb.asBinder();
                            }
                            _data.writeStrongBinder(iBinder);
                            _data.writeInt(sdk);
                            if (this.mRemote.transact(48, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                int _result = _reply.readInt();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            int requestAudioFocus = Stub.getDefaultImpl().requestAudioFocus(aa, durationHint, cb, fd, clientId, callingPackageName, flags, pcb, sdk);
                            _reply.recycle();
                            _data.recycle();
                            return requestAudioFocus;
                        } catch (Throwable th) {
                            th = th;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        IBinder iBinder2 = cb;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    int i = durationHint;
                    IBinder iBinder22 = cb;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

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
                    if (!this.mRemote.transact(49, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().abandonAudioFocus(fd, clientId, aa, callingPackageName);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterAudioFocusClient(String clientId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(clientId);
                    if (this.mRemote.transact(50, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterAudioFocusClient(clientId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCurrentAudioFocus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(51, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getCurrentAudioFocus();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startBluetoothSco(IBinder cb, int targetSdkVersion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    _data.writeInt(targetSdkVersion);
                    if (this.mRemote.transact(52, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startBluetoothSco(cb, targetSdkVersion);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startBluetoothScoVirtualCall(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    if (this.mRemote.transact(53, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().startBluetoothScoVirtualCall(cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopBluetoothSco(IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb);
                    if (this.mRemote.transact(54, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().stopBluetoothSco(cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void forceVolumeControlStream(int streamType, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeStrongBinder(cb);
                    if (this.mRemote.transact(55, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().forceVolumeControlStream(streamType, cb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRingtonePlayer(IRingtonePlayer player) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(player != null ? player.asBinder() : null);
                    if (this.mRemote.transact(56, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setRingtonePlayer(player);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IRingtonePlayer getRingtonePlayer() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(57, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getRingtonePlayer();
                    }
                    _reply.readException();
                    IRingtonePlayer _result = IRingtonePlayer.Stub.asInterface(_reply.readStrongBinder());
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getUiSoundsStreamType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(58, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getUiSoundsStreamType();
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(59, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setWiredDeviceConnectionState(type, state, address, name, caller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(60, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleBluetoothA2dpDeviceConfigChange(device);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    _data.writeInt(suppressNoisyIntent);
                    _data.writeInt(a2dpVolume);
                    if (this.mRemote.transact(61, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().handleBluetoothA2dpActiveDeviceChange(device, state, profile, suppressNoisyIntent, a2dpVolume);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public AudioRoutesInfo startWatchingRoutes(IAudioRoutesObserver observer) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    AudioRoutesInfo _result = null;
                    _data.writeStrongBinder(observer != null ? observer.asBinder() : null);
                    if (!this.mRemote.transact(62, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().startWatchingRoutes(observer);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = AudioRoutesInfo.CREATOR.createFromParcel(_reply);
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isCameraSoundForced() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(63, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isCameraSoundForced();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setVolumeController(IVolumeController controller) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(controller != null ? controller.asBinder() : null);
                    if (this.mRemote.transact(64, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolumeController(controller);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void notifyVolumeControllerVisible(IVolumeController controller, boolean visible) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(controller != null ? controller.asBinder() : null);
                    _data.writeInt(visible);
                    if (this.mRemote.transact(65, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().notifyVolumeControllerVisible(controller, visible);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isStreamAffectedByRingerMode(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean z = false;
                    if (!this.mRemote.transact(66, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStreamAffectedByRingerMode(streamType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isStreamAffectedByMute(int streamType) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    boolean z = false;
                    if (!this.mRemote.transact(67, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isStreamAffectedByMute(streamType);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableSafeMediaVolume(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(68, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableSafeMediaVolume(callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setHdmiSystemAudioSupported(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(on);
                    if (!this.mRemote.transact(69, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setHdmiSystemAudioSupported(on);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isHdmiSystemAudioSupported() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(70, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isHdmiSystemAudioSupported();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String registerAudioPolicy(AudioPolicyConfig policyConfig, IAudioPolicyCallback pcb, boolean hasFocusListener, boolean isFocusPolicy, boolean isTestFocusPolicy, boolean isVolumeController, IMediaProjection projection) throws RemoteException {
                AudioPolicyConfig audioPolicyConfig = policyConfig;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (audioPolicyConfig != null) {
                        _data.writeInt(1);
                        audioPolicyConfig.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    IBinder iBinder = null;
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    try {
                        _data.writeInt(hasFocusListener ? 1 : 0);
                    } catch (Throwable th) {
                        th = th;
                        boolean z = isFocusPolicy;
                        boolean z2 = isTestFocusPolicy;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(isFocusPolicy ? 1 : 0);
                        try {
                            _data.writeInt(isTestFocusPolicy ? 1 : 0);
                            _data.writeInt(isVolumeController ? 1 : 0);
                            if (projection != null) {
                                iBinder = projection.asBinder();
                            }
                            _data.writeStrongBinder(iBinder);
                            if (this.mRemote.transact(71, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                                _reply.readException();
                                String _result = _reply.readString();
                                _reply.recycle();
                                _data.recycle();
                                return _result;
                            }
                            String registerAudioPolicy = Stub.getDefaultImpl().registerAudioPolicy(policyConfig, pcb, hasFocusListener, isFocusPolicy, isTestFocusPolicy, isVolumeController, projection);
                            _reply.recycle();
                            _data.recycle();
                            return registerAudioPolicy;
                        } catch (Throwable th2) {
                            th = th2;
                            _reply.recycle();
                            _data.recycle();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        boolean z22 = isTestFocusPolicy;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    boolean z3 = hasFocusListener;
                    boolean z4 = isFocusPolicy;
                    boolean z222 = isTestFocusPolicy;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            public void unregisterAudioPolicyAsync(IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    if (this.mRemote.transact(72, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterAudioPolicyAsync(pcb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void unregisterAudioPolicy(IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    if (this.mRemote.transact(73, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().unregisterAudioPolicy(pcb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(74, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().addMixForPolicy(policyConfig, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(75, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeMixForPolicy(policyConfig, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setFocusPropertiesForPolicy(int duckingBehavior, IAudioPolicyCallback pcb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(duckingBehavior);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    if (!this.mRemote.transact(76, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setFocusPropertiesForPolicy(duckingBehavior, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(77, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setVolumePolicy(policy);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasRegisteredDynamicPolicy() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(78, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasRegisteredDynamicPolicy();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rcdb != null ? rcdb.asBinder() : null);
                    if (this.mRemote.transact(79, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerRecordingCallback(rcdb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterRecordingCallback(IRecordingConfigDispatcher rcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(rcdb != null ? rcdb.asBinder() : null);
                    if (this.mRemote.transact(80, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterRecordingCallback(rcdb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public List<AudioRecordingConfiguration> getActiveRecordingConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(81, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActiveRecordingConfigurations();
                    }
                    _reply.readException();
                    List<AudioRecordingConfiguration> _result = _reply.createTypedArrayList(AudioRecordingConfiguration.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcdb != null ? pcdb.asBinder() : null);
                    if (this.mRemote.transact(82, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerPlaybackCallback(pcdb);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterPlaybackCallback(IPlaybackConfigDispatcher pcdb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcdb != null ? pcdb.asBinder() : null);
                    if (this.mRemote.transact(83, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterPlaybackCallback(pcdb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public List<AudioPlaybackConfiguration> getActivePlaybackConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(84, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getActivePlaybackConfigurations();
                    }
                    _reply.readException();
                    List<AudioPlaybackConfiguration> _result = _reply.createTypedArrayList(AudioPlaybackConfiguration.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableRingtoneSync(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    if (this.mRemote.transact(85, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().disableRingtoneSync(userId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(86, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getFocusRampTimeMs(focusGain, attr);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (!this.mRemote.transact(87, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().dispatchFocusChange(afi, focusChange, pcb);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void playerHasOpPlayAudio(int piid, boolean hasOpPlayAudio) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(piid);
                    _data.writeInt(hasOpPlayAudio);
                    if (this.mRemote.transact(88, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().playerHasOpPlayAudio(piid, hasOpPlayAudio);
                    }
                } finally {
                    _data.recycle();
                }
            }

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
                    _data.writeInt(suppressNoisyIntent);
                    _data.writeInt(musicDevice);
                    if (this.mRemote.transact(89, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBluetoothHearingAidDeviceConnectionState(device, state, suppressNoisyIntent, musicDevice);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    _data.writeInt(suppressNoisyIntent);
                    _data.writeInt(a2dpVolume);
                    if (this.mRemote.transact(90, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().setBluetoothA2dpDeviceConnectionStateSuppressNoisyIntent(device, state, profile, suppressNoisyIntent, a2dpVolume);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                    if (this.mRemote.transact(91, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().setFocusRequestResultFromExtPolicy(afi, requestResult, pcb);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public void registerAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(asd != null ? asd.asBinder() : null);
                    if (this.mRemote.transact(92, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().registerAudioServerStateDispatcher(asd);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterAudioServerStateDispatcher(IAudioServerStateDispatcher asd) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(asd != null ? asd.asBinder() : null);
                    if (this.mRemote.transact(93, _data, (Parcel) null, 1) || Stub.getDefaultImpl() == null) {
                        _data.recycle();
                    } else {
                        Stub.getDefaultImpl().unregisterAudioServerStateDispatcher(asd);
                    }
                } finally {
                    _data.recycle();
                }
            }

            public boolean isAudioServerRunning() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean z = false;
                    if (!this.mRemote.transact(94, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAudioServerRunning();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        z = true;
                    }
                    boolean _result = z;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setUidDeviceAffinity(IAudioPolicyCallback pcb, int uid, int[] deviceTypes, String[] deviceAddresses) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    _data.writeInt(uid);
                    _data.writeIntArray(deviceTypes);
                    _data.writeStringArray(deviceAddresses);
                    if (!this.mRemote.transact(95, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().setUidDeviceAffinity(pcb, uid, deviceTypes, deviceAddresses);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int removeUidDeviceAffinity(IAudioPolicyCallback pcb, int uid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(pcb != null ? pcb.asBinder() : null);
                    _data.writeInt(uid);
                    if (!this.mRemote.transact(96, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().removeUidDeviceAffinity(pcb, uid);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasHapticChannels(Uri uri) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean _result = true;
                    if (uri != null) {
                        _data.writeInt(1);
                        uri.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(97, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().hasHapticChannels(uri);
                    }
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAudioService impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IAudioService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
