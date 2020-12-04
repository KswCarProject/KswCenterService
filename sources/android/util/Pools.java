package android.util;

import android.annotation.UnsupportedAppUsage;

public final class Pools {

    public interface Pool<T> {
        @UnsupportedAppUsage
        T acquire();

        @UnsupportedAppUsage
        boolean release(T t);
    }

    private Pools() {
    }

    public static class SimplePool<T> implements Pool<T> {
        @UnsupportedAppUsage
        private final Object[] mPool;
        private int mPoolSize;

        @UnsupportedAppUsage
        public SimplePool(int maxPoolSize) {
            if (maxPoolSize > 0) {
                this.mPool = new Object[maxPoolSize];
                return;
            }
            throw new IllegalArgumentException("The max pool size must be > 0");
        }

        @UnsupportedAppUsage
        public T acquire() {
            if (this.mPoolSize <= 0) {
                return null;
            }
            int lastPooledIndex = this.mPoolSize - 1;
            T instance = this.mPool[lastPooledIndex];
            this.mPool[lastPooledIndex] = null;
            this.mPoolSize--;
            return instance;
        }

        @UnsupportedAppUsage
        public boolean release(T instance) {
            if (isInPool(instance)) {
                throw new IllegalStateException("Already in the pool!");
            } else if (this.mPoolSize >= this.mPool.length) {
                return false;
            } else {
                this.mPool[this.mPoolSize] = instance;
                this.mPoolSize++;
                return true;
            }
        }

        private boolean isInPool(T instance) {
            for (int i = 0; i < this.mPoolSize; i++) {
                if (this.mPool[i] == instance) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object mLock;

        public SynchronizedPool(int maxPoolSize, Object lock) {
            super(maxPoolSize);
            this.mLock = lock;
        }

        @UnsupportedAppUsage
        public SynchronizedPool(int maxPoolSize) {
            this(maxPoolSize, new Object());
        }

        @UnsupportedAppUsage
        public T acquire() {
            T acquire;
            synchronized (this.mLock) {
                acquire = super.acquire();
            }
            return acquire;
        }

        @UnsupportedAppUsage
        public boolean release(T element) {
            boolean release;
            synchronized (this.mLock) {
                release = super.release(element);
            }
            return release;
        }
    }
}
