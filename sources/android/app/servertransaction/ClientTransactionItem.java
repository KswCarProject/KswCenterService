package android.app.servertransaction;

import android.p007os.Parcelable;

/* loaded from: classes.dex */
public abstract class ClientTransactionItem implements BaseClientRequest, Parcelable {
    public int getPostExecutionState() {
        return -1;
    }

    @Override // android.p007os.Parcelable
    public int describeContents() {
        return 0;
    }
}
