package com.prep.design.coffee;

public class Milk extends CoffeeDecorator{
    private CoffeeCondiments coffeeCondiments;
    public Milk(CoffeeCondiments coffeeCondiments) {
        super(coffeeCondiments);
        this.coffeeCondiments=coffeeCondiments;
    }

    @Override
    public double getPrice() {
        return coffeeCondiments.getPrice()+CondimentEnum.MILK.getValue();
    }

    @Override
    public String getDescription() {
        return coffeeCondiments.getDescription()+","+CondimentEnum.MILK.getCondimentType();
    }
}
