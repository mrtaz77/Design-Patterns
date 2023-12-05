package ingredients.syrup;

import flavourful.Strawberry;

public class StrawberrySyrup implements Syrup, Strawberry{
    @Override
    public String syrup() {
        return "Strawberry syrup";    
    }
}
