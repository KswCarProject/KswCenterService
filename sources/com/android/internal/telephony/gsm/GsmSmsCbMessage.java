package com.android.internal.telephony.gsm;

import android.app.backup.FullBackup;
import android.content.Context;
import android.content.res.Resources;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.util.Pair;
import com.android.internal.R;
import com.android.internal.telephony.GsmAlphabet;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

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
                return r.getString(R.string.etws_primary_default_message_earthquake);
            case 1:
                return r.getString(R.string.etws_primary_default_message_tsunami);
            case 2:
                return r.getString(R.string.etws_primary_default_message_earthquake_and_tsunami);
            case 3:
                return r.getString(R.string.etws_primary_default_message_test);
            case 4:
                return r.getString(R.string.etws_primary_default_message_others);
            default:
                return "";
        }
    }

    public static SmsCbMessage createSmsCbMessage(Context context, SmsCbHeader header, SmsCbLocation location, byte[][] pdus) throws IllegalArgumentException {
        byte[][] bArr = pdus;
        if (header.isEtwsPrimaryNotification()) {
            return new SmsCbMessage(1, header.getGeographicalScope(), header.getSerialNumber(), location, header.getServiceCategory(), (String) null, getEtwsPrimaryMessage(context, header.getEtwsInfo().getWarningType()), 3, header.getEtwsInfo(), header.getCmasInfo());
        }
        Context context2 = context;
        StringBuilder sb = new StringBuilder();
        int priority = 0;
        String language = null;
        for (byte[] pdu : bArr) {
            Pair<String, String> p = parseBody(header, pdu);
            language = p.first;
            sb.append((String) p.second);
        }
        SmsCbHeader smsCbHeader = header;
        if (header.isEmergencyMessage()) {
            priority = 3;
        }
        return new SmsCbMessage(1, header.getGeographicalScope(), header.getSerialNumber(), location, header.getServiceCategory(), language, sb.toString(), priority, header.getEtwsInfo(), header.getCmasInfo());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: byte} */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004f, code lost:
        if (r14.isUmtsFormat() == false) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0051, code lost:
        r3 = r15[6];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0059, code lost:
        if (r15.length < ((r3 * 83) + 7)) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005b, code lost:
        r4 = new java.lang.StringBuilder();
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
        r11 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
        if (r11 >= r3) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0064, code lost:
        r12 = (r11 * 83) + 7;
        r13 = r15[r12 + 82];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006e, code lost:
        if (r13 > 82) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0070, code lost:
        r5 = unpackBody(r15, r6, r12, r13, r1, r0);
        r0 = r5.first;
        r4.append(r5.second);
        r5 = r11 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a6, code lost:
        throw new java.lang.IllegalArgumentException("Page length " + r13 + " exceeds maximum value " + 82);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b0, code lost:
        return new android.util.Pair<>(r0, r4.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d5, code lost:
        throw new java.lang.IllegalArgumentException("Pdu length " + r15.length + " does not match " + r3 + " pages");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e3, code lost:
        return unpackBody(r15, r6, 6, r15.length - 6, r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0016, code lost:
        r6 = r4;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.util.Pair<java.lang.String, java.lang.String> parseBody(com.android.internal.telephony.gsm.SmsCbHeader r14, byte[] r15) {
        /*
            r0 = 0
            r1 = 0
            int r2 = r14.getDataCodingScheme()
            r3 = r2 & 240(0xf0, float:3.36E-43)
            int r3 = r3 >> 4
            r4 = 9
            if (r3 == r4) goto L_0x00e4
            r4 = 1
            switch(r3) {
                case 0: goto L_0x0042;
                case 1: goto L_0x0039;
                case 2: goto L_0x0031;
                case 3: goto L_0x002f;
                case 4: goto L_0x0022;
                case 5: goto L_0x0022;
                case 6: goto L_0x00e4;
                case 7: goto L_0x00e4;
                default: goto L_0x0012;
            }
        L_0x0012:
            switch(r3) {
                case 14: goto L_0x00e4;
                case 15: goto L_0x0018;
                default: goto L_0x0015;
            }
        L_0x0015:
        L_0x0016:
            r6 = r4
            goto L_0x004a
        L_0x0018:
            r3 = r2 & 4
            int r3 = r3 >> 2
            if (r3 != r4) goto L_0x0020
            r4 = 2
            goto L_0x0016
        L_0x0020:
            r4 = 1
            goto L_0x0016
        L_0x0022:
            r3 = r2 & 12
            int r3 = r3 >> 2
            switch(r3) {
                case 1: goto L_0x002d;
                case 2: goto L_0x002b;
                default: goto L_0x0029;
            }
        L_0x0029:
            r4 = 1
            goto L_0x0016
        L_0x002b:
            r4 = 3
            goto L_0x0016
        L_0x002d:
            r4 = 2
            goto L_0x0016
        L_0x002f:
            r4 = 1
            goto L_0x0016
        L_0x0031:
            r4 = 1
            java.lang.String[] r3 = LANGUAGE_CODES_GROUP_2
            r5 = r2 & 15
            r0 = r3[r5]
            goto L_0x0016
        L_0x0039:
            r1 = 1
            r3 = r2 & 15
            if (r3 != r4) goto L_0x0040
            r4 = 3
            goto L_0x0016
        L_0x0040:
            r4 = 1
            goto L_0x0016
        L_0x0042:
            r4 = 1
            java.lang.String[] r3 = LANGUAGE_CODES_GROUP_0
            r5 = r2 & 15
            r0 = r3[r5]
            goto L_0x0016
        L_0x004a:
            boolean r3 = r14.isUmtsFormat()
            if (r3 == 0) goto L_0x00d6
            r3 = 6
            byte r3 = r15[r3]
            int r4 = r15.length
            int r5 = r3 * 83
            int r5 = r5 + 7
            if (r4 < r5) goto L_0x00b1
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = 0
        L_0x0061:
            r11 = r5
            if (r11 >= r3) goto L_0x00a7
            int r5 = r11 * 83
            int r12 = r5 + 7
            int r5 = r12 + 82
            byte r13 = r15[r5]
            r5 = 82
            if (r13 > r5) goto L_0x0088
            r5 = r15
            r7 = r12
            r8 = r13
            r9 = r1
            r10 = r0
            android.util.Pair r5 = unpackBody(r5, r6, r7, r8, r9, r10)
            F r7 = r5.first
            r0 = r7
            java.lang.String r0 = (java.lang.String) r0
            S r7 = r5.second
            java.lang.String r7 = (java.lang.String) r7
            r4.append(r7)
            int r5 = r11 + 1
            goto L_0x0061
        L_0x0088:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Page length "
            r8.append(r9)
            r8.append(r13)
            java.lang.String r9 = " exceeds maximum value "
            r8.append(r9)
            r8.append(r5)
            java.lang.String r5 = r8.toString()
            r7.<init>(r5)
            throw r7
        L_0x00a7:
            android.util.Pair r5 = new android.util.Pair
            java.lang.String r7 = r4.toString()
            r5.<init>(r0, r7)
            return r5
        L_0x00b1:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "Pdu length "
            r5.append(r7)
            int r7 = r15.length
            r5.append(r7)
            java.lang.String r7 = " does not match "
            r5.append(r7)
            r5.append(r3)
            java.lang.String r7 = " pages"
            r5.append(r7)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x00d6:
            r3 = 6
            int r4 = r15.length
            int r4 = r4 - r3
            r7 = r15
            r8 = r6
            r9 = r3
            r10 = r4
            r11 = r1
            r12 = r0
            android.util.Pair r5 = unpackBody(r7, r8, r9, r10, r11, r12)
            return r5
        L_0x00e4:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Unsupported GSM dataCodingScheme "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.GsmSmsCbMessage.parseBody(com.android.internal.telephony.gsm.SmsCbHeader, byte[]):android.util.Pair");
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
                } else if (body.charAt(i) != 13) {
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
