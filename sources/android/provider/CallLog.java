package android.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.location.Country;
import android.location.CountryDetector;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.CallerInfo;

public class CallLog {
    public static final String AUTHORITY = "call_log";
    public static final Uri CONTENT_URI = Uri.parse("content://call_log");
    private static final String LOG_TAG = "CallLog";
    public static final String SHADOW_AUTHORITY = "call_log_shadow";
    private static final boolean VERBOSE_LOG = false;

    public static class Calls implements BaseColumns {
        public static final String ADD_FOR_ALL_USERS = "add_for_all_users";
        public static final String ALLOW_VOICEMAILS_PARAM_KEY = "allow_voicemails";
        public static final int ANSWERED_EXTERNALLY_TYPE = 7;
        public static final int BLOCKED_TYPE = 6;
        public static final String BLOCK_REASON = "block_reason";
        public static final int BLOCK_REASON_BLOCKED_NUMBER = 3;
        public static final int BLOCK_REASON_CALL_SCREENING_SERVICE = 1;
        public static final int BLOCK_REASON_DIRECT_TO_VOICEMAIL = 2;
        public static final int BLOCK_REASON_NOT_BLOCKED = 0;
        public static final int BLOCK_REASON_NOT_IN_CONTACTS = 7;
        public static final int BLOCK_REASON_PAY_PHONE = 6;
        public static final int BLOCK_REASON_RESTRICTED_NUMBER = 5;
        public static final int BLOCK_REASON_UNKNOWN_NUMBER = 4;
        public static final String CACHED_FORMATTED_NUMBER = "formatted_number";
        public static final String CACHED_LOOKUP_URI = "lookup_uri";
        public static final String CACHED_MATCHED_NUMBER = "matched_number";
        public static final String CACHED_NAME = "name";
        public static final String CACHED_NORMALIZED_NUMBER = "normalized_number";
        public static final String CACHED_NUMBER_LABEL = "numberlabel";
        public static final String CACHED_NUMBER_TYPE = "numbertype";
        public static final String CACHED_PHOTO_ID = "photo_id";
        public static final String CACHED_PHOTO_URI = "photo_uri";
        public static final String CALL_SCREENING_APP_NAME = "call_screening_app_name";
        public static final String CALL_SCREENING_COMPONENT_NAME = "call_screening_component_name";
        public static final Uri CONTENT_FILTER_URI = Uri.parse("content://call_log/calls/filter");
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/calls";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/calls";
        public static final Uri CONTENT_URI = Uri.parse("content://call_log/calls");
        public static final Uri CONTENT_URI_WITH_VOICEMAIL = CONTENT_URI.buildUpon().appendQueryParameter(ALLOW_VOICEMAILS_PARAM_KEY, "true").build();
        public static final String COUNTRY_ISO = "countryiso";
        public static final String DATA_USAGE = "data_usage";
        public static final String DATE = "date";
        public static final String DEFAULT_SORT_ORDER = "date DESC";
        public static final String DURATION = "duration";
        public static final String EXTRA_CALL_TYPE_FILTER = "android.provider.extra.CALL_TYPE_FILTER";
        public static final String FEATURES = "features";
        public static final int FEATURES_ASSISTED_DIALING_USED = 16;
        public static final int FEATURES_HD_CALL = 4;
        public static final int FEATURES_PULLED_EXTERNALLY = 2;
        public static final int FEATURES_RTT = 32;
        public static final int FEATURES_VIDEO = 1;
        public static final int FEATURES_WIFI = 8;
        public static final String GEOCODED_LOCATION = "geocoded_location";
        public static final int INCOMING_TYPE = 1;
        public static final String IS_READ = "is_read";
        public static final String LAST_MODIFIED = "last_modified";
        public static final String LIMIT_PARAM_KEY = "limit";
        private static final int MIN_DURATION_FOR_NORMALIZED_NUMBER_UPDATE_MS = 10000;
        public static final int MISSED_TYPE = 3;
        public static final String NEW = "new";
        public static final String NUMBER = "number";
        public static final String NUMBER_PRESENTATION = "presentation";
        public static final String OFFSET_PARAM_KEY = "offset";
        public static final int OUTGOING_TYPE = 2;
        public static final String PHONE_ACCOUNT_ADDRESS = "phone_account_address";
        public static final String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
        public static final String PHONE_ACCOUNT_HIDDEN = "phone_account_hidden";
        public static final String PHONE_ACCOUNT_ID = "subscription_id";
        public static final String POST_DIAL_DIGITS = "post_dial_digits";
        public static final int PRESENTATION_ALLOWED = 1;
        public static final int PRESENTATION_PAYPHONE = 4;
        public static final int PRESENTATION_RESTRICTED = 2;
        public static final int PRESENTATION_UNKNOWN = 3;
        public static final int REJECTED_TYPE = 5;
        public static final Uri SHADOW_CONTENT_URI = Uri.parse("content://call_log_shadow/calls");
        public static final String SUB_ID = "sub_id";
        public static final String TRANSCRIPTION = "transcription";
        public static final String TRANSCRIPTION_STATE = "transcription_state";
        public static final String TYPE = "type";
        public static final String VIA_NUMBER = "via_number";
        public static final int VOICEMAIL_TYPE = 4;
        public static final String VOICEMAIL_URI = "voicemail_uri";

