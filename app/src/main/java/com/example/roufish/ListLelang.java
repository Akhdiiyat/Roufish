package com.example.roufish;

public class ListLelang {
    private String itemName;
    private int startingPrice;
    private String itemDescription;
    private String imageUrl;

    public ListLelang(String itemName, String itemDescription, int startingPrice, String imageUrl) {
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

    public int getStartingPrice() {
        return startingPrice;
    }
}


