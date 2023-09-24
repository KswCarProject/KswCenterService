package android.view.autofill;

import android.graphics.Rect;
import android.p007os.Binder;
import android.p007os.IBinder;
import android.p007os.IInterface;
import android.p007os.Parcel;
import android.p007os.RemoteException;
import android.view.ThreadedRenderer;
import android.view.WindowManager;

/* loaded from: classes4.dex */
public interface IAutofillWindowPresenter extends IInterface {
    void hide(Rect rect) throws RemoteException;

    void show(WindowManager.LayoutParams layoutParams, Rect rect, boolean z, int i) throws RemoteException;

    /* loaded from: classes4.dex */
    public static class Default implements IAutofillWindowPresenter {
        @Override // android.view.autofill.IAutofillWindowPresenter
        public void show(WindowManager.LayoutParams p, Rect transitionEpicenter, boolean fitsSystemWindows, int layoutDirection) throws RemoteException {
        }

        @Override // android.view.autofill.IAutofillWindowPresenter
        public void hide(Rect transitionEpicenter) throws RemoteException {
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static abstract class Stub extends Binder implements IAutofillWindowPresenter {
        private static final String DESCRIPTOR = "android.view.autofill.IAutofillWindowPresenter";
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_show = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutofillWindowPresenter asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAutofillWindowPresenter)) {
                return (IAutofillWindowPresenter) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p007os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 2:
                    return "hide";
                default:
                    return null;
            }
        }

        @Override // android.p007os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p007os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            WindowManager.LayoutParams _arg0;
            Rect _arg02;
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg0 = WindowManager.LayoutParams.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    _arg02 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    boolean _arg2 = data.readInt() != 0;
                    int _arg3 = data.readInt();
                    show(_arg0, _arg02, _arg2, _arg3);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    _arg02 = data.readInt() != 0 ? Rect.CREATOR.createFromParcel(data) : null;
                    hide(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        /* loaded from: classes4.dex */
        private static class Proxy implements IAutofillWindowPresenter {
            public static IAutofillWindowPresenter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p007os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAutofillWindowPresenter
            public void show(WindowManager.LayoutParams p, Rect transitionEpicenter, boolean fitsSystemWindows, int layoutDirection) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (p != null) {
                        _data.writeInt(1);
                        p.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    if (transitionEpicenter != null) {
                        _data.writeInt(1);
                        transitionEpicenter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(fitsSystemWindows ? 1 : 0);
                    _data.writeInt(layoutDirection);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().show(p, transitionEpicenter, fitsSystemWindows, layoutDirection);
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutofillWindowPresenter
            public void hide(Rect transitionEpicenter) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (transitionEpicenter != null) {
                        _data.writeInt(1);
                        transitionEpicenter.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hide(transitionEpicenter);
                    }
                } finally {
                    _data.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IAutofillWindowPresenter impl) {
            if (Proxy.sDefaultImpl == null && impl != null) {
                Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static IAutofillWindowPresenter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
