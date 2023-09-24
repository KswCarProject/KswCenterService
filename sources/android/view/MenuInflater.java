package android.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.BlendMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MenuItem;
import com.android.internal.C3132R;
import com.android.internal.view.menu.MenuItemImpl;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes4.dex */
public class MenuInflater {
    private static final String LOG_TAG = "MenuInflater";
    private static final int NO_ID = 0;
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final String XML_MENU = "menu";
    private final Object[] mActionProviderConstructorArguments;
    private final Object[] mActionViewConstructorArguments;
    private Context mContext;
    private Object mRealOwner;
    private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = {Context.class};
    private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

    public MenuInflater(Context context) {
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public MenuInflater(Context context, Object realOwner) {
        this.mContext = context;
        this.mRealOwner = realOwner;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public void inflate(int menuRes, Menu menu) {
        XmlResourceParser parser = null;
        try {
            try {
                try {
                    parser = this.mContext.getResources().getLayout(menuRes);
                    AttributeSet attrs = Xml.asAttributeSet(parser);
                    parseMenu(parser, attrs, menu);
                } catch (IOException e) {
                    throw new InflateException("Error inflating menu XML", e);
                }
            } catch (XmlPullParserException e2) {
                throw new InflateException("Error inflating menu XML", e2);
            }
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x003e, code lost:
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003f, code lost:
        if (r4 != false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0041, code lost:
        switch(r1) {
            case 1: goto L55;
            case 2: goto L39;
            case 3: goto L10;
            default: goto L59;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0046, code lost:
        r5 = r9.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x004a, code lost:
        if (r2 == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
        if (r5.equals(r3) == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0052, code lost:
        r2 = false;
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005c, code lost:
        if (r5.equals("group") == false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x005e, code lost:
        r0.resetGroup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:
        if (r5.equals("item") == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006f, code lost:
        if (r0.hasAddedItem() != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
        if (r0.itemActionProvider == null) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007f, code lost:
        if (r0.itemActionProvider.hasSubMenu() == false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0081, code lost:
        registerMenu(r0.addSubMenuItem(), r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0089, code lost:
        registerMenu(r0.addItem(), r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0098, code lost:
        if (r5.equals(android.view.MenuInflater.XML_MENU) == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009a, code lost:
        r4 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009c, code lost:
        if (r2 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x009f, code lost:
        r5 = r9.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a9, code lost:
        if (r5.equals("group") == false) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ab, code lost:
        r0.readGroup(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b5, code lost:
        if (r5.equals("item") == false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b7, code lost:
        r0.readItem(r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c2, code lost:
        if (r5.equals(android.view.MenuInflater.XML_MENU) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00c4, code lost:
        r6 = r0.addSubMenuItem();
        registerMenu(r6, r10);
        parseMenu(r9, r10, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00cf, code lost:
        r2 = true;
        r3 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00d9, code lost:
        throw new java.lang.RuntimeException("Unexpected end of document");
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00da, code lost:
        r1 = r9.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00e0, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu) throws XmlPullParserException, IOException {
        MenuState menuState = new MenuState(menu);
        int eventType = parser.getEventType();
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;
        while (true) {
            if (eventType == 2) {
                String tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    eventType = parser.next();
                } else {
                    throw new RuntimeException("Expecting menu, got " + tagName);
                }
            } else {
                eventType = parser.next();
                if (eventType == 1) {
                    break;
                }
            }
        }
    }

    private void registerMenu(MenuItem item, AttributeSet set) {
    }

    private void registerMenu(SubMenu subMenu, AttributeSet set) {
    }

    Context getContext() {
        return this.mContext;
    }

    /* loaded from: classes4.dex */
    private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = {MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object realOwner, String methodName) {
            this.mRealOwner = realOwner;
            Class<?> c = realOwner.getClass();
            try {
                this.mMethod = c.getMethod(methodName, PARAM_TYPES);
            } catch (Exception e) {
                InflateException ex = new InflateException("Couldn't resolve menu item onClick handler " + methodName + " in class " + c.getName());
                ex.initCause(e);
                throw ex;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem item) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return ((Boolean) this.mMethod.invoke(this.mRealOwner, item)).booleanValue();
                }
                this.mMethod.invoke(this.mRealOwner, item);
                return true;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private Object findRealOwner(Object owner) {
        if (owner instanceof Activity) {
            return owner;
        }
        if (owner instanceof ContextWrapper) {
            return findRealOwner(((ContextWrapper) owner).getBaseContext());
        }
        return owner;
    }

    /* loaded from: classes4.dex */
    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private Menu menu;
        private ColorStateList itemIconTintList = null;
        private BlendMode mItemIconBlendMode = null;

        public MenuState(Menu menu) {
            this.menu = menu;
            resetGroup();
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }

        public void readGroup(AttributeSet attrs) {
            TypedArray a = MenuInflater.this.mContext.obtainStyledAttributes(attrs, C3132R.styleable.MenuGroup);
            this.groupId = a.getResourceId(1, 0);
            this.groupCategory = a.getInt(3, 0);
            this.groupOrder = a.getInt(4, 0);
            this.groupCheckable = a.getInt(5, 0);
            this.groupVisible = a.getBoolean(2, true);
            this.groupEnabled = a.getBoolean(0, true);
            a.recycle();
        }

        public void readItem(AttributeSet attrs) {
            TypedArray a = MenuInflater.this.mContext.obtainStyledAttributes(attrs, C3132R.styleable.MenuItem);
            this.itemId = a.getResourceId(2, 0);
            int category = a.getInt(5, this.groupCategory);
            int order = a.getInt(6, this.groupOrder);
            this.itemCategoryOrder = ((-65536) & category) | (65535 & order);
            this.itemTitle = a.getText(7);
            this.itemTitleCondensed = a.getText(8);
            this.itemIconResId = a.getResourceId(0, 0);
            if (a.hasValue(22)) {
                this.mItemIconBlendMode = Drawable.parseBlendMode(a.getInt(22, -1), this.mItemIconBlendMode);
            } else {
                this.mItemIconBlendMode = null;
            }
            if (a.hasValue(21)) {
                this.itemIconTintList = a.getColorStateList(21);
            } else {
                this.itemIconTintList = null;
            }
            this.itemAlphabeticShortcut = getShortcut(a.getString(9));
            this.itemAlphabeticModifiers = a.getInt(19, 4096);
            this.itemNumericShortcut = getShortcut(a.getString(10));
            this.itemNumericModifiers = a.getInt(20, 4096);
            if (a.hasValue(11)) {
                this.itemCheckable = a.getBoolean(11, false) ? 1 : 0;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = a.getBoolean(3, false);
            this.itemVisible = a.getBoolean(4, this.groupVisible);
            this.itemEnabled = a.getBoolean(1, this.groupEnabled);
            this.itemShowAsAction = a.getInt(14, -1);
            this.itemListenerMethodName = a.getString(12);
            this.itemActionViewLayout = a.getResourceId(15, 0);
            this.itemActionViewClassName = a.getString(16);
            this.itemActionProviderClassName = a.getString(17);
            boolean hasActionProvider = this.itemActionProviderClassName != null;
            if (hasActionProvider && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider) newInstance(this.itemActionProviderClassName, MenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, MenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (hasActionProvider) {
                    Log.m64w(MenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = a.getText(13);
            this.itemTooltipText = a.getText(18);
            a.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(String shortcutString) {
            if (shortcutString == null) {
                return (char) 0;
            }
            return shortcutString.charAt(0);
        }

        private void setItem(MenuItem item) {
            boolean z;
            MenuItem enabled = item.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
            if (this.itemCheckable < 1) {
                z = false;
            } else {
                z = true;
            }
            enabled.setCheckable(z).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut, this.itemAlphabeticModifiers).setNumericShortcut(this.itemNumericShortcut, this.itemNumericModifiers);
            if (this.itemShowAsAction >= 0) {
                item.setShowAsAction(this.itemShowAsAction);
            }
            if (this.mItemIconBlendMode != null) {
                item.setIconTintBlendMode(this.mItemIconBlendMode);
            }
            if (this.itemIconTintList != null) {
                item.setIconTintList(this.itemIconTintList);
            }
            if (this.itemListenerMethodName != null) {
                if (MenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                item.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(MenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (item instanceof MenuItemImpl) {
                MenuItemImpl impl = (MenuItemImpl) item;
                if (this.itemCheckable >= 2) {
                    impl.setExclusiveCheckable(true);
                }
            }
            boolean actionViewSpecified = false;
            if (this.itemActionViewClassName != null) {
                View actionView = (View) newInstance(this.itemActionViewClassName, MenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, MenuInflater.this.mActionViewConstructorArguments);
                item.setActionView(actionView);
                actionViewSpecified = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!actionViewSpecified) {
                    item.setActionView(this.itemActionViewLayout);
                } else {
                    Log.m64w(MenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if (this.itemActionProvider != null) {
                item.setActionProvider(this.itemActionProvider);
            }
            item.setContentDescription(this.itemContentDescription);
            item.setTooltipText(this.itemTooltipText);
        }

        public MenuItem addItem() {
            this.itemAdded = true;
            MenuItem item = this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(item);
            return item;
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        private <T> T newInstance(String className, Class<?>[] constructorSignature, Object[] arguments) {
            try {
                Class<?> clazz = MenuInflater.this.mContext.getClassLoader().loadClass(className);
                Constructor<?> constructor = clazz.getConstructor(constructorSignature);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(arguments);
            } catch (Exception e) {
                Log.m63w(MenuInflater.LOG_TAG, "Cannot instantiate class: " + className, e);
                return null;
            }
        }
    }
}
