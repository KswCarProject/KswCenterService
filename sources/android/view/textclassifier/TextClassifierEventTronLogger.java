package android.view.textclassifier;

import android.metrics.LogMaker;
import android.view.textclassifier.SelectionSessionLogger;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.Preconditions;

public final class TextClassifierEventTronLogger {
    private static final String TAG = "TCEventTronLogger";
    private final MetricsLogger mMetricsLogger;

    public TextClassifierEventTronLogger() {
        this(new MetricsLogger());
    }

    @VisibleForTesting
    public TextClassifierEventTronLogger(MetricsLogger metricsLogger) {
        this.mMetricsLogger = (MetricsLogger) Preconditions.checkNotNull(metricsLogger);
    }

    public void writeEvent(TextClassifierEvent event) {
        Preconditions.checkNotNull(event);
        int category = getCategory(event);
        if (category == -1) {
            Log.w(TAG, "Unknown category: " + event.getEventCategory());
            return;
        }
        LogMaker log = new LogMaker(category).setSubtype(getLogType(event)).addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SESSION_ID, event.getResultId()).addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXTCLASSIFIER_MODEL, getModelName(event));
        if (event.getScores().length >= 1) {
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SCORE, Float.valueOf(event.getScores()[0]));
        }
        String[] entityTypes = event.getEntityTypes();
        if (entityTypes.length >= 1) {
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_FIRST_ENTITY_TYPE, entityTypes[0]);
        }
        if (entityTypes.length >= 2) {
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SECOND_ENTITY_TYPE, entityTypes[1]);
        }
        if (entityTypes.length >= 3) {
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_THIRD_ENTITY_TYPE, entityTypes[2]);
        }
        TextClassificationContext eventContext = event.getEventContext();
        if (eventContext != null) {
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_WIDGET_TYPE, eventContext.getWidgetType());
            log.addTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_WIDGET_VERSION, eventContext.getWidgetVersion());
            log.setPackageName(eventContext.getPackageName());
        }
        this.mMetricsLogger.write(log);
        debugLog(log);
    }

    private static String getModelName(TextClassifierEvent event) {
        if (event.getModelName() != null) {
            return event.getModelName();
        }
        return SelectionSessionLogger.SignatureParser.getModelName(event.getResultId());
    }

    private static int getCategory(TextClassifierEvent event) {
        switch (event.getEventCategory()) {
            case 3:
                return 1615;
            case 4:
                return 1614;
            default:
                return -1;
        }
    }

    private static int getLogType(TextClassifierEvent event) {
        int eventType = event.getEventType();
        if (eventType == 6) {
            return 1616;
        }
        if (eventType == 13) {
            return MetricsProto.MetricsEvent.ACTION_TEXT_SELECTION_SMART_SHARE;
        }
        switch (eventType) {
            case 19:
                return 1618;
            case 20:
                return 1619;
            default:
                return 0;
        }
    }

    private String toCategoryName(int category) {
        switch (category) {
            case 1614:
                return "language_detection";
            case 1615:
                return "conversation_actions";
            default:
                return "unknown";
        }
    }

    private String toEventName(int logType) {
        if (logType == 1113) {
            return "smart_share";
        }
        if (logType == 1616) {
            return "actions_shown";
        }
        switch (logType) {
            case 1618:
                return "manual_reply";
            case 1619:
                return "actions_generated";
            default:
                return "unknown";
        }
    }

    private void debugLog(LogMaker log) {
        if (Log.ENABLE_FULL_LOGGING) {
            String id = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SESSION_ID));
            String categoryName = toCategoryName(log.getCategory());
            String eventName = toEventName(log.getSubtype());
            String widgetType = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_WIDGET_TYPE));
            String widgetVersion = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_WIDGET_VERSION));
            String model = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXTCLASSIFIER_MODEL));
            String firstEntityType = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_FIRST_ENTITY_TYPE));
            String secondEntityType = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SECOND_ENTITY_TYPE));
            String thirdEntityType = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_THIRD_ENTITY_TYPE));
            String score = String.valueOf(log.getTaggedData(MetricsProto.MetricsEvent.FIELD_TEXT_CLASSIFIER_SCORE));
            Log.v(TAG, "writeEvent: " + "id=" + id + ", category=" + categoryName + ", eventName=" + eventName + ", widgetType=" + widgetType + ", widgetVersion=" + widgetVersion + ", model=" + model + ", firstEntityType=" + firstEntityType + ", secondEntityType=" + secondEntityType + ", thirdEntityType=" + thirdEntityType + ", score=" + score);
        }
    }
}
