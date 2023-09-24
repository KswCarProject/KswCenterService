package android.telephony.data;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.p007os.Handler;
import android.p007os.HandlerThread;
import android.p007os.IBinder;
import android.p007os.Looper;
import android.p007os.Message;
import android.p007os.RemoteException;
import android.telephony.Rlog;
import android.telephony.data.IQualifiedNetworksService;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;
import java.util.function.ToIntFunction;

@SystemApi
/* loaded from: classes3.dex */
public abstract class QualifiedNetworksService extends Service {
    private static final int QNS_CREATE_NETWORK_AVAILABILITY_PROVIDER = 1;
    private static final int QNS_REMOVE_ALL_NETWORK_AVAILABILITY_PROVIDERS = 3;
    private static final int QNS_REMOVE_NETWORK_AVAILABILITY_PROVIDER = 2;
    private static final int QNS_UPDATE_QUALIFIED_NETWORKS = 4;
    public static final String QUALIFIED_NETWORKS_SERVICE_INTERFACE = "android.telephony.data.QualifiedNetworksService";
    private static final String TAG = QualifiedNetworksService.class.getSimpleName();
    private final QualifiedNetworksServiceHandler mHandler;
    private final SparseArray<NetworkAvailabilityProvider> mProviders = new SparseArray<>();
    @VisibleForTesting
    public final IQualifiedNetworksServiceWrapper mBinder = new IQualifiedNetworksServiceWrapper();
    private final HandlerThread mHandlerThread = new HandlerThread(TAG);

    public abstract NetworkAvailabilityProvider onCreateNetworkAvailabilityProvider(int i);

    /* loaded from: classes3.dex */
    public abstract class NetworkAvailabilityProvider implements AutoCloseable {
        private IQualifiedNetworksServiceCallback mCallback;
        private SparseArray<int[]> mQualifiedNetworkTypesList = new SparseArray<>();
        private final int mSlotIndex;

        @Override // java.lang.AutoCloseable
        public abstract void close();

        public NetworkAvailabilityProvider(int slotIndex) {
            this.mSlotIndex = slotIndex;
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void registerForQualifiedNetworkTypesChanged(IQualifiedNetworksServiceCallback callback) {
            this.mCallback = callback;
            if (this.mCallback != null) {
                for (int i = 0; i < this.mQualifiedNetworkTypesList.size(); i++) {
                    try {
                        this.mCallback.onQualifiedNetworkTypesChanged(this.mQualifiedNetworkTypesList.keyAt(i), this.mQualifiedNetworkTypesList.valueAt(i));
                    } catch (RemoteException e) {
                        QualifiedNetworksService qualifiedNetworksService = QualifiedNetworksService.this;
                        qualifiedNetworksService.loge("Failed to call onQualifiedNetworksChanged. " + e);
                    }
                }
            }
        }

        public final void updateQualifiedNetworkTypes(int apnTypes, List<Integer> qualifiedNetworkTypes) {
            int[] qualifiedNetworkTypesArray = qualifiedNetworkTypes.stream().mapToInt(new ToIntFunction() { // from class: android.telephony.data.-$$Lambda$QualifiedNetworksService$NetworkAvailabilityProvider$sNPqwkqArvqymBmHYmxAc4rF5Es
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    int intValue;
                    intValue = ((Integer) obj).intValue();
                    return intValue;
                }
            }).toArray();
            QualifiedNetworksService.this.mHandler.obtainMessage(4, this.mSlotIndex, apnTypes, qualifiedNetworkTypesArray).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUpdateQualifiedNetworkTypes(int apnTypes, int[] qualifiedNetworkTypes) {
            this.mQualifiedNetworkTypesList.put(apnTypes, qualifiedNetworkTypes);
            if (this.mCallback != null) {
                try {
                    this.mCallback.onQualifiedNetworkTypesChanged(apnTypes, qualifiedNetworkTypes);
                } catch (RemoteException e) {
                    QualifiedNetworksService qualifiedNetworksService = QualifiedNetworksService.this;
                    qualifiedNetworksService.loge("Failed to call onQualifiedNetworksChanged. " + e);
                }
            }
        }
    }

    /* loaded from: classes3.dex */
    private class QualifiedNetworksServiceHandler extends Handler {
        QualifiedNetworksServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message message) {
            int slotIndex = message.arg1;
            NetworkAvailabilityProvider provider = (NetworkAvailabilityProvider) QualifiedNetworksService.this.mProviders.get(slotIndex);
            switch (message.what) {
                case 1:
                    if (QualifiedNetworksService.this.mProviders.get(slotIndex) != null) {
                        QualifiedNetworksService qualifiedNetworksService = QualifiedNetworksService.this;
                        qualifiedNetworksService.loge("Network availability provider for slot " + slotIndex + " already existed.");
                        return;
                    }
                    NetworkAvailabilityProvider provider2 = QualifiedNetworksService.this.onCreateNetworkAvailabilityProvider(slotIndex);
                    if (provider2 != null) {
                        QualifiedNetworksService.this.mProviders.put(slotIndex, provider2);
                        IQualifiedNetworksServiceCallback callback = (IQualifiedNetworksServiceCallback) message.obj;
                        provider2.registerForQualifiedNetworkTypesChanged(callback);
                        return;
                    }
                    QualifiedNetworksService qualifiedNetworksService2 = QualifiedNetworksService.this;
                    qualifiedNetworksService2.loge("Failed to create network availability provider. slot index = " + slotIndex);
                    return;
                case 2:
                    if (provider != null) {
                        provider.close();
                        QualifiedNetworksService.this.mProviders.remove(slotIndex);
                        return;
                    }
                    return;
                case 3:
                    for (int i = 0; i < QualifiedNetworksService.this.mProviders.size(); i++) {
                        NetworkAvailabilityProvider provider3 = (NetworkAvailabilityProvider) QualifiedNetworksService.this.mProviders.get(i);
                        if (provider3 != null) {
                            provider3.close();
                        }
                    }
                    QualifiedNetworksService.this.mProviders.clear();
                    return;
                case 4:
                    if (provider != null) {
                        provider.onUpdateQualifiedNetworkTypes(message.arg2, (int[]) message.obj);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public QualifiedNetworksService() {
        this.mHandlerThread.start();
        this.mHandler = new QualifiedNetworksServiceHandler(this.mHandlerThread.getLooper());
        log("Qualified networks service created");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (intent == null || !QUALIFIED_NETWORKS_SERVICE_INTERFACE.equals(intent.getAction())) {
            loge("Unexpected intent " + intent);
            return null;
        }
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        this.mHandler.obtainMessage(3).sendToTarget();
        return false;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandlerThread.quit();
    }

    /* loaded from: classes3.dex */
    private class IQualifiedNetworksServiceWrapper extends IQualifiedNetworksService.Stub {
        private IQualifiedNetworksServiceWrapper() {
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void createNetworkAvailabilityProvider(int slotIndex, IQualifiedNetworksServiceCallback callback) {
            QualifiedNetworksService.this.mHandler.obtainMessage(1, slotIndex, 0, callback).sendToTarget();
        }

        @Override // android.telephony.data.IQualifiedNetworksService
        public void removeNetworkAvailabilityProvider(int slotIndex) {
            QualifiedNetworksService.this.mHandler.obtainMessage(2, slotIndex, 0).sendToTarget();
        }
    }

    private void log(String s) {
        Rlog.m88d(TAG, s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loge(String s) {
        Rlog.m86e(TAG, s);
    }
}
