package com.prep.design.coffee;

public class BlackCoffee extends CoffeeDecorator {
    private CoffeeCondiments coffeeCondiments;

    public BlackCoffee(CoffeeCondiments coffeeCondiments) {
        super(coffeeCondiments);
        this.coffeeCondiments=coffeeCondiments;
    }

    @Override
    public double getPrice() {
        return coffeeCondiments.getPrice()+CondimentEnum.BLACKCOFFEE.getValue();
    }

    @Override
    public String getDescription() {
        return coffeeCondiments.getDescription()+","+CondimentEnum.BLACKCOFFEE.getCondimentType();
    }
}
