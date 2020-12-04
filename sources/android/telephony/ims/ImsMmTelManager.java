package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.FunctionalUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
public class ImsMmTelManager {
    private static final String TAG = "ImsMmTelManager";
    public static final int WIFI_MODE_CELLULAR_PREFERRED = 1;
    public static final int WIFI_MODE_WIFI_ONLY = 0;
    public static final int WIFI_MODE_WIFI_PREFERRED = 2;
    private int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface WiFiCallingMode {
    }

    public static class RegistrationCallback {
        private final RegistrationBinder mBinder = new RegistrationBinder(this);

        private static class RegistrationBinder extends IImsRegistrationCallback.Stub {
            private static final Map<Integer, Integer> IMS_REG_TO_ACCESS_TYPE_MAP = new HashMap<Integer, Integer>() {
                {
                    put(-1, -1);
                    put(0, 1);
                    put(1, 2);
                }
            };
            private Executor mExecutor;
            private final RegistrationCallback mLocalCallback;

            RegistrationBinder(RegistrationCallback localCallback) {
                this.mLocalCallback = localCallback;
            }

            public void onRegistered(int imsRadioTech) {
                if (this.mLocalCallback != null) {
                    Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000a: INVOKE  
                          (wrap: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc : 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc) = 
                          (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                          (r2v0 'imsRadioTech' int)
                         call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, int):void type: CONSTRUCTOR)
                         android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onRegistered(int):void, dex: classes4.dex
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
                        Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc) = 
                          (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                          (r2v0 'imsRadioTech' int)
                         call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, int):void type: CONSTRUCTOR in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onRegistered(int):void, dex: classes4.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	... 66 more
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	... 72 more
                        */
                    /*
                        this = this;
                        android.telephony.ims.ImsMmTelManager$RegistrationCallback r0 = r1.mLocalCallback
                        if (r0 != 0) goto L_0x0005
                        return
                    L_0x0005:
                        android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc r0 = new android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$8xq93ap6i0L56Aegaj-ZEUt9ISc
                        r0.<init>(r1, r2)
                        android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onRegistered(int):void");
                }

                public void onRegistering(int imsRadioTech) {
                    if (this.mLocalCallback != null) {
                        Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                        /*  JADX ERROR: Method code generation error
                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000a: INVOKE  
                              (wrap: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g : 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g) = 
                              (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                              (r2v0 'imsRadioTech' int)
                             call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, int):void type: CONSTRUCTOR)
                             android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onRegistering(int):void, dex: classes4.dex
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
                            Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g) = 
                              (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                              (r2v0 'imsRadioTech' int)
                             call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, int):void type: CONSTRUCTOR in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onRegistering(int):void, dex: classes4.dex
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                            	... 66 more
                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g, state: NOT_LOADED
                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                            	... 72 more
                            */
                        /*
                            this = this;
                            android.telephony.ims.ImsMmTelManager$RegistrationCallback r0 = r1.mLocalCallback
                            if (r0 != 0) goto L_0x0005
                            return
                        L_0x0005:
                            android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g r0 = new android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$iuI3HyNU5eUABA_QRyzQ8Jw2-8g
                            r0.<init>(r1, r2)
                            android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onRegistering(int):void");
                    }

                    public void onDeregistered(ImsReasonInfo info) {
                        if (this.mLocalCallback != null) {
                            Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000a: INVOKE  
                                  (wrap: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU : 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU) = 
                                  (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                                  (r2v0 'info' android.telephony.ims.ImsReasonInfo)
                                 call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, android.telephony.ims.ImsReasonInfo):void type: CONSTRUCTOR)
                                 android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onDeregistered(android.telephony.ims.ImsReasonInfo):void, dex: classes4.dex
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
                                Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU) = 
                                  (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                                  (r2v0 'info' android.telephony.ims.ImsReasonInfo)
                                 call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, android.telephony.ims.ImsReasonInfo):void type: CONSTRUCTOR in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onDeregistered(android.telephony.ims.ImsReasonInfo):void, dex: classes4.dex
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                                	... 66 more
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU, state: NOT_LOADED
                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                                	... 72 more
                                */
                            /*
                                this = this;
                                android.telephony.ims.ImsMmTelManager$RegistrationCallback r0 = r1.mLocalCallback
                                if (r0 != 0) goto L_0x0005
                                return
                            L_0x0005:
                                android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU r0 = new android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$F58PRHsH__07pmyvC0NTRprfEPU
                                r0.<init>(r1, r2)
                                android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onDeregistered(android.telephony.ims.ImsReasonInfo):void");
                        }

                        public void onTechnologyChangeFailed(int imsRadioTech, ImsReasonInfo info) {
                            if (this.mLocalCallback != null) {
                                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                                /*  JADX ERROR: Method code generation error
                                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000a: INVOKE  
                                      (wrap: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY : 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY) = 
                                      (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                                      (r2v0 'imsRadioTech' int)
                                      (r3v0 'info' android.telephony.ims.ImsReasonInfo)
                                     call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, int, android.telephony.ims.ImsReasonInfo):void type: CONSTRUCTOR)
                                     android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onTechnologyChangeFailed(int, android.telephony.ims.ImsReasonInfo):void, dex: classes4.dex
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
                                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY) = 
                                      (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                                      (r2v0 'imsRadioTech' int)
                                      (r3v0 'info' android.telephony.ims.ImsReasonInfo)
                                     call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, int, android.telephony.ims.ImsReasonInfo):void type: CONSTRUCTOR in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onTechnologyChangeFailed(int, android.telephony.ims.ImsReasonInfo):void, dex: classes4.dex
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                                    	... 66 more
                                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY, state: NOT_LOADED
                                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                                    	... 72 more
                                    */
                                /*
                                    this = this;
                                    android.telephony.ims.ImsMmTelManager$RegistrationCallback r0 = r1.mLocalCallback
                                    if (r0 != 0) goto L_0x0005
                                    return
                                L_0x0005:
                                    android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY r0 = new android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$Nztp9t3A8T2T3SbLvxLZoInLgWY
                                    r0.<init>(r1, r2, r3)
                                    android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                                    return
                                */
                                throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onTechnologyChangeFailed(int, android.telephony.ims.ImsReasonInfo):void");
                            }

