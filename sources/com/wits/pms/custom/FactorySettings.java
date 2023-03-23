package com.wits.pms.custom;

import android.os.Handler;
import android.util.Log;
import com.google.gson.Gson;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.utils.SysConfigUtil;
import java.io.File;

public class FactorySettings {
    public static final String FACTORY_SETTINGS_JSON = "/mnt/vendor/persist/OEM/FACTORY_SETTINGS";
    public static final String FACTORY_TYPE = new Gson().toJson((Object) new FactorySettings());
    private static final String TAG = "FactorySettings";
    public static final Handler mHandler = new Handler(PowerManagerAppService.serviceContext.getMainLooper());
    public static final Object mLock = new Object();
    public static SaveRunnable saveRunnable;
    public int AMP_Type;
    public int APK_Install;
    public int AUX_Type;
    public int Android_Bt_Switch = 1;
    public int AppsIcon_Select;
    public int Audi_Logo_Left;
    public int Audi_Logo_Right;
    public int BT_Type;
    public int Backlight_auto_set;
    public int CCC_IDrive;
    public int CarAux_Operate;
    public int CarAux_auto_method;
    public int CarDisplay;
    public int CarDoorNum;
    public int CarDoorSelect;
    public int CarVideoDisplayStyle;
    public int DTV_Type;
    public int DVD_Type;
    public String DVRApk_PackageName = "";
    public int DVR_Type;
    public int DashBoardUnit;
    public int Dashboard_MaxSpeed;
    public int Dashboard_Select;
    public int Default_PowerBoot;
    public int DirtTravelSelection;
    public int EQ_app;
    public int Front_view_camera;
    public int GoogleAPP;
    public int HandsetAutomaticSelect;
    public int Map_key;
    public int Mic_gain;
    public int Mode_key;
    public String Reverse_time = "";
    public int Screen_cast;
    public int Support_FM_Transmit;
    public int Support_TXZ;
    public int Support_TXZ_Voice_Wakeup;
    public int Support_dashboard;
    public String TXZ_Wakeup = "";
    public int UI;
    public String UI_type = "";
    public int Voice_key;
    public int Wifi_or_4G;
    public int cam360_video;
    public int canBusType;
    public int car_manufacturer;
    public String client = "";
    public int e_car;
    public String password = "";
    public int phone_key;
    public int speed_play_switch = 1;
    public int touch_continuous_send;
    public int txz_oil = 1;
    public int txz_speed = 120;
    public int txz_temp;
    public String ver = "";
    public int zlink_auto_start;
    public int zlink_hicar;

    public FactorySettings() {
        LogI(TAG, "create a new factory settings");
    }

    public int getTxz_oil() {
        return this.txz_oil;
    }

    public void setTxz_oil(int txz_oil2) {
        this.txz_oil = txz_oil2;
    }

    public int getTxz_speed() {
        return this.txz_speed;
    }

    public void setTxz_speed(int txz_speed2) {
        this.txz_speed = txz_speed2;
    }

    public int getTxz_temp() {
        return this.txz_temp;
    }

    public void setTxz_temp(int txz_temp2) {
        this.txz_temp = txz_temp2;
    }

    public void setReverse_time(String reverse_time) {
        this.Reverse_time = reverse_time;
    }

    public String getReverse_time() {
        return this.Reverse_time;
    }

    public int getMic_gain() {
        return this.Mic_gain;
    }

    public void setMic_gain(int mic_gain) {
        this.Mic_gain = mic_gain;
    }

    public int getScreen_cast() {
        return this.Screen_cast;
    }

    public void setScreen_cast(int screen_cast) {
        this.Screen_cast = screen_cast;
    }

    public int getDirtTravelSelection() {
        return this.DirtTravelSelection;
    }

    public void setDirtTravelSelection(int dirtTravelSelection) {
        this.DirtTravelSelection = dirtTravelSelection;
    }

    public int getSpeed_play_switch() {
        return this.speed_play_switch;
    }

    public void setSpeed_play_switch(int speed_play_switch2) {
        this.speed_play_switch = speed_play_switch2;
    }

    public int getCanBusType() {
        return this.canBusType;
    }

    public void setCanBusType(int canBusType2) {
        this.canBusType = canBusType2;
    }

