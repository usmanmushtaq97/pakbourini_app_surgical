package com.tss.pakbourini.model;

public class FoodItemQty {
    private String gram, type, family;

    public FoodItemQty(String gram, String type, String family) {
        this.gram = gram;
        this.type = type;
        this.family = family;
    }

    public String getGram() {
        return gram;
    }

    public String getType() {
        return type;
    }

    public String getFamily() {
        return family;
    }
}
