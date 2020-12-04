package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.hardware.contexthub.V1_0.HostEndPoint;
import android.net.LocalSocket;
import android.os.ParcelFileDescriptor;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

public final class BluetoothSocket implements Closeable {
    static final int BTSOCK_FLAG_NO_SDP = 4;
    private static final boolean DBG = Log.isLoggable(TAG, 3);
    @UnsupportedAppUsage
    static final int EADDRINUSE = 98;
    static final int EBADFD = 77;
    static final int MAX_L2CAP_PACKAGE_SIZE = 65535;
    public static final int MAX_RFCOMM_CHANNEL = 30;
    private static final int PROXY_CONNECTION_TIMEOUT = 5000;
    static final int SEC_FLAG_AUTH = 2;
    static final int SEC_FLAG_AUTH_16_DIGIT = 16;
    static final int SEC_FLAG_AUTH_MITM = 8;
    static final int SEC_FLAG_ENCRYPT = 1;
    private static final int SOCK_SIGNAL_SIZE = 20;
    private static final String TAG = "BluetoothSocket";
    public static final int TYPE_L2CAP = 3;
    public static final int TYPE_L2CAP_BREDR = 3;
    public static final int TYPE_L2CAP_LE = 4;
    public static final int TYPE_RFCOMM = 1;
    public static final int TYPE_SCO = 2;
    private static final boolean VDBG = Log.isLoggable(TAG, 2);
    private String mAddress;
    private final boolean mAuth;
    private boolean mAuthMitm;
    private BluetoothDevice mDevice;
    private final boolean mEncrypt;
    private boolean mExcludeSdp;
    private int mFd;
    private final BluetoothInputStream mInputStream;
    private ByteBuffer mL2capBuffer;
    private int mMaxRxPacketSize;
    private int mMaxTxPacketSize;
    private boolean mMin16DigitPin;
    private final BluetoothOutputStream mOutputStream;
    @UnsupportedAppUsage
    private ParcelFileDescriptor mPfd;
    @UnsupportedAppUsage
    private int mPort;
    private String mServiceName;
    @UnsupportedAppUsage
    private LocalSocket mSocket;
    private InputStream mSocketIS;
    private OutputStream mSocketOS;
    private volatile SocketState mSocketState;
    private final int mType;
    private final ParcelUuid mUuid;

    private enum SocketState {
        INIT,
        CONNECTED,
        LISTENING,
        CLOSED
    }

    BluetoothSocket(int type, int fd, boolean auth, boolean encrypt, BluetoothDevice device, int port, ParcelUuid uuid) throws IOException {
        this(type, fd, auth, encrypt, device, port, uuid, false, false);
    }

    BluetoothSocket(int type, int fd, boolean auth, boolean encrypt, BluetoothDevice device, int port, ParcelUuid uuid, boolean mitm, boolean min16DigitPin) throws IOException {
        this.mExcludeSdp = false;
        this.mAuthMitm = false;
        this.mMin16DigitPin = false;
        this.mL2capBuffer = null;
        this.mMaxTxPacketSize = 0;
        this.mMaxRxPacketSize = 0;
        if (VDBG) {
            Log.d(TAG, "Creating new BluetoothSocket of type: " + type);
        }
        if (type == 1 && uuid == null && fd == -1 && port != -2 && (port < 1 || port > 30)) {
            throw new IOException("Invalid RFCOMM channel: " + port);
        }
        if (uuid != null) {
            this.mUuid = uuid;
        } else {
            this.mUuid = new ParcelUuid(new UUID(0, 0));
        }
        this.mType = type;
        this.mAuth = auth;
        this.mAuthMitm = mitm;
        this.mMin16DigitPin = min16DigitPin;
        this.mEncrypt = encrypt;
        this.mDevice = device;
        this.mPort = port;
        this.mFd = fd;
        this.mSocketState = SocketState.INIT;
        if (device == null) {
            this.mAddress = BluetoothAdapter.getDefaultAdapter().getAddress();
        } else {
            this.mAddress = device.getAddress();
        }
        this.mInputStream = new BluetoothInputStream(this);
        this.mOutputStream = new BluetoothOutputStream(this);
    }

