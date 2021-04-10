package com.tss.pakbourini.model;

public class FoodItem {
    private int id;
    private String title, min, price,descriptions,stock,image;
    private float rating;

    public FoodItem(int id, String title, String min, String price, String descriptions, String stock, String image, float rating) {
        this.id = id;
        this.title = title;
        this.min = min;
        this.price = price;
        this.descriptions = descriptions;
        this.stock = stock;
        this.image = image;
        this.rating = rating;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