        public static Uri addCall(CallerInfo ci, Context context, String number, int presentation, int callType, int features, PhoneAccountHandle accountHandle, long start, int duration, Long dataUsage) {
            return addCall(ci, context, number, "", "", presentation, callType, features, accountHandle, start, duration, dataUsage, false, (UserHandle) null, false, 0, (CharSequence) null, (String) null);
        }

        public static Uri addCall(CallerInfo ci, Context context, String number, String postDialDigits, String viaNumber, int presentation, int callType, int features, PhoneAccountHandle accountHandle, long start, int duration, Long dataUsage, boolean addForAllUsers, UserHandle userToBeInsertedTo) {
            return addCall(ci, context, number, postDialDigits, viaNumber, presentation, callType, features, accountHandle, start, duration, dataUsage, addForAllUsers, userToBeInsertedTo, false, 0, (CharSequence) null, (String) null);
        }

        /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
            java.lang.NullPointerException
            */
        @android.annotation.UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
        public static android.net.Uri addCall(com.android.internal.telephony.CallerInfo r28, android.content.Context r29, java.lang.String r30, java.lang.String r31, java.lang.String r32, int r33, int r34, int r35, android.telecom.PhoneAccountHandle r36, long r37, int r39, java.lang.Long r40, boolean r41, android.os.UserHandle r42, boolean r43, int r44, java.lang.CharSequence r45, java.lang.String r46) {
            /*
                r1 = r28
                r2 = r29
                r3 = r34
                r4 = r36
                r5 = r39
                r6 = r40
                android.content.ContentResolver r13 = r29.getContentResolver()
                java.lang.String r14 = getLogAccountAddress(r2, r4)
                r0 = r30
                r15 = r33
                int r12 = getLogNumberPresentation(r0, r15)
                r11 = 1
                if (r12 == r11) goto L_0x0027
                java.lang.String r0 = ""
                if (r1 == 0) goto L_0x0027
                java.lang.String r7 = ""
                r1.name = r7
            L_0x0027:
                r10 = r0
                r0 = 0
                r7 = 0
                if (r4 == 0) goto L_0x0038
                android.content.ComponentName r8 = r36.getComponentName()
                java.lang.String r0 = r8.flattenToString()
                java.lang.String r7 = r36.getId()
            L_0x0038:
                r9 = r0
                r8 = r7
                android.content.ContentValues r0 = new android.content.ContentValues
                r7 = 6
                r0.<init>((int) r7)
                r7 = r0
                java.lang.String r0 = "number"
                r7.put((java.lang.String) r0, (java.lang.String) r10)
                java.lang.String r0 = "post_dial_digits"
                r11 = r31
                r7.put((java.lang.String) r0, (java.lang.String) r11)
                java.lang.String r0 = "via_number"
                r4 = r32
                r7.put((java.lang.String) r0, (java.lang.String) r4)
                java.lang.String r0 = "presentation"
                java.lang.Integer r4 = java.lang.Integer.valueOf(r12)
                r7.put((java.lang.String) r0, (java.lang.Integer) r4)
                java.lang.String r0 = "type"
                java.lang.Integer r4 = java.lang.Integer.valueOf(r34)
                r7.put((java.lang.String) r0, (java.lang.Integer) r4)
                java.lang.String r0 = "features"
                java.lang.Integer r4 = java.lang.Integer.valueOf(r35)
                r7.put((java.lang.String) r0, (java.lang.Integer) r4)
                java.lang.String r0 = "date"
                java.lang.Long r4 = java.lang.Long.valueOf(r37)
                r7.put((java.lang.String) r0, (java.lang.Long) r4)
                java.lang.String r0 = "duration"
                r17 = r10
                long r10 = (long) r5
                java.lang.Long r4 = java.lang.Long.valueOf(r10)
                r7.put((java.lang.String) r0, (java.lang.Long) r4)
                if (r6 == 0) goto L_0x0090
                java.lang.String r0 = "data_usage"
                r7.put((java.lang.String) r0, (java.lang.Long) r6)
            L_0x0090:
                java.lang.String r0 = "subscription_component_name"
                r7.put((java.lang.String) r0, (java.lang.String) r9)
                java.lang.String r0 = "subscription_id"
                r7.put((java.lang.String) r0, (java.lang.String) r8)
                java.lang.String r0 = "phone_account_address"
                r7.put((java.lang.String) r0, (java.lang.String) r14)
                java.lang.String r0 = "new"
                r4 = 1
                java.lang.Integer r10 = java.lang.Integer.valueOf(r4)
                r7.put((java.lang.String) r0, (java.lang.Integer) r10)
                if (r1 == 0) goto L_0x00bb
                java.lang.String r0 = r1.name
                if (r0 == 0) goto L_0x00bb
                java.lang.String r0 = "name"
                java.lang.String r4 = r1.name
                r7.put((java.lang.String) r0, (java.lang.String) r4)
            L_0x00bb:
                java.lang.String r0 = "add_for_all_users"
                java.lang.Integer r4 = java.lang.Integer.valueOf(r41)
                r7.put((java.lang.String) r0, (java.lang.Integer) r4)
                r0 = 3
                if (r3 != r0) goto L_0x00d0
                java.lang.String r0 = "is_read"
                java.lang.Integer r4 = java.lang.Integer.valueOf(r43)
                r7.put((java.lang.String) r0, (java.lang.Integer) r4)
            L_0x00d0:
                java.lang.String r0 = "block_reason"
                java.lang.Integer r4 = java.lang.Integer.valueOf(r44)
                r7.put((java.lang.String) r0, (java.lang.Integer) r4)
                java.lang.String r0 = "call_screening_app_name"
                java.lang.String r4 = charSequenceToString(r45)
                r7.put((java.lang.String) r0, (java.lang.String) r4)
                java.lang.String r0 = "call_screening_component_name"
                r4 = r46
                r7.put((java.lang.String) r0, (java.lang.String) r4)
                if (r1 == 0) goto L_0x01b6
                long r10 = r1.contactIdOrZero
                r18 = 0
                int r10 = (r10 > r18 ? 1 : (r10 == r18 ? 0 : -1))
                if (r10 <= 0) goto L_0x01b6
                java.lang.String r10 = r1.normalizedNumber
                r11 = 2
                if (r10 == 0) goto L_0x012f
                java.lang.String r10 = r1.normalizedNumber
                android.net.Uri r18 = android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                java.lang.String r19 = "_id"
                java.lang.String[] r19 = new java.lang.String[]{r19}
                java.lang.String r20 = "contact_id =? AND data4 =?"
                java.lang.String[] r0 = new java.lang.String[r11]
                r21 = r12
                long r11 = r1.contactIdOrZero
                java.lang.String r11 = java.lang.String.valueOf(r11)
                r12 = 0
                r0[r12] = r11
                r11 = 1
                r0[r11] = r10
                r12 = 0
                r22 = r7
                r7 = r13
                r16 = r8
                r8 = r18
                r18 = r9
                r9 = r19
                r23 = r17
                r17 = r10
                r10 = r20
                r4 = r11
                r11 = r0
                r19 = r21
                android.database.Cursor r0 = r7.query(r8, r9, r10, r11, r12)
                goto L_0x0167
            L_0x012f:
                r22 = r7
                r16 = r8
                r18 = r9
                r19 = r12
                r23 = r17
                r4 = 1
                java.lang.String r0 = r1.phoneNumber
                if (r0 == 0) goto L_0x0141
                java.lang.String r10 = r1.phoneNumber
                goto L_0x0143
            L_0x0141:
                r10 = r23
            L_0x0143:
                r0 = r10
                android.net.Uri r7 = android.provider.ContactsContract.CommonDataKinds.Callable.CONTENT_FILTER_URI
                java.lang.String r8 = android.net.Uri.encode(r0)
                android.net.Uri r8 = android.net.Uri.withAppendedPath(r7, r8)
                java.lang.String r7 = "_id"
                java.lang.String[] r9 = new java.lang.String[]{r7}
                java.lang.String r10 = "contact_id =?"
                java.lang.String[] r11 = new java.lang.String[r4]
                long r4 = r1.contactIdOrZero
                java.lang.String r4 = java.lang.String.valueOf(r4)
                r5 = 0
                r11[r5] = r4
                r12 = 0
                r7 = r13
                android.database.Cursor r0 = r7.query(r8, r9, r10, r11, r12)
            L_0x0167:
                r4 = r0
                if (r4 == 0) goto L_0x01b0
                int r0 = r4.getCount()     // Catch:{ all -> 0x01a7 }
                if (r0 <= 0) goto L_0x019e
                boolean r0 = r4.moveToFirst()     // Catch:{ all -> 0x01a7 }
                if (r0 == 0) goto L_0x019e
                r12 = 0
                java.lang.String r0 = r4.getString(r12)     // Catch:{ all -> 0x01a7 }
                updateDataUsageStatForData(r13, r0)     // Catch:{ all -> 0x01a7 }
                r5 = 10000(0x2710, float:1.4013E-41)
                r7 = r39
                if (r7 < r5) goto L_0x019b
                r5 = 2
                if (r3 != r5) goto L_0x019b
                java.lang.String r5 = r1.normalizedNumber     // Catch:{ all -> 0x0197 }
                boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0197 }
                if (r5 == 0) goto L_0x019b
                r5 = r23
                updateNormalizedNumber(r2, r13, r0, r5)     // Catch:{ all -> 0x0195 }
                goto L_0x01a3
            L_0x0195:
                r0 = move-exception
                goto L_0x01ac
            L_0x0197:
                r0 = move-exception
                r5 = r23
                goto L_0x01ac
            L_0x019b:
                r5 = r23
                goto L_0x01a3
            L_0x019e:
                r5 = r23
                r7 = r39
                r12 = 0
            L_0x01a3:
                r4.close()
                goto L_0x01c2
            L_0x01a7:
                r0 = move-exception
                r5 = r23
                r7 = r39
            L_0x01ac:
                r4.close()
                throw r0
            L_0x01b0:
                r5 = r23
                r7 = r39
                r12 = 0
                goto L_0x01c2
            L_0x01b6:
                r22 = r7
                r16 = r8
                r18 = r9
                r19 = r12
                r12 = 0
                r7 = r5
                r5 = r17
            L_0x01c2:
                r0 = 0
                java.lang.Class<android.os.UserManager> r4 = android.os.UserManager.class
                java.lang.Object r4 = r2.getSystemService(r4)
                android.os.UserManager r4 = (android.os.UserManager) r4
                int r8 = r4.getUserHandle()
                if (r41 == 0) goto L_0x0242
                android.os.UserHandle r10 = android.os.UserHandle.SYSTEM
                r11 = r22
                android.net.Uri r10 = addEntryAndRemoveExpiredEntries(r2, r4, r10, r11)
                if (r10 == 0) goto L_0x023e
                java.lang.String r12 = "call_log_shadow"
                r25 = r0
                java.lang.String r0 = r10.getAuthority()
                boolean r0 = r12.equals(r0)
                if (r0 == 0) goto L_0x01ea
                goto L_0x0240
            L_0x01ea:
                if (r8 != 0) goto L_0x01ef
                r0 = r10
                r25 = r0
            L_0x01ef:
                r0 = 1
                java.util.List r0 = r4.getUsers(r0)
                int r12 = r0.size()
                r24 = 0
            L_0x01fa:
                r26 = r24
                r1 = r26
                if (r1 >= r12) goto L_0x023d
                java.lang.Object r17 = r0.get(r1)
                android.content.pm.UserInfo r17 = (android.content.pm.UserInfo) r17
                r27 = r0
                android.os.UserHandle r0 = r17.getUserHandle()
                int r3 = r0.getIdentifier()
                boolean r20 = r0.isSystem()
                if (r20 == 0) goto L_0x0217
                goto L_0x0234
            L_0x0217:
                boolean r20 = shouldHaveSharedCallLogEntries(r2, r4, r3)
                if (r20 != 0) goto L_0x021e
                goto L_0x0234
            L_0x021e:
                boolean r20 = r4.isUserRunning((android.os.UserHandle) r0)
                if (r20 == 0) goto L_0x0234
                boolean r20 = r4.isUserUnlocked((android.os.UserHandle) r0)
                if (r20 == 0) goto L_0x0234
                android.net.Uri r20 = addEntryAndRemoveExpiredEntries(r2, r4, r0, r11)
                if (r3 != r8) goto L_0x0234
                r0 = r20
                r25 = r0
            L_0x0234:
                int r24 = r1 + 1
                r0 = r27
                r1 = r28
                r3 = r34
                goto L_0x01fa
            L_0x023d:
                goto L_0x0254
            L_0x023e:
                r25 = r0
            L_0x0240:
                r0 = 0
                return r0
            L_0x0242:
                r25 = r0
                r11 = r22
                if (r42 == 0) goto L_0x024c
                r0 = r42
                goto L_0x0250
            L_0x024c:
                android.os.UserHandle r0 = android.os.UserHandle.of(r8)
            L_0x0250:
                android.net.Uri r25 = addEntryAndRemoveExpiredEntries(r2, r4, r0, r11)
            L_0x0254:
                return r25
            */
            throw new UnsupportedOperationException("Method not decompiled: android.provider.CallLog.Calls.addCall(com.android.internal.telephony.CallerInfo, android.content.Context, java.lang.String, java.lang.String, java.lang.String, int, int, int, android.telecom.PhoneAccountHandle, long, int, java.lang.Long, boolean, android.os.UserHandle, boolean, int, java.lang.CharSequence, java.lang.String):android.net.Uri");
        }

