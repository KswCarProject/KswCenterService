package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorWindow;
import android.hardware.contexthub.V1_0.HostEndPoint;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.IFinancialSmsCallback;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SeempLog;
import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.telephony.IMms;
import com.android.internal.telephony.ISms;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.SmsRawData;
import com.android.internal.util.FunctionalUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public final class SmsManager {
    public static final int CDMA_SMS_RECORD_LENGTH = 255;
    public static final int CELL_BROADCAST_RAN_TYPE_CDMA = 1;
    public static final int CELL_BROADCAST_RAN_TYPE_GSM = 0;
    public static final String EXTRA_MMS_DATA = "android.telephony.extra.MMS_DATA";
    public static final String EXTRA_MMS_HTTP_STATUS = "android.telephony.extra.MMS_HTTP_STATUS";
    public static final String EXTRA_SIM_SUBSCRIPTION_ID = "android.telephony.extra.SIM_SUBSCRIPTION_ID";
    public static final String EXTRA_SMS_MESSAGE = "android.telephony.extra.SMS_MESSAGE";
    public static final String EXTRA_STATUS = "android.telephony.extra.STATUS";
    public static final String MESSAGE_STATUS_READ = "read";
    public static final String MESSAGE_STATUS_SEEN = "seen";
    public static final String MMS_CONFIG_ALIAS_ENABLED = "aliasEnabled";
    public static final String MMS_CONFIG_ALIAS_MAX_CHARS = "aliasMaxChars";
    public static final String MMS_CONFIG_ALIAS_MIN_CHARS = "aliasMinChars";
    public static final String MMS_CONFIG_ALLOW_ATTACH_AUDIO = "allowAttachAudio";
    public static final String MMS_CONFIG_APPEND_TRANSACTION_ID = "enabledTransID";
    public static final String MMS_CONFIG_CLOSE_CONNECTION = "mmsCloseConnection";
    public static final String MMS_CONFIG_EMAIL_GATEWAY_NUMBER = "emailGatewayNumber";
    public static final String MMS_CONFIG_GROUP_MMS_ENABLED = "enableGroupMms";
    public static final String MMS_CONFIG_HTTP_PARAMS = "httpParams";
    public static final String MMS_CONFIG_HTTP_SOCKET_TIMEOUT = "httpSocketTimeout";
    public static final String MMS_CONFIG_MAX_IMAGE_HEIGHT = "maxImageHeight";
    public static final String MMS_CONFIG_MAX_IMAGE_WIDTH = "maxImageWidth";
    public static final String MMS_CONFIG_MAX_MESSAGE_SIZE = "maxMessageSize";
    public static final String MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE = "maxMessageTextSize";
    public static final String MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED = "enableMMSDeliveryReports";
    public static final String MMS_CONFIG_MMS_ENABLED = "enabledMMS";
    public static final String MMS_CONFIG_MMS_READ_REPORT_ENABLED = "enableMMSReadReports";
    public static final String MMS_CONFIG_MULTIPART_SMS_ENABLED = "enableMultipartSMS";
    public static final String MMS_CONFIG_NAI_SUFFIX = "naiSuffix";
    public static final String MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED = "enabledNotifyWapMMSC";
    public static final String MMS_CONFIG_RECIPIENT_LIMIT = "recipientLimit";
    public static final String MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES = "sendMultipartSmsAsSeparateMessages";
    public static final String MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS = "config_cellBroadcastAppLinks";
    public static final String MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED = "enableSMSDeliveryReports";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD = "smsToMmsTextLengthThreshold";
    public static final String MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD = "smsToMmsTextThreshold";
    public static final String MMS_CONFIG_SUBJECT_MAX_LENGTH = "maxSubjectLength";
    public static final String MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER = "supportHttpCharsetHeader";
    public static final String MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION = "supportMmsContentDisposition";
    public static final String MMS_CONFIG_UA_PROF_TAG_NAME = "uaProfTagName";
    public static final String MMS_CONFIG_UA_PROF_URL = "uaProfUrl";
    public static final String MMS_CONFIG_USER_AGENT = "userAgent";
    public static final int MMS_ERROR_CONFIGURATION_ERROR = 7;
    public static final int MMS_ERROR_HTTP_FAILURE = 4;
    public static final int MMS_ERROR_INVALID_APN = 2;
    public static final int MMS_ERROR_IO_ERROR = 5;
    public static final int MMS_ERROR_NO_DATA_NETWORK = 8;
    public static final int MMS_ERROR_RETRY = 6;
    public static final int MMS_ERROR_UNABLE_CONNECT_MMS = 3;
    public static final int MMS_ERROR_UNSPECIFIED = 1;
    private static final String NO_DEFAULT_EXTRA = "noDefault";
    private static final String PHONE_PACKAGE_NAME = "com.android.phone";
    public static final String REGEX_PREFIX_DELIMITER = ",";
    @SystemApi
    public static final int RESULT_CANCELLED = 23;
    @SystemApi
    public static final int RESULT_ENCODING_ERROR = 18;
    @SystemApi
    public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
    public static final int RESULT_ERROR_LIMIT_EXCEEDED = 5;
    @SystemApi
    public static final int RESULT_ERROR_NONE = 0;
    public static final int RESULT_ERROR_NO_SERVICE = 4;
    public static final int RESULT_ERROR_NULL_PDU = 3;
    public static final int RESULT_ERROR_RADIO_OFF = 2;
    public static final int RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED = 8;
    public static final int RESULT_ERROR_SHORT_CODE_NOT_ALLOWED = 7;
    @SystemApi
    public static final int RESULT_INTERNAL_ERROR = 21;
    @SystemApi
    public static final int RESULT_INVALID_ARGUMENTS = 11;
    @SystemApi
    public static final int RESULT_INVALID_SMSC_ADDRESS = 19;
    @SystemApi
    public static final int RESULT_INVALID_SMS_FORMAT = 14;
    @SystemApi
    public static final int RESULT_INVALID_STATE = 12;
    @SystemApi
    public static final int RESULT_MODEM_ERROR = 16;
    @SystemApi
    public static final int RESULT_NETWORK_ERROR = 17;
    @SystemApi
    public static final int RESULT_NETWORK_REJECT = 10;
    @SystemApi
    public static final int RESULT_NO_MEMORY = 13;
    @SystemApi
    public static final int RESULT_NO_RESOURCES = 22;
    @SystemApi
    public static final int RESULT_OPERATION_NOT_ALLOWED = 20;
    @SystemApi
    public static final int RESULT_RADIO_NOT_AVAILABLE = 9;
    @SystemApi
    public static final int RESULT_REQUEST_NOT_SUPPORTED = 24;
    public static final int RESULT_STATUS_SUCCESS = 0;
    public static final int RESULT_STATUS_TIMEOUT = 1;
    @SystemApi
    public static final int RESULT_SYSTEM_ERROR = 15;
    public static final int SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int SMS_CATEGORY_NOT_SHORT_CODE = 0;
    public static final int SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE = 3;
    public static final int SMS_CATEGORY_PREMIUM_SHORT_CODE = 4;
    public static final int SMS_CATEGORY_STANDARD_SHORT_CODE = 2;
    public static final int SMS_MESSAGE_PERIOD_NOT_SPECIFIED = -1;
    public static final int SMS_MESSAGE_PRIORITY_NOT_SPECIFIED = -1;
    public static final int SMS_RECORD_LENGTH = 176;
    public static final int SMS_TYPE_INCOMING = 0;
    public static final int SMS_TYPE_OUTGOING = 1;
    public static final int STATUS_ON_ICC_FREE = 0;
    public static final int STATUS_ON_ICC_READ = 1;
    public static final int STATUS_ON_ICC_SENT = 5;
    public static final int STATUS_ON_ICC_UNREAD = 3;
    public static final int STATUS_ON_ICC_UNSENT = 7;
    private static final String TAG = "SmsManager";
    private static final SmsManager sInstance = new SmsManager(Integer.MAX_VALUE);
    private static final Object sLockObject = new Object();
    private static final Map<Integer, SmsManager> sSubInstances = new ArrayMap();
    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    private int mSubId;

    public static abstract class FinancialSmsCallback {
        public abstract void onFinancialSmsMessages(CursorWindow cursorWindow);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SmsShortCodeCategory {
    }

    private interface SubscriptionResolverResult {
        void onFailure();

        void onSuccess(int i);
    }

    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        SeempLog.record_str(75, destinationAddress);
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, true, ActivityThread.currentPackageName());
    }

    private void sendTextMessageInternal(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage, String packageName) {
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (!TextUtils.isEmpty(text)) {
            Context context = ActivityThread.currentApplication().getApplicationContext();
            if (persistMessage) {
                final String str = packageName;
                final String str2 = destinationAddress;
                final String str3 = scAddress;
                final String str4 = text;
                final PendingIntent pendingIntent = sentIntent;
                final PendingIntent pendingIntent2 = deliveryIntent;
                final boolean z = persistMessage;
                final Context context2 = context;
                AnonymousClass1 r1 = new SubscriptionResolverResult() {
                    public void onSuccess(int subId) {
                        try {
                            SmsManager.getISmsServiceOrThrow().sendTextForSubscriber(subId, str, str2, str3, str4, pendingIntent, pendingIntent2, z);
                        } catch (RemoteException e) {
                            Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage());
                            SmsManager.notifySmsGenericError(pendingIntent);
                        }
                    }

                    public void onFailure() {
                        SmsManager.notifySmsErrorNoDefaultSet(context2, pendingIntent);
                    }
                };
                resolveSubscriptionForOperation(r1);
                return;
            }
            try {
                getISmsServiceOrThrow().sendTextForSubscriber(getSubscriptionId(), packageName, destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage);
            } catch (RemoteException e) {
                Log.e(TAG, "sendTextMessageInternal (no persist): Couldn't send SMS, exception - " + e.getMessage());
                notifySmsGenericError(sentIntent);
            }
        } else {
            throw new IllegalArgumentException("Invalid message body");
        }
    }

    public void sendTextMessageWithoutPersisting(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, false, ActivityThread.currentPackageName());
    }

    public void sendTextMessageWithSelfPermissions(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage) {
        String str = destinationAddress;
        SeempLog.record_str(75, destinationAddress);
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (!TextUtils.isEmpty(text)) {
            try {
                getISmsServiceOrThrow().sendTextForSubscriberWithSelfPermissions(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage);
            } catch (RemoteException e) {
                notifySmsGenericError(sentIntent);
            }
        } else {
            throw new IllegalArgumentException("Invalid message body");
        }
    }

    @UnsupportedAppUsage
    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, int priority, boolean expectMore, int validityPeriod) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, true, priority, expectMore, validityPeriod);
    }

    private void sendTextMessageInternal(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, boolean persistMessage, int priority, boolean expectMore, int validityPeriod) {
        int priority2 = priority;
        int i = validityPeriod;
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (!TextUtils.isEmpty(text)) {
            if (priority2 < 0 || priority2 > 3) {
                priority2 = -1;
            }
            final int finalPriority = priority2;
            final int finalValidity = (i < 5 || i > 635040) ? -1 : i;
            Context context = ActivityThread.currentApplication().getApplicationContext();
            if (persistMessage) {
                final String str = destinationAddress;
                final String str2 = scAddress;
                final String str3 = text;
                final PendingIntent pendingIntent = sentIntent;
                final PendingIntent pendingIntent2 = deliveryIntent;
                final boolean z = persistMessage;
                final boolean z2 = expectMore;
                final Context context2 = context;
                resolveSubscriptionForOperation(new SubscriptionResolverResult() {
                    public void onSuccess(int subId) {
                        try {
                            ISms iSms = SmsManager.getISmsServiceOrThrow();
                            if (iSms != null) {
                                iSms.sendTextForSubscriberWithOptions(subId, ActivityThread.currentPackageName(), str, str2, str3, pendingIntent, pendingIntent2, z, finalPriority, z2, finalValidity);
                            }
                        } catch (RemoteException e) {
                            Log.e(SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage());
                            SmsManager.notifySmsGenericError(pendingIntent);
                        }
                    }

                    public void onFailure() {
                        SmsManager.notifySmsErrorNoDefaultSet(context2, pendingIntent);
                    }
                });
                return;
            }
            try {
                ISms iSms = getISmsServiceOrThrow();
                if (iSms != null) {
                    iSms.sendTextForSubscriberWithOptions(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, text, sentIntent, deliveryIntent, persistMessage, finalPriority, expectMore, finalValidity);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "sendTextMessageInternal(no persist): Couldn't send SMS, exception - " + e.getMessage());
                notifySmsGenericError(sentIntent);
            }
        } else {
            throw new IllegalArgumentException("Invalid message body");
        }
    }

    @UnsupportedAppUsage
    public void sendTextMessageWithoutPersisting(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent, int priority, boolean expectMore, int validityPeriod) {
        sendTextMessageInternal(destinationAddress, scAddress, text, sentIntent, deliveryIntent, false, priority, expectMore, validityPeriod);
    }

    public void injectSmsPdu(byte[] pdu, String format, PendingIntent receivedIntent) {
        if (format.equals("3gpp") || format.equals("3gpp2")) {
            try {
                ISms iSms = ISms.Stub.asInterface(ServiceManager.getService("isms"));
                if (iSms != null) {
                    iSms.injectSmsPduForSubscriber(getSubscriptionId(), pdu, format, receivedIntent);
                }
            } catch (RemoteException e) {
                if (receivedIntent != null) {
                    try {
                        receivedIntent.send(2);
                    } catch (PendingIntent.CanceledException e2) {
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid pdu format. format must be either 3gpp or 3gpp2");
        }
    }

    public ArrayList<String> divideMessage(String text) {
        if (text != null) {
            return SmsMessage.fragmentText(text, getSubscriptionId());
        }
        throw new IllegalArgumentException("text is null");
    }

    public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, true, ActivityThread.currentPackageName());
    }

    public void sendMultipartTextMessageExternal(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents, String packageName) {
        String str;
        if (ActivityThread.currentPackageName() == null) {
            str = packageName;
        } else {
            str = ActivityThread.currentPackageName();
        }
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, true, str);
    }

    private void sendMultipartTextMessageInternal(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessage, String packageName) {
        List<String> list = parts;
        List<PendingIntent> list2 = sentIntents;
        List<PendingIntent> list3 = deliveryIntents;
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (list == null || parts.size() < 1) {
            throw new IllegalArgumentException("Invalid message body");
        } else if (parts.size() > 1) {
            Context context = ActivityThread.currentApplication().getApplicationContext();
            if (persistMessage) {
                final String str = packageName;
                final String str2 = destinationAddress;
                final String str3 = scAddress;
                final List<String> list4 = parts;
                final List<PendingIntent> list5 = sentIntents;
                final List<PendingIntent> list6 = deliveryIntents;
                final boolean z = persistMessage;
                final Context context2 = context;
                AnonymousClass3 r1 = new SubscriptionResolverResult() {
                    public void onSuccess(int subId) {
                        try {
                            SmsManager.getISmsServiceOrThrow().sendMultipartTextForSubscriber(subId, str, str2, str3, list4, list5, list6, z);
                        } catch (RemoteException e) {
                            Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                            SmsManager.notifySmsGenericError((List<PendingIntent>) list5);
                        }
                    }

                    public void onFailure() {
                        SmsManager.notifySmsErrorNoDefaultSet(context2, (List<PendingIntent>) list5);
                    }
                };
                resolveSubscriptionForOperation(r1);
                return;
            }
            try {
                ISms iSms = getISmsServiceOrThrow();
                if (iSms != null) {
                    iSms.sendMultipartTextForSubscriber(getSubscriptionId(), packageName, destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessage);
                }
            } catch (RemoteException e) {
                Log.e(TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                notifySmsGenericError(sentIntents);
            }
        } else {
            PendingIntent sentIntent = null;
            PendingIntent deliveryIntent = null;
            if (list2 != null && sentIntents.size() > 0) {
                sentIntent = list2.get(0);
            }
            if (list3 != null && deliveryIntents.size() > 0) {
                deliveryIntent = list3.get(0);
            }
            PendingIntent deliveryIntent2 = deliveryIntent;
            sendTextMessageInternal(destinationAddress, scAddress, list.get(0), sentIntent, deliveryIntent2, true, packageName);
        }
    }

    @SystemApi
    public void sendMultipartTextMessageWithoutPersisting(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, false, ActivityThread.currentPackageName());
    }

    @UnsupportedAppUsage
    public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents, int priority, boolean expectMore, int validityPeriod) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, true, priority, expectMore, validityPeriod);
    }

    private void sendMultipartTextMessageInternal(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessage, int priority, boolean expectMore, int validityPeriod) {
        List<String> list = parts;
        List<PendingIntent> list2 = sentIntents;
        List<PendingIntent> list3 = deliveryIntents;
        int i = priority;
        int i2 = validityPeriod;
        if (TextUtils.isEmpty(destinationAddress)) {
            List<String> list4 = list;
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (list == null || parts.size() < 1) {
            List<String> list5 = list;
            throw new IllegalArgumentException("Invalid message body");
        } else {
            if (i < 0 || i > 3) {
                i = -1;
            }
            int priority2 = i;
            int validityPeriod2 = (i2 < 5 || i2 > 635040) ? -1 : i2;
            if (parts.size() > 1) {
                final int finalPriority = priority2;
                final int finalValidity = validityPeriod2;
                Context context = ActivityThread.currentApplication().getApplicationContext();
                if (persistMessage) {
                    final String str = destinationAddress;
                    final String str2 = scAddress;
                    final List<String> list6 = parts;
                    final List<PendingIntent> list7 = sentIntents;
                    final List<PendingIntent> list8 = deliveryIntents;
                    final boolean z = persistMessage;
                    final boolean z2 = expectMore;
                    final Context context2 = context;
                    resolveSubscriptionForOperation(new SubscriptionResolverResult() {
                        public void onSuccess(int subId) {
                            try {
                                ISms iSms = SmsManager.getISmsServiceOrThrow();
                                if (iSms != null) {
                                    iSms.sendMultipartTextForSubscriberWithOptions(subId, ActivityThread.currentPackageName(), str, str2, list6, list7, list8, z, finalPriority, z2, finalValidity);
                                }
                            } catch (RemoteException e) {
                                Log.e(SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                                SmsManager.notifySmsGenericError((List<PendingIntent>) list7);
                            }
                        }

                        public void onFailure() {
                            SmsManager.notifySmsErrorNoDefaultSet(context2, (List<PendingIntent>) list7);
                        }
                    });
                    List<PendingIntent> list9 = list3;
                    List<PendingIntent> list10 = list2;
                    List<String> list11 = list;
                    return;
                }
                try {
                    ISms iSms = getISmsServiceOrThrow();
                    if (iSms != null) {
                        List<PendingIntent> list12 = list3;
                        List<PendingIntent> list13 = list2;
                        List<String> list14 = list;
                        try {
                            iSms.sendMultipartTextForSubscriberWithOptions(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, parts, sentIntents, deliveryIntents, persistMessage, finalPriority, expectMore, finalValidity);
                        } catch (RemoteException e) {
                            e = e;
                        }
                    } else {
                        List<PendingIntent> list15 = list3;
                        List<PendingIntent> list16 = list2;
                        List<String> list17 = list;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    List<PendingIntent> list18 = list3;
                    List<PendingIntent> list19 = list2;
                    List<String> list20 = list;
                    Log.e(TAG, "sendMultipartTextMessageInternal (no persist): Couldn't send SMS - " + e.getMessage());
                    notifySmsGenericError(sentIntents);
                }
            } else {
                List<PendingIntent> list21 = list3;
                List<PendingIntent> list22 = list2;
                List<String> list23 = list;
                PendingIntent sentIntent = null;
                PendingIntent deliveryIntent = null;
                if (list22 != null && sentIntents.size() > 0) {
                    sentIntent = list22.get(0);
                }
                if (list21 != null && deliveryIntents.size() > 0) {
                    deliveryIntent = list21.get(0);
                }
                sendTextMessageInternal(destinationAddress, scAddress, list23.get(0), sentIntent, deliveryIntent, persistMessage, priority2, expectMore, validityPeriod2);
            }
        }
    }

    public void sendMultipartTextMessageWithoutPersisting(String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, int priority, boolean expectMore, int validityPeriod) {
        sendMultipartTextMessageInternal(destinationAddress, scAddress, parts, sentIntents, deliveryIntents, false, priority, expectMore, validityPeriod);
    }

    public void sendDataMessage(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        byte[] bArr = data;
        String str = destinationAddress;
        SeempLog.record_str(73, destinationAddress);
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Invalid message data");
        } else {
            final String str2 = destinationAddress;
            final String str3 = scAddress;
            final short s = destinationPort;
            final byte[] bArr2 = data;
            final PendingIntent pendingIntent = sentIntent;
            final PendingIntent pendingIntent2 = deliveryIntent;
            final Context applicationContext = ActivityThread.currentApplication().getApplicationContext();
            resolveSubscriptionForOperation(new SubscriptionResolverResult() {
                public void onSuccess(int subId) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendDataForSubscriber(subId, ActivityThread.currentPackageName(), str2, str3, 65535 & s, bArr2, pendingIntent, pendingIntent2);
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendDataMessage: Couldn't send SMS - Exception: " + e.getMessage());
                        SmsManager.notifySmsGenericError(pendingIntent);
                    }
                }

                public void onFailure() {
                    SmsManager.notifySmsErrorNoDefaultSet(applicationContext, pendingIntent);
                }
            });
        }
    }

    public void sendDataMessageWithSelfPermissions(String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        byte[] bArr = data;
        String str = destinationAddress;
        SeempLog.record_str(73, destinationAddress);
        if (TextUtils.isEmpty(destinationAddress)) {
            throw new IllegalArgumentException("Invalid destinationAddress");
        } else if (bArr == null || bArr.length == 0) {
            throw new IllegalArgumentException("Invalid message data");
        } else {
            try {
                getISmsServiceOrThrow().sendDataForSubscriberWithSelfPermissions(getSubscriptionId(), ActivityThread.currentPackageName(), destinationAddress, scAddress, destinationPort & HostEndPoint.BROADCAST, data, sentIntent, deliveryIntent);
            } catch (RemoteException e) {
                Log.e(TAG, "sendDataMessageWithSelfPermissions: Couldn't send SMS - Exception: " + e.getMessage());
                notifySmsGenericError(sentIntent);
            }
        }
    }

    public static SmsManager getDefault() {
        return sInstance;
    }

    public static SmsManager getSmsManagerForSubscriptionId(int subId) {
        SmsManager smsManager;
        synchronized (sLockObject) {
            smsManager = sSubInstances.get(Integer.valueOf(subId));
            if (smsManager == null) {
                smsManager = new SmsManager(subId);
                sSubInstances.put(Integer.valueOf(subId), smsManager);
            }
        }
        return smsManager;
    }

    private SmsManager(int subId) {
        this.mSubId = subId;
    }

    public int getSubscriptionId() {
        try {
            return this.mSubId == Integer.MAX_VALUE ? getISmsServiceOrThrow().getPreferredSmsSubscription() : this.mSubId;
        } catch (RemoteException e) {
            return -1;
        }
    }

    private void resolveSubscriptionForOperation(final SubscriptionResolverResult resolverResult) {
        int subId = getSubscriptionId();
        boolean isSmsSimPickActivityNeeded = false;
        Context context = ActivityThread.currentApplication().getApplicationContext();
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                isSmsSimPickActivityNeeded = iSms.isSmsSimPickActivityNeeded(subId);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "resolveSubscriptionForOperation", ex);
        }
        if (!isSmsSimPickActivityNeeded) {
            sendResolverResult(resolverResult, subId, false);
            return;
        }
        Log.d(TAG, "resolveSubscriptionForOperation isSmsSimPickActivityNeeded is true for package " + context.getPackageName());
        try {
            getITelephony().enqueueSmsPickResult(context.getOpPackageName(), new IIntegerConsumer.Stub() {
                public void accept(int subId) {
                    SmsManager.this.sendResolverResult(resolverResult, subId, true);
                }
            });
        } catch (RemoteException ex2) {
            Log.e(TAG, "Unable to launch activity", ex2);
            sendResolverResult(resolverResult, subId, true);
        }
    }

    /* access modifiers changed from: private */
    public void sendResolverResult(SubscriptionResolverResult resolverResult, int subId, boolean pickActivityShown) {
        if (SubscriptionManager.isValidSubscriptionId(subId)) {
            resolverResult.onSuccess(subId);
        } else if (getTargetSdkVersion() > 28 || pickActivityShown) {
            resolverResult.onFailure();
        } else {
            resolverResult.onSuccess(subId);
        }
    }

    private static int getTargetSdkVersion() {
        Context context = ActivityThread.currentApplication().getApplicationContext();
        try {
            return context.getPackageManager().getApplicationInfo(context.getOpPackageName(), 0).targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    private static ITelephony getITelephony() {
        ITelephony binder = ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
        if (binder != null) {
            return binder;
        }
        throw new RuntimeException("Could not find Telephony Service.");
    }

    /* access modifiers changed from: private */
    public static void notifySmsErrorNoDefaultSet(Context context, PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            Intent errorMessage = new Intent();
            errorMessage.putExtra(NO_DEFAULT_EXTRA, true);
            try {
                pendingIntent.send(context, 1, errorMessage);
            } catch (PendingIntent.CanceledException e) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static void notifySmsErrorNoDefaultSet(Context context, List<PendingIntent> pendingIntents) {
        if (pendingIntents != null) {
            for (PendingIntent pendingIntent : pendingIntents) {
                Intent errorMessage = new Intent();
                errorMessage.putExtra(NO_DEFAULT_EXTRA, true);
                try {
                    pendingIntent.send(context, 1, errorMessage);
                } catch (PendingIntent.CanceledException e) {
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void notifySmsGenericError(PendingIntent pendingIntent) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(1);
            } catch (PendingIntent.CanceledException e) {
            }
        }
    }

    /* access modifiers changed from: private */
    public static void notifySmsGenericError(List<PendingIntent> pendingIntents) {
        if (pendingIntents != null) {
            for (PendingIntent pendingIntent : pendingIntents) {
                try {
                    pendingIntent.send(1);
                } catch (PendingIntent.CanceledException e) {
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static ISms getISmsServiceOrThrow() {
        ISms iSms = getISmsService();
        if (iSms != null) {
            return iSms;
        }
        throw new UnsupportedOperationException("Sms is not supported");
    }

    private static ISms getISmsService() {
        return ISms.Stub.asInterface(ServiceManager.getService("isms"));
    }

    @UnsupportedAppUsage
    public boolean copyMessageToIcc(byte[] smsc, byte[] pdu, int status) {
        SeempLog.record(79);
        if (pdu != null) {
            try {
                ISms iSms = getISmsService();
                if (iSms == null) {
                    return false;
                }
                return iSms.copyMessageToIccEfForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), status, pdu, smsc);
            } catch (RemoteException e) {
                return false;
            }
        } else {
            throw new IllegalArgumentException("pdu is NULL");
        }
    }

    @UnsupportedAppUsage
    public boolean deleteMessageFromIcc(int messageIndex) {
        SeempLog.record(80);
        byte[] pdu = new byte[175];
        Arrays.fill(pdu, (byte) -1);
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            return iSms.updateMessageOnIccEfForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), messageIndex, 0, pdu);
        } catch (RemoteException e) {
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean updateMessageOnIcc(int messageIndex, int newStatus, byte[] pdu) {
        SeempLog.record(81);
        try {
            ISms iSms = getISmsService();
            if (iSms == null) {
                return false;
            }
            return iSms.updateMessageOnIccEfForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName(), messageIndex, newStatus, pdu);
        } catch (RemoteException e) {
            return false;
        }
    }

    @UnsupportedAppUsage
    public ArrayList<SmsMessage> getAllMessagesFromIcc() {
        List<SmsRawData> records = null;
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                records = iSms.getAllMessagesFromIccEfForSubscriber(getSubscriptionId(), ActivityThread.currentPackageName());
            }
        } catch (RemoteException e) {
        }
        return createMessageListFromRawRecords(records);
    }

    public boolean enableCellBroadcast(int messageIdentifier, int ranType) {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                return iSms.enableCellBroadcastForSubscriber(getSubscriptionId(), messageIdentifier, ranType);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean disableCellBroadcast(int messageIdentifier, int ranType) {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                return iSms.disableCellBroadcastForSubscriber(getSubscriptionId(), messageIdentifier, ranType);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean enableCellBroadcastRange(int startMessageId, int endMessageId, int ranType) {
        if (endMessageId >= startMessageId) {
            try {
                ISms iSms = getISmsService();
                if (iSms != null) {
                    return iSms.enableCellBroadcastRangeForSubscriber(getSubscriptionId(), startMessageId, endMessageId, ranType);
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        } else {
            throw new IllegalArgumentException("endMessageId < startMessageId");
        }
    }

    @UnsupportedAppUsage
    public boolean disableCellBroadcastRange(int startMessageId, int endMessageId, int ranType) {
        if (endMessageId >= startMessageId) {
            try {
                ISms iSms = getISmsService();
                if (iSms != null) {
                    return iSms.disableCellBroadcastRangeForSubscriber(getSubscriptionId(), startMessageId, endMessageId, ranType);
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        } else {
            throw new IllegalArgumentException("endMessageId < startMessageId");
        }
    }

    private ArrayList<SmsMessage> createMessageListFromRawRecords(List<SmsRawData> records) {
        SmsMessage sms;
        ArrayList<SmsMessage> messages = new ArrayList<>();
        if (records != null) {
            int count = records.size();
            for (int i = 0; i < count; i++) {
                SmsRawData data = records.get(i);
                if (!(data == null || (sms = SmsMessage.createFromEfRecord(i + 1, data.getBytes(), getSubscriptionId())) == null)) {
                    messages.add(sms);
                }
            }
        }
        return messages;
    }

    public boolean isImsSmsSupported() {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                return iSms.isImsSmsSupportedForSubscriber(getSubscriptionId());
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public String getImsSmsFormat() {
        try {
            ISms iSms = getISmsService();
            if (iSms != null) {
                return iSms.getImsSmsFormatForSubscriber(getSubscriptionId());
            }
            return "unknown";
        } catch (RemoteException e) {
            return "unknown";
        }
    }

    public static int getDefaultSmsSubscriptionId() {
        try {
            return getISmsService().getPreferredSmsSubscription();
        } catch (RemoteException e) {
            return -1;
        } catch (NullPointerException e2) {
            return -1;
        }
    }

    @UnsupportedAppUsage
    public boolean isSMSPromptEnabled() {
        try {
            return ISms.Stub.asInterface(ServiceManager.getService("isms")).isSMSPromptEnabled();
        } catch (RemoteException e) {
            return false;
        } catch (NullPointerException e2) {
            return false;
        }
    }

    public int getSmsCapacityOnIcc() {
        try {
            ISms iccISms = getISmsService();
            if (iccISms != null) {
                return iccISms.getSmsCapacityOnIccForSubscriber(getSubscriptionId());
            }
            return -1;
        } catch (RemoteException e) {
            return -1;
        }
    }

    public void sendMultimediaMessage(Context context, Uri contentUri, String locationUrl, Bundle configOverrides, PendingIntent sentIntent) {
        if (contentUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms != null) {
                    iMms.sendMessage(getSubscriptionId(), ActivityThread.currentPackageName(), contentUri, locationUrl, configOverrides, sentIntent);
                }
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalArgumentException("Uri contentUri null");
        }
    }

    public void downloadMultimediaMessage(Context context, String locationUrl, Uri contentUri, Bundle configOverrides, PendingIntent downloadedIntent) {
        if (TextUtils.isEmpty(locationUrl)) {
            throw new IllegalArgumentException("Empty MMS location URL");
        } else if (contentUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms != null) {
                    iMms.downloadMessage(getSubscriptionId(), ActivityThread.currentPackageName(), locationUrl, contentUri, configOverrides, downloadedIntent);
                }
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalArgumentException("Uri contentUri null");
        }
    }

    public Uri importTextMessage(String address, int type, String text, long timestampMillis, boolean seen, boolean read) {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms == null) {
                return null;
            }
            return iMms.importTextMessage(ActivityThread.currentPackageName(), address, type, text, timestampMillis, seen, read);
        } catch (RemoteException e) {
            return null;
        }
    }

    public Uri importMultimediaMessage(Uri contentUri, String messageId, long timestampSecs, boolean seen, boolean read) {
        if (contentUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms == null) {
                    return null;
                }
                return iMms.importMultimediaMessage(ActivityThread.currentPackageName(), contentUri, messageId, timestampSecs, seen, read);
            } catch (RemoteException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Uri contentUri null");
        }
    }

    public boolean deleteStoredMessage(Uri messageUri) {
        if (messageUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms != null) {
                    return iMms.deleteStoredMessage(ActivityThread.currentPackageName(), messageUri);
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        } else {
            throw new IllegalArgumentException("Empty message URI");
        }
    }

    public boolean deleteStoredConversation(long conversationId) {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms != null) {
                return iMms.deleteStoredConversation(ActivityThread.currentPackageName(), conversationId);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean updateStoredMessageStatus(Uri messageUri, ContentValues statusValues) {
        if (messageUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms != null) {
                    return iMms.updateStoredMessageStatus(ActivityThread.currentPackageName(), messageUri, statusValues);
                }
                return false;
            } catch (RemoteException e) {
                return false;
            }
        } else {
            throw new IllegalArgumentException("Empty message URI");
        }
    }

    public boolean archiveStoredConversation(long conversationId, boolean archived) {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms != null) {
                return iMms.archiveStoredConversation(ActivityThread.currentPackageName(), conversationId, archived);
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public Uri addTextMessageDraft(String address, String text) {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms != null) {
                return iMms.addTextMessageDraft(ActivityThread.currentPackageName(), address, text);
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public Uri addMultimediaMessageDraft(Uri contentUri) {
        if (contentUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms != null) {
                    return iMms.addMultimediaMessageDraft(ActivityThread.currentPackageName(), contentUri);
                }
                return null;
            } catch (RemoteException e) {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Uri contentUri null");
        }
    }

    public void sendStoredTextMessage(Uri messageUri, String scAddress, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        if (messageUri != null) {
            final Uri uri = messageUri;
            final String str = scAddress;
            final PendingIntent pendingIntent = sentIntent;
            final PendingIntent pendingIntent2 = deliveryIntent;
            final Context applicationContext = ActivityThread.currentApplication().getApplicationContext();
            resolveSubscriptionForOperation(new SubscriptionResolverResult() {
                public void onSuccess(int subId) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendStoredText(subId, ActivityThread.currentPackageName(), uri, str, pendingIntent, pendingIntent2);
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendStoredTextMessage: Couldn't send SMS - Exception: " + e.getMessage());
                        SmsManager.notifySmsGenericError(pendingIntent);
                    }
                }

                public void onFailure() {
                    SmsManager.notifySmsErrorNoDefaultSet(applicationContext, pendingIntent);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public void sendStoredMultipartTextMessage(Uri messageUri, String scAddress, ArrayList<PendingIntent> sentIntents, ArrayList<PendingIntent> deliveryIntents) {
        if (messageUri != null) {
            final Uri uri = messageUri;
            final String str = scAddress;
            final ArrayList<PendingIntent> arrayList = sentIntents;
            final ArrayList<PendingIntent> arrayList2 = deliveryIntents;
            final Context applicationContext = ActivityThread.currentApplication().getApplicationContext();
            resolveSubscriptionForOperation(new SubscriptionResolverResult() {
                public void onSuccess(int subId) {
                    try {
                        SmsManager.getISmsServiceOrThrow().sendStoredMultipartText(subId, ActivityThread.currentPackageName(), uri, str, arrayList, arrayList2);
                    } catch (RemoteException e) {
                        Log.e(SmsManager.TAG, "sendStoredTextMessage: Couldn't send SMS - Exception: " + e.getMessage());
                        SmsManager.notifySmsGenericError((List<PendingIntent>) arrayList);
                    }
                }

                public void onFailure() {
                    SmsManager.notifySmsErrorNoDefaultSet(applicationContext, (List<PendingIntent>) arrayList);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Empty message URI");
    }

    public void sendStoredMultimediaMessage(Uri messageUri, Bundle configOverrides, PendingIntent sentIntent) {
        if (messageUri != null) {
            try {
                IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
                if (iMms != null) {
                    iMms.sendStoredMessage(getSubscriptionId(), ActivityThread.currentPackageName(), messageUri, configOverrides, sentIntent);
                }
            } catch (RemoteException e) {
            }
        } else {
            throw new IllegalArgumentException("Empty message URI");
        }
    }

    public void setAutoPersisting(boolean enabled) {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms != null) {
                iMms.setAutoPersisting(ActivityThread.currentPackageName(), enabled);
            }
        } catch (RemoteException e) {
        }
    }

    public boolean getAutoPersisting() {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms != null) {
                return iMms.getAutoPersisting();
            }
            return false;
        } catch (RemoteException e) {
            return false;
        }
    }

    public Bundle getCarrierConfigValues() {
        try {
            IMms iMms = IMms.Stub.asInterface(ServiceManager.getService("imms"));
            if (iMms != null) {
                return iMms.getCarrierConfigValues(getSubscriptionId());
            }
            return null;
        } catch (RemoteException e) {
            return null;
        }
    }

    public String createAppSpecificSmsToken(PendingIntent intent) {
        try {
            return getISmsServiceOrThrow().createAppSpecificSmsToken(getSubscriptionId(), ActivityThread.currentPackageName(), intent);
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
            return null;
        }
    }

    public void getSmsMessagesForFinancialApp(Bundle params, final Executor executor, final FinancialSmsCallback callback) {
        try {
            getISmsServiceOrThrow().getSmsMessagesForFinancialApp(getSubscriptionId(), ActivityThread.currentPackageName(), params, new IFinancialSmsCallback.Stub() {
                public void onGetSmsMessagesForFinancialApp(CursorWindow msgs) {
                    Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingRunnable) new FunctionalUtils.ThrowingRunnable(executor, callback, msgs) {
                        private final /* synthetic */ Executor f$0;
                        private final /* synthetic */ SmsManager.FinancialSmsCallback f$1;
                        private final /* synthetic */ CursorWindow f$2;

                        {
                            this.f$0 = r1;
                            this.f$1 = r2;
                            this.f$2 = r3;
                        }

                        public final void runOrThrow() {
                            this.f$0.execute(new Runnable(this.f$2) {
                                private final /* synthetic */ CursorWindow f$1;

                                {
                                    this.f$1 = r2;
                                }

                                public final void run() {
                                    SmsManager.FinancialSmsCallback.this.onFinancialSmsMessages(this.f$1);
                                }
                            });
                        }
                    });
                }
            });
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
        }
    }

    public String createAppSpecificSmsTokenWithPackageInfo(String prefixes, PendingIntent intent) {
        try {
            return getISmsServiceOrThrow().createAppSpecificSmsTokenWithPackageInfo(getSubscriptionId(), ActivityThread.currentPackageName(), prefixes, intent);
        } catch (RemoteException ex) {
            ex.rethrowFromSystemServer();
            return null;
        }
    }

    public static Bundle getMmsConfig(BaseBundle config) {
        Bundle filtered = new Bundle();
        filtered.putBoolean("enabledTransID", config.getBoolean("enabledTransID"));
        filtered.putBoolean("enabledMMS", config.getBoolean("enabledMMS"));
        filtered.putBoolean("enableGroupMms", config.getBoolean("enableGroupMms"));
        filtered.putBoolean("enabledNotifyWapMMSC", config.getBoolean("enabledNotifyWapMMSC"));
        filtered.putBoolean("aliasEnabled", config.getBoolean("aliasEnabled"));
        filtered.putBoolean("allowAttachAudio", config.getBoolean("allowAttachAudio"));
        filtered.putBoolean("enableMultipartSMS", config.getBoolean("enableMultipartSMS"));
        filtered.putBoolean("enableSMSDeliveryReports", config.getBoolean("enableSMSDeliveryReports"));
        filtered.putBoolean("supportMmsContentDisposition", config.getBoolean("supportMmsContentDisposition"));
        filtered.putBoolean("sendMultipartSmsAsSeparateMessages", config.getBoolean("sendMultipartSmsAsSeparateMessages"));
        filtered.putBoolean("enableMMSReadReports", config.getBoolean("enableMMSReadReports"));
        filtered.putBoolean("enableMMSDeliveryReports", config.getBoolean("enableMMSDeliveryReports"));
        filtered.putBoolean("mmsCloseConnection", config.getBoolean("mmsCloseConnection"));
        filtered.putInt("maxMessageSize", config.getInt("maxMessageSize"));
        filtered.putInt("maxImageWidth", config.getInt("maxImageWidth"));
        filtered.putInt("maxImageHeight", config.getInt("maxImageHeight"));
        filtered.putInt("recipientLimit", config.getInt("recipientLimit"));
        filtered.putInt("aliasMinChars", config.getInt("aliasMinChars"));
        filtered.putInt("aliasMaxChars", config.getInt("aliasMaxChars"));
        filtered.putInt("smsToMmsTextThreshold", config.getInt("smsToMmsTextThreshold"));
        filtered.putInt("smsToMmsTextLengthThreshold", config.getInt("smsToMmsTextLengthThreshold"));
        filtered.putInt("maxMessageTextSize", config.getInt("maxMessageTextSize"));
        filtered.putInt("maxSubjectLength", config.getInt("maxSubjectLength"));
        filtered.putInt("httpSocketTimeout", config.getInt("httpSocketTimeout"));
        filtered.putString("uaProfTagName", config.getString("uaProfTagName"));
        filtered.putString("userAgent", config.getString("userAgent"));
        filtered.putString("uaProfUrl", config.getString("uaProfUrl"));
        filtered.putString("httpParams", config.getString("httpParams"));
        filtered.putString("emailGatewayNumber", config.getString("emailGatewayNumber"));
        filtered.putString("naiSuffix", config.getString("naiSuffix"));
        filtered.putBoolean("config_cellBroadcastAppLinks", config.getBoolean("config_cellBroadcastAppLinks"));
        filtered.putBoolean("supportHttpCharsetHeader", config.getBoolean("supportHttpCharsetHeader"));
        return filtered;
    }

    public int checkSmsShortCodeDestination(String destAddress, String countryIso) {
        try {
            ISms iccISms = getISmsServiceOrThrow();
            if (iccISms != null) {
                return iccISms.checkSmsShortCodeDestination(getSubscriptionId(), ActivityThread.currentPackageName(), destAddress, countryIso);
            }
            return 0;
        } catch (RemoteException e) {
            Log.e(TAG, "checkSmsShortCodeDestination() RemoteException", e);
            return 0;
        }
    }
}
