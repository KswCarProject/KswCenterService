package com.android.internal.util.function.pooled;

import android.os.Message;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Pools;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HeptFunction;
import com.android.internal.util.function.HeptPredicate;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.HexPredicate;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.NonaFunction;
import com.android.internal.util.function.NonaPredicate;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.OctPredicate;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.QuadPredicate;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintFunction;
import com.android.internal.util.function.QuintPredicate;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.function.TriPredicate;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

final class PooledLambdaImpl<R> extends OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> {
    private static final boolean DEBUG = false;
    private static final int FLAG_ACQUIRED_FROM_MESSAGE_CALLBACKS_POOL = 2048;
    private static final int FLAG_RECYCLED = 512;
    private static final int FLAG_RECYCLE_ON_USE = 1024;
    private static final String LOG_TAG = "PooledLambdaImpl";
    static final int MASK_EXPOSED_AS = 520192;
    static final int MASK_FUNC_TYPE = 66584576;
    private static final int MAX_ARGS = 9;
    private static final int MAX_POOL_SIZE = 50;
    static final Pool sMessageCallbacksPool = new Pool(Message.sPoolSync);
    static final Pool sPool = new Pool(new Object());
    Object[] mArgs = null;
    long mConstValue;
    int mFlags = 0;
    Object mFunc;

    static class Pool extends Pools.SynchronizedPool<PooledLambdaImpl> {
        public Pool(Object lock) {
            super(50, lock);
        }
    }

    private PooledLambdaImpl() {
    }

    public void recycle() {
        if (!isRecycled()) {
            doRecycle();
        }
    }

