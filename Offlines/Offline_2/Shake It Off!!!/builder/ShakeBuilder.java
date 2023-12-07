package builder;

import java.util.ArrayList;
import java.util.List;

import ingredients.Ingredient;
import priced.Priced;
import shake.*;

public class ShakeBuilder {
    private ShakeType type;
    private double basePrice = 0;
    private double extraPrice = 0;
    private List<Ingredient> baseIngredients = new ArrayList<Ingredient>();
    private List<Ingredient> extraIngredients = new ArrayList<Ingredient>(); 

    public void setType(ShakeType type){
        this.type = type;
    }

    public String getType() {
        return type.name();
    }

    public void setBasePrice(double basePrice){
        this.basePrice = basePrice;
    }

    public void addBasic(Ingredient ingredient){
        baseIngredients.add(ingredient);
    }

    public void addExtra(Ingredient ingredient){
        extraIngredients.add(ingredient);
        extraPrice += ((Priced)ingredient).price();
    }

    public Shake getShake(){
        return new Shake(type, basePrice, extraPrice, baseIngredients, extraIngredients);
    }
}
