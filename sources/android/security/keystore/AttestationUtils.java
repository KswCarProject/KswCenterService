package android.security.keystore;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Build;
import android.security.KeyStore;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterCertificateChain;
import android.text.TextUtils;
import com.android.internal.R;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;

@SystemApi
public abstract class AttestationUtils {
    public static final int ID_TYPE_IMEI = 2;
    public static final int ID_TYPE_MEID = 3;
    public static final int ID_TYPE_SERIAL = 1;

    private AttestationUtils() {
    }

    public static X509Certificate[] parseCertificateChain(KeymasterCertificateChain kmChain) throws KeyAttestationException {
        Collection<byte[]> rawChain = kmChain.getCertificates();
        if (rawChain.size() >= 2) {
            ByteArrayOutputStream concatenatedRawChain = new ByteArrayOutputStream();
            try {
                for (byte[] cert : rawChain) {
                    concatenatedRawChain.write(cert);
                }
                return (X509Certificate[]) CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(concatenatedRawChain.toByteArray())).toArray(new X509Certificate[0]);
            } catch (Exception e) {
                throw new KeyAttestationException("Unable to construct certificate chain", e);
            }
        } else {
            throw new KeyAttestationException("Attestation certificate chain contained " + rawChain.size() + " entries. At least two are required.");
        }
    }

    private static KeymasterArguments prepareAttestationArgumentsForDeviceId(Context context, int[] idTypes, byte[] attestationChallenge) throws DeviceIdAttestationException {
        if (idTypes != null) {
            return prepareAttestationArguments(context, idTypes, attestationChallenge);
        }
        throw new NullPointerException("Missing id types");
    }

    public static KeymasterArguments prepareAttestationArguments(Context context, int[] idTypes, byte[] attestationChallenge) throws DeviceIdAttestationException {
        return prepareAttestationArguments(context, idTypes, attestationChallenge, Build.BRAND);
    }

    public static KeymasterArguments prepareAttestationArgumentsIfMisprovisioned(Context context, int[] idTypes, byte[] attestationChallenge) throws DeviceIdAttestationException {
        String misprovisionedBrand = context.getResources().getString(R.string.config_misprovisionedBrandValue);
        if (TextUtils.isEmpty(misprovisionedBrand) || isPotentiallyMisprovisionedDevice(context)) {
            return prepareAttestationArguments(context, idTypes, attestationChallenge, misprovisionedBrand);
        }
        return null;
    }

    private static boolean isPotentiallyMisprovisionedDevice(Context context) {
        return Build.MODEL.equals(context.getResources().getString(R.string.config_misprovisionedDeviceModel));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: android.telephony.TelephonyManager} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.security.keymaster.KeymasterArguments prepareAttestationArguments(android.content.Context r9, int[] r10, byte[] r11, java.lang.String r12) throws android.security.keystore.DeviceIdAttestationException {
        /*
            if (r11 == 0) goto L_0x010d
            android.security.keymaster.KeymasterArguments r0 = new android.security.keymaster.KeymasterArguments
            r0.<init>()
            r1 = -1879047484(0xffffffff900002c4, float:-2.524568E-29)
            r0.addBytes(r1, r11)
            if (r10 != 0) goto L_0x0010
            return r0
        L_0x0010:
            android.util.ArraySet r1 = new android.util.ArraySet
            int r2 = r10.length
            r1.<init>((int) r2)
            int r2 = r10.length
            r3 = 0
            r4 = r3
        L_0x0019:
            if (r4 >= r2) goto L_0x0027
            r5 = r10[r4]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            r1.add(r6)
            int r4 = r4 + 1
            goto L_0x0019
        L_0x0027:
            r2 = 0
            r4 = 2
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            boolean r4 = r1.contains(r4)
            if (r4 != 0) goto L_0x003e
            r4 = 3
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            boolean r4 = r1.contains(r4)
            if (r4 == 0) goto L_0x004a
        L_0x003e:
            java.lang.String r4 = "phone"
            java.lang.Object r4 = r9.getSystemService((java.lang.String) r4)
            r2 = r4
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2
            if (r2 == 0) goto L_0x0105
        L_0x004a:
            java.util.Iterator r4 = r1.iterator()
        L_0x004e:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00c0
            java.lang.Object r5 = r4.next()
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r6 = r5.intValue()
            switch(r6) {
                case 1: goto L_0x00ae;
                case 2: goto L_0x0093;
                case 3: goto L_0x0078;
                default: goto L_0x0061;
            }
        L_0x0061:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Unknown device ID type "
            r4.append(r6)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0078:
            java.lang.String r6 = r2.getMeid(r3)
            if (r6 == 0) goto L_0x008b
            r7 = -1879047477(0xffffffff900002cb, float:-2.52457E-29)
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r8 = r6.getBytes(r8)
            r0.addBytes(r7, r8)
            goto L_0x00bf
        L_0x008b:
            android.security.keystore.DeviceIdAttestationException r3 = new android.security.keystore.DeviceIdAttestationException
            java.lang.String r4 = "Unable to retrieve MEID"
            r3.<init>(r4)
            throw r3
        L_0x0093:
            java.lang.String r6 = r2.getImei(r3)
            if (r6 == 0) goto L_0x00a6
            r7 = -1879047478(0xffffffff900002ca, float:-2.5245698E-29)
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r8 = r6.getBytes(r8)
            r0.addBytes(r7, r8)
            goto L_0x00bf
        L_0x00a6:
            android.security.keystore.DeviceIdAttestationException r3 = new android.security.keystore.DeviceIdAttestationException
            java.lang.String r4 = "Unable to retrieve IMEI"
            r3.<init>(r4)
            throw r3
        L_0x00ae:
            r6 = -1879047479(0xffffffff900002c9, float:-2.5245695E-29)
            java.lang.String r7 = android.os.Build.getSerial()
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r7 = r7.getBytes(r8)
            r0.addBytes(r6, r7)
        L_0x00bf:
            goto L_0x004e
        L_0x00c0:
            r3 = -1879047482(0xffffffff900002c6, float:-2.5245686E-29)
            java.nio.charset.Charset r4 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r12.getBytes(r4)
            r0.addBytes(r3, r4)
            r3 = -1879047481(0xffffffff900002c7, float:-2.524569E-29)
            java.lang.String r4 = android.os.Build.DEVICE
            java.nio.charset.Charset r5 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r4.getBytes(r5)
            r0.addBytes(r3, r4)
            r3 = -1879047480(0xffffffff900002c8, float:-2.5245692E-29)
            java.lang.String r4 = android.os.Build.PRODUCT
            java.nio.charset.Charset r5 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r4.getBytes(r5)
            r0.addBytes(r3, r4)
            r3 = -1879047476(0xffffffff900002cc, float:-2.5245704E-29)
            java.lang.String r4 = android.os.Build.MANUFACTURER
            java.nio.charset.Charset r5 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r4.getBytes(r5)
            r0.addBytes(r3, r4)
            r3 = -1879047475(0xffffffff900002cd, float:-2.5245707E-29)
            java.lang.String r4 = android.os.Build.MODEL
            java.nio.charset.Charset r5 = java.nio.charset.StandardCharsets.UTF_8
            byte[] r4 = r4.getBytes(r5)
            r0.addBytes(r3, r4)
            return r0
        L_0x0105:
            android.security.keystore.DeviceIdAttestationException r3 = new android.security.keystore.DeviceIdAttestationException
            java.lang.String r4 = "Unable to access telephony service"
            r3.<init>(r4)
            throw r3
        L_0x010d:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "Missing attestation challenge"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.security.keystore.AttestationUtils.prepareAttestationArguments(android.content.Context, int[], byte[], java.lang.String):android.security.keymaster.KeymasterArguments");
    }

    public static X509Certificate[] attestDeviceIds(Context context, int[] idTypes, byte[] attestationChallenge) throws DeviceIdAttestationException {
        KeymasterArguments attestArgs = prepareAttestationArgumentsForDeviceId(context, idTypes, attestationChallenge);
        KeymasterCertificateChain outChain = new KeymasterCertificateChain();
        int errorCode = KeyStore.getInstance().attestDeviceIds(attestArgs, outChain);
        if (errorCode == 1) {
            try {
                return parseCertificateChain(outChain);
            } catch (KeyAttestationException e) {
                throw new DeviceIdAttestationException(e.getMessage(), e);
            }
        } else {
            throw new DeviceIdAttestationException("Unable to perform attestation", KeyStore.getKeyStoreException(errorCode));
        }
    }

    public static boolean isChainValid(KeymasterCertificateChain chain) {
        return chain != null && chain.getCertificates().size() >= 2;
    }
}
