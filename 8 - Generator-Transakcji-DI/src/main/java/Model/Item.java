package Model;

import lombok.Data;

@Data
public class Item {
    private String name;
    private int quantity;
    private double price;

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Item(){};
}
