package android.os;

import android.Manifest;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.IRecoverySystemProgressListener;
import android.os.IVold;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.WindowManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import libcore.io.Streams;

public class RecoverySystem {
    private static final String ACTION_EUICC_FACTORY_RESET = "com.android.internal.action.EUICC_FACTORY_RESET";
    public static final File BLOCK_MAP_FILE = new File(RECOVERY_DIR, "block.map");
    private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000;
    private static final File DEFAULT_KEYSTORE = new File("/system/etc/security/otacerts.zip");
    private static final String LAST_INSTALL_PATH = "last_install";
    private static final String LAST_PREFIX = "last_";
    private static final File LOG_FILE = new File(RECOVERY_DIR, "log");
    private static final int LOG_FILE_MAX_LENGTH = 65536;
    private static final long MAX_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 60000;
    private static final long MIN_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 5000;
    private static final String PACKAGE_NAME_WIPING_EUICC_DATA_CALLBACK = "android";
    private static final long PUBLISH_PROGRESS_INTERVAL_MS = 500;
    private static final File RECOVERY_DIR = new File("/cache/recovery");
    private static final String TAG = "RecoverySystem";
    public static final File UNCRYPT_PACKAGE_FILE = new File(RECOVERY_DIR, "uncrypt_file");
    public static final File UNCRYPT_STATUS_FILE = new File(RECOVERY_DIR, "uncrypt_status");
    private static final Object sRequestLock = new Object();
    private final IRecoverySystem mService;

    public interface ProgressListener {
        void onProgress(int i);
    }

