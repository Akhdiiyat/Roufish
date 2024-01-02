package com.example.roufish.items;



public class listRiwayatPenjualan /*implements Parcelable*/ {
    private String name, idPenjualan;
    private String price;
    private String deskripsi;
    private String imageResId;
    private long timeStamp;

    public listRiwayatPenjualan(){

    }

    public listRiwayatPenjualan(String idPenjualan,String name, String price, String imageResId, long timeStamp) {
        this.idPenjualan = idPenjualan;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getImageResId() {
        return imageResId;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setImageResId(String imageResId) {
        this.imageResId = imageResId;
    }


}
