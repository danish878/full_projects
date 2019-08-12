package com.danny.discoveryserver.test.pizzachallenge;

public class Peppers extends PizzaDecorator {
    public Peppers(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Peppers";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.75;
    }
}
