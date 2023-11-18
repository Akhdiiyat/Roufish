package com.example.roufish;

public class ListLelang {
    private String itemName;
    private double startingPrice;
    private String itemDescription;
    private String imageUrl;

    public ListLelang(String itemName, String itemDescription, double startingPrice, String imageUrl) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.startingPrice = startingPrice;
        this.imageUrl = imageUrl;
    }
    public String getImageUrl(){return imageUrl;}
    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getStartingPrice() {
        return startingPrice;
    }
}


