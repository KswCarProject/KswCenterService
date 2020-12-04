package android.telephony.ims.compat.stub;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;
import com.android.ims.ImsConfig;
import com.android.ims.ImsConfigListener;
import com.android.ims.internal.IImsConfig;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ImsConfigImplBase {
    private static final String TAG = "ImsConfigImplBase";
    ImsConfigStub mImsConfigStub;

    @UnsupportedAppUsage
    public ImsConfigImplBase(Context context) {
        this.mImsConfigStub = new ImsConfigStub(this, context);
    }

    public int getProvisionedValue(int item) throws RemoteException {
        return -1;
    }

    public String getProvisionedStringValue(int item) throws RemoteException {
        return null;
    }

    public int setProvisionedValue(int item, int value) throws RemoteException {
        return 1;
    }

    public int setProvisionedStringValue(int item, String value) throws RemoteException {
        return 1;
    }

    public void getFeatureValue(int feature, int network, ImsConfigListener listener) throws RemoteException {
    }

    public void setFeatureValue(int feature, int network, int value, ImsConfigListener listener) throws RemoteException {
    }

    public boolean getVolteProvisioned() throws RemoteException {
        return false;
    }

    public void getVideoQuality(ImsConfigListener listener) throws RemoteException {
    }

    public void setVideoQuality(int quality, ImsConfigListener listener) throws RemoteException {
    }

    @UnsupportedAppUsage
    public IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public final void notifyProvisionedValueChanged(int item, int value) {
        this.mImsConfigStub.updateCachedValue(item, value, true);
    }

    public final void notifyProvisionedValueChanged(int item, String value) {
        this.mImsConfigStub.updateCachedValue(item, value, true);
    }

    @VisibleForTesting
    public static class ImsConfigStub extends IImsConfig.Stub {
        Context mContext;
        WeakReference<ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private HashMap<Integer, Integer> mProvisionedIntValue = new HashMap<>();
        private HashMap<Integer, String> mProvisionedStringValue = new HashMap<>();

        @VisibleForTesting
        public ImsConfigStub(ImsConfigImplBase imsConfigImplBase, Context context) {
            this.mContext = context;
            this.mImsConfigImplBaseWeakReference = new WeakReference<>(imsConfigImplBase);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized int getProvisionedValue(int r3) throws android.os.RemoteException {
            /*
                r2 = this;
                monitor-enter(r2)
                java.util.HashMap<java.lang.Integer, java.lang.Integer> r0 = r2.mProvisionedIntValue     // Catch:{ all -> 0x0030 }
                java.lang.Integer r1 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0030 }
                boolean r0 = r0.containsKey(r1)     // Catch:{ all -> 0x0030 }
                if (r0 == 0) goto L_0x001f
                java.util.HashMap<java.lang.Integer, java.lang.Integer> r0 = r2.mProvisionedIntValue     // Catch:{ all -> 0x0030 }
                java.lang.Integer r1 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x0030 }
                java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x0030 }
                java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x0030 }
                int r0 = r0.intValue()     // Catch:{ all -> 0x0030 }
                monitor-exit(r2)
                return r0
            L_0x001f:
                android.telephony.ims.compat.stub.ImsConfigImplBase r0 = r2.getImsConfigImpl()     // Catch:{ all -> 0x0030 }
                int r0 = r0.getProvisionedValue(r3)     // Catch:{ all -> 0x0030 }
                r1 = -1
                if (r0 == r1) goto L_0x002e
                r1 = 0
                r2.updateCachedValue((int) r3, (int) r0, (boolean) r1)     // Catch:{ all -> 0x0030 }
            L_0x002e:
                monitor-exit(r2)
                return r0
            L_0x0030:
                r3 = move-exception
                monitor-exit(r2)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.compat.stub.ImsConfigImplBase.ImsConfigStub.getProvisionedValue(int):int");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized java.lang.String getProvisionedStringValue(int r3) throws android.os.RemoteException {
            /*
                r2 = this;
                monitor-enter(r2)
                java.util.HashMap<java.lang.Integer, java.lang.Integer> r0 = r2.mProvisionedIntValue     // Catch:{ all -> 0x002b }
                java.lang.Integer r1 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x002b }
                boolean r0 = r0.containsKey(r1)     // Catch:{ all -> 0x002b }
                if (r0 == 0) goto L_0x001b
                java.util.HashMap<java.lang.Integer, java.lang.String> r0 = r2.mProvisionedStringValue     // Catch:{ all -> 0x002b }
                java.lang.Integer r1 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x002b }
                java.lang.Object r0 = r0.get(r1)     // Catch:{ all -> 0x002b }
                java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x002b }
                monitor-exit(r2)
                return r0
            L_0x001b:
                android.telephony.ims.compat.stub.ImsConfigImplBase r0 = r2.getImsConfigImpl()     // Catch:{ all -> 0x002b }
                java.lang.String r0 = r0.getProvisionedStringValue(r3)     // Catch:{ all -> 0x002b }
                if (r0 == 0) goto L_0x0029
                r1 = 0
                r2.updateCachedValue((int) r3, (java.lang.String) r0, (boolean) r1)     // Catch:{ all -> 0x002b }
            L_0x0029:
                monitor-exit(r2)
                return r0
            L_0x002b:
                r3 = move-exception
                monitor-exit(r2)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: android.telephony.ims.compat.stub.ImsConfigImplBase.ImsConfigStub.getProvisionedStringValue(int):java.lang.String");
        }

        public synchronized int setProvisionedValue(int item, int value) throws RemoteException {
            int retVal;
            this.mProvisionedIntValue.remove(Integer.valueOf(item));
            retVal = getImsConfigImpl().setProvisionedValue(item, value);
            if (retVal == 0) {
                updateCachedValue(item, value, true);
            } else {
                Log.d(ImsConfigImplBase.TAG, "Set provision value of " + item + " to " + value + " failed with error code " + retVal);
            }
            return retVal;
        }

        public synchronized int setProvisionedStringValue(int item, String value) throws RemoteException {
            int retVal;
            this.mProvisionedStringValue.remove(Integer.valueOf(item));
            retVal = getImsConfigImpl().setProvisionedStringValue(item, value);
            if (retVal == 0) {
                updateCachedValue(item, value, true);
            }
            return retVal;
        }

        public void getFeatureValue(int feature, int network, ImsConfigListener listener) throws RemoteException {
            getImsConfigImpl().getFeatureValue(feature, network, listener);
        }

        public void setFeatureValue(int feature, int network, int value, ImsConfigListener listener) throws RemoteException {
            getImsConfigImpl().setFeatureValue(feature, network, value, listener);
        }

        public boolean getVolteProvisioned() throws RemoteException {
            return getImsConfigImpl().getVolteProvisioned();
        }

        public void getVideoQuality(ImsConfigListener listener) throws RemoteException {
            getImsConfigImpl().getVideoQuality(listener);
        }

        public void setVideoQuality(int quality, ImsConfigListener listener) throws RemoteException {
            getImsConfigImpl().setVideoQuality(quality, listener);
        }

        private ImsConfigImplBase getImsConfigImpl() throws RemoteException {
            ImsConfigImplBase ref = (ImsConfigImplBase) this.mImsConfigImplBaseWeakReference.get();
            if (ref != null) {
                return ref;
            }
            throw new RemoteException("Fail to get ImsConfigImpl");
        }

        private void sendImsConfigChangedIntent(int item, int value) {
            sendImsConfigChangedIntent(item, Integer.toString(value));
        }

        private void sendImsConfigChangedIntent(int item, String value) {
            Intent configChangedIntent = new Intent(ImsConfig.ACTION_IMS_CONFIG_CHANGED);
            configChangedIntent.putExtra(ImsConfig.EXTRA_CHANGED_ITEM, item);
            configChangedIntent.putExtra("value", value);
            if (this.mContext != null) {
                this.mContext.sendBroadcast(configChangedIntent);
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void updateCachedValue(int item, int value, boolean notifyChange) {
            this.mProvisionedIntValue.put(Integer.valueOf(item), Integer.valueOf(value));
            if (notifyChange) {
                sendImsConfigChangedIntent(item, value);
            }
        }

        /* access modifiers changed from: protected */
        public synchronized void updateCachedValue(int item, String value, boolean notifyChange) {
            this.mProvisionedStringValue.put(Integer.valueOf(item), value);
            if (notifyChange) {
                sendImsConfigChangedIntent(item, value);
            }
        }
    }
}
