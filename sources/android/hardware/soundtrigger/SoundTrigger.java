package android.hardware.soundtrigger;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.media.AudioFormat;
import android.p007os.Handler;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.system.OsConstants;
import com.android.internal.logging.nano.MetricsProto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@SystemApi
/* loaded from: classes.dex */
public class SoundTrigger {
    public static final int RECOGNITION_MODE_USER_AUTHENTICATION = 4;
    public static final int RECOGNITION_MODE_USER_IDENTIFICATION = 2;
    public static final int RECOGNITION_MODE_VOICE_TRIGGER = 1;
    public static final int RECOGNITION_STATUS_ABORT = 1;
    public static final int RECOGNITION_STATUS_FAILURE = 2;
    public static final int RECOGNITION_STATUS_GET_STATE_RESPONSE = 3;
    public static final int RECOGNITION_STATUS_SUCCESS = 0;
    public static final int SERVICE_STATE_DISABLED = 1;
    public static final int SERVICE_STATE_ENABLED = 0;
    public static final int SOUNDMODEL_STATUS_UPDATED = 0;
    public static final int STATUS_ERROR = Integer.MIN_VALUE;
    public static final int STATUS_OK = 0;
    public static final int STATUS_PERMISSION_DENIED = -OsConstants.EPERM;
    public static final int STATUS_NO_INIT = -OsConstants.ENODEV;
    public static final int STATUS_BAD_VALUE = -OsConstants.EINVAL;
    public static final int STATUS_DEAD_OBJECT = -OsConstants.EPIPE;
    public static final int STATUS_INVALID_OPERATION = -OsConstants.ENOSYS;

    /* loaded from: classes.dex */
    public interface StatusListener {
        void onRecognition(RecognitionEvent recognitionEvent);

        void onServiceDied();

        void onServiceStateChange(int i);

        void onSoundModelUpdate(SoundModelEvent soundModelEvent);
    }

    private static native int listModules(String str, ArrayList<ModuleProperties> arrayList);

    private SoundTrigger() {
    }

    /* loaded from: classes.dex */
    public static class ModuleProperties implements Parcelable {
        public static final Parcelable.Creator<ModuleProperties> CREATOR = new Parcelable.Creator<ModuleProperties>() { // from class: android.hardware.soundtrigger.SoundTrigger.ModuleProperties.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public ModuleProperties createFromParcel(Parcel in) {
                return ModuleProperties.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public ModuleProperties[] newArray(int size) {
                return new ModuleProperties[size];
            }
        };
        public final String description;
        @UnsupportedAppUsage

        /* renamed from: id */
        public final int f107id;
        public final String implementor;
        public final int maxBufferMs;
        public final int maxKeyphrases;
        @UnsupportedAppUsage
        public final int maxSoundModels;
        public final int maxUsers;
        public final int powerConsumptionMw;
        public final int recognitionModes;
        public final boolean returnsTriggerInEvent;
        public final boolean supportsCaptureTransition;
        public final boolean supportsConcurrentCapture;
        @UnsupportedAppUsage
        public final UUID uuid;
        public final int version;

