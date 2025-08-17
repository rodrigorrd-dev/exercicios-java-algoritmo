package service;

import model.Item;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DefaultPricingStrategyTest {
    private final DefaultPricingStrategy strategy = new DefaultPricingStrategy();

    @Test
    void deveCalcularTotalCorretamente() {
        List<Item> items = Arrays.asList(
                new Item("Notebook", 2500.0),
                new Item("Mouse", 100.0),
                new Item("Teclado", 150.0)
        );

        double total = strategy.calculate(items);

        assertEquals(2750.0, total, 0.001);
    }

    @Test
    void deveRetornarZeroSeListaVazia() {
        List<Item> items = Collections.emptyList();

        double total = strategy.calculate(items);

        assertEquals(0.0, total, 0.001);
    }

    @Test
    void deveCalcularComUmItem() {
        List<Item> items = List.of(new Item("Caderno", 20.0));

        double total = strategy.calculate(items);

        assertEquals(20.0, total, 0.001);
    }
}
