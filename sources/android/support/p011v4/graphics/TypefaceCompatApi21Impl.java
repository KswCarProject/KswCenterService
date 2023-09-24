package android.support.p011v4.graphics;

import android.p007os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v4.graphics.TypefaceCompatApi21Impl */
/* loaded from: classes3.dex */
class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            String path = Os.readlink("/proc/self/fd/" + fd.getFd());
            if (!OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return null;
            }
            return new File(path);
        } catch (ErrnoException e) {
            return null;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: BlockProcessor
        jadx.core.utils.exceptions.JadxRuntimeException: Found unreachable blocks
        	at jadx.core.dex.visitors.blocks.DominatorTree.sortBlocks(DominatorTree.java:35)
        	at jadx.core.dex.visitors.blocks.DominatorTree.compute(DominatorTree.java:25)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.computeDominators(BlockProcessor.java:202)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.processBlocksTree(BlockProcessor.java:45)
        	at jadx.core.dex.visitors.blocks.BlockProcessor.visit(BlockProcessor.java:39)
        */
    @Override // android.support.p011v4.graphics.TypefaceCompatBaseImpl, android.support.p011v4.graphics.TypefaceCompat.TypefaceCompatImpl
    public android.graphics.Typeface createFromFontInfo(android.content.Context r11, android.p007os.CancellationSignal r12, @android.support.annotation.NonNull android.support.p011v4.provider.FontsContractCompat.FontInfo[] r13, int r14) {
        /*
            r10 = this;
            int r0 = r13.length
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L6
            return r1
        L6:
            android.support.v4.provider.FontsContractCompat$FontInfo r0 = r10.findBestInfo(r13, r14)
            android.content.ContentResolver r2 = r11.getContentResolver()
            android.net.Uri r3 = r0.getUri()     // Catch: java.io.IOException -> L7a
            java.lang.String r4 = "r"
            android.os.ParcelFileDescriptor r3 = r2.openFileDescriptor(r3, r4, r12)     // Catch: java.io.IOException -> L7a
            java.io.File r4 = r10.getFile(r3)     // Catch: java.lang.Throwable -> L63
            if (r4 == 0) goto L32
            boolean r5 = r4.canRead()     // Catch: java.lang.Throwable -> L63
            if (r5 != 0) goto L28
            goto L32
        L28:
            android.graphics.Typeface r5 = android.graphics.Typeface.createFromFile(r4)     // Catch: java.lang.Throwable -> L63
            if (r3 == 0) goto L31
            r3.close()     // Catch: java.io.IOException -> L7a
        L31:
            return r5
        L32:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L63
            java.io.FileDescriptor r6 = r3.getFileDescriptor()     // Catch: java.lang.Throwable -> L63
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L63
            android.graphics.Typeface r6 = super.createFromInputStream(r11, r5)     // Catch: java.lang.Throwable -> L4b
            r5.close()     // Catch: java.lang.Throwable -> L63
            if (r3 == 0) goto L47
            r3.close()     // Catch: java.io.IOException -> L7a
        L47:
            return r6
        L48:
            r6 = move-exception
            r7 = r1
            goto L51
        L4b:
            r6 = move-exception
            throw r6     // Catch: java.lang.Throwable -> L4d
        L4d:
            r7 = move-exception
            r9 = r7
            r7 = r6
            r6 = r9
        L51:
            if (r7 == 0) goto L5c
            r5.close()     // Catch: java.lang.Throwable -> L57
            goto L5f
        L57:
            r8 = move-exception
            r7.addSuppressed(r8)     // Catch: java.lang.Throwable -> L63
            goto L5f
        L5c:
            r5.close()     // Catch: java.lang.Throwable -> L63
        L5f:
            throw r6     // Catch: java.lang.Throwable -> L63
        L60:
            r4 = move-exception
            r5 = r1
            goto L69
        L63:
            r4 = move-exception
            throw r4     // Catch: java.lang.Throwable -> L65
        L65:
            r5 = move-exception
            r9 = r5
            r5 = r4
            r4 = r9
        L69:
            if (r3 == 0) goto L79
            if (r5 == 0) goto L76
            r3.close()     // Catch: java.lang.Throwable -> L71
            goto L79
        L71:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch: java.io.IOException -> L7a
            goto L79
        L76:
            r3.close()     // Catch: java.io.IOException -> L7a
        L79:
            throw r4     // Catch: java.io.IOException -> L7a
        L7a:
            r3 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p011v4.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, android.support.v4.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }
}
