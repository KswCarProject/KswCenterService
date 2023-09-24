package com.android.internal.util.function.pooled;

import android.p007os.Message;
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

/* loaded from: classes4.dex */
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
    long mConstValue;
    Object mFunc;
    static final Pool sPool = new Pool(new Object());
    static final Pool sMessageCallbacksPool = new Pool(Message.sPoolSync);
    Object[] mArgs = null;
    int mFlags = 0;

    /* loaded from: classes4.dex */
    static class Pool extends Pools.SynchronizedPool<PooledLambdaImpl> {
        public Pool(Object lock) {
            super(50, lock);
        }
    }

    private PooledLambdaImpl() {
    }

    @Override // com.android.internal.util.function.pooled.PooledLambda
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
        this.mConstValue = 0L;
        pool.release(this);
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction
    R invoke(Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7, Object a8, Object a9) {
        checkNotRecycled();
        int i = 0;
        if (!fillInArg(a1) || !fillInArg(a2) || !fillInArg(a3) || !fillInArg(a4) || !fillInArg(a5) || !fillInArg(a6) || !fillInArg(a7) || !fillInArg(a8) || !fillInArg(a9)) {
        }
        int argCount = LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE));
        if (argCount != 15) {
            for (int i2 = 0; i2 < argCount; i2++) {
                if (this.mArgs[i2] == ArgumentPlaceholder.INSTANCE) {
                    throw new IllegalStateException("Missing argument #" + i2 + " among " + Arrays.toString(this.mArgs));
                }
            }
        }
        try {
            return doInvoke();
        } finally {
            if (isRecycleOnUse()) {
                doRecycle();
            }
            if (!isRecycled()) {
                int argsSize = ArrayUtils.size(this.mArgs);
                while (i < argsSize) {
                    popArg(i);
                    i++;
                }
            }
        }
    }

    private boolean fillInArg(Object invocationArg) {
        int argsSize = ArrayUtils.size(this.mArgs);
        for (int i = 0; i < argsSize; i++) {
            if (this.mArgs[i] == ArgumentPlaceholder.INSTANCE) {
                this.mArgs[i] = invocationArg;
                this.mFlags = (int) (this.mFlags | BitUtils.bitAt(i));
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
        if (argCount == 15) {
            switch (returnType) {
                case 4:
                    return (R) Integer.valueOf(getAsInt());
                case 5:
                    return (R) Long.valueOf(getAsLong());
                case 6:
                    return (R) Double.valueOf(getAsDouble());
                default:
                    return (R) this.mFunc;
            }
        }
        switch (argCount) {
            case 0:
                switch (returnType) {
                    case 1:
                        ((Runnable) this.mFunc).run();
                        return null;
                    case 2:
                    case 3:
                        return (R) ((Supplier) this.mFunc).get();
                }
            case 1:
                switch (returnType) {
                    case 1:
                        ((Consumer) this.mFunc).accept(popArg(0));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((Predicate) this.mFunc).test(popArg(0)));
                    case 3:
                        return (R) ((Function) this.mFunc).apply(popArg(0));
                }
            case 2:
                switch (returnType) {
                    case 1:
                        ((BiConsumer) this.mFunc).accept(popArg(0), popArg(1));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((BiPredicate) this.mFunc).test(popArg(0), popArg(1)));
                    case 3:
                        return (R) ((BiFunction) this.mFunc).apply(popArg(0), popArg(1));
                }
            case 3:
                switch (returnType) {
                    case 1:
                        ((TriConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((TriPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2)));
                    case 3:
                        return (R) ((TriFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2));
                }
            case 4:
                switch (returnType) {
                    case 1:
                        ((QuadConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((QuadPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3)));
                    case 3:
                        return (R) ((QuadFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3));
                }
            case 5:
                switch (returnType) {
                    case 1:
                        ((QuintConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((QuintPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4)));
                    case 3:
                        return (R) ((QuintFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4));
                }
            case 6:
                switch (returnType) {
                    case 1:
                        ((HexConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((HexPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5)));
                    case 3:
                        return (R) ((HexFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5));
                }
            case 7:
                switch (returnType) {
                    case 1:
                        ((HeptConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((HeptPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6)));
                    case 3:
                        return (R) ((HeptFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6));
                }
            case 8:
                switch (returnType) {
                    case 1:
                        ((OctConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((OctPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7)));
                    case 3:
                        return (R) ((OctFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7));
                }
            case 9:
                switch (returnType) {
                    case 1:
                        ((NonaConsumer) this.mFunc).accept(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                        return null;
                    case 2:
                        return (R) Boolean.valueOf(((NonaPredicate) this.mFunc).test(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8)));
                    case 3:
                        return (R) ((NonaFunction) this.mFunc).apply(popArg(0), popArg(1), popArg(2), popArg(3), popArg(4), popArg(5), popArg(6), popArg(7), popArg(8));
                }
        }
        throw new IllegalStateException("Unknown function type: " + LambdaType.toString(funcType));
    }

    private boolean isConstSupplier() {
        return LambdaType.decodeArgCount(getFlags(MASK_FUNC_TYPE)) == 15;
    }

    private Object popArg(int index) {
        Object result = this.mArgs[index];
        if (isInvocationArgAtIndex(index)) {
            this.mArgs[index] = ArgumentPlaceholder.INSTANCE;
            this.mFlags = (int) (this.mFlags & (~BitUtils.bitAt(index)));
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
        return arr == null ? "" : TextUtils.join(SmsManager.REGEX_PREFIX_DELIMITER, Arrays.copyOf(arr, n));
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
        return name.endsWith("Consumer") ? "consumer" : name.endsWith("Function") ? "function" : name.endsWith("Predicate") ? "predicate" : name.endsWith("Supplier") ? "supplier" : name.endsWith("Runnable") ? "runnable" : name;
    }

    static <E extends PooledLambda> E acquire(Pool pool, Object func, int fNumArgs, int numPlaceholders, int fReturnType, Object a, Object b, Object c, Object d, Object e, Object f, Object g, Object h, Object i) {
        PooledLambdaImpl r = acquire(pool);
        r.mFunc = Preconditions.checkNotNull(func);
        r.setFlags(MASK_FUNC_TYPE, LambdaType.encode(fNumArgs, fReturnType));
        r.setFlags(MASK_EXPOSED_AS, LambdaType.encode(numPlaceholders, fReturnType));
        if (ArrayUtils.size(r.mArgs) < fNumArgs) {
            r.mArgs = new Object[fNumArgs];
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
        PooledLambdaImpl r = pool.acquire();
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

    @Override // com.android.internal.util.function.pooled.OmniFunction, java.util.function.Predicate, java.util.function.BiPredicate
    public OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, R> negate() {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, java.util.function.Function, java.util.function.BiFunction
    public <V> OmniFunction<Object, Object, Object, Object, Object, Object, Object, Object, Object, V> andThen(Function<? super R, ? extends V> after) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.function.DoubleSupplier
    public double getAsDouble() {
        return Double.longBitsToDouble(this.mConstValue);
    }

    @Override // java.util.function.IntSupplier
    public int getAsInt() {
        return (int) this.mConstValue;
    }

    @Override // java.util.function.LongSupplier
    public long getAsLong() {
        return this.mConstValue;
    }

    @Override // com.android.internal.util.function.pooled.OmniFunction, com.android.internal.util.function.pooled.PooledFunction, com.android.internal.util.function.pooled.PooledLambda, com.android.internal.util.function.pooled.PooledConsumer, com.android.internal.util.function.pooled.PooledPredicate, com.android.internal.util.function.pooled.PooledSupplier, com.android.internal.util.function.pooled.PooledRunnable, com.android.internal.util.function.pooled.PooledSupplier.OfInt, com.android.internal.util.function.pooled.PooledSupplier.OfLong, com.android.internal.util.function.pooled.PooledSupplier.OfDouble
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

    int getFlags(int mask) {
        return unmask(mask, this.mFlags);
    }

    void setFlags(int mask, int value) {
        this.mFlags &= ~mask;
        this.mFlags |= mask(mask, value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int mask(int mask, int value) {
        return (value << Integer.numberOfTrailingZeros(mask)) & mask;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int unmask(int mask, int bits) {
        return (bits & mask) / (1 << Integer.numberOfTrailingZeros(mask));
    }

    /* loaded from: classes4.dex */
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

        /* loaded from: classes4.dex */
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
