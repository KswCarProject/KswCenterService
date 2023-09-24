package com.android.internal.widget;

import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.service.gatekeeper.GateKeeperResponse;
import android.util.Slog;

/* loaded from: classes4.dex */
public final class VerifyCredentialResponse implements Parcelable {
    public static final int RESPONSE_ERROR = -1;
    public static final int RESPONSE_OK = 0;
    public static final int RESPONSE_RETRY = 1;
    private static final String TAG = "VerifyCredentialResponse";
    private byte[] mPayload;
    private int mResponseCode;
    private int mTimeout;

    /* renamed from: OK */
    public static final VerifyCredentialResponse f2498OK = new VerifyCredentialResponse();
    public static final VerifyCredentialResponse ERROR = new VerifyCredentialResponse(-1, 0, null);
    public static final Parcelable.Creator<VerifyCredentialResponse> CREATOR = new Parcelable.Creator<VerifyCredentialResponse>() { // from class: com.android.internal.widget.VerifyCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public VerifyCredentialResponse createFromParcel(Parcel source) {
            int size;
            int responseCode = source.readInt();
            VerifyCredentialResponse response = new VerifyCredentialResponse(responseCode, 0, null);
            if (responseCode == 1) {
                response.setTimeout(source.readInt());
            } else if (responseCode == 0 && (size = source.readInt()) > 0) {
                byte[] payload = new byte[size];
                source.readByteArray(payload);
                response.setPayload(payload);
            }
            return response;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.p007os.Parcelable.Creator
        public VerifyCredentialResponse[] newArray(int size) {
            return new VerifyCredentialResponse[size];
        }
    };

    public VerifyCredentialResponse() {
        this.mResponseCode = 0;
        this.mPayload = null;
    }

    public VerifyCredentialResponse(byte[] payload) {
        this.mPayload = payload;
        this.mResponseCode = 0;
    }

    public VerifyCredentialResponse(int timeout) {
        this.mTimeout = timeout;
        this.mResponseCode = 1;
        this.mPayload = null;
    }

    private VerifyCredentialResponse(int responseCode, int timeout, byte[] payload) {
        this.mResponseCode = responseCode;
        this.mTimeout = timeout;
        this.mPayload = payload;
    }

    @Override // android.p007os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mResponseCode);
        if (this.mResponseCode == 1) {
            dest.writeInt(this.mTimeout);
        } else if (this.mResponseCode == 0) {
            if (this.mPayload != null) {
                dest.writeInt(this.mPayload.length);
                dest.writeByteArray(this.mPayload);
                return;
            }
            dest.writeInt(0);
        }
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte[] getPayload() {
        return this.mPayload;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTimeout(int timeout) {
        this.mTimeout = timeout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPayload(byte[] payload) {
        this.mPayload = payload;
    }

    public VerifyCredentialResponse stripPayload() {
        return new VerifyCredentialResponse(this.mResponseCode, this.mTimeout, new byte[0]);
    }

    public static VerifyCredentialResponse fromGateKeeperResponse(GateKeeperResponse gateKeeperResponse) {
        VerifyCredentialResponse response;
        int responseCode = gateKeeperResponse.getResponseCode();
        if (responseCode == 1) {
            VerifyCredentialResponse response2 = new VerifyCredentialResponse(gateKeeperResponse.getTimeout());
            return response2;
        } else if (responseCode == 0) {
            byte[] token = gateKeeperResponse.getPayload();
            if (token == null) {
                Slog.m56e(TAG, "verifyChallenge response had no associated payload");
                response = ERROR;
            } else {
                response = new VerifyCredentialResponse(token);
            }
            return response;
        } else {
            VerifyCredentialResponse response3 = ERROR;
            return response3;
        }
    }
}