                            public void onSubscriberAssociatedUriChanged(Uri[] uris) {
                                if (this.mLocalCallback != null) {
                                    Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                                    /*  JADX ERROR: Method code generation error
                                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000a: INVOKE  
                                          (wrap: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM : 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM) = 
                                          (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                                          (r2v0 'uris' android.net.Uri[])
                                         call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, android.net.Uri[]):void type: CONSTRUCTOR)
                                         android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onSubscriberAssociatedUriChanged(android.net.Uri[]):void, dex: classes4.dex
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
                                        Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM) = 
                                          (r1v0 'this' android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder A[THIS])
                                          (r2v0 'uris' android.net.Uri[])
                                         call: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM.<init>(android.telephony.ims.ImsMmTelManager$RegistrationCallback$RegistrationBinder, android.net.Uri[]):void type: CONSTRUCTOR in method: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onSubscriberAssociatedUriChanged(android.net.Uri[]):void, dex: classes4.dex
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                                        	... 66 more
                                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM, state: NOT_LOADED
                                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                                        	... 72 more
                                        */
                                    /*
                                        this = this;
                                        android.telephony.ims.ImsMmTelManager$RegistrationCallback r0 = r1.mLocalCallback
                                        if (r0 != 0) goto L_0x0005
                                        return
                                    L_0x0005:
                                        android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM r0 = new android.telephony.ims.-$$Lambda$ImsMmTelManager$RegistrationCallback$RegistrationBinder$AhnK6VJjwgpDNC1GXRrwfgtYvkM
                                        r0.<init>(r1, r2)
                                        android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                                        return
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ImsMmTelManager.RegistrationCallback.RegistrationBinder.onSubscriberAssociatedUriChanged(android.net.Uri[]):void");
                                }

                                /* access modifiers changed from: private */
                                public void setExecutor(Executor executor) {
                                    this.mExecutor = executor;
                                }

                                private static int getAccessType(int regType) {
                                    if (IMS_REG_TO_ACCESS_TYPE_MAP.containsKey(Integer.valueOf(regType))) {
                                        return IMS_REG_TO_ACCESS_TYPE_MAP.get(Integer.valueOf(regType)).intValue();
                                    }
                                    Log.w(ImsMmTelManager.TAG, "RegistrationBinder - invalid regType returned: " + regType);
                                    return -1;
                                }
                            }

                            public void onRegistered(int imsTransportType) {
                            }

                            public void onRegistering(int imsTransportType) {
                            }

                            public void onUnregistered(ImsReasonInfo info) {
                            }

                            public void onTechnologyChangeFailed(int imsTransportType, ImsReasonInfo info) {
                            }

                            public void onSubscriberAssociatedUriChanged(Uri[] uris) {
                            }

                            public final IImsRegistrationCallback getBinder() {
                                return this.mBinder;
                            }

                            public void setExecutor(Executor executor) {
                                this.mBinder.setExecutor(executor);
                            }
                        }

