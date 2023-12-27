package com.example.roufish.activities;

import java.io.Serializable;

public class ListLelang implements Serializable {
    private String itemName;
    private String itemDescription;
    private int startingPrice;
    private int kelipatan;
    private int berat;
    private String imageUrl;

    public ListLelang() {
    }


    public ListLelang(String itemName, String itemDescription, int startingPrice, int kelipatan, int berat, String imageUrl) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.startingPrice = startingPrice;
        this.kelipatan = kelipatan;
        this.berat = berat;
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getKelipatan() {
        return kelipatan;
    }

    public void setKelipatan(int kelipatan) {
        this.kelipatan = kelipatan;
    }

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
