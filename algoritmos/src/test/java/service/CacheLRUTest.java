package service;

import impl.EvictionPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CacheLRUTest {

    private CacheLRU<String, String> cache;
    private FakeEvictionPolicy policy;

    @BeforeEach
    void setup() {
        cache = new CacheLRU<>();
        policy = new FakeEvictionPolicy();
        cache.setCapacity(2).setPolicy(policy);
    }

    @Test
    void deveInserirERecuperarValor() {
        cache.put("a", "alpha");

        assertEquals("alpha", cache.get("a"));
        assertEquals(1, cache.size());
    }

    @Test
    void deveAtualizarValorExistente() {
        cache.put("a", "alpha");
        cache.put("a", "novo");

        assertEquals("novo", cache.get("a"));
        assertEquals(1, cache.size(), "não deve duplicar chave");
    }

    @Test
    void deveRemoverValor() {
        cache.put("a", "alpha");
        cache.remove("a");

        assertNull(cache.get("a"));
        assertEquals(0, cache.size());
    }

    @Test
    void deveRespeitarCapacidadeComEviction() {
        cache.put("a", "alpha");
        cache.put("b", "beta");
        cache.put("c", "gamma"); // ultrapassa a capacidade (2)

        // política fake sempre remove "a"
        assertNull(cache.get("a"));
        assertNotNull(cache.get("b"));
        assertNotNull(cache.get("c"));
        assertEquals(2, cache.size());
    }

    @Test
    void deveLancarExcecaoSeNaoConfigurado() {
        CacheLRU<String, String> semConfig = new CacheLRU<>();

        assertThrows(IllegalStateException.class, () -> semConfig.put("x", "y"));
        assertThrows(IllegalStateException.class, () -> semConfig.get("x"));
        assertThrows(IllegalStateException.class, () -> semConfig.remove("x"));
    }

    // --- implementação fake de EvictionPolicy só para teste ---
    static class FakeEvictionPolicy implements EvictionPolicy<String> {

        @Override
        public void onPut(String key) {
        }

        @Override
        public void onGet(String key) {
        }

        @Override
        public void onRemove(String key) {
        }

        @Override
        public void clear() {
        }

        @Override
        public String evictKey() {
            // sempre remove "a" (controlado para os testes)
            return "a";
        }
    }
}