        @UnsupportedAppUsage
        ModuleProperties(int id, String implementor, String description, String uuid, int version, int maxSoundModels, int maxKeyphrases, int maxUsers, int recognitionModes, boolean supportsCaptureTransition, int maxBufferMs, boolean supportsConcurrentCapture, int powerConsumptionMw, boolean returnsTriggerInEvent) {
            this.f107id = id;
            this.implementor = implementor;
            this.description = description;
            this.uuid = UUID.fromString(uuid);
            this.version = version;
            this.maxSoundModels = maxSoundModels;
            this.maxKeyphrases = maxKeyphrases;
            this.maxUsers = maxUsers;
            this.recognitionModes = recognitionModes;
            this.supportsCaptureTransition = supportsCaptureTransition;
            this.maxBufferMs = maxBufferMs;
            this.supportsConcurrentCapture = supportsConcurrentCapture;
            this.powerConsumptionMw = powerConsumptionMw;
            this.returnsTriggerInEvent = returnsTriggerInEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ModuleProperties fromParcel(Parcel in) {
            int id = in.readInt();
            String implementor = in.readString();
            String description = in.readString();
            String uuid = in.readString();
            int version = in.readInt();
            int maxSoundModels = in.readInt();
            int maxKeyphrases = in.readInt();
            int maxUsers = in.readInt();
            int recognitionModes = in.readInt();
            boolean supportsCaptureTransition = in.readByte() == 1;
            int maxBufferMs = in.readInt();
            boolean supportsConcurrentCapture = in.readByte() == 1;
            int powerConsumptionMw = in.readInt();
            boolean returnsTriggerInEvent = in.readByte() == 1;
            return new ModuleProperties(id, implementor, description, uuid, version, maxSoundModels, maxKeyphrases, maxUsers, recognitionModes, supportsCaptureTransition, maxBufferMs, supportsConcurrentCapture, powerConsumptionMw, returnsTriggerInEvent);
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.f107id);
            dest.writeString(this.implementor);
            dest.writeString(this.description);
            dest.writeString(this.uuid.toString());
            dest.writeInt(this.version);
            dest.writeInt(this.maxSoundModels);
            dest.writeInt(this.maxKeyphrases);
            dest.writeInt(this.maxUsers);
            dest.writeInt(this.recognitionModes);
            dest.writeByte(this.supportsCaptureTransition ? (byte) 1 : (byte) 0);
            dest.writeInt(this.maxBufferMs);
            dest.writeByte(this.supportsConcurrentCapture ? (byte) 1 : (byte) 0);
            dest.writeInt(this.powerConsumptionMw);
            dest.writeByte(this.returnsTriggerInEvent ? (byte) 1 : (byte) 0);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "ModuleProperties [id=" + this.f107id + ", implementor=" + this.implementor + ", description=" + this.description + ", uuid=" + this.uuid + ", version=" + this.version + ", maxSoundModels=" + this.maxSoundModels + ", maxKeyphrases=" + this.maxKeyphrases + ", maxUsers=" + this.maxUsers + ", recognitionModes=" + this.recognitionModes + ", supportsCaptureTransition=" + this.supportsCaptureTransition + ", maxBufferMs=" + this.maxBufferMs + ", supportsConcurrentCapture=" + this.supportsConcurrentCapture + ", powerConsumptionMw=" + this.powerConsumptionMw + ", returnsTriggerInEvent=" + this.returnsTriggerInEvent + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class SoundModel {
        public static final int TYPE_GENERIC_SOUND = 1;
        public static final int TYPE_KEYPHRASE = 0;
        public static final int TYPE_UNKNOWN = -1;
        @UnsupportedAppUsage
        public final byte[] data;
        public final int type;
        @UnsupportedAppUsage
        public final UUID uuid;
        @UnsupportedAppUsage
        public final UUID vendorUuid;

        public SoundModel(UUID uuid, UUID vendorUuid, int type, byte[] data) {
            this.uuid = uuid;
            this.vendorUuid = vendorUuid;
            this.type = type;
            this.data = data;
        }

        public int hashCode() {
            int result = (1 * 31) + Arrays.hashCode(this.data);
            return (((((result * 31) + this.type) * 31) + (this.uuid == null ? 0 : this.uuid.hashCode())) * 31) + (this.vendorUuid != null ? this.vendorUuid.hashCode() : 0);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof SoundModel)) {
                return false;
            }
            SoundModel other = (SoundModel) obj;
            if (!Arrays.equals(this.data, other.data) || this.type != other.type) {
                return false;
            }
            if (this.uuid == null) {
                if (other.uuid != null) {
                    return false;
                }
            } else if (!this.uuid.equals(other.uuid)) {
                return false;
            }
            if (this.vendorUuid == null) {
                if (other.vendorUuid != null) {
                    return false;
                }
            } else if (!this.vendorUuid.equals(other.vendorUuid)) {
                return false;
            }
            return true;
        }
    }

    /* loaded from: classes.dex */
    public static class Keyphrase implements Parcelable {
        public static final Parcelable.Creator<Keyphrase> CREATOR = new Parcelable.Creator<Keyphrase>() { // from class: android.hardware.soundtrigger.SoundTrigger.Keyphrase.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Keyphrase createFromParcel(Parcel in) {
                return Keyphrase.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public Keyphrase[] newArray(int size) {
                return new Keyphrase[size];
            }
        };
        @UnsupportedAppUsage

        /* renamed from: id */
        public final int f105id;
        @UnsupportedAppUsage
        public final String locale;
        @UnsupportedAppUsage
        public final int recognitionModes;
        @UnsupportedAppUsage
        public final String text;
        @UnsupportedAppUsage
        public final int[] users;

