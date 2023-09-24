package android.bluetooth.p000le;

import android.annotation.SystemApi;
import java.util.List;

@SystemApi
/* renamed from: android.bluetooth.le.TruncatedFilter */
/* loaded from: classes.dex */
public final class TruncatedFilter {
    private final ScanFilter mFilter;
    private final List<ResultStorageDescriptor> mStorageDescriptors;

    public TruncatedFilter(ScanFilter filter, List<ResultStorageDescriptor> storageDescriptors) {
        this.mFilter = filter;
        this.mStorageDescriptors = storageDescriptors;
    }

    public ScanFilter getFilter() {
        return this.mFilter;
    }

    public List<ResultStorageDescriptor> getStorageDescriptors() {
        return this.mStorageDescriptors;
    }
}
