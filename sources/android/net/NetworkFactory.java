package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AsyncChannel;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkFactory extends Handler {
    private static final int BASE = 536576;
    public static final int CMD_CANCEL_REQUEST = 536577;
    public static final int CMD_REQUEST_NETWORK = 536576;
    private static final int CMD_SET_FILTER = 536579;
    private static final int CMD_SET_SCORE = 536578;
    private static final boolean DBG = true;
    public static final int EVENT_UNFULFILLABLE_REQUEST = 536580;
    private static final boolean VDBG = false;
    private final String LOG_TAG;
    private AsyncChannel mAsyncChannel;
    private NetworkCapabilities mCapabilityFilter;
    private final Context mContext;
    private Messenger mMessenger = null;
    private final SparseArray<NetworkRequestInfo> mNetworkRequests = new SparseArray<>();
    private final ArrayList<Message> mPreConnectedQueue = new ArrayList<>();
    private int mRefCount = 0;
    private int mScore;
    private int mSerialNumber;

    public static class SerialNumber {
        public static final int NONE = -1;
        public static final int VPN = -2;
        private static final AtomicInteger sNetworkFactorySerialNumber = new AtomicInteger(1);

        public static final int nextSerialNumber() {
            return sNetworkFactorySerialNumber.getAndIncrement();
        }
    }

    @UnsupportedAppUsage
    public NetworkFactory(Looper looper, Context context, String logTag, NetworkCapabilities filter) {
        super(looper);
        this.LOG_TAG = logTag;
        this.mContext = context;
        this.mCapabilityFilter = filter;
    }

    public void register() {
        log("Registering NetworkFactory");
        if (this.mMessenger == null) {
            this.mMessenger = new Messenger((Handler) this);
            this.mSerialNumber = ConnectivityManager.from(this.mContext).registerNetworkFactory(this.mMessenger, this.LOG_TAG);
        }
    }

    public void unregister() {
        log("Unregistering NetworkFactory");
        if (this.mMessenger != null) {
            ConnectivityManager.from(this.mContext).unregisterNetworkFactory(this.mMessenger);
            this.mMessenger = null;
        }
    }

    public void handleMessage(Message msg) {
        int i = msg.what;
        if (i != 69633) {
            switch (i) {
                case AsyncChannel.CMD_CHANNEL_DISCONNECT /*69635*/:
                    if (this.mAsyncChannel != null) {
                        this.mAsyncChannel.disconnect();
                        this.mAsyncChannel = null;
                        return;
                    }
                    return;
                case AsyncChannel.CMD_CHANNEL_DISCONNECTED /*69636*/:
                    log("NetworkFactory channel lost");
                    this.mAsyncChannel = null;
                    return;
                default:
                    switch (i) {
                        case 536576:
                            handleAddRequest((NetworkRequest) msg.obj, msg.arg1, msg.arg2);
                            return;
                        case CMD_CANCEL_REQUEST /*536577*/:
                            handleRemoveRequest((NetworkRequest) msg.obj);
                            return;
                        case CMD_SET_SCORE /*536578*/:
                            handleSetScore(msg.arg1);
                            return;
                        case CMD_SET_FILTER /*536579*/:
                            handleSetFilter((NetworkCapabilities) msg.obj);
                            return;
                        default:
                            return;
                    }
            }
        } else if (this.mAsyncChannel != null) {
            log("Received new connection while already connected!");
        } else {
            AsyncChannel ac = new AsyncChannel();
            ac.connected((Context) null, this, msg.replyTo);
            ac.replyToMessage(msg, (int) AsyncChannel.CMD_CHANNEL_FULLY_CONNECTED, 0);
            this.mAsyncChannel = ac;
            Iterator<Message> it = this.mPreConnectedQueue.iterator();
            while (it.hasNext()) {
                ac.sendMessage(it.next());
            }
            this.mPreConnectedQueue.clear();
        }
    }

    private class NetworkRequestInfo {
        public int factorySerialNumber;
        public final NetworkRequest request;
        public boolean requested = false;
        public int score;

        NetworkRequestInfo(NetworkRequest request2, int score2, int factorySerialNumber2) {
            this.request = request2;
            this.score = score2;
            this.factorySerialNumber = factorySerialNumber2;
        }

        public String toString() {
            return "{" + this.request + ", score=" + this.score + ", requested=" + this.requested + "}";
        }
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public void handleAddRequest(NetworkRequest request, int score) {
        handleAddRequest(request, score, -1);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public void handleAddRequest(NetworkRequest request, int score, int servingFactorySerialNumber) {
        NetworkRequestInfo n = this.mNetworkRequests.get(request.requestId);
        if (n == null) {
            log("got request " + request + " with score " + score + " and serial " + servingFactorySerialNumber);
            n = new NetworkRequestInfo(request, score, servingFactorySerialNumber);
            this.mNetworkRequests.put(n.request.requestId, n);
        } else {
            n.score = score;
            n.factorySerialNumber = servingFactorySerialNumber;
        }
        evalRequest(n);
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public void handleRemoveRequest(NetworkRequest request) {
        NetworkRequestInfo n = this.mNetworkRequests.get(request.requestId);
        if (n != null) {
            this.mNetworkRequests.remove(request.requestId);
            if (n.requested) {
                releaseNetworkFor(n.request);
            }
        }
    }

    private void handleSetScore(int score) {
        this.mScore = score;
        evalRequests();
    }

    private void handleSetFilter(NetworkCapabilities netCap) {
        this.mCapabilityFilter = netCap;
        evalRequests();
    }

    public boolean acceptRequest(NetworkRequest request, int score) {
        return true;
    }

    private void evalRequest(NetworkRequestInfo n) {
        if (shouldNeedNetworkFor(n)) {
            needNetworkFor(n.request, n.score);
            n.requested = true;
        } else if (shouldReleaseNetworkFor(n)) {
            releaseNetworkFor(n.request);
            n.requested = false;
        }
    }

    private boolean shouldNeedNetworkFor(NetworkRequestInfo n) {
        return !n.requested && (n.score < this.mScore || n.factorySerialNumber == this.mSerialNumber) && n.request.networkCapabilities.satisfiedByNetworkCapabilities(this.mCapabilityFilter) && acceptRequest(n.request, n.score);
    }

    private boolean shouldReleaseNetworkFor(NetworkRequestInfo n) {
        return n.requested && ((n.score > this.mScore && n.factorySerialNumber != this.mSerialNumber) || !n.request.networkCapabilities.satisfiedByNetworkCapabilities(this.mCapabilityFilter) || !acceptRequest(n.request, n.score));
    }

    /* access modifiers changed from: private */
    public void evalRequests() {
        for (int i = 0; i < this.mNetworkRequests.size(); i++) {
            evalRequest(this.mNetworkRequests.valueAt(i));
        }
    }

    /* access modifiers changed from: protected */
    public void reevaluateAllRequests() {
        post(new Runnable() {
            public final void run() {
                NetworkFactory.this.evalRequests();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void releaseRequestAsUnfulfillableByAnyFactory(NetworkRequest r) {
        post(new Runnable(r) {
            private final /* synthetic */ NetworkRequest f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                NetworkFactory.lambda$releaseRequestAsUnfulfillableByAnyFactory$1(NetworkFactory.this, this.f$1);
            }
        });
    }

    public static /* synthetic */ void lambda$releaseRequestAsUnfulfillableByAnyFactory$1(NetworkFactory networkFactory, NetworkRequest r) {
        networkFactory.log("releaseRequestAsUnfulfillableByAnyFactory: " + r);
        Message msg = networkFactory.obtainMessage(EVENT_UNFULFILLABLE_REQUEST, r);
        if (networkFactory.mAsyncChannel != null) {
            networkFactory.mAsyncChannel.sendMessage(msg);
        } else {
            networkFactory.mPreConnectedQueue.add(msg);
        }
    }

    /* access modifiers changed from: protected */
    public void startNetwork() {
    }

    /* access modifiers changed from: protected */
    public void stopNetwork() {
    }

    /* access modifiers changed from: protected */
    public void needNetworkFor(NetworkRequest networkRequest, int score) {
        int i = this.mRefCount + 1;
        this.mRefCount = i;
        if (i == 1) {
            startNetwork();
        }
    }

    /* access modifiers changed from: protected */
    public void releaseNetworkFor(NetworkRequest networkRequest) {
        int i = this.mRefCount - 1;
        this.mRefCount = i;
        if (i == 0) {
            stopNetwork();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void setScoreFilter(int score) {
        sendMessage(obtainMessage(CMD_SET_SCORE, score, 0));
    }

    public void setCapabilityFilter(NetworkCapabilities netCap) {
        sendMessage(obtainMessage(CMD_SET_FILTER, new NetworkCapabilities(netCap)));
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public int getRequestCount() {
        return this.mNetworkRequests.size();
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    /* access modifiers changed from: protected */
    public void log(String s) {
        Log.d(this.LOG_TAG, s);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
        IndentingPrintWriter pw = new IndentingPrintWriter(writer, "  ");
        pw.println(toString());
        pw.increaseIndent();
        for (int i = 0; i < this.mNetworkRequests.size(); i++) {
            pw.println(this.mNetworkRequests.valueAt(i));
        }
        pw.decreaseIndent();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append(this.LOG_TAG);
        sb.append(" - mSerialNumber=");
        sb.append(this.mSerialNumber);
        sb.append(", ScoreFilter=");
        sb.append(this.mScore);
        sb.append(", Filter=");
        sb.append(this.mCapabilityFilter);
        sb.append(", requests=");
        sb.append(this.mNetworkRequests.size());
        sb.append(", refCount=");
        sb.append(this.mRefCount);
        return sb.append("}").toString();
    }
}
