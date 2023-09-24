package android.accounts;

import android.accounts.IAccountAuthenticatorResponse;
import android.annotation.UnsupportedAppUsage;
import android.p007os.Bundle;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import android.util.Log;

/* loaded from: classes.dex */
public class AccountAuthenticatorResponse implements Parcelable {
    public static final Parcelable.Creator<AccountAuthenticatorResponse> CREATOR = new Parcelable.Creator<AccountAuthenticatorResponse>() { // from class: android.accounts.AccountAuthenticatorResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public AccountAuthenticatorResponse createFromParcel(Parcel source) {
            return new AccountAuthenticatorResponse(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public AccountAuthenticatorResponse[] newArray(int size) {
            return new AccountAuthenticatorResponse[size];
        }
    };
    private static final String TAG = "AccountAuthenticator";
    private IAccountAuthenticatorResponse mAccountAuthenticatorResponse;

    @UnsupportedAppUsage
    public AccountAuthenticatorResponse(IAccountAuthenticatorResponse response) {
        this.mAccountAuthenticatorResponse = response;
    }

    public AccountAuthenticatorResponse(Parcel parcel) {
        this.mAccountAuthenticatorResponse = IAccountAuthenticatorResponse.Stub.asInterface(parcel.readStrongBinder());
    }

    public void onResult(Bundle result) {
        if (Log.isLoggable(TAG, 2)) {
            result.keySet();
            Log.m66v(TAG, "AccountAuthenticatorResponse.onResult: " + AccountManager.sanitizeResult(result));
        }
        try {
            this.mAccountAuthenticatorResponse.onResult(result);
        } catch (RemoteException e) {
        }
    }

    public void onRequestContinued() {
        if (Log.isLoggable(TAG, 2)) {
            Log.m66v(TAG, "AccountAuthenticatorResponse.onRequestContinued");
        }
        try {
            this.mAccountAuthenticatorResponse.onRequestContinued();
        } catch (RemoteException e) {
        }
    }

    public void onError(int errorCode, String errorMessage) {
        if (Log.isLoggable(TAG, 2)) {
            Log.m66v(TAG, "AccountAuthenticatorResponse.onError: " + errorCode + ", " + errorMessage);
        }
        try {
            this.mAccountAuthenticatorResponse.onError(errorCode, errorMessage);
        } catch (RemoteException e) {
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mAccountAuthenticatorResponse.asBinder());
    }
}
