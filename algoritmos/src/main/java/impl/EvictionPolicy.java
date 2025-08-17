package impl;

public interface EvictionPolicy<K> {
    void onPut(K key);

    void onGet(K key);

    void onRemove(K key);

    /**
     * Próxima chave a ser expulsa (ou null se nenhuma).
     */
    K evictKey();

    /**
     * Limpa estado interno (opcional).
     */
    default void clear() {
    }
}
