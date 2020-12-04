package android.hardware.hdmi;

import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiControlManager;
import android.hardware.hdmi.HdmiSwitchClient;
import android.hardware.hdmi.IHdmiControlCallback;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
public class HdmiSwitchClient extends HdmiClient {
    private static final String TAG = "HdmiSwitchClient";

    @SystemApi
    public interface OnSelectListener {
        void onSelect(@HdmiControlManager.ControlCallbackResult int i);
    }

    HdmiSwitchClient(IHdmiControlService service) {
        super(service);
    }

    private static IHdmiControlCallback getCallbackWrapper(final OnSelectListener listener) {
        return new IHdmiControlCallback.Stub() {
            public void onComplete(int result) {
                OnSelectListener.this.onSelect(result);
            }
        };
    }

    public int getDeviceType() {
        return 6;
    }

    public void selectDevice(int logicalAddress, OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.deviceSelect(logicalAddress, getCallbackWrapper(listener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void selectPort(int portId, OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.portSelect(portId, getCallbackWrapper(listener));
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void selectDevice(int logicalAddress, final Executor executor, final OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.deviceSelect(logicalAddress, new IHdmiControlCallback.Stub() {
                public void onComplete(int result) {
                    Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, listener, result) {
                        private final /* synthetic */ Executor f$0;
                        private final /* synthetic */ HdmiSwitchClient.OnSelectListener f$1;
                        private final /* synthetic */ int f$2;

                        {
                            this.f$0 = r1;
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void runOrThrow() {
                            this.f$0.execute(new Runnable(this.f$2) {
                                private final /* synthetic */ int f$1;

                                {
                                    this.f$1 = r2;
                                }

                                public final void run() {
                                    HdmiSwitchClient.OnSelectListener.this.onSelect(this.f$1);
                                }
                            });
                        }
                    });
                }
            });
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select device: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void selectPort(int portId, final Executor executor, final OnSelectListener listener) {
        Preconditions.checkNotNull(listener);
        try {
            this.mService.portSelect(portId, new IHdmiControlCallback.Stub() {
                public void onComplete(int result) {
                    Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, listener, result) {
                        private final /* synthetic */ Executor f$0;
                        private final /* synthetic */ HdmiSwitchClient.OnSelectListener f$1;
                        private final /* synthetic */ int f$2;

                        {
                            this.f$0 = r1;
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void runOrThrow() {
                            this.f$0.execute(new Runnable(this.f$2) {
                                private final /* synthetic */ int f$1;

                                {
                                    this.f$1 = r2;
                                }

                                public final void run() {
                                    HdmiSwitchClient.OnSelectListener.this.onSelect(this.f$1);
                                }
                            });
                        }
                    });
                }
            });
        } catch (RemoteException e) {
            Log.e(TAG, "failed to select port: ", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public List<HdmiDeviceInfo> getDeviceList() {
        try {
            return this.mService.getDeviceList();
        } catch (RemoteException e) {
            Log.e("TAG", "Failed to call getDeviceList():", e);
            return Collections.emptyList();
        }
    }
}
