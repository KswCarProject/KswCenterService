package com.wits.pms.custom;

import android.bluetooth.BluetoothHidDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.wifi.WifiScanner;
import android.p007os.Build;
import android.p007os.Handler;
import android.p007os.PowerManager;
import android.p007os.UserHandle;
import android.provider.Settings;
import android.provider.SettingsStringUtil;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.midi.MidiConstants;
import com.android.internal.telephony.GsmAlphabet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.icu.text.SymbolTable;
import com.wits.pms.C3580R;
import com.wits.pms.core.CenterControlImpl;
import com.wits.pms.core.PowerManagerAppService;
import com.wits.pms.mcu.custom.KswMcuSender;
import com.wits.pms.mcu.custom.utils.BrightnessUtils;
import com.wits.pms.mirror.SystemProperties;
import com.wits.pms.utils.CopyFile;
import com.wits.pms.utils.LanguageUtil;
import com.wits.pms.utils.SysConfigUtil;
import com.wits.pms.utils.UsbUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.mozilla.universalchardet.prober.HebrewProber;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
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
    private File directUpdateFile;
    private boolean isSyncStatus;
    private final Context mContext;
    private FactorySettings mFactorySettings;
    private final Handler mHandler;
    private boolean onlyLanguageId;
    private File defaultConfig = new File("/mnt/vendor/persist/OEM/factory_config.xml");
    private Set<String> stringKeys = null;
    private Set<String> intKeys = null;
    private long initTime = System.currentTimeMillis();
    private final InitConfigRunnable mInitConfigRunnable = new InitConfigRunnable();

    public static KswSettings init(Context context) {
        if (settings == null) {
            synchronized (KswSettings.class) {
                if (settings == null) {
                    settings = new KswSettings(context);
                }
            }
        }
        return settings;
    }

    public static KswSettings getSettings() {
        if (settings == null) {
            KswSettings init = init(PowerManagerAppService.serviceContext);
            settings = init;
            return init;
        }
        return settings;
    }

    private KswSettings(Context context) {
        this.mContext = context;
        this.mHandler = new Handler(this.mContext.getMainLooper());
        buildKswSettings();
    }

    private void buildKswSettings() {
        this.stringKeys = new HashSet(getStringKeysFromSp());
        this.intKeys = new HashSet(getIntKeysFromSp());
        Log.m68i(TAG, "stringKeys size:" + this.stringKeys.size());
        Log.m68i(TAG, "intKeys size:" + this.intKeys.size());
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

    /* JADX INFO: Access modifiers changed from: private */
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
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true, new ContentObserver(this.mHandler) { // from class: com.wits.pms.custom.KswSettings.1
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                try {
                    int brightness = Settings.System.getInt(KswSettings.this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                    int value = BrightnessUtils.convertLinearToGamma(brightness, 10, 255);
                    double b = BrightnessUtils.getPercentage(value, 0, 1023);
                    int progress = (int) Math.round(100.0d * b);
                    Settings.System.putInt(KswSettings.this.mContext.getContentResolver(), "Brightness", progress);
                    CenterControlImpl.getImpl().setBrightness(progress);
                } catch (Settings.SettingNotFoundException e) {
                }
            }
        });
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24), true, new ContentObserver(this.mHandler) { // from class: com.wits.pms.custom.KswSettings.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange) {
                String time = KswSettings.this.getSettingsString(Settings.System.TIME_12_24);
                KswSettings.this.setInt("TimeFormat", "12".equals(time) ? 1 : 0);
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
        Log.m68i(TAG, "setInt key:" + key + " - value:" + value);
    }

    public void setIntWithoutMCU(String key, int value) {
        Settings.System.putInt(this.mContext.getContentResolver(), key, value);
        Log.m68i(TAG, "setIntWithoutMCU setInt key:" + key + " - value:" + value);
    }

    public void setString(String key, String value) {
        Settings.System.putString(this.mContext.getContentResolver(), key, value);
        handleConfig(key, value);
        saveStringKey(key);
        if (this.mFactorySettings != null) {
            this.mFactorySettings.saveStringValue(key, value);
        }
        Log.m68i(TAG, "setString key:" + key + " - value:" + value);
    }

    private void saveIntKey(String key) {
        Log.m68i(TAG, "saveIntKey key:" + key);
        this.intKeys.add(key);
        Set<String> intSet = getIntKeysFromSp();
        HashSet<String> intSetSave = new HashSet<>(intSet);
        intSetSave.add(key);
        getSettingsSp().edit().putStringSet(INT_KEY_MAP, intSetSave).apply();
    }

    private void saveStringKey(String key) {
        Log.m68i(TAG, "saveStringKey key:" + key);
        this.stringKeys.add(key);
        Set<String> stringSet = getStringKeysFromSp();
        HashSet<String> stringSetSave = new HashSet<>(stringSet);
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
            Log.m68i(TAG, "initConfig");
            updateConfig(this.defaultConfig);
            return;
        }
        Settings.System.putString(this.mContext.getContentResolver(), "UiName", getSaveUiName());
        Log.m68i(TAG, "sync UI:" + getSaveUiName());
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
            Log.m68i(TAG, "fixVersion");
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
        Log.m68i(TAG, "syncStatus to Mcu");
        this.isSyncStatus = true;
        this.mFactorySettings = FactorySettings.getFactorySettings();
        boolean fixLocalFactory = false;
        if (this.mFactorySettings == null) {
            Log.m68i("FactorySettings", "fix missing local factory settings file");
            fixLocalFactory = true;
            this.mFactorySettings = new FactorySettings();
        }
        Set<String> intKeys = getIntKeysFromSp();
        Set<String> stringKeys = getStringKeysFromSp();
        if (intKeys.size() == 0 || stringKeys.size() == 0) {
            return;
        }
        for (String key : intKeys) {
            if (!key.equals("USB_HOST") && !key.equals("Language") && !key.equals("Support_TXZ") && !key.equals("BenzPanelEnable") && !key.equals("benz_aux_switch") && !key.equals("benzClockSort")) {
                try {
                    int value = getSettingsInt(key);
                    handleConfig(key, value);
                    if (fixLocalFactory) {
                        Log.m68i("FactorySettings", "fix key:" + key + "-value:" + value);
                        this.mFactorySettings.saveIntValue(key, value);
                    }
                } catch (Settings.SettingNotFoundException e) {
                }
            }
        }
        for (String key2 : stringKeys) {
            if (!key2.contains("UI")) {
                String value2 = getSettingsString(key2);
                handleConfig(key2, value2);
                if (fixLocalFactory) {
                    Log.m68i("FactorySettings", "fix key:" + key2 + "-value:" + value2);
                    this.mFactorySettings.saveStringValue(key2, value2);
                }
            }
        }
        handleConfig("Brightness", getSettingsInt("Brightness", 60));
        this.isSyncStatus = false;
    }

    private void sendMcu(int cmdType, byte[] data) {
        KswMcuSender.getSender().sendMessage(cmdType, data);
    }

    private void handleConfig(String key, String value) {
        char c;
        int hashCode = key.hashCode();
        boolean initKswConfig = true;
        if (hashCode == -1548945544) {
            if (key.equals("Language")) {
                c = 1;
            }
            c = '\uffff';
        } else if (hashCode == -533217944) {
            if (key.equals("TXZ_Wakeup")) {
                c = 0;
            }
            c = '\uffff';
        } else if (hashCode == 2708) {
            if (key.equals("UI")) {
                c = 2;
            }
            c = '\uffff';
        } else if (hashCode != 91785770) {
            if (hashCode == 309639685 && key.equals("UI_type")) {
                c = 3;
            }
            c = '\uffff';
        } else {
            if (key.equals("Reverse_time")) {
                c = 4;
            }
            c = '\uffff';
        }
        switch (c) {
            case 0:
                Settings.System.putString(this.mContext.getContentResolver(), "ksw_wakeup_keywords", value);
                return;
            case 1:
                String[] split = value.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                Locale locale = new Locale(split[0]);
                if (split.length == 2) {
                    locale = new Locale(split[0], split[1]);
                }
                LanguageUtil.changeSystemLanguage(locale);
                return;
            case 2:
            case 3:
                if (key.equals("UI")) {
                    value = getDataListFromJsonKey("").get(Integer.parseInt(value));
                }
                try {
                    initKswConfig = getSettingsInt("initKswConfig") == 0;
                } catch (Settings.SettingNotFoundException e) {
                }
                if (initKswConfig) {
                    if (!checkUi()) {
                        SysConfigUtil.writeArg(value, uiSave);
                    } else {
                        value = SysConfigUtil.getArg(uiSave);
                    }
                    Settings.System.putString(this.mContext.getContentResolver(), "UiName", value);
                    return;
                }
                Settings.System.putString(this.mContext.getContentResolver(), "UiName", value);
                try {
                    File file = new File(uiSave);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    SysConfigUtil.writeArg(value, uiSave);
                    return;
                } catch (IOException e2) {
                    return;
                }
            case 4:
                if (TextUtils.isEmpty(value)) {
                    sendMcu(112, new byte[]{30, 0, 0});
                    return;
                }
                try {
                    String[] reverseTimes = value.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    int item = Integer.parseInt(reverseTimes[0]);
                    switch (item) {
                        case 0:
                            sendMcu(112, new byte[]{30, 0, 0});
                            break;
                        case 1:
                            int time = Integer.parseInt(reverseTimes[1]);
                            sendMcu(112, new byte[]{30, 1, (byte) time});
                            break;
                    }
                    return;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    Log.m70e(TAG, "handleConfig: set Reverse_time error set 0");
                    sendMcu(112, new byte[]{30, 0, 0});
                    return;
                }
            default:
                return;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int handleConfig(String key, int intValue) {
        char c;
        byte value = (byte) intValue;
        switch (key.hashCode()) {
            case -2113780075:
                if (key.equals("CarAux_Operate")) {
                    c = 31;
                    break;
                }
                c = '\uffff';
                break;
            case -1997186328:
                if (key.equals("Front_view_camer1a")) {
                    c = '#';
                    break;
                }
                c = '\uffff';
                break;
            case -1885010620:
                if (key.equals("TimeFormat")) {
                    c = 17;
                    break;
                }
                c = '\uffff';
                break;
            case -1793262372:
                if (key.equals("Map_key")) {
                    c = '(';
                    break;
                }
                c = '\uffff';
                break;
            case -1724865544:
                if (key.equals("TurnSignalControl")) {
                    c = 27;
                    break;
                }
                c = '\uffff';
                break;
            case -1669187502:
                if (key.equals("Speed_type")) {
                    c = '=';
                    break;
                }
                c = '\uffff';
                break;
            case -1653340047:
                if (key.equals("Brightness")) {
                    c = 11;
                    break;
                }
                c = '\uffff';
                break;
            case -1645198443:
                if (key.equals("OriginalRadar")) {
                    c = '?';
                    break;
                }
                c = '\uffff';
                break;
            case -1548945544:
                if (key.equals("Language")) {
                    c = 23;
                    break;
                }
                c = '\uffff';
                break;
            case -1528907729:
                if (key.equals("CarAuxIndex1")) {
                    c = ',';
                    break;
                }
                c = '\uffff';
                break;
            case -1528907728:
                if (key.equals("CarAuxIndex2")) {
                    c = '-';
                    break;
                }
                c = '\uffff';
                break;
            case -1282124803:
                if (key.equals("txz_temp")) {
                    c = '8';
                    break;
                }
                c = '\uffff';
                break;
            case -1257575528:
                if (key.equals("TempUnit")) {
                    c = '\t';
                    break;
                }
                c = '\uffff';
                break;
            case -1181891355:
                if (key.equals("Car_navi_vol")) {
                    c = 15;
                    break;
                }
                c = '\uffff';
                break;
            case -1103831850:
                if (key.equals("CarVideoDisplayStyle")) {
                    c = 28;
                    break;
                }
                c = '\uffff';
                break;
            case -1091766978:
                if (key.equals("txz_speed")) {
                    c = '7';
                    break;
                }
                c = '\uffff';
                break;
            case -1070793006:
                if (key.equals("BootUpCamera")) {
                    c = 26;
                    break;
                }
                c = '\uffff';
                break;
            case -1028676594:
                if (key.equals("phone_key")) {
                    c = '5';
                    break;
                }
                c = '\uffff';
                break;
            case -952414302:
                if (key.equals("Backlight_auto_set")) {
                    c = '+';
                    break;
                }
                c = '\uffff';
                break;
            case -924519752:
                if (key.equals(PROTOCOL_KEY)) {
                    c = 24;
                    break;
                }
                c = '\uffff';
                break;
            case -899948362:
                if (key.equals("NaviMix")) {
                    c = 18;
                    break;
                }
                c = '\uffff';
                break;
            case -872647543:
                if (key.equals("txz_oil")) {
                    c = '6';
                    break;
                }
                c = '\uffff';
                break;
            case -816518904:
                if (key.equals("GoogleAPP")) {
                    c = ';';
                    break;
                }
                c = '\uffff';
                break;
            case -801026034:
                if (key.equals("AHD_cam_Select")) {
                    c = '/';
                    break;
                }
                c = '\uffff';
                break;
            case -776702634:
                if (key.equals("EQ_mode")) {
                    c = 19;
                    break;
                }
                c = '\uffff';
                break;
            case -738352606:
                if (key.equals("CarAux_auto_method")) {
                    c = 30;
                    break;
                }
                c = '\uffff';
                break;
            case -682449643:
                if (key.equals("AMP_Type")) {
                    c = '\"';
                    break;
                }
                c = '\uffff';
                break;
            case -677297959:
                if (key.equals("Default_PowerBoot")) {
                    c = '.';
                    break;
                }
                c = '\uffff';
                break;
            case -660995341:
                if (key.equals("OLDBMWX")) {
                    c = '0';
                    break;
                }
                c = '\uffff';
                break;
            case -602992886:
                if (key.equals("RearCamMirror")) {
                    c = 2;
                    break;
                }
                c = '\uffff';
                break;
            case -554769949:
                if (key.equals("Mode_key")) {
                    c = ')';
                    break;
                }
                c = '\uffff';
                break;
            case -519600940:
                if (key.equals("ReversingMuteSelect")) {
                    c = 6;
                    break;
                }
                c = '\uffff';
                break;
            case -294129095:
                if (key.equals("DVR_Type")) {
                    c = '!';
                    break;
                }
                c = '\uffff';
                break;
            case -220630678:
                if (key.equals("DoNotPlayVideosWhileDriving")) {
                    c = 5;
                    break;
                }
                c = '\uffff';
                break;
            case -183099962:
                if (key.equals("Support_TXZ")) {
                    c = '2';
                    break;
                }
                c = '\uffff';
                break;
            case -114997240:
                if (key.equals("Android_media_vol")) {
                    c = '\f';
                    break;
                }
                c = '\uffff';
                break;
            case -63299011:
                if (key.equals("benzClockSort")) {
                    c = 0;
                    break;
                }
                c = '\uffff';
                break;
            case 88063465:
                if (key.equals("ShowRadar")) {
                    c = 4;
                    break;
                }
                c = '\uffff';
                break;
            case 90414126:
                if (key.equals("ShowTrack")) {
                    c = 3;
                    break;
                }
                c = '\uffff';
                break;
            case 106858821:
                if (key.equals("RearCamType")) {
                    c = '\b';
                    break;
                }
                c = '\uffff';
                break;
            case 214368532:
                if (key.equals("HandsetAutomaticSelect")) {
                    c = '&';
                    break;
                }
                c = '\uffff';
                break;
            case 241352631:
                if (key.equals("Car_phone_vol")) {
                    c = 14;
                    break;
                }
                c = '\uffff';
                break;
            case 252039837:
                if (key.equals("CCC_IDrive")) {
                    c = '%';
                    break;
                }
                c = '\uffff';
                break;
            case 304932150:
                if (key.equals("MicControl")) {
                    c = '>';
                    break;
                }
                c = '\uffff';
                break;
            case 340057703:
                if (key.equals("Middle_value")) {
                    c = 21;
                    break;
                }
                c = '\uffff';
                break;
            case 400825784:
                if (key.equals("touch_continuous_send")) {
                    c = '4';
                    break;
                }
                c = '\uffff';
                break;
            case 442866595:
                if (key.equals("USB_HOST")) {
                    c = '1';
                    break;
                }
                c = '\uffff';
                break;
            case 642405998:
                if (key.equals("CarDisplay")) {
                    c = SymbolTable.SYMBOL_REF;
                    break;
                }
                c = '\uffff';
                break;
            case 677993257:
                if (key.equals("forwardCamMirror")) {
                    c = 1;
                    break;
                }
                c = '\uffff';
                break;
            case 940906279:
                if (key.equals("BT_Type")) {
                    c = ' ';
                    break;
                }
                c = '\uffff';
                break;
            case 985261490:
                if (key.equals("Voice_key")) {
                    c = DateFormat.QUOTE;
                    break;
                }
                c = '\uffff';
                break;
            case 1010516114:
                if (key.equals("Android_phone_vol")) {
                    c = '\r';
                    break;
                }
                c = '\uffff';
                break;
            case 1031815915:
                if (key.equals("DirtTravelSelection")) {
                    c = 25;
                    break;
                }
                c = '\uffff';
                break;
            case 1060762673:
                if (key.equals("Bass_value")) {
                    c = 20;
                    break;
                }
                c = '\uffff';
                break;
            case 1071490530:
                if (key.equals("TimeSyncSoucrce")) {
                    c = 16;
                    break;
                }
                c = '\uffff';
                break;
            case 1195313274:
                if (key.equals("FuelUnit")) {
                    c = '\n';
                    break;
                }
                c = '\uffff';
                break;
            case 1374371814:
                if (key.equals("Treble_value")) {
                    c = 22;
                    break;
                }
                c = '\uffff';
                break;
            case 1533443583:
                if (key.equals("benz_aux_switch")) {
                    c = '3';
                    break;
                }
                c = '\uffff';
                break;
            case 1598142665:
                if (key.equals("Front_view_camera")) {
                    c = 7;
                    break;
                }
                c = '\uffff';
                break;
            case 1669187709:
                if (key.equals("Front_left")) {
                    c = '<';
                    break;
                }
                c = '\uffff';
                break;
            case 1757663793:
                if (key.equals("mic_gain_m501")) {
                    c = '9';
                    break;
                }
                c = '\uffff';
                break;
            case 1757664753:
                if (key.equals("mic_gain_m600")) {
                    c = ':';
                    break;
                }
                c = '\uffff';
                break;
            case 1857618170:
                if (key.equals("cam360_video")) {
                    c = '*';
                    break;
                }
                c = '\uffff';
                break;
            case 2053143566:
                if (key.equals("EQ_app")) {
                    c = '@';
                    break;
                }
                c = '\uffff';
                break;
            case 2103778808:
                if (key.equals("DashBoardUnit")) {
                    c = 29;
                    break;
                }
                c = '\uffff';
                break;
            default:
                c = '\uffff';
                break;
        }
        switch (c) {
            case 0:
                Log.m72d(TAG, "clock input handleConfig benzClockSort, key : " + key + ", value : " + intValue);
                CenterControlImpl.getImpl().configDialByUser(intValue);
                break;
            case 1:
                sendMcu(106, new byte[]{GsmAlphabet.GSM_EXTENDED_ESCAPE, value});
                break;
            case 2:
                sendMcu(106, new byte[]{1, value});
                break;
            case 3:
                sendMcu(106, new byte[]{22, value});
                break;
            case 4:
                sendMcu(106, new byte[]{23, value});
                break;
            case 5:
                sendMcu(106, new byte[]{BluetoothHidDevice.ERROR_RSP_UNKNOWN, value});
                break;
            case 6:
                sendMcu(112, new byte[]{8, value});
                break;
            case 7:
                sendMcu(106, new byte[]{20, value});
                break;
            case '\b':
                sendMcu(106, new byte[]{11, value});
                break;
            case '\t':
                sendMcu(106, new byte[]{24, value});
                break;
            case '\n':
                sendMcu(106, new byte[]{26, value});
                break;
            case 11:
                int realBrightness = BrightnessUtils.convertGammaToLinear((intValue * 1023) / 100, 10, 255);
                Settings.System.putInt(this.mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, realBrightness);
                if (this.isSyncStatus) {
                    CenterControlImpl.getImpl().setBrightness(intValue);
                    break;
                }
                break;
            case '\f':
                sendMcu(98, new byte[]{0, 1, 1, value, this.isSyncStatus ? (byte) 1 : (byte) 0});
                break;
            case '\r':
                sendMcu(98, new byte[]{0, 1, 2, value, this.isSyncStatus ? (byte) 1 : (byte) 0});
                break;
            case 14:
                sendMcu(98, new byte[]{0, 2, 2, value, this.isSyncStatus ? (byte) 1 : (byte) 0});
                break;
            case 15:
                sendMcu(98, new byte[]{0, 2, 3, value, this.isSyncStatus ? (byte) 1 : (byte) 0});
                break;
            case 16:
                sendMcu(106, new byte[]{WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, value});
                Settings.Global.putInt(this.mContext.getContentResolver(), "auto_time", value != 1 ? 1 : 0);
                break;
            case 17:
                try {
                    if (getSettingsInt("TimeSyncSoucrce") == 0) {
                        Settings.System.putString(this.mContext.getContentResolver(), Settings.System.TIME_12_24, intValue == 1 ? "12" : "24");
                        Intent timeChanged = new Intent(Intent.ACTION_TIME_CHANGED);
                        this.mContext.sendBroadcastAsUser(timeChanged, UserHandle.getUserHandleForUid(this.mContext.getApplicationInfo().uid));
                    } else {
                        Settings.System.putString(this.mContext.getContentResolver(), Settings.System.TIME_12_24, intValue == 1 ? "12" : "24");
                        sendMcu(106, new byte[]{MidiConstants.STATUS_CHANNEL_MASK, value});
                    }
                    break;
                } catch (Settings.SettingNotFoundException e) {
                    break;
                }
            case 18:
                float mixVolume = (intValue + 1) / 10.0f;
                Settings.System.putFloat(this.mContext.getContentResolver(), "NaviMix", mixVolume);
                break;
            case 19:
            case 20:
            case 21:
            case 22:
                try {
                    byte eqMode = (byte) getSettingsInt("EQ_mode");
                    switch (eqMode) {
                        case 0:
                            byte bass = (byte) getSettingsInt("Bass_value");
                            byte middle = (byte) getSettingsInt("Middle_value");
                            byte treble = (byte) getSettingsInt("Treble_value");
                            sendMcu(115, new byte[]{bass, middle, treble, eqMode});
                            break;
                        case 1:
                            sendMcu(115, new byte[]{WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, 9, WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, eqMode});
                            break;
                        case 2:
                            sendMcu(115, new byte[]{19, 13, MidiConstants.STATUS_CHANNEL_MASK, eqMode});
                            break;
                        case 3:
                            sendMcu(115, new byte[]{MidiConstants.STATUS_CHANNEL_MASK, 11, 17, eqMode});
                            break;
                        case 4:
                            sendMcu(115, new byte[]{13, WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, eqMode});
                            break;
                        case 5:
                            sendMcu(115, new byte[]{17, 11, 19, eqMode});
                            break;
                    }
                    break;
                } catch (Settings.SettingNotFoundException e2) {
                    break;
                }
                break;
            case 23:
                int index = Integer.parseInt(getDataListFromJsonKey(LANGUAGE_ID).get(intValue));
                if (index >= 0) {
                    List<Locale> locales = new ArrayList<Locale>() { // from class: com.wits.pms.custom.KswSettings.3
                        {
                            add(new Locale("zh", "CN"));
                            add(new Locale("zh", "TW"));
                            add(new Locale("en"));
                            add(new Locale("de"));
                            add(new Locale("pt"));
                            add(new Locale("es"));
                            add(new Locale("ru"));
                            add(new Locale("ja"));
                            add(Locale.KOREA);
                            add(new Locale("it"));
                            add(new Locale("fi"));
                            add(new Locale("tr"));
                            add(new Locale("vi"));
                            add(new Locale("nl"));
                            add(new Locale("iw", "IL"));
                            add(new Locale("fr"));
                            add(new Locale("pl"));
                            add(new Locale("ar"));
                            add(new Locale("el"));
                            add(new Locale("th"));
                            add(new Locale("hr"));
                        }
                    };
                    Log.m68i(TAG, "Change Language@" + locales.get(index).getLanguage() + " @index=" + index);
                    LanguageUtil.changeSystemLanguage(locales.get(index));
                    break;
                } else {
                    return -1;
                }
            case 24:
                if (value == -1) {
                    value = 0;
                }
                try {
                    List<String> carDisplayParam = getDataListFromJsonKey(CAN_ID);
                    if (carDisplayParam != null) {
                        String s = carDisplayParam.get(value);
                        byte id = Byte.parseByte(s);
                        sendMcu(112, new byte[]{7, id});
                        break;
                    }
                } catch (Exception e3) {
                    break;
                }
                break;
            case 25:
                sendMcu(112, new byte[]{GsmAlphabet.GSM_EXTENDED_ESCAPE, value});
                break;
            case 26:
                sendMcu(112, new byte[]{36, value});
                break;
            case 27:
                sendMcu(112, new byte[]{37, value});
                break;
            case 28:
                try {
                    List<String> carDisplayParam2 = getDataListFromJsonKey(CAR_DISPLAY_ID);
                    if (carDisplayParam2 != null) {
                        String s2 = carDisplayParam2.get(value);
                        byte id2 = Byte.parseByte(s2);
                        sendMcu(112, new byte[]{1, id2});
                        break;
                    }
                } catch (Exception e4) {
                    break;
                }
                break;
            case 29:
                sendMcu(112, new byte[]{26, value});
                break;
            case 30:
                sendMcu(112, new byte[]{12, value});
                break;
            case 31:
                sendMcu(112, new byte[]{3, value});
                break;
            case ' ':
                sendMcu(112, new byte[]{5, value});
                break;
            case '!':
                sendMcu(112, new byte[]{4, value});
                break;
            case '\"':
                sendMcu(112, new byte[]{2, value});
                break;
            case '#':
                sendMcu(112, new byte[]{13, value});
                break;
            case '$':
                sendMcu(112, new byte[]{BluetoothHidDevice.ERROR_RSP_UNKNOWN, value});
                break;
            case '%':
                sendMcu(112, new byte[]{6, value});
                break;
            case '&':
                sendMcu(112, new byte[]{9, value});
                break;
            case '\'':
                sendMcu(112, new byte[]{17, value});
                break;
            case '(':
                sendMcu(112, new byte[]{21, value});
                break;
            case ')':
                sendMcu(112, new byte[]{22, value});
                break;
            case '*':
                sendMcu(112, new byte[]{18, value});
                break;
            case '+':
                sendMcu(112, new byte[]{19, value});
                break;
            case ',':
                sendMcu(112, new byte[]{23, value});
                break;
            case '-':
                sendMcu(112, new byte[]{24, value});
                break;
            case '.':
                sendMcu(112, new byte[]{25, value});
                break;
            case '/':
                sendMcu(112, new byte[]{20, value});
                break;
            case '0':
                handleConfig("CarDisplay", value != 1 ? 1 : 0);
                break;
            case '1':
                UsbUtil.updateUsbMode(value);
                break;
            case '2':
                CenterControlImpl.getImpl().setTxzSwitch(value == 1);
                break;
            case '3':
                sendMcu(112, new byte[]{WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK, value});
                break;
            case '4':
                sendMcu(112, new byte[]{28, value});
                break;
            case '5':
                sendMcu(112, new byte[]{29, value});
                break;
            case '6':
                sendMcu(112, new byte[]{31, value});
                break;
            case '7':
                sendMcu(112, new byte[]{HebrewProber.SPACE, value});
                break;
            case '8':
                sendMcu(112, new byte[]{33, value});
                break;
            case '9':
                Log.m72d(TAG, "handleConfig mic_gain_m501  intValue = " + intValue);
                if (Build.VERSION.RELEASE.contains("10")) {
                    Log.m72d(TAG, "handleConfig mic_gain_m501 set");
                    SystemProperties.set("persist.mic.gain", intValue + "");
                    SystemProperties.set("persist.micgain.change", "0");
                    SystemProperties.set("persist.micgain.change", "1");
                    break;
                }
                break;
            case ':':
                Log.m72d(TAG, "handleConfig mic_gain_m600  intValue = " + intValue);
                if (Integer.parseInt(Build.VERSION.RELEASE) > 10 && Build.DISPLAY.contains("M600")) {
                    Log.m72d(TAG, "handleConfig mic_gain_m600 set");
                    SystemProperties.set("persist.mic.gain", intValue + "");
                    SystemProperties.set("persist.micgain.change", "0");
                    SystemProperties.set("persist.micgain.change", "1");
                    break;
                }
                break;
            case ';':
                Log.m72d(TAG, "handleConfig:  GoogleAPP intValue = " + intValue);
                if (intValue == 0) {
                    android.p007os.SystemProperties.set("persist.install.type", "chinese");
                    break;
                } else {
                    android.p007os.SystemProperties.set("persist.install.type", "foreign");
                    break;
                }
            case '<':
                sendMcu(112, new byte[]{34, value});
                break;
            case '=':
                sendMcu(112, new byte[]{35, value});
                break;
            case '>':
                Log.m68i(TAG, "Task#17317 -- MicControl value = " + ((int) value));
                sendMcu(112, new byte[]{38, value});
                break;
            case '?':
                Log.m68i(TAG, "Task#17907 -- OriginalRadar value = " + ((int) value));
                sendMcu(112, new byte[]{39, value});
                break;
            case '@':
                Settings.System.putInt(this.mContext.getContentResolver(), "EQ_app", intValue);
                break;
        }
        return intValue;
    }

    private void reboot() {
        this.mHandler.postDelayed(new Runnable() { // from class: com.wits.pms.custom.-$$Lambda$KswSettings$5nUKr8yRkQeboAsX8u8qFJsh60E
            @Override // java.lang.Runnable
            public final void run() {
                SystemProperties.set("sys.powerctl", "reboot");
            }
        }, 500L);
    }

    private boolean checkUi() {
        File uiSign = new File(uiSave);
        if (uiSign.exists()) {
            return !TextUtils.isEmpty(getSaveUiName());
        }
        return false;
    }

    private String getSaveUiName() {
        return SysConfigUtil.getArg(uiSave);
    }

    public List<String> getDataListFromJsonKey(String key) {
        String json = Settings.System.getString(this.mContext.getContentResolver(), key);
        return (List) new Gson().fromJson(json, new TypeToken<ArrayList<String>>() { // from class: com.wits.pms.custom.KswSettings.4
        }.getType());
    }

    /* loaded from: classes2.dex */
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
            Log.m68i("FactorySettings", "local factory data - " + this.addFactorySettingsLocal);
            if (this.addFactorySettingsLocal) {
                KswSettings.this.mFactorySettings = new FactorySettings();
            } else {
                KswSettings.this.mFactorySettings = FactorySettings.getFactorySettings();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:112:0x03df A[Catch: all -> 0x04a5, Exception -> 0x04a8, TryCatch #2 {Exception -> 0x04a8, blocks: (B:3:0x0007, B:15:0x0029, B:18:0x0073, B:20:0x0091, B:24:0x009f, B:26:0x00a4, B:28:0x00b0, B:29:0x00b4, B:31:0x00c0, B:32:0x00c4, B:34:0x00d0, B:35:0x00e6, B:37:0x00f2, B:38:0x011a, B:40:0x0126, B:41:0x0155, B:43:0x0161, B:45:0x01a3, B:47:0x020a, B:46:0x01e3, B:48:0x021d, B:50:0x0229, B:51:0x023f, B:53:0x024b, B:54:0x0261, B:56:0x0265, B:59:0x026b, B:61:0x026f, B:64:0x0275, B:66:0x0279, B:69:0x027f, B:71:0x0283, B:110:0x03d3, B:112:0x03df, B:113:0x03e3, B:115:0x03ef, B:116:0x03f2, B:118:0x03fe, B:119:0x0401, B:121:0x040d, B:122:0x0410, B:124:0x041c, B:125:0x041f, B:127:0x042b, B:128:0x042e, B:130:0x043a, B:131:0x043d, B:133:0x0449, B:73:0x0287, B:75:0x028d, B:78:0x02a5, B:80:0x02be, B:81:0x02c8, B:83:0x02d6, B:84:0x02e3, B:86:0x02e7, B:88:0x02f5, B:89:0x0304, B:91:0x030a, B:92:0x0314, B:93:0x031f, B:95:0x0325, B:96:0x0331, B:98:0x0337, B:100:0x0343, B:101:0x036e, B:103:0x0372, B:105:0x0376, B:107:0x0386, B:108:0x03a2, B:109:0x03c6, B:135:0x044d, B:136:0x0456, B:138:0x0472, B:139:0x048b, B:19:0x0086), top: B:166:0x0007, outer: #0 }] */
        /* JADX WARN: Removed duplicated region for block: B:113:0x03e3 A[Catch: all -> 0x04a5, Exception -> 0x04a8, TryCatch #2 {Exception -> 0x04a8, blocks: (B:3:0x0007, B:15:0x0029, B:18:0x0073, B:20:0x0091, B:24:0x009f, B:26:0x00a4, B:28:0x00b0, B:29:0x00b4, B:31:0x00c0, B:32:0x00c4, B:34:0x00d0, B:35:0x00e6, B:37:0x00f2, B:38:0x011a, B:40:0x0126, B:41:0x0155, B:43:0x0161, B:45:0x01a3, B:47:0x020a, B:46:0x01e3, B:48:0x021d, B:50:0x0229, B:51:0x023f, B:53:0x024b, B:54:0x0261, B:56:0x0265, B:59:0x026b, B:61:0x026f, B:64:0x0275, B:66:0x0279, B:69:0x027f, B:71:0x0283, B:110:0x03d3, B:112:0x03df, B:113:0x03e3, B:115:0x03ef, B:116:0x03f2, B:118:0x03fe, B:119:0x0401, B:121:0x040d, B:122:0x0410, B:124:0x041c, B:125:0x041f, B:127:0x042b, B:128:0x042e, B:130:0x043a, B:131:0x043d, B:133:0x0449, B:73:0x0287, B:75:0x028d, B:78:0x02a5, B:80:0x02be, B:81:0x02c8, B:83:0x02d6, B:84:0x02e3, B:86:0x02e7, B:88:0x02f5, B:89:0x0304, B:91:0x030a, B:92:0x0314, B:93:0x031f, B:95:0x0325, B:96:0x0331, B:98:0x0337, B:100:0x0343, B:101:0x036e, B:103:0x0372, B:105:0x0376, B:107:0x0386, B:108:0x03a2, B:109:0x03c6, B:135:0x044d, B:136:0x0456, B:138:0x0472, B:139:0x048b, B:19:0x0086), top: B:166:0x0007, outer: #0 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            String str;
            clearData();
            FileReader fr = null;
            InputStream inputStream = null;
            try {
                try {
                    try {
                        if (!this.mConfig.exists()) {
                            if (0 != 0) {
                                try {
                                    inputStream.close();
                                } catch (Exception e) {
                                    Log.m69e(KswSettings.TAG, "close error:", e);
                                    return;
                                }
                            }
                            if (0 != 0) {
                                fr.close();
                                return;
                            }
                            return;
                        }
                        byte[] fileBytes = KswSettings.this.fileToByteArray(this.mConfig);
                        boolean isBom = KswSettings.CheckBOM(fileBytes);
                        fr = new FileReader(this.mConfig);
                        String encoding = fr.getEncoding();
                        Log.m72d(KswSettings.TAG, "config  isBom = " + isBom + "    encoding  = " + encoding);
                        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                        int i = 3;
                        boolean z = false;
                        if (encoding.equals("UTF-8") && isBom) {
                            byte[] noBomBytes = new byte[fileBytes.length - 3];
                            System.arraycopy(fileBytes, 3, noBomBytes, 0, fileBytes.length - 3);
                            inputStream = new ByteArrayInputStream(noBomBytes);
                            parser.setInput(inputStream, encoding);
                        } else {
                            inputStream = new FileInputStream(this.mConfig);
                            parser.setInput(inputStream, encoding);
                        }
                        int eventType = parser.getEventType();
                        Gson gson = new Gson();
                        while (eventType != 1) {
                            if (eventType != 0) {
                                switch (eventType) {
                                    case 2:
                                        if (!this.setting && !this.factory) {
                                            if (!this.naviList && !this.dvrList) {
                                                if (!this.canType && !this.carType) {
                                                    if (this.language || this.uiType) {
                                                        if (parser.getName() != null) {
                                                            int attributeCount = parser.getAttributeCount();
                                                            ArrayList<String> arrayList = this.dataList;
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append(parser.getAttributeValue(1));
                                                            if (this.uiType && attributeCount == i) {
                                                                str = "&" + parser.getAttributeValue(2);
                                                            } else {
                                                                str = "";
                                                            }
                                                            sb.append(str);
                                                            arrayList.add(sb.toString());
                                                        }
                                                        if (!TextUtils.isEmpty(parser.getAttributeValue("", "code"))) {
                                                            this.codeList.add(parser.getAttributeValue("", "code"));
                                                        }
                                                        if (this.language && !TextUtils.isEmpty(parser.getAttributeValue("", "id"))) {
                                                            this.idList.add(parser.getAttributeValue("", "id"));
                                                        }
                                                    }
                                                    if (!parser.getName().equalsIgnoreCase("setings")) {
                                                        this.setting = true;
                                                        continue;
                                                    } else if (!parser.getName().equalsIgnoreCase("factory")) {
                                                        if (!parser.getName().equalsIgnoreCase("SupportNaviAppList")) {
                                                            if (!parser.getName().equalsIgnoreCase("CarDisplayParam")) {
                                                                if (!parser.getName().equalsIgnoreCase("CANBusProtocol")) {
                                                                    if (!parser.getName().equalsIgnoreCase("SupportLanguageList")) {
                                                                        if (!parser.getName().equalsIgnoreCase("SupportUIList")) {
                                                                            if (!parser.getName().equalsIgnoreCase("SupportDvrAppList")) {
                                                                                break;
                                                                            } else {
                                                                                this.dvrList = true;
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            this.uiType = true;
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        this.language = true;
                                                                        break;
                                                                    }
                                                                } else {
                                                                    this.canType = true;
                                                                    break;
                                                                }
                                                            } else {
                                                                this.carType = true;
                                                                break;
                                                            }
                                                        } else {
                                                            this.naviList = true;
                                                            break;
                                                        }
                                                    } else {
                                                        this.factory = true;
                                                        break;
                                                    }
                                                }
                                                if (parser.getName() != null) {
                                                    this.idList.add(parser.getAttributeValue(0));
                                                }
                                                this.dataList.add(parser.nextText());
                                                if (!parser.getName().equalsIgnoreCase("setings")) {
                                                }
                                            }
                                            if (parser.getName() != null) {
                                                this.dataList.add(parser.getAttributeValue(0));
                                            }
                                            if (!parser.getName().equalsIgnoreCase("setings")) {
                                            }
                                        }
                                        if (parser.getName() != null) {
                                            if (parser.getName().equals("Language")) {
                                                KswSettings.this.saveConfig(parser.getName(), "" + KswSettings.this.getDataListFromJsonKey(KswSettings.LANGUAGE_ID).indexOf(parser.nextText()));
                                            } else if (!this.factory || this.addFactorySettingsLocal) {
                                                KswSettings.this.saveConfig(parser.getName(), parser.nextText());
                                            } else {
                                                String key = parser.getName();
                                                String value = KswSettings.this.mFactorySettings.getStringValue(key);
                                                if (value == null) {
                                                    value = KswSettings.this.mFactorySettings.getIntValue(key) + "";
                                                }
                                                Log.m68i("FactorySettings", "FactorySettings data key:" + key + " - value:" + value);
                                                KswSettings.this.saveConfig(key, value);
                                            }
                                        }
                                        if (!parser.getName().equalsIgnoreCase("setings")) {
                                        }
                                        break;
                                    case 3:
                                        if (!parser.getName().equalsIgnoreCase("setings")) {
                                            if (!parser.getName().equalsIgnoreCase("factory")) {
                                                if (!parser.getName().equalsIgnoreCase("SupportNaviAppList")) {
                                                    if (!parser.getName().equalsIgnoreCase("CarDisplayParam")) {
                                                        if (!parser.getName().equalsIgnoreCase("CANBusProtocol")) {
                                                            if (!parser.getName().equalsIgnoreCase("SupportLanguageList")) {
                                                                if (!parser.getName().equalsIgnoreCase("SupportUIList")) {
                                                                    if (!parser.getName().equalsIgnoreCase("SupportDvrAppList")) {
                                                                        break;
                                                                    } else {
                                                                        KswSettings.this.saveJson("SupportDvrAppList", gson.toJson(this.dataList));
                                                                        this.dataList.clear();
                                                                        this.dvrList = z;
                                                                        break;
                                                                    }
                                                                } else {
                                                                    KswSettings.this.saveJson("SupportUIList", gson.toJson(this.dataList));
                                                                    this.uiType = z;
                                                                    this.dataList.clear();
                                                                    break;
                                                                }
                                                            } else {
                                                                Log.m72d(KswSettings.TAG, "dataList = " + gson.toJson(this.dataList) + " codeList = " + gson.toJson(this.codeList) + "   idList = " + gson.toJson(this.idList));
                                                                if (Build.DISPLAY.contains("8937")) {
                                                                    this.dataList.clear();
                                                                    this.idList.clear();
                                                                    this.dataList.add("\u7b80\u4f53\u4e2d\u6587");
                                                                    this.idList.add("0");
                                                                    KswSettings.this.saveJson("SupportLanguageList", gson.toJson(this.dataList));
                                                                    KswSettings.this.saveJson(KswSettings.CODE_LIST, gson.toJson(this.codeList));
                                                                    KswSettings.this.saveJson(KswSettings.LANGUAGE_ID, gson.toJson(this.idList));
                                                                } else {
                                                                    KswSettings.this.saveJson("SupportLanguageList", gson.toJson(this.dataList));
                                                                    KswSettings.this.saveJson(KswSettings.CODE_LIST, gson.toJson(this.codeList));
                                                                    KswSettings.this.saveJson(KswSettings.LANGUAGE_ID, gson.toJson(this.idList));
                                                                }
                                                                this.language = z;
                                                                this.dataList.clear();
                                                                this.idList.clear();
                                                                this.codeList.clear();
                                                                break;
                                                            }
                                                        } else {
                                                            KswSettings.this.saveJson("CANBusProtocol", gson.toJson(this.dataList));
                                                            KswSettings.this.saveJson(KswSettings.CAN_ID, gson.toJson(this.idList));
                                                            KswSettings.this.setUpProtocolForInit(this.idList);
                                                            this.canType = z;
                                                            this.idList.clear();
                                                            this.dataList.clear();
                                                            break;
                                                        }
                                                    } else {
                                                        KswSettings.this.saveJson("CarDisplayParam", gson.toJson(this.dataList));
                                                        KswSettings.this.saveJson(KswSettings.CAR_DISPLAY_ID, gson.toJson(this.idList));
                                                        this.carType = z;
                                                        this.idList.clear();
                                                        this.dataList.clear();
                                                        break;
                                                    }
                                                } else {
                                                    KswSettings.this.saveJson("SupportNaviAppList", gson.toJson(this.dataList));
                                                    this.naviList = z;
                                                    this.dataList.clear();
                                                    break;
                                                }
                                            } else {
                                                this.factory = z;
                                                break;
                                            }
                                        } else {
                                            this.setting = z;
                                            continue;
                                        }
                                    default:
                                        continue;
                                }
                            }
                            eventType = parser.next();
                            i = 3;
                            z = false;
                        }
                        KswSettings.this.onlyLanguageId = false;
                        if (!this.mConfig.getAbsolutePath().equals(KswSettings.this.defaultConfig.getAbsolutePath())) {
                            CopyFile.copyTo(this.mConfig, KswSettings.this.defaultConfig);
                            KswSettings.this.mHandler.post(new Runnable() { // from class: com.wits.pms.custom.-$$Lambda$KswSettings$InitConfigRunnable$_9H3V7Punr9ZRjsLwtgyMAkCm50
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Toast.makeText(KswSettings.this.mContext, (int) C3580R.string.import_config_success, 1).show();
                                }
                            });
                        }
                        KswSettings.this.setInt("initKswConfig", 1);
                        inputStream.close();
                        fr.close();
                    } catch (Exception e2) {
                        Log.m69e(KswSettings.TAG, "error", e2);
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fr != null) {
                            fr.close();
                        }
                    }
                } catch (Exception e3) {
                    Log.m69e(KswSettings.TAG, "close error:", e3);
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        inputStream.close();
                    } catch (Exception e4) {
                        Log.m69e(KswSettings.TAG, "close error:", e4);
                        throw th;
                    }
                }
                if (0 != 0) {
                    fr.close();
                }
                throw th;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.wits.pms.custom.KswSettings$5] */
    private void checkDirectUpdate() {
        new Thread() { // from class: com.wits.pms.custom.KswSettings.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int i = 0;
                while (i < 150) {
                    if (KswSettings.this.directUpdateFile != null && KswSettings.this.directUpdateFile.exists()) {
                        KswSettings.this.updateConfig(KswSettings.this.directUpdateFile);
                        return;
                    } else {
                        i++;
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }.start();
    }

    public void check(Context context, String path) {
        File file = new File(path + "/OEM/factory_config.xml");
        if (file.exists()) {
            Log.m68i(TAG, "Checking factory_config.xml go to update: " + file.getAbsolutePath());
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpProtocolForInit(ArrayList<String> idList) {
        String protocolData = getSettingsString("ProtocolData");
        if (!TextUtils.isEmpty(protocolData)) {
            setInt(PROTOCOL_KEY, idList.indexOf(protocolData));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveConfig(String key, String value) {
        if (this.onlyLanguageId) {
            return;
        }
        char c = '\uffff';
        switch (key.hashCode()) {
            case -1548945544:
                if (key.equals("Language")) {
                    c = '\b';
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
            case 91785770:
                if (key.equals("Reverse_time")) {
                    c = 7;
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
            case 7:
                setString(key, value);
                return;
            case '\b':
                try {
                    int intValue = Integer.parseInt(value);
                    setInt(key, intValue);
                    return;
                } catch (Exception e) {
                    setString(key, value);
                    return;
                }
            default:
                try {
                    int intValue2 = Integer.parseInt(value);
                    setInt(key, intValue2);
                    return;
                } catch (Exception e2) {
                    return;
                }
        }
    }

    public byte[] fileToByteArray(File mFile) {
        FileInputStream fileInputStream = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        byte[] bt = null;
        try {
            try {
                try {
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (out != null) {
                    out.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        if (mFile.exists() && !mFile.isDirectory()) {
            fileInputStream = new FileInputStream(mFile);
            in = new BufferedInputStream(fileInputStream);
            out = new ByteArrayOutputStream();
            byte[] temp = new byte[1048576];
            while (true) {
                int size = in.read(temp);
                if (size == -1) {
                    break;
                }
                out.write(temp, 0, size);
            }
            bt = out.toByteArray();
            out.close();
            fileInputStream.close();
            in.close();
            return bt;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean CheckBOM(byte[] bytes) {
        if (bytes.length <= 3 || 239 != (bytes[0] & 255) || 187 != (bytes[1] & 255) || 191 != (bytes[2] & 255)) {
            return false;
        }
        return true;
    }

    public String getUiSavePath() {
        return uiSave;
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("KswSettings dump");
        Set<String> iKeys = getIntKeysFromSp();
        Set<String> sKeys = getStringKeysFromSp();
        pw.println("intKeys size is " + iKeys.size() + SettingsStringUtil.DELIMITER);
        int i = 0;
        for (String key : iKeys) {
            pw.println("intKeys[" + i + "]=" + key);
            i++;
        }
        pw.println("");
        pw.println("stringKeys size is " + sKeys.size() + SettingsStringUtil.DELIMITER);
        int j = 0;
        for (String key2 : sKeys) {
            pw.println("stringKeys[" + j + "]=" + key2);
            j++;
        }
        pw.println("");
        pw.println("onlyLanguageId is " + this.onlyLanguageId);
    }
}
