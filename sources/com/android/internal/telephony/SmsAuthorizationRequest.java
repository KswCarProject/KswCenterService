package com.android.internal.telephony;

import android.p007os.IBinder;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import com.android.internal.telephony.ISmsSecurityService;

/* loaded from: classes4.dex */
public class SmsAuthorizationRequest implements Parcelable {
    public static Parcelable.Creator<SmsAuthorizationRequest> CREATOR = new Parcelable.Creator<SmsAuthorizationRequest>() { // from class: com.android.internal.telephony.SmsAuthorizationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public SmsAuthorizationRequest[] newArray(int size) {
            return new SmsAuthorizationRequest[size];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public SmsAuthorizationRequest createFromParcel(Parcel source) {
            return new SmsAuthorizationRequest(source);
        }
    };
    public final String destinationAddress;
    public final String message;
    public final String packageName;
    private final ISmsSecurityService service;
    private final IBinder token;

    public SmsAuthorizationRequest(Parcel source) {
        this.service = ISmsSecurityService.Stub.asInterface(source.readStrongBinder());
        this.token = source.readStrongBinder();
        this.packageName = source.readString();
        this.destinationAddress = source.readString();
        this.message = source.readString();
    }

    public SmsAuthorizationRequest(ISmsSecurityService service, IBinder binderToken, String packageName, String destinationAddress, String message) {
        this.service = service;
        this.token = binderToken;
        this.packageName = packageName;
        this.destinationAddress = destinationAddress;
        this.message = message;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.service.asBinder());
        dest.writeStrongBinder(this.token);
        dest.writeString(this.packageName);
        dest.writeString(this.destinationAddress);
        dest.writeString(this.message);
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void accept() throws RemoteException {
        this.service.sendResponse(this, true);
    }

    public void reject() throws RemoteException {
        this.service.sendResponse(this, false);
    }

    public IBinder getToken() {
        return this.token;
    }

    public String toString() {
        return String.format("[%s] (%s) # %s", this.packageName, this.destinationAddress, this.message);
    }
}
