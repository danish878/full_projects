package com.danny.discoveryserver.test.pizzachallenge;

public class PizzaHut {

    public static void main(String[] args) {
        Pizza pizza = new ThickCrustPizza();
        pizza = new Cheese(pizza);
        pizza = new Olives(pizza);
        pizza = new Peppers(pizza);
        System.out.println(pizza.getDescription());
        System.out.println(pizza.getCost());
    }
}