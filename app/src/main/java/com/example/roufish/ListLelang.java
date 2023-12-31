package com.example.roufish;

// ListLelang.java
import android.os.Parcel;
import android.os.Parcelable;

public class ListLelang implements Parcelable {
    private String itemName;
    private int startingPrice;
    private String itemDescription;
    private String imageUrl;

    private String documentId;

    public ListLelang(String itemName, String itemDescription, int startingPrice, String imageUrl, String documentId) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.startingPrice = startingPrice;
        this.imageUrl = imageUrl;
        this.documentId = documentId;
    }
    public String getDocumentId() {
        return documentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    // Parcelable implementation
    protected ListLelang(Parcel in) {
        itemName = in.readString();
        startingPrice = in.readInt();
        itemDescription = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<ListLelang> CREATOR = new Creator<ListLelang>() {
        @Override
        public ListLelang createFromParcel(Parcel in) {
            return new ListLelang(in);
        }

        @Override
        public ListLelang[] newArray(int size) {
            return new ListLelang[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeInt(startingPrice);
        dest.writeString(itemDescription);
        dest.writeString(imageUrl);
    }
}

