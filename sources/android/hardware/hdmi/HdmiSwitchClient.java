package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiSwitchClient;
import android.hardware.hdmi.IHdmiControlCallback;
import android.p007os.Binder;
import android.p007os.RemoteException;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public class HdmiSwitchClient extends HdmiClient {
    private static final String TAG = "HdmiSwitchClient";

    @SystemApi
    /* loaded from: classes.dex */
    public interface OnSelectListener {
        void onSelect(@HdmiControlManager.ControlCallbackResult int i);
    }

    HdmiSwitchClient(IHdmiControlService service) {
        super(service);
    }

    private static IHdmiControlCallback getCallbackWrapper(final OnSelectListener listener) {
        return new IHdmiControlCallback.Stub() { // from class: android.hardware.hdmi.HdmiSwitchClient.1
            @Override // android.hardware.hdmi.IHdmiControlCallback
            public void onComplete(int result) {
                OnSelectListener.this.onSelect(result);
            }
        };
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 6;
    }

    public void selectDevice(int logicalAddress, OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.deviceSelect(logicalAddress, getCallbackWrapper(listener));
        } catch (RemoteException e) {
            Log.m69e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void selectPort(int portId, OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.portSelect(portId, getCallbackWrapper(listener));
        } catch (RemoteException e) {
            Log.m69e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void selectDevice(int logicalAddress, Executor executor, OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.deviceSelect(logicalAddress, new BinderC08892(executor, listener));
        } catch (RemoteException e) {
            Log.m69e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiSwitchClient$2 */
    /* loaded from: classes.dex */
    class BinderC08892 extends IHdmiControlCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnSelectListener val$listener;

        BinderC08892(Executor executor, OnSelectListener onSelectListener) {
            this.val$executor = executor;
            this.val$listener = onSelectListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int result) {
            final Executor executor = this.val$executor;
            final OnSelectListener onSelectListener = this.val$listener;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.-$$Lambda$HdmiSwitchClient$2$knvX6ZgANoRRFcb_fUHlUdWIjCQ
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.hardware.hdmi.-$$Lambda$HdmiSwitchClient$2$wYF9AcLTW87bh8nh0L1O42--jdg
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdmiSwitchClient.OnSelectListener.this.onSelect(r2);
                        }
                    });
                }
            });
        }
    }

    @SystemApi
    public void selectPort(int portId, Executor executor, OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.portSelect(portId, new BinderC08903(executor, listener));
        } catch (RemoteException e) {
            Log.m69e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiSwitchClient$3 */
    /* loaded from: classes.dex */
    class BinderC08903 extends IHdmiControlCallback.Stub {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ OnSelectListener val$listener;

        BinderC08903(Executor executor, OnSelectListener onSelectListener) {
            this.val$executor = executor;
            this.val$listener = onSelectListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlCallback
        public void onComplete(final int result) {
            final Executor executor = this.val$executor;
            final OnSelectListener onSelectListener = this.val$listener;
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.hardware.hdmi.-$$Lambda$HdmiSwitchClient$3$Cqxvec1NmkC6VlEdX5OEOabobXY
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new Runnable() { // from class: android.hardware.hdmi.-$$Lambda$HdmiSwitchClient$3$apecUZ8P9DH90drOKNmw2Y8Fspg
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdmiSwitchClient.OnSelectListener.this.onSelect(r2);
                        }
                    });
                }
            });
        }
    }

    public List<HdmiDeviceInfo> getDeviceList() {
        try {
            return this.mService.getDeviceList();
        } catch (RemoteException e) {
            Log.m69e("TAG", "Failed to call getDeviceList():", e);
            return Collections.emptyList();
        }
    }
}
