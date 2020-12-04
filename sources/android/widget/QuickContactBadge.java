package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.R;

public class QuickContactBadge extends ImageView implements View.OnClickListener {
    static final int EMAIL_ID_COLUMN_INDEX = 0;
    static final String[] EMAIL_LOOKUP_PROJECTION = {"contact_id", ContactsContract.ContactsColumns.LOOKUP_KEY};
    static final int EMAIL_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final String EXTRA_URI_CONTENT = "uri_content";
    static final int PHONE_ID_COLUMN_INDEX = 0;
    static final String[] PHONE_LOOKUP_PROJECTION = {"_id", ContactsContract.ContactsColumns.LOOKUP_KEY};
    static final int PHONE_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final int TOKEN_EMAIL_LOOKUP = 0;
    private static final int TOKEN_EMAIL_LOOKUP_AND_TRIGGER = 2;
    private static final int TOKEN_PHONE_LOOKUP = 1;
    private static final int TOKEN_PHONE_LOOKUP_AND_TRIGGER = 3;
    private String mContactEmail;
    private String mContactPhone;
    /* access modifiers changed from: private */
    public Uri mContactUri;
    private Drawable mDefaultAvatar;
    protected String[] mExcludeMimes;
    private Bundle mExtras;
    @UnsupportedAppUsage
    private Drawable mOverlay;
    /* access modifiers changed from: private */
    public String mPrioritizedMimeType;
    private QueryHandler mQueryHandler;

    public QuickContactBadge(Context context) {
        this(context, (AttributeSet) null);
    }

    public QuickContactBadge(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickContactBadge(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public QuickContactBadge(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mExtras = null;
        this.mExcludeMimes = null;
        TypedArray styledAttributes = this.mContext.obtainStyledAttributes(R.styleable.Theme);
        this.mOverlay = styledAttributes.getDrawable(325);
        styledAttributes.recycle();
        setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mQueryHandler = new QueryHandler(this.mContext.getContentResolver());
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable overlay = this.mOverlay;
        if (overlay != null && overlay.isStateful() && overlay.setState(getDrawableState())) {
            invalidateDrawable(overlay);
        }
    }

    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mOverlay != null) {
            this.mOverlay.setHotspot(x, y);
        }
    }

    public void setMode(int size) {
    }

