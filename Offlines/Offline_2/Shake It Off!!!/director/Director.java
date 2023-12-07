package director;

import java.util.*;

import builder.ShakeBuilder;
import ingredients.Ingredient;
import ingredients.coffee.Coffee;
import ingredients.flavouring.*;
import ingredients.icecream.*;
import ingredients.jello.*;
import ingredients.milk.*;
import ingredients.sweet.*;
import ingredients.syrup.*;
import shake.ShakeType;

public class Director {

    public void setMilk(ShakeBuilder maker, boolean isLactoseFree) {
        if(!isLactoseFree)maker.addBasic(new RegularMilk());
        else maker.addExtra(new AlmondMilk());
    }

    public void setExtra(ShakeBuilder maker, List<Ingredient> extra){
        for(Ingredient ingredient : extra){
            maker.addExtra(ingredient);
        }
    }

    public void makeChocolateShake(ShakeBuilder maker) {
        maker.setType(ShakeType.Chocolate);
        maker.setBasePrice(230);
        maker.addBasic(new Sugar());
        maker.addBasic(new ChocolateIceCream());
        maker.addBasic(new ChocolateSyrup());
    }

    public void makeCoffeeShake(ShakeBuilder maker) {
        maker.setType(ShakeType.Coffee);
        maker.setBasePrice(250);
        maker.addBasic(new Sugar());
        maker.addBasic(new Coffee());
        maker.addBasic(new Jello());
    }

    public void makeStrawberryShake(ShakeBuilder maker) {
        maker.setType(ShakeType.Strawberry);
        maker.setBasePrice(200);
        maker.addBasic(new Sugar());
        maker.addBasic(new StrawberrySyrup());
        maker.addBasic(new StrawberryIceCream());
    }

    public void makeVanillaShake(ShakeBuilder maker) {
        maker.setType(ShakeType.Vanilla);
        maker.setBasePrice(190);
        maker.addBasic(new Sugar());
        maker.addBasic(new Vanillaflavoring());
        maker.addBasic(new Jello());
    }

    public void makeZeroShake(ShakeBuilder maker) {
        maker.setType(ShakeType.Zero);
        maker.setBasePrice(240);
        maker.addBasic(new Sweetner());
        maker.addBasic(new Vanillaflavoring());
        maker.addBasic(new SugarFreeJello());
    }
}
