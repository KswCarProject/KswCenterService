package android.util;

import android.hardware.Sensor;
import android.net.Uri;
import android.provider.Settings;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class SeempLog {
    public static final int SEEMP_API_android_provider_Settings__get_ACCELEROMETER_ROTATION_ = 96;
    public static final int SEEMP_API_android_provider_Settings__get_ADB_ENABLED_ = 98;
    public static final int SEEMP_API_android_provider_Settings__get_AIRPLANE_MODE_ON_ = 101;
    public static final int SEEMP_API_android_provider_Settings__get_AIRPLANE_MODE_RADIOS_ = 102;
    public static final int SEEMP_API_android_provider_Settings__get_ALARM_ALERT_ = 103;

    /* renamed from: SEEMP_API_android_provider_Settings__get_ALWAYS_FINISH_ACTIVITIES_ */
    public static final int f292xb0d4c91a = 105;
    public static final int SEEMP_API_android_provider_Settings__get_ANDROID_ID_ = 7;

    /* renamed from: SEEMP_API_android_provider_Settings__get_ANIMATOR_DURATION_SCALE_ */
    public static final int f293xb9a7ff24 = 107;

    /* renamed from: SEEMP_API_android_provider_Settings__get_APPEND_FOR_LAST_AUDIBLE_ */
    public static final int f294xcaf79d47 = 171;
    public static final int SEEMP_API_android_provider_Settings__get_AUTO_TIME_ = 123;
    public static final int SEEMP_API_android_provider_Settings__get_AUTO_TIME_ZONE_ = 124;

    /* renamed from: SEEMP_API_android_provider_Settings__get_BLUETOOTH_DISCOVERABILITY_ */
    public static final int f295x2d7ed50b = 127;

    /* renamed from: SEEMP_API_android_provider_Settings__get_BLUETOOTH_DISCOVERABILITY_TIMEOUT_ */
    public static final int f296xd1aae949 = 128;
    public static final int SEEMP_API_android_provider_Settings__get_BLUETOOTH_ON_ = 129;
    public static final int SEEMP_API_android_provider_Settings__get_DATA_ROAMING_ = 142;
    public static final int SEEMP_API_android_provider_Settings__get_DATE_FORMAT_ = 125;
    public static final int SEEMP_API_android_provider_Settings__get_DEBUG_APP_ = 99;
    public static final int SEEMP_API_android_provider_Settings__get_DEVICE_PROVISIONED_ = 130;
    public static final int SEEMP_API_android_provider_Settings__get_DIM_SCREEN_ = 115;
    public static final int SEEMP_API_android_provider_Settings__get_DTMF_TONE_WHEN_DIALING_ = 132;
    public static final int SEEMP_API_android_provider_Settings__get_END_BUTTON_BEHAVIOR_ = 133;
    public static final int SEEMP_API_android_provider_Settings__get_FONT_SCALE_ = 109;

    /* renamed from: SEEMP_API_android_provider_Settings__get_HAPTIC_FEEDBACK_ENABLED_ */
    public static final int f297x7ebff82c = 159;
    public static final int SEEMP_API_android_provider_Settings__get_HTTP_PROXY_ = 143;

    /* renamed from: SEEMP_API_android_provider_Settings__get_INSTALL_NON_MARKET_APPS_ */
    public static final int f298x1e7c23fc = 136;

    /* renamed from: SEEMP_API_android_provider_Settings__get_LOCATION_PROVIDERS_ALLOWED_ */
    public static final int f299x4e9b9f42 = 137;
    public static final int SEEMP_API_android_provider_Settings__get_LOCK_PATTERN_ENABLED_ = 138;

    /* renamed from: SEEMP_API_android_provider_Settings__get_LOCK_PATTERN_TACTILE_FEEDBACK_ENABLED_ */
    public static final int f300x53bfa056 = 139;
    public static final int SEEMP_API_android_provider_Settings__get_LOCK_PATTERN_VISIBLE_ = 140;
    public static final int SEEMP_API_android_provider_Settings__get_LOGGING_ID_ = 106;
    public static final int SEEMP_API_android_provider_Settings__get_MODE_RINGER_ = 135;

    /* renamed from: SEEMP_API_android_provider_Settings__get_MODE_RINGER_STREAMS_AFFECTED_ */
    public static final int f301xf531a8bd = 168;
    public static final int SEEMP_API_android_provider_Settings__get_MUTE_STREAMS_AFFECTED_ = 169;
    public static final int SEEMP_API_android_provider_Settings__get_NETWORK_PREFERENCE_ = 141;
    public static final int SEEMP_API_android_provider_Settings__get_NEXT_ALARM_FORMATTED_ = 104;
    public static final int SEEMP_API_android_provider_Settings__get_NOTIFICATION_SOUND_ = 170;

    /* renamed from: SEEMP_API_android_provider_Settings__get_PARENTAL_CONTROL_ENABLED_ */
    public static final int f302x9f2b1f4e = 144;

    /* renamed from: SEEMP_API_android_provider_Settings__get_PARENTAL_CONTROL_LAST_UPDATE_ */
    public static final int f303x223d81fd = 145;

    /* renamed from: SEEMP_API_android_provider_Settings__get_PARENTAL_CONTROL_REDIRECT_URL_ */
    public static final int f304xa749f83 = 146;
    public static final int SEEMP_API_android_provider_Settings__get_RADIO_BLUETOOTH_ = 147;
    public static final int SEEMP_API_android_provider_Settings__get_RADIO_CELL_ = 148;
    public static final int SEEMP_API_android_provider_Settings__get_RADIO_NFC_ = 149;
    public static final int SEEMP_API_android_provider_Settings__get_RADIO_WIFI_ = 150;
    public static final int SEEMP_API_android_provider_Settings__get_RINGTONE_ = 134;
    public static final int SEEMP_API_android_provider_Settings__get_SCREEN_BRIGHTNESS_ = 110;
    public static final int SEEMP_API_android_provider_Settings__get_SCREEN_BRIGHTNESS_MODE_ = 111;

    /* renamed from: SEEMP_API_android_provider_Settings__get_SCREEN_BRIGHTNESS_MODE_AUTOMATIC_ */
    public static final int f305x726aa019 = 112;

    /* renamed from: SEEMP_API_android_provider_Settings__get_SCREEN_BRIGHTNESS_MODE_MANUAL_ */
    public static final int f306xfb325f14 = 113;
    public static final int SEEMP_API_android_provider_Settings__get_SCREEN_OFF_TIMEOUT_ = 114;
    public static final int SEEMP_API_android_provider_Settings__get_SETTINGS_CLASSNAME_ = 152;
    public static final int SEEMP_API_android_provider_Settings__get_SETUP_WIZARD_HAS_RUN_ = 131;

    /* renamed from: SEEMP_API_android_provider_Settings__get_SHOW_GTALK_SERVICE_STATUS_ */
    public static final int f307xdaac6071 = 121;
    public static final int SEEMP_API_android_provider_Settings__get_SHOW_PROCESSES_ = 119;
    public static final int SEEMP_API_android_provider_Settings__get_SHOW_WEB_SUGGESTIONS_ = 120;
    public static final int SEEMP_API_android_provider_Settings__get_SOUND_EFFECTS_ENABLED_ = 167;

    /* renamed from: SEEMP_API_android_provider_Settings__get_STAY_ON_WHILE_PLUGGED_IN_ */
    public static final int f308x6a249a0f = 117;

    /* renamed from: SEEMP_API_android_provider_Settings__get_SYS_PROP_SETTING_VERSION_ */
    public static final int f309xc52771a4 = 151;
    public static final int SEEMP_API_android_provider_Settings__get_TEXT_AUTO_CAPS_ = 153;
    public static final int SEEMP_API_android_provider_Settings__get_TEXT_AUTO_PUNCTUATE_ = 154;
    public static final int SEEMP_API_android_provider_Settings__get_TEXT_AUTO_REPLACE_ = 155;
    public static final int SEEMP_API_android_provider_Settings__get_TEXT_SHOW_PASSWORD_ = 156;
    public static final int SEEMP_API_android_provider_Settings__get_TIME_12_24_ = 126;

    /* renamed from: SEEMP_API_android_provider_Settings__get_TRANSITION_ANIMATION_SCALE_ */
    public static final int f310xbe4402de = 116;

    /* renamed from: SEEMP_API_android_provider_Settings__get_USB_MASS_STORAGE_ENABLED_ */
    public static final int f311x925b4096 = 157;
    public static final int SEEMP_API_android_provider_Settings__get_USER_ROTATION_ = 97;
    public static final int SEEMP_API_android_provider_Settings__get_USE_GOOGLE_MAIL_ = 122;
    public static final int SEEMP_API_android_provider_Settings__get_VIBRATE_ON_ = 158;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_ALARM_ = 160;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_BLUETOOTH_SCO_ = 161;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_MUSIC_ = 162;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_NOTIFICATION_ = 163;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_RING_ = 164;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_SYSTEM_ = 165;
    public static final int SEEMP_API_android_provider_Settings__get_VOLUME_VOICE_ = 166;
    public static final int SEEMP_API_android_provider_Settings__get_WAIT_FOR_DEBUGGER_ = 100;
    public static final int SEEMP_API_android_provider_Settings__get_WALLPAPER_ACTIVITY_ = 118;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_MAX_DHCP_RETRY_COUNT_ */
    public static final int f312x9f33148c = 172;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS_ */
    public static final int f313xebd29a6f = 173;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON_ */
    public static final int f314xdd56bf2e = 174;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY_ */
    public static final int f315x3770fb9e = 175;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_NUM_OPEN_NETWORKS_KEPT_ */
    public static final int f316x2c0cd495 = 176;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_ON_ = 177;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_SLEEP_POLICY_ = 178;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_SLEEP_POLICY_DEFAULT_ */
    public static final int f317xa8447395 = 179;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_SLEEP_POLICY_NEVER_ */
    public static final int f318x9c96ee6a = 180;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED_ */
    public static final int f319xe97d0edf = 181;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_STATIC_DNS1_ = 182;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_STATIC_DNS2_ = 183;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_STATIC_GATEWAY_ = 184;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_STATIC_IP_ = 185;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_STATIC_NETMASK_ = 186;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_USE_STATIC_IP_ = 187;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_ACCEPTABLE_PACKET_LOSS_PERCENTAGE_ */
    public static final int f320x34833e27 = 188;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_AP_COUNT_ = 189;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_BACKGROUND_CHECK_DELAY_MS_ */
    public static final int f321x604e2b39 = 190;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_BACKGROUND_CHECK_ENABLED_ */
    public static final int f322x976fd142 = 191;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_BACKGROUND_CHECK_TIMEOUT_MS_ */
    public static final int f323xb7036d17 = 192;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_INITIAL_IGNORED_PING_COUNT_ */
    public static final int f324xa774b751 = 193;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_MAX_AP_CHECKS_ */
    public static final int f325xba0e4563 = 194;
    public static final int SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_ON_ = 195;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_PING_COUNT_ */
    public static final int f326x86ac0cf9 = 196;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_PING_DELAY_MS_ */
    public static final int f327x8332de54 = 197;

    /* renamed from: SEEMP_API_android_provider_Settings__get_WIFI_WATCHDOG_PING_TIMEOUT_MS_ */
    public static final int f328xb387c572 = 198;
    public static final int SEEMP_API_android_provider_Settings__get_WINDOW_ANIMATION_SCALE_ = 108;
    public static final int SEEMP_API_android_provider_Settings__put_ACCELEROMETER_ROTATION_ = 199;
    public static final int SEEMP_API_android_provider_Settings__put_ADB_ENABLED_ = 201;
    public static final int SEEMP_API_android_provider_Settings__put_AIRPLANE_MODE_ON_ = 204;
    public static final int SEEMP_API_android_provider_Settings__put_AIRPLANE_MODE_RADIOS_ = 205;
    public static final int SEEMP_API_android_provider_Settings__put_ALARM_ALERT_ = 206;

    /* renamed from: SEEMP_API_android_provider_Settings__put_ALWAYS_FINISH_ACTIVITIES_ */
    public static final int f329x31b44793 = 208;
    public static final int SEEMP_API_android_provider_Settings__put_ANDROID_ID_ = 209;

    /* renamed from: SEEMP_API_android_provider_Settings__put_ANIMATOR_DURATION_SCALE_ */
    public static final int f330xc6124d8b = 211;

    /* renamed from: SEEMP_API_android_provider_Settings__put_APPEND_FOR_LAST_AUDIBLE_ */
    public static final int f331xd761ebae = 275;
    public static final int SEEMP_API_android_provider_Settings__put_AUTO_TIME_ = 227;
    public static final int SEEMP_API_android_provider_Settings__put_AUTO_TIME_ZONE_ = 228;

    /* renamed from: SEEMP_API_android_provider_Settings__put_BLUETOOTH_DISCOVERABILITY_ */
    public static final int f332xc88f25b2 = 231;

    /* renamed from: SEEMP_API_android_provider_Settings__put_BLUETOOTH_DISCOVERABILITY_TIMEOUT_ */
    public static final int f333x780fa2f0 = 232;
    public static final int SEEMP_API_android_provider_Settings__put_BLUETOOTH_ON_ = 233;
    public static final int SEEMP_API_android_provider_Settings__put_DATA_ROAMING_ = 246;
    public static final int SEEMP_API_android_provider_Settings__put_DATE_FORMAT_ = 229;
    public static final int SEEMP_API_android_provider_Settings__put_DEBUG_APP_ = 202;
    public static final int SEEMP_API_android_provider_Settings__put_DEVICE_PROVISIONED_ = 234;
    public static final int SEEMP_API_android_provider_Settings__put_DIM_SCREEN_ = 219;
    public static final int SEEMP_API_android_provider_Settings__put_DTMF_TONE_WHEN_DIALING_ = 236;
    public static final int SEEMP_API_android_provider_Settings__put_END_BUTTON_BEHAVIOR_ = 237;
    public static final int SEEMP_API_android_provider_Settings__put_FONT_SCALE_ = 213;

    /* renamed from: SEEMP_API_android_provider_Settings__put_HAPTIC_FEEDBACK_ENABLED_ */
    public static final int f334x8b2a4693 = 263;
    public static final int SEEMP_API_android_provider_Settings__put_HTTP_PROXY_ = 247;

    /* renamed from: SEEMP_API_android_provider_Settings__put_INSTALL_NON_MARKET_APPS_ */
    public static final int f335x2ae67263 = 240;

    /* renamed from: SEEMP_API_android_provider_Settings__put_LOCATION_PROVIDERS_ALLOWED_ */
    public static final int f336x1595637b = 241;
    public static final int SEEMP_API_android_provider_Settings__put_LOCK_PATTERN_ENABLED_ = 242;

    /* renamed from: SEEMP_API_android_provider_Settings__put_LOCK_PATTERN_TACTILE_FEEDBACK_ENABLED_ */
    public static final int f337x60512e7d = 243;
    public static final int SEEMP_API_android_provider_Settings__put_LOCK_PATTERN_VISIBLE_ = 244;
    public static final int SEEMP_API_android_provider_Settings__put_LOGGING_ID_ = 210;
    public static final int SEEMP_API_android_provider_Settings__put_MODE_RINGER_ = 239;

    /* renamed from: SEEMP_API_android_provider_Settings__put_MODE_RINGER_STREAMS_AFFECTED_ */
    public static final int f338xe4cb42b6 = 272;
    public static final int SEEMP_API_android_provider_Settings__put_MUTE_STREAMS_AFFECTED_ = 273;
    public static final int SEEMP_API_android_provider_Settings__put_NETWORK_PREFERENCE_ = 245;
    public static final int SEEMP_API_android_provider_Settings__put_NEXT_ALARM_FORMATTED_ = 207;
    public static final int SEEMP_API_android_provider_Settings__put_NOTIFICATION_SOUND_ = 274;

    /* renamed from: SEEMP_API_android_provider_Settings__put_PARENTAL_CONTROL_ENABLED_ */
    public static final int f339x200a9dc7 = 248;

    /* renamed from: SEEMP_API_android_provider_Settings__put_PARENTAL_CONTROL_LAST_UPDATE_ */
    public static final int f340x11d71bf6 = 249;

    /* renamed from: SEEMP_API_android_provider_Settings__put_PARENTAL_CONTROL_REDIRECT_URL_ */
    public static final int f341xe0e44aa = 250;
    public static final int SEEMP_API_android_provider_Settings__put_RADIO_BLUETOOTH_ = 251;
    public static final int SEEMP_API_android_provider_Settings__put_RADIO_CELL_ = 252;
    public static final int SEEMP_API_android_provider_Settings__put_RADIO_NFC_ = 253;
    public static final int SEEMP_API_android_provider_Settings__put_RADIO_WIFI_ = 254;
    public static final int SEEMP_API_android_provider_Settings__put_RINGTONE_ = 238;
    public static final int SEEMP_API_android_provider_Settings__put_SCREEN_BRIGHTNESS_ = 214;
    public static final int SEEMP_API_android_provider_Settings__put_SCREEN_BRIGHTNESS_MODE_ = 215;

    /* renamed from: SEEMP_API_android_provider_Settings__put_SCREEN_BRIGHTNESS_MODE_AUTOMATIC_ */
    public static final int f342x67449592 = 216;

    /* renamed from: SEEMP_API_android_provider_Settings__put_SCREEN_BRIGHTNESS_MODE_MANUAL_ */
    public static final int f343xfecc043b = 217;
    public static final int SEEMP_API_android_provider_Settings__put_SCREEN_OFF_TIMEOUT_ = 218;
    public static final int SEEMP_API_android_provider_Settings__put_SETTINGS_CLASSNAME_ = 256;
    public static final int SEEMP_API_android_provider_Settings__put_SETUP_WIZARD_HAS_RUN_ = 235;

    /* renamed from: SEEMP_API_android_provider_Settings__put_SHOW_GTALK_SERVICE_STATUS_ */
    public static final int f344x75bcb118 = 225;
    public static final int SEEMP_API_android_provider_Settings__put_SHOW_PROCESSES_ = 223;
    public static final int SEEMP_API_android_provider_Settings__put_SHOW_WEB_SUGGESTIONS_ = 224;
    public static final int SEEMP_API_android_provider_Settings__put_SOUND_EFFECTS_ENABLED_ = 271;

    /* renamed from: SEEMP_API_android_provider_Settings__put_STAY_ON_WHILE_PLUGGED_IN_ */
    public static final int f345xeb041888 = 221;

    /* renamed from: SEEMP_API_android_provider_Settings__put_SYS_PROP_SETTING_VERSION_ */
    public static final int f346x4606f01d = 255;
    public static final int SEEMP_API_android_provider_Settings__put_TEXT_AUTO_CAPS_ = 257;
    public static final int SEEMP_API_android_provider_Settings__put_TEXT_AUTO_PUNCTUATE_ = 258;
    public static final int SEEMP_API_android_provider_Settings__put_TEXT_AUTO_REPLACE_ = 259;
    public static final int SEEMP_API_android_provider_Settings__put_TEXT_SHOW_PASSWORD_ = 260;
    public static final int SEEMP_API_android_provider_Settings__put_TIME_12_24_ = 230;

    /* renamed from: SEEMP_API_android_provider_Settings__put_TRANSITION_ANIMATION_SCALE_ */
    public static final int f347x853dc717 = 220;

    /* renamed from: SEEMP_API_android_provider_Settings__put_USB_MASS_STORAGE_ENABLED_ */
    public static final int f348x133abf0f = 261;
    public static final int SEEMP_API_android_provider_Settings__put_USER_ROTATION_ = 200;
    public static final int SEEMP_API_android_provider_Settings__put_USE_GOOGLE_MAIL_ = 226;
    public static final int SEEMP_API_android_provider_Settings__put_VIBRATE_ON_ = 262;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_ALARM_ = 264;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_BLUETOOTH_SCO_ = 265;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_MUSIC_ = 266;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_NOTIFICATION_ = 267;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_RING_ = 268;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_SYSTEM_ = 269;
    public static final int SEEMP_API_android_provider_Settings__put_VOLUME_VOICE_ = 270;
    public static final int SEEMP_API_android_provider_Settings__put_WAIT_FOR_DEBUGGER_ = 203;
    public static final int SEEMP_API_android_provider_Settings__put_WALLPAPER_ACTIVITY_ = 222;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_MAX_DHCP_RETRY_COUNT_ */
    public static final int f349x3a436533 = 276;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS_ */
    public static final int f350xc121e3d6 = 277;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON_ */
    public static final int f351xbbd5f95 = 278;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY_ */
    public static final int f352x9af18c97 = 279;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_NUM_OPEN_NETWORKS_KEPT_ */
    public static final int f353x444b977c = 280;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_ON_ = 281;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_SLEEP_POLICY_ = 282;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_SLEEP_POLICY_DEFAULT_ */
    public static final int f354x4354c43c = 283;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_SLEEP_POLICY_NEVER_ */
    public static final int f355xa9013cd1 = 284;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED_ */
    public static final int f356xf60e9d06 = 285;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_STATIC_DNS1_ = 286;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_STATIC_DNS2_ = 287;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_STATIC_GATEWAY_ = 288;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_STATIC_IP_ = 289;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_STATIC_NETMASK_ = 290;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_USE_STATIC_IP_ = 291;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_ACCEPTABLE_PACKET_LOSS_PERCENTAGE_ */
    public static final int f357x9d2878e = 292;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_AP_COUNT_ = 293;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_BACKGROUND_CHECK_DELAY_MS_ */
    public static final int f358x8eb4cba0 = 294;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_BACKGROUND_CHECK_ENABLED_ */
    public static final int f359x1d1007fb = 295;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_BACKGROUND_CHECK_TIMEOUT_MS_ */
    public static final int f360xe6438fbe = 296;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_INITIAL_IGNORED_PING_COUNT_ */
    public static final int f361x45e223ca = 297;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_MAX_AP_CHECKS_ */
    public static final int f362xd24d084a = 298;
    public static final int SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_ON_ = 299;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_PING_COUNT_ */
    public static final int f363x78b8b72 = 300;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_PING_DELAY_MS_ */
    public static final int f364x9b71a13b = 301;

    /* renamed from: SEEMP_API_android_provider_Settings__put_WIFI_WATCHDOG_PING_TIMEOUT_MS_ */
    public static final int f365xb7216a99 = 302;
    public static final int SEEMP_API_android_provider_Settings__put_WINDOW_ANIMATION_SCALE_ = 212;
    private static final Map<String, Integer> value_to_get_map = new HashMap(198);
    private static final Map<String, Integer> value_to_put_map;

    public static native int seemp_println_native(int i, String str);

    private SeempLog() {
    }

    public static int record(int api) {
        return seemp_println_native(api, "");
    }

    public static int record_str(int api, String msg) {
        if (msg != null) {
            return seemp_println_native(api, msg);
        }
        return seemp_println_native(api, "");
    }

    public static int record_sensor(int api, Sensor sensor) {
        if (sensor != null) {
            return seemp_println_native(api, "sensor=" + sensor.getType());
        }
        return seemp_println_native(api, "sensor=-1");
    }

    public static int record_sensor_rate(int api, Sensor sensor, int rate) {
        if (sensor != null) {
            return seemp_println_native(api, "sensor=" + sensor.getType() + ",rate=" + rate);
        }
        return seemp_println_native(api, "sensor=-1,rate=" + rate);
    }

    public static int record_uri(int api, Uri uri) {
        if (uri != null) {
            return seemp_println_native(api, "uri, " + uri.toString());
        }
        return seemp_println_native(api, "uri, null");
    }

    public static int record_vg_layout(int api, ViewGroup.LayoutParams params) {
        try {
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) params;
            if (p != null) {
                return seemp_println_native(api, "window_type=" + p.type + ",window_flag=" + p.flags);
            }
            return seemp_println_native(api, "");
        } catch (ClassCastException e) {
            return seemp_println_native(api, "");
        }
    }

    static {
        value_to_get_map.put(Settings.System.NOTIFICATION_SOUND, 170);
        value_to_get_map.put(Settings.System.DTMF_TONE_WHEN_DIALING, 132);
        value_to_get_map.put("lock_pattern_autolock", 138);
        value_to_get_map.put("wifi_max_dhcp_retry_count", 172);
        value_to_get_map.put("auto_time", 123);
        value_to_get_map.put(Settings.System.SETUP_WIZARD_HAS_RUN, 131);
        value_to_get_map.put("wifi_watchdog_background_check_timeout_ms", 192);
        value_to_get_map.put("location_providers_allowed", 137);
        value_to_get_map.put(Settings.System.ALARM_ALERT, 103);
        value_to_get_map.put(Settings.System.VIBRATE_ON, 158);
        value_to_get_map.put("usb_mass_storage_enabled", 157);
        value_to_get_map.put("wifi_watchdog_ping_delay_ms", 197);
        value_to_get_map.put(Settings.System.FONT_SCALE, 109);
        value_to_get_map.put("wifi_watchdog_ap_count", 189);
        value_to_get_map.put("always_finish_activities", 105);
        value_to_get_map.put(Settings.System.ACCELEROMETER_ROTATION, 96);
        value_to_get_map.put("wifi_watchdog_ping_timeout_ms", 198);
        value_to_get_map.put(Settings.System.VOLUME_NOTIFICATION, 163);
        value_to_get_map.put("airplane_mode_on", 101);
        value_to_get_map.put("wifi_watchdog_background_check_delay_ms", 190);
        value_to_get_map.put(Settings.System.WIFI_STATIC_IP, 185);
        value_to_get_map.put("bluetooth", 147);
        value_to_get_map.put(Settings.System.BLUETOOTH_DISCOVERABILITY_TIMEOUT, 128);
        value_to_get_map.put(Settings.System.VOLUME_RING, 164);
        value_to_get_map.put(Settings.System.MODE_RINGER_STREAMS_AFFECTED, 168);
        value_to_get_map.put(Settings.System.VOLUME_SYSTEM, 165);
        value_to_get_map.put(Settings.System.SCREEN_OFF_TIMEOUT, 114);
        value_to_get_map.put("wifi", 150);
        value_to_get_map.put("auto_time_zone", 124);
        value_to_get_map.put(Settings.System.TEXT_AUTO_CAPS, 153);
        value_to_get_map.put(Settings.System.WALLPAPER_ACTIVITY, 118);
        value_to_get_map.put("animator_duration_scale", 107);
        value_to_get_map.put("wifi_num_open_networks_kept", 176);
        value_to_get_map.put("lock_pattern_visible_pattern", 140);
        value_to_get_map.put(Settings.System.VOLUME_VOICE, 166);
        value_to_get_map.put("debug_app", 99);
        value_to_get_map.put("wifi_on", 177);
        value_to_get_map.put(Settings.System.TEXT_SHOW_PASSWORD, 156);
        value_to_get_map.put("wifi_networks_available_repeat_delay", 175);
        value_to_get_map.put("wifi_sleep_policy", 178);
        value_to_get_map.put(Settings.System.VOLUME_MUSIC, 162);
        value_to_get_map.put("parental_control_last_update", 145);
        value_to_get_map.put("device_provisioned", 130);
        value_to_get_map.put("http_proxy", 143);
        value_to_get_map.put("android_id", 7);
        value_to_get_map.put("wifi_watchdog_max_ap_checks", 194);
        value_to_get_map.put(Settings.System.END_BUTTON_BEHAVIOR, 133);
        value_to_get_map.put(Settings.System.NEXT_ALARM_FORMATTED, 104);
        value_to_get_map.put("cell", 148);
        value_to_get_map.put("parental_control_enabled", 144);
        value_to_get_map.put("bluetooth_on", 129);
        value_to_get_map.put("window_animation_scale", 108);
        value_to_get_map.put("wifi_watchdog_background_check_enabled", 191);
        value_to_get_map.put(Settings.System.BLUETOOTH_DISCOVERABILITY, 127);
        value_to_get_map.put(Settings.System.WIFI_STATIC_DNS1, 182);
        value_to_get_map.put(Settings.System.WIFI_STATIC_DNS2, 183);
        value_to_get_map.put(Settings.System.HAPTIC_FEEDBACK_ENABLED, 159);
        value_to_get_map.put(Settings.System.SHOW_WEB_SUGGESTIONS, 120);
        value_to_get_map.put("parental_control_redirect_url", 146);
        value_to_get_map.put(Settings.System.DATE_FORMAT, 125);
        value_to_get_map.put("nfc", 149);
        value_to_get_map.put("airplane_mode_radios", 102);
        value_to_get_map.put("lock_pattern_tactile_feedback_enabled", 139);
        value_to_get_map.put(Settings.System.TIME_12_24, 126);
        value_to_get_map.put("wifi_watchdog_initial_ignored_ping_count", 193);
        value_to_get_map.put(Settings.System.VOLUME_BLUETOOTH_SCO, 161);
        value_to_get_map.put(Settings.System.USER_ROTATION, 97);
        value_to_get_map.put(Settings.System.WIFI_STATIC_GATEWAY, 184);
        value_to_get_map.put("stay_on_while_plugged_in", 117);
        value_to_get_map.put(Settings.System.SOUND_EFFECTS_ENABLED, 167);
        value_to_get_map.put("wifi_watchdog_ping_count", 196);
        value_to_get_map.put("data_roaming", 142);
        value_to_get_map.put("settings_classname", 152);
        value_to_get_map.put("transition_animation_scale", 116);
        value_to_get_map.put("wait_for_debugger", 100);
        value_to_get_map.put("install_non_market_apps", 136);
        value_to_get_map.put("adb_enabled", 98);
        value_to_get_map.put(Settings.System.WIFI_USE_STATIC_IP, 187);
        value_to_get_map.put(Settings.System.DIM_SCREEN, 115);
        value_to_get_map.put(Settings.System.VOLUME_ALARM, 160);
        value_to_get_map.put("wifi_watchdog_on", 195);
        value_to_get_map.put(Settings.System.WIFI_STATIC_NETMASK, 186);
        value_to_get_map.put("network_preference", 141);
        value_to_get_map.put("show_processes", 119);
        value_to_get_map.put(Settings.System.TEXT_AUTO_REPLACE, 155);
        value_to_get_map.put("wifi_networks_available_notification_on", 174);
        value_to_get_map.put(Settings.System.APPEND_FOR_LAST_AUDIBLE, 171);
        value_to_get_map.put(Settings.System.SHOW_GTALK_SERVICE_STATUS, 121);
        value_to_get_map.put(Settings.System.SCREEN_BRIGHTNESS, 110);
        value_to_get_map.put("use_google_mail", 122);
        value_to_get_map.put(Settings.System.RINGTONE, 134);
        value_to_get_map.put("logging_id", 106);
        value_to_get_map.put("mode_ringer", 135);
        value_to_get_map.put(Settings.System.MUTE_STREAMS_AFFECTED, 169);
        value_to_get_map.put("wifi_watchdog_acceptable_packet_loss_percentage", 188);
        value_to_get_map.put(Settings.System.TEXT_AUTO_PUNCTUATE, 154);
        value_to_get_map.put("wifi_mobile_data_transition_wakelock_timeout_ms", 173);
        value_to_get_map.put(Settings.System.SCREEN_BRIGHTNESS_MODE, 111);
        value_to_put_map = new HashMap(198);
        value_to_put_map.put(Settings.System.NOTIFICATION_SOUND, 274);
        value_to_put_map.put(Settings.System.DTMF_TONE_WHEN_DIALING, 236);
        value_to_put_map.put("lock_pattern_autolock", 242);
        value_to_put_map.put("wifi_max_dhcp_retry_count", 276);
        value_to_put_map.put("auto_time", 227);
        value_to_put_map.put(Settings.System.SETUP_WIZARD_HAS_RUN, 235);
        value_to_put_map.put("wifi_watchdog_background_check_timeout_ms", 296);
        value_to_put_map.put("location_providers_allowed", 241);
        value_to_put_map.put(Settings.System.ALARM_ALERT, 206);
        value_to_put_map.put(Settings.System.VIBRATE_ON, 262);
        value_to_put_map.put("usb_mass_storage_enabled", 261);
        value_to_put_map.put("wifi_watchdog_ping_delay_ms", 301);
        value_to_put_map.put(Settings.System.FONT_SCALE, 213);
        value_to_put_map.put("wifi_watchdog_ap_count", 293);
        value_to_put_map.put("always_finish_activities", 208);
        value_to_put_map.put(Settings.System.ACCELEROMETER_ROTATION, 199);
        value_to_put_map.put("wifi_watchdog_ping_timeout_ms", 302);
        value_to_put_map.put(Settings.System.VOLUME_NOTIFICATION, 267);
        value_to_put_map.put("airplane_mode_on", 204);
        value_to_put_map.put("wifi_watchdog_background_check_delay_ms", 294);
        value_to_put_map.put(Settings.System.WIFI_STATIC_IP, 289);
        value_to_put_map.put("bluetooth", 251);
        value_to_put_map.put(Settings.System.BLUETOOTH_DISCOVERABILITY_TIMEOUT, 232);
        value_to_put_map.put(Settings.System.VOLUME_RING, 268);
        value_to_put_map.put(Settings.System.MODE_RINGER_STREAMS_AFFECTED, 272);
        value_to_put_map.put(Settings.System.VOLUME_SYSTEM, 269);
        value_to_put_map.put(Settings.System.SCREEN_OFF_TIMEOUT, 218);
        value_to_put_map.put("wifi", 254);
        value_to_put_map.put("auto_time_zone", 228);
        value_to_put_map.put(Settings.System.TEXT_AUTO_CAPS, 257);
        value_to_put_map.put(Settings.System.WALLPAPER_ACTIVITY, 222);
        value_to_put_map.put("animator_duration_scale", 211);
        value_to_put_map.put("wifi_num_open_networks_kept", 280);
        value_to_put_map.put("lock_pattern_visible_pattern", 244);
        value_to_put_map.put(Settings.System.VOLUME_VOICE, 270);
        value_to_put_map.put("debug_app", 202);
        value_to_put_map.put("wifi_on", 281);
        value_to_put_map.put(Settings.System.TEXT_SHOW_PASSWORD, 260);
        value_to_put_map.put("wifi_networks_available_repeat_delay", 279);
        value_to_put_map.put("wifi_sleep_policy", 282);
        value_to_put_map.put(Settings.System.VOLUME_MUSIC, 266);
        value_to_put_map.put("parental_control_last_update", 249);
        value_to_put_map.put("device_provisioned", 234);
        value_to_put_map.put("http_proxy", 247);
        value_to_put_map.put("android_id", 209);
        value_to_put_map.put("wifi_watchdog_max_ap_checks", 298);
        value_to_put_map.put(Settings.System.END_BUTTON_BEHAVIOR, 237);
        value_to_put_map.put(Settings.System.NEXT_ALARM_FORMATTED, 207);
        value_to_put_map.put("cell", 252);
        value_to_put_map.put("parental_control_enabled", 248);
        value_to_put_map.put("bluetooth_on", 233);
        value_to_put_map.put("window_animation_scale", 212);
        value_to_put_map.put("wifi_watchdog_background_check_enabled", 295);
        value_to_put_map.put(Settings.System.BLUETOOTH_DISCOVERABILITY, 231);
        value_to_put_map.put(Settings.System.WIFI_STATIC_DNS1, 286);
        value_to_put_map.put(Settings.System.WIFI_STATIC_DNS2, 287);
        value_to_put_map.put(Settings.System.HAPTIC_FEEDBACK_ENABLED, 263);
        value_to_put_map.put(Settings.System.SHOW_WEB_SUGGESTIONS, 224);
        value_to_put_map.put("parental_control_redirect_url", 250);
        value_to_put_map.put(Settings.System.DATE_FORMAT, 229);
        value_to_put_map.put("nfc", 253);
        value_to_put_map.put("airplane_mode_radios", 205);
        value_to_put_map.put("lock_pattern_tactile_feedback_enabled", 243);
        value_to_put_map.put(Settings.System.TIME_12_24, 230);
        value_to_put_map.put("wifi_watchdog_initial_ignored_ping_count", 297);
        value_to_put_map.put(Settings.System.VOLUME_BLUETOOTH_SCO, 265);
        value_to_put_map.put(Settings.System.USER_ROTATION, 200);
        value_to_put_map.put(Settings.System.WIFI_STATIC_GATEWAY, 288);
        value_to_put_map.put("stay_on_while_plugged_in", 221);
        value_to_put_map.put(Settings.System.SOUND_EFFECTS_ENABLED, 271);
        value_to_put_map.put("wifi_watchdog_ping_count", 300);
        value_to_put_map.put("data_roaming", 246);
        value_to_put_map.put("settings_classname", 256);
        value_to_put_map.put("transition_animation_scale", 220);
        value_to_put_map.put("wait_for_debugger", 203);
        value_to_put_map.put("install_non_market_apps", 240);
        value_to_put_map.put("adb_enabled", 201);
        value_to_put_map.put(Settings.System.WIFI_USE_STATIC_IP, 291);
        value_to_put_map.put(Settings.System.DIM_SCREEN, 219);
        value_to_put_map.put(Settings.System.VOLUME_ALARM, 264);
        value_to_put_map.put("wifi_watchdog_on", 299);
        value_to_put_map.put(Settings.System.WIFI_STATIC_NETMASK, 290);
        value_to_put_map.put("network_preference", 245);
        value_to_put_map.put("show_processes", 223);
        value_to_put_map.put(Settings.System.TEXT_AUTO_REPLACE, 259);
        value_to_put_map.put("wifi_networks_available_notification_on", 278);
        value_to_put_map.put(Settings.System.APPEND_FOR_LAST_AUDIBLE, 275);
        value_to_put_map.put(Settings.System.SHOW_GTALK_SERVICE_STATUS, 225);
        value_to_put_map.put(Settings.System.SCREEN_BRIGHTNESS, 214);
        value_to_put_map.put("use_google_mail", 226);
        value_to_put_map.put(Settings.System.RINGTONE, 238);
        value_to_put_map.put("logging_id", 210);
        value_to_put_map.put("mode_ringer", 239);
        value_to_put_map.put(Settings.System.MUTE_STREAMS_AFFECTED, 273);
        value_to_put_map.put("wifi_watchdog_acceptable_packet_loss_percentage", 292);
        value_to_put_map.put(Settings.System.TEXT_AUTO_PUNCTUATE, 258);
        value_to_put_map.put("wifi_mobile_data_transition_wakelock_timeout_ms", 277);
        value_to_put_map.put(Settings.System.SCREEN_BRIGHTNESS_MODE, 215);
    }

    public static int getSeempGetApiIdFromValue(String v) {
        Integer result = value_to_get_map.get(v);
        if (result == null) {
            result = -1;
        }
        return result.intValue();
    }

    public static int getSeempPutApiIdFromValue(String v) {
        Integer result = value_to_put_map.get(v);
        if (result == null) {
            result = -1;
        }
        return result.intValue();
    }
}
