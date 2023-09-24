package android.security;

import android.annotation.UnsupportedAppUsage;
import com.android.org.bouncycastle.util.io.pem.PemObject;
import com.android.org.bouncycastle.util.io.pem.PemReader;
import com.android.org.bouncycastle.util.io.pem.PemWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class Credentials {
    public static final String CA_CERTIFICATE = "CACERT_";
    public static final String EXTENSION_CER = ".cer";
    public static final String EXTENSION_CRT = ".crt";
    public static final String EXTENSION_P12 = ".p12";
    public static final String EXTENSION_PFX = ".pfx";
    public static final String EXTRA_CA_CERTIFICATES_DATA = "ca_certificates_data";
    public static final String EXTRA_CA_CERTIFICATES_NAME = "ca_certificates_name";
    public static final String EXTRA_INSTALL_AS_UID = "install_as_uid";
    public static final String EXTRA_PRIVATE_KEY = "PKEY";
    public static final String EXTRA_PUBLIC_KEY = "KEY";
    public static final String EXTRA_USER_CERTIFICATE_DATA = "user_certificate_data";
    public static final String EXTRA_USER_CERTIFICATE_NAME = "user_certificate_name";
    public static final String EXTRA_USER_PRIVATE_KEY_DATA = "user_private_key_data";
    public static final String EXTRA_USER_PRIVATE_KEY_NAME = "user_private_key_name";
    public static final String INSTALL_ACTION = "android.credentials.INSTALL";
    public static final String INSTALL_AS_USER_ACTION = "android.credentials.INSTALL_AS_USER";
    public static final String LOCKDOWN_VPN = "LOCKDOWN_VPN";
    private static final String LOGTAG = "Credentials";
    public static final String USER_CERTIFICATE = "USRCERT_";
    public static final String USER_PRIVATE_KEY = "USRPKEY_";
    public static final String USER_SECRET_KEY = "USRSKEY_";
    public static final String VPN = "VPN_";
    public static final String WIFI = "WIFI_";

    @UnsupportedAppUsage
    public static byte[] convertToPem(Certificate... objects) throws IOException, CertificateEncodingException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(bao, StandardCharsets.US_ASCII);
        PemWriter pw = new PemWriter(writer);
        for (Certificate o : objects) {
            pw.writeObject(new PemObject("CERTIFICATE", o.getEncoded()));
        }
        pw.close();
        return bao.toByteArray();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x005d, code lost:
        throw new java.lang.IllegalArgumentException("Unknown type " + r5.getType());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<X509Certificate> convertFromPem(byte[] bytes) throws IOException, CertificateException {
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        Reader reader = new InputStreamReader(bai, StandardCharsets.US_ASCII);
        PemReader pr = new PemReader(reader);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            List<X509Certificate> result = new ArrayList<>();
            while (true) {
                PemObject o = pr.readPemObject();
                if (o != null) {
                    if (!o.getType().equals("CERTIFICATE")) {
                        break;
                    }
                    Certificate c = cf.generateCertificate(new ByteArrayInputStream(o.getContent()));
                    result.add((X509Certificate) c);
                } else {
                    return result;
                }
            }
        } finally {
            pr.close();
        }
    }

    public static boolean deleteAllTypesForAlias(KeyStore keystore, String alias) {
        return deleteAllTypesForAlias(keystore, alias, -1);
    }

    public static boolean deleteAllTypesForAlias(KeyStore keystore, String alias, int uid) {
        return deleteUserKeyTypeForAlias(keystore, alias, uid) & deleteCertificateTypesForAlias(keystore, alias, uid);
    }

    public static boolean deleteCertificateTypesForAlias(KeyStore keystore, String alias) {
        return deleteCertificateTypesForAlias(keystore, alias, -1);
    }

    public static boolean deleteCertificateTypesForAlias(KeyStore keystore, String alias, int uid) {
        boolean delete = keystore.delete(USER_CERTIFICATE + alias, uid);
        return delete & keystore.delete(CA_CERTIFICATE + alias, uid);
    }

    public static boolean deleteUserKeyTypeForAlias(KeyStore keystore, String alias) {
        return deleteUserKeyTypeForAlias(keystore, alias, -1);
    }

    public static boolean deleteUserKeyTypeForAlias(KeyStore keystore, String alias, int uid) {
        int ret = keystore.delete2(USER_PRIVATE_KEY + alias, uid);
        if (ret != 7) {
            return ret == 1;
        }
        return keystore.delete(USER_SECRET_KEY + alias, uid);
    }

    public static boolean deleteLegacyKeyForAlias(KeyStore keystore, String alias, int uid) {
        return keystore.delete(USER_SECRET_KEY + alias, uid);
    }
}
