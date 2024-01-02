package com.example.roufish.items;

public class ListSeller {

    private String productName;
    private int productPrice;
    private String productImage;
    private String sellerId;

    public ListSeller(String productName, int productPrice, String productImage, String sellerId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.sellerId = sellerId;
        // Initialize other properties in the constructor
    }
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    // Add getter and setter methods for other properties
}
