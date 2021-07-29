package com.android.internal.content;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.provider.MetadataReader;
import android.system.Int64Ref;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.MessagingMessage;
import com.ibm.icu.text.PluralRules;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import libcore.io.IoUtils;

public abstract class FileSystemProvider extends DocumentsProvider {
    private static final boolean LOG_INOTIFY = false;
    protected static final String SUPPORTED_QUERY_ARGS = joinNewline(DocumentsContract.QUERY_ARG_DISPLAY_NAME, DocumentsContract.QUERY_ARG_FILE_SIZE_OVER, DocumentsContract.QUERY_ARG_LAST_MODIFIED_AFTER, DocumentsContract.QUERY_ARG_MIME_TYPES);
    private static final String TAG = "FileSystemProvider";
    private String[] mDefaultProjection;
    private Handler mHandler;
    @GuardedBy({"mObservers"})
    private final ArrayMap<File, DirectoryObserver> mObservers = new ArrayMap<>();

    /* access modifiers changed from: protected */
    public abstract Uri buildNotificationUri(String str);

    /* access modifiers changed from: protected */
    public abstract String getDocIdForFile(File file) throws FileNotFoundException;

    /* access modifiers changed from: protected */
    public abstract File getFileForDocId(String str, boolean z) throws FileNotFoundException;

    private static String joinNewline(String... args) {
        return TextUtils.join((CharSequence) "\n", (Object[]) args);
    }

    /* access modifiers changed from: protected */
    public void onDocIdChanged(String docId) {
    }

    public boolean onCreate() {
        throw new UnsupportedOperationException("Subclass should override this and call onCreate(defaultDocumentProjection)");
    }

    /* access modifiers changed from: protected */
    public void onCreate(String[] defaultProjection) {
        this.mHandler = new Handler();
        this.mDefaultProjection = defaultProjection;
    }

    public boolean isChildDocument(String parentDocId, String docId) {
        try {
            return FileUtils.contains(getFileForDocId(parentDocId).getCanonicalFile(), getFileForDocId(docId).getCanonicalFile());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to determine if " + docId + " is child of " + parentDocId + PluralRules.KEYWORD_RULE_SEPARATOR + e);
        }
    }

    public Bundle getDocumentMetadata(String documentId) throws FileNotFoundException {
        File file = getFileForDocId(documentId);
        if (file.exists()) {
            String mimeType = getDocumentType(documentId);
            if (DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
                final Int64Ref treeCount = new Int64Ref(0);
                final Int64Ref treeSize = new Int64Ref(0);
                try {
                    Files.walkFileTree(FileSystems.getDefault().getPath(file.getAbsolutePath(), new String[0]), new FileVisitor<Path>() {
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                            return FileVisitResult.CONTINUE;
                        }

                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            treeCount.value++;
                            treeSize.value += attrs.size();
                            return FileVisitResult.CONTINUE;
                        }

                        public FileVisitResult visitFileFailed(Path file, IOException exc) {
                            return FileVisitResult.CONTINUE;
                        }

                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    Bundle res = new Bundle();
                    res.putLong(DocumentsContract.METADATA_TREE_COUNT, treeCount.value);
                    res.putLong(DocumentsContract.METADATA_TREE_SIZE, treeSize.value);
                    return res;
                } catch (IOException e) {
                    Log.e(TAG, "An error occurred retrieving the metadata", e);
                    return null;
                }
            } else if (!file.isFile()) {
                Log.w(TAG, "Can't stream non-regular file. Returning empty metadata.");
                return null;
            } else if (!file.canRead()) {
                Log.w(TAG, "Can't stream non-readable file. Returning empty metadata.");
                return null;
            } else if (!MetadataReader.isSupportedMimeType(mimeType)) {
                Log.w(TAG, "Unsupported type " + mimeType + ". Returning empty metadata.");
                return null;
            } else {
                InputStream stream = null;
                try {
                    Bundle metadata = new Bundle();
                    stream = new FileInputStream(file.getAbsolutePath());
                    MetadataReader.getMetadata(metadata, stream, mimeType, (String[]) null);
                    return metadata;
                } catch (IOException e2) {
                    Log.e(TAG, "An error occurred retrieving the metadata", e2);
                    return null;
                } finally {
                    IoUtils.closeQuietly(stream);
                }
            }
        } else {
            throw new FileNotFoundException("Can't find the file for documentId: " + documentId);
        }
    }

