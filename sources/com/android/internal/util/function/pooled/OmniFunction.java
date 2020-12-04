package com.android.internal.util.function.pooled;

import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HeptFunction;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.NonaConsumer;
import com.android.internal.util.function.NonaFunction;
import com.android.internal.util.function.OctConsumer;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.QuintFunction;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.TriFunction;
import com.android.internal.util.function.pooled.PooledSupplier;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

abstract class OmniFunction<A, B, C, D, E, F, G, H, I, R> implements PooledFunction<A, R>, BiFunction<A, B, R>, TriFunction<A, B, C, R>, QuadFunction<A, B, C, D, R>, QuintFunction<A, B, C, D, E, R>, HexFunction<A, B, C, D, E, F, R>, HeptFunction<A, B, C, D, E, F, G, R>, OctFunction<A, B, C, D, E, F, G, H, R>, NonaFunction<A, B, C, D, E, F, G, H, I, R>, PooledConsumer<A>, BiConsumer<A, B>, TriConsumer<A, B, C>, QuadConsumer<A, B, C, D>, QuintConsumer<A, B, C, D, E>, HexConsumer<A, B, C, D, E, F>, HeptConsumer<A, B, C, D, E, F, G>, OctConsumer<A, B, C, D, E, F, G, H>, NonaConsumer<A, B, C, D, E, F, G, H, I>, PooledPredicate<A>, BiPredicate<A, B>, PooledSupplier<R>, PooledRunnable, FunctionalUtils.ThrowingRunnable, FunctionalUtils.ThrowingSupplier<R>, PooledSupplier.OfInt, PooledSupplier.OfLong, PooledSupplier.OfDouble {
    public abstract <V> OmniFunction<A, B, C, D, E, F, G, H, I, V> andThen(Function<? super R, ? extends V> function);

    /* access modifiers changed from: package-private */
    public abstract R invoke(A a, B b, C c, D d, E e, F f, G g, H h, I i);

    public abstract OmniFunction<A, B, C, D, E, F, G, H, I, R> negate();

    public abstract OmniFunction<A, B, C, D, E, F, G, H, I, R> recycleOnUse();

    OmniFunction() {
    }

    public R apply(A o, B o2) {
        return invoke(o, o2, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public R apply(A o) {
        return invoke(o, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public void accept(A o, B o2) {
        invoke(o, o2, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public void accept(A o) {
        invoke(o, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public void run() {
        invoke((Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public R get() {
        return invoke((Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public boolean test(A o, B o2) {
        return ((Boolean) invoke(o, o2, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null)).booleanValue();
    }

    public boolean test(A o) {
        return ((Boolean) invoke(o, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null)).booleanValue();
    }

    public PooledRunnable asRunnable() {
        return this;
    }

    public PooledConsumer<A> asConsumer() {
        return this;
    }

    public R apply(A a, B b, C c) {
        return invoke(a, b, c, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public void accept(A a, B b, C c) {
        invoke(a, b, c, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public R apply(A a, B b, C c, D d) {
        return invoke(a, b, c, d, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public R apply(A a, B b, C c, D d, E e) {
        return invoke(a, b, c, d, e, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public R apply(A a, B b, C c, D d, E e, F f) {
        return invoke(a, b, c, d, e, f, (Object) null, (Object) null, (Object) null);
    }

    public R apply(A a, B b, C c, D d, E e, F f, G g) {
        return invoke(a, b, c, d, e, f, g, (Object) null, (Object) null);
    }

    public R apply(A a, B b, C c, D d, E e, F f, G g, H h) {
        return invoke(a, b, c, d, e, f, g, h, (Object) null);
    }

    public R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        return invoke(a, b, c, d, e, f, g, h, i);
    }

    public void accept(A a, B b, C c, D d) {
        invoke(a, b, c, d, (Object) null, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public void accept(A a, B b, C c, D d, E e) {
        invoke(a, b, c, d, e, (Object) null, (Object) null, (Object) null, (Object) null);
    }

    public void accept(A a, B b, C c, D d, E e, F f) {
        invoke(a, b, c, d, e, f, (Object) null, (Object) null, (Object) null);
    }

    public void accept(A a, B b, C c, D d, E e, F f, G g) {
        invoke(a, b, c, d, e, f, g, (Object) null, (Object) null);
    }

    public void accept(A a, B b, C c, D d, E e, F f, G g, H h) {
        invoke(a, b, c, d, e, f, g, h, (Object) null);
    }

    public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        invoke(a, b, c, d, e, f, g, h, i);
    }

    public void runOrThrow() throws Exception {
        run();
    }

    public R getOrThrow() throws Exception {
        return get();
    }
}
