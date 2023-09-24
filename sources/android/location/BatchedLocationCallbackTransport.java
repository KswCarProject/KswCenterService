package android.location;

import android.content.Context;
import android.location.IBatchedLocationCallback;
import android.location.LocalListenerHelper;
import android.p007os.RemoteException;
import java.util.List;

/* loaded from: classes.dex */
class BatchedLocationCallbackTransport extends LocalListenerHelper<BatchedLocationCallback> {
    private final IBatchedLocationCallback mCallbackTransport;
    private final ILocationManager mLocationManager;

    public BatchedLocationCallbackTransport(Context context, ILocationManager locationManager) {
        super(context, "BatchedLocationCallbackTransport");
        this.mCallbackTransport = new CallbackTransport();
        this.mLocationManager = locationManager;
    }

    @Override // android.location.LocalListenerHelper
    protected boolean registerWithServer() throws RemoteException {
        return this.mLocationManager.addGnssBatchingCallback(this.mCallbackTransport, getContext().getPackageName());
    }

    @Override // android.location.LocalListenerHelper
    protected void unregisterFromServer() throws RemoteException {
        this.mLocationManager.removeGnssBatchingCallback();
    }

    /* loaded from: classes.dex */
    private class CallbackTransport extends IBatchedLocationCallback.Stub {
        private CallbackTransport() {
        }

        @Override // android.location.IBatchedLocationCallback
        public void onLocationBatch(final List<Location> locations) {
            LocalListenerHelper.ListenerOperation<BatchedLocationCallback> operation = new LocalListenerHelper.ListenerOperation<BatchedLocationCallback>() { // from class: android.location.BatchedLocationCallbackTransport.CallbackTransport.1
                @Override // android.location.LocalListenerHelper.ListenerOperation
                public void execute(BatchedLocationCallback callback) throws RemoteException {
                    callback.onLocationBatch(locations);
                }
            };
            BatchedLocationCallbackTransport.this.foreach(operation);
        }
    }
}
