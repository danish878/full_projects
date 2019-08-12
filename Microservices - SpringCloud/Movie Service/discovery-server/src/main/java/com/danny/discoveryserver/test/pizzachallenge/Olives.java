package com.danny.discoveryserver.test.pizzachallenge;

public class Olives extends PizzaDecorator {
    public Olives(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Olives";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.25;
    }
}