    /* access modifiers changed from: protected */
    public final List<String> findDocumentPath(File parent, File doc) throws FileNotFoundException {
        if (!doc.exists()) {
            throw new FileNotFoundException(doc + " is not found.");
        } else if (FileUtils.contains(parent, doc)) {
            LinkedList<String> path = new LinkedList<>();
            while (doc != null && FileUtils.contains(parent, doc)) {
                path.addFirst(getDocIdForFile(doc));
                doc = doc.getParentFile();
            }
            return path;
        } else {
            throw new FileNotFoundException(doc + " is not found under " + parent);
        }
    }

    public String createDocument(String docId, String mimeType, String displayName) throws FileNotFoundException {
        String childId;
        String displayName2 = FileUtils.buildValidFatFilename(displayName);
        File parent = getFileForDocId(docId);
        if (parent.isDirectory()) {
            File file = FileUtils.buildUniqueFile(parent, mimeType, displayName2);
            if (!DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
                try {
                    if (file.createNewFile()) {
                        childId = getDocIdForFile(file);
                        onDocIdChanged(childId);
                    } else {
                        throw new IllegalStateException("Failed to touch " + file);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException("Failed to touch " + file + PluralRules.KEYWORD_RULE_SEPARATOR + e);
                }
            } else if (file.mkdir()) {
                childId = getDocIdForFile(file);
                onDocIdChanged(childId);
            } else {
                throw new IllegalStateException("Failed to mkdir " + file);
            }
            MediaStore.scanFile(getContext(), file);
            return childId;
        }
        throw new IllegalArgumentException("Parent document isn't a directory");
    }

    public String renameDocument(String docId, String displayName) throws FileNotFoundException {
        String displayName2 = FileUtils.buildValidFatFilename(displayName);
        File before = getFileForDocId(docId);
        File beforeVisibleFile = getFileForDocId(docId, true);
        File after = FileUtils.buildUniqueFile(before.getParentFile(), displayName2);
        if (before.renameTo(after)) {
            String afterDocId = getDocIdForFile(after);
            onDocIdChanged(docId);
            onDocIdChanged(afterDocId);
            moveInMediaStore(beforeVisibleFile, getFileForDocId(afterDocId, true));
            if (!TextUtils.equals(docId, afterDocId)) {
                return afterDocId;
            }
            return null;
        }
        throw new IllegalStateException("Failed to rename to " + after);
    }

    public String moveDocument(String sourceDocumentId, String sourceParentDocumentId, String targetParentDocumentId) throws FileNotFoundException {
        File before = getFileForDocId(sourceDocumentId);
        File after = new File(getFileForDocId(targetParentDocumentId), before.getName());
        File visibleFileBefore = getFileForDocId(sourceDocumentId, true);
        if (after.exists()) {
            throw new IllegalStateException("Already exists " + after);
        } else if (before.renameTo(after)) {
            String docId = getDocIdForFile(after);
            onDocIdChanged(sourceDocumentId);
            onDocIdChanged(docId);
            moveInMediaStore(visibleFileBefore, getFileForDocId(docId, true));
            return docId;
        } else {
            throw new IllegalStateException("Failed to move to " + after);
        }
    }

    private void moveInMediaStore(File oldVisibleFile, File newVisibleFile) {
        if (oldVisibleFile != null) {
            MediaStore.scanFile(getContext(), oldVisibleFile);
        }
        if (newVisibleFile != null) {
            MediaStore.scanFile(getContext(), newVisibleFile);
        }
    }

    public void deleteDocument(String docId) throws FileNotFoundException {
        File file = getFileForDocId(docId);
        File visibleFile = getFileForDocId(docId, true);
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            FileUtils.deleteContents(file);
        }
        if (file.delete()) {
            onDocIdChanged(docId);
            removeFromMediaStore(visibleFile, isDirectory);
            return;
        }
        throw new IllegalStateException("Failed to delete " + file);
    }

