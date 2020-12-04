package android.util.apk;

import android.security.keystore.KeyProperties;
import android.util.ArrayMap;
import android.util.Pair;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import java.util.Arrays;
import java.util.Map;

final class ApkSigningBlockUtils {
    private static final long APK_SIG_BLOCK_MAGIC_HI = 3617552046287187010L;
    private static final long APK_SIG_BLOCK_MAGIC_LO = 2334950737559900225L;
    private static final int APK_SIG_BLOCK_MIN_SIZE = 32;
    private static final int CHUNK_SIZE_BYTES = 1048576;
    static final int CONTENT_DIGEST_CHUNKED_SHA256 = 1;
    static final int CONTENT_DIGEST_CHUNKED_SHA512 = 2;
    static final int CONTENT_DIGEST_VERITY_CHUNKED_SHA256 = 3;
    static final int SIGNATURE_DSA_WITH_SHA256 = 769;
    static final int SIGNATURE_ECDSA_WITH_SHA256 = 513;
    static final int SIGNATURE_ECDSA_WITH_SHA512 = 514;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA256 = 259;
    static final int SIGNATURE_RSA_PKCS1_V1_5_WITH_SHA512 = 260;
    static final int SIGNATURE_RSA_PSS_WITH_SHA256 = 257;
    static final int SIGNATURE_RSA_PSS_WITH_SHA512 = 258;
    static final int SIGNATURE_VERITY_DSA_WITH_SHA256 = 1061;
    static final int SIGNATURE_VERITY_ECDSA_WITH_SHA256 = 1059;
    static final int SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256 = 1057;

    private ApkSigningBlockUtils() {
    }

    static SignatureInfo findSignature(RandomAccessFile apk, int blockId) throws IOException, SignatureNotFoundException {
        RandomAccessFile randomAccessFile = apk;
        Pair<ByteBuffer, Long> eocdAndOffsetInFile = getEocd(apk);
        ByteBuffer eocd = (ByteBuffer) eocdAndOffsetInFile.first;
        long eocdOffset = ((Long) eocdAndOffsetInFile.second).longValue();
        if (!ZipUtils.isZip64EndOfCentralDirectoryLocatorPresent(randomAccessFile, eocdOffset)) {
            long centralDirOffset = getCentralDirOffset(eocd, eocdOffset);
            Pair<ByteBuffer, Long> apkSigningBlockAndOffsetInFile = findApkSigningBlock(randomAccessFile, centralDirOffset);
            ByteBuffer apkSigningBlock = (ByteBuffer) apkSigningBlockAndOffsetInFile.first;
            ByteBuffer byteBuffer = apkSigningBlock;
            Pair<ByteBuffer, Long> pair = apkSigningBlockAndOffsetInFile;
            return new SignatureInfo(findApkSignatureSchemeBlock(apkSigningBlock, blockId), ((Long) apkSigningBlockAndOffsetInFile.second).longValue(), centralDirOffset, eocdOffset, eocd);
        }
        throw new SignatureNotFoundException("ZIP64 APK not supported");
    }

    static void verifyIntegrity(Map<Integer, byte[]> expectedDigests, RandomAccessFile apk, SignatureInfo signatureInfo) throws SecurityException {
        if (!expectedDigests.isEmpty()) {
            boolean neverVerified = true;
            Map<Integer, byte[]> expected1MbChunkDigests = new ArrayMap<>();
            if (expectedDigests.containsKey(1)) {
                expected1MbChunkDigests.put(1, expectedDigests.get(1));
            }
            if (expectedDigests.containsKey(2)) {
                expected1MbChunkDigests.put(2, expectedDigests.get(2));
            }
            if (!expected1MbChunkDigests.isEmpty()) {
                try {
                    verifyIntegrityFor1MbChunkBasedAlgorithm(expected1MbChunkDigests, apk.getFD(), signatureInfo);
                    neverVerified = false;
                } catch (IOException e) {
                    throw new SecurityException("Cannot get FD", e);
                }
            }
            if (expectedDigests.containsKey(3)) {
                verifyIntegrityForVerityBasedAlgorithm(expectedDigests.get(3), apk, signatureInfo);
                neverVerified = false;
            }
            if (neverVerified) {
                throw new SecurityException("No known digest exists for integrity check");
            }
            return;
        }
        throw new SecurityException("No digests provided");
    }

