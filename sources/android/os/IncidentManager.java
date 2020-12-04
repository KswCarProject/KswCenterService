package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.Uri;
import android.os.IBinder;
import android.os.IIncidentAuthListener;
import android.os.IIncidentCompanion;
import android.os.IIncidentManager;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Slog;
import android.util.TimeUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
public class IncidentManager {
    public static final int FLAG_CONFIRMATION_DIALOG = 1;
    public static final int PRIVACY_POLICY_AUTO = 200;
    public static final int PRIVACY_POLICY_EXPLICIT = 100;
    public static final int PRIVACY_POLICY_LOCAL = 0;
    private static final String TAG = "IncidentManager";
    public static final String URI_AUTHORITY = "android.os.IncidentManager";
    public static final String URI_PARAM_CALLING_PACKAGE = "pkg";
    public static final String URI_PARAM_FLAGS = "flags";
    public static final String URI_PARAM_ID = "id";
    public static final String URI_PARAM_RECEIVER_CLASS = "receiver";
    public static final String URI_PARAM_REPORT_ID = "r";
    public static final String URI_PARAM_TIMESTAMP = "t";
    public static final String URI_PATH = "/pending";
    public static final String URI_SCHEME = "content";
    private IIncidentCompanion mCompanionService;
    private final Context mContext;
    private IIncidentManager mIncidentService;
    private Object mLock = new Object();

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrivacyPolicy {
    }

    @SystemApi
    public static class PendingReport {
        private final int mFlags;
        private final String mRequestingPackage;
        private final long mTimestamp;
        private final Uri mUri;

        public PendingReport(Uri uri) {
            try {
                this.mFlags = Integer.parseInt(uri.getQueryParameter("flags"));
                String requestingPackage = uri.getQueryParameter("pkg");
                if (requestingPackage != null) {
                    this.mRequestingPackage = requestingPackage;
                    try {
                        this.mTimestamp = Long.parseLong(uri.getQueryParameter(IncidentManager.URI_PARAM_TIMESTAMP));
                        this.mUri = uri;
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid URI: No t parameter. " + uri);
                    }
                } else {
                    throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
                }
            } catch (NumberFormatException e2) {
                throw new RuntimeException("Invalid URI: No flags parameter. " + uri);
            }
        }

