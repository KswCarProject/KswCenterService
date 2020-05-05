package com.wits.pms.custom;

import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import com.wits.pms.IContentObserver;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;
import com.wits.pms.statuscontrol.WitsCommand;
import com.wits.pms.utils.SystemProperties;

public class KswStatusHandler extends LogicSystem {
    private static final String TAG = "KswStatusHandler";
    private static final int TEST = 10000;
    private final int ACC = 500;
    private final int CCD = 600;
    private final int EPB = 711;
    private final int ILL = WitsCommand.SystemCommand.MCU_UPDATE;
    private final int POWER = WitsCommand.SystemCommand.OUT_MODE;
    private final int RLIGHT = WitsCommand.SystemCommand.CAR_MODE;
    private final int TRIPE = WitsCommand.SystemCommand.ANDROID_MODE;
    private final Context mContext = PowerManagerAppService.serviceContext;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler();
    private boolean statusChange;

    public KswStatusHandler() {
        initCustomObs();
    }

    private void initCustomObs() {
        PowerManagerApp.registerIContentObserver("ccd", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                boolean usingCall = true;
                if (PowerManagerApp.getStatusInt("ccd") == 1) {
                    int callStatus = KswStatusHandler.this.mBtPhoneStatus.callStatus;
                    if (callStatus >= 7 || callStatus < 4) {
                        usingCall = false;
                    }
                    if (usingCall) {
                        CenterControlImpl.getImpl().switchSoundToCar();
                    }
                }
            }
        });
        PowerManagerApp.registerIContentObserver("callStatus", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                if (KswStatusHandler.this.mBtPhoneStatus != null) {
                    int callStatus = PowerManagerApp.getStatusInt("callStatus");
                    switch (callStatus) {
                        case 4:
                            KswStatusHandler.this.mHandler.postDelayed(KswStatusHandler$2$$Lambda$0.$instance, 550);
                            CenterControlImpl.getImpl().handupEcarPhone();
                            break;
                        case 5:
                            KswMcuSender.getSender().sendMessage(99, new byte[]{1, 2});
                            CenterControlImpl.getImpl().handupEcarPhone();
                            break;
                        case 6:
                            KswMcuSender.getSender().sendMessage(99, new byte[]{1, 1});
                            break;
                        case 7:
                            KswMcuSender.getSender().sendMessage(99, new byte[]{1, 0});
                            break;
                    }
                    if (BtPhoneStatus.isCalling(callStatus) && PowerManagerApp.getStatusInt("screenSwitch") == 0) {
                        CenterControlImpl.getImpl().displayScreen(true);
                    }
                }
            }
        });
        PowerManagerApp.registerIContentObserver("systemMode", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                if (PowerManagerApp.getStatusInt("systemMode") == 2) {
                    if (PowerManagerApp.getSettingsInt("Support_TXZ") != 0) {
                        CenterControlImpl.getImpl().setTxzSleep(true);
                    }
                } else if (PowerManagerApp.getStatusInt("systemMode") == 1) {
                    int oldStatus = PowerManagerApp.getSettingsInt("Support_TXZ");
                    String zlink = SystemProperties.get(ZlinkMessage.ZLINK_CONNECT);
                    String autoKit = SystemProperties.get(AutoKitMessage.AUTOBOX_CONNECT);
                    if (oldStatus != 0 && !BtPhoneStatus.isCalling(PowerManagerApp.getStatusInt("callStatus")) && !zlink.equals("1") && !autoKit.equals("1")) {
                        CenterControlImpl.getImpl().setTxzSleep(false);
                    }
                }
            }
        });
    }

    public void handle() {
    }
}
