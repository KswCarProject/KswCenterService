package com.android.internal.util;

import android.util.SparseArray;

public class MessageUtils {
    private static final boolean DBG = false;
    public static final String[] DEFAULT_PREFIXES = {"CMD_", "EVENT_"};
    private static final String TAG = MessageUtils.class.getSimpleName();

    public static class DuplicateConstantError extends Error {
        private DuplicateConstantError() {
        }

        public DuplicateConstantError(String name1, String name2, int value) {
            super(String.format("Duplicate constant value: both %s and %s = %d", new Object[]{name1, name2, Integer.valueOf(value)}));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0070, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        r0 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0074 A[ExcHandler: IllegalAccessException | SecurityException (e java.lang.Throwable), Splitter:B:14:0x0049] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.SparseArray<java.lang.String> findMessageNames(java.lang.Class[] r19, java.lang.String[] r20) {
        /*
            r1 = r19
            r2 = r20
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            r3 = r0
            int r4 = r1.length
            r6 = 0
        L_0x000c:
            if (r6 >= r4) goto L_0x00a6
            r7 = r1[r6]
            java.lang.String r0 = r7.getName()
            r8 = r0
            java.lang.reflect.Field[] r0 = r7.getDeclaredFields()     // Catch:{ SecurityException -> 0x0084 }
            r9 = r0
            int r10 = r9.length
            r11 = 0
        L_0x001e:
            if (r11 >= r10) goto L_0x009e
            r12 = r9[r11]
            int r13 = r12.getModifiers()
            boolean r0 = java.lang.reflect.Modifier.isStatic(r13)
            r14 = 1
            r0 = r0 ^ r14
            boolean r15 = java.lang.reflect.Modifier.isFinal(r13)
            r15 = r15 ^ r14
            r0 = r0 | r15
            if (r0 == 0) goto L_0x0035
            goto L_0x007d
        L_0x0035:
            java.lang.String r15 = r12.getName()
            int r5 = r2.length
            r14 = 0
        L_0x003b:
            if (r14 >= r5) goto L_0x007d
            r1 = r2[r14]
            boolean r0 = r15.startsWith(r1)
            if (r0 != 0) goto L_0x0046
            goto L_0x0076
        L_0x0046:
            r17 = r1
            r1 = 1
            r12.setAccessible(r1)     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            r0 = 0
            int r0 = r12.getInt(r0)     // Catch:{ ExceptionInInitializerError | IllegalArgumentException -> 0x0070, IllegalAccessException | SecurityException -> 0x0074 }
            java.lang.Object r16 = r3.get(r0)     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            java.lang.String r16 = (java.lang.String) r16     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            r18 = r16
            r1 = r18
            if (r1 == 0) goto L_0x006c
            boolean r16 = r1.equals(r15)     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            if (r16 == 0) goto L_0x0066
            goto L_0x006c
        L_0x0066:
            com.android.internal.util.MessageUtils$DuplicateConstantError r2 = new com.android.internal.util.MessageUtils$DuplicateConstantError     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            r2.<init>(r15, r1, r0)     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            throw r2     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
        L_0x006c:
            r3.put(r0, r15)     // Catch:{ IllegalAccessException | SecurityException -> 0x0074 }
            goto L_0x0076
        L_0x0070:
            r0 = move-exception
            r1 = r0
            r0 = r1
            goto L_0x007d
        L_0x0074:
            r0 = move-exception
        L_0x0076:
            int r14 = r14 + 1
            r1 = r19
            r2 = r20
            goto L_0x003b
        L_0x007d:
            int r11 = r11 + 1
            r1 = r19
            r2 = r20
            goto L_0x001e
        L_0x0084:
            r0 = move-exception
            r1 = r0
            r0 = r1
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "Can't list fields of class "
            r2.append(r5)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r1, r2)
        L_0x009e:
            int r6 = r6 + 1
            r1 = r19
            r2 = r20
            goto L_0x000c
        L_0x00a6:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.MessageUtils.findMessageNames(java.lang.Class[], java.lang.String[]):android.util.SparseArray");
    }

    public static SparseArray<String> findMessageNames(Class[] classNames) {
        return findMessageNames(classNames, DEFAULT_PREFIXES);
    }
}
