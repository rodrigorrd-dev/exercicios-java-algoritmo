package model;

import impl.PricingStrategy;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long codigo;
    private final List<Item> items;
    private final PricingStrategy pricingStrategy;

    public Order(PricingStrategy pricingStrategy) {
        this.items = new ArrayList<>();
        this.pricingStrategy = pricingStrategy;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public double calculateTotal() {
        return pricingStrategy.calculate(items);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}