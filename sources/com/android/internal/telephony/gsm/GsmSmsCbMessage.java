package com.android.internal.telephony.gsm;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.util.Pair;
import com.android.internal.C3132R;
import com.android.internal.telephony.GsmAlphabet;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/* loaded from: classes4.dex */
public class GsmSmsCbMessage {
    private static final char CARRIAGE_RETURN = '\r';
    private static final String[] LANGUAGE_CODES_GROUP_0 = {Locale.GERMAN.getLanguage(), Locale.ENGLISH.getLanguage(), Locale.ITALIAN.getLanguage(), Locale.FRENCH.getLanguage(), new Locale("es").getLanguage(), new Locale("nl").getLanguage(), new Locale("sv").getLanguage(), new Locale("da").getLanguage(), new Locale("pt").getLanguage(), new Locale("fi").getLanguage(), new Locale(FullBackup.NO_BACKUP_TREE_TOKEN).getLanguage(), new Locale("el").getLanguage(), new Locale("tr").getLanguage(), new Locale("hu").getLanguage(), new Locale("pl").getLanguage(), null};
    private static final String[] LANGUAGE_CODES_GROUP_2 = {new Locale("cs").getLanguage(), new Locale("he").getLanguage(), new Locale("ar").getLanguage(), new Locale("ru").getLanguage(), new Locale("is").getLanguage(), null, null, null, null, null, null, null, null, null, null, null};
    private static final int PDU_BODY_PAGE_LENGTH = 82;

    private GsmSmsCbMessage() {
    }

    private static String getEtwsPrimaryMessage(Context context, int category) {
        Resources r = context.getResources();
        switch (category) {
            case 0:
                return r.getString(C3132R.string.etws_primary_default_message_earthquake);
            case 1:
                return r.getString(C3132R.string.etws_primary_default_message_tsunami);
            case 2:
                return r.getString(C3132R.string.etws_primary_default_message_earthquake_and_tsunami);
            case 3:
                return r.getString(C3132R.string.etws_primary_default_message_test);
            case 4:
                return r.getString(C3132R.string.etws_primary_default_message_others);
            default:
                return "";
        }
    }

    public static SmsCbMessage createSmsCbMessage(Context context, SmsCbHeader header, SmsCbLocation location, byte[][] pdus) throws IllegalArgumentException {
        if (header.isEtwsPrimaryNotification()) {
            return new SmsCbMessage(1, header.getGeographicalScope(), header.getSerialNumber(), location, header.getServiceCategory(), null, getEtwsPrimaryMessage(context, header.getEtwsInfo().getWarningType()), 3, header.getEtwsInfo(), header.getCmasInfo());
        }
        StringBuilder sb = new StringBuilder();
        String language = null;
        for (byte[] pdu : pdus) {
            Pair<String, String> p = parseBody(header, pdu);
            String language2 = p.first;
            language = language2;
            sb.append(p.second);
        }
        int priority = header.isEmergencyMessage() ? 3 : 0;
        return new SmsCbMessage(1, header.getGeographicalScope(), header.getSerialNumber(), location, header.getServiceCategory(), language, sb.toString(), priority, header.getEtwsInfo(), header.getCmasInfo());
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Pair<String, String> parseBody(SmsCbHeader header, byte[] pdu) {
        String language = null;
        boolean hasLanguageIndicator = false;
        int dataCodingScheme = header.getDataCodingScheme();
        int i = (dataCodingScheme & 240) >> 4;
        if (i != 9) {
            int encoding = 1;
            switch (i) {
                case 0:
                    encoding = 1;
                    language = LANGUAGE_CODES_GROUP_0[dataCodingScheme & 15];
                    int encoding2 = encoding;
                    if (header.isUmtsFormat()) {
                        int nrPages = pdu[6];
                        if (pdu.length < (nrPages * 83) + 7) {
                            throw new IllegalArgumentException("Pdu length " + pdu.length + " does not match " + nrPages + " pages");
                        }
                        StringBuilder sb = new StringBuilder();
                        int i2 = 0;
                        while (true) {
                            int i3 = i2;
                            if (i3 < nrPages) {
                                int offset = (i3 * 83) + 7;
                                int length = pdu[offset + 82];
                                if (length > 82) {
                                    throw new IllegalArgumentException("Page length " + length + " exceeds maximum value 82");
                                }
                                Pair<String, String> p = unpackBody(pdu, encoding2, offset, length, hasLanguageIndicator, language);
                                language = p.first;
                                sb.append(p.second);
                                i2 = i3 + 1;
                            } else {
                                return new Pair<>(language, sb.toString());
                            }
                        }
                    } else {
                        return unpackBody(pdu, encoding2, 6, pdu.length - 6, hasLanguageIndicator, language);
                    }
                case 1:
                    hasLanguageIndicator = true;
                    if ((dataCodingScheme & 15) == 1) {
                        encoding = 3;
                    } else {
                        encoding = 1;
                    }
                    int encoding22 = encoding;
                    if (header.isUmtsFormat()) {
                    }
                    break;
                case 2:
                    encoding = 1;
                    language = LANGUAGE_CODES_GROUP_2[dataCodingScheme & 15];
                    int encoding222 = encoding;
                    if (header.isUmtsFormat()) {
                    }
                    break;
                case 3:
                    encoding = 1;
                    int encoding2222 = encoding;
                    if (header.isUmtsFormat()) {
                    }
                    break;
                case 4:
                case 5:
                    switch ((dataCodingScheme & 12) >> 2) {
                        case 1:
                            encoding = 2;
                            break;
                        case 2:
                            encoding = 3;
                            break;
                        default:
                            encoding = 1;
                            break;
                    }
                    int encoding22222 = encoding;
                    if (header.isUmtsFormat()) {
                    }
                    break;
                case 6:
                case 7:
                    break;
                default:
                    switch (i) {
                        case 14:
                            break;
                        case 15:
                            if (((dataCodingScheme & 4) >> 2) == 1) {
                                encoding = 2;
                            } else {
                                encoding = 1;
                            }
                            int encoding222222 = encoding;
                            if (header.isUmtsFormat()) {
                            }
                            break;
                        default:
                            int encoding2222222 = encoding;
                            if (header.isUmtsFormat()) {
                            }
                            break;
                    }
            }
        }
        throw new IllegalArgumentException("Unsupported GSM dataCodingScheme " + dataCodingScheme);
    }

    private static Pair<String, String> unpackBody(byte[] pdu, int encoding, int offset, int length, boolean hasLanguageIndicator, String language) {
        String body = null;
        if (encoding == 1) {
            body = GsmAlphabet.gsm7BitPackedToString(pdu, offset, (length * 8) / 7);
            if (hasLanguageIndicator && body != null && body.length() > 2) {
                language = body.substring(0, 2);
                body = body.substring(3);
            }
        } else if (encoding == 3) {
            if (hasLanguageIndicator && pdu.length >= offset + 2) {
                language = GsmAlphabet.gsm7BitPackedToString(pdu, offset, 2);
                offset += 2;
                length -= 2;
            }
            try {
                body = new String(pdu, offset, 65534 & length, "utf-16");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("Error decoding UTF-16 message", e);
            }
        }
        if (body != null) {
            int i = body.length() - 1;
            while (true) {
                if (i < 0) {
                    break;
                } else if (body.charAt(i) != '\r') {
                    body = body.substring(0, i + 1);
                    break;
                } else {
                    i--;
                }
            }
        } else {
            body = "";
        }
        return new Pair<>(language, body);
    }
}
