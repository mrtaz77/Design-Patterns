package ingredients.flavouring;

import flavourful.Vanilla;

public class Vanillaflavoring implements Flavouring, Vanilla {
    @Override
    public String flavouring() {
        return "Vanilla flavouring";    
    }
}
