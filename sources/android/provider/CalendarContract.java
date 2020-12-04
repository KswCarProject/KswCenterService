package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.app.AlarmManager;
import android.app.admin.DevicePolicyManager;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorEntityIterator;
import android.content.Entity;
import android.content.EntityIterator;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.SyncStateContract;
import android.util.SeempLog;
import com.android.internal.util.Preconditions;

public final class CalendarContract {
    public static final String ACCOUNT_TYPE_LOCAL = "LOCAL";
    public static final String ACTION_EVENT_REMINDER = "android.intent.action.EVENT_REMINDER";
    public static final String ACTION_HANDLE_CUSTOM_EVENT = "android.provider.calendar.action.HANDLE_CUSTOM_EVENT";
    public static final String ACTION_VIEW_MANAGED_PROFILE_CALENDAR_EVENT = "android.provider.calendar.action.VIEW_MANAGED_PROFILE_CALENDAR_EVENT";
    public static final String AUTHORITY = "com.android.calendar";
    public static final String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar");
    public static final Uri ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise");
    public static final String EXTRA_CUSTOM_APP_URI = "customAppUri";
    public static final String EXTRA_EVENT_ALL_DAY = "allDay";
    public static final String EXTRA_EVENT_BEGIN_TIME = "beginTime";
    public static final String EXTRA_EVENT_END_TIME = "endTime";
    public static final String EXTRA_EVENT_ID = "id";
    private static final String TAG = "Calendar";

    protected interface AttendeesColumns {
        public static final String ATTENDEE_EMAIL = "attendeeEmail";
        public static final String ATTENDEE_IDENTITY = "attendeeIdentity";
        public static final String ATTENDEE_ID_NAMESPACE = "attendeeIdNamespace";
        public static final String ATTENDEE_NAME = "attendeeName";
        public static final String ATTENDEE_RELATIONSHIP = "attendeeRelationship";
        public static final String ATTENDEE_STATUS = "attendeeStatus";
        public static final int ATTENDEE_STATUS_ACCEPTED = 1;
        public static final int ATTENDEE_STATUS_DECLINED = 2;
        public static final int ATTENDEE_STATUS_INVITED = 3;
        public static final int ATTENDEE_STATUS_NONE = 0;
        public static final int ATTENDEE_STATUS_TENTATIVE = 4;
        public static final String ATTENDEE_TYPE = "attendeeType";
        public static final String EVENT_ID = "event_id";
        public static final int RELATIONSHIP_ATTENDEE = 1;
        public static final int RELATIONSHIP_NONE = 0;
        public static final int RELATIONSHIP_ORGANIZER = 2;
        public static final int RELATIONSHIP_PERFORMER = 3;
        public static final int RELATIONSHIP_SPEAKER = 4;
        public static final int TYPE_NONE = 0;
        public static final int TYPE_OPTIONAL = 2;
        public static final int TYPE_REQUIRED = 1;
        public static final int TYPE_RESOURCE = 3;
    }

    protected interface CalendarAlertsColumns {
        public static final String ALARM_TIME = "alarmTime";
        public static final String BEGIN = "begin";
        public static final String CREATION_TIME = "creationTime";
        public static final String DEFAULT_SORT_ORDER = "begin ASC,title ASC";
        public static final String END = "end";
        public static final String EVENT_ID = "event_id";
        public static final String MINUTES = "minutes";
        public static final String NOTIFY_TIME = "notifyTime";
        public static final String RECEIVED_TIME = "receivedTime";
        public static final String STATE = "state";
        public static final int STATE_DISMISSED = 2;
        public static final int STATE_FIRED = 1;
        public static final int STATE_SCHEDULED = 0;
    }

    protected interface CalendarCacheColumns {
        public static final String KEY = "key";
        public static final String VALUE = "value";
    }

