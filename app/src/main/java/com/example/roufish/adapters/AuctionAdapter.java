package com.example.roufish.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.ListLelang;
import com.example.roufish.R;
import com.example.roufish.PageLelang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.ViewHolder> {


    private ArrayList<ListLelang> productList;

    /*public AuctionAdapter(ArrayList<ListLelang> productList) {
        this.productList = productList;
    }*/

    public AuctionAdapter(ArrayList<com.example.roufish.ListLelang> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.roufish.ListLelang product = productList.get(position);

        // Menggunakan Picasso untuk memuat gambar dari URL
        /*FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection("produkLelang").document();
        String produkId= documentReference.getId();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference produkRef = storageReference.child("produklelang/" + produkId + ".jpg"  );


        produkRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.productAuctionImageView);
            }
        });*/
        //Picasso.get().load(product.getImageUrl()).into(holder.productAuctionImageView);

        // Menetapkan teks pada TextView berdasarkan data produk
        holder.productAuctionNameTextView.setText(product.getItemName());
        holder.productStartingPriceTextView.setText(String.valueOf(product.getStartingPrice()));
        holder.productDescriptionTextView.setText(product.getItemDescription());

        // Menambahkan onClickListener untuk setiap item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengirimkan data produk ke PageLelang atau aktivitas lain
                Intent intent = new Intent(v.getContext(), PageLelang.class);
                intent.putExtra("product_data", String.valueOf(product));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productAuctionImageView;
        TextView productAuctionNameTextView;
        TextView productStartingPriceTextView;
        TextView productDescriptionTextView;
        TextView productWeightTextView; // TextView baru untuk berat
        TextView productIncrementTextView; // TextView baru untuk kelipatan

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productAuctionImageView = itemView.findViewById(R.id.productAuctionImageView);
            productAuctionNameTextView = itemView.findViewById(R.id.productAuctionNameTextView);
            productStartingPriceTextView = itemView.findViewById(R.id.productStartingPriceTextView);
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            /*productWeightTextView = itemView.findViewById(R.id.productWeightTextView); // ID baru untuk berat
            productIncrementTextView = itemView.findViewById(R.id.productIncrementTextView); // ID baru untuk kelipatan*/
        }
    }
}