    private void removeFromMediaStore(File visibleFile, boolean isFolder) throws FileNotFoundException {
        if (visibleFile != null) {
            long token = Binder.clearCallingIdentity();
            try {
                ContentResolver resolver = getContext().getContentResolver();
                Uri externalUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
                if (isFolder) {
                    String path = visibleFile.getAbsolutePath() + "/";
                    resolver.delete(externalUri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{path + "%", Integer.toString(path.length()), path});
                }
                String path2 = visibleFile.getAbsolutePath();
                resolver.delete(externalUri, "_data LIKE ?1 AND lower(_data)=lower(?2)", new String[]{path2, path2});
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }
    }

    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        MatrixCursor result = new MatrixCursor(resolveProjection(projection));
        includeFile(result, documentId, (File) null);
        return result;
    }

    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        File parent = getFileForDocId(parentDocumentId);
        MatrixCursor result = new DirectoryCursor(resolveProjection(projection), parentDocumentId, parent);
        if (parent.isDirectory()) {
            for (File file : FileUtils.listFilesOrEmpty(parent)) {
                includeFile(result, (String) null, file);
            }
        } else {
            Log.w(TAG, "parentDocumentId '" + parentDocumentId + "' is not Directory");
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public final Cursor querySearchDocuments(File folder, String[] projection, Set<String> exclusion, Bundle queryArgs) throws FileNotFoundException {
        MatrixCursor result = new MatrixCursor(resolveProjection(projection));
        LinkedList<File> pending = new LinkedList<>();
        pending.add(folder);
        while (!pending.isEmpty() && result.getCount() < 24) {
            File file = pending.removeFirst();
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    pending.add(child);
                }
            }
            if (!exclusion.contains(file.getAbsolutePath()) && matchSearchQueryArguments(file, queryArgs)) {
                includeFile(result, (String) null, file);
            }
        }
        String[] handledQueryArgs = DocumentsContract.getHandledQueryArguments(queryArgs);
        if (handledQueryArgs.length > 0) {
            Bundle extras = new Bundle();
            extras.putStringArray(ContentResolver.EXTRA_HONORED_ARGS, handledQueryArgs);
            result.setExtras(extras);
        }
        return result;
    }

    public String getDocumentType(String documentId) throws FileNotFoundException {
        return getDocumentType(documentId, getFileForDocId(documentId));
    }

    private String getDocumentType(String documentId, File file) throws FileNotFoundException {
        String mime;
        if (file.isDirectory()) {
            return DocumentsContract.Document.MIME_TYPE_DIR;
        }
        int lastDot = documentId.lastIndexOf(46);
        if (lastDot < 0 || (mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(documentId.substring(lastDot + 1).toLowerCase())) == null) {
            return ContentResolver.MIME_TYPE_DEFAULT;
        }
        return mime;
    }

    public ParcelFileDescriptor openDocument(String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {
        File file = getFileForDocId(documentId);
        File visibleFile = getFileForDocId(documentId, true);
        int pfdMode = ParcelFileDescriptor.parseMode(mode);
        if (pfdMode == 268435456 || visibleFile == null) {
            return ParcelFileDescriptor.open(file, pfdMode);
        }
        try {
            return ParcelFileDescriptor.open(file, pfdMode, this.mHandler, new ParcelFileDescriptor.OnCloseListener(documentId, visibleFile) {
                private final /* synthetic */ String f$1;
                private final /* synthetic */ File f$2;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onClose(IOException iOException) {
                    FileSystemProvider.lambda$openDocument$0(FileSystemProvider.this, this.f$1, this.f$2, iOException);
                }
            });
        } catch (IOException e) {
            throw new FileNotFoundException("Failed to open for writing: " + e);
        }
    }

    public static /* synthetic */ void lambda$openDocument$0(FileSystemProvider fileSystemProvider, String documentId, File visibleFile, IOException e) {
        fileSystemProvider.onDocIdChanged(documentId);
        fileSystemProvider.scanFile(visibleFile);
    }

    private boolean matchSearchQueryArguments(File file, Bundle queryArgs) {
        String extension;
        if (file == null) {
            return false;
        }
        String fileName = file.getName();
        if (file.isDirectory()) {
            extension = DocumentsContract.Document.MIME_TYPE_DIR;
        } else {
            int dotPos = fileName.lastIndexOf(46);
            if (dotPos < 0) {
                return false;
            }
            extension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileName.substring(dotPos + 1));
        }
        return DocumentsContract.matchSearchQueryArguments(queryArgs, fileName, extension, file.lastModified(), file.length());
    }

    private void scanFile(File visibleFile) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(visibleFile));
        getContext().sendBroadcast(intent);
    }

    public AssetFileDescriptor openDocumentThumbnail(String documentId, Point sizeHint, CancellationSignal signal) throws FileNotFoundException {
        return DocumentsContract.openImageThumbnail(getFileForDocId(documentId));
    }

    /* access modifiers changed from: protected */
    public MatrixCursor.RowBuilder includeFile(MatrixCursor result, String docId, File file) throws FileNotFoundException {
        String[] columns = result.getColumnNames();
        MatrixCursor.RowBuilder row = result.newRow();
        if (docId == null) {
            docId = getDocIdForFile(file);
        } else {
            file = getFileForDocId(docId);
        }
        String mimeType = getDocumentType(docId, file);
        row.add("document_id", (Object) docId);
        row.add("mime_type", (Object) mimeType);
        int flagIndex = ArrayUtils.indexOf(columns, "flags");
        if (flagIndex != -1) {
            int flags = 0;
            if (file.canWrite()) {
                if (mimeType.equals(DocumentsContract.Document.MIME_TYPE_DIR)) {
                    flags = 0 | 8 | 4 | 64 | 256;
                } else {
                    flags = 0 | 2 | 4 | 64 | 256;
                }
            }
            if (mimeType.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                flags |= 1;
            }
            if (typeSupportsMetadata(mimeType)) {
                flags |= 16384;
            }
            row.add(flagIndex, (Object) Integer.valueOf(flags));
        }
        int displayNameIndex = ArrayUtils.indexOf(columns, "_display_name");
        if (displayNameIndex != -1) {
            row.add(displayNameIndex, (Object) file.getName());
        }
        int lastModifiedIndex = ArrayUtils.indexOf(columns, "last_modified");
        if (lastModifiedIndex != -1) {
            long lastModified = file.lastModified();
            if (lastModified > 31536000000L) {
                row.add(lastModifiedIndex, (Object) Long.valueOf(lastModified));
            }
        }
        int sizeIndex = ArrayUtils.indexOf(columns, "_size");
        if (sizeIndex != -1) {
            row.add(sizeIndex, (Object) Long.valueOf(file.length()));
        }
        return row;
    }

    /* access modifiers changed from: protected */
    public boolean typeSupportsMetadata(String mimeType) {
        return MetadataReader.isSupportedMimeType(mimeType) || DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType);
    }

    /* access modifiers changed from: protected */
    public final File getFileForDocId(String docId) throws FileNotFoundException {
        return getFileForDocId(docId, false);
    }

    private String[] resolveProjection(String[] projection) {
        return projection == null ? this.mDefaultProjection : projection;
    }

    /* access modifiers changed from: private */
    public void startObserving(File file, Uri notifyUri, DirectoryCursor cursor) {
        synchronized (this.mObservers) {
            DirectoryObserver observer = this.mObservers.get(file);
            if (observer == null) {
                observer = new DirectoryObserver(file, getContext().getContentResolver(), notifyUri);
                observer.startWatching();
                this.mObservers.put(file, observer);
            }
            observer.mCursors.add(cursor);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0029, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stopObserving(java.io.File r4, com.android.internal.content.FileSystemProvider.DirectoryCursor r5) {
        /*
            r3 = this;
            android.util.ArrayMap<java.io.File, com.android.internal.content.FileSystemProvider$DirectoryObserver> r0 = r3.mObservers
            monitor-enter(r0)
            android.util.ArrayMap<java.io.File, com.android.internal.content.FileSystemProvider$DirectoryObserver> r1 = r3.mObservers     // Catch:{ all -> 0x002a }
            java.lang.Object r1 = r1.get(r4)     // Catch:{ all -> 0x002a }
            com.android.internal.content.FileSystemProvider$DirectoryObserver r1 = (com.android.internal.content.FileSystemProvider.DirectoryObserver) r1     // Catch:{ all -> 0x002a }
            if (r1 != 0) goto L_0x000f
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x000f:
            java.util.concurrent.CopyOnWriteArrayList r2 = r1.mCursors     // Catch:{ all -> 0x002a }
            r2.remove(r5)     // Catch:{ all -> 0x002a }
            java.util.concurrent.CopyOnWriteArrayList r2 = r1.mCursors     // Catch:{ all -> 0x002a }
            int r2 = r2.size()     // Catch:{ all -> 0x002a }
            if (r2 != 0) goto L_0x0028
            android.util.ArrayMap<java.io.File, com.android.internal.content.FileSystemProvider$DirectoryObserver> r2 = r3.mObservers     // Catch:{ all -> 0x002a }
            r2.remove(r4)     // Catch:{ all -> 0x002a }
            r1.stopWatching()     // Catch:{ all -> 0x002a }
        L_0x0028:
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            return
        L_0x002a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.content.FileSystemProvider.stopObserving(java.io.File, com.android.internal.content.FileSystemProvider$DirectoryCursor):void");
    }

    private static class DirectoryObserver extends FileObserver {
        private static final int NOTIFY_EVENTS = 4044;
        /* access modifiers changed from: private */
        public final CopyOnWriteArrayList<DirectoryCursor> mCursors = new CopyOnWriteArrayList<>();
        private final File mFile;
        private final Uri mNotifyUri;
        private final ContentResolver mResolver;

        DirectoryObserver(File file, ContentResolver resolver, Uri notifyUri) {
            super(file.getAbsolutePath(), (int) NOTIFY_EVENTS);
            this.mFile = file;
            this.mResolver = resolver;
            this.mNotifyUri = notifyUri;
        }

        public void onEvent(int event, String path) {
            if ((event & NOTIFY_EVENTS) != 0) {
                Iterator<DirectoryCursor> it = this.mCursors.iterator();
                while (it.hasNext()) {
                    it.next().notifyChanged();
                }
                this.mResolver.notifyChange(this.mNotifyUri, (ContentObserver) null, false);
            }
        }

        public String toString() {
            String filePath = this.mFile.getAbsolutePath();
            return "DirectoryObserver{file=" + filePath + ", ref=" + this.mCursors.size() + "}";
        }
    }

    private class DirectoryCursor extends MatrixCursor {
        private final File mFile;

        public DirectoryCursor(String[] columnNames, String docId, File file) {
            super(columnNames);
            Uri notifyUri = FileSystemProvider.this.buildNotificationUri(docId);
            setNotificationUris(FileSystemProvider.this.getContext().getContentResolver(), Arrays.asList(new Uri[]{notifyUri}), FileSystemProvider.this.getContext().getContentResolver().getUserId(), false);
            this.mFile = file;
            FileSystemProvider.this.startObserving(this.mFile, notifyUri, this);
        }

        public void notifyChanged() {
            onChange(false);
        }

        public void close() {
            super.close();
            FileSystemProvider.this.stopObserving(this.mFile, this);
        }
    }
}
