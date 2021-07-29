package android.content;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ClipData;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import com.ibm.icu.text.PluralRules;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Intent implements Parcelable, Cloneable {
    public static final String ACTION_ADVANCED_SETTINGS_CHANGED = "android.intent.action.ADVANCED_SETTINGS";
    public static final String ACTION_AIRPLANE_MODE_CHANGED = "android.intent.action.AIRPLANE_MODE";
    @UnsupportedAppUsage
    public static final String ACTION_ALARM_CHANGED = "android.intent.action.ALARM_CHANGED";
    public static final String ACTION_ALL_APPS = "android.intent.action.ALL_APPS";
    public static final String ACTION_ANSWER = "android.intent.action.ANSWER";
    public static final String ACTION_APPLICATION_PREFERENCES = "android.intent.action.APPLICATION_PREFERENCES";
    public static final String ACTION_APPLICATION_RESTRICTIONS_CHANGED = "android.intent.action.APPLICATION_RESTRICTIONS_CHANGED";
    public static final String ACTION_APP_ERROR = "android.intent.action.APP_ERROR";
    public static final String ACTION_ASSIST = "android.intent.action.ASSIST";
    public static final String ACTION_ATTACH_DATA = "android.intent.action.ATTACH_DATA";
    public static final String ACTION_BATTERY_CHANGED = "android.intent.action.BATTERY_CHANGED";
    @SystemApi
    public static final String ACTION_BATTERY_LEVEL_CHANGED = "android.intent.action.BATTERY_LEVEL_CHANGED";
    public static final String ACTION_BATTERY_LOW = "android.intent.action.BATTERY_LOW";
    public static final String ACTION_BATTERY_OKAY = "android.intent.action.BATTERY_OKAY";
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_BUG_REPORT = "android.intent.action.BUG_REPORT";
    public static final String ACTION_CALL = "android.intent.action.CALL";
    public static final String ACTION_CALL_BUTTON = "android.intent.action.CALL_BUTTON";
    @SystemApi
    public static final String ACTION_CALL_EMERGENCY = "android.intent.action.CALL_EMERGENCY";
    @SystemApi
    public static final String ACTION_CALL_PRIVILEGED = "android.intent.action.CALL_PRIVILEGED";
    public static final String ACTION_CAMERA_BUTTON = "android.intent.action.CAMERA_BUTTON";
    public static final String ACTION_CANCEL_ENABLE_ROLLBACK = "android.intent.action.CANCEL_ENABLE_ROLLBACK";
    public static final String ACTION_CARRIER_SETUP = "android.intent.action.CARRIER_SETUP";
    public static final String ACTION_CHOOSER = "android.intent.action.CHOOSER";
    public static final String ACTION_CLEAR_DNS_CACHE = "android.intent.action.CLEAR_DNS_CACHE";
    public static final String ACTION_CLOSE_SYSTEM_DIALOGS = "android.intent.action.CLOSE_SYSTEM_DIALOGS";
    public static final String ACTION_CONFIGURATION_CHANGED = "android.intent.action.CONFIGURATION_CHANGED";
    public static final String ACTION_CREATE_DOCUMENT = "android.intent.action.CREATE_DOCUMENT";
    public static final String ACTION_CREATE_SHORTCUT = "android.intent.action.CREATE_SHORTCUT";
    public static final String ACTION_DATE_CHANGED = "android.intent.action.DATE_CHANGED";
    public static final String ACTION_DEFAULT = "android.intent.action.VIEW";
    public static final String ACTION_DEFINE = "android.intent.action.DEFINE";
    public static final String ACTION_DELETE = "android.intent.action.DELETE";
    @SystemApi
    public static final String ACTION_DEVICE_CUSTOMIZATION_READY = "android.intent.action.DEVICE_CUSTOMIZATION_READY";
    @SystemApi
    @Deprecated
    public static final String ACTION_DEVICE_INITIALIZATION_WIZARD = "android.intent.action.DEVICE_INITIALIZATION_WIZARD";
    public static final String ACTION_DEVICE_LOCKED_CHANGED = "android.intent.action.DEVICE_LOCKED_CHANGED";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_FULL = "android.intent.action.DEVICE_STORAGE_FULL";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_LOW = "android.intent.action.DEVICE_STORAGE_LOW";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_NOT_FULL = "android.intent.action.DEVICE_STORAGE_NOT_FULL";
    @Deprecated
    public static final String ACTION_DEVICE_STORAGE_OK = "android.intent.action.DEVICE_STORAGE_OK";
    public static final String ACTION_DIAL = "android.intent.action.DIAL";
    public static final String ACTION_DISMISS_KEYBOARD_SHORTCUTS = "com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS";
    public static final String ACTION_DISTRACTING_PACKAGES_CHANGED = "android.intent.action.DISTRACTING_PACKAGES_CHANGED";
    public static final String ACTION_DOCK_ACTIVE = "android.intent.action.DOCK_ACTIVE";
    public static final String ACTION_DOCK_EVENT = "android.intent.action.DOCK_EVENT";
    public static final String ACTION_DOCK_IDLE = "android.intent.action.DOCK_IDLE";
    public static final String ACTION_DREAMING_STARTED = "android.intent.action.DREAMING_STARTED";
    public static final String ACTION_DREAMING_STOPPED = "android.intent.action.DREAMING_STOPPED";
    public static final String ACTION_DYNAMIC_SENSOR_CHANGED = "android.intent.action.DYNAMIC_SENSOR_CHANGED";
    public static final String ACTION_EDIT = "android.intent.action.EDIT";
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    @SystemApi
    public static final String ACTION_FACTORY_RESET = "android.intent.action.FACTORY_RESET";
    public static final String ACTION_FACTORY_TEST = "android.intent.action.FACTORY_TEST";
    public static final String ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT";
    public static final String ACTION_GET_RESTRICTION_ENTRIES = "android.intent.action.GET_RESTRICTION_ENTRIES";
    @SystemApi
    public static final String ACTION_GLOBAL_BUTTON = "android.intent.action.GLOBAL_BUTTON";
    public static final String ACTION_GTALK_SERVICE_CONNECTED = "android.intent.action.GTALK_CONNECTED";
    public static final String ACTION_GTALK_SERVICE_DISCONNECTED = "android.intent.action.GTALK_DISCONNECTED";
    public static final String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final String ACTION_IDLE_MAINTENANCE_END = "android.intent.action.ACTION_IDLE_MAINTENANCE_END";
    public static final String ACTION_IDLE_MAINTENANCE_START = "android.intent.action.ACTION_IDLE_MAINTENANCE_START";
    @SystemApi
    public static final String ACTION_INCIDENT_REPORT_READY = "android.intent.action.INCIDENT_REPORT_READY";
    public static final String ACTION_INPUT_METHOD_CHANGED = "android.intent.action.INPUT_METHOD_CHANGED";
    public static final String ACTION_INSERT = "android.intent.action.INSERT";
    public static final String ACTION_INSERT_OR_EDIT = "android.intent.action.INSERT_OR_EDIT";
    public static final String ACTION_INSTALL_FAILURE = "android.intent.action.INSTALL_FAILURE";
    @SystemApi
    public static final String ACTION_INSTALL_INSTANT_APP_PACKAGE = "android.intent.action.INSTALL_INSTANT_APP_PACKAGE";
    @Deprecated
    public static final String ACTION_INSTALL_PACKAGE = "android.intent.action.INSTALL_PACKAGE";
    @SystemApi
    public static final String ACTION_INSTANT_APP_RESOLVER_SETTINGS = "android.intent.action.INSTANT_APP_RESOLVER_SETTINGS";
    @SystemApi
    public static final String ACTION_INTENT_FILTER_NEEDS_VERIFICATION = "android.intent.action.INTENT_FILTER_NEEDS_VERIFICATION";
    public static final String ACTION_LOCALE_CHANGED = "android.intent.action.LOCALE_CHANGED";
    public static final String ACTION_LOCKED_BOOT_COMPLETED = "android.intent.action.LOCKED_BOOT_COMPLETED";
    public static final String ACTION_MAIN = "android.intent.action.MAIN";
    public static final String ACTION_MANAGED_PROFILE_ADDED = "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final String ACTION_MANAGED_PROFILE_AVAILABLE = "android.intent.action.MANAGED_PROFILE_AVAILABLE";
    public static final String ACTION_MANAGED_PROFILE_REMOVED = "android.intent.action.MANAGED_PROFILE_REMOVED";
    public static final String ACTION_MANAGED_PROFILE_UNAVAILABLE = "android.intent.action.MANAGED_PROFILE_UNAVAILABLE";
    public static final String ACTION_MANAGED_PROFILE_UNLOCKED = "android.intent.action.MANAGED_PROFILE_UNLOCKED";
    @SystemApi
    public static final String ACTION_MANAGE_APP_PERMISSION = "android.intent.action.MANAGE_APP_PERMISSION";
    @SystemApi
    public static final String ACTION_MANAGE_APP_PERMISSIONS = "android.intent.action.MANAGE_APP_PERMISSIONS";
    @SystemApi
    public static final String ACTION_MANAGE_DEFAULT_APP = "android.intent.action.MANAGE_DEFAULT_APP";
    public static final String ACTION_MANAGE_NETWORK_USAGE = "android.intent.action.MANAGE_NETWORK_USAGE";
    public static final String ACTION_MANAGE_PACKAGE_STORAGE = "android.intent.action.MANAGE_PACKAGE_STORAGE";
    @SystemApi
    public static final String ACTION_MANAGE_PERMISSIONS = "android.intent.action.MANAGE_PERMISSIONS";
    @SystemApi
    public static final String ACTION_MANAGE_PERMISSION_APPS = "android.intent.action.MANAGE_PERMISSION_APPS";
    @SystemApi
    public static final String ACTION_MANAGE_SPECIAL_APP_ACCESSES = "android.intent.action.MANAGE_SPECIAL_APP_ACCESSES";
    @SystemApi
    @Deprecated
    public static final String ACTION_MASTER_CLEAR = "android.intent.action.MASTER_CLEAR";
    @SystemApi
    public static final String ACTION_MASTER_CLEAR_NOTIFICATION = "android.intent.action.MASTER_CLEAR_NOTIFICATION";
    public static final String ACTION_MEDIA_BAD_REMOVAL = "android.intent.action.MEDIA_BAD_REMOVAL";
    public static final String ACTION_MEDIA_BUTTON = "android.intent.action.MEDIA_BUTTON";
    public static final String ACTION_MEDIA_CHECKING = "android.intent.action.MEDIA_CHECKING";
    public static final String ACTION_MEDIA_EJECT = "android.intent.action.MEDIA_EJECT";
    public static final String ACTION_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
    public static final String ACTION_MEDIA_NOFS = "android.intent.action.MEDIA_NOFS";
    public static final String ACTION_MEDIA_REMOVED = "android.intent.action.MEDIA_REMOVED";
    public static final String ACTION_MEDIA_RESOURCE_GRANTED = "android.intent.action.MEDIA_RESOURCE_GRANTED";
    public static final String ACTION_MEDIA_SCANNER_FINISHED = "android.intent.action.MEDIA_SCANNER_FINISHED";
    @Deprecated
    public static final String ACTION_MEDIA_SCANNER_SCAN_FILE = "android.intent.action.MEDIA_SCANNER_SCAN_FILE";
    public static final String ACTION_MEDIA_SCANNER_STARTED = "android.intent.action.MEDIA_SCANNER_STARTED";
    public static final String ACTION_MEDIA_SHARED = "android.intent.action.MEDIA_SHARED";
    public static final String ACTION_MEDIA_UNMOUNTABLE = "android.intent.action.MEDIA_UNMOUNTABLE";
    public static final String ACTION_MEDIA_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED";
    public static final String ACTION_MEDIA_UNSHARED = "android.intent.action.MEDIA_UNSHARED";
    public static final String ACTION_MY_PACKAGE_REPLACED = "android.intent.action.MY_PACKAGE_REPLACED";
    public static final String ACTION_MY_PACKAGE_SUSPENDED = "android.intent.action.MY_PACKAGE_SUSPENDED";
    public static final String ACTION_MY_PACKAGE_UNSUSPENDED = "android.intent.action.MY_PACKAGE_UNSUSPENDED";
    @Deprecated
    public static final String ACTION_NEW_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
    public static final String ACTION_OPEN_DOCUMENT = "android.intent.action.OPEN_DOCUMENT";
    public static final String ACTION_OPEN_DOCUMENT_TREE = "android.intent.action.OPEN_DOCUMENT_TREE";
    public static final String ACTION_OVERLAY_CHANGED = "android.intent.action.OVERLAY_CHANGED";
    public static final String ACTION_PACKAGES_SUSPENDED = "android.intent.action.PACKAGES_SUSPENDED";
    public static final String ACTION_PACKAGES_UNSUSPENDED = "android.intent.action.PACKAGES_UNSUSPENDED";
    public static final String ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    public static final String ACTION_PACKAGE_CHANGED = "android.intent.action.PACKAGE_CHANGED";
    public static final String ACTION_PACKAGE_DATA_CLEARED = "android.intent.action.PACKAGE_DATA_CLEARED";
    public static final String ACTION_PACKAGE_ENABLE_ROLLBACK = "android.intent.action.PACKAGE_ENABLE_ROLLBACK";
    public static final String ACTION_PACKAGE_FIRST_LAUNCH = "android.intent.action.PACKAGE_FIRST_LAUNCH";
    public static final String ACTION_PACKAGE_FULLY_REMOVED = "android.intent.action.PACKAGE_FULLY_REMOVED";
    @Deprecated
    public static final String ACTION_PACKAGE_INSTALL = "android.intent.action.PACKAGE_INSTALL";
    public static final String ACTION_PACKAGE_NEEDS_OPTIONAL_VERIFICATION = "com.qualcomm.qti.intent.action.PACKAGE_NEEDS_OPTIONAL_VERIFICATION";
    public static final String ACTION_PACKAGE_NEEDS_VERIFICATION = "android.intent.action.PACKAGE_NEEDS_VERIFICATION";
    public static final String ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
    public static final String ACTION_PACKAGE_REPLACED = "android.intent.action.PACKAGE_REPLACED";
    public static final String ACTION_PACKAGE_RESTARTED = "android.intent.action.PACKAGE_RESTARTED";
    public static final String ACTION_PACKAGE_VERIFIED = "android.intent.action.PACKAGE_VERIFIED";
    public static final String ACTION_PASTE = "android.intent.action.PASTE";
    @SystemApi
    public static final String ACTION_PENDING_INCIDENT_REPORTS_CHANGED = "android.intent.action.PENDING_INCIDENT_REPORTS_CHANGED";
    public static final String ACTION_PICK = "android.intent.action.PICK";
    public static final String ACTION_PICK_ACTIVITY = "android.intent.action.PICK_ACTIVITY";
    public static final String ACTION_POWER_CONNECTED = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final String ACTION_POWER_DISCONNECTED = "android.intent.action.ACTION_POWER_DISCONNECTED";
    public static final String ACTION_POWER_USAGE_SUMMARY = "android.intent.action.POWER_USAGE_SUMMARY";
    public static final String ACTION_PREFERRED_ACTIVITY_CHANGED = "android.intent.action.ACTION_PREFERRED_ACTIVITY_CHANGED";
    @SystemApi
    public static final String ACTION_PRE_BOOT_COMPLETED = "android.intent.action.PRE_BOOT_COMPLETED";
    public static final String ACTION_PROCESS_TEXT = "android.intent.action.PROCESS_TEXT";
    public static final String ACTION_PROVIDER_CHANGED = "android.intent.action.PROVIDER_CHANGED";
    @SystemApi
    public static final String ACTION_QUERY_PACKAGE_RESTART = "android.intent.action.QUERY_PACKAGE_RESTART";
    public static final String ACTION_QUICK_CLOCK = "android.intent.action.QUICK_CLOCK";
    public static final String ACTION_QUICK_VIEW = "android.intent.action.QUICK_VIEW";
    public static final String ACTION_REBOOT = "android.intent.action.REBOOT";
    public static final String ACTION_REMOTE_INTENT = "com.google.android.c2dm.intent.RECEIVE";
    public static final String ACTION_REQUEST_SHUTDOWN = "com.android.internal.intent.action.REQUEST_SHUTDOWN";
    @SystemApi
    public static final String ACTION_RESOLVE_INSTANT_APP_PACKAGE = "android.intent.action.RESOLVE_INSTANT_APP_PACKAGE";
    @SystemApi
    public static final String ACTION_REVIEW_ACCESSIBILITY_SERVICES = "android.intent.action.REVIEW_ACCESSIBILITY_SERVICES";
    @SystemApi
    public static final String ACTION_REVIEW_ONGOING_PERMISSION_USAGE = "android.intent.action.REVIEW_ONGOING_PERMISSION_USAGE";
    @SystemApi
    public static final String ACTION_REVIEW_PERMISSIONS = "android.intent.action.REVIEW_PERMISSIONS";
    @SystemApi
    public static final String ACTION_REVIEW_PERMISSION_USAGE = "android.intent.action.REVIEW_PERMISSION_USAGE";
    @SystemApi
    public static final String ACTION_ROLLBACK_COMMITTED = "android.intent.action.ROLLBACK_COMMITTED";
    public static final String ACTION_RUN = "android.intent.action.RUN";
    public static final String ACTION_SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    public static final String ACTION_SCREEN_ON = "android.intent.action.SCREEN_ON";
    public static final String ACTION_SEARCH = "android.intent.action.SEARCH";
    public static final String ACTION_SEARCH_LONG_PRESS = "android.intent.action.SEARCH_LONG_PRESS";
    public static final String ACTION_SEND = "android.intent.action.SEND";
    public static final String ACTION_SENDTO = "android.intent.action.SENDTO";
    public static final String ACTION_SEND_MULTIPLE = "android.intent.action.SEND_MULTIPLE";
    @SystemApi
    @Deprecated
    public static final String ACTION_SERVICE_STATE = "android.intent.action.SERVICE_STATE";
    public static final String ACTION_SETTING_RESTORED = "android.os.action.SETTING_RESTORED";
    public static final String ACTION_SET_WALLPAPER = "android.intent.action.SET_WALLPAPER";
    public static final String ACTION_SHOW_APP_INFO = "android.intent.action.SHOW_APP_INFO";
    public static final String ACTION_SHOW_BRIGHTNESS_DIALOG = "com.android.intent.action.SHOW_BRIGHTNESS_DIALOG";
    public static final String ACTION_SHOW_KEYBOARD_SHORTCUTS = "com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS";
    @SystemApi
    public static final String ACTION_SHOW_SUSPENDED_APP_DETAILS = "android.intent.action.SHOW_SUSPENDED_APP_DETAILS";
    public static final String ACTION_SHUTDOWN = "android.intent.action.ACTION_SHUTDOWN";
    @SystemApi
    @Deprecated
    public static final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
    @SystemApi
    public static final String ACTION_SPLIT_CONFIGURATION_CHANGED = "android.intent.action.SPLIT_CONFIGURATION_CHANGED";
    public static final String ACTION_SYNC = "android.intent.action.SYNC";
    public static final String ACTION_SYSTEM_TUTORIAL = "android.intent.action.SYSTEM_TUTORIAL";
    public static final String ACTION_THERMAL_EVENT = "android.intent.action.THERMAL_EVENT";
    public static final String ACTION_TIMEZONE_CHANGED = "android.intent.action.TIMEZONE_CHANGED";
    public static final String ACTION_TIME_CHANGED = "android.intent.action.TIME_SET";
    public static final String ACTION_TIME_TICK = "android.intent.action.TIME_TICK";
    public static final String ACTION_TRANSLATE = "android.intent.action.TRANSLATE";
    public static final String ACTION_UID_REMOVED = "android.intent.action.UID_REMOVED";
    @Deprecated
    public static final String ACTION_UMS_CONNECTED = "android.intent.action.UMS_CONNECTED";
    @Deprecated
    public static final String ACTION_UMS_DISCONNECTED = "android.intent.action.UMS_DISCONNECTED";
    @Deprecated
    public static final String ACTION_UNINSTALL_PACKAGE = "android.intent.action.UNINSTALL_PACKAGE";
    @SystemApi
    public static final String ACTION_UPGRADE_SETUP = "android.intent.action.UPGRADE_SETUP";
    @SystemApi
    public static final String ACTION_USER_ADDED = "android.intent.action.USER_ADDED";
    public static final String ACTION_USER_BACKGROUND = "android.intent.action.USER_BACKGROUND";
    public static final String ACTION_USER_FOREGROUND = "android.intent.action.USER_FOREGROUND";
    public static final String ACTION_USER_INFO_CHANGED = "android.intent.action.USER_INFO_CHANGED";
    public static final String ACTION_USER_INITIALIZE = "android.intent.action.USER_INITIALIZE";
    public static final String ACTION_USER_PRESENT = "android.intent.action.USER_PRESENT";
    @SystemApi
    public static final String ACTION_USER_REMOVED = "android.intent.action.USER_REMOVED";
    public static final String ACTION_USER_STARTED = "android.intent.action.USER_STARTED";
    public static final String ACTION_USER_STARTING = "android.intent.action.USER_STARTING";
    public static final String ACTION_USER_STOPPED = "android.intent.action.USER_STOPPED";
    public static final String ACTION_USER_STOPPING = "android.intent.action.USER_STOPPING";
    @UnsupportedAppUsage
    public static final String ACTION_USER_SWITCHED = "android.intent.action.USER_SWITCHED";
    public static final String ACTION_USER_UNLOCKED = "android.intent.action.USER_UNLOCKED";
    public static final String ACTION_VIEW = "android.intent.action.VIEW";
    public static final String ACTION_VIEW_LOCUS = "android.intent.action.VIEW_LOCUS";
    public static final String ACTION_VIEW_PERMISSION_USAGE = "android.intent.action.VIEW_PERMISSION_USAGE";
    @SystemApi
    public static final String ACTION_VOICE_ASSIST = "android.intent.action.VOICE_ASSIST";
    public static final String ACTION_VOICE_COMMAND = "android.intent.action.VOICE_COMMAND";
    @Deprecated
    public static final String ACTION_WALLPAPER_CHANGED = "android.intent.action.WALLPAPER_CHANGED";
    public static final String ACTION_WEB_SEARCH = "android.intent.action.WEB_SEARCH";
    private static final String ATTR_ACTION = "action";
    private static final String ATTR_CATEGORY = "category";
    private static final String ATTR_COMPONENT = "component";
    private static final String ATTR_DATA = "data";
    private static final String ATTR_FLAGS = "flags";
    private static final String ATTR_IDENTIFIER = "ident";
    private static final String ATTR_TYPE = "type";
    public static final String CATEGORY_ALTERNATIVE = "android.intent.category.ALTERNATIVE";
    public static final String CATEGORY_APP_BROWSER = "android.intent.category.APP_BROWSER";
    public static final String CATEGORY_APP_CALCULATOR = "android.intent.category.APP_CALCULATOR";
    public static final String CATEGORY_APP_CALENDAR = "android.intent.category.APP_CALENDAR";
    public static final String CATEGORY_APP_CONTACTS = "android.intent.category.APP_CONTACTS";
    public static final String CATEGORY_APP_EMAIL = "android.intent.category.APP_EMAIL";
    public static final String CATEGORY_APP_FILES = "android.intent.category.APP_FILES";
    public static final String CATEGORY_APP_GALLERY = "android.intent.category.APP_GALLERY";
    public static final String CATEGORY_APP_MAPS = "android.intent.category.APP_MAPS";
    public static final String CATEGORY_APP_MARKET = "android.intent.category.APP_MARKET";
    public static final String CATEGORY_APP_MESSAGING = "android.intent.category.APP_MESSAGING";
    public static final String CATEGORY_APP_MUSIC = "android.intent.category.APP_MUSIC";
    public static final String CATEGORY_BROWSABLE = "android.intent.category.BROWSABLE";
    public static final String CATEGORY_CAR_DOCK = "android.intent.category.CAR_DOCK";
    public static final String CATEGORY_CAR_LAUNCHER = "android.intent.category.CAR_LAUNCHER";
    public static final String CATEGORY_CAR_MODE = "android.intent.category.CAR_MODE";
    public static final String CATEGORY_DEFAULT = "android.intent.category.DEFAULT";
    public static final String CATEGORY_DESK_DOCK = "android.intent.category.DESK_DOCK";
    public static final String CATEGORY_DEVELOPMENT_PREFERENCE = "android.intent.category.DEVELOPMENT_PREFERENCE";
    public static final String CATEGORY_EMBED = "android.intent.category.EMBED";
    public static final String CATEGORY_FRAMEWORK_INSTRUMENTATION_TEST = "android.intent.category.FRAMEWORK_INSTRUMENTATION_TEST";
    public static final String CATEGORY_HE_DESK_DOCK = "android.intent.category.HE_DESK_DOCK";
    public static final String CATEGORY_HOME = "android.intent.category.HOME";
    public static final String CATEGORY_HOME_MAIN = "android.intent.category.HOME_MAIN";
    public static final String CATEGORY_INFO = "android.intent.category.INFO";
    public static final String CATEGORY_LAUNCHER = "android.intent.category.LAUNCHER";
    public static final String CATEGORY_LAUNCHER_APP = "android.intent.category.LAUNCHER_APP";
    public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
    @SystemApi
    public static final String CATEGORY_LEANBACK_SETTINGS = "android.intent.category.LEANBACK_SETTINGS";
    public static final String CATEGORY_LE_DESK_DOCK = "android.intent.category.LE_DESK_DOCK";
    public static final String CATEGORY_MONKEY = "android.intent.category.MONKEY";
    public static final String CATEGORY_OPENABLE = "android.intent.category.OPENABLE";
    public static final String CATEGORY_PREFERENCE = "android.intent.category.PREFERENCE";
    public static final String CATEGORY_SAMPLE_CODE = "android.intent.category.SAMPLE_CODE";
    public static final String CATEGORY_SECONDARY_HOME = "android.intent.category.SECONDARY_HOME";
    public static final String CATEGORY_SELECTED_ALTERNATIVE = "android.intent.category.SELECTED_ALTERNATIVE";
    public static final String CATEGORY_SETUP_WIZARD = "android.intent.category.SETUP_WIZARD";
    public static final String CATEGORY_TAB = "android.intent.category.TAB";
    public static final String CATEGORY_TEST = "android.intent.category.TEST";
    public static final String CATEGORY_TYPED_OPENABLE = "android.intent.category.TYPED_OPENABLE";
    public static final String CATEGORY_UNIT_TEST = "android.intent.category.UNIT_TEST";
    public static final String CATEGORY_VOICE = "android.intent.category.VOICE";
    public static final String CATEGORY_VR_HOME = "android.intent.category.VR_HOME";
    private static final int COPY_MODE_ALL = 0;
    private static final int COPY_MODE_FILTER = 1;
    private static final int COPY_MODE_HISTORY = 2;
    public static final Parcelable.Creator<Intent> CREATOR = new Parcelable.Creator<Intent>() {
        public Intent createFromParcel(Parcel in) {
            return new Intent(in);
        }

        public Intent[] newArray(int size) {
            return new Intent[size];
        }
    };
    public static final String EXTRA_ALARM_COUNT = "android.intent.extra.ALARM_COUNT";
    public static final String EXTRA_ALLOW_MULTIPLE = "android.intent.extra.ALLOW_MULTIPLE";
    @Deprecated
    public static final String EXTRA_ALLOW_REPLACE = "android.intent.extra.ALLOW_REPLACE";
    public static final String EXTRA_ALTERNATE_INTENTS = "android.intent.extra.ALTERNATE_INTENTS";
    public static final String EXTRA_ASSIST_CONTEXT = "android.intent.extra.ASSIST_CONTEXT";
    public static final String EXTRA_ASSIST_INPUT_DEVICE_ID = "android.intent.extra.ASSIST_INPUT_DEVICE_ID";
    public static final String EXTRA_ASSIST_INPUT_HINT_KEYBOARD = "android.intent.extra.ASSIST_INPUT_HINT_KEYBOARD";
    public static final String EXTRA_ASSIST_PACKAGE = "android.intent.extra.ASSIST_PACKAGE";
    public static final String EXTRA_ASSIST_UID = "android.intent.extra.ASSIST_UID";
    public static final String EXTRA_AUTO_LAUNCH_SINGLE_CHOICE = "android.intent.extra.AUTO_LAUNCH_SINGLE_CHOICE";
    public static final String EXTRA_BCC = "android.intent.extra.BCC";
    public static final String EXTRA_BUG_REPORT = "android.intent.extra.BUG_REPORT";
    @SystemApi
    public static final String EXTRA_CALLING_PACKAGE = "android.intent.extra.CALLING_PACKAGE";
    public static final String EXTRA_CC = "android.intent.extra.CC";
    @SystemApi
    @Deprecated
    public static final String EXTRA_CDMA_DEFAULT_ROAMING_INDICATOR = "cdmaDefaultRoamingIndicator";
    @SystemApi
    @Deprecated
    public static final String EXTRA_CDMA_ROAMING_INDICATOR = "cdmaRoamingIndicator";
    @Deprecated
    public static final String EXTRA_CHANGED_COMPONENT_NAME = "android.intent.extra.changed_component_name";
    public static final String EXTRA_CHANGED_COMPONENT_NAME_LIST = "android.intent.extra.changed_component_name_list";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_CHOOSER_REFINEMENT_INTENT_SENDER = "android.intent.extra.CHOOSER_REFINEMENT_INTENT_SENDER";
    public static final String EXTRA_CHOOSER_TARGETS = "android.intent.extra.CHOOSER_TARGETS";
    public static final String EXTRA_CHOSEN_COMPONENT = "android.intent.extra.CHOSEN_COMPONENT";
    public static final String EXTRA_CHOSEN_COMPONENT_INTENT_SENDER = "android.intent.extra.CHOSEN_COMPONENT_INTENT_SENDER";
    public static final String EXTRA_CLIENT_INTENT = "android.intent.extra.client_intent";
    public static final String EXTRA_CLIENT_LABEL = "android.intent.extra.client_label";
    public static final String EXTRA_COMPONENT_NAME = "android.intent.extra.COMPONENT_NAME";
    public static final String EXTRA_CONTENT_ANNOTATIONS = "android.intent.extra.CONTENT_ANNOTATIONS";
    public static final String EXTRA_CONTENT_QUERY = "android.intent.extra.CONTENT_QUERY";
    @SystemApi
    @Deprecated
    public static final String EXTRA_CSS_INDICATOR = "cssIndicator";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_ALPHA_LONG = "data-operator-alpha-long";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_ALPHA_SHORT = "data-operator-alpha-short";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_OPERATOR_NUMERIC = "data-operator-numeric";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_RADIO_TECH = "dataRadioTechnology";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_REG_STATE = "dataRegState";
    public static final String EXTRA_DATA_REMOVED = "android.intent.extra.DATA_REMOVED";
    @SystemApi
    @Deprecated
    public static final String EXTRA_DATA_ROAMING_TYPE = "dataRoamingType";
    public static final String EXTRA_DISTRACTION_RESTRICTIONS = "android.intent.extra.distraction_restrictions";
    public static final String EXTRA_DOCK_STATE = "android.intent.extra.DOCK_STATE";
    public static final int EXTRA_DOCK_STATE_CAR = 2;
    public static final int EXTRA_DOCK_STATE_DESK = 1;
    public static final int EXTRA_DOCK_STATE_HE_DESK = 4;
    public static final int EXTRA_DOCK_STATE_LE_DESK = 3;
    public static final int EXTRA_DOCK_STATE_UNDOCKED = 0;
    public static final String EXTRA_DONT_KILL_APP = "android.intent.extra.DONT_KILL_APP";
    public static final String EXTRA_DURATION_MILLIS = "android.intent.extra.DURATION_MILLIS";
    public static final String EXTRA_EMAIL = "android.intent.extra.EMAIL";
    @SystemApi
    @Deprecated
    public static final String EXTRA_EMERGENCY_ONLY = "emergencyOnly";
    public static final String EXTRA_EXCLUDE_COMPONENTS = "android.intent.extra.EXCLUDE_COMPONENTS";
    @SystemApi
    public static final String EXTRA_FORCE_FACTORY_RESET = "android.intent.extra.FORCE_FACTORY_RESET";
    @Deprecated
    public static final String EXTRA_FORCE_MASTER_CLEAR = "android.intent.extra.FORCE_MASTER_CLEAR";
    public static final String EXTRA_FROM_STORAGE = "android.intent.extra.FROM_STORAGE";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final String EXTRA_INDEX = "android.intent.extra.INDEX";
    public static final String EXTRA_INITIAL_INTENTS = "android.intent.extra.INITIAL_INTENTS";
    public static final String EXTRA_INSTALLER_PACKAGE_NAME = "android.intent.extra.INSTALLER_PACKAGE_NAME";
    public static final String EXTRA_INSTALL_RESULT = "android.intent.extra.INSTALL_RESULT";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_ACTION = "android.intent.extra.INSTANT_APP_ACTION";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_BUNDLES = "android.intent.extra.INSTANT_APP_BUNDLES";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_EXTRAS = "android.intent.extra.INSTANT_APP_EXTRAS";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_FAILURE = "android.intent.extra.INSTANT_APP_FAILURE";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_HOSTNAME = "android.intent.extra.INSTANT_APP_HOSTNAME";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_SUCCESS = "android.intent.extra.INSTANT_APP_SUCCESS";
    @SystemApi
    public static final String EXTRA_INSTANT_APP_TOKEN = "android.intent.extra.INSTANT_APP_TOKEN";
    public static final String EXTRA_INTENT = "android.intent.extra.INTENT";
    @SystemApi
    @Deprecated
    public static final String EXTRA_IS_DATA_ROAMING_FROM_REGISTRATION = "isDataRoamingFromRegistration";
    @SystemApi
    @Deprecated
    public static final String EXTRA_IS_USING_CARRIER_AGGREGATION = "isUsingCarrierAggregation";
    public static final String EXTRA_KEY_CONFIRM = "android.intent.extra.KEY_CONFIRM";
    public static final String EXTRA_KEY_EVENT = "android.intent.extra.KEY_EVENT";
    public static final String EXTRA_LAUNCHER_EXTRAS = "android.intent.extra.LAUNCHER_EXTRAS";
    public static final String EXTRA_LOCAL_ONLY = "android.intent.extra.LOCAL_ONLY";
    public static final String EXTRA_LOCUS_ID = "android.intent.extra.LOCUS_ID";
    @SystemApi
    public static final String EXTRA_LONG_VERSION_CODE = "android.intent.extra.LONG_VERSION_CODE";
    @SystemApi
    @Deprecated
    public static final String EXTRA_LTE_EARFCN_RSRP_BOOST = "LteEarfcnRsrpBoost";
    @SystemApi
    @Deprecated
    public static final String EXTRA_MANUAL = "manual";
    public static final String EXTRA_MEDIA_RESOURCE_TYPE = "android.intent.extra.MEDIA_RESOURCE_TYPE";
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_AUDIO_CODEC = 1;
    public static final int EXTRA_MEDIA_RESOURCE_TYPE_VIDEO_CODEC = 0;
    public static final String EXTRA_MIME_TYPES = "android.intent.extra.MIME_TYPES";
    @SystemApi
    @Deprecated
    public static final String EXTRA_NETWORK_ID = "networkId";
    public static final String EXTRA_NOT_UNKNOWN_SOURCE = "android.intent.extra.NOT_UNKNOWN_SOURCE";
    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_ALPHA_LONG = "operator-alpha-long";
    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_ALPHA_SHORT = "operator-alpha-short";
    @SystemApi
    @Deprecated
    public static final String EXTRA_OPERATOR_NUMERIC = "operator-numeric";
    @SystemApi
    public static final String EXTRA_ORIGINATING_UID = "android.intent.extra.ORIGINATING_UID";
    public static final String EXTRA_ORIGINATING_URI = "android.intent.extra.ORIGINATING_URI";
    @SystemApi
    public static final String EXTRA_PACKAGES = "android.intent.extra.PACKAGES";
    public static final String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    @SystemApi
    public static final String EXTRA_PERMISSION_GROUP_NAME = "android.intent.extra.PERMISSION_GROUP_NAME";
    @SystemApi
    public static final String EXTRA_PERMISSION_NAME = "android.intent.extra.PERMISSION_NAME";
    public static final String EXTRA_PHONE_NUMBER = "android.intent.extra.PHONE_NUMBER";
    public static final String EXTRA_PROCESS_TEXT = "android.intent.extra.PROCESS_TEXT";
    public static final String EXTRA_PROCESS_TEXT_READONLY = "android.intent.extra.PROCESS_TEXT_READONLY";
    @Deprecated
    public static final String EXTRA_QUICK_VIEW_ADVANCED = "android.intent.extra.QUICK_VIEW_ADVANCED";
    public static final String EXTRA_QUICK_VIEW_FEATURES = "android.intent.extra.QUICK_VIEW_FEATURES";
    public static final String EXTRA_QUIET_MODE = "android.intent.extra.QUIET_MODE";
    @SystemApi
    public static final String EXTRA_REASON = "android.intent.extra.REASON";
    public static final String EXTRA_REFERRER = "android.intent.extra.REFERRER";
    public static final String EXTRA_REFERRER_NAME = "android.intent.extra.REFERRER_NAME";
    @SystemApi
    public static final String EXTRA_REMOTE_CALLBACK = "android.intent.extra.REMOTE_CALLBACK";
    public static final String EXTRA_REMOTE_INTENT_TOKEN = "android.intent.extra.remote_intent_token";
    public static final String EXTRA_REMOVED_FOR_ALL_USERS = "android.intent.extra.REMOVED_FOR_ALL_USERS";
    public static final String EXTRA_REPLACEMENT_EXTRAS = "android.intent.extra.REPLACEMENT_EXTRAS";
    public static final String EXTRA_REPLACING = "android.intent.extra.REPLACING";
    public static final String EXTRA_RESTRICTIONS_BUNDLE = "android.intent.extra.restrictions_bundle";
    public static final String EXTRA_RESTRICTIONS_INTENT = "android.intent.extra.restrictions_intent";
    public static final String EXTRA_RESTRICTIONS_LIST = "android.intent.extra.restrictions_list";
    @SystemApi
    public static final String EXTRA_RESULT_NEEDED = "android.intent.extra.RESULT_NEEDED";
    public static final String EXTRA_RESULT_RECEIVER = "android.intent.extra.RESULT_RECEIVER";
    public static final String EXTRA_RETURN_RESULT = "android.intent.extra.RETURN_RESULT";
    @SystemApi
    public static final String EXTRA_ROLE_NAME = "android.intent.extra.ROLE_NAME";
    public static final String EXTRA_SERVICE_STATE = "android.intent.extra.SERVICE_STATE";
    public static final String EXTRA_SETTING_NAME = "setting_name";
    public static final String EXTRA_SETTING_NEW_VALUE = "new_value";
    public static final String EXTRA_SETTING_PREVIOUS_VALUE = "previous_value";
    public static final String EXTRA_SETTING_RESTORED_FROM_SDK_INT = "restored_from_sdk_int";
    @Deprecated
    public static final String EXTRA_SHORTCUT_ICON = "android.intent.extra.shortcut.ICON";
    @Deprecated
    public static final String EXTRA_SHORTCUT_ICON_RESOURCE = "android.intent.extra.shortcut.ICON_RESOURCE";
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    @Deprecated
    public static final String EXTRA_SHORTCUT_INTENT = "android.intent.extra.shortcut.INTENT";
    @Deprecated
    public static final String EXTRA_SHORTCUT_NAME = "android.intent.extra.shortcut.NAME";
    public static final String EXTRA_SHUTDOWN_USERSPACE_ONLY = "android.intent.extra.SHUTDOWN_USERSPACE_ONLY";
    public static final String EXTRA_SIM_ACTIVATION_RESPONSE = "android.intent.extra.SIM_ACTIVATION_RESPONSE";
    public static final String EXTRA_SPLIT_NAME = "android.intent.extra.SPLIT_NAME";
    public static final String EXTRA_STREAM = "android.intent.extra.STREAM";
    public static final String EXTRA_SUBJECT = "android.intent.extra.SUBJECT";
    public static final String EXTRA_SUSPENDED_PACKAGE_EXTRAS = "android.intent.extra.SUSPENDED_PACKAGE_EXTRAS";
    @SystemApi
    @Deprecated
    public static final String EXTRA_SYSTEM_ID = "systemId";
    public static final String EXTRA_TASK_ID = "android.intent.extra.TASK_ID";
    public static final String EXTRA_TEMPLATE = "android.intent.extra.TEMPLATE";
    public static final String EXTRA_TEXT = "android.intent.extra.TEXT";
    public static final String EXTRA_THERMAL_STATE = "android.intent.extra.THERMAL_STATE";
    public static final int EXTRA_THERMAL_STATE_EXCEEDED = 2;
    public static final int EXTRA_THERMAL_STATE_NORMAL = 0;
    public static final int EXTRA_THERMAL_STATE_WARNING = 1;
    public static final String EXTRA_TIME_PREF_24_HOUR_FORMAT = "android.intent.extra.TIME_PREF_24_HOUR_FORMAT";
    public static final int EXTRA_TIME_PREF_VALUE_USE_12_HOUR = 0;
    public static final int EXTRA_TIME_PREF_VALUE_USE_24_HOUR = 1;
    public static final int EXTRA_TIME_PREF_VALUE_USE_LOCALE_DEFAULT = 2;
    public static final String EXTRA_TITLE = "android.intent.extra.TITLE";
    public static final String EXTRA_UID = "android.intent.extra.UID";
    public static final String EXTRA_UNINSTALL_ALL_USERS = "android.intent.extra.UNINSTALL_ALL_USERS";
    @SystemApi
    public static final String EXTRA_UNKNOWN_INSTANT_APP = "android.intent.extra.UNKNOWN_INSTANT_APP";
    public static final String EXTRA_USER = "android.intent.extra.USER";
    public static final String EXTRA_USER_HANDLE = "android.intent.extra.user_handle";
    public static final String EXTRA_USER_ID = "android.intent.extra.USER_ID";
    public static final String EXTRA_USER_REQUESTED_SHUTDOWN = "android.intent.extra.USER_REQUESTED_SHUTDOWN";
    @SystemApi
    public static final String EXTRA_VERIFICATION_BUNDLE = "android.intent.extra.VERIFICATION_BUNDLE";
    @Deprecated
    public static final String EXTRA_VERSION_CODE = "android.intent.extra.VERSION_CODE";
    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_RADIO_TECH = "radioTechnology";
    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_REG_STATE = "voiceRegState";
    @SystemApi
    @Deprecated
    public static final String EXTRA_VOICE_ROAMING_TYPE = "voiceRoamingType";
    public static final String EXTRA_WIPE_ESIMS = "com.android.internal.intent.extra.WIPE_ESIMS";
    public static final String EXTRA_WIPE_EXTERNAL_STORAGE = "android.intent.extra.WIPE_EXTERNAL_STORAGE";
    public static final int FILL_IN_ACTION = 1;
    public static final int FILL_IN_CATEGORIES = 4;
    public static final int FILL_IN_CLIP_DATA = 128;
    public static final int FILL_IN_COMPONENT = 8;
    public static final int FILL_IN_DATA = 2;
    public static final int FILL_IN_IDENTIFIER = 256;
    public static final int FILL_IN_PACKAGE = 16;
    public static final int FILL_IN_SELECTOR = 64;
    public static final int FILL_IN_SOURCE_BOUNDS = 32;
    public static final int FLAG_ACTIVITY_BROUGHT_TO_FRONT = 4194304;
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;
    @Deprecated
    public static final int FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET = 524288;
    public static final int FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS = 8388608;
    public static final int FLAG_ACTIVITY_FORWARD_RESULT = 33554432;
    public static final int FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY = 1048576;
    public static final int FLAG_ACTIVITY_LAUNCH_ADJACENT = 4096;
    public static final int FLAG_ACTIVITY_MATCH_EXTERNAL = 2048;
    public static final int FLAG_ACTIVITY_MULTIPLE_TASK = 134217728;
    public static final int FLAG_ACTIVITY_NEW_DOCUMENT = 524288;
    public static final int FLAG_ACTIVITY_NEW_TASK = 268435456;
    public static final int FLAG_ACTIVITY_NO_ANIMATION = 65536;
    public static final int FLAG_ACTIVITY_NO_HISTORY = 1073741824;
    public static final int FLAG_ACTIVITY_NO_USER_ACTION = 262144;
    public static final int FLAG_ACTIVITY_PREVIOUS_IS_TOP = 16777216;
    public static final int FLAG_ACTIVITY_REORDER_TO_FRONT = 131072;
    public static final int FLAG_ACTIVITY_RESET_TASK_IF_NEEDED = 2097152;
    public static final int FLAG_ACTIVITY_RETAIN_IN_RECENTS = 8192;
    public static final int FLAG_ACTIVITY_SINGLE_TOP = 536870912;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    public static final int FLAG_DEBUG_LOG_RESOLUTION = 8;
    @Deprecated
    public static final int FLAG_DEBUG_TRIAGED_MISSING = 256;
    public static final int FLAG_DIRECT_BOOT_AUTO = 256;
    public static final int FLAG_EXCLUDE_STOPPED_PACKAGES = 16;
    public static final int FLAG_FROM_BACKGROUND = 4;
    public static final int FLAG_GRANT_PERSISTABLE_URI_PERMISSION = 64;
    public static final int FLAG_GRANT_PREFIX_URI_PERMISSION = 128;
    public static final int FLAG_GRANT_READ_URI_PERMISSION = 1;
    public static final int FLAG_GRANT_WRITE_URI_PERMISSION = 2;
    public static final int FLAG_IGNORE_EPHEMERAL = 512;
    public static final int FLAG_INCLUDE_STOPPED_PACKAGES = 32;
    public static final int FLAG_RECEIVER_BOOT_UPGRADE = 33554432;
    public static final int FLAG_RECEIVER_EXCLUDE_BACKGROUND = 8388608;
    public static final int FLAG_RECEIVER_FOREGROUND = 268435456;
    public static final int FLAG_RECEIVER_FROM_SHELL = 4194304;
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 16777216;
    public static final int FLAG_RECEIVER_NO_ABORT = 134217728;
    public static final int FLAG_RECEIVER_OFFLOAD = Integer.MIN_VALUE;
    public static final int FLAG_RECEIVER_REGISTERED_ONLY = 1073741824;
    @UnsupportedAppUsage
    public static final int FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT = 67108864;
    public static final int FLAG_RECEIVER_REPLACE_PENDING = 536870912;
    public static final int FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS = 2097152;
    public static final int IMMUTABLE_FLAGS = 195;
    public static final String METADATA_DOCK_HOME = "android.dock_home";
    @SystemApi
    public static final String METADATA_SETUP_VERSION = "android.SETUP_VERSION";
    private static final String TAG = "Intent";
    private static final String TAG_CATEGORIES = "categories";
    private static final String TAG_EXTRA = "extra";
    public static final int URI_ALLOW_UNSAFE = 4;
    public static final int URI_ANDROID_APP_SCHEME = 2;
    public static final int URI_INTENT_SCHEME = 1;
    private String mAction;
    private ArraySet<String> mCategories;
    private ClipData mClipData;
    private ComponentName mComponent;
    private int mContentUserHint;
    private Uri mData;
    @UnsupportedAppUsage
    private Bundle mExtras;
    private int mFlags;
    private String mIdentifier;
    private String mLaunchToken;
    private String mPackage;
    private Intent mSelector;
    private Rect mSourceBounds;
    private String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AccessUriMode {
    }

    public interface CommandOptionHandler {
        boolean handleOption(String str, ShellCommand shellCommand);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CopyMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FillInFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface GrantUriMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MutableFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UriFlags {
    }

    public static class ShortcutIconResource implements Parcelable {
        public static final Parcelable.Creator<ShortcutIconResource> CREATOR = new Parcelable.Creator<ShortcutIconResource>() {
            public ShortcutIconResource createFromParcel(Parcel source) {
                ShortcutIconResource icon = new ShortcutIconResource();
                icon.packageName = source.readString();
                icon.resourceName = source.readString();
                return icon;
            }

            public ShortcutIconResource[] newArray(int size) {
                return new ShortcutIconResource[size];
            }
        };
        public String packageName;
        public String resourceName;

        public static ShortcutIconResource fromContext(Context context, int resourceId) {
            ShortcutIconResource icon = new ShortcutIconResource();
            icon.packageName = context.getPackageName();
            icon.resourceName = context.getResources().getResourceName(resourceId);
            return icon;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.packageName);
            dest.writeString(this.resourceName);
        }

        public String toString() {
            return this.resourceName;
        }
    }

    public static Intent createChooser(Intent target, CharSequence title) {
        return createChooser(target, title, (IntentSender) null);
    }

    public static Intent createChooser(Intent target, CharSequence title, IntentSender sender) {
        Intent intent = new Intent(ACTION_CHOOSER);
        intent.putExtra(EXTRA_INTENT, (Parcelable) target);
        if (title != null) {
            intent.putExtra(EXTRA_TITLE, title);
        }
        if (sender != null) {
            intent.putExtra(EXTRA_CHOSEN_COMPONENT_INTENT_SENDER, (Parcelable) sender);
        }
        int permFlags = target.getFlags() & 195;
        if (permFlags != 0) {
            ClipData targetClipData = target.getClipData();
            if (targetClipData == null && target.getData() != null) {
                targetClipData = new ClipData((CharSequence) null, target.getType() != null ? new String[]{target.getType()} : new String[0], new ClipData.Item(target.getData()));
            }
            if (targetClipData != null) {
                intent.setClipData(targetClipData);
                intent.addFlags(permFlags);
            }
        }
        return intent;
    }

    public static boolean isAccessUriMode(int modeFlags) {
        return (modeFlags & 3) != 0;
    }

    public Intent() {
        this.mContentUserHint = -2;
    }

    public Intent(Intent o) {
        this(o, 0);
    }

    private Intent(Intent o, int copyMode) {
        this.mContentUserHint = -2;
        this.mAction = o.mAction;
        this.mData = o.mData;
        this.mType = o.mType;
        this.mIdentifier = o.mIdentifier;
        this.mPackage = o.mPackage;
        this.mComponent = o.mComponent;
        if (o.mCategories != null) {
            this.mCategories = new ArraySet<>(o.mCategories);
        }
        if (copyMode != 1) {
            this.mFlags = o.mFlags;
            this.mContentUserHint = o.mContentUserHint;
            this.mLaunchToken = o.mLaunchToken;
            if (o.mSourceBounds != null) {
                this.mSourceBounds = new Rect(o.mSourceBounds);
            }
            if (o.mSelector != null) {
                this.mSelector = new Intent(o.mSelector);
            }
            if (copyMode != 2) {
                if (o.mExtras != null) {
                    this.mExtras = new Bundle(o.mExtras);
                }
                if (o.mClipData != null) {
                    this.mClipData = new ClipData(o.mClipData);
                }
            } else if (o.mExtras != null && !o.mExtras.maybeIsEmpty()) {
                this.mExtras = Bundle.STRIPPED;
            }
        }
    }

    public Object clone() {
        return new Intent(this);
    }

    public Intent cloneFilter() {
        return new Intent(this, 1);
    }

    public Intent(String action) {
        this.mContentUserHint = -2;
        setAction(action);
    }

    public Intent(String action, Uri uri) {
        this.mContentUserHint = -2;
        setAction(action);
        this.mData = uri;
    }

    public Intent(Context packageContext, Class<?> cls) {
        this.mContentUserHint = -2;
        this.mComponent = new ComponentName(packageContext, cls);
    }

    public Intent(String action, Uri uri, Context packageContext, Class<?> cls) {
        this.mContentUserHint = -2;
        setAction(action);
        this.mData = uri;
        this.mComponent = new ComponentName(packageContext, cls);
    }

    public static Intent makeMainActivity(ComponentName mainActivity) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.setComponent(mainActivity);
        intent.addCategory(CATEGORY_LAUNCHER);
        return intent;
    }

    public static Intent makeMainSelectorActivity(String selectorAction, String selectorCategory) {
        Intent intent = new Intent(ACTION_MAIN);
        intent.addCategory(CATEGORY_LAUNCHER);
        Intent selector = new Intent();
        selector.setAction(selectorAction);
        selector.addCategory(selectorCategory);
        intent.setSelector(selector);
        return intent;
    }

    public static Intent makeRestartActivityTask(ComponentName mainActivity) {
        Intent intent = makeMainActivity(mainActivity);
        intent.addFlags(268468224);
        return intent;
    }

    @Deprecated
    public static Intent getIntent(String uri) throws URISyntaxException {
        return parseUri(uri, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:169:0x0327 A[SYNTHETIC, Splitter:B:169:0x0327] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Intent parseUri(java.lang.String r18, int r19) throws java.net.URISyntaxException {
        /*
            r1 = r18
            r0 = 0
            r3 = r0
            java.lang.String r4 = "android-app:"
            boolean r4 = r1.startsWith(r4)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5 = r19 & 3
            if (r5 == 0) goto L_0x0034
            java.lang.String r5 = "intent:"
            boolean r5 = r1.startsWith(r5)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r5 != 0) goto L_0x0034
            if (r4 != 0) goto L_0x0034
            android.content.Intent r0 = new android.content.Intent     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r5 = "android.intent.action.VIEW"
            r0.<init>((java.lang.String) r5)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5 = r0
            android.net.Uri r0 = android.net.Uri.parse(r18)     // Catch:{ IllegalArgumentException -> 0x0029 }
            r5.setData(r0)     // Catch:{ IllegalArgumentException -> 0x0029 }
            return r5
        L_0x0029:
            r0 = move-exception
            java.net.URISyntaxException r6 = new java.net.URISyntaxException     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r7 = r0.getMessage()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r6.<init>(r1, r7)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            throw r6     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x0034:
            java.lang.String r5 = "#"
            int r5 = r1.lastIndexOf(r5)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r3 = r5
            r5 = -1
            if (r3 != r5) goto L_0x004c
            if (r4 != 0) goto L_0x005c
            android.content.Intent r0 = new android.content.Intent     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r5 = "android.intent.action.VIEW"
            android.net.Uri r6 = android.net.Uri.parse(r18)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.<init>((java.lang.String) r5, (android.net.Uri) r6)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            return r0
        L_0x004c:
            java.lang.String r5 = "#Intent;"
            boolean r5 = r1.startsWith(r5, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r5 != 0) goto L_0x005c
            if (r4 != 0) goto L_0x005b
            android.content.Intent r0 = getIntentOld(r18, r19)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            return r0
        L_0x005b:
            r3 = -1
        L_0x005c:
            android.content.Intent r5 = new android.content.Intent     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r6 = "android.intent.action.VIEW"
            r5.<init>((java.lang.String) r6)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r6 = r5
            r7 = 0
            r8 = 0
            r9 = 0
            if (r3 < 0) goto L_0x0070
            java.lang.String r10 = r1.substring(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            int r3 = r3 + 8
            goto L_0x0071
        L_0x0070:
            r10 = r1
        L_0x0071:
            if (r3 < 0) goto L_0x022d
            java.lang.String r11 = "end"
            boolean r11 = r1.startsWith(r11, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r11 != 0) goto L_0x022d
            r11 = 61
            int r11 = r1.indexOf(r11, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r11 >= 0) goto L_0x0085
            int r11 = r3 + -1
        L_0x0085:
            r12 = 59
            int r12 = r1.indexOf(r12, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r11 >= r12) goto L_0x0098
            int r13 = r11 + 1
            java.lang.String r13 = r1.substring(r13, r12)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r13 = android.net.Uri.decode(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x009a
        L_0x0098:
            java.lang.String r13 = ""
        L_0x009a:
            java.lang.String r14 = "action="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x00b2
            r5.setAction(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r8 != 0) goto L_0x00ac
            r7 = 1
        L_0x00a8:
            r16 = r10
            goto L_0x021c
        L_0x00ac:
            r17 = r9
            r16 = r10
            goto L_0x021a
        L_0x00b2:
            java.lang.String r14 = "category="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x00be
            r5.addCategory(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x00be:
            java.lang.String r14 = "type="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x00ca
            r5.mType = r13     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x00ca:
            java.lang.String r14 = "identifier="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x00d5
            r5.mIdentifier = r13     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x00d5:
            java.lang.String r14 = "launchFlags="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x00f2
            java.lang.Integer r14 = java.lang.Integer.decode(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            int r14 = r14.intValue()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mFlags = r14     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r14 = r19 & 4
            if (r14 != 0) goto L_0x00ac
            int r14 = r5.mFlags     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r14 = r14 & -196(0xffffffffffffff3c, float:NaN)
            r5.mFlags = r14     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x00f2:
            java.lang.String r14 = "package="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x00fe
            r5.mPackage = r13     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x00fe:
            java.lang.String r14 = "component="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x010d
            android.content.ComponentName r14 = android.content.ComponentName.unflattenFromString(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mComponent = r14     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x010d:
            java.lang.String r14 = "scheme="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x0134
            if (r8 == 0) goto L_0x0131
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r14.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r14.append(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r15 = ":"
            r14.append(r15)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r14 = r14.toString()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            android.net.Uri r14 = android.net.Uri.parse(r14)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mData = r14     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x0131:
            r9 = r13
            goto L_0x00a8
        L_0x0134:
            java.lang.String r14 = "sourceBounds="
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x0145
            android.graphics.Rect r14 = android.graphics.Rect.unflattenFromString(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mSourceBounds = r14     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x0145:
            int r14 = r3 + 3
            if (r12 != r14) goto L_0x015a
            java.lang.String r14 = "SEL"
            boolean r14 = r1.startsWith(r14, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r14 == 0) goto L_0x015a
            android.content.Intent r14 = new android.content.Intent     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r14.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5 = r14
            r8 = 1
            goto L_0x00a8
        L_0x015a:
            int r14 = r3 + 2
            java.lang.String r14 = r1.substring(r14, r11)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r14 = android.net.Uri.decode(r14)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            android.os.Bundle r15 = r5.mExtras     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r15 != 0) goto L_0x016f
            android.os.Bundle r15 = new android.os.Bundle     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mExtras = r15     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x016f:
            android.os.Bundle r15 = r5.mExtras     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r0 = "S."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x017e
            r15.putString(r14, r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x017e:
            java.lang.String r0 = "B."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x018f
            boolean r0 = java.lang.Boolean.parseBoolean(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putBoolean(r14, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x018f:
            java.lang.String r0 = "b."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x01a0
            byte r0 = java.lang.Byte.parseByte(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putByte(r14, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x01a0:
            java.lang.String r0 = "c."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x01b2
            r0 = 0
            char r2 = r13.charAt(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putChar(r14, r2)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x01b2:
            r0 = 0
            java.lang.String r2 = "d."
            boolean r2 = r1.startsWith(r2, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r2 == 0) goto L_0x01ce
            double r0 = java.lang.Double.parseDouble(r13)     // Catch:{ IndexOutOfBoundsException -> 0x01c9 }
            r15.putDouble(r14, r0)     // Catch:{ IndexOutOfBoundsException -> 0x01c9 }
            r17 = r9
            r16 = r10
            r1 = r18
            goto L_0x021a
        L_0x01c9:
            r0 = move-exception
            r1 = r18
            goto L_0x0341
        L_0x01ce:
            java.lang.String r0 = "f."
            r1 = r18
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x01e1
            float r0 = java.lang.Float.parseFloat(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putFloat(r14, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x01e1:
            java.lang.String r0 = "i."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x01f2
            int r0 = java.lang.Integer.parseInt(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putInt(r14, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x00ac
        L_0x01f2:
            java.lang.String r0 = "l."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x0206
            r17 = r9
            r16 = r10
            long r9 = java.lang.Long.parseLong(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putLong(r14, r9)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x021a
        L_0x0206:
            r17 = r9
            r16 = r10
            java.lang.String r0 = "s."
            boolean r0 = r1.startsWith(r0, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x0224
            short r0 = java.lang.Short.parseShort(r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r15.putShort(r14, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x021a:
            r9 = r17
        L_0x021c:
            int r3 = r12 + 1
            r10 = r16
            r0 = 0
            goto L_0x0071
        L_0x0224:
            java.net.URISyntaxException r0 = new java.net.URISyntaxException     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r2 = "unknown EXTRA type"
            r0.<init>(r1, r2, r3)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            throw r0     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x022d:
            r17 = r9
            r16 = r10
            if (r8 == 0) goto L_0x023b
            java.lang.String r0 = r6.mPackage     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 != 0) goto L_0x023a
            r6.setSelector(r5)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x023a:
            r5 = r6
        L_0x023b:
            if (r16 == 0) goto L_0x033b
            java.lang.String r0 = "intent:"
            r2 = r16
            boolean r0 = r2.startsWith(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x026c
            r0 = 7
            java.lang.String r0 = r2.substring(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r17 == 0) goto L_0x0267
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r2.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r9 = r17
            r2.append(r9)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r10 = 58
            r2.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r2.append(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r2 = r2.toString()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0 = r2
            goto L_0x031e
        L_0x0267:
            r9 = r17
            r10 = r0
            goto L_0x0321
        L_0x026c:
            r9 = r17
            java.lang.String r0 = "android-app:"
            boolean r0 = r2.startsWith(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 == 0) goto L_0x0320
            r0 = 12
            char r0 = r2.charAt(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r10 = 47
            if (r0 != r10) goto L_0x031c
            r0 = 13
            char r0 = r2.charAt(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 != r10) goto L_0x031c
            r0 = 14
            int r11 = r2.indexOf(r10, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r11 >= 0) goto L_0x02a1
            java.lang.String r0 = r2.substring(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mPackage = r0     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r7 != 0) goto L_0x029d
            java.lang.String r0 = "android.intent.action.MAIN"
            r5.setAction(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x029d:
            java.lang.String r0 = ""
            goto L_0x031b
        L_0x02a1:
            r12 = 0
            java.lang.String r0 = r2.substring(r0, r11)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r5.mPackage = r0     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            int r0 = r11 + 1
            int r13 = r2.length()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 >= r13) goto L_0x02e0
            int r0 = r11 + 1
            int r0 = r2.indexOf(r10, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r13 = r0
            if (r0 < 0) goto L_0x02d9
            int r0 = r11 + 1
            java.lang.String r0 = r2.substring(r0, r13)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r9 = r0
            r11 = r13
            int r0 = r2.length()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r11 >= r0) goto L_0x02e0
            int r0 = r11 + 1
            int r0 = r2.indexOf(r10, r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r10 = r0
            if (r0 < 0) goto L_0x02e0
            int r0 = r11 + 1
            java.lang.String r0 = r2.substring(r0, r10)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r12 = r0
            r11 = r10
            goto L_0x02e0
        L_0x02d9:
            int r0 = r11 + 1
            java.lang.String r0 = r2.substring(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r9 = r0
        L_0x02e0:
            if (r9 != 0) goto L_0x02ec
            if (r7 != 0) goto L_0x02e9
            java.lang.String r0 = "android.intent.action.MAIN"
            r5.setAction(r0)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x02e9:
            java.lang.String r0 = ""
            goto L_0x031b
        L_0x02ec:
            if (r12 != 0) goto L_0x0300
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.append(r9)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r10 = ":"
            r0.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r0 = r0.toString()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            goto L_0x031b
        L_0x0300:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.<init>()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.append(r9)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r10 = "://"
            r0.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.append(r12)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r10 = r2.substring(r11)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r0.append(r10)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r0 = r0.toString()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x031b:
            goto L_0x031e
        L_0x031c:
            java.lang.String r0 = ""
        L_0x031e:
            r10 = r0
            goto L_0x0321
        L_0x0320:
            r10 = r2
        L_0x0321:
            int r0 = r10.length()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            if (r0 <= 0) goto L_0x0339
            android.net.Uri r0 = android.net.Uri.parse(r10)     // Catch:{ IllegalArgumentException -> 0x032e }
            r5.mData = r0     // Catch:{ IllegalArgumentException -> 0x032e }
            goto L_0x0339
        L_0x032e:
            r0 = move-exception
            java.net.URISyntaxException r2 = new java.net.URISyntaxException     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            java.lang.String r11 = r0.getMessage()     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            r2.<init>(r1, r11)     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
            throw r2     // Catch:{ IndexOutOfBoundsException -> 0x0340 }
        L_0x0339:
            r2 = r10
            goto L_0x033f
        L_0x033b:
            r2 = r16
            r9 = r17
        L_0x033f:
            return r5
        L_0x0340:
            r0 = move-exception
        L_0x0341:
            java.net.URISyntaxException r2 = new java.net.URISyntaxException
            java.lang.String r4 = "illegal Intent URI format"
            r2.<init>(r1, r4, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.Intent.parseUri(java.lang.String, int):android.content.Intent");
    }

    public static Intent getIntentOld(String uri) throws URISyntaxException {
        return getIntentOld(uri, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01e2, code lost:
        throw new java.net.URISyntaxException(r1, "EXTRA missing '='", r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.Intent getIntentOld(java.lang.String r17, int r18) throws java.net.URISyntaxException {
        /*
            r1 = r17
            r0 = 35
            int r0 = r1.lastIndexOf(r0)
            if (r0 < 0) goto L_0x0207
            r2 = 0
            r3 = r0
            r4 = 0
            int r0 = r0 + 1
            java.lang.String r5 = "action("
            r6 = 7
            r7 = 0
            boolean r5 = r1.regionMatches(r0, r5, r7, r6)
            r8 = 41
            if (r5 == 0) goto L_0x0028
            r4 = 1
            int r0 = r0 + 7
            int r5 = r1.indexOf(r8, r0)
            java.lang.String r2 = r1.substring(r0, r5)
            int r0 = r5 + 1
        L_0x0028:
            android.content.Intent r5 = new android.content.Intent
            r5.<init>((java.lang.String) r2)
            java.lang.String r9 = "categories("
            r10 = 11
            boolean r9 = r1.regionMatches(r0, r9, r7, r10)
            r10 = 33
            if (r9 == 0) goto L_0x0059
            r4 = 1
            int r0 = r0 + 11
            int r9 = r1.indexOf(r8, r0)
        L_0x0040:
            if (r0 >= r9) goto L_0x0057
            int r11 = r1.indexOf(r10, r0)
            if (r11 < 0) goto L_0x004a
            if (r11 <= r9) goto L_0x004b
        L_0x004a:
            r11 = r9
        L_0x004b:
            if (r0 >= r11) goto L_0x0054
            java.lang.String r12 = r1.substring(r0, r11)
            r5.addCategory(r12)
        L_0x0054:
            int r0 = r11 + 1
            goto L_0x0040
        L_0x0057:
            int r0 = r9 + 1
        L_0x0059:
            java.lang.String r9 = "type("
            r11 = 5
            boolean r9 = r1.regionMatches(r0, r9, r7, r11)
            if (r9 == 0) goto L_0x0072
            r4 = 1
            int r0 = r0 + 5
            int r9 = r1.indexOf(r8, r0)
            java.lang.String r11 = r1.substring(r0, r9)
            r5.mType = r11
            int r0 = r9 + 1
        L_0x0072:
            java.lang.String r9 = "launchFlags("
            r11 = 12
            boolean r9 = r1.regionMatches(r0, r9, r7, r11)
            if (r9 == 0) goto L_0x009d
            r4 = 1
            int r0 = r0 + 12
            int r9 = r1.indexOf(r8, r0)
            java.lang.String r11 = r1.substring(r0, r9)
            java.lang.Integer r11 = java.lang.Integer.decode(r11)
            int r11 = r11.intValue()
            r5.mFlags = r11
            r12 = r18 & 4
            if (r12 != 0) goto L_0x009b
            int r12 = r5.mFlags
            r12 = r12 & -196(0xffffffffffffff3c, float:NaN)
            r5.mFlags = r12
        L_0x009b:
            int r0 = r9 + 1
        L_0x009d:
            java.lang.String r9 = "component("
            r12 = 10
            boolean r9 = r1.regionMatches(r0, r9, r7, r12)
            if (r9 == 0) goto L_0x00c9
            r4 = 1
            int r0 = r0 + 10
            int r9 = r1.indexOf(r8, r0)
            int r12 = r1.indexOf(r10, r0)
            if (r12 < 0) goto L_0x00c7
            if (r12 >= r9) goto L_0x00c7
            java.lang.String r13 = r1.substring(r0, r12)
            int r14 = r12 + 1
            java.lang.String r14 = r1.substring(r14, r9)
            android.content.ComponentName r15 = new android.content.ComponentName
            r15.<init>((java.lang.String) r13, (java.lang.String) r14)
            r5.mComponent = r15
        L_0x00c7:
            int r0 = r9 + 1
        L_0x00c9:
            java.lang.String r9 = "extras("
            boolean r6 = r1.regionMatches(r0, r9, r7, r6)
            if (r6 == 0) goto L_0x01eb
            r4 = 1
            int r0 = r0 + 7
            int r6 = r1.indexOf(r8, r0)
            r9 = -1
            if (r6 == r9) goto L_0x01e3
        L_0x00db:
            if (r0 >= r6) goto L_0x01eb
            r12 = 61
            int r12 = r1.indexOf(r12, r0)
            int r13 = r0 + 1
            if (r12 <= r13) goto L_0x01db
            if (r0 >= r6) goto L_0x01db
            char r13 = r1.charAt(r0)
            int r0 = r0 + 1
            java.lang.String r14 = r1.substring(r0, r12)
            int r0 = r12 + 1
            int r12 = r1.indexOf(r10, r0)
            if (r12 == r9) goto L_0x00fd
            if (r12 < r6) goto L_0x00fe
        L_0x00fd:
            r12 = r6
        L_0x00fe:
            if (r0 >= r12) goto L_0x01d3
            java.lang.String r15 = r1.substring(r0, r12)
            r16 = r12
            android.os.Bundle r0 = r5.mExtras
            if (r0 != 0) goto L_0x0111
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            r5.mExtras = r0
        L_0x0111:
            r0 = 66
            if (r13 == r0) goto L_0x01a3
            r0 = 83
            if (r13 == r0) goto L_0x0197
            r0 = 102(0x66, float:1.43E-43)
            if (r13 == r0) goto L_0x018b
            r0 = 105(0x69, float:1.47E-43)
            if (r13 == r0) goto L_0x017f
            r0 = 108(0x6c, float:1.51E-43)
            if (r13 == r0) goto L_0x0173
            r0 = 115(0x73, float:1.61E-43)
            if (r13 == r0) goto L_0x0167
            switch(r13) {
                case 98: goto L_0x015b;
                case 99: goto L_0x014b;
                case 100: goto L_0x013f;
                default: goto L_0x012c;
            }
        L_0x012c:
            java.net.URISyntaxException r0 = new java.net.URISyntaxException     // Catch:{ NumberFormatException -> 0x013a }
            java.lang.String r7 = "EXTRA has unknown type"
            r8 = r16
            r0.<init>(r1, r7, r8)     // Catch:{ NumberFormatException -> 0x0136 }
            throw r0     // Catch:{ NumberFormatException -> 0x0136 }
        L_0x0136:
            r0 = move-exception
            r9 = r8
            goto L_0x01cb
        L_0x013a:
            r0 = move-exception
            r9 = r16
            goto L_0x01cb
        L_0x013f:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            double r10 = java.lang.Double.parseDouble(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putDouble(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x014b:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            java.lang.String r10 = android.net.Uri.decode(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            char r10 = r10.charAt(r7)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putChar(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x015b:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            byte r10 = java.lang.Byte.parseByte(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putByte(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x0167:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            short r10 = java.lang.Short.parseShort(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putShort(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x0173:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            long r10 = java.lang.Long.parseLong(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putLong(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x017f:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            int r10 = java.lang.Integer.parseInt(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putInt(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x018b:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            float r10 = java.lang.Float.parseFloat(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putFloat(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x0197:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            java.lang.String r10 = android.net.Uri.decode(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putString(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
            goto L_0x01af
        L_0x01a3:
            r9 = r16
            android.os.Bundle r0 = r5.mExtras     // Catch:{ NumberFormatException -> 0x01ca }
            boolean r10 = java.lang.Boolean.parseBoolean(r15)     // Catch:{ NumberFormatException -> 0x01ca }
            r0.putBoolean(r14, r10)     // Catch:{ NumberFormatException -> 0x01ca }
        L_0x01af:
            char r0 = r1.charAt(r9)
            if (r0 != r8) goto L_0x01b8
            r0 = r9
            goto L_0x01eb
        L_0x01b8:
            r10 = 33
            if (r0 != r10) goto L_0x01c2
            int r0 = r9 + 1
            r9 = -1
            goto L_0x00db
        L_0x01c2:
            java.net.URISyntaxException r7 = new java.net.URISyntaxException
            java.lang.String r8 = "EXTRA missing '!'"
            r7.<init>(r1, r8, r9)
            throw r7
        L_0x01ca:
            r0 = move-exception
        L_0x01cb:
            java.net.URISyntaxException r7 = new java.net.URISyntaxException
            java.lang.String r8 = "EXTRA value can't be parsed"
            r7.<init>(r1, r8, r9)
            throw r7
        L_0x01d3:
            java.net.URISyntaxException r7 = new java.net.URISyntaxException
            java.lang.String r8 = "EXTRA missing '!'"
            r7.<init>(r1, r8, r0)
            throw r7
        L_0x01db:
            java.net.URISyntaxException r7 = new java.net.URISyntaxException
            java.lang.String r8 = "EXTRA missing '='"
            r7.<init>(r1, r8, r0)
            throw r7
        L_0x01e3:
            java.net.URISyntaxException r7 = new java.net.URISyntaxException
            java.lang.String r8 = "EXTRA missing trailing ')'"
            r7.<init>(r1, r8, r0)
            throw r7
        L_0x01eb:
            if (r4 == 0) goto L_0x01f8
            java.lang.String r6 = r1.substring(r7, r3)
            android.net.Uri r6 = android.net.Uri.parse(r6)
            r5.mData = r6
            goto L_0x01fe
        L_0x01f8:
            android.net.Uri r6 = android.net.Uri.parse(r17)
            r5.mData = r6
        L_0x01fe:
            java.lang.String r6 = r5.mAction
            if (r6 != 0) goto L_0x0206
            java.lang.String r6 = "android.intent.action.VIEW"
            r5.mAction = r6
        L_0x0206:
            goto L_0x0212
        L_0x0207:
            android.content.Intent r5 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.VIEW"
            android.net.Uri r3 = android.net.Uri.parse(r17)
            r5.<init>((java.lang.String) r2, (android.net.Uri) r3)
        L_0x0212:
            r2 = r5
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.Intent.getIntentOld(java.lang.String, int):android.content.Intent");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x037b, code lost:
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x04ab, code lost:
        r5 = r17;
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x0619, code lost:
        r0 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c1, code lost:
        if (r8.equals("--activity-match-external") != false) goto L_0x029d;
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Intent parseCommandArgs(android.os.ShellCommand r17, android.content.Intent.CommandOptionHandler r18) throws java.net.URISyntaxException {
        /*
            r1 = r18
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            r2 = r0
            r3 = 0
            r4 = 0
            r6 = r3
            r3 = r0
            r0 = 0
        L_0x000d:
            r7 = r0
            java.lang.String r0 = r17.getNextOption()
            r8 = r0
            r9 = 47
            r10 = 7
            if (r0 == 0) goto L_0x0635
            int r13 = r8.hashCode()
            r14 = 8
            r15 = 32
            r0 = 16
            r11 = 2
            switch(r13) {
                case 1494: goto L_0x0292;
                case 1495: goto L_0x0288;
                case 1496: goto L_0x027e;
                case 1497: goto L_0x0273;
                default: goto L_0x0026;
            }
        L_0x0026:
            switch(r13) {
                case -2147394086: goto L_0x0268;
                case -2118172637: goto L_0x025d;
                case -1630559130: goto L_0x0252;
                case -1252939549: goto L_0x0247;
                case -1069446353: goto L_0x023c;
                case -848214457: goto L_0x0231;
                case -833172539: goto L_0x0226;
                case -792169302: goto L_0x021a;
                case -780160399: goto L_0x020e;
                case 1492: goto L_0x0203;
                case 1500: goto L_0x01f8;
                case 1505: goto L_0x01ec;
                case 1507: goto L_0x01e0;
                case 1511: goto L_0x01d5;
                case 1387073: goto L_0x01ca;
                case 1387076: goto L_0x01bf;
                case 1387079: goto L_0x01b3;
                case 1387086: goto L_0x01a8;
                case 1387088: goto L_0x019c;
                case 1387093: goto L_0x0190;
                case 42999280: goto L_0x0184;
                case 42999360: goto L_0x0178;
                case 42999453: goto L_0x016c;
                case 42999546: goto L_0x0160;
                case 42999763: goto L_0x0154;
                case 42999776: goto L_0x0149;
                case 69120454: goto L_0x013d;
                case 88747734: goto L_0x0131;
                case 190913209: goto L_0x0125;
                case 236677687: goto L_0x0119;
                case 429439306: goto L_0x010d;
                case 436286937: goto L_0x0101;
                case 438531630: goto L_0x00f5;
                case 527014976: goto L_0x00e9;
                case 580418080: goto L_0x00dd;
                case 749648146: goto L_0x00d1;
                case 775126336: goto L_0x00c5;
                case 1110195121: goto L_0x00bb;
                case 1207327103: goto L_0x00af;
                case 1332980268: goto L_0x00a3;
                case 1332983151: goto L_0x0097;
                case 1332986034: goto L_0x008b;
                case 1332992761: goto L_0x007f;
                case 1353919836: goto L_0x0073;
                case 1398403374: goto L_0x0067;
                case 1453225122: goto L_0x005b;
                case 1652786753: goto L_0x004f;
                case 1742380566: goto L_0x0043;
                case 1765369476: goto L_0x0037;
                case 1816558127: goto L_0x002b;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x029c
        L_0x002b:
            java.lang.String r9 = "--grant-write-uri-permission"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 26
            goto L_0x029d
        L_0x0037:
            java.lang.String r9 = "--activity-multiple-task"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 37
            goto L_0x029d
        L_0x0043:
            java.lang.String r9 = "--grant-read-uri-permission"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 25
            goto L_0x029d
        L_0x004f:
            java.lang.String r9 = "--receiver-foreground"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 50
            goto L_0x029d
        L_0x005b:
            java.lang.String r9 = "--receiver-no-abort"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 51
            goto L_0x029d
        L_0x0067:
            java.lang.String r9 = "--activity-launched-from-history"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 36
            goto L_0x029d
        L_0x0073:
            java.lang.String r9 = "--activity-clear-when-task-reset"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 34
            goto L_0x029d
        L_0x007f:
            java.lang.String r9 = "--esal"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 20
            goto L_0x029d
        L_0x008b:
            java.lang.String r9 = "--elal"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 15
            goto L_0x029d
        L_0x0097:
            java.lang.String r9 = "--eial"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 12
            goto L_0x029d
        L_0x00a3:
            java.lang.String r9 = "--efal"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 18
            goto L_0x029d
        L_0x00af:
            java.lang.String r9 = "--selector"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 53
            goto L_0x029d
        L_0x00bb:
            java.lang.String r10 = "--activity-match-external"
            boolean r10 = r8.equals(r10)
            if (r10 == 0) goto L_0x029c
            goto L_0x029d
        L_0x00c5:
            java.lang.String r9 = "--receiver-replace-pending"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 49
            goto L_0x029d
        L_0x00d1:
            java.lang.String r9 = "--include-stopped-packages"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 30
            goto L_0x029d
        L_0x00dd:
            java.lang.String r9 = "--exclude-stopped-packages"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 29
            goto L_0x029d
        L_0x00e9:
            java.lang.String r9 = "--grant-persistable-uri-permission"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 27
            goto L_0x029d
        L_0x00f5:
            java.lang.String r9 = "--activity-single-top"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 44
            goto L_0x029d
        L_0x0101:
            java.lang.String r9 = "--receiver-registered-only"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 48
            goto L_0x029d
        L_0x010d:
            java.lang.String r9 = "--activity-no-user-action"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 40
            goto L_0x029d
        L_0x0119:
            java.lang.String r9 = "--activity-clear-top"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 33
            goto L_0x029d
        L_0x0125:
            java.lang.String r9 = "--activity-reset-task-if-needed"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 43
            goto L_0x029d
        L_0x0131:
            java.lang.String r9 = "--activity-no-animation"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 38
            goto L_0x029d
        L_0x013d:
            java.lang.String r9 = "--activity-exclude-from-recents"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 35
            goto L_0x029d
        L_0x0149:
            java.lang.String r9 = "--esn"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = r10
            goto L_0x029d
        L_0x0154:
            java.lang.String r9 = "--esa"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 19
            goto L_0x029d
        L_0x0160:
            java.lang.String r9 = "--ela"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 14
            goto L_0x029d
        L_0x016c:
            java.lang.String r9 = "--eia"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 11
            goto L_0x029d
        L_0x0178:
            java.lang.String r9 = "--efa"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 17
            goto L_0x029d
        L_0x0184:
            java.lang.String r9 = "--ecn"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 10
            goto L_0x029d
        L_0x0190:
            java.lang.String r9 = "--ez"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 21
            goto L_0x029d
        L_0x019c:
            java.lang.String r9 = "--eu"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 9
            goto L_0x029d
        L_0x01a8:
            java.lang.String r9 = "--es"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 6
            goto L_0x029d
        L_0x01b3:
            java.lang.String r9 = "--el"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 13
            goto L_0x029d
        L_0x01bf:
            java.lang.String r9 = "--ei"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = r14
            goto L_0x029d
        L_0x01ca:
            java.lang.String r9 = "--ef"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = r0
            goto L_0x029d
        L_0x01d5:
            java.lang.String r9 = "-t"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = r11
            goto L_0x029d
        L_0x01e0:
            java.lang.String r9 = "-p"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 23
            goto L_0x029d
        L_0x01ec:
            java.lang.String r9 = "-n"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 22
            goto L_0x029d
        L_0x01f8:
            java.lang.String r9 = "-i"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 3
            goto L_0x029d
        L_0x0203:
            java.lang.String r9 = "-a"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 0
            goto L_0x029d
        L_0x020e:
            java.lang.String r9 = "--receiver-include-background"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 52
            goto L_0x029d
        L_0x021a:
            java.lang.String r9 = "--activity-previous-is-top"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 41
            goto L_0x029d
        L_0x0226:
            java.lang.String r9 = "--activity-brought-to-front"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = r15
            goto L_0x029d
        L_0x0231:
            java.lang.String r9 = "--activity-reorder-to-front"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 42
            goto L_0x029d
        L_0x023c:
            java.lang.String r9 = "--debug-log-resolution"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 31
            goto L_0x029d
        L_0x0247:
            java.lang.String r9 = "--activity-clear-task"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 45
            goto L_0x029d
        L_0x0252:
            java.lang.String r9 = "--activity-no-history"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 39
            goto L_0x029d
        L_0x025d:
            java.lang.String r9 = "--activity-task-on-home"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 46
            goto L_0x029d
        L_0x0268:
            java.lang.String r9 = "--grant-prefix-uri-permission"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 28
            goto L_0x029d
        L_0x0273:
            java.lang.String r9 = "-f"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 24
            goto L_0x029d
        L_0x027e:
            java.lang.String r9 = "-e"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 5
            goto L_0x029d
        L_0x0288:
            java.lang.String r9 = "-d"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 1
            goto L_0x029d
        L_0x0292:
            java.lang.String r9 = "-c"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x029c
            r9 = 4
            goto L_0x029d
        L_0x029c:
            r9 = -1
        L_0x029d:
            r10 = 536870912(0x20000000, float:1.0842022E-19)
            r13 = 16777216(0x1000000, float:2.3509887E-38)
            r5 = 1073741824(0x40000000, float:2.0)
            r12 = 134217728(0x8000000, float:3.85186E-34)
            switch(r9) {
                case 0: goto L_0x060d;
                case 1: goto L_0x05fa;
                case 2: goto L_0x05ef;
                case 3: goto L_0x05e3;
                case 4: goto L_0x05d7;
                case 5: goto L_0x05ca;
                case 6: goto L_0x05ca;
                case 7: goto L_0x05bd;
                case 8: goto L_0x05ac;
                case 9: goto L_0x059b;
                case 10: goto L_0x0571;
                case 11: goto L_0x0545;
                case 12: goto L_0x0519;
                case 13: goto L_0x0508;
                case 14: goto L_0x04dc;
                case 15: goto L_0x04b0;
                case 16: goto L_0x049a;
                case 17: goto L_0x046e;
                case 18: goto L_0x0442;
                case 19: goto L_0x042f;
                case 20: goto L_0x0406;
                case 21: goto L_0x03a7;
                case 22: goto L_0x037f;
                case 23: goto L_0x0370;
                case 24: goto L_0x0360;
                case 25: goto L_0x035b;
                case 26: goto L_0x0357;
                case 27: goto L_0x0351;
                case 28: goto L_0x034b;
                case 29: goto L_0x0347;
                case 30: goto L_0x0343;
                case 31: goto L_0x033f;
                case 32: goto L_0x0339;
                case 33: goto L_0x0333;
                case 34: goto L_0x032d;
                case 35: goto L_0x0327;
                case 36: goto L_0x0321;
                case 37: goto L_0x031d;
                case 38: goto L_0x0317;
                case 39: goto L_0x0312;
                case 40: goto L_0x030b;
                case 41: goto L_0x0306;
                case 42: goto L_0x02ff;
                case 43: goto L_0x02f8;
                case 44: goto L_0x02f3;
                case 45: goto L_0x02eb;
                case 46: goto L_0x02e4;
                case 47: goto L_0x02dd;
                case 48: goto L_0x02d8;
                case 49: goto L_0x02d3;
                case 50: goto L_0x02cc;
                case 51: goto L_0x02c7;
                case 52: goto L_0x02c2;
                case 53: goto L_0x02b4;
                default: goto L_0x02a8;
            }
        L_0x02a8:
            if (r1 == 0) goto L_0x061c
            r5 = r17
            boolean r0 = r1.handleOption(r8, r5)
            if (r0 == 0) goto L_0x061e
            goto L_0x0619
        L_0x02b4:
            r3.setDataAndType(r4, r7)
            android.content.Intent r0 = new android.content.Intent
            r0.<init>()
            r5 = r17
            r3 = r0
            goto L_0x0619
        L_0x02c2:
            r3.addFlags(r13)
            goto L_0x037b
        L_0x02c7:
            r3.addFlags(r12)
            goto L_0x037b
        L_0x02cc:
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x02d3:
            r3.addFlags(r10)
            goto L_0x037b
        L_0x02d8:
            r3.addFlags(r5)
            goto L_0x037b
        L_0x02dd:
            r0 = 2048(0x800, float:2.87E-42)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x02e4:
            r0 = 16384(0x4000, float:2.2959E-41)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x02eb:
            r0 = 32768(0x8000, float:4.5918E-41)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x02f3:
            r3.addFlags(r10)
            goto L_0x037b
        L_0x02f8:
            r0 = 2097152(0x200000, float:2.938736E-39)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x02ff:
            r0 = 131072(0x20000, float:1.83671E-40)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0306:
            r3.addFlags(r13)
            goto L_0x037b
        L_0x030b:
            r0 = 262144(0x40000, float:3.67342E-40)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0312:
            r3.addFlags(r5)
            goto L_0x037b
        L_0x0317:
            r0 = 65536(0x10000, float:9.18355E-41)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x031d:
            r3.addFlags(r12)
            goto L_0x037b
        L_0x0321:
            r0 = 1048576(0x100000, float:1.469368E-39)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0327:
            r0 = 8388608(0x800000, float:1.17549435E-38)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x032d:
            r0 = 524288(0x80000, float:7.34684E-40)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0333:
            r0 = 67108864(0x4000000, float:1.5046328E-36)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0339:
            r0 = 4194304(0x400000, float:5.877472E-39)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x033f:
            r3.addFlags(r14)
            goto L_0x037b
        L_0x0343:
            r3.addFlags(r15)
            goto L_0x037b
        L_0x0347:
            r3.addFlags(r0)
            goto L_0x037b
        L_0x034b:
            r0 = 128(0x80, float:1.794E-43)
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0351:
            r0 = 64
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0357:
            r3.addFlags(r11)
            goto L_0x037b
        L_0x035b:
            r0 = 1
            r3.addFlags(r0)
            goto L_0x037b
        L_0x0360:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.Integer r5 = java.lang.Integer.decode(r0)
            int r5 = r5.intValue()
            r3.setFlags(r5)
            goto L_0x037b
        L_0x0370:
            java.lang.String r0 = r17.getNextArgRequired()
            r3.setPackage(r0)
            if (r3 != r2) goto L_0x037a
            r6 = 1
        L_0x037a:
        L_0x037b:
            r5 = r17
            goto L_0x0619
        L_0x037f:
            java.lang.String r0 = r17.getNextArgRequired()
            android.content.ComponentName r5 = android.content.ComponentName.unflattenFromString(r0)
            if (r5 == 0) goto L_0x0390
            r3.setComponent(r5)
            if (r3 != r2) goto L_0x038f
            r6 = 1
        L_0x038f:
            goto L_0x037b
        L_0x0390:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Bad component name: "
            r10.append(r11)
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        L_0x03a7:
            r0 = 1
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = r17.getNextArgRequired()
            java.lang.String r9 = r9.toLowerCase()
            java.lang.String r10 = "true"
            boolean r10 = r10.equals(r9)
            if (r10 != 0) goto L_0x03ff
            java.lang.String r10 = "t"
            boolean r10 = r10.equals(r9)
            if (r10 == 0) goto L_0x03c7
            goto L_0x03ff
        L_0x03c7:
            java.lang.String r10 = "false"
            boolean r10 = r10.equals(r9)
            if (r10 != 0) goto L_0x03fd
            java.lang.String r10 = "f"
            boolean r10 = r10.equals(r9)
            if (r10 == 0) goto L_0x03d8
            goto L_0x03fd
        L_0x03d8:
            java.lang.Integer r10 = java.lang.Integer.decode(r9)     // Catch:{ NumberFormatException -> 0x03e5 }
            int r10 = r10.intValue()     // Catch:{ NumberFormatException -> 0x03e5 }
            if (r10 == 0) goto L_0x03e3
            goto L_0x03e4
        L_0x03e3:
            r0 = 0
        L_0x03e4:
            goto L_0x0400
        L_0x03e5:
            r0 = move-exception
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Invalid boolean value: "
            r11.append(r12)
            r11.append(r9)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x03fd:
            r0 = 0
            goto L_0x0400
        L_0x03ff:
            r0 = 1
        L_0x0400:
            r3.putExtra((java.lang.String) r5, (boolean) r0)
            goto L_0x037b
        L_0x0406:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = "(?<!\\\\),"
            java.lang.String[] r9 = r5.split(r9)
            java.util.ArrayList r10 = new java.util.ArrayList
            int r11 = r9.length
            r10.<init>(r11)
            r16 = 0
        L_0x041c:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x0429
            r12 = r9[r11]
            r10.add(r12)
            int r16 = r11 + 1
            goto L_0x041c
        L_0x0429:
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r10)
            r0 = 1
            goto L_0x04ab
        L_0x042f:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = "(?<!\\\\),"
            java.lang.String[] r9 = r5.split(r9)
            r3.putExtra((java.lang.String) r0, (java.lang.String[]) r9)
            r0 = 1
            goto L_0x04ab
        L_0x0442:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = ","
            java.lang.String[] r9 = r5.split(r9)
            java.util.ArrayList r10 = new java.util.ArrayList
            int r11 = r9.length
            r10.<init>(r11)
            r16 = 0
        L_0x0458:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x0469
            r12 = r9[r11]
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            r10.add(r12)
            int r16 = r11 + 1
            goto L_0x0458
        L_0x0469:
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r10)
            r0 = 1
            goto L_0x04ab
        L_0x046e:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = ","
            java.lang.String[] r9 = r5.split(r9)
            int r10 = r9.length
            float[] r10 = new float[r10]
            r16 = 0
        L_0x0481:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x0495
            r12 = r9[r11]
            java.lang.Float r12 = java.lang.Float.valueOf(r12)
            float r12 = r12.floatValue()
            r10[r11] = r12
            int r16 = r11 + 1
            goto L_0x0481
        L_0x0495:
            r3.putExtra((java.lang.String) r0, (float[]) r10)
            r0 = 1
            goto L_0x04ab
        L_0x049a:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.Float r9 = java.lang.Float.valueOf(r5)
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r9)
            r0 = 1
        L_0x04ab:
            r5 = r17
            r6 = r0
            goto L_0x0619
        L_0x04b0:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = ","
            java.lang.String[] r9 = r5.split(r9)
            java.util.ArrayList r10 = new java.util.ArrayList
            int r11 = r9.length
            r10.<init>(r11)
            r16 = 0
        L_0x04c6:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x04d7
            r12 = r9[r11]
            java.lang.Long r12 = java.lang.Long.valueOf(r12)
            r10.add(r12)
            int r16 = r11 + 1
            goto L_0x04c6
        L_0x04d7:
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r10)
            r0 = 1
            goto L_0x04ab
        L_0x04dc:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = ","
            java.lang.String[] r9 = r5.split(r9)
            int r10 = r9.length
            long[] r10 = new long[r10]
            r16 = 0
        L_0x04ef:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x0503
            r12 = r9[r11]
            java.lang.Long r12 = java.lang.Long.valueOf(r12)
            long r12 = r12.longValue()
            r10[r11] = r12
            int r16 = r11 + 1
            goto L_0x04ef
        L_0x0503:
            r3.putExtra((java.lang.String) r0, (long[]) r10)
            r0 = 1
            goto L_0x04ab
        L_0x0508:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.Long r9 = java.lang.Long.valueOf(r5)
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r9)
            goto L_0x037b
        L_0x0519:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = ","
            java.lang.String[] r9 = r5.split(r9)
            java.util.ArrayList r10 = new java.util.ArrayList
            int r11 = r9.length
            r10.<init>(r11)
            r16 = 0
        L_0x052f:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x0540
            r12 = r9[r11]
            java.lang.Integer r12 = java.lang.Integer.decode(r12)
            r10.add(r12)
            int r16 = r11 + 1
            goto L_0x052f
        L_0x0540:
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r10)
            goto L_0x037b
        L_0x0545:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.String r9 = ","
            java.lang.String[] r9 = r5.split(r9)
            int r10 = r9.length
            int[] r10 = new int[r10]
            r16 = 0
        L_0x0558:
            r11 = r16
            int r12 = r9.length
            if (r11 >= r12) goto L_0x056c
            r12 = r9[r11]
            java.lang.Integer r12 = java.lang.Integer.decode(r12)
            int r12 = r12.intValue()
            r10[r11] = r12
            int r16 = r11 + 1
            goto L_0x0558
        L_0x056c:
            r3.putExtra((java.lang.String) r0, (int[]) r10)
            goto L_0x037b
        L_0x0571:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            android.content.ComponentName r9 = android.content.ComponentName.unflattenFromString(r5)
            if (r9 == 0) goto L_0x0584
            r3.putExtra((java.lang.String) r0, (android.os.Parcelable) r9)
            goto L_0x037b
        L_0x0584:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "Bad component name: "
            r11.append(r12)
            r11.append(r5)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x059b:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            android.net.Uri r9 = android.net.Uri.parse(r5)
            r3.putExtra((java.lang.String) r0, (android.os.Parcelable) r9)
            goto L_0x037b
        L_0x05ac:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            java.lang.Integer r9 = java.lang.Integer.decode(r5)
            r3.putExtra((java.lang.String) r0, (java.io.Serializable) r9)
            goto L_0x037b
        L_0x05bd:
            java.lang.String r0 = r17.getNextArgRequired()
            r5 = 0
            r9 = r5
            java.lang.String r9 = (java.lang.String) r9
            r3.putExtra((java.lang.String) r0, (java.lang.String) r9)
            goto L_0x037b
        L_0x05ca:
            java.lang.String r0 = r17.getNextArgRequired()
            java.lang.String r5 = r17.getNextArgRequired()
            r3.putExtra((java.lang.String) r0, (java.lang.String) r5)
            goto L_0x037b
        L_0x05d7:
            java.lang.String r0 = r17.getNextArgRequired()
            r3.addCategory(r0)
            if (r3 != r2) goto L_0x037b
            r0 = 1
            goto L_0x04ab
        L_0x05e3:
            java.lang.String r0 = r17.getNextArgRequired()
            r3.setIdentifier(r0)
            if (r3 != r2) goto L_0x037b
            r0 = 1
            goto L_0x04ab
        L_0x05ef:
            java.lang.String r0 = r17.getNextArgRequired()
            if (r3 != r2) goto L_0x05f7
            r5 = 1
            r6 = r5
        L_0x05f7:
            r5 = r17
            goto L_0x061a
        L_0x05fa:
            java.lang.String r0 = r17.getNextArgRequired()
            android.net.Uri r0 = android.net.Uri.parse(r0)
            if (r3 != r2) goto L_0x0609
            r4 = 1
            r5 = r17
            r6 = r4
            goto L_0x060b
        L_0x0609:
            r5 = r17
        L_0x060b:
            r4 = r0
            goto L_0x0619
        L_0x060d:
            java.lang.String r0 = r17.getNextArgRequired()
            r3.setAction(r0)
            if (r3 != r2) goto L_0x037b
            r0 = 1
            goto L_0x04ab
        L_0x0619:
            r0 = r7
        L_0x061a:
            goto L_0x000d
        L_0x061c:
            r5 = r17
        L_0x061e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Unknown option: "
            r9.append(r10)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L_0x0635:
            r0 = 1
            r5 = r17
            r3.setDataAndType(r4, r7)
            if (r3 == r2) goto L_0x063e
            goto L_0x063f
        L_0x063e:
            r0 = 0
        L_0x063f:
            if (r0 == 0) goto L_0x0645
            r2.setSelector(r3)
            r3 = r2
        L_0x0645:
            java.lang.String r11 = r17.getNextArg()
            r2 = 0
            if (r11 != 0) goto L_0x065c
            if (r0 == 0) goto L_0x0694
            android.content.Intent r9 = new android.content.Intent
            java.lang.String r10 = "android.intent.action.MAIN"
            r9.<init>((java.lang.String) r10)
            r2 = r9
            java.lang.String r9 = "android.intent.category.LAUNCHER"
            r2.addCategory(r9)
            goto L_0x0694
        L_0x065c:
            r12 = 58
            int r12 = r11.indexOf(r12)
            if (r12 < 0) goto L_0x0669
            android.content.Intent r2 = parseUri(r11, r10)
            goto L_0x0694
        L_0x0669:
            int r9 = r11.indexOf(r9)
            if (r9 < 0) goto L_0x0684
            android.content.Intent r9 = new android.content.Intent
            java.lang.String r10 = "android.intent.action.MAIN"
            r9.<init>((java.lang.String) r10)
            r2 = r9
            java.lang.String r9 = "android.intent.category.LAUNCHER"
            r2.addCategory(r9)
            android.content.ComponentName r9 = android.content.ComponentName.unflattenFromString(r11)
            r2.setComponent(r9)
            goto L_0x0694
        L_0x0684:
            android.content.Intent r9 = new android.content.Intent
            java.lang.String r10 = "android.intent.action.MAIN"
            r9.<init>((java.lang.String) r10)
            r2 = r9
            java.lang.String r9 = "android.intent.category.LAUNCHER"
            r2.addCategory(r9)
            r2.setPackage(r11)
        L_0x0694:
            if (r2 == 0) goto L_0x06e3
            android.os.Bundle r9 = r3.getExtras()
            r10 = 0
            android.os.Bundle r10 = (android.os.Bundle) r10
            r3.replaceExtras((android.os.Bundle) r10)
            android.os.Bundle r12 = r2.getExtras()
            r2.replaceExtras((android.os.Bundle) r10)
            java.lang.String r10 = r3.getAction()
            if (r10 == 0) goto L_0x06d0
            java.util.Set r10 = r2.getCategories()
            if (r10 == 0) goto L_0x06d0
            java.util.HashSet r10 = new java.util.HashSet
            java.util.Set r13 = r2.getCategories()
            r10.<init>(r13)
            java.util.Iterator r13 = r10.iterator()
        L_0x06c0:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x06d0
            java.lang.Object r14 = r13.next()
            java.lang.String r14 = (java.lang.String) r14
            r2.removeCategory(r14)
            goto L_0x06c0
        L_0x06d0:
            r10 = 72
            r3.fillIn(r2, r10)
            if (r9 != 0) goto L_0x06d9
            r9 = r12
            goto L_0x06df
        L_0x06d9:
            if (r12 == 0) goto L_0x06df
            r12.putAll(r9)
            r9 = r12
        L_0x06df:
            r3.replaceExtras((android.os.Bundle) r9)
            r6 = 1
        L_0x06e3:
            if (r6 == 0) goto L_0x06e6
            return r3
        L_0x06e6:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "No intent supplied"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.Intent.parseCommandArgs(android.os.ShellCommand, android.content.Intent$CommandOptionHandler):android.content.Intent");
    }

    @UnsupportedAppUsage
    public static void printIntentArgsHelp(PrintWriter pw, String prefix) {
        for (String line : new String[]{"<INTENT> specifications include these flags and arguments:", "    [-a <ACTION>] [-d <DATA_URI>] [-t <MIME_TYPE>] [-i <IDENTIFIER>]", "    [-c <CATEGORY> [-c <CATEGORY>] ...]", "    [-n <COMPONENT_NAME>]", "    [-e|--es <EXTRA_KEY> <EXTRA_STRING_VALUE> ...]", "    [--esn <EXTRA_KEY> ...]", "    [--ez <EXTRA_KEY> <EXTRA_BOOLEAN_VALUE> ...]", "    [--ei <EXTRA_KEY> <EXTRA_INT_VALUE> ...]", "    [--el <EXTRA_KEY> <EXTRA_LONG_VALUE> ...]", "    [--ef <EXTRA_KEY> <EXTRA_FLOAT_VALUE> ...]", "    [--eu <EXTRA_KEY> <EXTRA_URI_VALUE> ...]", "    [--ecn <EXTRA_KEY> <EXTRA_COMPONENT_NAME_VALUE>]", "    [--eia <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (mutiple extras passed as Integer[])", "    [--eial <EXTRA_KEY> <EXTRA_INT_VALUE>[,<EXTRA_INT_VALUE...]]", "        (mutiple extras passed as List<Integer>)", "    [--ela <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (mutiple extras passed as Long[])", "    [--elal <EXTRA_KEY> <EXTRA_LONG_VALUE>[,<EXTRA_LONG_VALUE...]]", "        (mutiple extras passed as List<Long>)", "    [--efa <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (mutiple extras passed as Float[])", "    [--efal <EXTRA_KEY> <EXTRA_FLOAT_VALUE>[,<EXTRA_FLOAT_VALUE...]]", "        (mutiple extras passed as List<Float>)", "    [--esa <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (mutiple extras passed as String[]; to embed a comma into a string,", "         escape it using \"\\,\")", "    [--esal <EXTRA_KEY> <EXTRA_STRING_VALUE>[,<EXTRA_STRING_VALUE...]]", "        (mutiple extras passed as List<String>; to embed a comma into a string,", "         escape it using \"\\,\")", "    [-f <FLAG>]", "    [--grant-read-uri-permission] [--grant-write-uri-permission]", "    [--grant-persistable-uri-permission] [--grant-prefix-uri-permission]", "    [--debug-log-resolution] [--exclude-stopped-packages]", "    [--include-stopped-packages]", "    [--activity-brought-to-front] [--activity-clear-top]", "    [--activity-clear-when-task-reset] [--activity-exclude-from-recents]", "    [--activity-launched-from-history] [--activity-multiple-task]", "    [--activity-no-animation] [--activity-no-history]", "    [--activity-no-user-action] [--activity-previous-is-top]", "    [--activity-reorder-to-front] [--activity-reset-task-if-needed]", "    [--activity-single-top] [--activity-clear-task]", "    [--activity-task-on-home] [--activity-match-external]", "    [--receiver-registered-only] [--receiver-replace-pending]", "    [--receiver-foreground] [--receiver-no-abort]", "    [--receiver-include-background]", "    [--selector]", "    [<URI> | <PACKAGE> | <COMPONENT>]"}) {
            pw.print(prefix);
            pw.println(line);
        }
        PrintWriter printWriter = pw;
    }

    public String getAction() {
        return this.mAction;
    }

    public Uri getData() {
        return this.mData;
    }

    public String getDataString() {
        if (this.mData != null) {
            return this.mData.toString();
        }
        return null;
    }

    public String getScheme() {
        if (this.mData != null) {
            return this.mData.getScheme();
        }
        return null;
    }

    public String getType() {
        return this.mType;
    }

    public String resolveType(Context context) {
        return resolveType(context.getContentResolver());
    }

    public String resolveType(ContentResolver resolver) {
        if (this.mType != null) {
            return this.mType;
        }
        if (this.mData == null || !"content".equals(this.mData.getScheme())) {
            return null;
        }
        return resolver.getType(this.mData);
    }

    public String resolveTypeIfNeeded(ContentResolver resolver) {
        if (this.mComponent != null) {
            return this.mType;
        }
        return resolveType(resolver);
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public boolean hasCategory(String category) {
        return this.mCategories != null && this.mCategories.contains(category);
    }

    public Set<String> getCategories() {
        return this.mCategories;
    }

    public Intent getSelector() {
        return this.mSelector;
    }

    public ClipData getClipData() {
        return this.mClipData;
    }

    public int getContentUserHint() {
        return this.mContentUserHint;
    }

    public String getLaunchToken() {
        return this.mLaunchToken;
    }

    public void setLaunchToken(String launchToken) {
        this.mLaunchToken = launchToken;
    }

    public void setExtrasClassLoader(ClassLoader loader) {
        if (this.mExtras != null) {
            this.mExtras.setClassLoader(loader);
        }
    }

    public boolean hasExtra(String name) {
        return this.mExtras != null && this.mExtras.containsKey(name);
    }

    public boolean hasFileDescriptors() {
        return this.mExtras != null && this.mExtras.hasFileDescriptors();
    }

    @UnsupportedAppUsage
    public void setAllowFds(boolean allowFds) {
        if (this.mExtras != null) {
            this.mExtras.setAllowFds(allowFds);
        }
    }

    public void setDefusable(boolean defusable) {
        if (this.mExtras != null) {
            this.mExtras.setDefusable(defusable);
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public Object getExtra(String name) {
        return getExtra(name, (Object) null);
    }

    public boolean getBooleanExtra(String name, boolean defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getBoolean(name, defaultValue);
    }

    public byte getByteExtra(String name, byte defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getByte(name, defaultValue).byteValue();
    }

    public short getShortExtra(String name, short defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getShort(name, defaultValue);
    }

    public char getCharExtra(String name, char defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getChar(name, defaultValue);
    }

    public int getIntExtra(String name, int defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getInt(name, defaultValue);
    }

    public long getLongExtra(String name, long defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getLong(name, defaultValue);
    }

    public float getFloatExtra(String name, float defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getFloat(name, defaultValue);
    }

    public double getDoubleExtra(String name, double defaultValue) {
        if (this.mExtras == null) {
            return defaultValue;
        }
        return this.mExtras.getDouble(name, defaultValue);
    }

    public String getStringExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getString(name);
    }

    public CharSequence getCharSequenceExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharSequence(name);
    }

    public <T extends Parcelable> T getParcelableExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getParcelable(name);
    }

    public Parcelable[] getParcelableArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getParcelableArray(name);
    }

    public <T extends Parcelable> ArrayList<T> getParcelableArrayListExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getParcelableArrayList(name);
    }

    public Serializable getSerializableExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getSerializable(name);
    }

    public ArrayList<Integer> getIntegerArrayListExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getIntegerArrayList(name);
    }

    public ArrayList<String> getStringArrayListExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getStringArrayList(name);
    }

    public ArrayList<CharSequence> getCharSequenceArrayListExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharSequenceArrayList(name);
    }

    public boolean[] getBooleanArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getBooleanArray(name);
    }

    public byte[] getByteArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getByteArray(name);
    }

    public short[] getShortArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getShortArray(name);
    }

    public char[] getCharArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharArray(name);
    }

    public int[] getIntArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getIntArray(name);
    }

    public long[] getLongArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getLongArray(name);
    }

    public float[] getFloatArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getFloatArray(name);
    }

    public double[] getDoubleArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getDoubleArray(name);
    }

    public String[] getStringArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getStringArray(name);
    }

    public CharSequence[] getCharSequenceArrayExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getCharSequenceArray(name);
    }

    public Bundle getBundleExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getBundle(name);
    }

    @Deprecated
    @UnsupportedAppUsage
    public IBinder getIBinderExtra(String name) {
        if (this.mExtras == null) {
            return null;
        }
        return this.mExtras.getIBinder(name);
    }

    @Deprecated
    @UnsupportedAppUsage
    public Object getExtra(String name, Object defaultValue) {
        Object result2;
        Object result = defaultValue;
        if (this.mExtras == null || (result2 = this.mExtras.get(name)) == null) {
            return result;
        }
        return result2;
    }

    public Bundle getExtras() {
        if (this.mExtras != null) {
            return new Bundle(this.mExtras);
        }
        return null;
    }

    public void removeUnsafeExtras() {
        if (this.mExtras != null) {
            this.mExtras = this.mExtras.filterValues();
        }
    }

    public boolean canStripForHistory() {
        return (this.mExtras != null && this.mExtras.isParcelled()) || this.mClipData != null;
    }

    public Intent maybeStripForHistory() {
        if (!canStripForHistory()) {
            return this;
        }
        return new Intent(this, 2);
    }

    public int getFlags() {
        return this.mFlags;
    }

    @UnsupportedAppUsage
    public boolean isExcludingStopped() {
        return (this.mFlags & 48) == 16;
    }

    public String getPackage() {
        return this.mPackage;
    }

    public ComponentName getComponent() {
        return this.mComponent;
    }

    public Rect getSourceBounds() {
        return this.mSourceBounds;
    }

    public ComponentName resolveActivity(PackageManager pm) {
        if (this.mComponent != null) {
            return this.mComponent;
        }
        ResolveInfo info = pm.resolveActivity(this, 65536);
        if (info != null) {
            return new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
        }
        return null;
    }

    public ActivityInfo resolveActivityInfo(PackageManager pm, int flags) {
        if (this.mComponent != null) {
            try {
                return pm.getActivityInfo(this.mComponent, flags);
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        } else {
            ResolveInfo info = pm.resolveActivity(this, 65536 | flags);
            if (info != null) {
                return info.activityInfo;
            }
            return null;
        }
    }

    @UnsupportedAppUsage
    public ComponentName resolveSystemService(PackageManager pm, int flags) {
        if (this.mComponent != null) {
            return this.mComponent;
        }
        List<ResolveInfo> results = pm.queryIntentServices(this, flags);
        if (results == null) {
            return null;
        }
        ComponentName comp = null;
        for (int i = 0; i < results.size(); i++) {
            ResolveInfo ri = results.get(i);
            if ((ri.serviceInfo.applicationInfo.flags & 1) != 0) {
                ComponentName foundComp = new ComponentName(ri.serviceInfo.applicationInfo.packageName, ri.serviceInfo.name);
                if (comp == null) {
                    comp = foundComp;
                } else {
                    throw new IllegalStateException("Multiple system services handle " + this + PluralRules.KEYWORD_RULE_SEPARATOR + comp + ", " + foundComp);
                }
            }
        }
        return comp;
    }

    public Intent setAction(String action) {
        this.mAction = action != null ? action.intern() : null;
        return this;
    }

    public Intent setData(Uri data) {
        this.mData = data;
        this.mType = null;
        return this;
    }

    public Intent setDataAndNormalize(Uri data) {
        return setData(data.normalizeScheme());
    }

    public Intent setType(String type) {
        this.mData = null;
        this.mType = type;
        return this;
    }

    public Intent setTypeAndNormalize(String type) {
        return setType(normalizeMimeType(type));
    }

    public Intent setDataAndType(Uri data, String type) {
        this.mData = data;
        this.mType = type;
        return this;
    }

    public Intent setDataAndTypeAndNormalize(Uri data, String type) {
        return setDataAndType(data.normalizeScheme(), normalizeMimeType(type));
    }

    public Intent setIdentifier(String identifier) {
        this.mIdentifier = identifier;
        return this;
    }

    public Intent addCategory(String category) {
        if (this.mCategories == null) {
            this.mCategories = new ArraySet<>();
        }
        this.mCategories.add(category.intern());
        return this;
    }

    public void removeCategory(String category) {
        if (this.mCategories != null) {
            this.mCategories.remove(category);
            if (this.mCategories.size() == 0) {
                this.mCategories = null;
            }
        }
    }

    public void setSelector(Intent selector) {
        if (selector == this) {
            throw new IllegalArgumentException("Intent being set as a selector of itself");
        } else if (selector == null || this.mPackage == null) {
            this.mSelector = selector;
        } else {
            throw new IllegalArgumentException("Can't set selector when package name is already set");
        }
    }

    public void setClipData(ClipData clip) {
        this.mClipData = clip;
    }

    public void prepareToLeaveUser(int userId) {
        if (this.mContentUserHint == -2) {
            this.mContentUserHint = userId;
        }
    }

    public Intent putExtra(String name, boolean value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBoolean(name, value);
        return this;
    }

    public Intent putExtra(String name, byte value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putByte(name, value);
        return this;
    }

    public Intent putExtra(String name, char value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putChar(name, value);
        return this;
    }

    public Intent putExtra(String name, short value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putShort(name, value);
        return this;
    }

    public Intent putExtra(String name, int value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putInt(name, value);
        return this;
    }

    public Intent putExtra(String name, long value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putLong(name, value);
        return this;
    }

    public Intent putExtra(String name, float value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putFloat(name, value);
        return this;
    }

    public Intent putExtra(String name, double value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putDouble(name, value);
        return this;
    }

    public Intent putExtra(String name, String value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putString(name, value);
        return this;
    }

    public Intent putExtra(String name, CharSequence value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequence(name, value);
        return this;
    }

    public Intent putExtra(String name, Parcelable value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelable(name, value);
        return this;
    }

    public Intent putExtra(String name, Parcelable[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelableArray(name, value);
        return this;
    }

    public Intent putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putParcelableArrayList(name, value);
        return this;
    }

    public Intent putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIntegerArrayList(name, value);
        return this;
    }

    public Intent putStringArrayListExtra(String name, ArrayList<String> value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putStringArrayList(name, value);
        return this;
    }

    public Intent putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequenceArrayList(name, value);
        return this;
    }

    public Intent putExtra(String name, Serializable value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putSerializable(name, value);
        return this;
    }

    public Intent putExtra(String name, boolean[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBooleanArray(name, value);
        return this;
    }

    public Intent putExtra(String name, byte[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putByteArray(name, value);
        return this;
    }

    public Intent putExtra(String name, short[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putShortArray(name, value);
        return this;
    }

    public Intent putExtra(String name, char[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharArray(name, value);
        return this;
    }

    public Intent putExtra(String name, int[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIntArray(name, value);
        return this;
    }

    public Intent putExtra(String name, long[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putLongArray(name, value);
        return this;
    }

    public Intent putExtra(String name, float[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putFloatArray(name, value);
        return this;
    }

    public Intent putExtra(String name, double[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putDoubleArray(name, value);
        return this;
    }

    public Intent putExtra(String name, String[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putStringArray(name, value);
        return this;
    }

    public Intent putExtra(String name, CharSequence[] value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putCharSequenceArray(name, value);
        return this;
    }

    public Intent putExtra(String name, Bundle value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putBundle(name, value);
        return this;
    }

    @Deprecated
    @UnsupportedAppUsage
    public Intent putExtra(String name, IBinder value) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putIBinder(name, value);
        return this;
    }

    public Intent putExtras(Intent src) {
        if (src.mExtras != null) {
            if (this.mExtras == null) {
                this.mExtras = new Bundle(src.mExtras);
            } else {
                this.mExtras.putAll(src.mExtras);
            }
        }
        return this;
    }

    public Intent putExtras(Bundle extras) {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putAll(extras);
        return this;
    }

    public Intent replaceExtras(Intent src) {
        this.mExtras = src.mExtras != null ? new Bundle(src.mExtras) : null;
        return this;
    }

    public Intent replaceExtras(Bundle extras) {
        this.mExtras = extras != null ? new Bundle(extras) : null;
        return this;
    }

    public void removeExtra(String name) {
        if (this.mExtras != null) {
            this.mExtras.remove(name);
            if (this.mExtras.size() == 0) {
                this.mExtras = null;
            }
        }
    }

    public Intent setFlags(int flags) {
        this.mFlags = flags;
        return this;
    }

    public Intent addFlags(int flags) {
        this.mFlags |= flags;
        return this;
    }

    public void removeFlags(int flags) {
        this.mFlags &= ~flags;
    }

    public Intent setPackage(String packageName) {
        if (packageName == null || this.mSelector == null) {
            this.mPackage = packageName;
            return this;
        }
        throw new IllegalArgumentException("Can't set package name when selector is already set");
    }

    public Intent setComponent(ComponentName component) {
        this.mComponent = component;
        return this;
    }

    public Intent setClassName(Context packageContext, String className) {
        this.mComponent = new ComponentName(packageContext, className);
        return this;
    }

    public Intent setClassName(String packageName, String className) {
        this.mComponent = new ComponentName(packageName, className);
        return this;
    }

    public Intent setClass(Context packageContext, Class<?> cls) {
        this.mComponent = new ComponentName(packageContext, cls);
        return this;
    }

    public void setSourceBounds(Rect r) {
        if (r != null) {
            this.mSourceBounds = new Rect(r);
        } else {
            this.mSourceBounds = null;
        }
    }

    public int fillIn(Intent other, int flags) {
        int changes = 0;
        boolean mayHaveCopiedUris = false;
        if (other.mAction != null && (this.mAction == null || (flags & 1) != 0)) {
            this.mAction = other.mAction;
            changes = 0 | 1;
        }
        if (!(other.mData == null && other.mType == null) && ((this.mData == null && this.mType == null) || (flags & 2) != 0)) {
            this.mData = other.mData;
            this.mType = other.mType;
            changes |= 2;
            mayHaveCopiedUris = true;
        }
        if (other.mIdentifier != null && (this.mIdentifier == null || (flags & 256) != 0)) {
            this.mIdentifier = other.mIdentifier;
            changes |= 256;
        }
        if (other.mCategories != null && (this.mCategories == null || (flags & 4) != 0)) {
            if (other.mCategories != null) {
                this.mCategories = new ArraySet<>(other.mCategories);
            }
            changes |= 4;
        }
        if (other.mPackage != null && ((this.mPackage == null || (flags & 16) != 0) && this.mSelector == null)) {
            this.mPackage = other.mPackage;
            changes |= 16;
        }
        if (!(other.mSelector == null || (flags & 64) == 0 || this.mPackage != null)) {
            this.mSelector = new Intent(other.mSelector);
            this.mPackage = null;
            changes |= 64;
        }
        if (other.mClipData != null && (this.mClipData == null || (flags & 128) != 0)) {
            this.mClipData = other.mClipData;
            changes |= 128;
            mayHaveCopiedUris = true;
        }
        if (!(other.mComponent == null || (flags & 8) == 0)) {
            this.mComponent = other.mComponent;
            changes |= 8;
        }
        this.mFlags |= other.mFlags;
        if (other.mSourceBounds != null && (this.mSourceBounds == null || (flags & 32) != 0)) {
            this.mSourceBounds = new Rect(other.mSourceBounds);
            changes |= 32;
        }
        if (this.mExtras == null) {
            if (other.mExtras != null) {
                this.mExtras = new Bundle(other.mExtras);
                mayHaveCopiedUris = true;
            }
        } else if (other.mExtras != null) {
            try {
                Bundle newb = new Bundle(other.mExtras);
                newb.putAll(this.mExtras);
                this.mExtras = newb;
                mayHaveCopiedUris = true;
            } catch (RuntimeException e) {
                Log.w(TAG, "Failure filling in extras", e);
            }
        }
        if (mayHaveCopiedUris && this.mContentUserHint == -2 && other.mContentUserHint != -2) {
            this.mContentUserHint = other.mContentUserHint;
        }
        return changes;
    }

    public static final class FilterComparison {
        private final int mHashCode;
        private final Intent mIntent;

        public FilterComparison(Intent intent) {
            this.mIntent = intent;
            this.mHashCode = intent.filterHashCode();
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof FilterComparison)) {
                return false;
            }
            return this.mIntent.filterEquals(((FilterComparison) obj).mIntent);
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    public boolean filterEquals(Intent other) {
        if (other != null && Objects.equals(this.mAction, other.mAction) && Objects.equals(this.mData, other.mData) && Objects.equals(this.mType, other.mType) && Objects.equals(this.mIdentifier, other.mIdentifier) && Objects.equals(this.mPackage, other.mPackage) && Objects.equals(this.mComponent, other.mComponent) && Objects.equals(this.mCategories, other.mCategories)) {
            return true;
        }
        return false;
    }

    public int filterHashCode() {
        int code = 0;
        if (this.mAction != null) {
            code = 0 + this.mAction.hashCode();
        }
        if (this.mData != null) {
            code += this.mData.hashCode();
        }
        if (this.mType != null) {
            code += this.mType.hashCode();
        }
        if (this.mIdentifier != null) {
            code += this.mIdentifier.hashCode();
        }
        if (this.mPackage != null) {
            code += this.mPackage.hashCode();
        }
        if (this.mComponent != null) {
            code += this.mComponent.hashCode();
        }
        if (this.mCategories != null) {
            return code + this.mCategories.hashCode();
        }
        return code;
    }

    public String toString() {
        StringBuilder b = new StringBuilder(128);
        b.append("Intent { ");
        toShortString(b, true, true, true, false);
        b.append(" }");
        return b.toString();
    }

    @UnsupportedAppUsage
    public String toInsecureString() {
        StringBuilder b = new StringBuilder(128);
        b.append("Intent { ");
        toShortString(b, false, true, true, false);
        b.append(" }");
        return b.toString();
    }

    public String toInsecureStringWithClip() {
        StringBuilder b = new StringBuilder(128);
        b.append("Intent { ");
        toShortString(b, false, true, true, true);
        b.append(" }");
        return b.toString();
    }

    public String toShortString(boolean secure, boolean comp, boolean extras, boolean clip) {
        StringBuilder b = new StringBuilder(128);
        toShortString(b, secure, comp, extras, clip);
        return b.toString();
    }

    public void toShortString(StringBuilder b, boolean secure, boolean comp, boolean extras, boolean clip) {
        boolean first;
        boolean first2 = true;
        if (this.mAction != null) {
            b.append("act=");
            b.append(this.mAction);
            first2 = false;
        }
        if (this.mCategories != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("cat=[");
            for (int i = 0; i < this.mCategories.size(); i++) {
                if (i > 0) {
                    b.append(',');
                }
                b.append(this.mCategories.valueAt(i));
            }
            b.append("]");
        }
        if (this.mData != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("dat=");
            if (secure) {
                b.append(this.mData.toSafeString());
            } else {
                b.append(this.mData);
            }
        }
        if (this.mType != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("typ=");
            b.append(this.mType);
        }
        if (this.mIdentifier != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("id=");
            b.append(this.mIdentifier);
        }
        if (this.mFlags != 0) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("flg=0x");
            b.append(Integer.toHexString(this.mFlags));
        }
        if (this.mPackage != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("pkg=");
            b.append(this.mPackage);
        }
        if (comp && this.mComponent != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("cmp=");
            b.append(this.mComponent.flattenToShortString());
        }
        if (this.mSourceBounds != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("bnds=");
            b.append(this.mSourceBounds.toShortString());
        }
        if (this.mClipData != null) {
            if (!first2) {
                b.append(' ');
            }
            b.append("clip={");
            if (clip) {
                this.mClipData.toShortString(b);
            } else {
                if (this.mClipData.getDescription() != null) {
                    first = !this.mClipData.getDescription().toShortStringTypesOnly(b);
                } else {
                    first = true;
                }
                this.mClipData.toShortStringShortItems(b, first);
            }
            first2 = false;
            b.append('}');
        }
        if (extras && this.mExtras != null) {
            if (!first2) {
                b.append(' ');
            }
            first2 = false;
            b.append("(has extras)");
        }
        if (this.mContentUserHint != -2) {
            if (!first2) {
                b.append(' ');
            }
            b.append("u=");
            b.append(this.mContentUserHint);
        }
        if (this.mSelector != null) {
            b.append(" sel=");
            this.mSelector.toShortString(b, secure, comp, extras, clip);
            b.append("}");
        }
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId) {
        writeToProto(proto, fieldId, true, true, true, false);
    }

    public void writeToProto(ProtoOutputStream proto) {
        writeToProtoWithoutFieldId(proto, true, true, true, false);
    }

    public void writeToProto(ProtoOutputStream proto, long fieldId, boolean secure, boolean comp, boolean extras, boolean clip) {
        long token = proto.start(fieldId);
        writeToProtoWithoutFieldId(proto, secure, comp, extras, clip);
        proto.end(token);
    }

    private void writeToProtoWithoutFieldId(ProtoOutputStream proto, boolean secure, boolean comp, boolean extras, boolean clip) {
        if (this.mAction != null) {
            proto.write(1138166333441L, this.mAction);
        }
        if (this.mCategories != null) {
            Iterator<String> it = this.mCategories.iterator();
            while (it.hasNext()) {
                proto.write(2237677961218L, it.next());
            }
        }
        if (this.mData != null) {
            proto.write(1138166333443L, secure ? this.mData.toSafeString() : this.mData.toString());
        }
        if (this.mType != null) {
            proto.write(1138166333444L, this.mType);
        }
        if (this.mIdentifier != null) {
            proto.write(1138166333453L, this.mIdentifier);
        }
        if (this.mFlags != 0) {
            proto.write(1138166333445L, "0x" + Integer.toHexString(this.mFlags));
        }
        if (this.mPackage != null) {
            proto.write(1138166333446L, this.mPackage);
        }
        if (comp && this.mComponent != null) {
            this.mComponent.writeToProto(proto, 1146756268039L);
        }
        if (this.mSourceBounds != null) {
            proto.write(1138166333448L, this.mSourceBounds.toShortString());
        }
        if (this.mClipData != null) {
            StringBuilder b = new StringBuilder();
            if (clip) {
                this.mClipData.toShortString(b);
            } else {
                this.mClipData.toShortStringShortItems(b, false);
            }
            proto.write(1138166333449L, b.toString());
        }
        if (extras && this.mExtras != null) {
            proto.write(1138166333450L, this.mExtras.toShortString());
        }
        if (this.mContentUserHint != 0) {
            proto.write(1120986464267L, this.mContentUserHint);
        }
        if (this.mSelector != null) {
            proto.write(1138166333452L, this.mSelector.toShortString(secure, comp, extras, clip));
        }
    }

    @Deprecated
    public String toURI() {
        return toUri(0);
    }

    public String toUri(int flags) {
        StringBuilder uri = new StringBuilder(128);
        if ((flags & 2) == 0) {
            String scheme = null;
            if (this.mData != null) {
                String data = this.mData.toString();
                if ((flags & 1) != 0) {
                    int N = data.length();
                    int i = 0;
                    while (true) {
                        if (i >= N) {
                            break;
                        }
                        char c = data.charAt(i);
                        if ((c >= 'a' && c <= 'z') || ((c >= 'A' && c <= 'Z') || ((c >= '0' && c <= '9') || c == '.' || c == '-' || c == '+'))) {
                            i++;
                        } else if (c == ':' && i > 0) {
                            scheme = data.substring(0, i);
                            uri.append("intent:");
                            data = data.substring(i + 1);
                        }
                    }
                }
                uri.append(data);
            } else if ((flags & 1) != 0) {
                uri.append("intent:");
            }
            toUriFragment(uri, scheme, "android.intent.action.VIEW", (String) null, flags);
            return uri.toString();
        } else if (this.mPackage != null) {
            uri.append("android-app://");
            uri.append(this.mPackage);
            String scheme2 = null;
            if (!(this.mData == null || (scheme2 = this.mData.getScheme()) == null)) {
                uri.append('/');
                uri.append(scheme2);
                String authority = this.mData.getEncodedAuthority();
                if (authority != null) {
                    uri.append('/');
                    uri.append(authority);
                    String path = this.mData.getEncodedPath();
                    if (path != null) {
                        uri.append(path);
                    }
                    String queryParams = this.mData.getEncodedQuery();
                    if (queryParams != null) {
                        uri.append('?');
                        uri.append(queryParams);
                    }
                    String fragment = this.mData.getEncodedFragment();
                    if (fragment != null) {
                        uri.append('#');
                        uri.append(fragment);
                    }
                }
            }
            toUriFragment(uri, (String) null, scheme2 == null ? ACTION_MAIN : "android.intent.action.VIEW", this.mPackage, flags);
            return uri.toString();
        } else {
            throw new IllegalArgumentException("Intent must include an explicit package name to build an android-app: " + this);
        }
    }

    private void toUriFragment(StringBuilder uri, String scheme, String defAction, String defPackage, int flags) {
        StringBuilder frag = new StringBuilder(128);
        toUriInner(frag, scheme, defAction, defPackage, flags);
        if (this.mSelector != null) {
            frag.append("SEL;");
            this.mSelector.toUriInner(frag, this.mSelector.mData != null ? this.mSelector.mData.getScheme() : null, (String) null, (String) null, flags);
        }
        if (frag.length() > 0) {
            uri.append("#Intent;");
            uri.append(frag);
            uri.append("end");
        }
    }

    private void toUriInner(StringBuilder uri, String scheme, String defAction, String defPackage, int flags) {
        char entryType;
        if (scheme != null) {
            uri.append("scheme=");
            uri.append(scheme);
            uri.append(';');
        }
        if (this.mAction != null && !this.mAction.equals(defAction)) {
            uri.append("action=");
            uri.append(Uri.encode(this.mAction));
            uri.append(';');
        }
        if (this.mCategories != null) {
            for (int i = 0; i < this.mCategories.size(); i++) {
                uri.append("category=");
                uri.append(Uri.encode(this.mCategories.valueAt(i)));
                uri.append(';');
            }
        }
        if (this.mType != null) {
            uri.append("type=");
            uri.append(Uri.encode(this.mType, "/"));
            uri.append(';');
        }
        if (this.mIdentifier != null) {
            uri.append("identifier=");
            uri.append(Uri.encode(this.mIdentifier, "/"));
            uri.append(';');
        }
        if (this.mFlags != 0) {
            uri.append("launchFlags=0x");
            uri.append(Integer.toHexString(this.mFlags));
            uri.append(';');
        }
        if (this.mPackage != null && !this.mPackage.equals(defPackage)) {
            uri.append("package=");
            uri.append(Uri.encode(this.mPackage));
            uri.append(';');
        }
        if (this.mComponent != null) {
            uri.append("component=");
            uri.append(Uri.encode(this.mComponent.flattenToShortString(), "/"));
            uri.append(';');
        }
        if (this.mSourceBounds != null) {
            uri.append("sourceBounds=");
            uri.append(Uri.encode(this.mSourceBounds.flattenToString()));
            uri.append(';');
        }
        if (this.mExtras != null) {
            for (String key : this.mExtras.keySet()) {
                Object value = this.mExtras.get(key);
                if (value instanceof String) {
                    entryType = 'S';
                } else if (value instanceof Boolean) {
                    entryType = 'B';
                } else if (value instanceof Byte) {
                    entryType = 'b';
                } else if (value instanceof Character) {
                    entryType = 'c';
                } else if (value instanceof Double) {
                    entryType = DateFormat.DATE;
                } else if (value instanceof Float) {
                    entryType = 'f';
                } else if (value instanceof Integer) {
                    entryType = 'i';
                } else if (value instanceof Long) {
                    entryType = 'l';
                } else if (value instanceof Short) {
                    entryType = 's';
                } else {
                    entryType = 0;
                }
                if (entryType != 0) {
                    uri.append(entryType);
                    uri.append('.');
                    uri.append(Uri.encode(key));
                    uri.append('=');
                    uri.append(Uri.encode(value.toString()));
                    uri.append(';');
                }
            }
        }
    }

    public int describeContents() {
        if (this.mExtras != null) {
            return this.mExtras.describeContents();
        }
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mAction);
        Uri.writeToParcel(out, this.mData);
        out.writeString(this.mType);
        out.writeString(this.mIdentifier);
        out.writeInt(this.mFlags);
        out.writeString(this.mPackage);
        ComponentName.writeToParcel(this.mComponent, out);
        if (this.mSourceBounds != null) {
            out.writeInt(1);
            this.mSourceBounds.writeToParcel(out, flags);
        } else {
            out.writeInt(0);
        }
        if (this.mCategories != null) {
            int N = this.mCategories.size();
            out.writeInt(N);
            for (int i = 0; i < N; i++) {
                out.writeString(this.mCategories.valueAt(i));
            }
        } else {
            out.writeInt(0);
        }
        if (this.mSelector != null) {
            out.writeInt(1);
            this.mSelector.writeToParcel(out, flags);
        } else {
            out.writeInt(0);
        }
        if (this.mClipData != null) {
            out.writeInt(1);
            this.mClipData.writeToParcel(out, flags);
        } else {
            out.writeInt(0);
        }
        out.writeInt(this.mContentUserHint);
        out.writeBundle(this.mExtras);
    }

    protected Intent(Parcel in) {
        this.mContentUserHint = -2;
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        setAction(in.readString());
        this.mData = Uri.CREATOR.createFromParcel(in);
        this.mType = in.readString();
        this.mIdentifier = in.readString();
        this.mFlags = in.readInt();
        this.mPackage = in.readString();
        this.mComponent = ComponentName.readFromParcel(in);
        if (in.readInt() != 0) {
            this.mSourceBounds = Rect.CREATOR.createFromParcel(in);
        }
        int N = in.readInt();
        if (N > 0) {
            this.mCategories = new ArraySet<>();
            for (int i = 0; i < N; i++) {
                this.mCategories.add(in.readString().intern());
            }
        } else {
            this.mCategories = null;
        }
        if (in.readInt() != 0) {
            this.mSelector = new Intent(in);
        }
        if (in.readInt() != 0) {
            this.mClipData = new ClipData(in);
        }
        this.mContentUserHint = in.readInt();
        this.mExtras = in.readBundle();
    }

    public static Intent parseIntent(Resources resources, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        Resources resources2 = resources;
        AttributeSet attributeSet = attrs;
        Intent intent = new Intent();
        TypedArray sa = resources2.obtainAttributes(attributeSet, R.styleable.Intent);
        intent.setAction(sa.getString(2));
        int i = 3;
        String data = sa.getString(3);
        intent.setDataAndType(data != null ? Uri.parse(data) : null, sa.getString(1));
        intent.setIdentifier(sa.getString(5));
        String packageName = sa.getString(0);
        String className = sa.getString(4);
        if (!(packageName == null || className == null)) {
            intent.setComponent(new ComponentName(packageName, className));
        }
        sa.recycle();
        int outerDepth = parser.getDepth();
        while (true) {
            int next = parser.next();
            int type = next;
            if (next == 1 || (type == i && parser.getDepth() <= outerDepth)) {
                return intent;
            }
            if (!(type == i || type == 4)) {
                String nodeName = parser.getName();
                if (nodeName.equals(TAG_CATEGORIES)) {
                    TypedArray sa2 = resources2.obtainAttributes(attributeSet, R.styleable.IntentCategory);
                    String cat = sa2.getString(0);
                    sa2.recycle();
                    if (cat != null) {
                        intent.addCategory(cat);
                    }
                    XmlUtils.skipCurrentTag(parser);
                } else if (nodeName.equals(TAG_EXTRA)) {
                    if (intent.mExtras == null) {
                        intent.mExtras = new Bundle();
                    }
                    resources2.parseBundleExtra(TAG_EXTRA, attributeSet, intent.mExtras);
                    XmlUtils.skipCurrentTag(parser);
                } else {
                    XmlUtils.skipCurrentTag(parser);
                }
            }
            i = 3;
        }
        return intent;
    }

    public void saveToXml(XmlSerializer out) throws IOException {
        if (this.mAction != null) {
            out.attribute((String) null, "action", this.mAction);
        }
        if (this.mData != null) {
            out.attribute((String) null, "data", this.mData.toString());
        }
        if (this.mType != null) {
            out.attribute((String) null, "type", this.mType);
        }
        if (this.mIdentifier != null) {
            out.attribute((String) null, ATTR_IDENTIFIER, this.mIdentifier);
        }
        if (this.mComponent != null) {
            out.attribute((String) null, "component", this.mComponent.flattenToShortString());
        }
        out.attribute((String) null, "flags", Integer.toHexString(getFlags()));
        if (this.mCategories != null) {
            out.startTag((String) null, TAG_CATEGORIES);
            for (int categoryNdx = this.mCategories.size() - 1; categoryNdx >= 0; categoryNdx--) {
                out.attribute((String) null, "category", this.mCategories.valueAt(categoryNdx));
            }
            out.endTag((String) null, TAG_CATEGORIES);
        }
    }

    public static Intent restoreFromXml(XmlPullParser in) throws IOException, XmlPullParserException {
        Intent intent = new Intent();
        int outerDepth = in.getDepth();
        for (int attrNdx = in.getAttributeCount() - 1; attrNdx >= 0; attrNdx--) {
            String attrName = in.getAttributeName(attrNdx);
            String attrValue = in.getAttributeValue(attrNdx);
            if ("action".equals(attrName)) {
                intent.setAction(attrValue);
            } else if ("data".equals(attrName)) {
                intent.setData(Uri.parse(attrValue));
            } else if ("type".equals(attrName)) {
                intent.setType(attrValue);
            } else if (ATTR_IDENTIFIER.equals(attrName)) {
                intent.setIdentifier(attrValue);
            } else if ("component".equals(attrName)) {
                intent.setComponent(ComponentName.unflattenFromString(attrValue));
            } else if ("flags".equals(attrName)) {
                intent.setFlags(Integer.parseInt(attrValue, 16));
            } else {
                Log.e(TAG, "restoreFromXml: unknown attribute=" + attrName);
            }
        }
        while (true) {
            int attrNdx2 = in.next();
            int event = attrNdx2;
            if (attrNdx2 == 1 || (event == 3 && in.getDepth() >= outerDepth)) {
                return intent;
            }
            if (event == 2) {
                String name = in.getName();
                if (TAG_CATEGORIES.equals(name)) {
                    for (int attrNdx3 = in.getAttributeCount() - 1; attrNdx3 >= 0; attrNdx3--) {
                        intent.addCategory(in.getAttributeValue(attrNdx3));
                    }
                } else {
                    Log.w(TAG, "restoreFromXml: unknown name=" + name);
                    XmlUtils.skipCurrentTag(in);
                }
            }
        }
        return intent;
    }

    public static String normalizeMimeType(String type) {
        if (type == null) {
            return null;
        }
        String type2 = type.trim().toLowerCase(Locale.ROOT);
        int semicolonIndex = type2.indexOf(59);
        if (semicolonIndex != -1) {
            return type2.substring(0, semicolonIndex);
        }
        return type2;
    }

    @UnsupportedAppUsage
    public void prepareToLeaveProcess(Context context) {
        prepareToLeaveProcess(this.mComponent == null || !Objects.equals(this.mComponent.getPackageName(), context.getPackageName()));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0144, code lost:
        if (r1.equals(ACTION_PROVIDER_CHANGED) == false) goto L_0x0151;
     */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0155  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void prepareToLeaveProcess(boolean r7) {
        /*
            r6 = this;
            r0 = 0
            r6.setAllowFds(r0)
            android.content.Intent r1 = r6.mSelector
            if (r1 == 0) goto L_0x000d
            android.content.Intent r1 = r6.mSelector
            r1.prepareToLeaveProcess((boolean) r7)
        L_0x000d:
            android.content.ClipData r1 = r6.mClipData
            if (r1 == 0) goto L_0x001a
            android.content.ClipData r1 = r6.mClipData
            int r2 = r6.getFlags()
            r1.prepareToLeaveProcess(r7, r2)
        L_0x001a:
            android.os.Bundle r1 = r6.mExtras
            if (r1 == 0) goto L_0x0038
            android.os.Bundle r1 = r6.mExtras
            boolean r1 = r1.isParcelled()
            if (r1 != 0) goto L_0x0038
            android.os.Bundle r1 = r6.mExtras
            java.lang.String r2 = "android.intent.extra.INTENT"
            java.lang.Object r1 = r1.get(r2)
            boolean r2 = r1 instanceof android.content.Intent
            if (r2 == 0) goto L_0x0038
            r2 = r1
            android.content.Intent r2 = (android.content.Intent) r2
            r2.prepareToLeaveProcess((boolean) r7)
        L_0x0038:
            java.lang.String r1 = r6.mAction
            r2 = 1
            r3 = -1
            if (r1 == 0) goto L_0x011d
            android.net.Uri r1 = r6.mData
            if (r1 == 0) goto L_0x011d
            boolean r1 = android.os.StrictMode.vmFileUriExposureEnabled()
            if (r1 == 0) goto L_0x011d
            if (r7 == 0) goto L_0x011d
            java.lang.String r1 = r6.mAction
            int r4 = r1.hashCode()
            switch(r4) {
                case -1823790459: goto L_0x0106;
                case -1665311200: goto L_0x00fc;
                case -1514214344: goto L_0x00f2;
                case -1142424621: goto L_0x00e7;
                case -963871873: goto L_0x00dd;
                case -808646005: goto L_0x00d2;
                case -625887599: goto L_0x00c7;
                case 257177710: goto L_0x00bd;
                case 410719838: goto L_0x00b3;
                case 582421979: goto L_0x00a7;
                case 852070077: goto L_0x009b;
                case 1412829408: goto L_0x008f;
                case 1431947322: goto L_0x0083;
                case 1599438242: goto L_0x0077;
                case 1920444806: goto L_0x006b;
                case 1964681210: goto L_0x0060;
                case 2045140818: goto L_0x0055;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x0110
        L_0x0055:
            java.lang.String r4 = "android.intent.action.MEDIA_BAD_REMOVAL"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 7
            goto L_0x0111
        L_0x0060:
            java.lang.String r4 = "android.intent.action.MEDIA_CHECKING"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 2
            goto L_0x0111
        L_0x006b:
            java.lang.String r4 = "android.intent.action.PACKAGE_VERIFIED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 15
            goto L_0x0111
        L_0x0077:
            java.lang.String r4 = "android.intent.action.PACKAGE_ENABLE_ROLLBACK"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 16
            goto L_0x0111
        L_0x0083:
            java.lang.String r4 = "android.intent.action.MEDIA_UNMOUNTABLE"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 8
            goto L_0x0111
        L_0x008f:
            java.lang.String r4 = "android.intent.action.MEDIA_SCANNER_STARTED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 10
            goto L_0x0111
        L_0x009b:
            java.lang.String r4 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 12
            goto L_0x0111
        L_0x00a7:
            java.lang.String r4 = "android.intent.action.PACKAGE_NEEDS_VERIFICATION"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 13
            goto L_0x0111
        L_0x00b3:
            java.lang.String r4 = "android.intent.action.MEDIA_UNSHARED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 6
            goto L_0x0111
        L_0x00bd:
            java.lang.String r4 = "android.intent.action.MEDIA_NOFS"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 3
            goto L_0x0111
        L_0x00c7:
            java.lang.String r4 = "android.intent.action.MEDIA_EJECT"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 9
            goto L_0x0111
        L_0x00d2:
            java.lang.String r4 = "com.qualcomm.qti.intent.action.PACKAGE_NEEDS_OPTIONAL_VERIFICATION"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 14
            goto L_0x0111
        L_0x00dd:
            java.lang.String r4 = "android.intent.action.MEDIA_UNMOUNTED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = r2
            goto L_0x0111
        L_0x00e7:
            java.lang.String r4 = "android.intent.action.MEDIA_SCANNER_FINISHED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 11
            goto L_0x0111
        L_0x00f2:
            java.lang.String r4 = "android.intent.action.MEDIA_MOUNTED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 4
            goto L_0x0111
        L_0x00fc:
            java.lang.String r4 = "android.intent.action.MEDIA_REMOVED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = r0
            goto L_0x0111
        L_0x0106:
            java.lang.String r4 = "android.intent.action.MEDIA_SHARED"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0110
            r1 = 5
            goto L_0x0111
        L_0x0110:
            r1 = r3
        L_0x0111:
            switch(r1) {
                case 0: goto L_0x011c;
                case 1: goto L_0x011c;
                case 2: goto L_0x011c;
                case 3: goto L_0x011c;
                case 4: goto L_0x011c;
                case 5: goto L_0x011c;
                case 6: goto L_0x011c;
                case 7: goto L_0x011c;
                case 8: goto L_0x011c;
                case 9: goto L_0x011c;
                case 10: goto L_0x011c;
                case 11: goto L_0x011c;
                case 12: goto L_0x011c;
                case 13: goto L_0x011c;
                case 14: goto L_0x011c;
                case 15: goto L_0x011c;
                case 16: goto L_0x011c;
                default: goto L_0x0114;
            }
        L_0x0114:
            android.net.Uri r1 = r6.mData
            java.lang.String r4 = "Intent.getData()"
            r1.checkFileUriExposed(r4)
            goto L_0x011d
        L_0x011c:
        L_0x011d:
            java.lang.String r1 = r6.mAction
            if (r1 == 0) goto L_0x0162
            android.net.Uri r1 = r6.mData
            if (r1 == 0) goto L_0x0162
            boolean r1 = android.os.StrictMode.vmContentUriWithoutPermissionEnabled()
            if (r1 == 0) goto L_0x0162
            if (r7 == 0) goto L_0x0162
            java.lang.String r1 = r6.mAction
            int r4 = r1.hashCode()
            r5 = -577088908(0xffffffffdd9a5274, float:-1.39000975E18)
            if (r4 == r5) goto L_0x0147
            r2 = 1662413067(0x6316690b, float:2.7745808E21)
            if (r4 == r2) goto L_0x013e
            goto L_0x0151
        L_0x013e:
            java.lang.String r2 = "android.intent.action.PROVIDER_CHANGED"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0151
            goto L_0x0152
        L_0x0147:
            java.lang.String r0 = "android.provider.action.QUICK_CONTACT"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0151
            r0 = r2
            goto L_0x0152
        L_0x0151:
            r0 = r3
        L_0x0152:
            switch(r0) {
                case 0: goto L_0x0161;
                case 1: goto L_0x0161;
                default: goto L_0x0155;
            }
        L_0x0155:
            android.net.Uri r0 = r6.mData
            java.lang.String r1 = "Intent.getData()"
            int r2 = r6.getFlags()
            r0.checkContentUriWithoutPermission(r1, r2)
            goto L_0x0162
        L_0x0161:
        L_0x0162:
            java.lang.String r0 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            java.lang.String r1 = r6.mAction
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x01ce
            android.net.Uri r0 = r6.mData
            if (r0 == 0) goto L_0x01ce
            java.lang.String r0 = "file"
            android.net.Uri r1 = r6.mData
            java.lang.String r1 = r1.getScheme()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x01ce
            if (r7 == 0) goto L_0x01ce
            android.app.Application r0 = android.app.AppGlobals.getInitialApplication()
            java.lang.Class<android.os.storage.StorageManager> r1 = android.os.storage.StorageManager.class
            java.lang.Object r0 = r0.getSystemService(r1)
            android.os.storage.StorageManager r0 = (android.os.storage.StorageManager) r0
            java.io.File r1 = new java.io.File
            android.net.Uri r2 = r6.mData
            java.lang.String r2 = r2.getPath()
            r1.<init>(r2)
            int r2 = android.os.Process.myPid()
            int r3 = android.os.Process.myUid()
            java.io.File r2 = r0.translateAppToSystem(r1, r2, r3)
            boolean r3 = java.util.Objects.equals(r1, r2)
            if (r3 != 0) goto L_0x01ce
            java.lang.String r3 = "Intent"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Translated "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = " to "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            android.util.Log.v(r3, r4)
            android.net.Uri r3 = android.net.Uri.fromFile(r2)
            r6.mData = r3
        L_0x01ce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.Intent.prepareToLeaveProcess(boolean):void");
    }

    public void prepareToEnterProcess() {
        setDefusable(true);
        if (this.mSelector != null) {
            this.mSelector.prepareToEnterProcess();
        }
        if (this.mClipData != null) {
            this.mClipData.prepareToEnterProcess();
        }
        if (this.mContentUserHint != -2 && UserHandle.getAppId(Process.myUid()) != 1000) {
            fixUris(this.mContentUserHint);
            this.mContentUserHint = -2;
        }
    }

    public boolean hasWebURI() {
        if (getData() == null) {
            return false;
        }
        String scheme = getScheme();
        if (TextUtils.isEmpty(scheme)) {
            return false;
        }
        if (scheme.equals(IntentFilter.SCHEME_HTTP) || scheme.equals(IntentFilter.SCHEME_HTTPS)) {
            return true;
        }
        return false;
    }

    public boolean isWebIntent() {
        return "android.intent.action.VIEW".equals(this.mAction) && hasWebURI();
    }

    public void fixUris(int contentUserHint) {
        Uri output;
        Uri data = getData();
        if (data != null) {
            this.mData = ContentProvider.maybeAddUserId(data, contentUserHint);
        }
        if (this.mClipData != null) {
            this.mClipData.fixUris(contentUserHint);
        }
        String action = getAction();
        if (ACTION_SEND.equals(action)) {
            Uri stream = (Uri) getParcelableExtra(EXTRA_STREAM);
            if (stream != null) {
                putExtra(EXTRA_STREAM, (Parcelable) ContentProvider.maybeAddUserId(stream, contentUserHint));
            }
        } else if (ACTION_SEND_MULTIPLE.equals(action)) {
            ArrayList<Uri> streams = getParcelableArrayListExtra(EXTRA_STREAM);
            if (streams != null) {
                ArrayList<Uri> newStreams = new ArrayList<>();
                for (int i = 0; i < streams.size(); i++) {
                    newStreams.add(ContentProvider.maybeAddUserId(streams.get(i), contentUserHint));
                }
                putParcelableArrayListExtra(EXTRA_STREAM, newStreams);
            }
        } else if ((MediaStore.ACTION_IMAGE_CAPTURE.equals(action) || MediaStore.ACTION_IMAGE_CAPTURE_SECURE.equals(action) || MediaStore.ACTION_VIDEO_CAPTURE.equals(action)) && (output = (Uri) getParcelableExtra(MediaStore.EXTRA_OUTPUT)) != null) {
            putExtra(MediaStore.EXTRA_OUTPUT, (Parcelable) ContentProvider.maybeAddUserId(output, contentUserHint));
        }
    }

    public boolean migrateExtraStreamToClipData() {
        if ((this.mExtras != null && this.mExtras.isParcelled()) || getClipData() != null) {
            return false;
        }
        String action = getAction();
        if (ACTION_CHOOSER.equals(action)) {
            boolean migrated = false;
            try {
                Intent intent = (Intent) getParcelableExtra(EXTRA_INTENT);
                if (intent != null) {
                    migrated = false | intent.migrateExtraStreamToClipData();
                }
            } catch (ClassCastException e) {
            }
            try {
                Parcelable[] intents = getParcelableArrayExtra(EXTRA_INITIAL_INTENTS);
                if (intents != null) {
                    for (Parcelable parcelable : intents) {
                        Intent intent2 = (Intent) parcelable;
                        if (intent2 != null) {
                            migrated |= intent2.migrateExtraStreamToClipData();
                        }
                    }
                }
            } catch (ClassCastException e2) {
            }
            return migrated;
        }
        if (ACTION_SEND.equals(action)) {
            try {
                Uri stream = (Uri) getParcelableExtra(EXTRA_STREAM);
                CharSequence text = getCharSequenceExtra(EXTRA_TEXT);
                String htmlText = getStringExtra("android.intent.extra.HTML_TEXT");
                if (!(stream == null && text == null && htmlText == null)) {
                    setClipData(new ClipData((CharSequence) null, new String[]{getType()}, new ClipData.Item(text, htmlText, (Intent) null, stream)));
                    addFlags(1);
                    return true;
                }
            } catch (ClassCastException e3) {
            }
        } else if (ACTION_SEND_MULTIPLE.equals(action)) {
            try {
                ArrayList<Uri> streams = getParcelableArrayListExtra(EXTRA_STREAM);
                ArrayList<CharSequence> texts = getCharSequenceArrayListExtra(EXTRA_TEXT);
                ArrayList<String> htmlTexts = getStringArrayListExtra("android.intent.extra.HTML_TEXT");
                int num = -1;
                if (streams != null) {
                    num = streams.size();
                }
                if (texts != null) {
                    if (num >= 0 && num != texts.size()) {
                        return false;
                    }
                    num = texts.size();
                }
                if (htmlTexts != null) {
                    if (num >= 0 && num != htmlTexts.size()) {
                        return false;
                    }
                    num = htmlTexts.size();
                }
                if (num > 0) {
                    ClipData clipData = new ClipData((CharSequence) null, new String[]{getType()}, makeClipItem(streams, texts, htmlTexts, 0));
                    for (int i = 1; i < num; i++) {
                        clipData.addItem(makeClipItem(streams, texts, htmlTexts, i));
                    }
                    setClipData(clipData);
                    addFlags(1);
                    return true;
                }
            } catch (ClassCastException e4) {
            }
        } else if (MediaStore.ACTION_IMAGE_CAPTURE.equals(action) || MediaStore.ACTION_IMAGE_CAPTURE_SECURE.equals(action) || MediaStore.ACTION_VIDEO_CAPTURE.equals(action)) {
            try {
                Uri output = (Uri) getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                if (output != null) {
                    setClipData(ClipData.newRawUri("", output));
                    addFlags(3);
                    return true;
                }
            } catch (ClassCastException e5) {
                return false;
            }
        }
        return false;
    }

    public static String dockStateToString(int dock) {
        switch (dock) {
            case 0:
                return "EXTRA_DOCK_STATE_UNDOCKED";
            case 1:
                return "EXTRA_DOCK_STATE_DESK";
            case 2:
                return "EXTRA_DOCK_STATE_CAR";
            case 3:
                return "EXTRA_DOCK_STATE_LE_DESK";
            case 4:
                return "EXTRA_DOCK_STATE_HE_DESK";
            default:
                return Integer.toString(dock);
        }
    }

    private static ClipData.Item makeClipItem(ArrayList<Uri> streams, ArrayList<CharSequence> texts, ArrayList<String> htmlTexts, int which) {
        return new ClipData.Item(texts != null ? texts.get(which) : null, htmlTexts != null ? htmlTexts.get(which) : null, (Intent) null, streams != null ? streams.get(which) : null);
    }

    public boolean isDocument() {
        return (this.mFlags & 524288) == 524288;
    }
}
