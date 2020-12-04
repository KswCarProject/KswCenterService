package android.util.apk;

import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.content.pm.Signature;
import android.os.Trace;
import android.util.BoostFramework;
import android.util.apk.ApkSignatureSchemeV3Verifier;
import android.util.jar.StrictJarFile;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import libcore.io.IoUtils;

public class ApkSignatureVerifier {
    /* access modifiers changed from: private */
    public static final int NUMBER_OF_CORES;
    private static final String TAG = "ApkSignatureVerifier";
    private static final AtomicReference<byte[]> sBuffer = new AtomicReference<>();
    private static boolean sIsPerfLockAcquired = false;
    private static BoostFramework sPerfBoost = null;

    static {
        int i = 4;
        if (Runtime.getRuntime().availableProcessors() < 4) {
            i = Runtime.getRuntime().availableProcessors();
        }
        NUMBER_OF_CORES = i;
    }

    public static PackageParser.SigningDetails verify(String apkPath, @PackageParser.SigningDetails.SignatureSchemeVersion int minSignatureSchemeVersion) throws PackageParser.PackageParserException {
        if (minSignatureSchemeVersion <= 3) {
            Trace.traceBegin(262144, "verifyV3");
            try {
                ApkSignatureSchemeV3Verifier.VerifiedSigner vSigner = ApkSignatureSchemeV3Verifier.verify(apkPath);
                Signature[] signerSigs = convertToSignatures(new Certificate[][]{vSigner.certs});
                Signature[] pastSignerSigs = null;
                if (vSigner.por != null) {
                    pastSignerSigs = new Signature[vSigner.por.certs.size()];
                    for (int i = 0; i < pastSignerSigs.length; i++) {
                        pastSignerSigs[i] = new Signature(vSigner.por.certs.get(i).getEncoded());
                        pastSignerSigs[i].setFlags(vSigner.por.flagsList.get(i).intValue());
                    }
                }
                PackageParser.SigningDetails signingDetails = new PackageParser.SigningDetails(signerSigs, 3, pastSignerSigs);
                Trace.traceEnd(262144);
                return signingDetails;
            } catch (SignatureNotFoundException e) {
                if (minSignatureSchemeVersion < 3) {
                    Trace.traceEnd(262144);
                    if (minSignatureSchemeVersion <= 2) {
                        Trace.traceBegin(262144, "verifyV2");
                        try {
                            PackageParser.SigningDetails signingDetails2 = new PackageParser.SigningDetails(convertToSignatures(ApkSignatureSchemeV2Verifier.verify(apkPath)), 2);
                            Trace.traceEnd(262144);
                            return signingDetails2;
                        } catch (SignatureNotFoundException e2) {
                            if (minSignatureSchemeVersion < 2) {
                                Trace.traceEnd(262144);
                                if (minSignatureSchemeVersion <= 1) {
                                    return verifyV1Signature(apkPath, true);
                                }
                                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                            }
                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v2 signature in package " + apkPath, e2);
                        } catch (Exception e3) {
                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v2", e3);
                        } catch (Throwable th) {
                            Trace.traceEnd(262144);
                            throw th;
                        }
                    } else {
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                    }
                } else {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v3 signature in package " + apkPath, e);
                }
            } catch (Exception e4) {
                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v3", e4);
            } catch (Throwable th2) {
                Trace.traceEnd(262144);
                throw th2;
            }
        } else {
            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0203, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x021c, code lost:
        throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + r8, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x021d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0236, code lost:
        throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING, "Failed to collect certificates from " + r8, r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0203 A[ExcHandler: IOException | RuntimeException (r0v3 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:5:0x001b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.pm.PackageParser.SigningDetails verifyV1Signature(java.lang.String r28, boolean r29) throws android.content.pm.PackageParser.PackageParserException {
        /*
            r8 = r28
            r9 = r29
            r10 = 1
            if (r9 == 0) goto L_0x000a
            int r0 = NUMBER_OF_CORES
            goto L_0x000b
        L_0x000a:
            r0 = r10
        L_0x000b:
            r11 = r0
            android.util.jar.StrictJarFile[] r12 = new android.util.jar.StrictJarFile[r11]
            android.util.ArrayMap r3 = new android.util.ArrayMap
            r3.<init>()
            r14 = 262144(0x40000, double:1.295163E-318)
            r16 = 0
            java.lang.String r0 = "strictJarFileCtor"
            android.os.Trace.traceBegin(r14, r0)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            android.util.BoostFramework r0 = sPerfBoost     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 != 0) goto L_0x0029
            android.util.BoostFramework r0 = new android.util.BoostFramework     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r0.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            sPerfBoost = r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0029:
            android.util.BoostFramework r0 = sPerfBoost     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 == 0) goto L_0x0048
            boolean r0 = sIsPerfLockAcquired     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 != 0) goto L_0x0048
            if (r9 == 0) goto L_0x0048
            android.util.BoostFramework r0 = sPerfBoost     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1 = 4232(0x1088, float:5.93E-42)
            r2 = 0
            r4 = 2147483647(0x7fffffff, float:NaN)
            r5 = -1
            r0.perfHint(r1, r2, r4, r5)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r0 = "ApkSignatureVerifier"
            java.lang.String r1 = "Perflock acquired for PackageInstall "
            android.util.Slog.d(r0, r1)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            sIsPerfLockAcquired = r10     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0048:
            r0 = r16
        L_0x004a:
            if (r0 >= r11) goto L_0x0056
            android.util.jar.StrictJarFile r1 = new android.util.jar.StrictJarFile     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1.<init>((java.lang.String) r8, (boolean) r10, (boolean) r9)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r12[r0] = r1     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            int r0 = r0 + 1
            goto L_0x004a
        L_0x0056:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r0.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r7 = r0
            r0 = r12[r16]     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r1 = "AndroidManifest.xml"
            java.util.zip.ZipEntry r0 = r0.findEntry(r1)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r6 = r0
            if (r6 == 0) goto L_0x01df
            r0 = r12[r16]     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.security.cert.Certificate[][] r0 = loadCertificates(r0, r6)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r17 = r0
            boolean r0 = com.android.internal.util.ArrayUtils.isEmpty((T[]) r17)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 != 0) goto L_0x01b8
            android.content.pm.Signature[] r0 = convertToSignatures(r17)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r4 = r0
            if (r9 == 0) goto L_0x0181
            r0 = r12[r16]     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0082:
            r18 = r0
            boolean r0 = r18.hasNext()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 == 0) goto L_0x00b4
            java.lang.Object r0 = r18.next()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.util.zip.ZipEntry r0 = (java.util.zip.ZipEntry) r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            boolean r1 = r0.isDirectory()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r1 == 0) goto L_0x0097
            goto L_0x00b1
        L_0x0097:
            java.lang.String r1 = r0.getName()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r2 = "META-INF/"
            boolean r2 = r1.startsWith(r2)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r2 == 0) goto L_0x00a4
            goto L_0x00b1
        L_0x00a4:
            java.lang.String r2 = "AndroidManifest.xml"
            boolean r2 = r1.equals(r2)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r2 == 0) goto L_0x00ad
            goto L_0x00b1
        L_0x00ad:
            r7.add(r0)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x00b1:
            r0 = r18
            goto L_0x0082
        L_0x00b4:
            android.util.apk.ApkSignatureVerifier$1VerificationData r0 = new android.util.apk.ApkSignatureVerifier$1VerificationData     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r0.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r2 = r0
            java.lang.Object r0 = new java.lang.Object     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r0.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r2.objWaitAll = r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.util.concurrent.ThreadPoolExecutor r0 = new java.util.concurrent.ThreadPoolExecutor     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            int r20 = NUMBER_OF_CORES     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            int r21 = NUMBER_OF_CORES     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r22 = 1
            java.util.concurrent.TimeUnit r24 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.util.concurrent.LinkedBlockingQueue r25 = new java.util.concurrent.LinkedBlockingQueue     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r25.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r19 = r0
            r19.<init>(r20, r21, r22, r24, r25)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1 = r0
            java.util.Iterator r0 = r7.iterator()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x00da:
            boolean r5 = r0.hasNext()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r5 == 0) goto L_0x0116
            java.lang.Object r5 = r0.next()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.util.zip.ZipEntry r5 = (java.util.zip.ZipEntry) r5     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            android.util.apk.ApkSignatureVerifier$1 r19 = new android.util.apk.ApkSignatureVerifier$1     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r13 = r1
            r1 = r19
            r26 = r2
            r27 = r4
            r4 = r12
            r20 = r6
            r6 = r28
            r21 = r7
            r7 = r27
            r1.<init>(r3, r4, r5, r6, r7)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1 = r19
            r2 = r26
            java.lang.Object r4 = r2.objWaitAll     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            monitor-enter(r4)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            int r6 = r2.exceptionFlag     // Catch:{ all -> 0x0113 }
            if (r6 != 0) goto L_0x0109
            r13.execute(r1)     // Catch:{ all -> 0x0113 }
        L_0x0109:
            monitor-exit(r4)     // Catch:{ all -> 0x0113 }
            r1 = r13
            r6 = r20
            r7 = r21
            r4 = r27
            goto L_0x00da
        L_0x0113:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0113 }
            throw r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0116:
            r13 = r1
            r27 = r4
            r20 = r6
            r21 = r7
            r2.wait = r10     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r13.shutdown()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0122:
            boolean r0 = r2.wait     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 == 0) goto L_0x0161
            int r0 = r2.exceptionFlag     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            if (r0 == 0) goto L_0x014c
            boolean r0 = r2.shutDown     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            if (r0 != 0) goto L_0x014c
            java.lang.String r0 = "ApkSignatureVerifier"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            r1.<init>()     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            java.lang.String r4 = "verifyV1 Exception "
            r1.append(r4)     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            int r4 = r2.exceptionFlag     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            r1.append(r4)     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            java.lang.String r1 = r1.toString()     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            android.util.Slog.w((java.lang.String) r0, (java.lang.String) r1)     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            r13.shutdownNow()     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            r2.shutDown = r10     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
        L_0x014c:
            r0 = 50
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            boolean r0 = r13.awaitTermination(r0, r4)     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            r0 = r0 ^ r10
            r2.wait = r0     // Catch:{ InterruptedException -> 0x0158, IOException | RuntimeException -> 0x0203 }
            goto L_0x0160
        L_0x0158:
            r0 = move-exception
            java.lang.String r1 = "ApkSignatureVerifier"
            java.lang.String r4 = "VerifyV1 interrupted while awaiting all threads done..."
            android.util.Slog.w((java.lang.String) r1, (java.lang.String) r4)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0160:
            goto L_0x0122
        L_0x0161:
            int r0 = r2.exceptionFlag     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            if (r0 != 0) goto L_0x0166
            goto L_0x0187
        L_0x0166:
            android.content.pm.PackageParser$PackageParserException r0 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            int r1 = r2.exceptionFlag     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r4.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r5 = "Failed to collect certificates from "
            r4.append(r5)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r4.append(r8)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r4 = r4.toString()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.Exception r5 = r2.exception     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r0.<init>(r1, r4, r5)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            throw r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0181:
            r27 = r4
            r20 = r6
            r21 = r7
        L_0x0187:
            android.content.pm.PackageParser$SigningDetails r0 = new android.content.pm.PackageParser$SigningDetails     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1 = r27
            r0.<init>(r1, r10)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            boolean r2 = sIsPerfLockAcquired
            if (r2 == 0) goto L_0x01a4
            android.util.BoostFramework r2 = sPerfBoost
            if (r2 == 0) goto L_0x01a4
            android.util.BoostFramework r2 = sPerfBoost
            r2.perfLockRelease()
            sIsPerfLockAcquired = r16
            java.lang.String r2 = "ApkSignatureVerifier"
            java.lang.String r4 = "Perflock released for PackageInstall "
            android.util.Slog.d(r2, r4)
        L_0x01a4:
            r3.clear()
            android.os.Trace.traceEnd(r14)
        L_0x01ab:
            r2 = r16
            if (r2 >= r11) goto L_0x01b7
            r4 = r12[r2]
            closeQuietly(r4)
            int r16 = r2 + 1
            goto L_0x01ab
        L_0x01b7:
            return r0
        L_0x01b8:
            r20 = r6
            r21 = r7
            android.content.pm.PackageParser$PackageParserException r0 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r2 = "Package "
            r1.append(r2)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1.append(r8)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r2 = " has no certificates at entry "
            r1.append(r2)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r2 = "AndroidManifest.xml"
            r1.append(r2)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r1 = r1.toString()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r2 = -103(0xffffffffffffff99, float:NaN)
            r0.<init>(r2, r1)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            throw r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x01df:
            r20 = r6
            r21 = r7
            android.content.pm.PackageParser$PackageParserException r0 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r1 = -101(0xffffffffffffff9b, float:NaN)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r2.<init>()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r4 = "Package "
            r2.append(r4)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r2.append(r8)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r4 = " has no manifest"
            r2.append(r4)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            java.lang.String r2 = r2.toString()     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            r0.<init>(r1, r2)     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
            throw r0     // Catch:{ GeneralSecurityException -> 0x021d, IOException | RuntimeException -> 0x0203 }
        L_0x0201:
            r0 = move-exception
            goto L_0x0237
        L_0x0203:
            r0 = move-exception
            android.content.pm.PackageParser$PackageParserException r1 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ all -> 0x0201 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0201 }
            r2.<init>()     // Catch:{ all -> 0x0201 }
            java.lang.String r4 = "Failed to collect certificates from "
            r2.append(r4)     // Catch:{ all -> 0x0201 }
            r2.append(r8)     // Catch:{ all -> 0x0201 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0201 }
            r4 = -103(0xffffffffffffff99, float:NaN)
            r1.<init>(r4, r2, r0)     // Catch:{ all -> 0x0201 }
            throw r1     // Catch:{ all -> 0x0201 }
        L_0x021d:
            r0 = move-exception
            android.content.pm.PackageParser$PackageParserException r1 = new android.content.pm.PackageParser$PackageParserException     // Catch:{ all -> 0x0201 }
            r2 = -105(0xffffffffffffff97, float:NaN)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0201 }
            r4.<init>()     // Catch:{ all -> 0x0201 }
            java.lang.String r5 = "Failed to collect certificates from "
            r4.append(r5)     // Catch:{ all -> 0x0201 }
            r4.append(r8)     // Catch:{ all -> 0x0201 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0201 }
            r1.<init>(r2, r4, r0)     // Catch:{ all -> 0x0201 }
            throw r1     // Catch:{ all -> 0x0201 }
        L_0x0237:
            boolean r1 = sIsPerfLockAcquired
            if (r1 == 0) goto L_0x024d
            android.util.BoostFramework r1 = sPerfBoost
            if (r1 == 0) goto L_0x024d
            android.util.BoostFramework r1 = sPerfBoost
            r1.perfLockRelease()
            sIsPerfLockAcquired = r16
            java.lang.String r1 = "ApkSignatureVerifier"
            java.lang.String r2 = "Perflock released for PackageInstall "
            android.util.Slog.d(r1, r2)
        L_0x024d:
            r3.clear()
            android.os.Trace.traceEnd(r14)
        L_0x0254:
            r1 = r16
            if (r1 >= r11) goto L_0x0260
            r2 = r12[r1]
            closeQuietly(r2)
            int r16 = r1 + 1
            goto L_0x0254
        L_0x0260:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.apk.ApkSignatureVerifier.verifyV1Signature(java.lang.String, boolean):android.content.pm.PackageParser$SigningDetails");
    }

    /* access modifiers changed from: private */
    public static Certificate[][] loadCertificates(StrictJarFile jarFile, ZipEntry entry) throws PackageParser.PackageParserException {
        try {
            InputStream is = jarFile.getInputStream(entry);
            readFullyIgnoringContents(is);
            Certificate[][] certificateChains = jarFile.getCertificateChains(entry);
            IoUtils.closeQuietly(is);
            return certificateChains;
        } catch (IOException | RuntimeException e) {
            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed reading " + entry.getName() + " in " + jarFile, e);
        } catch (Throwable th) {
            IoUtils.closeQuietly((AutoCloseable) null);
            throw th;
        }
    }

    private static void readFullyIgnoringContents(InputStream in) throws IOException {
        byte[] buffer = sBuffer.getAndSet((Object) null);
        if (buffer == null) {
            buffer = new byte[4096];
        }
        int count = 0;
        while (true) {
            int read = in.read(buffer, 0, buffer.length);
            int n = read;
            if (read != -1) {
                count += n;
            } else {
                sBuffer.set(buffer);
                return;
            }
        }
    }

    public static Signature[] convertToSignatures(Certificate[][] certs) throws CertificateEncodingException {
        Signature[] res = new Signature[certs.length];
        for (int i = 0; i < certs.length; i++) {
            res[i] = new Signature(certs[i]);
        }
        return res;
    }

    private static void closeQuietly(StrictJarFile jarFile) {
        if (jarFile != null) {
            try {
                jarFile.close();
            } catch (Exception e) {
            }
        }
    }

    public static PackageParser.SigningDetails unsafeGetCertsWithoutVerification(String apkPath, int minSignatureSchemeVersion) throws PackageParser.PackageParserException {
        if (minSignatureSchemeVersion <= 3) {
            Trace.traceBegin(262144, "certsOnlyV3");
            try {
                ApkSignatureSchemeV3Verifier.VerifiedSigner vSigner = ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(apkPath);
                Signature[] signerSigs = convertToSignatures(new Certificate[][]{vSigner.certs});
                Signature[] pastSignerSigs = null;
                if (vSigner.por != null) {
                    pastSignerSigs = new Signature[vSigner.por.certs.size()];
                    for (int i = 0; i < pastSignerSigs.length; i++) {
                        pastSignerSigs[i] = new Signature(vSigner.por.certs.get(i).getEncoded());
                        pastSignerSigs[i].setFlags(vSigner.por.flagsList.get(i).intValue());
                    }
                }
                PackageParser.SigningDetails signingDetails = new PackageParser.SigningDetails(signerSigs, 3, pastSignerSigs);
                Trace.traceEnd(262144);
                return signingDetails;
            } catch (SignatureNotFoundException e) {
                if (minSignatureSchemeVersion < 3) {
                    Trace.traceEnd(262144);
                    if (minSignatureSchemeVersion <= 2) {
                        Trace.traceBegin(262144, "certsOnlyV2");
                        try {
                            PackageParser.SigningDetails signingDetails2 = new PackageParser.SigningDetails(convertToSignatures(ApkSignatureSchemeV2Verifier.unsafeGetCertsWithoutVerification(apkPath)), 2);
                            Trace.traceEnd(262144);
                            return signingDetails2;
                        } catch (SignatureNotFoundException e2) {
                            if (minSignatureSchemeVersion < 2) {
                                Trace.traceEnd(262144);
                                if (minSignatureSchemeVersion <= 1) {
                                    return verifyV1Signature(apkPath, false);
                                }
                                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                            }
                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v2 signature in package " + apkPath, e2);
                        } catch (Exception e3) {
                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v2", e3);
                        } catch (Throwable th) {
                            Trace.traceEnd(262144);
                            throw th;
                        }
                    } else {
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                    }
                } else {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v3 signature in package " + apkPath, e);
                }
            } catch (Exception e4) {
                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v3", e4);
            } catch (Throwable th2) {
                Trace.traceEnd(262144);
                throw th2;
            }
        } else {
            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
        }
    }

    public static byte[] getVerityRootHash(String apkPath) throws IOException, SecurityException {
        try {
            return ApkSignatureSchemeV3Verifier.getVerityRootHash(apkPath);
        } catch (SignatureNotFoundException e) {
            try {
                return ApkSignatureSchemeV2Verifier.getVerityRootHash(apkPath);
            } catch (SignatureNotFoundException e2) {
                return null;
            }
        }
    }

    public static byte[] generateApkVerity(String apkPath, ByteBufferFactory bufferFactory) throws IOException, SignatureNotFoundException, SecurityException, DigestException, NoSuchAlgorithmException {
        try {
            return ApkSignatureSchemeV3Verifier.generateApkVerity(apkPath, bufferFactory);
        } catch (SignatureNotFoundException e) {
            return ApkSignatureSchemeV2Verifier.generateApkVerity(apkPath, bufferFactory);
        }
    }

    public static byte[] generateApkVerityRootHash(String apkPath) throws NoSuchAlgorithmException, DigestException, IOException {
        try {
            return ApkSignatureSchemeV3Verifier.generateApkVerityRootHash(apkPath);
        } catch (SignatureNotFoundException e) {
            try {
                return ApkSignatureSchemeV2Verifier.generateApkVerityRootHash(apkPath);
            } catch (SignatureNotFoundException e2) {
                return null;
            }
        }
    }

    public static class Result {
        public final Certificate[][] certs;
        public final int signatureSchemeVersion;
        public final Signature[] sigs;

        public Result(Certificate[][] certs2, Signature[] sigs2, int signingVersion) {
            this.certs = certs2;
            this.sigs = sigs2;
            this.signatureSchemeVersion = signingVersion;
        }
    }
}
