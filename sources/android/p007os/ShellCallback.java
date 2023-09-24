package android.p007os;

import android.p007os.Parcelable;
import android.util.Log;
import com.android.internal.p016os.IShellCallback;

/* renamed from: android.os.ShellCallback */
/* loaded from: classes3.dex */
public class ShellCallback implements Parcelable {
    public static final Parcelable.Creator<ShellCallback> CREATOR = new Parcelable.Creator<ShellCallback>() { // from class: android.os.ShellCallback.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ShellCallback createFromParcel(Parcel in) {
            return new ShellCallback(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public ShellCallback[] newArray(int size) {
            return new ShellCallback[size];
        }
    };
    static final boolean DEBUG = false;
    static final String TAG = "ShellCallback";
    final boolean mLocal = true;
    IShellCallback mShellCallback;

    /* renamed from: android.os.ShellCallback$MyShellCallback */
    /* loaded from: classes3.dex */
    class MyShellCallback extends IShellCallback.Stub {
        MyShellCallback() {
        }

        @Override // com.android.internal.p016os.IShellCallback
        public ParcelFileDescriptor openFile(String path, String seLinuxContext, String mode) {
            return ShellCallback.this.onOpenFile(path, seLinuxContext, mode);
        }
    }

    public ShellCallback() {
    }

    public ParcelFileDescriptor openFile(String path, String seLinuxContext, String mode) {
        if (this.mLocal) {
            return onOpenFile(path, seLinuxContext, mode);
        }
        if (this.mShellCallback != null) {
            try {
                return this.mShellCallback.openFile(path, seLinuxContext, mode);
            } catch (RemoteException e) {
                Log.m63w(TAG, "Failure opening " + path, e);
                return null;
            }
        }
        return null;
    }

    public ParcelFileDescriptor onOpenFile(String path, String seLinuxContext, String mode) {
        return null;
    }

    public static void writeToParcel(ShellCallback callback, Parcel out) {
        if (callback == null) {
            out.writeStrongBinder(null);
        } else {
            callback.writeToParcel(out, 0);
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        synchronized (this) {
            if (this.mShellCallback == null) {
                this.mShellCallback = new MyShellCallback();
            }
            out.writeStrongBinder(this.mShellCallback.asBinder());
        }
    }

    ShellCallback(Parcel in) {
        this.mShellCallback = IShellCallback.Stub.asInterface(in.readStrongBinder());
        if (this.mShellCallback != null) {
            Binder.allowBlocking(this.mShellCallback.asBinder());
        }
    }
}
