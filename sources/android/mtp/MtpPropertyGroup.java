package android.mtp;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.mtp.MtpStorageManager;
import android.net.Uri;
import android.p007os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes3.dex */
class MtpPropertyGroup {
    private static final String PATH_WHERE = "_data=?";
    private static final String TAG = MtpPropertyGroup.class.getSimpleName();
    private String[] mColumns;
    private final Property[] mProperties;

    private native String format_date_time(long j);

    /* loaded from: classes3.dex */
    private class Property {
        int code;
        int column;
        int type;

        Property(int code, int type, int column) {
            this.code = code;
            this.type = type;
            this.column = column;
        }
    }

    public MtpPropertyGroup(int[] properties) {
        int count = properties.length;
        ArrayList<String> columns = new ArrayList<>(count);
        columns.add("_id");
        this.mProperties = new Property[count];
        for (int i = 0; i < count; i++) {
            this.mProperties[i] = createProperty(properties[i], columns);
        }
        int count2 = columns.size();
        this.mColumns = new String[count2];
        for (int i2 = 0; i2 < count2; i2++) {
            this.mColumns[i2] = columns.get(i2);
        }
    }

    private Property createProperty(int code, ArrayList<String> columns) {
        int type;
        String column = null;
        switch (code) {
            case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                type = 6;
                break;
            case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                type = 8;
                break;
            case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                type = 6;
                break;
            case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                type = 10;
                break;
            case MtpConstants.PROPERTY_NAME /* 56388 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ARTIST /* 56390 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DESCRIPTION /* 56392 */:
                column = "description";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DURATION /* 56457 */:
                column = "duration";
                type = 6;
                break;
            case MtpConstants.PROPERTY_TRACK /* 56459 */:
                column = MediaStore.Audio.AudioColumns.TRACK;
                type = 4;
                break;
            case MtpConstants.PROPERTY_COMPOSER /* 56470 */:
                column = MediaStore.Audio.AudioColumns.COMPOSER;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                column = MediaStore.Audio.AudioColumns.YEAR;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_NAME /* 56474 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_ARTIST /* 56475 */:
                column = MediaStore.Audio.AudioColumns.ALBUM_ARTIST;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
            case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                type = 4;
                break;
            case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
            case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
            case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                type = 6;
                break;
            default:
                type = 0;
                String str = TAG;
                Log.m70e(str, "unsupported property " + code);
                break;
        }
        if (column != null) {
            columns.add(column);
            return new Property(code, type, columns.size() - 1);
        }
        return new Property(code, type, -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0160  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getPropertyList(ContentProviderClient provider, String volumeName, MtpStorageManager.MtpObject object, MtpPropertyList list) {
        int id = object.getId();
        String path = object.getPath().toString();
        Property[] propertyArr = this.mProperties;
        int length = propertyArr.length;
        Cursor c = null;
        int i = 0;
        while (i < length) {
            Property property = propertyArr[i];
            if (property.column != -1 && c == null) {
                try {
                } catch (RemoteException e) {
                } catch (IllegalArgumentException e2) {
                    return MtpConstants.RESPONSE_INVALID_OBJECT_PROP_CODE;
                }
                try {
                    Uri uri = MtpDatabase.getObjectPropertiesUri(object.getFormat(), volumeName);
                    c = provider.query(uri, this.mColumns, PATH_WHERE, new String[]{path}, null, null);
                    if (c != null && !c.moveToNext()) {
                        c.close();
                        c = null;
                    }
                } catch (RemoteException e3) {
                    Log.m70e(TAG, "Mediaprovider lookup failed");
                    Cursor c2 = c;
                    switch (property.code) {
                        case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                            break;
                        case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                            break;
                        case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                            break;
                        case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                            break;
                        case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                        case MtpConstants.PROPERTY_NAME /* 56388 */:
                        case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                            break;
                        case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                        case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                            break;
                        case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                            break;
                        case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                            break;
                        case MtpConstants.PROPERTY_TRACK /* 56459 */:
                            break;
                        case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                            break;
                        case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
                        case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                            break;
                        case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
                        case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
                        case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                            break;
                    }
                    i++;
                    c = c2;
                } catch (IllegalArgumentException e4) {
                    return MtpConstants.RESPONSE_INVALID_OBJECT_PROP_CODE;
                }
            }
            Cursor c22 = c;
            switch (property.code) {
                case MtpConstants.PROPERTY_STORAGE_ID /* 56321 */:
                    list.append(id, property.code, property.type, object.getStorageId());
                    break;
                case MtpConstants.PROPERTY_OBJECT_FORMAT /* 56322 */:
                    list.append(id, property.code, property.type, object.getFormat());
                    break;
                case MtpConstants.PROPERTY_PROTECTION_STATUS /* 56323 */:
                    list.append(id, property.code, property.type, 0L);
                    break;
                case MtpConstants.PROPERTY_OBJECT_SIZE /* 56324 */:
                    list.append(id, property.code, property.type, object.getSize());
                    break;
                case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                case MtpConstants.PROPERTY_NAME /* 56388 */:
                case MtpConstants.PROPERTY_DISPLAY_NAME /* 56544 */:
                    list.append(id, property.code, object.getName());
                    break;
                case MtpConstants.PROPERTY_DATE_MODIFIED /* 56329 */:
                case MtpConstants.PROPERTY_DATE_ADDED /* 56398 */:
                    list.append(id, property.code, format_date_time(object.getModifiedTime()));
                    break;
                case MtpConstants.PROPERTY_PARENT_OBJECT /* 56331 */:
                    list.append(id, property.code, property.type, object.getParent().isRoot() ? 0L : object.getParent().getId());
                    break;
                case MtpConstants.PROPERTY_PERSISTENT_UID /* 56385 */:
                    long puid = (object.getPath().toString().hashCode() << 32) + object.getModifiedTime();
                    list.append(id, property.code, property.type, puid);
                    break;
                case MtpConstants.PROPERTY_TRACK /* 56459 */:
                    int track = 0;
                    if (c22 != null) {
                        track = c22.getInt(property.column);
                    }
                    list.append(id, property.code, 4, track % 1000);
                    break;
                case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE /* 56473 */:
                    int year = 0;
                    if (c22 != null) {
                        year = c22.getInt(property.column);
                    }
                    String dateTime = Integer.toString(year) + "0101T000000";
                    list.append(id, property.code, dateTime);
                    break;
                case MtpConstants.PROPERTY_BITRATE_TYPE /* 56978 */:
                case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS /* 56980 */:
                    list.append(id, property.code, 4, 0L);
                    break;
                case MtpConstants.PROPERTY_SAMPLE_RATE /* 56979 */:
                case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC /* 56985 */:
                case MtpConstants.PROPERTY_AUDIO_BITRATE /* 56986 */:
                    list.append(id, property.code, 6, 0L);
                    break;
                default:
                    int i2 = property.type;
                    if (i2 == 0) {
                        list.append(id, property.code, property.type, 0L);
                        break;
                    } else if (i2 == 65535) {
                        String value = "";
                        if (c22 != null) {
                            value = c22.getString(property.column);
                        }
                        list.append(id, property.code, value);
                        break;
                    } else {
                        long longValue = 0;
                        if (c22 != null) {
                            longValue = c22.getLong(property.column);
                        }
                        list.append(id, property.code, property.type, longValue);
                        break;
                    }
            }
            i++;
            c = c22;
        }
        if (c != null) {
            c.close();
            return 8193;
        }
        return 8193;
    }
}