    protected interface CalendarColumns {
        public static final String ALLOWED_ATTENDEE_TYPES = "allowedAttendeeTypes";
        public static final String ALLOWED_AVAILABILITY = "allowedAvailability";
        public static final String ALLOWED_REMINDERS = "allowedReminders";
        public static final String CALENDAR_ACCESS_LEVEL = "calendar_access_level";
        public static final String CALENDAR_COLOR = "calendar_color";
        public static final String CALENDAR_COLOR_KEY = "calendar_color_index";
        public static final String CALENDAR_DISPLAY_NAME = "calendar_displayName";
        public static final String CALENDAR_TIME_ZONE = "calendar_timezone";
        public static final int CAL_ACCESS_CONTRIBUTOR = 500;
        public static final int CAL_ACCESS_EDITOR = 600;
        public static final int CAL_ACCESS_FREEBUSY = 100;
        public static final int CAL_ACCESS_NONE = 0;
        public static final int CAL_ACCESS_OVERRIDE = 400;
        public static final int CAL_ACCESS_OWNER = 700;
        public static final int CAL_ACCESS_READ = 200;
        public static final int CAL_ACCESS_RESPOND = 300;
        public static final int CAL_ACCESS_ROOT = 800;
        public static final String CAN_MODIFY_TIME_ZONE = "canModifyTimeZone";
        public static final String CAN_ORGANIZER_RESPOND = "canOrganizerRespond";
        public static final String IS_PRIMARY = "isPrimary";
        public static final String MAX_REMINDERS = "maxReminders";
        public static final String OWNER_ACCOUNT = "ownerAccount";
        public static final String SYNC_EVENTS = "sync_events";
        public static final String VISIBLE = "visible";
    }

    protected interface CalendarMetaDataColumns {
        public static final String LOCAL_TIMEZONE = "localTimezone";
        public static final String MAX_EVENTDAYS = "maxEventDays";
        public static final String MAX_INSTANCE = "maxInstance";
        public static final String MIN_EVENTDAYS = "minEventDays";
        public static final String MIN_INSTANCE = "minInstance";
    }

    protected interface CalendarSyncColumns {
        public static final String CAL_SYNC1 = "cal_sync1";
        public static final String CAL_SYNC10 = "cal_sync10";
        public static final String CAL_SYNC2 = "cal_sync2";
        public static final String CAL_SYNC3 = "cal_sync3";
        public static final String CAL_SYNC4 = "cal_sync4";
        public static final String CAL_SYNC5 = "cal_sync5";
        public static final String CAL_SYNC6 = "cal_sync6";
        public static final String CAL_SYNC7 = "cal_sync7";
        public static final String CAL_SYNC8 = "cal_sync8";
        public static final String CAL_SYNC9 = "cal_sync9";
    }

    protected interface ColorsColumns extends SyncStateContract.Columns {
        public static final String COLOR = "color";
        public static final String COLOR_KEY = "color_index";
        public static final String COLOR_TYPE = "color_type";
        public static final int TYPE_CALENDAR = 0;
        public static final int TYPE_EVENT = 1;
    }

    protected interface EventDaysColumns {
        public static final String ENDDAY = "endDay";
        public static final String STARTDAY = "startDay";
    }

    protected interface EventsColumns {
        public static final int ACCESS_CONFIDENTIAL = 1;
        public static final int ACCESS_DEFAULT = 0;
        public static final String ACCESS_LEVEL = "accessLevel";
        public static final int ACCESS_PRIVATE = 2;
        public static final int ACCESS_PUBLIC = 3;
        public static final String ALL_DAY = "allDay";
        public static final String AVAILABILITY = "availability";
        public static final int AVAILABILITY_BUSY = 0;
        public static final int AVAILABILITY_FREE = 1;
        public static final int AVAILABILITY_TENTATIVE = 2;
        public static final String CALENDAR_ID = "calendar_id";
        public static final String CAN_INVITE_OTHERS = "canInviteOthers";
        public static final String CUSTOM_APP_PACKAGE = "customAppPackage";
        public static final String CUSTOM_APP_URI = "customAppUri";
        public static final String DESCRIPTION = "description";
        public static final String DISPLAY_COLOR = "displayColor";
        public static final String DTEND = "dtend";
        public static final String DTSTART = "dtstart";
        public static final String DURATION = "duration";
        public static final String EVENT_COLOR = "eventColor";
        public static final String EVENT_COLOR_KEY = "eventColor_index";
        public static final String EVENT_END_TIMEZONE = "eventEndTimezone";
        public static final String EVENT_LOCATION = "eventLocation";
        public static final String EVENT_TIMEZONE = "eventTimezone";
        public static final String EXDATE = "exdate";
        public static final String EXRULE = "exrule";
        public static final String GUESTS_CAN_INVITE_OTHERS = "guestsCanInviteOthers";
        public static final String GUESTS_CAN_MODIFY = "guestsCanModify";
        public static final String GUESTS_CAN_SEE_GUESTS = "guestsCanSeeGuests";
        public static final String HAS_ALARM = "hasAlarm";
        public static final String HAS_ATTENDEE_DATA = "hasAttendeeData";
        public static final String HAS_EXTENDED_PROPERTIES = "hasExtendedProperties";
        public static final String IS_ORGANIZER = "isOrganizer";
        public static final String LAST_DATE = "lastDate";
        public static final String LAST_SYNCED = "lastSynced";
        public static final String ORGANIZER = "organizer";
        public static final String ORIGINAL_ALL_DAY = "originalAllDay";
        public static final String ORIGINAL_ID = "original_id";
        public static final String ORIGINAL_INSTANCE_TIME = "originalInstanceTime";
        public static final String ORIGINAL_SYNC_ID = "original_sync_id";
        public static final String RDATE = "rdate";
        public static final String RRULE = "rrule";
        public static final String SELF_ATTENDEE_STATUS = "selfAttendeeStatus";
        public static final String STATUS = "eventStatus";
        public static final int STATUS_CANCELED = 2;
        public static final int STATUS_CONFIRMED = 1;
        public static final int STATUS_TENTATIVE = 0;
        public static final String SYNC_DATA1 = "sync_data1";
        public static final String SYNC_DATA10 = "sync_data10";
        public static final String SYNC_DATA2 = "sync_data2";
        public static final String SYNC_DATA3 = "sync_data3";
        public static final String SYNC_DATA4 = "sync_data4";
        public static final String SYNC_DATA5 = "sync_data5";
        public static final String SYNC_DATA6 = "sync_data6";
        public static final String SYNC_DATA7 = "sync_data7";
        public static final String SYNC_DATA8 = "sync_data8";
        public static final String SYNC_DATA9 = "sync_data9";
        public static final String TITLE = "title";
        public static final String UID_2445 = "uid2445";
    }

