package impl;

import model.Item;

import java.util.List;

public interface PricingStrategy {
    double calculate(List<Item> items);
}
