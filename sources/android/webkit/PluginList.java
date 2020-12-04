package android.webkit;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PluginList {
    private ArrayList<Plugin> mPlugins = new ArrayList<>();

    @Deprecated
    public synchronized List getList() {
        return this.mPlugins;
    }

    @Deprecated
    public synchronized void addPlugin(Plugin plugin) {
        if (!this.mPlugins.contains(plugin)) {
            this.mPlugins.add(plugin);
        }
    }

    @Deprecated
    public synchronized void removePlugin(Plugin plugin) {
        int location = this.mPlugins.indexOf(plugin);
        if (location != -1) {
            this.mPlugins.remove(location);
        }
    }

    @Deprecated
    public synchronized void clear() {
        this.mPlugins.clear();
    }

    /* Debug info: failed to restart local var, previous not found, register: 1 */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void pluginClicked(android.content.Context r2, int r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.ArrayList<android.webkit.Plugin> r0 = r1.mPlugins     // Catch:{ IndexOutOfBoundsException -> 0x0010, all -> 0x000d }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ IndexOutOfBoundsException -> 0x0010, all -> 0x000d }
            android.webkit.Plugin r0 = (android.webkit.Plugin) r0     // Catch:{ IndexOutOfBoundsException -> 0x0010, all -> 0x000d }
            r0.dispatchClickEvent(r2)     // Catch:{ IndexOutOfBoundsException -> 0x0010, all -> 0x000d }
            goto L_0x0011
        L_0x000d:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        L_0x0010:
            r0 = move-exception
        L_0x0011:
            monitor-exit(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.webkit.PluginList.pluginClicked(android.content.Context, int):void");
    }
}
