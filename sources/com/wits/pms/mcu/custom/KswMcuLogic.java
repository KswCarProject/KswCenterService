package com.wits.pms.mcu.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObserver;
import android.net.wifi.WifiScanner;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.wits.pms.bean.ZlinkMessage;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.SystemStatusControl;
import com.wits.pms.custom.CallBackServiceImpl;
import com.wits.pms.mcu.McuService;
import com.wits.pms.mirror.PowerManagerMirror;
import com.wits.pms.mirror.SystemProperties;

/* loaded from: classes2.dex */
public class KswMcuLogic {
    private static final byte ANDROID_MODE = 1;
    private static final byte AUX = 6;
    private static final byte CAR_MODE = 2;
    private static final byte DTV = 9;
    private static final byte DVD = 8;
    private static final byte DVD_YUV = 12;
    private static final byte DVR = 5;
    private static final byte DV_TYPE_MODE = 0;
    private static final int EVENT_KILL_PROCESS = 5;
    private static final int EVENT_OPEN_LOGCAT = 4;
    private static final int EVENT_SHOW_A_SHORT_TOAST = 1;
    private static final int EVENT_START_LOGCAT = 2;
    private static final int EVENT_STOP_LOGCAT = 3;
    public static final String IAP_ERROR = "iaperror";
    private static final String TAG = "KswMcuLogic";
    private static boolean callingStopHeartBeat;
    private static boolean isNewPro;
    private static KswMcuLogic kswMcuLogic;
    private static boolean stopHeartBeat;
    private static boolean willCloseScreen;
    private boolean isReversing;
    private boolean isTurnOnBrightness;
    private boolean isUpdating;
    private CarCanMsgHandle mCarCanMsgHandle;
    private boolean mCloseScreen;
    private Context mContext;
    private int mCurrentTouchX;
    private int mCurrentTouchY;
    private Handler mHandler;
    private View mInterceptView;
    private long mLastEventTime;
    private int mLastTouchX;
    private int mLastTouchY;
    private long mLongPressDownTime;
    private final WindowManager mWindowManager;
    private boolean wasAdded;
    private boolean DEBUG = true;
    private byte mCurrentStatus = 0;
    private final KswMcuListener mListener = new KswMcuListener();

    /* loaded from: classes2.dex */
    public static class McuToArm {
        public static final int CMD_ATMOSPHERE_LIGHT_CONTROL = 25;
        public static final int CMD_CAN_MSG_RECV = 161;
        public static final int CMD_CAR_CONTROL_STATUS_INFO = 17;
        public static final int CMD_CAR_SYSTEM_SETTINGS = 19;
        public static final int CMD_DVD_SOURCE_CONTROL = 22;
        public static final int CMD_FACTORY_SETTINGS = 23;
        public static final int CMD_MCU_SERIAL_NUMBER = 20;
        public static final int CMD_MCU_VERSION_INFO = 18;
        public static final int CMD_MEDIA_CONTROL = 21;
        public static final int CMD_POWER_STATUS = 16;
        public static final int CMD_TOUCH_SYSTEM_SWITCH = 26;
        public static final int CMD_VIDEO_STATUS = 28;
    }

