package com.android.internal.p016os;

import android.telephony.SmsManager;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: com.android.internal.os.ZygoteArguments */
/* loaded from: classes4.dex */
class ZygoteArguments {
    boolean mAbiListQuery;
    String[] mApiBlacklistExemptions;
    String mAppDataDir;
    boolean mCapabilitiesSpecified;
    long mEffectiveCapabilities;
    boolean mGidSpecified;
    int[] mGids;
    String mInstructionSet;
    String mInvokeWith;
    String mNiceName;
    String mPackageName;
    long mPermittedCapabilities;
    boolean mPidQuery;
    String mPreloadApp;
    boolean mPreloadDefault;
    String mPreloadPackage;
    String mPreloadPackageCacheKey;
    String mPreloadPackageLibFileName;
    String mPreloadPackageLibs;
    ArrayList<int[]> mRLimits;
    String[] mRemainingArgs;
    int mRuntimeFlags;
    String mSeInfo;
    boolean mSeInfoSpecified;
    boolean mStartChildZygote;
    int mTargetSdkVersion;
    boolean mTargetSdkVersionSpecified;
    boolean mUidSpecified;
    boolean mUsapPoolEnabled;
    int mUid = 0;
    int mGid = 0;
    int mMountExternal = 0;
    boolean mUsapPoolStatusSpecified = false;
    int mHiddenApiAccessLogSampleRate = -1;
    int mHiddenApiAccessStatslogSampleRate = -1;

    ZygoteArguments(String[] args) throws IllegalArgumentException {
        parseArgs(args);
    }

