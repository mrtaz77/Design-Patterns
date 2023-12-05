package ingredients.flavouring;

import flavourful.Vanilla;

public class Vanillaflavoring implements Flavouring, Vanilla {
    @Override
    public String name() {
        return "Vanilla flavouring";    
    }
}
