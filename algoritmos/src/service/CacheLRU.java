package service;

import impl.Cache;
import impl.EvictionPolicy;
import validation.CacheValid;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLRU<K, V> implements Cache<K, V> {

    private final Map<K, V> store = new HashMap<>();
    private int capacity = Integer.MAX_VALUE;
    private EvictionPolicy<K> policy;

    public CacheLRU() {
    }

    /**
     * Aplicar configuração modular (capacidade + política).
     */
    public CacheLRU<K, V> configure(CacheValid<K, V> cfg) {
        this.capacity = cfg.capacity();
        this.policy = cfg.policy();
        // reindexa a política com as chaves já existentes, se houver
        policy.clear();
        for (K k : store.keySet()) policy.onPut(k);
        enforceCapacity();
        return this;
    }

    public CacheLRU<K, V> setCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacidade deve ser > 0");
        this.capacity = capacity;
        enforceCapacity();
        return this;
    }

    public CacheLRU<K, V> setPolicy(EvictionPolicy<K> policy) {
        if (policy == null) throw new IllegalArgumentException("policy == null");
        this.policy = policy;
        policy.clear();
        for (K k : store.keySet()) policy.onPut(k);
        enforceCapacity();
        return this;
    }

    @Override
    public void put(K key, V value) {
        ensureConfigured();
        boolean existed = store.containsKey(key);
        store.put(key, value);
        policy.onPut(key);
        if (!existed) enforceCapacity();
    }

    @Override
    public V get(K key) {
        ensureConfigured();
        V v = store.get(key);
        if (v != null) policy.onGet(key);
        return v;
    }

    @Override
    public void remove(K key) {
        ensureConfigured();
        if (store.containsKey(key)) {
            store.remove(key);
            policy.onRemove(key);
        }
    }

    @Override
    public int size() {
        return store.size();
    }

    private void enforceCapacity() {
        while (store.size() > capacity) {
            K victim = policy.evictKey();
            if (victim == null) break;
            store.remove(victim);
            policy.onRemove(victim);
        }
    }

    private void ensureConfigured() {
        if (policy == null)
            throw new IllegalStateException("Cache não configurado. Use configure(cfg) ou setPolicy/setCapacity.");
    }

}