                        public static class CapabilityCallback {
                            private final CapabilityBinder mBinder = new CapabilityBinder(this);

                            private static class CapabilityBinder extends IImsCapabilityCallback.Stub {
                                private Executor mExecutor;
                                private final CapabilityCallback mLocalCallback;

                                CapabilityBinder(CapabilityCallback c) {
                                    this.mLocalCallback = c;
                                }

                                public void onCapabilitiesStatusChanged(int config) {
                                    if (this.mLocalCallback != null) {
                                        Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                                        /*  JADX ERROR: Method code generation error
                                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000a: INVOKE  
                                              (wrap: android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8 : 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8) = 
                                              (r1v0 'this' android.telephony.ims.ImsMmTelManager$CapabilityCallback$CapabilityBinder A[THIS])
                                              (r2v0 'config' int)
                                             call: android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8.<init>(android.telephony.ims.ImsMmTelManager$CapabilityCallback$CapabilityBinder, int):void type: CONSTRUCTOR)
                                             android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ImsMmTelManager.CapabilityCallback.CapabilityBinder.onCapabilitiesStatusChanged(int):void, dex: classes4.dex
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
                                            Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0007: CONSTRUCTOR  (r0v1 android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8) = 
                                              (r1v0 'this' android.telephony.ims.ImsMmTelManager$CapabilityCallback$CapabilityBinder A[THIS])
                                              (r2v0 'config' int)
                                             call: android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8.<init>(android.telephony.ims.ImsMmTelManager$CapabilityCallback$CapabilityBinder, int):void type: CONSTRUCTOR in method: android.telephony.ims.ImsMmTelManager.CapabilityCallback.CapabilityBinder.onCapabilitiesStatusChanged(int):void, dex: classes4.dex
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                                            	... 66 more
                                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8, state: NOT_LOADED
                                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                                            	... 72 more
                                            */
                                        /*
                                            this = this;
                                            android.telephony.ims.ImsMmTelManager$CapabilityCallback r0 = r1.mLocalCallback
                                            if (r0 != 0) goto L_0x0005
                                            return
                                        L_0x0005:
                                            android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8 r0 = new android.telephony.ims.-$$Lambda$ImsMmTelManager$CapabilityCallback$CapabilityBinder$gK2iK9ZQ3GDeuMTfhRd7yjiYlO8
                                            r0.<init>(r1, r2)
                                            android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                                            return
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ImsMmTelManager.CapabilityCallback.CapabilityBinder.onCapabilitiesStatusChanged(int):void");
                                    }

                                    public void onQueryCapabilityConfiguration(int capability, int radioTech, boolean isEnabled) {
                                    }

                                    public void onChangeCapabilityConfigurationError(int capability, int radioTech, int reason) {
                                    }

                                    /* access modifiers changed from: private */
                                    public void setExecutor(Executor executor) {
                                        this.mExecutor = executor;
                                    }
                                }

                                public void onCapabilitiesStatusChanged(MmTelFeature.MmTelCapabilities capabilities) {
                                }

                                public final IImsCapabilityCallback getBinder() {
                                    return this.mBinder;
                                }

                                public final void setExecutor(Executor executor) {
                                    this.mBinder.setExecutor(executor);
                                }
                            }

                            public static ImsMmTelManager createForSubscriptionId(int subId) {
                                if (SubscriptionManager.isValidSubscriptionId(subId)) {
                                    return new ImsMmTelManager(subId);
                                }
                                throw new IllegalArgumentException("Invalid subscription ID");
                            }

                            @VisibleForTesting
                            public ImsMmTelManager(int subId) {
                                this.mSubId = subId;
                            }

                            public void registerImsRegistrationCallback(Executor executor, RegistrationCallback c) throws ImsException {
                                if (c == null) {
                                    throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
                                } else if (executor == null) {
                                    throw new IllegalArgumentException("Must include a non-null Executor.");
                                } else if (isImsAvailableOnDevice()) {
                                    c.setExecutor(executor);
                                    try {
                                        getITelephony().registerImsRegistrationCallback(this.mSubId, c.getBinder());
                                    } catch (RemoteException | IllegalStateException e) {
                                        throw new ImsException(e.getMessage(), 1);
                                    }
                                } else {
                                    throw new ImsException("IMS not available on device.", 2);
                                }
                            }

                            public void unregisterImsRegistrationCallback(RegistrationCallback c) {
                                if (c != null) {
                                    try {
                                        getITelephony().unregisterImsRegistrationCallback(this.mSubId, c.getBinder());
                                    } catch (RemoteException e) {
                                        throw e.rethrowAsRuntimeException();
                                    }
                                } else {
                                    throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
                                }
                            }

