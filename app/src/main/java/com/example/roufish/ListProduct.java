package com.example.roufish;

public class ListProduct {
    private String name;
    private int price;
    private String deskripsi;
    private String imageResId;

    public ListProduct(String name,String deskripsi, int price, String imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }
    public ListProduct()  { }

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

