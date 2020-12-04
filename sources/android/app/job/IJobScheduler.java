package android.app.job;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.notification.ZenModeConfig;
import java.util.List;

public interface IJobScheduler extends IInterface {
    void cancel(int i) throws RemoteException;

    void cancelAll() throws RemoteException;

    int enqueue(JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException;

    ParceledListSlice getAllJobSnapshots() throws RemoteException;

    ParceledListSlice getAllPendingJobs() throws RemoteException;

    JobInfo getPendingJob(int i) throws RemoteException;

    List<JobInfo> getStartedJobs() throws RemoteException;

    int schedule(JobInfo jobInfo) throws RemoteException;

    int scheduleAsPackage(JobInfo jobInfo, String str, int i, String str2) throws RemoteException;

    public static class Default implements IJobScheduler {
        public int schedule(JobInfo job) throws RemoteException {
            return 0;
        }

        public int enqueue(JobInfo job, JobWorkItem work) throws RemoteException {
            return 0;
        }

        public int scheduleAsPackage(JobInfo job, String packageName, int userId, String tag) throws RemoteException {
            return 0;
        }

        public void cancel(int jobId) throws RemoteException {
        }

        public void cancelAll() throws RemoteException {
        }

        public ParceledListSlice getAllPendingJobs() throws RemoteException {
            return null;
        }

        public JobInfo getPendingJob(int jobId) throws RemoteException {
            return null;
        }

        public List<JobInfo> getStartedJobs() throws RemoteException {
            return null;
        }

        public ParceledListSlice getAllJobSnapshots() throws RemoteException {
            return null;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IJobScheduler {
        private static final String DESCRIPTOR = "android.app.job.IJobScheduler";
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_cancelAll = 5;
        static final int TRANSACTION_enqueue = 2;
        static final int TRANSACTION_getAllJobSnapshots = 9;
        static final int TRANSACTION_getAllPendingJobs = 6;
        static final int TRANSACTION_getPendingJob = 7;
        static final int TRANSACTION_getStartedJobs = 8;
        static final int TRANSACTION_schedule = 1;
        static final int TRANSACTION_scheduleAsPackage = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IJobScheduler asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IJobScheduler)) {
                return new Proxy(obj);
            }
            return (IJobScheduler) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return ZenModeConfig.SCHEDULE_PATH;
                case 2:
                    return "enqueue";
                case 3:
                    return "scheduleAsPackage";
                case 4:
                    return "cancel";
                case 5:
                    return "cancelAll";
                case 6:
                    return "getAllPendingJobs";
                case 7:
                    return "getPendingJob";
                case 8:
                    return "getStartedJobs";
                case 9:
                    return "getAllJobSnapshots";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: android.app.job.JobWorkItem} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.app.job.JobWorkItem} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: android.app.job.JobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: android.app.job.JobWorkItem} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: android.app.job.JobWorkItem} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: android.app.job.JobInfo} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: android.app.job.JobWorkItem} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v17, resolved type: android.app.job.JobWorkItem} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: android.app.job.JobWorkItem} */
        /* JADX WARNING: type inference failed for: r3v4, types: [android.app.job.JobInfo] */
        /* JADX WARNING: type inference failed for: r3v12, types: [android.app.job.JobInfo] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r8, android.os.Parcel r9, android.os.Parcel r10, int r11) throws android.os.RemoteException {
            /*
                r7 = this;
                java.lang.String r0 = "android.app.job.IJobScheduler"
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r2 = 1
                if (r8 == r1) goto L_0x00fb
                r1 = 0
                r3 = 0
                switch(r8) {
                    case 1: goto L_0x00db;
                    case 2: goto L_0x00ad;
                    case 3: goto L_0x0081;
                    case 4: goto L_0x0073;
                    case 5: goto L_0x0069;
                    case 6: goto L_0x0052;
                    case 7: goto L_0x0037;
                    case 8: goto L_0x0029;
                    case 9: goto L_0x0012;
                    default: goto L_0x000d;
                }
            L_0x000d:
                boolean r1 = super.onTransact(r8, r9, r10, r11)
                return r1
            L_0x0012:
                r9.enforceInterface(r0)
                android.content.pm.ParceledListSlice r3 = r7.getAllJobSnapshots()
                r10.writeNoException()
                if (r3 == 0) goto L_0x0025
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x0028
            L_0x0025:
                r10.writeInt(r1)
            L_0x0028:
                return r2
            L_0x0029:
                r9.enforceInterface(r0)
                java.util.List r1 = r7.getStartedJobs()
                r10.writeNoException()
                r10.writeTypedList(r1)
                return r2
            L_0x0037:
                r9.enforceInterface(r0)
                int r3 = r9.readInt()
                android.app.job.JobInfo r4 = r7.getPendingJob(r3)
                r10.writeNoException()
                if (r4 == 0) goto L_0x004e
                r10.writeInt(r2)
                r4.writeToParcel(r10, r2)
                goto L_0x0051
            L_0x004e:
                r10.writeInt(r1)
            L_0x0051:
                return r2
            L_0x0052:
                r9.enforceInterface(r0)
                android.content.pm.ParceledListSlice r3 = r7.getAllPendingJobs()
                r10.writeNoException()
                if (r3 == 0) goto L_0x0065
                r10.writeInt(r2)
                r3.writeToParcel(r10, r2)
                goto L_0x0068
            L_0x0065:
                r10.writeInt(r1)
            L_0x0068:
                return r2
            L_0x0069:
                r9.enforceInterface(r0)
                r7.cancelAll()
                r10.writeNoException()
                return r2
            L_0x0073:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                r7.cancel(r1)
                r10.writeNoException()
                return r2
            L_0x0081:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x0094
                android.os.Parcelable$Creator<android.app.job.JobInfo> r1 = android.app.job.JobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r3 = r1
                android.app.job.JobInfo r3 = (android.app.job.JobInfo) r3
                goto L_0x0095
            L_0x0094:
            L_0x0095:
                r1 = r3
                java.lang.String r3 = r9.readString()
                int r4 = r9.readInt()
                java.lang.String r5 = r9.readString()
                int r6 = r7.scheduleAsPackage(r1, r3, r4, r5)
                r10.writeNoException()
                r10.writeInt(r6)
                return r2
            L_0x00ad:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x00bf
                android.os.Parcelable$Creator<android.app.job.JobInfo> r1 = android.app.job.JobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                android.app.job.JobInfo r1 = (android.app.job.JobInfo) r1
                goto L_0x00c0
            L_0x00bf:
                r1 = r3
            L_0x00c0:
                int r4 = r9.readInt()
                if (r4 == 0) goto L_0x00cf
                android.os.Parcelable$Creator<android.app.job.JobWorkItem> r3 = android.app.job.JobWorkItem.CREATOR
                java.lang.Object r3 = r3.createFromParcel(r9)
                android.app.job.JobWorkItem r3 = (android.app.job.JobWorkItem) r3
                goto L_0x00d0
            L_0x00cf:
            L_0x00d0:
                int r4 = r7.enqueue(r1, r3)
                r10.writeNoException()
                r10.writeInt(r4)
                return r2
            L_0x00db:
                r9.enforceInterface(r0)
                int r1 = r9.readInt()
                if (r1 == 0) goto L_0x00ee
                android.os.Parcelable$Creator<android.app.job.JobInfo> r1 = android.app.job.JobInfo.CREATOR
                java.lang.Object r1 = r1.createFromParcel(r9)
                r3 = r1
                android.app.job.JobInfo r3 = (android.app.job.JobInfo) r3
                goto L_0x00ef
            L_0x00ee:
            L_0x00ef:
                r1 = r3
                int r3 = r7.schedule(r1)
                r10.writeNoException()
                r10.writeInt(r3)
                return r2
            L_0x00fb:
                r10.writeString(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.job.IJobScheduler.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        private static class Proxy implements IJobScheduler {
            public static IJobScheduler sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public int schedule(JobInfo job) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (job != null) {
                        _data.writeInt(1);
                        job.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().schedule(job);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int enqueue(JobInfo job, JobWorkItem work) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (job != null) {
                        _data.writeInt(1);
                        job.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (work != null) {
                        _data.writeInt(1);
                        work.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().enqueue(job, work);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int scheduleAsPackage(JobInfo job, String packageName, int userId, String tag) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (job != null) {
                        _data.writeInt(1);
                        job.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeString(tag);
                    if (!this.mRemote.transact(3, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().scheduleAsPackage(job, packageName, userId, tag);
                    }
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancel(int jobId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(jobId);
                    if (this.mRemote.transact(4, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancel(jobId);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void cancelAll() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (this.mRemote.transact(5, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().cancelAll();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAllPendingJobs() throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllPendingJobs();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public JobInfo getPendingJob(int jobId) throws RemoteException {
                JobInfo _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(jobId);
                    if (!this.mRemote.transact(7, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getPendingJob(jobId);
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = JobInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    JobInfo _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<JobInfo> getStartedJobs() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getStartedJobs();
                    }
                    _reply.readException();
                    List<JobInfo> _result = _reply.createTypedArrayList(JobInfo.CREATOR);
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ParceledListSlice getAllJobSnapshots() throws RemoteException {
                ParceledListSlice _result;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, _data, _reply, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getAllJobSnapshots();
                    }
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = ParceledListSlice.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    ParceledListSlice _result2 = _result;
                    _reply.recycle();
                    _data.recycle();
                    return _result2;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IJobScheduler impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IJobScheduler getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