                            public void registerMmTelCapabilityCallback(Executor executor, CapabilityCallback c) throws ImsException {
                                if (c == null) {
                                    throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
                                } else if (executor == null) {
                                    throw new IllegalArgumentException("Must include a non-null Executor.");
                                } else if (isImsAvailableOnDevice()) {
                                    c.setExecutor(executor);
                                    try {
                                        getITelephony().registerMmTelCapabilityCallback(this.mSubId, c.getBinder());
                                    } catch (RemoteException e) {
                                        throw e.rethrowAsRuntimeException();
                                    } catch (IllegalStateException e2) {
                                        throw new ImsException(e2.getMessage(), 1);
                                    }
                                } else {
                                    throw new ImsException("IMS not available on device.", 2);
                                }
                            }

                            public void unregisterMmTelCapabilityCallback(CapabilityCallback c) {
                                if (c != null) {
                                    try {
                                        getITelephony().unregisterMmTelCapabilityCallback(this.mSubId, c.getBinder());
                                    } catch (RemoteException e) {
                                        throw e.rethrowAsRuntimeException();
                                    }
                                } else {
                                    throw new IllegalArgumentException("Must include a non-null RegistrationCallback.");
                                }
                            }

                            public boolean isAdvancedCallingSettingEnabled() {
                                try {
                                    return getITelephony().isAdvancedCallingSettingEnabled(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setAdvancedCallingSettingEnabled(boolean isEnabled) {
                                try {
                                    getITelephony().setAdvancedCallingSettingEnabled(this.mSubId, isEnabled);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public boolean isCapable(int capability, int imsRegTech) {
                                try {
                                    return getITelephony().isCapable(this.mSubId, capability, imsRegTech);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public boolean isAvailable(int capability, int imsRegTech) {
                                try {
                                    return getITelephony().isAvailable(this.mSubId, capability, imsRegTech);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public boolean isVtSettingEnabled() {
                                try {
                                    return getITelephony().isVtSettingEnabled(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setVtSettingEnabled(boolean isEnabled) {
                                try {
                                    getITelephony().setVtSettingEnabled(this.mSubId, isEnabled);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public boolean isVoWiFiSettingEnabled() {
                                try {
                                    return getITelephony().isVoWiFiSettingEnabled(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setVoWiFiSettingEnabled(boolean isEnabled) {
                                try {
                                    getITelephony().setVoWiFiSettingEnabled(this.mSubId, isEnabled);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public boolean isVoWiFiRoamingSettingEnabled() {
                                try {
                                    return getITelephony().isVoWiFiRoamingSettingEnabled(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setVoWiFiRoamingSettingEnabled(boolean isEnabled) {
                                try {
                                    getITelephony().setVoWiFiRoamingSettingEnabled(this.mSubId, isEnabled);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setVoWiFiNonPersistent(boolean isCapable, int mode) {
                                try {
                                    getITelephony().setVoWiFiNonPersistent(this.mSubId, isCapable, mode);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public int getVoWiFiModeSetting() {
                                try {
                                    return getITelephony().getVoWiFiModeSetting(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setVoWiFiModeSetting(int mode) {
                                try {
                                    getITelephony().setVoWiFiModeSetting(this.mSubId, mode);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public int getVoWiFiRoamingModeSetting() {
                                try {
                                    return getITelephony().getVoWiFiRoamingModeSetting(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setVoWiFiRoamingModeSetting(int mode) {
                                try {
                                    getITelephony().setVoWiFiRoamingModeSetting(this.mSubId, mode);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            public void setRttCapabilitySetting(boolean isEnabled) {
                                try {
                                    getITelephony().setRttCapabilitySetting(this.mSubId, isEnabled);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            /* access modifiers changed from: package-private */
                            public boolean isTtyOverVolteEnabled() {
                                try {
                                    return getITelephony().isTtyOverVolteEnabled(this.mSubId);
                                } catch (RemoteException e) {
                                    throw e.rethrowAsRuntimeException();
                                }
                            }

                            private static boolean isImsAvailableOnDevice() {
                                IPackageManager pm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
                                if (pm == null) {
                                    return true;
                                }
                                try {
                                    return pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_IMS, 0);
                                } catch (RemoteException e) {
                                    return true;
                                }
                            }

                            private static ITelephony getITelephony() {
                                ITelephony binder = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
                                if (binder != null) {
                                    return binder;
                                }
                                throw new RuntimeException("Could not find Telephony Service.");
                            }
                        }
