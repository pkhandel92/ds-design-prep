package com.prep.design.coffee;

public enum Order {
    LATTE(CondimentEnum.BLACKCOFFEE.getCondimentType()+","+CondimentEnum.MILK.getCondimentType()),
    SOY_LATTE(CondimentEnum.BLACKCOFFEE+","+CondimentEnum.SOYMILK.getCondimentType());
    private String description;

    Order(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
