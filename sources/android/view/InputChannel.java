package android.view;

import android.annotation.UnsupportedAppUsage;
import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;

/* loaded from: classes4.dex */
public final class InputChannel implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<InputChannel> CREATOR = new Parcelable.Creator<InputChannel>() { // from class: android.view.InputChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public InputChannel createFromParcel(Parcel source) {
            InputChannel result = new InputChannel();
            result.readFromParcel(source);
            return result;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public InputChannel[] newArray(int size) {
            return new InputChannel[size];
        }
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "InputChannel";
    @UnsupportedAppUsage
    private long mPtr;

    private native void nativeDispose(boolean z);

    private native void nativeDup(InputChannel inputChannel);

    private native String nativeGetName();

    private native IBinder nativeGetToken();

    private static native InputChannel[] nativeOpenInputChannelPair(String str);

    private native void nativeReadFromParcel(Parcel parcel);

    private native void nativeSetToken(IBinder iBinder);

    private native void nativeTransferTo(InputChannel inputChannel);

    private native void nativeWriteToParcel(Parcel parcel);

    protected void finalize() throws Throwable {
        try {
            nativeDispose(true);
        } finally {
            super.finalize();
        }
    }

    public static InputChannel[] openInputChannelPair(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }
        return nativeOpenInputChannelPair(name);
    }

    public String getName() {
        String name = nativeGetName();
        return name != null ? name : "uninitialized";
    }

    public void dispose() {
        nativeDispose(false);
    }

    public void transferTo(InputChannel outParameter) {
        if (outParameter == null) {
            throw new IllegalArgumentException("outParameter must not be null");
        }
        nativeTransferTo(outParameter);
    }

    public InputChannel dup() {
        InputChannel target = new InputChannel();
        nativeDup(target);
        return target;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 1;
    }

    public void readFromParcel(Parcel in) {
        if (in == null) {
            throw new IllegalArgumentException("in must not be null");
        }
        nativeReadFromParcel(in);
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        if (out == null) {
            throw new IllegalArgumentException("out must not be null");
        }
        nativeWriteToParcel(out);
        if ((flags & 1) != 0) {
            dispose();
        }
    }

    public String toString() {
        return getName();
    }

    public IBinder getToken() {
        return nativeGetToken();
    }

    public void setToken(IBinder token) {
        nativeSetToken(token);
    }
}
