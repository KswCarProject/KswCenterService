package android.media;

import android.app.backup.FullBackup;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.IncidentManager;
import com.ibm.icu.text.DateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: TtmlRenderer */
final class TtmlUtils {
    public static final String ATTR_BEGIN = "begin";
    public static final String ATTR_DURATION = "dur";
    public static final String ATTR_END = "end";
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    public static final long INVALID_TIMESTAMP = Long.MAX_VALUE;
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    public static final String PCDATA = "#pcdata";
    public static final String TAG_BODY = "body";
    public static final String TAG_BR = "br";
    public static final String TAG_DIV = "div";
    public static final String TAG_HEAD = "head";
    public static final String TAG_LAYOUT = "layout";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_P = "p";
    public static final String TAG_REGION = "region";
    public static final String TAG_SMPTE_DATA = "smpte:data";
    public static final String TAG_SMPTE_IMAGE = "smpte:image";
    public static final String TAG_SMPTE_INFORMATION = "smpte:information";
    public static final String TAG_SPAN = "span";
    public static final String TAG_STYLE = "style";
    public static final String TAG_STYLING = "styling";
    public static final String TAG_TT = "tt";

    private TtmlUtils() {
    }

    public static long parseTimeExpression(String time, int frameRate, int subframeRate, int tickRate) throws NumberFormatException {
        double d;
        String str = time;
        int i = frameRate;
        Matcher matcher = CLOCK_TIME.matcher(str);
        if (matcher.matches()) {
            double durationSeconds = ((double) (Long.parseLong(matcher.group(1)) * 3600)) + ((double) (Long.parseLong(matcher.group(2)) * 60)) + ((double) Long.parseLong(matcher.group(3)));
            String fraction = matcher.group(4);
            double durationSeconds2 = durationSeconds + (fraction != null ? Double.parseDouble(fraction) : 0.0d);
            String frames = matcher.group(5);
            double durationSeconds3 = durationSeconds2 + (frames != null ? ((double) Long.parseLong(frames)) / ((double) i) : 0.0d);
            String subframes = matcher.group(6);
            if (subframes != null) {
                d = (((double) Long.parseLong(subframes)) / ((double) subframeRate)) / ((double) i);
            } else {
                int i2 = subframeRate;
                d = 0.0d;
            }
            return (long) (1000.0d * (durationSeconds3 + d));
        }
        int i3 = subframeRate;
        Matcher matcher2 = OFFSET_TIME.matcher(str);
        if (matcher2.matches()) {
            double value = Double.parseDouble(matcher2.group(1));
            String unit = matcher2.group(2);
            if (unit.equals("h")) {
                value *= 3.6E9d;
            } else if (unit.equals(DateFormat.MINUTE)) {
                value *= 6.0E7d;
            } else if (unit.equals(DateFormat.SECOND)) {
                value *= 1000000.0d;
            } else if (unit.equals(DateFormat.MINUTE_SECOND)) {
                value *= 1000.0d;
            } else if (unit.equals(FullBackup.FILES_TREE_TOKEN)) {
                value = (value / ((double) i)) * 1000000.0d;
            } else if (unit.equals(IncidentManager.URI_PARAM_TIMESTAMP)) {
                value = (value / ((double) tickRate)) * 1000000.0d;
                return (long) value;
            }
            int i4 = tickRate;
            return (long) value;
        }
        int i5 = tickRate;
        throw new NumberFormatException("Malformed time expression : " + str);
    }

    public static String applyDefaultSpacePolicy(String in) {
        return applySpacePolicy(in, true);
    }

    public static String applySpacePolicy(String in, boolean treatLfAsSpace) {
        String lfToSpace;
        String spacesNeighboringLfRemoved = in.replaceAll("\r\n", "\n").replaceAll(" *\n *", "\n");
        if (treatLfAsSpace) {
            lfToSpace = spacesNeighboringLfRemoved.replaceAll("\n", WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
        } else {
            lfToSpace = spacesNeighboringLfRemoved;
        }
        return lfToSpace.replaceAll("[ \t\\x0B\f\r]+", WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER);
    }

    public static String extractText(TtmlNode root, long startUs, long endUs) {
        StringBuilder text = new StringBuilder();
        extractText(root, startUs, endUs, text, false);
        return text.toString().replaceAll("\n$", "");
    }

    private static void extractText(TtmlNode node, long startUs, long endUs, StringBuilder out, boolean inPTag) {
        TtmlNode ttmlNode = node;
        StringBuilder sb = out;
        if (ttmlNode.mName.equals(PCDATA) && inPTag) {
            sb.append(ttmlNode.mText);
        } else if (ttmlNode.mName.equals(TAG_BR) && inPTag) {
            sb.append("\n");
        } else if (!ttmlNode.mName.equals(TAG_METADATA) && node.isActive(startUs, endUs)) {
            boolean pTag = ttmlNode.mName.equals(TAG_P);
            int length = out.length();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= ttmlNode.mChildren.size()) {
                    break;
                }
                extractText(ttmlNode.mChildren.get(i2), startUs, endUs, out, pTag || inPTag);
                i = i2 + 1;
            }
            if (pTag && length != out.length()) {
                sb.append("\n");
            }
        }
    }

    public static String extractTtmlFragment(TtmlNode root, long startUs, long endUs) {
        StringBuilder fragment = new StringBuilder();
        extractTtmlFragment(root, startUs, endUs, fragment);
        return fragment.toString();
    }

    private static void extractTtmlFragment(TtmlNode node, long startUs, long endUs, StringBuilder out) {
        if (node.mName.equals(PCDATA)) {
            out.append(node.mText);
        } else if (node.mName.equals(TAG_BR)) {
            out.append("<br/>");
        } else if (node.isActive(startUs, endUs)) {
            out.append("<");
            out.append(node.mName);
            out.append(node.mAttributes);
            out.append(">");
            for (int i = 0; i < node.mChildren.size(); i++) {
                extractTtmlFragment(node.mChildren.get(i), startUs, endUs, out);
            }
            out.append("</");
            out.append(node.mName);
            out.append(">");
        }
    }
}