        public String getRequestingPackage() {
            return this.mRequestingPackage;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public String toString() {
            return "PendingReport(" + getUri().toString() + ")";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PendingReport)) {
                return false;
            }
            PendingReport that = (PendingReport) obj;
            if (!this.mUri.equals(that.mUri) || this.mFlags != that.mFlags || !this.mRequestingPackage.equals(that.mRequestingPackage) || this.mTimestamp != that.mTimestamp) {
                return false;
            }
            return true;
        }
    }

    @SystemApi
    public static class IncidentReport implements Parcelable, Closeable {
        public static final Parcelable.Creator<IncidentReport> CREATOR = new Parcelable.Creator() {
            public IncidentReport[] newArray(int size) {
                return new IncidentReport[size];
            }

            public IncidentReport createFromParcel(Parcel in) {
                return new IncidentReport(in);
            }
        };
        private ParcelFileDescriptor mFileDescriptor;
        private final int mPrivacyPolicy;
        private final long mTimestampNs;

        public IncidentReport(Parcel in) {
            this.mTimestampNs = in.readLong();
            this.mPrivacyPolicy = in.readInt();
            if (in.readInt() != 0) {
                this.mFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(in);
            } else {
                this.mFileDescriptor = null;
            }
        }

        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                    this.mFileDescriptor = null;
                }
            } catch (IOException e) {
            }
        }

        public long getTimestamp() {
            return this.mTimestampNs / TimeUtils.NANOS_PER_MS;
        }

        public long getPrivacyPolicy() {
            return (long) this.mPrivacyPolicy;
        }

        public InputStream getInputStream() throws IOException {
            if (this.mFileDescriptor == null) {
                return null;
            }
            return new ParcelFileDescriptor.AutoCloseInputStream(this.mFileDescriptor);
        }

        public int describeContents() {
            return this.mFileDescriptor != null ? 1 : 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeLong(this.mTimestampNs);
            out.writeInt(this.mPrivacyPolicy);
            if (this.mFileDescriptor != null) {
                out.writeInt(1);
                this.mFileDescriptor.writeToParcel(out, flags);
                return;
            }
            out.writeInt(0);
        }
    }

    public static class AuthListener {
        IIncidentAuthListener.Stub mBinder = new IIncidentAuthListener.Stub() {
            public void onReportApproved() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000f: INVOKE  
                          (wrap: java.util.concurrent.Executor : 0x0008: IGET  (r0v4 java.util.concurrent.Executor) = 
                          (wrap: android.os.IncidentManager$AuthListener : 0x0006: IGET  (r0v3 android.os.IncidentManager$AuthListener) = 
                          (r2v0 'this' android.os.IncidentManager$AuthListener$1 A[THIS])
                         android.os.IncidentManager.AuthListener.1.this$0 android.os.IncidentManager$AuthListener)
                         android.os.IncidentManager.AuthListener.mExecutor java.util.concurrent.Executor)
                          (wrap: android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U : 0x000c: CONSTRUCTOR  (r1v0 android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U) = 
                          (r2v0 'this' android.os.IncidentManager$AuthListener$1 A[THIS])
                         call: android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U.<init>(android.os.IncidentManager$AuthListener$1):void type: CONSTRUCTOR)
                         java.util.concurrent.Executor.execute(java.lang.Runnable):void type: INTERFACE in method: android.os.IncidentManager.AuthListener.1.onReportApproved():void, dex: classes3.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                        	at java.util.ArrayList.forEach(ArrayList.java:1257)
                        	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                        	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                        	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                        	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                        	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:98)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:480)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	at jadx.core.codegen.ClassGen.addInsnBody(ClassGen.java:437)
                        	at jadx.core.codegen.ClassGen.addField(ClassGen.java:378)
                        	at jadx.core.codegen.ClassGen.addFields(ClassGen.java:348)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:226)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                        	at java.util.ArrayList.forEach(ArrayList.java:1257)
                        	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                        	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                        	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                        	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                        	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                        Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000c: CONSTRUCTOR  (r1v0 android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U) = 
                          (r2v0 'this' android.os.IncidentManager$AuthListener$1 A[THIS])
                         call: android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U.<init>(android.os.IncidentManager$AuthListener$1):void type: CONSTRUCTOR in method: android.os.IncidentManager.AuthListener.1.onReportApproved():void, dex: classes3.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	... 62 more
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	... 68 more
                        */
                    /*
                        this = this;
                        android.os.IncidentManager$AuthListener r0 = android.os.IncidentManager.AuthListener.this
                        java.util.concurrent.Executor r0 = r0.mExecutor
                        if (r0 == 0) goto L_0x0013
                        android.os.IncidentManager$AuthListener r0 = android.os.IncidentManager.AuthListener.this
                        java.util.concurrent.Executor r0 = r0.mExecutor
                        android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U r1 = new android.os.-$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U
                        r1.<init>(r2)
                        r0.execute(r1)
                        goto L_0x0018
                    L_0x0013:
                        android.os.IncidentManager$AuthListener r0 = android.os.IncidentManager.AuthListener.this
                        r0.onReportApproved()
                    L_0x0018:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.os.IncidentManager.AuthListener.AnonymousClass1.onReportApproved():void");
                }

                public void onReportDenied() {
                    if (AuthListener.this.mExecutor != null) {
                        AuthListener.this.mExecutor.execute(
                        /*  JADX ERROR: Method code generation error
                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000f: INVOKE  
                              (wrap: java.util.concurrent.Executor : 0x0008: IGET  (r0v4 java.util.concurrent.Executor) = 
                              (wrap: android.os.IncidentManager$AuthListener : 0x0006: IGET  (r0v3 android.os.IncidentManager$AuthListener) = 
                              (r2v0 'this' android.os.IncidentManager$AuthListener$1 A[THIS])
                             android.os.IncidentManager.AuthListener.1.this$0 android.os.IncidentManager$AuthListener)
                             android.os.IncidentManager.AuthListener.mExecutor java.util.concurrent.Executor)
                              (wrap: android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ : 0x000c: CONSTRUCTOR  (r1v0 android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ) = 
                              (r2v0 'this' android.os.IncidentManager$AuthListener$1 A[THIS])
                             call: android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ.<init>(android.os.IncidentManager$AuthListener$1):void type: CONSTRUCTOR)
                             java.util.concurrent.Executor.execute(java.lang.Runnable):void type: INTERFACE in method: android.os.IncidentManager.AuthListener.1.onReportDenied():void, dex: classes3.dex
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                            	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                            	at java.util.ArrayList.forEach(ArrayList.java:1257)
                            	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                            	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                            	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                            	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                            	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                            	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:98)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:480)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	at jadx.core.codegen.ClassGen.addInsnBody(ClassGen.java:437)
                            	at jadx.core.codegen.ClassGen.addField(ClassGen.java:378)
                            	at jadx.core.codegen.ClassGen.addFields(ClassGen.java:348)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:226)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:249)
                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:238)
                            	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                            	at java.util.ArrayList.forEach(ArrayList.java:1257)
                            	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:390)
                            	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                            	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
                            	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)
                            	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
                            	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
                            	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                            	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
                            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                            Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000c: CONSTRUCTOR  (r1v0 android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ) = 
                              (r2v0 'this' android.os.IncidentManager$AuthListener$1 A[THIS])
                             call: android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ.<init>(android.os.IncidentManager$AuthListener$1):void type: CONSTRUCTOR in method: android.os.IncidentManager.AuthListener.1.onReportDenied():void, dex: classes3.dex
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	... 62 more
                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ, state: NOT_LOADED
                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	... 68 more
                            */
                        /*
                            this = this;
                            android.os.IncidentManager$AuthListener r0 = android.os.IncidentManager.AuthListener.this
                            java.util.concurrent.Executor r0 = r0.mExecutor
                            if (r0 == 0) goto L_0x0013
                            android.os.IncidentManager$AuthListener r0 = android.os.IncidentManager.AuthListener.this
                            java.util.concurrent.Executor r0 = r0.mExecutor
                            android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ r1 = new android.os.-$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ
                            r1.<init>(r2)
                            r0.execute(r1)
                            goto L_0x0018
                        L_0x0013:
                            android.os.IncidentManager$AuthListener r0 = android.os.IncidentManager.AuthListener.this
                            r0.onReportDenied()
                        L_0x0018:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: android.os.IncidentManager.AuthListener.AnonymousClass1.onReportDenied():void");
                    }
                };
                Executor mExecutor;

                public void onReportApproved() {
                }

                public void onReportDenied() {
                }
            }

            public IncidentManager(Context context) {
                this.mContext = context;
            }

            public void reportIncident(IncidentReportArgs args) {
                reportIncidentInternal(args);
            }

            public void requestAuthorization(int callingUid, String callingPackage, int flags, AuthListener listener) {
                requestAuthorization(callingUid, callingPackage, flags, this.mContext.getMainExecutor(), listener);
            }

            public void requestAuthorization(int callingUid, String callingPackage, int flags, Executor executor, AuthListener listener) {
                try {
                    if (listener.mExecutor == null) {
                        listener.mExecutor = executor;
                        getCompanionServiceLocked().authorizeReport(callingUid, callingPackage, (String) null, (String) null, flags, listener.mBinder);
                        return;
                    }
                    throw new RuntimeException("Do not reuse AuthListener objects when calling requestAuthorization");
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public void cancelAuthorization(AuthListener listener) {
                try {
                    getCompanionServiceLocked().cancelAuthorization(listener.mBinder);
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public List<PendingReport> getPendingReports() {
                try {
                    List<String> strings = getCompanionServiceLocked().getPendingReports();
                    int size = strings.size();
                    ArrayList<PendingReport> result = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        result.add(new PendingReport(Uri.parse(strings.get(i))));
                    }
                    return result;
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public void approveReport(Uri uri) {
                try {
                    getCompanionServiceLocked().approveReport(uri.toString());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public void denyReport(Uri uri) {
                try {
                    getCompanionServiceLocked().denyReport(uri.toString());
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

            public List<Uri> getIncidentReportList(String receiverClass) {
                try {
                    List<String> strings = getCompanionServiceLocked().getIncidentReportList(this.mContext.getPackageName(), receiverClass);
                    int size = strings.size();
                    ArrayList<Uri> result = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        result.add(Uri.parse(strings.get(i)));
                    }
                    return result;
                } catch (RemoteException ex) {
                    throw new RuntimeException("System server or incidentd going down", ex);
                }
            }

            public IncidentReport getIncidentReport(Uri uri) {
                String id = uri.getQueryParameter("r");
                if (id == null) {
                    return null;
                }
                String pkg = uri.getQueryParameter("pkg");
                if (pkg != null) {
                    String cls = uri.getQueryParameter(URI_PARAM_RECEIVER_CLASS);
                    if (cls != null) {
                        try {
                            return getCompanionServiceLocked().getIncidentReport(pkg, cls, id);
                        } catch (RemoteException ex) {
                            throw new RuntimeException("System server or incidentd going down", ex);
                        }
                    } else {
                        throw new RuntimeException("Invalid URI: No receiver parameter. " + uri);
                    }
                } else {
                    throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
                }
            }

            public void deleteIncidentReports(Uri uri) {
                if (uri == null) {
                    try {
                        getCompanionServiceLocked().deleteAllIncidentReports(this.mContext.getPackageName());
                    } catch (RemoteException ex) {
                        throw new RuntimeException("System server or incidentd going down", ex);
                    }
                } else {
                    String pkg = uri.getQueryParameter("pkg");
                    if (pkg != null) {
                        String cls = uri.getQueryParameter(URI_PARAM_RECEIVER_CLASS);
                        if (cls != null) {
                            String id = uri.getQueryParameter("r");
                            if (id != null) {
                                try {
                                    getCompanionServiceLocked().deleteIncidentReports(pkg, cls, id);
                                } catch (RemoteException ex2) {
                                    throw new RuntimeException("System server or incidentd going down", ex2);
                                }
                            } else {
                                throw new RuntimeException("Invalid URI: No r parameter. " + uri);
                            }
                        } else {
                            throw new RuntimeException("Invalid URI: No receiver parameter. " + uri);
                        }
                    } else {
                        throw new RuntimeException("Invalid URI: No pkg parameter. " + uri);
                    }
                }
            }

            private void reportIncidentInternal(IncidentReportArgs args) {
                try {
                    IIncidentManager service = getIIncidentManagerLocked();
                    if (service == null) {
                        Slog.e(TAG, "reportIncident can't find incident binder service");
                    } else {
                        service.reportIncident(args);
                    }
                } catch (RemoteException ex) {
                    Slog.e(TAG, "reportIncident failed", ex);
                }
            }

            private IIncidentManager getIIncidentManagerLocked() throws RemoteException {
                if (this.mIncidentService != null) {
                    return this.mIncidentService;
                }
                synchronized (this.mLock) {
                    if (this.mIncidentService != null) {
                        IIncidentManager iIncidentManager = this.mIncidentService;
                        return iIncidentManager;
                    }
                    this.mIncidentService = IIncidentManager.Stub.asInterface(ServiceManager.getService(Context.INCIDENT_SERVICE));
                    if (this.mIncidentService != null) {
                        this.mIncidentService.asBinder().linkToDeath(new IBinder.DeathRecipient() {
                            public final void binderDied() {
                                IncidentManager.lambda$getIIncidentManagerLocked$0(IncidentManager.this);
                            }
                        }, 0);
                    }
                    IIncidentManager iIncidentManager2 = this.mIncidentService;
                    return iIncidentManager2;
                }
            }

            public static /* synthetic */ void lambda$getIIncidentManagerLocked$0(IncidentManager incidentManager) {
                synchronized (incidentManager.mLock) {
                    incidentManager.mIncidentService = null;
                }
            }

            private IIncidentCompanion getCompanionServiceLocked() throws RemoteException {
                if (this.mCompanionService != null) {
                    return this.mCompanionService;
                }
                synchronized (this) {
                    if (this.mCompanionService != null) {
                        IIncidentCompanion iIncidentCompanion = this.mCompanionService;
                        return iIncidentCompanion;
                    }
                    this.mCompanionService = IIncidentCompanion.Stub.asInterface(ServiceManager.getService(Context.INCIDENT_COMPANION_SERVICE));
                    if (this.mCompanionService != null) {
                        this.mCompanionService.asBinder().linkToDeath(new IBinder.DeathRecipient() {
                            public final void binderDied() {
                                IncidentManager.lambda$getCompanionServiceLocked$1(IncidentManager.this);
                            }
                        }, 0);
                    }
                    IIncidentCompanion iIncidentCompanion2 = this.mCompanionService;
                    return iIncidentCompanion2;
                }
            }

            public static /* synthetic */ void lambda$getCompanionServiceLocked$1(IncidentManager incidentManager) {
                synchronized (incidentManager.mLock) {
                    incidentManager.mCompanionService = null;
                }
            }
        }
