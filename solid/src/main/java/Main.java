import impl.PricingStrategy;
import model.Item;
import model.Order;
import service.DefaultPricingStrategy;

public class Main {
    public static void main(String[] args) {
        PricingStrategy strategy = new DefaultPricingStrategy();
        Order order = new Order(strategy);

        order.addItem(new Item("Notebook", 3500.00));
        order.addItem(new Item("Mouse", 200.00));

        System.out.println("Total: " + order.calculateTotal());
    }

}