    private static HashSet<X509Certificate> getTrustedCerts(File keystore) throws IOException, GeneralSecurityException {
        InputStream is;
        HashSet<X509Certificate> trusted = new HashSet<>();
        if (keystore == null) {
            keystore = DEFAULT_KEYSTORE;
        }
        ZipFile zip = new ZipFile(keystore);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                is = zip.getInputStream((ZipEntry) entries.nextElement());
                trusted.add((X509Certificate) cf.generateCertificate(is));
                is.close();
            }
            zip.close();
            return trusted;
        } catch (Throwable th) {
            zip.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a1, code lost:
        r9 = new sun.security.pkcs.PKCS7(new java.io.ByteArrayInputStream(r4, (r15 + 22) - r7, r7));
        r2 = r9.getCertificates();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b4, code lost:
        if (r2 == null) goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b7, code lost:
        if (r2.length == 0) goto L_0x0186;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b9, code lost:
        r3 = r2[0].getPublicKey();
        r1 = r9.getSignerInfos();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c6, code lost:
        if (r1 == null) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c9, code lost:
        if (r1.length == 0) goto L_0x0171;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00cb, code lost:
        r0 = r1[0];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ce, code lost:
        if (r27 != null) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00d0, code lost:
        r18 = DEFAULT_KEYSTORE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00d3, code lost:
        r18 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d5, code lost:
        r19 = getTrustedCerts(r18).iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e1, code lost:
        if (r19.hasNext() == false) goto L_0x00fe;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e3, code lost:
        r22 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f3, code lost:
        if (r19.next().getPublicKey().equals(r3) == false) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f5, code lost:
        r19 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00fa, code lost:
        r1 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00fe, code lost:
        r22 = r1;
        r19 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0102, code lost:
        if (r19 == false) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0104, code lost:
        r13.seek(0);
        r8 = r26;
        r20 = r22;
        r12 = r1;
        r22 = r3;
        r21 = r2;
        r2 = r10;
        r23 = r4;
        r4 = r15;
        r24 = r7;
        r7 = r13;
        r1 = new android.os.RecoverySystem.AnonymousClass1();
        r1 = r9.verify(r0, r12);
        r2 = java.lang.Thread.interrupted();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0129, code lost:
        r3 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x012b, code lost:
        if (r3 == null) goto L_0x0132;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r3.onProgress(100);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0132, code lost:
        if (r2 != false) goto L_0x0153;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0134, code lost:
        if (r1 == null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0136, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x013e, code lost:
        if (readAndVerifyPackageCompatibilityEntry(r25) == false) goto L_0x0141;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0140, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0149, code lost:
        throw new java.security.SignatureException("package compatibility verification failed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0152, code lost:
        throw new java.security.SignatureException("signature digest verification failed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x015b, code lost:
        throw new java.security.SignatureException("verification was interrupted");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x015c, code lost:
        r21 = r2;
        r23 = r4;
        r24 = r7;
        r20 = r22;
        r22 = r3;
        r3 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0170, code lost:
        throw new java.security.SignatureException("signature doesn't match any trusted key");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0171, code lost:
        r20 = r1;
        r21 = r2;
        r22 = r3;
        r23 = r4;
        r24 = r7;
        r3 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0185, code lost:
        throw new java.security.SignatureException("signature contains no signedData");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0186, code lost:
        r21 = r2;
        r23 = r4;
        r24 = r7;
        r3 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0196, code lost:
        throw new java.security.SignatureException("signature contains no certificates");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void verifyPackage(java.io.File r25, android.os.RecoverySystem.ProgressListener r26, java.io.File r27) throws java.io.IOException, java.security.GeneralSecurityException {
        /*
            r9 = r26
            long r10 = r25.length()
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile
            java.lang.String r1 = "r"
            r12 = r25
            r0.<init>(r12, r1)
            r13 = r0
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01b6 }
            r0 = 0
            if (r9 == 0) goto L_0x001b
            r9.onProgress(r0)     // Catch:{ all -> 0x01b6 }
        L_0x001b:
            r1 = 6
            long r1 = r10 - r1
            r13.seek(r1)     // Catch:{ all -> 0x01b6 }
            r1 = 6
            byte[] r2 = new byte[r1]     // Catch:{ all -> 0x01b6 }
            r14 = r2
            r13.readFully(r14)     // Catch:{ all -> 0x01b6 }
            r2 = 2
            byte r3 = r14[r2]     // Catch:{ all -> 0x01b6 }
            r4 = -1
            if (r3 != r4) goto L_0x01aa
            r3 = 3
            byte r7 = r14[r3]     // Catch:{ all -> 0x01b6 }
            if (r7 != r4) goto L_0x01aa
            r4 = 4
            byte r7 = r14[r4]     // Catch:{ all -> 0x01b6 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            r8 = 5
            byte r15 = r14[r8]     // Catch:{ all -> 0x01b6 }
            r15 = r15 & 255(0xff, float:3.57E-43)
            int r15 = r15 << 8
            r15 = r15 | r7
            byte r7 = r14[r0]     // Catch:{ all -> 0x01b6 }
            r7 = r7 & 255(0xff, float:3.57E-43)
            r16 = 1
            byte r4 = r14[r16]     // Catch:{ all -> 0x01b6 }
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 8
            r7 = r7 | r4
            int r4 = r15 + 22
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x01b6 }
            int r1 = r15 + 22
            long r8 = (long) r1
            long r8 = r10 - r8
            r13.seek(r8)     // Catch:{ all -> 0x01a6 }
            r13.readFully(r4)     // Catch:{ all -> 0x01a6 }
            byte r1 = r4[r0]     // Catch:{ all -> 0x01a6 }
            r8 = 80
            if (r1 != r8) goto L_0x0197
            byte r1 = r4[r16]     // Catch:{ all -> 0x01a6 }
            r9 = 75
            if (r1 != r9) goto L_0x0197
            byte r1 = r4[r2]     // Catch:{ all -> 0x01a6 }
            r2 = 5
            if (r1 != r2) goto L_0x0197
            byte r1 = r4[r3]     // Catch:{ all -> 0x01a6 }
            r2 = 6
            if (r1 != r2) goto L_0x0197
            r17 = 4
        L_0x0075:
            r1 = r17
            int r2 = r4.length     // Catch:{ all -> 0x01a6 }
            int r2 = r2 - r3
            if (r1 >= r2) goto L_0x00a1
            byte r2 = r4[r1]     // Catch:{ all -> 0x01a6 }
            if (r2 != r8) goto L_0x009c
            int r2 = r1 + 1
            byte r2 = r4[r2]     // Catch:{ all -> 0x01a6 }
            if (r2 != r9) goto L_0x009c
            int r2 = r1 + 2
            byte r2 = r4[r2]     // Catch:{ all -> 0x01a6 }
            r3 = 5
            if (r2 != r3) goto L_0x009c
            int r2 = r1 + 3
            byte r2 = r4[r2]     // Catch:{ all -> 0x01a6 }
            r3 = 6
            if (r2 == r3) goto L_0x0094
            goto L_0x009d
        L_0x0094:
            java.security.SignatureException r0 = new java.security.SignatureException     // Catch:{ all -> 0x01a6 }
            java.lang.String r2 = "EOCD marker found after start of EOCD"
            r0.<init>(r2)     // Catch:{ all -> 0x01a6 }
            throw r0     // Catch:{ all -> 0x01a6 }
        L_0x009c:
            r3 = 6
        L_0x009d:
            int r17 = r1 + 1
            r3 = 3
            goto L_0x0075
        L_0x00a1:
            sun.security.pkcs.PKCS7 r1 = new sun.security.pkcs.PKCS7     // Catch:{ all -> 0x01a6 }
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ all -> 0x01a6 }
            int r3 = r15 + 22
            int r3 = r3 - r7
            r2.<init>(r4, r3, r7)     // Catch:{ all -> 0x01a6 }
            r1.<init>(r2)     // Catch:{ all -> 0x01a6 }
            r9 = r1
            java.security.cert.X509Certificate[] r1 = r9.getCertificates()     // Catch:{ all -> 0x01a6 }
            r2 = r1
            if (r2 == 0) goto L_0x0186
            int r1 = r2.length     // Catch:{ all -> 0x01a6 }
            if (r1 == 0) goto L_0x0186
            r1 = r2[r0]     // Catch:{ all -> 0x01a6 }
            r16 = r1
            java.security.PublicKey r1 = r16.getPublicKey()     // Catch:{ all -> 0x01a6 }
            r3 = r1
            sun.security.pkcs.SignerInfo[] r1 = r9.getSignerInfos()     // Catch:{ all -> 0x01a6 }
            if (r1 == 0) goto L_0x0171
            int r8 = r1.length     // Catch:{ all -> 0x01a6 }
            if (r8 == 0) goto L_0x0171
            r0 = r1[r0]     // Catch:{ all -> 0x01a6 }
            r8 = 0
            if (r27 != 0) goto L_0x00d3
            java.io.File r18 = DEFAULT_KEYSTORE     // Catch:{ all -> 0x01a6 }
            goto L_0x00d5
        L_0x00d3:
            r18 = r27
        L_0x00d5:
            java.util.HashSet r18 = getTrustedCerts(r18)     // Catch:{ all -> 0x01a6 }
            java.util.Iterator r19 = r18.iterator()     // Catch:{ all -> 0x01a6 }
        L_0x00dd:
            boolean r20 = r19.hasNext()     // Catch:{ all -> 0x01a6 }
            if (r20 == 0) goto L_0x00fe
            java.lang.Object r20 = r19.next()     // Catch:{ all -> 0x01a6 }
            java.security.cert.X509Certificate r20 = (java.security.cert.X509Certificate) r20     // Catch:{ all -> 0x01a6 }
            r22 = r1
            java.security.PublicKey r1 = r20.getPublicKey()     // Catch:{ all -> 0x01a6 }
            boolean r1 = r1.equals(r3)     // Catch:{ all -> 0x01a6 }
            if (r1 == 0) goto L_0x00fa
            r8 = 1
            r19 = r8
            goto L_0x0102
        L_0x00fa:
            r1 = r22
            goto L_0x00dd
        L_0x00fe:
            r22 = r1
            r19 = r8
        L_0x0102:
            if (r19 == 0) goto L_0x015c
            r23 = r2
            r1 = 0
            r13.seek(r1)     // Catch:{ all -> 0x01a6 }
            r8 = r26
            android.os.RecoverySystem$1 r2 = new android.os.RecoverySystem$1     // Catch:{ all -> 0x01a6 }
            r20 = r22
            r1 = r2
            r12 = r2
            r22 = r3
            r21 = r23
            r2 = r10
            r23 = r4
            r4 = r15
            r24 = r7
            r7 = r13
            r1.<init>(r2, r4, r5, r7, r8)     // Catch:{ all -> 0x01a6 }
            sun.security.pkcs.SignerInfo r1 = r9.verify(r0, r12)     // Catch:{ all -> 0x01a6 }
            boolean r2 = java.lang.Thread.interrupted()     // Catch:{ all -> 0x01a6 }
            r3 = r26
            if (r3 == 0) goto L_0x0132
            r4 = 100
            r3.onProgress(r4)     // Catch:{ all -> 0x01b4 }
        L_0x0132:
            if (r2 != 0) goto L_0x0153
            if (r1 == 0) goto L_0x014a
            r13.close()
            boolean r0 = readAndVerifyPackageCompatibilityEntry(r25)
            if (r0 == 0) goto L_0x0141
            return
        L_0x0141:
            java.security.SignatureException r0 = new java.security.SignatureException
            java.lang.String r1 = "package compatibility verification failed"
            r0.<init>(r1)
            throw r0
        L_0x014a:
            java.security.SignatureException r4 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r7 = "signature digest verification failed"
            r4.<init>(r7)     // Catch:{ all -> 0x01b4 }
            throw r4     // Catch:{ all -> 0x01b4 }
        L_0x0153:
            java.security.SignatureException r4 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r7 = "verification was interrupted"
            r4.<init>(r7)     // Catch:{ all -> 0x01b4 }
            throw r4     // Catch:{ all -> 0x01b4 }
        L_0x015c:
            r21 = r2
            r23 = r4
            r24 = r7
            r20 = r22
            r22 = r3
            r3 = r26
            java.security.SignatureException r1 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r2 = "signature doesn't match any trusted key"
            r1.<init>(r2)     // Catch:{ all -> 0x01b4 }
            throw r1     // Catch:{ all -> 0x01b4 }
        L_0x0171:
            r20 = r1
            r21 = r2
            r22 = r3
            r23 = r4
            r24 = r7
            r3 = r26
            java.security.SignatureException r0 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r1 = "signature contains no signedData"
            r0.<init>(r1)     // Catch:{ all -> 0x01b4 }
            throw r0     // Catch:{ all -> 0x01b4 }
        L_0x0186:
            r21 = r2
            r23 = r4
            r24 = r7
            r3 = r26
            java.security.SignatureException r0 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r1 = "signature contains no certificates"
            r0.<init>(r1)     // Catch:{ all -> 0x01b4 }
            throw r0     // Catch:{ all -> 0x01b4 }
        L_0x0197:
            r23 = r4
            r24 = r7
            r3 = r26
            java.security.SignatureException r0 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r1 = "no signature in file (bad footer)"
            r0.<init>(r1)     // Catch:{ all -> 0x01b4 }
            throw r0     // Catch:{ all -> 0x01b4 }
        L_0x01a6:
            r0 = move-exception
            r3 = r26
            goto L_0x01b8
        L_0x01aa:
            r3 = r9
            java.security.SignatureException r0 = new java.security.SignatureException     // Catch:{ all -> 0x01b4 }
            java.lang.String r1 = "no signature in file (no footer)"
            r0.<init>(r1)     // Catch:{ all -> 0x01b4 }
            throw r0     // Catch:{ all -> 0x01b4 }
        L_0x01b4:
            r0 = move-exception
            goto L_0x01b8
        L_0x01b6:
            r0 = move-exception
            r3 = r9
        L_0x01b8:
            r13.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.verifyPackage(java.io.File, android.os.RecoverySystem$ProgressListener, java.io.File):void");
    }

    @UnsupportedAppUsage
    private static boolean verifyPackageCompatibility(InputStream inputStream) throws IOException {
        long entrySize;
        ArrayList<String> list = new ArrayList<>();
        ZipInputStream zis = new ZipInputStream(inputStream);
        while (true) {
            ZipEntry nextEntry = zis.getNextEntry();
            ZipEntry entry = nextEntry;
            if (nextEntry != null) {
                entrySize = entry.getSize();
                if (entrySize > 2147483647L || entrySize < 0) {
                } else {
                    byte[] bytes = new byte[((int) entrySize)];
                    Streams.readFully(zis, bytes);
                    list.add(new String(bytes, StandardCharsets.UTF_8));
                }
            } else if (!list.isEmpty()) {
                return VintfObject.verify((String[]) list.toArray(new String[list.size()])) == 0;
            } else {
                throw new IOException("no entries found in the compatibility file");
            }
        }
        throw new IOException("invalid entry size (" + entrySize + ") in the compatibility file");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0023, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean readAndVerifyPackageCompatibilityEntry(java.io.File r5) throws java.io.IOException {
        /*
            java.util.zip.ZipFile r0 = new java.util.zip.ZipFile
            r0.<init>(r5)
            r1 = 0
            java.lang.String r2 = "compatibility.zip"
            java.util.zip.ZipEntry r2 = r0.getEntry(r2)     // Catch:{ Throwable -> 0x0021 }
            if (r2 != 0) goto L_0x0013
            r3 = 1
            $closeResource(r1, r0)
            return r3
        L_0x0013:
            java.io.InputStream r3 = r0.getInputStream(r2)     // Catch:{ Throwable -> 0x0021 }
            boolean r4 = verifyPackageCompatibility((java.io.InputStream) r3)     // Catch:{ Throwable -> 0x0021 }
            $closeResource(r1, r0)
            return r4
        L_0x001f:
            r2 = move-exception
            goto L_0x0023
        L_0x0021:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001f }
        L_0x0023:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.readAndVerifyPackageCompatibilityEntry(java.io.File):boolean");
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x000e, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        $closeResource(r1, r0);
     */
    @android.annotation.SuppressLint({"Doclava125"})
    @android.annotation.SystemApi
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean verifyPackageCompatibility(java.io.File r3) throws java.io.IOException {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream
            r0.<init>(r3)
            r1 = 0
            boolean r2 = verifyPackageCompatibility((java.io.InputStream) r0)     // Catch:{ Throwable -> 0x0010 }
            $closeResource(r1, r0)
            return r2
        L_0x000e:
            r2 = move-exception
            goto L_0x0012
        L_0x0010:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x000e }
        L_0x0012:
            $closeResource(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.RecoverySystem.verifyPackageCompatibility(java.io.File):boolean");
    }

    @SystemApi
    public static void processPackage(Context context, File packageFile, final ProgressListener listener, Handler handler) throws IOException {
        final Handler progressHandler;
        String filename = packageFile.getCanonicalPath();
        if (filename.startsWith("/data/")) {
            RecoverySystem rs = (RecoverySystem) context.getSystemService("recovery");
            IRecoverySystemProgressListener progressListener = null;
            if (listener != null) {
                if (handler != null) {
                    progressHandler = handler;
                } else {
                    progressHandler = new Handler(context.getMainLooper());
                }
                progressListener = new IRecoverySystemProgressListener.Stub() {
                    int lastProgress = 0;
                    long lastPublishTime = System.currentTimeMillis();

                    public void onProgress(final int progress) {
                        final long now = System.currentTimeMillis();
                        Handler.this.post(new Runnable() {
                            public void run() {
                                if (progress > AnonymousClass2.this.lastProgress && now - AnonymousClass2.this.lastPublishTime > RecoverySystem.PUBLISH_PROGRESS_INTERVAL_MS) {
                                    AnonymousClass2.this.lastProgress = progress;
                                    AnonymousClass2.this.lastPublishTime = now;
                                    listener.onProgress(progress);
                                }
                            }
                        });
                    }
                };
            }
            if (!rs.uncrypt(filename, progressListener)) {
                throw new IOException("process package failed");
            }
        }
    }

    @SystemApi
    public static void processPackage(Context context, File packageFile, ProgressListener listener) throws IOException {
        processPackage(context, packageFile, listener, (Handler) null);
    }

    public static void installPackage(Context context, File packageFile) throws IOException {
        installPackage(context, packageFile, false);
    }

    /* JADX INFO: finally extract failed */
    @SystemApi
    public static void installPackage(Context context, File packageFile, boolean processed) throws IOException {
        synchronized (sRequestLock) {
            LOG_FILE.delete();
            UNCRYPT_PACKAGE_FILE.delete();
            String filename = packageFile.getCanonicalPath();
            Log.w(TAG, "!!! REBOOTING TO INSTALL " + filename + " !!!");
            boolean securityUpdate = filename.endsWith("_s.zip");
            if (filename.startsWith("/data/")) {
                if (!processed) {
                    FileWriter uncryptFile = new FileWriter(UNCRYPT_PACKAGE_FILE);
                    try {
                        uncryptFile.write(filename + "\n");
                        uncryptFile.close();
                        if (!UNCRYPT_PACKAGE_FILE.setReadable(true, false) || !UNCRYPT_PACKAGE_FILE.setWritable(true, false)) {
                            Log.e(TAG, "Error setting permission for " + UNCRYPT_PACKAGE_FILE);
                        }
                        BLOCK_MAP_FILE.delete();
                    } catch (Throwable th) {
                        uncryptFile.close();
                        throw th;
                    }
                } else if (!BLOCK_MAP_FILE.exists()) {
                    Log.e(TAG, "Package claimed to have been processed but failed to find the block map file.");
                    throw new IOException("Failed to find block map file");
                }
                filename = "@/cache/recovery/block.map";
            }
            String command = ("--update_package=" + filename + "\n") + ("--locale=" + Locale.getDefault().toLanguageTag() + "\n");
            if (securityUpdate) {
                command = command + "--security\n";
            }
            if (((RecoverySystem) context.getSystemService("recovery")).setupBcb(command)) {
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                String reason = PowerManager.REBOOT_RECOVERY_UPDATE;
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LEANBACK) && ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getState() != 2) {
                    reason = reason + ",quiescent";
                }
                pm.reboot(reason);
                throw new IOException("Reboot failed (no permissions?)");
            }
            throw new IOException("Setup BCB failed");
        }
    }

    @SystemApi
    public static void scheduleUpdateOnBoot(Context context, File packageFile) throws IOException {
        String filename = packageFile.getCanonicalPath();
        boolean securityUpdate = filename.endsWith("_s.zip");
        if (filename.startsWith("/data/")) {
            filename = "@/cache/recovery/block.map";
        }
        String command = ("--update_package=" + filename + "\n") + ("--locale=" + Locale.getDefault().toLanguageTag() + "\n");
        if (securityUpdate) {
            command = command + "--security\n";
        }
        if (!((RecoverySystem) context.getSystemService("recovery")).setupBcb(command)) {
            throw new IOException("schedule update on boot failed");
        }
    }

    @SystemApi
    public static void cancelScheduledUpdate(Context context) throws IOException {
        if (!((RecoverySystem) context.getSystemService("recovery")).clearBcb()) {
            throw new IOException("cancel scheduled update failed");
        }
    }

    public static void rebootWipeUserData(Context context) throws IOException {
        rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, String reason) throws IOException {
        rebootWipeUserData(context, false, reason, false, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown) throws IOException {
        rebootWipeUserData(context, shutdown, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force) throws IOException {
        rebootWipeUserData(context, shutdown, reason, force, false);
    }

    public static void rebootWipeUserData(Context context, boolean shutdown, String reason, boolean force, boolean wipeEuicc) throws IOException {
        Context context2 = context;
        UserManager um = (UserManager) context.getSystemService("user");
        if (force || !um.hasUserRestriction(UserManager.DISALLOW_FACTORY_RESET)) {
            final ConditionVariable condition = new ConditionVariable();
            Intent intent = new Intent(Intent.ACTION_MASTER_CLEAR_NOTIFICATION);
            intent.addFlags(285212672);
            context.sendOrderedBroadcastAsUser(intent, UserHandle.SYSTEM, Manifest.permission.MASTER_CLEAR, new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    ConditionVariable.this.open();
                }
            }, (Handler) null, 0, (String) null, (Bundle) null);
            condition.block();
            if (wipeEuicc) {
                wipeEuiccData(context, "android");
            }
            String shutdownArg = null;
            if (shutdown) {
                shutdownArg = "--shutdown_after";
            }
            String reasonArg = null;
            if (!TextUtils.isEmpty(reason)) {
                String timeStamp = DateFormat.format((CharSequence) "yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
                StringBuilder sb = new StringBuilder();
                sb.append("--reason=");
                StringBuilder sb2 = new StringBuilder();
                String str = reason;
                sb2.append(reason);
                sb2.append(SmsManager.REGEX_PREFIX_DELIMITER);
                sb2.append(timeStamp);
                sb.append(sanitizeArg(sb2.toString()));
                reasonArg = sb.toString();
            } else {
                String str2 = reason;
            }
            bootCommand(context, shutdownArg, "--wipe_data", reasonArg, "--locale=" + Locale.getDefault().toLanguageTag());
            return;
        }
        throw new SecurityException("Wiping data is not allowed for this user.");
    }

    public static boolean wipeEuiccData(Context context, String packageName) {
        long waitingTimeMillis;
        Context context2 = context;
        if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.EUICC_PROVISIONED, 0) == 0) {
            Log.d(TAG, "Skipping eUICC wipe/retain as it is not provisioned");
            return true;
        }
        EuiccManager euiccManager = (EuiccManager) context2.getSystemService(Context.EUICC_SERVICE);
        if (euiccManager == null || !euiccManager.isEnabled()) {
            String str = packageName;
            return false;
        }
        final CountDownLatch euiccFactoryResetLatch = new CountDownLatch(1);
        final AtomicBoolean wipingSucceeded = new AtomicBoolean(false);
        BroadcastReceiver euiccWipeFinishReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                    if (getResultCode() != 0) {
                        int detailedCode = intent.getIntExtra(EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE, 0);
                        Log.e(RecoverySystem.TAG, "Error wiping euicc data, Detailed code = " + detailedCode);
                    } else {
                        Log.d(RecoverySystem.TAG, "Successfully wiped euicc data.");
                        wipingSucceeded.set(true);
                    }
                    euiccFactoryResetLatch.countDown();
                }
            }
        };
        Intent intent = new Intent(ACTION_EUICC_FACTORY_RESET);
        intent.setPackage(packageName);
        PendingIntent callbackIntent = PendingIntent.getBroadcastAsUser(context2, 0, intent, 134217728, UserHandle.SYSTEM);
        IntentFilter filterConsent = new IntentFilter();
        filterConsent.addAction(ACTION_EUICC_FACTORY_RESET);
        HandlerThread euiccHandlerThread = new HandlerThread("euiccWipeFinishReceiverThread");
        euiccHandlerThread.start();
        context.getApplicationContext().registerReceiver(euiccWipeFinishReceiver, filterConsent, (String) null, new Handler(euiccHandlerThread.getLooper()));
        euiccManager.eraseSubscriptions(callbackIntent);
        try {
            CountDownLatch euiccFactoryResetLatch2 = euiccFactoryResetLatch;
            try {
                waitingTimeMillis = Settings.Global.getLong(context.getContentResolver(), Settings.Global.EUICC_FACTORY_RESET_TIMEOUT_MILLIS, 30000);
                if (waitingTimeMillis < 5000) {
                    waitingTimeMillis = 5000;
                } else if (waitingTimeMillis > 60000) {
                    waitingTimeMillis = 60000;
                }
            } catch (InterruptedException e) {
                e = e;
                CountDownLatch countDownLatch = euiccFactoryResetLatch2;
                try {
                    Thread.currentThread().interrupt();
                    Log.e(TAG, "Wiping eUICC data interrupted", e);
                    context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                    return false;
                } catch (Throwable th) {
                    e = th;
                    context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                CountDownLatch countDownLatch2 = euiccFactoryResetLatch2;
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                throw e;
            }
            try {
                if (!euiccFactoryResetLatch2.await(waitingTimeMillis, TimeUnit.MILLISECONDS)) {
                    Log.e(TAG, "Timeout wiping eUICC data.");
                    context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                    return false;
                }
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                return wipingSucceeded.get();
            } catch (InterruptedException e2) {
                e = e2;
                Thread.currentThread().interrupt();
                Log.e(TAG, "Wiping eUICC data interrupted", e);
                context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
                return false;
            }
        } catch (InterruptedException e3) {
            e = e3;
            CountDownLatch countDownLatch3 = euiccFactoryResetLatch;
            Thread.currentThread().interrupt();
            Log.e(TAG, "Wiping eUICC data interrupted", e);
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            return false;
        } catch (Throwable th3) {
            e = th3;
            CountDownLatch countDownLatch4 = euiccFactoryResetLatch;
            context.getApplicationContext().unregisterReceiver(euiccWipeFinishReceiver);
            throw e;
        }
    }

    public static void rebootPromptAndWipeUserData(Context context, String reason) throws IOException {
        boolean checkpointing = false;
        IVold vold = null;
        try {
            vold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
            if (vold != null) {
                checkpointing = vold.needsCheckpoint();
            } else {
                Log.w(TAG, "Failed to get vold");
            }
        } catch (Exception e) {
            Log.w(TAG, "Failed to check for checkpointing");
        }
        if (checkpointing) {
            try {
                vold.abortChanges("rescueparty", false);
                Log.i(TAG, "Rescue Party requested wipe. Aborting update");
            } catch (Exception e2) {
                Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).reboot("rescueparty");
            }
        } else {
            String reasonArg = null;
            if (!TextUtils.isEmpty(reason)) {
                reasonArg = "--reason=" + sanitizeArg(reason);
            }
            SystemProperties.set("debug.wits_enter_recovery", "1");
            try {
                Thread.sleep((long) MediaPlayer.ProvisioningThread.TIMEOUT_MS);
            } catch (InterruptedException e3) {
            }
            bootCommand(context, null, "--prompt_and_wipe_data", reasonArg, "--locale=" + Locale.getDefault().toString());
        }
    }

    public static void rebootWipeCache(Context context) throws IOException {
        rebootWipeCache(context, context.getPackageName());
    }

    public static void rebootWipeCache(Context context, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        bootCommand(context, "--wipe_cache", reasonArg, "--locale=" + Locale.getDefault().toLanguageTag());
    }

    @SystemApi
    public static void rebootWipeAb(Context context, File packageFile, String reason) throws IOException {
        String reasonArg = null;
        if (!TextUtils.isEmpty(reason)) {
            reasonArg = "--reason=" + sanitizeArg(reason);
        }
        bootCommand(context, "--wipe_ab", "--wipe_package=" + packageFile.getCanonicalPath(), reasonArg, "--locale=" + Locale.getDefault().toLanguageTag());
    }

    private static void bootCommand(Context context, String... args) throws IOException {
        LOG_FILE.delete();
        StringBuilder command = new StringBuilder();
        for (String arg : args) {
            if (!TextUtils.isEmpty(arg)) {
                command.append(arg);
                command.append("\n");
            }
        }
        ((RecoverySystem) context.getSystemService("recovery")).rebootRecoveryWithCommand(command.toString());
        throw new IOException("Reboot failed (no permissions?)");
    }

    public static String handleAftermath(Context context) {
        String log = null;
        try {
            log = FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
        } catch (FileNotFoundException e) {
            Log.i(TAG, "No recovery log file");
        } catch (IOException e2) {
            Log.e(TAG, "Error reading recovery log", e2);
        }
        boolean reservePackage = BLOCK_MAP_FILE.exists();
        int i = 0;
        if (!reservePackage && UNCRYPT_PACKAGE_FILE.exists()) {
            String filename = null;
            try {
                filename = FileUtils.readTextFile(UNCRYPT_PACKAGE_FILE, 0, (String) null);
            } catch (IOException e3) {
                Log.e(TAG, "Error reading uncrypt file", e3);
            }
            if (filename != null && filename.startsWith("/data")) {
                if (UNCRYPT_PACKAGE_FILE.delete()) {
                    Log.i(TAG, "Deleted: " + filename);
                } else {
                    Log.e(TAG, "Can't delete: " + filename);
                }
            }
        }
        String[] names = RECOVERY_DIR.list();
        while (names != null && i < names.length) {
            if (!names[i].startsWith(LAST_PREFIX) && !names[i].equals(LAST_INSTALL_PATH) && ((!reservePackage || !names[i].equals(BLOCK_MAP_FILE.getName())) && (!reservePackage || !names[i].equals(UNCRYPT_PACKAGE_FILE.getName())))) {
                recursiveDelete(new File(RECOVERY_DIR, names[i]));
            }
            i++;
        }
        return log;
    }

    private static void recursiveDelete(File name) {
        if (name.isDirectory()) {
            String[] files = name.list();
            int i = 0;
            while (files != null && i < files.length) {
                recursiveDelete(new File(name, files[i]));
                i++;
            }
        }
        if (!name.delete()) {
            Log.e(TAG, "Can't delete: " + name);
            return;
        }
        Log.i(TAG, "Deleted: " + name);
    }

    private boolean uncrypt(String packageFile, IRecoverySystemProgressListener listener) {
        try {
            return this.mService.uncrypt(packageFile, listener);
        } catch (RemoteException e) {
            return false;
        }
    }

    private boolean setupBcb(String command) {
        try {
            return this.mService.setupBcb(command);
        } catch (RemoteException e) {
            return false;
        }
    }

    private boolean clearBcb() {
        try {
            return this.mService.clearBcb();
        } catch (RemoteException e) {
            return false;
        }
    }

    private void rebootRecoveryWithCommand(String command) {
        try {
            this.mService.rebootRecoveryWithCommand(command);
        } catch (RemoteException e) {
        }
    }

    private static String sanitizeArg(String arg) {
        return arg.replace(0, '?').replace(10, '?');
    }

    public RecoverySystem() {
        this.mService = null;
    }

    public RecoverySystem(IRecoverySystem service) {
        this.mService = service;
    }
}
