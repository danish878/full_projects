package com.danny.discoveryserver.test.pizzachallenge;

public class Cheese extends PizzaDecorator {

    public Cheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Cheese";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
}
