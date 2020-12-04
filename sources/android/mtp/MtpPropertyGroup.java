package android.mtp;

import android.provider.MediaStore;
import android.util.Log;
import java.util.ArrayList;

class MtpPropertyGroup {
    private static final String PATH_WHERE = "_data=?";
    private static final String TAG = MtpPropertyGroup.class.getSimpleName();
    private String[] mColumns;
    private final Property[] mProperties;

    private native String format_date_time(long j);

    private class Property {
        int code;
        int column;
        int type;

        Property(int code2, int type2, int column2) {
            this.code = code2;
            this.type = type2;
            this.column = column2;
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
            case MtpConstants.PROPERTY_STORAGE_ID:
                type = 6;
                break;
            case MtpConstants.PROPERTY_OBJECT_FORMAT:
                type = 4;
                break;
            case MtpConstants.PROPERTY_PROTECTION_STATUS:
                type = 4;
                break;
            case MtpConstants.PROPERTY_OBJECT_SIZE:
                type = 8;
                break;
            case MtpConstants.PROPERTY_OBJECT_FILE_NAME:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DATE_MODIFIED:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_PARENT_OBJECT:
                type = 6;
                break;
            case MtpConstants.PROPERTY_PERSISTENT_UID:
                type = 10;
                break;
            case MtpConstants.PROPERTY_NAME:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ARTIST:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DESCRIPTION:
                column = "description";
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DATE_ADDED:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DURATION:
                column = "duration";
                type = 6;
                break;
            case MtpConstants.PROPERTY_TRACK:
                column = MediaStore.Audio.AudioColumns.TRACK;
                type = 4;
                break;
            case MtpConstants.PROPERTY_COMPOSER:
                column = MediaStore.Audio.AudioColumns.COMPOSER;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE:
                column = MediaStore.Audio.AudioColumns.YEAR;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_NAME:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_ALBUM_ARTIST:
                column = MediaStore.Audio.AudioColumns.ALBUM_ARTIST;
                type = 65535;
                break;
            case MtpConstants.PROPERTY_DISPLAY_NAME:
                type = 65535;
                break;
            case MtpConstants.PROPERTY_BITRATE_TYPE:
            case MtpConstants.PROPERTY_NUMBER_OF_CHANNELS:
                type = 4;
                break;
            case MtpConstants.PROPERTY_SAMPLE_RATE:
            case MtpConstants.PROPERTY_AUDIO_WAVE_CODEC:
            case MtpConstants.PROPERTY_AUDIO_BITRATE:
                type = 6;
                break;
            default:
                type = 0;
                String str = TAG;
                Log.e(str, "unsupported property " + code);
                break;
        }
        if (column == null) {
            return new Property(code, type, -1);
        }
        columns.add(column);
        return new Property(code, type, columns.size() - 1);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0119  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0143  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0160  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getPropertyList(android.content.ContentProviderClient r24, java.lang.String r25, android.mtp.MtpStorageManager.MtpObject r26, android.mtp.MtpPropertyList r27) {
        /*
            r23 = this;
            r1 = r23
            r8 = r27
            r0 = 0
            int r9 = r26.getId()
            java.nio.file.Path r2 = r26.getPath()
            java.lang.String r10 = r2.toString()
            android.mtp.MtpPropertyGroup$Property[] r11 = r1.mProperties
            int r12 = r11.length
            r13 = 0
            r2 = r0
            r14 = r13
        L_0x0017:
            if (r14 >= r12) goto L_0x01b2
            r15 = r11[r14]
            int r0 = r15.column
            r3 = -1
            if (r0 == r3) goto L_0x0067
            if (r2 != 0) goto L_0x0067
            int r0 = r26.getFormat()     // Catch:{ IllegalArgumentException -> 0x0060, RemoteException -> 0x0055 }
            r6 = r25
            android.net.Uri r17 = android.mtp.MtpDatabase.getObjectPropertiesUri(r0, r6)     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            java.lang.String[] r0 = r1.mColumns     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            java.lang.String r19 = "_data=?"
            r3 = 1
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            r3[r13] = r10     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            r21 = 0
            r22 = 0
            r16 = r24
            r18 = r0
            r20 = r3
            android.database.Cursor r0 = r16.query(r17, r18, r19, r20, r21, r22)     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            r2 = r0
            if (r2 == 0) goto L_0x0050
            boolean r0 = r2.moveToNext()     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            if (r0 != 0) goto L_0x0050
            r2.close()     // Catch:{ IllegalArgumentException -> 0x0053, RemoteException -> 0x0051 }
            r2 = 0
        L_0x0050:
            goto L_0x0069
        L_0x0051:
            r0 = move-exception
            goto L_0x0058
        L_0x0053:
            r0 = move-exception
            goto L_0x0063
        L_0x0055:
            r0 = move-exception
            r6 = r25
        L_0x0058:
            java.lang.String r3 = TAG
            java.lang.String r4 = "Mediaprovider lookup failed"
            android.util.Log.e(r3, r4)
            goto L_0x0069
        L_0x0060:
            r0 = move-exception
            r6 = r25
        L_0x0063:
            r3 = 43009(0xa801, float:6.0268E-41)
            return r3
        L_0x0067:
            r6 = r25
        L_0x0069:
            r0 = r2
            int r2 = r15.code
            switch(r2) {
                case 56321: goto L_0x0160;
                case 56322: goto L_0x0150;
                case 56323: goto L_0x0143;
                case 56324: goto L_0x0133;
                case 56327: goto L_0x0128;
                case 56329: goto L_0x0119;
                case 56331: goto L_0x00f5;
                case 56385: goto L_0x00d2;
                case 56388: goto L_0x0128;
                case 56398: goto L_0x0119;
                case 56459: goto L_0x00b4;
                case 56473: goto L_0x008f;
                case 56544: goto L_0x0128;
                case 56978: goto L_0x0082;
                case 56979: goto L_0x0073;
                case 56980: goto L_0x0082;
                case 56985: goto L_0x0073;
                case 56986: goto L_0x0073;
                default: goto L_0x006f;
            }
        L_0x006f:
            int r2 = r15.type
            goto L_0x0170
        L_0x0073:
            int r4 = r15.code
            r5 = 6
            r16 = 0
            r2 = r27
            r3 = r9
            r6 = r16
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0082:
            int r4 = r15.code
            r5 = 4
            r6 = 0
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x008f:
            r2 = 0
            if (r0 == 0) goto L_0x0098
            int r3 = r15.column
            int r2 = r0.getInt(r3)
        L_0x0098:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = java.lang.Integer.toString(r2)
            r3.append(r4)
            java.lang.String r4 = "0101T000000"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            int r4 = r15.code
            r8.append(r9, r4, r3)
            goto L_0x01ad
        L_0x00b4:
            r2 = 0
            if (r0 == 0) goto L_0x00bd
            int r3 = r15.column
            int r2 = r0.getInt(r3)
        L_0x00bd:
            r6 = r2
            int r4 = r15.code
            r5 = 4
            int r2 = r6 % 1000
            long r2 = (long) r2
            r16 = r2
            r2 = r27
            r3 = r9
            r18 = r6
            r6 = r16
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x00d2:
            java.nio.file.Path r2 = r26.getPath()
            java.lang.String r2 = r2.toString()
            int r2 = r2.hashCode()
            int r2 = r2 << 32
            long r2 = (long) r2
            long r4 = r26.getModifiedTime()
            long r16 = r2 + r4
            int r4 = r15.code
            int r5 = r15.type
            r2 = r27
            r3 = r9
            r6 = r16
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x00f5:
            int r4 = r15.code
            int r5 = r15.type
            android.mtp.MtpStorageManager$MtpObject r2 = r26.getParent()
            boolean r2 = r2.isRoot()
            if (r2 == 0) goto L_0x0107
            r2 = 0
        L_0x0105:
            r6 = r2
            goto L_0x0111
        L_0x0107:
            android.mtp.MtpStorageManager$MtpObject r2 = r26.getParent()
            int r2 = r2.getId()
            long r2 = (long) r2
            goto L_0x0105
        L_0x0111:
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0119:
            int r2 = r15.code
            long r3 = r26.getModifiedTime()
            java.lang.String r3 = r1.format_date_time(r3)
            r8.append(r9, r2, r3)
            goto L_0x01ad
        L_0x0128:
            int r2 = r15.code
            java.lang.String r3 = r26.getName()
            r8.append(r9, r2, r3)
            goto L_0x01ad
        L_0x0133:
            int r4 = r15.code
            int r5 = r15.type
            long r6 = r26.getSize()
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0143:
            int r4 = r15.code
            int r5 = r15.type
            r6 = 0
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0150:
            int r4 = r15.code
            int r5 = r15.type
            int r2 = r26.getFormat()
            long r6 = (long) r2
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0160:
            int r4 = r15.code
            int r5 = r15.type
            int r2 = r26.getStorageId()
            long r6 = (long) r2
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0170:
            if (r2 == 0) goto L_0x01a0
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r2 == r3) goto L_0x0190
            r2 = 0
            if (r0 == 0) goto L_0x0181
            int r4 = r15.column
            long r2 = r0.getLong(r4)
        L_0x0181:
            r16 = r2
            int r4 = r15.code
            int r5 = r15.type
            r2 = r27
            r3 = r9
            r6 = r16
            r2.append(r3, r4, r5, r6)
            goto L_0x01ad
        L_0x0190:
            java.lang.String r2 = ""
            if (r0 == 0) goto L_0x019a
            int r3 = r15.column
            java.lang.String r2 = r0.getString(r3)
        L_0x019a:
            int r3 = r15.code
            r8.append(r9, r3, r2)
            goto L_0x01ad
        L_0x01a0:
            int r4 = r15.code
            int r5 = r15.type
            r6 = 0
            r2 = r27
            r3 = r9
            r2.append(r3, r4, r5, r6)
        L_0x01ad:
            int r14 = r14 + 1
            r2 = r0
            goto L_0x0017
        L_0x01b2:
            if (r2 == 0) goto L_0x01b7
            r2.close()
        L_0x01b7:
            r0 = 8193(0x2001, float:1.1481E-41)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpPropertyGroup.getPropertyList(android.content.ContentProviderClient, java.lang.String, android.mtp.MtpStorageManager$MtpObject, android.mtp.MtpPropertyList):int");
    }
}
