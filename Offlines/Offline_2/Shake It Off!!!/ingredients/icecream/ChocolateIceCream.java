package ingredients.icecream;

import flavourful.Chocolate;

public class ChocolateIceCream implements IceCream, Chocolate {
    @Override
    public String name() {
        return "Chocolate Ice cream";    
    }
}
