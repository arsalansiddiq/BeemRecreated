package com.example.arsalansiddiq.beem.models;

public class UserSelectedItems {

    private String name;
    private int quantityLoose, quantityCarton;

    public UserSelectedItems(String name, int quantityLoose, int quantityCarton) {
        this.name = name;
        this.quantityLoose = quantityLoose;
        this.quantityCarton = quantityCarton;
    }

    public String getName() {
        return name;
    }

    public int getQuantityLoose() {
        return quantityLoose;
    }

    public int getQuantityCarton() {
        return quantityCarton;
    }
}
