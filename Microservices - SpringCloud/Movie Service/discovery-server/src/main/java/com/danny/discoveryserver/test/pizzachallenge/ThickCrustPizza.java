package com.danny.discoveryserver.test.pizzachallenge;

public class ThickCrustPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Thick Crust Pizza";
    }

    @Override
    public double getCost() {
        return 7.25;
    }
}
