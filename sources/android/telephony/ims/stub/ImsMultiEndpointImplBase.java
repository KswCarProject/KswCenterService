package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.p007os.RemoteException;
import android.telephony.ims.ImsExternalCallState;
import android.util.Log;
import com.android.ims.internal.IImsExternalCallStateListener;
import com.android.ims.internal.IImsMultiEndpoint;
import java.util.List;

@SystemApi
/* loaded from: classes4.dex */
public class ImsMultiEndpointImplBase {
    private static final String TAG = "MultiEndpointImplBase";
    private IImsMultiEndpoint mImsMultiEndpoint = new IImsMultiEndpoint.Stub() { // from class: android.telephony.ims.stub.ImsMultiEndpointImplBase.1
        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void setListener(IImsExternalCallStateListener listener) throws RemoteException {
            ImsMultiEndpointImplBase.this.mListener = listener;
        }

        @Override // com.android.ims.internal.IImsMultiEndpoint
        public void requestImsExternalCallStateInfo() throws RemoteException {
            ImsMultiEndpointImplBase.this.requestImsExternalCallStateInfo();
        }
    };
    private IImsExternalCallStateListener mListener;

    public IImsMultiEndpoint getIImsMultiEndpoint() {
        return this.mImsMultiEndpoint;
    }

    public final void onImsExternalCallStateUpdate(List<ImsExternalCallState> externalCallDialogs) {
        Log.m72d(TAG, "ims external call state update triggered.");
        if (this.mListener != null) {
            try {
                this.mListener.onImsExternalCallStateUpdate(externalCallDialogs);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void requestImsExternalCallStateInfo() {
        Log.m72d(TAG, "requestImsExternalCallStateInfo() not implemented");
    }
}
