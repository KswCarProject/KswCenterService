package android.app.slice;

import android.app.slice.ISliceManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SliceManager {
    public static final String ACTION_REQUEST_SLICE_PERMISSION = "com.android.intent.action.REQUEST_SLICE_PERMISSION";
    public static final String CATEGORY_SLICE = "android.app.slice.category.SLICE";
    public static final String SLICE_METADATA_KEY = "android.metadata.SLICE_URI";
    private static final String TAG = "SliceManager";
    private final Context mContext;
    private final ISliceManager mService;
    private final IBinder mToken = new Binder();

    public SliceManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mService = ISliceManager.Stub.asInterface(ServiceManager.getServiceOrThrow("slice"));
    }

    public void pinSlice(Uri uri, Set<SliceSpec> specs) {
        try {
            this.mService.pinSlice(this.mContext.getPackageName(), uri, (SliceSpec[]) specs.toArray(new SliceSpec[specs.size()]), this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void pinSlice(Uri uri, List<SliceSpec> specs) {
        pinSlice(uri, (Set<SliceSpec>) new ArraySet(specs));
    }

    public void unpinSlice(Uri uri) {
        try {
            this.mService.unpinSlice(this.mContext.getPackageName(), uri, this.mToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasSliceAccess() {
        try {
            return this.mService.hasSliceAccess(this.mContext.getPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Set<SliceSpec> getPinnedSpecs(Uri uri) {
        try {
            return new ArraySet(Arrays.asList(this.mService.getPinnedSpecs(uri, this.mContext.getPackageName())));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Uri> getPinnedSlices() {
        try {
            return Arrays.asList(this.mService.getPinnedSlices(this.mContext.getPackageName()));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Collection<Uri> getSliceDescendants(Uri uri) {
        ContentProviderClient provider;
        try {
            provider = this.mContext.getContentResolver().acquireUnstableContentProviderClient(uri);
            Bundle extras = new Bundle();
            extras.putParcelable("slice_uri", uri);
            ArrayList parcelableArrayList = provider.call(SliceProvider.METHOD_GET_DESCENDANTS, (String) null, extras).getParcelableArrayList(SliceProvider.EXTRA_SLICE_DESCENDANTS);
            if (provider != null) {
                $closeResource((Throwable) null, provider);
            }
            return parcelableArrayList;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to get slice descendants", e);
            return Collections.emptyList();
        } catch (Throwable th) {
            if (provider != null) {
                $closeResource(r2, provider);
            }
            throw th;
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 != null) {
            try {
                x1.close();
            } catch (Throwable th) {
                x0.addSuppressed(th);
            }
        } else {
            x1.close();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002b, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        r4 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0068, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0069, code lost:
        r7 = r4;
        r4 = r3;
        r3 = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.slice.Slice bindSlice(android.net.Uri r9, java.util.Set<android.app.slice.SliceSpec> r10) {
        /*
            r8 = this;
            java.lang.String r0 = "uri"
            com.android.internal.util.Preconditions.checkNotNull(r9, r0)
            android.content.Context r0 = r8.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            r1 = 0
            android.content.ContentProviderClient r2 = r0.acquireUnstableContentProviderClient((android.net.Uri) r9)     // Catch:{ RemoteException -> 0x0072 }
            r3 = 1
            if (r2 != 0) goto L_0x0030
            java.lang.String r4 = "SliceManager"
            java.lang.String r5 = "Unknown URI: %s"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            r6 = 0
            r3[r6] = r9     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            java.lang.String r3 = java.lang.String.format(r5, r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            android.util.Log.w((java.lang.String) r4, (java.lang.String) r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            if (r2 == 0) goto L_0x002a
            $closeResource(r1, r2)     // Catch:{ RemoteException -> 0x0072 }
        L_0x002a:
            return r1
        L_0x002b:
            r3 = move-exception
            r4 = r1
            goto L_0x006c
        L_0x002e:
            r3 = move-exception
            goto L_0x0067
        L_0x0030:
            android.os.Bundle r4 = new android.os.Bundle     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            r4.<init>()     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            java.lang.String r5 = "slice_uri"
            r4.putParcelable(r5, r9)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            java.lang.String r5 = "supported_specs"
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            r6.<init>(r10)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            r4.putParcelableArrayList(r5, r6)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            java.lang.String r5 = "bind_slice"
            android.os.Bundle r5 = r2.call(r5, r1, r4)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            android.os.Bundle.setDefusable(r5, r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            if (r5 != 0) goto L_0x0058
            if (r2 == 0) goto L_0x0057
            $closeResource(r1, r2)     // Catch:{ RemoteException -> 0x0072 }
        L_0x0057:
            return r1
        L_0x0058:
            java.lang.String r3 = "slice"
            android.os.Parcelable r3 = r5.getParcelable(r3)     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            android.app.slice.Slice r3 = (android.app.slice.Slice) r3     // Catch:{ Throwable -> 0x002e, all -> 0x002b }
            if (r2 == 0) goto L_0x0066
            $closeResource(r1, r2)     // Catch:{ RemoteException -> 0x0072 }
        L_0x0066:
            return r3
        L_0x0067:
            throw r3     // Catch:{ all -> 0x0068 }
        L_0x0068:
            r4 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
        L_0x006c:
            if (r2 == 0) goto L_0x0071
            $closeResource(r4, r2)     // Catch:{ RemoteException -> 0x0072 }
        L_0x0071:
            throw r3     // Catch:{ RemoteException -> 0x0072 }
        L_0x0072:
            r2 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceManager.bindSlice(android.net.Uri, java.util.Set):android.app.slice.Slice");
    }

    @Deprecated
    public Slice bindSlice(Uri uri, List<SliceSpec> supportedSpecs) {
        return bindSlice(uri, (Set<SliceSpec>) new ArraySet(supportedSpecs));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0046, code lost:
        r6 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0047, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0075, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0076, code lost:
        r10 = r7;
        r7 = r6;
        r6 = r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.net.Uri mapIntentToUri(android.content.Intent r12) {
        /*
            r11 = this;
            android.content.Context r0 = r11.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            android.net.Uri r1 = r11.resolveStatic(r12, r0)
            if (r1 == 0) goto L_0x000d
            return r1
        L_0x000d:
            java.lang.String r2 = r11.getAuthority(r12)
            r3 = 0
            if (r2 != 0) goto L_0x0015
            return r3
        L_0x0015:
            android.net.Uri$Builder r4 = new android.net.Uri$Builder
            r4.<init>()
            java.lang.String r5 = "content"
            android.net.Uri$Builder r4 = r4.scheme(r5)
            android.net.Uri$Builder r4 = r4.authority((java.lang.String) r2)
            android.net.Uri r4 = r4.build()
            android.content.ContentProviderClient r5 = r0.acquireUnstableContentProviderClient((android.net.Uri) r4)     // Catch:{ RemoteException -> 0x007f }
            if (r5 != 0) goto L_0x004b
            java.lang.String r6 = "SliceManager"
            java.lang.String r7 = "Unknown URI: %s"
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r9 = 0
            r8[r9] = r4     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            android.util.Log.w((java.lang.String) r6, (java.lang.String) r7)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r5 == 0) goto L_0x0045
            $closeResource(r3, r5)     // Catch:{ RemoteException -> 0x007f }
        L_0x0045:
            return r3
        L_0x0046:
            r6 = move-exception
            r7 = r3
            goto L_0x0079
        L_0x0049:
            r6 = move-exception
            goto L_0x0074
        L_0x004b:
            android.os.Bundle r6 = new android.os.Bundle     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r6.<init>()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            java.lang.String r7 = "slice_intent"
            r6.putParcelable(r7, r12)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            java.lang.String r7 = "map_only"
            android.os.Bundle r7 = r5.call(r7, r3, r6)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r7 != 0) goto L_0x0065
            if (r5 == 0) goto L_0x0064
            $closeResource(r3, r5)     // Catch:{ RemoteException -> 0x007f }
        L_0x0064:
            return r3
        L_0x0065:
            java.lang.String r8 = "slice"
            android.os.Parcelable r8 = r7.getParcelable(r8)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            android.net.Uri r8 = (android.net.Uri) r8     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r5 == 0) goto L_0x0073
            $closeResource(r3, r5)     // Catch:{ RemoteException -> 0x007f }
        L_0x0073:
            return r8
        L_0x0074:
            throw r6     // Catch:{ all -> 0x0075 }
        L_0x0075:
            r7 = move-exception
            r10 = r7
            r7 = r6
            r6 = r10
        L_0x0079:
            if (r5 == 0) goto L_0x007e
            $closeResource(r7, r5)     // Catch:{ RemoteException -> 0x007f }
        L_0x007e:
            throw r6     // Catch:{ RemoteException -> 0x007f }
        L_0x007f:
            r5 = move-exception
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceManager.mapIntentToUri(android.content.Intent):android.net.Uri");
    }

    private String getAuthority(Intent intent) {
        Intent queryIntent = new Intent(intent);
        if (!queryIntent.hasCategory(CATEGORY_SLICE)) {
            queryIntent.addCategory(CATEGORY_SLICE);
        }
        List<ResolveInfo> providers = this.mContext.getPackageManager().queryIntentContentProviders(queryIntent, 0);
        if (providers == null || providers.isEmpty()) {
            return null;
        }
        return providers.get(0).providerInfo.authority;
    }

    private Uri resolveStatic(Intent intent, ContentResolver resolver) {
        Preconditions.checkNotNull(intent, "intent");
        Preconditions.checkArgument((intent.getComponent() == null && intent.getPackage() == null && intent.getData() == null) ? false : true, "Slice intent must be explicit %s", intent);
        Uri intentData = intent.getData();
        if (intentData != null && SliceProvider.SLICE_TYPE.equals(resolver.getType(intentData))) {
            return intentData;
        }
        ResolveInfo resolve = this.mContext.getPackageManager().resolveActivity(intent, 128);
        if (resolve == null || resolve.activityInfo == null || resolve.activityInfo.metaData == null || !resolve.activityInfo.metaData.containsKey(SLICE_METADATA_KEY)) {
            return null;
        }
        return Uri.parse(resolve.activityInfo.metaData.getString(SLICE_METADATA_KEY));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006e, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006f, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x009e, code lost:
        r10 = r2;
        r2 = r1;
        r1 = r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.app.slice.Slice bindSlice(android.content.Intent r12, java.util.Set<android.app.slice.SliceSpec> r13) {
        /*
            r11 = this;
            java.lang.String r0 = "intent"
            com.android.internal.util.Preconditions.checkNotNull(r12, r0)
            android.content.ComponentName r0 = r12.getComponent()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001c
            java.lang.String r0 = r12.getPackage()
            if (r0 != 0) goto L_0x001c
            android.net.Uri r0 = r12.getData()
            if (r0 == 0) goto L_0x001a
            goto L_0x001c
        L_0x001a:
            r0 = r1
            goto L_0x001d
        L_0x001c:
            r0 = r2
        L_0x001d:
            java.lang.String r3 = "Slice intent must be explicit %s"
            java.lang.Object[] r4 = new java.lang.Object[r2]
            r4[r1] = r12
            com.android.internal.util.Preconditions.checkArgument(r0, r3, r4)
            android.content.Context r0 = r11.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            android.net.Uri r3 = r11.resolveStatic(r12, r0)
            if (r3 == 0) goto L_0x0037
            android.app.slice.Slice r1 = r11.bindSlice((android.net.Uri) r3, (java.util.Set<android.app.slice.SliceSpec>) r13)
            return r1
        L_0x0037:
            java.lang.String r4 = r11.getAuthority(r12)
            r5 = 0
            if (r4 != 0) goto L_0x003f
            return r5
        L_0x003f:
            android.net.Uri$Builder r6 = new android.net.Uri$Builder
            r6.<init>()
            java.lang.String r7 = "content"
            android.net.Uri$Builder r6 = r6.scheme(r7)
            android.net.Uri$Builder r6 = r6.authority((java.lang.String) r4)
            android.net.Uri r6 = r6.build()
            android.content.ContentProviderClient r7 = r0.acquireUnstableContentProviderClient((android.net.Uri) r6)     // Catch:{ RemoteException -> 0x00a7 }
            if (r7 != 0) goto L_0x0073
            java.lang.String r8 = "SliceManager"
            java.lang.String r9 = "Unknown URI: %s"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            r2[r1] = r6     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            java.lang.String r1 = java.lang.String.format(r9, r2)     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            android.util.Log.w((java.lang.String) r8, (java.lang.String) r1)     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            if (r7 == 0) goto L_0x006d
            $closeResource(r5, r7)     // Catch:{ RemoteException -> 0x00a7 }
        L_0x006d:
            return r5
        L_0x006e:
            r1 = move-exception
            r2 = r5
            goto L_0x00a1
        L_0x0071:
            r1 = move-exception
            goto L_0x009c
        L_0x0073:
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            r1.<init>()     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            java.lang.String r2 = "slice_intent"
            r1.putParcelable(r2, r12)     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            java.lang.String r2 = "map_slice"
            android.os.Bundle r2 = r7.call(r2, r5, r1)     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            if (r2 != 0) goto L_0x008d
            if (r7 == 0) goto L_0x008c
            $closeResource(r5, r7)     // Catch:{ RemoteException -> 0x00a7 }
        L_0x008c:
            return r5
        L_0x008d:
            java.lang.String r8 = "slice"
            android.os.Parcelable r8 = r2.getParcelable(r8)     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            android.app.slice.Slice r8 = (android.app.slice.Slice) r8     // Catch:{ Throwable -> 0x0071, all -> 0x006e }
            if (r7 == 0) goto L_0x009b
            $closeResource(r5, r7)     // Catch:{ RemoteException -> 0x00a7 }
        L_0x009b:
            return r8
        L_0x009c:
            throw r1     // Catch:{ all -> 0x009d }
        L_0x009d:
            r2 = move-exception
            r10 = r2
            r2 = r1
            r1 = r10
        L_0x00a1:
            if (r7 == 0) goto L_0x00a6
            $closeResource(r2, r7)     // Catch:{ RemoteException -> 0x00a7 }
        L_0x00a6:
            throw r1     // Catch:{ RemoteException -> 0x00a7 }
        L_0x00a7:
            r1 = move-exception
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.slice.SliceManager.bindSlice(android.content.Intent, java.util.Set):android.app.slice.Slice");
    }

    @Deprecated
    public Slice bindSlice(Intent intent, List<SliceSpec> supportedSpecs) {
        return bindSlice(intent, (Set<SliceSpec>) new ArraySet(supportedSpecs));
    }

    public int checkSlicePermission(Uri uri, int pid, int uid) {
        try {
            return this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), (String) null, pid, uid, (String[]) null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantSlicePermission(String toPackage, Uri uri) {
        try {
            this.mService.grantSlicePermission(this.mContext.getPackageName(), toPackage, uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void revokeSlicePermission(String toPackage, Uri uri) {
        try {
            this.mService.revokeSlicePermission(this.mContext.getPackageName(), toPackage, uri);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enforceSlicePermission(Uri uri, String pkg, int pid, int uid, String[] autoGrantPermissions) {
        try {
            if (!UserHandle.isSameApp(uid, Process.myUid())) {
                if (pkg != null) {
                    if (this.mService.checkSlicePermission(uri, this.mContext.getPackageName(), pkg, pid, uid, autoGrantPermissions) == -1) {
                        throw new SecurityException("User " + uid + " does not have slice permission for " + uri + ".");
                    }
                    return;
                }
                throw new SecurityException("No pkg specified");
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void grantPermissionFromUser(Uri uri, String pkg, boolean allSlices) {
        try {
            this.mService.grantPermissionFromUser(uri, pkg, this.mContext.getPackageName(), allSlices);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
