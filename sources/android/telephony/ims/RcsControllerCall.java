package android.telephony.ims;

import android.content.Context;
import android.p007os.RemoteException;
import android.p007os.ServiceManager;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.aidl.IRcs;

/* loaded from: classes4.dex */
class RcsControllerCall {
    private final Context mContext;

    /* loaded from: classes4.dex */
    interface RcsServiceCall<R> {
        R methodOnIRcs(IRcs iRcs, String str) throws RemoteException;
    }

    /* loaded from: classes4.dex */
    interface RcsServiceCallWithNoReturn {
        void methodOnIRcs(IRcs iRcs, String str) throws RemoteException;
    }

    RcsControllerCall(Context context) {
        this.mContext = context;
    }

    <R> R call(RcsServiceCall<R> serviceCall) throws RcsMessageStoreException {
        IRcs iRcs = IRcs.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_RCS_SERVICE));
        if (iRcs == null) {
            throw new RcsMessageStoreException("Could not connect to RCS storage service");
        }
        try {
            return serviceCall.methodOnIRcs(iRcs, this.mContext.getOpPackageName());
        } catch (RemoteException exception) {
            throw new RcsMessageStoreException(exception.getMessage());
        }
    }

    void callWithNoReturn(final RcsServiceCallWithNoReturn serviceCall) throws RcsMessageStoreException {
        call(new RcsServiceCall() { // from class: android.telephony.ims.-$$Lambda$RcsControllerCall$lqKvRobLziMoZre7XkbJkfc5LEM
            @Override // android.telephony.ims.RcsControllerCall.RcsServiceCall
            public final Object methodOnIRcs(IRcs iRcs, String str) {
                return RcsControllerCall.lambda$callWithNoReturn$0(RcsControllerCall.RcsServiceCallWithNoReturn.this, iRcs, str);
            }
        });
    }

    static /* synthetic */ Object lambda$callWithNoReturn$0(RcsServiceCallWithNoReturn serviceCall, IRcs iRcs, String callingPackage) throws RemoteException {
        serviceCall.methodOnIRcs(iRcs, callingPackage);
        return null;
    }
}
