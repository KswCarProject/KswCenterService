package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.INetworkPolicyManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.MediaStore;
import android.telephony.euicc.EuiccManager;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.ISetOpportunisticDataCallback;
import com.android.internal.telephony.ISub;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SubscriptionManager {
    public static final String ACCESS_RULES = "access_rules";
    public static final String ACTION_DEFAULT_SMS_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED";
    public static final String ACTION_DEFAULT_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED";
    public static final String ACTION_MANAGE_SUBSCRIPTION_PLANS = "android.telephony.action.MANAGE_SUBSCRIPTION_PLANS";
    public static final String ACTION_REFRESH_SUBSCRIPTION_PLANS = "android.telephony.action.REFRESH_SUBSCRIPTION_PLANS";
    public static final String ACTION_SUBSCRIPTION_PLANS_CHANGED = "android.telephony.action.SUBSCRIPTION_PLANS_CHANGED";
    @SystemApi
    public static final Uri ADVANCED_CALLING_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "advanced_calling");
    public static final String CARD_ID = "card_id";
    public static final String CARRIER_ID = "carrier_id";
    public static final String CARRIER_NAME = "carrier_name";
    public static final String CB_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
    public static final String CB_ALERT_SOUND_DURATION = "alert_sound_duration";
    public static final String CB_ALERT_SPEECH = "enable_alert_speech";
    public static final String CB_ALERT_VIBRATE = "enable_alert_vibrate";
    public static final String CB_AMBER_ALERT = "enable_cmas_amber_alerts";
    public static final String CB_CHANNEL_50_ALERT = "enable_channel_50_alerts";
    public static final String CB_CMAS_TEST_ALERT = "enable_cmas_test_alerts";
    public static final String CB_EMERGENCY_ALERT = "enable_emergency_alerts";
    public static final String CB_ETWS_TEST_ALERT = "enable_etws_test_alerts";
    public static final String CB_EXTREME_THREAT_ALERT = "enable_cmas_extreme_threat_alerts";
    public static final String CB_OPT_OUT_DIALOG = "show_cmas_opt_out_dialog";
    public static final String CB_SEVERE_THREAT_ALERT = "enable_cmas_severe_threat_alerts";
    public static final String COLOR = "color";
    public static final int COLOR_1 = 0;
    public static final int COLOR_2 = 1;
    public static final int COLOR_3 = 2;
    public static final int COLOR_4 = 3;
    public static final int COLOR_DEFAULT = 0;
    @UnsupportedAppUsage
    public static final Uri CONTENT_URI = Uri.parse("content://telephony/siminfo");
    public static final String DATA_ENABLED_OVERRIDE_RULES = "data_enabled_override_rules";
    public static final String DATA_ROAMING = "data_roaming";
    public static final int DATA_ROAMING_DEFAULT = 0;
    public static final int DATA_ROAMING_DISABLE = 0;
    public static final int DATA_ROAMING_ENABLE = 1;
    private static final boolean DBG = false;
    public static final int DEFAULT_NAME_RES = 17039374;
    public static final int DEFAULT_PHONE_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SIM_SLOT_INDEX = Integer.MAX_VALUE;
    public static final int DEFAULT_SUBSCRIPTION_ID = Integer.MAX_VALUE;
    public static final String DISPLAY_NAME = "display_name";
    public static final int DISPLAY_NUMBER_DEFAULT = 1;
    public static final int DISPLAY_NUMBER_FIRST = 1;
    public static final String DISPLAY_NUMBER_FORMAT = "display_number_format";
    public static final int DISPLAY_NUMBER_LAST = 2;
    public static final int DISPLAY_NUMBER_NONE = 0;
    public static final int DUMMY_SUBSCRIPTION_ID_BASE = -2;
    public static final String EHPLMNS = "ehplmns";
    public static final String ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";
    public static final String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";
    public static final String GROUP_OWNER = "group_owner";
    public static final String GROUP_UUID = "group_uuid";
    public static final String HPLMNS = "hplmns";
    public static final String ICC_ID = "icc_id";
    public static final String IMSI = "imsi";
    public static final int INVALID_PHONE_INDEX = -1;
    public static final int INVALID_SIM_SLOT_INDEX = -1;
    public static final int INVALID_SUBSCRIPTION_ID = -1;
    public static final String ISO_COUNTRY_CODE = "iso_country_code";
    public static final String IS_EMBEDDED = "is_embedded";
    public static final String IS_METERED = "is_metered";
    public static final String IS_OPPORTUNISTIC = "is_opportunistic";
    public static final String IS_REMOVABLE = "is_removable";
    private static final String LOG_TAG = "SubscriptionManager";
    public static final int MAX_SUBSCRIPTION_ID_VALUE = 2147483646;
    public static final String MCC = "mcc";
    public static final String MCC_STRING = "mcc_string";
    public static final int MIN_SUBSCRIPTION_ID_VALUE = 0;
    public static final String MNC = "mnc";
    public static final String MNC_STRING = "mnc_string";
    public static final String NAME_SOURCE = "name_source";
    public static final int NAME_SOURCE_CARRIER = 3;
    public static final int NAME_SOURCE_DEFAULT_SOURCE = 0;
    public static final int NAME_SOURCE_SIM_SOURCE = 1;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public static final int NAME_SOURCE_USER_INPUT = 2;
    public static final String NUMBER = "number";
    public static final String PROFILE_CLASS = "profile_class";
    @SystemApi
    public static final int PROFILE_CLASS_DEFAULT = -1;
    @SystemApi
    public static final int PROFILE_CLASS_OPERATIONAL = 2;
    @SystemApi
    public static final int PROFILE_CLASS_PROVISIONING = 1;
    @SystemApi
    public static final int PROFILE_CLASS_TESTING = 0;
    @SystemApi
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final int SIM_NOT_INSERTED = -1;
    public static final int SIM_PROVISIONED = 0;
    public static final String SIM_PROVISIONING_STATUS = "sim_provisioning_status";
    public static final String SIM_SLOT_INDEX = "sim_id";
    public static final int SLOT_INDEX_FOR_REMOTE_SIM_SUB = -1;
    public static final String SUBSCRIPTION_TYPE = "subscription_type";
    public static final int SUBSCRIPTION_TYPE_LOCAL_SIM = 0;
    public static final int SUBSCRIPTION_TYPE_REMOTE_SIM = 1;
    public static final String SUB_DEFAULT_CHANGED_ACTION = "android.intent.action.SUB_DEFAULT_CHANGED";
    public static final String UNIQUE_KEY_SUBSCRIPTION_ID = "_id";
    private static final boolean VDBG = false;
    @SystemApi
    public static final Uri VT_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "vt_enabled");
    public static final String VT_IMS_ENABLED = "vt_ims_enabled";
    @SystemApi
    public static final Uri WFC_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc");
    public static final String WFC_IMS_ENABLED = "wfc_ims_enabled";
    public static final String WFC_IMS_MODE = "wfc_ims_mode";
    public static final String WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";
    public static final String WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";
    @SystemApi
    public static final Uri WFC_MODE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_mode");
    @SystemApi
    public static final Uri WFC_ROAMING_ENABLED_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_enabled");
    @SystemApi
    public static final Uri WFC_ROAMING_MODE_CONTENT_URI = Uri.withAppendedPath(CONTENT_URI, "wfc_roaming_mode");
    @Deprecated
    public static final String WHITE_LISTED_APN_DATA = "white_listed_apn_data";
    private final Context mContext;
    private volatile INetworkPolicyManager mNetworkPolicy;

    private interface CallISubMethodHelper {
        int callMethod(ISub iSub) throws RemoteException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SubscriptionType {
    }

    public static Uri getUriForSubscriptionId(int subscriptionId) {
        return Uri.withAppendedPath(CONTENT_URI, String.valueOf(subscriptionId));
    }

    public static class OnSubscriptionsChangedListener {
        IOnSubscriptionsChangedListener callback;
        /* access modifiers changed from: private */
        public final Handler mHandler;

        private class OnSubscriptionsChangedListenerHandler extends Handler {
            OnSubscriptionsChangedListenerHandler() {
            }

            OnSubscriptionsChangedListenerHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message msg) {
                OnSubscriptionsChangedListener.this.onSubscriptionsChanged();
            }
        }

        public OnSubscriptionsChangedListener() {
            this.callback = new IOnSubscriptionsChangedListener.Stub() {
                public void onSubscriptionsChanged() {
                    OnSubscriptionsChangedListener.this.mHandler.sendEmptyMessage(0);
                }
            };
            this.mHandler = new OnSubscriptionsChangedListenerHandler();
        }

        public OnSubscriptionsChangedListener(Looper looper) {
            this.callback = new IOnSubscriptionsChangedListener.Stub() {
                public void onSubscriptionsChanged() {
                    OnSubscriptionsChangedListener.this.mHandler.sendEmptyMessage(0);
                }
            };
            this.mHandler = new OnSubscriptionsChangedListenerHandler(looper);
        }

        public void onSubscriptionsChanged() {
        }

        private void log(String s) {
            Rlog.d(SubscriptionManager.LOG_TAG, s);
        }
    }

    @UnsupportedAppUsage
    public SubscriptionManager(Context context) {
        this.mContext = context;
    }

    @Deprecated
    public static SubscriptionManager from(Context context) {
        return (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
    }

    private final INetworkPolicyManager getNetworkPolicy() {
        if (this.mNetworkPolicy == null) {
            this.mNetworkPolicy = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService(Context.NETWORK_POLICY_SERVICE));
        }
        return this.mNetworkPolicy;
    }

    public void addOnSubscriptionsChangedListener(OnSubscriptionsChangedListener listener) {
        String pkgName = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
        try {
            ITelephonyRegistry tr = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
            if (tr != null) {
                tr.addOnSubscriptionsChangedListener(pkgName, listener.callback);
            }
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Remote exception ITelephonyRegistry " + ex);
        }
    }

    public void removeOnSubscriptionsChangedListener(OnSubscriptionsChangedListener listener) {
        String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
        try {
            ITelephonyRegistry tr = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
            if (tr != null) {
                tr.removeOnSubscriptionsChangedListener(pkgForDebug, listener.callback);
            }
        } catch (RemoteException ex) {
            Log.e(LOG_TAG, "Remote exception ITelephonyRegistry " + ex);
        }
    }

    public static class OnOpportunisticSubscriptionsChangedListener {
        IOnSubscriptionsChangedListener callback = new IOnSubscriptionsChangedListener.Stub() {
            public void onSubscriptionsChanged() {
                long identity = Binder.clearCallingIdentity();
                try {
                    OnOpportunisticSubscriptionsChangedListener.this.mExecutor.execute(
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000f: INVOKE  
                          (wrap: java.util.concurrent.Executor : 0x0006: INVOKE  (r2v2 java.util.concurrent.Executor) = 
                          (wrap: android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener : 0x0004: IGET  (r2v1 android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener) = 
                          (r4v0 'this' android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1 A[THIS])
                         android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.1.this$0 android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener)
                         android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.access$100(android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener):java.util.concurrent.Executor type: STATIC)
                          (wrap: android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E : 0x000c: CONSTRUCTOR  (r3v0 android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E) = 
                          (r4v0 'this' android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1 A[THIS])
                         call: android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E.<init>(android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1):void type: CONSTRUCTOR)
                         java.util.concurrent.Executor.execute(java.lang.Runnable):void type: INTERFACE in method: android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.1.onSubscriptionsChanged():void, dex: classes.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:311)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:68)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.util.ArrayList.forEach(ArrayList.java:1259)
                        	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:483)
                        	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
                        	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)
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
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.util.ArrayList.forEach(ArrayList.java:1259)
                        	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:483)
                        	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
                        	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                        Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000c: CONSTRUCTOR  (r3v0 android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E) = 
                          (r4v0 'this' android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1 A[THIS])
                         call: android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E.<init>(android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1):void type: CONSTRUCTOR in method: android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.1.onSubscriptionsChanged():void, dex: classes.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	... 62 more
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                        	... 68 more
                        */
                    /*
                        this = this;
                        long r0 = android.os.Binder.clearCallingIdentity()
                        android.telephony.SubscriptionManager$OnOpportunisticSubscriptionsChangedListener r2 = android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.this     // Catch:{ all -> 0x0017 }
                        java.util.concurrent.Executor r2 = r2.mExecutor     // Catch:{ all -> 0x0017 }
                        android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E r3 = new android.telephony.-$$Lambda$SubscriptionManager$OnOpportunisticSubscriptionsChangedListener$1$3LINuEtkXs3dEn49nQkzD0NIY3E     // Catch:{ all -> 0x0017 }
                        r3.<init>(r4)     // Catch:{ all -> 0x0017 }
                        r2.execute(r3)     // Catch:{ all -> 0x0017 }
                        android.os.Binder.restoreCallingIdentity(r0)
                        return
                    L_0x0017:
                        r2 = move-exception
                        android.os.Binder.restoreCallingIdentity(r0)
                        throw r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.telephony.SubscriptionManager.OnOpportunisticSubscriptionsChangedListener.AnonymousClass1.onSubscriptionsChanged():void");
                }
            };
            /* access modifiers changed from: private */
            public Executor mExecutor;

            public void onOpportunisticSubscriptionsChanged() {
            }

            /* access modifiers changed from: private */
            public void setExecutor(Executor executor) {
                this.mExecutor = executor;
            }

            private void log(String s) {
                Rlog.d(SubscriptionManager.LOG_TAG, s);
            }
        }

        public void addOnOpportunisticSubscriptionsChangedListener(Executor executor, OnOpportunisticSubscriptionsChangedListener listener) {
            if (executor != null && listener != null) {
                String pkgName = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
                listener.setExecutor(executor);
                try {
                    ITelephonyRegistry tr = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
                    if (tr != null) {
                        tr.addOnOpportunisticSubscriptionsChangedListener(pkgName, listener.callback);
                    }
                } catch (RemoteException ex) {
                    Log.e(LOG_TAG, "Remote exception ITelephonyRegistry " + ex);
                }
            }
        }

        public void removeOnOpportunisticSubscriptionsChangedListener(OnOpportunisticSubscriptionsChangedListener listener) {
            Preconditions.checkNotNull(listener, "listener cannot be null");
            String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
            try {
                ITelephonyRegistry tr = ITelephonyRegistry.Stub.asInterface(ServiceManager.getService("telephony.registry"));
                if (tr != null) {
                    tr.removeOnSubscriptionsChangedListener(pkgForDebug, listener.callback);
                }
            } catch (RemoteException ex) {
                Log.e(LOG_TAG, "Remote exception ITelephonyRegistry " + ex);
            }
        }

        public SubscriptionInfo getActiveSubscriptionInfo(int subId) {
            if (!isValidSubscriptionId(subId)) {
                return null;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getActiveSubscriptionInfo(subId, this.mContext.getOpPackageName());
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        public SubscriptionInfo getActiveSubscriptionInfoForIccIndex(String iccId) {
            if (iccId == null) {
                logd("[getActiveSubscriptionInfoForIccIndex]- null iccid");
                return null;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getActiveSubscriptionInfoForIccId(iccId, this.mContext.getOpPackageName());
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(int slotIndex) {
            if (!isValidSlotIndex(slotIndex)) {
                logd("[getActiveSubscriptionInfoForSimSlotIndex]- invalid slotIndex");
                return null;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getActiveSubscriptionInfoForSimSlotIndex(slotIndex, this.mContext.getOpPackageName());
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        @UnsupportedAppUsage
        public List<SubscriptionInfo> getAllSubscriptionInfoList() {
            List<SubscriptionInfo> result = null;
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    result = iSub.getAllSubInfoList(this.mContext.getOpPackageName());
                }
            } catch (RemoteException e) {
            }
            if (result == null) {
                return new ArrayList<>();
            }
            return result;
        }

        public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
            return getActiveSubscriptionInfoList(true);
        }

        public List<SubscriptionInfo> getActiveSubscriptionInfoList(boolean userVisibleOnly) {
            List<SubscriptionInfo> activeList = null;
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    activeList = iSub.getActiveSubscriptionInfoList(this.mContext.getOpPackageName());
                }
            } catch (RemoteException e) {
            }
            if (!userVisibleOnly || activeList == null) {
                return activeList;
            }
            return (List) activeList.stream().filter(new Predicate() {
                public final boolean test(Object obj) {
                    return SubscriptionManager.this.isSubscriptionVisible((SubscriptionInfo) obj);
                }
            }).collect(Collectors.toList());
        }

        @SystemApi
        public List<SubscriptionInfo> getAvailableSubscriptionInfoList() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getAvailableSubscriptionInfoList(this.mContext.getOpPackageName());
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        public List<SubscriptionInfo> getAccessibleSubscriptionInfoList() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getAccessibleSubscriptionInfoList(this.mContext.getOpPackageName());
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        @SystemApi
        public void requestEmbeddedSubscriptionInfoListRefresh() {
            int cardId = TelephonyManager.from(this.mContext).getCardIdForDefaultEuicc();
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.requestEmbeddedSubscriptionInfoListRefresh(cardId);
                }
            } catch (RemoteException e) {
                logd("requestEmbeddedSubscriptionInfoListFresh for card = " + cardId + " failed.");
            }
        }

        @SystemApi
        public void requestEmbeddedSubscriptionInfoListRefresh(int cardId) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.requestEmbeddedSubscriptionInfoListRefresh(cardId);
                }
            } catch (RemoteException e) {
                logd("requestEmbeddedSubscriptionInfoListFresh for card = " + cardId + " failed.");
            }
        }

        @UnsupportedAppUsage
        public int getAllSubscriptionInfoCount() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getAllSubInfoCount(this.mContext.getOpPackageName());
                }
                return 0;
            } catch (RemoteException e) {
                return 0;
            }
        }

        public int getActiveSubscriptionInfoCount() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getActiveSubInfoCount(this.mContext.getOpPackageName());
                }
                return 0;
            } catch (RemoteException e) {
                return 0;
            }
        }

        public int getActiveSubscriptionInfoCountMax() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getActiveSubInfoCountMax();
                }
                return 0;
            } catch (RemoteException e) {
                return 0;
            }
        }

        public Uri addSubscriptionInfoRecord(String iccId, int slotIndex) {
            if (iccId == null) {
                logd("[addSubscriptionInfoRecord]- null iccId");
            }
            if (!isValidSlotIndex(slotIndex)) {
                logd("[addSubscriptionInfoRecord]- invalid slotIndex");
            }
            addSubscriptionInfoRecord(iccId, (String) null, slotIndex, 0);
            return null;
        }

        public void addSubscriptionInfoRecord(String uniqueId, String displayName, int slotIndex, int subscriptionType) {
            if (uniqueId == null) {
                Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
                return;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub == null) {
                    Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- ISub service is null");
                    return;
                }
                int result = iSub.addSubInfo(uniqueId, displayName, slotIndex, subscriptionType);
                if (result < 0) {
                    Log.e(LOG_TAG, "Adding of subscription didn't succeed: error = " + result);
                    return;
                }
                logd("successfully added new subscription");
            } catch (RemoteException e) {
            }
        }

        public void removeSubscriptionInfoRecord(String uniqueId, int subscriptionType) {
            if (uniqueId == null) {
                Log.e(LOG_TAG, "[addSubscriptionInfoRecord]- uniqueId is null");
                return;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub == null) {
                    Log.e(LOG_TAG, "[removeSubscriptionInfoRecord]- ISub service is null");
                    return;
                }
                int result = iSub.removeSubInfo(uniqueId, subscriptionType);
                if (result < 0) {
                    Log.e(LOG_TAG, "Removal of subscription didn't succeed: error = " + result);
                    return;
                }
                logd("successfully removed subscription");
            } catch (RemoteException e) {
            }
        }

        @UnsupportedAppUsage
        public int setIconTint(int tint, int subId) {
            return setSubscriptionPropertyHelper(subId, "setIconTint", new CallISubMethodHelper(tint, subId) {
                private final /* synthetic */ int f$0;
                private final /* synthetic */ int f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final int callMethod(ISub iSub) {
                    return iSub.setIconTint(this.f$0, this.f$1);
                }
            });
        }

        @UnsupportedAppUsage
        public int setDisplayName(String displayName, int subId, int nameSource) {
            return setSubscriptionPropertyHelper(subId, "setDisplayName", new CallISubMethodHelper(displayName, subId, nameSource) {
                private final /* synthetic */ String f$0;
                private final /* synthetic */ int f$1;
                private final /* synthetic */ int f$2;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final int callMethod(ISub iSub) {
                    return iSub.setDisplayNameUsingSrc(this.f$0, this.f$1, this.f$2);
                }
            });
        }

        @UnsupportedAppUsage
        public int setDisplayNumber(String number, int subId) {
            if (number != null) {
                return setSubscriptionPropertyHelper(subId, "setDisplayNumber", new CallISubMethodHelper(number, subId) {
                    private final /* synthetic */ String f$0;
                    private final /* synthetic */ int f$1;

                    {
                        this.f$0 = r1;
                        this.f$1 = r2;
                    }

                    public final int callMethod(ISub iSub) {
                        return iSub.setDisplayNumber(this.f$0, this.f$1);
                    }
                });
            }
            logd("[setDisplayNumber]- fail");
            return -1;
        }

        @UnsupportedAppUsage
        public int setDataRoaming(int roaming, int subId) {
            return setSubscriptionPropertyHelper(subId, "setDataRoaming", new CallISubMethodHelper(roaming, subId) {
                private final /* synthetic */ int f$0;
                private final /* synthetic */ int f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final int callMethod(ISub iSub) {
                    return iSub.setDataRoaming(this.f$0, this.f$1);
                }
            });
        }

        public static int getSlotIndex(int subscriptionId) {
            isValidSubscriptionId(subscriptionId);
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getSlotIndex(subscriptionId);
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public int[] getSubscriptionIds(int slotIndex) {
            return getSubId(slotIndex);
        }

        @UnsupportedAppUsage
        public static int[] getSubId(int slotIndex) {
            if (!isValidSlotIndex(slotIndex)) {
                logd("[getSubId]- fail");
                return null;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getSubId(slotIndex);
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        @UnsupportedAppUsage(maxTargetSdk = 28)
        public static int getPhoneId(int subId) {
            if (!isValidSubscriptionId(subId)) {
                return -1;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getPhoneId(subId);
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        private static void logd(String msg) {
            Rlog.d(LOG_TAG, msg);
        }

        private static void loge(String msg) {
            Rlog.e(LOG_TAG, msg);
        }

        public static int getDefaultSubscriptionId() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getDefaultSubId();
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public static int getDefaultVoiceSubscriptionId() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getDefaultVoiceSubId();
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public void setDefaultVoiceSubId(int subId) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.setDefaultVoiceSubId(subId);
                }
            } catch (RemoteException e) {
            }
        }

        @UnsupportedAppUsage
        public SubscriptionInfo getDefaultVoiceSubscriptionInfo() {
            return getActiveSubscriptionInfo(getDefaultVoiceSubscriptionId());
        }

        @UnsupportedAppUsage
        public static int getDefaultVoicePhoneId() {
            return getPhoneId(getDefaultVoiceSubscriptionId());
        }

        public static int getDefaultSmsSubscriptionId() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getDefaultSmsSubId();
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        @SystemApi
        public void setDefaultSmsSubId(int subscriptionId) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.setDefaultSmsSubId(subscriptionId);
                }
            } catch (RemoteException ex) {
                ex.rethrowFromSystemServer();
            }
        }

        public SubscriptionInfo getDefaultSmsSubscriptionInfo() {
            return getActiveSubscriptionInfo(getDefaultSmsSubscriptionId());
        }

        @UnsupportedAppUsage
        public int getDefaultSmsPhoneId() {
            return getPhoneId(getDefaultSmsSubscriptionId());
        }

        public static int getDefaultDataSubscriptionId() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getDefaultDataSubId();
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        @SystemApi
        public void setDefaultDataSubId(int subscriptionId) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.setDefaultDataSubId(subscriptionId);
                }
            } catch (RemoteException e) {
            }
        }

        @UnsupportedAppUsage
        public SubscriptionInfo getDefaultDataSubscriptionInfo() {
            return getActiveSubscriptionInfo(getDefaultDataSubscriptionId());
        }

        @UnsupportedAppUsage
        public int getDefaultDataPhoneId() {
            return getPhoneId(getDefaultDataSubscriptionId());
        }

        public void clearSubscriptionInfo() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.clearSubInfo();
                }
            } catch (RemoteException e) {
            }
        }

        public boolean allDefaultsSelected() {
            if (isValidSubscriptionId(getDefaultDataSubscriptionId()) && isValidSubscriptionId(getDefaultSmsSubscriptionId()) && isValidSubscriptionId(getDefaultVoiceSubscriptionId())) {
                return true;
            }
            return false;
        }

        public static boolean isValidSubscriptionId(int subscriptionId) {
            return subscriptionId > -1;
        }

        public static boolean isUsableSubscriptionId(int subscriptionId) {
            return isUsableSubIdValue(subscriptionId);
        }

        @UnsupportedAppUsage(maxTargetSdk = 28)
        public static boolean isUsableSubIdValue(int subId) {
            return subId >= 0 && subId <= 2147483646;
        }

        @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public static boolean isValidSlotIndex(int slotIndex) {
            return slotIndex >= 0 && slotIndex < TelephonyManager.getDefault().getSimCount();
        }

        @UnsupportedAppUsage
        public static boolean isValidPhoneId(int phoneId) {
            return phoneId >= 0 && phoneId < TelephonyManager.getDefault().getPhoneCount();
        }

        @UnsupportedAppUsage(maxTargetSdk = 28)
        public static void putPhoneIdAndSubIdExtra(Intent intent, int phoneId) {
            int[] subIds = getSubId(phoneId);
            if (subIds == null || subIds.length <= 0) {
                logd("putPhoneIdAndSubIdExtra: no valid subs");
                intent.putExtra("phone", phoneId);
                return;
            }
            putPhoneIdAndSubIdExtra(intent, phoneId, subIds[0]);
        }

        @UnsupportedAppUsage
        public static void putPhoneIdAndSubIdExtra(Intent intent, int phoneId, int subId) {
            intent.putExtra(PhoneConstants.SUBSCRIPTION_KEY, subId);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", subId);
            intent.putExtra("phone", phoneId);
        }

        @UnsupportedAppUsage
        public int[] getActiveSubscriptionIdList() {
            int[] subId = null;
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    subId = iSub.getActiveSubIdList(true);
                }
            } catch (RemoteException e) {
            }
            if (subId == null) {
                return new int[0];
            }
            return subId;
        }

        public boolean isNetworkRoaming(int subId) {
            if (getPhoneId(subId) < 0) {
                return false;
            }
            return TelephonyManager.getDefault().isNetworkRoaming(subId);
        }

        public static int getSimStateForSlotIndex(int slotIndex) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getSimStateForSlotIndex(slotIndex);
                }
                return 0;
            } catch (RemoteException e) {
                return 0;
            }
        }

        public static void setSubscriptionProperty(int subId, String propKey, String propValue) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.setSubscriptionProperty(subId, propKey, propValue);
                }
            } catch (RemoteException e) {
            }
        }

        private static String getSubscriptionProperty(int subId, String propKey, Context context) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getSubscriptionProperty(subId, propKey, context.getOpPackageName());
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        }

        public static boolean getBooleanSubscriptionProperty(int subId, String propKey, boolean defValue, Context context) {
            String result = getSubscriptionProperty(subId, propKey, context);
            if (result != null) {
                try {
                    return Integer.parseInt(result) == 1;
                } catch (NumberFormatException e) {
                    logd("getBooleanSubscriptionProperty NumberFormat exception");
                }
            }
            return defValue;
        }

        public static int getIntegerSubscriptionProperty(int subId, String propKey, int defValue, Context context) {
            String result = getSubscriptionProperty(subId, propKey, context);
            if (result != null) {
                try {
                    return Integer.parseInt(result);
                } catch (NumberFormatException e) {
                    logd("getBooleanSubscriptionProperty NumberFormat exception");
                }
            }
            return defValue;
        }

        @UnsupportedAppUsage
        public static Resources getResourcesForSubId(Context context, int subId) {
            return getResourcesForSubId(context, subId, false);
        }

        public static Resources getResourcesForSubId(Context context, int subId, boolean useRootLocale) {
            SubscriptionInfo subInfo = from(context).getActiveSubscriptionInfo(subId);
            Configuration config = context.getResources().getConfiguration();
            Configuration newConfig = new Configuration();
            newConfig.setTo(config);
            if (subInfo != null) {
                newConfig.mcc = subInfo.getMcc();
                newConfig.mnc = subInfo.getMnc();
                if (newConfig.mnc == 0) {
                    newConfig.mnc = 65535;
                }
            }
            if (useRootLocale) {
                newConfig.setLocale(Locale.ROOT);
            }
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            DisplayMetrics newMetrics = new DisplayMetrics();
            newMetrics.setTo(metrics);
            return new Resources(context.getResources().getAssets(), newMetrics, newConfig);
        }

        public boolean isActiveSubscriptionId(int subscriptionId) {
            return isActiveSubId(subscriptionId);
        }

        @UnsupportedAppUsage
        public boolean isActiveSubId(int subId) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.isActiveSubId(subId, this.mContext.getOpPackageName());
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        }

        public List<SubscriptionPlan> getSubscriptionPlans(int subId) {
            try {
                SubscriptionPlan[] subscriptionPlans = getNetworkPolicy().getSubscriptionPlans(subId, this.mContext.getOpPackageName());
                return subscriptionPlans == null ? Collections.emptyList() : Arrays.asList(subscriptionPlans);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSubscriptionPlans(int subId, List<SubscriptionPlan> plans) {
            try {
                getNetworkPolicy().setSubscriptionPlans(subId, (SubscriptionPlan[]) plans.toArray(new SubscriptionPlan[plans.size()]), this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private String getSubscriptionPlansOwner(int subId) {
            try {
                return getNetworkPolicy().getSubscriptionPlansOwner(subId);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSubscriptionOverrideUnmetered(int subId, boolean overrideUnmetered, long timeoutMillis) {
            int overrideValue = overrideUnmetered;
            try {
                int overrideValue2 = (int) overrideValue;
                getNetworkPolicy().setSubscriptionOverride(subId, 1, overrideValue2, timeoutMillis, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void setSubscriptionOverrideCongested(int subId, boolean overrideCongested, long timeoutMillis) {
            try {
                getNetworkPolicy().setSubscriptionOverride(subId, 2, overrideCongested ? 2 : 0, timeoutMillis, this.mContext.getOpPackageName());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public Intent createManageSubscriptionIntent(int subId) {
            String owner = getSubscriptionPlansOwner(subId);
            if (owner == null || getSubscriptionPlans(subId).isEmpty()) {
                return null;
            }
            Intent intent = new Intent(ACTION_MANAGE_SUBSCRIPTION_PLANS);
            intent.setPackage(owner);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", subId);
            if (this.mContext.getPackageManager().queryIntentActivities(intent, 65536).isEmpty()) {
                return null;
            }
            return intent;
        }

        private Intent createRefreshSubscriptionIntent(int subId) {
            String owner = getSubscriptionPlansOwner(subId);
            if (owner == null || getSubscriptionPlans(subId).isEmpty()) {
                return null;
            }
            Intent intent = new Intent(ACTION_REFRESH_SUBSCRIPTION_PLANS);
            intent.addFlags(268435456);
            intent.setPackage(owner);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", subId);
            if (this.mContext.getPackageManager().queryBroadcastReceivers(intent, 0).isEmpty()) {
                return null;
            }
            return intent;
        }

        public boolean isSubscriptionPlansRefreshSupported(int subId) {
            return createRefreshSubscriptionIntent(subId) != null;
        }

        public void requestSubscriptionPlansRefresh(int subId) {
            Intent intent = createRefreshSubscriptionIntent(subId);
            BroadcastOptions options = BroadcastOptions.makeBasic();
            options.setTemporaryAppWhitelistDuration(TimeUnit.MINUTES.toMillis(1));
            this.mContext.sendBroadcast(intent, (String) null, options.toBundle());
        }

        public boolean canManageSubscription(SubscriptionInfo info) {
            return canManageSubscription(info, this.mContext.getPackageName());
        }

        public boolean canManageSubscription(SubscriptionInfo info, String packageName) {
            if (!info.isEmbedded()) {
                throw new IllegalArgumentException("Not an embedded subscription");
            } else if (info.getAccessRules() == null) {
                return false;
            } else {
                try {
                    PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(packageName, 64);
                    for (UiccAccessRule rule : info.getAccessRules()) {
                        if (rule.getCarrierPrivilegeStatus(packageInfo) == 1) {
                            return true;
                        }
                    }
                    return false;
                } catch (PackageManager.NameNotFoundException e) {
                    throw new IllegalArgumentException("Unknown package: " + packageName, e);
                }
            }
        }

        @SystemApi
        public void setPreferredDataSubscriptionId(int subId, boolean needValidation, final Executor executor, final Consumer<Integer> callback) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.setPreferredDataSubscriptionId(subId, needValidation, new ISetOpportunisticDataCallback.Stub() {
                        public void onComplete(int result) {
                            if (executor != null && callback != null) {
                                Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, callback, result) {
                                    private final /* synthetic */ Executor f$0;
                                    private final /* synthetic */ Consumer f$1;
                                    private final /* synthetic */ int f$2;

                                    {
                                        this.f$0 = r1;
                                        this.f$1 = r2;
                                        this.f$2 = r3;
                                    }

                                    public final void runOrThrow() {
                                        this.f$0.execute(new Runnable(this.f$1, this.f$2) {
                                            private final /* synthetic */ Consumer f$0;
                                            private final /* synthetic */ int f$1;

                                            {
                                                this.f$0 = r1;
                                                this.f$1 = r2;
                                            }

                                            public final void run() {
                                                this.f$0.accept(Integer.valueOf(this.f$1));
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            } catch (RemoteException e) {
            }
        }

        public int getPreferredDataSubscriptionId() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getPreferredDataSubscriptionId();
                }
                return Integer.MAX_VALUE;
            } catch (RemoteException e) {
                return Integer.MAX_VALUE;
            }
        }

        public List<SubscriptionInfo> getOpportunisticSubscriptions() {
            String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
            List<SubscriptionInfo> subInfoList = null;
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    subInfoList = iSub.getOpportunisticSubscriptions(pkgForDebug);
                }
            } catch (RemoteException e) {
            }
            if (subInfoList == null) {
                return new ArrayList<>();
            }
            return subInfoList;
        }

        public void switchToSubscription(int subId, PendingIntent callbackIntent) {
            Preconditions.checkNotNull(callbackIntent, "callbackIntent cannot be null");
            new EuiccManager(this.mContext).switchToSubscription(subId, callbackIntent);
        }

        public boolean setOpportunistic(boolean opportunistic, int subId) {
            return setSubscriptionPropertyHelper(subId, "setOpportunistic", 
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: RETURN  
                  (wrap: boolean : ?: TERNARYnull = ((wrap: int : 0x0008: INVOKE  (r0v1 int) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r4v0 'subId' int)
                  ("setOpportunistic")
                  (wrap: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk : 0x0005: CONSTRUCTOR  (r1v0 android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r3v0 'opportunistic' boolean)
                  (r4v0 'subId' int)
                 call: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk.<init>(android.telephony.SubscriptionManager, boolean, int):void type: CONSTRUCTOR)
                 android.telephony.SubscriptionManager.setSubscriptionPropertyHelper(int, java.lang.String, android.telephony.SubscriptionManager$CallISubMethodHelper):int type: DIRECT) == (1 int)) ? true : false)
                 in method: android.telephony.SubscriptionManager.setOpportunistic(boolean, int):boolean, dex: classes.dex
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
                	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                	at java.util.ArrayList.forEach(ArrayList.java:1259)
                	at java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                	at java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:483)
                	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
                	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:485)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: TERNARYnull = ((wrap: int : 0x0008: INVOKE  (r0v1 int) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r4v0 'subId' int)
                  ("setOpportunistic")
                  (wrap: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk : 0x0005: CONSTRUCTOR  (r1v0 android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r3v0 'opportunistic' boolean)
                  (r4v0 'subId' int)
                 call: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk.<init>(android.telephony.SubscriptionManager, boolean, int):void type: CONSTRUCTOR)
                 android.telephony.SubscriptionManager.setSubscriptionPropertyHelper(int, java.lang.String, android.telephony.SubscriptionManager$CallISubMethodHelper):int type: DIRECT) == (1 int)) ? true : false in method: android.telephony.SubscriptionManager.setOpportunistic(boolean, int):boolean, dex: classes.dex
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:314)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                	... 29 more
                Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0008: INVOKE  (r0v1 int) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r4v0 'subId' int)
                  ("setOpportunistic")
                  (wrap: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk : 0x0005: CONSTRUCTOR  (r1v0 android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r3v0 'opportunistic' boolean)
                  (r4v0 'subId' int)
                 call: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk.<init>(android.telephony.SubscriptionManager, boolean, int):void type: CONSTRUCTOR)
                 android.telephony.SubscriptionManager.setSubscriptionPropertyHelper(int, java.lang.String, android.telephony.SubscriptionManager$CallISubMethodHelper):int type: DIRECT in method: android.telephony.SubscriptionManager.setOpportunistic(boolean, int):boolean, dex: classes.dex
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.ConditionGen.addCompare(ConditionGen.java:129)
                	at jadx.core.codegen.ConditionGen.add(ConditionGen.java:57)
                	at jadx.core.codegen.ConditionGen.add(ConditionGen.java:46)
                	at jadx.core.codegen.InsnGen.makeTernary(InsnGen.java:948)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:476)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	... 33 more
                Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0005: CONSTRUCTOR  (r1v0 android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk) = 
                  (r2v0 'this' android.telephony.SubscriptionManager A[THIS])
                  (r3v0 'opportunistic' boolean)
                  (r4v0 'subId' int)
                 call: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk.<init>(android.telephony.SubscriptionManager, boolean, int):void type: CONSTRUCTOR in method: android.telephony.SubscriptionManager.setOpportunistic(boolean, int):boolean, dex: classes.dex
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:256)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:123)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:107)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:787)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:728)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	... 41 more
                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk, state: NOT_LOADED
                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:260)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:606)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:231)
                	... 47 more
                */
            /*
                this = this;
                java.lang.String r0 = "setOpportunistic"
                android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk r1 = new android.telephony.-$$Lambda$SubscriptionManager$NazcIP1h3U0cfnY--L174e3u4tk
                r1.<init>(r2, r3, r4)
                int r0 = r2.setSubscriptionPropertyHelper(r4, r0, r1)
                r1 = 1
                if (r0 != r1) goto L_0x0010
                goto L_0x0011
            L_0x0010:
                r1 = 0
            L_0x0011:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.SubscriptionManager.setOpportunistic(boolean, int):boolean");
        }

        public ParcelUuid createSubscriptionGroup(List<Integer> subIdList) {
            Preconditions.checkNotNull(subIdList, "can't create group for null subId list");
            String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
            ParcelUuid groupUuid = null;
            int[] subIdArray = subIdList.stream().mapToInt($$Lambda$SubscriptionManager$W41XrJh1c8ZX_i9kWtj1rBU9l8o.INSTANCE).toArray();
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    groupUuid = iSub.createSubscriptionGroup(subIdArray, pkgForDebug);
                } else if (!isSystemProcess()) {
                    throw new IllegalStateException("telephony service is null.");
                }
            } catch (RemoteException ex) {
                loge("createSubscriptionGroup RemoteException " + ex);
                if (!isSystemProcess()) {
                    ex.rethrowAsRuntimeException();
                }
            }
            return groupUuid;
        }

        public void addSubscriptionsIntoGroup(List<Integer> subIdList, ParcelUuid groupUuid) {
            Preconditions.checkNotNull(subIdList, "subIdList can't be null.");
            Preconditions.checkNotNull(groupUuid, "groupUuid can't be null.");
            String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
            int[] subIdArray = subIdList.stream().mapToInt($$Lambda$SubscriptionManager$D5_PmvQ13e0qLtSnBvNd4R7l2qA.INSTANCE).toArray();
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.addSubscriptionsIntoGroup(subIdArray, groupUuid, pkgForDebug);
                } else if (!isSystemProcess()) {
                    throw new IllegalStateException("telephony service is null.");
                }
            } catch (RemoteException ex) {
                loge("addSubscriptionsIntoGroup RemoteException " + ex);
                if (!isSystemProcess()) {
                    ex.rethrowAsRuntimeException();
                }
            }
        }

        private boolean isSystemProcess() {
            return Process.myUid() == 1000;
        }

        public void removeSubscriptionsFromGroup(List<Integer> subIdList, ParcelUuid groupUuid) {
            Preconditions.checkNotNull(subIdList, "subIdList can't be null.");
            Preconditions.checkNotNull(groupUuid, "groupUuid can't be null.");
            String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
            int[] subIdArray = subIdList.stream().mapToInt($$Lambda$SubscriptionManager$EEe2NsDpuDogw8UijVBhj7Vuhk.INSTANCE).toArray();
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    iSub.removeSubscriptionsFromGroup(subIdArray, groupUuid, pkgForDebug);
                } else if (!isSystemProcess()) {
                    throw new IllegalStateException("telephony service is null.");
                }
            } catch (RemoteException ex) {
                loge("removeSubscriptionsFromGroup RemoteException " + ex);
                if (!isSystemProcess()) {
                    ex.rethrowAsRuntimeException();
                }
            }
        }

        public List<SubscriptionInfo> getSubscriptionsInGroup(ParcelUuid groupUuid) {
            Preconditions.checkNotNull(groupUuid, "groupUuid can't be null");
            String pkgForDebug = this.mContext != null ? this.mContext.getOpPackageName() : MediaStore.UNKNOWN_STRING;
            List<SubscriptionInfo> result = null;
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    result = iSub.getSubscriptionsInGroup(groupUuid, pkgForDebug);
                } else if (!isSystemProcess()) {
                    throw new IllegalStateException("telephony service is null.");
                }
            } catch (RemoteException ex) {
                loge("removeSubscriptionsFromGroup RemoteException " + ex);
                if (!isSystemProcess()) {
                    ex.rethrowAsRuntimeException();
                }
            }
            return result;
        }

        /* access modifiers changed from: private */
        public boolean isSubscriptionVisible(SubscriptionInfo info) {
            if (info == null) {
                return false;
            }
            if (info.getGroupUuid() == null || !info.isOpportunistic()) {
                return true;
            }
            if (TelephonyManager.from(this.mContext).hasCarrierPrivileges(info.getSubscriptionId()) || (info.isEmbedded() && canManageSubscription(info))) {
                return true;
            }
            return false;
        }

        public List<SubscriptionInfo> getSelectableSubscriptionInfoList() {
            List<SubscriptionInfo> availableList = getAvailableSubscriptionInfoList();
            if (availableList == null) {
                return null;
            }
            List<SubscriptionInfo> selectableList = new ArrayList<>();
            Map<ParcelUuid, SubscriptionInfo> groupMap = new HashMap<>();
            for (SubscriptionInfo info : availableList) {
                if (isSubscriptionVisible(info)) {
                    ParcelUuid groupUuid = info.getGroupUuid();
                    if (groupUuid == null) {
                        selectableList.add(info);
                    } else if (!groupMap.containsKey(groupUuid) || (groupMap.get(groupUuid).getSimSlotIndex() == -1 && info.getSimSlotIndex() != -1)) {
                        selectableList.remove(groupMap.get(groupUuid));
                        selectableList.add(info);
                        groupMap.put(groupUuid, info);
                    }
                }
            }
            return selectableList;
        }

        @SystemApi
        public boolean setSubscriptionEnabled(int subscriptionId, boolean enable) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.setSubscriptionEnabled(enable, subscriptionId);
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        }

        @SystemApi
        public boolean isSubscriptionEnabled(int subscriptionId) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.isSubscriptionEnabled(subscriptionId);
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        }

        @SystemApi
        public int getEnabledSubscriptionId(int slotIndex) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getEnabledSubscriptionId(slotIndex);
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }

        public boolean setAlwaysAllowMmsData(int subId, boolean alwaysAllow) {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.setAlwaysAllowMmsData(subId, alwaysAllow);
                }
                return false;
            } catch (RemoteException ex) {
                if (isSystemProcess()) {
                    return false;
                }
                ex.rethrowAsRuntimeException();
                return false;
            }
        }

        private int setSubscriptionPropertyHelper(int subId, String methodName, CallISubMethodHelper helper) {
            if (!isValidSubscriptionId(subId)) {
                logd("[" + methodName + "]- fail");
                return -1;
            }
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return helper.callMethod(iSub);
                }
                return 0;
            } catch (RemoteException e) {
                return 0;
            }
        }

        public static int getActiveDataSubscriptionId() {
            try {
                ISub iSub = ISub.Stub.asInterface(ServiceManager.getService("isub"));
                if (iSub != null) {
                    return iSub.getActiveDataSubscriptionId();
                }
                return -1;
            } catch (RemoteException e) {
                return -1;
            }
        }
    }
