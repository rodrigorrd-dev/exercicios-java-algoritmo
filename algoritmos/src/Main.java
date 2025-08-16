import impl.Cache;
import model.Produto;
import service.CacheBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int CAPACITY = 3;

    public static void main(String[] args) {
        Cache<String, Produto> cache = CacheBuilder.<String, Produto>newCache()
                .capacity(CAPACITY)
                .lru()
                .build();

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto("SKU-1001", "Notebook Lenovo LOQ 15", 4799.90));
        produtos.add(new Produto("SKU-1002", "SSD NVMe 1TB", 389.00));
        produtos.add(new Produto("SKU-1003", "Monitor 27\" 144Hz", 1199.00));
        produtos.add(new Produto("SKU-1004", "Mouse Gamer", 89.90));

        // Coloca os primeiros produtos no cache
        for (int i = 0; i < CAPACITY; i++) {
            Produto p = produtos.get(i);
            cache.put(p.getCodigo(), p);
        }

        // Acessa um item para "promover" a MRU
        cache.get("SKU-1001");

        // Agora adiciona o 4º produto -> expulsa o menos recentemente usado (SKU-1002)
        Produto p4 = produtos.get(3);
        cache.put(p4.getCodigo(), p4);

        // Verificações
        System.out.println("SKU-1002 -> " + cache.get("SKU-1002")); // esperado: null
        System.out.println("SKU-1003 -> " + cache.get("SKU-1003")); // esperado: Monitor
        System.out.println("Itens na cache: " + cache.size());      // esperado: 3
    }
}