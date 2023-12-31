package com.example.roufish.items;
import android.os.Parcel;
import android.os.Parcelable;

public class ListProduct implements Parcelable {
    private String name;
    private int price;
    private String deskripsi;
    private String imageResId;

    public ListProduct(String name, String deskripsi, int price, String imageResId) {
        this.name = name;
        this.deskripsi = deskripsi;
        this.price = price;
        this.imageResId = imageResId;
    }

    protected ListProduct(Parcel in) {
        name = in.readString();
        deskripsi = in.readString();
        price = in.readInt();
        imageResId = in.readString();
    }

    public static final Creator<ListProduct> CREATOR = new Creator<ListProduct>() {
        @Override
        public ListProduct createFromParcel(Parcel in) {
            return new ListProduct(in);
        }

        @Override
        public ListProduct[] newArray(int size) {
            return new ListProduct[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getPrice() {
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setImageResId(String imageResId) {
        this.imageResId = imageResId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(deskripsi);
        dest.writeInt(price);
        dest.writeString(imageResId);
    }
}
