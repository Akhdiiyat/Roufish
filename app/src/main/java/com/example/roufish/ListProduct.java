package com.example.roufish;

public class ListProduct {
    private String name;
    private double price;
    private String imageResId;

    public ListProduct(String name, double price, String imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public String getImageResId() {
        return imageResId;
    }
    // Add any other methods or attributes you need for your ListProduct class
}