    private BluetoothSocket(BluetoothSocket s) {
        this.mExcludeSdp = false;
        this.mAuthMitm = false;
        this.mMin16DigitPin = false;
        this.mL2capBuffer = null;
        this.mMaxTxPacketSize = 0;
        this.mMaxRxPacketSize = 0;
        if (VDBG) {
            Log.d(TAG, "Creating new Private BluetoothSocket of type: " + s.mType);
        }
        this.mUuid = s.mUuid;
        this.mType = s.mType;
        this.mAuth = s.mAuth;
        this.mEncrypt = s.mEncrypt;
        this.mPort = s.mPort;
        this.mInputStream = new BluetoothInputStream(this);
        this.mOutputStream = new BluetoothOutputStream(this);
        this.mMaxRxPacketSize = s.mMaxRxPacketSize;
        this.mMaxTxPacketSize = s.mMaxTxPacketSize;
        this.mServiceName = s.mServiceName;
        this.mExcludeSdp = s.mExcludeSdp;
        this.mAuthMitm = s.mAuthMitm;
        this.mMin16DigitPin = s.mMin16DigitPin;
    }

    private BluetoothSocket acceptSocket(String remoteAddr) throws IOException {
        BluetoothSocket as = new BluetoothSocket(this);
        as.mSocketState = SocketState.CONNECTED;
        FileDescriptor[] fds = this.mSocket.getAncillaryFileDescriptors();
        if (DBG) {
            Log.d(TAG, "socket fd passed by stack fds: " + Arrays.toString(fds));
        }
        if (fds == null || fds.length != 1) {
            Log.e(TAG, "socket fd passed from stack failed, fds: " + Arrays.toString(fds));
            as.close();
            throw new IOException("bt socket acept failed");
        }
        as.mPfd = new ParcelFileDescriptor(fds[0]);
        as.mSocket = LocalSocket.createConnectedLocalSocket(fds[0]);
        as.mSocketIS = as.mSocket.getInputStream();
        as.mSocketOS = as.mSocket.getOutputStream();
        as.mAddress = remoteAddr;
        as.mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(remoteAddr);
        as.mPort = this.mPort;
        return as;
    }

