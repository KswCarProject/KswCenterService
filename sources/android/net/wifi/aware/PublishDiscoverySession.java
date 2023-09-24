package android.net.wifi.aware;

import android.util.Log;

/* loaded from: classes3.dex */
public class PublishDiscoverySession extends DiscoverySession {
    private static final String TAG = "PublishDiscoverySession";

    public PublishDiscoverySession(WifiAwareManager manager, int clientId, int sessionId) {
        super(manager, clientId, sessionId);
    }

    public void updatePublish(PublishConfig publishConfig) {
        if (this.mTerminated) {
            Log.m64w(TAG, "updatePublish: called on terminated session");
            return;
        }
        WifiAwareManager mgr = this.mMgr.get();
        if (mgr == null) {
            Log.m64w(TAG, "updatePublish: called post GC on WifiAwareManager");
        } else {
            mgr.updatePublish(this.mClientId, this.mSessionId, publishConfig);
        }
    }
}
