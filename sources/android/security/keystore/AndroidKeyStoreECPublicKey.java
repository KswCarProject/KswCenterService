package android.security.keystore;

import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

/* loaded from: classes3.dex */
public class AndroidKeyStoreECPublicKey extends AndroidKeyStorePublicKey implements ECPublicKey {
    private final ECParameterSpec mParams;

    /* renamed from: mW */
    private final ECPoint f235mW;

    public AndroidKeyStoreECPublicKey(String alias, int uid, byte[] x509EncodedForm, ECParameterSpec params, ECPoint w) {
        super(alias, uid, KeyProperties.KEY_ALGORITHM_EC, x509EncodedForm);
        this.mParams = params;
        this.f235mW = w;
    }

    public AndroidKeyStoreECPublicKey(String alias, int uid, ECPublicKey info) {
        this(alias, uid, info.getEncoded(), info.getParams(), info.getW());
        if (!"X.509".equalsIgnoreCase(info.getFormat())) {
            throw new IllegalArgumentException("Unsupported key export format: " + info.getFormat());
        }
    }

    @Override // java.security.interfaces.ECKey
    public ECParameterSpec getParams() {
        return this.mParams;
    }

    @Override // java.security.interfaces.ECPublicKey
    public ECPoint getW() {
        return this.f235mW;
    }
}
