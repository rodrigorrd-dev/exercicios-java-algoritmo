package policy;

import impl.EvictionPolicy;

import java.util.LinkedHashMap;

public class LruEvictionPolicy<K> implements EvictionPolicy<K> {
    private final LinkedHashMap<K, Boolean> order = new LinkedHashMap<>(16, 0.75f, true);

    @Override
    public void onPut(K key) {
        order.put(key, Boolean.TRUE); // promove
    }

    @Override
    public void onGet(K key) {
        if (order.containsKey(key)) {
            // acesso promove automaticamente em accessOrder=true
            order.get(key);
        }
    }

    @Override
    public void onRemove(K key) {
        order.remove(key);
    }

    @Override
    public K evictKey() {
        if (order.isEmpty()) return null;
        // primeiro entry = LRU em accessOrder=true
        return order.entrySet().iterator().next().getKey();
    }

    @Override
    public void clear() {
        order.clear();
    }
}
