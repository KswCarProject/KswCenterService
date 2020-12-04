package android.view;

import android.animation.StateListAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.slice.Slice;
import android.content.AutofillOptions;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Interpolator;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManagerGlobal;
import android.net.TrafficStats;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.BatteryStats;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.CalendarContract;
import android.provider.Downloads;
import android.provider.SettingsStringUtil;
import android.provider.Telephony;
import android.provider.UserDictionary;
import android.sysprop.DisplayProperties;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.Log;
import android.util.LongSparseLongArray;
import android.util.Pools;
import android.util.Property;
import android.util.SeempLog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.StateSet;
import android.util.StatsLog;
import android.util.SuperNotCalledException;
import android.util.TypedValue;
import android.view.AccessibilityIterators;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.DisplayCutout;
import android.view.KeyEvent;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsetsAnimationListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityEventSource;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.IntFlagMapping;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ScrollBarDrawable;
import com.android.internal.R;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.view.TooltipPopup;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.widget.ScrollBarUtils;
import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class View implements Drawable.Callback, KeyEvent.Callback, AccessibilityEventSource {
    public static final int ACCESSIBILITY_CURSOR_POSITION_UNDEFINED = -1;
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    static final int ACCESSIBILITY_LIVE_REGION_DEFAULT = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    static final int ALL_RTL_PROPERTIES_RESOLVED = 1610678816;
    public static final Property<View, Float> ALPHA = new FloatProperty<View>("alpha") {
        public void setValue(View object, float value) {
            object.setAlpha(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getAlpha());
        }
    };
    public static final int AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 1;
    private static final int[] AUTOFILL_HIGHLIGHT_ATTR = {16844136};
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE = "creditCardExpirationDate";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY = "creditCardExpirationDay";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH = "creditCardExpirationMonth";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR = "creditCardExpirationYear";
    public static final String AUTOFILL_HINT_CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE = "creditCardSecurityCode";
    public static final String AUTOFILL_HINT_EMAIL_ADDRESS = "emailAddress";
    public static final String AUTOFILL_HINT_NAME = "name";
    public static final String AUTOFILL_HINT_PASSWORD = "password";
    public static final String AUTOFILL_HINT_PHONE = "phone";
    public static final String AUTOFILL_HINT_POSTAL_ADDRESS = "postalAddress";
    public static final String AUTOFILL_HINT_POSTAL_CODE = "postalCode";
    public static final String AUTOFILL_HINT_USERNAME = "username";
    private static final String AUTOFILL_LOG_TAG = "View.Autofill";
    public static final int AUTOFILL_TYPE_DATE = 4;
    public static final int AUTOFILL_TYPE_LIST = 3;
    public static final int AUTOFILL_TYPE_NONE = 0;
    public static final int AUTOFILL_TYPE_TEXT = 1;
    public static final int AUTOFILL_TYPE_TOGGLE = 2;
    static final int CLICKABLE = 16384;
    private static final String CONTENT_CAPTURE_LOG_TAG = "View.ContentCapture";
    static final int CONTEXT_CLICKABLE = 8388608;
    @UnsupportedAppUsage
    private static final boolean DBG = false;
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    static final int DEBUG_CORNERS_COLOR = Color.rgb(63, 127, 255);
    static final int DEBUG_CORNERS_SIZE_DIP = 8;
    public static boolean DEBUG_DRAW = false;
    static final int DISABLED = 32;
    public static final int DRAG_FLAG_GLOBAL = 256;
    public static final int DRAG_FLAG_GLOBAL_PERSISTABLE_URI_PERMISSION = 64;
    public static final int DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION = 128;
    public static final int DRAG_FLAG_GLOBAL_URI_READ = 1;
    public static final int DRAG_FLAG_GLOBAL_URI_WRITE = 2;
    public static final int DRAG_FLAG_OPAQUE = 512;
    static final int DRAG_MASK = 3;
    static final int DRAWING_CACHE_ENABLED = 32768;
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_AUTO = 0;
    private static final int[] DRAWING_CACHE_QUALITY_FLAGS = {0, 524288, 1048576};
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_LOW = 524288;
    static final int DRAWING_CACHE_QUALITY_MASK = 1572864;
    static final int DRAW_MASK = 128;
    static final int DUPLICATE_PARENT_STATE = 4194304;
    protected static final int[] EMPTY_STATE_SET = StateSet.get(0);
    static final int ENABLED = 0;
    protected static final int[] ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(14);
    protected static final int[] ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(15);
    protected static final int[] ENABLED_FOCUSED_STATE_SET = StateSet.get(12);
    protected static final int[] ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(13);
    static final int ENABLED_MASK = 32;
    protected static final int[] ENABLED_SELECTED_STATE_SET = StateSet.get(10);
    protected static final int[] ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(11);
    protected static final int[] ENABLED_STATE_SET = StateSet.get(8);
    protected static final int[] ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(9);
    static final int FADING_EDGE_HORIZONTAL = 4096;
    static final int FADING_EDGE_MASK = 12288;
    static final int FADING_EDGE_NONE = 0;
    static final int FADING_EDGE_VERTICAL = 8192;
    static final int FILTER_TOUCHES_WHEN_OBSCURED = 1024;
    public static final int FIND_VIEWS_WITH_ACCESSIBILITY_NODE_PROVIDERS = 4;
    public static final int FIND_VIEWS_WITH_CONTENT_DESCRIPTION = 2;
    public static final int FIND_VIEWS_WITH_TEXT = 1;
    private static final int FITS_SYSTEM_WINDOWS = 2;
    public static final int FOCUSABLE = 1;
    public static final int FOCUSABLES_ALL = 0;
    public static final int FOCUSABLES_TOUCH_MODE = 1;
    public static final int FOCUSABLE_AUTO = 16;
    static final int FOCUSABLE_IN_TOUCH_MODE = 262144;
    private static final int FOCUSABLE_MASK = 17;
    protected static final int[] FOCUSED_SELECTED_STATE_SET = StateSet.get(6);
    protected static final int[] FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(7);
    protected static final int[] FOCUSED_STATE_SET = StateSet.get(4);
    protected static final int[] FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(5);
    public static final int FOCUS_BACKWARD = 1;
    public static final int FOCUS_DOWN = 130;
    public static final int FOCUS_FORWARD = 2;
    public static final int FOCUS_LEFT = 17;
    public static final int FOCUS_RIGHT = 66;
    public static final int FOCUS_UP = 33;
    public static final int GONE = 8;
    public static final int HAPTIC_FEEDBACK_ENABLED = 268435456;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    static final int IMPORTANT_FOR_ACCESSIBILITY_DEFAULT = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_AUTO = 0;
    public static final int IMPORTANT_FOR_AUTOFILL_NO = 2;
    public static final int IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS = 8;
    public static final int IMPORTANT_FOR_AUTOFILL_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS = 4;
    public static final int INVISIBLE = 4;
    public static final int KEEP_SCREEN_ON = 67108864;
    public static final int LAST_APP_AUTOFILL_ID = 1073741823;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    private static final int LAYOUT_DIRECTION_DEFAULT = 2;
    private static final int[] LAYOUT_DIRECTION_FLAGS = {0, 1, 2, 3};
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    static final int LAYOUT_DIRECTION_RESOLVED_DEFAULT = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int LAYOUT_DIRECTION_UNDEFINED = -1;
    static final int LONG_CLICKABLE = 2097152;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    @UnsupportedAppUsage
    public static final int NAVIGATION_BAR_TRANSIENT = 134217728;
    public static final int NAVIGATION_BAR_TRANSLUCENT = Integer.MIN_VALUE;
    public static final int NAVIGATION_BAR_TRANSPARENT = 32768;
    public static final int NAVIGATION_BAR_UNHIDE = 536870912;
    public static final int NOT_FOCUSABLE = 0;
    public static final int NO_ID = -1;
    static final int OPTIONAL_FITS_SYSTEM_WINDOWS = 2048;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    static final int PARENT_SAVE_DISABLED = 536870912;
    static final int PARENT_SAVE_DISABLED_MASK = 536870912;
    static final int PFLAG2_ACCESSIBILITY_FOCUSED = 67108864;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_MASK = 25165824;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_SHIFT = 23;
    static final int PFLAG2_DRAG_CAN_ACCEPT = 1;
    static final int PFLAG2_DRAG_HOVERED = 2;
    static final int PFLAG2_DRAWABLE_RESOLVED = 1073741824;
    static final int PFLAG2_HAS_TRANSIENT_STATE = Integer.MIN_VALUE;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK = 7340032;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT = 20;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK = 12;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT = 2;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED = 32;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK = 48;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL = 16;
    static final int PFLAG2_PADDING_RESOLVED = 536870912;
    static final int PFLAG2_SUBTREE_ACCESSIBILITY_STATE_CHANGED = 134217728;
    private static final int[] PFLAG2_TEXT_ALIGNMENT_FLAGS = {0, 8192, 16384, 24576, 32768, 40960, 49152};
    static final int PFLAG2_TEXT_ALIGNMENT_MASK = 57344;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT = 13;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED = 65536;
    private static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT = 131072;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK = 917504;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT = 17;
    private static final int[] PFLAG2_TEXT_DIRECTION_FLAGS = {0, 64, 128, 192, 256, 320, MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION, 448};
    static final int PFLAG2_TEXT_DIRECTION_MASK = 448;
    static final int PFLAG2_TEXT_DIRECTION_MASK_SHIFT = 6;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED = 512;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT = 1024;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK = 7168;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT = 10;
    static final int PFLAG2_VIEW_QUICK_REJECTED = 268435456;
    private static final int PFLAG3_ACCESSIBILITY_HEADING = Integer.MIN_VALUE;
    private static final int PFLAG3_AGGREGATED_VISIBLE = 536870912;
    static final int PFLAG3_APPLYING_INSETS = 32;
    static final int PFLAG3_ASSIST_BLOCKED = 16384;
    private static final int PFLAG3_AUTOFILLID_EXPLICITLY_SET = 1073741824;
    static final int PFLAG3_CALLED_SUPER = 16;
    private static final int PFLAG3_CLUSTER = 32768;
    private static final int PFLAG3_FINGER_DOWN = 131072;
    static final int PFLAG3_FITTING_SYSTEM_WINDOWS = 64;
    private static final int PFLAG3_FOCUSED_BY_DEFAULT = 262144;
    private static final int PFLAG3_HAS_OVERLAPPING_RENDERING_FORCED = 16777216;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK = 7864320;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_SHIFT = 19;
    private static final int PFLAG3_IS_AUTOFILLED = 65536;
    static final int PFLAG3_IS_LAID_OUT = 4;
    static final int PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT = 8;
    static final int PFLAG3_NESTED_SCROLLING_ENABLED = 128;
    static final int PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT = 134217728;
    private static final int PFLAG3_NO_REVEAL_ON_FOCUS = 67108864;
    private static final int PFLAG3_OVERLAPPING_RENDERING_FORCED_VALUE = 8388608;
    private static final int PFLAG3_SCREEN_READER_FOCUSABLE = 268435456;
    static final int PFLAG3_SCROLL_INDICATOR_BOTTOM = 512;
    static final int PFLAG3_SCROLL_INDICATOR_END = 8192;
    static final int PFLAG3_SCROLL_INDICATOR_LEFT = 1024;
    static final int PFLAG3_SCROLL_INDICATOR_RIGHT = 2048;
    static final int PFLAG3_SCROLL_INDICATOR_START = 4096;
    static final int PFLAG3_SCROLL_INDICATOR_TOP = 256;
    static final int PFLAG3_TEMPORARY_DETACH = 33554432;
    static final int PFLAG3_VIEW_IS_ANIMATING_ALPHA = 2;
    static final int PFLAG3_VIEW_IS_ANIMATING_TRANSFORM = 1;
    static final int PFLAG_ACTIVATED = 1073741824;
    static final int PFLAG_ALPHA_SET = 262144;
    static final int PFLAG_ANIMATION_STARTED = 65536;
    private static final int PFLAG_AWAKEN_SCROLL_BARS_ON_ATTACH = 134217728;
    static final int PFLAG_CANCEL_NEXT_UP_EVENT = 67108864;
    static final int PFLAG_DIRTY = 2097152;
    static final int PFLAG_DIRTY_MASK = 2097152;
    static final int PFLAG_DRAWABLE_STATE_DIRTY = 1024;
    static final int PFLAG_DRAWING_CACHE_VALID = 32768;
    static final int PFLAG_DRAWN = 32;
    static final int PFLAG_DRAW_ANIMATION = 64;
    static final int PFLAG_FOCUSED = 2;
    static final int PFLAG_FORCE_LAYOUT = 4096;
    static final int PFLAG_HAS_BOUNDS = 16;
    private static final int PFLAG_HOVERED = 268435456;
    static final int PFLAG_INVALIDATED = Integer.MIN_VALUE;
    static final int PFLAG_IS_ROOT_NAMESPACE = 8;
    static final int PFLAG_LAYOUT_REQUIRED = 8192;
    static final int PFLAG_MEASURED_DIMENSION_SET = 2048;
    private static final int PFLAG_NOTIFY_AUTOFILL_MANAGER_ON_CLICK = 536870912;
    static final int PFLAG_OPAQUE_BACKGROUND = 8388608;
    static final int PFLAG_OPAQUE_MASK = 25165824;
    static final int PFLAG_OPAQUE_SCROLLBARS = 16777216;
    private static final int PFLAG_PREPRESSED = 33554432;
    private static final int PFLAG_PRESSED = 16384;
    static final int PFLAG_REQUEST_TRANSPARENT_REGIONS = 512;
    private static final int PFLAG_SAVE_STATE_CALLED = 131072;
    static final int PFLAG_SCROLL_CONTAINER = 524288;
    static final int PFLAG_SCROLL_CONTAINER_ADDED = 1048576;
    static final int PFLAG_SELECTED = 4;
    static final int PFLAG_SKIP_DRAW = 128;
    static final int PFLAG_WANTS_FOCUS = 1;
    private static final int POPULATING_ACCESSIBILITY_EVENT_TYPES = 172479;
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(30);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(31);
    protected static final int[] PRESSED_ENABLED_FOCUSED_STATE_SET = StateSet.get(28);
    protected static final int[] PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(29);
    protected static final int[] PRESSED_ENABLED_SELECTED_STATE_SET = StateSet.get(26);
    protected static final int[] PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(27);
    protected static final int[] PRESSED_ENABLED_STATE_SET = StateSet.get(24);
    protected static final int[] PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(25);
    protected static final int[] PRESSED_FOCUSED_SELECTED_STATE_SET = StateSet.get(22);
    protected static final int[] PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(23);
    protected static final int[] PRESSED_FOCUSED_STATE_SET = StateSet.get(20);
    protected static final int[] PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(21);
    protected static final int[] PRESSED_SELECTED_STATE_SET = StateSet.get(18);
    protected static final int[] PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(19);
    protected static final int[] PRESSED_STATE_SET = StateSet.get(16);
    protected static final int[] PRESSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(17);
    private static final int PROVIDER_BACKGROUND = 0;
    private static final int PROVIDER_BOUNDS = 2;
    private static final int PROVIDER_NONE = 1;
    private static final int PROVIDER_PADDED_BOUNDS = 3;
    public static final int PUBLIC_STATUS_BAR_VISIBILITY_MASK = 16375;
    public static final Property<View, Float> ROTATION = new FloatProperty<View>("rotation") {
        public void setValue(View object, float value) {
            object.setRotation(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getRotation());
        }
    };
    public static final Property<View, Float> ROTATION_X = new FloatProperty<View>("rotationX") {
        public void setValue(View object, float value) {
            object.setRotationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getRotationX());
        }
    };
    public static final Property<View, Float> ROTATION_Y = new FloatProperty<View>("rotationY") {
        public void setValue(View object, float value) {
            object.setRotationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getRotationY());
        }
    };
    static final int SAVE_DISABLED = 65536;
    static final int SAVE_DISABLED_MASK = 65536;
    public static final Property<View, Float> SCALE_X = new FloatProperty<View>("scaleX") {
        public void setValue(View object, float value) {
            object.setScaleX(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getScaleX());
        }
    };
    public static final Property<View, Float> SCALE_Y = new FloatProperty<View>("scaleY") {
        public void setValue(View object, float value) {
            object.setScaleY(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getScaleY());
        }
    };
    public static final int SCREEN_STATE_OFF = 0;
    public static final int SCREEN_STATE_ON = 1;
    static final int SCROLLBARS_HORIZONTAL = 256;
    static final int SCROLLBARS_INSET_MASK = 16777216;
    public static final int SCROLLBARS_INSIDE_INSET = 16777216;
    public static final int SCROLLBARS_INSIDE_OVERLAY = 0;
    static final int SCROLLBARS_MASK = 768;
    static final int SCROLLBARS_NONE = 0;
    public static final int SCROLLBARS_OUTSIDE_INSET = 50331648;
    static final int SCROLLBARS_OUTSIDE_MASK = 33554432;
    public static final int SCROLLBARS_OUTSIDE_OVERLAY = 33554432;
    static final int SCROLLBARS_STYLE_MASK = 50331648;
    static final int SCROLLBARS_VERTICAL = 512;
    public static final int SCROLLBAR_POSITION_DEFAULT = 0;
    public static final int SCROLLBAR_POSITION_LEFT = 1;
    public static final int SCROLLBAR_POSITION_RIGHT = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    static final int SCROLL_INDICATORS_NONE = 0;
    static final int SCROLL_INDICATORS_PFLAG3_MASK = 16128;
    static final int SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT = 8;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    protected static final int[] SELECTED_STATE_SET = StateSet.get(2);
    protected static final int[] SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(3);
    public static final int SOUND_EFFECTS_ENABLED = 134217728;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_BACK = 4194304;
    public static final int STATUS_BAR_DISABLE_CLOCK = 8388608;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_EXPAND = 65536;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_HOME = 2097152;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ICONS = 131072;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_TICKER = 524288;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_RECENT = 16777216;
    public static final int STATUS_BAR_DISABLE_SEARCH = 33554432;
    public static final int STATUS_BAR_DISABLE_SYSTEM_INFO = 1048576;
    @Deprecated
    public static final int STATUS_BAR_HIDDEN = 1;
    public static final int STATUS_BAR_TRANSIENT = 67108864;
    public static final int STATUS_BAR_TRANSLUCENT = 1073741824;
    public static final int STATUS_BAR_TRANSPARENT = 8;
    public static final int STATUS_BAR_UNHIDE = 268435456;
    @Deprecated
    public static final int STATUS_BAR_VISIBLE = 0;
    public static final int SYSTEM_UI_CLEARABLE_FLAGS = 7;
    public static final int SYSTEM_UI_FLAG_FULLSCREEN = 4;
    public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 2;
    public static final int SYSTEM_UI_FLAG_IMMERSIVE = 2048;
    public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 4096;
    public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 1024;
    public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 512;
    public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 256;
    public static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR = 16;
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;
    public static final int SYSTEM_UI_FLAG_LOW_PROFILE = 1;
    public static final int SYSTEM_UI_FLAG_VISIBLE = 0;
    public static final int SYSTEM_UI_LAYOUT_FLAGS = 1536;
    private static final int SYSTEM_UI_RESERVED_LEGACY1 = 16384;
    private static final int SYSTEM_UI_RESERVED_LEGACY2 = 65536;
    public static final int SYSTEM_UI_TRANSPARENT = 32776;
    public static final int TEXT_ALIGNMENT_CENTER = 4;
    private static final int TEXT_ALIGNMENT_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_GRAVITY = 1;
    public static final int TEXT_ALIGNMENT_INHERIT = 0;
    static final int TEXT_ALIGNMENT_RESOLVED_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_TEXT_END = 3;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;
    public static final int TEXT_ALIGNMENT_VIEW_END = 6;
    public static final int TEXT_ALIGNMENT_VIEW_START = 5;
    public static final int TEXT_DIRECTION_ANY_RTL = 2;
    private static final int TEXT_DIRECTION_DEFAULT = 0;
    public static final int TEXT_DIRECTION_FIRST_STRONG = 1;
    public static final int TEXT_DIRECTION_FIRST_STRONG_LTR = 6;
    public static final int TEXT_DIRECTION_FIRST_STRONG_RTL = 7;
    public static final int TEXT_DIRECTION_INHERIT = 0;
    public static final int TEXT_DIRECTION_LOCALE = 5;
    public static final int TEXT_DIRECTION_LTR = 3;
    static final int TEXT_DIRECTION_RESOLVED_DEFAULT = 1;
    public static final int TEXT_DIRECTION_RTL = 4;
    static final int TOOLTIP = 1073741824;
    public static final Property<View, Float> TRANSLATION_X = new FloatProperty<View>("translationX") {
        public void setValue(View object, float value) {
            object.setTranslationX(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getTranslationX());
        }
    };
    public static final Property<View, Float> TRANSLATION_Y = new FloatProperty<View>("translationY") {
        public void setValue(View object, float value) {
            object.setTranslationY(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getTranslationY());
        }
    };
    public static final Property<View, Float> TRANSLATION_Z = new FloatProperty<View>("translationZ") {
        public void setValue(View object, float value) {
            object.setTranslationZ(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getTranslationZ());
        }
    };
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    protected static final String VIEW_LOG_TAG = "View";
    protected static final int VIEW_STRUCTURE_FOR_ASSIST = 0;
    protected static final int VIEW_STRUCTURE_FOR_AUTOFILL = 1;
    protected static final int VIEW_STRUCTURE_FOR_CONTENT_CAPTURE = 2;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    static final int VISIBILITY_MASK = 12;
    public static final int VISIBLE = 0;
    static final int WILL_NOT_CACHE_DRAWING = 131072;
    static final int WILL_NOT_DRAW = 128;
    protected static final int[] WINDOW_FOCUSED_STATE_SET = StateSet.get(1);
    public static final Property<View, Float> X = new FloatProperty<View>("x") {
        public void setValue(View object, float value) {
            object.setX(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getX());
        }
    };
    public static final Property<View, Float> Y = new FloatProperty<View>("y") {
        public void setValue(View object, float value) {
            object.setY(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getY());
        }
    };
    public static final Property<View, Float> Z = new FloatProperty<View>("z") {
        public void setValue(View object, float value) {
            object.setZ(value);
        }

        public Float get(View object) {
            return Float.valueOf(object.getZ());
        }
    };
    private static SparseArray<String> mAttributeMap;
    private static boolean sAcceptZeroSizeDragShadow;
    private static boolean sAlwaysAssignFocus;
    private static boolean sAlwaysRemeasureExactly = false;
    private static boolean sAutoFocusableOffUIThreadWontNotifyParents;
    static boolean sBrokenInsetsDispatch;
    protected static boolean sBrokenWindowBackground;
    private static boolean sCanFocusZeroSized;
    static boolean sCascadedDragDrop;
    private static boolean sCompatibilityDone = false;
    private static Paint sDebugPaint;
    public static boolean sDebugViewAttributes = false;
    public static String sDebugViewAttributesApplicationPackage;
    static boolean sHasFocusableExcludeAutoFocusable;
    private static boolean sIgnoreMeasureCache = false;
    private static int sNextAccessibilityViewId;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    protected static boolean sPreserveMarginParamsInLayoutParamConversion;
    static boolean sTextureViewIgnoresDrawableSetters = false;
    static final ThreadLocal<Rect> sThreadLocal = new ThreadLocal<>();
    private static boolean sThrowOnInvalidFloatProperties;
    /* access modifiers changed from: private */
    public static boolean sUseBrokenMakeMeasureSpec = false;
    private static boolean sUseDefaultFocusHighlight;
    static boolean sUseZeroUnspecifiedMeasureSpec = false;
    private int mAccessibilityCursorPosition;
    @UnsupportedAppUsage
    AccessibilityDelegate mAccessibilityDelegate;
    private CharSequence mAccessibilityPaneTitle;
    private int mAccessibilityTraversalAfterId;
    private int mAccessibilityTraversalBeforeId;
    @UnsupportedAppUsage
    private int mAccessibilityViewId;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private ViewPropertyAnimator mAnimator;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    AttachInfo mAttachInfo;
    private SparseArray<int[]> mAttributeResolutionStacks;
    private SparseIntArray mAttributeSourceResId;
    @ViewDebug.ExportedProperty(category = "attributes", hasAdjacentMapping = true)
    public String[] mAttributes;
    private String[] mAutofillHints;
    private AutofillId mAutofillId;
    private int mAutofillViewId;
    @ViewDebug.ExportedProperty(deepExport = true, prefix = "bg_")
    @UnsupportedAppUsage
    private Drawable mBackground;
    private RenderNode mBackgroundRenderNode;
    @UnsupportedAppUsage
    private int mBackgroundResource;
    private boolean mBackgroundSizeChanged;
    private TintInfo mBackgroundTint;
    @ViewDebug.ExportedProperty(category = "layout")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected int mBottom;
    private ContentCaptureSession mCachedContentCaptureSession;
    @UnsupportedAppUsage
    public boolean mCachingFailed;
    @ViewDebug.ExportedProperty(category = "drawing")
    Rect mClipBounds;
    private ContentCaptureSession mContentCaptureSession;
    private CharSequence mContentDescription;
    /* access modifiers changed from: protected */
    @ViewDebug.ExportedProperty(deepExport = true)
    @UnsupportedAppUsage
    public Context mContext;
    protected Animation mCurrentAnimation;
    private Drawable mDefaultFocusHighlight;
    private Drawable mDefaultFocusHighlightCache;
    boolean mDefaultFocusHighlightEnabled;
    private boolean mDefaultFocusHighlightSizeChanged;
    private int[] mDrawableState;
    @UnsupportedAppUsage
    private Bitmap mDrawingCache;
    private int mDrawingCacheBackgroundColor;
    private int mExplicitStyle;
    private ViewTreeObserver mFloatingTreeObserver;
    @ViewDebug.ExportedProperty(deepExport = true, prefix = "fg_")
    private ForegroundInfo mForegroundInfo;
    private ArrayList<FrameMetricsObserver> mFrameMetricsObservers;
    GhostView mGhostView;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public boolean mHasPerformedLongPress;
    private boolean mHoveringTouchDelegate;
    @ViewDebug.ExportedProperty(resolveId = true)
    int mID;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextButtonPress;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    @UnsupportedAppUsage
    private SparseArray<Object> mKeyedTags;
    /* access modifiers changed from: private */
    public int mLabelForId;
    private boolean mLastIsOpaque;
    Paint mLayerPaint;
    @ViewDebug.ExportedProperty(category = "drawing", mapping = {@ViewDebug.IntToString(from = 0, to = "NONE"), @ViewDebug.IntToString(from = 1, to = "SOFTWARE"), @ViewDebug.IntToString(from = 2, to = "HARDWARE")})
    int mLayerType;
    private Insets mLayoutInsets;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected ViewGroup.LayoutParams mLayoutParams;
    @ViewDebug.ExportedProperty(category = "layout")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected int mLeft;
    private boolean mLeftPaddingDefined;
    @UnsupportedAppUsage
    ListenerInfo mListenerInfo;
    private float mLongClickX;
    private float mLongClickY;
    private MatchIdPredicate mMatchIdPredicate;
    private MatchLabelForPredicate mMatchLabelForPredicate;
    private LongSparseLongArray mMeasureCache;
    @ViewDebug.ExportedProperty(category = "measurement")
    @UnsupportedAppUsage
    int mMeasuredHeight;
    @ViewDebug.ExportedProperty(category = "measurement")
    @UnsupportedAppUsage
    int mMeasuredWidth;
    @ViewDebug.ExportedProperty(category = "measurement")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private int mMinHeight;
    @ViewDebug.ExportedProperty(category = "measurement")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private int mMinWidth;
    private ViewParent mNestedScrollingParent;
    int mNextClusterForwardId;
    private int mNextFocusDownId;
    int mNextFocusForwardId;
    private int mNextFocusLeftId;
    private int mNextFocusRightId;
    private int mNextFocusUpId;
    int mOldHeightMeasureSpec;
    int mOldWidthMeasureSpec;
    ViewOutlineProvider mOutlineProvider;
    private int mOverScrollMode;
    ViewOverlay mOverlay;
    /* access modifiers changed from: protected */
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    public int mPaddingBottom;
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int mPaddingLeft;
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    protected int mPaddingRight;
    /* access modifiers changed from: protected */
    @ViewDebug.ExportedProperty(category = "padding")
    @UnsupportedAppUsage
    public int mPaddingTop;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected ViewParent mParent;
    private CheckForLongPress mPendingCheckForLongPress;
    @UnsupportedAppUsage
    private CheckForTap mPendingCheckForTap;
    private PerformClick mPerformClick;
    private PointerIcon mPointerIcon;
    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "FORCE_LAYOUT"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LAYOUT_REQUIRED"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "DRAWING_CACHE_INVALID", outputIf = false), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "DRAWN", outputIf = true), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "NOT_DRAWN", outputIf = false), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "DIRTY")}, formatToHexString = true)
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123769414)
    public int mPrivateFlags;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768943)
    int mPrivateFlags2;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 129147060)
    int mPrivateFlags3;
    @UnsupportedAppUsage
    boolean mRecreateDisplayList;
    @UnsupportedAppUsage
    final RenderNode mRenderNode;
    @UnsupportedAppUsage
    private final Resources mResources;
    @ViewDebug.ExportedProperty(category = "layout")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected int mRight;
    private boolean mRightPaddingDefined;
    private RoundScrollbarRenderer mRoundScrollbarRenderer;
    private HandlerActionQueue mRunQueue;
    @UnsupportedAppUsage
    private ScrollabilityCache mScrollCache;
    private Drawable mScrollIndicatorDrawable;
    /* access modifiers changed from: protected */
    @ViewDebug.ExportedProperty(category = "scrolling")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    public int mScrollX;
    /* access modifiers changed from: protected */
    @ViewDebug.ExportedProperty(category = "scrolling")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    public int mScrollY;
    private SendViewScrolledAccessibilityEvent mSendViewScrolledAccessibilityEvent;
    private boolean mSendingHoverAccessibilityEvents;
    private int mSourceLayoutId;
    @UnsupportedAppUsage
    String mStartActivityRequestWho;
    private StateListAnimator mStateListAnimator;
    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "LOW_PROFILE"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "HIDE_NAVIGATION"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "FULLSCREEN"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "LAYOUT_STABLE"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "LAYOUT_HIDE_NAVIGATION"), @ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "LAYOUT_FULLSCREEN"), @ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "IMMERSIVE"), @ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "IMMERSIVE_STICKY"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LIGHT_STATUS_BAR"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "LIGHT_NAVIGATION_BAR"), @ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "STATUS_BAR_DISABLE_EXPAND"), @ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "STATUS_BAR_DISABLE_NOTIFICATION_ICONS"), @ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "STATUS_BAR_DISABLE_NOTIFICATION_ALERTS"), @ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "STATUS_BAR_DISABLE_NOTIFICATION_TICKER"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "STATUS_BAR_DISABLE_SYSTEM_INFO"), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "STATUS_BAR_DISABLE_HOME"), @ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "STATUS_BAR_DISABLE_BACK"), @ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "STATUS_BAR_DISABLE_CLOCK"), @ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "STATUS_BAR_DISABLE_RECENT"), @ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "STATUS_BAR_DISABLE_SEARCH"), @ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "STATUS_BAR_TRANSIENT"), @ViewDebug.FlagToString(equals = 134217728, mask = 134217728, name = "NAVIGATION_BAR_TRANSIENT"), @ViewDebug.FlagToString(equals = 268435456, mask = 268435456, name = "STATUS_BAR_UNHIDE"), @ViewDebug.FlagToString(equals = 536870912, mask = 536870912, name = "NAVIGATION_BAR_UNHIDE"), @ViewDebug.FlagToString(equals = 1073741824, mask = 1073741824, name = "STATUS_BAR_TRANSLUCENT"), @ViewDebug.FlagToString(equals = Integer.MIN_VALUE, mask = Integer.MIN_VALUE, name = "NAVIGATION_BAR_TRANSLUCENT"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "NAVIGATION_BAR_TRANSPARENT"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "STATUS_BAR_TRANSPARENT")}, formatToHexString = true)
    int mSystemUiVisibility;
    @UnsupportedAppUsage
    protected Object mTag;
    private int[] mTempNestedScrollConsumed;
    TooltipInfo mTooltipInfo;
    @ViewDebug.ExportedProperty(category = "layout")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected int mTop;
    private TouchDelegate mTouchDelegate;
    private int mTouchSlop;
    @UnsupportedAppUsage
    public TransformationInfo mTransformationInfo;
    int mTransientStateCount;
    private String mTransitionName;
    @UnsupportedAppUsage
    private Bitmap mUnscaledDrawingCache;
    private UnsetPressedState mUnsetPressedState;
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingBottom;
    @ViewDebug.ExportedProperty(category = "padding")
    int mUserPaddingEnd;
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingLeft;
    int mUserPaddingLeftInitial;
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingRight;
    int mUserPaddingRightInitial;
    @ViewDebug.ExportedProperty(category = "padding")
    int mUserPaddingStart;
    private float mVerticalScrollFactor;
    @UnsupportedAppUsage
    private int mVerticalScrollbarPosition;
    @ViewDebug.ExportedProperty(formatToHexString = true)
    @UnsupportedAppUsage(maxTargetSdk = 28)
    int mViewFlags;
    private Handler mVisibilityChangeForAutofillHandler;
    int mWindowAttachCount;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillImportance {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutofillType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawingCacheQuality {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FindViewFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusRealDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Focusable {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FocusableMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayerType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutDir {
    }

    public interface OnApplyWindowInsetsListener {
        WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets);
    }

    public interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    public interface OnCapturedPointerListener {
        boolean onCapturedPointer(View view, MotionEvent motionEvent);
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnContextClickListener {
        boolean onContextClick(View view);
    }

    public interface OnCreateContextMenuListener {
        void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);
    }

    public interface OnDragListener {
        boolean onDrag(View view, DragEvent dragEvent);
    }

    public interface OnFocusChangeListener {
        void onFocusChange(View view, boolean z);
    }

    public interface OnGenericMotionListener {
        boolean onGenericMotion(View view, MotionEvent motionEvent);
    }

    public interface OnHoverListener {
        boolean onHover(View view, MotionEvent motionEvent);
    }

    public interface OnKeyListener {
        boolean onKey(View view, int i, KeyEvent keyEvent);
    }

    public interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
    }

    public interface OnLongClickListener {
        boolean onLongClick(View view);
    }

    public interface OnScrollChangeListener {
        void onScrollChange(View view, int i, int i2, int i3, int i4);
    }

    public interface OnSystemUiVisibilityChangeListener {
        void onSystemUiVisibilityChange(int i);
    }

    public interface OnTouchListener {
        boolean onTouch(View view, MotionEvent motionEvent);
    }

    public interface OnUnhandledKeyEventListener {
        boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ResolvedLayoutDir {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollBarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextAlignment {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewStructureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<View> {
        private int mAccessibilityFocusedId;
        private int mAccessibilityHeadingId;
        private int mAccessibilityLiveRegionId;
        private int mAccessibilityPaneTitleId;
        private int mAccessibilityTraversalAfterId;
        private int mAccessibilityTraversalBeforeId;
        private int mActivatedId;
        private int mAlphaId;
        private int mAutofillHintsId;
        private int mBackgroundId;
        private int mBackgroundTintId;
        private int mBackgroundTintModeId;
        private int mBaselineId;
        private int mClickableId;
        private int mContentDescriptionId;
        private int mContextClickableId;
        private int mDefaultFocusHighlightEnabledId;
        private int mDrawingCacheQualityId;
        private int mDuplicateParentStateId;
        private int mElevationId;
        private int mEnabledId;
        private int mFadingEdgeLengthId;
        private int mFilterTouchesWhenObscuredId;
        private int mFitsSystemWindowsId;
        private int mFocusableId;
        private int mFocusableInTouchModeId;
        private int mFocusedByDefaultId;
        private int mFocusedId;
        private int mForceDarkAllowedId;
        private int mForegroundGravityId;
        private int mForegroundId;
        private int mForegroundTintId;
        private int mForegroundTintModeId;
        private int mHapticFeedbackEnabledId;
        private int mIdId;
        private int mImportantForAccessibilityId;
        private int mImportantForAutofillId;
        private int mIsScrollContainerId;
        private int mKeepScreenOnId;
        private int mKeyboardNavigationClusterId;
        private int mLabelForId;
        private int mLayerTypeId;
        private int mLayoutDirectionId;
        private int mLongClickableId;
        private int mMinHeightId;
        private int mMinWidthId;
        private int mNestedScrollingEnabledId;
        private int mNextClusterForwardId;
        private int mNextFocusDownId;
        private int mNextFocusForwardId;
        private int mNextFocusLeftId;
        private int mNextFocusRightId;
        private int mNextFocusUpId;
        private int mOutlineAmbientShadowColorId;
        private int mOutlineProviderId;
        private int mOutlineSpotShadowColorId;
        private int mOverScrollModeId;
        private int mPaddingBottomId;
        private int mPaddingLeftId;
        private int mPaddingRightId;
        private int mPaddingTopId;
        private int mPointerIconId;
        private int mPressedId;
        private boolean mPropertiesMapped = false;
        private int mRawLayoutDirectionId;
        private int mRawTextAlignmentId;
        private int mRawTextDirectionId;
        private int mRequiresFadingEdgeId;
        private int mRotationId;
        private int mRotationXId;
        private int mRotationYId;
        private int mSaveEnabledId;
        private int mScaleXId;
        private int mScaleYId;
        private int mScreenReaderFocusableId;
        private int mScrollIndicatorsId;
        private int mScrollXId;
        private int mScrollYId;
        private int mScrollbarDefaultDelayBeforeFadeId;
        private int mScrollbarFadeDurationId;
        private int mScrollbarSizeId;
        private int mScrollbarStyleId;
        private int mSelectedId;
        private int mSolidColorId;
        private int mSoundEffectsEnabledId;
        private int mStateListAnimatorId;
        private int mTagId;
        private int mTextAlignmentId;
        private int mTextDirectionId;
        private int mTooltipTextId;
        private int mTransformPivotXId;
        private int mTransformPivotYId;
        private int mTransitionNameId;
        private int mTranslationXId;
        private int mTranslationYId;
        private int mTranslationZId;
        private int mVisibilityId;

        public void mapProperties(PropertyMapper propertyMapper) {
            PropertyMapper propertyMapper2 = propertyMapper;
            this.mAccessibilityFocusedId = propertyMapper2.mapBoolean("accessibilityFocused", 0);
            this.mAccessibilityHeadingId = propertyMapper2.mapBoolean("accessibilityHeading", 16844160);
            SparseArray<String> accessibilityLiveRegionEnumMapping = new SparseArray<>();
            accessibilityLiveRegionEnumMapping.put(0, "none");
            accessibilityLiveRegionEnumMapping.put(1, "polite");
            accessibilityLiveRegionEnumMapping.put(2, "assertive");
            Objects.requireNonNull(accessibilityLiveRegionEnumMapping);
            this.mAccessibilityLiveRegionId = propertyMapper2.mapIntEnum("accessibilityLiveRegion", 16843758, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mAccessibilityPaneTitleId = propertyMapper2.mapObject("accessibilityPaneTitle", 16844156);
            this.mAccessibilityTraversalAfterId = propertyMapper2.mapResourceId("accessibilityTraversalAfter", 16843986);
            this.mAccessibilityTraversalBeforeId = propertyMapper2.mapResourceId("accessibilityTraversalBefore", 16843985);
            this.mActivatedId = propertyMapper2.mapBoolean("activated", 0);
            this.mAlphaId = propertyMapper2.mapFloat("alpha", 16843551);
            this.mAutofillHintsId = propertyMapper2.mapObject("autofillHints", 16844118);
            this.mBackgroundId = propertyMapper2.mapObject("background", 16842964);
            this.mBackgroundTintId = propertyMapper2.mapObject("backgroundTint", 16843883);
            this.mBackgroundTintModeId = propertyMapper2.mapObject("backgroundTintMode", 16843884);
            this.mBaselineId = propertyMapper2.mapInt("baseline", 16843548);
            this.mClickableId = propertyMapper2.mapBoolean("clickable", 16842981);
            this.mContentDescriptionId = propertyMapper2.mapObject("contentDescription", 16843379);
            this.mContextClickableId = propertyMapper2.mapBoolean("contextClickable", 16844007);
            this.mDefaultFocusHighlightEnabledId = propertyMapper2.mapBoolean("defaultFocusHighlightEnabled", 16844130);
            SparseArray<String> drawingCacheQualityEnumMapping = new SparseArray<>();
            drawingCacheQualityEnumMapping.put(0, "auto");
            drawingCacheQualityEnumMapping.put(524288, "low");
            drawingCacheQualityEnumMapping.put(1048576, "high");
            Objects.requireNonNull(drawingCacheQualityEnumMapping);
            this.mDrawingCacheQualityId = propertyMapper2.mapIntEnum("drawingCacheQuality", 16842984, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mDuplicateParentStateId = propertyMapper2.mapBoolean("duplicateParentState", 16842985);
            this.mElevationId = propertyMapper2.mapFloat("elevation", 16843840);
            this.mEnabledId = propertyMapper2.mapBoolean("enabled", 16842766);
            this.mFadingEdgeLengthId = propertyMapper2.mapInt("fadingEdgeLength", 16842976);
            this.mFilterTouchesWhenObscuredId = propertyMapper2.mapBoolean("filterTouchesWhenObscured", 16843460);
            this.mFitsSystemWindowsId = propertyMapper2.mapBoolean("fitsSystemWindows", 16842973);
            SparseArray<String> focusableEnumMapping = new SparseArray<>();
            focusableEnumMapping.put(0, "false");
            focusableEnumMapping.put(1, "true");
            focusableEnumMapping.put(16, "auto");
            Objects.requireNonNull(focusableEnumMapping);
            this.mFocusableId = propertyMapper2.mapIntEnum("focusable", 16842970, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mFocusableInTouchModeId = propertyMapper2.mapBoolean("focusableInTouchMode", 16842971);
            this.mFocusedId = propertyMapper2.mapBoolean("focused", 0);
            this.mFocusedByDefaultId = propertyMapper2.mapBoolean("focusedByDefault", 16844100);
            this.mForceDarkAllowedId = propertyMapper2.mapBoolean("forceDarkAllowed", 16844172);
            this.mForegroundId = propertyMapper2.mapObject("foreground", 16843017);
            this.mForegroundGravityId = propertyMapper2.mapGravity("foregroundGravity", 16843264);
            this.mForegroundTintId = propertyMapper2.mapObject("foregroundTint", 16843885);
            this.mForegroundTintModeId = propertyMapper2.mapObject("foregroundTintMode", 16843886);
            this.mHapticFeedbackEnabledId = propertyMapper2.mapBoolean("hapticFeedbackEnabled", 16843358);
            this.mIdId = propertyMapper2.mapResourceId("id", 16842960);
            SparseArray<String> importantForAccessibilityEnumMapping = new SparseArray<>();
            importantForAccessibilityEnumMapping.put(0, "auto");
            importantForAccessibilityEnumMapping.put(1, "yes");
            importantForAccessibilityEnumMapping.put(2, "no");
            importantForAccessibilityEnumMapping.put(4, "noHideDescendants");
            Objects.requireNonNull(importantForAccessibilityEnumMapping);
            this.mImportantForAccessibilityId = propertyMapper2.mapIntEnum("importantForAccessibility", 16843690, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            SparseArray<String> importantForAutofillEnumMapping = new SparseArray<>();
            importantForAutofillEnumMapping.put(0, "auto");
            importantForAutofillEnumMapping.put(1, "yes");
            importantForAutofillEnumMapping.put(2, "no");
            importantForAutofillEnumMapping.put(4, "yesExcludeDescendants");
            importantForAutofillEnumMapping.put(8, "noExcludeDescendants");
            Objects.requireNonNull(importantForAutofillEnumMapping);
            this.mImportantForAutofillId = propertyMapper2.mapIntEnum("importantForAutofill", 16844120, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mIsScrollContainerId = propertyMapper2.mapBoolean("isScrollContainer", 16843342);
            this.mKeepScreenOnId = propertyMapper2.mapBoolean("keepScreenOn", 16843286);
            this.mKeyboardNavigationClusterId = propertyMapper2.mapBoolean("keyboardNavigationCluster", 16844096);
            this.mLabelForId = propertyMapper2.mapResourceId("labelFor", 16843718);
            SparseArray<String> layerTypeEnumMapping = new SparseArray<>();
            layerTypeEnumMapping.put(0, "none");
            layerTypeEnumMapping.put(1, "software");
            layerTypeEnumMapping.put(2, "hardware");
            Objects.requireNonNull(layerTypeEnumMapping);
            this.mLayerTypeId = propertyMapper2.mapIntEnum("layerType", 16843604, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            SparseArray<String> layoutDirectionEnumMapping = new SparseArray<>();
            layoutDirectionEnumMapping.put(0, "ltr");
            layoutDirectionEnumMapping.put(1, "rtl");
            Objects.requireNonNull(layoutDirectionEnumMapping);
            this.mLayoutDirectionId = propertyMapper2.mapIntEnum("layoutDirection", 16843698, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mLongClickableId = propertyMapper2.mapBoolean("longClickable", 16842982);
            this.mMinHeightId = propertyMapper2.mapInt("minHeight", 16843072);
            this.mMinWidthId = propertyMapper2.mapInt("minWidth", 16843071);
            this.mNestedScrollingEnabledId = propertyMapper2.mapBoolean("nestedScrollingEnabled", 16843830);
            this.mNextClusterForwardId = propertyMapper2.mapResourceId("nextClusterForward", 16844098);
            this.mNextFocusDownId = propertyMapper2.mapResourceId("nextFocusDown", 16842980);
            this.mNextFocusForwardId = propertyMapper2.mapResourceId("nextFocusForward", 16843580);
            this.mNextFocusLeftId = propertyMapper2.mapResourceId("nextFocusLeft", 16842977);
            this.mNextFocusRightId = propertyMapper2.mapResourceId("nextFocusRight", 16842978);
            this.mNextFocusUpId = propertyMapper2.mapResourceId("nextFocusUp", 16842979);
            this.mOutlineAmbientShadowColorId = propertyMapper2.mapColor("outlineAmbientShadowColor", 16844162);
            this.mOutlineProviderId = propertyMapper2.mapObject("outlineProvider", 16843960);
            this.mOutlineSpotShadowColorId = propertyMapper2.mapColor("outlineSpotShadowColor", 16844161);
            SparseArray<String> overScrollModeEnumMapping = new SparseArray<>();
            overScrollModeEnumMapping.put(0, "always");
            overScrollModeEnumMapping.put(1, "ifContentScrolls");
            overScrollModeEnumMapping.put(2, "never");
            Objects.requireNonNull(overScrollModeEnumMapping);
            this.mOverScrollModeId = propertyMapper2.mapIntEnum("overScrollMode", 16843457, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mPaddingBottomId = propertyMapper2.mapInt("paddingBottom", 16842969);
            this.mPaddingLeftId = propertyMapper2.mapInt("paddingLeft", 16842966);
            this.mPaddingRightId = propertyMapper2.mapInt("paddingRight", 16842968);
            this.mPaddingTopId = propertyMapper2.mapInt("paddingTop", 16842967);
            this.mPointerIconId = propertyMapper2.mapObject("pointerIcon", 16844041);
            this.mPressedId = propertyMapper2.mapBoolean("pressed", 0);
            SparseArray<String> rawLayoutDirectionEnumMapping = new SparseArray<>();
            rawLayoutDirectionEnumMapping.put(0, "ltr");
            rawLayoutDirectionEnumMapping.put(1, "rtl");
            rawLayoutDirectionEnumMapping.put(2, "inherit");
            rawLayoutDirectionEnumMapping.put(3, UserDictionary.Words.LOCALE);
            Objects.requireNonNull(rawLayoutDirectionEnumMapping);
            this.mRawLayoutDirectionId = propertyMapper2.mapIntEnum("rawLayoutDirection", 0, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            SparseArray<String> rawTextAlignmentEnumMapping = new SparseArray<>();
            rawTextAlignmentEnumMapping.put(0, "inherit");
            rawTextAlignmentEnumMapping.put(1, "gravity");
            rawTextAlignmentEnumMapping.put(2, "textStart");
            rawTextAlignmentEnumMapping.put(3, "textEnd");
            rawTextAlignmentEnumMapping.put(4, "center");
            rawTextAlignmentEnumMapping.put(5, "viewStart");
            rawTextAlignmentEnumMapping.put(6, "viewEnd");
            Objects.requireNonNull(rawTextAlignmentEnumMapping);
            this.mRawTextAlignmentId = propertyMapper2.mapIntEnum("rawTextAlignment", 0, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            SparseArray<String> rawTextDirectionEnumMapping = new SparseArray<>();
            rawTextDirectionEnumMapping.put(0, "inherit");
            rawTextDirectionEnumMapping.put(1, "firstStrong");
            rawTextDirectionEnumMapping.put(2, "anyRtl");
            rawTextDirectionEnumMapping.put(3, "ltr");
            rawTextDirectionEnumMapping.put(4, "rtl");
            rawTextDirectionEnumMapping.put(5, UserDictionary.Words.LOCALE);
            rawTextDirectionEnumMapping.put(6, "firstStrongLtr");
            rawTextDirectionEnumMapping.put(7, "firstStrongRtl");
            Objects.requireNonNull(rawTextDirectionEnumMapping);
            this.mRawTextDirectionId = propertyMapper2.mapIntEnum("rawTextDirection", 0, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            IntFlagMapping requiresFadingEdgeFlagMapping = new IntFlagMapping();
            requiresFadingEdgeFlagMapping.add(4096, 4096, Slice.HINT_HORIZONTAL);
            SparseArray<String> sparseArray = accessibilityLiveRegionEnumMapping;
            requiresFadingEdgeFlagMapping.add(12288, 0, "none");
            requiresFadingEdgeFlagMapping.add(8192, 8192, "vertical");
            Objects.requireNonNull(requiresFadingEdgeFlagMapping);
            this.mRequiresFadingEdgeId = propertyMapper2.mapIntFlag("requiresFadingEdge", 16843685, new IntFunction() {
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            this.mRotationId = propertyMapper2.mapFloat("rotation", 16843558);
            this.mRotationXId = propertyMapper2.mapFloat("rotationX", 16843559);
            this.mRotationYId = propertyMapper2.mapFloat("rotationY", 16843560);
            this.mSaveEnabledId = propertyMapper2.mapBoolean("saveEnabled", 16842983);
            this.mScaleXId = propertyMapper2.mapFloat("scaleX", 16843556);
            this.mScaleYId = propertyMapper2.mapFloat("scaleY", 16843557);
            this.mScreenReaderFocusableId = propertyMapper2.mapBoolean("screenReaderFocusable", 16844148);
            IntFlagMapping scrollIndicatorsFlagMapping = new IntFlagMapping();
            scrollIndicatorsFlagMapping.add(2, 2, "bottom");
            scrollIndicatorsFlagMapping.add(32, 32, "end");
            scrollIndicatorsFlagMapping.add(4, 4, "left");
            SparseArray<String> sparseArray2 = drawingCacheQualityEnumMapping;
            scrollIndicatorsFlagMapping.add(-1, 0, "none");
            scrollIndicatorsFlagMapping.add(8, 8, "right");
            scrollIndicatorsFlagMapping.add(16, 16, Telephony.BaseMmsColumns.START);
            scrollIndicatorsFlagMapping.add(1, 1, "top");
            Objects.requireNonNull(scrollIndicatorsFlagMapping);
            this.mScrollIndicatorsId = propertyMapper2.mapIntFlag("scrollIndicators", 16844006, new IntFunction() {
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            this.mScrollXId = propertyMapper2.mapInt("scrollX", 16842962);
            this.mScrollYId = propertyMapper2.mapInt("scrollY", 16842963);
            this.mScrollbarDefaultDelayBeforeFadeId = propertyMapper2.mapInt("scrollbarDefaultDelayBeforeFade", 16843433);
            this.mScrollbarFadeDurationId = propertyMapper2.mapInt("scrollbarFadeDuration", 16843432);
            this.mScrollbarSizeId = propertyMapper2.mapInt("scrollbarSize", 16842851);
            SparseArray<String> scrollbarStyleEnumMapping = new SparseArray<>();
            scrollbarStyleEnumMapping.put(0, "insideOverlay");
            scrollbarStyleEnumMapping.put(16777216, "insideInset");
            scrollbarStyleEnumMapping.put(33554432, "outsideOverlay");
            scrollbarStyleEnumMapping.put(50331648, "outsideInset");
            Objects.requireNonNull(scrollbarStyleEnumMapping);
            IntFlagMapping intFlagMapping = scrollIndicatorsFlagMapping;
            this.mScrollbarStyleId = propertyMapper2.mapIntEnum("scrollbarStyle", 16842879, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mSelectedId = propertyMapper2.mapBoolean(Slice.HINT_SELECTED, 0);
            this.mSolidColorId = propertyMapper2.mapColor("solidColor", 16843594);
            this.mSoundEffectsEnabledId = propertyMapper2.mapBoolean("soundEffectsEnabled", 16843285);
            this.mStateListAnimatorId = propertyMapper2.mapObject("stateListAnimator", 16843848);
            this.mTagId = propertyMapper2.mapObject(DropBoxManager.EXTRA_TAG, 16842961);
            SparseArray<String> textAlignmentEnumMapping = new SparseArray<>();
            textAlignmentEnumMapping.put(1, "gravity");
            textAlignmentEnumMapping.put(2, "textStart");
            textAlignmentEnumMapping.put(3, "textEnd");
            textAlignmentEnumMapping.put(4, "center");
            textAlignmentEnumMapping.put(5, "viewStart");
            textAlignmentEnumMapping.put(6, "viewEnd");
            Objects.requireNonNull(textAlignmentEnumMapping);
            SparseArray<String> sparseArray3 = textAlignmentEnumMapping;
            this.mTextAlignmentId = propertyMapper2.mapIntEnum("textAlignment", 16843697, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            SparseArray<String> textDirectionEnumMapping = new SparseArray<>();
            textDirectionEnumMapping.put(1, "firstStrong");
            textDirectionEnumMapping.put(2, "anyRtl");
            textDirectionEnumMapping.put(3, "ltr");
            textDirectionEnumMapping.put(4, "rtl");
            textDirectionEnumMapping.put(5, UserDictionary.Words.LOCALE);
            textDirectionEnumMapping.put(6, "firstStrongLtr");
            textDirectionEnumMapping.put(7, "firstStrongRtl");
            Objects.requireNonNull(textDirectionEnumMapping);
            SparseArray<String> sparseArray4 = textDirectionEnumMapping;
            this.mTextDirectionId = propertyMapper2.mapIntEnum("textDirection", 0, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mTooltipTextId = propertyMapper2.mapObject("tooltipText", 16844084);
            this.mTransformPivotXId = propertyMapper2.mapFloat("transformPivotX", 16843552);
            this.mTransformPivotYId = propertyMapper2.mapFloat("transformPivotY", 16843553);
            this.mTransitionNameId = propertyMapper2.mapObject("transitionName", 16843776);
            this.mTranslationXId = propertyMapper2.mapFloat("translationX", 16843554);
            this.mTranslationYId = propertyMapper2.mapFloat("translationY", 16843555);
            this.mTranslationZId = propertyMapper2.mapFloat("translationZ", 16843770);
            SparseArray<String> visibilityEnumMapping = new SparseArray<>();
            visibilityEnumMapping.put(0, CalendarContract.CalendarColumns.VISIBLE);
            visibilityEnumMapping.put(4, "invisible");
            visibilityEnumMapping.put(8, "gone");
            Objects.requireNonNull(visibilityEnumMapping);
            SparseArray<String> sparseArray5 = visibilityEnumMapping;
            this.mVisibilityId = propertyMapper2.mapIntEnum(Downloads.Impl.COLUMN_VISIBILITY, 16842972, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mPropertiesMapped = true;
        }

        public void readProperties(View node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readBoolean(this.mAccessibilityFocusedId, node.isAccessibilityFocused());
                propertyReader.readBoolean(this.mAccessibilityHeadingId, node.isAccessibilityHeading());
                propertyReader.readIntEnum(this.mAccessibilityLiveRegionId, node.getAccessibilityLiveRegion());
                propertyReader.readObject(this.mAccessibilityPaneTitleId, node.getAccessibilityPaneTitle());
                propertyReader.readResourceId(this.mAccessibilityTraversalAfterId, node.getAccessibilityTraversalAfter());
                propertyReader.readResourceId(this.mAccessibilityTraversalBeforeId, node.getAccessibilityTraversalBefore());
                propertyReader.readBoolean(this.mActivatedId, node.isActivated());
                propertyReader.readFloat(this.mAlphaId, node.getAlpha());
                propertyReader.readObject(this.mAutofillHintsId, node.getAutofillHints());
                propertyReader.readObject(this.mBackgroundId, node.getBackground());
                propertyReader.readObject(this.mBackgroundTintId, node.getBackgroundTintList());
                propertyReader.readObject(this.mBackgroundTintModeId, node.getBackgroundTintMode());
                propertyReader.readInt(this.mBaselineId, node.getBaseline());
                propertyReader.readBoolean(this.mClickableId, node.isClickable());
                propertyReader.readObject(this.mContentDescriptionId, node.getContentDescription());
                propertyReader.readBoolean(this.mContextClickableId, node.isContextClickable());
                propertyReader.readBoolean(this.mDefaultFocusHighlightEnabledId, node.getDefaultFocusHighlightEnabled());
                propertyReader.readIntEnum(this.mDrawingCacheQualityId, node.getDrawingCacheQuality());
                propertyReader.readBoolean(this.mDuplicateParentStateId, node.isDuplicateParentStateEnabled());
                propertyReader.readFloat(this.mElevationId, node.getElevation());
                propertyReader.readBoolean(this.mEnabledId, node.isEnabled());
                propertyReader.readInt(this.mFadingEdgeLengthId, node.getFadingEdgeLength());
                propertyReader.readBoolean(this.mFilterTouchesWhenObscuredId, node.getFilterTouchesWhenObscured());
                propertyReader.readBoolean(this.mFitsSystemWindowsId, node.getFitsSystemWindows());
                propertyReader.readIntEnum(this.mFocusableId, node.getFocusable());
                propertyReader.readBoolean(this.mFocusableInTouchModeId, node.isFocusableInTouchMode());
                propertyReader.readBoolean(this.mFocusedId, node.isFocused());
                propertyReader.readBoolean(this.mFocusedByDefaultId, node.isFocusedByDefault());
                propertyReader.readBoolean(this.mForceDarkAllowedId, node.isForceDarkAllowed());
                propertyReader.readObject(this.mForegroundId, node.getForeground());
                propertyReader.readGravity(this.mForegroundGravityId, node.getForegroundGravity());
                propertyReader.readObject(this.mForegroundTintId, node.getForegroundTintList());
                propertyReader.readObject(this.mForegroundTintModeId, node.getForegroundTintMode());
                propertyReader.readBoolean(this.mHapticFeedbackEnabledId, node.isHapticFeedbackEnabled());
                propertyReader.readResourceId(this.mIdId, node.getId());
                propertyReader.readIntEnum(this.mImportantForAccessibilityId, node.getImportantForAccessibility());
                propertyReader.readIntEnum(this.mImportantForAutofillId, node.getImportantForAutofill());
                propertyReader.readBoolean(this.mIsScrollContainerId, node.isScrollContainer());
                propertyReader.readBoolean(this.mKeepScreenOnId, node.getKeepScreenOn());
                propertyReader.readBoolean(this.mKeyboardNavigationClusterId, node.isKeyboardNavigationCluster());
                propertyReader.readResourceId(this.mLabelForId, node.getLabelFor());
                propertyReader.readIntEnum(this.mLayerTypeId, node.getLayerType());
                propertyReader.readIntEnum(this.mLayoutDirectionId, node.getLayoutDirection());
                propertyReader.readBoolean(this.mLongClickableId, node.isLongClickable());
                propertyReader.readInt(this.mMinHeightId, node.getMinimumHeight());
                propertyReader.readInt(this.mMinWidthId, node.getMinimumWidth());
                propertyReader.readBoolean(this.mNestedScrollingEnabledId, node.isNestedScrollingEnabled());
                propertyReader.readResourceId(this.mNextClusterForwardId, node.getNextClusterForwardId());
                propertyReader.readResourceId(this.mNextFocusDownId, node.getNextFocusDownId());
                propertyReader.readResourceId(this.mNextFocusForwardId, node.getNextFocusForwardId());
                propertyReader.readResourceId(this.mNextFocusLeftId, node.getNextFocusLeftId());
                propertyReader.readResourceId(this.mNextFocusRightId, node.getNextFocusRightId());
                propertyReader.readResourceId(this.mNextFocusUpId, node.getNextFocusUpId());
                propertyReader.readColor(this.mOutlineAmbientShadowColorId, node.getOutlineAmbientShadowColor());
                propertyReader.readObject(this.mOutlineProviderId, node.getOutlineProvider());
                propertyReader.readColor(this.mOutlineSpotShadowColorId, node.getOutlineSpotShadowColor());
                propertyReader.readIntEnum(this.mOverScrollModeId, node.getOverScrollMode());
                propertyReader.readInt(this.mPaddingBottomId, node.getPaddingBottom());
                propertyReader.readInt(this.mPaddingLeftId, node.getPaddingLeft());
                propertyReader.readInt(this.mPaddingRightId, node.getPaddingRight());
                propertyReader.readInt(this.mPaddingTopId, node.getPaddingTop());
                propertyReader.readObject(this.mPointerIconId, node.getPointerIcon());
                propertyReader.readBoolean(this.mPressedId, node.isPressed());
                propertyReader.readIntEnum(this.mRawLayoutDirectionId, node.getRawLayoutDirection());
                propertyReader.readIntEnum(this.mRawTextAlignmentId, node.getRawTextAlignment());
                propertyReader.readIntEnum(this.mRawTextDirectionId, node.getRawTextDirection());
                propertyReader.readIntFlag(this.mRequiresFadingEdgeId, node.getFadingEdge());
                propertyReader.readFloat(this.mRotationId, node.getRotation());
                propertyReader.readFloat(this.mRotationXId, node.getRotationX());
                propertyReader.readFloat(this.mRotationYId, node.getRotationY());
                propertyReader.readBoolean(this.mSaveEnabledId, node.isSaveEnabled());
                propertyReader.readFloat(this.mScaleXId, node.getScaleX());
                propertyReader.readFloat(this.mScaleYId, node.getScaleY());
                propertyReader.readBoolean(this.mScreenReaderFocusableId, node.isScreenReaderFocusable());
                propertyReader.readIntFlag(this.mScrollIndicatorsId, node.getScrollIndicators());
                propertyReader.readInt(this.mScrollXId, node.getScrollX());
                propertyReader.readInt(this.mScrollYId, node.getScrollY());
                propertyReader.readInt(this.mScrollbarDefaultDelayBeforeFadeId, node.getScrollBarDefaultDelayBeforeFade());
                propertyReader.readInt(this.mScrollbarFadeDurationId, node.getScrollBarFadeDuration());
                propertyReader.readInt(this.mScrollbarSizeId, node.getScrollBarSize());
                propertyReader.readIntEnum(this.mScrollbarStyleId, node.getScrollBarStyle());
                propertyReader.readBoolean(this.mSelectedId, node.isSelected());
                propertyReader.readColor(this.mSolidColorId, node.getSolidColor());
                propertyReader.readBoolean(this.mSoundEffectsEnabledId, node.isSoundEffectsEnabled());
                propertyReader.readObject(this.mStateListAnimatorId, node.getStateListAnimator());
                propertyReader.readObject(this.mTagId, node.getTag());
                propertyReader.readIntEnum(this.mTextAlignmentId, node.getTextAlignment());
                propertyReader.readIntEnum(this.mTextDirectionId, node.getTextDirection());
                propertyReader.readObject(this.mTooltipTextId, node.getTooltipText());
                propertyReader.readFloat(this.mTransformPivotXId, node.getPivotX());
                propertyReader.readFloat(this.mTransformPivotYId, node.getPivotY());
                propertyReader.readObject(this.mTransitionNameId, node.getTransitionName());
                propertyReader.readFloat(this.mTranslationXId, node.getTranslationX());
                propertyReader.readFloat(this.mTranslationYId, node.getTranslationY());
                propertyReader.readFloat(this.mTranslationZId, node.getTranslationZ());
                propertyReader.readIntEnum(this.mVisibilityId, node.getVisibility());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    static class TransformationInfo {
        /* access modifiers changed from: private */
        @ViewDebug.ExportedProperty
        public float mAlpha = 1.0f;
        /* access modifiers changed from: private */
        public Matrix mInverseMatrix;
        /* access modifiers changed from: private */
        public final Matrix mMatrix = new Matrix();
        float mTransitionAlpha = 1.0f;

        TransformationInfo() {
        }
    }

    static class TintInfo {
        BlendMode mBlendMode;
        boolean mHasTintList;
        boolean mHasTintMode;
        ColorStateList mTintList;

        TintInfo() {
        }
    }

    private static class ForegroundInfo {
        /* access modifiers changed from: private */
        public boolean mBoundsChanged;
        /* access modifiers changed from: private */
        public Drawable mDrawable;
        /* access modifiers changed from: private */
        public int mGravity;
        /* access modifiers changed from: private */
        public boolean mInsidePadding;
        /* access modifiers changed from: private */
        public final Rect mOverlayBounds;
        /* access modifiers changed from: private */
        public final Rect mSelfBounds;
        /* access modifiers changed from: private */
        public TintInfo mTintInfo;

        private ForegroundInfo() {
            this.mGravity = 119;
            this.mInsidePadding = true;
            this.mBoundsChanged = true;
            this.mSelfBounds = new Rect();
            this.mOverlayBounds = new Rect();
        }
    }

    static class ListenerInfo {
        OnApplyWindowInsetsListener mOnApplyWindowInsetsListener;
        /* access modifiers changed from: private */
        public CopyOnWriteArrayList<OnAttachStateChangeListener> mOnAttachStateChangeListeners;
        OnCapturedPointerListener mOnCapturedPointerListener;
        @UnsupportedAppUsage
        public OnClickListener mOnClickListener;
        protected OnContextClickListener mOnContextClickListener;
        @UnsupportedAppUsage
        protected OnCreateContextMenuListener mOnCreateContextMenuListener;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public OnDragListener mOnDragListener;
        @UnsupportedAppUsage
        protected OnFocusChangeListener mOnFocusChangeListener;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public OnGenericMotionListener mOnGenericMotionListener;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public OnHoverListener mOnHoverListener;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public OnKeyListener mOnKeyListener;
        /* access modifiers changed from: private */
        public ArrayList<OnLayoutChangeListener> mOnLayoutChangeListeners;
        @UnsupportedAppUsage
        protected OnLongClickListener mOnLongClickListener;
        protected OnScrollChangeListener mOnScrollChangeListener;
        /* access modifiers changed from: private */
        public OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener;
        /* access modifiers changed from: private */
        @UnsupportedAppUsage
        public OnTouchListener mOnTouchListener;
        public RenderNode.PositionUpdateListener mPositionUpdateListener;
        /* access modifiers changed from: private */
        public List<Rect> mSystemGestureExclusionRects;
        /* access modifiers changed from: private */
        public ArrayList<OnUnhandledKeyEventListener> mUnhandledKeyListeners;
        /* access modifiers changed from: private */
        public WindowInsetsAnimationListener mWindowInsetsAnimationListener;

        ListenerInfo() {
        }
    }

    private static class TooltipInfo {
        int mAnchorX;
        int mAnchorY;
        Runnable mHideTooltipRunnable;
        int mHoverSlop;
        Runnable mShowTooltipRunnable;
        boolean mTooltipFromLongClick;
        TooltipPopup mTooltipPopup;
        CharSequence mTooltipText;

        private TooltipInfo() {
        }

        /* access modifiers changed from: private */
        public boolean updateAnchorPos(MotionEvent event) {
            int newAnchorX = (int) event.getX();
            int newAnchorY = (int) event.getY();
            if (Math.abs(newAnchorX - this.mAnchorX) <= this.mHoverSlop && Math.abs(newAnchorY - this.mAnchorY) <= this.mHoverSlop) {
                return false;
            }
            this.mAnchorX = newAnchorX;
            this.mAnchorY = newAnchorY;
            return true;
        }

        /* access modifiers changed from: private */
        public void clearAnchorPos() {
            this.mAnchorX = Integer.MAX_VALUE;
            this.mAnchorY = Integer.MAX_VALUE;
        }
    }

    public View(Context context) {
        Resources resources = null;
        this.mCurrentAnimation = null;
        boolean z = false;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mLayerType = 0;
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mSourceLayoutId = 0;
        this.mContext = context;
        this.mResources = context != null ? context.getResources() : resources;
        this.mViewFlags = 402653200;
        this.mPrivateFlags2 = 140296;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOverScrollMode(1);
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mRenderNode = RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
        if (!sCompatibilityDone && context != null) {
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            sUseBrokenMakeMeasureSpec = targetSdkVersion <= 17;
            sIgnoreMeasureCache = targetSdkVersion < 19;
            Canvas.sCompatibilityRestore = targetSdkVersion < 23;
            Canvas.sCompatibilitySetBitmap = targetSdkVersion < 26;
            Canvas.setCompatibilityVersion(targetSdkVersion);
            sUseZeroUnspecifiedMeasureSpec = targetSdkVersion < 23;
            sAlwaysRemeasureExactly = targetSdkVersion <= 23;
            sTextureViewIgnoresDrawableSetters = targetSdkVersion <= 23;
            sPreserveMarginParamsInLayoutParamConversion = targetSdkVersion >= 24;
            sCascadedDragDrop = targetSdkVersion < 24;
            sHasFocusableExcludeAutoFocusable = targetSdkVersion < 26;
            sAutoFocusableOffUIThreadWontNotifyParents = targetSdkVersion < 26;
            sUseDefaultFocusHighlight = context.getResources().getBoolean(R.bool.config_useDefaultFocusHighlight);
            sThrowOnInvalidFloatProperties = targetSdkVersion >= 28;
            sCanFocusZeroSized = targetSdkVersion < 28;
            sAlwaysAssignFocus = targetSdkVersion < 28;
            sAcceptZeroSizeDragShadow = targetSdkVersion < 28;
            sBrokenInsetsDispatch = ViewRootImpl.sNewInsetsMode != 2 || targetSdkVersion < 29;
            sBrokenWindowBackground = targetSdkVersion < 29 ? true : z;
            sCompatibilityDone = true;
        }
    }

    public View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x00d0, code lost:
        r65 = r15;
        r10 = r46;
        r11 = r57;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x04d3, code lost:
        r30 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x06b0, code lost:
        if (r1 >= 14) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x06b4, code lost:
        r0 = r13.getInt(r2, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x06b9, code lost:
        if (r0 == 0) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x06bb, code lost:
        r10 = r10 | r0;
        r11 = r11 | 12288;
        initializeFadingEdgeInternal(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x06ec, code lost:
        r46 = r0;
        r57 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x0706, code lost:
        r46 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x0734, code lost:
        r46 = r0;
        r57 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x073c, code lost:
        r57 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x07a0, code lost:
        r15 = r65;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x0802, code lost:
        r46 = r10;
        r57 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x0806, code lost:
        r15 = r65;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x029b, code lost:
        r65 = r15;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public View(android.content.Context r80, android.util.AttributeSet r81, int r82, int r83) {
        /*
            r79 = this;
            r8 = r79
            r9 = r80
            r10 = r81
            r79.<init>(r80)
            int r0 = android.content.res.Resources.getAttributeSetSourceResId(r81)
            r8.mSourceLayoutId = r0
            int[] r0 = com.android.internal.R.styleable.View
            r11 = r82
            r12 = r83
            android.content.res.TypedArray r13 = r9.obtainStyledAttributes(r10, r0, r11, r12)
            android.content.res.Resources$Theme r0 = r80.getTheme()
            r8.retrieveExplicitStyle(r0, r10)
            int[] r3 = com.android.internal.R.styleable.View
            r1 = r79
            r2 = r80
            r4 = r81
            r5 = r13
            r6 = r82
            r7 = r83
            r1.saveAttributeDataForStyleable(r2, r3, r4, r5, r6, r7)
            boolean r0 = sDebugViewAttributes
            if (r0 == 0) goto L_0x0037
            r8.saveAttributeData(r10, r13)
        L_0x0037:
            r0 = 0
            r1 = -1
            r2 = -1
            r3 = -1
            r4 = -1
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = -1
            r14 = -1
            r15 = -1
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 1065353216(0x3f800000, float:1.0)
            r29 = 1065353216(0x3f800000, float:1.0)
            r30 = 0
            r31 = 0
            r32 = r0
            int r0 = r8.mOverScrollMode
            r33 = 0
            r34 = 0
            r35 = 0
            r36 = 0
            r37 = 0
            r38 = 0
            r39 = r0
            android.content.pm.ApplicationInfo r0 = r80.getApplicationInfo()
            r40 = r1
            int r1 = r0.targetSdkVersion
            r0 = r16 | 16
            r16 = r17 | 16
            r41 = r2
            int r2 = r13.getIndexCount()
            r42 = r3
            r46 = r0
            r43 = r7
            r56 = r14
            r17 = r15
            r57 = r16
            r14 = r19
            r15 = r20
            r47 = r21
            r48 = r22
            r54 = r23
            r55 = r24
            r51 = r25
            r52 = r26
            r53 = r27
            r49 = r28
            r50 = r29
            r7 = r32
            r44 = r37
            r45 = r38
            r0 = 0
            r16 = r4
            r4 = r39
        L_0x00b4:
            r58 = r0
            r3 = r58
            if (r3 >= r2) goto L_0x0845
            r59 = r2
            int r2 = r13.getIndex(r3)
            r0 = 109(0x6d, float:1.53E-43)
            if (r2 == r0) goto L_0x0809
            switch(r2) {
                case 8: goto L_0x07ec;
                case 9: goto L_0x07dd;
                case 10: goto L_0x07cf;
                case 11: goto L_0x07bf;
                case 12: goto L_0x07b0;
                case 13: goto L_0x07a3;
                case 14: goto L_0x0788;
                case 15: goto L_0x0774;
                case 16: goto L_0x0765;
                case 17: goto L_0x0750;
                case 18: goto L_0x0740;
                case 19: goto L_0x0721;
                case 20: goto L_0x0709;
                case 21: goto L_0x06f2;
                case 22: goto L_0x06db;
                case 23: goto L_0x06c2;
                case 24: goto L_0x06a8;
                default: goto L_0x00c7;
            }
        L_0x00c7:
            switch(r2) {
                case 26: goto L_0x0699;
                case 27: goto L_0x068a;
                case 28: goto L_0x067b;
                case 29: goto L_0x066c;
                case 30: goto L_0x0659;
                case 31: goto L_0x0642;
                case 32: goto L_0x062b;
                case 33: goto L_0x0614;
                case 34: goto L_0x05ff;
                case 35: goto L_0x05e8;
                case 36: goto L_0x05d9;
                case 37: goto L_0x05ca;
                case 38: goto L_0x05b2;
                case 39: goto L_0x059c;
                case 40: goto L_0x0587;
                case 41: goto L_0x0571;
                case 42: goto L_0x055b;
                case 43: goto L_0x0537;
                case 44: goto L_0x0528;
                default: goto L_0x00ca;
            }
        L_0x00ca:
            r10 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = 0
            switch(r2) {
                case 48: goto L_0x0519;
                case 49: goto L_0x0506;
                case 50: goto L_0x04f5;
                case 51: goto L_0x04e6;
                case 52: goto L_0x04d7;
                case 53: goto L_0x04c5;
                case 54: goto L_0x04b6;
                case 55: goto L_0x04a5;
                case 56: goto L_0x0494;
                case 57: goto L_0x0485;
                case 58: goto L_0x0476;
                case 59: goto L_0x0466;
                case 60: goto L_0x0457;
                case 61: goto L_0x0447;
                case 62: goto L_0x0436;
                case 63: goto L_0x042e;
                case 64: goto L_0x041e;
                case 65: goto L_0x0400;
                case 66: goto L_0x03e2;
                case 67: goto L_0x03bf;
                case 68: goto L_0x03a5;
                case 69: goto L_0x038b;
                case 70: goto L_0x037b;
                case 71: goto L_0x036b;
                case 72: goto L_0x035b;
                case 73: goto L_0x034c;
                case 74: goto L_0x033c;
                case 75: goto L_0x032c;
                case 76: goto L_0x0318;
                case 77: goto L_0x02f6;
                case 78: goto L_0x02ce;
                case 79: goto L_0x02ba;
                case 80: goto L_0x029f;
                case 81: goto L_0x028c;
                case 82: goto L_0x027f;
                case 83: goto L_0x0272;
                case 84: goto L_0x0255;
                case 85: goto L_0x0235;
                case 86: goto L_0x0210;
                case 87: goto L_0x0200;
                case 88: goto L_0x01f7;
                case 89: goto L_0x01e3;
                case 90: goto L_0x01d9;
                case 91: goto L_0x01c9;
                case 92: goto L_0x01c0;
                case 93: goto L_0x01b0;
                case 94: goto L_0x0138;
                case 95: goto L_0x0129;
                case 96: goto L_0x011a;
                case 97: goto L_0x010b;
                case 98: goto L_0x00fd;
                case 99: goto L_0x00f4;
                case 100: goto L_0x00ec;
                case 101: goto L_0x00e4;
                case 102: goto L_0x00d9;
                default: goto L_0x00d0;
            }
        L_0x00d0:
            r65 = r15
            r10 = r46
            r11 = r57
        L_0x00d6:
            r12 = 0
            goto L_0x0833
        L_0x00d9:
            android.graphics.RenderNode r0 = r8.mRenderNode
            r10 = 1
            boolean r10 = r13.getBoolean(r2, r10)
            r0.setForceDarkAllowed(r10)
            goto L_0x00d0
        L_0x00e4:
            int r0 = r13.getColor(r2, r10)
            r8.setOutlineAmbientShadowColor(r0)
            goto L_0x00d0
        L_0x00ec:
            int r0 = r13.getColor(r2, r10)
            r8.setOutlineSpotShadowColor(r0)
            goto L_0x00d0
        L_0x00f4:
            r10 = 0
            boolean r0 = r13.getBoolean(r2, r10)
            r8.setAccessibilityHeading(r0)
            goto L_0x00d0
        L_0x00fd:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            java.lang.String r0 = r13.getString(r2)
            r8.setAccessibilityPaneTitle(r0)
            goto L_0x00d0
        L_0x010b:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r10 = 0
            boolean r0 = r13.getBoolean(r2, r10)
            r8.setScreenReaderFocusable(r0)
            goto L_0x00d0
        L_0x011a:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            r8.setDefaultFocusHighlightEnabled(r0)
            goto L_0x00d0
        L_0x0129:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r10 = 0
            int r0 = r13.getInt(r2, r10)
            r8.setImportantForAutofill(r0)
            goto L_0x00d0
        L_0x0138:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r10 = 0
            r19 = 0
            int r0 = r13.getType(r2)
            r61 = r10
            r10 = 1
            if (r0 != r10) goto L_0x016b
            r10 = 0
            int r0 = r13.getResourceId(r2, r10)
            r10 = r0
            java.lang.CharSequence[] r0 = r13.getTextArray(r2)     // Catch:{ NotFoundException -> 0x0157 }
            r10 = r0
            goto L_0x0168
        L_0x0157:
            r0 = move-exception
            r20 = r0
            r0 = r20
            r62 = r0
            android.content.res.Resources r0 = r79.getResources()
            java.lang.String r19 = r0.getString(r10)
            r10 = r61
        L_0x0168:
            r61 = r10
            goto L_0x016f
        L_0x016b:
            java.lang.String r19 = r13.getString(r2)
        L_0x016f:
            r0 = r19
            if (r61 != 0) goto L_0x018a
            if (r0 == 0) goto L_0x0180
            java.lang.String r10 = ","
            java.lang.String[] r61 = r0.split(r10)
            r63 = r0
            r0 = r61
            goto L_0x018e
        L_0x0180:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            r63 = r0
            java.lang.String r0 = "Could not resolve autofillHints"
            r10.<init>(r0)
            throw r10
        L_0x018a:
            r63 = r0
            r0 = r61
        L_0x018e:
            int r10 = r0.length
            java.lang.String[] r10 = new java.lang.String[r10]
            int r11 = r0.length
            r19 = 0
        L_0x0194:
            r64 = r19
            r12 = r64
            if (r12 >= r11) goto L_0x01ab
            r19 = r0[r12]
            java.lang.String r19 = r19.toString()
            java.lang.String r19 = r19.trim()
            r10[r12] = r19
            int r19 = r12 + 1
            r12 = r83
            goto L_0x0194
        L_0x01ab:
            r8.setAutofillHints(r10)
            goto L_0x00d0
        L_0x01b0:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            r8.setFocusedByDefault(r0)
            goto L_0x00d0
        L_0x01c0:
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mNextClusterForwardId = r0
            goto L_0x00d0
        L_0x01c9:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            r8.setKeyboardNavigationCluster(r0)
            goto L_0x00d0
        L_0x01d9:
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r17 = r0
            goto L_0x07cc
        L_0x01e3:
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r8.mUserPaddingLeftInitial = r0
            r8.mUserPaddingRightInitial = r0
            r10 = 1
            r11 = 1
            r56 = r0
            r44 = r10
            r45 = r11
            goto L_0x07cc
        L_0x01f7:
            java.lang.CharSequence r0 = r13.getText(r2)
            r8.setTooltipText(r0)
            goto L_0x00d0
        L_0x0200:
            android.util.TypedValue r0 = r13.peekValue(r2)
            if (r0 == 0) goto L_0x00d0
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            r8.forceHasOverlappingRendering(r0)
            goto L_0x00d0
        L_0x0210:
            r10 = 0
            int r0 = r13.getResourceId(r2, r10)
            if (r0 == 0) goto L_0x0225
            android.content.res.Resources r10 = r80.getResources()
            android.view.PointerIcon r10 = android.view.PointerIcon.load(r10, r0)
            r8.setPointerIcon(r10)
            goto L_0x00d0
        L_0x0225:
            r10 = 1
            int r11 = r13.getInt(r2, r10)
            if (r11 == r10) goto L_0x0233
            android.view.PointerIcon r10 = android.view.PointerIcon.getSystemIcon(r9, r11)
            r8.setPointerIcon(r10)
        L_0x0233:
            goto L_0x00d0
        L_0x0235:
            r10 = 0
            boolean r0 = r13.getBoolean(r2, r10)
            if (r0 == 0) goto L_0x024c
            r0 = 8388608(0x800000, float:1.17549435E-38)
            r10 = r46
            r0 = r0 | r10
            r10 = 8388608(0x800000, float:1.17549435E-38)
            r11 = r57
            r10 = r10 | r11
            r46 = r0
            r57 = r10
            goto L_0x07cc
        L_0x024c:
            r10 = r46
            r11 = r57
            r65 = r15
            r12 = 0
            goto L_0x0833
        L_0x0255:
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            int r0 = r0 << 8
            r0 = r0 & 16128(0x3f00, float:2.26E-41)
            if (r0 == 0) goto L_0x029b
            int r12 = r8.mPrivateFlags3
            r12 = r12 | r0
            r8.mPrivateFlags3 = r12
            r12 = 1
            r46 = r10
            r57 = r11
            r34 = r12
            goto L_0x07cc
        L_0x0272:
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.setAccessibilityTraversalAfter(r0)
            goto L_0x029b
        L_0x027f:
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.setAccessibilityTraversalBefore(r0)
            goto L_0x029b
        L_0x028c:
            r10 = r46
            r11 = r57
            r0 = 81
            r12 = 0
            int r0 = r13.getInt(r0, r12)
            r8.setOutlineProviderFromAttribute(r0)
        L_0x029b:
            r65 = r15
            goto L_0x00d6
        L_0x029f:
            r10 = r46
            r11 = r57
            r0 = 23
            if (r1 >= r0) goto L_0x02ab
            boolean r0 = r8 instanceof android.widget.FrameLayout
            if (r0 == 0) goto L_0x029b
        L_0x02ab:
            r0 = -1
            int r0 = r13.getInt(r2, r0)
            r12 = 0
            android.graphics.BlendMode r0 = android.graphics.drawable.Drawable.parseBlendMode(r0, r12)
            r8.setForegroundTintBlendMode(r0)
            goto L_0x029b
        L_0x02ba:
            r10 = r46
            r11 = r57
            r0 = 23
            if (r1 >= r0) goto L_0x02c6
            boolean r0 = r8 instanceof android.widget.FrameLayout
            if (r0 == 0) goto L_0x029b
        L_0x02c6:
            android.content.res.ColorStateList r0 = r13.getColorStateList(r2)
            r8.setForegroundTintList(r0)
            goto L_0x029b
        L_0x02ce:
            r10 = r46
            r11 = r57
            android.view.View$TintInfo r0 = r8.mBackgroundTint
            if (r0 != 0) goto L_0x02dd
            android.view.View$TintInfo r0 = new android.view.View$TintInfo
            r0.<init>()
            r8.mBackgroundTint = r0
        L_0x02dd:
            android.view.View$TintInfo r0 = r8.mBackgroundTint
            r12 = 78
            r65 = r15
            r15 = -1
            int r12 = r13.getInt(r12, r15)
            r15 = 0
            android.graphics.BlendMode r12 = android.graphics.drawable.Drawable.parseBlendMode(r12, r15)
            r0.mBlendMode = r12
            android.view.View$TintInfo r0 = r8.mBackgroundTint
            r12 = 1
            r0.mHasTintMode = r12
            goto L_0x00d6
        L_0x02f6:
            r65 = r15
            r10 = r46
            r11 = r57
            android.view.View$TintInfo r0 = r8.mBackgroundTint
            if (r0 != 0) goto L_0x0307
            android.view.View$TintInfo r0 = new android.view.View$TintInfo
            r0.<init>()
            r8.mBackgroundTint = r0
        L_0x0307:
            android.view.View$TintInfo r0 = r8.mBackgroundTint
            r12 = 77
            android.content.res.ColorStateList r12 = r13.getColorStateList(r12)
            r0.mTintList = r12
            android.view.View$TintInfo r0 = r8.mBackgroundTint
            r12 = 1
            r0.mHasTintList = r12
            goto L_0x00d6
        L_0x0318:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getResourceId(r2, r12)
            android.animation.StateListAnimator r0 = android.animation.AnimatorInflater.loadStateListAnimator(r9, r0)
            r8.setStateListAnimator(r0)
            goto L_0x00d6
        L_0x032c:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getDimension(r2, r0)
            r12 = 1
            r55 = r0
            goto L_0x04d3
        L_0x033c:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            r8.setNestedScrollingEnabled(r0)
            goto L_0x00d6
        L_0x034c:
            r65 = r15
            r10 = r46
            r11 = r57
            java.lang.String r0 = r13.getString(r2)
            r8.setTransitionName(r0)
            goto L_0x00d6
        L_0x035b:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getDimension(r2, r0)
            r12 = 1
            r54 = r0
            goto L_0x04d3
        L_0x036b:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            r8.setAccessibilityLiveRegion(r0)
            goto L_0x00d6
        L_0x037b:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.setLabelFor(r0)
            goto L_0x00d6
        L_0x038b:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            int r6 = r13.getDimensionPixelSize(r2, r0)
            if (r6 == r0) goto L_0x039c
            r60 = 1
            goto L_0x039e
        L_0x039c:
            r60 = 0
        L_0x039e:
            r0 = r60
            r36 = r0
            goto L_0x0706
        L_0x03a5:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            int r5 = r13.getDimensionPixelSize(r2, r0)
            if (r5 == r0) goto L_0x03b6
            r60 = 1
            goto L_0x03b8
        L_0x03b6:
            r60 = 0
        L_0x03b8:
            r0 = r60
            r35 = r0
            goto L_0x0706
        L_0x03bf:
            r65 = r15
            r10 = r46
            r11 = r57
            int r0 = r8.mPrivateFlags2
            r0 = r0 & -61
            r8.mPrivateFlags2 = r0
            r0 = -1
            int r12 = r13.getInt(r2, r0)
            if (r12 == r0) goto L_0x03d7
            int[] r0 = LAYOUT_DIRECTION_FLAGS
            r0 = r0[r12]
            goto L_0x03d8
        L_0x03d7:
            r0 = 2
        L_0x03d8:
            int r15 = r8.mPrivateFlags2
            int r19 = r0 << 2
            r15 = r15 | r19
            r8.mPrivateFlags2 = r15
            goto L_0x00d6
        L_0x03e2:
            r65 = r15
            r10 = r46
            r11 = r57
            int r0 = r8.mPrivateFlags2
            r12 = -57345(0xffffffffffff1fff, float:NaN)
            r0 = r0 & r12
            r8.mPrivateFlags2 = r0
            r0 = 1
            int r0 = r13.getInt(r2, r0)
            int r12 = r8.mPrivateFlags2
            int[] r15 = PFLAG2_TEXT_ALIGNMENT_FLAGS
            r15 = r15[r0]
            r12 = r12 | r15
            r8.mPrivateFlags2 = r12
            goto L_0x00d6
        L_0x0400:
            r65 = r15
            r10 = r46
            r11 = r57
            int r0 = r8.mPrivateFlags2
            r0 = r0 & -449(0xfffffffffffffe3f, float:NaN)
            r8.mPrivateFlags2 = r0
            r0 = -1
            int r12 = r13.getInt(r2, r0)
            if (r12 == r0) goto L_0x00d6
            int r0 = r8.mPrivateFlags2
            int[] r15 = PFLAG2_TEXT_DIRECTION_FLAGS
            r15 = r15[r12]
            r0 = r0 | r15
            r8.mPrivateFlags2 = r0
            goto L_0x00d6
        L_0x041e:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            r8.setImportantForAccessibility(r0)
            goto L_0x0833
        L_0x042e:
            r65 = r15
            r10 = r46
            r11 = r57
            goto L_0x06b4
        L_0x0436:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            r15 = 0
            r8.setLayerType(r0, r15)
            goto L_0x0833
        L_0x0447:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mNextFocusForwardId = r0
            goto L_0x0833
        L_0x0457:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            r8.mVerticalScrollbarPosition = r0
            goto L_0x00d6
        L_0x0466:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getFloat(r2, r0)
            r12 = 1
            r53 = r0
            goto L_0x04d3
        L_0x0476:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getFloat(r2, r0)
            r12 = 1
            r52 = r0
            goto L_0x04d3
        L_0x0485:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getFloat(r2, r0)
            r12 = 1
            r51 = r0
            goto L_0x04d3
        L_0x0494:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1065353216(0x3f800000, float:1.0)
            float r0 = r13.getFloat(r2, r0)
            r12 = 1
            r50 = r0
            goto L_0x04d3
        L_0x04a5:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1065353216(0x3f800000, float:1.0)
            float r0 = r13.getFloat(r2, r0)
            r12 = 1
            r49 = r0
            goto L_0x04d3
        L_0x04b6:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getDimension(r2, r0)
            r12 = 1
            r48 = r0
            goto L_0x04d3
        L_0x04c5:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getDimension(r2, r0)
            r12 = 1
            r47 = r0
        L_0x04d3:
            r30 = r12
            goto L_0x07cc
        L_0x04d7:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getDimension(r2, r0)
            r8.setPivotY(r0)
            goto L_0x00d6
        L_0x04e6:
            r65 = r15
            r10 = r46
            r11 = r57
            float r0 = r13.getDimension(r2, r0)
            r8.setPivotX(r0)
            goto L_0x00d6
        L_0x04f5:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1065353216(0x3f800000, float:1.0)
            float r0 = r13.getFloat(r2, r0)
            r8.setAlpha(r0)
            goto L_0x00d6
        L_0x0506:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x00d6
            r0 = r10 | 1024(0x400, float:1.435E-42)
            r10 = r11 | 1024(0x400, float:1.435E-42)
            goto L_0x0734
        L_0x0519:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1
            int r0 = r13.getInt(r2, r0)
            r4 = r0
            goto L_0x07cc
        L_0x0528:
            r65 = r15
            r10 = r46
            r11 = r57
            java.lang.String r0 = r13.getString(r2)
            r8.setContentDescription(r0)
            goto L_0x00d6
        L_0x0537:
            r65 = r15
            r10 = r46
            r11 = r57
            boolean r0 = r80.isRestricted()
            if (r0 != 0) goto L_0x0553
            java.lang.String r0 = r13.getString(r2)
            if (r0 == 0) goto L_0x00d6
            android.view.View$DeclaredOnClickListener r12 = new android.view.View$DeclaredOnClickListener
            r12.<init>(r8, r0)
            r8.setOnClickListener(r12)
            goto L_0x00d6
        L_0x0553:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r12 = "The android:onClick attribute cannot be used within a restricted context"
            r0.<init>(r12)
            throw r0
        L_0x055b:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            if (r0 != 0) goto L_0x00d6
            r0 = -268435457(0xffffffffefffffff, float:-1.5845632E29)
            r0 = r0 & r10
            r10 = 268435456(0x10000000, float:2.5243549E-29)
            r10 = r10 | r11
            goto L_0x0734
        L_0x0571:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1
            r12 = 0
            boolean r15 = r13.getBoolean(r2, r12)
            if (r15 == 0) goto L_0x0583
            r15 = 1
            r8.setScrollContainer(r15)
        L_0x0583:
            r18 = r0
            goto L_0x0802
        L_0x0587:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x00d6
            r0 = 67108864(0x4000000, float:1.5046328E-36)
            r0 = r0 | r10
            r10 = 67108864(0x4000000, float:1.5046328E-36)
            r10 = r10 | r11
            goto L_0x0734
        L_0x059c:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            if (r0 != 0) goto L_0x00d6
            r0 = -134217729(0xfffffffff7ffffff, float:-1.0384593E34)
            r0 = r0 & r10
            r10 = 134217728(0x8000000, float:3.85186E-34)
            r10 = r10 | r11
            goto L_0x0734
        L_0x05b2:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 23
            if (r1 >= r0) goto L_0x05c0
            boolean r0 = r8 instanceof android.widget.FrameLayout
            if (r0 == 0) goto L_0x00d6
        L_0x05c0:
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            r8.setForegroundGravity(r0)
            goto L_0x0833
        L_0x05ca:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getDimensionPixelSize(r2, r12)
            r8.mMinHeight = r0
            goto L_0x0833
        L_0x05d9:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getDimensionPixelSize(r2, r12)
            r8.mMinWidth = r0
            goto L_0x00d6
        L_0x05e8:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 23
            if (r1 >= r0) goto L_0x05f6
            boolean r0 = r8 instanceof android.widget.FrameLayout
            if (r0 == 0) goto L_0x00d6
        L_0x05f6:
            android.graphics.drawable.Drawable r0 = r13.getDrawable(r2)
            r8.setForeground(r0)
            goto L_0x00d6
        L_0x05ff:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x0833
            r0 = 4194304(0x400000, float:5.877472E-39)
            r0 = r0 | r10
            r10 = 4194304(0x400000, float:5.877472E-39)
            r10 = r10 | r11
            goto L_0x06ec
        L_0x0614:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            if (r0 == 0) goto L_0x00d6
            int[] r12 = DRAWING_CACHE_QUALITY_FLAGS
            r12 = r12[r0]
            r10 = r10 | r12
            r12 = 1572864(0x180000, float:2.204052E-39)
            r11 = r11 | r12
            goto L_0x0706
        L_0x062b:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 1
            boolean r0 = r13.getBoolean(r2, r0)
            if (r0 != 0) goto L_0x00d6
            r0 = 65536(0x10000, float:9.18355E-41)
            r10 = r10 | r0
            r0 = r0 | r11
            r57 = r0
            r46 = r10
            goto L_0x07a0
        L_0x0642:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x0833
            r0 = 2097152(0x200000, float:2.938736E-39)
            r10 = r10 | r0
            r0 = r0 | r11
            r57 = r0
            r46 = r10
            goto L_0x0806
        L_0x0659:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x00d6
            r0 = r10 | 16384(0x4000, float:2.2959E-41)
            r10 = r11 | 16384(0x4000, float:2.2959E-41)
            goto L_0x0734
        L_0x066c:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mNextFocusDownId = r0
            goto L_0x00d6
        L_0x067b:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mNextFocusUpId = r0
            goto L_0x00d6
        L_0x068a:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mNextFocusRightId = r0
            goto L_0x00d6
        L_0x0699:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mNextFocusLeftId = r0
            goto L_0x00d6
        L_0x06a8:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = 14
            if (r1 < r0) goto L_0x06b4
            goto L_0x00d6
        L_0x06b4:
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            if (r0 == 0) goto L_0x00d6
            r10 = r10 | r0
            r11 = r11 | 12288(0x3000, float:1.7219E-41)
            r8.initializeFadingEdgeInternal(r13)
            goto L_0x0706
        L_0x06c2:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            if (r0 == 0) goto L_0x00d6
            r10 = r10 | r0
            r11 = r11 | 768(0x300, float:1.076E-42)
            r12 = 1
            r46 = r10
            r57 = r11
            r33 = r12
            goto L_0x07a0
        L_0x06db:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x0833
            r0 = r10 | 2
            r10 = r11 | 2
        L_0x06ec:
            r46 = r0
            r57 = r10
            goto L_0x0806
        L_0x06f2:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            if (r0 == 0) goto L_0x00d6
            int[] r12 = VISIBILITY_FLAGS
            r12 = r12[r0]
            r10 = r10 | r12
            r11 = r11 | 12
        L_0x0706:
            r46 = r10
            goto L_0x073c
        L_0x0709:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            boolean r0 = r13.getBoolean(r2, r12)
            if (r0 == 0) goto L_0x00d6
            r0 = r10 & -17
            r10 = 262145(0x40001, float:3.67343E-40)
            r0 = r0 | r10
            r10 = 262161(0x40011, float:3.67366E-40)
            r10 = r10 | r11
            goto L_0x0734
        L_0x0721:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = r10 & -18
            int r12 = r8.getFocusableAttribute(r13)
            r0 = r0 | r12
            r10 = r0 & 16
            if (r10 != 0) goto L_0x073a
            r10 = r11 | 17
        L_0x0734:
            r46 = r0
            r57 = r10
            goto L_0x07a0
        L_0x073a:
            r46 = r0
        L_0x073c:
            r57 = r11
            goto L_0x07a0
        L_0x0740:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r16 = r0
            goto L_0x07cc
        L_0x0750:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r8.mUserPaddingRightInitial = r0
            r12 = 1
            r42 = r0
            r45 = r12
            goto L_0x07cc
        L_0x0765:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r41 = r0
            goto L_0x07cc
        L_0x0774:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r8.mUserPaddingLeftInitial = r0
            r12 = 1
            r40 = r0
            r44 = r12
            goto L_0x07cc
        L_0x0788:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getDimensionPixelSize(r2, r0)
            r8.mUserPaddingLeftInitial = r0
            r8.mUserPaddingRightInitial = r0
            r12 = 1
            r15 = 1
            r43 = r0
            r44 = r12
            r45 = r15
        L_0x07a0:
            r15 = r65
            goto L_0x07cc
        L_0x07a3:
            r65 = r15
            r10 = r46
            r11 = r57
            android.graphics.drawable.Drawable r0 = r13.getDrawable(r2)
            r7 = r0
            goto L_0x07cc
        L_0x07b0:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getDimensionPixelOffset(r2, r12)
            r15 = r0
            goto L_0x0839
        L_0x07bf:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getDimensionPixelOffset(r2, r12)
            r14 = r0
        L_0x07cc:
            r12 = 0
            goto L_0x0839
        L_0x07cf:
            r65 = r15
            r10 = r46
            r11 = r57
            java.lang.CharSequence r0 = r13.getText(r2)
            r8.mTag = r0
            goto L_0x00d6
        L_0x07dd:
            r65 = r15
            r10 = r46
            r11 = r57
            r0 = -1
            int r0 = r13.getResourceId(r2, r0)
            r8.mID = r0
            goto L_0x00d6
        L_0x07ec:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            int r0 = r13.getInt(r2, r12)
            if (r0 == 0) goto L_0x0800
            r15 = 50331648(0x3000000, float:3.761582E-37)
            r19 = r0 & r15
            r10 = r10 | r19
            r11 = r11 | r15
        L_0x0800:
            r31 = r0
        L_0x0802:
            r46 = r10
            r57 = r11
        L_0x0806:
            r15 = r65
            goto L_0x0839
        L_0x0809:
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            r0 = 23
            if (r1 >= r0) goto L_0x0818
            boolean r0 = r8 instanceof android.widget.FrameLayout
            if (r0 == 0) goto L_0x0833
        L_0x0818:
            android.view.View$ForegroundInfo r0 = r8.mForegroundInfo
            if (r0 != 0) goto L_0x0824
            android.view.View$ForegroundInfo r0 = new android.view.View$ForegroundInfo
            r15 = 0
            r0.<init>()
            r8.mForegroundInfo = r0
        L_0x0824:
            android.view.View$ForegroundInfo r0 = r8.mForegroundInfo
            android.view.View$ForegroundInfo r15 = r8.mForegroundInfo
            boolean r15 = r15.mInsidePadding
            boolean r15 = r13.getBoolean(r2, r15)
            boolean unused = r0.mInsidePadding = r15
        L_0x0833:
            r46 = r10
            r57 = r11
            r15 = r65
        L_0x0839:
            int r0 = r3 + 1
            r2 = r59
            r10 = r81
            r11 = r82
            r12 = r83
            goto L_0x00b4
        L_0x0845:
            r59 = r2
            r65 = r15
            r10 = r46
            r11 = r57
            r12 = 0
            r8.setOverScrollMode(r4)
            r8.mUserPaddingStart = r5
            r8.mUserPaddingEnd = r6
            if (r7 == 0) goto L_0x085a
            r8.setBackground(r7)
        L_0x085a:
            r2 = r44
            r8.mLeftPaddingDefined = r2
            r3 = r45
            r8.mRightPaddingDefined = r3
            r15 = r43
            if (r15 < 0) goto L_0x0878
            r0 = r15
            r41 = r15
            r19 = r15
            r16 = r15
            r8.mUserPaddingLeftInitial = r15
            r8.mUserPaddingRightInitial = r15
            r67 = r1
            r1 = r19
            r12 = r56
            goto L_0x0892
        L_0x0878:
            r12 = r56
            if (r12 < 0) goto L_0x0884
            r40 = r12
            r42 = r12
            r8.mUserPaddingLeftInitial = r12
            r8.mUserPaddingRightInitial = r12
        L_0x0884:
            r0 = r40
            r19 = r42
            if (r17 < 0) goto L_0x088e
            r41 = r17
            r16 = r17
        L_0x088e:
            r67 = r1
            r1 = r19
        L_0x0892:
            boolean r19 = r79.isRtlCompatibilityMode()
            if (r19 == 0) goto L_0x08bb
            r68 = r2
            boolean r2 = r8.mLeftPaddingDefined
            if (r2 != 0) goto L_0x08a1
            if (r35 == 0) goto L_0x08a1
            r0 = r5
        L_0x08a1:
            if (r0 < 0) goto L_0x08a5
            r2 = r0
            goto L_0x08a7
        L_0x08a5:
            int r2 = r8.mUserPaddingLeftInitial
        L_0x08a7:
            r8.mUserPaddingLeftInitial = r2
            boolean r2 = r8.mRightPaddingDefined
            if (r2 != 0) goto L_0x08b0
            if (r36 == 0) goto L_0x08b0
            r1 = r6
        L_0x08b0:
            if (r1 < 0) goto L_0x08b4
            r2 = r1
            goto L_0x08b6
        L_0x08b4:
            int r2 = r8.mUserPaddingRightInitial
        L_0x08b6:
            r8.mUserPaddingRightInitial = r2
            r69 = r3
            goto L_0x08db
        L_0x08bb:
            r68 = r2
            if (r35 != 0) goto L_0x08c5
            if (r36 == 0) goto L_0x08c2
            goto L_0x08c5
        L_0x08c2:
            r66 = 0
            goto L_0x08c7
        L_0x08c5:
            r66 = 1
        L_0x08c7:
            r2 = r66
            r69 = r3
            boolean r3 = r8.mLeftPaddingDefined
            if (r3 == 0) goto L_0x08d3
            if (r2 != 0) goto L_0x08d3
            r8.mUserPaddingLeftInitial = r0
        L_0x08d3:
            boolean r3 = r8.mRightPaddingDefined
            if (r3 == 0) goto L_0x08db
            if (r2 != 0) goto L_0x08db
            r8.mUserPaddingRightInitial = r1
        L_0x08db:
            int r2 = r8.mUserPaddingLeftInitial
            if (r41 < 0) goto L_0x08e2
            r3 = r41
            goto L_0x08e4
        L_0x08e2:
            int r3 = r8.mPaddingTop
        L_0x08e4:
            r70 = r0
            int r0 = r8.mUserPaddingRightInitial
            if (r16 < 0) goto L_0x08ef
            r71 = r1
            r1 = r16
            goto L_0x08f3
        L_0x08ef:
            r71 = r1
            int r1 = r8.mPaddingBottom
        L_0x08f3:
            r8.internalSetPadding(r2, r3, r0, r1)
            if (r11 == 0) goto L_0x08fb
            r8.setFlags(r10, r11)
        L_0x08fb:
            if (r33 == 0) goto L_0x0900
            r8.initializeScrollbarsInternal(r13)
        L_0x0900:
            if (r34 == 0) goto L_0x0905
            r79.initializeScrollIndicatorsInternal()
        L_0x0905:
            r13.recycle()
            if (r31 == 0) goto L_0x090d
            r79.recomputePadding()
        L_0x090d:
            if (r14 != 0) goto L_0x0915
            if (r65 == 0) goto L_0x0912
            goto L_0x0915
        L_0x0912:
            r1 = r65
            goto L_0x091a
        L_0x0915:
            r1 = r65
            r8.scrollTo(r14, r1)
        L_0x091a:
            if (r30 == 0) goto L_0x0958
            r2 = r47
            r8.setTranslationX(r2)
            r3 = r48
            r8.setTranslationY(r3)
            r72 = r1
            r1 = r54
            r8.setTranslationZ(r1)
            r73 = r1
            r1 = r55
            r8.setElevation(r1)
            r74 = r1
            r1 = r51
            r8.setRotation(r1)
            r75 = r1
            r1 = r52
            r8.setRotationX(r1)
            r76 = r1
            r1 = r53
            r8.setRotationY(r1)
            r77 = r1
            r1 = r49
            r8.setScaleX(r1)
            r78 = r1
            r1 = r50
            r8.setScaleY(r1)
            goto L_0x096c
        L_0x0958:
            r72 = r1
            r2 = r47
            r3 = r48
            r78 = r49
            r1 = r50
            r75 = r51
            r76 = r52
            r77 = r53
            r73 = r54
            r74 = r55
        L_0x096c:
            if (r18 != 0) goto L_0x0976
            r0 = r10 & 512(0x200, float:7.175E-43)
            if (r0 == 0) goto L_0x0976
            r0 = 1
            r8.setScrollContainer(r0)
        L_0x0976:
            r79.computeOpaqueFlags()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    public int[] getAttributeResolutionStack(int attribute) {
        if (!sDebugViewAttributes || this.mAttributeResolutionStacks == null || this.mAttributeResolutionStacks.get(attribute) == null) {
            return new int[0];
        }
        int[] attributeResolutionStack = this.mAttributeResolutionStacks.get(attribute);
        int stackSize = attributeResolutionStack.length;
        if (this.mSourceLayoutId != 0) {
            stackSize++;
        }
        int currentIndex = 0;
        int[] stack = new int[stackSize];
        if (this.mSourceLayoutId != 0) {
            stack[0] = this.mSourceLayoutId;
            currentIndex = 0 + 1;
        }
        for (int i : attributeResolutionStack) {
            stack[currentIndex] = i;
            currentIndex++;
        }
        return stack;
    }

    public Map<Integer, Integer> getAttributeSourceResourceMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        if (!sDebugViewAttributes || this.mAttributeSourceResId == null) {
            return map;
        }
        for (int i = 0; i < this.mAttributeSourceResId.size(); i++) {
            map.put(Integer.valueOf(this.mAttributeSourceResId.keyAt(i)), Integer.valueOf(this.mAttributeSourceResId.valueAt(i)));
        }
        return map;
    }

    public int getExplicitStyle() {
        if (!sDebugViewAttributes) {
            return 0;
        }
        return this.mExplicitStyle;
    }

    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{v});
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context, String name) {
            String idText;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, new Class[]{View.class})) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                idText = "";
            } else {
                idText = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + idText);
        }
    }

    @UnsupportedAppUsage
    View() {
        this.mCurrentAnimation = null;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mLayerType = 0;
        this.mInputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mSourceLayoutId = 0;
        this.mResources = null;
        this.mRenderNode = RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
    }

    /* access modifiers changed from: package-private */
    public final boolean debugDraw() {
        return DEBUG_DRAW || (this.mAttachInfo != null && this.mAttachInfo.mDebugLayout);
    }

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray<>();
        }
        return mAttributeMap;
    }

    private void retrieveExplicitStyle(Resources.Theme theme, AttributeSet attrs) {
        if (sDebugViewAttributes) {
            this.mExplicitStyle = theme.getExplicitStyle(attrs);
        }
    }

    public final void saveAttributeDataForStyleable(Context context, int[] styleable, AttributeSet attrs, TypedArray t, int defStyleAttr, int defStyleRes) {
        if (sDebugViewAttributes) {
            int[] attributeResolutionStack = context.getTheme().getAttributeResolutionStack(defStyleAttr, defStyleRes, this.mExplicitStyle);
            if (this.mAttributeResolutionStacks == null) {
                this.mAttributeResolutionStacks = new SparseArray<>();
            }
            if (this.mAttributeSourceResId == null) {
                this.mAttributeSourceResId = new SparseIntArray();
            }
            int indexCount = t.getIndexCount();
            for (int j = 0; j < indexCount; j++) {
                int index = t.getIndex(j);
                this.mAttributeSourceResId.append(styleable[index], t.getSourceResourceId(index, 0));
                this.mAttributeResolutionStacks.append(styleable[index], attributeResolutionStack);
            }
        }
    }

    private void saveAttributeData(AttributeSet attrs, TypedArray t) {
        int resourceId;
        String resourceName;
        AttributeSet attributeSet = attrs;
        TypedArray typedArray = t;
        int attrsCount = attributeSet == null ? 0 : attrs.getAttributeCount();
        int indexCount = t.getIndexCount();
        String[] attributes = new String[((attrsCount + indexCount) * 2)];
        int i = 0;
        for (int j = 0; j < attrsCount; j++) {
            attributes[i] = attributeSet.getAttributeName(j);
            attributes[i + 1] = attributeSet.getAttributeValue(j);
            i += 2;
        }
        Resources res = t.getResources();
        SparseArray<String> attributeMap = getAttributeMap();
        int j2 = 0;
        while (true) {
            int j3 = j2;
            if (j3 < indexCount) {
                int index = typedArray.getIndex(j3);
                if (typedArray.hasValueOrEmpty(index) && (resourceId = typedArray.getResourceId(index, 0)) != 0) {
                    String resourceName2 = attributeMap.get(resourceId);
                    if (resourceName2 == null) {
                        try {
                            resourceName = res.getResourceName(resourceId);
                        } catch (Resources.NotFoundException e) {
                            Resources.NotFoundException notFoundException = e;
                            resourceName = "0x" + Integer.toHexString(resourceId);
                        }
                        resourceName2 = resourceName;
                        attributeMap.put(resourceId, resourceName2);
                    }
                    attributes[i] = resourceName2;
                    attributes[i + 1] = typedArray.getString(index);
                    i += 2;
                }
                j2 = j3 + 1;
            } else {
                String[] trimmed = new String[i];
                System.arraycopy(attributes, 0, trimmed, 0, i);
                this.mAttributes = trimmed;
                return;
            }
        }
    }

    public String toString() {
        String pkgname;
        StringBuilder out = new StringBuilder(128);
        out.append(getClass().getName());
        out.append('{');
        out.append(Integer.toHexString(System.identityHashCode(this)));
        out.append(' ');
        int i = this.mViewFlags & 12;
        char c = 'I';
        char c2 = 'V';
        char c3 = '.';
        if (i == 0) {
            out.append('V');
        } else if (i == 4) {
            out.append('I');
        } else if (i != 8) {
            out.append('.');
        } else {
            out.append('G');
        }
        char c4 = 'F';
        out.append((this.mViewFlags & 1) == 1 ? 'F' : '.');
        out.append((this.mViewFlags & 32) == 0 ? DateFormat.DAY : '.');
        out.append((this.mViewFlags & 128) == 128 ? '.' : 'D');
        char c5 = 'H';
        out.append((this.mViewFlags & 256) != 0 ? 'H' : '.');
        if ((this.mViewFlags & 512) == 0) {
            c2 = '.';
        }
        out.append(c2);
        out.append((this.mViewFlags & 16384) != 0 ? 'C' : '.');
        out.append((this.mViewFlags & 2097152) != 0 ? DateFormat.STANDALONE_MONTH : '.');
        out.append((this.mViewFlags & 8388608) != 0 ? 'X' : '.');
        out.append(' ');
        out.append((this.mPrivateFlags & 8) != 0 ? 'R' : '.');
        if ((this.mPrivateFlags & 2) == 0) {
            c4 = '.';
        }
        out.append(c4);
        out.append((this.mPrivateFlags & 4) != 0 ? 'S' : '.');
        if ((this.mPrivateFlags & 33554432) != 0) {
            out.append('p');
        } else {
            out.append((this.mPrivateFlags & 16384) != 0 ? 'P' : '.');
        }
        if ((this.mPrivateFlags & 268435456) == 0) {
            c5 = '.';
        }
        out.append(c5);
        out.append((this.mPrivateFlags & 1073741824) != 0 ? DateFormat.CAPITAL_AM_PM : '.');
        if ((this.mPrivateFlags & Integer.MIN_VALUE) == 0) {
            c = '.';
        }
        out.append(c);
        if ((this.mPrivateFlags & 2097152) != 0) {
            c3 = 'D';
        }
        out.append(c3);
        out.append(' ');
        out.append(this.mLeft);
        out.append(',');
        out.append(this.mTop);
        out.append('-');
        out.append(this.mRight);
        out.append(',');
        out.append(this.mBottom);
        int id = getId();
        if (id != -1) {
            out.append(" #");
            out.append(Integer.toHexString(id));
            Resources r = this.mResources;
            if (id > 0 && Resources.resourceHasPackage(id) && r != null) {
                int i2 = -16777216 & id;
                if (i2 == 16777216) {
                    pkgname = "android";
                } else if (i2 != 2130706432) {
                    try {
                        pkgname = r.getResourcePackageName(id);
                    } catch (Resources.NotFoundException e) {
                    }
                } else {
                    pkgname = "app";
                }
                String typename = r.getResourceTypeName(id);
                String entryname = r.getResourceEntryName(id);
                out.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
                out.append(pkgname);
                out.append(SettingsStringUtil.DELIMITER);
                out.append(typename);
                out.append("/");
                out.append(entryname);
            }
        }
        if (this.mAutofillId != null) {
            out.append(" aid=");
            out.append(this.mAutofillId);
        }
        out.append("}");
        return out.toString();
    }

    /* access modifiers changed from: protected */
    public void initializeFadingEdge(TypedArray a) {
        TypedArray arr = this.mContext.obtainStyledAttributes(R.styleable.View);
        initializeFadingEdgeInternal(arr);
        arr.recycle();
    }

    /* access modifiers changed from: protected */
    public void initializeFadingEdgeInternal(TypedArray a) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = a.getDimensionPixelSize(25, ViewConfiguration.get(this.mContext).getScaledFadingEdgeLength());
    }

    public int getVerticalFadingEdgeLength() {
        ScrollabilityCache cache;
        if (!isVerticalFadingEdgeEnabled() || (cache = this.mScrollCache) == null) {
            return 0;
        }
        return cache.fadingEdgeLength;
    }

    public void setFadingEdgeLength(int length) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = length;
    }

    public int getHorizontalFadingEdgeLength() {
        ScrollabilityCache cache;
        if (!isHorizontalFadingEdgeEnabled() || (cache = this.mScrollCache) == null) {
            return 0;
        }
        return cache.fadingEdgeLength;
    }

    public int getVerticalScrollbarWidth() {
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache == null || (scrollBar = cache.scrollBar) == null) {
            return 0;
        }
        int size = scrollBar.getSize(true);
        if (size <= 0) {
            return cache.scrollBarSize;
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public int getHorizontalScrollbarHeight() {
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache == null || (scrollBar = cache.scrollBar) == null) {
            return 0;
        }
        int size = scrollBar.getSize(false);
        if (size <= 0) {
            return cache.scrollBarSize;
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public void initializeScrollbars(TypedArray a) {
        TypedArray arr = this.mContext.obtainStyledAttributes(R.styleable.View);
        initializeScrollbarsInternal(arr);
        arr.recycle();
    }

    private void initializeScrollBarDrawable() {
        initScrollCache();
        if (this.mScrollCache.scrollBar == null) {
            this.mScrollCache.scrollBar = new ScrollBarDrawable();
            this.mScrollCache.scrollBar.setState(getDrawableState());
            this.mScrollCache.scrollBar.setCallback(this);
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void initializeScrollbarsInternal(TypedArray a) {
        initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new ScrollBarDrawable();
            scrollabilityCache.scrollBar.setState(getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        boolean fadeScrollbars = a.getBoolean(47, true);
        if (!fadeScrollbars) {
            scrollabilityCache.state = 1;
        }
        scrollabilityCache.fadeScrollBars = fadeScrollbars;
        scrollabilityCache.scrollBarFadeDuration = a.getInt(45, ViewConfiguration.getScrollBarFadeDuration());
        scrollabilityCache.scrollBarDefaultDelayBeforeFade = a.getInt(46, ViewConfiguration.getScrollDefaultDelay());
        scrollabilityCache.scrollBarSize = a.getDimensionPixelSize(1, ViewConfiguration.get(this.mContext).getScaledScrollBarSize());
        scrollabilityCache.scrollBar.setHorizontalTrackDrawable(a.getDrawable(4));
        Drawable thumb = a.getDrawable(2);
        if (thumb != null) {
            scrollabilityCache.scrollBar.setHorizontalThumbDrawable(thumb);
        }
        if (a.getBoolean(6, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawHorizontalTrack(true);
        }
        Drawable track = a.getDrawable(5);
        scrollabilityCache.scrollBar.setVerticalTrackDrawable(track);
        Drawable thumb2 = a.getDrawable(3);
        if (thumb2 != null) {
            scrollabilityCache.scrollBar.setVerticalThumbDrawable(thumb2);
        }
        if (a.getBoolean(7, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawVerticalTrack(true);
        }
        int layoutDirection = getLayoutDirection();
        if (track != null) {
            track.setLayoutDirection(layoutDirection);
        }
        if (thumb2 != null) {
            thumb2.setLayoutDirection(layoutDirection);
        }
        resolvePadding();
    }

    public void setVerticalScrollbarThumbDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalThumbDrawable(drawable);
    }

    public void setVerticalScrollbarTrackDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalTrackDrawable(drawable);
    }

    public void setHorizontalScrollbarThumbDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalThumbDrawable(drawable);
    }

    public void setHorizontalScrollbarTrackDrawable(Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalTrackDrawable(drawable);
    }

    public Drawable getVerticalScrollbarThumbDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getVerticalThumbDrawable();
        }
        return null;
    }

    public Drawable getVerticalScrollbarTrackDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getVerticalTrackDrawable();
        }
        return null;
    }

    public Drawable getHorizontalScrollbarThumbDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getHorizontalThumbDrawable();
        }
        return null;
    }

    public Drawable getHorizontalScrollbarTrackDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getHorizontalTrackDrawable();
        }
        return null;
    }

    private void initializeScrollIndicatorsInternal() {
        if (this.mScrollIndicatorDrawable == null) {
            this.mScrollIndicatorDrawable = this.mContext.getDrawable(R.drawable.scroll_indicator_material);
        }
    }

    private void initScrollCache() {
        if (this.mScrollCache == null) {
            this.mScrollCache = new ScrollabilityCache(ViewConfiguration.get(this.mContext), this);
        }
    }

    @UnsupportedAppUsage
    private ScrollabilityCache getScrollCache() {
        initScrollCache();
        return this.mScrollCache;
    }

    public void setVerticalScrollbarPosition(int position) {
        if (this.mVerticalScrollbarPosition != position) {
            this.mVerticalScrollbarPosition = position;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public int getVerticalScrollbarPosition() {
        return this.mVerticalScrollbarPosition;
    }

    /* access modifiers changed from: package-private */
    public boolean isOnScrollbar(float x, float y) {
        if (this.mScrollCache == null) {
            return false;
        }
        float x2 = x + ((float) getScrollX());
        float y2 = y + ((float) getScrollY());
        boolean canScrollVertically = computeVerticalScrollRange() > computeVerticalScrollExtent();
        if (isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && canScrollVertically) {
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds((Rect) null, touchBounds);
            if (touchBounds.contains((int) x2, (int) y2)) {
                return true;
            }
        }
        boolean canScrollHorizontally = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        if (isHorizontalScrollBarEnabled() && canScrollHorizontally) {
            Rect touchBounds2 = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds((Rect) null, touchBounds2);
            if (touchBounds2.contains((int) x2, (int) y2)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean isOnScrollbarThumb(float x, float y) {
        return isOnVerticalScrollbarThumb(x, y) || isOnHorizontalScrollbarThumb(x, y);
    }

    private boolean isOnVerticalScrollbarThumb(float x, float y) {
        int range;
        int extent;
        if (this.mScrollCache != null && isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && (range = computeVerticalScrollRange()) > (extent = computeVerticalScrollExtent())) {
            float x2 = x + ((float) getScrollX());
            float y2 = y + ((float) getScrollY());
            Rect bounds = this.mScrollCache.mScrollBarBounds;
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(bounds, touchBounds);
            int offset = computeVerticalScrollOffset();
            int thumbLength = ScrollBarUtils.getThumbLength(bounds.height(), bounds.width(), extent, range);
            int thumbTop = bounds.top + ScrollBarUtils.getThumbOffset(bounds.height(), thumbLength, extent, range, offset);
            int adjust = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (x2 < ((float) touchBounds.left) || x2 > ((float) touchBounds.right) || y2 < ((float) (thumbTop - adjust)) || y2 > ((float) (thumbTop + thumbLength + adjust))) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isOnHorizontalScrollbarThumb(float x, float y) {
        int range;
        int extent;
        if (this.mScrollCache != null && isHorizontalScrollBarEnabled() && (range = computeHorizontalScrollRange()) > (extent = computeHorizontalScrollExtent())) {
            float x2 = x + ((float) getScrollX());
            float y2 = y + ((float) getScrollY());
            Rect bounds = this.mScrollCache.mScrollBarBounds;
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(bounds, touchBounds);
            int offset = computeHorizontalScrollOffset();
            int thumbLength = ScrollBarUtils.getThumbLength(bounds.width(), bounds.height(), extent, range);
            int thumbLeft = bounds.left + ScrollBarUtils.getThumbOffset(bounds.width(), thumbLength, extent, range, offset);
            int adjust = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (x2 < ((float) (thumbLeft - adjust)) || x2 > ((float) (thumbLeft + thumbLength + adjust)) || y2 < ((float) touchBounds.top) || y2 > ((float) touchBounds.bottom)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean isDraggingScrollBar() {
        return (this.mScrollCache == null || this.mScrollCache.mScrollBarDraggingState == 0) ? false : true;
    }

    public void setScrollIndicators(int indicators) {
        setScrollIndicators(indicators, 63);
    }

    public void setScrollIndicators(int indicators, int mask) {
        int mask2 = (mask << 8) & SCROLL_INDICATORS_PFLAG3_MASK;
        int indicators2 = (indicators << 8) & mask2;
        int updatedFlags = (this.mPrivateFlags3 & (~mask2)) | indicators2;
        if (this.mPrivateFlags3 != updatedFlags) {
            this.mPrivateFlags3 = updatedFlags;
            if (indicators2 != 0) {
                initializeScrollIndicatorsInternal();
            }
            invalidate();
        }
    }

    public int getScrollIndicators() {
        return (this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) >>> 8;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public ListenerInfo getListenerInfo() {
        if (this.mListenerInfo != null) {
            return this.mListenerInfo;
        }
        this.mListenerInfo = new ListenerInfo();
        return this.mListenerInfo;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        getListenerInfo().mOnScrollChangeListener = l;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        getListenerInfo().mOnFocusChangeListener = l;
    }

    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        ListenerInfo li = getListenerInfo();
        if (li.mOnLayoutChangeListeners == null) {
            ArrayList unused = li.mOnLayoutChangeListeners = new ArrayList();
        }
        if (!li.mOnLayoutChangeListeners.contains(listener)) {
            li.mOnLayoutChangeListeners.add(listener);
        }
    }

    public void removeOnLayoutChangeListener(OnLayoutChangeListener listener) {
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnLayoutChangeListeners != null) {
            li.mOnLayoutChangeListeners.remove(listener);
        }
    }

    public void addOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        ListenerInfo li = getListenerInfo();
        if (li.mOnAttachStateChangeListeners == null) {
            CopyOnWriteArrayList unused = li.mOnAttachStateChangeListeners = new CopyOnWriteArrayList();
        }
        li.mOnAttachStateChangeListeners.add(listener);
    }

    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnAttachStateChangeListeners != null) {
            li.mOnAttachStateChangeListeners.remove(listener);
        }
    }

    public OnFocusChangeListener getOnFocusChangeListener() {
        ListenerInfo li = this.mListenerInfo;
        if (li != null) {
            return li.mOnFocusChangeListener;
        }
        return null;
    }

    public void setOnClickListener(OnClickListener l) {
        if (!isClickable()) {
            setClickable(true);
        }
        getListenerInfo().mOnClickListener = l;
    }

    public boolean hasOnClickListeners() {
        ListenerInfo li = this.mListenerInfo;
        return (li == null || li.mOnClickListener == null) ? false : true;
    }

    public void setOnLongClickListener(OnLongClickListener l) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnLongClickListener = l;
    }

    public void setOnContextClickListener(OnContextClickListener l) {
        if (!isContextClickable()) {
            setContextClickable(true);
        }
        getListenerInfo().mOnContextClickListener = l;
    }

    public void setOnCreateContextMenuListener(OnCreateContextMenuListener l) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnCreateContextMenuListener = l;
    }

    public void addFrameMetricsListener(Window window, Window.OnFrameMetricsAvailableListener listener, Handler handler) {
        if (this.mAttachInfo == null) {
            if (this.mFrameMetricsObservers == null) {
                this.mFrameMetricsObservers = new ArrayList<>();
            }
            this.mFrameMetricsObservers.add(new FrameMetricsObserver(window, handler.getLooper(), listener));
        } else if (this.mAttachInfo.mThreadedRenderer != null) {
            if (this.mFrameMetricsObservers == null) {
                this.mFrameMetricsObservers = new ArrayList<>();
            }
            FrameMetricsObserver fmo = new FrameMetricsObserver(window, handler.getLooper(), listener);
            this.mFrameMetricsObservers.add(fmo);
            this.mAttachInfo.mThreadedRenderer.addFrameMetricsObserver(fmo);
        } else {
            Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
        }
    }

    public void removeFrameMetricsListener(Window.OnFrameMetricsAvailableListener listener) {
        ThreadedRenderer renderer = getThreadedRenderer();
        FrameMetricsObserver fmo = findFrameMetricsObserver(listener);
        if (fmo == null) {
            throw new IllegalArgumentException("attempt to remove OnFrameMetricsAvailableListener that was never added");
        } else if (this.mFrameMetricsObservers != null) {
            this.mFrameMetricsObservers.remove(fmo);
            if (renderer != null) {
                renderer.removeFrameMetricsObserver(fmo);
            }
        }
    }

    private void registerPendingFrameMetricsObservers() {
        if (this.mFrameMetricsObservers != null) {
            ThreadedRenderer renderer = getThreadedRenderer();
            if (renderer != null) {
                Iterator<FrameMetricsObserver> it = this.mFrameMetricsObservers.iterator();
                while (it.hasNext()) {
                    renderer.addFrameMetricsObserver(it.next());
                }
                return;
            }
            Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
        }
    }

    private FrameMetricsObserver findFrameMetricsObserver(Window.OnFrameMetricsAvailableListener listener) {
        if (this.mFrameMetricsObservers == null) {
            return null;
        }
        for (int i = 0; i < this.mFrameMetricsObservers.size(); i++) {
            FrameMetricsObserver observer = this.mFrameMetricsObservers.get(i);
            if (observer.mListener == listener) {
                return observer;
            }
        }
        return null;
    }

    public void setNotifyAutofillManagerOnClick(boolean notify) {
        if (notify) {
            this.mPrivateFlags |= 536870912;
        } else {
            this.mPrivateFlags &= -536870913;
        }
    }

    private void notifyAutofillManagerOnClick() {
        if ((this.mPrivateFlags & 536870912) != 0) {
            try {
                getAutofillManager().notifyViewClicked(this);
            } finally {
                this.mPrivateFlags = -536870913 & this.mPrivateFlags;
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean performClickInternal() {
        notifyAutofillManagerOnClick();
        return performClick();
    }

    public boolean performClick() {
        notifyAutofillManagerOnClick();
        ListenerInfo li = this.mListenerInfo;
        boolean result = false;
        if (!(li == null || li.mOnClickListener == null)) {
            playSoundEffect(0);
            li.mOnClickListener.onClick(this);
            result = true;
        }
        sendAccessibilityEvent(1);
        notifyEnterOrExitForAutoFillIfNeeded(true);
        return result;
    }

    public boolean callOnClick() {
        ListenerInfo li = this.mListenerInfo;
        if (li == null || li.mOnClickListener == null) {
            return false;
        }
        li.mOnClickListener.onClick(this);
        return true;
    }

    public boolean performLongClick() {
        return performLongClickInternal(this.mLongClickX, this.mLongClickY);
    }

    public boolean performLongClick(float x, float y) {
        this.mLongClickX = x;
        this.mLongClickY = y;
        boolean handled = performLongClick();
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        return handled;
    }

    private boolean performLongClickInternal(float x, float y) {
        sendAccessibilityEvent(2);
        boolean handled = false;
        ListenerInfo li = this.mListenerInfo;
        if (!(li == null || li.mOnLongClickListener == null)) {
            handled = li.mOnLongClickListener.onLongClick(this);
        }
        if (!handled) {
            handled = !Float.isNaN(x) && !Float.isNaN(y) ? showContextMenu(x, y) : showContextMenu();
        }
        if ((this.mViewFlags & 1073741824) == 1073741824 && !handled) {
            handled = showLongClickTooltip((int) x, (int) y);
        }
        if (handled) {
            performHapticFeedback(0);
        }
        return handled;
    }

    public boolean performContextClick(float x, float y) {
        return performContextClick();
    }

    public boolean performContextClick() {
        sendAccessibilityEvent(8388608);
        boolean handled = false;
        ListenerInfo li = this.mListenerInfo;
        if (!(li == null || li.mOnContextClickListener == null)) {
            handled = li.mOnContextClickListener.onContextClick(this);
        }
        if (handled) {
            performHapticFeedback(6);
        }
        return handled;
    }

    /* access modifiers changed from: protected */
    public boolean performButtonActionOnTouchDown(MotionEvent event) {
        if (!event.isFromSource(8194) || (event.getButtonState() & 2) == 0) {
            return false;
        }
        showContextMenu(event.getX(), event.getY());
        this.mPrivateFlags |= 67108864;
        return true;
    }

    public boolean showContextMenu() {
        return getParent().showContextMenuForChild(this);
    }

    public boolean showContextMenu(float x, float y) {
        return getParent().showContextMenuForChild(this, x, y);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int type) {
        ViewParent parent = getParent();
        if (parent == null) {
            return null;
        }
        try {
            return parent.startActionModeForChild(this, callback, type);
        } catch (AbstractMethodError e) {
            return parent.startActionModeForChild(this, callback);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void startActivityForResult(Intent intent, int requestCode) {
        this.mStartActivityRequestWho = "@android:view:" + System.identityHashCode(this);
        getContext().startActivityForResult(this.mStartActivityRequestWho, intent, requestCode, (Bundle) null);
    }

    public boolean dispatchActivityResult(String who, int requestCode, int resultCode, Intent data) {
        if (this.mStartActivityRequestWho == null || !this.mStartActivityRequestWho.equals(who)) {
            return false;
        }
        onActivityResult(requestCode, resultCode, data);
        this.mStartActivityRequestWho = null;
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void setOnKeyListener(OnKeyListener l) {
        OnKeyListener unused = getListenerInfo().mOnKeyListener = l;
    }

    public void setOnTouchListener(OnTouchListener l) {
        OnTouchListener unused = getListenerInfo().mOnTouchListener = l;
    }

    public void setOnGenericMotionListener(OnGenericMotionListener l) {
        OnGenericMotionListener unused = getListenerInfo().mOnGenericMotionListener = l;
    }

    public void setOnHoverListener(OnHoverListener l) {
        OnHoverListener unused = getListenerInfo().mOnHoverListener = l;
    }

    public void setOnDragListener(OnDragListener l) {
        OnDragListener unused = getListenerInfo().mOnDragListener = l;
    }

    /* access modifiers changed from: package-private */
    public void handleFocusGainInternal(int direction, Rect previouslyFocusedRect) {
        if ((this.mPrivateFlags & 2) == 0) {
            this.mPrivateFlags |= 2;
            View oldFocus = this.mAttachInfo != null ? getRootView().findFocus() : null;
            if (this.mParent != null) {
                this.mParent.requestChildFocus(this, this);
                updateFocusedInCluster(oldFocus, direction);
            }
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(oldFocus, this);
            }
            onFocusChanged(true, direction, previouslyFocusedRect);
            refreshDrawableState();
        }
    }

    public final void setRevealOnFocusHint(boolean revealOnFocus) {
        if (revealOnFocus) {
            this.mPrivateFlags3 &= -67108865;
        } else {
            this.mPrivateFlags3 |= 67108864;
        }
    }

    public final boolean getRevealOnFocusHint() {
        return (this.mPrivateFlags3 & 67108864) == 0;
    }

    public void getHotspotBounds(Rect outRect) {
        Drawable background = getBackground();
        if (background != null) {
            background.getHotspotBounds(outRect);
        } else {
            getBoundsOnScreen(outRect);
        }
    }

    public boolean requestRectangleOnScreen(Rect rectangle) {
        return requestRectangleOnScreen(rectangle, false);
    }

    public boolean requestRectangleOnScreen(Rect rectangle, boolean immediate) {
        boolean scrolled = false;
        if (this.mParent == null) {
            return false;
        }
        View child = this;
        RectF position = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new RectF();
        position.set(rectangle);
        ViewParent parent = this.mParent;
        while (parent != null) {
            rectangle.set((int) position.left, (int) position.top, (int) position.right, (int) position.bottom);
            scrolled |= parent.requestChildRectangleOnScreen(child, rectangle, immediate);
            if (!(parent instanceof View)) {
                break;
            }
            position.offset((float) (child.mLeft - child.getScrollX()), (float) (child.mTop - child.getScrollY()));
            child = (View) parent;
            parent = child.getParent();
        }
        return scrolled;
    }

    public void clearFocus() {
        clearFocusInternal((View) null, true, sAlwaysAssignFocus || !isInTouchMode());
    }

    /* access modifiers changed from: package-private */
    public void clearFocusInternal(View focused, boolean propagate, boolean refocus) {
        if ((this.mPrivateFlags & 2) != 0) {
            this.mPrivateFlags &= -3;
            clearParentsWantFocus();
            if (propagate && this.mParent != null) {
                this.mParent.clearChildFocus(this);
            }
            onFocusChanged(false, 0, (Rect) null);
            refreshDrawableState();
            if (!propagate) {
                return;
            }
            if (!refocus || !rootViewRequestFocus()) {
                notifyGlobalFocusCleared(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyGlobalFocusCleared(View oldFocus) {
        if (oldFocus != null && this.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(oldFocus, (View) null);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean rootViewRequestFocus() {
        View root = getRootView();
        return root != null && root.requestFocus();
    }

    /* access modifiers changed from: package-private */
    public void unFocus(View focused) {
        clearFocusInternal(focused, false, false);
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public boolean hasFocus() {
        return (this.mPrivateFlags & 2) != 0;
    }

    public boolean hasFocusable() {
        return hasFocusable(!sHasFocusableExcludeAutoFocusable, false);
    }

    public boolean hasExplicitFocusable() {
        return hasFocusable(false, true);
    }

    /* access modifiers changed from: package-private */
    public boolean hasFocusable(boolean allowAutoFocus, boolean dispatchExplicit) {
        if (!isFocusableInTouchMode()) {
            for (ViewParent p = this.mParent; p instanceof ViewGroup; p = p.getParent()) {
                if (((ViewGroup) p).shouldBlockFocusForTouchscreen()) {
                    return false;
                }
            }
        }
        if ((this.mViewFlags & 12) != 0 || (this.mViewFlags & 32) != 0) {
            return false;
        }
        if ((allowAutoFocus || getFocusable() != 16) && isFocusable()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {
            sendAccessibilityEvent(8);
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        switchDefaultFocusHighlight();
        if (!gainFocus) {
            if (isPressed()) {
                setPressed(false);
            }
            if (this.mAttachInfo != null && this.mAttachInfo.mHasWindowFocus) {
                notifyFocusChangeToInputMethodManager(false);
            }
            onFocusLost();
        } else if (this.mAttachInfo != null && this.mAttachInfo.mHasWindowFocus) {
            notifyFocusChangeToInputMethodManager(true);
        }
        invalidate(true);
        ListenerInfo li = this.mListenerInfo;
        if (!(li == null || li.mOnFocusChangeListener == null)) {
            li.mOnFocusChangeListener.onFocusChange(this, gainFocus);
        }
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mKeyDispatchState.reset(this);
        }
        notifyEnterOrExitForAutoFillIfNeeded(gainFocus);
    }

    private void notifyFocusChangeToInputMethodManager(boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
        if (imm != null) {
            if (hasFocus) {
                imm.focusIn(this);
            } else {
                imm.focusOut(this);
            }
        }
    }

    public void notifyEnterOrExitForAutoFillIfNeeded(boolean enter) {
        AutofillManager afm;
        if (canNotifyAutofillEnterExitEvent() && (afm = getAutofillManager()) != null) {
            if (!enter || !isFocused()) {
                if (!enter && !isFocused()) {
                    afm.notifyViewExited(this);
                }
            } else if (!isLaidOut()) {
                this.mPrivateFlags3 |= 134217728;
            } else if (isVisibleToUser()) {
                afm.notifyViewEntered(this);
            }
        }
    }

    public void setAccessibilityPaneTitle(CharSequence accessibilityPaneTitle) {
        if (!TextUtils.equals(accessibilityPaneTitle, this.mAccessibilityPaneTitle)) {
            this.mAccessibilityPaneTitle = accessibilityPaneTitle;
            notifyViewAccessibilityStateChangedIfNeeded(8);
        }
    }

    public CharSequence getAccessibilityPaneTitle() {
        return this.mAccessibilityPaneTitle;
    }

    private boolean isAccessibilityPane() {
        return this.mAccessibilityPaneTitle != null;
    }

    public void sendAccessibilityEvent(int eventType) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEvent(this, eventType);
        } else {
            sendAccessibilityEventInternal(eventType);
        }
    }

    public void announceForAccessibility(CharSequence text) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mParent != null) {
            AccessibilityEvent event = AccessibilityEvent.obtain(16384);
            onInitializeAccessibilityEvent(event);
            event.getText().add(text);
            event.setContentDescription((CharSequence) null);
            this.mParent.requestSendAccessibilityEvent(this, event);
        }
    }

    public void sendAccessibilityEventInternal(int eventType) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            sendAccessibilityEventUnchecked(AccessibilityEvent.obtain(eventType));
        }
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, event);
        } else {
            sendAccessibilityEventUncheckedInternal(event);
        }
    }

    public void sendAccessibilityEventUncheckedInternal(AccessibilityEvent event) {
        boolean isWindowDisappearedEvent = false;
        if ((event.getEventType() == 32) && (32 & event.getContentChangeTypes()) != 0) {
            isWindowDisappearedEvent = true;
        }
        if (isShown() || isWindowDisappearedEvent) {
            onInitializeAccessibilityEvent(event);
            if ((event.getEventType() & POPULATING_ACCESSIBILITY_EVENT_TYPES) != 0) {
                dispatchPopulateAccessibilityEvent(event);
            }
            if (getParent() != null) {
                getParent().requestSendAccessibilityEvent(this, event);
            }
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.dispatchPopulateAccessibilityEvent(this, event);
        }
        return dispatchPopulateAccessibilityEventInternal(event);
    }

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        onPopulateAccessibilityEvent(event);
        return false;
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onPopulateAccessibilityEvent(this, event);
        } else {
            onPopulateAccessibilityEventInternal(event);
        }
    }

    public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        if (event.getEventType() == 32 && isAccessibilityPane()) {
            event.getText().add(getAccessibilityPaneTitle());
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onInitializeAccessibilityEvent(this, event);
        } else {
            onInitializeAccessibilityEventInternal(event);
        }
    }

    @UnsupportedAppUsage
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        CharSequence text;
        event.setSource(this);
        event.setClassName(getAccessibilityClassName());
        event.setPackageName(getContext().getPackageName());
        event.setEnabled(isEnabled());
        event.setContentDescription(this.mContentDescription);
        int eventType = event.getEventType();
        if (eventType == 8) {
            ArrayList<View> focusablesTempList = this.mAttachInfo != null ? this.mAttachInfo.mTempArrayList : new ArrayList<>();
            getRootView().addFocusables(focusablesTempList, 2, 0);
            event.setItemCount(focusablesTempList.size());
            event.setCurrentItemIndex(focusablesTempList.indexOf(this));
            if (this.mAttachInfo != null) {
                focusablesTempList.clear();
            }
        } else if (eventType == 8192 && (text = getIterableTextForAccessibility()) != null && text.length() > 0) {
            event.setFromIndex(getAccessibilitySelectionStart());
            event.setToIndex(getAccessibilitySelectionEnd());
            event.setItemCount(text.length());
        }
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.createAccessibilityNodeInfo(this);
        }
        return createAccessibilityNodeInfoInternal();
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfoInternal() {
        AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
        if (provider != null) {
            return provider.createAccessibilityNodeInfo(-1);
        }
        AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain(this);
        onInitializeAccessibilityNodeInfo(info);
        return info;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onInitializeAccessibilityNodeInfo(this, info);
        } else {
            onInitializeAccessibilityNodeInfoInternal(info);
        }
    }

    @UnsupportedAppUsage
    public void getBoundsOnScreen(Rect outRect) {
        getBoundsOnScreen(outRect, false);
    }

    @UnsupportedAppUsage
    public void getBoundsOnScreen(Rect outRect, boolean clipToParent) {
        if (this.mAttachInfo != null) {
            RectF position = this.mAttachInfo.mTmpTransformRect;
            position.set(0.0f, 0.0f, (float) (this.mRight - this.mLeft), (float) (this.mBottom - this.mTop));
            mapRectFromViewToScreenCoords(position, clipToParent);
            outRect.set(Math.round(position.left), Math.round(position.top), Math.round(position.right), Math.round(position.bottom));
        }
    }

    public void mapRectFromViewToScreenCoords(RectF rect, boolean clipToParent) {
        if (!hasIdentityMatrix()) {
            getMatrix().mapRect(rect);
        }
        rect.offset((float) this.mLeft, (float) this.mTop);
        ViewParent parent = this.mParent;
        while (parent instanceof View) {
            View parentView = (View) parent;
            rect.offset((float) (-parentView.mScrollX), (float) (-parentView.mScrollY));
            if (clipToParent) {
                rect.left = Math.max(rect.left, 0.0f);
                rect.top = Math.max(rect.top, 0.0f);
                rect.right = Math.min(rect.right, (float) parentView.getWidth());
                rect.bottom = Math.min(rect.bottom, (float) parentView.getHeight());
            }
            if (!parentView.hasIdentityMatrix()) {
                parentView.getMatrix().mapRect(rect);
            }
            rect.offset((float) parentView.mLeft, (float) parentView.mTop);
            parent = parentView.mParent;
        }
        if (parent instanceof ViewRootImpl) {
            rect.offset(0.0f, (float) (-((ViewRootImpl) parent).mCurScrollY));
        }
        rect.offset((float) this.mAttachInfo.mWindowLeft, (float) this.mAttachInfo.mWindowTop);
    }

    public CharSequence getAccessibilityClassName() {
        return View.class.getName();
    }

    public void onProvideStructure(ViewStructure structure) {
        onProvideStructure(structure, 0, 0);
    }

    public void onProvideAutofillStructure(ViewStructure structure, int flags) {
        onProvideStructure(structure, 1, flags);
    }

    /* JADX WARNING: type inference failed for: r5v1, types: [android.view.ViewParent] */
    /* JADX WARNING: type inference failed for: r5v2, types: [android.view.ViewParent] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onProvideStructure(android.view.ViewStructure r15, int r16, int r17) {
        /*
            r14 = this;
            r1 = r14
            r9 = r15
            r10 = r16
            int r11 = r1.mID
            r2 = 0
            r0 = -1
            if (r11 == r0) goto L_0x002c
            boolean r0 = isViewIdGenerated(r11)
            if (r0 != 0) goto L_0x002c
            android.content.res.Resources r0 = r14.getResources()     // Catch:{ NotFoundException -> 0x0024 }
            java.lang.String r3 = r0.getResourceEntryName(r11)     // Catch:{ NotFoundException -> 0x0024 }
            java.lang.String r4 = r0.getResourceTypeName(r11)     // Catch:{ NotFoundException -> 0x0024 }
            java.lang.String r5 = r0.getResourcePackageName(r11)     // Catch:{ NotFoundException -> 0x0024 }
            r0 = r5
            r2 = r3
            goto L_0x0028
        L_0x0024:
            r0 = move-exception
            r3 = r2
            r4 = r2
            r0 = r3
        L_0x0028:
            r15.setId(r11, r0, r4, r2)
            goto L_0x002f
        L_0x002c:
            r15.setId(r11, r2, r2, r2)
        L_0x002f:
            r0 = 1
            if (r10 == r0) goto L_0x0035
            r2 = 2
            if (r10 != r2) goto L_0x0053
        L_0x0035:
            int r2 = r14.getAutofillType()
            if (r2 == 0) goto L_0x004c
            r15.setAutofillType(r2)
            java.lang.String[] r3 = r14.getAutofillHints()
            r15.setAutofillHints(r3)
            android.view.autofill.AutofillValue r3 = r14.getAutofillValue()
            r15.setAutofillValue(r3)
        L_0x004c:
            int r3 = r14.getImportantForAutofill()
            r15.setImportantForAutofill(r3)
        L_0x0053:
            r2 = 0
            r3 = 0
            if (r10 != r0) goto L_0x0081
            r4 = r17 & 1
            if (r4 != 0) goto L_0x0081
            r4 = 0
            android.view.ViewParent r5 = r14.getParent()
            boolean r6 = r5 instanceof android.view.View
            if (r6 == 0) goto L_0x0067
            r4 = r5
            android.view.View r4 = (android.view.View) r4
        L_0x0067:
            if (r4 == 0) goto L_0x0081
            boolean r6 = r4.isImportantForAutofill()
            if (r6 != 0) goto L_0x0081
            int r6 = r4.mLeft
            int r2 = r2 + r6
            int r6 = r4.mTop
            int r3 = r3 + r6
            android.view.ViewParent r5 = r4.getParent()
            boolean r6 = r5 instanceof android.view.View
            if (r6 == 0) goto L_0x0081
            r4 = r5
            android.view.View r4 = (android.view.View) r4
            goto L_0x0067
        L_0x0081:
            r12 = r2
            r13 = r3
            int r2 = r1.mLeft
            int r3 = r12 + r2
            int r2 = r1.mTop
            int r4 = r13 + r2
            int r5 = r1.mScrollX
            int r6 = r1.mScrollY
            int r2 = r1.mRight
            int r7 = r1.mLeft
            int r7 = r2 - r7
            int r2 = r1.mBottom
            int r8 = r1.mTop
            int r8 = r2 - r8
            r2 = r15
            r2.setDimens(r3, r4, r5, r6, r7, r8)
            if (r10 != 0) goto L_0x00b5
            boolean r2 = r14.hasIdentityMatrix()
            if (r2 != 0) goto L_0x00ae
            android.graphics.Matrix r2 = r14.getMatrix()
            r15.setTransformation(r2)
        L_0x00ae:
            float r2 = r14.getZ()
            r15.setElevation(r2)
        L_0x00b5:
            int r2 = r14.getVisibility()
            r15.setVisibility(r2)
            boolean r2 = r14.isEnabled()
            r15.setEnabled(r2)
            boolean r2 = r14.isClickable()
            if (r2 == 0) goto L_0x00cc
            r15.setClickable(r0)
        L_0x00cc:
            boolean r2 = r14.isFocusable()
            if (r2 == 0) goto L_0x00d5
            r15.setFocusable(r0)
        L_0x00d5:
            boolean r2 = r14.isFocused()
            if (r2 == 0) goto L_0x00de
            r15.setFocused(r0)
        L_0x00de:
            boolean r2 = r14.isAccessibilityFocused()
            if (r2 == 0) goto L_0x00e7
            r15.setAccessibilityFocused(r0)
        L_0x00e7:
            boolean r2 = r14.isSelected()
            if (r2 == 0) goto L_0x00f0
            r15.setSelected(r0)
        L_0x00f0:
            boolean r2 = r14.isActivated()
            if (r2 == 0) goto L_0x00f9
            r15.setActivated(r0)
        L_0x00f9:
            boolean r2 = r14.isLongClickable()
            if (r2 == 0) goto L_0x0102
            r15.setLongClickable(r0)
        L_0x0102:
            boolean r2 = r1 instanceof android.widget.Checkable
            if (r2 == 0) goto L_0x0115
            r15.setCheckable(r0)
            r2 = r1
            android.widget.Checkable r2 = (android.widget.Checkable) r2
            boolean r2 = r2.isChecked()
            if (r2 == 0) goto L_0x0115
            r15.setChecked(r0)
        L_0x0115:
            boolean r2 = r14.isOpaque()
            if (r2 == 0) goto L_0x011e
            r15.setOpaque(r0)
        L_0x011e:
            boolean r2 = r14.isContextClickable()
            if (r2 == 0) goto L_0x0127
            r15.setContextClickable(r0)
        L_0x0127:
            java.lang.CharSequence r0 = r14.getAccessibilityClassName()
            java.lang.String r0 = r0.toString()
            r15.setClassName(r0)
            java.lang.CharSequence r0 = r14.getContentDescription()
            r15.setContentDescription(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.onProvideStructure(android.view.ViewStructure, int, int):void");
    }

    public void onProvideVirtualStructure(ViewStructure structure) {
        onProvideVirtualStructureCompat(structure, false);
    }

    private void onProvideVirtualStructureCompat(ViewStructure structure, boolean forAutofill) {
        AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
        if (provider != null) {
            if (forAutofill && Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.v(AUTOFILL_LOG_TAG, "onProvideVirtualStructureCompat() for " + this);
            }
            AccessibilityNodeInfo info = createAccessibilityNodeInfo();
            structure.setChildCount(1);
            populateVirtualStructure(structure.newChild(0), provider, info, forAutofill);
            info.recycle();
        }
    }

    public void onProvideAutofillVirtualStructure(ViewStructure structure, int flags) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            onProvideVirtualStructureCompat(structure, true);
        }
    }

    public void autofill(AutofillValue value) {
    }

    public void autofill(SparseArray<AutofillValue> values) {
        AccessibilityNodeProvider provider;
        if (this.mContext.isAutofillCompatibilityEnabled() && (provider = getAccessibilityNodeProvider()) != null) {
            int valueCount = values.size();
            for (int i = 0; i < valueCount; i++) {
                AutofillValue value = values.valueAt(i);
                if (value.isText()) {
                    int virtualId = values.keyAt(i);
                    CharSequence text = value.getTextValue();
                    Bundle arguments = new Bundle();
                    arguments.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", text);
                    provider.performAction(virtualId, 2097152, arguments);
                }
            }
        }
    }

    public final AutofillId getAutofillId() {
        if (this.mAutofillId == null) {
            this.mAutofillId = new AutofillId(getAutofillViewId());
        }
        return this.mAutofillId;
    }

    public void setAutofillId(AutofillId id) {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.v(AUTOFILL_LOG_TAG, "setAutofill(): from " + this.mAutofillId + " to " + id);
        }
        if (isAttachedToWindow()) {
            throw new IllegalStateException("Cannot set autofill id when view is attached");
        } else if (id != null && !id.isNonVirtual()) {
            throw new IllegalStateException("Cannot set autofill id assigned to virtual views");
        } else if (id != null || (this.mPrivateFlags3 & 1073741824) != 0) {
            this.mAutofillId = id;
            if (id != null) {
                this.mAutofillViewId = id.getViewId();
                this.mPrivateFlags3 = 1073741824 | this.mPrivateFlags3;
                return;
            }
            this.mAutofillViewId = -1;
            this.mPrivateFlags3 &= -1073741825;
        }
    }

    public int getAutofillType() {
        return 0;
    }

    @ViewDebug.ExportedProperty
    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public boolean isAutofilled() {
        return (this.mPrivateFlags3 & 65536) != 0;
    }

    public AutofillValue getAutofillValue() {
        return null;
    }

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "auto"), @ViewDebug.IntToString(from = 1, to = "yes"), @ViewDebug.IntToString(from = 2, to = "no"), @ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"), @ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")})
    public int getImportantForAutofill() {
        return (this.mPrivateFlags3 & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK) >> 19;
    }

    public void setImportantForAutofill(int mode) {
        this.mPrivateFlags3 &= -7864321;
        this.mPrivateFlags3 |= (mode << 19) & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK;
    }

    public final boolean isImportantForAutofill() {
        for (ViewParent parent = this.mParent; parent instanceof View; parent = parent.getParent()) {
            int parentImportance = ((View) parent).getImportantForAutofill();
            if (parentImportance == 8 || parentImportance == 4) {
                if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                    Log.v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because parent " + parent + "'s importance is " + parentImportance);
                }
                return false;
            }
        }
        int importance = getImportantForAutofill();
        if (importance == 4 || importance == 1) {
            return true;
        }
        if (importance == 8 || importance == 2) {
            if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because its importance is " + importance);
            }
            return false;
        } else if (importance != 0) {
            Log.w(AUTOFILL_LOG_TAG, "invalid autofill importance (" + importance + " on view " + this);
            return false;
        } else {
            int id = this.mID;
            if (id != -1 && !isViewIdGenerated(id)) {
                Resources res = getResources();
                String entry = null;
                String pkg = null;
                try {
                    entry = res.getResourceEntryName(id);
                    pkg = res.getResourcePackageName(id);
                } catch (Resources.NotFoundException e) {
                }
                if (!(entry == null || pkg == null || !pkg.equals(this.mContext.getPackageName()))) {
                    return true;
                }
            }
            if (getAutofillHints() != null) {
                return true;
            }
            return false;
        }
    }

    public void setContentCaptureSession(ContentCaptureSession contentCaptureSession) {
        this.mContentCaptureSession = contentCaptureSession;
    }

    public final ContentCaptureSession getContentCaptureSession() {
        if (this.mCachedContentCaptureSession != null) {
            return this.mCachedContentCaptureSession;
        }
        this.mCachedContentCaptureSession = getAndCacheContentCaptureSession();
        return this.mCachedContentCaptureSession;
    }

    private ContentCaptureSession getAndCacheContentCaptureSession() {
        if (this.mContentCaptureSession != null) {
            return this.mContentCaptureSession;
        }
        ContentCaptureSession session = null;
        if (this.mParent instanceof View) {
            session = ((View) this.mParent).getContentCaptureSession();
        }
        if (session != null) {
            return session;
        }
        ContentCaptureManager ccm = (ContentCaptureManager) this.mContext.getSystemService(ContentCaptureManager.class);
        if (ccm == null) {
            return null;
        }
        return ccm.getMainContentCaptureSession();
    }

    private AutofillManager getAutofillManager() {
        return (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
    }

    private boolean isAutofillable() {
        AutofillManager afm;
        if (getAutofillType() == 0) {
            return false;
        }
        if (!isImportantForAutofill()) {
            AutofillOptions options = this.mContext.getAutofillOptions();
            if (options == null || !options.isAugmentedAutofillEnabled(this.mContext) || (afm = getAutofillManager()) == null) {
                return false;
            }
            afm.notifyViewEnteredForAugmentedAutofill(this);
        }
        if (getAutofillViewId() > 1073741823) {
            return true;
        }
        return false;
    }

    public boolean canNotifyAutofillEnterExitEvent() {
        return isAutofillable() && isAttachedToWindow();
    }

    private void populateVirtualStructure(ViewStructure structure, AccessibilityNodeProvider provider, AccessibilityNodeInfo info, boolean forAutofill) {
        String str = null;
        structure.setId(AccessibilityNodeInfo.getVirtualDescendantId(info.getSourceNodeId()), (String) null, (String) null, info.getViewIdResourceName());
        Rect rect = structure.getTempRect();
        info.getBoundsInParent(rect);
        structure.setDimens(rect.left, rect.top, 0, 0, rect.width(), rect.height());
        structure.setVisibility(0);
        structure.setEnabled(info.isEnabled());
        if (info.isClickable()) {
            structure.setClickable(true);
        }
        if (info.isFocusable()) {
            structure.setFocusable(true);
        }
        if (info.isFocused()) {
            structure.setFocused(true);
        }
        if (info.isAccessibilityFocused()) {
            structure.setAccessibilityFocused(true);
        }
        if (info.isSelected()) {
            structure.setSelected(true);
        }
        if (info.isLongClickable()) {
            structure.setLongClickable(true);
        }
        if (info.isCheckable()) {
            structure.setCheckable(true);
            if (info.isChecked()) {
                structure.setChecked(true);
            }
        }
        if (info.isContextClickable()) {
            structure.setContextClickable(true);
        }
        if (forAutofill) {
            structure.setAutofillId(new AutofillId(getAutofillId(), AccessibilityNodeInfo.getVirtualDescendantId(info.getSourceNodeId())));
        }
        CharSequence cname = info.getClassName();
        if (cname != null) {
            str = cname.toString();
        }
        structure.setClassName(str);
        structure.setContentDescription(info.getContentDescription());
        if (forAutofill) {
            int maxTextLength = info.getMaxTextLength();
            if (maxTextLength != -1) {
                structure.setMaxTextLength(maxTextLength);
            }
            structure.setHint(info.getHintText());
        }
        CharSequence text = info.getText();
        boolean hasText = (text == null && info.getError() == null) ? false : true;
        if (hasText) {
            structure.setText(text, info.getTextSelectionStart(), info.getTextSelectionEnd());
        }
        if (forAutofill) {
            if (info.isEditable()) {
                structure.setDataIsSensitive(true);
                if (hasText) {
                    structure.setAutofillType(1);
                    structure.setAutofillValue(AutofillValue.forText(text));
                }
                int inputType = info.getInputType();
                if (inputType == 0 && info.isPassword()) {
                    inputType = 129;
                }
                structure.setInputType(inputType);
            } else {
                structure.setDataIsSensitive(false);
            }
        }
        int NCHILDREN = info.getChildCount();
        if (NCHILDREN > 0) {
            structure.setChildCount(NCHILDREN);
            for (int i = 0; i < NCHILDREN; i++) {
                if (AccessibilityNodeInfo.getVirtualDescendantId(info.getChildNodeIds().get(i)) == -1) {
                    Log.e(VIEW_LOG_TAG, "Virtual view pointing to its host. Ignoring");
                } else {
                    AccessibilityNodeInfo cinfo = provider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(info.getChildId(i)));
                    populateVirtualStructure(structure.newChild(i), provider, cinfo, forAutofill);
                    cinfo.recycle();
                }
            }
        }
    }

    public void dispatchProvideStructure(ViewStructure structure) {
        dispatchProvideStructure(structure, 0, 0);
    }

    public void dispatchProvideAutofillStructure(ViewStructure structure, int flags) {
        dispatchProvideStructure(structure, 1, flags);
    }

    private void dispatchProvideStructure(ViewStructure structure, int viewFor, int flags) {
        if (viewFor == 1) {
            structure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(structure, flags);
            onProvideAutofillVirtualStructure(structure, flags);
        } else if (!isAssistBlocked()) {
            onProvideStructure(structure);
            onProvideVirtualStructure(structure);
        } else {
            structure.setClassName(getAccessibilityClassName().toString());
            structure.setAssistBlocked(true);
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        AccessibilityNodeInfo.AccessibilityAction accessibilityAction;
        if (this.mAttachInfo != null) {
            Rect bounds = this.mAttachInfo.mTmpInvalRect;
            getDrawingRect(bounds);
            info.setBoundsInParent(bounds);
            getBoundsOnScreen(bounds, true);
            info.setBoundsInScreen(bounds);
            ViewParent parent = getParentForAccessibility();
            if (parent instanceof View) {
                info.setParent((View) parent);
            }
            if (this.mID != -1) {
                View rootView = getRootView();
                if (rootView == null) {
                    rootView = this;
                }
                View label = rootView.findLabelForView(this, this.mID);
                if (label != null) {
                    info.setLabeledBy(label);
                }
                if ((this.mAttachInfo.mAccessibilityFetchFlags & 16) != 0 && Resources.resourceHasPackage(this.mID)) {
                    try {
                        info.setViewIdResourceName(getResources().getResourceName(this.mID));
                    } catch (Resources.NotFoundException e) {
                    }
                }
            }
            if (this.mLabelForId != -1) {
                View rootView2 = getRootView();
                if (rootView2 == null) {
                    rootView2 = this;
                }
                View labeled = rootView2.findViewInsideOutShouldExist(this, this.mLabelForId);
                if (labeled != null) {
                    info.setLabelFor(labeled);
                }
            }
            if (this.mAccessibilityTraversalBeforeId != -1) {
                View rootView3 = getRootView();
                if (rootView3 == null) {
                    rootView3 = this;
                }
                View next = rootView3.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalBeforeId);
                if (next != null && next.includeForAccessibility()) {
                    info.setTraversalBefore(next);
                }
            }
            if (this.mAccessibilityTraversalAfterId != -1) {
                View rootView4 = getRootView();
                if (rootView4 == null) {
                    rootView4 = this;
                }
                View next2 = rootView4.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalAfterId);
                if (next2 != null && next2.includeForAccessibility()) {
                    info.setTraversalAfter(next2);
                }
            }
            info.setVisibleToUser(isVisibleToUser());
            info.setImportantForAccessibility(isImportantForAccessibility());
            info.setPackageName(this.mContext.getPackageName());
            info.setClassName(getAccessibilityClassName());
            info.setContentDescription(getContentDescription());
            info.setEnabled(isEnabled());
            info.setClickable(isClickable());
            info.setFocusable(isFocusable());
            info.setScreenReaderFocusable(isScreenReaderFocusable());
            info.setFocused(isFocused());
            info.setAccessibilityFocused(isAccessibilityFocused());
            info.setSelected(isSelected());
            info.setLongClickable(isLongClickable());
            info.setContextClickable(isContextClickable());
            info.setLiveRegion(getAccessibilityLiveRegion());
            if (!(this.mTooltipInfo == null || this.mTooltipInfo.mTooltipText == null)) {
                info.setTooltipText(this.mTooltipInfo.mTooltipText);
                if (this.mTooltipInfo.mTooltipPopup == null) {
                    accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP;
                } else {
                    accessibilityAction = AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
                }
                info.addAction(accessibilityAction);
            }
            info.addAction(4);
            info.addAction(8);
            if (isFocusable()) {
                if (isFocused()) {
                    info.addAction(2);
                } else {
                    info.addAction(1);
                }
            }
            if (!isAccessibilityFocused()) {
                info.addAction(64);
            } else {
                info.addAction(128);
            }
            if (isClickable() && isEnabled()) {
                info.addAction(16);
            }
            if (isLongClickable() && isEnabled()) {
                info.addAction(32);
            }
            if (isContextClickable() && isEnabled()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);
            }
            CharSequence text = getIterableTextForAccessibility();
            if (text != null && text.length() > 0) {
                info.setTextSelection(getAccessibilitySelectionStart(), getAccessibilitySelectionEnd());
                info.addAction(131072);
                info.addAction(256);
                info.addAction(512);
                info.setMovementGranularities(11);
            }
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
            populateAccessibilityNodeInfoDrawingOrderInParent(info);
            info.setPaneTitle(this.mAccessibilityPaneTitle);
            info.setHeading(isAccessibilityHeading());
            if (this.mTouchDelegate != null) {
                info.setTouchDelegateInfo(this.mTouchDelegate.getTouchDelegateInfo());
            }
        }
    }

    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
    }

    private void populateAccessibilityNodeInfoDrawingOrderInParent(AccessibilityNodeInfo info) {
        AccessibilityNodeInfo accessibilityNodeInfo = info;
        if ((this.mPrivateFlags & 16) == 0) {
            accessibilityNodeInfo.setDrawingOrder(0);
            return;
        }
        int drawingOrderInParent = 1;
        View viewAtDrawingLevel = this;
        ViewParent parent = getParentForAccessibility();
        while (true) {
            if (viewAtDrawingLevel == parent) {
                break;
            }
            ViewParent currentParent = viewAtDrawingLevel.getParent();
            if (!(currentParent instanceof ViewGroup)) {
                drawingOrderInParent = 0;
                break;
            }
            ViewGroup parentGroup = (ViewGroup) currentParent;
            int childCount = parentGroup.getChildCount();
            if (childCount > 1) {
                List<View> preorderedList = parentGroup.buildOrderedChildList();
                if (preorderedList != null) {
                    int childDrawIndex = preorderedList.indexOf(viewAtDrawingLevel);
                    int drawingOrderInParent2 = drawingOrderInParent;
                    for (int i = 0; i < childDrawIndex; i++) {
                        drawingOrderInParent2 += numViewsForAccessibility(preorderedList.get(i));
                    }
                    drawingOrderInParent = drawingOrderInParent2;
                } else {
                    int childIndex = parentGroup.indexOfChild(viewAtDrawingLevel);
                    boolean customOrder = parentGroup.isChildrenDrawingOrderEnabled();
                    int childDrawIndex2 = (childIndex < 0 || !customOrder) ? childIndex : parentGroup.getChildDrawingOrder(childCount, childIndex);
                    int numChildrenToIterate = customOrder ? childCount : childDrawIndex2;
                    if (childDrawIndex2 != 0) {
                        int drawingOrderInParent3 = drawingOrderInParent;
                        for (int i2 = 0; i2 < numChildrenToIterate; i2++) {
                            if ((customOrder ? parentGroup.getChildDrawingOrder(childCount, i2) : i2) < childDrawIndex2) {
                                drawingOrderInParent3 += numViewsForAccessibility(parentGroup.getChildAt(i2));
                            }
                        }
                        drawingOrderInParent = drawingOrderInParent3;
                    }
                }
            }
            viewAtDrawingLevel = (View) currentParent;
        }
        accessibilityNodeInfo.setDrawingOrder(drawingOrderInParent);
    }

    private static int numViewsForAccessibility(View view) {
        if (view == null) {
            return 0;
        }
        if (view.includeForAccessibility()) {
            return 1;
        }
        if (view instanceof ViewGroup) {
            return ((ViewGroup) view).getNumChildrenForAccessibility();
        }
        return 0;
    }

    private View findLabelForView(View view, int labeledId) {
        if (this.mMatchLabelForPredicate == null) {
            this.mMatchLabelForPredicate = new MatchLabelForPredicate();
        }
        int unused = this.mMatchLabelForPredicate.mLabeledId = labeledId;
        return findViewByPredicateInsideOut(view, this.mMatchLabelForPredicate);
    }

    public boolean isVisibleToUserForAutofill(int virtualId) {
        if (!this.mContext.isAutofillCompatibilityEnabled()) {
            return true;
        }
        AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
        if (provider != null) {
            AccessibilityNodeInfo node = provider.createAccessibilityNodeInfo(virtualId);
            if (node != null) {
                return node.isVisibleToUser();
            }
            return false;
        }
        Log.w(VIEW_LOG_TAG, "isVisibleToUserForAutofill(" + virtualId + "): no provider");
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVisibleToUser() {
        return isVisibleToUser((Rect) null);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isVisibleToUser(android.graphics.Rect r6) {
        /*
            r5 = this;
            android.view.View$AttachInfo r0 = r5.mAttachInfo
            r1 = 0
            if (r0 == 0) goto L_0x0051
            android.view.View$AttachInfo r0 = r5.mAttachInfo
            int r0 = r0.mWindowVisibility
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            r0 = r5
        L_0x000d:
            boolean r2 = r0 instanceof android.view.View
            if (r2 == 0) goto L_0x0030
            r2 = r0
            android.view.View r2 = (android.view.View) r2
            float r3 = r2.getAlpha()
            r4 = 0
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x002f
            float r3 = r2.getTransitionAlpha()
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 <= 0) goto L_0x002f
            int r3 = r2.getVisibility()
            if (r3 == 0) goto L_0x002c
            goto L_0x002f
        L_0x002c:
            android.view.ViewParent r0 = r2.mParent
            goto L_0x000d
        L_0x002f:
            return r1
        L_0x0030:
            android.view.View$AttachInfo r2 = r5.mAttachInfo
            android.graphics.Rect r2 = r2.mTmpInvalRect
            android.view.View$AttachInfo r3 = r5.mAttachInfo
            android.graphics.Point r3 = r3.mPoint
            boolean r4 = r5.getGlobalVisibleRect(r2, r3)
            if (r4 != 0) goto L_0x003f
            return r1
        L_0x003f:
            if (r6 == 0) goto L_0x004f
            int r1 = r3.x
            int r1 = -r1
            int r4 = r3.y
            int r4 = -r4
            r2.offset(r1, r4)
            boolean r1 = r6.intersect(r2)
            return r1
        L_0x004f:
            r1 = 1
            return r1
        L_0x0051:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.isVisibleToUser(android.graphics.Rect):boolean");
    }

    public AccessibilityDelegate getAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegate(AccessibilityDelegate delegate) {
        this.mAccessibilityDelegate = delegate;
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.getAccessibilityNodeProvider(this);
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getAccessibilityViewId() {
        if (this.mAccessibilityViewId == -1) {
            int i = sNextAccessibilityViewId;
            sNextAccessibilityViewId = i + 1;
            this.mAccessibilityViewId = i;
        }
        return this.mAccessibilityViewId;
    }

    public int getAutofillViewId() {
        if (this.mAutofillViewId == -1) {
            this.mAutofillViewId = this.mContext.getNextAutofillId();
        }
        return this.mAutofillViewId;
    }

    public int getAccessibilityWindowId() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mAccessibilityWindowId;
        }
        return -1;
    }

    @ViewDebug.ExportedProperty(category = "accessibility")
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @RemotableViewMethod
    public void setContentDescription(CharSequence contentDescription) {
        if (this.mContentDescription == null) {
            if (contentDescription == null) {
                return;
            }
        } else if (this.mContentDescription.equals(contentDescription)) {
            return;
        }
        this.mContentDescription = contentDescription;
        if (!(contentDescription != null && contentDescription.length() > 0) || getImportantForAccessibility() != 0) {
            notifyViewAccessibilityStateChangedIfNeeded(4);
            return;
        }
        setImportantForAccessibility(1);
        notifySubtreeAccessibilityStateChangedIfNeeded();
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalBefore(int beforeId) {
        if (this.mAccessibilityTraversalBeforeId != beforeId) {
            this.mAccessibilityTraversalBeforeId = beforeId;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getAccessibilityTraversalBefore() {
        return this.mAccessibilityTraversalBeforeId;
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalAfter(int afterId) {
        if (this.mAccessibilityTraversalAfterId != afterId) {
            this.mAccessibilityTraversalAfterId = afterId;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getAccessibilityTraversalAfter() {
        return this.mAccessibilityTraversalAfterId;
    }

    @ViewDebug.ExportedProperty(category = "accessibility")
    public int getLabelFor() {
        return this.mLabelForId;
    }

    @RemotableViewMethod
    public void setLabelFor(int id) {
        if (this.mLabelForId != id) {
            this.mLabelForId = id;
            if (this.mLabelForId != -1 && this.mID == -1) {
                this.mID = generateViewId();
            }
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void onFocusLost() {
        resetPressedState();
    }

    private void resetPressedState() {
        if ((this.mViewFlags & 32) != 32 && isPressed()) {
            setPressed(false);
            if (!this.mHasPerformedLongPress) {
                removeLongPressCallback();
            }
        }
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return (this.mPrivateFlags & 2) != 0;
    }

    public View findFocus() {
        if ((this.mPrivateFlags & 2) != 0) {
            return this;
        }
        return null;
    }

    public boolean isScrollContainer() {
        return (this.mPrivateFlags & 1048576) != 0;
    }

    public void setScrollContainer(boolean isScrollContainer) {
        if (isScrollContainer) {
            if (this.mAttachInfo != null && (this.mPrivateFlags & 1048576) == 0) {
                this.mAttachInfo.mScrollContainers.add(this);
                this.mPrivateFlags = 1048576 | this.mPrivateFlags;
            }
            this.mPrivateFlags |= 524288;
            return;
        }
        if ((1048576 & this.mPrivateFlags) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
        }
        this.mPrivateFlags &= -1572865;
    }

    @Deprecated
    public int getDrawingCacheQuality() {
        return this.mViewFlags & DRAWING_CACHE_QUALITY_MASK;
    }

    @Deprecated
    public void setDrawingCacheQuality(int quality) {
        setFlags(quality, DRAWING_CACHE_QUALITY_MASK);
    }

    public boolean getKeepScreenOn() {
        return (this.mViewFlags & 67108864) != 0;
    }

    public void setKeepScreenOn(boolean keepScreenOn) {
        setFlags(keepScreenOn ? 67108864 : 0, 67108864);
    }

    public int getNextFocusLeftId() {
        return this.mNextFocusLeftId;
    }

    public void setNextFocusLeftId(int nextFocusLeftId) {
        this.mNextFocusLeftId = nextFocusLeftId;
    }

    public int getNextFocusRightId() {
        return this.mNextFocusRightId;
    }

    public void setNextFocusRightId(int nextFocusRightId) {
        this.mNextFocusRightId = nextFocusRightId;
    }

    public int getNextFocusUpId() {
        return this.mNextFocusUpId;
    }

    public void setNextFocusUpId(int nextFocusUpId) {
        this.mNextFocusUpId = nextFocusUpId;
    }

    public int getNextFocusDownId() {
        return this.mNextFocusDownId;
    }

    public void setNextFocusDownId(int nextFocusDownId) {
        this.mNextFocusDownId = nextFocusDownId;
    }

    public int getNextFocusForwardId() {
        return this.mNextFocusForwardId;
    }

    public void setNextFocusForwardId(int nextFocusForwardId) {
        this.mNextFocusForwardId = nextFocusForwardId;
    }

    public int getNextClusterForwardId() {
        return this.mNextClusterForwardId;
    }

    public void setNextClusterForwardId(int nextClusterForwardId) {
        this.mNextClusterForwardId = nextClusterForwardId;
    }

    public boolean isShown() {
        ViewParent parent;
        View current = this;
        while ((current.mViewFlags & 12) == 0 && (parent = current.mParent) != null) {
            if (!(parent instanceof View)) {
                return true;
            }
            current = (View) parent;
            if (current == null) {
                return false;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean fitSystemWindows(Rect insets) {
        if ((this.mPrivateFlags3 & 32) != 0) {
            return fitSystemWindowsInt(insets);
        }
        if (insets == null) {
            return false;
        }
        try {
            this.mPrivateFlags3 |= 64;
            return dispatchApplyWindowInsets(new WindowInsets(insets)).isConsumed();
        } finally {
            this.mPrivateFlags3 &= -65;
        }
    }

    private boolean fitSystemWindowsInt(Rect insets) {
        if ((this.mViewFlags & 2) != 2) {
            return false;
        }
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        Rect localInsets = sThreadLocal.get();
        if (localInsets == null) {
            localInsets = new Rect();
            sThreadLocal.set(localInsets);
        }
        boolean res = computeFitSystemWindows(insets, localInsets);
        this.mUserPaddingLeftInitial = localInsets.left;
        this.mUserPaddingRightInitial = localInsets.right;
        internalSetPadding(localInsets.left, localInsets.top, localInsets.right, localInsets.bottom);
        return res;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if ((this.mPrivateFlags3 & 64) == 0) {
            if (fitSystemWindows(insets.getSystemWindowInsetsAsRect())) {
                return insets.consumeSystemWindowInsets();
            }
        } else if (fitSystemWindowsInt(insets.getSystemWindowInsetsAsRect())) {
            return insets.consumeSystemWindowInsets();
        }
        return insets;
    }

    public void setOnApplyWindowInsetsListener(OnApplyWindowInsetsListener listener) {
        getListenerInfo().mOnApplyWindowInsetsListener = listener;
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        try {
            this.mPrivateFlags3 |= 32;
            if (this.mListenerInfo != null && this.mListenerInfo.mOnApplyWindowInsetsListener != null) {
                return this.mListenerInfo.mOnApplyWindowInsetsListener.onApplyWindowInsets(this, insets);
            }
            WindowInsets onApplyWindowInsets = onApplyWindowInsets(insets);
            this.mPrivateFlags3 &= -33;
            return onApplyWindowInsets;
        } finally {
            this.mPrivateFlags3 &= -33;
        }
    }

    public void setWindowInsetsAnimationListener(WindowInsetsAnimationListener listener) {
        WindowInsetsAnimationListener unused = getListenerInfo().mWindowInsetsAnimationListener = listener;
    }

    /* access modifiers changed from: package-private */
    public void dispatchWindowInsetsAnimationStarted(WindowInsetsAnimationListener.InsetsAnimation animation) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationListener != null) {
            this.mListenerInfo.mWindowInsetsAnimationListener.onStarted(animation);
        }
    }

    /* access modifiers changed from: package-private */
    public WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets insets) {
        if (this.mListenerInfo == null || this.mListenerInfo.mWindowInsetsAnimationListener == null) {
            return insets;
        }
        return this.mListenerInfo.mWindowInsetsAnimationListener.onProgress(insets);
    }

    /* access modifiers changed from: package-private */
    public void dispatchWindowInsetsAnimationFinished(WindowInsetsAnimationListener.InsetsAnimation animation) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationListener != null) {
            this.mListenerInfo.mWindowInsetsAnimationListener.onFinished(animation);
        }
    }

    public void setSystemGestureExclusionRects(List<Rect> rects) {
        if (!rects.isEmpty() || this.mListenerInfo != null) {
            ListenerInfo info = getListenerInfo();
            if (rects.isEmpty()) {
                List unused = info.mSystemGestureExclusionRects = null;
                if (info.mPositionUpdateListener != null) {
                    this.mRenderNode.removePositionUpdateListener(info.mPositionUpdateListener);
                }
            } else {
                List unused2 = info.mSystemGestureExclusionRects = rects;
                if (info.mPositionUpdateListener == null) {
                    info.mPositionUpdateListener = new RenderNode.PositionUpdateListener() {
                        public void positionChanged(long n, int l, int t, int r, int b) {
                            View.this.postUpdateSystemGestureExclusionRects();
                        }

                        public void positionLost(long frameNumber) {
                            View.this.postUpdateSystemGestureExclusionRects();
                        }
                    };
                    this.mRenderNode.addPositionUpdateListener(info.mPositionUpdateListener);
                }
            }
            postUpdateSystemGestureExclusionRects();
        }
    }

    /* access modifiers changed from: package-private */
    public void postUpdateSystemGestureExclusionRects() {
        Handler h = getHandler();
        if (h != null) {
            h.postAtFrontOfQueue(new Runnable() {
                public final void run() {
                    View.this.updateSystemGestureExclusionRects();
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void updateSystemGestureExclusionRects() {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewRootImpl.updateSystemGestureExclusionRectsForView(this);
        }
    }

    public List<Rect> getSystemGestureExclusionRects() {
        List<Rect> list;
        ListenerInfo info = this.mListenerInfo;
        if (info == null || (list = info.mSystemGestureExclusionRects) == null) {
            return Collections.emptyList();
        }
        return list;
    }

    public void getLocationInSurface(int[] location) {
        getLocationInWindow(location);
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRootImpl != null) {
            location[0] = location[0] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.left;
            location[1] = location[1] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.top;
        }
    }

    public WindowInsets getRootWindowInsets() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl.getWindowInsets(false);
        }
        return null;
    }

    public WindowInsetsController getWindowInsetsController() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl.getInsetsController();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    @UnsupportedAppUsage
    public boolean computeFitSystemWindows(Rect inoutInsets, Rect outLocalInsets) {
        WindowInsets innerInsets = computeSystemWindowInsets(new WindowInsets(inoutInsets), outLocalInsets);
        inoutInsets.set(innerInsets.getSystemWindowInsetsAsRect());
        return innerInsets.isSystemWindowInsetsConsumed();
    }

    public WindowInsets computeSystemWindowInsets(WindowInsets in, Rect outLocalInsets) {
        if ((this.mViewFlags & 2048) == 0 || this.mAttachInfo == null || ((this.mAttachInfo.mSystemUiVisibility & 1536) == 0 && !this.mAttachInfo.mOverscanRequested)) {
            outLocalInsets.set(in.getSystemWindowInsetsAsRect());
            return in.consumeSystemWindowInsets().inset(outLocalInsets);
        }
        outLocalInsets.set(this.mAttachInfo.mOverscanInsets);
        return in.inset(outLocalInsets);
    }

    public void setFitsSystemWindows(boolean fitSystemWindows) {
        setFlags(fitSystemWindows ? 2 : 0, 2);
    }

    @ViewDebug.ExportedProperty
    public boolean getFitsSystemWindows() {
        return (this.mViewFlags & 2) == 2;
    }

    @UnsupportedAppUsage
    public boolean fitsSystemWindows() {
        return getFitsSystemWindows();
    }

    @Deprecated
    public void requestFitSystemWindows() {
        if (this.mParent != null) {
            this.mParent.requestFitSystemWindows();
        }
    }

    public void requestApplyInsets() {
        requestFitSystemWindows();
    }

    @UnsupportedAppUsage
    public void makeOptionalFitsSystemWindows() {
        setFlags(2048, 2048);
    }

    public void getOutsets(Rect outOutsetRect) {
        if (this.mAttachInfo != null) {
            outOutsetRect.set(this.mAttachInfo.mOutsets);
        } else {
            outOutsetRect.setEmpty();
        }
    }

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "VISIBLE"), @ViewDebug.IntToString(from = 4, to = "INVISIBLE"), @ViewDebug.IntToString(from = 8, to = "GONE")})
    public int getVisibility() {
        return this.mViewFlags & 12;
    }

    @RemotableViewMethod
    public void setVisibility(int visibility) {
        setFlags(visibility, 12);
    }

    @ViewDebug.ExportedProperty
    public boolean isEnabled() {
        return (this.mViewFlags & 32) == 0;
    }

    @RemotableViewMethod
    public void setEnabled(boolean enabled) {
        if (enabled != isEnabled()) {
            setFlags(enabled ? 0 : 32, 32);
            refreshDrawableState();
            invalidate(true);
            if (!enabled) {
                cancelPendingInputEvents();
            }
        }
    }

    public void setFocusable(boolean focusable) {
        setFocusable((int) focusable);
    }

    public void setFocusable(int focusable) {
        if ((focusable & 17) == 0) {
            setFlags(0, 262144);
        }
        setFlags(focusable, 17);
    }

    public void setFocusableInTouchMode(boolean focusableInTouchMode) {
        setFlags(focusableInTouchMode ? 262144 : 0, 262144);
        if (focusableInTouchMode) {
            setFlags(1, 17);
        }
    }

    public void setAutofillHints(String... autofillHints) {
        if (autofillHints == null || autofillHints.length == 0) {
            this.mAutofillHints = null;
        } else {
            this.mAutofillHints = autofillHints;
        }
    }

    public void setAutofilled(boolean isAutofilled) {
        if (isAutofilled != isAutofilled()) {
            if (isAutofilled) {
                this.mPrivateFlags3 |= 65536;
            } else {
                this.mPrivateFlags3 &= -65537;
            }
            invalidate();
        }
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        setFlags(soundEffectsEnabled ? 134217728 : 0, 134217728);
    }

    @ViewDebug.ExportedProperty
    public boolean isSoundEffectsEnabled() {
        return 134217728 == (this.mViewFlags & 134217728);
    }

    public void setHapticFeedbackEnabled(boolean hapticFeedbackEnabled) {
        setFlags(hapticFeedbackEnabled ? 268435456 : 0, 268435456);
    }

    @ViewDebug.ExportedProperty
    public boolean isHapticFeedbackEnabled() {
        return 268435456 == (this.mViewFlags & 268435456);
    }

    @ViewDebug.ExportedProperty(category = "layout", mapping = {@ViewDebug.IntToString(from = 0, to = "LTR"), @ViewDebug.IntToString(from = 1, to = "RTL"), @ViewDebug.IntToString(from = 2, to = "INHERIT"), @ViewDebug.IntToString(from = 3, to = "LOCALE")})
    public int getRawLayoutDirection() {
        return (this.mPrivateFlags2 & 12) >> 2;
    }

    @RemotableViewMethod
    public void setLayoutDirection(int layoutDirection) {
        if (getRawLayoutDirection() != layoutDirection) {
            this.mPrivateFlags2 &= -13;
            resetRtlProperties();
            this.mPrivateFlags2 |= (layoutDirection << 2) & 12;
            resolveRtlPropertiesIfNeeded();
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = "layout", mapping = {@ViewDebug.IntToString(from = 0, to = "RESOLVED_DIRECTION_LTR"), @ViewDebug.IntToString(from = 1, to = "RESOLVED_DIRECTION_RTL")})
    public int getLayoutDirection() {
        if (getContext().getApplicationInfo().targetSdkVersion < 17) {
            this.mPrivateFlags2 |= 32;
            return 0;
        } else if ((this.mPrivateFlags2 & 16) == 16) {
            return 1;
        } else {
            return 0;
        }
    }

    @ViewDebug.ExportedProperty(category = "layout")
    @UnsupportedAppUsage
    public boolean isLayoutRtl() {
        return getLayoutDirection() == 1;
    }

    @ViewDebug.ExportedProperty(category = "layout")
    public boolean hasTransientState() {
        return (this.mPrivateFlags2 & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public void setHasTransientState(boolean hasTransientState) {
        int i;
        boolean oldHasTransientState = hasTransientState();
        if (hasTransientState) {
            i = this.mTransientStateCount + 1;
        } else {
            i = this.mTransientStateCount - 1;
        }
        this.mTransientStateCount = i;
        int i2 = 0;
        if (this.mTransientStateCount < 0) {
            this.mTransientStateCount = 0;
            Log.e(VIEW_LOG_TAG, "hasTransientState decremented below 0: unmatched pair of setHasTransientState calls");
        } else if ((hasTransientState && this.mTransientStateCount == 1) || (!hasTransientState && this.mTransientStateCount == 0)) {
            int i3 = this.mPrivateFlags2 & Integer.MAX_VALUE;
            if (hasTransientState) {
                i2 = Integer.MIN_VALUE;
            }
            this.mPrivateFlags2 = i3 | i2;
            boolean newHasTransientState = hasTransientState();
            if (this.mParent != null && newHasTransientState != oldHasTransientState) {
                try {
                    this.mParent.childHasTransientStateChanged(this, newHasTransientState);
                } catch (AbstractMethodError e) {
                    Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    public boolean isAttachedToWindow() {
        return this.mAttachInfo != null;
    }

    public boolean isLaidOut() {
        return (this.mPrivateFlags3 & 4) == 4;
    }

    /* access modifiers changed from: package-private */
    public boolean isLayoutValid() {
        return isLaidOut() && (this.mPrivateFlags & 4096) == 0;
    }

    public void setWillNotDraw(boolean willNotDraw) {
        setFlags(willNotDraw ? 128 : 0, 128);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean willNotDraw() {
        return (this.mViewFlags & 128) == 128;
    }

    @Deprecated
    public void setWillNotCacheDrawing(boolean willNotCacheDrawing) {
        setFlags(willNotCacheDrawing ? 131072 : 0, 131072);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    @Deprecated
    public boolean willNotCacheDrawing() {
        return (this.mViewFlags & 131072) == 131072;
    }

    @ViewDebug.ExportedProperty
    public boolean isClickable() {
        return (this.mViewFlags & 16384) == 16384;
    }

    public void setClickable(boolean clickable) {
        setFlags(clickable ? 16384 : 0, 16384);
    }

    public boolean isLongClickable() {
        return (this.mViewFlags & 2097152) == 2097152;
    }

    public void setLongClickable(boolean longClickable) {
        setFlags(longClickable ? 2097152 : 0, 2097152);
    }

    public boolean isContextClickable() {
        return (this.mViewFlags & 8388608) == 8388608;
    }

    public void setContextClickable(boolean contextClickable) {
        setFlags(contextClickable ? 8388608 : 0, 8388608);
    }

    /* access modifiers changed from: private */
    public void setPressed(boolean pressed, float x, float y) {
        if (pressed) {
            drawableHotspotChanged(x, y);
        }
        setPressed(pressed);
    }

    public void setPressed(boolean pressed) {
        boolean z = false;
        if (pressed != ((this.mPrivateFlags & 16384) == 16384)) {
            z = true;
        }
        boolean needsRefresh = z;
        if (pressed) {
            this.mPrivateFlags = 16384 | this.mPrivateFlags;
        } else {
            this.mPrivateFlags &= -16385;
        }
        if (needsRefresh) {
            refreshDrawableState();
        }
        dispatchSetPressed(pressed);
    }

    /* access modifiers changed from: protected */
    public void dispatchSetPressed(boolean pressed) {
    }

    @ViewDebug.ExportedProperty
    public boolean isPressed() {
        return (this.mPrivateFlags & 16384) == 16384;
    }

    public boolean isAssistBlocked() {
        return (this.mPrivateFlags3 & 16384) != 0;
    }

    @UnsupportedAppUsage
    public void setAssistBlocked(boolean enabled) {
        if (enabled) {
            this.mPrivateFlags3 |= 16384;
        } else {
            this.mPrivateFlags3 &= -16385;
        }
    }

    public boolean isSaveEnabled() {
        return (this.mViewFlags & 65536) != 65536;
    }

    public void setSaveEnabled(boolean enabled) {
        setFlags(enabled ? 0 : 65536, 65536);
    }

    @ViewDebug.ExportedProperty
    public boolean getFilterTouchesWhenObscured() {
        return (this.mViewFlags & 1024) != 0;
    }

    public void setFilterTouchesWhenObscured(boolean enabled) {
        setFlags(enabled ? 1024 : 0, 1024);
    }

    public boolean isSaveFromParentEnabled() {
        return (this.mViewFlags & 536870912) != 536870912;
    }

    public void setSaveFromParentEnabled(boolean enabled) {
        setFlags(enabled ? 0 : 536870912, 536870912);
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusable() {
        return 1 == (this.mViewFlags & 1);
    }

    @ViewDebug.ExportedProperty(category = "focus", mapping = {@ViewDebug.IntToString(from = 0, to = "NOT_FOCUSABLE"), @ViewDebug.IntToString(from = 1, to = "FOCUSABLE"), @ViewDebug.IntToString(from = 16, to = "FOCUSABLE_AUTO")})
    public int getFocusable() {
        if ((this.mViewFlags & 16) > 0) {
            return 16;
        }
        return this.mViewFlags & 1;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusableInTouchMode() {
        return 262144 == (this.mViewFlags & 262144);
    }

    public boolean isScreenReaderFocusable() {
        return (this.mPrivateFlags3 & 268435456) != 0;
    }

    public void setScreenReaderFocusable(boolean screenReaderFocusable) {
        updatePflags3AndNotifyA11yIfChanged(268435456, screenReaderFocusable);
    }

    public boolean isAccessibilityHeading() {
        return (this.mPrivateFlags3 & Integer.MIN_VALUE) != 0;
    }

    public void setAccessibilityHeading(boolean isHeading) {
        updatePflags3AndNotifyA11yIfChanged(Integer.MIN_VALUE, isHeading);
    }

    private void updatePflags3AndNotifyA11yIfChanged(int mask, boolean newValue) {
        int pflags3;
        int pflags32 = this.mPrivateFlags3;
        if (newValue) {
            pflags3 = pflags32 | mask;
        } else {
            pflags3 = pflags32 & (~mask);
        }
        if (pflags3 != this.mPrivateFlags3) {
            this.mPrivateFlags3 = pflags3;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public View focusSearch(int direction) {
        if (this.mParent != null) {
            return this.mParent.focusSearch(this, direction);
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isKeyboardNavigationCluster() {
        return (this.mPrivateFlags3 & 32768) != 0;
    }

    /* access modifiers changed from: package-private */
    public View findKeyboardNavigationCluster() {
        if (!(this.mParent instanceof View)) {
            return null;
        }
        View cluster = ((View) this.mParent).findKeyboardNavigationCluster();
        if (cluster != null) {
            return cluster;
        }
        if (isKeyboardNavigationCluster()) {
            return this;
        }
        return null;
    }

    public void setKeyboardNavigationCluster(boolean isCluster) {
        if (isCluster) {
            this.mPrivateFlags3 |= 32768;
        } else {
            this.mPrivateFlags3 &= -32769;
        }
    }

    public final void setFocusedInCluster() {
        setFocusedInCluster(findKeyboardNavigationCluster());
    }

    private void setFocusedInCluster(View cluster) {
        if (this instanceof ViewGroup) {
            ((ViewGroup) this).mFocusedInCluster = null;
        }
        if (cluster != this) {
            ViewParent parent = this.mParent;
            View child = this;
            while (parent instanceof ViewGroup) {
                ((ViewGroup) parent).mFocusedInCluster = child;
                if (parent != cluster) {
                    child = (View) parent;
                    parent = parent.getParent();
                } else {
                    return;
                }
            }
        }
    }

    private void updateFocusedInCluster(View oldFocus, int direction) {
        View oldCluster;
        if (oldFocus != null && (oldCluster = oldFocus.findKeyboardNavigationCluster()) != findKeyboardNavigationCluster()) {
            oldFocus.setFocusedInCluster(oldCluster);
            if (oldFocus.mParent instanceof ViewGroup) {
                if (direction == 2 || direction == 1) {
                    ((ViewGroup) oldFocus.mParent).clearFocusedInCluster(oldFocus);
                } else if ((oldFocus instanceof ViewGroup) && ((ViewGroup) oldFocus).getDescendantFocusability() == 262144 && ViewRootImpl.isViewDescendantOf(this, oldFocus)) {
                    ((ViewGroup) oldFocus.mParent).clearFocusedInCluster(oldFocus);
                }
            }
        }
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusedByDefault() {
        return (this.mPrivateFlags3 & 262144) != 0;
    }

    public void setFocusedByDefault(boolean isFocusedByDefault) {
        if (isFocusedByDefault != ((this.mPrivateFlags3 & 262144) != 0)) {
            if (isFocusedByDefault) {
                this.mPrivateFlags3 |= 262144;
            } else {
                this.mPrivateFlags3 &= -262145;
            }
            if (!(this.mParent instanceof ViewGroup)) {
                return;
            }
            if (isFocusedByDefault) {
                ((ViewGroup) this.mParent).setDefaultFocus(this);
            } else {
                ((ViewGroup) this.mParent).clearDefaultFocus(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasDefaultFocus() {
        return isFocusedByDefault();
    }

    public View keyboardNavigationClusterSearch(View currentCluster, int direction) {
        if (isKeyboardNavigationCluster()) {
            currentCluster = this;
        }
        if (isRootNamespace()) {
            return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this, currentCluster, direction);
        }
        if (this.mParent != null) {
            return this.mParent.keyboardNavigationClusterSearch(currentCluster, direction);
        }
        return null;
    }

    public boolean dispatchUnhandledMove(View focused, int direction) {
        return false;
    }

    public void setDefaultFocusHighlightEnabled(boolean defaultFocusHighlightEnabled) {
        this.mDefaultFocusHighlightEnabled = defaultFocusHighlightEnabled;
    }

    @ViewDebug.ExportedProperty(category = "focus")
    public final boolean getDefaultFocusHighlightEnabled() {
        return this.mDefaultFocusHighlightEnabled;
    }

    /* access modifiers changed from: package-private */
    public View findUserSetNextFocus(View root, int direction) {
        if (direction != 17) {
            if (direction != 33) {
                if (direction != 66) {
                    if (direction != 130) {
                        switch (direction) {
                            case 1:
                                if (this.mID == -1) {
                                    return null;
                                }
                                final int id = this.mID;
                                return root.findViewByPredicateInsideOut(this, new Predicate<View>() {
                                    public boolean test(View t) {
                                        return t.mNextFocusForwardId == id;
                                    }
                                });
                            case 2:
                                if (this.mNextFocusForwardId == -1) {
                                    return null;
                                }
                                return findViewInsideOutShouldExist(root, this.mNextFocusForwardId);
                            default:
                                return null;
                        }
                    } else if (this.mNextFocusDownId == -1) {
                        return null;
                    } else {
                        return findViewInsideOutShouldExist(root, this.mNextFocusDownId);
                    }
                } else if (this.mNextFocusRightId == -1) {
                    return null;
                } else {
                    return findViewInsideOutShouldExist(root, this.mNextFocusRightId);
                }
            } else if (this.mNextFocusUpId == -1) {
                return null;
            } else {
                return findViewInsideOutShouldExist(root, this.mNextFocusUpId);
            }
        } else if (this.mNextFocusLeftId == -1) {
            return null;
        } else {
            return findViewInsideOutShouldExist(root, this.mNextFocusLeftId);
        }
    }

    /* access modifiers changed from: package-private */
    public View findUserSetNextKeyboardNavigationCluster(View root, int direction) {
        switch (direction) {
            case 1:
                if (this.mID == -1) {
                    return null;
                }
                return root.findViewByPredicateInsideOut(this, new Predicate(this.mID) {
                    private final /* synthetic */ int f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final boolean test(Object obj) {
                        return View.lambda$findUserSetNextKeyboardNavigationCluster$0(this.f$0, (View) obj);
                    }
                });
            case 2:
                if (this.mNextClusterForwardId == -1) {
                    return null;
                }
                return findViewInsideOutShouldExist(root, this.mNextClusterForwardId);
            default:
                return null;
        }
    }

    static /* synthetic */ boolean lambda$findUserSetNextKeyboardNavigationCluster$0(int id, View t) {
        return t.mNextClusterForwardId == id;
    }

    private View findViewInsideOutShouldExist(View root, int id) {
        if (this.mMatchIdPredicate == null) {
            this.mMatchIdPredicate = new MatchIdPredicate();
        }
        this.mMatchIdPredicate.mId = id;
        View result = root.findViewByPredicateInsideOut(this, this.mMatchIdPredicate);
        if (result == null) {
            Log.w(VIEW_LOG_TAG, "couldn't find view with id " + id);
        }
        return result;
    }

    public ArrayList<View> getFocusables(int direction) {
        ArrayList<View> result = new ArrayList<>(24);
        addFocusables(result, direction);
        return result;
    }

    public void addFocusables(ArrayList<View> views, int direction) {
        addFocusables(views, direction, isInTouchMode() ? 1 : 0);
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (views == null || !canTakeFocus()) {
            return;
        }
        if ((focusableMode & 1) != 1 || isFocusableInTouchMode()) {
            views.add(this);
        }
    }

    public void addKeyboardNavigationClusters(Collection<View> views, int direction) {
        if (isKeyboardNavigationCluster() && hasFocusable()) {
            views.add(this);
        }
    }

    public void findViewsWithText(ArrayList<View> outViews, CharSequence searched, int flags) {
        if (getAccessibilityNodeProvider() != null) {
            if ((flags & 4) != 0) {
                outViews.add(this);
            }
        } else if ((flags & 2) != 0 && searched != null && searched.length() > 0 && this.mContentDescription != null && this.mContentDescription.length() > 0) {
            if (this.mContentDescription.toString().toLowerCase().contains(searched.toString().toLowerCase())) {
                outViews.add(this);
            }
        }
    }

    public ArrayList<View> getTouchables() {
        ArrayList<View> result = new ArrayList<>();
        addTouchables(result);
        return result;
    }

    public void addTouchables(ArrayList<View> views) {
        int viewFlags = this.mViewFlags;
        if (((viewFlags & 16384) == 16384 || (viewFlags & 2097152) == 2097152 || (viewFlags & 8388608) == 8388608) && (viewFlags & 32) == 0) {
            views.add(this);
        }
    }

    public boolean isAccessibilityFocused() {
        return (this.mPrivateFlags2 & 67108864) != 0;
    }

    @UnsupportedAppUsage
    public boolean requestAccessibilityFocus() {
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (!manager.isEnabled() || !manager.isTouchExplorationEnabled() || (this.mViewFlags & 12) != 0 || (this.mPrivateFlags2 & 67108864) != 0) {
            return false;
        }
        this.mPrivateFlags2 |= 67108864;
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.setAccessibilityFocus(this, (AccessibilityNodeInfo) null);
        }
        invalidate();
        sendAccessibilityEvent(32768);
        return true;
    }

    @UnsupportedAppUsage
    public void clearAccessibilityFocus() {
        View focusHost;
        clearAccessibilityFocusNoCallbacks(0);
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null && (focusHost = viewRootImpl.getAccessibilityFocusedHost()) != null && ViewRootImpl.isViewDescendantOf(focusHost, this)) {
            viewRootImpl.setAccessibilityFocus((View) null, (AccessibilityNodeInfo) null);
        }
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendAccessibilityHoverEvent(int r4) {
        /*
            r3 = this;
            r0 = r3
        L_0x0001:
            boolean r1 = r0.includeForAccessibility()
            if (r1 == 0) goto L_0x000b
            r0.sendAccessibilityEvent(r4)
            return
        L_0x000b:
            android.view.ViewParent r1 = r0.getParent()
            boolean r2 = r1 instanceof android.view.View
            if (r2 == 0) goto L_0x0017
            r0 = r1
            android.view.View r0 = (android.view.View) r0
            goto L_0x0001
        L_0x0017:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.sendAccessibilityHoverEvent(int):void");
    }

    /* access modifiers changed from: package-private */
    public void clearAccessibilityFocusNoCallbacks(int action) {
        if ((this.mPrivateFlags2 & 67108864) != 0) {
            this.mPrivateFlags2 &= -67108865;
            invalidate();
            if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(65536);
                event.setAction(action);
                if (this.mAccessibilityDelegate != null) {
                    this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, event);
                } else {
                    sendAccessibilityEventUnchecked(event);
                }
            }
        }
    }

    public final boolean requestFocus() {
        return requestFocus(130);
    }

    public boolean restoreFocusInCluster(int direction) {
        if (restoreDefaultFocus()) {
            return true;
        }
        return requestFocus(direction);
    }

    public boolean restoreFocusNotInCluster() {
        return requestFocus(130);
    }

    public boolean restoreDefaultFocus() {
        return requestFocus(130);
    }

    public final boolean requestFocus(int direction) {
        return requestFocus(direction, (Rect) null);
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return requestFocusNoSearch(direction, previouslyFocusedRect);
    }

    private boolean requestFocusNoSearch(int direction, Rect previouslyFocusedRect) {
        if (!canTakeFocus()) {
            return false;
        }
        if ((isInTouchMode() && 262144 != (this.mViewFlags & 262144)) || hasAncestorThatBlocksDescendantFocus()) {
            return false;
        }
        if (!isLayoutValid()) {
            this.mPrivateFlags |= 1;
        } else {
            clearParentsWantFocus();
        }
        handleFocusGainInternal(direction, previouslyFocusedRect);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void clearParentsWantFocus() {
        if (this.mParent instanceof View) {
            ((View) this.mParent).mPrivateFlags &= -2;
            ((View) this.mParent).clearParentsWantFocus();
        }
    }

    public final boolean requestFocusFromTouch() {
        ViewRootImpl viewRoot;
        if (isInTouchMode() && (viewRoot = getViewRootImpl()) != null) {
            viewRoot.ensureTouchMode(false);
        }
        return requestFocus(130);
    }

    private boolean hasAncestorThatBlocksDescendantFocus() {
        boolean focusableInTouchMode = isFocusableInTouchMode();
        ViewParent ancestor = this.mParent;
        while (ancestor instanceof ViewGroup) {
            ViewGroup vgAncestor = (ViewGroup) ancestor;
            if (vgAncestor.getDescendantFocusability() == 393216) {
                return true;
            }
            if (!focusableInTouchMode && vgAncestor.shouldBlockFocusForTouchscreen()) {
                return true;
            }
            ancestor = vgAncestor.getParent();
        }
        return false;
    }

    @ViewDebug.ExportedProperty(category = "accessibility", mapping = {@ViewDebug.IntToString(from = 0, to = "auto"), @ViewDebug.IntToString(from = 1, to = "yes"), @ViewDebug.IntToString(from = 2, to = "no"), @ViewDebug.IntToString(from = 4, to = "noHideDescendants")})
    public int getImportantForAccessibility() {
        return (this.mPrivateFlags2 & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK) >> 20;
    }

    public void setAccessibilityLiveRegion(int mode) {
        if (mode != getAccessibilityLiveRegion()) {
            this.mPrivateFlags2 &= -25165825;
            this.mPrivateFlags2 |= (mode << 23) & 25165824;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getAccessibilityLiveRegion() {
        return (this.mPrivateFlags2 & 25165824) >> 23;
    }

    public void setImportantForAccessibility(int mode) {
        View focusHost;
        int oldMode = getImportantForAccessibility();
        if (mode != oldMode) {
            boolean oldIncludeForAccessibility = true;
            boolean hideDescendants = mode == 4;
            if ((mode == 2 || hideDescendants) && (focusHost = findAccessibilityFocusHost(hideDescendants)) != null) {
                focusHost.clearAccessibilityFocus();
            }
            boolean maySkipNotify = oldMode == 0 || mode == 0;
            if (!maySkipNotify || !includeForAccessibility()) {
                oldIncludeForAccessibility = false;
            }
            this.mPrivateFlags2 &= -7340033;
            this.mPrivateFlags2 |= (mode << 20) & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK;
            if (!maySkipNotify || oldIncludeForAccessibility != includeForAccessibility()) {
                notifySubtreeAccessibilityStateChangedIfNeeded();
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    private View findAccessibilityFocusHost(boolean searchDescendants) {
        ViewRootImpl viewRoot;
        View focusHost;
        if (isAccessibilityFocusedViewOrHost()) {
            return this;
        }
        if (!searchDescendants || (viewRoot = getViewRootImpl()) == null || (focusHost = viewRoot.getAccessibilityFocusedHost()) == null || !ViewRootImpl.isViewDescendantOf(focusHost, this)) {
            return null;
        }
        return focusHost;
    }

    public boolean isImportantForAccessibility() {
        int mode = (this.mPrivateFlags2 & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK) >> 20;
        if (mode == 2 || mode == 4) {
            return false;
        }
        for (ViewParent parent = this.mParent; parent instanceof View; parent = parent.getParent()) {
            if (((View) parent).getImportantForAccessibility() == 4) {
                return false;
            }
        }
        if (mode == 1 || isActionableForAccessibility() || hasListenersForAccessibility() || getAccessibilityNodeProvider() != null || getAccessibilityLiveRegion() != 0 || isAccessibilityPane()) {
            return true;
        }
        return false;
    }

    public ViewParent getParentForAccessibility() {
        if (!(this.mParent instanceof View)) {
            return null;
        }
        if (((View) this.mParent).includeForAccessibility()) {
            return this.mParent;
        }
        return this.mParent.getParentForAccessibility();
    }

    /* access modifiers changed from: package-private */
    public View getSelfOrParentImportantForA11y() {
        if (isImportantForAccessibility()) {
            return this;
        }
        ViewParent parent = getParentForAccessibility();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }

    public void addChildrenForAccessibility(ArrayList<View> arrayList) {
    }

    @UnsupportedAppUsage
    public boolean includeForAccessibility() {
        if (this.mAttachInfo == null) {
            return false;
        }
        if ((this.mAttachInfo.mAccessibilityFetchFlags & 8) != 0 || isImportantForAccessibility()) {
            return true;
        }
        return false;
    }

    public boolean isActionableForAccessibility() {
        return isClickable() || isLongClickable() || isFocusable();
    }

    private boolean hasListenersForAccessibility() {
        ListenerInfo info = getListenerInfo();
        return (this.mTouchDelegate == null && info.mOnKeyListener == null && info.mOnTouchListener == null && info.mOnGenericMotionListener == null && info.mOnHoverListener == null && info.mOnDragListener == null) ? false : true;
    }

    @UnsupportedAppUsage
    public void notifyViewAccessibilityStateChangedIfNeeded(int changeType) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mAttachInfo != null) {
            if (changeType != 1 && isAccessibilityPane() && (getVisibility() == 0 || changeType == 32)) {
                AccessibilityEvent event = AccessibilityEvent.obtain();
                onInitializeAccessibilityEvent(event);
                event.setEventType(32);
                event.setContentChangeTypes(changeType);
                event.setSource(this);
                onPopulateAccessibilityEvent(event);
                if (this.mParent != null) {
                    try {
                        this.mParent.requestSendAccessibilityEvent(this, event);
                    } catch (AbstractMethodError e) {
                        Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                    }
                }
            } else if (getAccessibilityLiveRegion() != 0) {
                AccessibilityEvent event2 = AccessibilityEvent.obtain();
                event2.setEventType(2048);
                event2.setContentChangeTypes(changeType);
                sendAccessibilityEventUnchecked(event2);
            } else if (this.mParent != null) {
                try {
                    this.mParent.notifySubtreeAccessibilityStateChanged(this, this, changeType);
                } catch (AbstractMethodError e2) {
                    Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                }
            }
        }
    }

    @UnsupportedAppUsage
    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mAttachInfo != null && (this.mPrivateFlags2 & 134217728) == 0) {
            this.mPrivateFlags2 |= 134217728;
            if (this.mParent != null) {
                try {
                    this.mParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
                } catch (AbstractMethodError e) {
                    Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    public void setTransitionVisibility(int visibility) {
        this.mViewFlags = (this.mViewFlags & -13) | visibility;
    }

    /* access modifiers changed from: package-private */
    public void resetSubtreeAccessibilityStateChanged() {
        this.mPrivateFlags2 &= -134217729;
    }

    public boolean dispatchNestedPrePerformAccessibilityAction(int action, Bundle arguments) {
        for (ViewParent p = getParent(); p != null; p = p.getParent()) {
            if (p.onNestedPrePerformAccessibilityAction(this, action, arguments)) {
                return true;
            }
        }
        return false;
    }

    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.performAccessibilityAction(this, action, arguments);
        }
        return performAccessibilityActionInternal(action, arguments);
    }

    @UnsupportedAppUsage
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        int start;
        if (isNestedScrollingEnabled() && ((action == 8192 || action == 4096 || action == 16908344 || action == 16908345 || action == 16908346 || action == 16908347) && dispatchNestedPrePerformAccessibilityAction(action, arguments))) {
            return true;
        }
        switch (action) {
            case 1:
                if (!hasFocus()) {
                    getViewRootImpl().ensureTouchMode(false);
                    return requestFocus();
                }
                break;
            case 2:
                if (hasFocus()) {
                    clearFocus();
                    return !isFocused();
                }
                break;
            case 4:
                if (!isSelected()) {
                    setSelected(true);
                    return isSelected();
                }
                break;
            case 8:
                if (isSelected()) {
                    setSelected(false);
                    return !isSelected();
                }
                break;
            case 16:
                if (isClickable()) {
                    performClickInternal();
                    return true;
                }
                break;
            case 32:
                if (isLongClickable()) {
                    performLongClick();
                    return true;
                }
                break;
            case 64:
                if (!isAccessibilityFocused()) {
                    return requestAccessibilityFocus();
                }
                break;
            case 128:
                if (isAccessibilityFocused()) {
                    clearAccessibilityFocus();
                    return true;
                }
                break;
            case 256:
                if (arguments != null) {
                    return traverseAtGranularity(arguments.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT"), true, arguments.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN"));
                }
                break;
            case 512:
                if (arguments != null) {
                    return traverseAtGranularity(arguments.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT"), false, arguments.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN"));
                }
                break;
            case 131072:
                if (getIterableTextForAccessibility() == null) {
                    return false;
                }
                int end = -1;
                if (arguments != null) {
                    start = arguments.getInt("ACTION_ARGUMENT_SELECTION_START_INT", -1);
                } else {
                    start = -1;
                }
                if (arguments != null) {
                    end = arguments.getInt("ACTION_ARGUMENT_SELECTION_END_INT", -1);
                }
                if (!(getAccessibilitySelectionStart() == start && getAccessibilitySelectionEnd() == end) && start == end) {
                    setAccessibilitySelection(start, end);
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                    return true;
                }
            case 16908342:
                if (this.mAttachInfo != null) {
                    Rect r = this.mAttachInfo.mTmpInvalRect;
                    getDrawingRect(r);
                    return requestRectangleOnScreen(r, true);
                }
                break;
            case 16908348:
                if (isContextClickable()) {
                    performContextClick();
                    return true;
                }
                break;
            case 16908356:
                if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
                    return showLongClickTooltip(0, 0);
                }
                return false;
            case 16908357:
                if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
                    return false;
                }
                hideTooltip();
                return true;
        }
        return false;
    }

    private boolean traverseAtGranularity(int granularity, boolean forward, boolean extendSelection) {
        AccessibilityIterators.TextSegmentIterator iterator;
        int selectionStart;
        int selectionEnd;
        int action;
        CharSequence text = getIterableTextForAccessibility();
        if (text == null || text.length() == 0 || (iterator = getIteratorForGranularity(granularity)) == null) {
            return false;
        }
        int current = getAccessibilitySelectionEnd();
        if (current == -1) {
            current = forward ? 0 : text.length();
        }
        int[] range = forward ? iterator.following(current) : iterator.preceding(current);
        if (range == null) {
            return false;
        }
        int segmentStart = range[0];
        int segmentEnd = range[1];
        if (!extendSelection || !isAccessibilitySelectionExtendable()) {
            selectionStart = forward ? segmentEnd : segmentStart;
            selectionEnd = selectionStart;
        } else {
            selectionStart = getAccessibilitySelectionStart();
            if (selectionStart == -1) {
                selectionStart = forward ? segmentStart : segmentEnd;
            }
            selectionEnd = forward ? segmentEnd : segmentStart;
        }
        setAccessibilitySelection(selectionStart, selectionEnd);
        if (forward) {
            action = 256;
        } else {
            action = 512;
        }
        sendViewTextTraversedAtGranularityEvent(action, granularity, segmentStart, segmentEnd);
        return true;
    }

    @UnsupportedAppUsage
    public CharSequence getIterableTextForAccessibility() {
        return getContentDescription();
    }

    public boolean isAccessibilitySelectionExtendable() {
        return false;
    }

    public int getAccessibilitySelectionStart() {
        return this.mAccessibilityCursorPosition;
    }

    public int getAccessibilitySelectionEnd() {
        return getAccessibilitySelectionStart();
    }

    public void setAccessibilitySelection(int start, int end) {
        if (start != end || end != this.mAccessibilityCursorPosition) {
            if (start < 0 || start != end || end > getIterableTextForAccessibility().length()) {
                this.mAccessibilityCursorPosition = -1;
            } else {
                this.mAccessibilityCursorPosition = start;
            }
            sendAccessibilityEvent(8192);
        }
    }

    private void sendViewTextTraversedAtGranularityEvent(int action, int granularity, int fromIndex, int toIndex) {
        if (this.mParent != null) {
            AccessibilityEvent event = AccessibilityEvent.obtain(131072);
            onInitializeAccessibilityEvent(event);
            onPopulateAccessibilityEvent(event);
            event.setFromIndex(fromIndex);
            event.setToIndex(toIndex);
            event.setAction(action);
            event.setMovementGranularity(granularity);
            this.mParent.requestSendAccessibilityEvent(this, event);
        }
    }

    @UnsupportedAppUsage
    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int granularity) {
        if (granularity != 8) {
            switch (granularity) {
                case 1:
                    CharSequence text = getIterableTextForAccessibility();
                    if (text == null || text.length() <= 0) {
                        return null;
                    }
                    AccessibilityIterators.CharacterTextSegmentIterator iterator = AccessibilityIterators.CharacterTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                    iterator.initialize(text.toString());
                    return iterator;
                case 2:
                    CharSequence text2 = getIterableTextForAccessibility();
                    if (text2 == null || text2.length() <= 0) {
                        return null;
                    }
                    AccessibilityIterators.WordTextSegmentIterator iterator2 = AccessibilityIterators.WordTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                    iterator2.initialize(text2.toString());
                    return iterator2;
                default:
                    return null;
            }
        } else {
            CharSequence text3 = getIterableTextForAccessibility();
            if (text3 == null || text3.length() <= 0) {
                return null;
            }
            AccessibilityIterators.ParagraphTextSegmentIterator iterator3 = AccessibilityIterators.ParagraphTextSegmentIterator.getInstance();
            iterator3.initialize(text3.toString());
            return iterator3;
        }
    }

    public final boolean isTemporarilyDetached() {
        return (this.mPrivateFlags3 & 33554432) != 0;
    }

    public void dispatchStartTemporaryDetach() {
        this.mPrivateFlags3 |= 33554432;
        notifyEnterOrExitForAutoFillIfNeeded(false);
        onStartTemporaryDetach();
    }

    public void onStartTemporaryDetach() {
        removeUnsetPressCallback();
        this.mPrivateFlags |= 67108864;
    }

    public void dispatchFinishTemporaryDetach() {
        this.mPrivateFlags3 &= -33554433;
        onFinishTemporaryDetach();
        if (hasWindowFocus() && hasFocus()) {
            notifyFocusChangeToInputMethodManager(true);
        }
        notifyEnterOrExitForAutoFillIfNeeded(true);
    }

    public void onFinishTemporaryDetach() {
    }

    public KeyEvent.DispatcherState getKeyDispatcherState() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mKeyDispatchState;
        }
        return null;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        return onKeyPreIme(event.getKeyCode(), event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onKeyEvent(event, 0);
        }
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnKeyListener != null && (this.mViewFlags & 32) == 0 && li.mOnKeyListener.onKey(this, event.getKeyCode(), event)) {
            return true;
        }
        if (event.dispatch(this, this.mAttachInfo != null ? this.mAttachInfo.mKeyDispatchState : null, this)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        return false;
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return onKeyShortcut(event.getKeyCode(), event);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.isTargetAccessibilityFocus()) {
            if (!isAccessibilityFocusedViewOrHost()) {
                return false;
            }
            event.setTargetAccessibilityFocus(false);
        }
        boolean result = false;
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(event, 0);
        }
        int actionMasked = event.getActionMasked();
        if (actionMasked == 0) {
            SeempLog.record(3);
            stopNestedScroll();
        }
        if (onFilterTouchEventForSecurity(event)) {
            if ((this.mViewFlags & 32) == 0 && handleScrollBarDragging(event)) {
                result = true;
            }
            ListenerInfo li = this.mListenerInfo;
            if (li != null && li.mOnTouchListener != null && (this.mViewFlags & 32) == 0 && li.mOnTouchListener.onTouch(this, event)) {
                result = true;
            }
            if (!result && onTouchEvent(event)) {
                result = true;
            }
        }
        if (!result && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        if (actionMasked == 1 || actionMasked == 3 || (actionMasked == 0 && !result)) {
            stopNestedScroll();
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean isAccessibilityFocusedViewOrHost() {
        return isAccessibilityFocused() || (getViewRootImpl() != null && getViewRootImpl().getAccessibilityFocusedHost() == this);
    }

    /* access modifiers changed from: protected */
    public boolean canReceivePointerEvents() {
        return (this.mViewFlags & 12) == 0 || getAnimation() != null;
    }

    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        if ((this.mViewFlags & 1024) == 0 || (event.getFlags() & 1) == 0) {
            return true;
        }
        return false;
    }

    public boolean dispatchTrackballEvent(MotionEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTrackballEvent(event, 0);
        }
        return onTrackballEvent(event);
    }

    public boolean dispatchCapturedPointerEvent(MotionEvent event) {
        if (!hasPointerCapture()) {
            return false;
        }
        ListenerInfo li = this.mListenerInfo;
        if (li == null || li.mOnCapturedPointerListener == null || !li.mOnCapturedPointerListener.onCapturedPointer(this, event)) {
            return onCapturedPointerEvent(event);
        }
        return true;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onGenericMotionEvent(event, 0);
        }
        if ((event.getSource() & 2) != 0) {
            int action = event.getAction();
            if (action == 9 || action == 7 || action == 10) {
                if (dispatchHoverEvent(event)) {
                    return true;
                }
            } else if (dispatchGenericPointerEvent(event)) {
                return true;
            }
        } else if (dispatchGenericFocusedEvent(event)) {
            return true;
        }
        if (dispatchGenericMotionEventInternal(event)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        return false;
    }

    private boolean dispatchGenericMotionEventInternal(MotionEvent event) {
        ListenerInfo li = this.mListenerInfo;
        if ((li != null && li.mOnGenericMotionListener != null && (this.mViewFlags & 32) == 0 && li.mOnGenericMotionListener.onGenericMotion(this, event)) || onGenericMotionEvent(event)) {
            return true;
        }
        int actionButton = event.getActionButton();
        switch (event.getActionMasked()) {
            case 11:
                if (isContextClickable() && !this.mInContextButtonPress && !this.mHasPerformedLongPress && ((actionButton == 32 || actionButton == 2) && performContextClick(event.getX(), event.getY()))) {
                    this.mInContextButtonPress = true;
                    setPressed(true, event.getX(), event.getY());
                    removeTapCallback();
                    removeLongPressCallback();
                    return true;
                }
            case 12:
                if (this.mInContextButtonPress && (actionButton == 32 || actionButton == 2)) {
                    this.mInContextButtonPress = false;
                    this.mIgnoreNextUpEvent = true;
                    break;
                }
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchHoverEvent(MotionEvent event) {
        ListenerInfo li = this.mListenerInfo;
        if (li == null || li.mOnHoverListener == null || (this.mViewFlags & 32) != 0 || !li.mOnHoverListener.onHover(this, event)) {
            return onHoverEvent(event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean hasHoveredChild() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean pointInHoveredChild(MotionEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchGenericPointerEvent(MotionEvent event) {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean dispatchGenericFocusedEvent(MotionEvent event) {
        return false;
    }

    @UnsupportedAppUsage
    public final boolean dispatchPointerEvent(MotionEvent event) {
        if (event.isTouchEvent()) {
            return dispatchTouchEvent(event);
        }
        return dispatchGenericMotionEvent(event);
    }

    public void dispatchWindowFocusChanged(boolean hasFocus) {
        onWindowFocusChanged(hasFocus);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            if (isPressed()) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            if ((this.mPrivateFlags & 2) != 0) {
                notifyFocusChangeToInputMethodManager(false);
            }
            removeLongPressCallback();
            removeTapCallback();
            onFocusLost();
        } else if ((this.mPrivateFlags & 2) != 0) {
            notifyFocusChangeToInputMethodManager(true);
        }
        refreshDrawableState();
    }

    public boolean hasWindowFocus() {
        return this.mAttachInfo != null && this.mAttachInfo.mHasWindowFocus;
    }

    /* access modifiers changed from: protected */
    public void dispatchVisibilityChanged(View changedView, int visibility) {
        onVisibilityChanged(changedView, visibility);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
    }

    public void dispatchDisplayHint(int hint) {
        onDisplayHint(hint);
    }

    /* access modifiers changed from: protected */
    public void onDisplayHint(int hint) {
    }

    public void dispatchWindowVisibilityChanged(int visibility) {
        onWindowVisibilityChanged(visibility);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        if (visibility == 0) {
            initialAwakenScrollBars();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchVisibilityAggregated(boolean isVisible) {
        boolean thisVisible = getVisibility() == 0;
        if (thisVisible || !isVisible) {
            onVisibilityAggregated(isVisible);
        }
        if (!thisVisible || !isVisible) {
            return false;
        }
        return true;
    }

    public void onVisibilityAggregated(boolean isVisible) {
        int i;
        int i2;
        AutofillManager afm;
        boolean oldVisible = (this.mPrivateFlags3 & 536870912) != 0;
        if (isVisible) {
            i = 536870912 | this.mPrivateFlags3;
        } else {
            i = this.mPrivateFlags3 & -536870913;
        }
        this.mPrivateFlags3 = i;
        if (isVisible && this.mAttachInfo != null) {
            initialAwakenScrollBars();
        }
        Drawable dr = this.mBackground;
        if (!(dr == null || isVisible == dr.isVisible())) {
            dr.setVisible(isVisible, false);
        }
        Drawable hl = this.mDefaultFocusHighlight;
        if (!(hl == null || isVisible == hl.isVisible())) {
            hl.setVisible(isVisible, false);
        }
        Drawable fg = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (!(fg == null || isVisible == fg.isVisible())) {
            fg.setVisible(isVisible, false);
        }
        if (isAutofillable() && (afm = getAutofillManager()) != null && getAutofillViewId() > 1073741823) {
            if (this.mVisibilityChangeForAutofillHandler != null) {
                this.mVisibilityChangeForAutofillHandler.removeMessages(0);
            }
            if (isVisible) {
                afm.notifyViewVisibilityChanged(this, true);
            } else {
                if (this.mVisibilityChangeForAutofillHandler == null) {
                    this.mVisibilityChangeForAutofillHandler = new VisibilityChangeForAutofillHandler(afm, this);
                }
                this.mVisibilityChangeForAutofillHandler.obtainMessage(0, this).sendToTarget();
            }
        }
        if (isAccessibilityPane() && isVisible != oldVisible) {
            if (isVisible) {
                i2 = 16;
            } else {
                i2 = 32;
            }
            notifyViewAccessibilityStateChangedIfNeeded(i2);
        }
    }

    public int getWindowVisibility() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindowVisibility;
        }
        return 8;
    }

    public void getWindowVisibleDisplayFrame(Rect outRect) {
        if (this.mAttachInfo != null) {
            try {
                this.mAttachInfo.mSession.getDisplayFrame(this.mAttachInfo.mWindow, outRect);
                Rect insets = this.mAttachInfo.mVisibleInsets;
                outRect.left += insets.left;
                outRect.top += insets.top;
                outRect.right -= insets.right;
                outRect.bottom -= insets.bottom;
            } catch (RemoteException e) {
            }
        } else {
            DisplayManagerGlobal.getInstance().getRealDisplay(0).getRectSize(outRect);
        }
    }

    @UnsupportedAppUsage
    public void getWindowDisplayFrame(Rect outRect) {
        if (this.mAttachInfo != null) {
            try {
                this.mAttachInfo.mSession.getDisplayFrame(this.mAttachInfo.mWindow, outRect);
            } catch (RemoteException e) {
            }
        } else {
            DisplayManagerGlobal.getInstance().getRealDisplay(0).getRectSize(outRect);
        }
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        onConfigurationChanged(newConfig);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
    }

    /* access modifiers changed from: package-private */
    public void dispatchCollectViewAttributes(AttachInfo attachInfo, int visibility) {
        performCollectViewAttributes(attachInfo, visibility);
    }

    /* access modifiers changed from: package-private */
    public void performCollectViewAttributes(AttachInfo attachInfo, int visibility) {
        if ((visibility & 12) == 0) {
            if ((this.mViewFlags & 67108864) == 67108864) {
                attachInfo.mKeepScreenOn = true;
            }
            attachInfo.mSystemUiVisibility |= this.mSystemUiVisibility;
            ListenerInfo li = this.mListenerInfo;
            if (li != null && li.mOnSystemUiVisibilityChangeListener != null) {
                attachInfo.mHasSystemUiListeners = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void needGlobalAttributesUpdate(boolean force) {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null && !ai.mRecomputeGlobalAttributes) {
            if (force || ai.mKeepScreenOn || ai.mSystemUiVisibility != 0 || ai.mHasSystemUiListeners) {
                ai.mRecomputeGlobalAttributes = true;
            }
        }
    }

    @ViewDebug.ExportedProperty
    public boolean isInTouchMode() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mInTouchMode;
        }
        return ViewRootImpl.isInTouchMode();
    }

    @ViewDebug.CapturedViewProperty
    public final Context getContext() {
        return this.mContext;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SeempLog.record(4);
        if (KeyEvent.isConfirmKey(keyCode)) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if (event.getRepeatCount() == 0) {
                boolean clickable = (this.mViewFlags & 16384) == 16384 || (this.mViewFlags & 2097152) == 2097152;
                if (clickable || (this.mViewFlags & 1073741824) == 1073741824) {
                    float x = ((float) getWidth()) / 2.0f;
                    float y = ((float) getHeight()) / 2.0f;
                    if (clickable) {
                        setPressed(true, x, y);
                    }
                    checkForLongClick((long) ViewConfiguration.getLongPressTimeout(), x, y, 0);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        SeempLog.record(5);
        if (KeyEvent.isConfirmKey(keyCode)) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if ((this.mViewFlags & 16384) == 16384 && isPressed()) {
                setPressed(false);
                if (!this.mHasPerformedLongPress) {
                    removeLongPressCallback();
                    if (!event.isCanceled()) {
                        return performClickInternal();
                    }
                }
            }
        }
        return false;
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return false;
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return null;
    }

    public boolean checkInputConnectionProxy(View view) {
        return false;
    }

    public void createContextMenu(ContextMenu menu) {
        ContextMenu.ContextMenuInfo menuInfo = getContextMenuInfo();
        ((MenuBuilder) menu).setCurrentMenuInfo(menuInfo);
        onCreateContextMenu(menu);
        ListenerInfo li = this.mListenerInfo;
        if (!(li == null || li.mOnCreateContextMenuListener == null)) {
            li.mOnCreateContextMenuListener.onCreateContextMenu(menu, this, menuInfo);
        }
        ((MenuBuilder) menu).setCurrentMenuInfo((ContextMenu.ContextMenuInfo) null);
        if (this.mParent != null) {
            this.mParent.createContextMenu(menu);
        }
    }

    /* access modifiers changed from: protected */
    public ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreateContextMenu(ContextMenu menu) {
    }

    public boolean onTrackballEvent(MotionEvent event) {
        return false;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return false;
    }

    private boolean dispatchTouchExplorationHoverEvent(MotionEvent event) {
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (!manager.isEnabled() || !manager.isTouchExplorationEnabled()) {
            return false;
        }
        boolean oldHoveringTouchDelegate = this.mHoveringTouchDelegate;
        int action = event.getActionMasked();
        AccessibilityNodeInfo.TouchDelegateInfo info = this.mTouchDelegate.getTouchDelegateInfo();
        boolean pointInDelegateRegion = false;
        for (int i = 0; i < info.getRegionCount(); i++) {
            if (info.getRegionAt(i).contains((int) event.getX(), (int) event.getY())) {
                pointInDelegateRegion = true;
            }
        }
        if (!oldHoveringTouchDelegate) {
            if ((action == 9 || action == 7) && !pointInHoveredChild(event) && pointInDelegateRegion) {
                this.mHoveringTouchDelegate = true;
            }
        } else if (action == 10 || (action == 7 && (pointInHoveredChild(event) || !pointInDelegateRegion))) {
            this.mHoveringTouchDelegate = false;
        }
        if (action != 7) {
            switch (action) {
                case 9:
                    if (oldHoveringTouchDelegate || !this.mHoveringTouchDelegate) {
                        return false;
                    }
                    return this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                case 10:
                    if (!oldHoveringTouchDelegate) {
                        return false;
                    }
                    this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    return false;
                default:
                    return false;
            }
        } else if (oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
            return this.mTouchDelegate.onTouchExplorationHoverEvent(event);
        } else {
            if (!oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
                MotionEvent eventNoHistory = event.getHistorySize() == 0 ? event : MotionEvent.obtainNoHistory(event);
                eventNoHistory.setAction(9);
                boolean handled = this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory);
                eventNoHistory.setAction(action);
                return this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory) | handled;
            } else if (!oldHoveringTouchDelegate || this.mHoveringTouchDelegate) {
                return false;
            } else {
                boolean hoverExitPending = event.isHoverExitPending();
                event.setHoverExitPending(true);
                this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                MotionEvent eventNoHistory2 = event.getHistorySize() == 0 ? event : MotionEvent.obtainNoHistory(event);
                eventNoHistory2.setHoverExitPending(hoverExitPending);
                eventNoHistory2.setAction(10);
                this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory2);
                return false;
            }
        }
    }

    public boolean onHoverEvent(MotionEvent event) {
        if (this.mTouchDelegate != null && dispatchTouchExplorationHoverEvent(event)) {
            return true;
        }
        int action = event.getActionMasked();
        if (!this.mSendingHoverAccessibilityEvents) {
            if ((action == 9 || action == 7) && !hasHoveredChild() && pointInView(event.getX(), event.getY())) {
                sendAccessibilityHoverEvent(128);
                this.mSendingHoverAccessibilityEvents = true;
            }
        } else if (action == 10 || (action == 7 && !pointInView(event.getX(), event.getY()))) {
            this.mSendingHoverAccessibilityEvents = false;
            sendAccessibilityHoverEvent(256);
        }
        if ((action == 9 || action == 7) && event.isFromSource(8194) && isOnScrollbar(event.getX(), event.getY())) {
            awakenScrollBars();
        }
        if (!isHoverable() && !isHovered()) {
            return false;
        }
        switch (action) {
            case 9:
                setHovered(true);
                break;
            case 10:
                setHovered(false);
                break;
        }
        dispatchGenericMotionEventInternal(event);
        return true;
    }

    private boolean isHoverable() {
        int viewFlags = this.mViewFlags;
        if ((viewFlags & 32) == 32) {
            return false;
        }
        if ((viewFlags & 16384) == 16384 || (viewFlags & 2097152) == 2097152 || (viewFlags & 8388608) == 8388608) {
            return true;
        }
        return false;
    }

    @ViewDebug.ExportedProperty
    public boolean isHovered() {
        return (this.mPrivateFlags & 268435456) != 0;
    }

    public void setHovered(boolean hovered) {
        if (hovered) {
            if ((this.mPrivateFlags & 268435456) == 0) {
                this.mPrivateFlags = 268435456 | this.mPrivateFlags;
                refreshDrawableState();
                onHoverChanged(true);
            }
        } else if ((268435456 & this.mPrivateFlags) != 0) {
            this.mPrivateFlags &= -268435457;
            refreshDrawableState();
            onHoverChanged(false);
        }
    }

    public void onHoverChanged(boolean hovered) {
    }

    /* access modifiers changed from: protected */
    public boolean handleScrollBarDragging(MotionEvent event) {
        MotionEvent motionEvent = event;
        if (this.mScrollCache == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if ((this.mScrollCache.mScrollBarDraggingState != 0 || action == 0) && motionEvent.isFromSource(8194) && motionEvent.isButtonPressed(1)) {
            if (action != 0) {
                if (action == 2) {
                    if (this.mScrollCache.mScrollBarDraggingState == 0) {
                        return false;
                    }
                    if (this.mScrollCache.mScrollBarDraggingState == 1) {
                        Rect bounds = this.mScrollCache.mScrollBarBounds;
                        getVerticalScrollBarBounds(bounds, (Rect) null);
                        int range = computeVerticalScrollRange();
                        int offset = computeVerticalScrollOffset();
                        int extent = computeVerticalScrollExtent();
                        int thumbLength = ScrollBarUtils.getThumbLength(bounds.height(), bounds.width(), extent, range);
                        int thumbOffset = ScrollBarUtils.getThumbOffset(bounds.height(), thumbLength, extent, range, offset);
                        float maxThumbOffset = (float) (bounds.height() - thumbLength);
                        float newThumbOffset = Math.min(Math.max(((float) thumbOffset) + (y - this.mScrollCache.mScrollBarDraggingPos), 0.0f), maxThumbOffset);
                        int height = getHeight();
                        if (Math.round(newThumbOffset) == thumbOffset || maxThumbOffset <= 0.0f || height <= 0 || extent <= 0) {
                            return true;
                        }
                        Rect rect = bounds;
                        int newY = Math.round((((float) (range - extent)) / (((float) extent) / ((float) height))) * (newThumbOffset / maxThumbOffset));
                        if (newY == getScrollY()) {
                            return true;
                        }
                        this.mScrollCache.mScrollBarDraggingPos = y;
                        setScrollY(newY);
                        return true;
                    } else if (this.mScrollCache.mScrollBarDraggingState == 2) {
                        Rect bounds2 = this.mScrollCache.mScrollBarBounds;
                        getHorizontalScrollBarBounds(bounds2, (Rect) null);
                        int range2 = computeHorizontalScrollRange();
                        int offset2 = computeHorizontalScrollOffset();
                        int extent2 = computeHorizontalScrollExtent();
                        int thumbLength2 = ScrollBarUtils.getThumbLength(bounds2.width(), bounds2.height(), extent2, range2);
                        int thumbOffset2 = ScrollBarUtils.getThumbOffset(bounds2.width(), thumbLength2, extent2, range2, offset2);
                        float maxThumbOffset2 = (float) (bounds2.width() - thumbLength2);
                        float newThumbOffset2 = Math.min(Math.max(((float) thumbOffset2) + (x - this.mScrollCache.mScrollBarDraggingPos), 0.0f), maxThumbOffset2);
                        int width = getWidth();
                        if (Math.round(newThumbOffset2) == thumbOffset2 || maxThumbOffset2 <= 0.0f || width <= 0 || extent2 <= 0) {
                            return true;
                        }
                        Rect rect2 = bounds2;
                        int newX = Math.round((((float) (range2 - extent2)) / (((float) extent2) / ((float) width))) * (newThumbOffset2 / maxThumbOffset2));
                        if (newX == getScrollX()) {
                            return true;
                        }
                        this.mScrollCache.mScrollBarDraggingPos = x;
                        setScrollX(newX);
                        return true;
                    }
                }
                this.mScrollCache.mScrollBarDraggingState = 0;
                return false;
            }
            if (this.mScrollCache.state == 0) {
                return false;
            }
            if (isOnVerticalScrollbarThumb(x, y)) {
                this.mScrollCache.mScrollBarDraggingState = 1;
                this.mScrollCache.mScrollBarDraggingPos = y;
                return true;
            }
            if (isOnHorizontalScrollbarThumb(x, y)) {
                this.mScrollCache.mScrollBarDraggingState = 2;
                this.mScrollCache.mScrollBarDraggingPos = x;
                return true;
            }
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        this.mScrollCache.mScrollBarDraggingState = 0;
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int motionClassification;
        int touchSlop;
        int touchSlop2;
        SeempLog.record(3);
        float x = event.getX();
        float y = event.getY();
        int viewFlags = this.mViewFlags;
        int action = event.getAction();
        boolean deepPress = false;
        boolean clickable = (viewFlags & 16384) == 16384 || (viewFlags & 2097152) == 2097152 || (viewFlags & 8388608) == 8388608;
        if ((viewFlags & 32) == 32) {
            if (action == 1 && (this.mPrivateFlags & 16384) != 0) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            return clickable;
        }
        if (this.mTouchDelegate == null) {
            MotionEvent motionEvent = event;
        } else if (this.mTouchDelegate.onTouchEvent(event)) {
            return true;
        }
        if (!clickable && (viewFlags & 1073741824) != 1073741824) {
            return false;
        }
        switch (action) {
            case 0:
                if (event.getSource() == 4098) {
                    this.mPrivateFlags3 |= 131072;
                }
                this.mHasPerformedLongPress = false;
                if (!clickable) {
                    checkForLongClick((long) ViewConfiguration.getLongPressTimeout(), x, y, 3);
                    return true;
                } else if (performButtonActionOnTouchDown(event)) {
                    return true;
                } else {
                    if (isInScrollingContainer()) {
                        this.mPrivateFlags |= 33554432;
                        if (this.mPendingCheckForTap == null) {
                            this.mPendingCheckForTap = new CheckForTap();
                        }
                        this.mPendingCheckForTap.x = event.getX();
                        this.mPendingCheckForTap.y = event.getY();
                        postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
                        return true;
                    }
                    setPressed(true, x, y);
                    checkForLongClick((long) ViewConfiguration.getLongPressTimeout(), x, y, 3);
                    return true;
                }
            case 1:
                this.mPrivateFlags3 &= -131073;
                if ((viewFlags & 1073741824) == 1073741824) {
                    handleTooltipUp();
                }
                if (!clickable) {
                    removeTapCallback();
                    removeLongPressCallback();
                    this.mInContextButtonPress = false;
                    this.mHasPerformedLongPress = false;
                    this.mIgnoreNextUpEvent = false;
                    return true;
                }
                boolean prepressed = (this.mPrivateFlags & 33554432) != 0;
                if ((this.mPrivateFlags & 16384) != 0 || prepressed) {
                    boolean focusTaken = false;
                    if (isFocusable() && isFocusableInTouchMode() && !isFocused()) {
                        focusTaken = requestFocus();
                    }
                    if (prepressed) {
                        setPressed(true, x, y);
                    }
                    if (!this.mHasPerformedLongPress && !this.mIgnoreNextUpEvent) {
                        removeLongPressCallback();
                        if (!focusTaken) {
                            if (this.mPerformClick == null) {
                                this.mPerformClick = new PerformClick();
                            }
                            if (!post(this.mPerformClick)) {
                                performClickInternal();
                            }
                        }
                    }
                    if (this.mUnsetPressedState == null) {
                        this.mUnsetPressedState = new UnsetPressedState();
                    }
                    if (prepressed) {
                        postDelayed(this.mUnsetPressedState, (long) ViewConfiguration.getPressedStateDuration());
                    } else if (!post(this.mUnsetPressedState)) {
                        this.mUnsetPressedState.run();
                    }
                    removeTapCallback();
                }
                this.mIgnoreNextUpEvent = false;
                return true;
            case 2:
                if (clickable) {
                    drawableHotspotChanged(x, y);
                }
                int motionClassification2 = event.getClassification();
                boolean ambiguousGesture = motionClassification2 == 1;
                int touchSlop3 = this.mTouchSlop;
                if (!ambiguousGesture || !hasPendingLongPressCallback()) {
                    touchSlop = touchSlop3;
                    motionClassification = motionClassification2;
                } else {
                    float ambiguousMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
                    if (!pointInView(x, y, (float) touchSlop3)) {
                        removeLongPressCallback();
                        touchSlop2 = touchSlop3;
                        motionClassification = motionClassification2;
                        checkForLongClick(((long) (((float) ViewConfiguration.getLongPressTimeout()) * ambiguousMultiplier)) - (event.getEventTime() - event.getDownTime()), x, y, 3);
                    } else {
                        touchSlop2 = touchSlop3;
                        motionClassification = motionClassification2;
                    }
                    touchSlop = (int) (((float) touchSlop2) * ambiguousMultiplier);
                }
                if (!pointInView(x, y, (float) touchSlop)) {
                    removeTapCallback();
                    removeLongPressCallback();
                    if ((this.mPrivateFlags & 16384) != 0) {
                        setPressed(false);
                    }
                    this.mPrivateFlags3 &= -131073;
                }
                if (motionClassification == 2) {
                    deepPress = true;
                }
                if (!deepPress || !hasPendingLongPressCallback()) {
                    return true;
                }
                removeLongPressCallback();
                checkForLongClick(0, x, y, 4);
                return true;
            case 3:
                if (clickable) {
                    setPressed(false);
                }
                removeTapCallback();
                removeLongPressCallback();
                this.mInContextButtonPress = false;
                this.mHasPerformedLongPress = false;
                this.mIgnoreNextUpEvent = false;
                this.mPrivateFlags3 &= -131073;
                return true;
            default:
                return true;
        }
    }

    @UnsupportedAppUsage
    public boolean isInScrollingContainer() {
        ViewParent p = getParent();
        while (p != null && (p instanceof ViewGroup)) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
        return false;
    }

    private void removeLongPressCallback() {
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
    }

    private boolean hasPendingLongPressCallback() {
        AttachInfo attachInfo;
        if (this.mPendingCheckForLongPress == null || (attachInfo = this.mAttachInfo) == null) {
            return false;
        }
        return attachInfo.mHandler.hasCallbacks(this.mPendingCheckForLongPress);
    }

    @UnsupportedAppUsage
    private void removePerformClickCallback() {
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
    }

    private void removeUnsetPressCallback() {
        if ((this.mPrivateFlags & 16384) != 0 && this.mUnsetPressedState != null) {
            setPressed(false);
            removeCallbacks(this.mUnsetPressedState);
        }
    }

    private void removeTapCallback() {
        if (this.mPendingCheckForTap != null) {
            this.mPrivateFlags &= -33554433;
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    public void cancelLongPress() {
        removeLongPressCallback();
        removeTapCallback();
    }

    public void setTouchDelegate(TouchDelegate delegate) {
        this.mTouchDelegate = delegate;
    }

    public TouchDelegate getTouchDelegate() {
        return this.mTouchDelegate;
    }

    public final void requestUnbufferedDispatch(MotionEvent event) {
        int action = event.getAction();
        if (this.mAttachInfo == null) {
            return;
        }
        if ((action == 0 || action == 2) && event.isTouchEvent()) {
            this.mAttachInfo.mUnbufferedDispatchRequested = true;
        }
    }

    private boolean hasSize() {
        return this.mBottom > this.mTop && this.mRight > this.mLeft;
    }

    private boolean canTakeFocus() {
        if ((this.mViewFlags & 12) == 0 && (this.mViewFlags & 1) == 1 && (this.mViewFlags & 32) == 0 && (sCanFocusZeroSized || !isLayoutValid() || hasSize())) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void setFlags(int flags, int mask) {
        int newFocus;
        boolean accessibilityEnabled = AccessibilityManager.getInstance(this.mContext).isEnabled();
        boolean oldIncludeForAccessibility = accessibilityEnabled && includeForAccessibility();
        int old = this.mViewFlags;
        this.mViewFlags = (this.mViewFlags & (~mask)) | (flags & mask);
        int changed = this.mViewFlags ^ old;
        if (changed != 0) {
            int privateFlags = this.mPrivateFlags;
            boolean shouldNotifyFocusableAvailable = false;
            int focusableChangedByAuto = 0;
            if (!((this.mViewFlags & 16) == 0 || (changed & BatteryStats.HistoryItem.EVENT_TEMP_WHITELIST_FINISH) == 0)) {
                if ((this.mViewFlags & 16384) != 0) {
                    newFocus = 1;
                } else {
                    newFocus = 0;
                }
                this.mViewFlags = (this.mViewFlags & -2) | newFocus;
                focusableChangedByAuto = (old & 1) ^ (newFocus & 1);
                changed = (changed & -2) | focusableChangedByAuto;
            }
            if (!((changed & 1) == 0 || (privateFlags & 16) == 0)) {
                if ((old & 1) == 1 && (privateFlags & 2) != 0) {
                    clearFocus();
                    if (this.mParent instanceof ViewGroup) {
                        ((ViewGroup) this.mParent).clearFocusedInCluster();
                    }
                } else if ((old & 1) == 0 && (privateFlags & 2) == 0 && this.mParent != null) {
                    ViewRootImpl viewRootImpl = getViewRootImpl();
                    if (!sAutoFocusableOffUIThreadWontNotifyParents || focusableChangedByAuto == 0 || viewRootImpl == null || viewRootImpl.mThread == Thread.currentThread()) {
                        shouldNotifyFocusableAvailable = canTakeFocus();
                    }
                }
            }
            int newVisibility = flags & 12;
            if (newVisibility == 0 && (changed & 12) != 0) {
                this.mPrivateFlags |= 32;
                invalidate(true);
                needGlobalAttributesUpdate(true);
                shouldNotifyFocusableAvailable = hasSize();
            }
            if ((changed & 32) != 0) {
                if ((this.mViewFlags & 32) == 0) {
                    shouldNotifyFocusableAvailable = canTakeFocus();
                } else if (isFocused()) {
                    clearFocus();
                }
            }
            if (shouldNotifyFocusableAvailable && this.mParent != null) {
                this.mParent.focusableViewAvailable(this);
            }
            if ((changed & 8) != 0) {
                needGlobalAttributesUpdate(false);
                requestLayout();
                if ((this.mViewFlags & 12) == 8) {
                    if (hasFocus()) {
                        clearFocus();
                        if (this.mParent instanceof ViewGroup) {
                            ((ViewGroup) this.mParent).clearFocusedInCluster();
                        }
                    }
                    clearAccessibilityFocus();
                    destroyDrawingCache();
                    if (this.mParent instanceof View) {
                        ((View) this.mParent).invalidate(true);
                    }
                    this.mPrivateFlags |= 32;
                }
                if (this.mAttachInfo != null) {
                    this.mAttachInfo.mViewVisibilityChanged = true;
                }
            }
            if ((changed & 4) != 0) {
                needGlobalAttributesUpdate(false);
                this.mPrivateFlags |= 32;
                if ((this.mViewFlags & 12) == 4 && getRootView() != this) {
                    if (hasFocus()) {
                        clearFocus();
                        if (this.mParent instanceof ViewGroup) {
                            ((ViewGroup) this.mParent).clearFocusedInCluster();
                        }
                    }
                    clearAccessibilityFocus();
                }
                if (this.mAttachInfo != null) {
                    this.mAttachInfo.mViewVisibilityChanged = true;
                }
            }
            if ((changed & 12) != 0) {
                if (!(newVisibility == 0 || this.mAttachInfo == null)) {
                    cleanupDraw();
                }
                if (this.mParent instanceof ViewGroup) {
                    ViewGroup parent = (ViewGroup) this.mParent;
                    parent.onChildVisibilityChanged(this, changed & 12, newVisibility);
                    parent.invalidate(true);
                } else if (this.mParent != null) {
                    this.mParent.invalidateChild(this, (Rect) null);
                }
                if (this.mAttachInfo != null) {
                    dispatchVisibilityChanged(this, newVisibility);
                    if (this.mParent != null && getWindowVisibility() == 0 && (!(this.mParent instanceof ViewGroup) || ((ViewGroup) this.mParent).isShown())) {
                        dispatchVisibilityAggregated(newVisibility == 0);
                    }
                    notifySubtreeAccessibilityStateChangedIfNeeded();
                }
            }
            if ((131072 & changed) != 0) {
                destroyDrawingCache();
            }
            if ((32768 & changed) != 0) {
                destroyDrawingCache();
                this.mPrivateFlags &= -32769;
                invalidateParentCaches();
            }
            if ((DRAWING_CACHE_QUALITY_MASK & changed) != 0) {
                destroyDrawingCache();
                this.mPrivateFlags &= -32769;
            }
            if ((changed & 128) != 0) {
                if ((this.mViewFlags & 128) == 0) {
                    this.mPrivateFlags &= -129;
                } else if (this.mBackground == null && this.mDefaultFocusHighlight == null && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
                    this.mPrivateFlags |= 128;
                } else {
                    this.mPrivateFlags &= -129;
                }
                requestLayout();
                invalidate(true);
            }
            if (!((67108864 & changed) == 0 || this.mParent == null || this.mAttachInfo == null || this.mAttachInfo.mRecomputeGlobalAttributes)) {
                this.mParent.recomputeViewAttributes(this);
            }
            if (accessibilityEnabled) {
                if (isAccessibilityPane()) {
                    changed &= -13;
                }
                if ((changed & 1) == 0 && (changed & 12) == 0 && (changed & 16384) == 0 && (2097152 & changed) == 0 && (8388608 & changed) == 0) {
                    if ((changed & 32) != 0) {
                        notifyViewAccessibilityStateChangedIfNeeded(0);
                    }
                } else if (oldIncludeForAccessibility != includeForAccessibility()) {
                    notifySubtreeAccessibilityStateChangedIfNeeded();
                } else {
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                }
            }
        }
    }

    public void bringToFront() {
        if (this.mParent != null) {
            this.mParent.bringChildToFront(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        notifySubtreeAccessibilityStateChangedIfNeeded();
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            postSendViewScrolledAccessibilityEventCallback(l - oldl, t - oldt);
        }
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (this.mForegroundInfo != null) {
            boolean unused = this.mForegroundInfo.mBoundsChanged = true;
        }
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewScrollChanged = true;
        }
        if (this.mListenerInfo != null && this.mListenerInfo.mOnScrollChangeListener != null) {
            this.mListenerInfo.mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
    }

    public final ViewParent getParent() {
        return this.mParent;
    }

    public void setScrollX(int value) {
        scrollTo(value, this.mScrollY);
    }

    public void setScrollY(int value) {
        scrollTo(this.mScrollX, value);
    }

    public final int getScrollX() {
        return this.mScrollX;
    }

    public final int getScrollY() {
        return this.mScrollY;
    }

    @ViewDebug.ExportedProperty(category = "layout")
    public final int getWidth() {
        return this.mRight - this.mLeft;
    }

    @ViewDebug.ExportedProperty(category = "layout")
    public final int getHeight() {
        return this.mBottom - this.mTop;
    }

    public void getDrawingRect(Rect outRect) {
        outRect.left = this.mScrollX;
        outRect.top = this.mScrollY;
        outRect.right = this.mScrollX + (this.mRight - this.mLeft);
        outRect.bottom = this.mScrollY + (this.mBottom - this.mTop);
    }

    public final int getMeasuredWidth() {
        return this.mMeasuredWidth & 16777215;
    }

    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = 16777216, mask = -16777216, name = "MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredWidthAndState() {
        return this.mMeasuredWidth;
    }

    public final int getMeasuredHeight() {
        return this.mMeasuredHeight & 16777215;
    }

    @ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@ViewDebug.FlagToString(equals = 16777216, mask = -16777216, name = "MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredHeightAndState() {
        return this.mMeasuredHeight;
    }

    public final int getMeasuredState() {
        return (this.mMeasuredWidth & -16777216) | ((this.mMeasuredHeight >> 16) & -256);
    }

    public Matrix getMatrix() {
        ensureTransformationInfo();
        Matrix matrix = this.mTransformationInfo.mMatrix;
        this.mRenderNode.getMatrix(matrix);
        return matrix;
    }

    @UnsupportedAppUsage
    public final boolean hasIdentityMatrix() {
        return this.mRenderNode.hasIdentityMatrix();
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void ensureTransformationInfo() {
        if (this.mTransformationInfo == null) {
            this.mTransformationInfo = new TransformationInfo();
        }
    }

    @UnsupportedAppUsage
    public final Matrix getInverseMatrix() {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mInverseMatrix == null) {
            Matrix unused = this.mTransformationInfo.mInverseMatrix = new Matrix();
        }
        Matrix matrix = this.mTransformationInfo.mInverseMatrix;
        this.mRenderNode.getInverseMatrix(matrix);
        return matrix;
    }

    public float getCameraDistance() {
        return this.mRenderNode.getCameraDistance() * ((float) this.mResources.getDisplayMetrics().densityDpi);
    }

    public void setCameraDistance(float distance) {
        invalidateViewProperty(true, false);
        this.mRenderNode.setCameraDistance(Math.abs(distance) / ((float) this.mResources.getDisplayMetrics().densityDpi));
        invalidateViewProperty(false, false);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotation() {
        return this.mRenderNode.getRotationZ();
    }

    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationZ(rotation);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotationY() {
        return this.mRenderNode.getRotationY();
    }

    public void setRotationY(float rotationY) {
        if (rotationY != getRotationY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationY(rotationY);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getRotationX() {
        return this.mRenderNode.getRotationX();
    }

    public void setRotationX(float rotationX) {
        if (rotationX != getRotationX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationX(rotationX);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getScaleX() {
        return this.mRenderNode.getScaleX();
    }

    public void setScaleX(float scaleX) {
        if (scaleX != getScaleX()) {
            float scaleX2 = sanitizeFloatPropertyValue(scaleX, "scaleX");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleX(scaleX2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getScaleY() {
        return this.mRenderNode.getScaleY();
    }

    public void setScaleY(float scaleY) {
        if (scaleY != getScaleY()) {
            float scaleY2 = sanitizeFloatPropertyValue(scaleY, "scaleY");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleY(scaleY2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotX() {
        return this.mRenderNode.getPivotX();
    }

    public void setPivotX(float pivotX) {
        if (!this.mRenderNode.isPivotExplicitlySet() || pivotX != getPivotX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setPivotX(pivotX);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotY() {
        return this.mRenderNode.getPivotY();
    }

    public void setPivotY(float pivotY) {
        if (!this.mRenderNode.isPivotExplicitlySet() || pivotY != getPivotY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setPivotY(pivotY);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public boolean isPivotSet() {
        return this.mRenderNode.isPivotExplicitlySet();
    }

    public void resetPivot() {
        if (this.mRenderNode.resetPivot()) {
            invalidateViewProperty(false, false);
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mAlpha;
        }
        return 1.0f;
    }

    public void forceHasOverlappingRendering(boolean hasOverlappingRendering) {
        this.mPrivateFlags3 |= 16777216;
        if (hasOverlappingRendering) {
            this.mPrivateFlags3 |= 8388608;
        } else {
            this.mPrivateFlags3 &= -8388609;
        }
    }

    public final boolean getHasOverlappingRendering() {
        if ((this.mPrivateFlags3 & 16777216) != 0) {
            return (this.mPrivateFlags3 & 8388608) != 0;
        }
        return hasOverlappingRendering();
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasOverlappingRendering() {
        return true;
    }

    public void setAlpha(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != alpha) {
            setAlphaInternal(alpha);
            if (onSetAlpha((int) (255.0f * alpha))) {
                this.mPrivateFlags |= 262144;
                invalidateParentCaches();
                invalidate(true);
                return;
            }
            this.mPrivateFlags &= -262145;
            invalidateViewProperty(true, false);
            this.mRenderNode.setAlpha(getFinalAlpha());
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768435)
    public boolean setAlphaNoInvalidation(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha == alpha) {
            return false;
        }
        setAlphaInternal(alpha);
        if (onSetAlpha((int) (255.0f * alpha))) {
            this.mPrivateFlags |= 262144;
            return true;
        }
        this.mPrivateFlags &= -262145;
        this.mRenderNode.setAlpha(getFinalAlpha());
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setAlphaInternal(float alpha) {
        float oldAlpha = this.mTransformationInfo.mAlpha;
        float unused = this.mTransformationInfo.mAlpha = alpha;
        boolean z = false;
        boolean z2 = alpha == 0.0f;
        if (oldAlpha == 0.0f) {
            z = true;
        }
        if (z2 ^ z) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionAlpha(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mTransitionAlpha != alpha) {
            this.mTransformationInfo.mTransitionAlpha = alpha;
            this.mPrivateFlags &= -262145;
            invalidateViewProperty(true, false);
            this.mRenderNode.setAlpha(getFinalAlpha());
        }
    }

    private float getFinalAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mAlpha * this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTransitionAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    public void setForceDarkAllowed(boolean allow) {
        if (this.mRenderNode.setForceDarkAllowed(allow)) {
            invalidate();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isForceDarkAllowed() {
        return this.mRenderNode.isForceDarkAllowed();
    }

    @ViewDebug.CapturedViewProperty
    public final int getTop() {
        return this.mTop;
    }

    public final void setTop(int top) {
        int yLoc;
        int minTop;
        if (top != this.mTop) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (!matrixIsIdentity) {
                invalidate(true);
            } else if (this.mAttachInfo != null) {
                if (top < this.mTop) {
                    minTop = top;
                    yLoc = top - this.mTop;
                } else {
                    minTop = this.mTop;
                    yLoc = 0;
                }
                invalidate(0, yLoc, this.mRight - this.mLeft, this.mBottom - minTop);
            }
            int width = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            this.mTop = top;
            this.mRenderNode.setTop(this.mTop);
            sizeChange(width, this.mBottom - this.mTop, width, oldHeight);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                boolean unused = this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                invalidateParentIfNeeded();
            }
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getBottom() {
        return this.mBottom;
    }

    public boolean isDirty() {
        return (this.mPrivateFlags & 2097152) != 0;
    }

    public final void setBottom(int bottom) {
        int maxBottom;
        if (bottom != this.mBottom) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (!matrixIsIdentity) {
                invalidate(true);
            } else if (this.mAttachInfo != null) {
                if (bottom < this.mBottom) {
                    maxBottom = this.mBottom;
                } else {
                    maxBottom = bottom;
                }
                invalidate(0, 0, this.mRight - this.mLeft, maxBottom - this.mTop);
            }
            int width = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            this.mBottom = bottom;
            this.mRenderNode.setBottom(this.mBottom);
            sizeChange(width, this.mBottom - this.mTop, width, oldHeight);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                boolean unused = this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                invalidateParentIfNeeded();
            }
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getLeft() {
        return this.mLeft;
    }

    public final void setLeft(int left) {
        int xLoc;
        int minLeft;
        if (left != this.mLeft) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (!matrixIsIdentity) {
                invalidate(true);
            } else if (this.mAttachInfo != null) {
                if (left < this.mLeft) {
                    minLeft = left;
                    xLoc = left - this.mLeft;
                } else {
                    minLeft = this.mLeft;
                    xLoc = 0;
                }
                invalidate(xLoc, 0, this.mRight - minLeft, this.mBottom - this.mTop);
            }
            int oldWidth = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            this.mLeft = left;
            this.mRenderNode.setLeft(left);
            sizeChange(this.mRight - this.mLeft, height, oldWidth, height);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                boolean unused = this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                invalidateParentIfNeeded();
            }
        }
    }

    @ViewDebug.CapturedViewProperty
    public final int getRight() {
        return this.mRight;
    }

    public final void setRight(int right) {
        int maxRight;
        if (right != this.mRight) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (!matrixIsIdentity) {
                invalidate(true);
            } else if (this.mAttachInfo != null) {
                if (right < this.mRight) {
                    maxRight = this.mRight;
                } else {
                    maxRight = right;
                }
                invalidate(0, 0, maxRight - this.mLeft, this.mBottom - this.mTop);
            }
            int oldWidth = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            this.mRight = right;
            this.mRenderNode.setRight(this.mRight);
            sizeChange(this.mRight - this.mLeft, height, oldWidth, height);
            if (!matrixIsIdentity) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                boolean unused = this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                invalidateParentIfNeeded();
            }
        }
    }

    private static float sanitizeFloatPropertyValue(float value, String propertyName) {
        return sanitizeFloatPropertyValue(value, propertyName, -3.4028235E38f, Float.MAX_VALUE);
    }

    private static float sanitizeFloatPropertyValue(float value, String propertyName, float min, float max) {
        if (value >= min && value <= max) {
            return value;
        }
        if (value < min || value == Float.NEGATIVE_INFINITY) {
            if (!sThrowOnInvalidFloatProperties) {
                return min;
            }
            throw new IllegalArgumentException("Cannot set '" + propertyName + "' to " + value + ", the value must be >= " + min);
        } else if (value > max || value == Float.POSITIVE_INFINITY) {
            if (!sThrowOnInvalidFloatProperties) {
                return max;
            }
            throw new IllegalArgumentException("Cannot set '" + propertyName + "' to " + value + ", the value must be <= " + max);
        } else if (!Float.isNaN(value)) {
            throw new IllegalStateException("How do you get here?? " + value);
        } else if (!sThrowOnInvalidFloatProperties) {
            return 0.0f;
        } else {
            throw new IllegalArgumentException("Cannot set '" + propertyName + "' to Float.NaN");
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getX() {
        return ((float) this.mLeft) + getTranslationX();
    }

    public void setX(float x) {
        setTranslationX(x - ((float) this.mLeft));
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getY() {
        return ((float) this.mTop) + getTranslationY();
    }

    public void setY(float y) {
        setTranslationY(y - ((float) this.mTop));
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    public void setZ(float z) {
        setTranslationZ(z - getElevation());
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getElevation() {
        return this.mRenderNode.getElevation();
    }

    public void setElevation(float elevation) {
        if (elevation != getElevation()) {
            float elevation2 = sanitizeFloatPropertyValue(elevation, "elevation");
            invalidateViewProperty(true, false);
            this.mRenderNode.setElevation(elevation2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationX() {
        return this.mRenderNode.getTranslationX();
    }

    public void setTranslationX(float translationX) {
        if (translationX != getTranslationX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationX(translationX);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationY() {
        return this.mRenderNode.getTranslationY();
    }

    public void setTranslationY(float translationY) {
        if (translationY != getTranslationY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationY(translationY);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationZ() {
        return this.mRenderNode.getTranslationZ();
    }

    public void setTranslationZ(float translationZ) {
        if (translationZ != getTranslationZ()) {
            float translationZ2 = sanitizeFloatPropertyValue(translationZ, "translationZ");
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationZ(translationZ2);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public void setAnimationMatrix(Matrix matrix) {
        invalidateViewProperty(true, false);
        this.mRenderNode.setAnimationMatrix(matrix);
        invalidateViewProperty(false, true);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    public Matrix getAnimationMatrix() {
        return this.mRenderNode.getAnimationMatrix();
    }

    public StateListAnimator getStateListAnimator() {
        return this.mStateListAnimator;
    }

    public void setStateListAnimator(StateListAnimator stateListAnimator) {
        if (this.mStateListAnimator != stateListAnimator) {
            if (this.mStateListAnimator != null) {
                this.mStateListAnimator.setTarget((View) null);
            }
            this.mStateListAnimator = stateListAnimator;
            if (stateListAnimator != null) {
                stateListAnimator.setTarget(this);
                if (isAttachedToWindow()) {
                    stateListAnimator.setState(getDrawableState());
                }
            }
        }
    }

    public final boolean getClipToOutline() {
        return this.mRenderNode.getClipToOutline();
    }

    public void setClipToOutline(boolean clipToOutline) {
        damageInParent();
        if (getClipToOutline() != clipToOutline) {
            this.mRenderNode.setClipToOutline(clipToOutline);
        }
    }

    private void setOutlineProviderFromAttribute(int providerInt) {
        switch (providerInt) {
            case 0:
                setOutlineProvider(ViewOutlineProvider.BACKGROUND);
                return;
            case 1:
                setOutlineProvider((ViewOutlineProvider) null);
                return;
            case 2:
                setOutlineProvider(ViewOutlineProvider.BOUNDS);
                return;
            case 3:
                setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);
                return;
            default:
                return;
        }
    }

    public void setOutlineProvider(ViewOutlineProvider provider) {
        this.mOutlineProvider = provider;
        invalidateOutline();
    }

    public ViewOutlineProvider getOutlineProvider() {
        return this.mOutlineProvider;
    }

    public void invalidateOutline() {
        rebuildOutline();
        notifySubtreeAccessibilityStateChangedIfNeeded();
        invalidateViewProperty(false, false);
    }

    private void rebuildOutline() {
        if (this.mAttachInfo != null) {
            if (this.mOutlineProvider == null) {
                this.mRenderNode.setOutline((Outline) null);
                return;
            }
            Outline outline = this.mAttachInfo.mTmpOutline;
            outline.setEmpty();
            outline.setAlpha(1.0f);
            this.mOutlineProvider.getOutline(this, outline);
            this.mRenderNode.setOutline(outline);
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasShadow() {
        return this.mRenderNode.hasShadow();
    }

    public void setOutlineSpotShadowColor(int color) {
        if (this.mRenderNode.setSpotShadowColor(color)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineSpotShadowColor() {
        return this.mRenderNode.getSpotShadowColor();
    }

    public void setOutlineAmbientShadowColor(int color) {
        if (this.mRenderNode.setAmbientShadowColor(color)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineAmbientShadowColor() {
        return this.mRenderNode.getAmbientShadowColor();
    }

    public void setRevealClip(boolean shouldClip, float x, float y, float radius) {
        this.mRenderNode.setRevealClip(shouldClip, x, y, radius);
        invalidateViewProperty(false, false);
    }

    public void getHitRect(Rect outRect) {
        if (hasIdentityMatrix() || this.mAttachInfo == null) {
            outRect.set(this.mLeft, this.mTop, this.mRight, this.mBottom);
            return;
        }
        RectF tmpRect = this.mAttachInfo.mTmpTransformRect;
        tmpRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        getMatrix().mapRect(tmpRect);
        outRect.set(((int) tmpRect.left) + this.mLeft, ((int) tmpRect.top) + this.mTop, ((int) tmpRect.right) + this.mLeft, ((int) tmpRect.bottom) + this.mTop);
    }

    /* access modifiers changed from: package-private */
    public final boolean pointInView(float localX, float localY) {
        return pointInView(localX, localY, 0.0f);
    }

    @UnsupportedAppUsage
    public boolean pointInView(float localX, float localY, float slop) {
        return localX >= (-slop) && localY >= (-slop) && localX < ((float) (this.mRight - this.mLeft)) + slop && localY < ((float) (this.mBottom - this.mTop)) + slop;
    }

    public void getFocusedRect(Rect r) {
        getDrawingRect(r);
    }

    public boolean getGlobalVisibleRect(Rect r, Point globalOffset) {
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        if (width <= 0 || height <= 0) {
            return false;
        }
        r.set(0, 0, width, height);
        if (globalOffset != null) {
            globalOffset.set(-this.mScrollX, -this.mScrollY);
        }
        if (this.mParent == null || this.mParent.getChildVisibleRect(this, r, globalOffset)) {
            return true;
        }
        return false;
    }

    public final boolean getGlobalVisibleRect(Rect r) {
        return getGlobalVisibleRect(r, (Point) null);
    }

    public final boolean getLocalVisibleRect(Rect r) {
        Point offset = this.mAttachInfo != null ? this.mAttachInfo.mPoint : new Point();
        if (!getGlobalVisibleRect(r, offset)) {
            return false;
        }
        r.offset(-offset.x, -offset.y);
        return true;
    }

    public void offsetTopAndBottom(int offset) {
        int yLoc;
        int maxBottom;
        int minTop;
        if (offset != 0) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (!matrixIsIdentity) {
                invalidateViewProperty(false, false);
            } else if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
            } else {
                ViewParent p = this.mParent;
                if (!(p == null || this.mAttachInfo == null)) {
                    Rect r = this.mAttachInfo.mTmpInvalRect;
                    if (offset < 0) {
                        minTop = this.mTop + offset;
                        maxBottom = this.mBottom;
                        yLoc = offset;
                    } else {
                        minTop = this.mTop;
                        maxBottom = this.mBottom + offset;
                        yLoc = 0;
                    }
                    r.set(0, yLoc, this.mRight - this.mLeft, maxBottom - minTop);
                    p.invalidateChild(this, r);
                }
            }
            this.mTop += offset;
            this.mBottom += offset;
            this.mRenderNode.offsetTopAndBottom(offset);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!matrixIsIdentity) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void offsetLeftAndRight(int offset) {
        int maxRight;
        int minLeft;
        if (offset != 0) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (!matrixIsIdentity) {
                invalidateViewProperty(false, false);
            } else if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
            } else {
                ViewParent p = this.mParent;
                if (!(p == null || this.mAttachInfo == null)) {
                    Rect r = this.mAttachInfo.mTmpInvalRect;
                    if (offset < 0) {
                        minLeft = this.mLeft + offset;
                        maxRight = this.mRight;
                    } else {
                        minLeft = this.mLeft;
                        maxRight = this.mRight + offset;
                    }
                    r.set(0, 0, maxRight - minLeft, this.mBottom - this.mTop);
                    p.invalidateChild(this, r);
                }
            }
            this.mLeft += offset;
            this.mRight += offset;
            this.mRenderNode.offsetLeftAndRight(offset);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!matrixIsIdentity) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(deepExport = true, prefix = "layout_")
    public ViewGroup.LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        if (params != null) {
            this.mLayoutParams = params;
            resolveLayoutParams();
            if (this.mParent instanceof ViewGroup) {
                ((ViewGroup) this.mParent).onSetLayoutParams(this, params);
            }
            requestLayout();
            return;
        }
        throw new NullPointerException("Layout parameters cannot be null");
    }

    public void resolveLayoutParams() {
        if (this.mLayoutParams != null) {
            this.mLayoutParams.resolveLayoutDirection(getLayoutDirection());
        }
    }

    public void scrollTo(int x, int y) {
        if (this.mScrollX != x || this.mScrollY != y) {
            int oldX = this.mScrollX;
            int oldY = this.mScrollY;
            this.mScrollX = x;
            this.mScrollY = y;
            invalidateParentCaches();
            onScrollChanged(this.mScrollX, this.mScrollY, oldX, oldY);
            if (!awakenScrollBars()) {
                postInvalidateOnAnimation();
            }
        }
    }

    public void scrollBy(int x, int y) {
        scrollTo(this.mScrollX + x, this.mScrollY + y);
    }

    /* access modifiers changed from: protected */
    public boolean awakenScrollBars() {
        if (this.mScrollCache == null || !awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade, true)) {
            return false;
        }
        return true;
    }

    private boolean initialAwakenScrollBars() {
        if (this.mScrollCache == null || !awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade * 4, true)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean awakenScrollBars(int startDelay) {
        return awakenScrollBars(startDelay, true);
    }

    /* access modifiers changed from: protected */
    public boolean awakenScrollBars(int startDelay, boolean invalidate) {
        ScrollabilityCache scrollCache = this.mScrollCache;
        if (scrollCache == null || !scrollCache.fadeScrollBars) {
            return false;
        }
        if (scrollCache.scrollBar == null) {
            scrollCache.scrollBar = new ScrollBarDrawable();
            scrollCache.scrollBar.setState(getDrawableState());
            scrollCache.scrollBar.setCallback(this);
        }
        if (!isHorizontalScrollBarEnabled() && !isVerticalScrollBarEnabled()) {
            return false;
        }
        if (invalidate) {
            postInvalidateOnAnimation();
        }
        if (scrollCache.state == 0) {
            startDelay = Math.max(750, startDelay);
        }
        long fadeStartTime = AnimationUtils.currentAnimationTimeMillis() + ((long) startDelay);
        scrollCache.fadeStartTime = fadeStartTime;
        scrollCache.state = 1;
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mHandler.removeCallbacks(scrollCache);
            this.mAttachInfo.mHandler.postAtTime(scrollCache, fadeStartTime);
        }
        return true;
    }

    private boolean skipInvalidate() {
        return (this.mViewFlags & 12) != 0 && this.mCurrentAnimation == null && (!(this.mParent instanceof ViewGroup) || !((ViewGroup) this.mParent).isViewTransitioning(this));
    }

    @Deprecated
    public void invalidate(Rect dirty) {
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        invalidateInternal(dirty.left - scrollX, dirty.top - scrollY, dirty.right - scrollX, dirty.bottom - scrollY, true, false);
    }

    @Deprecated
    public void invalidate(int l, int t, int r, int b) {
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        invalidateInternal(l - scrollX, t - scrollY, r - scrollX, b - scrollY, true, false);
    }

    public void invalidate() {
        invalidate(true);
    }

    @UnsupportedAppUsage
    public void invalidate(boolean invalidateCache) {
        invalidateInternal(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, invalidateCache, true);
    }

    /* access modifiers changed from: package-private */
    public void invalidateInternal(int l, int t, int r, int b, boolean invalidateCache, boolean fullInvalidate) {
        View receiver;
        if (this.mGhostView != null) {
            this.mGhostView.invalidate(true);
        } else if (!skipInvalidate()) {
            this.mCachedContentCaptureSession = null;
            if ((this.mPrivateFlags & 48) == 48 || ((invalidateCache && (this.mPrivateFlags & 32768) == 32768) || (this.mPrivateFlags & Integer.MIN_VALUE) != Integer.MIN_VALUE || (fullInvalidate && isOpaque() != this.mLastIsOpaque))) {
                if (fullInvalidate) {
                    this.mLastIsOpaque = isOpaque();
                    this.mPrivateFlags &= -33;
                }
                this.mPrivateFlags |= 2097152;
                if (invalidateCache) {
                    this.mPrivateFlags |= Integer.MIN_VALUE;
                    this.mPrivateFlags &= -32769;
                }
                AttachInfo ai = this.mAttachInfo;
                ViewParent p = this.mParent;
                if (p != null && ai != null && l < r && t < b) {
                    Rect damage = ai.mTmpInvalRect;
                    damage.set(l, t, r, b);
                    p.invalidateChild(this, damage);
                }
                if (this.mBackground != null && this.mBackground.isProjected() && (receiver = getProjectionReceiver()) != null) {
                    receiver.damageInParent();
                }
            }
        }
    }

    private View getProjectionReceiver() {
        ViewParent p = getParent();
        while (p != null && (p instanceof View)) {
            View v = (View) p;
            if (v.isProjectionReceiver()) {
                return v;
            }
            p = p.getParent();
        }
        return null;
    }

    private boolean isProjectionReceiver() {
        return this.mBackground != null;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void invalidateViewProperty(boolean invalidateParent, boolean forceRedraw) {
        if (!isHardwareAccelerated() || !this.mRenderNode.hasDisplayList() || (this.mPrivateFlags & 64) != 0) {
            if (invalidateParent) {
                invalidateParentCaches();
            }
            if (forceRedraw) {
                this.mPrivateFlags |= 32;
            }
            invalidate(false);
            return;
        }
        damageInParent();
    }

    /* access modifiers changed from: protected */
    public void damageInParent() {
        if (this.mParent != null && this.mAttachInfo != null) {
            this.mParent.onDescendantInvalidated(this, this);
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void invalidateParentCaches() {
        if (this.mParent instanceof View) {
            ((View) this.mParent).mPrivateFlags |= Integer.MIN_VALUE;
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void invalidateParentIfNeeded() {
        if (isHardwareAccelerated() && (this.mParent instanceof View)) {
            ((View) this.mParent).invalidate(true);
        }
    }

    /* access modifiers changed from: protected */
    public void invalidateParentIfNeededAndWasQuickRejected() {
        if ((this.mPrivateFlags2 & 268435456) != 0) {
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isOpaque() {
        return (this.mPrivateFlags & 25165824) == 25165824 && getFinalAlpha() >= 1.0f;
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void computeOpaqueFlags() {
        if (this.mBackground == null || this.mBackground.getOpacity() != -1) {
            this.mPrivateFlags &= -8388609;
        } else {
            this.mPrivateFlags |= 8388608;
        }
        int flags = this.mViewFlags;
        if (((flags & 512) == 0 && (flags & 256) == 0) || (flags & 50331648) == 0 || (50331648 & flags) == 33554432) {
            this.mPrivateFlags |= 16777216;
        } else {
            this.mPrivateFlags &= -16777217;
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasOpaqueScrollbars() {
        return (this.mPrivateFlags & 16777216) == 16777216;
    }

    public Handler getHandler() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler;
        }
        return null;
    }

    private HandlerActionQueue getRunQueue() {
        if (this.mRunQueue == null) {
            this.mRunQueue = new HandlerActionQueue();
        }
        return this.mRunQueue;
    }

    @UnsupportedAppUsage
    public ViewRootImpl getViewRootImpl() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl;
        }
        return null;
    }

    @UnsupportedAppUsage
    public ThreadedRenderer getThreadedRenderer() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mThreadedRenderer;
        }
        return null;
    }

    public boolean post(Runnable action) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.post(action);
        }
        getRunQueue().post(action);
        return true;
    }

    public boolean postDelayed(Runnable action, long delayMillis) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.postDelayed(action, delayMillis);
        }
        getRunQueue().postDelayed(action, delayMillis);
        return true;
    }

    public void postOnAnimation(Runnable action) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallback(1, action, (Object) null);
        } else {
            getRunQueue().post(action);
        }
    }

    public void postOnAnimationDelayed(Runnable action, long delayMillis) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, action, (Object) null, delayMillis);
        } else {
            getRunQueue().postDelayed(action, delayMillis);
        }
    }

    public boolean removeCallbacks(Runnable action) {
        if (action != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(action);
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, action, (Object) null);
            }
            getRunQueue().removeCallbacks(action);
        }
        return true;
    }

    public void postInvalidate() {
        postInvalidateDelayed(0);
    }

    public void postInvalidate(int left, int top, int right, int bottom) {
        postInvalidateDelayed(0, left, top, right, bottom);
    }

    public void postInvalidateDelayed(long delayMilliseconds) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateDelayed(this, delayMilliseconds);
        }
    }

    public void postInvalidateDelayed(long delayMilliseconds, int left, int top, int right, int bottom) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo info = AttachInfo.InvalidateInfo.obtain();
            info.target = this;
            info.left = left;
            info.top = top;
            info.right = right;
            info.bottom = bottom;
            attachInfo.mViewRootImpl.dispatchInvalidateRectDelayed(info, delayMilliseconds);
        }
    }

    public void postInvalidateOnAnimation() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateOnAnimation(this);
        }
    }

    public void postInvalidateOnAnimation(int left, int top, int right, int bottom) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo info = AttachInfo.InvalidateInfo.obtain();
            info.target = this;
            info.left = left;
            info.top = top;
            info.right = right;
            info.bottom = bottom;
            attachInfo.mViewRootImpl.dispatchInvalidateRectOnAnimation(info);
        }
    }

    private void postSendViewScrolledAccessibilityEventCallback(int dx, int dy) {
        if (this.mSendViewScrolledAccessibilityEvent == null) {
            this.mSendViewScrolledAccessibilityEvent = new SendViewScrolledAccessibilityEvent();
        }
        this.mSendViewScrolledAccessibilityEvent.post(dx, dy);
    }

    public void computeScroll() {
    }

    public boolean isHorizontalFadingEdgeEnabled() {
        return (this.mViewFlags & 4096) == 4096;
    }

    public void setHorizontalFadingEdgeEnabled(boolean horizontalFadingEdgeEnabled) {
        if (isHorizontalFadingEdgeEnabled() != horizontalFadingEdgeEnabled) {
            if (horizontalFadingEdgeEnabled) {
                initScrollCache();
            }
            this.mViewFlags ^= 4096;
        }
    }

    public boolean isVerticalFadingEdgeEnabled() {
        return (this.mViewFlags & 8192) == 8192;
    }

    public void setVerticalFadingEdgeEnabled(boolean verticalFadingEdgeEnabled) {
        if (isVerticalFadingEdgeEnabled() != verticalFadingEdgeEnabled) {
            if (verticalFadingEdgeEnabled) {
                initScrollCache();
            }
            this.mViewFlags ^= 8192;
        }
    }

    public int getFadingEdge() {
        return this.mViewFlags & 12288;
    }

    public int getFadingEdgeLength() {
        if (this.mScrollCache == null || (this.mViewFlags & 12288) == 0) {
            return 0;
        }
        return this.mScrollCache.fadingEdgeLength;
    }

    /* access modifiers changed from: protected */
    public float getTopFadingEdgeStrength() {
        return computeVerticalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    /* access modifiers changed from: protected */
    public float getBottomFadingEdgeStrength() {
        return computeVerticalScrollOffset() + computeVerticalScrollExtent() < computeVerticalScrollRange() ? 1.0f : 0.0f;
    }

    /* access modifiers changed from: protected */
    public float getLeftFadingEdgeStrength() {
        return computeHorizontalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    /* access modifiers changed from: protected */
    public float getRightFadingEdgeStrength() {
        return computeHorizontalScrollOffset() + computeHorizontalScrollExtent() < computeHorizontalScrollRange() ? 1.0f : 0.0f;
    }

    public boolean isHorizontalScrollBarEnabled() {
        return (this.mViewFlags & 256) == 256;
    }

    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        if (isHorizontalScrollBarEnabled() != horizontalScrollBarEnabled) {
            this.mViewFlags ^= 256;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public boolean isVerticalScrollBarEnabled() {
        return (this.mViewFlags & 512) == 512;
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        if (isVerticalScrollBarEnabled() != verticalScrollBarEnabled) {
            this.mViewFlags ^= 512;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void recomputePadding() {
        internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
    }

    public void setScrollbarFadingEnabled(boolean fadeScrollbars) {
        initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        scrollabilityCache.fadeScrollBars = fadeScrollbars;
        if (fadeScrollbars) {
            scrollabilityCache.state = 0;
        } else {
            scrollabilityCache.state = 1;
        }
    }

    public boolean isScrollbarFadingEnabled() {
        return this.mScrollCache != null && this.mScrollCache.fadeScrollBars;
    }

    public int getScrollBarDefaultDelayBeforeFade() {
        if (this.mScrollCache == null) {
            return ViewConfiguration.getScrollDefaultDelay();
        }
        return this.mScrollCache.scrollBarDefaultDelayBeforeFade;
    }

    public void setScrollBarDefaultDelayBeforeFade(int scrollBarDefaultDelayBeforeFade) {
        getScrollCache().scrollBarDefaultDelayBeforeFade = scrollBarDefaultDelayBeforeFade;
    }

    public int getScrollBarFadeDuration() {
        if (this.mScrollCache == null) {
            return ViewConfiguration.getScrollBarFadeDuration();
        }
        return this.mScrollCache.scrollBarFadeDuration;
    }

    public void setScrollBarFadeDuration(int scrollBarFadeDuration) {
        getScrollCache().scrollBarFadeDuration = scrollBarFadeDuration;
    }

    public int getScrollBarSize() {
        if (this.mScrollCache == null) {
            return ViewConfiguration.get(this.mContext).getScaledScrollBarSize();
        }
        return this.mScrollCache.scrollBarSize;
    }

    public void setScrollBarSize(int scrollBarSize) {
        getScrollCache().scrollBarSize = scrollBarSize;
    }

    public void setScrollBarStyle(int style) {
        if (style != (this.mViewFlags & 50331648)) {
            this.mViewFlags = (this.mViewFlags & -50331649) | (50331648 & style);
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, to = "INSIDE_OVERLAY"), @ViewDebug.IntToString(from = 16777216, to = "INSIDE_INSET"), @ViewDebug.IntToString(from = 33554432, to = "OUTSIDE_OVERLAY"), @ViewDebug.IntToString(from = 50331648, to = "OUTSIDE_INSET")})
    public int getScrollBarStyle() {
        return this.mViewFlags & 50331648;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollRange() {
        return getWidth();
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollOffset() {
        return this.mScrollX;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollExtent() {
        return getWidth();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollRange() {
        return getHeight();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollOffset() {
        return this.mScrollY;
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollExtent() {
        return getHeight();
    }

    public boolean canScrollHorizontally(int direction) {
        int offset = computeHorizontalScrollOffset();
        int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            if (offset > 0) {
                return true;
            }
            return false;
        } else if (offset < range - 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canScrollVertically(int direction) {
        int offset = computeVerticalScrollOffset();
        int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            if (offset > 0) {
                return true;
            }
            return false;
        } else if (offset < range - 1) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void getScrollIndicatorBounds(Rect out) {
        out.left = this.mScrollX;
        out.right = (this.mScrollX + this.mRight) - this.mLeft;
        out.top = this.mScrollY;
        out.bottom = (this.mScrollY + this.mBottom) - this.mTop;
    }

    private void onDrawScrollIndicators(Canvas c) {
        Drawable dr;
        int rightRtl;
        int leftRtl;
        if ((this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) != 0 && (dr = this.mScrollIndicatorDrawable) != null) {
            int h = dr.getIntrinsicHeight();
            int w = dr.getIntrinsicWidth();
            Rect rect = this.mAttachInfo.mTmpInvalRect;
            getScrollIndicatorBounds(rect);
            if ((this.mPrivateFlags3 & 256) != 0 && canScrollVertically(-1)) {
                dr.setBounds(rect.left, rect.top, rect.right, rect.top + h);
                dr.draw(c);
            }
            if ((this.mPrivateFlags3 & 512) != 0 && canScrollVertically(1)) {
                dr.setBounds(rect.left, rect.bottom - h, rect.right, rect.bottom);
                dr.draw(c);
            }
            if (getLayoutDirection() == 1) {
                leftRtl = 8192;
                rightRtl = 4096;
            } else {
                leftRtl = 4096;
                rightRtl = 8192;
            }
            if ((this.mPrivateFlags3 & (leftRtl | 1024)) != 0 && canScrollHorizontally(-1)) {
                dr.setBounds(rect.left, rect.top, rect.left + w, rect.bottom);
                dr.draw(c);
            }
            if ((this.mPrivateFlags3 & (rightRtl | 2048)) != 0 && canScrollHorizontally(1)) {
                dr.setBounds(rect.right - w, rect.top, rect.right, rect.bottom);
                dr.draw(c);
            }
        }
    }

    private void getHorizontalScrollBarBounds(Rect drawBounds, Rect touchBounds) {
        Rect bounds = drawBounds != null ? drawBounds : touchBounds;
        if (bounds != null) {
            int verticalScrollBarGap = 0;
            int inside = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
            boolean drawVerticalScrollBar = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
            int size = getHorizontalScrollbarHeight();
            if (drawVerticalScrollBar) {
                verticalScrollBarGap = getVerticalScrollbarWidth();
            }
            int width = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            bounds.top = ((this.mScrollY + height) - size) - (this.mUserPaddingBottom & inside);
            bounds.left = this.mScrollX + (this.mPaddingLeft & inside);
            bounds.right = ((this.mScrollX + width) - (this.mUserPaddingRight & inside)) - verticalScrollBarGap;
            bounds.bottom = bounds.top + size;
            if (touchBounds != null) {
                if (touchBounds != bounds) {
                    touchBounds.set(bounds);
                }
                int minTouchTarget = this.mScrollCache.scrollBarMinTouchTarget;
                if (touchBounds.height() < minTouchTarget) {
                    touchBounds.bottom = Math.min(touchBounds.bottom + ((minTouchTarget - touchBounds.height()) / 2), this.mScrollY + height);
                    touchBounds.top = touchBounds.bottom - minTouchTarget;
                }
                if (touchBounds.width() < minTouchTarget) {
                    touchBounds.left -= (minTouchTarget - touchBounds.width()) / 2;
                    touchBounds.right = touchBounds.left + minTouchTarget;
                }
            }
        }
    }

    private void getVerticalScrollBarBounds(Rect bounds, Rect touchBounds) {
        if (this.mRoundScrollbarRenderer == null) {
            getStraightVerticalScrollBarBounds(bounds, touchBounds);
        } else {
            getRoundVerticalScrollBarBounds(bounds != null ? bounds : touchBounds);
        }
    }

    private void getRoundVerticalScrollBarBounds(Rect bounds) {
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        bounds.left = this.mScrollX;
        bounds.top = this.mScrollY;
        bounds.right = bounds.left + width;
        bounds.bottom = this.mScrollY + height;
    }

    private void getStraightVerticalScrollBarBounds(Rect drawBounds, Rect touchBounds) {
        Rect bounds = drawBounds != null ? drawBounds : touchBounds;
        if (bounds != null) {
            int inside = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
            int size = getVerticalScrollbarWidth();
            int verticalScrollbarPosition = this.mVerticalScrollbarPosition;
            if (verticalScrollbarPosition == 0) {
                verticalScrollbarPosition = isLayoutRtl() ? 1 : 2;
            }
            int width = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            if (verticalScrollbarPosition != 1) {
                bounds.left = ((this.mScrollX + width) - size) - (this.mUserPaddingRight & inside);
            } else {
                bounds.left = this.mScrollX + (this.mUserPaddingLeft & inside);
            }
            bounds.top = this.mScrollY + (this.mPaddingTop & inside);
            bounds.right = bounds.left + size;
            bounds.bottom = (this.mScrollY + height) - (this.mUserPaddingBottom & inside);
            if (touchBounds != null) {
                if (touchBounds != bounds) {
                    touchBounds.set(bounds);
                }
                int minTouchTarget = this.mScrollCache.scrollBarMinTouchTarget;
                if (touchBounds.width() < minTouchTarget) {
                    int adjust = (minTouchTarget - touchBounds.width()) / 2;
                    if (verticalScrollbarPosition == 2) {
                        touchBounds.right = Math.min(touchBounds.right + adjust, this.mScrollX + width);
                        touchBounds.left = touchBounds.right - minTouchTarget;
                    } else {
                        touchBounds.left = Math.max(touchBounds.left + adjust, this.mScrollX);
                        touchBounds.right = touchBounds.left + minTouchTarget;
                    }
                }
                if (touchBounds.height() < minTouchTarget) {
                    touchBounds.top -= (minTouchTarget - touchBounds.height()) / 2;
                    touchBounds.bottom = touchBounds.top + minTouchTarget;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onDrawScrollBars(Canvas canvas) {
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache != null) {
            int state = cache.state;
            if (state != 0) {
                boolean invalidate = false;
                if (state == 2) {
                    if (cache.interpolatorValues == null) {
                        cache.interpolatorValues = new float[1];
                    }
                    float[] values = cache.interpolatorValues;
                    if (cache.scrollBarInterpolator.timeToValues(values) == Interpolator.Result.FREEZE_END) {
                        cache.state = 0;
                    } else {
                        cache.scrollBar.mutate().setAlpha(Math.round(values[0]));
                    }
                    invalidate = true;
                } else {
                    cache.scrollBar.mutate().setAlpha(255);
                }
                boolean invalidate2 = invalidate;
                boolean drawHorizontalScrollBar = isHorizontalScrollBarEnabled();
                boolean drawVerticalScrollBar = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
                if (this.mRoundScrollbarRenderer == null) {
                    Canvas canvas2 = canvas;
                    if (drawVerticalScrollBar || drawHorizontalScrollBar) {
                        ScrollBarDrawable scrollBar2 = cache.scrollBar;
                        if (drawHorizontalScrollBar) {
                            scrollBar2.setParameters(computeHorizontalScrollRange(), computeHorizontalScrollOffset(), computeHorizontalScrollExtent(), false);
                            Rect bounds = cache.mScrollBarBounds;
                            getHorizontalScrollBarBounds(bounds, (Rect) null);
                            Rect bounds2 = bounds;
                            scrollBar = scrollBar2;
                            onDrawHorizontalScrollBar(canvas, scrollBar2, bounds.left, bounds.top, bounds.right, bounds.bottom);
                            if (invalidate2) {
                                invalidate(bounds2);
                            }
                        } else {
                            scrollBar = scrollBar2;
                        }
                        if (drawVerticalScrollBar) {
                            scrollBar.setParameters(computeVerticalScrollRange(), computeVerticalScrollOffset(), computeVerticalScrollExtent(), true);
                            Rect bounds3 = cache.mScrollBarBounds;
                            getVerticalScrollBarBounds(bounds3, (Rect) null);
                            onDrawVerticalScrollBar(canvas, scrollBar, bounds3.left, bounds3.top, bounds3.right, bounds3.bottom);
                            if (invalidate2) {
                                invalidate(bounds3);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                } else if (drawVerticalScrollBar) {
                    Rect bounds4 = cache.mScrollBarBounds;
                    getVerticalScrollBarBounds(bounds4, (Rect) null);
                    this.mRoundScrollbarRenderer.drawRoundScrollbars(canvas, ((float) cache.scrollBar.getAlpha()) / 255.0f, bounds4);
                    if (invalidate2) {
                        invalidate();
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        }
        Canvas canvas3 = canvas;
    }

    /* access modifiers changed from: protected */
    public boolean isVerticalScrollBarHidden() {
        return false;
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void onDrawHorizontalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void onDrawVerticalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void assignParent(ViewParent parent) {
        if (this.mParent == null) {
            this.mParent = parent;
        } else if (parent == null) {
            this.mParent = null;
        } else {
            throw new RuntimeException("view " + this + " being added, but it already has a parent");
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        if ((this.mPrivateFlags & 512) != 0) {
            this.mParent.requestTransparentRegion(this);
        }
        this.mPrivateFlags3 &= -5;
        jumpDrawablesToCurrentState();
        AccessibilityNodeIdManager.getInstance().registerViewWithId(this, getAccessibilityViewId());
        resetSubtreeAccessibilityStateChanged();
        rebuildOutline();
        if (isFocused()) {
            notifyFocusChangeToInputMethodManager(true);
        }
    }

    public boolean resolveRtlPropertiesIfNeeded() {
        if (!needRtlPropertiesResolution()) {
            return false;
        }
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
            resolveLayoutParams();
        }
        if (!isTextDirectionResolved()) {
            resolveTextDirection();
        }
        if (!isTextAlignmentResolved()) {
            resolveTextAlignment();
        }
        if (!areDrawablesResolved()) {
            resolveDrawables();
        }
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        onRtlPropertiesChanged(getLayoutDirection());
        return true;
    }

    public void resetRtlProperties() {
        resetResolvedLayoutDirection();
        resetResolvedTextDirection();
        resetResolvedTextAlignment();
        resetResolvedPadding();
        resetResolvedDrawables();
    }

    /* access modifiers changed from: package-private */
    public void dispatchScreenStateChanged(int screenState) {
        onScreenStateChanged(screenState);
    }

    public void onScreenStateChanged(int screenState) {
    }

    /* access modifiers changed from: package-private */
    public void dispatchMovedToDisplay(Display display, Configuration config) {
        this.mAttachInfo.mDisplay = display;
        this.mAttachInfo.mDisplayState = display.getState();
        onMovedToDisplay(display.getDisplayId(), config);
    }

    public void onMovedToDisplay(int displayId, Configuration config) {
    }

    @UnsupportedAppUsage
    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    private boolean isRtlCompatibilityMode() {
        return getContext().getApplicationInfo().targetSdkVersion < 17 || !hasRtlSupport();
    }

    private boolean needRtlPropertiesResolution() {
        return (this.mPrivateFlags2 & ALL_RTL_PROPERTIES_RESOLVED) != ALL_RTL_PROPERTIES_RESOLVED;
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
    }

    public boolean resolveLayoutDirection() {
        this.mPrivateFlags2 &= -49;
        if (hasRtlSupport()) {
            switch ((this.mPrivateFlags2 & 12) >> 2) {
                case 1:
                    this.mPrivateFlags2 |= 16;
                    break;
                case 2:
                    if (canResolveLayoutDirection()) {
                        try {
                            if (this.mParent.isLayoutDirectionResolved()) {
                                if (this.mParent.getLayoutDirection() == 1) {
                                    this.mPrivateFlags2 |= 16;
                                    break;
                                }
                            } else {
                                return false;
                            }
                        } catch (AbstractMethodError e) {
                            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                            break;
                        }
                    } else {
                        return false;
                    }
                    break;
                case 3:
                    if (1 == TextUtils.getLayoutDirectionFromLocale(Locale.getDefault())) {
                        this.mPrivateFlags2 |= 16;
                        break;
                    }
                    break;
            }
        }
        this.mPrivateFlags2 |= 32;
        return true;
    }

    public boolean canResolveLayoutDirection() {
        if (getRawLayoutDirection() != 2) {
            return true;
        }
        if (this.mParent == null) {
            return false;
        }
        try {
            return this.mParent.canResolveLayoutDirection();
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            return false;
        }
    }

    public void resetResolvedLayoutDirection() {
        this.mPrivateFlags2 &= -49;
    }

    public boolean isLayoutDirectionInherited() {
        return getRawLayoutDirection() == 2;
    }

    public boolean isLayoutDirectionResolved() {
        return (this.mPrivateFlags2 & 32) == 32;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean isPaddingResolved() {
        return (this.mPrivateFlags2 & 536870912) == 536870912;
    }

    @UnsupportedAppUsage
    public void resolvePadding() {
        int resolvedLayoutDirection = getLayoutDirection();
        if (!isRtlCompatibilityMode()) {
            if (this.mBackground != null && (!this.mLeftPaddingDefined || !this.mRightPaddingDefined)) {
                Rect padding = sThreadLocal.get();
                if (padding == null) {
                    padding = new Rect();
                    sThreadLocal.set(padding);
                }
                this.mBackground.getPadding(padding);
                if (!this.mLeftPaddingDefined) {
                    this.mUserPaddingLeftInitial = padding.left;
                }
                if (!this.mRightPaddingDefined) {
                    this.mUserPaddingRightInitial = padding.right;
                }
            }
            if (resolvedLayoutDirection != 1) {
                if (this.mUserPaddingStart != Integer.MIN_VALUE) {
                    this.mUserPaddingLeft = this.mUserPaddingStart;
                } else {
                    this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                }
                if (this.mUserPaddingEnd != Integer.MIN_VALUE) {
                    this.mUserPaddingRight = this.mUserPaddingEnd;
                } else {
                    this.mUserPaddingRight = this.mUserPaddingRightInitial;
                }
            } else {
                if (this.mUserPaddingStart != Integer.MIN_VALUE) {
                    this.mUserPaddingRight = this.mUserPaddingStart;
                } else {
                    this.mUserPaddingRight = this.mUserPaddingRightInitial;
                }
                if (this.mUserPaddingEnd != Integer.MIN_VALUE) {
                    this.mUserPaddingLeft = this.mUserPaddingEnd;
                } else {
                    this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                }
            }
            this.mUserPaddingBottom = this.mUserPaddingBottom >= 0 ? this.mUserPaddingBottom : this.mPaddingBottom;
        }
        internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
        onRtlPropertiesChanged(resolvedLayoutDirection);
        this.mPrivateFlags2 |= 536870912;
    }

    public void resetResolvedPadding() {
        resetResolvedPaddingInternal();
    }

    /* access modifiers changed from: package-private */
    public void resetResolvedPaddingInternal() {
        this.mPrivateFlags2 &= -536870913;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void onDetachedFromWindowInternal() {
        this.mPrivateFlags &= -67108865;
        this.mPrivateFlags3 &= -5;
        this.mPrivateFlags3 &= -33554433;
        removeUnsetPressCallback();
        removeLongPressCallback();
        removePerformClickCallback();
        cancel(this.mSendViewScrolledAccessibilityEvent);
        stopNestedScroll();
        jumpDrawablesToCurrentState();
        destroyDrawingCache();
        cleanupDraw();
        this.mCurrentAnimation = null;
        if ((this.mViewFlags & 1073741824) == 1073741824) {
            hideTooltip();
        }
        AccessibilityNodeIdManager.getInstance().unregisterViewWithId(getAccessibilityViewId());
    }

    private void cleanupDraw() {
        resetDisplayList();
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.cancelInvalidate(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateInheritedLayoutMode(int layoutModeOfRoot) {
    }

    /* access modifiers changed from: protected */
    public int getWindowAttachCount() {
        return this.mWindowAttachCount;
    }

    public IBinder getWindowToken() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindowToken;
        }
        return null;
    }

    public WindowId getWindowId() {
        AttachInfo ai = this.mAttachInfo;
        if (ai == null) {
            return null;
        }
        if (ai.mWindowId == null) {
            try {
                ai.mIWindowId = ai.mSession.getWindowId(ai.mWindowToken);
                if (ai.mIWindowId != null) {
                    ai.mWindowId = new WindowId(ai.mIWindowId);
                }
            } catch (RemoteException e) {
            }
        }
        return ai.mWindowId;
    }

    public IBinder getApplicationWindowToken() {
        AttachInfo ai = this.mAttachInfo;
        if (ai == null) {
            return null;
        }
        IBinder appWindowToken = ai.mPanelParentWindowToken;
        if (appWindowToken == null) {
            return ai.mWindowToken;
        }
        return appWindowToken;
    }

    public Display getDisplay() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDisplay;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public IWindowSession getWindowSession() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSession;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public IWindow getWindow() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindow;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int combineVisibility(int vis1, int vis2) {
        return Math.max(vis1, vis2);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28)
    public void dispatchAttachedToWindow(AttachInfo info, int visibility) {
        this.mAttachInfo = info;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchAttachedToWindow(info, visibility);
        }
        this.mWindowAttachCount++;
        this.mPrivateFlags |= 1024;
        CopyOnWriteArrayList<OnAttachStateChangeListener> listeners = null;
        if (this.mFloatingTreeObserver != null) {
            info.mTreeObserver.merge(this.mFloatingTreeObserver);
            this.mFloatingTreeObserver = null;
        }
        registerPendingFrameMetricsObservers();
        if ((this.mPrivateFlags & 524288) != 0) {
            this.mAttachInfo.mScrollContainers.add(this);
            this.mPrivateFlags |= 1048576;
        }
        if (this.mRunQueue != null) {
            this.mRunQueue.executeActions(info.mHandler);
            this.mRunQueue = null;
        }
        performCollectViewAttributes(this.mAttachInfo, visibility);
        onAttachedToWindow();
        ListenerInfo li = this.mListenerInfo;
        if (li != null) {
            listeners = li.mOnAttachStateChangeListeners;
        }
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnAttachStateChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().onViewAttachedToWindow(this);
            }
        }
        int vis = info.mWindowVisibility;
        if (vis != 8) {
            onWindowVisibilityChanged(vis);
            if (isShown()) {
                onVisibilityAggregated(vis == 0);
            }
        }
        onVisibilityChanged(this, visibility);
        if ((this.mPrivateFlags & 1024) != 0) {
            refreshDrawableState();
        }
        needGlobalAttributesUpdate(false);
        notifyEnterOrExitForAutoFillIfNeeded(true);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage(maxTargetSdk = 28)
    public void dispatchDetachedFromWindow() {
        AttachInfo info = this.mAttachInfo;
        if (!(info == null || info.mWindowVisibility == 8)) {
            onWindowVisibilityChanged(8);
            if (isShown()) {
                onVisibilityAggregated(false);
            }
        }
        onDetachedFromWindow();
        onDetachedFromWindowInternal();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
        if (imm != null) {
            imm.onViewDetachedFromWindow(this);
        }
        ListenerInfo li = this.mListenerInfo;
        CopyOnWriteArrayList<OnAttachStateChangeListener> listeners = li != null ? li.mOnAttachStateChangeListeners : null;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnAttachStateChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                it.next().onViewDetachedFromWindow(this);
            }
        }
        if ((this.mPrivateFlags & 1048576) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
            this.mPrivateFlags &= -1048577;
        }
        this.mAttachInfo = null;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchDetachedFromWindow();
        }
        notifyEnterOrExitForAutoFillIfNeeded(false);
    }

    public final void cancelPendingInputEvents() {
        dispatchCancelPendingInputEvents();
    }

    /* access modifiers changed from: package-private */
    public void dispatchCancelPendingInputEvents() {
        this.mPrivateFlags3 &= -17;
        onCancelPendingInputEvents();
        if ((this.mPrivateFlags3 & 16) != 16) {
            throw new SuperNotCalledException("View " + getClass().getSimpleName() + " did not call through to super.onCancelPendingInputEvents()");
        }
    }

    public void onCancelPendingInputEvents() {
        removePerformClickCallback();
        cancelLongPress();
        this.mPrivateFlags3 |= 16;
    }

    public void saveHierarchyState(SparseArray<Parcelable> container) {
        dispatchSaveInstanceState(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        if (this.mID != -1 && (this.mViewFlags & 65536) == 0) {
            this.mPrivateFlags &= -131073;
            Parcelable state = onSaveInstanceState();
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            } else if (state != null) {
                container.put(this.mID, state);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        this.mPrivateFlags |= 131072;
        if (this.mStartActivityRequestWho == null && !isAutofilled() && this.mAutofillViewId <= 1073741823) {
            return BaseSavedState.EMPTY_STATE;
        }
        BaseSavedState state = new BaseSavedState((Parcelable) AbsSavedState.EMPTY_STATE);
        if (this.mStartActivityRequestWho != null) {
            state.mSavedData |= 1;
        }
        if (isAutofilled()) {
            state.mSavedData |= 2;
        }
        if (this.mAutofillViewId > 1073741823) {
            state.mSavedData |= 4;
        }
        state.mStartActivityRequestWhoSaved = this.mStartActivityRequestWho;
        state.mIsAutofilled = isAutofilled();
        state.mAutofillViewId = this.mAutofillViewId;
        return state;
    }

    public void restoreHierarchyState(SparseArray<Parcelable> container) {
        dispatchRestoreInstanceState(container);
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        Parcelable state;
        if (this.mID != -1 && (state = container.get(this.mID)) != null) {
            this.mPrivateFlags &= -131073;
            onRestoreInstanceState(state);
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        this.mPrivateFlags |= 131072;
        if (state != null && !(state instanceof AbsSavedState)) {
            throw new IllegalArgumentException("Wrong state class, expecting View State but received " + state.getClass().toString() + " instead. This usually happens when two views of different type have the same id in the same hierarchy. This view's id is " + ViewDebug.resolveId(this.mContext, getId()) + ". Make sure other views do not use the same id.");
        } else if (state != null && (state instanceof BaseSavedState)) {
            BaseSavedState baseState = (BaseSavedState) state;
            if ((baseState.mSavedData & 1) != 0) {
                this.mStartActivityRequestWho = baseState.mStartActivityRequestWhoSaved;
            }
            if ((baseState.mSavedData & 2) != 0) {
                setAutofilled(baseState.mIsAutofilled);
            }
            if ((baseState.mSavedData & 4) != 0) {
                ((BaseSavedState) state).mSavedData &= -5;
                if ((this.mPrivateFlags3 & 1073741824) == 0) {
                    this.mAutofillViewId = baseState.mAutofillViewId;
                    this.mAutofillId = null;
                } else if (Log.isLoggable(AUTOFILL_LOG_TAG, 3)) {
                    Log.d(AUTOFILL_LOG_TAG, "onRestoreInstanceState(): not setting autofillId to " + baseState.mAutofillViewId + " because view explicitly set it to " + this.mAutofillId);
                }
            }
        }
    }

    public long getDrawingTime() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDrawingTime;
        }
        return 0;
    }

    public void setDuplicateParentStateEnabled(boolean enabled) {
        setFlags(enabled ? 4194304 : 0, 4194304);
    }

    public boolean isDuplicateParentStateEnabled() {
        return (this.mViewFlags & 4194304) == 4194304;
    }

    public void setLayerType(int layerType, Paint paint) {
        if (layerType < 0 || layerType > 2) {
            throw new IllegalArgumentException("Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE");
        } else if (!this.mRenderNode.setLayerType(layerType)) {
            setLayerPaint(paint);
        } else {
            if (layerType != 1) {
                destroyDrawingCache();
            }
            this.mLayerType = layerType;
            this.mLayerPaint = this.mLayerType == 0 ? null : paint;
            this.mRenderNode.setLayerPaint(this.mLayerPaint);
            invalidateParentCaches();
            invalidate(true);
        }
    }

    public void setLayerPaint(Paint paint) {
        int layerType = getLayerType();
        if (layerType != 0) {
            this.mLayerPaint = paint;
            if (layerType != 2) {
                invalidate();
            } else if (this.mRenderNode.setLayerPaint(paint)) {
                invalidateViewProperty(false, false);
            }
        }
    }

    public int getLayerType() {
        return this.mLayerType;
    }

    public void buildLayer() {
        if (this.mLayerType != 0) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo == null) {
                throw new IllegalStateException("This view must be attached to a window first");
            } else if (getWidth() != 0 && getHeight() != 0) {
                switch (this.mLayerType) {
                    case 1:
                        buildDrawingCache(true);
                        return;
                    case 2:
                        updateDisplayListIfDirty();
                        if (attachInfo.mThreadedRenderer != null && this.mRenderNode.hasDisplayList()) {
                            attachInfo.mThreadedRenderer.buildLayer(this.mRenderNode);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void destroyHardwareResources() {
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().destroyHardwareResources();
        }
        if (this.mGhostView != null) {
            this.mGhostView.destroyHardwareResources();
        }
    }

    @Deprecated
    public void setDrawingCacheEnabled(boolean enabled) {
        int i = 0;
        this.mCachingFailed = false;
        if (enabled) {
            i = 32768;
        }
        setFlags(i, 32768);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    @Deprecated
    public boolean isDrawingCacheEnabled() {
        return (this.mViewFlags & 32768) == 32768;
    }

    public void outputDirtyFlags(String indent, boolean clear, int clearMask) {
        Log.d(VIEW_LOG_TAG, indent + this + "             DIRTY(" + (this.mPrivateFlags & 2097152) + ") DRAWN(" + (this.mPrivateFlags & 32) + ") CACHE_VALID(" + (this.mPrivateFlags & 32768) + ") INVALIDATED(" + (this.mPrivateFlags & Integer.MIN_VALUE) + ")");
        if (clear) {
            this.mPrivateFlags &= clearMask;
        }
        if (this instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) this;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                parent.getChildAt(i).outputDirtyFlags(indent + "  ", clear, clearMask);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchGetDisplayList() {
    }

    public boolean canHaveDisplayList() {
        return (this.mAttachInfo == null || this.mAttachInfo.mThreadedRenderer == null) ? false : true;
    }

    @UnsupportedAppUsage
    public RenderNode updateDisplayListIfDirty() {
        RenderNode renderNode = this.mRenderNode;
        if (!canHaveDisplayList()) {
            return renderNode;
        }
        if ((this.mPrivateFlags & 32768) != 0 && renderNode.hasDisplayList() && !this.mRecreateDisplayList) {
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
        } else if (!renderNode.hasDisplayList() || this.mRecreateDisplayList) {
            this.mRecreateDisplayList = true;
            int width = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            int layerType = getLayerType();
            RecordingCanvas canvas = renderNode.beginRecording(width, height);
            if (layerType == 1) {
                try {
                    buildDrawingCache(true);
                    Bitmap cache = getDrawingCache(true);
                    if (cache != null) {
                        canvas.drawBitmap(cache, 0.0f, 0.0f, this.mLayerPaint);
                    }
                } finally {
                    renderNode.endRecording();
                    setDisplayListProperties(renderNode);
                }
            } else {
                computeScroll();
                canvas.translate((float) (-this.mScrollX), (float) (-this.mScrollY));
                this.mPrivateFlags |= 32800;
                this.mPrivateFlags &= -2097153;
                if ((this.mPrivateFlags & 128) == 128) {
                    dispatchDraw(canvas);
                    drawAutofilledHighlight(canvas);
                    if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                        this.mOverlay.getOverlayView().draw(canvas);
                    }
                    if (debugDraw()) {
                        debugDrawFocus(canvas);
                    }
                } else {
                    draw(canvas);
                }
            }
        } else {
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
            dispatchGetDisplayList();
            return renderNode;
        }
        return renderNode;
    }

    @UnsupportedAppUsage
    private void resetDisplayList() {
        this.mRenderNode.discardDisplayList();
        if (this.mBackgroundRenderNode != null) {
            this.mBackgroundRenderNode.discardDisplayList();
        }
    }

    @Deprecated
    public Bitmap getDrawingCache() {
        return getDrawingCache(false);
    }

    @Deprecated
    public Bitmap getDrawingCache(boolean autoScale) {
        if ((this.mViewFlags & 131072) == 131072) {
            return null;
        }
        if ((this.mViewFlags & 32768) == 32768) {
            buildDrawingCache(autoScale);
        }
        return autoScale ? this.mDrawingCache : this.mUnscaledDrawingCache;
    }

    @Deprecated
    public void destroyDrawingCache() {
        if (this.mDrawingCache != null) {
            this.mDrawingCache.recycle();
            this.mDrawingCache = null;
        }
        if (this.mUnscaledDrawingCache != null) {
            this.mUnscaledDrawingCache.recycle();
            this.mUnscaledDrawingCache = null;
        }
    }

    @Deprecated
    public void setDrawingCacheBackgroundColor(int color) {
        if (color != this.mDrawingCacheBackgroundColor) {
            this.mDrawingCacheBackgroundColor = color;
            this.mPrivateFlags &= -32769;
        }
    }

    @Deprecated
    public int getDrawingCacheBackgroundColor() {
        return this.mDrawingCacheBackgroundColor;
    }

    @Deprecated
    public void buildDrawingCache() {
        buildDrawingCache(false);
    }

    @Deprecated
    public void buildDrawingCache(boolean autoScale) {
        if ((this.mPrivateFlags & 32768) != 0) {
            if (autoScale) {
                if (this.mDrawingCache != null) {
                    return;
                }
            } else if (this.mUnscaledDrawingCache != null) {
                return;
            }
        }
        if (Trace.isTagEnabled(8)) {
            Trace.traceBegin(8, "buildDrawingCache/SW Layer for " + getClass().getSimpleName());
        }
        try {
            buildDrawingCacheImpl(autoScale);
        } finally {
            Trace.traceEnd(8);
        }
    }

    private void buildDrawingCacheImpl(boolean autoScale) {
        boolean clear;
        Canvas canvas;
        Bitmap.Config quality;
        boolean z;
        this.mCachingFailed = false;
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        boolean scalingRequired = attachInfo != null && attachInfo.mScalingRequired;
        if (autoScale && scalingRequired) {
            width = (int) ((((float) width) * attachInfo.mApplicationScale) + 0.5f);
            height = (int) ((((float) height) * attachInfo.mApplicationScale) + 0.5f);
        }
        int drawingCacheBackgroundColor = this.mDrawingCacheBackgroundColor;
        boolean opaque = drawingCacheBackgroundColor != 0 || isOpaque();
        boolean use32BitCache = attachInfo != null && attachInfo.mUse32BitDrawingCache;
        long projectedBitmapSize = (long) (width * height * ((!opaque || use32BitCache) ? 4 : 2));
        long drawingCacheSize = (long) ViewConfiguration.get(this.mContext).getScaledMaximumDrawingCacheSize();
        if (width <= 0 || height <= 0) {
            boolean z2 = opaque;
        } else if (projectedBitmapSize > drawingCacheSize) {
            boolean z3 = scalingRequired;
            boolean z4 = opaque;
        } else {
            Bitmap bitmap = autoScale ? this.mDrawingCache : this.mUnscaledDrawingCache;
            if (bitmap != null && bitmap.getWidth() == width && bitmap.getHeight() == height) {
                clear = true;
            } else {
                if (!opaque) {
                    int i = this.mViewFlags;
                    quality = Bitmap.Config.ARGB_8888;
                } else {
                    quality = use32BitCache ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
                try {
                    bitmap = Bitmap.createBitmap(this.mResources.getDisplayMetrics(), width, height, quality);
                    bitmap.setDensity(getResources().getDisplayMetrics().densityDpi);
                    if (autoScale) {
                        try {
                            this.mDrawingCache = bitmap;
                        } catch (OutOfMemoryError e) {
                            boolean z5 = scalingRequired;
                            boolean z6 = opaque;
                        }
                    } else {
                        this.mUnscaledDrawingCache = bitmap;
                    }
                    if (!opaque || !use32BitCache) {
                        z = false;
                    } else {
                        z = false;
                        bitmap.setHasAlpha(false);
                    }
                    clear = drawingCacheBackgroundColor != 0 ? true : z;
                } catch (OutOfMemoryError e2) {
                    boolean z7 = scalingRequired;
                    boolean z8 = opaque;
                    if (autoScale) {
                        this.mDrawingCache = null;
                    } else {
                        this.mUnscaledDrawingCache = null;
                    }
                    this.mCachingFailed = true;
                    return;
                }
            }
            if (attachInfo != null) {
                canvas = attachInfo.mCanvas;
                if (canvas == null) {
                    canvas = new Canvas();
                }
                canvas.setBitmap(bitmap);
                boolean z9 = opaque;
                attachInfo.mCanvas = null;
            } else {
                canvas = new Canvas(bitmap);
            }
            if (clear) {
                bitmap.eraseColor(drawingCacheBackgroundColor);
            }
            computeScroll();
            int restoreCount = canvas.save();
            if (!autoScale || !scalingRequired) {
            } else {
                Bitmap bitmap2 = bitmap;
                float scale = attachInfo.mApplicationScale;
                canvas.scale(scale, scale);
            }
            boolean z10 = scalingRequired;
            canvas.translate((float) (-this.mScrollX), (float) (-this.mScrollY));
            this.mPrivateFlags |= 32;
            if (this.mAttachInfo == null || !this.mAttachInfo.mHardwareAccelerated || this.mLayerType != 0) {
                this.mPrivateFlags |= 32768;
            }
            if ((this.mPrivateFlags & 128) == 128) {
                this.mPrivateFlags &= -2097153;
                dispatchDraw(canvas);
                drawAutofilledHighlight(canvas);
                if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                    this.mOverlay.getOverlayView().draw(canvas);
                }
            } else {
                draw(canvas);
            }
            canvas.restoreToCount(restoreCount);
            canvas.setBitmap((Bitmap) null);
            if (attachInfo != null) {
                attachInfo.mCanvas = canvas;
                return;
            }
            return;
        }
        if (width > 0 && height > 0) {
            Log.w(VIEW_LOG_TAG, getClass().getSimpleName() + " not displayed because it is too large to fit into a software layer (or drawing cache), needs " + projectedBitmapSize + " bytes, only " + drawingCacheSize + " available");
        }
        destroyDrawingCache();
        this.mCachingFailed = true;
    }

    @UnsupportedAppUsage
    public Bitmap createSnapshot(ViewDebug.CanvasProvider canvasProvider, boolean skipChildren) {
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        float scale = attachInfo != null ? attachInfo.mApplicationScale : 1.0f;
        int width2 = (int) ((((float) width) * scale) + 0.5f);
        int height2 = (int) ((((float) height) * scale) + 0.5f);
        Canvas oldCanvas = null;
        int i = 1;
        int i2 = width2 > 0 ? width2 : 1;
        if (height2 > 0) {
            i = height2;
        }
        try {
            Canvas canvas = canvasProvider.getCanvas(this, i2, i);
            if (attachInfo != null) {
                oldCanvas = attachInfo.mCanvas;
                attachInfo.mCanvas = null;
            }
            computeScroll();
            int restoreCount = canvas.save();
            canvas.scale(scale, scale);
            canvas.translate((float) (-this.mScrollX), (float) (-this.mScrollY));
            int flags = this.mPrivateFlags;
            this.mPrivateFlags &= -2097153;
            if ((this.mPrivateFlags & 128) == 128) {
                dispatchDraw(canvas);
                drawAutofilledHighlight(canvas);
                if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                    this.mOverlay.getOverlayView().draw(canvas);
                }
            } else {
                draw(canvas);
            }
            this.mPrivateFlags = flags;
            canvas.restoreToCount(restoreCount);
            return canvasProvider.createBitmap();
        } finally {
            if (oldCanvas != null) {
                attachInfo.mCanvas = oldCanvas;
            }
        }
    }

    public boolean isInEditMode() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isPaddingOffsetRequired() {
        return false;
    }

    /* access modifiers changed from: protected */
    public int getLeftPaddingOffset() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getRightPaddingOffset() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getTopPaddingOffset() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getBottomPaddingOffset() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getFadeTop(boolean offsetRequired) {
        int top = this.mPaddingTop;
        if (offsetRequired) {
            return top + getTopPaddingOffset();
        }
        return top;
    }

    /* access modifiers changed from: protected */
    public int getFadeHeight(boolean offsetRequired) {
        int padding = this.mPaddingTop;
        if (offsetRequired) {
            padding += getTopPaddingOffset();
        }
        return ((this.mBottom - this.mTop) - this.mPaddingBottom) - padding;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isHardwareAccelerated() {
        return this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerated;
    }

    public void setClipBounds(Rect clipBounds) {
        if (clipBounds == this.mClipBounds) {
            return;
        }
        if (clipBounds == null || !clipBounds.equals(this.mClipBounds)) {
            if (clipBounds == null) {
                this.mClipBounds = null;
            } else if (this.mClipBounds == null) {
                this.mClipBounds = new Rect(clipBounds);
            } else {
                this.mClipBounds.set(clipBounds);
            }
            this.mRenderNode.setClipRect(this.mClipBounds);
            invalidateViewProperty(false, false);
        }
    }

    public Rect getClipBounds() {
        if (this.mClipBounds != null) {
            return new Rect(this.mClipBounds);
        }
        return null;
    }

    public boolean getClipBounds(Rect outRect) {
        if (this.mClipBounds == null) {
            return false;
        }
        outRect.set(this.mClipBounds);
        return true;
    }

    private boolean applyLegacyAnimation(ViewGroup parent, long drawingTime, Animation a, boolean scalingRequired) {
        Transformation invalidationTransform;
        ViewGroup viewGroup = parent;
        long j = drawingTime;
        Animation animation = a;
        int flags = viewGroup.mGroupFlags;
        if (!a.isInitialized()) {
            animation.initialize(this.mRight - this.mLeft, this.mBottom - this.mTop, parent.getWidth(), parent.getHeight());
            animation.initializeInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            if (this.mAttachInfo != null) {
                animation.setListenerHandler(this.mAttachInfo.mHandler);
            }
            onAnimationStart();
        }
        Transformation t = parent.getChildTransformation();
        boolean more = animation.getTransformation(j, t, 1.0f);
        if (!scalingRequired || this.mAttachInfo.mApplicationScale == 1.0f) {
            invalidationTransform = t;
        } else {
            if (viewGroup.mInvalidationTransformation == null) {
                viewGroup.mInvalidationTransformation = new Transformation();
            }
            Transformation invalidationTransform2 = viewGroup.mInvalidationTransformation;
            animation.getTransformation(j, invalidationTransform2, 1.0f);
            invalidationTransform = invalidationTransform2;
        }
        if (more) {
            if (a.willChangeBounds()) {
                if (viewGroup.mInvalidateRegion == null) {
                    viewGroup.mInvalidateRegion = new RectF();
                }
                RectF region = viewGroup.mInvalidateRegion;
                a.getInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, region, invalidationTransform);
                viewGroup.mPrivateFlags |= 64;
                RectF region2 = region;
                int left = this.mLeft + ((int) region2.left);
                int top = this.mTop + ((int) region2.top);
                viewGroup.invalidate(left, top, ((int) (region2.width() + 0.5f)) + left, ((int) (region2.height() + 0.5f)) + top);
            } else if ((flags & 144) == 128) {
                viewGroup.mGroupFlags |= 4;
            } else if ((flags & 4) == 0) {
                viewGroup.mPrivateFlags |= 64;
                viewGroup.invalidate(this.mLeft, this.mTop, this.mRight, this.mBottom);
            }
        }
        return more;
    }

    /* access modifiers changed from: package-private */
    public void setDisplayListProperties(RenderNode renderNode) {
        boolean z;
        int transformType;
        if (renderNode != null) {
            renderNode.setHasOverlappingRendering(getHasOverlappingRendering());
            if (!(this.mParent instanceof ViewGroup) || !((ViewGroup) this.mParent).getClipChildren()) {
                z = false;
            } else {
                z = true;
            }
            renderNode.setClipToBounds(z);
            float alpha = 1.0f;
            if ((this.mParent instanceof ViewGroup) && (((ViewGroup) this.mParent).mGroupFlags & 2048) != 0) {
                ViewGroup parentVG = (ViewGroup) this.mParent;
                Transformation t = parentVG.getChildTransformation();
                if (parentVG.getChildStaticTransformation(this, t) && (transformType = t.getTransformationType()) != 0) {
                    if ((transformType & 1) != 0) {
                        alpha = t.getAlpha();
                    }
                    if ((transformType & 2) != 0) {
                        renderNode.setStaticMatrix(t.getMatrix());
                    }
                }
            }
            if (this.mTransformationInfo != null) {
                float alpha2 = alpha * getFinalAlpha();
                if (alpha2 < 1.0f && onSetAlpha((int) (255.0f * alpha2))) {
                    alpha2 = 1.0f;
                }
                renderNode.setAlpha(alpha2);
            } else if (alpha < 1.0f) {
                renderNode.setAlpha(alpha);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean draw(Canvas canvas, ViewGroup parent, long drawingTime) {
        Animation a;
        int restoreTo;
        boolean more;
        boolean hardwareAcceleratedCanvas;
        RenderNode renderNode;
        Bitmap cache;
        int restoreTo2;
        int sy;
        int transY;
        Bitmap cache2;
        float alpha;
        Canvas canvas2 = canvas;
        ViewGroup viewGroup = parent;
        boolean hardwareAcceleratedCanvas2 = canvas.isHardwareAccelerated();
        boolean drawingWithRenderNode = this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerated && hardwareAcceleratedCanvas2;
        boolean more2 = false;
        boolean childHasIdentityMatrix = hasIdentityMatrix();
        int parentFlags = viewGroup.mGroupFlags;
        if ((parentFlags & 256) != 0) {
            parent.getChildTransformation().clear();
            viewGroup.mGroupFlags &= TrafficStats.TAG_NETWORK_STACK_RANGE_END;
        }
        Transformation transformToApply = null;
        boolean concatMatrix = false;
        boolean scalingRequired = this.mAttachInfo != null && this.mAttachInfo.mScalingRequired;
        Animation a2 = getAnimation();
        if (a2 != null) {
            a = a2;
            more2 = applyLegacyAnimation(parent, drawingTime, a2, scalingRequired);
            concatMatrix = a.willChangeTransformationMatrix();
            if (concatMatrix) {
                this.mPrivateFlags3 |= 1;
            }
            transformToApply = parent.getChildTransformation();
        } else {
            a = a2;
            Transformation transformation = null;
            if ((this.mPrivateFlags3 & 1) != 0) {
                this.mRenderNode.setAnimationMatrix((Matrix) null);
                this.mPrivateFlags3 &= -2;
            }
            if (!drawingWithRenderNode && (parentFlags & 2048) != 0) {
                Transformation t = parent.getChildTransformation();
                if (viewGroup.getChildStaticTransformation(this, t)) {
                    int transformType = t.getTransformationType();
                    if (transformType != 0) {
                        transformation = t;
                    }
                    transformToApply = transformation;
                    concatMatrix = (transformType & 2) != 0;
                }
            }
        }
        boolean concatMatrix2 = concatMatrix | (!childHasIdentityMatrix);
        this.mPrivateFlags |= 32;
        if (!concatMatrix2 && (parentFlags & 2049) == 1) {
            if (canvas.quickReject((float) this.mLeft, (float) this.mTop, (float) this.mRight, (float) this.mBottom, Canvas.EdgeType.BW) && (this.mPrivateFlags & 64) == 0) {
                this.mPrivateFlags2 |= 268435456;
                return more2;
            }
        }
        this.mPrivateFlags2 &= -268435457;
        if (hardwareAcceleratedCanvas2) {
            this.mRecreateDisplayList = (this.mPrivateFlags & Integer.MIN_VALUE) != 0;
            this.mPrivateFlags &= Integer.MAX_VALUE;
        }
        RenderNode renderNode2 = null;
        Bitmap cache3 = null;
        int layerType = getLayerType();
        if (layerType == 1 || !drawingWithRenderNode) {
            if (layerType != 0) {
                layerType = 1;
                buildDrawingCache(true);
            }
            cache3 = getDrawingCache(true);
        }
        Bitmap cache4 = cache3;
        int layerType2 = layerType;
        if (drawingWithRenderNode) {
            renderNode2 = updateDisplayListIfDirty();
            if (!renderNode2.hasDisplayList()) {
                renderNode2 = null;
                drawingWithRenderNode = false;
            }
        }
        RenderNode renderNode3 = renderNode2;
        int sx = 0;
        int sy2 = 0;
        if (!drawingWithRenderNode) {
            computeScroll();
            sx = this.mScrollX;
            sy2 = this.mScrollY;
        }
        int sx2 = sx;
        int sy3 = sy2;
        boolean drawingWithDrawingCache = cache4 != null && !drawingWithRenderNode;
        boolean offsetForScroll = cache4 == null && !drawingWithRenderNode;
        int restoreTo3 = -1;
        if (!drawingWithRenderNode || transformToApply != null) {
            restoreTo3 = canvas.save();
        }
        if (offsetForScroll) {
            canvas2.translate((float) (this.mLeft - sx2), (float) (this.mTop - sy3));
        } else {
            if (!drawingWithRenderNode) {
                canvas2.translate((float) this.mLeft, (float) this.mTop);
            }
            if (scalingRequired) {
                if (drawingWithRenderNode) {
                    restoreTo3 = canvas.save();
                }
                float scale = 1.0f / this.mAttachInfo.mApplicationScale;
                canvas2.scale(scale, scale);
            }
        }
        int restoreTo4 = restoreTo3;
        float alpha2 = drawingWithRenderNode ? 1.0f : getAlpha() * getTransitionAlpha();
        if (transformToApply != null || alpha2 < 1.0f || !hasIdentityMatrix() || (this.mPrivateFlags3 & 2) != 0) {
            if (transformToApply != null || !childHasIdentityMatrix) {
                int transX = 0;
                if (offsetForScroll) {
                    transX = -sx2;
                    transY = -sy3;
                } else {
                    transY = 0;
                }
                if (transformToApply != null) {
                    if (concatMatrix2) {
                        if (drawingWithRenderNode) {
                            cache2 = cache4;
                            renderNode3.setAnimationMatrix(transformToApply.getMatrix());
                            hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                        } else {
                            cache2 = cache4;
                            hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                            canvas2.translate((float) (-transX), (float) (-transY));
                            canvas2.concat(transformToApply.getMatrix());
                            canvas2.translate((float) transX, (float) transY);
                        }
                        viewGroup.mGroupFlags |= 256;
                    } else {
                        cache2 = cache4;
                        hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                    }
                    float transformAlpha = transformToApply.getAlpha();
                    if (transformAlpha < 1.0f) {
                        alpha2 *= transformAlpha;
                        viewGroup.mGroupFlags |= 256;
                    }
                } else {
                    cache2 = cache4;
                    hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                }
                if (!childHasIdentityMatrix && !drawingWithRenderNode) {
                    canvas2.translate((float) (-transX), (float) (-transY));
                    canvas2.concat(getMatrix());
                    canvas2.translate((float) transX, (float) transY);
                }
            } else {
                cache2 = cache4;
                hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
            }
            float alpha3 = alpha2;
            if (alpha3 < 1.0f || (this.mPrivateFlags3 & 2) != 0) {
                if (alpha3 < 1.0f) {
                    this.mPrivateFlags3 |= 2;
                } else {
                    this.mPrivateFlags3 &= -3;
                }
                viewGroup.mGroupFlags |= 256;
                if (!drawingWithDrawingCache) {
                    int multipliedAlpha = (int) (alpha3 * 255.0f);
                    if (onSetAlpha(multipliedAlpha)) {
                        alpha = alpha3;
                        restoreTo = restoreTo4;
                        more = more2;
                        boolean z = childHasIdentityMatrix;
                        cache = cache2;
                        sy = sy3;
                        restoreTo2 = sx2;
                        renderNode = renderNode3;
                        this.mPrivateFlags |= 262144;
                    } else if (drawingWithRenderNode) {
                        renderNode3.setAlpha(getAlpha() * alpha3 * getTransitionAlpha());
                        alpha = alpha3;
                        restoreTo = restoreTo4;
                        more = more2;
                        boolean z2 = childHasIdentityMatrix;
                        cache = cache2;
                        sy = sy3;
                        restoreTo2 = sx2;
                        renderNode = renderNode3;
                    } else if (layerType2 == 0) {
                        alpha = alpha3;
                        sy = sy3;
                        boolean z3 = childHasIdentityMatrix;
                        renderNode = renderNode3;
                        restoreTo = restoreTo4;
                        more = more2;
                        cache = cache2;
                        restoreTo2 = sx2;
                        canvas.saveLayerAlpha((float) sx2, (float) sy3, (float) (getWidth() + sx2), (float) (sy3 + getHeight()), multipliedAlpha);
                    } else {
                        alpha = alpha3;
                        restoreTo = restoreTo4;
                        more = more2;
                        boolean z4 = childHasIdentityMatrix;
                        cache = cache2;
                        sy = sy3;
                        restoreTo2 = sx2;
                        renderNode = renderNode3;
                    }
                } else {
                    alpha = alpha3;
                    restoreTo = restoreTo4;
                    more = more2;
                    boolean z5 = childHasIdentityMatrix;
                    cache = cache2;
                    sy = sy3;
                    restoreTo2 = sx2;
                    renderNode = renderNode3;
                }
            } else {
                alpha = alpha3;
                restoreTo = restoreTo4;
                more = more2;
                boolean z6 = childHasIdentityMatrix;
                cache = cache2;
                sy = sy3;
                restoreTo2 = sx2;
                renderNode = renderNode3;
            }
            alpha2 = alpha;
        } else {
            if ((this.mPrivateFlags & 262144) == 262144) {
                onSetAlpha(255);
                this.mPrivateFlags &= -262145;
            }
            hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
            restoreTo = restoreTo4;
            more = more2;
            boolean z7 = childHasIdentityMatrix;
            sy = sy3;
            restoreTo2 = sx2;
            renderNode = renderNode3;
            cache = cache4;
        }
        if (!drawingWithRenderNode) {
            if ((parentFlags & 1) != 0 && cache == null) {
                if (offsetForScroll) {
                    canvas2.clipRect(restoreTo2, sy, restoreTo2 + getWidth(), sy + getHeight());
                } else if (!scalingRequired || cache == null) {
                    canvas2.clipRect(0, 0, getWidth(), getHeight());
                } else {
                    canvas2.clipRect(0, 0, cache.getWidth(), cache.getHeight());
                }
            }
            if (this.mClipBounds != null) {
                canvas2.clipRect(this.mClipBounds);
            }
        }
        if (!drawingWithDrawingCache) {
            if (drawingWithRenderNode) {
                this.mPrivateFlags = -2097153 & this.mPrivateFlags;
                ((RecordingCanvas) canvas2).drawRenderNode(renderNode);
            } else if ((this.mPrivateFlags & 128) == 128) {
                this.mPrivateFlags = -2097153 & this.mPrivateFlags;
                dispatchDraw(canvas);
            } else {
                draw(canvas);
            }
        } else if (cache != null) {
            this.mPrivateFlags = -2097153 & this.mPrivateFlags;
            if (layerType2 == 0 || this.mLayerPaint == null) {
                Paint cachePaint = viewGroup.mCachePaint;
                if (cachePaint == null) {
                    cachePaint = new Paint();
                    cachePaint.setDither(false);
                    viewGroup.mCachePaint = cachePaint;
                }
                cachePaint.setAlpha((int) (alpha2 * 255.0f));
                canvas2.drawBitmap(cache, 0.0f, 0.0f, cachePaint);
            } else {
                int layerPaintAlpha = this.mLayerPaint.getAlpha();
                if (alpha2 < 1.0f) {
                    this.mLayerPaint.setAlpha((int) (((float) layerPaintAlpha) * alpha2));
                }
                canvas2.drawBitmap(cache, 0.0f, 0.0f, this.mLayerPaint);
                if (alpha2 < 1.0f) {
                    this.mLayerPaint.setAlpha(layerPaintAlpha);
                }
            }
        }
        if (restoreTo >= 0) {
            canvas2.restoreToCount(restoreTo);
        }
        Animation a3 = a;
        if (a3 != null && !more) {
            if (!hardwareAcceleratedCanvas && !a3.getFillAfter()) {
                onSetAlpha(255);
            }
            viewGroup.finishAnimatingView(this, a3);
        }
        if (more && hardwareAcceleratedCanvas && a3.hasAlpha() && (this.mPrivateFlags & 262144) == 262144) {
            invalidate(true);
        }
        this.mRecreateDisplayList = false;
        return more;
    }

    static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            sDebugPaint = new Paint();
            sDebugPaint.setAntiAlias(false);
        }
        return sDebugPaint;
    }

    /* access modifiers changed from: package-private */
    public final int dipsToPixels(int dips) {
        return (int) ((((float) dips) * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private final void debugDrawFocus(Canvas canvas) {
        if (isFocused()) {
            int cornerSquareSize = dipsToPixels(8);
            int l = this.mScrollX;
            int r = (this.mRight + l) - this.mLeft;
            int t = this.mScrollY;
            int b = (this.mBottom + t) - this.mTop;
            Paint paint = getDebugPaint();
            paint.setColor(DEBUG_CORNERS_COLOR);
            paint.setStyle(Paint.Style.FILL);
            Canvas canvas2 = canvas;
            Paint paint2 = paint;
            canvas2.drawRect((float) l, (float) t, (float) (l + cornerSquareSize), (float) (t + cornerSquareSize), paint2);
            Canvas canvas3 = canvas;
            canvas3.drawRect((float) (r - cornerSquareSize), (float) t, (float) r, (float) (t + cornerSquareSize), paint2);
            canvas.drawRect((float) l, (float) (b - cornerSquareSize), (float) (l + cornerSquareSize), (float) b, paint2);
            canvas.drawRect((float) (r - cornerSquareSize), (float) (b - cornerSquareSize), (float) r, (float) b, paint2);
            paint.setStyle(Paint.Style.STROKE);
            Canvas canvas4 = canvas;
            canvas4.drawLine((float) l, (float) t, (float) r, (float) b, paint2);
            canvas4.drawLine((float) l, (float) b, (float) r, (float) t, paint2);
        }
    }

    public void draw(Canvas canvas) {
        float topFadeStrength;
        boolean drawRight;
        float topFadeStrength2;
        int saveCount;
        int rightSaveCount;
        int topSaveCount;
        int leftSaveCount;
        int bottomSaveCount;
        int length;
        int bottom;
        int right;
        int solidColor;
        int left;
        int length2;
        int rightSaveCount2;
        float fadeHeight;
        int right2;
        int bottom2;
        int bottomSaveCount2;
        int leftSaveCount2;
        int topSaveCount2;
        Canvas canvas2 = canvas;
        int privateFlags = this.mPrivateFlags;
        this.mPrivateFlags = (-2097153 & privateFlags) | 32;
        drawBackground(canvas);
        int viewFlags = this.mViewFlags;
        boolean horizontalEdges = (viewFlags & 4096) != 0;
        boolean verticalEdges = (viewFlags & 8192) != 0;
        if (verticalEdges || horizontalEdges) {
            float bottomFadeStrength = 0.0f;
            float leftFadeStrength = 0.0f;
            float rightFadeStrength = 0.0f;
            int paddingLeft = this.mPaddingLeft;
            int i = privateFlags;
            boolean offsetRequired = isPaddingOffsetRequired();
            if (offsetRequired) {
                paddingLeft += getLeftPaddingOffset();
            }
            int paddingLeft2 = paddingLeft;
            int left2 = this.mScrollX + paddingLeft2;
            boolean drawTop = false;
            int right3 = (((this.mRight + left2) - this.mLeft) - this.mPaddingRight) - paddingLeft2;
            int top = this.mScrollY + getFadeTop(offsetRequired);
            int bottom3 = top + getFadeHeight(offsetRequired);
            if (offsetRequired) {
                right3 += getRightPaddingOffset();
                bottom3 += getBottomPaddingOffset();
            }
            int right4 = right3;
            int bottom4 = bottom3;
            int i2 = viewFlags;
            ScrollabilityCache scrollabilityCache = this.mScrollCache;
            boolean drawBottom = false;
            float fadeHeight2 = (float) scrollabilityCache.fadingEdgeLength;
            boolean drawLeft = false;
            int length3 = (int) fadeHeight2;
            if (verticalEdges) {
                drawRight = false;
                topFadeStrength = 0.0f;
                if (top + length3 > bottom4 - length3) {
                    length3 = (bottom4 - top) / 2;
                }
            } else {
                drawRight = false;
                topFadeStrength = 0.0f;
            }
            if (horizontalEdges && left2 + length3 > right4 - length3) {
                length3 = (right4 - left2) / 2;
            }
            int length4 = length3;
            if (verticalEdges) {
                boolean z = verticalEdges;
                float topFadeStrength3 = Math.max(0.0f, Math.min(1.0f, getTopFadingEdgeStrength()));
                drawTop = topFadeStrength3 * fadeHeight2 > 1.0f;
                topFadeStrength2 = topFadeStrength3;
                bottomFadeStrength = Math.max(0.0f, Math.min(1.0f, getBottomFadingEdgeStrength()));
                drawBottom = bottomFadeStrength * fadeHeight2 > 1.0f;
            } else {
                topFadeStrength2 = topFadeStrength;
            }
            if (horizontalEdges) {
                leftFadeStrength = Math.max(0.0f, Math.min(1.0f, getLeftFadingEdgeStrength()));
                boolean drawLeft2 = leftFadeStrength * fadeHeight2 > 1.0f;
                boolean z2 = horizontalEdges;
                rightFadeStrength = Math.max(0.0f, Math.min(1.0f, getRightFadingEdgeStrength()));
                drawRight = rightFadeStrength * fadeHeight2 > 1.0f;
                drawLeft = drawLeft2;
            }
            int saveCount2 = canvas.getSaveCount();
            int topSaveCount3 = -1;
            int bottomSaveCount3 = -1;
            int leftSaveCount3 = -1;
            int rightSaveCount3 = -1;
            int solidColor2 = getSolidColor();
            if (solidColor2 == 0) {
                if (drawTop) {
                    topSaveCount3 = canvas2.saveUnclippedLayer(left2, top, right4, top + length4);
                }
                if (drawBottom) {
                    topSaveCount2 = topSaveCount3;
                    bottomSaveCount3 = canvas2.saveUnclippedLayer(left2, bottom4 - length4, right4, bottom4);
                } else {
                    topSaveCount2 = topSaveCount3;
                }
                if (drawLeft) {
                    leftSaveCount3 = canvas2.saveUnclippedLayer(left2, top, left2 + length4, bottom4);
                }
                if (drawRight) {
                    rightSaveCount3 = canvas2.saveUnclippedLayer(right4 - length4, top, right4, bottom4);
                }
                saveCount = saveCount2;
                bottomSaveCount = bottomSaveCount3;
                leftSaveCount = leftSaveCount3;
                rightSaveCount = rightSaveCount3;
                topSaveCount = topSaveCount2;
            } else {
                scrollabilityCache.setFadeColor(solidColor2);
                saveCount = saveCount2;
                bottomSaveCount = -1;
                leftSaveCount = -1;
                rightSaveCount = -1;
                topSaveCount = -1;
            }
            onDraw(canvas);
            dispatchDraw(canvas);
            int topSaveCount4 = topSaveCount;
            Paint p = scrollabilityCache.paint;
            int bottomSaveCount4 = bottomSaveCount;
            Matrix matrix = scrollabilityCache.matrix;
            float bottomFadeStrength2 = bottomFadeStrength;
            Shader fade = scrollabilityCache.shader;
            if (drawRight) {
                int left3 = left2;
                ScrollabilityCache scrollabilityCache2 = scrollabilityCache;
                matrix.setScale(1.0f, fadeHeight2 * rightFadeStrength);
                matrix.postRotate(90.0f);
                matrix.postTranslate((float) right4, (float) top);
                fade.setLocalMatrix(matrix);
                p.setShader(fade);
                if (solidColor2 == 0) {
                    canvas2.restoreUnclippedLayer(rightSaveCount, p);
                    bottom = bottom4;
                    fadeHeight = fadeHeight2;
                    solidColor = solidColor2;
                    right = right4;
                    int i3 = rightSaveCount;
                    length = length4;
                    float f = rightFadeStrength;
                    left = left3;
                    length2 = 1065353216;
                    rightSaveCount2 = top;
                    right2 = leftSaveCount;
                } else {
                    int i4 = rightSaveCount;
                    rightSaveCount2 = top;
                    float f2 = rightFadeStrength;
                    left = left3;
                    right = right4;
                    fadeHeight = fadeHeight2;
                    bottom = bottom4;
                    right2 = leftSaveCount;
                    solidColor = solidColor2;
                    length = length4;
                    length2 = 1065353216;
                    canvas.drawRect((float) (right4 - length4), (float) top, (float) right4, (float) bottom4, p);
                }
            } else {
                bottom = bottom4;
                solidColor = solidColor2;
                right = right4;
                ScrollabilityCache scrollabilityCache3 = scrollabilityCache;
                int i5 = rightSaveCount;
                length = length4;
                float f3 = rightFadeStrength;
                length2 = 1065353216;
                rightSaveCount2 = top;
                left = left2;
                fadeHeight = fadeHeight2;
                right2 = leftSaveCount;
            }
            if (drawLeft) {
                matrix.setScale(length2, fadeHeight * leftFadeStrength);
                matrix.postRotate(-90.0f);
                matrix.postTranslate((float) left, (float) rightSaveCount2);
                fade.setLocalMatrix(matrix);
                p.setShader(fade);
                if (solidColor == 0) {
                    canvas2.restoreUnclippedLayer(right2, p);
                    bottom2 = bottom;
                } else {
                    int bottom5 = bottom;
                    bottom2 = bottom5;
                    canvas.drawRect((float) left, (float) rightSaveCount2, (float) (left + length), (float) bottom5, p);
                }
            } else {
                bottom2 = bottom;
            }
            if (drawBottom) {
                matrix.setScale(length2, fadeHeight * bottomFadeStrength2);
                matrix.postRotate(180.0f);
                int bottom6 = bottom2;
                matrix.postTranslate((float) left, (float) bottom6);
                fade.setLocalMatrix(matrix);
                p.setShader(fade);
                if (solidColor == 0) {
                    int bottomSaveCount5 = bottomSaveCount4;
                    canvas2.restoreUnclippedLayer(bottomSaveCount5, p);
                    bottomSaveCount2 = bottomSaveCount5;
                    int i6 = bottom6;
                    int i7 = right2;
                    leftSaveCount2 = right;
                } else {
                    int right5 = right;
                    int i8 = right2;
                    leftSaveCount2 = right5;
                    bottomSaveCount2 = bottomSaveCount4;
                    int i9 = bottom6;
                    canvas.drawRect((float) left, (float) (bottom6 - length), (float) right5, (float) bottom6, p);
                }
            } else {
                int leftSaveCount4 = right2;
                bottomSaveCount2 = bottomSaveCount4;
                leftSaveCount2 = right;
                int i10 = bottom2;
            }
            if (drawTop) {
                matrix.setScale(1.0f, fadeHeight * topFadeStrength2);
                matrix.postTranslate((float) left, (float) rightSaveCount2);
                fade.setLocalMatrix(matrix);
                p.setShader(fade);
                if (solidColor == 0) {
                    canvas2.restoreUnclippedLayer(topSaveCount4, p);
                } else {
                    canvas.drawRect((float) left, (float) rightSaveCount2, (float) leftSaveCount2, (float) (rightSaveCount2 + length), p);
                }
            }
            canvas2.restoreToCount(saveCount);
            drawAutofilledHighlight(canvas);
            int i11 = bottomSaveCount2;
            if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().dispatchDraw(canvas2);
            }
            onDrawForeground(canvas);
            if (debugDraw()) {
                debugDrawFocus(canvas);
                return;
            }
            return;
        }
        onDraw(canvas);
        dispatchDraw(canvas);
        drawAutofilledHighlight(canvas);
        if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
            this.mOverlay.getOverlayView().dispatchDraw(canvas2);
        }
        onDrawForeground(canvas);
        drawDefaultFocusHighlight(canvas);
        if (debugDraw()) {
            debugDrawFocus(canvas);
        }
    }

    @UnsupportedAppUsage
    private void drawBackground(Canvas canvas) {
        Drawable background = this.mBackground;
        if (background != null) {
            setBackgroundBounds();
            if (!(!canvas.isHardwareAccelerated() || this.mAttachInfo == null || this.mAttachInfo.mThreadedRenderer == null)) {
                this.mBackgroundRenderNode = getDrawableRenderNode(background, this.mBackgroundRenderNode);
                RenderNode renderNode = this.mBackgroundRenderNode;
                if (renderNode != null && renderNode.hasDisplayList()) {
                    setBackgroundRenderNodeProperties(renderNode);
                    ((RecordingCanvas) canvas).drawRenderNode(renderNode);
                    return;
                }
            }
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            if ((scrollX | scrollY) == 0) {
                background.draw(canvas);
                return;
            }
            canvas.translate((float) scrollX, (float) scrollY);
            background.draw(canvas);
            canvas.translate((float) (-scrollX), (float) (-scrollY));
        }
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundBounds() {
        if (this.mBackgroundSizeChanged && this.mBackground != null) {
            this.mBackground.setBounds(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            this.mBackgroundSizeChanged = false;
            rebuildOutline();
        }
    }

    private void setBackgroundRenderNodeProperties(RenderNode renderNode) {
        renderNode.setTranslationX((float) this.mScrollX);
        renderNode.setTranslationY((float) this.mScrollY);
    }

    /* JADX INFO: finally extract failed */
    private RenderNode getDrawableRenderNode(Drawable drawable, RenderNode renderNode) {
        if (renderNode == null) {
            renderNode = RenderNode.create(drawable.getClass().getName(), new ViewAnimationHostBridge(this));
            renderNode.setUsageHint(1);
        }
        Rect bounds = drawable.getBounds();
        RecordingCanvas canvas = renderNode.beginRecording(bounds.width(), bounds.height());
        canvas.translate((float) (-bounds.left), (float) (-bounds.top));
        try {
            drawable.draw(canvas);
            renderNode.endRecording();
            renderNode.setLeftTopRightBottom(bounds.left, bounds.top, bounds.right, bounds.bottom);
            renderNode.setProjectBackwards(drawable.isProjected());
            renderNode.setProjectionReceiver(true);
            renderNode.setClipToBounds(false);
            return renderNode;
        } catch (Throwable th) {
            renderNode.endRecording();
            throw th;
        }
    }

    public ViewOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new ViewOverlay(this.mContext, this);
        }
        return this.mOverlay;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public int getSolidColor() {
        return 0;
    }

    private static String printFlags(int flags) {
        String output = "";
        int numFlags = 0;
        if ((flags & 1) == 1) {
            output = output + "TAKES_FOCUS";
            numFlags = 0 + 1;
        }
        int i = flags & 12;
        if (i == 4) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            return output + "INVISIBLE";
        } else if (i != 8) {
            return output;
        } else {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            return output + "GONE";
        }
    }

    private static String printPrivateFlags(int privateFlags) {
        String output = "";
        int numFlags = 0;
        if ((privateFlags & 1) == 1) {
            output = output + "WANTS_FOCUS";
            numFlags = 0 + 1;
        }
        if ((privateFlags & 2) == 2) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            output = output + "FOCUSED";
            numFlags++;
        }
        if ((privateFlags & 4) == 4) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            output = output + "SELECTED";
            numFlags++;
        }
        if ((privateFlags & 8) == 8) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            output = output + "IS_ROOT_NAMESPACE";
            numFlags++;
        }
        if ((privateFlags & 16) == 16) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            output = output + "HAS_BOUNDS";
            numFlags++;
        }
        if ((privateFlags & 32) != 32) {
            return output;
        }
        if (numFlags > 0) {
            output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
        }
        return output + "DRAWN";
    }

    public boolean isLayoutRequested() {
        return (this.mPrivateFlags & 4096) == 4096;
    }

    public static boolean isLayoutModeOptical(Object o) {
        return (o instanceof ViewGroup) && ((ViewGroup) o).isLayoutModeOptical();
    }

    private boolean setOpticalFrame(int left, int top, int right, int bottom) {
        Insets parentInsets = this.mParent instanceof View ? ((View) this.mParent).getOpticalInsets() : Insets.NONE;
        Insets childInsets = getOpticalInsets();
        return setFrame((parentInsets.left + left) - childInsets.left, (parentInsets.top + top) - childInsets.top, parentInsets.left + right + childInsets.right, parentInsets.top + bottom + childInsets.bottom);
    }

    public void layout(int l, int t, int r, int b) {
        boolean z;
        if ((this.mPrivateFlags3 & 8) != 0) {
            onMeasure(this.mOldWidthMeasureSpec, this.mOldHeightMeasureSpec);
            this.mPrivateFlags3 &= -9;
        }
        int oldL = this.mLeft;
        int oldT = this.mTop;
        int oldB = this.mBottom;
        int oldR = this.mRight;
        boolean changed = isLayoutModeOptical(this.mParent) ? setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);
        boolean z2 = false;
        Object obj = null;
        if (changed || (this.mPrivateFlags & 8192) == 8192) {
            onLayout(changed, l, t, r, b);
            if (!shouldDrawRoundScrollbar()) {
                this.mRoundScrollbarRenderer = null;
            } else if (this.mRoundScrollbarRenderer == null) {
                this.mRoundScrollbarRenderer = new RoundScrollbarRenderer(this);
            }
            this.mPrivateFlags &= -8193;
            ListenerInfo li = this.mListenerInfo;
            if (li != null && li.mOnLayoutChangeListeners != null) {
                ArrayList<OnLayoutChangeListener> listenersCopy = (ArrayList) li.mOnLayoutChangeListeners.clone();
                int numListeners = listenersCopy.size();
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= numListeners) {
                        break;
                    }
                    int oldL2 = oldL;
                    Object obj2 = obj;
                    listenersCopy.get(i2).onLayoutChange(this, l, t, r, b, oldL, oldT, oldR, oldB);
                    i = i2 + 1;
                    z2 = z2;
                    numListeners = numListeners;
                    listenersCopy = listenersCopy;
                    li = li;
                    oldL = oldL2;
                    obj = null;
                }
            }
            z = z2;
        } else {
            int i3 = oldL;
            z = false;
        }
        boolean wasLayoutValid = isLayoutValid();
        this.mPrivateFlags &= -4097;
        this.mPrivateFlags3 |= 4;
        if (!wasLayoutValid && isFocused()) {
            this.mPrivateFlags &= -2;
            if (canTakeFocus()) {
                clearParentsWantFocus();
            } else if (getViewRootImpl() == null || !getViewRootImpl().isInLayout()) {
                clearFocusInternal((View) null, true, z);
                clearParentsWantFocus();
            } else if (!hasParentWantsFocus()) {
                clearFocusInternal((View) null, true, z);
            }
        } else if ((this.mPrivateFlags & 1) != 0) {
            this.mPrivateFlags &= -2;
            View focused = findFocus();
            if (focused != null && !restoreDefaultFocus() && !hasParentWantsFocus()) {
                focused.clearFocusInternal((View) null, true, z);
            }
        }
        if ((this.mPrivateFlags3 & 134217728) != 0) {
            this.mPrivateFlags3 &= -134217729;
            notifyEnterOrExitForAutoFillIfNeeded(true);
        }
    }

    private boolean hasParentWantsFocus() {
        ViewParent parent = this.mParent;
        while (parent instanceof ViewGroup) {
            ViewGroup pv = (ViewGroup) parent;
            if ((pv.mPrivateFlags & 1) != 0) {
                return true;
            }
            parent = pv.mParent;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean setFrame(int left, int top, int right, int bottom) {
        int i = left;
        int i2 = top;
        int i3 = right;
        int i4 = bottom;
        boolean changed = false;
        if (!(this.mLeft == i && this.mRight == i3 && this.mTop == i2 && this.mBottom == i4)) {
            changed = true;
            int drawn = this.mPrivateFlags & 32;
            int oldWidth = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            int newWidth = i3 - i;
            int newHeight = i4 - i2;
            boolean sizeChanged = (newWidth == oldWidth && newHeight == oldHeight) ? false : true;
            invalidate(sizeChanged);
            this.mLeft = i;
            this.mTop = i2;
            this.mRight = i3;
            this.mBottom = i4;
            this.mRenderNode.setLeftTopRightBottom(this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mPrivateFlags |= 16;
            if (sizeChanged) {
                sizeChange(newWidth, newHeight, oldWidth, oldHeight);
            }
            if ((this.mViewFlags & 12) == 0 || this.mGhostView != null) {
                this.mPrivateFlags |= 32;
                invalidate(sizeChanged);
                invalidateParentCaches();
            }
            this.mPrivateFlags |= drawn;
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                boolean unused = this.mForegroundInfo.mBoundsChanged = true;
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
        return changed;
    }

    public final void setLeftTopRightBottom(int left, int top, int right, int bottom) {
        setFrame(left, top, right, bottom);
    }

    private void sizeChange(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().setRight(newWidth);
            this.mOverlay.getOverlayView().setBottom(newHeight);
        }
        if (!sCanFocusZeroSized && isLayoutValid() && (!(this.mParent instanceof ViewGroup) || !((ViewGroup) this.mParent).isLayoutSuppressed())) {
            if (newWidth <= 0 || newHeight <= 0) {
                if (hasFocus()) {
                    clearFocus();
                    if (this.mParent instanceof ViewGroup) {
                        ((ViewGroup) this.mParent).clearFocusedInCluster();
                    }
                }
                clearAccessibilityFocus();
            } else if ((oldWidth <= 0 || oldHeight <= 0) && this.mParent != null && canTakeFocus()) {
                this.mParent.focusableViewAvailable(this);
            }
        }
        rebuildOutline();
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
    }

    public Resources getResources() {
        return this.mResources;
    }

    public void invalidateDrawable(Drawable drawable) {
        if (verifyDrawable(drawable)) {
            Rect dirty = drawable.getDirtyBounds();
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
            rebuildOutline();
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (verifyDrawable(who) && what != null) {
            long delay = when - SystemClock.uptimeMillis();
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, what, who, Choreographer.subtractFrameDelay(delay));
                return;
            }
            getRunQueue().postDelayed(what, delay);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (verifyDrawable(who) && what != null) {
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, what, who);
            }
            getRunQueue().removeCallbacks(what);
        }
    }

    public void unscheduleDrawable(Drawable who) {
        if (this.mAttachInfo != null && who != null) {
            this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, (Runnable) null, who);
        }
    }

    /* access modifiers changed from: protected */
    public void resolveDrawables() {
        if (isLayoutDirectionResolved() || getRawLayoutDirection() != 2) {
            int layoutDirection = isLayoutDirectionResolved() ? getLayoutDirection() : getRawLayoutDirection();
            if (this.mBackground != null) {
                this.mBackground.setLayoutDirection(layoutDirection);
            }
            if (!(this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
                this.mForegroundInfo.mDrawable.setLayoutDirection(layoutDirection);
            }
            if (this.mDefaultFocusHighlight != null) {
                this.mDefaultFocusHighlight.setLayoutDirection(layoutDirection);
            }
            this.mPrivateFlags2 |= 1073741824;
            onResolveDrawables(layoutDirection);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean areDrawablesResolved() {
        return (this.mPrivateFlags2 & 1073741824) == 1073741824;
    }

    public void onResolveDrawables(int layoutDirection) {
    }

    /* access modifiers changed from: protected */
    public void resetResolvedDrawables() {
        resetResolvedDrawablesInternal();
    }

    /* access modifiers changed from: package-private */
    public void resetResolvedDrawablesInternal() {
        this.mPrivateFlags2 &= -1073741825;
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return who == this.mBackground || (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable == who) || this.mDefaultFocusHighlight == who;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        Drawable scrollBar;
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable bg = this.mBackground;
        if (bg != null && bg.isStateful()) {
            changed = false | bg.setState(state);
        }
        Drawable hl = this.mDefaultFocusHighlight;
        if (hl != null && hl.isStateful()) {
            changed |= hl.setState(state);
        }
        Drawable fg = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (fg != null && fg.isStateful()) {
            changed |= fg.setState(state);
        }
        if (!(this.mScrollCache == null || (scrollBar = this.mScrollCache.scrollBar) == null || !scrollBar.isStateful())) {
            changed |= scrollBar.setState(state) && this.mScrollCache.state != 0;
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        if (this.mBackground != null) {
            this.mBackground.setHotspot(x, y);
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.setHotspot(x, y);
        }
        if (!(this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
            this.mForegroundInfo.mDrawable.setHotspot(x, y);
        }
        dispatchDrawableHotspotChanged(x, y);
    }

    public void dispatchDrawableHotspotChanged(float x, float y) {
    }

    public void refreshDrawableState() {
        this.mPrivateFlags |= 1024;
        drawableStateChanged();
        ViewParent parent = this.mParent;
        if (parent != null) {
            parent.childDrawableStateChanged(this);
        }
    }

    private Drawable getDefaultFocusHighlightDrawable() {
        if (this.mDefaultFocusHighlightCache == null && this.mContext != null) {
            TypedArray ta = this.mContext.obtainStyledAttributes(new int[]{16843534});
            this.mDefaultFocusHighlightCache = ta.getDrawable(0);
            ta.recycle();
        }
        return this.mDefaultFocusHighlightCache;
    }

    private void setDefaultFocusHighlight(Drawable highlight) {
        this.mDefaultFocusHighlight = highlight;
        boolean z = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (highlight != null) {
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= -129;
            }
            highlight.setLayoutDirection(getLayoutDirection());
            if (highlight.isStateful()) {
                highlight.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                if (getWindowVisibility() != 0 || !isShown()) {
                    z = false;
                }
                highlight.setVisible(z, false);
            }
            highlight.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
            this.mPrivateFlags |= 128;
        }
        invalidate();
    }

    public boolean isDefaultFocusHighlightNeeded(Drawable background, Drawable foreground) {
        boolean lackFocusState = (background == null || !background.isStateful() || !background.hasFocusStateSpecified()) && (foreground == null || !foreground.isStateful() || !foreground.hasFocusStateSpecified());
        if (isInTouchMode() || !getDefaultFocusHighlightEnabled() || !lackFocusState || !isAttachedToWindow() || !sUseDefaultFocusHighlight) {
            return false;
        }
        return true;
    }

    private void switchDefaultFocusHighlight() {
        Drawable drawable;
        if (isFocused()) {
            Drawable drawable2 = this.mBackground;
            if (this.mForegroundInfo == null) {
                drawable = null;
            } else {
                drawable = this.mForegroundInfo.mDrawable;
            }
            boolean needed = isDefaultFocusHighlightNeeded(drawable2, drawable);
            boolean active = this.mDefaultFocusHighlight != null;
            if (needed && !active) {
                setDefaultFocusHighlight(getDefaultFocusHighlightDrawable());
            } else if (!needed && active) {
                setDefaultFocusHighlight((Drawable) null);
            }
        }
    }

    private void drawDefaultFocusHighlight(Canvas canvas) {
        if (this.mDefaultFocusHighlight != null) {
            if (this.mDefaultFocusHighlightSizeChanged) {
                this.mDefaultFocusHighlightSizeChanged = false;
                int l = this.mScrollX;
                int r = (this.mRight + l) - this.mLeft;
                int t = this.mScrollY;
                this.mDefaultFocusHighlight.setBounds(l, t, r, (this.mBottom + t) - this.mTop);
            }
            this.mDefaultFocusHighlight.draw(canvas);
        }
    }

    public final int[] getDrawableState() {
        if (this.mDrawableState != null && (this.mPrivateFlags & 1024) == 0) {
            return this.mDrawableState;
        }
        this.mDrawableState = onCreateDrawableState(0);
        this.mPrivateFlags &= -1025;
        return this.mDrawableState;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        if ((this.mViewFlags & 4194304) == 4194304 && (this.mParent instanceof View)) {
            return ((View) this.mParent).onCreateDrawableState(extraSpace);
        }
        int privateFlags = this.mPrivateFlags;
        int viewStateIndex = 0;
        if ((privateFlags & 16384) != 0) {
            viewStateIndex = 0 | 16;
        }
        if ((this.mViewFlags & 32) == 0) {
            viewStateIndex |= 8;
        }
        if (isFocused()) {
            viewStateIndex |= 4;
        }
        if ((privateFlags & 4) != 0) {
            viewStateIndex |= 2;
        }
        if (hasWindowFocus()) {
            viewStateIndex |= 1;
        }
        if ((1073741824 & privateFlags) != 0) {
            viewStateIndex |= 32;
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerationRequested && ThreadedRenderer.isAvailable()) {
            viewStateIndex |= 64;
        }
        if ((268435456 & privateFlags) != 0) {
            viewStateIndex |= 128;
        }
        int privateFlags2 = this.mPrivateFlags2;
        if ((privateFlags2 & 1) != 0) {
            viewStateIndex |= 256;
        }
        if ((privateFlags2 & 2) != 0) {
            viewStateIndex |= 512;
        }
        int[] drawableState = StateSet.get(viewStateIndex);
        if (extraSpace == 0) {
            return drawableState;
        }
        if (drawableState == null) {
            return new int[extraSpace];
        }
        int[] fullState = new int[(drawableState.length + extraSpace)];
        System.arraycopy(drawableState, 0, fullState, 0, drawableState.length);
        return fullState;
    }

    protected static int[] mergeDrawableStates(int[] baseState, int[] additionalState) {
        int i = baseState.length - 1;
        while (i >= 0 && baseState[i] == 0) {
            i--;
        }
        System.arraycopy(additionalState, 0, baseState, i + 1, additionalState.length);
        return baseState;
    }

    public void jumpDrawablesToCurrentState() {
        if (this.mBackground != null) {
            this.mBackground.jumpToCurrentState();
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.jumpToCurrentState();
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.jumpToCurrentState();
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.jumpToCurrentState();
        }
    }

    @RemotableViewMethod
    public void setBackgroundColor(int color) {
        if (this.mBackground instanceof ColorDrawable) {
            ((ColorDrawable) this.mBackground.mutate()).setColor(color);
            computeOpaqueFlags();
            this.mBackgroundResource = 0;
            return;
        }
        setBackground(new ColorDrawable(color));
    }

    @RemotableViewMethod
    public void setBackgroundResource(int resid) {
        if (resid == 0 || resid != this.mBackgroundResource) {
            Drawable d = null;
            if (resid != 0) {
                d = this.mContext.getDrawable(resid);
            }
            setBackground(d);
            this.mBackgroundResource = resid;
        }
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        computeOpaqueFlags();
        if (background != this.mBackground) {
            boolean requestLayout = false;
            this.mBackgroundResource = 0;
            if (this.mBackground != null) {
                if (isAttachedToWindow()) {
                    this.mBackground.setVisible(false, false);
                }
                this.mBackground.setCallback((Drawable.Callback) null);
                unscheduleDrawable(this.mBackground);
            }
            if (background != null) {
                Rect padding = sThreadLocal.get();
                if (padding == null) {
                    padding = new Rect();
                    sThreadLocal.set(padding);
                }
                resetResolvedDrawablesInternal();
                background.setLayoutDirection(getLayoutDirection());
                if (background.getPadding(padding)) {
                    resetResolvedPaddingInternal();
                    if (background.getLayoutDirection() != 1) {
                        this.mUserPaddingLeftInitial = padding.left;
                        this.mUserPaddingRightInitial = padding.right;
                        internalSetPadding(padding.left, padding.top, padding.right, padding.bottom);
                    } else {
                        this.mUserPaddingLeftInitial = padding.right;
                        this.mUserPaddingRightInitial = padding.left;
                        internalSetPadding(padding.right, padding.top, padding.left, padding.bottom);
                    }
                    this.mLeftPaddingDefined = false;
                    this.mRightPaddingDefined = false;
                }
                if (!(this.mBackground != null && this.mBackground.getMinimumHeight() == background.getMinimumHeight() && this.mBackground.getMinimumWidth() == background.getMinimumWidth())) {
                    requestLayout = true;
                }
                this.mBackground = background;
                if (background.isStateful()) {
                    background.setState(getDrawableState());
                }
                if (isAttachedToWindow()) {
                    background.setVisible(getWindowVisibility() == 0 && isShown(), false);
                }
                applyBackgroundTint();
                background.setCallback(this);
                if ((this.mPrivateFlags & 128) != 0) {
                    this.mPrivateFlags &= -129;
                    requestLayout = true;
                }
            } else {
                this.mBackground = null;
                if ((this.mViewFlags & 128) != 0 && this.mDefaultFocusHighlight == null && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
                    this.mPrivateFlags |= 128;
                }
                requestLayout = true;
            }
            computeOpaqueFlags();
            if (requestLayout) {
                requestLayout();
            }
            this.mBackgroundSizeChanged = true;
            invalidate(true);
            invalidateOutline();
        }
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public void setBackgroundTintList(ColorStateList tint) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = tint;
        this.mBackgroundTint.mHasTintList = true;
        applyBackgroundTint();
    }

    public ColorStateList getBackgroundTintList() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList;
        }
        return null;
    }

    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        BlendMode mode = null;
        if (tintMode != null) {
            mode = BlendMode.fromValue(tintMode.nativeInt);
        }
        setBackgroundTintBlendMode(mode);
    }

    public void setBackgroundTintBlendMode(BlendMode blendMode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mBlendMode = blendMode;
        this.mBackgroundTint.mHasTintMode = true;
        applyBackgroundTint();
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        if (this.mBackgroundTint == null || this.mBackgroundTint.mBlendMode == null) {
            return null;
        }
        return BlendMode.blendModeToPorterDuffMode(this.mBackgroundTint.mBlendMode);
    }

    public BlendMode getBackgroundTintBlendMode() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mBlendMode;
        }
        return null;
    }

    private void applyBackgroundTint() {
        if (this.mBackground != null && this.mBackgroundTint != null) {
            TintInfo tintInfo = this.mBackgroundTint;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                this.mBackground = this.mBackground.mutate();
                if (tintInfo.mHasTintList) {
                    this.mBackground.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mBackground.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mBackground.isStateful()) {
                    this.mBackground.setState(getDrawableState());
                }
            }
        }
    }

    public Drawable getForeground() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mDrawable;
        }
        return null;
    }

    public void setForeground(Drawable foreground) {
        if (this.mForegroundInfo == null) {
            if (foreground != null) {
                this.mForegroundInfo = new ForegroundInfo();
            } else {
                return;
            }
        }
        if (foreground != this.mForegroundInfo.mDrawable) {
            if (this.mForegroundInfo.mDrawable != null) {
                if (isAttachedToWindow()) {
                    this.mForegroundInfo.mDrawable.setVisible(false, false);
                }
                this.mForegroundInfo.mDrawable.setCallback((Drawable.Callback) null);
                unscheduleDrawable(this.mForegroundInfo.mDrawable);
            }
            Drawable unused = this.mForegroundInfo.mDrawable = foreground;
            boolean z = true;
            boolean unused2 = this.mForegroundInfo.mBoundsChanged = true;
            if (foreground != null) {
                if ((this.mPrivateFlags & 128) != 0) {
                    this.mPrivateFlags &= -129;
                }
                foreground.setLayoutDirection(getLayoutDirection());
                if (foreground.isStateful()) {
                    foreground.setState(getDrawableState());
                }
                applyForegroundTint();
                if (isAttachedToWindow()) {
                    if (getWindowVisibility() != 0 || !isShown()) {
                        z = false;
                    }
                    foreground.setVisible(z, false);
                }
                foreground.setCallback(this);
            } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && this.mDefaultFocusHighlight == null) {
                this.mPrivateFlags |= 128;
            }
            requestLayout();
            invalidate();
        }
    }

    public boolean isForegroundInsidePadding() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mInsidePadding;
        }
        return true;
    }

    public int getForegroundGravity() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mGravity;
        }
        return 8388659;
    }

    public void setForegroundGravity(int gravity) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= 8388611;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            int unused = this.mForegroundInfo.mGravity = gravity;
            requestLayout();
        }
    }

    public void setForegroundTintList(ColorStateList tint) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            TintInfo unused = this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mTintList = tint;
        this.mForegroundInfo.mTintInfo.mHasTintList = true;
        applyForegroundTint();
    }

    public ColorStateList getForegroundTintList() {
        if (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mTintList;
    }

    public void setForegroundTintMode(PorterDuff.Mode tintMode) {
        BlendMode mode = null;
        if (tintMode != null) {
            mode = BlendMode.fromValue(tintMode.nativeInt);
        }
        setForegroundTintBlendMode(mode);
    }

    public void setForegroundTintBlendMode(BlendMode blendMode) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            TintInfo unused = this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mBlendMode = blendMode;
        this.mForegroundInfo.mTintInfo.mHasTintMode = true;
        applyForegroundTint();
    }

    public PorterDuff.Mode getForegroundTintMode() {
        BlendMode blendMode = (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) ? null : this.mForegroundInfo.mTintInfo.mBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getForegroundTintBlendMode() {
        if (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mBlendMode;
    }

    private void applyForegroundTint() {
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null && this.mForegroundInfo.mTintInfo != null) {
            TintInfo tintInfo = this.mForegroundInfo.mTintInfo;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                Drawable unused = this.mForegroundInfo.mDrawable = this.mForegroundInfo.mDrawable.mutate();
                if (tintInfo.mHasTintList) {
                    this.mForegroundInfo.mDrawable.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mForegroundInfo.mDrawable.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mForegroundInfo.mDrawable.isStateful()) {
                    this.mForegroundInfo.mDrawable.setState(getDrawableState());
                }
            }
        }
    }

    private Drawable getAutofilledDrawable() {
        if (this.mAttachInfo == null) {
            return null;
        }
        if (this.mAttachInfo.mAutofilledDrawable == null) {
            Context rootContext = getRootView().getContext();
            TypedArray a = rootContext.getTheme().obtainStyledAttributes(AUTOFILL_HIGHLIGHT_ATTR);
            int attributeResourceId = a.getResourceId(0, 0);
            this.mAttachInfo.mAutofilledDrawable = rootContext.getDrawable(attributeResourceId);
            a.recycle();
        }
        return this.mAttachInfo.mAutofilledDrawable;
    }

    private void drawAutofilledHighlight(Canvas canvas) {
        Drawable autofilledHighlight;
        if (isAutofilled() && (autofilledHighlight = getAutofilledDrawable()) != null) {
            autofilledHighlight.setBounds(0, 0, getWidth(), getHeight());
            autofilledHighlight.draw(canvas);
        }
    }

    public void onDrawForeground(Canvas canvas) {
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);
        Drawable foreground = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (foreground != null) {
            if (this.mForegroundInfo.mBoundsChanged) {
                boolean unused = this.mForegroundInfo.mBoundsChanged = false;
                Rect selfBounds = this.mForegroundInfo.mSelfBounds;
                Rect overlayBounds = this.mForegroundInfo.mOverlayBounds;
                if (this.mForegroundInfo.mInsidePadding) {
                    selfBounds.set(0, 0, getWidth(), getHeight());
                } else {
                    selfBounds.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }
                Gravity.apply(this.mForegroundInfo.mGravity, foreground.getIntrinsicWidth(), foreground.getIntrinsicHeight(), selfBounds, overlayBounds, getLayoutDirection());
                foreground.setBounds(overlayBounds);
            }
            foreground.draw(canvas);
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = left;
        this.mUserPaddingRightInitial = right;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        internalSetPadding(left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768420)
    public void internalSetPadding(int left, int top, int right, int bottom) {
        this.mUserPaddingLeft = left;
        this.mUserPaddingRight = right;
        this.mUserPaddingBottom = bottom;
        int viewFlags = this.mViewFlags;
        boolean changed = false;
        if ((viewFlags & 768) != 0) {
            int i = 0;
            if ((viewFlags & 512) != 0) {
                int offset = (viewFlags & 16777216) == 0 ? 0 : getVerticalScrollbarWidth();
                switch (this.mVerticalScrollbarPosition) {
                    case 0:
                        if (!isLayoutRtl()) {
                            right += offset;
                            break;
                        } else {
                            left += offset;
                            break;
                        }
                    case 1:
                        left += offset;
                        break;
                    case 2:
                        right += offset;
                        break;
                }
            }
            if ((viewFlags & 256) != 0) {
                if ((viewFlags & 16777216) != 0) {
                    i = getHorizontalScrollbarHeight();
                }
                bottom += i;
            }
        }
        if (this.mPaddingLeft != left) {
            changed = true;
            this.mPaddingLeft = left;
        }
        if (this.mPaddingTop != top) {
            changed = true;
            this.mPaddingTop = top;
        }
        if (this.mPaddingRight != right) {
            changed = true;
            this.mPaddingRight = right;
        }
        if (this.mPaddingBottom != bottom) {
            changed = true;
            this.mPaddingBottom = bottom;
        }
        if (changed) {
            requestLayout();
            invalidateOutline();
        }
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = start;
        this.mUserPaddingEnd = end;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        if (getLayoutDirection() != 1) {
            this.mUserPaddingLeftInitial = start;
            this.mUserPaddingRightInitial = end;
            internalSetPadding(start, top, end, bottom);
            return;
        }
        this.mUserPaddingLeftInitial = end;
        this.mUserPaddingRightInitial = start;
        internalSetPadding(end, top, start, bottom);
    }

    public int getSourceLayoutResId() {
        return this.mSourceLayoutId;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public int getPaddingLeft() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return this.mPaddingLeft;
    }

    public int getPaddingStart() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return getLayoutDirection() == 1 ? this.mPaddingRight : this.mPaddingLeft;
    }

    public int getPaddingRight() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return this.mPaddingRight;
    }

    public int getPaddingEnd() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return getLayoutDirection() == 1 ? this.mPaddingLeft : this.mPaddingRight;
    }

    public boolean isPaddingRelative() {
        return (this.mUserPaddingStart == Integer.MIN_VALUE && this.mUserPaddingEnd == Integer.MIN_VALUE) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public Insets computeOpticalInsets() {
        return this.mBackground == null ? Insets.NONE : this.mBackground.getOpticalInsets();
    }

    @UnsupportedAppUsage
    public void resetPaddingToInitialValues() {
        if (isRtlCompatibilityMode()) {
            this.mPaddingLeft = this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingRightInitial;
        } else if (isLayoutRtl()) {
            this.mPaddingLeft = this.mUserPaddingEnd >= 0 ? this.mUserPaddingEnd : this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingStart >= 0 ? this.mUserPaddingStart : this.mUserPaddingRightInitial;
        } else {
            this.mPaddingLeft = this.mUserPaddingStart >= 0 ? this.mUserPaddingStart : this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingEnd >= 0 ? this.mUserPaddingEnd : this.mUserPaddingRightInitial;
        }
    }

    public Insets getOpticalInsets() {
        if (this.mLayoutInsets == null) {
            this.mLayoutInsets = computeOpticalInsets();
        }
        return this.mLayoutInsets;
    }

    public void setOpticalInsets(Insets insets) {
        this.mLayoutInsets = insets;
    }

    public void setSelected(boolean selected) {
        if (((this.mPrivateFlags & 4) != 0) != selected) {
            this.mPrivateFlags = (this.mPrivateFlags & -5) | (selected ? 4 : 0);
            if (!selected) {
                resetPressedState();
            }
            invalidate(true);
            refreshDrawableState();
            dispatchSetSelected(selected);
            if (selected) {
                sendAccessibilityEvent(4);
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSetSelected(boolean selected) {
    }

    @ViewDebug.ExportedProperty
    public boolean isSelected() {
        return (this.mPrivateFlags & 4) != 0;
    }

    public void setActivated(boolean activated) {
        int i = 1073741824;
        if (((this.mPrivateFlags & 1073741824) != 0) != activated) {
            int i2 = this.mPrivateFlags & -1073741825;
            if (!activated) {
                i = 0;
            }
            this.mPrivateFlags = i2 | i;
            invalidate(true);
            refreshDrawableState();
            dispatchSetActivated(activated);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchSetActivated(boolean activated) {
    }

    @ViewDebug.ExportedProperty
    public boolean isActivated() {
        return (this.mPrivateFlags & 1073741824) != 0;
    }

    public ViewTreeObserver getViewTreeObserver() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mTreeObserver;
        }
        if (this.mFloatingTreeObserver == null) {
            this.mFloatingTreeObserver = new ViewTreeObserver(this.mContext);
        }
        return this.mFloatingTreeObserver;
    }

    /* JADX WARNING: type inference failed for: r1v3, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getRootView() {
        /*
            r2 = this;
            android.view.View$AttachInfo r0 = r2.mAttachInfo
            if (r0 == 0) goto L_0x000b
            android.view.View$AttachInfo r0 = r2.mAttachInfo
            android.view.View r0 = r0.mRootView
            if (r0 == 0) goto L_0x000b
            return r0
        L_0x000b:
            r0 = r2
        L_0x000c:
            android.view.ViewParent r1 = r0.mParent
            if (r1 == 0) goto L_0x001c
            android.view.ViewParent r1 = r0.mParent
            boolean r1 = r1 instanceof android.view.View
            if (r1 == 0) goto L_0x001c
            android.view.ViewParent r1 = r0.mParent
            r0 = r1
            android.view.View r0 = (android.view.View) r0
            goto L_0x000c
        L_0x001c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.getRootView():android.view.View");
    }

    @UnsupportedAppUsage
    public boolean toGlobalMotionEvent(MotionEvent ev) {
        AttachInfo info = this.mAttachInfo;
        if (info == null) {
            return false;
        }
        Matrix m = info.mTmpMatrix;
        m.set(Matrix.IDENTITY_MATRIX);
        transformMatrixToGlobal(m);
        ev.transform(m);
        return true;
    }

    @UnsupportedAppUsage
    public boolean toLocalMotionEvent(MotionEvent ev) {
        AttachInfo info = this.mAttachInfo;
        if (info == null) {
            return false;
        }
        Matrix m = info.mTmpMatrix;
        m.set(Matrix.IDENTITY_MATRIX);
        transformMatrixToLocal(m);
        ev.transform(m);
        return true;
    }

    public void transformMatrixToGlobal(Matrix matrix) {
        ViewParent parent = this.mParent;
        if (parent instanceof View) {
            View vp = (View) parent;
            vp.transformMatrixToGlobal(matrix);
            matrix.preTranslate((float) (-vp.mScrollX), (float) (-vp.mScrollY));
        } else if (parent instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) parent;
            vr.transformMatrixToGlobal(matrix);
            matrix.preTranslate(0.0f, (float) (-vr.mCurScrollY));
        }
        matrix.preTranslate((float) this.mLeft, (float) this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.preConcat(getMatrix());
        }
    }

    public void transformMatrixToLocal(Matrix matrix) {
        ViewParent parent = this.mParent;
        if (parent instanceof View) {
            View vp = (View) parent;
            vp.transformMatrixToLocal(matrix);
            matrix.postTranslate((float) vp.mScrollX, (float) vp.mScrollY);
        } else if (parent instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) parent;
            vr.transformMatrixToLocal(matrix);
            matrix.postTranslate(0.0f, (float) vr.mCurScrollY);
        }
        matrix.postTranslate((float) (-this.mLeft), (float) (-this.mTop));
        if (!hasIdentityMatrix()) {
            matrix.postConcat(getInverseMatrix());
        }
    }

    @ViewDebug.ExportedProperty(category = "layout", indexMapping = {@ViewDebug.IntToString(from = 0, to = "x"), @ViewDebug.IntToString(from = 1, to = "y")})
    @UnsupportedAppUsage
    public int[] getLocationOnScreen() {
        int[] location = new int[2];
        getLocationOnScreen(location);
        return location;
    }

    public void getLocationOnScreen(int[] outLocation) {
        getLocationInWindow(outLocation);
        AttachInfo info = this.mAttachInfo;
        if (info != null) {
            outLocation[0] = outLocation[0] + info.mWindowLeft;
            outLocation[1] = outLocation[1] + info.mWindowTop;
        }
    }

    public void getLocationInWindow(int[] outLocation) {
        if (outLocation == null || outLocation.length < 2) {
            throw new IllegalArgumentException("outLocation must be an array of two integers");
        }
        outLocation[0] = 0;
        outLocation[1] = 0;
        transformFromViewToWindowSpace(outLocation);
    }

    public void transformFromViewToWindowSpace(int[] inOutLocation) {
        if (inOutLocation == null || inOutLocation.length < 2) {
            throw new IllegalArgumentException("inOutLocation must be an array of two integers");
        } else if (this.mAttachInfo == null) {
            inOutLocation[1] = 0;
            inOutLocation[0] = 0;
        } else {
            float[] position = this.mAttachInfo.mTmpTransformLocation;
            position[0] = (float) inOutLocation[0];
            position[1] = (float) inOutLocation[1];
            if (!hasIdentityMatrix()) {
                getMatrix().mapPoints(position);
            }
            position[0] = position[0] + ((float) this.mLeft);
            position[1] = position[1] + ((float) this.mTop);
            ViewParent viewParent = this.mParent;
            while (viewParent instanceof View) {
                View view = (View) viewParent;
                position[0] = position[0] - ((float) view.mScrollX);
                position[1] = position[1] - ((float) view.mScrollY);
                if (!view.hasIdentityMatrix()) {
                    view.getMatrix().mapPoints(position);
                }
                position[0] = position[0] + ((float) view.mLeft);
                position[1] = position[1] + ((float) view.mTop);
                viewParent = view.mParent;
            }
            if (viewParent instanceof ViewRootImpl) {
                position[1] = position[1] - ((float) ((ViewRootImpl) viewParent).mCurScrollY);
            }
            inOutLocation[0] = Math.round(position[0]);
            inOutLocation[1] = Math.round(position[1]);
        }
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewTraversal(int id) {
        if (id == this.mID) {
            return this;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewWithTagTraversal(Object tag) {
        if (tag == null || !tag.equals(this.mTag)) {
            return null;
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View childToSkip) {
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final <T extends View> T findViewById(int id) {
        if (id == -1) {
            return null;
        }
        return findViewTraversal(id);
    }

    public final <T extends View> T requireViewById(int id) {
        T view = findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this View");
    }

    public <T extends View> T findViewByAccessibilityIdTraversal(int accessibilityId) {
        if (getAccessibilityViewId() == accessibilityId) {
            return this;
        }
        return null;
    }

    public <T extends View> T findViewByAutofillIdTraversal(int autofillId) {
        if (getAutofillViewId() == autofillId) {
            return this;
        }
        return null;
    }

    public final <T extends View> T findViewWithTag(Object tag) {
        if (tag == null) {
            return null;
        }
        return findViewWithTagTraversal(tag);
    }

    public final <T extends View> T findViewByPredicate(Predicate<View> predicate) {
        return findViewByPredicateTraversal(predicate, (View) null);
    }

    public final <T extends View> T findViewByPredicateInsideOut(View start, Predicate<View> predicate) {
        T view;
        View start2 = start;
        View childToSkip = null;
        while (true) {
            view = start2.findViewByPredicateTraversal(predicate, childToSkip);
            if (view != null || start2 == this) {
                return view;
            }
            ViewParent parent = start2.getParent();
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            childToSkip = start2;
            start2 = (View) parent;
        }
        return view;
    }

    public void setId(int id) {
        this.mID = id;
        if (this.mID == -1 && this.mLabelForId != -1) {
            this.mID = generateViewId();
        }
    }

    public void setIsRootNamespace(boolean isRoot) {
        if (isRoot) {
            this.mPrivateFlags |= 8;
        } else {
            this.mPrivateFlags &= -9;
        }
    }

    @UnsupportedAppUsage
    public boolean isRootNamespace() {
        return (this.mPrivateFlags & 8) != 0;
    }

    @ViewDebug.CapturedViewProperty
    public int getId() {
        return this.mID;
    }

    public long getUniqueDrawingId() {
        return this.mRenderNode.getUniqueId();
    }

    @ViewDebug.ExportedProperty
    public Object getTag() {
        return this.mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Object getTag(int key) {
        if (this.mKeyedTags != null) {
            return this.mKeyedTags.get(key);
        }
        return null;
    }

    public void setTag(int key, Object tag) {
        if ((key >>> 24) >= 2) {
            setKeyedTag(key, tag);
            return;
        }
        throw new IllegalArgumentException("The key must be an application-specific resource id.");
    }

    @UnsupportedAppUsage
    public void setTagInternal(int key, Object tag) {
        if ((key >>> 24) == 1) {
            setKeyedTag(key, tag);
            return;
        }
        throw new IllegalArgumentException("The key must be a framework-specific resource id.");
    }

    private void setKeyedTag(int key, Object tag) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new SparseArray<>(2);
        }
        this.mKeyedTags.put(key, tag);
    }

    @UnsupportedAppUsage
    public void debug() {
        debug(0);
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void debug(int depth) {
        String output;
        String output2 = debugIndent(depth - 1) + "+ " + this;
        int id = getId();
        if (id != -1) {
            output2 = output2 + " (id=" + id + ")";
        }
        Object tag = getTag();
        if (tag != null) {
            output2 = output2 + " (tag=" + tag + ")";
        }
        Log.d(VIEW_LOG_TAG, output2);
        if ((this.mPrivateFlags & 2) != 0) {
            Log.d(VIEW_LOG_TAG, debugIndent(depth) + " FOCUSED");
        }
        Log.d(VIEW_LOG_TAG, debugIndent(depth) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ");
        if (!(this.mPaddingLeft == 0 && this.mPaddingTop == 0 && this.mPaddingRight == 0 && this.mPaddingBottom == 0)) {
            Log.d(VIEW_LOG_TAG, debugIndent(depth) + "padding={" + this.mPaddingLeft + ", " + this.mPaddingTop + ", " + this.mPaddingRight + ", " + this.mPaddingBottom + "}");
        }
        Log.d(VIEW_LOG_TAG, debugIndent(depth) + "mMeasureWidth=" + this.mMeasuredWidth + " mMeasureHeight=" + this.mMeasuredHeight);
        String output3 = debugIndent(depth);
        if (this.mLayoutParams == null) {
            output = output3 + "BAD! no layout params";
        } else {
            output = this.mLayoutParams.debug(output3);
        }
        Log.d(VIEW_LOG_TAG, output);
        Log.d(VIEW_LOG_TAG, ((debugIndent(depth) + "flags={") + printFlags(this.mViewFlags)) + "}");
        Log.d(VIEW_LOG_TAG, ((debugIndent(depth) + "privateFlags={") + printPrivateFlags(this.mPrivateFlags)) + "}");
    }

    protected static String debugIndent(int depth) {
        StringBuilder spaces = new StringBuilder(((depth * 2) + 3) * 2);
        for (int i = 0; i < (depth * 2) + 3; i++) {
            spaces.append(' ');
            spaces.append(' ');
        }
        return spaces.toString();
    }

    @ViewDebug.ExportedProperty(category = "layout")
    public int getBaseline() {
        return -1;
    }

    public boolean isInLayout() {
        ViewRootImpl viewRoot = getViewRootImpl();
        return viewRoot != null && viewRoot.isInLayout();
    }

    public void requestLayout() {
        if (this.mMeasureCache != null) {
            this.mMeasureCache.clear();
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRequestingLayout == null) {
            ViewRootImpl viewRoot = getViewRootImpl();
            if (viewRoot == null || !viewRoot.isInLayout() || viewRoot.requestLayoutDuringLayout(this)) {
                this.mAttachInfo.mViewRequestingLayout = this;
            } else {
                return;
            }
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
        if (this.mParent != null && !this.mParent.isLayoutRequested()) {
            this.mParent.requestLayout();
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRequestingLayout == this) {
            this.mAttachInfo.mViewRequestingLayout = null;
        }
    }

    public void forceLayout() {
        if (this.mMeasureCache != null) {
            this.mMeasureCache.clear();
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
    }

    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0111  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void measure(int r21, int r22) {
        /*
            r20 = this;
            r0 = r20
            boolean r1 = isLayoutModeOptical(r20)
            android.view.ViewParent r2 = r0.mParent
            boolean r2 = isLayoutModeOptical(r2)
            if (r1 == r2) goto L_0x0033
            android.graphics.Insets r2 = r20.getOpticalInsets()
            int r3 = r2.left
            int r4 = r2.right
            int r3 = r3 + r4
            int r4 = r2.top
            int r5 = r2.bottom
            int r4 = r4 + r5
            if (r1 == 0) goto L_0x0020
            int r5 = -r3
            goto L_0x0021
        L_0x0020:
            r5 = r3
        L_0x0021:
            r6 = r21
            int r5 = android.view.View.MeasureSpec.adjust(r6, r5)
            if (r1 == 0) goto L_0x002b
            int r6 = -r4
            goto L_0x002c
        L_0x002b:
            r6 = r4
        L_0x002c:
            r7 = r22
            int r2 = android.view.View.MeasureSpec.adjust(r7, r6)
            goto L_0x0039
        L_0x0033:
            r6 = r21
            r7 = r22
            r5 = r6
            r2 = r7
        L_0x0039:
            long r3 = (long) r5
            r6 = 32
            long r3 = r3 << r6
            long r7 = (long) r2
            r9 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r7 = r7 & r9
            long r3 = r3 | r7
            android.util.LongSparseLongArray r7 = r0.mMeasureCache
            if (r7 != 0) goto L_0x0051
            android.util.LongSparseLongArray r7 = new android.util.LongSparseLongArray
            r8 = 2
            r7.<init>(r8)
            r0.mMeasureCache = r7
        L_0x0051:
            int r7 = r0.mPrivateFlags
            r8 = 4096(0x1000, float:5.74E-42)
            r7 = r7 & r8
            r11 = 0
            r12 = 1
            if (r7 != r8) goto L_0x005c
            r7 = r12
            goto L_0x005d
        L_0x005c:
            r7 = r11
        L_0x005d:
            int r8 = r0.mOldWidthMeasureSpec
            if (r5 != r8) goto L_0x0068
            int r8 = r0.mOldHeightMeasureSpec
            if (r2 == r8) goto L_0x0066
            goto L_0x0068
        L_0x0066:
            r8 = r11
            goto L_0x0069
        L_0x0068:
            r8 = r12
        L_0x0069:
            int r13 = android.view.View.MeasureSpec.getMode(r5)
            r14 = 1073741824(0x40000000, float:2.0)
            if (r13 != r14) goto L_0x0079
            int r13 = android.view.View.MeasureSpec.getMode(r2)
            if (r13 != r14) goto L_0x0079
            r13 = r12
            goto L_0x007a
        L_0x0079:
            r13 = r11
        L_0x007a:
            int r14 = r20.getMeasuredWidth()
            int r15 = android.view.View.MeasureSpec.getSize(r5)
            if (r14 != r15) goto L_0x0090
            int r14 = r20.getMeasuredHeight()
            int r15 = android.view.View.MeasureSpec.getSize(r2)
            if (r14 != r15) goto L_0x0090
            r14 = r12
            goto L_0x0091
        L_0x0090:
            r14 = r11
        L_0x0091:
            if (r8 == 0) goto L_0x009d
            boolean r15 = sAlwaysRemeasureExactly
            if (r15 != 0) goto L_0x009b
            if (r13 == 0) goto L_0x009b
            if (r14 != 0) goto L_0x009d
        L_0x009b:
            r11 = r12
        L_0x009d:
            if (r7 != 0) goto L_0x00a7
            if (r11 == 0) goto L_0x00a2
            goto L_0x00a7
        L_0x00a2:
            r17 = r7
            r18 = r8
            goto L_0x00f7
        L_0x00a7:
            int r12 = r0.mPrivateFlags
            r12 = r12 & -2049(0xfffffffffffff7ff, float:NaN)
            r0.mPrivateFlags = r12
            r20.resolveRtlPropertiesIfNeeded()
            if (r7 == 0) goto L_0x00b4
            r12 = -1
            goto L_0x00ba
        L_0x00b4:
            android.util.LongSparseLongArray r12 = r0.mMeasureCache
            int r12 = r12.indexOfKey(r3)
        L_0x00ba:
            if (r12 < 0) goto L_0x00dd
            boolean r15 = sIgnoreMeasureCache
            if (r15 == 0) goto L_0x00c5
            r17 = r7
            r18 = r8
            goto L_0x00e1
        L_0x00c5:
            android.util.LongSparseLongArray r15 = r0.mMeasureCache
            long r9 = r15.valueAt(r12)
            r17 = r7
            r18 = r8
            long r7 = r9 >> r6
            int r7 = (int) r7
            int r8 = (int) r9
            r0.setMeasuredDimensionRaw(r7, r8)
            int r7 = r0.mPrivateFlags3
            r7 = r7 | 8
            r0.mPrivateFlags3 = r7
            goto L_0x00ea
        L_0x00dd:
            r17 = r7
            r18 = r8
        L_0x00e1:
            r0.onMeasure(r5, r2)
            int r7 = r0.mPrivateFlags3
            r7 = r7 & -9
            r0.mPrivateFlags3 = r7
        L_0x00ea:
            int r7 = r0.mPrivateFlags
            r8 = 2048(0x800, float:2.87E-42)
            r7 = r7 & r8
            if (r7 != r8) goto L_0x0111
            int r7 = r0.mPrivateFlags
            r7 = r7 | 8192(0x2000, float:1.14794E-41)
            r0.mPrivateFlags = r7
        L_0x00f7:
            r0.mOldWidthMeasureSpec = r5
            r0.mOldHeightMeasureSpec = r2
            android.util.LongSparseLongArray r7 = r0.mMeasureCache
            int r8 = r0.mMeasuredWidth
            long r8 = (long) r8
            long r8 = r8 << r6
            int r6 = r0.mMeasuredHeight
            r19 = r1
            long r0 = (long) r6
            r15 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r15
            long r0 = r0 | r8
            r7.put(r3, r0)
            return
        L_0x0111:
            r19 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r6 = "View with id "
            r1.append(r6)
            int r6 = r20.getId()
            r1.append(r6)
            java.lang.String r6 = ": "
            r1.append(r6)
            java.lang.Class r6 = r20.getClass()
            java.lang.String r6 = r6.getName()
            r1.append(r6)
            java.lang.String r6 = "#onMeasure() did not set the measured dimension by calling setMeasuredDimension()"
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.measure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /* access modifiers changed from: protected */
    public final void setMeasuredDimension(int measuredWidth, int measuredHeight) {
        boolean optical = isLayoutModeOptical(this);
        if (optical != isLayoutModeOptical(this.mParent)) {
            Insets insets = getOpticalInsets();
            int opticalWidth = insets.left + insets.right;
            int opticalHeight = insets.top + insets.bottom;
            measuredWidth += optical ? opticalWidth : -opticalWidth;
            measuredHeight += optical ? opticalHeight : -opticalHeight;
        }
        setMeasuredDimensionRaw(measuredWidth, measuredHeight);
    }

    private void setMeasuredDimensionRaw(int measuredWidth, int measuredHeight) {
        this.mMeasuredWidth = measuredWidth;
        this.mMeasuredHeight = measuredHeight;
        this.mPrivateFlags |= 2048;
    }

    public static int combineMeasuredStates(int curState, int newState) {
        return curState | newState;
    }

    public static int resolveSize(int size, int measureSpec) {
        return resolveSizeAndState(size, measureSpec, 0) & 16777215;
    }

    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode != Integer.MIN_VALUE) {
            if (specMode != 1073741824) {
                result = size;
            } else {
                result = specSize;
            }
        } else if (specSize < size) {
            result = 16777216 | specSize;
        } else {
            result = size;
        }
        return (-16777216 & childMeasuredState) | result;
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode != Integer.MIN_VALUE) {
            if (specMode == 0) {
                return size;
            }
            if (specMode != 1073741824) {
                return result;
            }
        }
        return specSize;
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumHeight() {
        return this.mBackground == null ? this.mMinHeight : Math.max(this.mMinHeight, this.mBackground.getMinimumHeight());
    }

    /* access modifiers changed from: protected */
    public int getSuggestedMinimumWidth() {
        return this.mBackground == null ? this.mMinWidth : Math.max(this.mMinWidth, this.mBackground.getMinimumWidth());
    }

    public int getMinimumHeight() {
        return this.mMinHeight;
    }

    @RemotableViewMethod
    public void setMinimumHeight(int minHeight) {
        this.mMinHeight = minHeight;
        requestLayout();
    }

    public int getMinimumWidth() {
        return this.mMinWidth;
    }

    public void setMinimumWidth(int minWidth) {
        this.mMinWidth = minWidth;
        requestLayout();
    }

    public Animation getAnimation() {
        return this.mCurrentAnimation;
    }

    public void startAnimation(Animation animation) {
        animation.setStartTime(-1);
        setAnimation(animation);
        invalidateParentCaches();
        invalidate(true);
    }

    public void clearAnimation() {
        if (this.mCurrentAnimation != null) {
            this.mCurrentAnimation.detach();
        }
        this.mCurrentAnimation = null;
        invalidateParentIfNeeded();
    }

    public void setAnimation(Animation animation) {
        this.mCurrentAnimation = animation;
        if (animation != null) {
            if (this.mAttachInfo != null && this.mAttachInfo.mDisplayState == 1 && animation.getStartTime() == -1) {
                animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());
            }
            animation.reset();
        }
    }

    /* access modifiers changed from: protected */
    public void onAnimationStart() {
        this.mPrivateFlags |= 65536;
    }

    /* access modifiers changed from: protected */
    public void onAnimationEnd() {
        this.mPrivateFlags &= -65537;
    }

    /* access modifiers changed from: protected */
    public boolean onSetAlpha(int alpha) {
        return false;
    }

    @UnsupportedAppUsage
    public boolean gatherTransparentRegion(Region region) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (!(region == null || attachInfo == null)) {
            if ((this.mPrivateFlags & 128) == 0) {
                int[] location = attachInfo.mTransparentLocation;
                getLocationInWindow(location);
                int shadowOffset = getZ() > 0.0f ? (int) getZ() : 0;
                region.op(location[0] - shadowOffset, location[1] - shadowOffset, ((location[0] + this.mRight) - this.mLeft) + shadowOffset, ((location[1] + this.mBottom) - this.mTop) + (shadowOffset * 3), Region.Op.DIFFERENCE);
            } else {
                if (!(this.mBackground == null || this.mBackground.getOpacity() == -2)) {
                    applyDrawableToTransparentRegion(this.mBackground, region);
                }
                if (!(this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null || this.mForegroundInfo.mDrawable.getOpacity() == -2)) {
                    applyDrawableToTransparentRegion(this.mForegroundInfo.mDrawable, region);
                }
                if (!(this.mDefaultFocusHighlight == null || this.mDefaultFocusHighlight.getOpacity() == -2)) {
                    applyDrawableToTransparentRegion(this.mDefaultFocusHighlight, region);
                }
            }
        }
        return true;
    }

    public void playSoundEffect(int soundConstant) {
        if (this.mAttachInfo != null && this.mAttachInfo.mRootCallbacks != null && isSoundEffectsEnabled()) {
            this.mAttachInfo.mRootCallbacks.playSoundEffect(soundConstant);
        }
    }

    public boolean performHapticFeedback(int feedbackConstant) {
        return performHapticFeedback(feedbackConstant, 0);
    }

    public boolean performHapticFeedback(int feedbackConstant, int flags) {
        boolean z = false;
        if (this.mAttachInfo == null) {
            return false;
        }
        if ((flags & 1) == 0 && !isHapticFeedbackEnabled()) {
            return false;
        }
        AttachInfo.Callbacks callbacks = this.mAttachInfo.mRootCallbacks;
        if ((flags & 2) != 0) {
            z = true;
        }
        return callbacks.performHapticFeedback(feedbackConstant, z);
    }

    public void setSystemUiVisibility(int visibility) {
        if (visibility != this.mSystemUiVisibility) {
            this.mSystemUiVisibility = visibility;
            if (this.mParent != null && this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    public int getSystemUiVisibility() {
        return this.mSystemUiVisibility;
    }

    public int getWindowSystemUiVisibility() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSystemUiVisibility;
        }
        return 0;
    }

    public void onWindowSystemUiVisibilityChanged(int visible) {
    }

    public void dispatchWindowSystemUiVisiblityChanged(int visible) {
        onWindowSystemUiVisibilityChanged(visible);
    }

    public void setOnSystemUiVisibilityChangeListener(OnSystemUiVisibilityChangeListener l) {
        OnSystemUiVisibilityChangeListener unused = getListenerInfo().mOnSystemUiVisibilityChangeListener = l;
        if (this.mParent != null && this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
    }

    public void dispatchSystemUiVisibilityChanged(int visibility) {
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnSystemUiVisibilityChangeListener != null) {
            li.mOnSystemUiVisibilityChangeListener.onSystemUiVisibilityChange(visibility & PUBLIC_STATUS_BAR_VISIBILITY_MASK);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean updateLocalSystemUiVisibility(int localValue, int localChanges) {
        int val = (this.mSystemUiVisibility & (~localChanges)) | (localValue & localChanges);
        if (val == this.mSystemUiVisibility) {
            return false;
        }
        setSystemUiVisibility(val);
        return true;
    }

    @UnsupportedAppUsage
    public void setDisabledSystemUiVisibility(int flags) {
        if (this.mAttachInfo != null && this.mAttachInfo.mDisabledSystemUiVisibility != flags) {
            this.mAttachInfo.mDisabledSystemUiVisibility = flags;
            if (this.mParent != null) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    public static class DragShadowBuilder {
        @UnsupportedAppUsage
        private final WeakReference<View> mView;

        public DragShadowBuilder(View view) {
            this.mView = new WeakReference<>(view);
        }

        public DragShadowBuilder() {
            this.mView = new WeakReference<>((Object) null);
        }

        public final View getView() {
            return (View) this.mView.get();
        }

        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            View view = (View) this.mView.get();
            if (view != null) {
                outShadowSize.set(view.getWidth(), view.getHeight());
                outShadowTouchPoint.set(outShadowSize.x / 2, outShadowSize.y / 2);
                return;
            }
            Log.e(View.VIEW_LOG_TAG, "Asked for drag thumb metrics but no view");
        }

        public void onDrawShadow(Canvas canvas) {
            View view = (View) this.mView.get();
            if (view != null) {
                view.draw(canvas);
            } else {
                Log.e(View.VIEW_LOG_TAG, "Asked to draw drag shadow but no view");
            }
        }
    }

    @Deprecated
    public final boolean startDrag(ClipData data, DragShadowBuilder shadowBuilder, Object myLocalState, int flags) {
        return startDragAndDrop(data, shadowBuilder, myLocalState, flags);
    }

    /* JADX WARNING: Removed duplicated region for block: B:86:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x017a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startDragAndDrop(android.content.ClipData r22, android.view.View.DragShadowBuilder r23, java.lang.Object r24, int r25) {
        /*
            r21 = this;
            r1 = r21
            r12 = r22
            r13 = r23
            android.view.View$AttachInfo r0 = r1.mAttachInfo
            r14 = 0
            if (r0 != 0) goto L_0x0014
            java.lang.String r0 = "View"
            java.lang.String r2 = "startDragAndDrop called on a detached view."
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r2)
            return r14
        L_0x0014:
            android.view.View$AttachInfo r0 = r1.mAttachInfo
            android.view.ViewRootImpl r0 = r0.mViewRootImpl
            android.view.Surface r0 = r0.mSurface
            boolean r0 = r0.isValid()
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "View"
            java.lang.String r2 = "startDragAndDrop called with an invalid surface."
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r2)
            return r14
        L_0x0029:
            r0 = 1
            if (r12 == 0) goto L_0x0039
            r15 = r25
            r2 = r15 & 256(0x100, float:3.59E-43)
            if (r2 == 0) goto L_0x0034
            r2 = r0
            goto L_0x0035
        L_0x0034:
            r2 = r14
        L_0x0035:
            r12.prepareToLeaveProcess(r2)
            goto L_0x003b
        L_0x0039:
            r15 = r25
        L_0x003b:
            android.graphics.Point r2 = new android.graphics.Point
            r2.<init>()
            r11 = r2
            android.graphics.Point r2 = new android.graphics.Point
            r2.<init>()
            r10 = r2
            r13.onProvideShadowMetrics(r11, r10)
            int r2 = r11.x
            if (r2 < 0) goto L_0x018e
            int r2 = r11.y
            if (r2 < 0) goto L_0x018e
            int r2 = r10.x
            if (r2 < 0) goto L_0x018e
            int r2 = r10.y
            if (r2 < 0) goto L_0x018e
            int r2 = r11.x
            if (r2 == 0) goto L_0x0062
            int r2 = r11.y
            if (r2 != 0) goto L_0x006a
        L_0x0062:
            boolean r2 = sAcceptZeroSizeDragShadow
            if (r2 == 0) goto L_0x0181
            r11.x = r0
            r11.y = r0
        L_0x006a:
            android.view.View$AttachInfo r2 = r1.mAttachInfo
            android.view.ViewRootImpl r9 = r2.mViewRootImpl
            android.view.SurfaceSession r2 = new android.view.SurfaceSession
            r2.<init>()
            r8 = r2
            android.view.SurfaceControl$Builder r2 = new android.view.SurfaceControl$Builder
            r2.<init>(r8)
            java.lang.String r3 = "drag surface"
            android.view.SurfaceControl$Builder r2 = r2.setName(r3)
            android.view.SurfaceControl r3 = r9.getSurfaceControl()
            android.view.SurfaceControl$Builder r2 = r2.setParent(r3)
            int r3 = r11.x
            int r4 = r11.y
            android.view.SurfaceControl$Builder r2 = r2.setBufferSize(r3, r4)
            r3 = -3
            android.view.SurfaceControl$Builder r2 = r2.setFormat(r3)
            android.view.SurfaceControl r7 = r2.build()
            android.view.Surface r2 = new android.view.Surface
            r2.<init>()
            r6 = r2
            r6.copyFrom(r7)
            r2 = 0
            r16 = r2
            android.graphics.Canvas r2 = r6.lockCanvas(r2)     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            r5 = r2
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.CLEAR     // Catch:{ all -> 0x0139 }
            r5.drawColor((int) r14, (android.graphics.PorterDuff.Mode) r2)     // Catch:{ all -> 0x0139 }
            r13.onDrawShadow(r5)     // Catch:{ all -> 0x0139 }
            r6.unlockCanvasAndPost(r5)     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            r9.getLastTouchPoint(r11)     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            android.view.View$AttachInfo r2 = r1.mAttachInfo     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            android.view.IWindowSession r2 = r2.mSession     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            android.view.View$AttachInfo r3 = r1.mAttachInfo     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            android.view.IWindow r3 = r3.mWindow     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            int r17 = r9.getLastTouchSource()     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            int r4 = r11.x     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            float r4 = (float) r4     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            int r0 = r11.y     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            float r0 = (float) r0     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            int r14 = r10.x     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            float r14 = (float) r14     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            r19 = r4
            int r4 = r10.y     // Catch:{ Exception -> 0x0159, all -> 0x014c }
            float r4 = (float) r4
            r20 = r4
            r4 = r25
            r12 = r5
            r5 = r7
            r13 = r6
            r6 = r17
            r17 = r7
            r7 = r19
            r19 = r8
            r8 = r0
            r15 = r9
            r9 = r14
            r14 = r10
            r10 = r20
            r20 = r11
            r11 = r22
            android.os.IBinder r0 = r2.performDrag(r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x0135, all -> 0x0131 }
            r2 = r0
            if (r2 == 0) goto L_0x011e
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            android.view.Surface r0 = r0.mDragSurface     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            if (r0 == 0) goto L_0x00ff
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            android.view.Surface r0 = r0.mDragSurface     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            r0.release()     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
        L_0x00ff:
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            r0.mDragSurface = r13     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            android.view.View$AttachInfo r0 = r1.mAttachInfo     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            r0.mDragToken = r2     // Catch:{ Exception -> 0x0118, all -> 0x0111 }
            r3 = r24
            r15.setLocalDragState(r3)     // Catch:{ Exception -> 0x010f, all -> 0x010d }
            goto L_0x0120
        L_0x010d:
            r0 = move-exception
            goto L_0x0114
        L_0x010f:
            r0 = move-exception
            goto L_0x011b
        L_0x0111:
            r0 = move-exception
            r3 = r24
        L_0x0114:
            r16 = r2
            goto L_0x0178
        L_0x0118:
            r0 = move-exception
            r3 = r24
        L_0x011b:
            r16 = r2
            goto L_0x0165
        L_0x011e:
            r3 = r24
        L_0x0120:
            if (r2 == 0) goto L_0x0125
            r18 = 1
            goto L_0x0128
        L_0x0125:
            r18 = 0
        L_0x0128:
            if (r2 != 0) goto L_0x012d
            r13.destroy()
        L_0x012d:
            r19.kill()
            return r18
        L_0x0131:
            r0 = move-exception
            r3 = r24
            goto L_0x0178
        L_0x0135:
            r0 = move-exception
            r3 = r24
            goto L_0x0165
        L_0x0139:
            r0 = move-exception
            r3 = r24
            r12 = r5
            r13 = r6
            r17 = r7
            r19 = r8
            r15 = r9
            r14 = r10
            r20 = r11
            r13.unlockCanvasAndPost(r12)     // Catch:{ Exception -> 0x014a }
            throw r0     // Catch:{ Exception -> 0x014a }
        L_0x014a:
            r0 = move-exception
            goto L_0x0165
        L_0x014c:
            r0 = move-exception
            r3 = r24
            r13 = r6
            r17 = r7
            r19 = r8
            r15 = r9
            r14 = r10
            r20 = r11
            goto L_0x0178
        L_0x0159:
            r0 = move-exception
            r3 = r24
            r13 = r6
            r17 = r7
            r19 = r8
            r15 = r9
            r14 = r10
            r20 = r11
        L_0x0165:
            java.lang.String r2 = "View"
            java.lang.String r4 = "Unable to initiate drag"
            android.util.Log.e(r2, r4, r0)     // Catch:{ all -> 0x0177 }
            if (r16 != 0) goto L_0x0172
            r13.destroy()
        L_0x0172:
            r19.kill()
            r2 = 0
            return r2
        L_0x0177:
            r0 = move-exception
        L_0x0178:
            if (r16 != 0) goto L_0x017d
            r13.destroy()
        L_0x017d:
            r19.kill()
            throw r0
        L_0x0181:
            r3 = r24
            r14 = r10
            r20 = r11
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "Drag shadow dimensions must be positive"
            r0.<init>(r2)
            throw r0
        L_0x018e:
            r3 = r24
            r14 = r10
            r20 = r11
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "Drag shadow dimensions must not be negative"
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.startDragAndDrop(android.content.ClipData, android.view.View$DragShadowBuilder, java.lang.Object, int):boolean");
    }

    public final void cancelDragAndDrop() {
        if (this.mAttachInfo == null) {
            Log.w(VIEW_LOG_TAG, "cancelDragAndDrop called on a detached view.");
        } else if (this.mAttachInfo.mDragToken != null) {
            try {
                this.mAttachInfo.mSession.cancelDragAndDrop(this.mAttachInfo.mDragToken, false);
            } catch (Exception e) {
                Log.e(VIEW_LOG_TAG, "Unable to cancel drag", e);
            }
            this.mAttachInfo.mDragToken = null;
        } else {
            Log.e(VIEW_LOG_TAG, "No active drag to cancel");
        }
    }

    public final void updateDragShadow(DragShadowBuilder shadowBuilder) {
        Canvas canvas;
        if (this.mAttachInfo == null) {
            Log.w(VIEW_LOG_TAG, "updateDragShadow called on a detached view.");
        } else if (this.mAttachInfo.mDragToken != null) {
            try {
                canvas = this.mAttachInfo.mDragSurface.lockCanvas((Rect) null);
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                shadowBuilder.onDrawShadow(canvas);
                this.mAttachInfo.mDragSurface.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                Log.e(VIEW_LOG_TAG, "Unable to update drag shadow", e);
            } catch (Throwable th) {
                this.mAttachInfo.mDragSurface.unlockCanvasAndPost(canvas);
                throw th;
            }
        } else {
            Log.e(VIEW_LOG_TAG, "No active drag");
        }
    }

    public final boolean startMovingTask(float startX, float startY) {
        try {
            return this.mAttachInfo.mSession.startMovingTask(this.mAttachInfo.mWindow, startX, startY);
        } catch (RemoteException e) {
            Log.e(VIEW_LOG_TAG, "Unable to start moving", e);
            return false;
        }
    }

    public void finishMovingTask() {
        try {
            this.mAttachInfo.mSession.finishMovingTask(this.mAttachInfo.mWindow);
        } catch (RemoteException e) {
            Log.e(VIEW_LOG_TAG, "Unable to finish moving", e);
        }
    }

    public boolean onDragEvent(DragEvent event) {
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchDragEnterExitInPreN(DragEvent event) {
        return callDragEventHandler(event);
    }

    public boolean dispatchDragEvent(DragEvent event) {
        event.mEventHandlerWasCalled = true;
        if (event.mAction == 2 || event.mAction == 3) {
            getViewRootImpl().setDragFocus(this, event);
        }
        return callDragEventHandler(event);
    }

    /* access modifiers changed from: package-private */
    public final boolean callDragEventHandler(DragEvent event) {
        boolean result;
        ListenerInfo li = this.mListenerInfo;
        if (li == null || li.mOnDragListener == null || (this.mViewFlags & 32) != 0 || !li.mOnDragListener.onDrag(this, event)) {
            result = onDragEvent(event);
        } else {
            result = true;
        }
        switch (event.mAction) {
            case 4:
                this.mPrivateFlags2 &= -4;
                refreshDrawableState();
                break;
            case 5:
                this.mPrivateFlags2 |= 2;
                refreshDrawableState();
                break;
            case 6:
                this.mPrivateFlags2 &= -3;
                refreshDrawableState();
                break;
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean canAcceptDrag() {
        return (this.mPrivateFlags2 & 1) != 0;
    }

    @UnsupportedAppUsage
    public void onCloseSystemDialogs(String reason) {
    }

    @UnsupportedAppUsage
    public void applyDrawableToTransparentRegion(Drawable dr, Region region) {
        Region r = dr.getTransparentRegion();
        Rect db = dr.getBounds();
        AttachInfo attachInfo = this.mAttachInfo;
        if (r == null || attachInfo == null) {
            region.op(db, Region.Op.DIFFERENCE);
            return;
        }
        int w = getRight() - getLeft();
        int h = getBottom() - getTop();
        if (db.left > 0) {
            r.op(0, 0, db.left, h, Region.Op.UNION);
        }
        if (db.right < w) {
            r.op(db.right, 0, w, h, Region.Op.UNION);
        }
        if (db.top > 0) {
            r.op(0, 0, w, db.top, Region.Op.UNION);
        }
        if (db.bottom < h) {
            r.op(0, db.bottom, w, h, Region.Op.UNION);
        }
        int[] location = attachInfo.mTransparentLocation;
        getLocationInWindow(location);
        r.translate(location[0], location[1]);
        region.op(r, Region.Op.INTERSECT);
    }

    /* access modifiers changed from: private */
    public void checkForLongClick(long delay, float x, float y, int classification) {
        if ((this.mViewFlags & 2097152) == 2097152 || (this.mViewFlags & 1073741824) == 1073741824) {
            this.mHasPerformedLongPress = false;
            if (this.mPendingCheckForLongPress == null) {
                this.mPendingCheckForLongPress = new CheckForLongPress();
            }
            this.mPendingCheckForLongPress.setAnchor(x, y);
            this.mPendingCheckForLongPress.rememberWindowAttachCount();
            this.mPendingCheckForLongPress.rememberPressedState();
            this.mPendingCheckForLongPress.setClassification(classification);
            postDelayed(this.mPendingCheckForLongPress, delay);
        }
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        return LayoutInflater.from(context).inflate(resource, root);
    }

    /* access modifiers changed from: protected */
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        int maxOverScrollX2;
        int maxOverScrollY2;
        int overScrollMode = this.mOverScrollMode;
        boolean canScrollHorizontal = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean canScrollVertical = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean overScrollHorizontal = overScrollMode == 0 || (overScrollMode == 1 && canScrollHorizontal);
        boolean overScrollVertical = overScrollMode == 0 || (overScrollMode == 1 && canScrollVertical);
        int newScrollX = scrollX + deltaX;
        if (!overScrollHorizontal) {
            maxOverScrollX2 = 0;
        } else {
            maxOverScrollX2 = maxOverScrollX;
        }
        int newScrollY = scrollY + deltaY;
        if (!overScrollVertical) {
            maxOverScrollY2 = 0;
        } else {
            maxOverScrollY2 = maxOverScrollY;
        }
        int left = -maxOverScrollX2;
        int right = maxOverScrollX2 + scrollRangeX;
        int i = overScrollMode;
        int overScrollMode2 = -maxOverScrollY2;
        boolean z = canScrollHorizontal;
        int bottom = maxOverScrollY2 + scrollRangeY;
        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }
        boolean clampedX2 = clampedX;
        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < overScrollMode2) {
            newScrollY = overScrollMode2;
            clampedY = true;
        }
        int top = overScrollMode2;
        boolean clampedY2 = clampedY;
        onOverScrolled(newScrollX, newScrollY, clampedX2, clampedY2);
        return clampedX2 || clampedY2;
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    }

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public void setOverScrollMode(int overScrollMode) {
        if (overScrollMode == 0 || overScrollMode == 1 || overScrollMode == 2) {
            this.mOverScrollMode = overScrollMode;
            return;
        }
        throw new IllegalArgumentException("Invalid overscroll mode " + overScrollMode);
    }

    public void setNestedScrollingEnabled(boolean enabled) {
        if (enabled) {
            this.mPrivateFlags3 |= 128;
            return;
        }
        stopNestedScroll();
        this.mPrivateFlags3 &= -129;
    }

    public boolean isNestedScrollingEnabled() {
        return (this.mPrivateFlags3 & 128) == 128;
    }

    public boolean startNestedScroll(int axes) {
        if (hasNestedScrollingParent()) {
            return true;
        }
        if (!isNestedScrollingEnabled()) {
            return false;
        }
        View child = this;
        for (ViewParent p = getParent(); p != null; p = p.getParent()) {
            try {
                if (p.onStartNestedScroll(child, this, axes)) {
                    this.mNestedScrollingParent = p;
                    p.onNestedScrollAccepted(child, this, axes);
                    return true;
                }
            } catch (AbstractMethodError e) {
                Log.e(VIEW_LOG_TAG, "ViewParent " + p + " does not implement interface method onStartNestedScroll", e);
            }
            if (p instanceof View) {
                child = (View) p;
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        if (this.mNestedScrollingParent != null) {
            this.mNestedScrollingParent.onStopNestedScroll(this);
            this.mNestedScrollingParent = null;
        }
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingParent != null;
    }

    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (dxConsumed != 0 || dyConsumed != 0 || dxUnconsumed != 0 || dyUnconsumed != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }
                this.mNestedScrollingParent.onNestedScroll(this, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] = offsetInWindow[0] - startX;
                    offsetInWindow[1] = offsetInWindow[1] - startY;
                }
                return true;
            } else if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (dx != 0 || dy != 0) {
                int startX = 0;
                int startY = 0;
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    startX = offsetInWindow[0];
                    startY = offsetInWindow[1];
                }
                if (consumed == null) {
                    if (this.mTempNestedScrollConsumed == null) {
                        this.mTempNestedScrollConsumed = new int[2];
                    }
                    consumed = this.mTempNestedScrollConsumed;
                }
                consumed[0] = 0;
                consumed[1] = 0;
                this.mNestedScrollingParent.onNestedPreScroll(this, dx, dy, consumed);
                if (offsetInWindow != null) {
                    getLocationInWindow(offsetInWindow);
                    offsetInWindow[0] = offsetInWindow[0] - startX;
                    offsetInWindow[1] = offsetInWindow[1] - startY;
                }
                if (consumed[0] == 0 && consumed[1] == 0) {
                    return false;
                }
                return true;
            } else if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        if (!isNestedScrollingEnabled() || this.mNestedScrollingParent == null) {
            return false;
        }
        return this.mNestedScrollingParent.onNestedFling(this, velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (!isNestedScrollingEnabled() || this.mNestedScrollingParent == null) {
            return false;
        }
        return this.mNestedScrollingParent.onNestedPreFling(this, velocityX, velocityY);
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public float getVerticalScrollFactor() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue outValue = new TypedValue();
            if (this.mContext.getTheme().resolveAttribute(16842829, outValue, true)) {
                this.mVerticalScrollFactor = outValue.getDimension(this.mContext.getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
        }
        return this.mVerticalScrollFactor;
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public float getHorizontalScrollFactor() {
        return getVerticalScrollFactor();
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"), @ViewDebug.IntToString(from = 2, to = "ANY_RTL"), @ViewDebug.IntToString(from = 3, to = "LTR"), @ViewDebug.IntToString(from = 4, to = "RTL"), @ViewDebug.IntToString(from = 5, to = "LOCALE"), @ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"), @ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    @UnsupportedAppUsage
    public int getRawTextDirection() {
        return (this.mPrivateFlags2 & 448) >> 6;
    }

    public void setTextDirection(int textDirection) {
        if (getRawTextDirection() != textDirection) {
            this.mPrivateFlags2 &= -449;
            resetResolvedTextDirection();
            this.mPrivateFlags2 |= (textDirection << 6) & 448;
            resolveTextDirection();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"), @ViewDebug.IntToString(from = 2, to = "ANY_RTL"), @ViewDebug.IntToString(from = 3, to = "LTR"), @ViewDebug.IntToString(from = 4, to = "RTL"), @ViewDebug.IntToString(from = 5, to = "LOCALE"), @ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"), @ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    public int getTextDirection() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_DIRECTION_RESOLVED_MASK) >> 10;
    }

    public boolean resolveTextDirection() {
        int parentResolvedDirection;
        this.mPrivateFlags2 &= -7681;
        if (hasRtlSupport()) {
            int textDirection = getRawTextDirection();
            switch (textDirection) {
                case 0:
                    if (!canResolveTextDirection()) {
                        this.mPrivateFlags2 |= 1024;
                        return false;
                    }
                    try {
                        if (this.mParent.isTextDirectionResolved()) {
                            try {
                                parentResolvedDirection = this.mParent.getTextDirection();
                            } catch (AbstractMethodError e) {
                                Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                                parentResolvedDirection = 3;
                            }
                            switch (parentResolvedDirection) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                    this.mPrivateFlags2 |= parentResolvedDirection << 10;
                                    break;
                                default:
                                    this.mPrivateFlags2 |= 1024;
                                    break;
                            }
                        } else {
                            this.mPrivateFlags2 |= 1024;
                            return false;
                        }
                    } catch (AbstractMethodError e2) {
                        Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                        this.mPrivateFlags2 = this.mPrivateFlags2 | 1536;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    this.mPrivateFlags2 |= textDirection << 10;
                    break;
                default:
                    this.mPrivateFlags2 |= 1024;
                    break;
            }
        } else {
            this.mPrivateFlags2 |= 1024;
        }
        this.mPrivateFlags2 |= 512;
        return true;
    }

    public boolean canResolveTextDirection() {
        if (getRawTextDirection() != 0) {
            return true;
        }
        if (this.mParent == null) {
            return false;
        }
        try {
            return this.mParent.canResolveTextDirection();
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            return false;
        }
    }

    public void resetResolvedTextDirection() {
        this.mPrivateFlags2 &= -7681;
        this.mPrivateFlags2 |= 1024;
    }

    public boolean isTextDirectionInherited() {
        return getRawTextDirection() == 0;
    }

    public boolean isTextDirectionResolved() {
        return (this.mPrivateFlags2 & 512) == 512;
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "GRAVITY"), @ViewDebug.IntToString(from = 2, to = "TEXT_START"), @ViewDebug.IntToString(from = 3, to = "TEXT_END"), @ViewDebug.IntToString(from = 4, to = "CENTER"), @ViewDebug.IntToString(from = 5, to = "VIEW_START"), @ViewDebug.IntToString(from = 6, to = "VIEW_END")})
    @UnsupportedAppUsage
    public int getRawTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_MASK) >> 13;
    }

    public void setTextAlignment(int textAlignment) {
        if (textAlignment != getRawTextAlignment()) {
            this.mPrivateFlags2 &= -57345;
            resetResolvedTextAlignment();
            this.mPrivateFlags2 |= (textAlignment << 13) & PFLAG2_TEXT_ALIGNMENT_MASK;
            resolveTextAlignment();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "INHERIT"), @ViewDebug.IntToString(from = 1, to = "GRAVITY"), @ViewDebug.IntToString(from = 2, to = "TEXT_START"), @ViewDebug.IntToString(from = 3, to = "TEXT_END"), @ViewDebug.IntToString(from = 4, to = "CENTER"), @ViewDebug.IntToString(from = 5, to = "VIEW_START"), @ViewDebug.IntToString(from = 6, to = "VIEW_END")})
    public int getTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK) >> 17;
    }

    public boolean resolveTextAlignment() {
        int parentResolvedTextAlignment;
        this.mPrivateFlags2 &= -983041;
        if (hasRtlSupport()) {
            int textAlignment = getRawTextAlignment();
            switch (textAlignment) {
                case 0:
                    if (!canResolveTextAlignment()) {
                        this.mPrivateFlags2 |= 131072;
                        return false;
                    }
                    try {
                        if (this.mParent.isTextAlignmentResolved()) {
                            try {
                                parentResolvedTextAlignment = this.mParent.getTextAlignment();
                            } catch (AbstractMethodError e) {
                                Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                                parentResolvedTextAlignment = 1;
                            }
                            switch (parentResolvedTextAlignment) {
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                    this.mPrivateFlags2 |= parentResolvedTextAlignment << 17;
                                    break;
                                default:
                                    this.mPrivateFlags2 = 131072 | this.mPrivateFlags2;
                                    break;
                            }
                        } else {
                            this.mPrivateFlags2 = 131072 | this.mPrivateFlags2;
                            return false;
                        }
                    } catch (AbstractMethodError e2) {
                        Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                        this.mPrivateFlags2 = this.mPrivateFlags2 | 196608;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.mPrivateFlags2 |= textAlignment << 17;
                    break;
                default:
                    this.mPrivateFlags2 = 131072 | this.mPrivateFlags2;
                    break;
            }
        } else {
            this.mPrivateFlags2 |= 131072;
        }
        this.mPrivateFlags2 |= 65536;
        return true;
    }

    public boolean canResolveTextAlignment() {
        if (getRawTextAlignment() != 0) {
            return true;
        }
        if (this.mParent == null) {
            return false;
        }
        try {
            return this.mParent.canResolveTextAlignment();
        } catch (AbstractMethodError e) {
            Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
            return false;
        }
    }

    public void resetResolvedTextAlignment() {
        this.mPrivateFlags2 &= -983041;
        this.mPrivateFlags2 |= 131072;
    }

    public boolean isTextAlignmentInherited() {
        return getRawTextAlignment() == 0;
    }

    public boolean isTextAlignmentResolved() {
        return (this.mPrivateFlags2 & 65536) == 65536;
    }

    public static int generateViewId() {
        int result;
        int newValue;
        do {
            result = sNextGeneratedId.get();
            newValue = result + 1;
            if (newValue > 16777215) {
                newValue = 1;
            }
        } while (!sNextGeneratedId.compareAndSet(result, newValue));
        return result;
    }

    private static boolean isViewIdGenerated(int id) {
        return (-16777216 & id) == 0 && (16777215 & id) != 0;
    }

    public void captureTransitioningViews(List<View> transitioningViews) {
        if (getVisibility() == 0) {
            transitioningViews.add(this);
        }
    }

    public void findNamedViews(Map<String, View> namedElements) {
        String transitionName;
        if ((getVisibility() == 0 || this.mGhostView != null) && (transitionName = getTransitionName()) != null) {
            namedElements.put(transitionName, this);
        }
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        if (isDraggingScrollBar() || isOnScrollbarThumb(x, y)) {
            return PointerIcon.getSystemIcon(this.mContext, 1000);
        }
        return this.mPointerIcon;
    }

    public void setPointerIcon(PointerIcon pointerIcon) {
        this.mPointerIcon = pointerIcon;
        if (this.mAttachInfo != null && !this.mAttachInfo.mHandlingPointerEvent) {
            try {
                this.mAttachInfo.mSession.updatePointerIcon(this.mAttachInfo.mWindow);
            } catch (RemoteException e) {
            }
        }
    }

    public PointerIcon getPointerIcon() {
        return this.mPointerIcon;
    }

    public boolean hasPointerCapture() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.hasPointerCapture();
    }

    public void requestPointerCapture() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(true);
        }
    }

    public void releasePointerCapture() {
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(false);
        }
    }

    public void onPointerCaptureChange(boolean hasCapture) {
    }

    public void dispatchPointerCaptureChanged(boolean hasCapture) {
        onPointerCaptureChange(hasCapture);
    }

    public boolean onCapturedPointerEvent(MotionEvent event) {
        return false;
    }

    public void setOnCapturedPointerListener(OnCapturedPointerListener l) {
        getListenerInfo().mOnCapturedPointerListener = l;
    }

    public static class MeasureSpec {
        public static final int AT_MOST = Integer.MIN_VALUE;
        public static final int EXACTLY = 1073741824;
        private static final int MODE_MASK = -1073741824;
        private static final int MODE_SHIFT = 30;
        public static final int UNSPECIFIED = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface MeasureSpecMode {
        }

        public static int makeMeasureSpec(int size, int mode) {
            if (View.sUseBrokenMakeMeasureSpec) {
                return size + mode;
            }
            return (1073741823 & size) | (-1073741824 & mode);
        }

        @UnsupportedAppUsage
        public static int makeSafeMeasureSpec(int size, int mode) {
            if (!View.sUseZeroUnspecifiedMeasureSpec || mode != 0) {
                return makeMeasureSpec(size, mode);
            }
            return 0;
        }

        public static int getMode(int measureSpec) {
            return -1073741824 & measureSpec;
        }

        public static int getSize(int measureSpec) {
            return 1073741823 & measureSpec;
        }

        static int adjust(int measureSpec, int delta) {
            int mode = getMode(measureSpec);
            int size = getSize(measureSpec);
            if (mode == 0) {
                return makeMeasureSpec(size, 0);
            }
            int size2 = size + delta;
            if (size2 < 0) {
                Log.e(View.VIEW_LOG_TAG, "MeasureSpec.adjust: new size would be negative! (" + size2 + ") spec: " + toString(measureSpec) + " delta: " + delta);
                size2 = 0;
            }
            return makeMeasureSpec(size2, mode);
        }

        public static String toString(int measureSpec) {
            int mode = getMode(measureSpec);
            int size = getSize(measureSpec);
            StringBuilder sb = new StringBuilder("MeasureSpec: ");
            if (mode == 0) {
                sb.append("UNSPECIFIED ");
            } else if (mode == 1073741824) {
                sb.append("EXACTLY ");
            } else if (mode == Integer.MIN_VALUE) {
                sb.append("AT_MOST ");
            } else {
                sb.append(mode);
                sb.append(WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
            }
            sb.append(size);
            return sb.toString();
        }
    }

    private final class CheckForLongPress implements Runnable {
        private int mClassification;
        private boolean mOriginalPressedState;
        private int mOriginalWindowAttachCount;
        private float mX;
        private float mY;

        private CheckForLongPress() {
        }

        public void run() {
            if (this.mOriginalPressedState == View.this.isPressed() && View.this.mParent != null && this.mOriginalWindowAttachCount == View.this.mWindowAttachCount) {
                View.this.recordGestureClassification(this.mClassification);
                if (View.this.performLongClick(this.mX, this.mY)) {
                    boolean unused = View.this.mHasPerformedLongPress = true;
                }
            }
        }

        public void setAnchor(float x, float y) {
            this.mX = x;
            this.mY = y;
        }

        public void rememberWindowAttachCount() {
            this.mOriginalWindowAttachCount = View.this.mWindowAttachCount;
        }

        public void rememberPressedState() {
            this.mOriginalPressedState = View.this.isPressed();
        }

        public void setClassification(int classification) {
            this.mClassification = classification;
        }
    }

    private final class CheckForTap implements Runnable {
        public float x;
        public float y;

        private CheckForTap() {
        }

        public void run() {
            View.this.mPrivateFlags &= -33554433;
            View.this.setPressed(true, this.x, this.y);
            View.this.checkForLongClick((long) (ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout()), this.x, this.y, 3);
        }
    }

    private final class PerformClick implements Runnable {
        private PerformClick() {
        }

        public void run() {
            View.this.recordGestureClassification(1);
            boolean unused = View.this.performClickInternal();
        }
    }

    /* access modifiers changed from: private */
    public void recordGestureClassification(int classification) {
        if (classification != 0) {
            StatsLog.write(177, getClass().getName(), classification);
        }
    }

    public ViewPropertyAnimator animate() {
        if (this.mAnimator == null) {
            this.mAnimator = new ViewPropertyAnimator(this);
        }
        return this.mAnimator;
    }

    public final void setTransitionName(String transitionName) {
        this.mTransitionName = transitionName;
    }

    @ViewDebug.ExportedProperty
    public String getTransitionName() {
        return this.mTransitionName;
    }

    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int deviceId) {
    }

    private final class UnsetPressedState implements Runnable {
        private UnsetPressedState() {
        }

        public void run() {
            View.this.setPressed(false);
        }
    }

    private static class VisibilityChangeForAutofillHandler extends Handler {
        private final AutofillManager mAfm;
        private final View mView;

        private VisibilityChangeForAutofillHandler(AutofillManager afm, View view) {
            this.mAfm = afm;
            this.mView = view;
        }

        public void handleMessage(Message msg) {
            this.mAfm.notifyViewVisibilityChanged(this.mView, this.mView.isShown());
        }
    }

    public static class BaseSavedState extends AbsSavedState {
        static final int AUTOFILL_ID = 4;
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.ClassLoaderCreator<BaseSavedState>() {
            public BaseSavedState createFromParcel(Parcel in) {
                return new BaseSavedState(in);
            }

            public BaseSavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new BaseSavedState(in, loader);
            }

            public BaseSavedState[] newArray(int size) {
                return new BaseSavedState[size];
            }
        };
        static final int IS_AUTOFILLED = 2;
        static final int START_ACTIVITY_REQUESTED_WHO_SAVED = 1;
        int mAutofillViewId;
        boolean mIsAutofilled;
        int mSavedData;
        String mStartActivityRequestWhoSaved;

        public BaseSavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public BaseSavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.mSavedData = source.readInt();
            this.mStartActivityRequestWhoSaved = source.readString();
            this.mIsAutofilled = source.readBoolean();
            this.mAutofillViewId = source.readInt();
        }

        public BaseSavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mSavedData);
            out.writeString(this.mStartActivityRequestWhoSaved);
            out.writeBoolean(this.mIsAutofilled);
            out.writeInt(this.mAutofillViewId);
        }
    }

    static final class AttachInfo {
        int mAccessibilityFetchFlags;
        Drawable mAccessibilityFocusDrawable;
        int mAccessibilityWindowId = -1;
        boolean mAlwaysConsumeSystemBars;
        @UnsupportedAppUsage
        float mApplicationScale;
        Drawable mAutofilledDrawable;
        Canvas mCanvas;
        @UnsupportedAppUsage
        final Rect mContentInsets = new Rect();
        boolean mDebugLayout = DisplayProperties.debug_layout().orElse(false).booleanValue();
        int mDisabledSystemUiVisibility;
        Display mDisplay;
        final DisplayCutout.ParcelableWrapper mDisplayCutout = new DisplayCutout.ParcelableWrapper(DisplayCutout.NO_CUTOUT);
        @UnsupportedAppUsage
        int mDisplayState = 0;
        public Surface mDragSurface;
        IBinder mDragToken;
        @UnsupportedAppUsage
        long mDrawingTime;
        boolean mForceReportNewAttributes;
        @UnsupportedAppUsage
        final ViewTreeObserver.InternalInsetsInfo mGivenInternalInsets = new ViewTreeObserver.InternalInsetsInfo();
        int mGlobalSystemUiVisibility = -1;
        @UnsupportedAppUsage
        final Handler mHandler;
        boolean mHandlingPointerEvent;
        boolean mHardwareAccelerated;
        boolean mHardwareAccelerationRequested;
        boolean mHasNonEmptyGivenInternalInsets;
        boolean mHasSystemUiListeners;
        @UnsupportedAppUsage
        boolean mHasWindowFocus;
        IWindowId mIWindowId;
        @UnsupportedAppUsage
        boolean mInTouchMode;
        final int[] mInvalidateChildLocation = new int[2];
        @UnsupportedAppUsage
        boolean mKeepScreenOn;
        @UnsupportedAppUsage
        final KeyEvent.DispatcherState mKeyDispatchState = new KeyEvent.DispatcherState();
        boolean mNeedsUpdateLightCenter;
        final Rect mOutsets = new Rect();
        final Rect mOverscanInsets = new Rect();
        boolean mOverscanRequested;
        IBinder mPanelParentWindowToken;
        List<RenderNode> mPendingAnimatingRenderNodes;
        final Point mPoint = new Point();
        @UnsupportedAppUsage
        boolean mRecomputeGlobalAttributes;
        final Callbacks mRootCallbacks;
        View mRootView;
        @UnsupportedAppUsage
        boolean mScalingRequired;
        @UnsupportedAppUsage
        final ArrayList<View> mScrollContainers = new ArrayList<>();
        @UnsupportedAppUsage
        final IWindowSession mSession;
        @UnsupportedAppUsage
        final Rect mStableInsets = new Rect();
        int mSystemUiVisibility;
        final ArrayList<View> mTempArrayList = new ArrayList<>(24);
        ThreadedRenderer mThreadedRenderer;
        final Rect mTmpInvalRect = new Rect();
        final int[] mTmpLocation = new int[2];
        final Matrix mTmpMatrix = new Matrix();
        final Outline mTmpOutline = new Outline();
        final List<RectF> mTmpRectList = new ArrayList();
        final float[] mTmpTransformLocation = new float[2];
        final RectF mTmpTransformRect = new RectF();
        final RectF mTmpTransformRect1 = new RectF();
        final Transformation mTmpTransformation = new Transformation();
        View mTooltipHost;
        final int[] mTransparentLocation = new int[2];
        @UnsupportedAppUsage
        final ViewTreeObserver mTreeObserver;
        boolean mUnbufferedDispatchRequested;
        boolean mUse32BitDrawingCache;
        View mViewRequestingLayout;
        final ViewRootImpl mViewRootImpl;
        @UnsupportedAppUsage
        boolean mViewScrollChanged;
        @UnsupportedAppUsage
        boolean mViewVisibilityChanged;
        @UnsupportedAppUsage
        final Rect mVisibleInsets = new Rect();
        @UnsupportedAppUsage
        final IWindow mWindow;
        WindowId mWindowId;
        int mWindowLeft;
        final IBinder mWindowToken;
        int mWindowTop;
        int mWindowVisibility;

        interface Callbacks {
            boolean performHapticFeedback(int i, boolean z);

            void playSoundEffect(int i);
        }

        static class InvalidateInfo {
            private static final int POOL_LIMIT = 10;
            private static final Pools.SynchronizedPool<InvalidateInfo> sPool = new Pools.SynchronizedPool<>(10);
            @UnsupportedAppUsage
            int bottom;
            @UnsupportedAppUsage
            int left;
            @UnsupportedAppUsage
            int right;
            @UnsupportedAppUsage
            View target;
            @UnsupportedAppUsage
            int top;

            InvalidateInfo() {
            }

            public static InvalidateInfo obtain() {
                InvalidateInfo instance = sPool.acquire();
                return instance != null ? instance : new InvalidateInfo();
            }

            public void recycle() {
                this.target = null;
                sPool.release(this);
            }
        }

        AttachInfo(IWindowSession session, IWindow window, Display display, ViewRootImpl viewRootImpl, Handler handler, Callbacks effectPlayer, Context context) {
            this.mSession = session;
            this.mWindow = window;
            this.mWindowToken = window.asBinder();
            this.mDisplay = display;
            this.mViewRootImpl = viewRootImpl;
            this.mHandler = handler;
            this.mRootCallbacks = effectPlayer;
            this.mTreeObserver = new ViewTreeObserver(context);
        }
    }

    private static class ScrollabilityCache implements Runnable {
        public static final int DRAGGING_HORIZONTAL_SCROLL_BAR = 2;
        public static final int DRAGGING_VERTICAL_SCROLL_BAR = 1;
        public static final int FADING = 2;
        public static final int NOT_DRAGGING = 0;
        public static final int OFF = 0;
        public static final int ON = 1;
        private static final float[] OPAQUE = {255.0f};
        private static final float[] TRANSPARENT = {0.0f};
        public boolean fadeScrollBars;
        public long fadeStartTime;
        public int fadingEdgeLength;
        @UnsupportedAppUsage
        public View host;
        public float[] interpolatorValues;
        private int mLastColor;
        public final Rect mScrollBarBounds = new Rect();
        public float mScrollBarDraggingPos = 0.0f;
        public int mScrollBarDraggingState = 0;
        public final Rect mScrollBarTouchBounds = new Rect();
        public final Matrix matrix;
        public final Paint paint;
        @UnsupportedAppUsage
        public ScrollBarDrawable scrollBar;
        public int scrollBarDefaultDelayBeforeFade;
        public int scrollBarFadeDuration;
        public final Interpolator scrollBarInterpolator = new Interpolator(1, 2);
        public int scrollBarMinTouchTarget;
        public int scrollBarSize;
        public Shader shader;
        @UnsupportedAppUsage
        public int state = 0;

        public ScrollabilityCache(ViewConfiguration configuration, View host2) {
            this.fadingEdgeLength = configuration.getScaledFadingEdgeLength();
            this.scrollBarSize = configuration.getScaledScrollBarSize();
            this.scrollBarMinTouchTarget = configuration.getScaledMinScrollbarTouchTarget();
            this.scrollBarDefaultDelayBeforeFade = ViewConfiguration.getScrollDefaultDelay();
            this.scrollBarFadeDuration = ViewConfiguration.getScrollBarFadeDuration();
            this.paint = new Paint();
            this.matrix = new Matrix();
            this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
            this.paint.setShader(this.shader);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.host = host2;
        }

        public void setFadeColor(int color) {
            if (color != this.mLastColor) {
                this.mLastColor = color;
                if (color != 0) {
                    this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, color | -16777216, color & 16777215, Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode((Xfermode) null);
                    return;
                }
                this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
                this.paint.setShader(this.shader);
                this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            }
        }

        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            if (now >= this.fadeStartTime) {
                int nextFrame = (int) now;
                Interpolator interpolator = this.scrollBarInterpolator;
                interpolator.setKeyFrame(0, nextFrame, OPAQUE);
                interpolator.setKeyFrame(0 + 1, nextFrame + this.scrollBarFadeDuration, TRANSPARENT);
                this.state = 2;
                this.host.invalidate(true);
            }
        }
    }

    private class SendViewScrolledAccessibilityEvent implements Runnable {
        public int mDeltaX;
        public int mDeltaY;
        public volatile boolean mIsPending;

        private SendViewScrolledAccessibilityEvent() {
        }

        public void post(int dx, int dy) {
            this.mDeltaX += dx;
            this.mDeltaY += dy;
            if (!this.mIsPending) {
                this.mIsPending = true;
                View.this.postDelayed(this, ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
            }
        }

        public void run() {
            if (AccessibilityManager.getInstance(View.this.mContext).isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(4096);
                event.setScrollDeltaX(this.mDeltaX);
                event.setScrollDeltaY(this.mDeltaY);
                View.this.sendAccessibilityEventUnchecked(event);
            }
            reset();
        }

        /* access modifiers changed from: private */
        public void reset() {
            this.mIsPending = false;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    @UnsupportedAppUsage
    private void cancel(SendViewScrolledAccessibilityEvent callback) {
        if (callback != null && callback.mIsPending) {
            removeCallbacks(callback);
            callback.reset();
        }
    }

    public static class AccessibilityDelegate {
        public void sendAccessibilityEvent(View host, int eventType) {
            host.sendAccessibilityEventInternal(eventType);
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            return host.performAccessibilityActionInternal(action, args);
        }

        public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
            host.sendAccessibilityEventUncheckedInternal(event);
        }

        public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            return host.dispatchPopulateAccessibilityEventInternal(event);
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            host.onPopulateAccessibilityEventInternal(event);
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            host.onInitializeAccessibilityEventInternal(event);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            host.onInitializeAccessibilityNodeInfoInternal(info);
        }

        public void addExtraDataToAccessibilityNodeInfo(View host, AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
            host.addExtraDataToAccessibilityNodeInfo(info, extraDataKey, arguments);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
            return host.onRequestSendAccessibilityEventInternal(child, event);
        }

        public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
            return null;
        }

        @UnsupportedAppUsage
        public AccessibilityNodeInfo createAccessibilityNodeInfo(View host) {
            return host.createAccessibilityNodeInfoInternal();
        }
    }

    private static class MatchIdPredicate implements Predicate<View> {
        public int mId;

        private MatchIdPredicate() {
        }

        public boolean test(View view) {
            return view.mID == this.mId;
        }
    }

    private static class MatchLabelForPredicate implements Predicate<View> {
        /* access modifiers changed from: private */
        public int mLabeledId;

        private MatchLabelForPredicate() {
        }

        public boolean test(View view) {
            return view.mLabelForId == this.mLabeledId;
        }
    }

    private static void dumpFlags() {
        HashMap<String, String> found = Maps.newHashMap();
        try {
            for (Field field : View.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    if (field.getType().equals(Integer.TYPE)) {
                        dumpFlag(found, field.getName(), field.getInt((Object) null));
                    } else if (field.getType().equals(int[].class)) {
                        int[] values = (int[]) field.get((Object) null);
                        for (int i = 0; i < values.length; i++) {
                            dumpFlag(found, field.getName() + "[" + i + "]", values[i]);
                        }
                    }
                }
            }
            ArrayList<String> keys = Lists.newArrayList();
            keys.addAll(found.keySet());
            Collections.sort(keys);
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                Log.d(VIEW_LOG_TAG, found.get(it.next()));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void dumpFlag(HashMap<String, String> found, String name, int value) {
        String bits = String.format("%32s", new Object[]{Integer.toBinaryString(value)}).replace('0', ' ');
        int prefix = name.indexOf(95);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix > 0 ? name.substring(0, prefix) : name);
        sb.append(bits);
        sb.append(name);
        String key = sb.toString();
        found.put(key, bits + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + name);
    }

    public void encode(ViewHierarchyEncoder stream) {
        stream.beginObject(this);
        encodeProperties(stream);
        stream.endObject();
    }

    /* access modifiers changed from: protected */
    public void encodeProperties(ViewHierarchyEncoder stream) {
        Object resolveId = ViewDebug.resolveId(getContext(), this.mID);
        if (resolveId instanceof String) {
            stream.addProperty("id", (String) resolveId);
        } else {
            stream.addProperty("id", this.mID);
        }
        stream.addProperty("misc:transformation.alpha", this.mTransformationInfo != null ? this.mTransformationInfo.mAlpha : 0.0f);
        stream.addProperty("misc:transitionName", getTransitionName());
        stream.addProperty("layout:left", this.mLeft);
        stream.addProperty("layout:right", this.mRight);
        stream.addProperty("layout:top", this.mTop);
        stream.addProperty("layout:bottom", this.mBottom);
        stream.addProperty("layout:width", getWidth());
        stream.addProperty("layout:height", getHeight());
        stream.addProperty("layout:layoutDirection", getLayoutDirection());
        stream.addProperty("layout:layoutRtl", isLayoutRtl());
        stream.addProperty("layout:hasTransientState", hasTransientState());
        stream.addProperty("layout:baseline", getBaseline());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            stream.addPropertyKey("layoutParams");
            layoutParams.encode(stream);
        }
        stream.addProperty("scrolling:scrollX", this.mScrollX);
        stream.addProperty("scrolling:scrollY", this.mScrollY);
        stream.addProperty("padding:paddingLeft", this.mPaddingLeft);
        stream.addProperty("padding:paddingRight", this.mPaddingRight);
        stream.addProperty("padding:paddingTop", this.mPaddingTop);
        stream.addProperty("padding:paddingBottom", this.mPaddingBottom);
        stream.addProperty("padding:userPaddingRight", this.mUserPaddingRight);
        stream.addProperty("padding:userPaddingLeft", this.mUserPaddingLeft);
        stream.addProperty("padding:userPaddingBottom", this.mUserPaddingBottom);
        stream.addProperty("padding:userPaddingStart", this.mUserPaddingStart);
        stream.addProperty("padding:userPaddingEnd", this.mUserPaddingEnd);
        stream.addProperty("measurement:minHeight", this.mMinHeight);
        stream.addProperty("measurement:minWidth", this.mMinWidth);
        stream.addProperty("measurement:measuredWidth", this.mMeasuredWidth);
        stream.addProperty("measurement:measuredHeight", this.mMeasuredHeight);
        stream.addProperty("drawing:elevation", getElevation());
        stream.addProperty("drawing:translationX", getTranslationX());
        stream.addProperty("drawing:translationY", getTranslationY());
        stream.addProperty("drawing:translationZ", getTranslationZ());
        stream.addProperty("drawing:rotation", getRotation());
        stream.addProperty("drawing:rotationX", getRotationX());
        stream.addProperty("drawing:rotationY", getRotationY());
        stream.addProperty("drawing:scaleX", getScaleX());
        stream.addProperty("drawing:scaleY", getScaleY());
        stream.addProperty("drawing:pivotX", getPivotX());
        stream.addProperty("drawing:pivotY", getPivotY());
        stream.addProperty("drawing:clipBounds", this.mClipBounds == null ? null : this.mClipBounds.toString());
        stream.addProperty("drawing:opaque", isOpaque());
        stream.addProperty("drawing:alpha", getAlpha());
        stream.addProperty("drawing:transitionAlpha", getTransitionAlpha());
        stream.addProperty("drawing:shadow", hasShadow());
        stream.addProperty("drawing:solidColor", getSolidColor());
        stream.addProperty("drawing:layerType", this.mLayerType);
        stream.addProperty("drawing:willNotDraw", willNotDraw());
        stream.addProperty("drawing:hardwareAccelerated", isHardwareAccelerated());
        stream.addProperty("drawing:willNotCacheDrawing", willNotCacheDrawing());
        stream.addProperty("drawing:drawingCacheEnabled", isDrawingCacheEnabled());
        stream.addProperty("drawing:overlappingRendering", hasOverlappingRendering());
        stream.addProperty("drawing:outlineAmbientShadowColor", getOutlineAmbientShadowColor());
        stream.addProperty("drawing:outlineSpotShadowColor", getOutlineSpotShadowColor());
        stream.addProperty("focus:hasFocus", hasFocus());
        stream.addProperty("focus:isFocused", isFocused());
        stream.addProperty("focus:focusable", getFocusable());
        stream.addProperty("focus:isFocusable", isFocusable());
        stream.addProperty("focus:isFocusableInTouchMode", isFocusableInTouchMode());
        stream.addProperty("misc:clickable", isClickable());
        stream.addProperty("misc:pressed", isPressed());
        stream.addProperty("misc:selected", isSelected());
        stream.addProperty("misc:touchMode", isInTouchMode());
        stream.addProperty("misc:hovered", isHovered());
        stream.addProperty("misc:activated", isActivated());
        stream.addProperty("misc:visibility", getVisibility());
        stream.addProperty("misc:fitsSystemWindows", getFitsSystemWindows());
        stream.addProperty("misc:filterTouchesWhenObscured", getFilterTouchesWhenObscured());
        stream.addProperty("misc:enabled", isEnabled());
        stream.addProperty("misc:soundEffectsEnabled", isSoundEffectsEnabled());
        stream.addProperty("misc:hapticFeedbackEnabled", isHapticFeedbackEnabled());
        Resources.Theme theme = getContext().getTheme();
        if (theme != null) {
            stream.addPropertyKey("theme");
            theme.encode(stream);
        }
        int n = this.mAttributes != null ? this.mAttributes.length : 0;
        stream.addProperty("meta:__attrCount__", n / 2);
        for (int i = 0; i < n; i += 2) {
            stream.addProperty("meta:__attr__" + this.mAttributes[i], this.mAttributes[i + 1]);
        }
        stream.addProperty("misc:scrollBarStyle", getScrollBarStyle());
        stream.addProperty("text:textDirection", getTextDirection());
        stream.addProperty("text:textAlignment", getTextAlignment());
        CharSequence contentDescription = getContentDescription();
        stream.addProperty("accessibility:contentDescription", contentDescription == null ? "" : contentDescription.toString());
        stream.addProperty("accessibility:labelFor", getLabelFor());
        stream.addProperty("accessibility:importantForAccessibility", getImportantForAccessibility());
    }

    /* access modifiers changed from: package-private */
    public boolean shouldDrawRoundScrollbar() {
        if (!this.mResources.getConfiguration().isScreenRound() || this.mAttachInfo == null) {
            return false;
        }
        View rootView = getRootView();
        WindowInsets insets = getRootWindowInsets();
        int height = getHeight();
        int width = getWidth();
        int displayHeight = rootView.getHeight();
        int displayWidth = rootView.getWidth();
        if (height != displayHeight || width != displayWidth) {
            return false;
        }
        getLocationInWindow(this.mAttachInfo.mTmpLocation);
        if (this.mAttachInfo.mTmpLocation[0] == insets.getStableInsetLeft() && this.mAttachInfo.mTmpLocation[1] == insets.getStableInsetTop()) {
            return true;
        }
        return false;
    }

    public void setTooltipText(CharSequence tooltipText) {
        if (TextUtils.isEmpty(tooltipText)) {
            setFlags(0, 1073741824);
            hideTooltip();
            this.mTooltipInfo = null;
            return;
        }
        setFlags(1073741824, 1073741824);
        if (this.mTooltipInfo == null) {
            this.mTooltipInfo = new TooltipInfo();
            this.mTooltipInfo.mShowTooltipRunnable = new Runnable() {
                public final void run() {
                    boolean unused = View.this.showHoverTooltip();
                }
            };
            this.mTooltipInfo.mHideTooltipRunnable = new Runnable() {
                public final void run() {
                    View.this.hideTooltip();
                }
            };
            this.mTooltipInfo.mHoverSlop = ViewConfiguration.get(this.mContext).getScaledHoverSlop();
            this.mTooltipInfo.clearAnchorPos();
        }
        this.mTooltipInfo.mTooltipText = tooltipText;
    }

    @UnsupportedAppUsage
    public void setTooltip(CharSequence tooltipText) {
        setTooltipText(tooltipText);
    }

    public CharSequence getTooltipText() {
        if (this.mTooltipInfo != null) {
            return this.mTooltipInfo.mTooltipText;
        }
        return null;
    }

    public CharSequence getTooltip() {
        return getTooltipText();
    }

    private boolean showTooltip(int x, int y, boolean fromLongClick) {
        if (this.mAttachInfo == null || this.mTooltipInfo == null) {
            return false;
        }
        if ((fromLongClick && (this.mViewFlags & 32) != 0) || TextUtils.isEmpty(this.mTooltipInfo.mTooltipText)) {
            return false;
        }
        hideTooltip();
        this.mTooltipInfo.mTooltipFromLongClick = fromLongClick;
        this.mTooltipInfo.mTooltipPopup = new TooltipPopup(getContext());
        this.mTooltipInfo.mTooltipPopup.show(this, x, y, (this.mPrivateFlags3 & 131072) == 131072, this.mTooltipInfo.mTooltipText);
        this.mAttachInfo.mTooltipHost = this;
        notifyViewAccessibilityStateChangedIfNeeded(0);
        return true;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void hideTooltip() {
        if (this.mTooltipInfo != null) {
            removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
            if (this.mTooltipInfo.mTooltipPopup != null) {
                this.mTooltipInfo.mTooltipPopup.hide();
                this.mTooltipInfo.mTooltipPopup = null;
                this.mTooltipInfo.mTooltipFromLongClick = false;
                this.mTooltipInfo.clearAnchorPos();
                if (this.mAttachInfo != null) {
                    this.mAttachInfo.mTooltipHost = null;
                }
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    private boolean showLongClickTooltip(int x, int y) {
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        return showTooltip(x, y, true);
    }

    /* access modifiers changed from: private */
    public boolean showHoverTooltip() {
        return showTooltip(this.mTooltipInfo.mAnchorX, this.mTooltipInfo.mAnchorY, false);
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchTooltipHoverEvent(MotionEvent event) {
        int timeout;
        if (this.mTooltipInfo == null) {
            return false;
        }
        int action = event.getAction();
        if (action != 7) {
            if (action == 10) {
                this.mTooltipInfo.clearAnchorPos();
                if (!this.mTooltipInfo.mTooltipFromLongClick) {
                    hideTooltip();
                }
            }
        } else if ((this.mViewFlags & 1073741824) == 1073741824) {
            if (!this.mTooltipInfo.mTooltipFromLongClick && this.mTooltipInfo.updateAnchorPos(event)) {
                if (this.mTooltipInfo.mTooltipPopup == null) {
                    removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
                    postDelayed(this.mTooltipInfo.mShowTooltipRunnable, (long) ViewConfiguration.getHoverTooltipShowTimeout());
                }
                if ((getWindowSystemUiVisibility() & 1) == 1) {
                    timeout = ViewConfiguration.getHoverTooltipHideShortTimeout();
                } else {
                    timeout = ViewConfiguration.getHoverTooltipHideTimeout();
                }
                removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
                postDelayed(this.mTooltipInfo.mHideTooltipRunnable, (long) timeout);
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void handleTooltipKey(KeyEvent event) {
        switch (event.getAction()) {
            case 0:
                if (event.getRepeatCount() == 0) {
                    hideTooltip();
                    return;
                }
                return;
            case 1:
                handleTooltipUp();
                return;
            default:
                return;
        }
    }

    private void handleTooltipUp() {
        if (this.mTooltipInfo != null && this.mTooltipInfo.mTooltipPopup != null) {
            removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
            postDelayed(this.mTooltipInfo.mHideTooltipRunnable, (long) ViewConfiguration.getLongPressTooltipHideTimeout());
        }
    }

    private int getFocusableAttribute(TypedArray attributes) {
        TypedValue val = new TypedValue();
        if (!attributes.getValue(19, val)) {
            return 16;
        }
        if (val.type == 18) {
            return val.data == 0 ? 0 : 1;
        }
        return val.data;
    }

    public View getTooltipView() {
        if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
            return null;
        }
        return this.mTooltipInfo.mTooltipPopup.getContentView();
    }

    public static boolean isDefaultFocusHighlightEnabled() {
        return sUseDefaultFocusHighlight;
    }

    /* access modifiers changed from: package-private */
    public View dispatchUnhandledKeyEvent(KeyEvent evt) {
        if (onUnhandledKeyEvent(evt)) {
            return this;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean onUnhandledKeyEvent(KeyEvent event) {
        if (this.mListenerInfo == null || this.mListenerInfo.mUnhandledKeyListeners == null) {
            return false;
        }
        for (int i = this.mListenerInfo.mUnhandledKeyListeners.size() - 1; i >= 0; i--) {
            if (((OnUnhandledKeyEventListener) this.mListenerInfo.mUnhandledKeyListeners.get(i)).onUnhandledKeyEvent(this, event)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean hasUnhandledKeyListener() {
        return (this.mListenerInfo == null || this.mListenerInfo.mUnhandledKeyListeners == null || this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) ? false : true;
    }

    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        ArrayList<OnUnhandledKeyEventListener> listeners = getListenerInfo().mUnhandledKeyListeners;
        if (listeners == null) {
            listeners = new ArrayList<>();
            ArrayList unused = getListenerInfo().mUnhandledKeyListeners = listeners;
        }
        listeners.add(listener);
        if (listeners.size() == 1 && (this.mParent instanceof ViewGroup)) {
            ((ViewGroup) this.mParent).incrementChildUnhandledKeyListeners();
        }
    }

    public void removeOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        if (this.mListenerInfo != null && this.mListenerInfo.mUnhandledKeyListeners != null && !this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
            this.mListenerInfo.mUnhandledKeyListeners.remove(listener);
            if (this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
                ArrayList unused = this.mListenerInfo.mUnhandledKeyListeners = null;
                if (this.mParent instanceof ViewGroup) {
                    ((ViewGroup) this.mParent).decrementChildUnhandledKeyListeners();
                }
            }
        }
    }
}
