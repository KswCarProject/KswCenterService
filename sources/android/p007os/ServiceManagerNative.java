package android.p007os;

import android.annotation.UnsupportedAppUsage;
import android.p007os.IPermissionController;

/* renamed from: android.os.ServiceManagerNative */
/* loaded from: classes3.dex */
public abstract class ServiceManagerNative extends Binder implements IServiceManager {
    @UnsupportedAppUsage
    public static IServiceManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IServiceManager in = (IServiceManager) obj.queryLocalInterface(IServiceManager.descriptor);
        if (in != null) {
            return in;
        }
        return new ServiceManagerProxy(obj);
    }

    public ServiceManagerNative() {
        attachInterface(this, IServiceManager.descriptor);
    }

    @Override // android.p007os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
        if (code != 6) {
            switch (code) {
                case 1:
                    data.enforceInterface(IServiceManager.descriptor);
                    String name = data.readString();
                    IBinder service = getService(name);
                    reply.writeStrongBinder(service);
                    return true;
                case 2:
                    data.enforceInterface(IServiceManager.descriptor);
                    String name2 = data.readString();
                    IBinder service2 = checkService(name2);
                    reply.writeStrongBinder(service2);
                    return true;
                case 3:
                    data.enforceInterface(IServiceManager.descriptor);
                    String name3 = data.readString();
                    IBinder service3 = data.readStrongBinder();
                    boolean allowIsolated = data.readInt() != 0;
                    int dumpPriority = data.readInt();
                    addService(name3, service3, allowIsolated, dumpPriority);
                    return true;
                case 4:
                    data.enforceInterface(IServiceManager.descriptor);
                    int dumpPriority2 = data.readInt();
                    String[] list = listServices(dumpPriority2);
                    reply.writeStringArray(list);
                    return true;
            }
            return false;
        }
        data.enforceInterface(IServiceManager.descriptor);
        IPermissionController controller = IPermissionController.Stub.asInterface(data.readStrongBinder());
        setPermissionController(controller);
        return true;
    }

    @Override // android.p007os.IInterface
    public IBinder asBinder() {
        return this;
    }
}