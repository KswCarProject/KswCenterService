package com.android.internal.telephony.gsm;

import android.content.res.Resources;
import android.net.wifi.WifiEnterpriseConfig;
import android.telephony.PhoneNumberUtils;
import android.telephony.PreciseDisconnectCause;
import android.telephony.Rlog;
import android.text.TextUtils;
import android.text.format.Time;
import com.android.internal.R;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.telephony.EncodeException;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.uicc.IccUtils;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class SmsMessage extends SmsMessageBase {
    private static final int INVALID_VALIDITY_PERIOD = -1;
    static final String LOG_TAG = "SmsMessage";
    private static final int VALIDITY_PERIOD_FORMAT_ABSOLUTE = 3;
    private static final int VALIDITY_PERIOD_FORMAT_ENHANCED = 1;
    private static final int VALIDITY_PERIOD_FORMAT_NONE = 0;
    private static final int VALIDITY_PERIOD_FORMAT_RELATIVE = 2;
    private static final int VALIDITY_PERIOD_MAX = 635040;
    private static final int VALIDITY_PERIOD_MIN = 5;
    private static final boolean VDBG = false;
    private int mDataCodingScheme;
    private boolean mIsStatusReportMessage = false;
    private int mMti;
    private int mProtocolIdentifier;
    private boolean mReplyPathPresent = false;
    private int mStatus;
    private int mVoiceMailCount = 0;
    private SmsConstants.MessageClass messageClass;

    public static class SubmitPdu extends SmsMessageBase.SubmitPduBase {
    }

    public static SmsMessage createFromPdu(byte[] pdu) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.parsePdu(pdu);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        } catch (OutOfMemoryError e) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        }
    }

    public boolean isTypeZero() {
        return this.mProtocolIdentifier == 64;
    }

    public static SmsMessage newFromCMT(byte[] pdu) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.parsePdu(pdu);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public static SmsMessage newFromCDS(byte[] pdu) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.parsePdu(pdu);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "CDS SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public static SmsMessage createFromEfRecord(int index, byte[] data) {
        try {
            SmsMessage msg = new SmsMessage();
            msg.mIndexOnIcc = index;
            if ((data[0] & 1) == 0) {
                Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            msg.mStatusOnIcc = data[0] & 7;
            int size = data.length - 1;
            byte[] pdu = new byte[size];
            System.arraycopy(data, 1, pdu, 0, size);
            msg.parsePdu(pdu);
            return msg;
        } catch (RuntimeException ex) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", ex);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(String pdu) {
        return ((pdu.length() / 2) - Integer.parseInt(pdu.substring(0, 2), 16)) - 1;
    }

    public static int getRelativeValidityPeriod(int validityPeriod) {
        if (validityPeriod < 5 || validityPeriod > VALIDITY_PERIOD_MAX) {
            Rlog.e(LOG_TAG, "Invalid Validity Period" + validityPeriod);
            return -1;
        } else if (validityPeriod <= 720) {
            return (validityPeriod / 5) - 1;
        } else {
            if (validityPeriod <= 1440) {
                return ((validityPeriod - 720) / 30) + 143;
            }
            if (validityPeriod <= 43200) {
                return (validityPeriod / MetricsProto.MetricsEvent.ACTION_HUSH_GESTURE) + 166;
            }
            if (validityPeriod <= VALIDITY_PERIOD_MAX) {
                return (validityPeriod / 10080) + 192;
            }
            return -1;
        }
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, header, 0, 0, 0);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, int encoding, int languageTable, int languageShiftTable) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, header, encoding, languageTable, languageShiftTable, -1);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, byte[] header, int encoding, int languageTable, int languageShiftTable, int validityPeriod) {
        byte[] header2;
        int languageShiftTable2;
        int languageTable2;
        int encoding2;
        byte[] userData;
        String str = destinationAddress;
        String str2 = message;
        if (str2 == null || str == null) {
            String str3 = scAddress;
            boolean z = statusReportRequested;
            return null;
        }
        if (encoding == 0) {
            GsmAlphabet.TextEncodingDetails ted = calculateLength(str2, false);
            int encoding3 = ted.codeUnitSize;
            languageTable2 = ted.languageTable;
            languageShiftTable2 = ted.languageShiftTable;
            if (encoding3 != 1 || (languageTable2 == 0 && languageShiftTable2 == 0)) {
                header2 = header;
            } else if (header != null) {
                SmsHeader smsHeader = SmsHeader.fromByteArray(header);
                if (smsHeader.languageTable == languageTable2 && smsHeader.languageShiftTable == languageShiftTable2) {
                    header2 = header;
                } else {
                    Rlog.w(LOG_TAG, "Updating language table in SMS header: " + smsHeader.languageTable + " -> " + languageTable2 + ", " + smsHeader.languageShiftTable + " -> " + languageShiftTable2);
                    smsHeader.languageTable = languageTable2;
                    smsHeader.languageShiftTable = languageShiftTable2;
                    header2 = SmsHeader.toByteArray(smsHeader);
                }
            } else {
                SmsHeader smsHeader2 = new SmsHeader();
                smsHeader2.languageTable = languageTable2;
                smsHeader2.languageShiftTable = languageShiftTable2;
                header2 = SmsHeader.toByteArray(smsHeader2);
            }
            encoding2 = encoding3;
        } else {
            header2 = header;
            encoding2 = encoding;
            languageTable2 = languageTable;
            languageShiftTable2 = languageShiftTable;
        }
        SubmitPdu ret = new SubmitPdu();
        int validityPeriodFormat = 0;
        int relativeValidityPeriod = getRelativeValidityPeriod(validityPeriod);
        int relativeValidityPeriod2 = relativeValidityPeriod;
        if (relativeValidityPeriod >= 0) {
            validityPeriodFormat = 2;
        }
        int validityPeriodFormat2 = validityPeriodFormat;
        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, str, (byte) ((validityPeriodFormat2 << 3) | 1 | (header2 != null ? 64 : 0)), statusReportRequested, ret);
        if (bo == null) {
            return ret;
        }
        if (encoding2 == 1) {
            try {
                userData = GsmAlphabet.stringToGsm7BitPackedWithHeader(str2, header2, languageTable2, languageShiftTable2);
            } catch (EncodeException e) {
                EncodeException ex = e;
                if (ex.getError() == 1) {
                    Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", ex);
                    return null;
                }
                try {
                    userData = encodeUCS2(str2, header2);
                    encoding2 = 3;
                } catch (EncodeException e2) {
                    Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e2);
                    return null;
                } catch (UnsupportedEncodingException e3) {
                    Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e3);
                    return null;
                }
            }
        } else {
            try {
                userData = encodeUCS2(str2, header2);
            } catch (UnsupportedEncodingException e4) {
                Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e4);
                return null;
            }
        }
        if (encoding2 == 1) {
            if ((userData[0] & 255) > 160) {
                Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " septets)");
                return null;
            }
            bo.write(0);
        } else if ((userData[0] & 255) > 140) {
            Rlog.e(LOG_TAG, "Message too long (" + (userData[0] & 255) + " bytes)");
            return null;
        } else {
            bo.write(8);
        }
        if (validityPeriodFormat2 == 2) {
            bo.write(relativeValidityPeriod2);
        }
        bo.write(userData, 0, userData.length);
        ret.encodedMessage = bo.toByteArray();
        return ret;
    }

    private static byte[] encodeUCS2(String message, byte[] header) throws UnsupportedEncodingException, EncodeException {
        byte[] userData;
        byte[] textPart = message.getBytes("utf-16be");
        if (header != null) {
            userData = new byte[(header.length + textPart.length + 1)];
            userData[0] = (byte) header.length;
            System.arraycopy(header, 0, userData, 1, header.length);
            System.arraycopy(textPart, 0, userData, header.length + 1, textPart.length);
        } else {
            userData = textPart;
        }
        if (userData.length <= 255) {
            byte[] ret = new byte[(userData.length + 1)];
            ret[0] = (byte) (255 & userData.length);
            System.arraycopy(userData, 0, ret, 1, userData.length);
            return ret;
        }
        throw new EncodeException("Payload cannot exceed 255 bytes", 1);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, (byte[]) null);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, String message, boolean statusReportRequested, int validityPeriod) {
        return getSubmitPdu(scAddress, destinationAddress, message, statusReportRequested, (byte[]) null, 0, 0, 0, validityPeriod);
    }

    public static SubmitPdu getSubmitPdu(String scAddress, String destinationAddress, int destinationPort, byte[] data, boolean statusReportRequested) {
        SmsHeader.PortAddrs portAddrs = new SmsHeader.PortAddrs();
        portAddrs.destPort = destinationPort;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        SmsHeader smsHeader = new SmsHeader();
        smsHeader.portAddrs = portAddrs;
        byte[] smsHeaderData = SmsHeader.toByteArray(smsHeader);
        if (data.length + smsHeaderData.length + 1 > 140) {
            StringBuilder sb = new StringBuilder();
            sb.append("SMS data message may only contain ");
            sb.append((140 - smsHeaderData.length) - 1);
            sb.append(" bytes");
            Rlog.e(LOG_TAG, sb.toString());
            return null;
        }
        SubmitPdu ret = new SubmitPdu();
        ByteArrayOutputStream bo = getSubmitPduHead(scAddress, destinationAddress, (byte) 65, statusReportRequested, ret);
        if (bo == null) {
            return ret;
        }
        bo.write(4);
        bo.write(data.length + smsHeaderData.length + 1);
        bo.write(smsHeaderData.length);
        bo.write(smsHeaderData, 0, smsHeaderData.length);
        bo.write(data, 0, data.length);
        ret.encodedMessage = bo.toByteArray();
        return ret;
    }

    private static ByteArrayOutputStream getSubmitPduHead(String scAddress, String destinationAddress, byte mtiByte, boolean statusReportRequested, SubmitPdu ret) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream(180);
        if (scAddress == null) {
            ret.encodedScAddress = null;
        } else {
            ret.encodedScAddress = PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(scAddress);
        }
        if (statusReportRequested) {
            mtiByte = (byte) (mtiByte | 32);
        }
        bo.write(mtiByte);
        bo.write(0);
        byte[] daBytes = PhoneNumberUtils.networkPortionToCalledPartyBCD(destinationAddress);
        if (daBytes == null) {
            return null;
        }
        int i = 1;
        int length = (daBytes.length - 1) * 2;
        if ((daBytes[daBytes.length - 1] & 240) != 240) {
            i = 0;
        }
        bo.write(length - i);
        bo.write(daBytes, 0, daBytes.length);
        bo.write(0);
        return bo;
    }

    private static class PduParser {
        int mCur = 0;
        byte[] mPdu;
        byte[] mUserData;
        SmsHeader mUserDataHeader;
        int mUserDataSeptetPadding = 0;

        PduParser(byte[] pdu) {
            this.mPdu = pdu;
        }

        /* access modifiers changed from: package-private */
        public String getSCAddress() {
            String ret;
            int len = getByte();
            if (len == 0) {
                ret = null;
            } else {
                try {
                    ret = PhoneNumberUtils.calledPartyBCDToString(this.mPdu, this.mCur, len, 2);
                } catch (RuntimeException tr) {
                    Rlog.d(SmsMessage.LOG_TAG, "invalid SC address: ", tr);
                    ret = null;
                }
            }
            this.mCur += len;
            return ret;
        }

        /* access modifiers changed from: package-private */
        public int getByte() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            return bArr[i] & 255;
        }

        /* access modifiers changed from: package-private */
        public GsmSmsAddress getAddress() {
            int lengthBytes = (((this.mPdu[this.mCur] & 255) + 1) / 2) + 2;
            try {
                GsmSmsAddress ret = new GsmSmsAddress(this.mPdu, this.mCur, lengthBytes);
                this.mCur += lengthBytes;
                return ret;
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: package-private */
        public long getSCTimestampMillis() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            int year = IccUtils.gsmBcdByteToInt(bArr[i]);
            byte[] bArr2 = this.mPdu;
            int i2 = this.mCur;
            this.mCur = i2 + 1;
            int month = IccUtils.gsmBcdByteToInt(bArr2[i2]);
            byte[] bArr3 = this.mPdu;
            int i3 = this.mCur;
            this.mCur = i3 + 1;
            int day = IccUtils.gsmBcdByteToInt(bArr3[i3]);
            byte[] bArr4 = this.mPdu;
            int i4 = this.mCur;
            this.mCur = i4 + 1;
            int hour = IccUtils.gsmBcdByteToInt(bArr4[i4]);
            byte[] bArr5 = this.mPdu;
            int i5 = this.mCur;
            this.mCur = i5 + 1;
            int minute = IccUtils.gsmBcdByteToInt(bArr5[i5]);
            byte[] bArr6 = this.mPdu;
            int i6 = this.mCur;
            this.mCur = i6 + 1;
            int second = IccUtils.gsmBcdByteToInt(bArr6[i6]);
            byte[] bArr7 = this.mPdu;
            int i7 = this.mCur;
            this.mCur = i7 + 1;
            byte tzByte = bArr7[i7];
            int timezoneOffset = IccUtils.gsmBcdByteToInt((byte) (tzByte & -9));
            int timezoneOffset2 = (tzByte & 8) == 0 ? timezoneOffset : -timezoneOffset;
            Time time = new Time(Time.TIMEZONE_UTC);
            time.year = year >= 90 ? year + PreciseDisconnectCause.ECBM_NOT_SUPPORTED : year + 2000;
            time.month = month - 1;
            time.monthDay = day;
            time.hour = hour;
            time.minute = minute;
            time.second = second;
            return time.toMillis(true) - ((long) (((timezoneOffset2 * 15) * 60) * 1000));
        }

        /* access modifiers changed from: package-private */
        public int constructUserData(boolean hasUserDataHeader, boolean dataInSeptets) {
            int bufferLen;
            int offset = this.mCur;
            int offset2 = offset + 1;
            int userDataLength = this.mPdu[offset] & 255;
            int headerSeptets = 0;
            int userDataHeaderLength = 0;
            if (hasUserDataHeader) {
                int offset3 = offset2 + 1;
                userDataHeaderLength = this.mPdu[offset2] & 255;
                byte[] udh = new byte[userDataHeaderLength];
                System.arraycopy(this.mPdu, offset3, udh, 0, userDataHeaderLength);
                this.mUserDataHeader = SmsHeader.fromByteArray(udh);
                int offset4 = offset3 + userDataHeaderLength;
                int headerBits = (userDataHeaderLength + 1) * 8;
                headerSeptets = (headerBits / 7) + (headerBits % 7 > 0 ? 1 : 0);
                this.mUserDataSeptetPadding = (headerSeptets * 7) - headerBits;
                offset2 = offset4;
            }
            if (dataInSeptets) {
                bufferLen = this.mPdu.length - offset2;
            } else {
                bufferLen = userDataLength - (hasUserDataHeader ? userDataHeaderLength + 1 : 0);
                if (bufferLen < 0) {
                    bufferLen = 0;
                }
            }
            this.mUserData = new byte[bufferLen];
            System.arraycopy(this.mPdu, offset2, this.mUserData, 0, this.mUserData.length);
            this.mCur = offset2;
            if (!dataInSeptets) {
                return this.mUserData.length;
            }
            int count = userDataLength - headerSeptets;
            if (count < 0) {
                return 0;
            }
            return count;
        }

        /* access modifiers changed from: package-private */
        public byte[] getUserData() {
            return this.mUserData;
        }

        /* access modifiers changed from: package-private */
        public SmsHeader getUserDataHeader() {
            return this.mUserDataHeader;
        }

        /* access modifiers changed from: package-private */
        public String getUserDataGSM7Bit(int septetCount, int languageTable, int languageShiftTable) {
            String ret = GsmAlphabet.gsm7BitPackedToString(this.mPdu, this.mCur, septetCount, this.mUserDataSeptetPadding, languageTable, languageShiftTable);
            this.mCur += (septetCount * 7) / 8;
            return ret;
        }

        /* access modifiers changed from: package-private */
        public String getUserDataGSM8bit(int byteCount) {
            String ret = GsmAlphabet.gsm8BitUnpackedToString(this.mPdu, this.mCur, byteCount);
            this.mCur += byteCount;
            return ret;
        }

        /* access modifiers changed from: package-private */
        public String getUserDataUCS2(int byteCount) {
            String ret;
            try {
                ret = new String(this.mPdu, this.mCur, byteCount, "utf-16");
            } catch (UnsupportedEncodingException ex) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", ex);
                ret = "";
            }
            this.mCur += byteCount;
            return ret;
        }

        /* access modifiers changed from: package-private */
        public String getUserDataKSC5601(int byteCount) {
            String ret;
            try {
                ret = new String(this.mPdu, this.mCur, byteCount, "KSC5601");
            } catch (UnsupportedEncodingException ex) {
                Rlog.e(SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", ex);
                ret = "";
            }
            this.mCur += byteCount;
            return ret;
        }

        /* access modifiers changed from: package-private */
        public boolean moreDataPresent() {
            return this.mPdu.length > this.mCur;
        }
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence msgBody, boolean use7bitOnly) {
        CharSequence newMsgBody = null;
        if (Resources.getSystem().getBoolean(R.bool.config_sms_force_7bit_encoding)) {
            newMsgBody = Sms7BitEncodingTranslator.translate(msgBody, false);
        }
        if (TextUtils.isEmpty(newMsgBody)) {
            newMsgBody = msgBody;
        }
        GsmAlphabet.TextEncodingDetails ted = GsmAlphabet.countGsmSeptets(newMsgBody, use7bitOnly);
        if (ted == null) {
            return SmsMessageBase.calcUnicodeEncodingDetails(newMsgBody);
        }
        return ted;
    }

    public int getProtocolIdentifier() {
        return this.mProtocolIdentifier;
    }

    /* access modifiers changed from: package-private */
    public int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    public boolean isReplace() {
        return (this.mProtocolIdentifier & 192) == 64 && (this.mProtocolIdentifier & 63) > 0 && (this.mProtocolIdentifier & 63) < 8;
    }

    public boolean isCphsMwiMessage() {
        return ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageClear() || ((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet();
    }

    public boolean isMWIClearMessage() {
        if (this.mIsMwi && !this.mMwiSense) {
            return true;
        }
        if (this.mOriginatingAddress == null || !((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageClear()) {
            return false;
        }
        return true;
    }

    public boolean isMWISetMessage() {
        if (this.mIsMwi && this.mMwiSense) {
            return true;
        }
        if (this.mOriginatingAddress == null || !((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet()) {
            return false;
        }
        return true;
    }

    public boolean isMwiDontStore() {
        if (this.mIsMwi && this.mMwiDontStore) {
            return true;
        }
        if (!isCphsMwiMessage() || !WifiEnterpriseConfig.CA_CERT_ALIAS_DELIMITER.equals(getMessageBody())) {
            return false;
        }
        return true;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public boolean isStatusReportMessage() {
        return this.mIsStatusReportMessage;
    }

    public boolean isReplyPathPresent() {
        return this.mReplyPathPresent;
    }

    private void parsePdu(byte[] pdu) {
        this.mPdu = pdu;
        PduParser p = new PduParser(pdu);
        this.mScAddress = p.getSCAddress();
        String str = this.mScAddress;
        int firstByte = p.getByte();
        this.mMti = firstByte & 3;
        switch (this.mMti) {
            case 0:
            case 3:
                parseSmsDeliver(p, firstByte);
                return;
            case 1:
                parseSmsSubmit(p, firstByte);
                return;
            case 2:
                parseSmsStatusReport(p, firstByte);
                return;
            default:
                throw new RuntimeException("Unsupported message type");
        }
    }

    private void parseSmsStatusReport(PduParser p, int firstByte) {
        boolean hasUserDataHeader = true;
        this.mIsStatusReportMessage = true;
        this.mMessageRef = p.getByte();
        this.mRecipientAddress = p.getAddress();
        this.mScTimeMillis = p.getSCTimestampMillis();
        p.getSCTimestampMillis();
        this.mStatus = p.getByte();
        if (p.moreDataPresent()) {
            int extraParams = p.getByte();
            int moreExtraParams = extraParams;
            while ((moreExtraParams & 128) != 0) {
                moreExtraParams = p.getByte();
            }
            if ((extraParams & 120) == 0) {
                if ((extraParams & 1) != 0) {
                    this.mProtocolIdentifier = p.getByte();
                }
                if ((extraParams & 2) != 0) {
                    this.mDataCodingScheme = p.getByte();
                }
                if ((extraParams & 4) != 0) {
                    if ((firstByte & 64) != 64) {
                        hasUserDataHeader = false;
                    }
                    parseUserData(p, hasUserDataHeader);
                }
            }
        }
    }

    private void parseSmsDeliver(PduParser p, int firstByte) {
        boolean hasUserDataHeader = false;
        this.mReplyPathPresent = (firstByte & 128) == 128;
        this.mOriginatingAddress = p.getAddress();
        SmsAddress smsAddress = this.mOriginatingAddress;
        this.mProtocolIdentifier = p.getByte();
        this.mDataCodingScheme = p.getByte();
        this.mScTimeMillis = p.getSCTimestampMillis();
        if ((firstByte & 64) == 64) {
            hasUserDataHeader = true;
        }
        parseUserData(p, hasUserDataHeader);
    }

    private void parseSmsSubmit(PduParser p, int firstByte) {
        int validityPeriodLength;
        boolean hasUserDataHeader = false;
        this.mReplyPathPresent = (firstByte & 128) == 128;
        this.mMessageRef = p.getByte();
        this.mRecipientAddress = p.getAddress();
        SmsAddress smsAddress = this.mRecipientAddress;
        this.mProtocolIdentifier = p.getByte();
        this.mDataCodingScheme = p.getByte();
        int validityPeriodFormat = (firstByte >> 3) & 3;
        if (validityPeriodFormat == 0) {
            validityPeriodLength = 0;
        } else if (2 == validityPeriodFormat) {
            validityPeriodLength = 1;
        } else {
            validityPeriodLength = 7;
        }
        while (true) {
            int validityPeriodLength2 = validityPeriodLength - 1;
            if (validityPeriodLength <= 0) {
                break;
            }
            p.getByte();
            validityPeriodLength = validityPeriodLength2;
        }
        if ((firstByte & 64) == 64) {
            hasUserDataHeader = true;
        }
        parseUserData(p, hasUserDataHeader);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01e1, code lost:
        if ((r0.mDataCodingScheme & 240) == 224) goto L_0x01e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01ea, code lost:
        if ((r0.mDataCodingScheme & 3) != 0) goto L_0x01ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ec, code lost:
        r0.mMwiDontStore = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseUserData(com.android.internal.telephony.gsm.SmsMessage.PduParser r17, boolean r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = 0
            r4 = 0
            r5 = 0
            int r6 = r0.mDataCodingScheme
            r7 = 128(0x80, float:1.794E-43)
            r6 = r6 & r7
            r8 = 17891527(0x11100c7, float:2.6632852E-38)
            r9 = 208(0xd0, float:2.91E-43)
            r10 = 224(0xe0, float:3.14E-43)
            r11 = 240(0xf0, float:3.36E-43)
            r12 = 0
            r13 = 1
            if (r6 != 0) goto L_0x0085
            int r6 = r0.mDataCodingScheme
            r6 = r6 & 32
            if (r6 == 0) goto L_0x0023
            r6 = r13
            goto L_0x0024
        L_0x0023:
            r6 = r12
        L_0x0024:
            r4 = r6
            int r6 = r0.mDataCodingScheme
            r6 = r6 & 16
            if (r6 == 0) goto L_0x002d
            r6 = r13
            goto L_0x002e
        L_0x002d:
            r6 = r12
        L_0x002e:
            r3 = r6
            if (r4 == 0) goto L_0x004d
            java.lang.String r6 = "SmsMessage"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "4 - Unsupported SMS data coding scheme (compression) "
            r14.append(r15)
            int r15 = r0.mDataCodingScheme
            r15 = r15 & 255(0xff, float:3.57E-43)
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            android.telephony.Rlog.w((java.lang.String) r6, (java.lang.String) r14)
            goto L_0x016b
        L_0x004d:
            int r6 = r0.mDataCodingScheme
            int r6 = r6 >> 2
            r6 = r6 & 3
            switch(r6) {
                case 0: goto L_0x0081;
                case 1: goto L_0x0059;
                case 2: goto L_0x0057;
                case 3: goto L_0x0065;
                default: goto L_0x0056;
            }
        L_0x0056:
            goto L_0x0083
        L_0x0057:
            r5 = 3
            goto L_0x0083
        L_0x0059:
            android.content.res.Resources r6 = android.content.res.Resources.getSystem()
            boolean r14 = r6.getBoolean(r8)
            if (r14 == 0) goto L_0x0065
            r5 = 2
            goto L_0x0083
        L_0x0065:
            java.lang.String r6 = "SmsMessage"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "1 - Unsupported SMS data coding scheme "
            r14.append(r15)
            int r15 = r0.mDataCodingScheme
            r15 = r15 & 255(0xff, float:3.57E-43)
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            android.telephony.Rlog.w((java.lang.String) r6, (java.lang.String) r14)
            r5 = 2
            goto L_0x0083
        L_0x0081:
            r5 = 1
        L_0x0083:
            goto L_0x016b
        L_0x0085:
            int r6 = r0.mDataCodingScheme
            r6 = r6 & r11
            if (r6 != r11) goto L_0x0098
            r3 = 1
            r4 = 0
            int r6 = r0.mDataCodingScheme
            r6 = r6 & 4
            if (r6 != 0) goto L_0x0095
            r5 = 1
            goto L_0x016b
        L_0x0095:
            r5 = 2
            goto L_0x016b
        L_0x0098:
            int r6 = r0.mDataCodingScheme
            r6 = r6 & r11
            r14 = 192(0xc0, float:2.69E-43)
            if (r6 == r14) goto L_0x00f0
            int r6 = r0.mDataCodingScheme
            r6 = r6 & r11
            if (r6 == r9) goto L_0x00f0
            int r6 = r0.mDataCodingScheme
            r6 = r6 & r11
            if (r6 != r10) goto L_0x00aa
            goto L_0x00f0
        L_0x00aa:
            int r6 = r0.mDataCodingScheme
            r6 = r6 & r14
            if (r6 != r7) goto L_0x00d4
            int r6 = r0.mDataCodingScheme
            r14 = 132(0x84, float:1.85E-43)
            if (r6 != r14) goto L_0x00b8
            r5 = 4
            goto L_0x016b
        L_0x00b8:
            java.lang.String r6 = "SmsMessage"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "5 - Unsupported SMS data coding scheme "
            r14.append(r15)
            int r15 = r0.mDataCodingScheme
            r15 = r15 & 255(0xff, float:3.57E-43)
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            android.telephony.Rlog.w((java.lang.String) r6, (java.lang.String) r14)
            goto L_0x016b
        L_0x00d4:
            java.lang.String r6 = "SmsMessage"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "3 - Unsupported SMS data coding scheme "
            r14.append(r15)
            int r15 = r0.mDataCodingScheme
            r15 = r15 & 255(0xff, float:3.57E-43)
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            android.telephony.Rlog.w((java.lang.String) r6, (java.lang.String) r14)
            goto L_0x016b
        L_0x00f0:
            int r6 = r0.mDataCodingScheme
            r6 = r6 & r11
            if (r6 != r10) goto L_0x00f7
            r5 = 3
            goto L_0x00f8
        L_0x00f7:
            r5 = 1
        L_0x00f8:
            r4 = 0
            int r6 = r0.mDataCodingScheme
            r15 = 8
            r6 = r6 & r15
            if (r6 != r15) goto L_0x0102
            r6 = r13
            goto L_0x0103
        L_0x0102:
            r6 = r12
        L_0x0103:
            int r15 = r0.mDataCodingScheme
            r15 = r15 & 3
            if (r15 != 0) goto L_0x014e
            r0.mIsMwi = r13
            r0.mMwiSense = r6
            int r15 = r0.mDataCodingScheme
            r15 = r15 & r11
            if (r15 != r14) goto L_0x0114
            r14 = r13
            goto L_0x0115
        L_0x0114:
            r14 = r12
        L_0x0115:
            r0.mMwiDontStore = r14
            if (r6 != r13) goto L_0x011d
            r14 = -1
            r0.mVoiceMailCount = r14
            goto L_0x011f
        L_0x011d:
            r0.mVoiceMailCount = r12
        L_0x011f:
            java.lang.String r14 = "SmsMessage"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.String r8 = "MWI in DCS for Vmail. DCS = "
            r15.append(r8)
            int r8 = r0.mDataCodingScheme
            r8 = r8 & 255(0xff, float:3.57E-43)
            r15.append(r8)
            java.lang.String r8 = " Dont store = "
            r15.append(r8)
            boolean r8 = r0.mMwiDontStore
            r15.append(r8)
            java.lang.String r8 = " vmail count = "
            r15.append(r8)
            int r8 = r0.mVoiceMailCount
            r15.append(r8)
            java.lang.String r8 = r15.toString()
            android.telephony.Rlog.w((java.lang.String) r14, (java.lang.String) r8)
            goto L_0x016a
        L_0x014e:
            r0.mIsMwi = r12
            java.lang.String r8 = "SmsMessage"
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "MWI in DCS for fax/email/other: "
            r14.append(r15)
            int r15 = r0.mDataCodingScheme
            r15 = r15 & 255(0xff, float:3.57E-43)
            r14.append(r15)
            java.lang.String r14 = r14.toString()
            android.telephony.Rlog.w((java.lang.String) r8, (java.lang.String) r14)
        L_0x016a:
        L_0x016b:
            if (r5 != r13) goto L_0x016f
            r6 = r13
            goto L_0x0170
        L_0x016f:
            r6 = r12
        L_0x0170:
            int r6 = r1.constructUserData(r2, r6)
            byte[] r8 = r17.getUserData()
            r0.mUserData = r8
            com.android.internal.telephony.SmsHeader r8 = r17.getUserDataHeader()
            r0.mUserDataHeader = r8
            if (r2 == 0) goto L_0x0231
            com.android.internal.telephony.SmsHeader r8 = r0.mUserDataHeader
            java.util.ArrayList<com.android.internal.telephony.SmsHeader$SpecialSmsMsg> r8 = r8.specialSmsMsgList
            int r8 = r8.size()
            if (r8 == 0) goto L_0x0231
            com.android.internal.telephony.SmsHeader r8 = r0.mUserDataHeader
            java.util.ArrayList<com.android.internal.telephony.SmsHeader$SpecialSmsMsg> r8 = r8.specialSmsMsgList
            java.util.Iterator r8 = r8.iterator()
        L_0x0194:
            boolean r14 = r8.hasNext()
            if (r14 == 0) goto L_0x0231
            java.lang.Object r14 = r8.next()
            com.android.internal.telephony.SmsHeader$SpecialSmsMsg r14 = (com.android.internal.telephony.SmsHeader.SpecialSmsMsg) r14
            int r15 = r14.msgIndType
            r15 = r15 & 255(0xff, float:3.57E-43)
            if (r15 == 0) goto L_0x01c2
            if (r15 != r7) goto L_0x01a9
            goto L_0x01c2
        L_0x01a9:
            java.lang.String r10 = "SmsMessage"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r11 = "TP_UDH fax/email/extended msg/multisubscriber profile. Msg Ind = "
            r9.append(r11)
            r9.append(r15)
            java.lang.String r9 = r9.toString()
            android.telephony.Rlog.w((java.lang.String) r10, (java.lang.String) r9)
            r11 = 208(0xd0, float:2.91E-43)
            goto L_0x0227
        L_0x01c2:
            r0.mIsMwi = r13
            if (r15 != r7) goto L_0x01cf
            r0.mMwiDontStore = r12
        L_0x01c8:
            r7 = 224(0xe0, float:3.14E-43)
            r10 = 240(0xf0, float:3.36E-43)
            r11 = 208(0xd0, float:2.91E-43)
            goto L_0x01ee
        L_0x01cf:
            boolean r9 = r0.mMwiDontStore
            if (r9 != 0) goto L_0x01c8
            int r9 = r0.mDataCodingScheme
            r10 = 240(0xf0, float:3.36E-43)
            r9 = r9 & r10
            r11 = 208(0xd0, float:2.91E-43)
            if (r9 == r11) goto L_0x01e4
            int r9 = r0.mDataCodingScheme
            r9 = r9 & r10
            r7 = 224(0xe0, float:3.14E-43)
            if (r9 != r7) goto L_0x01ec
            goto L_0x01e6
        L_0x01e4:
            r7 = 224(0xe0, float:3.14E-43)
        L_0x01e6:
            int r9 = r0.mDataCodingScheme
            r9 = r9 & 3
            if (r9 == 0) goto L_0x01ee
        L_0x01ec:
            r0.mMwiDontStore = r13
        L_0x01ee:
            int r9 = r14.msgCount
            r9 = r9 & 255(0xff, float:3.57E-43)
            r0.mVoiceMailCount = r9
            int r9 = r0.mVoiceMailCount
            if (r9 <= 0) goto L_0x01fb
            r0.mMwiSense = r13
            goto L_0x01fd
        L_0x01fb:
            r0.mMwiSense = r12
        L_0x01fd:
            java.lang.String r9 = "SmsMessage"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r10 = "MWI in TP-UDH for Vmail. Msg Ind = "
            r7.append(r10)
            r7.append(r15)
            java.lang.String r10 = " Dont store = "
            r7.append(r10)
            boolean r10 = r0.mMwiDontStore
            r7.append(r10)
            java.lang.String r10 = " Vmail count = "
            r7.append(r10)
            int r10 = r0.mVoiceMailCount
            r7.append(r10)
            java.lang.String r7 = r7.toString()
            android.telephony.Rlog.w((java.lang.String) r9, (java.lang.String) r7)
        L_0x0227:
            r9 = r11
            r7 = 128(0x80, float:1.794E-43)
            r10 = 224(0xe0, float:3.14E-43)
            r11 = 240(0xf0, float:3.36E-43)
            goto L_0x0194
        L_0x0231:
            r7 = 0
            switch(r5) {
                case 0: goto L_0x0272;
                case 1: goto L_0x025b;
                case 2: goto L_0x0244;
                case 3: goto L_0x023d;
                case 4: goto L_0x0236;
                default: goto L_0x0235;
            }
        L_0x0235:
            goto L_0x0275
        L_0x0236:
            java.lang.String r7 = r1.getUserDataKSC5601(r6)
            r0.mMessageBody = r7
            goto L_0x0275
        L_0x023d:
            java.lang.String r7 = r1.getUserDataUCS2(r6)
            r0.mMessageBody = r7
            goto L_0x0275
        L_0x0244:
            android.content.res.Resources r8 = android.content.res.Resources.getSystem()
            r9 = 17891527(0x11100c7, float:2.6632852E-38)
            boolean r9 = r8.getBoolean(r9)
            if (r9 == 0) goto L_0x0258
            java.lang.String r7 = r1.getUserDataGSM8bit(r6)
            r0.mMessageBody = r7
            goto L_0x0275
        L_0x0258:
            r0.mMessageBody = r7
            goto L_0x0275
        L_0x025b:
            if (r2 == 0) goto L_0x0263
            com.android.internal.telephony.SmsHeader r7 = r0.mUserDataHeader
            int r7 = r7.languageTable
            goto L_0x0264
        L_0x0263:
            r7 = r12
        L_0x0264:
            if (r2 == 0) goto L_0x026b
            com.android.internal.telephony.SmsHeader r8 = r0.mUserDataHeader
            int r12 = r8.languageShiftTable
        L_0x026b:
            java.lang.String r7 = r1.getUserDataGSM7Bit(r6, r7, r12)
            r0.mMessageBody = r7
            goto L_0x0275
        L_0x0272:
            r0.mMessageBody = r7
        L_0x0275:
            java.lang.String r7 = r0.mMessageBody
            if (r7 == 0) goto L_0x027c
            r16.parseMessageBody()
        L_0x027c:
            if (r3 != 0) goto L_0x0283
            com.android.internal.telephony.SmsConstants$MessageClass r7 = com.android.internal.telephony.SmsConstants.MessageClass.UNKNOWN
            r0.messageClass = r7
            goto L_0x029f
        L_0x0283:
            int r7 = r0.mDataCodingScheme
            r7 = r7 & 3
            switch(r7) {
                case 0: goto L_0x029a;
                case 1: goto L_0x0295;
                case 2: goto L_0x0290;
                case 3: goto L_0x028b;
                default: goto L_0x028a;
            }
        L_0x028a:
            goto L_0x029f
        L_0x028b:
            com.android.internal.telephony.SmsConstants$MessageClass r7 = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_3
            r0.messageClass = r7
            goto L_0x029f
        L_0x0290:
            com.android.internal.telephony.SmsConstants$MessageClass r7 = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_2
            r0.messageClass = r7
            goto L_0x029f
        L_0x0295:
            com.android.internal.telephony.SmsConstants$MessageClass r7 = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_1
            r0.messageClass = r7
            goto L_0x029f
        L_0x029a:
            com.android.internal.telephony.SmsConstants$MessageClass r7 = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_0
            r0.messageClass = r7
        L_0x029f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.telephony.gsm.SmsMessage.parseUserData(com.android.internal.telephony.gsm.SmsMessage$PduParser, boolean):void");
    }

    public SmsConstants.MessageClass getMessageClass() {
        return this.messageClass;
    }

    /* access modifiers changed from: package-private */
    public boolean isUsimDataDownload() {
        return this.messageClass == SmsConstants.MessageClass.CLASS_2 && (this.mProtocolIdentifier == 127 || this.mProtocolIdentifier == 124);
    }

    public int getNumOfVoicemails() {
        if (!this.mIsMwi && isCphsMwiMessage()) {
            if (this.mOriginatingAddress == null || !((GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet()) {
                this.mVoiceMailCount = 0;
            } else {
                this.mVoiceMailCount = 255;
            }
            Rlog.v(LOG_TAG, "CPHS voice mail message");
        }
        return this.mVoiceMailCount;
    }
}
