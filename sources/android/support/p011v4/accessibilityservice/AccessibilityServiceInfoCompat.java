package android.support.p011v4.accessibilityservice;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.p007os.Build;
import android.support.annotation.RequiresApi;
import com.android.internal.telephony.IccCardConstants;

/* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat */
/* loaded from: classes3.dex */
public final class AccessibilityServiceInfoCompat {
    public static final int CAPABILITY_CAN_FILTER_KEY_EVENTS = 8;
    public static final int CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 4;
    public static final int CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION = 2;
    public static final int CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT = 1;
    @Deprecated
    public static final int DEFAULT = 1;
    public static final int FEEDBACK_ALL_MASK = -1;
    public static final int FEEDBACK_BRAILLE = 32;
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY = 8;
    public static final int FLAG_REQUEST_FILTER_KEY_EVENTS = 32;
    public static final int FLAG_REQUEST_TOUCH_EXPLORATION_MODE = 4;
    private static final AccessibilityServiceInfoBaseImpl IMPL;

    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoBaseImpl */
    /* loaded from: classes3.dex */
    static class AccessibilityServiceInfoBaseImpl {
        AccessibilityServiceInfoBaseImpl() {
        }

        public int getCapabilities(AccessibilityServiceInfo info) {
            if (AccessibilityServiceInfoCompat.getCanRetrieveWindowContent(info)) {
                return 1;
            }
            return 0;
        }

        public String loadDescription(AccessibilityServiceInfo info, PackageManager pm) {
            return null;
        }
    }

    @RequiresApi(16)
    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoApi16Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityServiceInfoApi16Impl extends AccessibilityServiceInfoBaseImpl {
        AccessibilityServiceInfoApi16Impl() {
        }

        @Override // android.support.p011v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoBaseImpl
        public String loadDescription(AccessibilityServiceInfo info, PackageManager pm) {
            return info.loadDescription(pm);
        }
    }

    @RequiresApi(18)
    /* renamed from: android.support.v4.accessibilityservice.AccessibilityServiceInfoCompat$AccessibilityServiceInfoApi18Impl */
    /* loaded from: classes3.dex */
    static class AccessibilityServiceInfoApi18Impl extends AccessibilityServiceInfoApi16Impl {
        AccessibilityServiceInfoApi18Impl() {
        }

        @Override // android.support.p011v4.accessibilityservice.AccessibilityServiceInfoCompat.AccessibilityServiceInfoBaseImpl
        public int getCapabilities(AccessibilityServiceInfo info) {
            return info.getCapabilities();
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityServiceInfoApi18Impl();
        } else if (Build.VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityServiceInfoApi16Impl();
        } else {
            IMPL = new AccessibilityServiceInfoBaseImpl();
        }
    }

    private AccessibilityServiceInfoCompat() {
    }

    @Deprecated
    public static String getId(AccessibilityServiceInfo info) {
        return info.getId();
    }

    @Deprecated
    public static ResolveInfo getResolveInfo(AccessibilityServiceInfo info) {
        return info.getResolveInfo();
    }

    @Deprecated
    public static String getSettingsActivityName(AccessibilityServiceInfo info) {
        return info.getSettingsActivityName();
    }

    @Deprecated
    public static boolean getCanRetrieveWindowContent(AccessibilityServiceInfo info) {
        return info.getCanRetrieveWindowContent();
    }

    @Deprecated
    public static String getDescription(AccessibilityServiceInfo info) {
        return info.getDescription();
    }

    public static String loadDescription(AccessibilityServiceInfo info, PackageManager packageManager) {
        return IMPL.loadDescription(info, packageManager);
    }

    public static String feedbackTypeToString(int feedbackType) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        while (feedbackType > 0) {
            int feedbackTypeFlag = 1 << Integer.numberOfTrailingZeros(feedbackType);
            feedbackType &= ~feedbackTypeFlag;
            if (builder.length() > 1) {
                builder.append(", ");
            }
            if (feedbackTypeFlag == 4) {
                builder.append("FEEDBACK_AUDIBLE");
            } else if (feedbackTypeFlag == 8) {
                builder.append("FEEDBACK_VISUAL");
            } else if (feedbackTypeFlag == 16) {
                builder.append("FEEDBACK_GENERIC");
            } else {
                switch (feedbackTypeFlag) {
                    case 1:
                        builder.append("FEEDBACK_SPOKEN");
                        continue;
                    case 2:
                        builder.append("FEEDBACK_HAPTIC");
                        continue;
                }
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public static String flagToString(int flag) {
        if (flag != 4) {
            if (flag != 8) {
                if (flag != 16) {
                    if (flag != 32) {
                        switch (flag) {
                            case 1:
                                return "DEFAULT";
                            case 2:
                                return "FLAG_INCLUDE_NOT_IMPORTANT_VIEWS";
                            default:
                                return null;
                        }
                    }
                    return "FLAG_REQUEST_FILTER_KEY_EVENTS";
                }
                return "FLAG_REPORT_VIEW_IDS";
            }
            return "FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
        }
        return "FLAG_REQUEST_TOUCH_EXPLORATION_MODE";
    }

    public static int getCapabilities(AccessibilityServiceInfo info) {
        return IMPL.getCapabilities(info);
    }

    public static String capabilityToString(int capability) {
        if (capability != 4) {
            if (capability != 8) {
                switch (capability) {
                    case 1:
                        return "CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT";
                    case 2:
                        return "CAPABILITY_CAN_REQUEST_TOUCH_EXPLORATION";
                    default:
                        return IccCardConstants.INTENT_VALUE_ICC_UNKNOWN;
                }
            }
            return "CAPABILITY_CAN_FILTER_KEY_EVENTS";
        }
        return "CAPABILITY_CAN_REQUEST_ENHANCED_WEB_ACCESSIBILITY";
    }
}
