package service;

import impl.Cache;
import impl.EvictionPolicy;
import policy.LruEvictionPolicy;
import validation.CacheValid;

public class CacheBuilder {
    public static <K, V> Builder<K, V> newCache() {
        return new Builder<>();
    }

    public static final class Builder<K, V> {
        private final CacheValid<K, V> cfg = new CacheValid<>();

        public Builder<K, V> capacity(int capacity) {
            cfg.capacity(capacity);
            return this;
        }

        public Builder<K, V> policy(EvictionPolicy<K> policy) {
            cfg.policy(policy);
            return this;
        }

        public Builder<K, V> lru() {
            cfg.policy(new LruEvictionPolicy<>());
            return this;
        }

        public Cache<K, V> build() {
            return new CacheLRU<K, V>().configure(cfg);
        }
    }
}
