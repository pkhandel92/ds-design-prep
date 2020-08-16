package com.prep.design.coffee;

public class SoyMilk extends CoffeeDecorator{
    private CoffeeCondiments coffeeCondiments;

    public SoyMilk(CoffeeCondiments coffeeCondiments) {
        super(coffeeCondiments);
        this.coffeeCondiments=coffeeCondiments;
    }

    @Override
    public double getPrice() {
        return coffeeCondiments.getPrice()+CondimentEnum.SOYMILK.getValue();
    }

    @Override
    public String getDescription() {
        return coffeeCondiments.getDescription()+","+CondimentEnum.SOYMILK.getCondimentType();
    }
}