    private void doRecycle() {
        Pool pool;
        if ((this.mFlags & 2048) != 0) {
            pool = sMessageCallbacksPool;
        } else {
            pool = sPool;
        }
        this.mFunc = null;
        if (this.mArgs != null) {
            Arrays.fill(this.mArgs, (Object) null);
        }
        this.mFlags = 512;
        this.mConstValue = 0;
        pool.release(this);
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    R invoke(java.lang.Object r7, java.lang.Object r8, java.lang.Object r9, java.lang.Object r10, java.lang.Object r11, java.lang.Object r12, java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
            r6 = this;
            r6.checkNotRecycled()
            boolean r0 = r6.fillInArg(r7)
            r1 = 0
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r8)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r9)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r10)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r11)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r12)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r13)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r14)
            if (r0 == 0) goto L_0x003c
            boolean r0 = r6.fillInArg(r15)
            if (r0 == 0) goto L_0x003c
            r0 = 1
            goto L_0x003d
        L_0x003c:
            r0 = r1
        L_0x003d:
            r2 = 66584576(0x3f80000, float:1.457613E-36)
            int r2 = r6.getFlags(r2)
            int r2 = com.android.internal.util.function.pooled.PooledLambdaImpl.LambdaType.decodeArgCount(r2)
            r3 = 15
            if (r2 == r3) goto L_0x007e
            r3 = r1
        L_0x004c:
            if (r3 >= r2) goto L_0x007e
            java.lang.Object[] r4 = r6.mArgs
            r4 = r4[r3]
            com.android.internal.util.function.pooled.ArgumentPlaceholder<?> r5 = com.android.internal.util.function.pooled.ArgumentPlaceholder.INSTANCE
            if (r4 == r5) goto L_0x0059
            int r3 = r3 + 1
            goto L_0x004c
        L_0x0059:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Missing argument #"
            r4.append(r5)
            r4.append(r3)
            java.lang.String r5 = " among "
            r4.append(r5)
            java.lang.Object[] r5 = r6.mArgs
            java.lang.String r5 = java.util.Arrays.toString(r5)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r1.<init>(r4)
            throw r1
        L_0x007e:
            java.lang.Object r3 = r6.doInvoke()     // Catch:{ all -> 0x00a1 }
            boolean r4 = r6.isRecycleOnUse()
            if (r4 == 0) goto L_0x008b
            r6.doRecycle()
        L_0x008b:
            boolean r4 = r6.isRecycled()
            if (r4 != 0) goto L_0x00a0
            java.lang.Object[] r4 = r6.mArgs
            int r4 = com.android.internal.util.ArrayUtils.size((java.lang.Object[]) r4)
        L_0x0098:
            if (r1 >= r4) goto L_0x00a0
            r6.popArg(r1)
            int r1 = r1 + 1
            goto L_0x0098
        L_0x00a0:
            return r3
        L_0x00a1:
            r3 = move-exception
            boolean r4 = r6.isRecycleOnUse()
            if (r4 == 0) goto L_0x00ab
            r6.doRecycle()
        L_0x00ab:
            boolean r4 = r6.isRecycled()
            if (r4 != 0) goto L_0x00c0
            java.lang.Object[] r4 = r6.mArgs
            int r4 = com.android.internal.util.ArrayUtils.size((java.lang.Object[]) r4)
        L_0x00b8:
            if (r1 >= r4) goto L_0x00c0
            r6.popArg(r1)
            int r1 = r1 + 1
            goto L_0x00b8
        L_0x00c0:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.util.function.pooled.PooledLambdaImpl.invoke(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    private boolean fillInArg(Object invocationArg) {
        int argsSize = ArrayUtils.size(this.mArgs);
        for (int i = 0; i < argsSize; i++) {
            if (this.mArgs[i] == ArgumentPlaceholder.INSTANCE) {
                this.mArgs[i] = invocationArg;
                this.mFlags = (int) (((long) this.mFlags) | BitUtils.bitAt(i));
                return true;
            }
        }
        if (invocationArg == null || invocationArg == ArgumentPlaceholder.INSTANCE) {
            return false;
        }
        throw new IllegalStateException("No more arguments expected for provided arg " + invocationArg + " among " + Arrays.toString(this.mArgs));
    }

    private void checkNotRecycled() {
        if (isRecycled()) {
            throw new IllegalStateException("Instance is recycled: " + this);
        }
    }

    private R doInvoke() {
        int funcType = getFlags(MASK_FUNC_TYPE);
        int argCount = LambdaType.decodeArgCount(funcType);
        int returnType = LambdaType.decodeReturnType(funcType);
        if (argCount != 15) {
            switch (argCount) {
                case 0:
                    switch (returnType) {
                        case 1:
                            ((Runnable) this.mFunc).run();
                            return null;
                        case 2:
                        case 3:
                            return ((Supplier) this.mFunc).get();
                    }
                case 1:
                    switch (returnType) {
                        case 1:
                            ((Consumer) this.mFunc).accept(popArg(0));
                            return null;
                        case 2:
                            return Boolean.valueOf(((Predicate) this.mFunc).test(popArg(0)));
                        case 3:
                            return ((Function) this.mFunc).apply(popArg(0));
                    }
                case 2:
                    switch (returnType) {
                        case 1:
                            ((BiConsumer) this.mFunc).accept(popArg(0), popArg(1));
                            return null;
                        case 2:
                            return Boolean.valueOf(((BiPredicate) this.mFunc).test(popArg(0), popArg(1)));
                        case 3:
                            return ((BiFunction) this.mFunc).apply(popArg(0), popArg(1));
                    }
                case 3:
                    switch (returnType) {
                        case 1:
                            ((TriConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2));
                            return null;
                        case 2:
                            return Boolean.valueOf(((TriPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2)));
                        case 3:
                            return ((TriFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2));
                    }
                case 4:
                    switch (returnType) {
                        case 1:
                            ((QuadConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3));
                            return null;
                        case 2:
                            return Boolean.valueOf(((QuadPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3)));
                        case 3:
                            return ((QuadFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3));
                    }
                case 5:
                    switch (returnType) {
                        case 1:
                            ((QuintConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                            return null;
                        case 2:
                            return Boolean.valueOf(((QuintPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4)));
                        case 3:
                            return ((QuintFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                    }
                case 6:
                    switch (returnType) {
                        case 1:
                            ((HexConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                            return null;
                        case 2:
                            return Boolean.valueOf(((HexPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5)));
                        case 3:
                            return ((HexFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                    }
                case 7:
                    switch (returnType) {
                        case 1:
                            ((HeptConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                            return null;
                        case 2:
                            return Boolean.valueOf(((HeptPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6)));
                        case 3:
                            return ((HeptFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                    }
                case 8:
                    switch (returnType) {
                        case 1:
                            ((OctConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                            return null;
                        case 2:
                            return Boolean.valueOf(((OctPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7)));
                        case 3:
                            return ((OctFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                    }
                case 9:
                    switch (returnType) {
                        case 1:
                            ((NonaConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                            return null;
                        case 2:
                            return Boolean.valueOf(((NonaPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8)));
                        case 3:
                            return ((NonaFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                    }
            }
            throw new IllegalStateException("Unknown function type: " + LambdaType.toString(funcType));
        }
        switch (returnType) {
            case 4:
                return Integer.valueOf(getAsInt());
            case 5:
                return Long.valueOf(getAsLong());
            case 6:
                return Double.valueOf(getAsDouble());
            default:
                return this.mFunc;
        }
    }

    private boolean isConstSupplier() {
        return LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE)) == 15;
    }

    private Object popArg(int index) {
        Object result = this.mArgs[index];
        if (isInvocationArgAtIndex(index)) {
            this.mArgs[index] = ArgumentPlaceholder.INSTANCE;
            this.mFlags = (int) (((long) this.mFlags) & (~BitUtils.bitAt(index)));
        }
        return result;
    }

    public String toString() {
        if (isRecycled()) {
            return "<recycled PooledLambda@" + hashCodeHex(this) + ">";
        }
        StringBuilder sb = new StringBuilder();
        if (isConstSupplier()) {
            sb.append(getFuncTypeAsString());
            sb.append("(");
            sb.append(doInvoke());
            sb.append(")");
        } else {
            Object func = this.mFunc;
            if (func instanceof PooledLambdaImpl) {
                sb.append(func);
            } else {
                sb.append(getFuncTypeAsString());
                sb.append("@");
                sb.append(hashCodeHex(func));
            }
            sb.append("(");
            sb.append(commaSeparateFirstN(this.mArgs, LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE))));
            sb.append(")");
        }
        return sb.toString();
    }

    private String commaSeparateFirstN(Object[] arr, int n) {
        if (arr == null) {
            return "";
        }
        return TextUtils.join((CharSequence) SmsManager.REGEX_PREFIX_DELIMITER, Arrays.copyOf(arr, n));
    }

    private static String hashCodeHex(Object o) {
        return Integer.toHexString(Objects.hashCode(o));
    }

    private String getFuncTypeAsString() {
        if (isRecycled()) {
            return "<recycled>";
        }
        if (isConstSupplier()) {
            return "supplier";
        }
        String name = LambdaType.toString(getFlags(MASK_EXPOSED_AS));
        if (name.endsWith("Consumer")) {
            return "consumer";
        }
        if (name.endsWith("Function")) {
            return "function";
        }
        if (name.endsWith("Predicate")) {
            return "predicate";
        }
        if (name.endsWith("Supplier")) {
            return "supplier";
        }
        if (name.endsWith("Runnable")) {
            return "runnable";
        }
        return name;
    }

    static <E extends PooledLambda> E acquire(Pool pool, Object func, int fNumArgs, int numPlaceholders, int fReturnType, Object a, Object b, Object c, Object d, Object e, Object f, Object g, Object h, Object i) {
        int i2 = fNumArgs;
        PooledLambdaImpl r = acquire(pool);
        r.mFunc = Preconditions.checkNotNull(func);
        r.setFlags(MASK_FUNC_TYPE, LambdaType.encode(i2, fReturnType));
        r.setFlags(MASK_EXPOSED_AS, LambdaType.encode(numPlaceholders, fReturnType));
        if (ArrayUtils.size(r.mArgs) < i2) {
            r.mArgs = new Object[i2];
        }
        setIfInBounds(r.mArgs, 0, a);
        setIfInBounds(r.mArgs, 1, b);
        setIfInBounds(r.mArgs, 2, c);
        setIfInBounds(r.mArgs, 3, d);
        setIfInBounds(r.mArgs, 4, e);
        setIfInBounds(r.mArgs, 5, f);
        setIfInBounds(r.mArgs, 6, g);
        setIfInBounds(r.mArgs, 7, h);
        setIfInBounds(r.mArgs, 8, i);
        return r;
    }

    static PooledLambdaImpl acquireConstSupplier(int type) {
        PooledLambdaImpl r = acquire(sPool);
        int lambdaType = LambdaType.encode(15, type);
        r.setFlags(MASK_FUNC_TYPE, lambdaType);
        r.setFlags(MASK_EXPOSED_AS, lambdaType);
        return r;
    }

    static PooledLambdaImpl acquire(Pool pool) {
        PooledLambdaImpl r = (PooledLambdaImpl) pool.acquire();
        if (r == null) {
            r = new PooledLambdaImpl();
        }
        r.mFlags &= -513;
        r.setFlags(2048, pool == sMessageCallbacksPool ? 1 : 0);
        return r;
    }

    private static void setIfInBounds(Object[] array, int i, Object a) {
        if (i < ArrayUtils.size(array)) {
            array[i] = a;
        }
    }

    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> negate() {
        throw new UnsupportedOperationException();
    }

    public <V> OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, V> andThen(Function<? super R, ? extends V> function) {
        throw new UnsupportedOperationException();
    }

    public double getAsDouble() {
        return Double.longBitsToDouble(this.mConstValue);
    }

    public int getAsInt() {
        return (int) this.mConstValue;
    }

    public long getAsLong() {
        return this.mConstValue;
    }

    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> recycleOnUse() {
        this.mFlags |= 1024;
        return this;
    }

    private boolean isRecycled() {
        return (this.mFlags & 512) != 0;
    }

    private boolean isRecycleOnUse() {
        return (this.mFlags & 1024) != 0;
    }

    private boolean isInvocationArgAtIndex(int argIndex) {
        return (this.mFlags & (1 << argIndex)) != 0;
    }

    /* access modifiers changed from: package-private */
    public int getFlags(int mask) {
        return unmask(mask, this.mFlags);
    }

    /* access modifiers changed from: package-private */
    public void setFlags(int mask, int value) {
        this.mFlags &= ~mask;
        this.mFlags |= mask(mask, value);
    }

    /* access modifiers changed from: private */
    public static int mask(int mask, int value) {
        return (value << Integer.numberOfTrailingZeros(mask)) & mask;
    }

    /* access modifiers changed from: private */
    public static int unmask(int mask, int bits) {
        return (bits & mask) / (1 << Integer.numberOfTrailingZeros(mask));
    }

    static class LambdaType {
        public static final int MASK = 127;
        public static final int MASK_ARG_COUNT = 15;
        public static final int MASK_BIT_COUNT = 7;
        public static final int MASK_RETURN_TYPE = 112;

        LambdaType() {
        }

        static int encode(int argCount, int returnType) {
            return PooledLambdaImpl.mask(15, argCount) | PooledLambdaImpl.mask(112, returnType);
        }

        static int decodeArgCount(int type) {
            return type & 15;
        }

        static int decodeReturnType(int type) {
            return PooledLambdaImpl.unmask(112, type);
        }

        static String toString(int type) {
            int argCount = decodeArgCount(type);
            int returnType = decodeReturnType(type);
            if (argCount == 0) {
                if (returnType == 1) {
                    return "Runnable";
                }
                if (returnType == 3 || returnType == 2) {
                    return "Supplier";
                }
            }
            return argCountPrefix(argCount) + ReturnType.lambdaSuffix(returnType);
        }

        private static String argCountPrefix(int argCount) {
            if (argCount == 15) {
                return "";
            }
            switch (argCount) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "Bi";
                case 3:
                    return "Tri";
                case 4:
                    return "Quad";
                case 5:
                    return "Quint";
                case 6:
                    return "Hex";
                case 7:
                    return "Hept";
                case 8:
                    return "Oct";
                case 9:
                    return "Nona";
                default:
                    return "" + argCount + "arg";
            }
        }

        static class ReturnType {
            public static final int BOOLEAN = 2;
            public static final int DOUBLE = 6;
            public static final int INT = 4;
            public static final int LONG = 5;
            public static final int OBJECT = 3;
            public static final int VOID = 1;

            ReturnType() {
            }

            static String toString(int returnType) {
                switch (returnType) {
                    case 1:
                        return "VOID";
                    case 2:
                        return "BOOLEAN";
                    case 3:
                        return "OBJECT";
                    case 4:
                        return "INT";
                    case 5:
                        return "LONG";
                    case 6:
                        return "DOUBLE";
                    default:
                        return "" + returnType;
                }
            }

            static String lambdaSuffix(int type) {
                return prefix(type) + suffix(type);
            }

            private static String prefix(int type) {
                switch (type) {
                    case 4:
                        return "Int";
                    case 5:
                        return "Long";
                    case 6:
                        return "Double";
                    default:
                        return "";
                }
            }

            private static String suffix(int type) {
                switch (type) {
                    case 1:
                        return "Consumer";
                    case 2:
                        return "Predicate";
                    case 3:
                        return "Function";
                    default:
                        return "Supplier";
                }
            }
        }
    }
}
