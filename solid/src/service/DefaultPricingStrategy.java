package service;

import impl.PricingStrategy;
import model.Item;

import java.util.List;

public class DefaultPricingStrategy implements PricingStrategy {

    @Override
    public double calculate(List<Item> items) {
        double total = 0;
        for (Item item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
