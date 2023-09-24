package android.support.p011v4.media.session;

import android.p007os.Parcel;
import android.p007os.Parcelable;

/* renamed from: android.support.v4.media.session.ParcelableVolumeInfo */
/* loaded from: classes3.dex */
public class ParcelableVolumeInfo implements Parcelable {
    public static final Parcelable.Creator<ParcelableVolumeInfo> CREATOR = new Parcelable.Creator<ParcelableVolumeInfo>() { // from class: android.support.v4.media.session.ParcelableVolumeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ParcelableVolumeInfo createFromParcel(Parcel in) {
            return new ParcelableVolumeInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ParcelableVolumeInfo[] newArray(int size) {
            return new ParcelableVolumeInfo[size];
        }
    };
    public int audioStream;
    public int controlType;
    public int currentVolume;
    public int maxVolume;
    public int volumeType;

    public ParcelableVolumeInfo(int volumeType, int audioStream, int controlType, int maxVolume, int currentVolume) {
        this.volumeType = volumeType;
        this.audioStream = audioStream;
        this.controlType = controlType;
        this.maxVolume = maxVolume;
        this.currentVolume = currentVolume;
    }

    public ParcelableVolumeInfo(Parcel from) {
        this.volumeType = from.readInt();
        this.controlType = from.readInt();
        this.maxVolume = from.readInt();
        this.currentVolume = from.readInt();
        this.audioStream = from.readInt();
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.volumeType);
        dest.writeInt(this.controlType);
        dest.writeInt(this.maxVolume);
        dest.writeInt(this.currentVolume);
        dest.writeInt(this.audioStream);
    }
}
