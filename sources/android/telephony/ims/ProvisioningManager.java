package android.telephony.ims;

import android.annotation.SystemApi;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.aidl.IImsConfigCallback;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.FunctionalUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
public class ProvisioningManager {
    public static final int KEY_VOICE_OVER_WIFI_MODE_OVERRIDE = 27;
    public static final int KEY_VOICE_OVER_WIFI_ROAMING_ENABLED_OVERRIDE = 26;
    public static final int PROVISIONING_VALUE_DISABLED = 0;
    public static final int PROVISIONING_VALUE_ENABLED = 1;
    public static final String STRING_QUERY_RESULT_ERROR_GENERIC = "STRING_QUERY_RESULT_ERROR_GENERIC";
    public static final String STRING_QUERY_RESULT_ERROR_NOT_READY = "STRING_QUERY_RESULT_ERROR_NOT_READY";
    private int mSubId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface StringResultError {
    }

    public static class Callback {
        private final CallbackBinder mBinder = new CallbackBinder();

        private static class CallbackBinder extends IImsConfigCallback.Stub {
            private Executor mExecutor;
            private final Callback mLocalConfigurationCallback;

            private CallbackBinder(Callback localConfigurationCallback) {
                this.mLocalConfigurationCallback = localConfigurationCallback;
            }

            public final void onIntConfigChanged(int item, int value) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0005: INVOKE  
                      (wrap: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY : 0x0002: CONSTRUCTOR  (r0v0 android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY) = 
                      (r1v0 'this' android.telephony.ims.ProvisioningManager$Callback$CallbackBinder A[THIS])
                      (r2v0 'item' int)
                      (r3v0 'value' int)
                     call: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY.<init>(android.telephony.ims.ProvisioningManager$Callback$CallbackBinder, int, int):void type: CONSTRUCTOR)
                     android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.onIntConfigChanged(int, int):void, dex: classes4.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: CONSTRUCTOR  (r0v0 android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY) = 
                      (r1v0 'this' android.telephony.ims.ProvisioningManager$Callback$CallbackBinder A[THIS])
                      (r2v0 'item' int)
                      (r3v0 'value' int)
                     call: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY.<init>(android.telephony.ims.ProvisioningManager$Callback$CallbackBinder, int, int):void type: CONSTRUCTOR in method: android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.onIntConfigChanged(int, int):void, dex: classes4.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	... 59 more
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 65 more
                    */
                /*
                    this = this;
                    android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY r0 = new android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$rMBayJlNIko46WAqcRq_ggxbfrY
                    r0.<init>(r1, r2, r3)
                    android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.onIntConfigChanged(int, int):void");
            }

            public final void onStringConfigChanged(int item, String value) {
                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) 
                /*  JADX ERROR: Method code generation error
                    jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0005: INVOKE  
                      (wrap: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0 : 0x0002: CONSTRUCTOR  (r0v0 android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0) = 
                      (r1v0 'this' android.telephony.ims.ProvisioningManager$Callback$CallbackBinder A[THIS])
                      (r2v0 'item' int)
                      (r3v0 'value' java.lang.String)
                     call: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0.<init>(android.telephony.ims.ProvisioningManager$Callback$CallbackBinder, int, java.lang.String):void type: CONSTRUCTOR)
                     android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingRunnable):void type: STATIC in method: android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.onStringConfigChanged(int, java.lang.String):void, dex: classes4.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                    	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                    	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
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
                    Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0002: CONSTRUCTOR  (r0v0 android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0) = 
                      (r1v0 'this' android.telephony.ims.ProvisioningManager$Callback$CallbackBinder A[THIS])
                      (r2v0 'item' int)
                      (r3v0 'value' java.lang.String)
                     call: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0.<init>(android.telephony.ims.ProvisioningManager$Callback$CallbackBinder, int, java.lang.String):void type: CONSTRUCTOR in method: android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.onStringConfigChanged(int, java.lang.String):void, dex: classes4.dex
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                    	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                    	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                    	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                    	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                    	... 59 more
                    Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0, state: NOT_LOADED
                    	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                    	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                    	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                    	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                    	... 65 more
                    */
                /*
                    this = this;
                    android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0 r0 = new android.telephony.ims.-$$Lambda$ProvisioningManager$Callback$CallbackBinder$Jkes2onT-fqeBCDl6FWK1nXcIt0
                    r0.<init>(r1, r2, r3)
                    android.os.Binder.withCleanCallingIdentity((com.android.internal.util.FunctionalUtils.ThrowingRunnable) r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.ProvisioningManager.Callback.CallbackBinder.onStringConfigChanged(int, java.lang.String):void");
            }

            /* access modifiers changed from: private */
            public void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }
        }

        public void onProvisioningIntChanged(int item, int value) {
        }

        public void onProvisioningStringChanged(int item, String value) {
        }

        public final IImsConfigCallback getBinder() {
            return this.mBinder;
        }

        public void setExecutor(Executor executor) {
            this.mBinder.setExecutor(executor);
        }
    }

    public static ProvisioningManager createForSubscriptionId(int subId) {
        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            return new ProvisioningManager(subId);
        }
        throw new IllegalArgumentException("Invalid subscription ID");
    }

    private ProvisioningManager(int subId) {
        this.mSubId = subId;
    }

    public void registerProvisioningChangedCallback(Executor executor, Callback callback) throws ImsException {
        if (isImsAvailableOnDevice()) {
            callback.setExecutor(executor);
            try {
                getITelephony().registerImsProvisioningChangedCallback(this.mSubId, callback.getBinder());
            } catch (RemoteException | IllegalStateException e) {
                throw new ImsException(e.getMessage(), 1);
            }
        } else {
            throw new ImsException("IMS not available on device.", 2);
        }
    }

    public void unregisterProvisioningChangedCallback(Callback callback) {
        try {
            getITelephony().unregisterImsProvisioningChangedCallback(this.mSubId, callback.getBinder());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public int getProvisioningIntValue(int key) {
        try {
            return getITelephony().getImsProvisioningInt(this.mSubId, key);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public String getProvisioningStringValue(int key) {
        try {
            return getITelephony().getImsProvisioningString(this.mSubId, key);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public int setProvisioningIntValue(int key, int value) {
        try {
            return getITelephony().setImsProvisioningInt(this.mSubId, key, value);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public int setProvisioningStringValue(int key, String value) {
        try {
            return getITelephony().setImsProvisioningString(this.mSubId, key, value);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public void setProvisioningStatusForCapability(int capability, int tech, boolean isProvisioned) {
        try {
            getITelephony().setImsProvisioningStatusForCapability(this.mSubId, capability, tech, isProvisioned);
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean getProvisioningStatusForCapability(int capability, int tech) {
        try {
            return getITelephony().getImsProvisioningStatusForCapability(this.mSubId, capability, tech);
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
