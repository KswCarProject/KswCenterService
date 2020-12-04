package android.content.pm;

import android.util.ArrayMap;

public class FallbackCategoryProvider {
    private static final String TAG = "FallbackCategoryProvider";
    private static final ArrayMap<String, Integer> sFallbacks = new ArrayMap<>();

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0095, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r3.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009e, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009f, code lost:
        android.util.Log.w(TAG, "Failed to read fallback categories", r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x009e A[ExcHandler: IOException | NumberFormatException (r1v1 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:5:0x0026] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void loadFallbacks() {
        /*
            android.util.ArrayMap<java.lang.String, java.lang.Integer> r0 = sFallbacks
            r0.clear()
            java.lang.String r0 = "fw.ignore_fb_categories"
            r1 = 0
            boolean r0 = android.os.SystemProperties.getBoolean(r0, r1)
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = "FallbackCategoryProvider"
            java.lang.String r1 = "Ignoring fallback categories"
            android.util.Log.d(r0, r1)
            return
        L_0x0016:
            android.content.res.AssetManager r0 = new android.content.res.AssetManager
            r0.<init>()
            java.lang.String r2 = "/system/framework/framework-res.apk"
            r0.addAssetPath(r2)
            android.content.res.Resources r2 = new android.content.res.Resources
            r3 = 0
            r2.<init>(r0, r3, r3)
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException | NumberFormatException -> 0x009e }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException | NumberFormatException -> 0x009e }
            r6 = 17825796(0x1100004, float:2.6448634E-38)
            java.io.InputStream r6 = r2.openRawResource(r6)     // Catch:{ IOException | NumberFormatException -> 0x009e }
            r5.<init>(r6)     // Catch:{ IOException | NumberFormatException -> 0x009e }
            r4.<init>(r5)     // Catch:{ IOException | NumberFormatException -> 0x009e }
        L_0x0038:
            java.lang.String r5 = r4.readLine()     // Catch:{ Throwable -> 0x008c }
            r6 = r5
            if (r5 == 0) goto L_0x0065
            char r5 = r6.charAt(r1)     // Catch:{ Throwable -> 0x008c }
            r7 = 35
            if (r5 != r7) goto L_0x0048
            goto L_0x0038
        L_0x0048:
            java.lang.String r5 = ","
            java.lang.String[] r5 = r6.split(r5)     // Catch:{ Throwable -> 0x008c }
            int r7 = r5.length     // Catch:{ Throwable -> 0x008c }
            r8 = 2
            if (r7 != r8) goto L_0x0064
            android.util.ArrayMap<java.lang.String, java.lang.Integer> r7 = sFallbacks     // Catch:{ Throwable -> 0x008c }
            r8 = r5[r1]     // Catch:{ Throwable -> 0x008c }
            r9 = 1
            r9 = r5[r9]     // Catch:{ Throwable -> 0x008c }
            int r9 = java.lang.Integer.parseInt(r9)     // Catch:{ Throwable -> 0x008c }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x008c }
            r7.put(r8, r9)     // Catch:{ Throwable -> 0x008c }
        L_0x0064:
            goto L_0x0038
        L_0x0065:
            java.lang.String r1 = "FallbackCategoryProvider"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008c }
            r5.<init>()     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = "Found "
            r5.append(r7)     // Catch:{ Throwable -> 0x008c }
            android.util.ArrayMap<java.lang.String, java.lang.Integer> r7 = sFallbacks     // Catch:{ Throwable -> 0x008c }
            int r7 = r7.size()     // Catch:{ Throwable -> 0x008c }
            r5.append(r7)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r7 = " fallback categories"
            r5.append(r7)     // Catch:{ Throwable -> 0x008c }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x008c }
            android.util.Log.d(r1, r5)     // Catch:{ Throwable -> 0x008c }
            r4.close()     // Catch:{ IOException | NumberFormatException -> 0x009e }
            goto L_0x00a6
        L_0x008a:
            r1 = move-exception
            goto L_0x008f
        L_0x008c:
            r1 = move-exception
            r3 = r1
            throw r3     // Catch:{ all -> 0x008a }
        L_0x008f:
            if (r3 == 0) goto L_0x009a
            r4.close()     // Catch:{ Throwable -> 0x0095, IOException | NumberFormatException -> 0x009e }
            goto L_0x009d
        L_0x0095:
            r5 = move-exception
            r3.addSuppressed(r5)     // Catch:{ IOException | NumberFormatException -> 0x009e }
            goto L_0x009d
        L_0x009a:
            r4.close()     // Catch:{ IOException | NumberFormatException -> 0x009e }
        L_0x009d:
            throw r1     // Catch:{ IOException | NumberFormatException -> 0x009e }
        L_0x009e:
            r1 = move-exception
            java.lang.String r3 = "FallbackCategoryProvider"
            java.lang.String r4 = "Failed to read fallback categories"
            android.util.Log.w(r3, r4, r1)
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.FallbackCategoryProvider.loadFallbacks():void");
    }

    public static int getFallbackCategory(String packageName) {
        return ((Integer) sFallbacks.getOrDefault(packageName, -1)).intValue();
    }
}
