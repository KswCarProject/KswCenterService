package android.content.pm;

import android.Manifest;
import android.accounts.GrantCredentialsPermissionActivity;
import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AtomicFile;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastXmlSerializer;
import com.google.android.collect.Maps;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public abstract class RegisteredServicesCache<V> {
    private static final boolean DEBUG = false;
    protected static final String REGISTERED_SERVICES_DIR = "registered_services";
    private static final String TAG = "PackageManager";
    private final String mAttributesName;
    public final Context mContext;
    private final BroadcastReceiver mExternalReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            RegisteredServicesCache.this.handlePackageEvent(intent, 0);
        }
    };
    private Handler mHandler;
    private final String mInterfaceName;
    private RegisteredServicesCacheListener<V> mListener;
    private final String mMetaDataName;
    private final BroadcastReceiver mPackageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int uid = intent.getIntExtra(Intent.EXTRA_UID, -1);
            if (uid != -1) {
                RegisteredServicesCache.this.handlePackageEvent(intent, UserHandle.getUserId(uid));
            }
        }
    };
    private final XmlSerializerAndParser<V> mSerializerAndParser;
    protected final Object mServicesLock = new Object();
    private final BroadcastReceiver mUserRemovedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            RegisteredServicesCache.this.onUserRemoved(intent.getIntExtra(Intent.EXTRA_USER_HANDLE, -1));
        }
    };
    @GuardedBy({"mServicesLock"})
    private final SparseArray<UserServices<V>> mUserServices = new SparseArray<>(2);

    public abstract V parseServiceAttributes(Resources resources, String str, AttributeSet attributeSet);

    private static class UserServices<V> {
        @GuardedBy({"mServicesLock"})
        boolean mBindInstantServiceAllowed;
        @GuardedBy({"mServicesLock"})
        boolean mPersistentServicesFileDidNotExist;
        @GuardedBy({"mServicesLock"})
        final Map<V, Integer> persistentServices;
        @GuardedBy({"mServicesLock"})
        Map<V, ServiceInfo<V>> services;

        private UserServices() {
            this.persistentServices = Maps.newHashMap();
            this.services = null;
            this.mPersistentServicesFileDidNotExist = true;
            this.mBindInstantServiceAllowed = false;
        }
    }

    @GuardedBy({"mServicesLock"})
    private UserServices<V> findOrCreateUserLocked(int userId) {
        return findOrCreateUserLocked(userId, true);
    }

    @GuardedBy({"mServicesLock"})
    private UserServices<V> findOrCreateUserLocked(int userId, boolean loadFromFileIfNew) {
        UserInfo user;
        UserServices<V> services = this.mUserServices.get(userId);
        if (services == null) {
            InputStream is = null;
            services = new UserServices<>();
            this.mUserServices.put(userId, services);
            if (!(!loadFromFileIfNew || this.mSerializerAndParser == null || (user = getUser(userId)) == null)) {
                AtomicFile file = createFileForUser(user.id);
                if (file.getBaseFile().exists()) {
                    try {
                        is = file.openRead();
                        readPersistentServicesLocked(is);
                    } catch (Exception e) {
                        Log.w(TAG, "Error reading persistent services for user " + user.id, e);
                    } catch (Throwable th) {
                        IoUtils.closeQuietly(is);
                        throw th;
                    }
                    IoUtils.closeQuietly(is);
                }
            }
        }
        return services;
    }

    @UnsupportedAppUsage
    public RegisteredServicesCache(Context context, String interfaceName, String metaDataName, String attributeName, XmlSerializerAndParser<V> serializerAndParser) {
        this.mContext = context;
        this.mInterfaceName = interfaceName;
        this.mMetaDataName = metaDataName;
        this.mAttributesName = attributeName;
        this.mSerializerAndParser = serializerAndParser;
        migrateIfNecessaryLocked();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(this.mPackageReceiver, UserHandle.ALL, intentFilter, (String) null, (Handler) null);
        IntentFilter sdFilter = new IntentFilter();
        sdFilter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        sdFilter.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        this.mContext.registerReceiver(this.mExternalReceiver, sdFilter);
        IntentFilter userFilter = new IntentFilter();
        sdFilter.addAction(Intent.ACTION_USER_REMOVED);
        this.mContext.registerReceiver(this.mUserRemovedReceiver, userFilter);
    }

    /* access modifiers changed from: private */
    public void handlePackageEvent(Intent intent, int userId) {
        String action = intent.getAction();
        boolean isRemoval = Intent.ACTION_PACKAGE_REMOVED.equals(action) || "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action);
        boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
        if (!isRemoval || !replacing) {
            int[] uids = null;
            if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action) || "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                uids = intent.getIntArrayExtra("android.intent.extra.changed_uid_list");
            } else {
                int uid = intent.getIntExtra(Intent.EXTRA_UID, -1);
                if (uid > 0) {
                    uids = new int[]{uid};
                }
            }
            generateServicesMap(uids, userId);
        }
    }

    public void invalidateCache(int userId) {
        synchronized (this.mServicesLock) {
            findOrCreateUserLocked(userId).services = null;
            onServicesChangedLocked(userId);
        }
    }

    public void dump(FileDescriptor fd, PrintWriter fout, String[] args, int userId) {
        synchronized (this.mServicesLock) {
            UserServices<V> user = findOrCreateUserLocked(userId);
            if (user.services != null) {
                fout.println("RegisteredServicesCache: " + user.services.size() + " services");
                for (ServiceInfo<V> info : user.services.values()) {
                    fout.println("  " + info);
                }
            } else {
                fout.println("RegisteredServicesCache: services not loaded");
            }
        }
    }

    public RegisteredServicesCacheListener<V> getListener() {
        RegisteredServicesCacheListener<V> registeredServicesCacheListener;
        synchronized (this) {
            registeredServicesCacheListener = this.mListener;
        }
        return registeredServicesCacheListener;
    }

    public void setListener(RegisteredServicesCacheListener<V> listener, Handler handler) {
        if (handler == null) {
            handler = new Handler(this.mContext.getMainLooper());
        }
        synchronized (this) {
            this.mHandler = handler;
            this.mListener = listener;
        }
    }

    private void notifyListener(V type, int userId, boolean removed) {
        RegisteredServicesCacheListener<V> listener;
        Handler handler;
        synchronized (this) {
            listener = this.mListener;
            handler = this.mHandler;
        }
        if (listener != null) {
            handler.post(new Runnable(type, userId, removed) {
                private final /* synthetic */ Object f$1;
                private final /* synthetic */ int f$2;
                private final /* synthetic */ boolean f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    RegisteredServicesCache.lambda$notifyListener$0(RegisteredServicesCacheListener.this, this.f$1, this.f$2, this.f$3);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyListener$0(RegisteredServicesCacheListener listener2, Object type, int userId, boolean removed) {
        try {
            listener2.onServiceChanged(type, userId, removed);
        } catch (Throwable th) {
            Slog.wtf(TAG, "Exception from onServiceChanged", th);
        }
    }

    public static class ServiceInfo<V> {
        public final ComponentInfo componentInfo;
        @UnsupportedAppUsage
        public final ComponentName componentName;
        @UnsupportedAppUsage
        public final V type;
        @UnsupportedAppUsage
        public final int uid;

        public ServiceInfo(V type2, ComponentInfo componentInfo2, ComponentName componentName2) {
            this.type = type2;
            this.componentInfo = componentInfo2;
            this.componentName = componentName2;
            this.uid = componentInfo2 != null ? componentInfo2.applicationInfo.uid : -1;
        }

        public String toString() {
            return "ServiceInfo: " + this.type + ", " + this.componentName + ", uid " + this.uid;
        }
    }

    public ServiceInfo<V> getServiceInfo(V type, int userId) {
        ServiceInfo<V> serviceInfo;
        synchronized (this.mServicesLock) {
            UserServices<V> user = findOrCreateUserLocked(userId);
            if (user.services == null) {
                generateServicesMap((int[]) null, userId);
            }
            serviceInfo = user.services.get(type);
        }
        return serviceInfo;
    }

    public Collection<ServiceInfo<V>> getAllServices(int userId) {
        Collection<ServiceInfo<V>> unmodifiableCollection;
        synchronized (this.mServicesLock) {
            UserServices<V> user = findOrCreateUserLocked(userId);
            if (user.services == null) {
                generateServicesMap((int[]) null, userId);
            }
            unmodifiableCollection = Collections.unmodifiableCollection(new ArrayList(user.services.values()));
        }
        return unmodifiableCollection;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
        if (r2.hasNext() == false) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        r3 = r2.next();
        r4 = (long) r3.componentInfo.applicationInfo.versionCode;
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        r7 = r10.mContext.getPackageManager().getApplicationInfoAsUser(r3.componentInfo.packageName, 0, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005c, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0062, code lost:
        if (r0.size() <= 0) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0064, code lost:
        generateServicesMap(r0.toArray(), r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001a, code lost:
        r0 = null;
        r2 = r1.iterator();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateServices(int r11) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mServicesLock
            monitor-enter(r0)
            android.content.pm.RegisteredServicesCache$UserServices r1 = r10.findOrCreateUserLocked(r11)     // Catch:{ all -> 0x006c }
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r2 = r1.services     // Catch:{ all -> 0x006c }
            if (r2 != 0) goto L_0x000d
            monitor-exit(r0)     // Catch:{ all -> 0x006c }
            return
        L_0x000d:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x006c }
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r3 = r1.services     // Catch:{ all -> 0x006c }
            java.util.Collection r3 = r3.values()     // Catch:{ all -> 0x006c }
            r2.<init>(r3)     // Catch:{ all -> 0x006c }
            r1 = r2
            monitor-exit(r0)     // Catch:{ all -> 0x006c }
            r0 = 0
            java.util.Iterator r2 = r1.iterator()
        L_0x001f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x005c
            java.lang.Object r3 = r2.next()
            android.content.pm.RegisteredServicesCache$ServiceInfo r3 = (android.content.pm.RegisteredServicesCache.ServiceInfo) r3
            android.content.pm.ComponentInfo r4 = r3.componentInfo
            android.content.pm.ApplicationInfo r4 = r4.applicationInfo
            int r4 = r4.versionCode
            long r4 = (long) r4
            android.content.pm.ComponentInfo r6 = r3.componentInfo
            java.lang.String r6 = r6.packageName
            r7 = 0
            android.content.Context r8 = r10.mContext     // Catch:{ NameNotFoundException -> 0x0044 }
            android.content.pm.PackageManager r8 = r8.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0044 }
            r9 = 0
            android.content.pm.ApplicationInfo r8 = r8.getApplicationInfoAsUser((java.lang.String) r6, (int) r9, (int) r11)     // Catch:{ NameNotFoundException -> 0x0044 }
            r7 = r8
            goto L_0x0045
        L_0x0044:
            r8 = move-exception
        L_0x0045:
            if (r7 == 0) goto L_0x004e
            int r8 = r7.versionCode
            long r8 = (long) r8
            int r8 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r8 == 0) goto L_0x005b
        L_0x004e:
            if (r0 != 0) goto L_0x0056
            android.util.IntArray r8 = new android.util.IntArray
            r8.<init>()
            r0 = r8
        L_0x0056:
            int r8 = r3.uid
            r0.add(r8)
        L_0x005b:
            goto L_0x001f
        L_0x005c:
            if (r0 == 0) goto L_0x006b
            int r2 = r0.size()
            if (r2 <= 0) goto L_0x006b
            int[] r2 = r0.toArray()
            r10.generateServicesMap(r2, r11)
        L_0x006b:
            return
        L_0x006c:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x006c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.RegisteredServicesCache.updateServices(int):void");
    }

    public boolean getBindInstantServiceAllowed(int userId) {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.MANAGE_BIND_INSTANT_SERVICE, "getBindInstantServiceAllowed");
        synchronized (this.mServicesLock) {
            z = findOrCreateUserLocked(userId).mBindInstantServiceAllowed;
        }
        return z;
    }

    public void setBindInstantServiceAllowed(int userId, boolean allowed) {
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.MANAGE_BIND_INSTANT_SERVICE, "setBindInstantServiceAllowed");
        synchronized (this.mServicesLock) {
            findOrCreateUserLocked(userId).mBindInstantServiceAllowed = allowed;
        }
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public boolean inSystemImage(int callerUid) {
        String[] packages = this.mContext.getPackageManager().getPackagesForUid(callerUid);
        if (packages != null) {
            int length = packages.length;
            int i = 0;
            while (i < length) {
                try {
                    if ((this.mContext.getPackageManager().getPackageInfo(packages[i], 0).applicationInfo.flags & 1) != 0) {
                        return true;
                    }
                    i++;
                } catch (PackageManager.NameNotFoundException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public List<ResolveInfo> queryIntentServices(int userId) {
        PackageManager pm = this.mContext.getPackageManager();
        int flags = 786560;
        synchronized (this.mServicesLock) {
            if (findOrCreateUserLocked(userId).mBindInstantServiceAllowed) {
                flags = 786560 | 8388608;
            }
        }
        return pm.queryIntentServicesAsUser(new Intent(this.mInterfaceName), flags, userId);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:58:0x015c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void generateServicesMap(int[] r17, int r18) {
        /*
            r16 = this;
            r1 = r16
            r2 = r18
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r3 = r0
            java.util.List r4 = r1.queryIntentServices(r2)
            java.util.Iterator r5 = r4.iterator()
        L_0x0012:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x0060
            java.lang.Object r0 = r5.next()
            android.content.pm.ResolveInfo r0 = (android.content.pm.ResolveInfo) r0
            r6 = r0
            android.content.pm.RegisteredServicesCache$ServiceInfo r0 = r1.parseServiceInfo(r6)     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            if (r0 != 0) goto L_0x0040
            java.lang.String r7 = "PackageManager"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            r8.<init>()     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            java.lang.String r9 = "Unable to load service info "
            r8.append(r9)     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            java.lang.String r9 = r6.toString()     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            r8.append(r9)     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            android.util.Log.w((java.lang.String) r7, (java.lang.String) r8)     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            goto L_0x0012
        L_0x0040:
            r3.add(r0)     // Catch:{ IOException | XmlPullParserException -> 0x0044 }
            goto L_0x005f
        L_0x0044:
            r0 = move-exception
            java.lang.String r7 = "PackageManager"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Unable to load service info "
            r8.append(r9)
            java.lang.String r9 = r6.toString()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.util.Log.w(r7, r8, r0)
        L_0x005f:
            goto L_0x0012
        L_0x0060:
            java.lang.Object r5 = r1.mServicesLock
            monitor-enter(r5)
            android.content.pm.RegisteredServicesCache$UserServices r0 = r1.findOrCreateUserLocked(r2)     // Catch:{ all -> 0x015d }
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r6 = r0.services     // Catch:{ all -> 0x015d }
            r8 = 0
            if (r6 != 0) goto L_0x006e
            r6 = 1
            goto L_0x006f
        L_0x006e:
            r6 = r8
        L_0x006f:
            if (r6 == 0) goto L_0x0077
            java.util.HashMap r9 = com.google.android.collect.Maps.newHashMap()     // Catch:{ all -> 0x015d }
            r0.services = r9     // Catch:{ all -> 0x015d }
        L_0x0077:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x015d }
            r9.<init>()     // Catch:{ all -> 0x015d }
            r10 = 0
            java.util.Iterator r11 = r3.iterator()     // Catch:{ all -> 0x015d }
        L_0x0081:
            boolean r12 = r11.hasNext()     // Catch:{ all -> 0x015d }
            if (r12 == 0) goto L_0x00fa
            java.lang.Object r12 = r11.next()     // Catch:{ all -> 0x015d }
            android.content.pm.RegisteredServicesCache$ServiceInfo r12 = (android.content.pm.RegisteredServicesCache.ServiceInfo) r12     // Catch:{ all -> 0x015d }
            java.util.Map<V, java.lang.Integer> r13 = r0.persistentServices     // Catch:{ all -> 0x015d }
            V r14 = r12.type     // Catch:{ all -> 0x015d }
            java.lang.Object r13 = r13.get(r14)     // Catch:{ all -> 0x015d }
            java.lang.Integer r13 = (java.lang.Integer) r13     // Catch:{ all -> 0x015d }
            if (r13 != 0) goto L_0x00ba
            r10 = 1
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r14 = r0.services     // Catch:{ all -> 0x015d }
            V r15 = r12.type     // Catch:{ all -> 0x015d }
            r14.put(r15, r12)     // Catch:{ all -> 0x015d }
            java.util.Map<V, java.lang.Integer> r14 = r0.persistentServices     // Catch:{ all -> 0x015d }
            V r15 = r12.type     // Catch:{ all -> 0x015d }
            int r7 = r12.uid     // Catch:{ all -> 0x015d }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x015d }
            r14.put(r15, r7)     // Catch:{ all -> 0x015d }
            boolean r7 = r0.mPersistentServicesFileDidNotExist     // Catch:{ all -> 0x015d }
            if (r7 == 0) goto L_0x00b4
            if (r6 != 0) goto L_0x00f9
        L_0x00b4:
            V r7 = r12.type     // Catch:{ all -> 0x015d }
            r1.notifyListener(r7, r2, r8)     // Catch:{ all -> 0x015d }
            goto L_0x00f9
        L_0x00ba:
            int r7 = r13.intValue()     // Catch:{ all -> 0x015d }
            int r14 = r12.uid     // Catch:{ all -> 0x015d }
            if (r7 != r14) goto L_0x00ca
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r7 = r0.services     // Catch:{ all -> 0x015d }
            V r14 = r12.type     // Catch:{ all -> 0x015d }
            r7.put(r14, r12)     // Catch:{ all -> 0x015d }
            goto L_0x00f9
        L_0x00ca:
            int r7 = r12.uid     // Catch:{ all -> 0x015d }
            boolean r7 = r1.inSystemImage(r7)     // Catch:{ all -> 0x015d }
            if (r7 != 0) goto L_0x00de
            V r7 = r12.type     // Catch:{ all -> 0x015d }
            int r14 = r13.intValue()     // Catch:{ all -> 0x015d }
            boolean r7 = r1.containsTypeAndUid(r3, r7, r14)     // Catch:{ all -> 0x015d }
            if (r7 != 0) goto L_0x00f9
        L_0x00de:
            r7 = 1
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r10 = r0.services     // Catch:{ all -> 0x015d }
            V r14 = r12.type     // Catch:{ all -> 0x015d }
            r10.put(r14, r12)     // Catch:{ all -> 0x015d }
            java.util.Map<V, java.lang.Integer> r10 = r0.persistentServices     // Catch:{ all -> 0x015d }
            V r14 = r12.type     // Catch:{ all -> 0x015d }
            int r15 = r12.uid     // Catch:{ all -> 0x015d }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch:{ all -> 0x015d }
            r10.put(r14, r15)     // Catch:{ all -> 0x015d }
            V r10 = r12.type     // Catch:{ all -> 0x015d }
            r1.notifyListener(r10, r2, r8)     // Catch:{ all -> 0x015d }
            r10 = r7
        L_0x00f9:
            goto L_0x0081
        L_0x00fa:
            java.util.ArrayList r7 = com.google.android.collect.Lists.newArrayList()     // Catch:{ all -> 0x015d }
            java.util.Map<V, java.lang.Integer> r8 = r0.persistentServices     // Catch:{ all -> 0x015d }
            java.util.Set r8 = r8.keySet()     // Catch:{ all -> 0x015d }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ all -> 0x015d }
        L_0x0108:
            boolean r11 = r8.hasNext()     // Catch:{ all -> 0x015d }
            if (r11 == 0) goto L_0x0133
            java.lang.Object r11 = r8.next()     // Catch:{ all -> 0x015d }
            boolean r12 = r1.containsType(r3, r11)     // Catch:{ all -> 0x015d }
            if (r12 != 0) goto L_0x0130
            java.util.Map<V, java.lang.Integer> r12 = r0.persistentServices     // Catch:{ all -> 0x015d }
            java.lang.Object r12 = r12.get(r11)     // Catch:{ all -> 0x015d }
            java.lang.Integer r12 = (java.lang.Integer) r12     // Catch:{ all -> 0x015d }
            int r12 = r12.intValue()     // Catch:{ all -> 0x015d }
            r13 = r17
            boolean r12 = r1.containsUid(r13, r12)     // Catch:{ all -> 0x0162 }
            if (r12 == 0) goto L_0x0132
            r7.add(r11)     // Catch:{ all -> 0x0162 }
            goto L_0x0132
        L_0x0130:
            r13 = r17
        L_0x0132:
            goto L_0x0108
        L_0x0133:
            r13 = r17
            java.util.Iterator r8 = r7.iterator()     // Catch:{ all -> 0x0162 }
        L_0x0139:
            boolean r11 = r8.hasNext()     // Catch:{ all -> 0x0162 }
            if (r11 == 0) goto L_0x0153
            java.lang.Object r11 = r8.next()     // Catch:{ all -> 0x0162 }
            r10 = 1
            java.util.Map<V, java.lang.Integer> r12 = r0.persistentServices     // Catch:{ all -> 0x0162 }
            r12.remove(r11)     // Catch:{ all -> 0x0162 }
            java.util.Map<V, android.content.pm.RegisteredServicesCache$ServiceInfo<V>> r12 = r0.services     // Catch:{ all -> 0x0162 }
            r12.remove(r11)     // Catch:{ all -> 0x0162 }
            r12 = 1
            r1.notifyListener(r11, r2, r12)     // Catch:{ all -> 0x0162 }
            goto L_0x0139
        L_0x0153:
            if (r10 == 0) goto L_0x015b
            r1.onServicesChangedLocked(r2)     // Catch:{ all -> 0x0162 }
            r1.writePersistentServicesLocked(r0, r2)     // Catch:{ all -> 0x0162 }
        L_0x015b:
            monitor-exit(r5)     // Catch:{ all -> 0x0162 }
            return
        L_0x015d:
            r0 = move-exception
            r13 = r17
        L_0x0160:
            monitor-exit(r5)     // Catch:{ all -> 0x0162 }
            throw r0
        L_0x0162:
            r0 = move-exception
            goto L_0x0160
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.RegisteredServicesCache.generateServicesMap(int[], int):void");
    }

    /* access modifiers changed from: protected */
    public void onServicesChangedLocked(int userId) {
    }

    private boolean containsUid(int[] changedUids, int uid) {
        return changedUids == null || ArrayUtils.contains(changedUids, uid);
    }

    private boolean containsType(ArrayList<ServiceInfo<V>> serviceInfos, V type) {
        int N = serviceInfos.size();
        for (int i = 0; i < N; i++) {
            if (serviceInfos.get(i).type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsTypeAndUid(ArrayList<ServiceInfo<V>> serviceInfos, V type, int uid) {
        int N = serviceInfos.size();
        for (int i = 0; i < N; i++) {
            ServiceInfo<V> serviceInfo = serviceInfos.get(i);
            if (serviceInfo.type.equals(type) && serviceInfo.uid == uid) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public ServiceInfo<V> parseServiceInfo(ResolveInfo service) throws XmlPullParserException, IOException {
        ServiceInfo si = service.serviceInfo;
        ComponentName componentName = new ComponentName(si.packageName, si.name);
        PackageManager pm = this.mContext.getPackageManager();
        XmlResourceParser parser = null;
        try {
            parser = si.loadXmlMetaData(pm, this.mMetaDataName);
            if (parser != null) {
                AttributeSet attrs = Xml.asAttributeSet(parser);
                while (true) {
                    int next = parser.next();
                    int type = next;
                    if (next == 1 || type == 2) {
                    }
                }
                if (this.mAttributesName.equals(parser.getName())) {
                    V v = parseServiceAttributes(pm.getResourcesForApplication(si.applicationInfo), si.packageName, attrs);
                    if (v == null) {
                        if (parser != null) {
                            parser.close();
                        }
                        return null;
                    }
                    ServiceInfo<V> serviceInfo = new ServiceInfo<>(v, service.serviceInfo, componentName);
                    if (parser != null) {
                        parser.close();
                    }
                    return serviceInfo;
                }
                throw new XmlPullParserException("Meta-data does not start with " + this.mAttributesName + " tag");
            }
            throw new XmlPullParserException("No " + this.mMetaDataName + " meta-data");
        } catch (PackageManager.NameNotFoundException e) {
            throw new XmlPullParserException("Unable to load resources for pacakge " + si.packageName);
        } catch (Throwable th) {
            if (parser != null) {
                parser.close();
            }
            throw th;
        }
    }

    private void readPersistentServicesLocked(InputStream is) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, StandardCharsets.UTF_8.name());
        int eventType = parser.getEventType();
        while (eventType != 2 && eventType != 1) {
            eventType = parser.next();
        }
        if ("services".equals(parser.getName())) {
            int eventType2 = parser.next();
            do {
                if (eventType2 == 2 && parser.getDepth() == 2 && "service".equals(parser.getName())) {
                    V service = this.mSerializerAndParser.createFromXml(parser);
                    if (service != null) {
                        int uid = Integer.parseInt(parser.getAttributeValue((String) null, GrantCredentialsPermissionActivity.EXTRAS_REQUESTING_UID));
                        findOrCreateUserLocked(UserHandle.getUserId(uid), false).persistentServices.put(service, Integer.valueOf(uid));
                    } else {
                        return;
                    }
                }
                eventType2 = parser.next();
            } while (eventType2 != 1);
        }
    }

    private void migrateIfNecessaryLocked() {
        if (this.mSerializerAndParser != null) {
            File syncDir = new File(new File(getDataDirectory(), "system"), REGISTERED_SERVICES_DIR);
            AtomicFile oldFile = new AtomicFile(new File(syncDir, this.mInterfaceName + ".xml"));
            if (oldFile.getBaseFile().exists()) {
                File marker = new File(syncDir, this.mInterfaceName + ".xml.migrated");
                if (!marker.exists()) {
                    InputStream is = null;
                    try {
                        is = oldFile.openRead();
                        this.mUserServices.clear();
                        readPersistentServicesLocked(is);
                    } catch (Exception e) {
                        Log.w(TAG, "Error reading persistent services, starting from scratch", e);
                    } catch (Throwable th) {
                        IoUtils.closeQuietly(is);
                        throw th;
                    }
                    IoUtils.closeQuietly(is);
                    try {
                        for (UserInfo user : getUsers()) {
                            UserServices<V> userServices = this.mUserServices.get(user.id);
                            if (userServices != null) {
                                writePersistentServicesLocked(userServices, user.id);
                            }
                        }
                        marker.createNewFile();
                    } catch (Exception e2) {
                        Log.w(TAG, "Migration failed", e2);
                    }
                    this.mUserServices.clear();
                }
            }
        }
    }

    private void writePersistentServicesLocked(UserServices<V> user, int userId) {
        if (this.mSerializerAndParser != null) {
            AtomicFile atomicFile = createFileForUser(userId);
            try {
                FileOutputStream fos = atomicFile.startWrite();
                XmlSerializer out = new FastXmlSerializer();
                out.setOutput(fos, StandardCharsets.UTF_8.name());
                out.startDocument((String) null, true);
                out.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                out.startTag((String) null, "services");
                for (Map.Entry<V, Integer> service : user.persistentServices.entrySet()) {
                    out.startTag((String) null, "service");
                    out.attribute((String) null, GrantCredentialsPermissionActivity.EXTRAS_REQUESTING_UID, Integer.toString(service.getValue().intValue()));
                    this.mSerializerAndParser.writeAsXml(service.getKey(), out);
                    out.endTag((String) null, "service");
                }
                out.endTag((String) null, "services");
                out.endDocument();
                atomicFile.finishWrite(fos);
            } catch (IOException e1) {
                Log.w(TAG, "Error writing accounts", e1);
                if (0 != 0) {
                    atomicFile.failWrite((FileOutputStream) null);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public void onUserRemoved(int userId) {
        synchronized (this.mServicesLock) {
            this.mUserServices.remove(userId);
        }
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public List<UserInfo> getUsers() {
        return UserManager.get(this.mContext).getUsers(true);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public UserInfo getUser(int userId) {
        return UserManager.get(this.mContext).getUserInfo(userId);
    }

    private AtomicFile createFileForUser(int userId) {
        File userDir = getUserSystemDirectory(userId);
        return new AtomicFile(new File(userDir, "registered_services/" + this.mInterfaceName + ".xml"));
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public File getUserSystemDirectory(int userId) {
        return Environment.getUserSystemDirectory(userId);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public File getDataDirectory() {
        return Environment.getDataDirectory();
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public Map<V, Integer> getPersistentServices(int userId) {
        return findOrCreateUserLocked(userId).persistentServices;
    }
}
