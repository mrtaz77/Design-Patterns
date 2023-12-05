package ingredients.milk;

public class RegularMilk implements Milk {
    @Override
    public boolean isLactoseFree() {
        return false;    
    }

    @Override
    public String milk() {
        return "Milk";    
    }    
}
