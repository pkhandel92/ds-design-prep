package com.prep.design.coffee;

public class CoffeCondimentFactory {
    private static CoffeeCondiments coffeeCondiments;
    public static CoffeeCondiments getCondiment(Order order) {
        //Using same logic we can calculate price also and in an oops word this is a better approach.
        //But the interviewer wants to see decorator so here we go.
        switch (order){
            case LATTE: coffeeCondiments=new BlackCoffee(new Milk(new Coffee()));
            break;
            case SOY_LATTE:coffeeCondiments=new SoyMilk(new BlackCoffee(new Coffee()));
            break;
            default: coffeeCondiments=null;
        }
        return coffeeCondiments;
    }
}
