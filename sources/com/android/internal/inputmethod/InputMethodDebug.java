package com.android.internal.inputmethod;

import java.util.StringJoiner;

/* loaded from: classes4.dex */
public final class InputMethodDebug {
    private InputMethodDebug() {
    }

    public static String startInputReasonToString(int reason) {
        switch (reason) {
            case 0:
                return "UNSPECIFIED";
            case 1:
                return "WINDOW_FOCUS_GAIN";
            case 2:
                return "WINDOW_FOCUS_GAIN_REPORT_ONLY";
            case 3:
                return "APP_CALLED_RESTART_INPUT_API";
            case 4:
                return "CHECK_FOCUS";
            case 5:
                return "BOUND_TO_IMMS";
            case 6:
                return "UNBOUND_FROM_IMMS";
            case 7:
                return "ACTIVATED_BY_IMMS";
            case 8:
                return "DEACTIVATED_BY_IMMS";
            case 9:
                return "SESSION_CREATED_BY_IME";
            default:
                return "Unknown=" + reason;
        }
    }

    public static String unbindReasonToString(int reason) {
        switch (reason) {
            case 0:
                return "UNSPECIFIED";
            case 1:
                return "SWITCH_CLIENT";
            case 2:
                return "SWITCH_IME";
            case 3:
                return "DISCONNECT_IME";
            case 4:
                return "NO_IME";
            case 5:
                return "SWITCH_IME_FAILED";
            case 6:
                return "SWITCH_USER";
            default:
                return "Unknown=" + reason;
        }
    }

    public static String softInputModeToString(int softInputMode) {
        StringJoiner joiner = new StringJoiner("|");
        int state = softInputMode & 15;
        int adjust = softInputMode & 240;
        boolean isForwardNav = (softInputMode & 256) != 0;
        switch (state) {
            case 0:
                joiner.add("STATE_UNSPECIFIED");
                break;
            case 1:
                joiner.add("STATE_UNCHANGED");
                break;
            case 2:
                joiner.add("STATE_HIDDEN");
                break;
            case 3:
                joiner.add("STATE_ALWAYS_HIDDEN");
                break;
            case 4:
                joiner.add("STATE_VISIBLE");
                break;
            case 5:
                joiner.add("STATE_ALWAYS_VISIBLE");
                break;
            default:
                joiner.add("STATE_UNKNOWN(" + state + ")");
                break;
        }
        if (adjust == 0) {
            joiner.add("ADJUST_UNSPECIFIED");
        } else if (adjust == 16) {
            joiner.add("ADJUST_RESIZE");
        } else if (adjust == 32) {
            joiner.add("ADJUST_PAN");
        } else if (adjust == 48) {
            joiner.add("ADJUST_NOTHING");
        } else {
            joiner.add("ADJUST_UNKNOWN(" + adjust + ")");
        }
        if (isForwardNav) {
            joiner.add("IS_FORWARD_NAVIGATION");
        }
        return joiner.setEmptyValue("(none)").toString();
    }

    public static String startInputFlagsToString(int startInputFlags) {
        StringJoiner joiner = new StringJoiner("|");
        if ((startInputFlags & 1) != 0) {
            joiner.add("VIEW_HAS_FOCUS");
        }
        if ((startInputFlags & 2) != 0) {
            joiner.add("IS_TEXT_EDITOR");
        }
        if ((startInputFlags & 4) != 0) {
            joiner.add("FIRST_WINDOW_FOCUS_GAIN");
        }
        if ((startInputFlags & 8) != 0) {
            joiner.add("INITIAL_CONNECTION");
        }
        return joiner.setEmptyValue("(none)").toString();
    }
}
