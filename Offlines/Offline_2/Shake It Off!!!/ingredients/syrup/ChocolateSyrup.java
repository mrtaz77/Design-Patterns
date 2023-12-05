package ingredients.syrup;

import flavourful.Chocolate;

public class ChocolateSyrup implements Syrup, Chocolate {
    @Override
    public String name() {
        return "Chocolate syrup";    
    }
}
