package android.net;

import android.annotation.UnsupportedAppUsage;
import android.system.ErrnoException;
import android.system.Int32Ref;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructLinger;
import android.system.StructTimeval;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes3.dex */
class LocalSocketImpl {

    /* renamed from: fd */
    private FileDescriptor f130fd;
    private SocketInputStream fis;
    private SocketOutputStream fos;
    @UnsupportedAppUsage
    FileDescriptor[] inboundFileDescriptors;
    private boolean mFdCreatedInternally;
    @UnsupportedAppUsage
    FileDescriptor[] outboundFileDescriptors;
    private Object readMonitor = new Object();
    private Object writeMonitor = new Object();

    private native void bindLocal(FileDescriptor fileDescriptor, String str, int i) throws IOException;

    private native void connectLocal(FileDescriptor fileDescriptor, String str, int i) throws IOException;

    private native Credentials getPeerCredentials_native(FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int read_native(FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native int readba_native(byte[] bArr, int i, int i2, FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void write_native(int i, FileDescriptor fileDescriptor) throws IOException;

    /* JADX INFO: Access modifiers changed from: private */
    public native void writeba_native(byte[] bArr, int i, int i2, FileDescriptor fileDescriptor) throws IOException;

    /* loaded from: classes3.dex */
    class SocketInputStream extends InputStream {
        SocketInputStream() {
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            FileDescriptor myFd = LocalSocketImpl.this.f130fd;
            if (myFd == null) {
                throw new IOException("socket closed");
            }
            Int32Ref avail = new Int32Ref(0);
            try {
                Os.ioctlInt(myFd, OsConstants.FIONREAD, avail);
                return avail.value;
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            LocalSocketImpl.this.close();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            int ret;
            synchronized (LocalSocketImpl.this.readMonitor) {
                FileDescriptor myFd = LocalSocketImpl.this.f130fd;
                if (myFd != null) {
                    ret = LocalSocketImpl.this.read_native(myFd);
                } else {
                    throw new IOException("socket closed");
                }
            }
            return ret;
        }

        @Override // java.io.InputStream
        public int read(byte[] b) throws IOException {
            return read(b, 0, b.length);
        }

        @Override // java.io.InputStream
        public int read(byte[] b, int off, int len) throws IOException {
            int ret;
            synchronized (LocalSocketImpl.this.readMonitor) {
                FileDescriptor myFd = LocalSocketImpl.this.f130fd;
                if (myFd == null) {
                    throw new IOException("socket closed");
                }
                if (off >= 0 && len >= 0 && off + len <= b.length) {
                    ret = LocalSocketImpl.this.readba_native(b, off, len, myFd);
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
            return ret;
        }
    }

    /* loaded from: classes3.dex */
    class SocketOutputStream extends OutputStream {
        SocketOutputStream() {
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            LocalSocketImpl.this.close();
        }

        @Override // java.io.OutputStream
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] b, int off, int len) throws IOException {
            synchronized (LocalSocketImpl.this.writeMonitor) {
                FileDescriptor myFd = LocalSocketImpl.this.f130fd;
                if (myFd == null) {
                    throw new IOException("socket closed");
                }
                if (off >= 0 && len >= 0 && off + len <= b.length) {
                    LocalSocketImpl.this.writeba_native(b, off, len, myFd);
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        }

        @Override // java.io.OutputStream
        public void write(int b) throws IOException {
            synchronized (LocalSocketImpl.this.writeMonitor) {
                FileDescriptor myFd = LocalSocketImpl.this.f130fd;
                if (myFd != null) {
                    LocalSocketImpl.this.write_native(b, myFd);
                } else {
                    throw new IOException("socket closed");
                }
            }
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            FileDescriptor myFd = LocalSocketImpl.this.f130fd;
            if (myFd == null) {
                throw new IOException("socket closed");
            }
            Int32Ref pending = new Int32Ref(0);
            while (true) {
                try {
                    Os.ioctlInt(myFd, OsConstants.TIOCOUTQ, pending);
                    if (pending.value > 0) {
                        try {
                            Thread.sleep(10L);
                        } catch (InterruptedException e) {
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (ErrnoException e2) {
                    throw e2.rethrowAsIOException();
                }
            }
        }
    }

    @UnsupportedAppUsage
    LocalSocketImpl() {
    }

    LocalSocketImpl(FileDescriptor fd) {
        this.f130fd = fd;
    }

    public String toString() {
        return super.toString() + " fd:" + this.f130fd;
    }

    public void create(int sockType) throws IOException {
        int osType;
        if (this.f130fd != null) {
            throw new IOException("LocalSocketImpl already has an fd");
        }
        switch (sockType) {
            case 1:
                osType = OsConstants.SOCK_DGRAM;
                break;
            case 2:
                osType = OsConstants.SOCK_STREAM;
                break;
            case 3:
                osType = OsConstants.SOCK_SEQPACKET;
                break;
            default:
                throw new IllegalStateException("unknown sockType");
        }
        try {
            this.f130fd = Os.socket(OsConstants.AF_UNIX, osType, 0);
            this.mFdCreatedInternally = true;
        } catch (ErrnoException e) {
            e.rethrowAsIOException();
        }
    }

    public void close() throws IOException {
        synchronized (this) {
            if (this.f130fd == null || !this.mFdCreatedInternally) {
                this.f130fd = null;
                return;
            }
            try {
                Os.close(this.f130fd);
            } catch (ErrnoException e) {
                e.rethrowAsIOException();
            }
            this.f130fd = null;
        }
    }

    protected void connect(LocalSocketAddress address, int timeout) throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        connectLocal(this.f130fd, address.getName(), address.getNamespace().getId());
    }

    public void bind(LocalSocketAddress endpoint) throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        bindLocal(this.f130fd, endpoint.getName(), endpoint.getNamespace().getId());
    }

    protected void listen(int backlog) throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        try {
            Os.listen(this.f130fd, backlog);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected void accept(LocalSocketImpl s) throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        try {
            s.f130fd = Os.accept(this.f130fd, null);
            s.mFdCreatedInternally = true;
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected InputStream getInputStream() throws IOException {
        SocketInputStream socketInputStream;
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        synchronized (this) {
            if (this.fis == null) {
                this.fis = new SocketInputStream();
            }
            socketInputStream = this.fis;
        }
        return socketInputStream;
    }

    protected OutputStream getOutputStream() throws IOException {
        SocketOutputStream socketOutputStream;
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        synchronized (this) {
            if (this.fos == null) {
                this.fos = new SocketOutputStream();
            }
            socketOutputStream = this.fos;
        }
        return socketOutputStream;
    }

    protected int available() throws IOException {
        return getInputStream().available();
    }

    protected void shutdownInput() throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        try {
            Os.shutdown(this.f130fd, OsConstants.SHUT_RD);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected void shutdownOutput() throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        try {
            Os.shutdown(this.f130fd, OsConstants.SHUT_WR);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    protected FileDescriptor getFileDescriptor() {
        return this.f130fd;
    }

    protected boolean supportsUrgentData() {
        return false;
    }

    protected void sendUrgentData(int data) throws IOException {
        throw new RuntimeException("not impled");
    }

    public Object getOption(int optID) throws IOException {
        Object toReturn;
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        try {
            if (optID != 1) {
                if (optID != 4) {
                    if (optID == 128) {
                        StructLinger linger = Os.getsockoptLinger(this.f130fd, OsConstants.SOL_SOCKET, OsConstants.SO_LINGER);
                        if (!linger.isOn()) {
                            toReturn = -1;
                        } else {
                            toReturn = Integer.valueOf(linger.l_linger);
                        }
                    } else if (optID == 4102) {
                        StructTimeval timeval = Os.getsockoptTimeval(this.f130fd, OsConstants.SOL_SOCKET, OsConstants.SO_SNDTIMEO);
                        toReturn = Integer.valueOf((int) timeval.toMillis());
                    } else {
                        switch (optID) {
                            case 4097:
                            case 4098:
                                break;
                            default:
                                throw new IOException("Unknown option: " + optID);
                        }
                    }
                }
                int osOpt = javaSoToOsOpt(optID);
                toReturn = Integer.valueOf(Os.getsockoptInt(this.f130fd, OsConstants.SOL_SOCKET, osOpt));
            } else {
                toReturn = Integer.valueOf(Os.getsockoptInt(this.f130fd, OsConstants.IPPROTO_TCP, OsConstants.TCP_NODELAY));
            }
            return toReturn;
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public void setOption(int optID, Object value) throws IOException {
        if (this.f130fd == null) {
            throw new IOException("socket not created");
        }
        int boolValue = -1;
        int intValue = 0;
        if (value instanceof Integer) {
            intValue = ((Integer) value).intValue();
        } else if (value instanceof Boolean) {
            boolValue = ((Boolean) value).booleanValue();
        } else {
            throw new IOException("bad value: " + value);
        }
        try {
            if (optID != 1) {
                if (optID != 4) {
                    if (optID == 128) {
                        StructLinger linger = new StructLinger(boolValue, intValue);
                        Os.setsockoptLinger(this.f130fd, OsConstants.SOL_SOCKET, OsConstants.SO_LINGER, linger);
                        return;
                    } else if (optID == 4102) {
                        StructTimeval timeval = StructTimeval.fromMillis(intValue);
                        Os.setsockoptTimeval(this.f130fd, OsConstants.SOL_SOCKET, OsConstants.SO_RCVTIMEO, timeval);
                        Os.setsockoptTimeval(this.f130fd, OsConstants.SOL_SOCKET, OsConstants.SO_SNDTIMEO, timeval);
                        return;
                    } else {
                        switch (optID) {
                            case 4097:
                            case 4098:
                                break;
                            default:
                                throw new IOException("Unknown option: " + optID);
                        }
                    }
                }
                int osOpt = javaSoToOsOpt(optID);
                Os.setsockoptInt(this.f130fd, OsConstants.SOL_SOCKET, osOpt, intValue);
                return;
            }
            Os.setsockoptInt(this.f130fd, OsConstants.IPPROTO_TCP, OsConstants.TCP_NODELAY, intValue);
        } catch (ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    public void setFileDescriptorsForSend(FileDescriptor[] fds) {
        synchronized (this.writeMonitor) {
            this.outboundFileDescriptors = fds;
        }
    }

    public FileDescriptor[] getAncillaryFileDescriptors() throws IOException {
        FileDescriptor[] result;
        synchronized (this.readMonitor) {
            result = this.inboundFileDescriptors;
            this.inboundFileDescriptors = null;
        }
        return result;
    }

    public Credentials getPeerCredentials() throws IOException {
        return getPeerCredentials_native(this.f130fd);
    }

    public LocalSocketAddress getSockAddress() throws IOException {
        return null;
    }

    protected void finalize() throws IOException {
        close();
    }

    private static int javaSoToOsOpt(int optID) {
        if (optID != 4) {
            switch (optID) {
                case 4097:
                    return OsConstants.SO_SNDBUF;
                case 4098:
                    return OsConstants.SO_RCVBUF;
                default:
                    throw new UnsupportedOperationException("Unknown option: " + optID);
            }
        }
        return OsConstants.SO_REUSEADDR;
    }
}
