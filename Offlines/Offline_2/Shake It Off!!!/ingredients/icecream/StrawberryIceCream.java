package ingredients.icecream;

import flavourful.Strawberry;

public class StrawberryIceCream implements IceCream, Strawberry {
    @Override
    public String iceCream() {
        return "Strawberry Ice cream";    
    }
    
}
