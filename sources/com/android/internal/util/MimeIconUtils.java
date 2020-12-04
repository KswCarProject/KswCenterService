package com.android.internal.util;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.internal.R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.widget.MessagingMessage;
import java.util.Locale;
import java.util.Objects;
import libcore.net.MimeUtils;

public class MimeIconUtils {
    @GuardedBy({"sCache"})
    private static final ArrayMap<String, ContentResolver.MimeTypeInfo> sCache = new ArrayMap<>();

    private static ContentResolver.MimeTypeInfo buildTypeInfo(String mimeType, int iconId, int labelId, int extLabelId) {
        CharSequence label;
        Resources res = Resources.getSystem();
        String ext = MimeUtils.guessExtensionFromMimeType(mimeType);
        if (TextUtils.isEmpty(ext) || extLabelId == -1) {
            label = res.getString(labelId);
        } else {
            label = res.getString(extLabelId, ext.toUpperCase(Locale.US));
        }
        return new ContentResolver.MimeTypeInfo(Icon.createWithResource(res, iconId), label, label);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.ContentResolver.MimeTypeInfo buildTypeInfo(java.lang.String r10) {
        /*
            int r0 = r10.hashCode()
            r1 = -1
            switch(r0) {
                case -2135180893: goto L_0x0543;
                case -2135135086: goto L_0x0538;
                case -2035614749: goto L_0x052d;
                case -1988437312: goto L_0x0522;
                case -1917350260: goto L_0x0516;
                case -1883861089: goto L_0x050b;
                case -1808693885: goto L_0x04ff;
                case -1777056778: goto L_0x04f4;
                case -1747277413: goto L_0x04e9;
                case -1719571662: goto L_0x04dd;
                case -1628346451: goto L_0x04d1;
                case -1590813831: goto L_0x04c5;
                case -1506009513: goto L_0x04b9;
                case -1386165903: goto L_0x04ad;
                case -1348236371: goto L_0x04a1;
                case -1348228591: goto L_0x0495;
                case -1348228026: goto L_0x0489;
                case -1348228010: goto L_0x047d;
                case -1348221103: goto L_0x0471;
                case -1326989846: goto L_0x0465;
                case -1316922187: goto L_0x0459;
                case -1296467268: goto L_0x044d;
                case -1294595255: goto L_0x0442;
                case -1248334925: goto L_0x0436;
                case -1248333084: goto L_0x042a;
                case -1248326952: goto L_0x041e;
                case -1248325150: goto L_0x0412;
                case -1190438973: goto L_0x0406;
                case -1143717099: goto L_0x03fb;
                case -1082243251: goto L_0x03ee;
                case -1073633483: goto L_0x03e2;
                case -1071817359: goto L_0x03d6;
                case -1050893613: goto L_0x03ca;
                case -1033484950: goto L_0x03be;
                case -1004747231: goto L_0x03b1;
                case -1004727243: goto L_0x03a4;
                case -958424608: goto L_0x0397;
                case -951557661: goto L_0x038b;
                case -779959281: goto L_0x037f;
                case -779913474: goto L_0x0373;
                case -723118015: goto L_0x0367;
                case -676675015: goto L_0x035b;
                case -479218428: goto L_0x034f;
                case -427343476: goto L_0x0343;
                case -396757341: goto L_0x0337;
                case -366307023: goto L_0x032b;
                case -261480694: goto L_0x031e;
                case -261469704: goto L_0x0311;
                case -261439913: goto L_0x0304;
                case -261278343: goto L_0x02f7;
                case -228136375: goto L_0x02eb;
                case -221944004: goto L_0x02df;
                case -109382304: goto L_0x02d3;
                case -43923783: goto L_0x02c7;
                case -43840953: goto L_0x02bb;
                case 26919318: goto L_0x02af;
                case 81142075: goto L_0x02a4;
                case 163679941: goto L_0x0299;
                case 180207563: goto L_0x028d;
                case 245790645: goto L_0x0281;
                case 262346941: goto L_0x0274;
                case 302189274: goto L_0x0268;
                case 302663708: goto L_0x025c;
                case 363965503: goto L_0x0250;
                case 394650567: goto L_0x0245;
                case 428819984: goto L_0x0239;
                case 501428239: goto L_0x022c;
                case 571050671: goto L_0x0220;
                case 603849904: goto L_0x0214;
                case 641141505: goto L_0x0208;
                case 669516689: goto L_0x01fc;
                case 694663701: goto L_0x01f0;
                case 717553764: goto L_0x01e4;
                case 822609188: goto L_0x01d7;
                case 822849473: goto L_0x01ca;
                case 822865318: goto L_0x01bd;
                case 822865392: goto L_0x01b0;
                case 859118878: goto L_0x01a4;
                case 904647503: goto L_0x0198;
                case 1043583697: goto L_0x018c;
                case 1060806194: goto L_0x0180;
                case 1154415139: goto L_0x0174;
                case 1154449330: goto L_0x0168;
                case 1239557416: goto L_0x015d;
                case 1255211837: goto L_0x0150;
                case 1283455191: goto L_0x0144;
                case 1305955842: goto L_0x0138;
                case 1377360791: goto L_0x012c;
                case 1383977381: goto L_0x0120;
                case 1431987873: goto L_0x0114;
                case 1432260414: goto L_0x0108;
                case 1436962847: goto L_0x00fc;
                case 1440428940: goto L_0x00f0;
                case 1454024983: goto L_0x00e4;
                case 1461751133: goto L_0x00d8;
                case 1502452311: goto L_0x00cc;
                case 1536912403: goto L_0x00c0;
                case 1573656544: goto L_0x00b5;
                case 1577426419: goto L_0x00a9;
                case 1637222218: goto L_0x009d;
                case 1643664935: goto L_0x0091;
                case 1673742401: goto L_0x0085;
                case 1709755138: goto L_0x0079;
                case 1851895234: goto L_0x006d;
                case 1868769095: goto L_0x0061;
                case 1948418893: goto L_0x0055;
                case 1969663169: goto L_0x0049;
                case 1993842850: goto L_0x003d;
                case 2041423923: goto L_0x0031;
                case 2062084266: goto L_0x0024;
                case 2062095256: goto L_0x0017;
                case 2132236175: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x054e
        L_0x000a:
            java.lang.String r0 = "text/javascript"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 39
            goto L_0x054f
        L_0x0017:
            java.lang.String r0 = "text/x-c++src"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 21
            goto L_0x054f
        L_0x0024:
            java.lang.String r0 = "text/x-c++hdr"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 20
            goto L_0x054f
        L_0x0031:
            java.lang.String r0 = "application/x-x509-user-cert"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 9
            goto L_0x054f
        L_0x003d:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 106(0x6a, float:1.49E-43)
            goto L_0x054f
        L_0x0049:
            java.lang.String r0 = "application/rdf+xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 13
            goto L_0x054f
        L_0x0055:
            java.lang.String r0 = "application/mac-binhex40"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 41
            goto L_0x054f
        L_0x0061:
            java.lang.String r0 = "application/x-quicktimeplayer"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 100
            goto L_0x054f
        L_0x006d:
            java.lang.String r0 = "application/x-webarchive"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 53
            goto L_0x054f
        L_0x0079:
            java.lang.String r0 = "application/x-font-woff"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 65
            goto L_0x054f
        L_0x0085:
            java.lang.String r0 = "application/vnd.stardivision.writer"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 92
            goto L_0x054f
        L_0x0091:
            java.lang.String r0 = "application/vnd.oasis.opendocument.spreadsheet"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 81
            goto L_0x054f
        L_0x009d:
            java.lang.String r0 = "application/x-kspread"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 86
            goto L_0x054f
        L_0x00a9:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.presentationml.slideshow"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 111(0x6f, float:1.56E-43)
            goto L_0x054f
        L_0x00b5:
            java.lang.String r0 = "application/x-pkcs12"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 5
            goto L_0x054f
        L_0x00c0:
            java.lang.String r0 = "application/x-object"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 15
            goto L_0x054f
        L_0x00cc:
            java.lang.String r0 = "application/font-woff"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 64
            goto L_0x054f
        L_0x00d8:
            java.lang.String r0 = "application/vnd.oasis.opendocument.text-master"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 89
            goto L_0x054f
        L_0x00e4:
            java.lang.String r0 = "application/x-7z-compressed"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 56
            goto L_0x054f
        L_0x00f0:
            java.lang.String r0 = "application/javascript"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 37
            goto L_0x054f
        L_0x00fc:
            java.lang.String r0 = "application/vnd.oasis.opendocument.presentation"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 79
            goto L_0x054f
        L_0x0108:
            java.lang.String r0 = "application/x-latex"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 32
            goto L_0x054f
        L_0x0114:
            java.lang.String r0 = "application/x-kword"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 98
            goto L_0x054f
        L_0x0120:
            java.lang.String r0 = "application/vnd.sun.xml.impress"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 76
            goto L_0x054f
        L_0x012c:
            java.lang.String r0 = "application/vnd.oasis.opendocument.graphics-template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 68
            goto L_0x054f
        L_0x0138:
            java.lang.String r0 = "application/x-debian-package"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 45
            goto L_0x054f
        L_0x0144:
            java.lang.String r0 = "application/x-apple-diskimage"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 44
            goto L_0x054f
        L_0x0150:
            java.lang.String r0 = "text/x-haskell"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 26
            goto L_0x054f
        L_0x015d:
            java.lang.String r0 = "application/x-pkcs7-crl"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 7
            goto L_0x054f
        L_0x0168:
            java.lang.String r0 = "application/x-gtar"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 46
            goto L_0x054f
        L_0x0174:
            java.lang.String r0 = "application/x-font"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 63
            goto L_0x054f
        L_0x0180:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.wordprocessingml.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 104(0x68, float:1.46E-43)
            goto L_0x054f
        L_0x018c:
            java.lang.String r0 = "application/x-pkcs7-certificates"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 10
            goto L_0x054f
        L_0x0198:
            java.lang.String r0 = "application/msword"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 102(0x66, float:1.43E-43)
            goto L_0x054f
        L_0x01a4:
            java.lang.String r0 = "application/x-abiword"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 97
            goto L_0x054f
        L_0x01b0:
            java.lang.String r0 = "text/x-tex"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 31
            goto L_0x054f
        L_0x01bd:
            java.lang.String r0 = "text/x-tcl"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 30
            goto L_0x054f
        L_0x01ca:
            java.lang.String r0 = "text/x-csh"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 25
            goto L_0x054f
        L_0x01d7:
            java.lang.String r0 = "text/vcard"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 60
            goto L_0x054f
        L_0x01e4:
            java.lang.String r0 = "application/vnd.google-apps.document"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 99
            goto L_0x054f
        L_0x01f0:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.presentationml.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 110(0x6e, float:1.54E-43)
            goto L_0x054f
        L_0x01fc:
            java.lang.String r0 = "application/vnd.stardivision.impress"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 75
            goto L_0x054f
        L_0x0208:
            java.lang.String r0 = "application/x-texinfo"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 33
            goto L_0x054f
        L_0x0214:
            java.lang.String r0 = "application/xhtml+xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 16
            goto L_0x054f
        L_0x0220:
            java.lang.String r0 = "application/vnd.stardivision.writer-global"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 93
            goto L_0x054f
        L_0x022c:
            java.lang.String r0 = "text/x-vcard"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 59
            goto L_0x054f
        L_0x0239:
            java.lang.String r0 = "application/vnd.oasis.opendocument.graphics"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 67
            goto L_0x054f
        L_0x0245:
            java.lang.String r0 = "application/pgp-keys"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 3
            goto L_0x054f
        L_0x0250:
            java.lang.String r0 = "application/x-rar-compressed"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 58
            goto L_0x054f
        L_0x025c:
            java.lang.String r0 = "application/ecmascript"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 35
            goto L_0x054f
        L_0x0268:
            java.lang.String r0 = "vnd.android.document/directory"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 1
            goto L_0x054f
        L_0x0274:
            java.lang.String r0 = "text/x-vcalendar"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 62
            goto L_0x054f
        L_0x0281:
            java.lang.String r0 = "application/vnd.google-apps.drawing"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 73
            goto L_0x054f
        L_0x028d:
            java.lang.String r0 = "application/x-stuffit"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 51
            goto L_0x054f
        L_0x0299:
            java.lang.String r0 = "application/pgp-signature"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 4
            goto L_0x054f
        L_0x02a4:
            java.lang.String r0 = "application/vnd.android.package-archive"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 2
            goto L_0x054f
        L_0x02af:
            java.lang.String r0 = "application/x-iso9660-image"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 47
            goto L_0x054f
        L_0x02bb:
            java.lang.String r0 = "application/json"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 36
            goto L_0x054f
        L_0x02c7:
            java.lang.String r0 = "application/gzip"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 55
            goto L_0x054f
        L_0x02d3:
            java.lang.String r0 = "application/vnd.oasis.opendocument.spreadsheet-template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 82
            goto L_0x054f
        L_0x02df:
            java.lang.String r0 = "application/x-font-ttf"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 66
            goto L_0x054f
        L_0x02eb:
            java.lang.String r0 = "application/x-pkcs7-mime"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 11
            goto L_0x054f
        L_0x02f7:
            java.lang.String r0 = "text/x-java"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 27
            goto L_0x054f
        L_0x0304:
            java.lang.String r0 = "text/x-dsrc"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 24
            goto L_0x054f
        L_0x0311:
            java.lang.String r0 = "text/x-csrc"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 23
            goto L_0x054f
        L_0x031e:
            java.lang.String r0 = "text/x-chdr"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 22
            goto L_0x054f
        L_0x032b:
            java.lang.String r0 = "application/vnd.ms-excel"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 105(0x69, float:1.47E-43)
            goto L_0x054f
        L_0x0337:
            java.lang.String r0 = "application/vnd.sun.xml.impress.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 77
            goto L_0x054f
        L_0x0343:
            java.lang.String r0 = "application/x-webarchive-xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 54
            goto L_0x054f
        L_0x034f:
            java.lang.String r0 = "application/vnd.sun.xml.writer.global"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 95
            goto L_0x054f
        L_0x035b:
            java.lang.String r0 = "application/vnd.oasis.opendocument.text-web"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 91
            goto L_0x054f
        L_0x0367:
            java.lang.String r0 = "application/x-javascript"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 40
            goto L_0x054f
        L_0x0373:
            java.lang.String r0 = "application/vnd.sun.xml.draw"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 71
            goto L_0x054f
        L_0x037f:
            java.lang.String r0 = "application/vnd.sun.xml.calc"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 84
            goto L_0x054f
        L_0x038b:
            java.lang.String r0 = "application/vnd.google-apps.presentation"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 80
            goto L_0x054f
        L_0x0397:
            java.lang.String r0 = "text/calendar"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 61
            goto L_0x054f
        L_0x03a4:
            java.lang.String r0 = "text/xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 19
            goto L_0x054f
        L_0x03b1:
            java.lang.String r0 = "text/css"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 17
            goto L_0x054f
        L_0x03be:
            java.lang.String r0 = "application/vnd.sun.xml.draw.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 72
            goto L_0x054f
        L_0x03ca:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 103(0x67, float:1.44E-43)
            goto L_0x054f
        L_0x03d6:
            java.lang.String r0 = "application/vnd.ms-powerpoint"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 108(0x6c, float:1.51E-43)
            goto L_0x054f
        L_0x03e2:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 109(0x6d, float:1.53E-43)
            goto L_0x054f
        L_0x03ee:
            java.lang.String r0 = "text/html"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 18
            goto L_0x054f
        L_0x03fb:
            java.lang.String r0 = "application/x-pkcs7-certreqresp"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 6
            goto L_0x054f
        L_0x0406:
            java.lang.String r0 = "application/x-pkcs7-signature"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 12
            goto L_0x054f
        L_0x0412:
            java.lang.String r0 = "application/zip"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 43
            goto L_0x054f
        L_0x041e:
            java.lang.String r0 = "application/xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 38
            goto L_0x054f
        L_0x042a:
            java.lang.String r0 = "application/rar"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 42
            goto L_0x054f
        L_0x0436:
            java.lang.String r0 = "application/pdf"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 74
            goto L_0x054f
        L_0x0442:
            java.lang.String r0 = "inode/directory"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 0
            goto L_0x054f
        L_0x044d:
            java.lang.String r0 = "application/atom+xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 34
            goto L_0x054f
        L_0x0459:
            java.lang.String r0 = "application/vnd.oasis.opendocument.text-template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 90
            goto L_0x054f
        L_0x0465:
            java.lang.String r0 = "application/x-shockwave-flash"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 101(0x65, float:1.42E-43)
            goto L_0x054f
        L_0x0471:
            java.lang.String r0 = "application/x-tar"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 52
            goto L_0x054f
        L_0x047d:
            java.lang.String r0 = "application/x-lzx"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 50
            goto L_0x054f
        L_0x0489:
            java.lang.String r0 = "application/x-lzh"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 49
            goto L_0x054f
        L_0x0495:
            java.lang.String r0 = "application/x-lha"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 48
            goto L_0x054f
        L_0x04a1:
            java.lang.String r0 = "application/x-deb"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 57
            goto L_0x054f
        L_0x04ad:
            java.lang.String r0 = "application/x-kpresenter"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 78
            goto L_0x054f
        L_0x04b9:
            java.lang.String r0 = "application/vnd.openxmlformats-officedocument.spreadsheetml.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 107(0x6b, float:1.5E-43)
            goto L_0x054f
        L_0x04c5:
            java.lang.String r0 = "application/vnd.sun.xml.calc.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 85
            goto L_0x054f
        L_0x04d1:
            java.lang.String r0 = "application/vnd.sun.xml.writer"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 94
            goto L_0x054f
        L_0x04dd:
            java.lang.String r0 = "application/vnd.oasis.opendocument.text"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 88
            goto L_0x054f
        L_0x04e9:
            java.lang.String r0 = "application/vnd.sun.xml.writer.template"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 96
            goto L_0x054f
        L_0x04f4:
            java.lang.String r0 = "application/vnd.oasis.opendocument.image"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 69
            goto L_0x054f
        L_0x04ff:
            java.lang.String r0 = "text/x-pascal"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 29
            goto L_0x054f
        L_0x050b:
            java.lang.String r0 = "application/rss+xml"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 14
            goto L_0x054f
        L_0x0516:
            java.lang.String r0 = "text/x-literate-haskell"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 28
            goto L_0x054f
        L_0x0522:
            java.lang.String r0 = "application/x-x509-ca-cert"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 8
            goto L_0x054f
        L_0x052d:
            java.lang.String r0 = "application/vnd.google-apps.spreadsheet"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 87
            goto L_0x054f
        L_0x0538:
            java.lang.String r0 = "application/vnd.stardivision.draw"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 70
            goto L_0x054f
        L_0x0543:
            java.lang.String r0 = "application/vnd.stardivision.calc"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x054e
            r0 = 83
            goto L_0x054f
        L_0x054e:
            r0 = r1
        L_0x054f:
            r2 = 17040431(0x104042f, float:2.4247573E-38)
            r3 = 17040430(0x104042e, float:2.424757E-38)
            r4 = 17040429(0x104042d, float:2.4247567E-38)
            r5 = 17040428(0x104042c, float:2.4247564E-38)
            r6 = 17040422(0x1040426, float:2.4247547E-38)
            r7 = 17040421(0x1040425, float:2.4247545E-38)
            r8 = 17040425(0x1040429, float:2.4247556E-38)
            r9 = 17040424(0x1040428, float:2.4247553E-38)
            switch(r0) {
                case 0: goto L_0x0604;
                case 1: goto L_0x0604;
                case 2: goto L_0x05f9;
                case 3: goto L_0x05f1;
                case 4: goto L_0x05f1;
                case 5: goto L_0x05f1;
                case 6: goto L_0x05f1;
                case 7: goto L_0x05f1;
                case 8: goto L_0x05f1;
                case 9: goto L_0x05f1;
                case 10: goto L_0x05f1;
                case 11: goto L_0x05f1;
                case 12: goto L_0x05f1;
                case 13: goto L_0x05e9;
                case 14: goto L_0x05e9;
                case 15: goto L_0x05e9;
                case 16: goto L_0x05e9;
                case 17: goto L_0x05e9;
                case 18: goto L_0x05e9;
                case 19: goto L_0x05e9;
                case 20: goto L_0x05e9;
                case 21: goto L_0x05e9;
                case 22: goto L_0x05e9;
                case 23: goto L_0x05e9;
                case 24: goto L_0x05e9;
                case 25: goto L_0x05e9;
                case 26: goto L_0x05e9;
                case 27: goto L_0x05e9;
                case 28: goto L_0x05e9;
                case 29: goto L_0x05e9;
                case 30: goto L_0x05e9;
                case 31: goto L_0x05e9;
                case 32: goto L_0x05e9;
                case 33: goto L_0x05e9;
                case 34: goto L_0x05e9;
                case 35: goto L_0x05e9;
                case 36: goto L_0x05e9;
                case 37: goto L_0x05e9;
                case 38: goto L_0x05e9;
                case 39: goto L_0x05e9;
                case 40: goto L_0x05e9;
                case 41: goto L_0x05db;
                case 42: goto L_0x05db;
                case 43: goto L_0x05db;
                case 44: goto L_0x05db;
                case 45: goto L_0x05db;
                case 46: goto L_0x05db;
                case 47: goto L_0x05db;
                case 48: goto L_0x05db;
                case 49: goto L_0x05db;
                case 50: goto L_0x05db;
                case 51: goto L_0x05db;
                case 52: goto L_0x05db;
                case 53: goto L_0x05db;
                case 54: goto L_0x05db;
                case 55: goto L_0x05db;
                case 56: goto L_0x05db;
                case 57: goto L_0x05db;
                case 58: goto L_0x05db;
                case 59: goto L_0x05d3;
                case 60: goto L_0x05d3;
                case 61: goto L_0x05cb;
                case 62: goto L_0x05cb;
                case 63: goto L_0x05c3;
                case 64: goto L_0x05c3;
                case 65: goto L_0x05c3;
                case 66: goto L_0x05c3;
                case 67: goto L_0x05b5;
                case 68: goto L_0x05b5;
                case 69: goto L_0x05b5;
                case 70: goto L_0x05b5;
                case 71: goto L_0x05b5;
                case 72: goto L_0x05b5;
                case 73: goto L_0x05b5;
                case 74: goto L_0x05ad;
                case 75: goto L_0x05a5;
                case 76: goto L_0x05a5;
                case 77: goto L_0x05a5;
                case 78: goto L_0x05a5;
                case 79: goto L_0x05a5;
                case 80: goto L_0x05a5;
                case 81: goto L_0x059d;
                case 82: goto L_0x059d;
                case 83: goto L_0x059d;
                case 84: goto L_0x059d;
                case 85: goto L_0x059d;
                case 86: goto L_0x059d;
                case 87: goto L_0x059d;
                case 88: goto L_0x0595;
                case 89: goto L_0x0595;
                case 90: goto L_0x0595;
                case 91: goto L_0x0595;
                case 92: goto L_0x0595;
                case 93: goto L_0x0595;
                case 94: goto L_0x0595;
                case 95: goto L_0x0595;
                case 96: goto L_0x0595;
                case 97: goto L_0x0595;
                case 98: goto L_0x0595;
                case 99: goto L_0x0595;
                case 100: goto L_0x0587;
                case 101: goto L_0x0587;
                case 102: goto L_0x057f;
                case 103: goto L_0x057f;
                case 104: goto L_0x057f;
                case 105: goto L_0x0577;
                case 106: goto L_0x0577;
                case 107: goto L_0x0577;
                case 108: goto L_0x056f;
                case 109: goto L_0x056f;
                case 110: goto L_0x056f;
                case 111: goto L_0x056f;
                default: goto L_0x056a;
            }
        L_0x056a:
            android.content.ContentResolver$MimeTypeInfo r0 = buildGenericTypeInfo(r10)
            return r0
        L_0x056f:
            r0 = 17302395(0x108037b, float:2.4981752E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r5, r4)
            return r0
        L_0x0577:
            r0 = 17302389(0x1080375, float:2.4981735E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r3, r2)
            return r0
        L_0x057f:
            r0 = 17302400(0x1080380, float:2.4981766E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r7, r6)
            return r0
        L_0x0587:
            r0 = 17302399(0x108037f, float:2.4981763E-38)
            r1 = 17040432(0x1040430, float:2.4247575E-38)
            r2 = 17040433(0x1040431, float:2.4247578E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r1, r2)
            return r0
        L_0x0595:
            r0 = 17302387(0x1080373, float:2.498173E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r7, r6)
            return r0
        L_0x059d:
            r0 = 17302397(0x108037d, float:2.4981758E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r3, r2)
            return r0
        L_0x05a5:
            r0 = 17302396(0x108037c, float:2.4981755E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r5, r4)
            return r0
        L_0x05ad:
            r0 = 17302394(0x108037a, float:2.498175E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r7, r6)
            return r0
        L_0x05b5:
            r0 = 17302393(0x1080379, float:2.4981746E-38)
            r1 = 17040426(0x104042a, float:2.4247559E-38)
            r2 = 17040427(0x104042b, float:2.424756E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r1, r2)
            return r0
        L_0x05c3:
            r0 = 17302391(0x1080377, float:2.498174E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r9, r8)
            return r0
        L_0x05cb:
            r0 = 17302388(0x1080374, float:2.4981732E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r9, r8)
            return r0
        L_0x05d3:
            r0 = 17302386(0x1080372, float:2.4981727E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r9, r8)
            return r0
        L_0x05db:
            r0 = 17302385(0x1080371, float:2.4981724E-38)
            r1 = 17040419(0x1040423, float:2.424754E-38)
            r2 = 17040420(0x1040424, float:2.4247542E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r1, r2)
            return r0
        L_0x05e9:
            r0 = 17302384(0x1080370, float:2.498172E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r7, r6)
            return r0
        L_0x05f1:
            r0 = 17302383(0x108036f, float:2.4981718E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r9, r8)
            return r0
        L_0x05f9:
            r0 = 17302381(0x108036d, float:2.4981713E-38)
            r2 = 17040416(0x1040420, float:2.424753E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r2, r1)
            return r0
        L_0x0604:
            r0 = 17302390(0x1080376, float:2.4981738E-38)
            r2 = 17040423(0x1040427, float:2.424755E-38)
            android.content.ContentResolver$MimeTypeInfo r0 = buildTypeInfo(r10, r0, r2, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.MimeIconUtils.buildTypeInfo(java.lang.String):android.content.ContentResolver$MimeTypeInfo");
    }

    private static ContentResolver.MimeTypeInfo buildGenericTypeInfo(String mimeType) {
        if (mimeType.startsWith("audio/")) {
            return buildTypeInfo(mimeType, R.drawable.ic_doc_audio, R.string.mime_type_audio, R.string.mime_type_audio_ext);
        }
        if (mimeType.startsWith("video/")) {
            return buildTypeInfo(mimeType, R.drawable.ic_doc_video, R.string.mime_type_video, R.string.mime_type_video_ext);
        }
        if (mimeType.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
            return buildTypeInfo(mimeType, R.drawable.ic_doc_image, R.string.mime_type_image, R.string.mime_type_image_ext);
        }
        if (mimeType.startsWith("text/")) {
            return buildTypeInfo(mimeType, R.drawable.ic_doc_text, R.string.mime_type_document, R.string.mime_type_document_ext);
        }
        String bouncedMimeType = MimeUtils.guessMimeTypeFromExtension(MimeUtils.guessExtensionFromMimeType(mimeType));
        if (bouncedMimeType == null || Objects.equals(mimeType, bouncedMimeType)) {
            return buildTypeInfo(mimeType, R.drawable.ic_doc_generic, R.string.mime_type_generic, R.string.mime_type_generic_ext);
        }
        return buildTypeInfo(bouncedMimeType);
    }

    public static ContentResolver.MimeTypeInfo getTypeInfo(String mimeType) {
        ContentResolver.MimeTypeInfo res;
        String mimeType2 = mimeType.toLowerCase(Locale.US);
        synchronized (sCache) {
            res = sCache.get(mimeType2);
            if (res == null) {
                res = buildTypeInfo(mimeType2);
                sCache.put(mimeType2, res);
            }
        }
        return res;
    }
}