    protected interface EventsRawTimesColumns {
        public static final String DTEND_2445 = "dtend2445";
        public static final String DTSTART_2445 = "dtstart2445";
        public static final String EVENT_ID = "event_id";
        public static final String LAST_DATE_2445 = "lastDate2445";
        public static final String ORIGINAL_INSTANCE_TIME_2445 = "originalInstanceTime2445";
    }

    protected interface ExtendedPropertiesColumns {
        public static final String EVENT_ID = "event_id";
        public static final String NAME = "name";
        public static final String VALUE = "value";
    }

    protected interface RemindersColumns {
        public static final String EVENT_ID = "event_id";
        public static final String METHOD = "method";
        public static final int METHOD_ALARM = 4;
        public static final int METHOD_ALERT = 1;
        public static final int METHOD_DEFAULT = 0;
        public static final int METHOD_EMAIL = 2;
        public static final int METHOD_SMS = 3;
        public static final String MINUTES = "minutes";
        public static final int MINUTES_DEFAULT = -1;
    }

    protected interface SyncColumns extends CalendarSyncColumns {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_TYPE = "account_type";
        public static final String CAN_PARTIALLY_UPDATE = "canPartiallyUpdate";
        public static final String DELETED = "deleted";
        public static final String DIRTY = "dirty";
        public static final String MUTATORS = "mutators";
        public static final String _SYNC_ID = "_sync_id";
    }

    private CalendarContract() {
    }

