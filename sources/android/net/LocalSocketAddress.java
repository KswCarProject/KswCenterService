package android.net;

/* loaded from: classes3.dex */
public class LocalSocketAddress {
    private final String name;
    private final Namespace namespace;

    /* loaded from: classes3.dex */
    public enum Namespace {
        ABSTRACT(0),
        RESERVED(1),
        FILESYSTEM(2);
        

        /* renamed from: id */
        private int f129id;

        Namespace(int id) {
            this.f129id = id;
        }

        int getId() {
            return this.f129id;
        }
    }

    public LocalSocketAddress(String name, Namespace namespace) {
        this.name = name;
        this.namespace = namespace;
    }

    public LocalSocketAddress(String name) {
        this(name, Namespace.ABSTRACT);
    }

    public String getName() {
        return this.name;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }
}