    private BluetoothSocket(int type, int fd, boolean auth, boolean encrypt, String address, int port) throws IOException {
        this(type, fd, auth, encrypt, new BluetoothDevice(address), port, (ParcelUuid) null, false, false);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private int getSecurityFlags() {
        int flags = 0;
        if (this.mAuth) {
            flags = 0 | 2;
        }
        if (this.mEncrypt) {
            flags |= 1;
        }
        if (this.mExcludeSdp) {
            flags |= 4;
        }
        if (this.mAuthMitm) {
            flags |= 8;
        }
        if (this.mMin16DigitPin) {
            return flags | 16;
        }
        return flags;
    }

    public BluetoothDevice getRemoteDevice() {
        return this.mDevice;
    }

    public InputStream getInputStream() throws IOException {
        return this.mInputStream;
    }

    public OutputStream getOutputStream() throws IOException {
        return this.mOutputStream;
    }

    public boolean isConnected() {
        return this.mSocketState == SocketState.CONNECTED;
    }

    /* access modifiers changed from: package-private */
    public void setServiceName(String name) {
        this.mServiceName = name;
    }

    public void connect() throws IOException {
        if (this.mDevice != null) {
            try {
                if (this.mSocketState != SocketState.CLOSED) {
                    IBluetooth bluetoothProxy = BluetoothAdapter.getDefaultAdapter().getBluetoothService((IBluetoothManagerCallback) null);
                    if (bluetoothProxy != null) {
                        this.mPfd = bluetoothProxy.getSocketManager().connectSocket(this.mDevice, this.mType, this.mUuid, this.mPort, getSecurityFlags());
                        synchronized (this) {
                            if (DBG) {
                                Log.d(TAG, "connect(), SocketState: " + this.mSocketState + ", mPfd: " + this.mPfd);
                            }
                            if (this.mSocketState == SocketState.CLOSED) {
                                throw new IOException("socket closed");
                            } else if (this.mPfd != null) {
                                this.mSocket = LocalSocket.createConnectedLocalSocket(this.mPfd.getFileDescriptor());
                                this.mSocketIS = this.mSocket.getInputStream();
                                this.mSocketOS = this.mSocket.getOutputStream();
                            } else {
                                throw new IOException("bt socket connect failed");
                            }
                        }
                        int channel = readInt(this.mSocketIS);
                        if (channel > 0) {
                            this.mPort = channel;
                            waitSocketSignal(this.mSocketIS);
                            synchronized (this) {
                                if (this.mSocketState != SocketState.CLOSED) {
                                    this.mSocketState = SocketState.CONNECTED;
                                } else {
                                    throw new IOException("bt socket closed");
                                }
                            }
                            return;
                        }
                        throw new IOException("bt socket connect failed");
                    }
                    throw new IOException("Bluetooth is off");
                }
                throw new IOException("socket closed");
            } catch (RemoteException e) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                throw new IOException("unable to send RPC: " + e.getMessage());
            }
        } else {
            throw new IOException("Connect is called on null device");
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00cf, code lost:
        if (DBG == false) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d1, code lost:
        android.util.Log.d(TAG, "bindListen(), readInt mSocketIS: " + r10.mSocketIS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e9, code lost:
        r2 = readInt(r10.mSocketIS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ef, code lost:
        monitor-enter(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f4, code lost:
        if (r10.mSocketState != android.bluetooth.BluetoothSocket.SocketState.INIT) goto L_0x00fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f6, code lost:
        r10.mSocketState = android.bluetooth.BluetoothSocket.SocketState.LISTENING;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00fa, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00fd, code lost:
        if (DBG == false) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ff, code lost:
        android.util.Log.d(TAG, "bindListen(): channel=" + r2 + ", mPort=" + r10.mPort);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0121, code lost:
        if (r10.mPort > -1) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0123, code lost:
        r10.mPort = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
        return 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int bindListen() {
        /*
            r10 = this;
            android.bluetooth.BluetoothSocket$SocketState r0 = r10.mSocketState
            android.bluetooth.BluetoothSocket$SocketState r1 = android.bluetooth.BluetoothSocket.SocketState.CLOSED
            r2 = 77
            if (r0 != r1) goto L_0x0009
            return r2
        L_0x0009:
            android.bluetooth.BluetoothAdapter r0 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            r1 = 0
            android.bluetooth.IBluetooth r0 = r0.getBluetoothService(r1)
            r3 = -1
            if (r0 != 0) goto L_0x001d
            java.lang.String r1 = "BluetoothSocket"
            java.lang.String r2 = "bindListen fail, reason: bluetooth is off"
            android.util.Log.e(r1, r2)
            return r3
        L_0x001d:
            boolean r4 = DBG     // Catch:{ RemoteException -> 0x016a }
            if (r4 == 0) goto L_0x0043
            java.lang.String r4 = "BluetoothSocket"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x016a }
            r5.<init>()     // Catch:{ RemoteException -> 0x016a }
            java.lang.String r6 = "bindListen(): mPort="
            r5.append(r6)     // Catch:{ RemoteException -> 0x016a }
            int r6 = r10.mPort     // Catch:{ RemoteException -> 0x016a }
            r5.append(r6)     // Catch:{ RemoteException -> 0x016a }
            java.lang.String r6 = ", mType="
            r5.append(r6)     // Catch:{ RemoteException -> 0x016a }
            int r6 = r10.mType     // Catch:{ RemoteException -> 0x016a }
            r5.append(r6)     // Catch:{ RemoteException -> 0x016a }
            java.lang.String r5 = r5.toString()     // Catch:{ RemoteException -> 0x016a }
            android.util.Log.d(r4, r5)     // Catch:{ RemoteException -> 0x016a }
        L_0x0043:
            android.bluetooth.IBluetoothSocketManager r4 = r0.getSocketManager()     // Catch:{ RemoteException -> 0x016a }
            int r5 = r10.mType     // Catch:{ RemoteException -> 0x016a }
            java.lang.String r6 = r10.mServiceName     // Catch:{ RemoteException -> 0x016a }
            android.os.ParcelUuid r7 = r10.mUuid     // Catch:{ RemoteException -> 0x016a }
            int r8 = r10.mPort     // Catch:{ RemoteException -> 0x016a }
            int r9 = r10.getSecurityFlags()     // Catch:{ RemoteException -> 0x016a }
            android.os.ParcelFileDescriptor r4 = r4.createSocketChannel(r5, r6, r7, r8, r9)     // Catch:{ RemoteException -> 0x016a }
            r10.mPfd = r4     // Catch:{ RemoteException -> 0x016a }
            monitor-enter(r10)     // Catch:{ IOException -> 0x012f }
            boolean r4 = DBG     // Catch:{ all -> 0x012c }
            if (r4 == 0) goto L_0x0081
            java.lang.String r4 = "BluetoothSocket"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x012c }
            r5.<init>()     // Catch:{ all -> 0x012c }
            java.lang.String r6 = "bindListen(), SocketState: "
            r5.append(r6)     // Catch:{ all -> 0x012c }
            android.bluetooth.BluetoothSocket$SocketState r6 = r10.mSocketState     // Catch:{ all -> 0x012c }
            r5.append(r6)     // Catch:{ all -> 0x012c }
            java.lang.String r6 = ", mPfd: "
            r5.append(r6)     // Catch:{ all -> 0x012c }
            android.os.ParcelFileDescriptor r6 = r10.mPfd     // Catch:{ all -> 0x012c }
            r5.append(r6)     // Catch:{ all -> 0x012c }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x012c }
            android.util.Log.d(r4, r5)     // Catch:{ all -> 0x012c }
        L_0x0081:
            android.bluetooth.BluetoothSocket$SocketState r4 = r10.mSocketState     // Catch:{ all -> 0x012c }
            android.bluetooth.BluetoothSocket$SocketState r5 = android.bluetooth.BluetoothSocket.SocketState.INIT     // Catch:{ all -> 0x012c }
            if (r4 == r5) goto L_0x0089
            monitor-exit(r10)     // Catch:{ all -> 0x012c }
            return r2
        L_0x0089:
            android.os.ParcelFileDescriptor r2 = r10.mPfd     // Catch:{ all -> 0x012c }
            if (r2 != 0) goto L_0x008f
            monitor-exit(r10)     // Catch:{ all -> 0x012c }
            return r3
        L_0x008f:
            android.os.ParcelFileDescriptor r2 = r10.mPfd     // Catch:{ all -> 0x012c }
            java.io.FileDescriptor r2 = r2.getFileDescriptor()     // Catch:{ all -> 0x012c }
            if (r2 != 0) goto L_0x00a0
            java.lang.String r4 = "BluetoothSocket"
            java.lang.String r5 = "bindListen(), null file descriptor"
            android.util.Log.e(r4, r5)     // Catch:{ all -> 0x012c }
            monitor-exit(r10)     // Catch:{ all -> 0x012c }
            return r3
        L_0x00a0:
            boolean r4 = DBG     // Catch:{ all -> 0x012c }
            if (r4 == 0) goto L_0x00ab
            java.lang.String r4 = "BluetoothSocket"
            java.lang.String r5 = "bindListen(), Create LocalSocket"
            android.util.Log.d(r4, r5)     // Catch:{ all -> 0x012c }
        L_0x00ab:
            android.net.LocalSocket r4 = android.net.LocalSocket.createConnectedLocalSocket(r2)     // Catch:{ all -> 0x012c }
            r10.mSocket = r4     // Catch:{ all -> 0x012c }
            boolean r4 = DBG     // Catch:{ all -> 0x012c }
            if (r4 == 0) goto L_0x00bc
            java.lang.String r4 = "BluetoothSocket"
            java.lang.String r5 = "bindListen(), new LocalSocket.getInputStream()"
            android.util.Log.d(r4, r5)     // Catch:{ all -> 0x012c }
        L_0x00bc:
            android.net.LocalSocket r4 = r10.mSocket     // Catch:{ all -> 0x012c }
            java.io.InputStream r4 = r4.getInputStream()     // Catch:{ all -> 0x012c }
            r10.mSocketIS = r4     // Catch:{ all -> 0x012c }
            android.net.LocalSocket r4 = r10.mSocket     // Catch:{ all -> 0x012c }
            java.io.OutputStream r4 = r4.getOutputStream()     // Catch:{ all -> 0x012c }
            r10.mSocketOS = r4     // Catch:{ all -> 0x012c }
            monitor-exit(r10)     // Catch:{ all -> 0x012c }
            boolean r2 = DBG     // Catch:{ IOException -> 0x012f }
            if (r2 == 0) goto L_0x00e9
            java.lang.String r2 = "BluetoothSocket"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x012f }
            r4.<init>()     // Catch:{ IOException -> 0x012f }
            java.lang.String r5 = "bindListen(), readInt mSocketIS: "
            r4.append(r5)     // Catch:{ IOException -> 0x012f }
            java.io.InputStream r5 = r10.mSocketIS     // Catch:{ IOException -> 0x012f }
            r4.append(r5)     // Catch:{ IOException -> 0x012f }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x012f }
            android.util.Log.d(r2, r4)     // Catch:{ IOException -> 0x012f }
        L_0x00e9:
            java.io.InputStream r2 = r10.mSocketIS     // Catch:{ IOException -> 0x012f }
            int r2 = r10.readInt(r2)     // Catch:{ IOException -> 0x012f }
            monitor-enter(r10)     // Catch:{ IOException -> 0x012f }
            android.bluetooth.BluetoothSocket$SocketState r4 = r10.mSocketState     // Catch:{ all -> 0x0129 }
            android.bluetooth.BluetoothSocket$SocketState r5 = android.bluetooth.BluetoothSocket.SocketState.INIT     // Catch:{ all -> 0x0129 }
            if (r4 != r5) goto L_0x00fa
            android.bluetooth.BluetoothSocket$SocketState r4 = android.bluetooth.BluetoothSocket.SocketState.LISTENING     // Catch:{ all -> 0x0129 }
            r10.mSocketState = r4     // Catch:{ all -> 0x0129 }
        L_0x00fa:
            monitor-exit(r10)     // Catch:{ all -> 0x0129 }
            boolean r4 = DBG     // Catch:{ IOException -> 0x012f }
            if (r4 == 0) goto L_0x011f
            java.lang.String r4 = "BluetoothSocket"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x012f }
            r5.<init>()     // Catch:{ IOException -> 0x012f }
            java.lang.String r6 = "bindListen(): channel="
            r5.append(r6)     // Catch:{ IOException -> 0x012f }
            r5.append(r2)     // Catch:{ IOException -> 0x012f }
            java.lang.String r6 = ", mPort="
            r5.append(r6)     // Catch:{ IOException -> 0x012f }
            int r6 = r10.mPort     // Catch:{ IOException -> 0x012f }
            r5.append(r6)     // Catch:{ IOException -> 0x012f }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x012f }
            android.util.Log.d(r4, r5)     // Catch:{ IOException -> 0x012f }
        L_0x011f:
            int r4 = r10.mPort     // Catch:{ IOException -> 0x012f }
            if (r4 > r3) goto L_0x0125
            r10.mPort = r2     // Catch:{ IOException -> 0x012f }
        L_0x0125:
            r1 = 0
            return r1
        L_0x0129:
            r4 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0129 }
            throw r4     // Catch:{ IOException -> 0x012f }
        L_0x012c:
            r2 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x012c }
            throw r2     // Catch:{ IOException -> 0x012f }
        L_0x012f:
            r2 = move-exception
            android.os.ParcelFileDescriptor r4 = r10.mPfd
            if (r4 == 0) goto L_0x0153
            android.os.ParcelFileDescriptor r4 = r10.mPfd     // Catch:{ IOException -> 0x013a }
            r4.close()     // Catch:{ IOException -> 0x013a }
            goto L_0x0151
        L_0x013a:
            r4 = move-exception
            java.lang.String r5 = "BluetoothSocket"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "bindListen, close mPfd: "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            android.util.Log.e(r5, r6)
        L_0x0151:
            r10.mPfd = r1
        L_0x0153:
            java.lang.String r1 = "BluetoothSocket"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "bindListen, fail to get port number, exception: "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r4 = r4.toString()
            android.util.Log.e(r1, r4)
            return r3
        L_0x016a:
            r1 = move-exception
            java.lang.String r2 = "BluetoothSocket"
            java.lang.Throwable r4 = new java.lang.Throwable
            r4.<init>()
            java.lang.String r4 = android.util.Log.getStackTraceString(r4)
            android.util.Log.e(r2, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothSocket.bindListen():int");
    }

    /* access modifiers changed from: package-private */
    public BluetoothSocket accept(int timeout) throws IOException {
        BluetoothSocket acceptedSocket;
        if (this.mSocketState == SocketState.LISTENING) {
            if (timeout > 0) {
                Log.d(TAG, "accept() set timeout (ms):" + timeout);
                this.mSocket.setSoTimeout(timeout);
            }
            String RemoteAddr = waitSocketSignal(this.mSocketIS);
            if (timeout > 0) {
                this.mSocket.setSoTimeout(0);
            }
            synchronized (this) {
                if (this.mSocketState == SocketState.LISTENING) {
                    acceptedSocket = acceptSocket(RemoteAddr);
                } else {
                    throw new IOException("bt socket is not in listen state");
                }
            }
            return acceptedSocket;
        }
        throw new IOException("bt socket is not in listen state");
    }

    /* access modifiers changed from: package-private */
    public int available() throws IOException {
        if (VDBG) {
            Log.d(TAG, "available: " + this.mSocketIS);
        }
        return this.mSocketIS.available();
    }

    /* access modifiers changed from: package-private */
    @UnsupportedAppUsage
    public void flush() throws IOException {
        if (this.mSocketOS != null) {
            if (VDBG) {
                Log.d(TAG, "flush: " + this.mSocketOS);
            }
            this.mSocketOS.flush();
            return;
        }
        throw new IOException("flush is called on null OutputStream");
    }

    /* access modifiers changed from: package-private */
    public int read(byte[] b, int offset, int length) throws IOException {
        int ret;
        if (VDBG) {
            Log.d(TAG, "read in:  " + this.mSocketIS + " len: " + length);
        }
        if (this.mType == 3 || this.mType == 4) {
            int bytesToRead = length;
            if (VDBG) {
                Log.v(TAG, "l2cap: read(): offset: " + offset + " length:" + length + "mL2capBuffer= " + this.mL2capBuffer);
            }
            if (this.mL2capBuffer == null) {
                createL2capRxBuffer();
            }
            if (this.mL2capBuffer.remaining() == 0) {
                if (VDBG) {
                    Log.v(TAG, "l2cap buffer empty, refilling...");
                }
                if (fillL2capRxBuffer() == -1) {
                    return -1;
                }
            }
            if (bytesToRead > this.mL2capBuffer.remaining()) {
                bytesToRead = this.mL2capBuffer.remaining();
            }
            if (VDBG) {
                Log.v(TAG, "get(): offset: " + offset + " bytesToRead: " + bytesToRead);
            }
            this.mL2capBuffer.get(b, offset, bytesToRead);
            ret = bytesToRead;
        } else {
            if (VDBG) {
                Log.v(TAG, "default: read(): offset: " + offset + " length:" + length);
            }
            ret = this.mSocketIS.read(b, offset, length);
        }
        if (ret >= 0) {
            if (VDBG) {
                Log.d(TAG, "read out:  " + this.mSocketIS + " ret: " + ret);
            }
            return ret;
        }
        throw new IOException("bt socket closed, read return: " + ret);
    }

    /* access modifiers changed from: package-private */
    public int write(byte[] b, int offset, int length) throws IOException {
        int tmpLength;
        if (VDBG) {
            Log.d(TAG, "write: " + this.mSocketOS + " length: " + length);
        }
        if (this.mType != 3 && this.mType != 4) {
            this.mSocketOS.write(b, offset, length);
        } else if (length <= this.mMaxTxPacketSize) {
            this.mSocketOS.write(b, offset, length);
        } else {
            if (DBG) {
                Log.w(TAG, "WARNING: Write buffer larger than L2CAP packet size!\nPacket will be divided into SDU packets of size " + this.mMaxTxPacketSize);
            }
            int tmpOffset = offset;
            int bytesToWrite = length;
            while (bytesToWrite > 0) {
                if (bytesToWrite > this.mMaxTxPacketSize) {
                    tmpLength = this.mMaxTxPacketSize;
                } else {
                    tmpLength = bytesToWrite;
                }
                this.mSocketOS.write(b, tmpOffset, tmpLength);
                tmpOffset += tmpLength;
                bytesToWrite -= tmpLength;
            }
        }
        if (VDBG) {
            Log.d(TAG, "write out: " + this.mSocketOS + " length: " + length);
        }
        return length;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x009a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() throws java.io.IOException {
        /*
            r4 = this;
            java.lang.String r0 = "BluetoothSocket"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "close() this: "
            r1.append(r2)
            r1.append(r4)
            java.lang.String r2 = ", channel: "
            r1.append(r2)
            int r2 = r4.mPort
            r1.append(r2)
            java.lang.String r2 = ", mSocketIS: "
            r1.append(r2)
            java.io.InputStream r2 = r4.mSocketIS
            r1.append(r2)
            java.lang.String r2 = ", mSocketOS: "
            r1.append(r2)
            java.io.OutputStream r2 = r4.mSocketOS
            r1.append(r2)
            java.lang.String r2 = "mSocket: "
            r1.append(r2)
            android.net.LocalSocket r2 = r4.mSocket
            r1.append(r2)
            java.lang.String r2 = ", mSocketState: "
            r1.append(r2)
            android.bluetooth.BluetoothSocket$SocketState r2 = r4.mSocketState
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            android.bluetooth.BluetoothSocket$SocketState r0 = r4.mSocketState
            android.bluetooth.BluetoothSocket$SocketState r1 = android.bluetooth.BluetoothSocket.SocketState.CLOSED
            if (r0 != r1) goto L_0x004f
            return
        L_0x004f:
            monitor-enter(r4)
            android.bluetooth.BluetoothSocket$SocketState r0 = r4.mSocketState     // Catch:{ all -> 0x009b }
            android.bluetooth.BluetoothSocket$SocketState r1 = android.bluetooth.BluetoothSocket.SocketState.CLOSED     // Catch:{ all -> 0x009b }
            if (r0 != r1) goto L_0x0058
            monitor-exit(r4)     // Catch:{ all -> 0x009b }
            return
        L_0x0058:
            android.bluetooth.BluetoothSocket$SocketState r0 = android.bluetooth.BluetoothSocket.SocketState.CLOSED     // Catch:{ all -> 0x009b }
            r4.mSocketState = r0     // Catch:{ all -> 0x009b }
            android.net.LocalSocket r0 = r4.mSocket     // Catch:{ all -> 0x009b }
            r1 = 0
            if (r0 == 0) goto L_0x008e
            boolean r0 = DBG     // Catch:{ all -> 0x009b }
            if (r0 == 0) goto L_0x007d
            java.lang.String r0 = "BluetoothSocket"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x009b }
            r2.<init>()     // Catch:{ all -> 0x009b }
            java.lang.String r3 = "Closing mSocket: "
            r2.append(r3)     // Catch:{ all -> 0x009b }
            android.net.LocalSocket r3 = r4.mSocket     // Catch:{ all -> 0x009b }
            r2.append(r3)     // Catch:{ all -> 0x009b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x009b }
            android.util.Log.d(r0, r2)     // Catch:{ all -> 0x009b }
        L_0x007d:
            android.net.LocalSocket r0 = r4.mSocket     // Catch:{ all -> 0x009b }
            r0.shutdownInput()     // Catch:{ all -> 0x009b }
            android.net.LocalSocket r0 = r4.mSocket     // Catch:{ all -> 0x009b }
            r0.shutdownOutput()     // Catch:{ all -> 0x009b }
            android.net.LocalSocket r0 = r4.mSocket     // Catch:{ all -> 0x009b }
            r0.close()     // Catch:{ all -> 0x009b }
            r4.mSocket = r1     // Catch:{ all -> 0x009b }
        L_0x008e:
            android.os.ParcelFileDescriptor r0 = r4.mPfd     // Catch:{ all -> 0x009b }
            if (r0 == 0) goto L_0x0099
            android.os.ParcelFileDescriptor r0 = r4.mPfd     // Catch:{ all -> 0x009b }
            r0.close()     // Catch:{ all -> 0x009b }
            r4.mPfd = r1     // Catch:{ all -> 0x009b }
        L_0x0099:
            monitor-exit(r4)     // Catch:{ all -> 0x009b }
            return
        L_0x009b:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x009b }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.bluetooth.BluetoothSocket.close():void");
    }

    /* access modifiers changed from: package-private */
    public void removeChannel() {
    }

    /* access modifiers changed from: package-private */
    public int getPort() {
        return this.mPort;
    }

    public int getMaxTransmitPacketSize() {
        return this.mMaxTxPacketSize;
    }

    public int getMaxReceivePacketSize() {
        return this.mMaxRxPacketSize;
    }

    public int getConnectionType() {
        if (this.mType == 4) {
            return 3;
        }
        return this.mType;
    }

    public void setExcludeSdp(boolean excludeSdp) {
        this.mExcludeSdp = excludeSdp;
    }

    public void requestMaximumTxDataLength() throws IOException {
        if (this.mDevice != null) {
            try {
                if (this.mSocketState != SocketState.CLOSED) {
                    IBluetooth bluetoothProxy = BluetoothAdapter.getDefaultAdapter().getBluetoothService((IBluetoothManagerCallback) null);
                    if (bluetoothProxy != null) {
                        if (DBG) {
                            Log.d(TAG, "requestMaximumTxDataLength");
                        }
                        bluetoothProxy.getSocketManager().requestMaximumTxDataLength(this.mDevice);
                        return;
                    }
                    throw new IOException("Bluetooth is off");
                }
                throw new IOException("socket closed");
            } catch (RemoteException e) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                throw new IOException("unable to send RPC: " + e.getMessage());
            }
        } else {
            throw new IOException("requestMaximumTxDataLength is called on null device");
        }
    }

    private String convertAddr(byte[] addr) {
        return String.format(Locale.US, "%02X:%02X:%02X:%02X:%02X:%02X", new Object[]{Byte.valueOf(addr[0]), Byte.valueOf(addr[1]), Byte.valueOf(addr[2]), Byte.valueOf(addr[3]), Byte.valueOf(addr[4]), Byte.valueOf(addr[5])});
    }

    private String waitSocketSignal(InputStream is) throws IOException {
        byte[] sig = new byte[20];
        int ret = readAll(is, sig);
        if (VDBG) {
            Log.d(TAG, "waitSocketSignal read 20 bytes signal ret: " + ret);
        }
        ByteBuffer bb = ByteBuffer.wrap(sig);
        bb.order(ByteOrder.nativeOrder());
        int size = bb.getShort();
        if (size == 20) {
            byte[] addr = new byte[6];
            bb.get(addr);
            int channel = bb.getInt();
            int status = bb.getInt();
            this.mMaxTxPacketSize = bb.getShort() & HostEndPoint.BROADCAST;
            this.mMaxRxPacketSize = bb.getShort() & HostEndPoint.BROADCAST;
            String RemoteAddr = convertAddr(addr);
            if (VDBG) {
                Log.d(TAG, "waitSocketSignal: sig size: " + size + ", remote addr: " + RemoteAddr + ", channel: " + channel + ", status: " + status + " MaxRxPktSize: " + this.mMaxRxPacketSize + " MaxTxPktSize: " + this.mMaxTxPacketSize);
            }
            if (status == 0) {
                return RemoteAddr;
            }
            throw new IOException("Connection failure, status: " + status);
        }
        throw new IOException("Connection failure, wrong signal size: " + size);
    }

    private void createL2capRxBuffer() {
        if (this.mType == 3 || this.mType == 4) {
            if (VDBG) {
                Log.v(TAG, "  Creating mL2capBuffer: mMaxPacketSize: " + this.mMaxRxPacketSize);
            }
            this.mL2capBuffer = ByteBuffer.wrap(new byte[this.mMaxRxPacketSize]);
            if (VDBG) {
                Log.v(TAG, "mL2capBuffer.remaining()" + this.mL2capBuffer.remaining());
            }
            this.mL2capBuffer.limit(0);
            if (VDBG) {
                Log.v(TAG, "mL2capBuffer.remaining() after limit(0):" + this.mL2capBuffer.remaining());
            }
        }
    }

    private int readAll(InputStream is, byte[] b) throws IOException {
        int left = b.length;
        while (left > 0) {
            int ret = is.read(b, b.length - left, left);
            if (ret > 0) {
                left -= ret;
                if (left != 0) {
                    Log.w(TAG, "readAll() looping, read partial size: " + (b.length - left) + ", expect size: " + b.length);
                }
            } else {
                throw new IOException("read failed, socket might closed or timeout, read ret: " + ret);
            }
        }
        return b.length;
    }

    private int readInt(InputStream is) throws IOException {
        byte[] ibytes = new byte[4];
        int ret = readAll(is, ibytes);
        if (VDBG) {
            Log.d(TAG, "inputStream.read ret: " + ret);
        }
        ByteBuffer bb = ByteBuffer.wrap(ibytes);
        bb.order(ByteOrder.nativeOrder());
        return bb.getInt();
    }

    private int fillL2capRxBuffer() throws IOException {
        this.mL2capBuffer.rewind();
        int ret = this.mSocketIS.read(this.mL2capBuffer.array());
        if (ret == -1) {
            this.mL2capBuffer.limit(0);
            return -1;
        }
        this.mL2capBuffer.limit(ret);
        return ret;
    }

    public int setSocketOpt(int optionName, byte[] optionVal, int optionLen) throws IOException {
        if (this.mSocketState != SocketState.CLOSED) {
            IBluetooth bluetoothProxy = BluetoothAdapter.getDefaultAdapter().getBluetoothService((IBluetoothManagerCallback) null);
            if (bluetoothProxy == null) {
                Log.e(TAG, "setSocketOpt fail, reason: bluetooth is off");
                return -1;
            }
            try {
                if (VDBG) {
                    Log.d(TAG, "setSocketOpt(), mType: " + this.mType + " mPort: " + this.mPort);
                }
                return bluetoothProxy.setSocketOpt(this.mType, this.mPort, optionName, optionVal, optionLen);
            } catch (RemoteException e) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return -1;
            }
        } else {
            throw new IOException("socket closed");
        }
    }

    public int getSocketOpt(int optionName, byte[] optionVal) throws IOException {
        if (this.mSocketState != SocketState.CLOSED) {
            IBluetooth bluetoothProxy = BluetoothAdapter.getDefaultAdapter().getBluetoothService((IBluetoothManagerCallback) null);
            if (bluetoothProxy == null) {
                Log.e(TAG, "getSocketOpt fail, reason: bluetooth is off");
                return -1;
            }
            try {
                if (VDBG) {
                    Log.d(TAG, "getSocketOpt(), mType: " + this.mType + " mPort: " + this.mPort);
                }
                return bluetoothProxy.getSocketOpt(this.mType, this.mPort, optionName, optionVal);
            } catch (RemoteException e) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return -1;
            }
        } else {
            throw new IOException("socket closed");
        }
    }
}