    public KswMcuLogic(Context context) {
        this.mContext = null;
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        this.mWindowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        this.mCarCanMsgHandle = new CarCanMsgHandle(context);
        McuService.setListener(this.mListener);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("isCalling"), true, new ContentObserver(this.mHandler) { // from class: com.wits.pms.mcu.custom.KswMcuLogic.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                boolean unused = KswMcuLogic.callingStopHeartBeat = Settings.System.getInt(KswMcuLogic.this.mContext.getContentResolver(), "isCalling", 0) == 1;
            }
        });
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("isZlinkCalling"), true, new ContentObserver(this.mHandler) { // from class: com.wits.pms.mcu.custom.KswMcuLogic.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                boolean unused = KswMcuLogic.callingStopHeartBeat = Settings.System.getInt(KswMcuLogic.this.mContext.getContentResolver(), "isZlinkCalling", 0) == 1;
            }
        });
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("touch_continuous_send"), true, new ContentObserver(this.mHandler) { // from class: com.wits.pms.mcu.custom.KswMcuLogic.3
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                boolean unused = KswMcuLogic.isNewPro = Settings.System.getInt(KswMcuLogic.this.mContext.getContentResolver(), "touch_continuous_send", 0) == 1;
            }
        });
        heartBeatData();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.mcu.custom.KswMcuLogic$4] */
    private void heartBeatData() {
        new Thread() { // from class: com.wits.pms.mcu.custom.KswMcuLogic.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                while (true) {
                    try {
                        if (!KswMcuLogic.stopHeartBeat && !KswMcuLogic.callingStopHeartBeat) {
                            KswMcuLogic.this.send(KswMessage.obtain(104, new byte[]{8, 0}));
                        }
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    public KswMcuListener getListener() {
        return this.mListener;
    }

    public void testMcuMessage(int cmdType, byte[] data) {
        handleMessage(KswMessage.obtain(cmdType, data));
    }

    private void setScreenLightOn(boolean on) {
        try {
            this.mCloseScreen = false;
            KswMcuSender.getSender().sendMessage(108, new byte[]{2, on ? (byte) 1 : (byte) 0});
        } catch (Exception e) {
        }
    }

    public void powerOff() {
    }

    public void updateVideoStatus(byte status) {
        this.mCurrentStatus = status != 1 ? (byte) 2 : (byte) 1;
        updateInterceptView();
    }

    public void updateStatus(byte status) {
        SystemStatusControl.getDefault().getMcuStatus().systemMode = status;
        SystemStatusControl.getDefault().handle();
        this.mCurrentStatus = status;
        updateInterceptView();
    }

    private void updateInterceptView() {
        if (this.isReversing) {
            opInterceptView(true, false);
        } else if (SystemStatusControl.getStatus().topApp.equals("com.wits.ksw") && SystemStatusControl.getStatus().topClass.equals("com.wits.ksw.launcher.view.lexus.OEMFMActivity")) {
            opInterceptView(false, false);
        } else {
            switch (this.mCurrentStatus) {
                case 1:
                    opInterceptView(false, false);
                    return;
                case 2:
                    opInterceptView(true, false);
                    return;
                default:
                    opInterceptView(false, false);
                    return;
            }
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private synchronized void opInterceptView(final boolean intercept, boolean closeScreen) {
        if (this.mCloseScreen) {
            return;
        }
        this.mCloseScreen = closeScreen;
        this.mHandler.post(new Runnable() { // from class: com.wits.pms.mcu.custom.-$$Lambda$KswMcuLogic$__FC79HbfdJBlor_gaXb6waH9Wo
            @Override // java.lang.Runnable
            public final void run() {
                KswMcuLogic.lambda$opInterceptView$1(KswMcuLogic.this, intercept);
            }
        });
    }

    public static /* synthetic */ void lambda$opInterceptView$1(final KswMcuLogic kswMcuLogic2, boolean intercept) {
        boolean z = false;
        if (!intercept) {
            if (kswMcuLogic2.mInterceptView != null && kswMcuLogic2.wasAdded) {
                try {
                    Log.m70e(TAG, "-- Bug#11240/11693 -- removeViewImmediate --1--");
                    kswMcuLogic2.mWindowManager.removeViewImmediate(kswMcuLogic2.mInterceptView);
                    kswMcuLogic2.wasAdded = false;
                    kswMcuLogic2.mLongPressDownTime = 0L;
                } catch (Exception e) {
                    Log.m64w(TAG, "remove interceptView failed.");
                }
            }
        } else if (kswMcuLogic2.wasAdded) {
        } else {
            if (kswMcuLogic2.DEBUG) {
                Log.m66v(TAG, "opInterceptView to op other View");
            }
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.type = 2010;
            lp.flags = lp.flags | 1024 | 262144 | 524288;
            lp.height = -1;
            lp.width = -1;
            lp.format = 1;
            lp.alpha = 1.0f;
            lp.f2425x = 0;
            lp.f2426y = 0;
            kswMcuLogic2.mInterceptView = new View(kswMcuLogic2.mContext);
            kswMcuLogic2.mInterceptView.setClickable(true);
            kswMcuLogic2.mInterceptView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            if (Settings.System.getInt(kswMcuLogic2.mContext.getContentResolver(), "touch_continuous_send", 0) == 1) {
                z = true;
            }
            isNewPro = z;
            kswMcuLogic2.mInterceptView.setOnTouchListener(new View.OnTouchListener() { // from class: com.wits.pms.mcu.custom.-$$Lambda$KswMcuLogic$ZpUPJBptj6i8Z1G8W4FyOQi-KzE
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return KswMcuLogic.lambda$null$0(KswMcuLogic.this, view, motionEvent);
                }
            });
            try {
                if (kswMcuLogic2.wasAdded) {
                    Log.m70e(TAG, "-- Bug#11240/11693 -- removeViewImmediate --3--");
                    kswMcuLogic2.mWindowManager.removeViewImmediate(kswMcuLogic2.mInterceptView);
                }
                kswMcuLogic2.wasAdded = true;
                Log.m70e(TAG, "-- Bug#11240/11693 -- addView ");
                kswMcuLogic2.mWindowManager.addView(kswMcuLogic2.mInterceptView, lp);
            } catch (Exception e2) {
                Log.m64w(TAG, "remove interceptView failed.");
            }
        }
    }

    public static /* synthetic */ boolean lambda$null$0(KswMcuLogic kswMcuLogic2, View v, MotionEvent event) {
        if (kswMcuLogic2.mCloseScreen) {
            kswMcuLogic2.wasAdded = false;
            try {
                Log.m70e(TAG, "-- Bug#11240/11693 -- removeViewImmediate --2--");
                kswMcuLogic2.mWindowManager.removeViewImmediate(kswMcuLogic2.mInterceptView);
            } catch (Exception e) {
                Log.m64w(TAG, "remove interceptView failed.");
            }
            kswMcuLogic2.setScreenLightOn(true);
        } else if (!kswMcuLogic2.needSendTouchData() || !kswMcuLogic2.filter(event)) {
            return false;
        } else {
            kswMcuLogic2.mCurrentTouchX = (int) event.getX();
            kswMcuLogic2.mCurrentTouchY = kswMcuLogic2.getTouchY((int) event.getY());
            if (kswMcuLogic2.mCurrentTouchX < -5 || kswMcuLogic2.mCurrentTouchX > 4000 || kswMcuLogic2.mCurrentTouchY < -5 || kswMcuLogic2.mCurrentTouchY > 2000) {
                Log.m70e(TAG, "-- Bug#10725 -- INVALID TOUCH -- touchEvent is " + event);
                return false;
            }
            Log.m70e(TAG, "-- Bug#10725 -- touchEvent is " + event);
            if (isNewPro) {
                kswMcuLogic2.sendTouchDataA(event);
            } else {
                kswMcuLogic2.sendTouchDataB(event);
            }
            kswMcuLogic2.mLastTouchX = kswMcuLogic2.mCurrentTouchX;
            kswMcuLogic2.mLastTouchY = kswMcuLogic2.mCurrentTouchY;
            kswMcuLogic2.mLastEventTime = event.getEventTime();
            if (event.getAction() == 1) {
                kswMcuLogic2.mLastEventTime = 0L;
            }
        }
        return false;
    }

    private boolean filter(MotionEvent event) {
        return this.mLastEventTime == 0 || event.getEventTime() - this.mLastEventTime >= 25 || event.getAction() == 1;
    }

    private boolean isLongClick(MotionEvent event) {
        if (this.mLastEventTime == 0) {
            return false;
        }
        long downTime = event.getEventTime() - this.mLastEventTime;
        float x = this.mCurrentTouchX - this.mLastTouchX;
        float y = this.mCurrentTouchY - this.mLastTouchY;
        boolean longPress = x < 5.0f && x > -5.0f && y < 5.0f && y > -5.0f;
        if (event.getAction() == 1) {
            this.mLongPressDownTime = 0L;
        } else if (longPress) {
            this.mLongPressDownTime += downTime;
        }
        return this.mLongPressDownTime >= 1500;
    }

    private int getTouchY(int currentY) {
        boolean needAdd = SystemProperties.get("app.carMode.control").equals("0");
        int i = 0;
        boolean versionIsOld = Build.VERSION.RELEASE.equals("10") || Build.VERSION.RELEASE.equals("11");
        Log.m68i(TAG, "versionIsOld" + versionIsOld);
        if (needAdd && versionIsOld) {
            i = 62;
        }
        return i + currentY;
    }

    private void sendTouchDataA(MotionEvent event) {
        byte[] touchData = new byte[6];
        int i = 0;
        touchData[0] = 10;
        int x = this.mCurrentTouchX;
        int y = this.mCurrentTouchY;
        Log.m68i(TAG, "sendTouchDataA Touch X - " + this.mCurrentTouchX + " ; Y - " + this.mCurrentTouchY + "- event:" + event.toString());
        touchData[1] = (byte) (x >> 8);
        touchData[2] = (byte) x;
        touchData[3] = (byte) (y >> 8);
        touchData[4] = (byte) y;
        boolean isLongClicked = isLongClick(event);
        boolean isActionUp = event.getAction() == 1;
        if (isActionUp) {
            i = 2;
        } else if (isLongClicked) {
            i = 1;
        }
        touchData[5] = (byte) i;
        if (this.isUpdating) {
            return;
        }
        send(KswMessage.obtain(107, touchData));
    }

    private void sendTouchDataB(MotionEvent event) {
        byte[] touchData = new byte[6];
        touchData[0] = this.mCurrentStatus;
        int x = this.mCurrentTouchX;
        int y = this.mCurrentTouchY;
        Log.m68i(TAG, "sendTouchDataB Touch X - " + this.mCurrentTouchX + " ; Y - " + this.mCurrentTouchY + "- event:" + event.toString());
        touchData[1] = (byte) (x >> 8);
        touchData[2] = (byte) x;
        touchData[3] = (byte) (y >> 8);
        touchData[4] = (byte) y;
        if (event.getAction() != 0) {
            return;
        }
        boolean isDown = event.getAction() == 0;
        touchData[5] = (byte) (isDown ? 0 : 2);
        if (this.isUpdating) {
            return;
        }
        send(KswMessage.obtain(107, touchData));
    }

    private boolean needSendTouchData() {
        if (SystemProperties.get(ZlinkMessage.ZLINK_CALL).equals("1") && this.mCurrentStatus == 2) {
            return false;
        }
        return this.isReversing || this.mCurrentStatus == 2;
    }

    private void handleUpdateMessage(KswMessage message) {
        if (message != null) {
            Log.m68i("handleUpdateMessage", message.toString());
        }
        if (message.getCmdType() == 224) {
            if (message.getData()[0] == 1) {
                if (!this.isUpdating) {
                    opInterceptView(true, false);
                }
                this.isUpdating = true;
            } else if (message.getData()[0] == 0) {
                opInterceptView(false, false);
                this.isUpdating = false;
                stopHeartBeat = false;
            } else if (message.getData()[0] != 2 && message.getData()[0] != 3 && message.getData()[0] != 4 && (message.getData()[0] & 255) >= 241) {
                opInterceptView(false, false);
                this.isUpdating = false;
                stopHeartBeat = false;
            }
        } else if (message.getCmdType() != 225) {
            message.getCmdType();
        }
    }

    private boolean handleSendMessage(KswMessage msg) {
        boolean intercept = false;
        if (msg == null) {
            return false;
        }
        Log.m68i("McuSendMessage", msg.toString());
        if (this.isUpdating && msg.getDataType() != 160) {
            return false;
        }
        if (msg.getDataType() == 160) {
            Log.m70e(TAG, "sendMessage::update type");
            if (msg.getCmdType() == 232) {
                stopHeartBeat = true;
            }
            return true;
        } else if (msg.getCmdType() != 0) {
            byte data = msg.getData()[0];
            if (msg.getData() != null && msg.getData().length < 1) {
                return false;
            }
            if (msg.getCmdType() == 108) {
                if (data == 2 && msg.getData()[1] == 0) {
                    if (!this.mCloseScreen) {
                        opInterceptView(true, true);
                    }
                } else if (this.mCloseScreen) {
                    this.mCloseScreen = false;
                    opInterceptView(false, false);
                }
            }
            if (msg.getCmdType() == 99) {
                boolean release = data == 1 && msg.getData()[1] > 0;
                if (release | (data == 0 && msg.getData()[1] > 0)) {
                    updateStatus((byte) 1);
                }
            }
            if (msg.getCmdType() == 103) {
                if (data == 0 || data == 8 || data == 12 || data == 5 || data == 6 || data == 11 || data == 9) {
                    updateStatus((byte) 2);
                } else {
                    updateStatus((byte) 1);
                }
                SystemStatusControl.getStatus().lastMode = data;
            }
            if (msg.getCmdType() == 104) {
                boolean quit = data == 4;
                if (quit) {
                    updateStatus((byte) 1);
                }
            }
            if (msg.getCmdType() == 105) {
                if (data == 18 && msg.getData()[1] == 2) {
                    intercept = true;
                }
                if (intercept) {
                    updateStatus((byte) 2);
                }
            }
            return true;
        } else {
            Log.m70e(TAG, "sendMessage::cmdType error!");
            return false;
        }
    }

    private void handleMessage(KswMessage message) {
        if (message.getDataType() == 160) {
            handleUpdateMessage(message);
            return;
        }
        int cmdType = message.getCmdType();
        if (cmdType == 28) {
            if (callingStopHeartBeat) {
                return;
            }
            updateVideoStatus(message.getData()[0]);
        } else if (cmdType == 161) {
            this.mCarCanMsgHandle.handleCanMsg(message.getData());
            if (message.getData()[0] == 26) {
                updateStatus(message.getData()[1]);
            }
        } else {
            switch (cmdType) {
                case 16:
                case 18:
                case 20:
                case 22:
                case 23:
                    return;
                case 17:
                    if (message.getData()[0] == 1) {
                        this.isReversing = message.getData()[1] == 1;
                        updateInterceptView();
                        return;
                    } else if (message.getData()[0] == 2) {
                        SystemStatusControl.getStatus().setCcd(message.getData()[1]);
                        CallBackServiceImpl.getCallBackServiceImpl().handleReverse();
                        return;
                    } else if (message.getData()[0] == 4) {
                        this.isTurnOnBrightness = message.getData()[1] == 1;
                        return;
                    } else if (message.getData()[0] == 7 && message.getData()[1] == 1) {
                        try {
                            KswMcuSender.getSender().sendMessage(105, new byte[]{WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, 2, 100});
                            Thread.sleep(1000L);
                            PowerManager powerManager = (PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE);
                            new PowerManagerMirror(powerManager).shutdown(false, "CenterService shutdown", true);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                case 19:
                    Log.m72d(TAG, "clock input handleMessage CMD_CAR_SYSTEM_SETTINGS");
                    CenterControlImpl.getImpl().initBenzClockSort(message.getData()[9]);
                    CenterControlImpl.getImpl().saveForwardCamMirror(message.getData()[10]);
                    return;
                case 21:
                    this.mCurrentStatus = message.getData()[0];
                    updateStatus(this.mCurrentStatus);
                    return;
                default:
                    switch (cmdType) {
                        case 25:
                        default:
                            return;
                        case 26:
                            if (message.getData()[0] != 1) {
                                updateStatus((byte) 1);
                                return;
                            } else {
                                updateStatus((byte) 2);
                                return;
                            }
                    }
            }
        }
    }

    public void send(KswMessage message) {
        KswMcuSender.getSender().sendMessage(message);
    }

    public static boolean handleSendMsg(KswMessage message) {
        if (kswMcuLogic != null) {
            return kswMcuLogic.handleSendMessage(message);
        }
        return false;
    }

    public static void handleMsg(KswMessage message) {
        if (kswMcuLogic != null) {
            kswMcuLogic.handleMessage(message);
        }
    }

    public static void init(Context context) {
        kswMcuLogic = new KswMcuLogic(context);
    }

    public static KswMcuLogic getTestMcu() {
        return kswMcuLogic;
    }
}
