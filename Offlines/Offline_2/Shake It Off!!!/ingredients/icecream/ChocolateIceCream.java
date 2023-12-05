package ingredients.icecream;

import flavourful.Chocolate;

public class ChocolateIceCream implements IceCream, Chocolate {
    @Override
    public String iceCream() {
        return "Chocolate Ice cream";    
    }
}
