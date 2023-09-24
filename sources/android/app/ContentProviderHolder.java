package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProviderNative;
import android.content.IContentProvider;
import android.content.p002pm.ProviderInfo;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes.dex */
public class ContentProviderHolder implements Parcelable {
    public static final Parcelable.Creator<ContentProviderHolder> CREATOR = new Parcelable.Creator<ContentProviderHolder>() { // from class: android.app.ContentProviderHolder.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ContentProviderHolder createFromParcel(Parcel source) {
            return new ContentProviderHolder(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ContentProviderHolder[] newArray(int size) {
            return new ContentProviderHolder[size];
        }
    };
    public IBinder connection;
    @UnsupportedAppUsage
    public final ProviderInfo info;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public boolean noReleaseNeeded;
    @UnsupportedAppUsage
    public IContentProvider provider;

    @UnsupportedAppUsage
    public ContentProviderHolder(ProviderInfo _info) {
        this.info = _info;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.info.writeToParcel(dest, 0);
        if (this.provider != null) {
            dest.writeStrongBinder(this.provider.asBinder());
        } else {
            dest.writeStrongBinder(null);
        }
        dest.writeStrongBinder(this.connection);
        dest.writeInt(this.noReleaseNeeded ? 1 : 0);
    }

    @UnsupportedAppUsage
    private ContentProviderHolder(Parcel source) {
        this.info = ProviderInfo.CREATOR.createFromParcel(source);
        this.provider = ContentProviderNative.asInterface(source.readStrongBinder());
        this.connection = source.readStrongBinder();
        this.noReleaseNeeded = source.readInt() != 0;
    }
}
