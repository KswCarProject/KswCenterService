package android.widget;

import android.app.RemoteAction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.Telephony;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.EventLog;
import android.util.Log;
import android.view.ActionMode;
import android.view.textclassifier.ExtrasUtils;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.SelectionSessionLogger;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextSelection;
import android.widget.Editor;
import android.widget.SelectionActionModeHelper;
import android.widget.SmartSelectSprite;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@VisibleForTesting(visibility = VisibleForTesting.Visibility.PACKAGE)
public final class SelectionActionModeHelper {
    private static final String LOG_TAG = "SelectActionModeHelper";
    private final Editor mEditor;
    private final SelectionTracker mSelectionTracker;
    private final SmartSelectSprite mSmartSelectSprite;
    private TextClassification mTextClassification;
    private AsyncTask mTextClassificationAsyncTask;
    private final TextClassificationHelper mTextClassificationHelper;
    private final TextView mTextView = this.mEditor.getTextView();

    SelectionActionModeHelper(Editor editor) {
        this.mEditor = (Editor) Preconditions.checkNotNull(editor);
        Context context = this.mTextView.getContext();
        TextView textView = this.mTextView;
        Objects.requireNonNull(textView);
        this.mTextClassificationHelper = new TextClassificationHelper(context, new Supplier() {
            public final Object get() {
                return TextView.this.getTextClassifier();
            }
        }, getText(this.mTextView), 0, 1, this.mTextView.getTextLocales());
        this.mSelectionTracker = new SelectionTracker(this.mTextView);
        if (getTextClassificationSettings().isSmartSelectionAnimationEnabled()) {
            Context context2 = this.mTextView.getContext();
            int i = editor.getTextView().mHighlightColor;
            TextView textView2 = this.mTextView;
            Objects.requireNonNull(textView2);
            this.mSmartSelectSprite = new SmartSelectSprite(context2, i, new Runnable() {
                public final void run() {
                    TextView.this.invalidate();
                }
            });
            return;
        }
        this.mSmartSelectSprite = null;
    }