    /* JADX WARN: Code restructure failed: missing block: B:169:0x0386, code lost:
        if (r10.mAbiListQuery != false) goto L249;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x038a, code lost:
        if (r10.mPidQuery == false) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x038f, code lost:
        if (r10.mPreloadPackage == null) goto L238;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0393, code lost:
        if ((r11.length - r1) > 0) goto L221;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x039d, code lost:
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-package.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x03a0, code lost:
        if (r10.mPreloadApp == null) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x03a4, code lost:
        if ((r11.length - r1) > 0) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x03ae, code lost:
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-app.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x03af, code lost:
        if (r0 == 0) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x03b1, code lost:
        if (r3 == false) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x03b3, code lost:
        r10.mRemainingArgs = new java.lang.String[r11.length - r1];
        java.lang.System.arraycopy(r11, r1, r10.mRemainingArgs, 0, r10.mRemainingArgs.length);
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x03da, code lost:
        throw new java.lang.IllegalArgumentException("Unexpected argument : " + r11[r1]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x03dd, code lost:
        if ((r11.length - r1) > 0) goto L251;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x03e1, code lost:
        if (r10.mStartChildZygote == false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x03e3, code lost:
        r2 = false;
        r4 = r10.mRemainingArgs;
        r6 = r4.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x03e7, code lost:
        if (r5 >= r6) goto L236;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x03f1, code lost:
        if (r4[r5].startsWith(com.android.internal.p016os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG) == false) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x03f3, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x03f5, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x03f8, code lost:
        if (r2 == false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0402, code lost:
        throw new java.lang.IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x0403, code lost:
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x040b, code lost:
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --query-abi-list.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseArgs(String[] args) throws IllegalArgumentException {
        boolean seenRuntimeArgs = false;
        int curArg = 0;
        int curArg2 = 1;
        while (true) {
            int i = 0;
            if (curArg >= args.length) {
                break;
            }
            String arg = args[curArg];
            if (arg.equals("--")) {
                curArg++;
                break;
            }
            if (arg.startsWith("--setuid=")) {
                if (this.mUidSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mUidSpecified = true;
                this.mUid = Integer.parseInt(arg.substring(arg.indexOf(61) + 1));
            } else if (arg.startsWith("--setgid=")) {
                if (this.mGidSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mGidSpecified = true;
                this.mGid = Integer.parseInt(arg.substring(arg.indexOf(61) + 1));
            } else if (arg.startsWith("--target-sdk-version=")) {
                if (this.mTargetSdkVersionSpecified) {
                    throw new IllegalArgumentException("Duplicate target-sdk-version specified");
                }
                this.mTargetSdkVersionSpecified = true;
                this.mTargetSdkVersion = Integer.parseInt(arg.substring(arg.indexOf(61) + 1));
            } else if (arg.equals("--runtime-args")) {
                seenRuntimeArgs = true;
            } else if (arg.startsWith("--runtime-flags=")) {
                this.mRuntimeFlags = Integer.parseInt(arg.substring(arg.indexOf(61) + 1));
            } else if (arg.startsWith("--seinfo=")) {
                if (this.mSeInfoSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mSeInfoSpecified = true;
                this.mSeInfo = arg.substring(arg.indexOf(61) + 1);
            } else if (arg.startsWith("--capabilities=")) {
                if (this.mCapabilitiesSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mCapabilitiesSpecified = true;
                String capString = arg.substring(arg.indexOf(61) + 1);
                String[] capStrings = capString.split(SmsManager.REGEX_PREFIX_DELIMITER, 2);
                if (capStrings.length == 1) {
                    this.mEffectiveCapabilities = Long.decode(capStrings[0]).longValue();
                    this.mPermittedCapabilities = this.mEffectiveCapabilities;
                } else {
                    this.mPermittedCapabilities = Long.decode(capStrings[0]).longValue();
                    this.mEffectiveCapabilities = Long.decode(capStrings[1]).longValue();
                }
            } else if (arg.startsWith("--rlimit=")) {
                String[] limitStrings = arg.substring(arg.indexOf(61) + 1).split(SmsManager.REGEX_PREFIX_DELIMITER);
                if (limitStrings.length != 3) {
                    throw new IllegalArgumentException("--rlimit= should have 3 comma-delimited ints");
                }
                int[] rlimitTuple = new int[limitStrings.length];
                while (i < limitStrings.length) {
                    rlimitTuple[i] = Integer.parseInt(limitStrings[i]);
                    i++;
                }
                if (this.mRLimits == null) {
                    this.mRLimits = new ArrayList<>();
                }
                this.mRLimits.add(rlimitTuple);
            } else if (arg.startsWith("--setgroups=")) {
                if (this.mGids == null) {
                    String[] params = arg.substring(arg.indexOf(61) + 1).split(SmsManager.REGEX_PREFIX_DELIMITER);
                    this.mGids = new int[params.length];
                    for (int i2 = params.length - 1; i2 >= 0; i2--) {
                        this.mGids[i2] = Integer.parseInt(params[i2]);
                    }
                } else {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
            } else if (arg.equals("--invoke-with")) {
                if (this.mInvokeWith != null) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                curArg++;
                try {
                    this.mInvokeWith = args[curArg];
                } catch (IndexOutOfBoundsException e) {
                    throw new IllegalArgumentException("--invoke-with requires argument");
                }
            } else if (arg.startsWith("--nice-name=")) {
                if (this.mNiceName == null) {
                    this.mNiceName = arg.substring(arg.indexOf(61) + 1);
                } else {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
            } else if (arg.equals("--mount-external-default")) {
                this.mMountExternal = 1;
            } else if (arg.equals("--mount-external-read")) {
                this.mMountExternal = 2;
            } else if (arg.equals("--mount-external-write")) {
                this.mMountExternal = 3;
            } else if (arg.equals("--mount-external-full")) {
                this.mMountExternal = 6;
            } else if (arg.equals("--mount-external-installer")) {
                this.mMountExternal = 5;
            } else if (arg.equals("--mount-external-legacy")) {
                this.mMountExternal = 4;
            } else if (arg.equals("--query-abi-list")) {
                this.mAbiListQuery = true;
            } else if (arg.equals("--get-pid")) {
                this.mPidQuery = true;
            } else if (arg.startsWith("--instruction-set=")) {
                this.mInstructionSet = arg.substring(arg.indexOf(61) + 1);
            } else if (arg.startsWith("--app-data-dir=")) {
                this.mAppDataDir = arg.substring(arg.indexOf(61) + 1);
            } else if (arg.equals("--preload-app")) {
                curArg++;
                this.mPreloadApp = args[curArg];
            } else if (arg.equals("--preload-package")) {
                int curArg3 = curArg + 1;
                this.mPreloadPackage = args[curArg3];
                int curArg4 = curArg3 + 1;
                this.mPreloadPackageLibs = args[curArg4];
                int curArg5 = curArg4 + 1;
                this.mPreloadPackageLibFileName = args[curArg5];
                curArg = curArg5 + 1;
                this.mPreloadPackageCacheKey = args[curArg];
            } else if (arg.equals("--preload-default")) {
                this.mPreloadDefault = true;
                curArg2 = 0;
            } else if (arg.equals("--start-child-zygote")) {
                this.mStartChildZygote = true;
            } else if (arg.equals("--set-api-blacklist-exemptions")) {
                this.mApiBlacklistExemptions = (String[]) Arrays.copyOfRange(args, curArg + 1, args.length);
                curArg = args.length;
                curArg2 = 0;
            } else if (arg.startsWith("--hidden-api-log-sampling-rate=")) {
                String rateStr = arg.substring(arg.indexOf(61) + 1);
                try {
                    this.mHiddenApiAccessLogSampleRate = Integer.parseInt(rateStr);
                    curArg2 = 0;
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Invalid log sampling rate: " + rateStr, nfe);
                }
            } else if (arg.startsWith("--hidden-api-statslog-sampling-rate=")) {
                String rateStr2 = arg.substring(arg.indexOf(61) + 1);
                try {
                    this.mHiddenApiAccessStatslogSampleRate = Integer.parseInt(rateStr2);
                    curArg2 = 0;
                } catch (NumberFormatException nfe2) {
                    throw new IllegalArgumentException("Invalid statslog sampling rate: " + rateStr2, nfe2);
                }
            } else if (arg.startsWith("--package-name=")) {
                if (this.mPackageName == null) {
                    this.mPackageName = arg.substring(arg.indexOf(61) + 1);
                } else {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
            } else if (!arg.startsWith("--usap-pool-enabled=")) {
                break;
            } else {
                this.mUsapPoolStatusSpecified = true;
                this.mUsapPoolEnabled = Boolean.parseBoolean(arg.substring(arg.indexOf(61) + 1));
                curArg2 = 0;
            }
            curArg++;
        }
    }
}
