package android.security.keystore;

import java.security.KeyStore;

/* loaded from: classes3.dex */
class AndroidKeyStoreLoadStoreParameter implements KeyStore.LoadStoreParameter {
    private final int mUid;

    AndroidKeyStoreLoadStoreParameter(int uid) {
        this.mUid = uid;
    }

    @Override // java.security.KeyStore.LoadStoreParameter
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }

    int getUid() {
        return this.mUid;
    }
}
