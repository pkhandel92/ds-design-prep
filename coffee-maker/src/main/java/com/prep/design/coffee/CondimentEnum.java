package com.prep.design.coffee;

import java.util.HashMap;
import java.util.Map;

public enum CondimentEnum {
    BLACKCOFFEE("black_coffee",0.50),
    SOYMILK("soyMilk",1.00),
    MILK("milk",0.35);
   public  static Map<String,CondimentEnum> condimentMap;
    static {
        condimentMap=new HashMap<>();
        for (CondimentEnum condimentEnum:CondimentEnum.values()){
            condimentMap.put(condimentEnum.getCondimentType(),condimentEnum);
        }
    }


    private String condimentType;
    private double value;
    CondimentEnum(String condimentType, double value) {
        this.condimentType=condimentType;
        this.value=value;

    }
    public String getCondimentType() {
        return condimentType;
    }

    public double getValue() {
        return value;
    }
}
