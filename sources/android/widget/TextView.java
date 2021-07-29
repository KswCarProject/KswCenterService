package android.widget;

import android.Manifest;
import android.R;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.UndoManager;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.BaseCanvas;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.icu.text.DecimalFormatSymbols;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ParcelableParcel;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Downloads;
import android.provider.Settings;
import android.text.BoringLayout;
import android.text.DynamicLayout;
import android.text.Editable;
import android.text.GetChars;
import android.text.GraphicsOperations;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.PrecomputedText;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.AllCapsTransformationMethod;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.DateKeyListener;
import android.text.method.DateTimeKeyListener;
import android.text.method.DialerKeyListener;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.LinkMovementMethod;
import android.text.method.MetaKeyKeyListener;
import android.text.method.MovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TextKeyListener;
import android.text.method.TimeKeyListener;
import android.text.method.TransformationMethod;
import android.text.method.TransformationMethod2;
import android.text.method.WordIterator;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ParagraphStyle;
import android.text.style.SpellCheckSpan;
import android.text.style.SuggestionSpan;
import android.text.style.URLSpan;
import android.text.style.UpdateAppearance;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.IntArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.AccessibilityIterators;
import android.view.ActionMode;
import android.view.Choreographer;
import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.IntFlagMapping;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextLinks;
import android.view.textservice.SpellCheckerSubtype;
import android.view.textservice.TextServicesManager;
import android.widget.AccessibilityIterators;
import android.widget.Editor;
import android.widget.RemoteViews;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.FastMath;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.EditableInputConnection;
import com.ibm.icu.text.DateFormat;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import libcore.util.EmptyArray;
import org.xmlpull.v1.XmlPullParserException;

@RemoteViews.RemoteView
public class TextView extends View implements ViewTreeObserver.OnPreDrawListener {
    static final int ACCESSIBILITY_ACTION_PROCESS_TEXT_START_ID = 268435712;
    private static final int ACCESSIBILITY_ACTION_SHARE = 268435456;
    private static final int ANIMATED_SCROLL_GAP = 250;
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    private static final int CHANGE_WATCHER_PRIORITY = 100;
    static final boolean DEBUG_EXTRACT = false;
    private static final int DECIMAL = 4;
    private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
    private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
    private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
    private static final int DEFAULT_TYPEFACE = -1;
    private static final int DEVICE_PROVISIONED_NO = 1;
    private static final int DEVICE_PROVISIONED_UNKNOWN = 0;
    private static final int DEVICE_PROVISIONED_YES = 2;
    private static final int ELLIPSIZE_END = 3;
    private static final int ELLIPSIZE_MARQUEE = 4;
    private static final int ELLIPSIZE_MIDDLE = 2;
    private static final int ELLIPSIZE_NONE = 0;
    private static final int ELLIPSIZE_NOT_SET = -1;
    private static final int ELLIPSIZE_START = 1;
    private static final Spanned EMPTY_SPANNED = new SpannedString("");
    private static final int EMS = 1;
    private static final int FLOATING_TOOLBAR_SELECT_ALL_REFRESH_DELAY = 500;
    static final int ID_ASSIST = 16908353;
    static final int ID_AUTOFILL = 16908355;
    static final int ID_COPY = 16908321;
    static final int ID_CUT = 16908320;
    static final int ID_PASTE = 16908322;
    static final int ID_PASTE_AS_PLAIN_TEXT = 16908337;
    static final int ID_REDO = 16908339;
    static final int ID_REPLACE = 16908340;
    static final int ID_SELECT_ALL = 16908319;
    static final int ID_SHARE = 16908341;
    static final int ID_UNDO = 16908338;
    private static final int KEY_DOWN_HANDLED_BY_KEY_LISTENER = 1;
    private static final int KEY_DOWN_HANDLED_BY_MOVEMENT_METHOD = 2;
    private static final int KEY_EVENT_HANDLED = -1;
    private static final int KEY_EVENT_NOT_HANDLED = 0;
    @UnsupportedAppUsage
    private static final int LINES = 1;
    static final String LOG_TAG = "TextView";
    private static final int MARQUEE_FADE_NORMAL = 0;
    private static final int MARQUEE_FADE_SWITCH_SHOW_ELLIPSIS = 1;
    private static final int MARQUEE_FADE_SWITCH_SHOW_FADE = 2;
    private static final int MONOSPACE = 3;
    private static final int[] MULTILINE_STATE_SET = {16843597};
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final int PIXELS = 2;
    static final int PROCESS_TEXT_REQUEST_CODE = 100;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int SIGNED = 2;
    private static final float[] TEMP_POSITION = new float[2];
    private static final RectF TEMP_RECTF = new RectF();
    @VisibleForTesting
    public static final BoringLayout.Metrics UNKNOWN_BORING = new BoringLayout.Metrics();
    private static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0f;
    static final int VERY_WIDE = 1048576;
    private static final SparseIntArray sAppearanceValues = new SparseIntArray();
    static long sLastCutCopyOrTextChangedTime;
    @UnsupportedAppUsage
    private boolean mAllowTransformationLengthChange;
    private int mAutoLinkMask;
    private float mAutoSizeMaxTextSizeInPx;
    private float mAutoSizeMinTextSizeInPx;
    private float mAutoSizeStepGranularityInPx;
    private int[] mAutoSizeTextSizesInPx;
    private int mAutoSizeTextType;
    @UnsupportedAppUsage
    private BoringLayout.Metrics mBoring;
    private int mBreakStrategy;
    @UnsupportedAppUsage
    private BufferType mBufferType;
    @UnsupportedAppUsage
    private ChangeWatcher mChangeWatcher;
    @UnsupportedAppUsage
    private CharWrapper mCharWrapper;
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mCurHintTextColor;
    @ViewDebug.ExportedProperty(category = "text")
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private int mCurTextColor;
    private volatile Locale mCurrentSpellCheckerLocaleCache;
    private Drawable mCursorDrawable;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    int mCursorDrawableRes;
    private int mDeferScroll;
    @UnsupportedAppUsage
    private int mDesiredHeightAtMeasure;
    private int mDeviceProvisionedState;
    @UnsupportedAppUsage
    Drawables mDrawables;
    @UnsupportedAppUsage
    private Editable.Factory mEditableFactory;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public Editor mEditor;
    private TextUtils.TruncateAt mEllipsize;
    private InputFilter[] mFilters;
    private boolean mFreezesText;
    @ViewDebug.ExportedProperty(category = "text")
    @UnsupportedAppUsage
    private int mGravity;
    private boolean mHasPresetAutoSizeValues;
    @UnsupportedAppUsage
    int mHighlightColor;
    @UnsupportedAppUsage
    private final Paint mHighlightPaint;
    private Path mHighlightPath;
    @UnsupportedAppUsage
    private boolean mHighlightPathBogus;
    private CharSequence mHint;
    @UnsupportedAppUsage
    private BoringLayout.Metrics mHintBoring;
    @UnsupportedAppUsage
    private Layout mHintLayout;
    private ColorStateList mHintTextColor;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    private boolean mHorizontallyScrolling;
    private int mHyphenationFrequency;
    @UnsupportedAppUsage
    private boolean mIncludePad;
    private int mJustificationMode;
    private int mLastLayoutDirection;
    private long mLastScroll;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public Layout mLayout;
    private ColorStateList mLinkTextColor;
    private boolean mLinksClickable;
    private boolean mListenerChanged;
    @UnsupportedAppUsage(trackingBug = 123769451)
    private ArrayList<TextWatcher> mListeners;
    private boolean mLocalesChanged;
    @UnsupportedAppUsage(trackingBug = 124050217)
    private Marquee mMarquee;
    @UnsupportedAppUsage
    private int mMarqueeFadeMode;
    private int mMarqueeRepeatLimit;
    @UnsupportedAppUsage
    private int mMaxMode;
    @UnsupportedAppUsage
    private int mMaxWidth;
    @UnsupportedAppUsage
    private int mMaxWidthMode;
    @UnsupportedAppUsage
    private int mMaximum;
    @UnsupportedAppUsage
    private int mMinMode;
    @UnsupportedAppUsage
    private int mMinWidth;
    @UnsupportedAppUsage
    private int mMinWidthMode;
    @UnsupportedAppUsage
    private int mMinimum;
    private MovementMethod mMovement;
    private boolean mNeedsAutoSizeText;
    @UnsupportedAppUsage
    private int mOldMaxMode;
    @UnsupportedAppUsage
    private int mOldMaximum;
    private boolean mPreDrawListenerDetached;
    private boolean mPreDrawRegistered;
    private PrecomputedText mPrecomputed;
    private boolean mPreventDefaultMovement;
    @UnsupportedAppUsage
    private boolean mRestartMarquee;
    @UnsupportedAppUsage
    private BoringLayout mSavedHintLayout;
    @UnsupportedAppUsage
    private BoringLayout mSavedLayout;
    @UnsupportedAppUsage
    private Layout mSavedMarqueeModeLayout;
    private Scroller mScroller;
    private int mShadowColor;
    @UnsupportedAppUsage
    private float mShadowDx;
    @UnsupportedAppUsage
    private float mShadowDy;
    @UnsupportedAppUsage
    private float mShadowRadius;
    @UnsupportedAppUsage
    private boolean mSingleLine;
    @UnsupportedAppUsage
    private float mSpacingAdd;
    @UnsupportedAppUsage
    private float mSpacingMult;
    private Spannable mSpannable;
    @UnsupportedAppUsage
    private Spannable.Factory mSpannableFactory;
    private Rect mTempRect;
    private TextPaint mTempTextPaint;
    @ViewDebug.ExportedProperty(category = "text")
    @UnsupportedAppUsage
    private CharSequence mText;
    private TextClassificationContext mTextClassificationContext;
    private TextClassifier mTextClassificationSession;
    private TextClassifier mTextClassifier;
    private ColorStateList mTextColor;
    @UnsupportedAppUsage
    private TextDirectionHeuristic mTextDir;
    int mTextEditSuggestionContainerLayout;
    int mTextEditSuggestionHighlightStyle;
    int mTextEditSuggestionItemLayout;
    private int mTextId;
    private UserHandle mTextOperationUser;
    @UnsupportedAppUsage
    private final TextPaint mTextPaint;
    private Drawable mTextSelectHandle;
    private Drawable mTextSelectHandleLeft;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    int mTextSelectHandleLeftRes;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    int mTextSelectHandleRes;
    private Drawable mTextSelectHandleRight;
    @UnsupportedAppUsage(maxTargetSdk = 28)
    int mTextSelectHandleRightRes;
    private boolean mTextSetFromXmlOrResourceId;
    private TransformationMethod mTransformation;
    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public CharSequence mTransformed;
    boolean mUseFallbackLineSpacing;
    private final boolean mUseInternationalizedInput;
    @UnsupportedAppUsage
    private boolean mUserSetTextScaleX;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    public enum BufferType {
        NORMAL,
        SPANNABLE,
        EDITABLE
    }

    public interface OnEditorActionListener {
        boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface XMLTypefaceAttr {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<TextView> {
        private int mAutoLinkId;
        private int mAutoSizeMaxTextSizeId;
        private int mAutoSizeMinTextSizeId;
        private int mAutoSizeStepGranularityId;
        private int mAutoSizeTextTypeId;
        private int mBreakStrategyId;
        private int mCursorVisibleId;
        private int mDrawableBlendModeId;
        private int mDrawablePaddingId;
        private int mDrawableTintId;
        private int mDrawableTintModeId;
        private int mElegantTextHeightId;
        private int mEllipsizeId;
        private int mFallbackLineSpacingId;
        private int mFirstBaselineToTopHeightId;
        private int mFontFeatureSettingsId;
        private int mFreezesTextId;
        private int mGravityId;
        private int mHintId;
        private int mHyphenationFrequencyId;
        private int mImeActionIdId;
        private int mImeActionLabelId;
        private int mImeOptionsId;
        private int mIncludeFontPaddingId;
        private int mInputTypeId;
        private int mJustificationModeId;
        private int mLastBaselineToBottomHeightId;
        private int mLetterSpacingId;
        private int mLineHeightId;
        private int mLineSpacingExtraId;
        private int mLineSpacingMultiplierId;
        private int mLinksClickableId;
        private int mMarqueeRepeatLimitId;
        private int mMaxEmsId;
        private int mMaxHeightId;
        private int mMaxLinesId;
        private int mMaxWidthId;
        private int mMinEmsId;
        private int mMinLinesId;
        private int mMinWidthId;
        private int mPrivateImeOptionsId;
        private boolean mPropertiesMapped = false;
        private int mScrollHorizontallyId;
        private int mShadowColorId;
        private int mShadowDxId;
        private int mShadowDyId;
        private int mShadowRadiusId;
        private int mSingleLineId;
        private int mTextAllCapsId;
        private int mTextColorHighlightId;
        private int mTextColorHintId;
        private int mTextColorId;
        private int mTextColorLinkId;
        private int mTextId;
        private int mTextIsSelectableId;
        private int mTextScaleXId;
        private int mTextSizeId;
        private int mTypefaceId;

        public void mapProperties(PropertyMapper propertyMapper) {
            IntFlagMapping autoLinkFlagMapping = new IntFlagMapping();
            autoLinkFlagMapping.add(2, 2, "email");
            autoLinkFlagMapping.add(8, 8, "map");
            autoLinkFlagMapping.add(4, 4, "phone");
            autoLinkFlagMapping.add(1, 1, "web");
            Objects.requireNonNull(autoLinkFlagMapping);
            this.mAutoLinkId = propertyMapper.mapIntFlag("autoLink", 16842928, new IntFunction() {
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            this.mAutoSizeMaxTextSizeId = propertyMapper.mapInt("autoSizeMaxTextSize", 16844102);
            this.mAutoSizeMinTextSizeId = propertyMapper.mapInt("autoSizeMinTextSize", 16844088);
            this.mAutoSizeStepGranularityId = propertyMapper.mapInt("autoSizeStepGranularity", 16844086);
            SparseArray<String> autoSizeTextTypeEnumMapping = new SparseArray<>();
            autoSizeTextTypeEnumMapping.put(0, "none");
            autoSizeTextTypeEnumMapping.put(1, "uniform");
            Objects.requireNonNull(autoSizeTextTypeEnumMapping);
            this.mAutoSizeTextTypeId = propertyMapper.mapIntEnum("autoSizeTextType", 16844085, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            SparseArray<String> breakStrategyEnumMapping = new SparseArray<>();
            breakStrategyEnumMapping.put(0, "simple");
            breakStrategyEnumMapping.put(1, "high_quality");
            breakStrategyEnumMapping.put(2, "balanced");
            Objects.requireNonNull(breakStrategyEnumMapping);
            this.mBreakStrategyId = propertyMapper.mapIntEnum("breakStrategy", 16843997, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mCursorVisibleId = propertyMapper.mapBoolean("cursorVisible", 16843090);
            this.mDrawableBlendModeId = propertyMapper.mapObject("drawableBlendMode", 80);
            this.mDrawablePaddingId = propertyMapper.mapInt("drawablePadding", 16843121);
            this.mDrawableTintId = propertyMapper.mapObject("drawableTint", 16843990);
            this.mDrawableTintModeId = propertyMapper.mapObject("drawableTintMode", 16843991);
            this.mElegantTextHeightId = propertyMapper.mapBoolean("elegantTextHeight", 16843869);
            this.mEllipsizeId = propertyMapper.mapObject("ellipsize", 16842923);
            this.mFallbackLineSpacingId = propertyMapper.mapBoolean("fallbackLineSpacing", 16844155);
            this.mFirstBaselineToTopHeightId = propertyMapper.mapInt("firstBaselineToTopHeight", 16844157);
            this.mFontFeatureSettingsId = propertyMapper.mapObject("fontFeatureSettings", 16843959);
            this.mFreezesTextId = propertyMapper.mapBoolean("freezesText", 16843116);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mHintId = propertyMapper.mapObject(Downloads.Impl.COLUMN_FILE_NAME_HINT, 16843088);
            SparseArray<String> hyphenationFrequencyEnumMapping = new SparseArray<>();
            hyphenationFrequencyEnumMapping.put(0, "none");
            hyphenationFrequencyEnumMapping.put(1, Camera.Parameters.FOCUS_MODE_NORMAL);
            hyphenationFrequencyEnumMapping.put(2, "full");
            Objects.requireNonNull(hyphenationFrequencyEnumMapping);
            this.mHyphenationFrequencyId = propertyMapper.mapIntEnum("hyphenationFrequency", 16843998, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mImeActionIdId = propertyMapper.mapInt("imeActionId", 16843366);
            this.mImeActionLabelId = propertyMapper.mapObject("imeActionLabel", 16843365);
            IntFlagMapping imeOptionsFlagMapping = new IntFlagMapping();
            imeOptionsFlagMapping.add(255, 6, "actionDone");
            imeOptionsFlagMapping.add(255, 2, "actionGo");
            imeOptionsFlagMapping.add(255, 5, "actionNext");
            imeOptionsFlagMapping.add(255, 1, "actionNone");
            imeOptionsFlagMapping.add(255, 7, "actionPrevious");
            imeOptionsFlagMapping.add(255, 3, "actionSearch");
            imeOptionsFlagMapping.add(255, 4, "actionSend");
            imeOptionsFlagMapping.add(255, 0, "actionUnspecified");
            imeOptionsFlagMapping.add(Integer.MIN_VALUE, Integer.MIN_VALUE, "flagForceAscii");
            imeOptionsFlagMapping.add(134217728, 134217728, "flagNavigateNext");
            imeOptionsFlagMapping.add(67108864, 67108864, "flagNavigatePrevious");
            imeOptionsFlagMapping.add(536870912, 536870912, "flagNoAccessoryAction");
            imeOptionsFlagMapping.add(1073741824, 1073741824, "flagNoEnterAction");
            imeOptionsFlagMapping.add(268435456, 268435456, "flagNoExtractUi");
            imeOptionsFlagMapping.add(33554432, 33554432, "flagNoFullscreen");
            imeOptionsFlagMapping.add(16777216, 16777216, "flagNoPersonalizedLearning");
            imeOptionsFlagMapping.add(-1, 0, Camera.Parameters.FOCUS_MODE_NORMAL);
            Objects.requireNonNull(imeOptionsFlagMapping);
            this.mImeOptionsId = propertyMapper.mapIntFlag("imeOptions", 16843364, new IntFunction() {
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            this.mIncludeFontPaddingId = propertyMapper.mapBoolean("includeFontPadding", 16843103);
            IntFlagMapping inputTypeFlagMapping = new IntFlagMapping();
            inputTypeFlagMapping.add(4095, 20, "date");
            inputTypeFlagMapping.add(4095, 4, TextClassifier.TYPE_DATE_TIME);
            inputTypeFlagMapping.add(-1, 0, "none");
            inputTypeFlagMapping.add(4095, 2, "number");
            inputTypeFlagMapping.add(16773135, 8194, "numberDecimal");
            inputTypeFlagMapping.add(4095, 18, "numberPassword");
            inputTypeFlagMapping.add(16773135, 4098, "numberSigned");
            inputTypeFlagMapping.add(4095, 3, "phone");
            inputTypeFlagMapping.add(4095, 1, "text");
            inputTypeFlagMapping.add(16773135, 65537, "textAutoComplete");
            inputTypeFlagMapping.add(16773135, 32769, "textAutoCorrect");
            inputTypeFlagMapping.add(16773135, 4097, "textCapCharacters");
            inputTypeFlagMapping.add(16773135, 16385, "textCapSentences");
            inputTypeFlagMapping.add(16773135, 8193, "textCapWords");
            inputTypeFlagMapping.add(4095, 33, "textEmailAddress");
            inputTypeFlagMapping.add(4095, 49, "textEmailSubject");
            inputTypeFlagMapping.add(4095, 177, "textFilter");
            inputTypeFlagMapping.add(16773135, 262145, "textImeMultiLine");
            inputTypeFlagMapping.add(4095, 81, "textLongMessage");
            inputTypeFlagMapping.add(16773135, 131073, "textMultiLine");
            inputTypeFlagMapping.add(16773135, ConnectivityManager.CALLBACK_PRECHECK, "textNoSuggestions");
            inputTypeFlagMapping.add(4095, 129, "textPassword");
            inputTypeFlagMapping.add(4095, 97, "textPersonName");
            inputTypeFlagMapping.add(4095, 193, "textPhonetic");
            inputTypeFlagMapping.add(4095, 113, "textPostalAddress");
            inputTypeFlagMapping.add(4095, 65, "textShortMessage");
            inputTypeFlagMapping.add(4095, 17, "textUri");
            inputTypeFlagMapping.add(4095, 145, "textVisiblePassword");
            inputTypeFlagMapping.add(4095, 161, "textWebEditText");
            inputTypeFlagMapping.add(4095, 209, "textWebEmailAddress");
            inputTypeFlagMapping.add(4095, 225, "textWebPassword");
            inputTypeFlagMapping.add(4095, 36, DropBoxManager.EXTRA_TIME);
            Objects.requireNonNull(inputTypeFlagMapping);
            this.mInputTypeId = propertyMapper.mapIntFlag("inputType", 16843296, new IntFunction() {
                public final Object apply(int i) {
                    return IntFlagMapping.this.get(i);
                }
            });
            SparseArray<String> justificationModeEnumMapping = new SparseArray<>();
            justificationModeEnumMapping.put(0, "none");
            justificationModeEnumMapping.put(1, "inter_word");
            Objects.requireNonNull(justificationModeEnumMapping);
            this.mJustificationModeId = propertyMapper.mapIntEnum("justificationMode", 16844135, new IntFunction() {
                public final Object apply(int i) {
                    return (String) SparseArray.this.get(i);
                }
            });
            this.mLastBaselineToBottomHeightId = propertyMapper.mapInt("lastBaselineToBottomHeight", 16844158);
            this.mLetterSpacingId = propertyMapper.mapFloat("letterSpacing", 16843958);
            this.mLineHeightId = propertyMapper.mapInt("lineHeight", 16844159);
            this.mLineSpacingExtraId = propertyMapper.mapFloat("lineSpacingExtra", 16843287);
            this.mLineSpacingMultiplierId = propertyMapper.mapFloat("lineSpacingMultiplier", 16843288);
            this.mLinksClickableId = propertyMapper.mapBoolean("linksClickable", 16842929);
            this.mMarqueeRepeatLimitId = propertyMapper.mapInt("marqueeRepeatLimit", 16843293);
            this.mMaxEmsId = propertyMapper.mapInt("maxEms", 16843095);
            this.mMaxHeightId = propertyMapper.mapInt("maxHeight", 16843040);
            this.mMaxLinesId = propertyMapper.mapInt("maxLines", 16843091);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mMinEmsId = propertyMapper.mapInt("minEms", 16843098);
            this.mMinLinesId = propertyMapper.mapInt("minLines", 16843094);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mPrivateImeOptionsId = propertyMapper.mapObject("privateImeOptions", 16843299);
            this.mScrollHorizontallyId = propertyMapper.mapBoolean("scrollHorizontally", 16843099);
            this.mShadowColorId = propertyMapper.mapColor("shadowColor", 16843105);
            this.mShadowDxId = propertyMapper.mapFloat("shadowDx", 16843106);
            this.mShadowDyId = propertyMapper.mapFloat("shadowDy", 16843107);
            this.mShadowRadiusId = propertyMapper.mapFloat("shadowRadius", 16843108);
            this.mSingleLineId = propertyMapper.mapBoolean("singleLine", 16843101);
            this.mTextId = propertyMapper.mapObject("text", 16843087);
            this.mTextAllCapsId = propertyMapper.mapBoolean("textAllCaps", 16843660);
            this.mTextColorId = propertyMapper.mapObject("textColor", 16842904);
            this.mTextColorHighlightId = propertyMapper.mapColor("textColorHighlight", 16842905);
            this.mTextColorHintId = propertyMapper.mapObject("textColorHint", 16842906);
            this.mTextColorLinkId = propertyMapper.mapObject("textColorLink", 16842907);
            this.mTextIsSelectableId = propertyMapper.mapBoolean("textIsSelectable", 16843542);
            this.mTextScaleXId = propertyMapper.mapFloat("textScaleX", 16843089);
            this.mTextSizeId = propertyMapper.mapFloat("textSize", 16842901);
            this.mTypefaceId = propertyMapper.mapObject("typeface", 16842902);
            this.mPropertiesMapped = true;
        }

        public void readProperties(TextView node, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readIntFlag(this.mAutoLinkId, node.getAutoLinkMask());
                propertyReader.readInt(this.mAutoSizeMaxTextSizeId, node.getAutoSizeMaxTextSize());
                propertyReader.readInt(this.mAutoSizeMinTextSizeId, node.getAutoSizeMinTextSize());
                propertyReader.readInt(this.mAutoSizeStepGranularityId, node.getAutoSizeStepGranularity());
                propertyReader.readIntEnum(this.mAutoSizeTextTypeId, node.getAutoSizeTextType());
                propertyReader.readIntEnum(this.mBreakStrategyId, node.getBreakStrategy());
                propertyReader.readBoolean(this.mCursorVisibleId, node.isCursorVisible());
                propertyReader.readObject(this.mDrawableBlendModeId, node.getCompoundDrawableTintBlendMode());
                propertyReader.readInt(this.mDrawablePaddingId, node.getCompoundDrawablePadding());
                propertyReader.readObject(this.mDrawableTintId, node.getCompoundDrawableTintList());
                propertyReader.readObject(this.mDrawableTintModeId, node.getCompoundDrawableTintMode());
                propertyReader.readBoolean(this.mElegantTextHeightId, node.isElegantTextHeight());
                propertyReader.readObject(this.mEllipsizeId, node.getEllipsize());
                propertyReader.readBoolean(this.mFallbackLineSpacingId, node.isFallbackLineSpacing());
                propertyReader.readInt(this.mFirstBaselineToTopHeightId, node.getFirstBaselineToTopHeight());
                propertyReader.readObject(this.mFontFeatureSettingsId, node.getFontFeatureSettings());
                propertyReader.readBoolean(this.mFreezesTextId, node.getFreezesText());
                propertyReader.readGravity(this.mGravityId, node.getGravity());
                propertyReader.readObject(this.mHintId, node.getHint());
                propertyReader.readIntEnum(this.mHyphenationFrequencyId, node.getHyphenationFrequency());
                propertyReader.readInt(this.mImeActionIdId, node.getImeActionId());
                propertyReader.readObject(this.mImeActionLabelId, node.getImeActionLabel());
                propertyReader.readIntFlag(this.mImeOptionsId, node.getImeOptions());
                propertyReader.readBoolean(this.mIncludeFontPaddingId, node.getIncludeFontPadding());
                propertyReader.readIntFlag(this.mInputTypeId, node.getInputType());
                propertyReader.readIntEnum(this.mJustificationModeId, node.getJustificationMode());
                propertyReader.readInt(this.mLastBaselineToBottomHeightId, node.getLastBaselineToBottomHeight());
                propertyReader.readFloat(this.mLetterSpacingId, node.getLetterSpacing());
                propertyReader.readInt(this.mLineHeightId, node.getLineHeight());
                propertyReader.readFloat(this.mLineSpacingExtraId, node.getLineSpacingExtra());
                propertyReader.readFloat(this.mLineSpacingMultiplierId, node.getLineSpacingMultiplier());
                propertyReader.readBoolean(this.mLinksClickableId, node.getLinksClickable());
                propertyReader.readInt(this.mMarqueeRepeatLimitId, node.getMarqueeRepeatLimit());
                propertyReader.readInt(this.mMaxEmsId, node.getMaxEms());
                propertyReader.readInt(this.mMaxHeightId, node.getMaxHeight());
                propertyReader.readInt(this.mMaxLinesId, node.getMaxLines());
                propertyReader.readInt(this.mMaxWidthId, node.getMaxWidth());
                propertyReader.readInt(this.mMinEmsId, node.getMinEms());
                propertyReader.readInt(this.mMinLinesId, node.getMinLines());
                propertyReader.readInt(this.mMinWidthId, node.getMinWidth());
                propertyReader.readObject(this.mPrivateImeOptionsId, node.getPrivateImeOptions());
                propertyReader.readBoolean(this.mScrollHorizontallyId, node.isHorizontallyScrollable());
                propertyReader.readColor(this.mShadowColorId, node.getShadowColor());
                propertyReader.readFloat(this.mShadowDxId, node.getShadowDx());
                propertyReader.readFloat(this.mShadowDyId, node.getShadowDy());
                propertyReader.readFloat(this.mShadowRadiusId, node.getShadowRadius());
                propertyReader.readBoolean(this.mSingleLineId, node.isSingleLine());
                propertyReader.readObject(this.mTextId, node.getText());
                propertyReader.readBoolean(this.mTextAllCapsId, node.isAllCaps());
                propertyReader.readObject(this.mTextColorId, node.getTextColors());
                propertyReader.readColor(this.mTextColorHighlightId, node.getHighlightColor());
                propertyReader.readObject(this.mTextColorHintId, node.getHintTextColors());
                propertyReader.readObject(this.mTextColorLinkId, node.getLinkTextColors());
                propertyReader.readBoolean(this.mTextIsSelectableId, node.isTextSelectable());
                propertyReader.readFloat(this.mTextScaleXId, node.getTextScaleX());
                propertyReader.readFloat(this.mTextSizeId, node.getTextSize());
                propertyReader.readObject(this.mTypefaceId, node.getTypeface());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    static {
        sAppearanceValues.put(6, 4);
        sAppearanceValues.put(5, 3);
        sAppearanceValues.put(7, 5);
        sAppearanceValues.put(8, 6);
        sAppearanceValues.put(2, 0);
        sAppearanceValues.put(96, 19);
        sAppearanceValues.put(3, 1);
        sAppearanceValues.put(75, 12);
        sAppearanceValues.put(4, 2);
        sAppearanceValues.put(95, 18);
        sAppearanceValues.put(72, 11);
        sAppearanceValues.put(36, 7);
        sAppearanceValues.put(37, 8);
        sAppearanceValues.put(38, 9);
        sAppearanceValues.put(39, 10);
        sAppearanceValues.put(76, 13);
        sAppearanceValues.put(91, 17);
        sAppearanceValues.put(77, 14);
        sAppearanceValues.put(78, 15);
        sAppearanceValues.put(90, 16);
    }

    static class Drawables {
        static final int BOTTOM = 3;
        static final int DRAWABLE_LEFT = 1;
        static final int DRAWABLE_NONE = -1;
        static final int DRAWABLE_RIGHT = 0;
        static final int LEFT = 0;
        static final int RIGHT = 2;
        static final int TOP = 1;
        BlendMode mBlendMode;
        final Rect mCompoundRect = new Rect();
        Drawable mDrawableEnd;
        Drawable mDrawableError;
        int mDrawableHeightEnd;
        int mDrawableHeightError;
        int mDrawableHeightLeft;
        int mDrawableHeightRight;
        int mDrawableHeightStart;
        int mDrawableHeightTemp;
        Drawable mDrawableLeftInitial;
        int mDrawablePadding;
        Drawable mDrawableRightInitial;
        int mDrawableSaved = -1;
        int mDrawableSizeBottom;
        int mDrawableSizeEnd;
        int mDrawableSizeError;
        int mDrawableSizeLeft;
        int mDrawableSizeRight;
        int mDrawableSizeStart;
        int mDrawableSizeTemp;
        int mDrawableSizeTop;
        Drawable mDrawableStart;
        Drawable mDrawableTemp;
        int mDrawableWidthBottom;
        int mDrawableWidthTop;
        boolean mHasTint;
        boolean mHasTintMode;
        boolean mIsRtlCompatibilityMode;
        boolean mOverride;
        final Drawable[] mShowing = new Drawable[4];
        ColorStateList mTintList;

        public Drawables(Context context) {
            this.mIsRtlCompatibilityMode = context.getApplicationInfo().targetSdkVersion < 17 || !context.getApplicationInfo().hasRtlSupport();
            this.mOverride = false;
        }

        public boolean hasMetadata() {
            return this.mDrawablePadding != 0 || this.mHasTintMode || this.mHasTint;
        }

        public boolean resolveWithLayoutDirection(int layoutDirection) {
            Drawable previousLeft = this.mShowing[0];
            Drawable previousRight = this.mShowing[2];
            this.mShowing[0] = this.mDrawableLeftInitial;
            this.mShowing[2] = this.mDrawableRightInitial;
            if (this.mIsRtlCompatibilityMode) {
                if (this.mDrawableStart != null && this.mShowing[0] == null) {
                    this.mShowing[0] = this.mDrawableStart;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                }
                if (this.mDrawableEnd != null && this.mShowing[2] == null) {
                    this.mShowing[2] = this.mDrawableEnd;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else if (layoutDirection != 1) {
                if (this.mOverride) {
                    this.mShowing[0] = this.mDrawableStart;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                    this.mShowing[2] = this.mDrawableEnd;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else if (this.mOverride) {
                this.mShowing[2] = this.mDrawableStart;
                this.mDrawableSizeRight = this.mDrawableSizeStart;
                this.mDrawableHeightRight = this.mDrawableHeightStart;
                this.mShowing[0] = this.mDrawableEnd;
                this.mDrawableSizeLeft = this.mDrawableSizeEnd;
                this.mDrawableHeightLeft = this.mDrawableHeightEnd;
            }
            applyErrorDrawableIfNeeded(layoutDirection);
            if (this.mShowing[0] == previousLeft && this.mShowing[2] == previousRight) {
                return false;
            }
            return true;
        }

        public void setErrorDrawable(Drawable dr, TextView tv) {
            if (!(this.mDrawableError == dr || this.mDrawableError == null)) {
                this.mDrawableError.setCallback((Drawable.Callback) null);
            }
            this.mDrawableError = dr;
            if (this.mDrawableError != null) {
                Rect compoundRect = this.mCompoundRect;
                this.mDrawableError.setState(tv.getDrawableState());
                this.mDrawableError.copyBounds(compoundRect);
                this.mDrawableError.setCallback(tv);
                this.mDrawableSizeError = compoundRect.width();
                this.mDrawableHeightError = compoundRect.height();
                return;
            }
            this.mDrawableHeightError = 0;
            this.mDrawableSizeError = 0;
        }

        private void applyErrorDrawableIfNeeded(int layoutDirection) {
            switch (this.mDrawableSaved) {
                case 0:
                    this.mShowing[2] = this.mDrawableTemp;
                    this.mDrawableSizeRight = this.mDrawableSizeTemp;
                    this.mDrawableHeightRight = this.mDrawableHeightTemp;
                    break;
                case 1:
                    this.mShowing[0] = this.mDrawableTemp;
                    this.mDrawableSizeLeft = this.mDrawableSizeTemp;
                    this.mDrawableHeightLeft = this.mDrawableHeightTemp;
                    break;
            }
            if (this.mDrawableError == null) {
                return;
            }
            if (layoutDirection != 1) {
                this.mDrawableSaved = 0;
                this.mDrawableTemp = this.mShowing[2];
                this.mDrawableSizeTemp = this.mDrawableSizeRight;
                this.mDrawableHeightTemp = this.mDrawableHeightRight;
                this.mShowing[2] = this.mDrawableError;
                this.mDrawableSizeRight = this.mDrawableSizeError;
                this.mDrawableHeightRight = this.mDrawableHeightError;
                return;
            }
            this.mDrawableSaved = 1;
            this.mDrawableTemp = this.mShowing[0];
            this.mDrawableSizeTemp = this.mDrawableSizeLeft;
            this.mDrawableHeightTemp = this.mDrawableHeightLeft;
            this.mShowing[0] = this.mDrawableError;
            this.mDrawableSizeLeft = this.mDrawableSizeError;
            this.mDrawableHeightLeft = this.mDrawableHeightError;
        }
    }

    public static void preloadFontCache() {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setTypeface(Typeface.DEFAULT);
        p.measureText(DateFormat.HOUR24);
    }

    public TextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x096c, code lost:
        r6 = r77;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x01ef, code lost:
        r77 = r6;
        r76 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x01f3, code lost:
        r78 = r14;
        r9 = r53;
        r80 = r54;
        r10 = r55;
        r14 = r57;
        r6 = r58;
        r12 = r59;
        r11 = r60;
        r13 = r61;
        r79 = r66;
        r81 = r70;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x0c9f, code lost:
        if ((r8.mEditor.mInputType & 4095) == 129) goto L_0x0caa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0287, code lost:
        r76 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x02bf, code lost:
        r77 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x04ff, code lost:
        r78 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0536, code lost:
        r78 = r14;
     */
    /* JADX WARNING: Removed duplicated region for block: B:233:0x0b9e  */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x0bae  */
    /* JADX WARNING: Removed duplicated region for block: B:236:0x0bba  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x0bcb  */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x0bd1 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:246:0x0be1  */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x0bed  */
    /* JADX WARNING: Removed duplicated region for block: B:249:0x0bf3  */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x0bff  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x0c04  */
    /* JADX WARNING: Removed duplicated region for block: B:253:0x0c12  */
    /* JADX WARNING: Removed duplicated region for block: B:258:0x0c43  */
    /* JADX WARNING: Removed duplicated region for block: B:261:0x0c4a  */
    /* JADX WARNING: Removed duplicated region for block: B:263:0x0c50  */
    /* JADX WARNING: Removed duplicated region for block: B:268:0x0c6f  */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x0c75  */
    /* JADX WARNING: Removed duplicated region for block: B:270:0x0c7b  */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x0c83 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:278:0x0c8f  */
    /* JADX WARNING: Removed duplicated region for block: B:284:0x0ca6  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x0cad  */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x0cb5  */
    /* JADX WARNING: Removed duplicated region for block: B:291:0x0cbe  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x0cc9  */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x0cdd  */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x0cef  */
    /* JADX WARNING: Removed duplicated region for block: B:301:0x0cf7  */
    /* JADX WARNING: Removed duplicated region for block: B:303:0x0cfd  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0d03  */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x0d09  */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x0d2d  */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x0d2f  */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x0d39  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x0d3c  */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x0d47  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x0d4a  */
    /* JADX WARNING: Removed duplicated region for block: B:327:0x0d68  */
    /* JADX WARNING: Removed duplicated region for block: B:390:0x0db9 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextView(android.content.Context r119, android.util.AttributeSet r120, int r121, int r122) {
        /*
            r118 = this;
            r8 = r118
            r9 = r119
            r10 = r120
            r11 = r121
            r12 = r122
            r118.<init>(r119, r120, r121, r122)
            android.text.Editable$Factory r0 = android.text.Editable.Factory.getInstance()
            r8.mEditableFactory = r0
            android.text.Spannable$Factory r0 = android.text.Spannable.Factory.getInstance()
            r8.mSpannableFactory = r0
            r13 = 3
            r8.mMarqueeRepeatLimit = r13
            r14 = -1
            r8.mLastLayoutDirection = r14
            r15 = 0
            r8.mMarqueeFadeMode = r15
            android.widget.TextView$BufferType r0 = android.widget.TextView.BufferType.NORMAL
            r8.mBufferType = r0
            r8.mLocalesChanged = r15
            r8.mListenerChanged = r15
            r0 = 8388659(0x800033, float:1.1755015E-38)
            r8.mGravity = r0
            r7 = 1
            r8.mLinksClickable = r7
            r6 = 1065353216(0x3f800000, float:1.0)
            r8.mSpacingMult = r6
            r0 = 0
            r8.mSpacingAdd = r0
            r0 = 2147483647(0x7fffffff, float:NaN)
            r8.mMaximum = r0
            r8.mMaxMode = r7
            r8.mMinimum = r15
            r8.mMinMode = r7
            int r1 = r8.mMaximum
            r8.mOldMaximum = r1
            int r1 = r8.mMaxMode
            r8.mOldMaxMode = r1
            r8.mMaxWidth = r0
            r5 = 2
            r8.mMaxWidthMode = r5
            r8.mMinWidth = r15
            r8.mMinWidthMode = r5
            r8.mDesiredHeightAtMeasure = r14
            r8.mIncludePad = r7
            r8.mDeferScroll = r14
            android.text.InputFilter[] r0 = NO_FILTERS
            r8.mFilters = r0
            r0 = 1714664933(0x6633b5e5, float:2.1216474E23)
            r8.mHighlightColor = r0
            r8.mHighlightPathBogus = r7
            r8.mDeviceProvisionedState = r15
            r8.mAutoSizeTextType = r15
            r8.mNeedsAutoSizeText = r15
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            r8.mAutoSizeStepGranularityInPx = r4
            r8.mAutoSizeMinTextSizeInPx = r4
            r8.mAutoSizeMaxTextSizeInPx = r4
            int[] r0 = libcore.util.EmptyArray.INT
            r8.mAutoSizeTextSizesInPx = r0
            r8.mHasPresetAutoSizeValues = r15
            r8.mTextSetFromXmlOrResourceId = r15
            r8.mTextId = r15
            int r0 = r118.getImportantForAutofill()
            if (r0 != 0) goto L_0x0089
            r8.setImportantForAutofill(r7)
        L_0x0089:
            java.lang.String r0 = ""
            r8.setTextInternal(r0)
            android.content.res.Resources r16 = r118.getResources()
            android.content.res.CompatibilityInfo r3 = r16.getCompatibilityInfo()
            android.text.TextPaint r0 = new android.text.TextPaint
            r0.<init>((int) r7)
            r8.mTextPaint = r0
            android.text.TextPaint r0 = r8.mTextPaint
            android.util.DisplayMetrics r1 = r16.getDisplayMetrics()
            float r1 = r1.density
            r0.density = r1
            android.text.TextPaint r0 = r8.mTextPaint
            float r1 = r3.applicationScale
            r0.setCompatibilityScaling(r1)
            android.graphics.Paint r0 = new android.graphics.Paint
            r0.<init>((int) r7)
            r8.mHighlightPaint = r0
            android.graphics.Paint r0 = r8.mHighlightPaint
            float r1 = r3.applicationScale
            r0.setCompatibilityScaling(r1)
            android.text.method.MovementMethod r0 = r118.getDefaultMovementMethod()
            r8.mMovement = r0
            r2 = 0
            r8.mTransformation = r2
            android.widget.TextView$TextAppearanceAttributes r0 = new android.widget.TextView$TextAppearanceAttributes
            r0.<init>()
            r1 = r0
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            android.content.res.ColorStateList r0 = android.content.res.ColorStateList.valueOf(r0)
            r1.mTextColor = r0
            r0 = 15
            r1.mTextSize = r0
            r8.mBreakStrategy = r15
            r8.mHyphenationFrequency = r15
            r8.mJustificationMode = r15
            android.content.res.Resources$Theme r13 = r119.getTheme()
            int[] r0 = com.android.internal.R.styleable.TextViewAppearance
            android.content.res.TypedArray r0 = r13.obtainStyledAttributes(r10, r0, r11, r12)
            int[] r18 = com.android.internal.R.styleable.TextViewAppearance
            r19 = r1
            r1 = r118
            r2 = r119
            r21 = r3
            r3 = r18
            r4 = r120
            r5 = r0
            r6 = r121
            r7 = r122
            r1.saveAttributeDataForStyleable(r2, r3, r4, r5, r6, r7)
            r1 = 0
            int r7 = r0.getResourceId(r15, r14)
            r0.recycle()
            if (r7 == r14) goto L_0x011f
            int[] r2 = com.android.internal.R.styleable.TextAppearance
            android.content.res.TypedArray r18 = r13.obtainStyledAttributes(r7, r2)
            int[] r3 = com.android.internal.R.styleable.TextAppearance
            r4 = 0
            r6 = 0
            r1 = r118
            r2 = r119
            r5 = r18
            r22 = r7
            r1.saveAttributeDataForStyleable(r2, r3, r4, r5, r6, r7)
            r7 = r18
            goto L_0x0122
        L_0x011f:
            r22 = r7
            r7 = r1
        L_0x0122:
            if (r7 == 0) goto L_0x012f
            r6 = r19
            r8.readTextAppearance(r9, r7, r6, r15)
            r6.mFontFamilyExplicit = r15
            r7.recycle()
            goto L_0x0131
        L_0x012f:
            r6 = r19
        L_0x0131:
            boolean r18 = r118.getDefaultEditable()
            r19 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = -1
            r28 = 0
            r29 = 0
            r30 = 0
            r31 = 0
            r32 = 0
            r33 = 0
            r34 = 0
            r35 = 0
            r36 = 0
            r37 = 0
            r38 = 0
            r39 = -1
            r40 = 0
            r41 = -1
            java.lang.String r42 = ""
            r43 = 0
            r44 = 0
            r45 = -1082130432(0xffffffffbf800000, float:-1.0)
            r46 = -1082130432(0xffffffffbf800000, float:-1.0)
            r47 = -1082130432(0xffffffffbf800000, float:-1.0)
            r48 = 0
            int[] r1 = com.android.internal.R.styleable.TextView
            android.content.res.TypedArray r5 = r13.obtainStyledAttributes(r10, r1, r11, r12)
            int[] r3 = com.android.internal.R.styleable.TextView
            r1 = r118
            r2 = r119
            r4 = r120
            r49 = r5
            r14 = r6
            r6 = r121
            r50 = r7
            r7 = r122
            r1.saveAttributeDataForStyleable(r2, r3, r4, r5, r6, r7)
            r0 = -1
            r1 = -1
            r2 = -1
            r3 = r49
            r4 = 1
            r8.readTextAppearance(r9, r3, r14, r4)
            int r5 = r3.getIndexCount()
            r6 = 0
            r71 = r0
            r72 = r1
            r73 = r2
            r0 = r15
            r61 = r18
            r57 = r23
            r58 = r25
            r60 = r26
            r59 = r27
            r2 = r28
            r55 = r29
            r64 = r30
            r62 = r31
            r65 = r32
            r63 = r33
            r67 = r34
            r68 = r35
            r69 = r36
            r70 = r37
            r66 = r38
            r1 = r39
            r54 = r40
            r56 = r41
            r51 = r42
            r52 = r43
            r53 = r44
            r7 = r48
        L_0x01c8:
            r74 = r0
            r4 = r74
            if (r4 >= r5) goto L_0x09e7
            int r15 = r3.getIndex(r4)
            if (r15 == 0) goto L_0x0995
            r0 = 67
            if (r15 == r0) goto L_0x0970
            switch(r15) {
                case 9: goto L_0x094a;
                case 10: goto L_0x0924;
                case 11: goto L_0x08ff;
                case 12: goto L_0x08da;
                case 13: goto L_0x08b4;
                case 14: goto L_0x088e;
                case 15: goto L_0x0868;
                case 16: goto L_0x0842;
                case 17: goto L_0x081e;
                case 18: goto L_0x07eb;
                case 19: goto L_0x07c4;
                case 20: goto L_0x079d;
                case 21: goto L_0x0774;
                case 22: goto L_0x074e;
                case 23: goto L_0x0728;
                case 24: goto L_0x0702;
                case 25: goto L_0x06dc;
                case 26: goto L_0x06b6;
                case 27: goto L_0x0690;
                case 28: goto L_0x066a;
                case 29: goto L_0x0644;
                case 30: goto L_0x0619;
                case 31: goto L_0x05ee;
                case 32: goto L_0x05cb;
                case 33: goto L_0x05aa;
                case 34: goto L_0x057f;
                case 35: goto L_0x0559;
                default: goto L_0x01db;
            }
        L_0x01db:
            switch(r15) {
                case 40: goto L_0x053a;
                case 41: goto L_0x051d;
                case 42: goto L_0x0503;
                case 43: goto L_0x04ea;
                case 44: goto L_0x04d4;
                case 45: goto L_0x04c0;
                case 46: goto L_0x04ae;
                case 47: goto L_0x0484;
                case 48: goto L_0x0473;
                case 49: goto L_0x0462;
                case 50: goto L_0x0451;
                case 51: goto L_0x0440;
                case 52: goto L_0x042f;
                case 53: goto L_0x0404;
                case 54: goto L_0x03f5;
                case 55: goto L_0x03e5;
                case 56: goto L_0x03d8;
                case 57: goto L_0x03ca;
                case 58: goto L_0x03a9;
                case 59: goto L_0x0389;
                case 60: goto L_0x036f;
                case 61: goto L_0x034f;
                case 62: goto L_0x0340;
                case 63: goto L_0x0331;
                case 64: goto L_0x0322;
                default: goto L_0x01de;
            }
        L_0x01de:
            switch(r15) {
                case 70: goto L_0x0313;
                case 71: goto L_0x0304;
                default: goto L_0x01e1;
            }
        L_0x01e1:
            switch(r15) {
                case 73: goto L_0x02f5;
                case 74: goto L_0x02e6;
                default: goto L_0x01e4;
            }
        L_0x01e4:
            switch(r15) {
                case 79: goto L_0x02d7;
                case 80: goto L_0x02c3;
                case 81: goto L_0x02b3;
                case 82: goto L_0x02a7;
                case 83: goto L_0x0296;
                case 84: goto L_0x028b;
                case 85: goto L_0x027c;
                case 86: goto L_0x0261;
                case 87: goto L_0x0255;
                case 88: goto L_0x0249;
                case 89: goto L_0x023f;
                default: goto L_0x01e7;
            }
        L_0x01e7:
            switch(r15) {
                case 92: goto L_0x0234;
                case 93: goto L_0x0229;
                case 94: goto L_0x021f;
                default: goto L_0x01ea;
            }
        L_0x01ea:
            switch(r15) {
                case 97: goto L_0x0215;
                case 98: goto L_0x020b;
                default: goto L_0x01ed;
            }
        L_0x01ed:
            r75 = r5
        L_0x01ef:
            r77 = r6
            r76 = r13
        L_0x01f3:
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            goto L_0x09bd
        L_0x020b:
            r75 = r5
            r5 = 0
            int r0 = r3.getResourceId(r15, r5)
            r8.mTextEditSuggestionHighlightStyle = r0
            goto L_0x01ef
        L_0x0215:
            r75 = r5
            r5 = 0
            int r0 = r3.getResourceId(r15, r5)
            r8.mTextEditSuggestionContainerLayout = r0
            goto L_0x01ef
        L_0x021f:
            r75 = r5
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r73 = r0
            goto L_0x0287
        L_0x0229:
            r75 = r5
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r72 = r0
            goto L_0x0287
        L_0x0234:
            r75 = r5
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r71 = r0
            goto L_0x0287
        L_0x023f:
            r75 = r5
            r5 = 0
            int r0 = r3.getInt(r15, r5)
            r8.mJustificationMode = r0
            goto L_0x01ef
        L_0x0249:
            r75 = r5
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r0 = r3.getDimension(r15, r5)
            r46 = r0
            goto L_0x0287
        L_0x0255:
            r75 = r5
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r0 = r3.getDimension(r15, r5)
            r45 = r0
            goto L_0x0287
        L_0x0261:
            r75 = r5
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            r5 = 0
            int r0 = r3.getResourceId(r15, r5)
            if (r0 <= 0) goto L_0x01ef
            android.content.res.Resources r5 = r3.getResources()
            android.content.res.TypedArray r5 = r5.obtainTypedArray(r0)
            r8.setupAutoSizeUniformPresetSizes(r5)
            r5.recycle()
            goto L_0x01ef
        L_0x027c:
            r75 = r5
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r0 = r3.getDimension(r15, r5)
            r47 = r0
        L_0x0287:
            r76 = r13
            goto L_0x04ff
        L_0x028b:
            r75 = r5
            r5 = 0
            int r0 = r3.getInt(r15, r5)
            r8.mAutoSizeTextType = r0
            goto L_0x01ef
        L_0x0296:
            r75 = r5
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            r76 = r13
            r5 = 1
            boolean r13 = r3.getBoolean(r15, r5)
            r0.mAllowUndo = r13
            goto L_0x02bf
        L_0x02a7:
            r75 = r5
            r76 = r13
            r5 = 0
            int r0 = r3.getInt(r15, r5)
            r8.mHyphenationFrequency = r0
            goto L_0x02bf
        L_0x02b3:
            r75 = r5
            r76 = r13
            r5 = 0
            int r0 = r3.getInt(r15, r5)
            r8.mBreakStrategy = r0
        L_0x02bf:
            r77 = r6
            goto L_0x01f3
        L_0x02c3:
            r75 = r5
            r76 = r13
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r5 = r70
            android.graphics.BlendMode r0 = android.graphics.drawable.Drawable.parseBlendMode(r0, r5)
            r70 = r0
            goto L_0x04ff
        L_0x02d7:
            r75 = r5
            r76 = r13
            r5 = r70
            android.content.res.ColorStateList r0 = r3.getColorStateList(r15)
            r69 = r0
            goto L_0x04ff
        L_0x02e6:
            r75 = r5
            r76 = r13
            r5 = r70
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r15)
            r68 = r0
            goto L_0x04ff
        L_0x02f5:
            r75 = r5
            r76 = r13
            r5 = r70
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r15)
            r67 = r0
            goto L_0x04ff
        L_0x0304:
            r75 = r5
            r76 = r13
            r5 = r70
            r13 = 0
            int r0 = r3.getResourceId(r15, r13)
            r8.mTextEditSuggestionItemLayout = r0
            goto L_0x0415
        L_0x0313:
            r75 = r5
            r76 = r13
            r5 = r70
            r13 = 0
            int r0 = r3.getResourceId(r15, r13)
            r8.mCursorDrawableRes = r0
            goto L_0x0415
        L_0x0322:
            r75 = r5
            r76 = r13
            r5 = r70
            r13 = 0
            int r0 = r3.getResourceId(r15, r13)
            r8.mTextSelectHandleRes = r0
            goto L_0x0415
        L_0x0331:
            r75 = r5
            r76 = r13
            r5 = r70
            r13 = 0
            int r0 = r3.getResourceId(r15, r13)
            r8.mTextSelectHandleRightRes = r0
            goto L_0x0415
        L_0x0340:
            r75 = r5
            r76 = r13
            r5 = r70
            r13 = 0
            int r0 = r3.getResourceId(r15, r13)
            r8.mTextSelectHandleLeftRes = r0
            goto L_0x0415
        L_0x034f:
            r75 = r5
            r76 = r13
            r5 = r70
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            r0.createInputContentTypeIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            android.widget.Editor$InputContentType r0 = r0.mInputContentType
            android.widget.Editor r13 = r8.mEditor
            android.widget.Editor$InputContentType r13 = r13.mInputContentType
            int r13 = r13.imeActionId
            int r13 = r3.getInt(r15, r13)
            r0.imeActionId = r13
            goto L_0x0415
        L_0x036f:
            r75 = r5
            r76 = r13
            r5 = r70
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            r0.createInputContentTypeIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            android.widget.Editor$InputContentType r0 = r0.mInputContentType
            java.lang.CharSequence r13 = r3.getText(r15)
            r0.imeActionLabel = r13
            goto L_0x0415
        L_0x0389:
            r75 = r5
            r76 = r13
            r5 = r70
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            r0.createInputContentTypeIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            android.widget.Editor$InputContentType r0 = r0.mInputContentType
            android.widget.Editor r13 = r8.mEditor
            android.widget.Editor$InputContentType r13 = r13.mInputContentType
            int r13 = r13.imeOptions
            int r13 = r3.getInt(r15, r13)
            r0.imeOptions = r13
            goto L_0x0415
        L_0x03a9:
            r75 = r5
            r76 = r13
            r5 = r70
            r13 = 0
            int r0 = r3.getResourceId(r15, r13)     // Catch:{ XmlPullParserException -> 0x03c1, IOException -> 0x03b8 }
            r8.setInputExtras(r0)     // Catch:{ XmlPullParserException -> 0x03c1, IOException -> 0x03b8 }
            goto L_0x03c9
        L_0x03b8:
            r0 = move-exception
            java.lang.String r13 = "TextView"
            java.lang.String r10 = "Failure reading input extras"
            android.util.Log.w(r13, r10, r0)
            goto L_0x0415
        L_0x03c1:
            r0 = move-exception
            java.lang.String r10 = "TextView"
            java.lang.String r13 = "Failure reading input extras"
            android.util.Log.w(r10, r13, r0)
        L_0x03c9:
            goto L_0x0415
        L_0x03ca:
            r75 = r5
            r76 = r13
            r5 = r70
            java.lang.String r0 = r3.getString(r15)
            r8.setPrivateImeOptions(r0)
            goto L_0x0415
        L_0x03d8:
            r75 = r5
            r76 = r13
            r5 = r70
            r10 = 0
            int r7 = r3.getInt(r15, r10)
            goto L_0x04ff
        L_0x03e5:
            r75 = r5
            r76 = r13
            r5 = r70
            int r0 = r8.mMarqueeRepeatLimit
            int r0 = r3.getInt(r15, r0)
            r8.setMarqueeRepeatLimit(r0)
            goto L_0x0415
        L_0x03f5:
            r75 = r5
            r76 = r13
            r5 = r70
            float r0 = r8.mSpacingMult
            float r0 = r3.getFloat(r15, r0)
            r8.mSpacingMult = r0
            goto L_0x0415
        L_0x0404:
            r75 = r5
            r76 = r13
            r5 = r70
            float r0 = r8.mSpacingAdd
            int r0 = (int) r0
            int r0 = r3.getDimensionPixelSize(r15, r0)
            float r0 = (float) r0
            r8.mSpacingAdd = r0
        L_0x0415:
            r81 = r5
            r77 = r6
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            goto L_0x09bd
        L_0x042f:
            r75 = r5
            r76 = r13
            r5 = r70
            r10 = r66
            int r0 = r3.getDimensionPixelSize(r15, r10)
            r66 = r0
            goto L_0x04ff
        L_0x0440:
            r75 = r5
            r76 = r13
            r10 = r66
            r5 = r70
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r15)
            r65 = r0
            goto L_0x04ff
        L_0x0451:
            r75 = r5
            r76 = r13
            r10 = r66
            r5 = r70
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r15)
            r64 = r0
            goto L_0x04ff
        L_0x0462:
            r75 = r5
            r76 = r13
            r10 = r66
            r5 = r70
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r15)
            r63 = r0
            goto L_0x04ff
        L_0x0473:
            r75 = r5
            r76 = r13
            r10 = r66
            r5 = r70
            android.graphics.drawable.Drawable r0 = r3.getDrawable(r15)
            r62 = r0
            goto L_0x04ff
        L_0x0484:
            r75 = r5
            r76 = r13
            r10 = r66
            r5 = r70
            r13 = 0
            boolean r0 = r3.getBoolean(r15, r13)
            r8.mFreezesText = r0
            r81 = r5
            r77 = r6
            r79 = r10
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            goto L_0x09bd
        L_0x04ae:
            r75 = r5
            r76 = r13
            r10 = r66
            r5 = r70
            r13 = r61
            boolean r0 = r3.getBoolean(r15, r13)
            r61 = r0
            goto L_0x04ff
        L_0x04c0:
            r75 = r5
            r76 = r13
            r13 = r61
            r10 = r66
            r5 = r70
            r11 = r60
            boolean r0 = r3.getBoolean(r15, r11)
            r60 = r0
            goto L_0x04ff
        L_0x04d4:
            r75 = r5
            r76 = r13
            r11 = r60
            r13 = r61
            r10 = r66
            r5 = r70
            r12 = r59
            int r0 = r3.getInt(r15, r12)
            r59 = r0
            goto L_0x04ff
        L_0x04ea:
            r75 = r5
            r76 = r13
            r12 = r59
            r11 = r60
            r13 = r61
            r10 = r66
            r5 = r70
            java.lang.CharSequence r0 = r3.getText(r15)
            r19 = r0
        L_0x04ff:
            r78 = r14
            goto L_0x09d3
        L_0x0503:
            r75 = r5
            r76 = r13
            r12 = r59
            r11 = r60
            r13 = r61
            r10 = r66
            r5 = r70
            r77 = r6
            r6 = r58
            boolean r0 = r3.getBoolean(r15, r6)
            r58 = r0
            goto L_0x0536
        L_0x051d:
            r75 = r5
            r77 = r6
            r76 = r13
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r10 = r66
            r5 = r70
            java.lang.CharSequence r0 = r3.getText(r15)
            r24 = r0
        L_0x0536:
            r78 = r14
            goto L_0x096c
        L_0x053a:
            r75 = r5
            r77 = r6
            r76 = r13
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r10 = r66
            r5 = r70
            r78 = r14
            r14 = r57
            int r0 = r3.getInt(r15, r14)
            r57 = r0
            goto L_0x096c
        L_0x0559:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r10 = r66
            r5 = r70
            r79 = r10
            r10 = -1
            int r0 = r3.getInt(r15, r10)
            r56 = r0
            r6 = r77
            r66 = r79
            goto L_0x09d3
        L_0x057f:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r5 = r70
            r10 = 1
            boolean r0 = r3.getBoolean(r15, r10)
            if (r0 != 0) goto L_0x05a0
            r10 = 0
            r8.setIncludeFontPadding(r10)
        L_0x05a0:
            r81 = r5
            r9 = r53
            r80 = r54
            r10 = r55
            goto L_0x09bd
        L_0x05aa:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r5 = r70
            r10 = r55
            boolean r0 = r3.getBoolean(r15, r10)
            r55 = r0
            goto L_0x096c
        L_0x05cb:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r5 = r70
            r9 = r54
            boolean r0 = r3.getBoolean(r15, r9)
            r54 = r0
            goto L_0x096c
        L_0x05ee:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r5 = r70
            r80 = r9
            r9 = r53
            boolean r0 = r3.getBoolean(r15, r9)
            r53 = r0
            r6 = r77
            r54 = r80
            goto L_0x09d3
        L_0x0619:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r5 = r70
            r81 = r5
            r5 = 0
            boolean r0 = r3.getBoolean(r15, r5)
            if (r0 == 0) goto L_0x09bd
            r5 = 1
            r8.setHorizontallyScrolling(r5)
            goto L_0x09bd
        L_0x0644:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setMinEms(r0)
            goto L_0x09bd
        L_0x066a:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r8.setWidth(r0)
            goto L_0x09bd
        L_0x0690:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setEms(r0)
            goto L_0x09bd
        L_0x06b6:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setMaxEms(r0)
            goto L_0x09bd
        L_0x06dc:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setMinLines(r0)
            goto L_0x09bd
        L_0x0702:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r8.setHeight(r0)
            goto L_0x09bd
        L_0x0728:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setLines(r0)
            goto L_0x09bd
        L_0x074e:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setMaxLines(r0)
            goto L_0x09bd
        L_0x0774:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 1
            boolean r0 = r3.getBoolean(r15, r5)
            if (r0 != 0) goto L_0x09bd
            r5 = 0
            r8.setCursorVisible(r5)
            goto L_0x09bd
        L_0x079d:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 1065353216(0x3f800000, float:1.0)
            float r0 = r3.getFloat(r15, r5)
            r8.setTextScaleX(r0)
            goto L_0x09bd
        L_0x07c4:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 1065353216(0x3f800000, float:1.0)
            java.lang.CharSequence r0 = r3.getText(r15)
            r52 = r0
            goto L_0x096c
        L_0x07eb:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 1065353216(0x3f800000, float:1.0)
            r0 = 1
            r82 = r0
            r5 = 0
            int r0 = r3.getResourceId(r15, r5)
            r8.mTextId = r0
            java.lang.CharSequence r0 = r3.getText(r15)
            r51 = r0
            r6 = r82
            goto L_0x09d3
        L_0x081e:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            int r0 = r3.getInt(r15, r2)
            r2 = r0
            goto L_0x096c
        L_0x0842:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r8.setMinHeight(r0)
            goto L_0x09bd
        L_0x0868:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r8.setMinWidth(r0)
            goto L_0x09bd
        L_0x088e:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r8.setMaxHeight(r0)
            goto L_0x09bd
        L_0x08b4:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getDimensionPixelSize(r15, r5)
            r8.setMaxWidth(r0)
            goto L_0x09bd
        L_0x08da:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 1
            boolean r0 = r3.getBoolean(r15, r5)
            r8.mLinksClickable = r0
            goto L_0x09bd
        L_0x08ff:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 0
            int r0 = r3.getInt(r15, r5)
            r8.mAutoLinkMask = r0
            goto L_0x09bd
        L_0x0924:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = -1
            int r0 = r3.getInt(r15, r5)
            r8.setGravity(r0)
            goto L_0x09bd
        L_0x094a:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            int r0 = r3.getInt(r15, r1)
            r1 = r0
        L_0x096c:
            r6 = r77
            goto L_0x09d3
        L_0x0970:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r5 = 0
            boolean r0 = r3.getBoolean(r15, r5)
            r8.setTextIsSelectable(r0)
            goto L_0x09bd
        L_0x0995:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            boolean r0 = r118.isEnabled()
            boolean r0 = r3.getBoolean(r15, r0)
            r8.setEnabled(r0)
        L_0x09bd:
            r58 = r6
            r53 = r9
            r55 = r10
            r60 = r11
            r59 = r12
            r61 = r13
            r57 = r14
            r6 = r77
            r66 = r79
            r54 = r80
            r70 = r81
        L_0x09d3:
            int r0 = r4 + 1
            r5 = r75
            r13 = r76
            r14 = r78
            r4 = 1
            r9 = r119
            r10 = r120
            r11 = r121
            r12 = r122
            r15 = 0
            goto L_0x01c8
        L_0x09e7:
            r75 = r5
            r77 = r6
            r76 = r13
            r78 = r14
            r9 = r53
            r80 = r54
            r10 = r55
            r14 = r57
            r6 = r58
            r12 = r59
            r11 = r60
            r13 = r61
            r79 = r66
            r81 = r70
            r3.recycle()
            android.widget.TextView$BufferType r4 = android.widget.TextView.BufferType.EDITABLE
            r5 = r7 & 4095(0xfff, float:5.738E-42)
            r15 = 129(0x81, float:1.81E-43)
            if (r5 != r15) goto L_0x0a10
            r0 = 1
            goto L_0x0a11
        L_0x0a10:
            r0 = 0
        L_0x0a11:
            r83 = r0
            r0 = 225(0xe1, float:3.15E-43)
            if (r5 != r0) goto L_0x0a19
            r0 = 1
            goto L_0x0a1a
        L_0x0a19:
            r0 = 0
        L_0x0a1a:
            r84 = r0
            r15 = 18
            if (r5 != r15) goto L_0x0a22
            r0 = 1
            goto L_0x0a23
        L_0x0a22:
            r0 = 0
        L_0x0a23:
            r85 = r0
            android.content.pm.ApplicationInfo r0 = r119.getApplicationInfo()
            int r15 = r0.targetSdkVersion
            r0 = 26
            if (r15 < r0) goto L_0x0a31
            r0 = 1
            goto L_0x0a32
        L_0x0a31:
            r0 = 0
        L_0x0a32:
            r8.mUseInternationalizedInput = r0
            r0 = 28
            if (r15 < r0) goto L_0x0a3a
            r0 = 1
            goto L_0x0a3b
        L_0x0a3a:
            r0 = 0
        L_0x0a3b:
            r8.mUseFallbackLineSpacing = r0
            if (r19 == 0) goto L_0x0aa4
            java.lang.String r0 = r19.toString()     // Catch:{ ClassNotFoundException -> 0x0a99 }
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0a99 }
            r18 = r0
            r118.createEditorIfNeeded()     // Catch:{ InstantiationException -> 0x0a8e, IllegalAccessException -> 0x0a83 }
            android.widget.Editor r0 = r8.mEditor     // Catch:{ InstantiationException -> 0x0a8e, IllegalAccessException -> 0x0a83 }
            java.lang.Object r20 = r18.newInstance()     // Catch:{ InstantiationException -> 0x0a8e, IllegalAccessException -> 0x0a83 }
            r86 = r3
            r3 = r20
            android.text.method.KeyListener r3 = (android.text.method.KeyListener) r3     // Catch:{ InstantiationException -> 0x0a7f, IllegalAccessException -> 0x0a7b }
            r0.mKeyListener = r3     // Catch:{ InstantiationException -> 0x0a7f, IllegalAccessException -> 0x0a7b }
            android.widget.Editor r0 = r8.mEditor     // Catch:{ IncompatibleClassChangeError -> 0x0a72 }
            if (r7 == 0) goto L_0x0a64
            r3 = r7
            goto L_0x0a6c
        L_0x0a64:
            android.widget.Editor r3 = r8.mEditor     // Catch:{ IncompatibleClassChangeError -> 0x0a72 }
            android.text.method.KeyListener r3 = r3.mKeyListener     // Catch:{ IncompatibleClassChangeError -> 0x0a72 }
            int r3 = r3.getInputType()     // Catch:{ IncompatibleClassChangeError -> 0x0a72 }
        L_0x0a6c:
            r0.mInputType = r3     // Catch:{ IncompatibleClassChangeError -> 0x0a72 }
            r87 = r4
            goto L_0x0a7a
        L_0x0a72:
            r0 = move-exception
            android.widget.Editor r3 = r8.mEditor
            r87 = r4
            r4 = 1
            r3.mInputType = r4
        L_0x0a7a:
            goto L_0x0ac2
        L_0x0a7b:
            r0 = move-exception
            r87 = r4
            goto L_0x0a88
        L_0x0a7f:
            r0 = move-exception
            r87 = r4
            goto L_0x0a93
        L_0x0a83:
            r0 = move-exception
            r86 = r3
            r87 = r4
        L_0x0a88:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            r3.<init>(r0)
            throw r3
        L_0x0a8e:
            r0 = move-exception
            r86 = r3
            r87 = r4
        L_0x0a93:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            r3.<init>(r0)
            throw r3
        L_0x0a99:
            r0 = move-exception
            r86 = r3
            r87 = r4
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            r3.<init>(r0)
            throw r3
        L_0x0aa4:
            r86 = r3
            r87 = r4
            if (r24 == 0) goto L_0x0ac6
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            java.lang.String r3 = r24.toString()
            android.text.method.DigitsKeyListener r3 = android.text.method.DigitsKeyListener.getInstance((java.lang.String) r3)
            r0.mKeyListener = r3
            android.widget.Editor r0 = r8.mEditor
            if (r7 == 0) goto L_0x0abf
            r3 = r7
            goto L_0x0ac0
        L_0x0abf:
            r3 = 1
        L_0x0ac0:
            r0.mInputType = r3
        L_0x0ac2:
            r88 = r5
            goto L_0x0b6c
        L_0x0ac6:
            if (r7 == 0) goto L_0x0ad6
            r3 = 1
            r8.setInputType(r7, r3)
            boolean r0 = isMultilineInputType(r7)
            r0 = r0 ^ r3
            r54 = r0
            r88 = r5
            goto L_0x0aed
        L_0x0ad6:
            if (r6 == 0) goto L_0x0af1
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            android.text.method.DialerKeyListener r3 = android.text.method.DialerKeyListener.getInstance()
            r0.mKeyListener = r3
            android.widget.Editor r0 = r8.mEditor
            r3 = 3
            r7 = r3
            r0.mInputType = r3
            r88 = r5
        L_0x0aeb:
            r0 = r80
        L_0x0aed:
            r4 = r87
            goto L_0x0b9a
        L_0x0af1:
            if (r14 == 0) goto L_0x0b1c
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            r3 = r14 & 2
            if (r3 == 0) goto L_0x0afe
            r3 = 1
            goto L_0x0aff
        L_0x0afe:
            r3 = 0
        L_0x0aff:
            r4 = r14 & 4
            if (r4 == 0) goto L_0x0b05
            r4 = 1
            goto L_0x0b06
        L_0x0b05:
            r4 = 0
        L_0x0b06:
            r88 = r5
            r5 = 0
            android.text.method.DigitsKeyListener r3 = android.text.method.DigitsKeyListener.getInstance(r5, r3, r4)
            r0.mKeyListener = r3
            android.widget.Editor r0 = r8.mEditor
            android.text.method.KeyListener r0 = r0.mKeyListener
            int r7 = r0.getInputType()
            android.widget.Editor r0 = r8.mEditor
            r0.mInputType = r7
            goto L_0x0aeb
        L_0x0b1c:
            r88 = r5
            r5 = 0
            if (r11 != 0) goto L_0x0b71
            r3 = -1
            if (r12 == r3) goto L_0x0b25
            goto L_0x0b71
        L_0x0b25:
            if (r13 == 0) goto L_0x0b38
            r118.createEditorIfNeeded()
            android.widget.Editor r0 = r8.mEditor
            android.text.method.TextKeyListener r3 = android.text.method.TextKeyListener.getInstance()
            r0.mKeyListener = r3
            android.widget.Editor r0 = r8.mEditor
            r3 = 1
            r0.mInputType = r3
            goto L_0x0b6c
        L_0x0b38:
            boolean r0 = r118.isTextSelectable()
            if (r0 == 0) goto L_0x0b57
            android.widget.Editor r0 = r8.mEditor
            if (r0 == 0) goto L_0x0b4b
            android.widget.Editor r0 = r8.mEditor
            r0.mKeyListener = r5
            android.widget.Editor r0 = r8.mEditor
            r3 = 0
            r0.mInputType = r3
        L_0x0b4b:
            android.widget.TextView$BufferType r4 = android.widget.TextView.BufferType.SPANNABLE
            android.text.method.MovementMethod r0 = android.text.method.ArrowKeyMovementMethod.getInstance()
            r8.setMovementMethod(r0)
        L_0x0b54:
            r0 = r80
            goto L_0x0b9a
        L_0x0b57:
            android.widget.Editor r0 = r8.mEditor
            if (r0 == 0) goto L_0x0b5f
            android.widget.Editor r0 = r8.mEditor
            r0.mKeyListener = r5
        L_0x0b5f:
            switch(r2) {
                case 0: goto L_0x0b69;
                case 1: goto L_0x0b66;
                case 2: goto L_0x0b63;
                default: goto L_0x0b62;
            }
        L_0x0b62:
            goto L_0x0b6c
        L_0x0b63:
            android.widget.TextView$BufferType r4 = android.widget.TextView.BufferType.EDITABLE
            goto L_0x0b54
        L_0x0b66:
            android.widget.TextView$BufferType r4 = android.widget.TextView.BufferType.SPANNABLE
            goto L_0x0b54
        L_0x0b69:
            android.widget.TextView$BufferType r4 = android.widget.TextView.BufferType.NORMAL
            goto L_0x0b54
        L_0x0b6c:
            r0 = r80
            r4 = r87
            goto L_0x0b9a
        L_0x0b71:
            r0 = 1
            switch(r12) {
                case 1: goto L_0x0b83;
                case 2: goto L_0x0b7e;
                case 3: goto L_0x0b79;
                default: goto L_0x0b75;
            }
        L_0x0b75:
            android.text.method.TextKeyListener$Capitalize r3 = android.text.method.TextKeyListener.Capitalize.NONE
        L_0x0b77:
            r7 = r0
            goto L_0x0b88
        L_0x0b79:
            android.text.method.TextKeyListener$Capitalize r3 = android.text.method.TextKeyListener.Capitalize.CHARACTERS
            r0 = r0 | 4096(0x1000, float:5.74E-42)
            goto L_0x0b77
        L_0x0b7e:
            android.text.method.TextKeyListener$Capitalize r3 = android.text.method.TextKeyListener.Capitalize.WORDS
            r0 = r0 | 8192(0x2000, float:1.14794E-41)
            goto L_0x0b77
        L_0x0b83:
            android.text.method.TextKeyListener$Capitalize r3 = android.text.method.TextKeyListener.Capitalize.SENTENCES
            r0 = r0 | 16384(0x4000, float:2.2959E-41)
            goto L_0x0b77
        L_0x0b88:
            r0 = r3
            r118.createEditorIfNeeded()
            android.widget.Editor r3 = r8.mEditor
            android.text.method.TextKeyListener r4 = android.text.method.TextKeyListener.getInstance(r11, r0)
            r3.mKeyListener = r4
            android.widget.Editor r3 = r8.mEditor
            r3.mInputType = r7
            goto L_0x0aeb
        L_0x0b9a:
            android.widget.Editor r3 = r8.mEditor
            if (r3 == 0) goto L_0x0bae
            android.widget.Editor r3 = r8.mEditor
            r89 = r2
            r90 = r6
            r5 = r83
            r2 = r84
            r6 = r85
            r3.adjustInputType(r9, r5, r2, r6)
            goto L_0x0bb8
        L_0x0bae:
            r89 = r2
            r90 = r6
            r5 = r83
            r2 = r84
            r6 = r85
        L_0x0bb8:
            if (r10 == 0) goto L_0x0bcb
            r118.createEditorIfNeeded()
            android.widget.Editor r3 = r8.mEditor
            r91 = r7
            r7 = 1
            r3.mSelectAllOnFocus = r7
            android.widget.TextView$BufferType r3 = android.widget.TextView.BufferType.NORMAL
            if (r4 != r3) goto L_0x0bcd
            android.widget.TextView$BufferType r4 = android.widget.TextView.BufferType.SPANNABLE
            goto L_0x0bcd
        L_0x0bcb:
            r91 = r7
        L_0x0bcd:
            r3 = r69
            if (r3 != 0) goto L_0x0bdd
            if (r81 == 0) goto L_0x0bd4
            goto L_0x0bdd
        L_0x0bd4:
            r93 = r3
            r92 = r10
            r94 = r81
            r10 = r119
            goto L_0x0c14
        L_0x0bdd:
            android.widget.TextView$Drawables r7 = r8.mDrawables
            if (r7 != 0) goto L_0x0bed
            android.widget.TextView$Drawables r7 = new android.widget.TextView$Drawables
            r92 = r10
            r10 = r119
            r7.<init>(r10)
            r8.mDrawables = r7
            goto L_0x0bf1
        L_0x0bed:
            r92 = r10
            r10 = r119
        L_0x0bf1:
            if (r3 == 0) goto L_0x0bff
            android.widget.TextView$Drawables r7 = r8.mDrawables
            r7.mTintList = r3
            android.widget.TextView$Drawables r7 = r8.mDrawables
            r93 = r3
            r3 = 1
            r7.mHasTint = r3
            goto L_0x0c02
        L_0x0bff:
            r93 = r3
            r3 = 1
        L_0x0c02:
            if (r81 == 0) goto L_0x0c12
            android.widget.TextView$Drawables r7 = r8.mDrawables
            r3 = r81
            r7.mBlendMode = r3
            android.widget.TextView$Drawables r7 = r8.mDrawables
            r94 = r3
            r3 = 1
            r7.mHasTintMode = r3
            goto L_0x0c14
        L_0x0c12:
            r94 = r81
        L_0x0c14:
            r95 = r11
            r96 = r12
            r3 = r62
            r7 = r63
            r11 = r64
            r12 = r65
            r8.setCompoundDrawablesWithIntrinsicBounds((android.graphics.drawable.Drawable) r11, (android.graphics.drawable.Drawable) r3, (android.graphics.drawable.Drawable) r12, (android.graphics.drawable.Drawable) r7)
            r97 = r3
            r98 = r7
            r3 = r67
            r7 = r68
            r8.setRelativeDrawablesIfNeeded(r3, r7)
            r99 = r3
            r3 = r79
            r8.setCompoundDrawablePadding(r3)
            r8.setInputTypeSingleLine(r0)
            r8.applySingleLine(r0, r0, r0)
            if (r0 == 0) goto L_0x0c4a
            android.text.method.KeyListener r18 = r118.getKeyListener()
            if (r18 != 0) goto L_0x0c4a
            r100 = r3
            r3 = -1
            if (r1 != r3) goto L_0x0c4c
            r1 = 3
            goto L_0x0c4c
        L_0x0c4a:
            r100 = r3
        L_0x0c4c:
            switch(r1) {
                case 1: goto L_0x0c7b;
                case 2: goto L_0x0c75;
                case 3: goto L_0x0c6f;
                case 4: goto L_0x0c50;
                default: goto L_0x0c4f;
            }
        L_0x0c4f:
            goto L_0x0c81
        L_0x0c50:
            android.view.ViewConfiguration r3 = android.view.ViewConfiguration.get(r119)
            boolean r3 = r3.isFadingMarqueeEnabled()
            if (r3 == 0) goto L_0x0c62
            r3 = 1
            r8.setHorizontalFadingEdgeEnabled(r3)
            r3 = 0
            r8.mMarqueeFadeMode = r3
            goto L_0x0c69
        L_0x0c62:
            r3 = 0
            r8.setHorizontalFadingEdgeEnabled(r3)
            r3 = 1
            r8.mMarqueeFadeMode = r3
        L_0x0c69:
            android.text.TextUtils$TruncateAt r3 = android.text.TextUtils.TruncateAt.MARQUEE
            r8.setEllipsize(r3)
            goto L_0x0c81
        L_0x0c6f:
            android.text.TextUtils$TruncateAt r3 = android.text.TextUtils.TruncateAt.END
            r8.setEllipsize(r3)
            goto L_0x0c81
        L_0x0c75:
            android.text.TextUtils$TruncateAt r3 = android.text.TextUtils.TruncateAt.MIDDLE
            r8.setEllipsize(r3)
            goto L_0x0c81
        L_0x0c7b:
            android.text.TextUtils$TruncateAt r3 = android.text.TextUtils.TruncateAt.START
            r8.setEllipsize(r3)
        L_0x0c81:
            if (r9 != 0) goto L_0x0c8c
            if (r5 != 0) goto L_0x0c8c
            if (r2 != 0) goto L_0x0c8c
            if (r6 == 0) goto L_0x0c8a
            goto L_0x0c8c
        L_0x0c8a:
            r3 = 0
            goto L_0x0c8d
        L_0x0c8c:
            r3 = 1
        L_0x0c8d:
            if (r3 != 0) goto L_0x0ca6
            r101 = r0
            android.widget.Editor r0 = r8.mEditor
            if (r0 == 0) goto L_0x0ca2
            android.widget.Editor r0 = r8.mEditor
            int r0 = r0.mInputType
            r0 = r0 & 4095(0xfff, float:5.738E-42)
            r102 = r1
            r1 = 129(0x81, float:1.81E-43)
            if (r0 != r1) goto L_0x0ca4
            goto L_0x0caa
        L_0x0ca2:
            r102 = r1
        L_0x0ca4:
            r0 = 0
            goto L_0x0cab
        L_0x0ca6:
            r101 = r0
            r102 = r1
        L_0x0caa:
            r0 = 1
        L_0x0cab:
            if (r0 == 0) goto L_0x0cb5
            r103 = r2
            r1 = r78
            r2 = 3
            r1.mTypefaceIndex = r2
            goto L_0x0cb9
        L_0x0cb5:
            r103 = r2
            r1 = r78
        L_0x0cb9:
            r8.applyTextAppearance(r1)
            if (r3 == 0) goto L_0x0cc5
            android.text.method.PasswordTransformationMethod r2 = android.text.method.PasswordTransformationMethod.getInstance()
            r8.setTransformationMethod(r2)
        L_0x0cc5:
            r2 = r56
            if (r2 < 0) goto L_0x0cdd
            r105 = r0
            r104 = r1
            r1 = 1
            android.text.InputFilter[] r0 = new android.text.InputFilter[r1]
            android.text.InputFilter$LengthFilter r1 = new android.text.InputFilter$LengthFilter
            r1.<init>(r2)
            r17 = 0
            r0[r17] = r1
            r8.setFilters(r0)
            goto L_0x0ce6
        L_0x0cdd:
            r105 = r0
            r104 = r1
            android.text.InputFilter[] r0 = NO_FILTERS
            r8.setFilters(r0)
        L_0x0ce6:
            r1 = r51
            r8.setText((java.lang.CharSequence) r1, (android.widget.TextView.BufferType) r4)
            java.lang.CharSequence r0 = r8.mText
            if (r0 != 0) goto L_0x0cf3
            java.lang.String r0 = ""
            r8.mText = r0
        L_0x0cf3:
            java.lang.CharSequence r0 = r8.mTransformed
            if (r0 != 0) goto L_0x0cfb
            java.lang.String r0 = ""
            r8.mTransformed = r0
        L_0x0cfb:
            if (r77 == 0) goto L_0x0d03
            r106 = r1
            r1 = 1
            r8.mTextSetFromXmlOrResourceId = r1
            goto L_0x0d05
        L_0x0d03:
            r106 = r1
        L_0x0d05:
            r1 = r52
            if (r1 == 0) goto L_0x0d0c
            r8.setHint((java.lang.CharSequence) r1)
        L_0x0d0c:
            int[] r0 = com.android.internal.R.styleable.View
            r107 = r1
            r108 = r2
            r109 = r3
            r26 = r95
            r27 = r96
            r1 = r120
            r2 = r121
            r3 = r122
            android.content.res.TypedArray r0 = r10.obtainStyledAttributes(r1, r0, r2, r3)
            android.text.method.MovementMethod r1 = r8.mMovement
            if (r1 != 0) goto L_0x0d2f
            android.text.method.KeyListener r1 = r118.getKeyListener()
            if (r1 == 0) goto L_0x0d2d
            goto L_0x0d2f
        L_0x0d2d:
            r1 = 0
            goto L_0x0d30
        L_0x0d2f:
            r1 = 1
        L_0x0d30:
            if (r1 != 0) goto L_0x0d3c
            boolean r17 = r118.isClickable()
            if (r17 == 0) goto L_0x0d39
            goto L_0x0d3c
        L_0x0d39:
            r17 = 0
            goto L_0x0d3e
        L_0x0d3c:
            r17 = 1
        L_0x0d3e:
            if (r1 != 0) goto L_0x0d4a
            boolean r18 = r118.isLongClickable()
            if (r18 == 0) goto L_0x0d47
            goto L_0x0d4a
        L_0x0d47:
            r18 = 0
            goto L_0x0d4c
        L_0x0d4a:
            r18 = 1
        L_0x0d4c:
            int r20 = r118.getFocusable()
            r110 = r1
            int r1 = r0.getIndexCount()
            r111 = r4
            r3 = r17
            r4 = r18
            r2 = r20
            r17 = 0
        L_0x0d60:
            r112 = r17
            r113 = r5
            r5 = r112
            if (r5 >= r1) goto L_0x0db9
            r114 = r1
            int r1 = r0.getIndex(r5)
            r115 = r6
            r6 = 19
            if (r1 == r6) goto L_0x0d88
            switch(r1) {
                case 30: goto L_0x0d7e;
                case 31: goto L_0x0d78;
                default: goto L_0x0d77;
            }
        L_0x0d77:
            goto L_0x0d83
        L_0x0d78:
            boolean r1 = r0.getBoolean(r1, r4)
            r4 = r1
            goto L_0x0d83
        L_0x0d7e:
            boolean r3 = r0.getBoolean(r1, r3)
        L_0x0d83:
            r117 = r7
            r7 = 18
            goto L_0x0dae
        L_0x0d88:
            android.util.TypedValue r6 = new android.util.TypedValue
            r6.<init>()
            boolean r17 = r0.getValue(r1, r6)
            if (r17 == 0) goto L_0x0daa
            r116 = r1
            int r1 = r6.type
            r117 = r7
            r7 = 18
            if (r1 != r7) goto L_0x0da6
            int r1 = r6.data
            if (r1 != 0) goto L_0x0da3
            r1 = 0
            goto L_0x0da8
        L_0x0da3:
            r1 = 1
            goto L_0x0da8
        L_0x0da6:
            int r1 = r6.data
        L_0x0da8:
            r2 = r1
            goto L_0x0dae
        L_0x0daa:
            r117 = r7
            r7 = 18
        L_0x0dae:
            int r17 = r5 + 1
            r5 = r113
            r1 = r114
            r6 = r115
            r7 = r117
            goto L_0x0d60
        L_0x0db9:
            r114 = r1
            r115 = r6
            r117 = r7
            r0.recycle()
            int r1 = r118.getFocusable()
            if (r2 == r1) goto L_0x0dcb
            r8.setFocusable((int) r2)
        L_0x0dcb:
            r8.setClickable(r3)
            r8.setLongClickable(r4)
            android.widget.Editor r1 = r8.mEditor
            if (r1 == 0) goto L_0x0dda
            android.widget.Editor r1 = r8.mEditor
            r1.prepareCursorControllers()
        L_0x0dda:
            int r1 = r118.getImportantForAccessibility()
            if (r1 != 0) goto L_0x0de5
            r1 = 1
            r8.setImportantForAccessibility(r1)
            goto L_0x0de6
        L_0x0de5:
            r1 = 1
        L_0x0de6:
            boolean r5 = r118.supportsAutoSizeText()
            if (r5 == 0) goto L_0x0e30
            int r5 = r8.mAutoSizeTextType
            if (r5 != r1) goto L_0x0e33
            boolean r1 = r8.mHasPresetAutoSizeValues
            if (r1 != 0) goto L_0x0e2c
            android.content.res.Resources r1 = r118.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r6 = (r45 > r5 ? 1 : (r45 == r5 ? 0 : -1))
            if (r6 != 0) goto L_0x0e0a
            r6 = 1094713344(0x41400000, float:12.0)
            r7 = 2
            float r45 = android.util.TypedValue.applyDimension(r7, r6, r1)
            goto L_0x0e0b
        L_0x0e0a:
            r7 = 2
        L_0x0e0b:
            r6 = r45
            int r17 = (r46 > r5 ? 1 : (r46 == r5 ? 0 : -1))
            if (r17 != 0) goto L_0x0e17
            r5 = 1121976320(0x42e00000, float:112.0)
            float r46 = android.util.TypedValue.applyDimension(r7, r5, r1)
        L_0x0e17:
            r5 = r46
            r7 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r7 = (r47 > r7 ? 1 : (r47 == r7 ? 0 : -1))
            if (r7 != 0) goto L_0x0e21
            r47 = 1065353216(0x3f800000, float:1.0)
        L_0x0e21:
            r7 = r47
            r8.validateAndSetAutoSizeTextTypeUniformConfiguration(r6, r5, r7)
            r46 = r5
            r45 = r6
            r47 = r7
        L_0x0e2c:
            r118.setupAutoSizeText()
            goto L_0x0e33
        L_0x0e30:
            r1 = 0
            r8.mAutoSizeTextType = r1
        L_0x0e33:
            r1 = r71
            if (r1 < 0) goto L_0x0e3a
            r8.setFirstBaselineToTopHeight(r1)
        L_0x0e3a:
            r5 = r72
            if (r5 < 0) goto L_0x0e41
            r8.setLastBaselineToBottomHeight(r5)
        L_0x0e41:
            r6 = r73
            if (r6 < 0) goto L_0x0e48
            r8.setLineHeight(r6)
        L_0x0e48:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    private void setTextInternal(CharSequence text) {
        this.mText = text;
        PrecomputedText precomputedText = null;
        this.mSpannable = text instanceof Spannable ? (Spannable) text : null;
        if (text instanceof PrecomputedText) {
            precomputedText = (PrecomputedText) text;
        }
        this.mPrecomputed = precomputedText;
    }

    public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        if (supportsAutoSizeText()) {
            switch (autoSizeTextType) {
                case 0:
                    clearAutoSizeConfiguration();
                    return;
                case 1:
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                    if (setupAutoSizeText()) {
                        autoSizeText();
                        invalidate();
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Unknown auto-size text type: " + autoSizeTextType);
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) {
        if (supportsAutoSizeText()) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(unit, (float) autoSizeMinTextSize, displayMetrics), TypedValue.applyDimension(unit, (float) autoSizeMaxTextSize, displayMetrics), TypedValue.applyDimension(unit, (float) autoSizeStepGranularity, displayMetrics));
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] presetSizes, int unit) {
        if (supportsAutoSizeText()) {
            int presetSizesLength = presetSizes.length;
            if (presetSizesLength > 0) {
                int[] presetSizesInPx = new int[presetSizesLength];
                if (unit == 0) {
                    presetSizesInPx = Arrays.copyOf(presetSizes, presetSizesLength);
                } else {
                    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    for (int i = 0; i < presetSizesLength; i++) {
                        presetSizesInPx[i] = Math.round(TypedValue.applyDimension(unit, (float) presetSizes[i], displayMetrics));
                    }
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(presetSizesInPx);
                if (!setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(presetSizes));
                }
            } else {
                this.mHasPresetAutoSizeValues = false;
            }
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public int getAutoSizeTextType() {
        return this.mAutoSizeTextType;
    }

    public int getAutoSizeStepGranularity() {
        return Math.round(this.mAutoSizeStepGranularityInPx);
    }

    public int getAutoSizeMinTextSize() {
        return Math.round(this.mAutoSizeMinTextSizeInPx);
    }

    public int getAutoSizeMaxTextSize() {
        return Math.round(this.mAutoSizeMaxTextSizeInPx);
    }

    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx;
    }

    private void setupAutoSizeUniformPresetSizes(TypedArray textSizes) {
        int textSizesLength = textSizes.length();
        int[] parsedSizes = new int[textSizesLength];
        if (textSizesLength > 0) {
            for (int i = 0; i < textSizesLength; i++) {
                parsedSizes[i] = textSizes.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(parsedSizes);
            setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        int sizesLength = this.mAutoSizeTextSizesInPx.length;
        this.mHasPresetAutoSizeValues = sizesLength > 0;
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = (float) this.mAutoSizeTextSizesInPx[0];
            this.mAutoSizeMaxTextSizeInPx = (float) this.mAutoSizeTextSizesInPx[sizesLength - 1];
            this.mAutoSizeStepGranularityInPx = -1.0f;
        }
        return this.mHasPresetAutoSizeValues;
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float autoSizeMinTextSizeInPx, float autoSizeMaxTextSizeInPx, float autoSizeStepGranularityInPx) {
        if (autoSizeMinTextSizeInPx <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px) is less or equal to (0px)");
        } else if (autoSizeMaxTextSizeInPx <= autoSizeMinTextSizeInPx) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + autoSizeMaxTextSizeInPx + "px) is less or equal to minimum auto-size text size (" + autoSizeMinTextSizeInPx + "px)");
        } else if (autoSizeStepGranularityInPx > 0.0f) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = autoSizeMinTextSizeInPx;
            this.mAutoSizeMaxTextSizeInPx = autoSizeMaxTextSizeInPx;
            this.mAutoSizeStepGranularityInPx = autoSizeStepGranularityInPx;
            this.mHasPresetAutoSizeValues = false;
        } else {
            throw new IllegalArgumentException("The auto-size step granularity (" + autoSizeStepGranularityInPx + "px) is less or equal to (0px)");
        }
    }

    private void clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = EmptyArray.INT;
        this.mNeedsAutoSizeText = false;
    }

    private int[] cleanupAutoSizePresetSizes(int[] presetValues) {
        if (presetValuesLength == 0) {
            return presetValues;
        }
        Arrays.sort(presetValues);
        IntArray uniqueValidSizes = new IntArray();
        for (int currentPresetValue : presetValues) {
            if (currentPresetValue > 0 && uniqueValidSizes.binarySearch(currentPresetValue) < 0) {
                uniqueValidSizes.add(currentPresetValue);
            }
        }
        if (presetValuesLength == uniqueValidSizes.size()) {
            return presetValues;
        }
        return uniqueValidSizes.toArray();
    }

    private boolean setupAutoSizeText() {
        if (!supportsAutoSizeText() || this.mAutoSizeTextType != 1) {
            this.mNeedsAutoSizeText = false;
        } else {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int autoSizeValuesLength = ((int) Math.floor((double) ((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx))) + 1;
                int[] autoSizeTextSizesInPx = new int[autoSizeValuesLength];
                for (int i = 0; i < autoSizeValuesLength; i++) {
                    autoSizeTextSizesInPx[i] = Math.round(this.mAutoSizeMinTextSizeInPx + (((float) i) * this.mAutoSizeStepGranularityInPx));
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(autoSizeTextSizesInPx);
            }
            this.mNeedsAutoSizeText = true;
        }
        return this.mNeedsAutoSizeText;
    }

    private int[] parseDimensionArray(TypedArray dimens) {
        if (dimens == null) {
            return null;
        }
        int[] result = new int[dimens.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = dimens.getDimensionPixelSize(i, 0);
        }
        return result;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 100) {
            return;
        }
        if (resultCode == -1 && data != null) {
            CharSequence result = data.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
            if (result == null) {
                return;
            }
            if (isTextEditable()) {
                replaceSelectionWithText(result);
                if (this.mEditor != null) {
                    this.mEditor.refreshTextActionMode();
                }
            } else if (result.length() > 0) {
                Toast.makeText(getContext(), (CharSequence) String.valueOf(result), 1).show();
            }
        } else if (this.mSpannable != null) {
            Selection.setSelection(this.mSpannable, getSelectionEnd());
        }
    }

    private void setTypefaceFromAttrs(Typeface typeface, String familyName, int typefaceIndex, int style, int weight) {
        if (typeface == null && familyName != null) {
            resolveStyleAndSetTypeface(Typeface.create(familyName, 0), style, weight);
        } else if (typeface != null) {
            resolveStyleAndSetTypeface(typeface, style, weight);
        } else {
            switch (typefaceIndex) {
                case 1:
                    resolveStyleAndSetTypeface(Typeface.SANS_SERIF, style, weight);
                    return;
                case 2:
                    resolveStyleAndSetTypeface(Typeface.SERIF, style, weight);
                    return;
                case 3:
                    resolveStyleAndSetTypeface(Typeface.MONOSPACE, style, weight);
                    return;
                default:
                    resolveStyleAndSetTypeface((Typeface) null, style, weight);
                    return;
            }
        }
    }

    private void resolveStyleAndSetTypeface(Typeface typeface, int style, int weight) {
        if (weight >= 0) {
            setTypeface(Typeface.create(typeface, Math.min(1000, weight), (style & 2) != 0));
        } else {
            setTypeface(typeface, style);
        }
    }

    private void setRelativeDrawablesIfNeeded(Drawable start, Drawable end) {
        if ((start == null && end == null) ? false : true) {
            Drawables dr = this.mDrawables;
            if (dr == null) {
                Drawables drawables = new Drawables(getContext());
                dr = drawables;
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = true;
            Rect compoundRect = dr.mCompoundRect;
            int[] state = getDrawableState();
            if (start != null) {
                start.setBounds(0, 0, start.getIntrinsicWidth(), start.getIntrinsicHeight());
                start.setState(state);
                start.copyBounds(compoundRect);
                start.setCallback(this);
                dr.mDrawableStart = start;
                dr.mDrawableSizeStart = compoundRect.width();
                dr.mDrawableHeightStart = compoundRect.height();
            } else {
                dr.mDrawableHeightStart = 0;
                dr.mDrawableSizeStart = 0;
            }
            if (end != null) {
                end.setBounds(0, 0, end.getIntrinsicWidth(), end.getIntrinsicHeight());
                end.setState(state);
                end.copyBounds(compoundRect);
                end.setCallback(this);
                dr.mDrawableEnd = end;
                dr.mDrawableSizeEnd = compoundRect.width();
                dr.mDrawableHeightEnd = compoundRect.height();
            } else {
                dr.mDrawableHeightEnd = 0;
                dr.mDrawableSizeEnd = 0;
            }
            resetResolvedDrawables();
            resolveDrawables();
            applyCompoundDrawableTint();
        }
    }

    @RemotableViewMethod
    public void setEnabled(boolean enabled) {
        InputMethodManager imm;
        InputMethodManager imm2;
        if (enabled != isEnabled()) {
            if (!enabled && (imm2 = getInputMethodManager()) != null && imm2.isActive(this)) {
                imm2.hideSoftInputFromWindow(getWindowToken(), 0);
            }
            super.setEnabled(enabled);
            if (enabled && (imm = getInputMethodManager()) != null) {
                imm.restartInput(this);
            }
            if (this.mEditor != null) {
                this.mEditor.invalidateTextDisplayList();
                this.mEditor.prepareCursorControllers();
                this.mEditor.makeBlink();
            }
        }
    }

    public void setTypeface(Typeface tf, int style) {
        Typeface tf2;
        float f = 0.0f;
        boolean z = false;
        if (style > 0) {
            if (tf == null) {
                tf2 = Typeface.defaultFromStyle(style);
            } else {
                tf2 = Typeface.create(tf, style);
            }
            setTypeface(tf2);
            int need = (~(tf2 != null ? tf2.getStyle() : 0)) & style;
            TextPaint textPaint = this.mTextPaint;
            if ((need & 1) != 0) {
                z = true;
            }
            textPaint.setFakeBoldText(z);
            TextPaint textPaint2 = this.mTextPaint;
            if ((need & 2) != 0) {
                f = -0.25f;
            }
            textPaint2.setTextSkewX(f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        setTypeface(tf);
    }

    /* access modifiers changed from: protected */
    public boolean getDefaultEditable() {
        return false;
    }

    /* access modifiers changed from: protected */
    public MovementMethod getDefaultMovementMethod() {
        return null;
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getText() {
        return this.mText;
    }

    public int length() {
        return this.mText.length();
    }

    public Editable getEditableText() {
        if (this.mText instanceof Editable) {
            return (Editable) this.mText;
        }
        return null;
    }

    @VisibleForTesting
    public CharSequence getTransformed() {
        return this.mTransformed;
    }

    public int getLineHeight() {
        return FastMath.round((((float) this.mTextPaint.getFontMetricsInt((Paint.FontMetricsInt) null)) * this.mSpacingMult) + this.mSpacingAdd);
    }

    public final Layout getLayout() {
        return this.mLayout;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public final Layout getHintLayout() {
        return this.mHintLayout;
    }

    public final UndoManager getUndoManager() {
        throw new UnsupportedOperationException("not implemented");
    }

    @VisibleForTesting
    public final Editor getEditorForTesting() {
        return this.mEditor;
    }

    public final void setUndoManager(UndoManager undoManager, String tag) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final KeyListener getKeyListener() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mKeyListener;
    }

    public void setKeyListener(KeyListener input) {
        this.mListenerChanged = true;
        setKeyListenerOnly(input);
        fixFocusableAndClickableSettings();
        if (input != null) {
            createEditorIfNeeded();
            setInputTypeFromEditor();
        } else if (this.mEditor != null) {
            this.mEditor.mInputType = 0;
        }
        InputMethodManager imm = getInputMethodManager();
        if (imm != null) {
            imm.restartInput(this);
        }
    }

    private void setInputTypeFromEditor() {
        try {
            this.mEditor.mInputType = this.mEditor.mKeyListener.getInputType();
        } catch (IncompatibleClassChangeError e) {
            this.mEditor.mInputType = 1;
        }
        setInputTypeSingleLine(this.mSingleLine);
    }

    private void setKeyListenerOnly(KeyListener input) {
        if (this.mEditor != null || input != null) {
            createEditorIfNeeded();
            if (this.mEditor.mKeyListener != input) {
                this.mEditor.mKeyListener = input;
                if (input != null && !(this.mText instanceof Editable)) {
                    setText(this.mText);
                }
                setFilters((Editable) this.mText, this.mFilters);
            }
        }
    }

    public final MovementMethod getMovementMethod() {
        return this.mMovement;
    }

    public final void setMovementMethod(MovementMethod movement) {
        if (this.mMovement != movement) {
            this.mMovement = movement;
            if (movement != null && this.mSpannable == null) {
                setText(this.mText);
            }
            fixFocusableAndClickableSettings();
            if (this.mEditor != null) {
                this.mEditor.prepareCursorControllers();
            }
        }
    }

    private void fixFocusableAndClickableSettings() {
        if (this.mMovement == null && (this.mEditor == null || this.mEditor.mKeyListener == null)) {
            setFocusable(16);
            setClickable(false);
            setLongClickable(false);
            return;
        }
        setFocusable(1);
        setClickable(true);
        setLongClickable(true);
    }

    public final TransformationMethod getTransformationMethod() {
        return this.mTransformation;
    }

    public final void setTransformationMethod(TransformationMethod method) {
        if (method != this.mTransformation) {
            if (!(this.mTransformation == null || this.mSpannable == null)) {
                this.mSpannable.removeSpan(this.mTransformation);
            }
            this.mTransformation = method;
            if (method instanceof TransformationMethod2) {
                TransformationMethod2 method2 = (TransformationMethod2) method;
                this.mAllowTransformationLengthChange = !isTextSelectable() && !(this.mText instanceof Editable);
                method2.setLengthChangesAllowed(this.mAllowTransformationLengthChange);
            } else {
                this.mAllowTransformationLengthChange = false;
            }
            setText(this.mText);
            if (hasPasswordTransformationMethod()) {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
            this.mTextDir = getTextDirectionHeuristic();
        }
    }

    public int getCompoundPaddingTop() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[1] == null) {
            return this.mPaddingTop;
        }
        return this.mPaddingTop + dr.mDrawablePadding + dr.mDrawableSizeTop;
    }

    public int getCompoundPaddingBottom() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[3] == null) {
            return this.mPaddingBottom;
        }
        return this.mPaddingBottom + dr.mDrawablePadding + dr.mDrawableSizeBottom;
    }

    public int getCompoundPaddingLeft() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[0] == null) {
            return this.mPaddingLeft;
        }
        return this.mPaddingLeft + dr.mDrawablePadding + dr.mDrawableSizeLeft;
    }

    public int getCompoundPaddingRight() {
        Drawables dr = this.mDrawables;
        if (dr == null || dr.mShowing[2] == null) {
            return this.mPaddingRight;
        }
        return this.mPaddingRight + dr.mDrawablePadding + dr.mDrawableSizeRight;
    }

    public int getCompoundPaddingStart() {
        resolveDrawables();
        if (getLayoutDirection() != 1) {
            return getCompoundPaddingLeft();
        }
        return getCompoundPaddingRight();
    }

    public int getCompoundPaddingEnd() {
        resolveDrawables();
        if (getLayoutDirection() != 1) {
            return getCompoundPaddingRight();
        }
        return getCompoundPaddingLeft();
    }

    public int getExtendedPaddingTop() {
        int gravity;
        if (this.mMaxMode != 1) {
            return getCompoundPaddingTop();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingTop();
        }
        int top = getCompoundPaddingTop();
        int viewht = (getHeight() - top) - getCompoundPaddingBottom();
        int layoutht = this.mLayout.getLineTop(this.mMaximum);
        if (layoutht >= viewht || (gravity = this.mGravity & 112) == 48) {
            return top;
        }
        if (gravity == 80) {
            return (top + viewht) - layoutht;
        }
        return ((viewht - layoutht) / 2) + top;
    }

    public int getExtendedPaddingBottom() {
        if (this.mMaxMode != 1) {
            return getCompoundPaddingBottom();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingBottom();
        }
        int top = getCompoundPaddingTop();
        int bottom = getCompoundPaddingBottom();
        int viewht = (getHeight() - top) - bottom;
        int layoutht = this.mLayout.getLineTop(this.mMaximum);
        if (layoutht >= viewht) {
            return bottom;
        }
        int gravity = this.mGravity & 112;
        if (gravity == 48) {
            return (bottom + viewht) - layoutht;
        }
        if (gravity == 80) {
            return bottom;
        }
        return ((viewht - layoutht) / 2) + bottom;
    }

    public int getTotalPaddingLeft() {
        return getCompoundPaddingLeft();
    }

    public int getTotalPaddingRight() {
        return getCompoundPaddingRight();
    }

    public int getTotalPaddingStart() {
        return getCompoundPaddingStart();
    }

    public int getTotalPaddingEnd() {
        return getCompoundPaddingEnd();
    }

    public int getTotalPaddingTop() {
        return getExtendedPaddingTop() + getVerticalOffset(true);
    }

    public int getTotalPaddingBottom() {
        return getExtendedPaddingBottom() + getBottomVerticalOffset(true);
    }

    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            if (dr.mDrawableStart != null) {
                dr.mDrawableStart.setCallback((Drawable.Callback) null);
            }
            dr.mDrawableStart = null;
            if (dr.mDrawableEnd != null) {
                dr.mDrawableEnd.setCallback((Drawable.Callback) null);
            }
            dr.mDrawableEnd = null;
            dr.mDrawableHeightStart = 0;
            dr.mDrawableSizeStart = 0;
            dr.mDrawableHeightEnd = 0;
            dr.mDrawableSizeEnd = 0;
        }
        if ((left == null && top == null && right == null && bottom == null) ? false : true) {
            if (dr == null) {
                Drawables drawables = new Drawables(getContext());
                dr = drawables;
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = false;
            if (!(dr.mShowing[0] == left || dr.mShowing[0] == null)) {
                dr.mShowing[0].setCallback((Drawable.Callback) null);
            }
            dr.mShowing[0] = left;
            if (!(dr.mShowing[1] == top || dr.mShowing[1] == null)) {
                dr.mShowing[1].setCallback((Drawable.Callback) null);
            }
            dr.mShowing[1] = top;
            if (!(dr.mShowing[2] == right || dr.mShowing[2] == null)) {
                dr.mShowing[2].setCallback((Drawable.Callback) null);
            }
            dr.mShowing[2] = right;
            if (!(dr.mShowing[3] == bottom || dr.mShowing[3] == null)) {
                dr.mShowing[3].setCallback((Drawable.Callback) null);
            }
            dr.mShowing[3] = bottom;
            Rect compoundRect = dr.mCompoundRect;
            int[] state = getDrawableState();
            if (left != null) {
                left.setState(state);
                left.copyBounds(compoundRect);
                left.setCallback(this);
                dr.mDrawableSizeLeft = compoundRect.width();
                dr.mDrawableHeightLeft = compoundRect.height();
            } else {
                dr.mDrawableHeightLeft = 0;
                dr.mDrawableSizeLeft = 0;
            }
            if (right != null) {
                right.setState(state);
                right.copyBounds(compoundRect);
                right.setCallback(this);
                dr.mDrawableSizeRight = compoundRect.width();
                dr.mDrawableHeightRight = compoundRect.height();
            } else {
                dr.mDrawableHeightRight = 0;
                dr.mDrawableSizeRight = 0;
            }
            if (top != null) {
                top.setState(state);
                top.copyBounds(compoundRect);
                top.setCallback(this);
                dr.mDrawableSizeTop = compoundRect.height();
                dr.mDrawableWidthTop = compoundRect.width();
            } else {
                dr.mDrawableWidthTop = 0;
                dr.mDrawableSizeTop = 0;
            }
            if (bottom != null) {
                bottom.setState(state);
                bottom.copyBounds(compoundRect);
                bottom.setCallback(this);
                dr.mDrawableSizeBottom = compoundRect.height();
                dr.mDrawableWidthBottom = compoundRect.width();
            } else {
                dr.mDrawableWidthBottom = 0;
                dr.mDrawableSizeBottom = 0;
            }
        } else if (dr != null) {
            if (!dr.hasMetadata()) {
                this.mDrawables = null;
            } else {
                int i = dr.mShowing.length - 1;
                while (true) {
                    int i2 = i;
                    if (i2 < 0) {
                        break;
                    }
                    if (dr.mShowing[i2] != null) {
                        dr.mShowing[i2].setCallback((Drawable.Callback) null);
                    }
                    dr.mShowing[i2] = null;
                    i = i2 - 1;
                }
                dr.mDrawableHeightLeft = 0;
                dr.mDrawableSizeLeft = 0;
                dr.mDrawableHeightRight = 0;
                dr.mDrawableSizeRight = 0;
                dr.mDrawableWidthTop = 0;
                dr.mDrawableSizeTop = 0;
                dr.mDrawableWidthBottom = 0;
                dr.mDrawableSizeBottom = 0;
            }
        }
        if (dr != null) {
            dr.mDrawableLeftInitial = left;
            dr.mDrawableRightInitial = right;
        }
        resetResolvedDrawables();
        resolveDrawables();
        applyCompoundDrawableTint();
        invalidate();
        requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Context context = getContext();
        Drawable drawable4 = null;
        if (left != 0) {
            drawable = context.getDrawable(left);
        } else {
            drawable = null;
        }
        if (top != 0) {
            drawable2 = context.getDrawable(top);
        } else {
            drawable2 = null;
        }
        if (right != 0) {
            drawable3 = context.getDrawable(right);
        } else {
            drawable3 = null;
        }
        if (bottom != 0) {
            drawable4 = context.getDrawable(bottom);
        }
        setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (left != null) {
            left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
        }
        if (right != null) {
            right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }
        setCompoundDrawables(left, top, right, bottom);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelative(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            if (dr.mShowing[0] != null) {
                dr.mShowing[0].setCallback((Drawable.Callback) null);
            }
            Drawable[] drawableArr = dr.mShowing;
            dr.mDrawableLeftInitial = null;
            drawableArr[0] = null;
            if (dr.mShowing[2] != null) {
                dr.mShowing[2].setCallback((Drawable.Callback) null);
            }
            Drawable[] drawableArr2 = dr.mShowing;
            dr.mDrawableRightInitial = null;
            drawableArr2[2] = null;
            dr.mDrawableHeightLeft = 0;
            dr.mDrawableSizeLeft = 0;
            dr.mDrawableHeightRight = 0;
            dr.mDrawableSizeRight = 0;
        }
        if ((start == null && top == null && end == null && bottom == null) ? false : true) {
            if (dr == null) {
                Drawables drawables = new Drawables(getContext());
                dr = drawables;
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = true;
            if (!(dr.mDrawableStart == start || dr.mDrawableStart == null)) {
                dr.mDrawableStart.setCallback((Drawable.Callback) null);
            }
            dr.mDrawableStart = start;
            if (!(dr.mShowing[1] == top || dr.mShowing[1] == null)) {
                dr.mShowing[1].setCallback((Drawable.Callback) null);
            }
            dr.mShowing[1] = top;
            if (!(dr.mDrawableEnd == end || dr.mDrawableEnd == null)) {
                dr.mDrawableEnd.setCallback((Drawable.Callback) null);
            }
            dr.mDrawableEnd = end;
            if (!(dr.mShowing[3] == bottom || dr.mShowing[3] == null)) {
                dr.mShowing[3].setCallback((Drawable.Callback) null);
            }
            dr.mShowing[3] = bottom;
            Rect compoundRect = dr.mCompoundRect;
            int[] state = getDrawableState();
            if (start != null) {
                start.setState(state);
                start.copyBounds(compoundRect);
                start.setCallback(this);
                dr.mDrawableSizeStart = compoundRect.width();
                dr.mDrawableHeightStart = compoundRect.height();
            } else {
                dr.mDrawableHeightStart = 0;
                dr.mDrawableSizeStart = 0;
            }
            if (end != null) {
                end.setState(state);
                end.copyBounds(compoundRect);
                end.setCallback(this);
                dr.mDrawableSizeEnd = compoundRect.width();
                dr.mDrawableHeightEnd = compoundRect.height();
            } else {
                dr.mDrawableHeightEnd = 0;
                dr.mDrawableSizeEnd = 0;
            }
            if (top != null) {
                top.setState(state);
                top.copyBounds(compoundRect);
                top.setCallback(this);
                dr.mDrawableSizeTop = compoundRect.height();
                dr.mDrawableWidthTop = compoundRect.width();
            } else {
                dr.mDrawableWidthTop = 0;
                dr.mDrawableSizeTop = 0;
            }
            if (bottom != null) {
                bottom.setState(state);
                bottom.copyBounds(compoundRect);
                bottom.setCallback(this);
                dr.mDrawableSizeBottom = compoundRect.height();
                dr.mDrawableWidthBottom = compoundRect.width();
            } else {
                dr.mDrawableWidthBottom = 0;
                dr.mDrawableSizeBottom = 0;
            }
        } else if (dr != null) {
            if (!dr.hasMetadata()) {
                this.mDrawables = null;
            } else {
                if (dr.mDrawableStart != null) {
                    dr.mDrawableStart.setCallback((Drawable.Callback) null);
                }
                dr.mDrawableStart = null;
                if (dr.mShowing[1] != null) {
                    dr.mShowing[1].setCallback((Drawable.Callback) null);
                }
                dr.mShowing[1] = null;
                if (dr.mDrawableEnd != null) {
                    dr.mDrawableEnd.setCallback((Drawable.Callback) null);
                }
                dr.mDrawableEnd = null;
                if (dr.mShowing[3] != null) {
                    dr.mShowing[3].setCallback((Drawable.Callback) null);
                }
                dr.mShowing[3] = null;
                dr.mDrawableHeightStart = 0;
                dr.mDrawableSizeStart = 0;
                dr.mDrawableHeightEnd = 0;
                dr.mDrawableSizeEnd = 0;
                dr.mDrawableWidthTop = 0;
                dr.mDrawableSizeTop = 0;
                dr.mDrawableWidthBottom = 0;
                dr.mDrawableSizeBottom = 0;
            }
        }
        resetResolvedDrawables();
        resolveDrawables();
        invalidate();
        requestLayout();
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Context context = getContext();
        Drawable drawable4 = null;
        if (start != 0) {
            drawable = context.getDrawable(start);
        } else {
            drawable = null;
        }
        if (top != 0) {
            drawable2 = context.getDrawable(top);
        } else {
            drawable2 = null;
        }
        if (end != 0) {
            drawable3 = context.getDrawable(end);
        } else {
            drawable3 = null;
        }
        if (bottom != 0) {
            drawable4 = context.getDrawable(bottom);
        }
        setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable start, Drawable top, Drawable end, Drawable bottom) {
        if (start != null) {
            start.setBounds(0, 0, start.getIntrinsicWidth(), start.getIntrinsicHeight());
        }
        if (end != null) {
            end.setBounds(0, 0, end.getIntrinsicWidth(), end.getIntrinsicHeight());
        }
        if (top != null) {
            top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
        }
        setCompoundDrawablesRelative(start, top, end, bottom);
    }

    public Drawable[] getCompoundDrawables() {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            return (Drawable[]) dr.mShowing.clone();
        }
        return new Drawable[]{null, null, null, null};
    }

    public Drawable[] getCompoundDrawablesRelative() {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            return new Drawable[]{dr.mDrawableStart, dr.mShowing[1], dr.mDrawableEnd, dr.mShowing[3]};
        }
        return new Drawable[]{null, null, null, null};
    }

    @RemotableViewMethod
    public void setCompoundDrawablePadding(int pad) {
        Drawables dr = this.mDrawables;
        if (pad != 0) {
            if (dr == null) {
                Drawables drawables = new Drawables(getContext());
                dr = drawables;
                this.mDrawables = drawables;
            }
            dr.mDrawablePadding = pad;
        } else if (dr != null) {
            dr.mDrawablePadding = pad;
        }
        invalidate();
        requestLayout();
    }

    public int getCompoundDrawablePadding() {
        Drawables dr = this.mDrawables;
        if (dr != null) {
            return dr.mDrawablePadding;
        }
        return 0;
    }

    public void setCompoundDrawableTintList(ColorStateList tint) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(getContext());
        }
        this.mDrawables.mTintList = tint;
        this.mDrawables.mHasTint = true;
        applyCompoundDrawableTint();
    }

    public ColorStateList getCompoundDrawableTintList() {
        if (this.mDrawables != null) {
            return this.mDrawables.mTintList;
        }
        return null;
    }

    public void setCompoundDrawableTintMode(PorterDuff.Mode tintMode) {
        setCompoundDrawableTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
    }

    public void setCompoundDrawableTintBlendMode(BlendMode blendMode) {
        if (this.mDrawables == null) {
            this.mDrawables = new Drawables(getContext());
        }
        this.mDrawables.mBlendMode = blendMode;
        this.mDrawables.mHasTintMode = true;
        applyCompoundDrawableTint();
    }

    public PorterDuff.Mode getCompoundDrawableTintMode() {
        BlendMode mode = getCompoundDrawableTintBlendMode();
        if (mode != null) {
            return BlendMode.blendModeToPorterDuffMode(mode);
        }
        return null;
    }

    public BlendMode getCompoundDrawableTintBlendMode() {
        if (this.mDrawables != null) {
            return this.mDrawables.mBlendMode;
        }
        return null;
    }

    private void applyCompoundDrawableTint() {
        if (this.mDrawables != null) {
            if (this.mDrawables.mHasTint || this.mDrawables.mHasTintMode) {
                ColorStateList tintList = this.mDrawables.mTintList;
                BlendMode blendMode = this.mDrawables.mBlendMode;
                boolean hasTint = this.mDrawables.mHasTint;
                boolean hasTintMode = this.mDrawables.mHasTintMode;
                int[] state = getDrawableState();
                for (Drawable dr : this.mDrawables.mShowing) {
                    if (!(dr == null || dr == this.mDrawables.mDrawableError)) {
                        dr.mutate();
                        if (hasTint) {
                            dr.setTintList(tintList);
                        }
                        if (hasTintMode) {
                            dr.setTintBlendMode(blendMode);
                        }
                        if (dr.isStateful()) {
                            dr.setState(state);
                        }
                    }
                }
            }
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        if (!(left == this.mPaddingLeft && right == this.mPaddingRight && top == this.mPaddingTop && bottom == this.mPaddingBottom)) {
            nullLayouts();
        }
        super.setPadding(left, top, right, bottom);
        invalidate();
    }

    public void setPaddingRelative(int start, int top, int end, int bottom) {
        if (!(start == getPaddingStart() && end == getPaddingEnd() && top == this.mPaddingTop && bottom == this.mPaddingBottom)) {
            nullLayouts();
        }
        super.setPaddingRelative(start, top, end, bottom);
        invalidate();
    }

    public void setFirstBaselineToTopHeight(int firstBaselineToTopHeight) {
        int fontMetricsTop;
        Preconditions.checkArgumentNonnegative(firstBaselineToTopHeight);
        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            fontMetricsTop = fontMetrics.top;
        } else {
            fontMetricsTop = fontMetrics.ascent;
        }
        if (firstBaselineToTopHeight > Math.abs(fontMetricsTop)) {
            setPadding(getPaddingLeft(), firstBaselineToTopHeight - (-fontMetricsTop), getPaddingRight(), getPaddingBottom());
        }
    }

    public void setLastBaselineToBottomHeight(int lastBaselineToBottomHeight) {
        int fontMetricsBottom;
        Preconditions.checkArgumentNonnegative(lastBaselineToBottomHeight);
        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            fontMetricsBottom = fontMetrics.bottom;
        } else {
            fontMetricsBottom = fontMetrics.descent;
        }
        if (lastBaselineToBottomHeight > Math.abs(fontMetricsBottom)) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), lastBaselineToBottomHeight - fontMetricsBottom);
        }
    }

    public int getFirstBaselineToTopHeight() {
        return getPaddingTop() - getPaint().getFontMetricsInt().top;
    }

    public int getLastBaselineToBottomHeight() {
        return getPaddingBottom() + getPaint().getFontMetricsInt().bottom;
    }

    public final int getAutoLinkMask() {
        return this.mAutoLinkMask;
    }

    @RemotableViewMethod
    public void setTextSelectHandle(Drawable textSelectHandle) {
        Preconditions.checkNotNull(textSelectHandle, "The text select handle should not be null.");
        this.mTextSelectHandle = textSelectHandle;
        this.mTextSelectHandleRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandle(int textSelectHandle) {
        Preconditions.checkArgument(textSelectHandle != 0, "The text select handle should be a valid drawable resource id.");
        setTextSelectHandle(this.mContext.getDrawable(textSelectHandle));
    }

    public Drawable getTextSelectHandle() {
        if (this.mTextSelectHandle == null && this.mTextSelectHandleRes != 0) {
            this.mTextSelectHandle = this.mContext.getDrawable(this.mTextSelectHandleRes);
        }
        return this.mTextSelectHandle;
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(Drawable textSelectHandleLeft) {
        Preconditions.checkNotNull(textSelectHandleLeft, "The left text select handle should not be null.");
        this.mTextSelectHandleLeft = textSelectHandleLeft;
        this.mTextSelectHandleLeftRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleLeft(int textSelectHandleLeft) {
        Preconditions.checkArgument(textSelectHandleLeft != 0, "The text select left handle should be a valid drawable resource id.");
        setTextSelectHandleLeft(this.mContext.getDrawable(textSelectHandleLeft));
    }

    public Drawable getTextSelectHandleLeft() {
        if (this.mTextSelectHandleLeft == null && this.mTextSelectHandleLeftRes != 0) {
            this.mTextSelectHandleLeft = this.mContext.getDrawable(this.mTextSelectHandleLeftRes);
        }
        return this.mTextSelectHandleLeft;
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(Drawable textSelectHandleRight) {
        Preconditions.checkNotNull(textSelectHandleRight, "The right text select handle should not be null.");
        this.mTextSelectHandleRight = textSelectHandleRight;
        this.mTextSelectHandleRightRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @RemotableViewMethod
    public void setTextSelectHandleRight(int textSelectHandleRight) {
        Preconditions.checkArgument(textSelectHandleRight != 0, "The text select right handle should be a valid drawable resource id.");
        setTextSelectHandleRight(this.mContext.getDrawable(textSelectHandleRight));
    }

    public Drawable getTextSelectHandleRight() {
        if (this.mTextSelectHandleRight == null && this.mTextSelectHandleRightRes != 0) {
            this.mTextSelectHandleRight = this.mContext.getDrawable(this.mTextSelectHandleRightRes);
        }
        return this.mTextSelectHandleRight;
    }

    public void setTextCursorDrawable(Drawable textCursorDrawable) {
        this.mCursorDrawable = textCursorDrawable;
        this.mCursorDrawableRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadCursorDrawable();
        }
    }

    public void setTextCursorDrawable(int textCursorDrawable) {
        setTextCursorDrawable(textCursorDrawable != 0 ? this.mContext.getDrawable(textCursorDrawable) : null);
    }

    public Drawable getTextCursorDrawable() {
        if (this.mCursorDrawable == null && this.mCursorDrawableRes != 0) {
            this.mCursorDrawable = this.mContext.getDrawable(this.mCursorDrawableRes);
        }
        return this.mCursorDrawable;
    }

    public void setTextAppearance(int resId) {
        setTextAppearance(this.mContext, resId);
    }

    @Deprecated
    public void setTextAppearance(Context context, int resId) {
        TypedArray ta = context.obtainStyledAttributes(resId, R.styleable.TextAppearance);
        TextAppearanceAttributes attributes = new TextAppearanceAttributes();
        readTextAppearance(context, ta, attributes, false);
        ta.recycle();
        applyTextAppearance(attributes);
    }

    private static class TextAppearanceAttributes {
        boolean mAllCaps;
        boolean mElegant;
        boolean mFallbackLineSpacing;
        String mFontFamily;
        boolean mFontFamilyExplicit;
        String mFontFeatureSettings;
        Typeface mFontTypeface;
        String mFontVariationSettings;
        int mFontWeight;
        boolean mHasElegant;
        boolean mHasFallbackLineSpacing;
        boolean mHasLetterSpacing;
        float mLetterSpacing;
        int mShadowColor;
        float mShadowDx;
        float mShadowDy;
        float mShadowRadius;
        ColorStateList mTextColor;
        int mTextColorHighlight;
        ColorStateList mTextColorHint;
        ColorStateList mTextColorLink;
        LocaleList mTextLocales;
        int mTextSize;
        int mTextStyle;
        int mTypefaceIndex;

        private TextAppearanceAttributes() {
            this.mTextColorHighlight = 0;
            this.mTextColor = null;
            this.mTextColorHint = null;
            this.mTextColorLink = null;
            this.mTextSize = -1;
            this.mTextLocales = null;
            this.mFontFamily = null;
            this.mFontTypeface = null;
            this.mFontFamilyExplicit = false;
            this.mTypefaceIndex = -1;
            this.mTextStyle = 0;
            this.mFontWeight = -1;
            this.mAllCaps = false;
            this.mShadowColor = 0;
            this.mShadowDx = 0.0f;
            this.mShadowDy = 0.0f;
            this.mShadowRadius = 0.0f;
            this.mHasElegant = false;
            this.mElegant = false;
            this.mHasFallbackLineSpacing = false;
            this.mFallbackLineSpacing = false;
            this.mHasLetterSpacing = false;
            this.mLetterSpacing = 0.0f;
            this.mFontFeatureSettings = null;
            this.mFontVariationSettings = null;
        }

        public String toString() {
            return "TextAppearanceAttributes {\n    mTextColorHighlight:" + this.mTextColorHighlight + "\n    mTextColor:" + this.mTextColor + "\n    mTextColorHint:" + this.mTextColorHint + "\n    mTextColorLink:" + this.mTextColorLink + "\n    mTextSize:" + this.mTextSize + "\n    mTextLocales:" + this.mTextLocales + "\n    mFontFamily:" + this.mFontFamily + "\n    mFontTypeface:" + this.mFontTypeface + "\n    mFontFamilyExplicit:" + this.mFontFamilyExplicit + "\n    mTypefaceIndex:" + this.mTypefaceIndex + "\n    mTextStyle:" + this.mTextStyle + "\n    mFontWeight:" + this.mFontWeight + "\n    mAllCaps:" + this.mAllCaps + "\n    mShadowColor:" + this.mShadowColor + "\n    mShadowDx:" + this.mShadowDx + "\n    mShadowDy:" + this.mShadowDy + "\n    mShadowRadius:" + this.mShadowRadius + "\n    mHasElegant:" + this.mHasElegant + "\n    mElegant:" + this.mElegant + "\n    mHasFallbackLineSpacing:" + this.mHasFallbackLineSpacing + "\n    mFallbackLineSpacing:" + this.mFallbackLineSpacing + "\n    mHasLetterSpacing:" + this.mHasLetterSpacing + "\n    mLetterSpacing:" + this.mLetterSpacing + "\n    mFontFeatureSettings:" + this.mFontFeatureSettings + "\n    mFontVariationSettings:" + this.mFontVariationSettings + "\n}";
        }
    }

    private void readTextAppearance(Context context, TypedArray appearance, TextAppearanceAttributes attributes, boolean styleArray) {
        int n = appearance.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = appearance.getIndex(i);
            int index = attr;
            if (!styleArray || (index = sAppearanceValues.get(attr, -1)) != -1) {
                switch (index) {
                    case 0:
                        attributes.mTextSize = appearance.getDimensionPixelSize(attr, attributes.mTextSize);
                        break;
                    case 1:
                        attributes.mTypefaceIndex = appearance.getInt(attr, attributes.mTypefaceIndex);
                        if (attributes.mTypefaceIndex != -1 && !attributes.mFontFamilyExplicit) {
                            attributes.mFontFamily = null;
                            break;
                        }
                    case 2:
                        attributes.mTextStyle = appearance.getInt(attr, attributes.mTextStyle);
                        break;
                    case 3:
                        attributes.mTextColor = appearance.getColorStateList(attr);
                        break;
                    case 4:
                        attributes.mTextColorHighlight = appearance.getColor(attr, attributes.mTextColorHighlight);
                        break;
                    case 5:
                        attributes.mTextColorHint = appearance.getColorStateList(attr);
                        break;
                    case 6:
                        attributes.mTextColorLink = appearance.getColorStateList(attr);
                        break;
                    case 7:
                        attributes.mShadowColor = appearance.getInt(attr, attributes.mShadowColor);
                        break;
                    case 8:
                        attributes.mShadowDx = appearance.getFloat(attr, attributes.mShadowDx);
                        break;
                    case 9:
                        attributes.mShadowDy = appearance.getFloat(attr, attributes.mShadowDy);
                        break;
                    case 10:
                        attributes.mShadowRadius = appearance.getFloat(attr, attributes.mShadowRadius);
                        break;
                    case 11:
                        attributes.mAllCaps = appearance.getBoolean(attr, attributes.mAllCaps);
                        break;
                    case 12:
                        if (!context.isRestricted() && context.canLoadUnsafeResources()) {
                            try {
                                attributes.mFontTypeface = appearance.getFont(attr);
                            } catch (Resources.NotFoundException | UnsupportedOperationException e) {
                            }
                        }
                        if (attributes.mFontTypeface == null) {
                            attributes.mFontFamily = appearance.getString(attr);
                        }
                        attributes.mFontFamilyExplicit = true;
                        break;
                    case 13:
                        attributes.mHasElegant = true;
                        attributes.mElegant = appearance.getBoolean(attr, attributes.mElegant);
                        break;
                    case 14:
                        attributes.mHasLetterSpacing = true;
                        attributes.mLetterSpacing = appearance.getFloat(attr, attributes.mLetterSpacing);
                        break;
                    case 15:
                        attributes.mFontFeatureSettings = appearance.getString(attr);
                        break;
                    case 16:
                        attributes.mFontVariationSettings = appearance.getString(attr);
                        break;
                    case 17:
                        attributes.mHasFallbackLineSpacing = true;
                        attributes.mFallbackLineSpacing = appearance.getBoolean(attr, attributes.mFallbackLineSpacing);
                        break;
                    case 18:
                        attributes.mFontWeight = appearance.getInt(attr, attributes.mFontWeight);
                        break;
                    case 19:
                        String localeString = appearance.getString(attr);
                        if (localeString == null) {
                            break;
                        } else {
                            LocaleList localeList = LocaleList.forLanguageTags(localeString);
                            if (localeList.isEmpty()) {
                                break;
                            } else {
                                attributes.mTextLocales = localeList;
                                break;
                            }
                        }
                }
            }
        }
    }

    private void applyTextAppearance(TextAppearanceAttributes attributes) {
        if (attributes.mTextColor != null) {
            setTextColor(attributes.mTextColor);
        }
        if (attributes.mTextColorHint != null) {
            setHintTextColor(attributes.mTextColorHint);
        }
        if (attributes.mTextColorLink != null) {
            setLinkTextColor(attributes.mTextColorLink);
        }
        if (attributes.mTextColorHighlight != 0) {
            setHighlightColor(attributes.mTextColorHighlight);
        }
        if (attributes.mTextSize != -1) {
            setRawTextSize((float) attributes.mTextSize, true);
        }
        if (attributes.mTextLocales != null) {
            setTextLocales(attributes.mTextLocales);
        }
        if (attributes.mTypefaceIndex != -1 && !attributes.mFontFamilyExplicit) {
            attributes.mFontFamily = null;
        }
        setTypefaceFromAttrs(attributes.mFontTypeface, attributes.mFontFamily, attributes.mTypefaceIndex, attributes.mTextStyle, attributes.mFontWeight);
        if (attributes.mShadowColor != 0) {
            setShadowLayer(attributes.mShadowRadius, attributes.mShadowDx, attributes.mShadowDy, attributes.mShadowColor);
        }
        if (attributes.mAllCaps) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        }
        if (attributes.mHasElegant) {
            setElegantTextHeight(attributes.mElegant);
        }
        if (attributes.mHasFallbackLineSpacing) {
            setFallbackLineSpacing(attributes.mFallbackLineSpacing);
        }
        if (attributes.mHasLetterSpacing) {
            setLetterSpacing(attributes.mLetterSpacing);
        }
        if (attributes.mFontFeatureSettings != null) {
            setFontFeatureSettings(attributes.mFontFeatureSettings);
        }
        if (attributes.mFontVariationSettings != null) {
            setFontVariationSettings(attributes.mFontVariationSettings);
        }
    }

    public Locale getTextLocale() {
        return this.mTextPaint.getTextLocale();
    }

    public LocaleList getTextLocales() {
        return this.mTextPaint.getTextLocales();
    }

    private void changeListenerLocaleTo(Locale locale) {
        KeyListener listener;
        if (!this.mListenerChanged && this.mEditor != null) {
            KeyListener listener2 = this.mEditor.mKeyListener;
            if (listener2 instanceof DigitsKeyListener) {
                listener = DigitsKeyListener.getInstance(locale, (DigitsKeyListener) listener2);
            } else if (listener2 instanceof DateKeyListener) {
                listener = DateKeyListener.getInstance(locale);
            } else if (listener2 instanceof TimeKeyListener) {
                listener = TimeKeyListener.getInstance(locale);
            } else if (listener2 instanceof DateTimeKeyListener) {
                listener = DateTimeKeyListener.getInstance(locale);
            } else {
                return;
            }
            boolean wasPasswordType = isPasswordInputType(this.mEditor.mInputType);
            setKeyListenerOnly(listener);
            setInputTypeFromEditor();
            if (wasPasswordType) {
                int newInputClass = this.mEditor.mInputType & 15;
                if (newInputClass == 1) {
                    this.mEditor.mInputType |= 128;
                } else if (newInputClass == 2) {
                    this.mEditor.mInputType |= 16;
                }
            }
        }
    }

    public void setTextLocale(Locale locale) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocale(locale);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public void setTextLocales(LocaleList locales) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocales(locales);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!this.mLocalesChanged) {
            this.mTextPaint.setTextLocales(LocaleList.getDefault());
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    @ViewDebug.ExportedProperty(category = "text")
    public float getTextSize() {
        return this.mTextPaint.getTextSize();
    }

    @ViewDebug.ExportedProperty(category = "text")
    public float getScaledTextSize() {
        return this.mTextPaint.getTextSize() / this.mTextPaint.density;
    }

    @ViewDebug.ExportedProperty(category = "text", mapping = {@ViewDebug.IntToString(from = 0, to = "NORMAL"), @ViewDebug.IntToString(from = 1, to = "BOLD"), @ViewDebug.IntToString(from = 2, to = "ITALIC"), @ViewDebug.IntToString(from = 3, to = "BOLD_ITALIC")})
    public int getTypefaceStyle() {
        Typeface typeface = this.mTextPaint.getTypeface();
        if (typeface != null) {
            return typeface.getStyle();
        }
        return 0;
    }

    @RemotableViewMethod
    public void setTextSize(float size) {
        setTextSize(2, size);
    }

    public void setTextSize(int unit, float size) {
        if (!isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size, true);
        }
    }

    private void setTextSizeInternal(int unit, float size, boolean shouldRequestLayout) {
        Resources r;
        Context c = getContext();
        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }
        setRawTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()), shouldRequestLayout);
    }

    @UnsupportedAppUsage
    private void setRawTextSize(float size, boolean shouldRequestLayout) {
        if (size != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(size);
            if (shouldRequestLayout && this.mLayout != null) {
                this.mNeedsAutoSizeText = false;
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public float getTextScaleX() {
        return this.mTextPaint.getTextScaleX();
    }

    @RemotableViewMethod
    public void setTextScaleX(float size) {
        if (size != this.mTextPaint.getTextScaleX()) {
            this.mUserSetTextScaleX = true;
            this.mTextPaint.setTextScaleX(size);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setTypeface(Typeface tf) {
        if (this.mTextPaint.getTypeface() != tf) {
            this.mTextPaint.setTypeface(tf);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public Typeface getTypeface() {
        return this.mTextPaint.getTypeface();
    }

    public void setElegantTextHeight(boolean elegant) {
        if (elegant != this.mTextPaint.isElegantTextHeight()) {
            this.mTextPaint.setElegantTextHeight(elegant);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setFallbackLineSpacing(boolean enabled) {
        if (this.mUseFallbackLineSpacing != enabled) {
            this.mUseFallbackLineSpacing = enabled;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean isFallbackLineSpacing() {
        return this.mUseFallbackLineSpacing;
    }

    public boolean isElegantTextHeight() {
        return this.mTextPaint.isElegantTextHeight();
    }

    public float getLetterSpacing() {
        return this.mTextPaint.getLetterSpacing();
    }

    @RemotableViewMethod
    public void setLetterSpacing(float letterSpacing) {
        if (letterSpacing != this.mTextPaint.getLetterSpacing()) {
            this.mTextPaint.setLetterSpacing(letterSpacing);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public String getFontFeatureSettings() {
        return this.mTextPaint.getFontFeatureSettings();
    }

    public String getFontVariationSettings() {
        return this.mTextPaint.getFontVariationSettings();
    }

    public void setBreakStrategy(int breakStrategy) {
        this.mBreakStrategy = breakStrategy;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public void setHyphenationFrequency(int hyphenationFrequency) {
        this.mHyphenationFrequency = hyphenationFrequency;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public PrecomputedText.Params getTextMetricsParams() {
        return new PrecomputedText.Params(new TextPaint((Paint) this.mTextPaint), getTextDirectionHeuristic(), this.mBreakStrategy, this.mHyphenationFrequency);
    }

    public void setTextMetricsParams(PrecomputedText.Params params) {
        this.mTextPaint.set(params.getTextPaint());
        this.mUserSetTextScaleX = true;
        this.mTextDir = params.getTextDirection();
        this.mBreakStrategy = params.getBreakStrategy();
        this.mHyphenationFrequency = params.getHyphenationFrequency();
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public void setJustificationMode(int justificationMode) {
        this.mJustificationMode = justificationMode;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getJustificationMode() {
        return this.mJustificationMode;
    }

    @RemotableViewMethod
    public void setFontFeatureSettings(String fontFeatureSettings) {
        if (fontFeatureSettings != this.mTextPaint.getFontFeatureSettings()) {
            this.mTextPaint.setFontFeatureSettings(fontFeatureSettings);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean setFontVariationSettings(String fontVariationSettings) {
        String existingSettings = this.mTextPaint.getFontVariationSettings();
        if (fontVariationSettings == existingSettings) {
            return true;
        }
        if (fontVariationSettings != null && fontVariationSettings.equals(existingSettings)) {
            return true;
        }
        boolean effective = this.mTextPaint.setFontVariationSettings(fontVariationSettings);
        if (effective && this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
        return effective;
    }

    @RemotableViewMethod
    public void setTextColor(int color) {
        this.mTextColor = ColorStateList.valueOf(color);
        updateTextColors();
    }

    @RemotableViewMethod
    public void setTextColor(ColorStateList colors) {
        if (colors != null) {
            this.mTextColor = colors;
            updateTextColors();
            return;
        }
        throw new NullPointerException();
    }

    public final ColorStateList getTextColors() {
        return this.mTextColor;
    }

    public final int getCurrentTextColor() {
        return this.mCurTextColor;
    }

    @RemotableViewMethod
    public void setHighlightColor(int color) {
        if (this.mHighlightColor != color) {
            this.mHighlightColor = color;
            invalidate();
        }
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    @RemotableViewMethod
    public final void setShowSoftInputOnFocus(boolean show) {
        createEditorIfNeeded();
        this.mEditor.mShowSoftInputOnFocus = show;
    }

    public final boolean getShowSoftInputOnFocus() {
        return this.mEditor == null || this.mEditor.mShowSoftInputOnFocus;
    }

    public void setShadowLayer(float radius, float dx, float dy, int color) {
        this.mTextPaint.setShadowLayer(radius, dx, dy, color);
        this.mShadowRadius = radius;
        this.mShadowDx = dx;
        this.mShadowDy = dy;
        this.mShadowColor = color;
        if (this.mEditor != null) {
            this.mEditor.invalidateTextDisplayList();
            this.mEditor.invalidateHandlesAndActionMode();
        }
        invalidate();
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public TextPaint getPaint() {
        return this.mTextPaint;
    }

    @RemotableViewMethod
    public final void setAutoLinkMask(int mask) {
        this.mAutoLinkMask = mask;
    }

    @RemotableViewMethod
    public final void setLinksClickable(boolean whether) {
        this.mLinksClickable = whether;
    }

    public final boolean getLinksClickable() {
        return this.mLinksClickable;
    }

    public URLSpan[] getUrls() {
        if (this.mText instanceof Spanned) {
            return (URLSpan[]) ((Spanned) this.mText).getSpans(0, this.mText.length(), URLSpan.class);
        }
        return new URLSpan[0];
    }

    @RemotableViewMethod
    public final void setHintTextColor(int color) {
        this.mHintTextColor = ColorStateList.valueOf(color);
        updateTextColors();
    }

    public final void setHintTextColor(ColorStateList colors) {
        this.mHintTextColor = colors;
        updateTextColors();
    }

    public final ColorStateList getHintTextColors() {
        return this.mHintTextColor;
    }

    public final int getCurrentHintTextColor() {
        return this.mHintTextColor != null ? this.mCurHintTextColor : this.mCurTextColor;
    }

    @RemotableViewMethod
    public final void setLinkTextColor(int color) {
        this.mLinkTextColor = ColorStateList.valueOf(color);
        updateTextColors();
    }

    public final void setLinkTextColor(ColorStateList colors) {
        this.mLinkTextColor = colors;
        updateTextColors();
    }

    public final ColorStateList getLinkTextColors() {
        return this.mLinkTextColor;
    }

    public void setGravity(int gravity) {
        if ((gravity & 8388615) == 0) {
            gravity |= 8388611;
        }
        if ((gravity & 112) == 0) {
            gravity |= 48;
        }
        boolean newLayout = false;
        if ((gravity & 8388615) != (8388615 & this.mGravity)) {
            newLayout = true;
        }
        if (gravity != this.mGravity) {
            invalidate();
        }
        this.mGravity = gravity;
        if (this.mLayout != null && newLayout) {
            makeNewLayout(this.mLayout.getWidth(), this.mHintLayout == null ? 0 : this.mHintLayout.getWidth(), UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), true);
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getPaintFlags() {
        return this.mTextPaint.getFlags();
    }

    @RemotableViewMethod
    public void setPaintFlags(int flags) {
        if (this.mTextPaint.getFlags() != flags) {
            this.mTextPaint.setFlags(flags);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setHorizontallyScrolling(boolean whether) {
        if (this.mHorizontallyScrolling != whether) {
            this.mHorizontallyScrolling = whether;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public final boolean isHorizontallyScrollable() {
        return this.mHorizontallyScrolling;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28)
    public boolean getHorizontallyScrolling() {
        return this.mHorizontallyScrolling;
    }

    @RemotableViewMethod
    public void setMinLines(int minLines) {
        this.mMinimum = minLines;
        this.mMinMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMinLines() {
        if (this.mMinMode == 1) {
            return this.mMinimum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMinHeight(int minPixels) {
        this.mMinimum = minPixels;
        this.mMinMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMinHeight() {
        if (this.mMinMode == 2) {
            return this.mMinimum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxLines(int maxLines) {
        this.mMaximum = maxLines;
        this.mMaxMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMaxLines() {
        if (this.mMaxMode == 1) {
            return this.mMaximum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxHeight(int maxPixels) {
        this.mMaximum = maxPixels;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMaxHeight() {
        if (this.mMaxMode == 2) {
            return this.mMaximum;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setLines(int lines) {
        this.mMinimum = lines;
        this.mMaximum = lines;
        this.mMinMode = 1;
        this.mMaxMode = 1;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setHeight(int pixels) {
        this.mMinimum = pixels;
        this.mMaximum = pixels;
        this.mMinMode = 2;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setMinEms(int minEms) {
        this.mMinWidth = minEms;
        this.mMinWidthMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMinEms() {
        if (this.mMinWidthMode == 1) {
            return this.mMinWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMinWidth(int minPixels) {
        this.mMinWidth = minPixels;
        this.mMinWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMinWidth() {
        if (this.mMinWidthMode == 2) {
            return this.mMinWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxEms(int maxEms) {
        this.mMaxWidth = maxEms;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMaxEms() {
        if (this.mMaxWidthMode == 1) {
            return this.mMaxWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setMaxWidth(int maxPixels) {
        this.mMaxWidth = maxPixels;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMaxWidth() {
        if (this.mMaxWidthMode == 2) {
            return this.mMaxWidth;
        }
        return -1;
    }

    @RemotableViewMethod
    public void setEms(int ems) {
        this.mMinWidth = ems;
        this.mMaxWidth = ems;
        this.mMinWidthMode = 1;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    @RemotableViewMethod
    public void setWidth(int pixels) {
        this.mMinWidth = pixels;
        this.mMaxWidth = pixels;
        this.mMinWidthMode = 2;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public void setLineSpacing(float add, float mult) {
        if (this.mSpacingAdd != add || this.mSpacingMult != mult) {
            this.mSpacingAdd = add;
            this.mSpacingMult = mult;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public float getLineSpacingExtra() {
        return this.mSpacingAdd;
    }

    public void setLineHeight(int lineHeight) {
        Preconditions.checkArgumentNonnegative(lineHeight);
        int fontHeight = getPaint().getFontMetricsInt((Paint.FontMetricsInt) null);
        if (lineHeight != fontHeight) {
            setLineSpacing((float) (lineHeight - fontHeight), 1.0f);
        }
    }

    public final void append(CharSequence text) {
        append(text, 0, text.length());
    }

    public void append(CharSequence text, int start, int end) {
        if (!(this.mText instanceof Editable)) {
            setText(this.mText, BufferType.EDITABLE);
        }
        ((Editable) this.mText).append(text, start, end);
        if (this.mAutoLinkMask != 0 && Linkify.addLinks(this.mSpannable, this.mAutoLinkMask) && this.mLinksClickable && !textCanBeSelected()) {
            setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void updateTextColors() {
        int color;
        int color2;
        boolean inval = false;
        int[] drawableState = getDrawableState();
        int color3 = this.mTextColor.getColorForState(drawableState, 0);
        if (color3 != this.mCurTextColor) {
            this.mCurTextColor = color3;
            inval = true;
        }
        if (!(this.mLinkTextColor == null || (color2 = this.mLinkTextColor.getColorForState(drawableState, 0)) == this.mTextPaint.linkColor)) {
            this.mTextPaint.linkColor = color2;
            inval = true;
        }
        if (!(this.mHintTextColor == null || (color = this.mHintTextColor.getColorForState(drawableState, 0)) == this.mCurHintTextColor)) {
            this.mCurHintTextColor = color;
            if (this.mText.length() == 0) {
                inval = true;
            }
        }
        if (inval) {
            if (this.mEditor != null) {
                this.mEditor.invalidateTextDisplayList();
            }
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if ((this.mTextColor != null && this.mTextColor.isStateful()) || ((this.mHintTextColor != null && this.mHintTextColor.isStateful()) || (this.mLinkTextColor != null && this.mLinkTextColor.isStateful()))) {
            updateTextColors();
        }
        if (this.mDrawables != null) {
            int[] state = getDrawableState();
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null && dr.isStateful() && dr.setState(state)) {
                    invalidateDrawable(dr);
                }
            }
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mDrawables != null) {
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null) {
                    dr.setHotspot(x, y);
                }
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        boolean freezesText = getFreezesText();
        boolean hasSelection = false;
        int start = -1;
        int end = -1;
        if (this.mText != null) {
            start = getSelectionStart();
            end = getSelectionEnd();
            if (start >= 0 || end >= 0) {
                hasSelection = true;
            }
        }
        if (!freezesText && !hasSelection) {
            return superState;
        }
        SavedState ss = new SavedState(superState);
        if (freezesText) {
            if (this.mText instanceof Spanned) {
                Spannable sp = new SpannableStringBuilder(this.mText);
                if (this.mEditor != null) {
                    removeMisspelledSpans(sp);
                    sp.removeSpan(this.mEditor.mSuggestionRangeSpan);
                }
                ss.text = sp;
            } else {
                ss.text = this.mText.toString();
            }
        }
        if (hasSelection) {
            ss.selStart = start;
            ss.selEnd = end;
        }
        if (isFocused() && start >= 0 && end >= 0) {
            ss.frozenWithFocus = true;
        }
        ss.error = getError();
        if (this.mEditor != null) {
            ss.editorState = this.mEditor.saveInstanceState();
        }
        return ss;
    }

    /* access modifiers changed from: package-private */
    public void removeMisspelledSpans(Spannable spannable) {
        int flags = 0;
        SuggestionSpan[] suggestionSpans = (SuggestionSpan[]) spannable.getSpans(0, spannable.length(), SuggestionSpan.class);
        while (true) {
            int i = flags;
            if (i < suggestionSpans.length) {
                int flags2 = suggestionSpans[i].getFlags();
                if (!((flags2 & 1) == 0 || (flags2 & 2) == 0)) {
                    spannable.removeSpan(suggestionSpans[i]);
                }
                flags = i + 1;
            } else {
                return;
            }
        }
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (ss.text != null) {
            setText(ss.text);
        }
        if (ss.selStart >= 0 && ss.selEnd >= 0 && this.mSpannable != null) {
            int len = this.mText.length();
            if (ss.selStart > len || ss.selEnd > len) {
                String restored = "";
                if (ss.text != null) {
                    restored = "(restored) ";
                }
                Log.e(LOG_TAG, "Saved cursor position " + ss.selStart + "/" + ss.selEnd + " out of range for " + restored + "text " + this.mText);
            } else {
                Selection.setSelection(this.mSpannable, ss.selStart, ss.selEnd);
                if (ss.frozenWithFocus) {
                    createEditorIfNeeded();
                    this.mEditor.mFrozenWithFocus = true;
                }
            }
        }
        if (ss.error != null) {
            final CharSequence error = ss.error;
            post(new Runnable() {
                public void run() {
                    if (TextView.this.mEditor == null || !TextView.this.mEditor.mErrorWasChanged) {
                        TextView.this.setError(error);
                    }
                }
            });
        }
        if (ss.editorState != null) {
            createEditorIfNeeded();
            this.mEditor.restoreInstanceState(ss.editorState);
        }
    }

    @RemotableViewMethod
    public void setFreezesText(boolean freezesText) {
        this.mFreezesText = freezesText;
    }

    public boolean getFreezesText() {
        return this.mFreezesText;
    }

    public final void setEditableFactory(Editable.Factory factory) {
        this.mEditableFactory = factory;
        setText(this.mText);
    }

    public final void setSpannableFactory(Spannable.Factory factory) {
        this.mSpannableFactory = factory;
        setText(this.mText);
    }

    @RemotableViewMethod
    public final void setText(CharSequence text) {
        setText(text, this.mBufferType);
    }

    @RemotableViewMethod
    public final void setTextKeepState(CharSequence text) {
        setTextKeepState(text, this.mBufferType);
    }

    public void setText(CharSequence text, BufferType type) {
        setText(text, type, true, 0);
        if (this.mCharWrapper != null) {
            char[] unused = this.mCharWrapper.mChars = null;
        }
    }

    @UnsupportedAppUsage
    private void setText(CharSequence text, BufferType type, boolean notifyBefore, int oldlen) {
        Spannable s2;
        this.mTextSetFromXmlOrResourceId = false;
        if (text == null) {
            text = "";
        }
        if (!isSuggestionsEnabled()) {
            text = removeSuggestionSpans(text);
        }
        if (!this.mUserSetTextScaleX) {
            this.mTextPaint.setTextScaleX(1.0f);
        }
        if ((text instanceof Spanned) && ((Spanned) text).getSpanStart(TextUtils.TruncateAt.MARQUEE) >= 0) {
            if (ViewConfiguration.get(this.mContext).isFadingMarqueeEnabled()) {
                setHorizontalFadingEdgeEnabled(true);
                this.mMarqueeFadeMode = 0;
            } else {
                setHorizontalFadingEdgeEnabled(false);
                this.mMarqueeFadeMode = 1;
            }
            setEllipsize(TextUtils.TruncateAt.MARQUEE);
        }
        CharSequence text2 = text;
        for (InputFilter filter : this.mFilters) {
            CharSequence out = filter.filter(text2, 0, text2.length(), EMPTY_SPANNED, 0, 0);
            if (out != null) {
                text2 = out;
            }
        }
        if (notifyBefore) {
            if (this.mText != null) {
                oldlen = this.mText.length();
                sendBeforeTextChanged(this.mText, 0, oldlen, text2.length());
            } else {
                sendBeforeTextChanged("", 0, 0, text2.length());
            }
        }
        boolean needEditableForNotification = false;
        if (!(this.mListeners == null || this.mListeners.size() == 0)) {
            needEditableForNotification = true;
        }
        PrecomputedText precomputed = text2 instanceof PrecomputedText ? (PrecomputedText) text2 : null;
        if (type == BufferType.EDITABLE || getKeyListener() != null || needEditableForNotification) {
            createEditorIfNeeded();
            this.mEditor.forgetUndoRedo();
            Editable t = this.mEditableFactory.newEditable(text2);
            text2 = t;
            setFilters(t, this.mFilters);
            InputMethodManager imm = getInputMethodManager();
            if (imm != null) {
                imm.restartInput(this);
            }
        } else if (precomputed != null) {
            if (this.mTextDir == null) {
                this.mTextDir = getTextDirectionHeuristic();
            }
            switch (precomputed.getParams().checkResultUsable(getPaint(), this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency)) {
                case 0:
                    throw new IllegalArgumentException("PrecomputedText's Parameters don't match the parameters of this TextView.Consider using setTextMetricsParams(precomputedText.getParams()) to override the settings of this TextView: PrecomputedText: " + precomputed.getParams() + "TextView: " + getTextMetricsParams());
                case 1:
                    PrecomputedText precomputed2 = PrecomputedText.create(precomputed, getTextMetricsParams());
                    break;
            }
        } else if (type == BufferType.SPANNABLE || this.mMovement != null) {
            text2 = this.mSpannableFactory.newSpannable(text2);
        } else if (!(text2 instanceof CharWrapper)) {
            text2 = TextUtils.stringOrSpannedString(text2);
        }
        if (this.mAutoLinkMask != 0) {
            if (type == BufferType.EDITABLE || (text2 instanceof Spannable)) {
                s2 = (Spannable) text2;
            } else {
                s2 = this.mSpannableFactory.newSpannable(text2);
            }
            if (Linkify.addLinks(s2, this.mAutoLinkMask)) {
                text2 = s2;
                type = type == BufferType.EDITABLE ? BufferType.EDITABLE : BufferType.SPANNABLE;
                setTextInternal(text2);
                if (this.mLinksClickable && !textCanBeSelected()) {
                    setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
        }
        this.mBufferType = type;
        setTextInternal(text2);
        if (this.mTransformation == null) {
            this.mTransformed = text2;
        } else {
            this.mTransformed = this.mTransformation.getTransformation(text2, this);
        }
        if (this.mTransformed == null) {
            this.mTransformed = "";
        }
        int textLength = text2.length();
        if ((text2 instanceof Spannable) && !this.mAllowTransformationLengthChange) {
            Spannable sp = (Spannable) text2;
            for (ChangeWatcher removeSpan : (ChangeWatcher[]) sp.getSpans(0, sp.length(), ChangeWatcher.class)) {
                sp.removeSpan(removeSpan);
            }
            if (this.mChangeWatcher == null) {
                this.mChangeWatcher = new ChangeWatcher();
            }
            sp.setSpan(this.mChangeWatcher, 0, textLength, 6553618);
            if (this.mEditor != null) {
                this.mEditor.addSpanWatchers(sp);
            }
            if (this.mTransformation != null) {
                sp.setSpan(this.mTransformation, 0, textLength, 18);
            }
            if (this.mMovement != null) {
                this.mMovement.initialize(this, (Spannable) text2);
                if (this.mEditor != null) {
                    this.mEditor.mSelectionMoved = false;
                }
            }
        }
        if (this.mLayout != null) {
            checkForRelayout();
        }
        sendOnTextChanged(text2, 0, oldlen, textLength);
        onTextChanged(text2, 0, oldlen, textLength);
        notifyViewAccessibilityStateChangedIfNeeded(2);
        if (needEditableForNotification) {
            sendAfterTextChanged((Editable) text2);
        } else {
            notifyListeningManagersAfterTextChanged();
        }
        if (this.mEditor != null) {
            this.mEditor.prepareCursorControllers();
        }
    }

    public final void setText(char[] text, int start, int len) {
        int oldlen = 0;
        if (start < 0 || len < 0 || start + len > text.length) {
            throw new IndexOutOfBoundsException(start + ", " + len);
        }
        if (this.mText != null) {
            oldlen = this.mText.length();
            sendBeforeTextChanged(this.mText, 0, oldlen, len);
        } else {
            sendBeforeTextChanged("", 0, 0, len);
        }
        if (this.mCharWrapper == null) {
            this.mCharWrapper = new CharWrapper(text, start, len);
        } else {
            this.mCharWrapper.set(text, start, len);
        }
        setText(this.mCharWrapper, this.mBufferType, false, oldlen);
    }

    public final void setTextKeepState(CharSequence text, BufferType type) {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int len = text.length();
        setText(text, type);
        if ((start >= 0 || end >= 0) && this.mSpannable != null) {
            Selection.setSelection(this.mSpannable, Math.max(0, Math.min(start, len)), Math.max(0, Math.min(end, len)));
        }
    }

    @RemotableViewMethod
    public final void setText(int resid) {
        setText(getContext().getResources().getText(resid));
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = resid;
    }

    public final void setText(int resid, BufferType type) {
        setText(getContext().getResources().getText(resid), type);
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = resid;
    }

    @RemotableViewMethod
    public final void setHint(CharSequence hint) {
        setHintInternal(hint);
        if (this.mEditor != null && isInputMethodTarget()) {
            this.mEditor.reportExtractedText();
        }
    }

    private void setHintInternal(CharSequence hint) {
        this.mHint = TextUtils.stringOrSpannedString(hint);
        if (this.mLayout != null) {
            checkForRelayout();
        }
        if (this.mText.length() == 0) {
            invalidate();
        }
        if (this.mEditor != null && this.mText.length() == 0 && this.mHint != null) {
            this.mEditor.invalidateTextDisplayList();
        }
    }

    @RemotableViewMethod
    public final void setHint(int resid) {
        setHint(getContext().getResources().getText(resid));
    }

    @ViewDebug.CapturedViewProperty
    public CharSequence getHint() {
        return this.mHint;
    }

    public boolean isSingleLine() {
        return this.mSingleLine;
    }

    private static boolean isMultilineInputType(int type) {
        return (131087 & type) == 131073;
    }

    /* access modifiers changed from: package-private */
    public CharSequence removeSuggestionSpans(CharSequence text) {
        Spannable spannable;
        if (text instanceof Spanned) {
            if (text instanceof Spannable) {
                spannable = (Spannable) text;
            } else {
                spannable = this.mSpannableFactory.newSpannable(text);
            }
            int i = 0;
            SuggestionSpan[] spans = (SuggestionSpan[]) spannable.getSpans(0, text.length(), SuggestionSpan.class);
            if (spans.length != 0) {
                text = spannable;
                while (true) {
                    int i2 = i;
                    if (i2 >= spans.length) {
                        break;
                    }
                    spannable.removeSpan(spans[i2]);
                    i = i2 + 1;
                }
            } else {
                return text;
            }
        }
        return text;
    }

    public void setInputType(int type) {
        boolean wasPassword = isPasswordInputType(getInputType());
        boolean wasVisiblePassword = isVisiblePasswordInputType(getInputType());
        setInputType(type, false);
        boolean isPassword = isPasswordInputType(type);
        boolean isVisiblePassword = isVisiblePasswordInputType(type);
        boolean forceUpdate = false;
        if (isPassword) {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
            setTypefaceFromAttrs((Typeface) null, (String) null, 3, 0, -1);
        } else if (isVisiblePassword) {
            if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                forceUpdate = true;
            }
            setTypefaceFromAttrs((Typeface) null, (String) null, 3, 0, -1);
        } else if (wasPassword || wasVisiblePassword) {
            setTypefaceFromAttrs((Typeface) null, (String) null, -1, 0, -1);
            if (this.mTransformation == PasswordTransformationMethod.getInstance()) {
                forceUpdate = true;
            }
        }
        boolean singleLine = !isMultilineInputType(type);
        if (this.mSingleLine != singleLine || forceUpdate) {
            applySingleLine(singleLine, !isPassword, true);
        }
        if (!isSuggestionsEnabled()) {
            setTextInternal(removeSuggestionSpans(this.mText));
        }
        InputMethodManager imm = getInputMethodManager();
        if (imm != null) {
            imm.restartInput(this);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasPasswordTransformationMethod() {
        return this.mTransformation instanceof PasswordTransformationMethod;
    }

    static boolean isPasswordInputType(int inputType) {
        int variation = inputType & 4095;
        return variation == 129 || variation == 225 || variation == 18;
    }

    private static boolean isVisiblePasswordInputType(int inputType) {
        return (inputType & 4095) == 145;
    }

    public void setRawInputType(int type) {
        if (type != 0 || this.mEditor != null) {
            createEditorIfNeeded();
            this.mEditor.mInputType = type;
        }
    }

    private Locale getCustomLocaleForKeyListenerOrNull() {
        LocaleList locales;
        if (this.mUseInternationalizedInput && (locales = getImeHintLocales()) != null) {
            return locales.get(0);
        }
        return null;
    }

    @UnsupportedAppUsage
    private void setInputType(int type, boolean direct) {
        KeyListener input;
        KeyListener input2;
        TextKeyListener.Capitalize cap;
        int cls = type & 15;
        boolean autotext = true;
        if (cls == 1) {
            if ((32768 & type) == 0) {
                autotext = false;
            }
            if ((type & 4096) != 0) {
                cap = TextKeyListener.Capitalize.CHARACTERS;
            } else if ((type & 8192) != 0) {
                cap = TextKeyListener.Capitalize.WORDS;
            } else if ((type & 16384) != 0) {
                cap = TextKeyListener.Capitalize.SENTENCES;
            } else {
                cap = TextKeyListener.Capitalize.NONE;
            }
            input = TextKeyListener.getInstance(autotext, cap);
        } else if (cls == 2) {
            Locale locale = getCustomLocaleForKeyListenerOrNull();
            boolean z = (type & 4096) != 0;
            if ((type & 8192) == 0) {
                autotext = false;
            }
            input = DigitsKeyListener.getInstance(locale, z, autotext);
            if (locale != null) {
                int newType = input.getInputType();
                if ((newType & 15) != 2) {
                    if ((type & 16) != 0) {
                        newType |= 128;
                    }
                    type = newType;
                }
            }
        } else if (cls == 4) {
            Locale locale2 = getCustomLocaleForKeyListenerOrNull();
            int i = type & InputType.TYPE_MASK_VARIATION;
            if (i == 16) {
                input2 = DateKeyListener.getInstance(locale2);
            } else if (i != 32) {
                input2 = DateTimeKeyListener.getInstance(locale2);
            } else {
                input2 = TimeKeyListener.getInstance(locale2);
            }
            if (this.mUseInternationalizedInput) {
                type = input2.getInputType();
            }
            input = input2;
        } else {
            input = cls == 3 ? DialerKeyListener.getInstance() : TextKeyListener.getInstance();
        }
        setRawInputType(type);
        this.mListenerChanged = false;
        if (direct) {
            createEditorIfNeeded();
            this.mEditor.mKeyListener = input;
            return;
        }
        setKeyListenerOnly(input);
    }

    public int getInputType() {
        if (this.mEditor == null) {
            return 0;
        }
        return this.mEditor.mInputType;
    }

    public void setImeOptions(int imeOptions) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeOptions = imeOptions;
    }

    public int getImeOptions() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeOptions;
    }

    public void setImeActionLabel(CharSequence label, int actionId) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeActionLabel = label;
        this.mEditor.mInputContentType.imeActionId = actionId;
    }

    public CharSequence getImeActionLabel() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeActionLabel;
    }

    public int getImeActionId() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeActionId;
    }

    public void setOnEditorActionListener(OnEditorActionListener l) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.onEditorActionListener = l;
    }

    public void onEditorAction(int actionCode) {
        int i = actionCode;
        Editor.InputContentType ict = this.mEditor == null ? null : this.mEditor.mInputContentType;
        if (ict != null) {
            if (ict.onEditorActionListener != null && ict.onEditorActionListener.onEditorAction(this, i, (KeyEvent) null)) {
                return;
            }
            if (i == 5) {
                View v = focusSearch(2);
                if (v != null && !v.requestFocus(2)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            } else if (i == 7) {
                View v2 = focusSearch(1);
                if (v2 != null && !v2.requestFocus(1)) {
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            } else if (i == 6) {
                InputMethodManager imm = getInputMethodManager();
                if (imm != null && imm.isActive(this)) {
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    return;
                }
                return;
            }
        }
        ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            long eventTime = SystemClock.uptimeMillis();
            long j = eventTime;
            KeyEvent keyEvent = r4;
            KeyEvent keyEvent2 = new KeyEvent(eventTime, j, 0, 66, 0, 0, -1, 0, 22);
            viewRootImpl.dispatchKeyFromIme(keyEvent);
            viewRootImpl.dispatchKeyFromIme(new KeyEvent(SystemClock.uptimeMillis(), j, 1, 66, 0, 0, -1, 0, 22));
        }
    }

    public void setPrivateImeOptions(String type) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.privateImeOptions = type;
    }

    public String getPrivateImeOptions() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.privateImeOptions;
    }

    public void setInputExtras(int xmlResId) throws XmlPullParserException, IOException {
        createEditorIfNeeded();
        XmlResourceParser parser = getResources().getXml(xmlResId);
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.extras = new Bundle();
        getResources().parseBundleExtras(parser, this.mEditor.mInputContentType.extras);
    }

    public Bundle getInputExtras(boolean create) {
        if (this.mEditor == null && !create) {
            return null;
        }
        createEditorIfNeeded();
        if (this.mEditor.mInputContentType == null) {
            if (!create) {
                return null;
            }
            this.mEditor.createInputContentTypeIfNeeded();
        }
        if (this.mEditor.mInputContentType.extras == null) {
            if (!create) {
                return null;
            }
            this.mEditor.mInputContentType.extras = new Bundle();
        }
        return this.mEditor.mInputContentType.extras;
    }

    public void setImeHintLocales(LocaleList hintLocales) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeHintLocales = hintLocales;
        if (this.mUseInternationalizedInput) {
            changeListenerLocaleTo(hintLocales == null ? null : hintLocales.get(0));
        }
    }

    public LocaleList getImeHintLocales() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeHintLocales;
    }

    public CharSequence getError() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mError;
    }

    @RemotableViewMethod
    public void setError(CharSequence error) {
        if (error == null) {
            setError((CharSequence) null, (Drawable) null);
            return;
        }
        Drawable dr = getContext().getDrawable(com.android.internal.R.drawable.indicator_input_error);
        dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
        setError(error, dr);
    }

    public void setError(CharSequence error, Drawable icon) {
        createEditorIfNeeded();
        this.mEditor.setError(error, icon);
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    /* access modifiers changed from: protected */
    public boolean setFrame(int l, int t, int r, int b) {
        boolean result = super.setFrame(l, t, r, b);
        if (this.mEditor != null) {
            this.mEditor.setFrame();
        }
        restartMarqueeIfNeeded();
        return result;
    }

    private void restartMarqueeIfNeeded() {
        if (this.mRestartMarquee && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            this.mRestartMarquee = false;
            startMarquee();
        }
    }

    public void setFilters(InputFilter[] filters) {
        if (filters != null) {
            this.mFilters = filters;
            if (this.mText instanceof Editable) {
                setFilters((Editable) this.mText, filters);
                return;
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    private void setFilters(Editable e, InputFilter[] filters) {
        if (this.mEditor != null) {
            boolean undoFilter = this.mEditor.mUndoInputFilter != null;
            boolean keyFilter = this.mEditor.mKeyListener instanceof InputFilter;
            int num = 0;
            if (undoFilter) {
                num = 0 + 1;
            }
            if (keyFilter) {
                num++;
            }
            if (num > 0) {
                InputFilter[] nf = new InputFilter[(filters.length + num)];
                System.arraycopy(filters, 0, nf, 0, filters.length);
                int num2 = 0;
                if (undoFilter) {
                    nf[filters.length] = this.mEditor.mUndoInputFilter;
                    num2 = 0 + 1;
                }
                if (keyFilter) {
                    nf[filters.length + num2] = (InputFilter) this.mEditor.mKeyListener;
                }
                e.setFilters(nf);
                return;
            }
        }
        e.setFilters(filters);
    }

    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    private int getBoxHeight(Layout l) {
        int padding;
        Insets opticalInsets = isLayoutModeOptical(this.mParent) ? getOpticalInsets() : Insets.NONE;
        if (l == this.mHintLayout) {
            padding = getCompoundPaddingTop() + getCompoundPaddingBottom();
        } else {
            padding = getExtendedPaddingTop() + getExtendedPaddingBottom();
        }
        return (getMeasuredHeight() - padding) + opticalInsets.top + opticalInsets.bottom;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        r3 = getBoxHeight(r2);
     */
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getVerticalOffset(boolean r7) {
        /*
            r6 = this;
            r0 = 0
            int r1 = r6.mGravity
            r1 = r1 & 112(0x70, float:1.57E-43)
            android.text.Layout r2 = r6.mLayout
            if (r7 != 0) goto L_0x0017
            java.lang.CharSequence r3 = r6.mText
            int r3 = r3.length()
            if (r3 != 0) goto L_0x0017
            android.text.Layout r3 = r6.mHintLayout
            if (r3 == 0) goto L_0x0017
            android.text.Layout r2 = r6.mHintLayout
        L_0x0017:
            r3 = 48
            if (r1 == r3) goto L_0x0030
            int r3 = r6.getBoxHeight(r2)
            int r4 = r2.getHeight()
            if (r4 >= r3) goto L_0x0030
            r5 = 80
            if (r1 != r5) goto L_0x002c
            int r0 = r3 - r4
            goto L_0x0030
        L_0x002c:
            int r5 = r3 - r4
            int r0 = r5 >> 1
        L_0x0030:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.getVerticalOffset(boolean):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001b, code lost:
        r3 = getBoxHeight(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getBottomVerticalOffset(boolean r7) {
        /*
            r6 = this;
            r0 = 0
            int r1 = r6.mGravity
            r1 = r1 & 112(0x70, float:1.57E-43)
            android.text.Layout r2 = r6.mLayout
            if (r7 != 0) goto L_0x0017
            java.lang.CharSequence r3 = r6.mText
            int r3 = r3.length()
            if (r3 != 0) goto L_0x0017
            android.text.Layout r3 = r6.mHintLayout
            if (r3 == 0) goto L_0x0017
            android.text.Layout r2 = r6.mHintLayout
        L_0x0017:
            r3 = 80
            if (r1 == r3) goto L_0x0030
            int r3 = r6.getBoxHeight(r2)
            int r4 = r2.getHeight()
            if (r4 >= r3) goto L_0x0030
            r5 = 48
            if (r1 != r5) goto L_0x002c
            int r0 = r3 - r4
            goto L_0x0030
        L_0x002c:
            int r5 = r3 - r4
            int r0 = r5 >> 1
        L_0x0030:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.getBottomVerticalOffset(boolean):int");
    }

    /* access modifiers changed from: package-private */
    public void invalidateCursorPath() {
        if (this.mHighlightPathBogus) {
            invalidateCursor();
            return;
        }
        int horizontalPadding = getCompoundPaddingLeft();
        int verticalPadding = getExtendedPaddingTop() + getVerticalOffset(true);
        if (this.mEditor.mDrawableForCursor == null) {
            synchronized (TEMP_RECTF) {
                float thick = (float) Math.ceil((double) this.mTextPaint.getStrokeWidth());
                if (thick < 1.0f) {
                    thick = 1.0f;
                }
                float thick2 = thick / 2.0f;
                this.mHighlightPath.computeBounds(TEMP_RECTF, false);
                invalidate((int) Math.floor((double) ((((float) horizontalPadding) + TEMP_RECTF.left) - thick2)), (int) Math.floor((double) ((((float) verticalPadding) + TEMP_RECTF.top) - thick2)), (int) Math.ceil((double) (((float) horizontalPadding) + TEMP_RECTF.right + thick2)), (int) Math.ceil((double) (((float) verticalPadding) + TEMP_RECTF.bottom + thick2)));
            }
            return;
        }
        Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
        invalidate(bounds.left + horizontalPadding, bounds.top + verticalPadding, bounds.right + horizontalPadding, bounds.bottom + verticalPadding);
    }

    /* access modifiers changed from: package-private */
    public void invalidateCursor() {
        int where = getSelectionEnd();
        invalidateCursor(where, where, where);
    }

    private void invalidateCursor(int a, int b, int c) {
        if (a >= 0 || b >= 0 || c >= 0) {
            invalidateRegion(Math.min(Math.min(a, b), c), Math.max(Math.max(a, b), c), true);
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateRegion(int start, int end, boolean invalidateCursor) {
        int lineEnd;
        int right;
        int left;
        if (this.mLayout == null) {
            invalidate();
            return;
        }
        int lineStart = this.mLayout.getLineForOffset(start);
        int top = this.mLayout.getLineTop(lineStart);
        if (lineStart > 0) {
            top -= this.mLayout.getLineDescent(lineStart - 1);
        }
        if (start == end) {
            lineEnd = lineStart;
        } else {
            lineEnd = this.mLayout.getLineForOffset(end);
        }
        int bottom = this.mLayout.getLineBottom(lineEnd);
        if (!(!invalidateCursor || this.mEditor == null || this.mEditor.mDrawableForCursor == null)) {
            Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
            top = Math.min(top, bounds.top);
            bottom = Math.max(bottom, bounds.bottom);
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int verticalPadding = getExtendedPaddingTop() + getVerticalOffset(true);
        if (lineStart != lineEnd || invalidateCursor) {
            left = compoundPaddingLeft;
            right = getWidth() - getCompoundPaddingRight();
        } else {
            left = ((int) this.mLayout.getPrimaryHorizontal(start)) + compoundPaddingLeft;
            right = ((int) (((double) this.mLayout.getPrimaryHorizontal(end)) + 1.0d)) + compoundPaddingLeft;
        }
        invalidate(this.mScrollX + left, verticalPadding + top, this.mScrollX + right, verticalPadding + bottom);
    }

    private void registerForPreDraw() {
        if (!this.mPreDrawRegistered) {
            getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawRegistered = true;
        }
    }

    private void unregisterForPreDraw() {
        getViewTreeObserver().removeOnPreDrawListener(this);
        this.mPreDrawRegistered = false;
        this.mPreDrawListenerDetached = false;
    }

    public boolean onPreDraw() {
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mMovement != null) {
            int curs = getSelectionEnd();
            if (!(this.mEditor == null || this.mEditor.mSelectionModifierCursorController == null || !this.mEditor.mSelectionModifierCursorController.isSelectionStartDragged())) {
                curs = getSelectionStart();
            }
            if (curs < 0 && (this.mGravity & 112) == 80) {
                curs = this.mText.length();
            }
            if (curs >= 0) {
                bringPointIntoView(curs);
            }
        } else {
            bringTextIntoView();
        }
        if (this.mEditor != null && this.mEditor.mCreatedWithASelection) {
            this.mEditor.refreshTextActionMode();
            this.mEditor.mCreatedWithASelection = false;
        }
        unregisterForPreDraw();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mEditor != null) {
            this.mEditor.onAttachedToWindow();
        }
        if (this.mPreDrawListenerDetached) {
            getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawListenerDetached = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindowInternal() {
        if (this.mPreDrawRegistered) {
            getViewTreeObserver().removeOnPreDrawListener(this);
            this.mPreDrawListenerDetached = true;
        }
        resetResolvedDrawables();
        if (this.mEditor != null) {
            this.mEditor.onDetachedFromWindow();
        }
        super.onDetachedFromWindowInternal();
    }

    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if (this.mEditor != null) {
            this.mEditor.onScreenStateChanged(screenState);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isPaddingOffsetRequired() {
        return (this.mShadowRadius == 0.0f && this.mDrawables == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    public int getLeftPaddingOffset() {
        return (getCompoundPaddingLeft() - this.mPaddingLeft) + ((int) Math.min(0.0f, this.mShadowDx - this.mShadowRadius));
    }

    /* access modifiers changed from: protected */
    public int getTopPaddingOffset() {
        return (int) Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
    }

    /* access modifiers changed from: protected */
    public int getBottomPaddingOffset() {
        return (int) Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
    }

    /* access modifiers changed from: protected */
    public int getRightPaddingOffset() {
        return (-(getCompoundPaddingRight() - this.mPaddingRight)) + ((int) Math.max(0.0f, this.mShadowDx + this.mShadowRadius));
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        boolean verified = super.verifyDrawable(who);
        if (!verified && this.mDrawables != null) {
            for (Drawable dr : this.mDrawables.mShowing) {
                if (who == dr) {
                    return true;
                }
            }
        }
        return verified;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mDrawables != null) {
            for (Drawable dr : this.mDrawables.mShowing) {
                if (dr != null) {
                    dr.jumpToCurrentState();
                }
            }
        }
    }

    public void invalidateDrawable(Drawable drawable) {
        boolean handled = false;
        if (verifyDrawable(drawable)) {
            Rect dirty = drawable.getBounds();
            int scrollX = this.mScrollX;
            int scrollY = this.mScrollY;
            Drawables drawables = this.mDrawables;
            if (drawables != null) {
                if (drawable == drawables.mShowing[0]) {
                    int compoundPaddingTop = getCompoundPaddingTop();
                    int compoundPaddingBottom = getCompoundPaddingBottom();
                    scrollX += this.mPaddingLeft;
                    scrollY += (((((this.mBottom - this.mTop) - compoundPaddingBottom) - compoundPaddingTop) - drawables.mDrawableHeightLeft) / 2) + compoundPaddingTop;
                    handled = true;
                } else if (drawable == drawables.mShowing[2]) {
                    int compoundPaddingTop2 = getCompoundPaddingTop();
                    int compoundPaddingBottom2 = getCompoundPaddingBottom();
                    scrollX += ((this.mRight - this.mLeft) - this.mPaddingRight) - drawables.mDrawableSizeRight;
                    scrollY += (((((this.mBottom - this.mTop) - compoundPaddingBottom2) - compoundPaddingTop2) - drawables.mDrawableHeightRight) / 2) + compoundPaddingTop2;
                    handled = true;
                } else if (drawable == drawables.mShowing[1]) {
                    int compoundPaddingLeft = getCompoundPaddingLeft();
                    scrollX += (((((this.mRight - this.mLeft) - getCompoundPaddingRight()) - compoundPaddingLeft) - drawables.mDrawableWidthTop) / 2) + compoundPaddingLeft;
                    scrollY += this.mPaddingTop;
                    handled = true;
                } else if (drawable == drawables.mShowing[3]) {
                    int compoundPaddingLeft2 = getCompoundPaddingLeft();
                    scrollX += (((((this.mRight - this.mLeft) - getCompoundPaddingRight()) - compoundPaddingLeft2) - drawables.mDrawableWidthBottom) / 2) + compoundPaddingLeft2;
                    scrollY += ((this.mBottom - this.mTop) - this.mPaddingBottom) - drawables.mDrawableSizeBottom;
                    handled = true;
                }
            }
            if (handled) {
                invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
            }
        }
        if (!handled) {
            super.invalidateDrawable(drawable);
        }
    }

    public boolean hasOverlappingRendering() {
        return ((getBackground() == null || getBackground().getCurrent() == null) && this.mSpannable == null && !hasSelection() && !isHorizontalFadingEdgeEnabled() && this.mShadowColor == 0) ? false : true;
    }

    public boolean isTextSelectable() {
        if (this.mEditor == null) {
            return false;
        }
        return this.mEditor.mTextIsSelectable;
    }

    public void setTextIsSelectable(boolean selectable) {
        if (selectable || this.mEditor != null) {
            createEditorIfNeeded();
            if (this.mEditor.mTextIsSelectable != selectable) {
                this.mEditor.mTextIsSelectable = selectable;
                setFocusableInTouchMode(selectable);
                setFocusable(16);
                setClickable(selectable);
                setLongClickable(selectable);
                setMovementMethod(selectable ? ArrowKeyMovementMethod.getInstance() : null);
                setText(this.mText, selectable ? BufferType.SPANNABLE : BufferType.NORMAL);
                this.mEditor.prepareCursorControllers();
            }
        }
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState;
        if (this.mSingleLine) {
            drawableState = super.onCreateDrawableState(extraSpace);
        } else {
            drawableState = super.onCreateDrawableState(extraSpace + 1);
            mergeDrawableStates(drawableState, MULTILINE_STATE_SET);
        }
        if (isTextSelectable()) {
            int length = drawableState.length;
            for (int i = 0; i < length; i++) {
                if (drawableState[i] == 16842919) {
                    int[] nonPressedState = new int[(length - 1)];
                    System.arraycopy(drawableState, 0, nonPressedState, 0, i);
                    System.arraycopy(drawableState, i + 1, nonPressedState, i, (length - i) - 1);
                    return nonPressedState;
                }
            }
        }
        return drawableState;
    }

    @UnsupportedAppUsage
    private Path getUpdatedHighlightPath() {
        Paint highlightPaint = this.mHighlightPaint;
        int selStart = getSelectionStart();
        int selEnd = getSelectionEnd();
        if (this.mMovement == null) {
            return null;
        }
        if ((!isFocused() && !isPressed()) || selStart < 0) {
            return null;
        }
        if (selStart != selEnd) {
            if (this.mHighlightPathBogus) {
                if (this.mHighlightPath == null) {
                    this.mHighlightPath = new Path();
                }
                this.mHighlightPath.reset();
                this.mLayout.getSelectionPath(selStart, selEnd, this.mHighlightPath);
                this.mHighlightPathBogus = false;
            }
            highlightPaint.setColor(this.mHighlightColor);
            highlightPaint.setStyle(Paint.Style.FILL);
            return this.mHighlightPath;
        } else if (this.mEditor == null || !this.mEditor.shouldRenderCursor()) {
            return null;
        } else {
            if (this.mHighlightPathBogus) {
                if (this.mHighlightPath == null) {
                    this.mHighlightPath = new Path();
                }
                this.mHighlightPath.reset();
                this.mLayout.getCursorPath(selStart, this.mHighlightPath, this.mText);
                this.mEditor.updateCursorPosition();
                this.mHighlightPathBogus = false;
            }
            highlightPaint.setColor(this.mCurTextColor);
            highlightPaint.setStyle(Paint.Style.STROKE);
            return this.mHighlightPath;
        }
    }

    public int getHorizontalOffsetForDrawables() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float clipTop;
        float clipTop2;
        float clipRight;
        float clipBottom;
        int voffsetCursor;
        float clipBottom2;
        int layoutDirection;
        int layoutDirection2;
        int compoundPaddingLeft;
        Layout layout;
        int cursorOffsetVertical;
        Canvas canvas2 = canvas;
        restartMarqueeIfNeeded();
        super.onDraw(canvas);
        int compoundPaddingLeft2 = getCompoundPaddingLeft();
        int compoundPaddingTop = getCompoundPaddingTop();
        int compoundPaddingRight = getCompoundPaddingRight();
        int compoundPaddingBottom = getCompoundPaddingBottom();
        int scrollX = this.mScrollX;
        int scrollY = this.mScrollY;
        int right = this.mRight;
        int left = this.mLeft;
        int bottom = this.mBottom;
        int top = this.mTop;
        boolean isLayoutRtl = isLayoutRtl();
        int offset = getHorizontalOffsetForDrawables();
        int leftOffset = isLayoutRtl ? 0 : offset;
        int rightOffset = isLayoutRtl ? offset : 0;
        Drawables dr = this.mDrawables;
        if (dr != null) {
            int vspace = ((bottom - top) - compoundPaddingBottom) - compoundPaddingTop;
            int hspace = ((right - left) - compoundPaddingRight) - compoundPaddingLeft2;
            if (dr.mShowing[0] != null) {
                canvas.save();
                canvas2.translate((float) (this.mPaddingLeft + scrollX + leftOffset), (float) (scrollY + compoundPaddingTop + ((vspace - dr.mDrawableHeightLeft) / 2)));
                dr.mShowing[0].draw(canvas2);
                canvas.restore();
            }
            if (dr.mShowing[2] != null) {
                canvas.save();
                canvas2.translate((float) (((((scrollX + right) - left) - this.mPaddingRight) - dr.mDrawableSizeRight) - rightOffset), (float) (scrollY + compoundPaddingTop + ((vspace - dr.mDrawableHeightRight) / 2)));
                dr.mShowing[2].draw(canvas2);
                canvas.restore();
            }
            if (dr.mShowing[1] != null) {
                canvas.save();
                canvas2.translate((float) (scrollX + compoundPaddingLeft2 + ((hspace - dr.mDrawableWidthTop) / 2)), (float) (this.mPaddingTop + scrollY));
                dr.mShowing[1].draw(canvas2);
                canvas.restore();
            }
            if (dr.mShowing[3] != null) {
                canvas.save();
                canvas2.translate((float) (scrollX + compoundPaddingLeft2 + ((hspace - dr.mDrawableWidthBottom) / 2)), (float) ((((scrollY + bottom) - top) - this.mPaddingBottom) - dr.mDrawableSizeBottom));
                dr.mShowing[3].draw(canvas2);
                canvas.restore();
            }
        }
        int color = this.mCurTextColor;
        if (this.mLayout == null) {
            assumeLayout();
        }
        Layout layout2 = this.mLayout;
        if (this.mHint != null && this.mText.length() == 0) {
            if (this.mHintTextColor != null) {
                color = this.mCurHintTextColor;
            }
            layout2 = this.mHintLayout;
        }
        int color2 = color;
        this.mTextPaint.setColor(color2);
        Drawables dr2 = dr;
        this.mTextPaint.drawableState = getDrawableState();
        canvas.save();
        int extendedPaddingTop = getExtendedPaddingTop();
        int extendedPaddingBottom = getExtendedPaddingBottom();
        int maxScrollY = this.mLayout.getHeight() - (((this.mBottom - this.mTop) - compoundPaddingBottom) - compoundPaddingTop);
        float clipLeft = (float) (compoundPaddingLeft2 + scrollX);
        int i = compoundPaddingTop;
        float clipTop3 = scrollY == 0 ? 0.0f : (float) (extendedPaddingTop + scrollY);
        int color3 = color2;
        float clipRight2 = (float) (((right - left) - getCompoundPaddingRight()) + scrollX);
        int maxScrollY2 = maxScrollY;
        float clipBottom3 = (float) (((bottom - top) + scrollY) - (scrollY == maxScrollY ? 0 : extendedPaddingBottom));
        int top2 = top;
        int bottom2 = bottom;
        if (this.mShadowRadius != 0.0f) {
            int i2 = compoundPaddingRight;
            float clipLeft2 = clipLeft + Math.min(0.0f, this.mShadowDx - this.mShadowRadius);
            float clipRight3 = clipRight2 + Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
            float clipTop4 = clipTop3 + Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
            clipBottom = clipBottom3 + Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
            clipRight = clipRight3;
            clipTop = clipTop4;
            clipTop2 = clipLeft2;
        } else {
            clipBottom = clipBottom3;
            clipRight = clipRight2;
            clipTop = clipTop3;
            clipTop2 = clipLeft;
        }
        canvas2.clipRect(clipTop2, clipTop, clipRight, clipBottom);
        int voffsetText = 0;
        if ((this.mGravity & 112) != 48) {
            int voffsetText2 = getVerticalOffset(false);
            voffsetCursor = getVerticalOffset(true);
            voffsetText = voffsetText2;
        } else {
            voffsetCursor = 0;
        }
        canvas2.translate((float) compoundPaddingLeft2, (float) (extendedPaddingTop + voffsetText));
        int layoutDirection3 = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity, layoutDirection3);
        if (isMarqueeFadeEnabled()) {
            if (this.mSingleLine || getLineCount() != 1 || !canMarquee() || (absoluteGravity & 7) == 3) {
                layoutDirection = layoutDirection3;
                clipBottom2 = clipBottom;
            } else {
                int width = this.mRight - this.mLeft;
                layoutDirection = layoutDirection3;
                clipBottom2 = clipBottom;
                int i3 = width;
                canvas2.translate(((float) layout2.getParagraphDirection(0)) * (this.mLayout.getLineRight(0) - ((float) (width - (getCompoundPaddingLeft() + getCompoundPaddingRight())))), 0.0f);
            }
            if (this.mMarquee == null || !this.mMarquee.isRunning()) {
                layoutDirection2 = 0;
            } else {
                layoutDirection2 = 0;
                canvas2.translate(((float) layout2.getParagraphDirection(0)) * (-this.mMarquee.getScroll()), 0.0f);
            }
        } else {
            layoutDirection = layoutDirection3;
            clipBottom2 = clipBottom;
            layoutDirection2 = 0;
        }
        int cursorOffsetVertical2 = voffsetCursor - voffsetText;
        Path highlight = getUpdatedHighlightPath();
        if (this.mEditor != null) {
            Layout layout3 = layout2;
            Path highlight2 = highlight;
            Drawables drawables = dr2;
            int i4 = maxScrollY2;
            int i5 = compoundPaddingLeft2;
            int i6 = color3;
            int i7 = layoutDirection;
            compoundPaddingLeft = layoutDirection2;
            int i8 = top2;
            float f = clipBottom2;
            int cursorOffsetVertical3 = cursorOffsetVertical2;
            int top3 = bottom2;
            float f2 = clipRight;
            this.mEditor.onDraw(canvas, layout3, highlight2, this.mHighlightPaint, cursorOffsetVertical3);
            layout = layout3;
            highlight = highlight2;
            cursorOffsetVertical = cursorOffsetVertical3;
        } else {
            Path path = highlight;
            int i9 = compoundPaddingLeft2;
            Drawables drawables2 = dr2;
            int i10 = color3;
            int i11 = maxScrollY2;
            int color4 = top2;
            int top4 = bottom2;
            int i12 = layoutDirection;
            float f3 = clipBottom2;
            compoundPaddingLeft = layoutDirection2;
            float f4 = clipRight;
            layout = layout2;
            cursorOffsetVertical = cursorOffsetVertical2;
            layout.draw(canvas2, highlight, this.mHighlightPaint, cursorOffsetVertical);
        }
        if (this.mMarquee != null && this.mMarquee.shouldDrawGhost()) {
            canvas2.translate(((float) layout.getParagraphDirection(compoundPaddingLeft)) * this.mMarquee.getGhostOffset(), 0.0f);
            layout.draw(canvas2, highlight, this.mHighlightPaint, cursorOffsetVertical);
        }
        canvas.restore();
    }

    public void getFocusedRect(Rect r) {
        if (this.mLayout == null) {
            super.getFocusedRect(r);
            return;
        }
        int selEnd = getSelectionEnd();
        if (selEnd < 0) {
            super.getFocusedRect(r);
            return;
        }
        int selStart = getSelectionStart();
        if (selStart < 0 || selStart >= selEnd) {
            int line = this.mLayout.getLineForOffset(selEnd);
            r.top = this.mLayout.getLineTop(line);
            r.bottom = this.mLayout.getLineBottom(line);
            r.left = ((int) this.mLayout.getPrimaryHorizontal(selEnd)) - 2;
            r.right = r.left + 4;
        } else {
            int lineStart = this.mLayout.getLineForOffset(selStart);
            int lineEnd = this.mLayout.getLineForOffset(selEnd);
            r.top = this.mLayout.getLineTop(lineStart);
            r.bottom = this.mLayout.getLineBottom(lineEnd);
            if (lineStart == lineEnd) {
                r.left = (int) this.mLayout.getPrimaryHorizontal(selStart);
                r.right = (int) this.mLayout.getPrimaryHorizontal(selEnd);
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(selStart, selEnd, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                synchronized (TEMP_RECTF) {
                    this.mHighlightPath.computeBounds(TEMP_RECTF, true);
                    r.left = ((int) TEMP_RECTF.left) - 1;
                    r.right = ((int) TEMP_RECTF.right) + 1;
                }
            }
        }
        int paddingLeft = getCompoundPaddingLeft();
        int paddingTop = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            paddingTop += getVerticalOffset(false);
        }
        r.offset(paddingLeft, paddingTop);
        r.bottom += getExtendedPaddingBottom();
    }

    public int getLineCount() {
        if (this.mLayout != null) {
            return this.mLayout.getLineCount();
        }
        return 0;
    }

    public int getLineBounds(int line, Rect bounds) {
        if (this.mLayout == null) {
            if (bounds != null) {
                bounds.set(0, 0, 0, 0);
            }
            return 0;
        }
        int baseline = this.mLayout.getLineBounds(line, bounds);
        int voffset = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            voffset += getVerticalOffset(true);
        }
        if (bounds != null) {
            bounds.offset(getCompoundPaddingLeft(), voffset);
        }
        return baseline + voffset;
    }

    public int getBaseline() {
        if (this.mLayout == null) {
            return super.getBaseline();
        }
        return getBaselineOffset() + this.mLayout.getLineBaseline(0);
    }

    /* access modifiers changed from: package-private */
    public int getBaselineOffset() {
        int voffset = 0;
        if ((this.mGravity & 112) != 48) {
            voffset = getVerticalOffset(true);
        }
        if (isLayoutModeOptical(this.mParent)) {
            voffset -= getOpticalInsets().top;
        }
        return getExtendedPaddingTop() + voffset;
    }

    /* access modifiers changed from: protected */
    public int getFadeTop(boolean offsetRequired) {
        if (this.mLayout == null) {
            return 0;
        }
        int voffset = 0;
        if ((this.mGravity & 112) != 48) {
            voffset = getVerticalOffset(true);
        }
        if (offsetRequired) {
            voffset += getTopPaddingOffset();
        }
        return getExtendedPaddingTop() + voffset;
    }

    /* access modifiers changed from: protected */
    public int getFadeHeight(boolean offsetRequired) {
        if (this.mLayout != null) {
            return this.mLayout.getHeight();
        }
        return 0;
    }

    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        if (this.mSpannable != null && this.mLinksClickable) {
            int offset = getOffsetForPosition(event.getX(pointerIndex), event.getY(pointerIndex));
            if (((ClickableSpan[]) this.mSpannable.getSpans(offset, offset, ClickableSpan.class)).length > 0) {
                return PointerIcon.getSystemIcon(this.mContext, 1002);
            }
        }
        if (isTextSelectable() || isTextEditable()) {
            return PointerIcon.getSystemIcon(this.mContext, 1008);
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !handleBackInTextActionModeIfNeeded(event)) {
            return super.onKeyPreIme(keyCode, event);
        }
        return true;
    }

    public boolean handleBackInTextActionModeIfNeeded(KeyEvent event) {
        if (this.mEditor == null || this.mEditor.getTextActionMode() == null) {
            return false;
        }
        if (event.getAction() == 0 && event.getRepeatCount() == 0) {
            KeyEvent.DispatcherState state = getKeyDispatcherState();
            if (state != null) {
                state.startTracking(event, this);
            }
            return true;
        }
        if (event.getAction() == 1) {
            KeyEvent.DispatcherState state2 = getKeyDispatcherState();
            if (state2 != null) {
                state2.handleUpEvent(event);
            }
            if (event.isTracking() && !event.isCanceled()) {
                stopTextActionMode();
                return true;
            }
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (doKeyDown(keyCode, event, (KeyEvent) null) == 0) {
            return super.onKeyDown(keyCode, event);
        }
        return true;
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        KeyEvent down = KeyEvent.changeAction(event, 0);
        int which = doKeyDown(keyCode, down, event);
        if (which == 0) {
            return super.onKeyMultiple(keyCode, repeatCount, event);
        }
        if (which == -1) {
            return true;
        }
        int repeatCount2 = repeatCount - 1;
        KeyEvent up = KeyEvent.changeAction(event, 1);
        if (which == 1) {
            this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, keyCode, up);
            while (true) {
                repeatCount2--;
                if (repeatCount2 <= 0) {
                    break;
                }
                this.mEditor.mKeyListener.onKeyDown(this, (Editable) this.mText, keyCode, down);
                this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, keyCode, up);
            }
            hideErrorIfUnchanged();
        } else if (which == 2) {
            this.mMovement.onKeyUp(this, this.mSpannable, keyCode, up);
            while (true) {
                repeatCount2--;
                if (repeatCount2 <= 0) {
                    break;
                }
                this.mMovement.onKeyDown(this, this.mSpannable, keyCode, down);
                this.mMovement.onKeyUp(this, this.mSpannable, keyCode, up);
            }
        }
        return true;
    }

    private boolean shouldAdvanceFocusOnEnter() {
        int variation;
        if (getKeyListener() == null) {
            return false;
        }
        if (this.mSingleLine) {
            return true;
        }
        if (this.mEditor != null && (this.mEditor.mInputType & 15) == 1 && ((variation = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION) == 32 || variation == 48)) {
            return true;
        }
        return false;
    }

    private boolean shouldAdvanceFocusOnTab() {
        int variation;
        if (getKeyListener() == null || this.mSingleLine || this.mEditor == null || (this.mEditor.mInputType & 15) != 1 || ((variation = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION) != 262144 && variation != 131072)) {
            return true;
        }
        return false;
    }

    private boolean isDirectionalNavigationKey(int keyCode) {
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                return false;
        }
    }

    private int doKeyDown(int keyCode, KeyEvent event, KeyEvent otherEvent) {
        if (!isEnabled()) {
            return 0;
        }
        if (event.getRepeatCount() == 0 && !KeyEvent.isModifierKey(keyCode)) {
            this.mPreventDefaultMovement = false;
        }
        if (keyCode != 4) {
            if (keyCode != 23) {
                if (keyCode != 61) {
                    if (keyCode != 66) {
                        if (keyCode != 112) {
                            if (keyCode != 124) {
                                switch (keyCode) {
                                    case 277:
                                        if (event.hasNoModifiers() && canCut() && onTextContextMenuItem(16908320)) {
                                            return -1;
                                        }
                                    case 278:
                                        if (event.hasNoModifiers() && canCopy() && onTextContextMenuItem(16908321)) {
                                            return -1;
                                        }
                                    case 279:
                                        if (event.hasNoModifiers() && canPaste() && onTextContextMenuItem(16908322)) {
                                            return -1;
                                        }
                                }
                            } else if (!event.hasModifiers(4096) || !canCopy()) {
                                if (event.hasModifiers(1) && canPaste() && onTextContextMenuItem(16908322)) {
                                    return -1;
                                }
                            } else if (onTextContextMenuItem(16908321)) {
                                return -1;
                            }
                        } else if (event.hasModifiers(1) && canCut() && onTextContextMenuItem(16908320)) {
                            return -1;
                        }
                    } else if (event.hasNoModifiers()) {
                        if (this.mEditor != null && this.mEditor.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, 0, event)) {
                            this.mEditor.mInputContentType.enterDown = true;
                            return -1;
                        } else if ((event.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) {
                            if (hasOnClickListeners()) {
                                return 0;
                            }
                            return -1;
                        }
                    }
                } else if ((event.hasNoModifiers() || event.hasModifiers(1)) && shouldAdvanceFocusOnTab()) {
                    return 0;
                }
            } else if (event.hasNoModifiers() && shouldAdvanceFocusOnEnter()) {
                return 0;
            }
        } else if (!(this.mEditor == null || this.mEditor.getTextActionMode() == null)) {
            stopTextActionMode();
            return -1;
        }
        if (!(this.mEditor == null || this.mEditor.mKeyListener == null)) {
            boolean doDown = true;
            if (otherEvent != null) {
                try {
                    beginBatchEdit();
                    boolean handled = this.mEditor.mKeyListener.onKeyOther(this, (Editable) this.mText, otherEvent);
                    hideErrorIfUnchanged();
                    doDown = false;
                    if (handled) {
                        endBatchEdit();
                        return -1;
                    }
                } catch (AbstractMethodError e) {
                } catch (Throwable th) {
                    endBatchEdit();
                    throw th;
                }
                endBatchEdit();
            }
            if (doDown) {
                beginBatchEdit();
                boolean handled2 = this.mEditor.mKeyListener.onKeyDown(this, (Editable) this.mText, keyCode, event);
                endBatchEdit();
                hideErrorIfUnchanged();
                if (handled2) {
                    return 1;
                }
            }
        }
        if (!(this.mMovement == null || this.mLayout == null)) {
            boolean doDown2 = true;
            if (otherEvent != null) {
                try {
                    doDown2 = false;
                    if (this.mMovement.onKeyOther(this, this.mSpannable, otherEvent)) {
                        return -1;
                    }
                } catch (AbstractMethodError e2) {
                }
            }
            if (!doDown2 || !this.mMovement.onKeyDown(this, this.mSpannable, keyCode, event)) {
                if (event.getSource() == 257 && isDirectionalNavigationKey(keyCode)) {
                    return -1;
                }
            } else if (event.getRepeatCount() != 0 || KeyEvent.isModifierKey(keyCode)) {
                return 2;
            } else {
                this.mPreventDefaultMovement = true;
                return 2;
            }
        }
        if (!this.mPreventDefaultMovement || KeyEvent.isModifierKey(keyCode)) {
            return 0;
        }
        return -1;
    }

    public void resetErrorChangedFlag() {
        if (this.mEditor != null) {
            this.mEditor.mErrorWasChanged = false;
        }
    }

    public void hideErrorIfUnchanged() {
        if (this.mEditor != null && this.mEditor.mError != null && !this.mEditor.mErrorWasChanged) {
            setError((CharSequence) null, (Drawable) null);
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        InputMethodManager imm;
        if (!isEnabled()) {
            return super.onKeyUp(keyCode, event);
        }
        if (!KeyEvent.isModifierKey(keyCode)) {
            this.mPreventDefaultMovement = false;
        }
        if (keyCode == 23) {
            if (event.hasNoModifiers() && !hasOnClickListeners() && this.mMovement != null && (this.mText instanceof Editable) && this.mLayout != null && onCheckIsTextEditor()) {
                InputMethodManager imm2 = getInputMethodManager();
                viewClicked(imm2);
                if (imm2 != null && getShowSoftInputOnFocus()) {
                    imm2.showSoftInput(this, 0);
                }
            }
            return super.onKeyUp(keyCode, event);
        } else if (keyCode == 66 && event.hasNoModifiers()) {
            if (!(this.mEditor == null || this.mEditor.mInputContentType == null || this.mEditor.mInputContentType.onEditorActionListener == null || !this.mEditor.mInputContentType.enterDown)) {
                this.mEditor.mInputContentType.enterDown = false;
                if (this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, 0, event)) {
                    return true;
                }
            }
            if (((event.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) && !hasOnClickListeners()) {
                View v = focusSearch(130);
                if (v != null) {
                    if (v.requestFocus(130)) {
                        super.onKeyUp(keyCode, event);
                        return true;
                    }
                    throw new IllegalStateException("focus search returned a view that wasn't able to take focus!");
                } else if (!((event.getFlags() & 16) == 0 || (imm = getInputMethodManager()) == null || !imm.isActive(this))) {
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                }
            }
            return super.onKeyUp(keyCode, event);
        } else if (this.mEditor != null && this.mEditor.mKeyListener != null && this.mEditor.mKeyListener.onKeyUp(this, (Editable) this.mText, keyCode, event)) {
            return true;
        } else {
            if (this.mMovement == null || this.mLayout == null || !this.mMovement.onKeyUp(this, this.mSpannable, keyCode, event)) {
                return super.onKeyUp(keyCode, event);
            }
            return true;
        }
    }

    public boolean onCheckIsTextEditor() {
        return (this.mEditor == null || this.mEditor.mInputType == 0) ? false : true;
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        if (onCheckIsTextEditor() && isEnabled()) {
            this.mEditor.createInputMethodStateIfNeeded();
            outAttrs.inputType = getInputType();
            if (this.mEditor.mInputContentType != null) {
                outAttrs.imeOptions = this.mEditor.mInputContentType.imeOptions;
                outAttrs.privateImeOptions = this.mEditor.mInputContentType.privateImeOptions;
                outAttrs.actionLabel = this.mEditor.mInputContentType.imeActionLabel;
                outAttrs.actionId = this.mEditor.mInputContentType.imeActionId;
                outAttrs.extras = this.mEditor.mInputContentType.extras;
                outAttrs.hintLocales = this.mEditor.mInputContentType.imeHintLocales;
            } else {
                outAttrs.imeOptions = 0;
                outAttrs.hintLocales = null;
            }
            if (focusSearch(130) != null) {
                outAttrs.imeOptions |= 134217728;
            }
            if (focusSearch(33) != null) {
                outAttrs.imeOptions |= 67108864;
            }
            if ((outAttrs.imeOptions & 255) == 0) {
                if ((outAttrs.imeOptions & 134217728) != 0) {
                    outAttrs.imeOptions |= 5;
                } else {
                    outAttrs.imeOptions |= 6;
                }
                if (!shouldAdvanceFocusOnEnter()) {
                    outAttrs.imeOptions |= 1073741824;
                }
            }
            if (isMultilineInputType(outAttrs.inputType)) {
                outAttrs.imeOptions |= 1073741824;
            }
            outAttrs.hintText = this.mHint;
            outAttrs.targetInputMethodUser = this.mTextOperationUser;
            if (this.mText instanceof Editable) {
                InputConnection ic = new EditableInputConnection(this);
                outAttrs.initialSelStart = getSelectionStart();
                outAttrs.initialSelEnd = getSelectionEnd();
                outAttrs.initialCapsMode = ic.getCursorCapsMode(getInputType());
                return ic;
            }
        }
        return null;
    }

    public boolean extractText(ExtractedTextRequest request, ExtractedText outText) {
        createEditorIfNeeded();
        return this.mEditor.extractText(request, outText);
    }

    static void removeParcelableSpans(Spannable spannable, int start, int end) {
        Object[] spans = spannable.getSpans(start, end, ParcelableSpan.class);
        int i = spans.length;
        while (i > 0) {
            i--;
            spannable.removeSpan(spans[i]);
        }
    }

    public void setExtractedText(ExtractedText text) {
        Editable content = getEditableText();
        if (text.text != null) {
            if (content == null) {
                setText(text.text, BufferType.EDITABLE);
            } else {
                int start = 0;
                int end = content.length();
                if (text.partialStartOffset >= 0) {
                    int N = content.length();
                    start = text.partialStartOffset;
                    if (start > N) {
                        start = N;
                    }
                    end = text.partialEndOffset;
                    if (end > N) {
                        end = N;
                    }
                }
                int start2 = start;
                int end2 = end;
                removeParcelableSpans(content, start2, end2);
                if (!TextUtils.equals(content.subSequence(start2, end2), text.text)) {
                    content.replace(start2, end2, text.text);
                } else if (text.text instanceof Spanned) {
                    TextUtils.copySpansFrom((Spanned) text.text, 0, end2 - start2, Object.class, content, start2);
                }
            }
        }
        Spannable sp = (Spannable) getText();
        int N2 = sp.length();
        int start3 = text.selectionStart;
        if (start3 < 0) {
            start3 = 0;
        } else if (start3 > N2) {
            start3 = N2;
        }
        int end3 = text.selectionEnd;
        if (end3 < 0) {
            end3 = 0;
        } else if (end3 > N2) {
            end3 = N2;
        }
        Selection.setSelection(sp, start3, end3);
        if ((text.flags & 2) != 0) {
            MetaKeyKeyListener.startSelecting(this, sp);
        } else {
            MetaKeyKeyListener.stopSelecting(this, sp);
        }
        setHintInternal(text.hint);
    }

    public void setExtracting(ExtractedTextRequest req) {
        if (this.mEditor.mInputMethodState != null) {
            this.mEditor.mInputMethodState.mExtractedTextRequest = req;
        }
        this.mEditor.hideCursorAndSpanControllers();
        stopTextActionMode();
        if (this.mEditor.mSelectionModifierCursorController != null) {
            this.mEditor.mSelectionModifierCursorController.resetTouchOffsets();
        }
    }

    public void onCommitCompletion(CompletionInfo text) {
    }

    public void onCommitCorrection(CorrectionInfo info) {
        if (this.mEditor != null) {
            this.mEditor.onCommitCorrection(info);
        }
    }

    public void beginBatchEdit() {
        if (this.mEditor != null) {
            this.mEditor.beginBatchEdit();
        }
    }

    public void endBatchEdit() {
        if (this.mEditor != null) {
            this.mEditor.endBatchEdit();
        }
    }

    public void onBeginBatchEdit() {
    }

    public void onEndBatchEdit() {
    }

    public boolean onPrivateIMECommand(String action, Bundle data) {
        return false;
    }

    @VisibleForTesting
    @UnsupportedAppUsage
    public void nullLayouts() {
        if ((this.mLayout instanceof BoringLayout) && this.mSavedLayout == null) {
            this.mSavedLayout = (BoringLayout) this.mLayout;
        }
        if ((this.mHintLayout instanceof BoringLayout) && this.mSavedHintLayout == null) {
            this.mSavedHintLayout = (BoringLayout) this.mHintLayout;
        }
        this.mHintLayout = null;
        this.mLayout = null;
        this.mSavedMarqueeModeLayout = null;
        this.mHintBoring = null;
        this.mBoring = null;
        if (this.mEditor != null) {
            this.mEditor.prepareCursorControllers();
        }
    }

    @UnsupportedAppUsage
    private void assumeLayout() {
        int width = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (width < 1) {
            width = 0;
        }
        int physicalWidth = width;
        if (this.mHorizontallyScrolling) {
            width = 1048576;
        }
        makeNewLayout(width, physicalWidth, UNKNOWN_BORING, UNKNOWN_BORING, physicalWidth, false);
    }

    @UnsupportedAppUsage
    private Layout.Alignment getLayoutAlignment() {
        switch (getTextAlignment()) {
            case 1:
                int i = this.mGravity & 8388615;
                if (i == 1) {
                    return Layout.Alignment.ALIGN_CENTER;
                }
                if (i == 3) {
                    return Layout.Alignment.ALIGN_LEFT;
                }
                if (i == 5) {
                    return Layout.Alignment.ALIGN_RIGHT;
                }
                if (i == 8388611) {
                    return Layout.Alignment.ALIGN_NORMAL;
                }
                if (i != 8388613) {
                    return Layout.Alignment.ALIGN_NORMAL;
                }
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 2:
                return Layout.Alignment.ALIGN_NORMAL;
            case 3:
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 4:
                return Layout.Alignment.ALIGN_CENTER;
            case 5:
                return getLayoutDirection() == 1 ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
            case 6:
                return getLayoutDirection() == 1 ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0226, code lost:
        if (r21 != r14.mLayout.getParagraphDirection(r6 ? 1 : 0)) goto L_0x022e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0248  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x0252  */
    /* JADX WARNING: Removed duplicated region for block: B:128:? A[RETURN, SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.UnsupportedAppUsage
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void makeNewLayout(int r24, int r25, android.text.BoringLayout.Metrics r26, android.text.BoringLayout.Metrics r27, int r28, boolean r29) {
        /*
            r23 = this;
            r14 = r23
            r15 = r28
            r23.stopMarquee()
            int r0 = r14.mMaximum
            r14.mOldMaximum = r0
            int r0 = r14.mMaxMode
            r14.mOldMaxMode = r0
            r13 = 1
            r14.mHighlightPathBogus = r13
            if (r24 >= 0) goto L_0x0018
            r0 = 0
            r16 = r0
            goto L_0x001a
        L_0x0018:
            r16 = r24
        L_0x001a:
            if (r25 >= 0) goto L_0x0020
            r0 = 0
            r17 = r0
            goto L_0x0022
        L_0x0020:
            r17 = r25
        L_0x0022:
            android.text.Layout$Alignment r11 = r23.getLayoutAlignment()
            boolean r0 = r14.mSingleLine
            r10 = 0
            if (r0 == 0) goto L_0x0039
            android.text.Layout r0 = r14.mLayout
            if (r0 == 0) goto L_0x0039
            android.text.Layout$Alignment r0 = android.text.Layout.Alignment.ALIGN_NORMAL
            if (r11 == r0) goto L_0x0037
            android.text.Layout$Alignment r0 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            if (r11 != r0) goto L_0x0039
        L_0x0037:
            r0 = r13
            goto L_0x003a
        L_0x0039:
            r0 = r10
        L_0x003a:
            r18 = r0
            r0 = 0
            if (r18 == 0) goto L_0x0045
            android.text.Layout r1 = r14.mLayout
            int r0 = r1.getParagraphDirection(r10)
        L_0x0045:
            r9 = r0
            android.text.TextUtils$TruncateAt r0 = r14.mEllipsize
            if (r0 == 0) goto L_0x0052
            android.text.method.KeyListener r0 = r23.getKeyListener()
            if (r0 != 0) goto L_0x0052
            r5 = r13
            goto L_0x0053
        L_0x0052:
            r5 = r10
        L_0x0053:
            android.text.TextUtils$TruncateAt r0 = r14.mEllipsize
            android.text.TextUtils$TruncateAt r1 = android.text.TextUtils.TruncateAt.MARQUEE
            if (r0 != r1) goto L_0x005f
            int r0 = r14.mMarqueeFadeMode
            if (r0 == 0) goto L_0x005f
            r0 = r13
            goto L_0x0060
        L_0x005f:
            r0 = r10
        L_0x0060:
            r19 = r0
            android.text.TextUtils$TruncateAt r0 = r14.mEllipsize
            android.text.TextUtils$TruncateAt r1 = r14.mEllipsize
            android.text.TextUtils$TruncateAt r2 = android.text.TextUtils.TruncateAt.MARQUEE
            if (r1 != r2) goto L_0x0070
            int r1 = r14.mMarqueeFadeMode
            if (r1 != r13) goto L_0x0070
            android.text.TextUtils$TruncateAt r0 = android.text.TextUtils.TruncateAt.END_SMALL
        L_0x0070:
            r8 = r0
            android.text.TextDirectionHeuristic r0 = r14.mTextDir
            if (r0 != 0) goto L_0x007b
            android.text.TextDirectionHeuristic r0 = r23.getTextDirectionHeuristic()
            r14.mTextDir = r0
        L_0x007b:
            android.text.TextUtils$TruncateAt r0 = r14.mEllipsize
            if (r8 != r0) goto L_0x0081
            r7 = r13
            goto L_0x0082
        L_0x0081:
            r7 = r10
        L_0x0082:
            r0 = r23
            r1 = r16
            r2 = r26
            r3 = r28
            r4 = r11
            r6 = r8
            android.text.Layout r0 = r0.makeSingleLayout(r1, r2, r3, r4, r5, r6, r7)
            r14.mLayout = r0
            if (r19 == 0) goto L_0x00be
            android.text.TextUtils$TruncateAt r0 = android.text.TextUtils.TruncateAt.MARQUEE
            if (r8 != r0) goto L_0x009c
            android.text.TextUtils$TruncateAt r0 = android.text.TextUtils.TruncateAt.END
        L_0x009a:
            r12 = r0
            goto L_0x009f
        L_0x009c:
            android.text.TextUtils$TruncateAt r0 = android.text.TextUtils.TruncateAt.MARQUEE
            goto L_0x009a
        L_0x009f:
            android.text.TextUtils$TruncateAt r0 = r14.mEllipsize
            if (r8 == r0) goto L_0x00a5
            r0 = r13
            goto L_0x00a6
        L_0x00a5:
            r0 = r10
        L_0x00a6:
            r6 = r23
            r7 = r16
            r20 = r8
            r8 = r26
            r4 = r9
            r9 = r28
            r3 = r10
            r10 = r11
            r2 = r11
            r11 = r5
            r1 = r13
            r13 = r0
            android.text.Layout r0 = r6.makeSingleLayout(r7, r8, r9, r10, r11, r12, r13)
            r14.mSavedMarqueeModeLayout = r0
            goto L_0x00c4
        L_0x00be:
            r20 = r8
            r4 = r9
            r3 = r10
            r2 = r11
            r1 = r13
        L_0x00c4:
            android.text.TextUtils$TruncateAt r0 = r14.mEllipsize
            if (r0 == 0) goto L_0x00ca
            r0 = r1
            goto L_0x00cb
        L_0x00ca:
            r0 = r3
        L_0x00cb:
            r11 = r0
            r0 = 0
            r14.mHintLayout = r0
            java.lang.CharSequence r0 = r14.mHint
            if (r0 == 0) goto L_0x020f
            if (r11 == 0) goto L_0x00d9
            r0 = r16
            r12 = r0
            goto L_0x00db
        L_0x00d9:
            r12 = r17
        L_0x00db:
            android.text.BoringLayout$Metrics r0 = UNKNOWN_BORING
            r5 = r27
            if (r5 != r0) goto L_0x00f3
            java.lang.CharSequence r0 = r14.mHint
            android.text.TextPaint r6 = r14.mTextPaint
            android.text.TextDirectionHeuristic r7 = r14.mTextDir
            android.text.BoringLayout$Metrics r8 = r14.mHintBoring
            android.text.BoringLayout$Metrics r0 = android.text.BoringLayout.isBoring(r0, r6, r7, r8)
            if (r0 == 0) goto L_0x00f1
            r14.mHintBoring = r0
        L_0x00f1:
            r13 = r0
            goto L_0x00f4
        L_0x00f3:
            r13 = r5
        L_0x00f4:
            if (r13 == 0) goto L_0x0197
            int r0 = r13.width
            if (r0 > r12) goto L_0x0153
            if (r11 == 0) goto L_0x0109
            int r0 = r13.width
            if (r0 > r15) goto L_0x0101
            goto L_0x0109
        L_0x0101:
            r10 = r1
            r22 = r2
            r15 = r3
            r21 = r4
            goto L_0x0159
        L_0x0109:
            android.text.BoringLayout r0 = r14.mSavedHintLayout
            if (r0 == 0) goto L_0x0132
            android.text.BoringLayout r0 = r14.mSavedHintLayout
            java.lang.CharSequence r5 = r14.mHint
            android.text.TextPaint r6 = r14.mTextPaint
            float r7 = r14.mSpacingMult
            float r8 = r14.mSpacingAdd
            boolean r9 = r14.mIncludePad
            r10 = r1
            r1 = r5
            r5 = r2
            r2 = r6
            r6 = r3
            r3 = r12
            r21 = r4
            r4 = r5
            r22 = r5
            r5 = r7
            r7 = r6
            r6 = r8
            r8 = r7
            r7 = r13
            r15 = r8
            r8 = r9
            android.text.BoringLayout r0 = r0.replaceOrMake(r1, r2, r3, r4, r5, r6, r7, r8)
            r14.mHintLayout = r0
            goto L_0x014c
        L_0x0132:
            r10 = r1
            r22 = r2
            r15 = r3
            r21 = r4
            java.lang.CharSequence r0 = r14.mHint
            android.text.TextPaint r1 = r14.mTextPaint
            float r4 = r14.mSpacingMult
            float r5 = r14.mSpacingAdd
            boolean r7 = r14.mIncludePad
            r2 = r12
            r3 = r22
            r6 = r13
            android.text.BoringLayout r0 = android.text.BoringLayout.make(r0, r1, r2, r3, r4, r5, r6, r7)
            r14.mHintLayout = r0
        L_0x014c:
            android.text.Layout r0 = r14.mHintLayout
            android.text.BoringLayout r0 = (android.text.BoringLayout) r0
            r14.mSavedHintLayout = r0
            goto L_0x019c
        L_0x0153:
            r10 = r1
            r22 = r2
            r15 = r3
            r21 = r4
        L_0x0159:
            if (r11 == 0) goto L_0x019c
            int r0 = r13.width
            if (r0 > r12) goto L_0x019c
            android.text.BoringLayout r0 = r14.mSavedHintLayout
            if (r0 == 0) goto L_0x017e
            android.text.BoringLayout r0 = r14.mSavedHintLayout
            java.lang.CharSequence r1 = r14.mHint
            android.text.TextPaint r2 = r14.mTextPaint
            float r5 = r14.mSpacingMult
            float r6 = r14.mSpacingAdd
            boolean r8 = r14.mIncludePad
            android.text.TextUtils$TruncateAt r9 = r14.mEllipsize
            r3 = r12
            r4 = r22
            r7 = r13
            r10 = r28
            android.text.BoringLayout r0 = r0.replaceOrMake(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r14.mHintLayout = r0
            goto L_0x019c
        L_0x017e:
            java.lang.CharSequence r0 = r14.mHint
            android.text.TextPaint r1 = r14.mTextPaint
            float r4 = r14.mSpacingMult
            float r5 = r14.mSpacingAdd
            boolean r7 = r14.mIncludePad
            android.text.TextUtils$TruncateAt r8 = r14.mEllipsize
            r2 = r12
            r3 = r22
            r6 = r13
            r9 = r28
            android.text.BoringLayout r0 = android.text.BoringLayout.make(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r14.mHintLayout = r0
            goto L_0x019c
        L_0x0197:
            r22 = r2
            r15 = r3
            r21 = r4
        L_0x019c:
            android.text.Layout r0 = r14.mHintLayout
            if (r0 != 0) goto L_0x0208
            java.lang.CharSequence r0 = r14.mHint
            java.lang.CharSequence r1 = r14.mHint
            int r1 = r1.length()
            android.text.TextPaint r2 = r14.mTextPaint
            android.text.StaticLayout$Builder r0 = android.text.StaticLayout.Builder.obtain(r0, r15, r1, r2, r12)
            r1 = r22
            android.text.StaticLayout$Builder r0 = r0.setAlignment(r1)
            android.text.TextDirectionHeuristic r2 = r14.mTextDir
            android.text.StaticLayout$Builder r0 = r0.setTextDirection(r2)
            float r2 = r14.mSpacingAdd
            float r3 = r14.mSpacingMult
            android.text.StaticLayout$Builder r0 = r0.setLineSpacing(r2, r3)
            boolean r2 = r14.mIncludePad
            android.text.StaticLayout$Builder r0 = r0.setIncludePad(r2)
            boolean r2 = r14.mUseFallbackLineSpacing
            android.text.StaticLayout$Builder r0 = r0.setUseLineSpacingFromFallbacks(r2)
            int r2 = r14.mBreakStrategy
            android.text.StaticLayout$Builder r0 = r0.setBreakStrategy(r2)
            int r2 = r14.mHyphenationFrequency
            android.text.StaticLayout$Builder r0 = r0.setHyphenationFrequency(r2)
            int r2 = r14.mJustificationMode
            android.text.StaticLayout$Builder r0 = r0.setJustificationMode(r2)
            int r2 = r14.mMaxMode
            r3 = 1
            if (r2 != r3) goto L_0x01e8
            int r2 = r14.mMaximum
            goto L_0x01eb
        L_0x01e8:
            r2 = 2147483647(0x7fffffff, float:NaN)
        L_0x01eb:
            android.text.StaticLayout$Builder r0 = r0.setMaxLines(r2)
            if (r11 == 0) goto L_0x01fe
            android.text.TextUtils$TruncateAt r2 = r14.mEllipsize
            android.text.StaticLayout$Builder r2 = r0.setEllipsize(r2)
            r6 = r15
            r4 = r28
            r2.setEllipsizedWidth(r4)
            goto L_0x0201
        L_0x01fe:
            r6 = r15
            r4 = r28
        L_0x0201:
            android.text.StaticLayout r2 = r0.build()
            r14.mHintLayout = r2
            goto L_0x021a
        L_0x0208:
            r6 = r15
            r1 = r22
            r3 = 1
            r4 = r28
            goto L_0x021a
        L_0x020f:
            r5 = r27
            r6 = r3
            r21 = r4
            r4 = r15
            r3 = r1
            r1 = r2
            r13 = r5
            r12 = r17
        L_0x021a:
            if (r29 != 0) goto L_0x022c
            if (r18 == 0) goto L_0x0229
            android.text.Layout r2 = r14.mLayout
            int r2 = r2.getParagraphDirection(r6)
            r5 = r21
            if (r5 == r2) goto L_0x0231
            goto L_0x022e
        L_0x0229:
            r5 = r21
            goto L_0x0231
        L_0x022c:
            r5 = r21
        L_0x022e:
            r23.registerForPreDraw()
        L_0x0231:
            android.text.TextUtils$TruncateAt r2 = r14.mEllipsize
            android.text.TextUtils$TruncateAt r6 = android.text.TextUtils.TruncateAt.MARQUEE
            if (r2 != r6) goto L_0x024e
            float r2 = (float) r4
            boolean r2 = r14.compressText(r2)
            if (r2 != 0) goto L_0x024e
            android.view.ViewGroup$LayoutParams r2 = r14.mLayoutParams
            int r2 = r2.height
            r6 = -2
            if (r2 == r6) goto L_0x024c
            r6 = -1
            if (r2 == r6) goto L_0x024c
            r23.startMarquee()
            goto L_0x024e
        L_0x024c:
            r14.mRestartMarquee = r3
        L_0x024e:
            android.widget.Editor r2 = r14.mEditor
            if (r2 == 0) goto L_0x0257
            android.widget.Editor r2 = r14.mEditor
            r2.prepareCursorControllers()
        L_0x0257:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.makeNewLayout(int, int, android.text.BoringLayout$Metrics, android.text.BoringLayout$Metrics, int, boolean):void");
    }

    @VisibleForTesting
    public boolean useDynamicLayout() {
        return isTextSelectable() || (this.mSpannable != null && this.mPrecomputed == null);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0174  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.text.Layout makeSingleLayout(int r18, android.text.BoringLayout.Metrics r19, int r20, android.text.Layout.Alignment r21, boolean r22, android.text.TextUtils.TruncateAt r23, boolean r24) {
        /*
            r17 = this;
            r0 = r17
            r12 = r18
            r13 = r20
            r14 = r21
            r11 = r23
            r15 = 0
            boolean r1 = r17.useDynamicLayout()
            if (r1 == 0) goto L_0x006a
            java.lang.CharSequence r1 = r0.mText
            android.text.TextPaint r2 = r0.mTextPaint
            android.text.DynamicLayout$Builder r1 = android.text.DynamicLayout.Builder.obtain(r1, r2, r12)
            java.lang.CharSequence r2 = r0.mTransformed
            android.text.DynamicLayout$Builder r1 = r1.setDisplayText(r2)
            android.text.DynamicLayout$Builder r1 = r1.setAlignment(r14)
            android.text.TextDirectionHeuristic r2 = r0.mTextDir
            android.text.DynamicLayout$Builder r1 = r1.setTextDirection(r2)
            float r2 = r0.mSpacingAdd
            float r3 = r0.mSpacingMult
            android.text.DynamicLayout$Builder r1 = r1.setLineSpacing(r2, r3)
            boolean r2 = r0.mIncludePad
            android.text.DynamicLayout$Builder r1 = r1.setIncludePad(r2)
            boolean r2 = r0.mUseFallbackLineSpacing
            android.text.DynamicLayout$Builder r1 = r1.setUseLineSpacingFromFallbacks(r2)
            int r2 = r0.mBreakStrategy
            android.text.DynamicLayout$Builder r1 = r1.setBreakStrategy(r2)
            int r2 = r0.mHyphenationFrequency
            android.text.DynamicLayout$Builder r1 = r1.setHyphenationFrequency(r2)
            int r2 = r0.mJustificationMode
            android.text.DynamicLayout$Builder r1 = r1.setJustificationMode(r2)
            android.text.method.KeyListener r2 = r17.getKeyListener()
            if (r2 != 0) goto L_0x0057
            r2 = r11
            goto L_0x0058
        L_0x0057:
            r2 = 0
        L_0x0058:
            android.text.DynamicLayout$Builder r1 = r1.setEllipsize(r2)
            android.text.DynamicLayout$Builder r1 = r1.setEllipsizedWidth(r13)
            android.text.DynamicLayout r15 = r1.build()
            r16 = r19
        L_0x0067:
            r13 = r11
            goto L_0x0111
        L_0x006a:
            android.text.BoringLayout$Metrics r1 = UNKNOWN_BORING
            r2 = r19
            if (r2 != r1) goto L_0x0082
            java.lang.CharSequence r1 = r0.mTransformed
            android.text.TextPaint r3 = r0.mTextPaint
            android.text.TextDirectionHeuristic r4 = r0.mTextDir
            android.text.BoringLayout$Metrics r5 = r0.mBoring
            android.text.BoringLayout$Metrics r1 = android.text.BoringLayout.isBoring(r1, r3, r4, r5)
            if (r1 == 0) goto L_0x0080
            r0.mBoring = r1
        L_0x0080:
            r10 = r1
            goto L_0x0083
        L_0x0082:
            r10 = r2
        L_0x0083:
            if (r10 == 0) goto L_0x010e
            int r1 = r10.width
            if (r1 > r12) goto L_0x00c9
            if (r11 == 0) goto L_0x008f
            int r1 = r10.width
            if (r1 > r13) goto L_0x00c9
        L_0x008f:
            if (r24 == 0) goto L_0x00ab
            android.text.BoringLayout r1 = r0.mSavedLayout
            if (r1 == 0) goto L_0x00ab
            android.text.BoringLayout r1 = r0.mSavedLayout
            java.lang.CharSequence r2 = r0.mTransformed
            android.text.TextPaint r3 = r0.mTextPaint
            float r6 = r0.mSpacingMult
            float r7 = r0.mSpacingAdd
            boolean r9 = r0.mIncludePad
            r4 = r18
            r5 = r21
            r8 = r10
            android.text.BoringLayout r1 = r1.replaceOrMake(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x00be
        L_0x00ab:
            java.lang.CharSequence r1 = r0.mTransformed
            android.text.TextPaint r2 = r0.mTextPaint
            float r5 = r0.mSpacingMult
            float r6 = r0.mSpacingAdd
            boolean r8 = r0.mIncludePad
            r3 = r18
            r4 = r21
            r7 = r10
            android.text.BoringLayout r1 = android.text.BoringLayout.make(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x00be:
            r15 = r1
            if (r24 == 0) goto L_0x00c6
            r1 = r15
            android.text.BoringLayout r1 = (android.text.BoringLayout) r1
            r0.mSavedLayout = r1
        L_0x00c6:
            r16 = r10
            goto L_0x0067
        L_0x00c9:
            if (r22 == 0) goto L_0x010e
            int r1 = r10.width
            if (r1 > r12) goto L_0x010e
            if (r24 == 0) goto L_0x00f2
            android.text.BoringLayout r1 = r0.mSavedLayout
            if (r1 == 0) goto L_0x00f2
            android.text.BoringLayout r1 = r0.mSavedLayout
            java.lang.CharSequence r2 = r0.mTransformed
            android.text.TextPaint r3 = r0.mTextPaint
            float r6 = r0.mSpacingMult
            float r7 = r0.mSpacingAdd
            boolean r9 = r0.mIncludePad
            r4 = r18
            r5 = r21
            r8 = r10
            r16 = r10
            r10 = r23
            r13 = r11
            r11 = r20
            android.text.BoringLayout r15 = r1.replaceOrMake(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)
            goto L_0x0111
        L_0x00f2:
            r16 = r10
            r13 = r11
            java.lang.CharSequence r1 = r0.mTransformed
            android.text.TextPaint r2 = r0.mTextPaint
            float r5 = r0.mSpacingMult
            float r6 = r0.mSpacingAdd
            boolean r8 = r0.mIncludePad
            r3 = r18
            r4 = r21
            r7 = r16
            r9 = r23
            r10 = r20
            android.text.BoringLayout r15 = android.text.BoringLayout.make(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            goto L_0x0111
        L_0x010e:
            r16 = r10
            r13 = r11
        L_0x0111:
            if (r15 != 0) goto L_0x0174
            java.lang.CharSequence r1 = r0.mTransformed
            r2 = 0
            java.lang.CharSequence r3 = r0.mTransformed
            int r3 = r3.length()
            android.text.TextPaint r4 = r0.mTextPaint
            android.text.StaticLayout$Builder r1 = android.text.StaticLayout.Builder.obtain(r1, r2, r3, r4, r12)
            android.text.StaticLayout$Builder r1 = r1.setAlignment(r14)
            android.text.TextDirectionHeuristic r2 = r0.mTextDir
            android.text.StaticLayout$Builder r1 = r1.setTextDirection(r2)
            float r2 = r0.mSpacingAdd
            float r3 = r0.mSpacingMult
            android.text.StaticLayout$Builder r1 = r1.setLineSpacing(r2, r3)
            boolean r2 = r0.mIncludePad
            android.text.StaticLayout$Builder r1 = r1.setIncludePad(r2)
            boolean r2 = r0.mUseFallbackLineSpacing
            android.text.StaticLayout$Builder r1 = r1.setUseLineSpacingFromFallbacks(r2)
            int r2 = r0.mBreakStrategy
            android.text.StaticLayout$Builder r1 = r1.setBreakStrategy(r2)
            int r2 = r0.mHyphenationFrequency
            android.text.StaticLayout$Builder r1 = r1.setHyphenationFrequency(r2)
            int r2 = r0.mJustificationMode
            android.text.StaticLayout$Builder r1 = r1.setJustificationMode(r2)
            int r2 = r0.mMaxMode
            r3 = 1
            if (r2 != r3) goto L_0x015a
            int r2 = r0.mMaximum
            goto L_0x015d
        L_0x015a:
            r2 = 2147483647(0x7fffffff, float:NaN)
        L_0x015d:
            android.text.StaticLayout$Builder r1 = r1.setMaxLines(r2)
            if (r22 == 0) goto L_0x016d
            android.text.StaticLayout$Builder r2 = r1.setEllipsize(r13)
            r3 = r20
            r2.setEllipsizedWidth(r3)
            goto L_0x016f
        L_0x016d:
            r3 = r20
        L_0x016f:
            android.text.StaticLayout r15 = r1.build()
            goto L_0x0176
        L_0x0174:
            r3 = r20
        L_0x0176:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.makeSingleLayout(int, android.text.BoringLayout$Metrics, int, android.text.Layout$Alignment, boolean, android.text.TextUtils$TruncateAt, boolean):android.text.Layout");
    }

    @UnsupportedAppUsage
    private boolean compressText(float width) {
        if (!isHardwareAccelerated() && width > 0.0f && this.mLayout != null && getLineCount() == 1 && !this.mUserSetTextScaleX && this.mTextPaint.getTextScaleX() == 1.0f) {
            float overflow = ((this.mLayout.getLineWidth(0) + 1.0f) - width) / width;
            if (overflow > 0.0f && overflow <= 0.07f) {
                this.mTextPaint.setTextScaleX((1.0f - overflow) - 0.005f);
                post(new Runnable() {
                    public void run() {
                        TextView.this.requestLayout();
                    }
                });
                return true;
            }
        }
        return false;
    }

    private static int desired(Layout layout) {
        int n = layout.getLineCount();
        CharSequence text = layout.getText();
        float max = 0.0f;
        for (int i = 0; i < n - 1; i++) {
            if (text.charAt(layout.getLineEnd(i) - 1) != 10) {
                return -1;
            }
        }
        for (int i2 = 0; i2 < n; i2++) {
            max = Math.max(max, layout.getLineWidth(i2));
        }
        return (int) Math.ceil((double) max);
    }

    public void setIncludeFontPadding(boolean includepad) {
        if (this.mIncludePad != includepad) {
            this.mIncludePad = includepad;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getIncludeFontPadding() {
        return this.mIncludePad;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x01f9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r33, int r34) {
        /*
            r32 = this;
            r7 = r32
            int r8 = android.view.View.MeasureSpec.getMode(r33)
            int r9 = android.view.View.MeasureSpec.getMode(r34)
            int r10 = android.view.View.MeasureSpec.getSize(r33)
            int r11 = android.view.View.MeasureSpec.getSize(r34)
            android.text.BoringLayout$Metrics r0 = UNKNOWN_BORING
            android.text.BoringLayout$Metrics r1 = UNKNOWN_BORING
            android.text.TextDirectionHeuristic r2 = r7.mTextDir
            if (r2 != 0) goto L_0x0020
            android.text.TextDirectionHeuristic r2 = r32.getTextDirectionHeuristic()
            r7.mTextDir = r2
        L_0x0020:
            r2 = -1
            r3 = 0
            r12 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r8 != r12) goto L_0x002a
            float r4 = (float) r10
        L_0x0027:
            r18 = r4
            goto L_0x002e
        L_0x002a:
            r4 = 2139095039(0x7f7fffff, float:3.4028235E38)
            goto L_0x0027
        L_0x002e:
            r6 = 1073741824(0x40000000, float:2.0)
            if (r8 != r6) goto L_0x003c
            r4 = r10
            r14 = r0
            r15 = r1
            r6 = r2
            r16 = r3
            r13 = r4
            r12 = 1
            goto L_0x013b
        L_0x003c:
            android.text.Layout r4 = r7.mLayout
            if (r4 == 0) goto L_0x004a
            android.text.TextUtils$TruncateAt r4 = r7.mEllipsize
            if (r4 != 0) goto L_0x004a
            android.text.Layout r4 = r7.mLayout
            int r2 = desired(r4)
        L_0x004a:
            if (r2 >= 0) goto L_0x005d
            java.lang.CharSequence r4 = r7.mTransformed
            android.text.TextPaint r13 = r7.mTextPaint
            android.text.TextDirectionHeuristic r14 = r7.mTextDir
            android.text.BoringLayout$Metrics r15 = r7.mBoring
            android.text.BoringLayout$Metrics r0 = android.text.BoringLayout.isBoring(r4, r13, r14, r15)
            if (r0 == 0) goto L_0x005e
            r7.mBoring = r0
            goto L_0x005e
        L_0x005d:
            r3 = 1
        L_0x005e:
            if (r0 == 0) goto L_0x0068
            android.text.BoringLayout$Metrics r4 = UNKNOWN_BORING
            if (r0 != r4) goto L_0x0065
            goto L_0x0068
        L_0x0065:
            int r4 = r0.width
            goto L_0x0086
        L_0x0068:
            if (r2 >= 0) goto L_0x0085
            java.lang.CharSequence r13 = r7.mTransformed
            r14 = 0
            java.lang.CharSequence r4 = r7.mTransformed
            int r15 = r4.length()
            android.text.TextPaint r4 = r7.mTextPaint
            android.text.TextDirectionHeuristic r6 = r7.mTextDir
            r16 = r4
            r17 = r6
            float r4 = android.text.Layout.getDesiredWidthWithLimit(r13, r14, r15, r16, r17, r18)
            double r13 = (double) r4
            double r13 = java.lang.Math.ceil(r13)
            int r2 = (int) r13
        L_0x0085:
            r4 = r2
        L_0x0086:
            android.widget.TextView$Drawables r6 = r7.mDrawables
            if (r6 == 0) goto L_0x0097
            int r13 = r6.mDrawableWidthTop
            int r4 = java.lang.Math.max(r4, r13)
            int r13 = r6.mDrawableWidthBottom
            int r4 = java.lang.Math.max(r4, r13)
        L_0x0097:
            java.lang.CharSequence r13 = r7.mHint
            if (r13 == 0) goto L_0x00ed
            r13 = -1
            android.text.Layout r14 = r7.mHintLayout
            if (r14 == 0) goto L_0x00aa
            android.text.TextUtils$TruncateAt r14 = r7.mEllipsize
            if (r14 != 0) goto L_0x00aa
            android.text.Layout r14 = r7.mHintLayout
            int r13 = desired(r14)
        L_0x00aa:
            if (r13 >= 0) goto L_0x00bc
            java.lang.CharSequence r14 = r7.mHint
            android.text.TextPaint r15 = r7.mTextPaint
            android.text.TextDirectionHeuristic r12 = r7.mTextDir
            android.text.BoringLayout$Metrics r5 = r7.mHintBoring
            android.text.BoringLayout$Metrics r1 = android.text.BoringLayout.isBoring(r14, r15, r12, r5)
            if (r1 == 0) goto L_0x00bc
            r7.mHintBoring = r1
        L_0x00bc:
            if (r1 == 0) goto L_0x00c6
            android.text.BoringLayout$Metrics r5 = UNKNOWN_BORING
            if (r1 != r5) goto L_0x00c3
            goto L_0x00c6
        L_0x00c3:
            int r5 = r1.width
            goto L_0x00e9
        L_0x00c6:
            if (r13 >= 0) goto L_0x00e8
            java.lang.CharSequence r5 = r7.mHint
            r22 = 0
            java.lang.CharSequence r12 = r7.mHint
            int r23 = r12.length()
            android.text.TextPaint r12 = r7.mTextPaint
            android.text.TextDirectionHeuristic r14 = r7.mTextDir
            r21 = r5
            r24 = r12
            r25 = r14
            r26 = r18
            float r5 = android.text.Layout.getDesiredWidthWithLimit(r21, r22, r23, r24, r25, r26)
            double r14 = (double) r5
            double r14 = java.lang.Math.ceil(r14)
            int r13 = (int) r14
        L_0x00e8:
            r5 = r13
        L_0x00e9:
            if (r5 <= r4) goto L_0x00ed
            r4 = r5
        L_0x00ed:
            int r5 = r32.getCompoundPaddingLeft()
            int r12 = r32.getCompoundPaddingRight()
            int r5 = r5 + r12
            int r4 = r4 + r5
            int r5 = r7.mMaxWidthMode
            r12 = 1
            if (r5 != r12) goto L_0x0108
            int r5 = r7.mMaxWidth
            int r12 = r32.getLineHeight()
            int r5 = r5 * r12
            int r4 = java.lang.Math.min(r4, r5)
            goto L_0x010e
        L_0x0108:
            int r5 = r7.mMaxWidth
            int r4 = java.lang.Math.min(r4, r5)
        L_0x010e:
            int r5 = r7.mMinWidthMode
            r12 = 1
            if (r5 != r12) goto L_0x011f
            int r5 = r7.mMinWidth
            int r13 = r32.getLineHeight()
            int r5 = r5 * r13
            int r4 = java.lang.Math.max(r4, r5)
            goto L_0x0125
        L_0x011f:
            int r5 = r7.mMinWidth
            int r4 = java.lang.Math.max(r4, r5)
        L_0x0125:
            int r5 = r32.getSuggestedMinimumWidth()
            int r4 = java.lang.Math.max(r4, r5)
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r8 != r5) goto L_0x0135
            int r4 = java.lang.Math.min(r10, r4)
        L_0x0135:
            r14 = r0
            r15 = r1
            r6 = r2
            r16 = r3
            r13 = r4
        L_0x013b:
            int r0 = r32.getCompoundPaddingLeft()
            int r0 = r13 - r0
            int r1 = r32.getCompoundPaddingRight()
            int r0 = r0 - r1
            r5 = r0
            boolean r1 = r7.mHorizontallyScrolling
            if (r1 == 0) goto L_0x014d
            r0 = 1048576(0x100000, float:1.469368E-39)
        L_0x014d:
            r4 = r0
            r3 = r4
            android.text.Layout r0 = r7.mHintLayout
            if (r0 != 0) goto L_0x0155
            r0 = r3
            goto L_0x015b
        L_0x0155:
            android.text.Layout r0 = r7.mHintLayout
            int r0 = r0.getWidth()
        L_0x015b:
            r2 = r0
            android.text.Layout r0 = r7.mLayout
            r1 = 0
            if (r0 != 0) goto L_0x0194
            int r0 = r32.getCompoundPaddingLeft()
            int r0 = r13 - r0
            int r17 = r32.getCompoundPaddingRight()
            int r17 = r0 - r17
            r20 = 0
            r0 = r32
            r12 = r1
            r1 = r4
            r12 = r2
            r2 = r3
            r27 = r3
            r3 = r14
            r28 = r4
            r4 = r15
            r29 = r8
            r30 = r10
            r10 = 1
            r8 = r5
            r5 = r17
            r10 = r6
            r31 = r8
            r8 = 1073741824(0x40000000, float:2.0)
            r6 = r20
            r0.makeNewLayout(r1, r2, r3, r4, r5, r6)
            r23 = r27
            r21 = r28
            goto L_0x0232
        L_0x0194:
            r12 = r2
            r27 = r3
            r28 = r4
            r31 = r5
            r29 = r8
            r30 = r10
            r8 = 1073741824(0x40000000, float:2.0)
            r10 = r6
            android.text.Layout r0 = r7.mLayout
            int r0 = r0.getWidth()
            r6 = r28
            if (r0 != r6) goto L_0x01c6
            r5 = r27
            if (r12 != r5) goto L_0x01c8
            android.text.Layout r0 = r7.mLayout
            int r0 = r0.getEllipsizedWidth()
            int r1 = r32.getCompoundPaddingLeft()
            int r1 = r13 - r1
            int r2 = r32.getCompoundPaddingRight()
            int r1 = r1 - r2
            if (r0 == r1) goto L_0x01c4
            goto L_0x01c8
        L_0x01c4:
            r0 = 0
            goto L_0x01c9
        L_0x01c6:
            r5 = r27
        L_0x01c8:
            r0 = 1
        L_0x01c9:
            r17 = r0
            java.lang.CharSequence r0 = r7.mHint
            if (r0 != 0) goto L_0x01e9
            android.text.TextUtils$TruncateAt r0 = r7.mEllipsize
            if (r0 != 0) goto L_0x01e9
            android.text.Layout r0 = r7.mLayout
            int r0 = r0.getWidth()
            if (r6 <= r0) goto L_0x01e9
            android.text.Layout r0 = r7.mLayout
            boolean r0 = r0 instanceof android.text.BoringLayout
            if (r0 != 0) goto L_0x01e7
            if (r16 == 0) goto L_0x01e9
            if (r10 < 0) goto L_0x01e9
            if (r10 > r6) goto L_0x01e9
        L_0x01e7:
            r0 = 1
            goto L_0x01ea
        L_0x01e9:
            r0 = 0
        L_0x01ea:
            r19 = r0
            int r0 = r7.mMaxMode
            int r1 = r7.mOldMaxMode
            if (r0 != r1) goto L_0x01fb
            int r0 = r7.mMaximum
            int r1 = r7.mOldMaximum
            if (r0 == r1) goto L_0x01f9
            goto L_0x01fb
        L_0x01f9:
            r0 = 0
            goto L_0x01fc
        L_0x01fb:
            r0 = 1
        L_0x01fc:
            r20 = r0
            if (r17 != 0) goto L_0x0208
            if (r20 == 0) goto L_0x0203
            goto L_0x0208
        L_0x0203:
            r23 = r5
            r21 = r6
            goto L_0x0232
        L_0x0208:
            if (r20 != 0) goto L_0x0212
            if (r19 == 0) goto L_0x0212
            android.text.Layout r0 = r7.mLayout
            r0.increaseWidthTo(r6)
            goto L_0x0203
        L_0x0212:
            int r0 = r32.getCompoundPaddingLeft()
            int r0 = r13 - r0
            int r1 = r32.getCompoundPaddingRight()
            int r21 = r0 - r1
            r22 = 0
            r0 = r32
            r1 = r6
            r2 = r5
            r3 = r14
            r4 = r15
            r23 = r5
            r5 = r21
            r21 = r6
            r6 = r22
            r0.makeNewLayout(r1, r2, r3, r4, r5, r6)
        L_0x0232:
            if (r9 != r8) goto L_0x0239
            r0 = r11
            r1 = -1
            r7.mDesiredHeightAtMeasure = r1
            goto L_0x024a
        L_0x0239:
            int r0 = r32.getDesiredHeight()
            r1 = r0
            r7.mDesiredHeightAtMeasure = r0
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r9 != r2) goto L_0x0249
            int r0 = java.lang.Math.min(r0, r11)
            goto L_0x024a
        L_0x0249:
            r0 = r1
        L_0x024a:
            int r1 = r32.getCompoundPaddingTop()
            int r1 = r0 - r1
            int r2 = r32.getCompoundPaddingBottom()
            int r1 = r1 - r2
            int r2 = r7.mMaxMode
            r3 = 1
            if (r2 != r3) goto L_0x0270
            android.text.Layout r2 = r7.mLayout
            int r2 = r2.getLineCount()
            int r3 = r7.mMaximum
            if (r2 <= r3) goto L_0x0270
            android.text.Layout r2 = r7.mLayout
            int r3 = r7.mMaximum
            int r2 = r2.getLineTop(r3)
            int r1 = java.lang.Math.min(r1, r2)
        L_0x0270:
            android.text.method.MovementMethod r2 = r7.mMovement
            if (r2 != 0) goto L_0x028c
            android.text.Layout r2 = r7.mLayout
            int r2 = r2.getWidth()
            r3 = r31
            if (r2 > r3) goto L_0x028e
            android.text.Layout r2 = r7.mLayout
            int r2 = r2.getHeight()
            if (r2 <= r1) goto L_0x0287
            goto L_0x028e
        L_0x0287:
            r2 = 0
            r7.scrollTo(r2, r2)
            goto L_0x0291
        L_0x028c:
            r3 = r31
        L_0x028e:
            r32.registerForPreDraw()
        L_0x0291:
            r7.setMeasuredDimension(r13, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.onMeasure(int, int):void");
    }

    private void autoSizeText() {
        int availableWidth;
        if (isAutoSizeEnabled()) {
            if (this.mNeedsAutoSizeText) {
                if (getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
                    if (this.mHorizontallyScrolling) {
                        availableWidth = 1048576;
                    } else {
                        availableWidth = (getMeasuredWidth() - getTotalPaddingLeft()) - getTotalPaddingRight();
                    }
                    int availableHeight = (getMeasuredHeight() - getExtendedPaddingBottom()) - getExtendedPaddingTop();
                    if (availableWidth > 0 && availableHeight > 0) {
                        synchronized (TEMP_RECTF) {
                            TEMP_RECTF.setEmpty();
                            TEMP_RECTF.right = (float) availableWidth;
                            TEMP_RECTF.bottom = (float) availableHeight;
                            float optimalTextSize = (float) findLargestTextSizeWhichFits(TEMP_RECTF);
                            if (optimalTextSize != getTextSize()) {
                                setTextSizeInternal(0, optimalTextSize, false);
                                makeNewLayout(availableWidth, 0, UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.mNeedsAutoSizeText = true;
        }
    }

    private int findLargestTextSizeWhichFits(RectF availableSpace) {
        int sizesCount = this.mAutoSizeTextSizesInPx.length;
        if (sizesCount != 0) {
            int bestSizeIndex = 0;
            int lowIndex = 0 + 1;
            int highIndex = sizesCount - 1;
            while (lowIndex <= highIndex) {
                int sizeToTryIndex = (lowIndex + highIndex) / 2;
                if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[sizeToTryIndex], availableSpace)) {
                    bestSizeIndex = lowIndex;
                    lowIndex = sizeToTryIndex + 1;
                } else {
                    highIndex = sizeToTryIndex - 1;
                    bestSizeIndex = highIndex;
                }
            }
            return this.mAutoSizeTextSizesInPx[bestSizeIndex];
        }
        throw new IllegalStateException("No available text sizes to choose from.");
    }

    private boolean suggestedSizeFitsInSpace(int suggestedSizeInPx, RectF availableSpace) {
        CharSequence text;
        if (this.mTransformed != null) {
            text = this.mTransformed;
        } else {
            text = getText();
        }
        int maxLines = getMaxLines();
        if (this.mTempTextPaint == null) {
            this.mTempTextPaint = new TextPaint();
        } else {
            this.mTempTextPaint.reset();
        }
        this.mTempTextPaint.set(getPaint());
        this.mTempTextPaint.setTextSize((float) suggestedSizeInPx);
        StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(text, 0, text.length(), this.mTempTextPaint, Math.round(availableSpace.right));
        layoutBuilder.setAlignment(getLayoutAlignment()).setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier()).setIncludePad(getIncludeFontPadding()).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).setBreakStrategy(getBreakStrategy()).setHyphenationFrequency(getHyphenationFrequency()).setJustificationMode(getJustificationMode()).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setTextDirection(getTextDirectionHeuristic());
        StaticLayout layout = layoutBuilder.build();
        if ((maxLines == -1 || layout.getLineCount() <= maxLines) && ((float) layout.getHeight()) <= availableSpace.bottom) {
            return true;
        }
        return false;
    }

    private int getDesiredHeight() {
        boolean z = true;
        int desiredHeight = getDesiredHeight(this.mLayout, true);
        Layout layout = this.mHintLayout;
        if (this.mEllipsize == null) {
            z = false;
        }
        return Math.max(desiredHeight, getDesiredHeight(layout, z));
    }

    private int getDesiredHeight(Layout layout, boolean cap) {
        if (layout == null) {
            return 0;
        }
        int desired = layout.getHeight(cap);
        Drawables dr = this.mDrawables;
        if (dr != null) {
            desired = Math.max(Math.max(desired, dr.mDrawableHeightLeft), dr.mDrawableHeightRight);
        }
        int linecount = layout.getLineCount();
        int padding = getCompoundPaddingTop() + getCompoundPaddingBottom();
        int desired2 = desired + padding;
        if (this.mMaxMode != 1) {
            desired2 = Math.min(desired2, this.mMaximum);
        } else if (cap && linecount > this.mMaximum && ((layout instanceof DynamicLayout) || (layout instanceof BoringLayout))) {
            int desired3 = layout.getLineTop(this.mMaximum);
            if (dr != null) {
                desired3 = Math.max(Math.max(desired3, dr.mDrawableHeightLeft), dr.mDrawableHeightRight);
            }
            desired2 = desired3 + padding;
            linecount = this.mMaximum;
        }
        if (this.mMinMode != 1) {
            desired2 = Math.max(desired2, this.mMinimum);
        } else if (linecount < this.mMinimum) {
            desired2 += getLineHeight() * (this.mMinimum - linecount);
        }
        return Math.max(desired2, getSuggestedMinimumHeight());
    }

    private void checkForResize() {
        boolean sizeChanged = false;
        if (this.mLayout != null) {
            if (this.mLayoutParams.width == -2) {
                sizeChanged = true;
                invalidate();
            }
            if (this.mLayoutParams.height == -2) {
                if (getDesiredHeight() != getHeight()) {
                    sizeChanged = true;
                }
            } else if (this.mLayoutParams.height == -1 && this.mDesiredHeightAtMeasure >= 0 && getDesiredHeight() != this.mDesiredHeightAtMeasure) {
                sizeChanged = true;
            }
        }
        if (sizeChanged) {
            requestLayout();
        }
    }

    @UnsupportedAppUsage
    private void checkForRelayout() {
        if ((this.mLayoutParams.width != -2 || (this.mMaxWidthMode == this.mMinWidthMode && this.mMaxWidth == this.mMinWidth)) && ((this.mHint == null || this.mHintLayout != null) && ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight() > 0)) {
            int oldht = this.mLayout.getHeight();
            makeNewLayout(this.mLayout.getWidth(), this.mHintLayout == null ? 0 : this.mHintLayout.getWidth(), UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
            if (this.mEllipsize != TextUtils.TruncateAt.MARQUEE) {
                if (this.mLayoutParams.height != -2 && this.mLayoutParams.height != -1) {
                    autoSizeText();
                    invalidate();
                    return;
                } else if (this.mLayout.getHeight() == oldht && (this.mHintLayout == null || this.mHintLayout.getHeight() == oldht)) {
                    autoSizeText();
                    invalidate();
                    return;
                }
            }
            requestLayout();
            invalidate();
            return;
        }
        nullLayouts();
        requestLayout();
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mDeferScroll >= 0) {
            int curs = this.mDeferScroll;
            this.mDeferScroll = -1;
            bringPointIntoView(Math.min(curs, this.mText.length()));
        }
        autoSizeText();
    }

    private boolean isShowingHint() {
        return TextUtils.isEmpty(this.mText) && !TextUtils.isEmpty(this.mHint);
    }

    @UnsupportedAppUsage
    private boolean bringTextIntoView() {
        int left;
        int scrolly;
        int scrollx;
        Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
        int line = 0;
        if ((this.mGravity & 112) == 80) {
            line = layout.getLineCount() - 1;
        }
        Layout.Alignment a = layout.getParagraphAlignment(line);
        int dir = layout.getParagraphDirection(line);
        int hspace = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int vspace = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int ht = layout.getHeight();
        if (a == Layout.Alignment.ALIGN_NORMAL) {
            a = dir == 1 ? Layout.Alignment.ALIGN_LEFT : Layout.Alignment.ALIGN_RIGHT;
        } else if (a == Layout.Alignment.ALIGN_OPPOSITE) {
            a = dir == 1 ? Layout.Alignment.ALIGN_RIGHT : Layout.Alignment.ALIGN_LEFT;
        }
        if (a == Layout.Alignment.ALIGN_CENTER) {
            int left2 = (int) Math.floor((double) layout.getLineLeft(line));
            int right = (int) Math.ceil((double) layout.getLineRight(line));
            if (right - left2 < hspace) {
                scrollx = ((right + left2) / 2) - (hspace / 2);
            } else if (dir < 0) {
                scrollx = right - hspace;
            } else {
                scrollx = left2;
            }
            left = scrollx;
        } else if (a == Layout.Alignment.ALIGN_RIGHT) {
            left = ((int) Math.ceil((double) layout.getLineRight(line))) - hspace;
        } else {
            left = (int) Math.floor((double) layout.getLineLeft(line));
        }
        if (ht < vspace) {
            scrolly = 0;
        } else if ((this.mGravity & 112) == 80) {
            scrolly = ht - vspace;
        } else {
            scrolly = 0;
        }
        if (left == this.mScrollX && scrolly == this.mScrollY) {
            return false;
        }
        scrollTo(left, scrolly);
        return true;
    }

    public boolean bringPointIntoView(int offset) {
        int grav;
        int vs;
        int i = offset;
        if (isLayoutRequested()) {
            this.mDeferScroll = i;
            return false;
        }
        Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
        if (layout == null) {
            return false;
        }
        int line = layout.getLineForOffset(i);
        switch (layout.getParagraphAlignment(line)) {
            case ALIGN_LEFT:
                grav = 1;
                break;
            case ALIGN_RIGHT:
                grav = -1;
                break;
            case ALIGN_NORMAL:
                grav = layout.getParagraphDirection(line);
                break;
            case ALIGN_OPPOSITE:
                grav = -layout.getParagraphDirection(line);
                break;
            default:
                grav = 0;
                break;
        }
        boolean clamped = grav > 0;
        int x = (int) layout.getPrimaryHorizontal(i, clamped);
        int top = layout.getLineTop(line);
        int bottom = layout.getLineTop(line + 1);
        int left = (int) Math.floor((double) layout.getLineLeft(line));
        int right = (int) Math.ceil((double) layout.getLineRight(line));
        int ht = layout.getHeight();
        int hspace = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int vspace = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        if (!this.mHorizontallyScrolling && right - left > hspace && right > x) {
            right = Math.max(x, left + hspace);
        }
        int hslack = (bottom - top) / 2;
        boolean changed = false;
        int vslack = hslack;
        if (vslack > vspace / 4) {
            vslack = vspace / 4;
        }
        if (hslack > hspace / 4) {
            hslack = hspace / 4;
        }
        int hs = this.mScrollX;
        Layout layout2 = layout;
        int vs2 = this.mScrollY;
        boolean z = clamped;
        if (top - vs2 < vslack) {
            vs2 = top - vslack;
        }
        int vs3 = vs2;
        if (bottom - vs2 > vspace - vslack) {
            vs = bottom - (vspace - vslack);
        } else {
            vs = vs3;
        }
        if (ht - vs < vspace) {
            vs = ht - vspace;
        }
        if (0 - vs > 0) {
            vs = 0;
        }
        if (grav != 0) {
            if (x - hs < hslack) {
                hs = x - hslack;
            }
            int hs2 = hs;
            if (x - hs > hspace - hslack) {
                hs = x - (hspace - hslack);
            } else {
                hs = hs2;
            }
        }
        if (grav < 0) {
            if (left - hs > 0) {
                hs = left;
            }
            if (right - hs < hspace) {
                hs = right - hspace;
            }
        } else if (grav > 0) {
            if (right - hs < hspace) {
                hs = right - hspace;
            }
            if (left - hs > 0) {
                hs = left;
            }
        } else if (right - left <= hspace) {
            hs = left - ((hspace - (right - left)) / 2);
        } else if (x > right - hslack) {
            hs = right - hspace;
        } else if (x < left + hslack) {
            hs = left;
        } else if (left > hs) {
            hs = left;
        } else if (right < hs + hspace) {
            hs = right - hspace;
        } else {
            if (x - hs < hslack) {
                hs = x - hslack;
            }
            int hs3 = hs;
            if (x - hs > hspace - hslack) {
                hs = x - (hspace - hslack);
            } else {
                hs = hs3;
            }
        }
        if (hs == this.mScrollX && vs == this.mScrollY) {
            int i2 = hs;
            int i3 = vslack;
            int i4 = hslack;
            int i5 = vs;
        } else {
            if (this.mScroller == null) {
                scrollTo(hs, vs);
                int i6 = hs;
                int i7 = vslack;
                int i8 = hslack;
                int i9 = vs;
            } else {
                int i10 = vslack;
                int i11 = hslack;
                long duration = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
                int dx = hs - this.mScrollX;
                int dy = vs - this.mScrollY;
                if (duration > 250) {
                    int i12 = hs;
                    int i13 = vs;
                    this.mScroller.startScroll(this.mScrollX, this.mScrollY, dx, dy);
                    awakenScrollBars(this.mScroller.getDuration());
                    invalidate();
                } else {
                    int i14 = vs;
                    if (!this.mScroller.isFinished()) {
                        this.mScroller.abortAnimation();
                    }
                    scrollBy(dx, dy);
                }
                int i15 = dx;
                this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
            }
            changed = true;
        }
        if (!isFocused()) {
            return changed;
        }
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        this.mTempRect.set(x - 2, top, x + 2, bottom);
        getInterestingRect(this.mTempRect, line);
        this.mTempRect.offset(this.mScrollX, this.mScrollY);
        if (requestRectangleOnScreen(this.mTempRect)) {
            return true;
        }
        return changed;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean moveCursorToVisibleOffset() {
        /*
            r18 = this;
            r0 = r18
            java.lang.CharSequence r1 = r0.mText
            boolean r1 = r1 instanceof android.text.Spannable
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            int r1 = r18.getSelectionStart()
            int r3 = r18.getSelectionEnd()
            if (r1 == r3) goto L_0x0015
            return r2
        L_0x0015:
            android.text.Layout r4 = r0.mLayout
            int r4 = r4.getLineForOffset(r1)
            android.text.Layout r5 = r0.mLayout
            int r5 = r5.getLineTop(r4)
            android.text.Layout r6 = r0.mLayout
            int r7 = r4 + 1
            int r6 = r6.getLineTop(r7)
            int r7 = r0.mBottom
            int r8 = r0.mTop
            int r7 = r7 - r8
            int r8 = r18.getExtendedPaddingTop()
            int r7 = r7 - r8
            int r8 = r18.getExtendedPaddingBottom()
            int r7 = r7 - r8
            int r8 = r6 - r5
            int r8 = r8 / 2
            int r9 = r7 / 4
            if (r8 <= r9) goto L_0x0042
            int r8 = r7 / 4
        L_0x0042:
            int r9 = r0.mScrollY
            int r10 = r9 + r8
            if (r5 >= r10) goto L_0x0054
            android.text.Layout r10 = r0.mLayout
            int r11 = r9 + r8
            int r12 = r6 - r5
            int r11 = r11 + r12
            int r4 = r10.getLineForVertical(r11)
            goto L_0x0065
        L_0x0054:
            int r10 = r7 + r9
            int r10 = r10 - r8
            if (r6 <= r10) goto L_0x0065
            android.text.Layout r10 = r0.mLayout
            int r11 = r7 + r9
            int r11 = r11 - r8
            int r12 = r6 - r5
            int r11 = r11 - r12
            int r4 = r10.getLineForVertical(r11)
        L_0x0065:
            int r10 = r0.mRight
            int r11 = r0.mLeft
            int r10 = r10 - r11
            int r11 = r18.getCompoundPaddingLeft()
            int r10 = r10 - r11
            int r11 = r18.getCompoundPaddingRight()
            int r10 = r10 - r11
            int r11 = r0.mScrollX
            android.text.Layout r12 = r0.mLayout
            float r13 = (float) r11
            int r12 = r12.getOffsetForHorizontal(r4, r13)
            android.text.Layout r13 = r0.mLayout
            int r14 = r10 + r11
            float r14 = (float) r14
            int r13 = r13.getOffsetForHorizontal(r4, r14)
            if (r12 >= r13) goto L_0x008a
            r14 = r12
            goto L_0x008b
        L_0x008a:
            r14 = r13
        L_0x008b:
            if (r12 <= r13) goto L_0x008f
            r15 = r12
            goto L_0x0090
        L_0x008f:
            r15 = r13
        L_0x0090:
            r16 = r1
            r2 = r16
            if (r2 >= r14) goto L_0x009b
            r16 = r14
        L_0x0098:
            r2 = r16
            goto L_0x00a0
        L_0x009b:
            if (r2 <= r15) goto L_0x00a0
            r16 = r15
            goto L_0x0098
        L_0x00a0:
            if (r2 == r1) goto L_0x00ab
            r17 = r1
            android.text.Spannable r1 = r0.mSpannable
            android.text.Selection.setSelection(r1, r2)
            r1 = 1
            return r1
        L_0x00ab:
            r17 = r1
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.moveCursorToVisibleOffset():boolean");
    }

    public void computeScroll() {
        if (this.mScroller != null && this.mScroller.computeScrollOffset()) {
            this.mScrollX = this.mScroller.getCurrX();
            this.mScrollY = this.mScroller.getCurrY();
            invalidateParentCaches();
            postInvalidate();
        }
    }

    private void getInterestingRect(Rect r, int line) {
        convertFromViewportToContentCoordinates(r);
        if (line == 0) {
            r.top -= getExtendedPaddingTop();
        }
        if (line == this.mLayout.getLineCount() - 1) {
            r.bottom += getExtendedPaddingBottom();
        }
    }

    private void convertFromViewportToContentCoordinates(Rect r) {
        int horizontalOffset = viewportToContentHorizontalOffset();
        r.left += horizontalOffset;
        r.right += horizontalOffset;
        int verticalOffset = viewportToContentVerticalOffset();
        r.top += verticalOffset;
        r.bottom += verticalOffset;
    }

    /* access modifiers changed from: package-private */
    public int viewportToContentHorizontalOffset() {
        return getCompoundPaddingLeft() - this.mScrollX;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int viewportToContentVerticalOffset() {
        int offset = getExtendedPaddingTop() - this.mScrollY;
        if ((this.mGravity & 112) != 48) {
            return offset + getVerticalOffset(false);
        }
        return offset;
    }

    public void debug(int depth) {
        String output;
        super.debug(depth);
        String output2 = debugIndent(depth) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ";
        if (this.mText != null) {
            output = output2 + "mText=\"" + this.mText + "\" ";
            if (this.mLayout != null) {
                output = output + "mLayout width=" + this.mLayout.getWidth() + " height=" + this.mLayout.getHeight();
            }
        } else {
            output = output2 + "mText=NULL";
        }
        Log.d("View", output);
    }

    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionStart() {
        return Selection.getSelectionStart(getText());
    }

    @ViewDebug.ExportedProperty(category = "text")
    public int getSelectionEnd() {
        return Selection.getSelectionEnd(getText());
    }

    public boolean hasSelection() {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        return selectionStart >= 0 && selectionEnd > 0 && selectionStart != selectionEnd;
    }

    /* access modifiers changed from: package-private */
    public String getSelectedText() {
        if (!hasSelection()) {
            return null;
        }
        int start = getSelectionStart();
        int end = getSelectionEnd();
        return String.valueOf(start > end ? this.mText.subSequence(end, start) : this.mText.subSequence(start, end));
    }

    public void setSingleLine() {
        setSingleLine(true);
    }

    public void setAllCaps(boolean allCaps) {
        if (allCaps) {
            setTransformationMethod(new AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod((TransformationMethod) null);
        }
    }

    public boolean isAllCaps() {
        TransformationMethod method = getTransformationMethod();
        return method != null && (method instanceof AllCapsTransformationMethod);
    }

    @RemotableViewMethod
    public void setSingleLine(boolean singleLine) {
        setInputTypeSingleLine(singleLine);
        applySingleLine(singleLine, true, true);
    }

    private void setInputTypeSingleLine(boolean singleLine) {
        if (this.mEditor != null && (this.mEditor.mInputType & 15) == 1) {
            if (singleLine) {
                this.mEditor.mInputType &= -131073;
                return;
            }
            this.mEditor.mInputType |= 131072;
        }
    }

    private void applySingleLine(boolean singleLine, boolean applyTransformation, boolean changeMaxLines) {
        this.mSingleLine = singleLine;
        if (singleLine) {
            setLines(1);
            setHorizontallyScrolling(true);
            if (applyTransformation) {
                setTransformationMethod(SingleLineTransformationMethod.getInstance());
                return;
            }
            return;
        }
        if (changeMaxLines) {
            setMaxLines(Integer.MAX_VALUE);
        }
        setHorizontallyScrolling(false);
        if (applyTransformation) {
            setTransformationMethod((TransformationMethod) null);
        }
    }

    public void setEllipsize(TextUtils.TruncateAt where) {
        if (this.mEllipsize != where) {
            this.mEllipsize = where;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setMarqueeRepeatLimit(int marqueeLimit) {
        this.mMarqueeRepeatLimit = marqueeLimit;
    }

    public int getMarqueeRepeatLimit() {
        return this.mMarqueeRepeatLimit;
    }

    @ViewDebug.ExportedProperty
    public TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    @RemotableViewMethod
    public void setSelectAllOnFocus(boolean selectAllOnFocus) {
        createEditorIfNeeded();
        this.mEditor.mSelectAllOnFocus = selectAllOnFocus;
        if (selectAllOnFocus && !(this.mText instanceof Spannable)) {
            setText(this.mText, BufferType.SPANNABLE);
        }
    }

    @RemotableViewMethod
    public void setCursorVisible(boolean visible) {
        if (!visible || this.mEditor != null) {
            createEditorIfNeeded();
            if (this.mEditor.mCursorVisible != visible) {
                this.mEditor.mCursorVisible = visible;
                invalidate();
                this.mEditor.makeBlink();
                this.mEditor.prepareCursorControllers();
            }
        }
    }

    public boolean isCursorVisible() {
        if (this.mEditor == null) {
            return true;
        }
        return this.mEditor.mCursorVisible;
    }

    private boolean canMarquee() {
        int width = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (width <= 0) {
            return false;
        }
        if (this.mLayout.getLineWidth(0) > ((float) width) || (this.mMarqueeFadeMode != 0 && this.mSavedMarqueeModeLayout != null && this.mSavedMarqueeModeLayout.getLineWidth(0) > ((float) width))) {
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private void startMarquee() {
        if (getKeyListener() != null || compressText((float) ((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight()))) {
            return;
        }
        if (this.mMarquee != null && !this.mMarquee.isStopped()) {
            return;
        }
        if ((isFocused() || isSelected()) && getLineCount() == 1 && canMarquee()) {
            if (this.mMarqueeFadeMode == 1) {
                this.mMarqueeFadeMode = 2;
                Layout tmp = this.mLayout;
                this.mLayout = this.mSavedMarqueeModeLayout;
                this.mSavedMarqueeModeLayout = tmp;
                setHorizontalFadingEdgeEnabled(true);
                requestLayout();
                invalidate();
            }
            if (this.mMarquee == null) {
                this.mMarquee = new Marquee(this);
            }
            this.mMarquee.start(this.mMarqueeRepeatLimit);
        }
    }

    private void stopMarquee() {
        if (this.mMarquee != null && !this.mMarquee.isStopped()) {
            this.mMarquee.stop();
        }
        if (this.mMarqueeFadeMode == 2) {
            this.mMarqueeFadeMode = 1;
            Layout tmp = this.mSavedMarqueeModeLayout;
            this.mSavedMarqueeModeLayout = this.mLayout;
            this.mLayout = tmp;
            setHorizontalFadingEdgeEnabled(false);
            requestLayout();
            invalidate();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private void startStopMarquee(boolean start) {
        if (this.mEllipsize != TextUtils.TruncateAt.MARQUEE) {
            return;
        }
        if (start) {
            startMarquee();
        } else {
            stopMarquee();
        }
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
    }

    /* access modifiers changed from: protected */
    public void onSelectionChanged(int selStart, int selEnd) {
        sendAccessibilityEvent(8192);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(watcher);
    }

    public void removeTextChangedListener(TextWatcher watcher) {
        int i;
        if (this.mListeners != null && (i = this.mListeners.indexOf(watcher)) >= 0) {
            this.mListeners.remove(i);
        }
    }

    /* access modifiers changed from: private */
    public void sendBeforeTextChanged(CharSequence text, int start, int before, int after) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> list = this.mListeners;
            int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).beforeTextChanged(text, start, before, after);
            }
        }
        removeIntersectingNonAdjacentSpans(start, start + before, SpellCheckSpan.class);
        removeIntersectingNonAdjacentSpans(start, start + before, SuggestionSpan.class);
    }

    private <T> void removeIntersectingNonAdjacentSpans(int start, int end, Class<T> type) {
        if (this.mText instanceof Editable) {
            Editable text = (Editable) this.mText;
            T[] spans = text.getSpans(start, end, type);
            int length = spans.length;
            int i = 0;
            while (i < length) {
                int spanStart = text.getSpanStart(spans[i]);
                if (text.getSpanEnd(spans[i]) != start && spanStart != end) {
                    text.removeSpan(spans[i]);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeAdjacentSuggestionSpans(int pos) {
        if (this.mText instanceof Editable) {
            Editable text = (Editable) this.mText;
            SuggestionSpan[] spans = (SuggestionSpan[]) text.getSpans(pos, pos, SuggestionSpan.class);
            int length = spans.length;
            for (int i = 0; i < length; i++) {
                int spanStart = text.getSpanStart(spans[i]);
                int spanEnd = text.getSpanEnd(spans[i]);
                if ((spanEnd == pos || spanStart == pos) && SpellChecker.haveWordBoundariesChanged(text, pos, pos, spanStart, spanEnd)) {
                    text.removeSpan(spans[i]);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void sendOnTextChanged(CharSequence text, int start, int before, int after) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> list = this.mListeners;
            int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).onTextChanged(text, start, before, after);
            }
        }
        if (this.mEditor != null) {
            this.mEditor.sendOnTextChanged(start, before, after);
        }
    }

    /* access modifiers changed from: package-private */
    public void sendAfterTextChanged(Editable text) {
        if (this.mListeners != null) {
            ArrayList<TextWatcher> list = this.mListeners;
            int count = list.size();
            for (int i = 0; i < count; i++) {
                list.get(i).afterTextChanged(text);
            }
        }
        notifyListeningManagersAfterTextChanged();
        hideErrorIfUnchanged();
    }

    private void notifyListeningManagersAfterTextChanged() {
        AutofillManager afm;
        if (isAutofillable() && (afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) != null) {
            if (Helper.sVerbose) {
                Log.v(LOG_TAG, "notifyAutoFillManagerAfterTextChanged");
            }
            afm.notifyValueChanged(this);
        }
    }

    private boolean isAutofillable() {
        return getAutofillType() != 0;
    }

    /* access modifiers changed from: package-private */
    public void updateAfterEdit() {
        invalidate();
        int curs = getSelectionStart();
        if (curs >= 0 || (this.mGravity & 112) == 80) {
            registerForPreDraw();
        }
        checkForResize();
        if (curs >= 0) {
            this.mHighlightPathBogus = true;
            if (this.mEditor != null) {
                this.mEditor.makeBlink();
            }
            bringPointIntoView(curs);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleTextChanged(CharSequence buffer, int start, int before, int after) {
        sLastCutCopyOrTextChangedTime = 0;
        Editor.InputMethodState ims = this.mEditor == null ? null : this.mEditor.mInputMethodState;
        if (ims == null || ims.mBatchEditNesting == 0) {
            updateAfterEdit();
        }
        if (ims != null) {
            ims.mContentChanged = true;
            if (ims.mChangedStart < 0) {
                ims.mChangedStart = start;
                ims.mChangedEnd = start + before;
            } else {
                ims.mChangedStart = Math.min(ims.mChangedStart, start);
                ims.mChangedEnd = Math.max(ims.mChangedEnd, (start + before) - ims.mChangedDelta);
            }
            ims.mChangedDelta += after - before;
        }
        resetErrorChangedFlag();
        sendOnTextChanged(buffer, start, before, after);
        onTextChanged(buffer, start, before, after);
    }

    /* access modifiers changed from: package-private */
    public void spanChange(Spanned buf, Object what, int oldStart, int newStart, int oldEnd, int newEnd) {
        boolean selChanged = false;
        int newSelStart = -1;
        int newSelEnd = -1;
        Editor.InputMethodState ims = this.mEditor == null ? null : this.mEditor.mInputMethodState;
        if (what == Selection.SELECTION_END) {
            selChanged = true;
            newSelEnd = newStart;
            if (oldStart >= 0 || newStart >= 0) {
                invalidateCursor(Selection.getSelectionStart(buf), oldStart, newStart);
                checkForResize();
                registerForPreDraw();
                if (this.mEditor != null) {
                    this.mEditor.makeBlink();
                }
            }
        }
        if (what == Selection.SELECTION_START) {
            selChanged = true;
            newSelStart = newStart;
            if (oldStart >= 0 || newStart >= 0) {
                invalidateCursor(Selection.getSelectionEnd(buf), oldStart, newStart);
            }
        }
        if (selChanged) {
            this.mHighlightPathBogus = true;
            if (this.mEditor != null && !isFocused()) {
                this.mEditor.mSelectionMoved = true;
            }
            if ((buf.getSpanFlags(what) & 512) == 0) {
                if (newSelStart < 0) {
                    newSelStart = Selection.getSelectionStart(buf);
                }
                if (newSelEnd < 0) {
                    newSelEnd = Selection.getSelectionEnd(buf);
                }
                if (this.mEditor != null) {
                    this.mEditor.refreshTextActionMode();
                    if (!hasSelection() && this.mEditor.getTextActionMode() == null && hasTransientState()) {
                        setHasTransientState(false);
                    }
                }
                onSelectionChanged(newSelStart, newSelEnd);
            }
        }
        if ((what instanceof UpdateAppearance) || (what instanceof ParagraphStyle) || (what instanceof CharacterStyle)) {
            if (ims == null || ims.mBatchEditNesting == 0) {
                invalidate();
                this.mHighlightPathBogus = true;
                checkForResize();
            } else {
                ims.mContentChanged = true;
            }
            if (this.mEditor != null) {
                if (oldStart >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, oldStart, oldEnd);
                }
                if (newStart >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, newStart, newEnd);
                }
                this.mEditor.invalidateHandlesAndActionMode();
            }
        }
        if (MetaKeyKeyListener.isMetaTracker(buf, what)) {
            this.mHighlightPathBogus = true;
            if (ims != null && MetaKeyKeyListener.isSelectingMetaTracker(buf, what)) {
                ims.mSelectionModeChanged = true;
            }
            if (Selection.getSelectionStart(buf) >= 0) {
                if (ims == null || ims.mBatchEditNesting == 0) {
                    invalidateCursor();
                } else {
                    ims.mCursorChanged = true;
                }
            }
        }
        if (!(!(what instanceof ParcelableSpan) || ims == null || ims.mExtractedTextRequest == null)) {
            if (ims.mBatchEditNesting != 0) {
                if (oldStart >= 0) {
                    if (ims.mChangedStart > oldStart) {
                        ims.mChangedStart = oldStart;
                    }
                    if (ims.mChangedStart > oldEnd) {
                        ims.mChangedStart = oldEnd;
                    }
                }
                if (newStart >= 0) {
                    if (ims.mChangedStart > newStart) {
                        ims.mChangedStart = newStart;
                    }
                    if (ims.mChangedStart > newEnd) {
                        ims.mChangedStart = newEnd;
                    }
                }
            } else {
                ims.mContentChanged = true;
            }
        }
        if (this.mEditor != null && this.mEditor.mSpellChecker != null && newStart < 0 && (what instanceof SpellCheckSpan)) {
            this.mEditor.mSpellChecker.onSpellCheckSpanRemoved((SpellCheckSpan) what);
        }
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (isTemporarilyDetached()) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            return;
        }
        if (this.mEditor != null) {
            this.mEditor.onFocusChanged(focused, direction);
        }
        if (focused && this.mSpannable != null) {
            MetaKeyKeyListener.resetMetaState(this.mSpannable);
        }
        startStopMarquee(focused);
        if (this.mTransformation != null) {
            this.mTransformation.onFocusChanged(this, this.mText, focused, direction, previouslyFocusedRect);
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (this.mEditor != null) {
            this.mEditor.onWindowFocusChanged(hasWindowFocus);
        }
        startStopMarquee(hasWindowFocus);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (this.mEditor != null && visibility != 0) {
            this.mEditor.hideCursorAndSpanControllers();
            stopTextActionMode();
        }
    }

    public void clearComposingText() {
        if (this.mText instanceof Spannable) {
            BaseInputConnection.removeComposingSpans(this.mSpannable);
        }
    }

    public void setSelected(boolean selected) {
        boolean wasSelected = isSelected();
        super.setSelected(selected);
        if (selected != wasSelected && this.mEllipsize == TextUtils.TruncateAt.MARQUEE) {
            if (selected) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if (this.mEditor != null) {
            this.mEditor.onTouchEvent(event);
            if (this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isDragAcceleratorActive()) {
                return true;
            }
        }
        boolean superResult = super.onTouchEvent(event);
        if (this.mEditor == null || !this.mEditor.mDiscardNextActionUp || action != 1) {
            boolean touchIsFinished = action == 1 && (this.mEditor == null || !this.mEditor.mIgnoreActionUpEvent) && isFocused();
            if ((this.mMovement != null || onCheckIsTextEditor()) && isEnabled() && (this.mText instanceof Spannable) && this.mLayout != null) {
                boolean handled = false;
                if (this.mMovement != null) {
                    handled = false | this.mMovement.onTouchEvent(this, this.mSpannable, event);
                }
                boolean textIsSelectable = isTextSelectable();
                if (touchIsFinished && this.mLinksClickable && this.mAutoLinkMask != 0 && textIsSelectable) {
                    ClickableSpan[] links = (ClickableSpan[]) this.mSpannable.getSpans(getSelectionStart(), getSelectionEnd(), ClickableSpan.class);
                    if (links.length > 0) {
                        links[0].onClick(this);
                        handled = true;
                    }
                }
                if (touchIsFinished && (isTextEditable() || textIsSelectable)) {
                    InputMethodManager imm = getInputMethodManager();
                    viewClicked(imm);
                    if (isTextEditable() && this.mEditor.mShowSoftInputOnFocus && imm != null) {
                        imm.showSoftInput(this, 0);
                    }
                    this.mEditor.onTouchUpEvent(event);
                    handled = true;
                }
                if (handled) {
                    return true;
                }
            }
            return superResult;
        }
        this.mEditor.mDiscardNextActionUp = false;
        if (this.mEditor.mIsInsertionActionModeStartPending) {
            this.mEditor.startInsertionActionMode();
            this.mEditor.mIsInsertionActionModeStartPending = false;
        }
        return superResult;
    }

    public boolean onGenericMotionEvent(MotionEvent event) {
        if (!(this.mMovement == null || !(this.mText instanceof Spannable) || this.mLayout == null)) {
            try {
                if (this.mMovement.onGenericMotionEvent(this, this.mSpannable, event)) {
                    return true;
                }
            } catch (AbstractMethodError e) {
            }
        }
        return super.onGenericMotionEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onCreateContextMenu(ContextMenu menu) {
        if (this.mEditor != null) {
            this.mEditor.onCreateContextMenu(menu);
        }
    }

    public boolean showContextMenu() {
        if (this.mEditor != null) {
            this.mEditor.setContextMenuAnchor(Float.NaN, Float.NaN);
        }
        return super.showContextMenu();
    }

    public boolean showContextMenu(float x, float y) {
        if (this.mEditor != null) {
            this.mEditor.setContextMenuAnchor(x, y);
        }
        return super.showContextMenu(x, y);
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean isTextEditable() {
        return (this.mText instanceof Editable) && onCheckIsTextEditor() && isEnabled();
    }

    public boolean didTouchFocusSelect() {
        return this.mEditor != null && this.mEditor.mTouchFocusSelected;
    }

    public void cancelLongPress() {
        super.cancelLongPress();
        if (this.mEditor != null) {
            this.mEditor.mIgnoreActionUpEvent = true;
        }
    }

    public boolean onTrackballEvent(MotionEvent event) {
        if (this.mMovement == null || this.mSpannable == null || this.mLayout == null || !this.mMovement.onTrackballEvent(this, this.mSpannable, event)) {
            return super.onTrackballEvent(event);
        }
        return true;
    }

    public void setScroller(Scroller s) {
        this.mScroller = s;
    }

    /* access modifiers changed from: protected */
    public float getLeftFadingEdgeStrength() {
        if (isMarqueeFadeEnabled() && this.mMarquee != null && !this.mMarquee.isStopped()) {
            Marquee marquee = this.mMarquee;
            if (marquee.shouldDrawLeftFade()) {
                return getHorizontalFadingEdgeStrength(marquee.getScroll(), 0.0f);
            }
            return 0.0f;
        } else if (getLineCount() != 1) {
            return super.getLeftFadingEdgeStrength();
        } else {
            float lineLeft = getLayout().getLineLeft(0);
            if (lineLeft > ((float) this.mScrollX)) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength((float) this.mScrollX, lineLeft);
        }
    }

    /* access modifiers changed from: protected */
    public float getRightFadingEdgeStrength() {
        if (isMarqueeFadeEnabled() && this.mMarquee != null && !this.mMarquee.isStopped()) {
            Marquee marquee = this.mMarquee;
            return getHorizontalFadingEdgeStrength(marquee.getMaxFadeScroll(), marquee.getScroll());
        } else if (getLineCount() != 1) {
            return super.getRightFadingEdgeStrength();
        } else {
            float rightEdge = (float) (this.mScrollX + ((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight()));
            float lineRight = getLayout().getLineRight(0);
            if (lineRight < rightEdge) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength(rightEdge, lineRight);
        }
    }

    private float getHorizontalFadingEdgeStrength(float position1, float position2) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (horizontalFadingEdgeLength == 0) {
            return 0.0f;
        }
        float diff = Math.abs(position1 - position2);
        if (diff > ((float) horizontalFadingEdgeLength)) {
            return 1.0f;
        }
        return diff / ((float) horizontalFadingEdgeLength);
    }

    private boolean isMarqueeFadeEnabled() {
        return this.mEllipsize == TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 1;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollRange() {
        if (this.mLayout != null) {
            return (!this.mSingleLine || (this.mGravity & 7) != 3) ? this.mLayout.getWidth() : (int) this.mLayout.getLineWidth(0);
        }
        return super.computeHorizontalScrollRange();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollRange() {
        if (this.mLayout != null) {
            return this.mLayout.getHeight();
        }
        return super.computeVerticalScrollRange();
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollExtent() {
        return (getHeight() - getCompoundPaddingTop()) - getCompoundPaddingBottom();
    }

    public void findViewsWithText(ArrayList<View> outViews, CharSequence searched, int flags) {
        super.findViewsWithText(outViews, searched, flags);
        if (!outViews.contains(this) && (flags & 1) != 0 && !TextUtils.isEmpty(searched) && !TextUtils.isEmpty(this.mText)) {
            if (this.mText.toString().toLowerCase().contains(searched.toString().toLowerCase())) {
                outViews.add(this);
            }
        }
    }

    public static ColorStateList getTextColors(Context context, TypedArray attrs) {
        int ap;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(R.styleable.TextView);
            ColorStateList colors = a.getColorStateList(5);
            if (colors == null && (ap = a.getResourceId(1, 0)) != 0) {
                TypedArray appearance = context.obtainStyledAttributes(ap, R.styleable.TextAppearance);
                colors = appearance.getColorStateList(3);
                appearance.recycle();
            }
            a.recycle();
            return colors;
        }
        throw new NullPointerException();
    }

    public static int getTextColor(Context context, TypedArray attrs, int def) {
        ColorStateList colors = getTextColors(context, attrs);
        if (colors == null) {
            return def;
        }
        return colors.getDefaultColor();
    }

    public boolean onKeyShortcut(int keyCode, KeyEvent event) {
        if (event.hasModifiers(4096)) {
            if (keyCode != 29) {
                if (keyCode != 31) {
                    if (keyCode != 50) {
                        if (keyCode != 52) {
                            if (keyCode == 54 && canUndo()) {
                                return onTextContextMenuItem(16908338);
                            }
                        } else if (canCut()) {
                            return onTextContextMenuItem(16908320);
                        }
                    } else if (canPaste()) {
                        return onTextContextMenuItem(16908322);
                    }
                } else if (canCopy()) {
                    return onTextContextMenuItem(16908321);
                }
            } else if (canSelectText()) {
                return onTextContextMenuItem(16908319);
            }
        } else if (event.hasModifiers(4097)) {
            if (keyCode != 50) {
                if (keyCode == 54 && canRedo()) {
                    return onTextContextMenuItem(16908339);
                }
            } else if (canPaste()) {
                return onTextContextMenuItem(16908337);
            }
        }
        return super.onKeyShortcut(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    public boolean canSelectText() {
        return (this.mText.length() == 0 || this.mEditor == null || !this.mEditor.hasSelectionController()) ? false : true;
    }

    /* access modifiers changed from: package-private */
    public boolean textCanBeSelected() {
        if (this.mMovement == null || !this.mMovement.canSelectArbitrarily()) {
            return false;
        }
        if (isTextEditable() || (isTextSelectable() && (this.mText instanceof Spannable) && isEnabled())) {
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage
    private Locale getTextServicesLocale(boolean allowNullLocale) {
        updateTextServicesLocaleAsync();
        if (this.mCurrentSpellCheckerLocaleCache != null || allowNullLocale) {
            return this.mCurrentSpellCheckerLocaleCache;
        }
        return Locale.getDefault();
    }

    public final void setTextOperationUser(UserHandle user) {
        if (!Objects.equals(this.mTextOperationUser, user)) {
            if (user == null || Process.myUserHandle().equals(user) || getContext().checkSelfPermission(Manifest.permission.INTERACT_ACROSS_USERS_FULL) == 0) {
                this.mTextOperationUser = user;
                this.mCurrentSpellCheckerLocaleCache = null;
                if (this.mEditor != null) {
                    this.mEditor.onTextOperationUserChanged();
                    return;
                }
                return;
            }
            throw new SecurityException("INTERACT_ACROSS_USERS_FULL is required. userId=" + user.getIdentifier() + " callingUserId" + UserHandle.myUserId());
        }
    }

    /* access modifiers changed from: package-private */
    public final TextServicesManager getTextServicesManagerForUser() {
        return (TextServicesManager) getServiceManagerForUser("android", TextServicesManager.class);
    }

    /* access modifiers changed from: package-private */
    public final ClipboardManager getClipboardManagerForUser() {
        return (ClipboardManager) getServiceManagerForUser(getContext().getPackageName(), ClipboardManager.class);
    }

    /* access modifiers changed from: package-private */
    public final <T> T getServiceManagerForUser(String packageName, Class<T> managerClazz) {
        if (this.mTextOperationUser == null) {
            return getContext().getSystemService(managerClazz);
        }
        try {
            return getContext().createPackageContextAsUser(packageName, 0, this.mTextOperationUser).getSystemService(managerClazz);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void startActivityAsTextOperationUserIfNecessary(Intent intent) {
        if (this.mTextOperationUser != null) {
            getContext().startActivityAsUser(intent, this.mTextOperationUser);
        } else {
            getContext().startActivity(intent);
        }
    }

    public Locale getTextServicesLocale() {
        return getTextServicesLocale(false);
    }

    public boolean isInExtractedMode() {
        return false;
    }

    private boolean isAutoSizeEnabled() {
        return supportsAutoSizeText() && this.mAutoSizeTextType != 0;
    }

    /* access modifiers changed from: protected */
    public boolean supportsAutoSizeText() {
        return true;
    }

    public Locale getSpellCheckerLocale() {
        return getTextServicesLocale(true);
    }

    private void updateTextServicesLocaleAsync() {
        AsyncTask.execute((Runnable) new Runnable() {
            public void run() {
                TextView.this.updateTextServicesLocaleLocked();
            }
        });
    }

    /* access modifiers changed from: private */
    @UnsupportedAppUsage
    public void updateTextServicesLocaleLocked() {
        Locale locale;
        TextServicesManager textServicesManager = getTextServicesManagerForUser();
        if (textServicesManager != null) {
            SpellCheckerSubtype subtype = textServicesManager.getCurrentSpellCheckerSubtype(true);
            if (subtype != null) {
                locale = subtype.getLocaleObject();
            } else {
                locale = null;
            }
            this.mCurrentSpellCheckerLocaleCache = locale;
        }
    }

    /* access modifiers changed from: package-private */
    public void onLocaleChanged() {
        this.mEditor.onLocaleChanged();
    }

    public WordIterator getWordIterator() {
        if (this.mEditor != null) {
            return this.mEditor.getWordIterator();
        }
        return null;
    }

    public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
        super.onPopulateAccessibilityEventInternal(event);
        CharSequence text = getTextForAccessibility();
        if (!TextUtils.isEmpty(text)) {
            event.getText().add(text);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return TextView.class.getName();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0116, code lost:
        if (r4 < r8.length()) goto L_0x011b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onProvideStructure(android.view.ViewStructure r27, int r28, int r29) {
        /*
            r26 = this;
            r1 = r26
            r2 = r27
            r3 = r28
            super.onProvideStructure(r27, r28, r29)
            boolean r0 = r26.hasPasswordTransformationMethod()
            r5 = 1
            if (r0 != 0) goto L_0x001d
            int r0 = r26.getInputType()
            boolean r0 = isPasswordInputType(r0)
            if (r0 == 0) goto L_0x001b
            goto L_0x001d
        L_0x001b:
            r0 = 0
            goto L_0x001e
        L_0x001d:
            r0 = r5
        L_0x001e:
            r6 = r0
            if (r3 != r5) goto L_0x0065
            if (r3 != r5) goto L_0x0029
            boolean r0 = r1.mTextSetFromXmlOrResourceId
            r0 = r0 ^ r5
            r2.setDataIsSensitive(r0)
        L_0x0029:
            int r0 = r1.mTextId
            if (r0 == 0) goto L_0x0065
            android.content.res.Resources r0 = r26.getResources()     // Catch:{ NotFoundException -> 0x003b }
            int r7 = r1.mTextId     // Catch:{ NotFoundException -> 0x003b }
            java.lang.String r0 = r0.getResourceEntryName(r7)     // Catch:{ NotFoundException -> 0x003b }
            r2.setTextIdEntry(r0)     // Catch:{ NotFoundException -> 0x003b }
            goto L_0x0065
        L_0x003b:
            r0 = move-exception
            boolean r7 = android.view.autofill.Helper.sVerbose
            if (r7 == 0) goto L_0x0065
            java.lang.String r7 = "TextView"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "onProvideAutofillStructure(): cannot set name for text id "
            r8.append(r9)
            int r9 = r1.mTextId
            r8.append(r9)
            java.lang.String r9 = ": "
            r8.append(r9)
            java.lang.String r9 = r0.getMessage()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.util.Log.v(r7, r8)
        L_0x0065:
            if (r6 == 0) goto L_0x006e
            if (r3 != r5) goto L_0x006a
            goto L_0x006e
        L_0x006a:
            r18 = r6
            goto L_0x01cb
        L_0x006e:
            android.text.Layout r0 = r1.mLayout
            if (r0 != 0) goto L_0x0075
            r26.assumeLayout()
        L_0x0075:
            android.text.Layout r0 = r1.mLayout
            int r7 = r0.getLineCount()
            if (r7 > r5) goto L_0x0099
            java.lang.CharSequence r8 = r26.getText()
            if (r3 != r5) goto L_0x0087
            r2.setText(r8)
            goto L_0x0092
        L_0x0087:
            int r9 = r26.getSelectionStart()
            int r10 = r26.getSelectionEnd()
            r2.setText(r8, r9, r10)
        L_0x0092:
            r18 = r6
            r19 = r7
            goto L_0x0165
        L_0x0099:
            r8 = 2
            int[] r9 = new int[r8]
            r1.getLocationInWindow(r9)
            r10 = r9[r5]
            r11 = r26
            android.view.ViewParent r12 = r26.getParent()
        L_0x00a7:
            boolean r13 = r12 instanceof android.view.View
            if (r13 == 0) goto L_0x00b3
            r11 = r12
            android.view.View r11 = (android.view.View) r11
            android.view.ViewParent r12 = r11.getParent()
            goto L_0x00a7
        L_0x00b3:
            int r13 = r11.getHeight()
            if (r10 < 0) goto L_0x00c6
            r14 = 0
            int r14 = r1.getLineAtCoordinateUnclamped(r14)
            int r15 = r13 + -1
            float r15 = (float) r15
            int r15 = r1.getLineAtCoordinateUnclamped(r15)
            goto L_0x00d4
        L_0x00c6:
            int r14 = -r10
            float r14 = (float) r14
            int r14 = r1.getLineAtCoordinateUnclamped(r14)
            int r15 = r13 + -1
            int r15 = r15 - r10
            float r15 = (float) r15
            int r15 = r1.getLineAtCoordinateUnclamped(r15)
        L_0x00d4:
            int r16 = r15 - r14
            int r16 = r16 / 2
            int r16 = r14 - r16
            if (r16 >= 0) goto L_0x00de
            r16 = 0
        L_0x00de:
            r4 = r16
            int r16 = r15 - r14
            int r16 = r16 / 2
            int r8 = r15 + r16
            if (r8 < r7) goto L_0x00ea
            int r8 = r7 + -1
        L_0x00ea:
            int r5 = r0.getLineStart(r4)
            r17 = r4
            int r4 = r0.getLineEnd(r8)
            r18 = r6
            int r6 = r26.getSelectionStart()
            r19 = r7
            int r7 = r26.getSelectionEnd()
            if (r6 >= r7) goto L_0x0108
            if (r6 >= r5) goto L_0x0105
            r5 = r6
        L_0x0105:
            if (r7 <= r4) goto L_0x0108
            r4 = r7
        L_0x0108:
            r20 = r8
            java.lang.CharSequence r8 = r26.getText()
            if (r5 > 0) goto L_0x0119
            r21 = r9
            int r9 = r8.length()
            if (r4 >= r9) goto L_0x011f
            goto L_0x011b
        L_0x0119:
            r21 = r9
        L_0x011b:
            java.lang.CharSequence r8 = r8.subSequence(r5, r4)
        L_0x011f:
            r9 = 1
            if (r3 != r9) goto L_0x0126
            r2.setText(r8)
            goto L_0x0165
        L_0x0126:
            int r9 = r6 - r5
            r22 = r4
            int r4 = r7 - r5
            r2.setText(r8, r9, r4)
            int r4 = r15 - r14
            r9 = 1
            int r4 = r4 + r9
            int[] r4 = new int[r4]
            int r16 = r15 - r14
            r23 = r5
            int r5 = r16 + 1
            int[] r5 = new int[r5]
            int r9 = r26.getBaselineOffset()
            r16 = r14
        L_0x0143:
            r24 = r16
            r25 = r6
            r6 = r24
            if (r6 > r15) goto L_0x0162
            int r24 = r6 - r14
            int r16 = r0.getLineStart(r6)
            r4[r24] = r16
            int r24 = r6 - r14
            int r16 = r0.getLineBaseline(r6)
            int r16 = r16 + r9
            r5[r24] = r16
            int r16 = r6 + 1
            r6 = r25
            goto L_0x0143
        L_0x0162:
            r2.setTextLines(r4, r5)
        L_0x0165:
            if (r3 != 0) goto L_0x019d
            r4 = 0
            int r5 = r26.getTypefaceStyle()
            r6 = r5 & 1
            if (r6 == 0) goto L_0x0172
            r4 = r4 | 1
        L_0x0172:
            r6 = r5 & 2
            if (r6 == 0) goto L_0x0178
            r4 = r4 | 2
        L_0x0178:
            android.text.TextPaint r6 = r1.mTextPaint
            int r6 = r6.getFlags()
            r7 = r6 & 32
            if (r7 == 0) goto L_0x0184
            r4 = r4 | 1
        L_0x0184:
            r7 = r6 & 8
            if (r7 == 0) goto L_0x018a
            r4 = r4 | 4
        L_0x018a:
            r7 = r6 & 16
            if (r7 == 0) goto L_0x0190
            r4 = r4 | 8
        L_0x0190:
            float r7 = r26.getTextSize()
            int r8 = r26.getCurrentTextColor()
            r9 = 1
            r2.setTextStyle(r7, r8, r9, r4)
            goto L_0x019e
        L_0x019d:
            r9 = 1
        L_0x019e:
            if (r3 != r9) goto L_0x01cb
            int r4 = r26.getMinEms()
            r2.setMinTextEms(r4)
            int r4 = r26.getMaxEms()
            r2.setMaxTextEms(r4)
            r4 = -1
            android.text.InputFilter[] r5 = r26.getFilters()
            int r6 = r5.length
            r7 = 0
        L_0x01b5:
            if (r7 >= r6) goto L_0x01c8
            r8 = r5[r7]
            boolean r9 = r8 instanceof android.text.InputFilter.LengthFilter
            if (r9 == 0) goto L_0x01c5
            r5 = r8
            android.text.InputFilter$LengthFilter r5 = (android.text.InputFilter.LengthFilter) r5
            int r4 = r5.getMax()
            goto L_0x01c8
        L_0x01c5:
            int r7 = r7 + 1
            goto L_0x01b5
        L_0x01c8:
            r2.setMaxTextLength(r4)
        L_0x01cb:
            java.lang.CharSequence r0 = r26.getHint()
            r2.setHint(r0)
            int r0 = r26.getInputType()
            r2.setInputType(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.onProvideStructure(android.view.ViewStructure, int, int):void");
    }

    /* access modifiers changed from: package-private */
    public boolean canRequestAutofill() {
        AutofillManager afm;
        if (isAutofillable() && (afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) != null) {
            return afm.isEnabled();
        }
        return false;
    }

    private void requestAutofill() {
        AutofillManager afm = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (afm != null) {
            afm.requestAutofill(this);
        }
    }

    public void autofill(AutofillValue value) {
        if (!value.isText() || !isTextEditable()) {
            Log.w(LOG_TAG, value + " could not be autofilled into " + this);
            return;
        }
        setText(value.getTextValue(), this.mBufferType, true, 0);
        CharSequence text = getText();
        if (text instanceof Spannable) {
            Selection.setSelection((Spannable) text, text.length());
        }
    }

    public int getAutofillType() {
        return isTextEditable() ? 1 : 0;
    }

    public AutofillValue getAutofillValue() {
        if (isTextEditable()) {
            return AutofillValue.forText(TextUtils.trimToParcelableSize(getText()));
        }
        return null;
    }

    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        super.onInitializeAccessibilityEventInternal(event);
        event.setPassword(hasPasswordTransformationMethod());
        if (event.getEventType() == 8192) {
            event.setFromIndex(Selection.getSelectionStart(this.mText));
            event.setToIndex(Selection.getSelectionEnd(this.mText));
            event.setItemCount(this.mText.length());
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        info.setPassword(hasPasswordTransformationMethod());
        info.setText(getTextForAccessibility());
        info.setHintText(this.mHint);
        info.setShowingHintText(isShowingHint());
        if (this.mBufferType == BufferType.EDITABLE) {
            info.setEditable(true);
            if (isEnabled()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
            }
        }
        if (this.mEditor != null) {
            info.setInputType(this.mEditor.mInputType);
            if (this.mEditor.mError != null) {
                info.setContentInvalid(true);
                info.setError(this.mEditor.mError);
            }
        }
        if (!TextUtils.isEmpty(this.mText)) {
            info.addAction(256);
            info.addAction(512);
            info.setMovementGranularities(31);
            info.addAction(131072);
            info.setAvailableExtraData(Arrays.asList(new String[]{AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY}));
        }
        if (isFocused()) {
            if (canCopy()) {
                info.addAction(16384);
            }
            if (canPaste()) {
                info.addAction(32768);
            }
            if (canCut()) {
                info.addAction(65536);
            }
            if (canShare()) {
                info.addAction(new AccessibilityNodeInfo.AccessibilityAction(268435456, getResources().getString(com.android.internal.R.string.share)));
            }
            if (canProcessText()) {
                this.mEditor.mProcessTextIntentActionsHandler.onInitializeAccessibilityNodeInfo(info);
            }
        }
        for (InputFilter filter : this.mFilters) {
            if (filter instanceof InputFilter.LengthFilter) {
                info.setMaxTextLength(((InputFilter.LengthFilter) filter).getMax());
            }
        }
        if (isSingleLine() == 0) {
            info.setMultiLine(true);
        }
    }

    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo info, String extraDataKey, Bundle arguments) {
        RectF bounds;
        if (arguments != null && extraDataKey.equals(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY)) {
            int positionInfoStartIndex = arguments.getInt(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX, -1);
            int positionInfoLength = arguments.getInt(AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1);
            if (positionInfoLength <= 0 || positionInfoStartIndex < 0 || positionInfoStartIndex >= this.mText.length()) {
                Log.e(LOG_TAG, "Invalid arguments for accessibility character locations");
                return;
            }
            RectF[] boundingRects = new RectF[positionInfoLength];
            CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
            CursorAnchorInfo.Builder builder2 = builder;
            int i = positionInfoStartIndex;
            populateCharacterBounds(builder2, i, positionInfoStartIndex + positionInfoLength, (float) viewportToContentHorizontalOffset(), (float) viewportToContentVerticalOffset());
            CursorAnchorInfo cursorAnchorInfo = builder.setMatrix((Matrix) null).build();
            for (int i2 = 0; i2 < positionInfoLength; i2++) {
                if ((cursorAnchorInfo.getCharacterBoundsFlags(positionInfoStartIndex + i2) & 1) == 1 && (bounds = cursorAnchorInfo.getCharacterBounds(positionInfoStartIndex + i2)) != null) {
                    mapRectFromViewToScreenCoords(bounds, true);
                    boundingRects[i2] = bounds;
                }
            }
            info.getExtras().putParcelableArray(extraDataKey, boundingRects);
        }
    }

    public void populateCharacterBounds(CursorAnchorInfo.Builder builder, int startIndex, int endIndex, float viewportToContentHorizontalOffset, float viewportToContentVerticalOffset) {
        float left;
        float right;
        int i = startIndex;
        int i2 = endIndex;
        int minLine = this.mLayout.getLineForOffset(i);
        int maxLine = this.mLayout.getLineForOffset(i2 - 1);
        int line = minLine;
        while (line <= maxLine) {
            int lineStart = this.mLayout.getLineStart(line);
            int lineEnd = this.mLayout.getLineEnd(line);
            int offsetStart = Math.max(lineStart, i);
            int offsetEnd = Math.min(lineEnd, i2);
            boolean z = true;
            if (this.mLayout.getParagraphDirection(line) != 1) {
                z = false;
            }
            boolean ltrLine = z;
            float[] widths = new float[(offsetEnd - offsetStart)];
            this.mLayout.getPaint().getTextWidths(this.mTransformed, offsetStart, offsetEnd, widths);
            float top = (float) this.mLayout.getLineTop(line);
            float bottom = (float) this.mLayout.getLineBottom(line);
            int offset = offsetStart;
            while (true) {
                int offset2 = offset;
                if (offset2 >= offsetEnd) {
                    break;
                }
                float charWidth = widths[offset2 - offsetStart];
                boolean isRtl = this.mLayout.isRtlCharAt(offset2);
                int minLine2 = minLine;
                float primary = this.mLayout.getPrimaryHorizontal(offset2);
                int maxLine2 = maxLine;
                float secondary = this.mLayout.getSecondaryHorizontal(offset2);
                if (ltrLine) {
                    if (isRtl) {
                        left = secondary - charWidth;
                        right = secondary;
                    } else {
                        left = primary;
                        right = primary + charWidth;
                    }
                } else if (!isRtl) {
                    left = secondary;
                    right = secondary + charWidth;
                } else {
                    left = primary - charWidth;
                    right = primary;
                }
                float f = primary;
                float localLeft = left + viewportToContentHorizontalOffset;
                float f2 = secondary;
                float secondary2 = right + viewportToContentHorizontalOffset;
                float localTop = top + viewportToContentVerticalOffset;
                int lineStart2 = lineStart;
                float localBottom = bottom + viewportToContentVerticalOffset;
                boolean isTopLeftVisible = isPositionVisible(localLeft, localTop);
                boolean isBottomRightVisible = isPositionVisible(secondary2, localBottom);
                int characterBoundsFlags = 0;
                if (isTopLeftVisible || isBottomRightVisible) {
                    characterBoundsFlags = 0 | 1;
                }
                if (!isTopLeftVisible || !isBottomRightVisible) {
                    characterBoundsFlags |= 2;
                }
                if (isRtl) {
                    characterBoundsFlags |= 4;
                }
                builder.addCharacterBounds(offset2, localLeft, localTop, secondary2, localBottom, characterBoundsFlags);
                offset = offset2 + 1;
                minLine = minLine2;
                maxLine = maxLine2;
                lineStart = lineStart2;
                int offset3 = startIndex;
                int i3 = endIndex;
            }
            int i4 = maxLine;
            line++;
            i = startIndex;
            i2 = endIndex;
        }
        int i5 = maxLine;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007d, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isPositionVisible(float r8, float r9) {
        /*
            r7 = this;
            float[] r0 = TEMP_POSITION
            monitor-enter(r0)
            float[] r1 = TEMP_POSITION     // Catch:{ all -> 0x0080 }
            r2 = 0
            r1[r2] = r8     // Catch:{ all -> 0x0080 }
            r3 = 1
            r1[r3] = r9     // Catch:{ all -> 0x0080 }
            r4 = r7
        L_0x000c:
            if (r4 == 0) goto L_0x007e
            if (r4 == r7) goto L_0x0024
            r5 = r1[r2]     // Catch:{ all -> 0x0080 }
            int r6 = r4.getScrollX()     // Catch:{ all -> 0x0080 }
            float r6 = (float) r6     // Catch:{ all -> 0x0080 }
            float r5 = r5 - r6
            r1[r2] = r5     // Catch:{ all -> 0x0080 }
            r5 = r1[r3]     // Catch:{ all -> 0x0080 }
            int r6 = r4.getScrollY()     // Catch:{ all -> 0x0080 }
            float r6 = (float) r6     // Catch:{ all -> 0x0080 }
            float r5 = r5 - r6
            r1[r3] = r5     // Catch:{ all -> 0x0080 }
        L_0x0024:
            r5 = r1[r2]     // Catch:{ all -> 0x0080 }
            r6 = 0
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 < 0) goto L_0x007c
            r5 = r1[r3]     // Catch:{ all -> 0x0080 }
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 < 0) goto L_0x007c
            r5 = r1[r2]     // Catch:{ all -> 0x0080 }
            int r6 = r4.getWidth()     // Catch:{ all -> 0x0080 }
            float r6 = (float) r6     // Catch:{ all -> 0x0080 }
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 > 0) goto L_0x007c
            r5 = r1[r3]     // Catch:{ all -> 0x0080 }
            int r6 = r4.getHeight()     // Catch:{ all -> 0x0080 }
            float r6 = (float) r6     // Catch:{ all -> 0x0080 }
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x0048
            goto L_0x007c
        L_0x0048:
            android.graphics.Matrix r5 = r4.getMatrix()     // Catch:{ all -> 0x0080 }
            boolean r5 = r5.isIdentity()     // Catch:{ all -> 0x0080 }
            if (r5 != 0) goto L_0x0059
            android.graphics.Matrix r5 = r4.getMatrix()     // Catch:{ all -> 0x0080 }
            r5.mapPoints(r1)     // Catch:{ all -> 0x0080 }
        L_0x0059:
            r5 = r1[r2]     // Catch:{ all -> 0x0080 }
            int r6 = r4.getLeft()     // Catch:{ all -> 0x0080 }
            float r6 = (float) r6     // Catch:{ all -> 0x0080 }
            float r5 = r5 + r6
            r1[r2] = r5     // Catch:{ all -> 0x0080 }
            r5 = r1[r3]     // Catch:{ all -> 0x0080 }
            int r6 = r4.getTop()     // Catch:{ all -> 0x0080 }
            float r6 = (float) r6     // Catch:{ all -> 0x0080 }
            float r5 = r5 + r6
            r1[r3] = r5     // Catch:{ all -> 0x0080 }
            android.view.ViewParent r5 = r4.getParent()     // Catch:{ all -> 0x0080 }
            boolean r6 = r5 instanceof android.view.View     // Catch:{ all -> 0x0080 }
            if (r6 == 0) goto L_0x007a
            r6 = r5
            android.view.View r6 = (android.view.View) r6     // Catch:{ all -> 0x0080 }
            r4 = r6
            goto L_0x007b
        L_0x007a:
            r4 = 0
        L_0x007b:
            goto L_0x000c
        L_0x007c:
            monitor-exit(r0)     // Catch:{ all -> 0x0080 }
            return r2
        L_0x007e:
            monitor-exit(r0)     // Catch:{ all -> 0x0080 }
            return r3
        L_0x0080:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0080 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.TextView.isPositionVisible(float, float):boolean");
    }

    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        int start;
        int end;
        CharSequence text;
        int updatedTextLength;
        if (this.mEditor != null && this.mEditor.mProcessTextIntentActionsHandler.performAccessibilityAction(action)) {
            return true;
        }
        if (action == 16) {
            return performAccessibilityActionClick(arguments);
        }
        if (action == 256 || action == 512) {
            ensureIterableTextForAccessibilitySelectable();
            return super.performAccessibilityActionInternal(action, arguments);
        } else if (action != 16384) {
            if (action != 32768) {
                if (action != 65536) {
                    if (action == 131072) {
                        ensureIterableTextForAccessibilitySelectable();
                        CharSequence text2 = getIterableTextForAccessibility();
                        if (text2 == null) {
                            return false;
                        }
                        if (arguments != null) {
                            start = arguments.getInt("ACTION_ARGUMENT_SELECTION_START_INT", -1);
                        } else {
                            start = -1;
                        }
                        if (arguments != null) {
                            end = arguments.getInt("ACTION_ARGUMENT_SELECTION_END_INT", -1);
                        } else {
                            end = -1;
                        }
                        if (!(getSelectionStart() == start && getSelectionEnd() == end)) {
                            if (start == end && end == -1) {
                                Selection.removeSelection((Spannable) text2);
                                return true;
                            } else if (start >= 0 && start <= end && end <= text2.length()) {
                                Selection.setSelection((Spannable) text2, start, end);
                                if (this.mEditor != null) {
                                    this.mEditor.startSelectionActionModeAsync(false);
                                }
                                return true;
                            }
                        }
                        return false;
                    } else if (action != 2097152) {
                        if (action != 268435456) {
                            return super.performAccessibilityActionInternal(action, arguments);
                        }
                        if (!isFocused() || !canShare() || !onTextContextMenuItem(16908341)) {
                            return false;
                        }
                        return true;
                    } else if (!isEnabled() || this.mBufferType != BufferType.EDITABLE) {
                        return false;
                    } else {
                        if (arguments != null) {
                            text = arguments.getCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE");
                        } else {
                            text = null;
                        }
                        setText(text);
                        if (this.mText != null && (updatedTextLength = this.mText.length()) > 0) {
                            Selection.setSelection(this.mSpannable, updatedTextLength);
                        }
                        return true;
                    }
                } else if (!isFocused() || !canCut() || !onTextContextMenuItem(16908320)) {
                    return false;
                } else {
                    return true;
                }
            } else if (!isFocused() || !canPaste() || !onTextContextMenuItem(16908322)) {
                return false;
            } else {
                return true;
            }
        } else if (!isFocused() || !canCopy() || !onTextContextMenuItem(16908321)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean performAccessibilityActionClick(Bundle arguments) {
        boolean handled = false;
        if (!isEnabled()) {
            return false;
        }
        if (isClickable() || isLongClickable()) {
            if (isFocusable() && !isFocused()) {
                requestFocus();
            }
            performClick();
            handled = true;
        }
        if ((this.mMovement == null && !onCheckIsTextEditor()) || !hasSpannableText() || this.mLayout == null) {
            return handled;
        }
        if ((!isTextEditable() && !isTextSelectable()) || !isFocused()) {
            return handled;
        }
        InputMethodManager imm = getInputMethodManager();
        viewClicked(imm);
        if (isTextSelectable() || !this.mEditor.mShowSoftInputOnFocus || imm == null) {
            return handled;
        }
        return handled | imm.showSoftInput(this, 0);
    }

    private boolean hasSpannableText() {
        return this.mText != null && (this.mText instanceof Spannable);
    }

    public void sendAccessibilityEventInternal(int eventType) {
        if (eventType == 32768 && this.mEditor != null) {
            this.mEditor.mProcessTextIntentActionsHandler.initializeAccessibilityActions();
        }
        super.sendAccessibilityEventInternal(eventType);
    }

    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (event.getEventType() != 4096) {
            super.sendAccessibilityEventUnchecked(event);
        }
    }

    @UnsupportedAppUsage
    private CharSequence getTextForAccessibility() {
        if (TextUtils.isEmpty(this.mText)) {
            return this.mHint;
        }
        return TextUtils.trimToParcelableSize(this.mTransformed);
    }

    /* access modifiers changed from: package-private */
    public void sendAccessibilityEventTypeViewTextChanged(CharSequence beforeText, int fromIndex, int removedCount, int addedCount) {
        AccessibilityEvent event = AccessibilityEvent.obtain(16);
        event.setFromIndex(fromIndex);
        event.setRemovedCount(removedCount);
        event.setAddedCount(addedCount);
        event.setBeforeText(beforeText);
        sendAccessibilityEventUnchecked(event);
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
    }

    public boolean isInputMethodTarget() {
        InputMethodManager imm = getInputMethodManager();
        return imm != null && imm.isActive(this);
    }

    public boolean onTextContextMenuItem(int id) {
        int min = 0;
        int max = this.mText.length();
        if (isFocused()) {
            int selStart = getSelectionStart();
            int selEnd = getSelectionEnd();
            min = Math.max(0, Math.min(selStart, selEnd));
            max = Math.max(0, Math.max(selStart, selEnd));
        }
        if (id != 16908355) {
            switch (id) {
                case 16908319:
                    boolean hadSelection = hasSelection();
                    selectAllText();
                    if (this.mEditor != null && hadSelection) {
                        this.mEditor.invalidateActionModeAsync();
                    }
                    return true;
                case 16908320:
                    if (setPrimaryClip(ClipData.newPlainText((CharSequence) null, getTransformedText(min, max)))) {
                        deleteText_internal(min, max);
                    } else {
                        Toast.makeText(getContext(), (int) com.android.internal.R.string.failed_to_copy_to_clipboard, 0).show();
                    }
                    return true;
                case 16908321:
                    int selStart2 = getSelectionStart();
                    int selEnd2 = getSelectionEnd();
                    if (setPrimaryClip(ClipData.newPlainText((CharSequence) null, getTransformedText(Math.max(0, Math.min(selStart2, selEnd2)), Math.max(0, Math.max(selStart2, selEnd2)))))) {
                        stopTextActionMode();
                    } else {
                        Toast.makeText(getContext(), (int) com.android.internal.R.string.failed_to_copy_to_clipboard, 0).show();
                    }
                    return true;
                case 16908322:
                    paste(min, max, true);
                    return true;
                default:
                    switch (id) {
                        case 16908337:
                            paste(min, max, false);
                            return true;
                        case 16908338:
                            if (this.mEditor != null) {
                                this.mEditor.undo();
                            }
                            return true;
                        case 16908339:
                            if (this.mEditor != null) {
                                this.mEditor.redo();
                            }
                            return true;
                        case 16908340:
                            if (this.mEditor != null) {
                                this.mEditor.replace();
                            }
                            return true;
                        case 16908341:
                            shareSelectedText();
                            return true;
                        default:
                            return false;
                    }
            }
        } else {
            requestAutofill();
            stopTextActionMode();
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public CharSequence getTransformedText(int start, int end) {
        return removeSuggestionSpans(this.mTransformed.subSequence(start, end));
    }

    public boolean performLongClick() {
        boolean handled = false;
        boolean performedHapticFeedback = false;
        if (this.mEditor != null) {
            this.mEditor.mIsBeingLongClicked = true;
        }
        if (super.performLongClick()) {
            handled = true;
            performedHapticFeedback = true;
        }
        if (this.mEditor != null) {
            handled |= this.mEditor.performLongClick(handled);
            this.mEditor.mIsBeingLongClicked = false;
        }
        if (handled) {
            if (!performedHapticFeedback) {
                performHapticFeedback(0);
            }
            if (this.mEditor != null) {
                this.mEditor.mDiscardNextActionUp = true;
            }
        } else {
            MetricsLogger.action(this.mContext, (int) MetricsProto.MetricsEvent.TEXT_LONGPRESS, 0);
        }
        return handled;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        if (this.mEditor != null) {
            this.mEditor.onScrollChanged();
        }
    }

    public boolean isSuggestionsEnabled() {
        if (this.mEditor == null || (this.mEditor.mInputType & 15) != 1 || (this.mEditor.mInputType & 524288) > 0) {
            return false;
        }
        int variation = this.mEditor.mInputType & InputType.TYPE_MASK_VARIATION;
        if (variation == 0 || variation == 48 || variation == 80 || variation == 64 || variation == 160) {
            return true;
        }
        return false;
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        createEditorIfNeeded();
        this.mEditor.mCustomSelectionActionModeCallback = actionModeCallback;
    }

    public ActionMode.Callback getCustomSelectionActionModeCallback() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mCustomSelectionActionModeCallback;
    }

    public void setCustomInsertionActionModeCallback(ActionMode.Callback actionModeCallback) {
        createEditorIfNeeded();
        this.mEditor.mCustomInsertionActionModeCallback = actionModeCallback;
    }

    public ActionMode.Callback getCustomInsertionActionModeCallback() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mCustomInsertionActionModeCallback;
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    public TextClassifier getTextClassifier() {
        if (this.mTextClassifier != null) {
            return this.mTextClassifier;
        }
        TextClassificationManager tcm = (TextClassificationManager) this.mContext.getSystemService(TextClassificationManager.class);
        if (tcm != null) {
            return tcm.getTextClassifier();
        }
        return TextClassifier.NO_OP;
    }

    /* access modifiers changed from: package-private */
    public TextClassifier getTextClassificationSession() {
        String widgetType;
        if (this.mTextClassificationSession == null || this.mTextClassificationSession.isDestroyed()) {
            TextClassificationManager tcm = (TextClassificationManager) this.mContext.getSystemService(TextClassificationManager.class);
            if (tcm != null) {
                if (isTextEditable()) {
                    widgetType = TextClassifier.WIDGET_TYPE_EDITTEXT;
                } else if (isTextSelectable()) {
                    widgetType = TextClassifier.WIDGET_TYPE_TEXTVIEW;
                } else {
                    widgetType = TextClassifier.WIDGET_TYPE_UNSELECTABLE_TEXTVIEW;
                }
                this.mTextClassificationContext = new TextClassificationContext.Builder(this.mContext.getPackageName(), widgetType).build();
                if (this.mTextClassifier != null) {
                    this.mTextClassificationSession = tcm.createTextClassificationSession(this.mTextClassificationContext, this.mTextClassifier);
                } else {
                    this.mTextClassificationSession = tcm.createTextClassificationSession(this.mTextClassificationContext);
                }
            } else {
                this.mTextClassificationSession = TextClassifier.NO_OP;
            }
        }
        return this.mTextClassificationSession;
    }

    /* access modifiers changed from: package-private */
    public TextClassificationContext getTextClassificationContext() {
        return this.mTextClassificationContext;
    }

    /* access modifiers changed from: package-private */
    public boolean usesNoOpTextClassifier() {
        return getTextClassifier() == TextClassifier.NO_OP;
    }

    public boolean requestActionMode(TextLinks.TextLinkSpan clickedSpan) {
        Preconditions.checkNotNull(clickedSpan);
        if (!(this.mText instanceof Spanned)) {
            return false;
        }
        int start = ((Spanned) this.mText).getSpanStart(clickedSpan);
        int end = ((Spanned) this.mText).getSpanEnd(clickedSpan);
        if (start < 0 || end > this.mText.length() || start >= end) {
            return false;
        }
        createEditorIfNeeded();
        this.mEditor.startLinkActionModeAsync(start, end);
        return true;
    }

    public boolean handleClick(TextLinks.TextLinkSpan clickedSpan) {
        Preconditions.checkNotNull(clickedSpan);
        if (!(this.mText instanceof Spanned)) {
            return false;
        }
        Spanned spanned = (Spanned) this.mText;
        int start = spanned.getSpanStart(clickedSpan);
        int end = spanned.getSpanEnd(clickedSpan);
        if (start < 0 || end > this.mText.length() || start >= end) {
            return false;
        }
        Supplier<TextClassification> supplier = new Supplier(new TextClassification.Request.Builder(this.mText, start, end).setDefaultLocales(getTextLocales()).build()) {
            private final /* synthetic */ TextClassification.Request f$1;

            {
                this.f$1 = r2;
            }

            public final Object get() {
                return TextView.this.getTextClassifier().classifyText(this.f$1);
            }
        };
        CompletableFuture.supplyAsync(supplier).completeOnTimeout((Object) null, 1, TimeUnit.SECONDS).thenAccept($$Lambda$TextView$jQz3_DIfGrNeNdu_95_wi6UkW4E.INSTANCE);
        return true;
    }

    static /* synthetic */ void lambda$handleClick$1(TextClassification classification) {
        if (classification == null) {
            Log.d(LOG_TAG, "Timeout while classifying text");
        } else if (!classification.getActions().isEmpty()) {
            try {
                classification.getActions().get(0).getActionIntent().send();
            } catch (PendingIntent.CanceledException e) {
                Log.e(LOG_TAG, "Error sending PendingIntent", e);
            }
        } else {
            Log.d(LOG_TAG, "No link action to perform");
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void stopTextActionMode() {
        if (this.mEditor != null) {
            this.mEditor.stopTextActionMode();
        }
    }

    public void hideFloatingToolbar(int durationMs) {
        if (this.mEditor != null) {
            this.mEditor.hideFloatingToolbar(durationMs);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean canUndo() {
        return this.mEditor != null && this.mEditor.canUndo();
    }

    /* access modifiers changed from: package-private */
    public boolean canRedo() {
        return this.mEditor != null && this.mEditor.canRedo();
    }

    /* access modifiers changed from: package-private */
    public boolean canCut() {
        if (!hasPasswordTransformationMethod() && this.mText.length() > 0 && hasSelection() && (this.mText instanceof Editable) && this.mEditor != null && this.mEditor.mKeyListener != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean canCopy() {
        if (!hasPasswordTransformationMethod() && this.mText.length() > 0 && hasSelection() && this.mEditor != null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean canShare() {
        if (!getContext().canStartActivityForResult() || !isDeviceProvisioned()) {
            return false;
        }
        return canCopy();
    }

    /* access modifiers changed from: package-private */
    public boolean isDeviceProvisioned() {
        if (this.mDeviceProvisionedState == 0) {
            this.mDeviceProvisionedState = Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0 ? 2 : 1;
        }
        if (this.mDeviceProvisionedState == 2) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public boolean canPaste() {
        return (this.mText instanceof Editable) && this.mEditor != null && this.mEditor.mKeyListener != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0 && getClipboardManagerForUser().hasPrimaryClip();
    }

    /* access modifiers changed from: package-private */
    public boolean canPasteAsPlainText() {
        if (!canPaste()) {
            return false;
        }
        ClipData clipData = getClipboardManagerForUser().getPrimaryClip();
        ClipDescription description = clipData.getDescription();
        boolean isPlainType = description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
        CharSequence text = clipData.getItemAt(0).getText();
        if (!isPlainType || !(text instanceof Spanned) || !TextUtils.hasStyleSpan((Spanned) text)) {
            return description.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML);
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean canProcessText() {
        if (getId() == -1) {
            return false;
        }
        return canShare();
    }

    /* access modifiers changed from: package-private */
    public boolean canSelectAllText() {
        return canSelectText() && !hasPasswordTransformationMethod() && !(getSelectionStart() == 0 && getSelectionEnd() == this.mText.length());
    }

    /* access modifiers changed from: package-private */
    public boolean selectAllText() {
        if (this.mEditor != null) {
            hideFloatingToolbar(500);
        }
        int length = this.mText.length();
        Selection.setSelection(this.mSpannable, 0, length);
        if (length > 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void replaceSelectionWithText(CharSequence text) {
        ((Editable) this.mText).replace(getSelectionStart(), getSelectionEnd(), text);
    }

    private void paste(int min, int max, boolean withFormatting) {
        CharSequence text;
        ClipData clip = getClipboardManagerForUser().getPrimaryClip();
        if (clip != null) {
            boolean didFirst = false;
            for (int i = 0; i < clip.getItemCount(); i++) {
                if (withFormatting) {
                    text = clip.getItemAt(i).coerceToStyledText(getContext());
                } else {
                    CharSequence text2 = clip.getItemAt(i).coerceToText(getContext());
                    text = text2 instanceof Spanned ? text2.toString() : text2;
                }
                if (text != null) {
                    if (!didFirst) {
                        Selection.setSelection(this.mSpannable, max);
                        ((Editable) this.mText).replace(min, max, text);
                        didFirst = true;
                    } else {
                        ((Editable) this.mText).insert(getSelectionEnd(), "\n");
                        ((Editable) this.mText).insert(getSelectionEnd(), text);
                    }
                }
            }
            sLastCutCopyOrTextChangedTime = 0;
        }
    }

    private void shareSelectedText() {
        String selectedText = getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType(ClipDescription.MIMETYPE_TEXT_PLAIN);
            sharingIntent.removeExtra(Intent.EXTRA_TEXT);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, (String) TextUtils.trimToParcelableSize(selectedText));
            getContext().startActivity(Intent.createChooser(sharingIntent, (CharSequence) null));
            Selection.setSelection(this.mSpannable, getSelectionEnd());
        }
    }

    private boolean setPrimaryClip(ClipData clip) {
        try {
            getClipboardManagerForUser().setPrimaryClip(clip);
            sLastCutCopyOrTextChangedTime = SystemClock.uptimeMillis();
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public int getOffsetForPosition(float x, float y) {
        if (getLayout() == null) {
            return -1;
        }
        return getOffsetAtCoordinate(getLineAtCoordinate(y), x);
    }

    /* access modifiers changed from: package-private */
    public float convertToLocalHorizontalCoordinate(float x) {
        return Math.min((float) ((getWidth() - getTotalPaddingRight()) - 1), Math.max(0.0f, x - ((float) getTotalPaddingLeft()))) + ((float) getScrollX());
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public int getLineAtCoordinate(float y) {
        return getLayout().getLineForVertical((int) (Math.min((float) ((getHeight() - getTotalPaddingBottom()) - 1), Math.max(0.0f, y - ((float) getTotalPaddingTop()))) + ((float) getScrollY())));
    }

    /* access modifiers changed from: package-private */
    public int getLineAtCoordinateUnclamped(float y) {
        return getLayout().getLineForVertical((int) ((y - ((float) getTotalPaddingTop())) + ((float) getScrollY())));
    }

    /* access modifiers changed from: package-private */
    public int getOffsetAtCoordinate(int line, float x) {
        return getLayout().getOffsetForHorizontal(line, convertToLocalHorizontalCoordinate(x));
    }

    public boolean onDragEvent(DragEvent event) {
        int action = event.getAction();
        if (action != 5) {
            switch (action) {
                case 1:
                    if (this.mEditor == null || !this.mEditor.hasInsertionController()) {
                        return false;
                    }
                    return true;
                case 2:
                    if (this.mText instanceof Spannable) {
                        Selection.setSelection(this.mSpannable, getOffsetForPosition(event.getX(), event.getY()));
                    }
                    return true;
                case 3:
                    if (this.mEditor != null) {
                        this.mEditor.onDrop(event);
                    }
                    return true;
                default:
                    return true;
            }
        } else {
            requestFocus();
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInBatchEditMode() {
        if (this.mEditor == null) {
            return false;
        }
        Editor.InputMethodState ims = this.mEditor.mInputMethodState;
        if (ims == null) {
            return this.mEditor.mInBatchEditControllers;
        }
        if (ims.mBatchEditNesting > 0) {
            return true;
        }
        return false;
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        TextDirectionHeuristic newTextDir = getTextDirectionHeuristic();
        if (this.mTextDir != newTextDir) {
            this.mTextDir = newTextDir;
            if (this.mLayout != null) {
                checkForRelayout();
            }
        }
    }

    public TextDirectionHeuristic getTextDirectionHeuristic() {
        if (hasPasswordTransformationMethod()) {
            return TextDirectionHeuristics.LTR;
        }
        boolean z = false;
        if (this.mEditor == null || (this.mEditor.mInputType & 15) != 3) {
            if (getLayoutDirection() == 1) {
                z = true;
            }
            boolean defaultIsRtl = z;
            switch (getTextDirection()) {
                case 2:
                    return TextDirectionHeuristics.ANYRTL_LTR;
                case 3:
                    return TextDirectionHeuristics.LTR;
                case 4:
                    return TextDirectionHeuristics.RTL;
                case 5:
                    return TextDirectionHeuristics.LOCALE;
                case 6:
                    return TextDirectionHeuristics.FIRSTSTRONG_LTR;
                case 7:
                    return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                default:
                    if (defaultIsRtl) {
                        return TextDirectionHeuristics.FIRSTSTRONG_RTL;
                    }
                    return TextDirectionHeuristics.FIRSTSTRONG_LTR;
            }
        } else {
            byte digitDirection = Character.getDirectionality(DecimalFormatSymbols.getInstance(getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (digitDirection == 1 || digitDirection == 2) {
                return TextDirectionHeuristics.RTL;
            }
            return TextDirectionHeuristics.LTR;
        }
    }

    public void onResolveDrawables(int layoutDirection) {
        if (this.mLastLayoutDirection != layoutDirection) {
            this.mLastLayoutDirection = layoutDirection;
            if (this.mDrawables != null && this.mDrawables.resolveWithLayoutDirection(layoutDirection)) {
                prepareDrawableForDisplay(this.mDrawables.mShowing[0]);
                prepareDrawableForDisplay(this.mDrawables.mShowing[2]);
                applyCompoundDrawableTint();
            }
        }
    }

    private void prepareDrawableForDisplay(Drawable dr) {
        if (dr != null) {
            dr.setLayoutDirection(getLayoutDirection());
            if (dr.isStateful()) {
                dr.setState(getDrawableState());
                dr.jumpToCurrentState();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        this.mLastLayoutDirection = -1;
    }

    /* access modifiers changed from: protected */
    public void viewClicked(InputMethodManager imm) {
        if (imm != null) {
            imm.viewClicked(this);
        }
    }

    /* access modifiers changed from: protected */
    @UnsupportedAppUsage
    public void deleteText_internal(int start, int end) {
        ((Editable) this.mText).delete(start, end);
    }

    /* access modifiers changed from: protected */
    public void replaceText_internal(int start, int end, CharSequence text) {
        ((Editable) this.mText).replace(start, end, text);
    }

    /* access modifiers changed from: protected */
    public void setSpan_internal(Object span, int start, int end, int flags) {
        ((Editable) this.mText).setSpan(span, start, end, flags);
    }

    /* access modifiers changed from: protected */
    public void setCursorPosition_internal(int start, int end) {
        Selection.setSelection((Editable) this.mText, start, end);
    }

    @UnsupportedAppUsage
    private void createEditorIfNeeded() {
        if (this.mEditor == null) {
            this.mEditor = new Editor(this);
        }
    }

    @UnsupportedAppUsage
    public CharSequence getIterableTextForAccessibility() {
        return this.mText;
    }

    private void ensureIterableTextForAccessibilitySelectable() {
        if (!(this.mText instanceof Spannable)) {
            setText(this.mText, BufferType.SPANNABLE);
        }
    }

    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int granularity) {
        if (granularity == 4) {
            Spannable text = (Spannable) getIterableTextForAccessibility();
            if (!TextUtils.isEmpty(text) && getLayout() != null) {
                AccessibilityIterators.LineTextSegmentIterator iterator = AccessibilityIterators.LineTextSegmentIterator.getInstance();
                iterator.initialize(text, getLayout());
                return iterator;
            }
        } else if (granularity == 16 && !TextUtils.isEmpty((Spannable) getIterableTextForAccessibility()) && getLayout() != null) {
            AccessibilityIterators.PageTextSegmentIterator iterator2 = AccessibilityIterators.PageTextSegmentIterator.getInstance();
            iterator2.initialize(this);
            return iterator2;
        }
        return super.getIteratorForGranularity(granularity);
    }

    public int getAccessibilitySelectionStart() {
        return getSelectionStart();
    }

    public boolean isAccessibilitySelectionExtendable() {
        return true;
    }

    public int getAccessibilitySelectionEnd() {
        return getSelectionEnd();
    }

    public void setAccessibilitySelection(int start, int end) {
        if (getAccessibilitySelectionStart() != start || getAccessibilitySelectionEnd() != end) {
            CharSequence text = getIterableTextForAccessibility();
            if (Math.min(start, end) < 0 || Math.max(start, end) > text.length()) {
                Selection.removeSelection((Spannable) text);
            } else {
                Selection.setSelection((Spannable) text, start, end);
            }
            if (this.mEditor != null) {
                this.mEditor.hideCursorAndSpanControllers();
                this.mEditor.stopTextActionMode();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void encodeProperties(ViewHierarchyEncoder stream) {
        super.encodeProperties(stream);
        TextUtils.TruncateAt ellipsize = getEllipsize();
        String str = null;
        stream.addProperty("text:ellipsize", ellipsize == null ? null : ellipsize.name());
        stream.addProperty("text:textSize", getTextSize());
        stream.addProperty("text:scaledTextSize", getScaledTextSize());
        stream.addProperty("text:typefaceStyle", getTypefaceStyle());
        stream.addProperty("text:selectionStart", getSelectionStart());
        stream.addProperty("text:selectionEnd", getSelectionEnd());
        stream.addProperty("text:curTextColor", this.mCurTextColor);
        if (this.mText != null) {
            str = this.mText.toString();
        }
        stream.addProperty("text:text", str);
        stream.addProperty("text:gravity", this.mGravity);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        ParcelableParcel editorState;
        CharSequence error;
        boolean frozenWithFocus;
        int selEnd;
        int selStart;
        @UnsupportedAppUsage
        CharSequence text;

        SavedState(Parcelable superState) {
            super(superState);
            this.selStart = -1;
            this.selEnd = -1;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.selStart);
            out.writeInt(this.selEnd);
            out.writeInt(this.frozenWithFocus ? 1 : 0);
            TextUtils.writeToParcel(this.text, out, flags);
            if (this.error == null) {
                out.writeInt(0);
            } else {
                out.writeInt(1);
                TextUtils.writeToParcel(this.error, out, flags);
            }
            if (this.editorState == null) {
                out.writeInt(0);
                return;
            }
            out.writeInt(1);
            this.editorState.writeToParcel(out, flags);
        }

        public String toString() {
            String str = "TextView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " start=" + this.selStart + " end=" + this.selEnd;
            if (this.text != null) {
                str = str + " text=" + this.text;
            }
            return str + "}";
        }

        private SavedState(Parcel in) {
            super(in);
            this.selStart = -1;
            this.selEnd = -1;
            this.selStart = in.readInt();
            this.selEnd = in.readInt();
            this.frozenWithFocus = in.readInt() != 0;
            this.text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            if (in.readInt() != 0) {
                this.error = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            }
            if (in.readInt() != 0) {
                this.editorState = ParcelableParcel.CREATOR.createFromParcel(in);
            }
        }
    }

    private static class CharWrapper implements CharSequence, GetChars, GraphicsOperations {
        /* access modifiers changed from: private */
        public char[] mChars;
        private int mLength;
        private int mStart;

        public CharWrapper(char[] chars, int start, int len) {
            this.mChars = chars;
            this.mStart = start;
            this.mLength = len;
        }

        /* access modifiers changed from: package-private */
        public void set(char[] chars, int start, int len) {
            this.mChars = chars;
            this.mStart = start;
            this.mLength = len;
        }

        public int length() {
            return this.mLength;
        }

        public char charAt(int off) {
            return this.mChars[this.mStart + off];
        }

        public String toString() {
            return new String(this.mChars, this.mStart, this.mLength);
        }

        public CharSequence subSequence(int start, int end) {
            if (start >= 0 && end >= 0 && start <= this.mLength && end <= this.mLength) {
                return new String(this.mChars, this.mStart + start, end - start);
            }
            throw new IndexOutOfBoundsException(start + ", " + end);
        }

        public void getChars(int start, int end, char[] buf, int off) {
            if (start < 0 || end < 0 || start > this.mLength || end > this.mLength) {
                throw new IndexOutOfBoundsException(start + ", " + end);
            }
            System.arraycopy(this.mChars, this.mStart + start, buf, off, end - start);
        }

        public void drawText(BaseCanvas c, int start, int end, float x, float y, Paint p) {
            c.drawText(this.mChars, start + this.mStart, end - start, x, y, p);
        }

        public void drawTextRun(BaseCanvas c, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint p) {
            c.drawTextRun(this.mChars, start + this.mStart, end - start, contextStart + this.mStart, contextEnd - contextStart, x, y, isRtl, p);
        }

        public float measureText(int start, int end, Paint p) {
            return p.measureText(this.mChars, this.mStart + start, end - start);
        }

        public int getTextWidths(int start, int end, float[] widths, Paint p) {
            return p.getTextWidths(this.mChars, this.mStart + start, end - start, widths);
        }

        public float getTextRunAdvances(int start, int end, int contextStart, int contextEnd, boolean isRtl, float[] advances, int advancesIndex, Paint p) {
            return p.getTextRunAdvances(this.mChars, start + this.mStart, end - start, contextStart + this.mStart, contextEnd - contextStart, isRtl, advances, advancesIndex);
        }

        public int getTextRunCursor(int contextStart, int contextEnd, boolean isRtl, int offset, int cursorOpt, Paint p) {
            return p.getTextRunCursor(this.mChars, contextStart + this.mStart, contextEnd - contextStart, isRtl, offset + this.mStart, cursorOpt);
        }
    }

    private static final class Marquee {
        private static final int MARQUEE_DELAY = 1200;
        private static final float MARQUEE_DELTA_MAX = 0.07f;
        private static final int MARQUEE_DP_PER_SECOND = 30;
        private static final byte MARQUEE_RUNNING = 2;
        private static final byte MARQUEE_STARTING = 1;
        private static final byte MARQUEE_STOPPED = 0;
        /* access modifiers changed from: private */
        public final Choreographer mChoreographer;
        private float mFadeStop;
        private float mGhostOffset;
        private float mGhostStart;
        /* access modifiers changed from: private */
        public long mLastAnimationMs;
        private float mMaxFadeScroll;
        private float mMaxScroll;
        private final float mPixelsPerMs;
        /* access modifiers changed from: private */
        public int mRepeatLimit;
        private Choreographer.FrameCallback mRestartCallback = new Choreographer.FrameCallback() {
            public void doFrame(long frameTimeNanos) {
                if (Marquee.this.mStatus == 2) {
                    if (Marquee.this.mRepeatLimit >= 0) {
                        Marquee.access$910(Marquee.this);
                    }
                    Marquee.this.start(Marquee.this.mRepeatLimit);
                }
            }
        };
        private float mScroll;
        private Choreographer.FrameCallback mStartCallback = new Choreographer.FrameCallback() {
            public void doFrame(long frameTimeNanos) {
                byte unused = Marquee.this.mStatus = (byte) 2;
                long unused2 = Marquee.this.mLastAnimationMs = Marquee.this.mChoreographer.getFrameTime();
                Marquee.this.tick();
            }
        };
        /* access modifiers changed from: private */
        public byte mStatus = 0;
        private Choreographer.FrameCallback mTickCallback = new Choreographer.FrameCallback() {
            public void doFrame(long frameTimeNanos) {
                Marquee.this.tick();
            }
        };
        private final WeakReference<TextView> mView;

        static /* synthetic */ int access$910(Marquee x0) {
            int i = x0.mRepeatLimit;
            x0.mRepeatLimit = i - 1;
            return i;
        }

        Marquee(TextView v) {
            this.mPixelsPerMs = (30.0f * v.getContext().getResources().getDisplayMetrics().density) / 1000.0f;
            this.mView = new WeakReference<>(v);
            this.mChoreographer = Choreographer.getInstance();
        }

        /* access modifiers changed from: package-private */
        public void tick() {
            if (this.mStatus == 2) {
                this.mChoreographer.removeFrameCallback(this.mTickCallback);
                TextView textView = (TextView) this.mView.get();
                if (textView == null) {
                    return;
                }
                if (textView.isFocused() || textView.isSelected()) {
                    long currentMs = this.mChoreographer.getFrameTime();
                    this.mLastAnimationMs = currentMs;
                    this.mScroll += ((float) (currentMs - this.mLastAnimationMs)) * this.mPixelsPerMs;
                    if (this.mScroll > this.mMaxScroll) {
                        this.mScroll = this.mMaxScroll;
                        this.mChoreographer.postFrameCallbackDelayed(this.mRestartCallback, 1200);
                    } else {
                        this.mChoreographer.postFrameCallback(this.mTickCallback);
                    }
                    textView.invalidate();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void stop() {
            this.mStatus = 0;
            this.mChoreographer.removeFrameCallback(this.mStartCallback);
            this.mChoreographer.removeFrameCallback(this.mRestartCallback);
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            resetScroll();
        }

        private void resetScroll() {
            this.mScroll = 0.0f;
            TextView textView = (TextView) this.mView.get();
            if (textView != null) {
                textView.invalidate();
            }
        }

        /* access modifiers changed from: package-private */
        public void start(int repeatLimit) {
            if (repeatLimit == 0) {
                stop();
                return;
            }
            this.mRepeatLimit = repeatLimit;
            TextView textView = (TextView) this.mView.get();
            if (textView != null && textView.mLayout != null) {
                this.mStatus = 1;
                this.mScroll = 0.0f;
                int textWidth = (textView.getWidth() - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
                float lineWidth = textView.mLayout.getLineWidth(0);
                float gap = ((float) textWidth) / 3.0f;
                this.mGhostStart = (lineWidth - ((float) textWidth)) + gap;
                this.mMaxScroll = this.mGhostStart + ((float) textWidth);
                this.mGhostOffset = lineWidth + gap;
                this.mFadeStop = (((float) textWidth) / 6.0f) + lineWidth;
                this.mMaxFadeScroll = this.mGhostStart + lineWidth + lineWidth;
                textView.invalidate();
                this.mChoreographer.postFrameCallback(this.mStartCallback);
            }
        }

        /* access modifiers changed from: package-private */
        public float getGhostOffset() {
            return this.mGhostOffset;
        }

        /* access modifiers changed from: package-private */
        public float getScroll() {
            return this.mScroll;
        }

        /* access modifiers changed from: package-private */
        public float getMaxFadeScroll() {
            return this.mMaxFadeScroll;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldDrawLeftFade() {
            return this.mScroll <= this.mFadeStop;
        }

        /* access modifiers changed from: package-private */
        public boolean shouldDrawGhost() {
            return this.mStatus == 2 && this.mScroll > this.mGhostStart;
        }

        /* access modifiers changed from: package-private */
        public boolean isRunning() {
            return this.mStatus == 2;
        }

        /* access modifiers changed from: package-private */
        public boolean isStopped() {
            return this.mStatus == 0;
        }
    }

    private class ChangeWatcher implements TextWatcher, SpanWatcher {
        private CharSequence mBeforeText;

        private ChangeWatcher() {
        }

        public void beforeTextChanged(CharSequence buffer, int start, int before, int after) {
            if (AccessibilityManager.getInstance(TextView.this.mContext).isEnabled() && TextView.this.mTransformed != null) {
                this.mBeforeText = TextView.this.mTransformed.toString();
            }
            TextView.this.sendBeforeTextChanged(buffer, start, before, after);
        }

        public void onTextChanged(CharSequence buffer, int start, int before, int after) {
            TextView.this.handleTextChanged(buffer, start, before, after);
            if (!AccessibilityManager.getInstance(TextView.this.mContext).isEnabled()) {
                return;
            }
            if (TextView.this.isFocused() || (TextView.this.isSelected() && TextView.this.isShown())) {
                TextView.this.sendAccessibilityEventTypeViewTextChanged(this.mBeforeText, start, before, after);
                this.mBeforeText = null;
            }
        }

        public void afterTextChanged(Editable buffer) {
            TextView.this.sendAfterTextChanged(buffer);
            if (MetaKeyKeyListener.getMetaState((CharSequence) buffer, 2048) != 0) {
                MetaKeyKeyListener.stopSelecting(TextView.this, buffer);
            }
        }

        public void onSpanChanged(Spannable buf, Object what, int s, int e, int st, int en) {
            TextView.this.spanChange(buf, what, s, st, e, en);
        }

        public void onSpanAdded(Spannable buf, Object what, int s, int e) {
            TextView.this.spanChange(buf, what, -1, s, -1, e);
        }

        public void onSpanRemoved(Spannable buf, Object what, int s, int e) {
            TextView.this.spanChange(buf, what, s, -1, e, -1);
        }
    }
}
