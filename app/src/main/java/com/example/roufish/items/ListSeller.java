package com.example.roufish.items;

public class ListSeller {

    private String productName;
    private int productPrice;
    private String productImage;
    private String sellerId;
    private String productId;
    private String productDescription;


    public ListSeller(String productName, int productPrice, String productImage, String sellerId, String productId, String productDescription) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.sellerId = sellerId;
        this.productId = productId;
        this.productDescription = productDescription;
    }
    public String getProductDescription(){return  productDescription;}
    public void setProductDescription(String productDescription){this.productDescription  = productDescription;}
    public String getSellerId() {
        return sellerId;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
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

}