    public void setPrioritizedMimeType(String prioritizedMimeType) {
        this.mPrioritizedMimeType = prioritizedMimeType;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isEnabled() && this.mOverlay != null && this.mOverlay.getIntrinsicWidth() != 0 && this.mOverlay.getIntrinsicHeight() != 0) {
            this.mOverlay.setBounds(0, 0, getWidth(), getHeight());
            if (this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
                this.mOverlay.draw(canvas);
                return;
            }
            int saveCount = canvas.getSaveCount();
            canvas.save();
            canvas.translate((float) this.mPaddingLeft, (float) this.mPaddingTop);
            this.mOverlay.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    private boolean isAssigned() {
        return (this.mContactUri == null && this.mContactEmail == null && this.mContactPhone == null) ? false : true;
    }

    public void setImageToDefault() {
        if (this.mDefaultAvatar == null) {
            this.mDefaultAvatar = this.mContext.getDrawable(R.drawable.ic_contact_picture);
        }
        setImageDrawable(this.mDefaultAvatar);
    }

    public void assignContactUri(Uri contactUri) {
        this.mContactUri = contactUri;
        this.mContactEmail = null;
        this.mContactPhone = null;
        onContactUriChanged();
    }

    public void assignContactFromEmail(String emailAddress, boolean lazyLookup) {
        assignContactFromEmail(emailAddress, lazyLookup, (Bundle) null);
    }

    public void assignContactFromEmail(String emailAddress, boolean lazyLookup, Bundle extras) {
        this.mContactEmail = emailAddress;
        this.mExtras = extras;
        if (lazyLookup || this.mQueryHandler == null) {
            this.mContactUri = null;
            onContactUriChanged();
            return;
        }
        this.mQueryHandler.startQuery(0, (Object) null, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, (String) null, (String[]) null, (String) null);
    }

    public void assignContactFromPhone(String phoneNumber, boolean lazyLookup) {
        assignContactFromPhone(phoneNumber, lazyLookup, new Bundle());
    }

    public void assignContactFromPhone(String phoneNumber, boolean lazyLookup, Bundle extras) {
        this.mContactPhone = phoneNumber;
        this.mExtras = extras;
        if (lazyLookup || this.mQueryHandler == null) {
            this.mContactUri = null;
            onContactUriChanged();
            return;
        }
        this.mQueryHandler.startQuery(1, (Object) null, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, (String) null, (String[]) null, (String) null);
    }

    public void setOverlay(Drawable overlay) {
        this.mOverlay = overlay;
    }

    /* access modifiers changed from: private */
    public void onContactUriChanged() {
        setEnabled(isAssigned());
    }

    public void onClick(View v) {
        Bundle extras = this.mExtras == null ? new Bundle() : this.mExtras;
        if (this.mContactUri != null) {
            ContactsContract.QuickContact.showQuickContact(getContext(), (View) this, this.mContactUri, this.mExcludeMimes, this.mPrioritizedMimeType);
        } else if (this.mContactEmail != null && this.mQueryHandler != null) {
            extras.putString(EXTRA_URI_CONTENT, this.mContactEmail);
            this.mQueryHandler.startQuery(2, extras, Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, (String) null, (String[]) null, (String) null);
        } else if (this.mContactPhone != null && this.mQueryHandler != null) {
            extras.putString(EXTRA_URI_CONTENT, this.mContactPhone);
            this.mQueryHandler.startQuery(3, extras, Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, (String) null, (String[]) null, (String) null);
        }
    }

    public CharSequence getAccessibilityClassName() {
        return QuickContactBadge.class.getName();
    }

    public void setExcludeMimes(String[] excludeMimes) {
        this.mExcludeMimes = excludeMimes;
    }

    private class QueryHandler extends AsyncQueryHandler {
        public QueryHandler(ContentResolver cr) {
            super(cr);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0039, code lost:
            if (r12 == null) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
            if (r12.moveToFirst() == false) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
            r0 = android.provider.ContactsContract.Contacts.getLookupUri(r12.getLong(0), r12.getString(1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
            if (r12 == null) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0055, code lost:
            if (r12.moveToFirst() == false) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0063, code lost:
            r0 = android.provider.ContactsContract.Contacts.getLookupUri(r12.getLong(0), r12.getString(1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x006b, code lost:
            if (r12 == null) goto L_0x0070;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x006d, code lost:
            r12.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0070, code lost:
            android.widget.QuickContactBadge.access$002(r9.this$0, r0);
            android.widget.QuickContactBadge.access$100(r9.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x007a, code lost:
            if (r2 == false) goto L_0x00a0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0082, code lost:
            if (android.widget.QuickContactBadge.access$000(r9.this$0) == null) goto L_0x00a0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0084, code lost:
            android.provider.ContactsContract.QuickContact.showQuickContact(r9.this$0.getContext(), (android.view.View) r9.this$0, android.widget.QuickContactBadge.access$000(r9.this$0), r9.this$0.mExcludeMimes, android.widget.QuickContactBadge.access$200(r9.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a0, code lost:
            if (r1 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a2, code lost:
            r4 = new android.content.Intent("com.android.contacts.action.SHOW_OR_CREATE_CONTACT", r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a9, code lost:
            if (r3 == null) goto L_0x00b4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ab, code lost:
            r3.remove(android.widget.QuickContactBadge.EXTRA_URI_CONTENT);
            r4.putExtras(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b4, code lost:
            r9.this$0.getContext().startActivity(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onQueryComplete(int r10, java.lang.Object r11, android.database.Cursor r12) {
            /*
                r9 = this;
                r0 = 0
                r1 = 0
                r2 = 0
                if (r11 == 0) goto L_0x0009
                r3 = r11
                android.os.Bundle r3 = (android.os.Bundle) r3
                goto L_0x000e
            L_0x0009:
                android.os.Bundle r3 = new android.os.Bundle
                r3.<init>()
            L_0x000e:
                r4 = 1
                r5 = 0
                r6 = 0
                switch(r10) {
                    case 0: goto L_0x004f;
                    case 1: goto L_0x0039;
                    case 2: goto L_0x0026;
                    case 3: goto L_0x0015;
                    default: goto L_0x0014;
                }
            L_0x0014:
                goto L_0x006b
            L_0x0015:
                r2 = 1
                java.lang.String r7 = "tel"
                java.lang.String r8 = "uri_content"
                java.lang.String r8 = r3.getString(r8)     // Catch:{ all -> 0x0037 }
                android.net.Uri r5 = android.net.Uri.fromParts(r7, r8, r5)     // Catch:{ all -> 0x0037 }
                r1 = r5
                goto L_0x0039
            L_0x0026:
                r2 = 1
                java.lang.String r7 = "mailto"
                java.lang.String r8 = "uri_content"
                java.lang.String r8 = r3.getString(r8)     // Catch:{ all -> 0x0037 }
                android.net.Uri r5 = android.net.Uri.fromParts(r7, r8, r5)     // Catch:{ all -> 0x0037 }
                r1 = r5
                goto L_0x004f
            L_0x0037:
                r4 = move-exception
                goto L_0x0065
            L_0x0039:
                if (r12 == 0) goto L_0x006b
                boolean r5 = r12.moveToFirst()     // Catch:{ all -> 0x0037 }
                if (r5 == 0) goto L_0x006b
                long r5 = r12.getLong(r6)     // Catch:{ all -> 0x0037 }
                java.lang.String r4 = r12.getString(r4)     // Catch:{ all -> 0x0037 }
                android.net.Uri r7 = android.provider.ContactsContract.Contacts.getLookupUri((long) r5, (java.lang.String) r4)     // Catch:{ all -> 0x0037 }
                r0 = r7
                goto L_0x006b
            L_0x004f:
                if (r12 == 0) goto L_0x006b
                boolean r5 = r12.moveToFirst()     // Catch:{ all -> 0x0037 }
                if (r5 == 0) goto L_0x006b
                long r5 = r12.getLong(r6)     // Catch:{ all -> 0x0037 }
                java.lang.String r4 = r12.getString(r4)     // Catch:{ all -> 0x0037 }
                android.net.Uri r7 = android.provider.ContactsContract.Contacts.getLookupUri((long) r5, (java.lang.String) r4)     // Catch:{ all -> 0x0037 }
                r0 = r7
                goto L_0x006b
            L_0x0065:
                if (r12 == 0) goto L_0x006a
                r12.close()
            L_0x006a:
                throw r4
            L_0x006b:
                if (r12 == 0) goto L_0x0070
                r12.close()
            L_0x0070:
                android.widget.QuickContactBadge r4 = android.widget.QuickContactBadge.this
                android.net.Uri unused = r4.mContactUri = r0
                android.widget.QuickContactBadge r4 = android.widget.QuickContactBadge.this
                r4.onContactUriChanged()
                if (r2 == 0) goto L_0x00a0
                android.widget.QuickContactBadge r4 = android.widget.QuickContactBadge.this
                android.net.Uri r4 = r4.mContactUri
                if (r4 == 0) goto L_0x00a0
                android.widget.QuickContactBadge r4 = android.widget.QuickContactBadge.this
                android.content.Context r4 = r4.getContext()
                android.widget.QuickContactBadge r5 = android.widget.QuickContactBadge.this
                android.widget.QuickContactBadge r6 = android.widget.QuickContactBadge.this
                android.net.Uri r6 = r6.mContactUri
                android.widget.QuickContactBadge r7 = android.widget.QuickContactBadge.this
                java.lang.String[] r7 = r7.mExcludeMimes
                android.widget.QuickContactBadge r8 = android.widget.QuickContactBadge.this
                java.lang.String r8 = r8.mPrioritizedMimeType
                android.provider.ContactsContract.QuickContact.showQuickContact((android.content.Context) r4, (android.view.View) r5, (android.net.Uri) r6, (java.lang.String[]) r7, (java.lang.String) r8)
                goto L_0x00bd
            L_0x00a0:
                if (r1 == 0) goto L_0x00bd
                android.content.Intent r4 = new android.content.Intent
                java.lang.String r5 = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT"
                r4.<init>((java.lang.String) r5, (android.net.Uri) r1)
                if (r3 == 0) goto L_0x00b4
                java.lang.String r5 = "uri_content"
                r3.remove(r5)
                r4.putExtras((android.os.Bundle) r3)
            L_0x00b4:
                android.widget.QuickContactBadge r5 = android.widget.QuickContactBadge.this
                android.content.Context r5 = r5.getContext()
                r5.startActivity(r4)
            L_0x00bd:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.QuickContactBadge.QueryHandler.onQueryComplete(int, java.lang.Object, android.database.Cursor):void");
        }
    }
}
