package com.wits.pms.custom;

import android.content.ComponentName;
import android.content.Intent;
import android.p007os.RemoteException;
import android.util.Log;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.ICallBackController;
import com.wits.pms.statuscontrol.McuStatus;

/* loaded from: classes2.dex */
public class CallBackServiceImpl extends ICallBackController.Stub {
    private static String TAG = CallBackServiceImpl.class.getSimpleName();
    private static CallBackServiceImpl mCallBackServiceImpl;
    ICallBack iCallBack;

    public static CallBackServiceImpl getCallBackServiceImpl() {
        if (mCallBackServiceImpl == null) {
            mCallBackServiceImpl = new CallBackServiceImpl();
        }
        return mCallBackServiceImpl;
    }

    @Override // com.wits.pms.custom.ICallBackController
    public void setICallBack(ICallBack iCallBack) throws RemoteException {
        this.iCallBack = iCallBack;
    }

    @Override // com.wits.pms.custom.ICallBackController
    public ICallBack getICallBack() throws RemoteException {
        return this.iCallBack;
    }

    public void handleReverse() {
        String str = TAG;
        Log.m72d(str, "handleReverse  iCallBack :" + this.iCallBack + "   ccd = " + SystemStatusControl.getStatus().ccd);
        start360();
        if (this.iCallBack != null) {
            try {
                this.iCallBack.onReverse(SystemStatusControl.getStatus().ccd);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleLRReverse() {
        String str = TAG;
        Log.m72d(str, "handleLRReverse  iCallBack :" + this.iCallBack);
        if (this.iCallBack != null) {
            try {
                McuStatus mcuStatus = SystemStatusControl.getDefault().getMcuStatus();
                this.iCallBack.onLRReverse((mcuStatus.carData.getSignalRight() >> 4) + ((mcuStatus.carData.getSignalLeft() >> 3) * 2));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleRRadar(int var1, int var2, int var3, int var4, int var5) {
        if (this.iCallBack != null) {
            try {
                this.iCallBack.onRRadar(var1, var2, var3, var4, var5);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleFRadar(int var1, int var2, int var3, int var4, int var5) {
        if (this.iCallBack != null) {
            try {
                this.iCallBack.onFRadar(var1, var2, var3, var4, var5);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleAngle(int angle) {
        if (this.iCallBack != null) {
            try {
                this.iCallBack.onAngle(angle);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDoor(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6) {
        if (this.iCallBack != null) {
            try {
                this.iCallBack.onDoor(var1, var2, var3, var4, var5, var6);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleAcc(boolean isAcc) {
        String str = TAG;
        Log.m72d(str, "handleAcc  iCallBack :" + this.iCallBack);
        if (this.iCallBack != null) {
            try {
                this.iCallBack.onAcc(isAcc);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void start360() {
        Log.m72d(TAG, "start360");
        Intent tempIntent = new Intent();
        tempIntent.setComponent(new ComponentName("com.baony.avm360", "com.baony.ui.service.AVMCanBusService"));
        PowerManagerAppService.serviceContext.startService(tempIntent);
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
