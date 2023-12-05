package ingredients.syrup;

import flavourful.Chocolate;

public class ChocolateSyrup implements Syrup, Chocolate {
    @Override
    public String syrup() {
        return "Chocolate syrup";    
    }
}