    public void startSelectionActionModeAsync(boolean adjustSelection) {
        Supplier r2;
        Consumer r1;
        boolean adjustSelection2 = adjustSelection & getTextClassificationSettings().isSmartSelectionEnabled();
        this.mSelectionTracker.onOriginalSelection(getText(this.mTextView), this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), false);
        cancelAsyncTask();
        if (skipTextClassification()) {
            startSelectionActionMode((SelectionResult) null);
            return;
        }
        resetTextClassificationHelper();
        TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        if (adjustSelection2) {
            TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper);
            r2 = new Supplier() {
                public final Object get() {
                    return SelectionActionModeHelper.TextClassificationHelper.this.suggestSelection();
                }
            };
        } else {
            TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
            Objects.requireNonNull(textClassificationHelper2);
            r2 = new Supplier() {
                public final Object get() {
                    return SelectionActionModeHelper.TextClassificationHelper.this.classifyText();
                }
            };
        }
        Supplier supplier = r2;
        if (this.mSmartSelectSprite != null) {
            r1 = new Consumer() {
                public final void accept(Object obj) {
                    SelectionActionModeHelper.this.startSelectionActionModeWithSmartSelectAnimation((SelectionActionModeHelper.SelectionResult) obj);
                }
            };
        } else {
            r1 = new Consumer() {
                public final void accept(Object obj) {
                    SelectionActionModeHelper.this.startSelectionActionMode((SelectionActionModeHelper.SelectionResult) obj);
                }
            };
        }
        Consumer consumer = r1;
        TextClassificationHelper textClassificationHelper3 = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper3);
        this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, timeoutDuration, supplier, consumer, new Supplier() {
            public final Object get() {
                return SelectionActionModeHelper.TextClassificationHelper.this.getOriginalSelection();
            }
        }).execute((Params[]) new Void[0]);
    }

    public void startLinkActionModeAsync(int start, int end) {
        this.mSelectionTracker.onOriginalSelection(getText(this.mTextView), start, end, true);
        cancelAsyncTask();
        if (skipTextClassification()) {
            startLinkActionMode((SelectionResult) null);
            return;
        }
        resetTextClassificationHelper(start, end);
        TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper);
        $$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI r4 = new Supplier() {
            public final Object get() {
                return SelectionActionModeHelper.TextClassificationHelper.this.classifyText();
            }
        };
        $$Lambda$SelectionActionModeHelper$WnFw1_gP20c3ltvTN6OPqQ5XUns r5 = new Consumer() {
            public final void accept(Object obj) {
                SelectionActionModeHelper.this.startLinkActionMode((SelectionActionModeHelper.SelectionResult) obj);
            }
        };
        TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper2);
        this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, timeoutDuration, r4, r5, new Supplier() {
            public final Object get() {
                return SelectionActionModeHelper.TextClassificationHelper.this.getOriginalSelection();
            }
        }).execute((Params[]) new Void[0]);
    }

    public void invalidateActionModeAsync() {
        cancelAsyncTask();
        if (skipTextClassification()) {
            invalidateActionMode((SelectionResult) null);
            return;
        }
        resetTextClassificationHelper();
        TextView textView = this.mTextView;
        int timeoutDuration = this.mTextClassificationHelper.getTimeoutDuration();
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper);
        $$Lambda$aOGBsMC_jnvTDjezYLRtz35nAPI r4 = new Supplier() {
            public final Object get() {
                return SelectionActionModeHelper.TextClassificationHelper.this.classifyText();
            }
        };
        $$Lambda$SelectionActionModeHelper$Lwzg10CkEpNBaAXBpjnWEpIlTzQ r5 = new Consumer() {
            public final void accept(Object obj) {
                SelectionActionModeHelper.this.invalidateActionMode((SelectionActionModeHelper.SelectionResult) obj);
            }
        };
        TextClassificationHelper textClassificationHelper2 = this.mTextClassificationHelper;
        Objects.requireNonNull(textClassificationHelper2);
        this.mTextClassificationAsyncTask = new TextClassificationAsyncTask(textView, timeoutDuration, r4, r5, new Supplier() {
            public final Object get() {
                return SelectionActionModeHelper.TextClassificationHelper.this.getOriginalSelection();
            }
        }).execute((Params[]) new Void[0]);
    }

    public void onSelectionAction(int menuItemId, String actionLabel) {
        this.mSelectionTracker.onSelectionAction(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), getActionType(menuItemId), actionLabel, this.mTextClassification);
    }

    public void onSelectionDrag() {
        this.mSelectionTracker.onSelectionAction(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), 106, (String) null, this.mTextClassification);
    }

    public void onTextChanged(int start, int end) {
        this.mSelectionTracker.onTextChanged(start, end, this.mTextClassification);
    }

    public boolean resetSelection(int textIndex) {
        if (!this.mSelectionTracker.resetSelection(textIndex, this.mEditor)) {
            return false;
        }
        invalidateActionModeAsync();
        return true;
    }

    public TextClassification getTextClassification() {
        return this.mTextClassification;
    }

    public void onDestroyActionMode() {
        cancelSmartSelectAnimation();
        this.mSelectionTracker.onSelectionDestroyed();
        cancelAsyncTask();
    }

    public void onDraw(Canvas canvas) {
        if (isDrawingHighlight() && this.mSmartSelectSprite != null) {
            this.mSmartSelectSprite.draw(canvas);
        }
    }

    public boolean isDrawingHighlight() {
        return this.mSmartSelectSprite != null && this.mSmartSelectSprite.isAnimationActive();
    }

    private TextClassificationConstants getTextClassificationSettings() {
        return TextClassificationManager.getSettings(this.mTextView.getContext());
    }

    private void cancelAsyncTask() {
        if (this.mTextClassificationAsyncTask != null) {
            this.mTextClassificationAsyncTask.cancel(true);
            this.mTextClassificationAsyncTask = null;
        }
        this.mTextClassification = null;
    }

    private boolean skipTextClassification() {
        boolean noOpTextClassifier = this.mTextView.usesNoOpTextClassifier();
        boolean noSelection = this.mTextView.getSelectionEnd() == this.mTextView.getSelectionStart();
        boolean password = this.mTextView.hasPasswordTransformationMethod() || TextView.isPasswordInputType(this.mTextView.getInputType());
        if (noOpTextClassifier || noSelection || password) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void startLinkActionMode(SelectionResult result) {
        startActionMode(2, result);
    }

    /* access modifiers changed from: private */
    public void startSelectionActionMode(SelectionResult result) {
        startActionMode(0, result);
    }

    private void startActionMode(@Editor.TextActionMode int actionMode, SelectionResult result) {
        CharSequence text = getText(this.mTextView);
        if (result != null && (text instanceof Spannable) && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
            if (!getTextClassificationSettings().isModelDarkLaunchEnabled()) {
                Selection.setSelection((Spannable) text, result.mStart, result.mEnd);
                this.mTextView.invalidate();
            }
            this.mTextClassification = result.mClassification;
        } else if (result == null || actionMode != 2) {
            this.mTextClassification = null;
        } else {
            this.mTextClassification = result.mClassification;
        }
        if (this.mEditor.startActionModeInternal(actionMode)) {
            Editor.SelectionModifierCursorController controller = this.mEditor.getSelectionController();
            if (controller != null && (this.mTextView.isTextSelectable() || this.mTextView.isTextEditable())) {
                controller.show();
            }
            if (result != null) {
                if (actionMode == 0) {
                    this.mSelectionTracker.onSmartSelection(result);
                } else if (actionMode == 2) {
                    this.mSelectionTracker.onLinkSelected(result);
                }
            }
        }
        this.mEditor.setRestartActionModeOnNextRefresh(false);
        this.mTextClassificationAsyncTask = null;
    }

    /* access modifiers changed from: private */
    public void startSelectionActionModeWithSmartSelectAnimation(SelectionResult result) {
        Layout layout = this.mTextView.getLayout();
        Runnable onAnimationEndCallback = new Runnable(result) {
            private final /* synthetic */ SelectionActionModeHelper.SelectionResult f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                SelectionActionModeHelper.lambda$startSelectionActionModeWithSmartSelectAnimation$0(SelectionActionModeHelper.this, this.f$1);
            }
        };
        if (!((result == null || (this.mTextView.getSelectionStart() == result.mStart && this.mTextView.getSelectionEnd() == result.mEnd)) ? false : true)) {
            onAnimationEndCallback.run();
            return;
        }
        List<SmartSelectSprite.RectangleWithTextSelectionLayout> selectionRectangles = convertSelectionToRectangles(layout, result.mStart, result.mEnd);
        this.mSmartSelectSprite.startAnimation(movePointInsideNearestRectangle(new PointF(this.mEditor.getLastUpPositionX(), this.mEditor.getLastUpPositionY()), selectionRectangles, $$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU.INSTANCE), selectionRectangles, onAnimationEndCallback);
    }

    public static /* synthetic */ void lambda$startSelectionActionModeWithSmartSelectAnimation$0(SelectionActionModeHelper selectionActionModeHelper, SelectionResult result) {
        SelectionResult startSelectionResult;
        if (result == null || result.mStart < 0 || result.mEnd > getText(selectionActionModeHelper.mTextView).length() || result.mStart > result.mEnd) {
            startSelectionResult = null;
        } else {
            startSelectionResult = result;
        }
        selectionActionModeHelper.startSelectionActionMode(startSelectionResult);
    }

    private List<SmartSelectSprite.RectangleWithTextSelectionLayout> convertSelectionToRectangles(Layout layout, int start, int end) {
        List<SmartSelectSprite.RectangleWithTextSelectionLayout> result = new ArrayList<>();
        layout.getSelection(start, end, new Layout.SelectionRectangleConsumer(result) {
            private final /* synthetic */ List f$0;

            {
                this.f$0 = r1;
            }

            public final void accept(float f, float f2, float f3, float f4, int i) {
                SelectionActionModeHelper.mergeRectangleIntoList(this.f$0, new RectF(f, f2, f3, f4), $$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU.INSTANCE, new Function(i) {
                    private final /* synthetic */ int f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final Object apply(Object obj) {
                        return SelectionActionModeHelper.lambda$convertSelectionToRectangles$1(this.f$0, (RectF) obj);
                    }
                });
            }
        });
        result.sort(Comparator.comparing($$Lambda$ChL7kntlZCrPaPVdRfaSzGdk1JU.INSTANCE, SmartSelectSprite.RECTANGLE_COMPARATOR));
        return result;
    }

    static /* synthetic */ SmartSelectSprite.RectangleWithTextSelectionLayout lambda$convertSelectionToRectangles$1(int textSelectionLayout, RectF r) {
        return new SmartSelectSprite.RectangleWithTextSelectionLayout(r, textSelectionLayout);
    }

    @VisibleForTesting
    public static <T> void mergeRectangleIntoList(List<T> list, RectF candidate, Function<T, RectF> extractor, Function<RectF, T> packer) {
        if (!candidate.isEmpty()) {
            int elementCount = list.size();
            int index = 0;
            while (index < elementCount) {
                RectF existingRectangle = extractor.apply(list.get(index));
                if (!existingRectangle.contains(candidate)) {
                    if (candidate.contains(existingRectangle)) {
                        existingRectangle.setEmpty();
                    } else {
                        boolean canMerge = true;
                        boolean rectanglesContinueEachOther = candidate.left == existingRectangle.right || candidate.right == existingRectangle.left;
                        if (!(candidate.top == existingRectangle.top && candidate.bottom == existingRectangle.bottom && (RectF.intersects(candidate, existingRectangle) || rectanglesContinueEachOther))) {
                            canMerge = false;
                        }
                        if (canMerge) {
                            candidate.union(existingRectangle);
                            existingRectangle.setEmpty();
                        }
                    }
                    index++;
                } else {
                    return;
                }
            }
            for (int index2 = elementCount - 1; index2 >= 0; index2--) {
                if (extractor.apply(list.get(index2)).isEmpty()) {
                    list.remove(index2);
                }
            }
            list.add(packer.apply(candidate));
        }
    }

    @VisibleForTesting
    public static <T> PointF movePointInsideNearestRectangle(PointF point, List<T> list, Function<T, RectF> extractor) {
        float candidateX;
        PointF pointF = point;
        float bestX = -1.0f;
        float bestY = -1.0f;
        double bestDistance = Double.MAX_VALUE;
        int elementCount = list.size();
        for (int index = 0; index < elementCount; index++) {
            RectF rectangle = extractor.apply(list.get(index));
            float candidateY = rectangle.centerY();
            if (pointF.x > rectangle.right) {
                candidateX = rectangle.right;
            } else if (pointF.x < rectangle.left) {
                candidateX = rectangle.left;
            } else {
                candidateX = pointF.x;
            }
            RectF rectF = rectangle;
            double candidateDistance = Math.pow((double) (pointF.x - candidateX), 2.0d) + Math.pow((double) (pointF.y - candidateY), 2.0d);
            if (candidateDistance < bestDistance) {
                bestX = candidateX;
                bestY = candidateY;
                bestDistance = candidateDistance;
            }
        }
        Function<T, RectF> function = extractor;
        return new PointF(bestX, bestY);
    }

    /* access modifiers changed from: private */
    public void invalidateActionMode(SelectionResult result) {
        cancelSmartSelectAnimation();
        this.mTextClassification = result != null ? result.mClassification : null;
        ActionMode actionMode = this.mEditor.getTextActionMode();
        if (actionMode != null) {
            actionMode.invalidate();
        }
        this.mSelectionTracker.onSelectionUpdated(this.mTextView.getSelectionStart(), this.mTextView.getSelectionEnd(), this.mTextClassification);
        this.mTextClassificationAsyncTask = null;
    }

    private void resetTextClassificationHelper(int selectionStart, int selectionEnd) {
        if (selectionStart < 0 || selectionEnd < 0) {
            selectionStart = this.mTextView.getSelectionStart();
            selectionEnd = this.mTextView.getSelectionEnd();
        }
        TextClassificationHelper textClassificationHelper = this.mTextClassificationHelper;
        TextView textView = this.mTextView;
        Objects.requireNonNull(textView);
        textClassificationHelper.init(new Supplier() {
            public final Object get() {
                return TextView.this.getTextClassifier();
            }
        }, getText(this.mTextView), selectionStart, selectionEnd, this.mTextView.getTextLocales());
    }

    private void resetTextClassificationHelper() {
        resetTextClassificationHelper(-1, -1);
    }

    private void cancelSmartSelectAnimation() {
        if (this.mSmartSelectSprite != null) {
            this.mSmartSelectSprite.cancelAnimation();
        }
    }

    private static final class SelectionTracker {
        private boolean mAllowReset;
        private final LogAbandonRunnable mDelayedLogAbandon = new LogAbandonRunnable();
        /* access modifiers changed from: private */
        public SelectionMetricsLogger mLogger;
        private int mOriginalEnd;
        private int mOriginalStart;
        /* access modifiers changed from: private */
        public int mSelectionEnd;
        /* access modifiers changed from: private */
        public int mSelectionStart;
        /* access modifiers changed from: private */
        public final TextView mTextView;

        SelectionTracker(TextView textView) {
            this.mTextView = (TextView) Preconditions.checkNotNull(textView);
            this.mLogger = new SelectionMetricsLogger(textView);
        }

        public void onOriginalSelection(CharSequence text, int selectionStart, int selectionEnd, boolean isLink) {
            int i;
            this.mDelayedLogAbandon.flush();
            this.mSelectionStart = selectionStart;
            this.mOriginalStart = selectionStart;
            this.mSelectionEnd = selectionEnd;
            this.mOriginalEnd = selectionEnd;
            this.mAllowReset = false;
            maybeInvalidateLogger();
            SelectionMetricsLogger selectionMetricsLogger = this.mLogger;
            TextClassifier textClassificationSession = this.mTextView.getTextClassificationSession();
            TextClassificationContext textClassificationContext = this.mTextView.getTextClassificationContext();
            if (isLink) {
                i = 2;
            } else {
                i = 1;
            }
            selectionMetricsLogger.logSelectionStarted(textClassificationSession, textClassificationContext, text, selectionStart, i);
        }

        public void onSmartSelection(SelectionResult result) {
            onClassifiedSelection(result);
            this.mLogger.logSelectionModified(result.mStart, result.mEnd, result.mClassification, result.mSelection);
        }

        public void onLinkSelected(SelectionResult result) {
            onClassifiedSelection(result);
        }

        private void onClassifiedSelection(SelectionResult result) {
            if (isSelectionStarted()) {
                this.mSelectionStart = result.mStart;
                this.mSelectionEnd = result.mEnd;
                this.mAllowReset = (this.mSelectionStart == this.mOriginalStart && this.mSelectionEnd == this.mOriginalEnd) ? false : true;
            }
        }

        public void onSelectionUpdated(int selectionStart, int selectionEnd, TextClassification classification) {
            if (isSelectionStarted()) {
                this.mSelectionStart = selectionStart;
                this.mSelectionEnd = selectionEnd;
                this.mAllowReset = false;
                this.mLogger.logSelectionModified(selectionStart, selectionEnd, classification, (TextSelection) null);
            }
        }

        public void onSelectionDestroyed() {
            this.mAllowReset = false;
            this.mDelayedLogAbandon.schedule(100);
        }

        public void onSelectionAction(int selectionStart, int selectionEnd, int action, String actionLabel, TextClassification classification) {
            if (isSelectionStarted()) {
                this.mAllowReset = false;
                this.mLogger.logSelectionAction(selectionStart, selectionEnd, action, actionLabel, classification);
            }
        }

        public boolean resetSelection(int textIndex, Editor editor) {
            TextView textView = editor.getTextView();
            if (!isSelectionStarted() || !this.mAllowReset || textIndex < this.mSelectionStart || textIndex > this.mSelectionEnd || !(SelectionActionModeHelper.getText(textView) instanceof Spannable)) {
                return false;
            }
            this.mAllowReset = false;
            boolean selected = editor.selectCurrentWord();
            if (selected) {
                this.mSelectionStart = editor.getTextView().getSelectionStart();
                this.mSelectionEnd = editor.getTextView().getSelectionEnd();
                this.mLogger.logSelectionAction(textView.getSelectionStart(), textView.getSelectionEnd(), 201, (String) null, (TextClassification) null);
            }
            return selected;
        }

        public void onTextChanged(int start, int end, TextClassification classification) {
            if (isSelectionStarted() && start == this.mSelectionStart && end == this.mSelectionEnd) {
                onSelectionAction(start, end, 100, (String) null, classification);
            }
        }

        private void maybeInvalidateLogger() {
            if (this.mLogger.isEditTextLogger() != this.mTextView.isTextEditable()) {
                this.mLogger = new SelectionMetricsLogger(this.mTextView);
            }
        }

        private boolean isSelectionStarted() {
            return this.mSelectionStart >= 0 && this.mSelectionEnd >= 0 && this.mSelectionStart != this.mSelectionEnd;
        }

        private final class LogAbandonRunnable implements Runnable {
            private boolean mIsPending;

            private LogAbandonRunnable() {
            }

            /* access modifiers changed from: package-private */
            public void schedule(int delayMillis) {
                if (this.mIsPending) {
                    Log.e(SelectionActionModeHelper.LOG_TAG, "Force flushing abandon due to new scheduling request");
                    flush();
                }
                this.mIsPending = true;
                SelectionTracker.this.mTextView.postDelayed(this, (long) delayMillis);
            }

            /* access modifiers changed from: package-private */
            public void flush() {
                SelectionTracker.this.mTextView.removeCallbacks(this);
                run();
            }

            public void run() {
                if (this.mIsPending) {
                    SelectionTracker.this.mLogger.logSelectionAction(SelectionTracker.this.mSelectionStart, SelectionTracker.this.mSelectionEnd, 107, (String) null, (TextClassification) null);
                    int unused = SelectionTracker.this.mSelectionStart = SelectionTracker.this.mSelectionEnd = -1;
                    SelectionTracker.this.mLogger.endTextClassificationSession();
                    this.mIsPending = false;
                }
            }
        }
    }

    private static final class SelectionMetricsLogger {
        private static final String LOG_TAG = "SelectionMetricsLogger";
        private static final Pattern PATTERN_WHITESPACE = Pattern.compile("\\s+");
        private TextClassificationContext mClassificationContext;
        private TextClassifier mClassificationSession;
        private final boolean mEditTextLogger;
        private int mStartIndex;
        private String mText;
        private final BreakIterator mTokenIterator;
        private TextClassifierEvent mTranslateClickEvent;
        private TextClassifierEvent mTranslateViewEvent;

        SelectionMetricsLogger(TextView textView) {
            Preconditions.checkNotNull(textView);
            this.mEditTextLogger = textView.isTextEditable();
            this.mTokenIterator = SelectionSessionLogger.getTokenIterator(textView.getTextLocale());
        }

        public void logSelectionStarted(TextClassifier classificationSession, TextClassificationContext classificationContext, CharSequence text, int index, int invocationMethod) {
            try {
                Preconditions.checkNotNull(text);
                Preconditions.checkArgumentInRange(index, 0, text.length(), "index");
                if (this.mText == null || !this.mText.contentEquals(text)) {
                    this.mText = text.toString();
                }
                this.mTokenIterator.setText(this.mText);
                this.mStartIndex = index;
                this.mClassificationSession = classificationSession;
                this.mClassificationContext = classificationContext;
                if (hasActiveClassificationSession()) {
                    this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionStartedEvent(invocationMethod, 0));
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public void logSelectionModified(int start, int end, TextClassification classification, TextSelection selection) {
            try {
                if (hasActiveClassificationSession()) {
                    Preconditions.checkArgumentInRange(start, 0, this.mText.length(), Telephony.BaseMmsColumns.START);
                    Preconditions.checkArgumentInRange(end, start, this.mText.length(), "end");
                    int[] wordIndices = getWordDelta(start, end);
                    if (selection != null) {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(wordIndices[0], wordIndices[1], selection));
                    } else if (classification != null) {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(wordIndices[0], wordIndices[1], classification));
                    } else {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionModifiedEvent(wordIndices[0], wordIndices[1]));
                    }
                    maybeGenerateTranslateViewEvent(classification);
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public void logSelectionAction(int start, int end, int action, String actionLabel, TextClassification classification) {
            try {
                if (hasActiveClassificationSession()) {
                    Preconditions.checkArgumentInRange(start, 0, this.mText.length(), Telephony.BaseMmsColumns.START);
                    Preconditions.checkArgumentInRange(end, start, this.mText.length(), "end");
                    int[] wordIndices = getWordDelta(start, end);
                    if (classification != null) {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionActionEvent(wordIndices[0], wordIndices[1], action, classification));
                    } else {
                        this.mClassificationSession.onSelectionEvent(SelectionEvent.createSelectionActionEvent(wordIndices[0], wordIndices[1], action));
                    }
                    maybeGenerateTranslateClickEvent(classification, actionLabel);
                    if (SelectionEvent.isTerminal(action)) {
                        endTextClassificationSession();
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "" + e.getMessage(), e);
            }
        }

        public boolean isEditTextLogger() {
            return this.mEditTextLogger;
        }

        public void endTextClassificationSession() {
            if (hasActiveClassificationSession()) {
                maybeReportTranslateEvents();
                this.mClassificationSession.destroy();
            }
        }

        private boolean hasActiveClassificationSession() {
            return this.mClassificationSession != null && !this.mClassificationSession.isDestroyed();
        }

        private int[] getWordDelta(int start, int end) {
            int[] wordIndices = new int[2];
            if (start == this.mStartIndex) {
                wordIndices[0] = 0;
            } else if (start < this.mStartIndex) {
                wordIndices[0] = -countWordsForward(start);
            } else {
                wordIndices[0] = countWordsBackward(start);
                if (!this.mTokenIterator.isBoundary(start) && !isWhitespace(this.mTokenIterator.preceding(start), this.mTokenIterator.following(start))) {
                    wordIndices[0] = wordIndices[0] - 1;
                }
            }
            if (end == this.mStartIndex) {
                wordIndices[1] = 0;
            } else if (end < this.mStartIndex) {
                wordIndices[1] = -countWordsForward(end);
            } else {
                wordIndices[1] = countWordsBackward(end);
            }
            return wordIndices;
        }

        private int countWordsBackward(int from) {
            Preconditions.checkArgument(from >= this.mStartIndex);
            int wordCount = 0;
            int offset = from;
            while (offset > this.mStartIndex) {
                int start = this.mTokenIterator.preceding(offset);
                if (!isWhitespace(start, offset)) {
                    wordCount++;
                }
                offset = start;
            }
            return wordCount;
        }

        private int countWordsForward(int from) {
            Preconditions.checkArgument(from <= this.mStartIndex);
            int wordCount = 0;
            int offset = from;
            while (offset < this.mStartIndex) {
                int end = this.mTokenIterator.following(offset);
                if (!isWhitespace(offset, end)) {
                    wordCount++;
                }
                offset = end;
            }
            return wordCount;
        }

        private boolean isWhitespace(int start, int end) {
            return PATTERN_WHITESPACE.matcher(this.mText.substring(start, end)).matches();
        }

        private void maybeGenerateTranslateViewEvent(TextClassification classification) {
            if (classification != null) {
                TextClassifierEvent event = generateTranslateEvent(6, classification, this.mClassificationContext, (String) null);
                this.mTranslateViewEvent = event != null ? event : this.mTranslateViewEvent;
            }
        }

        private void maybeGenerateTranslateClickEvent(TextClassification classification, String actionLabel) {
            if (classification != null) {
                this.mTranslateClickEvent = generateTranslateEvent(13, classification, this.mClassificationContext, actionLabel);
            }
        }

        private void maybeReportTranslateEvents() {
            if (this.mTranslateViewEvent != null) {
                this.mClassificationSession.onTextClassifierEvent(this.mTranslateViewEvent);
                this.mTranslateViewEvent = null;
            }
            if (this.mTranslateClickEvent != null) {
                this.mClassificationSession.onTextClassifierEvent(this.mTranslateClickEvent);
                this.mTranslateClickEvent = null;
            }
        }

        private static TextClassifierEvent generateTranslateEvent(int eventType, TextClassification classification, TextClassificationContext classificationContext, String actionLabel) {
            RemoteAction translateAction = ExtrasUtils.findTranslateAction(classification);
            if (translateAction == null) {
                return null;
            }
            if (eventType == 13 && !translateAction.getTitle().toString().equals(actionLabel)) {
                return null;
            }
            Bundle foreignLanguageExtra = ExtrasUtils.getForeignLanguageExtra(classification);
            String language = ExtrasUtils.getEntityType(foreignLanguageExtra);
            float score = ExtrasUtils.getScore(foreignLanguageExtra);
            String[] strArr = {language};
            float[] fArr = {score};
            return ((TextClassifierEvent.LanguageDetectionEvent.Builder) ((TextClassifierEvent.LanguageDetectionEvent.Builder) ((TextClassifierEvent.LanguageDetectionEvent.Builder) ((TextClassifierEvent.LanguageDetectionEvent.Builder) ((TextClassifierEvent.LanguageDetectionEvent.Builder) ((TextClassifierEvent.LanguageDetectionEvent.Builder) new TextClassifierEvent.LanguageDetectionEvent.Builder(eventType).setEventContext(classificationContext)).setResultId(classification.getId())).setEntityTypes(strArr)).setScores(fArr)).setActionIndices(classification.getActions().indexOf(translateAction))).setModelName(ExtrasUtils.getModelName(foreignLanguageExtra))).build();
        }
    }

    private static final class TextClassificationAsyncTask extends AsyncTask<Void, Void, SelectionResult> {
        private final String mOriginalText;
        private final Consumer<SelectionResult> mSelectionResultCallback;
        private final Supplier<SelectionResult> mSelectionResultSupplier;
        private final TextView mTextView;
        private final int mTimeOutDuration;
        private final Supplier<SelectionResult> mTimeOutResultSupplier;

        /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Object, java.util.function.Supplier<android.widget.SelectionActionModeHelper$SelectionResult>] */
        /* JADX WARNING: type inference failed for: r5v0, types: [java.util.function.Consumer<android.widget.SelectionActionModeHelper$SelectionResult>, java.lang.Object] */
        /* JADX WARNING: type inference failed for: r6v0, types: [java.lang.Object, java.util.function.Supplier<android.widget.SelectionActionModeHelper$SelectionResult>] */
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        TextClassificationAsyncTask(android.widget.TextView r2, int r3, java.util.function.Supplier<android.widget.SelectionActionModeHelper.SelectionResult> r4, java.util.function.Consumer<android.widget.SelectionActionModeHelper.SelectionResult> r5, java.util.function.Supplier<android.widget.SelectionActionModeHelper.SelectionResult> r6) {
            /*
                r1 = this;
                if (r2 == 0) goto L_0x0007
                android.os.Handler r0 = r2.getHandler()
                goto L_0x0008
            L_0x0007:
                r0 = 0
            L_0x0008:
                r1.<init>((android.os.Handler) r0)
                java.lang.Object r0 = com.android.internal.util.Preconditions.checkNotNull(r2)
                android.widget.TextView r0 = (android.widget.TextView) r0
                r1.mTextView = r0
                r1.mTimeOutDuration = r3
                java.lang.Object r0 = com.android.internal.util.Preconditions.checkNotNull(r4)
                java.util.function.Supplier r0 = (java.util.function.Supplier) r0
                r1.mSelectionResultSupplier = r0
                java.lang.Object r0 = com.android.internal.util.Preconditions.checkNotNull(r5)
                java.util.function.Consumer r0 = (java.util.function.Consumer) r0
                r1.mSelectionResultCallback = r0
                java.lang.Object r0 = com.android.internal.util.Preconditions.checkNotNull(r6)
                java.util.function.Supplier r0 = (java.util.function.Supplier) r0
                r1.mTimeOutResultSupplier = r0
                android.widget.TextView r0 = r1.mTextView
                java.lang.CharSequence r0 = android.widget.SelectionActionModeHelper.getText(r0)
                java.lang.String r0 = r0.toString()
                r1.mOriginalText = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.SelectionActionModeHelper.TextClassificationAsyncTask.<init>(android.widget.TextView, int, java.util.function.Supplier, java.util.function.Consumer, java.util.function.Supplier):void");
        }

        /* access modifiers changed from: protected */
        public SelectionResult doInBackground(Void... params) {
            Runnable onTimeOut = new Runnable() {
                public final void run() {
                    SelectionActionModeHelper.TextClassificationAsyncTask.this.onTimeOut();
                }
            };
            this.mTextView.postDelayed(onTimeOut, (long) this.mTimeOutDuration);
            SelectionResult result = this.mSelectionResultSupplier.get();
            this.mTextView.removeCallbacks(onTimeOut);
            return result;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(SelectionResult result) {
            this.mSelectionResultCallback.accept(TextUtils.equals(this.mOriginalText, SelectionActionModeHelper.getText(this.mTextView)) ? result : null);
        }

        /* access modifiers changed from: private */
        public void onTimeOut() {
            if (getStatus() == AsyncTask.Status.RUNNING) {
                onPostExecute(this.mTimeOutResultSupplier.get());
            }
            cancel(true);
        }
    }

    private static final class TextClassificationHelper {
        private static final int TRIM_DELTA = 120;
        private final Context mContext;
        private LocaleList mDefaultLocales;
        private boolean mHot;
        private LocaleList mLastClassificationLocales;
        private SelectionResult mLastClassificationResult;
        private int mLastClassificationSelectionEnd;
        private int mLastClassificationSelectionStart;
        private CharSequence mLastClassificationText;
        private int mRelativeEnd;
        private int mRelativeStart;
        private int mSelectionEnd;
        private int mSelectionStart;
        private String mText;
        private Supplier<TextClassifier> mTextClassifier;
        private int mTrimStart;
        private CharSequence mTrimmedText;

        TextClassificationHelper(Context context, Supplier<TextClassifier> textClassifier, CharSequence text, int selectionStart, int selectionEnd, LocaleList locales) {
            init(textClassifier, text, selectionStart, selectionEnd, locales);
            this.mContext = (Context) Preconditions.checkNotNull(context);
        }

        /* JADX WARNING: type inference failed for: r2v0, types: [java.util.function.Supplier<android.view.textclassifier.TextClassifier>, java.lang.Object] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void init(java.util.function.Supplier<android.view.textclassifier.TextClassifier> r2, java.lang.CharSequence r3, int r4, int r5, android.os.LocaleList r6) {
            /*
                r1 = this;
                java.lang.Object r0 = com.android.internal.util.Preconditions.checkNotNull(r2)
                java.util.function.Supplier r0 = (java.util.function.Supplier) r0
                r1.mTextClassifier = r0
                java.lang.Object r0 = com.android.internal.util.Preconditions.checkNotNull(r3)
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                java.lang.String r0 = r0.toString()
                r1.mText = r0
                r0 = 0
                r1.mLastClassificationText = r0
                if (r5 <= r4) goto L_0x001b
                r0 = 1
                goto L_0x001c
            L_0x001b:
                r0 = 0
            L_0x001c:
                com.android.internal.util.Preconditions.checkArgument(r0)
                r1.mSelectionStart = r4
                r1.mSelectionEnd = r5
                r1.mDefaultLocales = r6
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.SelectionActionModeHelper.TextClassificationHelper.init(java.util.function.Supplier, java.lang.CharSequence, int, int, android.os.LocaleList):void");
        }

        public SelectionResult classifyText() {
            this.mHot = true;
            return performClassification((TextSelection) null);
        }

        public SelectionResult suggestSelection() {
            TextSelection selection;
            this.mHot = true;
            trimText();
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                selection = this.mTextClassifier.get().suggestSelection(new TextSelection.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).setDarkLaunchAllowed(true).build());
            } else {
                selection = this.mTextClassifier.get().suggestSelection(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
            }
            if (!isDarkLaunchEnabled()) {
                this.mSelectionStart = Math.max(0, selection.getSelectionStartIndex() + this.mTrimStart);
                this.mSelectionEnd = Math.min(this.mText.length(), selection.getSelectionEndIndex() + this.mTrimStart);
            }
            return performClassification(selection);
        }

        public SelectionResult getOriginalSelection() {
            return new SelectionResult(this.mSelectionStart, this.mSelectionEnd, (TextClassification) null, (TextSelection) null);
        }

        public int getTimeoutDuration() {
            if (this.mHot) {
                return 200;
            }
            return 500;
        }

        private boolean isDarkLaunchEnabled() {
            return TextClassificationManager.getSettings(this.mContext).isModelDarkLaunchEnabled();
        }

        private SelectionResult performClassification(TextSelection selection) {
            TextClassification classification;
            if (!Objects.equals(this.mText, this.mLastClassificationText) || this.mSelectionStart != this.mLastClassificationSelectionStart || this.mSelectionEnd != this.mLastClassificationSelectionEnd || !Objects.equals(this.mDefaultLocales, this.mLastClassificationLocales)) {
                this.mLastClassificationText = this.mText;
                this.mLastClassificationSelectionStart = this.mSelectionStart;
                this.mLastClassificationSelectionEnd = this.mSelectionEnd;
                this.mLastClassificationLocales = this.mDefaultLocales;
                trimText();
                if (Linkify.containsUnsupportedCharacters(this.mText)) {
                    EventLog.writeEvent(1397638484, "116321860", -1, "");
                    classification = TextClassification.EMPTY;
                } else if (this.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                    classification = this.mTextClassifier.get().classifyText(new TextClassification.Request.Builder(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd).setDefaultLocales(this.mDefaultLocales).build());
                } else {
                    classification = this.mTextClassifier.get().classifyText(this.mTrimmedText, this.mRelativeStart, this.mRelativeEnd, this.mDefaultLocales);
                }
                this.mLastClassificationResult = new SelectionResult(this.mSelectionStart, this.mSelectionEnd, classification, selection);
            }
            return this.mLastClassificationResult;
        }

        private void trimText() {
            this.mTrimStart = Math.max(0, this.mSelectionStart + PackageManager.INSTALL_FAILED_MULTIPACKAGE_INCONSISTENCY);
            this.mTrimmedText = this.mText.subSequence(this.mTrimStart, Math.min(this.mText.length(), this.mSelectionEnd + 120));
            this.mRelativeStart = this.mSelectionStart - this.mTrimStart;
            this.mRelativeEnd = this.mSelectionEnd - this.mTrimStart;
        }
    }

    private static final class SelectionResult {
        /* access modifiers changed from: private */
        public final TextClassification mClassification;
        /* access modifiers changed from: private */
        public final int mEnd;
        /* access modifiers changed from: private */
        public final TextSelection mSelection;
        /* access modifiers changed from: private */
        public final int mStart;

        SelectionResult(int start, int end, TextClassification classification, TextSelection selection) {
            this.mStart = start;
            this.mEnd = end;
            this.mClassification = classification;
            this.mSelection = selection;
        }
    }

    private static int getActionType(int menuItemId) {
        if (menuItemId == 16908337) {
            return 102;
        }
        if (menuItemId == 16908341) {
            return 104;
        }
        if (menuItemId == 16908353) {
            return 105;
        }
        switch (menuItemId) {
            case 16908319:
                return 200;
            case 16908320:
                return 103;
            case 16908321:
                return 101;
            case 16908322:
                return 102;
            default:
                return 108;
        }
    }

    /* access modifiers changed from: private */
    public static CharSequence getText(TextView textView) {
        CharSequence text = textView.getText();
        if (text != null) {
            return text;
        }
        return "";
    }
}
