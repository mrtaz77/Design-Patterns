package ingredients.jello;

import ingredients.Ingredient;

public interface Jello extends Ingredient{
    default public String name(){
        return "Jello";
    }
}
