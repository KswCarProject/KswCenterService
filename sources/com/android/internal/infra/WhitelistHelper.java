package com.android.internal.infra;

import android.content.ComponentName;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.util.List;

public final class WhitelistHelper {
    private static final String TAG = "WhitelistHelper";
    private ArrayMap<String, ArraySet<ComponentName>> mWhitelistedPackages;

    public void setWhitelist(ArraySet<String> packageNames, ArraySet<ComponentName> components) {
        this.mWhitelistedPackages = null;
        if (packageNames != null || components != null) {
            if ((packageNames == null || !packageNames.isEmpty()) && (components == null || !components.isEmpty())) {
                this.mWhitelistedPackages = new ArrayMap<>();
                int i = 0;
                if (packageNames != null) {
                    for (int i2 = 0; i2 < packageNames.size(); i2++) {
                        this.mWhitelistedPackages.put(packageNames.valueAt(i2), null);
                    }
                }
                if (components != null) {
                    while (true) {
                        int i3 = i;
                        if (i3 < components.size()) {
                            ComponentName component = components.valueAt(i3);
                            if (component == null) {
                                Log.w(TAG, "setWhitelist(): component is null");
                            } else {
                                String packageName = component.getPackageName();
                                ArraySet<ComponentName> set = this.mWhitelistedPackages.get(packageName);
                                if (set == null) {
                                    set = new ArraySet<>();
                                    this.mWhitelistedPackages.put(packageName, set);
                                }
                                set.add(component);
                            }
                            i = i3 + 1;
                        } else {
                            return;
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Packages or Components cannot be empty.");
            }
        }
    }

    public void setWhitelist(List<String> packageNames, List<ComponentName> components) {
        ArraySet<ComponentName> componentsSet = null;
        ArraySet<String> packageNamesSet = packageNames == null ? null : new ArraySet<>(packageNames);
        if (components != null) {
            componentsSet = new ArraySet<>(components);
        }
        setWhitelist(packageNamesSet, componentsSet);
    }

    public boolean isWhitelisted(String packageName) {
        Preconditions.checkNotNull(packageName);
        if (this.mWhitelistedPackages != null && this.mWhitelistedPackages.containsKey(packageName) && this.mWhitelistedPackages.get(packageName) == null) {
            return true;
        }
        return false;
    }

    public boolean isWhitelisted(ComponentName componentName) {
        Preconditions.checkNotNull(componentName);
        String packageName = componentName.getPackageName();
        ArraySet<ComponentName> whitelistedComponents = getWhitelistedComponents(packageName);
        if (whitelistedComponents != null) {
            return whitelistedComponents.contains(componentName);
        }
        return isWhitelisted(packageName);
    }

    public ArraySet<ComponentName> getWhitelistedComponents(String packageName) {
        Preconditions.checkNotNull(packageName);
        if (this.mWhitelistedPackages == null) {
            return null;
        }
        return this.mWhitelistedPackages.get(packageName);
    }

    public String toString() {
        return "WhitelistHelper[" + this.mWhitelistedPackages + ']';
    }

    public void dump(String prefix, String message, PrintWriter pw) {
        if (this.mWhitelistedPackages == null || this.mWhitelistedPackages.size() == 0) {
            pw.print(prefix);
            pw.print(message);
            pw.println(": (no whitelisted packages)");
            return;
        }
        String prefix2 = prefix + "  ";
        int size = this.mWhitelistedPackages.size();
        pw.print(prefix);
        pw.print(message);
        pw.print(": ");
        pw.print(size);
        pw.println(" packages");
        for (int i = 0; i < this.mWhitelistedPackages.size(); i++) {
            ArraySet<ComponentName> components = this.mWhitelistedPackages.valueAt(i);
            pw.print(prefix2);
            pw.print(i);
            pw.print(".");
            pw.print(this.mWhitelistedPackages.keyAt(i));
            pw.print(": ");
            if (components == null) {
                pw.println("(whole package)");
            } else {
                pw.print("[");
                pw.print(components.valueAt(0));
                for (int j = 1; j < components.size(); j++) {
                    pw.print(", ");
                    pw.print(components.valueAt(j));
                }
                pw.println("]");
            }
        }
    }
}
