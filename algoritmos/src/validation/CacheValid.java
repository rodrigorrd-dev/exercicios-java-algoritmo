package validation;

import impl.EvictionPolicy;
import policy.LruEvictionPolicy;

public class CacheValid<K, V> {

    private int capacity = Integer.MAX_VALUE;
    private EvictionPolicy<K> policy = new LruEvictionPolicy<>();

    public CacheValid<K, V> capacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacidade deve ser maior que 0");
        this.capacity = capacity;
        return this;
    }

    public CacheValid<K, V> policy(EvictionPolicy<K> policy) {
        if (policy == null) throw new IllegalArgumentException("Policy igual a vazio");
        this.policy = policy;
        return this;
    }

    public int capacity() {
        return capacity;
    }

    public EvictionPolicy<K> policy() {
        return policy;
    }

}
