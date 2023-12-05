package ingredients.topping;

public class Candy implements Topping {
    @Override
    public double price() {
        return 50;    
    }
    
    @Override
    public String topping() {
        return "Candy";
    }
}