        @UnsupportedAppUsage
        public Keyphrase(int id, int recognitionModes, String locale, String text, int[] users) {
            this.f105id = id;
            this.recognitionModes = recognitionModes;
            this.locale = locale;
            this.text = text;
            this.users = users;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Keyphrase fromParcel(Parcel in) {
            int id = in.readInt();
            int recognitionModes = in.readInt();
            String locale = in.readString();
            String text = in.readString();
            int[] users = null;
            int numUsers = in.readInt();
            if (numUsers >= 0) {
                users = new int[numUsers];
                in.readIntArray(users);
            }
            return new Keyphrase(id, recognitionModes, locale, text, users);
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.f105id);
            dest.writeInt(this.recognitionModes);
            dest.writeString(this.locale);
            dest.writeString(this.text);
            if (this.users != null) {
                dest.writeInt(this.users.length);
                dest.writeIntArray(this.users);
                return;
            }
            dest.writeInt(-1);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            int result = (1 * 31) + (this.text == null ? 0 : this.text.hashCode());
            return (((((((result * 31) + this.f105id) * 31) + (this.locale != null ? this.locale.hashCode() : 0)) * 31) + this.recognitionModes) * 31) + Arrays.hashCode(this.users);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Keyphrase other = (Keyphrase) obj;
            if (this.text == null) {
                if (other.text != null) {
                    return false;
                }
            } else if (!this.text.equals(other.text)) {
                return false;
            }
            if (this.f105id != other.f105id) {
                return false;
            }
            if (this.locale == null) {
                if (other.locale != null) {
                    return false;
                }
            } else if (!this.locale.equals(other.locale)) {
                return false;
            }
            if (this.recognitionModes == other.recognitionModes && Arrays.equals(this.users, other.users)) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "Keyphrase [id=" + this.f105id + ", recognitionModes=" + this.recognitionModes + ", locale=" + this.locale + ", text=" + this.text + ", users=" + Arrays.toString(this.users) + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class KeyphraseSoundModel extends SoundModel implements Parcelable {
        public static final Parcelable.Creator<KeyphraseSoundModel> CREATOR = new Parcelable.Creator<KeyphraseSoundModel>() { // from class: android.hardware.soundtrigger.SoundTrigger.KeyphraseSoundModel.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public KeyphraseSoundModel createFromParcel(Parcel in) {
                return KeyphraseSoundModel.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public KeyphraseSoundModel[] newArray(int size) {
                return new KeyphraseSoundModel[size];
            }
        };
        @UnsupportedAppUsage
        public final Keyphrase[] keyphrases;

        @UnsupportedAppUsage
        public KeyphraseSoundModel(UUID uuid, UUID vendorUuid, byte[] data, Keyphrase[] keyphrases) {
            super(uuid, vendorUuid, 0, data);
            this.keyphrases = keyphrases;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static KeyphraseSoundModel fromParcel(Parcel in) {
            UUID uuid = UUID.fromString(in.readString());
            UUID vendorUuid = null;
            int length = in.readInt();
            if (length >= 0) {
                vendorUuid = UUID.fromString(in.readString());
            }
            byte[] data = in.readBlob();
            Keyphrase[] keyphrases = (Keyphrase[]) in.createTypedArray(Keyphrase.CREATOR);
            return new KeyphraseSoundModel(uuid, vendorUuid, data, keyphrases);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.uuid.toString());
            if (this.vendorUuid == null) {
                dest.writeInt(-1);
            } else {
                dest.writeInt(this.vendorUuid.toString().length());
                dest.writeString(this.vendorUuid.toString());
            }
            dest.writeBlob(this.data);
            dest.writeTypedArray(this.keyphrases, flags);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("KeyphraseSoundModel [keyphrases=");
            sb.append(Arrays.toString(this.keyphrases));
            sb.append(", uuid=");
            sb.append(this.uuid);
            sb.append(", vendorUuid=");
            sb.append(this.vendorUuid);
            sb.append(", type=");
            sb.append(this.type);
            sb.append(", data=");
            sb.append(this.data == null ? 0 : this.data.length);
            sb.append("]");
            return sb.toString();
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.SoundModel
        public int hashCode() {
            int result = super.hashCode();
            return (result * 31) + Arrays.hashCode(this.keyphrases);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.SoundModel
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (super.equals(obj) && (obj instanceof KeyphraseSoundModel)) {
                KeyphraseSoundModel other = (KeyphraseSoundModel) obj;
                return Arrays.equals(this.keyphrases, other.keyphrases);
            }
            return false;
        }
    }

    /* loaded from: classes.dex */
    public static class GenericSoundModel extends SoundModel implements Parcelable {
        public static final Parcelable.Creator<GenericSoundModel> CREATOR = new Parcelable.Creator<GenericSoundModel>() { // from class: android.hardware.soundtrigger.SoundTrigger.GenericSoundModel.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public GenericSoundModel createFromParcel(Parcel in) {
                return GenericSoundModel.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public GenericSoundModel[] newArray(int size) {
                return new GenericSoundModel[size];
            }
        };

        @UnsupportedAppUsage
        public GenericSoundModel(UUID uuid, UUID vendorUuid, byte[] data) {
            super(uuid, vendorUuid, 1, data);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static GenericSoundModel fromParcel(Parcel in) {
            UUID uuid = UUID.fromString(in.readString());
            UUID vendorUuid = null;
            int length = in.readInt();
            if (length >= 0) {
                vendorUuid = UUID.fromString(in.readString());
            }
            byte[] data = in.readBlob();
            return new GenericSoundModel(uuid, vendorUuid, data);
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.uuid.toString());
            if (this.vendorUuid == null) {
                dest.writeInt(-1);
            } else {
                dest.writeInt(this.vendorUuid.toString().length());
                dest.writeString(this.vendorUuid.toString());
            }
            dest.writeBlob(this.data);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("GenericSoundModel [uuid=");
            sb.append(this.uuid);
            sb.append(", vendorUuid=");
            sb.append(this.vendorUuid);
            sb.append(", type=");
            sb.append(this.type);
            sb.append(", data=");
            sb.append(this.data == null ? 0 : this.data.length);
            sb.append("]");
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public static class RecognitionEvent {
        public static final Parcelable.Creator<RecognitionEvent> CREATOR = new Parcelable.Creator<RecognitionEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.RecognitionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public RecognitionEvent createFromParcel(Parcel in) {
                return RecognitionEvent.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public RecognitionEvent[] newArray(int size) {
                return new RecognitionEvent[size];
            }
        };
        @UnsupportedAppUsage
        public final boolean captureAvailable;
        public final int captureDelayMs;
        public final AudioFormat captureFormat;
        public final int capturePreambleMs;
        @UnsupportedAppUsage
        public final int captureSession;
        @UnsupportedAppUsage
        public final byte[] data;
        @UnsupportedAppUsage
        public final int soundModelHandle;
        @UnsupportedAppUsage
        public final int status;
        public final boolean triggerInData;

        @UnsupportedAppUsage
        public RecognitionEvent(int status, int soundModelHandle, boolean captureAvailable, int captureSession, int captureDelayMs, int capturePreambleMs, boolean triggerInData, AudioFormat captureFormat, byte[] data) {
            this.status = status;
            this.soundModelHandle = soundModelHandle;
            this.captureAvailable = captureAvailable;
            this.captureSession = captureSession;
            this.captureDelayMs = captureDelayMs;
            this.capturePreambleMs = capturePreambleMs;
            this.triggerInData = triggerInData;
            this.captureFormat = captureFormat;
            this.data = data;
        }

        public boolean isCaptureAvailable() {
            return this.captureAvailable;
        }

        public AudioFormat getCaptureFormat() {
            return this.captureFormat;
        }

        public int getCaptureSession() {
            return this.captureSession;
        }

        public byte[] getData() {
            return this.data;
        }

        protected static RecognitionEvent fromParcel(Parcel in) {
            int status = in.readInt();
            int soundModelHandle = in.readInt();
            boolean captureAvailable = in.readByte() == 1;
            int captureSession = in.readInt();
            int captureDelayMs = in.readInt();
            int capturePreambleMs = in.readInt();
            boolean triggerInData = in.readByte() == 1;
            AudioFormat captureFormat = null;
            if (in.readByte() == 1) {
                int sampleRate = in.readInt();
                int encoding = in.readInt();
                int channelMask = in.readInt();
                captureFormat = new AudioFormat.Builder().setChannelMask(channelMask).setEncoding(encoding).setSampleRate(sampleRate).build();
            }
            byte[] data = in.readBlob();
            return new RecognitionEvent(status, soundModelHandle, captureAvailable, captureSession, captureDelayMs, capturePreambleMs, triggerInData, captureFormat, data);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeInt(this.soundModelHandle);
            dest.writeByte(this.captureAvailable ? (byte) 1 : (byte) 0);
            dest.writeInt(this.captureSession);
            dest.writeInt(this.captureDelayMs);
            dest.writeInt(this.capturePreambleMs);
            dest.writeByte(this.triggerInData ? (byte) 1 : (byte) 0);
            if (this.captureFormat != null) {
                dest.writeByte((byte) 1);
                dest.writeInt(this.captureFormat.getSampleRate());
                dest.writeInt(this.captureFormat.getEncoding());
                dest.writeInt(this.captureFormat.getChannelMask());
            } else {
                dest.writeByte((byte) 0);
            }
            dest.writeBlob(this.data);
        }

        public int hashCode() {
            int i = 1 * 31;
            boolean z = this.captureAvailable;
            int i2 = MetricsProto.MetricsEvent.ANOMALY_TYPE_UNOPTIMIZED_BT;
            int result = i + (z ? 1231 : 1237);
            int result2 = ((((((result * 31) + this.captureDelayMs) * 31) + this.capturePreambleMs) * 31) + this.captureSession) * 31;
            if (this.triggerInData) {
                i2 = 1231;
            }
            int result3 = result2 + i2;
            if (this.captureFormat != null) {
                result3 = (((((result3 * 31) + this.captureFormat.getSampleRate()) * 31) + this.captureFormat.getEncoding()) * 31) + this.captureFormat.getChannelMask();
            }
            return (((((result3 * 31) + Arrays.hashCode(this.data)) * 31) + this.soundModelHandle) * 31) + this.status;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            RecognitionEvent other = (RecognitionEvent) obj;
            if (this.captureAvailable != other.captureAvailable || this.captureDelayMs != other.captureDelayMs || this.capturePreambleMs != other.capturePreambleMs || this.captureSession != other.captureSession || !Arrays.equals(this.data, other.data) || this.soundModelHandle != other.soundModelHandle || this.status != other.status || this.triggerInData != other.triggerInData) {
                return false;
            }
            if (this.captureFormat == null) {
                if (other.captureFormat != null) {
                    return false;
                }
            } else if (other.captureFormat == null || this.captureFormat.getSampleRate() != other.captureFormat.getSampleRate() || this.captureFormat.getEncoding() != other.captureFormat.getEncoding() || this.captureFormat.getChannelMask() != other.captureFormat.getChannelMask()) {
                return false;
            }
            return true;
        }

        public String toString() {
            String str;
            String str2;
            String str3;
            StringBuilder sb = new StringBuilder();
            sb.append("RecognitionEvent [status=");
            sb.append(this.status);
            sb.append(", soundModelHandle=");
            sb.append(this.soundModelHandle);
            sb.append(", captureAvailable=");
            sb.append(this.captureAvailable);
            sb.append(", captureSession=");
            sb.append(this.captureSession);
            sb.append(", captureDelayMs=");
            sb.append(this.captureDelayMs);
            sb.append(", capturePreambleMs=");
            sb.append(this.capturePreambleMs);
            sb.append(", triggerInData=");
            sb.append(this.triggerInData);
            if (this.captureFormat == null) {
                str = "";
            } else {
                str = ", sampleRate=" + this.captureFormat.getSampleRate();
            }
            sb.append(str);
            if (this.captureFormat == null) {
                str2 = "";
            } else {
                str2 = ", encoding=" + this.captureFormat.getEncoding();
            }
            sb.append(str2);
            if (this.captureFormat == null) {
                str3 = "";
            } else {
                str3 = ", channelMask=" + this.captureFormat.getChannelMask();
            }
            sb.append(str3);
            sb.append(", data=");
            sb.append(this.data == null ? 0 : this.data.length);
            sb.append("]");
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public static class RecognitionConfig implements Parcelable {
        public static final Parcelable.Creator<RecognitionConfig> CREATOR = new Parcelable.Creator<RecognitionConfig>() { // from class: android.hardware.soundtrigger.SoundTrigger.RecognitionConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public RecognitionConfig createFromParcel(Parcel in) {
                return RecognitionConfig.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public RecognitionConfig[] newArray(int size) {
                return new RecognitionConfig[size];
            }
        };
        public final boolean allowMultipleTriggers;
        @UnsupportedAppUsage
        public final boolean captureRequested;
        @UnsupportedAppUsage
        public final byte[] data;
        @UnsupportedAppUsage
        public final KeyphraseRecognitionExtra[] keyphrases;

        @UnsupportedAppUsage
        public RecognitionConfig(boolean captureRequested, boolean allowMultipleTriggers, KeyphraseRecognitionExtra[] keyphrases, byte[] data) {
            this.captureRequested = captureRequested;
            this.allowMultipleTriggers = allowMultipleTriggers;
            this.keyphrases = keyphrases;
            this.data = data;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static RecognitionConfig fromParcel(Parcel in) {
            boolean captureRequested = in.readByte() == 1;
            boolean allowMultipleTriggers = in.readByte() == 1;
            KeyphraseRecognitionExtra[] keyphrases = (KeyphraseRecognitionExtra[]) in.createTypedArray(KeyphraseRecognitionExtra.CREATOR);
            byte[] data = in.readBlob();
            return new RecognitionConfig(captureRequested, allowMultipleTriggers, keyphrases, data);
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.captureRequested ? (byte) 1 : (byte) 0);
            dest.writeByte(this.allowMultipleTriggers ? (byte) 1 : (byte) 0);
            dest.writeTypedArray(this.keyphrases, flags);
            dest.writeBlob(this.data);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "RecognitionConfig [captureRequested=" + this.captureRequested + ", allowMultipleTriggers=" + this.allowMultipleTriggers + ", keyphrases=" + Arrays.toString(this.keyphrases) + ", data=" + Arrays.toString(this.data) + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class ConfidenceLevel implements Parcelable {
        public static final Parcelable.Creator<ConfidenceLevel> CREATOR = new Parcelable.Creator<ConfidenceLevel>() { // from class: android.hardware.soundtrigger.SoundTrigger.ConfidenceLevel.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public ConfidenceLevel createFromParcel(Parcel in) {
                return ConfidenceLevel.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public ConfidenceLevel[] newArray(int size) {
                return new ConfidenceLevel[size];
            }
        };
        @UnsupportedAppUsage
        public final int confidenceLevel;
        @UnsupportedAppUsage
        public final int userId;

        @UnsupportedAppUsage
        public ConfidenceLevel(int userId, int confidenceLevel) {
            this.userId = userId;
            this.confidenceLevel = confidenceLevel;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ConfidenceLevel fromParcel(Parcel in) {
            int userId = in.readInt();
            int confidenceLevel = in.readInt();
            return new ConfidenceLevel(userId, confidenceLevel);
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.userId);
            dest.writeInt(this.confidenceLevel);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            int result = (1 * 31) + this.confidenceLevel;
            return (result * 31) + this.userId;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ConfidenceLevel other = (ConfidenceLevel) obj;
            if (this.confidenceLevel == other.confidenceLevel && this.userId == other.userId) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "ConfidenceLevel [userId=" + this.userId + ", confidenceLevel=" + this.confidenceLevel + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class KeyphraseRecognitionExtra implements Parcelable {
        public static final Parcelable.Creator<KeyphraseRecognitionExtra> CREATOR = new Parcelable.Creator<KeyphraseRecognitionExtra>() { // from class: android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionExtra.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public KeyphraseRecognitionExtra createFromParcel(Parcel in) {
                return KeyphraseRecognitionExtra.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public KeyphraseRecognitionExtra[] newArray(int size) {
                return new KeyphraseRecognitionExtra[size];
            }
        };
        @UnsupportedAppUsage
        public final int coarseConfidenceLevel;
        @UnsupportedAppUsage
        public final ConfidenceLevel[] confidenceLevels;
        @UnsupportedAppUsage

        /* renamed from: id */
        public final int f106id;
        @UnsupportedAppUsage
        public final int recognitionModes;

        @UnsupportedAppUsage
        public KeyphraseRecognitionExtra(int id, int recognitionModes, int coarseConfidenceLevel, ConfidenceLevel[] confidenceLevels) {
            this.f106id = id;
            this.recognitionModes = recognitionModes;
            this.coarseConfidenceLevel = coarseConfidenceLevel;
            this.confidenceLevels = confidenceLevels;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static KeyphraseRecognitionExtra fromParcel(Parcel in) {
            int id = in.readInt();
            int recognitionModes = in.readInt();
            int coarseConfidenceLevel = in.readInt();
            ConfidenceLevel[] confidenceLevels = (ConfidenceLevel[]) in.createTypedArray(ConfidenceLevel.CREATOR);
            return new KeyphraseRecognitionExtra(id, recognitionModes, coarseConfidenceLevel, confidenceLevels);
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.f106id);
            dest.writeInt(this.recognitionModes);
            dest.writeInt(this.coarseConfidenceLevel);
            dest.writeTypedArray(this.confidenceLevels, flags);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int hashCode() {
            int result = (1 * 31) + Arrays.hashCode(this.confidenceLevels);
            return (((((result * 31) + this.f106id) * 31) + this.recognitionModes) * 31) + this.coarseConfidenceLevel;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            KeyphraseRecognitionExtra other = (KeyphraseRecognitionExtra) obj;
            if (Arrays.equals(this.confidenceLevels, other.confidenceLevels) && this.f106id == other.f106id && this.recognitionModes == other.recognitionModes && this.coarseConfidenceLevel == other.coarseConfidenceLevel) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "KeyphraseRecognitionExtra [id=" + this.f106id + ", recognitionModes=" + this.recognitionModes + ", coarseConfidenceLevel=" + this.coarseConfidenceLevel + ", confidenceLevels=" + Arrays.toString(this.confidenceLevels) + "]";
        }
    }

    /* loaded from: classes.dex */
    public static class KeyphraseRecognitionEvent extends RecognitionEvent implements Parcelable {
        public static final Parcelable.Creator<KeyphraseRecognitionEvent> CREATOR = new Parcelable.Creator<KeyphraseRecognitionEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public KeyphraseRecognitionEvent createFromParcel(Parcel in) {
                return KeyphraseRecognitionEvent.fromParcelForKeyphrase(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public KeyphraseRecognitionEvent[] newArray(int size) {
                return new KeyphraseRecognitionEvent[size];
            }
        };
        @UnsupportedAppUsage
        public final KeyphraseRecognitionExtra[] keyphraseExtras;

        @UnsupportedAppUsage
        public KeyphraseRecognitionEvent(int status, int soundModelHandle, boolean captureAvailable, int captureSession, int captureDelayMs, int capturePreambleMs, boolean triggerInData, AudioFormat captureFormat, byte[] data, KeyphraseRecognitionExtra[] keyphraseExtras) {
            super(status, soundModelHandle, captureAvailable, captureSession, captureDelayMs, capturePreambleMs, triggerInData, captureFormat, data);
            this.keyphraseExtras = keyphraseExtras;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static KeyphraseRecognitionEvent fromParcelForKeyphrase(Parcel in) {
            int status = in.readInt();
            int soundModelHandle = in.readInt();
            boolean captureAvailable = in.readByte() == 1;
            int captureSession = in.readInt();
            int captureDelayMs = in.readInt();
            int capturePreambleMs = in.readInt();
            boolean triggerInData = in.readByte() == 1;
            AudioFormat captureFormat = null;
            if (in.readByte() == 1) {
                int sampleRate = in.readInt();
                int encoding = in.readInt();
                int channelMask = in.readInt();
                captureFormat = new AudioFormat.Builder().setChannelMask(channelMask).setEncoding(encoding).setSampleRate(sampleRate).build();
            }
            AudioFormat captureFormat2 = captureFormat;
            byte[] data = in.readBlob();
            KeyphraseRecognitionExtra[] keyphraseExtras = (KeyphraseRecognitionExtra[]) in.createTypedArray(KeyphraseRecognitionExtra.CREATOR);
            return new KeyphraseRecognitionEvent(status, soundModelHandle, captureAvailable, captureSession, captureDelayMs, capturePreambleMs, triggerInData, captureFormat2, data, keyphraseExtras);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent, android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeInt(this.soundModelHandle);
            dest.writeByte(this.captureAvailable ? (byte) 1 : (byte) 0);
            dest.writeInt(this.captureSession);
            dest.writeInt(this.captureDelayMs);
            dest.writeInt(this.capturePreambleMs);
            dest.writeByte(this.triggerInData ? (byte) 1 : (byte) 0);
            if (this.captureFormat != null) {
                dest.writeByte((byte) 1);
                dest.writeInt(this.captureFormat.getSampleRate());
                dest.writeInt(this.captureFormat.getEncoding());
                dest.writeInt(this.captureFormat.getChannelMask());
            } else {
                dest.writeByte((byte) 0);
            }
            dest.writeBlob(this.data);
            dest.writeTypedArray(this.keyphraseExtras, flags);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent, android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public int hashCode() {
            int result = super.hashCode();
            return (result * 31) + Arrays.hashCode(this.keyphraseExtras);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (super.equals(obj) && getClass() == obj.getClass()) {
                KeyphraseRecognitionEvent other = (KeyphraseRecognitionEvent) obj;
                return Arrays.equals(this.keyphraseExtras, other.keyphraseExtras);
            }
            return false;
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public String toString() {
            String str;
            String str2;
            String str3;
            StringBuilder sb = new StringBuilder();
            sb.append("KeyphraseRecognitionEvent [keyphraseExtras=");
            sb.append(Arrays.toString(this.keyphraseExtras));
            sb.append(", status=");
            sb.append(this.status);
            sb.append(", soundModelHandle=");
            sb.append(this.soundModelHandle);
            sb.append(", captureAvailable=");
            sb.append(this.captureAvailable);
            sb.append(", captureSession=");
            sb.append(this.captureSession);
            sb.append(", captureDelayMs=");
            sb.append(this.captureDelayMs);
            sb.append(", capturePreambleMs=");
            sb.append(this.capturePreambleMs);
            sb.append(", triggerInData=");
            sb.append(this.triggerInData);
            if (this.captureFormat == null) {
                str = "";
            } else {
                str = ", sampleRate=" + this.captureFormat.getSampleRate();
            }
            sb.append(str);
            if (this.captureFormat == null) {
                str2 = "";
            } else {
                str2 = ", encoding=" + this.captureFormat.getEncoding();
            }
            sb.append(str2);
            if (this.captureFormat == null) {
                str3 = "";
            } else {
                str3 = ", channelMask=" + this.captureFormat.getChannelMask();
            }
            sb.append(str3);
            sb.append(", data=");
            sb.append(this.data == null ? 0 : this.data.length);
            sb.append("]");
            return sb.toString();
        }
    }

    /* loaded from: classes.dex */
    public static class GenericRecognitionEvent extends RecognitionEvent implements Parcelable {
        public static final Parcelable.Creator<GenericRecognitionEvent> CREATOR = new Parcelable.Creator<GenericRecognitionEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.GenericRecognitionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public GenericRecognitionEvent createFromParcel(Parcel in) {
                return GenericRecognitionEvent.fromParcelForGeneric(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public GenericRecognitionEvent[] newArray(int size) {
                return new GenericRecognitionEvent[size];
            }
        };

        @UnsupportedAppUsage
        public GenericRecognitionEvent(int status, int soundModelHandle, boolean captureAvailable, int captureSession, int captureDelayMs, int capturePreambleMs, boolean triggerInData, AudioFormat captureFormat, byte[] data) {
            super(status, soundModelHandle, captureAvailable, captureSession, captureDelayMs, capturePreambleMs, triggerInData, captureFormat, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static GenericRecognitionEvent fromParcelForGeneric(Parcel in) {
            RecognitionEvent event = RecognitionEvent.fromParcel(in);
            return new GenericRecognitionEvent(event.status, event.soundModelHandle, event.captureAvailable, event.captureSession, event.captureDelayMs, event.capturePreambleMs, event.triggerInData, event.captureFormat, event.data);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return super.equals(obj);
        }

        @Override // android.hardware.soundtrigger.SoundTrigger.RecognitionEvent
        public String toString() {
            return "GenericRecognitionEvent ::" + super.toString();
        }
    }

    /* loaded from: classes.dex */
    public static class SoundModelEvent implements Parcelable {
        public static final Parcelable.Creator<SoundModelEvent> CREATOR = new Parcelable.Creator<SoundModelEvent>() { // from class: android.hardware.soundtrigger.SoundTrigger.SoundModelEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SoundModelEvent createFromParcel(Parcel in) {
                return SoundModelEvent.fromParcel(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.Creator
            public SoundModelEvent[] newArray(int size) {
                return new SoundModelEvent[size];
            }
        };
        public final byte[] data;
        public final int soundModelHandle;
        public final int status;

        @UnsupportedAppUsage
        SoundModelEvent(int status, int soundModelHandle, byte[] data) {
            this.status = status;
            this.soundModelHandle = soundModelHandle;
            this.data = data;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static SoundModelEvent fromParcel(Parcel in) {
            int status = in.readInt();
            int soundModelHandle = in.readInt();
            byte[] data = in.readBlob();
            return new SoundModelEvent(status, soundModelHandle, data);
        }

        @Override // android.p007os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.p007os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.status);
            dest.writeInt(this.soundModelHandle);
            dest.writeBlob(this.data);
        }

        public int hashCode() {
            int result = (1 * 31) + Arrays.hashCode(this.data);
            return (((result * 31) + this.soundModelHandle) * 31) + this.status;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SoundModelEvent other = (SoundModelEvent) obj;
            if (Arrays.equals(this.data, other.data) && this.soundModelHandle == other.soundModelHandle && this.status == other.status) {
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("SoundModelEvent [status=");
            sb.append(this.status);
            sb.append(", soundModelHandle=");
            sb.append(this.soundModelHandle);
            sb.append(", data=");
            sb.append(this.data == null ? 0 : this.data.length);
            sb.append("]");
            return sb.toString();
        }
    }

    static String getCurrentOpPackageName() {
        String packageName = ActivityThread.currentOpPackageName();
        if (packageName == null) {
            return "";
        }
        return packageName;
    }

    @UnsupportedAppUsage
    public static int listModules(ArrayList<ModuleProperties> modules) {
        return listModules(getCurrentOpPackageName(), modules);
    }

    @UnsupportedAppUsage
    public static SoundTriggerModule attachModule(int moduleId, StatusListener listener, Handler handler) {
        if (listener == null) {
            return null;
        }
        SoundTriggerModule module = new SoundTriggerModule(moduleId, listener, handler);
        return module;
    }
}
