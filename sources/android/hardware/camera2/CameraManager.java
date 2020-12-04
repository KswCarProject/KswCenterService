package android.hardware.camera2;

import android.app.ActivityThread;
import android.content.Context;
import android.hardware.CameraStatus;
import android.hardware.ICameraService;
import android.hardware.ICameraServiceListener;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.impl.CameraDeviceImpl;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.legacy.LegacyMetadataMapper;
import android.os.Binder;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class CameraManager {
    private static final int API_VERSION_1 = 1;
    private static final int API_VERSION_2 = 2;
    private static final int CAMERA_TYPE_ALL = 1;
    private static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    private static final String TAG = "CameraManager";
    private static final int USE_CALLING_UID = -1;
    private final boolean DEBUG = false;
    private final Context mContext;
    private ArrayList<String> mDeviceIdList;
    private final Object mLock = new Object();

    public CameraManager(Context context) {
        synchronized (this.mLock) {
            this.mContext = context;
        }
    }

    public String[] getCameraIdList() throws CameraAccessException {
        return CameraManagerGlobal.get().getCameraIdList();
    }

    public void registerAvailabilityCallback(AvailabilityCallback callback, Handler handler) {
        CameraManagerGlobal.get().registerAvailabilityCallback(callback, CameraDeviceImpl.checkAndWrapHandler(handler));
    }

    public void registerAvailabilityCallback(Executor executor, AvailabilityCallback callback) {
        if (executor != null) {
            CameraManagerGlobal.get().registerAvailabilityCallback(callback, executor);
            return;
        }
        throw new IllegalArgumentException("executor was null");
    }

    public void unregisterAvailabilityCallback(AvailabilityCallback callback) {
        CameraManagerGlobal.get().unregisterAvailabilityCallback(callback);
    }

    public void registerTorchCallback(TorchCallback callback, Handler handler) {
        CameraManagerGlobal.get().registerTorchCallback(callback, CameraDeviceImpl.checkAndWrapHandler(handler));
    }

    public void registerTorchCallback(Executor executor, TorchCallback callback) {
        if (executor != null) {
            CameraManagerGlobal.get().registerTorchCallback(callback, executor);
            return;
        }
        throw new IllegalArgumentException("executor was null");
    }

    public void unregisterTorchCallback(TorchCallback callback) {
        CameraManagerGlobal.get().unregisterTorchCallback(callback);
    }

    private Size getDisplaySize() {
        Size ret = new Size(0, 0);
        try {
            Display display = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            if (height > width) {
                height = width;
                width = display.getHeight();
            }
            return new Size(width, height);
        } catch (Exception e) {
            Log.e(TAG, "getDisplaySize Failed. " + e.toString());
            return ret;
        }
    }

    public CameraCharacteristics getCameraCharacteristics(String cameraId) throws CameraAccessException {
        CameraCharacteristics characteristics = null;
        if (!CameraManagerGlobal.sCameraServiceDisabled) {
            synchronized (this.mLock) {
                ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
                if (cameraService != null) {
                    try {
                        Size displaySize = getDisplaySize();
                        if (isHiddenPhysicalCamera(cameraId) || supportsCamera2ApiLocked(cameraId)) {
                            CameraMetadataNative info = cameraService.getCameraCharacteristics(cameraId);
                            try {
                                info.setCameraId(Integer.parseInt(cameraId));
                            } catch (NumberFormatException e) {
                                Log.e(TAG, "Failed to parse camera Id " + cameraId + " to integer");
                            }
                            info.setDisplaySize(displaySize);
                            characteristics = new CameraCharacteristics(info);
                        } else {
                            int id = Integer.parseInt(cameraId);
                            characteristics = LegacyMetadataMapper.createCharacteristics(cameraService.getLegacyParameters(id), cameraService.getCameraInfo(id), id, displaySize);
                        }
                    } catch (ServiceSpecificException e2) {
                        throwAsPublicException(e2);
                    } catch (RemoteException e3) {
                        throw new CameraAccessException(2, "Camera service is currently unavailable", e3);
                    }
                } else {
                    throw new CameraAccessException(2, "Camera service is currently unavailable");
                }
            }
            return characteristics;
        }
        throw new IllegalArgumentException("No cameras available on device");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0070, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0072, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0073, code lost:
        r0 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x008b, code lost:
        throw new java.lang.IllegalArgumentException("Expected cameraId to be numeric, but it was: " + r8);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:13:0x0040, B:19:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a7 A[Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c, all -> 0x00ec }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00df A[Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c, all -> 0x00ec }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.hardware.camera2.CameraDevice openCameraDeviceUserAsync(java.lang.String r16, android.hardware.camera2.CameraDevice.StateCallback r17, java.util.concurrent.Executor r18, int r19) throws android.hardware.camera2.CameraAccessException {
        /*
            r15 = this;
            r1 = r15
            r8 = r16
            android.hardware.camera2.CameraCharacteristics r9 = r15.getCameraCharacteristics(r16)
            r10 = 0
            java.lang.Object r11 = r1.mLock
            monitor-enter(r11)
            r12 = 0
            android.hardware.camera2.impl.CameraDeviceImpl r0 = new android.hardware.camera2.impl.CameraDeviceImpl     // Catch:{ all -> 0x00e7 }
            android.content.Context r2 = r1.mContext     // Catch:{ all -> 0x00e7 }
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo()     // Catch:{ all -> 0x00e7 }
            int r7 = r2.targetSdkVersion     // Catch:{ all -> 0x00e7 }
            r2 = r0
            r3 = r16
            r4 = r17
            r5 = r18
            r6 = r9
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00e7 }
            r2 = r0
            android.hardware.camera2.impl.CameraDeviceImpl$CameraDeviceCallbacks r0 = r2.getCallbacks()     // Catch:{ all -> 0x00e7 }
            r3 = r0
            r4 = 4
            boolean r0 = r15.supportsCamera2ApiLocked(r16)     // Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c }
            if (r0 == 0) goto L_0x0052
            android.hardware.camera2.CameraManager$CameraManagerGlobal r0 = android.hardware.camera2.CameraManager.CameraManagerGlobal.get()     // Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c }
            android.hardware.ICameraService r0 = r0.getCameraService()     // Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c }
            if (r0 == 0) goto L_0x0048
            android.content.Context r5 = r1.mContext     // Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c }
            java.lang.String r5 = r5.getOpPackageName()     // Catch:{ ServiceSpecificException -> 0x009e, RemoteException -> 0x008c }
            r6 = r19
            android.hardware.camera2.ICameraDeviceUser r5 = r0.connectDevice(r3, r8, r5, r6)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            r0 = r5
        L_0x0046:
            r12 = r0
            goto L_0x006c
        L_0x0048:
            r6 = r19
            android.os.ServiceSpecificException r5 = new android.os.ServiceSpecificException     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            java.lang.String r7 = "Camera service is currently unavailable"
            r5.<init>(r4, r7)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            throw r5     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
        L_0x0052:
            r6 = r19
            int r0 = java.lang.Integer.parseInt(r16)     // Catch:{ NumberFormatException -> 0x0072 }
            java.lang.String r5 = "CameraManager"
            java.lang.String r7 = "Using legacy camera HAL."
            android.util.Log.i(r5, r7)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            android.util.Size r5 = r15.getDisplaySize()     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            android.hardware.camera2.legacy.CameraDeviceUserShim r5 = android.hardware.camera2.legacy.CameraDeviceUserShim.connectBinderShim(r3, r0, r5)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            r0 = r5
            goto L_0x0046
        L_0x006c:
            goto L_0x00d9
        L_0x006e:
            r0 = move-exception
            goto L_0x008f
        L_0x0070:
            r0 = move-exception
            goto L_0x00a1
        L_0x0072:
            r0 = move-exception
            r5 = r0
            r0 = r5
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            r7.<init>()     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            java.lang.String r13 = "Expected cameraId to be numeric, but it was: "
            r7.append(r13)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            r7.append(r8)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            java.lang.String r7 = r7.toString()     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            r5.<init>(r7)     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
            throw r5     // Catch:{ ServiceSpecificException -> 0x0070, RemoteException -> 0x006e }
        L_0x008c:
            r0 = move-exception
            r6 = r19
        L_0x008f:
            android.os.ServiceSpecificException r5 = new android.os.ServiceSpecificException     // Catch:{ all -> 0x00ec }
            java.lang.String r7 = "Camera service is currently unavailable"
            r5.<init>(r4, r7)     // Catch:{ all -> 0x00ec }
            r4 = r5
            r2.setRemoteFailure(r4)     // Catch:{ all -> 0x00ec }
            throwAsPublicException(r4)     // Catch:{ all -> 0x00ec }
            goto L_0x00d9
        L_0x009e:
            r0 = move-exception
            r6 = r19
        L_0x00a1:
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            r7 = 9
            if (r5 == r7) goto L_0x00df
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            r7 = 6
            r13 = 7
            if (r5 == r13) goto L_0x00c6
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            r14 = 8
            if (r5 == r14) goto L_0x00c6
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            if (r5 == r7) goto L_0x00c6
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            if (r5 == r4) goto L_0x00c6
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            r14 = 10
            if (r5 != r14) goto L_0x00c2
            goto L_0x00c6
        L_0x00c2:
            throwAsPublicException(r0)     // Catch:{ all -> 0x00ec }
            goto L_0x006c
        L_0x00c6:
            r2.setRemoteFailure(r0)     // Catch:{ all -> 0x00ec }
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            if (r5 == r7) goto L_0x00d5
            int r5 = r0.errorCode     // Catch:{ all -> 0x00ec }
            if (r5 == r4) goto L_0x00d5
            int r4 = r0.errorCode     // Catch:{ all -> 0x00ec }
            if (r4 != r13) goto L_0x006c
        L_0x00d5:
            throwAsPublicException(r0)     // Catch:{ all -> 0x00ec }
            goto L_0x006c
        L_0x00d9:
            r2.setRemoteDevice(r12)     // Catch:{ all -> 0x00ec }
            r10 = r2
            monitor-exit(r11)     // Catch:{ all -> 0x00ec }
            return r10
        L_0x00df:
            java.lang.AssertionError r4 = new java.lang.AssertionError     // Catch:{ all -> 0x00ec }
            java.lang.String r5 = "Should've gone down the shim path"
            r4.<init>(r5)     // Catch:{ all -> 0x00ec }
            throw r4     // Catch:{ all -> 0x00ec }
        L_0x00e7:
            r0 = move-exception
            r6 = r19
        L_0x00ea:
            monitor-exit(r11)     // Catch:{ all -> 0x00ec }
            throw r0
        L_0x00ec:
            r0 = move-exception
            goto L_0x00ea
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.camera2.CameraManager.openCameraDeviceUserAsync(java.lang.String, android.hardware.camera2.CameraDevice$StateCallback, java.util.concurrent.Executor, int):android.hardware.camera2.CameraDevice");
    }

    public void openCamera(String cameraId, CameraDevice.StateCallback callback, Handler handler) throws CameraAccessException {
        openCameraForUid(cameraId, callback, CameraDeviceImpl.checkAndWrapHandler(handler), -1);
    }

    public void openCamera(String cameraId, Executor executor, CameraDevice.StateCallback callback) throws CameraAccessException {
        if (executor != null) {
            openCameraForUid(cameraId, callback, executor, -1);
            return;
        }
        throw new IllegalArgumentException("executor was null");
    }

    public void openCameraForUid(String cameraId, CameraDevice.StateCallback callback, Executor executor, int clientUid) throws CameraAccessException {
        if (cameraId == null) {
            throw new IllegalArgumentException("cameraId was null");
        } else if (callback == null) {
            throw new IllegalArgumentException("callback was null");
        } else if (!CameraManagerGlobal.sCameraServiceDisabled) {
            openCameraDeviceUserAsync(cameraId, callback, executor, clientUid);
        } else {
            throw new IllegalArgumentException("No cameras available on device");
        }
    }

    public void setTorchMode(String cameraId, boolean enabled) throws CameraAccessException {
        if (!CameraManagerGlobal.sCameraServiceDisabled) {
            CameraManagerGlobal.get().setTorchMode(cameraId, enabled);
            return;
        }
        throw new IllegalArgumentException("No cameras available on device");
    }

    public static abstract class AvailabilityCallback {
        public void onCameraAvailable(String cameraId) {
        }

        public void onCameraUnavailable(String cameraId) {
        }

        public void onCameraAccessPrioritiesChanged() {
        }
    }

    public static abstract class TorchCallback {
        public void onTorchModeUnavailable(String cameraId) {
        }

        public void onTorchModeChanged(String cameraId, boolean enabled) {
        }
    }

    public static void throwAsPublicException(Throwable t) throws CameraAccessException {
        int reason;
        if (t instanceof ServiceSpecificException) {
            ServiceSpecificException e = (ServiceSpecificException) t;
            switch (e.errorCode) {
                case 1:
                    throw new SecurityException(e.getMessage(), e);
                case 2:
                case 3:
                    throw new IllegalArgumentException(e.getMessage(), e);
                case 4:
                    reason = 2;
                    break;
                case 6:
                    reason = 1;
                    break;
                case 7:
                    reason = 4;
                    break;
                case 8:
                    reason = 5;
                    break;
                case 9:
                    reason = 1000;
                    break;
                default:
                    reason = 3;
                    break;
            }
            throw new CameraAccessException(reason, e.getMessage(), e);
        } else if (t instanceof DeadObjectException) {
            throw new CameraAccessException(2, "Camera service has died unexpectedly", t);
        } else if (t instanceof RemoteException) {
            throw new UnsupportedOperationException("An unknown RemoteException was thrown which should never happen.", t);
        } else if (t instanceof RuntimeException) {
            throw ((RuntimeException) t);
        }
    }

    private boolean supportsCamera2ApiLocked(String cameraId) {
        return supportsCameraApiLocked(cameraId, 2);
    }

    private boolean supportsCameraApiLocked(String cameraId, int apiVersion) {
        try {
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                return false;
            }
            return cameraService.supportsCameraApi(cameraId, apiVersion);
        } catch (RemoteException e) {
            return false;
        }
    }

    public static boolean isHiddenPhysicalCamera(String cameraId) {
        try {
            ICameraService cameraService = CameraManagerGlobal.get().getCameraService();
            if (cameraService == null) {
                return false;
            }
            return cameraService.isHiddenPhysicalCamera(cameraId);
        } catch (RemoteException e) {
            return false;
        }
    }

    private static final class CameraManagerGlobal extends ICameraServiceListener.Stub implements IBinder.DeathRecipient {
        private static final String CAMERA_SERVICE_BINDER_NAME = "media.camera";
        private static final String TAG = "CameraManagerGlobal";
        private static final CameraManagerGlobal gCameraManager = new CameraManagerGlobal();
        public static final boolean sCameraServiceDisabled = SystemProperties.getBoolean("config.disable_cameraservice", false);
        private final int CAMERA_SERVICE_RECONNECT_DELAY_MS = 1000;
        private final boolean DEBUG = false;
        private final ArrayMap<AvailabilityCallback, Executor> mCallbackMap = new ArrayMap<>();
        private ICameraService mCameraService;
        private final ArrayMap<String, Integer> mDeviceStatus = new ArrayMap<>();
        private final Object mLock = new Object();
        private final ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);
        private final ArrayMap<TorchCallback, Executor> mTorchCallbackMap = new ArrayMap<>();
        private Binder mTorchClientBinder = new Binder();
        private final ArrayMap<String, Integer> mTorchStatus = new ArrayMap<>();

        private CameraManagerGlobal() {
        }

        public static CameraManagerGlobal get() {
            return gCameraManager;
        }

        public IBinder asBinder() {
            return this;
        }

        public ICameraService getCameraService() {
            ICameraService iCameraService;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                if (this.mCameraService == null && !sCameraServiceDisabled) {
                    Log.e(TAG, "Camera service is unavailable");
                }
                iCameraService = this.mCameraService;
            }
            return iCameraService;
        }

        private void connectCameraServiceLocked() {
            if (this.mCameraService == null && !sCameraServiceDisabled) {
                Log.i(TAG, "Connecting to camera service");
                IBinder cameraServiceBinder = ServiceManager.getService(CAMERA_SERVICE_BINDER_NAME);
                if (cameraServiceBinder != null) {
                    try {
                        cameraServiceBinder.linkToDeath(this, 0);
                        ICameraService cameraService = ICameraService.Stub.asInterface(cameraServiceBinder);
                        try {
                            CameraMetadataNative.setupGlobalVendorTagDescriptor();
                        } catch (ServiceSpecificException e) {
                            handleRecoverableSetupErrors(e);
                        }
                        try {
                            for (CameraStatus c : cameraService.addListener(this)) {
                                onStatusChangedLocked(c.status, c.cameraId);
                            }
                            this.mCameraService = cameraService;
                        } catch (ServiceSpecificException e2) {
                            throw new IllegalStateException("Failed to register a camera service listener", e2);
                        } catch (RemoteException e3) {
                        }
                    } catch (RemoteException e4) {
                    }
                }
            }
        }

        public String[] getCameraIdList() {
            String[] cameraIds;
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                boolean exposeAuxCamera = false;
                String packageName = ActivityThread.currentOpPackageName();
                String packageList = SystemProperties.get("vendor.camera.aux.packagelist");
                if (packageList.length() > 0) {
                    TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
                    splitter.setString(packageList);
                    Iterator it = splitter.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (packageName.equals((String) it.next())) {
                            exposeAuxCamera = true;
                            break;
                        }
                    }
                }
                int i = 0;
                int idCount = 0;
                int i2 = 0;
                while (true) {
                    if (i2 < this.mDeviceStatus.size()) {
                        if (!exposeAuxCamera && i2 == 2) {
                            break;
                        }
                        int status = this.mDeviceStatus.valueAt(i2).intValue();
                        if (status != 0) {
                            if (status != 2) {
                                idCount++;
                            }
                        }
                        i2++;
                    } else {
                        break;
                    }
                }
                cameraIds = new String[idCount];
                int idCount2 = 0;
                while (true) {
                    if (i < this.mDeviceStatus.size()) {
                        if (!exposeAuxCamera && i == 2) {
                            break;
                        }
                        int status2 = this.mDeviceStatus.valueAt(i).intValue();
                        if (status2 != 0) {
                            if (status2 != 2) {
                                cameraIds[idCount2] = this.mDeviceStatus.keyAt(i);
                                idCount2++;
                            }
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            }
            Arrays.sort(cameraIds, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    int s1Int;
                    int s2Int;
                    try {
                        s1Int = Integer.parseInt(s1);
                    } catch (NumberFormatException e) {
                        s1Int = -1;
                    }
                    try {
                        s2Int = Integer.parseInt(s2);
                    } catch (NumberFormatException e2) {
                        s2Int = -1;
                    }
                    if (s1Int >= 0 && s2Int >= 0) {
                        return s1Int - s2Int;
                    }
                    if (s1Int >= 0) {
                        return -1;
                    }
                    if (s2Int >= 0) {
                        return 1;
                    }
                    return s1.compareTo(s2);
                }
            });
            return cameraIds;
        }

        public void setTorchMode(String cameraId, boolean enabled) throws CameraAccessException {
            synchronized (this.mLock) {
                if (cameraId != null) {
                    boolean exposeAuxCamera = false;
                    String packageName = ActivityThread.currentOpPackageName();
                    String packageList = SystemProperties.get("vendor.camera.aux.packagelist");
                    if (packageList.length() > 0) {
                        TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
                        splitter.setString(packageList);
                        Iterator it = splitter.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            } else if (packageName.equals((String) it.next())) {
                                exposeAuxCamera = true;
                                break;
                            }
                        }
                    }
                    if (!exposeAuxCamera) {
                        if (Integer.parseInt(cameraId) >= 2) {
                            throw new IllegalArgumentException("invalid cameraId");
                        }
                    }
                    ICameraService cameraService = getCameraService();
                    if (cameraService != null) {
                        try {
                            cameraService.setTorchMode(cameraId, enabled, this.mTorchClientBinder);
                        } catch (ServiceSpecificException e) {
                            CameraManager.throwAsPublicException(e);
                        } catch (RemoteException e2) {
                            throw new CameraAccessException(2, "Camera service is currently unavailable");
                        }
                    } else {
                        throw new CameraAccessException(2, "Camera service is currently unavailable");
                    }
                } else {
                    throw new IllegalArgumentException("cameraId was null");
                }
            }
        }

        private void handleRecoverableSetupErrors(ServiceSpecificException e) {
            if (e.errorCode == 4) {
                Log.w(TAG, e.getMessage());
                return;
            }
            throw new IllegalStateException(e);
        }

        private boolean isAvailable(int status) {
            if (status != 1) {
                return false;
            }
            return true;
        }

        private boolean validStatus(int status) {
            if (status == -2) {
                return true;
            }
            switch (status) {
                case 0:
                case 1:
                case 2:
                    return true;
                default:
                    return false;
            }
        }

        private boolean validTorchStatus(int status) {
            switch (status) {
                case 0:
                case 1:
                case 2:
                    return true;
                default:
                    return false;
            }
        }

        private void postSingleAccessPriorityChangeUpdate(final AvailabilityCallback callback, Executor executor) {
            long ident = Binder.clearCallingIdentity();
            try {
                executor.execute(new Runnable() {
                    public void run() {
                        callback.onCameraAccessPrioritiesChanged();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        private void postSingleUpdate(final AvailabilityCallback callback, Executor executor, final String id, int status) {
            if (isAvailable(status)) {
                long ident = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() {
                        public void run() {
                            callback.onCameraAvailable(id);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            } else {
                long ident2 = Binder.clearCallingIdentity();
                try {
                    executor.execute(new Runnable() {
                        public void run() {
                            callback.onCameraUnavailable(id);
                        }
                    });
                } finally {
                    Binder.restoreCallingIdentity(ident2);
                }
            }
        }

        private void postSingleTorchUpdate(TorchCallback callback, Executor executor, String id, int status) {
            switch (status) {
                case 1:
                case 2:
                    long ident = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable(id, status) {
                            private final /* synthetic */ String f$1;
                            private final /* synthetic */ int f$2;

                            {
                                this.f$1 = r2;
                                this.f$2 = r3;
                            }

                            public final void run() {
                                CameraManager.CameraManagerGlobal.lambda$postSingleTorchUpdate$0(CameraManager.TorchCallback.this, this.f$1, this.f$2);
                            }
                        });
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(ident);
                    }
                default:
                    long ident2 = Binder.clearCallingIdentity();
                    try {
                        executor.execute(new Runnable(id) {
                            private final /* synthetic */ String f$1;

                            {
                                this.f$1 = r2;
                            }

                            public final void run() {
                                CameraManager.TorchCallback.this.onTorchModeUnavailable(this.f$1);
                            }
                        });
                        return;
                    } finally {
                        Binder.restoreCallingIdentity(ident2);
                    }
            }
        }

        static /* synthetic */ void lambda$postSingleTorchUpdate$0(TorchCallback callback, String id, int status) {
            callback.onTorchModeChanged(id, status == 2);
        }

        private void updateCallbackLocked(AvailabilityCallback callback, Executor executor) {
            for (int i = 0; i < this.mDeviceStatus.size(); i++) {
                postSingleUpdate(callback, executor, this.mDeviceStatus.keyAt(i), this.mDeviceStatus.valueAt(i).intValue());
            }
        }

        private void onStatusChangedLocked(int status, String id) {
            Integer oldStatus;
            boolean exposeMonoCamera = false;
            String packageName = ActivityThread.currentOpPackageName();
            String packageList = SystemProperties.get("vendor.camera.aux.packagelist");
            if (packageList.length() > 0) {
                TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
                splitter.setString(packageList);
                Iterator it = splitter.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (packageName.equals((String) it.next())) {
                            exposeMonoCamera = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (exposeMonoCamera || Integer.parseInt(id) < 2) {
                if (!validStatus(status)) {
                    Log.e(TAG, String.format("Ignoring invalid device %s status 0x%x", new Object[]{id, Integer.valueOf(status)}));
                    return;
                }
                if (status == 0) {
                    oldStatus = this.mDeviceStatus.remove(id);
                } else {
                    oldStatus = this.mDeviceStatus.put(id, Integer.valueOf(status));
                }
                if (oldStatus != null && oldStatus.intValue() == status) {
                    return;
                }
                if (oldStatus == null || isAvailable(status) != isAvailable(oldStatus.intValue())) {
                    int callbackCount = this.mCallbackMap.size();
                    for (int i = 0; i < callbackCount; i++) {
                        postSingleUpdate(this.mCallbackMap.keyAt(i), this.mCallbackMap.valueAt(i), id, status);
                    }
                    return;
                }
                return;
            }
            Log.w(TAG, "[soar.cts] ignore the status update of camera: " + id);
        }

        private void updateTorchCallbackLocked(TorchCallback callback, Executor executor) {
            for (int i = 0; i < this.mTorchStatus.size(); i++) {
                postSingleTorchUpdate(callback, executor, this.mTorchStatus.keyAt(i), this.mTorchStatus.valueAt(i).intValue());
            }
        }

        private void onTorchStatusChangedLocked(int status, String id) {
            boolean exposeMonoCamera = false;
            String packageName = ActivityThread.currentOpPackageName();
            String packageList = SystemProperties.get("vendor.camera.aux.packagelist");
            if (packageList.length() > 0) {
                TextUtils.StringSplitter splitter = new TextUtils.SimpleStringSplitter(',');
                splitter.setString(packageList);
                Iterator it = splitter.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (packageName.equals((String) it.next())) {
                            exposeMonoCamera = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (exposeMonoCamera || Integer.parseInt(id) < 2) {
                if (!validTorchStatus(status)) {
                    Log.e(TAG, String.format("Ignoring invalid device %s torch status 0x%x", new Object[]{id, Integer.valueOf(status)}));
                    return;
                }
                Integer oldStatus = this.mTorchStatus.put(id, Integer.valueOf(status));
                if (oldStatus == null || oldStatus.intValue() != status) {
                    int callbackCount = this.mTorchCallbackMap.size();
                    for (int i = 0; i < callbackCount; i++) {
                        postSingleTorchUpdate(this.mTorchCallbackMap.keyAt(i), this.mTorchCallbackMap.valueAt(i), id, status);
                    }
                    return;
                }
                return;
            }
            Log.w(TAG, "ignore the torch status update of camera: " + id);
        }

        public void registerAvailabilityCallback(AvailabilityCallback callback, Executor executor) {
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                if (this.mCallbackMap.put(callback, executor) == null) {
                    updateCallbackLocked(callback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterAvailabilityCallback(AvailabilityCallback callback) {
            synchronized (this.mLock) {
                this.mCallbackMap.remove(callback);
            }
        }

        public void registerTorchCallback(TorchCallback callback, Executor executor) {
            synchronized (this.mLock) {
                connectCameraServiceLocked();
                if (this.mTorchCallbackMap.put(callback, executor) == null) {
                    updateTorchCallbackLocked(callback, executor);
                }
                if (this.mCameraService == null) {
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void unregisterTorchCallback(TorchCallback callback) {
            synchronized (this.mLock) {
                this.mTorchCallbackMap.remove(callback);
            }
        }

        public void onStatusChanged(int status, String cameraId) throws RemoteException {
            synchronized (this.mLock) {
                onStatusChangedLocked(status, cameraId);
            }
        }

        public void onTorchStatusChanged(int status, String cameraId) throws RemoteException {
            synchronized (this.mLock) {
                onTorchStatusChangedLocked(status, cameraId);
            }
        }

        public void onCameraAccessPrioritiesChanged() {
            synchronized (this.mLock) {
                int callbackCount = this.mCallbackMap.size();
                for (int i = 0; i < callbackCount; i++) {
                    postSingleAccessPriorityChangeUpdate(this.mCallbackMap.keyAt(i), this.mCallbackMap.valueAt(i));
                }
            }
        }

        private void scheduleCameraServiceReconnectionLocked() {
            if (!this.mCallbackMap.isEmpty() || !this.mTorchCallbackMap.isEmpty()) {
                try {
                    this.mScheduler.schedule(new Runnable() {
                        public final void run() {
                            CameraManager.CameraManagerGlobal.lambda$scheduleCameraServiceReconnectionLocked$2(CameraManager.CameraManagerGlobal.this);
                        }
                    }, 1000, TimeUnit.MILLISECONDS);
                } catch (RejectedExecutionException e) {
                    Log.e(TAG, "Failed to schedule camera service re-connect: " + e);
                }
            }
        }

        public static /* synthetic */ void lambda$scheduleCameraServiceReconnectionLocked$2(CameraManagerGlobal cameraManagerGlobal) {
            if (cameraManagerGlobal.getCameraService() == null) {
                synchronized (cameraManagerGlobal.mLock) {
                    cameraManagerGlobal.scheduleCameraServiceReconnectionLocked();
                }
            }
        }

        public void binderDied() {
            synchronized (this.mLock) {
                if (this.mCameraService != null) {
                    this.mCameraService = null;
                    for (int i = 0; i < this.mDeviceStatus.size(); i++) {
                        onStatusChangedLocked(0, this.mDeviceStatus.keyAt(i));
                    }
                    for (int i2 = 0; i2 < this.mTorchStatus.size(); i2++) {
                        onTorchStatusChangedLocked(0, this.mTorchStatus.keyAt(i2));
                    }
                    scheduleCameraServiceReconnectionLocked();
                }
            }
        }
    }
}
