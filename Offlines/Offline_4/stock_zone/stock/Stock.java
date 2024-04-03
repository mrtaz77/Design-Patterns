package stock;

import java.io.Serializable;

import util.DoubleToString;

public class Stock implements DoubleToString,Serializable {
    private String name;
    private int quantity;
    private double price;

    public Stock(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

	public void setName(String name) {
		this.name = name;
	}

    public String getName() {
        return name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

	@Override 
	public String toString() {
		return name + " " + quantity + " " + doubleToString(price) ;
	}
}
