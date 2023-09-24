package android.util.apk;

import android.content.p002pm.PackageManager;
import android.content.p002pm.PackageParser;
import android.content.p002pm.Signature;
import android.p007os.Trace;
import android.util.ArrayMap;
import android.util.BoostFramework;
import android.util.Slog;
import android.util.apk.ApkSignatureSchemeV3Verifier;
import android.util.jar.StrictJarFile;
import com.android.internal.util.ArrayUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import libcore.io.IoUtils;

/* loaded from: classes4.dex */
public class ApkSignatureVerifier {
    private static final int NUMBER_OF_CORES;
    private static final String TAG = "ApkSignatureVerifier";
    private static final AtomicReference<byte[]> sBuffer = new AtomicReference<>();
    private static boolean sIsPerfLockAcquired;
    private static BoostFramework sPerfBoost;

    static {
        NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors() < 4 ? Runtime.getRuntime().availableProcessors() : 4;
        sPerfBoost = null;
        sIsPerfLockAcquired = false;
    }

    public static PackageParser.SigningDetails verify(String apkPath, @PackageParser.SigningDetails.SignatureSchemeVersion int minSignatureSchemeVersion) throws PackageParser.PackageParserException {
        if (minSignatureSchemeVersion > 3) {
            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
        }
        Trace.traceBegin(262144L, "verifyV3");
        try {
            try {
                ApkSignatureSchemeV3Verifier.VerifiedSigner vSigner = ApkSignatureSchemeV3Verifier.verify(apkPath);
                Certificate[][] signerCerts = {vSigner.certs};
                Signature[] signerSigs = convertToSignatures(signerCerts);
                Signature[] pastSignerSigs = null;
                if (vSigner.por != null) {
                    pastSignerSigs = new Signature[vSigner.por.certs.size()];
                    for (int i = 0; i < pastSignerSigs.length; i++) {
                        pastSignerSigs[i] = new Signature(vSigner.por.certs.get(i).getEncoded());
                        pastSignerSigs[i].setFlags(vSigner.por.flagsList.get(i).intValue());
                    }
                }
                PackageParser.SigningDetails signingDetails = new PackageParser.SigningDetails(signerSigs, 3, pastSignerSigs);
                Trace.traceEnd(262144L);
                return signingDetails;
            } catch (SignatureNotFoundException e) {
                if (minSignatureSchemeVersion >= 3) {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v3 signature in package " + apkPath, e);
                }
                Trace.traceEnd(262144L);
                if (minSignatureSchemeVersion > 2) {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                }
                Trace.traceBegin(262144L, "verifyV2");
                try {
                    try {
                        Signature[] signerSigs2 = convertToSignatures(ApkSignatureSchemeV2Verifier.verify(apkPath));
                        PackageParser.SigningDetails signingDetails2 = new PackageParser.SigningDetails(signerSigs2, 2);
                        Trace.traceEnd(262144L);
                        return signingDetails2;
                    } catch (SignatureNotFoundException e2) {
                        if (minSignatureSchemeVersion >= 2) {
                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v2 signature in package " + apkPath, e2);
                        }
                        Trace.traceEnd(262144L);
                        if (minSignatureSchemeVersion <= 1) {
                            return verifyV1Signature(apkPath, true);
                        }
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                    } catch (Exception e3) {
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v2", e3);
                    }
                } catch (Throwable th) {
                    Trace.traceEnd(262144L);
                    throw th;
                }
            } catch (Exception e4) {
                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v3", e4);
            }
        } catch (Throwable th2) {
            Trace.traceEnd(262144L);
            throw th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r16v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v6 */
    private static PackageParser.SigningDetails verifyV1Signature(final String apkPath, boolean verifyFull) throws PackageParser.PackageParserException {
        Signature[] lastSigs;
        int i;
        int objectNumber = verifyFull ? NUMBER_OF_CORES : 1;
        final StrictJarFile[] jarFile = new StrictJarFile[objectNumber];
        final ArrayMap<String, StrictJarFile> strictJarFiles = new ArrayMap<>();
        ?? r16 = 0;
        int i2 = 0;
        try {
            try {
                try {
                    Trace.traceBegin(262144L, "strictJarFileCtor");
                    if (sPerfBoost == null) {
                        sPerfBoost = new BoostFramework();
                    }
                    if (sPerfBoost != null && !sIsPerfLockAcquired && verifyFull) {
                        sPerfBoost.perfHint(BoostFramework.VENDOR_HINT_PACKAGE_INSTALL_BOOST, null, Integer.MAX_VALUE, -1);
                        Slog.m58d(TAG, "Perflock acquired for PackageInstall ");
                        sIsPerfLockAcquired = true;
                    }
                    for (int i3 = 0; i3 < objectNumber; i3++) {
                        jarFile[i3] = new StrictJarFile(apkPath, true, verifyFull);
                    }
                    List<ZipEntry> toVerify = new ArrayList<>();
                    ZipEntry manifestEntry = jarFile[0].findEntry(PackageParser.ANDROID_MANIFEST_FILENAME);
                    if (manifestEntry == null) {
                        throw new PackageParser.PackageParserException(-101, "Package " + apkPath + " has no manifest");
                    }
                    Certificate[][] lastCerts = loadCertificates(jarFile[0], manifestEntry);
                    if (ArrayUtils.isEmpty(lastCerts)) {
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Package " + apkPath + " has no certificates at entry " + PackageParser.ANDROID_MANIFEST_FILENAME);
                    }
                    Signature[] lastSigs2 = convertToSignatures(lastCerts);
                    if (verifyFull) {
                        Iterator<ZipEntry> i4 = jarFile[0].iterator();
                        while (true) {
                            Iterator<ZipEntry> i5 = i4;
                            if (!i5.hasNext()) {
                                break;
                            }
                            ZipEntry entry = i5.next();
                            if (!entry.isDirectory()) {
                                String entryName = entry.getName();
                                if (!entryName.startsWith("META-INF/") && !entryName.equals(PackageParser.ANDROID_MANIFEST_FILENAME)) {
                                    toVerify.add(entry);
                                }
                            }
                            i4 = i5;
                        }
                        final C1VerificationData vData = new C1VerificationData();
                        vData.objWaitAll = new Object();
                        ThreadPoolExecutor verificationExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
                        for (final ZipEntry entry2 : toVerify) {
                            ThreadPoolExecutor verificationExecutor2 = verificationExecutor;
                            final Signature[] lastSigs3 = lastSigs2;
                            ZipEntry manifestEntry2 = manifestEntry;
                            List<ZipEntry> toVerify2 = toVerify;
                            Runnable verifyTask = new Runnable() { // from class: android.util.apk.ApkSignatureVerifier.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    StrictJarFile tempJarFile;
                                    try {
                                        if (C1VerificationData.this.exceptionFlag != 0) {
                                            Slog.m50w(ApkSignatureVerifier.TAG, "VerifyV1 exit with exception " + C1VerificationData.this.exceptionFlag);
                                            return;
                                        }
                                        String tid = Long.toString(Thread.currentThread().getId());
                                        synchronized (strictJarFiles) {
                                            tempJarFile = (StrictJarFile) strictJarFiles.get(tid);
                                            if (tempJarFile == null) {
                                                if (C1VerificationData.this.index >= ApkSignatureVerifier.NUMBER_OF_CORES) {
                                                    C1VerificationData.this.index = 0;
                                                }
                                                StrictJarFile[] strictJarFileArr = jarFile;
                                                C1VerificationData c1VerificationData = C1VerificationData.this;
                                                int i6 = c1VerificationData.index;
                                                c1VerificationData.index = i6 + 1;
                                                tempJarFile = strictJarFileArr[i6];
                                                strictJarFiles.put(tid, tempJarFile);
                                            }
                                        }
                                        Certificate[][] entryCerts = ApkSignatureVerifier.loadCertificates(tempJarFile, entry2);
                                        if (ArrayUtils.isEmpty(entryCerts)) {
                                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Package " + apkPath + " has no certificates at entry " + entry2.getName());
                                        }
                                        Signature[] entrySigs = ApkSignatureVerifier.convertToSignatures(entryCerts);
                                        if (!Signature.areExactMatch(lastSigs3, entrySigs)) {
                                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES, "Package " + apkPath + " has mismatched certificates at entry " + entry2.getName());
                                        }
                                    } catch (PackageParser.PackageParserException e) {
                                        synchronized (C1VerificationData.this.objWaitAll) {
                                            C1VerificationData.this.exceptionFlag = PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION;
                                            C1VerificationData.this.exception = e;
                                        }
                                    } catch (GeneralSecurityException e2) {
                                        synchronized (C1VerificationData.this.objWaitAll) {
                                            C1VerificationData.this.exceptionFlag = PackageManager.INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING;
                                            C1VerificationData.this.exception = e2;
                                        }
                                    }
                                }
                            };
                            vData = vData;
                            synchronized (vData.objWaitAll) {
                                if (vData.exceptionFlag == 0) {
                                    verificationExecutor2.execute(verifyTask);
                                }
                            }
                            verificationExecutor = verificationExecutor2;
                            manifestEntry = manifestEntry2;
                            toVerify = toVerify2;
                            lastSigs2 = lastSigs3;
                        }
                        ThreadPoolExecutor verificationExecutor3 = verificationExecutor;
                        lastSigs = lastSigs2;
                        vData.wait = true;
                        verificationExecutor3.shutdown();
                        while (vData.wait) {
                            try {
                                if (vData.exceptionFlag != 0 && !vData.shutDown) {
                                    Slog.m50w(TAG, "verifyV1 Exception " + vData.exceptionFlag);
                                    verificationExecutor3.shutdownNow();
                                    vData.shutDown = true;
                                }
                                vData.wait = !verificationExecutor3.awaitTermination(50L, TimeUnit.MILLISECONDS);
                            } catch (InterruptedException e) {
                                Slog.m50w(TAG, "VerifyV1 interrupted while awaiting all threads done...");
                            }
                        }
                        if (vData.exceptionFlag != 0) {
                            throw new PackageParser.PackageParserException(vData.exceptionFlag, "Failed to collect certificates from " + apkPath, vData.exception);
                        }
                    } else {
                        lastSigs = lastSigs2;
                    }
                    while (true) {
                        if (i >= objectNumber) {
                            return new PackageParser.SigningDetails(lastSigs, 1);
                        }
                    }
                } catch (GeneralSecurityException e2) {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING, "Failed to collect certificates from " + apkPath, e2);
                }
            } catch (IOException | RuntimeException e3) {
                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath, e3);
            }
        } finally {
            if (sIsPerfLockAcquired && sPerfBoost != null) {
                sPerfBoost.perfLockRelease();
                sIsPerfLockAcquired = false;
                Slog.m58d(TAG, "Perflock released for PackageInstall ");
            }
            strictJarFiles.clear();
            Trace.traceEnd(262144L);
            while (true) {
                int i6 = r16;
                if (i6 >= objectNumber) {
                    break;
                }
                closeQuietly(jarFile[i6]);
                r16 = i6 + 1;
            }
        }
    }

    /* renamed from: android.util.apk.ApkSignatureVerifier$1VerificationData  reason: invalid class name */
    /* loaded from: classes4.dex */
    class C1VerificationData {
        public Exception exception;
        public int exceptionFlag;
        public int index;
        public Object objWaitAll;
        public boolean shutDown;
        public boolean wait;

        C1VerificationData() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Certificate[][] loadCertificates(StrictJarFile jarFile, ZipEntry entry) throws PackageParser.PackageParserException {
        InputStream is = null;
        try {
            try {
                is = jarFile.getInputStream(entry);
                readFullyIgnoringContents(is);
                return jarFile.getCertificateChains(entry);
            } catch (IOException | RuntimeException e) {
                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION, "Failed reading " + entry.getName() + " in " + jarFile, e);
            }
        } finally {
            IoUtils.closeQuietly(is);
        }
    }

    private static void readFullyIgnoringContents(InputStream in) throws IOException {
        byte[] buffer = sBuffer.getAndSet(null);
        if (buffer == null) {
            buffer = new byte[4096];
        }
        int n = 0;
        while (true) {
            int n2 = in.read(buffer, 0, buffer.length);
            if (n2 != -1) {
                n += n2;
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
        if (minSignatureSchemeVersion > 3) {
            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
        }
        Trace.traceBegin(262144L, "certsOnlyV3");
        try {
            try {
                ApkSignatureSchemeV3Verifier.VerifiedSigner vSigner = ApkSignatureSchemeV3Verifier.unsafeGetCertsWithoutVerification(apkPath);
                Certificate[][] signerCerts = {vSigner.certs};
                Signature[] signerSigs = convertToSignatures(signerCerts);
                Signature[] pastSignerSigs = null;
                if (vSigner.por != null) {
                    pastSignerSigs = new Signature[vSigner.por.certs.size()];
                    for (int i = 0; i < pastSignerSigs.length; i++) {
                        pastSignerSigs[i] = new Signature(vSigner.por.certs.get(i).getEncoded());
                        pastSignerSigs[i].setFlags(vSigner.por.flagsList.get(i).intValue());
                    }
                }
                PackageParser.SigningDetails signingDetails = new PackageParser.SigningDetails(signerSigs, 3, pastSignerSigs);
                Trace.traceEnd(262144L);
                return signingDetails;
            } catch (SignatureNotFoundException e) {
                if (minSignatureSchemeVersion >= 3) {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v3 signature in package " + apkPath, e);
                }
                Trace.traceEnd(262144L);
                if (minSignatureSchemeVersion > 2) {
                    throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                }
                Trace.traceBegin(262144L, "certsOnlyV2");
                try {
                    try {
                        Signature[] signerSigs2 = convertToSignatures(ApkSignatureSchemeV2Verifier.unsafeGetCertsWithoutVerification(apkPath));
                        PackageParser.SigningDetails signingDetails2 = new PackageParser.SigningDetails(signerSigs2, 2);
                        Trace.traceEnd(262144L);
                        return signingDetails2;
                    } catch (SignatureNotFoundException e2) {
                        if (minSignatureSchemeVersion >= 2) {
                            throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No APK Signature Scheme v2 signature in package " + apkPath, e2);
                        }
                        Trace.traceEnd(262144L);
                        if (minSignatureSchemeVersion <= 1) {
                            return verifyV1Signature(apkPath, false);
                        }
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "No signature found in package of version " + minSignatureSchemeVersion + " or newer for package " + apkPath);
                    } catch (Exception e3) {
                        throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v2", e3);
                    }
                } catch (Throwable th) {
                    Trace.traceEnd(262144L);
                    throw th;
                }
            } catch (Exception e4) {
                throw new PackageParser.PackageParserException(PackageManager.INSTALL_PARSE_FAILED_NO_CERTIFICATES, "Failed to collect certificates from " + apkPath + " using APK Signature Scheme v3", e4);
            }
        } catch (Throwable th2) {
            Trace.traceEnd(262144L);
            throw th2;
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

    /* loaded from: classes4.dex */
    public static class Result {
        public final Certificate[][] certs;
        public final int signatureSchemeVersion;
        public final Signature[] sigs;

        public Result(Certificate[][] certs, Signature[] sigs, int signingVersion) {
            this.certs = certs;
            this.sigs = sigs;
            this.signatureSchemeVersion = signingVersion;
        }
    }
}
