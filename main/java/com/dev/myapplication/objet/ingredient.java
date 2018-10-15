package com.dev.myapplication.objet;

public class ingredient {
    int id; String name;String quantity;

    public int getId() {
        return id;
    }

    public ingredient() {
    }

    public ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ingredient(int id, String name, String quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;

    }
}