    private static void verifyIntegrityFor1MbChunkBasedAlgorithm(Map<Integer, byte[]> expectedDigests, FileDescriptor apkFileDescriptor, SignatureInfo signatureInfo) throws SecurityException {
        SignatureInfo signatureInfo2 = signatureInfo;
        DataSource beforeApkSigningBlock = new MemoryMappedFileDataSource(apkFileDescriptor, 0, signatureInfo2.apkSigningBlockOffset);
        DataSource centralDir = new MemoryMappedFileDataSource(apkFileDescriptor, signatureInfo2.centralDirOffset, signatureInfo2.eocdOffset - signatureInfo2.centralDirOffset);
        ByteBuffer eocdBuf = signatureInfo2.eocd.duplicate();
        eocdBuf.order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.setZipEocdCentralDirectoryOffset(eocdBuf, signatureInfo2.apkSigningBlockOffset);
        DataSource eocd = new ByteBufferDataSource(eocdBuf);
        int[] digestAlgorithms = new int[expectedDigests.size()];
        int digestAlgorithmCount = 0;
        for (Integer intValue : expectedDigests.keySet()) {
            digestAlgorithms[digestAlgorithmCount] = intValue.intValue();
            digestAlgorithmCount++;
        }
        try {
            int i = 0;
            byte[][] actualDigests = computeContentDigestsPer1MbChunk(digestAlgorithms, new DataSource[]{beforeApkSigningBlock, centralDir, eocd});
            while (i < digestAlgorithms.length) {
                int digestAlgorithm = digestAlgorithms[i];
                if (MessageDigest.isEqual(expectedDigests.get(Integer.valueOf(digestAlgorithm)), actualDigests[i])) {
                    i++;
                } else {
                    throw new SecurityException(getContentDigestAlgorithmJcaDigestAlgorithm(digestAlgorithm) + " digest of contents did not verify");
                }
            }
            Map<Integer, byte[]> map = expectedDigests;
        } catch (DigestException e) {
            Map<Integer, byte[]> map2 = expectedDigests;
            throw new SecurityException("Failed to compute digest(s) of contents", e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x016b, code lost:
        r25 = r3;
        r30 = r5;
        r28 = r6;
        r31 = r8;
        r33 = r12;
        r32 = r13;
        r27 = r14;
        r6 = r21;
        r15 = r15 + 1;
        r0 = r0 + 1;
        r6 = r28;
        r2 = r37;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[][] computeContentDigestsPer1MbChunk(int[] r36, android.util.apk.DataSource[] r37) throws java.security.DigestException {
        /*
            r1 = r36
            r2 = r37
            r3 = 0
            int r0 = r2.length
            r5 = 0
            r6 = r3
            r3 = r5
        L_0x000a:
            if (r3 >= r0) goto L_0x001a
            r4 = r2[r3]
            long r8 = r4.size()
            long r8 = getChunkCount(r8)
            long r6 = r6 + r8
            int r3 = r3 + 1
            goto L_0x000a
        L_0x001a:
            r3 = 2097151(0x1fffff, double:1.0361303E-317)
            int r0 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x01d2
            int r3 = (int) r6
            int r0 = r1.length
            byte[][] r4 = new byte[r0][]
            r0 = r5
        L_0x0026:
            int r8 = r1.length
            r9 = 5
            r10 = 1
            if (r0 >= r8) goto L_0x0042
            r8 = r1[r0]
            int r11 = getContentDigestAlgorithmOutputSizeBytes(r8)
            int r12 = r3 * r11
            int r12 = r12 + r9
            byte[] r9 = new byte[r12]
            r12 = 90
            r9[r5] = r12
            setUnsignedInt32LittleEndian(r3, r9, r10)
            r4[r0] = r9
            int r0 = r0 + 1
            goto L_0x0026
        L_0x0042:
            byte[] r8 = new byte[r9]
            r0 = -91
            r8[r5] = r0
            r11 = 0
            int r0 = r1.length
            java.security.MessageDigest[] r12 = new java.security.MessageDigest[r0]
            r0 = r5
        L_0x004d:
            r13 = r0
            int r0 = r1.length
            if (r13 >= r0) goto L_0x007a
            r0 = r1[r13]
            java.lang.String r0 = getContentDigestAlgorithmJcaDigestAlgorithm(r0)
            r14 = r0
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r14)     // Catch:{ NoSuchAlgorithmException -> 0x0062 }
            r12[r13] = r0     // Catch:{ NoSuchAlgorithmException -> 0x0062 }
            int r0 = r13 + 1
            goto L_0x004d
        L_0x0062:
            r0 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r14)
            java.lang.String r10 = " digest not supported"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r5.<init>(r9, r0)
            throw r5
        L_0x007a:
            android.util.apk.ApkSigningBlockUtils$MultipleDigestDataDigester r0 = new android.util.apk.ApkSigningBlockUtils$MultipleDigestDataDigester
            r0.<init>(r12)
            r13 = r0
            r0 = 0
            int r14 = r2.length
            r15 = r0
            r0 = r5
        L_0x0084:
            if (r0 >= r14) goto L_0x018b
            r5 = r2[r0]
            r17 = 0
            long r19 = r5.size()
            r21 = r17
        L_0x0090:
            r23 = r19
            r17 = 0
            r9 = r23
            int r17 = (r9 > r17 ? 1 : (r9 == r17 ? 0 : -1))
            if (r17 <= 0) goto L_0x016b
            r25 = r3
            r2 = 1048576(0x100000, double:5.180654E-318)
            long r2 = java.lang.Math.min(r9, r2)
            int r2 = (int) r2
            r3 = 1
            setUnsignedInt32LittleEndian(r2, r8, r3)
            r17 = 0
        L_0x00aa:
            r26 = r17
            int r3 = r12.length
            r27 = r14
            r14 = r26
            if (r14 >= r3) goto L_0x00be
            r3 = r12[r14]
            r3.update(r8)
            int r17 = r14 + 1
            r14 = r27
            r3 = 1
            goto L_0x00aa
        L_0x00be:
            r28 = r6
            r6 = r21
            r5.feedIntoDataDigester(r13, r6, r2)     // Catch:{ IOException -> 0x0141 }
            r3 = 0
        L_0x00c7:
            int r14 = r1.length
            if (r3 >= r14) goto L_0x011b
            r14 = r1[r3]
            r30 = r5
            r5 = r4[r3]
            r31 = r8
            int r8 = getContentDigestAlgorithmOutputSizeBytes(r14)
            r32 = r13
            r13 = r12[r3]
            int r17 = r11 * r8
            r33 = r12
            r18 = 5
            int r12 = r17 + 5
            int r12 = r13.digest(r5, r12, r8)
            if (r12 != r8) goto L_0x00f4
            int r3 = r3 + 1
            r5 = r30
            r8 = r31
            r13 = r32
            r12 = r33
            goto L_0x00c7
        L_0x00f4:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r34 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r35 = r5
            java.lang.String r5 = "Unexpected output size of "
            r3.append(r5)
            java.lang.String r5 = r13.getAlgorithm()
            r3.append(r5)
            java.lang.String r5 = " digest: "
            r3.append(r5)
            r3.append(r12)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x011b:
            r30 = r5
            r31 = r8
            r33 = r12
            r32 = r13
            r18 = 5
            long r12 = (long) r2
            long r21 = r6 + r12
            long r5 = (long) r2
            long r19 = r9 - r5
            int r11 = r11 + 1
            r9 = r18
            r3 = r25
            r14 = r27
            r6 = r28
            r5 = r30
            r13 = r32
            r12 = r33
            r2 = r37
            r10 = 1
            goto L_0x0090
        L_0x0141:
            r0 = move-exception
            r3 = r0
            r30 = r5
            r31 = r8
            r33 = r12
            r32 = r13
            r0 = r3
            java.security.DigestException r3 = new java.security.DigestException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "Failed to digest chunk #"
            r5.append(r8)
            r5.append(r11)
            java.lang.String r8 = " of section #"
            r5.append(r8)
            r5.append(r15)
            java.lang.String r5 = r5.toString()
            r3.<init>(r5, r0)
            throw r3
        L_0x016b:
            r25 = r3
            r30 = r5
            r28 = r6
            r31 = r8
            r33 = r12
            r32 = r13
            r27 = r14
            r6 = r21
            r18 = 5
            int r15 = r15 + 1
            int r0 = r0 + 1
            r9 = r18
            r6 = r28
            r2 = r37
            r5 = 0
            r10 = 1
            goto L_0x0084
        L_0x018b:
            r25 = r3
            r28 = r6
            r31 = r8
            r33 = r12
            r32 = r13
            int r0 = r1.length
            byte[][] r2 = new byte[r0][]
            r16 = 0
        L_0x019a:
            r3 = r16
            int r0 = r1.length
            if (r3 >= r0) goto L_0x01d1
            r5 = r1[r3]
            r6 = r4[r3]
            java.lang.String r0 = getContentDigestAlgorithmJcaDigestAlgorithm(r5)
            r7 = r0
            java.security.MessageDigest r0 = java.security.MessageDigest.getInstance(r7)     // Catch:{ NoSuchAlgorithmException -> 0x01b7 }
            byte[] r8 = r0.digest(r6)
            r2[r3] = r8
            int r16 = r3 + 1
            goto L_0x019a
        L_0x01b7:
            r0 = move-exception
            r8 = r0
            r0 = r8
            java.lang.RuntimeException r8 = new java.lang.RuntimeException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            java.lang.String r10 = " digest not supported"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9, r0)
            throw r8
        L_0x01d1:
            return r2
        L_0x01d2:
            r28 = r6
            java.security.DigestException r0 = new java.security.DigestException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Too many chunks: "
            r2.append(r3)
            r3 = r28
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.util.apk.ApkSigningBlockUtils.computeContentDigestsPer1MbChunk(int[], android.util.apk.DataSource[]):byte[][]");
    }

    static byte[] parseVerityDigestAndVerifySourceLength(byte[] data, long fileSize, SignatureInfo signatureInfo) throws SecurityException {
        if (data.length == 32 + 8) {
            ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
            buffer.position(32);
            if (buffer.getLong() == fileSize - (signatureInfo.centralDirOffset - signatureInfo.apkSigningBlockOffset)) {
                return Arrays.copyOfRange(data, 0, 32);
            }
            throw new SecurityException("APK content size did not verify");
        }
        throw new SecurityException("Verity digest size is wrong: " + data.length);
    }

    private static void verifyIntegrityForVerityBasedAlgorithm(byte[] expectedDigest, RandomAccessFile apk, SignatureInfo signatureInfo) throws SecurityException {
        try {
            if (!Arrays.equals(parseVerityDigestAndVerifySourceLength(expectedDigest, apk.length(), signatureInfo), VerityBuilder.generateApkVerityTree(apk, signatureInfo, new ByteBufferFactory() {
                public ByteBuffer create(int capacity) {
                    return ByteBuffer.allocate(capacity);
                }
            }).rootHash)) {
                throw new SecurityException("APK verity digest of contents did not verify");
            }
        } catch (IOException | DigestException | NoSuchAlgorithmException e) {
            throw new SecurityException("Error during verification", e);
        }
    }

    static Pair<ByteBuffer, Long> getEocd(RandomAccessFile apk) throws IOException, SignatureNotFoundException {
        Pair<ByteBuffer, Long> eocdAndOffsetInFile = ZipUtils.findZipEndOfCentralDirectoryRecord(apk);
        if (eocdAndOffsetInFile != null) {
            return eocdAndOffsetInFile;
        }
        throw new SignatureNotFoundException("Not an APK file: ZIP End of Central Directory record not found");
    }

    static long getCentralDirOffset(ByteBuffer eocd, long eocdOffset) throws SignatureNotFoundException {
        long centralDirOffset = ZipUtils.getZipEocdCentralDirectoryOffset(eocd);
        if (centralDirOffset > eocdOffset) {
            throw new SignatureNotFoundException("ZIP Central Directory offset out of range: " + centralDirOffset + ". ZIP End of Central Directory offset: " + eocdOffset);
        } else if (centralDirOffset + ZipUtils.getZipEocdCentralDirectorySizeBytes(eocd) == eocdOffset) {
            return centralDirOffset;
        } else {
            throw new SignatureNotFoundException("ZIP Central Directory is not immediately followed by End of Central Directory");
        }
    }

    private static long getChunkCount(long inputSizeBytes) {
        return ((inputSizeBytes + 1048576) - 1) / 1048576;
    }

    static int compareSignatureAlgorithm(int sigAlgorithm1, int sigAlgorithm2) {
        return compareContentDigestAlgorithm(getSignatureAlgorithmContentDigestAlgorithm(sigAlgorithm1), getSignatureAlgorithmContentDigestAlgorithm(sigAlgorithm2));
    }

    private static int compareContentDigestAlgorithm(int digestAlgorithm1, int digestAlgorithm2) {
        switch (digestAlgorithm1) {
            case 1:
                switch (digestAlgorithm2) {
                    case 1:
                        return 0;
                    case 2:
                    case 3:
                        return -1;
                    default:
                        throw new IllegalArgumentException("Unknown digestAlgorithm2: " + digestAlgorithm2);
                }
            case 2:
                switch (digestAlgorithm2) {
                    case 1:
                    case 3:
                        return 1;
                    case 2:
                        return 0;
                    default:
                        throw new IllegalArgumentException("Unknown digestAlgorithm2: " + digestAlgorithm2);
                }
            case 3:
                switch (digestAlgorithm2) {
                    case 1:
                        return 1;
                    case 2:
                        return -1;
                    case 3:
                        return 0;
                    default:
                        throw new IllegalArgumentException("Unknown digestAlgorithm2: " + digestAlgorithm2);
                }
            default:
                throw new IllegalArgumentException("Unknown digestAlgorithm1: " + digestAlgorithm1);
        }
    }

    static int getSignatureAlgorithmContentDigestAlgorithm(int sigAlgorithm) {
        if (sigAlgorithm == 769) {
            return 1;
        }
        if (sigAlgorithm == SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256 || sigAlgorithm == SIGNATURE_VERITY_ECDSA_WITH_SHA256 || sigAlgorithm == 1061) {
            return 3;
        }
        switch (sigAlgorithm) {
            case 257:
            case 259:
                return 1;
            case 258:
            case 260:
                return 2;
            default:
                switch (sigAlgorithm) {
                    case 513:
                        return 1;
                    case 514:
                        return 2;
                    default:
                        throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString((long) (sigAlgorithm & -1)));
                }
        }
    }

    static String getContentDigestAlgorithmJcaDigestAlgorithm(int digestAlgorithm) {
        switch (digestAlgorithm) {
            case 1:
            case 3:
                return KeyProperties.DIGEST_SHA256;
            case 2:
                return KeyProperties.DIGEST_SHA512;
            default:
                throw new IllegalArgumentException("Unknown content digest algorthm: " + digestAlgorithm);
        }
    }

    private static int getContentDigestAlgorithmOutputSizeBytes(int digestAlgorithm) {
        switch (digestAlgorithm) {
            case 1:
            case 3:
                return 32;
            case 2:
                return 64;
            default:
                throw new IllegalArgumentException("Unknown content digest algorthm: " + digestAlgorithm);
        }
    }

    static String getSignatureAlgorithmJcaKeyAlgorithm(int sigAlgorithm) {
        if (sigAlgorithm == 769) {
            return "DSA";
        }
        if (sigAlgorithm == SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256) {
            return KeyProperties.KEY_ALGORITHM_RSA;
        }
        if (sigAlgorithm == SIGNATURE_VERITY_ECDSA_WITH_SHA256) {
            return KeyProperties.KEY_ALGORITHM_EC;
        }
        if (sigAlgorithm == 1061) {
            return "DSA";
        }
        switch (sigAlgorithm) {
            case 257:
            case 258:
            case 259:
            case 260:
                return KeyProperties.KEY_ALGORITHM_RSA;
            default:
                switch (sigAlgorithm) {
                    case 513:
                    case 514:
                        return KeyProperties.KEY_ALGORITHM_EC;
                    default:
                        throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString((long) (sigAlgorithm & -1)));
                }
        }
    }

    static Pair<String, ? extends AlgorithmParameterSpec> getSignatureAlgorithmJcaSignatureAlgorithm(int sigAlgorithm) {
        if (sigAlgorithm != 769) {
            if (sigAlgorithm != SIGNATURE_VERITY_RSA_PKCS1_V1_5_WITH_SHA256) {
                if (sigAlgorithm != SIGNATURE_VERITY_ECDSA_WITH_SHA256) {
                    if (sigAlgorithm != 1061) {
                        switch (sigAlgorithm) {
                            case 257:
                                return Pair.create("SHA256withRSA/PSS", new PSSParameterSpec(KeyProperties.DIGEST_SHA256, "MGF1", MGF1ParameterSpec.SHA256, 32, 1));
                            case 258:
                                return Pair.create("SHA512withRSA/PSS", new PSSParameterSpec(KeyProperties.DIGEST_SHA512, "MGF1", MGF1ParameterSpec.SHA512, 64, 1));
                            case 259:
                                break;
                            case 260:
                                return Pair.create("SHA512withRSA", null);
                            default:
                                switch (sigAlgorithm) {
                                    case 513:
                                        break;
                                    case 514:
                                        return Pair.create("SHA512withECDSA", null);
                                    default:
                                        throw new IllegalArgumentException("Unknown signature algorithm: 0x" + Long.toHexString((long) (sigAlgorithm & -1)));
                                }
                        }
                    }
                }
                return Pair.create("SHA256withECDSA", null);
            }
            return Pair.create("SHA256withRSA", null);
        }
        return Pair.create("SHA256withDSA", null);
    }

    /* JADX INFO: finally extract failed */
    static ByteBuffer sliceFromTo(ByteBuffer source, int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("start: " + start);
        } else if (end >= start) {
            int capacity = source.capacity();
            if (end <= source.capacity()) {
                int originalLimit = source.limit();
                int originalPosition = source.position();
                try {
                    source.position(0);
                    source.limit(end);
                    source.position(start);
                    ByteBuffer result = source.slice();
                    result.order(source.order());
                    source.position(0);
                    source.limit(originalLimit);
                    source.position(originalPosition);
                    return result;
                } catch (Throwable th) {
                    source.position(0);
                    source.limit(originalLimit);
                    source.position(originalPosition);
                    throw th;
                }
            } else {
                throw new IllegalArgumentException("end > capacity: " + end + " > " + capacity);
            }
        } else {
            throw new IllegalArgumentException("end < start: " + end + " < " + start);
        }
    }

    static ByteBuffer getByteBuffer(ByteBuffer source, int size) throws BufferUnderflowException {
        if (size >= 0) {
            int originalLimit = source.limit();
            int position = source.position();
            int limit = position + size;
            if (limit < position || limit > originalLimit) {
                throw new BufferUnderflowException();
            }
            source.limit(limit);
            try {
                ByteBuffer result = source.slice();
                result.order(source.order());
                source.position(limit);
                return result;
            } finally {
                source.limit(originalLimit);
            }
        } else {
            throw new IllegalArgumentException("size: " + size);
        }
    }

    static ByteBuffer getLengthPrefixedSlice(ByteBuffer source) throws IOException {
        if (source.remaining() >= 4) {
            int len = source.getInt();
            if (len < 0) {
                throw new IllegalArgumentException("Negative length");
            } else if (len <= source.remaining()) {
                return getByteBuffer(source, len);
            } else {
                throw new IOException("Length-prefixed field longer than remaining buffer. Field length: " + len + ", remaining: " + source.remaining());
            }
        } else {
            throw new IOException("Remaining buffer too short to contain length of length-prefixed field. Remaining: " + source.remaining());
        }
    }

    static byte[] readLengthPrefixedByteArray(ByteBuffer buf) throws IOException {
        int len = buf.getInt();
        if (len < 0) {
            throw new IOException("Negative length");
        } else if (len <= buf.remaining()) {
            byte[] result = new byte[len];
            buf.get(result);
            return result;
        } else {
            throw new IOException("Underflow while reading length-prefixed value. Length: " + len + ", available: " + buf.remaining());
        }
    }

    static void setUnsignedInt32LittleEndian(int value, byte[] result, int offset) {
        result[offset] = (byte) (value & 255);
        result[offset + 1] = (byte) ((value >>> 8) & 255);
        result[offset + 2] = (byte) ((value >>> 16) & 255);
        result[offset + 3] = (byte) ((value >>> 24) & 255);
    }

    static Pair<ByteBuffer, Long> findApkSigningBlock(RandomAccessFile apk, long centralDirOffset) throws IOException, SignatureNotFoundException {
        if (centralDirOffset >= 32) {
            ByteBuffer footer = ByteBuffer.allocate(24);
            footer.order(ByteOrder.LITTLE_ENDIAN);
            apk.seek(centralDirOffset - ((long) footer.capacity()));
            apk.readFully(footer.array(), footer.arrayOffset(), footer.capacity());
            if (footer.getLong(8) == APK_SIG_BLOCK_MAGIC_LO && footer.getLong(16) == APK_SIG_BLOCK_MAGIC_HI) {
                long apkSigBlockSizeInFooter = footer.getLong(0);
                if (apkSigBlockSizeInFooter < ((long) footer.capacity()) || apkSigBlockSizeInFooter > 2147483639) {
                    throw new SignatureNotFoundException("APK Signing Block size out of range: " + apkSigBlockSizeInFooter);
                }
                int totalSize = (int) (8 + apkSigBlockSizeInFooter);
                long apkSigBlockOffset = centralDirOffset - ((long) totalSize);
                if (apkSigBlockOffset >= 0) {
                    ByteBuffer apkSigBlock = ByteBuffer.allocate(totalSize);
                    apkSigBlock.order(ByteOrder.LITTLE_ENDIAN);
                    apk.seek(apkSigBlockOffset);
                    apk.readFully(apkSigBlock.array(), apkSigBlock.arrayOffset(), apkSigBlock.capacity());
                    long apkSigBlockSizeInHeader = apkSigBlock.getLong(0);
                    if (apkSigBlockSizeInHeader == apkSigBlockSizeInFooter) {
                        return Pair.create(apkSigBlock, Long.valueOf(apkSigBlockOffset));
                    }
                    throw new SignatureNotFoundException("APK Signing Block sizes in header and footer do not match: " + apkSigBlockSizeInHeader + " vs " + apkSigBlockSizeInFooter);
                }
                throw new SignatureNotFoundException("APK Signing Block offset out of range: " + apkSigBlockOffset);
            }
            throw new SignatureNotFoundException("No APK Signing Block before ZIP Central Directory");
        }
        throw new SignatureNotFoundException("APK too small for APK Signing Block. ZIP Central Directory offset: " + centralDirOffset);
    }

    static ByteBuffer findApkSignatureSchemeBlock(ByteBuffer apkSigningBlock, int blockId) throws SignatureNotFoundException {
        checkByteOrderLittleEndian(apkSigningBlock);
        ByteBuffer pairs = sliceFromTo(apkSigningBlock, 8, apkSigningBlock.capacity() - 24);
        int entryCount = 0;
        while (pairs.hasRemaining()) {
            entryCount++;
            if (pairs.remaining() >= 8) {
                long lenLong = pairs.getLong();
                if (lenLong < 4 || lenLong > 2147483647L) {
                    throw new SignatureNotFoundException("APK Signing Block entry #" + entryCount + " size out of range: " + lenLong);
                }
                int len = (int) lenLong;
                int nextEntryPos = pairs.position() + len;
                if (len > pairs.remaining()) {
                    throw new SignatureNotFoundException("APK Signing Block entry #" + entryCount + " size out of range: " + len + ", available: " + pairs.remaining());
                } else if (pairs.getInt() == blockId) {
                    return getByteBuffer(pairs, len - 4);
                } else {
                    pairs.position(nextEntryPos);
                }
            } else {
                throw new SignatureNotFoundException("Insufficient data to read size of APK Signing Block entry #" + entryCount);
            }
        }
        throw new SignatureNotFoundException("No block with ID " + blockId + " in APK Signing Block.");
    }

    private static void checkByteOrderLittleEndian(ByteBuffer buffer) {
        if (buffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    private static class MultipleDigestDataDigester implements DataDigester {
        private final MessageDigest[] mMds;

        MultipleDigestDataDigester(MessageDigest[] mds) {
            this.mMds = mds;
        }

        public void consume(ByteBuffer buffer) {
            ByteBuffer buffer2 = buffer.slice();
            for (MessageDigest md : this.mMds) {
                buffer2.position(0);
                md.update(buffer2);
            }
        }
    }
}