    public int getCar_manufacturer() {
        return this.car_manufacturer;
    }

    public void setCar_manufacturer(int car_manufacturer2) {
        this.car_manufacturer = car_manufacturer2;
    }

    public int getAppsIcon_Select() {
        return this.AppsIcon_Select;
    }

    public void setAppsIcon_Select(int appsIcon_Select) {
        this.AppsIcon_Select = appsIcon_Select;
    }

    public int getTouch_continuous_send() {
        return this.touch_continuous_send;
    }

    public void setTouch_continuous_send(int touch_continuous_send2) {
        this.touch_continuous_send = touch_continuous_send2;
    }

    public int getAndroid_Bt_Switch() {
        return this.Android_Bt_Switch;
    }

    public void setAndroid_Bt_Switch(int android_Bt_Switch) {
        this.Android_Bt_Switch = android_Bt_Switch;
    }

    public String getVer() {
        return this.ver;
    }

    public void setVer(String ver2) {
        this.ver = ver2;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client2) {
        this.client = client2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getUI_type() {
        return this.UI_type;
    }

    public void setUI_type(String UI_type2) {
        this.UI_type = UI_type2;
    }

    public String getTXZ_Wakeup() {
        return this.TXZ_Wakeup;
    }

    public void setTXZ_Wakeup(String TXZ_Wakeup2) {
        this.TXZ_Wakeup = TXZ_Wakeup2;
    }

    public String getDVRApk_PackageName() {
        return this.DVRApk_PackageName;
    }

    public void setDVRApk_PackageName(String DVRApk_PackageName2) {
        this.DVRApk_PackageName = DVRApk_PackageName2;
    }

    public int getUI() {
        return this.UI;
    }

    public void setUI(int UI2) {
        this.UI = UI2;
    }

    public int getCarVideoDisplayStyle() {
        return this.CarVideoDisplayStyle;
    }

    public void setCarVideoDisplayStyle(int carVideoDisplayStyle) {
        this.CarVideoDisplayStyle = carVideoDisplayStyle;
    }

    public int getAudi_Logo_Left() {
        return this.Audi_Logo_Left;
    }

    public void setAudi_Logo_Left(int audi_Logo_Left) {
        this.Audi_Logo_Left = audi_Logo_Left;
    }

    public int getAudi_Logo_Right() {
        return this.Audi_Logo_Right;
    }

    public void setAudi_Logo_Right(int audi_Logo_Right) {
        this.Audi_Logo_Right = audi_Logo_Right;
    }

    public int getGoogleAPP() {
        return this.GoogleAPP;
    }

    public void setGoogleAPP(int googleAPP) {
        this.GoogleAPP = googleAPP;
    }

    public int getZlink_auto_start() {
        return this.zlink_auto_start;
    }

    public void setZlink_auto_start(int zlink_auto_start2) {
        this.zlink_auto_start = zlink_auto_start2;
    }

    public int getSupport_FM_Transmit() {
        return this.Support_FM_Transmit;
    }

    public void setSupport_FM_Transmit(int support_FM_Transmit) {
        this.Support_FM_Transmit = support_FM_Transmit;
    }

    public int getSupport_dashboard() {
        return this.Support_dashboard;
    }

    public void setSupport_dashboard(int support_dashboard) {
        this.Support_dashboard = support_dashboard;
    }

    public int getFront_view_camera() {
        return this.Front_view_camera;
    }

    public void setFront_view_camera(int front_view_camera) {
        this.Front_view_camera = front_view_camera;
    }

    public int getDVD_Type() {
        return this.DVD_Type;
    }

    public void setDVD_Type(int DVD_Type2) {
        this.DVD_Type = DVD_Type2;
    }

    public int getAUX_Type() {
        return this.AUX_Type;
    }

    public void setAUX_Type(int AUX_Type2) {
        this.AUX_Type = AUX_Type2;
    }

    public int getDTV_Type() {
        return this.DTV_Type;
    }

    public void setDTV_Type(int DTV_Type2) {
        this.DTV_Type = DTV_Type2;
    }

    public int getDVR_Type() {
        return this.DVR_Type;
    }

    public void setDVR_Type(int DVR_Type2) {
        this.DVR_Type = DVR_Type2;
    }

    public int getBT_Type() {
        return this.BT_Type;
    }

    public void setBT_Type(int BT_Type2) {
        this.BT_Type = BT_Type2;
    }

    public int getAPK_Install() {
        return this.APK_Install;
    }

    public void setAPK_Install(int APK_Install2) {
        this.APK_Install = APK_Install2;
    }

    public int getCarAux_auto_method() {
        return this.CarAux_auto_method;
    }

    public void setCarAux_auto_method(int carAux_auto_method) {
        this.CarAux_auto_method = carAux_auto_method;
    }

    public int getCarAux_Operate() {
        return this.CarAux_Operate;
    }

    public void setCarAux_Operate(int carAux_Operate) {
        this.CarAux_Operate = carAux_Operate;
    }

    public int getAMP_Type() {
        return this.AMP_Type;
    }

    public void setAMP_Type(int AMP_Type2) {
        this.AMP_Type = AMP_Type2;
    }

    public int getCarDisplay() {
        return this.CarDisplay;
    }

    public void setCarDisplay(int carDisplay) {
        this.CarDisplay = carDisplay;
    }

    public int getSupport_TXZ() {
        return this.Support_TXZ;
    }

    public void setSupport_TXZ(int support_TXZ) {
        this.Support_TXZ = support_TXZ;
    }

    public int getEQ_app() {
        return this.EQ_app;
    }

    public void setEQ_app(int eq_app) {
        this.EQ_app = eq_app;
    }

    public int getSupport_TXZ_Voice_Wakeup() {
        return this.Support_TXZ_Voice_Wakeup;
    }

    public void setSupport_TXZ_Voice_Wakeup(int support_TXZ_Voice_Wakeup) {
        this.Support_TXZ_Voice_Wakeup = support_TXZ_Voice_Wakeup;
    }

    public int getCCC_IDrive() {
        return this.CCC_IDrive;
    }

    public void setCCC_IDrive(int CCC_IDrive2) {
        this.CCC_IDrive = CCC_IDrive2;
    }

    public int getDashboard_MaxSpeed() {
        return this.Dashboard_MaxSpeed;
    }

    public void setDashboard_MaxSpeed(int dashboard_MaxSpeed) {
        this.Dashboard_MaxSpeed = dashboard_MaxSpeed;
    }

    public int getHandsetAutomaticSelect() {
        return this.HandsetAutomaticSelect;
    }

    public void setHandsetAutomaticSelect(int handsetAutomaticSelect) {
        this.HandsetAutomaticSelect = handsetAutomaticSelect;
    }

    public int getCarDoorSelect() {
        return this.CarDoorSelect;
    }

    public void setCarDoorSelect(int carDoorSelect) {
        this.CarDoorSelect = carDoorSelect;
    }

    public int getWifi_or_4G() {
        return this.Wifi_or_4G;
    }

    public void setWifi_or_4G(int wifi_or_4G) {
        this.Wifi_or_4G = wifi_or_4G;
    }

    public int getVoice_key() {
        return this.Voice_key;
    }

    public void setVoice_key(int voice_key) {
        this.Voice_key = voice_key;
    }

    public int getPhone_key() {
        return this.phone_key;
    }

    public void setPhone_key(int phone_key2) {
        this.phone_key = phone_key2;
    }

    public int getMap_key() {
        return this.Map_key;
    }

    public void setMap_key(int map_key) {
        this.Map_key = map_key;
    }

    public int getCam360_video() {
        return this.cam360_video;
    }

    public void setCam360_video(int cam360_video2) {
        this.cam360_video = cam360_video2;
    }

    public int getCarDoorNum() {
        return this.CarDoorNum;
    }

    public void setCarDoorNum(int carDoorNum) {
        this.CarDoorNum = carDoorNum;
    }

    public int getMode_key() {
        return this.Mode_key;
    }

    public void setMode_key(int mode_key) {
        this.Mode_key = mode_key;
    }

    public int getBacklight_auto_set() {
        return this.Backlight_auto_set;
    }

    public void setBacklight_auto_set(int backlight_auto_set) {
        this.Backlight_auto_set = backlight_auto_set;
    }

    public int getDashBoardUnit() {
        return this.DashBoardUnit;
    }

    public void setDashBoardUnit(int dashBoardUnit) {
        this.DashBoardUnit = dashBoardUnit;
    }

    public int getDashboard_Select() {
        return this.Dashboard_Select;
    }

    public void setDashboard_Select(int dashboard_Select) {
        this.Dashboard_Select = dashboard_Select;
    }

    public int getE_car() {
        return this.e_car;
    }

    public void setE_car(int e_car2) {
        this.e_car = e_car2;
    }

    public int getZlink_hicar() {
        return this.zlink_hicar;
    }

    public void setZlink_hicar(int zlink_hicar2) {
        this.zlink_hicar = zlink_hicar2;
    }

    public static FactorySettings getFactorySettings() {
        FactorySettings factorySettings;
        synchronized (mLock) {
            String settingsJson = SysConfigUtil.getArg(FACTORY_SETTINGS_JSON);
            factorySettings = (FactorySettings) new Gson().fromJson(settingsJson, FactorySettings.class);
            if (factorySettings != null) {
                LogI(TAG, "getFactorySettings:" + settingsJson);
            }
        }
        return factorySettings;
    }

    public static void clearFactorySettings() {
        try {
            new File(FACTORY_SETTINGS_JSON).delete();
        } catch (Exception e) {
        }
    }

    public static void saveFactorySettings(FactorySettings settings) {
        synchronized (mLock) {
            SysConfigUtil.writeArg(new Gson().toJson((Object) settings), FACTORY_SETTINGS_JSON, new SysConfigUtil.WriteListener() {
                public void start() {
                    FactorySettings.LogI(FactorySettings.TAG, "save start");
                }

                public void success() {
                    FactorySettings.LogI(FactorySettings.TAG, "save success");
                }

                public void failed() {
                    FactorySettings.LogI(FactorySettings.TAG, "save failed");
                }
            });
        }
    }

    public static boolean isFactory(String key) {
        return FACTORY_TYPE.contains(key);
    }

    public class SaveRunnable implements Runnable {
        private final FactorySettings settings;

        public SaveRunnable(FactorySettings settings2) {
            this.settings = settings2;
        }

        public void run() {
            FactorySettings.saveFactorySettings(this.settings);
        }
    }

    private String getFirstUpCaseString(String key) {
        StringBuilder sb = new StringBuilder(key);
        sb.replace(0, 1, (key.charAt(0) + "").toUpperCase());
        return sb.toString();
    }

    public void saveStringValue(String key, String value) {
        if (isFactory(key)) {
            LogI(TAG, "saveStringValue key:" + key + "- value:" + value);
            String key2 = getFirstUpCaseString(key);
            Class<FactorySettings> cls = FactorySettings.class;
            try {
                cls.getMethod("set" + key2, new Class[]{String.class}).invoke(this, new Object[]{value});
                saveToFile();
            } catch (Exception e) {
            }
        }
    }

    public void saveIntValue(String key, int value) {
        if (isFactory(key)) {
            LogI(TAG, "saveIntValue key:" + key + "- value:" + value);
            String key2 = getFirstUpCaseString(key);
            Class<FactorySettings> cls = FactorySettings.class;
            try {
                cls.getMethod("set" + key2, new Class[]{Integer.TYPE}).invoke(this, new Object[]{Integer.valueOf(value)});
                saveToFile();
            } catch (Exception e) {
            }
        }
    }

    public String getStringValue(String key) {
        if (!isFactory(key)) {
            return null;
        }
        LogI(TAG, "getStringValue key:" + key);
        String key2 = getFirstUpCaseString(key);
        Class<FactorySettings> cls = FactorySettings.class;
        try {
            return (String) cls.getMethod("get" + key2, new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    public int getIntValue(String key) {
        if (!isFactory(key)) {
            return 0;
        }
        LogI(TAG, "getIntValue key:" + key);
        String key2 = getFirstUpCaseString(key);
        Class<FactorySettings> cls = FactorySettings.class;
        try {
            return ((Integer) cls.getMethod("get" + key2, new Class[0]).invoke(this, new Object[0])).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public void saveToFile() {
        mHandler.removeCallbacks(saveRunnable);
        saveRunnable = new SaveRunnable(this);
        mHandler.postDelayed(saveRunnable, 2000);
    }

    public static void LogI(String TAG2, String content) {
        Log.i(TAG2, content);
    }
}
