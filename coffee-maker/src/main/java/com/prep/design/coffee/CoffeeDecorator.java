package com.prep.design.coffee;

public abstract class CoffeeDecorator implements CoffeeCondiments{
    public CoffeeDecorator(CoffeeCondiments coffeeCondiments) {
        this.coffeeCondiments = coffeeCondiments;
    }

    private CoffeeCondiments coffeeCondiments;

    @Override
    public double getPrice() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
