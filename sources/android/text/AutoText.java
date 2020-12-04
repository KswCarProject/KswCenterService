package android.text;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.provider.UserDictionary;
import android.view.View;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.util.Locale;
import org.xmlpull.v1.XmlPullParserException;

public class AutoText {
    private static final int DEFAULT = 14337;
    private static final int INCREMENT = 1024;
    private static final int RIGHT = 9300;
    private static final int TRIE_C = 0;
    private static final int TRIE_CHILD = 2;
    private static final int TRIE_NEXT = 3;
    private static final char TRIE_NULL = '￿';
    private static final int TRIE_OFF = 1;
    private static final int TRIE_ROOT = 0;
    private static final int TRIE_SIZEOF = 4;
    private static AutoText sInstance = new AutoText(Resources.getSystem());
    private static Object sLock = new Object();
    private Locale mLocale;
    private int mSize;
    private String mText;
    private char[] mTrie;
    private char mTrieUsed;

    private AutoText(Resources resources) {
        this.mLocale = resources.getConfiguration().locale;
        init(resources);
    }

    private static AutoText getInstance(View view) {
        AutoText instance;
        Resources res = view.getContext().getResources();
        Locale locale = res.getConfiguration().locale;
        synchronized (sLock) {
            instance = sInstance;
            if (!locale.equals(instance.mLocale)) {
                instance = new AutoText(res);
                sInstance = instance;
            }
        }
        return instance;
    }

    public static String get(CharSequence src, int start, int end, View view) {
        return getInstance(view).lookup(src, start, end);
    }

    public static int getSize(View view) {
        return getInstance(view).getSize();
    }

    private int getSize() {
        return this.mSize;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v5, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v6, types: [char] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String lookup(java.lang.CharSequence r9, int r10, int r11) {
        /*
            r8 = this;
            char[] r0 = r8.mTrie
            r1 = 0
            char r0 = r0[r1]
            r1 = r0
            r0 = r10
        L_0x0007:
            r2 = 0
            if (r0 >= r11) goto L_0x0053
            char r3 = r9.charAt(r0)
        L_0x000e:
            r4 = 65535(0xffff, float:9.1834E-41)
            if (r1 == r4) goto L_0x004d
            char[] r5 = r8.mTrie
            int r6 = r1 + 0
            char r5 = r5[r6]
            if (r3 != r5) goto L_0x0046
            int r5 = r11 + -1
            if (r0 != r5) goto L_0x003f
            char[] r5 = r8.mTrie
            int r6 = r1 + 1
            char r5 = r5[r6]
            if (r5 == r4) goto L_0x003f
            char[] r2 = r8.mTrie
            int r4 = r1 + 1
            char r2 = r2[r4]
            java.lang.String r4 = r8.mText
            char r4 = r4.charAt(r2)
            java.lang.String r5 = r8.mText
            int r6 = r2 + 1
            int r7 = r2 + 1
            int r7 = r7 + r4
            java.lang.String r5 = r5.substring(r6, r7)
            return r5
        L_0x003f:
            char[] r5 = r8.mTrie
            int r6 = r1 + 2
            char r1 = r5[r6]
            goto L_0x004d
        L_0x0046:
            char[] r4 = r8.mTrie
            int r5 = r1 + 3
            char r1 = r4[r5]
            goto L_0x000e
        L_0x004d:
            if (r1 != r4) goto L_0x0050
            return r2
        L_0x0050:
            int r0 = r0 + 1
            goto L_0x0007
        L_0x0053:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.text.AutoText.lookup(java.lang.CharSequence, int, int):java.lang.String");
    }

    private void init(Resources r) {
        char off;
        XmlResourceParser parser = r.getXml(R.xml.autotext);
        StringBuilder right = new StringBuilder(RIGHT);
        this.mTrie = new char[14337];
        this.mTrie[0] = TRIE_NULL;
        this.mTrieUsed = 1;
        try {
            XmlUtils.beginDocument(parser, "words");
            while (true) {
                XmlUtils.nextElement(parser);
                String element = parser.getName();
                if (element == null) {
                    break;
                } else if (!element.equals(UserDictionary.Words.WORD)) {
                    break;
                } else {
                    String src = parser.getAttributeValue((String) null, "src");
                    if (parser.next() == 4) {
                        String dest = parser.getText();
                        if (dest.equals("")) {
                            off = 0;
                        } else {
                            off = (char) right.length();
                            right.append((char) dest.length());
                            right.append(dest);
                        }
                        add(src, off);
                    }
                }
            }
            r.flushLayoutCache();
            parser.close();
            this.mText = right.toString();
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            parser.close();
            throw th;
        }
    }

    private void add(String src, char off) {
        int slen = src.length();
        this.mSize++;
        int herep = 0;
        for (int i = 0; i < slen; i++) {
            char c = src.charAt(i);
            int herep2 = herep;
            boolean found = false;
            while (true) {
                if (this.mTrie[herep2] == 65535) {
                    break;
                } else if (c != this.mTrie[this.mTrie[herep2] + 0]) {
                    herep2 = this.mTrie[herep2] + 3;
                } else if (i == slen - 1) {
                    this.mTrie[this.mTrie[herep2] + 1] = off;
                    return;
                } else {
                    herep2 = this.mTrie[herep2] + 2;
                    found = true;
                }
            }
            if (!found) {
                this.mTrie[herep2] = newTrieNode();
                this.mTrie[this.mTrie[herep2] + 0] = c;
                this.mTrie[this.mTrie[herep2] + 1] = TRIE_NULL;
                this.mTrie[this.mTrie[herep2] + 3] = TRIE_NULL;
                this.mTrie[this.mTrie[herep2] + 2] = TRIE_NULL;
                if (i == slen - 1) {
                    this.mTrie[this.mTrie[herep2] + 1] = off;
                    return;
                }
                herep = this.mTrie[herep2] + 2;
            } else {
                herep = herep2;
            }
        }
    }

    private char newTrieNode() {
        if (this.mTrieUsed + 4 > this.mTrie.length) {
            char[] copy = new char[(this.mTrie.length + 1024)];
            System.arraycopy(this.mTrie, 0, copy, 0, this.mTrie.length);
            this.mTrie = copy;
        }
        char ret = this.mTrieUsed;
        this.mTrieUsed = (char) (this.mTrieUsed + 4);
        return ret;
    }
}
