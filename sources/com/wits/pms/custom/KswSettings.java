package com.wits.pms.custom;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wits.pms.BuildConfig;
import com.wits.pms.R;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.utils.BrightnessUtils;
import com.wits.pms.utils.SysConfigUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KswSettings {
    private static final String CAN_ID = "CANBusProtocolID";
    private static final String CAR_DISPLAY_ID = "CarDisplayParamID";
    public static final String CODE_LIST = "codeList";
    private static final String INT_KEY_MAP = "ksw_settings_int_key";
    private static final String LANGUAGE_ID = "languageID";
    private static final String PROTOCOL_KEY = "Protocol";
    private static final String STRING_KEY_MAP = "ksw_settings_string_key";
    private static final String TAG = "KswSettings";
    private static KswSettings settings = null;
    private static final String uiSave = "/mnt/vendor/persist/OEM/uiSave.ui";
    /* access modifiers changed from: private */
    public File defaultConfig = new File("/mnt/vendor/persist/OEM/factory_config.xml");
    /* access modifiers changed from: private */
    public File directUpdateFile;
    private long initTime;
    private String intKeys = BuildConfig.FLAVOR;
    private boolean isSyncStatus;
    /* access modifiers changed from: private */
    public final Context mContext;
    /* access modifiers changed from: private */
    public FactorySettings mFactorySettings;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    private final InitConfigRunnable mInitConfigRunnable;
    /* access modifiers changed from: private */
    public boolean onlyLanguageId;
    private String stringKeys = BuildConfig.FLAVOR;

    public static KswSettings init(Context context) {
        if (settings == null) {
            settings = new KswSettings(context);
        }
        return settings;
    }

    public static KswSettings getSettings() {
        if (settings != null) {
            return settings;
        }
        KswSettings init = init(PowerManagerAppService.serviceContext);
        settings = init;
        return init;
    }

    private KswSettings(Context context) {
        this.mContext = context;
        this.mHandler = new Handler(this.mContext.getMainLooper());
        this.initTime = System.currentTimeMillis();
        this.stringKeys = getSettingsString(STRING_KEY_MAP);
        this.intKeys = getIntKeysFromSp();
        if (this.stringKeys == null) {
            this.stringKeys = BuildConfig.FLAVOR;
        }
        if (this.intKeys == null) {
            this.intKeys = BuildConfig.FLAVOR;
        }
        this.mInitConfigRunnable = new InitConfigRunnable();
        boolean initKswConfig = true;
        try {
            if (getSettingsInt("initKswConfig") != 0) {
                initKswConfig = false;
            }
        } catch (Settings.SettingNotFoundException e) {
        }
        initConfig(initKswConfig);
        checkDirectUpdate();
        obsSystemSettings();
        clearNoSaveData();
    }

    private String getIntKeysFromSp() {
        String keys1 = getSettingsString("ksw_settings_int_key1");
        String keys2 = getSettingsString("ksw_settings_int_key2");
        return keys1 + keys2;
    }

    /* access modifiers changed from: private */
    public void saveIntKeyToSp(String keyMap) {
        String keys1 = keyMap;
        String keys2 = BuildConfig.FLAVOR;
        if (keyMap.length() >= 1000) {
            keys1 = keyMap.substring(1000);
            keys2 = keyMap.substring(0, 1000);
        }
        Settings.System.putString(this.mContext.getContentResolver(), "ksw_settings_int_key1", keys1);
        Settings.System.putString(this.mContext.getContentResolver(), "ksw_settings_int_key2", keys2);
    }

    private void clearNoSaveData() {
        try {
            getSettingsInt("Android_Bt_Switch");
        } catch (Settings.SettingNotFoundException e) {
            setInt("Android_Bt_Switch", 1);
        }
        Settings.System.putInt(this.mContext.getContentResolver(), "benz_aux_switch", 0);
        setInt("can_bus_switch", 0);
    }

    private void obsSystemSettings() {
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_brightness"), true, new ContentObserver(this.mHandler) {
            public void onChange(boolean selfChange) {
                try {
                    int progress = (int) Math.round(100.0d * BrightnessUtils.getPercentage((double) BrightnessUtils.convertLinearToGamma(Settings.System.getInt(KswSettings.this.mContext.getContentResolver(), "screen_brightness"), 10, 255), 0, BrightnessUtils.GAMMA_SPACE_MAX));
                    Settings.System.putInt(KswSettings.this.mContext.getContentResolver(), "Brightness", progress);
                    CenterControlImpl.getImpl().setBrightness(progress);
                } catch (Settings.SettingNotFoundException e) {
                }
            }
        });
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_12_24"), true, new ContentObserver(this.mHandler) {
            public void onChange(boolean selfChange) {
                KswSettings.this.setInt("TimeFormat", "12".equals(KswSettings.this.getSettingsString("time_12_24")) ? 1 : 0);
            }
        });
    }

    public int getSettingsInt(String key) throws Settings.SettingNotFoundException {
        return Settings.System.getInt(this.mContext.getContentResolver(), key);
    }

    public String getSettingsString(String key) {
        return Settings.System.getString(this.mContext.getContentResolver(), key);
    }

    public void setInt(String key, int value) {
        Settings.System.putInt(this.mContext.getContentResolver(), key, value);
        handleConfig(key, value);
        saveIntKey(key);
        if (this.mFactorySettings != null) {
            this.mFactorySettings.saveIntValue(key, value);
        }
        Log.i(TAG, "setInt key:" + key + " - value:" + value);
    }

    public void setString(String key, String value) {
        Settings.System.putString(this.mContext.getContentResolver(), key, value);
        handleConfig(key, value);
        saveStringKey(key);
        if (this.mFactorySettings != null) {
            this.mFactorySettings.saveStringValue(key, value);
        }
        Log.i(TAG, "setString key:" + key + " - value:" + value);
    }

    private void saveIntKey(String key) {
        String str;
        Log.i(TAG, "saveIntKey key:" + key);
        if (this.intKeys.contains(key)) {
            str = this.intKeys;
        } else {
            str = this.intKeys + key + ",";
        }
        this.intKeys = str;
        saveIntKeyToSp(this.intKeys + key + ",");
    }

    private void saveStringKey(String key) {
        String str;
        Log.i(TAG, "saveStringKey key:" + key);
        if (this.stringKeys.contains(key)) {
            str = this.stringKeys;
        } else {
            str = this.stringKeys + key + ",";
        }
        this.stringKeys = str;
        Settings.System.putString(this.mContext.getContentResolver(), STRING_KEY_MAP, this.stringKeys);
    }

    public void saveJson(String key, String json) {
        setString(key, json);
    }

    public void updateConfig(File confgFile) {
        if (!confgFile.equals(this.defaultConfig)) {
            FactorySettings.clearFactorySettings();
        }
        this.mInitConfigRunnable.setFilePath(confgFile);
        new Thread(this.mInitConfigRunnable).start();
    }

    public void initConfig(boolean forConfig) {
        if (forConfig) {
            Log.i(TAG, "initConfig");
            updateConfig(this.defaultConfig);
            return;
        }
        Settings.System.putString(this.mContext.getContentResolver(), "UiName", getSaveUiName());
        Log.i(TAG, "sync UI:" + getSaveUiName());
        syncStatus();
        fixVersion();
    }

    public void saveAndReboot() {
        this.mFactorySettings.saveToFile();
        PowerManager pm = (PowerManager) this.mContext.getSystemService("power");
        if (pm != null) {
            pm.reboot(BuildConfig.FLAVOR);
        }
    }

    private void fixVersion() {
        if (TextUtils.isEmpty(getSettingsString(LANGUAGE_ID))) {
            Log.i(TAG, "fixVersion");
            this.onlyLanguageId = true;
            initConfig(true);
        }
        try {
            if (this.mFactorySettings.getDashboard_Select() != getSettingsInt("Dashboard_Select")) {
                this.mFactorySettings.saveIntValue("Dashboard_Select", getSettingsInt("Dashboard_Select"));
            }
        } catch (Settings.SettingNotFoundException e) {
        }
    }

    private void syncStatus() {
        Log.i(TAG, "syncStatus to Mcu");
        this.isSyncStatus = true;
        this.mFactorySettings = FactorySettings.getFactorySettings();
        boolean fixLocalFactory = false;
        if (this.mFactorySettings == null) {
            Log.i("FactorySettings", "fix missing local factory settings file");
            fixLocalFactory = true;
            this.mFactorySettings = new FactorySettings();
        }
        String intKeys2 = getIntKeysFromSp();
        String stringKeys2 = Settings.System.getString(this.mContext.getContentResolver(), STRING_KEY_MAP);
        if (!TextUtils.isEmpty(intKeys2) && !TextUtils.isEmpty(stringKeys2)) {
            String[] handleIntKeys = intKeys2.split(",");
            String[] handleStringKeys = stringKeys2.split(",");
            for (String key : handleIntKeys) {
                if (!key.equals("USB_HOST") && !key.equals("Language") && !key.equals("Support_TXZ") && !key.equals("BenzPanelEnable") && !key.equals("benz_aux_switch")) {
                    try {
                        int value = getSettingsInt(key);
                        handleConfig(key, value);
                        if (fixLocalFactory) {
                            Log.i("FactorySettings", "fix key:" + key + "-value:" + value);
                            this.mFactorySettings.saveIntValue(key, value);
                        }
                    } catch (Settings.SettingNotFoundException e) {
                    }
                }
            }
            for (String key2 : handleStringKeys) {
                if (!key2.contains("UI")) {
                    String value2 = getSettingsString(key2);
                    handleConfig(key2, value2);
                    if (fixLocalFactory) {
                        Log.i("FactorySettings", "fix key:" + key2 + "-value:" + value2);
                        this.mFactorySettings.saveStringValue(key2, value2);
                    }
                }
            }
            this.isSyncStatus = false;
        }
    }

    private void sendMcu(int cmdType, byte[] data) {
        KswMcuSender.getSender().sendMessage(cmdType, data);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleConfig(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            r1 = -1548945544(0xffffffffa3acf778, float:-1.8753084E-17)
            r2 = 2
            r3 = 1
            r4 = 0
            if (r0 == r1) goto L_0x0039
            r1 = -533217944(0xffffffffe037bd68, float:-5.295941E19)
            if (r0 == r1) goto L_0x002f
            r1 = 2708(0xa94, float:3.795E-42)
            if (r0 == r1) goto L_0x0025
            r1 = 309639685(0x1274ba05, float:7.722211E-28)
            if (r0 == r1) goto L_0x001b
            goto L_0x0043
        L_0x001b:
            java.lang.String r0 = "UI_type"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 3
            goto L_0x0044
        L_0x0025:
            java.lang.String r0 = "UI"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 2
            goto L_0x0044
        L_0x002f:
            java.lang.String r0 = "TXZ_Wakeup"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 0
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "Language"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = 1
            goto L_0x0044
        L_0x0043:
            r0 = -1
        L_0x0044:
            switch(r0) {
                case 0: goto L_0x00d1;
                case 1: goto L_0x00b3;
                case 2: goto L_0x0049;
                case 3: goto L_0x0049;
                default: goto L_0x0047;
            }
        L_0x0047:
            goto L_0x00dd
        L_0x0049:
            java.lang.String r0 = "UI"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0062
            java.lang.String r0 = "SupportUIList"
            java.util.List r0 = r6.getDataListFromJsonKey(r0)
            int r1 = java.lang.Integer.parseInt(r8)
            java.lang.Object r0 = r0.get(r1)
            r8 = r0
            java.lang.String r8 = (java.lang.String) r8
        L_0x0062:
            java.lang.String r0 = "initKswConfig"
            int r0 = r6.getSettingsInt(r0)     // Catch:{ SettingNotFoundException -> 0x006d }
            if (r0 != 0) goto L_0x006b
            goto L_0x006c
        L_0x006b:
            r3 = 0
        L_0x006c:
            goto L_0x006f
        L_0x006d:
            r0 = move-exception
        L_0x006f:
            r0 = r3
            if (r0 == 0) goto L_0x0090
            boolean r1 = r6.checkUi()
            if (r1 != 0) goto L_0x007e
            java.lang.String r1 = "/mnt/vendor/persist/OEM/uiSave.ui"
            com.wits.pms.utils.SysConfigUtil.writeArg(r8, r1)
            goto L_0x0084
        L_0x007e:
            java.lang.String r1 = "/mnt/vendor/persist/OEM/uiSave.ui"
            java.lang.String r8 = com.wits.pms.utils.SysConfigUtil.getArg(r1)
        L_0x0084:
            android.content.Context r1 = r6.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "UiName"
            android.provider.Settings.System.putString(r1, r2, r8)
            goto L_0x00dd
        L_0x0090:
            android.content.Context r1 = r6.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            java.lang.String r2 = "UiName"
            android.provider.Settings.System.putString(r1, r2, r8)
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x00b1 }
            java.lang.String r2 = "/mnt/vendor/persist/OEM/uiSave.ui"
            r1.<init>(r2)     // Catch:{ IOException -> 0x00b1 }
            boolean r2 = r1.exists()     // Catch:{ IOException -> 0x00b1 }
            if (r2 != 0) goto L_0x00ab
            r1.createNewFile()     // Catch:{ IOException -> 0x00b1 }
        L_0x00ab:
            java.lang.String r2 = "/mnt/vendor/persist/OEM/uiSave.ui"
            com.wits.pms.utils.SysConfigUtil.writeArg(r8, r2)     // Catch:{ IOException -> 0x00b1 }
            goto L_0x00dd
        L_0x00b1:
            r1 = move-exception
            goto L_0x00dd
        L_0x00b3:
            java.lang.String r0 = "-"
            java.lang.String[] r0 = r8.split(r0)
            java.util.Locale r1 = new java.util.Locale
            r5 = r0[r4]
            r1.<init>(r5)
            int r5 = r0.length
            if (r5 != r2) goto L_0x00cd
            java.util.Locale r2 = new java.util.Locale
            r4 = r0[r4]
            r3 = r0[r3]
            r2.<init>(r4, r3)
            r1 = r2
        L_0x00cd:
            com.wits.pms.utils.LanguageUtil.changeSystemLanguage(r1)
            goto L_0x00dd
        L_0x00d1:
            android.content.Context r0 = r6.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "ksw_wakeup_keywords"
            android.provider.Settings.System.putString(r0, r1, r8)
        L_0x00dd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.custom.KswSettings.handleConfig(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int handleConfig(java.lang.String r19, int r20) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            byte r4 = (byte) r3
            int r5 = r19.hashCode()
            r6 = 13
            r7 = 19
            r10 = 17
            r11 = 5
            r12 = 16
            r13 = 4
            r14 = 3
            r15 = 2
            r16 = 0
            r9 = 1
            switch(r5) {
                case -2113780075: goto L_0x021f;
                case -1997186328: goto L_0x0214;
                case -1885010620: goto L_0x0209;
                case -1793262372: goto L_0x01fe;
                case -1653340047: goto L_0x01f4;
                case -1548945544: goto L_0x01e9;
                case -1528907729: goto L_0x01de;
                case -1528907728: goto L_0x01d3;
                case -1181891355: goto L_0x01c8;
                case -1103831850: goto L_0x01bc;
                case -952414302: goto L_0x01b0;
                case -924519752: goto L_0x01a4;
                case -899948362: goto L_0x0198;
                case -801026034: goto L_0x018c;
                case -776702634: goto L_0x0180;
                case -738352606: goto L_0x0174;
                case -682449643: goto L_0x0168;
                case -677297959: goto L_0x015c;
                case -660995341: goto L_0x0150;
                case -602992886: goto L_0x0145;
                case -554769949: goto L_0x0139;
                case -519600940: goto L_0x012e;
                case -294129095: goto L_0x0122;
                case -220630678: goto L_0x0117;
                case -183099962: goto L_0x010b;
                case -114997240: goto L_0x00ff;
                case 88063465: goto L_0x00f4;
                case 90414126: goto L_0x00e9;
                case 106858821: goto L_0x00de;
                case 214368532: goto L_0x00d2;
                case 241352631: goto L_0x00c6;
                case 252039837: goto L_0x00ba;
                case 340057703: goto L_0x00ae;
                case 442866595: goto L_0x00a2;
                case 642405998: goto L_0x0096;
                case 940906279: goto L_0x008a;
                case 985261490: goto L_0x007e;
                case 1010516114: goto L_0x0072;
                case 1060762673: goto L_0x0066;
                case 1071490530: goto L_0x005a;
                case 1374371814: goto L_0x004e;
                case 1533443583: goto L_0x0042;
                case 1598142665: goto L_0x0037;
                case 1857618170: goto L_0x002b;
                case 2103778808: goto L_0x001f;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x022a
        L_0x001f:
            java.lang.String r5 = "DashBoardUnit"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 22
            goto L_0x022b
        L_0x002b:
            java.lang.String r5 = "cam360_video"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 35
            goto L_0x022b
        L_0x0037:
            java.lang.String r5 = "Front_view_camera"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 5
            goto L_0x022b
        L_0x0042:
            java.lang.String r5 = "benz_aux_switch"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 44
            goto L_0x022b
        L_0x004e:
            java.lang.String r5 = "Treble_value"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 18
            goto L_0x022b
        L_0x005a:
            java.lang.String r5 = "TimeSyncSoucrce"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 12
            goto L_0x022b
        L_0x0066:
            java.lang.String r5 = "Bass_value"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 16
            goto L_0x022b
        L_0x0072:
            java.lang.String r5 = "Android_phone_vol"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 9
            goto L_0x022b
        L_0x007e:
            java.lang.String r5 = "Voice_key"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 32
            goto L_0x022b
        L_0x008a:
            java.lang.String r5 = "BT_Type"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 25
            goto L_0x022b
        L_0x0096:
            java.lang.String r5 = "CarDisplay"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 29
            goto L_0x022b
        L_0x00a2:
            java.lang.String r5 = "USB_HOST"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 42
            goto L_0x022b
        L_0x00ae:
            java.lang.String r5 = "Middle_value"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 17
            goto L_0x022b
        L_0x00ba:
            java.lang.String r5 = "CCC_IDrive"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 30
            goto L_0x022b
        L_0x00c6:
            java.lang.String r5 = "Car_phone_vol"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 10
            goto L_0x022b
        L_0x00d2:
            java.lang.String r5 = "HandsetAutomaticSelect"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 31
            goto L_0x022b
        L_0x00de:
            java.lang.String r5 = "RearCamType"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 6
            goto L_0x022b
        L_0x00e9:
            java.lang.String r5 = "ShowTrack"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 1
            goto L_0x022b
        L_0x00f4:
            java.lang.String r5 = "ShowRadar"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 2
            goto L_0x022b
        L_0x00ff:
            java.lang.String r5 = "Android_media_vol"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 8
            goto L_0x022b
        L_0x010b:
            java.lang.String r5 = "Support_TXZ"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 43
            goto L_0x022b
        L_0x0117:
            java.lang.String r5 = "DoNotPlayVideosWhileDriving"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 3
            goto L_0x022b
        L_0x0122:
            java.lang.String r5 = "DVR_Type"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 26
            goto L_0x022b
        L_0x012e:
            java.lang.String r5 = "ReversingMuteSelect"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 4
            goto L_0x022b
        L_0x0139:
            java.lang.String r5 = "Mode_key"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 34
            goto L_0x022b
        L_0x0145:
            java.lang.String r5 = "RearCamMirror"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 0
            goto L_0x022b
        L_0x0150:
            java.lang.String r5 = "OLDBMWX"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 41
            goto L_0x022b
        L_0x015c:
            java.lang.String r5 = "Default_PowerBoot"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 39
            goto L_0x022b
        L_0x0168:
            java.lang.String r5 = "AMP_Type"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 27
            goto L_0x022b
        L_0x0174:
            java.lang.String r5 = "CarAux_auto_method"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 23
            goto L_0x022b
        L_0x0180:
            java.lang.String r5 = "EQ_mode"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 15
            goto L_0x022b
        L_0x018c:
            java.lang.String r5 = "AHD_cam_Select"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 40
            goto L_0x022b
        L_0x0198:
            java.lang.String r5 = "NaviMix"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 14
            goto L_0x022b
        L_0x01a4:
            java.lang.String r5 = "Protocol"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 20
            goto L_0x022b
        L_0x01b0:
            java.lang.String r5 = "Backlight_auto_set"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 36
            goto L_0x022b
        L_0x01bc:
            java.lang.String r5 = "CarVideoDisplayStyle"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 21
            goto L_0x022b
        L_0x01c8:
            java.lang.String r5 = "Car_navi_vol"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 11
            goto L_0x022b
        L_0x01d3:
            java.lang.String r5 = "CarAuxIndex2"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 38
            goto L_0x022b
        L_0x01de:
            java.lang.String r5 = "CarAuxIndex1"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 37
            goto L_0x022b
        L_0x01e9:
            java.lang.String r5 = "Language"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 19
            goto L_0x022b
        L_0x01f4:
            java.lang.String r5 = "Brightness"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 7
            goto L_0x022b
        L_0x01fe:
            java.lang.String r5 = "Map_key"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 33
            goto L_0x022b
        L_0x0209:
            java.lang.String r5 = "TimeFormat"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 13
            goto L_0x022b
        L_0x0214:
            java.lang.String r5 = "Front_view_camer1a"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 28
            goto L_0x022b
        L_0x021f:
            java.lang.String r5 = "CarAux_Operate"
            boolean r5 = r2.equals(r5)
            if (r5 == 0) goto L_0x022a
            r5 = 24
            goto L_0x022b
        L_0x022a:
            r5 = -1
        L_0x022b:
            r8 = 112(0x70, float:1.57E-43)
            switch(r5) {
                case 0: goto L_0x059f;
                case 1: goto L_0x0591;
                case 2: goto L_0x0583;
                case 3: goto L_0x0575;
                case 4: goto L_0x0569;
                case 5: goto L_0x055b;
                case 6: goto L_0x054d;
                case 7: goto L_0x052a;
                case 8: goto L_0x0514;
                case 9: goto L_0x04fe;
                case 10: goto L_0x04e8;
                case 11: goto L_0x04d2;
                case 12: goto L_0x04b6;
                case 13: goto L_0x0471;
                case 14: goto L_0x045e;
                case 15: goto L_0x03d8;
                case 16: goto L_0x03d8;
                case 17: goto L_0x03d8;
                case 18: goto L_0x03d8;
                case 19: goto L_0x038c;
                case 20: goto L_0x0367;
                case 21: goto L_0x0347;
                case 22: goto L_0x033a;
                case 23: goto L_0x032d;
                case 24: goto L_0x0322;
                case 25: goto L_0x0317;
                case 26: goto L_0x030c;
                case 27: goto L_0x0301;
                case 28: goto L_0x02f6;
                case 29: goto L_0x02e9;
                case 30: goto L_0x02dd;
                case 31: goto L_0x02d0;
                case 32: goto L_0x02c5;
                case 33: goto L_0x02b8;
                case 34: goto L_0x02ab;
                case 35: goto L_0x029e;
                case 36: goto L_0x0293;
                case 37: goto L_0x0286;
                case 38: goto L_0x0279;
                case 39: goto L_0x026c;
                case 40: goto L_0x025f;
                case 41: goto L_0x0254;
                case 42: goto L_0x024f;
                case 43: goto L_0x0242;
                case 44: goto L_0x0232;
                default: goto L_0x0230;
            }
        L_0x0230:
            goto L_0x05ab
        L_0x0232:
            java.lang.String r5 = "LKT"
            com.wits.pms.utils.TestUtil.printStack(r5)
            byte[] r5 = new byte[r15]
            r5[r16] = r12
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0242:
            com.wits.pms.core.CenterControlImpl r5 = com.wits.pms.core.CenterControlImpl.getImpl()
            if (r4 != r9) goto L_0x0249
            goto L_0x024a
        L_0x0249:
            r9 = 0
        L_0x024a:
            r5.setTxzSwitch(r9)
            goto L_0x05ab
        L_0x024f:
            com.wits.pms.utils.UsbUtil.updateUsbMode((int) r4)
            goto L_0x05ab
        L_0x0254:
            java.lang.String r5 = "CarDisplay"
            if (r4 != r9) goto L_0x025a
            r9 = 0
        L_0x025a:
            r1.handleConfig((java.lang.String) r5, (int) r9)
            goto L_0x05ab
        L_0x025f:
            byte[] r5 = new byte[r15]
            r6 = 20
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x026c:
            byte[] r5 = new byte[r15]
            r6 = 25
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0279:
            byte[] r5 = new byte[r15]
            r6 = 24
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0286:
            byte[] r5 = new byte[r15]
            r6 = 23
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0293:
            byte[] r5 = new byte[r15]
            r5[r16] = r7
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x029e:
            byte[] r5 = new byte[r15]
            r6 = 18
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02ab:
            byte[] r5 = new byte[r15]
            r6 = 22
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02b8:
            byte[] r5 = new byte[r15]
            r6 = 21
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02c5:
            byte[] r5 = new byte[r15]
            r5[r16] = r10
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02d0:
            byte[] r5 = new byte[r15]
            r6 = 9
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02dd:
            byte[] r5 = new byte[r15]
            r6 = 6
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02e9:
            byte[] r5 = new byte[r15]
            r6 = 14
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x02f6:
            byte[] r5 = new byte[r15]
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0301:
            byte[] r5 = new byte[r15]
            r5[r16] = r15
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x030c:
            byte[] r5 = new byte[r15]
            r5[r16] = r13
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0317:
            byte[] r5 = new byte[r15]
            r5[r16] = r11
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0322:
            byte[] r5 = new byte[r15]
            r5[r16] = r14
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x032d:
            byte[] r5 = new byte[r15]
            r6 = 12
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x033a:
            byte[] r5 = new byte[r15]
            r6 = 26
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0347:
            java.lang.String r5 = "CarDisplayParamID"
            java.util.List r5 = r1.getDataListFromJsonKey(r5)     // Catch:{ Exception -> 0x0364 }
            if (r5 == 0) goto L_0x0362
            java.lang.Object r6 = r5.get(r4)     // Catch:{ Exception -> 0x0364 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0364 }
            byte r7 = java.lang.Byte.parseByte(r6)     // Catch:{ Exception -> 0x0364 }
            byte[] r10 = new byte[r15]     // Catch:{ Exception -> 0x0364 }
            r10[r16] = r9     // Catch:{ Exception -> 0x0364 }
            r10[r9] = r7     // Catch:{ Exception -> 0x0364 }
            r1.sendMcu(r8, r10)     // Catch:{ Exception -> 0x0364 }
        L_0x0362:
            goto L_0x05ab
        L_0x0364:
            r0 = move-exception
            goto L_0x05ab
        L_0x0367:
            r5 = -1
            if (r4 != r5) goto L_0x036b
            r4 = 0
        L_0x036b:
            java.lang.String r5 = "CANBusProtocolID"
            java.util.List r5 = r1.getDataListFromJsonKey(r5)     // Catch:{ Exception -> 0x0389 }
            if (r5 == 0) goto L_0x0387
            java.lang.Object r6 = r5.get(r4)     // Catch:{ Exception -> 0x0389 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0389 }
            byte r7 = java.lang.Byte.parseByte(r6)     // Catch:{ Exception -> 0x0389 }
            byte[] r10 = new byte[r15]     // Catch:{ Exception -> 0x0389 }
            r11 = 7
            r10[r16] = r11     // Catch:{ Exception -> 0x0389 }
            r10[r9] = r7     // Catch:{ Exception -> 0x0389 }
            r1.sendMcu(r8, r10)     // Catch:{ Exception -> 0x0389 }
        L_0x0387:
            goto L_0x05ab
        L_0x0389:
            r0 = move-exception
            goto L_0x05ab
        L_0x038c:
            java.lang.String r5 = "languageID"
            java.util.List r5 = r1.getDataListFromJsonKey(r5)
            java.lang.Object r5 = r5.get(r3)
            java.lang.String r5 = (java.lang.String) r5
            int r5 = java.lang.Integer.parseInt(r5)
            if (r5 >= 0) goto L_0x03a0
            r6 = -1
            return r6
        L_0x03a0:
            com.wits.pms.custom.KswSettings$3 r6 = new com.wits.pms.custom.KswSettings$3
            r6.<init>()
            java.lang.String r7 = "KswSettings"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Change Language@"
            r8.append(r9)
            java.lang.Object r9 = r6.get(r5)
            java.util.Locale r9 = (java.util.Locale) r9
            java.lang.String r9 = r9.getLanguage()
            r8.append(r9)
            java.lang.String r9 = " @index="
            r8.append(r9)
            r8.append(r5)
            java.lang.String r8 = r8.toString()
            android.util.Log.i(r7, r8)
            java.lang.Object r7 = r6.get(r5)
            java.util.Locale r7 = (java.util.Locale) r7
            com.wits.pms.utils.LanguageUtil.changeSystemLanguage(r7)
            goto L_0x05ab
        L_0x03d8:
            java.lang.String r5 = "EQ_mode"
            int r5 = r1.getSettingsInt(r5)     // Catch:{ SettingNotFoundException -> 0x045b }
            byte r5 = (byte) r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r8 = 115(0x73, float:1.61E-43)
            switch(r5) {
                case 0: goto L_0x0436;
                case 1: goto L_0x0426;
                case 2: goto L_0x0416;
                case 3: goto L_0x0404;
                case 4: goto L_0x03f6;
                case 5: goto L_0x03e6;
                default: goto L_0x03e4;
            }     // Catch:{ SettingNotFoundException -> 0x045b }
        L_0x03e4:
            goto L_0x0459
        L_0x03e6:
            byte[] r6 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r16] = r10     // Catch:{ SettingNotFoundException -> 0x045b }
            r10 = 11
            r6[r9] = r10     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r15] = r7     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r14] = r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r1.sendMcu(r8, r6)     // Catch:{ SettingNotFoundException -> 0x045b }
            goto L_0x0459
        L_0x03f6:
            byte[] r7 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x045b }
            r7[r16] = r6     // Catch:{ SettingNotFoundException -> 0x045b }
            r7[r9] = r12     // Catch:{ SettingNotFoundException -> 0x045b }
            r7[r15] = r12     // Catch:{ SettingNotFoundException -> 0x045b }
            r7[r14] = r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r1.sendMcu(r8, r7)     // Catch:{ SettingNotFoundException -> 0x045b }
            goto L_0x0459
        L_0x0404:
            byte[] r6 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x045b }
            r7 = 15
            r6[r16] = r7     // Catch:{ SettingNotFoundException -> 0x045b }
            r7 = 11
            r6[r9] = r7     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r15] = r10     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r14] = r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r1.sendMcu(r8, r6)     // Catch:{ SettingNotFoundException -> 0x045b }
            goto L_0x0459
        L_0x0416:
            byte[] r10 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x045b }
            r10[r16] = r7     // Catch:{ SettingNotFoundException -> 0x045b }
            r10[r9] = r6     // Catch:{ SettingNotFoundException -> 0x045b }
            r6 = 15
            r10[r15] = r6     // Catch:{ SettingNotFoundException -> 0x045b }
            r10[r14] = r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r1.sendMcu(r8, r10)     // Catch:{ SettingNotFoundException -> 0x045b }
            goto L_0x0459
        L_0x0426:
            byte[] r6 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r16] = r12     // Catch:{ SettingNotFoundException -> 0x045b }
            r7 = 9
            r6[r9] = r7     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r15] = r12     // Catch:{ SettingNotFoundException -> 0x045b }
            r6[r14] = r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r1.sendMcu(r8, r6)     // Catch:{ SettingNotFoundException -> 0x045b }
            goto L_0x0459
        L_0x0436:
            java.lang.String r6 = "Bass_value"
            int r6 = r1.getSettingsInt(r6)     // Catch:{ SettingNotFoundException -> 0x045b }
            byte r6 = (byte) r6     // Catch:{ SettingNotFoundException -> 0x045b }
            java.lang.String r7 = "Middle_value"
            int r7 = r1.getSettingsInt(r7)     // Catch:{ SettingNotFoundException -> 0x045b }
            byte r7 = (byte) r7     // Catch:{ SettingNotFoundException -> 0x045b }
            java.lang.String r10 = "Treble_value"
            int r10 = r1.getSettingsInt(r10)     // Catch:{ SettingNotFoundException -> 0x045b }
            byte r10 = (byte) r10     // Catch:{ SettingNotFoundException -> 0x045b }
            byte[] r11 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x045b }
            r11[r16] = r6     // Catch:{ SettingNotFoundException -> 0x045b }
            r11[r9] = r7     // Catch:{ SettingNotFoundException -> 0x045b }
            r11[r15] = r10     // Catch:{ SettingNotFoundException -> 0x045b }
            r11[r14] = r5     // Catch:{ SettingNotFoundException -> 0x045b }
            r1.sendMcu(r8, r11)     // Catch:{ SettingNotFoundException -> 0x045b }
        L_0x0459:
            goto L_0x05ab
        L_0x045b:
            r0 = move-exception
            goto L_0x05ab
        L_0x045e:
            int r5 = r3 + 1
            float r5 = (float) r5
            r6 = 1092616192(0x41200000, float:10.0)
            float r5 = r5 / r6
            android.content.Context r6 = r1.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r7 = "NaviMix"
            android.provider.Settings.System.putFloat(r6, r7, r5)
            goto L_0x05ab
        L_0x0471:
            java.lang.String r5 = "TimeSyncSoucrce"
            int r5 = r1.getSettingsInt(r5)     // Catch:{ SettingNotFoundException -> 0x04b3 }
            if (r5 != 0) goto L_0x04a4
            android.content.Context r5 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x04b3 }
            android.content.ContentResolver r5 = r5.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x04b3 }
            java.lang.String r6 = "time_12_24"
            if (r3 != r9) goto L_0x0486
            java.lang.String r7 = "12"
            goto L_0x0488
        L_0x0486:
            java.lang.String r7 = "24"
        L_0x0488:
            android.provider.Settings.System.putString(r5, r6, r7)     // Catch:{ SettingNotFoundException -> 0x04b3 }
            android.content.Intent r5 = new android.content.Intent     // Catch:{ SettingNotFoundException -> 0x04b3 }
            java.lang.String r6 = "android.intent.action.TIME_SET"
            r5.<init>(r6)     // Catch:{ SettingNotFoundException -> 0x04b3 }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x04b3 }
            android.content.Context r7 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x04b3 }
            android.content.pm.ApplicationInfo r7 = r7.getApplicationInfo()     // Catch:{ SettingNotFoundException -> 0x04b3 }
            int r7 = r7.uid     // Catch:{ SettingNotFoundException -> 0x04b3 }
            android.os.UserHandle r7 = android.os.UserHandle.getUserHandleForUid(r7)     // Catch:{ SettingNotFoundException -> 0x04b3 }
            r6.sendBroadcastAsUser(r5, r7)     // Catch:{ SettingNotFoundException -> 0x04b3 }
            goto L_0x04b1
        L_0x04a4:
            byte[] r5 = new byte[r15]     // Catch:{ SettingNotFoundException -> 0x04b3 }
            r6 = 15
            r5[r16] = r6     // Catch:{ SettingNotFoundException -> 0x04b3 }
            r5[r9] = r4     // Catch:{ SettingNotFoundException -> 0x04b3 }
            r6 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r6, r5)     // Catch:{ SettingNotFoundException -> 0x04b3 }
        L_0x04b1:
            goto L_0x05ab
        L_0x04b3:
            r0 = move-exception
            goto L_0x05ab
        L_0x04b6:
            byte[] r5 = new byte[r15]
            r5[r16] = r12
            r5[r9] = r4
            r6 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r6, r5)
            android.content.Context r5 = r1.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r6 = "auto_time"
            if (r4 != r9) goto L_0x04cd
            r9 = 0
        L_0x04cd:
            android.provider.Settings.Global.putInt(r5, r6, r9)
            goto L_0x05ab
        L_0x04d2:
            r5 = 98
            byte[] r6 = new byte[r11]
            r6[r16] = r16
            r6[r9] = r15
            r6[r15] = r14
            r6[r14] = r4
            boolean r7 = r1.isSyncStatus
            byte r7 = (byte) r7
            r6[r13] = r7
            r1.sendMcu(r5, r6)
            goto L_0x05ab
        L_0x04e8:
            r5 = 98
            byte[] r6 = new byte[r11]
            r6[r16] = r16
            r6[r9] = r15
            r6[r15] = r15
            r6[r14] = r4
            boolean r7 = r1.isSyncStatus
            byte r7 = (byte) r7
            r6[r13] = r7
            r1.sendMcu(r5, r6)
            goto L_0x05ab
        L_0x04fe:
            r5 = 98
            byte[] r6 = new byte[r11]
            r6[r16] = r16
            r6[r9] = r9
            r6[r15] = r15
            r6[r14] = r4
            boolean r7 = r1.isSyncStatus
            byte r7 = (byte) r7
            r6[r13] = r7
            r1.sendMcu(r5, r6)
            goto L_0x05ab
        L_0x0514:
            r5 = 98
            byte[] r6 = new byte[r11]
            r6[r16] = r16
            r6[r9] = r9
            r6[r15] = r9
            r6[r14] = r4
            boolean r7 = r1.isSyncStatus
            byte r7 = (byte) r7
            r6[r13] = r7
            r1.sendMcu(r5, r6)
            goto L_0x05ab
        L_0x052a:
            int r5 = r3 * 1023
            int r5 = r5 / 100
            r6 = 10
            r7 = 255(0xff, float:3.57E-43)
            int r5 = com.wits.pms.mcu.custom.utils.BrightnessUtils.convertGammaToLinear(r5, r6, r7)
            android.content.Context r6 = r1.mContext
            android.content.ContentResolver r6 = r6.getContentResolver()
            java.lang.String r7 = "screen_brightness"
            android.provider.Settings.System.putInt(r6, r7, r5)
            boolean r6 = r1.isSyncStatus
            if (r6 == 0) goto L_0x05ab
            com.wits.pms.core.CenterControlImpl r6 = com.wits.pms.core.CenterControlImpl.getImpl()
            r6.setBrightness(r3)
            goto L_0x05ab
        L_0x054d:
            byte[] r5 = new byte[r15]
            r6 = 11
            r5[r16] = r6
            r5[r9] = r4
            r6 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r6, r5)
            goto L_0x05ab
        L_0x055b:
            r6 = 106(0x6a, float:1.49E-43)
            byte[] r5 = new byte[r15]
            r7 = 20
            r5[r16] = r7
            r5[r9] = r4
            r1.sendMcu(r6, r5)
            goto L_0x05ab
        L_0x0569:
            byte[] r5 = new byte[r15]
            r6 = 8
            r5[r16] = r6
            r5[r9] = r4
            r1.sendMcu(r8, r5)
            goto L_0x05ab
        L_0x0575:
            byte[] r5 = new byte[r15]
            r6 = 14
            r5[r16] = r6
            r5[r9] = r4
            r6 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r6, r5)
            goto L_0x05ab
        L_0x0583:
            r6 = 106(0x6a, float:1.49E-43)
            byte[] r5 = new byte[r15]
            r7 = 23
            r5[r16] = r7
            r5[r9] = r4
            r1.sendMcu(r6, r5)
            goto L_0x05ab
        L_0x0591:
            r6 = 106(0x6a, float:1.49E-43)
            byte[] r5 = new byte[r15]
            r7 = 22
            r5[r16] = r7
            r5[r9] = r4
            r1.sendMcu(r6, r5)
            goto L_0x05ab
        L_0x059f:
            r6 = 106(0x6a, float:1.49E-43)
            byte[] r5 = new byte[r15]
            r5[r16] = r9
            r5[r9] = r4
            r1.sendMcu(r6, r5)
        L_0x05ab:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.custom.KswSettings.handleConfig(java.lang.String, int):int");
    }

    private void reboot() {
        this.mHandler.postDelayed(KswSettings$$Lambda$0.$instance, 500);
    }

    private boolean checkUi() {
        if (new File(uiSave).exists()) {
            return !TextUtils.isEmpty(getSaveUiName());
        }
        return false;
    }

    private String getSaveUiName() {
        return SysConfigUtil.getArg(uiSave);
    }

    public List<String> getDataListFromJsonKey(String key) {
        return (List) new Gson().fromJson(Settings.System.getString(this.mContext.getContentResolver(), key), new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    private class InitConfigRunnable implements Runnable {
        private boolean addFactorySettingsLocal;
        boolean canType;
        boolean carType;
        ArrayList<String> codeList;
        ArrayList<String> dataList;
        boolean dvrList;
        boolean factory;
        ArrayList<String> idList;
        boolean language;
        private File mConfig;
        boolean naviList;
        boolean setting;
        boolean uiType;

        private InitConfigRunnable() {
            this.dataList = new ArrayList<>();
            this.idList = new ArrayList<>();
            this.codeList = new ArrayList<>();
            this.setting = false;
            this.factory = false;
            this.naviList = false;
            this.carType = false;
            this.canType = false;
            this.language = false;
            this.uiType = false;
            this.dvrList = false;
        }

        public void setFilePath(File configFile) {
            this.mConfig = configFile;
        }

        private void clearData() {
            KswSettings.this.saveIntKeyToSp(BuildConfig.FLAVOR);
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.STRING_KEY_MAP, BuildConfig.FLAVOR);
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.CAN_ID, BuildConfig.FLAVOR);
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.CAR_DISPLAY_ID, BuildConfig.FLAVOR);
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.LANGUAGE_ID, BuildConfig.FLAVOR);
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.CODE_LIST, BuildConfig.FLAVOR);
            this.addFactorySettingsLocal = FactorySettings.getFactorySettings() == null;
            Log.i("FactorySettings", "local factory data - " + this.addFactorySettingsLocal);
            if (this.addFactorySettingsLocal) {
                FactorySettings unused = KswSettings.this.mFactorySettings = new FactorySettings();
            } else {
                FactorySettings unused2 = KswSettings.this.mFactorySettings = FactorySettings.getFactorySettings();
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:88:0x02c1 A[Catch:{ Exception -> 0x0372 }] */
        /* JADX WARNING: Removed duplicated region for block: B:89:0x02c5 A[Catch:{ Exception -> 0x0372 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r11 = this;
                r11.clearData()
                java.io.File r0 = r11.mConfig     // Catch:{ Exception -> 0x0372 }
                boolean r0 = r0.exists()     // Catch:{ Exception -> 0x0372 }
                if (r0 != 0) goto L_0x000c
                return
            L_0x000c:
                java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Exception -> 0x0372 }
                java.io.File r1 = r11.mConfig     // Catch:{ Exception -> 0x0372 }
                r0.<init>(r1)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r1 = r0.getEncoding()     // Catch:{ Exception -> 0x0372 }
                org.xmlpull.v1.XmlPullParserFactory r2 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch:{ Exception -> 0x0372 }
                org.xmlpull.v1.XmlPullParser r2 = r2.newPullParser()     // Catch:{ Exception -> 0x0372 }
                java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0372 }
                java.io.File r4 = r11.mConfig     // Catch:{ Exception -> 0x0372 }
                r3.<init>(r4)     // Catch:{ Exception -> 0x0372 }
                r2.setInput(r3, r1)     // Catch:{ Exception -> 0x0372 }
                int r3 = r2.getEventType()     // Catch:{ Exception -> 0x0372 }
                com.google.gson.Gson r4 = new com.google.gson.Gson     // Catch:{ Exception -> 0x0372 }
                r4.<init>()     // Catch:{ Exception -> 0x0372 }
            L_0x0032:
                r5 = 0
                r6 = 1
                if (r3 == r6) goto L_0x0336
                if (r3 == 0) goto L_0x032e
                switch(r3) {
                    case 2: goto L_0x0178;
                    case 3: goto L_0x003d;
                    default: goto L_0x003b;
                }     // Catch:{ Exception -> 0x0372 }
            L_0x003b:
                goto L_0x032f
            L_0x003d:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "setings"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x004d
                r11.setting = r5     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x004d:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "factory"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x005d
                r11.factory = r5     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x005d:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportNaviAppList"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x007f
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportNaviAppList"
                java.util.ArrayList<java.lang.String> r8 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                r11.naviList = r5     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x007f:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CarDisplayParam"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x00b3
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CarDisplayParam"
                java.util.ArrayList<java.lang.String> r8 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CarDisplayParamID"
                java.util.ArrayList<java.lang.String> r8 = r11.idList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                r11.carType = r5     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.idList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x00b3:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CANBusProtocol"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x00ee
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CANBusProtocol"
                java.util.ArrayList<java.lang.String> r8 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CANBusProtocolID"
                java.util.ArrayList<java.lang.String> r8 = r11.idList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r7 = r11.idList     // Catch:{ Exception -> 0x0372 }
                r6.setUpProtocolForInit(r7)     // Catch:{ Exception -> 0x0372 }
                r11.canType = r5     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.idList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x00ee:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportLanguageList"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x0134
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportLanguageList"
                java.util.ArrayList<java.lang.String> r8 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "codeList"
                java.util.ArrayList<java.lang.String> r8 = r11.codeList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "languageID"
                java.util.ArrayList<java.lang.String> r8 = r11.idList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                r11.language = r5     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.idList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.codeList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x0134:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportUIList"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x0156
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportUIList"
                java.util.ArrayList<java.lang.String> r8 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                r11.uiType = r5     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                r5.clear()     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x0156:
                java.lang.String r6 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportDvrAppList"
                boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r6 == 0) goto L_0x032f
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportDvrAppList"
                java.util.ArrayList<java.lang.String> r8 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r4.toJson((java.lang.Object) r8)     // Catch:{ Exception -> 0x0372 }
                r6.saveJson(r7, r8)     // Catch:{ Exception -> 0x0372 }
                java.util.ArrayList<java.lang.String> r6 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                r6.clear()     // Catch:{ Exception -> 0x0372 }
                r11.dvrList = r5     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x0178:
                boolean r7 = r11.setting     // Catch:{ Exception -> 0x0372 }
                if (r7 != 0) goto L_0x0213
                boolean r7 = r11.factory     // Catch:{ Exception -> 0x0372 }
                if (r7 == 0) goto L_0x0182
                goto L_0x0213
            L_0x0182:
                boolean r7 = r11.naviList     // Catch:{ Exception -> 0x0372 }
                if (r7 != 0) goto L_0x0202
                boolean r7 = r11.dvrList     // Catch:{ Exception -> 0x0372 }
                if (r7 == 0) goto L_0x018c
                goto L_0x0202
            L_0x018c:
                boolean r7 = r11.canType     // Catch:{ Exception -> 0x0372 }
                if (r7 != 0) goto L_0x01e8
                boolean r7 = r11.carType     // Catch:{ Exception -> 0x0372 }
                if (r7 == 0) goto L_0x0195
                goto L_0x01e8
            L_0x0195:
                boolean r5 = r11.language     // Catch:{ Exception -> 0x0372 }
                if (r5 != 0) goto L_0x019d
                boolean r5 = r11.uiType     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02b5
            L_0x019d:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x01ac
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = r2.getAttributeValue(r6)     // Catch:{ Exception -> 0x0372 }
                r5.add(r7)     // Catch:{ Exception -> 0x0372 }
            L_0x01ac:
                java.lang.String r5 = ""
                java.lang.String r7 = "code"
                java.lang.String r5 = r2.getAttributeValue(r5, r7)     // Catch:{ Exception -> 0x0372 }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0372 }
                if (r5 != 0) goto L_0x01c7
                java.util.ArrayList<java.lang.String> r5 = r11.codeList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = ""
                java.lang.String r8 = "code"
                java.lang.String r7 = r2.getAttributeValue(r7, r8)     // Catch:{ Exception -> 0x0372 }
                r5.add(r7)     // Catch:{ Exception -> 0x0372 }
            L_0x01c7:
                boolean r5 = r11.language     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02b5
                java.lang.String r5 = ""
                java.lang.String r7 = "id"
                java.lang.String r5 = r2.getAttributeValue(r5, r7)     // Catch:{ Exception -> 0x0372 }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0372 }
                if (r5 != 0) goto L_0x02b5
                java.util.ArrayList<java.lang.String> r5 = r11.idList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = ""
                java.lang.String r8 = "id"
                java.lang.String r7 = r2.getAttributeValue(r7, r8)     // Catch:{ Exception -> 0x0372 }
                r5.add(r7)     // Catch:{ Exception -> 0x0372 }
                goto L_0x02b5
            L_0x01e8:
                java.lang.String r7 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                if (r7 == 0) goto L_0x01f7
                java.util.ArrayList<java.lang.String> r7 = r11.idList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r5 = r2.getAttributeValue(r5)     // Catch:{ Exception -> 0x0372 }
                r7.add(r5)     // Catch:{ Exception -> 0x0372 }
            L_0x01f7:
                java.util.ArrayList<java.lang.String> r5 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = r2.nextText()     // Catch:{ Exception -> 0x0372 }
                r5.add(r7)     // Catch:{ Exception -> 0x0372 }
                goto L_0x02b5
            L_0x0202:
                java.lang.String r7 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                if (r7 == 0) goto L_0x02b5
                java.util.ArrayList<java.lang.String> r7 = r11.dataList     // Catch:{ Exception -> 0x0372 }
                java.lang.String r5 = r2.getAttributeValue(r5)     // Catch:{ Exception -> 0x0372 }
                r7.add(r5)     // Catch:{ Exception -> 0x0372 }
                goto L_0x02b5
            L_0x0213:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02b5
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "Language"
                boolean r5 = r5.equals(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x0250
                com.wits.pms.custom.KswSettings r5 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0372 }
                r8.<init>()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r9 = ""
                r8.append(r9)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r10 = "languageID"
                java.util.List r9 = r9.getDataListFromJsonKey(r10)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r10 = r2.nextText()     // Catch:{ Exception -> 0x0372 }
                int r9 = r9.indexOf(r10)     // Catch:{ Exception -> 0x0372 }
                r8.append(r9)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0372 }
                r5.saveConfig(r7, r8)     // Catch:{ Exception -> 0x0372 }
                goto L_0x02b5
            L_0x0250:
                boolean r5 = r11.factory     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02a8
                boolean r5 = r11.addFactorySettingsLocal     // Catch:{ Exception -> 0x0372 }
                if (r5 != 0) goto L_0x02a8
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r7 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.FactorySettings r7 = r7.mFactorySettings     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = r7.getStringValue(r5)     // Catch:{ Exception -> 0x0372 }
                if (r7 != 0) goto L_0x0284
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0372 }
                r8.<init>()     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.FactorySettings r9 = r9.mFactorySettings     // Catch:{ Exception -> 0x0372 }
                int r9 = r9.getIntValue(r5)     // Catch:{ Exception -> 0x0372 }
                r8.append(r9)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r9 = ""
                r8.append(r9)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0372 }
                r7 = r8
            L_0x0284:
                java.lang.String r8 = "FactorySettings"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0372 }
                r9.<init>()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r10 = "FactorySettings data key:"
                r9.append(r10)     // Catch:{ Exception -> 0x0372 }
                r9.append(r5)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r10 = " - value:"
                r9.append(r10)     // Catch:{ Exception -> 0x0372 }
                r9.append(r7)     // Catch:{ Exception -> 0x0372 }
                java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0372 }
                android.util.Log.i(r8, r9)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r8 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                r8.saveConfig(r5, r7)     // Catch:{ Exception -> 0x0372 }
                goto L_0x02b5
            L_0x02a8:
                com.wits.pms.custom.KswSettings r5 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r8 = r2.nextText()     // Catch:{ Exception -> 0x0372 }
                r5.saveConfig(r7, r8)     // Catch:{ Exception -> 0x0372 }
            L_0x02b5:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "setings"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02c5
                r11.setting = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x02c5:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "factory"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02d4
                r11.factory = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x02d4:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportNaviAppList"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02e3
                r11.naviList = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x02e3:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CarDisplayParam"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x02f2
                r11.carType = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x02f2:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "CANBusProtocol"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x0301
                r11.canType = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x0301:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportLanguageList"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x0310
                r11.language = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x0310:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportUIList"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x031f
                r11.uiType = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x031f:
                java.lang.String r5 = r2.getName()     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "SupportDvrAppList"
                boolean r5 = r5.equalsIgnoreCase(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 == 0) goto L_0x032f
                r11.dvrList = r6     // Catch:{ Exception -> 0x0372 }
                goto L_0x032f
            L_0x032e:
            L_0x032f:
                int r5 = r2.next()     // Catch:{ Exception -> 0x0372 }
                r3 = r5
                goto L_0x0032
            L_0x0336:
                com.wits.pms.custom.KswSettings r7 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                boolean unused = r7.onlyLanguageId = r5     // Catch:{ Exception -> 0x0372 }
                java.io.File r5 = r11.mConfig     // Catch:{ Exception -> 0x0372 }
                java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r7 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.io.File r7 = r7.defaultConfig     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ Exception -> 0x0372 }
                boolean r5 = r5.equals(r7)     // Catch:{ Exception -> 0x0372 }
                if (r5 != 0) goto L_0x036a
                java.io.File r5 = r11.mConfig     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r7 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.io.File r7 = r7.defaultConfig     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.utils.CopyFile.copyTo(r5, r7)     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings r5 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                android.os.Handler r5 = r5.mHandler     // Catch:{ Exception -> 0x0372 }
                com.wits.pms.custom.KswSettings$InitConfigRunnable$$Lambda$0 r7 = new com.wits.pms.custom.KswSettings$InitConfigRunnable$$Lambda$0     // Catch:{ Exception -> 0x0372 }
                r7.<init>(r11)     // Catch:{ Exception -> 0x0372 }
                r5.post(r7)     // Catch:{ Exception -> 0x0372 }
            L_0x036a:
                com.wits.pms.custom.KswSettings r5 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x0372 }
                java.lang.String r7 = "initKswConfig"
                r5.setInt(r7, r6)     // Catch:{ Exception -> 0x0372 }
                goto L_0x037a
            L_0x0372:
                r0 = move-exception
                java.lang.String r1 = "KswSettings"
                java.lang.String r2 = "error"
                android.util.Log.e(r1, r2, r0)
            L_0x037a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.custom.KswSettings.InitConfigRunnable.run():void");
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$run$0$KswSettings$InitConfigRunnable() {
            Toast.makeText(KswSettings.this.mContext, R.string.import_config_success, 1).show();
        }
    }

    private void checkDirectUpdate() {
        new Thread() {
            public void run() {
                int i = 0;
                while (i < 150) {
                    if (KswSettings.this.directUpdateFile == null || !KswSettings.this.directUpdateFile.exists()) {
                        i++;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        KswSettings.this.updateConfig(KswSettings.this.directUpdateFile);
                        return;
                    }
                }
            }
        }.start();
    }

    public void check(Context context, String path) {
        File file = new File(path + "/OEM/factory_config.xml");
        if (file.exists()) {
            Log.i(TAG, "Checking factory_config.xml go to update: " + file.getAbsolutePath());
            this.directUpdateFile = file;
        }
    }

    public void setUpProtocolForMcuListen(int id) {
        setString("ProtocolData", id + BuildConfig.FLAVOR);
        List<String> protocolID = getDataListFromJsonKey(CAN_ID);
        if (protocolID != null) {
            setInt(PROTOCOL_KEY, protocolID.indexOf(id + BuildConfig.FLAVOR));
        }
    }

    /* access modifiers changed from: private */
    public void setUpProtocolForInit(ArrayList<String> idList) {
        String protocolData = getSettingsString("ProtocolData");
        if (!TextUtils.isEmpty(protocolData)) {
            setInt(PROTOCOL_KEY, idList.indexOf(protocolData));
        }
    }

    /* access modifiers changed from: private */
    public void saveConfig(String key, String value) {
        if (!this.onlyLanguageId) {
            char c = 65535;
            switch (key.hashCode()) {
                case -1548945544:
                    if (key.equals("Language")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1357712437:
                    if (key.equals("client")) {
                        c = 2;
                        break;
                    }
                    break;
                case -899959685:
                    if (key.equals("NaviApp")) {
                        c = 0;
                        break;
                    }
                    break;
                case -533217944:
                    if (key.equals("TXZ_Wakeup")) {
                        c = 5;
                        break;
                    }
                    break;
                case 116643:
                    if (key.equals("ver")) {
                        c = 1;
                        break;
                    }
                    break;
                case 309639685:
                    if (key.equals("UI_type")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1216985755:
                    if (key.equals("password")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1468806158:
                    if (key.equals("DVRApk_PackageName")) {
                        c = 4;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    setString(key, value);
                    return;
                case 7:
                    try {
                        setInt(key, Integer.parseInt(value));
                        return;
                    } catch (Exception e) {
                        setString(key, value);
                        return;
                    }
                default:
                    try {
                        setInt(key, Integer.parseInt(value));
                        return;
                    } catch (Exception e2) {
                        return;
                    }
            }
        }
    }
}
