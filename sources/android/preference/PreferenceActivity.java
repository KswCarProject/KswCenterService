package android.preference;

import android.animation.LayoutTransition;
import android.annotation.UnsupportedAppUsage;
import android.app.Fragment;
import android.app.FragmentBreadCrumbs;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class PreferenceActivity extends ListActivity implements PreferenceManager.OnPreferenceTreeClickListener, PreferenceFragment.OnPreferenceStartFragmentCallback {
    private static final String BACK_STACK_PREFS = ":android:prefs";
    private static final String CUR_HEADER_TAG = ":android:cur_header";
    public static final String EXTRA_NO_HEADERS = ":android:no_headers";
    private static final String EXTRA_PREFS_SET_BACK_TEXT = "extra_prefs_set_back_text";
    private static final String EXTRA_PREFS_SET_NEXT_TEXT = "extra_prefs_set_next_text";
    private static final String EXTRA_PREFS_SHOW_BUTTON_BAR = "extra_prefs_show_button_bar";
    private static final String EXTRA_PREFS_SHOW_SKIP = "extra_prefs_show_skip";
    public static final String EXTRA_SHOW_FRAGMENT = ":android:show_fragment";
    public static final String EXTRA_SHOW_FRAGMENT_ARGUMENTS = ":android:show_fragment_args";
    public static final String EXTRA_SHOW_FRAGMENT_SHORT_TITLE = ":android:show_fragment_short_title";
    public static final String EXTRA_SHOW_FRAGMENT_TITLE = ":android:show_fragment_title";
    private static final int FIRST_REQUEST_CODE = 100;
    private static final String HEADERS_TAG = ":android:headers";
    public static final long HEADER_ID_UNDEFINED = -1;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final int MSG_BUILD_HEADERS = 2;
    private static final String PREFERENCES_TAG = ":android:preferences";
    private static final String TAG = "PreferenceActivity";
    private CharSequence mActivityTitle;
    /* access modifiers changed from: private */
    public Header mCurHeader;
    private FragmentBreadCrumbs mFragmentBreadCrumbs;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Header mappedHeader;
            switch (msg.what) {
                case 1:
                    PreferenceActivity.this.bindPreferences();
                    return;
                case 2:
                    ArrayList<Header> oldHeaders = new ArrayList<>(PreferenceActivity.this.mHeaders);
                    PreferenceActivity.this.mHeaders.clear();
                    PreferenceActivity.this.onBuildHeaders(PreferenceActivity.this.mHeaders);
                    if (PreferenceActivity.this.mAdapter instanceof BaseAdapter) {
                        ((BaseAdapter) PreferenceActivity.this.mAdapter).notifyDataSetChanged();
                    }
                    Header header = PreferenceActivity.this.onGetNewHeader();
                    if (header != null && header.fragment != null) {
                        Header mappedHeader2 = PreferenceActivity.this.findBestMatchingHeader(header, oldHeaders);
                        if (mappedHeader2 == null || PreferenceActivity.this.mCurHeader != mappedHeader2) {
                            PreferenceActivity.this.switchToHeader(header);
                            return;
                        }
                        return;
                    } else if (PreferenceActivity.this.mCurHeader != null && (mappedHeader = PreferenceActivity.this.findBestMatchingHeader(PreferenceActivity.this.mCurHeader, PreferenceActivity.this.mHeaders)) != null) {
                        PreferenceActivity.this.setSelectedHeader(mappedHeader);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public final ArrayList<Header> mHeaders = new ArrayList<>();
    private ViewGroup mHeadersContainer;
    private FrameLayout mListFooter;
    private Button mNextButton;
    private int mPreferenceHeaderItemResId = 0;
    private boolean mPreferenceHeaderRemoveEmptyIcon = false;
    @UnsupportedAppUsage
    private PreferenceManager mPreferenceManager;
    @UnsupportedAppUsage
    private ViewGroup mPrefsContainer;
    private Bundle mSavedInstanceState;
    private boolean mSinglePane;

    private static class HeaderAdapter extends ArrayAdapter<Header> {
        private LayoutInflater mInflater;
        private int mLayoutResId;
        private boolean mRemoveIconIfEmpty;

        private static class HeaderViewHolder {
            ImageView icon;
            TextView summary;
            TextView title;

            private HeaderViewHolder() {
            }
        }

        public HeaderAdapter(Context context, List<Header> objects, int layoutResId, boolean removeIconBehavior) {
            super(context, 0, objects);
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mLayoutResId = layoutResId;
            this.mRemoveIconIfEmpty = removeIconBehavior;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            HeaderViewHolder holder;
            View view;
            if (convertView == null) {
                view = this.mInflater.inflate(this.mLayoutResId, parent, false);
                holder = new HeaderViewHolder();
                holder.icon = (ImageView) view.findViewById(16908294);
                holder.title = (TextView) view.findViewById(16908310);
                holder.summary = (TextView) view.findViewById(16908304);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (HeaderViewHolder) view.getTag();
            }
            Header header = (Header) getItem(position);
            if (!this.mRemoveIconIfEmpty) {
                holder.icon.setImageResource(header.iconRes);
            } else if (header.iconRes == 0) {
                holder.icon.setVisibility(8);
            } else {
                holder.icon.setVisibility(0);
                holder.icon.setImageResource(header.iconRes);
            }
            holder.title.setText(header.getTitle(getContext().getResources()));
            CharSequence summary = header.getSummary(getContext().getResources());
            if (!TextUtils.isEmpty(summary)) {
                holder.summary.setVisibility(0);
                holder.summary.setText(summary);
            } else {
                holder.summary.setVisibility(8);
            }
            return view;
        }
    }

    @Deprecated
    public static final class Header implements Parcelable {
        public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>() {
            public Header createFromParcel(Parcel source) {
                return new Header(source);
            }

            public Header[] newArray(int size) {
                return new Header[size];
            }
        };
        public CharSequence breadCrumbShortTitle;
        public int breadCrumbShortTitleRes;
        public CharSequence breadCrumbTitle;
        public int breadCrumbTitleRes;
        public Bundle extras;
        public String fragment;
        public Bundle fragmentArguments;
        public int iconRes;
        public long id = -1;
        public Intent intent;
        public CharSequence summary;
        public int summaryRes;
        public CharSequence title;
        public int titleRes;

        public Header() {
        }

        public CharSequence getTitle(Resources res) {
            if (this.titleRes != 0) {
                return res.getText(this.titleRes);
            }
            return this.title;
        }

        public CharSequence getSummary(Resources res) {
            if (this.summaryRes != 0) {
                return res.getText(this.summaryRes);
            }
            return this.summary;
        }

        public CharSequence getBreadCrumbTitle(Resources res) {
            if (this.breadCrumbTitleRes != 0) {
                return res.getText(this.breadCrumbTitleRes);
            }
            return this.breadCrumbTitle;
        }

        public CharSequence getBreadCrumbShortTitle(Resources res) {
            if (this.breadCrumbShortTitleRes != 0) {
                return res.getText(this.breadCrumbShortTitleRes);
            }
            return this.breadCrumbShortTitle;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeInt(this.titleRes);
            TextUtils.writeToParcel(this.title, dest, flags);
            dest.writeInt(this.summaryRes);
            TextUtils.writeToParcel(this.summary, dest, flags);
            dest.writeInt(this.breadCrumbTitleRes);
            TextUtils.writeToParcel(this.breadCrumbTitle, dest, flags);
            dest.writeInt(this.breadCrumbShortTitleRes);
            TextUtils.writeToParcel(this.breadCrumbShortTitle, dest, flags);
            dest.writeInt(this.iconRes);
            dest.writeString(this.fragment);
            dest.writeBundle(this.fragmentArguments);
            if (this.intent != null) {
                dest.writeInt(1);
                this.intent.writeToParcel(dest, flags);
            } else {
                dest.writeInt(0);
            }
            dest.writeBundle(this.extras);
        }

        public void readFromParcel(Parcel in) {
            this.id = in.readLong();
            this.titleRes = in.readInt();
            this.title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.summaryRes = in.readInt();
            this.summary = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.breadCrumbTitleRes = in.readInt();
            this.breadCrumbTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.breadCrumbShortTitleRes = in.readInt();
            this.breadCrumbShortTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.iconRes = in.readInt();
            this.fragment = in.readString();
            this.fragmentArguments = in.readBundle();
            if (in.readInt() != 0) {
                this.intent = Intent.CREATOR.createFromParcel(in);
            }
            this.extras = in.readBundle();
        }

        Header(Parcel in) {
            readFromParcel(in);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = savedInstanceState;
        super.onCreate(savedInstanceState);
        TypedArray sa = obtainStyledAttributes((AttributeSet) null, R.styleable.PreferenceActivity, R.attr.preferenceActivityStyle, 0);
        int layoutResId = sa.getResourceId(0, R.layout.preference_list_content);
        this.mPreferenceHeaderItemResId = sa.getResourceId(1, R.layout.preference_header_item);
        this.mPreferenceHeaderRemoveEmptyIcon = sa.getBoolean(2, false);
        sa.recycle();
        setContentView(layoutResId);
        this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
        this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs_frame);
        this.mHeadersContainer = (ViewGroup) findViewById(R.id.headers);
        this.mSinglePane = onIsHidingHeaders() || !onIsMultiPane();
        String initialFragment = getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT);
        Bundle initialArguments = getIntent().getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS);
        int initialTitle = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_TITLE, 0);
        int initialShortTitle = getIntent().getIntExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, 0);
        this.mActivityTitle = getTitle();
        if (bundle != null) {
            ArrayList<Header> headers = bundle.getParcelableArrayList(HEADERS_TAG);
            if (headers != null) {
                this.mHeaders.addAll(headers);
                int curHeader = bundle.getInt(CUR_HEADER_TAG, -1);
                if (curHeader >= 0 && curHeader < this.mHeaders.size()) {
                    setSelectedHeader(this.mHeaders.get(curHeader));
                } else if (!this.mSinglePane && initialFragment == null) {
                    switchToHeader(onGetInitialHeader());
                }
            } else {
                showBreadCrumbs(getTitle(), (CharSequence) null);
            }
        } else {
            if (!onIsHidingHeaders()) {
                onBuildHeaders(this.mHeaders);
            }
            if (initialFragment != null) {
                switchToHeader(initialFragment, initialArguments);
            } else if (!this.mSinglePane && this.mHeaders.size() > 0) {
                switchToHeader(onGetInitialHeader());
            }
        }
        if (this.mHeaders.size() > 0) {
            setListAdapter(new HeaderAdapter(this, this.mHeaders, this.mPreferenceHeaderItemResId, this.mPreferenceHeaderRemoveEmptyIcon));
            if (!this.mSinglePane) {
                getListView().setChoiceMode(1);
            }
        }
        if (!(!this.mSinglePane || initialFragment == null || initialTitle == 0)) {
            showBreadCrumbs(getText(initialTitle), initialShortTitle != 0 ? getText(initialShortTitle) : null);
        }
        if (this.mHeaders.size() == 0 && initialFragment == null) {
            setContentView((int) R.layout.preference_list_content_single);
            this.mListFooter = (FrameLayout) findViewById(R.id.list_footer);
            this.mPrefsContainer = (ViewGroup) findViewById(R.id.prefs);
            this.mPreferenceManager = new PreferenceManager(this, 100);
            this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
            this.mHeadersContainer = null;
        } else if (this.mSinglePane) {
            if (initialFragment == null && this.mCurHeader == null) {
                this.mPrefsContainer.setVisibility(8);
            } else {
                this.mHeadersContainer.setVisibility(8);
            }
            ((ViewGroup) findViewById(R.id.prefs_container)).setLayoutTransition(new LayoutTransition());
        } else if (this.mHeaders.size() > 0 && this.mCurHeader != null) {
            setSelectedHeader(this.mCurHeader);
        }
        Intent intent = getIntent();
        if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_BUTTON_BAR, false)) {
            findViewById(R.id.button_bar).setVisibility(0);
            Button backButton = (Button) findViewById(R.id.back_button);
            backButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(0);
                    PreferenceActivity.this.finish();
                }
            });
            Button skipButton = (Button) findViewById(R.id.skip_button);
            skipButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            this.mNextButton = (Button) findViewById(R.id.next_button);
            this.mNextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    PreferenceActivity.this.setResult(-1);
                    PreferenceActivity.this.finish();
                }
            });
            if (intent.hasExtra(EXTRA_PREFS_SET_NEXT_TEXT)) {
                String buttonText = intent.getStringExtra(EXTRA_PREFS_SET_NEXT_TEXT);
                if (TextUtils.isEmpty(buttonText)) {
                    this.mNextButton.setVisibility(8);
                } else {
                    this.mNextButton.setText((CharSequence) buttonText);
                }
            }
            if (intent.hasExtra(EXTRA_PREFS_SET_BACK_TEXT)) {
                String buttonText2 = intent.getStringExtra(EXTRA_PREFS_SET_BACK_TEXT);
                if (TextUtils.isEmpty(buttonText2)) {
                    backButton.setVisibility(8);
                } else {
                    backButton.setText((CharSequence) buttonText2);
                }
            }
            if (intent.getBooleanExtra(EXTRA_PREFS_SHOW_SKIP, false)) {
                skipButton.setVisibility(0);
            }
        }
    }

    public void onBackPressed() {
        if (this.mCurHeader == null || !this.mSinglePane || getFragmentManager().getBackStackEntryCount() != 0 || getIntent().getStringExtra(EXTRA_SHOW_FRAGMENT) != null) {
            super.onBackPressed();
            return;
        }
        this.mCurHeader = null;
        this.mPrefsContainer.setVisibility(8);
        this.mHeadersContainer.setVisibility(0);
        if (this.mActivityTitle != null) {
            showBreadCrumbs(this.mActivityTitle, (CharSequence) null);
        }
        getListView().clearChoices();
    }

    public boolean hasHeaders() {
        return this.mHeadersContainer != null && this.mHeadersContainer.getVisibility() == 0;
    }

    @UnsupportedAppUsage
    public List<Header> getHeaders() {
        return this.mHeaders;
    }

    public boolean isMultiPane() {
        return !this.mSinglePane;
    }

    public boolean onIsMultiPane() {
        return getResources().getBoolean(R.bool.preferences_prefer_dual_pane);
    }

    public boolean onIsHidingHeaders() {
        return getIntent().getBooleanExtra(EXTRA_NO_HEADERS, false);
    }

    public Header onGetInitialHeader() {
        for (int i = 0; i < this.mHeaders.size(); i++) {
            Header h = this.mHeaders.get(i);
            if (h.fragment != null) {
                return h;
            }
        }
        throw new IllegalStateException("Must have at least one header with a fragment");
    }

    public Header onGetNewHeader() {
        return null;
    }

    public void onBuildHeaders(List<Header> list) {
    }

    public void invalidateHeaders() {
        if (!this.mHandler.hasMessages(2)) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:127:0x01bf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void loadHeadersFromResource(int r17, java.util.List<android.preference.PreferenceActivity.Header> r18) {
        /*
            r16 = this;
            r0 = 0
            r1 = r0
            android.content.res.Resources r0 = r16.getResources()     // Catch:{ XmlPullParserException -> 0x01ad, IOException -> 0x019e, all -> 0x0196 }
            r2 = r17
            android.content.res.XmlResourceParser r0 = r0.getXml(r2)     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            r1 = r0
            android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r1)     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
        L_0x0011:
            int r3 = r1.next()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            r4 = r3
            r5 = 2
            r6 = 1
            if (r3 == r6) goto L_0x001d
            if (r4 == r5) goto L_0x001d
            goto L_0x0011
        L_0x001d:
            java.lang.String r3 = r1.getName()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            java.lang.String r7 = "preference-headers"
            boolean r7 = r7.equals(r3)     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            if (r7 == 0) goto L_0x015f
            r7 = 0
            int r8 = r1.getDepth()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
        L_0x002f:
            int r9 = r1.next()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            r4 = r9
            if (r9 == r6) goto L_0x0155
            r9 = 3
            if (r4 != r9) goto L_0x003f
            int r10 = r1.getDepth()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            if (r10 <= r8) goto L_0x0155
        L_0x003f:
            if (r4 == r9) goto L_0x014d
            r10 = 4
            if (r4 != r10) goto L_0x0046
            goto L_0x014d
        L_0x0046:
            java.lang.String r11 = r1.getName()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            r3 = r11
            java.lang.String r11 = "header"
            boolean r11 = r11.equals(r3)     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            if (r11 == 0) goto L_0x0145
            android.preference.PreferenceActivity$Header r11 = new android.preference.PreferenceActivity$Header     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            r11.<init>()     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            int[] r12 = com.android.internal.R.styleable.PreferenceHeader     // Catch:{ XmlPullParserException -> 0x0192, IOException -> 0x018e, all -> 0x018a }
            r13 = r16
            android.content.res.TypedArray r12 = r13.obtainStyledAttributes((android.util.AttributeSet) r0, (int[]) r12)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r14 = -1
            int r14 = r12.getResourceId(r6, r14)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            long r14 = (long) r14     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.id = r14     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            android.util.TypedValue r14 = r12.peekValue(r5)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r14 == 0) goto L_0x007f
            int r15 = r14.type     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 != r9) goto L_0x007f
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 == 0) goto L_0x007b
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.titleRes = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            goto L_0x007f
        L_0x007b:
            java.lang.CharSequence r15 = r14.string     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.title = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
        L_0x007f:
            android.util.TypedValue r15 = r12.peekValue(r9)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r14 = r15
            if (r14 == 0) goto L_0x0097
            int r15 = r14.type     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 != r9) goto L_0x0097
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 == 0) goto L_0x0093
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.summaryRes = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            goto L_0x0097
        L_0x0093:
            java.lang.CharSequence r15 = r14.string     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.summary = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
        L_0x0097:
            r15 = 5
            android.util.TypedValue r15 = r12.peekValue(r15)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r14 = r15
            if (r14 == 0) goto L_0x00b0
            int r15 = r14.type     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 != r9) goto L_0x00b0
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 == 0) goto L_0x00ac
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.breadCrumbTitleRes = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            goto L_0x00b0
        L_0x00ac:
            java.lang.CharSequence r15 = r14.string     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.breadCrumbTitle = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
        L_0x00b0:
            r15 = 6
            android.util.TypedValue r15 = r12.peekValue(r15)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r14 = r15
            if (r14 == 0) goto L_0x00c9
            int r15 = r14.type     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 != r9) goto L_0x00c9
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r15 == 0) goto L_0x00c5
            int r15 = r14.resourceId     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.breadCrumbShortTitleRes = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            goto L_0x00c9
        L_0x00c5:
            java.lang.CharSequence r15 = r14.string     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.breadCrumbShortTitle = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
        L_0x00c9:
            r15 = 0
            int r15 = r12.getResourceId(r15, r15)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.iconRes = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            java.lang.String r15 = r12.getString(r10)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.fragment = r15     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r12.recycle()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r7 != 0) goto L_0x00e1
            android.os.Bundle r15 = new android.os.Bundle     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r15.<init>()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r7 = r15
        L_0x00e1:
            int r15 = r1.getDepth()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
        L_0x00e5:
            int r5 = r1.next()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r4 = r5
            if (r5 == r6) goto L_0x012d
            if (r4 != r9) goto L_0x00f4
            int r5 = r1.getDepth()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r5 <= r15) goto L_0x012d
        L_0x00f4:
            if (r4 == r9) goto L_0x0129
            if (r4 != r10) goto L_0x00f9
            goto L_0x0129
        L_0x00f9:
            java.lang.String r5 = r1.getName()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            java.lang.String r6 = "extra"
            boolean r6 = r5.equals(r6)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r6 == 0) goto L_0x0112
            android.content.res.Resources r6 = r16.getResources()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            java.lang.String r9 = "extra"
            r6.parseBundleExtra(r9, r0, r7)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            com.android.internal.util.XmlUtils.skipCurrentTag(r1)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            goto L_0x0128
        L_0x0112:
            java.lang.String r6 = "intent"
            boolean r6 = r5.equals(r6)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r6 == 0) goto L_0x0125
            android.content.res.Resources r6 = r16.getResources()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            android.content.Intent r6 = android.content.Intent.parseIntent(r6, r1, r0)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r11.intent = r6     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            goto L_0x0128
        L_0x0125:
            com.android.internal.util.XmlUtils.skipCurrentTag(r1)     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
        L_0x0128:
        L_0x0129:
            r5 = 2
            r6 = 1
            r9 = 3
            goto L_0x00e5
        L_0x012d:
            int r5 = r7.size()     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            if (r5 <= 0) goto L_0x0137
            r11.fragmentArguments = r7     // Catch:{ XmlPullParserException -> 0x0142, IOException -> 0x013f, all -> 0x013d }
            r5 = 0
            r7 = r5
        L_0x0137:
            r5 = r18
            r5.add(r11)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            goto L_0x0151
        L_0x013d:
            r0 = move-exception
            goto L_0x019b
        L_0x013f:
            r0 = move-exception
            goto L_0x01a3
        L_0x0142:
            r0 = move-exception
            goto L_0x01b2
        L_0x0145:
            r13 = r16
            r5 = r18
            com.android.internal.util.XmlUtils.skipCurrentTag(r1)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            goto L_0x0151
        L_0x014d:
            r13 = r16
            r5 = r18
        L_0x0151:
            r5 = 2
            r6 = 1
            goto L_0x002f
        L_0x0155:
            r13 = r16
            r5 = r18
            if (r1 == 0) goto L_0x015e
            r1.close()
        L_0x015e:
            return
        L_0x015f:
            r13 = r16
            r5 = r18
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            r7.<init>()     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            java.lang.String r8 = "XML document must start with <preference-headers> tag; found"
            r7.append(r8)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            r7.append(r3)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            java.lang.String r8 = " at "
            r7.append(r8)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            java.lang.String r8 = r1.getPositionDescription()     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            r7.append(r8)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            java.lang.String r7 = r7.toString()     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            r6.<init>(r7)     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
            throw r6     // Catch:{ XmlPullParserException -> 0x0188, IOException -> 0x0186 }
        L_0x0186:
            r0 = move-exception
            goto L_0x01a5
        L_0x0188:
            r0 = move-exception
            goto L_0x01b4
        L_0x018a:
            r0 = move-exception
            r13 = r16
            goto L_0x019b
        L_0x018e:
            r0 = move-exception
            r13 = r16
            goto L_0x01a3
        L_0x0192:
            r0 = move-exception
            r13 = r16
            goto L_0x01b2
        L_0x0196:
            r0 = move-exception
            r13 = r16
            r2 = r17
        L_0x019b:
            r5 = r18
            goto L_0x01bd
        L_0x019e:
            r0 = move-exception
            r13 = r16
            r2 = r17
        L_0x01a3:
            r5 = r18
        L_0x01a5:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x01bc }
            java.lang.String r4 = "Error parsing headers"
            r3.<init>(r4, r0)     // Catch:{ all -> 0x01bc }
            throw r3     // Catch:{ all -> 0x01bc }
        L_0x01ad:
            r0 = move-exception
            r13 = r16
            r2 = r17
        L_0x01b2:
            r5 = r18
        L_0x01b4:
            java.lang.RuntimeException r3 = new java.lang.RuntimeException     // Catch:{ all -> 0x01bc }
            java.lang.String r4 = "Error parsing headers"
            r3.<init>(r4, r0)     // Catch:{ all -> 0x01bc }
            throw r3     // Catch:{ all -> 0x01bc }
        L_0x01bc:
            r0 = move-exception
        L_0x01bd:
            if (r1 == 0) goto L_0x01c2
            r1.close()
        L_0x01c2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceActivity.loadHeadersFromResource(int, java.util.List):void");
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragmentName) {
        if (getApplicationInfo().targetSdkVersion < 19) {
            return true;
        }
        throw new RuntimeException("Subclasses of PreferenceActivity must override isValidFragment(String) to verify that the Fragment class is valid! " + getClass().getName() + " has not checked if fragment " + fragmentName + " is valid.");
    }

    public void setListFooter(View view) {
        this.mListFooter.removeAllViews();
        this.mListFooter.addView(view, (ViewGroup.LayoutParams) new FrameLayout.LayoutParams(-1, -2));
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityStop();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        super.onDestroy();
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityDestroy();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        PreferenceScreen preferenceScreen;
        int index;
        super.onSaveInstanceState(outState);
        if (this.mHeaders.size() > 0) {
            outState.putParcelableArrayList(HEADERS_TAG, this.mHeaders);
            if (this.mCurHeader != null && (index = this.mHeaders.indexOf(this.mCurHeader)) >= 0) {
                outState.putInt(CUR_HEADER_TAG, index);
            }
        }
        if (this.mPreferenceManager != null && (preferenceScreen = getPreferenceScreen()) != null) {
            Bundle container = new Bundle();
            preferenceScreen.saveHierarchyState(container);
            outState.putBundle(PREFERENCES_TAG, container);
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle state) {
        Bundle container;
        PreferenceScreen preferenceScreen;
        if (this.mPreferenceManager == null || (container = state.getBundle(PREFERENCES_TAG)) == null || (preferenceScreen = getPreferenceScreen()) == null) {
            super.onRestoreInstanceState(state);
            if (!this.mSinglePane && this.mCurHeader != null) {
                setSelectedHeader(this.mCurHeader);
                return;
            }
            return;
        }
        preferenceScreen.restoreHierarchyState(container);
        this.mSavedInstanceState = state;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchActivityResult(requestCode, resultCode, data);
        }
    }

    public void onContentChanged() {
        super.onContentChanged();
        if (this.mPreferenceManager != null) {
            postBindPreferences();
        }
    }

    /* access modifiers changed from: protected */
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (isResumed()) {
            super.onListItemClick(l, v, position, id);
            if (this.mAdapter != null) {
                Object item = this.mAdapter.getItem(position);
                if (item instanceof Header) {
                    onHeaderClick((Header) item, position);
                }
            }
        }
    }

    public void onHeaderClick(Header header, int position) {
        if (header.fragment != null) {
            switchToHeader(header);
        } else if (header.intent != null) {
            startActivity(header.intent);
        }
    }

    public Intent onBuildStartFragmentIntent(String fragmentName, Bundle args, int titleRes, int shortTitleRes) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(this, getClass());
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, titleRes);
        intent.putExtra(EXTRA_SHOW_FRAGMENT_SHORT_TITLE, shortTitleRes);
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    public void startWithFragment(String fragmentName, Bundle args, Fragment resultTo, int resultRequestCode) {
        startWithFragment(fragmentName, args, resultTo, resultRequestCode, 0, 0);
    }

    public void startWithFragment(String fragmentName, Bundle args, Fragment resultTo, int resultRequestCode, int titleRes, int shortTitleRes) {
        Intent intent = onBuildStartFragmentIntent(fragmentName, args, titleRes, shortTitleRes);
        if (resultTo == null) {
            startActivity(intent);
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode);
        }
    }

    public void showBreadCrumbs(CharSequence title, CharSequence shortTitle) {
        if (this.mFragmentBreadCrumbs == null) {
            try {
                this.mFragmentBreadCrumbs = (FragmentBreadCrumbs) findViewById(16908310);
                if (this.mFragmentBreadCrumbs != null) {
                    if (this.mSinglePane) {
                        this.mFragmentBreadCrumbs.setVisibility(8);
                        View bcSection = findViewById(R.id.breadcrumb_section);
                        if (bcSection != null) {
                            bcSection.setVisibility(8);
                        }
                        setTitle(title);
                    }
                    this.mFragmentBreadCrumbs.setMaxVisible(2);
                    this.mFragmentBreadCrumbs.setActivity(this);
                } else if (title != null) {
                    setTitle(title);
                    return;
                } else {
                    return;
                }
            } catch (ClassCastException e) {
                setTitle(title);
                return;
            }
        }
        if (this.mFragmentBreadCrumbs.getVisibility() != 0) {
            setTitle(title);
            return;
        }
        this.mFragmentBreadCrumbs.setTitle(title, shortTitle);
        this.mFragmentBreadCrumbs.setParentTitle((CharSequence) null, (CharSequence) null, (View.OnClickListener) null);
    }

    public void setParentTitle(CharSequence title, CharSequence shortTitle, View.OnClickListener listener) {
        if (this.mFragmentBreadCrumbs != null) {
            this.mFragmentBreadCrumbs.setParentTitle(title, shortTitle, listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void setSelectedHeader(Header header) {
        this.mCurHeader = header;
        int index = this.mHeaders.indexOf(header);
        if (index >= 0) {
            getListView().setItemChecked(index, true);
        } else {
            getListView().clearChoices();
        }
        showBreadCrumbs(header);
    }

    /* access modifiers changed from: package-private */
    public void showBreadCrumbs(Header header) {
        if (header != null) {
            CharSequence title = header.getBreadCrumbTitle(getResources());
            if (title == null) {
                title = header.getTitle(getResources());
            }
            if (title == null) {
                title = getTitle();
            }
            showBreadCrumbs(title, header.getBreadCrumbShortTitle(getResources()));
            return;
        }
        showBreadCrumbs(getTitle(), (CharSequence) null);
    }

    private void switchToHeaderInner(String fragmentName, Bundle args) {
        int i;
        getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        if (isValidFragment(fragmentName)) {
            Fragment f = Fragment.instantiate(this, fragmentName, args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (this.mSinglePane) {
                i = 0;
            } else {
                i = 4099;
            }
            transaction.setTransition(i);
            transaction.replace(R.id.prefs, f);
            transaction.commitAllowingStateLoss();
            if (this.mSinglePane && this.mPrefsContainer.getVisibility() == 8) {
                this.mPrefsContainer.setVisibility(0);
                this.mHeadersContainer.setVisibility(8);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Invalid fragment for this activity: " + fragmentName);
    }

    public void switchToHeader(String fragmentName, Bundle args) {
        Header selectedHeader = null;
        int i = 0;
        while (true) {
            if (i >= this.mHeaders.size()) {
                break;
            } else if (fragmentName.equals(this.mHeaders.get(i).fragment)) {
                selectedHeader = this.mHeaders.get(i);
                break;
            } else {
                i++;
            }
        }
        setSelectedHeader(selectedHeader);
        switchToHeaderInner(fragmentName, args);
    }

    public void switchToHeader(Header header) {
        if (this.mCurHeader == header) {
            getFragmentManager().popBackStack(BACK_STACK_PREFS, 1);
        } else if (header.fragment != null) {
            switchToHeaderInner(header.fragment, header.fragmentArguments);
            setSelectedHeader(header);
        } else {
            throw new IllegalStateException("can't switch to header that has no fragment");
        }
    }

    /* access modifiers changed from: package-private */
    public Header findBestMatchingHeader(Header cur, ArrayList<Header> from) {
        Header oh;
        ArrayList<Header> matches = new ArrayList<>();
        int j = 0;
        while (true) {
            if (j >= from.size()) {
                break;
            }
            oh = from.get(j);
            if (cur == oh || (cur.id != -1 && cur.id == oh.id)) {
                matches.clear();
                matches.add(oh);
            } else {
                if (cur.fragment != null) {
                    if (cur.fragment.equals(oh.fragment)) {
                        matches.add(oh);
                    }
                } else if (cur.intent != null) {
                    if (cur.intent.equals(oh.intent)) {
                        matches.add(oh);
                    }
                } else if (cur.title != null && cur.title.equals(oh.title)) {
                    matches.add(oh);
                }
                j++;
            }
        }
        matches.clear();
        matches.add(oh);
        int NM = matches.size();
        if (NM == 1) {
            return matches.get(0);
        }
        if (NM <= 1) {
            return null;
        }
        for (int j2 = 0; j2 < NM; j2++) {
            Header oh2 = matches.get(j2);
            if (cur.fragmentArguments != null && cur.fragmentArguments.equals(oh2.fragmentArguments)) {
                return oh2;
            }
            if (cur.extras != null && cur.extras.equals(oh2.extras)) {
                return oh2;
            }
            if (cur.title != null && cur.title.equals(oh2.title)) {
                return oh2;
            }
        }
        return null;
    }

    public void startPreferenceFragment(Fragment fragment, boolean push) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.prefs, fragment);
        if (push) {
            transaction.setTransition(4097);
            transaction.addToBackStack(BACK_STACK_PREFS);
        } else {
            transaction.setTransition(4099);
        }
        transaction.commitAllowingStateLoss();
    }

    public void startPreferencePanel(String fragmentClass, Bundle args, int titleRes, CharSequence titleText, Fragment resultTo, int resultRequestCode) {
        Fragment f = Fragment.instantiate(this, fragmentClass, args);
        if (resultTo != null) {
            f.setTargetFragment(resultTo, resultRequestCode);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.prefs, f);
        if (titleRes != 0) {
            transaction.setBreadCrumbTitle(titleRes);
        } else if (titleText != null) {
            transaction.setBreadCrumbTitle(titleText);
        }
        transaction.setTransition(4097);
        transaction.addToBackStack(BACK_STACK_PREFS);
        transaction.commitAllowingStateLoss();
    }

    public void finishPreferencePanel(Fragment caller, int resultCode, Intent resultData) {
        onBackPressed();
        if (caller != null && caller.getTargetFragment() != null) {
            caller.getTargetFragment().onActivityResult(caller.getTargetRequestCode(), resultCode, resultData);
        }
    }

    public boolean onPreferenceStartFragment(PreferenceFragment caller, Preference pref) {
        startPreferencePanel(pref.getFragment(), pref.getExtras(), pref.getTitleRes(), pref.getTitle(), (Fragment) null, 0);
        return true;
    }

    @UnsupportedAppUsage
    private void postBindPreferences() {
        if (!this.mHandler.hasMessages(1)) {
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    public void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            preferenceScreen.bind(getListView());
            if (this.mSavedInstanceState != null) {
                super.onRestoreInstanceState(this.mSavedInstanceState);
                this.mSavedInstanceState = null;
            }
        }
    }

    @Deprecated
    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    @UnsupportedAppUsage
    private void requirePreferenceManager() {
        if (this.mPreferenceManager != null) {
            return;
        }
        if (this.mAdapter == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
        throw new RuntimeException("Modern two-pane PreferenceActivity requires use of a PreferenceFragment");
    }

    @Deprecated
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        requirePreferenceManager();
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            postBindPreferences();
            CharSequence title = getPreferenceScreen().getTitle();
            if (title != null) {
                setTitle(title);
            }
        }
    }

    @Deprecated
    public PreferenceScreen getPreferenceScreen() {
        if (this.mPreferenceManager != null) {
            return this.mPreferenceManager.getPreferenceScreen();
        }
        return null;
    }

    @Deprecated
    public void addPreferencesFromIntent(Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    @Deprecated
    public void addPreferencesFromResource(int preferencesResId) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this, preferencesResId, getPreferenceScreen()));
    }

    @Deprecated
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return false;
    }

    @Deprecated
    public Preference findPreference(CharSequence key) {
        if (this.mPreferenceManager == null) {
            return null;
        }
        return this.mPreferenceManager.findPreference(key);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        if (this.mPreferenceManager != null) {
            this.mPreferenceManager.dispatchNewIntent(intent);
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasNextButton() {
        return this.mNextButton != null;
    }

    /* access modifiers changed from: protected */
    public Button getNextButton() {
        return this.mNextButton;
    }
}
