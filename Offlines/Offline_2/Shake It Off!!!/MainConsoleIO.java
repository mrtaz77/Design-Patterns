import java.util.ArrayList;
import java.util.Scanner;

import builder.ShakeBuilder;
import director.Director;
import ingredients.Ingredient;
import ingredients.topping.*;
import shake.Shake;

public class MainConsoleIO {

    private static void menu(){
        System.out.println("Welcome to Shake It Off!!!");
        System.out.println(
            """
                Type
                'o' to open an order, 
                'e' to close an order, 
                'a' to add a shake, 
                'q' to quit.
            """
        );
    }

    private static char checkCustomization(Scanner scanner) {
        char choice = scanner.next().charAt(0);
        while(!(choice == 'y' || choice == 'n')) {
            System.out.println("Invalid choice: " + choice);
            System.out.print("Please select a correct choice (y/n)? ");
            choice = scanner.next().charAt(0);
        }
        return choice;
    }

    public static void main(String[] args) {
        ArrayList<Shake> orders = new ArrayList<Shake>();
        double price = 0;
        boolean isOpen = false;
        var director = new Director();

        menu();

        char choice = ' ';
        var scanner = new Scanner(System.in);
        
        while (choice != 'q') {
            System.out.print("Enter your choice : ");
            choice = scanner.next().charAt(0);

            switch (choice){
                case 'o':
                    if(isOpen){
                        System.out.println("Order already open");
                        System.out.println("Do you want to add anything?");
                    }else{
                        orders.clear();
                        price = 0;
                        isOpen = true;
                        System.out.println("Order opened");
                    }
                    break;
                case 'e':
                    if(!isOpen){
                        System.out.println("No order is open now.");
                    }else if(orders.size() == 0){
                        System.out.println("A shake must be ordered before closing current order");
                    }else {
                        System.out.println("Order:");
                        for(int i = 0; i < orders.size(); i ++){
                            System.out.print((i+1) + ") ");
                            System.out.println(orders.get(i));
                            price += orders.get(i).price();
                        }
                        System.out.println("Total price: " + price);
                        isOpen = false;
                        System.out.println("Order closed");
                    }
                    break;
                case 'a':
                    if (isOpen) {
                        System.out.print(
                            """
                            Shakes
                            1. Chocolate 
                            2. Coffee
                            3. Strawberry
                            4. Vanilla
                            5. Zero
                            Enter choice : 
                            """
                        );

                        int cmd = Integer.parseInt(scanner.next());
                        
                        if(cmd < 1 || cmd > 5) { 
                            System.out.println("Invalid command");
                            break;
                        }

                        System.out.print("Make shake lactose free? (y/n) ");
                        char milkChoice = checkCustomization(scanner);
                        boolean isFree = false;
                        if(milkChoice == 'y') isFree = true;

                        var addOns = new ArrayList<Ingredient>();

                        System.out.print("Add candy? (y/n) ");
                        char addOnChoice = checkCustomization(scanner);
                        if(addOnChoice == 'y') addOns.add(new Candy());

                        System.out.print("Add cookie? (y/n) ");
                        addOnChoice = checkCustomization(scanner);
                        if(addOnChoice == 'y') addOns.add(new Cookie());
                        
                        var maker = new ShakeBuilder();

                        switch(cmd){
                            case 1:
                                director.makeChocolateShake(maker, isFree, addOns);
                            break;
                            case 2:
                                director.makeCoffeeShake(maker, isFree, addOns);
                            break;
                            case 3:
                                director.makeStrawberryShake(maker, isFree, addOns);
                            break;
                            case 4:
                                director.makeVanillaShake(maker, isFree, addOns);
                            break;
                            case 5:
                                director.makeZeroShake(maker, isFree, addOns);
                            break;
                        }
                        
                        orders.add(maker.getShake());
                        System.out.println("Order placed successfully.");
                    } else {
                        System.out.println("Cannot add a shake. Open an order first.");
                    }
                    break;
                case 'q':
                    if(isOpen){
                        System.out.println("Please close current order");
                    }else{
                        System.out.println("Thank you, have a good day !!!");
                        scanner.close();
                        System.exit(0);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    } 
}