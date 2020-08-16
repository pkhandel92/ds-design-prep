package com.prep.design.coffee;

public class Coffee implements CoffeeCondiments {
    @Override
    public double getPrice() {
        return 0.10;
    }

    @Override
    public String getDescription() {
        return "Base coffee";
    }
}
