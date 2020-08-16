package com.prep.design.coffee;

public class CoffeeMaker implements ICoffeeMaker{
    @Override
    public double getPrice(Order order) {
        CoffeeCondiments coffeeCondiments=CoffeCondimentFactory.getCondiment(order);
        if(coffeeCondiments==null)
            return 0;
        return coffeeCondiments.getPrice();
    }

    @Override
    public String getOrderDesc(Order order) {
        CoffeeCondiments coffeeCondiments=CoffeCondimentFactory.getCondiment(order);
        if(coffeeCondiments==null)
            return "We dont serve this order";
        return coffeeCondiments.getDescription();
    }

    public static void main(String[] args) {
        CoffeeMaker coffeeMaker=new CoffeeMaker();

        System.out.println("Price "+coffeeMaker.getPrice(Order.LATTE));
        System.out.println("Price "+coffeeMaker.getPrice(Order.SOY_LATTE));
    }
}
