package android.preference;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PreferenceManager {
    public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    public static final String METADATA_KEY_PREFERENCES = "android.preference";
    private static final int STORAGE_CREDENTIAL_PROTECTED = 2;
    private static final int STORAGE_DEFAULT = 0;
    private static final int STORAGE_DEVICE_PROTECTED = 1;
    private static final String TAG = "PreferenceManager";
    private Activity mActivity;
    @UnsupportedAppUsage
    private List<OnActivityDestroyListener> mActivityDestroyListeners;
    private List<OnActivityResultListener> mActivityResultListeners;
    private List<OnActivityStopListener> mActivityStopListeners;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private PreferenceFragment mFragment;
    private long mNextId = 0;
    private int mNextRequestCode;
    private boolean mNoCommit;
    @UnsupportedAppUsage
    private OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    private PreferenceDataStore mPreferenceDataStore;
    private PreferenceScreen mPreferenceScreen;
    private List<DialogInterface> mPreferencesScreens;
    @UnsupportedAppUsage
    private SharedPreferences mSharedPreferences;
    private int mSharedPreferencesMode;
    private String mSharedPreferencesName;
    private int mStorage = 0;

    @Deprecated
    public interface OnActivityDestroyListener {
        void onActivityDestroy();
    }

    @Deprecated
    public interface OnActivityResultListener {
        boolean onActivityResult(int i, int i2, Intent intent);
    }

    @Deprecated
    public interface OnActivityStopListener {
        void onActivityStop();
    }

    @Deprecated
    public interface OnPreferenceTreeClickListener {
        boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference);
    }

    @UnsupportedAppUsage
    public PreferenceManager(Activity activity, int firstRequestCode) {
        this.mActivity = activity;
        this.mNextRequestCode = firstRequestCode;
        init(activity);
    }

    @UnsupportedAppUsage
    PreferenceManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setSharedPreferencesName(getDefaultSharedPreferencesName(context));
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void setFragment(PreferenceFragment fragment) {
        this.mFragment = fragment;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public PreferenceFragment getFragment() {
        return this.mFragment;
    }

    public void setPreferenceDataStore(PreferenceDataStore dataStore) {
        this.mPreferenceDataStore = dataStore;
    }

    public PreferenceDataStore getPreferenceDataStore() {
        return this.mPreferenceDataStore;
    }

    private List<ResolveInfo> queryIntentActivities(Intent queryIntent) {
        return this.mContext.getPackageManager().queryIntentActivities(queryIntent, 128);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: android.preference.PreferenceScreen} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.preference.PreferenceScreen inflateFromIntent(android.content.Intent r12, android.preference.PreferenceScreen r13) {
        /*
            r11 = this;
            java.util.List r0 = r11.queryIntentActivities(r12)
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            int r2 = r0.size()
            r3 = 1
            int r2 = r2 - r3
        L_0x000f:
            if (r2 < 0) goto L_0x009e
            java.lang.Object r4 = r0.get(r2)
            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
            android.content.pm.ActivityInfo r4 = r4.activityInfo
            android.os.Bundle r5 = r4.metaData
            if (r5 == 0) goto L_0x009a
            java.lang.String r6 = "android.preference"
            boolean r6 = r5.containsKey(r6)
            if (r6 != 0) goto L_0x0026
            goto L_0x009a
        L_0x0026:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = r4.packageName
            r6.append(r7)
            java.lang.String r7 = ":"
            r6.append(r7)
            android.os.Bundle r7 = r4.metaData
            java.lang.String r8 = "android.preference"
            int r7 = r7.getInt(r8)
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            boolean r7 = r1.contains(r6)
            if (r7 != 0) goto L_0x009a
            r1.add(r6)
            android.content.Context r7 = r11.mContext     // Catch:{ NameNotFoundException -> 0x0074 }
            java.lang.String r8 = r4.packageName     // Catch:{ NameNotFoundException -> 0x0074 }
            r9 = 0
            android.content.Context r7 = r7.createPackageContext(r8, r9)     // Catch:{ NameNotFoundException -> 0x0074 }
            android.preference.PreferenceInflater r8 = new android.preference.PreferenceInflater
            r8.<init>(r7, r11)
            android.content.pm.PackageManager r9 = r7.getPackageManager()
            java.lang.String r10 = "android.preference"
            android.content.res.XmlResourceParser r9 = r4.loadXmlMetaData(r9, r10)
            java.lang.Object r10 = r8.inflate((org.xmlpull.v1.XmlPullParser) r9, r13, (boolean) r3)
            r13 = r10
            android.preference.PreferenceScreen r13 = (android.preference.PreferenceScreen) r13
            r9.close()
            goto L_0x009a
        L_0x0074:
            r7 = move-exception
            java.lang.String r8 = "PreferenceManager"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Could not create context for "
            r9.append(r10)
            java.lang.String r10 = r4.packageName
            r9.append(r10)
            java.lang.String r10 = ": "
            r9.append(r10)
            java.lang.String r10 = android.util.Log.getStackTraceString(r7)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.w((java.lang.String) r8, (java.lang.String) r9)
        L_0x009a:
            int r2 = r2 + -1
            goto L_0x000f
        L_0x009e:
            r13.onAttachedToHierarchy(r11)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceManager.inflateFromIntent(android.content.Intent, android.preference.PreferenceScreen):android.preference.PreferenceScreen");
    }

    @UnsupportedAppUsage
    public PreferenceScreen inflateFromResource(Context context, int resId, PreferenceScreen rootPreferences) {
        setNoCommit(true);
        PreferenceScreen rootPreferences2 = (PreferenceScreen) new PreferenceInflater(context, this).inflate(resId, rootPreferences, true);
        rootPreferences2.onAttachedToHierarchy(this);
        setNoCommit(false);
        return rootPreferences2;
    }

    public PreferenceScreen createPreferenceScreen(Context context) {
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, (AttributeSet) null);
        preferenceScreen.onAttachedToHierarchy(this);
        return preferenceScreen;
    }

    /* access modifiers changed from: package-private */
    public long getNextId() {
        long j;
        synchronized (this) {
            j = this.mNextId;
            this.mNextId = 1 + j;
        }
        return j;
    }

    public String getSharedPreferencesName() {
        return this.mSharedPreferencesName;
    }

    public void setSharedPreferencesName(String sharedPreferencesName) {
        this.mSharedPreferencesName = sharedPreferencesName;
        this.mSharedPreferences = null;
    }

    public int getSharedPreferencesMode() {
        return this.mSharedPreferencesMode;
    }

    public void setSharedPreferencesMode(int sharedPreferencesMode) {
        this.mSharedPreferencesMode = sharedPreferencesMode;
        this.mSharedPreferences = null;
    }

    public void setStorageDefault() {
        this.mStorage = 0;
        this.mSharedPreferences = null;
    }

    public void setStorageDeviceProtected() {
        this.mStorage = 1;
        this.mSharedPreferences = null;
    }

    @SystemApi
    public void setStorageCredentialProtected() {
        this.mStorage = 2;
        this.mSharedPreferences = null;
    }

    public boolean isStorageDefault() {
        return this.mStorage == 0;
    }

    public boolean isStorageDeviceProtected() {
        return this.mStorage == 1;
    }

    @SystemApi
    public boolean isStorageCredentialProtected() {
        return this.mStorage == 2;
    }

    public SharedPreferences getSharedPreferences() {
        Context storageContext;
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (this.mSharedPreferences == null) {
            switch (this.mStorage) {
                case 1:
                    storageContext = this.mContext.createDeviceProtectedStorageContext();
                    break;
                case 2:
                    storageContext = this.mContext.createCredentialProtectedStorageContext();
                    break;
                default:
                    storageContext = this.mContext;
                    break;
            }
            this.mSharedPreferences = storageContext.getSharedPreferences(this.mSharedPreferencesName, this.mSharedPreferencesMode);
        }
        return this.mSharedPreferences;
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode());
    }

    public static String getDefaultSharedPreferencesName(Context context) {
        return context.getPackageName() + "_preferences";
    }

    private static int getDefaultSharedPreferencesMode() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceScreen;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean setPreferences(PreferenceScreen preferenceScreen) {
        if (preferenceScreen == this.mPreferenceScreen) {
            return false;
        }
        this.mPreferenceScreen = preferenceScreen;
        return true;
    }

    public Preference findPreference(CharSequence key) {
        if (this.mPreferenceScreen == null) {
            return null;
        }
        return this.mPreferenceScreen.findPreference(key);
    }

    public static void setDefaultValues(Context context, int resId, boolean readAgain) {
        setDefaultValues(context, getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode(), resId, readAgain);
    }

    public static void setDefaultValues(Context context, String sharedPreferencesName, int sharedPreferencesMode, int resId, boolean readAgain) {
        SharedPreferences defaultValueSp = context.getSharedPreferences(KEY_HAS_SET_DEFAULT_VALUES, 0);
        if (readAgain || !defaultValueSp.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {
            PreferenceManager pm = new PreferenceManager(context);
            pm.setSharedPreferencesName(sharedPreferencesName);
            pm.setSharedPreferencesMode(sharedPreferencesMode);
            pm.inflateFromResource(context, resId, (PreferenceScreen) null);
            SharedPreferences.Editor editor = defaultValueSp.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true);
            try {
                editor.apply();
            } catch (AbstractMethodError e) {
                editor.commit();
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public SharedPreferences.Editor getEditor() {
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (!this.mNoCommit) {
            return getSharedPreferences().edit();
        }
        if (this.mEditor == null) {
            this.mEditor = getSharedPreferences().edit();
        }
        return this.mEditor;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean shouldCommit() {
        return !this.mNoCommit;
    }

    @UnsupportedAppUsage
    private void setNoCommit(boolean noCommit) {
        if (!noCommit && this.mEditor != null) {
            try {
                this.mEditor.apply();
            } catch (AbstractMethodError e) {
                this.mEditor.commit();
            }
        }
        this.mNoCommit = noCommit;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public Activity getActivity() {
        return this.mActivity;
    }

    /* access modifiers changed from: package-private */
    public Context getContext() {
        return this.mContext;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void registerOnActivityResultListener(OnActivityResultListener listener) {
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                this.mActivityResultListeners = new ArrayList();
            }
            if (!this.mActivityResultListeners.contains(listener)) {
                this.mActivityResultListeners.add(listener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void unregisterOnActivityResultListener(OnActivityResultListener listener) {
        synchronized (this) {
            if (this.mActivityResultListeners != null) {
                this.mActivityResultListeners.remove(listener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0020, code lost:
        if (r0.get(r2).onActivityResult(r5, r6, r7) == false) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r1 = r0.size();
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        if (r2 >= r1) goto L_0x0026;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispatchActivityResult(int r5, int r6, android.content.Intent r7) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.List<android.preference.PreferenceManager$OnActivityResultListener> r0 = r4.mActivityResultListeners     // Catch:{ all -> 0x0027 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x0027 }
            return
        L_0x0007:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0027 }
            java.util.List<android.preference.PreferenceManager$OnActivityResultListener> r1 = r4.mActivityResultListeners     // Catch:{ all -> 0x0027 }
            r0.<init>(r1)     // Catch:{ all -> 0x0027 }
            monitor-exit(r4)     // Catch:{ all -> 0x0027 }
            int r1 = r0.size()
            r2 = 0
        L_0x0014:
            if (r2 >= r1) goto L_0x0026
            java.lang.Object r3 = r0.get(r2)
            android.preference.PreferenceManager$OnActivityResultListener r3 = (android.preference.PreferenceManager.OnActivityResultListener) r3
            boolean r3 = r3.onActivityResult(r5, r6, r7)
            if (r3 == 0) goto L_0x0023
            goto L_0x0026
        L_0x0023:
            int r2 = r2 + 1
            goto L_0x0014
        L_0x0026:
            return
        L_0x0027:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0027 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceManager.dispatchActivityResult(int, int, android.content.Intent):void");
    }

    @UnsupportedAppUsage
    public void registerOnActivityStopListener(OnActivityStopListener listener) {
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                this.mActivityStopListeners = new ArrayList();
            }
            if (!this.mActivityStopListeners.contains(listener)) {
                this.mActivityStopListeners.add(listener);
            }
        }
    }

    @UnsupportedAppUsage
    public void unregisterOnActivityStopListener(OnActivityStopListener listener) {
        synchronized (this) {
            if (this.mActivityStopListeners != null) {
                this.mActivityStopListeners.remove(listener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
        r0.get(r2).onActivityStop();
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x000f, code lost:
        r1 = r0.size();
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
        if (r2 >= r1) goto L_0x0022;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void dispatchActivityStop() {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.List<android.preference.PreferenceManager$OnActivityStopListener> r0 = r4.mActivityStopListeners     // Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r4)     // Catch:{ all -> 0x0023 }
            return
        L_0x0007:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0023 }
            java.util.List<android.preference.PreferenceManager$OnActivityStopListener> r1 = r4.mActivityStopListeners     // Catch:{ all -> 0x0023 }
            r0.<init>(r1)     // Catch:{ all -> 0x0023 }
            monitor-exit(r4)     // Catch:{ all -> 0x0023 }
            int r1 = r0.size()
            r2 = 0
        L_0x0014:
            if (r2 >= r1) goto L_0x0022
            java.lang.Object r3 = r0.get(r2)
            android.preference.PreferenceManager$OnActivityStopListener r3 = (android.preference.PreferenceManager.OnActivityStopListener) r3
            r3.onActivityStop()
            int r2 = r2 + 1
            goto L_0x0014
        L_0x0022:
            return
        L_0x0023:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0023 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceManager.dispatchActivityStop():void");
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void registerOnActivityDestroyListener(OnActivityDestroyListener listener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners == null) {
                this.mActivityDestroyListeners = new ArrayList();
            }
            if (!this.mActivityDestroyListeners.contains(listener)) {
                this.mActivityDestroyListeners.add(listener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void unregisterOnActivityDestroyListener(OnActivityDestroyListener listener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners != null) {
                this.mActivityDestroyListeners.remove(listener);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void dispatchActivityDestroy() {
        List<OnActivityDestroyListener> list = null;
        synchronized (this) {
            if (this.mActivityDestroyListeners != null) {
                list = new ArrayList<>(this.mActivityDestroyListeners);
            }
        }
        if (list != null) {
            int N = list.size();
            for (int i = 0; i < N; i++) {
                list.get(i).onActivityDestroy();
            }
        }
        dismissAllScreens();
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int getNextRequestCode() {
        int i;
        synchronized (this) {
            i = this.mNextRequestCode;
            this.mNextRequestCode = i + 1;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public void addPreferencesScreen(DialogInterface screen) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                this.mPreferencesScreens = new ArrayList();
            }
            this.mPreferencesScreens.add(screen);
        }
    }

    /* access modifiers changed from: package-private */
    public void removePreferencesScreen(DialogInterface screen) {
        synchronized (this) {
            if (this.mPreferencesScreens != null) {
                this.mPreferencesScreens.remove(screen);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchNewIntent(Intent intent) {
        dismissAllScreens();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        r0.get(r1).dismiss();
        r1 = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0014, code lost:
        r1 = r0.size() - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        if (r1 < 0) goto L_0x0028;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void dismissAllScreens() {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.List<android.content.DialogInterface> r0 = r3.mPreferencesScreens     // Catch:{ all -> 0x0029 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            return
        L_0x0007:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0029 }
            java.util.List<android.content.DialogInterface> r1 = r3.mPreferencesScreens     // Catch:{ all -> 0x0029 }
            r0.<init>(r1)     // Catch:{ all -> 0x0029 }
            java.util.List<android.content.DialogInterface> r1 = r3.mPreferencesScreens     // Catch:{ all -> 0x0029 }
            r1.clear()     // Catch:{ all -> 0x0029 }
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            int r1 = r0.size()
            int r1 = r1 + -1
        L_0x001a:
            if (r1 < 0) goto L_0x0028
            java.lang.Object r2 = r0.get(r1)
            android.content.DialogInterface r2 = (android.content.DialogInterface) r2
            r2.dismiss()
            int r1 = r1 + -1
            goto L_0x001a
        L_0x0028:
            return
        L_0x0029:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceManager.dismissAllScreens():void");
    }

    /* access modifiers changed from: package-private */
    public void setOnPreferenceTreeClickListener(OnPreferenceTreeClickListener listener) {
        this.mOnPreferenceTreeClickListener = listener;
    }

    /* access modifiers changed from: package-private */
    public OnPreferenceTreeClickListener getOnPreferenceTreeClickListener() {
        return this.mOnPreferenceTreeClickListener;
    }
}
