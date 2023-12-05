package ingredients.milk;

import priced.Priced;

public class AlmondMilk implements Milk, Priced{
    @Override
    public boolean isLactoseFree() {
        return true;    
    }

    @Override
    public String milk() {
        return "Almond Milk";    
    }

    @Override
    public double price() {
        return 60;    
    }
}
