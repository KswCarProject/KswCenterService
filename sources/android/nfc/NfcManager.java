package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;

public final class NfcManager {
    private final NfcAdapter mAdapter;

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public NfcManager(Context context) {
        NfcAdapter adapter;
        Context context2 = context.getApplicationContext();
        if (context2 != null) {
            try {
                adapter = NfcAdapter.getNfcAdapter(context2);
            } catch (UnsupportedOperationException e) {
                adapter = null;
            }
            this.mAdapter = adapter;
            return;
        }
        throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
    }

    public NfcAdapter getDefaultAdapter() {
        return this.mAdapter;
    }
}
