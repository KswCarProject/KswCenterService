package android.content.p002pm.split;

import android.content.p002pm.PackageParser;
import android.util.IntArray;
import android.util.SparseArray;
import java.lang.Exception;
import java.util.Arrays;
import java.util.BitSet;
import libcore.util.EmptyArray;

/* renamed from: android.content.pm.split.SplitDependencyLoader */
/* loaded from: classes.dex */
public abstract class SplitDependencyLoader<E extends Exception> {
    private final SparseArray<int[]> mDependencies;

    protected abstract void constructSplit(int i, int[] iArr, int i2) throws Exception;

    protected abstract boolean isSplitCached(int i);

    protected SplitDependencyLoader(SparseArray<int[]> dependencies) {
        this.mDependencies = dependencies;
    }

    protected void loadDependenciesForSplit(int splitIdx) throws Exception {
        if (isSplitCached(splitIdx)) {
            return;
        }
        if (splitIdx == 0) {
            int[] configSplitIndices = collectConfigSplitIndices(0);
            constructSplit(0, configSplitIndices, -1);
            return;
        }
        IntArray linearDependencies = new IntArray();
        linearDependencies.add(splitIdx);
        while (true) {
            int[] deps = this.mDependencies.get(splitIdx);
            if (deps != null && deps.length > 0) {
                splitIdx = deps[0];
            } else {
                splitIdx = -1;
            }
            if (splitIdx < 0 || isSplitCached(splitIdx)) {
                break;
            }
            linearDependencies.add(splitIdx);
        }
        int parentIdx = splitIdx;
        for (int i = linearDependencies.size() - 1; i >= 0; i--) {
            int idx = linearDependencies.get(i);
            int[] configSplitIndices2 = collectConfigSplitIndices(idx);
            constructSplit(idx, configSplitIndices2, parentIdx);
            parentIdx = idx;
        }
    }

    private int[] collectConfigSplitIndices(int splitIdx) {
        int[] deps = this.mDependencies.get(splitIdx);
        if (deps == null || deps.length <= 1) {
            return EmptyArray.INT;
        }
        return Arrays.copyOfRange(deps, 1, deps.length);
    }

    /* renamed from: android.content.pm.split.SplitDependencyLoader$IllegalDependencyException */
    /* loaded from: classes.dex */
    public static class IllegalDependencyException extends Exception {
        private IllegalDependencyException(String message) {
            super(message);
        }
    }

    private static int[] append(int[] src, int elem) {
        if (src == null) {
            return new int[]{elem};
        }
        int[] dst = Arrays.copyOf(src, src.length + 1);
        dst[src.length] = elem;
        return dst;
    }

    public static SparseArray<int[]> createDependenciesFromPackage(PackageParser.PackageLite pkg) throws IllegalDependencyException {
        int depIdx;
        int targetIdx;
        SparseArray<int[]> splitDependencies = new SparseArray<>();
        splitDependencies.put(0, new int[]{-1});
        for (int splitIdx = 0; splitIdx < pkg.splitNames.length; splitIdx++) {
            if (pkg.isFeatureSplits[splitIdx]) {
                String splitDependency = pkg.usesSplitNames[splitIdx];
                if (splitDependency != null) {
                    int depIdx2 = Arrays.binarySearch(pkg.splitNames, splitDependency);
                    if (depIdx2 < 0) {
                        throw new IllegalDependencyException("Split '" + pkg.splitNames[splitIdx] + "' requires split '" + splitDependency + "', which is missing.");
                    }
                    targetIdx = depIdx2 + 1;
                } else {
                    targetIdx = 0;
                }
                splitDependencies.put(splitIdx + 1, new int[]{targetIdx});
            }
        }
        for (int splitIdx2 = 0; splitIdx2 < pkg.splitNames.length; splitIdx2++) {
            if (!pkg.isFeatureSplits[splitIdx2]) {
                String configForSplit = pkg.configForSplit[splitIdx2];
                if (configForSplit != null) {
                    int depIdx3 = Arrays.binarySearch(pkg.splitNames, configForSplit);
                    if (depIdx3 < 0) {
                        throw new IllegalDependencyException("Split '" + pkg.splitNames[splitIdx2] + "' targets split '" + configForSplit + "', which is missing.");
                    } else if (!pkg.isFeatureSplits[depIdx3]) {
                        throw new IllegalDependencyException("Split '" + pkg.splitNames[splitIdx2] + "' declares itself as configuration split for a non-feature split '" + pkg.splitNames[depIdx3] + "'");
                    } else {
                        depIdx = depIdx3 + 1;
                    }
                } else {
                    depIdx = 0;
                }
                splitDependencies.put(depIdx, append(splitDependencies.get(depIdx), splitIdx2 + 1));
            }
        }
        BitSet bitset = new BitSet();
        int size = splitDependencies.size();
        for (int i = 0; i < size; i++) {
            int splitIdx3 = splitDependencies.keyAt(i);
            bitset.clear();
            while (splitIdx3 != -1) {
                if (bitset.get(splitIdx3)) {
                    throw new IllegalDependencyException("Cycle detected in split dependencies.");
                }
                bitset.set(splitIdx3);
                int[] deps = splitDependencies.get(splitIdx3);
                splitIdx3 = deps != null ? deps[0] : -1;
            }
        }
        return splitDependencies;
    }
}