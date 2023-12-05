package ingredients.topping;

public class Cookie implements Topping {
    @Override
    public double price() {
        return 40;    
    }

    @Override
    public String topping() {
        return "Cookie";    
    }
}