        private static String charSequenceToString(CharSequence sequence) {
            if (sequence == null) {
                return null;
            }
            return sequence.toString();
        }

        public static boolean shouldHaveSharedCallLogEntries(Context context, UserManager userManager, int userId) {
            UserInfo userInfo;
            if (!userManager.hasUserRestriction(UserManager.DISALLOW_OUTGOING_CALLS, UserHandle.of(userId)) && (userInfo = userManager.getUserInfo(userId)) != null && !userInfo.isManagedProfile()) {
                return true;
            }
            return false;
        }

        public static String getLastOutgoingCall(Context context) {
            Cursor c = null;
            try {
                c = context.getContentResolver().query(CONTENT_URI, new String[]{"number"}, "type = 2", (String[]) null, "date DESC LIMIT 1");
                if (c != null) {
                    if (c.moveToFirst()) {
                        String string = c.getString(0);
                        if (c != null) {
                            c.close();
                        }
                        return string;
                    }
                }
                return "";
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }

        private static Uri addEntryAndRemoveExpiredEntries(Context context, UserManager userManager, UserHandle user, ContentValues values) {
            ContentResolver resolver = context.getContentResolver();
            Uri uri = ContentProvider.maybeAddUserId(userManager.isUserUnlocked(user) ? CONTENT_URI : SHADOW_CONTENT_URI, user.getIdentifier());
            try {
                Uri result = resolver.insert(uri, values);
                if (!values.containsKey("subscription_id") || TextUtils.isEmpty(values.getAsString("subscription_id")) || !values.containsKey("subscription_component_name") || TextUtils.isEmpty(values.getAsString("subscription_component_name"))) {
                    resolver.delete(uri, "_id IN (SELECT _id FROM calls ORDER BY date DESC LIMIT -1 OFFSET 500)", (String[]) null);
                } else {
                    resolver.delete(uri, "_id IN (SELECT _id FROM calls WHERE subscription_component_name = ? AND subscription_id = ? ORDER BY date DESC LIMIT -1 OFFSET 500)", new String[]{values.getAsString("subscription_component_name"), values.getAsString("subscription_id")});
                }
                return result;
            } catch (IllegalArgumentException e) {
                Log.w(CallLog.LOG_TAG, "Failed to insert calllog", e);
                return null;
            }
        }

        private static void updateDataUsageStatForData(ContentResolver resolver, String dataId) {
            resolver.update(ContactsContract.DataUsageFeedback.FEEDBACK_URI.buildUpon().appendPath(dataId).appendQueryParameter("type", "call").build(), new ContentValues(), (String) null, (String[]) null);
        }

        private static void updateNormalizedNumber(Context context, ContentResolver resolver, String dataId, String number) {
            if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(dataId)) {
                String countryIso = getCurrentCountryIso(context);
                if (!TextUtils.isEmpty(countryIso)) {
                    String normalizedNumber = PhoneNumberUtils.formatNumberToE164(number, countryIso);
                    if (!TextUtils.isEmpty(normalizedNumber)) {
                        ContentValues values = new ContentValues();
                        values.put("data4", normalizedNumber);
                        resolver.update(ContactsContract.Data.CONTENT_URI, values, "_id=?", new String[]{dataId});
                    }
                }
            }
        }

        private static int getLogNumberPresentation(String number, int presentation) {
            if (presentation == 2 || presentation == 4) {
                return presentation;
            }
            if (TextUtils.isEmpty(number) || presentation == 3) {
                return 3;
            }
            return 1;
        }

        private static String getLogAccountAddress(Context context, PhoneAccountHandle accountHandle) {
            PhoneAccount account;
            Uri address;
            TelecomManager tm = null;
            try {
                tm = TelecomManager.from(context);
            } catch (UnsupportedOperationException e) {
            }
            if (tm == null || accountHandle == null || (account = tm.getPhoneAccount(accountHandle)) == null || (address = account.getSubscriptionAddress()) == null) {
                return null;
            }
            return address.getSchemeSpecificPart();
        }

        private static String getCurrentCountryIso(Context context) {
            Country country;
            CountryDetector detector = (CountryDetector) context.getSystemService(Context.COUNTRY_DETECTOR);
            if (detector == null || (country = detector.detectCountry()) == null) {
                return null;
            }
            return country.getCountryIso();
        }
    }
}
