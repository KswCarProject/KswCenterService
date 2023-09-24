package android.util;

import android.p007os.WorkSource;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class StatsLogInternal {
    public static final int ACTIVITY_FOREGROUND_STATE_CHANGED = 42;
    public static final int ACTIVITY_FOREGROUND_STATE_CHANGED__STATE__BACKGROUND = 0;
    public static final int ACTIVITY_FOREGROUND_STATE_CHANGED__STATE__FOREGROUND = 1;
    public static final int ACTIVITY_MANAGER_SLEEP_STATE_CHANGED = 14;
    public static final int ACTIVITY_MANAGER_SLEEP_STATE_CHANGED__STATE__ASLEEP = 1;
    public static final int ACTIVITY_MANAGER_SLEEP_STATE_CHANGED__STATE__AWAKE = 2;
    public static final int ACTIVITY_MANAGER_SLEEP_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int ADB_CONNECTION_CHANGED = 144;
    public static final int ADB_CONNECTION_CHANGED__STATE__AUTOMATICALLY_ALLOWED = 4;
    public static final int ADB_CONNECTION_CHANGED__STATE__AWAITING_USER_APPROVAL = 1;
    public static final int ADB_CONNECTION_CHANGED__STATE__DENIED_INVALID_KEY = 5;
    public static final int ADB_CONNECTION_CHANGED__STATE__DENIED_VOLD_DECRYPT = 6;
    public static final int ADB_CONNECTION_CHANGED__STATE__DISCONNECTED = 7;
    public static final int ADB_CONNECTION_CHANGED__STATE__UNKNOWN = 0;
    public static final int ADB_CONNECTION_CHANGED__STATE__USER_ALLOWED = 2;
    public static final int ADB_CONNECTION_CHANGED__STATE__USER_DENIED = 3;
    public static final int ANOMALY_DETECTED = 46;
    public static final int ANROCCURRED__ERROR_SOURCE__DATA_APP = 1;
    public static final int ANROCCURRED__ERROR_SOURCE__ERROR_SOURCE_UNKNOWN = 0;
    public static final int ANROCCURRED__ERROR_SOURCE__SYSTEM_APP = 2;
    public static final int ANROCCURRED__ERROR_SOURCE__SYSTEM_SERVER = 3;
    public static final int ANROCCURRED__FOREGROUND_STATE__BACKGROUND = 1;
    public static final int ANROCCURRED__FOREGROUND_STATE__FOREGROUND = 2;
    public static final int ANROCCURRED__FOREGROUND_STATE__UNKNOWN = 0;
    public static final int ANROCCURRED__IS_INSTANT_APP__FALSE = 1;
    public static final int ANROCCURRED__IS_INSTANT_APP__TRUE = 2;
    public static final int ANROCCURRED__IS_INSTANT_APP__UNAVAILABLE = 0;
    public static final int ANR_OCCURRED = 79;
    public static final int APPS_ON_EXTERNAL_STORAGE_INFO = 10057;
    public static final int APPS_ON_EXTERNAL_STORAGE_INFO__EXTERNAL_STORAGE_TYPE__OTHER = 3;
    public static final int APPS_ON_EXTERNAL_STORAGE_INFO__EXTERNAL_STORAGE_TYPE__SD_CARD = 1;
    public static final int APPS_ON_EXTERNAL_STORAGE_INFO__EXTERNAL_STORAGE_TYPE__UNKNOWN = 0;
    public static final int APPS_ON_EXTERNAL_STORAGE_INFO__EXTERNAL_STORAGE_TYPE__USB = 2;
    public static final int APP_BREADCRUMB_REPORTED = 47;
    public static final int APP_BREADCRUMB_REPORTED__STATE__START = 3;
    public static final int APP_BREADCRUMB_REPORTED__STATE__STOP = 2;
    public static final int APP_BREADCRUMB_REPORTED__STATE__UNKNOWN = 0;
    public static final int APP_BREADCRUMB_REPORTED__STATE__UNSPECIFIED = 1;
    public static final int APP_COMPACTED = 115;
    public static final int APP_COMPACTED__ACTION__BFGS = 4;
    public static final int APP_COMPACTED__ACTION__FULL = 2;
    public static final int APP_COMPACTED__ACTION__PERSISTENT = 3;
    public static final int APP_COMPACTED__ACTION__SOME = 1;
    public static final int APP_COMPACTED__ACTION__UNKNOWN = 0;
    public static final int APP_COMPACTED__LAST_ACTION__BFGS = 4;
    public static final int APP_COMPACTED__LAST_ACTION__FULL = 2;
    public static final int APP_COMPACTED__LAST_ACTION__PERSISTENT = 3;
    public static final int APP_COMPACTED__LAST_ACTION__SOME = 1;
    public static final int APP_COMPACTED__LAST_ACTION__UNKNOWN = 0;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_BACKUP = 1008;

    /* renamed from: APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_BOUND_FOREGROUND_SERVICE */
    public static final int f372x45df3ef5 = 1004;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_BOUND_TOP = 1020;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_CACHED_ACTIVITY = 1015;

    /* renamed from: APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_CACHED_ACTIVITY_CLIENT */
    public static final int f373x9b750b79 = 1016;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_CACHED_EMPTY = 1018;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_CACHED_RECENT = 1017;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_FOREGROUND_SERVICE = 1003;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_HEAVY_WEIGHT = 1012;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_HOME = 1013;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_IMPORTANT_BACKGROUND = 1006;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_IMPORTANT_FOREGROUND = 1005;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_LAST_ACTIVITY = 1014;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_NONEXISTENT = 1019;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_PERSISTENT = 1000;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_PERSISTENT_UI = 1001;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_RECEIVER = 1010;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_SERVICE = 1009;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_TOP = 1002;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_TOP_SLEEPING = 1011;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_TRANSIENT_BACKGROUND = 1007;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_UNKNOWN = 999;
    public static final int APP_COMPACTED__PROCESS_STATE__PROCESS_STATE_UNKNOWN_TO_PROTO = 998;
    public static final int APP_CRASH_OCCURRED = 78;
    public static final int APP_CRASH_OCCURRED__ERROR_SOURCE__DATA_APP = 1;
    public static final int APP_CRASH_OCCURRED__ERROR_SOURCE__ERROR_SOURCE_UNKNOWN = 0;
    public static final int APP_CRASH_OCCURRED__ERROR_SOURCE__SYSTEM_APP = 2;
    public static final int APP_CRASH_OCCURRED__ERROR_SOURCE__SYSTEM_SERVER = 3;
    public static final int APP_CRASH_OCCURRED__FOREGROUND_STATE__BACKGROUND = 1;
    public static final int APP_CRASH_OCCURRED__FOREGROUND_STATE__FOREGROUND = 2;
    public static final int APP_CRASH_OCCURRED__FOREGROUND_STATE__UNKNOWN = 0;
    public static final int APP_CRASH_OCCURRED__IS_INSTANT_APP__FALSE = 1;
    public static final int APP_CRASH_OCCURRED__IS_INSTANT_APP__TRUE = 2;
    public static final int APP_CRASH_OCCURRED__IS_INSTANT_APP__UNAVAILABLE = 0;
    public static final int APP_DIED = 65;
    public static final int APP_DOWNGRADED = 128;
    public static final int APP_INSTALL_ON_EXTERNAL_STORAGE_REPORTED = 181;
    public static final int APP_INSTALL_ON_EXTERNAL_STORAGE_REPORTED__STORAGE_TYPE__OTHER = 3;
    public static final int APP_INSTALL_ON_EXTERNAL_STORAGE_REPORTED__STORAGE_TYPE__SD_CARD = 1;
    public static final int APP_INSTALL_ON_EXTERNAL_STORAGE_REPORTED__STORAGE_TYPE__UNKNOWN = 0;
    public static final int APP_INSTALL_ON_EXTERNAL_STORAGE_REPORTED__STORAGE_TYPE__USB = 2;
    public static final int APP_MOVED_STORAGE_REPORTED = 183;
    public static final int APP_MOVED_STORAGE_REPORTED__EXTERNAL_STORAGE_TYPE__OTHER = 3;
    public static final int APP_MOVED_STORAGE_REPORTED__EXTERNAL_STORAGE_TYPE__SD_CARD = 1;
    public static final int APP_MOVED_STORAGE_REPORTED__EXTERNAL_STORAGE_TYPE__UNKNOWN = 0;
    public static final int APP_MOVED_STORAGE_REPORTED__EXTERNAL_STORAGE_TYPE__USB = 2;
    public static final int APP_MOVED_STORAGE_REPORTED__MOVE_TYPE__TO_EXTERNAL = 1;
    public static final int APP_MOVED_STORAGE_REPORTED__MOVE_TYPE__TO_INTERNAL = 2;
    public static final int APP_MOVED_STORAGE_REPORTED__MOVE_TYPE__UNKNOWN = 0;
    public static final int APP_OPS = 10060;
    public static final int APP_OPTIMIZED_AFTER_DOWNGRADED = 129;
    public static final int APP_PERMISSIONS_FRAGMENT_VIEWED = 217;
    public static final int APP_PERMISSIONS_FRAGMENT_VIEWED__CATEGORY__ALLOWED = 1;
    public static final int APP_PERMISSIONS_FRAGMENT_VIEWED__CATEGORY__ALLOWED_FOREGROUND = 2;
    public static final int APP_PERMISSIONS_FRAGMENT_VIEWED__CATEGORY__DENIED = 3;
    public static final int APP_PERMISSIONS_FRAGMENT_VIEWED__CATEGORY__UNDEFINED = 0;
    public static final int APP_PERMISSION_FRAGMENT_ACTION_REPORTED = 215;
    public static final int APP_PERMISSION_FRAGMENT_VIEWED = 216;
    public static final int APP_SIZE = 10027;
    public static final int APP_START_CANCELED = 49;
    public static final int APP_START_CANCELED__TYPE__COLD = 3;
    public static final int APP_START_CANCELED__TYPE__HOT = 2;
    public static final int APP_START_CANCELED__TYPE__UNKNOWN = 0;
    public static final int APP_START_CANCELED__TYPE__WARM = 1;
    public static final int APP_START_FULLY_DRAWN = 50;
    public static final int APP_START_FULLY_DRAWN__TYPE__UNKNOWN = 0;
    public static final int APP_START_FULLY_DRAWN__TYPE__WITHOUT_BUNDLE = 2;
    public static final int APP_START_FULLY_DRAWN__TYPE__WITH_BUNDLE = 1;
    public static final int APP_START_MEMORY_STATE_CAPTURED = 55;
    public static final int APP_START_OCCURRED = 48;
    public static final int APP_START_OCCURRED__REASON__APP_TRANSITION_REASON_UNKNOWN = 0;
    public static final int APP_START_OCCURRED__REASON__APP_TRANSITION_RECENTS_ANIM = 5;
    public static final int APP_START_OCCURRED__REASON__APP_TRANSITION_SNAPSHOT = 4;
    public static final int APP_START_OCCURRED__REASON__APP_TRANSITION_SPLASH_SCREEN = 1;
    public static final int APP_START_OCCURRED__REASON__APP_TRANSITION_TIMEOUT = 3;
    public static final int APP_START_OCCURRED__REASON__APP_TRANSITION_WINDOWS_DRAWN = 2;
    public static final int APP_START_OCCURRED__TYPE__COLD = 3;
    public static final int APP_START_OCCURRED__TYPE__HOT = 2;
    public static final int APP_START_OCCURRED__TYPE__UNKNOWN = 0;
    public static final int APP_START_OCCURRED__TYPE__WARM = 1;
    public static final int ASSIST_GESTURE_FEEDBACK_REPORTED = 175;

    /* renamed from: ASSIST_GESTURE_FEEDBACK_REPORTED__FEEDBACK_TYPE__ASSIST_GESTURE_FEEDBACK_NOT_USED */
    public static final int f374x510869ca = 1;

    /* renamed from: ASSIST_GESTURE_FEEDBACK_REPORTED__FEEDBACK_TYPE__ASSIST_GESTURE_FEEDBACK_UNKNOWN */
    public static final int f375x83317009 = 0;

    /* renamed from: ASSIST_GESTURE_FEEDBACK_REPORTED__FEEDBACK_TYPE__ASSIST_GESTURE_FEEDBACK_USED */
    public static final int f376xff0ad03e = 2;
    public static final int ASSIST_GESTURE_PROGRESS_REPORTED = 176;
    public static final int ASSIST_GESTURE_STAGE_REPORTED = 174;

    /* renamed from: ASSIST_GESTURE_STAGE_REPORTED__GESTURE_STAGE__ASSIST_GESTURE_STAGE_DETECTED */
    public static final int f377x33ca7b67 = 3;

    /* renamed from: ASSIST_GESTURE_STAGE_REPORTED__GESTURE_STAGE__ASSIST_GESTURE_STAGE_PRIMED */
    public static final int f378xdd04fbea = 2;

    /* renamed from: ASSIST_GESTURE_STAGE_REPORTED__GESTURE_STAGE__ASSIST_GESTURE_STAGE_PROGRESS */
    public static final int f379xb9a42712 = 1;

    /* renamed from: ASSIST_GESTURE_STAGE_REPORTED__GESTURE_STAGE__ASSIST_GESTURE_STAGE_UNKNOWN */
    public static final int f380xc56312a5 = 0;
    public static final int ATTENTION_MANAGER_SERVICE_RESULT_REPORTED = 143;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_FAILURE_CAMERA_PERMISSION_ABSENT */
    public static final int f381xf60d65b2 = 6;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_FAILURE_CANCELLED */
    public static final int f382xa4b54eee = 3;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_FAILURE_PREEMPTED */
    public static final int f383x39e7344b = 4;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_FAILURE_TIMED_OUT */
    public static final int f384x8ac823c3 = 5;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_FAILURE_UNKNOWN */
    public static final int f385x98da9927 = 2;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_SUCCESS_ABSENT */
    public static final int f386xa0f54a03 = 0;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__ATTENTION_SUCCESS_PRESENT */
    public static final int f387xb1bfeb11 = 1;

    /* renamed from: ATTENTION_MANAGER_SERVICE_RESULT_REPORTED__ATTENTION_CHECK_RESULT__UNKNOWN */
    public static final int f388x9ab762fd = 20;
    public static final int AUDIO_STATE_CHANGED = 23;
    public static final int AUDIO_STATE_CHANGED__STATE__OFF = 0;
    public static final int AUDIO_STATE_CHANGED__STATE__ON = 1;
    public static final int AUDIO_STATE_CHANGED__STATE__RESET = 2;
    public static final int BATTERY_CAUSED_SHUTDOWN = 93;
    public static final int BATTERY_CYCLE_COUNT = 10045;
    public static final int BATTERY_HEALTH_SNAPSHOT = 91;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__AVG_RESISTANCE = 11;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MAX_BATT_LEVEL = 10;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MAX_CURRENT = 8;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MAX_RESISTANCE = 4;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MAX_TEMP = 2;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MAX_VOLTAGE = 6;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MIN_BATT_LEVEL = 9;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MIN_CURRENT = 7;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MIN_RESISTANCE = 3;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MIN_TEMP = 1;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__MIN_VOLTAGE = 5;
    public static final int BATTERY_HEALTH_SNAPSHOT__TYPE__UNKNOWN = 0;
    public static final int BATTERY_LEVEL = 10043;
    public static final int BATTERY_LEVEL_CHANGED = 30;
    public static final int BATTERY_SAVER_MODE_STATE_CHANGED = 20;
    public static final int BATTERY_SAVER_MODE_STATE_CHANGED__STATE__OFF = 0;
    public static final int BATTERY_SAVER_MODE_STATE_CHANGED__STATE__ON = 1;
    public static final int BATTERY_VOLTAGE = 10030;
    public static final int BINARY_PUSH_STATE_CHANGED = 102;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_BOOT_TRIGGERED */
    public static final int f389xd1ec069c = 13;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_BOOT_TRIGGERED_FAILURE */
    public static final int f390x90d10ec7 = 14;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_FAILURE = 16;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_INITIATED = 9;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_INITIATED_FAILURE */
    public static final int f391x7125b144 = 10;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_REQUESTED = 8;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_STAGED = 11;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_STAGED_CANCEL_FAILURE */
    public static final int f392x16ef392c = 19;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_STAGED_CANCEL_REQUESTED */
    public static final int f393x606c3a90 = 17;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_STAGED_CANCEL_SUCCESS */
    public static final int f394xe86847a5 = 18;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_STAGED_FAILURE */
    public static final int f395x8c3eac3 = 12;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALLER_ROLLBACK_SUCCESS = 15;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_CANCELLED = 7;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_FAILURE = 6;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_FAILURE_COMMIT = 25;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_FAILURE_DOWNLOAD = 23;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_FAILURE_STATE_MISMATCH = 24;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_REQUESTED = 1;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_STAGED_CANCEL_FAILURE = 22;

    /* renamed from: BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_STAGED_CANCEL_REQUESTED */
    public static final int f396xa6c9030c = 20;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_STAGED_CANCEL_SUCCESS = 21;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_STAGED_NOT_READY = 3;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_STAGED_READY = 4;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_STARTED = 2;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__INSTALL_SUCCESS = 5;
    public static final int BINARY_PUSH_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int BINDER_CALLS = 10022;
    public static final int BINDER_CALLS_EXCEPTIONS = 10023;
    public static final int BIOMETRIC_ACQUIRED = 87;
    public static final int BIOMETRIC_ACQUIRED__ACTION__ACTION_AUTHENTICATE = 2;
    public static final int BIOMETRIC_ACQUIRED__ACTION__ACTION_ENROLL = 1;
    public static final int BIOMETRIC_ACQUIRED__ACTION__ACTION_ENUMERATE = 3;
    public static final int BIOMETRIC_ACQUIRED__ACTION__ACTION_REMOVE = 4;
    public static final int BIOMETRIC_ACQUIRED__ACTION__ACTION_UNKNOWN = 0;
    public static final int BIOMETRIC_ACQUIRED__CLIENT__CLIENT_BIOMETRIC_PROMPT = 2;
    public static final int BIOMETRIC_ACQUIRED__CLIENT__CLIENT_FINGERPRINT_MANAGER = 3;
    public static final int BIOMETRIC_ACQUIRED__CLIENT__CLIENT_KEYGUARD = 1;
    public static final int BIOMETRIC_ACQUIRED__CLIENT__CLIENT_UNKNOWN = 0;
    public static final int BIOMETRIC_ACQUIRED__MODALITY__MODALITY_FACE = 4;
    public static final int BIOMETRIC_ACQUIRED__MODALITY__MODALITY_FINGERPRINT = 1;
    public static final int BIOMETRIC_ACQUIRED__MODALITY__MODALITY_IRIS = 2;
    public static final int BIOMETRIC_ACQUIRED__MODALITY__MODALITY_UNKNOWN = 0;
    public static final int BIOMETRIC_AUTHENTICATED = 88;
    public static final int BIOMETRIC_AUTHENTICATED__CLIENT__CLIENT_BIOMETRIC_PROMPT = 2;
    public static final int BIOMETRIC_AUTHENTICATED__CLIENT__CLIENT_FINGERPRINT_MANAGER = 3;
    public static final int BIOMETRIC_AUTHENTICATED__CLIENT__CLIENT_KEYGUARD = 1;
    public static final int BIOMETRIC_AUTHENTICATED__CLIENT__CLIENT_UNKNOWN = 0;
    public static final int BIOMETRIC_AUTHENTICATED__MODALITY__MODALITY_FACE = 4;
    public static final int BIOMETRIC_AUTHENTICATED__MODALITY__MODALITY_FINGERPRINT = 1;
    public static final int BIOMETRIC_AUTHENTICATED__MODALITY__MODALITY_IRIS = 2;
    public static final int BIOMETRIC_AUTHENTICATED__MODALITY__MODALITY_UNKNOWN = 0;
    public static final int BIOMETRIC_AUTHENTICATED__STATE__CONFIRMED = 3;
    public static final int BIOMETRIC_AUTHENTICATED__STATE__PENDING_CONFIRMATION = 2;
    public static final int BIOMETRIC_AUTHENTICATED__STATE__REJECTED = 1;
    public static final int BIOMETRIC_AUTHENTICATED__STATE__UNKNOWN = 0;
    public static final int BIOMETRIC_ENROLLED = 184;
    public static final int BIOMETRIC_ENROLLED__MODALITY__MODALITY_FACE = 4;
    public static final int BIOMETRIC_ENROLLED__MODALITY__MODALITY_FINGERPRINT = 1;
    public static final int BIOMETRIC_ENROLLED__MODALITY__MODALITY_IRIS = 2;
    public static final int BIOMETRIC_ENROLLED__MODALITY__MODALITY_UNKNOWN = 0;
    public static final int BIOMETRIC_ERROR_OCCURRED = 89;
    public static final int BIOMETRIC_ERROR_OCCURRED__ACTION__ACTION_AUTHENTICATE = 2;
    public static final int BIOMETRIC_ERROR_OCCURRED__ACTION__ACTION_ENROLL = 1;
    public static final int BIOMETRIC_ERROR_OCCURRED__ACTION__ACTION_ENUMERATE = 3;
    public static final int BIOMETRIC_ERROR_OCCURRED__ACTION__ACTION_REMOVE = 4;
    public static final int BIOMETRIC_ERROR_OCCURRED__ACTION__ACTION_UNKNOWN = 0;
    public static final int BIOMETRIC_ERROR_OCCURRED__CLIENT__CLIENT_BIOMETRIC_PROMPT = 2;
    public static final int BIOMETRIC_ERROR_OCCURRED__CLIENT__CLIENT_FINGERPRINT_MANAGER = 3;
    public static final int BIOMETRIC_ERROR_OCCURRED__CLIENT__CLIENT_KEYGUARD = 1;
    public static final int BIOMETRIC_ERROR_OCCURRED__CLIENT__CLIENT_UNKNOWN = 0;
    public static final int BIOMETRIC_ERROR_OCCURRED__MODALITY__MODALITY_FACE = 4;
    public static final int BIOMETRIC_ERROR_OCCURRED__MODALITY__MODALITY_FINGERPRINT = 1;
    public static final int BIOMETRIC_ERROR_OCCURRED__MODALITY__MODALITY_IRIS = 2;
    public static final int BIOMETRIC_ERROR_OCCURRED__MODALITY__MODALITY_UNKNOWN = 0;
    public static final int BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED = 148;

    /* renamed from: BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__ISSUE__ISSUE_CANCEL_TIMED_OUT */
    public static final int f397xd0999afe = 4;
    public static final int BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__ISSUE__ISSUE_HAL_DEATH = 1;
    public static final int BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__ISSUE__ISSUE_UNKNOWN = 0;

    /* renamed from: BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__ISSUE__ISSUE_UNKNOWN_TEMPLATE_ENROLLED_FRAMEWORK */
    public static final int f398x4a1ad797 = 2;

    /* renamed from: BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__ISSUE__ISSUE_UNKNOWN_TEMPLATE_ENROLLED_HAL */
    public static final int f399x9a52eaec = 3;
    public static final int BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__MODALITY__MODALITY_FACE = 4;

    /* renamed from: BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__MODALITY__MODALITY_FINGERPRINT */
    public static final int f400x85631c09 = 1;
    public static final int BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__MODALITY__MODALITY_IRIS = 2;

    /* renamed from: BIOMETRIC_SYSTEM_HEALTH_ISSUE_DETECTED__MODALITY__MODALITY_UNKNOWN */
    public static final int f401xb2b841ef = 0;
    public static final int BLE_SCAN_RESULT_RECEIVED = 4;
    public static final int BLE_SCAN_STATE_CHANGED = 2;
    public static final int BLE_SCAN_STATE_CHANGED__STATE__OFF = 0;
    public static final int BLE_SCAN_STATE_CHANGED__STATE__ON = 1;
    public static final int BLE_SCAN_STATE_CHANGED__STATE__RESET = 2;
    public static final int BLUETOOTH_A2DP_AUDIO_OVERRUN_REPORTED = 156;
    public static final int BLUETOOTH_A2DP_AUDIO_UNDERRUN_REPORTED = 155;
    public static final int BLUETOOTH_A2DP_CODEC_CAPABILITY_CHANGED = 154;
    public static final int BLUETOOTH_A2DP_CODEC_CONFIG_CHANGED = 153;
    public static final int BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED = 152;

    /* renamed from: BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED__AUDIO_CODING_MODE__AUDIO_CODING_MODE_HARDWARE */
    public static final int f402x517b49b4 = 1;

    /* renamed from: BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED__AUDIO_CODING_MODE__AUDIO_CODING_MODE_SOFTWARE */
    public static final int f403x9926c573 = 2;

    /* renamed from: BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED__AUDIO_CODING_MODE__AUDIO_CODING_MODE_UNKNOWN */
    public static final int f404x7dd01ade = 0;

    /* renamed from: BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED__PLAYBACK_STATE__PLAYBACK_STATE_NOT_PLAYING */
    public static final int f405xcc1d0f30 = 11;

    /* renamed from: BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED__PLAYBACK_STATE__PLAYBACK_STATE_PLAYING */
    public static final int f406xb66823bc = 10;

    /* renamed from: BLUETOOTH_A2DP_PLAYBACK_STATE_CHANGED__PLAYBACK_STATE__PLAYBACK_STATE_UNKNOWN */
    public static final int f407xc2d8ffd8 = 0;
    public static final int BLUETOOTH_ACL_CONNECTION_STATE_CHANGED = 126;

    /* renamed from: BLUETOOTH_ACL_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_CONNECTED */
    public static final int f408xe6a82e0 = 2;

    /* renamed from: BLUETOOTH_ACL_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_CONNECTING */
    public static final int f409xbee5e9a1 = 1;

    /* renamed from: BLUETOOTH_ACL_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_DISCONNECTED */
    public static final int f410xd81bfee4 = 0;

    /* renamed from: BLUETOOTH_ACL_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_DISCONNECTING */
    public static final int f411x2b63ee1d = 3;
    public static final int BLUETOOTH_ACTIVE_DEVICE_CHANGED = 151;
    public static final int BLUETOOTH_ACTIVITY_INFO = 10007;
    public static final int BLUETOOTH_BOND_STATE_CHANGED = 165;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__BONDING_SUB_STATE__BOND_SUB_STATE_LOCAL_OOB_DATA_PROVIDED */
    public static final int f412x613c2895 = 1;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__BONDING_SUB_STATE__BOND_SUB_STATE_LOCAL_PIN_REPLIED */
    public static final int f413xea8ad869 = 3;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__BONDING_SUB_STATE__BOND_SUB_STATE_LOCAL_PIN_REQUESTED */
    public static final int f414xb7467ede = 2;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__BONDING_SUB_STATE__BOND_SUB_STATE_LOCAL_SSP_REPLIED */
    public static final int f415x5ca039e4 = 5;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__BONDING_SUB_STATE__BOND_SUB_STATE_LOCAL_SSP_REQUESTED */
    public static final int f416xf9896d99 = 4;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__BONDING_SUB_STATE__BOND_SUB_STATE_UNKNOWN */
    public static final int f417x303098f8 = 0;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__BOND_STATE__BOND_STATE_BONDED = 12;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__BOND_STATE__BOND_STATE_BONDING = 11;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__BOND_STATE__BOND_STATE_NONE = 10;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__BOND_STATE__BOND_STATE_UNKNOWN = 0;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TRANSPORT__TRANSPORT_TYPE_AUTO = 0;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TRANSPORT__TRANSPORT_TYPE_BREDR = 1;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TRANSPORT__TRANSPORT_TYPE_LE = 2;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TYPE__DEVICE_TYPE_CLASSIC = 1;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TYPE__DEVICE_TYPE_DUAL = 3;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TYPE__DEVICE_TYPE_LE = 2;
    public static final int BLUETOOTH_BOND_STATE_CHANGED__TYPE__DEVICE_TYPE_UNKNOWN = 0;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_AUTH_CANCELED */
    public static final int f418x84fa98c = 3;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_AUTH_FAILED */
    public static final int f419x44c95df0 = 1;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_AUTH_REJECTED */
    public static final int f420xeb623971 = 2;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_AUTH_TIMEOUT */
    public static final int f421x46d5b9ee = 6;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_DISCOVERY_IN_PROGRESS */
    public static final int f422x89357014 = 5;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_REMOTE_AUTH_CANCELED */
    public static final int f423x114e815b = 8;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_REMOTE_DEVICE_DOWN */
    public static final int f424xd52c23b6 = 4;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_REMOVED */
    public static final int f425x1b197d7c = 9;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_REPEATED_ATTEMPTS */
    public static final int f426x8444c0a7 = 7;

    /* renamed from: BLUETOOTH_BOND_STATE_CHANGED__UNBOND_REASON__UNBOND_REASON_UNKNOWN */
    public static final int f427xc90b1166 = 0;
    public static final int BLUETOOTH_BYTES_TRANSFER = 10006;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED = 166;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ADVERTISING_TIMEOUT */
    public static final int f428x3b3e7ddd = 60;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_AUTH_FAILURE */
    public static final int f429x98002edc = 5;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f430x236d6bd4 = 46;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CLB_DATA_TOO_BIG */
    public static final int f431x967671cf = 67;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CLB_NOT_ENABLED */
    public static final int f432x4e9d19a6 = 66;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_COMMAND_DISALLOWED */
    public static final int f433xb981137 = 12;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CONNECTION_EXISTS */
    public static final int f434x3d33c574 = 11;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CONNECTION_TOUT */
    public static final int f435x1566a572 = 8;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f436x1e9d8d9c = 22;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f437x3608263f = 62;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f438x64ad654 = 61;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_CONTROLLER_BUSY */
    public static final int f439xd1ebef13 = 58;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f440x2e293f60 = 42;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f441x88010a63 = 37;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_HOST_BUSY_PAIRING */
    public static final int f442xbde11030 = 56;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_HOST_REJECT_DEVICE */
    public static final int f443xab59d2e8 = 15;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_HOST_REJECT_RESOURCES */
    public static final int f444xd2336c33 = 13;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_HOST_REJECT_SECURITY */
    public static final int f445x32a64b52 = 14;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_HOST_TIMEOUT */
    public static final int f446x41de62b3 = 16;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_HW_FAILURE */
    public static final int f447xb44cb983 = 3;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ILLEGAL_COMMAND */
    public static final int f448x7b02d599 = 1;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f449x197b76a5 = 18;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f450xb1fdfb27 = 54;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_INSTANT_PASSED */
    public static final int f451x5dd1a377 = 40;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_INSUFFCIENT_SECURITY */
    public static final int f452x13550caa = 47;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_INVALID_LMP_PARAM */
    public static final int f453xe7b6734c = 30;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_KEY_MISSING */
    public static final int f454x8d5dc51d = 6;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f455x34a4df25 = 36;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f456xfd6fce9c = 34;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f457x67d72c67 = 35;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f458x9c607bd4 = 64;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f459x2195b77b = 65;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_MAC_CONNECTION_FAILED */
    public static final int f460x8cf806c5 = 63;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f461xdd31a96a = 9;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_MAX_NUM_OF_SCOS */
    public static final int f462x4cc9a99f = 10;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_MEMORY_FULL */
    public static final int f463x6a4f38e4 = 7;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_NO_CONNECTION */
    public static final int f464x9a084833 = 2;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f465x7cc09c2b = 68;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PAGE_TIMEOUT */
    public static final int f466x3775f89a = 4;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f467xddea13bc = 24;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f468xdefc5160 = 41;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f469x4c3ff781 = 48;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PEER_LOW_RESOURCES */
    public static final int f470xe73118e6 = 20;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PEER_POWER_OFF */
    public static final int f471x9b828721 = 21;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_PEER_USER */
    public static final int f472xadcae81f = 19;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_QOS_NOT_SUPPORTED */
    public static final int f473x45c725cf = 39;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_QOS_REJECTED */
    public static final int f474x87647c91 = 45;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f475xe16d993c = 44;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f476x5e7988d8 = 57;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_REPEATED_ATTEMPTS */
    public static final int f477x4cbe302 = 23;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f478xca79b952 = 52;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f479x620c8fad = 33;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ROLE_SWITCH_FAILED */
    public static final int f480x433b4de8 = 53;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_ROLE_SWITCH_PENDING */
    public static final int f481x3c43052c = 50;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_SCO_AIR_MODE */
    public static final int f482xc7a834e1 = 29;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f483x82bc338f = 28;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_SCO_OFFSET_REJECTED */
    public static final int f484x5b7d6141 = 27;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f485x362550e7 = 55;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_SUCCESS */
    public static final int f486xc75ced3a = 0;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f487xc490a763 = 59;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNDEFINED_0X2_B */
    public static final int f488xf8a03ad5 = 43;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNDEFINED_0X31 */
    public static final int f489xc5f4a6fe = 49;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNDEFINED_0X33 */
    public static final int f490xc5f4a700 = 51;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNIT_KEY_USED */
    public static final int f491xd95c622f = 38;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNKNOWN */
    public static final int f492x25ad5981 = 4095;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNKNOWN_LMP_PDU */
    public static final int f493x72a26ad3 = 25;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNSPECIFIED */
    public static final int f494xb313bcee = 31;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f495xab95a5b3 = 32;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f496xc9d9003e = 26;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__CMD_STATUS__STATUS_UNSUPPORTED_VALUE */
    public static final int f497x1e12af3e = 17;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ACCEPT_CONNECTION_REQUEST */
    public static final int f498x92cd4b44 = 1033;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ACCEPT_ESCO_CONNECTION */
    public static final int f499x9816812d = 1065;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ACCEPT_LOGICAL_LINK */
    public static final int f500x9093dc46 = 1081;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ACCEPT_PHYSICAL_LINK */
    public static final int f501xed249b6c = 1078;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ADD_SCO_CONNECTION */
    public static final int f502xdf68b6dd = 1031;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_AMP_TEST = 6153;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_AMP_TEST_END */
    public static final int f503x74f4ccca = 6152;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_AUTHENTICATION_REQUESTED */
    public static final int f504x97c8d9c8 = 1041;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ADD_DEVICE_TO_PERIODIC_ADVERTISING_LIST */
    public static final int f505xb6cb95bf = 8263;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ADD_DEV_RESOLVING_LIST */
    public static final int f506xce2ef061 = 8231;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ADD_WHITE_LIST */
    public static final int f507x2886557 = 8209;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ADV_FILTER */
    public static final int f508x470c3ae9 = 64855;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_BATCH_SCAN */
    public static final int f509xea3a2ec7 = 64854;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_CLEAR_ADVERTISING_SETS */
    public static final int f510xd2627343 = 8253;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_CLEAR_PERIODIC_ADVERTISING_LIST */
    public static final int f511x3e91d66 = 8265;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_CLEAR_RESOLVING_LIST */
    public static final int f512xd29e1feb = 8233;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_CLEAR_WHITE_LIST */
    public static final int f513xf44a42eb = 8208;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_CREATE_CONN_CANCEL */
    public static final int f514xa57cc96f = 8206;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_CREATE_LL_CONN */
    public static final int f515x1ebf520d = 8205;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ENCRYPT */
    public static final int f516x9542b620 = 8215;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ENERGY_INFO */
    public static final int f517xab2c5660 = 64857;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ENH_RECEIVER_TEST */
    public static final int f518xf2e43f7d = 8243;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_ENH_TRANSMITTER_TEST */
    public static final int f519x12e38c45 = 8244;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_EXTENDED_CREATE_CONNECTION */
    public static final int f520xc9a64cc0 = 8259;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_EXTENDED_SCAN_PARAMS */
    public static final int f521x2dfbe787 = 64858;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_GENERATE_DHKEY */
    public static final int f522x1eb563b6 = 8230;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_LTK_REQ_NEG_REPLY */
    public static final int f523xf11dd359 = 8219;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_LTK_REQ_REPLY */
    public static final int f524xaccad5e8 = 8218;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_MULTI_ADV */
    public static final int f525xa7604188 = 64852;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC */
    public static final int f526x70bc0624 = 8260;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC_CANCEL */
    public static final int f527x7df8a8f5 = 8261;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_PERIODIC_ADVERTISING_TERMINATE_SYNC */
    public static final int f528xe8f9b1f3 = 8262;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_RAND = 8216;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_RC_PARAM_REQ_NEG_REPLY */
    public static final int f529xf2c9c41f = 8225;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_RC_PARAM_REQ_REPLY */
    public static final int f530x1aea19ae = 8224;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_ADV_CHNL_TX_POWER */
    public static final int f531x6d7a88f6 = 8199;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_BUFFER_SIZE */
    public static final int f532x34faddfc = 8194;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_CHNL_MAP */
    public static final int f533xfa1a6c04 = 8213;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_DEFAULT_DATA_LENGTH */
    public static final int f534x6f5b5959 = 8227;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_LOCAL_SPT_FEAT */
    public static final int f535x5d020e72 = 8195;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_MAXIMUM_ADVERTISING_DATA_LENGTH */
    public static final int f536x683a427d = 8250;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_MAXIMUM_DATA_LENGTH */
    public static final int f537xeced62b8 = 8239;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_NUMBER_OF_SUPPORTED_ADVERTISING_SETS */
    public static final int f538x5c66af53 = 8251;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_PERIODIC_ADVERTISING_LIST_SIZE */
    public static final int f539x363fda07 = 8266;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_PHY */
    public static final int f540x793efebd = 8240;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_REMOTE_FEAT */
    public static final int f541xf6c112e7 = 8214;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_RESOLVABLE_ADDR_LOCAL */
    public static final int f542x83a33125 = 8236;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_RESOLVABLE_ADDR_PEER */
    public static final int f543xe3387c48 = 8235;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_RESOLVING_LIST_SIZE */
    public static final int f544x9548f328 = 8234;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_RF_COMPENS_POWER */
    public static final int f545x7b20d85a = 8268;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_SUPPORTED_STATES */
    public static final int f546x928a8af7 = 8220;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_TRANSMIT_POWER */
    public static final int f547xcfa8f7ba = 8267;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_READ_WHITE_LIST_SIZE */
    public static final int f548xde5151a8 = 8207;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_RECEIVER_TEST */
    public static final int f549x7929607d = 8221;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_REMOVE_ADVERTISING_SET */
    public static final int f550x47b06991 = 8252;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_REMOVE_WHITE_LIST */
    public static final int f551x19a0fbca = 8210;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_RM_DEVICE_FROM_PERIODIC_ADVERTISING_LIST */
    public static final int f552x305c4d52 = 8264;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_RM_DEV_RESOLVING_LIST */
    public static final int f553x6a4c2a3d = 8232;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_ADDR_RESOLUTION_ENABLE */
    public static final int f554x88519c8a = 8237;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_DATA_LENGTH */
    public static final int f555x96d46979 = 8226;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_DEFAULT_PHY */
    public static final int f556x3c1906a1 = 8241;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EVENT_MASK */
    public static final int f557x7900fd73 = 8193;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXTENDED_SCAN_ENABLE */
    public static final int f558xbd9e80e1 = 8258;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXTENDED_SCAN_PARAMETERS */
    public static final int f559x81593648 = 8257;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_DATA */
    public static final int f560x39c6bfe5 = 8247;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_ENABLE */
    public static final int f561xe579cefe = 8249;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_PARAM */
    public static final int f562xffba4f92 = 8246;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_RANDOM_ADDRESS */
    public static final int f563x650f3ed3 = 8245;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_SCAN_RESP */
    public static final int f564x149ce8f7 = 8248;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_HOST_CHNL_CLASS */
    public static final int f565x81b5d351 = 8212;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_PERIODIC_ADVERTISING_DATA */
    public static final int f566xe868b907 = 8255;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE */
    public static final int f567x738207a0 = 8256;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_PERIODIC_ADVERTISING_PARAM */
    public static final int f568x25577ab0 = 8254;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_PHY */
    public static final int f569x6b73abbf = 8242;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_PRIVACY_MODE */
    public static final int f570x48496f7c = 8270;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_SET_RAND_PRIV_ADDR_TIMOUT */
    public static final int f571x2137d5ec = 8238;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_START_ENC */
    public static final int f572x70668458 = 8217;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_TEST_END */
    public static final int f573x6899e313 = 8223;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_TRACK_ADV */
    public static final int f574x1ad1049a = 64856;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_TRANSMITTER_TEST */
    public static final int f575x397ecb45 = 8222;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_UPD_LL_CONN_PARAMS */
    public static final int f576x96734f5 = 8211;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_VENDOR_CAP */
    public static final int f577x21e81e80 = 64851;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_ADV_DATA */
    public static final int f578xebf42c1b = 8200;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_ADV_ENABLE */
    public static final int f579xc1fd05b4 = 8202;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_ADV_PARAMS */
    public static final int f580xd412d9b7 = 8198;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_DEFAULT_DATA_LENGTH */
    public static final int f581x33923698 = 8228;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_LOCAL_SPT_FEAT */
    public static final int f582x8337a5d3 = 8196;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_RANDOM_ADDR */
    public static final int f583xfe03af88 = 8197;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_RF_COMPENS_POWER */
    public static final int f584xea4e1b7b = 8269;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_SCAN_ENABLE */
    public static final int f585x429abb20 = 8204;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_SCAN_PARAMS */
    public static final int f586x54b08f23 = 8203;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BLE_WRITE_SCAN_RSP_DATA */
    public static final int f587x70f49617 = 8201;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_BRCM_SET_ACL_PRIORITY */
    public static final int f588x6408fbba = 64599;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CHANGE_CONN_LINK_KEY */
    public static final int f589xd7dcfddf = 1045;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CHANGE_CONN_PACKET_TYPE */
    public static final int f590x41c8cd0c = 1039;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CHANGE_LOCAL_NAME */
    public static final int f591x79d3928d = 3091;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CONTROLLER_A2DP_OPCODE */
    public static final int f592x5f11090e = 64861;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CONTROLLER_DEBUG_INFO */
    public static final int f593x864dbbdc = 64859;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CREATE_CONNECTION */
    public static final int f594xd7a74da0 = 1029;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CREATE_CONNECTION_CANCEL */
    public static final int f595x4d313df9 = 1032;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CREATE_LOGICAL_LINK */
    public static final int f596xf6effcd2 = 1080;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CREATE_NEW_UNIT_KEY */
    public static final int f597x2fb22f25 = 3083;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_CREATE_PHYSICAL_LINK */
    public static final int f598x524c8c60 = 1077;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_DELETE_RESERVED_LT_ADDR */
    public static final int f599x9a40eaa4 = 3189;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_DELETE_STORED_LINK_KEY */
    public static final int f600x23da9ac3 = 3090;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_DISCONNECT */
    public static final int f601x89e631dd = 1030;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_DISCONNECT_LOGICAL_LINK */
    public static final int f602x972ca9b2 = 1082;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_DISCONNECT_PHYSICAL_LINK */
    public static final int f603xb9a57b80 = 1079;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ENABLE_AMP_RCVR_REPORTS */
    public static final int f604xc2e10e63 = 6151;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ENABLE_DEV_UNDER_TEST_MODE */
    public static final int f605xf661dea4 = 6147;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ENHANCED_FLUSH */
    public static final int f606xf1ac9d9c = 3167;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ENH_ACCEPT_ESCO_CONNECTION */
    public static final int f607x4997a42d = 1086;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ENH_SETUP_ESCO_CONNECTION */
    public static final int f608xabdf29e0 = 1085;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_EXIT_PARK_MODE */
    public static final int f609x3c2233b8 = 2054;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_EXIT_PERIODIC_INQUIRY_MODE */
    public static final int f610x3e0aef3f = 1028;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_EXIT_SNIFF_MODE */
    public static final int f611xd3643dd4 = 2052;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_FLOW_SPECIFICATION */
    public static final int f612xc05e97d3 = 2064;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_FLOW_SPEC_MODIFY */
    public static final int f613xc6e19cae = 1084;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_FLUSH = 3080;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_GET_LINK_QUALITY */
    public static final int f614xc3b8f884 = 5123;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_GET_MWS_TRANSPORT_CFG */
    public static final int f615x52f1cb8e = 5132;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_GET_MWS_TRANS_LAYER_CFG */
    public static final int f616xe83958bf = 3084;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_HOLD_MODE = 2049;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_HOST_BUFFER_SIZE */
    public static final int f617xabbb790a = 3123;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_HOST_NUM_PACKETS_DONE */
    public static final int f618x5ee62485 = 3125;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_INQUIRY = 1025;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_INQUIRY_CANCEL */
    public static final int f619xcca614d3 = 1026;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_IO_CAPABILITY_REQUEST_REPLY */
    public static final int f620x755863eb = 1067;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_IO_CAP_REQ_NEG_REPLY */
    public static final int f621xb243c415 = 1076;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_LINK_KEY_REQUEST_NEG_REPLY */
    public static final int f622xa8e58827 = 1036;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_LINK_KEY_REQUEST_REPLY */
    public static final int f623x2f5921b6 = 1035;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_LOGICAL_LINK_CANCEL */
    public static final int f624x61c4c6e8 = 1083;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_MASTER_LINK_KEY */
    public static final int f625x5063c116 = 1047;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_PARK_MODE = 2053;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_PERIODIC_INQUIRY_MODE */
    public static final int f626x54946c7e = 1027;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_PIN_CODE_REQUEST_NEG_REPLY */
    public static final int f627xfefcc0e4 = 1038;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_PIN_CODE_REQUEST_REPLY */
    public static final int f628x36bc40f3 = 1037;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_QOS_SETUP = 2055;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_AFH_ASSESSMENT_MODE */
    public static final int f629x4595fcdc = 3144;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_AFH_CH_MAP */
    public static final int f630x9dd2d306 = 5126;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f631xea55dd20 = 3195;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_AUTHENTICATION_ENABLE */
    public static final int f632x9878282 = 3103;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_AUTOMATIC_FLUSH_TIMEOUT */
    public static final int f633xda18b70a = 3111;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_BD_ADDR */
    public static final int f634x1c04c126 = 4105;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_BE_FLUSH_TOUT */
    public static final int f635x803a5da9 = 3177;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_BLE_HOST_SUPPORT */
    public static final int f636x3809844 = 3180;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_BUFFER_SIZE */
    public static final int f637x2f1367f8 = 4101;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_CLASS_OF_DEVICE */
    public static final int f638xb3213eef = 3107;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_CLOCK */
    public static final int f639x969b7d46 = 5127;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_CONN_ACCEPT_TOUT */
    public static final int f640x4f9e1a46 = 3093;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_COUNTRY_CODE */
    public static final int f641x3cbae8be = 4103;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_CURRENT_IAC_LAP */
    public static final int f642x8ee0b3d9 = 3129;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_DATA_BLOCK_SIZE */
    public static final int f643xfe652ba0 = 4106;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_DEF_POLICY_SETTINGS */
    public static final int f644x481dcf8e = 2062;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_ENCRYPTION_MODE */
    public static final int f645xe077437 = 3105;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_ENCR_KEY_SIZE */
    public static final int f646xe62318e0 = 5128;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_ENHANCED_TX_PWR_LEVEL */
    public static final int f647xc3140d6 = 3176;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_ERRONEOUS_DATA_RPT */
    public static final int f648x24b71f9c = 3162;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_EXTENDED_INQUIRY_LENGTH */
    public static final int f649xa44e879c = 3200;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_EXTENDED_PAGE_TIMEOUT */
    public static final int f650x6e8cd62f = 3198;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_EXT_INQ_RESPONSE */
    public static final int f651xd538247a = 3153;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_FAILED_CONTACT_COUNTER */
    public static final int f652x295d3e43 = 5121;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_FLOW_CONTROL_MODE */
    public static final int f653x1ccbd82e = 3174;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_HOLD_MODE_ACTIVITY */
    public static final int f654xc1ca6453 = 3115;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_INQSCAN_TYPE */
    public static final int f655x6e352518 = 3138;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_INQUIRYSCAN_CFG */
    public static final int f656x943cb581 = 3101;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_INQUIRY_MODE */
    public static final int f657x9c10f8e3 = 3140;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_INQ_TX_POWER_LEVEL */
    public static final int f658xcd4170ca = 3160;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LINK_SUPER_TOUT */
    public static final int f659xdef59d7b = 3126;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LMP_HANDLE */
    public static final int f660x7200db20 = 1056;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_AMP_ASSOC */
    public static final int f661x917ed4fe = 5130;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_AMP_INFO */
    public static final int f662x5749b685 = 5129;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_EXT_FEATURES */
    public static final int f663xd0bb72d7 = 4100;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_FEATURES */
    public static final int f664x9d4f8c59 = 4099;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_NAME */
    public static final int f665x299998a7 = 3092;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_OOB_DATA */
    public static final int f666x57805383 = 3159;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_OOB_EXTENDED_DATA */
    public static final int f667xda7c7737 = 3197;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_SUPPORTED_CMDS */
    public static final int f668xe4104ea6 = 4098;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_SUPPORTED_CODECS */
    public static final int f669x2153400a = 4107;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCAL_VERSION_INFO */
    public static final int f670xca2c69f1 = 4097;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOCATION_DATA */
    public static final int f671xa085b4c = 3172;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOGICAL_LINK_ACCEPT_TIMEOUT */
    public static final int f672x29d361f1 = 3169;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_LOOPBACK_MODE */
    public static final int f673x2950212f = 6145;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_NUM_BCAST_REXMITS */
    public static final int f674x605613a1 = 3113;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_NUM_SUPPORTED_IAC */
    public static final int f675xdb06a339 = 3128;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_PAGESCAN_CFG */
    public static final int f676x3e9c7ab9 = 3099;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_PAGESCAN_MODE */
    public static final int f677x94f789ce = 3133;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_PAGESCAN_PERIOD_MODE */
    public static final int f678xae5a2d6 = 3131;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_PAGESCAN_TYPE */
    public static final int f679x94fadf65 = 3142;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_PAGE_TOUT */
    public static final int f680x24b52f62 = 3095;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_PIN_TYPE */
    public static final int f681xef36c = 3081;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_POLICY_SETTINGS */
    public static final int f682x1afb47e8 = 2060;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_RMT_CLOCK_OFFSET */
    public static final int f683x760602b2 = 1055;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_RMT_EXT_FEATURES */
    public static final int f684xff2d6169 = 1052;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_RMT_FEATURES */
    public static final int f685xe31393eb = 1051;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_RMT_VERSION_INFO */
    public static final int f686xf89e5883 = 1053;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_RSSI = 5125;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_SCAN_ENABLE */
    public static final int f687x8707b8dd = 3097;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_SCO_FLOW_CTRL_ENABLE */
    public static final int f688x6b71ac2e = 3118;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_SECURE_CONNS_SUPPORT */
    public static final int f689x5e652af7 = 3193;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_SIMPLE_PAIRING_MODE */
    public static final int f690xd5d7ad9f = 3157;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_STORED_LINK_KEY */
    public static final int f691xb42c38ee = 3085;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_SYNC_TRAIN_PARAM */
    public static final int f692x280c6f5a = 3191;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_TRANSMIT_POWER_LEVEL */
    public static final int f693x5143bc23 = 3117;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_READ_VOICE_SETTINGS */
    public static final int f694x80c07258 = 3109;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_RECEIVE_CLB */
    public static final int f695x6185d45c = 1090;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_RECEIVE_SYNC_TRAIN */
    public static final int f696xc6b27a81 = 1092;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_REFRESH_ENCRYPTION_KEY */
    public static final int f697xe6918948 = 3155;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_REJECT_CONNECTION_REQUEST */
    public static final int f698xb59c3f4d = 1034;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_REJECT_ESCO_CONNECTION */
    public static final int f699xfa2e31c4 = 1066;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_REM_OOB_DATA_REQ_NEG_REPLY */
    public static final int f700xa0527a88 = 1075;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_REM_OOB_DATA_REQ_REPLY */
    public static final int f701xff186c97 = 1072;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_REM_OOB_EXTENDED_DATA_REQ_REPLY */
    public static final int f702xf0b0b577 = 1093;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_RESET = 3075;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_RESET_FAILED_CONTACT_COUNTER */
    public static final int f703xe2cdd0ac = 5122;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_RMT_NAME_REQUEST */
    public static final int f704x58b0ae42 = 1049;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_RMT_NAME_REQUEST_CANCEL */
    public static final int f705x6d5a4317 = 1050;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_ROLE_DISCOVERY */
    public static final int f706x21aed1c8 = 2057;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SEND_KEYPRESS_NOTIF */
    public static final int f707xbbeee1cb = 3168;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SETUP_ESCO_CONNECTION */
    public static final int f708x5390ace0 = 1064;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_AFH_CHANNELS */
    public static final int f709xc4bd512a = 3135;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_CLB = 1089;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_CONN_ENCRYPTION */
    public static final int f710xf6b8f458 = 1043;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_EVENT_FILTER */
    public static final int f711x2b4bc23b = 3077;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_EVENT_MASK */
    public static final int f712x226bd66f = 3073;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_EVENT_MASK_PAGE_2 */
    public static final int f713xd302da52 = 3171;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_EXTERNAL_FRAME_CONFIGURATION */
    public static final int f714xb04e344e = 3183;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_HC_TO_HOST_FLOW_CTRL */
    public static final int f715xcf01a463 = 3121;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_MWS_CHANNEL_PARAMETERS */
    public static final int f716xd1ecb6ba = 3182;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_MWS_PATTERN_CONFIGURATION */
    public static final int f717x32f685d3 = 3187;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_MWS_SCAN_FREQUENCY_TABLE */
    public static final int f718xd5e2c41d = 3186;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_MWS_SIGNALING */
    public static final int f719xd28a3da6 = 3184;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_MWS_TRANSPORT_LAYER */
    public static final int f720xd87d2907 = 3185;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_RESERVED_LT_ADDR */
    public static final int f721xb71bf08f = 3188;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SET_TRIGGERED_CLK_CAPTURE */
    public static final int f722x35975143 = 5133;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SHORT_RANGE_MODE */
    public static final int f723xdd3ac3c9 = 3179;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SNIFF_MODE */
    public static final int f724x410491f5 = 2051;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SNIFF_SUB_RATE */
    public static final int f725xd2a7f6f1 = 2065;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_START_SYNC_TRAIN */
    public static final int f726xec42b3e2 = 1091;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_SWITCH_ROLE */
    public static final int f727x5f802180 = 2059;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_TRUNCATED_PAGE */
    public static final int f728x52101b51 = 1087;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_TRUNCATED_PAGE_CANCEL */
    public static final int f729xf167d728 = 1088;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_UNKNOWN = 1048575;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_USER_CONF_REQUEST_REPLY */
    public static final int f730x7e293c72 = 1068;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_USER_CONF_VALUE_NEG_REPLY */
    public static final int f731x23bdcb45 = 1069;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_USER_PASSKEY_REQ_NEG_REPLY */
    public static final int f732x57c8096 = 1071;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_USER_PASSKEY_REQ_REPLY */
    public static final int f733x2563a9a5 = 1070;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_AFH_ASSESSMENT_MODE */
    public static final int f734xbbacfda3 = 3145;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f735x293952f9 = 3196;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_AUTHENTICATION_ENABLE */
    public static final int f736x55e16d89 = 3104;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_AUTOMATIC_FLUSH_TIMEOUT */
    public static final int f737x77a3fc51 = 3112;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_BE_FLUSH_TOUT */
    public static final int f738x90b73fb0 = 3178;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_BLE_HOST_SUPPORT */
    public static final int f739xb843a4dd = 3181;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_CLASS_OF_DEVICE */
    public static final int f740x97edbb36 = 3108;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_CLB_DATA */
    public static final int f741xea324c71 = 3190;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_CONN_ACCEPT_TOUT */
    public static final int f742x46126df = 3094;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_CURRENT_IAC_LAP */
    public static final int f743x73ad3020 = 3130;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_DEF_POLICY_SETTINGS */
    public static final int f744xbe34d055 = 2063;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_ENCRYPTION_MODE */
    public static final int f745xf2d3f07e = 3106;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_ERRONEOUS_DATA_RPT */
    public static final int f746xb4e969f5 = 3163;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_EXTENDED_INQUIRY_LENGTH */
    public static final int f747x41d9cce3 = 3201;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_EXTENDED_PAGE_TIMEOUT */
    public static final int f748xbae6c136 = 3199;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_EXT_INQ_RESPONSE */
    public static final int f749x89fb3113 = 3154;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_FLOW_CONTROL_MODE */
    public static final int f750x6a5eb5 = 3175;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_HOLD_MODE_ACTIVITY */
    public static final int f751x51fcaeac = 3116;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_INQSCAN_TYPE */
    public static final int f752xfb206631 = 3139;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_INQUIRYSCAN_CFG */
    public static final int f753x790931c8 = 3102;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_INQUIRY_MODE */
    public static final int f754x28fc39fc = 3141;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_INQ_TX_POWER_LEVEL */
    public static final int f755x5d73bb23 = 3161;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_LINK_SUPER_TOUT */
    public static final int f756xc3c219c2 = 3127;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_LOCATION_DATA */
    public static final int f757x1a853d53 = 3173;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_LOGICAL_LINK_ACCEPT_TIMEOUT */
    public static final int f758xda1cabb8 = 3170;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_LOOPBACK_MODE */
    public static final int f759x39cd0336 = 6146;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_NUM_BCAST_REXMITS */
    public static final int f760x43f49a28 = 3114;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_PAGESCAN_CFG */
    public static final int f761xcb87bbd2 = 3100;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_PAGESCAN_MODE */
    public static final int f762xa5746bd5 = 3134;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_PAGESCAN_PERIOD_MODE */
    public static final int f763x57aebaef = 3132;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_PAGESCAN_TYPE */
    public static final int f764xa577c16c = 3143;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_PAGE_TOUT */
    public static final int f765x4a8a2ce9 = 3096;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_PIN_TYPE */
    public static final int f766xa670a905 = 3082;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_POLICY_SETTINGS */
    public static final int f767xffc7c42f = 2061;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_REMOTE_AMP_ASSOC */
    public static final int f768xa497fd22 = 5131;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SCAN_ENABLE */
    public static final int f769x8b9370a4 = 3098;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SCO_FLOW_CTRL_ENABLE */
    public static final int f770xb83ac447 = 3119;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SECURE_CONNS_SUPPORT */
    public static final int f771xab2e4310 = 3194;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SECURE_CONN_TEST_MODE */
    public static final int f772xbea38a04 = 6154;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SIMPLE_PAIRING_MODE */
    public static final int f773x4beeae66 = 3158;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SIMP_PAIR_DEBUG_MODE */
    public static final int f774x4e0480af = 6148;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_STORED_LINK_KEY */
    public static final int f775x98f8b535 = 3089;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_SYNC_TRAIN_PARAM */
    public static final int f776xdccf7bf3 = 3192;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_CMD__CMD_WRITE_VOICE_SETTINGS */
    public static final int f777x7fdfd131 = 3110;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_AMP_RECEIVER_RPT */
    public static final int f778xa1d27a19 = 75;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_AMP_STATUS_CHANGE */
    public static final int f779x20fbdc6a = 77;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_AMP_TEST_END */
    public static final int f780x2915e3a1 = 74;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_AMP_TEST_START */
    public static final int f781x3bf381a8 = 73;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f782x32ea6550 = 87;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_AUTHENTICATION_COMP */
    public static final int f783x12f7299e = 6;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_BLE_META */
    public static final int f784xb583b661 = 62;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CHANGE_CONN_LINK_KEY */
    public static final int f785x42014db6 = 9;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CHANNEL_SELECTED */
    public static final int f786xae6c664f = 65;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_COMMAND_COMPLETE */
    public static final int f787x964753a5 = 14;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_COMMAND_STATUS */
    public static final int f788x7dbea15e = 15;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CONNECTION_COMP */
    public static final int f789x7f6b28f8 = 3;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CONNECTION_REQUEST */
    public static final int f790xdffd47e6 = 4;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CONNLESS_SLAVE_BROADCAST_CHNL_MAP_CHANGE */
    public static final int f791xdae20327 = 85;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CONNLESS_SLAVE_BROADCAST_RCVD */
    public static final int f792x14902967 = 81;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CONNLESS_SLAVE_BROADCAST_TIMEOUT */
    public static final int f793x67ce6779 = 82;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_CONN_PKT_TYPE_CHANGE */
    public static final int f794x79cc1394 = 29;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_DATA_BUF_OVERFLOW */
    public static final int f795xac1bf60b = 26;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_DISCONNECTION_COMP */
    public static final int f796x66c3d37a = 5;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_DISC_LOGICAL_LINK_COMP */
    public static final int f797x1667150c = 70;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_DISC_PHYSICAL_LINK_COMP */
    public static final int f798xe80e6de = 66;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_ENCRYPTION_CHANGE */
    public static final int f799xc1ea0f14 = 8;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_ENCRYPTION_KEY_REFRESH_COMP */
    public static final int f800x356c7e77 = 48;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_ENHANCED_FLUSH_COMPLETE */
    public static final int f801xfeec5145 = 57;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_ESCO_CONNECTION_CHANGED */
    public static final int f802x43523220 = 45;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_ESCO_CONNECTION_COMP */
    public static final int f803x3a19ef03 = 44;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_EXTENDED_INQUIRY_RESULT */
    public static final int f804x788a92a3 = 47;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_FLOW_SPECIFICATION_COMP */
    public static final int f805xbb37c0c4 = 33;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_FLOW_SPEC_MODIFY_COMP */
    public static final int f806x9a6b2b09 = 71;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_FLUSH_OCCURRED */
    public static final int f807x2b8c6df8 = 17;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_HARDWARE_ERROR */
    public static final int f808x9fca26a9 = 16;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_INQUIRY_COMP */
    public static final int f809x68f4727f = 1;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_INQUIRY_RESULT */
    public static final int f810x16b06b8d = 2;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_INQUIRY_RES_NOTIFICATION */
    public static final int f811xee4d11ba = 86;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_INQUIRY_RSSI_RESULT */
    public static final int f812x898be795 = 34;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_IO_CAPABILITY_REQUEST */
    public static final int f813xd9eca929 = 49;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_IO_CAPABILITY_RESPONSE */
    public static final int f814x66d030c7 = 50;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_KEYPRESS_NOTIFY */
    public static final int f815xae253cc = 60;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_LINK_KEY_NOTIFICATION */
    public static final int f816x8eee9fb8 = 24;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_LINK_KEY_REQUEST */
    public static final int f817xfeac8382 = 23;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_LINK_SUPER_TOUT_CHANGED */
    public static final int f818xfc4d05a0 = 56;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_LOGICAL_LINK_COMP */
    public static final int f819x7ab02686 = 69;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_LOOPBACK_COMMAND */
    public static final int f820xe7352c4f = 25;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_MASTER_LINK_KEY_COMP */
    public static final int f821x61d62c6f = 10;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_MAX_SLOTS_CHANGED */
    public static final int f822xedf1d7 = 27;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_MODE_CHANGE */
    public static final int f823xe03032f4 = 20;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_NUM_COMPL_DATA_BLOCKS */
    public static final int f824xcd378028 = 72;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_NUM_COMPL_DATA_PKTS */
    public static final int f825xbe2c80dc = 19;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_PAGE_SCAN_MODE_CHANGE */
    public static final int f826x7108c1e2 = 31;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_PAGE_SCAN_REP_MODE_CHNG */
    public static final int f827x1d92ef4e = 32;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_PHYSICAL_LINK_COMP */
    public static final int f828x335a04a4 = 64;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_PHY_LINK_LOSS_EARLY_WARNING */
    public static final int f829x521f8313 = 67;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_PHY_LINK_RECOVERY */
    public static final int f830xbe734b04 = 68;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_PIN_CODE_REQUEST */
    public static final int f831x19b3147f = 22;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_QOS_SETUP_COMP */
    public static final int f832xdd71f213 = 13;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_QOS_VIOLATION */
    public static final int f833x95163a63 = 30;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_READ_CLOCK_OFF_COMP */
    public static final int f834x8470c421 = 28;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_READ_RMT_EXT_FEATURES_COMP */
    public static final int f835x6c574abc = 35;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_READ_RMT_FEATURES_COMP */
    public static final int f836xf1dd3cfa = 11;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_READ_RMT_VERSION_COMP */
    public static final int f837xd3014ded = 12;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_REMOTE_OOB_DATA_REQUEST */
    public static final int f838xc511a598 = 53;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_RETURN_LINK_KEYS */
    public static final int f839x69aa9ea2 = 21;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_RMT_HOST_SUP_FEAT_NOTIFY */
    public static final int f840xf8c674ac = 61;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_RMT_NAME_REQUEST_COMP */
    public static final int f841xd0ebf5f5 = 7;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_ROLE_CHANGE */
    public static final int f842x6acb28c1 = 18;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SAM_STATUS_CHANGE */
    public static final int f843x2c84d345 = 88;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SET_TRIGGERED_CLOCK_CAPTURE */
    public static final int f844x270855f8 = 78;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SHORT_RANGE_MODE_COMPLETE */
    public static final int f845xbd41bf78 = 76;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SIMPLE_PAIRING_COMPLETE */
    public static final int f846x6c44bd05 = 54;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SLAVE_PAGE_RES_TIMEOUT */
    public static final int f847xc6be5e32 = 84;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SNIFF_SUB_RATE */
    public static final int f848x2deb408 = 46;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SYNC_TRAIN_CMPL */
    public static final int f849x4e4c8d29 = 79;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_SYNC_TRAIN_RCVD */
    public static final int f850x4e5339e2 = 80;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_TRUNCATED_PAGE_CMPL */
    public static final int f851x4712815d = 83;
    public static final int BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_UNKNOWN = 4095;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_USER_CONFIRMATION_REQUEST */
    public static final int f852xe84a6f81 = 51;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_USER_PASSKEY_NOTIFY */
    public static final int f853x22e0cc36 = 59;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__HCI_EVENT__EVT_USER_PASSKEY_REQUEST */
    public static final int f854xfb9c5b22 = 52;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ADVERTISING_TIMEOUT */
    public static final int f855x82fbdb34 = 60;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_AUTH_FAILURE */
    public static final int f856x47951565 = 5;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f857x37e3ae9d = 46;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CLB_DATA_TOO_BIG */
    public static final int f858xcb2ebd8 = 67;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CLB_NOT_ENABLED */
    public static final int f859xef54ba7d = 66;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_COMMAND_DISALLOWED */
    public static final int f860xe49e2d00 = 12;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CONNECTION_EXISTS */
    public static final int f861x8e868c8b = 11;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CONNECTION_TOUT */
    public static final int f862xb61e4649 = 8;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f863x6c78f133 = 22;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f864x99c9e656 = 62;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f865xd63b8add = 61;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_CONTROLLER_BUSY */
    public static final int f866x72a38fea = 58;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f867x429f8229 = 42;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f868xebc2ca7a = 37;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_HOST_BUSY_PAIRING */
    public static final int f869xf33d747 = 56;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_HOST_REJECT_DEVICE */
    public static final int f870x845feeb1 = 15;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_HOST_REJECT_RESOURCES */
    public static final int f871x200ecfca = 13;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_HOST_REJECT_SECURITY */
    public static final int f872xe29498db = 14;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_HOST_TIMEOUT */
    public static final int f873xf173493c = 16;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_HW_FAILURE */
    public static final int f874x47cbae4c = 3;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ILLEGAL_COMMAND */
    public static final int f875x1bba7670 = 1;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f876x6756da3c = 18;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f877x1f8f0a70 = 54;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_INSTANT_PASSED */
    public static final int f878x7bc70bc0 = 40;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_INSUFFCIENT_SECURITY */
    public static final int f879xc3435a33 = 47;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_INVALID_LMP_PARAM */
    public static final int f880x39093a63 = 30;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_KEY_MISSING */
    public static final int f881x69bd6974 = 6;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f882x7c623c7c = 36;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f883xad5e1c25 = 34;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f884x7c4d6f30 = 35;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f885x9f18b1d = 64;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f886x6f711b12 = 65;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_MAC_CONNECTION_FAILED */
    public static final int f887xdad36a5c = 63;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f888x4ac2b8b3 = 9;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_MAX_NUM_OF_SCOS */
    public static final int f889xed814a76 = 10;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_MEMORY_FULL */
    public static final int f890x46aedd3b = 7;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_NO_CONNECTION */
    public static final int f891xdd1032ca = 2;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f892x9136def4 = 68;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PAGE_TIMEOUT */
    public static final int f893xe70adf23 = 4;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f894x25a77113 = 24;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f895x2c1a20b7 = 41;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f896x2546134a = 48;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PEER_LOW_RESOURCES */
    public static final int f897xc03734af = 20;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PEER_POWER_OFF */
    public static final int f898xb977ef6a = 21;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_PEER_USER */
    public static final int f899x15a5b636 = 19;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_QOS_NOT_SUPPORTED */
    public static final int f900x9719ece6 = 39;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_QOS_REJECTED */
    public static final int f901x36f9631a = 45;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f902x4efea885 = 44;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f903xa30a62af = 57;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_REPEATED_ATTEMPTS */
    public static final int f904x561eaa19 = 23;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f905xf0a9329 = 52;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f906xa69d6984 = 33;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ROLE_SWITCH_FAILED */
    public static final int f907x1c4169b1 = 53;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_ROLE_SWITCH_PENDING */
    public static final int f908x84006283 = 50;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_SCO_AIR_MODE */
    public static final int f909x773d1b6a = 29;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f910xd0979726 = 28;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_SCO_OFFSET_REJECTED */
    public static final int f911xa33abe98 = 27;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f912x6160570 = 55;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_SUCCESS */
    public static final int f913xbef25511 = 0;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f914x3221b6ac = 59;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNDEFINED_0X2_B */
    public static final int f915x9957dbac = 43;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNDEFINED_0X31 */
    public static final int f916xe3ea0f47 = 49;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNDEFINED_0X33 */
    public static final int f917xe3ea0f49 = 51;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNIT_KEY_USED */
    public static final int f918x1c644cc6 = 38;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNKNOWN */
    public static final int f919x1d42c158 = 4095;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNKNOWN_LMP_PDU */
    public static final int f920x135a0baa = 25;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNSPECIFIED */
    public static final int f921x8f736145 = 31;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f922xf0267f8a = 32;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f923xe69da15 = 26;

    /* renamed from: BLUETOOTH_CLASSIC_PAIRING_EVENT_REPORTED__REASON_CODE__STATUS_UNSUPPORTED_VALUE */
    public static final int f924x6f657655 = 17;
    public static final int BLUETOOTH_CLASS_OF_DEVICE_REPORTED = 187;
    public static final int BLUETOOTH_CONNECTION_STATE_CHANGED = 68;

    /* renamed from: BLUETOOTH_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_CONNECTED */
    public static final int f925x6cfa18b5 = 2;

    /* renamed from: BLUETOOTH_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_CONNECTING */
    public static final int f926x32490e6c = 1;

    /* renamed from: BLUETOOTH_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_DISCONNECTED */
    public static final int f927xff491cef = 0;

    /* renamed from: BLUETOOTH_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_DISCONNECTING */
    public static final int f928xe9da9172 = 3;
    public static final int BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED = 158;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ADVERTISING_TIMEOUT */
    public static final int f929x3cbc5b0f = 60;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_AUTH_FAILURE */
    public static final int f930x4526e5ea = 5;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f931x9907b262 = 46;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CLB_DATA_TOO_BIG */
    public static final int f932x542ef1dd = 67;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CLB_NOT_ENABLED */
    public static final int f933x1aed5fd8 = 66;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_COMMAND_DISALLOWED */
    public static final int f934x3d30c5c5 = 12;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CONNECTION_EXISTS */
    public static final int f935x368b4726 = 11;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CONNECTION_TOUT */
    public static final int f936xe1b6eba4 = 8;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f937xb818e64e = 22;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f938x7be3d5f1 = 62;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f939x7e6db162 = 61;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_CONTROLLER_BUSY */
    public static final int f940x9e3c3545 = 58;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f941xa3c385ee = 42;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f942xcddcba15 = 37;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_HOST_BUSY_PAIRING */
    public static final int f943xb73891e2 = 56;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_HOST_REJECT_DEVICE */
    public static final int f944xdcf28776 = 15;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_HOST_REJECT_RESOURCES */
    public static final int f945x6baec4e5 = 13;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_HOST_REJECT_SECURITY */
    public static final int f946x60e41460 = 14;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_HOST_TIMEOUT */
    public static final int f947xef0519c1 = 16;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_HW_FAILURE */
    public static final int f948x4631dc11 = 3;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ILLEGAL_COMMAND */
    public static final int f949x47531bcb = 1;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f950xb2f6cf57 = 18;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f951x47edb8b5 = 54;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_INSTANT_PASSED */
    public static final int f952x5c26cf05 = 40;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_INSUFFCIENT_SECURITY */
    public static final int f953x4192d5b8 = 47;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_INVALID_LMP_PARAM */
    public static final int f954xe10df4fe = 30;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_KEY_MISSING */
    public static final int f955x381cf44f = 6;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f956x3622bc57 = 36;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f957x2bad97aa = 34;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f958xdd7172f5 = 35;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f959x32503962 = 64;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f960xbb11102d = 65;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_MAC_CONNECTION_FAILED */
    public static final int f961x26735f77 = 63;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f962x732166f8 = 9;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_MAX_NUM_OF_SCOS */
    public static final int f963x1919efd1 = 10;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_MEMORY_FULL */
    public static final int f964x150e6816 = 7;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_NO_CONNECTION */
    public static final int f965x91b872e5 = 2;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f966xf25ae2b9 = 68;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PAGE_TIMEOUT */
    public static final int f967xe49cafa8 = 4;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f968xdf67f0ee = 24;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f969xac4d8a92 = 41;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f970x7dd8ac0f = 48;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PEER_LOW_RESOURCES */
    public static final int f971x18c9cd74 = 20;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PEER_POWER_OFF */
    public static final int f972x99d7b2af = 21;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_PEER_USER */
    public static final int f973xf4903bd1 = 19;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_QOS_NOT_SUPPORTED */
    public static final int f974x3f1ea781 = 39;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_QOS_REJECTED */
    public static final int f975x348b339f = 45;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f976x775d56ca = 44;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f977x86817d0a = 57;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_REPEATED_ATTEMPTS */
    public static final int f978xfe2364b4 = 23;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f979xf281ad84 = 52;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f980x8a1483df = 33;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ROLE_SWITCH_FAILED */
    public static final int f981x74d40276 = 53;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_ROLE_SWITCH_PENDING */
    public static final int f982x3dc0e25e = 50;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_SCO_AIR_MODE */
    public static final int f983x74ceebef = 29;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f984x1c378c41 = 28;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_SCO_OFFSET_REJECTED */
    public static final int f985x5cfb3e73 = 27;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f986xae482bf5 = 55;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_SUCCESS */
    public static final int f987xa667856c = 0;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f988x5a8064f1 = 59;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNDEFINED_0X2_B */
    public static final int f989xc4f08107 = 43;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNDEFINED_0X31 */
    public static final int f990xc449d28c = 49;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNDEFINED_0X33 */
    public static final int f991xc449d28e = 51;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNIT_KEY_USED */
    public static final int f992xd10c8ce1 = 38;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNKNOWN */
    public static final int f993x4b7f1b3 = 4095;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNKNOWN_LMP_PDU */
    public static final int f994x3ef2b105 = 25;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNSPECIFIED */
    public static final int f995x5dd2ec20 = 31;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f996xd39d99e5 = 32;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f997xf1e0f470 = 26;

    /* renamed from: BLUETOOTH_DEVICE_FAILED_CONTACT_COUNTER_REPORTED__CMD_STATUS__STATUS_UNSUPPORTED_VALUE */
    public static final int f998x176a30f0 = 17;
    public static final int BLUETOOTH_DEVICE_INFO_REPORTED = 162;

    /* renamed from: BLUETOOTH_DEVICE_INFO_REPORTED__SOURCE_TYPE__DEVICE_INFO_EXTERNAL */
    public static final int f999xd81d90c5 = 2;

    /* renamed from: BLUETOOTH_DEVICE_INFO_REPORTED__SOURCE_TYPE__DEVICE_INFO_INTERNAL */
    public static final int f1000x66a3f3b7 = 1;

    /* renamed from: BLUETOOTH_DEVICE_INFO_REPORTED__SOURCE_TYPE__DEVICE_INFO_SRC_UNKNOWN */
    public static final int f1001x56f00575 = 0;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED = 157;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ADVERTISING_TIMEOUT */
    public static final int f1002xbccdfebf = 60;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_AUTH_FAILURE = 5;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f1003xde3788b2 = 46;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CLB_DATA_TOO_BIG */
    public static final int f1004x9eb76c2d = 67;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CLB_NOT_ENABLED */
    public static final int f1005xdb445b88 = 66;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_COMMAND_DISALLOWED */
    public static final int f1006x783ec15 = 12;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CONNECTION_EXISTS */
    public static final int f1007x3d1216d6 = 11;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CONNECTION_TOUT */
    public static final int f1008xa20de754 = 8;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f1009x7a505dfe = 22;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f1010xe137f5a1 = 62;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f1011x370133b2 = 61;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_CONTROLLER_BUSY */
    public static final int f1012x5e9330f5 = 58;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f1013xe8f35c3e = 42;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f1014x3330d9c5 = 37;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_HOST_BUSY_PAIRING */
    public static final int f1015xbdbf6192 = 56;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_DEVICE */
    public static final int f1016xa745adc6 = 15;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_RESOURCES */
    public static final int f1017x2de63c95 = 13;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_SECURITY */
    public static final int f1018xe306e6b0 = 14;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_HOST_TIMEOUT = 16;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_HW_FAILURE = 3;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ILLEGAL_COMMAND */
    public static final int f1019x7aa177b = 1;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f1020x752e4707 = 18;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f1021xcca53705 = 54;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_INSTANT_PASSED */
    public static final int f1022x9c299d55 = 40;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_INSUFFCIENT_SECURITY */
    public static final int f1023xc3b5a808 = 47;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_INVALID_LMP_PARAM */
    public static final int f1024xe794c4ae = 30;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_KEY_MISSING = 6;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f1025xb6346007 = 36;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f1026xadd069fa = 34;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f1027x22a14945 = 35;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f1028xb707b7b2 = 64;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f1029x7d4887dd = 65;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_MAC_CONNECTION_FAILED */
    public static final int f1030xe8aad727 = 63;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f1031xf7d8e548 = 9;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_MAX_NUM_OF_SCOS */
    public static final int f1032xd970eb81 = 10;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_MEMORY_FULL = 7;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_NO_CONNECTION = 2;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f1033x378ab909 = 68;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PAGE_TIMEOUT = 4;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f1034x5f79949e = 24;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f1035x8781ce42 = 41;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f1036x482bd25f = 48;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PEER_LOW_RESOURCES */
    public static final int f1037xe31cf3c4 = 20;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PEER_POWER_OFF */
    public static final int f1038xd9da80ff = 21;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_PEER_USER = 19;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_QOS_NOT_SUPPORTED */
    public static final int f1039x45a57731 = 39;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_QOS_REJECTED = 45;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f1040xfc14d51a = 44;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f1041x98b9c8ba = 57;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_REPEATED_ATTEMPTS */
    public static final int f1042x4aa3464 = 23;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f1043x4b9f934 = 52;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f1044x9c4ccf8f = 33;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ROLE_SWITCH_FAILED */
    public static final int f1045x3f2728c6 = 53;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_ROLE_SWITCH_PENDING */
    public static final int f1046xbdd2860e = 50;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_SCO_AIR_MODE = 29;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f1047xde6f03f1 = 28;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_SCO_OFFSET_REJECTED */
    public static final int f1048xdd0ce223 = 27;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f1049x66dbae45 = 55;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_SUCCESS = 0;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f1050xdf37e341 = 59;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X2_B */
    public static final int f1051x85477cb7 = 43;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X31 */
    public static final int f1052x44ca0dc = 49;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X33 */
    public static final int f1053x44ca0de = 51;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNIT_KEY_USED = 38;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNKNOWN = 4095;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNKNOWN_LMP_PDU */
    public static final int f1054xff49acb5 = 25;
    public static final int BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNSPECIFIED = 31;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f1055xe5d5e595 = 32;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f1056x4194020 = 26;

    /* renamed from: BLUETOOTH_DEVICE_RSSI_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_VALUE */
    public static final int f1057x1df100a0 = 17;
    public static final int BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED = 159;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ADVERTISING_TIMEOUT */
    public static final int f1058x5afab9c7 = 60;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_AUTH_FAILURE */
    public static final int f1059x8db52632 = 5;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f1060xf15bd4aa = 46;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CLB_DATA_TOO_BIG */
    public static final int f1061x2f93ce25 = 67;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CLB_NOT_ENABLED */
    public static final int f1062x7cd7da90 = 66;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_COMMAND_DISALLOWED */
    public static final int f1063xd2cfb00d = 12;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CONNECTION_EXISTS */
    public static final int f1064xc7c1f3de = 11;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CONNECTION_TOUT */
    public static final int f1065x43a1665c = 8;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f1066x403a7706 = 22;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f1067x2de4aa9 = 62;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f1068x124261aa = 61;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_CONTROLLER_BUSY */
    public static final int f1069x26affd = 58;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f1070xfc17a836 = 42;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f1071x54d72ecd = 37;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_HOST_BUSY_PAIRING */
    public static final int f1072x486f3e9a = 56;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_DEVICE */
    public static final int f1073x729171be = 15;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_RESOURCES */
    public static final int f1074xf3d0559d = 13;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_SECURITY */
    public static final int f1075xa718ca8 = 14;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_HOST_TIMEOUT */
    public static final int f1076x37935a09 = 16;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_HW_FAILURE */
    public static final int f1077x54638e59 = 3;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ILLEGAL_COMMAND */
    public static final int f1078xa93d9683 = 1;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f1079x3b18600f = 18;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f1080xc3fe3efd = 54;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_INSTANT_PASSED */
    public static final int f1081xba261d4d = 40;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_INSUFFCIENT_SECURITY */
    public static final int f1082xeb204e00 = 47;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_INVALID_LMP_PARAM */
    public static final int f1083x7244a1b6 = 30;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_KEY_MISSING */
    public static final int f1084xf0218b07 = 6;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f1085x54611b0f = 36;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f1086xd53b0ff2 = 34;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f1087x35c5953d = 35;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f1088xae60bfaa = 64;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f1089x4332a0e5 = 65;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_MAC_CONNECTION_FAILED */
    public static final int f1090xae94f02f = 63;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f1091xef31ed40 = 9;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_MAX_NUM_OF_SCOS */
    public static final int f1092x7b046a89 = 10;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_MEMORY_FULL */
    public static final int f1093xcd12fece = 7;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_NO_CONNECTION */
    public static final int f1094x5af23b9d = 2;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f1095x4aaf0501 = 68;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PAGE_TIMEOUT */
    public static final int f1096x2d2aeff0 = 4;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f1097xfda64fa6 = 24;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f1098xe827794a = 41;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f1099x13779657 = 48;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PEER_LOW_RESOURCES */
    public static final int f1100xae68b7bc = 20;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PEER_POWER_OFF */
    public static final int f1101xf7d700f7 = 21;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_PEER_USER */
    public static final int f1102xcbbb2089 = 19;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_QOS_NOT_SUPPORTED */
    public static final int f1103xd0555439 = 39;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_QOS_REJECTED */
    public static final int f1104x7d1973e7 = 45;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f1105xf36ddd12 = 44;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f1106x8c81bfc2 = 57;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_REPEATED_ATTEMPTS */
    public static final int f1107x8f5a116c = 23;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f1108xf881f03c = 52;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f1109x9014c697 = 33;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ROLE_SWITCH_FAILED */
    public static final int f1110xa72ecbe = 53;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_ROLE_SWITCH_PENDING */
    public static final int f1111x5bff4116 = 50;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_SCO_AIR_MODE */
    public static final int f1112xbd5d2c37 = 29;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f1113xa4591cf9 = 28;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_SCO_OFFSET_REJECTED */
    public static final int f1114x7b399d2b = 27;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f1115x421cdc3d = 55;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_SUCCESS */
    public static final int f1116x2df43824 = 0;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f1117xd690eb39 = 59;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X2_B */
    public static final int f1118x26dafbbf = 43;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X31 */
    public static final int f1119x224920d4 = 49;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X33 */
    public static final int f1120x224920d6 = 51;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNIT_KEY_USED */
    public static final int f1121x9a465599 = 38;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNKNOWN */
    public static final int f1122x8c44a46b = 4095;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNKNOWN_LMP_PDU */
    public static final int f1123xa0dd2bbd = 25;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNSPECIFIED */
    public static final int f1124x15d782d8 = 31;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f1125xd99ddc9d = 32;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f1126xf7e13728 = 26;

    /* renamed from: BLUETOOTH_DEVICE_TX_POWER_LEVEL_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_VALUE */
    public static final int f1127xa8a0dda8 = 17;
    public static final int BLUETOOTH_ENABLED_STATE_CHANGED = 67;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_AIRPLANE_MODE */
    public static final int f1128xb8788ead = 2;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_APPLICATION_REQUEST */
    public static final int f1129xdfac1cdd = 1;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_CRASH */
    public static final int f1130xd36b68c4 = 7;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_DISALLOWED */
    public static final int f1131xbc998e3d = 3;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_RESTARTED */
    public static final int f1132xd7d898b = 4;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_RESTORE_USER_SETTING */
    public static final int f1133x7104d910 = 9;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_START_ERROR */
    public static final int f1134x4f59ab28 = 5;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_SYSTEM_BOOT */
    public static final int f1135x1a8d667f = 6;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_UNSPECIFIED */
    public static final int f1136x13657a94 = 0;

    /* renamed from: BLUETOOTH_ENABLED_STATE_CHANGED__REASON__ENABLE_DISABLE_REASON_USER_SWITCH */
    public static final int f1137x84f55a5 = 8;
    public static final int BLUETOOTH_ENABLED_STATE_CHANGED__STATE__DISABLED = 2;
    public static final int BLUETOOTH_ENABLED_STATE_CHANGED__STATE__ENABLED = 1;
    public static final int BLUETOOTH_ENABLED_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED = 160;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ACCEPT_CONNECTION_REQUEST */
    public static final int f1138x2ebf19f9 = 1033;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ACCEPT_ESCO_CONNECTION */
    public static final int f1139x50dd3698 = 1065;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ACCEPT_LOGICAL_LINK */
    public static final int f1140xbee016bb = 1081;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ACCEPT_PHYSICAL_LINK */
    public static final int f1141x885faf97 = 1078;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ADD_SCO_CONNECTION */
    public static final int f1142xc820d9c8 = 1031;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_AMP_TEST = 6153;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_AMP_TEST_END = 6152;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_AUTHENTICATION_REQUESTED */
    public static final int f1143x39b7e073 = 1041;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ADD_DEVICE_TO_PERIODIC_ADVERTISING_LIST */
    public static final int f1144x1c9d0134 = 8263;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ADD_DEV_RESOLVING_LIST */
    public static final int f1145xb076f84c = 8231;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ADD_WHITE_LIST */
    public static final int f1146xeb408842 = 8209;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ADV_FILTER = 64855;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_BATCH_SCAN = 64854;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_CLEAR_ADVERTISING_SETS */
    public static final int f1147xb4aa7b2e = 8253;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_CLEAR_PERIODIC_ADVERTISING_LIST */
    public static final int f1148x7423cddb = 8265;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_CLEAR_RESOLVING_LIST */
    public static final int f1149x748d2696 = 8233;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_CLEAR_WHITE_LIST */
    public static final int f1150x8f855716 = 8208;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_CREATE_CONN_CANCEL */
    public static final int f1151x5e437eda = 8206;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_CREATE_LL_CONN */
    public static final int f1152x77774f8 = 8205;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ENCRYPT = 8215;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ENERGY_INFO = 64857;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ENH_RECEIVER_TEST */
    public static final int f1153xbf0bb0b2 = 8243;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_ENH_TRANSMITTER_TEST */
    public static final int f1154xb4d292f0 = 8244;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_EXTENDED_CREATE_CONNECTION */
    public static final int f1155xb782672b = 8259;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_EXTENDED_SCAN_PARAMS */
    public static final int f1156xcfeaee32 = 64858;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_GENERATE_DHKEY */
    public static final int f1157x76d86a1 = 8230;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_LTK_REQ_NEG_REPLY */
    public static final int f1158xbd45448e = 8219;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_LTK_REQ_REPLY */
    public static final int f1159xf65d299d = 8218;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_MULTI_ADV = 64852;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC */
    public static final int f1160x7d7644f = 8260;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC_CANCEL */
    public static final int f1161xe3ca146a = 8261;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_PERIODIC_ADVERTISING_TERMINATE_SYNC */
    public static final int f1162x62cd1fe8 = 8262;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_RAND = 8216;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_RC_PARAM_REQ_NEG_REPLY */
    public static final int f1163xd511cc0a = 8225;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_RC_PARAM_REQ_REPLY */
    public static final int f1164xd3b0cf19 = 8224;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_ADV_CHNL_TX_POWER */
    public static final int f1165x4fc290e1 = 8199;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_BUFFER_SIZE */
    public static final int f1166xd035f227 = 8194;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_CHNL_MAP */
    public static final int f1167x43acbfb9 = 8213;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_DEFAULT_DATA_LENGTH */
    public static final int f1168xdfc11284 = 8227;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_LOCAL_SPT_FEAT */
    public static final int f1169xbd120667 = 8195;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_MAXIMUM_ADVERTISING_DATA_LENGTH */
    public static final int f1170x28d49328 = 8250;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_MAXIMUM_DATA_LENGTH */
    public static final int f1171x5d531be3 = 8239;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_NUMBER_OF_SUPPORTED_ADVERTISING_SETS */
    public static final int f1172x938b1188 = 8251;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_PERIODIC_ADVERTISING_LIST_SIZE */
    public static final int f1173xb01347fc = 8266;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_PHY = 8240;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_REMOTE_FEAT */
    public static final int f1174x91fc2712 = 8214;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_RESOLVABLE_ADDR_LOCAL */
    public static final int f1175x717f4b90 = 8236;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_RESOLVABLE_ADDR_PEER */
    public static final int f1176x7f89e87d = 8235;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_RESOLVING_LIST_SIZE */
    public static final int f1177x5aeac53 = 8234;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_RF_COMPENS_POWER */
    public static final int f1178x1712a70f = 8268;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_SUPPORTED_STATES */
    public static final int f1179x2e7c59ac = 8220;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_TRANSMIT_POWER */
    public static final int f1180x2fb8efaf = 8267;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_READ_WHITE_LIST_SIZE */
    public static final int f1181x80405853 = 8207;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_RECEIVER_TEST */
    public static final int f1182xc2bbb432 = 8221;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_REMOVE_ADVERTISING_SET */
    public static final int f1183x29f8717c = 8252;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_REMOVE_WHITE_LIST */
    public static final int f1184xe5c86cff = 8210;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_RM_DEVICE_FROM_PERIODIC_ADVERTISING_LIST */
    public static final int f1185x84b8507d = 8264;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_RM_DEV_RESOLVING_LIST */
    public static final int f1186x63df8f2 = 8232;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_ADDR_RESOLUTION_ENABLE */
    public static final int f1187x762db6f5 = 8237;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_DATA_LENGTH */
    public static final int f1188xc520a3ee = 8226;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_DEFAULT_PHY */
    public static final int f1189x6a654116 = 8241;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EVENT_MASK */
    public static final int f1190x61b9205e = 8193;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXTENDED_SCAN_ENABLE */
    public static final int f1191x2e043a0c = 8258;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXTENDED_SCAN_PARAMETERS */
    public static final int f1192x689861f3 = 8257;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXT_ADVERTISING_DATA */
    public static final int f1193xaa2c7910 = 8247;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXT_ADVERTISING_ENABLE */
    public static final int f1194xd355e969 = 8249;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXT_ADVERTISING_PARAM */
    public static final int f1195x9c0bbbc7 = 8246;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXT_ADVERTISING_RANDOM_ADDRESS */
    public static final int f1196xa2cbbe3e = 8245;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_EXT_ADVERTISING_SCAN_RESP */
    public static final int f1197x154332ac = 8248;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_HOST_CHNL_CLASS */
    public static final int f1198xe1c5cb46 = 8212;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_PERIODIC_ADVERTISING_DATA */
    public static final int f1199xe90f02bc = 8255;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE */
    public static final int f1200xe3bcb815 = 8256;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_PERIODIC_ADVERTISING_PARAM */
    public static final int f1201x397a679b = 8254;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_PHY = 8242;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_PRIVACY_MODE */
    public static final int f1202xe38483a7 = 8270;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_SET_RAND_PRIV_ADDR_TIMOUT */
    public static final int f1203xbd894221 = 8238;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_START_ENC = 8217;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_TEST_END = 8223;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_TRACK_ADV = 64856;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_TRANSMITTER_TEST */
    public static final int f1204xd4b9df70 = 8222;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_UPD_LL_CONN_PARAMS */
    public static final int f1205xc22dea60 = 8211;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_VENDOR_CAP = 64851;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_ADV_DATA */
    public static final int f1206xd4ac4f06 = 8200;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_ADV_ENABLE */
    public static final int f1207x5d3819df = 8202;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_ADV_PARAMS */
    public static final int f1208x6f4dede2 = 8198;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_DEFAULT_DATA_LENGTH */
    public static final int f1209xcfe3a2cd = 8228;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_LOCAL_SPT_FEAT */
    public static final int f1210x2526ac7e = 8196;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_RANDOM_ADDR */
    public static final int f1211xca2b20bd = 8197;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_RF_COMPENS_POWER */
    public static final int f1212xcc962366 = 8269;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_SCAN_ENABLE */
    public static final int f1213xec22c55 = 8204;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_SCAN_PARAMS */
    public static final int f1214x20d80058 = 8203;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BLE_WRITE_SCAN_RSP_DATA */
    public static final int f1215xd1048e0c = 8201;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_BRCM_SET_ACL_PRIORITY */
    public static final int f1216x30306cef = 64599;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CHANGE_CONN_LINK_KEY */
    public static final int f1217x7318120a = 1045;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CHANGE_CONN_PACKET_TYPE */
    public static final int f1218xa1d8c501 = 1039;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CHANGE_LOCAL_NAME */
    public static final int f1219xc365e642 = 3091;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CONTROLLER_A2DP_OPCODE */
    public static final int f1220x17d7be79 = 64861;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CONTROLLER_DEBUG_INFO */
    public static final int f1221x52752d11 = 64859;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CREATE_CONNECTION */
    public static final int f1222x2139a155 = 1029;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CREATE_CONNECTION_CANCEL */
    public static final int f1223xef2044a4 = 1032;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CREATE_LOGICAL_LINK */
    public static final int f1224x253c3747 = 1080;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CREATE_NEW_UNIT_KEY */
    public static final int f1225x5dfe699a = 3083;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_CREATE_PHYSICAL_LINK */
    public static final int f1226xed87a08b = 1077;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_DELETE_RESERVED_LT_ADDR */
    public static final int f1227xfa50e299 = 3189;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_DELETE_STORED_LINK_KEY */
    public static final int f1228xdca1502e = 3090;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_DISCONNECT = 1030;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_DISCONNECT_LOGICAL_LINK */
    public static final int f1229xf73ca1a7 = 1082;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_DISCONNECT_PHYSICAL_LINK */
    public static final int f1230x5b94822b = 1079;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ENABLE_AMP_RCVR_REPORTS */
    public static final int f1231x22f10658 = 6151;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ENABLE_DEV_UNDER_TEST_MODE */
    public static final int f1232xd8a9e68f = 6147;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ENHANCED_FLUSH = 3167;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ENH_ACCEPT_ESCO_CONNECTION */
    public static final int f1233x2bdfac18 = 1086;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ENH_SETUP_ESCO_CONNECTION */
    public static final int f1234x47d0f895 = 1085;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_EXIT_PARK_MODE = 2054;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_EXIT_PERIODIC_INQUIRY_MODE */
    public static final int f1235x2052f72a = 1028;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_EXIT_SNIFF_MODE = 2052;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_FLOW_SPECIFICATION */
    public static final int f1236xa916babe = 2064;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_FLOW_SPEC_MODIFY */
    public static final int f1237xf28b7e59 = 1084;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_FLUSH = 3080;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_GET_LINK_QUALITY */
    public static final int f1238xef62da2f = 5123;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_GET_MWS_TRANSPORT_CFG */
    public static final int f1239x1f193cc3 = 5132;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_GET_MWS_TRANS_LAYER_CFG */
    public static final int f1240x484950b4 = 3084;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_HOLD_MODE = 2049;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_HOST_BUFFER_SIZE */
    public static final int f1241xd7655ab5 = 3123;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_HOST_NUM_PACKETS_DONE */
    public static final int f1242x2b0d95ba = 3125;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_INQUIRY = 1025;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_INQUIRY_CANCEL = 1026;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_IO_CAPABILITY_REQUEST_REPLY */
    public static final int f1243xdc115960 = 1067;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_IO_CAP_REQ_NEG_REPLY */
    public static final int f1244x4d7ed840 = 1076;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_LINK_KEY_REQUEST_NEG_REPLY */
    public static final int f1245x8b2d9012 = 1036;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_LINK_KEY_REQUEST_REPLY */
    public static final int f1246xe81fd721 = 1035;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_LOGICAL_LINK_CANCEL */
    public static final int f1247x9011015d = 1083;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_MASTER_LINK_KEY = 1047;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_PARK_MODE = 2053;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_PERIODIC_INQUIRY_MODE */
    public static final int f1248x20bbddb3 = 1027;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_PIN_CODE_REQUEST_NEG_REPLY */
    public static final int f1249xe144c8cf = 1038;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_PIN_CODE_REQUEST_REPLY */
    public static final int f1250xef82f65e = 1037;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_QOS_SETUP = 2055;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_AFH_ASSESSMENT_MODE */
    public static final int f1251xe7850387 = 3144;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_AFH_CH_MAP = 5126;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f1252x510ed295 = 3195;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_AUTHENTICATION_ENABLE */
    public static final int f1253xebcf8a6d = 3103;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_AUTOMATIC_FLUSH_TIMEOUT */
    public static final int f1254x4a7e7035 = 3111;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_BD_ADDR = 4105;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_BE_FLUSH_TOUT */
    public static final int f1255x68f28094 = 3177;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_BLE_HOST_SUPPORT */
    public static final int f1256xcfa80979 = 3180;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_BUFFER_SIZE */
    public static final int f1257x5abd49a3 = 4101;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_CLASS_OF_DEVICE */
    public static final int f1258x4e5c531a = 3107;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_CLOCK = 5127;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_CONN_ACCEPT_TOUT */
    public static final int f1259x1bc58b7b = 3093;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_COUNTRY_CODE */
    public static final int f1260x864d3c73 = 4103;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_CURRENT_IAC_LAP */
    public static final int f1261x2a1bc804 = 3129;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_DATA_BLOCK_SIZE */
    public static final int f1262x99a03fcb = 4106;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_DEF_POLICY_SETTINGS */
    public static final int f1263xea0cd639 = 2062;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_ENCRYPTION_MODE */
    public static final int f1264xa9428862 = 3105;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_ENCR_KEY_SIZE */
    public static final int f1265xcedb3bcb = 5128;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_ENHANCED_TX_PWR_LEVEL */
    public static final int f1266xee7948c1 = 3176;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_ERRONEOUS_DATA_RPT */
    public static final int f1267x84c71791 = 3162;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_EXTENDED_INQUIRY_LENGTH */
    public static final int f1268x14b440c7 = 3200;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_EXTENDED_PAGE_TIMEOUT */
    public static final int f1269x50d4de1a = 3198;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_EXT_INQ_RESPONSE */
    public static final int f1270xa15f95af = 3153;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_FAILED_CONTACT_COUNTER */
    public static final int f1271x901633b8 = 5121;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_FLOW_CONTROL_MODE */
    public static final int f1272xd5928d99 = 3174;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_HOLD_MODE_ACTIVITY */
    public static final int f1273x21da5c48 = 3115;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_INQSCAN_TYPE */
    public static final int f1274xb7c778cd = 3138;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_INQUIRYSCAN_CFG */
    public static final int f1275x2f77c9ac = 3101;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_INQUIRY_MODE */
    public static final int f1276xe5a34c98 = 3140;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_INQ_TX_POWER_LEVEL */
    public static final int f1277x2d5168bf = 3160;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LINK_SUPER_TOUT */
    public static final int f1278x7a30b1a6 = 3126;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LMP_HANDLE = 1056;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_AMP_ASSOC */
    public static final int f1279x2cb9e929 = 5130;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_AMP_INFO */
    public static final int f1280x8595f0fa = 5129;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_EXT_FEATURES */
    public static final int f1281x30cb6acc = 4100;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_FEATURES */
    public static final int f1282xcb9bc6ce = 4099;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_NAME = 3092;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_OOB_DATA */
    public static final int f1283x85cc8df8 = 3159;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_OOB_EXTENDED_DATA */
    public static final int f1284x4ae23062 = 3197;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_SUPPORTED_CMDS */
    public static final int f1285x80021d5b = 4098;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_SUPPORTED_CODECS */
    public static final int f1286x880c357f = 4107;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCAL_VERSION_INFO */
    public static final int f1287x2a3c61e6 = 4097;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOCATION_DATA */
    public static final int f1288xf2c07e37 = 3172;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOGICAL_LINK_ACCEPT_TIMEOUT */
    public static final int f1289x11128d9c = 3169;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_LOOPBACK_MODE */
    public static final int f1290x1208441a = 6145;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_NUM_BCAST_REXMITS */
    public static final int f1291x191cc90c = 3113;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_NUM_SUPPORTED_IAC */
    public static final int f1292x93cd58a4 = 3128;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_PAGESCAN_CFG */
    public static final int f1293x882ece6e = 3099;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_PAGESCAN_MODE */
    public static final int f1294x7dafacb9 = 3133;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_PAGESCAN_PERIOD_MODE */
    public static final int f1295xa6d7718b = 3131;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_PAGESCAN_TYPE */
    public static final int f1296x7db30250 = 3142;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_PAGE_TOUT = 3095;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_PIN_TYPE = 3081;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_POLICY_SETTINGS */
    public static final int f1297xb6365c13 = 2060;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_RMT_CLOCK_OFFSET */
    public static final int f1298x422d73e7 = 1055;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_RMT_EXT_FEATURES */
    public static final int f1299xcb54d29e = 1052;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_RMT_FEATURES */
    public static final int f1300x2ca5e7a0 = 1051;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_RMT_VERSION_INFO */
    public static final int f1301xc4c5c9b8 = 1053;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_RSSI = 5125;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_SCAN_ENABLE */
    public static final int f1302xb2b19a88 = 3097;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_SCO_FLOW_CTRL_ENABLE */
    public static final int f1303x7637ae3 = 3118;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_SECURE_CONNS_SUPPORT */
    public static final int f1304xfa56f9ac = 3193;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_SIMPLE_PAIRING_MODE */
    public static final int f1305x77c6b44a = 3157;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_STORED_LINK_KEY */
    public static final int f1306x4f674d19 = 3085;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_SYNC_TRAIN_PARAM */
    public static final int f1307xf433e08f = 3191;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_TRANSMIT_POWER_LEVEL */
    public static final int f1308xed358ad8 = 3117;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_READ_VOICE_SETTINGS */
    public static final int f1309xaf0caccd = 3109;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_RECEIVE_CLB = 1090;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_RECEIVE_SYNC_TRAIN */
    public static final int f1310xaf6a9d6c = 1092;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_REFRESH_ENCRYPTION_KEY */
    public static final int f1311x9f583eb3 = 3155;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_REJECT_CONNECTION_REQUEST */
    public static final int f1312x518e0e02 = 1034;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_REJECT_ESCO_CONNECTION */
    public static final int f1313xb2f4e72f = 1066;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_REM_OOB_DATA_REQ_NEG_REPLY */
    public static final int f1314x829a8273 = 1075;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_REM_OOB_DATA_REQ_REPLY */
    public static final int f1315xb7df2202 = 1072;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_REM_OOB_EXTENDED_DATA_REQ_REPLY */
    public static final int f1316xbe57e86c = 1093;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_RESET = 3075;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_RESET_FAILED_CONTACT_COUNTER */
    public static final int f1317x533389d7 = 5122;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_RMT_NAME_REQUEST */
    public static final int f1318x845a8fed = 1049;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_RMT_NAME_REQUEST_CANCEL */
    public static final int f1319xcd6a3b0c = 1050;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_ROLE_DISCOVERY = 2057;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SEND_KEYPRESS_NOTIF */
    public static final int f1320xea3b1c40 = 3168;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SETUP_ESCO_CONNECTION */
    public static final int f1321x1fb81e15 = 1064;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_AFH_CHANNELS */
    public static final int f1322xf06732d5 = 3135;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_CLB = 1089;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_CONN_ENCRYPTION */
    public static final int f1323x25052ecd = 1043;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_EVENT_FILTER */
    public static final int f1324x56f5a3e6 = 3077;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_EVENT_MASK = 3073;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_EVENT_MASK_PAGE_2 */
    public static final int f1325x9f2a4b87 = 3171;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_EXTERNAL_FRAME_CONFIGURATION */
    public static final int f1326x978d5ff9 = 3183;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_HC_TO_HOST_FLOW_CTRL */
    public static final int f1327x70f0ab0e = 3121;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_MWS_CHANNEL_PARAMETERS */
    public static final int f1328xb434bea5 = 3182;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_MWS_PATTERN_CONFIGURATION */
    public static final int f1329xcf47f208 = 3187;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_MWS_SCAN_FREQUENCY_TABLE */
    public static final int f1330x46487d48 = 3186;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_MWS_SIGNALING */
    public static final int f1331x1c1c915b = 3184;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_MWS_TRANSPORT_LAYER */
    public static final int f1332x388d20fc = 3185;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_RESERVED_LT_ADDR */
    public static final int f1333x525704ba = 3188;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SET_TRIGGERED_CLK_CAPTURE */
    public static final int f1334xd1891ff8 = 5133;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SHORT_RANGE_MODE */
    public static final int f1335x8e4a574 = 3179;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SNIFF_MODE = 2051;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SNIFF_SUB_RATE = 2065;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_START_SYNC_TRAIN */
    public static final int f1336x17ec958d = 1091;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_SWITCH_ROLE = 2059;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_TRUNCATED_PAGE = 1087;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_TRUNCATED_PAGE_CANCEL */
    public static final int f1337xbd8f485d = 1088;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_UNKNOWN = 1048575;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_USER_CONF_REQUEST_REPLY */
    public static final int f1338xde393467 = 1068;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_USER_CONF_VALUE_NEG_REPLY */
    public static final int f1339xbfaf99fa = 1069;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_USER_PASSKEY_REQ_NEG_REPLY */
    public static final int f1340xe7c48881 = 1071;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_USER_PASSKEY_REQ_REPLY */
    public static final int f1341xde2a5f10 = 1070;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_AFH_ASSESSMENT_MODE */
    public static final int f1342x579ecc58 = 3145;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f1343x999f0c24 = 3196;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_AUTHENTICATION_ENABLE */
    public static final int f1344xbc9a62fe = 3104;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_AUTOMATIC_FLUSH_TIMEOUT */
    public static final int f1345x13f56886 = 3112;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_BE_FLUSH_TOUT */
    public static final int f1346xbf037a25 = 3178;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_BLE_HOST_SUPPORT */
    public static final int f1347x710a5a48 = 3181;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_CLASS_OF_DEVICE */
    public static final int f1348x64152c6b = 3108;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_CLB_DATA = 3190;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_CONN_ACCEPT_TOUT */
    public static final int f1349xbd27dc4a = 3094;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_CURRENT_IAC_LAP */
    public static final int f1350x3fd4a155 = 3130;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_DEF_POLICY_SETTINGS */
    public static final int f1351x5a269f0a = 2063;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_ENCRYPTION_MODE */
    public static final int f1352xbefb61b3 = 3106;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_ERRONEOUS_DATA_RPT */
    public static final int f1353x56d870a0 = 3163;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_EXTENDED_INQUIRY_LENGTH */
    public static final int f1354xde2b3918 = 3201;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_EXTENDED_PAGE_TIMEOUT */
    public static final int f1355x219fb6ab = 3199;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_EXT_INQ_RESPONSE */
    public static final int f1356x42c1e67e = 3154;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_FLOW_CONTROL_MODE */
    public static final int f1357x607a56aa = 3175;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_HOLD_MODE_ACTIVITY */
    public static final int f1358xf3ebb557 = 3116;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_INQSCAN_TYPE */
    public static final int f1359xe3d8891c = 3139;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_INQUIRYSCAN_CFG */
    public static final int f1360x4530a2fd = 3102;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_INQUIRY_MODE */
    public static final int f1361x11b45ce7 = 3141;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_INQ_TX_POWER_LEVEL */
    public static final int f1362xff62c1ce = 3161;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_LINK_SUPER_TOUT */
    public static final int f1363x8fe98af7 = 3127;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_LOCATION_DATA */
    public static final int f1364x48d177c8 = 3173;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_LOGICAL_LINK_ACCEPT_TIMEOUT */
    public static final int f1365xdac2f56d = 3170;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_LOOPBACK_MODE */
    public static final int f1366x68193dab = 6146;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_NUM_BCAST_REXMITS */
    public static final int f1367xa404921d = 3114;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_PAGESCAN_CFG */
    public static final int f1368xb43fdebd = 3100;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_PAGESCAN_MODE */
    public static final int f1369xd3c0a64a = 3134;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_PAGESCAN_PERIOD_MODE */
    public static final int f1370x39f6c2da = 3132;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_PAGESCAN_TYPE */
    public static final int f1371xd3c3fbe1 = 3143;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_PAGE_TOUT = 3096;
    public static final int BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_PIN_TYPE = 3082;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_POLICY_SETTINGS */
    public static final int f1372xcbef3564 = 2061;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_REMOTE_AMP_ASSOC */
    public static final int f1373x5d5eb28d = 5131;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SCAN_ENABLE */
    public static final int f1374xd525c459 = 3098;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SCO_FLOW_CTRL_ENABLE */
    public static final int f1375x9a82cc32 = 3119;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SECURE_CONNS_SUPPORT */
    public static final int f1376x8d764afb = 3194;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SECURE_CONN_TEST_MODE */
    public static final int f1377x255c7f79 = 6154;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SIMPLE_PAIRING_MODE */
    public static final int f1378xe7e07d1b = 3158;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SIMP_PAIR_DEBUG_MODE */
    public static final int f1379x304c889a = 6148;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_STORED_LINK_KEY */
    public static final int f1380x6520266a = 3089;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_SYNC_TRAIN_PARAM */
    public static final int f1381x9596315e = 3192;

    /* renamed from: BLUETOOTH_HCI_TIMEOUT_REPORTED__HCI_COMMAND__CMD_WRITE_VOICE_SETTINGS */
    public static final int f1382x1b1ae55c = 3110;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT = 125;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ADVERTISING_TIMEOUT */
    public static final int f1383x575f310b = 60;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_AUTH_FAILURE */
    public static final int f1384x6792936e = 5;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f1385x402368e6 = 46;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CLB_DATA_TOO_BIG */
    public static final int f1386xd6403d61 = 67;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CLB_NOT_ENABLED */
    public static final int f1387xab8293d4 = 66;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_COMMAND_DISALLOWED */
    public static final int f1388x801d4049 = 12;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CONNECTION_EXISTS */
    public static final int f1389xf6a36c22 = 11;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CONNECTION_TOUT */
    public static final int f1390x724c1fa0 = 8;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f1391xb55e2d4a = 22;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f1392x36f53eed = 62;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f1393xcd75d6e6 = 61;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_CONTROLLER_BUSY */
    public static final int f1394x2ed16941 = 58;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f1395x4adf3c72 = 42;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f1396x88ee2311 = 37;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_HOST_BUSY_PAIRING */
    public static final int f1397x7750b6de = 56;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_HOST_REJECT_DEVICE */
    public static final int f1398x1fdf01fa = 15;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_HOST_REJECT_RESOURCES */
    public static final int f1399x68f40be1 = 13;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_HOST_REJECT_SECURITY */
    public static final int f1400x9a9bfde4 = 14;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_HOST_TIMEOUT */
    public static final int f1401x1170c745 = 16;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_HW_FAILURE */
    public static final int f1402xd3f31a95 = 3;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ILLEGAL_COMMAND */
    public static final int f1403xd7e84fc7 = 1;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f1404xb03c1653 = 18;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f1405xf3515139 = 54;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_INSTANT_PASSED */
    public static final int f1406x925d2b89 = 40;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_INSUFFCIENT_SECURITY */
    public static final int f1407x7b4abf3c = 47;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_INVALID_LMP_PARAM */
    public static final int f1408xa12619fa = 30;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_KEY_MISSING */
    public static final int f1409x6283864b = 6;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f1410x50c59253 = 36;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f1411x6565812e = 34;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f1412x848d2979 = 35;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f1413xddb3d1e6 = 64;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f1414xb8565729 = 65;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_MAC_CONNECTION_FAILED */
    public static final int f1415x23b8a673 = 63;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f1416x1e84ff7c = 9;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_MAX_NUM_OF_SCOS */
    public static final int f1417xa9af23cd = 10;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_MEMORY_FULL */
    public static final int f1418x3f74fa12 = 7;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_NO_CONNECTION */
    public static final int f1419xbcc275e1 = 2;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f1420x9976993d = 68;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PAGE_TIMEOUT */
    public static final int f1421x7085d2c = 4;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f1422xfa0ac6ea = 24;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f1423xffdce88e = 41;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f1424xc0c52693 = 48;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PEER_LOW_RESOURCES */
    public static final int f1425x5bb647f8 = 20;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PEER_POWER_OFF */
    public static final int f1426xd00e0f33 = 21;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_PEER_USER */
    public static final int f1427x1a2b1ccd = 19;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_QOS_NOT_SUPPORTED */
    public static final int f1428xff36cc7d = 39;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_QOS_REJECTED */
    public static final int f1429x56f6e123 = 45;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f1430x22c0ef4e = 44;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f1431x4790f506 = 57;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_REPEATED_ATTEMPTS */
    public static final int f1432xbe3b89b0 = 23;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f1433xb3912580 = 52;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f1434x4b23fbdb = 33;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ROLE_SWITCH_FAILED */
    public static final int f1435xb7c07cfa = 53;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_ROLE_SWITCH_PENDING */
    public static final int f1436x5863b85a = 50;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_SCO_AIR_MODE */
    public static final int f1437x973a9973 = 29;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f1438x197cd33d = 28;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_SCO_OFFSET_REJECTED */
    public static final int f1439x779e146f = 27;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f1440xfd505179 = 55;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_SUCCESS */
    public static final int f1441x55fe7568 = 0;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f1442x5e3fd75 = 59;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNDEFINED_0X2_B */
    public static final int f1443x5585b503 = 43;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNDEFINED_0X31 */
    public static final int f1444xfa802f10 = 49;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNDEFINED_0X33 */
    public static final int f1445xfa802f12 = 51;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNIT_KEY_USED */
    public static final int f1446xfc168fdd = 38;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNKNOWN */
    public static final int f1447xb44ee1af = 4095;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNKNOWN_LMP_PDU */
    public static final int f1448xcf87e501 = 25;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNSPECIFIED */
    public static final int f1449x88397e1c = 31;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f1450x94ad11e1 = 32;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f1451xb2f06c6c = 26;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__CMD_STATUS__STATUS_UNSUPPORTED_VALUE */
    public static final int f1452xd78255ec = 17;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__DIRECTION__DIRECTION_INCOMING */
    public static final int f1453x6fec04aa = 2;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__DIRECTION__DIRECTION_OUTGOING */
    public static final int f1454x6e113ff0 = 1;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__DIRECTION__DIRECTION_UNKNOWN */
    public static final int f1455x8f5b7b26 = 0;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_ADVERTISING_SET_TERMINATED_EVT */
    public static final int f1456xd395a7c9 = 18;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_ADV_PKT_RPT_EVT */
    public static final int f1457x9e0e1f7e = 2;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_CHNL_SELECTION_ALGORITHM */
    public static final int f1458x431f0eca = 20;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_CONN_COMPLETE_EVT */
    public static final int f1459x89391c46 = 1;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_DATA_LENGTH_CHANGE_EVT */
    public static final int f1460x30886fc2 = 7;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_DIRECT_ADV_EVT */
    public static final int f1461x9c788d2b = 11;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_ENHANCED_CONN_COMPLETE_EVT */
    public static final int f1462xbdff67d1 = 10;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_EXTENDED_ADVERTISING_REPORT_EVT */
    public static final int f1463x2c8c07ef = 13;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_GEN_DHKEY_CMPL */
    public static final int f1464x63d70643 = 9;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_LL_CONN_PARAM_UPD_EVT */
    public static final int f1465xf12c4a5d = 3;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_LTK_REQ_EVT */
    public static final int f1466x28b916dc = 5;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_PERIODIC_ADV_REPORT_EVT */
    public static final int f1467x8652b95e = 15;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_PERIODIC_ADV_SYNC_EST_EVT */
    public static final int f1468x23a0516c = 14;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_PERIODIC_ADV_SYNC_LOST_EVT */
    public static final int f1469x51b4e066 = 16;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_PHY_UPDATE_COMPLETE_EVT */
    public static final int f1470xea508d6b = 12;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_RC_PARAM_REQ_EVT */
    public static final int f1471x62272c0c = 6;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_READ_LOCAL_P256_PUB_KEY */
    public static final int f1472x8ebeeb34 = 8;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_READ_REMOTE_FEAT_CMPL_EVT */
    public static final int f1473x11ab977d = 4;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_SCAN_REQ_RX_EVT */
    public static final int f1474xb67af803 = 19;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_SCAN_TIMEOUT_EVT */
    public static final int f1475x91823bed = 17;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_BLE_EVENT__BLE_EVT_UNKNOWN */
    public static final int f1476xcdc6c400 = 4095;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ACCEPT_CONNECTION_REQUEST */
    public static final int f1477xaeedfe72 = 1033;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ACCEPT_ESCO_CONNECTION */
    public static final int f1478xd7e04cbf = 1065;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ACCEPT_LOGICAL_LINK */
    public static final int f1479xb34e09f4 = 1081;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ACCEPT_PHYSICAL_LINK */
    public static final int f1480x21b0237e = 1078;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ADD_SCO_CONNECTION */
    public static final int f1481xaefb1b6f = 1031;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_AMP_TEST = 6153;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_AMP_TEST_END = 6152;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_AUTHENTICATION_REQUESTED */
    public static final int f1482xc4e08da = 1041;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ADD_DEVICE_TO_PERIODIC_ADVERTISING_LIST */
    public static final int f1483x21e3196d = 8263;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ADD_DEV_RESOLVING_LIST */
    public static final int f1484x3624a2f3 = 8231;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ADD_WHITE_LIST */
    public static final int f1485xd21ac9e9 = 8209;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ADV_FILTER */
    public static final int f1486x8c9bb87b = 64855;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_BATCH_SCAN */
    public static final int f1487x2fc9ac59 = 64854;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_CLEAR_ADVERTISING_SETS */
    public static final int f1488x3a5825d5 = 8253;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_CLEAR_PERIODIC_ADVERTISING_LIST */
    public static final int f1489x221e2f14 = 8265;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_CLEAR_RESOLVING_LIST */
    public static final int f1490x47234efd = 8233;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_CLEAR_WHITE_LIST */
    public static final int f1491x28d5cafd = 8208;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_CREATE_CONN_CANCEL */
    public static final int f1492xe5469501 = 8206;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_CREATE_LL_CONN */
    public static final int f1493xee51b69f = 8205;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ENCRYPT = 8215;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ENERGY_INFO */
    public static final int f1494x178c8b0e = 64857;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ENH_RECEIVER_TEST */
    public static final int f1495x4fc9b9ab = 8243;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_ENH_TRANSMITTER_TEST */
    public static final int f1496x8768bb57 = 8244;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_EXTENDED_CREATE_CONNECTION */
    public static final int f1497x37c6652 = 8259;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_EXTENDED_SCAN_PARAMS */
    public static final int f1498xa2811699 = 64858;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_GENERATE_DHKEY */
    public static final int f1499xee47c848 = 8230;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_LTK_REQ_NEG_REPLY */
    public static final int f1500x4e034d87 = 8219;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_LTK_REQ_REPLY */
    public static final int f1501x81f09716 = 8218;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_MULTI_ADV */
    public static final int f1502x3601c9b6 = 64852;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC */
    public static final int f1503x19292a36 = 8260;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_PERIODIC_ADVERTISING_CREATE_SYNC_CANCEL */
    public static final int f1504xe9102ca3 = 8261;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_PERIODIC_ADVERTISING_TERMINATE_SYNC */
    public static final int f1505xddd23ca1 = 8262;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_RAND = 8216;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_RC_PARAM_REQ_NEG_REPLY */
    public static final int f1506x5abf76b1 = 8225;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_RC_PARAM_REQ_REPLY */
    public static final int f1507x5ab3e540 = 8224;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_ADV_CHNL_TX_POWER */
    public static final int f1508xd5703b88 = 8199;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_BUFFER_SIZE */
    public static final int f1509x6986660e = 8194;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_CHNL_MAP */
    public static final int f1510xcf402d32 = 8213;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_DEFAULT_DATA_LENGTH */
    public static final int f1511xb0aeaf6b = 8227;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_LOCAL_SPT_FEAT */
    public static final int f1512x1671b520 = 8195;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_MAXIMUM_ADVERTISING_DATA_LENGTH */
    public static final int f1513xe730d8f = 8250;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_MAXIMUM_DATA_LENGTH */
    public static final int f1514x2e40b8ca = 8239;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_NUMBER_OF_SUPPORTED_ADVERTISING_SETS */
    public static final int f1515x5fabff81 = 8251;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_PERIODIC_ADVERTISING_LIST_SIZE */
    public static final int f1516x2b1864b5 = 8266;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_PHY = 8240;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_REMOTE_FEAT */
    public static final int f1517x2b4c9af9 = 8214;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_RESOLVABLE_ADDR_LOCAL */
    public static final int f1518xbd794ab7 = 8236;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_RESOLVABLE_ADDR_PEER */
    public static final int f1519xcc4fe876 = 8235;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_RESOLVING_LIST_SIZE */
    public static final int f1520xd69c493a = 8234;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_RF_COMPENS_POWER */
    public static final int f1521x97418b88 = 8268;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_SUPPORTED_STATES */
    public static final int f1522xaeab3e25 = 8220;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_TRANSMIT_POWER */
    public static final int f1523x89189e68 = 8267;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_READ_WHITE_LIST_SIZE */
    public static final int f1524x52d680ba = 8207;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_RECEIVER_TEST */
    public static final int f1525x4e4f21ab = 8221;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_REMOVE_ADVERTISING_SET */
    public static final int f1526xafa61c23 = 8252;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_REMOVE_WHITE_LIST */
    public static final int f1527x768675f8 = 8210;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_RM_DEVICE_FROM_PERIODIC_ADVERTISING_LIST */
    public static final int f1528x28353f64 = 8264;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_RM_DEV_RESOLVING_LIST */
    public static final int f1529x866cdd6b = 8232;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_ADDR_RESOLUTION_ENABLE */
    public static final int f1530xc227b61c = 8237;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_DATA_LENGTH */
    public static final int f1531xb98e9727 = 8226;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_DEFAULT_PHY */
    public static final int f1532x5ed3344f = 8241;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EVENT_MASK */
    public static final int f1533x48936205 = 8193;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXTENDED_SCAN_ENABLE */
    public static final int f1534xfef1d6f3 = 8258;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXTENDED_SCAN_PARAMETERS */
    public static final int f1535x9e0f335a = 8257;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_DATA */
    public static final int f1536x7b1a15f7 = 8247;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_ENABLE */
    public static final int f1537x1f4fe890 = 8249;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_PARAM */
    public static final int f1538xe8d1bbc0 = 8246;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_RANDOM_ADDRESS */
    public static final int f1539xa6c3a665 = 8245;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_EXT_ADVERTISING_SCAN_RESP */
    public static final int f1540x8ea68e25 = 8248;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_HOST_CHNL_CLASS */
    public static final int f1541x3b2579ff = 8212;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_PERIODIC_ADVERTISING_DATA */
    public static final int f1542x62725e35 = 8255;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE */
    public static final int f1543x91b7194e = 8256;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_PERIODIC_ADVERTISING_PARAM */
    public static final int f1544xec827b42 = 8254;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_PHY = 8242;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_PRIVACY_MODE */
    public static final int f1545x7cd4f78e = 8270;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_SET_RAND_PRIV_ADDR_TIMOUT */
    public static final int f1546xa4f421a = 8238;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_START_ENC */
    public static final int f1547xff080c86 = 8217;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_TEST_END = 8223;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_TRACK_ADV */
    public static final int f1548xa9728cc8 = 64856;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_TRANSMITTER_TEST */
    public static final int f1549x6e0a5357 = 8222;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_UPD_LL_CONN_PARAMS */
    public static final int f1550x49310087 = 8211;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_VENDOR_CAP */
    public static final int f1551x67779c12 = 64851;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_ADV_DATA */
    public static final int f1552xbb8690ad = 8200;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_ADV_ENABLE */
    public static final int f1553xf6888dc6 = 8202;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_ADV_PARAMS */
    public static final int f1554x89e61c9 = 8198;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_DEFAULT_DATA_LENGTH */
    public static final int f1555x1ca9a2c6 = 8228;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_LOCAL_SPT_FEAT */
    public static final int f1556xf7bcd4e5 = 8196;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_RANDOM_ADDR */
    public static final int f1557x5ae929b6 = 8197;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_RF_COMPENS_POWER */
    public static final int f1558x5243ce0d = 8269;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_SCAN_ENABLE */
    public static final int f1559x9f80354e = 8204;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_SCAN_PARAMS */
    public static final int f1560xb1960951 = 8203;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BLE_WRITE_SCAN_RSP_DATA */
    public static final int f1561x2a643cc5 = 8201;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_BRCM_SET_ACL_PRIORITY */
    public static final int f1562xc0ee75e8 = 64599;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CHANGE_CONN_LINK_KEY */
    public static final int f1563xc6885f1 = 1045;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CHANGE_CONN_PACKET_TYPE */
    public static final int f1564xfb3873ba = 1039;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CHANGE_LOCAL_NAME */
    public static final int f1565x4ef953bb = 3091;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CONTROLLER_A2DP_OPCODE */
    public static final int f1566x9edad4a0 = 64861;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CONTROLLER_DEBUG_INFO */
    public static final int f1567xe333360a = 64859;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CREATE_CONNECTION */
    public static final int f1568xaccd0ece = 1029;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CREATE_CONNECTION_CANCEL */
    public static final int f1569xc1b66d0b = 1032;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CREATE_LOGICAL_LINK */
    public static final int f1570x19aa2a80 = 1080;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CREATE_NEW_UNIT_KEY */
    public static final int f1571x526c5cd3 = 3083;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_CREATE_PHYSICAL_LINK */
    public static final int f1572x86d81472 = 1077;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_DELETE_RESERVED_LT_ADDR */
    public static final int f1573x53b09152 = 3189;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_DELETE_STORED_LINK_KEY */
    public static final int f1574x63a46655 = 3090;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_DISCONNECT = 1030;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_DISCONNECT_LOGICAL_LINK */
    public static final int f1575x509c5060 = 1082;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_DISCONNECT_PHYSICAL_LINK */
    public static final int f1576x2e2aaa92 = 1079;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ENABLE_AMP_RCVR_REPORTS */
    public static final int f1577x7c50b511 = 6151;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ENABLE_DEV_UNDER_TEST_MODE */
    public static final int f1578x5e579136 = 6147;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ENHANCED_FLUSH */
    public static final int f1579x373c1b2e = 3167;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ENH_ACCEPT_ESCO_CONNECTION */
    public static final int f1580xb18d56bf = 1086;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ENH_SETUP_ESCO_CONNECTION */
    public static final int f1581xc7ffdd0e = 1085;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_EXIT_PARK_MODE */
    public static final int f1582x81b1b14a = 2054;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_EXIT_PERIODIC_INQUIRY_MODE */
    public static final int f1583xa600a1d1 = 1028;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_EXIT_SNIFF_MODE */
    public static final int f1584x3fc47282 = 2052;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_FLOW_SPECIFICATION */
    public static final int f1585x8ff0fc65 = 2064;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_FLOW_SPEC_MODIFY */
    public static final int f1586xe687fdc0 = 1084;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_FLUSH = 3080;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_GET_LINK_QUALITY */
    public static final int f1587xe35f5996 = 5123;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_GET_MWS_TRANSPORT_CFG */
    public static final int f1588xafd745bc = 5132;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_GET_MWS_TRANS_LAYER_CFG */
    public static final int f1589xa1a8ff6d = 3084;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_HOLD_MODE = 2049;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_HOST_BUFFER_SIZE */
    public static final int f1590xcb61da1c = 3123;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_HOST_NUM_PACKETS_DONE */
    public static final int f1591xbbcb9eb3 = 3125;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_INQUIRY = 1025;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_INQUIRY_CANCEL */
    public static final int f1592x12359265 = 1026;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_IO_CAPABILITY_REQUEST_REPLY */
    public static final int f1593xc190399 = 1067;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_IO_CAP_REQ_NEG_REPLY */
    public static final int f1594xe6cf4c27 = 1076;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_LINK_KEY_REQUEST_NEG_REPLY */
    public static final int f1595x10db3ab9 = 1036;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_LINK_KEY_REQUEST_REPLY */
    public static final int f1596x6f22ed48 = 1035;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_LOGICAL_LINK_CANCEL */
    public static final int f1597x847ef496 = 1083;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_MASTER_LINK_KEY */
    public static final int f1598xbcc3f5c4 = 1047;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_PARK_MODE = 2053;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_PERIODIC_INQUIRY_MODE */
    public static final int f1599xb179e6ac = 1027;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_PIN_CODE_REQUEST_NEG_REPLY */
    public static final int f1600x66f27376 = 1038;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_PIN_CODE_REQUEST_REPLY */
    public static final int f1601x76860c85 = 1037;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_QOS_SETUP = 2055;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_AFH_ASSESSMENT_MODE */
    public static final int f1602xba1b2bee = 3144;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_AFH_CH_MAP */
    public static final int f1603xa3307b4 = 5126;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f1604x81167cce = 3195;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_AUTHENTICATION_ENABLE */
    public static final int f1605x717d3514 = 3103;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_AUTOMATIC_FLUSH_TIMEOUT */
    public static final int f1606x1b6c0d1c = 3111;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_BD_ADDR = 4105;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_BE_FLUSH_TOUT */
    public static final int f1607x4fccc23b = 3177;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_BLE_HOST_SUPPORT */
    public static final int f1608x60661272 = 3180;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_BUFFER_SIZE */
    public static final int f1609x4eb9c90a = 4101;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_CLASS_OF_DEVICE */
    public static final int f1610xe7acc701 = 3107;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_CLOCK = 5127;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_CONN_ACCEPT_TOUT */
    public static final int f1611xac839474 = 3093;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_COUNTRY_CODE */
    public static final int f1612x11e0a9ec = 4103;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_CURRENT_IAC_LAP */
    public static final int f1613xc36c3beb = 3129;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_DATA_BLOCK_SIZE */
    public static final int f1614x32f0b3b2 = 4106;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_DEF_POLICY_SETTINGS */
    public static final int f1615xbca2fea0 = 2062;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_ENCRYPTION_MODE */
    public static final int f1616x4292fc49 = 3105;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_ENCR_KEY_SIZE */
    public static final int f1617xb5b57d72 = 5128;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_ENHANCED_TX_PWR_LEVEL */
    public static final int f1618x7426f368 = 3176;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_ERRONEOUS_DATA_RPT */
    public static final int f1619xde26c64a = 3162;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_EXTENDED_INQUIRY_LENGTH */
    public static final int f1620xe5a1ddae = 3200;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_EXTENDED_PAGE_TIMEOUT */
    public static final int f1621xd68288c1 = 3198;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_EXT_INQ_RESPONSE */
    public static final int f1622x321d9ea8 = 3153;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_FAILED_CONTACT_COUNTER */
    public static final int f1623xc01dddf1 = 5121;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_FLOW_CONTROL_MODE */
    public static final int f1624x5c95a3c0 = 3174;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_HOLD_MODE_ACTIVITY */
    public static final int f1625x7b3a0b01 = 3115;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_INQSCAN_TYPE */
    public static final int f1626x435ae646 = 3138;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_INQUIRYSCAN_CFG */
    public static final int f1627xc8c83d93 = 3101;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_INQUIRY_MODE */
    public static final int f1628x7136ba11 = 3140;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_INQ_TX_POWER_LEVEL */
    public static final int f1629x86b11778 = 3160;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LINK_SUPER_TOUT */
    public static final int f1630x1381258d = 3126;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LMP_HANDLE */
    public static final int f1631xde610fce = 1056;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_AMP_ASSOC */
    public static final int f1632xc60a5d10 = 5130;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_AMP_INFO */
    public static final int f1633x7a03e433 = 5129;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_EXT_FEATURES */
    public static final int f1634x8a2b1985 = 4100;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_FEATURES */
    public static final int f1635xc009ba07 = 4099;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_NAME */
    public static final int f1636x95f9cd55 = 3092;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_OOB_DATA */
    public static final int f1637x7a3a8131 = 3159;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_OOB_EXTENDED_DATA */
    public static final int f1638x1bcfcd49 = 3197;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_SUPPORTED_CMDS */
    public static final int f1639x3101d4 = 4098;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_SUPPORTED_CODECS */
    public static final int f1640xb813dfb8 = 4107;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCAL_VERSION_INFO */
    public static final int f1641x839c109f = 4097;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOCATION_DATA */
    public static final int f1642xd99abfde = 3172;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOGICAL_LINK_ACCEPT_TIMEOUT */
    public static final int f1643x46895f03 = 3169;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_LOOPBACK_MODE */
    public static final int f1644xf8e285c1 = 6145;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_NUM_BCAST_REXMITS */
    public static final int f1645xa01fdf33 = 3113;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_NUM_SUPPORTED_IAC */
    public static final int f1646x1ad06ecb = 3128;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_PAGESCAN_CFG */
    public static final int f1647x13c23be7 = 3099;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_PAGESCAN_MODE */
    public static final int f1648x6489ee60 = 3133;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_PAGESCAN_PERIOD_MODE */
    public static final int f1649x27065604 = 3131;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_PAGESCAN_TYPE */
    public static final int f1650x648d43f7 = 3142;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_PAGE_TOUT */
    public static final int f1651x6a44acf4 = 3095;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_PIN_TYPE */
    public static final int f1652x8eb07b9a = 3081;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_POLICY_SETTINGS */
    public static final int f1653x4f86cffa = 2060;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_RMT_CLOCK_OFFSET */
    public static final int f1654xd2eb7ce0 = 1055;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_RMT_EXT_FEATURES */
    public static final int f1655x5c12db97 = 1052;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_RMT_FEATURES */
    public static final int f1656xb8395519 = 1051;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_RMT_VERSION_INFO */
    public static final int f1657x5583d2b1 = 1053;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_RSSI = 5125;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_SCAN_ENABLE */
    public static final int f1658xa6ae19ef = 3097;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_SCO_FLOW_CTRL_ENABLE */
    public static final int f1659x87925f5c = 3118;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_SECURE_CONNS_SUPPORT */
    public static final int f1660x7a85de25 = 3193;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_SIMPLE_PAIRING_MODE */
    public static final int f1661x4a5cdcb1 = 3157;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_STORED_LINK_KEY */
    public static final int f1662xe8b7c100 = 3085;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_SYNC_TRAIN_PARAM */
    public static final int f1663x84f1e988 = 3191;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_TRANSMIT_POWER_LEVEL */
    public static final int f1664x6d646f51 = 3117;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_READ_VOICE_SETTINGS */
    public static final int f1665xa37aa006 = 3109;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_RECEIVE_CLB = 1090;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_RECEIVE_SYNC_TRAIN */
    public static final int f1666x9644df13 = 1092;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_REFRESH_ENCRYPTION_KEY */
    public static final int f1667x265b54da = 3155;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_REJECT_CONNECTION_REQUEST */
    public static final int f1668xd1bcf27b = 1034;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_REJECT_ESCO_CONNECTION */
    public static final int f1669x39f7fd56 = 1066;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_REM_OOB_DATA_REQ_NEG_REPLY */
    public static final int f1670x8482d1a = 1075;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_REM_OOB_DATA_REQ_REPLY */
    public static final int f1671x3ee23829 = 1072;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_REM_OOB_EXTENDED_DATA_REQ_REPLY */
    public static final int f1672xf19dce25 = 1093;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_RESET = 3075;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_RESET_FAILED_CONTACT_COUNTER */
    public static final int f1673x242126be = 5122;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_RMT_NAME_REQUEST */
    public static final int f1674x78570f54 = 1049;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_RMT_NAME_REQUEST_CANCEL */
    public static final int f1675x26c9e9c5 = 1050;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_ROLE_DISCOVERY */
    public static final int f1676x673e4f5a = 2057;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SEND_KEYPRESS_NOTIF */
    public static final int f1677xdea90f79 = 3168;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SETUP_ESCO_CONNECTION */
    public static final int f1678xb076270e = 1064;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_AFH_CHANNELS */
    public static final int f1679xe463b23c = 3135;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_CLB = 1089;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_CONN_ENCRYPTION */
    public static final int f1680x19732206 = 1043;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_EVENT_FILTER */
    public static final int f1681x4af2234d = 3077;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_EVENT_MASK */
    public static final int f1682x67fb5401 = 3073;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_EVENT_MASK_PAGE_2 */
    public static final int f1683x2fe85480 = 3171;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_EXTERNAL_FRAME_CONFIGURATION */
    public static final int f1684xcd043160 = 3183;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_HC_TO_HOST_FLOW_CTRL */
    public static final int f1685x4386d375 = 3121;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_MWS_CHANNEL_PARAMETERS */
    public static final int f1686x39e2694c = 3182;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_MWS_PATTERN_CONFIGURATION */
    public static final int f1687x1c0df201 = 3187;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_MWS_SCAN_FREQUENCY_TABLE */
    public static final int f1688x17361a2f = 3186;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_MWS_SIGNALING */
    public static final int f1689xa7affed4 = 3184;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_MWS_TRANSPORT_LAYER */
    public static final int f1690x91eccfb5 = 3185;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_RESERVED_LT_ADDR */
    public static final int f1691xeba778a1 = 3188;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SET_TRIGGERED_CLK_CAPTURE */
    public static final int f1692x51b80471 = 5133;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SHORT_RANGE_MODE */
    public static final int f1693xfce124db = 3179;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SNIFF_MODE = 2051;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SNIFF_SUB_RATE */
    public static final int f1694x18377483 = 2065;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_START_SYNC_TRAIN */
    public static final int f1695xbe914f4 = 1091;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_SWITCH_ROLE = 2059;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_TRUNCATED_PAGE */
    public static final int f1696x979f98e3 = 1087;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_TRUNCATED_PAGE_CANCEL */
    public static final int f1697x4e4d5156 = 1088;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_UNKNOWN = 1048575;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_USER_CONF_REQUEST_REPLY */
    public static final int f1698x3798e320 = 1068;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_USER_CONF_VALUE_NEG_REPLY */
    public static final int f1699x3fde7e73 = 1069;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_USER_PASSKEY_REQ_NEG_REPLY */
    public static final int f1700x6d723328 = 1071;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_USER_PASSKEY_REQ_REPLY */
    public static final int f1701x652d7537 = 1070;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_AFH_ASSESSMENT_MODE */
    public static final int f1702xd7cdb0d1 = 3145;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f1703x6a8ca90b = 3196;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_AUTHENTICATION_ENABLE */
    public static final int f1704xeca20d37 = 3104;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_AUTOMATIC_FLUSH_TIMEOUT */
    public static final int f1705x60bb687f = 3112;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_BE_FLUSH_TOUT */
    public static final int f1706xb3716d5e = 3178;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_BLE_HOST_SUPPORT */
    public static final int f1707xf80d706f = 3181;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_CLASS_OF_DEVICE */
    public static final int f1708xf4d33564 = 3108;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_CLB_DATA */
    public static final int f1709x2fc1ca03 = 3190;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_CONN_ACCEPT_TOUT */
    public static final int f1710x442af271 = 3094;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_CURRENT_IAC_LAP */
    public static final int f1711xd092aa4e = 3130;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_DEF_POLICY_SETTINGS */
    public static final int f1712xda558383 = 2063;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_ENCRYPTION_MODE */
    public static final int f1713x4fb96aac = 3106;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_ERRONEOUS_DATA_RPT */
    public static final int f1714x296e9907 = 3163;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_EXTENDED_INQUIRY_LENGTH */
    public static final int f1715x2af13911 = 3201;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_EXTENDED_PAGE_TIMEOUT */
    public static final int f1716x51a760e4 = 3199;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_EXT_INQ_RESPONSE */
    public static final int f1717xc9c4fca5 = 3154;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_FLOW_CONTROL_MODE */
    public static final int f1718xb9da0563 = 3175;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_HOLD_MODE_ACTIVITY */
    public static final int f1719xc681ddbe = 3116;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_INQSCAN_TYPE */
    public static final int f1720xcab2cac3 = 3139;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_INQUIRYSCAN_CFG */
    public static final int f1721xd5eeabf6 = 3102;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_INQUIRY_MODE */
    public static final int f1722xf88e9e8e = 3141;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_INQ_TX_POWER_LEVEL */
    public static final int f1723xd1f8ea35 = 3161;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_LINK_SUPER_TOUT */
    public static final int f1724x20a793f0 = 3127;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_LOCATION_DATA */
    public static final int f1725x3d3f6b01 = 3173;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_LOGICAL_LINK_ACCEPT_TIMEOUT */
    public static final int f1726x542650e6 = 3170;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_LOOPBACK_MODE */
    public static final int f1727x5c8730e4 = 6146;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_NUM_BCAST_REXMITS */
    public static final int f1728xfd6440d6 = 3114;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_PAGESCAN_CFG */
    public static final int f1729x9b1a2064 = 3100;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_PAGESCAN_MODE */
    public static final int f1730xc82e9983 = 3134;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_PAGESCAN_PERIOD_MODE */
    public static final int f1731xbfa46d81 = 3132;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_PAGESCAN_TYPE */
    public static final int f1732xc831ef1a = 3143;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_PAGE_TOUT */
    public static final int f1733xb6ea6197 = 3096;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_PIN_TYPE */
    public static final int f1734xec002697 = 3082;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_POLICY_SETTINGS */
    public static final int f1735x5cad3e5d = 2061;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_REMOTE_AMP_ASSOC */
    public static final int f1736xe461c8b4 = 5131;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SCAN_ENABLE */
    public static final int f1737x60b931d2 = 3098;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SCO_FLOW_CTRL_ENABLE */
    public static final int f1738x203076d9 = 3119;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SECURE_CONNS_SUPPORT */
    public static final int f1739x1323f5a2 = 3194;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SECURE_CONN_TEST_MODE */
    public static final int f1740x556429b2 = 6154;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SIMPLE_PAIRING_MODE */
    public static final int f1741x680f6194 = 3158;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SIMP_PAIR_DEBUG_MODE */
    public static final int f1742xb5fa3341 = 6148;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_STORED_LINK_KEY */
    public static final int f1743xf5de2f63 = 3089;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_SYNC_TRAIN_PARAM */
    public static final int f1744x1c994785 = 3192;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_CMD__CMD_WRITE_VOICE_SETTINGS */
    public static final int f1745xb46b5943 = 3110;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_AMP_RECEIVER_RPT */
    public static final int f1746x7164deab = 75;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_AMP_STATUS_CHANGE */
    public static final int f1747x43b60a18 = 77;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_AMP_TEST_END */
    public static final int f1748x6ea56133 = 74;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_AMP_TEST_START */
    public static final int f1749x5b99e2ba = 73;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_AUTHED_PAYLOAD_TIMEOUT */
    public static final int f1750xa76f9462 = 87;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_AUTHENTICATION_COMP */
    public static final int f1751x6fdca3cc = 6;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_BLE_META = 62;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CHANGE_CONN_LINK_KEY */
    public static final int f1752x81cb1948 = 9;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CHANNEL_SELECTED */
    public static final int f1753x7dfecae1 = 65;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_COMMAND_COMPLETE */
    public static final int f1754x65d9b837 = 14;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_COMMAND_STATUS */
    public static final int f1755x9d650270 = 15;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CONNECTION_COMP */
    public static final int f1756x5490ea26 = 3;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CONNECTION_REQUEST */
    public static final int f1757x1488cff8 = 4;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CONNLESS_SLAVE_BROADCAST_CHNL_MAP_CHANGE */
    public static final int f1758xd61451b9 = 85;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CONNLESS_SLAVE_BROADCAST_RCVD */
    public static final int f1759x157d4215 = 81;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CONNLESS_SLAVE_BROADCAST_TIMEOUT */
    public static final int f1760x2ef9680b = 82;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_CONN_PKT_TYPE_CHANGE */
    public static final int f1761xb995df26 = 29;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_DATA_BUF_OVERFLOW */
    public static final int f1762xced623b9 = 26;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_DISCONNECTION_COMP */
    public static final int f1763x9b4f5b8c = 5;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_DISC_LOGICAL_LINK_COMP */
    public static final int f1764x8aec441e = 70;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_DISC_PHYSICAL_LINK_COMP */
    public static final int f1765x2aa19a0c = 66;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_ENCRYPTION_CHANGE */
    public static final int f1766xe4a43cc2 = 8;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_ENCRYPTION_KEY_REFRESH_COMP */
    public static final int f1767x1e83eaa5 = 48;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_ENHANCED_FLUSH_COMPLETE */
    public static final int f1768x1b0d0473 = 57;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_ESCO_CONNECTION_CHANGED */
    public static final int f1769x5f72e54e = 45;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_ESCO_CONNECTION_COMP */
    public static final int f1770x79e3ba95 = 44;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_EXTENDED_INQUIRY_RESULT */
    public static final int f1771x94ab45d1 = 47;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_FLOW_SPECIFICATION_COMP */
    public static final int f1772xd75873f2 = 33;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_FLOW_SPEC_MODIFY_COMP */
    public static final int f1773x53dad1b7 = 71;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_FLUSH_OCCURRED */
    public static final int f1774x4b32cf0a = 17;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_HARDWARE_ERROR */
    public static final int f1775xbf7087bb = 16;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_INQUIRY_COMP */
    public static final int f1776xae83f011 = 1;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_INQUIRY_RESULT */
    public static final int f1777x3656cc9f = 2;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_INQUIRY_RES_NOTIFICATION */
    public static final int f1778x5642c44c = 86;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_INQUIRY_RSSI_RESULT */
    public static final int f1779xe67161c3 = 34;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_IO_CAPABILITY_REQUEST */
    public static final int f1780x935c4fd7 = 49;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_IO_CAPABILITY_RESPONSE */
    public static final int f1781xdb555fd9 = 50;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_KEYPRESS_NOTIFY */
    public static final int f1782xe00814fa = 60;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_LINK_KEY_NOTIFICATION */
    public static final int f1783x485e4666 = 24;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_LINK_KEY_REQUEST */
    public static final int f1784xce3ee814 = 23;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_LINK_SUPER_TOUT_CHANGED */
    public static final int f1785x186db8ce = 56;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_LOGICAL_LINK_COMP */
    public static final int f1786x9d6a5434 = 69;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_LOOPBACK_COMMAND */
    public static final int f1787xb6c790e1 = 25;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_MASTER_LINK_KEY_COMP */
    public static final int f1788xa19ff801 = 10;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_MAX_SLOTS_CHANGED */
    public static final int f1789x23a81f85 = 27;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_MODE_CHANGE */
    public static final int f1790x6ed1bb22 = 20;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_NUM_COMPL_DATA_BLOCKS */
    public static final int f1791x86a726d6 = 72;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_NUM_COMPL_DATA_PKTS */
    public static final int f1792x1b11fb0a = 19;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_PAGE_SCAN_MODE_CHANGE */
    public static final int f1793x2a786890 = 31;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_PAGE_SCAN_REP_MODE_CHNG */
    public static final int f1794x39b3a27c = 32;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_PHYSICAL_LINK_COMP */
    public static final int f1795x67e58cb6 = 64;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_PHY_LINK_LOSS_EARLY_WARNING */
    public static final int f1796x3b36ef41 = 67;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_PHY_LINK_RECOVERY */
    public static final int f1797xe12d78b2 = 68;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_PIN_CODE_REQUEST */
    public static final int f1798xe9457911 = 22;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_QOS_SETUP_COMP */
    public static final int f1799xfd185325 = 13;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_QOS_VIOLATION */
    public static final int f1800x1766f11 = 30;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_READ_CLOCK_OFF_COMP */
    public static final int f1801xe1563e4f = 28;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_READ_RMT_EXT_FEATURES_COMP */
    public static final int f1802xadaaa0ce = 35;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_READ_RMT_FEATURES_COMP */
    public static final int f1803x66626c0c = 11;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_READ_RMT_VERSION_COMP */
    public static final int f1804x8c70f49b = 12;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_REMOTE_OOB_DATA_REQUEST */
    public static final int f1805xe13258c6 = 53;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_RETURN_LINK_KEYS */
    public static final int f1806x393d0334 = 21;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_RMT_HOST_SUP_FEAT_NOTIFY */
    public static final int f1807x60bc273e = 61;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_RMT_NAME_REQUEST_COMP */
    public static final int f1808x8a5b9ca3 = 7;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_ROLE_CHANGE */
    public static final int f1809xf96cb0ef = 18;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SAM_STATUS_CHANGE */
    public static final int f1810x4f3f00f3 = 88;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SET_TRIGGERED_CLOCK_CAPTURE */
    public static final int f1811x101fc226 = 78;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SHORT_RANGE_MODE_COMPLETE */
    public static final int f1812x54025f26 = 76;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SIMPLE_PAIRING_COMPLETE */
    public static final int f1813x88657033 = 54;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SLAVE_PAGE_RES_TIMEOUT */
    public static final int f1814x3b438d44 = 84;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SNIFF_SUB_RATE */
    public static final int f1815x2285151a = 46;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SYNC_TRAIN_CMPL */
    public static final int f1816x23724e57 = 79;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_SYNC_TRAIN_RCVD */
    public static final int f1817x2378fb10 = 80;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_TRUNCATED_PAGE_CMPL */
    public static final int f1818xa3f7fb8b = 83;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_UNKNOWN = 4095;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_USER_CONFIRMATION_REQUEST */
    public static final int f1819x7f0b0f2f = 51;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_USER_PASSKEY_NOTIFY */
    public static final int f1820x7fc64664 = 59;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__HCI_EVENT__EVT_USER_PASSKEY_REQUEST */
    public static final int f1821x3b6626b4 = 52;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ADVERTISING_TIMEOUT */
    public static final int f1822xeaf18dc6 = 60;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_AUTH_FAILURE */
    public static final int f1823x6a4f4313 = 5;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f1824xb1ed53cb = 46;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CLB_DATA_TOO_BIG */
    public static final int f1825xc6229286 = 67;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CLB_NOT_ENABLED */
    public static final int f1826x2f1e860f = 66;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_COMMAND_DISALLOWED */
    public static final int f1827xbee02e = 12;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CONNECTION_EXISTS */
    public static final int f1828x30bbb9d = 11;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CONNECTION_TOUT */
    public static final int f1829xf5e811db = 8;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f1830xadcc4745 = 22;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f1831xb67fe368 = 62;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f1832xf4709c8b = 61;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_CONTROLLER_BUSY */
    public static final int f1833xb26d5b7c = 58;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f1834xbca92757 = 42;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f1835x878c78c = 37;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_HOST_BUSY_PAIRING */
    public static final int f1836x83b90659 = 56;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_HOST_REJECT_DEVICE */
    public static final int f1837xa080a1df = 15;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_HOST_REJECT_RESOURCES */
    public static final int f1838x616225dc = 13;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_HOST_REJECT_SECURITY */
    public static final int f1839x79553889 = 14;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_HOST_TIMEOUT */
    public static final int f1840x142d76ea = 16;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_HW_FAILURE */
    public static final int f1841x1cf16f7a = 3;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ILLEGAL_COMMAND */
    public static final int f1842x5b844202 = 1;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f1843xa8aa304e = 18;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f1844x8a6769e = 54;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_INSTANT_PASSED */
    public static final int f1845xd8ac85ee = 40;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_INSUFFCIENT_SECURITY */
    public static final int f1846x5a03f9e1 = 47;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_INVALID_LMP_PARAM */
    public static final int f1847xad8e6975 = 30;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_KEY_MISSING */
    public static final int f1848x394fce06 = 6;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f1849xe457ef0e = 36;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f1850x441ebbd3 = 34;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f1851xf657145e = 35;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f1852xf308f74b = 64;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f1853xb0c47124 = 65;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_MAC_CONNECTION_FAILED */
    public static final int f1854x1c26c06e = 63;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f1855x33da24e1 = 9;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_MAX_NUM_OF_SCOS */
    public static final int f1856x2d4b1608 = 10;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_MEMORY_FULL */
    public static final int f1857x164141cd = 7;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_NO_CONNECTION */
    public static final int f1858x119bbadc = 2;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f1859xb408422 = 68;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PAGE_TIMEOUT */
    public static final int f1860x9c50cd1 = 4;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f1861x8d9d23a5 = 24;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f1862x274c6f49 = 41;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f1863x4166c678 = 48;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PEER_LOW_RESOURCES */
    public static final int f1864xdc57e7dd = 20;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PEER_POWER_OFF */
    public static final int f1865x165d6998 = 21;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_PEER_USER */
    public static final int f1866x354c1748 = 19;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_QOS_NOT_SUPPORTED */
    public static final int f1867xb9f1bf8 = 39;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_QOS_REJECTED */
    public static final int f1868x59b390c8 = 45;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f1869x381614b3 = 44;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f1870xdce07c41 = 57;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_REPEATED_ATTEMPTS */
    public static final int f1871xcaa3d92b = 23;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f1872x48e0acbb = 52;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f1873xe0738316 = 33;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ROLE_SWITCH_FAILED */
    public static final int f1874x38621cdf = 53;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_ROLE_SWITCH_PENDING */
    public static final int f1875xebf61515 = 50;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_SCO_AIR_MODE */
    public static final int f1876x99f74918 = 29;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f1877x11eaed38 = 28;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_SCO_OFFSET_REJECTED */
    public static final int f1878xb30712a = 27;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f1879x244b171e = 55;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_SUCCESS */
    public static final int f1880x481d2a3 = 0;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f1881x1b3922da = 59;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNDEFINED_0X2_B */
    public static final int f1882xd921a73e = 43;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNDEFINED_0X31 */
    public static final int f1883x40cf8975 = 49;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNDEFINED_0X33 */
    public static final int f1884x40cf8977 = 51;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNIT_KEY_USED */
    public static final int f1885x50efd4d8 = 38;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNKNOWN */
    public static final int f1886x62d23eea = 4095;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNKNOWN_LMP_PDU */
    public static final int f1887x5323d73c = 25;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNSPECIFIED */
    public static final int f1888x5f05c5d7 = 31;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f1889x29fc991c = 32;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f1890x483ff3a7 = 26;

    /* renamed from: BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__REASON_CODE__STATUS_UNSUPPORTED_VALUE */
    public static final int f1891xe3eaa567 = 17;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__TYPE__LINK_TYPE_ACL = 1;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__TYPE__LINK_TYPE_ESCO = 2;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__TYPE__LINK_TYPE_SCO = 0;
    public static final int BLUETOOTH_LINK_LAYER_CONNECTION_EVENT__TYPE__LINK_TYPE_UNKNOWN = 4095;
    public static final int BLUETOOTH_QUALITY_REPORT_REPORTED = 161;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_2DH1 */
    public static final int f1892x27830b94 = 23;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_2DH3 */
    public static final int f1893x27830b96 = 24;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_2DH5 */
    public static final int f1894x27830b98 = 25;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_2EV3 */
    public static final int f1895x27831109 = 12;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_2EV5 */
    public static final int f1896x2783110b = 13;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_3DH1 */
    public static final int f1897x27837ff3 = 26;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_3DH3 */
    public static final int f1898x27837ff5 = 27;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_3DH5 */
    public static final int f1899x27837ff7 = 28;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_3EV3 */
    public static final int f1900x27838568 = 14;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_3EV5 */
    public static final int f1901x2783856a = 15;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_AUX1 */
    public static final int f1902x278a1ee6 = 22;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DH1 */
    public static final int f1903x1a0cc034 = 17;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DH3 */
    public static final int f1904x1a0cc036 = 19;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DH5 */
    public static final int f1905x1a0cc038 = 21;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DM1 */
    public static final int f1906x1a0cc0cf = 16;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DM3 */
    public static final int f1907x1a0cc0d1 = 18;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DM5 */
    public static final int f1908x1a0cc0d3 = 20;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_DV */
    public static final int f1909x4b29b3ab = 8;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_EV3 */
    public static final int f1910x1a0cc5a9 = 9;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_EV4 */
    public static final int f1911x1a0cc5aa = 10;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_EV5 */
    public static final int f1912x1a0cc5ab = 11;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_FHS */
    public static final int f1913x1a0cc7d8 = 4;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_HV1 */
    public static final int f1914x1a0cd0ea = 5;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_HV2 */
    public static final int f1915x1a0cd0eb = 6;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_HV3 */
    public static final int f1916x1a0cd0ec = 7;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_ID */
    public static final int f1917x4b29b434 = 1;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_NULL */
    public static final int f1918x27900660 = 2;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_POLL */
    public static final int f1919x2790d898 = 3;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__PACKET_TYPES__BQR_PACKET_TYPE_UNKNOWN */
    public static final int f1920x57a394b1 = 0;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__QUALITY_REPORT_ID__BQR_ID_A2DP_AUDIO_CHOPPY */
    public static final int f1921x132f166b = 3;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__QUALITY_REPORT_ID__BQR_ID_APPROACH_LSTO */
    public static final int f1922xd5fae88a = 2;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__QUALITY_REPORT_ID__BQR_ID_MONITOR_MODE */
    public static final int f1923x7faf57 = 1;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__QUALITY_REPORT_ID__BQR_ID_SCO_VOICE_CHOPPY */
    public static final int f1924x1363c3eb = 4;

    /* renamed from: BLUETOOTH_QUALITY_REPORT_REPORTED__QUALITY_REPORT_ID__BQR_ID_UNKNOWN */
    public static final int f1925x4327b8fb = 0;
    public static final int BLUETOOTH_REMOTE_VERSION_INFO_REPORTED = 163;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ADVERTISING_TIMEOUT */
    public static final int f1926xb780b8f1 = 60;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_AUTH_FAILURE */
    public static final int f1927x20298248 = 5;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CHAN_CLASSIF_NOT_SUPPORTED */
    public static final int f1928xbbda5240 = 46;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CLB_DATA_TOO_BIG */
    public static final int f1929x79b02f3b = 67;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CLB_NOT_ENABLED */
    public static final int f1930x8fbffeba = 66;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_COMMAND_DISALLOWED */
    public static final int f1931x75823a3 = 12;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CONNECTION_EXISTS */
    public static final int f1932xc131b588 = 11;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CONNECTION_TOUT */
    public static final int f1933x56898a86 = 8;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CONN_CAUSE_LOCAL_HOST */
    public static final int f1934x933d53b0 = 22;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CONN_FAILED_ESTABLISHMENT */
    public static final int f1935x2a6ec253 = 62;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CONN_TOUT_DUE_TO_MIC_FAILURE */
    public static final int f1936x3717d1c0 = 61;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_CONTROLLER_BUSY */
    public static final int f1937x130ed427 = 58;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_DIFF_TRANSACTION_COLLISION */
    public static final int f1938xc69625cc = 42;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ENCRY_MODE_NOT_ACCEPTABLE */
    public static final int f1939x7c67a677 = 37;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_HOST_BUSY_PAIRING */
    public static final int f1940x41df0044 = 56;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_DEVICE */
    public static final int f1941xa719e554 = 15;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_RESOURCES */
    public static final int f1942x46d33247 = 13;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_HOST_REJECT_SECURITY */
    public static final int f1943x3eab72be = 14;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_HOST_TIMEOUT */
    public static final int f1944xca07b61f = 16;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_HW_FAILURE */
    public static final int f1945xff4bf7ef = 3;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ILLEGAL_COMMAND */
    public static final int f1946xbc25baad = 1;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ILLEGAL_PARAMETER_FMT */
    public static final int f1947x8e1b3cb9 = 18;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_INQ_RSP_DATA_TOO_LARGE */
    public static final int f1948xd156f793 = 54;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_INSTANT_PASSED */
    public static final int f1949x80f3cbe3 = 40;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_INSUFFCIENT_SECURITY */
    public static final int f1950x1f5a3416 = 47;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_INVALID_LMP_PARAM */
    public static final int f1951x6bb46360 = 30;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_KEY_MISSING */
    public static final int f1952xa2465431 = 6;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_LMP_PDU_NOT_ALLOWED */
    public static final int f1953xb0e71a39 = 36;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_LMP_RESPONSE_TIMEOUT */
    public static final int f1954x974f608 = 34;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_LMP_STATUS_TRANS_COLLISION */
    public static final int f1955x4412d3 = 35;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_LT_ADDR_ALREADY_IN_USE */
    public static final int f1956xbbb97840 = 64;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_LT_ADDR_NOT_ALLOCATED */
    public static final int f1957x96357d8f = 65;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_MAC_CONNECTION_FAILED */
    public static final int f1958x197ccd9 = 63;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_MAX_NUM_OF_CONNECTIONS */
    public static final int f1959xfc8aa5d6 = 9;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_MAX_NUM_OF_SCOS */
    public static final int f1960x8dec8eb3 = 10;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_MEMORY_FULL */
    public static final int f1961x7f37c7f8 = 7;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_NO_CONNECTION */
    public static final int f1962x17096247 = 2;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_OPERATION_CANCELED_BY_HOST */
    public static final int f1963x152d8297 = 68;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PAGE_TIMEOUT */
    public static final int f1964xbf9f4c06 = 4;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PAIRING_NOT_ALLOWED */
    public static final int f1965x5a2c4ed0 = 24;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED */
    public static final int f1966xaade474 = 41;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PARAM_OUT_OF_RANGE */
    public static final int f1967x480009ed = 48;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PEER_LOW_RESOURCES */
    public static final int f1968xe2f12b52 = 20;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PEER_POWER_OFF */
    public static final int f1969xbea4af8d = 21;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_PEER_USER */
    public static final int f1970xa7f42c33 = 19;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_QOS_NOT_SUPPORTED */
    public static final int f1971xc9c515e3 = 39;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_QOS_REJECTED */
    public static final int f1972xf8dcffd = 45;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_QOS_UNACCEPTABLE_PARAM */
    public static final int f1973xc695a8 = 44;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_REJ_NO_SUITABLE_CHANNEL */
    public static final int f1974x2a4019ec = 57;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_REPEATED_ATTEMPTS */
    public static final int f1975x88c9d316 = 23;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_RESERVED_SLOT_VIOLATION */
    public static final int f1976x96404a66 = 52;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ROLE_CHANGE_NOT_ALLOWED */
    public static final int f1977x2dd320c1 = 33;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ROLE_SWITCH_FAILED */
    public static final int f1978x3efb6054 = 53;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_ROLE_SWITCH_PENDING */
    public static final int f1979xb8854040 = 50;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_SCO_AIR_MODE */
    public static final int f1980x4fd1884d = 29;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_SCO_INTERVAL_REJECTED */
    public static final int f1981xf75bf9a3 = 28;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_SCO_OFFSET_REJECTED */
    public static final int f1982xd7bf9c55 = 27;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_SIMPLE_PAIRING_NOT_SUPPORTED */
    public static final int f1983x66f24c53 = 55;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_SUCCESS */
    public static final int f1984xf970264e = 0;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNACCEPT_CONN_INTERVAL */
    public static final int f1985xe3e9a3cf = 59;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X2_B */
    public static final int f1986x39c31fe9 = 43;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X31 */
    public static final int f1987xe916cf6a = 49;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNDEFINED_0X33 */
    public static final int f1988xe916cf6c = 51;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNIT_KEY_USED */
    public static final int f1989x565d7c43 = 38;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNKNOWN */
    public static final int f1990x57c09295 = 4095;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNKNOWN_LMP_PDU */
    public static final int f1991xb3c54fe7 = 25;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNSPECIFIED */
    public static final int f1992xc7fc4c02 = 31;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_LMP_FEATURE */
    public static final int f1993x775c36c7 = 32;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_REM_FEATURE */
    public static final int f1994x959f9152 = 26;

    /* renamed from: BLUETOOTH_REMOTE_VERSION_INFO_REPORTED__HCI_STATUS__STATUS_UNSUPPORTED_VALUE */
    public static final int f1995xa2109f52 = 17;
    public static final int BLUETOOTH_SCO_CONNECTION_STATE_CHANGED = 127;
    public static final int BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__CODEC__SCO_CODEC_CVSD = 1;
    public static final int BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__CODEC__SCO_CODEC_MSBC = 2;
    public static final int BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__CODEC__SCO_CODEC_UNKNOWN = 0;

    /* renamed from: BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_CONNECTED */
    public static final int f1996x5de2b0f5 = 2;

    /* renamed from: BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_CONNECTING */
    public static final int f1997x5e737e2c = 1;

    /* renamed from: BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_DISCONNECTED */
    public static final int f1998xca969caf = 0;

    /* renamed from: BLUETOOTH_SCO_CONNECTION_STATE_CHANGED__STATE__CONNECTION_STATE_DISCONNECTING */
    public static final int f1999x883d09b2 = 3;
    public static final int BLUETOOTH_SDP_ATTRIBUTE_REPORTED = 164;
    public static final int BLUETOOTH_SMP_PAIRING_EVENT_REPORTED = 167;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__DIRECTION__DIRECTION_INCOMING */
    public static final int f2000x8dfbc4dc = 2;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__DIRECTION__DIRECTION_OUTGOING */
    public static final int f2001x8c210022 = 1;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__DIRECTION__DIRECTION_UNKNOWN */
    public static final int f2002xa919ecb4 = 0;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_ENCRYPTION_INFON */
    public static final int f2003x42b7d042 = 6;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_IDENTITY_ADDR_INFO */
    public static final int f2004xe2d04d19 = 9;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_IDENTITY_INFO */
    public static final int f2005x98f709b1 = 8;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_MASTER_IDENTIFICATION */
    public static final int f2006x7541f20d = 7;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_CONFIRM */
    public static final int f2007x680c19ab = 3;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_DHKEY_CHECK */
    public static final int f2008x121cfc6f = 13;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_FAILED */
    public static final int f2009x8bd353d2 = 5;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_KEYPRESS_INFO */
    public static final int f2010xd8d61994 = 14;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_PUBLIC_KEY */
    public static final int f2011x5781947e = 12;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_RANDOM */
    public static final int f2012xa04fa458 = 4;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_REQUEST */
    public static final int f2013x70aa257a = 1;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_PAIRING_RESPONSE */
    public static final int f2014xa7c23e96 = 2;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_SECURITY_REQUEST */
    public static final int f2015x5d70730e = 11;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_SIGNING_INFO */
    public static final int f2016xa719f166 = 10;
    public static final int BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_COMMAND__CMD_UNKNOWN = 0;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_AUTH_REQ */
    public static final int f2017xbfdc8b0b = 3;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_CLASSIC_PAIRING_IN_PROGR */
    public static final int f2018xc4d28626 = 13;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_CONFIRM_VALUE */
    public static final int f2019x5d037fce = 4;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_DHKEY_CHK */
    public static final int f2020xd08d905e = 11;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_ENC_KEY_SIZE */
    public static final int f2021x94c9bf8a = 6;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_INVALID_CMD */
    public static final int f2022x6fe3fe8e = 7;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_INVALID_PARAMETERS */
    public static final int f2023x9ccce9f6 = 10;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_NUMERIC_COMPARISON */
    public static final int f2024x176c813f = 12;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_OOB */
    public static final int f2025xdf59de3e = 2;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_PAIR_NOT_SUPPORT */
    public static final int f2026xb6f739e2 = 5;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_PASSKEY_ENTRY */
    public static final int f2027xba20da3d = 1;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_REPEATED_ATTEMPTS */
    public static final int f2028xf50cfd47 = 9;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_RESERVED */
    public static final int f2029x5587d8cc = 0;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_UNSPECIFIED */
    public static final int f2030x47b32ef3 = 8;

    /* renamed from: BLUETOOTH_SMP_PAIRING_EVENT_REPORTED__SMP_FAIL_REASON__PAIRING_FAIL_REASON_XTRANS_DERIVE_NOT_ALLOW */
    public static final int f2031xaf9e8090 = 14;
    public static final int BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED = 171;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__IS_SERVER__SOCKET_ROLE_CONNECTION */
    public static final int f2032xa6c4a6b3 = 2;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__IS_SERVER__SOCKET_ROLE_LISTEN */
    public static final int f2033x2aab701c = 1;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__IS_SERVER__SOCKET_ROLE_UNKNOWN */
    public static final int f2034xeef9475 = 0;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__STATE__SOCKET_CONNECTION_STATE_CONNECTED */
    public static final int f2035x49389235 = 3;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__STATE__SOCKET_CONNECTION_STATE_CONNECTING */
    public static final int f2036xddd9c4ec = 2;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__STATE__SOCKET_CONNECTION_STATE_DISCONNECTED */
    public static final int f2037x986336f = 5;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__STATE__SOCKET_CONNECTION_STATE_DISCONNECTING */
    public static final int f2038x27404af2 = 4;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__STATE__SOCKET_CONNECTION_STATE_LISTENING */
    public static final int f2039x231a7fe7 = 1;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__STATE__SOCKET_CONNECTION_STATE_UNKNOWN */
    public static final int f2040x8e2b2a76 = 0;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__TYPE__SOCKET_TYPE_L2CAP_BREDR */
    public static final int f2041x78bfad1d = 3;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__TYPE__SOCKET_TYPE_L2CAP_LE */
    public static final int f2042x238ddbdf = 4;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__TYPE__SOCKET_TYPE_RFCOMM */
    public static final int f2043x64e251d3 = 1;
    public static final int BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__TYPE__SOCKET_TYPE_SCO = 2;

    /* renamed from: BLUETOOTH_SOCKET_CONNECTION_STATE_CHANGED__TYPE__SOCKET_TYPE_UNKNOWN */
    public static final int f2044xe431aff7 = 0;
    public static final int BOOT_SEQUENCE_REPORTED = 57;
    public static final int BROADCAST_DISPATCH_LATENCY_REPORTED = 142;
    public static final int BUBBLE_DEVELOPER_ERROR_REPORTED = 173;
    public static final int BUBBLE_DEVELOPER_ERROR_REPORTED__ERROR__ACTIVITY_INFO_MISSING = 1;

    /* renamed from: BUBBLE_DEVELOPER_ERROR_REPORTED__ERROR__ACTIVITY_INFO_NOT_RESIZABLE */
    public static final int f2045x9e921a48 = 2;

    /* renamed from: BUBBLE_DEVELOPER_ERROR_REPORTED__ERROR__DOCUMENT_LAUNCH_NOT_ALWAYS */
    public static final int f2046x6658e0d9 = 3;
    public static final int BUBBLE_DEVELOPER_ERROR_REPORTED__ERROR__UNKNOWN = 0;
    public static final int BUBBLE_UICHANGED__ACTION__COLLAPSED = 4;
    public static final int BUBBLE_UICHANGED__ACTION__DISMISSED = 5;
    public static final int BUBBLE_UICHANGED__ACTION__EXPANDED = 3;
    public static final int BUBBLE_UICHANGED__ACTION__FLYOUT = 16;
    public static final int BUBBLE_UICHANGED__ACTION__HEADER_GO_TO_APP = 8;
    public static final int BUBBLE_UICHANGED__ACTION__HEADER_GO_TO_SETTINGS = 9;
    public static final int BUBBLE_UICHANGED__ACTION__PERMISSION_DIALOG_SHOWN = 12;
    public static final int BUBBLE_UICHANGED__ACTION__PERMISSION_OPT_IN = 10;
    public static final int BUBBLE_UICHANGED__ACTION__PERMISSION_OPT_OUT = 11;
    public static final int BUBBLE_UICHANGED__ACTION__POSTED = 1;
    public static final int BUBBLE_UICHANGED__ACTION__STACK_DISMISSED = 6;
    public static final int BUBBLE_UICHANGED__ACTION__STACK_EXPANDED = 15;
    public static final int BUBBLE_UICHANGED__ACTION__STACK_MOVED = 7;
    public static final int BUBBLE_UICHANGED__ACTION__SWIPE_LEFT = 13;
    public static final int BUBBLE_UICHANGED__ACTION__SWIPE_RIGHT = 14;
    public static final int BUBBLE_UICHANGED__ACTION__UNKNOWN = 0;
    public static final int BUBBLE_UICHANGED__ACTION__UPDATED = 2;
    public static final int BUBBLE_UI_CHANGED = 149;
    public static final int BUILD_INFORMATION = 10044;
    public static final int CACHED_KILL_REPORTED = 17;
    public static final int CALL_STATE_CHANGED = 61;
    public static final int CALL_STATE_CHANGED__CALL_STATE__ABORTED = 8;
    public static final int CALL_STATE_CHANGED__CALL_STATE__ACTIVE = 5;
    public static final int CALL_STATE_CHANGED__CALL_STATE__CONNECTING = 1;
    public static final int CALL_STATE_CHANGED__CALL_STATE__DIALING = 3;
    public static final int CALL_STATE_CHANGED__CALL_STATE__DISCONNECTED = 7;
    public static final int CALL_STATE_CHANGED__CALL_STATE__DISCONNECTING = 9;
    public static final int CALL_STATE_CHANGED__CALL_STATE__NEW = 0;
    public static final int CALL_STATE_CHANGED__CALL_STATE__ON_HOLD = 6;
    public static final int CALL_STATE_CHANGED__CALL_STATE__PULLING = 10;
    public static final int CALL_STATE_CHANGED__CALL_STATE__RINGING = 4;
    public static final int CALL_STATE_CHANGED__CALL_STATE__SELECT_PHONE_ACCOUNT = 2;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__ANSWERED_ELSEWHERE = 11;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__BUSY = 7;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__CALL_PULLED = 12;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__CANCELED = 4;

    /* renamed from: CALL_STATE_CHANGED__DISCONNECT_CAUSE__CONNECTION_MANAGER_NOT_SUPPORTED */
    public static final int f2047x8d94437a = 10;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__ERROR = 1;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__LOCAL = 2;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__MISSED = 5;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__OTHER = 9;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__REJECTED = 6;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__REMOTE = 3;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__RESTRICTED = 8;
    public static final int CALL_STATE_CHANGED__DISCONNECT_CAUSE__UNKNOWN = 0;
    public static final int CAMERA_STATE_CHANGED = 25;
    public static final int CAMERA_STATE_CHANGED__STATE__OFF = 0;
    public static final int CAMERA_STATE_CHANGED__STATE__ON = 1;
    public static final int CAMERA_STATE_CHANGED__STATE__RESET = 2;
    public static final int CAR_POWER_STATE_CHANGED = 203;
    public static final int CAR_POWER_STATE_CHANGED__STATE__ON = 1;
    public static final int CAR_POWER_STATE_CHANGED__STATE__SHUTDOWN_PREPARE = 2;
    public static final int CAR_POWER_STATE_CHANGED__STATE__SIMULATE_SLEEP = 5;
    public static final int CAR_POWER_STATE_CHANGED__STATE__SUSPEND = 4;
    public static final int CAR_POWER_STATE_CHANGED__STATE__WAIT_FOR_FINISH = 3;
    public static final int CAR_POWER_STATE_CHANGED__STATE__WAIT_FOR_VHAL = 0;
    public static final int CATEGORY_SIZE = 10028;
    public static final int CATEGORY_SIZE__CATEGORY__APP_CACHE_SIZE = 3;
    public static final int CATEGORY_SIZE__CATEGORY__APP_DATA_SIZE = 2;
    public static final int CATEGORY_SIZE__CATEGORY__APP_SIZE = 1;
    public static final int CATEGORY_SIZE__CATEGORY__AUDIO = 6;
    public static final int CATEGORY_SIZE__CATEGORY__DOWNLOADS = 7;
    public static final int CATEGORY_SIZE__CATEGORY__OTHER = 9;
    public static final int CATEGORY_SIZE__CATEGORY__PHOTOS = 4;
    public static final int CATEGORY_SIZE__CATEGORY__SYSTEM = 8;
    public static final int CATEGORY_SIZE__CATEGORY__UNKNOWN = 0;
    public static final int CATEGORY_SIZE__CATEGORY__VIDEOS = 5;
    public static final int CHARGE_CYCLES_REPORTED = 74;
    public static final int CHARGING_STATE_CHANGED = 31;
    public static final int CHARGING_STATE_CHANGED__STATE__BATTERY_STATUS_CHARGING = 2;
    public static final int CHARGING_STATE_CHANGED__STATE__BATTERY_STATUS_DISCHARGING = 3;
    public static final int CHARGING_STATE_CHANGED__STATE__BATTERY_STATUS_FULL = 5;
    public static final int CHARGING_STATE_CHANGED__STATE__BATTERY_STATUS_INVALID = 0;
    public static final int CHARGING_STATE_CHANGED__STATE__BATTERY_STATUS_NOT_CHARGING = 4;
    public static final int CHARGING_STATE_CHANGED__STATE__BATTERY_STATUS_UNKNOWN = 1;
    public static final int CONNECTIVITY_STATE_CHANGED = 98;
    public static final int CONNECTIVITY_STATE_CHANGED__STATE__CONNECTED = 1;
    public static final int CONNECTIVITY_STATE_CHANGED__STATE__DISCONNECTED = 2;
    public static final int CONNECTIVITY_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int CONTENT_CAPTURE_CALLER_MISMATCH_REPORTED = 206;
    public static final int CONTENT_CAPTURE_FLUSHED = 209;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS = 207;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS__EVENT__ON_CONNECTED = 1;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS__EVENT__ON_DISCONNECTED = 2;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS__EVENT__ON_USER_DATA_REMOVED = 5;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS__EVENT__SET_DISABLED = 4;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS__EVENT__SET_WHITELIST = 3;
    public static final int CONTENT_CAPTURE_SERVICE_EVENTS__EVENT__UNKNOWN = 0;
    public static final int CONTENT_CAPTURE_SESSION_EVENTS = 208;
    public static final int CONTENT_CAPTURE_SESSION_EVENTS__EVENT__ON_SESSION_FINISHED = 2;
    public static final int CONTENT_CAPTURE_SESSION_EVENTS__EVENT__ON_SESSION_STARTED = 1;
    public static final int CONTENT_CAPTURE_SESSION_EVENTS__EVENT__SESSION_NOT_CREATED = 3;
    public static final int CONTENT_CAPTURE_SESSION_EVENTS__EVENT__UNKNOWN = 0;
    public static final int COOLING_DEVICE = 10059;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__BATTERY = 1;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__COMPONENT = 6;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__CPU = 2;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__FAN = 0;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__GPU = 3;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__MODEM = 4;
    public static final int COOLING_DEVICE__DEVICE_LOCATION__NPU = 5;
    public static final int CPU_ACTIVE_TIME = 10016;
    public static final int CPU_CLUSTER_TIME = 10017;
    public static final int CPU_TIME_PER_FREQ = 10008;
    public static final int CPU_TIME_PER_THREAD_FREQ = 10037;
    public static final int CPU_TIME_PER_UID = 10009;
    public static final int CPU_TIME_PER_UID_FREQ = 10010;
    public static final int DANGEROUS_PERMISSION_STATE = 10050;
    public static final int DATA_STALL_EVENT = 121;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_BLUETOOTH = 2;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_CELLULAR = 0;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_ETHERNET = 3;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_LOWPAN = 6;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_VPN = 4;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_WIFI = 1;
    public static final int DATA_STALL_EVENT__NETWORK_TYPE__TRANSPORT_WIFI_AWARE = 5;
    public static final int DATA_STALL_EVENT__VALIDATION_RESULT__INVALID = 2;
    public static final int DATA_STALL_EVENT__VALIDATION_RESULT__PARTIAL = 4;
    public static final int DATA_STALL_EVENT__VALIDATION_RESULT__PORTAL = 3;
    public static final int DATA_STALL_EVENT__VALIDATION_RESULT__UNKNOWN = 0;
    public static final int DATA_STALL_EVENT__VALIDATION_RESULT__VALID = 1;
    public static final int DAVEY_OCCURRED = 58;
    public static final int DEBUG_ELAPSED_CLOCK = 10046;
    public static final int DEBUG_ELAPSED_CLOCK__TYPE__ALWAYS_PRESENT = 1;
    public static final int DEBUG_ELAPSED_CLOCK__TYPE__PRESENT_ON_ODD_PULLS = 2;
    public static final int DEBUG_ELAPSED_CLOCK__TYPE__TYPE_UNKNOWN = 0;
    public static final int DEBUG_FAILING_ELAPSED_CLOCK = 10047;
    public static final int DEFERRED_JOB_STATS_REPORTED = 85;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER = 10041;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__AMBIENT_DISPLAY = 0;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__BLUETOOTH = 2;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__CAMERA = 3;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__CELL = 4;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__FLASHLIGHT = 5;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__IDLE = 6;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__MEMORY = 7;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__OVERCOUNTED = 8;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__PHONE = 9;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__SCREEN = 10;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__UNACCOUNTED = 11;
    public static final int DEVICE_CALCULATED_POWER_BLAME_OTHER__DRAIN_TYPE__WIFI = 13;
    public static final int DEVICE_CALCULATED_POWER_BLAME_UID = 10040;
    public static final int DEVICE_CALCULATED_POWER_USE = 10039;
    public static final int DEVICE_IDENTIFIER_ACCESS_DENIED = 172;
    public static final int DEVICE_IDLE_MODE_STATE_CHANGED = 21;
    public static final int DEVICE_IDLE_MODE_STATE_CHANGED__STATE__DEVICE_IDLE_MODE_DEEP = 2;
    public static final int DEVICE_IDLE_MODE_STATE_CHANGED__STATE__DEVICE_IDLE_MODE_LIGHT = 1;
    public static final int DEVICE_IDLE_MODE_STATE_CHANGED__STATE__DEVICE_IDLE_MODE_OFF = 0;
    public static final int DEVICE_IDLING_MODE_STATE_CHANGED = 22;
    public static final int DEVICE_IDLING_MODE_STATE_CHANGED__STATE__DEVICE_IDLE_MODE_DEEP = 2;
    public static final int DEVICE_IDLING_MODE_STATE_CHANGED__STATE__DEVICE_IDLE_MODE_LIGHT = 1;
    public static final int DEVICE_IDLING_MODE_STATE_CHANGED__STATE__DEVICE_IDLE_MODE_OFF = 0;
    public static final int DEVICE_POLICY_EVENT = 103;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ADD_CROSS_PROFILE_INTENT_FILTER = 48;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ADD_CROSS_PROFILE_WIDGET_PROVIDER = 49;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ADD_PERSISTENT_PREFERRED_ACTIVITY = 52;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ADD_USER_RESTRICTION = 12;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__CHOOSE_PRIVATE_KEY_ALIAS = 22;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_APPS_GET_TARGET_USER_PROFILES */
    public static final int f2048x721b4b3b = 125;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_APPS_START_ACTIVITY_AS_USER */
    public static final int f2049xc6e5d387 = 126;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__DO_USER_INFO_CLICKED = 57;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ENABLE_SYSTEM_APP = 64;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ENABLE_SYSTEM_APP_WITH_INTENT = 65;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ESTABLISH_VPN = 118;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__GENERATE_KEY_PAIR = 59;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__GET_USER_PASSWORD_COMPLEXITY_LEVEL */
    public static final int f2050x4d3ee472 = 72;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__GET_WIFI_MAC_ADDRESS = 54;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__INSTALL_CA_CERT = 21;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__INSTALL_EXISTING_PACKAGE = 66;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__INSTALL_KEY_PAIR = 20;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__INSTALL_PACKAGE = 112;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__INSTALL_SYSTEM_UPDATE = 73;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__INSTALL_SYSTEM_UPDATE_ERROR = 74;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__IS_MANAGED_KIOSK = 75;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__IS_UNATTENDED_MANAGED_KIOSK = 76;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__LOCK_NOW = 10;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__ON_LOCK_TASK_MODE_ENTERING = 69;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ACTION = 94;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_CANCELLED = 101;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_COPY_ACCOUNT_STATUS = 103;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_COPY_ACCOUNT_TASK_MS = 96;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_CREATE_PROFILE_TASK_MS */
    public static final int f2051x1fdc7643 = 97;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DOWNLOAD_PACKAGE_TASK_MS */
    public static final int f2052xf6fcc86c = 99;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_INSTALLED_BY_PACKAGE */
    public static final int f2053xe09c16e7 = 85;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_PACKAGE_NAME = 84;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ENCRYPT_DEVICE_ACTIVITY_TIME_MS */
    public static final int f2054x65fbefdb = 88;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ENTRY_POINT_ADB = 82;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ENTRY_POINT_CLOUD_ENROLLMENT */
    public static final int f2055x75765986 = 81;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ENTRY_POINT_NFC = 79;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ENTRY_POINT_QR_CODE = 80;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ENTRY_POINT_TRUSTED_SOURCE */
    public static final int f2056xd839d89b = 83;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_ERROR = 102;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_EXTRAS = 95;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_FINALIZATION_ACTIVITY_TIME_MS */
    public static final int f2057xfd73d7fd = 92;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_FLOW_TYPE = 124;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_INSTALL_PACKAGE_TASK_MS */
    public static final int f2058xa6b012a7 = 100;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_MANAGED_PROFILE_ON_FULLY_MANAGED_DEVICE */
    public static final int f2059x32a8cb19 = 77;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_NETWORK_TYPE = 93;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_PERSISTENT_DEVICE_OWNER */
    public static final int f2060xaf5ddf6 = 78;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_POST_ENCRYPTION_ACTIVITY_TIME_MS */
    public static final int f2061x7a78afe1 = 91;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_PREPARE_COMPLETED = 123;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_PREPARE_STARTED = 122;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_PREPARE_TOTAL_TIME_MS */
    public static final int f2062x48ac97c9 = 121;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_PREPROVISIONING_ACTIVITY_TIME_MS */
    public static final int f2063x8e20b133 = 87;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_PROVISIONING_ACTIVITY_TIME_MS */
    public static final int f2064xb2574d1e = 86;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_SESSION_COMPLETED = 106;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_SESSION_STARTED = 105;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_START_PROFILE_TASK_MS */
    public static final int f2065x75bb2ad1 = 98;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_TERMS_ACTIVITY_TIME_MS */
    public static final int f2066x2c57949c = 107;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_TERMS_COUNT = 108;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_TERMS_READ = 109;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_TOTAL_TASK_TIME_MS = 104;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_TRAMPOLINE_ACTIVITY_TIME_MS */
    public static final int f2067x490bb8ee = 90;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_WEB_ACTIVITY_TIME_MS = 89;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__QUERY_DETAILS = 33;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__QUERY_SUMMARY = 32;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__QUERY_SUMMARY_FOR_DEVICE = 116;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__QUERY_SUMMARY_FOR_USER = 31;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__REBOOT = 34;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__REMOVE_CROSS_PROFILE_WIDGET_PROVIDER */
    public static final int f2068xd960e272 = 117;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__REMOVE_KEY_PAIR = 23;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__REMOVE_USER_RESTRICTION = 13;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__REQUEST_BUGREPORT = 53;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__REQUEST_QUIET_MODE_ENABLED = 55;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__RETRIEVE_NETWORK_LOGS = 120;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__RETRIEVE_PRE_REBOOT_SECURITY_LOGS = 17;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__RETRIEVE_SECURITY_LOGS = 16;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__SEPARATE_PROFILE_CHALLENGE_CHANGED */
    public static final int f2069xcfd8c9fc = 110;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_ALWAYS_ON_VPN_PACKAGE = 26;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_HIDDEN = 63;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_RESTRICTIONS = 62;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_AUTO_TIME_REQUIRED = 36;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__SET_BLUETOOTH_CONTACT_SHARING_DISABLED */
    public static final int f2070xb9f21e62 = 47;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_CAMERA_DISABLED = 30;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_CERT_INSTALLER_PACKAGE = 25;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__SET_CROSS_PROFILE_CALENDAR_PACKAGES */
    public static final int f2071x8a0a7c46 = 70;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__SET_CROSS_PROFILE_CALLER_ID_DISABLED */
    public static final int f2072x605b65f4 = 46;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__SET_CROSS_PROFILE_CONTACTS_SEARCH_DISABLED */
    public static final int f2073x4b077ccf = 45;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_DEVICE_OWNER_LOCK_SCREEN_INFO = 42;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_GLOBAL_SETTING = 111;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_KEEP_UNINSTALLED_PACKAGES = 61;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_KEYGUARD_DISABLED = 37;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_KEYGUARD_DISABLED_FEATURES = 9;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_KEY_PAIR_CERTIFICATE = 60;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_LOCKTASK_MODE_ENABLED = 51;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_LONG_SUPPORT_MESSAGE = 44;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_MASTER_VOLUME_MUTED = 35;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_NETWORK_LOGGING_ENABLED = 119;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_ORGANIZATION_COLOR = 39;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PACKAGES_SUSPENDED = 68;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_LENGTH = 2;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_LETTERS = 5;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_LOWER_CASE = 6;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_NON_LETTER = 4;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_NUMERIC = 3;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_SYMBOLS = 8;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_MINIMUM_UPPER_CASE = 7;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PASSWORD_QUALITY = 1;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PERMISSION_GRANT_STATE = 19;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PERMISSION_POLICY = 18;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__SET_PERMITTED_ACCESSIBILITY_SERVICES */
    public static final int f2074x90e6d809 = 28;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PERMITTED_INPUT_METHODS = 27;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_PROFILE_NAME = 40;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_SCREEN_CAPTURE_DISABLED = 29;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_SECURE_SETTING = 14;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_SECURITY_LOGGING_ENABLED = 15;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_SHORT_SUPPORT_MESSAGE = 43;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_STATUS_BAR_DISABLED = 38;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_SYSTEM_UPDATE_POLICY = 50;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_UNINSTALL_BLOCKED = 67;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__SET_USER_ICON = 41;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__TRANSFER_OWNERSHIP = 58;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__UNINSTALL_CA_CERTS = 24;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__UNINSTALL_PACKAGE = 113;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__WIFI_SERVICE_ADD_NETWORK_SUGGESTIONS */
    public static final int f2075x6b32b9c2 = 114;

    /* renamed from: DEVICE_POLICY_EVENT__EVENT_ID__WIFI_SERVICE_ADD_OR_UPDATE_NETWORK */
    public static final int f2076x6668b18 = 115;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__WIPE_DATA_WITH_REASON = 11;
    public static final int DEVICE_POLICY_EVENT__EVENT_ID__WORK_PROFILE_LOCATION_CHANGED = 56;
    public static final int DIRECTORY_USAGE = 10026;
    public static final int DIRECTORY_USAGE__DIRECTORY__CACHE = 2;
    public static final int DIRECTORY_USAGE__DIRECTORY__DATA = 1;
    public static final int DIRECTORY_USAGE__DIRECTORY__SYSTEM = 3;
    public static final int DIRECTORY_USAGE__DIRECTORY__UNKNOWN = 0;
    public static final int DISK_IO = 10032;
    public static final int DISK_SPACE = 10018;
    public static final int DISK_STATS = 10025;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COMPRESS_ERROR */
    public static final int f2077xf269757b = 25;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COMPRESS_EXTERNAL_PROVIDER */
    public static final int f2078xe77cbef8 = 21;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COMPRESS_INTRA_PROVIDER */
    public static final int f2079xff7e989f = 19;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COMPRESS_SYSTEM_PROVIDER */
    public static final int f2080xe271c5d4 = 20;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COPY = 2;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COPY_ERROR = 16;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COPY_EXTERNAL_PROVIDER */
    public static final int f2081x7497a44b = 5;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COPY_INTRA_PROVIDER */
    public static final int f2082xec5eec2c = 3;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_COPY_SYSTEM_PROVIDER */
    public static final int f2083x919be3e7 = 4;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_CREATE_DIR = 12;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_CREATE_DIR_ERROR */
    public static final int f2084xad7b06c3 = 18;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_DELETE = 10;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_DELETE_ERROR */
    public static final int f2085xdb796504 = 14;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_EXTRACT_ERROR */
    public static final int f2086x383193da = 26;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_EXTRACT_EXTERNAL_PROVIDER */
    public static final int f2087x9ea746d7 = 24;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_EXTRACT_INTRA_PROVIDER */
    public static final int f2088xfd393520 = 22;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_EXTRACT_SYSTEM_PROVIDER */
    public static final int f2089x9c0ab973 = 23;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_MOVE = 6;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_MOVE_ERROR = 15;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_MOVE_EXTERNAL_PROVIDER */
    public static final int f2090x124c51a7 = 9;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_MOVE_INTRA_PROVIDER */
    public static final int f2091x399c9c50 = 7;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_MOVE_SYSTEM_PROVIDER */
    public static final int f2092xec143843 = 8;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_OTHER = 1;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_OTHER_ERROR = 13;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_RENAME = 11;

    /* renamed from: DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_RENAME_ERROR */
    public static final int f2093x7895c57 = 17;
    public static final int DOCS_UIFILE_OPERATION_CANCELED_REPORTED__FILE_OP__OP_UNKNOWN = 0;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COMPRESS_ERROR */
    public static final int f2094x6ee2b80d = 25;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COMPRESS_EXTERNAL_PROVIDER */
    public static final int f2095x44dcb68a = 21;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COMPRESS_INTRA_PROVIDER */
    public static final int f2096xbc321a4d = 19;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COMPRESS_SYSTEM_PROVIDER */
    public static final int f2097xbc2e79e6 = 20;
    public static final int DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COPY = 2;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COPY_ERROR */
    public static final int f2098x55e0dae0 = 16;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COPY_EXTERNAL_PROVIDER */
    public static final int f2099x9bb634dd = 5;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COPY_INTRA_PROVIDER */
    public static final int f2100xec8274da = 3;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_COPY_SYSTEM_PROVIDER */
    public static final int f2101x95e970f9 = 4;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_CREATE_DIR */
    public static final int f2102x93ac10ec = 12;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_CREATE_DIR_ERROR */
    public static final int f2103xf0adecd5 = 18;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_DELETE */
    public static final int f2104x226b0ced = 10;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_DELETE_ERROR */
    public static final int f2105xd0262416 = 14;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_EXTRACT_ERROR */
    public static final int f2106xd91cb708 = 26;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_EXTRACT_EXTERNAL_PROVIDER */
    public static final int f2107xfc811505 = 24;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_EXTRACT_INTRA_PROVIDER */
    public static final int f2108x2457c5b2 = 22;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_EXTRACT_SYSTEM_PROVIDER */
    public static final int f2109x58be3b21 = 23;
    public static final int DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_MOVE = 6;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_MOVE_ERROR */
    public static final int f2110x2e09b23c = 15;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_MOVE_EXTERNAL_PROVIDER */
    public static final int f2111x396ae239 = 9;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_MOVE_INTRA_PROVIDER */
    public static final int f2112x39c024fe = 7;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_MOVE_SYSTEM_PROVIDER */
    public static final int f2113xf061c555 = 8;
    public static final int DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_OTHER = 1;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_OTHER_ERROR */
    public static final int f2114xe29cf5d7 = 13;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_RENAME */
    public static final int f2115x3a4fbe80 = 11;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_RENAME_ERROR */
    public static final int f2116xfc361b69 = 17;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__FILE_OP__OP_UNKNOWN */
    public static final int f2117xbd920d28 = 0;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__MODE__MODE_CONVENTIONAL */
    public static final int f2118x3ba4da0d = 3;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__MODE__MODE_CONVERTED */
    public static final int f2119x229f5c61 = 2;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__MODE__MODE_PROVIDER */
    public static final int f2120x8fe9922 = 1;

    /* renamed from: DOCS_UIFILE_OPERATION_COPY_MOVE_MODE_REPORTED__MODE__MODE_UNKNOWN */
    public static final int f2121x64d2ead9 = 0;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_DOWNLOADS */
    public static final int f2122x51ae36ac = 5;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_MEDIA = 2;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_MTP = 6;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_OTHER = 1;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_STORAGE_EXTERNAL */
    public static final int f2123x89f9a02e = 4;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_STORAGE_INTERNAL */
    public static final int f2124x18800320 = 3;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__AUTHORITY__AUTH_UNKNOWN = 0;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_CREATE_DOC */
    public static final int f2125xdf6ab709 = 5;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_DELETE_DOC */
    public static final int f2126xf6fbac38 = 7;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_OBTAIN_STREAM_TYPE */
    public static final int f2127x2ec9a3b3 = 8;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_OPEN_FILE = 3;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_QUERY_CHILD */
    public static final int f2128x28929171 = 2;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_QUERY_DOC = 1;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_QUICK_COPY */
    public static final int f2129xf393925b = 10;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_QUICK_MOVE */
    public static final int f2130xf3981eb7 = 9;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_READ_FILE = 4;
    public static final int DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_UNKNOWN = 0;

    /* renamed from: DOCS_UIFILE_OPERATION_FAILURE_REPORTED__SUB_OP__SUB_OP_WRITE_FILE */
    public static final int f2131x698d0db0 = 6;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COMPRESS_ERROR = 25;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COMPRESS_EXTERNAL_PROVIDER */
    public static final int f2132xf5c815b8 = 21;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COMPRESS_INTRA_PROVIDER */
    public static final int f2133xff10b9df = 19;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COMPRESS_SYSTEM_PROVIDER */
    public static final int f2134xd523cc94 = 20;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COPY = 2;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COPY_ERROR = 16;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COPY_EXTERNAL_PROVIDER */
    public static final int f2135x959c5b0b = 5;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COPY_INTRA_PROVIDER = 3;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_COPY_SYSTEM_PROVIDER = 4;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_CREATE_DIR = 12;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_CREATE_DIR_ERROR = 18;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_DELETE = 10;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_DELETE_ERROR = 14;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_EXTRACT_ERROR = 26;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_EXTRACT_EXTERNAL_PROVIDER */
    public static final int f2136x2361817 = 24;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_EXTRACT_INTRA_PROVIDER */
    public static final int f2137x1e3debe0 = 22;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_EXTRACT_SYSTEM_PROVIDER */
    public static final int f2138x9b9cdab3 = 23;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_MOVE = 6;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_MOVE_ERROR = 15;

    /* renamed from: DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_MOVE_EXTERNAL_PROVIDER */
    public static final int f2139x33510867 = 9;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_MOVE_INTRA_PROVIDER = 7;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_MOVE_SYSTEM_PROVIDER = 8;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_OTHER = 1;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_OTHER_ERROR = 13;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_RENAME = 11;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_RENAME_ERROR = 17;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__FILE_OP__OP_UNKNOWN = 0;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__PROVIDER__PROVIDER_EXTERNAL = 2;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__PROVIDER__PROVIDER_SYSTEM = 1;
    public static final int DOCS_UIFILE_OPERATION_REPORTED__PROVIDER__PROVIDER_UNKNOWN = 0;

    /* renamed from: DOCS_UIINVALID_SCOPED_ACCESS_REQUEST_REPORTED__TYPE__SCOPED_DIR_ACCESS_DEPRECATED */
    public static final int f2140x5f1cab96 = 4;

    /* renamed from: DOCS_UIINVALID_SCOPED_ACCESS_REQUEST_REPORTED__TYPE__SCOPED_DIR_ACCESS_ERROR */
    public static final int f2141x3a469e25 = 3;

    /* renamed from: DOCS_UIINVALID_SCOPED_ACCESS_REQUEST_REPORTED__TYPE__SCOPED_DIR_ACCESS_INVALID_ARGUMENTS */
    public static final int f2142x1d41318b = 1;

    /* renamed from: DOCS_UIINVALID_SCOPED_ACCESS_REQUEST_REPORTED__TYPE__SCOPED_DIR_ACCESS_INVALID_DIRECTORY */
    public static final int f2143x5d34d6a2 = 2;

    /* renamed from: DOCS_UIINVALID_SCOPED_ACCESS_REQUEST_REPORTED__TYPE__SCOPED_DIR_ACCESS_UNKNOWN */
    public static final int f2144xa44e127 = 0;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_AUDIO = 3;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_DEVICE_STORAGE = 4;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_DOWNLOADS = 5;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_HOME = 6;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_IMAGES = 7;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_MTP = 10;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_NONE = 1;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_OTHER_DOCS_PROVIDER = 2;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_RECENTS = 8;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_THIRD_PARTY_APP = 11;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_UNKNOWN = 0;
    public static final int DOCS_UILAUNCH_REPORTED__INITIAL_ROOT__ROOT_VIDEOS = 9;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__BROWSE = 6;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__CREATE = 2;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__GET_CONTENT = 3;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__OPEN = 1;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__OPEN_TREE = 4;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__OTHER = 7;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__PICK_COPY_DEST = 5;
    public static final int DOCS_UILAUNCH_REPORTED__LAUNCH_ACTION__UNKNOWN = 0;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_ANY = 2;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_APPLICATION = 3;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_AUDIO = 4;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_IMAGE = 5;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_MESSAGE = 6;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_MULTIPART = 7;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_NONE = 1;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_OTHER = 10;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_TEXT = 8;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_UNKNOWN = 0;
    public static final int DOCS_UILAUNCH_REPORTED__MIME_TYPE__MIME_VIDEO = 9;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_ANY = 2;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_APPLICATION = 3;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_AUDIO = 4;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_IMAGE = 5;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_MESSAGE = 6;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_MULTIPART = 7;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_NONE = 1;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_OTHER = 10;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_TEXT = 8;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_UNKNOWN = 0;
    public static final int DOCS_UIPICK_RESULT_REPORTED__MIME_TYPE__MIME_VIDEO = 9;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_AUDIO = 3;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_DEVICE_STORAGE = 4;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_DOWNLOADS = 5;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_HOME = 6;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_IMAGES = 7;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_MTP = 10;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_NONE = 1;

    /* renamed from: DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_OTHER_DOCS_PROVIDER */
    public static final int f2145x18f42bc3 = 2;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_RECENTS = 8;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_THIRD_PARTY_APP = 11;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_UNKNOWN = 0;
    public static final int DOCS_UIPICK_RESULT_REPORTED__PICKED_FROM__ROOT_VIDEOS = 9;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_AUDIO = 3;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_DEVICE_STORAGE = 4;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_DOWNLOADS = 5;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_HOME = 6;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_IMAGES = 7;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_MTP = 10;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_NONE = 1;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_OTHER_DOCS_PROVIDER = 2;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_RECENTS = 8;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_THIRD_PARTY_APP = 11;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_UNKNOWN = 0;
    public static final int DOCS_UIROOT_VISITED_REPORTED__ROOT__ROOT_VIDEOS = 9;
    public static final int DOCS_UIROOT_VISITED_REPORTED__SCOPE__SCOPE_FILES = 1;
    public static final int DOCS_UIROOT_VISITED_REPORTED__SCOPE__SCOPE_PICKER = 2;
    public static final int DOCS_UIROOT_VISITED_REPORTED__SCOPE__SCOPE_UNKNOWN = 0;
    public static final int DOCS_UISEARCH_MODE_REPORTED__SEARCH_MODE__SEARCH_CHIPS = 2;
    public static final int DOCS_UISEARCH_MODE_REPORTED__SEARCH_MODE__SEARCH_KEYWORD = 1;
    public static final int DOCS_UISEARCH_MODE_REPORTED__SEARCH_MODE__SEARCH_KEYWORD_N_CHIPS = 3;
    public static final int DOCS_UISEARCH_MODE_REPORTED__SEARCH_MODE__SEARCH_UNKNOWN = 0;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_CHIP_AUDIOS = 2;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_CHIP_DOCS = 4;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_CHIP_IMAGES = 1;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_CHIP_VIDEOS = 3;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_SEARCH_HISTORY = 5;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_SEARCH_STRING = 6;
    public static final int DOCS_UISEARCH_TYPE_REPORTED__SEARCH_TYPE__TYPE_UNKNOWN = 0;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_COMPRESS = 28;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_COPY_CLIPBOARD = 24;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_COPY_TO = 12;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_CREATE_DIR = 16;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_CUT_CLIPBOARD = 27;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_DELETE = 14;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_DRAG_N_DROP = 25;

    /* renamed from: DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_DRAG_N_DROP_MULTI_WINDOW */
    public static final int f2146x46b0073d = 26;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_EXTRACT_TO = 29;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_GRID = 2;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_HIDE_ADVANCED = 21;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_HIDE_SIZE = 10;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_INSPECTOR = 31;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_LIST = 3;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_MOVE_TO = 13;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_NEW_WINDOW = 22;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_OPEN = 19;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_OTHER = 1;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_PASTE_CLIPBOARD = 23;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_RENAME = 15;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SEARCH = 8;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SEARCH_CHIP = 32;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SEARCH_HISTORY = 33;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SELECT_ALL = 17;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SETTINGS = 11;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SHARE = 18;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SHOW_ADVANCED = 20;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SHOW_SIZE = 9;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SORT_DATE = 5;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SORT_NAME = 4;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SORT_SIZE = 6;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_SORT_TYPE = 7;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_UNKNOWN = 0;
    public static final int DOCS_UIUSER_ACTION_REPORTED__ACTION__ACTION_VIEW_IN_APPLICATION = 30;
    public static final int DOCS_UI_FILE_OP_CANCELED = 104;
    public static final int DOCS_UI_FILE_OP_COPY_MOVE_MODE_REPORTED = 105;
    public static final int DOCS_UI_FILE_OP_FAILURE = 106;
    public static final int DOCS_UI_INVALID_SCOPED_ACCESS_REQUEST = 108;
    public static final int DOCS_UI_LAUNCH_REPORTED = 109;
    public static final int DOCS_UI_PICKER_LAUNCHED_FROM_REPORTED = 117;
    public static final int DOCS_UI_PICK_RESULT_REPORTED = 118;
    public static final int DOCS_UI_PROVIDER_FILE_OP = 107;
    public static final int DOCS_UI_ROOT_VISITED = 110;
    public static final int DOCS_UI_SEARCH_MODE_REPORTED = 119;
    public static final int DOCS_UI_SEARCH_TYPE_REPORTED = 120;
    public static final int DOCS_UI_STARTUP_MS = 111;
    public static final int DOCS_UI_USER_ACTION_REPORTED = 112;
    public static final int EXCESSIVE_CPU_USAGE_REPORTED = 16;
    public static final int EXTERNAL_STORAGE_INFO = 10053;
    public static final int EXTERNAL_STORAGE_INFO__STORAGE_TYPE__OTHER = 3;
    public static final int EXTERNAL_STORAGE_INFO__STORAGE_TYPE__SD_CARD = 1;
    public static final int EXTERNAL_STORAGE_INFO__STORAGE_TYPE__UNKNOWN = 0;
    public static final int EXTERNAL_STORAGE_INFO__STORAGE_TYPE__USB = 2;
    public static final int EXTERNAL_STORAGE_INFO__VOLUME_TYPE__OTHER = 3;
    public static final int EXTERNAL_STORAGE_INFO__VOLUME_TYPE__PRIVATE = 2;
    public static final int EXTERNAL_STORAGE_INFO__VOLUME_TYPE__PUBLIC = 1;
    public static final int EXTERNAL_STORAGE_INFO__VOLUME_TYPE__UNKNOWN = 0;
    public static final int FACE_SETTINGS = 10058;
    public static final int FLAG_FLIP_UPDATE_OCCURRED = 101;
    public static final int FLASHLIGHT_STATE_CHANGED = 26;
    public static final int FLASHLIGHT_STATE_CHANGED__STATE__OFF = 0;
    public static final int FLASHLIGHT_STATE_CHANGED__STATE__ON = 1;
    public static final int FLASHLIGHT_STATE_CHANGED__STATE__RESET = 2;
    public static final int FOREGROUND_SERVICE_STATE_CHANGED = 60;
    public static final int FOREGROUND_SERVICE_STATE_CHANGED__STATE__ENTER = 1;
    public static final int FOREGROUND_SERVICE_STATE_CHANGED__STATE__EXIT = 2;
    public static final int FULL_BATTERY_CAPACITY = 10020;
    public static final int GARAGE_MODE_INFO = 204;
    public static final int GENERIC_ATOM = 82;
    public static final int GENERIC_ATOM__EVENT_ID__TYPE_UNKNOWN = 0;
    public static final int GNSS_CONFIGURATION_REPORTED = 132;

    /* renamed from: GNSS_CONFIGURATION_REPORTED__A_GLONASS_POS_PROTOCOL_SELECT__LPP_UPLANE */
    public static final int f2147x1984f9e = 4;

    /* renamed from: GNSS_CONFIGURATION_REPORTED__A_GLONASS_POS_PROTOCOL_SELECT__RRC_CPLANE */
    public static final int f2148x7eb351d9 = 1;

    /* renamed from: GNSS_CONFIGURATION_REPORTED__A_GLONASS_POS_PROTOCOL_SELECT__RRLP_CPLANE */
    public static final int f2149x2b29d8f0 = 2;
    public static final int GNSS_CONFIGURATION_REPORTED__GPS_LOCK__MO = 1;
    public static final int GNSS_CONFIGURATION_REPORTED__GPS_LOCK__NI = 2;
    public static final int GNSS_CONFIGURATION_REPORTED__LPP_PROFILE__CONTROL_PLANE = 2;
    public static final int GNSS_CONFIGURATION_REPORTED__LPP_PROFILE__USER_PLANE = 1;
    public static final int GNSS_CONFIGURATION_REPORTED__SUPL_MODE__MSA = 2;
    public static final int GNSS_CONFIGURATION_REPORTED__SUPL_MODE__MSB = 1;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED = 131;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__PROTOCOL_STACK__CTRL_PLANE = 0;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__PROTOCOL_STACK__IMS = 10;

    /* renamed from: GNSS_NFW_NOTIFICATION_REPORTED__PROTOCOL_STACK__OTHER_PROTOCOL_STACK */
    public static final int f2150xb407e940 = 100;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__PROTOCOL_STACK__SIM = 11;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__PROTOCOL_STACK__SUPL = 1;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__AUTOMOBILE_CLIENT = 20;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__CARRIER = 0;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__GNSS_CHIPSET_VENDOR = 12;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__MODEM_CHIPSET_VENDOR = 11;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__OEM = 10;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__OTHER_CHIPSET_VENDOR = 13;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__REQUESTOR__OTHER_REQUESTOR = 100;

    /* renamed from: GNSS_NFW_NOTIFICATION_REPORTED__RESPONSE_TYPE__ACCEPTED_LOCATION_PROVIDED */
    public static final int f2151x87ef235e = 2;

    /* renamed from: GNSS_NFW_NOTIFICATION_REPORTED__RESPONSE_TYPE__ACCEPTED_NO_LOCATION_PROVIDED */
    public static final int f2152x3be6e5e = 1;
    public static final int GNSS_NFW_NOTIFICATION_REPORTED__RESPONSE_TYPE__REJECTED = 0;
    public static final int GNSS_NI_EVENT_REPORTED = 124;
    public static final int GNSS_NI_EVENT_REPORTED__DEFAULT_RESPONSE__RESPONSE_ACCEPT = 1;
    public static final int GNSS_NI_EVENT_REPORTED__DEFAULT_RESPONSE__RESPONSE_DENY = 2;
    public static final int GNSS_NI_EVENT_REPORTED__DEFAULT_RESPONSE__RESPONSE_NORESP = 3;
    public static final int GNSS_NI_EVENT_REPORTED__EVENT_TYPE__NI_REQUEST = 1;
    public static final int GNSS_NI_EVENT_REPORTED__EVENT_TYPE__NI_RESPONSE = 2;
    public static final int GNSS_NI_EVENT_REPORTED__EVENT_TYPE__UNKNOWN = 0;
    public static final int GNSS_NI_EVENT_REPORTED__NI_TYPE__EMERGENCY_SUPL = 4;
    public static final int GNSS_NI_EVENT_REPORTED__NI_TYPE__UMTS_CTRL_PLANE = 3;
    public static final int GNSS_NI_EVENT_REPORTED__NI_TYPE__UMTS_SUPL = 2;
    public static final int GNSS_NI_EVENT_REPORTED__NI_TYPE__VOICE = 1;
    public static final int GNSS_NI_EVENT_REPORTED__REQUESTOR_ID_ENCODING__ENC_NONE = 0;

    /* renamed from: GNSS_NI_EVENT_REPORTED__REQUESTOR_ID_ENCODING__ENC_SUPL_GSM_DEFAULT */
    public static final int f2153x98f0fa4d = 1;
    public static final int GNSS_NI_EVENT_REPORTED__REQUESTOR_ID_ENCODING__ENC_SUPL_UCS2 = 3;
    public static final int GNSS_NI_EVENT_REPORTED__REQUESTOR_ID_ENCODING__ENC_SUPL_UTF8 = 2;
    public static final int GNSS_NI_EVENT_REPORTED__REQUESTOR_ID_ENCODING__ENC_UNKNOWN = -1;
    public static final int GNSS_NI_EVENT_REPORTED__TEXT_ENCODING__ENC_NONE = 0;
    public static final int GNSS_NI_EVENT_REPORTED__TEXT_ENCODING__ENC_SUPL_GSM_DEFAULT = 1;
    public static final int GNSS_NI_EVENT_REPORTED__TEXT_ENCODING__ENC_SUPL_UCS2 = 3;
    public static final int GNSS_NI_EVENT_REPORTED__TEXT_ENCODING__ENC_SUPL_UTF8 = 2;
    public static final int GNSS_NI_EVENT_REPORTED__TEXT_ENCODING__ENC_UNKNOWN = -1;
    public static final int GNSS_NI_EVENT_REPORTED__USER_RESPONSE__RESPONSE_ACCEPT = 1;
    public static final int GNSS_NI_EVENT_REPORTED__USER_RESPONSE__RESPONSE_DENY = 2;
    public static final int GNSS_NI_EVENT_REPORTED__USER_RESPONSE__RESPONSE_NORESP = 3;
    public static final int GPS_SCAN_STATE_CHANGED = 6;
    public static final int GPS_SCAN_STATE_CHANGED__STATE__OFF = 0;
    public static final int GPS_SCAN_STATE_CHANGED__STATE__ON = 1;
    public static final int GPS_SIGNAL_QUALITY_CHANGED = 69;
    public static final int GPS_SIGNAL_QUALITY_CHANGED__LEVEL__GPS_SIGNAL_QUALITY_GOOD = 1;
    public static final int GPS_SIGNAL_QUALITY_CHANGED__LEVEL__GPS_SIGNAL_QUALITY_POOR = 0;
    public static final int GPS_SIGNAL_QUALITY_CHANGED__LEVEL__GPS_SIGNAL_QUALITY_UNKNOWN = -1;
    public static final int GPU_STATS_APP_INFO = 10055;
    public static final int GPU_STATS_GLOBAL_INFO = 10054;
    public static final int GRANT_PERMISSIONS_ACTIVITY_BUTTON_ACTIONS = 213;
    public static final int HARDWARE_FAILED = 72;
    public static final int HARDWARE_FAILED__HARDWARE_TYPE__HARDWARE_FAILED_CODEC = 2;
    public static final int HARDWARE_FAILED__HARDWARE_TYPE__HARDWARE_FAILED_FINGERPRINT = 4;
    public static final int HARDWARE_FAILED__HARDWARE_TYPE__HARDWARE_FAILED_MICROPHONE = 1;
    public static final int HARDWARE_FAILED__HARDWARE_TYPE__HARDWARE_FAILED_SPEAKER = 3;
    public static final int HARDWARE_FAILED__HARDWARE_TYPE__HARDWARE_FAILED_UNKNOWN = 0;
    public static final int HIDDEN_API_USED = 178;
    public static final int HIDDEN_API_USED__ACCESS_METHOD__JNI = 2;
    public static final int HIDDEN_API_USED__ACCESS_METHOD__LINKING = 3;
    public static final int HIDDEN_API_USED__ACCESS_METHOD__NONE = 0;
    public static final int HIDDEN_API_USED__ACCESS_METHOD__REFLECTION = 1;
    public static final int INTELLIGENCE_EVENT_REPORTED = 188;

    /* renamed from: INTELLIGENCE_EVENT_REPORTED__EVENT_ID__EVENT_CONTENT_SUGGESTIONS_CLASSIFY_CONTENT_CALL */
    public static final int f2154xa834becc = 1;

    /* renamed from: INTELLIGENCE_EVENT_REPORTED__EVENT_ID__EVENT_CONTENT_SUGGESTIONS_SUGGEST_CONTENT_CALL */
    public static final int f2155x56a19b72 = 2;
    public static final int INTELLIGENCE_EVENT_REPORTED__EVENT_ID__EVENT_UNKNOWN = 0;
    public static final int INTELLIGENCE_EVENT_REPORTED__STATUS__STATUS_FAILED = 2;
    public static final int INTELLIGENCE_EVENT_REPORTED__STATUS__STATUS_SUCCEEDED = 1;
    public static final int INTELLIGENCE_EVENT_REPORTED__STATUS__STATUS_UNKNOWN = 0;
    public static final int INTERACTIVE_STATE_CHANGED = 33;
    public static final int INTERACTIVE_STATE_CHANGED__STATE__OFF = 0;
    public static final int INTERACTIVE_STATE_CHANGED__STATE__ON = 1;
    public static final int ISOLATED_UID_CHANGED = 43;
    public static final int ISOLATED_UID_CHANGED__EVENT__CREATED = 1;
    public static final int ISOLATED_UID_CHANGED__EVENT__REMOVED = 0;
    public static final int KERNEL_WAKELOCK = 10004;
    public static final int KERNEL_WAKEUP_REPORTED = 36;
    public static final int KEYGUARD_BOUNCER_PASSWORD_ENTERED = 64;
    public static final int KEYGUARD_BOUNCER_PASSWORD_ENTERED__RESULT__FAILURE = 1;
    public static final int KEYGUARD_BOUNCER_PASSWORD_ENTERED__RESULT__SUCCESS = 2;
    public static final int KEYGUARD_BOUNCER_PASSWORD_ENTERED__RESULT__UNKNOWN = 0;
    public static final int KEYGUARD_BOUNCER_STATE_CHANGED = 63;
    public static final int KEYGUARD_BOUNCER_STATE_CHANGED__STATE__HIDDEN = 1;
    public static final int KEYGUARD_BOUNCER_STATE_CHANGED__STATE__SHOWN = 2;
    public static final int KEYGUARD_BOUNCER_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int KEYGUARD_STATE_CHANGED = 62;
    public static final int KEYGUARD_STATE_CHANGED__STATE__HIDDEN = 1;
    public static final int KEYGUARD_STATE_CHANGED__STATE__OCCLUDED = 3;
    public static final int KEYGUARD_STATE_CHANGED__STATE__SHOWN = 2;
    public static final int KEYGUARD_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int KEY_VALUE_PAIRS_ATOM = 83;
    public static final int LAUNCHER_EVENT = 19;
    public static final int LAUNCHER_UICHANGED__ACTION__DEFAULT_ACTION = 0;
    public static final int LAUNCHER_UICHANGED__ACTION__DISMISS_TASK = 3;
    public static final int LAUNCHER_UICHANGED__ACTION__DRAGDROP = 5;
    public static final int LAUNCHER_UICHANGED__ACTION__LAUNCH_APP = 1;
    public static final int LAUNCHER_UICHANGED__ACTION__LAUNCH_TASK = 2;
    public static final int LAUNCHER_UICHANGED__ACTION__LONGPRESS = 4;
    public static final int LAUNCHER_UICHANGED__ACTION__SWIPE_DOWN = 7;
    public static final int LAUNCHER_UICHANGED__ACTION__SWIPE_LEFT = 8;
    public static final int LAUNCHER_UICHANGED__ACTION__SWIPE_RIGHT = 9;
    public static final int LAUNCHER_UICHANGED__ACTION__SWIPE_UP = 6;
    public static final int LAUNCHER_UICHANGED__DST_STATE__ALLAPPS = 3;
    public static final int LAUNCHER_UICHANGED__DST_STATE__BACKGROUND = 0;
    public static final int LAUNCHER_UICHANGED__DST_STATE__HOME = 1;
    public static final int LAUNCHER_UICHANGED__DST_STATE__OVERVIEW = 2;
    public static final int LAUNCHER_UICHANGED__SRC_STATE__ALLAPPS = 3;
    public static final int LAUNCHER_UICHANGED__SRC_STATE__BACKGROUND = 0;
    public static final int LAUNCHER_UICHANGED__SRC_STATE__HOME = 1;
    public static final int LAUNCHER_UICHANGED__SRC_STATE__OVERVIEW = 2;
    public static final int LMK_KILL_OCCURRED = 51;
    public static final int LMK_STATE_CHANGED = 54;
    public static final int LMK_STATE_CHANGED__STATE__START = 1;
    public static final int LMK_STATE_CHANGED__STATE__STOP = 2;
    public static final int LMK_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int LOCATION_ACCESS_CHECK_NOTIFICATION_ACTION = 214;

    /* renamed from: LOCATION_ACCESS_CHECK_NOTIFICATION_ACTION__RESULT__NOTIFICATION_CLICKED */
    public static final int f2156xaa17e3b2 = 3;

    /* renamed from: LOCATION_ACCESS_CHECK_NOTIFICATION_ACTION__RESULT__NOTIFICATION_DECLINED */
    public static final int f2157x84bb19e3 = 2;

    /* renamed from: LOCATION_ACCESS_CHECK_NOTIFICATION_ACTION__RESULT__NOTIFICATION_PRESENTED */
    public static final int f2158xc1d2db65 = 1;
    public static final int LOCATION_ACCESS_CHECK_NOTIFICATION_ACTION__RESULT__UNDEFINED = 0;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED = 210;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__ACTIVIY_IMPORTANCE__IMPORTANCE_BACKGROUND */
    public static final int f2159xaf1c8ac3 = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__ACTIVIY_IMPORTANCE__IMPORTANCE_FORGROUND_SERVICE */
    public static final int f2160xf4283991 = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__ACTIVIY_IMPORTANCE__IMPORTANCE_TOP */
    public static final int f2161x95236940 = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__ACTIVIY_IMPORTANCE__IMPORTANCE_UNKNOWN */
    public static final int f2162x49dc5eb5 = 0;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__API_IN_USE__API_ADD_GNSS_MEASUREMENTS_LISTENER */
    public static final int f2163x597dcbcb = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__API_IN_USE__API_REGISTER_GNSS_STATUS_CALLBACK */
    public static final int f2164x2564ebed = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__API_IN_USE__API_REQUEST_GEOFENCE */
    public static final int f2165xc241e159 = 4;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__API_IN_USE__API_REQUEST_LOCATION_UPDATES */
    public static final int f2166x2c3edaf9 = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__API_IN_USE__API_SEND_EXTRA_COMMAND */
    public static final int f2167x1e65512e = 5;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__API_IN_USE__API_UNKNOWN = 0;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_BETWEEN_0_AND_20_SEC */
    public static final int f2168xcdd0ffc = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_BETWEEN_10_MIN_AND_1_HOUR */
    public static final int f2169x2ad5ddd2 = 4;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_BETWEEN_1_MIN_AND_10_MIN */
    public static final int f2170x6752b56a = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_BETWEEN_20_SEC_AND_1_MIN */
    public static final int f2171x504245a2 = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_LARGER_THAN_1_HOUR */
    public static final int f2172x589914c6 = 5;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_NO_EXPIRY */
    public static final int f2173x4dd574d3 = 6;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_EXPIRE_IN__EXPIRATION_UNKNOWN */
    public static final int f2174xe487840c = 0;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_BETWEEN_0_SEC_AND_1_SEC */
    public static final int f2175x29961d25 = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_BETWEEN_10_MIN_AND_1_HOUR */
    public static final int f2176x716c342e = 5;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_BETWEEN_1_MIN_AND_10_MIN */
    public static final int f2177xabaa238e = 4;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_BETWEEN_1_SEC_AND_5_SEC */
    public static final int f2178x83a9e56a = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_BETWEEN_5_SEC_AND_1_MIN */
    public static final int f2179xeadf1a6b = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_LARGER_THAN_1_HOUR */
    public static final int f2180xd616edea = 6;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_INTERVAL__INTERVAL_UNKNOWN */
    public static final int f2181x52b5f968 = 0;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_BETWEEN_0_AND_100 */
    public static final int f2182x5201395b = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_BETWEEN_1000_AND_10000 */
    public static final int f2183x658f84e8 = 5;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_BETWEEN_100_AND_200 */
    public static final int f2184xe4d5ed9d = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_BETWEEN_200_AND_300 */
    public static final int f2185x79baa41f = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_BETWEEN_300_AND_1000 */
    public static final int f2186xc54a9c92 = 4;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_LARGER_THAN_100000 */
    public static final int f2187x5f8a87bd = 6;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_NEGATIVE */
    public static final int f2188xf790d69d = 7;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_RADIUS__RADIUS_UNKNOWN */
    public static final int f2189xd43470c2 = 0;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_SMALLEST_DISPLACEMENT__DISTANCE_BETWEEN_0_AND_100 */
    public static final int f2190xe0b7c4dd = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_SMALLEST_DISPLACEMENT__DISTANCE_LARGER_THAN_100 */
    public static final int f2191x1a1a6995 = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_SMALLEST_DISPLACEMENT__DISTANCE_UNKNOWN */
    public static final int f2192xa23916c4 = 0;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__BUCKETIZED_SMALLEST_DISPLACEMENT__DISTANCE_ZERO */
    public static final int f2193x468e2e4e = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__CALLBACK_TYPE__CALLBACK_LISTENER */
    public static final int f2194xec262eec = 2;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__CALLBACK_TYPE__CALLBACK_NOT_APPLICABLE */
    public static final int f2195xb3e878e3 = 1;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__CALLBACK_TYPE__CALLBACK_PENDING_INTENT */
    public static final int f2196x4cf3e9c = 3;

    /* renamed from: LOCATION_MANAGER_API_USAGE_REPORTED__CALLBACK_TYPE__CALLBACK_UNKNOWN */
    public static final int f2197x259996f2 = 0;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__PROVIDER__PROVIDER_FUSED = 4;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__PROVIDER__PROVIDER_GPS = 2;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__PROVIDER__PROVIDER_NETWORK = 1;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__PROVIDER__PROVIDER_PASSIVE = 3;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__PROVIDER__PROVIDER_UNKNOWN = 0;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__ACCURACY_BLOCK = 102;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__ACCURACY_CITY = 104;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__ACCURACY_FINE = 100;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__POWER_HIGH = 203;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__POWER_LOW = 201;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__POWER_NONE = 200;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__QUALITY__QUALITY_UNKNOWN = 0;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__STATE__USAGE_ENDED = 1;
    public static final int LOCATION_MANAGER_API_USAGE_REPORTED__STATE__USAGE_STARTED = 0;
    public static final int LONG_PARTIAL_WAKELOCK_STATE_CHANGED = 11;
    public static final int LONG_PARTIAL_WAKELOCK_STATE_CHANGED__STATE__OFF = 0;
    public static final int LONG_PARTIAL_WAKELOCK_STATE_CHANGED__STATE__ON = 1;
    public static final int LOOPER_STATS = 10024;
    public static final int LOW_MEM_REPORTED = 81;
    public static final int LOW_STORAGE_STATE_CHANGED = 130;
    public static final int LOW_STORAGE_STATE_CHANGED__STATE__OFF = 1;
    public static final int LOW_STORAGE_STATE_CHANGED__STATE__ON = 2;
    public static final int LOW_STORAGE_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int MEDIAMETRICS_AUDIOPOLICY_REPORTED = 191;
    public static final int MEDIAMETRICS_AUDIORECORD_REPORTED = 192;
    public static final int MEDIAMETRICS_AUDIOTHREAD_REPORTED = 193;
    public static final int MEDIAMETRICS_AUDIOTRACK_REPORTED = 194;
    public static final int MEDIAMETRICS_CODEC_REPORTED = 195;
    public static final int MEDIAMETRICS_DRM_WIDEVINE_REPORTED = 196;
    public static final int MEDIAMETRICS_EXTRACTOR_REPORTED = 197;
    public static final int MEDIAMETRICS_MEDIADRM_REPORTED = 198;
    public static final int MEDIAMETRICS_NUPLAYER_REPORTED = 199;
    public static final int MEDIAMETRICS_RECORDER_REPORTED = 200;
    public static final int MEDIA_CODEC_STATE_CHANGED = 24;
    public static final int MEDIA_CODEC_STATE_CHANGED__STATE__OFF = 0;
    public static final int MEDIA_CODEC_STATE_CHANGED__STATE__ON = 1;
    public static final int MEDIA_CODEC_STATE_CHANGED__STATE__RESET = 2;
    public static final int MEMORY_FACTOR_STATE_CHANGED = 15;
    public static final int MEMORY_FACTOR_STATE_CHANGED__FACTOR__CRITICAL = 4;
    public static final int MEMORY_FACTOR_STATE_CHANGED__FACTOR__LOW = 3;
    public static final int MEMORY_FACTOR_STATE_CHANGED__FACTOR__MEMORY_UNKNOWN = 0;
    public static final int MEMORY_FACTOR_STATE_CHANGED__FACTOR__MODERATE = 2;
    public static final int MEMORY_FACTOR_STATE_CHANGED__FACTOR__NORMAL = 1;
    public static final int MOBILE_BYTES_TRANSFER = 10002;
    public static final int MOBILE_BYTES_TRANSFER_BY_FG_BG = 10003;
    public static final int MOBILE_CONNECTION_STATE_CHANGED = 75;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__ACTIVATING = 2;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__ACTIVE = 3;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__DISCONNECTING = 4;

    /* renamed from: MOBILE_CONNECTION_STATE_CHANGED__STATE__DISCONNECTION_ERROR_CREATING_CONNECTION */
    public static final int f2198x2f245b6b = 5;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__INACTIVE = 1;
    public static final int MOBILE_CONNECTION_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int MOBILE_RADIO_POWER_STATE_CHANGED = 12;

    /* renamed from: MOBILE_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_HIGH */
    public static final int f2199x40fa71dc = 3;

    /* renamed from: MOBILE_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_LOW */
    public static final int f2200x3be71bba = 1;

    /* renamed from: MOBILE_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_MEDIUM */
    public static final int f2201xf473f92f = 2;

    /* renamed from: MOBILE_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_UNKNOWN */
    public static final int f2202x50fd79b0 = Integer.MAX_VALUE;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED = 76;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_1XRTT = 7;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_CDMA = 4;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EDGE = 2;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EHRPD = 14;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EVDO_0 = 5;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EVDO_A = 6;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_EVDO_B = 12;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_GPRS = 1;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_GSM = 16;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSDPA = 8;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSPA = 10;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSPAP = 15;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_HSUPA = 9;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_IDEN = 11;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_IWLAN = 18;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_LTE = 13;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_LTE_CA = 19;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_NR = 20;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_TD_SCDMA = 17;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_UMTS = 3;
    public static final int MOBILE_RADIO_TECHNOLOGY_CHANGED__STATE__NETWORK_TYPE_UNKNOWN = 0;
    public static final int MODEM_ACTIVITY_INFO = 10012;
    public static final int NATIVE_PROCESS_MEMORY_STATE = 10036;
    public static final int NETWORK_DNS_EVENT_REPORTED = 116;
    public static final int NETWORK_DNS_EVENT_REPORTED__EVENT_TYPE__EVENT_GETADDRINFO = 1;
    public static final int NETWORK_DNS_EVENT_REPORTED__EVENT_TYPE__EVENT_GETHOSTBYADDR = 3;
    public static final int NETWORK_DNS_EVENT_REPORTED__EVENT_TYPE__EVENT_GETHOSTBYNAME = 2;
    public static final int NETWORK_DNS_EVENT_REPORTED__EVENT_TYPE__EVENT_RES_NSEND = 4;
    public static final int NETWORK_DNS_EVENT_REPORTED__EVENT_TYPE__EVENT_UNKNOWN = 0;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_BLUETOOTH = 2;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_DEFAULT = 0;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_ETHERNET = 3;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_LOWPAN = 6;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_VPN = 4;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_WIFI = 1;
    public static final int NETWORK_DNS_EVENT_REPORTED__NETWORK_TYPE__TRANSPORT_WIFI_AWARE = 5;
    public static final int NETWORK_DNS_EVENT_REPORTED__PRIVATE_DNS_MODES__PDM_OFF = 1;
    public static final int NETWORK_DNS_EVENT_REPORTED__PRIVATE_DNS_MODES__PDM_OPPORTUNISTIC = 2;
    public static final int NETWORK_DNS_EVENT_REPORTED__PRIVATE_DNS_MODES__PDM_STRICT = 3;
    public static final int NETWORK_DNS_EVENT_REPORTED__PRIVATE_DNS_MODES__PDM_UNKNOWN = 0;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_ADDRFAMILY = 1;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_AGAIN = 2;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_BADFLAGS = 3;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_BADHINTS = 12;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_FAIL = 4;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_FAMILY = 5;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_MAX = 256;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_MEMORY = 6;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_NODATA = 7;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_NONAME = 8;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_NO_ERROR = 0;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_OVERFLOW = 14;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_PROTOCOL = 13;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_SERVICE = 9;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_SOCKTYPE = 10;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_EAI_SYSTEM = 11;
    public static final int NETWORK_DNS_EVENT_REPORTED__RETURN_CODE__RC_RESOLV_TIMEOUT = 255;
    public static final int NETWORK_STACK_REPORTED = 182;
    public static final int NFC_BEAM_OCCURRED = 136;
    public static final int NFC_BEAM_OCCURRED__OPERATION__RECEIVE = 2;
    public static final int NFC_BEAM_OCCURRED__OPERATION__SEND = 1;
    public static final int NFC_BEAM_OCCURRED__OPERATION__UNKNOWN = 0;
    public static final int NFC_CARDEMULATION_OCCURRED = 137;
    public static final int NFC_CARDEMULATION_OCCURRED__CATEGORY__HCE_OTHER = 2;
    public static final int NFC_CARDEMULATION_OCCURRED__CATEGORY__HCE_PAYMENT = 1;
    public static final int NFC_CARDEMULATION_OCCURRED__CATEGORY__OFFHOST = 3;
    public static final int NFC_CARDEMULATION_OCCURRED__CATEGORY__UNKNOWN = 0;
    public static final int NFC_ERROR_OCCURRED = 134;
    public static final int NFC_ERROR_OCCURRED__TYPE__AID_OVERFLOW = 3;
    public static final int NFC_ERROR_OCCURRED__TYPE__CMD_TIMEOUT = 1;
    public static final int NFC_ERROR_OCCURRED__TYPE__ERROR_NOTIFICATION = 2;
    public static final int NFC_ERROR_OCCURRED__TYPE__UNKNOWN = 0;
    public static final int NFC_HCE_TRANSACTION_OCCURRED = 139;
    public static final int NFC_STATE_CHANGED = 135;
    public static final int NFC_STATE_CHANGED__STATE__CRASH_RESTART = 4;
    public static final int NFC_STATE_CHANGED__STATE__OFF = 1;
    public static final int NFC_STATE_CHANGED__STATE__ON = 2;
    public static final int NFC_STATE_CHANGED__STATE__ON_LOCKED = 3;
    public static final int NFC_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int NFC_TAG_OCCURRED = 138;
    public static final int NFC_TAG_OCCURRED__TYPE__APP_LAUNCH = 5;
    public static final int NFC_TAG_OCCURRED__TYPE__BT_PAIRING = 2;
    public static final int NFC_TAG_OCCURRED__TYPE__OTHERS = 6;
    public static final int NFC_TAG_OCCURRED__TYPE__PROVISION = 3;
    public static final int NFC_TAG_OCCURRED__TYPE__UNKNOWN = 0;
    public static final int NFC_TAG_OCCURRED__TYPE__URL = 1;
    public static final int NFC_TAG_OCCURRED__TYPE__WIFI_CONNECT = 4;
    public static final int NUM_FACES_ENROLLED = 10048;
    public static final int NUM_FINGERPRINTS_ENROLLED = 10031;
    public static final int ON_DEVICE_POWER_MEASUREMENT = 10038;
    public static final int OVERLAY_STATE_CHANGED = 59;
    public static final int OVERLAY_STATE_CHANGED__STATE__ENTERED = 1;
    public static final int OVERLAY_STATE_CHANGED__STATE__EXITED = 2;
    public static final int PACKET_WAKEUP_OCCURRED = 44;
    public static final int PERMISSION_APPS_FRAGMENT_VIEWED = 218;
    public static final int PERMISSION_APPS_FRAGMENT_VIEWED__CATEGORY__ALLOWED = 1;
    public static final int PERMISSION_APPS_FRAGMENT_VIEWED__CATEGORY__ALLOWED_FOREGROUND = 2;
    public static final int PERMISSION_APPS_FRAGMENT_VIEWED__CATEGORY__DENIED = 3;
    public static final int PERMISSION_APPS_FRAGMENT_VIEWED__CATEGORY__UNDEFINED = 0;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED = 170;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__AUTO_DENIED = 8;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__AUTO_GRANTED = 5;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__IGNORED = 1;

    /* renamed from: PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__IGNORED_POLICY_FIXED */
    public static final int f2203xf164f543 = 3;

    /* renamed from: PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__IGNORED_RESTRICTED_PERMISSION */
    public static final int f2204xc3368877 = 9;

    /* renamed from: PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__IGNORED_USER_FIXED */
    public static final int f2205xcc62c81c = 2;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__UNDEFINED = 0;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__USER_DENIED = 6;

    /* renamed from: PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__USER_DENIED_WITH_PREJUDICE */
    public static final int f2206xea6508bb = 7;
    public static final int PERMISSION_GRANT_REQUEST_RESULT_REPORTED__RESULT__USER_GRANTED = 4;
    public static final int PHONE_SERVICE_STATE_CHANGED = 94;

    /* renamed from: PHONE_SERVICE_STATE_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GOOD */
    public static final int f2207x350c37d1 = 3;

    /* renamed from: PHONE_SERVICE_STATE_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GREAT */
    public static final int f2208x6c7bf9d9 = 4;

    /* renamed from: PHONE_SERVICE_STATE_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_MODERATE */
    public static final int f2209x3e500217 = 2;

    /* renamed from: PHONE_SERVICE_STATE_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_NONE_OR_UNKNOWN */
    public static final int f2210x34069da1 = 0;

    /* renamed from: PHONE_SERVICE_STATE_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_POOR */
    public static final int f2211x35104f36 = 1;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_ABSENT = 1;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_CARD_IO_ERROR = 8;

    /* renamed from: PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_CARD_RESTRICTED */
    public static final int f2212xf7e7f36 = 9;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_LOADED = 10;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_NETWORK_LOCKED = 4;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_NOT_READY = 6;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_PERM_DISABLED = 7;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_PIN_REQUIRED = 2;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_PRESENT = 11;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_PUK_REQUIRED = 3;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_READY = 5;
    public static final int PHONE_SERVICE_STATE_CHANGED__SIM_STATE__SIM_STATE_UNKNOWN = 0;
    public static final int PHONE_SERVICE_STATE_CHANGED__STATE__SERVICE_STATE_EMERGENCY_ONLY = 2;
    public static final int PHONE_SERVICE_STATE_CHANGED__STATE__SERVICE_STATE_IN_SERVICE = 0;
    public static final int PHONE_SERVICE_STATE_CHANGED__STATE__SERVICE_STATE_OUT_OF_SERVICE = 1;
    public static final int PHONE_SERVICE_STATE_CHANGED__STATE__SERVICE_STATE_POWER_OFF = 3;
    public static final int PHONE_SIGNAL_STRENGTH_CHANGED = 40;

    /* renamed from: PHONE_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GOOD */
    public static final int f2213x66af65c0 = 3;

    /* renamed from: PHONE_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GREAT */
    public static final int f2214x6f3e89ca = 4;

    /* renamed from: PHONE_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_MODERATE */
    public static final int f2215x6dbca086 = 2;

    /* renamed from: PHONE_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_NONE_OR_UNKNOWN */
    public static final int f2216x176eb452 = 0;

    /* renamed from: PHONE_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_POOR */
    public static final int f2217x66b37d25 = 1;
    public static final int PHONE_STATE_CHANGED = 95;
    public static final int PHONE_STATE_CHANGED__STATE__OFF = 0;
    public static final int PHONE_STATE_CHANGED__STATE__ON = 1;
    public static final int PHYSICAL_DROP_DETECTED = 73;
    public static final int PICTURE_IN_PICTURE_STATE_CHANGED = 52;
    public static final int PICTURE_IN_PICTURE_STATE_CHANGED__STATE__DISMISSED = 4;
    public static final int PICTURE_IN_PICTURE_STATE_CHANGED__STATE__ENTERED = 1;
    public static final int PICTURE_IN_PICTURE_STATE_CHANGED__STATE__EXPANDED_TO_FULL_SCREEN = 2;
    public static final int PICTURE_IN_PICTURE_STATE_CHANGED__STATE__MINIMIZED = 3;
    public static final int PLUGGED_STATE_CHANGED = 32;
    public static final int PLUGGED_STATE_CHANGED__STATE__BATTERY_PLUGGED_AC = 1;
    public static final int PLUGGED_STATE_CHANGED__STATE__BATTERY_PLUGGED_NONE = 0;
    public static final int PLUGGED_STATE_CHANGED__STATE__BATTERY_PLUGGED_USB = 2;
    public static final int PLUGGED_STATE_CHANGED__STATE__BATTERY_PLUGGED_WIRELESS = 4;
    public static final int POWER_PROFILE = 10033;
    public static final int PRIVACY_INDICATORS_INTERACTED = 180;
    public static final int PRIVACY_INDICATORS_INTERACTED__TYPE__CHIP_CLICKED = 2;
    public static final int PRIVACY_INDICATORS_INTERACTED__TYPE__CHIP_VIEWED = 1;
    public static final int PRIVACY_INDICATORS_INTERACTED__TYPE__DIALOG_DISMISS = 4;
    public static final int PRIVACY_INDICATORS_INTERACTED__TYPE__DIALOG_LINE_ITEM = 5;
    public static final int PRIVACY_INDICATORS_INTERACTED__TYPE__DIALOG_PRIVACY_SETTINGS = 3;
    public static final int PRIVACY_INDICATORS_INTERACTED__TYPE__UNKNOWN = 0;
    public static final int PROCESS_CPU_TIME = 10035;
    public static final int PROCESS_LIFE_CYCLE_STATE_CHANGED = 28;
    public static final int PROCESS_LIFE_CYCLE_STATE_CHANGED__STATE__CRASHED = 2;
    public static final int PROCESS_LIFE_CYCLE_STATE_CHANGED__STATE__FINISHED = 0;
    public static final int PROCESS_LIFE_CYCLE_STATE_CHANGED__STATE__STARTED = 1;
    public static final int PROCESS_MEMORY_HIGH_WATER_MARK = 10042;
    public static final int PROCESS_MEMORY_STATE = 10013;
    public static final int PROCESS_MEMORY_STAT_REPORTED = 18;
    public static final int PROCESS_MEMORY_STAT_REPORTED__TYPE__ADD_PSS_EXTERNAL = 3;
    public static final int PROCESS_MEMORY_STAT_REPORTED__TYPE__ADD_PSS_EXTERNAL_SLOW = 4;
    public static final int PROCESS_MEMORY_STAT_REPORTED__TYPE__ADD_PSS_INTERNAL_ALL_MEM = 1;
    public static final int PROCESS_MEMORY_STAT_REPORTED__TYPE__ADD_PSS_INTERNAL_ALL_POLL = 2;
    public static final int PROCESS_MEMORY_STAT_REPORTED__TYPE__ADD_PSS_INTERNAL_SINGLE = 0;
    public static final int PROCESS_START_TIME = 169;
    public static final int PROCESS_START_TIME__TYPE__COLD = 3;
    public static final int PROCESS_START_TIME__TYPE__HOT = 2;
    public static final int PROCESS_START_TIME__TYPE__UNKNOWN = 0;
    public static final int PROCESS_START_TIME__TYPE__WARM = 1;
    public static final int PROCESS_STATE_CHANGED = 3;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_BACKUP = 1008;

    /* renamed from: PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_BOUND_FOREGROUND_SERVICE */
    public static final int f2218x76ba1f3 = 1004;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_BOUND_TOP = 1020;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_ACTIVITY = 1015;

    /* renamed from: PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_ACTIVITY_CLIENT */
    public static final int f2219x721a15f7 = 1016;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_EMPTY = 1018;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_RECENT = 1017;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_FOREGROUND_SERVICE = 1003;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_HEAVY_WEIGHT = 1012;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_HOME = 1013;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_IMPORTANT_BACKGROUND = 1006;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_IMPORTANT_FOREGROUND = 1005;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_LAST_ACTIVITY = 1014;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_NONEXISTENT = 1019;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_PERSISTENT = 1000;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_PERSISTENT_UI = 1001;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_RECEIVER = 1010;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_SERVICE = 1009;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_TOP = 1002;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_TOP_SLEEPING = 1011;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_TRANSIENT_BACKGROUND = 1007;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_UNKNOWN = 999;
    public static final int PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_UNKNOWN_TO_PROTO = 998;
    public static final int PROCESS_SYSTEM_ION_HEAP_SIZE = 10061;
    public static final int PROC_STATS = 10029;
    public static final int PROC_STATS_PKG_PROC = 10034;
    public static final int REMAINING_BATTERY_CAPACITY = 10019;
    public static final int RESCUE_PARTY_RESET_REPORTED = 122;
    public static final int RESOURCE_CONFIGURATION_CHANGED = 66;
    public static final int REVIEW_PERMISSIONS_FRAGMENT_RESULT_REPORTED = 211;
    public static final int ROLE_HOLDER = 10049;
    public static final int ROLE_REQUEST_RESULT_REPORTED = 190;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__IGNORED = 1;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__IGNORED_ALREADY_GRANTED = 2;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__IGNORED_NOT_QUALIFIED = 3;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__IGNORED_USER_ALWAYS_DENIED = 4;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__UNDEFINED = 0;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__USER_DENIED = 6;

    /* renamed from: ROLE_REQUEST_RESULT_REPORTED__RESULT__USER_DENIED_GRANTED_ANOTHER */
    public static final int f2220x930a2236 = 7;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__USER_DENIED_WITH_ALWAYS = 8;
    public static final int ROLE_REQUEST_RESULT_REPORTED__RESULT__USER_GRANTED = 5;
    public static final int RUNTIME_PERMISSIONS_UPGRADE_RESULT = 212;
    public static final int SCHEDULED_JOB_CONSTRAINT_CHANGED = 150;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_BACKGROUND_NOT_RESTRICTED */
    public static final int f2221xa620b119 = 11;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_BATTERY_NOT_LOW */
    public static final int f2222xfaffdd17 = 2;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_CHARGING */
    public static final int f2223xa2393d0 = 1;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_CONNECTIVITY */
    public static final int f2224x89d8cb96 = 7;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_CONTENT_TRIGGER */
    public static final int f2225xa34c2573 = 8;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_DEADLINE */
    public static final int f2226xd28f0bb7 = 5;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_DEVICE_NOT_DOZING */
    public static final int f2227x1862aa49 = 9;
    public static final int SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_IDLE = 6;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_STORAGE_NOT_LOW */
    public static final int f2228x96022385 = 3;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_TIMING_DELAY */
    public static final int f2229x42a3a30d = 4;
    public static final int SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_UNKNOWN = 0;

    /* renamed from: SCHEDULED_JOB_CONSTRAINT_CHANGED__CONSTRAINT__CONSTRAINT_WITHIN_QUOTA */
    public static final int f2230xdf1a7f63 = 10;
    public static final int SCHEDULED_JOB_CONSTRAINT_CHANGED__STATE__SATISFIED = 2;
    public static final int SCHEDULED_JOB_CONSTRAINT_CHANGED__STATE__UNKNOWN = 0;
    public static final int SCHEDULED_JOB_CONSTRAINT_CHANGED__STATE__UNSATISFIED = 1;
    public static final int SCHEDULED_JOB_STATE_CHANGED = 8;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STANDBY_BUCKET__ACTIVE = 0;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STANDBY_BUCKET__FREQUENT = 2;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STANDBY_BUCKET__NEVER = 4;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STANDBY_BUCKET__RARE = 3;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STANDBY_BUCKET__UNKNOWN = -1;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STANDBY_BUCKET__WORKING_SET = 1;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STATE__FINISHED = 0;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STATE__SCHEDULED = 2;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STATE__STARTED = 1;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_CANCELLED = 0;

    /* renamed from: SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_CONSTRAINTS_NOT_SATISFIED */
    public static final int f2231xa606b0b0 = 1;

    /* renamed from: SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_DEVICE_IDLE */
    public static final int f2232x58c30af0 = 4;

    /* renamed from: SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_DEVICE_THERMAL */
    public static final int f2233x9db22adb = 5;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_PREEMPT = 2;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_TIMEOUT = 3;
    public static final int SCHEDULED_JOB_STATE_CHANGED__STOP_REASON__STOP_REASON_UNKNOWN = -1;
    public static final int SCREEN_BRIGHTNESS_CHANGED = 9;
    public static final int SCREEN_STATE_CHANGED = 29;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_DOZE = 3;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_DOZE_SUSPEND = 4;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_OFF = 1;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_ON = 2;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_ON_SUSPEND = 6;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_UNKNOWN = 0;
    public static final int SCREEN_STATE_CHANGED__STATE__DISPLAY_STATE_VR = 5;
    public static final int SCREEN_TIMEOUT_EXTENSION_REPORTED = 168;
    public static final int SENSOR_STATE_CHANGED = 5;
    public static final int SENSOR_STATE_CHANGED__STATE__OFF = 0;
    public static final int SENSOR_STATE_CHANGED__STATE__ON = 1;
    public static final int SERVICE_LAUNCH_REPORTED = 100;
    public static final int SERVICE_STATE_CHANGED = 99;
    public static final int SERVICE_STATE_CHANGED__STATE__START = 1;
    public static final int SERVICE_STATE_CHANGED__STATE__STOP = 2;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_AIRPLANE_TOGGLE = 177;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_AMBIENT_DISPLAY = 495;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ANOMALY_IGNORED = 1387;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ANOMALY_TRIGGERED = 1367;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_APP_FORCE_STOP = 807;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_APP_RESTRICTION_TIP = 1347;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_APP_RESTRICTION_TIP_LIST = 1353;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATCGIB = 1677;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATCHNUC = 1681;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATCLPB = 1676;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATCPAB = 1678;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATCSAUC = 1679;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATCSCUC = 1680;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATPG = 1675;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ATSG = 1674;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_BLUETOOTH_FILES = 162;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_BLUETOOTH_RENAME = 161;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_BUGREPORT_FROM_SETTINGS_FULL = 295;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_BUGREPORT_FROM_SETTINGS_INTERACTIVE */
    public static final int f2234x2cb7a2f5 = 294;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CELL_DATA_TOGGLE = 178;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_CLICK = 1666;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_DISMISS = 1665;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_ELIGIBILITY = 1686;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_LOAD = 1684;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_LOAD_TIMEOUT = 1685;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_NOT_SHOW = 1664;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_CARD_SHOW = 1663;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_CONTEXTUAL_HOME_SHOW = 1662;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_DATA_SAVER_BLACKLIST = 396;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_DATA_SAVER_MODE = 394;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_DATA_SAVER_WHITELIST = 395;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_DISPLAY_WHITE_BALANCE_SETTING_CHANGED */
    public static final int f2235xe38dc080 = 1703;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_EARLY_WARNING_TIP = 1351;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_FINGERPRINT_DELETE = 253;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_FINGERPRINT_RENAME = 254;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_HIGH_USAGE_TIP = 1348;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_HIGH_USAGE_TIP_LIST = 1354;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_LOW_BATTERY_TIP = 1352;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_MOBILE_NETWORK_MANUAL_SELECT_NETWORK */
    public static final int f2236x394f7965 = 1210;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_NFC_PAYMENT_ALWAYS_SETTING = 1623;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_NFC_PAYMENT_FOREGROUND_SETTING */
    public static final int f2237xebb174f0 = 1622;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_OPEN_APP_NOTIFICATION_SETTING = 1016;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_OPEN_APP_SETTING = 1017;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_PANEL_INTERACTION = 1658;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_PRIVATE_DNS_MODE = 1249;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_PSD_LOADER = 1019;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ROTATION_LOCK = 203;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SEARCH_RESULTS = 226;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_ADVANCED_BUTTON_EXPAND */
    public static final int f2238xc89f28d7 = 834;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_BLUETOOTH_CONNECT = 867;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_BLUETOOTH_CONNECT_ERROR */
    public static final int f2239x5c1189f5 = 869;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_BLUETOOTH_DISCONNECT = 868;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_BLUETOOTH_PAIR = 866;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_BLUETOOTH_PAIR_DEVICES_WITHOUT_NAMES */
    public static final int f2240x39016ca8 = 1096;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_BUILD_NUMBER_PREF = 847;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CLEAR_APP_CACHE = 877;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CLEAR_APP_DATA = 876;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CLEAR_INSTANT_APP = 923;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CONDITION_BUTTON = 376;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CONDITION_CLICK = 375;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CONDITION_EXPAND = 373;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_CREATE_SHORTCUT = 829;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_DISABLE_APP = 874;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_ENABLE_APP = 875;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_ENROLL_WIFI_QR_CODE = 1711;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_MASTER_SWITCH_BLUETOOTH_TOGGLE */
    public static final int f2241x6b3ade0 = 870;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_MENU_BATTERY_APPS_TOGGLE */
    public static final int f2242x7c12fa3c = 852;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_MENU_BATTERY_OPTIMIZATION */
    public static final int f2243xf739b452 = 851;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_PREFERENCE_CHANGE = 853;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_SHARE_WIFI_HOTSPOT_QR_CODE */
    public static final int f2244xf67dd27e = 1712;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_SHARE_WIFI_QR_CODE = 1710;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_SLICE_CHANGED = 1372;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_SLICE_REQUESTED = 1371;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_UNINSTALL_APP = 872;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_UNINSTALL_DEVICE_ADMIN */
    public static final int f2245xbb5cae50 = 873;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SETTINGS_UPDATE_DEFAULT_APP = 1000;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_SET_NEW_PARENT_PROFILE_PASSWORD */
    public static final int f2246xf45c259b = 1646;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SET_NEW_PASSWORD = 1645;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SMART_BATTERY_TIP = 1350;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_BENCHMARK_FAST_CONTINUE */
    public static final int f2247x4efb81b5 = 1409;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_BENCHMARK_SLOW_ABORT = 1411;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_BENCHMARK_SLOW_CONTINUE */
    public static final int f2248x498e79b0 = 1410;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_INIT_EXTERNAL = 1407;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_INIT_INTERNAL = 1408;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_MIGRATE_LATER = 1413;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_STORAGE_MIGRATE_NOW = 1412;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_SUMMARY_TIP = 1349;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_THEME = 816;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TIP_OPEN_APP_RESTRICTION_PAGE = 1361;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TIP_OPEN_BATTERY_SAVER_PAGE = 1388;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TIP_OPEN_SMART_BATTERY = 1364;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TIP_RESTRICT_APP = 1362;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TIP_TURN_ON_BATTERY_SAVER = 1365;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TIP_UNRESTRICT_APP = 1363;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_TOGGLE_STORAGE_MANAGER = 489;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_UNKNOWN = 0;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_VERIFY_SLICE_ERROR_INVALID_DATA */
    public static final int f2249xcb46e14d = 1725;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_VERIFY_SLICE_OTHER_EXCEPTION = 1727;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_VERIFY_SLICE_PARSING_ERROR = 1726;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_WIFI_CONNECT = 135;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_WIFI_FORGET = 137;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_WIFI_OFF = 138;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_WIFI_ON = 139;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_WIFI_SIGNIN = 1008;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_ALARMS = 1226;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_CALLS = 170;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_EVENTS = 168;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_MEDIA = 1227;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_MESSAGES = 169;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_REMINDERS = 167;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_REPEAT_CALLS = 171;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ALLOW_SYSTEM = 1340;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_AMBIENT = 1337;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_BADGE = 1336;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_FULL_SCREEN_INTENTS = 1332;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_LIGHT = 1333;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_NOTIFICATION_LIST = 1338;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_PEEK = 1334;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_BLOCK_STATUS = 1335;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_CUSTOM = 1399;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_DELETE_RULE_OK = 175;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_MODE_RULE_NAME_CHANGE_OK = 1267;

    /* renamed from: SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ONBOARDING_KEEP_CURRENT_SETTINGS */
    public static final int f2250x5808f12a = 1406;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ONBOARDING_OK = 1378;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_ONBOARDING_SETTINGS = 1379;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_SHOW_CUSTOM = 1398;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_SOUND_AND_VIS_EFFECTS = 1397;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_SOUND_ONLY = 1396;
    public static final int SETTINGS_UICHANGED__ACTION__ACTION_ZEN_TOGGLE_DND_BUTTON = 1268;
    public static final int SETTINGS_UICHANGED__ACTION__APP_PICTURE_IN_PICTURE_ALLOW = 813;
    public static final int SETTINGS_UICHANGED__ACTION__APP_PICTURE_IN_PICTURE_DENY = 814;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_ADMIN_ALLOW = 766;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_ADMIN_DENY = 767;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_APPDRAW_ALLOW = 770;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_APPDRAW_DENY = 771;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_BATTERY_ALLOW = 764;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_BATTERY_DENY = 765;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_DND_ALLOW = 768;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_DND_DENY = 769;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_NOTIVIEW_ALLOW */
    public static final int f2251x27dd1b77 = 776;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_NOTIVIEW_DENY = 777;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_PREMIUM_SMS_ALWAYS_ALLOW */
    public static final int f2252x5584eb9 = 780;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_PREMIUM_SMS_ASK */
    public static final int f2253xb14b4499 = 778;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_PREMIUM_SMS_DENY */
    public static final int f2254x781e77cc = 779;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_SETTINGS_CHANGE_ALLOW */
    public static final int f2255xf185ae44 = 774;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_SETTINGS_CHANGE_DENY */
    public static final int f2256xf747a391 = 775;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_UNL_DATA_ALLOW */
    public static final int f2257xaf9fe732 = 781;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_UNL_DATA_DENY = 782;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_USAGE_VIEW_ALLOW */
    public static final int f2258x1baabfff = 783;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_USAGE_VIEW_DENY */
    public static final int f2259x19abee76 = 784;

    /* renamed from: SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_VRHELPER_ALLOW */
    public static final int f2260xccef6606 = 772;
    public static final int SETTINGS_UICHANGED__ACTION__APP_SPECIAL_PERMISSION_VRHELPER_DENY = 773;
    public static final int SETTINGS_UICHANGED__ACTION__CLICK_ACCOUNT_AVATAR = 1643;
    public static final int SETTINGS_UICHANGED__ACTION__DIALOG_SWITCH_A2DP_DEVICES = 1415;
    public static final int SETTINGS_UICHANGED__ACTION__DIALOG_SWITCH_HFP_DEVICES = 1416;
    public static final int SETTINGS_UICHANGED__ACTION__PAGE_HIDE = 2;
    public static final int SETTINGS_UICHANGED__ACTION__PAGE_VISIBLE = 1;
    public static final int SETTINGS_UICHANGED__ACTION__QS_SENSOR_PRIVACY = 1598;

    /* renamed from: SETTINGS_UICHANGED__ACTION__SETTINGS_ASSIST_GESTURE_TRAINING_ENROLLING */
    public static final int f2261xba0700e1 = 992;

    /* renamed from: SETTINGS_UICHANGED__ACTION__SETTINGS_ASSIST_GESTURE_TRAINING_FINISHED */
    public static final int f2262x366b84d = 993;

    /* renamed from: SETTINGS_UICHANGED__ACTION__SETTINGS_ASSIST_GESTURE_TRAINING_INTRO */
    public static final int f2263xfceaec51 = 991;
    public static final int SETTINGS_UICHANGED__ACTION__TRAMPOLINE_SETTINGS_EVENT = 1033;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ABOUT_LEGAL_SETTINGS = 225;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY = 2;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_CAPTION_PROPERTIES */
    public static final int f2264x3f8c6bb8 = 3;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_DETAILS_SETTINGS = 1682;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_FONT_SIZE = 340;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_SCREEN_MAGNIFICATION_SETTINGS */
    public static final int f2265xe61e6d06 = 922;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_SERVICE = 4;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_TOGGLE_AUTOCLICK = 335;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_TOGGLE_DALTONIZER = 5;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_TOGGLE_GLOBAL_GESTURE */
    public static final int f2266x5b1ebeec = 6;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_TOGGLE_SCREEN_MAGNIFICATION */
    public static final int f2267x6e123af = 7;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_VIBRATION = 1292;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_VIBRATION_NOTIFICATION */
    public static final int f2268x2ee2c604 = 1293;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_VIBRATION_RING = 1620;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCESSIBILITY_VIBRATION_TOUCH = 1294;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCOUNT = 8;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCOUNTS_ACCOUNT_SYNC = 9;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ACCOUNTS_CHOOSE_ACCOUNT_ACTIVITY */
    public static final int f2269xfaf58c8b = 10;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ACCOUNTS_WORK_PROFILE_SETTINGS = 401;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APN = 12;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APN_EDITOR = 13;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_ADVANCED = 130;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_APP_LAUNCH = 17;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_APP_STORAGE = 19;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_HIGH_POWER_APPS = 184;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_INSTALLED_APP_DETAILS */
    public static final int f2270xa6a2a75e = 20;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_MANAGE_ASSIST = 201;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_PROCESS_STATS_DETAIL */
    public static final int f2271x76aeb942 = 21;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_PROCESS_STATS_UI = 23;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_STORAGE_APPS = 182;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_STORAGE_GAMES = 838;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_STORAGE_MOVIES = 935;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_STORAGE_MUSIC = 839;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_STORAGE_PHOTOS = 1092;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__APPLICATIONS_USAGE_ACCESS_DETAIL */
    public static final int f2272x3381bbed = 183;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APP_BUBBLE_SETTINGS = 1700;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__APP_DATA_USAGE = 343;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BACKGROUND_CHECK_SUMMARY = 258;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BACKUP_SETTINGS = 818;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BILLING_CYCLE = 342;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BIOMETRIC_ENROLL_ACTIVITY = 1586;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BIOMETRIC_FRAGMENT = 1585;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BLUETOOTH_DEVICE_DETAILS = 1009;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BLUETOOTH_DEVICE_PICKER = 25;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BLUETOOTH_DIALOG_FRAGMENT = 613;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BLUETOOTH_FRAGMENT = 1390;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BLUETOOTH_PAIRING = 1018;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__BUBBLE_SETTINGS = 1699;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CHOOSE_LOCK_GENERIC = 27;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CHOOSE_LOCK_PASSWORD = 28;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CHOOSE_LOCK_PATTERN = 29;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__COLOR_MODE_SETTINGS = 1143;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONFIGURE_KEYGUARD_DIALOG = 1010;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONFIGURE_NOTIFICATION = 337;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONFIGURE_WIFI = 338;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONFIRM_LOCK_PASSWORD = 30;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONFIRM_LOCK_PATTERN = 31;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONNECTION_DEVICE_ADVANCED = 1264;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONVERT_FBE = 402;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CONVERT_FBE_CONFIRM = 403;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CRYPT_KEEPER = 32;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__CRYPT_KEEPER_CONFIRM = 33;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DARK_UI_SETTINGS = 1698;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DASHBOARD_SUMMARY = 35;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DATA_SAVER_SUMMARY = 348;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DATA_USAGE_LIST = 341;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DATA_USAGE_SUMMARY = 37;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DATA_USAGE_UNRESTRICTED_ACCESS = 349;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DATE_TIME = 38;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_APP_PICKER_CONFIRMATION_DIALOG */
    public static final int f2273x67ae15b8 = 791;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_ASSIST_PICKER = 843;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_AUTOFILL_PICKER = 792;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_BROWSER_PICKER = 785;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_EMERGENCY_APP_PICKER = 786;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_HOME_PICKER = 787;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_NOTIFICATION_ASSISTANT = 790;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_PHONE_PICKER = 788;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_SMS_PICKER = 789;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEFAULT_VOICE_INPUT_PICKER = 844;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEVELOPMENT = 39;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEVELOPMENT_QS_TILE_CONFIG = 1224;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEVICEINFO = 40;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEVICEINFO_STORAGE = 42;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DEVICE_ADMIN_SETTINGS = 516;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ACCESSIBILITY_HEARINGAID = 1512;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ACCESSIBILITY_SERVICE_DISABLE */
    public static final int f2274xcc0fdb91 = 584;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ACCESSIBILITY_SERVICE_ENABLE */
    public static final int f2275x3a14af9a = 583;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ACCOUNT_SYNC_CANNOT_ONETIME_SYNC */
    public static final int f2276xb003e54d = 587;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ACCOUNT_SYNC_FAILED_REMOVAL */
    public static final int f2277xd4ebe940 = 586;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ACCOUNT_SYNC_REMOVE = 585;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_APN_EDITOR_ERROR = 530;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_APN_RESTORE_DEFAULT = 579;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_APP_BUBBLE_SETTINGS = 1702;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_APP_INFO_ACTION = 558;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_AWARE_DISABLE = 1633;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_AWARE_STATUS = 1701;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BILLING_BYTE_LIMIT = 550;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BILLING_CONFIRM_LIMIT = 551;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BILLING_CYCLE = 549;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BLUETOOTH_DISABLE_A2DP_HW_OFFLOAD */
    public static final int f2278xa60d1e03 = 1441;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BLUETOOTH_PAIRED_DEVICE_FORGET */
    public static final int f2279xabf10b3d = 1031;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BLUETOOTH_PAIRED_DEVICE_RENAME */
    public static final int f2280xbfdc608e = 1015;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_BLUETOOTH_RENAME = 538;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_CALL_SIM_LIST = 1708;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_CLEAR_ADB_KEYS = 1223;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_CONFIRM_AUTO_SYNC_CHANGE = 535;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_CUSTOM_LIST_CONFIRMATION = 529;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_DARK_UI_INFO = 1740;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_DATE_PICKER = 607;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_DELETE_SIM_CONFIRMATION = 1713;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_DELETE_SIM_PROGRESS = 1714;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_DISABLE_DEVELOPMENT_OPTIONS */
    public static final int f2281x364bbde7 = 1591;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_DISABLE_NOTIFICATION_ACCESS */
    public static final int f2282x4ecd85c5 = 552;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ENABLE_ADB = 1222;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ENABLE_DEVELOPMENT_OPTIONS */
    public static final int f2283x830b6f9a = 1219;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ENABLE_OEM_UNLOCKING = 1220;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ENCRYPTION_INTERSTITIAL_ACCESSIBILITY */
    public static final int f2284x51c1e11b = 581;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FACE_ERROR = 1510;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FACE_REMOVE = 1693;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FINGERPINT_DELETE_LAST = 571;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FINGERPINT_EDIT = 570;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FINGERPINT_ERROR = 569;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FINGERPRINT_ICON_TOUCH = 568;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FINGERPRINT_SKIP_SETUP = 573;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FIRMWARE_VERSION = 1247;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_FRP = 528;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_HIGH_POWER_DETAILS = 540;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_IMEI_INFO = 1240;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_KEYBOARD_LAYOUT = 541;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_LEGACY_VPN_CONFIG = 545;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_LOG_PERSIST = 1225;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_MANAGE_MOBILE_PLAN = 609;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_NIGHT_DISPLAY_SET_END_TIME */
    public static final int f2285x1e040e8e = 589;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_NIGHT_DISPLAY_SET_START_TIME */
    public static final int f2286x9cbe3027 = 588;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_OEM_LOCK_INFO = 1238;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_OWNER_INFO_SETTINGS = 531;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_PREFERRED_SIM_PICKER = 1709;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_PROXY_SELECTOR_ERROR = 574;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_REMOVE_USER = 534;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_RUNNIGN_SERVICE = 536;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_SERVICE_ACCESS_WARNING = 557;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_SETTINGS_HARDWARE_INFO = 862;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_SIM_LIST = 1707;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_SIM_STATUS = 1246;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_STORAGE_CLEAR_CACHE = 564;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_STORAGE_OTHER_INFO = 566;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_STORAGE_SYSTEM_INFO = 565;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_STORAGE_USER_INFO = 567;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_TIME_PICKER = 608;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_UNIFICATION_CONFIRMATION = 532;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_UNIFY_SOUND_SETTINGS = 553;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_ADD = 595;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_CANNOT_MANAGE = 594;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_CHOOSE_TYPE = 598;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_CONFIRM_EXIT_GUEST = 600;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_CREDENTIAL = 533;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_EDIT = 590;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_EDIT_PROFILE = 601;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_ENABLE_CALLING = 592;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_ENABLE_CALLING_AND_SMS */
    public static final int f2287x8fdda8d2 = 593;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_NEED_LOCKSCREEN = 599;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_REMOVE = 591;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_SETUP = 596;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_USER_SETUP_PROFILE = 597;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VOLUME_FORGET = 559;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VOLUME_FORMAT = 1375;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VOLUME_INIT = 561;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VOLUME_RENAME = 563;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VOLUME_UNMOUNT = 562;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VPN_APP_CONFIG = 546;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VPN_CANNOT_CONNECT = 547;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_VPN_REPLACE_EXISTING = 548;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_AP_EDIT = 603;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_P2P_CANCEL_CONNECT = 576;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_P2P_DELETE_GROUP = 578;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_P2P_DISCONNECT = 575;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_P2P_RENAME = 577;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_SAVED_AP_EDIT = 602;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_SCAN_MODE = 543;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_WIFI_WRITE_NFC = 606;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ZEN_ACCESS_GRANT = 554;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ZEN_ACCESS_REVOKE = 555;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DIALOG_ZEN_TIMEPICKER = 556;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DISPLAY = 46;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DISPLAY_SCREEN_ZOOM = 339;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__DREAM = 47;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ENABLE_VIRTUAL_KEYBOARDS = 347;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ENCRYPTION = 48;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ENCRYPTION_AND_CREDENTIAL = 846;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ENTERPRISE_PRIVACY_DEFAULT_APPS = 940;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ENTERPRISE_PRIVACY_INSTALLED_APPS */
    public static final int f2288x93241031 = 938;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ENTERPRISE_PRIVACY_PERMISSIONS = 939;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ENTERPRISE_PRIVACY_SETTINGS = 628;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FACE = 1511;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FACE_ENROLL_ENROLLING = 1507;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FACE_ENROLL_FINISHED = 1508;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FACE_ENROLL_INTRO = 1506;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FACE_ENROLL_PREVIEW = 1554;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FACE_ENROLL_SIDECAR = 1509;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT = 49;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_AUTHENTICATE_SIDECAR */
    public static final int f2289xa8053e9 = 1221;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLLING = 240;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLLING_SETUP = 246;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLL_FINISH = 242;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLL_FINISH_SETUP = 248;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLL_INTRO = 243;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLL_INTRO_SETUP = 249;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_ENROLL_SIDECAR = 245;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_FIND_SENSOR = 241;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_FIND_SENSOR_SETUP = 247;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FINGERPRINT_REMOVE_SIDECAR = 934;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_BATTERY_HISTORY_DETAIL */
    public static final int f2290x9d72c1a5 = 51;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_BATTERY_SAVER = 52;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_BATTERY_TIP_DIALOG = 1323;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_INACTIVE_APPS = 238;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_POWER_USAGE_DETAIL = 53;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_POWER_USAGE_SUMMARY_V2 */
    public static final int f2291x8d58ba24 = 1263;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_RESTRICTED_APP_DETAILS */
    public static final int f2292xe17a8a97 = 1285;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__FUELGAUGE_SMART_BATTERY = 1281;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__GENTLE_NOTIFICATIONS_SCREEN = 1715;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__GLOBAL_ACTIONS_PANEL_SETTINGS = 1728;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ICC_LOCK = 56;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__INPUTMETHOD_KEYBOARD = 58;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__INPUTMETHOD_SPELL_CHECKERS = 59;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__INPUTMETHOD_SUBTYPE_ENABLER = 60;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__INPUTMETHOD_USER_DICTIONARY = 61;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__INPUTMETHOD_USER_DICTIONARY_ADD_WORD */
    public static final int f2293xd6103a0c = 62;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__LOCATION = 63;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__LOCATION_SCANNING = 131;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__LOCK_SCREEN_NOTIFICATION_CONTENT */
    public static final int f2294x8037619f = 1584;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MANAGE_APPLICATIONS = 65;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__MANAGE_APPLICATIONS_NOTIFICATIONS */
    public static final int f2295x25cc116b = 133;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MANAGE_DOMAIN_URLS = 143;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MANAGE_EXTERNAL_SOURCES = 808;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MASTER_CLEAR = 66;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MASTER_CLEAR_CONFIRM = 67;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MOBILE_DATA_DIALOG = 1582;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MOBILE_NETWORK = 1571;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MOBILE_NETWORK_LIST = 1627;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MOBILE_NETWORK_RENAME_DIALOG = 1642;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MOBILE_NETWORK_SELECT = 1581;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MOBILE_ROAMING_DIALOG = 1583;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__MODULE_LICENSES_DASHBOARD = 1746;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NFC_BEAM = 69;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NFC_PAYMENT = 70;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NIGHT_DISPLAY_SETTINGS = 488;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ACCESS = 179;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_APP_NOTIFICATION = 72;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_CHANNEL_GROUP = 1218;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_REDACTION = 74;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_STATION = 75;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_TOPIC_NOTIFICATION = 265;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE = 76;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_ACCESS = 180;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_AUTOMATION */
    public static final int f2296x7f691dde = 142;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_DELETE_RULE_DIALOG */
    public static final int f2297x3091e11e = 1266;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_DURATION_DIALOG */
    public static final int f2298x8e7cc9ec = 1341;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_ENABLE_DIALOG */
    public static final int f2299x414a855d = 1286;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_EVENT_RULE */
    public static final int f2300xa7536668 = 146;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_OVERRIDING_APP */
    public static final int f2301xc3490392 = 1589;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_OVERRIDING_APPS */
    public static final int f2302xa5d76f01 = 1588;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_PRIORITY = 141;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_RULE_NAME_DIALOG */
    public static final int f2303x520e1840 = 1269;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_RULE_SELECTION_DIALOG */
    public static final int f2304xc5665737 = 1270;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__NOTIFICATION_ZEN_MODE_SCHEDULE_RULE */
    public static final int f2305xd8bba57d = 144;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATHNP = 1673;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATSAP = 1671;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATSCP = 1672;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATSII = 1668;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATSSI = 1667;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATSSP = 1670;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_ATUS = 1669;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PAGE_UNKNOWN = 0;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PANEL_INTERNET_CONNECTIVITY = 1654;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PANEL_MEDIA_OUTPUT = 1657;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PANEL_NFC = 1656;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PANEL_VOLUME = 1655;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PANEL_WIFI = 1687;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PHYSICAL_KEYBOARDS = 346;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PREMIUM_SMS_ACCESS = 388;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PREVIOUSLY_CONNECTED_DEVICES = 1370;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PRINT_JOB_SETTINGS = 78;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PRINT_SERVICE_SETTINGS = 79;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PRINT_SETTINGS = 80;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PRIVACY = 81;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PROCESS_STATS_SUMMARY = 202;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__PROXY_SELECTOR = 82;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__RESET_DASHBOARD = 924;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__RESET_NETWORK = 83;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__RESET_NETWORK_CONFIRM = 84;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__RUNNING_SERVICES = 404;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__RUNNING_SERVICE_DETAILS = 85;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SCREEN_LOCK_SETTINGS = 1265;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SCREEN_PINNING = 86;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SECURITY = 87;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ADAPTIVE_SLEEP = 1628;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_APP_NOTIF_CATEGORY = 748;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ASSIST_GESTURE = 996;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_AUTO_BRIGHTNESS = 1381;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_AWARE = 1632;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CHOOSE_LOCK_DIALOG = 990;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_AIRPLANE_MODE */
    public static final int f2306x98c579cb = 377;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_BACKGROUND_DATA */
    public static final int f2307x7911dcb6 = 378;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_BATTERY_SAVER */
    public static final int f2308x8e5388fe = 379;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_CELLULAR_DATA */
    public static final int f2309x1c1184ba = 380;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_DEVICE_MUTED = 1368;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_DEVICE_VIBRATE */
    public static final int f2310x7593146b = 1369;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_DND = 381;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_GRAYSCALE_MODE */
    public static final int f2311xbdfc3640 = 1683;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_HOTSPOT = 382;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_NIGHT_DISPLAY */
    public static final int f2312x71019ab6 = 492;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONDITION_WORK_MODE = 383;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CONNECTED_DEVICE_CATEGORY */
    public static final int f2313xacfce750 = 747;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_CREATE_SHORTCUT = 1503;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_FEATURE_FLAGS_DASHBOARD */
    public static final int f2314xab2926d2 = 1217;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_FINANCIAL_APPS_SMS_ACCESS */
    public static final int f2315xb05eff20 = 1597;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GAME_DRIVER_DASHBOARD = 1613;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURES = 459;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_DOUBLE_TAP_POWER */
    public static final int f2316x6d42c6d2 = 752;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_DOUBLE_TAP_SCREEN */
    public static final int f2317x3f893c1f = 754;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_DOUBLE_TWIST = 755;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_NAV_BACK_SENSITIVITY_DLG */
    public static final int f2318x1086d2fa = 1748;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_NAV_NOT_AVAILABLE_DLG */
    public static final int f2319xbe907f2a = 1747;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_PICKUP = 753;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_SILENCE = 1625;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_SKIP = 1624;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_SWIPE_TO_NOTIFICATION */
    public static final int f2320x1b4d7ad3 = 751;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_SWIPE_UP = 1374;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_TAP_SCREEN = 1626;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_WAKE_LOCK_SCREEN */
    public static final int f2321xd45c547c = 1557;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_GESTURE_WAKE_SCREEN = 1570;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_HOMEPAGE = 1502;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_LANGUAGE_CATEGORY = 750;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_LOCK_SCREEN_PREFERENCES */
    public static final int f2322x45d28eb8 = 882;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_MANAGE_PICTURE_IN_PICTURE */
    public static final int f2323xda0a96be = 812;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_NETWORK_CATEGORY = 746;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_NETWORK_SCORER = 861;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_PREVENT_RINGING = 1360;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_STORAGE_CATEGORY = 745;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_STORAGE_PROFILE = 845;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_SYSTEM_CATEGORY = 744;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_WIFI_ADD_NETWORK = 1556;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_WIFI_DPP_CONFIGURATOR = 1595;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_WIFI_DPP_ENROLLEE = 1596;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ZEN_NOTIFICATIONS = 1400;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ZEN_ONBOARDING = 1380;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ZONE_PICKER_FIXED_OFFSET */
    public static final int f2324x5ebe331d = 1357;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ZONE_PICKER_REGION = 1355;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SETTINGS_ZONE_PICKER_TIME_ZONE = 1356;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SET_NEW_PASSWORD_ACTIVITY = 1644;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SIM = 88;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SLICE = 1401;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SOUND = 336;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SPECIAL_ACCESS = 351;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__STORAGE_FILES = 841;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__STORAGE_FREE_UP_SPACE_NOW = 840;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__STORAGE_MANAGER_SETTINGS = 458;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SUW_ACCESSIBILITY = 367;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SUW_ACCESSIBILITY_DISPLAY_SIZE = 370;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SUW_ACCESSIBILITY_FONT_SIZE = 369;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SUW_ACCESSIBILITY_TOGGLE_SCREEN_MAGNIFICATION */
    public static final int f2325xe62afa5 = 368;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SUW_ACCESSIBILITY_TOGGLE_SCREEN_READER */
    public static final int f2326x5188e81 = 371;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__SUW_ACCESSIBILITY_TOGGLE_SELECT_TO_SPEAK */
    public static final int f2327xf291e49c = 817;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__SYSTEM_ALERT_WINDOW_APPS = 221;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TESTING = 89;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TETHER = 90;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TOP_LEVEL_PRIVACY = 1587;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TRUSTED_CREDENTIALS = 92;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TRUST_AGENT = 91;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TTS_ENGINE_SETTINGS = 93;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__TTS_TEXT_TO_SPEECH = 94;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USAGE_ACCESS = 95;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USB_DEFAULT = 1312;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USB_DEVICE_DETAILS = 1291;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USER = 96;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USERS_APP_RESTRICTIONS = 97;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USER_CREDENTIALS = 285;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USER_DETAILS = 98;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USER_DICTIONARY_SETTINGS = 514;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__USER_LOCALE_LIST = 344;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__VIRTUAL_KEYBOARDS = 345;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__VPN = 100;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__VR_DISPLAY_PREFERENCE = 921;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__VR_MANAGE_LISTENERS = 334;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WALLPAPER_TYPE = 101;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WEBVIEW_IMPLEMENTATION = 405;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WFD_WIFI_DISPLAY = 102;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI = 103;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_CALLING = 105;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_CALLING_FOR_SUB = 1230;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_NETWORK_DETAILS = 849;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_P2P = 109;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_SAVED_ACCESS_POINTS = 106;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_SCANNING_NEEDED_DIALOG = 1373;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__WIFI_TETHER_SETTINGS = 1014;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_ACCESS_DETAIL = 1692;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_CALLS = 1611;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_DEFAULT_SETTINGS */
    public static final int f2328xe3d3cd0d = 1606;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_MESSAGES = 1610;

    /* renamed from: SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_NOTIFICATION_RESTRICTIONS */
    public static final int f2329xc8773def = 1608;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_SETTINGS = 1604;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_SOUND_SETTINGS = 1605;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_RULE_VIS_EFFECTS = 1609;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_CUSTOM_SETTINGS_DIALOG = 1612;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZEN_WHAT_TO_BLOCK = 1339;
    public static final int SETTINGS_UICHANGED__ATTRIBUTION__ZONE_PICKER = 515;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ABOUT_LEGAL_SETTINGS = 225;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY = 2;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_CAPTION_PROPERTIES = 3;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_DETAILS_SETTINGS = 1682;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_FONT_SIZE = 340;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_SCREEN_MAGNIFICATION_SETTINGS */
    public static final int f2330x2174be1a = 922;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_SERVICE = 4;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_TOGGLE_AUTOCLICK = 335;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_TOGGLE_DALTONIZER = 5;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_TOGGLE_GLOBAL_GESTURE = 6;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_TOGGLE_SCREEN_MAGNIFICATION */
    public static final int f2331xc00a9c3 = 7;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_VIBRATION = 1292;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_VIBRATION_NOTIFICATION */
    public static final int f2332xa0c4c370 = 1293;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_VIBRATION_RING = 1620;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCESSIBILITY_VIBRATION_TOUCH = 1294;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCOUNT = 8;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCOUNTS_ACCOUNT_SYNC = 9;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCOUNTS_CHOOSE_ACCOUNT_ACTIVITY = 10;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ACCOUNTS_WORK_PROFILE_SETTINGS = 401;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APN = 12;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APN_EDITOR = 13;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_ADVANCED = 130;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_APP_LAUNCH = 17;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_APP_STORAGE = 19;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_HIGH_POWER_APPS = 184;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_INSTALLED_APP_DETAILS = 20;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_MANAGE_ASSIST = 201;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_PROCESS_STATS_DETAIL = 21;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_PROCESS_STATS_UI = 23;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_STORAGE_APPS = 182;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_STORAGE_GAMES = 838;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_STORAGE_MOVIES = 935;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_STORAGE_MUSIC = 839;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_STORAGE_PHOTOS = 1092;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APPLICATIONS_USAGE_ACCESS_DETAIL = 183;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APP_BUBBLE_SETTINGS = 1700;
    public static final int SETTINGS_UICHANGED__PAGE_ID__APP_DATA_USAGE = 343;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BACKGROUND_CHECK_SUMMARY = 258;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BACKUP_SETTINGS = 818;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BILLING_CYCLE = 342;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BIOMETRIC_ENROLL_ACTIVITY = 1586;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BIOMETRIC_FRAGMENT = 1585;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BLUETOOTH_DEVICE_DETAILS = 1009;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BLUETOOTH_DEVICE_PICKER = 25;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BLUETOOTH_DIALOG_FRAGMENT = 613;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BLUETOOTH_FRAGMENT = 1390;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BLUETOOTH_PAIRING = 1018;
    public static final int SETTINGS_UICHANGED__PAGE_ID__BUBBLE_SETTINGS = 1699;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CHOOSE_LOCK_GENERIC = 27;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CHOOSE_LOCK_PASSWORD = 28;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CHOOSE_LOCK_PATTERN = 29;
    public static final int SETTINGS_UICHANGED__PAGE_ID__COLOR_MODE_SETTINGS = 1143;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONFIGURE_KEYGUARD_DIALOG = 1010;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONFIGURE_NOTIFICATION = 337;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONFIGURE_WIFI = 338;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONFIRM_LOCK_PASSWORD = 30;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONFIRM_LOCK_PATTERN = 31;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONNECTION_DEVICE_ADVANCED = 1264;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONVERT_FBE = 402;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CONVERT_FBE_CONFIRM = 403;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CRYPT_KEEPER = 32;
    public static final int SETTINGS_UICHANGED__PAGE_ID__CRYPT_KEEPER_CONFIRM = 33;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DARK_UI_SETTINGS = 1698;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DASHBOARD_SUMMARY = 35;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DATA_SAVER_SUMMARY = 348;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DATA_USAGE_LIST = 341;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DATA_USAGE_SUMMARY = 37;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DATA_USAGE_UNRESTRICTED_ACCESS = 349;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DATE_TIME = 38;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DEFAULT_APP_PICKER_CONFIRMATION_DIALOG */
    public static final int f2333xe9066824 = 791;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_ASSIST_PICKER = 843;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_AUTOFILL_PICKER = 792;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_BROWSER_PICKER = 785;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_EMERGENCY_APP_PICKER = 786;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_HOME_PICKER = 787;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_NOTIFICATION_ASSISTANT = 790;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_PHONE_PICKER = 788;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_SMS_PICKER = 789;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEFAULT_VOICE_INPUT_PICKER = 844;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEVELOPMENT = 39;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEVELOPMENT_QS_TILE_CONFIG = 1224;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEVICEINFO = 40;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEVICEINFO_STORAGE = 42;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DEVICE_ADMIN_SETTINGS = 516;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ACCESSIBILITY_HEARINGAID = 1512;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DIALOG_ACCESSIBILITY_SERVICE_DISABLE */
    public static final int f2334x3df1d8fd = 584;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ACCESSIBILITY_SERVICE_ENABLE = 583;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DIALOG_ACCOUNT_SYNC_CANNOT_ONETIME_SYNC */
    public static final int f2335x59b5e061 = 587;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ACCOUNT_SYNC_FAILED_REMOVAL = 586;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ACCOUNT_SYNC_REMOVE = 585;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_APN_EDITOR_ERROR = 530;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_APN_RESTORE_DEFAULT = 579;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_APP_BUBBLE_SETTINGS = 1702;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_APP_INFO_ACTION = 558;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_AWARE_DISABLE = 1633;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_AWARE_STATUS = 1701;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_BILLING_BYTE_LIMIT = 550;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_BILLING_CONFIRM_LIMIT = 551;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_BILLING_CYCLE = 549;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DIALOG_BLUETOOTH_DISABLE_A2DP_HW_OFFLOAD */
    public static final int f2336x329a856f = 1441;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DIALOG_BLUETOOTH_PAIRED_DEVICE_FORGET */
    public static final int f2337x764ebb51 = 1031;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DIALOG_BLUETOOTH_PAIRED_DEVICE_RENAME */
    public static final int f2338x8a3a10a2 = 1015;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_BLUETOOTH_RENAME = 538;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_CALL_SIM_LIST = 1708;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_CLEAR_ADB_KEYS = 1223;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_CONFIRM_AUTO_SYNC_CHANGE = 535;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_CUSTOM_LIST_CONFIRMATION = 529;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_DARK_UI_INFO = 1740;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_DATE_PICKER = 607;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_DELETE_SIM_CONFIRMATION = 1713;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_DELETE_SIM_PROGRESS = 1714;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_DISABLE_DEVELOPMENT_OPTIONS = 1591;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_DISABLE_NOTIFICATION_ACCESS = 552;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ENABLE_ADB = 1222;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ENABLE_DEVELOPMENT_OPTIONS = 1219;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ENABLE_OEM_UNLOCKING = 1220;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__DIALOG_ENCRYPTION_INTERSTITIAL_ACCESSIBILITY */
    public static final int f2339x8135b287 = 581;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FACE_ERROR = 1510;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FACE_REMOVE = 1693;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FINGERPINT_DELETE_LAST = 571;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FINGERPINT_EDIT = 570;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FINGERPINT_ERROR = 569;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FINGERPRINT_ICON_TOUCH = 568;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FINGERPRINT_SKIP_SETUP = 573;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FIRMWARE_VERSION = 1247;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_FRP = 528;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_HIGH_POWER_DETAILS = 540;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_IMEI_INFO = 1240;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_KEYBOARD_LAYOUT = 541;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_LEGACY_VPN_CONFIG = 545;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_LOG_PERSIST = 1225;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_MANAGE_MOBILE_PLAN = 609;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_NIGHT_DISPLAY_SET_END_TIME = 589;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_NIGHT_DISPLAY_SET_START_TIME = 588;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_OEM_LOCK_INFO = 1238;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_OWNER_INFO_SETTINGS = 531;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_PREFERRED_SIM_PICKER = 1709;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_PROXY_SELECTOR_ERROR = 574;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_REMOVE_USER = 534;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_RUNNIGN_SERVICE = 536;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_SERVICE_ACCESS_WARNING = 557;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_SETTINGS_HARDWARE_INFO = 862;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_SIM_LIST = 1707;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_SIM_STATUS = 1246;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_STORAGE_CLEAR_CACHE = 564;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_STORAGE_OTHER_INFO = 566;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_STORAGE_SYSTEM_INFO = 565;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_STORAGE_USER_INFO = 567;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_TIME_PICKER = 608;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_UNIFICATION_CONFIRMATION = 532;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_UNIFY_SOUND_SETTINGS = 553;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_ADD = 595;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_CANNOT_MANAGE = 594;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_CHOOSE_TYPE = 598;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_CONFIRM_EXIT_GUEST = 600;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_CREDENTIAL = 533;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_EDIT = 590;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_EDIT_PROFILE = 601;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_ENABLE_CALLING = 592;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_ENABLE_CALLING_AND_SMS = 593;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_NEED_LOCKSCREEN = 599;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_REMOVE = 591;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_SETUP = 596;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_USER_SETUP_PROFILE = 597;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VOLUME_FORGET = 559;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VOLUME_FORMAT = 1375;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VOLUME_INIT = 561;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VOLUME_RENAME = 563;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VOLUME_UNMOUNT = 562;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VPN_APP_CONFIG = 546;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VPN_CANNOT_CONNECT = 547;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_VPN_REPLACE_EXISTING = 548;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_AP_EDIT = 603;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_P2P_CANCEL_CONNECT = 576;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_P2P_DELETE_GROUP = 578;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_P2P_DISCONNECT = 575;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_P2P_RENAME = 577;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_SAVED_AP_EDIT = 602;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_SCAN_MODE = 543;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_WIFI_WRITE_NFC = 606;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ZEN_ACCESS_GRANT = 554;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ZEN_ACCESS_REVOKE = 555;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DIALOG_ZEN_TIMEPICKER = 556;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DISPLAY = 46;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DISPLAY_SCREEN_ZOOM = 339;
    public static final int SETTINGS_UICHANGED__PAGE_ID__DREAM = 47;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENABLE_VIRTUAL_KEYBOARDS = 347;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENCRYPTION = 48;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENCRYPTION_AND_CREDENTIAL = 846;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENTERPRISE_PRIVACY_DEFAULT_APPS = 940;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENTERPRISE_PRIVACY_INSTALLED_APPS = 938;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENTERPRISE_PRIVACY_PERMISSIONS = 939;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ENTERPRISE_PRIVACY_SETTINGS = 628;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FACE = 1511;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FACE_ENROLL_ENROLLING = 1507;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FACE_ENROLL_FINISHED = 1508;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FACE_ENROLL_INTRO = 1506;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FACE_ENROLL_PREVIEW = 1554;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FACE_ENROLL_SIDECAR = 1509;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT = 49;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_AUTHENTICATE_SIDECAR = 1221;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLLING = 240;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLLING_SETUP = 246;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLL_FINISH = 242;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLL_FINISH_SETUP = 248;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLL_INTRO = 243;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLL_INTRO_SETUP = 249;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_ENROLL_SIDECAR = 245;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_FIND_SENSOR = 241;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_FIND_SENSOR_SETUP = 247;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FINGERPRINT_REMOVE_SIDECAR = 934;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_BATTERY_HISTORY_DETAIL = 51;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_BATTERY_SAVER = 52;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_BATTERY_TIP_DIALOG = 1323;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_INACTIVE_APPS = 238;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_POWER_USAGE_DETAIL = 53;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_POWER_USAGE_SUMMARY_V2 = 1263;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_RESTRICTED_APP_DETAILS = 1285;
    public static final int SETTINGS_UICHANGED__PAGE_ID__FUELGAUGE_SMART_BATTERY = 1281;
    public static final int SETTINGS_UICHANGED__PAGE_ID__GENTLE_NOTIFICATIONS_SCREEN = 1715;
    public static final int SETTINGS_UICHANGED__PAGE_ID__GLOBAL_ACTIONS_PANEL_SETTINGS = 1728;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ICC_LOCK = 56;
    public static final int SETTINGS_UICHANGED__PAGE_ID__INPUTMETHOD_KEYBOARD = 58;
    public static final int SETTINGS_UICHANGED__PAGE_ID__INPUTMETHOD_SPELL_CHECKERS = 59;
    public static final int SETTINGS_UICHANGED__PAGE_ID__INPUTMETHOD_SUBTYPE_ENABLER = 60;
    public static final int SETTINGS_UICHANGED__PAGE_ID__INPUTMETHOD_USER_DICTIONARY = 61;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__INPUTMETHOD_USER_DICTIONARY_ADD_WORD */
    public static final int f2340x47f23778 = 62;
    public static final int SETTINGS_UICHANGED__PAGE_ID__LOCATION = 63;
    public static final int SETTINGS_UICHANGED__PAGE_ID__LOCATION_SCANNING = 131;
    public static final int SETTINGS_UICHANGED__PAGE_ID__LOCK_SCREEN_NOTIFICATION_CONTENT = 1584;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MANAGE_APPLICATIONS = 65;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MANAGE_APPLICATIONS_NOTIFICATIONS = 133;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MANAGE_DOMAIN_URLS = 143;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MANAGE_EXTERNAL_SOURCES = 808;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MASTER_CLEAR = 66;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MASTER_CLEAR_CONFIRM = 67;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MOBILE_DATA_DIALOG = 1582;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MOBILE_NETWORK = 1571;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MOBILE_NETWORK_LIST = 1627;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MOBILE_NETWORK_RENAME_DIALOG = 1642;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MOBILE_NETWORK_SELECT = 1581;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MOBILE_ROAMING_DIALOG = 1583;
    public static final int SETTINGS_UICHANGED__PAGE_ID__MODULE_LICENSES_DASHBOARD = 1746;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NFC_BEAM = 69;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NFC_PAYMENT = 70;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NIGHT_DISPLAY_SETTINGS = 488;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ACCESS = 179;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_APP_NOTIFICATION = 72;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_CHANNEL_GROUP = 1218;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_REDACTION = 74;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_STATION = 75;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_TOPIC_NOTIFICATION = 265;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE = 76;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_ACCESS = 180;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_AUTOMATION = 142;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_DELETE_RULE_DIALOG */
    public static final int f2341xbd1f488a = 1266;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_DURATION_DIALOG */
    public static final int f2342x58da7a00 = 1341;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_ENABLE_DIALOG = 1286;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_EVENT_RULE = 146;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_OVERRIDING_APP */
    public static final int f2343x352b00fe = 1589;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_OVERRIDING_APPS */
    public static final int f2344x70351f15 = 1588;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_PRIORITY = 141;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_RULE_NAME_DIALOG */
    public static final int f2345xd3666aac = 1269;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_RULE_SELECTION_DIALOG */
    public static final int f2346xbca84b = 1270;
    public static final int SETTINGS_UICHANGED__PAGE_ID__NOTIFICATION_ZEN_MODE_SCHEDULE_RULE = 144;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATHNP = 1673;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATSAP = 1671;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATSCP = 1672;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATSII = 1668;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATSSI = 1667;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATSSP = 1670;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_ATUS = 1669;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PAGE_UNKNOWN = 0;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PANEL_INTERNET_CONNECTIVITY = 1654;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PANEL_MEDIA_OUTPUT = 1657;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PANEL_NFC = 1656;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PANEL_VOLUME = 1655;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PANEL_WIFI = 1687;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PHYSICAL_KEYBOARDS = 346;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PREMIUM_SMS_ACCESS = 388;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PREVIOUSLY_CONNECTED_DEVICES = 1370;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PRINT_JOB_SETTINGS = 78;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PRINT_SERVICE_SETTINGS = 79;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PRINT_SETTINGS = 80;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PRIVACY = 81;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PROCESS_STATS_SUMMARY = 202;
    public static final int SETTINGS_UICHANGED__PAGE_ID__PROXY_SELECTOR = 82;
    public static final int SETTINGS_UICHANGED__PAGE_ID__RESET_DASHBOARD = 924;
    public static final int SETTINGS_UICHANGED__PAGE_ID__RESET_NETWORK = 83;
    public static final int SETTINGS_UICHANGED__PAGE_ID__RESET_NETWORK_CONFIRM = 84;
    public static final int SETTINGS_UICHANGED__PAGE_ID__RUNNING_SERVICES = 404;
    public static final int SETTINGS_UICHANGED__PAGE_ID__RUNNING_SERVICE_DETAILS = 85;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SCREEN_LOCK_SETTINGS = 1265;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SCREEN_PINNING = 86;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SECURITY = 87;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ADAPTIVE_SLEEP = 1628;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_APP_NOTIF_CATEGORY = 748;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ASSIST_GESTURE = 996;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_AUTO_BRIGHTNESS = 1381;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_AWARE = 1632;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CHOOSE_LOCK_DIALOG = 990;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_AIRPLANE_MODE = 377;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_BACKGROUND_DATA = 378;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_BATTERY_SAVER = 379;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_CELLULAR_DATA = 380;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_DEVICE_MUTED = 1368;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_DEVICE_VIBRATE = 1369;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_DND = 381;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_GRAYSCALE_MODE = 1683;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_HOTSPOT = 382;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_NIGHT_DISPLAY = 492;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONDITION_WORK_MODE = 383;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CONNECTED_DEVICE_CATEGORY = 747;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_CREATE_SHORTCUT = 1503;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_FEATURE_FLAGS_DASHBOARD = 1217;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_FINANCIAL_APPS_SMS_ACCESS = 1597;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GAME_DRIVER_DASHBOARD = 1613;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURES = 459;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_DOUBLE_TAP_POWER = 752;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_DOUBLE_TAP_SCREEN = 754;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_DOUBLE_TWIST = 755;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_NAV_BACK_SENSITIVITY_DLG */
    public static final int f2347x15a6590e = 1748;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_NAV_NOT_AVAILABLE_DLG */
    public static final int f2348x3fe8d196 = 1747;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_PICKUP = 753;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_SILENCE = 1625;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_SKIP = 1624;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_SWIPE_TO_NOTIFICATION */
    public static final int f2349x9ca5cd3f = 751;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_SWIPE_UP = 1374;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_TAP_SCREEN = 1626;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_WAKE_LOCK_SCREEN = 1557;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_GESTURE_WAKE_SCREEN = 1570;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_HOMEPAGE = 1502;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_LANGUAGE_CATEGORY = 750;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_LOCK_SCREEN_PREFERENCES = 882;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_MANAGE_PICTURE_IN_PICTURE = 812;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_NETWORK_CATEGORY = 746;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_NETWORK_SCORER = 861;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_PREVENT_RINGING = 1360;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_STORAGE_CATEGORY = 745;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_STORAGE_PROFILE = 845;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_SYSTEM_CATEGORY = 744;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_WIFI_ADD_NETWORK = 1556;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_WIFI_DPP_CONFIGURATOR = 1595;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_WIFI_DPP_ENROLLEE = 1596;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ZEN_NOTIFICATIONS = 1400;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ZEN_ONBOARDING = 1380;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ZONE_PICKER_FIXED_OFFSET = 1357;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ZONE_PICKER_REGION = 1355;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SETTINGS_ZONE_PICKER_TIME_ZONE = 1356;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SET_NEW_PASSWORD_ACTIVITY = 1644;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SIM = 88;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SLICE = 1401;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SOUND = 336;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SPECIAL_ACCESS = 351;
    public static final int SETTINGS_UICHANGED__PAGE_ID__STORAGE_FILES = 841;
    public static final int SETTINGS_UICHANGED__PAGE_ID__STORAGE_FREE_UP_SPACE_NOW = 840;
    public static final int SETTINGS_UICHANGED__PAGE_ID__STORAGE_MANAGER_SETTINGS = 458;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SUW_ACCESSIBILITY = 367;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SUW_ACCESSIBILITY_DISPLAY_SIZE = 370;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SUW_ACCESSIBILITY_FONT_SIZE = 369;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__SUW_ACCESSIBILITY_TOGGLE_SCREEN_MAGNIFICATION */
    public static final int f2350xcd690bb9 = 368;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__SUW_ACCESSIBILITY_TOGGLE_SCREEN_READER */
    public static final int f2351x8670e0ed = 371;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__SUW_ACCESSIBILITY_TOGGLE_SELECT_TO_SPEAK */
    public static final int f2352x7f1f4c08 = 817;
    public static final int SETTINGS_UICHANGED__PAGE_ID__SYSTEM_ALERT_WINDOW_APPS = 221;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TESTING = 89;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TETHER = 90;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TOP_LEVEL_PRIVACY = 1587;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TRUSTED_CREDENTIALS = 92;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TRUST_AGENT = 91;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TTS_ENGINE_SETTINGS = 93;
    public static final int SETTINGS_UICHANGED__PAGE_ID__TTS_TEXT_TO_SPEECH = 94;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USAGE_ACCESS = 95;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USB_DEFAULT = 1312;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USB_DEVICE_DETAILS = 1291;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USER = 96;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USERS_APP_RESTRICTIONS = 97;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USER_CREDENTIALS = 285;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USER_DETAILS = 98;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USER_DICTIONARY_SETTINGS = 514;
    public static final int SETTINGS_UICHANGED__PAGE_ID__USER_LOCALE_LIST = 344;
    public static final int SETTINGS_UICHANGED__PAGE_ID__VIRTUAL_KEYBOARDS = 345;
    public static final int SETTINGS_UICHANGED__PAGE_ID__VPN = 100;
    public static final int SETTINGS_UICHANGED__PAGE_ID__VR_DISPLAY_PREFERENCE = 921;
    public static final int SETTINGS_UICHANGED__PAGE_ID__VR_MANAGE_LISTENERS = 334;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WALLPAPER_TYPE = 101;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WEBVIEW_IMPLEMENTATION = 405;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WFD_WIFI_DISPLAY = 102;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI = 103;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_CALLING = 105;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_CALLING_FOR_SUB = 1230;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_NETWORK_DETAILS = 849;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_P2P = 109;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_SAVED_ACCESS_POINTS = 106;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_SCANNING_NEEDED_DIALOG = 1373;
    public static final int SETTINGS_UICHANGED__PAGE_ID__WIFI_TETHER_SETTINGS = 1014;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_ACCESS_DETAIL = 1692;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_CALLS = 1611;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_DEFAULT_SETTINGS = 1606;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_MESSAGES = 1610;

    /* renamed from: SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_NOTIFICATION_RESTRICTIONS */
    public static final int f2353xcd96c403 = 1608;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_SETTINGS = 1604;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_SOUND_SETTINGS = 1605;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_RULE_VIS_EFFECTS = 1609;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_CUSTOM_SETTINGS_DIALOG = 1612;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZEN_WHAT_TO_BLOCK = 1339;
    public static final int SETTINGS_UICHANGED__PAGE_ID__ZONE_PICKER = 515;
    public static final int SETTINGS_UI_CHANGED = 97;
    public static final int SETTING_CHANGED = 41;
    public static final int SETTING_CHANGED__REASON__DELETED = 2;
    public static final int SETTING_CHANGED__REASON__UPDATED = 1;
    public static final int SE_OMAPI_REPORTED = 141;
    public static final int SE_OMAPI_REPORTED__OPERATION__OPEN_CHANNEL = 1;
    public static final int SE_OMAPI_REPORTED__OPERATION__UNKNOWN = 0;
    public static final int SE_STATE_CHANGED = 140;
    public static final int SE_STATE_CHANGED__STATE__CONNECTED = 3;
    public static final int SE_STATE_CHANGED__STATE__DISCONNECTED = 2;
    public static final int SE_STATE_CHANGED__STATE__HALCRASH = 4;
    public static final int SE_STATE_CHANGED__STATE__INITIALIZED = 1;
    public static final int SE_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int SHUTDOWN_SEQUENCE_REPORTED = 56;
    public static final int SIGNED_CONFIG_REPORTED = 123;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__APPLIED = 1;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__BASE64_FAILURE_CONFIG = 2;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__BASE64_FAILURE_SIGNATURE = 3;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__INVALID_CONFIG = 5;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__NOT_APPLICABLE = 8;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__OLD_CONFIG = 6;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__SECURITY_EXCEPTION = 4;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__SIGNATURE_CHECK_FAILED = 7;

    /* renamed from: SIGNED_CONFIG_REPORTED__STATUS__SIGNATURE_CHECK_FAILED_PROD_KEY_ABSENT */
    public static final int f2354x12a8845c = 9;
    public static final int SIGNED_CONFIG_REPORTED__STATUS__UNKNOWN_STATUS = 0;
    public static final int SIGNED_CONFIG_REPORTED__TYPE__GLOBAL_SETTINGS = 1;
    public static final int SIGNED_CONFIG_REPORTED__TYPE__UNKNOWN_TYPE = 0;
    public static final int SIGNED_CONFIG_REPORTED__VERIFIED_WITH__DEBUG = 1;
    public static final int SIGNED_CONFIG_REPORTED__VERIFIED_WITH__NO_KEY = 0;
    public static final int SIGNED_CONFIG_REPORTED__VERIFIED_WITH__PRODUCTION = 2;
    public static final int SLOW_IO = 92;
    public static final int SLOW_IO__OPERATION__READ = 1;
    public static final int SLOW_IO__OPERATION__SYNC = 4;
    public static final int SLOW_IO__OPERATION__UNKNOWN = 0;
    public static final int SLOW_IO__OPERATION__UNMAP = 3;
    public static final int SLOW_IO__OPERATION__WRITE = 2;
    public static final int SPEAKER_IMPEDANCE_REPORTED = 71;
    public static final int SPEECH_DSP_STAT_REPORTED = 145;
    public static final int STYLE_UICHANGED__ACTION__DEFAULT_ACTION = 0;
    public static final int STYLE_UICHANGED__ACTION__LIVE_WALLPAPER_APPLIED = 16;
    public static final int STYLE_UICHANGED__ACTION__LIVE_WALLPAPER_DELETE_FAILED = 15;
    public static final int STYLE_UICHANGED__ACTION__LIVE_WALLPAPER_DELETE_SUCCESS = 14;
    public static final int STYLE_UICHANGED__ACTION__LIVE_WALLPAPER_DOWNLOAD_CANCELLED = 13;
    public static final int STYLE_UICHANGED__ACTION__LIVE_WALLPAPER_DOWNLOAD_FAILED = 12;
    public static final int STYLE_UICHANGED__ACTION__LIVE_WALLPAPER_DOWNLOAD_SUCCESS = 11;
    public static final int STYLE_UICHANGED__ACTION__ONRESUME = 1;
    public static final int STYLE_UICHANGED__ACTION__ONSTOP = 2;
    public static final int STYLE_UICHANGED__ACTION__PICKER_APPLIED = 4;
    public static final int STYLE_UICHANGED__ACTION__PICKER_SELECT = 3;
    public static final int STYLE_UICHANGED__ACTION__WALLPAPER_APPLIED = 7;
    public static final int STYLE_UICHANGED__ACTION__WALLPAPER_DOWNLOAD = 9;
    public static final int STYLE_UICHANGED__ACTION__WALLPAPER_EXPLORE = 8;
    public static final int STYLE_UICHANGED__ACTION__WALLPAPER_OPEN_CATEGORY = 5;
    public static final int STYLE_UICHANGED__ACTION__WALLPAPER_REMOVE = 10;
    public static final int STYLE_UICHANGED__ACTION__WALLPAPER_SELECT = 6;
    public static final int STYLE_UICHANGED__LOCATION_PREFERENCE__LOCATION_CURRENT = 2;
    public static final int STYLE_UICHANGED__LOCATION_PREFERENCE__LOCATION_MANUAL = 3;

    /* renamed from: STYLE_UICHANGED__LOCATION_PREFERENCE__LOCATION_PREFERENCE_UNSPECIFIED */
    public static final int f2355x13cf4d4a = 0;
    public static final int STYLE_UICHANGED__LOCATION_PREFERENCE__LOCATION_UNAVAILABLE = 1;
    public static final int STYLE_UI_CHANGED = 179;
    public static final int SUBSYSTEM_SLEEP_STATE = 10005;
    public static final int SYNC_STATE_CHANGED = 7;
    public static final int SYNC_STATE_CHANGED__STATE__OFF = 0;
    public static final int SYNC_STATE_CHANGED__STATE__ON = 1;
    public static final int SYSTEM_ELAPSED_REALTIME = 10014;
    public static final int SYSTEM_ION_HEAP_SIZE = 10056;
    public static final int SYSTEM_SERVER_WATCHDOG_OCCURRED = 185;
    public static final int SYSTEM_UPTIME = 10015;
    public static final int TEMPERATURE = 10021;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_BATTERY = 2;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_BCL_CURRENT = 7;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_BCL_PERCENTAGE = 8;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_BCL_VOLTAGE = 6;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_CPU = 0;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_GPU = 1;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_NPU = 9;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_POWER_AMPLIFIER = 5;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_SKIN = 3;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_UNKNOWN = -1;
    public static final int TEMPERATURE__SENSOR_LOCATION__TEMPERATURE_TYPE_USB_PORT = 4;
    public static final int TEMPERATURE__SEVERITY__CRITICAL = 4;
    public static final int TEMPERATURE__SEVERITY__EMERGENCY = 5;
    public static final int TEMPERATURE__SEVERITY__LIGHT = 1;
    public static final int TEMPERATURE__SEVERITY__MODERATE = 2;
    public static final int TEMPERATURE__SEVERITY__NONE = 0;
    public static final int TEMPERATURE__SEVERITY__SEVERE = 3;
    public static final int TEMPERATURE__SEVERITY__SHUTDOWN = 6;
    public static final int TEST_ATOM_REPORTED = 205;
    public static final int TEST_ATOM_REPORTED__STATE__OFF = 1;
    public static final int TEST_ATOM_REPORTED__STATE__ON = 2;
    public static final int TEST_ATOM_REPORTED__STATE__UNKNOWN = 0;
    public static final int THERMAL_THROTTLING = 86;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED = 189;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BATTERY */
    public static final int f2356xf0a01a3e = 2;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BCL_CURRENT */
    public static final int f2357x6a9ee516 = 7;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BCL_PERCENTAGE */
    public static final int f2358x9abc4dbd = 8;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BCL_VOLTAGE */
    public static final int f2359x4d2482fb = 6;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_CPU */
    public static final int f2360x7a6ab659 = 0;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_GPU */
    public static final int f2361x7a6ac55d = 1;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_NPU */
    public static final int f2362x7a6adfa4 = 9;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_POWER_AMPLIFIER */
    public static final int f2363x262a92c8 = 5;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_SKIN */
    public static final int f2364xd2f346cc = 3;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_UNKNOWN */
    public static final int f2365xf364187b = -1;

    /* renamed from: THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_USB_PORT */
    public static final int f2366x7332684b = 4;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__CRITICAL = 4;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__EMERGENCY = 5;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__LIGHT = 1;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__MODERATE = 2;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__NONE = 0;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__SEVERE = 3;
    public static final int THERMAL_THROTTLING_SEVERITY_STATE_CHANGED__SEVERITY__SHUTDOWN = 6;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BATTERY */
    public static final int f2367x24271e5c = 2;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BCL_CURRENT */
    public static final int f2368xbcaaaa34 = 7;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BCL_PERCENTAGE */
    public static final int f2369x626c0bdf = 8;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_BCL_VOLTAGE */
    public static final int f2370x9f304819 = 6;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_CPU */
    public static final int f2371xd4e47977 = 0;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_GPU */
    public static final int f2372xd4e4887b = 1;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_NPU */
    public static final int f2373xd4e4a2c2 = 9;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_POWER_AMPLIFIER */
    public static final int f2374x547298e6 = 5;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_SKIN */
    public static final int f2375xc7b1e76e = 3;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_UNKNOWN */
    public static final int f2376x26eb1c99 = -1;

    /* renamed from: THERMAL_THROTTLING_STATE_CHANGED__SENSOR_TYPE__TEMPERATURE_TYPE_USB_PORT */
    public static final int f2377xb08be7ed = 4;
    public static final int THERMAL_THROTTLING_STATE_CHANGED__STATE__START = 1;
    public static final int THERMAL_THROTTLING_STATE_CHANGED__STATE__STOP = 2;
    public static final int THERMAL_THROTTLING_STATE_CHANGED__STATE__UNKNOWN = 0;
    public static final int TIME_ZONE_DATA_INFO = 10052;
    public static final int TOMB_STONE_OCCURRED = 186;
    public static final int TOUCH_EVENT_REPORTED = 34;
    public static final int TOUCH_GESTURE_CLASSIFIED = 177;
    public static final int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DEEP_PRESS = 4;
    public static final int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__DOUBLE_TAP = 2;
    public static final int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__LONG_PRESS = 3;
    public static final int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SCROLL = 5;
    public static final int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__SINGLE_TAP = 1;
    public static final int TOUCH_GESTURE_CLASSIFIED__CLASSIFICATION__UNKNOWN_CLASSIFICATION = 0;
    public static final int TRAIN_INFO = 10051;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_BOOT_TRIGGERED = 13;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_BOOT_TRIGGERED_FAILURE = 14;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_FAILURE = 16;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_INITIATED = 9;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_INITIATED_FAILURE = 10;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_REQUESTED = 8;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_STAGED = 11;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_STAGED_CANCEL_FAILURE = 19;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_STAGED_CANCEL_REQUESTED = 17;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_STAGED_CANCEL_SUCCESS = 18;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_STAGED_FAILURE = 12;
    public static final int TRAIN_INFO__STATUS__INSTALLER_ROLLBACK_SUCCESS = 15;
    public static final int TRAIN_INFO__STATUS__INSTALL_CANCELLED = 7;
    public static final int TRAIN_INFO__STATUS__INSTALL_FAILURE = 6;
    public static final int TRAIN_INFO__STATUS__INSTALL_FAILURE_COMMIT = 25;
    public static final int TRAIN_INFO__STATUS__INSTALL_FAILURE_DOWNLOAD = 23;
    public static final int TRAIN_INFO__STATUS__INSTALL_FAILURE_STATE_MISMATCH = 24;
    public static final int TRAIN_INFO__STATUS__INSTALL_REQUESTED = 1;
    public static final int TRAIN_INFO__STATUS__INSTALL_STAGED_CANCEL_FAILURE = 22;
    public static final int TRAIN_INFO__STATUS__INSTALL_STAGED_CANCEL_REQUESTED = 20;
    public static final int TRAIN_INFO__STATUS__INSTALL_STAGED_CANCEL_SUCCESS = 21;
    public static final int TRAIN_INFO__STATUS__INSTALL_STAGED_NOT_READY = 3;
    public static final int TRAIN_INFO__STATUS__INSTALL_STAGED_READY = 4;
    public static final int TRAIN_INFO__STATUS__INSTALL_STARTED = 2;
    public static final int TRAIN_INFO__STATUS__INSTALL_SUCCESS = 5;
    public static final int TRAIN_INFO__STATUS__UNKNOWN = 0;
    public static final int UID_PROCESS_STATE_CHANGED = 27;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_BACKUP = 1008;

    /* renamed from: UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_BOUND_FOREGROUND_SERVICE */
    public static final int f2378xba8db8a2 = 1004;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_BOUND_TOP = 1020;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_ACTIVITY = 1015;

    /* renamed from: UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_ACTIVITY_CLIENT */
    public static final int f2379x3ee00c66 = 1016;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_EMPTY = 1018;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_CACHED_RECENT = 1017;

    /* renamed from: UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_FOREGROUND_SERVICE */
    public static final int f2380xa55ca221 = 1003;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_HEAVY_WEIGHT = 1012;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_HOME = 1013;

    /* renamed from: UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_IMPORTANT_BACKGROUND */
    public static final int f2381x6d166e13 = 1006;

    /* renamed from: UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_IMPORTANT_FOREGROUND */
    public static final int f2382x32c68228 = 1005;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_LAST_ACTIVITY = 1014;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_NONEXISTENT = 1019;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_PERSISTENT = 1000;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_PERSISTENT_UI = 1001;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_RECEIVER = 1010;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_SERVICE = 1009;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_TOP = 1002;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_TOP_SLEEPING = 1011;

    /* renamed from: UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_TRANSIENT_BACKGROUND */
    public static final int f2383x1cc3e2ab = 1007;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_UNKNOWN = 999;
    public static final int UID_PROCESS_STATE_CHANGED__STATE__PROCESS_STATE_UNKNOWN_TO_PROTO = 998;
    public static final int USB_CONNECTOR_STATE_CHANGED = 70;
    public static final int USB_CONNECTOR_STATE_CHANGED__STATE__STATE_CONNECTED = 1;
    public static final int USB_CONNECTOR_STATE_CHANGED__STATE__STATE_DISCONNECTED = 0;
    public static final int USB_CONTAMINANT_REPORTED = 146;
    public static final int USB_CONTAMINANT_REPORTED__STATUS__CONTAMINANT_STATUS_DETECTED = 4;
    public static final int USB_CONTAMINANT_REPORTED__STATUS__CONTAMINANT_STATUS_DISABLED = 2;

    /* renamed from: USB_CONTAMINANT_REPORTED__STATUS__CONTAMINANT_STATUS_NOT_DETECTED */
    public static final int f2384x68d24bc5 = 3;

    /* renamed from: USB_CONTAMINANT_REPORTED__STATUS__CONTAMINANT_STATUS_NOT_SUPPORTED */
    public static final int f2385x1f6147ab = 1;
    public static final int USB_CONTAMINANT_REPORTED__STATUS__CONTAMINANT_STATUS_UNKNOWN = 0;
    public static final int USB_DEVICE_ATTACHED = 77;
    public static final int USB_DEVICE_ATTACHED__STATE__STATE_CONNECTED = 1;
    public static final int USB_DEVICE_ATTACHED__STATE__STATE_DISCONNECTED = 0;
    public static final int USB_PORT_OVERHEAT_EVENT_REPORTED = 133;
    public static final int USER_RESTRICTION_CHANGED = 96;
    public static final int VIBRATOR_STATE_CHANGED = 84;
    public static final int VIBRATOR_STATE_CHANGED__STATE__OFF = 0;
    public static final int VIBRATOR_STATE_CHANGED__STATE__ON = 1;
    public static final int WAKELOCK_STATE_CHANGED = 10;
    public static final int WAKELOCK_STATE_CHANGED__STATE__ACQUIRE = 1;
    public static final int WAKELOCK_STATE_CHANGED__STATE__CHANGE_ACQUIRE = 3;
    public static final int WAKELOCK_STATE_CHANGED__STATE__CHANGE_RELEASE = 2;
    public static final int WAKELOCK_STATE_CHANGED__STATE__RELEASE = 0;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__DOZE_WAKE_LOCK = 64;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__DRAW_WAKE_LOCK = 128;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__FULL_WAKE_LOCK = 26;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__PARTIAL_WAKE_LOCK = 1;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__PROXIMITY_SCREEN_OFF_WAKE_LOCK = 32;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__SCREEN_BRIGHT_WAKE_LOCK = 10;
    public static final int WAKELOCK_STATE_CHANGED__TYPE__SCREEN_DIM_WAKE_LOCK = 6;
    public static final int WAKEUP_ALARM_OCCURRED = 35;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__ACTIVE = 10;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__EXEMPTED = 5;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__FREQUENT = 30;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__NEVER = 50;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__RARE = 40;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__UNKNOWN = 0;
    public static final int WAKEUP_ALARM_OCCURRED__APP_STANDBY_BUCKET__WORKING_SET = 20;
    public static final int WALL_CLOCK_TIME_SHIFTED = 45;
    public static final int WATCHDOG_ROLLBACK_OCCURRED = 147;

    /* renamed from: WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_BOOT_TRIGGERED */
    public static final int f2386x2e3f8c3e = 4;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_FAILURE = 3;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_INITIATE = 1;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__ROLLBACK_SUCCESS = 2;
    public static final int WATCHDOG_ROLLBACK_OCCURRED__ROLLBACK_TYPE__UNKNOWN = 0;
    public static final int WIFI_ACTIVITY_INFO = 10011;
    public static final int WIFI_BYTES_TRANSFER = 10000;
    public static final int WIFI_BYTES_TRANSFER_BY_FG_BG = 10001;
    public static final int WIFI_ENABLED_STATE_CHANGED = 113;
    public static final int WIFI_ENABLED_STATE_CHANGED__STATE__OFF = 0;
    public static final int WIFI_ENABLED_STATE_CHANGED__STATE__ON = 1;
    public static final int WIFI_LOCK_STATE_CHANGED = 37;
    public static final int WIFI_LOCK_STATE_CHANGED__MODE__WIFI_MODE_FULL = 1;
    public static final int WIFI_LOCK_STATE_CHANGED__MODE__WIFI_MODE_FULL_HIGH_PERF = 3;
    public static final int WIFI_LOCK_STATE_CHANGED__MODE__WIFI_MODE_FULL_LOW_LATENCY = 4;
    public static final int WIFI_LOCK_STATE_CHANGED__MODE__WIFI_MODE_SCAN_ONLY = 2;
    public static final int WIFI_LOCK_STATE_CHANGED__STATE__OFF = 0;
    public static final int WIFI_LOCK_STATE_CHANGED__STATE__ON = 1;
    public static final int WIFI_MULTICAST_LOCK_STATE_CHANGED = 53;
    public static final int WIFI_MULTICAST_LOCK_STATE_CHANGED__STATE__OFF = 0;
    public static final int WIFI_MULTICAST_LOCK_STATE_CHANGED__STATE__ON = 1;
    public static final int WIFI_RADIO_POWER_STATE_CHANGED = 13;

    /* renamed from: WIFI_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_HIGH */
    public static final int f2387x99bc6e89 = 3;

    /* renamed from: WIFI_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_LOW */
    public static final int f2388x5fcc556d = 1;

    /* renamed from: WIFI_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_MEDIUM */
    public static final int f2389x24a97e9c = 2;

    /* renamed from: WIFI_RADIO_POWER_STATE_CHANGED__STATE__DATA_CONNECTION_POWER_STATE_UNKNOWN */
    public static final int f2390x2778a1e3 = Integer.MAX_VALUE;
    public static final int WIFI_RUNNING_STATE_CHANGED = 114;
    public static final int WIFI_RUNNING_STATE_CHANGED__STATE__OFF = 0;
    public static final int WIFI_RUNNING_STATE_CHANGED__STATE__ON = 1;
    public static final int WIFI_SCAN_STATE_CHANGED = 39;
    public static final int WIFI_SCAN_STATE_CHANGED__STATE__OFF = 0;
    public static final int WIFI_SCAN_STATE_CHANGED__STATE__ON = 1;
    public static final int WIFI_SIGNAL_STRENGTH_CHANGED = 38;

    /* renamed from: WIFI_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GOOD */
    public static final int f2391x7311e719 = 3;

    /* renamed from: WIFI_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_GREAT */
    public static final int f2392xef2c3391 = 4;

    /* renamed from: WIFI_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_MODERATE */
    public static final int f2393x97dccd5f = 2;

    /* renamed from: WIFI_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_NONE_OR_UNKNOWN */
    public static final int f2394xae41159 = 0;

    /* renamed from: WIFI_SIGNAL_STRENGTH_CHANGED__SIGNAL_STRENGTH__SIGNAL_STRENGTH_POOR */
    public static final int f2395x7315fe7e = 1;
    public static final int WTFOCCURRED__ERROR_SOURCE__DATA_APP = 1;
    public static final int WTFOCCURRED__ERROR_SOURCE__ERROR_SOURCE_UNKNOWN = 0;
    public static final int WTFOCCURRED__ERROR_SOURCE__SYSTEM_APP = 2;
    public static final int WTFOCCURRED__ERROR_SOURCE__SYSTEM_SERVER = 3;
    public static final int WTF_OCCURRED = 80;

    public static native int write(int i);

    public static native int write(int i, float f, float f2, float f3, float f4, int i2);

    public static native int write(int i, int i2);

    public static native int write(int i, int i2, int i3);

    public static native int write(int i, int i2, int i3, float f);

    public static native int write(int i, int i2, int i3, float f, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17);

    public static native int write(int i, int i2, int i3, int i4);

    public static native int write(int i, int i2, int i3, int i4, int i5);

    public static native int write(int i, int i2, int i3, int i4, int i5, int i6);

    public static native int write(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    public static native int write(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    public static native int write(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9);

    public static native int write(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr);

    public static native int write(int i, int i2, int i3, int i4, long j, boolean z);

    public static native int write(int i, int i2, int i3, int i4, String str, int i5);

    public static native int write(int i, int i2, int i3, int i4, String str, long j);

    public static native int write(int i, int i2, int i3, int i4, String str, String str2, boolean z);

    public static native int write(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, int i5, int i6, String str, String str2, int i7, int i8, boolean z4, boolean z5, int i9);

    public static native int write(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int write(int i, int i2, int i3, int i4, byte[] bArr, boolean z);

    public static native int write(int i, int i2, int i3, int i4, byte[] bArr, byte[] bArr2, byte[] bArr3);

    public static native int write(int i, int i2, int i3, long j, boolean z);

    public static native int write(int i, int i2, int i3, String str);

    public static native int write(int i, int i2, int i3, String str, int i4, int i5, int i6, int i7, long j, int i8, int i9, int i10, int i11);

    public static native int write(int i, int i2, int i3, String str, int i4, long j, int i5, int i6, String str2, String str3);

    public static native int write(int i, int i2, int i3, boolean z);

    public static native int write(int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7, boolean z2);

    public static native int write(int i, int i2, int i3, boolean z, int i4, boolean z2, int i5, long j, boolean z3);

    public static native int write(int i, int i2, int i3, boolean z, boolean z2);

    public static native int write(int i, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, long j);

    public static native int write(int i, int i2, long j);

    public static native int write(int i, int i2, long j, int i3, boolean z, int i4, int i5, int i6);

    public static native int write(int i, int i2, long j, long j2);

    public static native int write(int i, int i2, SparseArray<Object> sparseArray);

    public static native int write(int i, int i2, String str);

    public static native int write(int i, int i2, String str, int i3);

    public static native int write(int i, int i2, String str, int i3, int i4);

    public static native int write(int i, int i2, String str, int i3, long j, long j2, long j3, long j4, long j5, long j6, int i4);

    public static native int write(int i, int i2, String str, int i3, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, int i4, long j10, int i5, int i6, long j11, long j12);

    public static native int write(int i, int i2, String str, int i3, String str2);

    public static native int write(int i, int i2, String str, int i3, String str2, String str3, String str4, int i4, int i5, int i6);

    public static native int write(int i, int i2, String str, int i3, String str2, String str3, boolean z, long j, int i4, int i5, int i6, int i7, int i8, String str4, int i9, int i10);

    public static native int write(int i, int i2, String str, int i3, String str2, boolean z, long j);

    public static native int write(int i, int i2, String str, int i3, boolean z);

    public static native int write(int i, int i2, String str, int i3, boolean z, long j, byte[] bArr);

    public static native int write(int i, int i2, String str, long j);

    public static native int write(int i, int i2, String str, String str2);

    public static native int write(int i, int i2, String str, String str2, int i3);

    public static native int write(int i, int i2, String str, String str2, int i3, int i4);

    public static native int write(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    public static native int write(int i, int i2, String str, String str2, int i3, int i4, String str3, int i5, String str4, int i6);

    public static native int write(int i, int i2, String str, String str2, int i3, long j);

    public static native int write(int i, int i2, String str, String str2, int i3, String str3, int i4, int i5, int i6);

    public static native int write(int i, int i2, String str, String str2, long j);

    public static native int write(int i, int i2, String str, String str2, long j, long j2);

    public static native int write(int i, int i2, String str, String str2, long j, long j2, long j3, int i3, long j4, long j5);

    public static native int write(int i, int i2, String str, String str2, long j, long j2, long j3, long j4, long j5);

    public static native int write(int i, int i2, String str, String str2, String str3, int i3, int i4, int i5, String str4);

    public static native int write(int i, int i2, String str, boolean z, int i3);

    public static native int write(int i, int i2, boolean z, int i3, int i4);

    public static native int write(int i, int i2, byte[] bArr);

    public static native int write(int i, long j);

    public static native int write(int i, long j, int i2, String str, int i3);

    public static native int write(int i, long j, int i2, String str, String str2);

    public static native int write(int i, long j, int i2, String str, String str2, boolean z);

    public static native int write(int i, long j, int i2, String str, String str2, boolean z, int i3);

    public static native int write(int i, long j, long j2, int i2, String str, String str2, boolean z);

    public static native int write(int i, long j, long j2, int i2, boolean z);

    public static native int write(int i, long j, long j2, String str, int i2, String str2, int i3);

    public static native int write(int i, long j, String str, long j2, long j3, String str2, String str3, byte[] bArr);

    public static native int write(int i, long j, String str, long j2, long j3, byte[] bArr);

    public static native int write(int i, String str);

    public static native int write(int i, String str, int i2);

    public static native int write(int i, String str, int i2, int i3, float f);

    public static native int write(int i, String str, int i2, String str2);

    public static native int write(int i, String str, int i2, String str2, int i3, int i4);

    public static native int write(int i, String str, int i2, String str2, int i3, int i4, int i5, boolean z, int i6, int i7, boolean z2, int i8, int i9, String str3);

    public static native int write(int i, String str, int i2, String str2, int i3, String str3, int i4, boolean z, boolean z2, boolean z3);

    public static native int write(int i, String str, long j);

    public static native int write(int i, String str, long j, long j2, boolean z);

    public static native int write(int i, String str, long j, boolean z, boolean z2, boolean z3, int i2, byte[] bArr, int i3);

    public static native int write(int i, String str, String str2);

    public static native int write(int i, String str, String str2, int i2, int i3, int i4, int i5, float f, float f2, boolean z, boolean z2, boolean z3);

    public static native int write(int i, String str, String str2, long j, long j2, long j3, long j4);

    public static native int write(int i, String str, String str2, String str3, String str4, String str5, boolean z, int i2, int i3);

    public static native int write(int i, String str, String str2, boolean z, boolean z2);

    public static native int write(int i, String str, boolean z);

    public static native int write(int i, boolean z);

    public static native int write(int i, boolean z, String str, long j, long j2);

    public static native int write(int i, byte[] bArr, int i2);

    public static native int write(int i, byte[] bArr, int i2, int i3);

    public static native int write(int i, byte[] bArr, int i2, int i3, int i4);

    public static native int write(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6);

    public static native int write(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    public static native int write(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, long j);

    public static native int write(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, long j, long j2, long j3, long j4);

    public static native int write(int i, byte[] bArr, int i2, int i3, int i4, long j, long j2, int i5, int i6, int i7);

    public static native int write(int i, byte[] bArr, int i2, int i3, byte[] bArr2);

    public static native int write(int i, byte[] bArr, int i2, String str, String str2, String str3, String str4, String str5);

    public static native int write(int i, byte[] bArr, long j, int i2);

    public static native int write(int i, byte[] bArr, long j, int i2, int i3, int i4);

    public static native int write(int i, int[] iArr, String[] strArr, int i2);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, int i3);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, int i3, String str);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, long j);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, long j, float f, String str, boolean z, int i3, byte[] bArr);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, String str);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, String str, int i3);

    public static native int write(int i, int[] iArr, String[] strArr, int i2, boolean z, boolean z2, boolean z3);

    public static native int write(int i, int[] iArr, String[] strArr, String str, int i2);

    public static native int write(int i, int[] iArr, String[] strArr, String str, int i2, int i3);

    public static native int write(int i, int[] iArr, String[] strArr, String str, int i2, int i3, int i4, int i5);

    public static native int write(int i, int[] iArr, String[] strArr, String str, String str2, int i2);

    public static native int write_non_chained(int i, int i2, String str, int i3);

    public static native int write_non_chained(int i, int i2, String str, int i3, int i4);

    public static native int write_non_chained(int i, int i2, String str, int i3, int i4, String str2);

    public static native int write_non_chained(int i, int i2, String str, int i3, long j);

    public static native int write_non_chained(int i, int i2, String str, int i3, long j, float f, String str2, boolean z, int i4, byte[] bArr);

    public static native int write_non_chained(int i, int i2, String str, int i3, String str2);

    public static native int write_non_chained(int i, int i2, String str, int i3, String str2, int i4);

    public static native int write_non_chained(int i, int i2, String str, int i3, boolean z, boolean z2, boolean z3);

    public static native int write_non_chained(int i, int i2, String str, String str2, int i3);

    public static native int write_non_chained(int i, int i2, String str, String str2, int i3, int i4);

    public static native int write_non_chained(int i, int i2, String str, String str2, int i3, int i4, int i5, int i6);

    public static native int write_non_chained(int i, int i2, String str, String str2, String str3, int i3);

    public static void write(int code, WorkSource ws, int arg2) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, boolean arg3, boolean arg4, boolean arg5) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4, arg5);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4, arg5);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, int arg3) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, int arg3, String arg4) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, long arg3) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, long arg3, float arg4, String arg5, boolean arg6, int arg7, byte[] arg8) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4, arg5, arg6, arg7, arg8);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4, arg5, arg6, arg7, arg8);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, String arg3) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3);
            }
        }
    }

    public static void write(int code, WorkSource ws, int arg2, String arg3, int arg4) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4);
            }
        }
    }

    public static void write(int code, WorkSource ws, String arg2, int arg3) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3);
            }
        }
    }

    public static void write(int code, WorkSource ws, String arg2, int arg3, int arg4) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4);
            }
        }
    }

    public static void write(int code, WorkSource ws, String arg2, int arg3, int arg4, int arg5, int arg6) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4, arg5, arg6);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4, arg5, arg6);
            }
        }
    }

    public static void write(int code, WorkSource ws, String arg2, String arg3, int arg4) {
        for (int i = 0; i < ws.size(); i++) {
            write_non_chained(code, ws.get(i), ws.getName(i), arg2, arg3, arg4);
        }
        ArrayList<WorkSource.WorkChain> workChains = ws.getWorkChains();
        if (workChains != null) {
            Iterator<WorkSource.WorkChain> it = workChains.iterator();
            while (it.hasNext()) {
                WorkSource.WorkChain wc = it.next();
                write(code, wc.getUids(), wc.getTags(), arg2, arg3, arg4);
            }
        }
    }
}
