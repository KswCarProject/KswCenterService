package android.view;

import android.animation.AnimatorInflater;
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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManagerGlobal;
import android.media.TtmlUtils;
import android.net.TrafficStats;
import android.net.wifi.WifiEnterpriseConfig;
import android.p007os.BatteryStats;
import android.p007os.Bundle;
import android.p007os.DropBoxManager;
import android.p007os.Handler;
import android.p007os.IBinder;
import android.p007os.Message;
import android.p007os.Parcel;
import android.p007os.Parcelable;
import android.p007os.RemoteException;
import android.p007os.SystemClock;
import android.p007os.Trace;
import android.provider.CalendarContract;
import android.provider.Downloads;
import android.provider.SettingsStringUtil;
import android.provider.Telephony;
import android.provider.UserDictionary;
import android.security.keystore.KeyProperties;
import android.sysprop.DisplayProperties;
import android.telephony.SmsManager;
import android.text.TextUtils;
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
import android.view.SurfaceControl;
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
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ScrollBarDrawable;
import com.android.internal.C3132R;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.view.TooltipPopup;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.widget.ScrollBarUtils;
import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.PluralRules;
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

/* loaded from: classes4.dex */
public class View implements Drawable.Callback, KeyEvent.Callback, AccessibilityEventSource {
    public static final int ACCESSIBILITY_CURSOR_POSITION_UNDEFINED = -1;
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    static final int ACCESSIBILITY_LIVE_REGION_DEFAULT = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    static final int ALL_RTL_PROPERTIES_RESOLVED = 1610678816;
    public static final int AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 1;
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
    static final int DEBUG_CORNERS_SIZE_DIP = 8;
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
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_LOW = 524288;
    static final int DRAWING_CACHE_QUALITY_MASK = 1572864;
    static final int DRAW_MASK = 128;
    static final int DUPLICATE_PARENT_STATE = 4194304;
    static final int ENABLED = 0;
    static final int ENABLED_MASK = 32;
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
    static final int PFLAG2_TEXT_ALIGNMENT_MASK = 57344;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT = 13;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED = 65536;
    private static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT = 131072;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK = 917504;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT = 17;
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
    private static final int PROVIDER_BACKGROUND = 0;
    private static final int PROVIDER_BOUNDS = 2;
    private static final int PROVIDER_NONE = 1;
    private static final int PROVIDER_PADDED_BOUNDS = 3;
    public static final int PUBLIC_STATUS_BAR_VISIBILITY_MASK = 16375;
    static final int SAVE_DISABLED = 65536;
    static final int SAVE_DISABLED_MASK = 65536;
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
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    protected static final String VIEW_LOG_TAG = "View";
    protected static final int VIEW_STRUCTURE_FOR_ASSIST = 0;
    protected static final int VIEW_STRUCTURE_FOR_AUTOFILL = 1;
    protected static final int VIEW_STRUCTURE_FOR_CONTENT_CAPTURE = 2;
    static final int VISIBILITY_MASK = 12;
    public static final int VISIBLE = 0;
    static final int WILL_NOT_CACHE_DRAWING = 131072;
    static final int WILL_NOT_DRAW = 128;
    private static SparseArray<String> mAttributeMap;
    private static boolean sAcceptZeroSizeDragShadow;
    private static boolean sAlwaysAssignFocus;
    private static boolean sAutoFocusableOffUIThreadWontNotifyParents;
    static boolean sBrokenInsetsDispatch;
    protected static boolean sBrokenWindowBackground;
    private static boolean sCanFocusZeroSized;
    static boolean sCascadedDragDrop;
    private static Paint sDebugPaint;
    public static String sDebugViewAttributesApplicationPackage;
    static boolean sHasFocusableExcludeAutoFocusable;
    private static int sNextAccessibilityViewId;
    protected static boolean sPreserveMarginParamsInLayoutParamConversion;
    private static boolean sThrowOnInvalidFloatProperties;
    private static boolean sUseDefaultFocusHighlight;
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
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(deepExport = true, prefix = "bg_")
    private Drawable mBackground;
    private RenderNode mBackgroundRenderNode;
    @UnsupportedAppUsage
    private int mBackgroundResource;
    private boolean mBackgroundSizeChanged;
    private TintInfo mBackgroundTint;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mBottom;
    private ContentCaptureSession mCachedContentCaptureSession;
    @UnsupportedAppUsage
    public boolean mCachingFailed;
    @ViewDebug.ExportedProperty(category = "drawing")
    Rect mClipBounds;
    private ContentCaptureSession mContentCaptureSession;
    private CharSequence mContentDescription;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(deepExport = true)
    protected Context mContext;
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
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private boolean mHasPerformedLongPress;
    private boolean mHoveringTouchDelegate;
    @ViewDebug.ExportedProperty(resolveId = true)
    int mID;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextButtonPress;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    @UnsupportedAppUsage
    private SparseArray<Object> mKeyedTags;
    private int mLabelForId;
    private boolean mLastIsOpaque;
    Paint mLayerPaint;
    @ViewDebug.ExportedProperty(category = "drawing", mapping = {@ViewDebug.IntToString(from = 0, m46to = KeyProperties.DIGEST_NONE), @ViewDebug.IntToString(from = 1, m46to = "SOFTWARE"), @ViewDebug.IntToString(from = 2, m46to = "HARDWARE")})
    int mLayerType;
    private Insets mLayoutInsets;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected ViewGroup.LayoutParams mLayoutParams;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mLeft;
    private boolean mLeftPaddingDefined;
    @UnsupportedAppUsage
    ListenerInfo mListenerInfo;
    private float mLongClickX;
    private float mLongClickY;
    private MatchIdPredicate mMatchIdPredicate;
    private MatchLabelForPredicate mMatchLabelForPredicate;
    private LongSparseLongArray mMeasureCache;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "measurement")
    int mMeasuredHeight;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "measurement")
    int mMeasuredWidth;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = "measurement")
    private int mMinHeight;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = "measurement")
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
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingBottom;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingLeft;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingRight;
    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingTop;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    protected ViewParent mParent;
    private CheckForLongPress mPendingCheckForLongPress;
    @UnsupportedAppUsage
    private CheckForTap mPendingCheckForTap;
    private PerformClick mPerformClick;
    private PointerIcon mPointerIcon;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123769414)
    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "FORCE_LAYOUT"), @ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LAYOUT_REQUIRED"), @ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "DRAWING_CACHE_INVALID", outputIf = false), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "DRAWN", outputIf = true), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "NOT_DRAWN", outputIf = false), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "DIRTY")}, formatToHexString = true)
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
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    protected int mRight;
    private boolean mRightPaddingDefined;
    private RoundScrollbarRenderer mRoundScrollbarRenderer;
    private HandlerActionQueue mRunQueue;
    @UnsupportedAppUsage
    private ScrollabilityCache mScrollCache;
    private Drawable mScrollIndicatorDrawable;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = "scrolling")
    protected int mScrollX;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = "scrolling")
    protected int mScrollY;
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
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
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
    @UnsupportedAppUsage(maxTargetSdk = 28)
    @ViewDebug.ExportedProperty(formatToHexString = true)
    int mViewFlags;
    private Handler mVisibilityChangeForAutofillHandler;
    int mWindowAttachCount;
    public static boolean DEBUG_DRAW = false;
    public static boolean sDebugViewAttributes = false;
    private static final int[] AUTOFILL_HIGHLIGHT_ATTR = {16844136};
    private static boolean sCompatibilityDone = false;
    private static boolean sUseBrokenMakeMeasureSpec = false;
    static boolean sUseZeroUnspecifiedMeasureSpec = false;
    private static boolean sIgnoreMeasureCache = false;
    private static boolean sAlwaysRemeasureExactly = false;
    static boolean sTextureViewIgnoresDrawableSetters = false;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static final int[] DRAWING_CACHE_QUALITY_FLAGS = {0, 524288, 1048576};
    protected static final int[] EMPTY_STATE_SET = StateSet.get(0);
    protected static final int[] WINDOW_FOCUSED_STATE_SET = StateSet.get(1);
    protected static final int[] SELECTED_STATE_SET = StateSet.get(2);
    protected static final int[] SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(3);
    protected static final int[] FOCUSED_STATE_SET = StateSet.get(4);
    protected static final int[] FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(5);
    protected static final int[] FOCUSED_SELECTED_STATE_SET = StateSet.get(6);
    protected static final int[] FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(7);
    protected static final int[] ENABLED_STATE_SET = StateSet.get(8);
    protected static final int[] ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(9);
    protected static final int[] ENABLED_SELECTED_STATE_SET = StateSet.get(10);
    protected static final int[] ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(11);
    protected static final int[] ENABLED_FOCUSED_STATE_SET = StateSet.get(12);
    protected static final int[] ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(13);
    protected static final int[] ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(14);
    protected static final int[] ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(15);
    protected static final int[] PRESSED_STATE_SET = StateSet.get(16);
    protected static final int[] PRESSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(17);
    protected static final int[] PRESSED_SELECTED_STATE_SET = StateSet.get(18);
    protected static final int[] PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(19);
    protected static final int[] PRESSED_FOCUSED_STATE_SET = StateSet.get(20);
    protected static final int[] PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(21);
    protected static final int[] PRESSED_FOCUSED_SELECTED_STATE_SET = StateSet.get(22);
    protected static final int[] PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(23);
    protected static final int[] PRESSED_ENABLED_STATE_SET = StateSet.get(24);
    protected static final int[] PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(25);
    protected static final int[] PRESSED_ENABLED_SELECTED_STATE_SET = StateSet.get(26);
    protected static final int[] PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(27);
    protected static final int[] PRESSED_ENABLED_FOCUSED_STATE_SET = StateSet.get(28);
    protected static final int[] PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(29);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(30);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(31);
    static final int DEBUG_CORNERS_COLOR = Color.rgb(63, 127, 255);
    static final ThreadLocal<Rect> sThreadLocal = new ThreadLocal<>();
    private static final int[] LAYOUT_DIRECTION_FLAGS = {0, 1, 2, 3};
    private static final int[] PFLAG2_TEXT_DIRECTION_FLAGS = {0, 64, 128, 192, 256, 320, MetricsProto.MetricsEvent.ACTION_SHOW_SETTINGS_SUGGESTION, 448};
    private static final int[] PFLAG2_TEXT_ALIGNMENT_FLAGS = {0, 8192, 16384, 24576, 32768, 40960, 49152};
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static final Property<View, Float> ALPHA = new FloatProperty<View>("alpha") { // from class: android.view.View.3
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setAlpha(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getAlpha());
        }
    };
    public static final Property<View, Float> TRANSLATION_X = new FloatProperty<View>("translationX") { // from class: android.view.View.4
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setTranslationX(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getTranslationX());
        }
    };
    public static final Property<View, Float> TRANSLATION_Y = new FloatProperty<View>("translationY") { // from class: android.view.View.5
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setTranslationY(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getTranslationY());
        }
    };
    public static final Property<View, Float> TRANSLATION_Z = new FloatProperty<View>("translationZ") { // from class: android.view.View.6
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setTranslationZ(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getTranslationZ());
        }
    };

    /* renamed from: X */
    public static final Property<View, Float> f2410X = new FloatProperty<View>("x") { // from class: android.view.View.7
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setX(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getX());
        }
    };

    /* renamed from: Y */
    public static final Property<View, Float> f2411Y = new FloatProperty<View>(DateFormat.YEAR) { // from class: android.view.View.8
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setY(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getY());
        }
    };

    /* renamed from: Z */
    public static final Property<View, Float> f2412Z = new FloatProperty<View>(DateFormat.ABBR_SPECIFIC_TZ) { // from class: android.view.View.9
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setZ(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getZ());
        }
    };
    public static final Property<View, Float> ROTATION = new FloatProperty<View>("rotation") { // from class: android.view.View.10
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setRotation(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getRotation());
        }
    };
    public static final Property<View, Float> ROTATION_X = new FloatProperty<View>("rotationX") { // from class: android.view.View.11
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setRotationX(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getRotationX());
        }
    };
    public static final Property<View, Float> ROTATION_Y = new FloatProperty<View>("rotationY") { // from class: android.view.View.12
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setRotationY(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getRotationY());
        }
    };
    public static final Property<View, Float> SCALE_X = new FloatProperty<View>("scaleX") { // from class: android.view.View.13
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setScaleX(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getScaleX());
        }
    };
    public static final Property<View, Float> SCALE_Y = new FloatProperty<View>("scaleY") { // from class: android.view.View.14
        @Override // android.util.FloatProperty
        public void setValue(View object, float value) {
            object.setScaleY(value);
        }

        @Override // android.util.Property
        public Float get(View object) {
            return Float.valueOf(object.getScaleY());
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface AutofillFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface AutofillImportance {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface AutofillType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface DrawingCacheQuality {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface FindViewFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface FocusDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface FocusRealDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface Focusable {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface FocusableMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface LayerType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface LayoutDir {
    }

    /* loaded from: classes4.dex */
    public interface OnApplyWindowInsetsListener {
        WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets);
    }

    /* loaded from: classes4.dex */
    public interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View view);

        void onViewDetachedFromWindow(View view);
    }

    /* loaded from: classes4.dex */
    public interface OnCapturedPointerListener {
        boolean onCapturedPointer(View view, MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnClickListener {
        void onClick(View view);
    }

    /* loaded from: classes4.dex */
    public interface OnContextClickListener {
        boolean onContextClick(View view);
    }

    /* loaded from: classes4.dex */
    public interface OnCreateContextMenuListener {
        void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo);
    }

    /* loaded from: classes4.dex */
    public interface OnDragListener {
        boolean onDrag(View view, DragEvent dragEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnFocusChangeListener {
        void onFocusChange(View view, boolean z);
    }

    /* loaded from: classes4.dex */
    public interface OnGenericMotionListener {
        boolean onGenericMotion(View view, MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnHoverListener {
        boolean onHover(View view, MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnKeyListener {
        boolean onKey(View view, int i, KeyEvent keyEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnLayoutChangeListener {
        void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
    }

    /* loaded from: classes4.dex */
    public interface OnLongClickListener {
        boolean onLongClick(View view);
    }

    /* loaded from: classes4.dex */
    public interface OnScrollChangeListener {
        void onScrollChange(View view, int i, int i2, int i3, int i4);
    }

    /* loaded from: classes4.dex */
    public interface OnSystemUiVisibilityChangeListener {
        void onSystemUiVisibilityChange(int i);
    }

    /* loaded from: classes4.dex */
    public interface OnTouchListener {
        boolean onTouch(View view, MotionEvent motionEvent);
    }

    /* loaded from: classes4.dex */
    public interface OnUnhandledKeyEventListener {
        boolean onUnhandledKeyEvent(View view, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface ResolvedLayoutDir {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface ScrollBarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface ScrollIndicators {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface TextAlignment {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface ViewStructureType {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface Visibility {
    }

    /* loaded from: classes4.dex */
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

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAccessibilityFocusedId = propertyMapper.mapBoolean("accessibilityFocused", 0);
            this.mAccessibilityHeadingId = propertyMapper.mapBoolean("accessibilityHeading", 16844160);
            SparseArray<String> accessibilityLiveRegionEnumMapping = new SparseArray<>();
            accessibilityLiveRegionEnumMapping.put(0, "none");
            accessibilityLiveRegionEnumMapping.put(1, "polite");
            accessibilityLiveRegionEnumMapping.put(2, "assertive");
            Objects.requireNonNull(accessibilityLiveRegionEnumMapping);
            this.mAccessibilityLiveRegionId = propertyMapper.mapIntEnum("accessibilityLiveRegion", 16843758, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(accessibilityLiveRegionEnumMapping));
            this.mAccessibilityPaneTitleId = propertyMapper.mapObject("accessibilityPaneTitle", 16844156);
            this.mAccessibilityTraversalAfterId = propertyMapper.mapResourceId("accessibilityTraversalAfter", 16843986);
            this.mAccessibilityTraversalBeforeId = propertyMapper.mapResourceId("accessibilityTraversalBefore", 16843985);
            this.mActivatedId = propertyMapper.mapBoolean("activated", 0);
            this.mAlphaId = propertyMapper.mapFloat("alpha", 16843551);
            this.mAutofillHintsId = propertyMapper.mapObject("autofillHints", 16844118);
            this.mBackgroundId = propertyMapper.mapObject("background", 16842964);
            this.mBackgroundTintId = propertyMapper.mapObject("backgroundTint", 16843883);
            this.mBackgroundTintModeId = propertyMapper.mapObject("backgroundTintMode", 16843884);
            this.mBaselineId = propertyMapper.mapInt("baseline", 16843548);
            this.mClickableId = propertyMapper.mapBoolean("clickable", 16842981);
            this.mContentDescriptionId = propertyMapper.mapObject("contentDescription", 16843379);
            this.mContextClickableId = propertyMapper.mapBoolean("contextClickable", 16844007);
            this.mDefaultFocusHighlightEnabledId = propertyMapper.mapBoolean("defaultFocusHighlightEnabled", 16844130);
            SparseArray<String> drawingCacheQualityEnumMapping = new SparseArray<>();
            drawingCacheQualityEnumMapping.put(0, "auto");
            drawingCacheQualityEnumMapping.put(524288, "low");
            drawingCacheQualityEnumMapping.put(1048576, "high");
            Objects.requireNonNull(drawingCacheQualityEnumMapping);
            this.mDrawingCacheQualityId = propertyMapper.mapIntEnum("drawingCacheQuality", 16842984, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(drawingCacheQualityEnumMapping));
            this.mDuplicateParentStateId = propertyMapper.mapBoolean("duplicateParentState", 16842985);
            this.mElevationId = propertyMapper.mapFloat("elevation", 16843840);
            this.mEnabledId = propertyMapper.mapBoolean("enabled", 16842766);
            this.mFadingEdgeLengthId = propertyMapper.mapInt("fadingEdgeLength", 16842976);
            this.mFilterTouchesWhenObscuredId = propertyMapper.mapBoolean("filterTouchesWhenObscured", 16843460);
            this.mFitsSystemWindowsId = propertyMapper.mapBoolean("fitsSystemWindows", 16842973);
            SparseArray<String> focusableEnumMapping = new SparseArray<>();
            focusableEnumMapping.put(0, "false");
            focusableEnumMapping.put(1, "true");
            focusableEnumMapping.put(16, "auto");
            Objects.requireNonNull(focusableEnumMapping);
            this.mFocusableId = propertyMapper.mapIntEnum("focusable", 16842970, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(focusableEnumMapping));
            this.mFocusableInTouchModeId = propertyMapper.mapBoolean("focusableInTouchMode", 16842971);
            this.mFocusedId = propertyMapper.mapBoolean("focused", 0);
            this.mFocusedByDefaultId = propertyMapper.mapBoolean("focusedByDefault", 16844100);
            this.mForceDarkAllowedId = propertyMapper.mapBoolean("forceDarkAllowed", 16844172);
            this.mForegroundId = propertyMapper.mapObject("foreground", 16843017);
            this.mForegroundGravityId = propertyMapper.mapGravity("foregroundGravity", 16843264);
            this.mForegroundTintId = propertyMapper.mapObject("foregroundTint", 16843885);
            this.mForegroundTintModeId = propertyMapper.mapObject("foregroundTintMode", 16843886);
            this.mHapticFeedbackEnabledId = propertyMapper.mapBoolean("hapticFeedbackEnabled", 16843358);
            this.mIdId = propertyMapper.mapResourceId("id", 16842960);
            SparseArray<String> importantForAccessibilityEnumMapping = new SparseArray<>();
            importantForAccessibilityEnumMapping.put(0, "auto");
            importantForAccessibilityEnumMapping.put(1, "yes");
            importantForAccessibilityEnumMapping.put(2, "no");
            importantForAccessibilityEnumMapping.put(4, "noHideDescendants");
            Objects.requireNonNull(importantForAccessibilityEnumMapping);
            this.mImportantForAccessibilityId = propertyMapper.mapIntEnum("importantForAccessibility", 16843690, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(importantForAccessibilityEnumMapping));
            SparseArray<String> importantForAutofillEnumMapping = new SparseArray<>();
            importantForAutofillEnumMapping.put(0, "auto");
            importantForAutofillEnumMapping.put(1, "yes");
            importantForAutofillEnumMapping.put(2, "no");
            importantForAutofillEnumMapping.put(4, "yesExcludeDescendants");
            importantForAutofillEnumMapping.put(8, "noExcludeDescendants");
            Objects.requireNonNull(importantForAutofillEnumMapping);
            this.mImportantForAutofillId = propertyMapper.mapIntEnum("importantForAutofill", 16844120, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(importantForAutofillEnumMapping));
            this.mIsScrollContainerId = propertyMapper.mapBoolean("isScrollContainer", 16843342);
            this.mKeepScreenOnId = propertyMapper.mapBoolean("keepScreenOn", 16843286);
            this.mKeyboardNavigationClusterId = propertyMapper.mapBoolean("keyboardNavigationCluster", 16844096);
            this.mLabelForId = propertyMapper.mapResourceId("labelFor", 16843718);
            SparseArray<String> layerTypeEnumMapping = new SparseArray<>();
            layerTypeEnumMapping.put(0, "none");
            layerTypeEnumMapping.put(1, "software");
            layerTypeEnumMapping.put(2, "hardware");
            Objects.requireNonNull(layerTypeEnumMapping);
            this.mLayerTypeId = propertyMapper.mapIntEnum("layerType", 16843604, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(layerTypeEnumMapping));
            SparseArray<String> layoutDirectionEnumMapping = new SparseArray<>();
            layoutDirectionEnumMapping.put(0, "ltr");
            layoutDirectionEnumMapping.put(1, "rtl");
            Objects.requireNonNull(layoutDirectionEnumMapping);
            this.mLayoutDirectionId = propertyMapper.mapIntEnum("layoutDirection", 16843698, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(layoutDirectionEnumMapping));
            this.mLongClickableId = propertyMapper.mapBoolean("longClickable", 16842982);
            this.mMinHeightId = propertyMapper.mapInt("minHeight", 16843072);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mNestedScrollingEnabledId = propertyMapper.mapBoolean("nestedScrollingEnabled", 16843830);
            this.mNextClusterForwardId = propertyMapper.mapResourceId("nextClusterForward", 16844098);
            this.mNextFocusDownId = propertyMapper.mapResourceId("nextFocusDown", 16842980);
            this.mNextFocusForwardId = propertyMapper.mapResourceId("nextFocusForward", 16843580);
            this.mNextFocusLeftId = propertyMapper.mapResourceId("nextFocusLeft", 16842977);
            this.mNextFocusRightId = propertyMapper.mapResourceId("nextFocusRight", 16842978);
            this.mNextFocusUpId = propertyMapper.mapResourceId("nextFocusUp", 16842979);
            this.mOutlineAmbientShadowColorId = propertyMapper.mapColor("outlineAmbientShadowColor", 16844162);
            this.mOutlineProviderId = propertyMapper.mapObject("outlineProvider", 16843960);
            this.mOutlineSpotShadowColorId = propertyMapper.mapColor("outlineSpotShadowColor", 16844161);
            SparseArray<String> overScrollModeEnumMapping = new SparseArray<>();
            overScrollModeEnumMapping.put(0, "always");
            overScrollModeEnumMapping.put(1, "ifContentScrolls");
            overScrollModeEnumMapping.put(2, "never");
            Objects.requireNonNull(overScrollModeEnumMapping);
            this.mOverScrollModeId = propertyMapper.mapIntEnum("overScrollMode", 16843457, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(overScrollModeEnumMapping));
            this.mPaddingBottomId = propertyMapper.mapInt("paddingBottom", 16842969);
            this.mPaddingLeftId = propertyMapper.mapInt("paddingLeft", 16842966);
            this.mPaddingRightId = propertyMapper.mapInt("paddingRight", 16842968);
            this.mPaddingTopId = propertyMapper.mapInt("paddingTop", 16842967);
            this.mPointerIconId = propertyMapper.mapObject("pointerIcon", 16844041);
            this.mPressedId = propertyMapper.mapBoolean("pressed", 0);
            SparseArray<String> rawLayoutDirectionEnumMapping = new SparseArray<>();
            rawLayoutDirectionEnumMapping.put(0, "ltr");
            rawLayoutDirectionEnumMapping.put(1, "rtl");
            rawLayoutDirectionEnumMapping.put(2, "inherit");
            rawLayoutDirectionEnumMapping.put(3, UserDictionary.Words.LOCALE);
            Objects.requireNonNull(rawLayoutDirectionEnumMapping);
            this.mRawLayoutDirectionId = propertyMapper.mapIntEnum("rawLayoutDirection", 0, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(rawLayoutDirectionEnumMapping));
            SparseArray<String> rawTextAlignmentEnumMapping = new SparseArray<>();
            rawTextAlignmentEnumMapping.put(0, "inherit");
            rawTextAlignmentEnumMapping.put(1, "gravity");
            rawTextAlignmentEnumMapping.put(2, "textStart");
            rawTextAlignmentEnumMapping.put(3, "textEnd");
            rawTextAlignmentEnumMapping.put(4, "center");
            rawTextAlignmentEnumMapping.put(5, "viewStart");
            rawTextAlignmentEnumMapping.put(6, "viewEnd");
            Objects.requireNonNull(rawTextAlignmentEnumMapping);
            this.mRawTextAlignmentId = propertyMapper.mapIntEnum("rawTextAlignment", 0, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(rawTextAlignmentEnumMapping));
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
            this.mRawTextDirectionId = propertyMapper.mapIntEnum("rawTextDirection", 0, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(rawTextDirectionEnumMapping));
            final IntFlagMapping requiresFadingEdgeFlagMapping = new IntFlagMapping();
            requiresFadingEdgeFlagMapping.add(4096, 4096, Slice.HINT_HORIZONTAL);
            requiresFadingEdgeFlagMapping.add(12288, 0, "none");
            requiresFadingEdgeFlagMapping.add(8192, 8192, "vertical");
            Objects.requireNonNull(requiresFadingEdgeFlagMapping);
            this.mRequiresFadingEdgeId = propertyMapper.mapIntFlag("requiresFadingEdge", 16843685, new IntFunction() { // from class: android.view.-$$Lambda$gFNlJIKfxqleu304aRWP5R5v1yY
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            this.mRotationId = propertyMapper.mapFloat("rotation", 16843558);
            this.mRotationXId = propertyMapper.mapFloat("rotationX", 16843559);
            this.mRotationYId = propertyMapper.mapFloat("rotationY", 16843560);
            this.mSaveEnabledId = propertyMapper.mapBoolean("saveEnabled", 16842983);
            this.mScaleXId = propertyMapper.mapFloat("scaleX", 16843556);
            this.mScaleYId = propertyMapper.mapFloat("scaleY", 16843557);
            this.mScreenReaderFocusableId = propertyMapper.mapBoolean("screenReaderFocusable", 16844148);
            final IntFlagMapping scrollIndicatorsFlagMapping = new IntFlagMapping();
            scrollIndicatorsFlagMapping.add(2, 2, "bottom");
            scrollIndicatorsFlagMapping.add(32, 32, "end");
            scrollIndicatorsFlagMapping.add(4, 4, "left");
            scrollIndicatorsFlagMapping.add(-1, 0, "none");
            scrollIndicatorsFlagMapping.add(8, 8, "right");
            scrollIndicatorsFlagMapping.add(16, 16, Telephony.BaseMmsColumns.START);
            scrollIndicatorsFlagMapping.add(1, 1, "top");
            Objects.requireNonNull(scrollIndicatorsFlagMapping);
            this.mScrollIndicatorsId = propertyMapper.mapIntFlag("scrollIndicators", 16844006, new IntFunction() { // from class: android.view.-$$Lambda$gFNlJIKfxqleu304aRWP5R5v1yY
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            this.mScrollXId = propertyMapper.mapInt("scrollX", 16842962);
            this.mScrollYId = propertyMapper.mapInt("scrollY", 16842963);
            this.mScrollbarDefaultDelayBeforeFadeId = propertyMapper.mapInt("scrollbarDefaultDelayBeforeFade", 16843433);
            this.mScrollbarFadeDurationId = propertyMapper.mapInt("scrollbarFadeDuration", 16843432);
            this.mScrollbarSizeId = propertyMapper.mapInt("scrollbarSize", 16842851);
            SparseArray<String> scrollbarStyleEnumMapping = new SparseArray<>();
            scrollbarStyleEnumMapping.put(0, "insideOverlay");
            scrollbarStyleEnumMapping.put(16777216, "insideInset");
            scrollbarStyleEnumMapping.put(33554432, "outsideOverlay");
            scrollbarStyleEnumMapping.put(50331648, "outsideInset");
            Objects.requireNonNull(scrollbarStyleEnumMapping);
            this.mScrollbarStyleId = propertyMapper.mapIntEnum("scrollbarStyle", 16842879, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(scrollbarStyleEnumMapping));
            this.mSelectedId = propertyMapper.mapBoolean(Slice.HINT_SELECTED, 0);
            this.mSolidColorId = propertyMapper.mapColor("solidColor", 16843594);
            this.mSoundEffectsEnabledId = propertyMapper.mapBoolean("soundEffectsEnabled", 16843285);
            this.mStateListAnimatorId = propertyMapper.mapObject("stateListAnimator", 16843848);
            this.mTagId = propertyMapper.mapObject(DropBoxManager.EXTRA_TAG, 16842961);
            SparseArray<String> textAlignmentEnumMapping = new SparseArray<>();
            textAlignmentEnumMapping.put(1, "gravity");
            textAlignmentEnumMapping.put(2, "textStart");
            textAlignmentEnumMapping.put(3, "textEnd");
            textAlignmentEnumMapping.put(4, "center");
            textAlignmentEnumMapping.put(5, "viewStart");
            textAlignmentEnumMapping.put(6, "viewEnd");
            Objects.requireNonNull(textAlignmentEnumMapping);
            this.mTextAlignmentId = propertyMapper.mapIntEnum("textAlignment", 16843697, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(textAlignmentEnumMapping));
            SparseArray<String> textDirectionEnumMapping = new SparseArray<>();
            textDirectionEnumMapping.put(1, "firstStrong");
            textDirectionEnumMapping.put(2, "anyRtl");
            textDirectionEnumMapping.put(3, "ltr");
            textDirectionEnumMapping.put(4, "rtl");
            textDirectionEnumMapping.put(5, UserDictionary.Words.LOCALE);
            textDirectionEnumMapping.put(6, "firstStrongLtr");
            textDirectionEnumMapping.put(7, "firstStrongRtl");
            Objects.requireNonNull(textDirectionEnumMapping);
            this.mTextDirectionId = propertyMapper.mapIntEnum("textDirection", 0, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(textDirectionEnumMapping));
            this.mTooltipTextId = propertyMapper.mapObject("tooltipText", 16844084);
            this.mTransformPivotXId = propertyMapper.mapFloat("transformPivotX", 16843552);
            this.mTransformPivotYId = propertyMapper.mapFloat("transformPivotY", 16843553);
            this.mTransitionNameId = propertyMapper.mapObject("transitionName", 16843776);
            this.mTranslationXId = propertyMapper.mapFloat("translationX", 16843554);
            this.mTranslationYId = propertyMapper.mapFloat("translationY", 16843555);
            this.mTranslationZId = propertyMapper.mapFloat("translationZ", 16843770);
            SparseArray<String> visibilityEnumMapping = new SparseArray<>();
            visibilityEnumMapping.put(0, CalendarContract.CalendarColumns.VISIBLE);
            visibilityEnumMapping.put(4, "invisible");
            visibilityEnumMapping.put(8, "gone");
            Objects.requireNonNull(visibilityEnumMapping);
            this.mVisibilityId = propertyMapper.mapIntEnum(Downloads.Impl.COLUMN_VISIBILITY, 16842972, new $$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(visibilityEnumMapping));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(View node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
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
        }
    }

    /* loaded from: classes4.dex */
    static class TransformationInfo {
        private Matrix mInverseMatrix;
        private final Matrix mMatrix = new Matrix();
        @ViewDebug.ExportedProperty
        private float mAlpha = 1.0f;
        float mTransitionAlpha = 1.0f;

        TransformationInfo() {
        }
    }

    /* loaded from: classes4.dex */
    static class TintInfo {
        BlendMode mBlendMode;
        boolean mHasTintList;
        boolean mHasTintMode;
        ColorStateList mTintList;

        TintInfo() {
        }
    }

    /* loaded from: classes4.dex */
    private static class ForegroundInfo {
        private boolean mBoundsChanged;
        private Drawable mDrawable;
        private int mGravity;
        private boolean mInsidePadding;
        private final Rect mOverlayBounds;
        private final Rect mSelfBounds;
        private TintInfo mTintInfo;

        private ForegroundInfo() {
            this.mGravity = 119;
            this.mInsidePadding = true;
            this.mBoundsChanged = true;
            this.mSelfBounds = new Rect();
            this.mOverlayBounds = new Rect();
        }
    }

    /* loaded from: classes4.dex */
    static class ListenerInfo {
        OnApplyWindowInsetsListener mOnApplyWindowInsetsListener;
        private CopyOnWriteArrayList<OnAttachStateChangeListener> mOnAttachStateChangeListeners;
        OnCapturedPointerListener mOnCapturedPointerListener;
        @UnsupportedAppUsage
        public OnClickListener mOnClickListener;
        protected OnContextClickListener mOnContextClickListener;
        @UnsupportedAppUsage
        protected OnCreateContextMenuListener mOnCreateContextMenuListener;
        @UnsupportedAppUsage
        private OnDragListener mOnDragListener;
        @UnsupportedAppUsage
        protected OnFocusChangeListener mOnFocusChangeListener;
        @UnsupportedAppUsage
        private OnGenericMotionListener mOnGenericMotionListener;
        @UnsupportedAppUsage
        private OnHoverListener mOnHoverListener;
        @UnsupportedAppUsage
        private OnKeyListener mOnKeyListener;
        private ArrayList<OnLayoutChangeListener> mOnLayoutChangeListeners;
        @UnsupportedAppUsage
        protected OnLongClickListener mOnLongClickListener;
        protected OnScrollChangeListener mOnScrollChangeListener;
        private OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener;
        @UnsupportedAppUsage
        private OnTouchListener mOnTouchListener;
        public RenderNode.PositionUpdateListener mPositionUpdateListener;
        private List<Rect> mSystemGestureExclusionRects;
        private ArrayList<OnUnhandledKeyEventListener> mUnhandledKeyListeners;
        private WindowInsetsAnimationListener mWindowInsetsAnimationListener;

        ListenerInfo() {
        }
    }

    /* loaded from: classes4.dex */
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

        /* JADX INFO: Access modifiers changed from: private */
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

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAnchorPos() {
            this.mAnchorX = Integer.MAX_VALUE;
            this.mAnchorY = Integer.MAX_VALUE;
        }
    }

    public View(Context context) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
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
        if (!InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = null;
        } else {
            inputEventConsistencyVerifier = new InputEventConsistencyVerifier(this, 0);
        }
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mSourceLayoutId = 0;
        this.mContext = context;
        this.mResources = context != null ? context.getResources() : null;
        this.mViewFlags = 402653200;
        this.mPrivateFlags2 = 140296;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOverScrollMode(1);
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mRenderNode = RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
        if (!sCompatibilityDone && context != null) {
            int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
            if (targetSdkVersion > 17) {
                z = false;
            } else {
                z = true;
            }
            sUseBrokenMakeMeasureSpec = z;
            if (targetSdkVersion >= 19) {
                z2 = false;
            } else {
                z2 = true;
            }
            sIgnoreMeasureCache = z2;
            if (targetSdkVersion >= 23) {
                z3 = false;
            } else {
                z3 = true;
            }
            Canvas.sCompatibilityRestore = z3;
            if (targetSdkVersion >= 26) {
                z4 = false;
            } else {
                z4 = true;
            }
            Canvas.sCompatibilitySetBitmap = z4;
            Canvas.setCompatibilityVersion(targetSdkVersion);
            if (targetSdkVersion >= 23) {
                z5 = false;
            } else {
                z5 = true;
            }
            sUseZeroUnspecifiedMeasureSpec = z5;
            if (targetSdkVersion > 23) {
                z6 = false;
            } else {
                z6 = true;
            }
            sAlwaysRemeasureExactly = z6;
            if (targetSdkVersion > 23) {
                z7 = false;
            } else {
                z7 = true;
            }
            sTextureViewIgnoresDrawableSetters = z7;
            if (targetSdkVersion < 24) {
                z8 = false;
            } else {
                z8 = true;
            }
            sPreserveMarginParamsInLayoutParamConversion = z8;
            if (targetSdkVersion >= 24) {
                z9 = false;
            } else {
                z9 = true;
            }
            sCascadedDragDrop = z9;
            if (targetSdkVersion >= 26) {
                z10 = false;
            } else {
                z10 = true;
            }
            sHasFocusableExcludeAutoFocusable = z10;
            if (targetSdkVersion >= 26) {
                z11 = false;
            } else {
                z11 = true;
            }
            sAutoFocusableOffUIThreadWontNotifyParents = z11;
            sUseDefaultFocusHighlight = context.getResources().getBoolean(C3132R.bool.config_useDefaultFocusHighlight);
            if (targetSdkVersion < 28) {
                z12 = false;
            } else {
                z12 = true;
            }
            sThrowOnInvalidFloatProperties = z12;
            if (targetSdkVersion >= 28) {
                z13 = false;
            } else {
                z13 = true;
            }
            sCanFocusZeroSized = z13;
            if (targetSdkVersion >= 28) {
                z14 = false;
            } else {
                z14 = true;
            }
            sAlwaysAssignFocus = z14;
            if (targetSdkVersion >= 28) {
                z15 = false;
            } else {
                z15 = true;
            }
            sAcceptZeroSizeDragShadow = z15;
            if (ViewRootImpl.sNewInsetsMode == 2 && targetSdkVersion >= 29) {
                z16 = false;
            } else {
                z16 = true;
            }
            sBrokenInsetsDispatch = z16;
            sBrokenWindowBackground = targetSdkVersion < 29;
            sCompatibilityDone = true;
        }
    }

    public View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:204:0x06b0, code lost:
        if (r1 >= 14) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context);
        int leftPadding;
        int targetSdkVersion;
        int y;
        int y2;
        int viewFlagValues;
        int viewFlagMasks;
        int viewFlagValues2;
        int viewFlagMasks2;
        int viewFlagMasks3;
        int viewFlagValues3;
        int viewFlagValues4;
        int viewFlagValues5;
        int viewFlagValues6;
        int viewFlagValues7;
        boolean transformSet;
        CharSequence[] rawHints;
        CharSequence[] rawHints2;
        this.mSourceLayoutId = Resources.getAttributeSetSourceResId(attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, C3132R.styleable.View, defStyleAttr, defStyleRes);
        retrieveExplicitStyle(context.getTheme(), attrs);
        saveAttributeDataForStyleable(context, C3132R.styleable.View, attrs, a, defStyleAttr, defStyleRes);
        if (sDebugViewAttributes) {
            saveAttributeData(attrs, a);
        }
        int startPadding = Integer.MIN_VALUE;
        int endPadding = Integer.MIN_VALUE;
        boolean setScrollContainer = false;
        boolean transformSet2 = false;
        int scrollbarStyle = 0;
        int overScrollMode = this.mOverScrollMode;
        boolean initializeScrollbars = false;
        boolean initializeScrollIndicators = false;
        boolean startPaddingDefined = false;
        boolean endPaddingDefined = false;
        int leftPadding2 = -1;
        int leftPadding3 = context.getApplicationInfo().targetSdkVersion;
        int viewFlagValues8 = 0 | 16;
        int viewFlagValues9 = 0 | 16;
        int topPadding = -1;
        int N = a.getIndexCount();
        int rightPadding = -1;
        int viewFlagValues10 = viewFlagValues8;
        int padding = -1;
        int paddingHorizontal = -1;
        int paddingVertical = -1;
        int viewFlagMasks4 = viewFlagValues9;
        int x = 0;
        int y3 = 0;
        float tx = 0.0f;
        float ty = 0.0f;
        float tz = 0.0f;
        float elevation = 0.0f;
        float rotation = 0.0f;
        float rotationX = 0.0f;
        float rotationY = 0.0f;
        float sx = 1.0f;
        float sy = 1.0f;
        Drawable background = null;
        boolean leftPaddingDefined = false;
        boolean rightPaddingDefined = false;
        int viewFlagValues11 = 0;
        int bottomPadding = -1;
        int overScrollMode2 = overScrollMode;
        while (true) {
            int i = viewFlagValues11;
            if (i >= N) {
                int y4 = y3;
                int viewFlagValues12 = viewFlagValues10;
                int viewFlagMasks5 = viewFlagMasks4;
                setOverScrollMode(overScrollMode2);
                this.mUserPaddingStart = startPadding;
                this.mUserPaddingEnd = endPadding;
                if (background != null) {
                    setBackground(background);
                }
                this.mLeftPaddingDefined = leftPaddingDefined;
                this.mRightPaddingDefined = rightPaddingDefined;
                int padding2 = padding;
                if (padding2 >= 0) {
                    leftPadding = padding2;
                    topPadding = padding2;
                    bottomPadding = padding2;
                    this.mUserPaddingLeftInitial = padding2;
                    this.mUserPaddingRightInitial = padding2;
                    targetSdkVersion = padding2;
                } else {
                    int paddingHorizontal2 = paddingHorizontal;
                    if (paddingHorizontal2 >= 0) {
                        leftPadding2 = paddingHorizontal2;
                        rightPadding = paddingHorizontal2;
                        this.mUserPaddingLeftInitial = paddingHorizontal2;
                        this.mUserPaddingRightInitial = paddingHorizontal2;
                    }
                    leftPadding = leftPadding2;
                    int rightPadding2 = rightPadding;
                    if (paddingVertical >= 0) {
                        topPadding = paddingVertical;
                        bottomPadding = paddingVertical;
                    }
                    targetSdkVersion = rightPadding2;
                }
                if (isRtlCompatibilityMode()) {
                    boolean leftPaddingDefined2 = this.mLeftPaddingDefined;
                    if (!leftPaddingDefined2 && startPaddingDefined) {
                        leftPadding = startPadding;
                    }
                    this.mUserPaddingLeftInitial = leftPadding >= 0 ? leftPadding : this.mUserPaddingLeftInitial;
                    if (!this.mRightPaddingDefined && endPaddingDefined) {
                        targetSdkVersion = endPadding;
                    }
                    this.mUserPaddingRightInitial = targetSdkVersion >= 0 ? targetSdkVersion : this.mUserPaddingRightInitial;
                } else {
                    boolean hasRelativePadding = startPaddingDefined || endPaddingDefined;
                    boolean rightPaddingDefined2 = this.mLeftPaddingDefined;
                    if (rightPaddingDefined2 && !hasRelativePadding) {
                        this.mUserPaddingLeftInitial = leftPadding;
                    }
                    if (this.mRightPaddingDefined && !hasRelativePadding) {
                        this.mUserPaddingRightInitial = targetSdkVersion;
                    }
                }
                int i2 = this.mUserPaddingLeftInitial;
                int i3 = topPadding >= 0 ? topPadding : this.mPaddingTop;
                int leftPadding4 = this.mUserPaddingRightInitial;
                int rightPadding3 = bottomPadding >= 0 ? bottomPadding : this.mPaddingBottom;
                internalSetPadding(i2, i3, leftPadding4, rightPadding3);
                if (viewFlagMasks5 != 0) {
                    setFlags(viewFlagValues12, viewFlagMasks5);
                }
                if (initializeScrollbars) {
                    initializeScrollbarsInternal(a);
                }
                if (initializeScrollIndicators) {
                    initializeScrollIndicatorsInternal();
                }
                a.recycle();
                if (scrollbarStyle != 0) {
                    recomputePadding();
                }
                if (x == 0 && y4 == 0) {
                    y = y4;
                } else {
                    y = y4;
                    scrollTo(x, y);
                }
                if (transformSet2) {
                    setTranslationX(tx);
                    setTranslationY(ty);
                    setTranslationZ(tz);
                    float tz2 = elevation;
                    setElevation(tz2);
                    float elevation2 = rotation;
                    setRotation(elevation2);
                    float rotation2 = rotationX;
                    setRotationX(rotation2);
                    float rotationX2 = rotationY;
                    setRotationY(rotationX2);
                    float rotationY2 = sx;
                    setScaleX(rotationY2);
                    float sx2 = sy;
                    setScaleY(sx2);
                }
                if (!setScrollContainer && (viewFlagValues12 & 512) != 0) {
                    setScrollContainer(true);
                }
                computeOpaqueFlags();
                return;
            }
            int N2 = N;
            int attr = a.getIndex(i);
            if (attr != 109) {
                switch (attr) {
                    case 8:
                        y2 = y3;
                        viewFlagValues2 = viewFlagValues10;
                        viewFlagMasks2 = viewFlagMasks4;
                        int scrollbarStyle2 = a.getInt(attr, 0);
                        if (scrollbarStyle2 != 0) {
                            viewFlagValues2 |= scrollbarStyle2 & 50331648;
                            viewFlagMasks2 |= 50331648;
                        }
                        scrollbarStyle = scrollbarStyle2;
                        viewFlagValues10 = viewFlagValues2;
                        viewFlagMasks4 = viewFlagMasks2;
                        y3 = y2;
                        break;
                    case 9:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        this.mID = a.getResourceId(attr, -1);
                        break;
                    case 10:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        this.mTag = a.getText(attr);
                        break;
                    case 11:
                        int x2 = a.getDimensionPixelOffset(attr, 0);
                        x = x2;
                        break;
                    case 12:
                        int y5 = a.getDimensionPixelOffset(attr, 0);
                        y3 = y5;
                        break;
                    case 13:
                        Drawable background2 = a.getDrawable(attr);
                        background = background2;
                        break;
                    case 14:
                        y2 = y3;
                        int padding3 = a.getDimensionPixelSize(attr, -1);
                        this.mUserPaddingLeftInitial = padding3;
                        this.mUserPaddingRightInitial = padding3;
                        padding = padding3;
                        leftPaddingDefined = true;
                        rightPaddingDefined = true;
                        y3 = y2;
                        break;
                    case 15:
                        int leftPadding5 = a.getDimensionPixelSize(attr, -1);
                        this.mUserPaddingLeftInitial = leftPadding5;
                        leftPadding2 = leftPadding5;
                        leftPaddingDefined = true;
                        break;
                    case 16:
                        int topPadding2 = a.getDimensionPixelSize(attr, -1);
                        topPadding = topPadding2;
                        break;
                    case 17:
                        int rightPadding4 = a.getDimensionPixelSize(attr, -1);
                        this.mUserPaddingRightInitial = rightPadding4;
                        rightPadding = rightPadding4;
                        rightPaddingDefined = true;
                        break;
                    case 18:
                        int bottomPadding2 = a.getDimensionPixelSize(attr, -1);
                        bottomPadding = bottomPadding2;
                        break;
                    case 19:
                        y2 = y3;
                        viewFlagMasks3 = viewFlagMasks4;
                        viewFlagValues3 = (viewFlagValues10 & (-18)) | getFocusableAttribute(a);
                        if ((viewFlagValues3 & 16) == 0) {
                            viewFlagValues4 = viewFlagMasks3 | 17;
                            viewFlagValues10 = viewFlagValues3;
                            viewFlagMasks4 = viewFlagValues4;
                            y3 = y2;
                            break;
                        } else {
                            viewFlagValues10 = viewFlagValues3;
                            viewFlagMasks4 = viewFlagMasks3;
                            y3 = y2;
                        }
                    case 20:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        if (a.getBoolean(attr, false)) {
                            viewFlagValues3 = (viewFlagValues & (-17)) | 262145;
                            viewFlagValues4 = 262161 | viewFlagMasks;
                            viewFlagValues10 = viewFlagValues3;
                            viewFlagMasks4 = viewFlagValues4;
                            y3 = y2;
                            break;
                        }
                        break;
                    case 21:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        int visibility = a.getInt(attr, 0);
                        if (visibility != 0) {
                            viewFlagValues5 = viewFlagValues | VISIBILITY_FLAGS[visibility];
                            viewFlagMasks3 = viewFlagMasks | 12;
                            viewFlagValues10 = viewFlagValues5;
                            viewFlagMasks4 = viewFlagMasks3;
                            y3 = y2;
                            break;
                        }
                        break;
                    case 22:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        if (a.getBoolean(attr, false)) {
                            viewFlagValues6 = viewFlagValues | 2;
                            viewFlagValues7 = viewFlagMasks | 2;
                            viewFlagValues10 = viewFlagValues6;
                            viewFlagMasks4 = viewFlagValues7;
                            y3 = y2;
                            break;
                        }
                        break;
                    case 23:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        int scrollbars = a.getInt(attr, 0);
                        if (scrollbars != 0) {
                            viewFlagValues10 = viewFlagValues | scrollbars;
                            viewFlagMasks4 = viewFlagMasks | 768;
                            initializeScrollbars = true;
                            y3 = y2;
                            break;
                        }
                        break;
                    case 24:
                        y2 = y3;
                        viewFlagValues = viewFlagValues10;
                        viewFlagMasks = viewFlagMasks4;
                        break;
                    default:
                        switch (attr) {
                            case 26:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                this.mNextFocusLeftId = a.getResourceId(attr, -1);
                                break;
                            case 27:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                this.mNextFocusRightId = a.getResourceId(attr, -1);
                                break;
                            case 28:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                this.mNextFocusUpId = a.getResourceId(attr, -1);
                                break;
                            case 29:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                this.mNextFocusDownId = a.getResourceId(attr, -1);
                                break;
                            case 30:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (a.getBoolean(attr, false)) {
                                    viewFlagValues3 = viewFlagValues | 16384;
                                    viewFlagValues4 = viewFlagMasks | 16384;
                                    viewFlagValues10 = viewFlagValues3;
                                    viewFlagMasks4 = viewFlagValues4;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 31:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (a.getBoolean(attr, false)) {
                                    viewFlagMasks4 = 2097152 | viewFlagMasks;
                                    viewFlagValues10 = viewFlagValues | 2097152;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 32:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (!a.getBoolean(attr, true)) {
                                    viewFlagMasks4 = 65536 | viewFlagMasks;
                                    viewFlagValues10 = viewFlagValues | 65536;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 33:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                int cacheQuality = a.getInt(attr, 0);
                                if (cacheQuality != 0) {
                                    viewFlagValues5 = viewFlagValues | DRAWING_CACHE_QUALITY_FLAGS[cacheQuality];
                                    viewFlagMasks3 = viewFlagMasks | DRAWING_CACHE_QUALITY_MASK;
                                    viewFlagValues10 = viewFlagValues5;
                                    viewFlagMasks4 = viewFlagMasks3;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 34:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (a.getBoolean(attr, false)) {
                                    viewFlagValues6 = 4194304 | viewFlagValues;
                                    viewFlagValues7 = 4194304 | viewFlagMasks;
                                    viewFlagValues10 = viewFlagValues6;
                                    viewFlagMasks4 = viewFlagValues7;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 35:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (leftPadding3 >= 23 || (this instanceof FrameLayout)) {
                                    setForeground(a.getDrawable(attr));
                                }
                                break;
                            case 36:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                this.mMinWidth = a.getDimensionPixelSize(attr, 0);
                                break;
                            case 37:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                this.mMinHeight = a.getDimensionPixelSize(attr, 0);
                                break;
                            case 38:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (leftPadding3 >= 23 || (this instanceof FrameLayout)) {
                                    setForegroundGravity(a.getInt(attr, 0));
                                    break;
                                }
                                break;
                            case 39:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (!a.getBoolean(attr, true)) {
                                    viewFlagValues3 = (-134217729) & viewFlagValues;
                                    viewFlagValues4 = 134217728 | viewFlagMasks;
                                    viewFlagValues10 = viewFlagValues3;
                                    viewFlagMasks4 = viewFlagValues4;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 40:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (a.getBoolean(attr, false)) {
                                    viewFlagValues3 = 67108864 | viewFlagValues;
                                    viewFlagValues4 = 67108864 | viewFlagMasks;
                                    viewFlagValues10 = viewFlagValues3;
                                    viewFlagMasks4 = viewFlagValues4;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 41:
                                y2 = y3;
                                viewFlagValues2 = viewFlagValues10;
                                viewFlagMasks2 = viewFlagMasks4;
                                if (a.getBoolean(attr, false)) {
                                    setScrollContainer(true);
                                }
                                setScrollContainer = true;
                                viewFlagValues10 = viewFlagValues2;
                                viewFlagMasks4 = viewFlagMasks2;
                                y3 = y2;
                                break;
                            case 42:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (!a.getBoolean(attr, true)) {
                                    viewFlagValues3 = (-268435457) & viewFlagValues;
                                    viewFlagValues4 = 268435456 | viewFlagMasks;
                                    viewFlagValues10 = viewFlagValues3;
                                    viewFlagMasks4 = viewFlagValues4;
                                    y3 = y2;
                                    break;
                                }
                                break;
                            case 43:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                if (!context.isRestricted()) {
                                    String handlerName = a.getString(attr);
                                    if (handlerName != null) {
                                        setOnClickListener(new DeclaredOnClickListener(this, handlerName));
                                    }
                                    break;
                                } else {
                                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                                }
                            case 44:
                                y2 = y3;
                                viewFlagValues = viewFlagValues10;
                                viewFlagMasks = viewFlagMasks4;
                                setContentDescription(a.getString(attr));
                                break;
                            default:
                                switch (attr) {
                                    case 48:
                                        int overScrollMode3 = a.getInt(attr, 1);
                                        overScrollMode2 = overScrollMode3;
                                        break;
                                    case 49:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        if (a.getBoolean(attr, false)) {
                                            viewFlagValues3 = viewFlagValues | 1024;
                                            viewFlagValues4 = viewFlagMasks | 1024;
                                            viewFlagValues10 = viewFlagValues3;
                                            viewFlagMasks4 = viewFlagValues4;
                                            y3 = y2;
                                            break;
                                        }
                                        break;
                                    case 50:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setAlpha(a.getFloat(attr, 1.0f));
                                        break;
                                    case 51:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setPivotX(a.getDimension(attr, 0.0f));
                                        break;
                                    case 52:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setPivotY(a.getDimension(attr, 0.0f));
                                        break;
                                    case 53:
                                        float tx2 = a.getDimension(attr, 0.0f);
                                        transformSet = true;
                                        tx = tx2;
                                        transformSet2 = transformSet;
                                        break;
                                    case 54:
                                        float ty2 = a.getDimension(attr, 0.0f);
                                        transformSet = true;
                                        ty = ty2;
                                        transformSet2 = transformSet;
                                        break;
                                    case 55:
                                        float sx3 = a.getFloat(attr, 1.0f);
                                        transformSet = true;
                                        sx = sx3;
                                        transformSet2 = transformSet;
                                        break;
                                    case 56:
                                        float sy2 = a.getFloat(attr, 1.0f);
                                        transformSet = true;
                                        sy = sy2;
                                        transformSet2 = transformSet;
                                        break;
                                    case 57:
                                        float rotation3 = a.getFloat(attr, 0.0f);
                                        transformSet = true;
                                        rotation = rotation3;
                                        transformSet2 = transformSet;
                                        break;
                                    case 58:
                                        float rotationX3 = a.getFloat(attr, 0.0f);
                                        transformSet = true;
                                        rotationX = rotationX3;
                                        transformSet2 = transformSet;
                                        break;
                                    case 59:
                                        float rotationY3 = a.getFloat(attr, 0.0f);
                                        transformSet = true;
                                        rotationY = rotationY3;
                                        transformSet2 = transformSet;
                                        break;
                                    case 60:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        this.mVerticalScrollbarPosition = a.getInt(attr, 0);
                                        break;
                                    case 61:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        this.mNextFocusForwardId = a.getResourceId(attr, -1);
                                        break;
                                    case 62:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setLayerType(a.getInt(attr, 0), null);
                                        break;
                                    case 63:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        int fadingEdge = a.getInt(attr, 0);
                                        if (fadingEdge != 0) {
                                            viewFlagValues5 = viewFlagValues | fadingEdge;
                                            viewFlagMasks3 = viewFlagMasks | 12288;
                                            initializeFadingEdgeInternal(a);
                                            viewFlagValues10 = viewFlagValues5;
                                            viewFlagMasks4 = viewFlagMasks3;
                                            y3 = y2;
                                            break;
                                        }
                                        break;
                                    case 64:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setImportantForAccessibility(a.getInt(attr, 0));
                                        break;
                                    case 65:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        this.mPrivateFlags2 &= -449;
                                        int textDirection = a.getInt(attr, -1);
                                        if (textDirection != -1) {
                                            this.mPrivateFlags2 |= PFLAG2_TEXT_DIRECTION_FLAGS[textDirection];
                                        }
                                        break;
                                    case 66:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        this.mPrivateFlags2 &= -57345;
                                        int textAlignment = a.getInt(attr, 1);
                                        this.mPrivateFlags2 |= PFLAG2_TEXT_ALIGNMENT_FLAGS[textAlignment];
                                        break;
                                    case 67:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        this.mPrivateFlags2 &= -61;
                                        int layoutDirection = a.getInt(attr, -1);
                                        int value = layoutDirection != -1 ? LAYOUT_DIRECTION_FLAGS[layoutDirection] : 2;
                                        this.mPrivateFlags2 |= value << 2;
                                        break;
                                    case 68:
                                        y2 = y3;
                                        viewFlagValues5 = viewFlagValues10;
                                        viewFlagMasks3 = viewFlagMasks4;
                                        startPadding = a.getDimensionPixelSize(attr, Integer.MIN_VALUE);
                                        boolean startPaddingDefined2 = startPadding != Integer.MIN_VALUE;
                                        startPaddingDefined = startPaddingDefined2;
                                        viewFlagValues10 = viewFlagValues5;
                                        viewFlagMasks4 = viewFlagMasks3;
                                        y3 = y2;
                                        break;
                                    case 69:
                                        y2 = y3;
                                        viewFlagValues5 = viewFlagValues10;
                                        viewFlagMasks3 = viewFlagMasks4;
                                        endPadding = a.getDimensionPixelSize(attr, Integer.MIN_VALUE);
                                        boolean endPaddingDefined2 = endPadding != Integer.MIN_VALUE;
                                        endPaddingDefined = endPaddingDefined2;
                                        viewFlagValues10 = viewFlagValues5;
                                        viewFlagMasks4 = viewFlagMasks3;
                                        y3 = y2;
                                        break;
                                    case 70:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setLabelFor(a.getResourceId(attr, -1));
                                        break;
                                    case 71:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setAccessibilityLiveRegion(a.getInt(attr, 0));
                                        break;
                                    case 72:
                                        float tz3 = a.getDimension(attr, 0.0f);
                                        transformSet = true;
                                        tz = tz3;
                                        transformSet2 = transformSet;
                                        break;
                                    case 73:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setTransitionName(a.getString(attr));
                                        break;
                                    case 74:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setNestedScrollingEnabled(a.getBoolean(attr, false));
                                        break;
                                    case 75:
                                        float elevation3 = a.getDimension(attr, 0.0f);
                                        transformSet = true;
                                        elevation = elevation3;
                                        transformSet2 = transformSet;
                                        break;
                                    case 76:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, a.getResourceId(attr, 0)));
                                        break;
                                    case 77:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        if (this.mBackgroundTint == null) {
                                            this.mBackgroundTint = new TintInfo();
                                        }
                                        this.mBackgroundTint.mTintList = a.getColorStateList(77);
                                        this.mBackgroundTint.mHasTintList = true;
                                        break;
                                    case 78:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        if (this.mBackgroundTint == null) {
                                            this.mBackgroundTint = new TintInfo();
                                        }
                                        y2 = y3;
                                        this.mBackgroundTint.mBlendMode = Drawable.parseBlendMode(a.getInt(78, -1), null);
                                        this.mBackgroundTint.mHasTintMode = true;
                                        break;
                                    case 79:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        if (leftPadding3 >= 23 || (this instanceof FrameLayout)) {
                                            setForegroundTintList(a.getColorStateList(attr));
                                        }
                                        y2 = y3;
                                        break;
                                    case 80:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        if (leftPadding3 >= 23 || (this instanceof FrameLayout)) {
                                            setForegroundTintBlendMode(Drawable.parseBlendMode(a.getInt(attr, -1), null));
                                        }
                                        y2 = y3;
                                        break;
                                    case 81:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setOutlineProviderFromAttribute(a.getInt(81, 0));
                                        y2 = y3;
                                        break;
                                    case 82:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setAccessibilityTraversalBefore(a.getResourceId(attr, -1));
                                        y2 = y3;
                                        break;
                                    case 83:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        setAccessibilityTraversalAfter(a.getResourceId(attr, -1));
                                        y2 = y3;
                                        break;
                                    case 84:
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        int scrollIndicators = (a.getInt(attr, 0) << 8) & SCROLL_INDICATORS_PFLAG3_MASK;
                                        if (scrollIndicators != 0) {
                                            this.mPrivateFlags3 |= scrollIndicators;
                                            viewFlagValues10 = viewFlagValues;
                                            viewFlagMasks4 = viewFlagMasks;
                                            initializeScrollIndicators = true;
                                            break;
                                        }
                                        y2 = y3;
                                        break;
                                    case 85:
                                        if (!a.getBoolean(attr, false)) {
                                            viewFlagValues = viewFlagValues10;
                                            viewFlagMasks = viewFlagMasks4;
                                            y2 = y3;
                                            break;
                                        } else {
                                            viewFlagValues10 = 8388608 | viewFlagValues10;
                                            viewFlagMasks4 = 8388608 | viewFlagMasks4;
                                            break;
                                        }
                                    case 86:
                                        int resourceId = a.getResourceId(attr, 0);
                                        if (resourceId != 0) {
                                            setPointerIcon(PointerIcon.load(context.getResources(), resourceId));
                                        } else {
                                            int pointerType = a.getInt(attr, 1);
                                            if (pointerType != 1) {
                                                setPointerIcon(PointerIcon.getSystemIcon(context, pointerType));
                                            }
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 87:
                                        if (a.peekValue(attr) != null) {
                                            forceHasOverlappingRendering(a.getBoolean(attr, true));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 88:
                                        setTooltipText(a.getText(attr));
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 89:
                                        int paddingHorizontal3 = a.getDimensionPixelSize(attr, -1);
                                        this.mUserPaddingLeftInitial = paddingHorizontal3;
                                        this.mUserPaddingRightInitial = paddingHorizontal3;
                                        paddingHorizontal = paddingHorizontal3;
                                        leftPaddingDefined = true;
                                        rightPaddingDefined = true;
                                        break;
                                    case 90:
                                        int paddingVertical2 = a.getDimensionPixelSize(attr, -1);
                                        paddingVertical = paddingVertical2;
                                        break;
                                    case 91:
                                        if (a.peekValue(attr) != null) {
                                            setKeyboardNavigationCluster(a.getBoolean(attr, true));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 92:
                                        this.mNextClusterForwardId = a.getResourceId(attr, -1);
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 93:
                                        if (a.peekValue(attr) != null) {
                                            setFocusedByDefault(a.getBoolean(attr, true));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 94:
                                        if (a.peekValue(attr) != null) {
                                            String rawString = null;
                                            CharSequence[] rawHints3 = null;
                                            if (a.getType(attr) == 1) {
                                                int resId = a.getResourceId(attr, 0);
                                                try {
                                                    CharSequence[] rawHints4 = a.getTextArray(attr);
                                                    rawHints2 = rawHints4;
                                                } catch (Resources.NotFoundException e) {
                                                    rawString = getResources().getString(resId);
                                                    rawHints2 = null;
                                                }
                                                rawHints3 = rawHints2;
                                            } else {
                                                rawString = a.getString(attr);
                                            }
                                            String rawString2 = rawString;
                                            if (rawHints3 != null) {
                                                rawHints = rawHints3;
                                            } else if (rawString2 == null) {
                                                throw new IllegalArgumentException("Could not resolve autofillHints");
                                            } else {
                                                CharSequence[] rawHints5 = rawString2.split(SmsManager.REGEX_PREFIX_DELIMITER);
                                                rawHints = rawHints5;
                                            }
                                            String[] hints = new String[rawHints.length];
                                            int numHints = rawHints.length;
                                            int rawHintNum = 0;
                                            while (true) {
                                                int rawHintNum2 = rawHintNum;
                                                if (rawHintNum2 < numHints) {
                                                    hints[rawHintNum2] = rawHints[rawHintNum2].toString().trim();
                                                    rawHintNum = rawHintNum2 + 1;
                                                } else {
                                                    setAutofillHints(hints);
                                                }
                                            }
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 95:
                                        if (a.peekValue(attr) != null) {
                                            setImportantForAutofill(a.getInt(attr, 0));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 96:
                                        if (a.peekValue(attr) != null) {
                                            setDefaultFocusHighlightEnabled(a.getBoolean(attr, true));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 97:
                                        if (a.peekValue(attr) != null) {
                                            setScreenReaderFocusable(a.getBoolean(attr, false));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 98:
                                        if (a.peekValue(attr) != null) {
                                            setAccessibilityPaneTitle(a.getString(attr));
                                        }
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 99:
                                        setAccessibilityHeading(a.getBoolean(attr, false));
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 100:
                                        setOutlineSpotShadowColor(a.getColor(attr, -16777216));
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 101:
                                        setOutlineAmbientShadowColor(a.getColor(attr, -16777216));
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    case 102:
                                        this.mRenderNode.setForceDarkAllowed(a.getBoolean(attr, true));
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                    default:
                                        y2 = y3;
                                        viewFlagValues = viewFlagValues10;
                                        viewFlagMasks = viewFlagMasks4;
                                        break;
                                }
                        }
                }
                viewFlagValues11 = i + 1;
                N = N2;
            } else {
                y2 = y3;
                viewFlagValues = viewFlagValues10;
                viewFlagMasks = viewFlagMasks4;
                if (leftPadding3 >= 23 || (this instanceof FrameLayout)) {
                    if (this.mForegroundInfo == null) {
                        this.mForegroundInfo = new ForegroundInfo();
                    }
                    this.mForegroundInfo.mInsidePadding = a.getBoolean(attr, this.mForegroundInfo.mInsidePadding);
                }
            }
            viewFlagValues10 = viewFlagValues;
            viewFlagMasks4 = viewFlagMasks;
            y3 = y2;
            viewFlagValues11 = i + 1;
            N = N2;
        }
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

    /* loaded from: classes4.dex */
    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, v);
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
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, View.class)) != null) {
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
        InputEventConsistencyVerifier inputEventConsistencyVerifier;
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
        if (!InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = null;
        } else {
            inputEventConsistencyVerifier = new InputEventConsistencyVerifier(this, 0);
        }
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mSourceLayoutId = 0;
        this.mResources = null;
        this.mRenderNode = RenderNode.create(getClass().getName(), new ViewAnimationHostBridge(this));
    }

    final boolean debugDraw() {
        return DEBUG_DRAW || (this.mAttachInfo != null && this.mAttachInfo.mDebugLayout);
    }

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray<>();
        }
        return mAttributeMap;
    }

    private void retrieveExplicitStyle(Resources.Theme theme, AttributeSet attrs) {
        if (!sDebugViewAttributes) {
            return;
        }
        this.mExplicitStyle = theme.getExplicitStyle(attrs);
    }

    public final void saveAttributeDataForStyleable(Context context, int[] styleable, AttributeSet attrs, TypedArray t, int defStyleAttr, int defStyleRes) {
        if (!sDebugViewAttributes) {
            return;
        }
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

    private void saveAttributeData(AttributeSet attrs, TypedArray t) {
        int resourceId;
        String resourceName;
        int attrsCount = attrs == null ? 0 : attrs.getAttributeCount();
        int indexCount = t.getIndexCount();
        String[] attributes = new String[(attrsCount + indexCount) * 2];
        int i = 0;
        for (int i2 = 0; i2 < attrsCount; i2++) {
            attributes[i] = attrs.getAttributeName(i2);
            attributes[i + 1] = attrs.getAttributeValue(i2);
            i += 2;
        }
        Resources res = t.getResources();
        SparseArray<String> attributeMap = getAttributeMap();
        int j = 0;
        while (true) {
            int j2 = j;
            if (j2 < indexCount) {
                int index = t.getIndex(j2);
                if (t.hasValueOrEmpty(index) && (resourceId = t.getResourceId(index, 0)) != 0) {
                    String resourceName2 = attributeMap.get(resourceId);
                    if (resourceName2 == null) {
                        try {
                            resourceName = res.getResourceName(resourceId);
                        } catch (Resources.NotFoundException e) {
                            resourceName = "0x" + Integer.toHexString(resourceId);
                        }
                        resourceName2 = resourceName;
                        attributeMap.put(resourceId, resourceName2);
                    }
                    attributes[i] = resourceName2;
                    attributes[i + 1] = t.getString(index);
                    i += 2;
                }
                j = j2 + 1;
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
        if (i == 0) {
            out.append('V');
        } else if (i == 4) {
            out.append('I');
        } else if (i == 8) {
            out.append('G');
        } else {
            out.append('.');
        }
        out.append((this.mViewFlags & 1) == 1 ? 'F' : '.');
        out.append((this.mViewFlags & 32) == 0 ? android.text.format.DateFormat.DAY : '.');
        out.append((this.mViewFlags & 128) == 128 ? '.' : 'D');
        out.append((this.mViewFlags & 256) != 0 ? 'H' : '.');
        out.append((this.mViewFlags & 512) == 0 ? '.' : 'V');
        out.append((this.mViewFlags & 16384) != 0 ? 'C' : '.');
        out.append((this.mViewFlags & 2097152) != 0 ? android.text.format.DateFormat.STANDALONE_MONTH : '.');
        out.append((this.mViewFlags & 8388608) != 0 ? 'X' : '.');
        out.append(' ');
        out.append((this.mPrivateFlags & 8) != 0 ? 'R' : '.');
        out.append((this.mPrivateFlags & 2) == 0 ? '.' : 'F');
        out.append((this.mPrivateFlags & 4) != 0 ? 'S' : '.');
        if ((this.mPrivateFlags & 33554432) != 0) {
            out.append('p');
        } else {
            out.append((this.mPrivateFlags & 16384) != 0 ? 'P' : '.');
        }
        out.append((this.mPrivateFlags & 268435456) == 0 ? '.' : 'H');
        out.append((this.mPrivateFlags & 1073741824) != 0 ? android.text.format.DateFormat.CAPITAL_AM_PM : '.');
        out.append((this.mPrivateFlags & Integer.MIN_VALUE) == 0 ? '.' : 'I');
        out.append((this.mPrivateFlags & 2097152) != 0 ? 'D' : '.');
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
                int i2 = (-16777216) & id;
                if (i2 == 16777216) {
                    pkgname = "android";
                } else if (i2 == 2130706432) {
                    pkgname = "app";
                } else {
                    try {
                        pkgname = r.getResourcePackageName(id);
                    } catch (Resources.NotFoundException e) {
                    }
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

    protected void initializeFadingEdge(TypedArray a) {
        TypedArray arr = this.mContext.obtainStyledAttributes(C3132R.styleable.View);
        initializeFadingEdgeInternal(arr);
        arr.recycle();
    }

    protected void initializeFadingEdgeInternal(TypedArray a) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = a.getDimensionPixelSize(25, ViewConfiguration.get(this.mContext).getScaledFadingEdgeLength());
    }

    public int getVerticalFadingEdgeLength() {
        ScrollabilityCache cache;
        if (isVerticalFadingEdgeEnabled() && (cache = this.mScrollCache) != null) {
            return cache.fadingEdgeLength;
        }
        return 0;
    }

    public void setFadingEdgeLength(int length) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = length;
    }

    public int getHorizontalFadingEdgeLength() {
        ScrollabilityCache cache;
        if (isHorizontalFadingEdgeEnabled() && (cache = this.mScrollCache) != null) {
            return cache.fadingEdgeLength;
        }
        return 0;
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

    /* JADX INFO: Access modifiers changed from: protected */
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

    protected void initializeScrollbars(TypedArray a) {
        TypedArray arr = this.mContext.obtainStyledAttributes(C3132R.styleable.View);
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

    @UnsupportedAppUsage
    protected void initializeScrollbarsInternal(TypedArray a) {
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
        boolean alwaysDraw = a.getBoolean(6, false);
        if (alwaysDraw) {
            scrollabilityCache.scrollBar.setAlwaysDrawHorizontalTrack(true);
        }
        Drawable track = a.getDrawable(5);
        scrollabilityCache.scrollBar.setVerticalTrackDrawable(track);
        Drawable thumb2 = a.getDrawable(3);
        if (thumb2 != null) {
            scrollabilityCache.scrollBar.setVerticalThumbDrawable(thumb2);
        }
        boolean alwaysDraw2 = a.getBoolean(7, false);
        if (alwaysDraw2) {
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
            this.mScrollIndicatorDrawable = this.mContext.getDrawable(C3132R.C3133drawable.scroll_indicator_material);
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

    boolean isOnScrollbar(float x, float y) {
        if (this.mScrollCache == null) {
            return false;
        }
        float x2 = x + getScrollX();
        float y2 = y + getScrollY();
        boolean canScrollVertically = computeVerticalScrollRange() > computeVerticalScrollExtent();
        if (isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && canScrollVertically) {
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(null, touchBounds);
            if (touchBounds.contains((int) x2, (int) y2)) {
                return true;
            }
        }
        boolean canScrollHorizontally = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        if (isHorizontalScrollBarEnabled() && canScrollHorizontally) {
            Rect touchBounds2 = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(null, touchBounds2);
            if (touchBounds2.contains((int) x2, (int) y2)) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    boolean isOnScrollbarThumb(float x, float y) {
        return isOnVerticalScrollbarThumb(x, y) || isOnHorizontalScrollbarThumb(x, y);
    }

    private boolean isOnVerticalScrollbarThumb(float x, float y) {
        int range;
        int extent;
        if (this.mScrollCache != null && isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && (range = computeVerticalScrollRange()) > (extent = computeVerticalScrollExtent())) {
            float x2 = x + getScrollX();
            float y2 = y + getScrollY();
            Rect bounds = this.mScrollCache.mScrollBarBounds;
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(bounds, touchBounds);
            int offset = computeVerticalScrollOffset();
            int thumbLength = ScrollBarUtils.getThumbLength(bounds.height(), bounds.width(), extent, range);
            int thumbOffset = ScrollBarUtils.getThumbOffset(bounds.height(), thumbLength, extent, range, offset);
            int thumbTop = bounds.top + thumbOffset;
            int adjust = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (x2 >= touchBounds.left && x2 <= touchBounds.right && y2 >= thumbTop - adjust && y2 <= thumbTop + thumbLength + adjust) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnHorizontalScrollbarThumb(float x, float y) {
        int range;
        int extent;
        if (this.mScrollCache != null && isHorizontalScrollBarEnabled() && (range = computeHorizontalScrollRange()) > (extent = computeHorizontalScrollExtent())) {
            float x2 = x + getScrollX();
            float y2 = y + getScrollY();
            Rect bounds = this.mScrollCache.mScrollBarBounds;
            Rect touchBounds = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(bounds, touchBounds);
            int offset = computeHorizontalScrollOffset();
            int thumbLength = ScrollBarUtils.getThumbLength(bounds.width(), bounds.height(), extent, range);
            int thumbOffset = ScrollBarUtils.getThumbOffset(bounds.width(), thumbLength, extent, range, offset);
            int thumbLeft = bounds.left + thumbOffset;
            int adjust = Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (x2 >= thumbLeft - adjust && x2 <= thumbLeft + thumbLength + adjust && y2 >= touchBounds.top && y2 <= touchBounds.bottom) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    boolean isDraggingScrollBar() {
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

    @UnsupportedAppUsage
    ListenerInfo getListenerInfo() {
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
            li.mOnLayoutChangeListeners = new ArrayList();
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
            li.mOnAttachStateChangeListeners = new CopyOnWriteArrayList();
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
        if (this.mAttachInfo != null) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                if (this.mFrameMetricsObservers == null) {
                    this.mFrameMetricsObservers = new ArrayList<>();
                }
                FrameMetricsObserver fmo = new FrameMetricsObserver(window, handler.getLooper(), listener);
                this.mFrameMetricsObservers.add(fmo);
                this.mAttachInfo.mThreadedRenderer.addFrameMetricsObserver(fmo);
                return;
            }
            Log.m64w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
            return;
        }
        if (this.mFrameMetricsObservers == null) {
            this.mFrameMetricsObservers = new ArrayList<>();
        }
        this.mFrameMetricsObservers.add(new FrameMetricsObserver(window, handler.getLooper(), listener));
    }

    public void removeFrameMetricsListener(Window.OnFrameMetricsAvailableListener listener) {
        ThreadedRenderer renderer = getThreadedRenderer();
        FrameMetricsObserver fmo = findFrameMetricsObserver(listener);
        if (fmo == null) {
            throw new IllegalArgumentException("attempt to remove OnFrameMetricsAvailableListener that was never added");
        }
        if (this.mFrameMetricsObservers != null) {
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
                    FrameMetricsObserver fmo = it.next();
                    renderer.addFrameMetricsObserver(fmo);
                }
                return;
            }
            Log.m64w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
        }
    }

    private FrameMetricsObserver findFrameMetricsObserver(Window.OnFrameMetricsAvailableListener listener) {
        if (this.mFrameMetricsObservers != null) {
            for (int i = 0; i < this.mFrameMetricsObservers.size(); i++) {
                FrameMetricsObserver observer = this.mFrameMetricsObservers.get(i);
                if (observer.mListener == listener) {
                    return observer;
                }
            }
            return null;
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
                this.mPrivateFlags = (-536870913) & this.mPrivateFlags;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performClickInternal() {
        notifyAutofillManagerOnClick();
        return performClick();
    }

    public boolean performClick() {
        notifyAutofillManagerOnClick();
        ListenerInfo li = this.mListenerInfo;
        boolean result = false;
        if (li != null && li.mOnClickListener != null) {
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
        if (li != null && li.mOnClickListener != null) {
            li.mOnClickListener.onClick(this);
            return true;
        }
        return false;
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
        if (li != null && li.mOnLongClickListener != null) {
            handled = li.mOnLongClickListener.onLongClick(this);
        }
        if (!handled) {
            boolean isAnchored = (Float.isNaN(x) || Float.isNaN(y)) ? false : true;
            handled = isAnchored ? showContextMenu(x, y) : showContextMenu();
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
        if (li != null && li.mOnContextClickListener != null) {
            handled = li.mOnContextClickListener.onContextClick(this);
        }
        if (handled) {
            performHapticFeedback(6);
        }
        return handled;
    }

    protected boolean performButtonActionOnTouchDown(MotionEvent event) {
        if (event.isFromSource(8194) && (event.getButtonState() & 2) != 0) {
            showContextMenu(event.getX(), event.getY());
            this.mPrivateFlags |= 67108864;
            return true;
        }
        return false;
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
        getContext().startActivityForResult(this.mStartActivityRequestWho, intent, requestCode, null);
    }

    public boolean dispatchActivityResult(String who, int requestCode, int resultCode, Intent data) {
        if (this.mStartActivityRequestWho != null && this.mStartActivityRequestWho.equals(who)) {
            onActivityResult(requestCode, resultCode, data);
            this.mStartActivityRequestWho = null;
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void setOnKeyListener(OnKeyListener l) {
        getListenerInfo().mOnKeyListener = l;
    }

    public void setOnTouchListener(OnTouchListener l) {
        getListenerInfo().mOnTouchListener = l;
    }

    public void setOnGenericMotionListener(OnGenericMotionListener l) {
        getListenerInfo().mOnGenericMotionListener = l;
    }

    public void setOnHoverListener(OnHoverListener l) {
        getListenerInfo().mOnHoverListener = l;
    }

    public void setOnDragListener(OnDragListener l) {
        getListenerInfo().mOnDragListener = l;
    }

    void handleFocusGainInternal(int direction, Rect previouslyFocusedRect) {
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
            position.offset(child.mLeft - child.getScrollX(), child.mTop - child.getScrollY());
            View child2 = parent;
            child = child2;
            parent = child.getParent();
        }
        return scrolled;
    }

    public void clearFocus() {
        boolean refocus = sAlwaysAssignFocus || !isInTouchMode();
        clearFocusInternal(null, true, refocus);
    }

    void clearFocusInternal(View focused, boolean propagate, boolean refocus) {
        if ((this.mPrivateFlags & 2) != 0) {
            this.mPrivateFlags &= -3;
            clearParentsWantFocus();
            if (propagate && this.mParent != null) {
                this.mParent.clearChildFocus(this);
            }
            onFocusChanged(false, 0, null);
            refreshDrawableState();
            if (propagate) {
                if (!refocus || !rootViewRequestFocus()) {
                    notifyGlobalFocusCleared(this);
                }
            }
        }
    }

    void notifyGlobalFocusCleared(View oldFocus) {
        if (oldFocus != null && this.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(oldFocus, null);
        }
    }

    boolean rootViewRequestFocus() {
        View root = getRootView();
        return root != null && root.requestFocus();
    }

    void unFocus(View focused) {
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

    boolean hasFocusable(boolean allowAutoFocus, boolean dispatchExplicit) {
        if (!isFocusableInTouchMode()) {
            for (ViewParent p = this.mParent; p instanceof ViewGroup; p = p.getParent()) {
                ViewGroup g = (ViewGroup) p;
                if (g.shouldBlockFocusForTouchscreen()) {
                    return false;
                }
            }
        }
        if ((this.mViewFlags & 12) == 0 && (this.mViewFlags & 32) == 0) {
            return (allowAutoFocus || getFocusable() != 16) && isFocusable();
        }
        return false;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
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
        if (li != null && li.mOnFocusChangeListener != null) {
            li.mOnFocusChangeListener.onFocusChange(this, gainFocus);
        }
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mKeyDispatchState.reset(this);
        }
        notifyEnterOrExitForAutoFillIfNeeded(gainFocus);
    }

    private void notifyFocusChangeToInputMethodManager(boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
        if (imm == null) {
            return;
        }
        if (hasFocus) {
            imm.focusIn(this);
        } else {
            imm.focusOut(this);
        }
    }

    public void notifyEnterOrExitForAutoFillIfNeeded(boolean enter) {
        AutofillManager afm;
        if (canNotifyAutofillEnterExitEvent() && (afm = getAutofillManager()) != null) {
            if (enter && isFocused()) {
                if (!isLaidOut()) {
                    this.mPrivateFlags3 |= 134217728;
                } else if (isVisibleToUser()) {
                    afm.notifyViewEntered(this);
                }
            } else if (!enter && !isFocused()) {
                afm.notifyViewExited(this);
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

    @Override // android.view.accessibility.AccessibilityEventSource
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
            event.setContentDescription(null);
            this.mParent.requestSendAccessibilityEvent(this, event);
        }
    }

    public void sendAccessibilityEventInternal(int eventType) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            sendAccessibilityEventUnchecked(AccessibilityEvent.obtain(eventType));
        }
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, event);
        } else {
            sendAccessibilityEventUncheckedInternal(event);
        }
    }

    public void sendAccessibilityEventUncheckedInternal(AccessibilityEvent event) {
        boolean isWindowDisappearedEvent = false;
        boolean isWindowStateChanged = event.getEventType() == 32;
        if (isWindowStateChanged && (32 & event.getContentChangeTypes()) != 0) {
            isWindowDisappearedEvent = true;
        }
        if (!isShown() && !isWindowDisappearedEvent) {
            return;
        }
        onInitializeAccessibilityEvent(event);
        if ((event.getEventType() & POPULATING_ACCESSIBILITY_EVENT_TYPES) != 0) {
            dispatchPopulateAccessibilityEvent(event);
        }
        ViewParent parent = getParent();
        if (parent != null) {
            getParent().requestSendAccessibilityEvent(this, event);
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
        if (this.mAttachInfo == null) {
            return;
        }
        RectF position = this.mAttachInfo.mTmpTransformRect;
        position.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToScreenCoords(position, clipToParent);
        outRect.set(Math.round(position.left), Math.round(position.top), Math.round(position.right), Math.round(position.bottom));
    }

    public void mapRectFromViewToScreenCoords(RectF rect, boolean clipToParent) {
        if (!hasIdentityMatrix()) {
            getMatrix().mapRect(rect);
        }
        rect.offset(this.mLeft, this.mTop);
        ViewParent parent = this.mParent;
        while (parent instanceof View) {
            View parentView = (View) parent;
            rect.offset(-parentView.mScrollX, -parentView.mScrollY);
            if (clipToParent) {
                rect.left = Math.max(rect.left, 0.0f);
                rect.top = Math.max(rect.top, 0.0f);
                rect.right = Math.min(rect.right, parentView.getWidth());
                rect.bottom = Math.min(rect.bottom, parentView.getHeight());
            }
            if (!parentView.hasIdentityMatrix()) {
                parentView.getMatrix().mapRect(rect);
            }
            rect.offset(parentView.mLeft, parentView.mTop);
            parent = parentView.mParent;
        }
        if (parent instanceof ViewRootImpl) {
            ViewRootImpl viewRootImpl = (ViewRootImpl) parent;
            rect.offset(0.0f, -viewRootImpl.mCurScrollY);
        }
        rect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
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

    protected void onProvideStructure(ViewStructure structure, int viewFor, int flags) {
        String type;
        String pkg;
        int id = this.mID;
        String entry = null;
        if (id != -1 && !isViewIdGenerated(id)) {
            try {
                Resources res = getResources();
                String entry2 = res.getResourceEntryName(id);
                type = res.getResourceTypeName(id);
                pkg = res.getResourcePackageName(id);
                entry = entry2;
            } catch (Resources.NotFoundException e) {
                type = null;
                pkg = null;
            }
            structure.setId(id, pkg, type, entry);
        } else {
            structure.setId(id, null, null, null);
        }
        if (viewFor == 1 || viewFor == 2) {
            int autofillType = getAutofillType();
            if (autofillType != 0) {
                structure.setAutofillType(autofillType);
                structure.setAutofillHints(getAutofillHints());
                structure.setAutofillValue(getAutofillValue());
            }
            structure.setImportantForAutofill(getImportantForAutofill());
        }
        int ignoredParentLeft = 0;
        int ignoredParentTop = 0;
        if (viewFor == 1 && (flags & 1) == 0) {
            View parentGroup = null;
            ViewParent viewParent = getParent();
            if (viewParent instanceof View) {
                parentGroup = (View) viewParent;
            }
            while (parentGroup != null && !parentGroup.isImportantForAutofill()) {
                ignoredParentLeft += parentGroup.mLeft;
                ignoredParentTop += parentGroup.mTop;
                ViewParent viewParent2 = parentGroup.getParent();
                if (!(viewParent2 instanceof View)) {
                    break;
                }
                parentGroup = (View) viewParent2;
            }
        }
        structure.setDimens(ignoredParentLeft + this.mLeft, ignoredParentTop + this.mTop, this.mScrollX, this.mScrollY, this.mRight - this.mLeft, this.mBottom - this.mTop);
        if (viewFor == 0) {
            if (!hasIdentityMatrix()) {
                structure.setTransformation(getMatrix());
            }
            structure.setElevation(getZ());
        }
        structure.setVisibility(getVisibility());
        structure.setEnabled(isEnabled());
        if (isClickable()) {
            structure.setClickable(true);
        }
        if (isFocusable()) {
            structure.setFocusable(true);
        }
        if (isFocused()) {
            structure.setFocused(true);
        }
        if (isAccessibilityFocused()) {
            structure.setAccessibilityFocused(true);
        }
        if (isSelected()) {
            structure.setSelected(true);
        }
        if (isActivated()) {
            structure.setActivated(true);
        }
        if (isLongClickable()) {
            structure.setLongClickable(true);
        }
        if (this instanceof Checkable) {
            structure.setCheckable(true);
            if (((Checkable) this).isChecked()) {
                structure.setChecked(true);
            }
        }
        if (isOpaque()) {
            structure.setOpaque(true);
        }
        if (isContextClickable()) {
            structure.setContextClickable(true);
        }
        structure.setClassName(getAccessibilityClassName().toString());
        structure.setContentDescription(getContentDescription());
    }

    public void onProvideVirtualStructure(ViewStructure structure) {
        onProvideVirtualStructureCompat(structure, false);
    }

    private void onProvideVirtualStructureCompat(ViewStructure structure, boolean forAutofill) {
        AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
        if (provider != null) {
            if (forAutofill && Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                Log.m66v(AUTOFILL_LOG_TAG, "onProvideVirtualStructureCompat() for " + this);
            }
            AccessibilityNodeInfo info = createAccessibilityNodeInfo();
            structure.setChildCount(1);
            ViewStructure root = structure.newChild(0);
            populateVirtualStructure(root, provider, info, forAutofill);
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
        if (!this.mContext.isAutofillCompatibilityEnabled() || (provider = getAccessibilityNodeProvider()) == null) {
            return;
        }
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

    public final AutofillId getAutofillId() {
        if (this.mAutofillId == null) {
            this.mAutofillId = new AutofillId(getAutofillViewId());
        }
        return this.mAutofillId;
    }

    public void setAutofillId(AutofillId id) {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            Log.m66v(AUTOFILL_LOG_TAG, "setAutofill(): from " + this.mAutofillId + " to " + id);
        }
        if (isAttachedToWindow()) {
            throw new IllegalStateException("Cannot set autofill id when view is attached");
        }
        if (id != null && !id.isNonVirtual()) {
            throw new IllegalStateException("Cannot set autofill id assigned to virtual views");
        }
        if (id == null && (this.mPrivateFlags3 & 1073741824) == 0) {
            return;
        }
        this.mAutofillId = id;
        if (id != null) {
            this.mAutofillViewId = id.getViewId();
            this.mPrivateFlags3 = 1073741824 | this.mPrivateFlags3;
            return;
        }
        this.mAutofillViewId = -1;
        this.mPrivateFlags3 &= -1073741825;
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

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, m46to = "auto"), @ViewDebug.IntToString(from = 1, m46to = "yes"), @ViewDebug.IntToString(from = 2, m46to = "no"), @ViewDebug.IntToString(from = 4, m46to = "yesExcludeDescendants"), @ViewDebug.IntToString(from = 8, m46to = "noExcludeDescendants")})
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
                    Log.m66v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because parent " + parent + "'s importance is " + parentImportance);
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
                Log.m66v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because its importance is " + importance);
            }
            return false;
        } else if (importance != 0) {
            Log.m64w(AUTOFILL_LOG_TAG, "invalid autofill importance (" + importance + " on view " + this);
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
                if (entry != null && pkg != null && pkg.equals(this.mContext.getPackageName())) {
                    return true;
                }
            }
            return getAutofillHints() != null;
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
        if (session == null) {
            ContentCaptureManager ccm = (ContentCaptureManager) this.mContext.getSystemService(ContentCaptureManager.class);
            if (ccm == null) {
                return null;
            }
            return ccm.getMainContentCaptureSession();
        }
        return session;
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
        return getAutofillViewId() > 1073741823;
    }

    public boolean canNotifyAutofillEnterExitEvent() {
        return isAutofillable() && isAttachedToWindow();
    }

    private void populateVirtualStructure(ViewStructure structure, AccessibilityNodeProvider provider, AccessibilityNodeInfo info, boolean forAutofill) {
        structure.setId(AccessibilityNodeInfo.getVirtualDescendantId(info.getSourceNodeId()), null, null, info.getViewIdResourceName());
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
        structure.setClassName(cname != null ? cname.toString() : null);
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
                    Log.m70e(VIEW_LOG_TAG, "Virtual view pointing to its host. Ignoring");
                } else {
                    AccessibilityNodeInfo cinfo = provider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(info.getChildId(i)));
                    ViewStructure child = structure.newChild(i);
                    populateVirtualStructure(child, provider, cinfo, forAutofill);
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
        if (this.mAttachInfo == null) {
            return;
        }
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
                    String viewId = getResources().getResourceName(this.mID);
                    info.setViewIdResourceName(viewId);
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
        if (this.mTooltipInfo != null && this.mTooltipInfo.mTooltipText != null) {
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

    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
    }

    private void populateAccessibilityNodeInfoDrawingOrderInParent(AccessibilityNodeInfo info) {
        if ((this.mPrivateFlags & 16) == 0) {
            info.setDrawingOrder(0);
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
                    for (int drawingOrderInParent3 = 0; drawingOrderInParent3 < childDrawIndex; drawingOrderInParent3++) {
                        drawingOrderInParent2 += numViewsForAccessibility(preorderedList.get(drawingOrderInParent3));
                    }
                    drawingOrderInParent = drawingOrderInParent2;
                } else {
                    int childIndex = parentGroup.indexOfChild(viewAtDrawingLevel);
                    boolean customOrder = parentGroup.isChildrenDrawingOrderEnabled();
                    int childDrawIndex2 = (childIndex < 0 || !customOrder) ? childIndex : parentGroup.getChildDrawingOrder(childCount, childIndex);
                    int numChildrenToIterate = customOrder ? childCount : childDrawIndex2;
                    if (childDrawIndex2 != 0) {
                        int drawingOrderInParent4 = drawingOrderInParent;
                        for (int drawingOrderInParent5 = 0; drawingOrderInParent5 < numChildrenToIterate; drawingOrderInParent5++) {
                            int otherDrawIndex = customOrder ? parentGroup.getChildDrawingOrder(childCount, drawingOrderInParent5) : drawingOrderInParent5;
                            if (otherDrawIndex < childDrawIndex2) {
                                drawingOrderInParent4 += numViewsForAccessibility(parentGroup.getChildAt(drawingOrderInParent5));
                            }
                        }
                        drawingOrderInParent = drawingOrderInParent4;
                    }
                }
            }
            viewAtDrawingLevel = (View) currentParent;
        }
        info.setDrawingOrder(drawingOrderInParent);
    }

    private static int numViewsForAccessibility(View view) {
        if (view != null) {
            if (view.includeForAccessibility()) {
                return 1;
            }
            if (view instanceof ViewGroup) {
                return ((ViewGroup) view).getNumChildrenForAccessibility();
            }
            return 0;
        }
        return 0;
    }

    private View findLabelForView(View view, int labeledId) {
        if (this.mMatchLabelForPredicate == null) {
            this.mMatchLabelForPredicate = new MatchLabelForPredicate();
        }
        this.mMatchLabelForPredicate.mLabeledId = labeledId;
        return findViewByPredicateInsideOut(view, this.mMatchLabelForPredicate);
    }

    public boolean isVisibleToUserForAutofill(int virtualId) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            AccessibilityNodeProvider provider = getAccessibilityNodeProvider();
            if (provider != null) {
                AccessibilityNodeInfo node = provider.createAccessibilityNodeInfo(virtualId);
                if (node != null) {
                    return node.isVisibleToUser();
                }
                return false;
            }
            Log.m64w(VIEW_LOG_TAG, "isVisibleToUserForAutofill(" + virtualId + "): no provider");
            return false;
        }
        return true;
    }

    @UnsupportedAppUsage
    public boolean isVisibleToUser() {
        return isVisibleToUser(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UnsupportedAppUsage
    protected boolean isVisibleToUser(Rect boundInView) {
        if (this.mAttachInfo == null || this.mAttachInfo.mWindowVisibility != 0) {
            return false;
        }
        Object current = this;
        while (current instanceof View) {
            View view = (View) current;
            if (view.getAlpha() <= 0.0f || view.getTransitionAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            Object current2 = view.mParent;
            current = current2;
        }
        Rect visibleRect = this.mAttachInfo.mTmpInvalRect;
        Point offset = this.mAttachInfo.mPoint;
        if (getGlobalVisibleRect(visibleRect, offset)) {
            if (boundInView != null) {
                visibleRect.offset(-offset.f59x, -offset.f60y);
                return boundInView.intersect(visibleRect);
            }
            return true;
        }
        return false;
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

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
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
        boolean nonEmptyDesc = contentDescription != null && contentDescription.length() > 0;
        if (nonEmptyDesc && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
            notifySubtreeAccessibilityStateChangedIfNeeded();
            return;
        }
        notifyViewAccessibilityStateChangedIfNeeded(4);
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalBefore(int beforeId) {
        if (this.mAccessibilityTraversalBeforeId == beforeId) {
            return;
        }
        this.mAccessibilityTraversalBeforeId = beforeId;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalBefore() {
        return this.mAccessibilityTraversalBeforeId;
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalAfter(int afterId) {
        if (this.mAccessibilityTraversalAfterId == afterId) {
            return;
        }
        this.mAccessibilityTraversalAfterId = afterId;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalAfter() {
        return this.mAccessibilityTraversalAfterId;
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE)
    public int getLabelFor() {
        return this.mLabelForId;
    }

    @RemotableViewMethod
    public void setLabelFor(int id) {
        if (this.mLabelForId == id) {
            return;
        }
        this.mLabelForId = id;
        if (this.mLabelForId != -1 && this.mID == -1) {
            this.mID = generateViewId();
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    @UnsupportedAppUsage
    protected void onFocusLost() {
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
        if (!isScrollContainer) {
            if ((1048576 & this.mPrivateFlags) != 0) {
                this.mAttachInfo.mScrollContainers.remove(this);
            }
            this.mPrivateFlags &= -1572865;
            return;
        }
        if (this.mAttachInfo != null && (this.mPrivateFlags & 1048576) == 0) {
            this.mAttachInfo.mScrollContainers.add(this);
            this.mPrivateFlags = 1048576 | this.mPrivateFlags;
        }
        this.mPrivateFlags |= 524288;
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

    @Deprecated
    protected boolean fitSystemWindows(Rect insets) {
        if ((this.mPrivateFlags3 & 32) == 0) {
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
        return fitSystemWindowsInt(insets);
    }

    private boolean fitSystemWindowsInt(Rect insets) {
        if ((this.mViewFlags & 2) == 2) {
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
        return false;
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
            return onApplyWindowInsets(insets);
        } finally {
            this.mPrivateFlags3 &= -33;
        }
    }

    public void setWindowInsetsAnimationListener(WindowInsetsAnimationListener listener) {
        getListenerInfo().mWindowInsetsAnimationListener = listener;
    }

    void dispatchWindowInsetsAnimationStarted(WindowInsetsAnimationListener.InsetsAnimation animation) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationListener != null) {
            this.mListenerInfo.mWindowInsetsAnimationListener.onStarted(animation);
        }
    }

    WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets insets) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationListener != null) {
            return this.mListenerInfo.mWindowInsetsAnimationListener.onProgress(insets);
        }
        return insets;
    }

    void dispatchWindowInsetsAnimationFinished(WindowInsetsAnimationListener.InsetsAnimation animation) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationListener != null) {
            this.mListenerInfo.mWindowInsetsAnimationListener.onFinished(animation);
        }
    }

    public void setSystemGestureExclusionRects(List<Rect> rects) {
        if (rects.isEmpty() && this.mListenerInfo == null) {
            return;
        }
        ListenerInfo info = getListenerInfo();
        if (rects.isEmpty()) {
            info.mSystemGestureExclusionRects = null;
            if (info.mPositionUpdateListener != null) {
                this.mRenderNode.removePositionUpdateListener(info.mPositionUpdateListener);
            }
        } else {
            info.mSystemGestureExclusionRects = rects;
            if (info.mPositionUpdateListener == null) {
                info.mPositionUpdateListener = new RenderNode.PositionUpdateListener() { // from class: android.view.View.1
                    @Override // android.graphics.RenderNode.PositionUpdateListener
                    public void positionChanged(long n, int l, int t, int r, int b) {
                        View.this.postUpdateSystemGestureExclusionRects();
                    }

                    @Override // android.graphics.RenderNode.PositionUpdateListener
                    public void positionLost(long frameNumber) {
                        View.this.postUpdateSystemGestureExclusionRects();
                    }
                };
                this.mRenderNode.addPositionUpdateListener(info.mPositionUpdateListener);
            }
        }
        postUpdateSystemGestureExclusionRects();
    }

    void postUpdateSystemGestureExclusionRects() {
        Handler h = getHandler();
        if (h != null) {
            h.postAtFrontOfQueue(new Runnable() { // from class: android.view.-$$Lambda$WlJa6OPA72p3gYtA3nVKC7Z1tGY
                @Override // java.lang.Runnable
                public final void run() {
                    View.this.updateSystemGestureExclusionRects();
                }
            });
        }
    }

    void updateSystemGestureExclusionRects() {
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewRootImpl.updateSystemGestureExclusionRectsForView(this);
        }
    }

    public List<Rect> getSystemGestureExclusionRects() {
        List<Rect> list;
        ListenerInfo info = this.mListenerInfo;
        if (info != null && (list = info.mSystemGestureExclusionRects) != null) {
            return list;
        }
        return Collections.emptyList();
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

    @UnsupportedAppUsage
    @Deprecated
    protected boolean computeFitSystemWindows(Rect inoutInsets, Rect outLocalInsets) {
        WindowInsets innerInsets = computeSystemWindowInsets(new WindowInsets(inoutInsets), outLocalInsets);
        inoutInsets.set(innerInsets.getSystemWindowInsetsAsRect());
        return innerInsets.isSystemWindowInsetsConsumed();
    }

    public WindowInsets computeSystemWindowInsets(WindowInsets in, Rect outLocalInsets) {
        if ((this.mViewFlags & 2048) == 0 || this.mAttachInfo == null || ((this.mAttachInfo.mSystemUiVisibility & 1536) == 0 && !this.mAttachInfo.mOverscanRequested)) {
            Rect overscan = in.getSystemWindowInsetsAsRect();
            outLocalInsets.set(overscan);
            return in.consumeSystemWindowInsets().inset(outLocalInsets);
        }
        Rect overscan2 = this.mAttachInfo.mOverscanInsets;
        outLocalInsets.set(overscan2);
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

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, m46to = "VISIBLE"), @ViewDebug.IntToString(from = 4, m46to = "INVISIBLE"), @ViewDebug.IntToString(from = 8, m46to = "GONE")})
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
        if (enabled == isEnabled()) {
            return;
        }
        setFlags(enabled ? 0 : 32, 32);
        refreshDrawableState();
        invalidate(true);
        if (!enabled) {
            cancelPendingInputEvents();
        }
    }

    public void setFocusable(boolean focusable) {
        setFocusable(focusable ? 1 : 0);
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
        boolean wasChanged = isAutofilled != isAutofilled();
        if (wasChanged) {
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

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = 0, m46to = "LTR"), @ViewDebug.IntToString(from = 1, m46to = "RTL"), @ViewDebug.IntToString(from = 2, m46to = "INHERIT"), @ViewDebug.IntToString(from = 3, m46to = "LOCALE")})
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

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, mapping = {@ViewDebug.IntToString(from = 0, m46to = "RESOLVED_DIRECTION_LTR"), @ViewDebug.IntToString(from = 1, m46to = "RESOLVED_DIRECTION_RTL")})
    public int getLayoutDirection() {
        int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
        if (targetSdkVersion < 17) {
            this.mPrivateFlags2 |= 32;
            return 0;
        } else if ((this.mPrivateFlags2 & 16) != 16) {
            return 0;
        } else {
            return 1;
        }
    }

    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public boolean isLayoutRtl() {
        return getLayoutDirection() == 1;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public boolean hasTransientState() {
        return (this.mPrivateFlags2 & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public void setHasTransientState(boolean hasTransientState) {
        boolean oldHasTransientState = hasTransientState();
        this.mTransientStateCount = hasTransientState ? this.mTransientStateCount + 1 : this.mTransientStateCount - 1;
        if (this.mTransientStateCount < 0) {
            this.mTransientStateCount = 0;
            Log.m70e(VIEW_LOG_TAG, "hasTransientState decremented below 0: unmatched pair of setHasTransientState calls");
        } else if ((hasTransientState && this.mTransientStateCount == 1) || (!hasTransientState && this.mTransientStateCount == 0)) {
            this.mPrivateFlags2 = (this.mPrivateFlags2 & Integer.MAX_VALUE) | (hasTransientState ? Integer.MIN_VALUE : 0);
            boolean newHasTransientState = hasTransientState();
            if (this.mParent != null && newHasTransientState != oldHasTransientState) {
                try {
                    this.mParent.childHasTransientStateChanged(this, newHasTransientState);
                } catch (AbstractMethodError e) {
                    Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
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

    boolean isLayoutValid() {
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

    /* JADX INFO: Access modifiers changed from: private */
    public void setPressed(boolean pressed, float x, float y) {
        if (pressed) {
            drawableHotspotChanged(x, y);
        }
        setPressed(pressed);
    }

    public void setPressed(boolean pressed) {
        boolean needsRefresh = pressed != ((this.mPrivateFlags & 16384) == 16384);
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

    protected void dispatchSetPressed(boolean pressed) {
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

    @ViewDebug.ExportedProperty(category = "focus", mapping = {@ViewDebug.IntToString(from = 0, m46to = "NOT_FOCUSABLE"), @ViewDebug.IntToString(from = 1, m46to = "FOCUSABLE"), @ViewDebug.IntToString(from = 16, m46to = "FOCUSABLE_AUTO")})
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

    View findKeyboardNavigationCluster() {
        if (this.mParent instanceof View) {
            View cluster = ((View) this.mParent).findKeyboardNavigationCluster();
            if (cluster != null) {
                return cluster;
            }
            if (isKeyboardNavigationCluster()) {
                return this;
            }
            return null;
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
        if (cluster == this) {
            return;
        }
        ViewParent parent = this.mParent;
        View child = this;
        for (ViewParent parent2 = parent; parent2 instanceof ViewGroup; parent2 = parent2.getParent()) {
            ((ViewGroup) parent2).mFocusedInCluster = child;
            if (parent2 != cluster) {
                child = (View) parent2;
            } else {
                return;
            }
        }
    }

    private void updateFocusedInCluster(View oldFocus, int direction) {
        if (oldFocus != null) {
            View oldCluster = oldFocus.findKeyboardNavigationCluster();
            View cluster = findKeyboardNavigationCluster();
            if (oldCluster != cluster) {
                oldFocus.setFocusedInCluster(oldCluster);
                if (!(oldFocus.mParent instanceof ViewGroup)) {
                    return;
                }
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
        if (isFocusedByDefault == ((this.mPrivateFlags3 & 262144) != 0)) {
            return;
        }
        if (isFocusedByDefault) {
            this.mPrivateFlags3 |= 262144;
        } else {
            this.mPrivateFlags3 &= -262145;
        }
        if (this.mParent instanceof ViewGroup) {
            if (isFocusedByDefault) {
                ((ViewGroup) this.mParent).setDefaultFocus(this);
            } else {
                ((ViewGroup) this.mParent).clearDefaultFocus(this);
            }
        }
    }

    boolean hasDefaultFocus() {
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

    View findUserSetNextFocus(View root, int direction) {
        if (direction == 17) {
            if (this.mNextFocusLeftId == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(root, this.mNextFocusLeftId);
        } else if (direction == 33) {
            if (this.mNextFocusUpId == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(root, this.mNextFocusUpId);
        } else if (direction == 66) {
            if (this.mNextFocusRightId == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(root, this.mNextFocusRightId);
        } else if (direction == 130) {
            int id = this.mNextFocusDownId;
            if (id == -1) {
                return null;
            }
            return findViewInsideOutShouldExist(root, this.mNextFocusDownId);
        } else {
            switch (direction) {
                case 1:
                    if (this.mID == -1) {
                        return null;
                    }
                    final int id2 = this.mID;
                    return root.findViewByPredicateInsideOut(this, new Predicate<View>() { // from class: android.view.View.2
                        @Override // java.util.function.Predicate
                        public boolean test(View t) {
                            return t.mNextFocusForwardId == id2;
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
        }
    }

    View findUserSetNextKeyboardNavigationCluster(View root, int direction) {
        switch (direction) {
            case 1:
                if (this.mID == -1) {
                    return null;
                }
                final int id = this.mID;
                return root.findViewByPredicateInsideOut(this, new Predicate() { // from class: android.view.-$$Lambda$View$7kZ4TXHKswReUMQB8098MEBcx_U
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return View.lambda$findUserSetNextKeyboardNavigationCluster$0(id, (View) obj);
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
            Log.m64w(VIEW_LOG_TAG, "couldn't find view with id " + id);
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
        if ((focusableMode & 1) == 1 && !isFocusableInTouchMode()) {
            return;
        }
        views.add(this);
    }

    public void addKeyboardNavigationClusters(Collection<View> views, int direction) {
        if (!isKeyboardNavigationCluster() || !hasFocusable()) {
            return;
        }
        views.add(this);
    }

    public void findViewsWithText(ArrayList<View> outViews, CharSequence searched, int flags) {
        if (getAccessibilityNodeProvider() != null) {
            if ((flags & 4) != 0) {
                outViews.add(this);
            }
        } else if ((flags & 2) != 0 && searched != null && searched.length() > 0 && this.mContentDescription != null && this.mContentDescription.length() > 0) {
            String searchedLowerCase = searched.toString().toLowerCase();
            String contentDescriptionLowerCase = this.mContentDescription.toString().toLowerCase();
            if (contentDescriptionLowerCase.contains(searchedLowerCase)) {
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
        if (manager.isEnabled() && manager.isTouchExplorationEnabled() && (this.mViewFlags & 12) == 0 && (this.mPrivateFlags2 & 67108864) == 0) {
            this.mPrivateFlags2 |= 67108864;
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.setAccessibilityFocus(this, null);
            }
            invalidate();
            sendAccessibilityEvent(32768);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    public void clearAccessibilityFocus() {
        View focusHost;
        clearAccessibilityFocusNoCallbacks(0);
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null && (focusHost = viewRootImpl.getAccessibilityFocusedHost()) != null && ViewRootImpl.isViewDescendantOf(focusHost, this)) {
            viewRootImpl.setAccessibilityFocus(null, null);
        }
    }

    private void sendAccessibilityHoverEvent(int eventType) {
        View source = this;
        while (!source.includeForAccessibility()) {
            ViewParent parent = source.getParent();
            if (parent instanceof View) {
                source = (View) parent;
            } else {
                return;
            }
        }
        source.sendAccessibilityEvent(eventType);
    }

    void clearAccessibilityFocusNoCallbacks(int action) {
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
        return requestFocus(direction, null);
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return requestFocusNoSearch(direction, previouslyFocusedRect);
    }

    private boolean requestFocusNoSearch(int direction, Rect previouslyFocusedRect) {
        if (canTakeFocus()) {
            if ((!isInTouchMode() || 262144 == (this.mViewFlags & 262144)) && !hasAncestorThatBlocksDescendantFocus()) {
                if (!isLayoutValid()) {
                    this.mPrivateFlags |= 1;
                } else {
                    clearParentsWantFocus();
                }
                handleFocusGainInternal(direction, previouslyFocusedRect);
                return true;
            }
            return false;
        }
        return false;
    }

    void clearParentsWantFocus() {
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
            if (vgAncestor.getDescendantFocusability() != 393216) {
                if (!focusableInTouchMode && vgAncestor.shouldBlockFocusForTouchscreen()) {
                    return true;
                }
                ancestor = vgAncestor.getParent();
            } else {
                return true;
            }
        }
        return false;
    }

    @ViewDebug.ExportedProperty(category = Context.ACCESSIBILITY_SERVICE, mapping = {@ViewDebug.IntToString(from = 0, m46to = "auto"), @ViewDebug.IntToString(from = 1, m46to = "yes"), @ViewDebug.IntToString(from = 2, m46to = "no"), @ViewDebug.IntToString(from = 4, m46to = "noHideDescendants")})
    public int getImportantForAccessibility() {
        return (this.mPrivateFlags2 & 7340032) >> 20;
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
            this.mPrivateFlags2 |= (mode << 20) & 7340032;
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
        if (searchDescendants && (viewRoot = getViewRootImpl()) != null && (focusHost = viewRoot.getAccessibilityFocusedHost()) != null && ViewRootImpl.isViewDescendantOf(focusHost, this)) {
            return focusHost;
        }
        return null;
    }

    public boolean isImportantForAccessibility() {
        int mode = (this.mPrivateFlags2 & 7340032) >> 20;
        if (mode == 2 || mode == 4) {
            return false;
        }
        for (ViewParent parent = this.mParent; parent instanceof View; parent = parent.getParent()) {
            if (((View) parent).getImportantForAccessibility() == 4) {
                return false;
            }
        }
        if (mode != 1 && !isActionableForAccessibility() && !hasListenersForAccessibility() && getAccessibilityNodeProvider() == null && getAccessibilityLiveRegion() == 0 && !isAccessibilityPane()) {
            return false;
        }
        return true;
    }

    public ViewParent getParentForAccessibility() {
        if (this.mParent instanceof View) {
            View parentView = (View) this.mParent;
            if (parentView.includeForAccessibility()) {
                return this.mParent;
            }
            return this.mParent.getParentForAccessibility();
        }
        return null;
    }

    View getSelfOrParentImportantForA11y() {
        if (isImportantForAccessibility()) {
            return this;
        }
        ViewParent parent = getParentForAccessibility();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }

    public void addChildrenForAccessibility(ArrayList<View> outChildren) {
    }

    @UnsupportedAppUsage
    public boolean includeForAccessibility() {
        if (this.mAttachInfo != null) {
            return (this.mAttachInfo.mAccessibilityFetchFlags & 8) != 0 || isImportantForAccessibility();
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
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled() || this.mAttachInfo == null) {
            return;
        }
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
                    Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
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
                Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
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
                    Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    public void setTransitionVisibility(int visibility) {
        this.mViewFlags = (this.mViewFlags & (-13)) | visibility;
    }

    void resetSubtreeAccessibilityStateChanged() {
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
                boolean extendSelection = isAccessibilityFocused();
                if (extendSelection) {
                    clearAccessibilityFocus();
                    return true;
                }
                break;
            case 256:
                if (arguments != null) {
                    int granularity = arguments.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
                    boolean extendSelection2 = arguments.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN");
                    return traverseAtGranularity(granularity, true, extendSelection2);
                }
                break;
            case 512:
                if (arguments != null) {
                    int granularity2 = arguments.getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT");
                    boolean extendSelection3 = arguments.getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN");
                    return traverseAtGranularity(granularity2, false, extendSelection3);
                }
                break;
            case 131072:
                CharSequence text = getIterableTextForAccessibility();
                if (text == null) {
                    return false;
                }
                if (arguments != null) {
                    start = arguments.getInt("ACTION_ARGUMENT_SELECTION_START_INT", -1);
                } else {
                    start = -1;
                }
                int end = arguments != null ? arguments.getInt("ACTION_ARGUMENT_SELECTION_END_INT", -1) : -1;
                if ((getAccessibilitySelectionStart() != start || getAccessibilitySelectionEnd() != end) && start == end) {
                    setAccessibilitySelection(start, end);
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                    return true;
                }
                break;
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
                if (this.mTooltipInfo != null && this.mTooltipInfo.mTooltipPopup != null) {
                    return false;
                }
                return showLongClickTooltip(0, 0);
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
        if (extendSelection && isAccessibilitySelectionExtendable()) {
            selectionStart = getAccessibilitySelectionStart();
            if (selectionStart == -1) {
                selectionStart = forward ? segmentStart : segmentEnd;
            }
            selectionEnd = forward ? segmentEnd : segmentStart;
        } else {
            selectionStart = forward ? segmentEnd : segmentStart;
            selectionEnd = selectionStart;
        }
        setAccessibilitySelection(selectionStart, selectionEnd);
        int action = forward ? 256 : 512;
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
        if (start == end && end == this.mAccessibilityCursorPosition) {
            return;
        }
        if (start >= 0 && start == end && end <= getIterableTextForAccessibility().length()) {
            this.mAccessibilityCursorPosition = start;
        } else {
            this.mAccessibilityCursorPosition = -1;
        }
        sendAccessibilityEvent(8192);
    }

    private void sendViewTextTraversedAtGranularityEvent(int action, int granularity, int fromIndex, int toIndex) {
        if (this.mParent == null) {
            return;
        }
        AccessibilityEvent event = AccessibilityEvent.obtain(131072);
        onInitializeAccessibilityEvent(event);
        onPopulateAccessibilityEvent(event);
        event.setFromIndex(fromIndex);
        event.setToIndex(toIndex);
        event.setAction(action);
        event.setMovementGranularity(granularity);
        this.mParent.requestSendAccessibilityEvent(this, event);
    }

    @UnsupportedAppUsage
    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int granularity) {
        if (granularity != 8) {
            switch (granularity) {
                case 1:
                    CharSequence text = getIterableTextForAccessibility();
                    if (text != null && text.length() > 0) {
                        AccessibilityIterators.CharacterTextSegmentIterator iterator = AccessibilityIterators.CharacterTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                        iterator.initialize(text.toString());
                        return iterator;
                    }
                    return null;
                case 2:
                    CharSequence text2 = getIterableTextForAccessibility();
                    if (text2 != null && text2.length() > 0) {
                        AccessibilityIterators.WordTextSegmentIterator iterator2 = AccessibilityIterators.WordTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                        iterator2.initialize(text2.toString());
                        return iterator2;
                    }
                    return null;
                default:
                    return null;
            }
        }
        CharSequence text3 = getIterableTextForAccessibility();
        if (text3 != null && text3.length() > 0) {
            AccessibilityIterators.ParagraphTextSegmentIterator iterator3 = AccessibilityIterators.ParagraphTextSegmentIterator.getInstance();
            iterator3.initialize(text3.toString());
            return iterator3;
        }
        return null;
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

    boolean isAccessibilityFocusedViewOrHost() {
        return isAccessibilityFocused() || (getViewRootImpl() != null && getViewRootImpl().getAccessibilityFocusedHost() == this);
    }

    protected boolean canReceivePointerEvents() {
        return (this.mViewFlags & 12) == 0 || getAnimation() != null;
    }

    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        return (this.mViewFlags & 1024) == 0 || (event.getFlags() & 1) == 0;
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
        if (li != null && li.mOnCapturedPointerListener != null && li.mOnCapturedPointerListener.onCapturedPointer(this, event)) {
            return true;
        }
        return onCapturedPointerEvent(event);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onGenericMotionEvent(event, 0);
        }
        int source = event.getSource();
        if ((source & 2) != 0) {
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
        if ((li == null || li.mOnGenericMotionListener == null || (this.mViewFlags & 32) != 0 || !li.mOnGenericMotionListener.onGenericMotion(this, event)) && !onGenericMotionEvent(event)) {
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
                    break;
                case 12:
                    if (this.mInContextButtonPress && (actionButton == 32 || actionButton == 2)) {
                        this.mInContextButtonPress = false;
                        this.mIgnoreNextUpEvent = true;
                        break;
                    }
                    break;
            }
            if (this.mInputEventConsistencyVerifier != null) {
                this.mInputEventConsistencyVerifier.onUnhandledEvent(event, 0);
            }
            return false;
        }
        return true;
    }

    protected boolean dispatchHoverEvent(MotionEvent event) {
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnHoverListener != null && (this.mViewFlags & 32) == 0 && li.mOnHoverListener.onHover(this, event)) {
            return true;
        }
        return onHoverEvent(event);
    }

    protected boolean hasHoveredChild() {
        return false;
    }

    protected boolean pointInHoveredChild(MotionEvent event) {
        return false;
    }

    protected boolean dispatchGenericPointerEvent(MotionEvent event) {
        return false;
    }

    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
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

    protected void dispatchVisibilityChanged(View changedView, int visibility) {
        onVisibilityChanged(changedView, visibility);
    }

    protected void onVisibilityChanged(View changedView, int visibility) {
    }

    public void dispatchDisplayHint(int hint) {
        onDisplayHint(hint);
    }

    protected void onDisplayHint(int hint) {
    }

    public void dispatchWindowVisibilityChanged(int visibility) {
        onWindowVisibilityChanged(visibility);
    }

    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == 0) {
            initialAwakenScrollBars();
        }
    }

    boolean dispatchVisibilityAggregated(boolean isVisible) {
        boolean thisVisible = getVisibility() == 0;
        if (thisVisible || !isVisible) {
            onVisibilityAggregated(isVisible);
        }
        return thisVisible && isVisible;
    }

    public void onVisibilityAggregated(boolean isVisible) {
        int i;
        AutofillManager afm;
        boolean oldVisible = (this.mPrivateFlags3 & 536870912) != 0;
        this.mPrivateFlags3 = isVisible ? 536870912 | this.mPrivateFlags3 : this.mPrivateFlags3 & (-536870913);
        if (isVisible && this.mAttachInfo != null) {
            initialAwakenScrollBars();
        }
        Drawable dr = this.mBackground;
        if (dr != null && isVisible != dr.isVisible()) {
            dr.setVisible(isVisible, false);
        }
        Drawable hl = this.mDefaultFocusHighlight;
        if (hl != null && isVisible != hl.isVisible()) {
            hl.setVisible(isVisible, false);
        }
        Drawable fg = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (fg != null && isVisible != fg.isVisible()) {
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
                i = 16;
            } else {
                i = 32;
            }
            notifyViewAccessibilityStateChangedIfNeeded(i);
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
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        Display d = DisplayManagerGlobal.getInstance().getRealDisplay(0);
        d.getRectSize(outRect);
    }

    @UnsupportedAppUsage
    public void getWindowDisplayFrame(Rect outRect) {
        if (this.mAttachInfo != null) {
            try {
                this.mAttachInfo.mSession.getDisplayFrame(this.mAttachInfo.mWindow, outRect);
                return;
            } catch (RemoteException e) {
                return;
            }
        }
        Display d = DisplayManagerGlobal.getInstance().getRealDisplay(0);
        d.getRectSize(outRect);
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        onConfigurationChanged(newConfig);
    }

    protected void onConfigurationChanged(Configuration newConfig) {
    }

    void dispatchCollectViewAttributes(AttachInfo attachInfo, int visibility) {
        performCollectViewAttributes(attachInfo, visibility);
    }

    void performCollectViewAttributes(AttachInfo attachInfo, int visibility) {
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

    void needGlobalAttributesUpdate(boolean force) {
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
                    float x = getWidth() / 2.0f;
                    float y = getHeight() / 2.0f;
                    if (clickable) {
                        setPressed(true, x, y);
                    }
                    checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
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

    @Override // android.view.KeyEvent.Callback
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
        if (li != null && li.mOnCreateContextMenuListener != null) {
            li.mOnCreateContextMenuListener.onCreateContextMenu(menu, this, menuInfo);
        }
        ((MenuBuilder) menu).setCurrentMenuInfo(null);
        if (this.mParent != null) {
            this.mParent.createContextMenu(menu);
        }
    }

    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return null;
    }

    protected void onCreateContextMenu(ContextMenu menu) {
    }

    public boolean onTrackballEvent(MotionEvent event) {
        return false;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        return false;
    }

    private boolean dispatchTouchExplorationHoverEvent(MotionEvent event) {
        AccessibilityManager manager = AccessibilityManager.getInstance(this.mContext);
        if (manager.isEnabled() && manager.isTouchExplorationEnabled()) {
            boolean oldHoveringTouchDelegate = this.mHoveringTouchDelegate;
            int action = event.getActionMasked();
            AccessibilityNodeInfo.TouchDelegateInfo info = this.mTouchDelegate.getTouchDelegateInfo();
            boolean pointInDelegateRegion = false;
            for (int i = 0; i < info.getRegionCount(); i++) {
                Region r = info.getRegionAt(i);
                if (r.contains((int) event.getX(), (int) event.getY())) {
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
            if (action == 7) {
                if (oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
                    boolean handled = this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    return handled;
                } else if (!oldHoveringTouchDelegate && this.mHoveringTouchDelegate) {
                    MotionEvent eventNoHistory = event.getHistorySize() == 0 ? event : MotionEvent.obtainNoHistory(event);
                    eventNoHistory.setAction(9);
                    boolean handled2 = this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory);
                    eventNoHistory.setAction(action);
                    return this.mTouchDelegate.onTouchExplorationHoverEvent(eventNoHistory) | handled2;
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
            switch (action) {
                case 9:
                    if (oldHoveringTouchDelegate || !this.mHoveringTouchDelegate) {
                        return false;
                    }
                    boolean handled3 = this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    return handled3;
                case 10:
                    if (!oldHoveringTouchDelegate) {
                        return false;
                    }
                    this.mTouchDelegate.onTouchExplorationHoverEvent(event);
                    return false;
                default:
                    return false;
            }
        }
        return false;
    }

    public boolean onHoverEvent(MotionEvent event) {
        if (this.mTouchDelegate == null || !dispatchTouchExplorationHoverEvent(event)) {
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
            if (isHoverable() || isHovered()) {
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
            return false;
        }
        return true;
    }

    private boolean isHoverable() {
        int viewFlags = this.mViewFlags;
        if ((viewFlags & 32) == 32) {
            return false;
        }
        return (viewFlags & 16384) == 16384 || (viewFlags & 2097152) == 2097152 || (viewFlags & 8388608) == 8388608;
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

    protected boolean handleScrollBarDragging(MotionEvent event) {
        if (this.mScrollCache == null) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if ((this.mScrollCache.mScrollBarDraggingState == 0 && action != 0) || !event.isFromSource(8194) || !event.isButtonPressed(1)) {
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        if (action != 0) {
            if (action == 2) {
                if (this.mScrollCache.mScrollBarDraggingState == 0) {
                    return false;
                }
                if (this.mScrollCache.mScrollBarDraggingState != 1) {
                    if (this.mScrollCache.mScrollBarDraggingState == 2) {
                        Rect bounds = this.mScrollCache.mScrollBarBounds;
                        getHorizontalScrollBarBounds(bounds, null);
                        int range = computeHorizontalScrollRange();
                        int offset = computeHorizontalScrollOffset();
                        int extent = computeHorizontalScrollExtent();
                        int thumbLength = ScrollBarUtils.getThumbLength(bounds.width(), bounds.height(), extent, range);
                        int thumbOffset = ScrollBarUtils.getThumbOffset(bounds.width(), thumbLength, extent, range, offset);
                        float diff = x - this.mScrollCache.mScrollBarDraggingPos;
                        float maxThumbOffset = bounds.width() - thumbLength;
                        float newThumbOffset = Math.min(Math.max(thumbOffset + diff, 0.0f), maxThumbOffset);
                        int width = getWidth();
                        if (Math.round(newThumbOffset) == thumbOffset || maxThumbOffset <= 0.0f || width <= 0 || extent <= 0) {
                            return true;
                        }
                        int newX = Math.round(((range - extent) / (extent / width)) * (newThumbOffset / maxThumbOffset));
                        if (newX != getScrollX()) {
                            this.mScrollCache.mScrollBarDraggingPos = x;
                            setScrollX(newX);
                            return true;
                        }
                        return true;
                    }
                } else {
                    Rect bounds2 = this.mScrollCache.mScrollBarBounds;
                    getVerticalScrollBarBounds(bounds2, null);
                    int range2 = computeVerticalScrollRange();
                    int offset2 = computeVerticalScrollOffset();
                    int extent2 = computeVerticalScrollExtent();
                    int thumbLength2 = ScrollBarUtils.getThumbLength(bounds2.height(), bounds2.width(), extent2, range2);
                    int thumbOffset2 = ScrollBarUtils.getThumbOffset(bounds2.height(), thumbLength2, extent2, range2, offset2);
                    float diff2 = y - this.mScrollCache.mScrollBarDraggingPos;
                    float maxThumbOffset2 = bounds2.height() - thumbLength2;
                    float newThumbOffset2 = Math.min(Math.max(thumbOffset2 + diff2, 0.0f), maxThumbOffset2);
                    int height = getHeight();
                    if (Math.round(newThumbOffset2) == thumbOffset2 || maxThumbOffset2 <= 0.0f || height <= 0 || extent2 <= 0) {
                        return true;
                    }
                    int newY = Math.round(((range2 - extent2) / (extent2 / height)) * (newThumbOffset2 / maxThumbOffset2));
                    if (newY != getScrollY()) {
                        this.mScrollCache.mScrollBarDraggingPos = y;
                        setScrollY(newY);
                        return true;
                    }
                    return true;
                }
            }
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        if (this.mScrollCache.state == 0) {
            return false;
        }
        if (!isOnVerticalScrollbarThumb(x, y)) {
            if (isOnHorizontalScrollbarThumb(x, y)) {
                this.mScrollCache.mScrollBarDraggingState = 2;
                this.mScrollCache.mScrollBarDraggingPos = x;
                return true;
            }
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        this.mScrollCache.mScrollBarDraggingState = 1;
        this.mScrollCache.mScrollBarDraggingPos = y;
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchSlop;
        int motionClassification;
        int touchSlop2;
        SeempLog.record(3);
        float x = event.getX();
        float y = event.getY();
        int viewFlags = this.mViewFlags;
        int action = event.getAction();
        boolean clickable = (viewFlags & 16384) == 16384 || (viewFlags & 2097152) == 2097152 || (viewFlags & 8388608) == 8388608;
        if ((viewFlags & 32) == 32) {
            if (action == 1 && (this.mPrivateFlags & 16384) != 0) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            return clickable;
        }
        if (this.mTouchDelegate != null && this.mTouchDelegate.onTouchEvent(event)) {
            return true;
        }
        if (clickable || (viewFlags & 1073741824) == 1073741824) {
            switch (action) {
                case 0:
                    if (event.getSource() == 4098) {
                        this.mPrivateFlags3 |= 131072;
                    }
                    this.mHasPerformedLongPress = false;
                    if (!clickable) {
                        checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 3);
                        return true;
                    } else if (!performButtonActionOnTouchDown(event)) {
                        boolean isInScrollingContainer = isInScrollingContainer();
                        if (isInScrollingContainer) {
                            this.mPrivateFlags |= 33554432;
                            if (this.mPendingCheckForTap == null) {
                                this.mPendingCheckForTap = new CheckForTap();
                            }
                            this.mPendingCheckForTap.f2415x = event.getX();
                            this.mPendingCheckForTap.f2416y = event.getY();
                            postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                            return true;
                        }
                        setPressed(true, x, y);
                        checkForLongClick(ViewConfiguration.getLongPressTimeout(), x, y, 3);
                        return true;
                    } else {
                        return true;
                    }
                case 1:
                    this.mPrivateFlags3 &= -131073;
                    if ((viewFlags & 1073741824) == 1073741824) {
                        handleTooltipUp();
                    }
                    if (clickable) {
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
                            if (!prepressed) {
                                if (!post(this.mUnsetPressedState)) {
                                    this.mUnsetPressedState.run();
                                }
                            } else {
                                postDelayed(this.mUnsetPressedState, ViewConfiguration.getPressedStateDuration());
                            }
                            removeTapCallback();
                        }
                        this.mIgnoreNextUpEvent = false;
                        return true;
                    }
                    removeTapCallback();
                    removeLongPressCallback();
                    this.mInContextButtonPress = false;
                    this.mHasPerformedLongPress = false;
                    this.mIgnoreNextUpEvent = false;
                    return true;
                case 2:
                    if (clickable) {
                        drawableHotspotChanged(x, y);
                    }
                    int motionClassification2 = event.getClassification();
                    boolean ambiguousGesture = motionClassification2 == 1;
                    int touchSlop3 = this.mTouchSlop;
                    if (ambiguousGesture && hasPendingLongPressCallback()) {
                        float ambiguousMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
                        if (!pointInView(x, y, touchSlop3)) {
                            removeLongPressCallback();
                            long delay = ViewConfiguration.getLongPressTimeout() * ambiguousMultiplier;
                            touchSlop2 = touchSlop3;
                            motionClassification = motionClassification2;
                            checkForLongClick(delay - (event.getEventTime() - event.getDownTime()), x, y, 3);
                        } else {
                            touchSlop2 = touchSlop3;
                            motionClassification = motionClassification2;
                        }
                        touchSlop = (int) (touchSlop2 * ambiguousMultiplier);
                    } else {
                        touchSlop = touchSlop3;
                        motionClassification = motionClassification2;
                    }
                    if (!pointInView(x, y, touchSlop)) {
                        removeTapCallback();
                        removeLongPressCallback();
                        if ((this.mPrivateFlags & 16384) != 0) {
                            setPressed(false);
                        }
                        this.mPrivateFlags3 &= -131073;
                    }
                    boolean deepPress = motionClassification == 2;
                    if (deepPress && hasPendingLongPressCallback()) {
                        removeLongPressCallback();
                        checkForLongClick(0L, x, y, 4);
                        return true;
                    }
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
        return false;
    }

    @UnsupportedAppUsage
    public boolean isInScrollingContainer() {
        for (ViewParent p = getParent(); p != null && (p instanceof ViewGroup); p = p.getParent()) {
            if (((ViewGroup) p).shouldDelayChildPressedState()) {
                return true;
            }
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
        if (this.mAttachInfo != null) {
            if ((action != 0 && action != 2) || !event.isTouchEvent()) {
                return;
            }
            this.mAttachInfo.mUnbufferedDispatchRequested = true;
        }
    }

    private boolean hasSize() {
        return this.mBottom > this.mTop && this.mRight > this.mLeft;
    }

    private boolean canTakeFocus() {
        return (this.mViewFlags & 12) == 0 && (this.mViewFlags & 1) == 1 && (this.mViewFlags & 32) == 0 && (sCanFocusZeroSized || !isLayoutValid() || hasSize());
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    void setFlags(int flags, int mask) {
        int newFocus;
        boolean accessibilityEnabled = AccessibilityManager.getInstance(this.mContext).isEnabled();
        boolean oldIncludeForAccessibility = accessibilityEnabled && includeForAccessibility();
        int old = this.mViewFlags;
        this.mViewFlags = (this.mViewFlags & (~mask)) | (flags & mask);
        int changed = this.mViewFlags ^ old;
        if (changed == 0) {
            return;
        }
        int privateFlags = this.mPrivateFlags;
        boolean shouldNotifyFocusableAvailable = false;
        int focusableChangedByAuto = 0;
        if ((this.mViewFlags & 16) != 0 && (changed & BatteryStats.HistoryItem.EVENT_TEMP_WHITELIST_FINISH) != 0) {
            if ((this.mViewFlags & 16384) != 0) {
                newFocus = 1;
            } else {
                newFocus = 0;
            }
            this.mViewFlags = (this.mViewFlags & (-2)) | newFocus;
            focusableChangedByAuto = (old & 1) ^ (newFocus & 1);
            changed = (changed & (-2)) | focusableChangedByAuto;
        }
        int newFocus2 = changed & 1;
        if (newFocus2 != 0 && (privateFlags & 16) != 0) {
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
            if (newVisibility != 0 && this.mAttachInfo != null) {
                cleanupDraw();
            }
            if (this.mParent instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) this.mParent;
                parent.onChildVisibilityChanged(this, changed & 12, newVisibility);
                parent.invalidate(true);
            } else if (this.mParent != null) {
                this.mParent.invalidateChild(this, null);
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
            if ((this.mViewFlags & 128) != 0) {
                if (this.mBackground != null || this.mDefaultFocusHighlight != null || (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null)) {
                    this.mPrivateFlags &= -129;
                } else {
                    this.mPrivateFlags |= 128;
                }
            } else {
                this.mPrivateFlags &= -129;
            }
            requestLayout();
            invalidate(true);
        }
        if ((67108864 & changed) != 0 && this.mParent != null && this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
        if (accessibilityEnabled) {
            if (isAccessibilityPane()) {
                changed &= -13;
            }
            if ((changed & 1) != 0 || (changed & 12) != 0 || (changed & 16384) != 0 || (2097152 & changed) != 0 || (8388608 & changed) != 0) {
                if (oldIncludeForAccessibility != includeForAccessibility()) {
                    notifySubtreeAccessibilityStateChangedIfNeeded();
                } else {
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                }
            } else if ((changed & 32) != 0) {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    public void bringToFront() {
        if (this.mParent != null) {
            this.mParent.bringChildToFront(this);
        }
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        notifySubtreeAccessibilityStateChangedIfNeeded();
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            postSendViewScrolledAccessibilityEventCallback(l - oldl, t - oldt);
        }
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (this.mForegroundInfo != null) {
            this.mForegroundInfo.mBoundsChanged = true;
        }
        AttachInfo ai = this.mAttachInfo;
        if (ai != null) {
            ai.mViewScrollChanged = true;
        }
        if (this.mListenerInfo != null && this.mListenerInfo.mOnScrollChangeListener != null) {
            this.mListenerInfo.mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    }

    protected void dispatchDraw(Canvas canvas) {
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

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    public final int getWidth() {
        return this.mRight - this.mLeft;
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
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
        return (this.mMeasuredWidth & (-16777216)) | ((this.mMeasuredHeight >> 16) & (-256));
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

    @UnsupportedAppUsage
    void ensureTransformationInfo() {
        if (this.mTransformationInfo == null) {
            this.mTransformationInfo = new TransformationInfo();
        }
    }

    @UnsupportedAppUsage
    public final Matrix getInverseMatrix() {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mInverseMatrix == null) {
            this.mTransformationInfo.mInverseMatrix = new Matrix();
        }
        Matrix matrix = this.mTransformationInfo.mInverseMatrix;
        this.mRenderNode.getInverseMatrix(matrix);
        return matrix;
    }

    public float getCameraDistance() {
        float dpi = this.mResources.getDisplayMetrics().densityDpi;
        return this.mRenderNode.getCameraDistance() * dpi;
    }

    public void setCameraDistance(float distance) {
        float dpi = this.mResources.getDisplayMetrics().densityDpi;
        invalidateViewProperty(true, false);
        this.mRenderNode.setCameraDistance(Math.abs(distance) / dpi);
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

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768435)
    boolean setAlphaNoInvalidation(float alpha) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != alpha) {
            setAlphaInternal(alpha);
            boolean subclassHandlesAlpha = onSetAlpha((int) (255.0f * alpha));
            if (subclassHandlesAlpha) {
                this.mPrivateFlags |= 262144;
                return true;
            }
            this.mPrivateFlags &= -262145;
            this.mRenderNode.setAlpha(getFinalAlpha());
            return false;
        }
        return false;
    }

    void setAlphaInternal(float alpha) {
        float oldAlpha = this.mTransformationInfo.mAlpha;
        this.mTransformationInfo.mAlpha = alpha;
        if ((alpha == 0.0f) ^ (oldAlpha == 0.0f)) {
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
        int minTop;
        int yLoc;
        if (top != this.mTop) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (top < this.mTop) {
                        minTop = top;
                        yLoc = top - this.mTop;
                    } else {
                        minTop = this.mTop;
                        yLoc = 0;
                    }
                    invalidate(0, yLoc, this.mRight - this.mLeft, this.mBottom - minTop);
                }
            } else {
                invalidate(true);
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
                this.mForegroundInfo.mBoundsChanged = true;
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
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (bottom < this.mBottom) {
                        maxBottom = this.mBottom;
                    } else {
                        maxBottom = bottom;
                    }
                    invalidate(0, 0, this.mRight - this.mLeft, maxBottom - this.mTop);
                }
            } else {
                invalidate(true);
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
                this.mForegroundInfo.mBoundsChanged = true;
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
        int minLeft;
        int xLoc;
        if (left != this.mLeft) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (left < this.mLeft) {
                        minLeft = left;
                        xLoc = left - this.mLeft;
                    } else {
                        minLeft = this.mLeft;
                        xLoc = 0;
                    }
                    invalidate(xLoc, 0, this.mRight - minLeft, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
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
                this.mForegroundInfo.mBoundsChanged = true;
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
            if (matrixIsIdentity) {
                if (this.mAttachInfo != null) {
                    if (right < this.mRight) {
                        maxRight = this.mRight;
                    } else {
                        maxRight = right;
                    }
                    invalidate(0, 0, maxRight - this.mLeft, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
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
                this.mForegroundInfo.mBoundsChanged = true;
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
        if (value < min || value > max) {
            if (value < min || value == Float.NEGATIVE_INFINITY) {
                if (sThrowOnInvalidFloatProperties) {
                    throw new IllegalArgumentException("Cannot set '" + propertyName + "' to " + value + ", the value must be >= " + min);
                }
                return min;
            } else if (value > max || value == Float.POSITIVE_INFINITY) {
                if (sThrowOnInvalidFloatProperties) {
                    throw new IllegalArgumentException("Cannot set '" + propertyName + "' to " + value + ", the value must be <= " + max);
                }
                return max;
            } else if (Float.isNaN(value)) {
                if (sThrowOnInvalidFloatProperties) {
                    throw new IllegalArgumentException("Cannot set '" + propertyName + "' to Float.NaN");
                }
                return 0.0f;
            } else {
                throw new IllegalStateException("How do you get here?? " + value);
            }
        }
        return value;
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getX() {
        return this.mLeft + getTranslationX();
    }

    public void setX(float x) {
        setTranslationX(x - this.mLeft);
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public float getY() {
        return this.mTop + getTranslationY();
    }

    public void setY(float y) {
        setTranslationY(y - this.mTop);
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
        if (this.mStateListAnimator == stateListAnimator) {
            return;
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.setTarget(null);
        }
        this.mStateListAnimator = stateListAnimator;
        if (stateListAnimator != null) {
            stateListAnimator.setTarget(this);
            if (isAttachedToWindow()) {
                stateListAnimator.setState(getDrawableState());
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
                setOutlineProvider(null);
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
        if (this.mAttachInfo == null) {
            return;
        }
        if (this.mOutlineProvider == null) {
            this.mRenderNode.setOutline(null);
            return;
        }
        Outline outline = this.mAttachInfo.mTmpOutline;
        outline.setEmpty();
        outline.setAlpha(1.0f);
        this.mOutlineProvider.getOutline(this, outline);
        this.mRenderNode.setOutline(outline);
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
        tmpRect.set(0.0f, 0.0f, getWidth(), getHeight());
        getMatrix().mapRect(tmpRect);
        outRect.set(((int) tmpRect.left) + this.mLeft, ((int) tmpRect.top) + this.mTop, ((int) tmpRect.right) + this.mLeft, ((int) tmpRect.bottom) + this.mTop);
    }

    final boolean pointInView(float localX, float localY) {
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
        return this.mParent == null || this.mParent.getChildVisibleRect(this, r, globalOffset);
    }

    public final boolean getGlobalVisibleRect(Rect r) {
        return getGlobalVisibleRect(r, null);
    }

    public final boolean getLocalVisibleRect(Rect r) {
        Point offset = this.mAttachInfo != null ? this.mAttachInfo.mPoint : new Point();
        if (getGlobalVisibleRect(r, offset)) {
            r.offset(-offset.f59x, -offset.f60y);
            return true;
        }
        return false;
    }

    public void offsetTopAndBottom(int offset) {
        int minTop;
        int maxBottom;
        int yLoc;
        if (offset != 0) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    ViewParent p = this.mParent;
                    if (p != null && this.mAttachInfo != null) {
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
            } else {
                invalidateViewProperty(false, false);
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
        int minLeft;
        int maxRight;
        if (offset != 0) {
            boolean matrixIsIdentity = hasIdentityMatrix();
            if (matrixIsIdentity) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    ViewParent p = this.mParent;
                    if (p != null && this.mAttachInfo != null) {
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
            } else {
                invalidateViewProperty(false, false);
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
        if (params == null) {
            throw new NullPointerException("Layout parameters cannot be null");
        }
        this.mLayoutParams = params;
        resolveLayoutParams();
        if (this.mParent instanceof ViewGroup) {
            ((ViewGroup) this.mParent).onSetLayoutParams(this, params);
        }
        requestLayout();
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

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars() {
        return this.mScrollCache != null && awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade, true);
    }

    private boolean initialAwakenScrollBars() {
        return this.mScrollCache != null && awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade * 4, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars(int startDelay) {
        return awakenScrollBars(startDelay, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
        long fadeStartTime = AnimationUtils.currentAnimationTimeMillis() + startDelay;
        scrollCache.fadeStartTime = fadeStartTime;
        scrollCache.state = 1;
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mHandler.removeCallbacks(scrollCache);
            this.mAttachInfo.mHandler.postAtTime(scrollCache, fadeStartTime);
        }
        return true;
    }

    private boolean skipInvalidate() {
        return ((this.mViewFlags & 12) == 0 || this.mCurrentAnimation != null || ((this.mParent instanceof ViewGroup) && ((ViewGroup) this.mParent).isViewTransitioning(this))) ? false : true;
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

    void invalidateInternal(int l, int t, int r, int b, boolean invalidateCache, boolean fullInvalidate) {
        View receiver;
        if (this.mGhostView != null) {
            this.mGhostView.invalidate(true);
        } else if (skipInvalidate()) {
        } else {
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
        for (ViewParent p = getParent(); p != null && (p instanceof View); p = p.getParent()) {
            View v = (View) p;
            if (v.isProjectionReceiver()) {
                return v;
            }
        }
        return null;
    }

    private boolean isProjectionReceiver() {
        return this.mBackground != null;
    }

    @UnsupportedAppUsage
    void invalidateViewProperty(boolean invalidateParent, boolean forceRedraw) {
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

    protected void damageInParent() {
        if (this.mParent != null && this.mAttachInfo != null) {
            this.mParent.onDescendantInvalidated(this, this);
        }
    }

    @UnsupportedAppUsage
    protected void invalidateParentCaches() {
        if (this.mParent instanceof View) {
            ((View) this.mParent).mPrivateFlags |= Integer.MIN_VALUE;
        }
    }

    @UnsupportedAppUsage
    protected void invalidateParentIfNeeded() {
        if (isHardwareAccelerated() && (this.mParent instanceof View)) {
            ((View) this.mParent).invalidate(true);
        }
    }

    protected void invalidateParentIfNeededAndWasQuickRejected() {
        if ((this.mPrivateFlags2 & 268435456) != 0) {
            invalidateParentIfNeeded();
        }
    }

    @ViewDebug.ExportedProperty(category = "drawing")
    public boolean isOpaque() {
        return (this.mPrivateFlags & 25165824) == 25165824 && getFinalAlpha() >= 1.0f;
    }

    @UnsupportedAppUsage
    protected void computeOpaqueFlags() {
        if (this.mBackground != null && this.mBackground.getOpacity() == -1) {
            this.mPrivateFlags |= 8388608;
        } else {
            this.mPrivateFlags &= -8388609;
        }
        int flags = this.mViewFlags;
        if (((flags & 512) == 0 && (flags & 256) == 0) || (flags & 50331648) == 0 || (50331648 & flags) == 33554432) {
            this.mPrivateFlags |= 16777216;
        } else {
            this.mPrivateFlags &= -16777217;
        }
    }

    protected boolean hasOpaqueScrollbars() {
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
            attachInfo.mViewRootImpl.mChoreographer.postCallback(1, action, null);
        } else {
            getRunQueue().post(action);
        }
    }

    public void postOnAnimationDelayed(Runnable action, long delayMillis) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, action, null, delayMillis);
        } else {
            getRunQueue().postDelayed(action, delayMillis);
        }
    }

    public boolean removeCallbacks(Runnable action) {
        if (action != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(action);
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, action, null);
            }
            getRunQueue().removeCallbacks(action);
        }
        return true;
    }

    public void postInvalidate() {
        postInvalidateDelayed(0L);
    }

    public void postInvalidate(int left, int top, int right, int bottom) {
        postInvalidateDelayed(0L, left, top, right, bottom);
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
        if (this.mScrollCache != null && (this.mViewFlags & 12288) != 0) {
            return this.mScrollCache.fadingEdgeLength;
        }
        return 0;
    }

    protected float getTopFadingEdgeStrength() {
        return computeVerticalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getBottomFadingEdgeStrength() {
        return computeVerticalScrollOffset() + computeVerticalScrollExtent() < computeVerticalScrollRange() ? 1.0f : 0.0f;
    }

    protected float getLeftFadingEdgeStrength() {
        return computeHorizontalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getRightFadingEdgeStrength() {
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

    @UnsupportedAppUsage
    protected void recomputePadding() {
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
        return this.mScrollCache == null ? ViewConfiguration.getScrollDefaultDelay() : this.mScrollCache.scrollBarDefaultDelayBeforeFade;
    }

    public void setScrollBarDefaultDelayBeforeFade(int scrollBarDefaultDelayBeforeFade) {
        getScrollCache().scrollBarDefaultDelayBeforeFade = scrollBarDefaultDelayBeforeFade;
    }

    public int getScrollBarFadeDuration() {
        return this.mScrollCache == null ? ViewConfiguration.getScrollBarFadeDuration() : this.mScrollCache.scrollBarFadeDuration;
    }

    public void setScrollBarFadeDuration(int scrollBarFadeDuration) {
        getScrollCache().scrollBarFadeDuration = scrollBarFadeDuration;
    }

    public int getScrollBarSize() {
        return this.mScrollCache == null ? ViewConfiguration.get(this.mContext).getScaledScrollBarSize() : this.mScrollCache.scrollBarSize;
    }

    public void setScrollBarSize(int scrollBarSize) {
        getScrollCache().scrollBarSize = scrollBarSize;
    }

    public void setScrollBarStyle(int style) {
        if (style != (this.mViewFlags & 50331648)) {
            this.mViewFlags = (this.mViewFlags & (-50331649)) | (50331648 & style);
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    @ViewDebug.ExportedProperty(mapping = {@ViewDebug.IntToString(from = 0, m46to = "INSIDE_OVERLAY"), @ViewDebug.IntToString(from = 16777216, m46to = "INSIDE_INSET"), @ViewDebug.IntToString(from = 33554432, m46to = "OUTSIDE_OVERLAY"), @ViewDebug.IntToString(from = 50331648, m46to = "OUTSIDE_INSET")})
    public int getScrollBarStyle() {
        return this.mViewFlags & 50331648;
    }

    protected int computeHorizontalScrollRange() {
        return getWidth();
    }

    protected int computeHorizontalScrollOffset() {
        return this.mScrollX;
    }

    protected int computeHorizontalScrollExtent() {
        return getWidth();
    }

    protected int computeVerticalScrollRange() {
        return getHeight();
    }

    protected int computeVerticalScrollOffset() {
        return this.mScrollY;
    }

    protected int computeVerticalScrollExtent() {
        return getHeight();
    }

    public boolean canScrollHorizontally(int direction) {
        int offset = computeHorizontalScrollOffset();
        int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            if (offset <= 0) {
                return false;
            }
            return true;
        } else if (offset >= range - 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean canScrollVertically(int direction) {
        int offset = computeVerticalScrollOffset();
        int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (range == 0) {
            return false;
        }
        if (direction < 0) {
            if (offset <= 0) {
                return false;
            }
            return true;
        } else if (offset >= range - 1) {
            return false;
        } else {
            return true;
        }
    }

    void getScrollIndicatorBounds(Rect out) {
        out.left = this.mScrollX;
        out.right = (this.mScrollX + this.mRight) - this.mLeft;
        out.top = this.mScrollY;
        out.bottom = (this.mScrollY + this.mBottom) - this.mTop;
    }

    private void onDrawScrollIndicators(Canvas c) {
        Drawable dr;
        int leftRtl;
        int rightRtl;
        if ((this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) == 0 || (dr = this.mScrollIndicatorDrawable) == null) {
            return;
        }
        int h = dr.getIntrinsicHeight();
        int w = dr.getIntrinsicWidth();
        Rect rect = this.mAttachInfo.mTmpInvalRect;
        getScrollIndicatorBounds(rect);
        if ((this.mPrivateFlags3 & 256) != 0) {
            boolean canScrollUp = canScrollVertically(-1);
            if (canScrollUp) {
                dr.setBounds(rect.left, rect.top, rect.right, rect.top + h);
                dr.draw(c);
            }
        }
        if ((this.mPrivateFlags3 & 512) != 0) {
            boolean canScrollDown = canScrollVertically(1);
            if (canScrollDown) {
                dr.setBounds(rect.left, rect.bottom - h, rect.right, rect.bottom);
                dr.draw(c);
            }
        }
        if (getLayoutDirection() == 1) {
            leftRtl = 8192;
            rightRtl = 4096;
        } else {
            leftRtl = 4096;
            rightRtl = 8192;
        }
        int leftMask = leftRtl | 1024;
        if ((this.mPrivateFlags3 & leftMask) != 0) {
            boolean canScrollLeft = canScrollHorizontally(-1);
            if (canScrollLeft) {
                dr.setBounds(rect.left, rect.top, rect.left + w, rect.bottom);
                dr.draw(c);
            }
        }
        int rightMask = rightRtl | 2048;
        if ((this.mPrivateFlags3 & rightMask) != 0) {
            boolean canScrollRight = canScrollHorizontally(1);
            if (canScrollRight) {
                dr.setBounds(rect.right - w, rect.top, rect.right, rect.bottom);
                dr.draw(c);
            }
        }
    }

    private void getHorizontalScrollBarBounds(Rect drawBounds, Rect touchBounds) {
        Rect bounds = drawBounds != null ? drawBounds : touchBounds;
        if (bounds == null) {
            return;
        }
        int inside = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        boolean drawVerticalScrollBar = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        int size = getHorizontalScrollbarHeight();
        int verticalScrollBarGap = drawVerticalScrollBar ? getVerticalScrollbarWidth() : 0;
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        bounds.top = ((this.mScrollY + height) - size) - (this.mUserPaddingBottom & inside);
        bounds.left = this.mScrollX + (this.mPaddingLeft & inside);
        bounds.right = ((this.mScrollX + width) - (this.mUserPaddingRight & inside)) - verticalScrollBarGap;
        bounds.bottom = bounds.top + size;
        if (touchBounds == null) {
            return;
        }
        if (touchBounds != bounds) {
            touchBounds.set(bounds);
        }
        int minTouchTarget = this.mScrollCache.scrollBarMinTouchTarget;
        if (touchBounds.height() < minTouchTarget) {
            int adjust = (minTouchTarget - touchBounds.height()) / 2;
            touchBounds.bottom = Math.min(touchBounds.bottom + adjust, this.mScrollY + height);
            touchBounds.top = touchBounds.bottom - minTouchTarget;
        }
        int adjust2 = touchBounds.width();
        if (adjust2 < minTouchTarget) {
            int adjust3 = (minTouchTarget - touchBounds.width()) / 2;
            touchBounds.left -= adjust3;
            touchBounds.right = touchBounds.left + minTouchTarget;
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
        if (bounds == null) {
            return;
        }
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
        if (touchBounds == null) {
            return;
        }
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
        int adjust2 = touchBounds.height();
        if (adjust2 < minTouchTarget) {
            int adjust3 = (minTouchTarget - touchBounds.height()) / 2;
            touchBounds.top -= adjust3;
            touchBounds.bottom = touchBounds.top + minTouchTarget;
        }
    }

    protected final void onDrawScrollBars(Canvas canvas) {
        ScrollBarDrawable scrollBar;
        ScrollabilityCache cache = this.mScrollCache;
        if (cache != null) {
            int state = cache.state;
            if (state == 0) {
                return;
            }
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
                if (drawVerticalScrollBar || drawHorizontalScrollBar) {
                    ScrollBarDrawable scrollBar2 = cache.scrollBar;
                    if (drawHorizontalScrollBar) {
                        scrollBar2.setParameters(computeHorizontalScrollRange(), computeHorizontalScrollOffset(), computeHorizontalScrollExtent(), false);
                        Rect bounds = cache.mScrollBarBounds;
                        getHorizontalScrollBarBounds(bounds, null);
                        scrollBar = scrollBar2;
                        onDrawHorizontalScrollBar(canvas, scrollBar2, bounds.left, bounds.top, bounds.right, bounds.bottom);
                        if (invalidate2) {
                            invalidate(bounds);
                        }
                    } else {
                        scrollBar = scrollBar2;
                    }
                    if (drawVerticalScrollBar) {
                        scrollBar.setParameters(computeVerticalScrollRange(), computeVerticalScrollOffset(), computeVerticalScrollExtent(), true);
                        Rect bounds2 = cache.mScrollBarBounds;
                        getVerticalScrollBarBounds(bounds2, null);
                        onDrawVerticalScrollBar(canvas, scrollBar, bounds2.left, bounds2.top, bounds2.right, bounds2.bottom);
                        if (invalidate2) {
                            invalidate(bounds2);
                        }
                    }
                }
            } else if (drawVerticalScrollBar) {
                Rect bounds3 = cache.mScrollBarBounds;
                getVerticalScrollBarBounds(bounds3, null);
                this.mRoundScrollbarRenderer.drawRoundScrollbars(canvas, cache.scrollBar.getAlpha() / 255.0f, bounds3);
                if (invalidate2) {
                    invalidate();
                }
            }
        }
    }

    protected boolean isVerticalScrollBarHidden() {
        return false;
    }

    @UnsupportedAppUsage
    protected void onDrawHorizontalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void onDrawVerticalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        scrollBar.setBounds(l, t, r, b);
        scrollBar.draw(canvas);
    }

    protected void onDraw(Canvas canvas) {
    }

    @UnsupportedAppUsage
    void assignParent(ViewParent parent) {
        if (this.mParent == null) {
            this.mParent = parent;
        } else if (parent == null) {
            this.mParent = null;
        } else {
            throw new RuntimeException("view " + this + " being added, but it already has a parent");
        }
    }

    protected void onAttachedToWindow() {
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
        if (needRtlPropertiesResolution()) {
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
        return false;
    }

    public void resetRtlProperties() {
        resetResolvedLayoutDirection();
        resetResolvedTextDirection();
        resetResolvedTextAlignment();
        resetResolvedPadding();
        resetResolvedDrawables();
    }

    void dispatchScreenStateChanged(int screenState) {
        onScreenStateChanged(screenState);
    }

    public void onScreenStateChanged(int screenState) {
    }

    void dispatchMovedToDisplay(Display display, Configuration config) {
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
        int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
        return targetSdkVersion < 17 || !hasRtlSupport();
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
                    if (!canResolveLayoutDirection()) {
                        return false;
                    }
                    try {
                        if (!this.mParent.isLayoutDirectionResolved()) {
                            return false;
                        }
                        if (this.mParent.getLayoutDirection() == 1) {
                            this.mPrivateFlags2 |= 16;
                            break;
                        }
                    } catch (AbstractMethodError e) {
                        Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        break;
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
        if (getRawLayoutDirection() == 2) {
            if (this.mParent != null) {
                try {
                    return this.mParent.canResolveLayoutDirection();
                } catch (AbstractMethodError e) {
                    Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                    return false;
                }
            }
            return false;
        }
        return true;
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

    @UnsupportedAppUsage
    boolean isPaddingResolved() {
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
            if (resolvedLayoutDirection == 1) {
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
            } else {
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

    void resetResolvedPaddingInternal() {
        this.mPrivateFlags2 &= -536870913;
    }

    protected void onDetachedFromWindow() {
    }

    @UnsupportedAppUsage
    protected void onDetachedFromWindowInternal() {
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

    void invalidateInheritedLayoutMode(int layoutModeOfRoot) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
        if (ai != null) {
            IBinder appWindowToken = ai.mPanelParentWindowToken;
            if (appWindowToken == null) {
                return ai.mWindowToken;
            }
            return appWindowToken;
        }
        return null;
    }

    public Display getDisplay() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDisplay;
        }
        return null;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    IWindowSession getWindowSession() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSession;
        }
        return null;
    }

    protected IWindow getWindow() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindow;
        }
        return null;
    }

    int combineVisibility(int vis1, int vis2) {
        return Math.max(vis1, vis2);
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    void dispatchAttachedToWindow(AttachInfo info, int visibility) {
        this.mAttachInfo = info;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchAttachedToWindow(info, visibility);
        }
        this.mWindowAttachCount++;
        this.mPrivateFlags |= 1024;
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
        CopyOnWriteArrayList<OnAttachStateChangeListener> listeners = li != null ? li.mOnAttachStateChangeListeners : null;
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnAttachStateChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnAttachStateChangeListener listener = it.next();
                listener.onViewAttachedToWindow(this);
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

    @UnsupportedAppUsage(maxTargetSdk = 28)
    void dispatchDetachedFromWindow() {
        CopyOnWriteArrayList<OnAttachStateChangeListener> listeners;
        AttachInfo info = this.mAttachInfo;
        if (info != null) {
            int vis = info.mWindowVisibility;
            if (vis != 8) {
                onWindowVisibilityChanged(8);
                if (isShown()) {
                    onVisibilityAggregated(false);
                }
            }
        }
        onDetachedFromWindow();
        onDetachedFromWindowInternal();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
        if (imm != null) {
            imm.onViewDetachedFromWindow(this);
        }
        ListenerInfo li = this.mListenerInfo;
        if (li != null) {
            listeners = li.mOnAttachStateChangeListeners;
        } else {
            listeners = null;
        }
        if (listeners != null && listeners.size() > 0) {
            Iterator<OnAttachStateChangeListener> it = listeners.iterator();
            while (it.hasNext()) {
                OnAttachStateChangeListener listener = it.next();
                listener.onViewDetachedFromWindow(this);
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

    void dispatchCancelPendingInputEvents() {
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

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        if (this.mID != -1 && (this.mViewFlags & 65536) == 0) {
            this.mPrivateFlags &= -131073;
            Parcelable state = onSaveInstanceState();
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
            if (state != null) {
                container.put(this.mID, state);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        this.mPrivateFlags |= 131072;
        if (this.mStartActivityRequestWho != null || isAutofilled() || this.mAutofillViewId > 1073741823) {
            BaseSavedState state = new BaseSavedState(AbsSavedState.EMPTY_STATE);
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
        return BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(SparseArray<Parcelable> container) {
        dispatchRestoreInstanceState(container);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        Parcelable state;
        if (this.mID != -1 && (state = container.get(this.mID)) != null) {
            this.mPrivateFlags &= -131073;
            onRestoreInstanceState(state);
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
                if ((this.mPrivateFlags3 & 1073741824) != 0) {
                    if (Log.isLoggable(AUTOFILL_LOG_TAG, 3)) {
                        Log.m72d(AUTOFILL_LOG_TAG, "onRestoreInstanceState(): not setting autofillId to " + baseState.mAutofillViewId + " because view explicitly set it to " + this.mAutofillId);
                        return;
                    }
                    return;
                }
                this.mAutofillViewId = baseState.mAutofillViewId;
                this.mAutofillId = null;
            }
        }
    }

    public long getDrawingTime() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDrawingTime;
        }
        return 0L;
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
        }
        boolean typeChanged = this.mRenderNode.setLayerType(layerType);
        if (!typeChanged) {
            setLayerPaint(paint);
            return;
        }
        if (layerType != 1) {
            destroyDrawingCache();
        }
        this.mLayerType = layerType;
        this.mLayerPaint = this.mLayerType == 0 ? null : paint;
        this.mRenderNode.setLayerPaint(this.mLayerPaint);
        invalidateParentCaches();
        invalidate(true);
    }

    public void setLayerPaint(Paint paint) {
        int layerType = getLayerType();
        if (layerType != 0) {
            this.mLayerPaint = paint;
            if (layerType == 2) {
                if (this.mRenderNode.setLayerPaint(paint)) {
                    invalidateViewProperty(false, false);
                    return;
                }
                return;
            }
            invalidate();
        }
    }

    public int getLayerType() {
        return this.mLayerType;
    }

    public void buildLayer() {
        if (this.mLayerType == 0) {
            return;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            throw new IllegalStateException("This view must be attached to a window first");
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
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

    @UnsupportedAppUsage
    protected void destroyHardwareResources() {
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
        Log.m72d(VIEW_LOG_TAG, indent + this + "             DIRTY(" + (this.mPrivateFlags & 2097152) + ") DRAWN(" + (this.mPrivateFlags & 32) + ") CACHE_VALID(" + (this.mPrivateFlags & 32768) + ") INVALIDATED(" + (this.mPrivateFlags & Integer.MIN_VALUE) + ")");
        if (clear) {
            this.mPrivateFlags &= clearMask;
        }
        if (this instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) this;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                child.outputDirtyFlags(indent + "  ", clear, clearMask);
            }
        }
    }

    protected void dispatchGetDisplayList() {
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
        if ((this.mPrivateFlags & 32768) == 0 || !renderNode.hasDisplayList() || this.mRecreateDisplayList) {
            if (renderNode.hasDisplayList() && !this.mRecreateDisplayList) {
                this.mPrivateFlags |= 32800;
                this.mPrivateFlags &= -2097153;
                dispatchGetDisplayList();
                return renderNode;
            }
            this.mRecreateDisplayList = true;
            int width = this.mRight - this.mLeft;
            int height = this.mBottom - this.mTop;
            int layerType = getLayerType();
            RecordingCanvas canvas = renderNode.beginRecording(width, height);
            try {
                if (layerType == 1) {
                    buildDrawingCache(true);
                    Bitmap cache = getDrawingCache(true);
                    if (cache != null) {
                        canvas.drawBitmap(cache, 0.0f, 0.0f, this.mLayerPaint);
                    }
                } else {
                    computeScroll();
                    canvas.translate(-this.mScrollX, -this.mScrollY);
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
            } finally {
                renderNode.endRecording();
                setDisplayListProperties(renderNode);
            }
        } else {
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
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
        if (Trace.isTagEnabled(8L)) {
            Trace.traceBegin(8L, "buildDrawingCache/SW Layer for " + getClass().getSimpleName());
        }
        try {
            buildDrawingCacheImpl(autoScale);
        } finally {
            Trace.traceEnd(8L);
        }
    }

    private void buildDrawingCacheImpl(boolean autoScale) {
        Bitmap.Config quality;
        boolean z;
        boolean clear;
        Canvas canvas;
        this.mCachingFailed = false;
        int width = this.mRight - this.mLeft;
        int height = this.mBottom - this.mTop;
        AttachInfo attachInfo = this.mAttachInfo;
        boolean scalingRequired = attachInfo != null && attachInfo.mScalingRequired;
        if (autoScale && scalingRequired) {
            width = (int) ((width * attachInfo.mApplicationScale) + 0.5f);
            height = (int) ((height * attachInfo.mApplicationScale) + 0.5f);
        }
        int drawingCacheBackgroundColor = this.mDrawingCacheBackgroundColor;
        boolean opaque = drawingCacheBackgroundColor != 0 || isOpaque();
        boolean use32BitCache = attachInfo != null && attachInfo.mUse32BitDrawingCache;
        long projectedBitmapSize = width * height * ((!opaque || use32BitCache) ? 4 : 2);
        long drawingCacheSize = ViewConfiguration.get(this.mContext).getScaledMaximumDrawingCacheSize();
        if (width > 0 && height > 0) {
            if (projectedBitmapSize <= drawingCacheSize) {
                Bitmap bitmap = autoScale ? this.mDrawingCache : this.mUnscaledDrawingCache;
                if (bitmap == null || bitmap.getWidth() != width || bitmap.getHeight() != height) {
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
                                if (autoScale) {
                                    this.mDrawingCache = null;
                                } else {
                                    this.mUnscaledDrawingCache = null;
                                }
                                this.mCachingFailed = true;
                                return;
                            }
                        } else {
                            this.mUnscaledDrawingCache = bitmap;
                        }
                        if (opaque && use32BitCache) {
                            z = false;
                            bitmap.setHasAlpha(false);
                        } else {
                            z = false;
                        }
                        clear = drawingCacheBackgroundColor != 0 ? true : z;
                    } catch (OutOfMemoryError e2) {
                    }
                } else {
                    clear = true;
                }
                if (attachInfo != null) {
                    canvas = attachInfo.mCanvas;
                    if (canvas == null) {
                        canvas = new Canvas();
                    }
                    canvas.setBitmap(bitmap);
                    attachInfo.mCanvas = null;
                } else {
                    canvas = new Canvas(bitmap);
                }
                if (clear) {
                    bitmap.eraseColor(drawingCacheBackgroundColor);
                }
                computeScroll();
                int restoreCount = canvas.save();
                if (autoScale && scalingRequired) {
                    float scale = attachInfo.mApplicationScale;
                    canvas.scale(scale, scale);
                }
                canvas.translate(-this.mScrollX, -this.mScrollY);
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
                canvas.setBitmap(null);
                if (attachInfo != null) {
                    attachInfo.mCanvas = canvas;
                    return;
                }
                return;
            }
        }
        if (width > 0 && height > 0) {
            Log.m64w(VIEW_LOG_TAG, getClass().getSimpleName() + " not displayed because it is too large to fit into a software layer (or drawing cache), needs " + projectedBitmapSize + " bytes, only " + drawingCacheSize + " available");
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
        int width2 = (int) ((width * scale) + 0.5f);
        int height2 = (int) ((height * scale) + 0.5f);
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
            canvas.translate(-this.mScrollX, -this.mScrollY);
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

    protected boolean isPaddingOffsetRequired() {
        return false;
    }

    protected int getLeftPaddingOffset() {
        return 0;
    }

    protected int getRightPaddingOffset() {
        return 0;
    }

    protected int getTopPaddingOffset() {
        return 0;
    }

    protected int getBottomPaddingOffset() {
        return 0;
    }

    protected int getFadeTop(boolean offsetRequired) {
        int top = this.mPaddingTop;
        return offsetRequired ? top + getTopPaddingOffset() : top;
    }

    protected int getFadeHeight(boolean offsetRequired) {
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
        if (clipBounds != this.mClipBounds) {
            if (clipBounds != null && clipBounds.equals(this.mClipBounds)) {
                return;
            }
            if (clipBounds != null) {
                if (this.mClipBounds == null) {
                    this.mClipBounds = new Rect(clipBounds);
                } else {
                    this.mClipBounds.set(clipBounds);
                }
            } else {
                this.mClipBounds = null;
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
        if (this.mClipBounds != null) {
            outRect.set(this.mClipBounds);
            return true;
        }
        return false;
    }

    private boolean applyLegacyAnimation(ViewGroup parent, long drawingTime, Animation a, boolean scalingRequired) {
        Transformation invalidationTransform;
        int flags = parent.mGroupFlags;
        boolean initialized = a.isInitialized();
        if (!initialized) {
            a.initialize(this.mRight - this.mLeft, this.mBottom - this.mTop, parent.getWidth(), parent.getHeight());
            a.initializeInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            if (this.mAttachInfo != null) {
                a.setListenerHandler(this.mAttachInfo.mHandler);
            }
            onAnimationStart();
        }
        Transformation t = parent.getChildTransformation();
        boolean more = a.getTransformation(drawingTime, t, 1.0f);
        if (!scalingRequired || this.mAttachInfo.mApplicationScale == 1.0f) {
            invalidationTransform = t;
        } else {
            if (parent.mInvalidationTransformation == null) {
                parent.mInvalidationTransformation = new Transformation();
            }
            Transformation invalidationTransform2 = parent.mInvalidationTransformation;
            a.getTransformation(drawingTime, invalidationTransform2, 1.0f);
            invalidationTransform = invalidationTransform2;
        }
        if (more) {
            if (!a.willChangeBounds()) {
                if ((flags & 144) == 128) {
                    parent.mGroupFlags |= 4;
                } else if ((flags & 4) == 0) {
                    parent.mPrivateFlags |= 64;
                    parent.invalidate(this.mLeft, this.mTop, this.mRight, this.mBottom);
                }
            } else {
                if (parent.mInvalidateRegion == null) {
                    parent.mInvalidateRegion = new RectF();
                }
                RectF region = parent.mInvalidateRegion;
                a.getInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, region, invalidationTransform);
                parent.mPrivateFlags |= 64;
                int left = this.mLeft + ((int) region.left);
                int top = this.mTop + ((int) region.top);
                parent.invalidate(left, top, ((int) (region.width() + 0.5f)) + left, ((int) (region.height() + 0.5f)) + top);
            }
        }
        return more;
    }

    void setDisplayListProperties(RenderNode renderNode) {
        boolean z;
        int transformType;
        if (renderNode != null) {
            renderNode.setHasOverlappingRendering(getHasOverlappingRendering());
            if ((this.mParent instanceof ViewGroup) && ((ViewGroup) this.mParent).getClipChildren()) {
                z = true;
            } else {
                z = false;
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
                if (alpha2 < 1.0f) {
                    int multipliedAlpha = (int) (255.0f * alpha2);
                    if (onSetAlpha(multipliedAlpha)) {
                        alpha2 = 1.0f;
                    }
                }
                renderNode.setAlpha(alpha2);
            } else if (alpha < 1.0f) {
                renderNode.setAlpha(alpha);
            }
        }
    }

    boolean draw(Canvas canvas, ViewGroup parent, long drawingTime) {
        Animation a;
        int transY;
        Bitmap cache;
        boolean hardwareAcceleratedCanvas;
        float alpha;
        int restoreTo;
        boolean more;
        Bitmap cache2;
        int sy;
        int restoreTo2;
        RenderNode renderNode;
        boolean hardwareAcceleratedCanvas2 = canvas.isHardwareAccelerated();
        boolean drawingWithRenderNode = this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerated && hardwareAcceleratedCanvas2;
        boolean more2 = false;
        boolean childHasIdentityMatrix = hasIdentityMatrix();
        int parentFlags = parent.mGroupFlags;
        if ((parentFlags & 256) != 0) {
            parent.getChildTransformation().clear();
            parent.mGroupFlags &= TrafficStats.TAG_NETWORK_STACK_RANGE_END;
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
            if ((this.mPrivateFlags3 & 1) != 0) {
                this.mRenderNode.setAnimationMatrix(null);
                this.mPrivateFlags3 &= -2;
            }
            if (!drawingWithRenderNode && (parentFlags & 2048) != 0) {
                Transformation t = parent.getChildTransformation();
                boolean hasTransform = parent.getChildStaticTransformation(this, t);
                if (hasTransform) {
                    int transformType = t.getTransformationType();
                    transformToApply = transformType != 0 ? t : null;
                    concatMatrix = (transformType & 2) != 0;
                }
            }
        }
        boolean concatMatrix2 = concatMatrix | (!childHasIdentityMatrix);
        this.mPrivateFlags |= 32;
        if (concatMatrix2 || (parentFlags & 2049) != 1 || !canvas.quickReject(this.mLeft, this.mTop, this.mRight, this.mBottom, Canvas.EdgeType.BW) || (this.mPrivateFlags & 64) != 0) {
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
            boolean drawingWithDrawingCache = (cache4 == null || drawingWithRenderNode) ? false : true;
            boolean offsetForScroll = cache4 == null && !drawingWithRenderNode;
            int restoreTo3 = -1;
            if (!drawingWithRenderNode || transformToApply != null) {
                restoreTo3 = canvas.save();
            }
            if (offsetForScroll) {
                canvas.translate(this.mLeft - sx2, this.mTop - sy3);
            } else {
                if (!drawingWithRenderNode) {
                    canvas.translate(this.mLeft, this.mTop);
                }
                if (scalingRequired) {
                    if (drawingWithRenderNode) {
                        restoreTo3 = canvas.save();
                    }
                    float scale = 1.0f / this.mAttachInfo.mApplicationScale;
                    canvas.scale(scale, scale);
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
                                cache = cache4;
                                renderNode3.setAnimationMatrix(transformToApply.getMatrix());
                                hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                            } else {
                                cache = cache4;
                                hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                                canvas.translate(-transX, -transY);
                                canvas.concat(transformToApply.getMatrix());
                                canvas.translate(transX, transY);
                            }
                            parent.mGroupFlags |= 256;
                        } else {
                            cache = cache4;
                            hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                        }
                        float transformAlpha = transformToApply.getAlpha();
                        if (transformAlpha < 1.0f) {
                            alpha2 *= transformAlpha;
                            parent.mGroupFlags |= 256;
                        }
                    } else {
                        cache = cache4;
                        hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                    }
                    if (!childHasIdentityMatrix && !drawingWithRenderNode) {
                        canvas.translate(-transX, -transY);
                        canvas.concat(getMatrix());
                        canvas.translate(transX, transY);
                    }
                } else {
                    cache = cache4;
                    hardwareAcceleratedCanvas = hardwareAcceleratedCanvas2;
                }
                float alpha3 = alpha2;
                if (alpha3 < 1.0f || (this.mPrivateFlags3 & 2) != 0) {
                    if (alpha3 < 1.0f) {
                        this.mPrivateFlags3 |= 2;
                    } else {
                        this.mPrivateFlags3 &= -3;
                    }
                    parent.mGroupFlags |= 256;
                    if (!drawingWithDrawingCache) {
                        int multipliedAlpha = (int) (alpha3 * 255.0f);
                        if (!onSetAlpha(multipliedAlpha)) {
                            if (drawingWithRenderNode) {
                                renderNode3.setAlpha(getAlpha() * alpha3 * getTransitionAlpha());
                                alpha = alpha3;
                                restoreTo = restoreTo4;
                                more = more2;
                                cache2 = cache;
                                sy = sy3;
                                restoreTo2 = sx2;
                                renderNode = renderNode3;
                            } else if (layerType2 == 0) {
                                alpha = alpha3;
                                sy = sy3;
                                renderNode = renderNode3;
                                restoreTo = restoreTo4;
                                more = more2;
                                cache2 = cache;
                                restoreTo2 = sx2;
                                canvas.saveLayerAlpha(sx2, sy3, getWidth() + sx2, sy3 + getHeight(), multipliedAlpha);
                            } else {
                                alpha = alpha3;
                                restoreTo = restoreTo4;
                                more = more2;
                                cache2 = cache;
                                sy = sy3;
                                restoreTo2 = sx2;
                                renderNode = renderNode3;
                            }
                        } else {
                            alpha = alpha3;
                            restoreTo = restoreTo4;
                            more = more2;
                            cache2 = cache;
                            sy = sy3;
                            restoreTo2 = sx2;
                            renderNode = renderNode3;
                            this.mPrivateFlags |= 262144;
                        }
                    } else {
                        alpha = alpha3;
                        restoreTo = restoreTo4;
                        more = more2;
                        cache2 = cache;
                        sy = sy3;
                        restoreTo2 = sx2;
                        renderNode = renderNode3;
                    }
                } else {
                    alpha = alpha3;
                    restoreTo = restoreTo4;
                    more = more2;
                    cache2 = cache;
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
                sy = sy3;
                restoreTo2 = sx2;
                renderNode = renderNode3;
                cache2 = cache4;
            }
            if (!drawingWithRenderNode) {
                if ((parentFlags & 1) != 0 && cache2 == null) {
                    if (offsetForScroll) {
                        canvas.clipRect(restoreTo2, sy, restoreTo2 + getWidth(), sy + getHeight());
                    } else if (!scalingRequired || cache2 == null) {
                        canvas.clipRect(0, 0, getWidth(), getHeight());
                    } else {
                        canvas.clipRect(0, 0, cache2.getWidth(), cache2.getHeight());
                    }
                }
                if (this.mClipBounds != null) {
                    canvas.clipRect(this.mClipBounds);
                }
            }
            if (!drawingWithDrawingCache) {
                if (drawingWithRenderNode) {
                    this.mPrivateFlags = (-2097153) & this.mPrivateFlags;
                    ((RecordingCanvas) canvas).drawRenderNode(renderNode);
                } else if ((this.mPrivateFlags & 128) == 128) {
                    this.mPrivateFlags = (-2097153) & this.mPrivateFlags;
                    dispatchDraw(canvas);
                } else {
                    draw(canvas);
                }
            } else if (cache2 != null) {
                this.mPrivateFlags = (-2097153) & this.mPrivateFlags;
                if (layerType2 == 0 || this.mLayerPaint == null) {
                    Paint cachePaint = parent.mCachePaint;
                    if (cachePaint == null) {
                        cachePaint = new Paint();
                        cachePaint.setDither(false);
                        parent.mCachePaint = cachePaint;
                    }
                    cachePaint.setAlpha((int) (alpha2 * 255.0f));
                    canvas.drawBitmap(cache2, 0.0f, 0.0f, cachePaint);
                } else {
                    int layerPaintAlpha = this.mLayerPaint.getAlpha();
                    if (alpha2 < 1.0f) {
                        this.mLayerPaint.setAlpha((int) (layerPaintAlpha * alpha2));
                    }
                    canvas.drawBitmap(cache2, 0.0f, 0.0f, this.mLayerPaint);
                    if (alpha2 < 1.0f) {
                        this.mLayerPaint.setAlpha(layerPaintAlpha);
                    }
                }
            }
            if (restoreTo >= 0) {
                canvas.restoreToCount(restoreTo);
            }
            Animation a3 = a;
            if (a3 != null && !more) {
                if (!hardwareAcceleratedCanvas && !a3.getFillAfter()) {
                    onSetAlpha(255);
                }
                parent.finishAnimatingView(this, a3);
            }
            if (more && hardwareAcceleratedCanvas && a3.hasAlpha() && (this.mPrivateFlags & 262144) == 262144) {
                invalidate(true);
            }
            this.mRecreateDisplayList = false;
            return more;
        }
        this.mPrivateFlags2 |= 268435456;
        return more2;
    }

    static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            sDebugPaint = new Paint();
            sDebugPaint.setAntiAlias(false);
        }
        return sDebugPaint;
    }

    final int dipsToPixels(int dips) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) ((dips * scale) + 0.5f);
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
            canvas.drawRect(l, t, l + cornerSquareSize, t + cornerSquareSize, paint);
            canvas.drawRect(r - cornerSquareSize, t, r, t + cornerSquareSize, paint);
            canvas.drawRect(l, b - cornerSquareSize, l + cornerSquareSize, b, paint);
            canvas.drawRect(r - cornerSquareSize, b - cornerSquareSize, r, b, paint);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(l, t, r, b, paint);
            canvas.drawLine(l, b, r, t, paint);
        }
    }

    public void draw(Canvas canvas) {
        boolean drawRight;
        float topFadeStrength;
        float topFadeStrength2;
        int saveCount;
        int bottomSaveCount;
        int leftSaveCount;
        int rightSaveCount;
        int topSaveCount;
        int bottom;
        int solidColor;
        int right;
        int length;
        int length2;
        int rightSaveCount2;
        int left;
        float fadeHeight;
        int right2;
        int bottom2;
        int bottomSaveCount2;
        int leftSaveCount2;
        int topSaveCount2;
        int privateFlags = this.mPrivateFlags;
        this.mPrivateFlags = ((-2097153) & privateFlags) | 32;
        drawBackground(canvas);
        int viewFlags = this.mViewFlags;
        boolean horizontalEdges = (viewFlags & 4096) != 0;
        boolean verticalEdges = (viewFlags & 8192) != 0;
        if (!verticalEdges && !horizontalEdges) {
            onDraw(canvas);
            dispatchDraw(canvas);
            drawAutofilledHighlight(canvas);
            if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().dispatchDraw(canvas);
            }
            onDrawForeground(canvas);
            drawDefaultFocusHighlight(canvas);
            if (debugDraw()) {
                debugDrawFocus(canvas);
                return;
            }
            return;
        }
        float bottomFadeStrength = 0.0f;
        float leftFadeStrength = 0.0f;
        float rightFadeStrength = 0.0f;
        int paddingLeft = this.mPaddingLeft;
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
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        boolean drawBottom = false;
        float fadeHeight2 = scrollabilityCache.fadingEdgeLength;
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
            float topFadeStrength3 = Math.max(0.0f, Math.min(1.0f, getTopFadingEdgeStrength()));
            drawTop = topFadeStrength3 * fadeHeight2 > 1.0f;
            topFadeStrength2 = topFadeStrength3;
            float topFadeStrength4 = getBottomFadingEdgeStrength();
            bottomFadeStrength = Math.max(0.0f, Math.min(1.0f, topFadeStrength4));
            drawBottom = bottomFadeStrength * fadeHeight2 > 1.0f;
        } else {
            topFadeStrength2 = topFadeStrength;
        }
        if (horizontalEdges) {
            leftFadeStrength = Math.max(0.0f, Math.min(1.0f, getLeftFadingEdgeStrength()));
            boolean drawLeft2 = leftFadeStrength * fadeHeight2 > 1.0f;
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
                int topSaveCount4 = top + length4;
                topSaveCount3 = canvas.saveUnclippedLayer(left2, top, right4, topSaveCount4);
            }
            if (drawBottom) {
                topSaveCount2 = topSaveCount3;
                int topSaveCount5 = bottom4 - length4;
                bottomSaveCount3 = canvas.saveUnclippedLayer(left2, topSaveCount5, right4, bottom4);
            } else {
                topSaveCount2 = topSaveCount3;
            }
            if (drawLeft) {
                leftSaveCount3 = canvas.saveUnclippedLayer(left2, top, left2 + length4, bottom4);
            }
            if (drawRight) {
                rightSaveCount3 = canvas.saveUnclippedLayer(right4 - length4, top, right4, bottom4);
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
        int topSaveCount6 = topSaveCount;
        Paint p = scrollabilityCache.paint;
        int bottomSaveCount4 = bottomSaveCount;
        Matrix matrix = scrollabilityCache.matrix;
        float bottomFadeStrength2 = bottomFadeStrength;
        Shader fade = scrollabilityCache.shader;
        if (drawRight) {
            matrix.setScale(1.0f, fadeHeight2 * rightFadeStrength);
            matrix.postRotate(90.0f);
            matrix.postTranslate(right4, top);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor2 != 0) {
                rightSaveCount2 = top;
                left = left2;
                right = right4;
                fadeHeight = fadeHeight2;
                bottom = bottom4;
                right2 = leftSaveCount;
                solidColor = solidColor2;
                length = length4;
                length2 = 1065353216;
                canvas.drawRect(right4 - length4, top, right4, bottom4, p);
            } else {
                canvas.restoreUnclippedLayer(rightSaveCount, p);
                bottom = bottom4;
                fadeHeight = fadeHeight2;
                solidColor = solidColor2;
                right = right4;
                length = length4;
                left = left2;
                length2 = 1065353216;
                rightSaveCount2 = top;
                right2 = leftSaveCount;
            }
        } else {
            bottom = bottom4;
            solidColor = solidColor2;
            right = right4;
            length = length4;
            length2 = 1065353216;
            rightSaveCount2 = top;
            left = left2;
            fadeHeight = fadeHeight2;
            right2 = leftSaveCount;
        }
        if (drawLeft) {
            matrix.setScale(length2, fadeHeight * leftFadeStrength);
            matrix.postRotate(-90.0f);
            matrix.postTranslate(left, rightSaveCount2);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(right2, p);
                bottom2 = bottom;
            } else {
                int bottom5 = bottom;
                bottom2 = bottom5;
                canvas.drawRect(left, rightSaveCount2, left + length, bottom5, p);
            }
        } else {
            bottom2 = bottom;
        }
        if (drawBottom) {
            matrix.setScale(length2, fadeHeight * bottomFadeStrength2);
            matrix.postRotate(180.0f);
            int bottom6 = bottom2;
            matrix.postTranslate(left, bottom6);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(bottomSaveCount4, p);
                bottomSaveCount2 = bottomSaveCount4;
                leftSaveCount2 = right;
            } else {
                int right5 = right;
                leftSaveCount2 = right5;
                bottomSaveCount2 = bottomSaveCount4;
                canvas.drawRect(left, bottom6 - length, right5, bottom6, p);
            }
        } else {
            bottomSaveCount2 = bottomSaveCount4;
            leftSaveCount2 = right;
        }
        if (drawTop) {
            matrix.setScale(1.0f, fadeHeight * topFadeStrength2);
            matrix.postTranslate(left, rightSaveCount2);
            fade.setLocalMatrix(matrix);
            p.setShader(fade);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(topSaveCount6, p);
            } else {
                canvas.drawRect(left, rightSaveCount2, leftSaveCount2, rightSaveCount2 + length, p);
            }
        }
        canvas.restoreToCount(saveCount);
        drawAutofilledHighlight(canvas);
        if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
            this.mOverlay.getOverlayView().dispatchDraw(canvas);
        }
        onDrawForeground(canvas);
        if (debugDraw()) {
            debugDrawFocus(canvas);
        }
    }

    @UnsupportedAppUsage
    private void drawBackground(Canvas canvas) {
        Drawable background = this.mBackground;
        if (background == null) {
            return;
        }
        setBackgroundBounds();
        if (canvas.isHardwareAccelerated() && this.mAttachInfo != null && this.mAttachInfo.mThreadedRenderer != null) {
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
        canvas.translate(scrollX, scrollY);
        background.draw(canvas);
        canvas.translate(-scrollX, -scrollY);
    }

    void setBackgroundBounds() {
        if (this.mBackgroundSizeChanged && this.mBackground != null) {
            this.mBackground.setBounds(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            this.mBackgroundSizeChanged = false;
            rebuildOutline();
        }
    }

    private void setBackgroundRenderNodeProperties(RenderNode renderNode) {
        renderNode.setTranslationX(this.mScrollX);
        renderNode.setTranslationY(this.mScrollY);
    }

    private RenderNode getDrawableRenderNode(Drawable drawable, RenderNode renderNode) {
        if (renderNode == null) {
            renderNode = RenderNode.create(drawable.getClass().getName(), new ViewAnimationHostBridge(this));
            renderNode.setUsageHint(1);
        }
        Rect bounds = drawable.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        RecordingCanvas canvas = renderNode.beginRecording(width, height);
        canvas.translate(-bounds.left, -bounds.top);
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
            output = "TAKES_FOCUS";
            numFlags = 0 + 1;
        }
        int i = flags & 12;
        if (i == 4) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            return output + "INVISIBLE";
        } else if (i == 8) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            return output + "GONE";
        } else {
            return output;
        }
    }

    private static String printPrivateFlags(int privateFlags) {
        String output = "";
        int numFlags = 0;
        if ((privateFlags & 1) == 1) {
            output = "WANTS_FOCUS";
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
        if ((privateFlags & 32) == 32) {
            if (numFlags > 0) {
                output = output + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER;
            }
            return output + "DRAWN";
        }
        return output;
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
        if (!changed && (this.mPrivateFlags & 8192) != 8192) {
            z = false;
        } else {
            onLayout(changed, l, t, r, b);
            if (shouldDrawRoundScrollbar()) {
                if (this.mRoundScrollbarRenderer == null) {
                    this.mRoundScrollbarRenderer = new RoundScrollbarRenderer(this);
                }
            } else {
                this.mRoundScrollbarRenderer = null;
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
                    listenersCopy.get(i2).onLayoutChange(this, l, t, r, b, oldL, oldT, oldR, oldB);
                    i = i2 + 1;
                    z2 = z2;
                    numListeners = numListeners;
                    listenersCopy = listenersCopy;
                    li = li;
                    oldL = oldL;
                    obj = null;
                }
            }
            z = z2;
        }
        boolean wasLayoutValid = isLayoutValid();
        this.mPrivateFlags &= -4097;
        this.mPrivateFlags3 |= 4;
        if (!wasLayoutValid && isFocused()) {
            this.mPrivateFlags &= -2;
            if (canTakeFocus()) {
                clearParentsWantFocus();
            } else if (getViewRootImpl() == null || !getViewRootImpl().isInLayout()) {
                clearFocusInternal(null, true, z);
                clearParentsWantFocus();
            } else if (!hasParentWantsFocus()) {
                clearFocusInternal(null, true, z);
            }
        } else if ((this.mPrivateFlags & 1) != 0) {
            this.mPrivateFlags &= -2;
            View focused = findFocus();
            if (focused != null && !restoreDefaultFocus() && !hasParentWantsFocus()) {
                focused.clearFocusInternal(null, true, z);
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

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean setFrame(int left, int top, int right, int bottom) {
        boolean changed = false;
        if (this.mLeft != left || this.mRight != right || this.mTop != top || this.mBottom != bottom) {
            changed = true;
            int drawn = this.mPrivateFlags & 32;
            int oldWidth = this.mRight - this.mLeft;
            int oldHeight = this.mBottom - this.mTop;
            int newWidth = right - left;
            int newHeight = bottom - top;
            boolean sizeChanged = (newWidth == oldWidth && newHeight == oldHeight) ? false : true;
            invalidate(sizeChanged);
            this.mLeft = left;
            this.mTop = top;
            this.mRight = right;
            this.mBottom = bottom;
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
                this.mForegroundInfo.mBoundsChanged = true;
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

    protected void onFinishInflate() {
    }

    public Resources getResources() {
        return this.mResources;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (verifyDrawable(drawable)) {
            Rect dirty = drawable.getDirtyBounds();
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
            rebuildOutline();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (verifyDrawable(who) && what != null) {
            long delay = when - SystemClock.uptimeMillis();
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, what, who, Choreographer.subtractFrameDelay(delay));
            } else {
                getRunQueue().postDelayed(what, delay);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
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
            this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, null, who);
        }
    }

    protected void resolveDrawables() {
        if (!isLayoutDirectionResolved() && getRawLayoutDirection() == 2) {
            return;
        }
        int layoutDirection = isLayoutDirectionResolved() ? getLayoutDirection() : getRawLayoutDirection();
        if (this.mBackground != null) {
            this.mBackground.setLayoutDirection(layoutDirection);
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.setLayoutDirection(layoutDirection);
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.setLayoutDirection(layoutDirection);
        }
        this.mPrivateFlags2 |= 1073741824;
        onResolveDrawables(layoutDirection);
    }

    boolean areDrawablesResolved() {
        return (this.mPrivateFlags2 & 1073741824) == 1073741824;
    }

    public void onResolveDrawables(int layoutDirection) {
    }

    protected void resetResolvedDrawables() {
        resetResolvedDrawablesInternal();
    }

    void resetResolvedDrawablesInternal() {
        this.mPrivateFlags2 &= -1073741825;
    }

    protected boolean verifyDrawable(Drawable who) {
        return who == this.mBackground || (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable == who) || this.mDefaultFocusHighlight == who;
    }

    protected void drawableStateChanged() {
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
        if (this.mScrollCache != null && (scrollBar = this.mScrollCache.scrollBar) != null && scrollBar.isStateful()) {
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
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
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
            int[] attrs = {16843534};
            TypedArray ta = this.mContext.obtainStyledAttributes(attrs);
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
        boolean lackFocusState = ((background != null && background.isStateful() && background.hasFocusStateSpecified()) || (foreground != null && foreground.isStateful() && foreground.hasFocusStateSpecified())) ? false : true;
        return !isInTouchMode() && getDefaultFocusHighlightEnabled() && lackFocusState && isAttachedToWindow() && sUseDefaultFocusHighlight;
    }

    private void switchDefaultFocusHighlight() {
        Drawable drawable;
        if (isFocused()) {
            Drawable drawable2 = this.mBackground;
            if (this.mForegroundInfo != null) {
                drawable = this.mForegroundInfo.mDrawable;
            } else {
                drawable = null;
            }
            boolean needed = isDefaultFocusHighlightNeeded(drawable2, drawable);
            boolean active = this.mDefaultFocusHighlight != null;
            if (needed && !active) {
                setDefaultFocusHighlight(getDefaultFocusHighlightDrawable());
            } else if (!needed && active) {
                setDefaultFocusHighlight(null);
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
                int b = (this.mBottom + t) - this.mTop;
                this.mDefaultFocusHighlight.setBounds(l, t, r, b);
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

    protected int[] onCreateDrawableState(int extraSpace) {
        if ((this.mViewFlags & 4194304) == 4194304 && (this.mParent instanceof View)) {
            return ((View) this.mParent).onCreateDrawableState(extraSpace);
        }
        int privateFlags = this.mPrivateFlags;
        int viewStateIndex = (privateFlags & 16384) != 0 ? 0 | 16 : 0;
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
        if (drawableState != null) {
            int[] fullState = new int[drawableState.length + extraSpace];
            System.arraycopy(drawableState, 0, fullState, 0, drawableState.length);
            return fullState;
        }
        return new int[extraSpace];
    }

    protected static int[] mergeDrawableStates(int[] baseState, int[] additionalState) {
        int N = baseState.length;
        int i = N - 1;
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
        if (resid != 0 && resid == this.mBackgroundResource) {
            return;
        }
        Drawable d = null;
        if (resid != 0) {
            d = this.mContext.getDrawable(resid);
        }
        setBackground(d);
        this.mBackgroundResource = resid;
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        boolean z;
        computeOpaqueFlags();
        if (background == this.mBackground) {
            return;
        }
        boolean requestLayout = false;
        this.mBackgroundResource = 0;
        if (this.mBackground != null) {
            if (isAttachedToWindow()) {
                this.mBackground.setVisible(false, false);
            }
            this.mBackground.setCallback(null);
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
                if (background.getLayoutDirection() == 1) {
                    this.mUserPaddingLeftInitial = padding.right;
                    this.mUserPaddingRightInitial = padding.left;
                    internalSetPadding(padding.right, padding.top, padding.left, padding.bottom);
                } else {
                    this.mUserPaddingLeftInitial = padding.left;
                    this.mUserPaddingRightInitial = padding.right;
                    internalSetPadding(padding.left, padding.top, padding.right, padding.bottom);
                }
                this.mLeftPaddingDefined = false;
                this.mRightPaddingDefined = false;
            }
            requestLayout = (this.mBackground != null && this.mBackground.getMinimumHeight() == background.getMinimumHeight() && this.mBackground.getMinimumWidth() == background.getMinimumWidth()) ? true : true;
            this.mBackground = background;
            if (background.isStateful()) {
                background.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                if (getWindowVisibility() != 0 || !isShown()) {
                    z = false;
                } else {
                    z = true;
                }
                background.setVisible(z, false);
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
        if (this.mBackgroundTint != null && this.mBackgroundTint.mBlendMode != null) {
            PorterDuff.Mode porterDuffMode = BlendMode.blendModeToPorterDuffMode(this.mBackgroundTint.mBlendMode);
            return porterDuffMode;
        }
        return null;
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
            if (foreground == null) {
                return;
            }
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (foreground != this.mForegroundInfo.mDrawable) {
            if (this.mForegroundInfo.mDrawable != null) {
                if (isAttachedToWindow()) {
                    this.mForegroundInfo.mDrawable.setVisible(false, false);
                }
                this.mForegroundInfo.mDrawable.setCallback(null);
                unscheduleDrawable(this.mForegroundInfo.mDrawable);
            }
            this.mForegroundInfo.mDrawable = foreground;
            boolean z = true;
            this.mForegroundInfo.mBoundsChanged = true;
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
            this.mForegroundInfo.mGravity = gravity;
            requestLayout();
        }
    }

    public void setForegroundTintList(ColorStateList tint) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new TintInfo();
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
            this.mForegroundInfo.mTintInfo = new TintInfo();
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
                this.mForegroundInfo.mDrawable = this.mForegroundInfo.mDrawable.mutate();
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
                this.mForegroundInfo.mBoundsChanged = false;
                Rect selfBounds = this.mForegroundInfo.mSelfBounds;
                Rect overlayBounds = this.mForegroundInfo.mOverlayBounds;
                if (this.mForegroundInfo.mInsidePadding) {
                    selfBounds.set(0, 0, getWidth(), getHeight());
                } else {
                    selfBounds.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }
                int ld = getLayoutDirection();
                Gravity.apply(this.mForegroundInfo.mGravity, foreground.getIntrinsicWidth(), foreground.getIntrinsicHeight(), selfBounds, overlayBounds, ld);
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

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 123768420)
    protected void internalSetPadding(int left, int top, int right, int bottom) {
        this.mUserPaddingLeft = left;
        this.mUserPaddingRight = right;
        this.mUserPaddingBottom = bottom;
        int viewFlags = this.mViewFlags;
        boolean changed = false;
        if ((viewFlags & 768) != 0) {
            if ((viewFlags & 512) != 0) {
                int offset = (viewFlags & 16777216) == 0 ? 0 : getVerticalScrollbarWidth();
                switch (this.mVerticalScrollbarPosition) {
                    case 0:
                        if (isLayoutRtl()) {
                            left += offset;
                            break;
                        } else {
                            right += offset;
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
                bottom += (viewFlags & 16777216) != 0 ? getHorizontalScrollbarHeight() : 0;
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
        if (getLayoutDirection() == 1) {
            this.mUserPaddingLeftInitial = end;
            this.mUserPaddingRightInitial = start;
            internalSetPadding(end, top, start, bottom);
            return;
        }
        this.mUserPaddingLeftInitial = start;
        this.mUserPaddingRightInitial = end;
        internalSetPadding(start, top, end, bottom);
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

    Insets computeOpticalInsets() {
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
            this.mPrivateFlags = (this.mPrivateFlags & (-5)) | (selected ? 4 : 0);
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

    protected void dispatchSetSelected(boolean selected) {
    }

    @ViewDebug.ExportedProperty
    public boolean isSelected() {
        return (this.mPrivateFlags & 4) != 0;
    }

    public void setActivated(boolean activated) {
        if (((this.mPrivateFlags & 1073741824) != 0) != activated) {
            this.mPrivateFlags = (this.mPrivateFlags & (-1073741825)) | (activated ? 1073741824 : 0);
            invalidate(true);
            refreshDrawableState();
            dispatchSetActivated(activated);
        }
    }

    protected void dispatchSetActivated(boolean activated) {
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

    public View getRootView() {
        View v;
        if (this.mAttachInfo != null && (v = this.mAttachInfo.mRootView) != null) {
            return v;
        }
        View parent = this;
        while (parent.mParent != null && (parent.mParent instanceof View)) {
            parent = (View) parent.mParent;
        }
        return parent;
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
            matrix.preTranslate(-vp.mScrollX, -vp.mScrollY);
        } else if (parent instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) parent;
            vr.transformMatrixToGlobal(matrix);
            matrix.preTranslate(0.0f, -vr.mCurScrollY);
        }
        matrix.preTranslate(this.mLeft, this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.preConcat(getMatrix());
        }
    }

    public void transformMatrixToLocal(Matrix matrix) {
        ViewParent parent = this.mParent;
        if (parent instanceof View) {
            View vp = (View) parent;
            vp.transformMatrixToLocal(matrix);
            matrix.postTranslate(vp.mScrollX, vp.mScrollY);
        } else if (parent instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) parent;
            vr.transformMatrixToLocal(matrix);
            matrix.postTranslate(0.0f, vr.mCurScrollY);
        }
        matrix.postTranslate(-this.mLeft, -this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.postConcat(getInverseMatrix());
        }
    }

    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT, indexMapping = {@ViewDebug.IntToString(from = 0, m46to = "x"), @ViewDebug.IntToString(from = 1, m46to = DateFormat.YEAR)})
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
        }
        if (this.mAttachInfo == null) {
            inOutLocation[1] = 0;
            inOutLocation[0] = 0;
            return;
        }
        float[] position = this.mAttachInfo.mTmpTransformLocation;
        position[0] = inOutLocation[0];
        position[1] = inOutLocation[1];
        if (!hasIdentityMatrix()) {
            getMatrix().mapPoints(position);
        }
        position[0] = position[0] + this.mLeft;
        position[1] = position[1] + this.mTop;
        ViewParent viewParent = this.mParent;
        while (viewParent instanceof View) {
            View view = (View) viewParent;
            position[0] = position[0] - view.mScrollX;
            position[1] = position[1] - view.mScrollY;
            if (!view.hasIdentityMatrix()) {
                view.getMatrix().mapPoints(position);
            }
            position[0] = position[0] + view.mLeft;
            position[1] = position[1] + view.mTop;
            viewParent = view.mParent;
        }
        if (viewParent instanceof ViewRootImpl) {
            ViewRootImpl vr = (ViewRootImpl) viewParent;
            position[1] = position[1] - vr.mCurScrollY;
        }
        inOutLocation[0] = Math.round(position[0]);
        inOutLocation[1] = Math.round(position[1]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends View> T findViewTraversal(int id) {
        if (id == this.mID) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends View> T findViewWithTagTraversal(Object tag) {
        if (tag != null && tag.equals(this.mTag)) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View childToSkip) {
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final <T extends View> T findViewById(int id) {
        if (id == -1) {
            return null;
        }
        return (T) findViewTraversal(id);
    }

    public final <T extends View> T requireViewById(int id) {
        T view = (T) findViewById(id);
        if (view == null) {
            throw new IllegalArgumentException("ID does not reference a View inside this View");
        }
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends View> T findViewByAccessibilityIdTraversal(int accessibilityId) {
        if (getAccessibilityViewId() == accessibilityId) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
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
        return (T) findViewWithTagTraversal(tag);
    }

    public final <T extends View> T findViewByPredicate(Predicate<View> predicate) {
        return (T) findViewByPredicateTraversal(predicate, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x001c, code lost:
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final <T extends View> T findViewByPredicateInsideOut(View start, Predicate<View> predicate) {
        T view;
        View start2 = start;
        View childToSkip = null;
        while (true) {
            view = (T) start2.findViewByPredicateTraversal(predicate, childToSkip);
            if (view != null || start2 == this) {
                break;
            }
            ViewParent parent = start2.getParent();
            if (parent == null || !(parent instanceof View)) {
                break;
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
        if ((key >>> 24) < 2) {
            throw new IllegalArgumentException("The key must be an application-specific resource id.");
        }
        setKeyedTag(key, tag);
    }

    @UnsupportedAppUsage
    public void setTagInternal(int key, Object tag) {
        if ((key >>> 24) != 1) {
            throw new IllegalArgumentException("The key must be a framework-specific resource id.");
        }
        setKeyedTag(key, tag);
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

    @UnsupportedAppUsage
    protected void debug(int depth) {
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
        Log.m72d(VIEW_LOG_TAG, output2);
        if ((this.mPrivateFlags & 2) != 0) {
            Log.m72d(VIEW_LOG_TAG, debugIndent(depth) + " FOCUSED");
        }
        Log.m72d(VIEW_LOG_TAG, debugIndent(depth) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ");
        if (this.mPaddingLeft != 0 || this.mPaddingTop != 0 || this.mPaddingRight != 0 || this.mPaddingBottom != 0) {
            Log.m72d(VIEW_LOG_TAG, debugIndent(depth) + "padding={" + this.mPaddingLeft + ", " + this.mPaddingTop + ", " + this.mPaddingRight + ", " + this.mPaddingBottom + "}");
        }
        Log.m72d(VIEW_LOG_TAG, debugIndent(depth) + "mMeasureWidth=" + this.mMeasuredWidth + " mMeasureHeight=" + this.mMeasuredHeight);
        String output3 = debugIndent(depth);
        if (this.mLayoutParams == null) {
            output = output3 + "BAD! no layout params";
        } else {
            output = this.mLayoutParams.debug(output3);
        }
        Log.m72d(VIEW_LOG_TAG, output);
        Log.m72d(VIEW_LOG_TAG, ((debugIndent(depth) + "flags={") + printFlags(this.mViewFlags)) + "}");
        Log.m72d(VIEW_LOG_TAG, ((debugIndent(depth) + "privateFlags={") + printPrivateFlags(this.mPrivateFlags)) + "}");
    }

    protected static String debugIndent(int depth) {
        StringBuilder spaces = new StringBuilder(((depth * 2) + 3) * 2);
        for (int i = 0; i < (depth * 2) + 3; i++) {
            spaces.append(' ');
            spaces.append(' ');
        }
        return spaces.toString();
    }

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
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
            if (viewRoot != null && viewRoot.isInLayout() && !viewRoot.requestLayoutDuringLayout(this)) {
                return;
            }
            this.mAttachInfo.mViewRequestingLayout = this;
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

    /* JADX WARN: Removed duplicated region for block: B:63:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSpec2;
        int heightMeasureSpec2;
        boolean optical = isLayoutModeOptical(this);
        if (optical != isLayoutModeOptical(this.mParent)) {
            Insets insets = getOpticalInsets();
            int oWidth = insets.left + insets.right;
            int oHeight = insets.top + insets.bottom;
            widthMeasureSpec2 = MeasureSpec.adjust(widthMeasureSpec, optical ? -oWidth : oWidth);
            heightMeasureSpec2 = MeasureSpec.adjust(heightMeasureSpec, optical ? -oHeight : oHeight);
        } else {
            widthMeasureSpec2 = widthMeasureSpec;
            heightMeasureSpec2 = heightMeasureSpec;
        }
        long key = (widthMeasureSpec2 << 32) | (heightMeasureSpec2 & 4294967295L);
        if (this.mMeasureCache == null) {
            this.mMeasureCache = new LongSparseLongArray(2);
        }
        boolean needsLayout = false;
        boolean forceLayout = (this.mPrivateFlags & 4096) == 4096;
        boolean specChanged = (widthMeasureSpec2 == this.mOldWidthMeasureSpec && heightMeasureSpec2 == this.mOldHeightMeasureSpec) ? false : true;
        boolean isSpecExactly = MeasureSpec.getMode(widthMeasureSpec2) == 1073741824 && MeasureSpec.getMode(heightMeasureSpec2) == 1073741824;
        boolean matchesSpecSize = getMeasuredWidth() == MeasureSpec.getSize(widthMeasureSpec2) && getMeasuredHeight() == MeasureSpec.getSize(heightMeasureSpec2);
        if (specChanged && (sAlwaysRemeasureExactly || !isSpecExactly || !matchesSpecSize)) {
            needsLayout = true;
        }
        if (forceLayout || needsLayout) {
            this.mPrivateFlags &= -2049;
            resolveRtlPropertiesIfNeeded();
            int cacheIndex = forceLayout ? -1 : this.mMeasureCache.indexOfKey(key);
            if (cacheIndex >= 0 && !sIgnoreMeasureCache) {
                long value = this.mMeasureCache.valueAt(cacheIndex);
                setMeasuredDimensionRaw((int) (value >> 32), (int) value);
                this.mPrivateFlags3 |= 8;
                if ((this.mPrivateFlags & 2048) != 2048) {
                    this.mPrivateFlags |= 8192;
                } else {
                    throw new IllegalStateException("View with id " + getId() + PluralRules.KEYWORD_RULE_SEPARATOR + getClass().getName() + "#onMeasure() did not set the measured dimension by calling setMeasuredDimension()");
                }
            }
            onMeasure(widthMeasureSpec2, heightMeasureSpec2);
            this.mPrivateFlags3 &= -9;
            if ((this.mPrivateFlags & 2048) != 2048) {
            }
        }
        this.mOldWidthMeasureSpec = widthMeasureSpec2;
        this.mOldHeightMeasureSpec = heightMeasureSpec2;
        this.mMeasureCache.put(key, (this.mMeasuredHeight & 4294967295L) | (this.mMeasuredWidth << 32));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
            if (specMode == 1073741824) {
                result = specSize;
            } else {
                result = size;
            }
        } else if (specSize < size) {
            result = 16777216 | specSize;
        } else {
            result = size;
        }
        return ((-16777216) & childMeasuredState) | result;
    }

    public static int getDefaultSize(int size, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode != Integer.MIN_VALUE) {
            if (specMode == 0) {
                return size;
            }
            if (specMode != 1073741824) {
                return size;
            }
        }
        return specSize;
    }

    protected int getSuggestedMinimumHeight() {
        return this.mBackground == null ? this.mMinHeight : Math.max(this.mMinHeight, this.mBackground.getMinimumHeight());
    }

    protected int getSuggestedMinimumWidth() {
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
        animation.setStartTime(-1L);
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

    protected void onAnimationStart() {
        this.mPrivateFlags |= 65536;
    }

    protected void onAnimationEnd() {
        this.mPrivateFlags &= -65537;
    }

    protected boolean onSetAlpha(int alpha) {
        return false;
    }

    @UnsupportedAppUsage
    public boolean gatherTransparentRegion(Region region) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (region != null && attachInfo != null) {
            int pflags = this.mPrivateFlags;
            if ((pflags & 128) == 0) {
                int[] location = attachInfo.mTransparentLocation;
                getLocationInWindow(location);
                int shadowOffset = getZ() > 0.0f ? (int) getZ() : 0;
                region.m126op(location[0] - shadowOffset, location[1] - shadowOffset, ((location[0] + this.mRight) - this.mLeft) + shadowOffset, ((location[1] + this.mBottom) - this.mTop) + (shadowOffset * 3), Region.EnumC0685Op.DIFFERENCE);
            } else {
                if (this.mBackground != null && this.mBackground.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mBackground, region);
                }
                if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null && this.mForegroundInfo.mDrawable.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mForegroundInfo.mDrawable, region);
                }
                if (this.mDefaultFocusHighlight != null && this.mDefaultFocusHighlight.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mDefaultFocusHighlight, region);
                }
            }
        }
        return true;
    }

    public void playSoundEffect(int soundConstant) {
        if (this.mAttachInfo == null || this.mAttachInfo.mRootCallbacks == null || !isSoundEffectsEnabled()) {
            return;
        }
        this.mAttachInfo.mRootCallbacks.playSoundEffect(soundConstant);
    }

    public boolean performHapticFeedback(int feedbackConstant) {
        return performHapticFeedback(feedbackConstant, 0);
    }

    public boolean performHapticFeedback(int feedbackConstant, int flags) {
        if (this.mAttachInfo == null) {
            return false;
        }
        if ((flags & 1) != 0 || isHapticFeedbackEnabled()) {
            return this.mAttachInfo.mRootCallbacks.performHapticFeedback(feedbackConstant, (flags & 2) != 0);
        }
        return false;
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
        getListenerInfo().mOnSystemUiVisibilityChangeListener = l;
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

    boolean updateLocalSystemUiVisibility(int localValue, int localChanges) {
        int val = (this.mSystemUiVisibility & (~localChanges)) | (localValue & localChanges);
        if (val != this.mSystemUiVisibility) {
            setSystemUiVisibility(val);
            return true;
        }
        return false;
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

    /* loaded from: classes4.dex */
    public static class DragShadowBuilder {
        @UnsupportedAppUsage
        private final WeakReference<View> mView;

        public DragShadowBuilder(View view) {
            this.mView = new WeakReference<>(view);
        }

        public DragShadowBuilder() {
            this.mView = new WeakReference<>(null);
        }

        public final View getView() {
            return this.mView.get();
        }

        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            View view = this.mView.get();
            if (view != null) {
                outShadowSize.set(view.getWidth(), view.getHeight());
                outShadowTouchPoint.set(outShadowSize.f59x / 2, outShadowSize.f60y / 2);
                return;
            }
            Log.m70e(View.VIEW_LOG_TAG, "Asked for drag thumb metrics but no view");
        }

        public void onDrawShadow(Canvas canvas) {
            View view = this.mView.get();
            if (view != null) {
                view.draw(canvas);
            } else {
                Log.m70e(View.VIEW_LOG_TAG, "Asked to draw drag shadow but no view");
            }
        }
    }

    @Deprecated
    public final boolean startDrag(ClipData data, DragShadowBuilder shadowBuilder, Object myLocalState, int flags) {
        return startDragAndDrop(data, shadowBuilder, myLocalState, flags);
    }

    /* JADX WARN: Removed duplicated region for block: B:83:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x017a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startDragAndDrop(ClipData data, DragShadowBuilder shadowBuilder, Object myLocalState, int flags) {
        Surface surface;
        SurfaceSession session;
        if (this.mAttachInfo == null) {
            Log.m64w(VIEW_LOG_TAG, "startDragAndDrop called on a detached view.");
            return false;
        } else if (!this.mAttachInfo.mViewRootImpl.mSurface.isValid()) {
            Log.m64w(VIEW_LOG_TAG, "startDragAndDrop called with an invalid surface.");
            return false;
        } else {
            if (data != null) {
                data.prepareToLeaveProcess((flags & 256) != 0);
            }
            Point shadowSize = new Point();
            Point shadowTouchPoint = new Point();
            shadowBuilder.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            if (shadowSize.f59x < 0 || shadowSize.f60y < 0 || shadowTouchPoint.f59x < 0 || shadowTouchPoint.f60y < 0) {
                throw new IllegalStateException("Drag shadow dimensions must not be negative");
            }
            if (shadowSize.f59x == 0 || shadowSize.f60y == 0) {
                if (!sAcceptZeroSizeDragShadow) {
                    throw new IllegalStateException("Drag shadow dimensions must be positive");
                }
                shadowSize.f59x = 1;
                shadowSize.f60y = 1;
            }
            ViewRootImpl root = this.mAttachInfo.mViewRootImpl;
            SurfaceSession session2 = new SurfaceSession();
            SurfaceControl surfaceControl = new SurfaceControl.Builder(session2).setName("drag surface").setParent(root.getSurfaceControl()).setBufferSize(shadowSize.f59x, shadowSize.f60y).setFormat(-3).build();
            Surface surface2 = new Surface();
            surface2.copyFrom(surfaceControl);
            IBinder token = null;
            try {
                Canvas canvas = surface2.lockCanvas(null);
                try {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    shadowBuilder.onDrawShadow(canvas);
                    surface2.unlockCanvasAndPost(canvas);
                    root.getLastTouchPoint(shadowSize);
                    surface = surface2;
                    session = session2;
                    try {
                        IBinder token2 = this.mAttachInfo.mSession.performDrag(this.mAttachInfo.mWindow, flags, surfaceControl, root.getLastTouchSource(), shadowSize.f59x, shadowSize.f60y, shadowTouchPoint.f59x, shadowTouchPoint.f60y, data);
                        if (token2 != null) {
                            try {
                                if (this.mAttachInfo.mDragSurface != null) {
                                    this.mAttachInfo.mDragSurface.release();
                                }
                                this.mAttachInfo.mDragSurface = surface;
                                this.mAttachInfo.mDragToken = token2;
                            } catch (Exception e) {
                                e = e;
                            } catch (Throwable th) {
                                e = th;
                            }
                            try {
                                root.setLocalDragState(myLocalState);
                            } catch (Exception e2) {
                                e = e2;
                                token = token2;
                                Log.m69e(VIEW_LOG_TAG, "Unable to initiate drag", e);
                                if (token == null) {
                                }
                                session.kill();
                                return false;
                            } catch (Throwable th2) {
                                e = th2;
                                token = token2;
                                if (token == null) {
                                }
                                session.kill();
                                throw e;
                            }
                        }
                        boolean z = token2 != null;
                        if (token2 == null) {
                            surface.destroy();
                        }
                        session.kill();
                        return z;
                    } catch (Exception e3) {
                        e = e3;
                    } catch (Throwable th3) {
                        e = th3;
                    }
                } catch (Throwable th4) {
                    surface = surface2;
                    session = session2;
                    try {
                        try {
                            surface.unlockCanvasAndPost(canvas);
                            throw th4;
                        } catch (Throwable th5) {
                            e = th5;
                            if (token == null) {
                                surface.destroy();
                            }
                            session.kill();
                            throw e;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        Log.m69e(VIEW_LOG_TAG, "Unable to initiate drag", e);
                        if (token == null) {
                            surface.destroy();
                        }
                        session.kill();
                        return false;
                    }
                }
            } catch (Exception e5) {
                e = e5;
                surface = surface2;
                session = session2;
            } catch (Throwable th6) {
                e = th6;
                surface = surface2;
                session = session2;
            }
        }
    }

    public final void cancelDragAndDrop() {
        if (this.mAttachInfo == null) {
            Log.m64w(VIEW_LOG_TAG, "cancelDragAndDrop called on a detached view.");
        } else if (this.mAttachInfo.mDragToken != null) {
            try {
                this.mAttachInfo.mSession.cancelDragAndDrop(this.mAttachInfo.mDragToken, false);
            } catch (Exception e) {
                Log.m69e(VIEW_LOG_TAG, "Unable to cancel drag", e);
            }
            this.mAttachInfo.mDragToken = null;
        } else {
            Log.m70e(VIEW_LOG_TAG, "No active drag to cancel");
        }
    }

    public final void updateDragShadow(DragShadowBuilder shadowBuilder) {
        if (this.mAttachInfo == null) {
            Log.m64w(VIEW_LOG_TAG, "updateDragShadow called on a detached view.");
        } else if (this.mAttachInfo.mDragToken != null) {
            try {
                Canvas canvas = this.mAttachInfo.mDragSurface.lockCanvas(null);
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                shadowBuilder.onDrawShadow(canvas);
                this.mAttachInfo.mDragSurface.unlockCanvasAndPost(canvas);
            } catch (Exception e) {
                Log.m69e(VIEW_LOG_TAG, "Unable to update drag shadow", e);
            }
        } else {
            Log.m70e(VIEW_LOG_TAG, "No active drag");
        }
    }

    public final boolean startMovingTask(float startX, float startY) {
        try {
            return this.mAttachInfo.mSession.startMovingTask(this.mAttachInfo.mWindow, startX, startY);
        } catch (RemoteException e) {
            Log.m69e(VIEW_LOG_TAG, "Unable to start moving", e);
            return false;
        }
    }

    public void finishMovingTask() {
        try {
            this.mAttachInfo.mSession.finishMovingTask(this.mAttachInfo.mWindow);
        } catch (RemoteException e) {
            Log.m69e(VIEW_LOG_TAG, "Unable to finish moving", e);
        }
    }

    public boolean onDragEvent(DragEvent event) {
        return false;
    }

    boolean dispatchDragEnterExitInPreN(DragEvent event) {
        return callDragEventHandler(event);
    }

    public boolean dispatchDragEvent(DragEvent event) {
        event.mEventHandlerWasCalled = true;
        if (event.mAction == 2 || event.mAction == 3) {
            getViewRootImpl().setDragFocus(this, event);
        }
        return callDragEventHandler(event);
    }

    final boolean callDragEventHandler(DragEvent event) {
        boolean result;
        ListenerInfo li = this.mListenerInfo;
        if (li != null && li.mOnDragListener != null && (this.mViewFlags & 32) == 0 && li.mOnDragListener.onDrag(this, event)) {
            result = true;
        } else {
            result = onDragEvent(event);
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

    boolean canAcceptDrag() {
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
        if (r != null && attachInfo != null) {
            int w = getRight() - getLeft();
            int h = getBottom() - getTop();
            if (db.left > 0) {
                r.m126op(0, 0, db.left, h, Region.EnumC0685Op.UNION);
            }
            if (db.right < w) {
                r.m126op(db.right, 0, w, h, Region.EnumC0685Op.UNION);
            }
            if (db.top > 0) {
                r.m126op(0, 0, w, db.top, Region.EnumC0685Op.UNION);
            }
            if (db.bottom < h) {
                r.m126op(0, db.bottom, w, h, Region.EnumC0685Op.UNION);
            }
            int[] location = attachInfo.mTransparentLocation;
            getLocationInWindow(location);
            r.translate(location[0], location[1]);
            region.m123op(r, Region.EnumC0685Op.INTERSECT);
            return;
        }
        region.m125op(db, Region.EnumC0685Op.DIFFERENCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
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
        LayoutInflater factory = LayoutInflater.from(context);
        return factory.inflate(resource, root);
    }

    /* JADX INFO: Access modifiers changed from: protected */
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
        int overScrollMode2 = -maxOverScrollY2;
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
        boolean clampedY2 = clampedY;
        onOverScrolled(newScrollX, newScrollY, clampedX2, clampedY2);
        return clampedX2 || clampedY2;
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    }

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public void setOverScrollMode(int overScrollMode) {
        if (overScrollMode != 0 && overScrollMode != 1 && overScrollMode != 2) {
            throw new IllegalArgumentException("Invalid overscroll mode " + overScrollMode);
        }
        this.mOverScrollMode = overScrollMode;
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
        if (isNestedScrollingEnabled()) {
            ViewParent p = getParent();
            View child = this;
            for (ViewParent p2 = p; p2 != null; p2 = p2.getParent()) {
                try {
                    if (p2.onStartNestedScroll(child, this, axes)) {
                        this.mNestedScrollingParent = p2;
                        p2.onNestedScrollAccepted(child, this, axes);
                        return true;
                    }
                } catch (AbstractMethodError e) {
                    Log.m69e(VIEW_LOG_TAG, "ViewParent " + p2 + " does not implement interface method onStartNestedScroll", e);
                }
                if (p2 instanceof View) {
                    View child2 = p2;
                    child = child2;
                }
            }
            return false;
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
                return (consumed[0] == 0 && consumed[1] == 0) ? false : true;
            } else if (offsetInWindow != null) {
                offsetInWindow[0] = 0;
                offsetInWindow[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return this.mNestedScrollingParent.onNestedFling(this, velocityX, velocityY, consumed);
        }
        return false;
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return this.mNestedScrollingParent.onNestedPreFling(this, velocityX, velocityY);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @UnsupportedAppUsage
    public float getVerticalScrollFactor() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue outValue = new TypedValue();
            if (!this.mContext.getTheme().resolveAttribute(16842829, outValue, true)) {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = outValue.getDimension(this.mContext.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @UnsupportedAppUsage
    public float getHorizontalScrollFactor() {
        return getVerticalScrollFactor();
    }

    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, m46to = "INHERIT"), @ViewDebug.IntToString(from = 1, m46to = "FIRST_STRONG"), @ViewDebug.IntToString(from = 2, m46to = "ANY_RTL"), @ViewDebug.IntToString(from = 3, m46to = "LTR"), @ViewDebug.IntToString(from = 4, m46to = "RTL"), @ViewDebug.IntToString(from = 5, m46to = "LOCALE"), @ViewDebug.IntToString(from = 6, m46to = "FIRST_STRONG_LTR"), @ViewDebug.IntToString(from = 7, m46to = "FIRST_STRONG_RTL")})
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

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, m46to = "INHERIT"), @ViewDebug.IntToString(from = 1, m46to = "FIRST_STRONG"), @ViewDebug.IntToString(from = 2, m46to = "ANY_RTL"), @ViewDebug.IntToString(from = 3, m46to = "LTR"), @ViewDebug.IntToString(from = 4, m46to = "RTL"), @ViewDebug.IntToString(from = 5, m46to = "LOCALE"), @ViewDebug.IntToString(from = 6, m46to = "FIRST_STRONG_LTR"), @ViewDebug.IntToString(from = 7, m46to = "FIRST_STRONG_RTL")})
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
                        if (!this.mParent.isTextDirectionResolved()) {
                            this.mPrivateFlags2 |= 1024;
                            return false;
                        }
                        try {
                            parentResolvedDirection = this.mParent.getTextDirection();
                        } catch (AbstractMethodError e) {
                            Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
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
                    } catch (AbstractMethodError e2) {
                        Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
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
        if (getRawTextDirection() == 0) {
            if (this.mParent != null) {
                try {
                    return this.mParent.canResolveTextDirection();
                } catch (AbstractMethodError e) {
                    Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                    return false;
                }
            }
            return false;
        }
        return true;
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

    @UnsupportedAppUsage
    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, m46to = "INHERIT"), @ViewDebug.IntToString(from = 1, m46to = "GRAVITY"), @ViewDebug.IntToString(from = 2, m46to = "TEXT_START"), @ViewDebug.IntToString(from = 3, m46to = "TEXT_END"), @ViewDebug.IntToString(from = 4, m46to = "CENTER"), @ViewDebug.IntToString(from = 5, m46to = "VIEW_START"), @ViewDebug.IntToString(from = 6, m46to = "VIEW_END")})
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

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, m46to = "INHERIT"), @ViewDebug.IntToString(from = 1, m46to = "GRAVITY"), @ViewDebug.IntToString(from = 2, m46to = "TEXT_START"), @ViewDebug.IntToString(from = 3, m46to = "TEXT_END"), @ViewDebug.IntToString(from = 4, m46to = "CENTER"), @ViewDebug.IntToString(from = 5, m46to = "VIEW_START"), @ViewDebug.IntToString(from = 6, m46to = "VIEW_END")})
    public int getTextAlignment() {
        return (this.mPrivateFlags2 & 917504) >> 17;
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
                        if (!this.mParent.isTextAlignmentResolved()) {
                            this.mPrivateFlags2 = 131072 | this.mPrivateFlags2;
                            return false;
                        }
                        try {
                            parentResolvedTextAlignment = this.mParent.getTextAlignment();
                        } catch (AbstractMethodError e) {
                            Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
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
                    } catch (AbstractMethodError e2) {
                        Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
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
        if (getRawTextAlignment() == 0) {
            if (this.mParent != null) {
                try {
                    return this.mParent.canResolveTextAlignment();
                } catch (AbstractMethodError e) {
                    Log.m69e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                    return false;
                }
            }
            return false;
        }
        return true;
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
        return ((-16777216) & id) == 0 && (16777215 & id) != 0;
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
        if (this.mAttachInfo == null || this.mAttachInfo.mHandlingPointerEvent) {
            return;
        }
        try {
            this.mAttachInfo.mSession.updatePointerIcon(this.mAttachInfo.mWindow);
        } catch (RemoteException e) {
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

    /* loaded from: classes4.dex */
    public static class MeasureSpec {
        public static final int AT_MOST = Integer.MIN_VALUE;
        public static final int EXACTLY = 1073741824;
        private static final int MODE_MASK = -1073741824;
        private static final int MODE_SHIFT = 30;
        public static final int UNSPECIFIED = 0;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes4.dex */
        public @interface MeasureSpecMode {
        }

        public static int makeMeasureSpec(int size, int mode) {
            if (View.sUseBrokenMakeMeasureSpec) {
                return size + mode;
            }
            return (1073741823 & size) | ((-1073741824) & mode);
        }

        @UnsupportedAppUsage
        public static int makeSafeMeasureSpec(int size, int mode) {
            if (View.sUseZeroUnspecifiedMeasureSpec && mode == 0) {
                return 0;
            }
            return makeMeasureSpec(size, mode);
        }

        public static int getMode(int measureSpec) {
            return (-1073741824) & measureSpec;
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
                Log.m70e(View.VIEW_LOG_TAG, "MeasureSpec.adjust: new size would be negative! (" + size2 + ") spec: " + toString(measureSpec) + " delta: " + delta);
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

    /* loaded from: classes4.dex */
    private final class CheckForLongPress implements Runnable {
        private int mClassification;
        private boolean mOriginalPressedState;
        private int mOriginalWindowAttachCount;

        /* renamed from: mX */
        private float f2413mX;

        /* renamed from: mY */
        private float f2414mY;

        private CheckForLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mOriginalPressedState == View.this.isPressed() && View.this.mParent != null && this.mOriginalWindowAttachCount == View.this.mWindowAttachCount) {
                View.this.recordGestureClassification(this.mClassification);
                if (View.this.performLongClick(this.f2413mX, this.f2414mY)) {
                    View.this.mHasPerformedLongPress = true;
                }
            }
        }

        public void setAnchor(float x, float y) {
            this.f2413mX = x;
            this.f2414mY = y;
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

    /* loaded from: classes4.dex */
    private final class CheckForTap implements Runnable {

        /* renamed from: x */
        public float f2415x;

        /* renamed from: y */
        public float f2416y;

        private CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            View.this.mPrivateFlags &= -33554433;
            View.this.setPressed(true, this.f2415x, this.f2416y);
            long delay = ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout();
            View.this.checkForLongClick(delay, this.f2415x, this.f2416y, 3);
        }
    }

    /* loaded from: classes4.dex */
    private final class PerformClick implements Runnable {
        private PerformClick() {
        }

        @Override // java.lang.Runnable
        public void run() {
            View.this.recordGestureClassification(1);
            View.this.performClickInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordGestureClassification(int classification) {
        if (classification == 0) {
            return;
        }
        StatsLog.write(177, getClass().getName(), classification);
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

    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> data, int deviceId) {
    }

    /* loaded from: classes4.dex */
    private final class UnsetPressedState implements Runnable {
        private UnsetPressedState() {
        }

        @Override // java.lang.Runnable
        public void run() {
            View.this.setPressed(false);
        }
    }

    /* loaded from: classes4.dex */
    private static class VisibilityChangeForAutofillHandler extends Handler {
        private final AutofillManager mAfm;
        private final View mView;

        private VisibilityChangeForAutofillHandler(AutofillManager afm, View view) {
            this.mAfm = afm;
            this.mView = view;
        }

        @Override // android.p007os.Handler
        public void handleMessage(Message msg) {
            this.mAfm.notifyViewVisibilityChanged(this.mView, this.mView.isShown());
        }
    }

    /* loaded from: classes4.dex */
    public static class BaseSavedState extends AbsSavedState {
        static final int AUTOFILL_ID = 4;
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.ClassLoaderCreator<BaseSavedState>() { // from class: android.view.View.BaseSavedState.1
            @Override // android.p007os.Parcelable.Creator
            public BaseSavedState createFromParcel(Parcel in) {
                return new BaseSavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.p007os.Parcelable.ClassLoaderCreator
            public BaseSavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new BaseSavedState(in, loader);
            }

            @Override // android.p007os.Parcelable.Creator
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
            this(source, null);
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

        @Override // android.view.AbsSavedState, android.p007os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mSavedData);
            out.writeString(this.mStartActivityRequestWhoSaved);
            out.writeBoolean(this.mIsAutofilled);
            out.writeInt(this.mAutofillViewId);
        }
    }

    /* loaded from: classes4.dex */
    static final class AttachInfo {
        int mAccessibilityFetchFlags;
        Drawable mAccessibilityFocusDrawable;
        boolean mAlwaysConsumeSystemBars;
        @UnsupportedAppUsage
        float mApplicationScale;
        Drawable mAutofilledDrawable;
        Canvas mCanvas;
        int mDisabledSystemUiVisibility;
        Display mDisplay;
        public Surface mDragSurface;
        IBinder mDragToken;
        @UnsupportedAppUsage
        long mDrawingTime;
        boolean mForceReportNewAttributes;
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
        @UnsupportedAppUsage
        boolean mKeepScreenOn;
        boolean mNeedsUpdateLightCenter;
        boolean mOverscanRequested;
        IBinder mPanelParentWindowToken;
        List<RenderNode> mPendingAnimatingRenderNodes;
        @UnsupportedAppUsage
        boolean mRecomputeGlobalAttributes;
        final Callbacks mRootCallbacks;
        View mRootView;
        @UnsupportedAppUsage
        boolean mScalingRequired;
        @UnsupportedAppUsage
        final IWindowSession mSession;
        int mSystemUiVisibility;
        ThreadedRenderer mThreadedRenderer;
        View mTooltipHost;
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
        final IWindow mWindow;
        WindowId mWindowId;
        int mWindowLeft;
        final IBinder mWindowToken;
        int mWindowTop;
        int mWindowVisibility;
        @UnsupportedAppUsage
        int mDisplayState = 0;
        final Rect mOverscanInsets = new Rect();
        @UnsupportedAppUsage
        final Rect mContentInsets = new Rect();
        @UnsupportedAppUsage
        final Rect mVisibleInsets = new Rect();
        @UnsupportedAppUsage
        final Rect mStableInsets = new Rect();
        final DisplayCutout.ParcelableWrapper mDisplayCutout = new DisplayCutout.ParcelableWrapper(DisplayCutout.NO_CUTOUT);
        final Rect mOutsets = new Rect();
        @UnsupportedAppUsage
        final ViewTreeObserver.InternalInsetsInfo mGivenInternalInsets = new ViewTreeObserver.InternalInsetsInfo();
        @UnsupportedAppUsage
        final ArrayList<View> mScrollContainers = new ArrayList<>();
        @UnsupportedAppUsage
        final KeyEvent.DispatcherState mKeyDispatchState = new KeyEvent.DispatcherState();
        int mGlobalSystemUiVisibility = -1;
        final int[] mTransparentLocation = new int[2];
        final int[] mInvalidateChildLocation = new int[2];
        final int[] mTmpLocation = new int[2];
        final float[] mTmpTransformLocation = new float[2];
        final Rect mTmpInvalRect = new Rect();
        final RectF mTmpTransformRect = new RectF();
        final RectF mTmpTransformRect1 = new RectF();
        final List<RectF> mTmpRectList = new ArrayList();
        final Matrix mTmpMatrix = new Matrix();
        final Transformation mTmpTransformation = new Transformation();
        final Outline mTmpOutline = new Outline();
        final ArrayList<View> mTempArrayList = new ArrayList<>(24);
        int mAccessibilityWindowId = -1;
        boolean mDebugLayout = DisplayProperties.debug_layout().orElse(false).booleanValue();
        final Point mPoint = new Point();

        /* loaded from: classes4.dex */
        interface Callbacks {
            boolean performHapticFeedback(int i, boolean z);

            void playSoundEffect(int i);
        }

        /* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    private static class ScrollabilityCache implements Runnable {
        public static final int DRAGGING_HORIZONTAL_SCROLL_BAR = 2;
        public static final int DRAGGING_VERTICAL_SCROLL_BAR = 1;
        public static final int FADING = 2;
        public static final int NOT_DRAGGING = 0;
        public static final int OFF = 0;

        /* renamed from: ON */
        public static final int f2417ON = 1;
        private static final float[] OPAQUE = {255.0f};
        private static final float[] TRANSPARENT = {0.0f};
        public boolean fadeScrollBars;
        public long fadeStartTime;
        public int fadingEdgeLength;
        @UnsupportedAppUsage
        public View host;
        public float[] interpolatorValues;
        private int mLastColor;
        @UnsupportedAppUsage
        public ScrollBarDrawable scrollBar;
        public int scrollBarMinTouchTarget;
        public int scrollBarSize;
        public final Interpolator scrollBarInterpolator = new Interpolator(1, 2);
        @UnsupportedAppUsage
        public int state = 0;
        public final Rect mScrollBarBounds = new Rect();
        public final Rect mScrollBarTouchBounds = new Rect();
        public int mScrollBarDraggingState = 0;
        public float mScrollBarDraggingPos = 0.0f;
        public int scrollBarDefaultDelayBeforeFade = ViewConfiguration.getScrollDefaultDelay();
        public int scrollBarFadeDuration = ViewConfiguration.getScrollBarFadeDuration();
        public final Paint paint = new Paint();
        public final Matrix matrix = new Matrix();
        public Shader shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);

        public ScrollabilityCache(ViewConfiguration configuration, View host) {
            this.fadingEdgeLength = configuration.getScaledFadingEdgeLength();
            this.scrollBarSize = configuration.getScaledScrollBarSize();
            this.scrollBarMinTouchTarget = configuration.getScaledMinScrollbarTouchTarget();
            this.paint.setShader(this.shader);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.host = host;
        }

        public void setFadeColor(int color) {
            if (color != this.mLastColor) {
                this.mLastColor = color;
                if (color != 0) {
                    this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, color | (-16777216), color & 16777215, Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(null);
                    return;
                }
                this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
                this.paint.setShader(this.shader);
                this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            if (now >= this.fadeStartTime) {
                int nextFrame = (int) now;
                Interpolator interpolator = this.scrollBarInterpolator;
                int framesCount = 0 + 1;
                interpolator.setKeyFrame(0, nextFrame, OPAQUE);
                int framesCount2 = this.scrollBarFadeDuration;
                interpolator.setKeyFrame(framesCount, nextFrame + framesCount2, TRANSPARENT);
                this.state = 2;
                this.host.invalidate(true);
            }
        }
    }

    /* loaded from: classes4.dex */
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

        @Override // java.lang.Runnable
        public void run() {
            if (AccessibilityManager.getInstance(View.this.mContext).isEnabled()) {
                AccessibilityEvent event = AccessibilityEvent.obtain(4096);
                event.setScrollDeltaX(this.mDeltaX);
                event.setScrollDeltaY(this.mDeltaY);
                View.this.sendAccessibilityEventUnchecked(event);
            }
            reset();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.mIsPending = false;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    @UnsupportedAppUsage
    private void cancel(SendViewScrolledAccessibilityEvent callback) {
        if (callback == null || !callback.mIsPending) {
            return;
        }
        removeCallbacks(callback);
        callback.reset();
    }

    /* loaded from: classes4.dex */
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

    /* loaded from: classes4.dex */
    private static class MatchIdPredicate implements Predicate<View> {
        public int mId;

        private MatchIdPredicate() {
        }

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            return view.mID == this.mId;
        }
    }

    /* loaded from: classes4.dex */
    private static class MatchLabelForPredicate implements Predicate<View> {
        private int mLabeledId;

        private MatchLabelForPredicate() {
        }

        @Override // java.util.function.Predicate
        public boolean test(View view) {
            return view.mLabelForId == this.mLabeledId;
        }
    }

    private static void dumpFlags() {
        Field[] declaredFields;
        HashMap<String, String> found = Maps.newHashMap();
        try {
            for (Field field : View.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
                    if (field.getType().equals(Integer.TYPE)) {
                        int value = field.getInt(null);
                        dumpFlag(found, field.getName(), value);
                    } else if (field.getType().equals(int[].class)) {
                        int[] values = (int[]) field.get(null);
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
                String key = it.next();
                Log.m72d(VIEW_LOG_TAG, found.get(key));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void dumpFlag(HashMap<String, String> found, String name, int value) {
        String bits = String.format("%32s", Integer.toBinaryString(value)).replace('0', ' ');
        int prefix = name.indexOf(95);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix > 0 ? name.substring(0, prefix) : name);
        sb.append(bits);
        sb.append(name);
        String key = sb.toString();
        String output = bits + WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER + name;
        found.put(key, output);
    }

    public void encode(ViewHierarchyEncoder stream) {
        stream.beginObject(this);
        encodeProperties(stream);
        stream.endObject();
    }

    protected void encodeProperties(ViewHierarchyEncoder stream) {
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

    boolean shouldDrawRoundScrollbar() {
        if (!this.mResources.getConfiguration().isScreenRound() || this.mAttachInfo == null) {
            return false;
        }
        View rootView = getRootView();
        WindowInsets insets = getRootWindowInsets();
        int height = getHeight();
        int width = getWidth();
        int displayHeight = rootView.getHeight();
        int displayWidth = rootView.getWidth();
        if (height == displayHeight && width == displayWidth) {
            getLocationInWindow(this.mAttachInfo.mTmpLocation);
            return this.mAttachInfo.mTmpLocation[0] == insets.getStableInsetLeft() && this.mAttachInfo.mTmpLocation[1] == insets.getStableInsetTop();
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
            this.mTooltipInfo.mShowTooltipRunnable = new Runnable() { // from class: android.view.-$$Lambda$View$llq76MkPXP4bNcb9oJt_msw0fnQ
                @Override // java.lang.Runnable
                public final void run() {
                    View.this.showHoverTooltip();
                }
            };
            this.mTooltipInfo.mHideTooltipRunnable = new Runnable() { // from class: android.view.-$$Lambda$QI1s392qW8l6mC24bcy9050SkuY
                @Override // java.lang.Runnable
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
        if ((!fromLongClick || (this.mViewFlags & 32) == 0) && !TextUtils.isEmpty(this.mTooltipInfo.mTooltipText)) {
            hideTooltip();
            this.mTooltipInfo.mTooltipFromLongClick = fromLongClick;
            this.mTooltipInfo.mTooltipPopup = new TooltipPopup(getContext());
            boolean fromTouch = (this.mPrivateFlags3 & 131072) == 131072;
            this.mTooltipInfo.mTooltipPopup.show(this, x, y, fromTouch, this.mTooltipInfo.mTooltipText);
            this.mAttachInfo.mTooltipHost = this;
            notifyViewAccessibilityStateChangedIfNeeded(0);
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    void hideTooltip() {
        if (this.mTooltipInfo == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        if (this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        this.mTooltipInfo.mTooltipPopup.hide();
        this.mTooltipInfo.mTooltipPopup = null;
        this.mTooltipInfo.mTooltipFromLongClick = false;
        this.mTooltipInfo.clearAnchorPos();
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mTooltipHost = null;
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    private boolean showLongClickTooltip(int x, int y) {
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        return showTooltip(x, y, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showHoverTooltip() {
        return showTooltip(this.mTooltipInfo.mAnchorX, this.mTooltipInfo.mAnchorY, false);
    }

    boolean dispatchTooltipHoverEvent(MotionEvent event) {
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
                    postDelayed(this.mTooltipInfo.mShowTooltipRunnable, ViewConfiguration.getHoverTooltipShowTimeout());
                }
                if ((getWindowSystemUiVisibility() & 1) == 1) {
                    timeout = ViewConfiguration.getHoverTooltipHideShortTimeout();
                } else {
                    timeout = ViewConfiguration.getHoverTooltipHideTimeout();
                }
                removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
                postDelayed(this.mTooltipInfo.mHideTooltipRunnable, timeout);
            }
            return true;
        }
        return false;
    }

    void handleTooltipKey(KeyEvent event) {
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
        if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        postDelayed(this.mTooltipInfo.mHideTooltipRunnable, ViewConfiguration.getLongPressTooltipHideTimeout());
    }

    private int getFocusableAttribute(TypedArray attributes) {
        TypedValue val = new TypedValue();
        if (attributes.getValue(19, val)) {
            if (val.type == 18) {
                return val.data == 0 ? 0 : 1;
            }
            return val.data;
        }
        return 16;
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

    View dispatchUnhandledKeyEvent(KeyEvent evt) {
        if (onUnhandledKeyEvent(evt)) {
            return this;
        }
        return null;
    }

    boolean onUnhandledKeyEvent(KeyEvent event) {
        if (this.mListenerInfo != null && this.mListenerInfo.mUnhandledKeyListeners != null) {
            for (int i = this.mListenerInfo.mUnhandledKeyListeners.size() - 1; i >= 0; i--) {
                if (((OnUnhandledKeyEventListener) this.mListenerInfo.mUnhandledKeyListeners.get(i)).onUnhandledKeyEvent(this, event)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    boolean hasUnhandledKeyListener() {
        return (this.mListenerInfo == null || this.mListenerInfo.mUnhandledKeyListeners == null || this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) ? false : true;
    }

    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        ArrayList<OnUnhandledKeyEventListener> listeners = getListenerInfo().mUnhandledKeyListeners;
        if (listeners == null) {
            listeners = new ArrayList<>();
            getListenerInfo().mUnhandledKeyListeners = listeners;
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
                this.mListenerInfo.mUnhandledKeyListeners = null;
                if (this.mParent instanceof ViewGroup) {
                    ((ViewGroup) this.mParent).decrementChildUnhandledKeyListeners();
                }
            }
        }
    }
}
