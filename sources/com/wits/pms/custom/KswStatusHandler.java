package com.wits.pms.custom;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.logging.nano.MetricsProto;
import com.wits.pms.IContentObserver;
import com.wits.pms.bean.AutoKitMessage;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.interfaces.LogicSystem;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.mirror.WifiManagerMirror;
import com.wits.pms.statuscontrol.BtPhoneStatus;
import com.wits.pms.statuscontrol.PowerManagerApp;

public class KswStatusHandler extends LogicSystem {
    private static final String TAG = "KswStatusHandler";
    private static final int TEST = 10000;
    private final int ACC = 500;
    private final int CCD = 600;
    private final int EPB = MetricsProto.MetricsEvent.ACTION_PERMISSION_GRANT_RECEIVE_SMS;
    private final int ILL = 700;
    private final int POWER = 603;
    private final int RLIGHT = 601;
    private final int TRIPE = 602;
    /* access modifiers changed from: private */
    public final Context mContext = PowerManagerAppService.serviceContext;
    /* access modifiers changed from: private */
    public final Handler mHandler = new Handler();
    private boolean statusChange;

    public KswStatusHandler() {
        initCustomObs();
    }

    private void initCustomObs() {
        PowerManagerApp.registerIContentObserver("ccd", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                boolean usingCall = false;
                boolean revers = PowerManagerApp.getStatusInt("ccd") == 1;
                if (revers) {
                    int callStatus = KswStatusHandler.this.mBtPhoneStatus.callStatus;
                    if (callStatus < 7 && callStatus >= 4) {
                        usingCall = true;
                    }
                    if (usingCall) {
                        CenterControlImpl.getImpl().switchSoundToCar();
                    }
                }
                CenterControlImpl.getImpl().setTxzQuickQuit(revers);
            }
        });
        PowerManagerApp.registerIContentObserver("callStatus", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                if (KswStatusHandler.this.mBtPhoneStatus != null) {
                    int callStatus = PowerManagerApp.getStatusInt("callStatus");
                    switch (callStatus) {
                        case 4:
                            KswStatusHandler.this.mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    try {
                                        if (PowerManagerApp.getStatusInt("callStatus") == 4) {
                                            KswMcuSender.getSender().sendMessage(99, new byte[]{1, 3});
                                        }
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 550);
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
        PowerManagerApp.registerIContentObserver("acc", new IContentObserver.Stub() {
            public void onChange() throws RemoteException {
                int acc = PowerManagerApp.getStatusInt("acc");
                Log.d(KswStatusHandler.TAG, "onChange   acc = " + acc);
                if (acc == 1) {
                    Intent accIntent = new Intent("com.wits.ksw.ACC_ON");
                    accIntent.addFlags(16777216);
                    KswStatusHandler.this.mContext.sendBroadcastAsUser(accIntent, UserHandle.getUserHandleForUid(KswStatusHandler.this.mContext.getApplicationInfo().uid));
                    CallBackServiceImpl.getCallBackServiceImpl().handleAcc(true);
                } else if (acc == 0) {
                    Intent accIntent2 = new Intent("com.wits.ksw.ACC_OFF");
                    accIntent2.addFlags(16777216);
                    KswStatusHandler.this.mContext.sendBroadcastAsUser(accIntent2, UserHandle.getUserHandleForUid(KswStatusHandler.this.mContext.getApplicationInfo().uid));
                    int apState = new WifiManagerMirror((WifiManager) KswStatusHandler.this.mContext.getSystemService("wifi")).getWifiApState();
                    Log.d(KswStatusHandler.TAG, "onChange  apState  = " + apState);
                    if (apState == 12 || apState == 13) {
                        Settings.System.putInt(KswStatusHandler.this.mContext.getContentResolver(), "hotspot_open", 1);
                    } else {
                        Settings.System.putInt(KswStatusHandler.this.mContext.getContentResolver(), "hotspot_open", 0);
                    }
                    CallBackServiceImpl.getCallBackServiceImpl().handleAcc(false);
                }
            }
        });
    }

    public void handle() {
    }
}
