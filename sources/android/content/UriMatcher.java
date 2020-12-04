package android.content;

import android.annotation.UnsupportedAppUsage;
import java.util.ArrayList;

public class UriMatcher {
    private static final int EXACT = 0;
    public static final int NO_MATCH = -1;
    private static final int NUMBER = 1;
    private static final int TEXT = 2;
    @UnsupportedAppUsage
    private ArrayList<UriMatcher> mChildren;
    private int mCode;
    @UnsupportedAppUsage
    private final String mText;
    private final int mWhich;

    public UriMatcher(int code) {
        this.mCode = code;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    private UriMatcher(int which, String text) {
        this.mCode = -1;
        this.mWhich = which;
        this.mChildren = new ArrayList<>();
        this.mText = text;
    }

    public void addURI(String authority, String path, int code) {
        if (code >= 0) {
            String[] tokens = null;
            if (path != null) {
                String newPath = path;
                if (path.length() > 1 && path.charAt(0) == '/') {
                    newPath = path.substring(1);
                }
                tokens = newPath.split("/");
            }
            int numTokens = tokens != null ? tokens.length : 0;
            UriMatcher node = this;
            int i = -1;
            while (i < numTokens) {
                String token = i < 0 ? authority : tokens[i];
                ArrayList<UriMatcher> children = node.mChildren;
                int numChildren = children.size();
                int j = 0;
                while (true) {
                    if (j >= numChildren) {
                        break;
                    }
                    UriMatcher child = children.get(j);
                    if (token.equals(child.mText)) {
                        node = child;
                        break;
                    }
                    j++;
                }
                if (j == numChildren) {
                    UriMatcher child2 = createChild(token);
                    node.mChildren.add(child2);
                    node = child2;
                }
                i++;
            }
            node.mCode = code;
            return;
        }
        throw new IllegalArgumentException("code " + code + " is invalid: it must be positive");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.content.UriMatcher createChild(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 35
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x0019
            r1 = 42
            if (r0 == r1) goto L_0x000f
            goto L_0x0023
        L_0x000f:
            java.lang.String r0 = "*"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = r2
            goto L_0x0024
        L_0x0019:
            java.lang.String r0 = "#"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x0023
            r0 = r3
            goto L_0x0024
        L_0x0023:
            r0 = -1
        L_0x0024:
            switch(r0) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002d;
                default: goto L_0x0027;
            }
        L_0x0027:
            android.content.UriMatcher r0 = new android.content.UriMatcher
            r0.<init>(r3, r4)
            return r0
        L_0x002d:
            android.content.UriMatcher r0 = new android.content.UriMatcher
            r1 = 2
            java.lang.String r2 = "*"
            r0.<init>(r1, r2)
            return r0
        L_0x0036:
            android.content.UriMatcher r0 = new android.content.UriMatcher
            java.lang.String r1 = "#"
            r0.<init>(r2, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.UriMatcher.createChild(java.lang.String):android.content.UriMatcher");
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0074 A[LOOP:0: B:7:0x001c->B:34:0x0074, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0073 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int match(android.net.Uri r17) {
        /*
            r16 = this;
            java.util.List r0 = r17.getPathSegments()
            int r1 = r0.size()
            r2 = r16
            if (r1 != 0) goto L_0x0017
            java.lang.String r3 = r17.getAuthority()
            if (r3 != 0) goto L_0x0017
            r3 = r16
            int r4 = r3.mCode
            return r4
        L_0x0017:
            r3 = r16
            r4 = -1
            r5 = r2
            r2 = r4
        L_0x001c:
            if (r2 >= r1) goto L_0x0077
            if (r2 >= 0) goto L_0x0025
            java.lang.String r6 = r17.getAuthority()
            goto L_0x002b
        L_0x0025:
            java.lang.Object r6 = r0.get(r2)
            java.lang.String r6 = (java.lang.String) r6
        L_0x002b:
            java.util.ArrayList<android.content.UriMatcher> r7 = r5.mChildren
            if (r7 != 0) goto L_0x0030
            goto L_0x0077
        L_0x0030:
            r5 = 0
            int r8 = r7.size()
            r9 = 0
            r10 = r5
            r5 = r9
        L_0x0038:
            if (r5 >= r8) goto L_0x0070
            java.lang.Object r11 = r7.get(r5)
            android.content.UriMatcher r11 = (android.content.UriMatcher) r11
            int r12 = r11.mWhich
            switch(r12) {
                case 0: goto L_0x0061;
                case 1: goto L_0x0048;
                case 2: goto L_0x0046;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x006a
        L_0x0046:
            r10 = r11
            goto L_0x006a
        L_0x0048:
            int r12 = r6.length()
            r13 = r9
        L_0x004d:
            if (r13 >= r12) goto L_0x005f
            char r14 = r6.charAt(r13)
            r15 = 48
            if (r14 < r15) goto L_0x006a
            r15 = 57
            if (r14 <= r15) goto L_0x005c
            goto L_0x006a
        L_0x005c:
            int r13 = r13 + 1
            goto L_0x004d
        L_0x005f:
            r10 = r11
            goto L_0x006a
        L_0x0061:
            java.lang.String r12 = r11.mText
            boolean r12 = r12.equals(r6)
            if (r12 == 0) goto L_0x006a
            r10 = r11
        L_0x006a:
            if (r10 == 0) goto L_0x006d
            goto L_0x0070
        L_0x006d:
            int r5 = r5 + 1
            goto L_0x0038
        L_0x0070:
            r5 = r10
            if (r5 != 0) goto L_0x0074
            return r4
        L_0x0074:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0077:
            int r2 = r5.mCode
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.UriMatcher.match(android.net.Uri):int");
    }
}
