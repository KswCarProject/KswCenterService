package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.p007os.UserHandle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Toast;
import com.android.internal.C3132R;
import java.util.List;

/* loaded from: classes3.dex */
public class ContactsInternal {
    private static final int CONTACTS_URI_LOOKUP = 1001;
    private static final int CONTACTS_URI_LOOKUP_ID = 1000;
    private static final UriMatcher sContactsUriMatcher = new UriMatcher(-1);

    private ContactsInternal() {
    }

    static {
        UriMatcher matcher = sContactsUriMatcher;
        matcher.addURI(ContactsContract.AUTHORITY, "contacts/lookup/*", 1001);
        matcher.addURI(ContactsContract.AUTHORITY, "contacts/lookup/*/#", 1000);
    }

    @UnsupportedAppUsage
    public static void startQuickContactWithErrorToast(Context context, Intent intent) {
        Uri uri = intent.getData();
        int match = sContactsUriMatcher.match(uri);
        switch (match) {
            case 1000:
            case 1001:
                if (maybeStartManagedQuickContact(context, intent)) {
                    return;
                }
                break;
        }
        startQuickContactWithErrorToastForUser(context, intent, context.getUser());
    }

    public static void startQuickContactWithErrorToastForUser(Context context, Intent intent, UserHandle user) {
        try {
            context.startActivityAsUser(intent, user);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, (int) C3132R.string.quick_contacts_not_available, 0).show();
        }
    }

    private static boolean maybeStartManagedQuickContact(Context context, Intent originalIntent) {
        long parseId;
        Uri uri = originalIntent.getData();
        List<String> pathSegments = uri.getPathSegments();
        boolean isContactIdIgnored = pathSegments.size() < 4;
        if (isContactIdIgnored) {
            parseId = ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE;
        } else {
            parseId = ContentUris.parseId(uri);
        }
        long contactId = parseId;
        String lookupKey = pathSegments.get(2);
        String directoryIdStr = uri.getQueryParameter(ContactsContract.DIRECTORY_PARAM_KEY);
        long directoryId = directoryIdStr == null ? 1000000000L : Long.parseLong(directoryIdStr);
        if (!TextUtils.isEmpty(lookupKey) && lookupKey.startsWith(ContactsContract.Contacts.ENTERPRISE_CONTACT_LOOKUP_PREFIX)) {
            if (!ContactsContract.Contacts.isEnterpriseContactId(contactId)) {
                throw new IllegalArgumentException("Invalid enterprise contact id: " + contactId);
            } else if (!ContactsContract.Directory.isEnterpriseDirectoryId(directoryId)) {
                throw new IllegalArgumentException("Invalid enterprise directory id: " + directoryId);
            } else {
                DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
                String actualLookupKey = lookupKey.substring(ContactsContract.Contacts.ENTERPRISE_CONTACT_LOOKUP_PREFIX.length());
                long actualContactId = contactId - ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE;
                long actualDirectoryId = directoryId - 1000000000;
                dpm.startManagedQuickContact(actualLookupKey, actualContactId, isContactIdIgnored, actualDirectoryId, originalIntent);
                return true;
            }
        }
        return false;
    }
}
