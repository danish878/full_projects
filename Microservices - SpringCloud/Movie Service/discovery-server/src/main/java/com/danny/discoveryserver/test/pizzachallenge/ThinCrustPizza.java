package com.danny.discoveryserver.test.pizzachallenge;

public class ThinCrustPizza implements Pizza {

    @Override
    public String getDescription() {
        return "Thin Crust Pizza";
    }

    @Override
    public double getCost() {
        return 5.5;
    }
}
