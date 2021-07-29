package com.wits.pms.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.utils.BrightnessUtils;
import com.wits.pms.utils.SysConfigUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<String> intKeys = null;
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
    private Set<String> stringKeys = null;

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
        this.mInitConfigRunnable = new InitConfigRunnable();
        buildKswSettings();
    }

    private void buildKswSettings() {
        this.stringKeys = new HashSet(getStringKeysFromSp());
        this.intKeys = new HashSet(getIntKeysFromSp());
        Log.i(TAG, "stringKeys size:" + this.stringKeys.size());
        Log.i(TAG, "intKeys size:" + this.intKeys.size());
        if (this.stringKeys == null) {
            this.stringKeys = new HashSet();
        }
        if (this.intKeys == null) {
            this.intKeys = new HashSet();
        }
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

    public SharedPreferences getSettingsSp() {
        return this.mContext.createDeviceProtectedStorageContext().getSharedPreferences("kswSettings", 0);
    }

    public Set<String> getStringKeysFromSp() {
        return getSettingsSp().getStringSet(STRING_KEY_MAP, new HashSet());
    }

    public Set<String> getIntKeysFromSp() {
        return getSettingsSp().getStringSet(INT_KEY_MAP, new HashSet());
    }

    public void clearKeys() {
        getSettingsSp().edit().clear().apply();
    }

    /* access modifiers changed from: private */
    public void saveIntKeyToSp(String keyMap) {
        String keys1 = keyMap;
        String keys2 = "";
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
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true, new ContentObserver(this.mHandler) {
            public void onChange(boolean selfChange) {
                try {
                    int progress = (int) Math.round(100.0d * BrightnessUtils.getPercentage((double) BrightnessUtils.convertLinearToGamma(Settings.System.getInt(KswSettings.this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS), 10, 255), 0, 1023));
                    Settings.System.putInt(KswSettings.this.mContext.getContentResolver(), "Brightness", progress);
                    CenterControlImpl.getImpl().setBrightness(progress);
                } catch (Settings.SettingNotFoundException e) {
                }
            }
        });
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24), true, new ContentObserver(this.mHandler) {
            public void onChange(boolean selfChange) {
                KswSettings.this.setInt("TimeFormat", "12".equals(KswSettings.this.getSettingsString(Settings.System.TIME_12_24)) ? 1 : 0);
            }
        });
    }

    public int getSettingsInt(String key) throws Settings.SettingNotFoundException {
        return Settings.System.getInt(this.mContext.getContentResolver(), key);
    }

    public int getSettingsInt(String key, int defaultInt) {
        return Settings.System.getInt(this.mContext.getContentResolver(), key, defaultInt);
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
        Log.i(TAG, "saveIntKey key:" + key);
        this.intKeys.add(key);
        HashSet<String> intSetSave = new HashSet<>(getIntKeysFromSp());
        intSetSave.add(key);
        getSettingsSp().edit().putStringSet(INT_KEY_MAP, intSetSave).apply();
    }

    private void saveStringKey(String key) {
        Log.i(TAG, "saveStringKey key:" + key);
        this.stringKeys.add(key);
        HashSet<String> stringSetSave = new HashSet<>(getStringKeysFromSp());
        stringSetSave.add(key);
        getSettingsSp().edit().putStringSet(STRING_KEY_MAP, stringSetSave).apply();
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
        PowerManager pm = (PowerManager) this.mContext.getSystemService(Context.POWER_SERVICE);
        if (pm != null) {
            pm.reboot("");
        }
    }

    private void fixVersion() {
        if (TextUtils.isEmpty(getSettingsString(LANGUAGE_ID))) {
            Log.i(TAG, "fixVersion");
            this.onlyLanguageId = true;
            initConfig(true);
        }
        if (this.intKeys.size() == 0) {
            Settings.System.putInt(this.mContext.getContentResolver(), "initKswConfig", 0);
            FactorySettings.clearFactorySettings();
            buildKswSettings();
        }
        try {
            if (this.mFactorySettings.getDashboard_Select() != getSettingsInt("Dashboard_Select")) {
                this.mFactorySettings.saveIntValue("Dashboard_Select", getSettingsInt("Dashboard_Select"));
            }
        } catch (Settings.SettingNotFoundException e) {
        }
    }

    public void syncStatus() {
        Log.i(TAG, "syncStatus to Mcu");
        this.isSyncStatus = true;
        this.mFactorySettings = FactorySettings.getFactorySettings();
        boolean fixLocalFactory = false;
        if (this.mFactorySettings == null) {
            Log.i("FactorySettings", "fix missing local factory settings file");
            fixLocalFactory = true;
            this.mFactorySettings = new FactorySettings();
        }
        Set<String> intKeys2 = getIntKeysFromSp();
        Set<String> stringKeys2 = getStringKeysFromSp();
        if (intKeys2.size() != 0 && stringKeys2.size() != 0) {
            for (String key : intKeys2) {
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
            for (String key2 : stringKeys2) {
                if (!key2.contains("UI")) {
                    String value2 = getSettingsString(key2);
                    handleConfig(key2, value2);
                    if (fixLocalFactory) {
                        Log.i("FactorySettings", "fix key:" + key2 + "-value:" + value2);
                        this.mFactorySettings.saveStringValue(key2, value2);
                    }
                }
            }
            handleConfig("Brightness", getSettingsInt("Brightness", 60));
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
            r0 = r2
            goto L_0x0044
        L_0x002f:
            java.lang.String r0 = "TXZ_Wakeup"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = r4
            goto L_0x0044
        L_0x0039:
            java.lang.String r0 = "Language"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0043
            r0 = r3
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
            java.lang.String r0 = ""
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
            r3 = r4
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
    private int handleConfig(java.lang.String r17, int r18) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            byte r4 = (byte) r3
            int r0 = r17.hashCode()
            r5 = 19
            r6 = 17
            r7 = 13
            r9 = 5
            r10 = 16
            r11 = 4
            r12 = 3
            r13 = 2
            r14 = 0
            r15 = 1
            switch(r0) {
                case -2113780075: goto L_0x024a;
                case -1997186328: goto L_0x023f;
                case -1885010620: goto L_0x0234;
                case -1793262372: goto L_0x0229;
                case -1653340047: goto L_0x021e;
                case -1548945544: goto L_0x0213;
                case -1528907729: goto L_0x0208;
                case -1528907728: goto L_0x01fd;
                case -1257575528: goto L_0x01f3;
                case -1181891355: goto L_0x01e8;
                case -1103831850: goto L_0x01dc;
                case -952414302: goto L_0x01d0;
                case -924519752: goto L_0x01c4;
                case -899948362: goto L_0x01b9;
                case -801026034: goto L_0x01ad;
                case -776702634: goto L_0x01a2;
                case -738352606: goto L_0x0196;
                case -682449643: goto L_0x018a;
                case -677297959: goto L_0x017e;
                case -660995341: goto L_0x0172;
                case -602992886: goto L_0x0167;
                case -554769949: goto L_0x015b;
                case -519600940: goto L_0x0150;
                case -294129095: goto L_0x0144;
                case -220630678: goto L_0x0139;
                case -183099962: goto L_0x012d;
                case -114997240: goto L_0x0121;
                case 88063465: goto L_0x0116;
                case 90414126: goto L_0x010b;
                case 106858821: goto L_0x0100;
                case 214368532: goto L_0x00f4;
                case 241352631: goto L_0x00e8;
                case 252039837: goto L_0x00dc;
                case 340057703: goto L_0x00d1;
                case 400825784: goto L_0x00c5;
                case 442866595: goto L_0x00b9;
                case 642405998: goto L_0x00ad;
                case 940906279: goto L_0x00a1;
                case 985261490: goto L_0x0095;
                case 1010516114: goto L_0x0089;
                case 1031815915: goto L_0x007d;
                case 1060762673: goto L_0x0071;
                case 1071490530: goto L_0x0065;
                case 1195313274: goto L_0x0059;
                case 1374371814: goto L_0x004d;
                case 1533443583: goto L_0x0041;
                case 1598142665: goto L_0x0036;
                case 1857618170: goto L_0x002a;
                case 2103778808: goto L_0x001e;
                default: goto L_0x001c;
            }
        L_0x001c:
            goto L_0x0255
        L_0x001e:
            java.lang.String r0 = "DashBoardUnit"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 25
            goto L_0x0256
        L_0x002a:
            java.lang.String r0 = "cam360_video"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 38
            goto L_0x0256
        L_0x0036:
            java.lang.String r0 = "Front_view_camera"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r9
            goto L_0x0256
        L_0x0041:
            java.lang.String r0 = "benz_aux_switch"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 47
            goto L_0x0256
        L_0x004d:
            java.lang.String r0 = "Treble_value"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 20
            goto L_0x0256
        L_0x0059:
            java.lang.String r0 = "FuelUnit"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 8
            goto L_0x0256
        L_0x0065:
            java.lang.String r0 = "TimeSyncSoucrce"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 14
            goto L_0x0256
        L_0x0071:
            java.lang.String r0 = "Bass_value"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 18
            goto L_0x0256
        L_0x007d:
            java.lang.String r0 = "DirtTravelSelection"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 23
            goto L_0x0256
        L_0x0089:
            java.lang.String r0 = "Android_phone_vol"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 11
            goto L_0x0256
        L_0x0095:
            java.lang.String r0 = "Voice_key"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 35
            goto L_0x0256
        L_0x00a1:
            java.lang.String r0 = "BT_Type"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 28
            goto L_0x0256
        L_0x00ad:
            java.lang.String r0 = "CarDisplay"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 32
            goto L_0x0256
        L_0x00b9:
            java.lang.String r0 = "USB_HOST"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 45
            goto L_0x0256
        L_0x00c5:
            java.lang.String r0 = "touch_continuous_send"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 48
            goto L_0x0256
        L_0x00d1:
            java.lang.String r0 = "Middle_value"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r5
            goto L_0x0256
        L_0x00dc:
            java.lang.String r0 = "CCC_IDrive"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 33
            goto L_0x0256
        L_0x00e8:
            java.lang.String r0 = "Car_phone_vol"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 12
            goto L_0x0256
        L_0x00f4:
            java.lang.String r0 = "HandsetAutomaticSelect"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 34
            goto L_0x0256
        L_0x0100:
            java.lang.String r0 = "RearCamType"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 6
            goto L_0x0256
        L_0x010b:
            java.lang.String r0 = "ShowTrack"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r15
            goto L_0x0256
        L_0x0116:
            java.lang.String r0 = "ShowRadar"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r13
            goto L_0x0256
        L_0x0121:
            java.lang.String r0 = "Android_media_vol"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 10
            goto L_0x0256
        L_0x012d:
            java.lang.String r0 = "Support_TXZ"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 46
            goto L_0x0256
        L_0x0139:
            java.lang.String r0 = "DoNotPlayVideosWhileDriving"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r12
            goto L_0x0256
        L_0x0144:
            java.lang.String r0 = "DVR_Type"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 29
            goto L_0x0256
        L_0x0150:
            java.lang.String r0 = "ReversingMuteSelect"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r11
            goto L_0x0256
        L_0x015b:
            java.lang.String r0 = "Mode_key"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 37
            goto L_0x0256
        L_0x0167:
            java.lang.String r0 = "RearCamMirror"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r14
            goto L_0x0256
        L_0x0172:
            java.lang.String r0 = "OLDBMWX"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 44
            goto L_0x0256
        L_0x017e:
            java.lang.String r0 = "Default_PowerBoot"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 42
            goto L_0x0256
        L_0x018a:
            java.lang.String r0 = "AMP_Type"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 30
            goto L_0x0256
        L_0x0196:
            java.lang.String r0 = "CarAux_auto_method"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 26
            goto L_0x0256
        L_0x01a2:
            java.lang.String r0 = "EQ_mode"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r6
            goto L_0x0256
        L_0x01ad:
            java.lang.String r0 = "AHD_cam_Select"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 43
            goto L_0x0256
        L_0x01b9:
            java.lang.String r0 = "NaviMix"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r10
            goto L_0x0256
        L_0x01c4:
            java.lang.String r0 = "Protocol"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 22
            goto L_0x0256
        L_0x01d0:
            java.lang.String r0 = "Backlight_auto_set"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 39
            goto L_0x0256
        L_0x01dc:
            java.lang.String r0 = "CarVideoDisplayStyle"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 24
            goto L_0x0256
        L_0x01e8:
            java.lang.String r0 = "Car_navi_vol"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = r7
            goto L_0x0256
        L_0x01f3:
            java.lang.String r0 = "TempUnit"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 7
            goto L_0x0256
        L_0x01fd:
            java.lang.String r0 = "CarAuxIndex2"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 41
            goto L_0x0256
        L_0x0208:
            java.lang.String r0 = "CarAuxIndex1"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 40
            goto L_0x0256
        L_0x0213:
            java.lang.String r0 = "Language"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 21
            goto L_0x0256
        L_0x021e:
            java.lang.String r0 = "Brightness"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 9
            goto L_0x0256
        L_0x0229:
            java.lang.String r0 = "Map_key"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 36
            goto L_0x0256
        L_0x0234:
            java.lang.String r0 = "TimeFormat"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 15
            goto L_0x0256
        L_0x023f:
            java.lang.String r0 = "Front_view_camer1a"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 31
            goto L_0x0256
        L_0x024a:
            java.lang.String r0 = "CarAux_Operate"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0255
            r0 = 27
            goto L_0x0256
        L_0x0255:
            r0 = -1
        L_0x0256:
            r8 = 112(0x70, float:1.57E-43)
            switch(r0) {
                case 0: goto L_0x05fc;
                case 1: goto L_0x05ee;
                case 2: goto L_0x05e0;
                case 3: goto L_0x05d2;
                case 4: goto L_0x05c6;
                case 5: goto L_0x05b8;
                case 6: goto L_0x05aa;
                case 7: goto L_0x059c;
                case 8: goto L_0x058e;
                case 9: goto L_0x056a;
                case 10: goto L_0x0554;
                case 11: goto L_0x053e;
                case 12: goto L_0x0528;
                case 13: goto L_0x0512;
                case 14: goto L_0x04f6;
                case 15: goto L_0x04b1;
                case 16: goto L_0x049e;
                case 17: goto L_0x0418;
                case 18: goto L_0x0418;
                case 19: goto L_0x0418;
                case 20: goto L_0x0418;
                case 21: goto L_0x03cc;
                case 22: goto L_0x03a7;
                case 23: goto L_0x039a;
                case 24: goto L_0x037a;
                case 25: goto L_0x036d;
                case 26: goto L_0x0360;
                case 27: goto L_0x0355;
                case 28: goto L_0x034a;
                case 29: goto L_0x033f;
                case 30: goto L_0x0334;
                case 31: goto L_0x0329;
                case 32: goto L_0x031c;
                case 33: goto L_0x0310;
                case 34: goto L_0x0303;
                case 35: goto L_0x02f8;
                case 36: goto L_0x02eb;
                case 37: goto L_0x02de;
                case 38: goto L_0x02d1;
                case 39: goto L_0x02c6;
                case 40: goto L_0x02b9;
                case 41: goto L_0x02ac;
                case 42: goto L_0x029f;
                case 43: goto L_0x0292;
                case 44: goto L_0x0287;
                case 45: goto L_0x0282;
                case 46: goto L_0x0275;
                case 47: goto L_0x026a;
                case 48: goto L_0x025d;
                default: goto L_0x025b;
            }
        L_0x025b:
            goto L_0x0608
        L_0x025d:
            byte[] r0 = new byte[r13]
            r5 = 28
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x026a:
            byte[] r0 = new byte[r13]
            r0[r14] = r10
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0275:
            com.wits.pms.core.CenterControlImpl r0 = com.wits.pms.core.CenterControlImpl.getImpl()
            if (r4 != r15) goto L_0x027d
            r14 = r15
        L_0x027d:
            r0.setTxzSwitch(r14)
            goto L_0x0608
        L_0x0282:
            com.wits.pms.utils.UsbUtil.updateUsbMode((int) r4)
            goto L_0x0608
        L_0x0287:
            java.lang.String r0 = "CarDisplay"
            if (r4 != r15) goto L_0x028c
            goto L_0x028d
        L_0x028c:
            r14 = r15
        L_0x028d:
            r1.handleConfig((java.lang.String) r0, (int) r14)
            goto L_0x0608
        L_0x0292:
            byte[] r0 = new byte[r13]
            r5 = 20
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x029f:
            byte[] r0 = new byte[r13]
            r5 = 25
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02ac:
            byte[] r0 = new byte[r13]
            r5 = 24
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02b9:
            byte[] r0 = new byte[r13]
            r5 = 23
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02c6:
            byte[] r0 = new byte[r13]
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02d1:
            byte[] r0 = new byte[r13]
            r5 = 18
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02de:
            byte[] r0 = new byte[r13]
            r5 = 22
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02eb:
            byte[] r0 = new byte[r13]
            r5 = 21
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x02f8:
            byte[] r0 = new byte[r13]
            r0[r14] = r6
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0303:
            byte[] r0 = new byte[r13]
            r5 = 9
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0310:
            byte[] r0 = new byte[r13]
            r5 = 6
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x031c:
            byte[] r0 = new byte[r13]
            r5 = 14
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0329:
            byte[] r0 = new byte[r13]
            r0[r14] = r7
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0334:
            byte[] r0 = new byte[r13]
            r0[r14] = r13
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x033f:
            byte[] r0 = new byte[r13]
            r0[r14] = r11
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x034a:
            byte[] r0 = new byte[r13]
            r0[r14] = r9
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0355:
            byte[] r0 = new byte[r13]
            r0[r14] = r12
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x0360:
            byte[] r0 = new byte[r13]
            r5 = 12
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x036d:
            byte[] r0 = new byte[r13]
            r5 = 26
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x037a:
            java.lang.String r0 = "CarDisplayParamID"
            java.util.List r0 = r1.getDataListFromJsonKey(r0)     // Catch:{ Exception -> 0x0397 }
            if (r0 == 0) goto L_0x0395
            java.lang.Object r5 = r0.get(r4)     // Catch:{ Exception -> 0x0397 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x0397 }
            byte r6 = java.lang.Byte.parseByte(r5)     // Catch:{ Exception -> 0x0397 }
            byte[] r7 = new byte[r13]     // Catch:{ Exception -> 0x0397 }
            r7[r14] = r15     // Catch:{ Exception -> 0x0397 }
            r7[r15] = r6     // Catch:{ Exception -> 0x0397 }
            r1.sendMcu(r8, r7)     // Catch:{ Exception -> 0x0397 }
        L_0x0395:
            goto L_0x0608
        L_0x0397:
            r0 = move-exception
            goto L_0x0608
        L_0x039a:
            byte[] r0 = new byte[r13]
            r5 = 27
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x03a7:
            r0 = -1
            if (r4 != r0) goto L_0x03ab
            r4 = 0
        L_0x03ab:
            java.lang.String r0 = "CANBusProtocolID"
            java.util.List r0 = r1.getDataListFromJsonKey(r0)     // Catch:{ Exception -> 0x03c9 }
            if (r0 == 0) goto L_0x03c7
            java.lang.Object r5 = r0.get(r4)     // Catch:{ Exception -> 0x03c9 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x03c9 }
            byte r6 = java.lang.Byte.parseByte(r5)     // Catch:{ Exception -> 0x03c9 }
            byte[] r7 = new byte[r13]     // Catch:{ Exception -> 0x03c9 }
            r9 = 7
            r7[r14] = r9     // Catch:{ Exception -> 0x03c9 }
            r7[r15] = r6     // Catch:{ Exception -> 0x03c9 }
            r1.sendMcu(r8, r7)     // Catch:{ Exception -> 0x03c9 }
        L_0x03c7:
            goto L_0x0608
        L_0x03c9:
            r0 = move-exception
            goto L_0x0608
        L_0x03cc:
            java.lang.String r0 = "languageID"
            java.util.List r0 = r1.getDataListFromJsonKey(r0)
            java.lang.Object r0 = r0.get(r3)
            java.lang.String r0 = (java.lang.String) r0
            int r0 = java.lang.Integer.parseInt(r0)
            if (r0 >= 0) goto L_0x03e0
            r5 = -1
            return r5
        L_0x03e0:
            com.wits.pms.custom.KswSettings$3 r5 = new com.wits.pms.custom.KswSettings$3
            r5.<init>()
            java.lang.String r6 = "KswSettings"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Change Language@"
            r7.append(r8)
            java.lang.Object r8 = r5.get(r0)
            java.util.Locale r8 = (java.util.Locale) r8
            java.lang.String r8 = r8.getLanguage()
            r7.append(r8)
            java.lang.String r8 = " @index="
            r7.append(r8)
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            android.util.Log.i(r6, r7)
            java.lang.Object r6 = r5.get(r0)
            java.util.Locale r6 = (java.util.Locale) r6
            com.wits.pms.utils.LanguageUtil.changeSystemLanguage(r6)
            goto L_0x0608
        L_0x0418:
            java.lang.String r0 = "EQ_mode"
            int r0 = r1.getSettingsInt(r0)     // Catch:{ SettingNotFoundException -> 0x049b }
            byte r0 = (byte) r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r8 = 115(0x73, float:1.61E-43)
            switch(r0) {
                case 0: goto L_0x0476;
                case 1: goto L_0x0466;
                case 2: goto L_0x0456;
                case 3: goto L_0x0444;
                case 4: goto L_0x0436;
                case 5: goto L_0x0426;
                default: goto L_0x0424;
            }     // Catch:{ SettingNotFoundException -> 0x049b }
        L_0x0424:
            goto L_0x0499
        L_0x0426:
            byte[] r7 = new byte[r11]     // Catch:{ SettingNotFoundException -> 0x049b }
            r7[r14] = r6     // Catch:{ SettingNotFoundException -> 0x049b }
            r6 = 11
            r7[r15] = r6     // Catch:{ SettingNotFoundException -> 0x049b }
            r7[r13] = r5     // Catch:{ SettingNotFoundException -> 0x049b }
            r7[r12] = r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r1.sendMcu(r8, r7)     // Catch:{ SettingNotFoundException -> 0x049b }
            goto L_0x0499
        L_0x0436:
            byte[] r5 = new byte[r11]     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r14] = r7     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r15] = r10     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r13] = r10     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r12] = r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r1.sendMcu(r8, r5)     // Catch:{ SettingNotFoundException -> 0x049b }
            goto L_0x0499
        L_0x0444:
            byte[] r5 = new byte[r11]     // Catch:{ SettingNotFoundException -> 0x049b }
            r7 = 15
            r5[r14] = r7     // Catch:{ SettingNotFoundException -> 0x049b }
            r7 = 11
            r5[r15] = r7     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r13] = r6     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r12] = r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r1.sendMcu(r8, r5)     // Catch:{ SettingNotFoundException -> 0x049b }
            goto L_0x0499
        L_0x0456:
            byte[] r6 = new byte[r11]     // Catch:{ SettingNotFoundException -> 0x049b }
            r6[r14] = r5     // Catch:{ SettingNotFoundException -> 0x049b }
            r6[r15] = r7     // Catch:{ SettingNotFoundException -> 0x049b }
            r5 = 15
            r6[r13] = r5     // Catch:{ SettingNotFoundException -> 0x049b }
            r6[r12] = r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r1.sendMcu(r8, r6)     // Catch:{ SettingNotFoundException -> 0x049b }
            goto L_0x0499
        L_0x0466:
            byte[] r5 = new byte[r11]     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r14] = r10     // Catch:{ SettingNotFoundException -> 0x049b }
            r6 = 9
            r5[r15] = r6     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r13] = r10     // Catch:{ SettingNotFoundException -> 0x049b }
            r5[r12] = r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r1.sendMcu(r8, r5)     // Catch:{ SettingNotFoundException -> 0x049b }
            goto L_0x0499
        L_0x0476:
            java.lang.String r5 = "Bass_value"
            int r5 = r1.getSettingsInt(r5)     // Catch:{ SettingNotFoundException -> 0x049b }
            byte r5 = (byte) r5     // Catch:{ SettingNotFoundException -> 0x049b }
            java.lang.String r6 = "Middle_value"
            int r6 = r1.getSettingsInt(r6)     // Catch:{ SettingNotFoundException -> 0x049b }
            byte r6 = (byte) r6     // Catch:{ SettingNotFoundException -> 0x049b }
            java.lang.String r7 = "Treble_value"
            int r7 = r1.getSettingsInt(r7)     // Catch:{ SettingNotFoundException -> 0x049b }
            byte r7 = (byte) r7     // Catch:{ SettingNotFoundException -> 0x049b }
            byte[] r9 = new byte[r11]     // Catch:{ SettingNotFoundException -> 0x049b }
            r9[r14] = r5     // Catch:{ SettingNotFoundException -> 0x049b }
            r9[r15] = r6     // Catch:{ SettingNotFoundException -> 0x049b }
            r9[r13] = r7     // Catch:{ SettingNotFoundException -> 0x049b }
            r9[r12] = r0     // Catch:{ SettingNotFoundException -> 0x049b }
            r1.sendMcu(r8, r9)     // Catch:{ SettingNotFoundException -> 0x049b }
        L_0x0499:
            goto L_0x0608
        L_0x049b:
            r0 = move-exception
            goto L_0x0608
        L_0x049e:
            int r0 = r3 + 1
            float r0 = (float) r0
            r5 = 1092616192(0x41200000, float:10.0)
            float r0 = r0 / r5
            android.content.Context r5 = r1.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r6 = "NaviMix"
            android.provider.Settings.System.putFloat(r5, r6, r0)
            goto L_0x0608
        L_0x04b1:
            java.lang.String r0 = "TimeSyncSoucrce"
            int r0 = r1.getSettingsInt(r0)     // Catch:{ SettingNotFoundException -> 0x04f3 }
            if (r0 != 0) goto L_0x04e4
            android.content.Context r0 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x04f3 }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ SettingNotFoundException -> 0x04f3 }
            java.lang.String r5 = "time_12_24"
            if (r3 != r15) goto L_0x04c6
            java.lang.String r6 = "12"
            goto L_0x04c8
        L_0x04c6:
            java.lang.String r6 = "24"
        L_0x04c8:
            android.provider.Settings.System.putString(r0, r5, r6)     // Catch:{ SettingNotFoundException -> 0x04f3 }
            android.content.Intent r0 = new android.content.Intent     // Catch:{ SettingNotFoundException -> 0x04f3 }
            java.lang.String r5 = "android.intent.action.TIME_SET"
            r0.<init>((java.lang.String) r5)     // Catch:{ SettingNotFoundException -> 0x04f3 }
            android.content.Context r5 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x04f3 }
            android.content.Context r6 = r1.mContext     // Catch:{ SettingNotFoundException -> 0x04f3 }
            android.content.pm.ApplicationInfo r6 = r6.getApplicationInfo()     // Catch:{ SettingNotFoundException -> 0x04f3 }
            int r6 = r6.uid     // Catch:{ SettingNotFoundException -> 0x04f3 }
            android.os.UserHandle r6 = android.os.UserHandle.getUserHandleForUid(r6)     // Catch:{ SettingNotFoundException -> 0x04f3 }
            r5.sendBroadcastAsUser(r0, r6)     // Catch:{ SettingNotFoundException -> 0x04f3 }
            goto L_0x04f1
        L_0x04e4:
            byte[] r0 = new byte[r13]     // Catch:{ SettingNotFoundException -> 0x04f3 }
            r5 = 15
            r0[r14] = r5     // Catch:{ SettingNotFoundException -> 0x04f3 }
            r0[r15] = r4     // Catch:{ SettingNotFoundException -> 0x04f3 }
            r5 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r5, r0)     // Catch:{ SettingNotFoundException -> 0x04f3 }
        L_0x04f1:
            goto L_0x0608
        L_0x04f3:
            r0 = move-exception
            goto L_0x0608
        L_0x04f6:
            byte[] r0 = new byte[r13]
            r0[r14] = r10
            r0[r15] = r4
            r5 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r5, r0)
            android.content.Context r0 = r1.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r5 = "auto_time"
            if (r4 != r15) goto L_0x050c
            goto L_0x050d
        L_0x050c:
            r14 = r15
        L_0x050d:
            android.provider.Settings.Global.putInt(r0, r5, r14)
            goto L_0x0608
        L_0x0512:
            byte[] r0 = new byte[r9]
            r0[r14] = r14
            r0[r15] = r13
            r0[r13] = r12
            r0[r12] = r4
            boolean r5 = r1.isSyncStatus
            byte r5 = (byte) r5
            r0[r11] = r5
            r5 = 98
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x0528:
            byte[] r0 = new byte[r9]
            r0[r14] = r14
            r0[r15] = r13
            r0[r13] = r13
            r0[r12] = r4
            boolean r5 = r1.isSyncStatus
            byte r5 = (byte) r5
            r0[r11] = r5
            r5 = 98
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x053e:
            byte[] r0 = new byte[r9]
            r0[r14] = r14
            r0[r15] = r15
            r0[r13] = r13
            r0[r12] = r4
            boolean r5 = r1.isSyncStatus
            byte r5 = (byte) r5
            r0[r11] = r5
            r5 = 98
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x0554:
            byte[] r0 = new byte[r9]
            r0[r14] = r14
            r0[r15] = r15
            r0[r13] = r15
            r0[r12] = r4
            boolean r5 = r1.isSyncStatus
            byte r5 = (byte) r5
            r0[r11] = r5
            r5 = 98
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x056a:
            int r0 = r3 * 1023
            int r0 = r0 / 100
            r5 = 10
            r6 = 255(0xff, float:3.57E-43)
            int r0 = com.wits.pms.mcu.custom.utils.BrightnessUtils.convertGammaToLinear(r0, r5, r6)
            android.content.Context r5 = r1.mContext
            android.content.ContentResolver r5 = r5.getContentResolver()
            java.lang.String r6 = "screen_brightness"
            android.provider.Settings.System.putInt(r5, r6, r0)
            boolean r5 = r1.isSyncStatus
            if (r5 == 0) goto L_0x0608
            com.wits.pms.core.CenterControlImpl r5 = com.wits.pms.core.CenterControlImpl.getImpl()
            r5.setBrightness(r3)
            goto L_0x0608
        L_0x058e:
            byte[] r0 = new byte[r13]
            r5 = 26
            r0[r14] = r5
            r0[r15] = r4
            r5 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x059c:
            r5 = 106(0x6a, float:1.49E-43)
            byte[] r0 = new byte[r13]
            r6 = 24
            r0[r14] = r6
            r0[r15] = r4
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x05aa:
            r5 = 106(0x6a, float:1.49E-43)
            byte[] r0 = new byte[r13]
            r6 = 11
            r0[r14] = r6
            r0[r15] = r4
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x05b8:
            r5 = 106(0x6a, float:1.49E-43)
            byte[] r0 = new byte[r13]
            r6 = 20
            r0[r14] = r6
            r0[r15] = r4
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x05c6:
            byte[] r0 = new byte[r13]
            r5 = 8
            r0[r14] = r5
            r0[r15] = r4
            r1.sendMcu(r8, r0)
            goto L_0x0608
        L_0x05d2:
            byte[] r0 = new byte[r13]
            r5 = 14
            r0[r14] = r5
            r0[r15] = r4
            r5 = 106(0x6a, float:1.49E-43)
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x05e0:
            r5 = 106(0x6a, float:1.49E-43)
            byte[] r0 = new byte[r13]
            r6 = 23
            r0[r14] = r6
            r0[r15] = r4
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x05ee:
            r5 = 106(0x6a, float:1.49E-43)
            byte[] r0 = new byte[r13]
            r6 = 22
            r0[r14] = r6
            r0[r15] = r4
            r1.sendMcu(r5, r0)
            goto L_0x0608
        L_0x05fc:
            r5 = 106(0x6a, float:1.49E-43)
            byte[] r0 = new byte[r13]
            r0[r14] = r15
            r0[r15] = r4
            r1.sendMcu(r5, r0)
        L_0x0608:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.custom.KswSettings.handleConfig(java.lang.String, int):int");
    }

    private void reboot() {
        this.mHandler.postDelayed($$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E.INSTANCE, 500);
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
            KswSettings.this.saveIntKeyToSp("");
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.STRING_KEY_MAP, "");
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.CAN_ID, "");
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.CAR_DISPLAY_ID, "");
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.LANGUAGE_ID, "");
            Settings.System.putString(KswSettings.this.mContext.getContentResolver(), KswSettings.CODE_LIST, "");
            this.addFactorySettingsLocal = FactorySettings.getFactorySettings() == null;
            Log.i("FactorySettings", "local factory data - " + this.addFactorySettingsLocal);
            if (this.addFactorySettingsLocal) {
                FactorySettings unused = KswSettings.this.mFactorySettings = new FactorySettings();
            } else {
                FactorySettings unused2 = KswSettings.this.mFactorySettings = FactorySettings.getFactorySettings();
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:102:0x03bd A[Catch:{ Exception -> 0x046e }] */
        /* JADX WARNING: Removed duplicated region for block: B:103:0x03c1 A[Catch:{ Exception -> 0x046e }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r15 = this;
                r15.clearData()
                java.io.File r0 = r15.mConfig     // Catch:{ Exception -> 0x046e }
                boolean r0 = r0.exists()     // Catch:{ Exception -> 0x046e }
                if (r0 != 0) goto L_0x000c
                return
            L_0x000c:
                com.wits.pms.custom.KswSettings r0 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.io.File r1 = r15.mConfig     // Catch:{ Exception -> 0x046e }
                byte[] r0 = r0.fileToByteArray(r1)     // Catch:{ Exception -> 0x046e }
                boolean r1 = com.wits.pms.custom.KswSettings.CheckBOM(r0)     // Catch:{ Exception -> 0x046e }
                java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x046e }
                java.io.File r3 = r15.mConfig     // Catch:{ Exception -> 0x046e }
                r2.<init>(r3)     // Catch:{ Exception -> 0x046e }
                java.lang.String r3 = r2.getEncoding()     // Catch:{ Exception -> 0x046e }
                java.lang.String r4 = "KswSettings"
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r5.<init>()     // Catch:{ Exception -> 0x046e }
                java.lang.String r6 = "config  isBom = "
                r5.append(r6)     // Catch:{ Exception -> 0x046e }
                r5.append(r1)     // Catch:{ Exception -> 0x046e }
                java.lang.String r6 = "    encoding  = "
                r5.append(r6)     // Catch:{ Exception -> 0x046e }
                r5.append(r3)     // Catch:{ Exception -> 0x046e }
                java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x046e }
                android.util.Log.d(r4, r5)     // Catch:{ Exception -> 0x046e }
                org.xmlpull.v1.XmlPullParserFactory r4 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch:{ Exception -> 0x046e }
                org.xmlpull.v1.XmlPullParser r4 = r4.newPullParser()     // Catch:{ Exception -> 0x046e }
                java.lang.String r5 = "UTF-8"
                boolean r5 = r3.equals(r5)     // Catch:{ Exception -> 0x046e }
                r6 = 3
                r7 = 0
                if (r5 == 0) goto L_0x0067
                if (r1 == 0) goto L_0x0067
                int r5 = r0.length     // Catch:{ Exception -> 0x046e }
                int r5 = r5 - r6
                byte[] r5 = new byte[r5]     // Catch:{ Exception -> 0x046e }
                int r8 = r0.length     // Catch:{ Exception -> 0x046e }
                int r8 = r8 - r6
                java.lang.System.arraycopy(r0, r6, r5, r7, r8)     // Catch:{ Exception -> 0x046e }
                java.io.ByteArrayInputStream r8 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x046e }
                r8.<init>(r5)     // Catch:{ Exception -> 0x046e }
                r4.setInput(r8, r3)     // Catch:{ Exception -> 0x046e }
                goto L_0x0071
            L_0x0067:
                java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x046e }
                java.io.File r8 = r15.mConfig     // Catch:{ Exception -> 0x046e }
                r5.<init>(r8)     // Catch:{ Exception -> 0x046e }
                r4.setInput(r5, r3)     // Catch:{ Exception -> 0x046e }
            L_0x0071:
                int r5 = r4.getEventType()     // Catch:{ Exception -> 0x046e }
                com.google.gson.Gson r8 = new com.google.gson.Gson     // Catch:{ Exception -> 0x046e }
                r8.<init>()     // Catch:{ Exception -> 0x046e }
            L_0x007a:
                r9 = 1
                if (r5 == r9) goto L_0x0432
                if (r5 == 0) goto L_0x042a
                switch(r5) {
                    case 2: goto L_0x0241;
                    case 3: goto L_0x0084;
                    default: goto L_0x0082;
                }     // Catch:{ Exception -> 0x046e }
            L_0x0082:
                goto L_0x042b
            L_0x0084:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "setings"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x0094
                r15.setting = r7     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x0094:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "factory"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x00a4
                r15.factory = r7     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x00a4:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportNaviAppList"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x00c6
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportNaviAppList"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                r15.naviList = r7     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x00c6:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "CarDisplayParam"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x00fa
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "CarDisplayParam"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "CarDisplayParamID"
                java.util.ArrayList<java.lang.String> r11 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                r15.carType = r7     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.idList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x00fa:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "CANBusProtocol"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x0135
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "CANBusProtocol"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "CANBusProtocolID"
                java.util.ArrayList<java.lang.String> r11 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r10 = r15.idList     // Catch:{ Exception -> 0x046e }
                r9.setUpProtocolForInit(r10)     // Catch:{ Exception -> 0x046e }
                r15.canType = r7     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.idList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x0135:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportLanguageList"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x01fd
                java.lang.String r9 = "KswSettings"
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r10.<init>()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "dataList = "
                r10.append(r11)     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r10.append(r11)     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = " codeList = "
                r10.append(r11)     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r11 = r15.codeList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r10.append(r11)     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "   idList = "
                r10.append(r11)     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r11 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r10.append(r11)     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x046e }
                android.util.Log.d(r9, r10)     // Catch:{ Exception -> 0x046e }
                java.lang.String r9 = android.os.Build.DISPLAY     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "8937"
                boolean r9 = r9.contains(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x01c3
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.idList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "简体中文"
                r9.add(r10)     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "0"
                r9.add(r10)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportLanguageList"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "codeList"
                java.util.ArrayList<java.lang.String> r11 = r15.codeList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "languageID"
                java.util.ArrayList<java.lang.String> r11 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                goto L_0x01ea
            L_0x01c3:
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportLanguageList"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "codeList"
                java.util.ArrayList<java.lang.String> r11 = r15.codeList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "languageID"
                java.util.ArrayList<java.lang.String> r11 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
            L_0x01ea:
                r15.language = r7     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.idList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.codeList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x01fd:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportUIList"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x021f
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportUIList"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                r15.uiType = r7     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x021f:
                java.lang.String r9 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportDvrAppList"
                boolean r9 = r9.equalsIgnoreCase(r10)     // Catch:{ Exception -> 0x046e }
                if (r9 == 0) goto L_0x042b
                com.wits.pms.custom.KswSettings r9 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r10 = "SupportDvrAppList"
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r8.toJson((java.lang.Object) r11)     // Catch:{ Exception -> 0x046e }
                r9.saveJson(r10, r11)     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r9 = r15.dataList     // Catch:{ Exception -> 0x046e }
                r9.clear()     // Catch:{ Exception -> 0x046e }
                r15.dvrList = r7     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x0241:
                boolean r10 = r15.setting     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x030f
                boolean r10 = r15.factory     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x024b
                goto L_0x030f
            L_0x024b:
                boolean r10 = r15.naviList     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x02fe
                boolean r10 = r15.dvrList     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x0255
                goto L_0x02fe
            L_0x0255:
                boolean r10 = r15.canType     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x02e4
                boolean r10 = r15.carType     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x025f
                goto L_0x02e4
            L_0x025f:
                boolean r10 = r15.language     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x0267
                boolean r10 = r15.uiType     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03b1
            L_0x0267:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x02a8
                int r10 = r4.getAttributeCount()     // Catch:{ Exception -> 0x046e }
                java.util.ArrayList<java.lang.String> r11 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r12.<init>()     // Catch:{ Exception -> 0x046e }
                java.lang.String r13 = r4.getAttributeValue(r9)     // Catch:{ Exception -> 0x046e }
                r12.append(r13)     // Catch:{ Exception -> 0x046e }
                boolean r13 = r15.uiType     // Catch:{ Exception -> 0x046e }
                if (r13 == 0) goto L_0x029c
                if (r10 != r6) goto L_0x029c
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r13.<init>()     // Catch:{ Exception -> 0x046e }
                java.lang.String r14 = "&"
                r13.append(r14)     // Catch:{ Exception -> 0x046e }
                r14 = 2
                java.lang.String r14 = r4.getAttributeValue(r14)     // Catch:{ Exception -> 0x046e }
                r13.append(r14)     // Catch:{ Exception -> 0x046e }
                java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x046e }
                goto L_0x029e
            L_0x029c:
                java.lang.String r13 = ""
            L_0x029e:
                r12.append(r13)     // Catch:{ Exception -> 0x046e }
                java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x046e }
                r11.add(r12)     // Catch:{ Exception -> 0x046e }
            L_0x02a8:
                java.lang.String r10 = ""
                java.lang.String r11 = "code"
                java.lang.String r10 = r4.getAttributeValue(r10, r11)     // Catch:{ Exception -> 0x046e }
                boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x02c3
                java.util.ArrayList<java.lang.String> r10 = r15.codeList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = ""
                java.lang.String r12 = "code"
                java.lang.String r11 = r4.getAttributeValue(r11, r12)     // Catch:{ Exception -> 0x046e }
                r10.add(r11)     // Catch:{ Exception -> 0x046e }
            L_0x02c3:
                boolean r10 = r15.language     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03b1
                java.lang.String r10 = ""
                java.lang.String r11 = "id"
                java.lang.String r10 = r4.getAttributeValue(r10, r11)     // Catch:{ Exception -> 0x046e }
                boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x03b1
                java.util.ArrayList<java.lang.String> r10 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = ""
                java.lang.String r12 = "id"
                java.lang.String r11 = r4.getAttributeValue(r11, r12)     // Catch:{ Exception -> 0x046e }
                r10.add(r11)     // Catch:{ Exception -> 0x046e }
                goto L_0x03b1
            L_0x02e4:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x02f3
                java.util.ArrayList<java.lang.String> r10 = r15.idList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r4.getAttributeValue(r7)     // Catch:{ Exception -> 0x046e }
                r10.add(r11)     // Catch:{ Exception -> 0x046e }
            L_0x02f3:
                java.util.ArrayList<java.lang.String> r10 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r4.nextText()     // Catch:{ Exception -> 0x046e }
                r10.add(r11)     // Catch:{ Exception -> 0x046e }
                goto L_0x03b1
            L_0x02fe:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03b1
                java.util.ArrayList<java.lang.String> r10 = r15.dataList     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r4.getAttributeValue(r7)     // Catch:{ Exception -> 0x046e }
                r10.add(r11)     // Catch:{ Exception -> 0x046e }
                goto L_0x03b1
            L_0x030f:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03b1
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "Language"
                boolean r10 = r10.equals(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x034c
                com.wits.pms.custom.KswSettings r10 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r12.<init>()     // Catch:{ Exception -> 0x046e }
                java.lang.String r13 = ""
                r12.append(r13)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r13 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r14 = "languageID"
                java.util.List r13 = r13.getDataListFromJsonKey(r14)     // Catch:{ Exception -> 0x046e }
                java.lang.String r14 = r4.nextText()     // Catch:{ Exception -> 0x046e }
                int r13 = r13.indexOf(r14)     // Catch:{ Exception -> 0x046e }
                r12.append(r13)     // Catch:{ Exception -> 0x046e }
                java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x046e }
                r10.saveConfig(r11, r12)     // Catch:{ Exception -> 0x046e }
                goto L_0x03b1
            L_0x034c:
                boolean r10 = r15.factory     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03a4
                boolean r10 = r15.addFactorySettingsLocal     // Catch:{ Exception -> 0x046e }
                if (r10 != 0) goto L_0x03a4
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r11 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.FactorySettings r11 = r11.mFactorySettings     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r11.getStringValue(r10)     // Catch:{ Exception -> 0x046e }
                if (r11 != 0) goto L_0x0380
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r12.<init>()     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r13 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.FactorySettings r13 = r13.mFactorySettings     // Catch:{ Exception -> 0x046e }
                int r13 = r13.getIntValue(r10)     // Catch:{ Exception -> 0x046e }
                r12.append(r13)     // Catch:{ Exception -> 0x046e }
                java.lang.String r13 = ""
                r12.append(r13)     // Catch:{ Exception -> 0x046e }
                java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x046e }
                r11 = r12
            L_0x0380:
                java.lang.String r12 = "FactorySettings"
                java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x046e }
                r13.<init>()     // Catch:{ Exception -> 0x046e }
                java.lang.String r14 = "FactorySettings data key:"
                r13.append(r14)     // Catch:{ Exception -> 0x046e }
                r13.append(r10)     // Catch:{ Exception -> 0x046e }
                java.lang.String r14 = " - value:"
                r13.append(r14)     // Catch:{ Exception -> 0x046e }
                r13.append(r11)     // Catch:{ Exception -> 0x046e }
                java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x046e }
                android.util.Log.i(r12, r13)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r12 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                r12.saveConfig(r10, r11)     // Catch:{ Exception -> 0x046e }
                goto L_0x03b1
            L_0x03a4:
                com.wits.pms.custom.KswSettings r10 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r12 = r4.nextText()     // Catch:{ Exception -> 0x046e }
                r10.saveConfig(r11, r12)     // Catch:{ Exception -> 0x046e }
            L_0x03b1:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "setings"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03c1
                r15.setting = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x03c1:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "factory"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03d0
                r15.factory = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x03d0:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "SupportNaviAppList"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03df
                r15.naviList = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x03df:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "CarDisplayParam"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03ee
                r15.carType = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x03ee:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "CANBusProtocol"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x03fd
                r15.canType = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x03fd:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "SupportLanguageList"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x040c
                r15.language = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x040c:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "SupportUIList"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x041b
                r15.uiType = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x041b:
                java.lang.String r10 = r4.getName()     // Catch:{ Exception -> 0x046e }
                java.lang.String r11 = "SupportDvrAppList"
                boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x046e }
                if (r10 == 0) goto L_0x042b
                r15.dvrList = r9     // Catch:{ Exception -> 0x046e }
                goto L_0x042b
            L_0x042a:
            L_0x042b:
                int r9 = r4.next()     // Catch:{ Exception -> 0x046e }
                r5 = r9
                goto L_0x007a
            L_0x0432:
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                boolean unused = r6.onlyLanguageId = r7     // Catch:{ Exception -> 0x046e }
                java.io.File r6 = r15.mConfig     // Catch:{ Exception -> 0x046e }
                java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r7 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.io.File r7 = r7.defaultConfig     // Catch:{ Exception -> 0x046e }
                java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ Exception -> 0x046e }
                boolean r6 = r6.equals(r7)     // Catch:{ Exception -> 0x046e }
                if (r6 != 0) goto L_0x0466
                java.io.File r6 = r15.mConfig     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r7 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.io.File r7 = r7.defaultConfig     // Catch:{ Exception -> 0x046e }
                com.wits.pms.utils.CopyFile.copyTo(r6, r7)     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                android.os.Handler r6 = r6.mHandler     // Catch:{ Exception -> 0x046e }
                com.wits.pms.custom.-$$Lambda$KswSettings$InitConfigRunnable$_9H3V7Punr9ZRjsLwtgyMAkCm50 r7 = new com.wits.pms.custom.-$$Lambda$KswSettings$InitConfigRunnable$_9H3V7Punr9ZRjsLwtgyMAkCm50     // Catch:{ Exception -> 0x046e }
                r7.<init>()     // Catch:{ Exception -> 0x046e }
                r6.post(r7)     // Catch:{ Exception -> 0x046e }
            L_0x0466:
                com.wits.pms.custom.KswSettings r6 = com.wits.pms.custom.KswSettings.this     // Catch:{ Exception -> 0x046e }
                java.lang.String r7 = "initKswConfig"
                r6.setInt(r7, r9)     // Catch:{ Exception -> 0x046e }
                goto L_0x0476
            L_0x046e:
                r0 = move-exception
                java.lang.String r1 = "KswSettings"
                java.lang.String r2 = "error"
                android.util.Log.e(r1, r2, r0)
            L_0x0476:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.wits.pms.custom.KswSettings.InitConfigRunnable.run():void");
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
        setString("ProtocolData", id + "");
        List<String> protocolID = getDataListFromJsonKey(CAN_ID);
        if (protocolID != null) {
            setInt(PROTOCOL_KEY, protocolID.indexOf(id + ""));
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

    public byte[] fileToByteArray(File mFile) {
        File file = mFile;
        FileInputStream fileInputStream = null;
        BufferedInputStream in = null;
        byte[] bt = null;
        try {
            if (file.exists()) {
                if (!file.isDirectory()) {
                    FileInputStream fileInputStream2 = new FileInputStream(file);
                    BufferedInputStream in2 = new BufferedInputStream(fileInputStream2);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] temp = new byte[1048576];
                    while (true) {
                        int read = in2.read(temp);
                        int size = read;
                        if (read == -1) {
                            break;
                        }
                        out.write(temp, 0, size);
                    }
                    bt = out.toByteArray();
                    try {
                        fileInputStream2.close();
                        in2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bt;
                }
            }
            try {
                fileInputStream.close();
                in.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            fileInputStream.close();
            in.close();
        } catch (Throwable th) {
            try {
                fileInputStream.close();
                in.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public static boolean CheckBOM(byte[] bytes) {
        if (bytes.length > 3 && 239 == (bytes[0] & 255) && 187 == (bytes[1] & 255) && 191 == (bytes[2] & 255)) {
            return true;
        }
        return false;
    }
}