    public static boolean startViewCalendarEventInManagedProfile(Context context, long eventId, long startMs, long endMs, boolean allDay, int flags) {
        Context context2 = context;
        Preconditions.checkNotNull(context, "Context is null");
        return ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).startViewCalendarEventInManagedProfile(eventId, startMs, endMs, allDay, flags);
    }

    public static final class CalendarEntity implements BaseColumns, SyncColumns, CalendarColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendar_entities");

        private CalendarEntity() {
        }

        public static EntityIterator newEntityIterator(Cursor cursor) {
            return new EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl extends CursorEntityIterator {
            public EntityIteratorImpl(Cursor cursor) {
                super(cursor);
            }

            public Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException {
                long calendarId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                ContentValues cv = new ContentValues();
                cv.put("_id", Long.valueOf(calendarId));
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, "account_name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, "account_type");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, "_sync_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, cv, "dirty");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, SyncColumns.MUTATORS);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC1);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC2);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC3);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC4);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC5);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC6);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC7);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC8);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC9);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarSyncColumns.CAL_SYNC10);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, "name");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, "calendar_displayName");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.CALENDAR_COLOR);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarColumns.CALENDAR_COLOR_KEY);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.CALENDAR_ACCESS_LEVEL);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.VISIBLE);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.SYNC_EVENTS);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, Calendars.CALENDAR_LOCATION);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarColumns.CALENDAR_TIME_ZONE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarColumns.OWNER_ACCOUNT);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.CAN_ORGANIZER_RESPOND);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.CAN_MODIFY_TIME_ZONE);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, CalendarColumns.MAX_REMINDERS);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, SyncColumns.CAN_PARTIALLY_UPDATE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, cv, CalendarColumns.ALLOWED_REMINDERS);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor, cv, "deleted");
                Entity entity = new Entity(cv);
                cursor.moveToNext();
                return entity;
            }
        }
    }

    public static final class Calendars implements BaseColumns, SyncColumns, CalendarColumns {
        public static final String CALENDAR_LOCATION = "calendar_location";
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendars");
        public static final String DEFAULT_SORT_ORDER = "calendar_displayName";
        public static final Uri ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise/calendars");
        public static final String NAME = "name";
        public static final String[] SYNC_WRITABLE_COLUMNS = {"account_name", "account_type", "_sync_id", "dirty", SyncColumns.MUTATORS, CalendarColumns.OWNER_ACCOUNT, CalendarColumns.MAX_REMINDERS, CalendarColumns.ALLOWED_REMINDERS, CalendarColumns.CAN_MODIFY_TIME_ZONE, CalendarColumns.CAN_ORGANIZER_RESPOND, SyncColumns.CAN_PARTIALLY_UPDATE, CALENDAR_LOCATION, CalendarColumns.CALENDAR_TIME_ZONE, CalendarColumns.CALENDAR_ACCESS_LEVEL, "deleted", CalendarSyncColumns.CAL_SYNC1, CalendarSyncColumns.CAL_SYNC2, CalendarSyncColumns.CAL_SYNC3, CalendarSyncColumns.CAL_SYNC4, CalendarSyncColumns.CAL_SYNC5, CalendarSyncColumns.CAL_SYNC6, CalendarSyncColumns.CAL_SYNC7, CalendarSyncColumns.CAL_SYNC8, CalendarSyncColumns.CAL_SYNC9, CalendarSyncColumns.CAL_SYNC10};

        private Calendars() {
        }
    }

    public static final class Attendees implements BaseColumns, AttendeesColumns, EventsColumns {
        private static final String ATTENDEES_WHERE = "event_id=?";
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/attendees");

        private Attendees() {
        }

        public static final Cursor query(ContentResolver cr, long eventId, String[] projection) {
            SeempLog.record(54);
            return cr.query(CONTENT_URI, projection, ATTENDEES_WHERE, new String[]{Long.toString(eventId)}, (String) null);
        }
    }

    public static final class EventsEntity implements BaseColumns, SyncColumns, EventsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/event_entities");

        private EventsEntity() {
        }

        public static EntityIterator newEntityIterator(Cursor cursor, ContentResolver resolver) {
            return new EntityIteratorImpl(cursor, resolver);
        }

        public static EntityIterator newEntityIterator(Cursor cursor, ContentProviderClient provider) {
            return new EntityIteratorImpl(cursor, provider);
        }

        private static class EntityIteratorImpl extends CursorEntityIterator {
            private static final String[] ATTENDEES_PROJECTION = {AttendeesColumns.ATTENDEE_NAME, AttendeesColumns.ATTENDEE_EMAIL, AttendeesColumns.ATTENDEE_RELATIONSHIP, AttendeesColumns.ATTENDEE_TYPE, AttendeesColumns.ATTENDEE_STATUS, AttendeesColumns.ATTENDEE_IDENTITY, AttendeesColumns.ATTENDEE_ID_NAMESPACE};
            private static final int COLUMN_ATTENDEE_EMAIL = 1;
            private static final int COLUMN_ATTENDEE_IDENTITY = 5;
            private static final int COLUMN_ATTENDEE_ID_NAMESPACE = 6;
            private static final int COLUMN_ATTENDEE_NAME = 0;
            private static final int COLUMN_ATTENDEE_RELATIONSHIP = 2;
            private static final int COLUMN_ATTENDEE_STATUS = 4;
            private static final int COLUMN_ATTENDEE_TYPE = 3;
            private static final int COLUMN_ID = 0;
            private static final int COLUMN_METHOD = 1;
            private static final int COLUMN_MINUTES = 0;
            private static final int COLUMN_NAME = 1;
            private static final int COLUMN_VALUE = 2;
            private static final String[] EXTENDED_PROJECTION = {"_id", "name", "value"};
            private static final String[] REMINDERS_PROJECTION = {"minutes", RemindersColumns.METHOD};
            private static final String WHERE_EVENT_ID = "event_id=?";
            private final ContentProviderClient mProvider;
            private final ContentResolver mResolver;

            public EntityIteratorImpl(Cursor cursor, ContentResolver resolver) {
                super(cursor);
                this.mResolver = resolver;
                this.mProvider = null;
            }

            public EntityIteratorImpl(Cursor cursor, ContentProviderClient provider) {
                super(cursor);
                this.mResolver = null;
                this.mProvider = provider;
            }

            /* JADX INFO: finally extract failed */
            public Entity getEntityAndIncrementCursor(Cursor cursor) throws RemoteException {
                Cursor subCursor;
                Cursor subCursor2;
                Cursor query;
                Cursor query2;
                Cursor cursor2 = cursor;
                long eventId = cursor2.getLong(cursor2.getColumnIndexOrThrow("_id"));
                ContentValues cv = new ContentValues();
                cv.put("_id", Long.valueOf(eventId));
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.CALENDAR_ID);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, "title");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, "description");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.EVENT_LOCATION);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.STATUS);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.SELF_ATTENDEE_STATUS);
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor2, cv, EventsColumns.DTSTART);
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor2, cv, EventsColumns.DTEND);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, "duration");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.EVENT_TIMEZONE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.EVENT_END_TIMEZONE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, "allDay");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.ACCESS_LEVEL);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, "availability");
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.EVENT_COLOR);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.EVENT_COLOR_KEY);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.HAS_ALARM);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.HAS_EXTENDED_PROPERTIES);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.RRULE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.RDATE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.EXRULE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.EXDATE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.ORIGINAL_SYNC_ID);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.ORIGINAL_ID);
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor2, cv, EventsColumns.ORIGINAL_INSTANCE_TIME);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.ORIGINAL_ALL_DAY);
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor2, cv, EventsColumns.LAST_DATE);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.HAS_ATTENDEE_DATA);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.GUESTS_CAN_INVITE_OTHERS);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.GUESTS_CAN_MODIFY);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, EventsColumns.GUESTS_CAN_SEE_GUESTS);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.CUSTOM_APP_PACKAGE);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, "customAppUri");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.UID_2445);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.ORGANIZER);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.IS_ORGANIZER);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, "_sync_id");
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor2, cv, "dirty");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, SyncColumns.MUTATORS);
                DatabaseUtils.cursorLongToContentValuesIfPresent(cursor2, cv, EventsColumns.LAST_SYNCED);
                DatabaseUtils.cursorIntToContentValuesIfPresent(cursor2, cv, "deleted");
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA1);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA2);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA3);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA4);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA5);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA6);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA7);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA8);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA9);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, EventsColumns.SYNC_DATA10);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC1);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC2);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC3);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC4);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC5);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC6);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC7);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC8);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC9);
                DatabaseUtils.cursorStringToContentValuesIfPresent(cursor2, cv, CalendarSyncColumns.CAL_SYNC10);
                Entity entity = new Entity(cv);
                if (this.mResolver != null) {
                    subCursor = this.mResolver.query(Reminders.CONTENT_URI, REMINDERS_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(eventId)}, (String) null);
                } else {
                    subCursor = this.mProvider.query(Reminders.CONTENT_URI, REMINDERS_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(eventId)}, (String) null);
                }
                while (true) {
                    subCursor2 = subCursor;
                    try {
                        if (!subCursor2.moveToNext()) {
                            break;
                        }
                        ContentValues reminderValues = new ContentValues();
                        reminderValues.put("minutes", Integer.valueOf(subCursor2.getInt(0)));
                        reminderValues.put(RemindersColumns.METHOD, Integer.valueOf(subCursor2.getInt(1)));
                        entity.addSubValue(Reminders.CONTENT_URI, reminderValues);
                        subCursor = subCursor2;
                    } catch (Throwable th) {
                        subCursor2.close();
                        throw th;
                    }
                }
                subCursor2.close();
                if (this.mResolver != null) {
                    query = this.mResolver.query(Attendees.CONTENT_URI, ATTENDEES_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(eventId)}, (String) null);
                } else {
                    query = this.mProvider.query(Attendees.CONTENT_URI, ATTENDEES_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(eventId)}, (String) null);
                }
                Cursor subCursor3 = query;
                while (subCursor3.moveToNext()) {
                    try {
                        ContentValues attendeeValues = new ContentValues();
                        attendeeValues.put(AttendeesColumns.ATTENDEE_NAME, subCursor3.getString(0));
                        attendeeValues.put(AttendeesColumns.ATTENDEE_EMAIL, subCursor3.getString(1));
                        attendeeValues.put(AttendeesColumns.ATTENDEE_RELATIONSHIP, Integer.valueOf(subCursor3.getInt(2)));
                        attendeeValues.put(AttendeesColumns.ATTENDEE_TYPE, Integer.valueOf(subCursor3.getInt(3)));
                        attendeeValues.put(AttendeesColumns.ATTENDEE_STATUS, Integer.valueOf(subCursor3.getInt(4)));
                        attendeeValues.put(AttendeesColumns.ATTENDEE_IDENTITY, subCursor3.getString(5));
                        attendeeValues.put(AttendeesColumns.ATTENDEE_ID_NAMESPACE, subCursor3.getString(6));
                        entity.addSubValue(Attendees.CONTENT_URI, attendeeValues);
                    } catch (Throwable th2) {
                        subCursor3.close();
                        throw th2;
                    }
                }
                subCursor3.close();
                if (this.mResolver != null) {
                    query2 = this.mResolver.query(ExtendedProperties.CONTENT_URI, EXTENDED_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(eventId)}, (String) null);
                } else {
                    query2 = this.mProvider.query(ExtendedProperties.CONTENT_URI, EXTENDED_PROJECTION, WHERE_EVENT_ID, new String[]{Long.toString(eventId)}, (String) null);
                }
                Cursor subCursor4 = query2;
                while (subCursor4.moveToNext()) {
                    try {
                        ContentValues extendedValues = new ContentValues();
                        extendedValues.put("_id", subCursor4.getString(0));
                        extendedValues.put("name", subCursor4.getString(1));
                        extendedValues.put("value", subCursor4.getString(2));
                        entity.addSubValue(ExtendedProperties.CONTENT_URI, extendedValues);
                    } catch (Throwable th3) {
                        subCursor4.close();
                        throw th3;
                    }
                }
                subCursor4.close();
                cursor.moveToNext();
                return entity;
            }
        }
    }

    public static final class Events implements BaseColumns, SyncColumns, EventsColumns, CalendarColumns {
        public static final Uri CONTENT_EXCEPTION_URI = Uri.parse("content://com.android.calendar/exception");
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/events");
        private static final String DEFAULT_SORT_ORDER = "";
        public static final Uri ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise/events");
        @UnsupportedAppUsage
        public static String[] PROVIDER_WRITABLE_COLUMNS = {"account_name", "account_type", CalendarSyncColumns.CAL_SYNC1, CalendarSyncColumns.CAL_SYNC2, CalendarSyncColumns.CAL_SYNC3, CalendarSyncColumns.CAL_SYNC4, CalendarSyncColumns.CAL_SYNC5, CalendarSyncColumns.CAL_SYNC6, CalendarSyncColumns.CAL_SYNC7, CalendarSyncColumns.CAL_SYNC8, CalendarSyncColumns.CAL_SYNC9, CalendarSyncColumns.CAL_SYNC10, CalendarColumns.ALLOWED_REMINDERS, CalendarColumns.ALLOWED_ATTENDEE_TYPES, CalendarColumns.ALLOWED_AVAILABILITY, CalendarColumns.CALENDAR_ACCESS_LEVEL, CalendarColumns.CALENDAR_COLOR, CalendarColumns.CALENDAR_TIME_ZONE, CalendarColumns.CAN_MODIFY_TIME_ZONE, CalendarColumns.CAN_ORGANIZER_RESPOND, "calendar_displayName", SyncColumns.CAN_PARTIALLY_UPDATE, CalendarColumns.SYNC_EVENTS, CalendarColumns.VISIBLE};
        public static final String[] SYNC_WRITABLE_COLUMNS = {"_sync_id", "dirty", SyncColumns.MUTATORS, EventsColumns.SYNC_DATA1, EventsColumns.SYNC_DATA2, EventsColumns.SYNC_DATA3, EventsColumns.SYNC_DATA4, EventsColumns.SYNC_DATA5, EventsColumns.SYNC_DATA6, EventsColumns.SYNC_DATA7, EventsColumns.SYNC_DATA8, EventsColumns.SYNC_DATA9, EventsColumns.SYNC_DATA10};

        private Events() {
        }
    }

    public static final class Instances implements BaseColumns, EventsColumns, CalendarColumns {
        public static final String BEGIN = "begin";
        public static final Uri CONTENT_BY_DAY_URI = Uri.parse("content://com.android.calendar/instances/whenbyday");
        public static final Uri CONTENT_SEARCH_BY_DAY_URI = Uri.parse("content://com.android.calendar/instances/searchbyday");
        public static final Uri CONTENT_SEARCH_URI = Uri.parse("content://com.android.calendar/instances/search");
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/instances/when");
        private static final String DEFAULT_SORT_ORDER = "begin ASC";
        public static final String END = "end";
        public static final String END_DAY = "endDay";
        public static final String END_MINUTE = "endMinute";
        public static final Uri ENTERPRISE_CONTENT_BY_DAY_URI = Uri.parse("content://com.android.calendar/enterprise/instances/whenbyday");
        public static final Uri ENTERPRISE_CONTENT_SEARCH_BY_DAY_URI = Uri.parse("content://com.android.calendar/enterprise/instances/searchbyday");
        public static final Uri ENTERPRISE_CONTENT_SEARCH_URI = Uri.parse("content://com.android.calendar/enterprise/instances/search");
        public static final Uri ENTERPRISE_CONTENT_URI = Uri.parse("content://com.android.calendar/enterprise/instances/when");
        public static final String EVENT_ID = "event_id";
        public static final String START_DAY = "startDay";
        public static final String START_MINUTE = "startMinute";
        private static final String[] WHERE_CALENDARS_ARGS = {"1"};
        private static final String WHERE_CALENDARS_SELECTED = "visible=?";

        private Instances() {
        }

        public static final Cursor query(ContentResolver cr, String[] projection, long begin, long end) {
            SeempLog.record(54);
            Uri.Builder builder = CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, begin);
            ContentUris.appendId(builder, end);
            return cr.query(builder.build(), projection, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
        }

        public static final Cursor query(ContentResolver cr, String[] projection, long begin, long end, String searchQuery) {
            SeempLog.record(54);
            Uri.Builder builder = CONTENT_SEARCH_URI.buildUpon();
            ContentUris.appendId(builder, begin);
            ContentUris.appendId(builder, end);
            return cr.query(builder.appendPath(searchQuery).build(), projection, WHERE_CALENDARS_SELECTED, WHERE_CALENDARS_ARGS, DEFAULT_SORT_ORDER);
        }
    }

    public static final class CalendarCache implements CalendarCacheColumns {
        public static final String KEY_TIMEZONE_INSTANCES = "timezoneInstances";
        public static final String KEY_TIMEZONE_INSTANCES_PREVIOUS = "timezoneInstancesPrevious";
        public static final String KEY_TIMEZONE_TYPE = "timezoneType";
        public static final String TIMEZONE_TYPE_AUTO = "auto";
        public static final String TIMEZONE_TYPE_HOME = "home";
        public static final Uri URI = Uri.parse("content://com.android.calendar/properties");

        private CalendarCache() {
        }
    }

    public static final class CalendarMetaData implements CalendarMetaDataColumns, BaseColumns {
        private CalendarMetaData() {
        }
    }

    public static final class EventDays implements EventDaysColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/instances/groupbyday");
        private static final String SELECTION = "selected=1";

        private EventDays() {
        }

        public static final Cursor query(ContentResolver cr, int startDay, int numDays, String[] projection) {
            SeempLog.record(54);
            if (numDays < 1) {
                return null;
            }
            Uri.Builder builder = CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, (long) startDay);
            ContentUris.appendId(builder, (long) ((startDay + numDays) - 1));
            return cr.query(builder.build(), projection, SELECTION, (String[]) null, "startDay");
        }
    }

    public static final class Reminders implements BaseColumns, RemindersColumns, EventsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/reminders");
        private static final String REMINDERS_WHERE = "event_id=?";

        private Reminders() {
        }

        public static final Cursor query(ContentResolver cr, long eventId, String[] projection) {
            SeempLog.record(54);
            return cr.query(CONTENT_URI, projection, REMINDERS_WHERE, new String[]{Long.toString(eventId)}, (String) null);
        }
    }

    public static final class CalendarAlerts implements BaseColumns, CalendarAlertsColumns, EventsColumns, CalendarColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/calendar_alerts");
        public static final Uri CONTENT_URI_BY_INSTANCE = Uri.parse("content://com.android.calendar/calendar_alerts/by_instance");
        private static final boolean DEBUG = false;
        private static final String SORT_ORDER_ALARMTIME_ASC = "alarmTime ASC";
        public static final String TABLE_NAME = "CalendarAlerts";
        private static final String WHERE_ALARM_EXISTS = "event_id=? AND begin=? AND alarmTime=?";
        private static final String WHERE_FINDNEXTALARMTIME = "alarmTime>=?";
        private static final String WHERE_RESCHEDULE_MISSED_ALARMS = "state=0 AND alarmTime<? AND alarmTime>? AND end>=?";

        private CalendarAlerts() {
        }

        public static final Uri insert(ContentResolver cr, long eventId, long begin, long end, long alarmTime, int minutes) {
            SeempLog.record(51);
            ContentValues values = new ContentValues();
            values.put("event_id", Long.valueOf(eventId));
            values.put("begin", Long.valueOf(begin));
            values.put("end", Long.valueOf(end));
            values.put(CalendarAlertsColumns.ALARM_TIME, Long.valueOf(alarmTime));
            values.put(CalendarAlertsColumns.CREATION_TIME, Long.valueOf(System.currentTimeMillis()));
            values.put(CalendarAlertsColumns.RECEIVED_TIME, (Integer) 0);
            values.put(CalendarAlertsColumns.NOTIFY_TIME, (Integer) 0);
            values.put("state", (Integer) 0);
            values.put("minutes", Integer.valueOf(minutes));
            return cr.insert(CONTENT_URI, values);
        }

        @UnsupportedAppUsage
        public static final long findNextAlarmTime(ContentResolver cr, long millis) {
            SeempLog.record(53);
            String str = "alarmTime>=" + millis;
            Cursor cursor = cr.query(CONTENT_URI, new String[]{CalendarAlertsColumns.ALARM_TIME}, WHERE_FINDNEXTALARMTIME, new String[]{Long.toString(millis)}, SORT_ORDER_ALARMTIME_ASC);
            long alarmTime = -1;
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        alarmTime = cursor.getLong(0);
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return alarmTime;
        }

        @UnsupportedAppUsage
        public static final void rescheduleMissedAlarms(ContentResolver cr, Context context, AlarmManager manager) {
            long now = System.currentTimeMillis();
            String[] projection = {CalendarAlertsColumns.ALARM_TIME};
            Cursor cursor = cr.query(CONTENT_URI, projection, WHERE_RESCHEDULE_MISSED_ALARMS, new String[]{Long.toString(now), Long.toString(now - 86400000), Long.toString(now)}, SORT_ORDER_ALARMTIME_ASC);
            if (cursor != null) {
                long alarmTime = -1;
                while (cursor.moveToNext()) {
                    try {
                        long newAlarmTime = cursor.getLong(0);
                        if (alarmTime != newAlarmTime) {
                            scheduleAlarm(context, manager, newAlarmTime);
                            alarmTime = newAlarmTime;
                        }
                    } finally {
                        cursor.close();
                    }
                }
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: android.app.AlarmManager} */
        /* JADX WARNING: Multi-variable type inference failed */
        @android.annotation.UnsupportedAppUsage
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static void scheduleAlarm(android.content.Context r3, android.app.AlarmManager r4, long r5) {
            /*
                if (r4 != 0) goto L_0x000b
                java.lang.String r0 = "alarm"
                java.lang.Object r0 = r3.getSystemService((java.lang.String) r0)
                r4 = r0
                android.app.AlarmManager r4 = (android.app.AlarmManager) r4
            L_0x000b:
                android.content.Intent r0 = new android.content.Intent
                java.lang.String r1 = "android.intent.action.EVENT_REMINDER"
                r0.<init>((java.lang.String) r1)
                android.net.Uri r1 = android.provider.CalendarContract.CONTENT_URI
                android.net.Uri r1 = android.content.ContentUris.withAppendedId(r1, r5)
                r0.setData(r1)
                java.lang.String r1 = "alarmTime"
                r0.putExtra((java.lang.String) r1, (long) r5)
                r1 = 16777216(0x1000000, float:2.3509887E-38)
                r0.setFlags(r1)
                r1 = 0
                android.app.PendingIntent r2 = android.app.PendingIntent.getBroadcast(r3, r1, r0, r1)
                r4.setExactAndAllowWhileIdle(r1, r5, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.provider.CalendarContract.CalendarAlerts.scheduleAlarm(android.content.Context, android.app.AlarmManager, long):void");
        }

        public static final boolean alarmExists(ContentResolver cr, long eventId, long begin, long alarmTime) {
            SeempLog.record(52);
            Cursor cursor = cr.query(CONTENT_URI, new String[]{CalendarAlertsColumns.ALARM_TIME}, WHERE_ALARM_EXISTS, new String[]{Long.toString(eventId), Long.toString(begin), Long.toString(alarmTime)}, (String) null);
            boolean found = false;
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        found = true;
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return found;
        }
    }

    public static final class Colors implements ColorsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/colors");
        public static final String TABLE_NAME = "Colors";

        private Colors() {
        }
    }

    public static final class ExtendedProperties implements BaseColumns, ExtendedPropertiesColumns, EventsColumns {
        public static final Uri CONTENT_URI = Uri.parse("content://com.android.calendar/extendedproperties");

        private ExtendedProperties() {
        }
    }

    public static final class SyncState implements SyncStateContract.Columns {
        private static final String CONTENT_DIRECTORY = "syncstate";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(CalendarContract.CONTENT_URI, "syncstate");

        private SyncState() {
        }
    }

    public static final class EventsRawTimes implements BaseColumns, EventsRawTimesColumns {
        private EventsRawTimes() {
        }
    }
}
