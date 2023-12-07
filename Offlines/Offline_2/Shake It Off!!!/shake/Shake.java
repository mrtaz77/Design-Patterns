package shake;

import java.util.List;

import ingredients.Ingredient;
import priced.Priced;

public class Shake implements Priced{
    private final ShakeType type;
    private final double basePrice;
    private double extraPrice;
    private final List<Ingredient> baseIngredients;
    private List<Ingredient> extraIngredients;

    public Shake(ShakeType type, double basePrice, double extraPrice,List<Ingredient> baseIngredients, List<Ingredient> extraIngredients) {
        this.type = type;
        this.basePrice = basePrice;
        this.extraPrice = extraPrice;
        this.baseIngredients = baseIngredients;
        this.extraIngredients = extraIngredients;
    }

    public ShakeType getType() {
        return type;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getExtraPrice() {
        return extraPrice;
    }

    public List<Ingredient> getBaseIngredients() {
        return baseIngredients;
    }

    public List<Ingredient> getExtraIngredients() {
        return extraIngredients;
    }

    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public void setExtraIngredients(List<Ingredient> extraIngredients) {
        this.extraIngredients = extraIngredients;
    }

    @Override
    public double price() {
        return basePrice + extraPrice;    
    }

    @Override
    public String toString(){
        var shake = new StringBuilder();
        shake.append(type.name()).append(" shake\n");

        shake.append("Basic Ingredients\n");

        for(Ingredient ingredient : baseIngredients){
            shake.append(ingredient.name()).append("\n");
        }

        if(extraIngredients.size() > 0){
            shake.append("Added Ingredients\n");

            for(Ingredient ingredient : extraIngredients){
                shake.append(ingredient.name()).append(" : ").append(((Priced)ingredient).price()).append("\n");
            }
        }

        shake.append("Price : ").append(price()).append("\n");

        if(extraPrice > 0){
            shake.append("Price increased by ").append(extraPrice).append("\n");
        }

        return shake.toString();
    }
}